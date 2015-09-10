/*    1:     */ package net.sourceforge.htmlunit.corejs.javascript;
/*    2:     */ 
/*    3:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.AstRoot;
/*    4:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.FunctionNode;
/*    5:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.Jump;
/*    6:     */ import net.sourceforge.htmlunit.corejs.javascript.ast.ScriptNode;
/*    7:     */ 
/*    8:     */ class CodeGenerator
/*    9:     */   extends Icode
/*   10:     */ {
/*   11:     */   private static final int MIN_LABEL_TABLE_SIZE = 32;
/*   12:     */   private static final int MIN_FIXUP_TABLE_SIZE = 40;
/*   13:     */   private CompilerEnvirons compilerEnv;
/*   14:     */   private boolean itsInFunctionFlag;
/*   15:     */   private boolean itsInTryFlag;
/*   16:     */   private InterpreterData itsData;
/*   17:     */   private ScriptNode scriptOrFn;
/*   18:     */   private int iCodeTop;
/*   19:     */   private int stackDepth;
/*   20:     */   private int lineNumber;
/*   21:     */   private int doubleTableTop;
/*   22:  76 */   private ObjToIntMap strings = new ObjToIntMap(20);
/*   23:     */   private int localTop;
/*   24:     */   private int[] labelTable;
/*   25:     */   private int labelTableTop;
/*   26:     */   private long[] fixupTable;
/*   27:     */   private int fixupTableTop;
/*   28:  84 */   private ObjArray literalIds = new ObjArray();
/*   29:     */   private int exceptionTableTop;
/*   30:     */   private static final int ECF_TAIL = 1;
/*   31:     */   
/*   32:     */   public InterpreterData compile(CompilerEnvirons compilerEnv, ScriptNode tree, String encodedSource, boolean returnFunction)
/*   33:     */   {
/*   34:  96 */     this.compilerEnv = compilerEnv;
/*   35:     */     
/*   36:     */ 
/*   37:     */ 
/*   38:     */ 
/*   39:     */ 
/*   40:     */ 
/*   41: 103 */     new NodeTransformer().transform(tree);
/*   42: 110 */     if (returnFunction) {
/*   43: 111 */       this.scriptOrFn = tree.getFunctionNode(0);
/*   44:     */     } else {
/*   45: 113 */       this.scriptOrFn = tree;
/*   46:     */     }
/*   47: 115 */     this.itsData = new InterpreterData(compilerEnv.getLanguageVersion(), this.scriptOrFn.getSourceName(), encodedSource, ((AstRoot)tree).isInStrictMode());
/*   48:     */     
/*   49:     */ 
/*   50:     */ 
/*   51: 119 */     this.itsData.topLevel = true;
/*   52: 121 */     if (returnFunction) {
/*   53: 122 */       generateFunctionICode();
/*   54:     */     } else {
/*   55: 124 */       generateICodeFromTree(this.scriptOrFn);
/*   56:     */     }
/*   57: 126 */     return this.itsData;
/*   58:     */   }
/*   59:     */   
/*   60:     */   private void generateFunctionICode()
/*   61:     */   {
/*   62: 131 */     this.itsInFunctionFlag = true;
/*   63:     */     
/*   64: 133 */     FunctionNode theFunction = (FunctionNode)this.scriptOrFn;
/*   65:     */     
/*   66: 135 */     this.itsData.itsFunctionType = theFunction.getFunctionType();
/*   67: 136 */     this.itsData.itsNeedsActivation = theFunction.requiresActivation();
/*   68: 137 */     if (theFunction.getFunctionName() != null) {
/*   69: 138 */       this.itsData.itsName = theFunction.getName();
/*   70:     */     }
/*   71: 140 */     if ((!theFunction.getIgnoreDynamicScope()) && 
/*   72: 141 */       (this.compilerEnv.isUseDynamicScope())) {
/*   73: 142 */       this.itsData.useDynamicScope = true;
/*   74:     */     }
/*   75: 145 */     if (theFunction.isGenerator())
/*   76:     */     {
/*   77: 146 */       addIcode(-62);
/*   78: 147 */       addUint16(theFunction.getBaseLineno() & 0xFFFF);
/*   79:     */     }
/*   80: 150 */     generateICodeFromTree(theFunction.getLastChild());
/*   81:     */   }
/*   82:     */   
/*   83:     */   private void generateICodeFromTree(Node tree)
/*   84:     */   {
/*   85: 155 */     generateNestedFunctions();
/*   86:     */     
/*   87: 157 */     generateRegExpLiterals();
/*   88:     */     
/*   89: 159 */     visitStatement(tree, 0);
/*   90: 160 */     fixLabelGotos();
/*   91: 162 */     if (this.itsData.itsFunctionType == 0) {
/*   92: 163 */       addToken(64);
/*   93:     */     }
/*   94: 166 */     if (this.itsData.itsICode.length != this.iCodeTop)
/*   95:     */     {
/*   96: 169 */       byte[] tmp = new byte[this.iCodeTop];
/*   97: 170 */       System.arraycopy(this.itsData.itsICode, 0, tmp, 0, this.iCodeTop);
/*   98: 171 */       this.itsData.itsICode = tmp;
/*   99:     */     }
/*  100: 173 */     if (this.strings.size() == 0)
/*  101:     */     {
/*  102: 174 */       this.itsData.itsStringTable = null;
/*  103:     */     }
/*  104:     */     else
/*  105:     */     {
/*  106: 176 */       this.itsData.itsStringTable = new String[this.strings.size()];
/*  107: 177 */       ObjToIntMap.Iterator iter = this.strings.newIterator();
/*  108: 178 */       for (iter.start(); !iter.done(); iter.next())
/*  109:     */       {
/*  110: 179 */         String str = (String)iter.getKey();
/*  111: 180 */         int index = iter.getValue();
/*  112: 181 */         if (this.itsData.itsStringTable[index] != null) {
/*  113: 181 */           Kit.codeBug();
/*  114:     */         }
/*  115: 182 */         this.itsData.itsStringTable[index] = str;
/*  116:     */       }
/*  117:     */     }
/*  118: 185 */     if (this.doubleTableTop == 0)
/*  119:     */     {
/*  120: 186 */       this.itsData.itsDoubleTable = null;
/*  121:     */     }
/*  122: 187 */     else if (this.itsData.itsDoubleTable.length != this.doubleTableTop)
/*  123:     */     {
/*  124: 188 */       double[] tmp = new double[this.doubleTableTop];
/*  125: 189 */       System.arraycopy(this.itsData.itsDoubleTable, 0, tmp, 0, this.doubleTableTop);
/*  126:     */       
/*  127: 191 */       this.itsData.itsDoubleTable = tmp;
/*  128:     */     }
/*  129: 193 */     if ((this.exceptionTableTop != 0) && (this.itsData.itsExceptionTable.length != this.exceptionTableTop))
/*  130:     */     {
/*  131: 196 */       int[] tmp = new int[this.exceptionTableTop];
/*  132: 197 */       System.arraycopy(this.itsData.itsExceptionTable, 0, tmp, 0, this.exceptionTableTop);
/*  133:     */       
/*  134: 199 */       this.itsData.itsExceptionTable = tmp;
/*  135:     */     }
/*  136: 202 */     this.itsData.itsMaxVars = this.scriptOrFn.getParamAndVarCount();
/*  137:     */     
/*  138:     */ 
/*  139: 205 */     this.itsData.itsMaxFrameArray = (this.itsData.itsMaxVars + this.itsData.itsMaxLocals + this.itsData.itsMaxStack);
/*  140:     */     
/*  141:     */ 
/*  142:     */ 
/*  143: 209 */     this.itsData.argNames = this.scriptOrFn.getParamAndVarNames();
/*  144: 210 */     this.itsData.argIsConst = this.scriptOrFn.getParamAndVarConst();
/*  145: 211 */     this.itsData.argCount = this.scriptOrFn.getParamCount();
/*  146:     */     
/*  147: 213 */     this.itsData.encodedSourceStart = this.scriptOrFn.getEncodedSourceStart();
/*  148: 214 */     this.itsData.encodedSourceEnd = this.scriptOrFn.getEncodedSourceEnd();
/*  149: 216 */     if (this.literalIds.size() != 0) {
/*  150: 217 */       this.itsData.literalIds = this.literalIds.toArray();
/*  151:     */     }
/*  152:     */   }
/*  153:     */   
/*  154:     */   private void generateNestedFunctions()
/*  155:     */   {
/*  156: 225 */     int functionCount = this.scriptOrFn.getFunctionCount();
/*  157: 226 */     if (functionCount == 0) {
/*  158: 226 */       return;
/*  159:     */     }
/*  160: 228 */     InterpreterData[] array = new InterpreterData[functionCount];
/*  161: 229 */     for (int i = 0; i != functionCount; i++)
/*  162:     */     {
/*  163: 230 */       FunctionNode fn = this.scriptOrFn.getFunctionNode(i);
/*  164: 231 */       CodeGenerator gen = new CodeGenerator();
/*  165: 232 */       gen.compilerEnv = this.compilerEnv;
/*  166: 233 */       gen.scriptOrFn = fn;
/*  167: 234 */       gen.itsData = new InterpreterData(this.itsData);
/*  168: 235 */       gen.generateFunctionICode();
/*  169: 236 */       array[i] = gen.itsData;
/*  170:     */     }
/*  171: 238 */     this.itsData.itsNestedFunctions = array;
/*  172:     */   }
/*  173:     */   
/*  174:     */   private void generateRegExpLiterals()
/*  175:     */   {
/*  176: 243 */     int N = this.scriptOrFn.getRegexpCount();
/*  177: 244 */     if (N == 0) {
/*  178: 244 */       return;
/*  179:     */     }
/*  180: 246 */     Context cx = Context.getContext();
/*  181: 247 */     RegExpProxy rep = ScriptRuntime.checkRegExpProxy(cx);
/*  182: 248 */     Object[] array = new Object[N];
/*  183: 249 */     for (int i = 0; i != N; i++)
/*  184:     */     {
/*  185: 250 */       String string = this.scriptOrFn.getRegexpString(i);
/*  186: 251 */       String flags = this.scriptOrFn.getRegexpFlags(i);
/*  187: 252 */       array[i] = rep.compileRegExp(cx, string, flags);
/*  188:     */     }
/*  189: 254 */     this.itsData.itsRegExpLiterals = array;
/*  190:     */   }
/*  191:     */   
/*  192:     */   private void updateLineNumber(Node node)
/*  193:     */   {
/*  194: 259 */     int lineno = node.getLineno();
/*  195: 260 */     if ((lineno != this.lineNumber) && (lineno >= 0))
/*  196:     */     {
/*  197: 261 */       if (this.itsData.firstLinePC < 0) {
/*  198: 262 */         this.itsData.firstLinePC = lineno;
/*  199:     */       }
/*  200: 264 */       this.lineNumber = lineno;
/*  201: 265 */       addIcode(-26);
/*  202: 266 */       addUint16(lineno & 0xFFFF);
/*  203:     */     }
/*  204:     */   }
/*  205:     */   
/*  206:     */   private RuntimeException badTree(Node node)
/*  207:     */   {
/*  208: 272 */     throw new RuntimeException(node.toString());
/*  209:     */   }
/*  210:     */   
/*  211:     */   private void visitStatement(Node node, int initialStackDepth)
/*  212:     */   {
/*  213: 277 */     int type = node.getType();
/*  214: 278 */     Node child = node.getFirstChild();
/*  215: 279 */     switch (type)
/*  216:     */     {
/*  217:     */     case 109: 
/*  218: 283 */       int fnIndex = node.getExistingIntProp(1);
/*  219: 284 */       int fnType = this.scriptOrFn.getFunctionNode(fnIndex).getFunctionType();
/*  220: 292 */       if (fnType == 3) {
/*  221: 293 */         addIndexOp(-20, fnIndex);
/*  222: 295 */       } else if (fnType != 1) {
/*  223: 296 */         throw Kit.codeBug();
/*  224:     */       }
/*  225: 304 */       if (!this.itsInFunctionFlag)
/*  226:     */       {
/*  227: 305 */         addIndexOp(-19, fnIndex);
/*  228: 306 */         stackChange(1);
/*  229: 307 */         addIcode(-5);
/*  230: 308 */         stackChange(-1);
/*  231:     */       }
/*  232: 311 */       break;
/*  233:     */     case 123: 
/*  234:     */     case 128: 
/*  235:     */     case 129: 
/*  236:     */     case 130: 
/*  237:     */     case 132: 
/*  238: 318 */       updateLineNumber(node);
/*  239:     */     case 136: 
/*  240:     */     case 2: 
/*  241:     */     case 3: 
/*  242:     */     case 141: 
/*  243:     */     case 160: 
/*  244:     */     case 114: 
/*  245:     */     case 131: 
/*  246:     */     case 6: 
/*  247:     */     case 7: 
/*  248:     */     case 5: 
/*  249:     */     case 135: 
/*  250:     */     case 125: 
/*  251:     */     case 133: 
/*  252:     */     case 134: 
/*  253:     */     case 81: 
/*  254:     */     case 57: 
/*  255:     */     case 50: 
/*  256:     */     case 51: 
/*  257:     */     case 4: 
/*  258:     */     case 64: 
/*  259:     */     case 58: 
/*  260:     */     case 59: 
/*  261:     */     case 60: 
/*  262:     */     case -62: 
/*  263:     */     default: 
/*  264: 321 */       while (child != null)
/*  265:     */       {
/*  266: 322 */         visitStatement(child, initialStackDepth);
/*  267: 323 */         child = child.getNext(); continue;
/*  268:     */         
/*  269:     */ 
/*  270:     */ 
/*  271:     */ 
/*  272: 328 */         visitExpression(child, 0);
/*  273: 329 */         addToken(2);
/*  274: 330 */         stackChange(-1);
/*  275: 331 */         break;
/*  276:     */         
/*  277:     */ 
/*  278: 334 */         addToken(3);
/*  279: 335 */         break;
/*  280:     */         
/*  281:     */ 
/*  282:     */ 
/*  283: 339 */         int local = allocLocal();
/*  284: 340 */         node.putIntProp(2, local);
/*  285: 341 */         updateLineNumber(node);
/*  286: 342 */         while (child != null)
/*  287:     */         {
/*  288: 343 */           visitStatement(child, initialStackDepth);
/*  289: 344 */           child = child.getNext();
/*  290:     */         }
/*  291: 346 */         addIndexOp(-56, local);
/*  292: 347 */         releaseLocal(local);
/*  293:     */         
/*  294: 349 */         break;
/*  295:     */         
/*  296:     */ 
/*  297: 352 */         addIcode(-64);
/*  298: 353 */         break;
/*  299:     */         
/*  300:     */ 
/*  301: 356 */         updateLineNumber(node);
/*  302:     */         
/*  303:     */ 
/*  304:     */ 
/*  305: 360 */         visitExpression(child, 0);
/*  306: 361 */         for (Jump caseNode = (Jump)child.getNext(); caseNode != null; caseNode = (Jump)caseNode.getNext())
/*  307:     */         {
/*  308: 365 */           if (caseNode.getType() != 115) {
/*  309: 366 */             throw badTree(caseNode);
/*  310:     */           }
/*  311: 367 */           Node test = caseNode.getFirstChild();
/*  312: 368 */           addIcode(-1);
/*  313: 369 */           stackChange(1);
/*  314: 370 */           visitExpression(test, 0);
/*  315: 371 */           addToken(46);
/*  316: 372 */           stackChange(-1);
/*  317:     */           
/*  318:     */ 
/*  319: 375 */           addGoto(caseNode.target, -6);
/*  320: 376 */           stackChange(-1);
/*  321:     */         }
/*  322: 378 */         addIcode(-4);
/*  323: 379 */         stackChange(-1);
/*  324:     */         
/*  325: 381 */         break;
/*  326:     */         
/*  327:     */ 
/*  328: 384 */         markTargetLabel(node);
/*  329: 385 */         break;
/*  330:     */         
/*  331:     */ 
/*  332:     */ 
/*  333:     */ 
/*  334: 390 */         Node target = ((Jump)node).target;
/*  335: 391 */         visitExpression(child, 0);
/*  336: 392 */         addGoto(target, type);
/*  337: 393 */         stackChange(-1);
/*  338:     */         
/*  339: 395 */         break;
/*  340:     */         
/*  341:     */ 
/*  342:     */ 
/*  343: 399 */         Node target = ((Jump)node).target;
/*  344: 400 */         addGoto(target, type);
/*  345:     */         
/*  346: 402 */         break;
/*  347:     */         
/*  348:     */ 
/*  349:     */ 
/*  350: 406 */         Node target = ((Jump)node).target;
/*  351: 407 */         addGoto(target, -23);
/*  352:     */         
/*  353: 409 */         break;
/*  354:     */         
/*  355:     */ 
/*  356:     */ 
/*  357:     */ 
/*  358: 414 */         stackChange(1);
/*  359: 415 */         int finallyRegister = getLocalBlockRef(node);
/*  360: 416 */         addIndexOp(-24, finallyRegister);
/*  361: 417 */         stackChange(-1);
/*  362: 418 */         while (child != null)
/*  363:     */         {
/*  364: 419 */           visitStatement(child, initialStackDepth);
/*  365: 420 */           child = child.getNext();
/*  366:     */         }
/*  367: 422 */         addIndexOp(-25, finallyRegister);
/*  368:     */         
/*  369: 424 */         break;
/*  370:     */         
/*  371:     */ 
/*  372:     */ 
/*  373: 428 */         updateLineNumber(node);
/*  374: 429 */         visitExpression(child, 0);
/*  375: 430 */         addIcode(type == 133 ? -4 : -5);
/*  376: 431 */         stackChange(-1);
/*  377: 432 */         break;
/*  378:     */         
/*  379:     */ 
/*  380:     */ 
/*  381: 436 */         Jump tryNode = (Jump)node;
/*  382: 437 */         int exceptionObjectLocal = getLocalBlockRef(tryNode);
/*  383: 438 */         int scopeLocal = allocLocal();
/*  384:     */         
/*  385: 440 */         addIndexOp(-13, scopeLocal);
/*  386:     */         
/*  387: 442 */         int tryStart = this.iCodeTop;
/*  388: 443 */         boolean savedFlag = this.itsInTryFlag;
/*  389: 444 */         this.itsInTryFlag = true;
/*  390: 445 */         while (child != null)
/*  391:     */         {
/*  392: 446 */           visitStatement(child, initialStackDepth);
/*  393: 447 */           child = child.getNext();
/*  394:     */         }
/*  395: 449 */         this.itsInTryFlag = savedFlag;
/*  396:     */         
/*  397: 451 */         Node catchTarget = tryNode.target;
/*  398: 452 */         if (catchTarget != null)
/*  399:     */         {
/*  400: 453 */           int catchStartPC = this.labelTable[getTargetLabel(catchTarget)];
/*  401:     */           
/*  402: 455 */           addExceptionHandler(tryStart, catchStartPC, catchStartPC, false, exceptionObjectLocal, scopeLocal);
/*  403:     */         }
/*  404: 459 */         Node finallyTarget = tryNode.getFinally();
/*  405: 460 */         if (finallyTarget != null)
/*  406:     */         {
/*  407: 461 */           int finallyStartPC = this.labelTable[getTargetLabel(finallyTarget)];
/*  408:     */           
/*  409: 463 */           addExceptionHandler(tryStart, finallyStartPC, finallyStartPC, true, exceptionObjectLocal, scopeLocal);
/*  410:     */         }
/*  411: 468 */         addIndexOp(-56, scopeLocal);
/*  412: 469 */         releaseLocal(scopeLocal);
/*  413:     */         
/*  414: 471 */         break;
/*  415:     */         
/*  416:     */ 
/*  417:     */ 
/*  418: 475 */         int localIndex = getLocalBlockRef(node);
/*  419: 476 */         int scopeIndex = node.getExistingIntProp(14);
/*  420: 477 */         String name = child.getString();
/*  421: 478 */         child = child.getNext();
/*  422: 479 */         visitExpression(child, 0);
/*  423: 480 */         addStringPrefix(name);
/*  424: 481 */         addIndexPrefix(localIndex);
/*  425: 482 */         addToken(57);
/*  426: 483 */         addUint8(scopeIndex != 0 ? 1 : 0);
/*  427: 484 */         stackChange(-1);
/*  428:     */         
/*  429: 486 */         break;
/*  430:     */         
/*  431:     */ 
/*  432: 489 */         updateLineNumber(node);
/*  433: 490 */         visitExpression(child, 0);
/*  434: 491 */         addToken(50);
/*  435: 492 */         addUint16(this.lineNumber & 0xFFFF);
/*  436: 493 */         stackChange(-1);
/*  437: 494 */         break;
/*  438:     */         
/*  439:     */ 
/*  440: 497 */         updateLineNumber(node);
/*  441: 498 */         addIndexOp(51, getLocalBlockRef(node));
/*  442: 499 */         break;
/*  443:     */         
/*  444:     */ 
/*  445: 502 */         updateLineNumber(node);
/*  446: 503 */         if (node.getIntProp(20, 0) != 0)
/*  447:     */         {
/*  448: 505 */           addIcode(-63);
/*  449: 506 */           addUint16(this.lineNumber & 0xFFFF);
/*  450:     */         }
/*  451: 507 */         else if (child != null)
/*  452:     */         {
/*  453: 508 */           visitExpression(child, 1);
/*  454: 509 */           addToken(4);
/*  455: 510 */           stackChange(-1);
/*  456:     */         }
/*  457:     */         else
/*  458:     */         {
/*  459: 512 */           addIcode(-22);
/*  460:     */           
/*  461: 514 */           break;
/*  462:     */           
/*  463:     */ 
/*  464: 517 */           updateLineNumber(node);
/*  465: 518 */           addToken(64);
/*  466: 519 */           break;
/*  467:     */           
/*  468:     */ 
/*  469:     */ 
/*  470:     */ 
/*  471: 524 */           visitExpression(child, 0);
/*  472: 525 */           addIndexOp(type, getLocalBlockRef(node));
/*  473: 526 */           stackChange(-1);
/*  474: 527 */           break;
/*  475:     */           
/*  476:     */ 
/*  477: 530 */           break;
/*  478:     */           
/*  479:     */ 
/*  480: 533 */           throw badTree(node);
/*  481:     */         }
/*  482:     */       }
/*  483:     */     }
/*  484: 536 */     if (this.stackDepth != initialStackDepth) {
/*  485: 537 */       throw Kit.codeBug();
/*  486:     */     }
/*  487:     */   }
/*  488:     */   
/*  489:     */   private void visitExpression(Node node, int contextFlags)
/*  490:     */   {
/*  491: 543 */     int type = node.getType();
/*  492: 544 */     Node child = node.getFirstChild();
/*  493: 545 */     int savedStackDepth = this.stackDepth;
/*  494: 546 */     switch (type)
/*  495:     */     {
/*  496:     */     case 109: 
/*  497: 550 */       int fnIndex = node.getExistingIntProp(1);
/*  498: 551 */       FunctionNode fn = this.scriptOrFn.getFunctionNode(fnIndex);
/*  499: 553 */       if (fn.getFunctionType() != 2) {
/*  500: 554 */         throw Kit.codeBug();
/*  501:     */       }
/*  502: 556 */       addIndexOp(-19, fnIndex);
/*  503: 557 */       stackChange(1);
/*  504:     */       
/*  505: 559 */       break;
/*  506:     */     case 54: 
/*  507: 563 */       int localIndex = getLocalBlockRef(node);
/*  508: 564 */       addIndexOp(54, localIndex);
/*  509: 565 */       stackChange(1);
/*  510:     */       
/*  511: 567 */       break;
/*  512:     */     case 89: 
/*  513: 571 */       Node lastChild = node.getLastChild();
/*  514: 572 */       while (child != lastChild)
/*  515:     */       {
/*  516: 573 */         visitExpression(child, 0);
/*  517: 574 */         addIcode(-4);
/*  518: 575 */         stackChange(-1);
/*  519: 576 */         child = child.getNext();
/*  520:     */       }
/*  521: 579 */       visitExpression(child, contextFlags & 0x1);
/*  522:     */       
/*  523: 581 */       break;
/*  524:     */     case 138: 
/*  525: 586 */       stackChange(1);
/*  526: 587 */       break;
/*  527:     */     case 30: 
/*  528:     */     case 38: 
/*  529:     */     case 70: 
/*  530: 593 */       if (type == 30) {
/*  531: 594 */         visitExpression(child, 0);
/*  532:     */       } else {
/*  533: 596 */         generateCallFunAndThis(child);
/*  534:     */       }
/*  535: 598 */       int argCount = 0;
/*  536: 599 */       while ((child = child.getNext()) != null)
/*  537:     */       {
/*  538: 600 */         visitExpression(child, 0);
/*  539: 601 */         argCount++;
/*  540:     */       }
/*  541: 603 */       int callType = node.getIntProp(10, 0);
/*  542: 605 */       if (callType != 0)
/*  543:     */       {
/*  544: 607 */         addIndexOp(-21, argCount);
/*  545: 608 */         addUint8(callType);
/*  546: 609 */         addUint8(type == 30 ? 1 : 0);
/*  547: 610 */         addUint16(this.lineNumber & 0xFFFF);
/*  548:     */       }
/*  549:     */       else
/*  550:     */       {
/*  551: 615 */         if ((type == 38) && ((contextFlags & 0x1) != 0) && (!this.compilerEnv.isGenerateDebugInfo()) && (!this.itsInTryFlag)) {
/*  552: 618 */           type = -55;
/*  553:     */         }
/*  554: 620 */         addIndexOp(type, argCount);
/*  555:     */       }
/*  556: 623 */       if (type == 30) {
/*  557: 625 */         stackChange(-argCount);
/*  558:     */       } else {
/*  559: 629 */         stackChange(-1 - argCount);
/*  560:     */       }
/*  561: 631 */       if (argCount > this.itsData.itsMaxCalleeArgs) {
/*  562: 632 */         this.itsData.itsMaxCalleeArgs = argCount;
/*  563:     */       }
/*  564: 635 */       break;
/*  565:     */     case 104: 
/*  566:     */     case 105: 
/*  567: 640 */       visitExpression(child, 0);
/*  568: 641 */       addIcode(-1);
/*  569: 642 */       stackChange(1);
/*  570: 643 */       int afterSecondJumpStart = this.iCodeTop;
/*  571: 644 */       int jump = type == 105 ? 7 : 6;
/*  572: 645 */       addGotoOp(jump);
/*  573: 646 */       stackChange(-1);
/*  574: 647 */       addIcode(-4);
/*  575: 648 */       stackChange(-1);
/*  576: 649 */       child = child.getNext();
/*  577:     */       
/*  578: 651 */       visitExpression(child, contextFlags & 0x1);
/*  579: 652 */       resolveForwardGoto(afterSecondJumpStart);
/*  580:     */       
/*  581: 654 */       break;
/*  582:     */     case 102: 
/*  583: 658 */       Node ifThen = child.getNext();
/*  584: 659 */       Node ifElse = ifThen.getNext();
/*  585: 660 */       visitExpression(child, 0);
/*  586: 661 */       int elseJumpStart = this.iCodeTop;
/*  587: 662 */       addGotoOp(7);
/*  588: 663 */       stackChange(-1);
/*  589:     */       
/*  590: 665 */       visitExpression(ifThen, contextFlags & 0x1);
/*  591: 666 */       int afterElseJumpStart = this.iCodeTop;
/*  592: 667 */       addGotoOp(5);
/*  593: 668 */       resolveForwardGoto(elseJumpStart);
/*  594: 669 */       this.stackDepth = savedStackDepth;
/*  595:     */       
/*  596: 671 */       visitExpression(ifElse, contextFlags & 0x1);
/*  597: 672 */       resolveForwardGoto(afterElseJumpStart);
/*  598:     */       
/*  599: 674 */       break;
/*  600:     */     case 33: 
/*  601:     */     case 34: 
/*  602: 678 */       visitExpression(child, 0);
/*  603: 679 */       child = child.getNext();
/*  604: 680 */       addStringOp(type, child.getString());
/*  605: 681 */       break;
/*  606:     */     case 9: 
/*  607:     */     case 10: 
/*  608:     */     case 11: 
/*  609:     */     case 12: 
/*  610:     */     case 13: 
/*  611:     */     case 14: 
/*  612:     */     case 15: 
/*  613:     */     case 16: 
/*  614:     */     case 17: 
/*  615:     */     case 18: 
/*  616:     */     case 19: 
/*  617:     */     case 20: 
/*  618:     */     case 21: 
/*  619:     */     case 22: 
/*  620:     */     case 23: 
/*  621:     */     case 24: 
/*  622:     */     case 25: 
/*  623:     */     case 31: 
/*  624:     */     case 36: 
/*  625:     */     case 46: 
/*  626:     */     case 47: 
/*  627:     */     case 52: 
/*  628:     */     case 53: 
/*  629: 706 */       visitExpression(child, 0);
/*  630: 707 */       child = child.getNext();
/*  631: 708 */       visitExpression(child, 0);
/*  632: 709 */       addToken(type);
/*  633: 710 */       stackChange(-1);
/*  634: 711 */       break;
/*  635:     */     case 26: 
/*  636:     */     case 27: 
/*  637:     */     case 28: 
/*  638:     */     case 29: 
/*  639:     */     case 32: 
/*  640:     */     case 126: 
/*  641: 719 */       visitExpression(child, 0);
/*  642: 720 */       if (type == 126)
/*  643:     */       {
/*  644: 721 */         addIcode(-4);
/*  645: 722 */         addIcode(-50);
/*  646:     */       }
/*  647:     */       else
/*  648:     */       {
/*  649: 724 */         addToken(type);
/*  650:     */       }
/*  651: 726 */       break;
/*  652:     */     case 67: 
/*  653:     */     case 69: 
/*  654: 730 */       visitExpression(child, 0);
/*  655: 731 */       addToken(type);
/*  656: 732 */       break;
/*  657:     */     case 35: 
/*  658:     */     case 139: 
/*  659: 737 */       visitExpression(child, 0);
/*  660: 738 */       child = child.getNext();
/*  661: 739 */       String property = child.getString();
/*  662: 740 */       child = child.getNext();
/*  663: 741 */       if (type == 139)
/*  664:     */       {
/*  665: 742 */         addIcode(-1);
/*  666: 743 */         stackChange(1);
/*  667: 744 */         addStringOp(33, property);
/*  668:     */         
/*  669: 746 */         stackChange(-1);
/*  670:     */       }
/*  671: 748 */       visitExpression(child, 0);
/*  672: 749 */       addStringOp(35, property);
/*  673: 750 */       stackChange(-1);
/*  674:     */       
/*  675: 752 */       break;
/*  676:     */     case 37: 
/*  677:     */     case 140: 
/*  678: 756 */       visitExpression(child, 0);
/*  679: 757 */       child = child.getNext();
/*  680: 758 */       visitExpression(child, 0);
/*  681: 759 */       child = child.getNext();
/*  682: 760 */       if (type == 140)
/*  683:     */       {
/*  684: 761 */         addIcode(-2);
/*  685: 762 */         stackChange(2);
/*  686: 763 */         addToken(36);
/*  687: 764 */         stackChange(-1);
/*  688:     */         
/*  689: 766 */         stackChange(-1);
/*  690:     */       }
/*  691: 768 */       visitExpression(child, 0);
/*  692: 769 */       addToken(37);
/*  693: 770 */       stackChange(-2);
/*  694: 771 */       break;
/*  695:     */     case 68: 
/*  696:     */     case 142: 
/*  697: 775 */       visitExpression(child, 0);
/*  698: 776 */       child = child.getNext();
/*  699: 777 */       if (type == 142)
/*  700:     */       {
/*  701: 778 */         addIcode(-1);
/*  702: 779 */         stackChange(1);
/*  703: 780 */         addToken(67);
/*  704:     */         
/*  705: 782 */         stackChange(-1);
/*  706:     */       }
/*  707: 784 */       visitExpression(child, 0);
/*  708: 785 */       addToken(68);
/*  709: 786 */       stackChange(-1);
/*  710: 787 */       break;
/*  711:     */     case 8: 
/*  712:     */     case 73: 
/*  713: 792 */       String name = child.getString();
/*  714: 793 */       visitExpression(child, 0);
/*  715: 794 */       child = child.getNext();
/*  716: 795 */       visitExpression(child, 0);
/*  717: 796 */       addStringOp(type, name);
/*  718: 797 */       stackChange(-1);
/*  719:     */       
/*  720: 799 */       break;
/*  721:     */     case 155: 
/*  722: 803 */       String name = child.getString();
/*  723: 804 */       visitExpression(child, 0);
/*  724: 805 */       child = child.getNext();
/*  725: 806 */       visitExpression(child, 0);
/*  726: 807 */       addStringOp(-59, name);
/*  727: 808 */       stackChange(-1);
/*  728:     */       
/*  729: 810 */       break;
/*  730:     */     case 137: 
/*  731: 814 */       int index = -1;
/*  732: 817 */       if ((this.itsInFunctionFlag) && (!this.itsData.itsNeedsActivation)) {
/*  733: 818 */         index = this.scriptOrFn.getIndexForNameNode(node);
/*  734:     */       }
/*  735: 819 */       if (index == -1)
/*  736:     */       {
/*  737: 820 */         addStringOp(-14, node.getString());
/*  738: 821 */         stackChange(1);
/*  739:     */       }
/*  740:     */       else
/*  741:     */       {
/*  742: 823 */         addVarOp(55, index);
/*  743: 824 */         stackChange(1);
/*  744: 825 */         addToken(32);
/*  745:     */       }
/*  746: 828 */       break;
/*  747:     */     case 39: 
/*  748:     */     case 41: 
/*  749:     */     case 49: 
/*  750: 833 */       addStringOp(type, node.getString());
/*  751: 834 */       stackChange(1);
/*  752: 835 */       break;
/*  753:     */     case 106: 
/*  754:     */     case 107: 
/*  755: 839 */       visitIncDec(node, child);
/*  756: 840 */       break;
/*  757:     */     case 40: 
/*  758: 844 */       double num = node.getDouble();
/*  759: 845 */       int inum = (int)num;
/*  760: 846 */       if (inum == num)
/*  761:     */       {
/*  762: 847 */         if (inum == 0)
/*  763:     */         {
/*  764: 848 */           addIcode(-51);
/*  765: 850 */           if (1.0D / num < 0.0D) {
/*  766: 851 */             addToken(29);
/*  767:     */           }
/*  768:     */         }
/*  769: 853 */         else if (inum == 1)
/*  770:     */         {
/*  771: 854 */           addIcode(-52);
/*  772:     */         }
/*  773: 855 */         else if ((short)inum == inum)
/*  774:     */         {
/*  775: 856 */           addIcode(-27);
/*  776:     */           
/*  777: 858 */           addUint16(inum & 0xFFFF);
/*  778:     */         }
/*  779:     */         else
/*  780:     */         {
/*  781: 860 */           addIcode(-28);
/*  782: 861 */           addInt(inum);
/*  783:     */         }
/*  784:     */       }
/*  785:     */       else
/*  786:     */       {
/*  787: 864 */         int index = getDoubleIndex(num);
/*  788: 865 */         addIndexOp(40, index);
/*  789:     */       }
/*  790: 867 */       stackChange(1);
/*  791:     */       
/*  792: 869 */       break;
/*  793:     */     case 55: 
/*  794: 873 */       if (this.itsData.itsNeedsActivation) {
/*  795: 873 */         Kit.codeBug();
/*  796:     */       }
/*  797: 874 */       int index = this.scriptOrFn.getIndexForNameNode(node);
/*  798: 875 */       addVarOp(55, index);
/*  799: 876 */       stackChange(1);
/*  800:     */       
/*  801: 878 */       break;
/*  802:     */     case 56: 
/*  803: 882 */       if (this.itsData.itsNeedsActivation) {
/*  804: 882 */         Kit.codeBug();
/*  805:     */       }
/*  806: 883 */       int index = this.scriptOrFn.getIndexForNameNode(child);
/*  807: 884 */       child = child.getNext();
/*  808: 885 */       visitExpression(child, 0);
/*  809: 886 */       addVarOp(56, index);
/*  810:     */       
/*  811: 888 */       break;
/*  812:     */     case 156: 
/*  813: 892 */       if (this.itsData.itsNeedsActivation) {
/*  814: 892 */         Kit.codeBug();
/*  815:     */       }
/*  816: 893 */       int index = this.scriptOrFn.getIndexForNameNode(child);
/*  817: 894 */       child = child.getNext();
/*  818: 895 */       visitExpression(child, 0);
/*  819: 896 */       addVarOp(156, index);
/*  820:     */       
/*  821: 898 */       break;
/*  822:     */     case 42: 
/*  823:     */     case 43: 
/*  824:     */     case 44: 
/*  825:     */     case 45: 
/*  826:     */     case 63: 
/*  827: 905 */       addToken(type);
/*  828: 906 */       stackChange(1);
/*  829: 907 */       break;
/*  830:     */     case 61: 
/*  831:     */     case 62: 
/*  832: 911 */       addIndexOp(type, getLocalBlockRef(node));
/*  833: 912 */       stackChange(1);
/*  834: 913 */       break;
/*  835:     */     case 48: 
/*  836: 917 */       int index = node.getExistingIntProp(4);
/*  837: 918 */       addIndexOp(48, index);
/*  838: 919 */       stackChange(1);
/*  839:     */       
/*  840: 921 */       break;
/*  841:     */     case 65: 
/*  842:     */     case 66: 
/*  843: 925 */       visitLiteral(node, child);
/*  844: 926 */       break;
/*  845:     */     case 157: 
/*  846: 929 */       visitArrayComprehension(node, child, child.getNext());
/*  847: 930 */       break;
/*  848:     */     case 71: 
/*  849: 933 */       visitExpression(child, 0);
/*  850: 934 */       addStringOp(type, (String)node.getProp(17));
/*  851: 935 */       break;
/*  852:     */     case 77: 
/*  853:     */     case 78: 
/*  854:     */     case 79: 
/*  855:     */     case 80: 
/*  856: 942 */       int memberTypeFlags = node.getIntProp(16, 0);
/*  857:     */       
/*  858: 944 */       int childCount = 0;
/*  859:     */       do
/*  860:     */       {
/*  861: 946 */         visitExpression(child, 0);
/*  862: 947 */         childCount++;
/*  863: 948 */         child = child.getNext();
/*  864: 949 */       } while (child != null);
/*  865: 950 */       addIndexOp(type, memberTypeFlags);
/*  866: 951 */       stackChange(1 - childCount);
/*  867:     */       
/*  868: 953 */       break;
/*  869:     */     case 146: 
/*  870: 958 */       updateLineNumber(node);
/*  871: 959 */       visitExpression(child, 0);
/*  872: 960 */       addIcode(-53);
/*  873: 961 */       stackChange(-1);
/*  874: 962 */       int queryPC = this.iCodeTop;
/*  875: 963 */       visitExpression(child.getNext(), 0);
/*  876: 964 */       addBackwardGoto(-54, queryPC);
/*  877:     */       
/*  878: 966 */       break;
/*  879:     */     case 74: 
/*  880:     */     case 75: 
/*  881:     */     case 76: 
/*  882: 971 */       visitExpression(child, 0);
/*  883: 972 */       addToken(type);
/*  884: 973 */       break;
/*  885:     */     case 72: 
/*  886: 976 */       if (child != null)
/*  887:     */       {
/*  888: 977 */         visitExpression(child, 0);
/*  889:     */       }
/*  890:     */       else
/*  891:     */       {
/*  892: 979 */         addIcode(-50);
/*  893: 980 */         stackChange(1);
/*  894:     */       }
/*  895: 982 */       addToken(72);
/*  896: 983 */       addUint16(node.getLineno() & 0xFFFF);
/*  897: 984 */       break;
/*  898:     */     case 159: 
/*  899: 987 */       Node enterWith = node.getFirstChild();
/*  900: 988 */       Node with = enterWith.getNext();
/*  901: 989 */       visitExpression(enterWith.getFirstChild(), 0);
/*  902: 990 */       addToken(2);
/*  903: 991 */       stackChange(-1);
/*  904: 992 */       visitExpression(with.getFirstChild(), 0);
/*  905: 993 */       addToken(3);
/*  906: 994 */       break;
/*  907:     */     case 50: 
/*  908:     */     case 51: 
/*  909:     */     case 57: 
/*  910:     */     case 58: 
/*  911:     */     case 59: 
/*  912:     */     case 60: 
/*  913:     */     case 64: 
/*  914:     */     case 81: 
/*  915:     */     case 82: 
/*  916:     */     case 83: 
/*  917:     */     case 84: 
/*  918:     */     case 85: 
/*  919:     */     case 86: 
/*  920:     */     case 87: 
/*  921:     */     case 88: 
/*  922:     */     case 90: 
/*  923:     */     case 91: 
/*  924:     */     case 92: 
/*  925:     */     case 93: 
/*  926:     */     case 94: 
/*  927:     */     case 95: 
/*  928:     */     case 96: 
/*  929:     */     case 97: 
/*  930:     */     case 98: 
/*  931:     */     case 99: 
/*  932:     */     case 100: 
/*  933:     */     case 101: 
/*  934:     */     case 103: 
/*  935:     */     case 108: 
/*  936:     */     case 110: 
/*  937:     */     case 111: 
/*  938:     */     case 112: 
/*  939:     */     case 113: 
/*  940:     */     case 114: 
/*  941:     */     case 115: 
/*  942:     */     case 116: 
/*  943:     */     case 117: 
/*  944:     */     case 118: 
/*  945:     */     case 119: 
/*  946:     */     case 120: 
/*  947:     */     case 121: 
/*  948:     */     case 122: 
/*  949:     */     case 123: 
/*  950:     */     case 124: 
/*  951:     */     case 125: 
/*  952:     */     case 127: 
/*  953:     */     case 128: 
/*  954:     */     case 129: 
/*  955:     */     case 130: 
/*  956:     */     case 131: 
/*  957:     */     case 132: 
/*  958:     */     case 133: 
/*  959:     */     case 134: 
/*  960:     */     case 135: 
/*  961:     */     case 136: 
/*  962:     */     case 141: 
/*  963:     */     case 143: 
/*  964:     */     case 144: 
/*  965:     */     case 145: 
/*  966:     */     case 147: 
/*  967:     */     case 148: 
/*  968:     */     case 149: 
/*  969:     */     case 150: 
/*  970:     */     case 151: 
/*  971:     */     case 152: 
/*  972:     */     case 153: 
/*  973:     */     case 154: 
/*  974:     */     case 158: 
/*  975:     */     default: 
/*  976: 998 */       throw badTree(node);
/*  977:     */     }
/*  978:1000 */     if (savedStackDepth + 1 != this.stackDepth) {
/*  979:1001 */       Kit.codeBug();
/*  980:     */     }
/*  981:     */   }
/*  982:     */   
/*  983:     */   private void generateCallFunAndThis(Node left)
/*  984:     */   {
/*  985:1008 */     int type = left.getType();
/*  986:1009 */     switch (type)
/*  987:     */     {
/*  988:     */     case 39: 
/*  989:1011 */       String name = left.getString();
/*  990:     */       
/*  991:1013 */       addStringOp(-15, name);
/*  992:1014 */       stackChange(2);
/*  993:1015 */       break;
/*  994:     */     case 33: 
/*  995:     */     case 36: 
/*  996:1019 */       Node target = left.getFirstChild();
/*  997:1020 */       visitExpression(target, 0);
/*  998:1021 */       Node id = target.getNext();
/*  999:1022 */       if (type == 33)
/* 1000:     */       {
/* 1001:1023 */         String property = id.getString();
/* 1002:     */         
/* 1003:1025 */         addStringOp(-16, property);
/* 1004:1026 */         stackChange(1);
/* 1005:     */       }
/* 1006:     */       else
/* 1007:     */       {
/* 1008:1028 */         visitExpression(id, 0);
/* 1009:     */         
/* 1010:1030 */         addIcode(-17);
/* 1011:     */       }
/* 1012:1032 */       break;
/* 1013:     */     default: 
/* 1014:1036 */       visitExpression(left, 0);
/* 1015:     */       
/* 1016:1038 */       addIcode(-18);
/* 1017:1039 */       stackChange(1);
/* 1018:     */     }
/* 1019:     */   }
/* 1020:     */   
/* 1021:     */   private void visitIncDec(Node node, Node child)
/* 1022:     */   {
/* 1023:1047 */     int incrDecrMask = node.getExistingIntProp(13);
/* 1024:1048 */     int childType = child.getType();
/* 1025:1049 */     switch (childType)
/* 1026:     */     {
/* 1027:     */     case 55: 
/* 1028:1051 */       if (this.itsData.itsNeedsActivation) {
/* 1029:1051 */         Kit.codeBug();
/* 1030:     */       }
/* 1031:1052 */       int i = this.scriptOrFn.getIndexForNameNode(child);
/* 1032:1053 */       addVarOp(-7, i);
/* 1033:1054 */       addUint8(incrDecrMask);
/* 1034:1055 */       stackChange(1);
/* 1035:1056 */       break;
/* 1036:     */     case 39: 
/* 1037:1059 */       String name = child.getString();
/* 1038:1060 */       addStringOp(-8, name);
/* 1039:1061 */       addUint8(incrDecrMask);
/* 1040:1062 */       stackChange(1);
/* 1041:1063 */       break;
/* 1042:     */     case 33: 
/* 1043:1066 */       Node object = child.getFirstChild();
/* 1044:1067 */       visitExpression(object, 0);
/* 1045:1068 */       String property = object.getNext().getString();
/* 1046:1069 */       addStringOp(-9, property);
/* 1047:1070 */       addUint8(incrDecrMask);
/* 1048:1071 */       break;
/* 1049:     */     case 36: 
/* 1050:1074 */       Node object = child.getFirstChild();
/* 1051:1075 */       visitExpression(object, 0);
/* 1052:1076 */       Node index = object.getNext();
/* 1053:1077 */       visitExpression(index, 0);
/* 1054:1078 */       addIcode(-10);
/* 1055:1079 */       addUint8(incrDecrMask);
/* 1056:1080 */       stackChange(-1);
/* 1057:1081 */       break;
/* 1058:     */     case 67: 
/* 1059:1084 */       Node ref = child.getFirstChild();
/* 1060:1085 */       visitExpression(ref, 0);
/* 1061:1086 */       addIcode(-11);
/* 1062:1087 */       addUint8(incrDecrMask);
/* 1063:1088 */       break;
/* 1064:     */     default: 
/* 1065:1091 */       throw badTree(node);
/* 1066:     */     }
/* 1067:     */   }
/* 1068:     */   
/* 1069:     */   private void visitLiteral(Node node, Node child)
/* 1070:     */   {
/* 1071:1098 */     int type = node.getType();
/* 1072:     */     
/* 1073:1100 */     Object[] propertyIds = null;
/* 1074:1101 */     if (type == 65)
/* 1075:     */     {
/* 1076:1102 */       int count = 0;
/* 1077:1103 */       for (Node n = child; n != null; n = n.getNext()) {
/* 1078:1104 */         count++;
/* 1079:     */       }
/* 1080:     */     }
/* 1081:     */     else
/* 1082:     */     {
/* 1083:     */       int count;
/* 1084:1106 */       if (type == 66)
/* 1085:     */       {
/* 1086:1107 */         propertyIds = (Object[])node.getProp(12);
/* 1087:1108 */         count = propertyIds.length;
/* 1088:     */       }
/* 1089:     */       else
/* 1090:     */       {
/* 1091:1110 */         throw badTree(node);
/* 1092:     */       }
/* 1093:     */     }
/* 1094:     */     int count;
/* 1095:1112 */     addIndexOp(-29, count);
/* 1096:1113 */     stackChange(2);
/* 1097:1114 */     while (child != null)
/* 1098:     */     {
/* 1099:1115 */       int childType = child.getType();
/* 1100:1116 */       if (childType == 151)
/* 1101:     */       {
/* 1102:1117 */         visitExpression(child.getFirstChild(), 0);
/* 1103:1118 */         addIcode(-57);
/* 1104:     */       }
/* 1105:1119 */       else if (childType == 152)
/* 1106:     */       {
/* 1107:1120 */         visitExpression(child.getFirstChild(), 0);
/* 1108:1121 */         addIcode(-58);
/* 1109:     */       }
/* 1110:     */       else
/* 1111:     */       {
/* 1112:1123 */         visitExpression(child, 0);
/* 1113:1124 */         addIcode(-30);
/* 1114:     */       }
/* 1115:1126 */       stackChange(-1);
/* 1116:1127 */       child = child.getNext();
/* 1117:     */     }
/* 1118:1129 */     if (type == 65)
/* 1119:     */     {
/* 1120:1130 */       int[] skipIndexes = (int[])node.getProp(11);
/* 1121:1131 */       if (skipIndexes == null)
/* 1122:     */       {
/* 1123:1132 */         addToken(65);
/* 1124:     */       }
/* 1125:     */       else
/* 1126:     */       {
/* 1127:1134 */         int index = this.literalIds.size();
/* 1128:1135 */         this.literalIds.add(skipIndexes);
/* 1129:1136 */         addIndexOp(-31, index);
/* 1130:     */       }
/* 1131:     */     }
/* 1132:     */     else
/* 1133:     */     {
/* 1134:1139 */       int index = this.literalIds.size();
/* 1135:1140 */       this.literalIds.add(propertyIds);
/* 1136:1141 */       addIndexOp(66, index);
/* 1137:     */     }
/* 1138:1143 */     stackChange(-1);
/* 1139:     */   }
/* 1140:     */   
/* 1141:     */   private void visitArrayComprehension(Node node, Node initStmt, Node expr)
/* 1142:     */   {
/* 1143:1153 */     visitStatement(initStmt, this.stackDepth);
/* 1144:1154 */     visitExpression(expr, 0);
/* 1145:     */   }
/* 1146:     */   
/* 1147:     */   private int getLocalBlockRef(Node node)
/* 1148:     */   {
/* 1149:1159 */     Node localBlock = (Node)node.getProp(3);
/* 1150:1160 */     return localBlock.getExistingIntProp(2);
/* 1151:     */   }
/* 1152:     */   
/* 1153:     */   private int getTargetLabel(Node target)
/* 1154:     */   {
/* 1155:1165 */     int label = target.labelId();
/* 1156:1166 */     if (label != -1) {
/* 1157:1167 */       return label;
/* 1158:     */     }
/* 1159:1169 */     label = this.labelTableTop;
/* 1160:1170 */     if ((this.labelTable == null) || (label == this.labelTable.length)) {
/* 1161:1171 */       if (this.labelTable == null)
/* 1162:     */       {
/* 1163:1172 */         this.labelTable = new int[32];
/* 1164:     */       }
/* 1165:     */       else
/* 1166:     */       {
/* 1167:1174 */         int[] tmp = new int[this.labelTable.length * 2];
/* 1168:1175 */         System.arraycopy(this.labelTable, 0, tmp, 0, label);
/* 1169:1176 */         this.labelTable = tmp;
/* 1170:     */       }
/* 1171:     */     }
/* 1172:1179 */     this.labelTableTop = (label + 1);
/* 1173:1180 */     this.labelTable[label] = -1;
/* 1174:     */     
/* 1175:1182 */     target.labelId(label);
/* 1176:1183 */     return label;
/* 1177:     */   }
/* 1178:     */   
/* 1179:     */   private void markTargetLabel(Node target)
/* 1180:     */   {
/* 1181:1188 */     int label = getTargetLabel(target);
/* 1182:1189 */     if (this.labelTable[label] != -1) {
/* 1183:1191 */       Kit.codeBug();
/* 1184:     */     }
/* 1185:1193 */     this.labelTable[label] = this.iCodeTop;
/* 1186:     */   }
/* 1187:     */   
/* 1188:     */   private void addGoto(Node target, int gotoOp)
/* 1189:     */   {
/* 1190:1198 */     int label = getTargetLabel(target);
/* 1191:1199 */     if (label >= this.labelTableTop) {
/* 1192:1199 */       Kit.codeBug();
/* 1193:     */     }
/* 1194:1200 */     int targetPC = this.labelTable[label];
/* 1195:1202 */     if (targetPC != -1)
/* 1196:     */     {
/* 1197:1203 */       addBackwardGoto(gotoOp, targetPC);
/* 1198:     */     }
/* 1199:     */     else
/* 1200:     */     {
/* 1201:1205 */       int gotoPC = this.iCodeTop;
/* 1202:1206 */       addGotoOp(gotoOp);
/* 1203:1207 */       int top = this.fixupTableTop;
/* 1204:1208 */       if ((this.fixupTable == null) || (top == this.fixupTable.length)) {
/* 1205:1209 */         if (this.fixupTable == null)
/* 1206:     */         {
/* 1207:1210 */           this.fixupTable = new long[40];
/* 1208:     */         }
/* 1209:     */         else
/* 1210:     */         {
/* 1211:1212 */           long[] tmp = new long[this.fixupTable.length * 2];
/* 1212:1213 */           System.arraycopy(this.fixupTable, 0, tmp, 0, top);
/* 1213:1214 */           this.fixupTable = tmp;
/* 1214:     */         }
/* 1215:     */       }
/* 1216:1217 */       this.fixupTableTop = (top + 1);
/* 1217:1218 */       this.fixupTable[top] = (label << 32 | gotoPC);
/* 1218:     */     }
/* 1219:     */   }
/* 1220:     */   
/* 1221:     */   private void fixLabelGotos()
/* 1222:     */   {
/* 1223:1224 */     for (int i = 0; i < this.fixupTableTop; i++)
/* 1224:     */     {
/* 1225:1225 */       long fixup = this.fixupTable[i];
/* 1226:1226 */       int label = (int)(fixup >> 32);
/* 1227:1227 */       int jumpSource = (int)fixup;
/* 1228:1228 */       int pc = this.labelTable[label];
/* 1229:1229 */       if (pc == -1) {
/* 1230:1231 */         throw Kit.codeBug();
/* 1231:     */       }
/* 1232:1233 */       resolveGoto(jumpSource, pc);
/* 1233:     */     }
/* 1234:1235 */     this.fixupTableTop = 0;
/* 1235:     */   }
/* 1236:     */   
/* 1237:     */   private void addBackwardGoto(int gotoOp, int jumpPC)
/* 1238:     */   {
/* 1239:1240 */     int fromPC = this.iCodeTop;
/* 1240:1242 */     if (fromPC <= jumpPC) {
/* 1241:1242 */       throw Kit.codeBug();
/* 1242:     */     }
/* 1243:1243 */     addGotoOp(gotoOp);
/* 1244:1244 */     resolveGoto(fromPC, jumpPC);
/* 1245:     */   }
/* 1246:     */   
/* 1247:     */   private void resolveForwardGoto(int fromPC)
/* 1248:     */   {
/* 1249:1250 */     if (this.iCodeTop < fromPC + 3) {
/* 1250:1250 */       throw Kit.codeBug();
/* 1251:     */     }
/* 1252:1251 */     resolveGoto(fromPC, this.iCodeTop);
/* 1253:     */   }
/* 1254:     */   
/* 1255:     */   private void resolveGoto(int fromPC, int jumpPC)
/* 1256:     */   {
/* 1257:1256 */     int offset = jumpPC - fromPC;
/* 1258:1258 */     if ((0 <= offset) && (offset <= 2)) {
/* 1259:1258 */       throw Kit.codeBug();
/* 1260:     */     }
/* 1261:1259 */     int offsetSite = fromPC + 1;
/* 1262:1260 */     if (offset != (short)offset)
/* 1263:     */     {
/* 1264:1261 */       if (this.itsData.longJumps == null) {
/* 1265:1262 */         this.itsData.longJumps = new UintMap();
/* 1266:     */       }
/* 1267:1264 */       this.itsData.longJumps.put(offsetSite, jumpPC);
/* 1268:1265 */       offset = 0;
/* 1269:     */     }
/* 1270:1267 */     byte[] array = this.itsData.itsICode;
/* 1271:1268 */     array[offsetSite] = ((byte)(offset >> 8));
/* 1272:1269 */     array[(offsetSite + 1)] = ((byte)offset);
/* 1273:     */   }
/* 1274:     */   
/* 1275:     */   private void addToken(int token)
/* 1276:     */   {
/* 1277:1274 */     if (!Icode.validTokenCode(token)) {
/* 1278:1274 */       throw Kit.codeBug();
/* 1279:     */     }
/* 1280:1275 */     addUint8(token);
/* 1281:     */   }
/* 1282:     */   
/* 1283:     */   private void addIcode(int icode)
/* 1284:     */   {
/* 1285:1280 */     if (!Icode.validIcode(icode)) {
/* 1286:1280 */       throw Kit.codeBug();
/* 1287:     */     }
/* 1288:1282 */     addUint8(icode & 0xFF);
/* 1289:     */   }
/* 1290:     */   
/* 1291:     */   private void addUint8(int value)
/* 1292:     */   {
/* 1293:1287 */     if ((value & 0xFFFFFF00) != 0) {
/* 1294:1287 */       throw Kit.codeBug();
/* 1295:     */     }
/* 1296:1288 */     byte[] array = this.itsData.itsICode;
/* 1297:1289 */     int top = this.iCodeTop;
/* 1298:1290 */     if (top == array.length) {
/* 1299:1291 */       array = increaseICodeCapacity(1);
/* 1300:     */     }
/* 1301:1293 */     array[top] = ((byte)value);
/* 1302:1294 */     this.iCodeTop = (top + 1);
/* 1303:     */   }
/* 1304:     */   
/* 1305:     */   private void addUint16(int value)
/* 1306:     */   {
/* 1307:1299 */     if ((value & 0xFFFF0000) != 0) {
/* 1308:1299 */       throw Kit.codeBug();
/* 1309:     */     }
/* 1310:1300 */     byte[] array = this.itsData.itsICode;
/* 1311:1301 */     int top = this.iCodeTop;
/* 1312:1302 */     if (top + 2 > array.length) {
/* 1313:1303 */       array = increaseICodeCapacity(2);
/* 1314:     */     }
/* 1315:1305 */     array[top] = ((byte)(value >>> 8));
/* 1316:1306 */     array[(top + 1)] = ((byte)value);
/* 1317:1307 */     this.iCodeTop = (top + 2);
/* 1318:     */   }
/* 1319:     */   
/* 1320:     */   private void addInt(int i)
/* 1321:     */   {
/* 1322:1312 */     byte[] array = this.itsData.itsICode;
/* 1323:1313 */     int top = this.iCodeTop;
/* 1324:1314 */     if (top + 4 > array.length) {
/* 1325:1315 */       array = increaseICodeCapacity(4);
/* 1326:     */     }
/* 1327:1317 */     array[top] = ((byte)(i >>> 24));
/* 1328:1318 */     array[(top + 1)] = ((byte)(i >>> 16));
/* 1329:1319 */     array[(top + 2)] = ((byte)(i >>> 8));
/* 1330:1320 */     array[(top + 3)] = ((byte)i);
/* 1331:1321 */     this.iCodeTop = (top + 4);
/* 1332:     */   }
/* 1333:     */   
/* 1334:     */   private int getDoubleIndex(double num)
/* 1335:     */   {
/* 1336:1326 */     int index = this.doubleTableTop;
/* 1337:1327 */     if (index == 0)
/* 1338:     */     {
/* 1339:1328 */       this.itsData.itsDoubleTable = new double[64];
/* 1340:     */     }
/* 1341:1329 */     else if (this.itsData.itsDoubleTable.length == index)
/* 1342:     */     {
/* 1343:1330 */       double[] na = new double[index * 2];
/* 1344:1331 */       System.arraycopy(this.itsData.itsDoubleTable, 0, na, 0, index);
/* 1345:1332 */       this.itsData.itsDoubleTable = na;
/* 1346:     */     }
/* 1347:1334 */     this.itsData.itsDoubleTable[index] = num;
/* 1348:1335 */     this.doubleTableTop = (index + 1);
/* 1349:1336 */     return index;
/* 1350:     */   }
/* 1351:     */   
/* 1352:     */   private void addGotoOp(int gotoOp)
/* 1353:     */   {
/* 1354:1341 */     byte[] array = this.itsData.itsICode;
/* 1355:1342 */     int top = this.iCodeTop;
/* 1356:1343 */     if (top + 3 > array.length) {
/* 1357:1344 */       array = increaseICodeCapacity(3);
/* 1358:     */     }
/* 1359:1346 */     array[top] = ((byte)gotoOp);
/* 1360:     */     
/* 1361:1348 */     this.iCodeTop = (top + 1 + 2);
/* 1362:     */   }
/* 1363:     */   
/* 1364:     */   private void addVarOp(int op, int varIndex)
/* 1365:     */   {
/* 1366:1353 */     switch (op)
/* 1367:     */     {
/* 1368:     */     case 156: 
/* 1369:1355 */       if (varIndex < 128)
/* 1370:     */       {
/* 1371:1356 */         addIcode(-61);
/* 1372:1357 */         addUint8(varIndex);
/* 1373:1358 */         return;
/* 1374:     */       }
/* 1375:1360 */       addIndexOp(-60, varIndex);
/* 1376:1361 */       return;
/* 1377:     */     case 55: 
/* 1378:     */     case 56: 
/* 1379:1364 */       if (varIndex < 128)
/* 1380:     */       {
/* 1381:1365 */         addIcode(op == 55 ? -48 : -49);
/* 1382:1366 */         addUint8(varIndex);
/* 1383:1367 */         return;
/* 1384:     */       }
/* 1385:     */     case -7: 
/* 1386:1371 */       addIndexOp(op, varIndex);
/* 1387:1372 */       return;
/* 1388:     */     }
/* 1389:1374 */     throw Kit.codeBug();
/* 1390:     */   }
/* 1391:     */   
/* 1392:     */   private void addStringOp(int op, String str)
/* 1393:     */   {
/* 1394:1379 */     addStringPrefix(str);
/* 1395:1380 */     if (Icode.validIcode(op)) {
/* 1396:1381 */       addIcode(op);
/* 1397:     */     } else {
/* 1398:1383 */       addToken(op);
/* 1399:     */     }
/* 1400:     */   }
/* 1401:     */   
/* 1402:     */   private void addIndexOp(int op, int index)
/* 1403:     */   {
/* 1404:1389 */     addIndexPrefix(index);
/* 1405:1390 */     if (Icode.validIcode(op)) {
/* 1406:1391 */       addIcode(op);
/* 1407:     */     } else {
/* 1408:1393 */       addToken(op);
/* 1409:     */     }
/* 1410:     */   }
/* 1411:     */   
/* 1412:     */   private void addStringPrefix(String str)
/* 1413:     */   {
/* 1414:1399 */     int index = this.strings.get(str, -1);
/* 1415:1400 */     if (index == -1)
/* 1416:     */     {
/* 1417:1401 */       index = this.strings.size();
/* 1418:1402 */       this.strings.put(str, index);
/* 1419:     */     }
/* 1420:1404 */     if (index < 4)
/* 1421:     */     {
/* 1422:1405 */       addIcode(-41 - index);
/* 1423:     */     }
/* 1424:1406 */     else if (index <= 255)
/* 1425:     */     {
/* 1426:1407 */       addIcode(-45);
/* 1427:1408 */       addUint8(index);
/* 1428:     */     }
/* 1429:1409 */     else if (index <= 65535)
/* 1430:     */     {
/* 1431:1410 */       addIcode(-46);
/* 1432:1411 */       addUint16(index);
/* 1433:     */     }
/* 1434:     */     else
/* 1435:     */     {
/* 1436:1413 */       addIcode(-47);
/* 1437:1414 */       addInt(index);
/* 1438:     */     }
/* 1439:     */   }
/* 1440:     */   
/* 1441:     */   private void addIndexPrefix(int index)
/* 1442:     */   {
/* 1443:1420 */     if (index < 0) {
/* 1444:1420 */       Kit.codeBug();
/* 1445:     */     }
/* 1446:1421 */     if (index < 6)
/* 1447:     */     {
/* 1448:1422 */       addIcode(-32 - index);
/* 1449:     */     }
/* 1450:1423 */     else if (index <= 255)
/* 1451:     */     {
/* 1452:1424 */       addIcode(-38);
/* 1453:1425 */       addUint8(index);
/* 1454:     */     }
/* 1455:1426 */     else if (index <= 65535)
/* 1456:     */     {
/* 1457:1427 */       addIcode(-39);
/* 1458:1428 */       addUint16(index);
/* 1459:     */     }
/* 1460:     */     else
/* 1461:     */     {
/* 1462:1430 */       addIcode(-40);
/* 1463:1431 */       addInt(index);
/* 1464:     */     }
/* 1465:     */   }
/* 1466:     */   
/* 1467:     */   private void addExceptionHandler(int icodeStart, int icodeEnd, int handlerStart, boolean isFinally, int exceptionObjectLocal, int scopeLocal)
/* 1468:     */   {
/* 1469:1439 */     int top = this.exceptionTableTop;
/* 1470:1440 */     int[] table = this.itsData.itsExceptionTable;
/* 1471:1441 */     if (table == null)
/* 1472:     */     {
/* 1473:1442 */       if (top != 0) {
/* 1474:1442 */         Kit.codeBug();
/* 1475:     */       }
/* 1476:1443 */       table = new int[12];
/* 1477:1444 */       this.itsData.itsExceptionTable = table;
/* 1478:     */     }
/* 1479:1445 */     else if (table.length == top)
/* 1480:     */     {
/* 1481:1446 */       table = new int[table.length * 2];
/* 1482:1447 */       System.arraycopy(this.itsData.itsExceptionTable, 0, table, 0, top);
/* 1483:1448 */       this.itsData.itsExceptionTable = table;
/* 1484:     */     }
/* 1485:1450 */     table[(top + 0)] = icodeStart;
/* 1486:1451 */     table[(top + 1)] = icodeEnd;
/* 1487:1452 */     table[(top + 2)] = handlerStart;
/* 1488:1453 */     table[(top + 3)] = (isFinally ? 1 : 0);
/* 1489:1454 */     table[(top + 4)] = exceptionObjectLocal;
/* 1490:1455 */     table[(top + 5)] = scopeLocal;
/* 1491:     */     
/* 1492:1457 */     this.exceptionTableTop = (top + 6);
/* 1493:     */   }
/* 1494:     */   
/* 1495:     */   private byte[] increaseICodeCapacity(int extraSize)
/* 1496:     */   {
/* 1497:1462 */     int capacity = this.itsData.itsICode.length;
/* 1498:1463 */     int top = this.iCodeTop;
/* 1499:1464 */     if (top + extraSize <= capacity) {
/* 1500:1464 */       throw Kit.codeBug();
/* 1501:     */     }
/* 1502:1465 */     capacity *= 2;
/* 1503:1466 */     if (top + extraSize > capacity) {
/* 1504:1467 */       capacity = top + extraSize;
/* 1505:     */     }
/* 1506:1469 */     byte[] array = new byte[capacity];
/* 1507:1470 */     System.arraycopy(this.itsData.itsICode, 0, array, 0, top);
/* 1508:1471 */     this.itsData.itsICode = array;
/* 1509:1472 */     return array;
/* 1510:     */   }
/* 1511:     */   
/* 1512:     */   private void stackChange(int change)
/* 1513:     */   {
/* 1514:1477 */     if (change <= 0)
/* 1515:     */     {
/* 1516:1478 */       this.stackDepth += change;
/* 1517:     */     }
/* 1518:     */     else
/* 1519:     */     {
/* 1520:1480 */       int newDepth = this.stackDepth + change;
/* 1521:1481 */       if (newDepth > this.itsData.itsMaxStack) {
/* 1522:1482 */         this.itsData.itsMaxStack = newDepth;
/* 1523:     */       }
/* 1524:1484 */       this.stackDepth = newDepth;
/* 1525:     */     }
/* 1526:     */   }
/* 1527:     */   
/* 1528:     */   private int allocLocal()
/* 1529:     */   {
/* 1530:1490 */     int localSlot = this.localTop;
/* 1531:1491 */     this.localTop += 1;
/* 1532:1492 */     if (this.localTop > this.itsData.itsMaxLocals) {
/* 1533:1493 */       this.itsData.itsMaxLocals = this.localTop;
/* 1534:     */     }
/* 1535:1495 */     return localSlot;
/* 1536:     */   }
/* 1537:     */   
/* 1538:     */   private void releaseLocal(int localSlot)
/* 1539:     */   {
/* 1540:1500 */     this.localTop -= 1;
/* 1541:1501 */     if (localSlot != this.localTop) {
/* 1542:1501 */       Kit.codeBug();
/* 1543:     */     }
/* 1544:     */   }
/* 1545:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.CodeGenerator
 * JD-Core Version:    0.7.0.1
 */