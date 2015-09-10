/*    1:     */ package org.apache.xalan.templates;
/*    2:     */ 
/*    3:     */ import java.io.PrintStream;
/*    4:     */ import java.util.Vector;
/*    5:     */ import org.apache.xalan.res.XSLMessages;
/*    6:     */ import org.apache.xml.utils.QName;
/*    7:     */ import org.apache.xml.utils.WrappedRuntimeException;
/*    8:     */ import org.apache.xpath.Expression;
/*    9:     */ import org.apache.xpath.ExpressionNode;
/*   10:     */ import org.apache.xpath.ExpressionOwner;
/*   11:     */ import org.apache.xpath.XPath;
/*   12:     */ import org.apache.xpath.axes.AxesWalker;
/*   13:     */ import org.apache.xpath.axes.FilterExprIteratorSimple;
/*   14:     */ import org.apache.xpath.axes.FilterExprWalker;
/*   15:     */ import org.apache.xpath.axes.LocPathIterator;
/*   16:     */ import org.apache.xpath.axes.PredicatedNodeTest;
/*   17:     */ import org.apache.xpath.axes.SelfIteratorNoPredicate;
/*   18:     */ import org.apache.xpath.axes.WalkerFactory;
/*   19:     */ import org.apache.xpath.axes.WalkingIterator;
/*   20:     */ import org.apache.xpath.operations.Variable;
/*   21:     */ import org.apache.xpath.operations.VariableSafeAbsRef;
/*   22:     */ import org.w3c.dom.DOMException;
/*   23:     */ 
/*   24:     */ public class RedundentExprEliminator
/*   25:     */   extends XSLTVisitor
/*   26:     */ {
/*   27:     */   Vector m_paths;
/*   28:     */   Vector m_absPaths;
/*   29:     */   boolean m_isSameContext;
/*   30:  54 */   AbsPathChecker m_absPathChecker = new AbsPathChecker();
/*   31:  56 */   private static int m_uniquePseudoVarID = 1;
/*   32:     */   static final String PSUEDOVARNAMESPACE = "http://xml.apache.org/xalan/psuedovar";
/*   33:     */   public static final boolean DEBUG = false;
/*   34:     */   public static final boolean DIAGNOSE_NUM_PATHS_REDUCED = false;
/*   35:     */   public static final boolean DIAGNOSE_MULTISTEPLIST = false;
/*   36:  66 */   VarNameCollector m_varNameCollector = new VarNameCollector();
/*   37:     */   
/*   38:     */   public RedundentExprEliminator()
/*   39:     */   {
/*   40:  73 */     this.m_isSameContext = true;
/*   41:  74 */     this.m_absPaths = new Vector();
/*   42:  75 */     this.m_paths = null;
/*   43:     */   }
/*   44:     */   
/*   45:     */   public void eleminateRedundentLocals(ElemTemplateElement psuedoVarRecipient)
/*   46:     */   {
/*   47:  92 */     eleminateRedundent(psuedoVarRecipient, this.m_paths);
/*   48:     */   }
/*   49:     */   
/*   50:     */   public void eleminateRedundentGlobals(StylesheetRoot stylesheet)
/*   51:     */   {
/*   52: 105 */     eleminateRedundent(stylesheet, this.m_absPaths);
/*   53:     */   }
/*   54:     */   
/*   55:     */   protected void eleminateRedundent(ElemTemplateElement psuedoVarRecipient, Vector paths)
/*   56:     */   {
/*   57: 123 */     int n = paths.size();
/*   58: 124 */     int numPathsEliminated = 0;
/*   59: 125 */     int numUniquePathsEliminated = 0;
/*   60: 126 */     for (int i = 0; i < n; i++)
/*   61:     */     {
/*   62: 128 */       ExpressionOwner owner = (ExpressionOwner)paths.elementAt(i);
/*   63: 129 */       if (null != owner)
/*   64:     */       {
/*   65: 131 */         int found = findAndEliminateRedundant(i + 1, i, owner, psuedoVarRecipient, paths);
/*   66: 132 */         if (found > 0) {
/*   67: 133 */           numUniquePathsEliminated++;
/*   68:     */         }
/*   69: 134 */         numPathsEliminated += found;
/*   70:     */       }
/*   71:     */     }
/*   72: 138 */     eleminateSharedPartialPaths(psuedoVarRecipient, paths);
/*   73:     */   }
/*   74:     */   
/*   75:     */   protected void eleminateSharedPartialPaths(ElemTemplateElement psuedoVarRecipient, Vector paths)
/*   76:     */   {
/*   77: 154 */     MultistepExprHolder list = createMultistepExprList(paths);
/*   78: 155 */     if (null != list)
/*   79:     */     {
/*   80: 160 */       boolean isGlobal = paths == this.m_absPaths;
/*   81:     */       
/*   82:     */ 
/*   83:     */ 
/*   84: 164 */       int longestStepsCount = list.m_stepCount;
/*   85: 165 */       for (int i = longestStepsCount - 1; i >= 1; i--)
/*   86:     */       {
/*   87: 167 */         MultistepExprHolder next = list;
/*   88: 168 */         while (null != next)
/*   89:     */         {
/*   90: 170 */           if (next.m_stepCount < i) {
/*   91:     */             break;
/*   92:     */           }
/*   93: 172 */           list = matchAndEliminatePartialPaths(next, list, isGlobal, i, psuedoVarRecipient);
/*   94: 173 */           next = next.m_next;
/*   95:     */         }
/*   96:     */       }
/*   97:     */     }
/*   98:     */   }
/*   99:     */   
/*  100:     */   protected MultistepExprHolder matchAndEliminatePartialPaths(MultistepExprHolder testee, MultistepExprHolder head, boolean isGlobal, int lengthToTest, ElemTemplateElement varScope)
/*  101:     */   {
/*  102: 192 */     if (null == testee.m_exprOwner) {
/*  103: 193 */       return head;
/*  104:     */     }
/*  105: 196 */     WalkingIterator iter1 = (WalkingIterator)testee.m_exprOwner.getExpression();
/*  106: 197 */     if (partialIsVariable(testee, lengthToTest)) {
/*  107: 198 */       return head;
/*  108:     */     }
/*  109: 199 */     MultistepExprHolder matchedPaths = null;
/*  110: 200 */     MultistepExprHolder matchedPathsTail = null;
/*  111: 201 */     MultistepExprHolder meh = head;
/*  112: 202 */     while (null != meh)
/*  113:     */     {
/*  114: 204 */       if ((meh != testee) && (null != meh.m_exprOwner))
/*  115:     */       {
/*  116: 206 */         WalkingIterator iter2 = (WalkingIterator)meh.m_exprOwner.getExpression();
/*  117: 207 */         if (stepsEqual(iter1, iter2, lengthToTest))
/*  118:     */         {
/*  119: 209 */           if (null == matchedPaths)
/*  120:     */           {
/*  121:     */             try
/*  122:     */             {
/*  123: 213 */               matchedPaths = (MultistepExprHolder)testee.clone();
/*  124: 214 */               testee.m_exprOwner = null;
/*  125:     */             }
/*  126:     */             catch (CloneNotSupportedException cnse) {}
/*  127: 217 */             matchedPathsTail = matchedPaths;
/*  128: 218 */             matchedPathsTail.m_next = null;
/*  129:     */           }
/*  130:     */           try
/*  131:     */           {
/*  132: 223 */             matchedPathsTail.m_next = ((MultistepExprHolder)meh.clone());
/*  133: 224 */             meh.m_exprOwner = null;
/*  134:     */           }
/*  135:     */           catch (CloneNotSupportedException cnse) {}
/*  136: 227 */           matchedPathsTail = matchedPathsTail.m_next;
/*  137: 228 */           matchedPathsTail.m_next = null;
/*  138:     */         }
/*  139:     */       }
/*  140: 231 */       meh = meh.m_next;
/*  141:     */     }
/*  142: 234 */     int matchCount = 0;
/*  143: 235 */     if (null != matchedPaths)
/*  144:     */     {
/*  145: 237 */       ElemTemplateElement root = isGlobal ? varScope : findCommonAncestor(matchedPaths);
/*  146: 238 */       WalkingIterator sharedIter = (WalkingIterator)matchedPaths.m_exprOwner.getExpression();
/*  147: 239 */       WalkingIterator newIter = createIteratorFromSteps(sharedIter, lengthToTest);
/*  148: 240 */       ElemVariable var = createPseudoVarDecl(root, newIter, isGlobal);
/*  149: 243 */       while (null != matchedPaths)
/*  150:     */       {
/*  151: 245 */         ExpressionOwner owner = matchedPaths.m_exprOwner;
/*  152: 246 */         WalkingIterator iter = (WalkingIterator)owner.getExpression();
/*  153:     */         
/*  154:     */ 
/*  155:     */ 
/*  156:     */ 
/*  157: 251 */         LocPathIterator newIter2 = changePartToRef(var.getName(), iter, lengthToTest, isGlobal);
/*  158:     */         
/*  159: 253 */         owner.setExpression(newIter2);
/*  160:     */         
/*  161: 255 */         matchedPaths = matchedPaths.m_next;
/*  162:     */       }
/*  163:     */     }
/*  164: 261 */     return head;
/*  165:     */   }
/*  166:     */   
/*  167:     */   boolean partialIsVariable(MultistepExprHolder testee, int lengthToTest)
/*  168:     */   {
/*  169: 270 */     if (1 == lengthToTest)
/*  170:     */     {
/*  171: 272 */       WalkingIterator wi = (WalkingIterator)testee.m_exprOwner.getExpression();
/*  172: 273 */       if ((wi.getFirstWalker() instanceof FilterExprWalker)) {
/*  173: 274 */         return true;
/*  174:     */       }
/*  175:     */     }
/*  176: 276 */     return false;
/*  177:     */   }
/*  178:     */   
/*  179:     */   protected void diagnoseLineNumber(Expression expr)
/*  180:     */   {
/*  181: 284 */     ElemTemplateElement e = getElemFromExpression(expr);
/*  182: 285 */     System.err.println("   " + e.getSystemId() + " Line " + e.getLineNumber());
/*  183:     */   }
/*  184:     */   
/*  185:     */   protected ElemTemplateElement findCommonAncestor(MultistepExprHolder head)
/*  186:     */   {
/*  187: 295 */     int numExprs = head.getLength();
/*  188:     */     
/*  189:     */ 
/*  190:     */ 
/*  191: 299 */     ElemTemplateElement[] elems = new ElemTemplateElement[numExprs];
/*  192: 300 */     int[] ancestorCounts = new int[numExprs];
/*  193:     */     
/*  194:     */ 
/*  195:     */ 
/*  196: 304 */     MultistepExprHolder next = head;
/*  197: 305 */     int shortestAncestorCount = 10000;
/*  198: 306 */     for (int i = 0; i < numExprs; i++)
/*  199:     */     {
/*  200: 308 */       ElemTemplateElement elem = getElemFromExpression(next.m_exprOwner.getExpression());
/*  201:     */       
/*  202: 310 */       elems[i] = elem;
/*  203: 311 */       int numAncestors = countAncestors(elem);
/*  204: 312 */       ancestorCounts[i] = numAncestors;
/*  205: 313 */       if (numAncestors < shortestAncestorCount) {
/*  206: 315 */         shortestAncestorCount = numAncestors;
/*  207:     */       }
/*  208: 317 */       next = next.m_next;
/*  209:     */     }
/*  210: 321 */     for (int i = 0; i < numExprs; i++) {
/*  211: 323 */       if (ancestorCounts[i] > shortestAncestorCount)
/*  212:     */       {
/*  213: 325 */         int numStepCorrection = ancestorCounts[i] - shortestAncestorCount;
/*  214: 326 */         for (int j = 0; j < numStepCorrection; j++) {
/*  215: 328 */           elems[i] = elems[i].getParentElem();
/*  216:     */         }
/*  217:     */       }
/*  218:     */     }
/*  219: 335 */     ElemTemplateElement first = null;
/*  220:     */     int i;
/*  221: 336 */     for (; shortestAncestorCount-- >= 0; i < numExprs)
/*  222:     */     {
/*  223: 338 */       boolean areEqual = true;
/*  224: 339 */       first = elems[0];
/*  225: 340 */       for (int i = 1; i < numExprs; i++) {
/*  226: 342 */         if (first != elems[i])
/*  227:     */         {
/*  228: 344 */           areEqual = false;
/*  229: 345 */           break;
/*  230:     */         }
/*  231:     */       }
/*  232: 350 */       if ((areEqual) && (isNotSameAsOwner(head, first)) && (first.canAcceptVariables())) {
/*  233: 357 */         return first;
/*  234:     */       }
/*  235: 360 */       i = 0; continue;
/*  236:     */       
/*  237: 362 */       elems[i] = elems[i].getParentElem();i++;
/*  238:     */     }
/*  239: 366 */     assertion(false, "Could not find common ancestor!!!");
/*  240: 367 */     return null;
/*  241:     */   }
/*  242:     */   
/*  243:     */   protected boolean isNotSameAsOwner(MultistepExprHolder head, ElemTemplateElement ete)
/*  244:     */   {
/*  245: 384 */     MultistepExprHolder next = head;
/*  246: 385 */     while (null != next)
/*  247:     */     {
/*  248: 387 */       ElemTemplateElement elemOwner = getElemFromExpression(next.m_exprOwner.getExpression());
/*  249: 388 */       if (elemOwner == ete) {
/*  250: 389 */         return false;
/*  251:     */       }
/*  252: 390 */       next = next.m_next;
/*  253:     */     }
/*  254: 392 */     return true;
/*  255:     */   }
/*  256:     */   
/*  257:     */   protected int countAncestors(ElemTemplateElement elem)
/*  258:     */   {
/*  259: 403 */     int count = 0;
/*  260: 404 */     while (null != elem)
/*  261:     */     {
/*  262: 406 */       count++;
/*  263: 407 */       elem = elem.getParentElem();
/*  264:     */     }
/*  265: 409 */     return count;
/*  266:     */   }
/*  267:     */   
/*  268:     */   protected void diagnoseMultistepList(int matchCount, int lengthToTest, boolean isGlobal)
/*  269:     */   {
/*  270: 420 */     if (matchCount > 0)
/*  271:     */     {
/*  272: 422 */       System.err.print("Found multistep matches: " + matchCount + ", " + lengthToTest + " length");
/*  273: 424 */       if (isGlobal) {
/*  274: 425 */         System.err.println(" (global)");
/*  275:     */       } else {
/*  276: 427 */         System.err.println();
/*  277:     */       }
/*  278:     */     }
/*  279:     */   }
/*  280:     */   
/*  281:     */   protected LocPathIterator changePartToRef(QName uniquePseudoVarName, WalkingIterator wi, int numSteps, boolean isGlobal)
/*  282:     */   {
/*  283: 442 */     Variable var = new Variable();
/*  284: 443 */     var.setQName(uniquePseudoVarName);
/*  285: 444 */     var.setIsGlobal(isGlobal);
/*  286: 445 */     if (isGlobal)
/*  287:     */     {
/*  288: 446 */       ElemTemplateElement elem = getElemFromExpression(wi);
/*  289: 447 */       StylesheetRoot root = elem.getStylesheetRoot();
/*  290: 448 */       Vector vars = root.getVariablesAndParamsComposed();
/*  291: 449 */       var.setIndex(vars.size() - 1);
/*  292:     */     }
/*  293: 453 */     AxesWalker walker = wi.getFirstWalker();
/*  294: 454 */     for (int i = 0; i < numSteps; i++)
/*  295:     */     {
/*  296: 456 */       assertion(null != walker, "Walker should not be null!");
/*  297: 457 */       walker = walker.getNextWalker();
/*  298:     */     }
/*  299: 460 */     if (null != walker)
/*  300:     */     {
/*  301: 463 */       FilterExprWalker few = new FilterExprWalker(wi);
/*  302: 464 */       few.setInnerExpression(var);
/*  303: 465 */       few.exprSetParent(wi);
/*  304: 466 */       few.setNextWalker(walker);
/*  305: 467 */       walker.setPrevWalker(few);
/*  306: 468 */       wi.setFirstWalker(few);
/*  307: 469 */       return wi;
/*  308:     */     }
/*  309: 473 */     FilterExprIteratorSimple feis = new FilterExprIteratorSimple(var);
/*  310: 474 */     feis.exprSetParent(wi.exprGetParent());
/*  311: 475 */     return feis;
/*  312:     */   }
/*  313:     */   
/*  314:     */   protected WalkingIterator createIteratorFromSteps(WalkingIterator wi, int numSteps)
/*  315:     */   {
/*  316: 489 */     WalkingIterator newIter = new WalkingIterator(wi.getPrefixResolver());
/*  317:     */     try
/*  318:     */     {
/*  319: 492 */       AxesWalker walker = (AxesWalker)wi.getFirstWalker().clone();
/*  320: 493 */       newIter.setFirstWalker(walker);
/*  321: 494 */       walker.setLocPathIterator(newIter);
/*  322: 495 */       for (int i = 1; i < numSteps; i++)
/*  323:     */       {
/*  324: 497 */         AxesWalker next = (AxesWalker)walker.getNextWalker().clone();
/*  325: 498 */         walker.setNextWalker(next);
/*  326: 499 */         next.setLocPathIterator(newIter);
/*  327: 500 */         walker = next;
/*  328:     */       }
/*  329: 502 */       walker.setNextWalker(null);
/*  330:     */     }
/*  331:     */     catch (CloneNotSupportedException cnse)
/*  332:     */     {
/*  333: 506 */       throw new WrappedRuntimeException(cnse);
/*  334:     */     }
/*  335: 508 */     return newIter;
/*  336:     */   }
/*  337:     */   
/*  338:     */   protected boolean stepsEqual(WalkingIterator iter1, WalkingIterator iter2, int numSteps)
/*  339:     */   {
/*  340: 523 */     AxesWalker aw1 = iter1.getFirstWalker();
/*  341: 524 */     AxesWalker aw2 = iter2.getFirstWalker();
/*  342: 526 */     for (int i = 0; i < numSteps; i++)
/*  343:     */     {
/*  344: 528 */       if ((null == aw1) || (null == aw2)) {
/*  345: 529 */         return false;
/*  346:     */       }
/*  347: 531 */       if (!aw1.deepEquals(aw2)) {
/*  348: 532 */         return false;
/*  349:     */       }
/*  350: 534 */       aw1 = aw1.getNextWalker();
/*  351: 535 */       aw2 = aw2.getNextWalker();
/*  352:     */     }
/*  353: 538 */     assertion((null != aw1) || (null != aw2), "Total match is incorrect!");
/*  354:     */     
/*  355: 540 */     return true;
/*  356:     */   }
/*  357:     */   
/*  358:     */   protected MultistepExprHolder createMultistepExprList(Vector paths)
/*  359:     */   {
/*  360: 556 */     MultistepExprHolder first = null;
/*  361: 557 */     int n = paths.size();
/*  362: 558 */     for (int i = 0; i < n; i++)
/*  363:     */     {
/*  364: 560 */       ExpressionOwner eo = (ExpressionOwner)paths.elementAt(i);
/*  365: 561 */       if (null != eo)
/*  366:     */       {
/*  367: 565 */         LocPathIterator lpi = (LocPathIterator)eo.getExpression();
/*  368: 566 */         int numPaths = countSteps(lpi);
/*  369: 567 */         if (numPaths > 1) {
/*  370: 569 */           if (null == first) {
/*  371: 570 */             first = new MultistepExprHolder(eo, numPaths, null);
/*  372:     */           } else {
/*  373: 572 */             first = first.addInSortedOrder(eo, numPaths);
/*  374:     */           }
/*  375:     */         }
/*  376:     */       }
/*  377:     */     }
/*  378: 576 */     if ((null == first) || (first.getLength() <= 1)) {
/*  379: 577 */       return null;
/*  380:     */     }
/*  381: 579 */     return first;
/*  382:     */   }
/*  383:     */   
/*  384:     */   protected int findAndEliminateRedundant(int start, int firstOccuranceIndex, ExpressionOwner firstOccuranceOwner, ElemTemplateElement psuedoVarRecipient, Vector paths)
/*  385:     */     throws DOMException
/*  386:     */   {
/*  387: 602 */     MultistepExprHolder head = null;
/*  388: 603 */     MultistepExprHolder tail = null;
/*  389: 604 */     int numPathsFound = 0;
/*  390: 605 */     int n = paths.size();
/*  391:     */     
/*  392: 607 */     Expression expr1 = firstOccuranceOwner.getExpression();
/*  393:     */     
/*  394:     */ 
/*  395: 610 */     boolean isGlobal = paths == this.m_absPaths;
/*  396: 611 */     LocPathIterator lpi = (LocPathIterator)expr1;
/*  397: 612 */     int stepCount = countSteps(lpi);
/*  398: 613 */     for (int j = start; j < n; j++)
/*  399:     */     {
/*  400: 615 */       ExpressionOwner owner2 = (ExpressionOwner)paths.elementAt(j);
/*  401: 616 */       if (null != owner2)
/*  402:     */       {
/*  403: 618 */         Expression expr2 = owner2.getExpression();
/*  404: 619 */         boolean isEqual = expr2.deepEquals(lpi);
/*  405: 620 */         if (isEqual)
/*  406:     */         {
/*  407: 622 */           LocPathIterator lpi2 = (LocPathIterator)expr2;
/*  408: 623 */           if (null == head)
/*  409:     */           {
/*  410: 625 */             head = new MultistepExprHolder(firstOccuranceOwner, stepCount, null);
/*  411: 626 */             tail = head;
/*  412: 627 */             numPathsFound++;
/*  413:     */           }
/*  414: 629 */           tail.m_next = new MultistepExprHolder(owner2, stepCount, null);
/*  415: 630 */           tail = tail.m_next;
/*  416:     */           
/*  417:     */ 
/*  418: 633 */           paths.setElementAt(null, j);
/*  419:     */           
/*  420:     */ 
/*  421: 636 */           numPathsFound++;
/*  422:     */         }
/*  423:     */       }
/*  424:     */     }
/*  425: 642 */     if ((0 == numPathsFound) && (isGlobal))
/*  426:     */     {
/*  427: 644 */       head = new MultistepExprHolder(firstOccuranceOwner, stepCount, null);
/*  428: 645 */       numPathsFound++;
/*  429:     */     }
/*  430: 648 */     if (null != head)
/*  431:     */     {
/*  432: 650 */       ElemTemplateElement root = isGlobal ? psuedoVarRecipient : findCommonAncestor(head);
/*  433: 651 */       LocPathIterator sharedIter = (LocPathIterator)head.m_exprOwner.getExpression();
/*  434: 652 */       ElemVariable var = createPseudoVarDecl(root, sharedIter, isGlobal);
/*  435:     */       
/*  436:     */ 
/*  437: 655 */       QName uniquePseudoVarName = var.getName();
/*  438: 656 */       while (null != head)
/*  439:     */       {
/*  440: 658 */         ExpressionOwner owner = head.m_exprOwner;
/*  441:     */         
/*  442:     */ 
/*  443: 661 */         changeToVarRef(uniquePseudoVarName, owner, paths, root);
/*  444: 662 */         head = head.m_next;
/*  445:     */       }
/*  446: 666 */       paths.setElementAt(var.getSelect(), firstOccuranceIndex);
/*  447:     */     }
/*  448: 669 */     return numPathsFound;
/*  449:     */   }
/*  450:     */   
/*  451:     */   protected int oldFindAndEliminateRedundant(int start, int firstOccuranceIndex, ExpressionOwner firstOccuranceOwner, ElemTemplateElement psuedoVarRecipient, Vector paths)
/*  452:     */     throws DOMException
/*  453:     */   {
/*  454: 681 */     QName uniquePseudoVarName = null;
/*  455: 682 */     boolean foundFirst = false;
/*  456: 683 */     int numPathsFound = 0;
/*  457: 684 */     int n = paths.size();
/*  458: 685 */     Expression expr1 = firstOccuranceOwner.getExpression();
/*  459:     */     
/*  460:     */ 
/*  461: 688 */     boolean isGlobal = paths == this.m_absPaths;
/*  462: 689 */     LocPathIterator lpi = (LocPathIterator)expr1;
/*  463: 690 */     for (int j = start; j < n; j++)
/*  464:     */     {
/*  465: 692 */       ExpressionOwner owner2 = (ExpressionOwner)paths.elementAt(j);
/*  466: 693 */       if (null != owner2)
/*  467:     */       {
/*  468: 695 */         Expression expr2 = owner2.getExpression();
/*  469: 696 */         boolean isEqual = expr2.deepEquals(lpi);
/*  470: 697 */         if (isEqual)
/*  471:     */         {
/*  472: 699 */           LocPathIterator lpi2 = (LocPathIterator)expr2;
/*  473: 700 */           if (!foundFirst)
/*  474:     */           {
/*  475: 702 */             foundFirst = true;
/*  476:     */             
/*  477:     */ 
/*  478:     */ 
/*  479: 706 */             ElemVariable var = createPseudoVarDecl(psuedoVarRecipient, lpi, isGlobal);
/*  480: 707 */             if (null == var) {
/*  481: 708 */               return 0;
/*  482:     */             }
/*  483: 709 */             uniquePseudoVarName = var.getName();
/*  484:     */             
/*  485: 711 */             changeToVarRef(uniquePseudoVarName, firstOccuranceOwner, paths, psuedoVarRecipient);
/*  486:     */             
/*  487:     */ 
/*  488:     */ 
/*  489:     */ 
/*  490: 716 */             paths.setElementAt(var.getSelect(), firstOccuranceIndex);
/*  491: 717 */             numPathsFound++;
/*  492:     */           }
/*  493: 720 */           changeToVarRef(uniquePseudoVarName, owner2, paths, psuedoVarRecipient);
/*  494:     */           
/*  495:     */ 
/*  496: 723 */           paths.setElementAt(null, j);
/*  497:     */           
/*  498:     */ 
/*  499: 726 */           numPathsFound++;
/*  500:     */         }
/*  501:     */       }
/*  502:     */     }
/*  503: 732 */     if ((0 == numPathsFound) && (paths == this.m_absPaths))
/*  504:     */     {
/*  505: 734 */       ElemVariable var = createPseudoVarDecl(psuedoVarRecipient, lpi, true);
/*  506: 735 */       if (null == var) {
/*  507: 736 */         return 0;
/*  508:     */       }
/*  509: 737 */       uniquePseudoVarName = var.getName();
/*  510: 738 */       changeToVarRef(uniquePseudoVarName, firstOccuranceOwner, paths, psuedoVarRecipient);
/*  511: 739 */       paths.setElementAt(var.getSelect(), firstOccuranceIndex);
/*  512: 740 */       numPathsFound++;
/*  513:     */     }
/*  514: 742 */     return numPathsFound;
/*  515:     */   }
/*  516:     */   
/*  517:     */   protected int countSteps(LocPathIterator lpi)
/*  518:     */   {
/*  519: 753 */     if ((lpi instanceof WalkingIterator))
/*  520:     */     {
/*  521: 755 */       WalkingIterator wi = (WalkingIterator)lpi;
/*  522: 756 */       AxesWalker aw = wi.getFirstWalker();
/*  523: 757 */       int count = 0;
/*  524: 758 */       while (null != aw)
/*  525:     */       {
/*  526: 760 */         count++;
/*  527: 761 */         aw = aw.getNextWalker();
/*  528:     */       }
/*  529: 763 */       return count;
/*  530:     */     }
/*  531: 766 */     return 1;
/*  532:     */   }
/*  533:     */   
/*  534:     */   protected void changeToVarRef(QName varName, ExpressionOwner owner, Vector paths, ElemTemplateElement psuedoVarRecipient)
/*  535:     */   {
/*  536: 788 */     Variable varRef = paths == this.m_absPaths ? new VariableSafeAbsRef() : new Variable();
/*  537: 789 */     varRef.setQName(varName);
/*  538: 790 */     if (paths == this.m_absPaths)
/*  539:     */     {
/*  540: 792 */       StylesheetRoot root = (StylesheetRoot)psuedoVarRecipient;
/*  541: 793 */       Vector globalVars = root.getVariablesAndParamsComposed();
/*  542:     */       
/*  543:     */ 
/*  544: 796 */       varRef.setIndex(globalVars.size() - 1);
/*  545: 797 */       varRef.setIsGlobal(true);
/*  546:     */     }
/*  547: 799 */     owner.setExpression(varRef);
/*  548:     */   }
/*  549:     */   
/*  550:     */   private static synchronized int getPseudoVarID()
/*  551:     */   {
/*  552: 803 */     return m_uniquePseudoVarID++;
/*  553:     */   }
/*  554:     */   
/*  555:     */   protected ElemVariable createPseudoVarDecl(ElemTemplateElement psuedoVarRecipient, LocPathIterator lpi, boolean isGlobal)
/*  556:     */     throws DOMException
/*  557:     */   {
/*  558: 821 */     QName uniquePseudoVarName = new QName("http://xml.apache.org/xalan/psuedovar", "#" + getPseudoVarID());
/*  559: 823 */     if (isGlobal) {
/*  560: 825 */       return createGlobalPseudoVarDecl(uniquePseudoVarName, (StylesheetRoot)psuedoVarRecipient, lpi);
/*  561:     */     }
/*  562: 829 */     return createLocalPseudoVarDecl(uniquePseudoVarName, psuedoVarRecipient, lpi);
/*  563:     */   }
/*  564:     */   
/*  565:     */   protected ElemVariable createGlobalPseudoVarDecl(QName uniquePseudoVarName, StylesheetRoot stylesheetRoot, LocPathIterator lpi)
/*  566:     */     throws DOMException
/*  567:     */   {
/*  568: 848 */     ElemVariable psuedoVar = new ElemVariable();
/*  569: 849 */     psuedoVar.setIsTopLevel(true);
/*  570: 850 */     XPath xpath = new XPath(lpi);
/*  571: 851 */     psuedoVar.setSelect(xpath);
/*  572: 852 */     psuedoVar.setName(uniquePseudoVarName);
/*  573:     */     
/*  574: 854 */     Vector globalVars = stylesheetRoot.getVariablesAndParamsComposed();
/*  575: 855 */     psuedoVar.setIndex(globalVars.size());
/*  576: 856 */     globalVars.addElement(psuedoVar);
/*  577: 857 */     return psuedoVar;
/*  578:     */   }
/*  579:     */   
/*  580:     */   protected ElemVariable createLocalPseudoVarDecl(QName uniquePseudoVarName, ElemTemplateElement psuedoVarRecipient, LocPathIterator lpi)
/*  581:     */     throws DOMException
/*  582:     */   {
/*  583: 879 */     ElemVariable psuedoVar = new ElemVariablePsuedo();
/*  584:     */     
/*  585: 881 */     XPath xpath = new XPath(lpi);
/*  586: 882 */     psuedoVar.setSelect(xpath);
/*  587: 883 */     psuedoVar.setName(uniquePseudoVarName);
/*  588:     */     
/*  589: 885 */     ElemVariable var = addVarDeclToElem(psuedoVarRecipient, lpi, psuedoVar);
/*  590:     */     
/*  591: 887 */     lpi.exprSetParent(var);
/*  592:     */     
/*  593: 889 */     return var;
/*  594:     */   }
/*  595:     */   
/*  596:     */   protected ElemVariable addVarDeclToElem(ElemTemplateElement psuedoVarRecipient, LocPathIterator lpi, ElemVariable psuedoVar)
/*  597:     */     throws DOMException
/*  598:     */   {
/*  599: 902 */     ElemTemplateElement ete = psuedoVarRecipient.getFirstChildElem();
/*  600:     */     
/*  601: 904 */     lpi.callVisitors(null, this.m_varNameCollector);
/*  602: 910 */     if (this.m_varNameCollector.getVarCount() > 0)
/*  603:     */     {
/*  604: 912 */       ElemTemplateElement baseElem = getElemFromExpression(lpi);
/*  605: 913 */       ElemVariable varElem = getPrevVariableElem(baseElem);
/*  606: 914 */       while (null != varElem)
/*  607:     */       {
/*  608: 916 */         if (this.m_varNameCollector.doesOccur(varElem.getName()))
/*  609:     */         {
/*  610: 918 */           psuedoVarRecipient = varElem.getParentElem();
/*  611: 919 */           ete = varElem.getNextSiblingElem();
/*  612: 920 */           break;
/*  613:     */         }
/*  614: 922 */         varElem = getPrevVariableElem(varElem);
/*  615:     */       }
/*  616:     */     }
/*  617: 926 */     if ((null != ete) && (41 == ete.getXSLToken()))
/*  618:     */     {
/*  619: 929 */       if (isParam(lpi)) {
/*  620: 930 */         return null;
/*  621:     */       }
/*  622: 932 */       while (null != ete)
/*  623:     */       {
/*  624: 934 */         ete = ete.getNextSiblingElem();
/*  625: 935 */         if ((null != ete) && (41 != ete.getXSLToken())) {
/*  626:     */           break;
/*  627:     */         }
/*  628:     */       }
/*  629:     */     }
/*  630: 939 */     psuedoVarRecipient.insertBefore(psuedoVar, ete);
/*  631: 940 */     this.m_varNameCollector.reset();
/*  632: 941 */     return psuedoVar;
/*  633:     */   }
/*  634:     */   
/*  635:     */   protected boolean isParam(ExpressionNode expr)
/*  636:     */   {
/*  637: 949 */     while (null != expr)
/*  638:     */     {
/*  639: 951 */       if ((expr instanceof ElemTemplateElement)) {
/*  640:     */         break;
/*  641:     */       }
/*  642: 953 */       expr = expr.exprGetParent();
/*  643:     */     }
/*  644: 955 */     if (null != expr)
/*  645:     */     {
/*  646: 957 */       ElemTemplateElement ete = (ElemTemplateElement)expr;
/*  647: 958 */       while (null != ete)
/*  648:     */       {
/*  649: 960 */         int type = ete.getXSLToken();
/*  650: 961 */         switch (type)
/*  651:     */         {
/*  652:     */         case 41: 
/*  653: 964 */           return true;
/*  654:     */         case 19: 
/*  655:     */         case 25: 
/*  656: 967 */           return false;
/*  657:     */         }
/*  658: 969 */         ete = ete.getParentElem();
/*  659:     */       }
/*  660:     */     }
/*  661: 972 */     return false;
/*  662:     */   }
/*  663:     */   
/*  664:     */   protected ElemVariable getPrevVariableElem(ElemTemplateElement elem)
/*  665:     */   {
/*  666: 989 */     while (null != (elem = getPrevElementWithinContext(elem)))
/*  667:     */     {
/*  668: 991 */       int type = elem.getXSLToken();
/*  669: 993 */       if ((73 == type) || (41 == type)) {
/*  670: 996 */         return (ElemVariable)elem;
/*  671:     */       }
/*  672:     */     }
/*  673: 999 */     return null;
/*  674:     */   }
/*  675:     */   
/*  676:     */   protected ElemTemplateElement getPrevElementWithinContext(ElemTemplateElement elem)
/*  677:     */   {
/*  678:1012 */     ElemTemplateElement prev = elem.getPreviousSiblingElem();
/*  679:1013 */     if (null == prev) {
/*  680:1014 */       prev = elem.getParentElem();
/*  681:     */     }
/*  682:1015 */     if (null != prev)
/*  683:     */     {
/*  684:1017 */       int type = prev.getXSLToken();
/*  685:1018 */       if ((28 == type) || (19 == type) || (25 == type)) {
/*  686:1022 */         prev = null;
/*  687:     */       }
/*  688:     */     }
/*  689:1025 */     return prev;
/*  690:     */   }
/*  691:     */   
/*  692:     */   protected ElemTemplateElement getElemFromExpression(Expression expr)
/*  693:     */   {
/*  694:1038 */     ExpressionNode parent = expr.exprGetParent();
/*  695:1039 */     while (null != parent)
/*  696:     */     {
/*  697:1041 */       if ((parent instanceof ElemTemplateElement)) {
/*  698:1042 */         return (ElemTemplateElement)parent;
/*  699:     */       }
/*  700:1043 */       parent = parent.exprGetParent();
/*  701:     */     }
/*  702:1045 */     throw new RuntimeException(XSLMessages.createMessage("ER_ASSERT_NO_TEMPLATE_PARENT", null));
/*  703:     */   }
/*  704:     */   
/*  705:     */   public boolean isAbsolute(LocPathIterator path)
/*  706:     */   {
/*  707:1057 */     int analysis = path.getAnalysisBits();
/*  708:1058 */     boolean isAbs = (WalkerFactory.isSet(analysis, 134217728)) || (WalkerFactory.isSet(analysis, 536870912));
/*  709:1060 */     if (isAbs) {
/*  710:1062 */       isAbs = this.m_absPathChecker.checkAbsolute(path);
/*  711:     */     }
/*  712:1064 */     return isAbs;
/*  713:     */   }
/*  714:     */   
/*  715:     */   public boolean visitLocationPath(ExpressionOwner owner, LocPathIterator path)
/*  716:     */   {
/*  717:1079 */     if ((path instanceof SelfIteratorNoPredicate)) {
/*  718:1081 */       return true;
/*  719:     */     }
/*  720:1083 */     if ((path instanceof WalkingIterator))
/*  721:     */     {
/*  722:1085 */       WalkingIterator wi = (WalkingIterator)path;
/*  723:1086 */       AxesWalker aw = wi.getFirstWalker();
/*  724:1087 */       if (((aw instanceof FilterExprWalker)) && (null == aw.getNextWalker()))
/*  725:     */       {
/*  726:1089 */         FilterExprWalker few = (FilterExprWalker)aw;
/*  727:1090 */         Expression exp = few.getInnerExpression();
/*  728:1091 */         if ((exp instanceof Variable)) {
/*  729:1092 */           return true;
/*  730:     */         }
/*  731:     */       }
/*  732:     */     }
/*  733:1096 */     if ((isAbsolute(path)) && (null != this.m_absPaths)) {
/*  734:1100 */       this.m_absPaths.addElement(owner);
/*  735:1102 */     } else if ((this.m_isSameContext) && (null != this.m_paths)) {
/*  736:1106 */       this.m_paths.addElement(owner);
/*  737:     */     }
/*  738:1109 */     return true;
/*  739:     */   }
/*  740:     */   
/*  741:     */   public boolean visitPredicate(ExpressionOwner owner, Expression pred)
/*  742:     */   {
/*  743:1124 */     boolean savedIsSame = this.m_isSameContext;
/*  744:1125 */     this.m_isSameContext = false;
/*  745:     */     
/*  746:     */ 
/*  747:1128 */     pred.callVisitors(owner, this);
/*  748:     */     
/*  749:1130 */     this.m_isSameContext = savedIsSame;
/*  750:     */     
/*  751:     */ 
/*  752:     */ 
/*  753:1134 */     return false;
/*  754:     */   }
/*  755:     */   
/*  756:     */   public boolean visitTopLevelInstruction(ElemTemplateElement elem)
/*  757:     */   {
/*  758:1145 */     int type = elem.getXSLToken();
/*  759:1146 */     switch (type)
/*  760:     */     {
/*  761:     */     case 19: 
/*  762:1149 */       return visitInstruction(elem);
/*  763:     */     }
/*  764:1151 */     return true;
/*  765:     */   }
/*  766:     */   
/*  767:     */   public boolean visitInstruction(ElemTemplateElement elem)
/*  768:     */   {
/*  769:1165 */     int type = elem.getXSLToken();
/*  770:1166 */     switch (type)
/*  771:     */     {
/*  772:     */     case 17: 
/*  773:     */     case 19: 
/*  774:     */     case 28: 
/*  775:1174 */       if (type == 28)
/*  776:     */       {
/*  777:1176 */         ElemForEach efe = (ElemForEach)elem;
/*  778:     */         
/*  779:1178 */         Expression select = efe.getSelect();
/*  780:1179 */         select.callVisitors(efe, this);
/*  781:     */       }
/*  782:1182 */       Vector savedPaths = this.m_paths;
/*  783:1183 */       this.m_paths = new Vector();
/*  784:     */       
/*  785:     */ 
/*  786:     */ 
/*  787:     */ 
/*  788:1188 */       elem.callChildVisitors(this, false);
/*  789:1189 */       eleminateRedundentLocals(elem);
/*  790:     */       
/*  791:1191 */       this.m_paths = savedPaths;
/*  792:     */       
/*  793:     */ 
/*  794:1194 */       return false;
/*  795:     */     case 35: 
/*  796:     */     case 64: 
/*  797:1200 */       boolean savedIsSame = this.m_isSameContext;
/*  798:1201 */       this.m_isSameContext = false;
/*  799:1202 */       elem.callChildVisitors(this);
/*  800:1203 */       this.m_isSameContext = savedIsSame;
/*  801:1204 */       return false;
/*  802:     */     }
/*  803:1207 */     return true;
/*  804:     */   }
/*  805:     */   
/*  806:     */   protected void diagnoseNumPaths(Vector paths, int numPathsEliminated, int numUniquePathsEliminated)
/*  807:     */   {
/*  808:1219 */     if (numPathsEliminated > 0) {
/*  809:1221 */       if (paths == this.m_paths)
/*  810:     */       {
/*  811:1223 */         System.err.println("Eliminated " + numPathsEliminated + " total paths!");
/*  812:1224 */         System.err.println("Consolodated " + numUniquePathsEliminated + " redundent paths!");
/*  813:     */       }
/*  814:     */       else
/*  815:     */       {
/*  816:1229 */         System.err.println("Eliminated " + numPathsEliminated + " total global paths!");
/*  817:1230 */         System.err.println("Consolodated " + numUniquePathsEliminated + " redundent global paths!");
/*  818:     */       }
/*  819:     */     }
/*  820:     */   }
/*  821:     */   
/*  822:     */   private final void assertIsLocPathIterator(Expression expr1, ExpressionOwner eo)
/*  823:     */     throws RuntimeException
/*  824:     */   {
/*  825:1244 */     if (!(expr1 instanceof LocPathIterator))
/*  826:     */     {
/*  827:     */       String errMsg;
/*  828:1247 */       if ((expr1 instanceof Variable)) {
/*  829:1249 */         errMsg = "Programmer's assertion: expr1 not an iterator: " + ((Variable)expr1).getQName();
/*  830:     */       } else {
/*  831:1254 */         errMsg = "Programmer's assertion: expr1 not an iterator: " + expr1.getClass().getName();
/*  832:     */       }
/*  833:1257 */       throw new RuntimeException(errMsg + ", " + eo.getClass().getName() + " " + expr1.exprGetParent());
/*  834:     */     }
/*  835:     */   }
/*  836:     */   
/*  837:     */   private static void validateNewAddition(Vector paths, ExpressionOwner owner, LocPathIterator path)
/*  838:     */     throws RuntimeException
/*  839:     */   {
/*  840:1272 */     assertion(owner.getExpression() == path, "owner.getExpression() != path!!!");
/*  841:1273 */     int n = paths.size();
/*  842:1275 */     for (int i = 0; i < n; i++)
/*  843:     */     {
/*  844:1277 */       ExpressionOwner ew = (ExpressionOwner)paths.elementAt(i);
/*  845:1278 */       assertion(ew != owner, "duplicate owner on the list!!!");
/*  846:1279 */       assertion(ew.getExpression() != path, "duplicate expression on the list!!!");
/*  847:     */     }
/*  848:     */   }
/*  849:     */   
/*  850:     */   protected static void assertion(boolean b, String msg)
/*  851:     */   {
/*  852:1288 */     if (!b) {
/*  853:1290 */       throw new RuntimeException(XSLMessages.createMessage("ER_ASSERT_REDUNDENT_EXPR_ELIMINATOR", new Object[] { msg }));
/*  854:     */     }
/*  855:     */   }
/*  856:     */   
/*  857:     */   class MultistepExprHolder
/*  858:     */     implements Cloneable
/*  859:     */   {
/*  860:     */     ExpressionOwner m_exprOwner;
/*  861:     */     final int m_stepCount;
/*  862:     */     MultistepExprHolder m_next;
/*  863:     */     
/*  864:     */     public Object clone()
/*  865:     */       throws CloneNotSupportedException
/*  866:     */     {
/*  867:1311 */       return super.clone();
/*  868:     */     }
/*  869:     */     
/*  870:     */     MultistepExprHolder(ExpressionOwner exprOwner, int stepCount, MultistepExprHolder next)
/*  871:     */     {
/*  872:1323 */       this.m_exprOwner = exprOwner;
/*  873:1324 */       RedundentExprEliminator.assertion(null != this.m_exprOwner, "exprOwner can not be null!");
/*  874:1325 */       this.m_stepCount = stepCount;
/*  875:1326 */       this.m_next = next;
/*  876:     */     }
/*  877:     */     
/*  878:     */     MultistepExprHolder addInSortedOrder(ExpressionOwner exprOwner, int stepCount)
/*  879:     */     {
/*  880:1339 */       MultistepExprHolder first = this;
/*  881:1340 */       MultistepExprHolder next = this;
/*  882:1341 */       MultistepExprHolder prev = null;
/*  883:1342 */       while (null != next)
/*  884:     */       {
/*  885:1344 */         if (stepCount >= next.m_stepCount)
/*  886:     */         {
/*  887:1346 */           MultistepExprHolder newholder = new MultistepExprHolder(RedundentExprEliminator.this, exprOwner, stepCount, next);
/*  888:1347 */           if (null == prev) {
/*  889:1348 */             first = newholder;
/*  890:     */           } else {
/*  891:1350 */             prev.m_next = newholder;
/*  892:     */           }
/*  893:1352 */           return first;
/*  894:     */         }
/*  895:1354 */         prev = next;
/*  896:1355 */         next = next.m_next;
/*  897:     */       }
/*  898:1358 */       prev.m_next = new MultistepExprHolder(RedundentExprEliminator.this, exprOwner, stepCount, null);
/*  899:1359 */       return first;
/*  900:     */     }
/*  901:     */     
/*  902:     */     MultistepExprHolder unlink(MultistepExprHolder itemToRemove)
/*  903:     */     {
/*  904:1374 */       MultistepExprHolder first = this;
/*  905:1375 */       MultistepExprHolder next = this;
/*  906:1376 */       MultistepExprHolder prev = null;
/*  907:1377 */       while (null != next)
/*  908:     */       {
/*  909:1379 */         if (next == itemToRemove)
/*  910:     */         {
/*  911:1381 */           if (null == prev) {
/*  912:1382 */             first = next.m_next;
/*  913:     */           } else {
/*  914:1384 */             prev.m_next = next.m_next;
/*  915:     */           }
/*  916:1386 */           next.m_next = null;
/*  917:     */           
/*  918:1388 */           return first;
/*  919:     */         }
/*  920:1390 */         prev = next;
/*  921:1391 */         next = next.m_next;
/*  922:     */       }
/*  923:1394 */       RedundentExprEliminator.assertion(false, "unlink failed!!!");
/*  924:1395 */       return null;
/*  925:     */     }
/*  926:     */     
/*  927:     */     int getLength()
/*  928:     */     {
/*  929:1403 */       int count = 0;
/*  930:1404 */       MultistepExprHolder next = this;
/*  931:1405 */       while (null != next)
/*  932:     */       {
/*  933:1407 */         count++;
/*  934:1408 */         next = next.m_next;
/*  935:     */       }
/*  936:1410 */       return count;
/*  937:     */     }
/*  938:     */     
/*  939:     */     protected void diagnose()
/*  940:     */     {
/*  941:1418 */       System.err.print("Found multistep iterators: " + getLength() + "  ");
/*  942:1419 */       MultistepExprHolder next = this;
/*  943:1420 */       while (null != next)
/*  944:     */       {
/*  945:1422 */         System.err.print("" + next.m_stepCount);
/*  946:1423 */         next = next.m_next;
/*  947:1424 */         if (null != next) {
/*  948:1425 */           System.err.print(", ");
/*  949:     */         }
/*  950:     */       }
/*  951:1427 */       System.err.println();
/*  952:     */     }
/*  953:     */   }
/*  954:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.templates.RedundentExprEliminator
 * JD-Core Version:    0.7.0.1
 */