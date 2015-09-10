/*    1:     */ package org.apache.xpath.patterns;
/*    2:     */ 
/*    3:     */ import java.util.Vector;
/*    4:     */ import javax.xml.transform.TransformerException;
/*    5:     */ import org.apache.xml.dtm.Axis;
/*    6:     */ import org.apache.xml.dtm.DTM;
/*    7:     */ import org.apache.xml.dtm.DTMAxisTraverser;
/*    8:     */ import org.apache.xpath.Expression;
/*    9:     */ import org.apache.xpath.ExpressionOwner;
/*   10:     */ import org.apache.xpath.XPathContext;
/*   11:     */ import org.apache.xpath.XPathVisitor;
/*   12:     */ import org.apache.xpath.axes.SubContextList;
/*   13:     */ import org.apache.xpath.objects.XObject;
/*   14:     */ 
/*   15:     */ public class StepPattern
/*   16:     */   extends NodeTest
/*   17:     */   implements SubContextList, ExpressionOwner
/*   18:     */ {
/*   19:     */   static final long serialVersionUID = 9071668960168152644L;
/*   20:     */   protected int m_axis;
/*   21:     */   String m_targetString;
/*   22:     */   StepPattern m_relativePathPattern;
/*   23:     */   Expression[] m_predicates;
/*   24:     */   private static final boolean DEBUG_MATCHES = false;
/*   25:     */   
/*   26:     */   public StepPattern(int whatToShow, String namespace, String name, int axis, int axisForPredicate)
/*   27:     */   {
/*   28:  60 */     super(whatToShow, namespace, name);
/*   29:     */     
/*   30:  62 */     this.m_axis = axis;
/*   31:     */   }
/*   32:     */   
/*   33:     */   public StepPattern(int whatToShow, int axis, int axisForPredicate)
/*   34:     */   {
/*   35:  76 */     super(whatToShow);
/*   36:     */     
/*   37:  78 */     this.m_axis = axis;
/*   38:     */   }
/*   39:     */   
/*   40:     */   public void calcTargetString()
/*   41:     */   {
/*   42:  96 */     int whatToShow = getWhatToShow();
/*   43:  98 */     switch (whatToShow)
/*   44:     */     {
/*   45:     */     case 128: 
/*   46: 101 */       this.m_targetString = "#comment";
/*   47: 102 */       break;
/*   48:     */     case 4: 
/*   49:     */     case 8: 
/*   50:     */     case 12: 
/*   51: 106 */       this.m_targetString = "#text";
/*   52: 107 */       break;
/*   53:     */     case -1: 
/*   54: 109 */       this.m_targetString = "*";
/*   55: 110 */       break;
/*   56:     */     case 256: 
/*   57:     */     case 1280: 
/*   58: 113 */       this.m_targetString = "/";
/*   59: 114 */       break;
/*   60:     */     case 1: 
/*   61: 116 */       if ("*" == this.m_name) {
/*   62: 117 */         this.m_targetString = "*";
/*   63:     */       } else {
/*   64: 119 */         this.m_targetString = this.m_name;
/*   65:     */       }
/*   66: 120 */       break;
/*   67:     */     default: 
/*   68: 122 */       this.m_targetString = "*";
/*   69:     */     }
/*   70:     */   }
/*   71:     */   
/*   72:     */   public String getTargetString()
/*   73:     */   {
/*   74: 137 */     return this.m_targetString;
/*   75:     */   }
/*   76:     */   
/*   77:     */   public void fixupVariables(Vector vars, int globalsSize)
/*   78:     */   {
/*   79: 161 */     super.fixupVariables(vars, globalsSize);
/*   80: 163 */     if (null != this.m_predicates) {
/*   81: 165 */       for (int i = 0; i < this.m_predicates.length; i++) {
/*   82: 167 */         this.m_predicates[i].fixupVariables(vars, globalsSize);
/*   83:     */       }
/*   84:     */     }
/*   85: 171 */     if (null != this.m_relativePathPattern) {
/*   86: 173 */       this.m_relativePathPattern.fixupVariables(vars, globalsSize);
/*   87:     */     }
/*   88:     */   }
/*   89:     */   
/*   90:     */   public void setRelativePathPattern(StepPattern expr)
/*   91:     */   {
/*   92: 187 */     this.m_relativePathPattern = expr;
/*   93: 188 */     expr.exprSetParent(this);
/*   94:     */     
/*   95: 190 */     calcScore();
/*   96:     */   }
/*   97:     */   
/*   98:     */   public StepPattern getRelativePathPattern()
/*   99:     */   {
/*  100: 202 */     return this.m_relativePathPattern;
/*  101:     */   }
/*  102:     */   
/*  103:     */   public Expression[] getPredicates()
/*  104:     */   {
/*  105: 220 */     return this.m_predicates;
/*  106:     */   }
/*  107:     */   
/*  108:     */   public boolean canTraverseOutsideSubtree()
/*  109:     */   {
/*  110: 241 */     int n = getPredicateCount();
/*  111: 243 */     for (int i = 0; i < n; i++) {
/*  112: 245 */       if (getPredicate(i).canTraverseOutsideSubtree()) {
/*  113: 246 */         return true;
/*  114:     */       }
/*  115:     */     }
/*  116: 249 */     return false;
/*  117:     */   }
/*  118:     */   
/*  119:     */   public Expression getPredicate(int i)
/*  120:     */   {
/*  121: 262 */     return this.m_predicates[i];
/*  122:     */   }
/*  123:     */   
/*  124:     */   public final int getPredicateCount()
/*  125:     */   {
/*  126: 273 */     return null == this.m_predicates ? 0 : this.m_predicates.length;
/*  127:     */   }
/*  128:     */   
/*  129:     */   public void setPredicates(Expression[] predicates)
/*  130:     */   {
/*  131: 286 */     this.m_predicates = predicates;
/*  132: 287 */     if (null != predicates) {
/*  133: 289 */       for (int i = 0; i < predicates.length; i++) {
/*  134: 291 */         predicates[i].exprSetParent(this);
/*  135:     */       }
/*  136:     */     }
/*  137: 295 */     calcScore();
/*  138:     */   }
/*  139:     */   
/*  140:     */   public void calcScore()
/*  141:     */   {
/*  142: 304 */     if ((getPredicateCount() > 0) || (null != this.m_relativePathPattern)) {
/*  143: 306 */       this.m_score = NodeTest.SCORE_OTHER;
/*  144:     */     } else {
/*  145: 309 */       super.calcScore();
/*  146:     */     }
/*  147: 311 */     if (null == this.m_targetString) {
/*  148: 312 */       calcTargetString();
/*  149:     */     }
/*  150:     */   }
/*  151:     */   
/*  152:     */   public XObject execute(XPathContext xctxt, int currentNode)
/*  153:     */     throws TransformerException
/*  154:     */   {
/*  155: 334 */     DTM dtm = xctxt.getDTM(currentNode);
/*  156: 336 */     if (dtm != null)
/*  157:     */     {
/*  158: 338 */       int expType = dtm.getExpandedTypeID(currentNode);
/*  159:     */       
/*  160: 340 */       return execute(xctxt, currentNode, dtm, expType);
/*  161:     */     }
/*  162: 343 */     return NodeTest.SCORE_NONE;
/*  163:     */   }
/*  164:     */   
/*  165:     */   public XObject execute(XPathContext xctxt)
/*  166:     */     throws TransformerException
/*  167:     */   {
/*  168: 363 */     return execute(xctxt, xctxt.getCurrentNode());
/*  169:     */   }
/*  170:     */   
/*  171:     */   public XObject execute(XPathContext xctxt, int currentNode, DTM dtm, int expType)
/*  172:     */     throws TransformerException
/*  173:     */   {
/*  174: 386 */     if (this.m_whatToShow == 65536)
/*  175:     */     {
/*  176: 388 */       if (null != this.m_relativePathPattern) {
/*  177: 390 */         return this.m_relativePathPattern.execute(xctxt);
/*  178:     */       }
/*  179: 393 */       return NodeTest.SCORE_NONE;
/*  180:     */     }
/*  181: 398 */     XObject score = super.execute(xctxt, currentNode, dtm, expType);
/*  182: 400 */     if (score == NodeTest.SCORE_NONE) {
/*  183: 401 */       return NodeTest.SCORE_NONE;
/*  184:     */     }
/*  185: 403 */     if (getPredicateCount() != 0) {
/*  186: 405 */       if (!executePredicates(xctxt, dtm, currentNode)) {
/*  187: 406 */         return NodeTest.SCORE_NONE;
/*  188:     */       }
/*  189:     */     }
/*  190: 409 */     if (null != this.m_relativePathPattern) {
/*  191: 410 */       return this.m_relativePathPattern.executeRelativePathPattern(xctxt, dtm, currentNode);
/*  192:     */     }
/*  193: 413 */     return score;
/*  194:     */   }
/*  195:     */   
/*  196:     */   private final boolean checkProximityPosition(XPathContext xctxt, int predPos, DTM dtm, int context, int pos)
/*  197:     */   {
/*  198:     */     try
/*  199:     */     {
/*  200: 434 */       DTMAxisTraverser traverser = dtm.getAxisTraverser(12);
/*  201: 437 */       for (int child = traverser.first(context); -1 != child; child = traverser.next(context, child)) {
/*  202:     */         try
/*  203:     */         {
/*  204: 442 */           xctxt.pushCurrentNode(child);
/*  205: 444 */           if (NodeTest.SCORE_NONE != super.execute(xctxt, child))
/*  206:     */           {
/*  207: 446 */             boolean pass = true;
/*  208:     */             int i;
/*  209:     */             try
/*  210:     */             {
/*  211: 450 */               xctxt.pushSubContextList(this);
/*  212: 452 */               for (i = 0; i < predPos; i++)
/*  213:     */               {
/*  214: 454 */                 xctxt.pushPredicatePos(i);
/*  215:     */                 try
/*  216:     */                 {
/*  217: 457 */                   XObject pred = this.m_predicates[i].execute(xctxt);
/*  218:     */                   try
/*  219:     */                   {
/*  220: 461 */                     if (2 == pred.getType()) {
/*  221: 463 */                       throw new Error("Why: Should never have been called");
/*  222:     */                     }
/*  223: 465 */                     if (!pred.boolWithSideEffects())
/*  224:     */                     {
/*  225: 467 */                       pass = false;
/*  226:     */                       
/*  227: 469 */                       break;
/*  228:     */                     }
/*  229:     */                   }
/*  230:     */                   finally
/*  231:     */                   {
/*  232: 474 */                     pred.detach();
/*  233:     */                   }
/*  234:     */                 }
/*  235:     */                 finally
/*  236:     */                 {
/*  237: 479 */                   xctxt.popPredicatePos();
/*  238:     */                 }
/*  239:     */               }
/*  240:     */             }
/*  241:     */             finally
/*  242:     */             {
/*  243: 485 */               xctxt.popSubContextList();
/*  244:     */             }
/*  245: 488 */             if (pass) {
/*  246: 489 */               pos--;
/*  247:     */             }
/*  248: 491 */             if (pos < 1) {
/*  249: 492 */               return 0;
/*  250:     */             }
/*  251:     */           }
/*  252:     */         }
/*  253:     */         finally
/*  254:     */         {
/*  255: 497 */           xctxt.popCurrentNode();
/*  256:     */         }
/*  257:     */       }
/*  258:     */     }
/*  259:     */     catch (TransformerException se)
/*  260:     */     {
/*  261: 505 */       throw new RuntimeException(se.getMessage());
/*  262:     */     }
/*  263: 508 */     return pos == 1;
/*  264:     */   }
/*  265:     */   
/*  266:     */   private final int getProximityPosition(XPathContext xctxt, int predPos, boolean findLast)
/*  267:     */   {
/*  268: 527 */     int pos = 0;
/*  269: 528 */     int context = xctxt.getCurrentNode();
/*  270: 529 */     DTM dtm = xctxt.getDTM(context);
/*  271: 530 */     int parent = dtm.getParent(context);
/*  272:     */     try
/*  273:     */     {
/*  274: 534 */       DTMAxisTraverser traverser = dtm.getAxisTraverser(3);
/*  275: 536 */       for (int child = traverser.first(parent); -1 != child; child = traverser.next(parent, child)) {
/*  276:     */         try
/*  277:     */         {
/*  278: 541 */           xctxt.pushCurrentNode(child);
/*  279: 543 */           if (NodeTest.SCORE_NONE != super.execute(xctxt, child))
/*  280:     */           {
/*  281: 545 */             boolean pass = true;
/*  282:     */             int i;
/*  283:     */             try
/*  284:     */             {
/*  285: 549 */               xctxt.pushSubContextList(this);
/*  286: 551 */               for (i = 0; i < predPos; i++)
/*  287:     */               {
/*  288: 553 */                 xctxt.pushPredicatePos(i);
/*  289:     */                 try
/*  290:     */                 {
/*  291: 556 */                   XObject pred = this.m_predicates[i].execute(xctxt);
/*  292:     */                   try
/*  293:     */                   {
/*  294: 560 */                     if (2 == pred.getType())
/*  295:     */                     {
/*  296: 562 */                       if (pos + 1 != (int)pred.numWithSideEffects())
/*  297:     */                       {
/*  298: 564 */                         pass = false;
/*  299:     */                         
/*  300: 566 */                         break;
/*  301:     */                       }
/*  302:     */                     }
/*  303: 569 */                     else if (!pred.boolWithSideEffects())
/*  304:     */                     {
/*  305: 571 */                       pass = false;
/*  306:     */                       
/*  307: 573 */                       break;
/*  308:     */                     }
/*  309:     */                   }
/*  310:     */                   finally
/*  311:     */                   {
/*  312: 578 */                     pred.detach();
/*  313:     */                   }
/*  314:     */                 }
/*  315:     */                 finally
/*  316:     */                 {
/*  317: 583 */                   xctxt.popPredicatePos();
/*  318:     */                 }
/*  319:     */               }
/*  320:     */             }
/*  321:     */             finally
/*  322:     */             {
/*  323: 589 */               xctxt.popSubContextList();
/*  324:     */             }
/*  325: 592 */             if (pass) {
/*  326: 593 */               pos++;
/*  327:     */             }
/*  328: 595 */             if ((!findLast) && (child == context)) {
/*  329: 597 */               return pos;
/*  330:     */             }
/*  331:     */           }
/*  332:     */         }
/*  333:     */         finally
/*  334:     */         {
/*  335: 603 */           xctxt.popCurrentNode();
/*  336:     */         }
/*  337:     */       }
/*  338:     */     }
/*  339:     */     catch (TransformerException se)
/*  340:     */     {
/*  341: 611 */       throw new RuntimeException(se.getMessage());
/*  342:     */     }
/*  343: 614 */     return pos;
/*  344:     */   }
/*  345:     */   
/*  346:     */   public int getProximityPosition(XPathContext xctxt)
/*  347:     */   {
/*  348: 629 */     return getProximityPosition(xctxt, xctxt.getPredicatePos(), false);
/*  349:     */   }
/*  350:     */   
/*  351:     */   public int getLastPos(XPathContext xctxt)
/*  352:     */   {
/*  353: 645 */     return getProximityPosition(xctxt, xctxt.getPredicatePos(), true);
/*  354:     */   }
/*  355:     */   
/*  356:     */   protected final XObject executeRelativePathPattern(XPathContext xctxt, DTM dtm, int currentNode)
/*  357:     */     throws TransformerException
/*  358:     */   {
/*  359: 669 */     XObject score = NodeTest.SCORE_NONE;
/*  360: 670 */     int context = currentNode;
/*  361:     */     
/*  362:     */ 
/*  363: 673 */     DTMAxisTraverser traverser = dtm.getAxisTraverser(this.m_axis);
/*  364: 675 */     for (int relative = traverser.first(context); -1 != relative; relative = traverser.next(context, relative)) {
/*  365:     */       try
/*  366:     */       {
/*  367: 680 */         xctxt.pushCurrentNode(relative);
/*  368:     */         
/*  369: 682 */         score = execute(xctxt);
/*  370: 684 */         if (score != NodeTest.SCORE_NONE) {
/*  371:     */           break;
/*  372:     */         }
/*  373:     */       }
/*  374:     */       finally
/*  375:     */       {
/*  376: 689 */         xctxt.popCurrentNode();
/*  377:     */       }
/*  378:     */     }
/*  379: 693 */     return score;
/*  380:     */   }
/*  381:     */   
/*  382:     */   protected final boolean executePredicates(XPathContext xctxt, DTM dtm, int currentNode)
/*  383:     */     throws TransformerException
/*  384:     */   {
/*  385: 713 */     boolean result = true;
/*  386: 714 */     boolean positionAlreadySeen = false;
/*  387: 715 */     int n = getPredicateCount();
/*  388:     */     try
/*  389:     */     {
/*  390: 719 */       xctxt.pushSubContextList(this);
/*  391: 721 */       for (int i = 0; i < n; i++)
/*  392:     */       {
/*  393: 723 */         xctxt.pushPredicatePos(i);
/*  394:     */         try
/*  395:     */         {
/*  396: 727 */           XObject pred = this.m_predicates[i].execute(xctxt);
/*  397:     */           try
/*  398:     */           {
/*  399: 731 */             if (2 == pred.getType())
/*  400:     */             {
/*  401: 733 */               int pos = (int)pred.num();
/*  402: 735 */               if (positionAlreadySeen)
/*  403:     */               {
/*  404: 737 */                 result = pos == 1;
/*  405:     */                 
/*  406: 739 */                 break;
/*  407:     */               }
/*  408: 743 */               positionAlreadySeen = true;
/*  409: 745 */               if (!checkProximityPosition(xctxt, i, dtm, currentNode, pos))
/*  410:     */               {
/*  411: 747 */                 result = false;
/*  412:     */                 
/*  413: 749 */                 break;
/*  414:     */               }
/*  415:     */             }
/*  416: 754 */             else if (!pred.boolWithSideEffects())
/*  417:     */             {
/*  418: 756 */               result = false;
/*  419:     */               
/*  420: 758 */               break;
/*  421:     */             }
/*  422:     */           }
/*  423:     */           finally
/*  424:     */           {
/*  425: 763 */             pred.detach();
/*  426:     */           }
/*  427:     */         }
/*  428:     */         finally
/*  429:     */         {
/*  430: 768 */           xctxt.popPredicatePos();
/*  431:     */         }
/*  432:     */       }
/*  433:     */     }
/*  434:     */     finally
/*  435:     */     {
/*  436: 774 */       xctxt.popSubContextList();
/*  437:     */     }
/*  438: 777 */     return result;
/*  439:     */   }
/*  440:     */   
/*  441:     */   public String toString()
/*  442:     */   {
/*  443: 790 */     StringBuffer buf = new StringBuffer();
/*  444: 792 */     for (StepPattern pat = this; pat != null; pat = pat.m_relativePathPattern)
/*  445:     */     {
/*  446: 794 */       if (pat != this) {
/*  447: 795 */         buf.append("/");
/*  448:     */       }
/*  449: 797 */       buf.append(Axis.getNames(pat.m_axis));
/*  450: 798 */       buf.append("::");
/*  451: 800 */       if (20480 == pat.m_whatToShow)
/*  452:     */       {
/*  453: 802 */         buf.append("doc()");
/*  454:     */       }
/*  455: 804 */       else if (65536 == pat.m_whatToShow)
/*  456:     */       {
/*  457: 806 */         buf.append("function()");
/*  458:     */       }
/*  459: 808 */       else if (-1 == pat.m_whatToShow)
/*  460:     */       {
/*  461: 810 */         buf.append("node()");
/*  462:     */       }
/*  463: 812 */       else if (4 == pat.m_whatToShow)
/*  464:     */       {
/*  465: 814 */         buf.append("text()");
/*  466:     */       }
/*  467: 816 */       else if (64 == pat.m_whatToShow)
/*  468:     */       {
/*  469: 818 */         buf.append("processing-instruction(");
/*  470: 820 */         if (null != pat.m_name) {
/*  471: 822 */           buf.append(pat.m_name);
/*  472:     */         }
/*  473: 825 */         buf.append(")");
/*  474:     */       }
/*  475: 827 */       else if (128 == pat.m_whatToShow)
/*  476:     */       {
/*  477: 829 */         buf.append("comment()");
/*  478:     */       }
/*  479: 831 */       else if (null != pat.m_name)
/*  480:     */       {
/*  481: 833 */         if (2 == pat.m_whatToShow) {
/*  482: 835 */           buf.append("@");
/*  483:     */         }
/*  484: 838 */         if (null != pat.m_namespace)
/*  485:     */         {
/*  486: 840 */           buf.append("{");
/*  487: 841 */           buf.append(pat.m_namespace);
/*  488: 842 */           buf.append("}");
/*  489:     */         }
/*  490: 845 */         buf.append(pat.m_name);
/*  491:     */       }
/*  492: 847 */       else if (2 == pat.m_whatToShow)
/*  493:     */       {
/*  494: 849 */         buf.append("@");
/*  495:     */       }
/*  496: 851 */       else if (1280 == pat.m_whatToShow)
/*  497:     */       {
/*  498: 854 */         buf.append("doc-root()");
/*  499:     */       }
/*  500:     */       else
/*  501:     */       {
/*  502: 858 */         buf.append("?" + Integer.toHexString(pat.m_whatToShow));
/*  503:     */       }
/*  504: 861 */       if (null != pat.m_predicates) {
/*  505: 863 */         for (int i = 0; i < pat.m_predicates.length; i++)
/*  506:     */         {
/*  507: 865 */           buf.append("[");
/*  508: 866 */           buf.append(pat.m_predicates[i]);
/*  509: 867 */           buf.append("]");
/*  510:     */         }
/*  511:     */       }
/*  512:     */     }
/*  513: 872 */     return buf.toString();
/*  514:     */   }
/*  515:     */   
/*  516:     */   public double getMatchScore(XPathContext xctxt, int context)
/*  517:     */     throws TransformerException
/*  518:     */   {
/*  519: 896 */     xctxt.pushCurrentNode(context);
/*  520: 897 */     xctxt.pushCurrentExpressionNode(context);
/*  521:     */     try
/*  522:     */     {
/*  523: 901 */       XObject score = execute(xctxt);
/*  524:     */       
/*  525: 903 */       return score.num();
/*  526:     */     }
/*  527:     */     finally
/*  528:     */     {
/*  529: 907 */       xctxt.popCurrentNode();
/*  530: 908 */       xctxt.popCurrentExpressionNode();
/*  531:     */     }
/*  532:     */   }
/*  533:     */   
/*  534:     */   public void setAxis(int axis)
/*  535:     */   {
/*  536: 922 */     this.m_axis = axis;
/*  537:     */   }
/*  538:     */   
/*  539:     */   public int getAxis()
/*  540:     */   {
/*  541: 933 */     return this.m_axis;
/*  542:     */   }
/*  543:     */   
/*  544:     */   class PredOwner
/*  545:     */     implements ExpressionOwner
/*  546:     */   {
/*  547:     */     int m_index;
/*  548:     */     
/*  549:     */     PredOwner(int index)
/*  550:     */     {
/*  551: 942 */       this.m_index = index;
/*  552:     */     }
/*  553:     */     
/*  554:     */     public Expression getExpression()
/*  555:     */     {
/*  556: 950 */       return StepPattern.this.m_predicates[this.m_index];
/*  557:     */     }
/*  558:     */     
/*  559:     */     public void setExpression(Expression exp)
/*  560:     */     {
/*  561: 959 */       exp.exprSetParent(StepPattern.this);
/*  562: 960 */       StepPattern.this.m_predicates[this.m_index] = exp;
/*  563:     */     }
/*  564:     */   }
/*  565:     */   
/*  566:     */   public void callVisitors(ExpressionOwner owner, XPathVisitor visitor)
/*  567:     */   {
/*  568: 969 */     if (visitor.visitMatchPattern(owner, this)) {
/*  569: 971 */       callSubtreeVisitors(visitor);
/*  570:     */     }
/*  571:     */   }
/*  572:     */   
/*  573:     */   protected void callSubtreeVisitors(XPathVisitor visitor)
/*  574:     */   {
/*  575: 981 */     if (null != this.m_predicates)
/*  576:     */     {
/*  577: 983 */       int n = this.m_predicates.length;
/*  578: 984 */       for (int i = 0; i < n; i++)
/*  579:     */       {
/*  580: 986 */         ExpressionOwner predOwner = new PredOwner(i);
/*  581: 987 */         if (visitor.visitPredicate(predOwner, this.m_predicates[i])) {
/*  582: 989 */           this.m_predicates[i].callVisitors(predOwner, visitor);
/*  583:     */         }
/*  584:     */       }
/*  585:     */     }
/*  586: 993 */     if (null != this.m_relativePathPattern) {
/*  587: 995 */       this.m_relativePathPattern.callVisitors(this, visitor);
/*  588:     */     }
/*  589:     */   }
/*  590:     */   
/*  591:     */   public Expression getExpression()
/*  592:     */   {
/*  593:1005 */     return this.m_relativePathPattern;
/*  594:     */   }
/*  595:     */   
/*  596:     */   public void setExpression(Expression exp)
/*  597:     */   {
/*  598:1013 */     exp.exprSetParent(this);
/*  599:1014 */     this.m_relativePathPattern = ((StepPattern)exp);
/*  600:     */   }
/*  601:     */   
/*  602:     */   public boolean deepEquals(Expression expr)
/*  603:     */   {
/*  604:1022 */     if (!super.deepEquals(expr)) {
/*  605:1023 */       return false;
/*  606:     */     }
/*  607:1025 */     StepPattern sp = (StepPattern)expr;
/*  608:1027 */     if (null != this.m_predicates)
/*  609:     */     {
/*  610:1029 */       int n = this.m_predicates.length;
/*  611:1030 */       if ((null == sp.m_predicates) || (sp.m_predicates.length != n)) {
/*  612:1031 */         return false;
/*  613:     */       }
/*  614:1032 */       for (int i = 0; i < n; i++) {
/*  615:1034 */         if (!this.m_predicates[i].deepEquals(sp.m_predicates[i])) {
/*  616:1035 */           return false;
/*  617:     */         }
/*  618:     */       }
/*  619:     */     }
/*  620:1038 */     else if (null != sp.m_predicates)
/*  621:     */     {
/*  622:1039 */       return false;
/*  623:     */     }
/*  624:1041 */     if (null != this.m_relativePathPattern)
/*  625:     */     {
/*  626:1043 */       if (!this.m_relativePathPattern.deepEquals(sp.m_relativePathPattern)) {
/*  627:1044 */         return false;
/*  628:     */       }
/*  629:     */     }
/*  630:1046 */     else if (sp.m_relativePathPattern != null) {
/*  631:1047 */       return false;
/*  632:     */     }
/*  633:1049 */     return true;
/*  634:     */   }
/*  635:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.patterns.StepPattern
 * JD-Core Version:    0.7.0.1
 */