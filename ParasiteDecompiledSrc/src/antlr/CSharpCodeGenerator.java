/*    1:     */ package antlr;
/*    2:     */ 
/*    3:     */ import antlr.actions.csharp.ActionLexer;
/*    4:     */ import antlr.collections.impl.BitSet;
/*    5:     */ import java.io.IOException;
/*    6:     */ import java.io.PrintStream;
/*    7:     */ import java.io.PrintWriter;
/*    8:     */ import java.util.Enumeration;
/*    9:     */ import java.util.Hashtable;
/*   10:     */ import java.util.StringTokenizer;
/*   11:     */ 
/*   12:     */ public class CSharpCodeGenerator
/*   13:     */   extends CodeGenerator
/*   14:     */ {
/*   15:  67 */   protected int syntacticPredLevel = 0;
/*   16:  70 */   protected boolean genAST = false;
/*   17:  73 */   protected boolean saveText = false;
/*   18:  77 */   boolean usingCustomAST = false;
/*   19:     */   String labeledElementType;
/*   20:     */   String labeledElementASTType;
/*   21:     */   String labeledElementInit;
/*   22:     */   String commonExtraArgs;
/*   23:     */   String commonExtraParams;
/*   24:     */   String commonLocalVars;
/*   25:     */   String lt1Value;
/*   26:     */   String exceptionThrown;
/*   27:     */   String throwNoViable;
/*   28:     */   RuleBlock currentRule;
/*   29:     */   String currentASTResult;
/*   30:  96 */   Hashtable treeVariableMap = new Hashtable();
/*   31: 101 */   Hashtable declaredASTVariables = new Hashtable();
/*   32: 104 */   int astVarNumber = 1;
/*   33: 107 */   protected static final String NONUNIQUE = new String();
/*   34:     */   public static final int caseSizeThreshold = 127;
/*   35:     */   private antlr.collections.impl.Vector semPreds;
/*   36:     */   private java.util.Vector astTypes;
/*   37: 116 */   private static CSharpNameSpace nameSpace = null;
/*   38:     */   int saveIndexCreateLevel;
/*   39:     */   int blockNestingLevel;
/*   40:     */   
/*   41:     */   public CSharpCodeGenerator()
/*   42:     */   {
/*   43: 129 */     this.charFormatter = new CSharpCharFormatter();
/*   44:     */   }
/*   45:     */   
/*   46:     */   protected int addSemPred(String paramString)
/*   47:     */   {
/*   48: 138 */     this.semPreds.appendElement(paramString);
/*   49: 139 */     return this.semPreds.size() - 1;
/*   50:     */   }
/*   51:     */   
/*   52:     */   public void exitIfError()
/*   53:     */   {
/*   54: 144 */     if (this.antlrTool.hasError()) {
/*   55: 146 */       this.antlrTool.fatalError("Exiting due to errors.");
/*   56:     */     }
/*   57:     */   }
/*   58:     */   
/*   59:     */   public void gen()
/*   60:     */   {
/*   61:     */     try
/*   62:     */     {
/*   63: 155 */       Enumeration localEnumeration = this.behavior.grammars.elements();
/*   64: 156 */       while (localEnumeration.hasMoreElements())
/*   65:     */       {
/*   66: 157 */         localObject = (Grammar)localEnumeration.nextElement();
/*   67:     */         
/*   68: 159 */         ((Grammar)localObject).setGrammarAnalyzer(this.analyzer);
/*   69: 160 */         ((Grammar)localObject).setCodeGenerator(this);
/*   70: 161 */         this.analyzer.setGrammar((Grammar)localObject);
/*   71:     */         
/*   72: 163 */         setupGrammarParameters((Grammar)localObject);
/*   73: 164 */         ((Grammar)localObject).generate();
/*   74: 165 */         exitIfError();
/*   75:     */       }
/*   76: 169 */       Object localObject = this.behavior.tokenManagers.elements();
/*   77: 170 */       while (((Enumeration)localObject).hasMoreElements())
/*   78:     */       {
/*   79: 171 */         TokenManager localTokenManager = (TokenManager)((Enumeration)localObject).nextElement();
/*   80: 172 */         if (!localTokenManager.isReadOnly())
/*   81:     */         {
/*   82: 176 */           genTokenTypes(localTokenManager);
/*   83:     */           
/*   84: 178 */           genTokenInterchange(localTokenManager);
/*   85:     */         }
/*   86: 180 */         exitIfError();
/*   87:     */       }
/*   88:     */     }
/*   89:     */     catch (IOException localIOException)
/*   90:     */     {
/*   91: 184 */       this.antlrTool.reportException(localIOException, null);
/*   92:     */     }
/*   93:     */   }
/*   94:     */   
/*   95:     */   public void gen(ActionElement paramActionElement)
/*   96:     */   {
/*   97: 192 */     if (this.DEBUG_CODE_GENERATOR) {
/*   98: 192 */       System.out.println("genAction(" + paramActionElement + ")");
/*   99:     */     }
/*  100: 193 */     if (paramActionElement.isSemPred)
/*  101:     */     {
/*  102: 194 */       genSemPred(paramActionElement.actionText, paramActionElement.line);
/*  103:     */     }
/*  104:     */     else
/*  105:     */     {
/*  106: 197 */       if (this.grammar.hasSyntacticPredicate)
/*  107:     */       {
/*  108: 198 */         println("if (0==inputState.guessing)");
/*  109: 199 */         println("{");
/*  110: 200 */         this.tabs += 1;
/*  111:     */       }
/*  112: 203 */       ActionTransInfo localActionTransInfo = new ActionTransInfo();
/*  113: 204 */       String str = processActionForSpecialSymbols(paramActionElement.actionText, paramActionElement.getLine(), this.currentRule, localActionTransInfo);
/*  114: 208 */       if (localActionTransInfo.refRuleRoot != null) {
/*  115: 213 */         println(localActionTransInfo.refRuleRoot + " = (" + this.labeledElementASTType + ")currentAST.root;");
/*  116:     */       }
/*  117: 217 */       printAction(str);
/*  118: 219 */       if (localActionTransInfo.assignToRoot)
/*  119:     */       {
/*  120: 221 */         println("currentAST.root = " + localActionTransInfo.refRuleRoot + ";");
/*  121:     */         
/*  122: 223 */         println("if ( (null != " + localActionTransInfo.refRuleRoot + ") && (null != " + localActionTransInfo.refRuleRoot + ".getFirstChild()) )");
/*  123: 224 */         this.tabs += 1;
/*  124: 225 */         println("currentAST.child = " + localActionTransInfo.refRuleRoot + ".getFirstChild();");
/*  125: 226 */         this.tabs -= 1;
/*  126: 227 */         println("else");
/*  127: 228 */         this.tabs += 1;
/*  128: 229 */         println("currentAST.child = " + localActionTransInfo.refRuleRoot + ";");
/*  129: 230 */         this.tabs -= 1;
/*  130: 231 */         println("currentAST.advanceChildToEnd();");
/*  131:     */       }
/*  132: 234 */       if (this.grammar.hasSyntacticPredicate)
/*  133:     */       {
/*  134: 235 */         this.tabs -= 1;
/*  135: 236 */         println("}");
/*  136:     */       }
/*  137:     */     }
/*  138:     */   }
/*  139:     */   
/*  140:     */   public void gen(AlternativeBlock paramAlternativeBlock)
/*  141:     */   {
/*  142: 245 */     if (this.DEBUG_CODE_GENERATOR) {
/*  143: 245 */       System.out.println("gen(" + paramAlternativeBlock + ")");
/*  144:     */     }
/*  145: 246 */     println("{");
/*  146: 247 */     this.tabs += 1;
/*  147:     */     
/*  148: 249 */     genBlockPreamble(paramAlternativeBlock);
/*  149: 250 */     genBlockInitAction(paramAlternativeBlock);
/*  150:     */     
/*  151:     */ 
/*  152: 253 */     String str = this.currentASTResult;
/*  153: 254 */     if (paramAlternativeBlock.getLabel() != null) {
/*  154: 255 */       this.currentASTResult = paramAlternativeBlock.getLabel();
/*  155:     */     }
/*  156: 258 */     boolean bool = this.grammar.theLLkAnalyzer.deterministic(paramAlternativeBlock);
/*  157:     */     
/*  158: 260 */     CSharpBlockFinishingInfo localCSharpBlockFinishingInfo = genCommonBlock(paramAlternativeBlock, true);
/*  159: 261 */     genBlockFinish(localCSharpBlockFinishingInfo, this.throwNoViable);
/*  160:     */     
/*  161: 263 */     this.tabs -= 1;
/*  162: 264 */     println("}");
/*  163:     */     
/*  164:     */ 
/*  165: 267 */     this.currentASTResult = str;
/*  166:     */   }
/*  167:     */   
/*  168:     */   public void gen(BlockEndElement paramBlockEndElement)
/*  169:     */   {
/*  170: 275 */     if (this.DEBUG_CODE_GENERATOR) {
/*  171: 275 */       System.out.println("genRuleEnd(" + paramBlockEndElement + ")");
/*  172:     */     }
/*  173:     */   }
/*  174:     */   
/*  175:     */   public void gen(CharLiteralElement paramCharLiteralElement)
/*  176:     */   {
/*  177: 281 */     if (this.DEBUG_CODE_GENERATOR) {
/*  178: 281 */       System.out.println("genChar(" + paramCharLiteralElement + ")");
/*  179:     */     }
/*  180: 283 */     if (paramCharLiteralElement.getLabel() != null) {
/*  181: 284 */       println(paramCharLiteralElement.getLabel() + " = " + this.lt1Value + ";");
/*  182:     */     }
/*  183: 287 */     boolean bool = this.saveText;
/*  184: 288 */     this.saveText = ((this.saveText) && (paramCharLiteralElement.getAutoGenType() == 1));
/*  185: 289 */     genMatch(paramCharLiteralElement);
/*  186: 290 */     this.saveText = bool;
/*  187:     */   }
/*  188:     */   
/*  189:     */   public void gen(CharRangeElement paramCharRangeElement)
/*  190:     */   {
/*  191: 296 */     if ((paramCharRangeElement.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/*  192: 297 */       println(paramCharRangeElement.getLabel() + " = " + this.lt1Value + ";");
/*  193:     */     }
/*  194: 299 */     int i = ((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramCharRangeElement.getAutoGenType() == 3)) ? 1 : 0;
/*  195: 301 */     if (i != 0) {
/*  196: 302 */       println("_saveIndex = text.Length;");
/*  197:     */     }
/*  198: 304 */     println("matchRange(" + OctalToUnicode(paramCharRangeElement.beginText) + "," + OctalToUnicode(paramCharRangeElement.endText) + ");");
/*  199: 306 */     if (i != 0) {
/*  200: 307 */       println("text.Length = _saveIndex;");
/*  201:     */     }
/*  202:     */   }
/*  203:     */   
/*  204:     */   public void gen(LexerGrammar paramLexerGrammar)
/*  205:     */     throws IOException
/*  206:     */   {
/*  207: 312 */     if (paramLexerGrammar.debuggingOutput) {
/*  208: 313 */       this.semPreds = new antlr.collections.impl.Vector();
/*  209:     */     }
/*  210: 315 */     setGrammar(paramLexerGrammar);
/*  211: 316 */     if (!(this.grammar instanceof LexerGrammar)) {
/*  212: 317 */       this.antlrTool.panic("Internal error generating lexer");
/*  213:     */     }
/*  214: 319 */     genBody(paramLexerGrammar);
/*  215:     */   }
/*  216:     */   
/*  217:     */   public void gen(OneOrMoreBlock paramOneOrMoreBlock)
/*  218:     */   {
/*  219: 325 */     if (this.DEBUG_CODE_GENERATOR) {
/*  220: 325 */       System.out.println("gen+(" + paramOneOrMoreBlock + ")");
/*  221:     */     }
/*  222: 328 */     println("{ // ( ... )+");
/*  223: 329 */     this.tabs += 1;
/*  224: 330 */     this.blockNestingLevel += 1;
/*  225: 331 */     genBlockPreamble(paramOneOrMoreBlock);
/*  226:     */     String str2;
/*  227: 332 */     if (paramOneOrMoreBlock.getLabel() != null) {
/*  228: 333 */       str2 = "_cnt_" + paramOneOrMoreBlock.getLabel();
/*  229:     */     } else {
/*  230: 336 */       str2 = "_cnt" + paramOneOrMoreBlock.ID;
/*  231:     */     }
/*  232: 338 */     println("int " + str2 + "=0;");
/*  233:     */     String str1;
/*  234: 339 */     if (paramOneOrMoreBlock.getLabel() != null) {
/*  235: 340 */       str1 = paramOneOrMoreBlock.getLabel();
/*  236:     */     } else {
/*  237: 343 */       str1 = "_loop" + paramOneOrMoreBlock.ID;
/*  238:     */     }
/*  239: 346 */     println("for (;;)");
/*  240: 347 */     println("{");
/*  241: 348 */     this.tabs += 1;
/*  242: 349 */     this.blockNestingLevel += 1;
/*  243:     */     
/*  244:     */ 
/*  245: 352 */     genBlockInitAction(paramOneOrMoreBlock);
/*  246:     */     
/*  247:     */ 
/*  248: 355 */     String str3 = this.currentASTResult;
/*  249: 356 */     if (paramOneOrMoreBlock.getLabel() != null) {
/*  250: 357 */       this.currentASTResult = paramOneOrMoreBlock.getLabel();
/*  251:     */     }
/*  252: 360 */     boolean bool = this.grammar.theLLkAnalyzer.deterministic(paramOneOrMoreBlock);
/*  253:     */     
/*  254:     */ 
/*  255:     */ 
/*  256:     */ 
/*  257:     */ 
/*  258:     */ 
/*  259:     */ 
/*  260:     */ 
/*  261:     */ 
/*  262:     */ 
/*  263:     */ 
/*  264: 372 */     int i = 0;
/*  265: 373 */     int j = this.grammar.maxk;
/*  266: 375 */     if ((!paramOneOrMoreBlock.greedy) && (paramOneOrMoreBlock.exitLookaheadDepth <= this.grammar.maxk) && (paramOneOrMoreBlock.exitCache[paramOneOrMoreBlock.exitLookaheadDepth].containsEpsilon()))
/*  267:     */     {
/*  268: 379 */       i = 1;
/*  269: 380 */       j = paramOneOrMoreBlock.exitLookaheadDepth;
/*  270:     */     }
/*  271: 382 */     else if ((!paramOneOrMoreBlock.greedy) && (paramOneOrMoreBlock.exitLookaheadDepth == 2147483647))
/*  272:     */     {
/*  273: 385 */       i = 1;
/*  274:     */     }
/*  275: 390 */     if (i != 0)
/*  276:     */     {
/*  277: 391 */       if (this.DEBUG_CODE_GENERATOR) {
/*  278: 392 */         System.out.println("nongreedy (...)+ loop; exit depth is " + paramOneOrMoreBlock.exitLookaheadDepth);
/*  279:     */       }
/*  280: 395 */       localObject = getLookaheadTestExpression(paramOneOrMoreBlock.exitCache, j);
/*  281:     */       
/*  282:     */ 
/*  283: 398 */       println("// nongreedy exit test");
/*  284: 399 */       println("if ((" + str2 + " >= 1) && " + (String)localObject + ") goto " + str1 + "_breakloop;");
/*  285:     */     }
/*  286: 402 */     Object localObject = genCommonBlock(paramOneOrMoreBlock, false);
/*  287: 403 */     genBlockFinish((CSharpBlockFinishingInfo)localObject, "if (" + str2 + " >= 1) { goto " + str1 + "_breakloop; } else { " + this.throwNoViable + "; }");
/*  288:     */     
/*  289:     */ 
/*  290:     */ 
/*  291:     */ 
/*  292: 408 */     println(str2 + "++;");
/*  293: 409 */     this.tabs -= 1;
/*  294: 410 */     if (this.blockNestingLevel-- == this.saveIndexCreateLevel) {
/*  295: 411 */       this.saveIndexCreateLevel = 0;
/*  296:     */     }
/*  297: 412 */     println("}");
/*  298: 413 */     _print(str1 + "_breakloop:");
/*  299: 414 */     println(";");
/*  300: 415 */     this.tabs -= 1;
/*  301: 416 */     if (this.blockNestingLevel-- == this.saveIndexCreateLevel) {
/*  302: 417 */       this.saveIndexCreateLevel = 0;
/*  303:     */     }
/*  304: 418 */     println("}    // ( ... )+");
/*  305:     */     
/*  306:     */ 
/*  307: 421 */     this.currentASTResult = str3;
/*  308:     */   }
/*  309:     */   
/*  310:     */   public void gen(ParserGrammar paramParserGrammar)
/*  311:     */     throws IOException
/*  312:     */   {
/*  313: 428 */     if (paramParserGrammar.debuggingOutput) {
/*  314: 429 */       this.semPreds = new antlr.collections.impl.Vector();
/*  315:     */     }
/*  316: 431 */     setGrammar(paramParserGrammar);
/*  317: 432 */     if (!(this.grammar instanceof ParserGrammar)) {
/*  318: 433 */       this.antlrTool.panic("Internal error generating parser");
/*  319:     */     }
/*  320: 435 */     genBody(paramParserGrammar);
/*  321:     */   }
/*  322:     */   
/*  323:     */   public void gen(RuleRefElement paramRuleRefElement)
/*  324:     */   {
/*  325: 442 */     if (this.DEBUG_CODE_GENERATOR) {
/*  326: 442 */       System.out.println("genRR(" + paramRuleRefElement + ")");
/*  327:     */     }
/*  328: 443 */     RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(paramRuleRefElement.targetRule);
/*  329: 444 */     if ((localRuleSymbol == null) || (!localRuleSymbol.isDefined()))
/*  330:     */     {
/*  331: 447 */       this.antlrTool.error("Rule '" + paramRuleRefElement.targetRule + "' is not defined", this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/*  332: 448 */       return;
/*  333:     */     }
/*  334: 450 */     if (!(localRuleSymbol instanceof RuleSymbol))
/*  335:     */     {
/*  336: 453 */       this.antlrTool.error("'" + paramRuleRefElement.targetRule + "' does not name a grammar rule", this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/*  337: 454 */       return;
/*  338:     */     }
/*  339: 457 */     genErrorTryForElement(paramRuleRefElement);
/*  340: 461 */     if (((this.grammar instanceof TreeWalkerGrammar)) && (paramRuleRefElement.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/*  341: 465 */       println(paramRuleRefElement.getLabel() + " = _t==ASTNULL ? null : " + this.lt1Value + ";");
/*  342:     */     }
/*  343: 469 */     if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramRuleRefElement.getAutoGenType() == 3)))
/*  344:     */     {
/*  345: 471 */       declareSaveIndexVariableIfNeeded();
/*  346: 472 */       println("_saveIndex = text.Length;");
/*  347:     */     }
/*  348: 476 */     printTabs();
/*  349: 477 */     if (paramRuleRefElement.idAssign != null)
/*  350:     */     {
/*  351: 480 */       if (localRuleSymbol.block.returnAction == null) {
/*  352: 482 */         this.antlrTool.warning("Rule '" + paramRuleRefElement.targetRule + "' has no return type", this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/*  353:     */       }
/*  354: 484 */       _print(paramRuleRefElement.idAssign + "=");
/*  355:     */     }
/*  356: 487 */     else if ((!(this.grammar instanceof LexerGrammar)) && (this.syntacticPredLevel == 0) && (localRuleSymbol.block.returnAction != null))
/*  357:     */     {
/*  358: 489 */       this.antlrTool.warning("Rule '" + paramRuleRefElement.targetRule + "' returns a value", this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/*  359:     */     }
/*  360: 494 */     GenRuleInvocation(paramRuleRefElement);
/*  361: 497 */     if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramRuleRefElement.getAutoGenType() == 3)))
/*  362:     */     {
/*  363: 498 */       declareSaveIndexVariableIfNeeded();
/*  364: 499 */       println("text.Length = _saveIndex;");
/*  365:     */     }
/*  366: 503 */     if (this.syntacticPredLevel == 0)
/*  367:     */     {
/*  368: 505 */       int i = (this.grammar.hasSyntacticPredicate) && (((this.grammar.buildAST) && (paramRuleRefElement.getLabel() != null)) || ((this.genAST) && (paramRuleRefElement.getAutoGenType() == 1))) ? 1 : 0;
/*  369: 512 */       if (i != 0)
/*  370:     */       {
/*  371: 513 */         println("if (0 == inputState.guessing)");
/*  372: 514 */         println("{");
/*  373: 515 */         this.tabs += 1;
/*  374:     */       }
/*  375: 518 */       if ((this.grammar.buildAST) && (paramRuleRefElement.getLabel() != null)) {
/*  376: 521 */         println(paramRuleRefElement.getLabel() + "_AST = (" + this.labeledElementASTType + ")returnAST;");
/*  377:     */       }
/*  378: 523 */       if (this.genAST) {
/*  379: 525 */         switch (paramRuleRefElement.getAutoGenType())
/*  380:     */         {
/*  381:     */         case 1: 
/*  382: 528 */           if (this.usingCustomAST) {
/*  383: 529 */             println("astFactory.addASTChild(ref currentAST, (AST)returnAST);");
/*  384:     */           } else {
/*  385: 531 */             println("astFactory.addASTChild(ref currentAST, returnAST);");
/*  386:     */           }
/*  387: 532 */           break;
/*  388:     */         case 2: 
/*  389: 534 */           this.antlrTool.error("Internal: encountered ^ after rule reference");
/*  390: 535 */           break;
/*  391:     */         }
/*  392:     */       }
/*  393: 542 */       if (((this.grammar instanceof LexerGrammar)) && (paramRuleRefElement.getLabel() != null)) {
/*  394: 544 */         println(paramRuleRefElement.getLabel() + " = returnToken_;");
/*  395:     */       }
/*  396: 547 */       if (i != 0)
/*  397:     */       {
/*  398: 549 */         this.tabs -= 1;
/*  399: 550 */         println("}");
/*  400:     */       }
/*  401:     */     }
/*  402: 553 */     genErrorCatchForElement(paramRuleRefElement);
/*  403:     */   }
/*  404:     */   
/*  405:     */   public void gen(StringLiteralElement paramStringLiteralElement)
/*  406:     */   {
/*  407: 559 */     if (this.DEBUG_CODE_GENERATOR) {
/*  408: 559 */       System.out.println("genString(" + paramStringLiteralElement + ")");
/*  409:     */     }
/*  410: 562 */     if ((paramStringLiteralElement.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/*  411: 563 */       println(paramStringLiteralElement.getLabel() + " = " + this.lt1Value + ";");
/*  412:     */     }
/*  413: 567 */     genElementAST(paramStringLiteralElement);
/*  414:     */     
/*  415:     */ 
/*  416: 570 */     boolean bool = this.saveText;
/*  417: 571 */     this.saveText = ((this.saveText) && (paramStringLiteralElement.getAutoGenType() == 1));
/*  418:     */     
/*  419:     */ 
/*  420: 574 */     genMatch(paramStringLiteralElement);
/*  421:     */     
/*  422: 576 */     this.saveText = bool;
/*  423: 579 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/*  424: 580 */       println("_t = _t.getNextSibling();");
/*  425:     */     }
/*  426:     */   }
/*  427:     */   
/*  428:     */   public void gen(TokenRangeElement paramTokenRangeElement)
/*  429:     */   {
/*  430: 588 */     genErrorTryForElement(paramTokenRangeElement);
/*  431: 589 */     if ((paramTokenRangeElement.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/*  432: 590 */       println(paramTokenRangeElement.getLabel() + " = " + this.lt1Value + ";");
/*  433:     */     }
/*  434: 594 */     genElementAST(paramTokenRangeElement);
/*  435:     */     
/*  436:     */ 
/*  437: 597 */     println("matchRange(" + OctalToUnicode(paramTokenRangeElement.beginText) + "," + OctalToUnicode(paramTokenRangeElement.endText) + ");");
/*  438: 598 */     genErrorCatchForElement(paramTokenRangeElement);
/*  439:     */   }
/*  440:     */   
/*  441:     */   public void gen(TokenRefElement paramTokenRefElement)
/*  442:     */   {
/*  443: 605 */     if (this.DEBUG_CODE_GENERATOR) {
/*  444: 605 */       System.out.println("genTokenRef(" + paramTokenRefElement + ")");
/*  445:     */     }
/*  446: 606 */     if ((this.grammar instanceof LexerGrammar)) {
/*  447: 607 */       this.antlrTool.panic("Token reference found in lexer");
/*  448:     */     }
/*  449: 609 */     genErrorTryForElement(paramTokenRefElement);
/*  450: 611 */     if ((paramTokenRefElement.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/*  451: 612 */       println(paramTokenRefElement.getLabel() + " = " + this.lt1Value + ";");
/*  452:     */     }
/*  453: 616 */     genElementAST(paramTokenRefElement);
/*  454:     */     
/*  455: 618 */     genMatch(paramTokenRefElement);
/*  456: 619 */     genErrorCatchForElement(paramTokenRefElement);
/*  457: 622 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/*  458: 623 */       println("_t = _t.getNextSibling();");
/*  459:     */     }
/*  460:     */   }
/*  461:     */   
/*  462:     */   public void gen(TreeElement paramTreeElement)
/*  463:     */   {
/*  464: 629 */     println("AST __t" + paramTreeElement.ID + " = _t;");
/*  465: 632 */     if (paramTreeElement.root.getLabel() != null) {
/*  466: 633 */       println(paramTreeElement.root.getLabel() + " = (ASTNULL == _t) ? null : (" + this.labeledElementASTType + ")_t;");
/*  467:     */     }
/*  468: 637 */     if (paramTreeElement.root.getAutoGenType() == 3)
/*  469:     */     {
/*  470: 638 */       this.antlrTool.error("Suffixing a root node with '!' is not implemented", this.grammar.getFilename(), paramTreeElement.getLine(), paramTreeElement.getColumn());
/*  471:     */       
/*  472: 640 */       paramTreeElement.root.setAutoGenType(1);
/*  473:     */     }
/*  474: 642 */     if (paramTreeElement.root.getAutoGenType() == 2)
/*  475:     */     {
/*  476: 643 */       this.antlrTool.warning("Suffixing a root node with '^' is redundant; already a root", this.grammar.getFilename(), paramTreeElement.getLine(), paramTreeElement.getColumn());
/*  477:     */       
/*  478: 645 */       paramTreeElement.root.setAutoGenType(1);
/*  479:     */     }
/*  480: 649 */     genElementAST(paramTreeElement.root);
/*  481: 650 */     if (this.grammar.buildAST)
/*  482:     */     {
/*  483: 652 */       println("ASTPair __currentAST" + paramTreeElement.ID + " = currentAST.copy();");
/*  484:     */       
/*  485: 654 */       println("currentAST.root = currentAST.child;");
/*  486: 655 */       println("currentAST.child = null;");
/*  487:     */     }
/*  488: 659 */     if ((paramTreeElement.root instanceof WildcardElement)) {
/*  489: 660 */       println("if (null == _t) throw new MismatchedTokenException();");
/*  490:     */     } else {
/*  491: 663 */       genMatch(paramTreeElement.root);
/*  492:     */     }
/*  493: 666 */     println("_t = _t.getFirstChild();");
/*  494: 669 */     for (int i = 0; i < paramTreeElement.getAlternatives().size(); i++)
/*  495:     */     {
/*  496: 670 */       Alternative localAlternative = paramTreeElement.getAlternativeAt(i);
/*  497: 671 */       AlternativeElement localAlternativeElement = localAlternative.head;
/*  498: 672 */       while (localAlternativeElement != null)
/*  499:     */       {
/*  500: 673 */         localAlternativeElement.generate();
/*  501: 674 */         localAlternativeElement = localAlternativeElement.next;
/*  502:     */       }
/*  503:     */     }
/*  504: 678 */     if (this.grammar.buildAST) {
/*  505: 681 */       println("currentAST = __currentAST" + paramTreeElement.ID + ";");
/*  506:     */     }
/*  507: 684 */     println("_t = __t" + paramTreeElement.ID + ";");
/*  508:     */     
/*  509: 686 */     println("_t = _t.getNextSibling();");
/*  510:     */   }
/*  511:     */   
/*  512:     */   public void gen(TreeWalkerGrammar paramTreeWalkerGrammar)
/*  513:     */     throws IOException
/*  514:     */   {
/*  515: 691 */     setGrammar(paramTreeWalkerGrammar);
/*  516: 692 */     if (!(this.grammar instanceof TreeWalkerGrammar)) {
/*  517: 693 */       this.antlrTool.panic("Internal error generating tree-walker");
/*  518:     */     }
/*  519: 695 */     genBody(paramTreeWalkerGrammar);
/*  520:     */   }
/*  521:     */   
/*  522:     */   public void gen(WildcardElement paramWildcardElement)
/*  523:     */   {
/*  524: 703 */     if ((paramWildcardElement.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/*  525: 704 */       println(paramWildcardElement.getLabel() + " = " + this.lt1Value + ";");
/*  526:     */     }
/*  527: 708 */     genElementAST(paramWildcardElement);
/*  528: 710 */     if ((this.grammar instanceof TreeWalkerGrammar))
/*  529:     */     {
/*  530: 711 */       println("if (null == _t) throw new MismatchedTokenException();");
/*  531:     */     }
/*  532: 713 */     else if ((this.grammar instanceof LexerGrammar))
/*  533:     */     {
/*  534: 714 */       if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramWildcardElement.getAutoGenType() == 3)))
/*  535:     */       {
/*  536: 716 */         declareSaveIndexVariableIfNeeded();
/*  537: 717 */         println("_saveIndex = text.Length;");
/*  538:     */       }
/*  539: 719 */       println("matchNot(EOF/*_CHAR*/);");
/*  540: 720 */       if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramWildcardElement.getAutoGenType() == 3)))
/*  541:     */       {
/*  542: 722 */         declareSaveIndexVariableIfNeeded();
/*  543: 723 */         println("text.Length = _saveIndex;");
/*  544:     */       }
/*  545:     */     }
/*  546:     */     else
/*  547:     */     {
/*  548: 727 */       println("matchNot(" + getValueString(1) + ");");
/*  549:     */     }
/*  550: 731 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/*  551: 732 */       println("_t = _t.getNextSibling();");
/*  552:     */     }
/*  553:     */   }
/*  554:     */   
/*  555:     */   public void gen(ZeroOrMoreBlock paramZeroOrMoreBlock)
/*  556:     */   {
/*  557: 740 */     if (this.DEBUG_CODE_GENERATOR) {
/*  558: 740 */       System.out.println("gen*(" + paramZeroOrMoreBlock + ")");
/*  559:     */     }
/*  560: 741 */     println("{    // ( ... )*");
/*  561: 742 */     this.tabs += 1;
/*  562: 743 */     this.blockNestingLevel += 1;
/*  563: 744 */     genBlockPreamble(paramZeroOrMoreBlock);
/*  564:     */     String str1;
/*  565: 746 */     if (paramZeroOrMoreBlock.getLabel() != null) {
/*  566: 747 */       str1 = paramZeroOrMoreBlock.getLabel();
/*  567:     */     } else {
/*  568: 750 */       str1 = "_loop" + paramZeroOrMoreBlock.ID;
/*  569:     */     }
/*  570: 752 */     println("for (;;)");
/*  571: 753 */     println("{");
/*  572: 754 */     this.tabs += 1;
/*  573: 755 */     this.blockNestingLevel += 1;
/*  574:     */     
/*  575:     */ 
/*  576: 758 */     genBlockInitAction(paramZeroOrMoreBlock);
/*  577:     */     
/*  578:     */ 
/*  579: 761 */     String str2 = this.currentASTResult;
/*  580: 762 */     if (paramZeroOrMoreBlock.getLabel() != null) {
/*  581: 763 */       this.currentASTResult = paramZeroOrMoreBlock.getLabel();
/*  582:     */     }
/*  583: 766 */     boolean bool = this.grammar.theLLkAnalyzer.deterministic(paramZeroOrMoreBlock);
/*  584:     */     
/*  585:     */ 
/*  586:     */ 
/*  587:     */ 
/*  588:     */ 
/*  589:     */ 
/*  590:     */ 
/*  591:     */ 
/*  592:     */ 
/*  593:     */ 
/*  594:     */ 
/*  595: 778 */     int i = 0;
/*  596: 779 */     int j = this.grammar.maxk;
/*  597: 781 */     if ((!paramZeroOrMoreBlock.greedy) && (paramZeroOrMoreBlock.exitLookaheadDepth <= this.grammar.maxk) && (paramZeroOrMoreBlock.exitCache[paramZeroOrMoreBlock.exitLookaheadDepth].containsEpsilon()))
/*  598:     */     {
/*  599: 785 */       i = 1;
/*  600: 786 */       j = paramZeroOrMoreBlock.exitLookaheadDepth;
/*  601:     */     }
/*  602: 788 */     else if ((!paramZeroOrMoreBlock.greedy) && (paramZeroOrMoreBlock.exitLookaheadDepth == 2147483647))
/*  603:     */     {
/*  604: 791 */       i = 1;
/*  605:     */     }
/*  606: 793 */     if (i != 0)
/*  607:     */     {
/*  608: 794 */       if (this.DEBUG_CODE_GENERATOR) {
/*  609: 795 */         System.out.println("nongreedy (...)* loop; exit depth is " + paramZeroOrMoreBlock.exitLookaheadDepth);
/*  610:     */       }
/*  611: 798 */       localObject = getLookaheadTestExpression(paramZeroOrMoreBlock.exitCache, j);
/*  612:     */       
/*  613:     */ 
/*  614: 801 */       println("// nongreedy exit test");
/*  615: 802 */       println("if (" + (String)localObject + ") goto " + str1 + "_breakloop;");
/*  616:     */     }
/*  617: 805 */     Object localObject = genCommonBlock(paramZeroOrMoreBlock, false);
/*  618: 806 */     genBlockFinish((CSharpBlockFinishingInfo)localObject, "goto " + str1 + "_breakloop;");
/*  619:     */     
/*  620: 808 */     this.tabs -= 1;
/*  621: 809 */     if (this.blockNestingLevel-- == this.saveIndexCreateLevel) {
/*  622: 810 */       this.saveIndexCreateLevel = 0;
/*  623:     */     }
/*  624: 811 */     println("}");
/*  625: 812 */     _print(str1 + "_breakloop:");
/*  626: 813 */     println(";");
/*  627: 814 */     this.tabs -= 1;
/*  628: 815 */     if (this.blockNestingLevel-- == this.saveIndexCreateLevel) {
/*  629: 816 */       this.saveIndexCreateLevel = 0;
/*  630:     */     }
/*  631: 817 */     println("}    // ( ... )*");
/*  632:     */     
/*  633:     */ 
/*  634: 820 */     this.currentASTResult = str2;
/*  635:     */   }
/*  636:     */   
/*  637:     */   protected void genAlt(Alternative paramAlternative, AlternativeBlock paramAlternativeBlock)
/*  638:     */   {
/*  639: 830 */     boolean bool1 = this.genAST;
/*  640: 831 */     this.genAST = ((this.genAST) && (paramAlternative.getAutoGen()));
/*  641:     */     
/*  642: 833 */     boolean bool2 = this.saveText;
/*  643: 834 */     this.saveText = ((this.saveText) && (paramAlternative.getAutoGen()));
/*  644:     */     
/*  645:     */ 
/*  646: 837 */     Hashtable localHashtable = this.treeVariableMap;
/*  647: 838 */     this.treeVariableMap = new Hashtable();
/*  648: 841 */     if (paramAlternative.exceptionSpec != null)
/*  649:     */     {
/*  650: 842 */       println("try        // for error handling");
/*  651: 843 */       println("{");
/*  652: 844 */       this.tabs += 1;
/*  653:     */     }
/*  654: 847 */     AlternativeElement localAlternativeElement = paramAlternative.head;
/*  655: 848 */     while (!(localAlternativeElement instanceof BlockEndElement))
/*  656:     */     {
/*  657: 849 */       localAlternativeElement.generate();
/*  658: 850 */       localAlternativeElement = localAlternativeElement.next;
/*  659:     */     }
/*  660: 853 */     if (this.genAST) {
/*  661: 855 */       if ((paramAlternativeBlock instanceof RuleBlock))
/*  662:     */       {
/*  663: 858 */         RuleBlock localRuleBlock = (RuleBlock)paramAlternativeBlock;
/*  664: 859 */         if (this.usingCustomAST) {
/*  665: 861 */           println(localRuleBlock.getRuleName() + "_AST = (" + this.labeledElementASTType + ")currentAST.root;");
/*  666:     */         } else {
/*  667: 865 */           println(localRuleBlock.getRuleName() + "_AST = currentAST.root;");
/*  668:     */         }
/*  669:     */       }
/*  670: 868 */       else if (paramAlternativeBlock.getLabel() != null)
/*  671:     */       {
/*  672: 871 */         this.antlrTool.warning("Labeled subrules not yet supported", this.grammar.getFilename(), paramAlternativeBlock.getLine(), paramAlternativeBlock.getColumn());
/*  673:     */       }
/*  674:     */     }
/*  675: 875 */     if (paramAlternative.exceptionSpec != null)
/*  676:     */     {
/*  677: 878 */       this.tabs -= 1;
/*  678: 879 */       println("}");
/*  679: 880 */       genErrorHandler(paramAlternative.exceptionSpec);
/*  680:     */     }
/*  681: 883 */     this.genAST = bool1;
/*  682: 884 */     this.saveText = bool2;
/*  683:     */     
/*  684: 886 */     this.treeVariableMap = localHashtable;
/*  685:     */   }
/*  686:     */   
/*  687:     */   protected void genBitsets(antlr.collections.impl.Vector paramVector, int paramInt)
/*  688:     */   {
/*  689: 900 */     println("");
/*  690: 901 */     for (int i = 0; i < paramVector.size(); i++)
/*  691:     */     {
/*  692: 903 */       BitSet localBitSet = (BitSet)paramVector.elementAt(i);
/*  693:     */       
/*  694: 905 */       localBitSet.growToInclude(paramInt);
/*  695: 906 */       genBitSet(localBitSet, i);
/*  696:     */     }
/*  697:     */   }
/*  698:     */   
/*  699:     */   private void genBitSet(BitSet paramBitSet, int paramInt)
/*  700:     */   {
/*  701: 922 */     println("private static long[] mk_" + getBitsetName(paramInt) + "()");
/*  702: 923 */     println("{");
/*  703: 924 */     this.tabs += 1;
/*  704: 925 */     int i = paramBitSet.lengthInLongWords();
/*  705:     */     long[] arrayOfLong;
/*  706:     */     int j;
/*  707: 926 */     if (i < 8)
/*  708:     */     {
/*  709: 927 */       println("long[] data = { " + paramBitSet.toStringOfWords() + "};");
/*  710:     */     }
/*  711:     */     else
/*  712:     */     {
/*  713: 931 */       println("long[] data = new long[" + i + "];");
/*  714: 932 */       arrayOfLong = paramBitSet.toPackedArray();
/*  715: 933 */       for (j = 0; j < arrayOfLong.length;) {
/*  716: 934 */         if ((j + 1 == arrayOfLong.length) || (arrayOfLong[j] != arrayOfLong[(j + 1)]))
/*  717:     */         {
/*  718: 936 */           println("data[" + j + "]=" + arrayOfLong[j] + "L;");
/*  719: 937 */           j++;
/*  720:     */         }
/*  721:     */         else
/*  722:     */         {
/*  723: 943 */           for (int k = j + 1; (k < arrayOfLong.length) && (arrayOfLong[k] == arrayOfLong[j]); k++) {}
/*  724: 948 */           println("for (int i = " + j + "; i<=" + (k - 1) + "; i++) { data[i]=" + arrayOfLong[j] + "L; }");
/*  725:     */           
/*  726: 950 */           j = k;
/*  727:     */         }
/*  728:     */       }
/*  729:     */     }
/*  730: 955 */     println("return data;");
/*  731: 956 */     this.tabs -= 1;
/*  732: 957 */     println("}");
/*  733:     */     
/*  734: 959 */     println("public static readonly BitSet " + getBitsetName(paramInt) + " = new BitSet(" + "mk_" + getBitsetName(paramInt) + "()" + ");");
/*  735:     */   }
/*  736:     */   
/*  737:     */   protected String getBitsetName(int paramInt)
/*  738:     */   {
/*  739: 969 */     return "tokenSet_" + paramInt + "_";
/*  740:     */   }
/*  741:     */   
/*  742:     */   private void genBlockFinish(CSharpBlockFinishingInfo paramCSharpBlockFinishingInfo, String paramString)
/*  743:     */   {
/*  744: 981 */     if ((paramCSharpBlockFinishingInfo.needAnErrorClause) && ((paramCSharpBlockFinishingInfo.generatedAnIf) || (paramCSharpBlockFinishingInfo.generatedSwitch)))
/*  745:     */     {
/*  746: 984 */       if (paramCSharpBlockFinishingInfo.generatedAnIf)
/*  747:     */       {
/*  748: 985 */         println("else");
/*  749: 986 */         println("{");
/*  750:     */       }
/*  751:     */       else
/*  752:     */       {
/*  753: 989 */         println("{");
/*  754:     */       }
/*  755: 991 */       this.tabs += 1;
/*  756: 992 */       println(paramString);
/*  757: 993 */       this.tabs -= 1;
/*  758: 994 */       println("}");
/*  759:     */     }
/*  760: 997 */     if (paramCSharpBlockFinishingInfo.postscript != null) {
/*  761: 998 */       if ((paramCSharpBlockFinishingInfo.needAnErrorClause) && (paramCSharpBlockFinishingInfo.generatedSwitch) && (!paramCSharpBlockFinishingInfo.generatedAnIf) && (paramString != null))
/*  762:     */       {
/*  763:1002 */         if ((paramString.indexOf("throw") == 0) || (paramString.indexOf("goto") == 0))
/*  764:     */         {
/*  765:1004 */           int i = paramCSharpBlockFinishingInfo.postscript.indexOf("break;") + 6;
/*  766:1005 */           String str = paramCSharpBlockFinishingInfo.postscript.substring(i);
/*  767:1006 */           println(str);
/*  768:     */         }
/*  769:     */         else
/*  770:     */         {
/*  771:1009 */           println(paramCSharpBlockFinishingInfo.postscript);
/*  772:     */         }
/*  773:     */       }
/*  774:     */       else {
/*  775:1013 */         println(paramCSharpBlockFinishingInfo.postscript);
/*  776:     */       }
/*  777:     */     }
/*  778:     */   }
/*  779:     */   
/*  780:     */   protected void genBlockInitAction(AlternativeBlock paramAlternativeBlock)
/*  781:     */   {
/*  782:1025 */     if (paramAlternativeBlock.initAction != null) {
/*  783:1026 */       printAction(processActionForSpecialSymbols(paramAlternativeBlock.initAction, paramAlternativeBlock.getLine(), this.currentRule, null));
/*  784:     */     }
/*  785:     */   }
/*  786:     */   
/*  787:     */   protected void genBlockPreamble(AlternativeBlock paramAlternativeBlock)
/*  788:     */   {
/*  789:1037 */     if ((paramAlternativeBlock instanceof RuleBlock))
/*  790:     */     {
/*  791:1038 */       RuleBlock localRuleBlock = (RuleBlock)paramAlternativeBlock;
/*  792:1039 */       if (localRuleBlock.labeledElements != null) {
/*  793:1040 */         for (int i = 0; i < localRuleBlock.labeledElements.size(); i++)
/*  794:     */         {
/*  795:1042 */           AlternativeElement localAlternativeElement = (AlternativeElement)localRuleBlock.labeledElements.elementAt(i);
/*  796:1049 */           if (((localAlternativeElement instanceof RuleRefElement)) || (((localAlternativeElement instanceof AlternativeBlock)) && (!(localAlternativeElement instanceof RuleBlock)) && (!(localAlternativeElement instanceof SynPredBlock))))
/*  797:     */           {
/*  798:1056 */             if ((!(localAlternativeElement instanceof RuleRefElement)) && (((AlternativeBlock)localAlternativeElement).not) && (this.analyzer.subruleCanBeInverted((AlternativeBlock)localAlternativeElement, this.grammar instanceof LexerGrammar)))
/*  799:     */             {
/*  800:1064 */               println(this.labeledElementType + " " + localAlternativeElement.getLabel() + " = " + this.labeledElementInit + ";");
/*  801:1065 */               if (this.grammar.buildAST) {
/*  802:1066 */                 genASTDeclaration(localAlternativeElement);
/*  803:     */               }
/*  804:     */             }
/*  805:     */             else
/*  806:     */             {
/*  807:1070 */               if (this.grammar.buildAST) {
/*  808:1074 */                 genASTDeclaration(localAlternativeElement);
/*  809:     */               }
/*  810:1076 */               if ((this.grammar instanceof LexerGrammar)) {
/*  811:1077 */                 println("IToken " + localAlternativeElement.getLabel() + " = null;");
/*  812:     */               }
/*  813:1079 */               if ((this.grammar instanceof TreeWalkerGrammar)) {
/*  814:1082 */                 println(this.labeledElementType + " " + localAlternativeElement.getLabel() + " = " + this.labeledElementInit + ";");
/*  815:     */               }
/*  816:     */             }
/*  817:     */           }
/*  818:     */           else
/*  819:     */           {
/*  820:1089 */             println(this.labeledElementType + " " + localAlternativeElement.getLabel() + " = " + this.labeledElementInit + ";");
/*  821:1091 */             if (this.grammar.buildAST) {
/*  822:1093 */               if (((localAlternativeElement instanceof GrammarAtom)) && (((GrammarAtom)localAlternativeElement).getASTNodeType() != null))
/*  823:     */               {
/*  824:1095 */                 GrammarAtom localGrammarAtom = (GrammarAtom)localAlternativeElement;
/*  825:1096 */                 genASTDeclaration(localAlternativeElement, localGrammarAtom.getASTNodeType());
/*  826:     */               }
/*  827:     */               else
/*  828:     */               {
/*  829:1099 */                 genASTDeclaration(localAlternativeElement);
/*  830:     */               }
/*  831:     */             }
/*  832:     */           }
/*  833:     */         }
/*  834:     */       }
/*  835:     */     }
/*  836:     */   }
/*  837:     */   
/*  838:     */   public void genBody(LexerGrammar paramLexerGrammar)
/*  839:     */     throws IOException
/*  840:     */   {
/*  841:1112 */     setupOutput(this.grammar.getClassName());
/*  842:     */     
/*  843:1114 */     this.genAST = false;
/*  844:1115 */     this.saveText = true;
/*  845:     */     
/*  846:1117 */     this.tabs = 0;
/*  847:     */     
/*  848:     */ 
/*  849:1120 */     genHeader();
/*  850:     */     
/*  851:1122 */     println(this.behavior.getHeaderAction(""));
/*  852:1125 */     if (nameSpace != null) {
/*  853:1126 */       nameSpace.emitDeclarations(this.currentOutput);
/*  854:     */     }
/*  855:1127 */     this.tabs += 1;
/*  856:     */     
/*  857:     */ 
/*  858:1130 */     println("// Generate header specific to lexer CSharp file");
/*  859:1131 */     println("using System;");
/*  860:1132 */     println("using Stream                          = System.IO.Stream;");
/*  861:1133 */     println("using TextReader                      = System.IO.TextReader;");
/*  862:1134 */     println("using Hashtable                       = System.Collections.Hashtable;");
/*  863:1135 */     println("using Comparer                        = System.Collections.Comparer;");
/*  864:1136 */     if (!paramLexerGrammar.caseSensitiveLiterals)
/*  865:     */     {
/*  866:1138 */       println("using CaseInsensitiveHashCodeProvider = System.Collections.CaseInsensitiveHashCodeProvider;");
/*  867:1139 */       println("using CaseInsensitiveComparer         = System.Collections.CaseInsensitiveComparer;");
/*  868:     */     }
/*  869:1141 */     println("");
/*  870:1142 */     println("using TokenStreamException            = antlr.TokenStreamException;");
/*  871:1143 */     println("using TokenStreamIOException          = antlr.TokenStreamIOException;");
/*  872:1144 */     println("using TokenStreamRecognitionException = antlr.TokenStreamRecognitionException;");
/*  873:1145 */     println("using CharStreamException             = antlr.CharStreamException;");
/*  874:1146 */     println("using CharStreamIOException           = antlr.CharStreamIOException;");
/*  875:1147 */     println("using ANTLRException                  = antlr.ANTLRException;");
/*  876:1148 */     println("using CharScanner                     = antlr.CharScanner;");
/*  877:1149 */     println("using InputBuffer                     = antlr.InputBuffer;");
/*  878:1150 */     println("using ByteBuffer                      = antlr.ByteBuffer;");
/*  879:1151 */     println("using CharBuffer                      = antlr.CharBuffer;");
/*  880:1152 */     println("using Token                           = antlr.Token;");
/*  881:1153 */     println("using IToken                          = antlr.IToken;");
/*  882:1154 */     println("using CommonToken                     = antlr.CommonToken;");
/*  883:1155 */     println("using SemanticException               = antlr.SemanticException;");
/*  884:1156 */     println("using RecognitionException            = antlr.RecognitionException;");
/*  885:1157 */     println("using NoViableAltForCharException     = antlr.NoViableAltForCharException;");
/*  886:1158 */     println("using MismatchedCharException         = antlr.MismatchedCharException;");
/*  887:1159 */     println("using TokenStream                     = antlr.TokenStream;");
/*  888:1160 */     println("using LexerSharedInputState           = antlr.LexerSharedInputState;");
/*  889:1161 */     println("using BitSet                          = antlr.collections.impl.BitSet;");
/*  890:     */     
/*  891:     */ 
/*  892:1164 */     println(this.grammar.preambleAction.getText());
/*  893:     */     
/*  894:     */ 
/*  895:1167 */     String str = null;
/*  896:1168 */     if (this.grammar.superClass != null) {
/*  897:1169 */       str = this.grammar.superClass;
/*  898:     */     } else {
/*  899:1172 */       str = "antlr." + this.grammar.getSuperClass();
/*  900:     */     }
/*  901:1176 */     if (this.grammar.comment != null) {
/*  902:1178 */       _println(this.grammar.comment);
/*  903:     */     }
/*  904:1181 */     Token localToken = (Token)this.grammar.options.get("classHeaderPrefix");
/*  905:1182 */     if (localToken == null)
/*  906:     */     {
/*  907:1183 */       print("public ");
/*  908:     */     }
/*  909:     */     else
/*  910:     */     {
/*  911:1186 */       localObject1 = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/*  912:1187 */       if (localObject1 == null) {
/*  913:1188 */         print("public ");
/*  914:     */       } else {
/*  915:1191 */         print((String)localObject1 + " ");
/*  916:     */       }
/*  917:     */     }
/*  918:1195 */     print("class " + this.grammar.getClassName() + " : " + str);
/*  919:1196 */     println(", TokenStream");
/*  920:1197 */     Object localObject1 = (Token)this.grammar.options.get("classHeaderSuffix");
/*  921:1198 */     if (localObject1 != null)
/*  922:     */     {
/*  923:1200 */       localObject2 = StringUtils.stripFrontBack(((Token)localObject1).getText(), "\"", "\"");
/*  924:1201 */       if (localObject2 != null) {
/*  925:1203 */         print(", " + (String)localObject2);
/*  926:     */       }
/*  927:     */     }
/*  928:1206 */     println(" {");
/*  929:1207 */     this.tabs += 1;
/*  930:     */     
/*  931:     */ 
/*  932:1210 */     genTokenDefinitions(this.grammar.tokenManager);
/*  933:     */     
/*  934:     */ 
/*  935:1213 */     print(processActionForSpecialSymbols(this.grammar.classMemberAction.getText(), this.grammar.classMemberAction.getLine(), this.currentRule, null));
/*  936:     */     
/*  937:     */ 
/*  938:     */ 
/*  939:     */ 
/*  940:     */ 
/*  941:     */ 
/*  942:     */ 
/*  943:1221 */     println("public " + this.grammar.getClassName() + "(Stream ins) : this(new ByteBuffer(ins))");
/*  944:1222 */     println("{");
/*  945:1223 */     println("}");
/*  946:1224 */     println("");
/*  947:     */     
/*  948:     */ 
/*  949:     */ 
/*  950:     */ 
/*  951:     */ 
/*  952:1230 */     println("public " + this.grammar.getClassName() + "(TextReader r) : this(new CharBuffer(r))");
/*  953:1231 */     println("{");
/*  954:1232 */     println("}");
/*  955:1233 */     println("");
/*  956:     */     
/*  957:1235 */     print("public " + this.grammar.getClassName() + "(InputBuffer ib)");
/*  958:1237 */     if (this.grammar.debuggingOutput) {
/*  959:1238 */       println(" : this(new LexerSharedInputState(new antlr.debug.DebuggingInputBuffer(ib)))");
/*  960:     */     } else {
/*  961:1240 */       println(" : this(new LexerSharedInputState(ib))");
/*  962:     */     }
/*  963:1241 */     println("{");
/*  964:1242 */     println("}");
/*  965:1243 */     println("");
/*  966:     */     
/*  967:     */ 
/*  968:     */ 
/*  969:     */ 
/*  970:1248 */     println("public " + this.grammar.getClassName() + "(LexerSharedInputState state) : base(state)");
/*  971:1249 */     println("{");
/*  972:1250 */     this.tabs += 1;
/*  973:1251 */     println("initialize();");
/*  974:1252 */     this.tabs -= 1;
/*  975:1253 */     println("}");
/*  976:     */     
/*  977:     */ 
/*  978:1256 */     println("private void initialize()");
/*  979:1257 */     println("{");
/*  980:1258 */     this.tabs += 1;
/*  981:1262 */     if (this.grammar.debuggingOutput)
/*  982:     */     {
/*  983:1263 */       println("ruleNames  = _ruleNames;");
/*  984:1264 */       println("semPredNames = _semPredNames;");
/*  985:1265 */       println("setupDebugging();");
/*  986:     */     }
/*  987:1271 */     println("caseSensitiveLiterals = " + paramLexerGrammar.caseSensitiveLiterals + ";");
/*  988:1272 */     println("setCaseSensitive(" + paramLexerGrammar.caseSensitive + ");");
/*  989:1277 */     if (paramLexerGrammar.caseSensitiveLiterals) {
/*  990:1278 */       println("literals = new Hashtable(100, (float) 0.4, null, Comparer.Default);");
/*  991:     */     } else {
/*  992:1280 */       println("literals = new Hashtable(100, (float) 0.4, CaseInsensitiveHashCodeProvider.Default, CaseInsensitiveComparer.Default);");
/*  993:     */     }
/*  994:1281 */     Object localObject2 = this.grammar.tokenManager.getTokenSymbolKeys();
/*  995:     */     Object localObject4;
/*  996:1282 */     while (((Enumeration)localObject2).hasMoreElements())
/*  997:     */     {
/*  998:1283 */       localObject3 = (String)((Enumeration)localObject2).nextElement();
/*  999:1284 */       if (((String)localObject3).charAt(0) == '"')
/* 1000:     */       {
/* 1001:1287 */         TokenSymbol localTokenSymbol = this.grammar.tokenManager.getTokenSymbol((String)localObject3);
/* 1002:1288 */         if ((localTokenSymbol instanceof StringLiteralSymbol))
/* 1003:     */         {
/* 1004:1289 */           localObject4 = (StringLiteralSymbol)localTokenSymbol;
/* 1005:1290 */           println("literals.Add(" + ((StringLiteralSymbol)localObject4).getId() + ", " + ((StringLiteralSymbol)localObject4).getTokenType() + ");");
/* 1006:     */         }
/* 1007:     */       }
/* 1008:     */     }
/* 1009:1295 */     this.tabs -= 1;
/* 1010:1296 */     println("}");
/* 1011:1299 */     if (this.grammar.debuggingOutput)
/* 1012:     */     {
/* 1013:1300 */       println("private static readonly string[] _ruleNames = new string[] {");
/* 1014:     */       
/* 1015:1302 */       localObject3 = this.grammar.rules.elements();
/* 1016:1303 */       i = 0;
/* 1017:1304 */       while (((Enumeration)localObject3).hasMoreElements())
/* 1018:     */       {
/* 1019:1305 */         localObject4 = (GrammarSymbol)((Enumeration)localObject3).nextElement();
/* 1020:1306 */         if ((localObject4 instanceof RuleSymbol)) {
/* 1021:1307 */           println("  \"" + ((RuleSymbol)localObject4).getId() + "\",");
/* 1022:     */         }
/* 1023:     */       }
/* 1024:1309 */       println("};");
/* 1025:     */     }
/* 1026:1315 */     genNextToken();
/* 1027:     */     
/* 1028:     */ 
/* 1029:1318 */     Object localObject3 = this.grammar.rules.elements();
/* 1030:1319 */     int i = 0;
/* 1031:1320 */     while (((Enumeration)localObject3).hasMoreElements())
/* 1032:     */     {
/* 1033:1321 */       localObject4 = (RuleSymbol)((Enumeration)localObject3).nextElement();
/* 1034:1323 */       if (!((RuleSymbol)localObject4).getId().equals("mnextToken")) {
/* 1035:1324 */         genRule((RuleSymbol)localObject4, false, i++, this.grammar.tokenManager);
/* 1036:     */       }
/* 1037:1326 */       exitIfError();
/* 1038:     */     }
/* 1039:1330 */     if (this.grammar.debuggingOutput) {
/* 1040:1331 */       genSemPredMap();
/* 1041:     */     }
/* 1042:1334 */     genBitsets(this.bitsetsUsed, ((LexerGrammar)this.grammar).charVocabulary.size());
/* 1043:     */     
/* 1044:1336 */     println("");
/* 1045:1337 */     this.tabs -= 1;
/* 1046:1338 */     println("}");
/* 1047:     */     
/* 1048:1340 */     this.tabs -= 1;
/* 1049:1342 */     if (nameSpace != null) {
/* 1050:1343 */       nameSpace.emitClosures(this.currentOutput);
/* 1051:     */     }
/* 1052:1346 */     this.currentOutput.close();
/* 1053:1347 */     this.currentOutput = null;
/* 1054:     */   }
/* 1055:     */   
/* 1056:     */   public void genInitFactory(Grammar paramGrammar)
/* 1057:     */   {
/* 1058:1351 */     if (paramGrammar.buildAST)
/* 1059:     */     {
/* 1060:1355 */       println("static public void initializeASTFactory( ASTFactory factory )");
/* 1061:1356 */       println("{");
/* 1062:1357 */       this.tabs += 1;
/* 1063:     */       
/* 1064:1359 */       println("factory.setMaxNodeType(" + paramGrammar.tokenManager.maxTokenType() + ");");
/* 1065:     */       
/* 1066:     */ 
/* 1067:     */ 
/* 1068:1363 */       antlr.collections.impl.Vector localVector = paramGrammar.tokenManager.getVocabulary();
/* 1069:1364 */       for (int i = 0; i < localVector.size(); i++)
/* 1070:     */       {
/* 1071:1365 */         String str = (String)localVector.elementAt(i);
/* 1072:1366 */         if (str != null)
/* 1073:     */         {
/* 1074:1367 */           TokenSymbol localTokenSymbol = paramGrammar.tokenManager.getTokenSymbol(str);
/* 1075:1368 */           if ((localTokenSymbol != null) && (localTokenSymbol.getASTNodeType() != null)) {
/* 1076:1369 */             println("factory.setTokenTypeASTNodeType(" + str + ", \"" + localTokenSymbol.getASTNodeType() + "\");");
/* 1077:     */           }
/* 1078:     */         }
/* 1079:     */       }
/* 1080:1374 */       this.tabs -= 1;
/* 1081:1375 */       println("}");
/* 1082:     */     }
/* 1083:     */   }
/* 1084:     */   
/* 1085:     */   public void genBody(ParserGrammar paramParserGrammar)
/* 1086:     */     throws IOException
/* 1087:     */   {
/* 1088:1383 */     setupOutput(this.grammar.getClassName());
/* 1089:     */     
/* 1090:1385 */     this.genAST = this.grammar.buildAST;
/* 1091:     */     
/* 1092:1387 */     this.tabs = 0;
/* 1093:     */     
/* 1094:     */ 
/* 1095:1390 */     genHeader();
/* 1096:     */     
/* 1097:1392 */     println(this.behavior.getHeaderAction(""));
/* 1098:1395 */     if (nameSpace != null) {
/* 1099:1396 */       nameSpace.emitDeclarations(this.currentOutput);
/* 1100:     */     }
/* 1101:1397 */     this.tabs += 1;
/* 1102:     */     
/* 1103:     */ 
/* 1104:1400 */     println("// Generate the header common to all output files.");
/* 1105:1401 */     println("using System;");
/* 1106:1402 */     println("");
/* 1107:1403 */     println("using TokenBuffer              = antlr.TokenBuffer;");
/* 1108:1404 */     println("using TokenStreamException     = antlr.TokenStreamException;");
/* 1109:1405 */     println("using TokenStreamIOException   = antlr.TokenStreamIOException;");
/* 1110:1406 */     println("using ANTLRException           = antlr.ANTLRException;");
/* 1111:     */     
/* 1112:1408 */     String str1 = this.grammar.getSuperClass();
/* 1113:1409 */     String[] arrayOfString = split(str1, ".");
/* 1114:1410 */     println("using " + arrayOfString[(arrayOfString.length - 1)] + " = antlr." + str1 + ";");
/* 1115:     */     
/* 1116:1412 */     println("using Token                    = antlr.Token;");
/* 1117:1413 */     println("using IToken                   = antlr.IToken;");
/* 1118:1414 */     println("using TokenStream              = antlr.TokenStream;");
/* 1119:1415 */     println("using RecognitionException     = antlr.RecognitionException;");
/* 1120:1416 */     println("using NoViableAltException     = antlr.NoViableAltException;");
/* 1121:1417 */     println("using MismatchedTokenException = antlr.MismatchedTokenException;");
/* 1122:1418 */     println("using SemanticException        = antlr.SemanticException;");
/* 1123:1419 */     println("using ParserSharedInputState   = antlr.ParserSharedInputState;");
/* 1124:1420 */     println("using BitSet                   = antlr.collections.impl.BitSet;");
/* 1125:1421 */     if (this.genAST)
/* 1126:     */     {
/* 1127:1422 */       println("using AST                      = antlr.collections.AST;");
/* 1128:1423 */       println("using ASTPair                  = antlr.ASTPair;");
/* 1129:1424 */       println("using ASTFactory               = antlr.ASTFactory;");
/* 1130:1425 */       println("using ASTArray                 = antlr.collections.impl.ASTArray;");
/* 1131:     */     }
/* 1132:1429 */     println(this.grammar.preambleAction.getText());
/* 1133:     */     
/* 1134:     */ 
/* 1135:1432 */     String str2 = null;
/* 1136:1433 */     if (this.grammar.superClass != null) {
/* 1137:1434 */       str2 = this.grammar.superClass;
/* 1138:     */     } else {
/* 1139:1436 */       str2 = "antlr." + this.grammar.getSuperClass();
/* 1140:     */     }
/* 1141:1439 */     if (this.grammar.comment != null) {
/* 1142:1440 */       _println(this.grammar.comment);
/* 1143:     */     }
/* 1144:1443 */     Token localToken = (Token)this.grammar.options.get("classHeaderPrefix");
/* 1145:1444 */     if (localToken == null)
/* 1146:     */     {
/* 1147:1445 */       print("public ");
/* 1148:     */     }
/* 1149:     */     else
/* 1150:     */     {
/* 1151:1448 */       localObject1 = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/* 1152:1449 */       if (localObject1 == null) {
/* 1153:1450 */         print("public ");
/* 1154:     */       } else {
/* 1155:1453 */         print((String)localObject1 + " ");
/* 1156:     */       }
/* 1157:     */     }
/* 1158:1457 */     println("class " + this.grammar.getClassName() + " : " + str2);
/* 1159:     */     
/* 1160:1459 */     Object localObject1 = (Token)this.grammar.options.get("classHeaderSuffix");
/* 1161:1460 */     if (localObject1 != null)
/* 1162:     */     {
/* 1163:1461 */       localObject2 = StringUtils.stripFrontBack(((Token)localObject1).getText(), "\"", "\"");
/* 1164:1462 */       if (localObject2 != null) {
/* 1165:1463 */         print("              , " + (String)localObject2);
/* 1166:     */       }
/* 1167:     */     }
/* 1168:1465 */     println("{");
/* 1169:1466 */     this.tabs += 1;
/* 1170:     */     
/* 1171:     */ 
/* 1172:1469 */     genTokenDefinitions(this.grammar.tokenManager);
/* 1173:     */     GrammarSymbol localGrammarSymbol;
/* 1174:1473 */     if (this.grammar.debuggingOutput)
/* 1175:     */     {
/* 1176:1474 */       println("private static readonly string[] _ruleNames = new string[] {");
/* 1177:1475 */       this.tabs += 1;
/* 1178:     */       
/* 1179:1477 */       localObject2 = this.grammar.rules.elements();
/* 1180:1478 */       i = 0;
/* 1181:1479 */       while (((Enumeration)localObject2).hasMoreElements())
/* 1182:     */       {
/* 1183:1480 */         localGrammarSymbol = (GrammarSymbol)((Enumeration)localObject2).nextElement();
/* 1184:1481 */         if ((localGrammarSymbol instanceof RuleSymbol)) {
/* 1185:1482 */           println("  \"" + ((RuleSymbol)localGrammarSymbol).getId() + "\",");
/* 1186:     */         }
/* 1187:     */       }
/* 1188:1484 */       this.tabs -= 1;
/* 1189:1485 */       println("};");
/* 1190:     */     }
/* 1191:1489 */     print(processActionForSpecialSymbols(this.grammar.classMemberAction.getText(), this.grammar.classMemberAction.getLine(), this.currentRule, null));
/* 1192:     */     
/* 1193:     */ 
/* 1194:     */ 
/* 1195:     */ 
/* 1196:1494 */     println("");
/* 1197:1495 */     println("protected void initialize()");
/* 1198:1496 */     println("{");
/* 1199:1497 */     this.tabs += 1;
/* 1200:1498 */     println("tokenNames = tokenNames_;");
/* 1201:1500 */     if (this.grammar.buildAST) {
/* 1202:1501 */       println("initializeFactory();");
/* 1203:     */     }
/* 1204:1505 */     if (this.grammar.debuggingOutput)
/* 1205:     */     {
/* 1206:1506 */       println("ruleNames  = _ruleNames;");
/* 1207:1507 */       println("semPredNames = _semPredNames;");
/* 1208:1508 */       println("setupDebugging(tokenBuf);");
/* 1209:     */     }
/* 1210:1510 */     this.tabs -= 1;
/* 1211:1511 */     println("}");
/* 1212:1512 */     println("");
/* 1213:     */     
/* 1214:1514 */     println("");
/* 1215:1515 */     println("protected " + this.grammar.getClassName() + "(TokenBuffer tokenBuf, int k) : base(tokenBuf, k)");
/* 1216:1516 */     println("{");
/* 1217:1517 */     this.tabs += 1;
/* 1218:1518 */     println("initialize();");
/* 1219:1519 */     this.tabs -= 1;
/* 1220:1520 */     println("}");
/* 1221:1521 */     println("");
/* 1222:     */     
/* 1223:1523 */     println("public " + this.grammar.getClassName() + "(TokenBuffer tokenBuf) : this(tokenBuf," + this.grammar.maxk + ")");
/* 1224:1524 */     println("{");
/* 1225:1525 */     println("}");
/* 1226:1526 */     println("");
/* 1227:     */     
/* 1228:     */ 
/* 1229:1529 */     println("protected " + this.grammar.getClassName() + "(TokenStream lexer, int k) : base(lexer,k)");
/* 1230:1530 */     println("{");
/* 1231:1531 */     this.tabs += 1;
/* 1232:1532 */     println("initialize();");
/* 1233:1533 */     this.tabs -= 1;
/* 1234:1534 */     println("}");
/* 1235:1535 */     println("");
/* 1236:     */     
/* 1237:1537 */     println("public " + this.grammar.getClassName() + "(TokenStream lexer) : this(lexer," + this.grammar.maxk + ")");
/* 1238:1538 */     println("{");
/* 1239:1539 */     println("}");
/* 1240:1540 */     println("");
/* 1241:     */     
/* 1242:1542 */     println("public " + this.grammar.getClassName() + "(ParserSharedInputState state) : base(state," + this.grammar.maxk + ")");
/* 1243:1543 */     println("{");
/* 1244:1544 */     this.tabs += 1;
/* 1245:1545 */     println("initialize();");
/* 1246:1546 */     this.tabs -= 1;
/* 1247:1547 */     println("}");
/* 1248:1548 */     println("");
/* 1249:     */     
/* 1250:1550 */     this.astTypes = new java.util.Vector(100);
/* 1251:     */     
/* 1252:     */ 
/* 1253:1553 */     Object localObject2 = this.grammar.rules.elements();
/* 1254:1554 */     int i = 0;
/* 1255:1555 */     while (((Enumeration)localObject2).hasMoreElements())
/* 1256:     */     {
/* 1257:1556 */       localGrammarSymbol = (GrammarSymbol)((Enumeration)localObject2).nextElement();
/* 1258:1557 */       if ((localGrammarSymbol instanceof RuleSymbol))
/* 1259:     */       {
/* 1260:1558 */         RuleSymbol localRuleSymbol = (RuleSymbol)localGrammarSymbol;
/* 1261:1559 */         genRule(localRuleSymbol, localRuleSymbol.references.size() == 0, i++, this.grammar.tokenManager);
/* 1262:     */       }
/* 1263:1561 */       exitIfError();
/* 1264:     */     }
/* 1265:1563 */     if (this.usingCustomAST)
/* 1266:     */     {
/* 1267:1567 */       println("public new " + this.labeledElementASTType + " getAST()");
/* 1268:1568 */       println("{");
/* 1269:1569 */       this.tabs += 1;
/* 1270:1570 */       println("return (" + this.labeledElementASTType + ") returnAST;");
/* 1271:1571 */       this.tabs -= 1;
/* 1272:1572 */       println("}");
/* 1273:1573 */       println("");
/* 1274:     */     }
/* 1275:1578 */     println("private void initializeFactory()");
/* 1276:1579 */     println("{");
/* 1277:1580 */     this.tabs += 1;
/* 1278:1581 */     if (this.grammar.buildAST)
/* 1279:     */     {
/* 1280:1582 */       println("if (astFactory == null)");
/* 1281:1583 */       println("{");
/* 1282:1584 */       this.tabs += 1;
/* 1283:1585 */       if (this.usingCustomAST) {
/* 1284:1587 */         println("astFactory = new ASTFactory(\"" + this.labeledElementASTType + "\");");
/* 1285:     */       } else {
/* 1286:1590 */         println("astFactory = new ASTFactory();");
/* 1287:     */       }
/* 1288:1591 */       this.tabs -= 1;
/* 1289:1592 */       println("}");
/* 1290:1593 */       println("initializeASTFactory( astFactory );");
/* 1291:     */     }
/* 1292:1595 */     this.tabs -= 1;
/* 1293:1596 */     println("}");
/* 1294:1597 */     genInitFactory(paramParserGrammar);
/* 1295:     */     
/* 1296:     */ 
/* 1297:1600 */     genTokenStrings();
/* 1298:     */     
/* 1299:     */ 
/* 1300:1603 */     genBitsets(this.bitsetsUsed, this.grammar.tokenManager.maxTokenType());
/* 1301:1606 */     if (this.grammar.debuggingOutput) {
/* 1302:1607 */       genSemPredMap();
/* 1303:     */     }
/* 1304:1610 */     println("");
/* 1305:1611 */     this.tabs -= 1;
/* 1306:1612 */     println("}");
/* 1307:     */     
/* 1308:1614 */     this.tabs -= 1;
/* 1309:1616 */     if (nameSpace != null) {
/* 1310:1617 */       nameSpace.emitClosures(this.currentOutput);
/* 1311:     */     }
/* 1312:1620 */     this.currentOutput.close();
/* 1313:1621 */     this.currentOutput = null;
/* 1314:     */   }
/* 1315:     */   
/* 1316:     */   public void genBody(TreeWalkerGrammar paramTreeWalkerGrammar)
/* 1317:     */     throws IOException
/* 1318:     */   {
/* 1319:1628 */     setupOutput(this.grammar.getClassName());
/* 1320:     */     
/* 1321:1630 */     this.genAST = this.grammar.buildAST;
/* 1322:1631 */     this.tabs = 0;
/* 1323:     */     
/* 1324:     */ 
/* 1325:1634 */     genHeader();
/* 1326:     */     
/* 1327:1636 */     println(this.behavior.getHeaderAction(""));
/* 1328:1639 */     if (nameSpace != null) {
/* 1329:1640 */       nameSpace.emitDeclarations(this.currentOutput);
/* 1330:     */     }
/* 1331:1641 */     this.tabs += 1;
/* 1332:     */     
/* 1333:     */ 
/* 1334:1644 */     println("// Generate header specific to the tree-parser CSharp file");
/* 1335:1645 */     println("using System;");
/* 1336:1646 */     println("");
/* 1337:1647 */     println("using " + this.grammar.getSuperClass() + " = antlr." + this.grammar.getSuperClass() + ";");
/* 1338:1648 */     println("using Token                    = antlr.Token;");
/* 1339:1649 */     println("using IToken                   = antlr.IToken;");
/* 1340:1650 */     println("using AST                      = antlr.collections.AST;");
/* 1341:1651 */     println("using RecognitionException     = antlr.RecognitionException;");
/* 1342:1652 */     println("using ANTLRException           = antlr.ANTLRException;");
/* 1343:1653 */     println("using NoViableAltException     = antlr.NoViableAltException;");
/* 1344:1654 */     println("using MismatchedTokenException = antlr.MismatchedTokenException;");
/* 1345:1655 */     println("using SemanticException        = antlr.SemanticException;");
/* 1346:1656 */     println("using BitSet                   = antlr.collections.impl.BitSet;");
/* 1347:1657 */     println("using ASTPair                  = antlr.ASTPair;");
/* 1348:1658 */     println("using ASTFactory               = antlr.ASTFactory;");
/* 1349:1659 */     println("using ASTArray                 = antlr.collections.impl.ASTArray;");
/* 1350:     */     
/* 1351:     */ 
/* 1352:1662 */     println(this.grammar.preambleAction.getText());
/* 1353:     */     
/* 1354:     */ 
/* 1355:1665 */     String str1 = null;
/* 1356:1666 */     if (this.grammar.superClass != null) {
/* 1357:1667 */       str1 = this.grammar.superClass;
/* 1358:     */     } else {
/* 1359:1670 */       str1 = "antlr." + this.grammar.getSuperClass();
/* 1360:     */     }
/* 1361:1672 */     println("");
/* 1362:1675 */     if (this.grammar.comment != null) {
/* 1363:1676 */       _println(this.grammar.comment);
/* 1364:     */     }
/* 1365:1679 */     Token localToken = (Token)this.grammar.options.get("classHeaderPrefix");
/* 1366:1680 */     if (localToken == null)
/* 1367:     */     {
/* 1368:1681 */       print("public ");
/* 1369:     */     }
/* 1370:     */     else
/* 1371:     */     {
/* 1372:1684 */       localObject1 = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/* 1373:1685 */       if (localObject1 == null) {
/* 1374:1686 */         print("public ");
/* 1375:     */       } else {
/* 1376:1689 */         print((String)localObject1 + " ");
/* 1377:     */       }
/* 1378:     */     }
/* 1379:1693 */     println("class " + this.grammar.getClassName() + " : " + str1);
/* 1380:1694 */     Object localObject1 = (Token)this.grammar.options.get("classHeaderSuffix");
/* 1381:1695 */     if (localObject1 != null)
/* 1382:     */     {
/* 1383:1696 */       localObject2 = StringUtils.stripFrontBack(((Token)localObject1).getText(), "\"", "\"");
/* 1384:1697 */       if (localObject2 != null) {
/* 1385:1698 */         print("              , " + (String)localObject2);
/* 1386:     */       }
/* 1387:     */     }
/* 1388:1701 */     println("{");
/* 1389:1702 */     this.tabs += 1;
/* 1390:     */     
/* 1391:     */ 
/* 1392:1705 */     genTokenDefinitions(this.grammar.tokenManager);
/* 1393:     */     
/* 1394:     */ 
/* 1395:1708 */     print(processActionForSpecialSymbols(this.grammar.classMemberAction.getText(), this.grammar.classMemberAction.getLine(), this.currentRule, null));
/* 1396:     */     
/* 1397:     */ 
/* 1398:     */ 
/* 1399:     */ 
/* 1400:1713 */     println("public " + this.grammar.getClassName() + "()");
/* 1401:1714 */     println("{");
/* 1402:1715 */     this.tabs += 1;
/* 1403:1716 */     println("tokenNames = tokenNames_;");
/* 1404:1717 */     this.tabs -= 1;
/* 1405:1718 */     println("}");
/* 1406:1719 */     println("");
/* 1407:     */     
/* 1408:1721 */     this.astTypes = new java.util.Vector();
/* 1409:     */     
/* 1410:1723 */     Object localObject2 = this.grammar.rules.elements();
/* 1411:1724 */     int i = 0;
/* 1412:1725 */     String str2 = "";
/* 1413:1726 */     while (((Enumeration)localObject2).hasMoreElements())
/* 1414:     */     {
/* 1415:1727 */       GrammarSymbol localGrammarSymbol = (GrammarSymbol)((Enumeration)localObject2).nextElement();
/* 1416:1728 */       if ((localGrammarSymbol instanceof RuleSymbol))
/* 1417:     */       {
/* 1418:1729 */         RuleSymbol localRuleSymbol = (RuleSymbol)localGrammarSymbol;
/* 1419:1730 */         genRule(localRuleSymbol, localRuleSymbol.references.size() == 0, i++, this.grammar.tokenManager);
/* 1420:     */       }
/* 1421:1732 */       exitIfError();
/* 1422:     */     }
/* 1423:1735 */     if (this.usingCustomAST)
/* 1424:     */     {
/* 1425:1739 */       println("public new " + this.labeledElementASTType + " getAST()");
/* 1426:1740 */       println("{");
/* 1427:1741 */       this.tabs += 1;
/* 1428:1742 */       println("return (" + this.labeledElementASTType + ") returnAST;");
/* 1429:1743 */       this.tabs -= 1;
/* 1430:1744 */       println("}");
/* 1431:1745 */       println("");
/* 1432:     */     }
/* 1433:1749 */     genInitFactory(this.grammar);
/* 1434:     */     
/* 1435:     */ 
/* 1436:1752 */     genTokenStrings();
/* 1437:     */     
/* 1438:     */ 
/* 1439:1755 */     genBitsets(this.bitsetsUsed, this.grammar.tokenManager.maxTokenType());
/* 1440:     */     
/* 1441:     */ 
/* 1442:1758 */     this.tabs -= 1;
/* 1443:1759 */     println("}");
/* 1444:1760 */     println("");
/* 1445:     */     
/* 1446:1762 */     this.tabs -= 1;
/* 1447:1764 */     if (nameSpace != null) {
/* 1448:1765 */       nameSpace.emitClosures(this.currentOutput);
/* 1449:     */     }
/* 1450:1768 */     this.currentOutput.close();
/* 1451:1769 */     this.currentOutput = null;
/* 1452:     */   }
/* 1453:     */   
/* 1454:     */   protected void genCases(BitSet paramBitSet)
/* 1455:     */   {
/* 1456:1776 */     if (this.DEBUG_CODE_GENERATOR) {
/* 1457:1776 */       System.out.println("genCases(" + paramBitSet + ")");
/* 1458:     */     }
/* 1459:1779 */     int[] arrayOfInt = paramBitSet.toArray();
/* 1460:     */     
/* 1461:1781 */     int i = (this.grammar instanceof LexerGrammar) ? 4 : 1;
/* 1462:1782 */     int j = 1;
/* 1463:1783 */     int k = 1;
/* 1464:1784 */     for (int m = 0; m < arrayOfInt.length; m++)
/* 1465:     */     {
/* 1466:1785 */       if (j == 1) {
/* 1467:1786 */         print("");
/* 1468:     */       } else {
/* 1469:1788 */         _print("  ");
/* 1470:     */       }
/* 1471:1790 */       _print("case " + getValueString(arrayOfInt[m]) + ":");
/* 1472:1791 */       if (j == i)
/* 1473:     */       {
/* 1474:1792 */         _println("");
/* 1475:1793 */         k = 1;
/* 1476:1794 */         j = 1;
/* 1477:     */       }
/* 1478:     */       else
/* 1479:     */       {
/* 1480:1797 */         j++;
/* 1481:1798 */         k = 0;
/* 1482:     */       }
/* 1483:     */     }
/* 1484:1801 */     if (k == 0) {
/* 1485:1802 */       _println("");
/* 1486:     */     }
/* 1487:     */   }
/* 1488:     */   
/* 1489:     */   public CSharpBlockFinishingInfo genCommonBlock(AlternativeBlock paramAlternativeBlock, boolean paramBoolean)
/* 1490:     */   {
/* 1491:1820 */     int i = 0;
/* 1492:1821 */     int j = 0;
/* 1493:1822 */     int k = 0;
/* 1494:1823 */     CSharpBlockFinishingInfo localCSharpBlockFinishingInfo = new CSharpBlockFinishingInfo();
/* 1495:1824 */     if (this.DEBUG_CODE_GENERATOR) {
/* 1496:1824 */       System.out.println("genCommonBlock(" + paramAlternativeBlock + ")");
/* 1497:     */     }
/* 1498:1827 */     boolean bool1 = this.genAST;
/* 1499:1828 */     this.genAST = ((this.genAST) && (paramAlternativeBlock.getAutoGen()));
/* 1500:     */     
/* 1501:1830 */     boolean bool2 = this.saveText;
/* 1502:1831 */     this.saveText = ((this.saveText) && (paramAlternativeBlock.getAutoGen()));
/* 1503:     */     Object localObject1;
/* 1504:1834 */     if ((paramAlternativeBlock.not) && (this.analyzer.subruleCanBeInverted(paramAlternativeBlock, this.grammar instanceof LexerGrammar)))
/* 1505:     */     {
/* 1506:1837 */       if (this.DEBUG_CODE_GENERATOR) {
/* 1507:1837 */         System.out.println("special case: ~(subrule)");
/* 1508:     */       }
/* 1509:1838 */       localObject1 = this.analyzer.look(1, paramAlternativeBlock);
/* 1510:1840 */       if ((paramAlternativeBlock.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/* 1511:1841 */         println(paramAlternativeBlock.getLabel() + " = " + this.lt1Value + ";");
/* 1512:     */       }
/* 1513:1845 */       genElementAST(paramAlternativeBlock);
/* 1514:     */       
/* 1515:1847 */       String str1 = "";
/* 1516:1848 */       if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 1517:1849 */         if (this.usingCustomAST) {
/* 1518:1850 */           str1 = "(AST)_t,";
/* 1519:     */         } else {
/* 1520:1852 */           str1 = "_t,";
/* 1521:     */         }
/* 1522:     */       }
/* 1523:1856 */       println("match(" + str1 + getBitsetName(markBitsetForGen(((Lookahead)localObject1).fset)) + ");");
/* 1524:1859 */       if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 1525:1861 */         println("_t = _t.getNextSibling();");
/* 1526:     */       }
/* 1527:1863 */       return localCSharpBlockFinishingInfo;
/* 1528:     */     }
/* 1529:1867 */     if (paramAlternativeBlock.getAlternatives().size() == 1)
/* 1530:     */     {
/* 1531:1869 */       localObject1 = paramAlternativeBlock.getAlternativeAt(0);
/* 1532:1871 */       if (((Alternative)localObject1).synPred != null) {
/* 1533:1873 */         this.antlrTool.warning("Syntactic predicate superfluous for single alternative", this.grammar.getFilename(), paramAlternativeBlock.getAlternativeAt(0).synPred.getLine(), paramAlternativeBlock.getAlternativeAt(0).synPred.getColumn());
/* 1534:     */       }
/* 1535:1880 */       if (paramBoolean)
/* 1536:     */       {
/* 1537:1882 */         if (((Alternative)localObject1).semPred != null) {
/* 1538:1885 */           genSemPred(((Alternative)localObject1).semPred, paramAlternativeBlock.line);
/* 1539:     */         }
/* 1540:1887 */         genAlt((Alternative)localObject1, paramAlternativeBlock);
/* 1541:1888 */         return localCSharpBlockFinishingInfo;
/* 1542:     */       }
/* 1543:     */     }
/* 1544:1901 */     int m = 0;
/* 1545:1902 */     for (int n = 0; n < paramAlternativeBlock.getAlternatives().size(); n++)
/* 1546:     */     {
/* 1547:1904 */       Alternative localAlternative1 = paramAlternativeBlock.getAlternativeAt(n);
/* 1548:1905 */       if (suitableForCaseExpression(localAlternative1)) {
/* 1549:1906 */         m++;
/* 1550:     */       }
/* 1551:     */     }
/* 1552:     */     Object localObject2;
/* 1553:1911 */     if (m >= this.makeSwitchThreshold)
/* 1554:     */     {
/* 1555:1914 */       String str2 = lookaheadString(1);
/* 1556:1915 */       j = 1;
/* 1557:1917 */       if ((this.grammar instanceof TreeWalkerGrammar))
/* 1558:     */       {
/* 1559:1919 */         println("if (null == _t)");
/* 1560:1920 */         this.tabs += 1;
/* 1561:1921 */         println("_t = ASTNULL;");
/* 1562:1922 */         this.tabs -= 1;
/* 1563:     */       }
/* 1564:1924 */       println("switch ( " + str2 + " )");
/* 1565:1925 */       println("{");
/* 1566:     */       
/* 1567:     */ 
/* 1568:1928 */       this.blockNestingLevel += 1;
/* 1569:1930 */       for (i2 = 0; i2 < paramAlternativeBlock.alternatives.size(); i2++)
/* 1570:     */       {
/* 1571:1932 */         Alternative localAlternative2 = paramAlternativeBlock.getAlternativeAt(i2);
/* 1572:1935 */         if (suitableForCaseExpression(localAlternative2))
/* 1573:     */         {
/* 1574:1939 */           localObject2 = localAlternative2.cache[1];
/* 1575:1940 */           if ((((Lookahead)localObject2).fset.degree() == 0) && (!((Lookahead)localObject2).containsEpsilon()))
/* 1576:     */           {
/* 1577:1942 */             this.antlrTool.warning("Alternate omitted due to empty prediction set", this.grammar.getFilename(), localAlternative2.head.getLine(), localAlternative2.head.getColumn());
/* 1578:     */           }
/* 1579:     */           else
/* 1580:     */           {
/* 1581:1948 */             genCases(((Lookahead)localObject2).fset);
/* 1582:1949 */             println("{");
/* 1583:1950 */             this.tabs += 1;
/* 1584:1951 */             this.blockNestingLevel += 1;
/* 1585:1952 */             genAlt(localAlternative2, paramAlternativeBlock);
/* 1586:1953 */             println("break;");
/* 1587:1954 */             if (this.blockNestingLevel-- == this.saveIndexCreateLevel) {
/* 1588:1955 */               this.saveIndexCreateLevel = 0;
/* 1589:     */             }
/* 1590:1956 */             this.tabs -= 1;
/* 1591:1957 */             println("}");
/* 1592:     */           }
/* 1593:     */         }
/* 1594:     */       }
/* 1595:1960 */       println("default:");
/* 1596:1961 */       this.tabs += 1;
/* 1597:     */     }
/* 1598:1977 */     int i1 = (this.grammar instanceof LexerGrammar) ? this.grammar.maxk : 0;
/* 1599:1978 */     for (int i2 = i1; i2 >= 0; i2--)
/* 1600:     */     {
/* 1601:1979 */       if (this.DEBUG_CODE_GENERATOR) {
/* 1602:1979 */         System.out.println("checking depth " + i2);
/* 1603:     */       }
/* 1604:1980 */       for (i3 = 0; i3 < paramAlternativeBlock.alternatives.size(); i3++)
/* 1605:     */       {
/* 1606:1981 */         localObject2 = paramAlternativeBlock.getAlternativeAt(i3);
/* 1607:1982 */         if (this.DEBUG_CODE_GENERATOR) {
/* 1608:1982 */           System.out.println("genAlt: " + i3);
/* 1609:     */         }
/* 1610:1987 */         if ((j != 0) && (suitableForCaseExpression((Alternative)localObject2)))
/* 1611:     */         {
/* 1612:1989 */           if (this.DEBUG_CODE_GENERATOR) {
/* 1613:1989 */             System.out.println("ignoring alt because it was in the switch");
/* 1614:     */           }
/* 1615:     */         }
/* 1616:     */         else
/* 1617:     */         {
/* 1618:1994 */           boolean bool3 = false;
/* 1619:     */           String str4;
/* 1620:1996 */           if ((this.grammar instanceof LexerGrammar))
/* 1621:     */           {
/* 1622:2000 */             int i4 = ((Alternative)localObject2).lookaheadDepth;
/* 1623:2001 */             if (i4 == 2147483647) {
/* 1624:2004 */               i4 = this.grammar.maxk;
/* 1625:     */             }
/* 1626:2006 */             while ((i4 >= 1) && (localObject2.cache[i4].containsEpsilon())) {
/* 1627:2009 */               i4--;
/* 1628:     */             }
/* 1629:2013 */             if (i4 != i2)
/* 1630:     */             {
/* 1631:2015 */               if (!this.DEBUG_CODE_GENERATOR) {
/* 1632:     */                 continue;
/* 1633:     */               }
/* 1634:2016 */               System.out.println("ignoring alt because effectiveDepth!=altDepth;" + i4 + "!=" + i2); continue;
/* 1635:     */             }
/* 1636:2019 */             bool3 = lookaheadIsEmpty((Alternative)localObject2, i4);
/* 1637:2020 */             str4 = getLookaheadTestExpression((Alternative)localObject2, i4);
/* 1638:     */           }
/* 1639:     */           else
/* 1640:     */           {
/* 1641:2024 */             bool3 = lookaheadIsEmpty((Alternative)localObject2, this.grammar.maxk);
/* 1642:2025 */             str4 = getLookaheadTestExpression((Alternative)localObject2, this.grammar.maxk);
/* 1643:     */           }
/* 1644:2030 */           if ((localObject2.cache[1].fset.degree() > 127) && (suitableForCaseExpression((Alternative)localObject2)))
/* 1645:     */           {
/* 1646:2033 */             if (i == 0)
/* 1647:     */             {
/* 1648:2035 */               println("if " + str4);
/* 1649:2036 */               println("{");
/* 1650:     */             }
/* 1651:     */             else
/* 1652:     */             {
/* 1653:2039 */               println("else if " + str4);
/* 1654:2040 */               println("{");
/* 1655:     */             }
/* 1656:     */           }
/* 1657:2043 */           else if ((bool3) && (((Alternative)localObject2).semPred == null) && (((Alternative)localObject2).synPred == null))
/* 1658:     */           {
/* 1659:2051 */             if (i == 0) {
/* 1660:2052 */               println("{");
/* 1661:     */             } else {
/* 1662:2055 */               println("else {");
/* 1663:     */             }
/* 1664:2057 */             localCSharpBlockFinishingInfo.needAnErrorClause = false;
/* 1665:     */           }
/* 1666:     */           else
/* 1667:     */           {
/* 1668:2063 */             if (((Alternative)localObject2).semPred != null)
/* 1669:     */             {
/* 1670:2067 */               ActionTransInfo localActionTransInfo = new ActionTransInfo();
/* 1671:2068 */               String str5 = processActionForSpecialSymbols(((Alternative)localObject2).semPred, paramAlternativeBlock.line, this.currentRule, localActionTransInfo);
/* 1672:2075 */               if ((((this.grammar instanceof ParserGrammar)) || ((this.grammar instanceof LexerGrammar))) && (this.grammar.debuggingOutput)) {
/* 1673:2077 */                 str4 = "(" + str4 + "&& fireSemanticPredicateEvaluated(antlr.debug.SemanticPredicateEventArgs.PREDICTING," + addSemPred(this.charFormatter.escapeString(str5)) + "," + str5 + "))";
/* 1674:     */               } else {
/* 1675:2081 */                 str4 = "(" + str4 + "&&(" + str5 + "))";
/* 1676:     */               }
/* 1677:     */             }
/* 1678:2086 */             if (i > 0)
/* 1679:     */             {
/* 1680:2087 */               if (((Alternative)localObject2).synPred != null)
/* 1681:     */               {
/* 1682:2088 */                 println("else {");
/* 1683:2089 */                 this.tabs += 1;
/* 1684:2090 */                 this.blockNestingLevel += 1;
/* 1685:2091 */                 genSynPred(((Alternative)localObject2).synPred, str4);
/* 1686:2092 */                 k++;
/* 1687:     */               }
/* 1688:     */               else
/* 1689:     */               {
/* 1690:2095 */                 println("else if " + str4 + " {");
/* 1691:     */               }
/* 1692:     */             }
/* 1693:2099 */             else if (((Alternative)localObject2).synPred != null)
/* 1694:     */             {
/* 1695:2100 */               genSynPred(((Alternative)localObject2).synPred, str4);
/* 1696:     */             }
/* 1697:     */             else
/* 1698:     */             {
/* 1699:2105 */               if ((this.grammar instanceof TreeWalkerGrammar))
/* 1700:     */               {
/* 1701:2106 */                 println("if (_t == null)");
/* 1702:2107 */                 this.tabs += 1;
/* 1703:2108 */                 println("_t = ASTNULL;");
/* 1704:2109 */                 this.tabs -= 1;
/* 1705:     */               }
/* 1706:2111 */               println("if " + str4);
/* 1707:2112 */               println("{");
/* 1708:     */             }
/* 1709:     */           }
/* 1710:2117 */           this.blockNestingLevel += 1;
/* 1711:     */           
/* 1712:2119 */           i++;
/* 1713:2120 */           this.tabs += 1;
/* 1714:2121 */           genAlt((Alternative)localObject2, paramAlternativeBlock);
/* 1715:2122 */           this.tabs -= 1;
/* 1716:2124 */           if (this.blockNestingLevel-- == this.saveIndexCreateLevel) {
/* 1717:2125 */             this.saveIndexCreateLevel = 0;
/* 1718:     */           }
/* 1719:2126 */           println("}");
/* 1720:     */         }
/* 1721:     */       }
/* 1722:     */     }
/* 1723:2130 */     String str3 = "";
/* 1724:2131 */     for (int i3 = 1; i3 <= k; i3++)
/* 1725:     */     {
/* 1726:2132 */       str3 = str3 + "}";
/* 1727:2133 */       if (this.blockNestingLevel-- == this.saveIndexCreateLevel) {
/* 1728:2134 */         this.saveIndexCreateLevel = 0;
/* 1729:     */       }
/* 1730:     */     }
/* 1731:2138 */     this.genAST = bool1;
/* 1732:     */     
/* 1733:     */ 
/* 1734:2141 */     this.saveText = bool2;
/* 1735:2144 */     if (j != 0)
/* 1736:     */     {
/* 1737:2145 */       this.tabs -= 1;
/* 1738:2146 */       localCSharpBlockFinishingInfo.postscript = (str3 + "break; }");
/* 1739:2147 */       if (this.blockNestingLevel-- == this.saveIndexCreateLevel) {
/* 1740:2148 */         this.saveIndexCreateLevel = 0;
/* 1741:     */       }
/* 1742:2149 */       localCSharpBlockFinishingInfo.generatedSwitch = true;
/* 1743:2150 */       localCSharpBlockFinishingInfo.generatedAnIf = (i > 0);
/* 1744:     */     }
/* 1745:     */     else
/* 1746:     */     {
/* 1747:2155 */       localCSharpBlockFinishingInfo.postscript = str3;
/* 1748:2156 */       localCSharpBlockFinishingInfo.generatedSwitch = false;
/* 1749:2157 */       localCSharpBlockFinishingInfo.generatedAnIf = (i > 0);
/* 1750:     */     }
/* 1751:2160 */     return localCSharpBlockFinishingInfo;
/* 1752:     */   }
/* 1753:     */   
/* 1754:     */   private static boolean suitableForCaseExpression(Alternative paramAlternative)
/* 1755:     */   {
/* 1756:2164 */     return (paramAlternative.lookaheadDepth == 1) && (paramAlternative.semPred == null) && (!paramAlternative.cache[1].containsEpsilon()) && (paramAlternative.cache[1].fset.degree() <= 127);
/* 1757:     */   }
/* 1758:     */   
/* 1759:     */   private void genElementAST(AlternativeElement paramAlternativeElement)
/* 1760:     */   {
/* 1761:2174 */     if (((this.grammar instanceof TreeWalkerGrammar)) && (!this.grammar.buildAST))
/* 1762:     */     {
/* 1763:2180 */       if (paramAlternativeElement.getLabel() == null)
/* 1764:     */       {
/* 1765:2182 */         String str1 = this.lt1Value;
/* 1766:     */         
/* 1767:2184 */         String str2 = "tmp" + this.astVarNumber + "_AST";
/* 1768:2185 */         this.astVarNumber += 1;
/* 1769:     */         
/* 1770:2187 */         mapTreeVariable(paramAlternativeElement, str2);
/* 1771:     */         
/* 1772:2189 */         println(this.labeledElementASTType + " " + str2 + "_in = " + str1 + ";");
/* 1773:     */       }
/* 1774:2191 */       return;
/* 1775:     */     }
/* 1776:2194 */     if ((this.grammar.buildAST) && (this.syntacticPredLevel == 0))
/* 1777:     */     {
/* 1778:2196 */       int i = (this.genAST) && ((paramAlternativeElement.getLabel() != null) || (paramAlternativeElement.getAutoGenType() != 3)) ? 1 : 0;
/* 1779:2204 */       if ((paramAlternativeElement.getAutoGenType() != 3) && ((paramAlternativeElement instanceof TokenRefElement))) {
/* 1780:2206 */         i = 1;
/* 1781:     */       }
/* 1782:2208 */       int j = (this.grammar.hasSyntacticPredicate) && (i != 0) ? 1 : 0;
/* 1783:     */       String str3;
/* 1784:     */       String str4;
/* 1785:2214 */       if (paramAlternativeElement.getLabel() != null)
/* 1786:     */       {
/* 1787:2217 */         str3 = paramAlternativeElement.getLabel();
/* 1788:2218 */         str4 = paramAlternativeElement.getLabel();
/* 1789:     */       }
/* 1790:     */       else
/* 1791:     */       {
/* 1792:2223 */         str3 = this.lt1Value;
/* 1793:     */         
/* 1794:2225 */         str4 = "tmp" + this.astVarNumber;
/* 1795:2226 */         this.astVarNumber += 1;
/* 1796:     */       }
/* 1797:2230 */       if (i != 0) {
/* 1798:2233 */         if ((paramAlternativeElement instanceof GrammarAtom))
/* 1799:     */         {
/* 1800:2235 */           localObject = (GrammarAtom)paramAlternativeElement;
/* 1801:2236 */           if (((GrammarAtom)localObject).getASTNodeType() != null) {
/* 1802:2238 */             genASTDeclaration(paramAlternativeElement, str4, ((GrammarAtom)localObject).getASTNodeType());
/* 1803:     */           } else {
/* 1804:2243 */             genASTDeclaration(paramAlternativeElement, str4, this.labeledElementASTType);
/* 1805:     */           }
/* 1806:     */         }
/* 1807:     */         else
/* 1808:     */         {
/* 1809:2249 */           genASTDeclaration(paramAlternativeElement, str4, this.labeledElementASTType);
/* 1810:     */         }
/* 1811:     */       }
/* 1812:2255 */       Object localObject = str4 + "_AST";
/* 1813:     */       
/* 1814:     */ 
/* 1815:2258 */       mapTreeVariable(paramAlternativeElement, (String)localObject);
/* 1816:2259 */       if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 1817:2262 */         println(this.labeledElementASTType + " " + (String)localObject + "_in = null;");
/* 1818:     */       }
/* 1819:2267 */       if ((j == 0) || 
/* 1820:     */       
/* 1821:     */ 
/* 1822:     */ 
/* 1823:     */ 
/* 1824:     */ 
/* 1825:     */ 
/* 1826:     */ 
/* 1827:2275 */         (paramAlternativeElement.getLabel() != null)) {
/* 1828:2277 */         if ((paramAlternativeElement instanceof GrammarAtom)) {
/* 1829:2279 */           println((String)localObject + " = " + getASTCreateString((GrammarAtom)paramAlternativeElement, str3) + ";");
/* 1830:     */         } else {
/* 1831:2283 */           println((String)localObject + " = " + getASTCreateString(str3) + ";");
/* 1832:     */         }
/* 1833:     */       }
/* 1834:2288 */       if ((paramAlternativeElement.getLabel() == null) && (i != 0))
/* 1835:     */       {
/* 1836:2290 */         str3 = this.lt1Value;
/* 1837:2291 */         if ((paramAlternativeElement instanceof GrammarAtom)) {
/* 1838:2293 */           println((String)localObject + " = " + getASTCreateString((GrammarAtom)paramAlternativeElement, str3) + ";");
/* 1839:     */         } else {
/* 1840:2297 */           println((String)localObject + " = " + getASTCreateString(str3) + ";");
/* 1841:     */         }
/* 1842:2300 */         if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 1843:2303 */           println((String)localObject + "_in = " + str3 + ";");
/* 1844:     */         }
/* 1845:     */       }
/* 1846:2307 */       if (this.genAST) {
/* 1847:2309 */         switch (paramAlternativeElement.getAutoGenType())
/* 1848:     */         {
/* 1849:     */         case 1: 
/* 1850:2312 */           if ((this.usingCustomAST) || (((paramAlternativeElement instanceof GrammarAtom)) && (((GrammarAtom)paramAlternativeElement).getASTNodeType() != null))) {
/* 1851:2315 */             println("astFactory.addASTChild(ref currentAST, (AST)" + (String)localObject + ");");
/* 1852:     */           } else {
/* 1853:2317 */             println("astFactory.addASTChild(ref currentAST, " + (String)localObject + ");");
/* 1854:     */           }
/* 1855:2318 */           break;
/* 1856:     */         case 2: 
/* 1857:2320 */           if ((this.usingCustomAST) || (((paramAlternativeElement instanceof GrammarAtom)) && (((GrammarAtom)paramAlternativeElement).getASTNodeType() != null))) {
/* 1858:2323 */             println("astFactory.makeASTRoot(ref currentAST, (AST)" + (String)localObject + ");");
/* 1859:     */           } else {
/* 1860:2325 */             println("astFactory.makeASTRoot(ref currentAST, " + (String)localObject + ");");
/* 1861:     */           }
/* 1862:2326 */           break;
/* 1863:     */         }
/* 1864:     */       }
/* 1865:2331 */       if (j == 0) {}
/* 1866:     */     }
/* 1867:     */   }
/* 1868:     */   
/* 1869:     */   private void genErrorCatchForElement(AlternativeElement paramAlternativeElement)
/* 1870:     */   {
/* 1871:2344 */     if (paramAlternativeElement.getLabel() == null) {
/* 1872:2344 */       return;
/* 1873:     */     }
/* 1874:2345 */     String str = paramAlternativeElement.enclosingRuleName;
/* 1875:2346 */     if ((this.grammar instanceof LexerGrammar)) {
/* 1876:2347 */       str = CodeGenerator.encodeLexerRuleName(paramAlternativeElement.enclosingRuleName);
/* 1877:     */     }
/* 1878:2349 */     RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(str);
/* 1879:2350 */     if (localRuleSymbol == null) {
/* 1880:2351 */       this.antlrTool.panic("Enclosing rule not found!");
/* 1881:     */     }
/* 1882:2353 */     ExceptionSpec localExceptionSpec = localRuleSymbol.block.findExceptionSpec(paramAlternativeElement.getLabel());
/* 1883:2354 */     if (localExceptionSpec != null)
/* 1884:     */     {
/* 1885:2355 */       this.tabs -= 1;
/* 1886:2356 */       println("}");
/* 1887:2357 */       genErrorHandler(localExceptionSpec);
/* 1888:     */     }
/* 1889:     */   }
/* 1890:     */   
/* 1891:     */   private void genErrorHandler(ExceptionSpec paramExceptionSpec)
/* 1892:     */   {
/* 1893:2365 */     for (int i = 0; i < paramExceptionSpec.handlers.size(); i++)
/* 1894:     */     {
/* 1895:2367 */       ExceptionHandler localExceptionHandler = (ExceptionHandler)paramExceptionSpec.handlers.elementAt(i);
/* 1896:     */       
/* 1897:2369 */       println("catch (" + localExceptionHandler.exceptionTypeAndName.getText() + ")");
/* 1898:2370 */       println("{");
/* 1899:2371 */       this.tabs += 1;
/* 1900:2372 */       if (this.grammar.hasSyntacticPredicate)
/* 1901:     */       {
/* 1902:2373 */         println("if (0 == inputState.guessing)");
/* 1903:2374 */         println("{");
/* 1904:2375 */         this.tabs += 1;
/* 1905:     */       }
/* 1906:2379 */       ActionTransInfo localActionTransInfo = new ActionTransInfo();
/* 1907:2380 */       printAction(processActionForSpecialSymbols(localExceptionHandler.action.getText(), localExceptionHandler.action.getLine(), this.currentRule, localActionTransInfo));
/* 1908:2383 */       if (this.grammar.hasSyntacticPredicate)
/* 1909:     */       {
/* 1910:2385 */         this.tabs -= 1;
/* 1911:2386 */         println("}");
/* 1912:2387 */         println("else");
/* 1913:2388 */         println("{");
/* 1914:2389 */         this.tabs += 1;
/* 1915:     */         
/* 1916:     */ 
/* 1917:2392 */         println("throw;");
/* 1918:2393 */         this.tabs -= 1;
/* 1919:2394 */         println("}");
/* 1920:     */       }
/* 1921:2397 */       this.tabs -= 1;
/* 1922:2398 */       println("}");
/* 1923:     */     }
/* 1924:     */   }
/* 1925:     */   
/* 1926:     */   private void genErrorTryForElement(AlternativeElement paramAlternativeElement)
/* 1927:     */   {
/* 1928:2403 */     if (paramAlternativeElement.getLabel() == null) {
/* 1929:2403 */       return;
/* 1930:     */     }
/* 1931:2404 */     String str = paramAlternativeElement.enclosingRuleName;
/* 1932:2405 */     if ((this.grammar instanceof LexerGrammar)) {
/* 1933:2406 */       str = CodeGenerator.encodeLexerRuleName(paramAlternativeElement.enclosingRuleName);
/* 1934:     */     }
/* 1935:2408 */     RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(str);
/* 1936:2409 */     if (localRuleSymbol == null) {
/* 1937:2410 */       this.antlrTool.panic("Enclosing rule not found!");
/* 1938:     */     }
/* 1939:2412 */     ExceptionSpec localExceptionSpec = localRuleSymbol.block.findExceptionSpec(paramAlternativeElement.getLabel());
/* 1940:2413 */     if (localExceptionSpec != null)
/* 1941:     */     {
/* 1942:2414 */       println("try   // for error handling");
/* 1943:2415 */       println("{");
/* 1944:2416 */       this.tabs += 1;
/* 1945:     */     }
/* 1946:     */   }
/* 1947:     */   
/* 1948:     */   protected void genASTDeclaration(AlternativeElement paramAlternativeElement)
/* 1949:     */   {
/* 1950:2422 */     genASTDeclaration(paramAlternativeElement, this.labeledElementASTType);
/* 1951:     */   }
/* 1952:     */   
/* 1953:     */   protected void genASTDeclaration(AlternativeElement paramAlternativeElement, String paramString)
/* 1954:     */   {
/* 1955:2427 */     genASTDeclaration(paramAlternativeElement, paramAlternativeElement.getLabel(), paramString);
/* 1956:     */   }
/* 1957:     */   
/* 1958:     */   protected void genASTDeclaration(AlternativeElement paramAlternativeElement, String paramString1, String paramString2)
/* 1959:     */   {
/* 1960:2433 */     if (this.declaredASTVariables.contains(paramAlternativeElement)) {
/* 1961:2434 */       return;
/* 1962:     */     }
/* 1963:2439 */     println(paramString2 + " " + paramString1 + "_AST = null;");
/* 1964:     */     
/* 1965:     */ 
/* 1966:2442 */     this.declaredASTVariables.put(paramAlternativeElement, paramAlternativeElement);
/* 1967:     */   }
/* 1968:     */   
/* 1969:     */   protected void genHeader()
/* 1970:     */   {
/* 1971:2448 */     println("// $ANTLR " + Tool.version + ": " + "\"" + this.antlrTool.fileMinusPath(this.antlrTool.grammarFile) + "\"" + " -> " + "\"" + this.grammar.getClassName() + ".cs\"$");
/* 1972:     */   }
/* 1973:     */   
/* 1974:     */   private void genLiteralsTest()
/* 1975:     */   {
/* 1976:2455 */     println("_ttype = testLiteralsTable(_ttype);");
/* 1977:     */   }
/* 1978:     */   
/* 1979:     */   private void genLiteralsTestForPartialToken()
/* 1980:     */   {
/* 1981:2459 */     println("_ttype = testLiteralsTable(text.ToString(_begin, text.Length-_begin), _ttype);");
/* 1982:     */   }
/* 1983:     */   
/* 1984:     */   protected void genMatch(BitSet paramBitSet) {}
/* 1985:     */   
/* 1986:     */   protected void genMatch(GrammarAtom paramGrammarAtom)
/* 1987:     */   {
/* 1988:2466 */     if ((paramGrammarAtom instanceof StringLiteralElement))
/* 1989:     */     {
/* 1990:2467 */       if ((this.grammar instanceof LexerGrammar)) {
/* 1991:2468 */         genMatchUsingAtomText(paramGrammarAtom);
/* 1992:     */       } else {
/* 1993:2471 */         genMatchUsingAtomTokenType(paramGrammarAtom);
/* 1994:     */       }
/* 1995:     */     }
/* 1996:2474 */     else if ((paramGrammarAtom instanceof CharLiteralElement))
/* 1997:     */     {
/* 1998:2475 */       if ((this.grammar instanceof LexerGrammar)) {
/* 1999:2476 */         genMatchUsingAtomText(paramGrammarAtom);
/* 2000:     */       } else {
/* 2001:2479 */         this.antlrTool.error("cannot ref character literals in grammar: " + paramGrammarAtom);
/* 2002:     */       }
/* 2003:     */     }
/* 2004:2482 */     else if ((paramGrammarAtom instanceof TokenRefElement)) {
/* 2005:2483 */       genMatchUsingAtomText(paramGrammarAtom);
/* 2006:2484 */     } else if ((paramGrammarAtom instanceof WildcardElement)) {
/* 2007:2485 */       gen((WildcardElement)paramGrammarAtom);
/* 2008:     */     }
/* 2009:     */   }
/* 2010:     */   
/* 2011:     */   protected void genMatchUsingAtomText(GrammarAtom paramGrammarAtom)
/* 2012:     */   {
/* 2013:2490 */     String str = "";
/* 2014:2491 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2015:2492 */       if (this.usingCustomAST) {
/* 2016:2493 */         str = "(AST)_t,";
/* 2017:     */       } else {
/* 2018:2495 */         str = "_t,";
/* 2019:     */       }
/* 2020:     */     }
/* 2021:2499 */     if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramGrammarAtom.getAutoGenType() == 3)))
/* 2022:     */     {
/* 2023:2500 */       declareSaveIndexVariableIfNeeded();
/* 2024:2501 */       println("_saveIndex = text.Length;");
/* 2025:     */     }
/* 2026:2504 */     print(paramGrammarAtom.not ? "matchNot(" : "match(");
/* 2027:2505 */     _print(str);
/* 2028:2508 */     if (paramGrammarAtom.atomText.equals("EOF")) {
/* 2029:2510 */       _print("Token.EOF_TYPE");
/* 2030:     */     } else {
/* 2031:2513 */       _print(paramGrammarAtom.atomText);
/* 2032:     */     }
/* 2033:2515 */     _println(");");
/* 2034:2517 */     if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramGrammarAtom.getAutoGenType() == 3)))
/* 2035:     */     {
/* 2036:2518 */       declareSaveIndexVariableIfNeeded();
/* 2037:2519 */       println("text.Length = _saveIndex;");
/* 2038:     */     }
/* 2039:     */   }
/* 2040:     */   
/* 2041:     */   protected void genMatchUsingAtomTokenType(GrammarAtom paramGrammarAtom)
/* 2042:     */   {
/* 2043:2525 */     String str1 = "";
/* 2044:2526 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2045:2527 */       if (this.usingCustomAST) {
/* 2046:2528 */         str1 = "(AST)_t,";
/* 2047:     */       } else {
/* 2048:2530 */         str1 = "_t,";
/* 2049:     */       }
/* 2050:     */     }
/* 2051:2534 */     Object localObject = null;
/* 2052:2535 */     String str2 = str1 + getValueString(paramGrammarAtom.getType());
/* 2053:     */     
/* 2054:     */ 
/* 2055:2538 */     println((paramGrammarAtom.not ? "matchNot(" : "match(") + str2 + ");");
/* 2056:     */   }
/* 2057:     */   
/* 2058:     */   public void genNextToken()
/* 2059:     */   {
/* 2060:2548 */     int i = 0;
/* 2061:2549 */     for (int j = 0; j < this.grammar.rules.size(); j++)
/* 2062:     */     {
/* 2063:2550 */       localRuleSymbol1 = (RuleSymbol)this.grammar.rules.elementAt(j);
/* 2064:2551 */       if ((localRuleSymbol1.isDefined()) && (localRuleSymbol1.access.equals("public")))
/* 2065:     */       {
/* 2066:2552 */         i = 1;
/* 2067:2553 */         break;
/* 2068:     */       }
/* 2069:     */     }
/* 2070:2556 */     if (i == 0)
/* 2071:     */     {
/* 2072:2557 */       println("");
/* 2073:2558 */       println("override public IToken nextToken()\t\t\t//throws TokenStreamException");
/* 2074:2559 */       println("{");
/* 2075:2560 */       this.tabs += 1;
/* 2076:2561 */       println("try");
/* 2077:2562 */       println("{");
/* 2078:2563 */       this.tabs += 1;
/* 2079:2564 */       println("uponEOF();");
/* 2080:2565 */       this.tabs -= 1;
/* 2081:2566 */       println("}");
/* 2082:2567 */       println("catch(CharStreamIOException csioe)");
/* 2083:2568 */       println("{");
/* 2084:2569 */       this.tabs += 1;
/* 2085:2570 */       println("throw new TokenStreamIOException(csioe.io);");
/* 2086:2571 */       this.tabs -= 1;
/* 2087:2572 */       println("}");
/* 2088:2573 */       println("catch(CharStreamException cse)");
/* 2089:2574 */       println("{");
/* 2090:2575 */       this.tabs += 1;
/* 2091:2576 */       println("throw new TokenStreamException(cse.Message);");
/* 2092:2577 */       this.tabs -= 1;
/* 2093:2578 */       println("}");
/* 2094:2579 */       println("return new CommonToken(Token.EOF_TYPE, \"\");");
/* 2095:2580 */       this.tabs -= 1;
/* 2096:2581 */       println("}");
/* 2097:2582 */       println("");
/* 2098:2583 */       return;
/* 2099:     */     }
/* 2100:2587 */     RuleBlock localRuleBlock = MakeGrammar.createNextTokenRule(this.grammar, this.grammar.rules, "nextToken");
/* 2101:     */     
/* 2102:2589 */     RuleSymbol localRuleSymbol1 = new RuleSymbol("mnextToken");
/* 2103:2590 */     localRuleSymbol1.setDefined();
/* 2104:2591 */     localRuleSymbol1.setBlock(localRuleBlock);
/* 2105:2592 */     localRuleSymbol1.access = "private";
/* 2106:2593 */     this.grammar.define(localRuleSymbol1);
/* 2107:     */     
/* 2108:2595 */     boolean bool = this.grammar.theLLkAnalyzer.deterministic(localRuleBlock);
/* 2109:     */     
/* 2110:     */ 
/* 2111:2598 */     String str1 = null;
/* 2112:2599 */     if (((LexerGrammar)this.grammar).filterMode) {
/* 2113:2600 */       str1 = ((LexerGrammar)this.grammar).filterRule;
/* 2114:     */     }
/* 2115:2603 */     println("");
/* 2116:2604 */     println("override public IToken nextToken()\t\t\t//throws TokenStreamException");
/* 2117:2605 */     println("{");
/* 2118:2606 */     this.tabs += 1;
/* 2119:     */     
/* 2120:2608 */     this.blockNestingLevel = 1;
/* 2121:2609 */     this.saveIndexCreateLevel = 0;
/* 2122:2610 */     println("IToken theRetToken = null;");
/* 2123:2611 */     _println("tryAgain:");
/* 2124:2612 */     println("for (;;)");
/* 2125:2613 */     println("{");
/* 2126:2614 */     this.tabs += 1;
/* 2127:2615 */     println("IToken _token = null;");
/* 2128:2616 */     println("int _ttype = Token.INVALID_TYPE;");
/* 2129:2617 */     if (((LexerGrammar)this.grammar).filterMode)
/* 2130:     */     {
/* 2131:2618 */       println("setCommitToPath(false);");
/* 2132:2619 */       if (str1 != null)
/* 2133:     */       {
/* 2134:2621 */         if (!this.grammar.isDefined(CodeGenerator.encodeLexerRuleName(str1)))
/* 2135:     */         {
/* 2136:2622 */           this.grammar.antlrTool.error("Filter rule " + str1 + " does not exist in this lexer");
/* 2137:     */         }
/* 2138:     */         else
/* 2139:     */         {
/* 2140:2625 */           RuleSymbol localRuleSymbol2 = (RuleSymbol)this.grammar.getSymbol(CodeGenerator.encodeLexerRuleName(str1));
/* 2141:2626 */           if (!localRuleSymbol2.isDefined()) {
/* 2142:2627 */             this.grammar.antlrTool.error("Filter rule " + str1 + " does not exist in this lexer");
/* 2143:2629 */           } else if (localRuleSymbol2.access.equals("public")) {
/* 2144:2630 */             this.grammar.antlrTool.error("Filter rule " + str1 + " must be protected");
/* 2145:     */           }
/* 2146:     */         }
/* 2147:2633 */         println("int _m;");
/* 2148:2634 */         println("_m = mark();");
/* 2149:     */       }
/* 2150:     */     }
/* 2151:2637 */     println("resetText();");
/* 2152:     */     
/* 2153:2639 */     println("try     // for char stream error handling");
/* 2154:2640 */     println("{");
/* 2155:2641 */     this.tabs += 1;
/* 2156:     */     
/* 2157:     */ 
/* 2158:2644 */     println("try     // for lexical error handling");
/* 2159:2645 */     println("{");
/* 2160:2646 */     this.tabs += 1;
/* 2161:2649 */     for (int k = 0; k < localRuleBlock.getAlternatives().size(); k++)
/* 2162:     */     {
/* 2163:2650 */       localObject1 = localRuleBlock.getAlternativeAt(k);
/* 2164:2651 */       if (localObject1.cache[1].containsEpsilon())
/* 2165:     */       {
/* 2166:2653 */         localObject2 = (RuleRefElement)((Alternative)localObject1).head;
/* 2167:2654 */         String str3 = CodeGenerator.decodeLexerRuleName(((RuleRefElement)localObject2).targetRule);
/* 2168:2655 */         this.antlrTool.warning("public lexical rule " + str3 + " is optional (can match \"nothing\")");
/* 2169:     */       }
/* 2170:     */     }
/* 2171:2660 */     String str2 = System.getProperty("line.separator");
/* 2172:2661 */     Object localObject1 = genCommonBlock(localRuleBlock, false);
/* 2173:2662 */     Object localObject2 = "if (cached_LA1==EOF_CHAR) { uponEOF(); returnToken_ = makeToken(Token.EOF_TYPE); }";
/* 2174:     */     
/* 2175:2664 */     localObject2 = (String)localObject2 + str2 + "\t\t\t\t";
/* 2176:2665 */     if (((LexerGrammar)this.grammar).filterMode)
/* 2177:     */     {
/* 2178:2666 */       if (str1 == null)
/* 2179:     */       {
/* 2180:2668 */         localObject2 = (String)localObject2 + "\t\t\t\telse";
/* 2181:2669 */         localObject2 = (String)localObject2 + "\t\t\t\t{";
/* 2182:2670 */         localObject2 = (String)localObject2 + "\t\t\t\t\tconsume();";
/* 2183:2671 */         localObject2 = (String)localObject2 + "\t\t\t\t\tgoto tryAgain;";
/* 2184:2672 */         localObject2 = (String)localObject2 + "\t\t\t\t}";
/* 2185:     */       }
/* 2186:     */       else
/* 2187:     */       {
/* 2188:2675 */         localObject2 = (String)localObject2 + "\t\t\t\t\telse" + str2 + "\t\t\t\t\t{" + str2 + "\t\t\t\t\tcommit();" + str2 + "\t\t\t\t\ttry {m" + str1 + "(false);}" + str2 + "\t\t\t\t\tcatch(RecognitionException e)" + str2 + "\t\t\t\t\t{" + str2 + "\t\t\t\t\t\t// catastrophic failure" + str2 + "\t\t\t\t\t\treportError(e);" + str2 + "\t\t\t\t\t\tconsume();" + str2 + "\t\t\t\t\t}" + str2 + "\t\t\t\t\tgoto tryAgain;" + str2 + "\t\t\t\t}";
/* 2189:     */       }
/* 2190:     */     }
/* 2191:     */     else {
/* 2192:2690 */       localObject2 = (String)localObject2 + "else {" + this.throwNoViable + "}";
/* 2193:     */     }
/* 2194:2692 */     genBlockFinish((CSharpBlockFinishingInfo)localObject1, (String)localObject2);
/* 2195:2695 */     if ((((LexerGrammar)this.grammar).filterMode) && (str1 != null)) {
/* 2196:2696 */       println("commit();");
/* 2197:     */     }
/* 2198:2702 */     println("if ( null==returnToken_ ) goto tryAgain; // found SKIP token");
/* 2199:2703 */     println("_ttype = returnToken_.Type;");
/* 2200:2704 */     if (((LexerGrammar)this.grammar).getTestLiterals()) {
/* 2201:2705 */       genLiteralsTest();
/* 2202:     */     }
/* 2203:2709 */     println("returnToken_.Type = _ttype;");
/* 2204:2710 */     println("return returnToken_;");
/* 2205:     */     
/* 2206:     */ 
/* 2207:2713 */     this.tabs -= 1;
/* 2208:2714 */     println("}");
/* 2209:2715 */     println("catch (RecognitionException e) {");
/* 2210:2716 */     this.tabs += 1;
/* 2211:2717 */     if (((LexerGrammar)this.grammar).filterMode) {
/* 2212:2718 */       if (str1 == null)
/* 2213:     */       {
/* 2214:2719 */         println("if (!getCommitToPath())");
/* 2215:2720 */         println("{");
/* 2216:2721 */         this.tabs += 1;
/* 2217:2722 */         println("consume();");
/* 2218:2723 */         println("goto tryAgain;");
/* 2219:2724 */         this.tabs -= 1;
/* 2220:2725 */         println("}");
/* 2221:     */       }
/* 2222:     */       else
/* 2223:     */       {
/* 2224:2728 */         println("if (!getCommitToPath())");
/* 2225:2729 */         println("{");
/* 2226:2730 */         this.tabs += 1;
/* 2227:2731 */         println("rewind(_m);");
/* 2228:2732 */         println("resetText();");
/* 2229:2733 */         println("try {m" + str1 + "(false);}");
/* 2230:2734 */         println("catch(RecognitionException ee) {");
/* 2231:2735 */         println("\t// horrendous failure: error in filter rule");
/* 2232:2736 */         println("\treportError(ee);");
/* 2233:2737 */         println("\tconsume();");
/* 2234:2738 */         println("}");
/* 2235:     */         
/* 2236:2740 */         this.tabs -= 1;
/* 2237:2741 */         println("}");
/* 2238:2742 */         println("else");
/* 2239:     */       }
/* 2240:     */     }
/* 2241:2745 */     if (localRuleBlock.getDefaultErrorHandler())
/* 2242:     */     {
/* 2243:2746 */       println("{");
/* 2244:2747 */       this.tabs += 1;
/* 2245:2748 */       println("reportError(e);");
/* 2246:2749 */       println("consume();");
/* 2247:2750 */       this.tabs -= 1;
/* 2248:2751 */       println("}");
/* 2249:     */     }
/* 2250:     */     else
/* 2251:     */     {
/* 2252:2755 */       this.tabs += 1;
/* 2253:2756 */       println("throw new TokenStreamRecognitionException(e);");
/* 2254:2757 */       this.tabs -= 1;
/* 2255:     */     }
/* 2256:2759 */     this.tabs -= 1;
/* 2257:2760 */     println("}");
/* 2258:     */     
/* 2259:     */ 
/* 2260:2763 */     this.tabs -= 1;
/* 2261:2764 */     println("}");
/* 2262:2765 */     println("catch (CharStreamException cse) {");
/* 2263:2766 */     println("\tif ( cse is CharStreamIOException ) {");
/* 2264:2767 */     println("\t\tthrow new TokenStreamIOException(((CharStreamIOException)cse).io);");
/* 2265:2768 */     println("\t}");
/* 2266:2769 */     println("\telse {");
/* 2267:2770 */     println("\t\tthrow new TokenStreamException(cse.Message);");
/* 2268:2771 */     println("\t}");
/* 2269:2772 */     println("}");
/* 2270:     */     
/* 2271:     */ 
/* 2272:2775 */     this.tabs -= 1;
/* 2273:2776 */     println("}");
/* 2274:     */     
/* 2275:     */ 
/* 2276:2779 */     this.tabs -= 1;
/* 2277:2780 */     println("}");
/* 2278:2781 */     println("");
/* 2279:     */   }
/* 2280:     */   
/* 2281:     */   public void genRule(RuleSymbol paramRuleSymbol, boolean paramBoolean, int paramInt, TokenManager paramTokenManager)
/* 2282:     */   {
/* 2283:2800 */     this.tabs = 1;
/* 2284:2801 */     if (this.DEBUG_CODE_GENERATOR) {
/* 2285:2801 */       System.out.println("genRule(" + paramRuleSymbol.getId() + ")");
/* 2286:     */     }
/* 2287:2802 */     if (!paramRuleSymbol.isDefined())
/* 2288:     */     {
/* 2289:2803 */       this.antlrTool.error("undefined rule: " + paramRuleSymbol.getId());
/* 2290:2804 */       return;
/* 2291:     */     }
/* 2292:2808 */     RuleBlock localRuleBlock = paramRuleSymbol.getBlock();
/* 2293:2809 */     this.currentRule = localRuleBlock;
/* 2294:2810 */     this.currentASTResult = paramRuleSymbol.getId();
/* 2295:     */     
/* 2296:     */ 
/* 2297:2813 */     this.declaredASTVariables.clear();
/* 2298:     */     
/* 2299:     */ 
/* 2300:2816 */     boolean bool1 = this.genAST;
/* 2301:2817 */     this.genAST = ((this.genAST) && (localRuleBlock.getAutoGen()));
/* 2302:     */     
/* 2303:     */ 
/* 2304:2820 */     this.saveText = localRuleBlock.getAutoGen();
/* 2305:2823 */     if (paramRuleSymbol.comment != null) {
/* 2306:2824 */       _println(paramRuleSymbol.comment);
/* 2307:     */     }
/* 2308:2829 */     print(paramRuleSymbol.access + " ");
/* 2309:2832 */     if (localRuleBlock.returnAction != null) {
/* 2310:2835 */       _print(extractTypeOfAction(localRuleBlock.returnAction, localRuleBlock.getLine(), localRuleBlock.getColumn()) + " ");
/* 2311:     */     } else {
/* 2312:2838 */       _print("void ");
/* 2313:     */     }
/* 2314:2842 */     _print(paramRuleSymbol.getId() + "(");
/* 2315:     */     
/* 2316:     */ 
/* 2317:2845 */     _print(this.commonExtraParams);
/* 2318:2846 */     if ((this.commonExtraParams.length() != 0) && (localRuleBlock.argAction != null)) {
/* 2319:2847 */       _print(",");
/* 2320:     */     }
/* 2321:2851 */     if (localRuleBlock.argAction != null)
/* 2322:     */     {
/* 2323:2854 */       _println("");
/* 2324:2855 */       this.tabs += 1;
/* 2325:2856 */       println(localRuleBlock.argAction);
/* 2326:2857 */       this.tabs -= 1;
/* 2327:2858 */       print(")");
/* 2328:     */     }
/* 2329:     */     else
/* 2330:     */     {
/* 2331:2862 */       _print(")");
/* 2332:     */     }
/* 2333:2866 */     _print(" //throws " + this.exceptionThrown);
/* 2334:2867 */     if ((this.grammar instanceof ParserGrammar)) {
/* 2335:2868 */       _print(", TokenStreamException");
/* 2336:2870 */     } else if ((this.grammar instanceof LexerGrammar)) {
/* 2337:2871 */       _print(", CharStreamException, TokenStreamException");
/* 2338:     */     }
/* 2339:2874 */     if (localRuleBlock.throwsSpec != null) {
/* 2340:2875 */       if ((this.grammar instanceof LexerGrammar)) {
/* 2341:2876 */         this.antlrTool.error("user-defined throws spec not allowed (yet) for lexer rule " + localRuleBlock.ruleName);
/* 2342:     */       } else {
/* 2343:2879 */         _print(", " + localRuleBlock.throwsSpec);
/* 2344:     */       }
/* 2345:     */     }
/* 2346:2883 */     _println("");
/* 2347:2884 */     _println("{");
/* 2348:2885 */     this.tabs += 1;
/* 2349:2888 */     if (localRuleBlock.returnAction != null) {
/* 2350:2889 */       println(localRuleBlock.returnAction + ";");
/* 2351:     */     }
/* 2352:2892 */     println(this.commonLocalVars);
/* 2353:2894 */     if (this.grammar.traceRules) {
/* 2354:2895 */       if ((this.grammar instanceof TreeWalkerGrammar))
/* 2355:     */       {
/* 2356:2896 */         if (this.usingCustomAST) {
/* 2357:2897 */           println("traceIn(\"" + paramRuleSymbol.getId() + "\",(AST)_t);");
/* 2358:     */         } else {
/* 2359:2899 */           println("traceIn(\"" + paramRuleSymbol.getId() + "\",_t);");
/* 2360:     */         }
/* 2361:     */       }
/* 2362:     */       else {
/* 2363:2902 */         println("traceIn(\"" + paramRuleSymbol.getId() + "\");");
/* 2364:     */       }
/* 2365:     */     }
/* 2366:2906 */     if ((this.grammar instanceof LexerGrammar))
/* 2367:     */     {
/* 2368:2909 */       if (paramRuleSymbol.getId().equals("mEOF")) {
/* 2369:2910 */         println("_ttype = Token.EOF_TYPE;");
/* 2370:     */       } else {
/* 2371:2912 */         println("_ttype = " + paramRuleSymbol.getId().substring(1) + ";");
/* 2372:     */       }
/* 2373:2915 */       this.blockNestingLevel = 1;
/* 2374:2916 */       this.saveIndexCreateLevel = 0;
/* 2375:     */     }
/* 2376:2927 */     if (this.grammar.debuggingOutput) {
/* 2377:2928 */       if ((this.grammar instanceof ParserGrammar)) {
/* 2378:2929 */         println("fireEnterRule(" + paramInt + ",0);");
/* 2379:2930 */       } else if ((this.grammar instanceof LexerGrammar)) {
/* 2380:2931 */         println("fireEnterRule(" + paramInt + ",_ttype);");
/* 2381:     */       }
/* 2382:     */     }
/* 2383:2935 */     if ((this.grammar.debuggingOutput) || (this.grammar.traceRules))
/* 2384:     */     {
/* 2385:2936 */       println("try { // debugging");
/* 2386:2937 */       this.tabs += 1;
/* 2387:     */     }
/* 2388:2941 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2389:2943 */       println(this.labeledElementASTType + " " + paramRuleSymbol.getId() + "_AST_in = (" + this.labeledElementASTType + ")_t;");
/* 2390:     */     }
/* 2391:2945 */     if (this.grammar.buildAST)
/* 2392:     */     {
/* 2393:2947 */       println("returnAST = null;");
/* 2394:     */       
/* 2395:     */ 
/* 2396:2950 */       println("ASTPair currentAST = new ASTPair();");
/* 2397:     */       
/* 2398:2952 */       println(this.labeledElementASTType + " " + paramRuleSymbol.getId() + "_AST = null;");
/* 2399:     */     }
/* 2400:2955 */     genBlockPreamble(localRuleBlock);
/* 2401:2956 */     genBlockInitAction(localRuleBlock);
/* 2402:2957 */     println("");
/* 2403:     */     
/* 2404:     */ 
/* 2405:2960 */     ExceptionSpec localExceptionSpec = localRuleBlock.findExceptionSpec("");
/* 2406:2963 */     if ((localExceptionSpec != null) || (localRuleBlock.getDefaultErrorHandler()))
/* 2407:     */     {
/* 2408:2964 */       println("try {      // for error handling");
/* 2409:2965 */       this.tabs += 1;
/* 2410:     */     }
/* 2411:     */     Object localObject;
/* 2412:2969 */     if (localRuleBlock.alternatives.size() == 1)
/* 2413:     */     {
/* 2414:2972 */       Alternative localAlternative = localRuleBlock.getAlternativeAt(0);
/* 2415:2973 */       localObject = localAlternative.semPred;
/* 2416:2974 */       if (localObject != null) {
/* 2417:2975 */         genSemPred((String)localObject, this.currentRule.line);
/* 2418:     */       }
/* 2419:2976 */       if (localAlternative.synPred != null) {
/* 2420:2977 */         this.antlrTool.warning("Syntactic predicate ignored for single alternative", this.grammar.getFilename(), localAlternative.synPred.getLine(), localAlternative.synPred.getColumn());
/* 2421:     */       }
/* 2422:2982 */       genAlt(localAlternative, localRuleBlock);
/* 2423:     */     }
/* 2424:     */     else
/* 2425:     */     {
/* 2426:2987 */       boolean bool2 = this.grammar.theLLkAnalyzer.deterministic(localRuleBlock);
/* 2427:     */       
/* 2428:2989 */       localObject = genCommonBlock(localRuleBlock, false);
/* 2429:2990 */       genBlockFinish((CSharpBlockFinishingInfo)localObject, this.throwNoViable);
/* 2430:     */     }
/* 2431:2994 */     if ((localExceptionSpec != null) || (localRuleBlock.getDefaultErrorHandler()))
/* 2432:     */     {
/* 2433:2996 */       this.tabs -= 1;
/* 2434:2997 */       println("}");
/* 2435:     */     }
/* 2436:3001 */     if (localExceptionSpec != null)
/* 2437:     */     {
/* 2438:3003 */       genErrorHandler(localExceptionSpec);
/* 2439:     */     }
/* 2440:3005 */     else if (localRuleBlock.getDefaultErrorHandler())
/* 2441:     */     {
/* 2442:3008 */       println("catch (" + this.exceptionThrown + " ex)");
/* 2443:3009 */       println("{");
/* 2444:3010 */       this.tabs += 1;
/* 2445:3012 */       if (this.grammar.hasSyntacticPredicate)
/* 2446:     */       {
/* 2447:3013 */         println("if (0 == inputState.guessing)");
/* 2448:3014 */         println("{");
/* 2449:3015 */         this.tabs += 1;
/* 2450:     */       }
/* 2451:3017 */       println("reportError(ex);");
/* 2452:3018 */       if (!(this.grammar instanceof TreeWalkerGrammar))
/* 2453:     */       {
/* 2454:3021 */         Lookahead localLookahead = this.grammar.theLLkAnalyzer.FOLLOW(1, localRuleBlock.endNode);
/* 2455:3022 */         localObject = getBitsetName(markBitsetForGen(localLookahead.fset));
/* 2456:3023 */         println("recover(ex," + (String)localObject + ");");
/* 2457:     */       }
/* 2458:     */       else
/* 2459:     */       {
/* 2460:3028 */         println("if (null != _t)");
/* 2461:3029 */         println("{");
/* 2462:3030 */         this.tabs += 1;
/* 2463:3031 */         println("_t = _t.getNextSibling();");
/* 2464:3032 */         this.tabs -= 1;
/* 2465:3033 */         println("}");
/* 2466:     */       }
/* 2467:3035 */       if (this.grammar.hasSyntacticPredicate)
/* 2468:     */       {
/* 2469:3037 */         this.tabs -= 1;
/* 2470:     */         
/* 2471:3039 */         println("}");
/* 2472:3040 */         println("else");
/* 2473:3041 */         println("{");
/* 2474:3042 */         this.tabs += 1;
/* 2475:3043 */         println("throw ex;");
/* 2476:3044 */         this.tabs -= 1;
/* 2477:3045 */         println("}");
/* 2478:     */       }
/* 2479:3048 */       this.tabs -= 1;
/* 2480:3049 */       println("}");
/* 2481:     */     }
/* 2482:3053 */     if (this.grammar.buildAST) {
/* 2483:3054 */       println("returnAST = " + paramRuleSymbol.getId() + "_AST;");
/* 2484:     */     }
/* 2485:3058 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2486:3059 */       println("retTree_ = _t;");
/* 2487:     */     }
/* 2488:3063 */     if (localRuleBlock.getTestLiterals()) {
/* 2489:3064 */       if (paramRuleSymbol.access.equals("protected")) {
/* 2490:3065 */         genLiteralsTestForPartialToken();
/* 2491:     */       } else {
/* 2492:3068 */         genLiteralsTest();
/* 2493:     */       }
/* 2494:     */     }
/* 2495:3073 */     if ((this.grammar instanceof LexerGrammar))
/* 2496:     */     {
/* 2497:3074 */       println("if (_createToken && (null == _token) && (_ttype != Token.SKIP))");
/* 2498:3075 */       println("{");
/* 2499:3076 */       this.tabs += 1;
/* 2500:3077 */       println("_token = makeToken(_ttype);");
/* 2501:3078 */       println("_token.setText(text.ToString(_begin, text.Length-_begin));");
/* 2502:3079 */       this.tabs -= 1;
/* 2503:3080 */       println("}");
/* 2504:3081 */       println("returnToken_ = _token;");
/* 2505:     */     }
/* 2506:3085 */     if (localRuleBlock.returnAction != null) {
/* 2507:3086 */       println("return " + extractIdOfAction(localRuleBlock.returnAction, localRuleBlock.getLine(), localRuleBlock.getColumn()) + ";");
/* 2508:     */     }
/* 2509:3089 */     if ((this.grammar.debuggingOutput) || (this.grammar.traceRules))
/* 2510:     */     {
/* 2511:3090 */       this.tabs -= 1;
/* 2512:3091 */       println("}");
/* 2513:3092 */       println("finally");
/* 2514:3093 */       println("{ // debugging");
/* 2515:3094 */       this.tabs += 1;
/* 2516:3097 */       if (this.grammar.debuggingOutput) {
/* 2517:3098 */         if ((this.grammar instanceof ParserGrammar)) {
/* 2518:3099 */           println("fireExitRule(" + paramInt + ",0);");
/* 2519:3100 */         } else if ((this.grammar instanceof LexerGrammar)) {
/* 2520:3101 */           println("fireExitRule(" + paramInt + ",_ttype);");
/* 2521:     */         }
/* 2522:     */       }
/* 2523:3103 */       if (this.grammar.traceRules) {
/* 2524:3104 */         if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2525:3105 */           println("traceOut(\"" + paramRuleSymbol.getId() + "\",_t);");
/* 2526:     */         } else {
/* 2527:3108 */           println("traceOut(\"" + paramRuleSymbol.getId() + "\");");
/* 2528:     */         }
/* 2529:     */       }
/* 2530:3112 */       this.tabs -= 1;
/* 2531:3113 */       println("}");
/* 2532:     */     }
/* 2533:3116 */     this.tabs -= 1;
/* 2534:3117 */     println("}");
/* 2535:3118 */     println("");
/* 2536:     */     
/* 2537:     */ 
/* 2538:3121 */     this.genAST = bool1;
/* 2539:     */   }
/* 2540:     */   
/* 2541:     */   private void GenRuleInvocation(RuleRefElement paramRuleRefElement)
/* 2542:     */   {
/* 2543:3128 */     _print(paramRuleRefElement.targetRule + "(");
/* 2544:3131 */     if ((this.grammar instanceof LexerGrammar))
/* 2545:     */     {
/* 2546:3133 */       if (paramRuleRefElement.getLabel() != null) {
/* 2547:3134 */         _print("true");
/* 2548:     */       } else {
/* 2549:3137 */         _print("false");
/* 2550:     */       }
/* 2551:3139 */       if ((this.commonExtraArgs.length() != 0) || (paramRuleRefElement.args != null)) {
/* 2552:3140 */         _print(",");
/* 2553:     */       }
/* 2554:     */     }
/* 2555:3145 */     _print(this.commonExtraArgs);
/* 2556:3146 */     if ((this.commonExtraArgs.length() != 0) && (paramRuleRefElement.args != null)) {
/* 2557:3147 */       _print(",");
/* 2558:     */     }
/* 2559:3151 */     RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(paramRuleRefElement.targetRule);
/* 2560:3152 */     if (paramRuleRefElement.args != null)
/* 2561:     */     {
/* 2562:3155 */       ActionTransInfo localActionTransInfo = new ActionTransInfo();
/* 2563:3156 */       String str = processActionForSpecialSymbols(paramRuleRefElement.args, 0, this.currentRule, localActionTransInfo);
/* 2564:3157 */       if ((localActionTransInfo.assignToRoot) || (localActionTransInfo.refRuleRoot != null)) {
/* 2565:3159 */         this.antlrTool.error("Arguments of rule reference '" + paramRuleRefElement.targetRule + "' cannot set or ref #" + this.currentRule.getRuleName(), this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/* 2566:     */       }
/* 2567:3162 */       _print(str);
/* 2568:3165 */       if (localRuleSymbol.block.argAction == null) {
/* 2569:3167 */         this.antlrTool.warning("Rule '" + paramRuleRefElement.targetRule + "' accepts no arguments", this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/* 2570:     */       }
/* 2571:     */     }
/* 2572:3174 */     else if (localRuleSymbol.block.argAction != null)
/* 2573:     */     {
/* 2574:3176 */       this.antlrTool.warning("Missing parameters on reference to rule " + paramRuleRefElement.targetRule, this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/* 2575:     */     }
/* 2576:3179 */     _println(");");
/* 2577:3182 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2578:3183 */       println("_t = retTree_;");
/* 2579:     */     }
/* 2580:     */   }
/* 2581:     */   
/* 2582:     */   protected void genSemPred(String paramString, int paramInt)
/* 2583:     */   {
/* 2584:3188 */     ActionTransInfo localActionTransInfo = new ActionTransInfo();
/* 2585:3189 */     paramString = processActionForSpecialSymbols(paramString, paramInt, this.currentRule, localActionTransInfo);
/* 2586:     */     
/* 2587:3191 */     String str = this.charFormatter.escapeString(paramString);
/* 2588:3195 */     if ((this.grammar.debuggingOutput) && (((this.grammar instanceof ParserGrammar)) || ((this.grammar instanceof LexerGrammar)))) {
/* 2589:3196 */       paramString = "fireSemanticPredicateEvaluated(antlr.debug.SemanticPredicateEvent.VALIDATING," + addSemPred(str) + "," + paramString + ")";
/* 2590:     */     }
/* 2591:3198 */     println("if (!(" + paramString + "))");
/* 2592:3199 */     println("  throw new SemanticException(\"" + str + "\");");
/* 2593:     */   }
/* 2594:     */   
/* 2595:     */   protected void genSemPredMap()
/* 2596:     */   {
/* 2597:3205 */     Enumeration localEnumeration = this.semPreds.elements();
/* 2598:3206 */     println("private string[] _semPredNames = {");
/* 2599:3207 */     this.tabs += 1;
/* 2600:3208 */     while (localEnumeration.hasMoreElements()) {
/* 2601:3209 */       println("\"" + localEnumeration.nextElement() + "\",");
/* 2602:     */     }
/* 2603:3210 */     this.tabs -= 1;
/* 2604:3211 */     println("};");
/* 2605:     */   }
/* 2606:     */   
/* 2607:     */   protected void genSynPred(SynPredBlock paramSynPredBlock, String paramString)
/* 2608:     */   {
/* 2609:3214 */     if (this.DEBUG_CODE_GENERATOR) {
/* 2610:3214 */       System.out.println("gen=>(" + paramSynPredBlock + ")");
/* 2611:     */     }
/* 2612:3217 */     println("bool synPredMatched" + paramSynPredBlock.ID + " = false;");
/* 2613:3219 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2614:3220 */       println("if (_t==null) _t=ASTNULL;");
/* 2615:     */     }
/* 2616:3223 */     println("if (" + paramString + ")");
/* 2617:3224 */     println("{");
/* 2618:3225 */     this.tabs += 1;
/* 2619:3228 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2620:3229 */       println("AST __t" + paramSynPredBlock.ID + " = _t;");
/* 2621:     */     } else {
/* 2622:3232 */       println("int _m" + paramSynPredBlock.ID + " = mark();");
/* 2623:     */     }
/* 2624:3236 */     println("synPredMatched" + paramSynPredBlock.ID + " = true;");
/* 2625:3237 */     println("inputState.guessing++;");
/* 2626:3240 */     if ((this.grammar.debuggingOutput) && (((this.grammar instanceof ParserGrammar)) || ((this.grammar instanceof LexerGrammar)))) {
/* 2627:3242 */       println("fireSyntacticPredicateStarted();");
/* 2628:     */     }
/* 2629:3245 */     this.syntacticPredLevel += 1;
/* 2630:3246 */     println("try {");
/* 2631:3247 */     this.tabs += 1;
/* 2632:3248 */     gen(paramSynPredBlock);
/* 2633:3249 */     this.tabs -= 1;
/* 2634:     */     
/* 2635:3251 */     println("}");
/* 2636:     */     
/* 2637:     */ 
/* 2638:3254 */     println("catch (" + this.exceptionThrown + ")");
/* 2639:3255 */     println("{");
/* 2640:3256 */     this.tabs += 1;
/* 2641:3257 */     println("synPredMatched" + paramSynPredBlock.ID + " = false;");
/* 2642:     */     
/* 2643:3259 */     this.tabs -= 1;
/* 2644:3260 */     println("}");
/* 2645:3263 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2646:3264 */       println("_t = __t" + paramSynPredBlock.ID + ";");
/* 2647:     */     } else {
/* 2648:3267 */       println("rewind(_m" + paramSynPredBlock.ID + ");");
/* 2649:     */     }
/* 2650:3270 */     println("inputState.guessing--;");
/* 2651:3273 */     if ((this.grammar.debuggingOutput) && (((this.grammar instanceof ParserGrammar)) || ((this.grammar instanceof LexerGrammar))))
/* 2652:     */     {
/* 2653:3275 */       println("if (synPredMatched" + paramSynPredBlock.ID + ")");
/* 2654:3276 */       println("  fireSyntacticPredicateSucceeded();");
/* 2655:3277 */       println("else");
/* 2656:3278 */       println("  fireSyntacticPredicateFailed();");
/* 2657:     */     }
/* 2658:3281 */     this.syntacticPredLevel -= 1;
/* 2659:3282 */     this.tabs -= 1;
/* 2660:     */     
/* 2661:     */ 
/* 2662:3285 */     println("}");
/* 2663:     */     
/* 2664:     */ 
/* 2665:3288 */     println("if ( synPredMatched" + paramSynPredBlock.ID + " )");
/* 2666:3289 */     println("{");
/* 2667:     */   }
/* 2668:     */   
/* 2669:     */   public void genTokenStrings()
/* 2670:     */   {
/* 2671:3302 */     println("");
/* 2672:3303 */     println("public static readonly string[] tokenNames_ = new string[] {");
/* 2673:3304 */     this.tabs += 1;
/* 2674:     */     
/* 2675:     */ 
/* 2676:     */ 
/* 2677:3308 */     antlr.collections.impl.Vector localVector = this.grammar.tokenManager.getVocabulary();
/* 2678:3309 */     for (int i = 0; i < localVector.size(); i++)
/* 2679:     */     {
/* 2680:3311 */       String str = (String)localVector.elementAt(i);
/* 2681:3312 */       if (str == null) {
/* 2682:3314 */         str = "<" + String.valueOf(i) + ">";
/* 2683:     */       }
/* 2684:3316 */       if ((!str.startsWith("\"")) && (!str.startsWith("<")))
/* 2685:     */       {
/* 2686:3317 */         TokenSymbol localTokenSymbol = this.grammar.tokenManager.getTokenSymbol(str);
/* 2687:3318 */         if ((localTokenSymbol != null) && (localTokenSymbol.getParaphrase() != null)) {
/* 2688:3319 */           str = StringUtils.stripFrontBack(localTokenSymbol.getParaphrase(), "\"", "\"");
/* 2689:     */         }
/* 2690:     */       }
/* 2691:3322 */       else if (str.startsWith("\""))
/* 2692:     */       {
/* 2693:3323 */         str = StringUtils.stripFrontBack(str, "\"", "\"");
/* 2694:     */       }
/* 2695:3325 */       print(this.charFormatter.literalString(str));
/* 2696:3326 */       if (i != localVector.size() - 1) {
/* 2697:3327 */         _print(",");
/* 2698:     */       }
/* 2699:3329 */       _println("");
/* 2700:     */     }
/* 2701:3333 */     this.tabs -= 1;
/* 2702:3334 */     println("};");
/* 2703:     */   }
/* 2704:     */   
/* 2705:     */   protected void genTokenTypes(TokenManager paramTokenManager)
/* 2706:     */     throws IOException
/* 2707:     */   {
/* 2708:3341 */     setupOutput(paramTokenManager.getName() + TokenTypesFileSuffix);
/* 2709:     */     
/* 2710:3343 */     this.tabs = 0;
/* 2711:     */     
/* 2712:     */ 
/* 2713:3346 */     genHeader();
/* 2714:     */     
/* 2715:3348 */     println(this.behavior.getHeaderAction(""));
/* 2716:3351 */     if (nameSpace != null) {
/* 2717:3352 */       nameSpace.emitDeclarations(this.currentOutput);
/* 2718:     */     }
/* 2719:3353 */     this.tabs += 1;
/* 2720:     */     
/* 2721:     */ 
/* 2722:     */ 
/* 2723:3357 */     println("public class " + paramTokenManager.getName() + TokenTypesFileSuffix);
/* 2724:     */     
/* 2725:3359 */     println("{");
/* 2726:3360 */     this.tabs += 1;
/* 2727:     */     
/* 2728:3362 */     genTokenDefinitions(paramTokenManager);
/* 2729:     */     
/* 2730:     */ 
/* 2731:3365 */     this.tabs -= 1;
/* 2732:3366 */     println("}");
/* 2733:     */     
/* 2734:3368 */     this.tabs -= 1;
/* 2735:3370 */     if (nameSpace != null) {
/* 2736:3371 */       nameSpace.emitClosures(this.currentOutput);
/* 2737:     */     }
/* 2738:3374 */     this.currentOutput.close();
/* 2739:3375 */     this.currentOutput = null;
/* 2740:3376 */     exitIfError();
/* 2741:     */   }
/* 2742:     */   
/* 2743:     */   protected void genTokenDefinitions(TokenManager paramTokenManager)
/* 2744:     */     throws IOException
/* 2745:     */   {
/* 2746:3380 */     antlr.collections.impl.Vector localVector = paramTokenManager.getVocabulary();
/* 2747:     */     
/* 2748:     */ 
/* 2749:3383 */     println("public const int EOF = 1;");
/* 2750:3384 */     println("public const int NULL_TREE_LOOKAHEAD = 3;");
/* 2751:3386 */     for (int i = 4; i < localVector.size(); i++)
/* 2752:     */     {
/* 2753:3387 */       String str1 = (String)localVector.elementAt(i);
/* 2754:3388 */       if (str1 != null) {
/* 2755:3389 */         if (str1.startsWith("\""))
/* 2756:     */         {
/* 2757:3391 */           StringLiteralSymbol localStringLiteralSymbol = (StringLiteralSymbol)paramTokenManager.getTokenSymbol(str1);
/* 2758:3392 */           if (localStringLiteralSymbol == null)
/* 2759:     */           {
/* 2760:3393 */             this.antlrTool.panic("String literal " + str1 + " not in symbol table");
/* 2761:     */           }
/* 2762:3395 */           else if (localStringLiteralSymbol.label != null)
/* 2763:     */           {
/* 2764:3396 */             println("public const int " + localStringLiteralSymbol.label + " = " + i + ";");
/* 2765:     */           }
/* 2766:     */           else
/* 2767:     */           {
/* 2768:3399 */             String str2 = mangleLiteral(str1);
/* 2769:3400 */             if (str2 != null)
/* 2770:     */             {
/* 2771:3402 */               println("public const int " + str2 + " = " + i + ";");
/* 2772:     */               
/* 2773:3404 */               localStringLiteralSymbol.label = str2;
/* 2774:     */             }
/* 2775:     */             else
/* 2776:     */             {
/* 2777:3407 */               println("// " + str1 + " = " + i);
/* 2778:     */             }
/* 2779:     */           }
/* 2780:     */         }
/* 2781:3411 */         else if (!str1.startsWith("<"))
/* 2782:     */         {
/* 2783:3412 */           println("public const int " + str1 + " = " + i + ";");
/* 2784:     */         }
/* 2785:     */       }
/* 2786:     */     }
/* 2787:3416 */     println("");
/* 2788:     */   }
/* 2789:     */   
/* 2790:     */   public String processStringForASTConstructor(String paramString)
/* 2791:     */   {
/* 2792:3434 */     if ((this.usingCustomAST) && (((this.grammar instanceof TreeWalkerGrammar)) || ((this.grammar instanceof ParserGrammar))) && (!this.grammar.tokenManager.tokenDefined(paramString))) {
/* 2793:3440 */       return "(AST)" + paramString;
/* 2794:     */     }
/* 2795:3445 */     return paramString;
/* 2796:     */   }
/* 2797:     */   
/* 2798:     */   public String getASTCreateString(antlr.collections.impl.Vector paramVector)
/* 2799:     */   {
/* 2800:3453 */     if (paramVector.size() == 0) {
/* 2801:3454 */       return "";
/* 2802:     */     }
/* 2803:3456 */     StringBuffer localStringBuffer = new StringBuffer();
/* 2804:3457 */     localStringBuffer.append("(" + this.labeledElementASTType + ") astFactory.make(");
/* 2805:     */     
/* 2806:3459 */     localStringBuffer.append(paramVector.elementAt(0));
/* 2807:3460 */     for (int i = 1; i < paramVector.size(); i++) {
/* 2808:3461 */       localStringBuffer.append(", " + paramVector.elementAt(i));
/* 2809:     */     }
/* 2810:3463 */     localStringBuffer.append(")");
/* 2811:3464 */     return localStringBuffer.toString();
/* 2812:     */   }
/* 2813:     */   
/* 2814:     */   public String getASTCreateString(GrammarAtom paramGrammarAtom, String paramString)
/* 2815:     */   {
/* 2816:3472 */     String str = "astFactory.create(" + paramString + ")";
/* 2817:3474 */     if (paramGrammarAtom == null) {
/* 2818:3475 */       return getASTCreateString(paramString);
/* 2819:     */     }
/* 2820:3477 */     if (paramGrammarAtom.getASTNodeType() != null)
/* 2821:     */     {
/* 2822:3485 */       TokenSymbol localTokenSymbol = this.grammar.tokenManager.getTokenSymbol(paramGrammarAtom.getText());
/* 2823:3486 */       if ((localTokenSymbol == null) || (localTokenSymbol.getASTNodeType() != paramGrammarAtom.getASTNodeType())) {
/* 2824:3487 */         str = "(" + paramGrammarAtom.getASTNodeType() + ") astFactory.create(" + paramString + ", \"" + paramGrammarAtom.getASTNodeType() + "\")";
/* 2825:3488 */       } else if ((localTokenSymbol != null) && (localTokenSymbol.getASTNodeType() != null)) {
/* 2826:3489 */         str = "(" + localTokenSymbol.getASTNodeType() + ") " + str;
/* 2827:     */       }
/* 2828:     */     }
/* 2829:3491 */     else if (this.usingCustomAST)
/* 2830:     */     {
/* 2831:3492 */       str = "(" + this.labeledElementASTType + ") " + str;
/* 2832:     */     }
/* 2833:3494 */     return str;
/* 2834:     */   }
/* 2835:     */   
/* 2836:     */   public String getASTCreateString(String paramString)
/* 2837:     */   {
/* 2838:3528 */     if (paramString == null) {
/* 2839:3529 */       paramString = "";
/* 2840:     */     }
/* 2841:3531 */     String str1 = "astFactory.create(" + paramString + ")";
/* 2842:3532 */     String str2 = paramString;
/* 2843:3533 */     String str3 = null;
/* 2844:     */     
/* 2845:3535 */     int j = 0;
/* 2846:     */     
/* 2847:3537 */     int i = paramString.indexOf(',');
/* 2848:3538 */     if (i != -1)
/* 2849:     */     {
/* 2850:3539 */       str2 = paramString.substring(0, i);
/* 2851:3540 */       str3 = paramString.substring(i + 1, paramString.length());
/* 2852:3541 */       i = str3.indexOf(',');
/* 2853:3542 */       if (i != -1) {
/* 2854:3546 */         j = 1;
/* 2855:     */       }
/* 2856:     */     }
/* 2857:3549 */     TokenSymbol localTokenSymbol = this.grammar.tokenManager.getTokenSymbol(str2);
/* 2858:3550 */     if ((null != localTokenSymbol) && (null != localTokenSymbol.getASTNodeType())) {
/* 2859:3551 */       str1 = "(" + localTokenSymbol.getASTNodeType() + ") " + str1;
/* 2860:3552 */     } else if (this.usingCustomAST) {
/* 2861:3553 */       str1 = "(" + this.labeledElementASTType + ") " + str1;
/* 2862:     */     }
/* 2863:3555 */     return str1;
/* 2864:     */   }
/* 2865:     */   
/* 2866:     */   protected String getLookaheadTestExpression(Lookahead[] paramArrayOfLookahead, int paramInt)
/* 2867:     */   {
/* 2868:3559 */     StringBuffer localStringBuffer = new StringBuffer(100);
/* 2869:3560 */     int i = 1;
/* 2870:     */     
/* 2871:3562 */     localStringBuffer.append("(");
/* 2872:3563 */     for (int j = 1; j <= paramInt; j++)
/* 2873:     */     {
/* 2874:3564 */       BitSet localBitSet = paramArrayOfLookahead[j].fset;
/* 2875:3565 */       if (i == 0) {
/* 2876:3566 */         localStringBuffer.append(") && (");
/* 2877:     */       }
/* 2878:3568 */       i = 0;
/* 2879:3573 */       if (paramArrayOfLookahead[j].containsEpsilon()) {
/* 2880:3574 */         localStringBuffer.append("true");
/* 2881:     */       } else {
/* 2882:3576 */         localStringBuffer.append(getLookaheadTestTerm(j, localBitSet));
/* 2883:     */       }
/* 2884:     */     }
/* 2885:3579 */     localStringBuffer.append(")");
/* 2886:     */     
/* 2887:3581 */     return localStringBuffer.toString();
/* 2888:     */   }
/* 2889:     */   
/* 2890:     */   protected String getLookaheadTestExpression(Alternative paramAlternative, int paramInt)
/* 2891:     */   {
/* 2892:3589 */     int i = paramAlternative.lookaheadDepth;
/* 2893:3590 */     if (i == 2147483647) {
/* 2894:3593 */       i = this.grammar.maxk;
/* 2895:     */     }
/* 2896:3596 */     if (paramInt == 0) {
/* 2897:3599 */       return "( true )";
/* 2898:     */     }
/* 2899:3601 */     return "(" + getLookaheadTestExpression(paramAlternative.cache, i) + ")";
/* 2900:     */   }
/* 2901:     */   
/* 2902:     */   protected String getLookaheadTestTerm(int paramInt, BitSet paramBitSet)
/* 2903:     */   {
/* 2904:3614 */     String str1 = lookaheadString(paramInt);
/* 2905:     */     
/* 2906:     */ 
/* 2907:3617 */     int[] arrayOfInt = paramBitSet.toArray();
/* 2908:3618 */     if (elementsAreRange(arrayOfInt)) {
/* 2909:3619 */       return getRangeExpression(paramInt, arrayOfInt);
/* 2910:     */     }
/* 2911:3624 */     int i = paramBitSet.degree();
/* 2912:3625 */     if (i == 0) {
/* 2913:3626 */       return "true";
/* 2914:     */     }
/* 2915:3629 */     if (i >= this.bitsetTestThreshold)
/* 2916:     */     {
/* 2917:3630 */       j = markBitsetForGen(paramBitSet);
/* 2918:3631 */       return getBitsetName(j) + ".member(" + str1 + ")";
/* 2919:     */     }
/* 2920:3635 */     StringBuffer localStringBuffer = new StringBuffer();
/* 2921:3636 */     for (int j = 0; j < arrayOfInt.length; j++)
/* 2922:     */     {
/* 2923:3638 */       String str2 = getValueString(arrayOfInt[j]);
/* 2924:3641 */       if (j > 0) {
/* 2925:3641 */         localStringBuffer.append("||");
/* 2926:     */       }
/* 2927:3642 */       localStringBuffer.append(str1);
/* 2928:3643 */       localStringBuffer.append("==");
/* 2929:3644 */       localStringBuffer.append(str2);
/* 2930:     */     }
/* 2931:3646 */     return localStringBuffer.toString();
/* 2932:     */   }
/* 2933:     */   
/* 2934:     */   public String getRangeExpression(int paramInt, int[] paramArrayOfInt)
/* 2935:     */   {
/* 2936:3655 */     if (!elementsAreRange(paramArrayOfInt)) {
/* 2937:3656 */       this.antlrTool.panic("getRangeExpression called with non-range");
/* 2938:     */     }
/* 2939:3658 */     int i = paramArrayOfInt[0];
/* 2940:3659 */     int j = paramArrayOfInt[(paramArrayOfInt.length - 1)];
/* 2941:     */     
/* 2942:3661 */     return "(" + lookaheadString(paramInt) + " >= " + getValueString(i) + " && " + lookaheadString(paramInt) + " <= " + getValueString(j) + ")";
/* 2943:     */   }
/* 2944:     */   
/* 2945:     */   private String getValueString(int paramInt)
/* 2946:     */   {
/* 2947:     */     Object localObject;
/* 2948:3671 */     if ((this.grammar instanceof LexerGrammar))
/* 2949:     */     {
/* 2950:3672 */       localObject = this.charFormatter.literalChar(paramInt);
/* 2951:     */     }
/* 2952:     */     else
/* 2953:     */     {
/* 2954:3676 */       TokenSymbol localTokenSymbol = this.grammar.tokenManager.getTokenSymbolAt(paramInt);
/* 2955:3677 */       if (localTokenSymbol == null) {
/* 2956:3678 */         return "" + paramInt;
/* 2957:     */       }
/* 2958:3681 */       String str1 = localTokenSymbol.getId();
/* 2959:3682 */       if ((localTokenSymbol instanceof StringLiteralSymbol))
/* 2960:     */       {
/* 2961:3686 */         StringLiteralSymbol localStringLiteralSymbol = (StringLiteralSymbol)localTokenSymbol;
/* 2962:3687 */         String str2 = localStringLiteralSymbol.getLabel();
/* 2963:3688 */         if (str2 != null)
/* 2964:     */         {
/* 2965:3689 */           localObject = str2;
/* 2966:     */         }
/* 2967:     */         else
/* 2968:     */         {
/* 2969:3692 */           localObject = mangleLiteral(str1);
/* 2970:3693 */           if (localObject == null) {
/* 2971:3694 */             localObject = String.valueOf(paramInt);
/* 2972:     */           }
/* 2973:     */         }
/* 2974:     */       }
/* 2975:     */       else
/* 2976:     */       {
/* 2977:3699 */         localObject = str1;
/* 2978:     */       }
/* 2979:     */     }
/* 2980:3702 */     return localObject;
/* 2981:     */   }
/* 2982:     */   
/* 2983:     */   protected boolean lookaheadIsEmpty(Alternative paramAlternative, int paramInt)
/* 2984:     */   {
/* 2985:3707 */     int i = paramAlternative.lookaheadDepth;
/* 2986:3708 */     if (i == 2147483647) {
/* 2987:3709 */       i = this.grammar.maxk;
/* 2988:     */     }
/* 2989:3711 */     for (int j = 1; (j <= i) && (j <= paramInt); j++)
/* 2990:     */     {
/* 2991:3712 */       BitSet localBitSet = paramAlternative.cache[j].fset;
/* 2992:3713 */       if (localBitSet.degree() != 0) {
/* 2993:3714 */         return false;
/* 2994:     */       }
/* 2995:     */     }
/* 2996:3717 */     return true;
/* 2997:     */   }
/* 2998:     */   
/* 2999:     */   private String lookaheadString(int paramInt)
/* 3000:     */   {
/* 3001:3721 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 3002:3722 */       return "_t.Type";
/* 3003:     */     }
/* 3004:3724 */     if ((this.grammar instanceof LexerGrammar))
/* 3005:     */     {
/* 3006:3725 */       if (paramInt == 1) {
/* 3007:3726 */         return "cached_LA1";
/* 3008:     */       }
/* 3009:3728 */       if (paramInt == 2) {
/* 3010:3729 */         return "cached_LA2";
/* 3011:     */       }
/* 3012:     */     }
/* 3013:3732 */     return "LA(" + paramInt + ")";
/* 3014:     */   }
/* 3015:     */   
/* 3016:     */   private String mangleLiteral(String paramString)
/* 3017:     */   {
/* 3018:3742 */     String str = this.antlrTool.literalsPrefix;
/* 3019:3743 */     for (int i = 1; i < paramString.length() - 1; i++)
/* 3020:     */     {
/* 3021:3744 */       if ((!Character.isLetter(paramString.charAt(i))) && (paramString.charAt(i) != '_')) {
/* 3022:3746 */         return null;
/* 3023:     */       }
/* 3024:3748 */       str = str + paramString.charAt(i);
/* 3025:     */     }
/* 3026:3750 */     if (this.antlrTool.upperCaseMangledLiterals) {
/* 3027:3751 */       str = str.toUpperCase();
/* 3028:     */     }
/* 3029:3753 */     return str;
/* 3030:     */   }
/* 3031:     */   
/* 3032:     */   public String mapTreeId(String paramString, ActionTransInfo paramActionTransInfo)
/* 3033:     */   {
/* 3034:3764 */     if (this.currentRule == null) {
/* 3035:3764 */       return paramString;
/* 3036:     */     }
/* 3037:3766 */     int i = 0;
/* 3038:3767 */     String str1 = paramString;
/* 3039:3768 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 3040:3770 */       if (!this.grammar.buildAST)
/* 3041:     */       {
/* 3042:3772 */         i = 1;
/* 3043:     */       }
/* 3044:3775 */       else if ((str1.length() > 3) && (str1.lastIndexOf("_in") == str1.length() - 3))
/* 3045:     */       {
/* 3046:3778 */         str1 = str1.substring(0, str1.length() - 3);
/* 3047:3779 */         i = 1;
/* 3048:     */       }
/* 3049:     */     }
/* 3050:     */     Object localObject;
/* 3051:3785 */     for (int j = 0; j < this.currentRule.labeledElements.size(); j++)
/* 3052:     */     {
/* 3053:3787 */       localObject = (AlternativeElement)this.currentRule.labeledElements.elementAt(j);
/* 3054:3788 */       if (((AlternativeElement)localObject).getLabel().equals(str1)) {
/* 3055:3790 */         return str1 + "_AST";
/* 3056:     */       }
/* 3057:     */     }
/* 3058:3797 */     String str2 = (String)this.treeVariableMap.get(str1);
/* 3059:3798 */     if (str2 != null)
/* 3060:     */     {
/* 3061:3800 */       if (str2 == NONUNIQUE)
/* 3062:     */       {
/* 3063:3803 */         this.antlrTool.error("Ambiguous reference to AST element " + str1 + " in rule " + this.currentRule.getRuleName());
/* 3064:     */         
/* 3065:3805 */         return null;
/* 3066:     */       }
/* 3067:3807 */       if (str2.equals(this.currentRule.getRuleName()))
/* 3068:     */       {
/* 3069:3813 */         this.antlrTool.error("Ambiguous reference to AST element " + str1 + " in rule " + this.currentRule.getRuleName());
/* 3070:     */         
/* 3071:3815 */         return null;
/* 3072:     */       }
/* 3073:3819 */       return i != 0 ? str2 + "_in" : str2;
/* 3074:     */     }
/* 3075:3825 */     if (str1.equals(this.currentRule.getRuleName()))
/* 3076:     */     {
/* 3077:3827 */       localObject = str1 + "_AST";
/* 3078:3828 */       if ((paramActionTransInfo != null) && 
/* 3079:3829 */         (i == 0)) {
/* 3080:3830 */         paramActionTransInfo.refRuleRoot = ((String)localObject);
/* 3081:     */       }
/* 3082:3833 */       return localObject;
/* 3083:     */     }
/* 3084:3838 */     return str1;
/* 3085:     */   }
/* 3086:     */   
/* 3087:     */   private void mapTreeVariable(AlternativeElement paramAlternativeElement, String paramString)
/* 3088:     */   {
/* 3089:3848 */     if ((paramAlternativeElement instanceof TreeElement))
/* 3090:     */     {
/* 3091:3849 */       mapTreeVariable(((TreeElement)paramAlternativeElement).root, paramString);
/* 3092:3850 */       return;
/* 3093:     */     }
/* 3094:3854 */     String str = null;
/* 3095:3857 */     if (paramAlternativeElement.getLabel() == null) {
/* 3096:3858 */       if ((paramAlternativeElement instanceof TokenRefElement)) {
/* 3097:3860 */         str = ((TokenRefElement)paramAlternativeElement).atomText;
/* 3098:3862 */       } else if ((paramAlternativeElement instanceof RuleRefElement)) {
/* 3099:3864 */         str = ((RuleRefElement)paramAlternativeElement).targetRule;
/* 3100:     */       }
/* 3101:     */     }
/* 3102:3868 */     if (str != null) {
/* 3103:3869 */       if (this.treeVariableMap.get(str) != null)
/* 3104:     */       {
/* 3105:3871 */         this.treeVariableMap.remove(str);
/* 3106:3872 */         this.treeVariableMap.put(str, NONUNIQUE);
/* 3107:     */       }
/* 3108:     */       else
/* 3109:     */       {
/* 3110:3875 */         this.treeVariableMap.put(str, paramString);
/* 3111:     */       }
/* 3112:     */     }
/* 3113:     */   }
/* 3114:     */   
/* 3115:     */   protected String processActionForSpecialSymbols(String paramString, int paramInt, RuleBlock paramRuleBlock, ActionTransInfo paramActionTransInfo)
/* 3116:     */   {
/* 3117:3889 */     if ((paramString == null) || (paramString.length() == 0)) {
/* 3118:3890 */       return null;
/* 3119:     */     }
/* 3120:3894 */     if (this.grammar == null) {
/* 3121:3895 */       return paramString;
/* 3122:     */     }
/* 3123:3898 */     if (((this.grammar.buildAST) && (paramString.indexOf('#') != -1)) || ((this.grammar instanceof TreeWalkerGrammar)) || ((((this.grammar instanceof LexerGrammar)) || ((this.grammar instanceof ParserGrammar))) && (paramString.indexOf('$') != -1)))
/* 3124:     */     {
/* 3125:3905 */       ActionLexer localActionLexer = new ActionLexer(paramString, paramRuleBlock, this, paramActionTransInfo);
/* 3126:     */       
/* 3127:3907 */       localActionLexer.setLineOffset(paramInt);
/* 3128:3908 */       localActionLexer.setFilename(this.grammar.getFilename());
/* 3129:3909 */       localActionLexer.setTool(this.antlrTool);
/* 3130:     */       try
/* 3131:     */       {
/* 3132:3912 */         localActionLexer.mACTION(true);
/* 3133:3913 */         paramString = localActionLexer.getTokenObject().getText();
/* 3134:     */       }
/* 3135:     */       catch (RecognitionException localRecognitionException)
/* 3136:     */       {
/* 3137:3918 */         localActionLexer.reportError(localRecognitionException);
/* 3138:3919 */         return paramString;
/* 3139:     */       }
/* 3140:     */       catch (TokenStreamException localTokenStreamException)
/* 3141:     */       {
/* 3142:3922 */         this.antlrTool.panic("Error reading action:" + paramString);
/* 3143:3923 */         return paramString;
/* 3144:     */       }
/* 3145:     */       catch (CharStreamException localCharStreamException)
/* 3146:     */       {
/* 3147:3926 */         this.antlrTool.panic("Error reading action:" + paramString);
/* 3148:3927 */         return paramString;
/* 3149:     */       }
/* 3150:     */     }
/* 3151:3930 */     return paramString;
/* 3152:     */   }
/* 3153:     */   
/* 3154:     */   private void setupGrammarParameters(Grammar paramGrammar)
/* 3155:     */   {
/* 3156:     */     Token localToken;
/* 3157:3934 */     if (((paramGrammar instanceof ParserGrammar)) || ((paramGrammar instanceof LexerGrammar)) || ((paramGrammar instanceof TreeWalkerGrammar)))
/* 3158:     */     {
/* 3159:3943 */       if (this.antlrTool.nameSpace != null) {
/* 3160:3944 */         nameSpace = new CSharpNameSpace(this.antlrTool.nameSpace.getName());
/* 3161:     */       }
/* 3162:3949 */       if (paramGrammar.hasOption("namespace"))
/* 3163:     */       {
/* 3164:3950 */         localToken = paramGrammar.getOption("namespace");
/* 3165:3951 */         if (localToken != null) {
/* 3166:3952 */           nameSpace = new CSharpNameSpace(localToken.getText());
/* 3167:     */         }
/* 3168:     */       }
/* 3169:     */     }
/* 3170:     */     String str;
/* 3171:3966 */     if ((paramGrammar instanceof ParserGrammar))
/* 3172:     */     {
/* 3173:3967 */       this.labeledElementASTType = "AST";
/* 3174:3968 */       if (paramGrammar.hasOption("ASTLabelType"))
/* 3175:     */       {
/* 3176:3969 */         localToken = paramGrammar.getOption("ASTLabelType");
/* 3177:3970 */         if (localToken != null)
/* 3178:     */         {
/* 3179:3971 */           str = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/* 3180:3972 */           if (str != null)
/* 3181:     */           {
/* 3182:3973 */             this.usingCustomAST = true;
/* 3183:3974 */             this.labeledElementASTType = str;
/* 3184:     */           }
/* 3185:     */         }
/* 3186:     */       }
/* 3187:3978 */       this.labeledElementType = "IToken ";
/* 3188:3979 */       this.labeledElementInit = "null";
/* 3189:3980 */       this.commonExtraArgs = "";
/* 3190:3981 */       this.commonExtraParams = "";
/* 3191:3982 */       this.commonLocalVars = "";
/* 3192:3983 */       this.lt1Value = "LT(1)";
/* 3193:3984 */       this.exceptionThrown = "RecognitionException";
/* 3194:3985 */       this.throwNoViable = "throw new NoViableAltException(LT(1), getFilename());";
/* 3195:     */     }
/* 3196:3987 */     else if ((paramGrammar instanceof LexerGrammar))
/* 3197:     */     {
/* 3198:3988 */       this.labeledElementType = "char ";
/* 3199:3989 */       this.labeledElementInit = "'\\0'";
/* 3200:3990 */       this.commonExtraArgs = "";
/* 3201:3991 */       this.commonExtraParams = "bool _createToken";
/* 3202:3992 */       this.commonLocalVars = "int _ttype; IToken _token=null; int _begin=text.Length;";
/* 3203:3993 */       this.lt1Value = "cached_LA1";
/* 3204:3994 */       this.exceptionThrown = "RecognitionException";
/* 3205:3995 */       this.throwNoViable = "throw new NoViableAltForCharException(cached_LA1, getFilename(), getLine(), getColumn());";
/* 3206:     */     }
/* 3207:3997 */     else if ((paramGrammar instanceof TreeWalkerGrammar))
/* 3208:     */     {
/* 3209:3998 */       this.labeledElementASTType = "AST";
/* 3210:3999 */       this.labeledElementType = "AST";
/* 3211:4000 */       if (paramGrammar.hasOption("ASTLabelType"))
/* 3212:     */       {
/* 3213:4001 */         localToken = paramGrammar.getOption("ASTLabelType");
/* 3214:4002 */         if (localToken != null)
/* 3215:     */         {
/* 3216:4003 */           str = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/* 3217:4004 */           if (str != null)
/* 3218:     */           {
/* 3219:4005 */             this.usingCustomAST = true;
/* 3220:4006 */             this.labeledElementASTType = str;
/* 3221:4007 */             this.labeledElementType = str;
/* 3222:     */           }
/* 3223:     */         }
/* 3224:     */       }
/* 3225:4011 */       if (!paramGrammar.hasOption("ASTLabelType")) {
/* 3226:4012 */         paramGrammar.setOption("ASTLabelType", new Token(6, "AST"));
/* 3227:     */       }
/* 3228:4014 */       this.labeledElementInit = "null";
/* 3229:4015 */       this.commonExtraArgs = "_t";
/* 3230:4016 */       this.commonExtraParams = "AST _t";
/* 3231:4017 */       this.commonLocalVars = "";
/* 3232:4018 */       if (this.usingCustomAST) {
/* 3233:4019 */         this.lt1Value = ("(_t==ASTNULL) ? null : (" + this.labeledElementASTType + ")_t");
/* 3234:     */       } else {
/* 3235:4021 */         this.lt1Value = "_t";
/* 3236:     */       }
/* 3237:4022 */       this.exceptionThrown = "RecognitionException";
/* 3238:4023 */       this.throwNoViable = "throw new NoViableAltException(_t);";
/* 3239:     */     }
/* 3240:     */     else
/* 3241:     */     {
/* 3242:4026 */       this.antlrTool.panic("Unknown grammar type");
/* 3243:     */     }
/* 3244:     */   }
/* 3245:     */   
/* 3246:     */   public void setupOutput(String paramString)
/* 3247:     */     throws IOException
/* 3248:     */   {
/* 3249:4036 */     this.currentOutput = this.antlrTool.openOutputFile(paramString + ".cs");
/* 3250:     */   }
/* 3251:     */   
/* 3252:     */   private static String OctalToUnicode(String paramString)
/* 3253:     */   {
/* 3254:4043 */     if ((4 <= paramString.length()) && ('\'' == paramString.charAt(0)) && ('\\' == paramString.charAt(1)) && ('0' <= paramString.charAt(2)) && ('7' >= paramString.charAt(2)) && ('\'' == paramString.charAt(paramString.length() - 1)))
/* 3255:     */     {
/* 3256:4050 */       Integer localInteger = Integer.valueOf(paramString.substring(2, paramString.length() - 1), 8);
/* 3257:     */       
/* 3258:4052 */       return "'\\x" + Integer.toHexString(localInteger.intValue()) + "'";
/* 3259:     */     }
/* 3260:4055 */     return paramString;
/* 3261:     */   }
/* 3262:     */   
/* 3263:     */   public String getTokenTypesClassName()
/* 3264:     */   {
/* 3265:4064 */     TokenManager localTokenManager = this.grammar.tokenManager;
/* 3266:4065 */     return new String(localTokenManager.getName() + TokenTypesFileSuffix);
/* 3267:     */   }
/* 3268:     */   
/* 3269:     */   private void declareSaveIndexVariableIfNeeded()
/* 3270:     */   {
/* 3271:4070 */     if (this.saveIndexCreateLevel == 0)
/* 3272:     */     {
/* 3273:4072 */       println("int _saveIndex = 0;");
/* 3274:4073 */       this.saveIndexCreateLevel = this.blockNestingLevel;
/* 3275:     */     }
/* 3276:     */   }
/* 3277:     */   
/* 3278:     */   public String[] split(String paramString1, String paramString2)
/* 3279:     */   {
/* 3280:4079 */     StringTokenizer localStringTokenizer = new StringTokenizer(paramString1, paramString2);
/* 3281:4080 */     int i = localStringTokenizer.countTokens();
/* 3282:4081 */     String[] arrayOfString = new String[i];
/* 3283:     */     
/* 3284:4083 */     int j = 0;
/* 3285:4084 */     while (localStringTokenizer.hasMoreTokens())
/* 3286:     */     {
/* 3287:4086 */       arrayOfString[j] = localStringTokenizer.nextToken();
/* 3288:4087 */       j++;
/* 3289:     */     }
/* 3290:4089 */     return arrayOfString;
/* 3291:     */   }
/* 3292:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.CSharpCodeGenerator
 * JD-Core Version:    0.7.0.1
 */