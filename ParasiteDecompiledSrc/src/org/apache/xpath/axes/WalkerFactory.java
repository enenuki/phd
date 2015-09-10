/*    1:     */ package org.apache.xpath.axes;
/*    2:     */ 
/*    3:     */ import java.io.PrintStream;
/*    4:     */ import javax.xml.transform.TransformerException;
/*    5:     */ import org.apache.xml.dtm.DTMIterator;
/*    6:     */ import org.apache.xpath.Expression;
/*    7:     */ import org.apache.xpath.compiler.Compiler;
/*    8:     */ import org.apache.xpath.compiler.OpMap;
/*    9:     */ import org.apache.xpath.objects.XNumber;
/*   10:     */ import org.apache.xpath.patterns.ContextMatchStepPattern;
/*   11:     */ import org.apache.xpath.patterns.FunctionPattern;
/*   12:     */ import org.apache.xpath.patterns.NodeTest;
/*   13:     */ import org.apache.xpath.patterns.StepPattern;
/*   14:     */ import org.apache.xpath.res.XPATHMessages;
/*   15:     */ 
/*   16:     */ public class WalkerFactory
/*   17:     */ {
/*   18:     */   static final boolean DEBUG_PATTERN_CREATION = false;
/*   19:     */   static final boolean DEBUG_WALKER_CREATION = false;
/*   20:     */   static final boolean DEBUG_ITERATOR_CREATION = false;
/*   21:     */   public static final int BITS_COUNT = 255;
/*   22:     */   public static final int BITS_RESERVED = 3840;
/*   23:     */   public static final int BIT_PREDICATE = 4096;
/*   24:     */   public static final int BIT_ANCESTOR = 8192;
/*   25:     */   public static final int BIT_ANCESTOR_OR_SELF = 16384;
/*   26:     */   public static final int BIT_ATTRIBUTE = 32768;
/*   27:     */   public static final int BIT_CHILD = 65536;
/*   28:     */   public static final int BIT_DESCENDANT = 131072;
/*   29:     */   public static final int BIT_DESCENDANT_OR_SELF = 262144;
/*   30:     */   public static final int BIT_FOLLOWING = 524288;
/*   31:     */   public static final int BIT_FOLLOWING_SIBLING = 1048576;
/*   32:     */   public static final int BIT_NAMESPACE = 2097152;
/*   33:     */   public static final int BIT_PARENT = 4194304;
/*   34:     */   public static final int BIT_PRECEDING = 8388608;
/*   35:     */   public static final int BIT_PRECEDING_SIBLING = 16777216;
/*   36:     */   public static final int BIT_SELF = 33554432;
/*   37:     */   public static final int BIT_FILTER = 67108864;
/*   38:     */   public static final int BIT_ROOT = 134217728;
/*   39:     */   public static final int BITMASK_TRAVERSES_OUTSIDE_SUBTREE = 234381312;
/*   40:     */   public static final int BIT_BACKWARDS_SELF = 268435456;
/*   41:     */   public static final int BIT_ANY_DESCENDANT_FROM_ROOT = 536870912;
/*   42:     */   public static final int BIT_NODETEST_ANY = 1073741824;
/*   43:     */   public static final int BIT_MATCH_PATTERN = -2147483648;
/*   44:     */   
/*   45:     */   static AxesWalker loadOneWalker(WalkingIterator lpi, Compiler compiler, int stepOpCodePos)
/*   46:     */     throws TransformerException
/*   47:     */   {
/*   48:  65 */     AxesWalker firstWalker = null;
/*   49:  66 */     int stepType = compiler.getOp(stepOpCodePos);
/*   50:  68 */     if (stepType != -1)
/*   51:     */     {
/*   52:  73 */       firstWalker = createDefaultWalker(compiler, stepType, lpi, 0);
/*   53:     */       
/*   54:  75 */       firstWalker.init(compiler, stepOpCodePos, stepType);
/*   55:     */     }
/*   56:  78 */     return firstWalker;
/*   57:     */   }
/*   58:     */   
/*   59:     */   static AxesWalker loadWalkers(WalkingIterator lpi, Compiler compiler, int stepOpCodePos, int stepIndex)
/*   60:     */     throws TransformerException
/*   61:     */   {
/*   62: 101 */     AxesWalker firstWalker = null;
/*   63: 102 */     AxesWalker prevWalker = null;
/*   64:     */     
/*   65: 104 */     int analysis = analyze(compiler, stepOpCodePos, stepIndex);
/*   66:     */     int stepType;
/*   67: 106 */     while (-1 != (stepType = compiler.getOp(stepOpCodePos)))
/*   68:     */     {
/*   69: 108 */       AxesWalker walker = createDefaultWalker(compiler, stepOpCodePos, lpi, analysis);
/*   70:     */       int i;
/*   71: 110 */       walker.init(compiler, stepOpCodePos, i);
/*   72: 111 */       walker.exprSetParent(lpi);
/*   73: 114 */       if (null == firstWalker)
/*   74:     */       {
/*   75: 116 */         firstWalker = walker;
/*   76:     */       }
/*   77:     */       else
/*   78:     */       {
/*   79: 120 */         prevWalker.setNextWalker(walker);
/*   80: 121 */         walker.setPrevWalker(prevWalker);
/*   81:     */       }
/*   82: 124 */       prevWalker = walker;
/*   83: 125 */       stepOpCodePos = compiler.getNextStepPos(stepOpCodePos);
/*   84: 127 */       if (stepOpCodePos < 0) {
/*   85:     */         break;
/*   86:     */       }
/*   87:     */     }
/*   88: 131 */     return firstWalker;
/*   89:     */   }
/*   90:     */   
/*   91:     */   public static boolean isSet(int analysis, int bits)
/*   92:     */   {
/*   93: 136 */     return 0 != (analysis & bits);
/*   94:     */   }
/*   95:     */   
/*   96:     */   public static void diagnoseIterator(String name, int analysis, Compiler compiler)
/*   97:     */   {
/*   98: 141 */     System.out.println(compiler.toString() + ", " + name + ", " + Integer.toBinaryString(analysis) + ", " + getAnalysisString(analysis));
/*   99:     */   }
/*  100:     */   
/*  101:     */   public static DTMIterator newDTMIterator(Compiler compiler, int opPos, boolean isTopLevel)
/*  102:     */     throws TransformerException
/*  103:     */   {
/*  104: 164 */     int firstStepPos = OpMap.getFirstChildPos(opPos);
/*  105: 165 */     int analysis = analyze(compiler, firstStepPos, 0);
/*  106: 166 */     boolean isOneStep = isOneStep(analysis);
/*  107:     */     DTMIterator iter;
/*  108: 170 */     if ((isOneStep) && (walksSelfOnly(analysis)) && (isWild(analysis)) && (!hasPredicate(analysis))) {
/*  109: 178 */       iter = new SelfIteratorNoPredicate(compiler, opPos, analysis);
/*  110: 181 */     } else if ((walksChildrenOnly(analysis)) && (isOneStep))
/*  111:     */     {
/*  112: 185 */       if ((isWild(analysis)) && (!hasPredicate(analysis))) {
/*  113: 191 */         iter = new ChildIterator(compiler, opPos, analysis);
/*  114:     */       } else {
/*  115: 199 */         iter = new ChildTestIterator(compiler, opPos, analysis);
/*  116:     */       }
/*  117:     */     }
/*  118: 203 */     else if ((isOneStep) && (walksAttributes(analysis))) {
/*  119: 210 */       iter = new AttributeIterator(compiler, opPos, analysis);
/*  120: 212 */     } else if ((isOneStep) && (!walksFilteredList(analysis)))
/*  121:     */     {
/*  122: 214 */       if ((!walksNamespaces(analysis)) && ((walksInDocOrder(analysis)) || (isSet(analysis, 4194304)))) {
/*  123: 222 */         iter = new OneStepIteratorForward(compiler, opPos, analysis);
/*  124:     */       } else {
/*  125: 231 */         iter = new OneStepIterator(compiler, opPos, analysis);
/*  126:     */       }
/*  127:     */     }
/*  128: 247 */     else if (isOptimizableForDescendantIterator(compiler, firstStepPos, 0)) {
/*  129: 256 */       iter = new DescendantIterator(compiler, opPos, analysis);
/*  130: 260 */     } else if (isNaturalDocOrder(compiler, firstStepPos, 0, analysis)) {
/*  131: 267 */       iter = new WalkingIterator(compiler, opPos, analysis, true);
/*  132:     */     } else {
/*  133: 278 */       iter = new WalkingIteratorSorted(compiler, opPos, analysis, true);
/*  134:     */     }
/*  135: 281 */     if ((iter instanceof LocPathIterator)) {
/*  136: 282 */       ((LocPathIterator)iter).setIsTopLevel(isTopLevel);
/*  137:     */     }
/*  138: 284 */     return iter;
/*  139:     */   }
/*  140:     */   
/*  141:     */   public static int getAxisFromStep(Compiler compiler, int stepOpCodePos)
/*  142:     */     throws TransformerException
/*  143:     */   {
/*  144: 305 */     int stepType = compiler.getOp(stepOpCodePos);
/*  145: 307 */     switch (stepType)
/*  146:     */     {
/*  147:     */     case 43: 
/*  148: 310 */       return 6;
/*  149:     */     case 44: 
/*  150: 312 */       return 7;
/*  151:     */     case 46: 
/*  152: 314 */       return 11;
/*  153:     */     case 47: 
/*  154: 316 */       return 12;
/*  155:     */     case 45: 
/*  156: 318 */       return 10;
/*  157:     */     case 49: 
/*  158: 320 */       return 9;
/*  159:     */     case 37: 
/*  160: 322 */       return 0;
/*  161:     */     case 38: 
/*  162: 324 */       return 1;
/*  163:     */     case 39: 
/*  164: 326 */       return 2;
/*  165:     */     case 50: 
/*  166: 328 */       return 19;
/*  167:     */     case 40: 
/*  168: 330 */       return 3;
/*  169:     */     case 42: 
/*  170: 332 */       return 5;
/*  171:     */     case 41: 
/*  172: 334 */       return 4;
/*  173:     */     case 48: 
/*  174: 336 */       return 13;
/*  175:     */     case 22: 
/*  176:     */     case 23: 
/*  177:     */     case 24: 
/*  178:     */     case 25: 
/*  179: 341 */       return 20;
/*  180:     */     }
/*  181: 344 */     throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_NULL_ERROR_HANDLER", new Object[] { Integer.toString(stepType) }));
/*  182:     */   }
/*  183:     */   
/*  184:     */   public static int getAnalysisBitFromAxes(int axis)
/*  185:     */   {
/*  186: 355 */     switch (axis)
/*  187:     */     {
/*  188:     */     case 0: 
/*  189: 358 */       return 8192;
/*  190:     */     case 1: 
/*  191: 360 */       return 16384;
/*  192:     */     case 2: 
/*  193: 362 */       return 32768;
/*  194:     */     case 3: 
/*  195: 364 */       return 65536;
/*  196:     */     case 4: 
/*  197: 366 */       return 131072;
/*  198:     */     case 5: 
/*  199: 368 */       return 262144;
/*  200:     */     case 6: 
/*  201: 370 */       return 524288;
/*  202:     */     case 7: 
/*  203: 372 */       return 1048576;
/*  204:     */     case 8: 
/*  205:     */     case 9: 
/*  206: 375 */       return 2097152;
/*  207:     */     case 10: 
/*  208: 377 */       return 4194304;
/*  209:     */     case 11: 
/*  210: 379 */       return 8388608;
/*  211:     */     case 12: 
/*  212: 381 */       return 16777216;
/*  213:     */     case 13: 
/*  214: 383 */       return 33554432;
/*  215:     */     case 14: 
/*  216: 385 */       return 262144;
/*  217:     */     case 16: 
/*  218:     */     case 17: 
/*  219:     */     case 18: 
/*  220: 390 */       return 536870912;
/*  221:     */     case 19: 
/*  222: 392 */       return 134217728;
/*  223:     */     case 20: 
/*  224: 394 */       return 67108864;
/*  225:     */     }
/*  226: 396 */     return 67108864;
/*  227:     */   }
/*  228:     */   
/*  229:     */   static boolean functionProximateOrContainsProximate(Compiler compiler, int opPos)
/*  230:     */   {
/*  231: 403 */     int endFunc = opPos + compiler.getOp(opPos + 1) - 1;
/*  232: 404 */     opPos = OpMap.getFirstChildPos(opPos);
/*  233: 405 */     int funcID = compiler.getOp(opPos);
/*  234: 409 */     switch (funcID)
/*  235:     */     {
/*  236:     */     case 1: 
/*  237:     */     case 2: 
/*  238: 413 */       return true;
/*  239:     */     }
/*  240: 415 */     opPos++;
/*  241: 416 */     int i = 0;
/*  242: 417 */     for (int p = opPos; p < endFunc; i++)
/*  243:     */     {
/*  244: 419 */       int innerExprOpPos = p + 2;
/*  245: 420 */       int argOp = compiler.getOp(innerExprOpPos);
/*  246: 421 */       boolean prox = isProximateInnerExpr(compiler, innerExprOpPos);
/*  247: 422 */       if (prox) {
/*  248: 423 */         return true;
/*  249:     */       }
/*  250: 417 */       p = compiler.getNextOpPos(p);
/*  251:     */     }
/*  252: 427 */     return false;
/*  253:     */   }
/*  254:     */   
/*  255:     */   static boolean isProximateInnerExpr(Compiler compiler, int opPos)
/*  256:     */   {
/*  257: 432 */     int op = compiler.getOp(opPos);
/*  258: 433 */     int innerExprOpPos = opPos + 2;
/*  259:     */     boolean isProx;
/*  260: 434 */     switch (op)
/*  261:     */     {
/*  262:     */     case 26: 
/*  263: 437 */       if (isProximateInnerExpr(compiler, innerExprOpPos)) {
/*  264: 438 */         return true;
/*  265:     */       }
/*  266:     */       break;
/*  267:     */     case 21: 
/*  268:     */     case 22: 
/*  269:     */     case 27: 
/*  270:     */     case 28: 
/*  271:     */       break;
/*  272:     */     case 25: 
/*  273: 446 */       isProx = functionProximateOrContainsProximate(compiler, opPos);
/*  274: 447 */       if (isProx) {
/*  275: 448 */         return true;
/*  276:     */       }
/*  277:     */       break;
/*  278:     */     case 5: 
/*  279:     */     case 6: 
/*  280:     */     case 7: 
/*  281:     */     case 8: 
/*  282:     */     case 9: 
/*  283: 455 */       int leftPos = OpMap.getFirstChildPos(op);
/*  284: 456 */       int rightPos = compiler.getNextOpPos(leftPos);
/*  285: 457 */       isProx = isProximateInnerExpr(compiler, leftPos);
/*  286: 458 */       if (isProx) {
/*  287: 459 */         return true;
/*  288:     */       }
/*  289: 460 */       isProx = isProximateInnerExpr(compiler, rightPos);
/*  290: 461 */       if (isProx) {
/*  291: 462 */         return true;
/*  292:     */       }
/*  293:     */       break;
/*  294:     */     case 10: 
/*  295:     */     case 11: 
/*  296:     */     case 12: 
/*  297:     */     case 13: 
/*  298:     */     case 14: 
/*  299:     */     case 15: 
/*  300:     */     case 16: 
/*  301:     */     case 17: 
/*  302:     */     case 18: 
/*  303:     */     case 19: 
/*  304:     */     case 20: 
/*  305:     */     case 23: 
/*  306:     */     case 24: 
/*  307:     */     default: 
/*  308: 465 */       return true;
/*  309:     */     }
/*  310: 467 */     return false;
/*  311:     */   }
/*  312:     */   
/*  313:     */   public static boolean mightBeProximate(Compiler compiler, int opPos, int stepType)
/*  314:     */     throws TransformerException
/*  315:     */   {
/*  316: 477 */     boolean mightBeProximate = false;
/*  317:     */     int argLen;
/*  318: 480 */     switch (stepType)
/*  319:     */     {
/*  320:     */     case 22: 
/*  321:     */     case 23: 
/*  322:     */     case 24: 
/*  323:     */     case 25: 
/*  324: 486 */       argLen = compiler.getArgLength(opPos);
/*  325: 487 */       break;
/*  326:     */     default: 
/*  327: 489 */       argLen = compiler.getArgLengthOfStep(opPos);
/*  328:     */     }
/*  329: 492 */     int predPos = compiler.getFirstPredicateOpPos(opPos);
/*  330: 493 */     int count = 0;
/*  331: 495 */     while (29 == compiler.getOp(predPos))
/*  332:     */     {
/*  333: 497 */       count++;
/*  334:     */       
/*  335: 499 */       int innerExprOpPos = predPos + 2;
/*  336: 500 */       int predOp = compiler.getOp(innerExprOpPos);
/*  337:     */       boolean isProx;
/*  338: 502 */       switch (predOp)
/*  339:     */       {
/*  340:     */       case 22: 
/*  341: 505 */         return true;
/*  342:     */       case 28: 
/*  343:     */         break;
/*  344:     */       case 19: 
/*  345:     */       case 27: 
/*  346: 511 */         return true;
/*  347:     */       case 25: 
/*  348: 513 */         isProx = functionProximateOrContainsProximate(compiler, innerExprOpPos);
/*  349: 515 */         if (isProx) {
/*  350: 516 */           return true;
/*  351:     */         }
/*  352:     */         break;
/*  353:     */       case 5: 
/*  354:     */       case 6: 
/*  355:     */       case 7: 
/*  356:     */       case 8: 
/*  357:     */       case 9: 
/*  358: 523 */         int leftPos = OpMap.getFirstChildPos(innerExprOpPos);
/*  359: 524 */         int rightPos = compiler.getNextOpPos(leftPos);
/*  360: 525 */         isProx = isProximateInnerExpr(compiler, leftPos);
/*  361: 526 */         if (isProx) {
/*  362: 527 */           return true;
/*  363:     */         }
/*  364: 528 */         isProx = isProximateInnerExpr(compiler, rightPos);
/*  365: 529 */         if (isProx) {
/*  366: 530 */           return true;
/*  367:     */         }
/*  368:     */         break;
/*  369:     */       case 10: 
/*  370:     */       case 11: 
/*  371:     */       case 12: 
/*  372:     */       case 13: 
/*  373:     */       case 14: 
/*  374:     */       case 15: 
/*  375:     */       case 16: 
/*  376:     */       case 17: 
/*  377:     */       case 18: 
/*  378:     */       case 20: 
/*  379:     */       case 21: 
/*  380:     */       case 23: 
/*  381:     */       case 24: 
/*  382:     */       case 26: 
/*  383:     */       default: 
/*  384: 533 */         return true;
/*  385:     */       }
/*  386: 536 */       predPos = compiler.getNextOpPos(predPos);
/*  387:     */     }
/*  388: 539 */     return mightBeProximate;
/*  389:     */   }
/*  390:     */   
/*  391:     */   private static boolean isOptimizableForDescendantIterator(Compiler compiler, int stepOpCodePos, int stepIndex)
/*  392:     */     throws TransformerException
/*  393:     */   {
/*  394: 562 */     int stepCount = 0;
/*  395: 563 */     boolean foundDorDS = false;
/*  396: 564 */     boolean foundSelf = false;
/*  397: 565 */     boolean foundDS = false;
/*  398:     */     
/*  399: 567 */     int nodeTestType = 1033;
/*  400:     */     int stepType;
/*  401: 569 */     while (-1 != (stepType = compiler.getOp(stepOpCodePos)))
/*  402:     */     {
/*  403: 573 */       if ((nodeTestType != 1033) && (nodeTestType != 35)) {
/*  404: 574 */         return false;
/*  405:     */       }
/*  406: 576 */       stepCount++;
/*  407: 577 */       if (stepCount > 3) {
/*  408: 578 */         return false;
/*  409:     */       }
/*  410:     */       int i;
/*  411: 580 */       boolean mightBeProximate = mightBeProximate(compiler, stepOpCodePos, i);
/*  412: 581 */       if (mightBeProximate) {
/*  413: 582 */         return false;
/*  414:     */       }
/*  415: 584 */       switch (i)
/*  416:     */       {
/*  417:     */       case 22: 
/*  418:     */       case 23: 
/*  419:     */       case 24: 
/*  420:     */       case 25: 
/*  421:     */       case 37: 
/*  422:     */       case 38: 
/*  423:     */       case 39: 
/*  424:     */       case 43: 
/*  425:     */       case 44: 
/*  426:     */       case 45: 
/*  427:     */       case 46: 
/*  428:     */       case 47: 
/*  429:     */       case 49: 
/*  430:     */       case 51: 
/*  431:     */       case 52: 
/*  432:     */       case 53: 
/*  433: 602 */         return false;
/*  434:     */       case 50: 
/*  435: 604 */         if (1 != stepCount) {
/*  436: 605 */           return false;
/*  437:     */         }
/*  438:     */         break;
/*  439:     */       case 40: 
/*  440: 608 */         if ((!foundDS) && ((!foundDorDS) || (!foundSelf))) {
/*  441: 609 */           return false;
/*  442:     */         }
/*  443:     */         break;
/*  444:     */       case 42: 
/*  445: 612 */         foundDS = true;
/*  446:     */       case 41: 
/*  447: 614 */         if (3 == stepCount) {
/*  448: 615 */           return false;
/*  449:     */         }
/*  450: 616 */         foundDorDS = true;
/*  451: 617 */         break;
/*  452:     */       case 48: 
/*  453: 619 */         if (1 != stepCount) {
/*  454: 620 */           return false;
/*  455:     */         }
/*  456: 621 */         foundSelf = true;
/*  457: 622 */         break;
/*  458:     */       case 26: 
/*  459:     */       case 27: 
/*  460:     */       case 28: 
/*  461:     */       case 29: 
/*  462:     */       case 30: 
/*  463:     */       case 31: 
/*  464:     */       case 32: 
/*  465:     */       case 33: 
/*  466:     */       case 34: 
/*  467:     */       case 35: 
/*  468:     */       case 36: 
/*  469:     */       default: 
/*  470: 624 */         throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_NULL_ERROR_HANDLER", new Object[] { Integer.toString(i) }));
/*  471:     */       }
/*  472: 628 */       nodeTestType = compiler.getStepTestType(stepOpCodePos);
/*  473:     */       
/*  474: 630 */       int nextStepOpCodePos = compiler.getNextStepPos(stepOpCodePos);
/*  475: 632 */       if (nextStepOpCodePos < 0) {
/*  476:     */         break;
/*  477:     */       }
/*  478: 635 */       if (-1 != compiler.getOp(nextStepOpCodePos)) {
/*  479: 637 */         if (compiler.countPredicates(stepOpCodePos) > 0) {
/*  480: 639 */           return false;
/*  481:     */         }
/*  482:     */       }
/*  483: 643 */       stepOpCodePos = nextStepOpCodePos;
/*  484:     */     }
/*  485: 646 */     return true;
/*  486:     */   }
/*  487:     */   
/*  488:     */   private static int analyze(Compiler compiler, int stepOpCodePos, int stepIndex)
/*  489:     */     throws TransformerException
/*  490:     */   {
/*  491: 670 */     int stepCount = 0;
/*  492: 671 */     int analysisResult = 0;
/*  493:     */     int stepType;
/*  494: 673 */     while (-1 != (stepType = compiler.getOp(stepOpCodePos)))
/*  495:     */     {
/*  496: 675 */       stepCount++;
/*  497:     */       int i;
/*  498: 682 */       boolean predAnalysis = analyzePredicate(compiler, stepOpCodePos, i);
/*  499: 685 */       if (predAnalysis) {
/*  500: 686 */         analysisResult |= 0x1000;
/*  501:     */       }
/*  502: 688 */       switch (i)
/*  503:     */       {
/*  504:     */       case 22: 
/*  505:     */       case 23: 
/*  506:     */       case 24: 
/*  507:     */       case 25: 
/*  508: 694 */         analysisResult |= 0x4000000;
/*  509: 695 */         break;
/*  510:     */       case 50: 
/*  511: 697 */         analysisResult |= 0x8000000;
/*  512: 698 */         break;
/*  513:     */       case 37: 
/*  514: 700 */         analysisResult |= 0x2000;
/*  515: 701 */         break;
/*  516:     */       case 38: 
/*  517: 703 */         analysisResult |= 0x4000;
/*  518: 704 */         break;
/*  519:     */       case 39: 
/*  520: 706 */         analysisResult |= 0x8000;
/*  521: 707 */         break;
/*  522:     */       case 49: 
/*  523: 709 */         analysisResult |= 0x200000;
/*  524: 710 */         break;
/*  525:     */       case 40: 
/*  526: 712 */         analysisResult |= 0x10000;
/*  527: 713 */         break;
/*  528:     */       case 41: 
/*  529: 715 */         analysisResult |= 0x20000;
/*  530: 716 */         break;
/*  531:     */       case 42: 
/*  532: 720 */         if ((2 == stepCount) && (134217728 == analysisResult)) {
/*  533: 722 */           analysisResult |= 0x20000000;
/*  534:     */         }
/*  535: 725 */         analysisResult |= 0x40000;
/*  536: 726 */         break;
/*  537:     */       case 43: 
/*  538: 728 */         analysisResult |= 0x80000;
/*  539: 729 */         break;
/*  540:     */       case 44: 
/*  541: 731 */         analysisResult |= 0x100000;
/*  542: 732 */         break;
/*  543:     */       case 46: 
/*  544: 734 */         analysisResult |= 0x800000;
/*  545: 735 */         break;
/*  546:     */       case 47: 
/*  547: 737 */         analysisResult |= 0x1000000;
/*  548: 738 */         break;
/*  549:     */       case 45: 
/*  550: 740 */         analysisResult |= 0x400000;
/*  551: 741 */         break;
/*  552:     */       case 48: 
/*  553: 743 */         analysisResult |= 0x2000000;
/*  554: 744 */         break;
/*  555:     */       case 51: 
/*  556: 746 */         analysisResult |= 0x80008000;
/*  557: 747 */         break;
/*  558:     */       case 52: 
/*  559: 749 */         analysisResult |= 0x80002000;
/*  560: 750 */         break;
/*  561:     */       case 53: 
/*  562: 752 */         analysisResult |= 0x80400000;
/*  563: 753 */         break;
/*  564:     */       case 26: 
/*  565:     */       case 27: 
/*  566:     */       case 28: 
/*  567:     */       case 29: 
/*  568:     */       case 30: 
/*  569:     */       case 31: 
/*  570:     */       case 32: 
/*  571:     */       case 33: 
/*  572:     */       case 34: 
/*  573:     */       case 35: 
/*  574:     */       case 36: 
/*  575:     */       default: 
/*  576: 755 */         throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_NULL_ERROR_HANDLER", new Object[] { Integer.toString(i) }));
/*  577:     */       }
/*  578: 759 */       if (1033 == compiler.getOp(stepOpCodePos + 3)) {
/*  579: 761 */         analysisResult |= 0x40000000;
/*  580:     */       }
/*  581: 764 */       stepOpCodePos = compiler.getNextStepPos(stepOpCodePos);
/*  582: 766 */       if (stepOpCodePos < 0) {
/*  583:     */         break;
/*  584:     */       }
/*  585:     */     }
/*  586: 770 */     analysisResult |= stepCount & 0xFF;
/*  587:     */     
/*  588: 772 */     return analysisResult;
/*  589:     */   }
/*  590:     */   
/*  591:     */   public static boolean isDownwardAxisOfMany(int axis)
/*  592:     */   {
/*  593: 785 */     return (5 == axis) || (4 == axis) || (6 == axis) || (11 == axis);
/*  594:     */   }
/*  595:     */   
/*  596:     */   static StepPattern loadSteps(MatchPatternIterator mpi, Compiler compiler, int stepOpCodePos, int stepIndex)
/*  597:     */     throws TransformerException
/*  598:     */   {
/*  599: 830 */     StepPattern step = null;
/*  600: 831 */     StepPattern firstStep = null;StepPattern prevStep = null;
/*  601: 832 */     int analysis = analyze(compiler, stepOpCodePos, stepIndex);
/*  602:     */     int stepType;
/*  603: 834 */     while (-1 != (stepType = compiler.getOp(stepOpCodePos)))
/*  604:     */     {
/*  605: 836 */       step = createDefaultStepPattern(compiler, stepOpCodePos, mpi, analysis, firstStep, prevStep);
/*  606: 839 */       if (null == firstStep) {
/*  607: 841 */         firstStep = step;
/*  608:     */       } else {
/*  609: 847 */         step.setRelativePathPattern(prevStep);
/*  610:     */       }
/*  611: 850 */       prevStep = step;
/*  612: 851 */       stepOpCodePos = compiler.getNextStepPos(stepOpCodePos);
/*  613: 853 */       if (stepOpCodePos < 0) {
/*  614:     */         break;
/*  615:     */       }
/*  616:     */     }
/*  617: 857 */     int axis = 13;
/*  618: 858 */     int paxis = 13;
/*  619: 859 */     StepPattern tail = step;
/*  620: 860 */     for (StepPattern pat = step; null != pat; pat = pat.getRelativePathPattern())
/*  621:     */     {
/*  622: 863 */       int nextAxis = pat.getAxis();
/*  623:     */       
/*  624: 865 */       pat.setAxis(axis);
/*  625:     */       
/*  626:     */ 
/*  627:     */ 
/*  628:     */ 
/*  629:     */ 
/*  630:     */ 
/*  631:     */ 
/*  632:     */ 
/*  633:     */ 
/*  634:     */ 
/*  635:     */ 
/*  636:     */ 
/*  637:     */ 
/*  638:     */ 
/*  639:     */ 
/*  640:     */ 
/*  641:     */ 
/*  642:     */ 
/*  643:     */ 
/*  644:     */ 
/*  645:     */ 
/*  646:     */ 
/*  647:     */ 
/*  648:     */ 
/*  649: 890 */       int whatToShow = pat.getWhatToShow();
/*  650: 891 */       if ((whatToShow == 2) || (whatToShow == 4096))
/*  651:     */       {
/*  652: 894 */         int newAxis = whatToShow == 2 ? 2 : 9;
/*  653: 896 */         if (isDownwardAxisOfMany(axis))
/*  654:     */         {
/*  655: 898 */           StepPattern attrPat = new StepPattern(whatToShow, pat.getNamespace(), pat.getLocalName(), newAxis, 0);
/*  656:     */           
/*  657:     */ 
/*  658:     */ 
/*  659:     */ 
/*  660: 903 */           XNumber score = pat.getStaticScore();
/*  661: 904 */           pat.setNamespace(null);
/*  662: 905 */           pat.setLocalName("*");
/*  663: 906 */           attrPat.setPredicates(pat.getPredicates());
/*  664: 907 */           pat.setPredicates(null);
/*  665: 908 */           pat.setWhatToShow(1);
/*  666: 909 */           StepPattern rel = pat.getRelativePathPattern();
/*  667: 910 */           pat.setRelativePathPattern(attrPat);
/*  668: 911 */           attrPat.setRelativePathPattern(rel);
/*  669: 912 */           attrPat.setStaticScore(score);
/*  670: 918 */           if (11 == pat.getAxis()) {
/*  671: 919 */             pat.setAxis(15);
/*  672: 921 */           } else if (4 == pat.getAxis()) {
/*  673: 922 */             pat.setAxis(5);
/*  674:     */           }
/*  675: 924 */           pat = attrPat;
/*  676:     */         }
/*  677: 926 */         else if (3 == pat.getAxis())
/*  678:     */         {
/*  679: 930 */           pat.setAxis(2);
/*  680:     */         }
/*  681:     */       }
/*  682: 933 */       axis = nextAxis;
/*  683:     */       
/*  684: 935 */       tail = pat;
/*  685:     */     }
/*  686: 938 */     if (axis < 16)
/*  687:     */     {
/*  688: 940 */       StepPattern selfPattern = new ContextMatchStepPattern(axis, paxis);
/*  689:     */       
/*  690: 942 */       XNumber score = tail.getStaticScore();
/*  691: 943 */       tail.setRelativePathPattern(selfPattern);
/*  692: 944 */       tail.setStaticScore(score);
/*  693: 945 */       selfPattern.setStaticScore(score);
/*  694:     */     }
/*  695: 954 */     return step;
/*  696:     */   }
/*  697:     */   
/*  698:     */   private static StepPattern createDefaultStepPattern(Compiler compiler, int opPos, MatchPatternIterator mpi, int analysis, StepPattern tail, StepPattern head)
/*  699:     */     throws TransformerException
/*  700:     */   {
/*  701: 984 */     int stepType = compiler.getOp(opPos);
/*  702: 985 */     boolean simpleInit = false;
/*  703: 986 */     boolean prevIsOneStepDown = true;
/*  704:     */     
/*  705: 988 */     int whatToShow = compiler.getWhatToShow(opPos);
/*  706: 989 */     StepPattern ai = null;
/*  707:     */     int axis;
/*  708:     */     int predicateAxis;
/*  709: 992 */     switch (stepType)
/*  710:     */     {
/*  711:     */     case 22: 
/*  712:     */     case 23: 
/*  713:     */     case 24: 
/*  714:     */     case 25: 
/*  715: 998 */       prevIsOneStepDown = false;
/*  716:     */       Expression expr;
/*  717:1002 */       switch (stepType)
/*  718:     */       {
/*  719:     */       case 22: 
/*  720:     */       case 23: 
/*  721:     */       case 24: 
/*  722:     */       case 25: 
/*  723:1008 */         expr = compiler.compile(opPos);
/*  724:1009 */         break;
/*  725:     */       default: 
/*  726:1011 */         expr = compiler.compile(opPos + 2);
/*  727:     */       }
/*  728:1014 */       axis = 20;
/*  729:1015 */       predicateAxis = 20;
/*  730:1016 */       ai = new FunctionPattern(expr, axis, predicateAxis);
/*  731:1017 */       simpleInit = true;
/*  732:1018 */       break;
/*  733:     */     case 50: 
/*  734:1020 */       whatToShow = 1280;
/*  735:     */       
/*  736:     */ 
/*  737:1023 */       axis = 19;
/*  738:1024 */       predicateAxis = 19;
/*  739:1025 */       ai = new StepPattern(1280, axis, predicateAxis);
/*  740:     */       
/*  741:     */ 
/*  742:1028 */       break;
/*  743:     */     case 39: 
/*  744:1030 */       whatToShow = 2;
/*  745:1031 */       axis = 10;
/*  746:1032 */       predicateAxis = 2;
/*  747:     */       
/*  748:1034 */       break;
/*  749:     */     case 49: 
/*  750:1036 */       whatToShow = 4096;
/*  751:1037 */       axis = 10;
/*  752:1038 */       predicateAxis = 9;
/*  753:     */       
/*  754:1040 */       break;
/*  755:     */     case 37: 
/*  756:1042 */       axis = 4;
/*  757:1043 */       predicateAxis = 0;
/*  758:1044 */       break;
/*  759:     */     case 40: 
/*  760:1046 */       axis = 10;
/*  761:1047 */       predicateAxis = 3;
/*  762:1048 */       break;
/*  763:     */     case 38: 
/*  764:1050 */       axis = 5;
/*  765:1051 */       predicateAxis = 1;
/*  766:1052 */       break;
/*  767:     */     case 48: 
/*  768:1054 */       axis = 13;
/*  769:1055 */       predicateAxis = 13;
/*  770:1056 */       break;
/*  771:     */     case 45: 
/*  772:1058 */       axis = 3;
/*  773:1059 */       predicateAxis = 10;
/*  774:1060 */       break;
/*  775:     */     case 47: 
/*  776:1062 */       axis = 7;
/*  777:1063 */       predicateAxis = 12;
/*  778:1064 */       break;
/*  779:     */     case 46: 
/*  780:1066 */       axis = 6;
/*  781:1067 */       predicateAxis = 11;
/*  782:1068 */       break;
/*  783:     */     case 44: 
/*  784:1070 */       axis = 12;
/*  785:1071 */       predicateAxis = 7;
/*  786:1072 */       break;
/*  787:     */     case 43: 
/*  788:1074 */       axis = 11;
/*  789:1075 */       predicateAxis = 6;
/*  790:1076 */       break;
/*  791:     */     case 42: 
/*  792:1078 */       axis = 1;
/*  793:1079 */       predicateAxis = 5;
/*  794:1080 */       break;
/*  795:     */     case 41: 
/*  796:1082 */       axis = 0;
/*  797:1083 */       predicateAxis = 4;
/*  798:1084 */       break;
/*  799:     */     case 26: 
/*  800:     */     case 27: 
/*  801:     */     case 28: 
/*  802:     */     case 29: 
/*  803:     */     case 30: 
/*  804:     */     case 31: 
/*  805:     */     case 32: 
/*  806:     */     case 33: 
/*  807:     */     case 34: 
/*  808:     */     case 35: 
/*  809:     */     case 36: 
/*  810:     */     default: 
/*  811:1086 */       throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_NULL_ERROR_HANDLER", new Object[] { Integer.toString(stepType) }));
/*  812:     */     }
/*  813:1089 */     if (null == ai)
/*  814:     */     {
/*  815:1091 */       whatToShow = compiler.getWhatToShow(opPos);
/*  816:1092 */       ai = new StepPattern(whatToShow, compiler.getStepNS(opPos), compiler.getStepLocalName(opPos), axis, predicateAxis);
/*  817:     */     }
/*  818:1107 */     int argLen = compiler.getFirstPredicateOpPos(opPos);
/*  819:     */     
/*  820:1109 */     ai.setPredicates(compiler.getCompiledPredicates(argLen));
/*  821:     */     
/*  822:1111 */     return ai;
/*  823:     */   }
/*  824:     */   
/*  825:     */   static boolean analyzePredicate(Compiler compiler, int opPos, int stepType)
/*  826:     */     throws TransformerException
/*  827:     */   {
/*  828:     */     int argLen;
/*  829:1133 */     switch (stepType)
/*  830:     */     {
/*  831:     */     case 22: 
/*  832:     */     case 23: 
/*  833:     */     case 24: 
/*  834:     */     case 25: 
/*  835:1139 */       argLen = compiler.getArgLength(opPos);
/*  836:1140 */       break;
/*  837:     */     default: 
/*  838:1142 */       argLen = compiler.getArgLengthOfStep(opPos);
/*  839:     */     }
/*  840:1145 */     int pos = compiler.getFirstPredicateOpPos(opPos);
/*  841:1146 */     int nPredicates = compiler.countPredicates(pos);
/*  842:     */     
/*  843:1148 */     return nPredicates > 0;
/*  844:     */   }
/*  845:     */   
/*  846:     */   private static AxesWalker createDefaultWalker(Compiler compiler, int opPos, WalkingIterator lpi, int analysis)
/*  847:     */   {
/*  848:1168 */     AxesWalker ai = null;
/*  849:1169 */     int stepType = compiler.getOp(opPos);
/*  850:     */     
/*  851:     */ 
/*  852:     */ 
/*  853:     */ 
/*  854:     */ 
/*  855:     */ 
/*  856:     */ 
/*  857:     */ 
/*  858:     */ 
/*  859:1179 */     boolean simpleInit = false;
/*  860:1180 */     int totalNumberWalkers = analysis & 0xFF;
/*  861:1181 */     boolean prevIsOneStepDown = true;
/*  862:1183 */     switch (stepType)
/*  863:     */     {
/*  864:     */     case 22: 
/*  865:     */     case 23: 
/*  866:     */     case 24: 
/*  867:     */     case 25: 
/*  868:1189 */       prevIsOneStepDown = false;
/*  869:     */       
/*  870:     */ 
/*  871:     */ 
/*  872:     */ 
/*  873:     */ 
/*  874:1195 */       ai = new FilterExprWalker(lpi);
/*  875:1196 */       simpleInit = true;
/*  876:1197 */       break;
/*  877:     */     case 50: 
/*  878:1199 */       ai = new AxesWalker(lpi, 19);
/*  879:1200 */       break;
/*  880:     */     case 37: 
/*  881:1202 */       prevIsOneStepDown = false;
/*  882:1203 */       ai = new ReverseAxesWalker(lpi, 0);
/*  883:1204 */       break;
/*  884:     */     case 38: 
/*  885:1206 */       prevIsOneStepDown = false;
/*  886:1207 */       ai = new ReverseAxesWalker(lpi, 1);
/*  887:1208 */       break;
/*  888:     */     case 39: 
/*  889:1210 */       ai = new AxesWalker(lpi, 2);
/*  890:1211 */       break;
/*  891:     */     case 49: 
/*  892:1213 */       ai = new AxesWalker(lpi, 9);
/*  893:1214 */       break;
/*  894:     */     case 40: 
/*  895:1216 */       ai = new AxesWalker(lpi, 3);
/*  896:1217 */       break;
/*  897:     */     case 41: 
/*  898:1219 */       prevIsOneStepDown = false;
/*  899:1220 */       ai = new AxesWalker(lpi, 4);
/*  900:1221 */       break;
/*  901:     */     case 42: 
/*  902:1223 */       prevIsOneStepDown = false;
/*  903:1224 */       ai = new AxesWalker(lpi, 5);
/*  904:1225 */       break;
/*  905:     */     case 43: 
/*  906:1227 */       prevIsOneStepDown = false;
/*  907:1228 */       ai = new AxesWalker(lpi, 6);
/*  908:1229 */       break;
/*  909:     */     case 44: 
/*  910:1231 */       prevIsOneStepDown = false;
/*  911:1232 */       ai = new AxesWalker(lpi, 7);
/*  912:1233 */       break;
/*  913:     */     case 46: 
/*  914:1235 */       prevIsOneStepDown = false;
/*  915:1236 */       ai = new ReverseAxesWalker(lpi, 11);
/*  916:1237 */       break;
/*  917:     */     case 47: 
/*  918:1239 */       prevIsOneStepDown = false;
/*  919:1240 */       ai = new ReverseAxesWalker(lpi, 12);
/*  920:1241 */       break;
/*  921:     */     case 45: 
/*  922:1243 */       prevIsOneStepDown = false;
/*  923:1244 */       ai = new ReverseAxesWalker(lpi, 10);
/*  924:1245 */       break;
/*  925:     */     case 48: 
/*  926:1247 */       ai = new AxesWalker(lpi, 13);
/*  927:1248 */       break;
/*  928:     */     case 26: 
/*  929:     */     case 27: 
/*  930:     */     case 28: 
/*  931:     */     case 29: 
/*  932:     */     case 30: 
/*  933:     */     case 31: 
/*  934:     */     case 32: 
/*  935:     */     case 33: 
/*  936:     */     case 34: 
/*  937:     */     case 35: 
/*  938:     */     case 36: 
/*  939:     */     default: 
/*  940:1250 */       throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_NULL_ERROR_HANDLER", new Object[] { Integer.toString(stepType) }));
/*  941:     */     }
/*  942:1254 */     if (simpleInit)
/*  943:     */     {
/*  944:1256 */       ai.initNodeTest(-1);
/*  945:     */     }
/*  946:     */     else
/*  947:     */     {
/*  948:1260 */       int whatToShow = compiler.getWhatToShow(opPos);
/*  949:1269 */       if ((0 == (whatToShow & 0x1043)) || (whatToShow == -1)) {
/*  950:1272 */         ai.initNodeTest(whatToShow);
/*  951:     */       } else {
/*  952:1275 */         ai.initNodeTest(whatToShow, compiler.getStepNS(opPos), compiler.getStepLocalName(opPos));
/*  953:     */       }
/*  954:     */     }
/*  955:1280 */     return ai;
/*  956:     */   }
/*  957:     */   
/*  958:     */   public static String getAnalysisString(int analysis)
/*  959:     */   {
/*  960:1285 */     StringBuffer buf = new StringBuffer();
/*  961:1286 */     buf.append("count: " + getStepCount(analysis) + " ");
/*  962:1287 */     if ((analysis & 0x40000000) != 0) {
/*  963:1289 */       buf.append("NTANY|");
/*  964:     */     }
/*  965:1291 */     if ((analysis & 0x1000) != 0) {
/*  966:1293 */       buf.append("PRED|");
/*  967:     */     }
/*  968:1295 */     if ((analysis & 0x2000) != 0) {
/*  969:1297 */       buf.append("ANC|");
/*  970:     */     }
/*  971:1299 */     if ((analysis & 0x4000) != 0) {
/*  972:1301 */       buf.append("ANCOS|");
/*  973:     */     }
/*  974:1303 */     if ((analysis & 0x8000) != 0) {
/*  975:1305 */       buf.append("ATTR|");
/*  976:     */     }
/*  977:1307 */     if ((analysis & 0x10000) != 0) {
/*  978:1309 */       buf.append("CH|");
/*  979:     */     }
/*  980:1311 */     if ((analysis & 0x20000) != 0) {
/*  981:1313 */       buf.append("DESC|");
/*  982:     */     }
/*  983:1315 */     if ((analysis & 0x40000) != 0) {
/*  984:1317 */       buf.append("DESCOS|");
/*  985:     */     }
/*  986:1319 */     if ((analysis & 0x80000) != 0) {
/*  987:1321 */       buf.append("FOL|");
/*  988:     */     }
/*  989:1323 */     if ((analysis & 0x100000) != 0) {
/*  990:1325 */       buf.append("FOLS|");
/*  991:     */     }
/*  992:1327 */     if ((analysis & 0x200000) != 0) {
/*  993:1329 */       buf.append("NS|");
/*  994:     */     }
/*  995:1331 */     if ((analysis & 0x400000) != 0) {
/*  996:1333 */       buf.append("P|");
/*  997:     */     }
/*  998:1335 */     if ((analysis & 0x800000) != 0) {
/*  999:1337 */       buf.append("PREC|");
/* 1000:     */     }
/* 1001:1339 */     if ((analysis & 0x1000000) != 0) {
/* 1002:1341 */       buf.append("PRECS|");
/* 1003:     */     }
/* 1004:1343 */     if ((analysis & 0x2000000) != 0) {
/* 1005:1345 */       buf.append(".|");
/* 1006:     */     }
/* 1007:1347 */     if ((analysis & 0x4000000) != 0) {
/* 1008:1349 */       buf.append("FLT|");
/* 1009:     */     }
/* 1010:1351 */     if ((analysis & 0x8000000) != 0) {
/* 1011:1353 */       buf.append("R|");
/* 1012:     */     }
/* 1013:1355 */     return buf.toString();
/* 1014:     */   }
/* 1015:     */   
/* 1016:     */   public static boolean hasPredicate(int analysis)
/* 1017:     */   {
/* 1018:1369 */     return 0 != (analysis & 0x1000);
/* 1019:     */   }
/* 1020:     */   
/* 1021:     */   public static boolean isWild(int analysis)
/* 1022:     */   {
/* 1023:1374 */     return 0 != (analysis & 0x40000000);
/* 1024:     */   }
/* 1025:     */   
/* 1026:     */   public static boolean walksAncestors(int analysis)
/* 1027:     */   {
/* 1028:1379 */     return isSet(analysis, 24576);
/* 1029:     */   }
/* 1030:     */   
/* 1031:     */   public static boolean walksAttributes(int analysis)
/* 1032:     */   {
/* 1033:1384 */     return 0 != (analysis & 0x8000);
/* 1034:     */   }
/* 1035:     */   
/* 1036:     */   public static boolean walksNamespaces(int analysis)
/* 1037:     */   {
/* 1038:1389 */     return 0 != (analysis & 0x200000);
/* 1039:     */   }
/* 1040:     */   
/* 1041:     */   public static boolean walksChildren(int analysis)
/* 1042:     */   {
/* 1043:1394 */     return 0 != (analysis & 0x10000);
/* 1044:     */   }
/* 1045:     */   
/* 1046:     */   public static boolean walksDescendants(int analysis)
/* 1047:     */   {
/* 1048:1399 */     return isSet(analysis, 393216);
/* 1049:     */   }
/* 1050:     */   
/* 1051:     */   public static boolean walksSubtree(int analysis)
/* 1052:     */   {
/* 1053:1404 */     return isSet(analysis, 458752);
/* 1054:     */   }
/* 1055:     */   
/* 1056:     */   public static boolean walksSubtreeOnlyMaybeAbsolute(int analysis)
/* 1057:     */   {
/* 1058:1409 */     return (walksSubtree(analysis)) && (!walksExtraNodes(analysis)) && (!walksUp(analysis)) && (!walksSideways(analysis));
/* 1059:     */   }
/* 1060:     */   
/* 1061:     */   public static boolean walksSubtreeOnly(int analysis)
/* 1062:     */   {
/* 1063:1418 */     return (walksSubtreeOnlyMaybeAbsolute(analysis)) && (!isAbsolute(analysis));
/* 1064:     */   }
/* 1065:     */   
/* 1066:     */   public static boolean walksFilteredList(int analysis)
/* 1067:     */   {
/* 1068:1425 */     return isSet(analysis, 67108864);
/* 1069:     */   }
/* 1070:     */   
/* 1071:     */   public static boolean walksSubtreeOnlyFromRootOrContext(int analysis)
/* 1072:     */   {
/* 1073:1430 */     return (walksSubtree(analysis)) && (!walksExtraNodes(analysis)) && (!walksUp(analysis)) && (!walksSideways(analysis)) && (!isSet(analysis, 67108864));
/* 1074:     */   }
/* 1075:     */   
/* 1076:     */   public static boolean walksInDocOrder(int analysis)
/* 1077:     */   {
/* 1078:1440 */     return ((walksSubtreeOnlyMaybeAbsolute(analysis)) || (walksExtraNodesOnly(analysis)) || (walksFollowingOnlyMaybeAbsolute(analysis))) && (!isSet(analysis, 67108864));
/* 1079:     */   }
/* 1080:     */   
/* 1081:     */   public static boolean walksFollowingOnlyMaybeAbsolute(int analysis)
/* 1082:     */   {
/* 1083:1449 */     return (isSet(analysis, 35127296)) && (!walksSubtree(analysis)) && (!walksUp(analysis)) && (!walksSideways(analysis));
/* 1084:     */   }
/* 1085:     */   
/* 1086:     */   public static boolean walksUp(int analysis)
/* 1087:     */   {
/* 1088:1458 */     return isSet(analysis, 4218880);
/* 1089:     */   }
/* 1090:     */   
/* 1091:     */   public static boolean walksSideways(int analysis)
/* 1092:     */   {
/* 1093:1463 */     return isSet(analysis, 26738688);
/* 1094:     */   }
/* 1095:     */   
/* 1096:     */   public static boolean walksExtraNodes(int analysis)
/* 1097:     */   {
/* 1098:1469 */     return isSet(analysis, 2129920);
/* 1099:     */   }
/* 1100:     */   
/* 1101:     */   public static boolean walksExtraNodesOnly(int analysis)
/* 1102:     */   {
/* 1103:1474 */     return (walksExtraNodes(analysis)) && (!isSet(analysis, 33554432)) && (!walksSubtree(analysis)) && (!walksUp(analysis)) && (!walksSideways(analysis)) && (!isAbsolute(analysis));
/* 1104:     */   }
/* 1105:     */   
/* 1106:     */   public static boolean isAbsolute(int analysis)
/* 1107:     */   {
/* 1108:1485 */     return isSet(analysis, 201326592);
/* 1109:     */   }
/* 1110:     */   
/* 1111:     */   public static boolean walksChildrenOnly(int analysis)
/* 1112:     */   {
/* 1113:1490 */     return (walksChildren(analysis)) && (!isSet(analysis, 33554432)) && (!walksExtraNodes(analysis)) && (!walksDescendants(analysis)) && (!walksUp(analysis)) && (!walksSideways(analysis)) && ((!isAbsolute(analysis)) || (isSet(analysis, 134217728)));
/* 1114:     */   }
/* 1115:     */   
/* 1116:     */   public static boolean walksChildrenAndExtraAndSelfOnly(int analysis)
/* 1117:     */   {
/* 1118:1502 */     return (walksChildren(analysis)) && (!walksDescendants(analysis)) && (!walksUp(analysis)) && (!walksSideways(analysis)) && ((!isAbsolute(analysis)) || (isSet(analysis, 134217728)));
/* 1119:     */   }
/* 1120:     */   
/* 1121:     */   public static boolean walksDescendantsAndExtraAndSelfOnly(int analysis)
/* 1122:     */   {
/* 1123:1512 */     return (!walksChildren(analysis)) && (walksDescendants(analysis)) && (!walksUp(analysis)) && (!walksSideways(analysis)) && ((!isAbsolute(analysis)) || (isSet(analysis, 134217728)));
/* 1124:     */   }
/* 1125:     */   
/* 1126:     */   public static boolean walksSelfOnly(int analysis)
/* 1127:     */   {
/* 1128:1522 */     return (isSet(analysis, 33554432)) && (!walksSubtree(analysis)) && (!walksUp(analysis)) && (!walksSideways(analysis)) && (!isAbsolute(analysis));
/* 1129:     */   }
/* 1130:     */   
/* 1131:     */   public static boolean walksUpOnly(int analysis)
/* 1132:     */   {
/* 1133:1533 */     return (!walksSubtree(analysis)) && (walksUp(analysis)) && (!walksSideways(analysis)) && (!isAbsolute(analysis));
/* 1134:     */   }
/* 1135:     */   
/* 1136:     */   public static boolean walksDownOnly(int analysis)
/* 1137:     */   {
/* 1138:1542 */     return (walksSubtree(analysis)) && (!walksUp(analysis)) && (!walksSideways(analysis)) && (!isAbsolute(analysis));
/* 1139:     */   }
/* 1140:     */   
/* 1141:     */   public static boolean walksDownExtraOnly(int analysis)
/* 1142:     */   {
/* 1143:1551 */     return (walksSubtree(analysis)) && (walksExtraNodes(analysis)) && (!walksUp(analysis)) && (!walksSideways(analysis)) && (!isAbsolute(analysis));
/* 1144:     */   }
/* 1145:     */   
/* 1146:     */   public static boolean canSkipSubtrees(int analysis)
/* 1147:     */   {
/* 1148:1560 */     return isSet(analysis, 65536) | walksSideways(analysis);
/* 1149:     */   }
/* 1150:     */   
/* 1151:     */   public static boolean canCrissCross(int analysis)
/* 1152:     */   {
/* 1153:1566 */     if (walksSelfOnly(analysis)) {
/* 1154:1567 */       return false;
/* 1155:     */     }
/* 1156:1568 */     if ((walksDownOnly(analysis)) && (!canSkipSubtrees(analysis))) {
/* 1157:1569 */       return false;
/* 1158:     */     }
/* 1159:1570 */     if (walksChildrenAndExtraAndSelfOnly(analysis)) {
/* 1160:1571 */       return false;
/* 1161:     */     }
/* 1162:1572 */     if (walksDescendantsAndExtraAndSelfOnly(analysis)) {
/* 1163:1573 */       return false;
/* 1164:     */     }
/* 1165:1574 */     if (walksUpOnly(analysis)) {
/* 1166:1575 */       return false;
/* 1167:     */     }
/* 1168:1576 */     if (walksExtraNodesOnly(analysis)) {
/* 1169:1577 */       return false;
/* 1170:     */     }
/* 1171:1578 */     if ((walksSubtree(analysis)) && ((walksSideways(analysis)) || (walksUp(analysis)) || (canSkipSubtrees(analysis)))) {
/* 1172:1582 */       return true;
/* 1173:     */     }
/* 1174:1584 */     return false;
/* 1175:     */   }
/* 1176:     */   
/* 1177:     */   public static boolean isNaturalDocOrder(int analysis)
/* 1178:     */   {
/* 1179:1599 */     if ((canCrissCross(analysis)) || (isSet(analysis, 2097152)) || (walksFilteredList(analysis))) {
/* 1180:1601 */       return false;
/* 1181:     */     }
/* 1182:1603 */     if (walksInDocOrder(analysis)) {
/* 1183:1604 */       return true;
/* 1184:     */     }
/* 1185:1606 */     return false;
/* 1186:     */   }
/* 1187:     */   
/* 1188:     */   private static boolean isNaturalDocOrder(Compiler compiler, int stepOpCodePos, int stepIndex, int analysis)
/* 1189:     */     throws TransformerException
/* 1190:     */   {
/* 1191:1627 */     if (canCrissCross(analysis)) {
/* 1192:1628 */       return false;
/* 1193:     */     }
/* 1194:1632 */     if (isSet(analysis, 2097152)) {
/* 1195:1633 */       return false;
/* 1196:     */     }
/* 1197:1641 */     if ((isSet(analysis, 1572864)) && (isSet(analysis, 25165824))) {
/* 1198:1643 */       return false;
/* 1199:     */     }
/* 1200:1651 */     int stepCount = 0;
/* 1201:1652 */     boolean foundWildAttribute = false;
/* 1202:     */     
/* 1203:     */ 
/* 1204:     */ 
/* 1205:     */ 
/* 1206:1657 */     int potentialDuplicateMakingStepCount = 0;
/* 1207:     */     int stepType;
/* 1208:1659 */     while (-1 != (stepType = compiler.getOp(stepOpCodePos)))
/* 1209:     */     {
/* 1210:1661 */       stepCount++;
/* 1211:     */       int i;
/* 1212:1663 */       switch (i)
/* 1213:     */       {
/* 1214:     */       case 39: 
/* 1215:     */       case 51: 
/* 1216:1667 */         if (foundWildAttribute) {
/* 1217:1668 */           return false;
/* 1218:     */         }
/* 1219:1673 */         String localName = compiler.getStepLocalName(stepOpCodePos);
/* 1220:1675 */         if (localName.equals("*")) {
/* 1221:1677 */           foundWildAttribute = true;
/* 1222:     */         }
/* 1223:     */         break;
/* 1224:     */       case 22: 
/* 1225:     */       case 23: 
/* 1226:     */       case 24: 
/* 1227:     */       case 25: 
/* 1228:     */       case 37: 
/* 1229:     */       case 38: 
/* 1230:     */       case 41: 
/* 1231:     */       case 42: 
/* 1232:     */       case 43: 
/* 1233:     */       case 44: 
/* 1234:     */       case 45: 
/* 1235:     */       case 46: 
/* 1236:     */       case 47: 
/* 1237:     */       case 49: 
/* 1238:     */       case 52: 
/* 1239:     */       case 53: 
/* 1240:1696 */         if (potentialDuplicateMakingStepCount > 0) {
/* 1241:1697 */           return false;
/* 1242:     */         }
/* 1243:1698 */         potentialDuplicateMakingStepCount++;
/* 1244:     */       case 40: 
/* 1245:     */       case 48: 
/* 1246:     */       case 50: 
/* 1247:1702 */         if (foundWildAttribute) {
/* 1248:1703 */           return false;
/* 1249:     */         }
/* 1250:     */         break;
/* 1251:     */       case 26: 
/* 1252:     */       case 27: 
/* 1253:     */       case 28: 
/* 1254:     */       case 29: 
/* 1255:     */       case 30: 
/* 1256:     */       case 31: 
/* 1257:     */       case 32: 
/* 1258:     */       case 33: 
/* 1259:     */       case 34: 
/* 1260:     */       case 35: 
/* 1261:     */       case 36: 
/* 1262:     */       default: 
/* 1263:1706 */         throw new RuntimeException(XPATHMessages.createXPATHMessage("ER_NULL_ERROR_HANDLER", new Object[] { Integer.toString(i) }));
/* 1264:     */       }
/* 1265:1710 */       int nextStepOpCodePos = compiler.getNextStepPos(stepOpCodePos);
/* 1266:1712 */       if (nextStepOpCodePos < 0) {
/* 1267:     */         break;
/* 1268:     */       }
/* 1269:1715 */       stepOpCodePos = nextStepOpCodePos;
/* 1270:     */     }
/* 1271:1718 */     return true;
/* 1272:     */   }
/* 1273:     */   
/* 1274:     */   public static boolean isOneStep(int analysis)
/* 1275:     */   {
/* 1276:1723 */     return (analysis & 0xFF) == 1;
/* 1277:     */   }
/* 1278:     */   
/* 1279:     */   public static int getStepCount(int analysis)
/* 1280:     */   {
/* 1281:1728 */     return analysis & 0xFF;
/* 1282:     */   }
/* 1283:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.axes.WalkerFactory
 * JD-Core Version:    0.7.0.1
 */