/*    1:     */ package antlr;
/*    2:     */ 
/*    3:     */ import antlr.actions.java.ActionLexer;
/*    4:     */ import antlr.collections.impl.BitSet;
/*    5:     */ import antlr.collections.impl.Vector;
/*    6:     */ import java.io.IOException;
/*    7:     */ import java.io.PrintStream;
/*    8:     */ import java.util.Enumeration;
/*    9:     */ import java.util.Hashtable;
/*   10:     */ 
/*   11:     */ public class JavaCodeGenerator
/*   12:     */   extends CodeGenerator
/*   13:     */ {
/*   14:     */   public static final int NO_MAPPING = -999;
/*   15:     */   public static final int CONTINUE_LAST_MAPPING = -888;
/*   16:     */   private JavaCodeGeneratorPrintWriterManager printWriterManager;
/*   17:  30 */   private int defaultLine = -999;
/*   18:  32 */   protected int syntacticPredLevel = 0;
/*   19:  35 */   protected boolean genAST = false;
/*   20:  38 */   protected boolean saveText = false;
/*   21:     */   String labeledElementType;
/*   22:     */   String labeledElementASTType;
/*   23:     */   String labeledElementInit;
/*   24:     */   String commonExtraArgs;
/*   25:     */   String commonExtraParams;
/*   26:     */   String commonLocalVars;
/*   27:     */   String lt1Value;
/*   28:     */   String exceptionThrown;
/*   29:     */   String throwNoViable;
/*   30:     */   RuleBlock currentRule;
/*   31:     */   String currentASTResult;
/*   32:  62 */   Hashtable treeVariableMap = new Hashtable();
/*   33:  67 */   Hashtable declaredASTVariables = new Hashtable();
/*   34:  70 */   int astVarNumber = 1;
/*   35:  73 */   protected static final String NONUNIQUE = new String();
/*   36:     */   public static final int caseSizeThreshold = 127;
/*   37:     */   private Vector semPreds;
/*   38:     */   
/*   39:     */   public JavaCodeGenerator()
/*   40:     */   {
/*   41:  85 */     this.charFormatter = new JavaCharFormatter();
/*   42:     */   }
/*   43:     */   
/*   44:     */   protected void printAction(String paramString)
/*   45:     */   {
/*   46:  89 */     printAction(paramString, this.defaultLine);
/*   47:     */   }
/*   48:     */   
/*   49:     */   protected void printAction(String paramString, int paramInt)
/*   50:     */   {
/*   51:  92 */     getPrintWriterManager().startMapping(paramInt);
/*   52:  93 */     super.printAction(paramString);
/*   53:  94 */     getPrintWriterManager().endMapping();
/*   54:     */   }
/*   55:     */   
/*   56:     */   public void println(String paramString)
/*   57:     */   {
/*   58:  98 */     println(paramString, this.defaultLine);
/*   59:     */   }
/*   60:     */   
/*   61:     */   public void println(String paramString, int paramInt)
/*   62:     */   {
/*   63: 101 */     if ((paramInt > 0) || (paramInt == -888)) {
/*   64: 102 */       getPrintWriterManager().startSingleSourceLineMapping(paramInt);
/*   65:     */     }
/*   66: 103 */     super.println(paramString);
/*   67: 104 */     if ((paramInt > 0) || (paramInt == -888)) {
/*   68: 105 */       getPrintWriterManager().endMapping();
/*   69:     */     }
/*   70:     */   }
/*   71:     */   
/*   72:     */   protected void print(String paramString)
/*   73:     */   {
/*   74: 109 */     print(paramString, this.defaultLine);
/*   75:     */   }
/*   76:     */   
/*   77:     */   protected void print(String paramString, int paramInt)
/*   78:     */   {
/*   79: 112 */     if ((paramInt > 0) || (paramInt == -888)) {
/*   80: 113 */       getPrintWriterManager().startMapping(paramInt);
/*   81:     */     }
/*   82: 114 */     super.print(paramString);
/*   83: 115 */     if ((paramInt > 0) || (paramInt == -888)) {
/*   84: 116 */       getPrintWriterManager().endMapping();
/*   85:     */     }
/*   86:     */   }
/*   87:     */   
/*   88:     */   protected void _print(String paramString)
/*   89:     */   {
/*   90: 120 */     _print(paramString, this.defaultLine);
/*   91:     */   }
/*   92:     */   
/*   93:     */   protected void _print(String paramString, int paramInt)
/*   94:     */   {
/*   95: 123 */     if ((paramInt > 0) || (paramInt == -888)) {
/*   96: 124 */       getPrintWriterManager().startMapping(paramInt);
/*   97:     */     }
/*   98: 125 */     super._print(paramString);
/*   99: 126 */     if ((paramInt > 0) || (paramInt == -888)) {
/*  100: 127 */       getPrintWriterManager().endMapping();
/*  101:     */     }
/*  102:     */   }
/*  103:     */   
/*  104:     */   protected void _println(String paramString)
/*  105:     */   {
/*  106: 131 */     _println(paramString, this.defaultLine);
/*  107:     */   }
/*  108:     */   
/*  109:     */   protected void _println(String paramString, int paramInt)
/*  110:     */   {
/*  111: 134 */     if ((paramInt > 0) || (paramInt == -888)) {
/*  112: 135 */       getPrintWriterManager().startMapping(paramInt);
/*  113:     */     }
/*  114: 136 */     super._println(paramString);
/*  115: 137 */     if ((paramInt > 0) || (paramInt == -888)) {
/*  116: 138 */       getPrintWriterManager().endMapping();
/*  117:     */     }
/*  118:     */   }
/*  119:     */   
/*  120:     */   protected int addSemPred(String paramString)
/*  121:     */   {
/*  122: 147 */     this.semPreds.appendElement(paramString);
/*  123: 148 */     return this.semPreds.size() - 1;
/*  124:     */   }
/*  125:     */   
/*  126:     */   public void exitIfError()
/*  127:     */   {
/*  128: 152 */     if (this.antlrTool.hasError()) {
/*  129: 153 */       this.antlrTool.fatalError("Exiting due to errors.");
/*  130:     */     }
/*  131:     */   }
/*  132:     */   
/*  133:     */   public void gen()
/*  134:     */   {
/*  135:     */     try
/*  136:     */     {
/*  137: 162 */       Enumeration localEnumeration = this.behavior.grammars.elements();
/*  138: 163 */       while (localEnumeration.hasMoreElements())
/*  139:     */       {
/*  140: 164 */         localObject = (Grammar)localEnumeration.nextElement();
/*  141:     */         
/*  142: 166 */         ((Grammar)localObject).setGrammarAnalyzer(this.analyzer);
/*  143: 167 */         ((Grammar)localObject).setCodeGenerator(this);
/*  144: 168 */         this.analyzer.setGrammar((Grammar)localObject);
/*  145:     */         
/*  146: 170 */         setupGrammarParameters((Grammar)localObject);
/*  147: 171 */         ((Grammar)localObject).generate();
/*  148:     */         
/*  149:     */ 
/*  150: 174 */         exitIfError();
/*  151:     */       }
/*  152: 178 */       Object localObject = this.behavior.tokenManagers.elements();
/*  153: 179 */       while (((Enumeration)localObject).hasMoreElements())
/*  154:     */       {
/*  155: 180 */         TokenManager localTokenManager = (TokenManager)((Enumeration)localObject).nextElement();
/*  156: 181 */         if (!localTokenManager.isReadOnly())
/*  157:     */         {
/*  158: 185 */           genTokenTypes(localTokenManager);
/*  159:     */           
/*  160: 187 */           genTokenInterchange(localTokenManager);
/*  161:     */         }
/*  162: 189 */         exitIfError();
/*  163:     */       }
/*  164:     */     }
/*  165:     */     catch (IOException localIOException)
/*  166:     */     {
/*  167: 193 */       this.antlrTool.reportException(localIOException, null);
/*  168:     */     }
/*  169:     */   }
/*  170:     */   
/*  171:     */   public void gen(ActionElement paramActionElement)
/*  172:     */   {
/*  173: 201 */     int i = this.defaultLine;
/*  174:     */     try
/*  175:     */     {
/*  176: 203 */       this.defaultLine = paramActionElement.getLine();
/*  177: 204 */       if (this.DEBUG_CODE_GENERATOR) {
/*  178: 204 */         System.out.println("genAction(" + paramActionElement + ")");
/*  179:     */       }
/*  180: 205 */       if (paramActionElement.isSemPred)
/*  181:     */       {
/*  182: 206 */         genSemPred(paramActionElement.actionText, paramActionElement.line);
/*  183:     */       }
/*  184:     */       else
/*  185:     */       {
/*  186: 209 */         if (this.grammar.hasSyntacticPredicate)
/*  187:     */         {
/*  188: 210 */           println("if ( inputState.guessing==0 ) {");
/*  189: 211 */           this.tabs += 1;
/*  190:     */         }
/*  191: 216 */         ActionTransInfo localActionTransInfo = new ActionTransInfo();
/*  192: 217 */         String str = processActionForSpecialSymbols(paramActionElement.actionText, paramActionElement.getLine(), this.currentRule, localActionTransInfo);
/*  193: 222 */         if (localActionTransInfo.refRuleRoot != null) {
/*  194: 227 */           println(localActionTransInfo.refRuleRoot + " = (" + this.labeledElementASTType + ")currentAST.root;");
/*  195:     */         }
/*  196: 231 */         printAction(str);
/*  197: 233 */         if (localActionTransInfo.assignToRoot)
/*  198:     */         {
/*  199: 235 */           println("currentAST.root = " + localActionTransInfo.refRuleRoot + ";");
/*  200:     */           
/*  201: 237 */           println("currentAST.child = " + localActionTransInfo.refRuleRoot + "!=null &&" + localActionTransInfo.refRuleRoot + ".getFirstChild()!=null ?", -999);
/*  202: 238 */           this.tabs += 1;
/*  203: 239 */           println(localActionTransInfo.refRuleRoot + ".getFirstChild() : " + localActionTransInfo.refRuleRoot + ";");
/*  204: 240 */           this.tabs -= 1;
/*  205: 241 */           println("currentAST.advanceChildToEnd();");
/*  206:     */         }
/*  207: 244 */         if (this.grammar.hasSyntacticPredicate)
/*  208:     */         {
/*  209: 245 */           this.tabs -= 1;
/*  210: 246 */           println("}", -999);
/*  211:     */         }
/*  212:     */       }
/*  213:     */     }
/*  214:     */     finally
/*  215:     */     {
/*  216: 250 */       this.defaultLine = i;
/*  217:     */     }
/*  218:     */   }
/*  219:     */   
/*  220:     */   public void gen(AlternativeBlock paramAlternativeBlock)
/*  221:     */   {
/*  222: 258 */     if (this.DEBUG_CODE_GENERATOR) {
/*  223: 258 */       System.out.println("gen(" + paramAlternativeBlock + ")");
/*  224:     */     }
/*  225: 259 */     println("{", -999);
/*  226: 260 */     genBlockPreamble(paramAlternativeBlock);
/*  227: 261 */     genBlockInitAction(paramAlternativeBlock);
/*  228:     */     
/*  229:     */ 
/*  230: 264 */     String str = this.currentASTResult;
/*  231: 265 */     if (paramAlternativeBlock.getLabel() != null) {
/*  232: 266 */       this.currentASTResult = paramAlternativeBlock.getLabel();
/*  233:     */     }
/*  234: 269 */     boolean bool = this.grammar.theLLkAnalyzer.deterministic(paramAlternativeBlock);
/*  235:     */     
/*  236: 271 */     JavaBlockFinishingInfo localJavaBlockFinishingInfo = genCommonBlock(paramAlternativeBlock, true);
/*  237: 272 */     genBlockFinish(localJavaBlockFinishingInfo, this.throwNoViable, paramAlternativeBlock.getLine());
/*  238:     */     
/*  239: 274 */     println("}", -999);
/*  240:     */     
/*  241:     */ 
/*  242: 277 */     this.currentASTResult = str;
/*  243:     */   }
/*  244:     */   
/*  245:     */   public void gen(BlockEndElement paramBlockEndElement)
/*  246:     */   {
/*  247: 286 */     if (this.DEBUG_CODE_GENERATOR) {
/*  248: 286 */       System.out.println("genRuleEnd(" + paramBlockEndElement + ")");
/*  249:     */     }
/*  250:     */   }
/*  251:     */   
/*  252:     */   public void gen(CharLiteralElement paramCharLiteralElement)
/*  253:     */   {
/*  254: 293 */     if (this.DEBUG_CODE_GENERATOR) {
/*  255: 293 */       System.out.println("genChar(" + paramCharLiteralElement + ")");
/*  256:     */     }
/*  257: 295 */     if (paramCharLiteralElement.getLabel() != null) {
/*  258: 296 */       println(paramCharLiteralElement.getLabel() + " = " + this.lt1Value + ";", paramCharLiteralElement.getLine());
/*  259:     */     }
/*  260: 299 */     boolean bool = this.saveText;
/*  261: 300 */     this.saveText = ((this.saveText) && (paramCharLiteralElement.getAutoGenType() == 1));
/*  262: 301 */     genMatch(paramCharLiteralElement);
/*  263: 302 */     this.saveText = bool;
/*  264:     */   }
/*  265:     */   
/*  266:     */   public void gen(CharRangeElement paramCharRangeElement)
/*  267:     */   {
/*  268: 309 */     int i = this.defaultLine;
/*  269:     */     try
/*  270:     */     {
/*  271: 311 */       this.defaultLine = paramCharRangeElement.getLine();
/*  272: 312 */       if ((paramCharRangeElement.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/*  273: 313 */         println(paramCharRangeElement.getLabel() + " = " + this.lt1Value + ";");
/*  274:     */       }
/*  275: 315 */       int j = ((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramCharRangeElement.getAutoGenType() == 3)) ? 1 : 0;
/*  276: 319 */       if (j != 0) {
/*  277: 320 */         println("_saveIndex=text.length();");
/*  278:     */       }
/*  279: 323 */       println("matchRange(" + paramCharRangeElement.beginText + "," + paramCharRangeElement.endText + ");");
/*  280: 325 */       if (j != 0) {
/*  281: 326 */         println("text.setLength(_saveIndex);");
/*  282:     */       }
/*  283:     */     }
/*  284:     */     finally
/*  285:     */     {
/*  286: 329 */       this.defaultLine = i;
/*  287:     */     }
/*  288:     */   }
/*  289:     */   
/*  290:     */   public void gen(LexerGrammar paramLexerGrammar)
/*  291:     */     throws IOException
/*  292:     */   {
/*  293: 335 */     int i = this.defaultLine;
/*  294:     */     try
/*  295:     */     {
/*  296: 337 */       this.defaultLine = -999;
/*  297: 339 */       if (paramLexerGrammar.debuggingOutput) {
/*  298: 340 */         this.semPreds = new Vector();
/*  299:     */       }
/*  300: 342 */       setGrammar(paramLexerGrammar);
/*  301: 343 */       if (!(this.grammar instanceof LexerGrammar)) {
/*  302: 344 */         this.antlrTool.panic("Internal error generating lexer");
/*  303:     */       }
/*  304: 349 */       this.currentOutput = getPrintWriterManager().setupOutput(this.antlrTool, this.grammar);
/*  305:     */       
/*  306: 351 */       this.genAST = false;
/*  307: 352 */       this.saveText = true;
/*  308:     */       
/*  309: 354 */       this.tabs = 0;
/*  310:     */       
/*  311:     */ 
/*  312: 357 */       genHeader();
/*  313:     */       try
/*  314:     */       {
/*  315: 361 */         this.defaultLine = this.behavior.getHeaderActionLine("");
/*  316: 362 */         println(this.behavior.getHeaderAction(""));
/*  317:     */       }
/*  318:     */       finally
/*  319:     */       {
/*  320: 364 */         this.defaultLine = -999;
/*  321:     */       }
/*  322: 369 */       println("import java.io.InputStream;");
/*  323: 370 */       println("import antlr.TokenStreamException;");
/*  324: 371 */       println("import antlr.TokenStreamIOException;");
/*  325: 372 */       println("import antlr.TokenStreamRecognitionException;");
/*  326: 373 */       println("import antlr.CharStreamException;");
/*  327: 374 */       println("import antlr.CharStreamIOException;");
/*  328: 375 */       println("import antlr.ANTLRException;");
/*  329: 376 */       println("import java.io.Reader;");
/*  330: 377 */       println("import java.util.Hashtable;");
/*  331: 378 */       println("import antlr." + this.grammar.getSuperClass() + ";");
/*  332: 379 */       println("import antlr.InputBuffer;");
/*  333: 380 */       println("import antlr.ByteBuffer;");
/*  334: 381 */       println("import antlr.CharBuffer;");
/*  335: 382 */       println("import antlr.Token;");
/*  336: 383 */       println("import antlr.CommonToken;");
/*  337: 384 */       println("import antlr.RecognitionException;");
/*  338: 385 */       println("import antlr.NoViableAltForCharException;");
/*  339: 386 */       println("import antlr.MismatchedCharException;");
/*  340: 387 */       println("import antlr.TokenStream;");
/*  341: 388 */       println("import antlr.ANTLRHashString;");
/*  342: 389 */       println("import antlr.LexerSharedInputState;");
/*  343: 390 */       println("import antlr.collections.impl.BitSet;");
/*  344: 391 */       println("import antlr.SemanticException;");
/*  345:     */       
/*  346:     */ 
/*  347: 394 */       println(this.grammar.preambleAction.getText());
/*  348:     */       
/*  349:     */ 
/*  350: 397 */       String str = null;
/*  351: 398 */       if (this.grammar.superClass != null) {
/*  352: 399 */         str = this.grammar.superClass;
/*  353:     */       } else {
/*  354: 402 */         str = "antlr." + this.grammar.getSuperClass();
/*  355:     */       }
/*  356: 406 */       if (this.grammar.comment != null) {
/*  357: 407 */         _println(this.grammar.comment);
/*  358:     */       }
/*  359: 411 */       Object localObject2 = "public";
/*  360: 412 */       Token localToken = (Token)this.grammar.options.get("classHeaderPrefix");
/*  361: 413 */       if (localToken != null)
/*  362:     */       {
/*  363: 414 */         localObject3 = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/*  364: 415 */         if (localObject3 != null) {
/*  365: 416 */           localObject2 = localObject3;
/*  366:     */         }
/*  367:     */       }
/*  368: 420 */       print((String)localObject2 + " ");
/*  369: 421 */       print("class " + this.grammar.getClassName() + " extends " + str);
/*  370: 422 */       println(" implements " + this.grammar.tokenManager.getName() + TokenTypesFileSuffix + ", TokenStream");
/*  371: 423 */       Object localObject3 = (Token)this.grammar.options.get("classHeaderSuffix");
/*  372: 424 */       if (localObject3 != null)
/*  373:     */       {
/*  374: 425 */         localObject4 = StringUtils.stripFrontBack(((Token)localObject3).getText(), "\"", "\"");
/*  375: 426 */         if (localObject4 != null) {
/*  376: 427 */           print(", " + (String)localObject4);
/*  377:     */         }
/*  378:     */       }
/*  379: 430 */       println(" {");
/*  380:     */       
/*  381:     */ 
/*  382: 433 */       print(processActionForSpecialSymbols(this.grammar.classMemberAction.getText(), this.grammar.classMemberAction.getLine(), this.currentRule, null), this.grammar.classMemberAction.getLine());
/*  383:     */       
/*  384:     */ 
/*  385:     */ 
/*  386:     */ 
/*  387:     */ 
/*  388:     */ 
/*  389:     */ 
/*  390:     */ 
/*  391: 442 */       println("public " + this.grammar.getClassName() + "(InputStream in) {");
/*  392: 443 */       this.tabs += 1;
/*  393: 444 */       println("this(new ByteBuffer(in));");
/*  394: 445 */       this.tabs -= 1;
/*  395: 446 */       println("}");
/*  396:     */       
/*  397:     */ 
/*  398:     */ 
/*  399:     */ 
/*  400:     */ 
/*  401: 452 */       println("public " + this.grammar.getClassName() + "(Reader in) {");
/*  402: 453 */       this.tabs += 1;
/*  403: 454 */       println("this(new CharBuffer(in));");
/*  404: 455 */       this.tabs -= 1;
/*  405: 456 */       println("}");
/*  406:     */       
/*  407: 458 */       println("public " + this.grammar.getClassName() + "(InputBuffer ib) {");
/*  408: 459 */       this.tabs += 1;
/*  409: 461 */       if (this.grammar.debuggingOutput) {
/*  410: 462 */         println("this(new LexerSharedInputState(new antlr.debug.DebuggingInputBuffer(ib)));");
/*  411:     */       } else {
/*  412: 464 */         println("this(new LexerSharedInputState(ib));");
/*  413:     */       }
/*  414: 465 */       this.tabs -= 1;
/*  415: 466 */       println("}");
/*  416:     */       
/*  417:     */ 
/*  418:     */ 
/*  419:     */ 
/*  420: 471 */       println("public " + this.grammar.getClassName() + "(LexerSharedInputState state) {");
/*  421: 472 */       this.tabs += 1;
/*  422:     */       
/*  423: 474 */       println("super(state);");
/*  424: 477 */       if (this.grammar.debuggingOutput)
/*  425:     */       {
/*  426: 478 */         println("  ruleNames  = _ruleNames;");
/*  427: 479 */         println("  semPredNames = _semPredNames;");
/*  428: 480 */         println("  setupDebugging();");
/*  429:     */       }
/*  430: 486 */       println("caseSensitiveLiterals = " + paramLexerGrammar.caseSensitiveLiterals + ";");
/*  431: 487 */       println("setCaseSensitive(" + paramLexerGrammar.caseSensitive + ");");
/*  432:     */       
/*  433:     */ 
/*  434:     */ 
/*  435:     */ 
/*  436: 492 */       println("literals = new Hashtable();");
/*  437: 493 */       Object localObject4 = this.grammar.tokenManager.getTokenSymbolKeys();
/*  438:     */       Object localObject6;
/*  439: 494 */       while (((Enumeration)localObject4).hasMoreElements())
/*  440:     */       {
/*  441: 495 */         localObject5 = (String)((Enumeration)localObject4).nextElement();
/*  442: 496 */         if (((String)localObject5).charAt(0) == '"')
/*  443:     */         {
/*  444: 499 */           TokenSymbol localTokenSymbol = this.grammar.tokenManager.getTokenSymbol((String)localObject5);
/*  445: 500 */           if ((localTokenSymbol instanceof StringLiteralSymbol))
/*  446:     */           {
/*  447: 501 */             localObject6 = (StringLiteralSymbol)localTokenSymbol;
/*  448: 502 */             println("literals.put(new ANTLRHashString(" + ((StringLiteralSymbol)localObject6).getId() + ", this), new Integer(" + ((StringLiteralSymbol)localObject6).getTokenType() + "));");
/*  449:     */           }
/*  450:     */         }
/*  451:     */       }
/*  452: 505 */       this.tabs -= 1;
/*  453:     */       
/*  454:     */ 
/*  455: 508 */       println("}");
/*  456: 511 */       if (this.grammar.debuggingOutput)
/*  457:     */       {
/*  458: 512 */         println("private static final String _ruleNames[] = {");
/*  459:     */         
/*  460: 514 */         localObject5 = this.grammar.rules.elements();
/*  461: 515 */         j = 0;
/*  462: 516 */         while (((Enumeration)localObject5).hasMoreElements())
/*  463:     */         {
/*  464: 517 */           localObject6 = (GrammarSymbol)((Enumeration)localObject5).nextElement();
/*  465: 518 */           if ((localObject6 instanceof RuleSymbol)) {
/*  466: 519 */             println("  \"" + ((RuleSymbol)localObject6).getId() + "\",");
/*  467:     */           }
/*  468:     */         }
/*  469: 521 */         println("};");
/*  470:     */       }
/*  471: 527 */       genNextToken();
/*  472:     */       
/*  473:     */ 
/*  474: 530 */       Object localObject5 = this.grammar.rules.elements();
/*  475: 531 */       int j = 0;
/*  476: 532 */       while (((Enumeration)localObject5).hasMoreElements())
/*  477:     */       {
/*  478: 533 */         localObject6 = (RuleSymbol)((Enumeration)localObject5).nextElement();
/*  479: 535 */         if (!((RuleSymbol)localObject6).getId().equals("mnextToken")) {
/*  480: 536 */           genRule((RuleSymbol)localObject6, false, j++);
/*  481:     */         }
/*  482: 538 */         exitIfError();
/*  483:     */       }
/*  484: 542 */       if (this.grammar.debuggingOutput) {
/*  485: 543 */         genSemPredMap();
/*  486:     */       }
/*  487: 546 */       genBitsets(this.bitsetsUsed, ((LexerGrammar)this.grammar).charVocabulary.size());
/*  488:     */       
/*  489: 548 */       println("");
/*  490: 549 */       println("}");
/*  491:     */       
/*  492:     */ 
/*  493: 552 */       getPrintWriterManager().finishOutput();
/*  494:     */     }
/*  495:     */     finally
/*  496:     */     {
/*  497: 554 */       this.defaultLine = i;
/*  498:     */     }
/*  499:     */   }
/*  500:     */   
/*  501:     */   public void gen(OneOrMoreBlock paramOneOrMoreBlock)
/*  502:     */   {
/*  503: 562 */     int i = this.defaultLine;
/*  504:     */     try
/*  505:     */     {
/*  506: 564 */       this.defaultLine = paramOneOrMoreBlock.getLine();
/*  507: 565 */       if (this.DEBUG_CODE_GENERATOR) {
/*  508: 565 */         System.out.println("gen+(" + paramOneOrMoreBlock + ")");
/*  509:     */       }
/*  510: 568 */       println("{", -999);
/*  511: 569 */       genBlockPreamble(paramOneOrMoreBlock);
/*  512:     */       String str2;
/*  513: 570 */       if (paramOneOrMoreBlock.getLabel() != null) {
/*  514: 571 */         str2 = "_cnt_" + paramOneOrMoreBlock.getLabel();
/*  515:     */       } else {
/*  516: 574 */         str2 = "_cnt" + paramOneOrMoreBlock.ID;
/*  517:     */       }
/*  518: 576 */       println("int " + str2 + "=0;");
/*  519:     */       String str1;
/*  520: 577 */       if (paramOneOrMoreBlock.getLabel() != null) {
/*  521: 578 */         str1 = paramOneOrMoreBlock.getLabel();
/*  522:     */       } else {
/*  523: 581 */         str1 = "_loop" + paramOneOrMoreBlock.ID;
/*  524:     */       }
/*  525: 583 */       println(str1 + ":");
/*  526: 584 */       println("do {");
/*  527: 585 */       this.tabs += 1;
/*  528:     */       
/*  529:     */ 
/*  530: 588 */       genBlockInitAction(paramOneOrMoreBlock);
/*  531:     */       
/*  532:     */ 
/*  533: 591 */       String str3 = this.currentASTResult;
/*  534: 592 */       if (paramOneOrMoreBlock.getLabel() != null) {
/*  535: 593 */         this.currentASTResult = paramOneOrMoreBlock.getLabel();
/*  536:     */       }
/*  537: 596 */       boolean bool = this.grammar.theLLkAnalyzer.deterministic(paramOneOrMoreBlock);
/*  538:     */       
/*  539:     */ 
/*  540:     */ 
/*  541:     */ 
/*  542:     */ 
/*  543:     */ 
/*  544:     */ 
/*  545:     */ 
/*  546:     */ 
/*  547:     */ 
/*  548:     */ 
/*  549: 608 */       int j = 0;
/*  550: 609 */       int k = this.grammar.maxk;
/*  551: 611 */       if ((!paramOneOrMoreBlock.greedy) && (paramOneOrMoreBlock.exitLookaheadDepth <= this.grammar.maxk) && (paramOneOrMoreBlock.exitCache[paramOneOrMoreBlock.exitLookaheadDepth].containsEpsilon()))
/*  552:     */       {
/*  553: 614 */         j = 1;
/*  554: 615 */         k = paramOneOrMoreBlock.exitLookaheadDepth;
/*  555:     */       }
/*  556: 617 */       else if ((!paramOneOrMoreBlock.greedy) && (paramOneOrMoreBlock.exitLookaheadDepth == 2147483647))
/*  557:     */       {
/*  558: 619 */         j = 1;
/*  559:     */       }
/*  560: 624 */       if (j != 0)
/*  561:     */       {
/*  562: 625 */         if (this.DEBUG_CODE_GENERATOR) {
/*  563: 626 */           System.out.println("nongreedy (...)+ loop; exit depth is " + paramOneOrMoreBlock.exitLookaheadDepth);
/*  564:     */         }
/*  565: 629 */         localObject1 = getLookaheadTestExpression(paramOneOrMoreBlock.exitCache, k);
/*  566:     */         
/*  567:     */ 
/*  568: 632 */         println("// nongreedy exit test", -999);
/*  569: 633 */         println("if ( " + str2 + ">=1 && " + (String)localObject1 + ") break " + str1 + ";", -888);
/*  570:     */       }
/*  571: 636 */       Object localObject1 = genCommonBlock(paramOneOrMoreBlock, false);
/*  572: 637 */       genBlockFinish((JavaBlockFinishingInfo)localObject1, "if ( " + str2 + ">=1 ) { break " + str1 + "; } else {" + this.throwNoViable + "}", paramOneOrMoreBlock.getLine());
/*  573:     */       
/*  574:     */ 
/*  575:     */ 
/*  576:     */ 
/*  577:     */ 
/*  578: 643 */       println(str2 + "++;");
/*  579: 644 */       this.tabs -= 1;
/*  580: 645 */       println("} while (true);");
/*  581: 646 */       println("}");
/*  582:     */       
/*  583:     */ 
/*  584: 649 */       this.currentASTResult = str3;
/*  585:     */     }
/*  586:     */     finally
/*  587:     */     {
/*  588: 651 */       this.defaultLine = i;
/*  589:     */     }
/*  590:     */   }
/*  591:     */   
/*  592:     */   public void gen(ParserGrammar paramParserGrammar)
/*  593:     */     throws IOException
/*  594:     */   {
/*  595: 657 */     int i = this.defaultLine;
/*  596:     */     try
/*  597:     */     {
/*  598: 659 */       this.defaultLine = -999;
/*  599: 662 */       if (paramParserGrammar.debuggingOutput) {
/*  600: 663 */         this.semPreds = new Vector();
/*  601:     */       }
/*  602: 665 */       setGrammar(paramParserGrammar);
/*  603: 666 */       if (!(this.grammar instanceof ParserGrammar)) {
/*  604: 667 */         this.antlrTool.panic("Internal error generating parser");
/*  605:     */       }
/*  606: 672 */       this.currentOutput = getPrintWriterManager().setupOutput(this.antlrTool, this.grammar);
/*  607:     */       
/*  608: 674 */       this.genAST = this.grammar.buildAST;
/*  609:     */       
/*  610: 676 */       this.tabs = 0;
/*  611:     */       
/*  612:     */ 
/*  613: 679 */       genHeader();
/*  614:     */       try
/*  615:     */       {
/*  616: 682 */         this.defaultLine = this.behavior.getHeaderActionLine("");
/*  617: 683 */         println(this.behavior.getHeaderAction(""));
/*  618:     */       }
/*  619:     */       finally
/*  620:     */       {
/*  621: 685 */         this.defaultLine = -999;
/*  622:     */       }
/*  623: 689 */       println("import antlr.TokenBuffer;");
/*  624: 690 */       println("import antlr.TokenStreamException;");
/*  625: 691 */       println("import antlr.TokenStreamIOException;");
/*  626: 692 */       println("import antlr.ANTLRException;");
/*  627: 693 */       println("import antlr." + this.grammar.getSuperClass() + ";");
/*  628: 694 */       println("import antlr.Token;");
/*  629: 695 */       println("import antlr.TokenStream;");
/*  630: 696 */       println("import antlr.RecognitionException;");
/*  631: 697 */       println("import antlr.NoViableAltException;");
/*  632: 698 */       println("import antlr.MismatchedTokenException;");
/*  633: 699 */       println("import antlr.SemanticException;");
/*  634: 700 */       println("import antlr.ParserSharedInputState;");
/*  635: 701 */       println("import antlr.collections.impl.BitSet;");
/*  636: 702 */       if (this.genAST)
/*  637:     */       {
/*  638: 703 */         println("import antlr.collections.AST;");
/*  639: 704 */         println("import java.util.Hashtable;");
/*  640: 705 */         println("import antlr.ASTFactory;");
/*  641: 706 */         println("import antlr.ASTPair;");
/*  642: 707 */         println("import antlr.collections.impl.ASTArray;");
/*  643:     */       }
/*  644: 711 */       println(this.grammar.preambleAction.getText());
/*  645:     */       
/*  646:     */ 
/*  647: 714 */       String str = null;
/*  648: 715 */       if (this.grammar.superClass != null) {
/*  649: 716 */         str = this.grammar.superClass;
/*  650:     */       } else {
/*  651: 718 */         str = "antlr." + this.grammar.getSuperClass();
/*  652:     */       }
/*  653: 721 */       if (this.grammar.comment != null) {
/*  654: 722 */         _println(this.grammar.comment);
/*  655:     */       }
/*  656: 726 */       Object localObject2 = "public";
/*  657: 727 */       Token localToken = (Token)this.grammar.options.get("classHeaderPrefix");
/*  658: 728 */       if (localToken != null)
/*  659:     */       {
/*  660: 729 */         localObject3 = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/*  661: 730 */         if (localObject3 != null) {
/*  662: 731 */           localObject2 = localObject3;
/*  663:     */         }
/*  664:     */       }
/*  665: 735 */       print((String)localObject2 + " ");
/*  666: 736 */       print("class " + this.grammar.getClassName() + " extends " + str);
/*  667: 737 */       println("       implements " + this.grammar.tokenManager.getName() + TokenTypesFileSuffix);
/*  668:     */       
/*  669: 739 */       Object localObject3 = (Token)this.grammar.options.get("classHeaderSuffix");
/*  670: 740 */       if (localObject3 != null)
/*  671:     */       {
/*  672: 741 */         localObject4 = StringUtils.stripFrontBack(((Token)localObject3).getText(), "\"", "\"");
/*  673: 742 */         if (localObject4 != null) {
/*  674: 743 */           print(", " + (String)localObject4);
/*  675:     */         }
/*  676:     */       }
/*  677: 745 */       println(" {");
/*  678:     */       GrammarSymbol localGrammarSymbol;
/*  679: 749 */       if (this.grammar.debuggingOutput)
/*  680:     */       {
/*  681: 750 */         println("private static final String _ruleNames[] = {");
/*  682:     */         
/*  683: 752 */         localObject4 = this.grammar.rules.elements();
/*  684: 753 */         j = 0;
/*  685: 754 */         while (((Enumeration)localObject4).hasMoreElements())
/*  686:     */         {
/*  687: 755 */           localGrammarSymbol = (GrammarSymbol)((Enumeration)localObject4).nextElement();
/*  688: 756 */           if ((localGrammarSymbol instanceof RuleSymbol)) {
/*  689: 757 */             println("  \"" + ((RuleSymbol)localGrammarSymbol).getId() + "\",");
/*  690:     */           }
/*  691:     */         }
/*  692: 759 */         println("};");
/*  693:     */       }
/*  694: 763 */       print(processActionForSpecialSymbols(this.grammar.classMemberAction.getText(), this.grammar.classMemberAction.getLine(), this.currentRule, null), this.grammar.classMemberAction.getLine());
/*  695:     */       
/*  696:     */ 
/*  697:     */ 
/*  698:     */ 
/*  699:     */ 
/*  700: 769 */       println("");
/*  701: 770 */       println("protected " + this.grammar.getClassName() + "(TokenBuffer tokenBuf, int k) {");
/*  702: 771 */       println("  super(tokenBuf,k);");
/*  703: 772 */       println("  tokenNames = _tokenNames;");
/*  704: 775 */       if (this.grammar.debuggingOutput)
/*  705:     */       {
/*  706: 776 */         println("  ruleNames  = _ruleNames;");
/*  707: 777 */         println("  semPredNames = _semPredNames;");
/*  708: 778 */         println("  setupDebugging(tokenBuf);");
/*  709:     */       }
/*  710: 780 */       if (this.grammar.buildAST)
/*  711:     */       {
/*  712: 781 */         println("  buildTokenTypeASTClassMap();");
/*  713: 782 */         println("  astFactory = new ASTFactory(getTokenTypeToASTClassMap());");
/*  714:     */       }
/*  715: 784 */       println("}");
/*  716: 785 */       println("");
/*  717:     */       
/*  718: 787 */       println("public " + this.grammar.getClassName() + "(TokenBuffer tokenBuf) {");
/*  719: 788 */       println("  this(tokenBuf," + this.grammar.maxk + ");");
/*  720: 789 */       println("}");
/*  721: 790 */       println("");
/*  722:     */       
/*  723:     */ 
/*  724: 793 */       println("protected " + this.grammar.getClassName() + "(TokenStream lexer, int k) {");
/*  725: 794 */       println("  super(lexer,k);");
/*  726: 795 */       println("  tokenNames = _tokenNames;");
/*  727: 799 */       if (this.grammar.debuggingOutput)
/*  728:     */       {
/*  729: 800 */         println("  ruleNames  = _ruleNames;");
/*  730: 801 */         println("  semPredNames = _semPredNames;");
/*  731: 802 */         println("  setupDebugging(lexer);");
/*  732:     */       }
/*  733: 804 */       if (this.grammar.buildAST)
/*  734:     */       {
/*  735: 805 */         println("  buildTokenTypeASTClassMap();");
/*  736: 806 */         println("  astFactory = new ASTFactory(getTokenTypeToASTClassMap());");
/*  737:     */       }
/*  738: 808 */       println("}");
/*  739: 809 */       println("");
/*  740:     */       
/*  741: 811 */       println("public " + this.grammar.getClassName() + "(TokenStream lexer) {");
/*  742: 812 */       println("  this(lexer," + this.grammar.maxk + ");");
/*  743: 813 */       println("}");
/*  744: 814 */       println("");
/*  745:     */       
/*  746: 816 */       println("public " + this.grammar.getClassName() + "(ParserSharedInputState state) {");
/*  747: 817 */       println("  super(state," + this.grammar.maxk + ");");
/*  748: 818 */       println("  tokenNames = _tokenNames;");
/*  749: 819 */       if (this.grammar.buildAST)
/*  750:     */       {
/*  751: 820 */         println("  buildTokenTypeASTClassMap();");
/*  752: 821 */         println("  astFactory = new ASTFactory(getTokenTypeToASTClassMap());");
/*  753:     */       }
/*  754: 823 */       println("}");
/*  755: 824 */       println("");
/*  756:     */       
/*  757:     */ 
/*  758: 827 */       Object localObject4 = this.grammar.rules.elements();
/*  759: 828 */       int j = 0;
/*  760: 829 */       while (((Enumeration)localObject4).hasMoreElements())
/*  761:     */       {
/*  762: 830 */         localGrammarSymbol = (GrammarSymbol)((Enumeration)localObject4).nextElement();
/*  763: 831 */         if ((localGrammarSymbol instanceof RuleSymbol))
/*  764:     */         {
/*  765: 832 */           RuleSymbol localRuleSymbol = (RuleSymbol)localGrammarSymbol;
/*  766: 833 */           genRule(localRuleSymbol, localRuleSymbol.references.size() == 0, j++);
/*  767:     */         }
/*  768: 835 */         exitIfError();
/*  769:     */       }
/*  770: 839 */       genTokenStrings();
/*  771: 841 */       if (this.grammar.buildAST) {
/*  772: 842 */         genTokenASTNodeMap();
/*  773:     */       }
/*  774: 846 */       genBitsets(this.bitsetsUsed, this.grammar.tokenManager.maxTokenType());
/*  775: 849 */       if (this.grammar.debuggingOutput) {
/*  776: 850 */         genSemPredMap();
/*  777:     */       }
/*  778: 853 */       println("");
/*  779: 854 */       println("}");
/*  780:     */       
/*  781:     */ 
/*  782: 857 */       getPrintWriterManager().finishOutput();
/*  783:     */     }
/*  784:     */     finally
/*  785:     */     {
/*  786: 859 */       this.defaultLine = i;
/*  787:     */     }
/*  788:     */   }
/*  789:     */   
/*  790:     */   public void gen(RuleRefElement paramRuleRefElement)
/*  791:     */   {
/*  792: 867 */     int i = this.defaultLine;
/*  793:     */     try
/*  794:     */     {
/*  795: 869 */       this.defaultLine = paramRuleRefElement.getLine();
/*  796: 870 */       if (this.DEBUG_CODE_GENERATOR) {
/*  797: 870 */         System.out.println("genRR(" + paramRuleRefElement + ")");
/*  798:     */       }
/*  799: 871 */       RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(paramRuleRefElement.targetRule);
/*  800: 872 */       if ((localRuleSymbol == null) || (!localRuleSymbol.isDefined()))
/*  801:     */       {
/*  802: 874 */         this.antlrTool.error("Rule '" + paramRuleRefElement.targetRule + "' is not defined", this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/*  803:     */       }
/*  804: 877 */       else if (!(localRuleSymbol instanceof RuleSymbol))
/*  805:     */       {
/*  806: 879 */         this.antlrTool.error("'" + paramRuleRefElement.targetRule + "' does not name a grammar rule", this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/*  807:     */       }
/*  808:     */       else
/*  809:     */       {
/*  810: 883 */         genErrorTryForElement(paramRuleRefElement);
/*  811: 887 */         if (((this.grammar instanceof TreeWalkerGrammar)) && (paramRuleRefElement.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/*  812: 890 */           println(paramRuleRefElement.getLabel() + " = _t==ASTNULL ? null : " + this.lt1Value + ";");
/*  813:     */         }
/*  814: 894 */         if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramRuleRefElement.getAutoGenType() == 3))) {
/*  815: 895 */           println("_saveIndex=text.length();");
/*  816:     */         }
/*  817: 899 */         printTabs();
/*  818: 900 */         if (paramRuleRefElement.idAssign != null)
/*  819:     */         {
/*  820: 902 */           if (localRuleSymbol.block.returnAction == null) {
/*  821: 903 */             this.antlrTool.warning("Rule '" + paramRuleRefElement.targetRule + "' has no return type", this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/*  822:     */           }
/*  823: 905 */           _print(paramRuleRefElement.idAssign + "=");
/*  824:     */         }
/*  825: 909 */         else if ((!(this.grammar instanceof LexerGrammar)) && (this.syntacticPredLevel == 0) && (localRuleSymbol.block.returnAction != null))
/*  826:     */         {
/*  827: 910 */           this.antlrTool.warning("Rule '" + paramRuleRefElement.targetRule + "' returns a value", this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/*  828:     */         }
/*  829: 915 */         GenRuleInvocation(paramRuleRefElement);
/*  830: 918 */         if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramRuleRefElement.getAutoGenType() == 3))) {
/*  831: 919 */           println("text.setLength(_saveIndex);");
/*  832:     */         }
/*  833: 923 */         if (this.syntacticPredLevel == 0)
/*  834:     */         {
/*  835: 924 */           int j = (this.grammar.hasSyntacticPredicate) && (((this.grammar.buildAST) && (paramRuleRefElement.getLabel() != null)) || ((this.genAST) && (paramRuleRefElement.getAutoGenType() == 1))) ? 1 : 0;
/*  836: 931 */           if ((j == 0) || (
/*  837:     */           
/*  838:     */ 
/*  839:     */ 
/*  840:     */ 
/*  841: 936 */             (this.grammar.buildAST) && (paramRuleRefElement.getLabel() != null))) {
/*  842: 938 */             println(paramRuleRefElement.getLabel() + "_AST = (" + this.labeledElementASTType + ")returnAST;");
/*  843:     */           }
/*  844: 940 */           if (this.genAST) {
/*  845: 941 */             switch (paramRuleRefElement.getAutoGenType())
/*  846:     */             {
/*  847:     */             case 1: 
/*  848: 944 */               println("astFactory.addASTChild(currentAST, returnAST);");
/*  849: 945 */               break;
/*  850:     */             case 2: 
/*  851: 947 */               this.antlrTool.error("Internal: encountered ^ after rule reference");
/*  852: 948 */               break;
/*  853:     */             }
/*  854:     */           }
/*  855: 955 */           if (((this.grammar instanceof LexerGrammar)) && (paramRuleRefElement.getLabel() != null)) {
/*  856: 956 */             println(paramRuleRefElement.getLabel() + "=_returnToken;");
/*  857:     */           }
/*  858: 959 */           if (j == 0) {}
/*  859:     */         }
/*  860: 964 */         genErrorCatchForElement(paramRuleRefElement);
/*  861:     */       }
/*  862:     */     }
/*  863:     */     finally
/*  864:     */     {
/*  865: 966 */       this.defaultLine = i;
/*  866:     */     }
/*  867:     */   }
/*  868:     */   
/*  869:     */   public void gen(StringLiteralElement paramStringLiteralElement)
/*  870:     */   {
/*  871: 974 */     if (this.DEBUG_CODE_GENERATOR) {
/*  872: 974 */       System.out.println("genString(" + paramStringLiteralElement + ")");
/*  873:     */     }
/*  874: 977 */     if ((paramStringLiteralElement.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/*  875: 978 */       println(paramStringLiteralElement.getLabel() + " = " + this.lt1Value + ";", paramStringLiteralElement.getLine());
/*  876:     */     }
/*  877: 982 */     genElementAST(paramStringLiteralElement);
/*  878:     */     
/*  879:     */ 
/*  880: 985 */     boolean bool = this.saveText;
/*  881: 986 */     this.saveText = ((this.saveText) && (paramStringLiteralElement.getAutoGenType() == 1));
/*  882:     */     
/*  883:     */ 
/*  884: 989 */     genMatch(paramStringLiteralElement);
/*  885:     */     
/*  886: 991 */     this.saveText = bool;
/*  887: 994 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/*  888: 995 */       println("_t = _t.getNextSibling();", paramStringLiteralElement.getLine());
/*  889:     */     }
/*  890:     */   }
/*  891:     */   
/*  892:     */   public void gen(TokenRangeElement paramTokenRangeElement)
/*  893:     */   {
/*  894:1003 */     genErrorTryForElement(paramTokenRangeElement);
/*  895:1004 */     if ((paramTokenRangeElement.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/*  896:1005 */       println(paramTokenRangeElement.getLabel() + " = " + this.lt1Value + ";", paramTokenRangeElement.getLine());
/*  897:     */     }
/*  898:1009 */     genElementAST(paramTokenRangeElement);
/*  899:     */     
/*  900:     */ 
/*  901:1012 */     println("matchRange(" + paramTokenRangeElement.beginText + "," + paramTokenRangeElement.endText + ");", paramTokenRangeElement.getLine());
/*  902:1013 */     genErrorCatchForElement(paramTokenRangeElement);
/*  903:     */   }
/*  904:     */   
/*  905:     */   public void gen(TokenRefElement paramTokenRefElement)
/*  906:     */   {
/*  907:1020 */     if (this.DEBUG_CODE_GENERATOR) {
/*  908:1020 */       System.out.println("genTokenRef(" + paramTokenRefElement + ")");
/*  909:     */     }
/*  910:1021 */     if ((this.grammar instanceof LexerGrammar)) {
/*  911:1022 */       this.antlrTool.panic("Token reference found in lexer");
/*  912:     */     }
/*  913:1024 */     genErrorTryForElement(paramTokenRefElement);
/*  914:1026 */     if ((paramTokenRefElement.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/*  915:1027 */       println(paramTokenRefElement.getLabel() + " = " + this.lt1Value + ";", paramTokenRefElement.getLine());
/*  916:     */     }
/*  917:1031 */     genElementAST(paramTokenRefElement);
/*  918:     */     
/*  919:1033 */     genMatch(paramTokenRefElement);
/*  920:1034 */     genErrorCatchForElement(paramTokenRefElement);
/*  921:1037 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/*  922:1038 */       println("_t = _t.getNextSibling();", paramTokenRefElement.getLine());
/*  923:     */     }
/*  924:     */   }
/*  925:     */   
/*  926:     */   public void gen(TreeElement paramTreeElement)
/*  927:     */   {
/*  928:1043 */     int i = this.defaultLine;
/*  929:     */     try
/*  930:     */     {
/*  931:1045 */       this.defaultLine = paramTreeElement.getLine();
/*  932:     */       
/*  933:1047 */       println("AST __t" + paramTreeElement.ID + " = _t;");
/*  934:1050 */       if (paramTreeElement.root.getLabel() != null) {
/*  935:1051 */         println(paramTreeElement.root.getLabel() + " = _t==ASTNULL ? null :(" + this.labeledElementASTType + ")_t;", paramTreeElement.root.getLine());
/*  936:     */       }
/*  937:1055 */       if (paramTreeElement.root.getAutoGenType() == 3)
/*  938:     */       {
/*  939:1056 */         this.antlrTool.error("Suffixing a root node with '!' is not implemented", this.grammar.getFilename(), paramTreeElement.getLine(), paramTreeElement.getColumn());
/*  940:     */         
/*  941:1058 */         paramTreeElement.root.setAutoGenType(1);
/*  942:     */       }
/*  943:1060 */       if (paramTreeElement.root.getAutoGenType() == 2)
/*  944:     */       {
/*  945:1061 */         this.antlrTool.warning("Suffixing a root node with '^' is redundant; already a root", this.grammar.getFilename(), paramTreeElement.getLine(), paramTreeElement.getColumn());
/*  946:     */         
/*  947:1063 */         paramTreeElement.root.setAutoGenType(1);
/*  948:     */       }
/*  949:1067 */       genElementAST(paramTreeElement.root);
/*  950:1068 */       if (this.grammar.buildAST)
/*  951:     */       {
/*  952:1070 */         println("ASTPair __currentAST" + paramTreeElement.ID + " = currentAST.copy();");
/*  953:     */         
/*  954:1072 */         println("currentAST.root = currentAST.child;");
/*  955:1073 */         println("currentAST.child = null;");
/*  956:     */       }
/*  957:1077 */       if ((paramTreeElement.root instanceof WildcardElement)) {
/*  958:1078 */         println("if ( _t==null ) throw new MismatchedTokenException();", paramTreeElement.root.getLine());
/*  959:     */       } else {
/*  960:1081 */         genMatch(paramTreeElement.root);
/*  961:     */       }
/*  962:1084 */       println("_t = _t.getFirstChild();");
/*  963:1087 */       for (int j = 0; j < paramTreeElement.getAlternatives().size(); j++)
/*  964:     */       {
/*  965:1088 */         Alternative localAlternative = paramTreeElement.getAlternativeAt(j);
/*  966:1089 */         AlternativeElement localAlternativeElement = localAlternative.head;
/*  967:1090 */         while (localAlternativeElement != null)
/*  968:     */         {
/*  969:1091 */           localAlternativeElement.generate();
/*  970:1092 */           localAlternativeElement = localAlternativeElement.next;
/*  971:     */         }
/*  972:     */       }
/*  973:1096 */       if (this.grammar.buildAST) {
/*  974:1099 */         println("currentAST = __currentAST" + paramTreeElement.ID + ";");
/*  975:     */       }
/*  976:1102 */       println("_t = __t" + paramTreeElement.ID + ";");
/*  977:     */       
/*  978:1104 */       println("_t = _t.getNextSibling();");
/*  979:     */     }
/*  980:     */     finally
/*  981:     */     {
/*  982:1106 */       this.defaultLine = i;
/*  983:     */     }
/*  984:     */   }
/*  985:     */   
/*  986:     */   public void gen(TreeWalkerGrammar paramTreeWalkerGrammar)
/*  987:     */     throws IOException
/*  988:     */   {
/*  989:1112 */     int i = this.defaultLine;
/*  990:     */     try
/*  991:     */     {
/*  992:1114 */       this.defaultLine = -999;
/*  993:     */       
/*  994:1116 */       setGrammar(paramTreeWalkerGrammar);
/*  995:1117 */       if (!(this.grammar instanceof TreeWalkerGrammar)) {
/*  996:1118 */         this.antlrTool.panic("Internal error generating tree-walker");
/*  997:     */       }
/*  998:1123 */       this.currentOutput = getPrintWriterManager().setupOutput(this.antlrTool, this.grammar);
/*  999:     */       
/* 1000:1125 */       this.genAST = this.grammar.buildAST;
/* 1001:1126 */       this.tabs = 0;
/* 1002:     */       
/* 1003:     */ 
/* 1004:1129 */       genHeader();
/* 1005:     */       try
/* 1006:     */       {
/* 1007:1132 */         this.defaultLine = this.behavior.getHeaderActionLine("");
/* 1008:1133 */         println(this.behavior.getHeaderAction(""));
/* 1009:     */       }
/* 1010:     */       finally
/* 1011:     */       {
/* 1012:1135 */         this.defaultLine = -999;
/* 1013:     */       }
/* 1014:1139 */       println("import antlr." + this.grammar.getSuperClass() + ";");
/* 1015:1140 */       println("import antlr.Token;");
/* 1016:1141 */       println("import antlr.collections.AST;");
/* 1017:1142 */       println("import antlr.RecognitionException;");
/* 1018:1143 */       println("import antlr.ANTLRException;");
/* 1019:1144 */       println("import antlr.NoViableAltException;");
/* 1020:1145 */       println("import antlr.MismatchedTokenException;");
/* 1021:1146 */       println("import antlr.SemanticException;");
/* 1022:1147 */       println("import antlr.collections.impl.BitSet;");
/* 1023:1148 */       println("import antlr.ASTPair;");
/* 1024:1149 */       println("import antlr.collections.impl.ASTArray;");
/* 1025:     */       
/* 1026:     */ 
/* 1027:1152 */       println(this.grammar.preambleAction.getText());
/* 1028:     */       
/* 1029:     */ 
/* 1030:1155 */       String str1 = null;
/* 1031:1156 */       if (this.grammar.superClass != null) {
/* 1032:1157 */         str1 = this.grammar.superClass;
/* 1033:     */       } else {
/* 1034:1160 */         str1 = "antlr." + this.grammar.getSuperClass();
/* 1035:     */       }
/* 1036:1162 */       println("");
/* 1037:1165 */       if (this.grammar.comment != null) {
/* 1038:1166 */         _println(this.grammar.comment);
/* 1039:     */       }
/* 1040:1170 */       Object localObject2 = "public";
/* 1041:1171 */       Token localToken = (Token)this.grammar.options.get("classHeaderPrefix");
/* 1042:1172 */       if (localToken != null)
/* 1043:     */       {
/* 1044:1173 */         localObject3 = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/* 1045:1174 */         if (localObject3 != null) {
/* 1046:1175 */           localObject2 = localObject3;
/* 1047:     */         }
/* 1048:     */       }
/* 1049:1179 */       print((String)localObject2 + " ");
/* 1050:1180 */       print("class " + this.grammar.getClassName() + " extends " + str1);
/* 1051:1181 */       println("       implements " + this.grammar.tokenManager.getName() + TokenTypesFileSuffix);
/* 1052:1182 */       Object localObject3 = (Token)this.grammar.options.get("classHeaderSuffix");
/* 1053:1183 */       if (localObject3 != null)
/* 1054:     */       {
/* 1055:1184 */         localObject4 = StringUtils.stripFrontBack(((Token)localObject3).getText(), "\"", "\"");
/* 1056:1185 */         if (localObject4 != null) {
/* 1057:1186 */           print(", " + (String)localObject4);
/* 1058:     */         }
/* 1059:     */       }
/* 1060:1189 */       println(" {");
/* 1061:     */       
/* 1062:     */ 
/* 1063:1192 */       print(processActionForSpecialSymbols(this.grammar.classMemberAction.getText(), this.grammar.classMemberAction.getLine(), this.currentRule, null), this.grammar.classMemberAction.getLine());
/* 1064:     */       
/* 1065:     */ 
/* 1066:     */ 
/* 1067:     */ 
/* 1068:     */ 
/* 1069:1198 */       println("public " + this.grammar.getClassName() + "() {");
/* 1070:1199 */       this.tabs += 1;
/* 1071:1200 */       println("tokenNames = _tokenNames;");
/* 1072:1201 */       this.tabs -= 1;
/* 1073:1202 */       println("}");
/* 1074:1203 */       println("");
/* 1075:     */       
/* 1076:     */ 
/* 1077:1206 */       Object localObject4 = this.grammar.rules.elements();
/* 1078:1207 */       int j = 0;
/* 1079:1208 */       String str2 = "";
/* 1080:1209 */       while (((Enumeration)localObject4).hasMoreElements())
/* 1081:     */       {
/* 1082:1210 */         GrammarSymbol localGrammarSymbol = (GrammarSymbol)((Enumeration)localObject4).nextElement();
/* 1083:1211 */         if ((localGrammarSymbol instanceof RuleSymbol))
/* 1084:     */         {
/* 1085:1212 */           RuleSymbol localRuleSymbol = (RuleSymbol)localGrammarSymbol;
/* 1086:1213 */           genRule(localRuleSymbol, localRuleSymbol.references.size() == 0, j++);
/* 1087:     */         }
/* 1088:1215 */         exitIfError();
/* 1089:     */       }
/* 1090:1219 */       genTokenStrings();
/* 1091:     */       
/* 1092:     */ 
/* 1093:1222 */       genBitsets(this.bitsetsUsed, this.grammar.tokenManager.maxTokenType());
/* 1094:     */       
/* 1095:     */ 
/* 1096:1225 */       println("}");
/* 1097:1226 */       println("");
/* 1098:     */       
/* 1099:     */ 
/* 1100:1229 */       getPrintWriterManager().finishOutput();
/* 1101:     */     }
/* 1102:     */     finally
/* 1103:     */     {
/* 1104:1231 */       this.defaultLine = i;
/* 1105:     */     }
/* 1106:     */   }
/* 1107:     */   
/* 1108:     */   public void gen(WildcardElement paramWildcardElement)
/* 1109:     */   {
/* 1110:1239 */     int i = this.defaultLine;
/* 1111:     */     try
/* 1112:     */     {
/* 1113:1241 */       this.defaultLine = paramWildcardElement.getLine();
/* 1114:1243 */       if ((paramWildcardElement.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/* 1115:1244 */         println(paramWildcardElement.getLabel() + " = " + this.lt1Value + ";");
/* 1116:     */       }
/* 1117:1248 */       genElementAST(paramWildcardElement);
/* 1118:1250 */       if ((this.grammar instanceof TreeWalkerGrammar))
/* 1119:     */       {
/* 1120:1251 */         println("if ( _t==null ) throw new MismatchedTokenException();");
/* 1121:     */       }
/* 1122:1253 */       else if ((this.grammar instanceof LexerGrammar))
/* 1123:     */       {
/* 1124:1254 */         if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramWildcardElement.getAutoGenType() == 3))) {
/* 1125:1256 */           println("_saveIndex=text.length();");
/* 1126:     */         }
/* 1127:1258 */         println("matchNot(EOF_CHAR);");
/* 1128:1259 */         if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramWildcardElement.getAutoGenType() == 3))) {
/* 1129:1261 */           println("text.setLength(_saveIndex);");
/* 1130:     */         }
/* 1131:     */       }
/* 1132:     */       else
/* 1133:     */       {
/* 1134:1265 */         println("matchNot(" + getValueString(1) + ");");
/* 1135:     */       }
/* 1136:1269 */       if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 1137:1270 */         println("_t = _t.getNextSibling();");
/* 1138:     */       }
/* 1139:     */     }
/* 1140:     */     finally
/* 1141:     */     {
/* 1142:1273 */       this.defaultLine = i;
/* 1143:     */     }
/* 1144:     */   }
/* 1145:     */   
/* 1146:     */   public void gen(ZeroOrMoreBlock paramZeroOrMoreBlock)
/* 1147:     */   {
/* 1148:1281 */     int i = this.defaultLine;
/* 1149:     */     try
/* 1150:     */     {
/* 1151:1283 */       this.defaultLine = paramZeroOrMoreBlock.getLine();
/* 1152:1284 */       if (this.DEBUG_CODE_GENERATOR) {
/* 1153:1284 */         System.out.println("gen*(" + paramZeroOrMoreBlock + ")");
/* 1154:     */       }
/* 1155:1285 */       println("{");
/* 1156:1286 */       genBlockPreamble(paramZeroOrMoreBlock);
/* 1157:     */       String str1;
/* 1158:1288 */       if (paramZeroOrMoreBlock.getLabel() != null) {
/* 1159:1289 */         str1 = paramZeroOrMoreBlock.getLabel();
/* 1160:     */       } else {
/* 1161:1292 */         str1 = "_loop" + paramZeroOrMoreBlock.ID;
/* 1162:     */       }
/* 1163:1294 */       println(str1 + ":");
/* 1164:1295 */       println("do {");
/* 1165:1296 */       this.tabs += 1;
/* 1166:     */       
/* 1167:     */ 
/* 1168:1299 */       genBlockInitAction(paramZeroOrMoreBlock);
/* 1169:     */       
/* 1170:     */ 
/* 1171:1302 */       String str2 = this.currentASTResult;
/* 1172:1303 */       if (paramZeroOrMoreBlock.getLabel() != null) {
/* 1173:1304 */         this.currentASTResult = paramZeroOrMoreBlock.getLabel();
/* 1174:     */       }
/* 1175:1307 */       boolean bool = this.grammar.theLLkAnalyzer.deterministic(paramZeroOrMoreBlock);
/* 1176:     */       
/* 1177:     */ 
/* 1178:     */ 
/* 1179:     */ 
/* 1180:     */ 
/* 1181:     */ 
/* 1182:     */ 
/* 1183:     */ 
/* 1184:     */ 
/* 1185:     */ 
/* 1186:     */ 
/* 1187:1319 */       int j = 0;
/* 1188:1320 */       int k = this.grammar.maxk;
/* 1189:1322 */       if ((!paramZeroOrMoreBlock.greedy) && (paramZeroOrMoreBlock.exitLookaheadDepth <= this.grammar.maxk) && (paramZeroOrMoreBlock.exitCache[paramZeroOrMoreBlock.exitLookaheadDepth].containsEpsilon()))
/* 1190:     */       {
/* 1191:1325 */         j = 1;
/* 1192:1326 */         k = paramZeroOrMoreBlock.exitLookaheadDepth;
/* 1193:     */       }
/* 1194:1328 */       else if ((!paramZeroOrMoreBlock.greedy) && (paramZeroOrMoreBlock.exitLookaheadDepth == 2147483647))
/* 1195:     */       {
/* 1196:1330 */         j = 1;
/* 1197:     */       }
/* 1198:1332 */       if (j != 0)
/* 1199:     */       {
/* 1200:1333 */         if (this.DEBUG_CODE_GENERATOR) {
/* 1201:1334 */           System.out.println("nongreedy (...)* loop; exit depth is " + paramZeroOrMoreBlock.exitLookaheadDepth);
/* 1202:     */         }
/* 1203:1337 */         localObject1 = getLookaheadTestExpression(paramZeroOrMoreBlock.exitCache, k);
/* 1204:     */         
/* 1205:     */ 
/* 1206:1340 */         println("// nongreedy exit test");
/* 1207:1341 */         println("if (" + (String)localObject1 + ") break " + str1 + ";");
/* 1208:     */       }
/* 1209:1344 */       Object localObject1 = genCommonBlock(paramZeroOrMoreBlock, false);
/* 1210:1345 */       genBlockFinish((JavaBlockFinishingInfo)localObject1, "break " + str1 + ";", paramZeroOrMoreBlock.getLine());
/* 1211:     */       
/* 1212:1347 */       this.tabs -= 1;
/* 1213:1348 */       println("} while (true);");
/* 1214:1349 */       println("}");
/* 1215:     */       
/* 1216:     */ 
/* 1217:1352 */       this.currentASTResult = str2;
/* 1218:     */     }
/* 1219:     */     finally
/* 1220:     */     {
/* 1221:1354 */       this.defaultLine = i;
/* 1222:     */     }
/* 1223:     */   }
/* 1224:     */   
/* 1225:     */   protected void genAlt(Alternative paramAlternative, AlternativeBlock paramAlternativeBlock)
/* 1226:     */   {
/* 1227:1364 */     boolean bool1 = this.genAST;
/* 1228:1365 */     this.genAST = ((this.genAST) && (paramAlternative.getAutoGen()));
/* 1229:     */     
/* 1230:1367 */     boolean bool2 = this.saveText;
/* 1231:1368 */     this.saveText = ((this.saveText) && (paramAlternative.getAutoGen()));
/* 1232:     */     
/* 1233:     */ 
/* 1234:1371 */     Hashtable localHashtable = this.treeVariableMap;
/* 1235:1372 */     this.treeVariableMap = new Hashtable();
/* 1236:1375 */     if (paramAlternative.exceptionSpec != null)
/* 1237:     */     {
/* 1238:1376 */       println("try {      // for error handling", paramAlternative.head.getLine());
/* 1239:1377 */       this.tabs += 1;
/* 1240:     */     }
/* 1241:1380 */     AlternativeElement localAlternativeElement = paramAlternative.head;
/* 1242:1381 */     while (!(localAlternativeElement instanceof BlockEndElement))
/* 1243:     */     {
/* 1244:1382 */       localAlternativeElement.generate();
/* 1245:1383 */       localAlternativeElement = localAlternativeElement.next;
/* 1246:     */     }
/* 1247:1386 */     if (this.genAST) {
/* 1248:1387 */       if ((paramAlternativeBlock instanceof RuleBlock))
/* 1249:     */       {
/* 1250:1389 */         RuleBlock localRuleBlock = (RuleBlock)paramAlternativeBlock;
/* 1251:1390 */         if (this.grammar.hasSyntacticPredicate) {}
/* 1252:1394 */         println(localRuleBlock.getRuleName() + "_AST = (" + this.labeledElementASTType + ")currentAST.root;", -888);
/* 1253:1395 */         if (!this.grammar.hasSyntacticPredicate) {}
/* 1254:     */       }
/* 1255:1400 */       else if (paramAlternativeBlock.getLabel() != null)
/* 1256:     */       {
/* 1257:1403 */         this.antlrTool.warning("Labeled subrules not yet supported", this.grammar.getFilename(), paramAlternativeBlock.getLine(), paramAlternativeBlock.getColumn());
/* 1258:     */       }
/* 1259:     */     }
/* 1260:1407 */     if (paramAlternative.exceptionSpec != null)
/* 1261:     */     {
/* 1262:1409 */       this.tabs -= 1;
/* 1263:1410 */       println("}", -999);
/* 1264:1411 */       genErrorHandler(paramAlternative.exceptionSpec);
/* 1265:     */     }
/* 1266:1414 */     this.genAST = bool1;
/* 1267:1415 */     this.saveText = bool2;
/* 1268:     */     
/* 1269:1417 */     this.treeVariableMap = localHashtable;
/* 1270:     */   }
/* 1271:     */   
/* 1272:     */   protected void genBitsets(Vector paramVector, int paramInt)
/* 1273:     */   {
/* 1274:1433 */     println("", -999);
/* 1275:1434 */     for (int i = 0; i < paramVector.size(); i++)
/* 1276:     */     {
/* 1277:1435 */       BitSet localBitSet = (BitSet)paramVector.elementAt(i);
/* 1278:     */       
/* 1279:1437 */       localBitSet.growToInclude(paramInt);
/* 1280:1438 */       genBitSet(localBitSet, i);
/* 1281:     */     }
/* 1282:     */   }
/* 1283:     */   
/* 1284:     */   private void genBitSet(BitSet paramBitSet, int paramInt)
/* 1285:     */   {
/* 1286:1453 */     int i = this.defaultLine;
/* 1287:     */     try
/* 1288:     */     {
/* 1289:1455 */       this.defaultLine = -999;
/* 1290:     */       
/* 1291:1457 */       println("private static final long[] mk" + getBitsetName(paramInt) + "() {");
/* 1292:     */       
/* 1293:     */ 
/* 1294:1460 */       int j = paramBitSet.lengthInLongWords();
/* 1295:     */       long[] arrayOfLong;
/* 1296:     */       int k;
/* 1297:1461 */       if (j < 8)
/* 1298:     */       {
/* 1299:1462 */         println("\tlong[] data = { " + paramBitSet.toStringOfWords() + "};");
/* 1300:     */       }
/* 1301:     */       else
/* 1302:     */       {
/* 1303:1466 */         println("\tlong[] data = new long[" + j + "];");
/* 1304:1467 */         arrayOfLong = paramBitSet.toPackedArray();
/* 1305:1468 */         for (k = 0; k < arrayOfLong.length;) {
/* 1306:1469 */           if (arrayOfLong[k] == 0L)
/* 1307:     */           {
/* 1308:1471 */             k++;
/* 1309:     */           }
/* 1310:1474 */           else if ((k + 1 == arrayOfLong.length) || (arrayOfLong[k] != arrayOfLong[(k + 1)]))
/* 1311:     */           {
/* 1312:1476 */             println("\tdata[" + k + "]=" + arrayOfLong[k] + "L;");
/* 1313:1477 */             k++;
/* 1314:     */           }
/* 1315:     */           else
/* 1316:     */           {
/* 1317:1482 */             int m = k + 1;
/* 1318:1483 */             while ((m < arrayOfLong.length) && (arrayOfLong[m] == arrayOfLong[k])) {
/* 1319:1484 */               m++;
/* 1320:     */             }
/* 1321:1488 */             println("\tfor (int i = " + k + "; i<=" + (m - 1) + "; i++) { data[i]=" + arrayOfLong[k] + "L; }");
/* 1322:     */             
/* 1323:1490 */             k = m;
/* 1324:     */           }
/* 1325:     */         }
/* 1326:     */       }
/* 1327:1495 */       println("\treturn data;");
/* 1328:1496 */       println("}");
/* 1329:     */       
/* 1330:1498 */       println("public static final BitSet " + getBitsetName(paramInt) + " = new BitSet(" + "mk" + getBitsetName(paramInt) + "()" + ");");
/* 1331:     */     }
/* 1332:     */     finally
/* 1333:     */     {
/* 1334:1504 */       this.defaultLine = i;
/* 1335:     */     }
/* 1336:     */   }
/* 1337:     */   
/* 1338:     */   private void genBlockFinish(JavaBlockFinishingInfo paramJavaBlockFinishingInfo, String paramString, int paramInt)
/* 1339:     */   {
/* 1340:1515 */     int i = this.defaultLine;
/* 1341:     */     try
/* 1342:     */     {
/* 1343:1517 */       this.defaultLine = paramInt;
/* 1344:1518 */       if ((paramJavaBlockFinishingInfo.needAnErrorClause) && ((paramJavaBlockFinishingInfo.generatedAnIf) || (paramJavaBlockFinishingInfo.generatedSwitch)))
/* 1345:     */       {
/* 1346:1520 */         if (paramJavaBlockFinishingInfo.generatedAnIf) {
/* 1347:1521 */           println("else {");
/* 1348:     */         } else {
/* 1349:1524 */           println("{");
/* 1350:     */         }
/* 1351:1526 */         this.tabs += 1;
/* 1352:1527 */         println(paramString);
/* 1353:1528 */         this.tabs -= 1;
/* 1354:1529 */         println("}");
/* 1355:     */       }
/* 1356:1532 */       if (paramJavaBlockFinishingInfo.postscript != null) {
/* 1357:1533 */         println(paramJavaBlockFinishingInfo.postscript);
/* 1358:     */       }
/* 1359:     */     }
/* 1360:     */     finally
/* 1361:     */     {
/* 1362:1536 */       this.defaultLine = i;
/* 1363:     */     }
/* 1364:     */   }
/* 1365:     */   
/* 1366:     */   protected void genBlockInitAction(AlternativeBlock paramAlternativeBlock)
/* 1367:     */   {
/* 1368:1546 */     if (paramAlternativeBlock.initAction != null) {
/* 1369:1547 */       printAction(processActionForSpecialSymbols(paramAlternativeBlock.initAction, paramAlternativeBlock.getLine(), this.currentRule, null), paramAlternativeBlock.getLine());
/* 1370:     */     }
/* 1371:     */   }
/* 1372:     */   
/* 1373:     */   protected void genBlockPreamble(AlternativeBlock paramAlternativeBlock)
/* 1374:     */   {
/* 1375:1558 */     if ((paramAlternativeBlock instanceof RuleBlock))
/* 1376:     */     {
/* 1377:1559 */       RuleBlock localRuleBlock = (RuleBlock)paramAlternativeBlock;
/* 1378:1560 */       if (localRuleBlock.labeledElements != null) {
/* 1379:1561 */         for (int i = 0; i < localRuleBlock.labeledElements.size(); i++)
/* 1380:     */         {
/* 1381:1562 */           AlternativeElement localAlternativeElement = (AlternativeElement)localRuleBlock.labeledElements.elementAt(i);
/* 1382:1563 */           int j = this.defaultLine;
/* 1383:     */           try
/* 1384:     */           {
/* 1385:1565 */             this.defaultLine = localAlternativeElement.getLine();
/* 1386:1572 */             if (((localAlternativeElement instanceof RuleRefElement)) || (((localAlternativeElement instanceof AlternativeBlock)) && (!(localAlternativeElement instanceof RuleBlock)) && (!(localAlternativeElement instanceof SynPredBlock))))
/* 1387:     */             {
/* 1388:1579 */               if ((!(localAlternativeElement instanceof RuleRefElement)) && (((AlternativeBlock)localAlternativeElement).not) && (this.analyzer.subruleCanBeInverted((AlternativeBlock)localAlternativeElement, this.grammar instanceof LexerGrammar)))
/* 1389:     */               {
/* 1390:1587 */                 println(this.labeledElementType + " " + localAlternativeElement.getLabel() + " = " + this.labeledElementInit + ";");
/* 1391:1588 */                 if (this.grammar.buildAST) {
/* 1392:1589 */                   genASTDeclaration(localAlternativeElement);
/* 1393:     */                 }
/* 1394:     */               }
/* 1395:     */               else
/* 1396:     */               {
/* 1397:1593 */                 if (this.grammar.buildAST) {
/* 1398:1597 */                   genASTDeclaration(localAlternativeElement);
/* 1399:     */                 }
/* 1400:1599 */                 if ((this.grammar instanceof LexerGrammar)) {
/* 1401:1600 */                   println("Token " + localAlternativeElement.getLabel() + "=null;");
/* 1402:     */                 }
/* 1403:1602 */                 if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 1404:1605 */                   println(this.labeledElementType + " " + localAlternativeElement.getLabel() + " = " + this.labeledElementInit + ";");
/* 1405:     */                 }
/* 1406:     */               }
/* 1407:     */             }
/* 1408:     */             else
/* 1409:     */             {
/* 1410:1612 */               println(this.labeledElementType + " " + localAlternativeElement.getLabel() + " = " + this.labeledElementInit + ";");
/* 1411:1616 */               if (this.grammar.buildAST) {
/* 1412:1617 */                 if (((localAlternativeElement instanceof GrammarAtom)) && (((GrammarAtom)localAlternativeElement).getASTNodeType() != null))
/* 1413:     */                 {
/* 1414:1619 */                   GrammarAtom localGrammarAtom = (GrammarAtom)localAlternativeElement;
/* 1415:1620 */                   genASTDeclaration(localAlternativeElement, localGrammarAtom.getASTNodeType());
/* 1416:     */                 }
/* 1417:     */                 else
/* 1418:     */                 {
/* 1419:1623 */                   genASTDeclaration(localAlternativeElement);
/* 1420:     */                 }
/* 1421:     */               }
/* 1422:     */             }
/* 1423:     */           }
/* 1424:     */           finally
/* 1425:     */           {
/* 1426:1628 */             this.defaultLine = j;
/* 1427:     */           }
/* 1428:     */         }
/* 1429:     */       }
/* 1430:     */     }
/* 1431:     */   }
/* 1432:     */   
/* 1433:     */   protected void genCases(BitSet paramBitSet, int paramInt)
/* 1434:     */   {
/* 1435:1639 */     int i = this.defaultLine;
/* 1436:     */     try
/* 1437:     */     {
/* 1438:1641 */       this.defaultLine = paramInt;
/* 1439:1642 */       if (this.DEBUG_CODE_GENERATOR) {
/* 1440:1642 */         System.out.println("genCases(" + paramBitSet + ")");
/* 1441:     */       }
/* 1442:1645 */       int[] arrayOfInt = paramBitSet.toArray();
/* 1443:     */       
/* 1444:1647 */       int j = (this.grammar instanceof LexerGrammar) ? 4 : 1;
/* 1445:1648 */       int k = 1;
/* 1446:1649 */       int m = 1;
/* 1447:1650 */       for (int n = 0; n < arrayOfInt.length; n++)
/* 1448:     */       {
/* 1449:1651 */         if (k == 1) {
/* 1450:1652 */           print("");
/* 1451:     */         } else {
/* 1452:1655 */           _print("  ");
/* 1453:     */         }
/* 1454:1657 */         _print("case " + getValueString(arrayOfInt[n]) + ":");
/* 1455:1659 */         if (k == j)
/* 1456:     */         {
/* 1457:1660 */           _println("");
/* 1458:1661 */           m = 1;
/* 1459:1662 */           k = 1;
/* 1460:     */         }
/* 1461:     */         else
/* 1462:     */         {
/* 1463:1665 */           k++;
/* 1464:1666 */           m = 0;
/* 1465:     */         }
/* 1466:     */       }
/* 1467:1669 */       if (m == 0) {
/* 1468:1670 */         _println("");
/* 1469:     */       }
/* 1470:     */     }
/* 1471:     */     finally
/* 1472:     */     {
/* 1473:1673 */       this.defaultLine = i;
/* 1474:     */     }
/* 1475:     */   }
/* 1476:     */   
/* 1477:     */   public JavaBlockFinishingInfo genCommonBlock(AlternativeBlock paramAlternativeBlock, boolean paramBoolean)
/* 1478:     */   {
/* 1479:1690 */     int i = this.defaultLine;
/* 1480:     */     try
/* 1481:     */     {
/* 1482:1692 */       this.defaultLine = paramAlternativeBlock.getLine();
/* 1483:1693 */       int j = 0;
/* 1484:1694 */       int k = 0;
/* 1485:1695 */       int m = 0;
/* 1486:1696 */       JavaBlockFinishingInfo localJavaBlockFinishingInfo1 = new JavaBlockFinishingInfo();
/* 1487:1697 */       if (this.DEBUG_CODE_GENERATOR) {
/* 1488:1697 */         System.out.println("genCommonBlock(" + paramAlternativeBlock + ")");
/* 1489:     */       }
/* 1490:1700 */       boolean bool1 = this.genAST;
/* 1491:1701 */       this.genAST = ((this.genAST) && (paramAlternativeBlock.getAutoGen()));
/* 1492:     */       
/* 1493:1703 */       boolean bool2 = this.saveText;
/* 1494:1704 */       this.saveText = ((this.saveText) && (paramAlternativeBlock.getAutoGen()));
/* 1495:     */       Object localObject1;
/* 1496:     */       Object localObject2;
/* 1497:     */       Object localObject3;
/* 1498:1707 */       if ((paramAlternativeBlock.not) && (this.analyzer.subruleCanBeInverted(paramAlternativeBlock, this.grammar instanceof LexerGrammar)))
/* 1499:     */       {
/* 1500:1711 */         if (this.DEBUG_CODE_GENERATOR) {
/* 1501:1711 */           System.out.println("special case: ~(subrule)");
/* 1502:     */         }
/* 1503:1712 */         localObject1 = this.analyzer.look(1, paramAlternativeBlock);
/* 1504:1714 */         if ((paramAlternativeBlock.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/* 1505:1715 */           println(paramAlternativeBlock.getLabel() + " = " + this.lt1Value + ";");
/* 1506:     */         }
/* 1507:1719 */         genElementAST(paramAlternativeBlock);
/* 1508:     */         
/* 1509:1721 */         localObject2 = "";
/* 1510:1722 */         if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 1511:1723 */           localObject2 = "_t,";
/* 1512:     */         }
/* 1513:1727 */         println("match(" + (String)localObject2 + getBitsetName(markBitsetForGen(((Lookahead)localObject1).fset)) + ");");
/* 1514:1730 */         if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 1515:1731 */           println("_t = _t.getNextSibling();");
/* 1516:     */         }
/* 1517:1733 */         return localJavaBlockFinishingInfo1;
/* 1518:     */       }
/* 1519:1737 */       if (paramAlternativeBlock.getAlternatives().size() == 1)
/* 1520:     */       {
/* 1521:1738 */         localObject1 = paramAlternativeBlock.getAlternativeAt(0);
/* 1522:1740 */         if (((Alternative)localObject1).synPred != null) {
/* 1523:1741 */           this.antlrTool.warning("Syntactic predicate superfluous for single alternative", this.grammar.getFilename(), paramAlternativeBlock.getAlternativeAt(0).synPred.getLine(), paramAlternativeBlock.getAlternativeAt(0).synPred.getColumn());
/* 1524:     */         }
/* 1525:1748 */         if (paramBoolean)
/* 1526:     */         {
/* 1527:1749 */           if (((Alternative)localObject1).semPred != null) {
/* 1528:1751 */             genSemPred(((Alternative)localObject1).semPred, paramAlternativeBlock.line);
/* 1529:     */           }
/* 1530:1753 */           genAlt((Alternative)localObject1, paramAlternativeBlock);
/* 1531:1754 */           return localJavaBlockFinishingInfo1;
/* 1532:     */         }
/* 1533:     */       }
/* 1534:1767 */       int n = 0;
/* 1535:1768 */       for (int i1 = 0; i1 < paramAlternativeBlock.getAlternatives().size(); i1++)
/* 1536:     */       {
/* 1537:1769 */         localObject3 = paramAlternativeBlock.getAlternativeAt(i1);
/* 1538:1770 */         if (suitableForCaseExpression((Alternative)localObject3)) {
/* 1539:1771 */           n++;
/* 1540:     */         }
/* 1541:     */       }
/* 1542:     */       Object localObject4;
/* 1543:1776 */       if (n >= this.makeSwitchThreshold)
/* 1544:     */       {
/* 1545:1778 */         String str1 = lookaheadString(1);
/* 1546:1779 */         k = 1;
/* 1547:1781 */         if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 1548:1782 */           println("if (_t==null) _t=ASTNULL;");
/* 1549:     */         }
/* 1550:1784 */         println("switch ( " + str1 + ") {");
/* 1551:1785 */         for (i3 = 0; i3 < paramAlternativeBlock.alternatives.size(); i3++)
/* 1552:     */         {
/* 1553:1786 */           Alternative localAlternative = paramAlternativeBlock.getAlternativeAt(i3);
/* 1554:1789 */           if (suitableForCaseExpression(localAlternative))
/* 1555:     */           {
/* 1556:1792 */             localObject4 = localAlternative.cache[1];
/* 1557:1793 */             if ((((Lookahead)localObject4).fset.degree() == 0) && (!((Lookahead)localObject4).containsEpsilon()))
/* 1558:     */             {
/* 1559:1794 */               this.antlrTool.warning("Alternate omitted due to empty prediction set", this.grammar.getFilename(), localAlternative.head.getLine(), localAlternative.head.getColumn());
/* 1560:     */             }
/* 1561:     */             else
/* 1562:     */             {
/* 1563:1799 */               genCases(((Lookahead)localObject4).fset, localAlternative.head.getLine());
/* 1564:1800 */               println("{", localAlternative.head.getLine());
/* 1565:1801 */               this.tabs += 1;
/* 1566:1802 */               genAlt(localAlternative, paramAlternativeBlock);
/* 1567:1803 */               println("break;", -999);
/* 1568:1804 */               this.tabs -= 1;
/* 1569:1805 */               println("}", -999);
/* 1570:     */             }
/* 1571:     */           }
/* 1572:     */         }
/* 1573:1808 */         println("default:");
/* 1574:1809 */         this.tabs += 1;
/* 1575:     */       }
/* 1576:1825 */       int i2 = (this.grammar instanceof LexerGrammar) ? this.grammar.maxk : 0;
/* 1577:1826 */       for (int i3 = i2; i3 >= 0; i3--)
/* 1578:     */       {
/* 1579:1827 */         if (this.DEBUG_CODE_GENERATOR) {
/* 1580:1827 */           System.out.println("checking depth " + i3);
/* 1581:     */         }
/* 1582:1828 */         for (i4 = 0; i4 < paramAlternativeBlock.alternatives.size(); i4++)
/* 1583:     */         {
/* 1584:1829 */           localObject4 = paramAlternativeBlock.getAlternativeAt(i4);
/* 1585:1830 */           if (this.DEBUG_CODE_GENERATOR) {
/* 1586:1830 */             System.out.println("genAlt: " + i4);
/* 1587:     */           }
/* 1588:1835 */           if ((k != 0) && (suitableForCaseExpression((Alternative)localObject4)))
/* 1589:     */           {
/* 1590:1836 */             if (this.DEBUG_CODE_GENERATOR) {
/* 1591:1836 */               System.out.println("ignoring alt because it was in the switch");
/* 1592:     */             }
/* 1593:     */           }
/* 1594:     */           else
/* 1595:     */           {
/* 1596:1841 */             boolean bool3 = false;
/* 1597:     */             String str3;
/* 1598:1843 */             if ((this.grammar instanceof LexerGrammar))
/* 1599:     */             {
/* 1600:1847 */               i5 = ((Alternative)localObject4).lookaheadDepth;
/* 1601:1848 */               if (i5 == 2147483647) {
/* 1602:1850 */                 i5 = this.grammar.maxk;
/* 1603:     */               }
/* 1604:1852 */               while ((i5 >= 1) && (localObject4.cache[i5].containsEpsilon())) {
/* 1605:1854 */                 i5--;
/* 1606:     */               }
/* 1607:1858 */               if (i5 != i3)
/* 1608:     */               {
/* 1609:1859 */                 if (!this.DEBUG_CODE_GENERATOR) {
/* 1610:     */                   continue;
/* 1611:     */                 }
/* 1612:1860 */                 System.out.println("ignoring alt because effectiveDepth!=altDepth;" + i5 + "!=" + i3); continue;
/* 1613:     */               }
/* 1614:1863 */               bool3 = lookaheadIsEmpty((Alternative)localObject4, i5);
/* 1615:1864 */               str3 = getLookaheadTestExpression((Alternative)localObject4, i5);
/* 1616:     */             }
/* 1617:     */             else
/* 1618:     */             {
/* 1619:1867 */               bool3 = lookaheadIsEmpty((Alternative)localObject4, this.grammar.maxk);
/* 1620:1868 */               str3 = getLookaheadTestExpression((Alternative)localObject4, this.grammar.maxk);
/* 1621:     */             }
/* 1622:1871 */             int i5 = this.defaultLine;
/* 1623:     */             try
/* 1624:     */             {
/* 1625:1873 */               this.defaultLine = ((Alternative)localObject4).head.getLine();
/* 1626:1876 */               if ((localObject4.cache[1].fset.degree() > 127) && (suitableForCaseExpression((Alternative)localObject4)))
/* 1627:     */               {
/* 1628:1878 */                 if (j == 0) {
/* 1629:1879 */                   println("if " + str3 + " {");
/* 1630:     */                 } else {
/* 1631:1882 */                   println("else if " + str3 + " {");
/* 1632:     */                 }
/* 1633:     */               }
/* 1634:1885 */               else if ((bool3) && (((Alternative)localObject4).semPred == null) && (((Alternative)localObject4).synPred == null))
/* 1635:     */               {
/* 1636:1892 */                 if (j == 0) {
/* 1637:1893 */                   println("{");
/* 1638:     */                 } else {
/* 1639:1896 */                   println("else {");
/* 1640:     */                 }
/* 1641:1898 */                 localJavaBlockFinishingInfo1.needAnErrorClause = false;
/* 1642:     */               }
/* 1643:     */               else
/* 1644:     */               {
/* 1645:1904 */                 if (((Alternative)localObject4).semPred != null)
/* 1646:     */                 {
/* 1647:1908 */                   ActionTransInfo localActionTransInfo = new ActionTransInfo();
/* 1648:1909 */                   String str4 = processActionForSpecialSymbols(((Alternative)localObject4).semPred, paramAlternativeBlock.line, this.currentRule, localActionTransInfo);
/* 1649:1917 */                   if ((((this.grammar instanceof ParserGrammar)) || ((this.grammar instanceof LexerGrammar))) && (this.grammar.debuggingOutput)) {
/* 1650:1920 */                     str3 = "(" + str3 + "&& fireSemanticPredicateEvaluated(antlr.debug.SemanticPredicateEvent.PREDICTING," + addSemPred(this.charFormatter.escapeString(str4)) + "," + str4 + "))";
/* 1651:     */                   } else {
/* 1652:1924 */                     str3 = "(" + str3 + "&&(" + str4 + "))";
/* 1653:     */                   }
/* 1654:     */                 }
/* 1655:1929 */                 if (j > 0)
/* 1656:     */                 {
/* 1657:1930 */                   if (((Alternative)localObject4).synPred != null)
/* 1658:     */                   {
/* 1659:1931 */                     println("else {", ((Alternative)localObject4).synPred.getLine());
/* 1660:1932 */                     this.tabs += 1;
/* 1661:1933 */                     genSynPred(((Alternative)localObject4).synPred, str3);
/* 1662:1934 */                     m++;
/* 1663:     */                   }
/* 1664:     */                   else
/* 1665:     */                   {
/* 1666:1937 */                     println("else if " + str3 + " {");
/* 1667:     */                   }
/* 1668:     */                 }
/* 1669:1941 */                 else if (((Alternative)localObject4).synPred != null)
/* 1670:     */                 {
/* 1671:1942 */                   genSynPred(((Alternative)localObject4).synPred, str3);
/* 1672:     */                 }
/* 1673:     */                 else
/* 1674:     */                 {
/* 1675:1947 */                   if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 1676:1948 */                     println("if (_t==null) _t=ASTNULL;");
/* 1677:     */                   }
/* 1678:1950 */                   println("if " + str3 + " {");
/* 1679:     */                 }
/* 1680:     */               }
/* 1681:     */             }
/* 1682:     */             finally {}
/* 1683:1959 */             j++;
/* 1684:1960 */             this.tabs += 1;
/* 1685:1961 */             genAlt((Alternative)localObject4, paramAlternativeBlock);
/* 1686:1962 */             this.tabs -= 1;
/* 1687:1963 */             println("}");
/* 1688:     */           }
/* 1689:     */         }
/* 1690:     */       }
/* 1691:1966 */       String str2 = "";
/* 1692:1967 */       for (int i4 = 1; i4 <= m; i4++) {
/* 1693:1968 */         str2 = str2 + "}";
/* 1694:     */       }
/* 1695:1972 */       this.genAST = bool1;
/* 1696:     */       
/* 1697:     */ 
/* 1698:1975 */       this.saveText = bool2;
/* 1699:1978 */       if (k != 0)
/* 1700:     */       {
/* 1701:1979 */         this.tabs -= 1;
/* 1702:1980 */         localJavaBlockFinishingInfo1.postscript = (str2 + "}");
/* 1703:1981 */         localJavaBlockFinishingInfo1.generatedSwitch = true;
/* 1704:1982 */         localJavaBlockFinishingInfo1.generatedAnIf = (j > 0);
/* 1705:     */       }
/* 1706:     */       else
/* 1707:     */       {
/* 1708:1987 */         localJavaBlockFinishingInfo1.postscript = str2;
/* 1709:1988 */         localJavaBlockFinishingInfo1.generatedSwitch = false;
/* 1710:1989 */         localJavaBlockFinishingInfo1.generatedAnIf = (j > 0);
/* 1711:     */       }
/* 1712:1992 */       return localJavaBlockFinishingInfo1;
/* 1713:     */     }
/* 1714:     */     finally
/* 1715:     */     {
/* 1716:1994 */       this.defaultLine = i;
/* 1717:     */     }
/* 1718:     */   }
/* 1719:     */   
/* 1720:     */   private static boolean suitableForCaseExpression(Alternative paramAlternative)
/* 1721:     */   {
/* 1722:1999 */     return (paramAlternative.lookaheadDepth == 1) && (paramAlternative.semPred == null) && (!paramAlternative.cache[1].containsEpsilon()) && (paramAlternative.cache[1].fset.degree() <= 127);
/* 1723:     */   }
/* 1724:     */   
/* 1725:     */   private void genElementAST(AlternativeElement paramAlternativeElement)
/* 1726:     */   {
/* 1727:2008 */     int i = this.defaultLine;
/* 1728:     */     try
/* 1729:     */     {
/* 1730:2010 */       this.defaultLine = paramAlternativeElement.getLine();
/* 1731:2013 */       if (((this.grammar instanceof TreeWalkerGrammar)) && (!this.grammar.buildAST))
/* 1732:     */       {
/* 1733:2018 */         if (paramAlternativeElement.getLabel() == null)
/* 1734:     */         {
/* 1735:2019 */           String str1 = this.lt1Value;
/* 1736:     */           
/* 1737:2021 */           String str2 = "tmp" + this.astVarNumber + "_AST";
/* 1738:2022 */           this.astVarNumber += 1;
/* 1739:     */           
/* 1740:2024 */           mapTreeVariable(paramAlternativeElement, str2);
/* 1741:     */           
/* 1742:2026 */           println(this.labeledElementASTType + " " + str2 + "_in = " + str1 + ";");
/* 1743:     */         }
/* 1744:     */       }
/* 1745:2031 */       else if ((this.grammar.buildAST) && (this.syntacticPredLevel == 0))
/* 1746:     */       {
/* 1747:2032 */         int j = (this.genAST) && ((paramAlternativeElement.getLabel() != null) || (paramAlternativeElement.getAutoGenType() != 3)) ? 1 : 0;
/* 1748:2043 */         if ((paramAlternativeElement.getAutoGenType() != 3) && ((paramAlternativeElement instanceof TokenRefElement))) {
/* 1749:2046 */           j = 1;
/* 1750:     */         }
/* 1751:2049 */         int k = (this.grammar.hasSyntacticPredicate) && (j != 0) ? 1 : 0;
/* 1752:     */         String str3;
/* 1753:     */         String str4;
/* 1754:2056 */         if (paramAlternativeElement.getLabel() != null)
/* 1755:     */         {
/* 1756:2057 */           str3 = paramAlternativeElement.getLabel();
/* 1757:2058 */           str4 = paramAlternativeElement.getLabel();
/* 1758:     */         }
/* 1759:     */         else
/* 1760:     */         {
/* 1761:2061 */           str3 = this.lt1Value;
/* 1762:     */           
/* 1763:2063 */           str4 = "tmp" + this.astVarNumber;
/* 1764:     */           
/* 1765:2065 */           this.astVarNumber += 1;
/* 1766:     */         }
/* 1767:2069 */         if (j != 0) {
/* 1768:2071 */           if ((paramAlternativeElement instanceof GrammarAtom))
/* 1769:     */           {
/* 1770:2072 */             localObject1 = (GrammarAtom)paramAlternativeElement;
/* 1771:2073 */             if (((GrammarAtom)localObject1).getASTNodeType() != null) {
/* 1772:2074 */               genASTDeclaration(paramAlternativeElement, str4, ((GrammarAtom)localObject1).getASTNodeType());
/* 1773:     */             } else {
/* 1774:2078 */               genASTDeclaration(paramAlternativeElement, str4, this.labeledElementASTType);
/* 1775:     */             }
/* 1776:     */           }
/* 1777:     */           else
/* 1778:     */           {
/* 1779:2083 */             genASTDeclaration(paramAlternativeElement, str4, this.labeledElementASTType);
/* 1780:     */           }
/* 1781:     */         }
/* 1782:2089 */         Object localObject1 = str4 + "_AST";
/* 1783:     */         
/* 1784:     */ 
/* 1785:2092 */         mapTreeVariable(paramAlternativeElement, (String)localObject1);
/* 1786:2093 */         if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 1787:2095 */           println(this.labeledElementASTType + " " + (String)localObject1 + "_in = null;");
/* 1788:     */         }
/* 1789:2099 */         if ((k == 0) || 
/* 1790:     */         
/* 1791:     */ 
/* 1792:     */ 
/* 1793:     */ 
/* 1794:     */ 
/* 1795:     */ 
/* 1796:2106 */           (paramAlternativeElement.getLabel() != null)) {
/* 1797:2107 */           if ((paramAlternativeElement instanceof GrammarAtom)) {
/* 1798:2108 */             println((String)localObject1 + " = " + getASTCreateString((GrammarAtom)paramAlternativeElement, str3) + ";");
/* 1799:     */           } else {
/* 1800:2111 */             println((String)localObject1 + " = " + getASTCreateString(str3) + ";");
/* 1801:     */           }
/* 1802:     */         }
/* 1803:2116 */         if ((paramAlternativeElement.getLabel() == null) && (j != 0))
/* 1804:     */         {
/* 1805:2117 */           str3 = this.lt1Value;
/* 1806:2118 */           if ((paramAlternativeElement instanceof GrammarAtom)) {
/* 1807:2119 */             println((String)localObject1 + " = " + getASTCreateString((GrammarAtom)paramAlternativeElement, str3) + ";");
/* 1808:     */           } else {
/* 1809:2122 */             println((String)localObject1 + " = " + getASTCreateString(str3) + ";");
/* 1810:     */           }
/* 1811:2125 */           if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 1812:2127 */             println((String)localObject1 + "_in = " + str3 + ";");
/* 1813:     */           }
/* 1814:     */         }
/* 1815:2131 */         if (this.genAST) {
/* 1816:2132 */           switch (paramAlternativeElement.getAutoGenType())
/* 1817:     */           {
/* 1818:     */           case 1: 
/* 1819:2134 */             println("astFactory.addASTChild(currentAST, " + (String)localObject1 + ");");
/* 1820:2135 */             break;
/* 1821:     */           case 2: 
/* 1822:2137 */             println("astFactory.makeASTRoot(currentAST, " + (String)localObject1 + ");");
/* 1823:2138 */             break;
/* 1824:     */           }
/* 1825:     */         }
/* 1826:2143 */         if (k == 0) {}
/* 1827:     */       }
/* 1828:     */     }
/* 1829:     */     finally
/* 1830:     */     {
/* 1831:2149 */       this.defaultLine = i;
/* 1832:     */     }
/* 1833:     */   }
/* 1834:     */   
/* 1835:     */   private void genErrorCatchForElement(AlternativeElement paramAlternativeElement)
/* 1836:     */   {
/* 1837:2157 */     if (paramAlternativeElement.getLabel() == null) {
/* 1838:2157 */       return;
/* 1839:     */     }
/* 1840:2158 */     String str = paramAlternativeElement.enclosingRuleName;
/* 1841:2159 */     if ((this.grammar instanceof LexerGrammar)) {
/* 1842:2160 */       str = CodeGenerator.encodeLexerRuleName(paramAlternativeElement.enclosingRuleName);
/* 1843:     */     }
/* 1844:2162 */     RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(str);
/* 1845:2163 */     if (localRuleSymbol == null) {
/* 1846:2164 */       this.antlrTool.panic("Enclosing rule not found!");
/* 1847:     */     }
/* 1848:2166 */     ExceptionSpec localExceptionSpec = localRuleSymbol.block.findExceptionSpec(paramAlternativeElement.getLabel());
/* 1849:2167 */     if (localExceptionSpec != null)
/* 1850:     */     {
/* 1851:2168 */       this.tabs -= 1;
/* 1852:2169 */       println("}", paramAlternativeElement.getLine());
/* 1853:2170 */       genErrorHandler(localExceptionSpec);
/* 1854:     */     }
/* 1855:     */   }
/* 1856:     */   
/* 1857:     */   private void genErrorHandler(ExceptionSpec paramExceptionSpec)
/* 1858:     */   {
/* 1859:2177 */     for (int i = 0; i < paramExceptionSpec.handlers.size(); i++)
/* 1860:     */     {
/* 1861:2178 */       ExceptionHandler localExceptionHandler = (ExceptionHandler)paramExceptionSpec.handlers.elementAt(i);
/* 1862:2179 */       int j = this.defaultLine;
/* 1863:     */       try
/* 1864:     */       {
/* 1865:2181 */         this.defaultLine = localExceptionHandler.action.getLine();
/* 1866:     */         
/* 1867:2183 */         println("catch (" + localExceptionHandler.exceptionTypeAndName.getText() + ") {", localExceptionHandler.exceptionTypeAndName.getLine());
/* 1868:2184 */         this.tabs += 1;
/* 1869:2185 */         if (this.grammar.hasSyntacticPredicate)
/* 1870:     */         {
/* 1871:2186 */           println("if (inputState.guessing==0) {");
/* 1872:2187 */           this.tabs += 1;
/* 1873:     */         }
/* 1874:2191 */         ActionTransInfo localActionTransInfo = new ActionTransInfo();
/* 1875:2192 */         printAction(processActionForSpecialSymbols(localExceptionHandler.action.getText(), localExceptionHandler.action.getLine(), this.currentRule, localActionTransInfo));
/* 1876:2198 */         if (this.grammar.hasSyntacticPredicate)
/* 1877:     */         {
/* 1878:2199 */           this.tabs -= 1;
/* 1879:2200 */           println("} else {");
/* 1880:2201 */           this.tabs += 1;
/* 1881:     */           
/* 1882:2203 */           println("throw " + extractIdOfAction(localExceptionHandler.exceptionTypeAndName) + ";");
/* 1883:     */           
/* 1884:     */ 
/* 1885:     */ 
/* 1886:     */ 
/* 1887:2208 */           this.tabs -= 1;
/* 1888:2209 */           println("}");
/* 1889:     */         }
/* 1890:2212 */         this.tabs -= 1;
/* 1891:2213 */         println("}");
/* 1892:     */       }
/* 1893:     */       finally
/* 1894:     */       {
/* 1895:2215 */         this.defaultLine = j;
/* 1896:     */       }
/* 1897:     */     }
/* 1898:     */   }
/* 1899:     */   
/* 1900:     */   private void genErrorTryForElement(AlternativeElement paramAlternativeElement)
/* 1901:     */   {
/* 1902:2222 */     if (paramAlternativeElement.getLabel() == null) {
/* 1903:2222 */       return;
/* 1904:     */     }
/* 1905:2223 */     String str = paramAlternativeElement.enclosingRuleName;
/* 1906:2224 */     if ((this.grammar instanceof LexerGrammar)) {
/* 1907:2225 */       str = CodeGenerator.encodeLexerRuleName(paramAlternativeElement.enclosingRuleName);
/* 1908:     */     }
/* 1909:2227 */     RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(str);
/* 1910:2228 */     if (localRuleSymbol == null) {
/* 1911:2229 */       this.antlrTool.panic("Enclosing rule not found!");
/* 1912:     */     }
/* 1913:2231 */     ExceptionSpec localExceptionSpec = localRuleSymbol.block.findExceptionSpec(paramAlternativeElement.getLabel());
/* 1914:2232 */     if (localExceptionSpec != null)
/* 1915:     */     {
/* 1916:2233 */       println("try { // for error handling", paramAlternativeElement.getLine());
/* 1917:2234 */       this.tabs += 1;
/* 1918:     */     }
/* 1919:     */   }
/* 1920:     */   
/* 1921:     */   protected void genASTDeclaration(AlternativeElement paramAlternativeElement)
/* 1922:     */   {
/* 1923:2239 */     genASTDeclaration(paramAlternativeElement, this.labeledElementASTType);
/* 1924:     */   }
/* 1925:     */   
/* 1926:     */   protected void genASTDeclaration(AlternativeElement paramAlternativeElement, String paramString)
/* 1927:     */   {
/* 1928:2243 */     genASTDeclaration(paramAlternativeElement, paramAlternativeElement.getLabel(), paramString);
/* 1929:     */   }
/* 1930:     */   
/* 1931:     */   protected void genASTDeclaration(AlternativeElement paramAlternativeElement, String paramString1, String paramString2)
/* 1932:     */   {
/* 1933:2248 */     if (this.declaredASTVariables.contains(paramAlternativeElement)) {
/* 1934:2249 */       return;
/* 1935:     */     }
/* 1936:2252 */     println(paramString2 + " " + paramString1 + "_AST = null;");
/* 1937:     */     
/* 1938:     */ 
/* 1939:2255 */     this.declaredASTVariables.put(paramAlternativeElement, paramAlternativeElement);
/* 1940:     */   }
/* 1941:     */   
/* 1942:     */   protected void genHeader()
/* 1943:     */   {
/* 1944:2260 */     println("// $ANTLR " + Tool.version + ": " + "\"" + this.antlrTool.fileMinusPath(this.antlrTool.grammarFile) + "\"" + " -> " + "\"" + this.grammar.getClassName() + ".java\"$", -999);
/* 1945:     */   }
/* 1946:     */   
/* 1947:     */   private void genLiteralsTest()
/* 1948:     */   {
/* 1949:2267 */     println("_ttype = testLiteralsTable(_ttype);");
/* 1950:     */   }
/* 1951:     */   
/* 1952:     */   private void genLiteralsTestForPartialToken()
/* 1953:     */   {
/* 1954:2271 */     println("_ttype = testLiteralsTable(new String(text.getBuffer(),_begin,text.length()-_begin),_ttype);");
/* 1955:     */   }
/* 1956:     */   
/* 1957:     */   protected void genMatch(BitSet paramBitSet) {}
/* 1958:     */   
/* 1959:     */   protected void genMatch(GrammarAtom paramGrammarAtom)
/* 1960:     */   {
/* 1961:2278 */     if ((paramGrammarAtom instanceof StringLiteralElement))
/* 1962:     */     {
/* 1963:2279 */       if ((this.grammar instanceof LexerGrammar)) {
/* 1964:2280 */         genMatchUsingAtomText(paramGrammarAtom);
/* 1965:     */       } else {
/* 1966:2283 */         genMatchUsingAtomTokenType(paramGrammarAtom);
/* 1967:     */       }
/* 1968:     */     }
/* 1969:2286 */     else if ((paramGrammarAtom instanceof CharLiteralElement))
/* 1970:     */     {
/* 1971:2287 */       if ((this.grammar instanceof LexerGrammar)) {
/* 1972:2288 */         genMatchUsingAtomText(paramGrammarAtom);
/* 1973:     */       } else {
/* 1974:2291 */         this.antlrTool.error("cannot ref character literals in grammar: " + paramGrammarAtom);
/* 1975:     */       }
/* 1976:     */     }
/* 1977:2294 */     else if ((paramGrammarAtom instanceof TokenRefElement)) {
/* 1978:2295 */       genMatchUsingAtomText(paramGrammarAtom);
/* 1979:2297 */     } else if ((paramGrammarAtom instanceof WildcardElement)) {
/* 1980:2298 */       gen((WildcardElement)paramGrammarAtom);
/* 1981:     */     }
/* 1982:     */   }
/* 1983:     */   
/* 1984:     */   protected void genMatchUsingAtomText(GrammarAtom paramGrammarAtom)
/* 1985:     */   {
/* 1986:2303 */     int i = this.defaultLine;
/* 1987:     */     try
/* 1988:     */     {
/* 1989:2305 */       this.defaultLine = paramGrammarAtom.getLine();
/* 1990:     */       
/* 1991:2307 */       String str = "";
/* 1992:2308 */       if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 1993:2309 */         str = "_t,";
/* 1994:     */       }
/* 1995:2313 */       if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramGrammarAtom.getAutoGenType() == 3))) {
/* 1996:2314 */         println("_saveIndex=text.length();");
/* 1997:     */       }
/* 1998:2317 */       print(paramGrammarAtom.not ? "matchNot(" : "match(");
/* 1999:2318 */       _print(str, -999);
/* 2000:2321 */       if (paramGrammarAtom.atomText.equals("EOF")) {
/* 2001:2323 */         _print("Token.EOF_TYPE");
/* 2002:     */       } else {
/* 2003:2326 */         _print(paramGrammarAtom.atomText);
/* 2004:     */       }
/* 2005:2328 */       _println(");");
/* 2006:2330 */       if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramGrammarAtom.getAutoGenType() == 3))) {
/* 2007:2331 */         println("text.setLength(_saveIndex);");
/* 2008:     */       }
/* 2009:     */     }
/* 2010:     */     finally
/* 2011:     */     {
/* 2012:2334 */       this.defaultLine = i;
/* 2013:     */     }
/* 2014:     */   }
/* 2015:     */   
/* 2016:     */   protected void genMatchUsingAtomTokenType(GrammarAtom paramGrammarAtom)
/* 2017:     */   {
/* 2018:2340 */     String str1 = "";
/* 2019:2341 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2020:2342 */       str1 = "_t,";
/* 2021:     */     }
/* 2022:2346 */     Object localObject = null;
/* 2023:2347 */     String str2 = str1 + getValueString(paramGrammarAtom.getType());
/* 2024:     */     
/* 2025:     */ 
/* 2026:2350 */     println((paramGrammarAtom.not ? "matchNot(" : "match(") + str2 + ");", paramGrammarAtom.getLine());
/* 2027:     */   }
/* 2028:     */   
/* 2029:     */   public void genNextToken()
/* 2030:     */   {
/* 2031:2358 */     int i = this.defaultLine;
/* 2032:     */     try
/* 2033:     */     {
/* 2034:2360 */       this.defaultLine = -999;
/* 2035:     */       
/* 2036:     */ 
/* 2037:2363 */       int j = 0;
/* 2038:     */       RuleSymbol localRuleSymbol1;
/* 2039:2364 */       for (int k = 0; k < this.grammar.rules.size(); k++)
/* 2040:     */       {
/* 2041:2365 */         localRuleSymbol1 = (RuleSymbol)this.grammar.rules.elementAt(k);
/* 2042:2366 */         if ((localRuleSymbol1.isDefined()) && (localRuleSymbol1.access.equals("public")))
/* 2043:     */         {
/* 2044:2367 */           j = 1;
/* 2045:2368 */           break;
/* 2046:     */         }
/* 2047:     */       }
/* 2048:2371 */       if (j == 0)
/* 2049:     */       {
/* 2050:2372 */         println("");
/* 2051:2373 */         println("public Token nextToken() throws TokenStreamException {");
/* 2052:2374 */         println("\ttry {uponEOF();}");
/* 2053:2375 */         println("\tcatch(CharStreamIOException csioe) {");
/* 2054:2376 */         println("\t\tthrow new TokenStreamIOException(csioe.io);");
/* 2055:2377 */         println("\t}");
/* 2056:2378 */         println("\tcatch(CharStreamException cse) {");
/* 2057:2379 */         println("\t\tthrow new TokenStreamException(cse.getMessage());");
/* 2058:2380 */         println("\t}");
/* 2059:2381 */         println("\treturn new CommonToken(Token.EOF_TYPE, \"\");");
/* 2060:2382 */         println("}");
/* 2061:2383 */         println("");
/* 2062:     */       }
/* 2063:     */       else
/* 2064:     */       {
/* 2065:2388 */         RuleBlock localRuleBlock = MakeGrammar.createNextTokenRule(this.grammar, this.grammar.rules, "nextToken");
/* 2066:     */         
/* 2067:2390 */         localRuleSymbol1 = new RuleSymbol("mnextToken");
/* 2068:2391 */         localRuleSymbol1.setDefined();
/* 2069:2392 */         localRuleSymbol1.setBlock(localRuleBlock);
/* 2070:2393 */         localRuleSymbol1.access = "private";
/* 2071:2394 */         this.grammar.define(localRuleSymbol1);
/* 2072:     */         
/* 2073:2396 */         boolean bool = this.grammar.theLLkAnalyzer.deterministic(localRuleBlock);
/* 2074:     */         
/* 2075:     */ 
/* 2076:2399 */         String str1 = null;
/* 2077:2400 */         if (((LexerGrammar)this.grammar).filterMode) {
/* 2078:2401 */           str1 = ((LexerGrammar)this.grammar).filterRule;
/* 2079:     */         }
/* 2080:2404 */         println("");
/* 2081:2405 */         println("public Token nextToken() throws TokenStreamException {");
/* 2082:2406 */         this.tabs += 1;
/* 2083:2407 */         println("Token theRetToken=null;");
/* 2084:2408 */         _println("tryAgain:");
/* 2085:2409 */         println("for (;;) {");
/* 2086:2410 */         this.tabs += 1;
/* 2087:2411 */         println("Token _token = null;");
/* 2088:2412 */         println("int _ttype = Token.INVALID_TYPE;");
/* 2089:2413 */         if (((LexerGrammar)this.grammar).filterMode)
/* 2090:     */         {
/* 2091:2414 */           println("setCommitToPath(false);");
/* 2092:2415 */           if (str1 != null)
/* 2093:     */           {
/* 2094:2417 */             if (!this.grammar.isDefined(CodeGenerator.encodeLexerRuleName(str1)))
/* 2095:     */             {
/* 2096:2418 */               this.grammar.antlrTool.error("Filter rule " + str1 + " does not exist in this lexer");
/* 2097:     */             }
/* 2098:     */             else
/* 2099:     */             {
/* 2100:2421 */               RuleSymbol localRuleSymbol2 = (RuleSymbol)this.grammar.getSymbol(CodeGenerator.encodeLexerRuleName(str1));
/* 2101:2422 */               if (!localRuleSymbol2.isDefined()) {
/* 2102:2423 */                 this.grammar.antlrTool.error("Filter rule " + str1 + " does not exist in this lexer");
/* 2103:2425 */               } else if (localRuleSymbol2.access.equals("public")) {
/* 2104:2426 */                 this.grammar.antlrTool.error("Filter rule " + str1 + " must be protected");
/* 2105:     */               }
/* 2106:     */             }
/* 2107:2429 */             println("int _m;");
/* 2108:2430 */             println("_m = mark();");
/* 2109:     */           }
/* 2110:     */         }
/* 2111:2433 */         println("resetText();");
/* 2112:     */         
/* 2113:2435 */         println("try {   // for char stream error handling");
/* 2114:2436 */         this.tabs += 1;
/* 2115:     */         
/* 2116:     */ 
/* 2117:2439 */         println("try {   // for lexical error handling");
/* 2118:2440 */         this.tabs += 1;
/* 2119:2443 */         for (int m = 0; m < localRuleBlock.getAlternatives().size(); m++)
/* 2120:     */         {
/* 2121:2444 */           localObject1 = localRuleBlock.getAlternativeAt(m);
/* 2122:2445 */           if (localObject1.cache[1].containsEpsilon())
/* 2123:     */           {
/* 2124:2447 */             localObject2 = (RuleRefElement)((Alternative)localObject1).head;
/* 2125:2448 */             String str3 = CodeGenerator.decodeLexerRuleName(((RuleRefElement)localObject2).targetRule);
/* 2126:2449 */             this.antlrTool.warning("public lexical rule " + str3 + " is optional (can match \"nothing\")");
/* 2127:     */           }
/* 2128:     */         }
/* 2129:2454 */         String str2 = System.getProperty("line.separator");
/* 2130:2455 */         Object localObject1 = genCommonBlock(localRuleBlock, false);
/* 2131:2456 */         Object localObject2 = "if (LA(1)==EOF_CHAR) {uponEOF(); _returnToken = makeToken(Token.EOF_TYPE);}";
/* 2132:2457 */         localObject2 = (String)localObject2 + str2 + "\t\t\t\t";
/* 2133:2458 */         if (((LexerGrammar)this.grammar).filterMode)
/* 2134:     */         {
/* 2135:2459 */           if (str1 == null) {
/* 2136:2460 */             localObject2 = (String)localObject2 + "else {consume(); continue tryAgain;}";
/* 2137:     */           } else {
/* 2138:2463 */             localObject2 = (String)localObject2 + "else {" + str2 + "\t\t\t\t\tcommit();" + str2 + "\t\t\t\t\ttry {m" + str1 + "(false);}" + str2 + "\t\t\t\t\tcatch(RecognitionException e) {" + str2 + "\t\t\t\t\t\t// catastrophic failure" + str2 + "\t\t\t\t\t\treportError(e);" + str2 + "\t\t\t\t\t\tconsume();" + str2 + "\t\t\t\t\t}" + str2 + "\t\t\t\t\tcontinue tryAgain;" + str2 + "\t\t\t\t}";
/* 2139:     */           }
/* 2140:     */         }
/* 2141:     */         else {
/* 2142:2476 */           localObject2 = (String)localObject2 + "else {" + this.throwNoViable + "}";
/* 2143:     */         }
/* 2144:2478 */         genBlockFinish((JavaBlockFinishingInfo)localObject1, (String)localObject2, localRuleBlock.getLine());
/* 2145:2481 */         if ((((LexerGrammar)this.grammar).filterMode) && (str1 != null)) {
/* 2146:2482 */           println("commit();");
/* 2147:     */         }
/* 2148:2488 */         println("if ( _returnToken==null ) continue tryAgain; // found SKIP token");
/* 2149:2489 */         println("_ttype = _returnToken.getType();");
/* 2150:2490 */         if (((LexerGrammar)this.grammar).getTestLiterals()) {
/* 2151:2491 */           genLiteralsTest();
/* 2152:     */         }
/* 2153:2495 */         println("_returnToken.setType(_ttype);");
/* 2154:2496 */         println("return _returnToken;");
/* 2155:     */         
/* 2156:     */ 
/* 2157:2499 */         this.tabs -= 1;
/* 2158:2500 */         println("}");
/* 2159:2501 */         println("catch (RecognitionException e) {");
/* 2160:2502 */         this.tabs += 1;
/* 2161:2503 */         if (((LexerGrammar)this.grammar).filterMode) {
/* 2162:2504 */           if (str1 == null)
/* 2163:     */           {
/* 2164:2505 */             println("if ( !getCommitToPath() ) {consume(); continue tryAgain;}");
/* 2165:     */           }
/* 2166:     */           else
/* 2167:     */           {
/* 2168:2508 */             println("if ( !getCommitToPath() ) {");
/* 2169:2509 */             this.tabs += 1;
/* 2170:2510 */             println("rewind(_m);");
/* 2171:2511 */             println("resetText();");
/* 2172:2512 */             println("try {m" + str1 + "(false);}");
/* 2173:2513 */             println("catch(RecognitionException ee) {");
/* 2174:2514 */             println("\t// horrendous failure: error in filter rule");
/* 2175:2515 */             println("\treportError(ee);");
/* 2176:2516 */             println("\tconsume();");
/* 2177:2517 */             println("}");
/* 2178:2518 */             println("continue tryAgain;");
/* 2179:2519 */             this.tabs -= 1;
/* 2180:2520 */             println("}");
/* 2181:     */           }
/* 2182:     */         }
/* 2183:2523 */         if (localRuleBlock.getDefaultErrorHandler())
/* 2184:     */         {
/* 2185:2524 */           println("reportError(e);");
/* 2186:2525 */           println("consume();");
/* 2187:     */         }
/* 2188:     */         else
/* 2189:     */         {
/* 2190:2529 */           println("throw new TokenStreamRecognitionException(e);");
/* 2191:     */         }
/* 2192:2531 */         this.tabs -= 1;
/* 2193:2532 */         println("}");
/* 2194:     */         
/* 2195:     */ 
/* 2196:2535 */         this.tabs -= 1;
/* 2197:2536 */         println("}");
/* 2198:2537 */         println("catch (CharStreamException cse) {");
/* 2199:2538 */         println("\tif ( cse instanceof CharStreamIOException ) {");
/* 2200:2539 */         println("\t\tthrow new TokenStreamIOException(((CharStreamIOException)cse).io);");
/* 2201:2540 */         println("\t}");
/* 2202:2541 */         println("\telse {");
/* 2203:2542 */         println("\t\tthrow new TokenStreamException(cse.getMessage());");
/* 2204:2543 */         println("\t}");
/* 2205:2544 */         println("}");
/* 2206:     */         
/* 2207:     */ 
/* 2208:2547 */         this.tabs -= 1;
/* 2209:2548 */         println("}");
/* 2210:     */         
/* 2211:     */ 
/* 2212:2551 */         this.tabs -= 1;
/* 2213:2552 */         println("}");
/* 2214:2553 */         println("");
/* 2215:     */       }
/* 2216:     */     }
/* 2217:     */     finally
/* 2218:     */     {
/* 2219:2555 */       this.defaultLine = i;
/* 2220:     */     }
/* 2221:     */   }
/* 2222:     */   
/* 2223:     */   public void genRule(RuleSymbol paramRuleSymbol, boolean paramBoolean, int paramInt)
/* 2224:     */   {
/* 2225:2576 */     this.tabs = 1;
/* 2226:2578 */     if (this.DEBUG_CODE_GENERATOR) {
/* 2227:2578 */       System.out.println("genRule(" + paramRuleSymbol.getId() + ")");
/* 2228:     */     }
/* 2229:2579 */     if (!paramRuleSymbol.isDefined())
/* 2230:     */     {
/* 2231:2580 */       this.antlrTool.error("undefined rule: " + paramRuleSymbol.getId());
/* 2232:2581 */       return;
/* 2233:     */     }
/* 2234:2585 */     RuleBlock localRuleBlock = paramRuleSymbol.getBlock();
/* 2235:     */     
/* 2236:2587 */     int i = this.defaultLine;
/* 2237:     */     try
/* 2238:     */     {
/* 2239:2589 */       this.defaultLine = localRuleBlock.getLine();
/* 2240:2590 */       this.currentRule = localRuleBlock;
/* 2241:2591 */       this.currentASTResult = paramRuleSymbol.getId();
/* 2242:     */       
/* 2243:     */ 
/* 2244:2594 */       this.declaredASTVariables.clear();
/* 2245:     */       
/* 2246:     */ 
/* 2247:2597 */       boolean bool1 = this.genAST;
/* 2248:2598 */       this.genAST = ((this.genAST) && (localRuleBlock.getAutoGen()));
/* 2249:     */       
/* 2250:     */ 
/* 2251:2601 */       this.saveText = localRuleBlock.getAutoGen();
/* 2252:2604 */       if (paramRuleSymbol.comment != null) {
/* 2253:2605 */         _println(paramRuleSymbol.comment);
/* 2254:     */       }
/* 2255:2609 */       print(paramRuleSymbol.access + " final ");
/* 2256:2612 */       if (localRuleBlock.returnAction != null) {
/* 2257:2614 */         _print(extractTypeOfAction(localRuleBlock.returnAction, localRuleBlock.getLine(), localRuleBlock.getColumn()) + " ");
/* 2258:     */       } else {
/* 2259:2618 */         _print("void ");
/* 2260:     */       }
/* 2261:2622 */       _print(paramRuleSymbol.getId() + "(");
/* 2262:     */       
/* 2263:     */ 
/* 2264:2625 */       _print(this.commonExtraParams);
/* 2265:2626 */       if ((this.commonExtraParams.length() != 0) && (localRuleBlock.argAction != null)) {
/* 2266:2627 */         _print(",");
/* 2267:     */       }
/* 2268:2631 */       if (localRuleBlock.argAction != null)
/* 2269:     */       {
/* 2270:2633 */         _println("");
/* 2271:2634 */         this.tabs += 1;
/* 2272:2635 */         println(localRuleBlock.argAction);
/* 2273:2636 */         this.tabs -= 1;
/* 2274:2637 */         print(")");
/* 2275:     */       }
/* 2276:     */       else
/* 2277:     */       {
/* 2278:2641 */         _print(")");
/* 2279:     */       }
/* 2280:2645 */       _print(" throws " + this.exceptionThrown);
/* 2281:2646 */       if ((this.grammar instanceof ParserGrammar)) {
/* 2282:2647 */         _print(", TokenStreamException");
/* 2283:2649 */       } else if ((this.grammar instanceof LexerGrammar)) {
/* 2284:2650 */         _print(", CharStreamException, TokenStreamException");
/* 2285:     */       }
/* 2286:2653 */       if (localRuleBlock.throwsSpec != null) {
/* 2287:2654 */         if ((this.grammar instanceof LexerGrammar)) {
/* 2288:2655 */           this.antlrTool.error("user-defined throws spec not allowed (yet) for lexer rule " + localRuleBlock.ruleName);
/* 2289:     */         } else {
/* 2290:2658 */           _print(", " + localRuleBlock.throwsSpec);
/* 2291:     */         }
/* 2292:     */       }
/* 2293:2662 */       _println(" {");
/* 2294:2663 */       this.tabs += 1;
/* 2295:2666 */       if (localRuleBlock.returnAction != null) {
/* 2296:2667 */         println(localRuleBlock.returnAction + ";");
/* 2297:     */       }
/* 2298:2670 */       println(this.commonLocalVars);
/* 2299:2672 */       if (this.grammar.traceRules) {
/* 2300:2673 */         if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2301:2674 */           println("traceIn(\"" + paramRuleSymbol.getId() + "\",_t);");
/* 2302:     */         } else {
/* 2303:2677 */           println("traceIn(\"" + paramRuleSymbol.getId() + "\");");
/* 2304:     */         }
/* 2305:     */       }
/* 2306:2681 */       if ((this.grammar instanceof LexerGrammar))
/* 2307:     */       {
/* 2308:2684 */         if (paramRuleSymbol.getId().equals("mEOF")) {
/* 2309:2685 */           println("_ttype = Token.EOF_TYPE;");
/* 2310:     */         } else {
/* 2311:2687 */           println("_ttype = " + paramRuleSymbol.getId().substring(1) + ";");
/* 2312:     */         }
/* 2313:2688 */         println("int _saveIndex;");
/* 2314:     */       }
/* 2315:2698 */       if (this.grammar.debuggingOutput) {
/* 2316:2699 */         if ((this.grammar instanceof ParserGrammar)) {
/* 2317:2700 */           println("fireEnterRule(" + paramInt + ",0);");
/* 2318:2701 */         } else if ((this.grammar instanceof LexerGrammar)) {
/* 2319:2702 */           println("fireEnterRule(" + paramInt + ",_ttype);");
/* 2320:     */         }
/* 2321:     */       }
/* 2322:2705 */       if ((this.grammar.debuggingOutput) || (this.grammar.traceRules))
/* 2323:     */       {
/* 2324:2706 */         println("try { // debugging");
/* 2325:2707 */         this.tabs += 1;
/* 2326:     */       }
/* 2327:2711 */       if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2328:2713 */         println(this.labeledElementASTType + " " + paramRuleSymbol.getId() + "_AST_in = (_t == ASTNULL) ? null : (" + this.labeledElementASTType + ")_t;", -999);
/* 2329:     */       }
/* 2330:2715 */       if (this.grammar.buildAST)
/* 2331:     */       {
/* 2332:2717 */         println("returnAST = null;");
/* 2333:     */         
/* 2334:     */ 
/* 2335:2720 */         println("ASTPair currentAST = new ASTPair();");
/* 2336:     */         
/* 2337:2722 */         println(this.labeledElementASTType + " " + paramRuleSymbol.getId() + "_AST = null;");
/* 2338:     */       }
/* 2339:2725 */       genBlockPreamble(localRuleBlock);
/* 2340:2726 */       genBlockInitAction(localRuleBlock);
/* 2341:2727 */       println("");
/* 2342:     */       
/* 2343:     */ 
/* 2344:2730 */       ExceptionSpec localExceptionSpec = localRuleBlock.findExceptionSpec("");
/* 2345:2733 */       if ((localExceptionSpec != null) || (localRuleBlock.getDefaultErrorHandler()))
/* 2346:     */       {
/* 2347:2734 */         println("try {      // for error handling");
/* 2348:2735 */         this.tabs += 1;
/* 2349:     */       }
/* 2350:     */       Object localObject1;
/* 2351:2739 */       if (localRuleBlock.alternatives.size() == 1)
/* 2352:     */       {
/* 2353:2741 */         Alternative localAlternative = localRuleBlock.getAlternativeAt(0);
/* 2354:2742 */         localObject1 = localAlternative.semPred;
/* 2355:2743 */         if (localObject1 != null) {
/* 2356:2744 */           genSemPred((String)localObject1, this.currentRule.line);
/* 2357:     */         }
/* 2358:2745 */         if (localAlternative.synPred != null) {
/* 2359:2746 */           this.antlrTool.warning("Syntactic predicate ignored for single alternative", this.grammar.getFilename(), localAlternative.synPred.getLine(), localAlternative.synPred.getColumn());
/* 2360:     */         }
/* 2361:2753 */         genAlt(localAlternative, localRuleBlock);
/* 2362:     */       }
/* 2363:     */       else
/* 2364:     */       {
/* 2365:2757 */         boolean bool2 = this.grammar.theLLkAnalyzer.deterministic(localRuleBlock);
/* 2366:     */         
/* 2367:2759 */         localObject1 = genCommonBlock(localRuleBlock, false);
/* 2368:2760 */         genBlockFinish((JavaBlockFinishingInfo)localObject1, this.throwNoViable, localRuleBlock.getLine());
/* 2369:     */       }
/* 2370:2764 */       if ((localExceptionSpec != null) || (localRuleBlock.getDefaultErrorHandler()))
/* 2371:     */       {
/* 2372:2766 */         this.tabs -= 1;
/* 2373:2767 */         println("}");
/* 2374:     */       }
/* 2375:2771 */       if (localExceptionSpec != null)
/* 2376:     */       {
/* 2377:2772 */         genErrorHandler(localExceptionSpec);
/* 2378:     */       }
/* 2379:2774 */       else if (localRuleBlock.getDefaultErrorHandler())
/* 2380:     */       {
/* 2381:2776 */         println("catch (" + this.exceptionThrown + " ex) {");
/* 2382:2777 */         this.tabs += 1;
/* 2383:2779 */         if (this.grammar.hasSyntacticPredicate)
/* 2384:     */         {
/* 2385:2780 */           println("if (inputState.guessing==0) {");
/* 2386:2781 */           this.tabs += 1;
/* 2387:     */         }
/* 2388:2783 */         println("reportError(ex);");
/* 2389:2784 */         if (!(this.grammar instanceof TreeWalkerGrammar))
/* 2390:     */         {
/* 2391:2786 */           Lookahead localLookahead = this.grammar.theLLkAnalyzer.FOLLOW(1, localRuleBlock.endNode);
/* 2392:2787 */           localObject1 = getBitsetName(markBitsetForGen(localLookahead.fset));
/* 2393:2788 */           println("recover(ex," + (String)localObject1 + ");");
/* 2394:     */         }
/* 2395:     */         else
/* 2396:     */         {
/* 2397:2792 */           println("if (_t!=null) {_t = _t.getNextSibling();}");
/* 2398:     */         }
/* 2399:2794 */         if (this.grammar.hasSyntacticPredicate)
/* 2400:     */         {
/* 2401:2795 */           this.tabs -= 1;
/* 2402:     */           
/* 2403:2797 */           println("} else {");
/* 2404:2798 */           println("  throw ex;");
/* 2405:2799 */           println("}");
/* 2406:     */         }
/* 2407:2802 */         this.tabs -= 1;
/* 2408:2803 */         println("}");
/* 2409:     */       }
/* 2410:2807 */       if (this.grammar.buildAST) {
/* 2411:2808 */         println("returnAST = " + paramRuleSymbol.getId() + "_AST;");
/* 2412:     */       }
/* 2413:2812 */       if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2414:2813 */         println("_retTree = _t;");
/* 2415:     */       }
/* 2416:2817 */       if (localRuleBlock.getTestLiterals()) {
/* 2417:2818 */         if (paramRuleSymbol.access.equals("protected")) {
/* 2418:2819 */           genLiteralsTestForPartialToken();
/* 2419:     */         } else {
/* 2420:2822 */           genLiteralsTest();
/* 2421:     */         }
/* 2422:     */       }
/* 2423:2827 */       if ((this.grammar instanceof LexerGrammar))
/* 2424:     */       {
/* 2425:2828 */         println("if ( _createToken && _token==null && _ttype!=Token.SKIP ) {");
/* 2426:2829 */         println("\t_token = makeToken(_ttype);");
/* 2427:2830 */         println("\t_token.setText(new String(text.getBuffer(), _begin, text.length()-_begin));");
/* 2428:2831 */         println("}");
/* 2429:2832 */         println("_returnToken = _token;");
/* 2430:     */       }
/* 2431:2836 */       if (localRuleBlock.returnAction != null) {
/* 2432:2837 */         println("return " + extractIdOfAction(localRuleBlock.returnAction, localRuleBlock.getLine(), localRuleBlock.getColumn()) + ";");
/* 2433:     */       }
/* 2434:2840 */       if ((this.grammar.debuggingOutput) || (this.grammar.traceRules))
/* 2435:     */       {
/* 2436:2841 */         this.tabs -= 1;
/* 2437:2842 */         println("} finally { // debugging");
/* 2438:2843 */         this.tabs += 1;
/* 2439:2846 */         if (this.grammar.debuggingOutput) {
/* 2440:2847 */           if ((this.grammar instanceof ParserGrammar)) {
/* 2441:2848 */             println("fireExitRule(" + paramInt + ",0);");
/* 2442:2849 */           } else if ((this.grammar instanceof LexerGrammar)) {
/* 2443:2850 */             println("fireExitRule(" + paramInt + ",_ttype);");
/* 2444:     */           }
/* 2445:     */         }
/* 2446:2852 */         if (this.grammar.traceRules) {
/* 2447:2853 */           if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2448:2854 */             println("traceOut(\"" + paramRuleSymbol.getId() + "\",_t);");
/* 2449:     */           } else {
/* 2450:2857 */             println("traceOut(\"" + paramRuleSymbol.getId() + "\");");
/* 2451:     */           }
/* 2452:     */         }
/* 2453:2861 */         this.tabs -= 1;
/* 2454:2862 */         println("}");
/* 2455:     */       }
/* 2456:2865 */       this.tabs -= 1;
/* 2457:2866 */       println("}");
/* 2458:2867 */       println("");
/* 2459:     */       
/* 2460:     */ 
/* 2461:2870 */       this.genAST = bool1;
/* 2462:     */     }
/* 2463:     */     finally
/* 2464:     */     {
/* 2465:2875 */       this.defaultLine = i;
/* 2466:     */     }
/* 2467:     */   }
/* 2468:     */   
/* 2469:     */   private void GenRuleInvocation(RuleRefElement paramRuleRefElement)
/* 2470:     */   {
/* 2471:2880 */     int i = this.defaultLine;
/* 2472:     */     try
/* 2473:     */     {
/* 2474:2882 */       this.defaultLine = paramRuleRefElement.getLine();
/* 2475:     */       
/* 2476:2884 */       getPrintWriterManager().startSingleSourceLineMapping(paramRuleRefElement.getLine());
/* 2477:2885 */       _print(paramRuleRefElement.targetRule + "(");
/* 2478:2886 */       getPrintWriterManager().endMapping();
/* 2479:2889 */       if ((this.grammar instanceof LexerGrammar))
/* 2480:     */       {
/* 2481:2891 */         if (paramRuleRefElement.getLabel() != null) {
/* 2482:2892 */           _print("true");
/* 2483:     */         } else {
/* 2484:2895 */           _print("false");
/* 2485:     */         }
/* 2486:2897 */         if ((this.commonExtraArgs.length() != 0) || (paramRuleRefElement.args != null)) {
/* 2487:2898 */           _print(",");
/* 2488:     */         }
/* 2489:     */       }
/* 2490:2903 */       _print(this.commonExtraArgs);
/* 2491:2904 */       if ((this.commonExtraArgs.length() != 0) && (paramRuleRefElement.args != null)) {
/* 2492:2905 */         _print(",");
/* 2493:     */       }
/* 2494:2909 */       RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(paramRuleRefElement.targetRule);
/* 2495:2910 */       if (paramRuleRefElement.args != null)
/* 2496:     */       {
/* 2497:2912 */         ActionTransInfo localActionTransInfo = new ActionTransInfo();
/* 2498:2913 */         String str = processActionForSpecialSymbols(paramRuleRefElement.args, 0, this.currentRule, localActionTransInfo);
/* 2499:2914 */         if ((localActionTransInfo.assignToRoot) || (localActionTransInfo.refRuleRoot != null)) {
/* 2500:2915 */           this.antlrTool.error("Arguments of rule reference '" + paramRuleRefElement.targetRule + "' cannot set or ref #" + this.currentRule.getRuleName(), this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/* 2501:     */         }
/* 2502:2918 */         _print(str);
/* 2503:2921 */         if (localRuleSymbol.block.argAction == null) {
/* 2504:2922 */           this.antlrTool.warning("Rule '" + paramRuleRefElement.targetRule + "' accepts no arguments", this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/* 2505:     */         }
/* 2506:     */       }
/* 2507:2928 */       else if (localRuleSymbol.block.argAction != null)
/* 2508:     */       {
/* 2509:2929 */         this.antlrTool.warning("Missing parameters on reference to rule " + paramRuleRefElement.targetRule, this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/* 2510:     */       }
/* 2511:2932 */       _println(");");
/* 2512:2935 */       if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2513:2936 */         println("_t = _retTree;");
/* 2514:     */       }
/* 2515:     */     }
/* 2516:     */     finally
/* 2517:     */     {
/* 2518:2939 */       this.defaultLine = i;
/* 2519:     */     }
/* 2520:     */   }
/* 2521:     */   
/* 2522:     */   protected void genSemPred(String paramString, int paramInt)
/* 2523:     */   {
/* 2524:2945 */     ActionTransInfo localActionTransInfo = new ActionTransInfo();
/* 2525:2946 */     paramString = processActionForSpecialSymbols(paramString, paramInt, this.currentRule, localActionTransInfo);
/* 2526:     */     
/* 2527:2948 */     String str = this.charFormatter.escapeString(paramString);
/* 2528:2952 */     if ((this.grammar.debuggingOutput) && (((this.grammar instanceof ParserGrammar)) || ((this.grammar instanceof LexerGrammar)))) {
/* 2529:2953 */       paramString = "fireSemanticPredicateEvaluated(antlr.debug.SemanticPredicateEvent.VALIDATING," + addSemPred(str) + "," + paramString + ")";
/* 2530:     */     }
/* 2531:2955 */     println("if (!(" + paramString + "))", paramInt);
/* 2532:2956 */     println("  throw new SemanticException(\"" + str + "\");", paramInt);
/* 2533:     */   }
/* 2534:     */   
/* 2535:     */   protected void genSemPredMap()
/* 2536:     */   {
/* 2537:2963 */     Enumeration localEnumeration = this.semPreds.elements();
/* 2538:2964 */     println("private String _semPredNames[] = {", -999);
/* 2539:2965 */     while (localEnumeration.hasMoreElements()) {
/* 2540:2966 */       println("\"" + localEnumeration.nextElement() + "\",", -999);
/* 2541:     */     }
/* 2542:2967 */     println("};", -999);
/* 2543:     */   }
/* 2544:     */   
/* 2545:     */   protected void genSynPred(SynPredBlock paramSynPredBlock, String paramString)
/* 2546:     */   {
/* 2547:2971 */     int i = this.defaultLine;
/* 2548:     */     try
/* 2549:     */     {
/* 2550:2973 */       this.defaultLine = paramSynPredBlock.getLine();
/* 2551:2974 */       if (this.DEBUG_CODE_GENERATOR) {
/* 2552:2974 */         System.out.println("gen=>(" + paramSynPredBlock + ")");
/* 2553:     */       }
/* 2554:2977 */       println("boolean synPredMatched" + paramSynPredBlock.ID + " = false;");
/* 2555:2980 */       if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2556:2981 */         println("if (_t==null) _t=ASTNULL;");
/* 2557:     */       }
/* 2558:2985 */       println("if (" + paramString + ") {");
/* 2559:2986 */       this.tabs += 1;
/* 2560:2989 */       if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2561:2990 */         println("AST __t" + paramSynPredBlock.ID + " = _t;");
/* 2562:     */       } else {
/* 2563:2993 */         println("int _m" + paramSynPredBlock.ID + " = mark();");
/* 2564:     */       }
/* 2565:2997 */       println("synPredMatched" + paramSynPredBlock.ID + " = true;");
/* 2566:2998 */       println("inputState.guessing++;");
/* 2567:3001 */       if ((this.grammar.debuggingOutput) && (((this.grammar instanceof ParserGrammar)) || ((this.grammar instanceof LexerGrammar)))) {
/* 2568:3003 */         println("fireSyntacticPredicateStarted();");
/* 2569:     */       }
/* 2570:3006 */       this.syntacticPredLevel += 1;
/* 2571:3007 */       println("try {");
/* 2572:3008 */       this.tabs += 1;
/* 2573:3009 */       gen(paramSynPredBlock);
/* 2574:3010 */       this.tabs -= 1;
/* 2575:     */       
/* 2576:3012 */       println("}");
/* 2577:3013 */       println("catch (" + this.exceptionThrown + " pe) {");
/* 2578:3014 */       this.tabs += 1;
/* 2579:3015 */       println("synPredMatched" + paramSynPredBlock.ID + " = false;");
/* 2580:     */       
/* 2581:3017 */       this.tabs -= 1;
/* 2582:3018 */       println("}");
/* 2583:3021 */       if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2584:3022 */         println("_t = __t" + paramSynPredBlock.ID + ";");
/* 2585:     */       } else {
/* 2586:3025 */         println("rewind(_m" + paramSynPredBlock.ID + ");");
/* 2587:     */       }
/* 2588:3028 */       _println("inputState.guessing--;");
/* 2589:3031 */       if ((this.grammar.debuggingOutput) && (((this.grammar instanceof ParserGrammar)) || ((this.grammar instanceof LexerGrammar))))
/* 2590:     */       {
/* 2591:3033 */         println("if (synPredMatched" + paramSynPredBlock.ID + ")");
/* 2592:3034 */         println("  fireSyntacticPredicateSucceeded();");
/* 2593:3035 */         println("else");
/* 2594:3036 */         println("  fireSyntacticPredicateFailed();");
/* 2595:     */       }
/* 2596:3039 */       this.syntacticPredLevel -= 1;
/* 2597:3040 */       this.tabs -= 1;
/* 2598:     */       
/* 2599:     */ 
/* 2600:3043 */       println("}");
/* 2601:     */       
/* 2602:     */ 
/* 2603:3046 */       println("if ( synPredMatched" + paramSynPredBlock.ID + " ) {");
/* 2604:     */     }
/* 2605:     */     finally
/* 2606:     */     {
/* 2607:3048 */       this.defaultLine = i;
/* 2608:     */     }
/* 2609:     */   }
/* 2610:     */   
/* 2611:     */   public void genTokenStrings()
/* 2612:     */   {
/* 2613:3061 */     int i = this.defaultLine;
/* 2614:     */     try
/* 2615:     */     {
/* 2616:3063 */       this.defaultLine = -999;
/* 2617:     */       
/* 2618:     */ 
/* 2619:3066 */       println("");
/* 2620:3067 */       println("public static final String[] _tokenNames = {");
/* 2621:3068 */       this.tabs += 1;
/* 2622:     */       
/* 2623:     */ 
/* 2624:     */ 
/* 2625:3072 */       Vector localVector = this.grammar.tokenManager.getVocabulary();
/* 2626:3073 */       for (int j = 0; j < localVector.size(); j++)
/* 2627:     */       {
/* 2628:3074 */         String str = (String)localVector.elementAt(j);
/* 2629:3075 */         if (str == null) {
/* 2630:3076 */           str = "<" + String.valueOf(j) + ">";
/* 2631:     */         }
/* 2632:3078 */         if ((!str.startsWith("\"")) && (!str.startsWith("<")))
/* 2633:     */         {
/* 2634:3079 */           TokenSymbol localTokenSymbol = this.grammar.tokenManager.getTokenSymbol(str);
/* 2635:3080 */           if ((localTokenSymbol != null) && (localTokenSymbol.getParaphrase() != null)) {
/* 2636:3081 */             str = StringUtils.stripFrontBack(localTokenSymbol.getParaphrase(), "\"", "\"");
/* 2637:     */           }
/* 2638:     */         }
/* 2639:3084 */         print(this.charFormatter.literalString(str));
/* 2640:3085 */         if (j != localVector.size() - 1) {
/* 2641:3086 */           _print(",");
/* 2642:     */         }
/* 2643:3088 */         _println("");
/* 2644:     */       }
/* 2645:3092 */       this.tabs -= 1;
/* 2646:3093 */       println("};");
/* 2647:     */     }
/* 2648:     */     finally
/* 2649:     */     {
/* 2650:3095 */       this.defaultLine = i;
/* 2651:     */     }
/* 2652:     */   }
/* 2653:     */   
/* 2654:     */   protected void genTokenASTNodeMap()
/* 2655:     */   {
/* 2656:3103 */     int i = this.defaultLine;
/* 2657:     */     try
/* 2658:     */     {
/* 2659:3105 */       this.defaultLine = -999;
/* 2660:3106 */       println("");
/* 2661:3107 */       println("protected void buildTokenTypeASTClassMap() {");
/* 2662:     */       
/* 2663:     */ 
/* 2664:3110 */       this.tabs += 1;
/* 2665:3111 */       int j = 0;
/* 2666:3112 */       int k = 0;
/* 2667:     */       
/* 2668:3114 */       Vector localVector = this.grammar.tokenManager.getVocabulary();
/* 2669:3115 */       for (int m = 0; m < localVector.size(); m++)
/* 2670:     */       {
/* 2671:3116 */         String str = (String)localVector.elementAt(m);
/* 2672:3117 */         if (str != null)
/* 2673:     */         {
/* 2674:3118 */           TokenSymbol localTokenSymbol = this.grammar.tokenManager.getTokenSymbol(str);
/* 2675:3119 */           if ((localTokenSymbol != null) && (localTokenSymbol.getASTNodeType() != null))
/* 2676:     */           {
/* 2677:3120 */             k++;
/* 2678:3121 */             if (j == 0)
/* 2679:     */             {
/* 2680:3123 */               println("tokenTypeToASTClassMap = new Hashtable();");
/* 2681:3124 */               j = 1;
/* 2682:     */             }
/* 2683:3126 */             println("tokenTypeToASTClassMap.put(new Integer(" + localTokenSymbol.getTokenType() + "), " + localTokenSymbol.getASTNodeType() + ".class);");
/* 2684:     */           }
/* 2685:     */         }
/* 2686:     */       }
/* 2687:3132 */       if (k == 0) {
/* 2688:3133 */         println("tokenTypeToASTClassMap=null;");
/* 2689:     */       }
/* 2690:3135 */       this.tabs -= 1;
/* 2691:3136 */       println("};");
/* 2692:     */     }
/* 2693:     */     finally
/* 2694:     */     {
/* 2695:3138 */       this.defaultLine = i;
/* 2696:     */     }
/* 2697:     */   }
/* 2698:     */   
/* 2699:     */   protected void genTokenTypes(TokenManager paramTokenManager)
/* 2700:     */     throws IOException
/* 2701:     */   {
/* 2702:3144 */     int i = this.defaultLine;
/* 2703:     */     try
/* 2704:     */     {
/* 2705:3146 */       this.defaultLine = -999;
/* 2706:     */       
/* 2707:     */ 
/* 2708:     */ 
/* 2709:3150 */       this.currentOutput = getPrintWriterManager().setupOutput(this.antlrTool, paramTokenManager.getName() + TokenTypesFileSuffix);
/* 2710:     */       
/* 2711:3152 */       this.tabs = 0;
/* 2712:     */       
/* 2713:     */ 
/* 2714:3155 */       genHeader();
/* 2715:     */       try
/* 2716:     */       {
/* 2717:3158 */         this.defaultLine = this.behavior.getHeaderActionLine("");
/* 2718:3159 */         println(this.behavior.getHeaderAction(""));
/* 2719:     */       }
/* 2720:     */       finally
/* 2721:     */       {
/* 2722:3161 */         this.defaultLine = -999;
/* 2723:     */       }
/* 2724:3166 */       println("public interface " + paramTokenManager.getName() + TokenTypesFileSuffix + " {");
/* 2725:3167 */       this.tabs += 1;
/* 2726:     */       
/* 2727:     */ 
/* 2728:3170 */       Vector localVector = paramTokenManager.getVocabulary();
/* 2729:     */       
/* 2730:     */ 
/* 2731:3173 */       println("int EOF = 1;");
/* 2732:3174 */       println("int NULL_TREE_LOOKAHEAD = 3;");
/* 2733:3176 */       for (int j = 4; j < localVector.size(); j++)
/* 2734:     */       {
/* 2735:3177 */         String str1 = (String)localVector.elementAt(j);
/* 2736:3178 */         if (str1 != null) {
/* 2737:3179 */           if (str1.startsWith("\""))
/* 2738:     */           {
/* 2739:3181 */             StringLiteralSymbol localStringLiteralSymbol = (StringLiteralSymbol)paramTokenManager.getTokenSymbol(str1);
/* 2740:3182 */             if (localStringLiteralSymbol == null)
/* 2741:     */             {
/* 2742:3183 */               this.antlrTool.panic("String literal " + str1 + " not in symbol table");
/* 2743:     */             }
/* 2744:3185 */             else if (localStringLiteralSymbol.label != null)
/* 2745:     */             {
/* 2746:3186 */               println("int " + localStringLiteralSymbol.label + " = " + j + ";");
/* 2747:     */             }
/* 2748:     */             else
/* 2749:     */             {
/* 2750:3189 */               String str2 = mangleLiteral(str1);
/* 2751:3190 */               if (str2 != null)
/* 2752:     */               {
/* 2753:3192 */                 println("int " + str2 + " = " + j + ";");
/* 2754:     */                 
/* 2755:3194 */                 localStringLiteralSymbol.label = str2;
/* 2756:     */               }
/* 2757:     */               else
/* 2758:     */               {
/* 2759:3197 */                 println("// " + str1 + " = " + j);
/* 2760:     */               }
/* 2761:     */             }
/* 2762:     */           }
/* 2763:3201 */           else if (!str1.startsWith("<"))
/* 2764:     */           {
/* 2765:3202 */             println("int " + str1 + " = " + j + ";");
/* 2766:     */           }
/* 2767:     */         }
/* 2768:     */       }
/* 2769:3208 */       this.tabs -= 1;
/* 2770:3209 */       println("}");
/* 2771:     */       
/* 2772:     */ 
/* 2773:3212 */       getPrintWriterManager().finishOutput();
/* 2774:3213 */       exitIfError();
/* 2775:     */     }
/* 2776:     */     finally
/* 2777:     */     {
/* 2778:3215 */       this.defaultLine = i;
/* 2779:     */     }
/* 2780:     */   }
/* 2781:     */   
/* 2782:     */   public String getASTCreateString(Vector paramVector)
/* 2783:     */   {
/* 2784:3223 */     if (paramVector.size() == 0) {
/* 2785:3224 */       return "";
/* 2786:     */     }
/* 2787:3226 */     StringBuffer localStringBuffer = new StringBuffer();
/* 2788:3227 */     localStringBuffer.append("(" + this.labeledElementASTType + ")astFactory.make( (new ASTArray(" + paramVector.size() + "))");
/* 2789:3230 */     for (int i = 0; i < paramVector.size(); i++) {
/* 2790:3231 */       localStringBuffer.append(".add(" + paramVector.elementAt(i) + ")");
/* 2791:     */     }
/* 2792:3233 */     localStringBuffer.append(")");
/* 2793:3234 */     return localStringBuffer.toString();
/* 2794:     */   }
/* 2795:     */   
/* 2796:     */   public String getASTCreateString(GrammarAtom paramGrammarAtom, String paramString)
/* 2797:     */   {
/* 2798:3243 */     if ((paramGrammarAtom != null) && (paramGrammarAtom.getASTNodeType() != null)) {
/* 2799:3245 */       return "(" + paramGrammarAtom.getASTNodeType() + ")" + "astFactory.create(" + paramString + ",\"" + paramGrammarAtom.getASTNodeType() + "\")";
/* 2800:     */     }
/* 2801:3250 */     return getASTCreateString(paramString);
/* 2802:     */   }
/* 2803:     */   
/* 2804:     */   public String getASTCreateString(String paramString)
/* 2805:     */   {
/* 2806:3264 */     if (paramString == null) {
/* 2807:3265 */       paramString = "";
/* 2808:     */     }
/* 2809:3267 */     int i = 0;
/* 2810:3268 */     for (int j = 0; j < paramString.length(); j++) {
/* 2811:3269 */       if (paramString.charAt(j) == ',') {
/* 2812:3270 */         i++;
/* 2813:     */       }
/* 2814:     */     }
/* 2815:3274 */     if (i < 2)
/* 2816:     */     {
/* 2817:3275 */       j = paramString.indexOf(',');
/* 2818:3276 */       int k = paramString.lastIndexOf(',');
/* 2819:3277 */       String str1 = paramString;
/* 2820:3278 */       if (i > 0) {
/* 2821:3279 */         str1 = paramString.substring(0, j);
/* 2822:     */       }
/* 2823:3282 */       TokenSymbol localTokenSymbol = this.grammar.tokenManager.getTokenSymbol(str1);
/* 2824:3283 */       if (localTokenSymbol != null)
/* 2825:     */       {
/* 2826:3284 */         String str2 = localTokenSymbol.getASTNodeType();
/* 2827:     */         
/* 2828:3286 */         String str3 = "";
/* 2829:3287 */         if (i == 0) {
/* 2830:3289 */           str3 = ",\"\"";
/* 2831:     */         }
/* 2832:3291 */         if (str2 != null) {
/* 2833:3292 */           return "(" + str2 + ")" + "astFactory.create(" + paramString + str3 + ",\"" + str2 + "\")";
/* 2834:     */         }
/* 2835:     */       }
/* 2836:3298 */       if (this.labeledElementASTType.equals("AST")) {
/* 2837:3299 */         return "astFactory.create(" + paramString + ")";
/* 2838:     */       }
/* 2839:3301 */       return "(" + this.labeledElementASTType + ")" + "astFactory.create(" + paramString + ")";
/* 2840:     */     }
/* 2841:3305 */     return "(" + this.labeledElementASTType + ")astFactory.create(" + paramString + ")";
/* 2842:     */   }
/* 2843:     */   
/* 2844:     */   protected String getLookaheadTestExpression(Lookahead[] paramArrayOfLookahead, int paramInt)
/* 2845:     */   {
/* 2846:3309 */     StringBuffer localStringBuffer = new StringBuffer(100);
/* 2847:3310 */     int i = 1;
/* 2848:     */     
/* 2849:3312 */     localStringBuffer.append("(");
/* 2850:3313 */     for (int j = 1; j <= paramInt; j++)
/* 2851:     */     {
/* 2852:3314 */       BitSet localBitSet = paramArrayOfLookahead[j].fset;
/* 2853:3315 */       if (i == 0) {
/* 2854:3316 */         localStringBuffer.append(") && (");
/* 2855:     */       }
/* 2856:3318 */       i = 0;
/* 2857:3323 */       if (paramArrayOfLookahead[j].containsEpsilon()) {
/* 2858:3324 */         localStringBuffer.append("true");
/* 2859:     */       } else {
/* 2860:3327 */         localStringBuffer.append(getLookaheadTestTerm(j, localBitSet));
/* 2861:     */       }
/* 2862:     */     }
/* 2863:3330 */     localStringBuffer.append(")");
/* 2864:     */     
/* 2865:3332 */     return localStringBuffer.toString();
/* 2866:     */   }
/* 2867:     */   
/* 2868:     */   protected String getLookaheadTestExpression(Alternative paramAlternative, int paramInt)
/* 2869:     */   {
/* 2870:3340 */     int i = paramAlternative.lookaheadDepth;
/* 2871:3341 */     if (i == 2147483647) {
/* 2872:3344 */       i = this.grammar.maxk;
/* 2873:     */     }
/* 2874:3347 */     if (paramInt == 0) {
/* 2875:3350 */       return "( true )";
/* 2876:     */     }
/* 2877:3353 */     return "(" + getLookaheadTestExpression(paramAlternative.cache, i) + ")";
/* 2878:     */   }
/* 2879:     */   
/* 2880:     */   protected String getLookaheadTestTerm(int paramInt, BitSet paramBitSet)
/* 2881:     */   {
/* 2882:3366 */     String str1 = lookaheadString(paramInt);
/* 2883:     */     
/* 2884:     */ 
/* 2885:3369 */     int[] arrayOfInt = paramBitSet.toArray();
/* 2886:3370 */     if (elementsAreRange(arrayOfInt)) {
/* 2887:3371 */       return getRangeExpression(paramInt, arrayOfInt);
/* 2888:     */     }
/* 2889:3376 */     int i = paramBitSet.degree();
/* 2890:3377 */     if (i == 0) {
/* 2891:3378 */       return "true";
/* 2892:     */     }
/* 2893:3381 */     if (i >= this.bitsetTestThreshold)
/* 2894:     */     {
/* 2895:3382 */       j = markBitsetForGen(paramBitSet);
/* 2896:3383 */       return getBitsetName(j) + ".member(" + str1 + ")";
/* 2897:     */     }
/* 2898:3387 */     StringBuffer localStringBuffer = new StringBuffer();
/* 2899:3388 */     for (int j = 0; j < arrayOfInt.length; j++)
/* 2900:     */     {
/* 2901:3390 */       String str2 = getValueString(arrayOfInt[j]);
/* 2902:3393 */       if (j > 0) {
/* 2903:3393 */         localStringBuffer.append("||");
/* 2904:     */       }
/* 2905:3394 */       localStringBuffer.append(str1);
/* 2906:3395 */       localStringBuffer.append("==");
/* 2907:3396 */       localStringBuffer.append(str2);
/* 2908:     */     }
/* 2909:3398 */     return localStringBuffer.toString();
/* 2910:     */   }
/* 2911:     */   
/* 2912:     */   public String getRangeExpression(int paramInt, int[] paramArrayOfInt)
/* 2913:     */   {
/* 2914:3407 */     if (!elementsAreRange(paramArrayOfInt)) {
/* 2915:3408 */       this.antlrTool.panic("getRangeExpression called with non-range");
/* 2916:     */     }
/* 2917:3410 */     int i = paramArrayOfInt[0];
/* 2918:3411 */     int j = paramArrayOfInt[(paramArrayOfInt.length - 1)];
/* 2919:3412 */     return "(" + lookaheadString(paramInt) + " >= " + getValueString(i) + " && " + lookaheadString(paramInt) + " <= " + getValueString(j) + ")";
/* 2920:     */   }
/* 2921:     */   
/* 2922:     */   private String getValueString(int paramInt)
/* 2923:     */   {
/* 2924:     */     Object localObject;
/* 2925:3422 */     if ((this.grammar instanceof LexerGrammar))
/* 2926:     */     {
/* 2927:3423 */       localObject = this.charFormatter.literalChar(paramInt);
/* 2928:     */     }
/* 2929:     */     else
/* 2930:     */     {
/* 2931:3426 */       TokenSymbol localTokenSymbol = this.grammar.tokenManager.getTokenSymbolAt(paramInt);
/* 2932:3427 */       if (localTokenSymbol == null) {
/* 2933:3428 */         return "" + paramInt;
/* 2934:     */       }
/* 2935:3431 */       String str1 = localTokenSymbol.getId();
/* 2936:3432 */       if ((localTokenSymbol instanceof StringLiteralSymbol))
/* 2937:     */       {
/* 2938:3436 */         StringLiteralSymbol localStringLiteralSymbol = (StringLiteralSymbol)localTokenSymbol;
/* 2939:3437 */         String str2 = localStringLiteralSymbol.getLabel();
/* 2940:3438 */         if (str2 != null)
/* 2941:     */         {
/* 2942:3439 */           localObject = str2;
/* 2943:     */         }
/* 2944:     */         else
/* 2945:     */         {
/* 2946:3442 */           localObject = mangleLiteral(str1);
/* 2947:3443 */           if (localObject == null) {
/* 2948:3444 */             localObject = String.valueOf(paramInt);
/* 2949:     */           }
/* 2950:     */         }
/* 2951:     */       }
/* 2952:     */       else
/* 2953:     */       {
/* 2954:3449 */         localObject = str1;
/* 2955:     */       }
/* 2956:     */     }
/* 2957:3452 */     return localObject;
/* 2958:     */   }
/* 2959:     */   
/* 2960:     */   protected boolean lookaheadIsEmpty(Alternative paramAlternative, int paramInt)
/* 2961:     */   {
/* 2962:3457 */     int i = paramAlternative.lookaheadDepth;
/* 2963:3458 */     if (i == 2147483647) {
/* 2964:3459 */       i = this.grammar.maxk;
/* 2965:     */     }
/* 2966:3461 */     for (int j = 1; (j <= i) && (j <= paramInt); j++)
/* 2967:     */     {
/* 2968:3462 */       BitSet localBitSet = paramAlternative.cache[j].fset;
/* 2969:3463 */       if (localBitSet.degree() != 0) {
/* 2970:3464 */         return false;
/* 2971:     */       }
/* 2972:     */     }
/* 2973:3467 */     return true;
/* 2974:     */   }
/* 2975:     */   
/* 2976:     */   private String lookaheadString(int paramInt)
/* 2977:     */   {
/* 2978:3471 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2979:3472 */       return "_t.getType()";
/* 2980:     */     }
/* 2981:3474 */     return "LA(" + paramInt + ")";
/* 2982:     */   }
/* 2983:     */   
/* 2984:     */   private String mangleLiteral(String paramString)
/* 2985:     */   {
/* 2986:3484 */     String str = this.antlrTool.literalsPrefix;
/* 2987:3485 */     for (int i = 1; i < paramString.length() - 1; i++)
/* 2988:     */     {
/* 2989:3486 */       if ((!Character.isLetter(paramString.charAt(i))) && (paramString.charAt(i) != '_')) {
/* 2990:3488 */         return null;
/* 2991:     */       }
/* 2992:3490 */       str = str + paramString.charAt(i);
/* 2993:     */     }
/* 2994:3492 */     if (this.antlrTool.upperCaseMangledLiterals) {
/* 2995:3493 */       str = str.toUpperCase();
/* 2996:     */     }
/* 2997:3495 */     return str;
/* 2998:     */   }
/* 2999:     */   
/* 3000:     */   public String mapTreeId(String paramString, ActionTransInfo paramActionTransInfo)
/* 3001:     */   {
/* 3002:3506 */     if (this.currentRule == null) {
/* 3003:3506 */       return paramString;
/* 3004:     */     }
/* 3005:3508 */     int i = 0;
/* 3006:3509 */     String str1 = paramString;
/* 3007:3510 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 3008:3511 */       if (!this.grammar.buildAST)
/* 3009:     */       {
/* 3010:3512 */         i = 1;
/* 3011:     */       }
/* 3012:3515 */       else if ((str1.length() > 3) && (str1.lastIndexOf("_in") == str1.length() - 3))
/* 3013:     */       {
/* 3014:3517 */         str1 = str1.substring(0, str1.length() - 3);
/* 3015:3518 */         i = 1;
/* 3016:     */       }
/* 3017:     */     }
/* 3018:     */     Object localObject;
/* 3019:3524 */     for (int j = 0; j < this.currentRule.labeledElements.size(); j++)
/* 3020:     */     {
/* 3021:3525 */       localObject = (AlternativeElement)this.currentRule.labeledElements.elementAt(j);
/* 3022:3526 */       if (((AlternativeElement)localObject).getLabel().equals(str1)) {
/* 3023:3527 */         return str1 + "_AST";
/* 3024:     */       }
/* 3025:     */     }
/* 3026:3534 */     String str2 = (String)this.treeVariableMap.get(str1);
/* 3027:3535 */     if (str2 != null)
/* 3028:     */     {
/* 3029:3536 */       if (str2 == NONUNIQUE)
/* 3030:     */       {
/* 3031:3538 */         this.antlrTool.error("Ambiguous reference to AST element " + str1 + " in rule " + this.currentRule.getRuleName());
/* 3032:     */         
/* 3033:     */ 
/* 3034:3541 */         return null;
/* 3035:     */       }
/* 3036:3543 */       if (str2.equals(this.currentRule.getRuleName()))
/* 3037:     */       {
/* 3038:3546 */         this.antlrTool.error("Ambiguous reference to AST element " + str1 + " in rule " + this.currentRule.getRuleName());
/* 3039:     */         
/* 3040:3548 */         return null;
/* 3041:     */       }
/* 3042:3551 */       return i != 0 ? str2 + "_in" : str2;
/* 3043:     */     }
/* 3044:3557 */     if (str1.equals(this.currentRule.getRuleName()))
/* 3045:     */     {
/* 3046:3558 */       localObject = str1 + "_AST";
/* 3047:3559 */       if ((paramActionTransInfo != null) && 
/* 3048:3560 */         (i == 0)) {
/* 3049:3561 */         paramActionTransInfo.refRuleRoot = ((String)localObject);
/* 3050:     */       }
/* 3051:3564 */       return localObject;
/* 3052:     */     }
/* 3053:3568 */     return str1;
/* 3054:     */   }
/* 3055:     */   
/* 3056:     */   private void mapTreeVariable(AlternativeElement paramAlternativeElement, String paramString)
/* 3057:     */   {
/* 3058:3577 */     if ((paramAlternativeElement instanceof TreeElement))
/* 3059:     */     {
/* 3060:3578 */       mapTreeVariable(((TreeElement)paramAlternativeElement).root, paramString);
/* 3061:3579 */       return;
/* 3062:     */     }
/* 3063:3583 */     String str = null;
/* 3064:3586 */     if (paramAlternativeElement.getLabel() == null) {
/* 3065:3587 */       if ((paramAlternativeElement instanceof TokenRefElement)) {
/* 3066:3589 */         str = ((TokenRefElement)paramAlternativeElement).atomText;
/* 3067:3591 */       } else if ((paramAlternativeElement instanceof RuleRefElement)) {
/* 3068:3593 */         str = ((RuleRefElement)paramAlternativeElement).targetRule;
/* 3069:     */       }
/* 3070:     */     }
/* 3071:3597 */     if (str != null) {
/* 3072:3598 */       if (this.treeVariableMap.get(str) != null)
/* 3073:     */       {
/* 3074:3600 */         this.treeVariableMap.remove(str);
/* 3075:3601 */         this.treeVariableMap.put(str, NONUNIQUE);
/* 3076:     */       }
/* 3077:     */       else
/* 3078:     */       {
/* 3079:3604 */         this.treeVariableMap.put(str, paramString);
/* 3080:     */       }
/* 3081:     */     }
/* 3082:     */   }
/* 3083:     */   
/* 3084:     */   protected String processActionForSpecialSymbols(String paramString, int paramInt, RuleBlock paramRuleBlock, ActionTransInfo paramActionTransInfo)
/* 3085:     */   {
/* 3086:3617 */     if ((paramString == null) || (paramString.length() == 0)) {
/* 3087:3617 */       return null;
/* 3088:     */     }
/* 3089:3621 */     if (this.grammar == null) {
/* 3090:3622 */       return paramString;
/* 3091:     */     }
/* 3092:3625 */     if (((this.grammar.buildAST) && (paramString.indexOf('#') != -1)) || ((this.grammar instanceof TreeWalkerGrammar)) || ((((this.grammar instanceof LexerGrammar)) || ((this.grammar instanceof ParserGrammar))) && (paramString.indexOf('$') != -1)))
/* 3093:     */     {
/* 3094:3631 */       ActionLexer localActionLexer = new ActionLexer(paramString, paramRuleBlock, this, paramActionTransInfo);
/* 3095:     */       
/* 3096:     */ 
/* 3097:     */ 
/* 3098:     */ 
/* 3099:     */ 
/* 3100:3637 */       localActionLexer.setLineOffset(paramInt);
/* 3101:3638 */       localActionLexer.setFilename(this.grammar.getFilename());
/* 3102:3639 */       localActionLexer.setTool(this.antlrTool);
/* 3103:     */       try
/* 3104:     */       {
/* 3105:3642 */         localActionLexer.mACTION(true);
/* 3106:3643 */         paramString = localActionLexer.getTokenObject().getText();
/* 3107:     */       }
/* 3108:     */       catch (RecognitionException localRecognitionException)
/* 3109:     */       {
/* 3110:3648 */         localActionLexer.reportError(localRecognitionException);
/* 3111:3649 */         return paramString;
/* 3112:     */       }
/* 3113:     */       catch (TokenStreamException localTokenStreamException)
/* 3114:     */       {
/* 3115:3652 */         this.antlrTool.panic("Error reading action:" + paramString);
/* 3116:3653 */         return paramString;
/* 3117:     */       }
/* 3118:     */       catch (CharStreamException localCharStreamException)
/* 3119:     */       {
/* 3120:3656 */         this.antlrTool.panic("Error reading action:" + paramString);
/* 3121:3657 */         return paramString;
/* 3122:     */       }
/* 3123:     */     }
/* 3124:3660 */     return paramString;
/* 3125:     */   }
/* 3126:     */   
/* 3127:     */   private void setupGrammarParameters(Grammar paramGrammar)
/* 3128:     */   {
/* 3129:     */     Token localToken;
/* 3130:     */     String str;
/* 3131:3664 */     if ((paramGrammar instanceof ParserGrammar))
/* 3132:     */     {
/* 3133:3665 */       this.labeledElementASTType = "AST";
/* 3134:3666 */       if (paramGrammar.hasOption("ASTLabelType"))
/* 3135:     */       {
/* 3136:3667 */         localToken = paramGrammar.getOption("ASTLabelType");
/* 3137:3668 */         if (localToken != null)
/* 3138:     */         {
/* 3139:3669 */           str = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/* 3140:3670 */           if (str != null) {
/* 3141:3671 */             this.labeledElementASTType = str;
/* 3142:     */           }
/* 3143:     */         }
/* 3144:     */       }
/* 3145:3675 */       this.labeledElementType = "Token ";
/* 3146:3676 */       this.labeledElementInit = "null";
/* 3147:3677 */       this.commonExtraArgs = "";
/* 3148:3678 */       this.commonExtraParams = "";
/* 3149:3679 */       this.commonLocalVars = "";
/* 3150:3680 */       this.lt1Value = "LT(1)";
/* 3151:3681 */       this.exceptionThrown = "RecognitionException";
/* 3152:3682 */       this.throwNoViable = "throw new NoViableAltException(LT(1), getFilename());";
/* 3153:     */     }
/* 3154:3684 */     else if ((paramGrammar instanceof LexerGrammar))
/* 3155:     */     {
/* 3156:3685 */       this.labeledElementType = "char ";
/* 3157:3686 */       this.labeledElementInit = "'\\0'";
/* 3158:3687 */       this.commonExtraArgs = "";
/* 3159:3688 */       this.commonExtraParams = "boolean _createToken";
/* 3160:3689 */       this.commonLocalVars = "int _ttype; Token _token=null; int _begin=text.length();";
/* 3161:3690 */       this.lt1Value = "LA(1)";
/* 3162:3691 */       this.exceptionThrown = "RecognitionException";
/* 3163:3692 */       this.throwNoViable = "throw new NoViableAltForCharException((char)LA(1), getFilename(), getLine(), getColumn());";
/* 3164:     */     }
/* 3165:3694 */     else if ((paramGrammar instanceof TreeWalkerGrammar))
/* 3166:     */     {
/* 3167:3695 */       this.labeledElementASTType = "AST";
/* 3168:3696 */       this.labeledElementType = "AST";
/* 3169:3697 */       if (paramGrammar.hasOption("ASTLabelType"))
/* 3170:     */       {
/* 3171:3698 */         localToken = paramGrammar.getOption("ASTLabelType");
/* 3172:3699 */         if (localToken != null)
/* 3173:     */         {
/* 3174:3700 */           str = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/* 3175:3701 */           if (str != null)
/* 3176:     */           {
/* 3177:3702 */             this.labeledElementASTType = str;
/* 3178:3703 */             this.labeledElementType = str;
/* 3179:     */           }
/* 3180:     */         }
/* 3181:     */       }
/* 3182:3707 */       if (!paramGrammar.hasOption("ASTLabelType")) {
/* 3183:3708 */         paramGrammar.setOption("ASTLabelType", new Token(6, "AST"));
/* 3184:     */       }
/* 3185:3710 */       this.labeledElementInit = "null";
/* 3186:3711 */       this.commonExtraArgs = "_t";
/* 3187:3712 */       this.commonExtraParams = "AST _t";
/* 3188:3713 */       this.commonLocalVars = "";
/* 3189:3714 */       this.lt1Value = ("(" + this.labeledElementASTType + ")_t");
/* 3190:3715 */       this.exceptionThrown = "RecognitionException";
/* 3191:3716 */       this.throwNoViable = "throw new NoViableAltException(_t);";
/* 3192:     */     }
/* 3193:     */     else
/* 3194:     */     {
/* 3195:3719 */       this.antlrTool.panic("Unknown grammar type");
/* 3196:     */     }
/* 3197:     */   }
/* 3198:     */   
/* 3199:     */   public JavaCodeGeneratorPrintWriterManager getPrintWriterManager()
/* 3200:     */   {
/* 3201:3728 */     if (this.printWriterManager == null) {
/* 3202:3729 */       this.printWriterManager = new DefaultJavaCodeGeneratorPrintWriterManager();
/* 3203:     */     }
/* 3204:3730 */     return this.printWriterManager;
/* 3205:     */   }
/* 3206:     */   
/* 3207:     */   public void setPrintWriterManager(JavaCodeGeneratorPrintWriterManager paramJavaCodeGeneratorPrintWriterManager)
/* 3208:     */   {
/* 3209:3738 */     this.printWriterManager = paramJavaCodeGeneratorPrintWriterManager;
/* 3210:     */   }
/* 3211:     */   
/* 3212:     */   public void setTool(Tool paramTool)
/* 3213:     */   {
/* 3214:3743 */     super.setTool(paramTool);
/* 3215:     */   }
/* 3216:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.JavaCodeGenerator
 * JD-Core Version:    0.7.0.1
 */