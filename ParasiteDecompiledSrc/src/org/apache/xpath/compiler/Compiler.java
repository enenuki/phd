/*    1:     */ package org.apache.xpath.compiler;
/*    2:     */ 
/*    3:     */ import java.io.PrintStream;
/*    4:     */ import javax.xml.transform.ErrorListener;
/*    5:     */ import javax.xml.transform.SourceLocator;
/*    6:     */ import javax.xml.transform.TransformerException;
/*    7:     */ import org.apache.xml.dtm.DTMIterator;
/*    8:     */ import org.apache.xml.utils.ObjectVector;
/*    9:     */ import org.apache.xml.utils.PrefixResolver;
/*   10:     */ import org.apache.xml.utils.QName;
/*   11:     */ import org.apache.xml.utils.SAXSourceLocator;
/*   12:     */ import org.apache.xpath.Expression;
/*   13:     */ import org.apache.xpath.axes.UnionPathIterator;
/*   14:     */ import org.apache.xpath.axes.WalkerFactory;
/*   15:     */ import org.apache.xpath.functions.FuncExtFunction;
/*   16:     */ import org.apache.xpath.functions.FuncExtFunctionAvailable;
/*   17:     */ import org.apache.xpath.functions.Function;
/*   18:     */ import org.apache.xpath.functions.WrongNumberArgsException;
/*   19:     */ import org.apache.xpath.objects.XNumber;
/*   20:     */ import org.apache.xpath.objects.XString;
/*   21:     */ import org.apache.xpath.operations.And;
/*   22:     */ import org.apache.xpath.operations.Bool;
/*   23:     */ import org.apache.xpath.operations.Div;
/*   24:     */ import org.apache.xpath.operations.Equals;
/*   25:     */ import org.apache.xpath.operations.Gt;
/*   26:     */ import org.apache.xpath.operations.Gte;
/*   27:     */ import org.apache.xpath.operations.Lt;
/*   28:     */ import org.apache.xpath.operations.Lte;
/*   29:     */ import org.apache.xpath.operations.Minus;
/*   30:     */ import org.apache.xpath.operations.Mod;
/*   31:     */ import org.apache.xpath.operations.Mult;
/*   32:     */ import org.apache.xpath.operations.Neg;
/*   33:     */ import org.apache.xpath.operations.NotEquals;
/*   34:     */ import org.apache.xpath.operations.Number;
/*   35:     */ import org.apache.xpath.operations.Operation;
/*   36:     */ import org.apache.xpath.operations.Or;
/*   37:     */ import org.apache.xpath.operations.Plus;
/*   38:     */ import org.apache.xpath.operations.UnaryOperation;
/*   39:     */ import org.apache.xpath.operations.Variable;
/*   40:     */ import org.apache.xpath.patterns.FunctionPattern;
/*   41:     */ import org.apache.xpath.patterns.StepPattern;
/*   42:     */ import org.apache.xpath.patterns.UnionPattern;
/*   43:     */ import org.apache.xpath.res.XPATHMessages;
/*   44:     */ 
/*   45:     */ public class Compiler
/*   46:     */   extends OpMap
/*   47:     */ {
/*   48:     */   public Compiler(ErrorListener errorHandler, SourceLocator locator, FunctionTable fTable)
/*   49:     */   {
/*   50:  91 */     this.m_errorHandler = errorHandler;
/*   51:  92 */     this.m_locator = locator;
/*   52:  93 */     this.m_functionTable = fTable;
/*   53:     */   }
/*   54:     */   
/*   55:     */   public Compiler()
/*   56:     */   {
/*   57: 102 */     this.m_errorHandler = null;
/*   58: 103 */     this.m_locator = null;
/*   59:     */   }
/*   60:     */   
/*   61:     */   public Expression compile(int opPos)
/*   62:     */     throws TransformerException
/*   63:     */   {
/*   64: 117 */     int op = getOp(opPos);
/*   65:     */     
/*   66: 119 */     Expression expr = null;
/*   67: 121 */     switch (op)
/*   68:     */     {
/*   69:     */     case 1: 
/*   70: 124 */       expr = compile(opPos + 2); break;
/*   71:     */     case 2: 
/*   72: 126 */       expr = or(opPos); break;
/*   73:     */     case 3: 
/*   74: 128 */       expr = and(opPos); break;
/*   75:     */     case 4: 
/*   76: 130 */       expr = notequals(opPos); break;
/*   77:     */     case 5: 
/*   78: 132 */       expr = equals(opPos); break;
/*   79:     */     case 6: 
/*   80: 134 */       expr = lte(opPos); break;
/*   81:     */     case 7: 
/*   82: 136 */       expr = lt(opPos); break;
/*   83:     */     case 8: 
/*   84: 138 */       expr = gte(opPos); break;
/*   85:     */     case 9: 
/*   86: 140 */       expr = gt(opPos); break;
/*   87:     */     case 10: 
/*   88: 142 */       expr = plus(opPos); break;
/*   89:     */     case 11: 
/*   90: 144 */       expr = minus(opPos); break;
/*   91:     */     case 12: 
/*   92: 146 */       expr = mult(opPos); break;
/*   93:     */     case 13: 
/*   94: 148 */       expr = div(opPos); break;
/*   95:     */     case 14: 
/*   96: 150 */       expr = mod(opPos); break;
/*   97:     */     case 16: 
/*   98: 154 */       expr = neg(opPos); break;
/*   99:     */     case 17: 
/*  100: 156 */       expr = string(opPos); break;
/*  101:     */     case 18: 
/*  102: 158 */       expr = bool(opPos); break;
/*  103:     */     case 19: 
/*  104: 160 */       expr = number(opPos); break;
/*  105:     */     case 20: 
/*  106: 162 */       expr = union(opPos); break;
/*  107:     */     case 21: 
/*  108: 164 */       expr = literal(opPos); break;
/*  109:     */     case 22: 
/*  110: 166 */       expr = variable(opPos); break;
/*  111:     */     case 23: 
/*  112: 168 */       expr = group(opPos); break;
/*  113:     */     case 27: 
/*  114: 170 */       expr = numberlit(opPos); break;
/*  115:     */     case 26: 
/*  116: 172 */       expr = arg(opPos); break;
/*  117:     */     case 24: 
/*  118: 174 */       expr = compileExtension(opPos); break;
/*  119:     */     case 25: 
/*  120: 176 */       expr = compileFunction(opPos); break;
/*  121:     */     case 28: 
/*  122: 178 */       expr = locationPath(opPos); break;
/*  123:     */     case 29: 
/*  124: 180 */       expr = null; break;
/*  125:     */     case 30: 
/*  126: 182 */       expr = matchPattern(opPos + 2); break;
/*  127:     */     case 31: 
/*  128: 184 */       expr = locationPathPattern(opPos); break;
/*  129:     */     case 15: 
/*  130: 186 */       error("ER_UNKNOWN_OPCODE", new Object[] { "quo" });
/*  131:     */       
/*  132: 188 */       break;
/*  133:     */     default: 
/*  134: 190 */       error("ER_UNKNOWN_OPCODE", new Object[] { Integer.toString(getOp(opPos)) });
/*  135:     */     }
/*  136: 196 */     return expr;
/*  137:     */   }
/*  138:     */   
/*  139:     */   private Expression compileOperation(Operation operation, int opPos)
/*  140:     */     throws TransformerException
/*  141:     */   {
/*  142: 213 */     int leftPos = OpMap.getFirstChildPos(opPos);
/*  143: 214 */     int rightPos = getNextOpPos(leftPos);
/*  144:     */     
/*  145: 216 */     operation.setLeftRight(compile(leftPos), compile(rightPos));
/*  146:     */     
/*  147: 218 */     return operation;
/*  148:     */   }
/*  149:     */   
/*  150:     */   private Expression compileUnary(UnaryOperation unary, int opPos)
/*  151:     */     throws TransformerException
/*  152:     */   {
/*  153: 235 */     int rightPos = OpMap.getFirstChildPos(opPos);
/*  154:     */     
/*  155: 237 */     unary.setRight(compile(rightPos));
/*  156:     */     
/*  157: 239 */     return unary;
/*  158:     */   }
/*  159:     */   
/*  160:     */   protected Expression or(int opPos)
/*  161:     */     throws TransformerException
/*  162:     */   {
/*  163: 253 */     return compileOperation(new Or(), opPos);
/*  164:     */   }
/*  165:     */   
/*  166:     */   protected Expression and(int opPos)
/*  167:     */     throws TransformerException
/*  168:     */   {
/*  169: 267 */     return compileOperation(new And(), opPos);
/*  170:     */   }
/*  171:     */   
/*  172:     */   protected Expression notequals(int opPos)
/*  173:     */     throws TransformerException
/*  174:     */   {
/*  175: 281 */     return compileOperation(new NotEquals(), opPos);
/*  176:     */   }
/*  177:     */   
/*  178:     */   protected Expression equals(int opPos)
/*  179:     */     throws TransformerException
/*  180:     */   {
/*  181: 295 */     return compileOperation(new Equals(), opPos);
/*  182:     */   }
/*  183:     */   
/*  184:     */   protected Expression lte(int opPos)
/*  185:     */     throws TransformerException
/*  186:     */   {
/*  187: 309 */     return compileOperation(new Lte(), opPos);
/*  188:     */   }
/*  189:     */   
/*  190:     */   protected Expression lt(int opPos)
/*  191:     */     throws TransformerException
/*  192:     */   {
/*  193: 323 */     return compileOperation(new Lt(), opPos);
/*  194:     */   }
/*  195:     */   
/*  196:     */   protected Expression gte(int opPos)
/*  197:     */     throws TransformerException
/*  198:     */   {
/*  199: 337 */     return compileOperation(new Gte(), opPos);
/*  200:     */   }
/*  201:     */   
/*  202:     */   protected Expression gt(int opPos)
/*  203:     */     throws TransformerException
/*  204:     */   {
/*  205: 351 */     return compileOperation(new Gt(), opPos);
/*  206:     */   }
/*  207:     */   
/*  208:     */   protected Expression plus(int opPos)
/*  209:     */     throws TransformerException
/*  210:     */   {
/*  211: 365 */     return compileOperation(new Plus(), opPos);
/*  212:     */   }
/*  213:     */   
/*  214:     */   protected Expression minus(int opPos)
/*  215:     */     throws TransformerException
/*  216:     */   {
/*  217: 379 */     return compileOperation(new Minus(), opPos);
/*  218:     */   }
/*  219:     */   
/*  220:     */   protected Expression mult(int opPos)
/*  221:     */     throws TransformerException
/*  222:     */   {
/*  223: 393 */     return compileOperation(new Mult(), opPos);
/*  224:     */   }
/*  225:     */   
/*  226:     */   protected Expression div(int opPos)
/*  227:     */     throws TransformerException
/*  228:     */   {
/*  229: 407 */     return compileOperation(new Div(), opPos);
/*  230:     */   }
/*  231:     */   
/*  232:     */   protected Expression mod(int opPos)
/*  233:     */     throws TransformerException
/*  234:     */   {
/*  235: 421 */     return compileOperation(new Mod(), opPos);
/*  236:     */   }
/*  237:     */   
/*  238:     */   protected Expression neg(int opPos)
/*  239:     */     throws TransformerException
/*  240:     */   {
/*  241: 449 */     return compileUnary(new Neg(), opPos);
/*  242:     */   }
/*  243:     */   
/*  244:     */   protected Expression string(int opPos)
/*  245:     */     throws TransformerException
/*  246:     */   {
/*  247: 463 */     return compileUnary(new org.apache.xpath.operations.String(), opPos);
/*  248:     */   }
/*  249:     */   
/*  250:     */   protected Expression bool(int opPos)
/*  251:     */     throws TransformerException
/*  252:     */   {
/*  253: 477 */     return compileUnary(new Bool(), opPos);
/*  254:     */   }
/*  255:     */   
/*  256:     */   protected Expression number(int opPos)
/*  257:     */     throws TransformerException
/*  258:     */   {
/*  259: 491 */     return compileUnary(new Number(), opPos);
/*  260:     */   }
/*  261:     */   
/*  262:     */   protected Expression literal(int opPos)
/*  263:     */   {
/*  264: 506 */     opPos = OpMap.getFirstChildPos(opPos);
/*  265:     */     
/*  266: 508 */     return (XString)getTokenQueue().elementAt(getOp(opPos));
/*  267:     */   }
/*  268:     */   
/*  269:     */   protected Expression numberlit(int opPos)
/*  270:     */   {
/*  271: 523 */     opPos = OpMap.getFirstChildPos(opPos);
/*  272:     */     
/*  273: 525 */     return (XNumber)getTokenQueue().elementAt(getOp(opPos));
/*  274:     */   }
/*  275:     */   
/*  276:     */   protected Expression variable(int opPos)
/*  277:     */     throws TransformerException
/*  278:     */   {
/*  279: 540 */     Variable var = new Variable();
/*  280:     */     
/*  281: 542 */     opPos = OpMap.getFirstChildPos(opPos);
/*  282:     */     
/*  283: 544 */     int nsPos = getOp(opPos);
/*  284: 545 */     java.lang.String namespace = -2 == nsPos ? null : (java.lang.String)getTokenQueue().elementAt(nsPos);
/*  285:     */     
/*  286:     */ 
/*  287: 548 */     java.lang.String localname = (java.lang.String)getTokenQueue().elementAt(getOp(opPos + 1));
/*  288:     */     
/*  289: 550 */     QName qname = new QName(namespace, localname);
/*  290:     */     
/*  291: 552 */     var.setQName(qname);
/*  292:     */     
/*  293: 554 */     return var;
/*  294:     */   }
/*  295:     */   
/*  296:     */   protected Expression group(int opPos)
/*  297:     */     throws TransformerException
/*  298:     */   {
/*  299: 570 */     return compile(opPos + 2);
/*  300:     */   }
/*  301:     */   
/*  302:     */   protected Expression arg(int opPos)
/*  303:     */     throws TransformerException
/*  304:     */   {
/*  305: 586 */     return compile(opPos + 2);
/*  306:     */   }
/*  307:     */   
/*  308:     */   protected Expression union(int opPos)
/*  309:     */     throws TransformerException
/*  310:     */   {
/*  311: 601 */     this.locPathDepth += 1;
/*  312:     */     try
/*  313:     */     {
/*  314: 604 */       return UnionPathIterator.createUnionIterator(this, opPos);
/*  315:     */     }
/*  316:     */     finally
/*  317:     */     {
/*  318: 608 */       this.locPathDepth -= 1;
/*  319:     */     }
/*  320:     */   }
/*  321:     */   
/*  322: 612 */   private int locPathDepth = -1;
/*  323:     */   private static final boolean DEBUG = false;
/*  324:     */   
/*  325:     */   public int getLocationPathDepth()
/*  326:     */   {
/*  327: 620 */     return this.locPathDepth;
/*  328:     */   }
/*  329:     */   
/*  330:     */   FunctionTable getFunctionTable()
/*  331:     */   {
/*  332: 628 */     return this.m_functionTable;
/*  333:     */   }
/*  334:     */   
/*  335:     */   public Expression locationPath(int opPos)
/*  336:     */     throws TransformerException
/*  337:     */   {
/*  338: 643 */     this.locPathDepth += 1;
/*  339:     */     try
/*  340:     */     {
/*  341: 646 */       DTMIterator iter = WalkerFactory.newDTMIterator(this, opPos, this.locPathDepth == 0);
/*  342: 647 */       return (Expression)iter;
/*  343:     */     }
/*  344:     */     finally
/*  345:     */     {
/*  346: 651 */       this.locPathDepth -= 1;
/*  347:     */     }
/*  348:     */   }
/*  349:     */   
/*  350:     */   public Expression predicate(int opPos)
/*  351:     */     throws TransformerException
/*  352:     */   {
/*  353: 666 */     return compile(opPos + 2);
/*  354:     */   }
/*  355:     */   
/*  356:     */   protected Expression matchPattern(int opPos)
/*  357:     */     throws TransformerException
/*  358:     */   {
/*  359: 680 */     this.locPathDepth += 1;
/*  360:     */     try
/*  361:     */     {
/*  362: 684 */       int nextOpPos = opPos;
/*  363: 687 */       for (int i = 0; getOp(nextOpPos) == 31; i++) {
/*  364: 689 */         nextOpPos = getNextOpPos(nextOpPos);
/*  365:     */       }
/*  366: 692 */       if (i == 1) {
/*  367: 693 */         return compile(opPos);
/*  368:     */       }
/*  369: 695 */       UnionPattern up = new UnionPattern();
/*  370: 696 */       StepPattern[] patterns = new StepPattern[i];
/*  371: 698 */       for (i = 0; getOp(opPos) == 31; i++)
/*  372:     */       {
/*  373: 700 */         nextOpPos = getNextOpPos(opPos);
/*  374: 701 */         patterns[i] = ((StepPattern)compile(opPos));
/*  375: 702 */         opPos = nextOpPos;
/*  376:     */       }
/*  377: 705 */       up.setPatterns(patterns);
/*  378:     */       
/*  379: 707 */       return up;
/*  380:     */     }
/*  381:     */     finally
/*  382:     */     {
/*  383: 711 */       this.locPathDepth -= 1;
/*  384:     */     }
/*  385:     */   }
/*  386:     */   
/*  387:     */   public Expression locationPathPattern(int opPos)
/*  388:     */     throws TransformerException
/*  389:     */   {
/*  390: 728 */     opPos = OpMap.getFirstChildPos(opPos);
/*  391:     */     
/*  392: 730 */     return stepPattern(opPos, 0, null);
/*  393:     */   }
/*  394:     */   
/*  395:     */   public int getWhatToShow(int opPos)
/*  396:     */   {
/*  397: 745 */     int axesType = getOp(opPos);
/*  398: 746 */     int testType = getOp(opPos + 3);
/*  399: 749 */     switch (testType)
/*  400:     */     {
/*  401:     */     case 1030: 
/*  402: 752 */       return 128;
/*  403:     */     case 1031: 
/*  404: 755 */       return 12;
/*  405:     */     case 1032: 
/*  406: 757 */       return 64;
/*  407:     */     case 1033: 
/*  408: 760 */       switch (axesType)
/*  409:     */       {
/*  410:     */       case 49: 
/*  411: 763 */         return 4096;
/*  412:     */       case 39: 
/*  413:     */       case 51: 
/*  414: 766 */         return 2;
/*  415:     */       case 38: 
/*  416:     */       case 42: 
/*  417:     */       case 48: 
/*  418: 770 */         return -1;
/*  419:     */       }
/*  420: 772 */       if (getOp(0) == 30) {
/*  421: 773 */         return -1283;
/*  422:     */       }
/*  423: 777 */       return -3;
/*  424:     */     case 35: 
/*  425: 780 */       return 1280;
/*  426:     */     case 1034: 
/*  427: 782 */       return 65536;
/*  428:     */     case 34: 
/*  429: 784 */       switch (axesType)
/*  430:     */       {
/*  431:     */       case 49: 
/*  432: 787 */         return 4096;
/*  433:     */       case 39: 
/*  434:     */       case 51: 
/*  435: 790 */         return 2;
/*  436:     */       case 52: 
/*  437:     */       case 53: 
/*  438: 795 */         return 1;
/*  439:     */       }
/*  440: 799 */       return 1;
/*  441:     */     }
/*  442: 803 */     return -1;
/*  443:     */   }
/*  444:     */   
/*  445:     */   protected StepPattern stepPattern(int opPos, int stepCount, StepPattern ancestorPattern)
/*  446:     */     throws TransformerException
/*  447:     */   {
/*  448: 826 */     int startOpPos = opPos;
/*  449: 827 */     int stepType = getOp(opPos);
/*  450: 829 */     if (-1 == stepType) {
/*  451: 831 */       return null;
/*  452:     */     }
/*  453: 834 */     boolean addMagicSelf = true;
/*  454:     */     
/*  455: 836 */     int endStep = getNextOpPos(opPos);
/*  456:     */     int argLen;
/*  457:     */     StepPattern pattern;
/*  458: 844 */     switch (stepType)
/*  459:     */     {
/*  460:     */     case 25: 
/*  461: 849 */       addMagicSelf = false;
/*  462: 850 */       argLen = getOp(opPos + 1);
/*  463: 851 */       pattern = new FunctionPattern(compileFunction(opPos), 10, 3);
/*  464: 852 */       break;
/*  465:     */     case 50: 
/*  466: 856 */       addMagicSelf = false;
/*  467: 857 */       argLen = getArgLengthOfStep(opPos);
/*  468: 858 */       opPos = OpMap.getFirstChildPosOfStep(opPos);
/*  469: 859 */       pattern = new StepPattern(1280, 10, 3);
/*  470:     */       
/*  471:     */ 
/*  472: 862 */       break;
/*  473:     */     case 51: 
/*  474: 866 */       argLen = getArgLengthOfStep(opPos);
/*  475: 867 */       opPos = OpMap.getFirstChildPosOfStep(opPos);
/*  476: 868 */       pattern = new StepPattern(2, getStepNS(startOpPos), getStepLocalName(startOpPos), 10, 2);
/*  477:     */       
/*  478:     */ 
/*  479:     */ 
/*  480: 872 */       break;
/*  481:     */     case 52: 
/*  482: 876 */       argLen = getArgLengthOfStep(opPos);
/*  483: 877 */       opPos = OpMap.getFirstChildPosOfStep(opPos);
/*  484: 878 */       int what = getWhatToShow(startOpPos);
/*  485: 880 */       if (1280 == what) {
/*  486: 881 */         addMagicSelf = false;
/*  487:     */       }
/*  488: 882 */       pattern = new StepPattern(getWhatToShow(startOpPos), getStepNS(startOpPos), getStepLocalName(startOpPos), 0, 3);
/*  489:     */       
/*  490:     */ 
/*  491:     */ 
/*  492: 886 */       break;
/*  493:     */     case 53: 
/*  494: 890 */       argLen = getArgLengthOfStep(opPos);
/*  495: 891 */       opPos = OpMap.getFirstChildPosOfStep(opPos);
/*  496: 892 */       pattern = new StepPattern(getWhatToShow(startOpPos), getStepNS(startOpPos), getStepLocalName(startOpPos), 10, 3);
/*  497:     */       
/*  498:     */ 
/*  499:     */ 
/*  500: 896 */       break;
/*  501:     */     default: 
/*  502: 898 */       error("ER_UNKNOWN_MATCH_OPERATION", null);
/*  503:     */       
/*  504: 900 */       return null;
/*  505:     */     }
/*  506: 903 */     pattern.setPredicates(getCompiledPredicates(opPos + argLen));
/*  507: 904 */     if (null != ancestorPattern) {
/*  508: 929 */       pattern.setRelativePathPattern(ancestorPattern);
/*  509:     */     }
/*  510: 932 */     StepPattern relativePathPattern = stepPattern(endStep, stepCount + 1, pattern);
/*  511:     */     
/*  512:     */ 
/*  513: 935 */     return null != relativePathPattern ? relativePathPattern : pattern;
/*  514:     */   }
/*  515:     */   
/*  516:     */   public Expression[] getCompiledPredicates(int opPos)
/*  517:     */     throws TransformerException
/*  518:     */   {
/*  519: 951 */     int count = countPredicates(opPos);
/*  520: 953 */     if (count > 0)
/*  521:     */     {
/*  522: 955 */       Expression[] predicates = new Expression[count];
/*  523:     */       
/*  524: 957 */       compilePredicates(opPos, predicates);
/*  525:     */       
/*  526: 959 */       return predicates;
/*  527:     */     }
/*  528: 962 */     return null;
/*  529:     */   }
/*  530:     */   
/*  531:     */   public int countPredicates(int opPos)
/*  532:     */     throws TransformerException
/*  533:     */   {
/*  534: 977 */     int count = 0;
/*  535: 979 */     while (29 == getOp(opPos))
/*  536:     */     {
/*  537: 981 */       count++;
/*  538:     */       
/*  539: 983 */       opPos = getNextOpPos(opPos);
/*  540:     */     }
/*  541: 986 */     return count;
/*  542:     */   }
/*  543:     */   
/*  544:     */   private void compilePredicates(int opPos, Expression[] predicates)
/*  545:     */     throws TransformerException
/*  546:     */   {
/*  547:1002 */     for (int i = 0; 29 == getOp(opPos); i++)
/*  548:     */     {
/*  549:1004 */       predicates[i] = predicate(opPos);
/*  550:1005 */       opPos = getNextOpPos(opPos);
/*  551:     */     }
/*  552:     */   }
/*  553:     */   
/*  554:     */   Expression compileFunction(int opPos)
/*  555:     */     throws TransformerException
/*  556:     */   {
/*  557:1021 */     int endFunc = opPos + getOp(opPos + 1) - 1;
/*  558:     */     
/*  559:1023 */     opPos = OpMap.getFirstChildPos(opPos);
/*  560:     */     
/*  561:1025 */     int funcID = getOp(opPos);
/*  562:     */     
/*  563:1027 */     opPos++;
/*  564:1029 */     if (-1 != funcID)
/*  565:     */     {
/*  566:1031 */       Function func = this.m_functionTable.getFunction(funcID);
/*  567:1038 */       if ((func instanceof FuncExtFunctionAvailable)) {
/*  568:1039 */         ((FuncExtFunctionAvailable)func).setFunctionTable(this.m_functionTable);
/*  569:     */       }
/*  570:1041 */       func.postCompileStep(this);
/*  571:     */       try
/*  572:     */       {
/*  573:1045 */         int i = 0;
/*  574:1047 */         for (int p = opPos; p < endFunc; i++)
/*  575:     */         {
/*  576:1052 */           func.setArg(compile(p), i);p = getNextOpPos(p);
/*  577:     */         }
/*  578:1055 */         func.checkNumberArgs(i);
/*  579:     */       }
/*  580:     */       catch (WrongNumberArgsException wnae)
/*  581:     */       {
/*  582:1059 */         java.lang.String name = this.m_functionTable.getFunctionName(funcID);
/*  583:     */         
/*  584:1061 */         this.m_errorHandler.fatalError(new TransformerException(XPATHMessages.createXPATHMessage("ER_ONLY_ALLOWS", new Object[] { name, wnae.getMessage() }), this.m_locator));
/*  585:     */       }
/*  586:1067 */       return func;
/*  587:     */     }
/*  588:1071 */     error("ER_FUNCTION_TOKEN_NOT_FOUND", null);
/*  589:     */     
/*  590:1073 */     return null;
/*  591:     */   }
/*  592:     */   
/*  593:1078 */   private static long s_nextMethodId = 0L;
/*  594:     */   
/*  595:     */   private synchronized long getNextMethodId()
/*  596:     */   {
/*  597:1085 */     if (s_nextMethodId == 9223372036854775807L) {
/*  598:1086 */       s_nextMethodId = 0L;
/*  599:     */     }
/*  600:1088 */     return s_nextMethodId++;
/*  601:     */   }
/*  602:     */   
/*  603:     */   private Expression compileExtension(int opPos)
/*  604:     */     throws TransformerException
/*  605:     */   {
/*  606:1104 */     int endExtFunc = opPos + getOp(opPos + 1) - 1;
/*  607:     */     
/*  608:1106 */     opPos = OpMap.getFirstChildPos(opPos);
/*  609:     */     
/*  610:1108 */     java.lang.String ns = (java.lang.String)getTokenQueue().elementAt(getOp(opPos));
/*  611:     */     
/*  612:1110 */     opPos++;
/*  613:     */     
/*  614:1112 */     java.lang.String funcName = (java.lang.String)getTokenQueue().elementAt(getOp(opPos));
/*  615:     */     
/*  616:     */ 
/*  617:1115 */     opPos++;
/*  618:     */     
/*  619:     */ 
/*  620:     */ 
/*  621:     */ 
/*  622:     */ 
/*  623:1121 */     Function extension = new FuncExtFunction(ns, funcName, java.lang.String.valueOf(getNextMethodId()));
/*  624:     */     try
/*  625:     */     {
/*  626:1125 */       int i = 0;
/*  627:1127 */       while (opPos < endExtFunc)
/*  628:     */       {
/*  629:1129 */         int nextOpPos = getNextOpPos(opPos);
/*  630:     */         
/*  631:1131 */         extension.setArg(compile(opPos), i);
/*  632:     */         
/*  633:1133 */         opPos = nextOpPos;
/*  634:     */         
/*  635:1135 */         i++;
/*  636:     */       }
/*  637:     */     }
/*  638:     */     catch (WrongNumberArgsException wnae) {}
/*  639:1143 */     return extension;
/*  640:     */   }
/*  641:     */   
/*  642:     */   public void warn(java.lang.String msg, Object[] args)
/*  643:     */     throws TransformerException
/*  644:     */   {
/*  645:1161 */     java.lang.String fmsg = XPATHMessages.createXPATHWarning(msg, args);
/*  646:1163 */     if (null != this.m_errorHandler) {
/*  647:1165 */       this.m_errorHandler.warning(new TransformerException(fmsg, this.m_locator));
/*  648:     */     } else {
/*  649:1169 */       System.out.println(fmsg + "; file " + this.m_locator.getSystemId() + "; line " + this.m_locator.getLineNumber() + "; column " + this.m_locator.getColumnNumber());
/*  650:     */     }
/*  651:     */   }
/*  652:     */   
/*  653:     */   public void assertion(boolean b, java.lang.String msg)
/*  654:     */   {
/*  655:1188 */     if (!b)
/*  656:     */     {
/*  657:1190 */       java.lang.String fMsg = XPATHMessages.createXPATHMessage("ER_INCORRECT_PROGRAMMER_ASSERTION", new Object[] { msg });
/*  658:     */       
/*  659:     */ 
/*  660:     */ 
/*  661:1194 */       throw new RuntimeException(fMsg);
/*  662:     */     }
/*  663:     */   }
/*  664:     */   
/*  665:     */   public void error(java.lang.String msg, Object[] args)
/*  666:     */     throws TransformerException
/*  667:     */   {
/*  668:1214 */     java.lang.String fmsg = XPATHMessages.createXPATHMessage(msg, args);
/*  669:1217 */     if (null != this.m_errorHandler) {
/*  670:1219 */       this.m_errorHandler.fatalError(new TransformerException(fmsg, this.m_locator));
/*  671:     */     } else {
/*  672:1228 */       throw new TransformerException(fmsg, (SAXSourceLocator)this.m_locator);
/*  673:     */     }
/*  674:     */   }
/*  675:     */   
/*  676:1235 */   private PrefixResolver m_currentPrefixResolver = null;
/*  677:     */   ErrorListener m_errorHandler;
/*  678:     */   SourceLocator m_locator;
/*  679:     */   private FunctionTable m_functionTable;
/*  680:     */   
/*  681:     */   public PrefixResolver getNamespaceContext()
/*  682:     */   {
/*  683:1244 */     return this.m_currentPrefixResolver;
/*  684:     */   }
/*  685:     */   
/*  686:     */   public void setNamespaceContext(PrefixResolver pr)
/*  687:     */   {
/*  688:1254 */     this.m_currentPrefixResolver = pr;
/*  689:     */   }
/*  690:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xpath.compiler.Compiler
 * JD-Core Version:    0.7.0.1
 */