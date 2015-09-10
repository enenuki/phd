/*    1:     */ package antlr;
/*    2:     */ 
/*    3:     */ import antlr.actions.python.ActionLexer;
/*    4:     */ import antlr.actions.python.CodeLexer;
/*    5:     */ import antlr.collections.impl.BitSet;
/*    6:     */ import antlr.collections.impl.Vector;
/*    7:     */ import java.io.IOException;
/*    8:     */ import java.io.PrintStream;
/*    9:     */ import java.io.PrintWriter;
/*   10:     */ import java.util.Enumeration;
/*   11:     */ import java.util.Hashtable;
/*   12:     */ 
/*   13:     */ public class PythonCodeGenerator
/*   14:     */   extends CodeGenerator
/*   15:     */ {
/*   16:  22 */   protected int syntacticPredLevel = 0;
/*   17:  25 */   protected boolean genAST = false;
/*   18:  28 */   protected boolean saveText = false;
/*   19:     */   String labeledElementType;
/*   20:     */   String labeledElementASTType;
/*   21:     */   String labeledElementInit;
/*   22:     */   String commonExtraArgs;
/*   23:     */   String commonExtraParams;
/*   24:     */   String commonLocalVars;
/*   25:     */   String lt1Value;
/*   26:     */   String exceptionThrown;
/*   27:     */   String throwNoViable;
/*   28:     */   public static final String initHeaderAction = "__init__";
/*   29:     */   public static final String mainHeaderAction = "__main__";
/*   30:     */   String lexerClassName;
/*   31:     */   String parserClassName;
/*   32:     */   String treeWalkerClassName;
/*   33:     */   RuleBlock currentRule;
/*   34:     */   String currentASTResult;
/*   35:  59 */   Hashtable treeVariableMap = new Hashtable();
/*   36:  64 */   Hashtable declaredASTVariables = new Hashtable();
/*   37:  67 */   int astVarNumber = 1;
/*   38:  70 */   protected static final String NONUNIQUE = new String();
/*   39:     */   public static final int caseSizeThreshold = 127;
/*   40:     */   private Vector semPreds;
/*   41:     */   
/*   42:     */   protected void printTabs()
/*   43:     */   {
/*   44:  83 */     for (int i = 0; i < this.tabs; i++) {
/*   45:  85 */       this.currentOutput.print("    ");
/*   46:     */     }
/*   47:     */   }
/*   48:     */   
/*   49:     */   public PythonCodeGenerator()
/*   50:     */   {
/*   51:  91 */     this.charFormatter = new PythonCharFormatter();
/*   52:  92 */     this.DEBUG_CODE_GENERATOR = true;
/*   53:     */   }
/*   54:     */   
/*   55:     */   protected int addSemPred(String paramString)
/*   56:     */   {
/*   57: 101 */     this.semPreds.appendElement(paramString);
/*   58: 102 */     return this.semPreds.size() - 1;
/*   59:     */   }
/*   60:     */   
/*   61:     */   public void exitIfError()
/*   62:     */   {
/*   63: 106 */     if (this.antlrTool.hasError()) {
/*   64: 107 */       this.antlrTool.fatalError("Exiting due to errors.");
/*   65:     */     }
/*   66:     */   }
/*   67:     */   
/*   68:     */   protected void checkCurrentOutputStream()
/*   69:     */   {
/*   70:     */     try
/*   71:     */     {
/*   72: 114 */       if (this.currentOutput == null) {
/*   73: 115 */         throw new NullPointerException();
/*   74:     */       }
/*   75:     */     }
/*   76:     */     catch (Exception localException)
/*   77:     */     {
/*   78: 119 */       Utils.error("current output is not set");
/*   79:     */     }
/*   80:     */   }
/*   81:     */   
/*   82:     */   protected String extractIdOfAction(String paramString, int paramInt1, int paramInt2)
/*   83:     */   {
/*   84: 132 */     paramString = removeAssignmentFromDeclaration(paramString);
/*   85:     */     
/*   86:     */ 
/*   87: 135 */     paramString = paramString.trim();
/*   88:     */     
/*   89: 137 */     return paramString;
/*   90:     */   }
/*   91:     */   
/*   92:     */   protected String extractTypeOfAction(String paramString, int paramInt1, int paramInt2)
/*   93:     */   {
/*   94: 149 */     return "";
/*   95:     */   }
/*   96:     */   
/*   97:     */   protected void flushTokens()
/*   98:     */   {
/*   99:     */     try
/*  100:     */     {
/*  101: 155 */       int i = 0;
/*  102:     */       
/*  103: 157 */       checkCurrentOutputStream();
/*  104:     */       
/*  105: 159 */       println("");
/*  106: 160 */       println("### import antlr.Token ");
/*  107: 161 */       println("from antlr import Token");
/*  108: 162 */       println("### >>>The Known Token Types <<<");
/*  109:     */       
/*  110:     */ 
/*  111:     */ 
/*  112: 166 */       PrintWriter localPrintWriter = this.currentOutput;
/*  113:     */       
/*  114:     */ 
/*  115: 169 */       Enumeration localEnumeration = this.behavior.tokenManagers.elements();
/*  116: 172 */       while (localEnumeration.hasMoreElements())
/*  117:     */       {
/*  118: 174 */         TokenManager localTokenManager = (TokenManager)localEnumeration.nextElement();
/*  119: 177 */         if (!localTokenManager.isReadOnly())
/*  120:     */         {
/*  121: 182 */           if (i == 0)
/*  122:     */           {
/*  123: 183 */             genTokenTypes(localTokenManager);
/*  124: 184 */             i = 1;
/*  125:     */           }
/*  126: 188 */           this.currentOutput = localPrintWriter;
/*  127:     */           
/*  128:     */ 
/*  129: 191 */           genTokenInterchange(localTokenManager);
/*  130: 192 */           this.currentOutput = localPrintWriter;
/*  131:     */         }
/*  132: 195 */         exitIfError();
/*  133:     */       }
/*  134:     */     }
/*  135:     */     catch (Exception localException)
/*  136:     */     {
/*  137: 199 */       exitIfError();
/*  138:     */     }
/*  139: 201 */     checkCurrentOutputStream();
/*  140: 202 */     println("");
/*  141:     */   }
/*  142:     */   
/*  143:     */   public void gen()
/*  144:     */   {
/*  145:     */     try
/*  146:     */     {
/*  147: 211 */       Enumeration localEnumeration = this.behavior.grammars.elements();
/*  148: 212 */       while (localEnumeration.hasMoreElements())
/*  149:     */       {
/*  150: 213 */         Grammar localGrammar = (Grammar)localEnumeration.nextElement();
/*  151:     */         
/*  152: 215 */         localGrammar.setGrammarAnalyzer(this.analyzer);
/*  153: 216 */         localGrammar.setCodeGenerator(this);
/*  154: 217 */         this.analyzer.setGrammar(localGrammar);
/*  155:     */         
/*  156: 219 */         setupGrammarParameters(localGrammar);
/*  157: 220 */         localGrammar.generate();
/*  158:     */         
/*  159:     */ 
/*  160: 223 */         exitIfError();
/*  161:     */       }
/*  162:     */     }
/*  163:     */     catch (IOException localIOException)
/*  164:     */     {
/*  165: 229 */       this.antlrTool.reportException(localIOException, null);
/*  166:     */     }
/*  167:     */   }
/*  168:     */   
/*  169:     */   public void gen(ActionElement paramActionElement)
/*  170:     */   {
/*  171: 237 */     if (paramActionElement.isSemPred)
/*  172:     */     {
/*  173: 238 */       genSemPred(paramActionElement.actionText, paramActionElement.line);
/*  174:     */     }
/*  175:     */     else
/*  176:     */     {
/*  177: 242 */       if (this.grammar.hasSyntacticPredicate)
/*  178:     */       {
/*  179: 243 */         println("if not self.inputState.guessing:");
/*  180: 244 */         this.tabs += 1;
/*  181:     */       }
/*  182: 249 */       ActionTransInfo localActionTransInfo = new ActionTransInfo();
/*  183: 250 */       String str = processActionForSpecialSymbols(paramActionElement.actionText, paramActionElement.getLine(), this.currentRule, localActionTransInfo);
/*  184: 255 */       if (localActionTransInfo.refRuleRoot != null) {
/*  185: 260 */         println(localActionTransInfo.refRuleRoot + " = currentAST.root");
/*  186:     */       }
/*  187: 264 */       printAction(str);
/*  188: 266 */       if (localActionTransInfo.assignToRoot)
/*  189:     */       {
/*  190: 268 */         println("currentAST.root = " + localActionTransInfo.refRuleRoot + "");
/*  191:     */         
/*  192: 270 */         println("if (" + localActionTransInfo.refRuleRoot + " != None) and (" + localActionTransInfo.refRuleRoot + ".getFirstChild() != None):");
/*  193: 271 */         this.tabs += 1;
/*  194: 272 */         println("currentAST.child = " + localActionTransInfo.refRuleRoot + ".getFirstChild()");
/*  195: 273 */         this.tabs -= 1;
/*  196: 274 */         println("else:");
/*  197: 275 */         this.tabs += 1;
/*  198: 276 */         println("currentAST.child = " + localActionTransInfo.refRuleRoot);
/*  199: 277 */         this.tabs -= 1;
/*  200: 278 */         println("currentAST.advanceChildToEnd()");
/*  201:     */       }
/*  202: 281 */       if (this.grammar.hasSyntacticPredicate) {
/*  203: 282 */         this.tabs -= 1;
/*  204:     */       }
/*  205:     */     }
/*  206:     */   }
/*  207:     */   
/*  208:     */   public void gen(AlternativeBlock paramAlternativeBlock)
/*  209:     */   {
/*  210: 291 */     if (this.DEBUG_CODE_GENERATOR) {
/*  211: 291 */       System.out.println("gen(" + paramAlternativeBlock + ")");
/*  212:     */     }
/*  213: 292 */     genBlockPreamble(paramAlternativeBlock);
/*  214: 293 */     genBlockInitAction(paramAlternativeBlock);
/*  215:     */     
/*  216:     */ 
/*  217: 296 */     String str = this.currentASTResult;
/*  218: 297 */     if (paramAlternativeBlock.getLabel() != null) {
/*  219: 298 */       this.currentASTResult = paramAlternativeBlock.getLabel();
/*  220:     */     }
/*  221: 301 */     boolean bool = this.grammar.theLLkAnalyzer.deterministic(paramAlternativeBlock);
/*  222:     */     
/*  223:     */ 
/*  224: 304 */     int i = this.tabs;
/*  225: 305 */     PythonBlockFinishingInfo localPythonBlockFinishingInfo = genCommonBlock(paramAlternativeBlock, true);
/*  226: 306 */     genBlockFinish(localPythonBlockFinishingInfo, this.throwNoViable);
/*  227: 307 */     this.tabs = i;
/*  228:     */     
/*  229:     */ 
/*  230:     */ 
/*  231: 311 */     this.currentASTResult = str;
/*  232:     */   }
/*  233:     */   
/*  234:     */   public void gen(BlockEndElement paramBlockEndElement)
/*  235:     */   {
/*  236: 320 */     if (this.DEBUG_CODE_GENERATOR) {
/*  237: 320 */       System.out.println("genRuleEnd(" + paramBlockEndElement + ")");
/*  238:     */     }
/*  239:     */   }
/*  240:     */   
/*  241:     */   public void gen(CharLiteralElement paramCharLiteralElement)
/*  242:     */   {
/*  243: 327 */     if (this.DEBUG_CODE_GENERATOR) {
/*  244: 327 */       System.out.println("genChar(" + paramCharLiteralElement + ")");
/*  245:     */     }
/*  246: 329 */     if (paramCharLiteralElement.getLabel() != null) {
/*  247: 330 */       println(paramCharLiteralElement.getLabel() + " = " + this.lt1Value);
/*  248:     */     }
/*  249: 333 */     boolean bool = this.saveText;
/*  250: 334 */     this.saveText = ((this.saveText) && (paramCharLiteralElement.getAutoGenType() == 1));
/*  251: 335 */     genMatch(paramCharLiteralElement);
/*  252: 336 */     this.saveText = bool;
/*  253:     */   }
/*  254:     */   
/*  255:     */   String toString(boolean paramBoolean)
/*  256:     */   {
/*  257:     */     String str;
/*  258: 342 */     if (paramBoolean) {
/*  259: 343 */       str = "True";
/*  260:     */     } else {
/*  261: 345 */       str = "False";
/*  262:     */     }
/*  263: 346 */     return str;
/*  264:     */   }
/*  265:     */   
/*  266:     */   public void gen(CharRangeElement paramCharRangeElement)
/*  267:     */   {
/*  268: 354 */     if ((paramCharRangeElement.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/*  269: 355 */       println(paramCharRangeElement.getLabel() + " = " + this.lt1Value);
/*  270:     */     }
/*  271: 357 */     int i = ((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramCharRangeElement.getAutoGenType() == 3)) ? 1 : 0;
/*  272: 361 */     if (i != 0) {
/*  273: 362 */       println("_saveIndex = self.text.length()");
/*  274:     */     }
/*  275: 365 */     println("self.matchRange(u" + paramCharRangeElement.beginText + ", u" + paramCharRangeElement.endText + ")");
/*  276: 367 */     if (i != 0) {
/*  277: 368 */       println("self.text.setLength(_saveIndex)");
/*  278:     */     }
/*  279:     */   }
/*  280:     */   
/*  281:     */   public void gen(LexerGrammar paramLexerGrammar)
/*  282:     */     throws IOException
/*  283:     */   {
/*  284: 376 */     if (paramLexerGrammar.debuggingOutput) {
/*  285: 377 */       this.semPreds = new Vector();
/*  286:     */     }
/*  287: 379 */     setGrammar(paramLexerGrammar);
/*  288: 380 */     if (!(this.grammar instanceof LexerGrammar)) {
/*  289: 381 */       this.antlrTool.panic("Internal error generating lexer");
/*  290:     */     }
/*  291: 386 */     setupOutput(this.grammar.getClassName());
/*  292:     */     
/*  293: 388 */     this.genAST = false;
/*  294: 389 */     this.saveText = true;
/*  295:     */     
/*  296: 391 */     this.tabs = 0;
/*  297:     */     
/*  298:     */ 
/*  299: 394 */     genHeader();
/*  300:     */     
/*  301:     */ 
/*  302: 397 */     println("### import antlr and other modules ..");
/*  303: 398 */     println("import sys");
/*  304: 399 */     println("import antlr");
/*  305: 400 */     println("");
/*  306: 401 */     println("version = sys.version.split()[0]");
/*  307: 402 */     println("if version < '2.2.1':");
/*  308: 403 */     this.tabs += 1;
/*  309: 404 */     println("False = 0");
/*  310: 405 */     this.tabs -= 1;
/*  311: 406 */     println("if version < '2.3':");
/*  312: 407 */     this.tabs += 1;
/*  313: 408 */     println("True = not False");
/*  314: 409 */     this.tabs -= 1;
/*  315:     */     
/*  316: 411 */     println("### header action >>> ");
/*  317: 412 */     printActionCode(this.behavior.getHeaderAction(""), 0);
/*  318: 413 */     println("### header action <<< ");
/*  319:     */     
/*  320:     */ 
/*  321: 416 */     println("### preamble action >>> ");
/*  322: 417 */     printActionCode(this.grammar.preambleAction.getText(), 0);
/*  323: 418 */     println("### preamble action <<< ");
/*  324:     */     
/*  325:     */ 
/*  326: 421 */     String str = null;
/*  327: 422 */     if (this.grammar.superClass != null) {
/*  328: 423 */       str = this.grammar.superClass;
/*  329:     */     } else {
/*  330: 426 */       str = "antlr." + this.grammar.getSuperClass();
/*  331:     */     }
/*  332: 430 */     Object localObject1 = "";
/*  333: 431 */     Token localToken = (Token)this.grammar.options.get("classHeaderPrefix");
/*  334: 432 */     if (localToken != null)
/*  335:     */     {
/*  336: 433 */       localObject2 = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/*  337: 434 */       if (localObject2 != null) {
/*  338: 435 */         localObject1 = localObject2;
/*  339:     */       }
/*  340:     */     }
/*  341: 440 */     println("### >>>The Literals<<<");
/*  342: 441 */     println("literals = {}");
/*  343: 442 */     Object localObject2 = this.grammar.tokenManager.getTokenSymbolKeys();
/*  344:     */     Object localObject4;
/*  345: 443 */     while (((Enumeration)localObject2).hasMoreElements())
/*  346:     */     {
/*  347: 444 */       localObject3 = (String)((Enumeration)localObject2).nextElement();
/*  348: 445 */       if (((String)localObject3).charAt(0) == '"')
/*  349:     */       {
/*  350: 448 */         TokenSymbol localTokenSymbol = this.grammar.tokenManager.getTokenSymbol((String)localObject3);
/*  351: 449 */         if ((localTokenSymbol instanceof StringLiteralSymbol))
/*  352:     */         {
/*  353: 450 */           localObject4 = (StringLiteralSymbol)localTokenSymbol;
/*  354: 451 */           println("literals[u" + ((StringLiteralSymbol)localObject4).getId() + "] = " + ((StringLiteralSymbol)localObject4).getTokenType());
/*  355:     */         }
/*  356:     */       }
/*  357:     */     }
/*  358: 454 */     println("");
/*  359: 455 */     flushTokens();
/*  360:     */     
/*  361:     */ 
/*  362: 458 */     genJavadocComment(this.grammar);
/*  363:     */     
/*  364:     */ 
/*  365: 461 */     println("class " + this.lexerClassName + "(" + str + ") :");
/*  366: 462 */     this.tabs += 1;
/*  367:     */     
/*  368: 464 */     printGrammarAction(this.grammar);
/*  369:     */     
/*  370:     */ 
/*  371:     */ 
/*  372:     */ 
/*  373:     */ 
/*  374: 470 */     println("def __init__(self, *argv, **kwargs) :");
/*  375: 471 */     this.tabs += 1;
/*  376: 472 */     println(str + ".__init__(self, *argv, **kwargs)");
/*  377:     */     
/*  378:     */ 
/*  379:     */ 
/*  380:     */ 
/*  381: 477 */     println("self.caseSensitiveLiterals = " + toString(paramLexerGrammar.caseSensitiveLiterals));
/*  382: 478 */     println("self.setCaseSensitive(" + toString(paramLexerGrammar.caseSensitive) + ")");
/*  383: 479 */     println("self.literals = literals");
/*  384: 483 */     if (this.grammar.debuggingOutput)
/*  385:     */     {
/*  386: 484 */       println("ruleNames[] = [");
/*  387: 485 */       localObject3 = this.grammar.rules.elements();
/*  388: 486 */       i = 0;
/*  389: 487 */       this.tabs += 1;
/*  390: 488 */       while (((Enumeration)localObject3).hasMoreElements())
/*  391:     */       {
/*  392: 489 */         localObject4 = (GrammarSymbol)((Enumeration)localObject3).nextElement();
/*  393: 490 */         if ((localObject4 instanceof RuleSymbol)) {
/*  394: 491 */           println("\"" + ((RuleSymbol)localObject4).getId() + "\",");
/*  395:     */         }
/*  396:     */       }
/*  397: 493 */       this.tabs -= 1;
/*  398: 494 */       println("]");
/*  399:     */     }
/*  400: 497 */     genHeaderInit(this.grammar);
/*  401:     */     
/*  402: 499 */     this.tabs -= 1;
/*  403:     */     
/*  404:     */ 
/*  405:     */ 
/*  406:     */ 
/*  407:     */ 
/*  408:     */ 
/*  409:     */ 
/*  410:     */ 
/*  411: 508 */     genNextToken();
/*  412: 509 */     println("");
/*  413:     */     
/*  414:     */ 
/*  415: 512 */     Object localObject3 = this.grammar.rules.elements();
/*  416: 513 */     int i = 0;
/*  417: 514 */     while (((Enumeration)localObject3).hasMoreElements())
/*  418:     */     {
/*  419: 515 */       localObject4 = (RuleSymbol)((Enumeration)localObject3).nextElement();
/*  420: 517 */       if (!((RuleSymbol)localObject4).getId().equals("mnextToken")) {
/*  421: 518 */         genRule((RuleSymbol)localObject4, false, i++);
/*  422:     */       }
/*  423: 520 */       exitIfError();
/*  424:     */     }
/*  425: 524 */     if (this.grammar.debuggingOutput) {
/*  426: 525 */       genSemPredMap();
/*  427:     */     }
/*  428: 528 */     genBitsets(this.bitsetsUsed, ((LexerGrammar)this.grammar).charVocabulary.size());
/*  429: 529 */     println("");
/*  430:     */     
/*  431: 531 */     genHeaderMain(this.grammar);
/*  432:     */     
/*  433:     */ 
/*  434: 534 */     this.currentOutput.close();
/*  435: 535 */     this.currentOutput = null;
/*  436:     */   }
/*  437:     */   
/*  438:     */   protected void genHeaderMain(Grammar paramGrammar)
/*  439:     */   {
/*  440: 540 */     String str1 = paramGrammar.getClassName() + "." + "__main__";
/*  441: 541 */     String str2 = this.behavior.getHeaderAction(str1);
/*  442: 543 */     if (isEmpty(str2)) {
/*  443: 544 */       str2 = this.behavior.getHeaderAction("__main__");
/*  444:     */     }
/*  445:     */     int i;
/*  446: 546 */     if (isEmpty(str2))
/*  447:     */     {
/*  448: 547 */       if ((paramGrammar instanceof LexerGrammar))
/*  449:     */       {
/*  450: 548 */         i = this.tabs;
/*  451: 549 */         this.tabs = 0;
/*  452: 550 */         println("### __main__ header action >>> ");
/*  453: 551 */         genLexerTest();
/*  454: 552 */         this.tabs = 0;
/*  455: 553 */         println("### __main__ header action <<< ");
/*  456: 554 */         this.tabs = i;
/*  457:     */       }
/*  458:     */     }
/*  459:     */     else
/*  460:     */     {
/*  461: 557 */       i = this.tabs;
/*  462: 558 */       this.tabs = 0;
/*  463: 559 */       println("");
/*  464: 560 */       println("### __main__ header action >>> ");
/*  465: 561 */       printMainFunc(str2);
/*  466: 562 */       this.tabs = 0;
/*  467: 563 */       println("### __main__ header action <<< ");
/*  468: 564 */       this.tabs = i;
/*  469:     */     }
/*  470:     */   }
/*  471:     */   
/*  472:     */   protected void genHeaderInit(Grammar paramGrammar)
/*  473:     */   {
/*  474: 570 */     String str1 = paramGrammar.getClassName() + "." + "__init__";
/*  475: 571 */     String str2 = this.behavior.getHeaderAction(str1);
/*  476: 573 */     if (isEmpty(str2)) {
/*  477: 574 */       str2 = this.behavior.getHeaderAction("__init__");
/*  478:     */     }
/*  479: 576 */     if (!isEmpty(str2))
/*  480:     */     {
/*  481: 579 */       int i = this.tabs;
/*  482: 580 */       println("### __init__ header action >>> ");
/*  483: 581 */       printActionCode(str2, 0);
/*  484: 582 */       this.tabs = i;
/*  485: 583 */       println("### __init__ header action <<< ");
/*  486:     */     }
/*  487:     */   }
/*  488:     */   
/*  489:     */   protected void printMainFunc(String paramString)
/*  490:     */   {
/*  491: 588 */     int i = this.tabs;
/*  492: 589 */     this.tabs = 0;
/*  493: 590 */     println("if __name__ == '__main__':");
/*  494: 591 */     this.tabs += 1;
/*  495: 592 */     printActionCode(paramString, 0);
/*  496: 593 */     this.tabs -= 1;
/*  497: 594 */     this.tabs = i;
/*  498:     */   }
/*  499:     */   
/*  500:     */   public void gen(OneOrMoreBlock paramOneOrMoreBlock)
/*  501:     */   {
/*  502: 604 */     int i = this.tabs;
/*  503:     */     
/*  504: 606 */     genBlockPreamble(paramOneOrMoreBlock);
/*  505:     */     String str1;
/*  506: 608 */     if (paramOneOrMoreBlock.getLabel() != null) {
/*  507: 610 */       str1 = "_cnt_" + paramOneOrMoreBlock.getLabel();
/*  508:     */     } else {
/*  509: 613 */       str1 = "_cnt" + paramOneOrMoreBlock.ID;
/*  510:     */     }
/*  511: 615 */     println("" + str1 + "= 0");
/*  512: 616 */     println("while True:");
/*  513: 617 */     this.tabs += 1;
/*  514: 618 */     i = this.tabs;
/*  515:     */     
/*  516:     */ 
/*  517: 621 */     genBlockInitAction(paramOneOrMoreBlock);
/*  518:     */     
/*  519:     */ 
/*  520: 624 */     String str2 = this.currentASTResult;
/*  521: 625 */     if (paramOneOrMoreBlock.getLabel() != null) {
/*  522: 626 */       this.currentASTResult = paramOneOrMoreBlock.getLabel();
/*  523:     */     }
/*  524: 629 */     boolean bool = this.grammar.theLLkAnalyzer.deterministic(paramOneOrMoreBlock);
/*  525:     */     
/*  526:     */ 
/*  527:     */ 
/*  528:     */ 
/*  529:     */ 
/*  530:     */ 
/*  531:     */ 
/*  532:     */ 
/*  533:     */ 
/*  534:     */ 
/*  535:     */ 
/*  536: 641 */     int j = 0;
/*  537: 642 */     int k = this.grammar.maxk;
/*  538: 644 */     if ((!paramOneOrMoreBlock.greedy) && (paramOneOrMoreBlock.exitLookaheadDepth <= this.grammar.maxk) && (paramOneOrMoreBlock.exitCache[paramOneOrMoreBlock.exitLookaheadDepth].containsEpsilon()))
/*  539:     */     {
/*  540: 648 */       j = 1;
/*  541: 649 */       k = paramOneOrMoreBlock.exitLookaheadDepth;
/*  542:     */     }
/*  543: 653 */     else if ((!paramOneOrMoreBlock.greedy) && (paramOneOrMoreBlock.exitLookaheadDepth == 2147483647))
/*  544:     */     {
/*  545: 655 */       j = 1;
/*  546:     */     }
/*  547: 661 */     if (j != 0)
/*  548:     */     {
/*  549: 663 */       println("### nongreedy (...)+ loop; exit depth is " + paramOneOrMoreBlock.exitLookaheadDepth);
/*  550:     */       
/*  551: 665 */       String str3 = getLookaheadTestExpression(paramOneOrMoreBlock.exitCache, k);
/*  552:     */       
/*  553:     */ 
/*  554:     */ 
/*  555:     */ 
/*  556: 670 */       println("### nongreedy exit test");
/*  557: 671 */       println("if " + str1 + " >= 1 and " + str3 + ":");
/*  558: 672 */       this.tabs += 1;
/*  559: 673 */       println("break");
/*  560: 674 */       this.tabs -= 1;
/*  561:     */     }
/*  562: 678 */     int m = this.tabs;
/*  563: 679 */     PythonBlockFinishingInfo localPythonBlockFinishingInfo = genCommonBlock(paramOneOrMoreBlock, false);
/*  564: 680 */     genBlockFinish(localPythonBlockFinishingInfo, "break");
/*  565: 681 */     this.tabs = m;
/*  566:     */     
/*  567:     */ 
/*  568:     */ 
/*  569: 685 */     this.tabs = i;
/*  570: 686 */     println(str1 + " += 1");
/*  571: 687 */     this.tabs = i;
/*  572: 688 */     this.tabs -= 1;
/*  573: 689 */     println("if " + str1 + " < 1:");
/*  574: 690 */     this.tabs += 1;
/*  575: 691 */     println(this.throwNoViable);
/*  576: 692 */     this.tabs -= 1;
/*  577:     */     
/*  578: 694 */     this.currentASTResult = str2;
/*  579:     */   }
/*  580:     */   
/*  581:     */   public void gen(ParserGrammar paramParserGrammar)
/*  582:     */     throws IOException
/*  583:     */   {
/*  584: 703 */     if (paramParserGrammar.debuggingOutput) {
/*  585: 704 */       this.semPreds = new Vector();
/*  586:     */     }
/*  587: 706 */     setGrammar(paramParserGrammar);
/*  588: 707 */     if (!(this.grammar instanceof ParserGrammar)) {
/*  589: 708 */       this.antlrTool.panic("Internal error generating parser");
/*  590:     */     }
/*  591: 713 */     setupOutput(this.grammar.getClassName());
/*  592:     */     
/*  593: 715 */     this.genAST = this.grammar.buildAST;
/*  594:     */     
/*  595: 717 */     this.tabs = 0;
/*  596:     */     
/*  597:     */ 
/*  598: 720 */     genHeader();
/*  599:     */     
/*  600:     */ 
/*  601: 723 */     println("### import antlr and other modules ..");
/*  602: 724 */     println("import sys");
/*  603: 725 */     println("import antlr");
/*  604: 726 */     println("");
/*  605: 727 */     println("version = sys.version.split()[0]");
/*  606: 728 */     println("if version < '2.2.1':");
/*  607: 729 */     this.tabs += 1;
/*  608: 730 */     println("False = 0");
/*  609: 731 */     this.tabs -= 1;
/*  610: 732 */     println("if version < '2.3':");
/*  611: 733 */     this.tabs += 1;
/*  612: 734 */     println("True = not False");
/*  613: 735 */     this.tabs -= 1;
/*  614:     */     
/*  615: 737 */     println("### header action >>> ");
/*  616: 738 */     printActionCode(this.behavior.getHeaderAction(""), 0);
/*  617: 739 */     println("### header action <<< ");
/*  618:     */     
/*  619: 741 */     println("### preamble action>>>");
/*  620:     */     
/*  621: 743 */     printActionCode(this.grammar.preambleAction.getText(), 0);
/*  622: 744 */     println("### preamble action <<<");
/*  623:     */     
/*  624: 746 */     flushTokens();
/*  625:     */     
/*  626:     */ 
/*  627: 749 */     String str = null;
/*  628: 750 */     if (this.grammar.superClass != null) {
/*  629: 751 */       str = this.grammar.superClass;
/*  630:     */     } else {
/*  631: 753 */       str = "antlr." + this.grammar.getSuperClass();
/*  632:     */     }
/*  633: 756 */     genJavadocComment(this.grammar);
/*  634:     */     
/*  635:     */ 
/*  636: 759 */     Object localObject1 = "";
/*  637: 760 */     Token localToken = (Token)this.grammar.options.get("classHeaderPrefix");
/*  638: 761 */     if (localToken != null)
/*  639:     */     {
/*  640: 762 */       localObject2 = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/*  641: 763 */       if (localObject2 != null) {
/*  642: 764 */         localObject1 = localObject2;
/*  643:     */       }
/*  644:     */     }
/*  645: 768 */     print("class " + this.parserClassName + "(" + str);
/*  646: 769 */     println("):");
/*  647: 770 */     this.tabs += 1;
/*  648:     */     GrammarSymbol localGrammarSymbol;
/*  649: 774 */     if (this.grammar.debuggingOutput)
/*  650:     */     {
/*  651: 775 */       println("_ruleNames = [");
/*  652:     */       
/*  653: 777 */       localObject2 = this.grammar.rules.elements();
/*  654: 778 */       i = 0;
/*  655: 779 */       this.tabs += 1;
/*  656: 780 */       while (((Enumeration)localObject2).hasMoreElements())
/*  657:     */       {
/*  658: 781 */         localGrammarSymbol = (GrammarSymbol)((Enumeration)localObject2).nextElement();
/*  659: 782 */         if ((localGrammarSymbol instanceof RuleSymbol)) {
/*  660: 783 */           println("\"" + ((RuleSymbol)localGrammarSymbol).getId() + "\",");
/*  661:     */         }
/*  662:     */       }
/*  663: 785 */       this.tabs -= 1;
/*  664: 786 */       println("]");
/*  665:     */     }
/*  666: 790 */     printGrammarAction(this.grammar);
/*  667:     */     
/*  668:     */ 
/*  669: 793 */     println("");
/*  670: 794 */     println("def __init__(self, *args, **kwargs):");
/*  671: 795 */     this.tabs += 1;
/*  672: 796 */     println(str + ".__init__(self, *args, **kwargs)");
/*  673: 797 */     println("self.tokenNames = _tokenNames");
/*  674: 800 */     if (this.grammar.debuggingOutput)
/*  675:     */     {
/*  676: 801 */       println("self.ruleNames  = _ruleNames");
/*  677: 802 */       println("self.semPredNames = _semPredNames");
/*  678: 803 */       println("self.setupDebugging(self.tokenBuf)");
/*  679:     */     }
/*  680: 805 */     if (this.grammar.buildAST)
/*  681:     */     {
/*  682: 806 */       println("self.buildTokenTypeASTClassMap()");
/*  683: 807 */       println("self.astFactory = antlr.ASTFactory(self.getTokenTypeToASTClassMap())");
/*  684: 808 */       if (this.labeledElementASTType != null) {
/*  685: 810 */         println("self.astFactory.setASTNodeClass(" + this.labeledElementASTType + ")");
/*  686:     */       }
/*  687:     */     }
/*  688: 814 */     genHeaderInit(this.grammar);
/*  689: 815 */     println("");
/*  690:     */     
/*  691:     */ 
/*  692: 818 */     Object localObject2 = this.grammar.rules.elements();
/*  693: 819 */     int i = 0;
/*  694: 820 */     while (((Enumeration)localObject2).hasMoreElements())
/*  695:     */     {
/*  696: 821 */       localGrammarSymbol = (GrammarSymbol)((Enumeration)localObject2).nextElement();
/*  697: 822 */       if ((localGrammarSymbol instanceof RuleSymbol))
/*  698:     */       {
/*  699: 823 */         RuleSymbol localRuleSymbol = (RuleSymbol)localGrammarSymbol;
/*  700: 824 */         genRule(localRuleSymbol, localRuleSymbol.references.size() == 0, i++);
/*  701:     */       }
/*  702: 826 */       exitIfError();
/*  703:     */     }
/*  704: 830 */     if (this.grammar.buildAST) {
/*  705: 831 */       genTokenASTNodeMap();
/*  706:     */     }
/*  707: 835 */     genTokenStrings();
/*  708:     */     
/*  709:     */ 
/*  710: 838 */     genBitsets(this.bitsetsUsed, this.grammar.tokenManager.maxTokenType());
/*  711: 841 */     if (this.grammar.debuggingOutput) {
/*  712: 842 */       genSemPredMap();
/*  713:     */     }
/*  714: 845 */     println("");
/*  715:     */     
/*  716: 847 */     this.tabs = 0;
/*  717: 848 */     genHeaderMain(this.grammar);
/*  718:     */     
/*  719: 850 */     this.currentOutput.close();
/*  720: 851 */     this.currentOutput = null;
/*  721:     */   }
/*  722:     */   
/*  723:     */   public void gen(RuleRefElement paramRuleRefElement)
/*  724:     */   {
/*  725: 858 */     if (this.DEBUG_CODE_GENERATOR) {
/*  726: 858 */       System.out.println("genRR(" + paramRuleRefElement + ")");
/*  727:     */     }
/*  728: 859 */     RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(paramRuleRefElement.targetRule);
/*  729: 860 */     if ((localRuleSymbol == null) || (!localRuleSymbol.isDefined()))
/*  730:     */     {
/*  731: 862 */       this.antlrTool.error("Rule '" + paramRuleRefElement.targetRule + "' is not defined", this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/*  732: 863 */       return;
/*  733:     */     }
/*  734: 865 */     if (!(localRuleSymbol instanceof RuleSymbol))
/*  735:     */     {
/*  736: 867 */       this.antlrTool.error("'" + paramRuleRefElement.targetRule + "' does not name a grammar rule", this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/*  737: 868 */       return;
/*  738:     */     }
/*  739: 871 */     genErrorTryForElement(paramRuleRefElement);
/*  740: 875 */     if (((this.grammar instanceof TreeWalkerGrammar)) && (paramRuleRefElement.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/*  741: 878 */       println(paramRuleRefElement.getLabel() + " = antlr.ifelse(_t == antlr.ASTNULL, None, " + this.lt1Value + ")");
/*  742:     */     }
/*  743: 882 */     if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramRuleRefElement.getAutoGenType() == 3))) {
/*  744: 883 */       println("_saveIndex = self.text.length()");
/*  745:     */     }
/*  746: 887 */     printTabs();
/*  747: 888 */     if (paramRuleRefElement.idAssign != null)
/*  748:     */     {
/*  749: 890 */       if (localRuleSymbol.block.returnAction == null) {
/*  750: 891 */         this.antlrTool.warning("Rule '" + paramRuleRefElement.targetRule + "' has no return type", this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/*  751:     */       }
/*  752: 893 */       _print(paramRuleRefElement.idAssign + "=");
/*  753:     */     }
/*  754: 897 */     else if ((!(this.grammar instanceof LexerGrammar)) && (this.syntacticPredLevel == 0) && (localRuleSymbol.block.returnAction != null))
/*  755:     */     {
/*  756: 898 */       this.antlrTool.warning("Rule '" + paramRuleRefElement.targetRule + "' returns a value", this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/*  757:     */     }
/*  758: 903 */     GenRuleInvocation(paramRuleRefElement);
/*  759: 906 */     if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramRuleRefElement.getAutoGenType() == 3))) {
/*  760: 907 */       println("self.text.setLength(_saveIndex)");
/*  761:     */     }
/*  762: 911 */     if (this.syntacticPredLevel == 0)
/*  763:     */     {
/*  764: 912 */       int i = (this.grammar.hasSyntacticPredicate) && (((this.grammar.buildAST) && (paramRuleRefElement.getLabel() != null)) || ((this.genAST) && (paramRuleRefElement.getAutoGenType() == 1))) ? 1 : 0;
/*  765: 918 */       if ((i == 0) || (
/*  766:     */       
/*  767:     */ 
/*  768:     */ 
/*  769:     */ 
/*  770: 923 */         (this.grammar.buildAST) && (paramRuleRefElement.getLabel() != null))) {
/*  771: 925 */         println(paramRuleRefElement.getLabel() + "_AST = self.returnAST");
/*  772:     */       }
/*  773: 927 */       if (this.genAST) {
/*  774: 928 */         switch (paramRuleRefElement.getAutoGenType())
/*  775:     */         {
/*  776:     */         case 1: 
/*  777: 930 */           println("self.addASTChild(currentAST, self.returnAST)");
/*  778: 931 */           break;
/*  779:     */         case 2: 
/*  780: 933 */           this.antlrTool.error("Internal: encountered ^ after rule reference");
/*  781: 934 */           break;
/*  782:     */         }
/*  783:     */       }
/*  784: 941 */       if (((this.grammar instanceof LexerGrammar)) && (paramRuleRefElement.getLabel() != null)) {
/*  785: 942 */         println(paramRuleRefElement.getLabel() + " = self._returnToken");
/*  786:     */       }
/*  787: 945 */       if (i == 0) {}
/*  788:     */     }
/*  789: 948 */     genErrorCatchForElement(paramRuleRefElement);
/*  790:     */   }
/*  791:     */   
/*  792:     */   public void gen(StringLiteralElement paramStringLiteralElement)
/*  793:     */   {
/*  794: 955 */     if (this.DEBUG_CODE_GENERATOR) {
/*  795: 955 */       System.out.println("genString(" + paramStringLiteralElement + ")");
/*  796:     */     }
/*  797: 958 */     if ((paramStringLiteralElement.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/*  798: 959 */       println(paramStringLiteralElement.getLabel() + " = " + this.lt1Value + "");
/*  799:     */     }
/*  800: 963 */     genElementAST(paramStringLiteralElement);
/*  801:     */     
/*  802:     */ 
/*  803: 966 */     boolean bool = this.saveText;
/*  804: 967 */     this.saveText = ((this.saveText) && (paramStringLiteralElement.getAutoGenType() == 1));
/*  805:     */     
/*  806:     */ 
/*  807: 970 */     genMatch(paramStringLiteralElement);
/*  808:     */     
/*  809: 972 */     this.saveText = bool;
/*  810: 975 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/*  811: 976 */       println("_t = _t.getNextSibling()");
/*  812:     */     }
/*  813:     */   }
/*  814:     */   
/*  815:     */   public void gen(TokenRangeElement paramTokenRangeElement)
/*  816:     */   {
/*  817: 984 */     genErrorTryForElement(paramTokenRangeElement);
/*  818: 985 */     if ((paramTokenRangeElement.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/*  819: 986 */       println(paramTokenRangeElement.getLabel() + " = " + this.lt1Value);
/*  820:     */     }
/*  821: 990 */     genElementAST(paramTokenRangeElement);
/*  822:     */     
/*  823:     */ 
/*  824: 993 */     println("self.matchRange(u" + paramTokenRangeElement.beginText + ", u" + paramTokenRangeElement.endText + ")");
/*  825: 994 */     genErrorCatchForElement(paramTokenRangeElement);
/*  826:     */   }
/*  827:     */   
/*  828:     */   public void gen(TokenRefElement paramTokenRefElement)
/*  829:     */   {
/*  830:1001 */     if (this.DEBUG_CODE_GENERATOR) {
/*  831:1001 */       System.out.println("genTokenRef(" + paramTokenRefElement + ")");
/*  832:     */     }
/*  833:1002 */     if ((this.grammar instanceof LexerGrammar)) {
/*  834:1003 */       this.antlrTool.panic("Token reference found in lexer");
/*  835:     */     }
/*  836:1005 */     genErrorTryForElement(paramTokenRefElement);
/*  837:1007 */     if ((paramTokenRefElement.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/*  838:1008 */       println(paramTokenRefElement.getLabel() + " = " + this.lt1Value + "");
/*  839:     */     }
/*  840:1012 */     genElementAST(paramTokenRefElement);
/*  841:     */     
/*  842:1014 */     genMatch(paramTokenRefElement);
/*  843:1015 */     genErrorCatchForElement(paramTokenRefElement);
/*  844:1018 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/*  845:1019 */       println("_t = _t.getNextSibling()");
/*  846:     */     }
/*  847:     */   }
/*  848:     */   
/*  849:     */   public void gen(TreeElement paramTreeElement)
/*  850:     */   {
/*  851:1025 */     println("_t" + paramTreeElement.ID + " = _t");
/*  852:1028 */     if (paramTreeElement.root.getLabel() != null) {
/*  853:1029 */       println(paramTreeElement.root.getLabel() + " = antlr.ifelse(_t == antlr.ASTNULL, None, _t)");
/*  854:     */     }
/*  855:1033 */     if (paramTreeElement.root.getAutoGenType() == 3)
/*  856:     */     {
/*  857:1034 */       this.antlrTool.error("Suffixing a root node with '!' is not implemented", this.grammar.getFilename(), paramTreeElement.getLine(), paramTreeElement.getColumn());
/*  858:     */       
/*  859:1036 */       paramTreeElement.root.setAutoGenType(1);
/*  860:     */     }
/*  861:1038 */     if (paramTreeElement.root.getAutoGenType() == 2)
/*  862:     */     {
/*  863:1039 */       this.antlrTool.warning("Suffixing a root node with '^' is redundant; already a root", this.grammar.getFilename(), paramTreeElement.getLine(), paramTreeElement.getColumn());
/*  864:     */       
/*  865:1041 */       paramTreeElement.root.setAutoGenType(1);
/*  866:     */     }
/*  867:1045 */     genElementAST(paramTreeElement.root);
/*  868:1046 */     if (this.grammar.buildAST)
/*  869:     */     {
/*  870:1048 */       println("_currentAST" + paramTreeElement.ID + " = currentAST.copy()");
/*  871:     */       
/*  872:1050 */       println("currentAST.root = currentAST.child");
/*  873:1051 */       println("currentAST.child = None");
/*  874:     */     }
/*  875:1055 */     if ((paramTreeElement.root instanceof WildcardElement)) {
/*  876:1056 */       println("if not _t: raise antlr.MismatchedTokenException()");
/*  877:     */     } else {
/*  878:1059 */       genMatch(paramTreeElement.root);
/*  879:     */     }
/*  880:1062 */     println("_t = _t.getFirstChild()");
/*  881:1065 */     for (int i = 0; i < paramTreeElement.getAlternatives().size(); i++)
/*  882:     */     {
/*  883:1066 */       Alternative localAlternative = paramTreeElement.getAlternativeAt(i);
/*  884:1067 */       AlternativeElement localAlternativeElement = localAlternative.head;
/*  885:1068 */       while (localAlternativeElement != null)
/*  886:     */       {
/*  887:1069 */         localAlternativeElement.generate();
/*  888:1070 */         localAlternativeElement = localAlternativeElement.next;
/*  889:     */       }
/*  890:     */     }
/*  891:1074 */     if (this.grammar.buildAST) {
/*  892:1077 */       println("currentAST = _currentAST" + paramTreeElement.ID + "");
/*  893:     */     }
/*  894:1080 */     println("_t = _t" + paramTreeElement.ID + "");
/*  895:     */     
/*  896:1082 */     println("_t = _t.getNextSibling()");
/*  897:     */   }
/*  898:     */   
/*  899:     */   public void gen(TreeWalkerGrammar paramTreeWalkerGrammar)
/*  900:     */     throws IOException
/*  901:     */   {
/*  902:1090 */     setGrammar(paramTreeWalkerGrammar);
/*  903:1091 */     if (!(this.grammar instanceof TreeWalkerGrammar)) {
/*  904:1092 */       this.antlrTool.panic("Internal error generating tree-walker");
/*  905:     */     }
/*  906:1097 */     setupOutput(this.grammar.getClassName());
/*  907:     */     
/*  908:1099 */     this.genAST = this.grammar.buildAST;
/*  909:1100 */     this.tabs = 0;
/*  910:     */     
/*  911:     */ 
/*  912:1103 */     genHeader();
/*  913:     */     
/*  914:     */ 
/*  915:1106 */     println("### import antlr and other modules ..");
/*  916:1107 */     println("import sys");
/*  917:1108 */     println("import antlr");
/*  918:1109 */     println("");
/*  919:1110 */     println("version = sys.version.split()[0]");
/*  920:1111 */     println("if version < '2.2.1':");
/*  921:1112 */     this.tabs += 1;
/*  922:1113 */     println("False = 0");
/*  923:1114 */     this.tabs -= 1;
/*  924:1115 */     println("if version < '2.3':");
/*  925:1116 */     this.tabs += 1;
/*  926:1117 */     println("True = not False");
/*  927:1118 */     this.tabs -= 1;
/*  928:     */     
/*  929:1120 */     println("### header action >>> ");
/*  930:1121 */     printActionCode(this.behavior.getHeaderAction(""), 0);
/*  931:1122 */     println("### header action <<< ");
/*  932:     */     
/*  933:1124 */     flushTokens();
/*  934:     */     
/*  935:1126 */     println("### user code>>>");
/*  936:     */     
/*  937:1128 */     printActionCode(this.grammar.preambleAction.getText(), 0);
/*  938:1129 */     println("### user code<<<");
/*  939:     */     
/*  940:     */ 
/*  941:1132 */     String str1 = null;
/*  942:1133 */     if (this.grammar.superClass != null) {
/*  943:1134 */       str1 = this.grammar.superClass;
/*  944:     */     } else {
/*  945:1137 */       str1 = "antlr." + this.grammar.getSuperClass();
/*  946:     */     }
/*  947:1139 */     println("");
/*  948:     */     
/*  949:     */ 
/*  950:1142 */     Object localObject1 = "";
/*  951:1143 */     Token localToken = (Token)this.grammar.options.get("classHeaderPrefix");
/*  952:1144 */     if (localToken != null)
/*  953:     */     {
/*  954:1145 */       localObject2 = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/*  955:1146 */       if (localObject2 != null) {
/*  956:1147 */         localObject1 = localObject2;
/*  957:     */       }
/*  958:     */     }
/*  959:1152 */     genJavadocComment(this.grammar);
/*  960:     */     
/*  961:1154 */     println("class " + this.treeWalkerClassName + "(" + str1 + "):");
/*  962:1155 */     this.tabs += 1;
/*  963:     */     
/*  964:     */ 
/*  965:1158 */     println("");
/*  966:1159 */     println("# ctor ..");
/*  967:1160 */     println("def __init__(self, *args, **kwargs):");
/*  968:1161 */     this.tabs += 1;
/*  969:1162 */     println(str1 + ".__init__(self, *args, **kwargs)");
/*  970:1163 */     println("self.tokenNames = _tokenNames");
/*  971:1164 */     genHeaderInit(this.grammar);
/*  972:1165 */     this.tabs -= 1;
/*  973:1166 */     println("");
/*  974:     */     
/*  975:     */ 
/*  976:1169 */     printGrammarAction(this.grammar);
/*  977:     */     
/*  978:     */ 
/*  979:1172 */     Object localObject2 = this.grammar.rules.elements();
/*  980:1173 */     int i = 0;
/*  981:1174 */     String str2 = "";
/*  982:1175 */     while (((Enumeration)localObject2).hasMoreElements())
/*  983:     */     {
/*  984:1176 */       GrammarSymbol localGrammarSymbol = (GrammarSymbol)((Enumeration)localObject2).nextElement();
/*  985:1177 */       if ((localGrammarSymbol instanceof RuleSymbol))
/*  986:     */       {
/*  987:1178 */         RuleSymbol localRuleSymbol = (RuleSymbol)localGrammarSymbol;
/*  988:1179 */         genRule(localRuleSymbol, localRuleSymbol.references.size() == 0, i++);
/*  989:     */       }
/*  990:1181 */       exitIfError();
/*  991:     */     }
/*  992:1185 */     genTokenStrings();
/*  993:     */     
/*  994:     */ 
/*  995:1188 */     genBitsets(this.bitsetsUsed, this.grammar.tokenManager.maxTokenType());
/*  996:     */     
/*  997:1190 */     this.tabs = 0;
/*  998:1191 */     genHeaderMain(this.grammar);
/*  999:     */     
/* 1000:1193 */     this.currentOutput.close();
/* 1001:1194 */     this.currentOutput = null;
/* 1002:     */   }
/* 1003:     */   
/* 1004:     */   public void gen(WildcardElement paramWildcardElement)
/* 1005:     */   {
/* 1006:1202 */     if ((paramWildcardElement.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/* 1007:1203 */       println(paramWildcardElement.getLabel() + " = " + this.lt1Value + "");
/* 1008:     */     }
/* 1009:1207 */     genElementAST(paramWildcardElement);
/* 1010:1209 */     if ((this.grammar instanceof TreeWalkerGrammar))
/* 1011:     */     {
/* 1012:1210 */       println("if not _t:");
/* 1013:1211 */       this.tabs += 1;
/* 1014:1212 */       println("raise antlr.MismatchedTokenException()");
/* 1015:1213 */       this.tabs -= 1;
/* 1016:     */     }
/* 1017:1215 */     else if ((this.grammar instanceof LexerGrammar))
/* 1018:     */     {
/* 1019:1216 */       if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramWildcardElement.getAutoGenType() == 3))) {
/* 1020:1218 */         println("_saveIndex = self.text.length()");
/* 1021:     */       }
/* 1022:1220 */       println("self.matchNot(antlr.EOF_CHAR)");
/* 1023:1221 */       if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramWildcardElement.getAutoGenType() == 3))) {
/* 1024:1223 */         println("self.text.setLength(_saveIndex)");
/* 1025:     */       }
/* 1026:     */     }
/* 1027:     */     else
/* 1028:     */     {
/* 1029:1227 */       println("self.matchNot(" + getValueString(1, false) + ")");
/* 1030:     */     }
/* 1031:1231 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 1032:1232 */       println("_t = _t.getNextSibling()");
/* 1033:     */     }
/* 1034:     */   }
/* 1035:     */   
/* 1036:     */   public void gen(ZeroOrMoreBlock paramZeroOrMoreBlock)
/* 1037:     */   {
/* 1038:1241 */     int i = this.tabs;
/* 1039:1242 */     genBlockPreamble(paramZeroOrMoreBlock);
/* 1040:     */     
/* 1041:1244 */     println("while True:");
/* 1042:1245 */     this.tabs += 1;
/* 1043:1246 */     i = this.tabs;
/* 1044:     */     
/* 1045:     */ 
/* 1046:1249 */     genBlockInitAction(paramZeroOrMoreBlock);
/* 1047:     */     
/* 1048:     */ 
/* 1049:1252 */     String str1 = this.currentASTResult;
/* 1050:1253 */     if (paramZeroOrMoreBlock.getLabel() != null) {
/* 1051:1254 */       this.currentASTResult = paramZeroOrMoreBlock.getLabel();
/* 1052:     */     }
/* 1053:1257 */     boolean bool = this.grammar.theLLkAnalyzer.deterministic(paramZeroOrMoreBlock);
/* 1054:     */     
/* 1055:     */ 
/* 1056:     */ 
/* 1057:     */ 
/* 1058:     */ 
/* 1059:     */ 
/* 1060:     */ 
/* 1061:     */ 
/* 1062:     */ 
/* 1063:     */ 
/* 1064:     */ 
/* 1065:1269 */     int j = 0;
/* 1066:1270 */     int k = this.grammar.maxk;
/* 1067:1272 */     if ((!paramZeroOrMoreBlock.greedy) && (paramZeroOrMoreBlock.exitLookaheadDepth <= this.grammar.maxk) && (paramZeroOrMoreBlock.exitCache[paramZeroOrMoreBlock.exitLookaheadDepth].containsEpsilon()))
/* 1068:     */     {
/* 1069:1275 */       j = 1;
/* 1070:1276 */       k = paramZeroOrMoreBlock.exitLookaheadDepth;
/* 1071:     */     }
/* 1072:1278 */     else if ((!paramZeroOrMoreBlock.greedy) && (paramZeroOrMoreBlock.exitLookaheadDepth == 2147483647))
/* 1073:     */     {
/* 1074:1280 */       j = 1;
/* 1075:     */     }
/* 1076:1282 */     if (j != 0)
/* 1077:     */     {
/* 1078:1283 */       if (this.DEBUG_CODE_GENERATOR) {
/* 1079:1284 */         System.out.println("nongreedy (...)* loop; exit depth is " + paramZeroOrMoreBlock.exitLookaheadDepth);
/* 1080:     */       }
/* 1081:1287 */       String str2 = getLookaheadTestExpression(paramZeroOrMoreBlock.exitCache, k);
/* 1082:     */       
/* 1083:     */ 
/* 1084:1290 */       println("###  nongreedy exit test");
/* 1085:1291 */       println("if (" + str2 + "):");
/* 1086:1292 */       this.tabs += 1;
/* 1087:1293 */       println("break");
/* 1088:1294 */       this.tabs -= 1;
/* 1089:     */     }
/* 1090:1298 */     int m = this.tabs;
/* 1091:1299 */     PythonBlockFinishingInfo localPythonBlockFinishingInfo = genCommonBlock(paramZeroOrMoreBlock, false);
/* 1092:1300 */     genBlockFinish(localPythonBlockFinishingInfo, "break");
/* 1093:1301 */     this.tabs = m;
/* 1094:     */     
/* 1095:1303 */     this.tabs = i;
/* 1096:1304 */     this.tabs -= 1;
/* 1097:     */     
/* 1098:     */ 
/* 1099:1307 */     this.currentASTResult = str1;
/* 1100:     */   }
/* 1101:     */   
/* 1102:     */   protected void genAlt(Alternative paramAlternative, AlternativeBlock paramAlternativeBlock)
/* 1103:     */   {
/* 1104:1316 */     boolean bool1 = this.genAST;
/* 1105:1317 */     this.genAST = ((this.genAST) && (paramAlternative.getAutoGen()));
/* 1106:     */     
/* 1107:1319 */     boolean bool2 = this.saveText;
/* 1108:1320 */     this.saveText = ((this.saveText) && (paramAlternative.getAutoGen()));
/* 1109:     */     
/* 1110:     */ 
/* 1111:1323 */     Hashtable localHashtable = this.treeVariableMap;
/* 1112:1324 */     this.treeVariableMap = new Hashtable();
/* 1113:1327 */     if (paramAlternative.exceptionSpec != null)
/* 1114:     */     {
/* 1115:1328 */       println("try:");
/* 1116:1329 */       this.tabs += 1;
/* 1117:     */     }
/* 1118:1332 */     println("pass");
/* 1119:1333 */     AlternativeElement localAlternativeElement = paramAlternative.head;
/* 1120:1334 */     while (!(localAlternativeElement instanceof BlockEndElement))
/* 1121:     */     {
/* 1122:1335 */       localAlternativeElement.generate();
/* 1123:1336 */       localAlternativeElement = localAlternativeElement.next;
/* 1124:     */     }
/* 1125:1339 */     if (this.genAST) {
/* 1126:1340 */       if ((paramAlternativeBlock instanceof RuleBlock))
/* 1127:     */       {
/* 1128:1342 */         RuleBlock localRuleBlock = (RuleBlock)paramAlternativeBlock;
/* 1129:1343 */         if (this.grammar.hasSyntacticPredicate) {}
/* 1130:1345 */         println(localRuleBlock.getRuleName() + "_AST = currentAST.root");
/* 1131:1346 */         if (!this.grammar.hasSyntacticPredicate) {}
/* 1132:     */       }
/* 1133:1349 */       else if (paramAlternativeBlock.getLabel() != null)
/* 1134:     */       {
/* 1135:1350 */         this.antlrTool.warning("Labeled subrules not yet supported", this.grammar.getFilename(), paramAlternativeBlock.getLine(), paramAlternativeBlock.getColumn());
/* 1136:     */       }
/* 1137:     */     }
/* 1138:1356 */     if (paramAlternative.exceptionSpec != null)
/* 1139:     */     {
/* 1140:1357 */       this.tabs -= 1;
/* 1141:1358 */       genErrorHandler(paramAlternative.exceptionSpec);
/* 1142:     */     }
/* 1143:1361 */     this.genAST = bool1;
/* 1144:1362 */     this.saveText = bool2;
/* 1145:     */     
/* 1146:1364 */     this.treeVariableMap = localHashtable;
/* 1147:     */   }
/* 1148:     */   
/* 1149:     */   protected void genBitsets(Vector paramVector, int paramInt)
/* 1150:     */   {
/* 1151:1380 */     println("");
/* 1152:1381 */     for (int i = 0; i < paramVector.size(); i++)
/* 1153:     */     {
/* 1154:1382 */       BitSet localBitSet = (BitSet)paramVector.elementAt(i);
/* 1155:     */       
/* 1156:1384 */       localBitSet.growToInclude(paramInt);
/* 1157:1385 */       genBitSet(localBitSet, i);
/* 1158:     */     }
/* 1159:     */   }
/* 1160:     */   
/* 1161:     */   private void genBitSet(BitSet paramBitSet, int paramInt)
/* 1162:     */   {
/* 1163:1400 */     int i = this.tabs;
/* 1164:     */     
/* 1165:     */ 
/* 1166:1403 */     this.tabs = 0;
/* 1167:     */     
/* 1168:1405 */     println("");
/* 1169:1406 */     println("### generate bit set");
/* 1170:1407 */     println("def mk" + getBitsetName(paramInt) + "(): ");
/* 1171:     */     
/* 1172:1409 */     this.tabs += 1;
/* 1173:1410 */     int j = paramBitSet.lengthInLongWords();
/* 1174:     */     long[] arrayOfLong;
/* 1175:     */     int k;
/* 1176:1411 */     if (j < 8)
/* 1177:     */     {
/* 1178:1413 */       println("### var1");
/* 1179:1414 */       println("data = [ " + paramBitSet.toStringOfWords() + "]");
/* 1180:     */     }
/* 1181:     */     else
/* 1182:     */     {
/* 1183:1419 */       println("data = [0L] * " + j + " ### init list");
/* 1184:     */       
/* 1185:1421 */       arrayOfLong = paramBitSet.toPackedArray();
/* 1186:1423 */       for (k = 0; k < arrayOfLong.length;) {
/* 1187:1425 */         if (arrayOfLong[k] == 0L)
/* 1188:     */         {
/* 1189:1428 */           k++;
/* 1190:     */         }
/* 1191:1432 */         else if ((k + 1 == arrayOfLong.length) || (arrayOfLong[k] != arrayOfLong[(k + 1)]))
/* 1192:     */         {
/* 1193:1435 */           println("data[" + k + "] =" + arrayOfLong[k] + "L");
/* 1194:1436 */           k++;
/* 1195:     */         }
/* 1196:     */         else
/* 1197:     */         {
/* 1198:1442 */           for (int m = k + 1; (m < arrayOfLong.length) && (arrayOfLong[m] == arrayOfLong[k]); m++) {}
/* 1199:1445 */           long l = arrayOfLong[k];
/* 1200:     */           
/* 1201:1447 */           println("for x in xrange(" + k + ", " + m + "):");
/* 1202:1448 */           this.tabs += 1;
/* 1203:1449 */           println("data[x] = " + l + "L");
/* 1204:1450 */           this.tabs -= 1;
/* 1205:1451 */           k = m;
/* 1206:     */         }
/* 1207:     */       }
/* 1208:     */     }
/* 1209:1455 */     println("return data");
/* 1210:1456 */     this.tabs -= 1;
/* 1211:     */     
/* 1212:     */ 
/* 1213:1459 */     println(getBitsetName(paramInt) + " = antlr.BitSet(mk" + getBitsetName(paramInt) + "())");
/* 1214:     */     
/* 1215:     */ 
/* 1216:     */ 
/* 1217:1463 */     this.tabs = i;
/* 1218:     */   }
/* 1219:     */   
/* 1220:     */   private void genBlockFinish(PythonBlockFinishingInfo paramPythonBlockFinishingInfo, String paramString)
/* 1221:     */   {
/* 1222:1468 */     if ((paramPythonBlockFinishingInfo.needAnErrorClause) && ((paramPythonBlockFinishingInfo.generatedAnIf) || (paramPythonBlockFinishingInfo.generatedSwitch)))
/* 1223:     */     {
/* 1224:1470 */       if (paramPythonBlockFinishingInfo.generatedAnIf) {
/* 1225:1472 */         println("else:");
/* 1226:     */       }
/* 1227:1474 */       this.tabs += 1;
/* 1228:1475 */       println(paramString);
/* 1229:1476 */       this.tabs -= 1;
/* 1230:     */     }
/* 1231:1479 */     if (paramPythonBlockFinishingInfo.postscript != null) {
/* 1232:1480 */       println(paramPythonBlockFinishingInfo.postscript);
/* 1233:     */     }
/* 1234:     */   }
/* 1235:     */   
/* 1236:     */   private void genBlockFinish1(PythonBlockFinishingInfo paramPythonBlockFinishingInfo, String paramString)
/* 1237:     */   {
/* 1238:1487 */     if ((paramPythonBlockFinishingInfo.needAnErrorClause) && ((paramPythonBlockFinishingInfo.generatedAnIf) || (paramPythonBlockFinishingInfo.generatedSwitch)))
/* 1239:     */     {
/* 1240:1490 */       if (paramPythonBlockFinishingInfo.generatedAnIf) {
/* 1241:1493 */         println("else:");
/* 1242:     */       }
/* 1243:1495 */       this.tabs += 1;
/* 1244:1496 */       println(paramString);
/* 1245:1497 */       this.tabs -= 1;
/* 1246:1498 */       if (!paramPythonBlockFinishingInfo.generatedAnIf) {}
/* 1247:     */     }
/* 1248:1505 */     if (paramPythonBlockFinishingInfo.postscript != null) {
/* 1249:1506 */       println(paramPythonBlockFinishingInfo.postscript);
/* 1250:     */     }
/* 1251:     */   }
/* 1252:     */   
/* 1253:     */   protected void genBlockInitAction(AlternativeBlock paramAlternativeBlock)
/* 1254:     */   {
/* 1255:1516 */     if (paramAlternativeBlock.initAction != null) {
/* 1256:1517 */       printAction(processActionForSpecialSymbols(paramAlternativeBlock.initAction, paramAlternativeBlock.getLine(), this.currentRule, null));
/* 1257:     */     }
/* 1258:     */   }
/* 1259:     */   
/* 1260:     */   protected void genBlockPreamble(AlternativeBlock paramAlternativeBlock)
/* 1261:     */   {
/* 1262:1528 */     if ((paramAlternativeBlock instanceof RuleBlock))
/* 1263:     */     {
/* 1264:1529 */       RuleBlock localRuleBlock = (RuleBlock)paramAlternativeBlock;
/* 1265:1530 */       if (localRuleBlock.labeledElements != null) {
/* 1266:1531 */         for (int i = 0; i < localRuleBlock.labeledElements.size(); i++)
/* 1267:     */         {
/* 1268:1532 */           AlternativeElement localAlternativeElement = (AlternativeElement)localRuleBlock.labeledElements.elementAt(i);
/* 1269:1539 */           if (((localAlternativeElement instanceof RuleRefElement)) || (((localAlternativeElement instanceof AlternativeBlock)) && (!(localAlternativeElement instanceof RuleBlock)) && (!(localAlternativeElement instanceof SynPredBlock))))
/* 1270:     */           {
/* 1271:1546 */             if ((!(localAlternativeElement instanceof RuleRefElement)) && (((AlternativeBlock)localAlternativeElement).not) && (this.analyzer.subruleCanBeInverted((AlternativeBlock)localAlternativeElement, this.grammar instanceof LexerGrammar)))
/* 1272:     */             {
/* 1273:1554 */               println(localAlternativeElement.getLabel() + " = " + this.labeledElementInit);
/* 1274:1555 */               if (this.grammar.buildAST) {
/* 1275:1556 */                 genASTDeclaration(localAlternativeElement);
/* 1276:     */               }
/* 1277:     */             }
/* 1278:     */             else
/* 1279:     */             {
/* 1280:1560 */               if (this.grammar.buildAST) {
/* 1281:1564 */                 genASTDeclaration(localAlternativeElement);
/* 1282:     */               }
/* 1283:1566 */               if ((this.grammar instanceof LexerGrammar)) {
/* 1284:1567 */                 println(localAlternativeElement.getLabel() + " = None");
/* 1285:     */               }
/* 1286:1569 */               if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 1287:1572 */                 println(localAlternativeElement.getLabel() + " = " + this.labeledElementInit);
/* 1288:     */               }
/* 1289:     */             }
/* 1290:     */           }
/* 1291:     */           else
/* 1292:     */           {
/* 1293:1579 */             println(localAlternativeElement.getLabel() + " = " + this.labeledElementInit);
/* 1294:1583 */             if (this.grammar.buildAST) {
/* 1295:1584 */               if (((localAlternativeElement instanceof GrammarAtom)) && (((GrammarAtom)localAlternativeElement).getASTNodeType() != null))
/* 1296:     */               {
/* 1297:1586 */                 GrammarAtom localGrammarAtom = (GrammarAtom)localAlternativeElement;
/* 1298:1587 */                 genASTDeclaration(localAlternativeElement, localGrammarAtom.getASTNodeType());
/* 1299:     */               }
/* 1300:     */               else
/* 1301:     */               {
/* 1302:1590 */                 genASTDeclaration(localAlternativeElement);
/* 1303:     */               }
/* 1304:     */             }
/* 1305:     */           }
/* 1306:     */         }
/* 1307:     */       }
/* 1308:     */     }
/* 1309:     */   }
/* 1310:     */   
/* 1311:     */   protected void genCases(BitSet paramBitSet)
/* 1312:     */   {
/* 1313:1603 */     if (this.DEBUG_CODE_GENERATOR) {
/* 1314:1603 */       System.out.println("genCases(" + paramBitSet + ")");
/* 1315:     */     }
/* 1316:1606 */     int[] arrayOfInt = paramBitSet.toArray();
/* 1317:     */     
/* 1318:1608 */     int i = (this.grammar instanceof LexerGrammar) ? 4 : 1;
/* 1319:1609 */     int j = 1;
/* 1320:1610 */     int k = 1;
/* 1321:1611 */     print("elif la1 and la1 in ");
/* 1322:1613 */     if ((this.grammar instanceof LexerGrammar))
/* 1323:     */     {
/* 1324:1615 */       _print("u'");
/* 1325:1616 */       for (m = 0; m < arrayOfInt.length; m++) {
/* 1326:1617 */         _print(getValueString(arrayOfInt[m], false));
/* 1327:     */       }
/* 1328:1619 */       _print("':\n");
/* 1329:1620 */       return;
/* 1330:     */     }
/* 1331:1624 */     _print("[");
/* 1332:1625 */     for (int m = 0; m < arrayOfInt.length; m++)
/* 1333:     */     {
/* 1334:1626 */       _print(getValueString(arrayOfInt[m], false));
/* 1335:1627 */       if (m + 1 < arrayOfInt.length) {
/* 1336:1628 */         _print(",");
/* 1337:     */       }
/* 1338:     */     }
/* 1339:1630 */     _print("]:\n");
/* 1340:     */   }
/* 1341:     */   
/* 1342:     */   public PythonBlockFinishingInfo genCommonBlock(AlternativeBlock paramAlternativeBlock, boolean paramBoolean)
/* 1343:     */   {
/* 1344:1646 */     int i = this.tabs;
/* 1345:1647 */     int j = 0;
/* 1346:1648 */     int k = 0;
/* 1347:1649 */     int m = 0;
/* 1348:     */     
/* 1349:1651 */     PythonBlockFinishingInfo localPythonBlockFinishingInfo = new PythonBlockFinishingInfo();
/* 1350:     */     
/* 1351:     */ 
/* 1352:     */ 
/* 1353:1655 */     boolean bool1 = this.genAST;
/* 1354:1656 */     this.genAST = ((this.genAST) && (paramAlternativeBlock.getAutoGen()));
/* 1355:     */     
/* 1356:1658 */     boolean bool2 = this.saveText;
/* 1357:1659 */     this.saveText = ((this.saveText) && (paramAlternativeBlock.getAutoGen()));
/* 1358:     */     Object localObject1;
/* 1359:1662 */     if ((paramAlternativeBlock.not) && (this.analyzer.subruleCanBeInverted(paramAlternativeBlock, this.grammar instanceof LexerGrammar)))
/* 1360:     */     {
/* 1361:1667 */       if (this.DEBUG_CODE_GENERATOR) {
/* 1362:1667 */         System.out.println("special case: ~(subrule)");
/* 1363:     */       }
/* 1364:1668 */       localObject1 = this.analyzer.look(1, paramAlternativeBlock);
/* 1365:1670 */       if ((paramAlternativeBlock.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/* 1366:1672 */         println(paramAlternativeBlock.getLabel() + " = " + this.lt1Value);
/* 1367:     */       }
/* 1368:1676 */       genElementAST(paramAlternativeBlock);
/* 1369:     */       
/* 1370:1678 */       String str1 = "";
/* 1371:1679 */       if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 1372:1680 */         str1 = "_t, ";
/* 1373:     */       }
/* 1374:1684 */       println("self.match(" + str1 + getBitsetName(markBitsetForGen(((Lookahead)localObject1).fset)) + ")");
/* 1375:1687 */       if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 1376:1688 */         println("_t = _t.getNextSibling()");
/* 1377:     */       }
/* 1378:1690 */       return localPythonBlockFinishingInfo;
/* 1379:     */     }
/* 1380:1695 */     if (paramAlternativeBlock.getAlternatives().size() == 1)
/* 1381:     */     {
/* 1382:1697 */       localObject1 = paramAlternativeBlock.getAlternativeAt(0);
/* 1383:1699 */       if (((Alternative)localObject1).synPred != null) {
/* 1384:1700 */         this.antlrTool.warning("Syntactic predicate superfluous for single alternative", this.grammar.getFilename(), paramAlternativeBlock.getAlternativeAt(0).synPred.getLine(), paramAlternativeBlock.getAlternativeAt(0).synPred.getColumn());
/* 1385:     */       }
/* 1386:1707 */       if (paramBoolean)
/* 1387:     */       {
/* 1388:1709 */         if (((Alternative)localObject1).semPred != null) {
/* 1389:1711 */           genSemPred(((Alternative)localObject1).semPred, paramAlternativeBlock.line);
/* 1390:     */         }
/* 1391:1713 */         genAlt((Alternative)localObject1, paramAlternativeBlock);
/* 1392:1714 */         return localPythonBlockFinishingInfo;
/* 1393:     */       }
/* 1394:     */     }
/* 1395:1727 */     int n = 0;
/* 1396:1728 */     for (int i1 = 0; i1 < paramAlternativeBlock.getAlternatives().size(); i1++)
/* 1397:     */     {
/* 1398:1729 */       Alternative localAlternative1 = paramAlternativeBlock.getAlternativeAt(i1);
/* 1399:1730 */       if (suitableForCaseExpression(localAlternative1)) {
/* 1400:1731 */         n++;
/* 1401:     */       }
/* 1402:     */     }
/* 1403:     */     Object localObject2;
/* 1404:1736 */     if (n >= this.makeSwitchThreshold)
/* 1405:     */     {
/* 1406:1739 */       String str2 = lookaheadString(1);
/* 1407:1740 */       k = 1;
/* 1408:1742 */       if ((this.grammar instanceof TreeWalkerGrammar))
/* 1409:     */       {
/* 1410:1743 */         println("if not _t:");
/* 1411:1744 */         this.tabs += 1;
/* 1412:1745 */         println("_t = antlr.ASTNULL");
/* 1413:1746 */         this.tabs -= 1;
/* 1414:     */       }
/* 1415:1749 */       println("la1 = " + str2);
/* 1416:     */       
/* 1417:1751 */       println("if False:");
/* 1418:1752 */       this.tabs += 1;
/* 1419:1753 */       println("pass");
/* 1420:     */       
/* 1421:1755 */       this.tabs -= 1;
/* 1422:1757 */       for (i3 = 0; i3 < paramAlternativeBlock.alternatives.size(); i3++)
/* 1423:     */       {
/* 1424:1759 */         Alternative localAlternative2 = paramAlternativeBlock.getAlternativeAt(i3);
/* 1425:1762 */         if (suitableForCaseExpression(localAlternative2))
/* 1426:     */         {
/* 1427:1765 */           localObject2 = localAlternative2.cache[1];
/* 1428:1766 */           if ((((Lookahead)localObject2).fset.degree() == 0) && (!((Lookahead)localObject2).containsEpsilon()))
/* 1429:     */           {
/* 1430:1768 */             this.antlrTool.warning("Alternate omitted due to empty prediction set", this.grammar.getFilename(), localAlternative2.head.getLine(), localAlternative2.head.getColumn());
/* 1431:     */           }
/* 1432:     */           else
/* 1433:     */           {
/* 1434:1776 */             genCases(((Lookahead)localObject2).fset);
/* 1435:1777 */             this.tabs += 1;
/* 1436:1778 */             genAlt(localAlternative2, paramAlternativeBlock);
/* 1437:1779 */             this.tabs -= 1;
/* 1438:     */           }
/* 1439:     */         }
/* 1440:     */       }
/* 1441:1783 */       println("else:");
/* 1442:1784 */       this.tabs += 1;
/* 1443:     */     }
/* 1444:1800 */     int i2 = (this.grammar instanceof LexerGrammar) ? this.grammar.maxk : 0;
/* 1445:1801 */     for (int i3 = i2; i3 >= 0; i3--) {
/* 1446:1803 */       for (int i4 = 0; i4 < paramAlternativeBlock.alternatives.size(); i4++)
/* 1447:     */       {
/* 1448:1805 */         localObject2 = paramAlternativeBlock.getAlternativeAt(i4);
/* 1449:1806 */         if (this.DEBUG_CODE_GENERATOR) {
/* 1450:1806 */           System.out.println("genAlt: " + i4);
/* 1451:     */         }
/* 1452:1811 */         if ((k != 0) && (suitableForCaseExpression((Alternative)localObject2)))
/* 1453:     */         {
/* 1454:1812 */           if (this.DEBUG_CODE_GENERATOR) {
/* 1455:1813 */             System.out.println("ignoring alt because it was in the switch");
/* 1456:     */           }
/* 1457:     */         }
/* 1458:     */         else
/* 1459:     */         {
/* 1460:1818 */           boolean bool3 = false;
/* 1461:     */           String str4;
/* 1462:1820 */           if ((this.grammar instanceof LexerGrammar))
/* 1463:     */           {
/* 1464:1825 */             int i5 = ((Alternative)localObject2).lookaheadDepth;
/* 1465:1826 */             if (i5 == 2147483647) {
/* 1466:1828 */               i5 = this.grammar.maxk;
/* 1467:     */             }
/* 1468:1830 */             while ((i5 >= 1) && (localObject2.cache[i5].containsEpsilon())) {
/* 1469:1832 */               i5--;
/* 1470:     */             }
/* 1471:1836 */             if (i5 != i3)
/* 1472:     */             {
/* 1473:1837 */               if (!this.DEBUG_CODE_GENERATOR) {
/* 1474:     */                 continue;
/* 1475:     */               }
/* 1476:1838 */               System.out.println("ignoring alt because effectiveDepth!=altDepth" + i5 + "!=" + i3); continue;
/* 1477:     */             }
/* 1478:1844 */             bool3 = lookaheadIsEmpty((Alternative)localObject2, i5);
/* 1479:1845 */             str4 = getLookaheadTestExpression((Alternative)localObject2, i5);
/* 1480:     */           }
/* 1481:     */           else
/* 1482:     */           {
/* 1483:1850 */             bool3 = lookaheadIsEmpty((Alternative)localObject2, this.grammar.maxk);
/* 1484:1851 */             str4 = getLookaheadTestExpression((Alternative)localObject2, this.grammar.maxk);
/* 1485:     */           }
/* 1486:1856 */           if ((localObject2.cache[1].fset.degree() > 127) && (suitableForCaseExpression((Alternative)localObject2)))
/* 1487:     */           {
/* 1488:1858 */             if (j == 0) {
/* 1489:1859 */               println("<m1> if " + str4 + ":");
/* 1490:     */             } else {
/* 1491:1862 */               println("<m2> elif " + str4 + ":");
/* 1492:     */             }
/* 1493:     */           }
/* 1494:1867 */           else if ((bool3) && (((Alternative)localObject2).semPred == null) && (((Alternative)localObject2).synPred == null))
/* 1495:     */           {
/* 1496:1875 */             if (j == 0)
/* 1497:     */             {
/* 1498:1876 */               println("##<m3> <closing");
/* 1499:     */             }
/* 1500:     */             else
/* 1501:     */             {
/* 1502:1880 */               println("else: ## <m4>");
/* 1503:1881 */               this.tabs += 1;
/* 1504:     */             }
/* 1505:1885 */             localPythonBlockFinishingInfo.needAnErrorClause = false;
/* 1506:     */           }
/* 1507:     */           else
/* 1508:     */           {
/* 1509:1893 */             if (((Alternative)localObject2).semPred != null)
/* 1510:     */             {
/* 1511:1898 */               ActionTransInfo localActionTransInfo = new ActionTransInfo();
/* 1512:1899 */               String str5 = processActionForSpecialSymbols(((Alternative)localObject2).semPred, paramAlternativeBlock.line, this.currentRule, localActionTransInfo);
/* 1513:1908 */               if ((((this.grammar instanceof ParserGrammar)) || ((this.grammar instanceof LexerGrammar))) && (this.grammar.debuggingOutput)) {
/* 1514:1912 */                 str4 = "(" + str4 + " and fireSemanticPredicateEvaluated(antlr.debug.SemanticPredicateEvent.PREDICTING, " + addSemPred(this.charFormatter.escapeString(str5)) + ", " + str5 + "))";
/* 1515:     */               } else {
/* 1516:1918 */                 str4 = "(" + str4 + " and (" + str5 + "))";
/* 1517:     */               }
/* 1518:     */             }
/* 1519:1923 */             if (j > 0)
/* 1520:     */             {
/* 1521:1925 */               if (((Alternative)localObject2).synPred != null)
/* 1522:     */               {
/* 1523:1927 */                 println("else:");
/* 1524:1928 */                 this.tabs += 1;
/* 1525:1929 */                 genSynPred(((Alternative)localObject2).synPred, str4);
/* 1526:1930 */                 m++;
/* 1527:     */               }
/* 1528:     */               else
/* 1529:     */               {
/* 1530:1934 */                 println("elif " + str4 + ":");
/* 1531:     */               }
/* 1532:     */             }
/* 1533:1939 */             else if (((Alternative)localObject2).synPred != null)
/* 1534:     */             {
/* 1535:1941 */               genSynPred(((Alternative)localObject2).synPred, str4);
/* 1536:     */             }
/* 1537:     */             else
/* 1538:     */             {
/* 1539:1947 */               if ((this.grammar instanceof TreeWalkerGrammar))
/* 1540:     */               {
/* 1541:1948 */                 println("if not _t:");
/* 1542:1949 */                 this.tabs += 1;
/* 1543:1950 */                 println("_t = antlr.ASTNULL");
/* 1544:1951 */                 this.tabs -= 1;
/* 1545:     */               }
/* 1546:1953 */               println("if " + str4 + ":");
/* 1547:     */             }
/* 1548:     */           }
/* 1549:1959 */           j++;
/* 1550:1960 */           this.tabs += 1;
/* 1551:1961 */           genAlt((Alternative)localObject2, paramAlternativeBlock);
/* 1552:     */           
/* 1553:1963 */           this.tabs -= 1;
/* 1554:     */         }
/* 1555:     */       }
/* 1556:     */     }
/* 1557:1966 */     String str3 = "";
/* 1558:     */     
/* 1559:     */ 
/* 1560:     */ 
/* 1561:     */ 
/* 1562:     */ 
/* 1563:1972 */     this.genAST = bool1;
/* 1564:     */     
/* 1565:     */ 
/* 1566:1975 */     this.saveText = bool2;
/* 1567:1978 */     if (k != 0)
/* 1568:     */     {
/* 1569:1979 */       localPythonBlockFinishingInfo.postscript = str3;
/* 1570:1980 */       localPythonBlockFinishingInfo.generatedSwitch = true;
/* 1571:1981 */       localPythonBlockFinishingInfo.generatedAnIf = (j > 0);
/* 1572:     */     }
/* 1573:     */     else
/* 1574:     */     {
/* 1575:1984 */       localPythonBlockFinishingInfo.postscript = str3;
/* 1576:1985 */       localPythonBlockFinishingInfo.generatedSwitch = false;
/* 1577:1986 */       localPythonBlockFinishingInfo.generatedAnIf = (j > 0);
/* 1578:     */     }
/* 1579:1989 */     return localPythonBlockFinishingInfo;
/* 1580:     */   }
/* 1581:     */   
/* 1582:     */   private static boolean suitableForCaseExpression(Alternative paramAlternative)
/* 1583:     */   {
/* 1584:1993 */     return (paramAlternative.lookaheadDepth == 1) && (paramAlternative.semPred == null) && (!paramAlternative.cache[1].containsEpsilon()) && (paramAlternative.cache[1].fset.degree() <= 127);
/* 1585:     */   }
/* 1586:     */   
/* 1587:     */   private void genElementAST(AlternativeElement paramAlternativeElement)
/* 1588:     */   {
/* 1589:2004 */     if (((this.grammar instanceof TreeWalkerGrammar)) && (!this.grammar.buildAST))
/* 1590:     */     {
/* 1591:2009 */       if (paramAlternativeElement.getLabel() == null)
/* 1592:     */       {
/* 1593:2010 */         String str1 = this.lt1Value;
/* 1594:     */         
/* 1595:2012 */         String str2 = "tmp" + this.astVarNumber + "_AST";
/* 1596:2013 */         this.astVarNumber += 1;
/* 1597:     */         
/* 1598:2015 */         mapTreeVariable(paramAlternativeElement, str2);
/* 1599:     */         
/* 1600:2017 */         println(str2 + "_in = " + str1);
/* 1601:     */       }
/* 1602:2019 */       return;
/* 1603:     */     }
/* 1604:2022 */     if ((this.grammar.buildAST) && (this.syntacticPredLevel == 0))
/* 1605:     */     {
/* 1606:2023 */       int i = (this.genAST) && ((paramAlternativeElement.getLabel() != null) || (paramAlternativeElement.getAutoGenType() != 3)) ? 1 : 0;
/* 1607:2034 */       if ((paramAlternativeElement.getAutoGenType() != 3) && ((paramAlternativeElement instanceof TokenRefElement))) {
/* 1608:2037 */         i = 1;
/* 1609:     */       }
/* 1610:2040 */       int j = (this.grammar.hasSyntacticPredicate) && (i != 0) ? 1 : 0;
/* 1611:     */       String str3;
/* 1612:     */       String str4;
/* 1613:2047 */       if (paramAlternativeElement.getLabel() != null)
/* 1614:     */       {
/* 1615:2048 */         str3 = paramAlternativeElement.getLabel();
/* 1616:2049 */         str4 = paramAlternativeElement.getLabel();
/* 1617:     */       }
/* 1618:     */       else
/* 1619:     */       {
/* 1620:2052 */         str3 = this.lt1Value;
/* 1621:     */         
/* 1622:2054 */         str4 = "tmp" + this.astVarNumber;
/* 1623:     */         
/* 1624:2056 */         this.astVarNumber += 1;
/* 1625:     */       }
/* 1626:2060 */       if (i != 0) {
/* 1627:2062 */         if ((paramAlternativeElement instanceof GrammarAtom))
/* 1628:     */         {
/* 1629:2063 */           localObject = (GrammarAtom)paramAlternativeElement;
/* 1630:2064 */           if (((GrammarAtom)localObject).getASTNodeType() != null) {
/* 1631:2065 */             genASTDeclaration(paramAlternativeElement, str4, ((GrammarAtom)localObject).getASTNodeType());
/* 1632:     */           } else {
/* 1633:2068 */             genASTDeclaration(paramAlternativeElement, str4, this.labeledElementASTType);
/* 1634:     */           }
/* 1635:     */         }
/* 1636:     */         else
/* 1637:     */         {
/* 1638:2072 */           genASTDeclaration(paramAlternativeElement, str4, this.labeledElementASTType);
/* 1639:     */         }
/* 1640:     */       }
/* 1641:2077 */       Object localObject = str4 + "_AST";
/* 1642:     */       
/* 1643:     */ 
/* 1644:2080 */       mapTreeVariable(paramAlternativeElement, (String)localObject);
/* 1645:2081 */       if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 1646:2083 */         println((String)localObject + "_in = None");
/* 1647:     */       }
/* 1648:2087 */       if ((j == 0) || 
/* 1649:     */       
/* 1650:     */ 
/* 1651:     */ 
/* 1652:     */ 
/* 1653:     */ 
/* 1654:     */ 
/* 1655:2094 */         (paramAlternativeElement.getLabel() != null)) {
/* 1656:2095 */         if ((paramAlternativeElement instanceof GrammarAtom)) {
/* 1657:2096 */           println((String)localObject + " = " + getASTCreateString((GrammarAtom)paramAlternativeElement, str3) + "");
/* 1658:     */         } else {
/* 1659:2099 */           println((String)localObject + " = " + getASTCreateString(str3) + "");
/* 1660:     */         }
/* 1661:     */       }
/* 1662:2104 */       if ((paramAlternativeElement.getLabel() == null) && (i != 0))
/* 1663:     */       {
/* 1664:2105 */         str3 = this.lt1Value;
/* 1665:2106 */         if ((paramAlternativeElement instanceof GrammarAtom)) {
/* 1666:2107 */           println((String)localObject + " = " + getASTCreateString((GrammarAtom)paramAlternativeElement, str3) + "");
/* 1667:     */         } else {
/* 1668:2110 */           println((String)localObject + " = " + getASTCreateString(str3) + "");
/* 1669:     */         }
/* 1670:2113 */         if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 1671:2115 */           println((String)localObject + "_in = " + str3 + "");
/* 1672:     */         }
/* 1673:     */       }
/* 1674:2119 */       if (this.genAST) {
/* 1675:2120 */         switch (paramAlternativeElement.getAutoGenType())
/* 1676:     */         {
/* 1677:     */         case 1: 
/* 1678:2122 */           println("self.addASTChild(currentAST, " + (String)localObject + ")");
/* 1679:2123 */           break;
/* 1680:     */         case 2: 
/* 1681:2125 */           println("self.makeASTRoot(currentAST, " + (String)localObject + ")");
/* 1682:2126 */           break;
/* 1683:     */         }
/* 1684:     */       }
/* 1685:2131 */       if (j == 0) {}
/* 1686:     */     }
/* 1687:     */   }
/* 1688:     */   
/* 1689:     */   private void genErrorCatchForElement(AlternativeElement paramAlternativeElement)
/* 1690:     */   {
/* 1691:2141 */     if (paramAlternativeElement.getLabel() == null) {
/* 1692:2141 */       return;
/* 1693:     */     }
/* 1694:2142 */     String str = paramAlternativeElement.enclosingRuleName;
/* 1695:2143 */     if ((this.grammar instanceof LexerGrammar)) {
/* 1696:2144 */       str = CodeGenerator.encodeLexerRuleName(paramAlternativeElement.enclosingRuleName);
/* 1697:     */     }
/* 1698:2146 */     RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(str);
/* 1699:2147 */     if (localRuleSymbol == null) {
/* 1700:2148 */       this.antlrTool.panic("Enclosing rule not found!");
/* 1701:     */     }
/* 1702:2150 */     ExceptionSpec localExceptionSpec = localRuleSymbol.block.findExceptionSpec(paramAlternativeElement.getLabel());
/* 1703:2151 */     if (localExceptionSpec != null)
/* 1704:     */     {
/* 1705:2152 */       this.tabs -= 1;
/* 1706:2153 */       genErrorHandler(localExceptionSpec);
/* 1707:     */     }
/* 1708:     */   }
/* 1709:     */   
/* 1710:     */   private void genErrorHandler(ExceptionSpec paramExceptionSpec)
/* 1711:     */   {
/* 1712:2160 */     for (int i = 0; i < paramExceptionSpec.handlers.size(); i++)
/* 1713:     */     {
/* 1714:2161 */       ExceptionHandler localExceptionHandler = (ExceptionHandler)paramExceptionSpec.handlers.elementAt(i);
/* 1715:     */       
/* 1716:2163 */       String str1 = "";String str2 = "";
/* 1717:2164 */       String str3 = localExceptionHandler.exceptionTypeAndName.getText();
/* 1718:2165 */       str3 = removeAssignmentFromDeclaration(str3);
/* 1719:2166 */       str3 = str3.trim();
/* 1720:2169 */       for (int j = str3.length() - 1; j >= 0; j--) {
/* 1721:2171 */         if ((!Character.isLetterOrDigit(str3.charAt(j))) && (str3.charAt(j) != '_'))
/* 1722:     */         {
/* 1723:2174 */           str1 = str3.substring(0, j);
/* 1724:2175 */           str2 = str3.substring(j + 1);
/* 1725:2176 */           break;
/* 1726:     */         }
/* 1727:     */       }
/* 1728:2180 */       println("except " + str1 + ", " + str2 + ":");
/* 1729:2181 */       this.tabs += 1;
/* 1730:2182 */       if (this.grammar.hasSyntacticPredicate)
/* 1731:     */       {
/* 1732:2183 */         println("if not self.inputState.guessing:");
/* 1733:2184 */         this.tabs += 1;
/* 1734:     */       }
/* 1735:2188 */       ActionTransInfo localActionTransInfo = new ActionTransInfo();
/* 1736:2189 */       printAction(processActionForSpecialSymbols(localExceptionHandler.action.getText(), localExceptionHandler.action.getLine(), this.currentRule, localActionTransInfo));
/* 1737:2195 */       if (this.grammar.hasSyntacticPredicate)
/* 1738:     */       {
/* 1739:2196 */         this.tabs -= 1;
/* 1740:2197 */         println("else:");
/* 1741:2198 */         this.tabs += 1;
/* 1742:     */         
/* 1743:2200 */         println("raise " + str2);
/* 1744:2201 */         this.tabs -= 1;
/* 1745:     */       }
/* 1746:2204 */       this.tabs -= 1;
/* 1747:     */     }
/* 1748:     */   }
/* 1749:     */   
/* 1750:     */   private void genErrorTryForElement(AlternativeElement paramAlternativeElement)
/* 1751:     */   {
/* 1752:2210 */     if (paramAlternativeElement.getLabel() == null) {
/* 1753:2210 */       return;
/* 1754:     */     }
/* 1755:2211 */     String str = paramAlternativeElement.enclosingRuleName;
/* 1756:2212 */     if ((this.grammar instanceof LexerGrammar)) {
/* 1757:2213 */       str = CodeGenerator.encodeLexerRuleName(paramAlternativeElement.enclosingRuleName);
/* 1758:     */     }
/* 1759:2215 */     RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(str);
/* 1760:2216 */     if (localRuleSymbol == null) {
/* 1761:2217 */       this.antlrTool.panic("Enclosing rule not found!");
/* 1762:     */     }
/* 1763:2219 */     ExceptionSpec localExceptionSpec = localRuleSymbol.block.findExceptionSpec(paramAlternativeElement.getLabel());
/* 1764:2220 */     if (localExceptionSpec != null)
/* 1765:     */     {
/* 1766:2221 */       println("try: # for error handling");
/* 1767:2222 */       this.tabs += 1;
/* 1768:     */     }
/* 1769:     */   }
/* 1770:     */   
/* 1771:     */   protected void genASTDeclaration(AlternativeElement paramAlternativeElement)
/* 1772:     */   {
/* 1773:2227 */     genASTDeclaration(paramAlternativeElement, this.labeledElementASTType);
/* 1774:     */   }
/* 1775:     */   
/* 1776:     */   protected void genASTDeclaration(AlternativeElement paramAlternativeElement, String paramString)
/* 1777:     */   {
/* 1778:2231 */     genASTDeclaration(paramAlternativeElement, paramAlternativeElement.getLabel(), paramString);
/* 1779:     */   }
/* 1780:     */   
/* 1781:     */   protected void genASTDeclaration(AlternativeElement paramAlternativeElement, String paramString1, String paramString2)
/* 1782:     */   {
/* 1783:2236 */     if (this.declaredASTVariables.contains(paramAlternativeElement)) {
/* 1784:2237 */       return;
/* 1785:     */     }
/* 1786:2240 */     println(paramString1 + "_AST = None");
/* 1787:     */     
/* 1788:     */ 
/* 1789:2243 */     this.declaredASTVariables.put(paramAlternativeElement, paramAlternativeElement);
/* 1790:     */   }
/* 1791:     */   
/* 1792:     */   protected void genHeader()
/* 1793:     */   {
/* 1794:2248 */     println("### $ANTLR " + Tool.version + ": " + "\"" + this.antlrTool.fileMinusPath(this.antlrTool.grammarFile) + "\"" + " -> " + "\"" + this.grammar.getClassName() + ".py\"$");
/* 1795:     */   }
/* 1796:     */   
/* 1797:     */   protected void genLexerTest()
/* 1798:     */   {
/* 1799:2264 */     String str = this.grammar.getClassName();
/* 1800:2265 */     println("if __name__ == '__main__' :");
/* 1801:2266 */     this.tabs += 1;
/* 1802:2267 */     println("import sys");
/* 1803:2268 */     println("import antlr");
/* 1804:2269 */     println("import " + str);
/* 1805:2270 */     println("");
/* 1806:2271 */     println("### create lexer - shall read from stdin");
/* 1807:2272 */     println("try:");
/* 1808:2273 */     this.tabs += 1;
/* 1809:2274 */     println("for token in " + str + ".Lexer():");
/* 1810:2275 */     this.tabs += 1;
/* 1811:2276 */     println("print token");
/* 1812:2277 */     println("");
/* 1813:2278 */     this.tabs -= 1;
/* 1814:2279 */     this.tabs -= 1;
/* 1815:2280 */     println("except antlr.TokenStreamException, e:");
/* 1816:2281 */     this.tabs += 1;
/* 1817:2282 */     println("print \"error: exception caught while lexing: \", e");
/* 1818:2283 */     this.tabs -= 1;
/* 1819:2284 */     this.tabs -= 1;
/* 1820:     */   }
/* 1821:     */   
/* 1822:     */   private void genLiteralsTest()
/* 1823:     */   {
/* 1824:2289 */     println("### option { testLiterals=true } ");
/* 1825:2290 */     println("_ttype = self.testLiteralsTable(_ttype)");
/* 1826:     */   }
/* 1827:     */   
/* 1828:     */   private void genLiteralsTestForPartialToken()
/* 1829:     */   {
/* 1830:2294 */     println("_ttype = self.testLiteralsTable(self.text.getString(), _begin, self.text.length()-_begin, _ttype)");
/* 1831:     */   }
/* 1832:     */   
/* 1833:     */   protected void genMatch(BitSet paramBitSet) {}
/* 1834:     */   
/* 1835:     */   protected void genMatch(GrammarAtom paramGrammarAtom)
/* 1836:     */   {
/* 1837:2301 */     if ((paramGrammarAtom instanceof StringLiteralElement))
/* 1838:     */     {
/* 1839:2302 */       if ((this.grammar instanceof LexerGrammar)) {
/* 1840:2303 */         genMatchUsingAtomText(paramGrammarAtom);
/* 1841:     */       } else {
/* 1842:2306 */         genMatchUsingAtomTokenType(paramGrammarAtom);
/* 1843:     */       }
/* 1844:     */     }
/* 1845:2309 */     else if ((paramGrammarAtom instanceof CharLiteralElement))
/* 1846:     */     {
/* 1847:2310 */       if ((this.grammar instanceof LexerGrammar)) {
/* 1848:2311 */         genMatchUsingAtomText(paramGrammarAtom);
/* 1849:     */       } else {
/* 1850:2314 */         this.antlrTool.error("cannot ref character literals in grammar: " + paramGrammarAtom);
/* 1851:     */       }
/* 1852:     */     }
/* 1853:2317 */     else if ((paramGrammarAtom instanceof TokenRefElement)) {
/* 1854:2318 */       genMatchUsingAtomText(paramGrammarAtom);
/* 1855:2320 */     } else if ((paramGrammarAtom instanceof WildcardElement)) {
/* 1856:2321 */       gen((WildcardElement)paramGrammarAtom);
/* 1857:     */     }
/* 1858:     */   }
/* 1859:     */   
/* 1860:     */   protected void genMatchUsingAtomText(GrammarAtom paramGrammarAtom)
/* 1861:     */   {
/* 1862:2327 */     String str = "";
/* 1863:2328 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 1864:2329 */       str = "_t,";
/* 1865:     */     }
/* 1866:2333 */     if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramGrammarAtom.getAutoGenType() == 3))) {
/* 1867:2336 */       println("_saveIndex = self.text.length()");
/* 1868:     */     }
/* 1869:2340 */     print(paramGrammarAtom.not ? "self.matchNot(" : "self.match(");
/* 1870:2341 */     _print(str);
/* 1871:2344 */     if (paramGrammarAtom.atomText.equals("EOF")) {
/* 1872:2346 */       _print("EOF_TYPE");
/* 1873:     */     } else {
/* 1874:2349 */       _print(paramGrammarAtom.atomText);
/* 1875:     */     }
/* 1876:2351 */     _println(")");
/* 1877:2353 */     if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramGrammarAtom.getAutoGenType() == 3))) {
/* 1878:2354 */       println("self.text.setLength(_saveIndex)");
/* 1879:     */     }
/* 1880:     */   }
/* 1881:     */   
/* 1882:     */   protected void genMatchUsingAtomTokenType(GrammarAtom paramGrammarAtom)
/* 1883:     */   {
/* 1884:2360 */     String str1 = "";
/* 1885:2361 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 1886:2362 */       str1 = "_t,";
/* 1887:     */     }
/* 1888:2366 */     Object localObject = null;
/* 1889:2367 */     String str2 = str1 + getValueString(paramGrammarAtom.getType(), true);
/* 1890:     */     
/* 1891:     */ 
/* 1892:2370 */     println((paramGrammarAtom.not ? "self.matchNot(" : "self.match(") + str2 + ")");
/* 1893:     */   }
/* 1894:     */   
/* 1895:     */   public void genNextToken()
/* 1896:     */   {
/* 1897:2381 */     int i = 0;
/* 1898:2382 */     for (int j = 0; j < this.grammar.rules.size(); j++)
/* 1899:     */     {
/* 1900:2383 */       localRuleSymbol1 = (RuleSymbol)this.grammar.rules.elementAt(j);
/* 1901:2384 */       if ((localRuleSymbol1.isDefined()) && (localRuleSymbol1.access.equals("public")))
/* 1902:     */       {
/* 1903:2385 */         i = 1;
/* 1904:2386 */         break;
/* 1905:     */       }
/* 1906:     */     }
/* 1907:2389 */     if (i == 0)
/* 1908:     */     {
/* 1909:2390 */       println("");
/* 1910:2391 */       println("def nextToken(self): ");
/* 1911:2392 */       this.tabs += 1;
/* 1912:2393 */       println("try:");
/* 1913:2394 */       this.tabs += 1;
/* 1914:2395 */       println("self.uponEOF()");
/* 1915:2396 */       this.tabs -= 1;
/* 1916:2397 */       println("except antlr.CharStreamIOException, csioe:");
/* 1917:2398 */       this.tabs += 1;
/* 1918:2399 */       println("raise antlr.TokenStreamIOException(csioe.io)");
/* 1919:2400 */       this.tabs -= 1;
/* 1920:2401 */       println("except antlr.CharStreamException, cse:");
/* 1921:2402 */       this.tabs += 1;
/* 1922:2403 */       println("raise antlr.TokenStreamException(str(cse))");
/* 1923:2404 */       this.tabs -= 1;
/* 1924:2405 */       println("return antlr.CommonToken(type=EOF_TYPE, text=\"\")");
/* 1925:2406 */       this.tabs -= 1;
/* 1926:2407 */       return;
/* 1927:     */     }
/* 1928:2411 */     RuleBlock localRuleBlock = MakeGrammar.createNextTokenRule(this.grammar, this.grammar.rules, "nextToken");
/* 1929:     */     
/* 1930:     */ 
/* 1931:     */ 
/* 1932:2415 */     RuleSymbol localRuleSymbol1 = new RuleSymbol("mnextToken");
/* 1933:2416 */     localRuleSymbol1.setDefined();
/* 1934:2417 */     localRuleSymbol1.setBlock(localRuleBlock);
/* 1935:2418 */     localRuleSymbol1.access = "private";
/* 1936:2419 */     this.grammar.define(localRuleSymbol1);
/* 1937:     */     
/* 1938:2421 */     boolean bool = this.grammar.theLLkAnalyzer.deterministic(localRuleBlock);
/* 1939:     */     
/* 1940:     */ 
/* 1941:2424 */     String str1 = null;
/* 1942:2425 */     if (((LexerGrammar)this.grammar).filterMode) {
/* 1943:2426 */       str1 = ((LexerGrammar)this.grammar).filterRule;
/* 1944:     */     }
/* 1945:2429 */     println("");
/* 1946:2430 */     println("def nextToken(self):");
/* 1947:2431 */     this.tabs += 1;
/* 1948:2432 */     println("while True:");
/* 1949:2433 */     this.tabs += 1;
/* 1950:2434 */     println("try: ### try again ..");
/* 1951:2435 */     this.tabs += 1;
/* 1952:2436 */     println("while True:");
/* 1953:2437 */     this.tabs += 1;
/* 1954:2438 */     int k = this.tabs;
/* 1955:2439 */     println("_token = None");
/* 1956:2440 */     println("_ttype = INVALID_TYPE");
/* 1957:2441 */     if (((LexerGrammar)this.grammar).filterMode)
/* 1958:     */     {
/* 1959:2443 */       println("self.setCommitToPath(False)");
/* 1960:2444 */       if (str1 != null)
/* 1961:     */       {
/* 1962:2447 */         if (!this.grammar.isDefined(CodeGenerator.encodeLexerRuleName(str1)))
/* 1963:     */         {
/* 1964:2448 */           this.grammar.antlrTool.error("Filter rule " + str1 + " does not exist in this lexer");
/* 1965:     */         }
/* 1966:     */         else
/* 1967:     */         {
/* 1968:2453 */           RuleSymbol localRuleSymbol2 = (RuleSymbol)this.grammar.getSymbol(CodeGenerator.encodeLexerRuleName(str1));
/* 1969:2455 */           if (!localRuleSymbol2.isDefined()) {
/* 1970:2456 */             this.grammar.antlrTool.error("Filter rule " + str1 + " does not exist in this lexer");
/* 1971:2459 */           } else if (localRuleSymbol2.access.equals("public")) {
/* 1972:2460 */             this.grammar.antlrTool.error("Filter rule " + str1 + " must be protected");
/* 1973:     */           }
/* 1974:     */         }
/* 1975:2464 */         println("_m = self.mark()");
/* 1976:     */       }
/* 1977:     */     }
/* 1978:2467 */     println("self.resetText()");
/* 1979:     */     
/* 1980:2469 */     println("try: ## for char stream error handling");
/* 1981:2470 */     this.tabs += 1;
/* 1982:2471 */     k = this.tabs;
/* 1983:     */     
/* 1984:     */ 
/* 1985:2474 */     println("try: ##for lexical error handling");
/* 1986:2475 */     this.tabs += 1;
/* 1987:2476 */     k = this.tabs;
/* 1988:2480 */     for (int m = 0; m < localRuleBlock.getAlternatives().size(); m++)
/* 1989:     */     {
/* 1990:2482 */       localObject1 = localRuleBlock.getAlternativeAt(m);
/* 1991:2483 */       if (localObject1.cache[1].containsEpsilon())
/* 1992:     */       {
/* 1993:2486 */         localObject2 = (RuleRefElement)((Alternative)localObject1).head;
/* 1994:2487 */         String str3 = CodeGenerator.decodeLexerRuleName(((RuleRefElement)localObject2).targetRule);
/* 1995:2488 */         this.antlrTool.warning("public lexical rule " + str3 + " is optional (can match \"nothing\")");
/* 1996:     */       }
/* 1997:     */     }
/* 1998:2493 */     String str2 = System.getProperty("line.separator");
/* 1999:     */     
/* 2000:     */ 
/* 2001:2496 */     Object localObject1 = genCommonBlock(localRuleBlock, false);
/* 2002:     */     
/* 2003:     */ 
/* 2004:     */ 
/* 2005:2500 */     Object localObject2 = "";
/* 2006:2506 */     if (((LexerGrammar)this.grammar).filterMode)
/* 2007:     */     {
/* 2008:2509 */       if (str1 == null) {
/* 2009:2512 */         localObject2 = (String)localObject2 + "self.filterdefault(self.LA(1))";
/* 2010:     */       } else {
/* 2011:2516 */         localObject2 = (String)localObject2 + "self.filterdefault(self.LA(1), self.m" + str1 + ", False)";
/* 2012:     */       }
/* 2013:     */     }
/* 2014:     */     else {
/* 2015:2528 */       localObject2 = "self.default(self.LA(1))";
/* 2016:     */     }
/* 2017:2533 */     genBlockFinish1((PythonBlockFinishingInfo)localObject1, (String)localObject2);
/* 2018:     */     
/* 2019:     */ 
/* 2020:2536 */     this.tabs = k;
/* 2021:2539 */     if ((((LexerGrammar)this.grammar).filterMode) && (str1 != null)) {
/* 2022:2540 */       println("self.commit()");
/* 2023:     */     }
/* 2024:2546 */     println("if not self._returnToken:");
/* 2025:2547 */     this.tabs += 1;
/* 2026:2548 */     println("raise antlr.TryAgain ### found SKIP token");
/* 2027:2549 */     this.tabs -= 1;
/* 2028:2553 */     if (((LexerGrammar)this.grammar).getTestLiterals())
/* 2029:     */     {
/* 2030:2555 */       println("### option { testLiterals=true } ");
/* 2031:     */       
/* 2032:2557 */       println("self.testForLiteral(self._returnToken)");
/* 2033:     */     }
/* 2034:2561 */     println("### return token to caller");
/* 2035:2562 */     println("return self._returnToken");
/* 2036:     */     
/* 2037:     */ 
/* 2038:2565 */     this.tabs -= 1;
/* 2039:2566 */     println("### handle lexical errors ....");
/* 2040:2567 */     println("except antlr.RecognitionException, e:");
/* 2041:2568 */     this.tabs += 1;
/* 2042:2569 */     if (((LexerGrammar)this.grammar).filterMode) {
/* 2043:2571 */       if (str1 == null)
/* 2044:     */       {
/* 2045:2573 */         println("if not self.getCommitToPath():");
/* 2046:2574 */         this.tabs += 1;
/* 2047:2575 */         println("self.consume()");
/* 2048:2576 */         println("raise antlr.TryAgain()");
/* 2049:2577 */         this.tabs -= 1;
/* 2050:     */       }
/* 2051:     */       else
/* 2052:     */       {
/* 2053:2581 */         println("if not self.getCommitToPath(): ");
/* 2054:2582 */         this.tabs += 1;
/* 2055:2583 */         println("self.rewind(_m)");
/* 2056:2584 */         println("self.resetText()");
/* 2057:2585 */         println("try:");
/* 2058:2586 */         this.tabs += 1;
/* 2059:2587 */         println("self.m" + str1 + "(False)");
/* 2060:2588 */         this.tabs -= 1;
/* 2061:2589 */         println("except antlr.RecognitionException, ee:");
/* 2062:2590 */         this.tabs += 1;
/* 2063:2591 */         println("### horrendous failure: error in filter rule");
/* 2064:2592 */         println("self.reportError(ee)");
/* 2065:2593 */         println("self.consume()");
/* 2066:2594 */         this.tabs -= 1;
/* 2067:2595 */         println("raise antlr.TryAgain()");
/* 2068:2596 */         this.tabs -= 1;
/* 2069:     */       }
/* 2070:     */     }
/* 2071:2599 */     if (localRuleBlock.getDefaultErrorHandler())
/* 2072:     */     {
/* 2073:2600 */       println("self.reportError(e)");
/* 2074:2601 */       println("self.consume()");
/* 2075:     */     }
/* 2076:     */     else
/* 2077:     */     {
/* 2078:2605 */       println("raise antlr.TokenStreamRecognitionException(e)");
/* 2079:     */     }
/* 2080:2607 */     this.tabs -= 1;
/* 2081:     */     
/* 2082:     */ 
/* 2083:     */ 
/* 2084:     */ 
/* 2085:2612 */     this.tabs -= 1;
/* 2086:2613 */     println("### handle char stream errors ...");
/* 2087:2614 */     println("except antlr.CharStreamException,cse:");
/* 2088:2615 */     this.tabs += 1;
/* 2089:2616 */     println("if isinstance(cse, antlr.CharStreamIOException):");
/* 2090:2617 */     this.tabs += 1;
/* 2091:2618 */     println("raise antlr.TokenStreamIOException(cse.io)");
/* 2092:2619 */     this.tabs -= 1;
/* 2093:2620 */     println("else:");
/* 2094:2621 */     this.tabs += 1;
/* 2095:2622 */     println("raise antlr.TokenStreamException(str(cse))");
/* 2096:2623 */     this.tabs -= 1;
/* 2097:2624 */     this.tabs -= 1;
/* 2098:     */     
/* 2099:     */ 
/* 2100:     */ 
/* 2101:2628 */     this.tabs -= 1;
/* 2102:     */     
/* 2103:     */ 
/* 2104:     */ 
/* 2105:2632 */     this.tabs -= 1;
/* 2106:     */     
/* 2107:2634 */     println("except antlr.TryAgain:");
/* 2108:2635 */     this.tabs += 1;
/* 2109:2636 */     println("pass");
/* 2110:2637 */     this.tabs -= 1;
/* 2111:     */     
/* 2112:2639 */     this.tabs -= 1;
/* 2113:     */   }
/* 2114:     */   
/* 2115:     */   public void genRule(RuleSymbol paramRuleSymbol, boolean paramBoolean, int paramInt)
/* 2116:     */   {
/* 2117:2664 */     this.tabs = 1;
/* 2118:2665 */     if (!paramRuleSymbol.isDefined())
/* 2119:     */     {
/* 2120:2666 */       this.antlrTool.error("undefined rule: " + paramRuleSymbol.getId());
/* 2121:2667 */       return;
/* 2122:     */     }
/* 2123:2671 */     RuleBlock localRuleBlock = paramRuleSymbol.getBlock();
/* 2124:     */     
/* 2125:2673 */     this.currentRule = localRuleBlock;
/* 2126:2674 */     this.currentASTResult = paramRuleSymbol.getId();
/* 2127:     */     
/* 2128:     */ 
/* 2129:2677 */     this.declaredASTVariables.clear();
/* 2130:     */     
/* 2131:     */ 
/* 2132:2680 */     boolean bool1 = this.genAST;
/* 2133:2681 */     this.genAST = ((this.genAST) && (localRuleBlock.getAutoGen()));
/* 2134:     */     
/* 2135:     */ 
/* 2136:2684 */     this.saveText = localRuleBlock.getAutoGen();
/* 2137:     */     
/* 2138:     */ 
/* 2139:2687 */     genJavadocComment(paramRuleSymbol);
/* 2140:     */     
/* 2141:     */ 
/* 2142:2690 */     print("def " + paramRuleSymbol.getId() + "(");
/* 2143:     */     
/* 2144:     */ 
/* 2145:2693 */     _print(this.commonExtraParams);
/* 2146:2694 */     if ((this.commonExtraParams.length() != 0) && (localRuleBlock.argAction != null)) {
/* 2147:2695 */       _print(",");
/* 2148:     */     }
/* 2149:2700 */     if (localRuleBlock.argAction != null)
/* 2150:     */     {
/* 2151:2702 */       _println("");
/* 2152:2703 */       this.tabs += 1;
/* 2153:2704 */       println(localRuleBlock.argAction);
/* 2154:2705 */       this.tabs -= 1;
/* 2155:2706 */       print("):");
/* 2156:     */     }
/* 2157:     */     else
/* 2158:     */     {
/* 2159:2710 */       _print("):");
/* 2160:     */     }
/* 2161:2713 */     println("");
/* 2162:2714 */     this.tabs += 1;
/* 2163:2717 */     if (localRuleBlock.returnAction != null) {
/* 2164:2718 */       if (localRuleBlock.returnAction.indexOf('=') >= 0) {
/* 2165:2719 */         println(localRuleBlock.returnAction);
/* 2166:     */       } else {
/* 2167:2722 */         println(extractIdOfAction(localRuleBlock.returnAction, localRuleBlock.getLine(), localRuleBlock.getColumn()) + " = None");
/* 2168:     */       }
/* 2169:     */     }
/* 2170:2726 */     println(this.commonLocalVars);
/* 2171:2728 */     if (this.grammar.traceRules) {
/* 2172:2729 */       if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2173:2730 */         println("self.traceIn(\"" + paramRuleSymbol.getId() + "\",_t)");
/* 2174:     */       } else {
/* 2175:2733 */         println("self.traceIn(\"" + paramRuleSymbol.getId() + "\")");
/* 2176:     */       }
/* 2177:     */     }
/* 2178:2737 */     if ((this.grammar instanceof LexerGrammar))
/* 2179:     */     {
/* 2180:2740 */       if (paramRuleSymbol.getId().equals("mEOF")) {
/* 2181:2741 */         println("_ttype = EOF_TYPE");
/* 2182:     */       } else {
/* 2183:2743 */         println("_ttype = " + paramRuleSymbol.getId().substring(1));
/* 2184:     */       }
/* 2185:2744 */       println("_saveIndex = 0");
/* 2186:     */     }
/* 2187:2748 */     if (this.grammar.debuggingOutput) {
/* 2188:2749 */       if ((this.grammar instanceof ParserGrammar)) {
/* 2189:2750 */         println("self.fireEnterRule(" + paramInt + ", 0)");
/* 2190:2751 */       } else if ((this.grammar instanceof LexerGrammar)) {
/* 2191:2752 */         println("self.fireEnterRule(" + paramInt + ", _ttype)");
/* 2192:     */       }
/* 2193:     */     }
/* 2194:2755 */     if ((this.grammar.debuggingOutput) || (this.grammar.traceRules))
/* 2195:     */     {
/* 2196:2756 */       println("try: ### debugging");
/* 2197:2757 */       this.tabs += 1;
/* 2198:     */     }
/* 2199:2761 */     if ((this.grammar instanceof TreeWalkerGrammar))
/* 2200:     */     {
/* 2201:2763 */       println(paramRuleSymbol.getId() + "_AST_in = None");
/* 2202:2764 */       println("if _t != antlr.ASTNULL:");
/* 2203:2765 */       this.tabs += 1;
/* 2204:2766 */       println(paramRuleSymbol.getId() + "_AST_in = _t");
/* 2205:2767 */       this.tabs -= 1;
/* 2206:     */     }
/* 2207:2769 */     if (this.grammar.buildAST)
/* 2208:     */     {
/* 2209:2772 */       println("self.returnAST = None");
/* 2210:2773 */       println("currentAST = antlr.ASTPair()");
/* 2211:     */       
/* 2212:2775 */       println(paramRuleSymbol.getId() + "_AST = None");
/* 2213:     */     }
/* 2214:2778 */     genBlockPreamble(localRuleBlock);
/* 2215:2779 */     genBlockInitAction(localRuleBlock);
/* 2216:     */     
/* 2217:     */ 
/* 2218:2782 */     ExceptionSpec localExceptionSpec = localRuleBlock.findExceptionSpec("");
/* 2219:2785 */     if ((localExceptionSpec != null) || (localRuleBlock.getDefaultErrorHandler()))
/* 2220:     */     {
/* 2221:2786 */       println("try:      ## for error handling");
/* 2222:2787 */       this.tabs += 1;
/* 2223:     */     }
/* 2224:2789 */     int i = this.tabs;
/* 2225:     */     Object localObject;
/* 2226:2791 */     if (localRuleBlock.alternatives.size() == 1)
/* 2227:     */     {
/* 2228:2794 */       Alternative localAlternative = localRuleBlock.getAlternativeAt(0);
/* 2229:2795 */       localObject = localAlternative.semPred;
/* 2230:2796 */       if (localObject != null) {
/* 2231:2797 */         genSemPred((String)localObject, this.currentRule.line);
/* 2232:     */       }
/* 2233:2798 */       if (localAlternative.synPred != null) {
/* 2234:2800 */         this.antlrTool.warning("Syntactic predicate ignored for single alternative", this.grammar.getFilename(), localAlternative.synPred.getLine(), localAlternative.synPred.getColumn());
/* 2235:     */       }
/* 2236:2807 */       genAlt(localAlternative, localRuleBlock);
/* 2237:     */     }
/* 2238:     */     else
/* 2239:     */     {
/* 2240:2812 */       boolean bool2 = this.grammar.theLLkAnalyzer.deterministic(localRuleBlock);
/* 2241:     */       
/* 2242:2814 */       localObject = genCommonBlock(localRuleBlock, false);
/* 2243:2815 */       genBlockFinish((PythonBlockFinishingInfo)localObject, this.throwNoViable);
/* 2244:     */     }
/* 2245:2817 */     this.tabs = i;
/* 2246:2820 */     if ((localExceptionSpec != null) || (localRuleBlock.getDefaultErrorHandler()))
/* 2247:     */     {
/* 2248:2822 */       this.tabs -= 1;
/* 2249:2823 */       println("");
/* 2250:     */     }
/* 2251:2827 */     if (localExceptionSpec != null)
/* 2252:     */     {
/* 2253:2828 */       genErrorHandler(localExceptionSpec);
/* 2254:     */     }
/* 2255:2830 */     else if (localRuleBlock.getDefaultErrorHandler())
/* 2256:     */     {
/* 2257:2832 */       println("except " + this.exceptionThrown + ", ex:");
/* 2258:2833 */       this.tabs += 1;
/* 2259:2835 */       if (this.grammar.hasSyntacticPredicate)
/* 2260:     */       {
/* 2261:2836 */         println("if not self.inputState.guessing:");
/* 2262:2837 */         this.tabs += 1;
/* 2263:     */       }
/* 2264:2839 */       println("self.reportError(ex)");
/* 2265:2840 */       if (!(this.grammar instanceof TreeWalkerGrammar))
/* 2266:     */       {
/* 2267:2842 */         Lookahead localLookahead = this.grammar.theLLkAnalyzer.FOLLOW(1, localRuleBlock.endNode);
/* 2268:2843 */         localObject = getBitsetName(markBitsetForGen(localLookahead.fset));
/* 2269:2844 */         println("self.consume()");
/* 2270:2845 */         println("self.consumeUntil(" + (String)localObject + ")");
/* 2271:     */       }
/* 2272:     */       else
/* 2273:     */       {
/* 2274:2849 */         println("if _t:");
/* 2275:2850 */         this.tabs += 1;
/* 2276:2851 */         println("_t = _t.getNextSibling()");
/* 2277:2852 */         this.tabs -= 1;
/* 2278:     */       }
/* 2279:2854 */       if (this.grammar.hasSyntacticPredicate)
/* 2280:     */       {
/* 2281:2855 */         this.tabs -= 1;
/* 2282:     */         
/* 2283:2857 */         println("else:");
/* 2284:2858 */         this.tabs += 1;
/* 2285:2859 */         println("raise ex");
/* 2286:2860 */         this.tabs -= 1;
/* 2287:     */       }
/* 2288:2863 */       this.tabs -= 1;
/* 2289:2864 */       println("");
/* 2290:     */     }
/* 2291:2868 */     if (this.grammar.buildAST) {
/* 2292:2869 */       println("self.returnAST = " + paramRuleSymbol.getId() + "_AST");
/* 2293:     */     }
/* 2294:2873 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2295:2874 */       println("self._retTree = _t");
/* 2296:     */     }
/* 2297:2878 */     if (localRuleBlock.getTestLiterals()) {
/* 2298:2879 */       if (paramRuleSymbol.access.equals("protected")) {
/* 2299:2880 */         genLiteralsTestForPartialToken();
/* 2300:     */       } else {
/* 2301:2883 */         genLiteralsTest();
/* 2302:     */       }
/* 2303:     */     }
/* 2304:2888 */     if ((this.grammar instanceof LexerGrammar)) {
/* 2305:2890 */       println("self.set_return_token(_createToken, _token, _ttype, _begin)");
/* 2306:     */     }
/* 2307:2893 */     if (localRuleBlock.returnAction != null) {
/* 2308:2897 */       println("return " + extractIdOfAction(localRuleBlock.returnAction, localRuleBlock.getLine(), localRuleBlock.getColumn()) + "");
/* 2309:     */     }
/* 2310:2908 */     if ((this.grammar.debuggingOutput) || (this.grammar.traceRules))
/* 2311:     */     {
/* 2312:2909 */       this.tabs -= 1;
/* 2313:2910 */       println("finally:  ### debugging");
/* 2314:2911 */       this.tabs += 1;
/* 2315:2914 */       if (this.grammar.debuggingOutput) {
/* 2316:2915 */         if ((this.grammar instanceof ParserGrammar)) {
/* 2317:2916 */           println("self.fireExitRule(" + paramInt + ", 0)");
/* 2318:2917 */         } else if ((this.grammar instanceof LexerGrammar)) {
/* 2319:2918 */           println("self.fireExitRule(" + paramInt + ", _ttype)");
/* 2320:     */         }
/* 2321:     */       }
/* 2322:2920 */       if (this.grammar.traceRules) {
/* 2323:2921 */         if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2324:2922 */           println("self.traceOut(\"" + paramRuleSymbol.getId() + "\", _t)");
/* 2325:     */         } else {
/* 2326:2925 */           println("self.traceOut(\"" + paramRuleSymbol.getId() + "\")");
/* 2327:     */         }
/* 2328:     */       }
/* 2329:2928 */       this.tabs -= 1;
/* 2330:     */     }
/* 2331:2930 */     this.tabs -= 1;
/* 2332:2931 */     println("");
/* 2333:     */     
/* 2334:     */ 
/* 2335:2934 */     this.genAST = bool1;
/* 2336:     */   }
/* 2337:     */   
/* 2338:     */   private void GenRuleInvocation(RuleRefElement paramRuleRefElement)
/* 2339:     */   {
/* 2340:2942 */     _print("self." + paramRuleRefElement.targetRule + "(");
/* 2341:2945 */     if ((this.grammar instanceof LexerGrammar))
/* 2342:     */     {
/* 2343:2947 */       if (paramRuleRefElement.getLabel() != null) {
/* 2344:2948 */         _print("True");
/* 2345:     */       } else {
/* 2346:2951 */         _print("False");
/* 2347:     */       }
/* 2348:2953 */       if ((this.commonExtraArgs.length() != 0) || (paramRuleRefElement.args != null)) {
/* 2349:2954 */         _print(", ");
/* 2350:     */       }
/* 2351:     */     }
/* 2352:2959 */     _print(this.commonExtraArgs);
/* 2353:2960 */     if ((this.commonExtraArgs.length() != 0) && (paramRuleRefElement.args != null)) {
/* 2354:2961 */       _print(", ");
/* 2355:     */     }
/* 2356:2965 */     RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(paramRuleRefElement.targetRule);
/* 2357:2966 */     if (paramRuleRefElement.args != null)
/* 2358:     */     {
/* 2359:2968 */       ActionTransInfo localActionTransInfo = new ActionTransInfo();
/* 2360:2969 */       String str = processActionForSpecialSymbols(paramRuleRefElement.args, 0, this.currentRule, localActionTransInfo);
/* 2361:2970 */       if ((localActionTransInfo.assignToRoot) || (localActionTransInfo.refRuleRoot != null)) {
/* 2362:2971 */         this.antlrTool.error("Arguments of rule reference '" + paramRuleRefElement.targetRule + "' cannot set or ref #" + this.currentRule.getRuleName(), this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/* 2363:     */       }
/* 2364:2974 */       _print(str);
/* 2365:2977 */       if (localRuleSymbol.block.argAction == null) {
/* 2366:2978 */         this.antlrTool.warning("Rule '" + paramRuleRefElement.targetRule + "' accepts no arguments", this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/* 2367:     */       }
/* 2368:     */     }
/* 2369:2984 */     else if (localRuleSymbol.block.argAction != null)
/* 2370:     */     {
/* 2371:2985 */       this.antlrTool.warning("Missing parameters on reference to rule " + paramRuleRefElement.targetRule, this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/* 2372:     */     }
/* 2373:2988 */     _println(")");
/* 2374:2991 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2375:2992 */       println("_t = self._retTree");
/* 2376:     */     }
/* 2377:     */   }
/* 2378:     */   
/* 2379:     */   protected void genSemPred(String paramString, int paramInt)
/* 2380:     */   {
/* 2381:2998 */     ActionTransInfo localActionTransInfo = new ActionTransInfo();
/* 2382:2999 */     paramString = processActionForSpecialSymbols(paramString, paramInt, this.currentRule, localActionTransInfo);
/* 2383:     */     
/* 2384:     */ 
/* 2385:3002 */     String str = this.charFormatter.escapeString(paramString);
/* 2386:3006 */     if ((this.grammar.debuggingOutput) && (((this.grammar instanceof ParserGrammar)) || ((this.grammar instanceof LexerGrammar)))) {
/* 2387:3008 */       paramString = "fireSemanticPredicateEvaluated(antlr.debug.SemanticPredicateEvent.VALIDATING," + addSemPred(str) + ", " + paramString + ")";
/* 2388:     */     }
/* 2389:3012 */     println("if not " + paramString + ":");
/* 2390:3013 */     this.tabs += 1;
/* 2391:3014 */     println("raise antlr.SemanticException(\"" + str + "\")");
/* 2392:3015 */     this.tabs -= 1;
/* 2393:     */   }
/* 2394:     */   
/* 2395:     */   protected void genSemPredMap()
/* 2396:     */   {
/* 2397:3022 */     Enumeration localEnumeration = this.semPreds.elements();
/* 2398:3023 */     println("_semPredNames = [");
/* 2399:3024 */     this.tabs += 1;
/* 2400:3025 */     while (localEnumeration.hasMoreElements()) {
/* 2401:3026 */       println("\"" + localEnumeration.nextElement() + "\",");
/* 2402:     */     }
/* 2403:3028 */     this.tabs -= 1;
/* 2404:3029 */     println("]");
/* 2405:     */   }
/* 2406:     */   
/* 2407:     */   protected void genSynPred(SynPredBlock paramSynPredBlock, String paramString)
/* 2408:     */   {
/* 2409:3033 */     if (this.DEBUG_CODE_GENERATOR) {
/* 2410:3033 */       System.out.println("gen=>(" + paramSynPredBlock + ")");
/* 2411:     */     }
/* 2412:3036 */     println("synPredMatched" + paramSynPredBlock.ID + " = False");
/* 2413:     */     
/* 2414:3038 */     println("if " + paramString + ":");
/* 2415:3039 */     this.tabs += 1;
/* 2416:3042 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2417:3043 */       println("_t" + paramSynPredBlock.ID + " = _t");
/* 2418:     */     } else {
/* 2419:3046 */       println("_m" + paramSynPredBlock.ID + " = self.mark()");
/* 2420:     */     }
/* 2421:3050 */     println("synPredMatched" + paramSynPredBlock.ID + " = True");
/* 2422:3051 */     println("self.inputState.guessing += 1");
/* 2423:3054 */     if ((this.grammar.debuggingOutput) && (((this.grammar instanceof ParserGrammar)) || ((this.grammar instanceof LexerGrammar)))) {
/* 2424:3056 */       println("self.fireSyntacticPredicateStarted()");
/* 2425:     */     }
/* 2426:3059 */     this.syntacticPredLevel += 1;
/* 2427:3060 */     println("try:");
/* 2428:3061 */     this.tabs += 1;
/* 2429:3062 */     gen(paramSynPredBlock);
/* 2430:3063 */     this.tabs -= 1;
/* 2431:3064 */     println("except " + this.exceptionThrown + ", pe:");
/* 2432:3065 */     this.tabs += 1;
/* 2433:3066 */     println("synPredMatched" + paramSynPredBlock.ID + " = False");
/* 2434:3067 */     this.tabs -= 1;
/* 2435:3070 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2436:3071 */       println("_t = _t" + paramSynPredBlock.ID + "");
/* 2437:     */     } else {
/* 2438:3074 */       println("self.rewind(_m" + paramSynPredBlock.ID + ")");
/* 2439:     */     }
/* 2440:3077 */     println("self.inputState.guessing -= 1");
/* 2441:3080 */     if ((this.grammar.debuggingOutput) && (((this.grammar instanceof ParserGrammar)) || ((this.grammar instanceof LexerGrammar))))
/* 2442:     */     {
/* 2443:3082 */       println("if synPredMatched" + paramSynPredBlock.ID + ":");
/* 2444:3083 */       this.tabs += 1;
/* 2445:3084 */       println("self.fireSyntacticPredicateSucceeded()");
/* 2446:3085 */       this.tabs -= 1;
/* 2447:3086 */       println("else:");
/* 2448:3087 */       this.tabs += 1;
/* 2449:3088 */       println("self.fireSyntacticPredicateFailed()");
/* 2450:3089 */       this.tabs -= 1;
/* 2451:     */     }
/* 2452:3092 */     this.syntacticPredLevel -= 1;
/* 2453:3093 */     this.tabs -= 1;
/* 2454:     */     
/* 2455:     */ 
/* 2456:     */ 
/* 2457:     */ 
/* 2458:3098 */     println("if synPredMatched" + paramSynPredBlock.ID + ":");
/* 2459:     */   }
/* 2460:     */   
/* 2461:     */   public void genTokenStrings()
/* 2462:     */   {
/* 2463:3112 */     int i = this.tabs;
/* 2464:3113 */     this.tabs = 0;
/* 2465:     */     
/* 2466:3115 */     println("");
/* 2467:3116 */     println("_tokenNames = [");
/* 2468:3117 */     this.tabs += 1;
/* 2469:     */     
/* 2470:     */ 
/* 2471:     */ 
/* 2472:3121 */     Vector localVector = this.grammar.tokenManager.getVocabulary();
/* 2473:3122 */     for (int j = 0; j < localVector.size(); j++)
/* 2474:     */     {
/* 2475:3123 */       String str = (String)localVector.elementAt(j);
/* 2476:3124 */       if (str == null) {
/* 2477:3125 */         str = "<" + String.valueOf(j) + ">";
/* 2478:     */       }
/* 2479:3127 */       if ((!str.startsWith("\"")) && (!str.startsWith("<")))
/* 2480:     */       {
/* 2481:3128 */         TokenSymbol localTokenSymbol = this.grammar.tokenManager.getTokenSymbol(str);
/* 2482:3129 */         if ((localTokenSymbol != null) && (localTokenSymbol.getParaphrase() != null)) {
/* 2483:3130 */           str = StringUtils.stripFrontBack(localTokenSymbol.getParaphrase(), "\"", "\"");
/* 2484:     */         }
/* 2485:     */       }
/* 2486:3133 */       print(this.charFormatter.literalString(str));
/* 2487:3134 */       if (j != localVector.size() - 1) {
/* 2488:3135 */         _print(", ");
/* 2489:     */       }
/* 2490:3137 */       _println("");
/* 2491:     */     }
/* 2492:3141 */     this.tabs -= 1;
/* 2493:3142 */     println("]");
/* 2494:3143 */     this.tabs = i;
/* 2495:     */   }
/* 2496:     */   
/* 2497:     */   protected void genTokenASTNodeMap()
/* 2498:     */   {
/* 2499:3150 */     println("");
/* 2500:3151 */     println("def buildTokenTypeASTClassMap(self):");
/* 2501:     */     
/* 2502:     */ 
/* 2503:3154 */     this.tabs += 1;
/* 2504:3155 */     int i = 0;
/* 2505:3156 */     int j = 0;
/* 2506:     */     
/* 2507:3158 */     Vector localVector = this.grammar.tokenManager.getVocabulary();
/* 2508:3159 */     for (int k = 0; k < localVector.size(); k++)
/* 2509:     */     {
/* 2510:3160 */       String str = (String)localVector.elementAt(k);
/* 2511:3161 */       if (str != null)
/* 2512:     */       {
/* 2513:3162 */         TokenSymbol localTokenSymbol = this.grammar.tokenManager.getTokenSymbol(str);
/* 2514:3163 */         if ((localTokenSymbol != null) && (localTokenSymbol.getASTNodeType() != null))
/* 2515:     */         {
/* 2516:3164 */           j++;
/* 2517:3165 */           if (i == 0)
/* 2518:     */           {
/* 2519:3167 */             println("self.tokenTypeToASTClassMap = {}");
/* 2520:3168 */             i = 1;
/* 2521:     */           }
/* 2522:3170 */           println("self.tokenTypeToASTClassMap[" + localTokenSymbol.getTokenType() + "] = " + localTokenSymbol.getASTNodeType());
/* 2523:     */         }
/* 2524:     */       }
/* 2525:     */     }
/* 2526:3179 */     if (j == 0) {
/* 2527:3180 */       println("self.tokenTypeToASTClassMap = None");
/* 2528:     */     }
/* 2529:3182 */     this.tabs -= 1;
/* 2530:     */   }
/* 2531:     */   
/* 2532:     */   protected void genTokenTypes(TokenManager paramTokenManager)
/* 2533:     */     throws IOException
/* 2534:     */   {
/* 2535:3193 */     this.tabs = 0;
/* 2536:     */     
/* 2537:     */ 
/* 2538:     */ 
/* 2539:     */ 
/* 2540:     */ 
/* 2541:     */ 
/* 2542:     */ 
/* 2543:3201 */     Vector localVector = paramTokenManager.getVocabulary();
/* 2544:     */     
/* 2545:     */ 
/* 2546:3204 */     println("SKIP                = antlr.SKIP");
/* 2547:3205 */     println("INVALID_TYPE        = antlr.INVALID_TYPE");
/* 2548:3206 */     println("EOF_TYPE            = antlr.EOF_TYPE");
/* 2549:3207 */     println("EOF                 = antlr.EOF");
/* 2550:3208 */     println("NULL_TREE_LOOKAHEAD = antlr.NULL_TREE_LOOKAHEAD");
/* 2551:3209 */     println("MIN_USER_TYPE       = antlr.MIN_USER_TYPE");
/* 2552:3211 */     for (int i = 4; i < localVector.size(); i++)
/* 2553:     */     {
/* 2554:3213 */       String str1 = (String)localVector.elementAt(i);
/* 2555:3214 */       if (str1 != null) {
/* 2556:3216 */         if (str1.startsWith("\""))
/* 2557:     */         {
/* 2558:3219 */           StringLiteralSymbol localStringLiteralSymbol = (StringLiteralSymbol)paramTokenManager.getTokenSymbol(str1);
/* 2559:3220 */           if (localStringLiteralSymbol == null) {
/* 2560:3221 */             this.antlrTool.panic("String literal " + str1 + " not in symbol table");
/* 2561:     */           }
/* 2562:3223 */           if (localStringLiteralSymbol.label != null)
/* 2563:     */           {
/* 2564:3225 */             println(localStringLiteralSymbol.label + " = " + i);
/* 2565:     */           }
/* 2566:     */           else
/* 2567:     */           {
/* 2568:3229 */             String str2 = mangleLiteral(str1);
/* 2569:3230 */             if (str2 != null)
/* 2570:     */             {
/* 2571:3232 */               println(str2 + " = " + i);
/* 2572:     */               
/* 2573:3234 */               localStringLiteralSymbol.label = str2;
/* 2574:     */             }
/* 2575:     */             else
/* 2576:     */             {
/* 2577:3238 */               println("### " + str1 + " = " + i);
/* 2578:     */             }
/* 2579:     */           }
/* 2580:     */         }
/* 2581:3242 */         else if (!str1.startsWith("<"))
/* 2582:     */         {
/* 2583:3243 */           println(str1 + " = " + i);
/* 2584:     */         }
/* 2585:     */       }
/* 2586:     */     }
/* 2587:3249 */     this.tabs -= 1;
/* 2588:     */     
/* 2589:3251 */     exitIfError();
/* 2590:     */   }
/* 2591:     */   
/* 2592:     */   public String getASTCreateString(Vector paramVector)
/* 2593:     */   {
/* 2594:3258 */     if (paramVector.size() == 0) {
/* 2595:3259 */       return "";
/* 2596:     */     }
/* 2597:3261 */     StringBuffer localStringBuffer = new StringBuffer();
/* 2598:3262 */     localStringBuffer.append("antlr.make(");
/* 2599:3263 */     for (int i = 0; i < paramVector.size(); i++)
/* 2600:     */     {
/* 2601:3264 */       localStringBuffer.append(paramVector.elementAt(i));
/* 2602:3265 */       if (i + 1 < paramVector.size()) {
/* 2603:3266 */         localStringBuffer.append(", ");
/* 2604:     */       }
/* 2605:     */     }
/* 2606:3269 */     localStringBuffer.append(")");
/* 2607:3270 */     return localStringBuffer.toString();
/* 2608:     */   }
/* 2609:     */   
/* 2610:     */   public String getASTCreateString(GrammarAtom paramGrammarAtom, String paramString)
/* 2611:     */   {
/* 2612:3279 */     if ((paramGrammarAtom != null) && (paramGrammarAtom.getASTNodeType() != null)) {
/* 2613:3282 */       return "self.astFactory.create(" + paramString + ", " + paramGrammarAtom.getASTNodeType() + ")";
/* 2614:     */     }
/* 2615:3287 */     return getASTCreateString(paramString);
/* 2616:     */   }
/* 2617:     */   
/* 2618:     */   public String getASTCreateString(String paramString)
/* 2619:     */   {
/* 2620:3300 */     if (paramString == null) {
/* 2621:3301 */       paramString = "";
/* 2622:     */     }
/* 2623:3303 */     int i = 0;
/* 2624:3304 */     for (int j = 0; j < paramString.length(); j++) {
/* 2625:3305 */       if (paramString.charAt(j) == ',') {
/* 2626:3306 */         i++;
/* 2627:     */       }
/* 2628:     */     }
/* 2629:3309 */     if (i < 2)
/* 2630:     */     {
/* 2631:3310 */       j = paramString.indexOf(',');
/* 2632:3311 */       int k = paramString.lastIndexOf(',');
/* 2633:3312 */       String str1 = paramString;
/* 2634:3313 */       if (i > 0) {
/* 2635:3314 */         str1 = paramString.substring(0, j);
/* 2636:     */       }
/* 2637:3316 */       TokenSymbol localTokenSymbol = this.grammar.tokenManager.getTokenSymbol(str1);
/* 2638:3317 */       if (localTokenSymbol != null)
/* 2639:     */       {
/* 2640:3318 */         String str2 = localTokenSymbol.getASTNodeType();
/* 2641:3319 */         String str3 = "";
/* 2642:3320 */         if (i == 0) {
/* 2643:3322 */           str3 = ", \"\"";
/* 2644:     */         }
/* 2645:3324 */         if (str2 != null) {
/* 2646:3325 */           return "self.astFactory.create(" + paramString + str3 + ", " + str2 + ")";
/* 2647:     */         }
/* 2648:     */       }
/* 2649:3330 */       if (this.labeledElementASTType.equals("AST")) {
/* 2650:3331 */         return "self.astFactory.create(" + paramString + ")";
/* 2651:     */       }
/* 2652:3333 */       return "self.astFactory.create(" + paramString + ")";
/* 2653:     */     }
/* 2654:3337 */     return "self.astFactory.create(" + paramString + ")";
/* 2655:     */   }
/* 2656:     */   
/* 2657:     */   protected String getLookaheadTestExpression(Lookahead[] paramArrayOfLookahead, int paramInt)
/* 2658:     */   {
/* 2659:3341 */     StringBuffer localStringBuffer = new StringBuffer(100);
/* 2660:3342 */     int i = 1;
/* 2661:     */     
/* 2662:3344 */     localStringBuffer.append("(");
/* 2663:3345 */     for (int j = 1; j <= paramInt; j++)
/* 2664:     */     {
/* 2665:3346 */       BitSet localBitSet = paramArrayOfLookahead[j].fset;
/* 2666:3347 */       if (i == 0) {
/* 2667:3348 */         localStringBuffer.append(") and (");
/* 2668:     */       }
/* 2669:3350 */       i = 0;
/* 2670:3355 */       if (paramArrayOfLookahead[j].containsEpsilon()) {
/* 2671:3356 */         localStringBuffer.append("True");
/* 2672:     */       } else {
/* 2673:3359 */         localStringBuffer.append(getLookaheadTestTerm(j, localBitSet));
/* 2674:     */       }
/* 2675:     */     }
/* 2676:3363 */     localStringBuffer.append(")");
/* 2677:3364 */     String str = localStringBuffer.toString();
/* 2678:3365 */     return str;
/* 2679:     */   }
/* 2680:     */   
/* 2681:     */   protected String getLookaheadTestExpression(Alternative paramAlternative, int paramInt)
/* 2682:     */   {
/* 2683:3373 */     int i = paramAlternative.lookaheadDepth;
/* 2684:3374 */     if (i == 2147483647) {
/* 2685:3378 */       i = this.grammar.maxk;
/* 2686:     */     }
/* 2687:3381 */     if (paramInt == 0) {
/* 2688:3385 */       return "True";
/* 2689:     */     }
/* 2690:3388 */     return getLookaheadTestExpression(paramAlternative.cache, i);
/* 2691:     */   }
/* 2692:     */   
/* 2693:     */   protected String getLookaheadTestTerm(int paramInt, BitSet paramBitSet)
/* 2694:     */   {
/* 2695:3401 */     String str1 = lookaheadString(paramInt);
/* 2696:     */     
/* 2697:     */ 
/* 2698:3404 */     int[] arrayOfInt = paramBitSet.toArray();
/* 2699:3405 */     if (elementsAreRange(arrayOfInt))
/* 2700:     */     {
/* 2701:3406 */       localObject = getRangeExpression(paramInt, arrayOfInt);
/* 2702:3407 */       return localObject;
/* 2703:     */     }
/* 2704:3412 */     int i = paramBitSet.degree();
/* 2705:3413 */     if (i == 0) {
/* 2706:3414 */       return "True";
/* 2707:     */     }
/* 2708:3417 */     if (i >= this.bitsetTestThreshold)
/* 2709:     */     {
/* 2710:3418 */       j = markBitsetForGen(paramBitSet);
/* 2711:3419 */       return getBitsetName(j) + ".member(" + str1 + ")";
/* 2712:     */     }
/* 2713:3423 */     Object localObject = new StringBuffer();
/* 2714:3424 */     for (int j = 0; j < arrayOfInt.length; j++)
/* 2715:     */     {
/* 2716:3426 */       String str3 = getValueString(arrayOfInt[j], true);
/* 2717:3429 */       if (j > 0) {
/* 2718:3429 */         ((StringBuffer)localObject).append(" or ");
/* 2719:     */       }
/* 2720:3430 */       ((StringBuffer)localObject).append(str1);
/* 2721:3431 */       ((StringBuffer)localObject).append("==");
/* 2722:3432 */       ((StringBuffer)localObject).append(str3);
/* 2723:     */     }
/* 2724:3434 */     String str2 = ((StringBuffer)localObject).toString();
/* 2725:3435 */     return ((StringBuffer)localObject).toString();
/* 2726:     */   }
/* 2727:     */   
/* 2728:     */   public String getRangeExpression(int paramInt, int[] paramArrayOfInt)
/* 2729:     */   {
/* 2730:3444 */     if (!elementsAreRange(paramArrayOfInt)) {
/* 2731:3445 */       this.antlrTool.panic("getRangeExpression called with non-range");
/* 2732:     */     }
/* 2733:3447 */     int i = paramArrayOfInt[0];
/* 2734:3448 */     int j = paramArrayOfInt[(paramArrayOfInt.length - 1)];
/* 2735:3449 */     return "(" + lookaheadString(paramInt) + " >= " + getValueString(i, true) + " and " + lookaheadString(paramInt) + " <= " + getValueString(j, true) + ")";
/* 2736:     */   }
/* 2737:     */   
/* 2738:     */   private String getValueString(int paramInt, boolean paramBoolean)
/* 2739:     */   {
/* 2740:     */     Object localObject;
/* 2741:3459 */     if ((this.grammar instanceof LexerGrammar))
/* 2742:     */     {
/* 2743:3460 */       localObject = this.charFormatter.literalChar(paramInt);
/* 2744:3461 */       if (paramBoolean) {
/* 2745:3462 */         localObject = "u'" + (String)localObject + "'";
/* 2746:     */       }
/* 2747:3463 */       return localObject;
/* 2748:     */     }
/* 2749:3467 */     TokenSymbol localTokenSymbol = this.grammar.tokenManager.getTokenSymbolAt(paramInt);
/* 2750:3471 */     if (localTokenSymbol == null)
/* 2751:     */     {
/* 2752:3472 */       localObject = "" + paramInt;
/* 2753:3473 */       return localObject;
/* 2754:     */     }
/* 2755:3476 */     String str1 = localTokenSymbol.getId();
/* 2756:3477 */     if (!(localTokenSymbol instanceof StringLiteralSymbol))
/* 2757:     */     {
/* 2758:3479 */       localObject = str1;
/* 2759:3480 */       return localObject;
/* 2760:     */     }
/* 2761:3486 */     StringLiteralSymbol localStringLiteralSymbol = (StringLiteralSymbol)localTokenSymbol;
/* 2762:3487 */     String str2 = localStringLiteralSymbol.getLabel();
/* 2763:3488 */     if (str2 != null)
/* 2764:     */     {
/* 2765:3489 */       localObject = str2;
/* 2766:     */     }
/* 2767:     */     else
/* 2768:     */     {
/* 2769:3493 */       localObject = mangleLiteral(str1);
/* 2770:3494 */       if (localObject == null) {
/* 2771:3495 */         localObject = String.valueOf(paramInt);
/* 2772:     */       }
/* 2773:     */     }
/* 2774:3498 */     return localObject;
/* 2775:     */   }
/* 2776:     */   
/* 2777:     */   protected boolean lookaheadIsEmpty(Alternative paramAlternative, int paramInt)
/* 2778:     */   {
/* 2779:3503 */     int i = paramAlternative.lookaheadDepth;
/* 2780:3504 */     if (i == 2147483647) {
/* 2781:3505 */       i = this.grammar.maxk;
/* 2782:     */     }
/* 2783:3507 */     for (int j = 1; (j <= i) && (j <= paramInt); j++)
/* 2784:     */     {
/* 2785:3508 */       BitSet localBitSet = paramAlternative.cache[j].fset;
/* 2786:3509 */       if (localBitSet.degree() != 0) {
/* 2787:3510 */         return false;
/* 2788:     */       }
/* 2789:     */     }
/* 2790:3513 */     return true;
/* 2791:     */   }
/* 2792:     */   
/* 2793:     */   private String lookaheadString(int paramInt)
/* 2794:     */   {
/* 2795:3518 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2796:3519 */       return "_t.getType()";
/* 2797:     */     }
/* 2798:3521 */     return "self.LA(" + paramInt + ")";
/* 2799:     */   }
/* 2800:     */   
/* 2801:     */   private String mangleLiteral(String paramString)
/* 2802:     */   {
/* 2803:3531 */     String str = this.antlrTool.literalsPrefix;
/* 2804:3532 */     for (int i = 1; i < paramString.length() - 1; i++)
/* 2805:     */     {
/* 2806:3533 */       if ((!Character.isLetter(paramString.charAt(i))) && (paramString.charAt(i) != '_')) {
/* 2807:3535 */         return null;
/* 2808:     */       }
/* 2809:3537 */       str = str + paramString.charAt(i);
/* 2810:     */     }
/* 2811:3539 */     if (this.antlrTool.upperCaseMangledLiterals) {
/* 2812:3540 */       str = str.toUpperCase();
/* 2813:     */     }
/* 2814:3542 */     return str;
/* 2815:     */   }
/* 2816:     */   
/* 2817:     */   public String mapTreeId(String paramString, ActionTransInfo paramActionTransInfo)
/* 2818:     */   {
/* 2819:3554 */     if (this.currentRule == null) {
/* 2820:3554 */       return paramString;
/* 2821:     */     }
/* 2822:3556 */     int i = 0;
/* 2823:3557 */     String str1 = paramString;
/* 2824:3558 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2825:3559 */       if (!this.grammar.buildAST)
/* 2826:     */       {
/* 2827:3560 */         i = 1;
/* 2828:     */       }
/* 2829:3563 */       else if ((str1.length() > 3) && (str1.lastIndexOf("_in") == str1.length() - 3))
/* 2830:     */       {
/* 2831:3565 */         str1 = str1.substring(0, str1.length() - 3);
/* 2832:3566 */         i = 1;
/* 2833:     */       }
/* 2834:     */     }
/* 2835:     */     Object localObject;
/* 2836:3572 */     for (int j = 0; j < this.currentRule.labeledElements.size(); j++)
/* 2837:     */     {
/* 2838:3573 */       localObject = (AlternativeElement)this.currentRule.labeledElements.elementAt(j);
/* 2839:3574 */       if (((AlternativeElement)localObject).getLabel().equals(str1)) {
/* 2840:3575 */         return str1 + "_AST";
/* 2841:     */       }
/* 2842:     */     }
/* 2843:3582 */     String str2 = (String)this.treeVariableMap.get(str1);
/* 2844:3583 */     if (str2 != null)
/* 2845:     */     {
/* 2846:3584 */       if (str2 == NONUNIQUE)
/* 2847:     */       {
/* 2848:3586 */         this.antlrTool.error("Ambiguous reference to AST element " + str1 + " in rule " + this.currentRule.getRuleName());
/* 2849:     */         
/* 2850:     */ 
/* 2851:3589 */         return null;
/* 2852:     */       }
/* 2853:3591 */       if (str2.equals(this.currentRule.getRuleName()))
/* 2854:     */       {
/* 2855:3594 */         this.antlrTool.error("Ambiguous reference to AST element " + str1 + " in rule " + this.currentRule.getRuleName());
/* 2856:     */         
/* 2857:3596 */         return null;
/* 2858:     */       }
/* 2859:3599 */       return i != 0 ? str2 + "_in" : str2;
/* 2860:     */     }
/* 2861:3605 */     if (str1.equals(this.currentRule.getRuleName()))
/* 2862:     */     {
/* 2863:3606 */       localObject = str1 + "_AST";
/* 2864:3607 */       if ((paramActionTransInfo != null) && 
/* 2865:3608 */         (i == 0)) {
/* 2866:3609 */         paramActionTransInfo.refRuleRoot = ((String)localObject);
/* 2867:     */       }
/* 2868:3612 */       return localObject;
/* 2869:     */     }
/* 2870:3616 */     return str1;
/* 2871:     */   }
/* 2872:     */   
/* 2873:     */   private void mapTreeVariable(AlternativeElement paramAlternativeElement, String paramString)
/* 2874:     */   {
/* 2875:3625 */     if ((paramAlternativeElement instanceof TreeElement))
/* 2876:     */     {
/* 2877:3626 */       mapTreeVariable(((TreeElement)paramAlternativeElement).root, paramString);
/* 2878:3627 */       return;
/* 2879:     */     }
/* 2880:3631 */     String str = null;
/* 2881:3634 */     if (paramAlternativeElement.getLabel() == null) {
/* 2882:3635 */       if ((paramAlternativeElement instanceof TokenRefElement)) {
/* 2883:3637 */         str = ((TokenRefElement)paramAlternativeElement).atomText;
/* 2884:3639 */       } else if ((paramAlternativeElement instanceof RuleRefElement)) {
/* 2885:3641 */         str = ((RuleRefElement)paramAlternativeElement).targetRule;
/* 2886:     */       }
/* 2887:     */     }
/* 2888:3645 */     if (str != null) {
/* 2889:3646 */       if (this.treeVariableMap.get(str) != null)
/* 2890:     */       {
/* 2891:3648 */         this.treeVariableMap.remove(str);
/* 2892:3649 */         this.treeVariableMap.put(str, NONUNIQUE);
/* 2893:     */       }
/* 2894:     */       else
/* 2895:     */       {
/* 2896:3652 */         this.treeVariableMap.put(str, paramString);
/* 2897:     */       }
/* 2898:     */     }
/* 2899:     */   }
/* 2900:     */   
/* 2901:     */   protected String processActionForSpecialSymbols(String paramString, int paramInt, RuleBlock paramRuleBlock, ActionTransInfo paramActionTransInfo)
/* 2902:     */   {
/* 2903:3664 */     if ((paramString == null) || (paramString.length() == 0)) {
/* 2904:3665 */       return null;
/* 2905:     */     }
/* 2906:3667 */     if (isEmpty(paramString)) {
/* 2907:3668 */       return "";
/* 2908:     */     }
/* 2909:3672 */     if (this.grammar == null) {
/* 2910:3674 */       return paramString;
/* 2911:     */     }
/* 2912:3678 */     ActionLexer localActionLexer = new ActionLexer(paramString, paramRuleBlock, this, paramActionTransInfo);
/* 2913:     */     
/* 2914:     */ 
/* 2915:     */ 
/* 2916:     */ 
/* 2917:     */ 
/* 2918:     */ 
/* 2919:3685 */     localActionLexer.setLineOffset(paramInt);
/* 2920:3686 */     localActionLexer.setFilename(this.grammar.getFilename());
/* 2921:3687 */     localActionLexer.setTool(this.antlrTool);
/* 2922:     */     try
/* 2923:     */     {
/* 2924:3690 */       localActionLexer.mACTION(true);
/* 2925:3691 */       paramString = localActionLexer.getTokenObject().getText();
/* 2926:     */     }
/* 2927:     */     catch (RecognitionException localRecognitionException)
/* 2928:     */     {
/* 2929:3694 */       localActionLexer.reportError(localRecognitionException);
/* 2930:     */     }
/* 2931:     */     catch (TokenStreamException localTokenStreamException)
/* 2932:     */     {
/* 2933:3697 */       this.antlrTool.panic("Error reading action:" + paramString);
/* 2934:     */     }
/* 2935:     */     catch (CharStreamException localCharStreamException)
/* 2936:     */     {
/* 2937:3700 */       this.antlrTool.panic("Error reading action:" + paramString);
/* 2938:     */     }
/* 2939:3702 */     return paramString;
/* 2940:     */   }
/* 2941:     */   
/* 2942:     */   static boolean isEmpty(String paramString)
/* 2943:     */   {
/* 2944:3707 */     boolean bool = true;
/* 2945:3710 */     for (int j = 0; (bool) && (j < paramString.length()); j++)
/* 2946:     */     {
/* 2947:3711 */       int i = paramString.charAt(j);
/* 2948:3712 */       switch (i)
/* 2949:     */       {
/* 2950:     */       case 9: 
/* 2951:     */       case 10: 
/* 2952:     */       case 12: 
/* 2953:     */       case 13: 
/* 2954:     */       case 32: 
/* 2955:     */         break;
/* 2956:     */       default: 
/* 2957:3722 */         bool = false;
/* 2958:     */       }
/* 2959:     */     }
/* 2960:3726 */     return bool;
/* 2961:     */   }
/* 2962:     */   
/* 2963:     */   protected String processActionCode(String paramString, int paramInt)
/* 2964:     */   {
/* 2965:3731 */     if ((paramString == null) || (isEmpty(paramString))) {
/* 2966:3732 */       return "";
/* 2967:     */     }
/* 2968:3734 */     CodeLexer localCodeLexer = new CodeLexer(paramString, this.grammar.getFilename(), paramInt, this.antlrTool);
/* 2969:     */     try
/* 2970:     */     {
/* 2971:3743 */       localCodeLexer.mACTION(true);
/* 2972:3744 */       paramString = localCodeLexer.getTokenObject().getText();
/* 2973:     */     }
/* 2974:     */     catch (RecognitionException localRecognitionException)
/* 2975:     */     {
/* 2976:3747 */       localCodeLexer.reportError(localRecognitionException);
/* 2977:     */     }
/* 2978:     */     catch (TokenStreamException localTokenStreamException)
/* 2979:     */     {
/* 2980:3750 */       this.antlrTool.panic("Error reading action:" + paramString);
/* 2981:     */     }
/* 2982:     */     catch (CharStreamException localCharStreamException)
/* 2983:     */     {
/* 2984:3753 */       this.antlrTool.panic("Error reading action:" + paramString);
/* 2985:     */     }
/* 2986:3755 */     return paramString;
/* 2987:     */   }
/* 2988:     */   
/* 2989:     */   protected void printActionCode(String paramString, int paramInt)
/* 2990:     */   {
/* 2991:3759 */     paramString = processActionCode(paramString, paramInt);
/* 2992:3760 */     printAction(paramString);
/* 2993:     */   }
/* 2994:     */   
/* 2995:     */   private void setupGrammarParameters(Grammar paramGrammar)
/* 2996:     */   {
/* 2997:     */     Token localToken;
/* 2998:     */     String str;
/* 2999:3764 */     if ((paramGrammar instanceof ParserGrammar))
/* 3000:     */     {
/* 3001:3766 */       this.labeledElementASTType = "";
/* 3002:3767 */       if (paramGrammar.hasOption("ASTLabelType"))
/* 3003:     */       {
/* 3004:3769 */         localToken = paramGrammar.getOption("ASTLabelType");
/* 3005:3770 */         if (localToken != null)
/* 3006:     */         {
/* 3007:3771 */           str = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/* 3008:3772 */           if (str != null) {
/* 3009:3773 */             this.labeledElementASTType = str;
/* 3010:     */           }
/* 3011:     */         }
/* 3012:     */       }
/* 3013:3777 */       this.labeledElementType = "";
/* 3014:3778 */       this.labeledElementInit = "None";
/* 3015:3779 */       this.commonExtraArgs = "";
/* 3016:3780 */       this.commonExtraParams = "self";
/* 3017:3781 */       this.commonLocalVars = "";
/* 3018:3782 */       this.lt1Value = "self.LT(1)";
/* 3019:3783 */       this.exceptionThrown = "antlr.RecognitionException";
/* 3020:3784 */       this.throwNoViable = "raise antlr.NoViableAltException(self.LT(1), self.getFilename())";
/* 3021:3785 */       this.parserClassName = "Parser";
/* 3022:3786 */       if (paramGrammar.hasOption("className"))
/* 3023:     */       {
/* 3024:3788 */         localToken = paramGrammar.getOption("className");
/* 3025:3789 */         if (localToken != null)
/* 3026:     */         {
/* 3027:3790 */           str = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/* 3028:3791 */           if (str != null) {
/* 3029:3792 */             this.parserClassName = str;
/* 3030:     */           }
/* 3031:     */         }
/* 3032:     */       }
/* 3033:3796 */       return;
/* 3034:     */     }
/* 3035:3799 */     if ((paramGrammar instanceof LexerGrammar))
/* 3036:     */     {
/* 3037:3801 */       this.labeledElementType = "char ";
/* 3038:3802 */       this.labeledElementInit = "'\\0'";
/* 3039:3803 */       this.commonExtraArgs = "";
/* 3040:3804 */       this.commonExtraParams = "self, _createToken";
/* 3041:3805 */       this.commonLocalVars = "_ttype = 0\n        _token = None\n        _begin = self.text.length()";
/* 3042:3806 */       this.lt1Value = "self.LA(1)";
/* 3043:3807 */       this.exceptionThrown = "antlr.RecognitionException";
/* 3044:3808 */       this.throwNoViable = "self.raise_NoViableAlt(self.LA(1))";
/* 3045:3809 */       this.lexerClassName = "Lexer";
/* 3046:3810 */       if (paramGrammar.hasOption("className"))
/* 3047:     */       {
/* 3048:3812 */         localToken = paramGrammar.getOption("className");
/* 3049:3813 */         if (localToken != null)
/* 3050:     */         {
/* 3051:3814 */           str = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/* 3052:3815 */           if (str != null) {
/* 3053:3816 */             this.lexerClassName = str;
/* 3054:     */           }
/* 3055:     */         }
/* 3056:     */       }
/* 3057:3820 */       return;
/* 3058:     */     }
/* 3059:3823 */     if ((paramGrammar instanceof TreeWalkerGrammar))
/* 3060:     */     {
/* 3061:3825 */       this.labeledElementASTType = "";
/* 3062:3826 */       this.labeledElementType = "";
/* 3063:3827 */       if (paramGrammar.hasOption("ASTLabelType"))
/* 3064:     */       {
/* 3065:3828 */         localToken = paramGrammar.getOption("ASTLabelType");
/* 3066:3829 */         if (localToken != null)
/* 3067:     */         {
/* 3068:3830 */           str = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/* 3069:3831 */           if (str != null)
/* 3070:     */           {
/* 3071:3832 */             this.labeledElementASTType = str;
/* 3072:3833 */             this.labeledElementType = str;
/* 3073:     */           }
/* 3074:     */         }
/* 3075:     */       }
/* 3076:3837 */       if (!paramGrammar.hasOption("ASTLabelType")) {
/* 3077:3838 */         paramGrammar.setOption("ASTLabelType", new Token(6, "<4>AST"));
/* 3078:     */       }
/* 3079:3840 */       this.labeledElementInit = "None";
/* 3080:3841 */       this.commonExtraArgs = "_t";
/* 3081:3842 */       this.commonExtraParams = "self, _t";
/* 3082:3843 */       this.commonLocalVars = "";
/* 3083:3844 */       this.lt1Value = "_t";
/* 3084:3845 */       this.exceptionThrown = "antlr.RecognitionException";
/* 3085:3846 */       this.throwNoViable = "raise antlr.NoViableAltException(_t)";
/* 3086:3847 */       this.treeWalkerClassName = "Walker";
/* 3087:3848 */       if (paramGrammar.hasOption("className"))
/* 3088:     */       {
/* 3089:3850 */         localToken = paramGrammar.getOption("className");
/* 3090:3851 */         if (localToken != null)
/* 3091:     */         {
/* 3092:3852 */           str = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/* 3093:3853 */           if (str != null) {
/* 3094:3854 */             this.treeWalkerClassName = str;
/* 3095:     */           }
/* 3096:     */         }
/* 3097:     */       }
/* 3098:3858 */       return;
/* 3099:     */     }
/* 3100:3862 */     this.antlrTool.panic("Unknown grammar type");
/* 3101:     */   }
/* 3102:     */   
/* 3103:     */   public void setupOutput(String paramString)
/* 3104:     */     throws IOException
/* 3105:     */   {
/* 3106:3870 */     this.currentOutput = this.antlrTool.openOutputFile(paramString + ".py");
/* 3107:     */   }
/* 3108:     */   
/* 3109:     */   protected boolean isspace(char paramChar)
/* 3110:     */   {
/* 3111:3874 */     boolean bool = true;
/* 3112:3875 */     switch (paramChar)
/* 3113:     */     {
/* 3114:     */     case '\t': 
/* 3115:     */     case '\n': 
/* 3116:     */     case '\r': 
/* 3117:     */     case ' ': 
/* 3118:     */       break;
/* 3119:     */     default: 
/* 3120:3882 */       bool = false;
/* 3121:     */     }
/* 3122:3885 */     return bool;
/* 3123:     */   }
/* 3124:     */   
/* 3125:     */   protected void _printAction(String paramString)
/* 3126:     */   {
/* 3127:3889 */     if (paramString == null) {
/* 3128:3890 */       return;
/* 3129:     */     }
/* 3130:3903 */     int j = 0;
/* 3131:3904 */     int k = paramString.length();
/* 3132:     */     
/* 3133:     */ 
/* 3134:3907 */     int i = 0;
/* 3135:3908 */     int m = 1;
/* 3136:     */     char c;
/* 3137:3910 */     while ((j < k) && (m != 0))
/* 3138:     */     {
/* 3139:3912 */       c = paramString.charAt(j++);
/* 3140:3913 */       switch (c)
/* 3141:     */       {
/* 3142:     */       case '\n': 
/* 3143:3915 */         i = j;
/* 3144:3916 */         break;
/* 3145:     */       case '\r': 
/* 3146:3918 */         if ((j <= k) && (paramString.charAt(j) == '\n')) {
/* 3147:3919 */           j++;
/* 3148:     */         }
/* 3149:3920 */         i = j;
/* 3150:3921 */         break;
/* 3151:     */       case ' ': 
/* 3152:     */         break;
/* 3153:     */       case '\t': 
/* 3154:     */       default: 
/* 3155:3926 */         m = 0;
/* 3156:     */       }
/* 3157:     */     }
/* 3158:3930 */     if (m == 0) {
/* 3159:3931 */       j--;
/* 3160:     */     }
/* 3161:3933 */     i = j - i;
/* 3162:     */     
/* 3163:     */ 
/* 3164:3936 */     k -= 1;
/* 3165:3937 */     while ((k > j) && (isspace(paramString.charAt(k)))) {
/* 3166:3938 */       k--;
/* 3167:     */     }
/* 3168:3941 */     int n = 0;
/* 3169:3944 */     for (int i2 = j; i2 <= k; i2++)
/* 3170:     */     {
/* 3171:3946 */       c = paramString.charAt(i2);
/* 3172:3947 */       switch (c)
/* 3173:     */       {
/* 3174:     */       case '\n': 
/* 3175:3949 */         n = 1;
/* 3176:3950 */         break;
/* 3177:     */       case '\r': 
/* 3178:3952 */         n = 1;
/* 3179:3953 */         if ((i2 + 1 <= k) && (paramString.charAt(i2 + 1) == '\n')) {
/* 3180:3954 */           i2++;
/* 3181:     */         }
/* 3182:     */         break;
/* 3183:     */       case '\t': 
/* 3184:3958 */         System.err.println("warning: tab characters used in Python action");
/* 3185:3959 */         this.currentOutput.print("        ");
/* 3186:3960 */         break;
/* 3187:     */       case ' ': 
/* 3188:3962 */         this.currentOutput.print(" ");
/* 3189:3963 */         break;
/* 3190:     */       default: 
/* 3191:3965 */         this.currentOutput.print(c);
/* 3192:     */       }
/* 3193:3969 */       if (n != 0)
/* 3194:     */       {
/* 3195:3971 */         this.currentOutput.print("\n");
/* 3196:3972 */         printTabs();
/* 3197:3973 */         int i1 = 0;
/* 3198:3974 */         n = 0;
/* 3199:3976 */         for (i2 += 1; i2 <= k; i2++)
/* 3200:     */         {
/* 3201:3978 */           c = paramString.charAt(i2);
/* 3202:3979 */           if (!isspace(c))
/* 3203:     */           {
/* 3204:3980 */             i2--;
/* 3205:3981 */             break;
/* 3206:     */           }
/* 3207:3983 */           switch (c)
/* 3208:     */           {
/* 3209:     */           case '\n': 
/* 3210:3985 */             n = 1;
/* 3211:3986 */             break;
/* 3212:     */           case '\r': 
/* 3213:3988 */             if ((i2 + 1 <= k) && (paramString.charAt(i2 + 1) == '\n')) {
/* 3214:3989 */               i2++;
/* 3215:     */             }
/* 3216:3991 */             n = 1;
/* 3217:     */           }
/* 3218:3995 */           if (n != 0)
/* 3219:     */           {
/* 3220:3997 */             this.currentOutput.print("\n");
/* 3221:3998 */             printTabs();
/* 3222:3999 */             i1 = 0;
/* 3223:4000 */             n = 0;
/* 3224:     */           }
/* 3225:     */           else
/* 3226:     */           {
/* 3227:4004 */             if (i1 >= i) {
/* 3228:     */               break;
/* 3229:     */             }
/* 3230:4005 */             i1++;
/* 3231:     */           }
/* 3232:     */         }
/* 3233:     */       }
/* 3234:     */     }
/* 3235:4015 */     this.currentOutput.println();
/* 3236:     */   }
/* 3237:     */   
/* 3238:     */   protected void od(String paramString1, int paramInt1, int paramInt2, String paramString2)
/* 3239:     */   {
/* 3240:4019 */     System.out.println(paramString2);
/* 3241:4021 */     for (int i = paramInt1; i <= paramInt2; i++)
/* 3242:     */     {
/* 3243:4023 */       char c = paramString1.charAt(i);
/* 3244:4024 */       switch (c)
/* 3245:     */       {
/* 3246:     */       case '\n': 
/* 3247:4026 */         System.out.print(" nl ");
/* 3248:4027 */         break;
/* 3249:     */       case '\t': 
/* 3250:4029 */         System.out.print(" ht ");
/* 3251:4030 */         break;
/* 3252:     */       case ' ': 
/* 3253:4032 */         System.out.print(" sp ");
/* 3254:4033 */         break;
/* 3255:     */       default: 
/* 3256:4035 */         System.out.print(" " + c + " ");
/* 3257:     */       }
/* 3258:     */     }
/* 3259:4038 */     System.out.println("");
/* 3260:     */   }
/* 3261:     */   
/* 3262:     */   protected void printAction(String paramString)
/* 3263:     */   {
/* 3264:4042 */     if (paramString != null)
/* 3265:     */     {
/* 3266:4043 */       printTabs();
/* 3267:4044 */       _printAction(paramString);
/* 3268:     */     }
/* 3269:     */   }
/* 3270:     */   
/* 3271:     */   protected void printGrammarAction(Grammar paramGrammar)
/* 3272:     */   {
/* 3273:4049 */     println("### user action >>>");
/* 3274:4050 */     printAction(processActionForSpecialSymbols(paramGrammar.classMemberAction.getText(), paramGrammar.classMemberAction.getLine(), this.currentRule, null));
/* 3275:     */     
/* 3276:     */ 
/* 3277:     */ 
/* 3278:     */ 
/* 3279:     */ 
/* 3280:     */ 
/* 3281:4057 */     println("### user action <<<");
/* 3282:     */   }
/* 3283:     */   
/* 3284:     */   protected void _printJavadoc(String paramString)
/* 3285:     */   {
/* 3286:4062 */     int i = paramString.length();
/* 3287:4063 */     int j = 0;
/* 3288:4064 */     int k = 0;
/* 3289:     */     
/* 3290:4066 */     this.currentOutput.print("\n");
/* 3291:4067 */     printTabs();
/* 3292:4068 */     this.currentOutput.print("###");
/* 3293:4070 */     for (int m = j; m < i; m++)
/* 3294:     */     {
/* 3295:4072 */       char c = paramString.charAt(m);
/* 3296:4073 */       switch (c)
/* 3297:     */       {
/* 3298:     */       case '\n': 
/* 3299:4075 */         k = 1;
/* 3300:4076 */         break;
/* 3301:     */       case '\r': 
/* 3302:4078 */         k = 1;
/* 3303:4079 */         if ((m + 1 <= i) && (paramString.charAt(m + 1) == '\n')) {
/* 3304:4080 */           m++;
/* 3305:     */         }
/* 3306:     */         break;
/* 3307:     */       case '\t': 
/* 3308:4084 */         this.currentOutput.print("\t");
/* 3309:4085 */         break;
/* 3310:     */       case ' ': 
/* 3311:4087 */         this.currentOutput.print(" ");
/* 3312:4088 */         break;
/* 3313:     */       default: 
/* 3314:4090 */         this.currentOutput.print(c);
/* 3315:     */       }
/* 3316:4094 */       if (k != 0)
/* 3317:     */       {
/* 3318:4096 */         this.currentOutput.print("\n");
/* 3319:4097 */         printTabs();
/* 3320:4098 */         this.currentOutput.print("###");
/* 3321:4099 */         k = 0;
/* 3322:     */       }
/* 3323:     */     }
/* 3324:4103 */     this.currentOutput.println();
/* 3325:     */   }
/* 3326:     */   
/* 3327:     */   protected void genJavadocComment(Grammar paramGrammar)
/* 3328:     */   {
/* 3329:4108 */     if (paramGrammar.comment != null) {
/* 3330:4109 */       _printJavadoc(paramGrammar.comment);
/* 3331:     */     }
/* 3332:     */   }
/* 3333:     */   
/* 3334:     */   protected void genJavadocComment(RuleSymbol paramRuleSymbol)
/* 3335:     */   {
/* 3336:4115 */     if (paramRuleSymbol.comment != null) {
/* 3337:4116 */       _printJavadoc(paramRuleSymbol.comment);
/* 3338:     */     }
/* 3339:     */   }
/* 3340:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.PythonCodeGenerator
 * JD-Core Version:    0.7.0.1
 */