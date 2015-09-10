/*    1:     */ package antlr;
/*    2:     */ 
/*    3:     */ import antlr.actions.cpp.ActionLexer;
/*    4:     */ import antlr.collections.impl.BitSet;
/*    5:     */ import antlr.collections.impl.Vector;
/*    6:     */ import java.io.IOException;
/*    7:     */ import java.io.PrintStream;
/*    8:     */ import java.io.PrintWriter;
/*    9:     */ import java.util.Enumeration;
/*   10:     */ import java.util.Hashtable;
/*   11:     */ 
/*   12:     */ public class CppCodeGenerator
/*   13:     */   extends CodeGenerator
/*   14:     */ {
/*   15:  25 */   boolean DEBUG_CPP_CODE_GENERATOR = false;
/*   16:  27 */   protected int syntacticPredLevel = 0;
/*   17:  30 */   protected boolean genAST = false;
/*   18:  33 */   protected boolean saveText = false;
/*   19:  36 */   protected boolean genHashLines = true;
/*   20:  38 */   protected boolean noConstructors = false;
/*   21:     */   protected int outputLine;
/*   22:     */   protected String outputFile;
/*   23:  46 */   boolean usingCustomAST = false;
/*   24:     */   String labeledElementType;
/*   25:     */   String labeledElementASTType;
/*   26:     */   String labeledElementASTInit;
/*   27:     */   String labeledElementInit;
/*   28:     */   String commonExtraArgs;
/*   29:     */   String commonExtraParams;
/*   30:     */   String commonLocalVars;
/*   31:     */   String lt1Value;
/*   32:     */   String exceptionThrown;
/*   33:     */   String throwNoViable;
/*   34:     */   RuleBlock currentRule;
/*   35:     */   String currentASTResult;
/*   36:  64 */   Hashtable treeVariableMap = new Hashtable();
/*   37:  69 */   Hashtable declaredASTVariables = new Hashtable();
/*   38:  72 */   int astVarNumber = 1;
/*   39:  74 */   protected static final String NONUNIQUE = new String();
/*   40:     */   public static final int caseSizeThreshold = 127;
/*   41:     */   private Vector semPreds;
/*   42:     */   private Vector astTypes;
/*   43:  84 */   private static String namespaceStd = "ANTLR_USE_NAMESPACE(std)";
/*   44:  85 */   private static String namespaceAntlr = "ANTLR_USE_NAMESPACE(antlr)";
/*   45:  86 */   private static NameSpace nameSpace = null;
/*   46:     */   private static final String preIncludeCpp = "pre_include_cpp";
/*   47:     */   private static final String preIncludeHpp = "pre_include_hpp";
/*   48:     */   private static final String postIncludeCpp = "post_include_cpp";
/*   49:     */   private static final String postIncludeHpp = "post_include_hpp";
/*   50:     */   
/*   51:     */   public CppCodeGenerator()
/*   52:     */   {
/*   53:  99 */     this.charFormatter = new CppCharFormatter();
/*   54:     */   }
/*   55:     */   
/*   56:     */   protected int addSemPred(String paramString)
/*   57:     */   {
/*   58: 107 */     this.semPreds.appendElement(paramString);
/*   59: 108 */     return this.semPreds.size() - 1;
/*   60:     */   }
/*   61:     */   
/*   62:     */   public void exitIfError()
/*   63:     */   {
/*   64: 112 */     if (this.antlrTool.hasError()) {
/*   65: 114 */       this.antlrTool.fatalError("Exiting due to errors.");
/*   66:     */     }
/*   67:     */   }
/*   68:     */   
/*   69:     */   protected int countLines(String paramString)
/*   70:     */   {
/*   71: 119 */     int i = 0;
/*   72: 120 */     for (int j = 0; j < paramString.length(); j++) {
/*   73: 122 */       if (paramString.charAt(j) == '\n') {
/*   74: 123 */         i++;
/*   75:     */       }
/*   76:     */     }
/*   77: 125 */     return i;
/*   78:     */   }
/*   79:     */   
/*   80:     */   protected void _print(String paramString)
/*   81:     */   {
/*   82: 133 */     if (paramString != null)
/*   83:     */     {
/*   84: 135 */       this.outputLine += countLines(paramString);
/*   85: 136 */       this.currentOutput.print(paramString);
/*   86:     */     }
/*   87:     */   }
/*   88:     */   
/*   89:     */   protected void _printAction(String paramString)
/*   90:     */   {
/*   91: 146 */     if (paramString != null)
/*   92:     */     {
/*   93: 148 */       this.outputLine += countLines(paramString) + 1;
/*   94: 149 */       super._printAction(paramString);
/*   95:     */     }
/*   96:     */   }
/*   97:     */   
/*   98:     */   public void printAction(Token paramToken)
/*   99:     */   {
/*  100: 155 */     if (paramToken != null)
/*  101:     */     {
/*  102: 157 */       genLineNo(paramToken.getLine());
/*  103: 158 */       printTabs();
/*  104: 159 */       _printAction(processActionForSpecialSymbols(paramToken.getText(), paramToken.getLine(), null, null));
/*  105:     */       
/*  106: 161 */       genLineNo2();
/*  107:     */     }
/*  108:     */   }
/*  109:     */   
/*  110:     */   public void printHeaderAction(String paramString)
/*  111:     */   {
/*  112: 169 */     Token localToken = (Token)this.behavior.headerActions.get(paramString);
/*  113: 170 */     if (localToken != null)
/*  114:     */     {
/*  115: 172 */       genLineNo(localToken.getLine());
/*  116: 173 */       println(processActionForSpecialSymbols(localToken.getText(), localToken.getLine(), null, null));
/*  117:     */       
/*  118: 175 */       genLineNo2();
/*  119:     */     }
/*  120:     */   }
/*  121:     */   
/*  122:     */   protected void _println(String paramString)
/*  123:     */   {
/*  124: 183 */     if (paramString != null)
/*  125:     */     {
/*  126: 184 */       this.outputLine += countLines(paramString) + 1;
/*  127: 185 */       this.currentOutput.println(paramString);
/*  128:     */     }
/*  129:     */   }
/*  130:     */   
/*  131:     */   protected void println(String paramString)
/*  132:     */   {
/*  133: 193 */     if (paramString != null)
/*  134:     */     {
/*  135: 194 */       printTabs();
/*  136: 195 */       this.outputLine += countLines(paramString) + 1;
/*  137: 196 */       this.currentOutput.println(paramString);
/*  138:     */     }
/*  139:     */   }
/*  140:     */   
/*  141:     */   public void genLineNo(int paramInt)
/*  142:     */   {
/*  143: 202 */     if (paramInt == 0) {
/*  144: 203 */       paramInt++;
/*  145:     */     }
/*  146: 205 */     if (this.genHashLines) {
/*  147: 206 */       _println("#line " + paramInt + " \"" + this.antlrTool.fileMinusPath(this.antlrTool.grammarFile) + "\"");
/*  148:     */     }
/*  149:     */   }
/*  150:     */   
/*  151:     */   public void genLineNo(GrammarElement paramGrammarElement)
/*  152:     */   {
/*  153: 212 */     if (paramGrammarElement != null) {
/*  154: 213 */       genLineNo(paramGrammarElement.getLine());
/*  155:     */     }
/*  156:     */   }
/*  157:     */   
/*  158:     */   public void genLineNo(Token paramToken)
/*  159:     */   {
/*  160: 218 */     if (paramToken != null) {
/*  161: 219 */       genLineNo(paramToken.getLine());
/*  162:     */     }
/*  163:     */   }
/*  164:     */   
/*  165:     */   public void genLineNo2()
/*  166:     */   {
/*  167: 224 */     if (this.genHashLines) {
/*  168: 226 */       _println("#line " + (this.outputLine + 1) + " \"" + this.outputFile + "\"");
/*  169:     */     }
/*  170:     */   }
/*  171:     */   
/*  172:     */   private boolean charIsDigit(String paramString, int paramInt)
/*  173:     */   {
/*  174: 232 */     return (paramInt < paramString.length()) && (Character.isDigit(paramString.charAt(paramInt)));
/*  175:     */   }
/*  176:     */   
/*  177:     */   private String convertJavaToCppString(String paramString, boolean paramBoolean)
/*  178:     */   {
/*  179: 249 */     String str1 = new String();
/*  180: 250 */     String str2 = paramString;
/*  181:     */     
/*  182: 252 */     int i = 0;
/*  183: 253 */     int j = 0;
/*  184: 255 */     if (paramBoolean)
/*  185:     */     {
/*  186: 257 */       if ((!paramString.startsWith("'")) || (!paramString.endsWith("'"))) {
/*  187: 258 */         this.antlrTool.error("Invalid character literal: '" + paramString + "'");
/*  188:     */       }
/*  189:     */     }
/*  190: 262 */     else if ((!paramString.startsWith("\"")) || (!paramString.endsWith("\""))) {
/*  191: 263 */       this.antlrTool.error("Invalid character string: '" + paramString + "'");
/*  192:     */     }
/*  193: 265 */     str2 = paramString.substring(1, paramString.length() - 1);
/*  194:     */     
/*  195: 267 */     String str3 = "";
/*  196: 268 */     int k = 255;
/*  197: 269 */     if ((this.grammar instanceof LexerGrammar))
/*  198:     */     {
/*  199: 272 */       k = ((LexerGrammar)this.grammar).charVocabulary.size() - 1;
/*  200: 273 */       if (k > 255) {
/*  201: 274 */         str3 = "L";
/*  202:     */       }
/*  203:     */     }
/*  204: 279 */     while (i < str2.length())
/*  205:     */     {
/*  206: 281 */       if (str2.charAt(i) == '\\')
/*  207:     */       {
/*  208: 283 */         if (str2.length() == i + 1) {
/*  209: 284 */           this.antlrTool.error("Invalid escape in char literal: '" + paramString + "' looking at '" + str2.substring(i) + "'");
/*  210:     */         }
/*  211: 287 */         switch (str2.charAt(i + 1))
/*  212:     */         {
/*  213:     */         case 'a': 
/*  214: 289 */           j = 7;
/*  215: 290 */           i += 2;
/*  216: 291 */           break;
/*  217:     */         case 'b': 
/*  218: 293 */           j = 8;
/*  219: 294 */           i += 2;
/*  220: 295 */           break;
/*  221:     */         case 't': 
/*  222: 297 */           j = 9;
/*  223: 298 */           i += 2;
/*  224: 299 */           break;
/*  225:     */         case 'n': 
/*  226: 301 */           j = 10;
/*  227: 302 */           i += 2;
/*  228: 303 */           break;
/*  229:     */         case 'f': 
/*  230: 305 */           j = 12;
/*  231: 306 */           i += 2;
/*  232: 307 */           break;
/*  233:     */         case 'r': 
/*  234: 309 */           j = 13;
/*  235: 310 */           i += 2;
/*  236: 311 */           break;
/*  237:     */         case '"': 
/*  238:     */         case '\'': 
/*  239:     */         case '\\': 
/*  240: 315 */           j = str2.charAt(i + 1);
/*  241: 316 */           i += 2;
/*  242: 317 */           break;
/*  243:     */         case 'u': 
/*  244: 321 */           if (i + 5 < str2.length())
/*  245:     */           {
/*  246: 323 */             j = Character.digit(str2.charAt(i + 2), 16) * 16 * 16 * 16 + Character.digit(str2.charAt(i + 3), 16) * 16 * 16 + Character.digit(str2.charAt(i + 4), 16) * 16 + Character.digit(str2.charAt(i + 5), 16);
/*  247:     */             
/*  248:     */ 
/*  249:     */ 
/*  250: 327 */             i += 6;
/*  251:     */           }
/*  252:     */           else
/*  253:     */           {
/*  254: 330 */             this.antlrTool.error("Invalid escape in char literal: '" + paramString + "' looking at '" + str2.substring(i) + "'");
/*  255:     */           }
/*  256: 331 */           break;
/*  257:     */         case '0': 
/*  258:     */         case '1': 
/*  259:     */         case '2': 
/*  260:     */         case '3': 
/*  261: 337 */           if (charIsDigit(str2, i + 2))
/*  262:     */           {
/*  263: 339 */             if (charIsDigit(str2, i + 3))
/*  264:     */             {
/*  265: 341 */               j = (str2.charAt(i + 1) - '0') * 8 * 8 + (str2.charAt(i + 2) - '0') * 8 + (str2.charAt(i + 3) - '0');
/*  266:     */               
/*  267: 343 */               i += 4;
/*  268:     */             }
/*  269:     */             else
/*  270:     */             {
/*  271: 347 */               j = (str2.charAt(i + 1) - '0') * 8 + (str2.charAt(i + 2) - '0');
/*  272: 348 */               i += 3;
/*  273:     */             }
/*  274:     */           }
/*  275:     */           else
/*  276:     */           {
/*  277: 353 */             j = str2.charAt(i + 1) - '0';
/*  278: 354 */             i += 2;
/*  279:     */           }
/*  280: 356 */           break;
/*  281:     */         case '4': 
/*  282:     */         case '5': 
/*  283:     */         case '6': 
/*  284:     */         case '7': 
/*  285: 362 */           if (charIsDigit(str2, i + 2))
/*  286:     */           {
/*  287: 364 */             j = (str2.charAt(i + 1) - '0') * 8 + (str2.charAt(i + 2) - '0');
/*  288: 365 */             i += 3;
/*  289:     */           }
/*  290:     */           else
/*  291:     */           {
/*  292: 369 */             j = str2.charAt(i + 1) - '0';
/*  293: 370 */             i += 2;
/*  294:     */           }
/*  295:     */           break;
/*  296:     */         }
/*  297: 373 */         this.antlrTool.error("Unhandled escape in char literal: '" + paramString + "' looking at '" + str2.substring(i) + "'");
/*  298: 374 */         j = 0;
/*  299:     */       }
/*  300:     */       else
/*  301:     */       {
/*  302: 378 */         j = str2.charAt(i++);
/*  303:     */       }
/*  304: 380 */       if ((this.grammar instanceof LexerGrammar)) {
/*  305: 382 */         if (j > k)
/*  306:     */         {
/*  307:     */           String str4;
/*  308: 385 */           if ((32 <= j) && (j < 127)) {
/*  309: 386 */             str4 = this.charFormatter.escapeChar(j, true);
/*  310:     */           } else {
/*  311: 388 */             str4 = "0x" + Integer.toString(j, 16);
/*  312:     */           }
/*  313: 390 */           this.antlrTool.error("Character out of range in " + (paramBoolean ? "char literal" : "string constant") + ": '" + str2 + "'");
/*  314: 391 */           this.antlrTool.error("Vocabulary size: " + k + " Character " + str4);
/*  315:     */         }
/*  316:     */       }
/*  317: 395 */       if (paramBoolean)
/*  318:     */       {
/*  319: 398 */         if (i != str2.length()) {
/*  320: 399 */           this.antlrTool.error("Invalid char literal: '" + paramString + "'");
/*  321:     */         }
/*  322: 401 */         if (k <= 255)
/*  323:     */         {
/*  324: 403 */           if ((j <= 255) && ((j & 0x80) != 0)) {
/*  325: 407 */             str1 = "static_cast<unsigned char>('" + this.charFormatter.escapeChar(j, true) + "')";
/*  326:     */           } else {
/*  327: 409 */             str1 = "'" + this.charFormatter.escapeChar(j, true) + "'";
/*  328:     */           }
/*  329:     */         }
/*  330:     */         else {
/*  331: 416 */           str1 = "L'" + this.charFormatter.escapeChar(j, true) + "'";
/*  332:     */         }
/*  333:     */       }
/*  334:     */       else
/*  335:     */       {
/*  336: 420 */         str1 = str1 + this.charFormatter.escapeChar(j, true);
/*  337:     */       }
/*  338:     */     }
/*  339: 422 */     if (!paramBoolean) {
/*  340: 423 */       str1 = str3 + "\"" + str1 + "\"";
/*  341:     */     }
/*  342: 424 */     return str1;
/*  343:     */   }
/*  344:     */   
/*  345:     */   public void gen()
/*  346:     */   {
/*  347:     */     try
/*  348:     */     {
/*  349: 432 */       Enumeration localEnumeration = this.behavior.grammars.elements();
/*  350: 433 */       while (localEnumeration.hasMoreElements())
/*  351:     */       {
/*  352: 434 */         localObject = (Grammar)localEnumeration.nextElement();
/*  353: 435 */         if (((Grammar)localObject).debuggingOutput) {
/*  354: 436 */           this.antlrTool.error(((Grammar)localObject).getFilename() + ": C++ mode does not support -debug");
/*  355:     */         }
/*  356: 439 */         ((Grammar)localObject).setGrammarAnalyzer(this.analyzer);
/*  357: 440 */         ((Grammar)localObject).setCodeGenerator(this);
/*  358: 441 */         this.analyzer.setGrammar((Grammar)localObject);
/*  359:     */         
/*  360: 443 */         setupGrammarParameters((Grammar)localObject);
/*  361: 444 */         ((Grammar)localObject).generate();
/*  362: 445 */         exitIfError();
/*  363:     */       }
/*  364: 449 */       Object localObject = this.behavior.tokenManagers.elements();
/*  365: 450 */       while (((Enumeration)localObject).hasMoreElements())
/*  366:     */       {
/*  367: 451 */         TokenManager localTokenManager = (TokenManager)((Enumeration)localObject).nextElement();
/*  368: 452 */         if (!localTokenManager.isReadOnly())
/*  369:     */         {
/*  370: 456 */           genTokenTypes(localTokenManager);
/*  371:     */           
/*  372: 458 */           genTokenInterchange(localTokenManager);
/*  373:     */         }
/*  374: 460 */         exitIfError();
/*  375:     */       }
/*  376:     */     }
/*  377:     */     catch (IOException localIOException)
/*  378:     */     {
/*  379: 464 */       this.antlrTool.reportException(localIOException, null);
/*  380:     */     }
/*  381:     */   }
/*  382:     */   
/*  383:     */   public void gen(ActionElement paramActionElement)
/*  384:     */   {
/*  385: 471 */     if ((this.DEBUG_CODE_GENERATOR) || (this.DEBUG_CPP_CODE_GENERATOR)) {
/*  386: 471 */       System.out.println("genAction(" + paramActionElement + ")");
/*  387:     */     }
/*  388: 472 */     if (paramActionElement.isSemPred)
/*  389:     */     {
/*  390: 473 */       genSemPred(paramActionElement.actionText, paramActionElement.line);
/*  391:     */     }
/*  392:     */     else
/*  393:     */     {
/*  394: 476 */       if (this.grammar.hasSyntacticPredicate)
/*  395:     */       {
/*  396: 477 */         println("if ( inputState->guessing==0 ) {");
/*  397: 478 */         this.tabs += 1;
/*  398:     */       }
/*  399: 481 */       ActionTransInfo localActionTransInfo = new ActionTransInfo();
/*  400: 482 */       String str = processActionForSpecialSymbols(paramActionElement.actionText, paramActionElement.getLine(), this.currentRule, localActionTransInfo);
/*  401: 486 */       if (localActionTransInfo.refRuleRoot != null) {
/*  402: 491 */         println(localActionTransInfo.refRuleRoot + " = " + this.labeledElementASTType + "(currentAST.root);");
/*  403:     */       }
/*  404: 495 */       genLineNo(paramActionElement);
/*  405: 496 */       printAction(str);
/*  406: 497 */       genLineNo2();
/*  407: 499 */       if (localActionTransInfo.assignToRoot)
/*  408:     */       {
/*  409: 501 */         println("currentAST.root = " + localActionTransInfo.refRuleRoot + ";");
/*  410:     */         
/*  411:     */ 
/*  412: 504 */         println("if ( " + localActionTransInfo.refRuleRoot + "!=" + this.labeledElementASTInit + " &&");
/*  413: 505 */         this.tabs += 1;
/*  414: 506 */         println(localActionTransInfo.refRuleRoot + "->getFirstChild() != " + this.labeledElementASTInit + " )");
/*  415: 507 */         println("  currentAST.child = " + localActionTransInfo.refRuleRoot + "->getFirstChild();");
/*  416: 508 */         this.tabs -= 1;
/*  417: 509 */         println("else");
/*  418: 510 */         this.tabs += 1;
/*  419: 511 */         println("currentAST.child = " + localActionTransInfo.refRuleRoot + ";");
/*  420: 512 */         this.tabs -= 1;
/*  421: 513 */         println("currentAST.advanceChildToEnd();");
/*  422:     */       }
/*  423: 516 */       if (this.grammar.hasSyntacticPredicate)
/*  424:     */       {
/*  425: 517 */         this.tabs -= 1;
/*  426: 518 */         println("}");
/*  427:     */       }
/*  428:     */     }
/*  429:     */   }
/*  430:     */   
/*  431:     */   public void gen(AlternativeBlock paramAlternativeBlock)
/*  432:     */   {
/*  433: 527 */     if ((this.DEBUG_CODE_GENERATOR) || (this.DEBUG_CPP_CODE_GENERATOR)) {
/*  434: 527 */       System.out.println("gen(" + paramAlternativeBlock + ")");
/*  435:     */     }
/*  436: 528 */     println("{");
/*  437: 529 */     genBlockPreamble(paramAlternativeBlock);
/*  438: 530 */     genBlockInitAction(paramAlternativeBlock);
/*  439:     */     
/*  440:     */ 
/*  441: 533 */     String str = this.currentASTResult;
/*  442: 534 */     if (paramAlternativeBlock.getLabel() != null) {
/*  443: 535 */       this.currentASTResult = paramAlternativeBlock.getLabel();
/*  444:     */     }
/*  445: 538 */     boolean bool = this.grammar.theLLkAnalyzer.deterministic(paramAlternativeBlock);
/*  446:     */     
/*  447: 540 */     CppBlockFinishingInfo localCppBlockFinishingInfo = genCommonBlock(paramAlternativeBlock, true);
/*  448: 541 */     genBlockFinish(localCppBlockFinishingInfo, this.throwNoViable);
/*  449:     */     
/*  450: 543 */     println("}");
/*  451:     */     
/*  452:     */ 
/*  453: 546 */     this.currentASTResult = str;
/*  454:     */   }
/*  455:     */   
/*  456:     */   public void gen(BlockEndElement paramBlockEndElement)
/*  457:     */   {
/*  458: 554 */     if ((this.DEBUG_CODE_GENERATOR) || (this.DEBUG_CPP_CODE_GENERATOR)) {
/*  459: 554 */       System.out.println("genRuleEnd(" + paramBlockEndElement + ")");
/*  460:     */     }
/*  461:     */   }
/*  462:     */   
/*  463:     */   public void gen(CharLiteralElement paramCharLiteralElement)
/*  464:     */   {
/*  465: 561 */     if ((this.DEBUG_CODE_GENERATOR) || (this.DEBUG_CPP_CODE_GENERATOR)) {
/*  466: 562 */       System.out.println("genChar(" + paramCharLiteralElement + ")");
/*  467:     */     }
/*  468: 564 */     if (!(this.grammar instanceof LexerGrammar)) {
/*  469: 565 */       this.antlrTool.error("cannot ref character literals in grammar: " + paramCharLiteralElement);
/*  470:     */     }
/*  471: 567 */     if (paramCharLiteralElement.getLabel() != null) {
/*  472: 568 */       println(paramCharLiteralElement.getLabel() + " = " + this.lt1Value + ";");
/*  473:     */     }
/*  474: 571 */     boolean bool = this.saveText;
/*  475: 572 */     this.saveText = ((this.saveText) && (paramCharLiteralElement.getAutoGenType() == 1));
/*  476: 575 */     if ((!this.saveText) || (paramCharLiteralElement.getAutoGenType() == 3)) {
/*  477: 576 */       println("_saveIndex = text.length();");
/*  478:     */     }
/*  479: 578 */     print(paramCharLiteralElement.not ? "matchNot(" : "match(");
/*  480: 579 */     _print(convertJavaToCppString(paramCharLiteralElement.atomText, true));
/*  481: 580 */     _println(" /* charlit */ );");
/*  482: 582 */     if ((!this.saveText) || (paramCharLiteralElement.getAutoGenType() == 3)) {
/*  483: 583 */       println("text.erase(_saveIndex);");
/*  484:     */     }
/*  485: 585 */     this.saveText = bool;
/*  486:     */   }
/*  487:     */   
/*  488:     */   public void gen(CharRangeElement paramCharRangeElement)
/*  489:     */   {
/*  490: 592 */     if ((this.DEBUG_CODE_GENERATOR) || (this.DEBUG_CPP_CODE_GENERATOR)) {
/*  491: 593 */       System.out.println("genCharRangeElement(" + paramCharRangeElement.beginText + ".." + paramCharRangeElement.endText + ")");
/*  492:     */     }
/*  493: 595 */     if (!(this.grammar instanceof LexerGrammar)) {
/*  494: 596 */       this.antlrTool.error("cannot ref character range in grammar: " + paramCharRangeElement);
/*  495:     */     }
/*  496: 598 */     if ((paramCharRangeElement.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/*  497: 599 */       println(paramCharRangeElement.getLabel() + " = " + this.lt1Value + ";");
/*  498:     */     }
/*  499: 602 */     int i = ((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramCharRangeElement.getAutoGenType() == 3)) ? 1 : 0;
/*  500: 606 */     if (i != 0) {
/*  501: 607 */       println("_saveIndex=text.length();");
/*  502:     */     }
/*  503: 609 */     println("matchRange(" + convertJavaToCppString(paramCharRangeElement.beginText, true) + "," + convertJavaToCppString(paramCharRangeElement.endText, true) + ");");
/*  504: 612 */     if (i != 0) {
/*  505: 613 */       println("text.erase(_saveIndex);");
/*  506:     */     }
/*  507:     */   }
/*  508:     */   
/*  509:     */   public void gen(LexerGrammar paramLexerGrammar)
/*  510:     */     throws IOException
/*  511:     */   {
/*  512: 618 */     if (paramLexerGrammar.debuggingOutput) {
/*  513: 619 */       this.semPreds = new Vector();
/*  514:     */     }
/*  515: 621 */     if (paramLexerGrammar.charVocabulary.size() > 256) {
/*  516: 622 */       this.antlrTool.warning(paramLexerGrammar.getFilename() + ": Vocabularies of this size still experimental in C++ mode (vocabulary size now: " + paramLexerGrammar.charVocabulary.size() + ")");
/*  517:     */     }
/*  518: 624 */     setGrammar(paramLexerGrammar);
/*  519: 625 */     if (!(this.grammar instanceof LexerGrammar)) {
/*  520: 626 */       this.antlrTool.panic("Internal error generating lexer");
/*  521:     */     }
/*  522: 629 */     genBody(paramLexerGrammar);
/*  523: 630 */     genInclude(paramLexerGrammar);
/*  524:     */   }
/*  525:     */   
/*  526:     */   public void gen(OneOrMoreBlock paramOneOrMoreBlock)
/*  527:     */   {
/*  528: 636 */     if ((this.DEBUG_CODE_GENERATOR) || (this.DEBUG_CPP_CODE_GENERATOR)) {
/*  529: 636 */       System.out.println("gen+(" + paramOneOrMoreBlock + ")");
/*  530:     */     }
/*  531: 639 */     println("{ // ( ... )+");
/*  532: 640 */     genBlockPreamble(paramOneOrMoreBlock);
/*  533:     */     String str2;
/*  534: 641 */     if (paramOneOrMoreBlock.getLabel() != null) {
/*  535: 642 */       str2 = "_cnt_" + paramOneOrMoreBlock.getLabel();
/*  536:     */     } else {
/*  537: 645 */       str2 = "_cnt" + paramOneOrMoreBlock.ID;
/*  538:     */     }
/*  539: 647 */     println("int " + str2 + "=0;");
/*  540:     */     String str1;
/*  541: 648 */     if (paramOneOrMoreBlock.getLabel() != null) {
/*  542: 649 */       str1 = paramOneOrMoreBlock.getLabel();
/*  543:     */     } else {
/*  544: 652 */       str1 = "_loop" + paramOneOrMoreBlock.ID;
/*  545:     */     }
/*  546: 655 */     println("for (;;) {");
/*  547: 656 */     this.tabs += 1;
/*  548:     */     
/*  549:     */ 
/*  550: 659 */     genBlockInitAction(paramOneOrMoreBlock);
/*  551:     */     
/*  552:     */ 
/*  553: 662 */     String str3 = this.currentASTResult;
/*  554: 663 */     if (paramOneOrMoreBlock.getLabel() != null) {
/*  555: 664 */       this.currentASTResult = paramOneOrMoreBlock.getLabel();
/*  556:     */     }
/*  557: 667 */     boolean bool = this.grammar.theLLkAnalyzer.deterministic(paramOneOrMoreBlock);
/*  558:     */     
/*  559:     */ 
/*  560:     */ 
/*  561:     */ 
/*  562:     */ 
/*  563:     */ 
/*  564:     */ 
/*  565:     */ 
/*  566:     */ 
/*  567:     */ 
/*  568:     */ 
/*  569: 679 */     int i = 0;
/*  570: 680 */     int j = this.grammar.maxk;
/*  571: 682 */     if ((!paramOneOrMoreBlock.greedy) && (paramOneOrMoreBlock.exitLookaheadDepth <= this.grammar.maxk) && (paramOneOrMoreBlock.exitCache[paramOneOrMoreBlock.exitLookaheadDepth].containsEpsilon()))
/*  572:     */     {
/*  573: 686 */       i = 1;
/*  574: 687 */       j = paramOneOrMoreBlock.exitLookaheadDepth;
/*  575:     */     }
/*  576: 689 */     else if ((!paramOneOrMoreBlock.greedy) && (paramOneOrMoreBlock.exitLookaheadDepth == 2147483647))
/*  577:     */     {
/*  578: 692 */       i = 1;
/*  579:     */     }
/*  580: 697 */     if (i != 0)
/*  581:     */     {
/*  582: 698 */       if ((this.DEBUG_CODE_GENERATOR) || (this.DEBUG_CPP_CODE_GENERATOR)) {
/*  583: 699 */         System.out.println("nongreedy (...)+ loop; exit depth is " + paramOneOrMoreBlock.exitLookaheadDepth);
/*  584:     */       }
/*  585: 702 */       localObject = getLookaheadTestExpression(paramOneOrMoreBlock.exitCache, j);
/*  586:     */       
/*  587:     */ 
/*  588: 705 */       println("// nongreedy exit test");
/*  589: 706 */       println("if ( " + str2 + ">=1 && " + (String)localObject + ") goto " + str1 + ";");
/*  590:     */     }
/*  591: 709 */     Object localObject = genCommonBlock(paramOneOrMoreBlock, false);
/*  592: 710 */     genBlockFinish((CppBlockFinishingInfo)localObject, "if ( " + str2 + ">=1 ) { goto " + str1 + "; } else {" + this.throwNoViable + "}");
/*  593:     */     
/*  594:     */ 
/*  595:     */ 
/*  596:     */ 
/*  597: 715 */     println(str2 + "++;");
/*  598: 716 */     this.tabs -= 1;
/*  599: 717 */     println("}");
/*  600: 718 */     println(str1 + ":;");
/*  601: 719 */     println("}  // ( ... )+");
/*  602:     */     
/*  603:     */ 
/*  604: 722 */     this.currentASTResult = str3;
/*  605:     */   }
/*  606:     */   
/*  607:     */   public void gen(ParserGrammar paramParserGrammar)
/*  608:     */     throws IOException
/*  609:     */   {
/*  610: 729 */     if (paramParserGrammar.debuggingOutput) {
/*  611: 730 */       this.semPreds = new Vector();
/*  612:     */     }
/*  613: 732 */     setGrammar(paramParserGrammar);
/*  614: 733 */     if (!(this.grammar instanceof ParserGrammar)) {
/*  615: 734 */       this.antlrTool.panic("Internal error generating parser");
/*  616:     */     }
/*  617: 737 */     genBody(paramParserGrammar);
/*  618: 738 */     genInclude(paramParserGrammar);
/*  619:     */   }
/*  620:     */   
/*  621:     */   public void gen(RuleRefElement paramRuleRefElement)
/*  622:     */   {
/*  623: 745 */     if ((this.DEBUG_CODE_GENERATOR) || (this.DEBUG_CPP_CODE_GENERATOR)) {
/*  624: 745 */       System.out.println("genRR(" + paramRuleRefElement + ")");
/*  625:     */     }
/*  626: 746 */     RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(paramRuleRefElement.targetRule);
/*  627: 747 */     if ((localRuleSymbol == null) || (!localRuleSymbol.isDefined()))
/*  628:     */     {
/*  629: 750 */       this.antlrTool.error("Rule '" + paramRuleRefElement.targetRule + "' is not defined", this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/*  630: 751 */       return;
/*  631:     */     }
/*  632: 753 */     if (!(localRuleSymbol instanceof RuleSymbol))
/*  633:     */     {
/*  634: 756 */       this.antlrTool.error("'" + paramRuleRefElement.targetRule + "' does not name a grammar rule", this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/*  635: 757 */       return;
/*  636:     */     }
/*  637: 760 */     genErrorTryForElement(paramRuleRefElement);
/*  638: 764 */     if (((this.grammar instanceof TreeWalkerGrammar)) && (paramRuleRefElement.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/*  639: 768 */       println(paramRuleRefElement.getLabel() + " = (_t == ASTNULL) ? " + this.labeledElementASTInit + " : " + this.lt1Value + ";");
/*  640:     */     }
/*  641: 773 */     if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramRuleRefElement.getAutoGenType() == 3))) {
/*  642: 775 */       println("_saveIndex = text.length();");
/*  643:     */     }
/*  644: 779 */     printTabs();
/*  645: 780 */     if (paramRuleRefElement.idAssign != null)
/*  646:     */     {
/*  647: 783 */       if (localRuleSymbol.block.returnAction == null) {
/*  648: 785 */         this.antlrTool.warning("Rule '" + paramRuleRefElement.targetRule + "' has no return type", this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/*  649:     */       }
/*  650: 787 */       _print(paramRuleRefElement.idAssign + "=");
/*  651:     */     }
/*  652: 790 */     else if ((!(this.grammar instanceof LexerGrammar)) && (this.syntacticPredLevel == 0) && (localRuleSymbol.block.returnAction != null))
/*  653:     */     {
/*  654: 792 */       this.antlrTool.warning("Rule '" + paramRuleRefElement.targetRule + "' returns a value", this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/*  655:     */     }
/*  656: 797 */     GenRuleInvocation(paramRuleRefElement);
/*  657: 800 */     if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramRuleRefElement.getAutoGenType() == 3))) {
/*  658: 801 */       println("text.erase(_saveIndex);");
/*  659:     */     }
/*  660: 805 */     if (this.syntacticPredLevel == 0)
/*  661:     */     {
/*  662: 807 */       int i = (this.grammar.hasSyntacticPredicate) && (((this.grammar.buildAST) && (paramRuleRefElement.getLabel() != null)) || ((this.genAST) && (paramRuleRefElement.getAutoGenType() == 1))) ? 1 : 0;
/*  663: 815 */       if (i != 0)
/*  664:     */       {
/*  665: 816 */         println("if (inputState->guessing==0) {");
/*  666: 817 */         this.tabs += 1;
/*  667:     */       }
/*  668: 820 */       if ((this.grammar.buildAST) && (paramRuleRefElement.getLabel() != null)) {
/*  669: 824 */         println(paramRuleRefElement.getLabel() + "_AST = returnAST;");
/*  670:     */       }
/*  671: 827 */       if (this.genAST) {
/*  672: 829 */         switch (paramRuleRefElement.getAutoGenType())
/*  673:     */         {
/*  674:     */         case 1: 
/*  675: 832 */           if (this.usingCustomAST) {
/*  676: 833 */             println("astFactory->addASTChild(currentAST, " + namespaceAntlr + "RefAST(returnAST));");
/*  677:     */           } else {
/*  678: 835 */             println("astFactory->addASTChild( currentAST, returnAST );");
/*  679:     */           }
/*  680: 836 */           break;
/*  681:     */         case 2: 
/*  682: 840 */           this.antlrTool.error("Internal: encountered ^ after rule reference");
/*  683: 841 */           break;
/*  684:     */         }
/*  685:     */       }
/*  686: 848 */       if (((this.grammar instanceof LexerGrammar)) && (paramRuleRefElement.getLabel() != null)) {
/*  687: 850 */         println(paramRuleRefElement.getLabel() + "=_returnToken;");
/*  688:     */       }
/*  689: 853 */       if (i != 0)
/*  690:     */       {
/*  691: 855 */         this.tabs -= 1;
/*  692: 856 */         println("}");
/*  693:     */       }
/*  694:     */     }
/*  695: 859 */     genErrorCatchForElement(paramRuleRefElement);
/*  696:     */   }
/*  697:     */   
/*  698:     */   public void gen(StringLiteralElement paramStringLiteralElement)
/*  699:     */   {
/*  700: 865 */     if ((this.DEBUG_CODE_GENERATOR) || (this.DEBUG_CPP_CODE_GENERATOR)) {
/*  701: 865 */       System.out.println("genString(" + paramStringLiteralElement + ")");
/*  702:     */     }
/*  703: 868 */     if ((paramStringLiteralElement.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/*  704: 869 */       println(paramStringLiteralElement.getLabel() + " = " + this.lt1Value + ";");
/*  705:     */     }
/*  706: 873 */     genElementAST(paramStringLiteralElement);
/*  707:     */     
/*  708:     */ 
/*  709: 876 */     boolean bool = this.saveText;
/*  710: 877 */     this.saveText = ((this.saveText) && (paramStringLiteralElement.getAutoGenType() == 1));
/*  711:     */     
/*  712:     */ 
/*  713: 880 */     genMatch(paramStringLiteralElement);
/*  714:     */     
/*  715: 882 */     this.saveText = bool;
/*  716: 885 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/*  717: 886 */       println("_t = _t->getNextSibling();");
/*  718:     */     }
/*  719:     */   }
/*  720:     */   
/*  721:     */   public void gen(TokenRangeElement paramTokenRangeElement)
/*  722:     */   {
/*  723: 893 */     genErrorTryForElement(paramTokenRangeElement);
/*  724: 894 */     if ((paramTokenRangeElement.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/*  725: 895 */       println(paramTokenRangeElement.getLabel() + " = " + this.lt1Value + ";");
/*  726:     */     }
/*  727: 899 */     genElementAST(paramTokenRangeElement);
/*  728:     */     
/*  729:     */ 
/*  730: 902 */     println("matchRange(" + paramTokenRangeElement.beginText + "," + paramTokenRangeElement.endText + ");");
/*  731: 903 */     genErrorCatchForElement(paramTokenRangeElement);
/*  732:     */   }
/*  733:     */   
/*  734:     */   public void gen(TokenRefElement paramTokenRefElement)
/*  735:     */   {
/*  736: 909 */     if ((this.DEBUG_CODE_GENERATOR) || (this.DEBUG_CPP_CODE_GENERATOR)) {
/*  737: 909 */       System.out.println("genTokenRef(" + paramTokenRefElement + ")");
/*  738:     */     }
/*  739: 910 */     if ((this.grammar instanceof LexerGrammar)) {
/*  740: 911 */       this.antlrTool.panic("Token reference found in lexer");
/*  741:     */     }
/*  742: 913 */     genErrorTryForElement(paramTokenRefElement);
/*  743: 915 */     if ((paramTokenRefElement.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/*  744: 916 */       println(paramTokenRefElement.getLabel() + " = " + this.lt1Value + ";");
/*  745:     */     }
/*  746: 920 */     genElementAST(paramTokenRefElement);
/*  747:     */     
/*  748: 922 */     genMatch(paramTokenRefElement);
/*  749: 923 */     genErrorCatchForElement(paramTokenRefElement);
/*  750: 926 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/*  751: 927 */       println("_t = _t->getNextSibling();");
/*  752:     */     }
/*  753:     */   }
/*  754:     */   
/*  755:     */   public void gen(TreeElement paramTreeElement)
/*  756:     */   {
/*  757: 932 */     println(this.labeledElementType + " __t" + paramTreeElement.ID + " = _t;");
/*  758: 935 */     if (paramTreeElement.root.getLabel() != null) {
/*  759: 936 */       println(paramTreeElement.root.getLabel() + " = (_t == " + this.labeledElementType + "(ASTNULL)) ? " + this.labeledElementASTInit + " : _t;");
/*  760:     */     }
/*  761: 940 */     if (paramTreeElement.root.getAutoGenType() == 3)
/*  762:     */     {
/*  763: 941 */       this.antlrTool.error("Suffixing a root node with '!' is not implemented", this.grammar.getFilename(), paramTreeElement.getLine(), paramTreeElement.getColumn());
/*  764:     */       
/*  765: 943 */       paramTreeElement.root.setAutoGenType(1);
/*  766:     */     }
/*  767: 945 */     if (paramTreeElement.root.getAutoGenType() == 2)
/*  768:     */     {
/*  769: 946 */       this.antlrTool.warning("Suffixing a root node with '^' is redundant; already a root", this.grammar.getFilename(), paramTreeElement.getLine(), paramTreeElement.getColumn());
/*  770:     */       
/*  771: 948 */       paramTreeElement.root.setAutoGenType(1);
/*  772:     */     }
/*  773: 952 */     genElementAST(paramTreeElement.root);
/*  774: 953 */     if (this.grammar.buildAST)
/*  775:     */     {
/*  776: 955 */       println(namespaceAntlr + "ASTPair __currentAST" + paramTreeElement.ID + " = currentAST;");
/*  777:     */       
/*  778: 957 */       println("currentAST.root = currentAST.child;");
/*  779: 958 */       println("currentAST.child = " + this.labeledElementASTInit + ";");
/*  780:     */     }
/*  781: 962 */     if ((paramTreeElement.root instanceof WildcardElement)) {
/*  782: 963 */       println("if ( _t == ASTNULL ) throw " + namespaceAntlr + "MismatchedTokenException();");
/*  783:     */     } else {
/*  784: 966 */       genMatch(paramTreeElement.root);
/*  785:     */     }
/*  786: 969 */     println("_t = _t->getFirstChild();");
/*  787: 972 */     for (int i = 0; i < paramTreeElement.getAlternatives().size(); i++)
/*  788:     */     {
/*  789: 973 */       Alternative localAlternative = paramTreeElement.getAlternativeAt(i);
/*  790: 974 */       AlternativeElement localAlternativeElement = localAlternative.head;
/*  791: 975 */       while (localAlternativeElement != null)
/*  792:     */       {
/*  793: 976 */         localAlternativeElement.generate();
/*  794: 977 */         localAlternativeElement = localAlternativeElement.next;
/*  795:     */       }
/*  796:     */     }
/*  797: 981 */     if (this.grammar.buildAST) {
/*  798: 984 */       println("currentAST = __currentAST" + paramTreeElement.ID + ";");
/*  799:     */     }
/*  800: 987 */     println("_t = __t" + paramTreeElement.ID + ";");
/*  801:     */     
/*  802: 989 */     println("_t = _t->getNextSibling();");
/*  803:     */   }
/*  804:     */   
/*  805:     */   public void gen(TreeWalkerGrammar paramTreeWalkerGrammar)
/*  806:     */     throws IOException
/*  807:     */   {
/*  808: 993 */     setGrammar(paramTreeWalkerGrammar);
/*  809: 994 */     if (!(this.grammar instanceof TreeWalkerGrammar)) {
/*  810: 995 */       this.antlrTool.panic("Internal error generating tree-walker");
/*  811:     */     }
/*  812: 998 */     genBody(paramTreeWalkerGrammar);
/*  813: 999 */     genInclude(paramTreeWalkerGrammar);
/*  814:     */   }
/*  815:     */   
/*  816:     */   public void gen(WildcardElement paramWildcardElement)
/*  817:     */   {
/*  818:1006 */     if ((paramWildcardElement.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/*  819:1007 */       println(paramWildcardElement.getLabel() + " = " + this.lt1Value + ";");
/*  820:     */     }
/*  821:1011 */     genElementAST(paramWildcardElement);
/*  822:1013 */     if ((this.grammar instanceof TreeWalkerGrammar))
/*  823:     */     {
/*  824:1014 */       println("if ( _t == " + this.labeledElementASTInit + " ) throw " + namespaceAntlr + "MismatchedTokenException();");
/*  825:     */     }
/*  826:1016 */     else if ((this.grammar instanceof LexerGrammar))
/*  827:     */     {
/*  828:1017 */       if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramWildcardElement.getAutoGenType() == 3))) {
/*  829:1019 */         println("_saveIndex = text.length();");
/*  830:     */       }
/*  831:1021 */       println("matchNot(EOF/*_CHAR*/);");
/*  832:1022 */       if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramWildcardElement.getAutoGenType() == 3))) {
/*  833:1024 */         println("text.erase(_saveIndex);");
/*  834:     */       }
/*  835:     */     }
/*  836:     */     else
/*  837:     */     {
/*  838:1028 */       println("matchNot(" + getValueString(1) + ");");
/*  839:     */     }
/*  840:1032 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/*  841:1033 */       println("_t = _t->getNextSibling();");
/*  842:     */     }
/*  843:     */   }
/*  844:     */   
/*  845:     */   public void gen(ZeroOrMoreBlock paramZeroOrMoreBlock)
/*  846:     */   {
/*  847:1040 */     if ((this.DEBUG_CODE_GENERATOR) || (this.DEBUG_CPP_CODE_GENERATOR)) {
/*  848:1040 */       System.out.println("gen*(" + paramZeroOrMoreBlock + ")");
/*  849:     */     }
/*  850:1041 */     println("{ // ( ... )*");
/*  851:1042 */     genBlockPreamble(paramZeroOrMoreBlock);
/*  852:     */     String str1;
/*  853:1044 */     if (paramZeroOrMoreBlock.getLabel() != null) {
/*  854:1045 */       str1 = paramZeroOrMoreBlock.getLabel();
/*  855:     */     } else {
/*  856:1048 */       str1 = "_loop" + paramZeroOrMoreBlock.ID;
/*  857:     */     }
/*  858:1050 */     println("for (;;) {");
/*  859:1051 */     this.tabs += 1;
/*  860:     */     
/*  861:     */ 
/*  862:1054 */     genBlockInitAction(paramZeroOrMoreBlock);
/*  863:     */     
/*  864:     */ 
/*  865:1057 */     String str2 = this.currentASTResult;
/*  866:1058 */     if (paramZeroOrMoreBlock.getLabel() != null) {
/*  867:1059 */       this.currentASTResult = paramZeroOrMoreBlock.getLabel();
/*  868:     */     }
/*  869:1062 */     boolean bool = this.grammar.theLLkAnalyzer.deterministic(paramZeroOrMoreBlock);
/*  870:     */     
/*  871:     */ 
/*  872:     */ 
/*  873:     */ 
/*  874:     */ 
/*  875:     */ 
/*  876:     */ 
/*  877:     */ 
/*  878:     */ 
/*  879:     */ 
/*  880:     */ 
/*  881:1074 */     int i = 0;
/*  882:1075 */     int j = this.grammar.maxk;
/*  883:1077 */     if ((!paramZeroOrMoreBlock.greedy) && (paramZeroOrMoreBlock.exitLookaheadDepth <= this.grammar.maxk) && (paramZeroOrMoreBlock.exitCache[paramZeroOrMoreBlock.exitLookaheadDepth].containsEpsilon()))
/*  884:     */     {
/*  885:1081 */       i = 1;
/*  886:1082 */       j = paramZeroOrMoreBlock.exitLookaheadDepth;
/*  887:     */     }
/*  888:1084 */     else if ((!paramZeroOrMoreBlock.greedy) && (paramZeroOrMoreBlock.exitLookaheadDepth == 2147483647))
/*  889:     */     {
/*  890:1087 */       i = 1;
/*  891:     */     }
/*  892:1089 */     if (i != 0)
/*  893:     */     {
/*  894:1090 */       if ((this.DEBUG_CODE_GENERATOR) || (this.DEBUG_CPP_CODE_GENERATOR)) {
/*  895:1091 */         System.out.println("nongreedy (...)* loop; exit depth is " + paramZeroOrMoreBlock.exitLookaheadDepth);
/*  896:     */       }
/*  897:1094 */       localObject = getLookaheadTestExpression(paramZeroOrMoreBlock.exitCache, j);
/*  898:     */       
/*  899:     */ 
/*  900:1097 */       println("// nongreedy exit test");
/*  901:1098 */       println("if (" + (String)localObject + ") goto " + str1 + ";");
/*  902:     */     }
/*  903:1101 */     Object localObject = genCommonBlock(paramZeroOrMoreBlock, false);
/*  904:1102 */     genBlockFinish((CppBlockFinishingInfo)localObject, "goto " + str1 + ";");
/*  905:     */     
/*  906:1104 */     this.tabs -= 1;
/*  907:1105 */     println("}");
/*  908:1106 */     println(str1 + ":;");
/*  909:1107 */     println("} // ( ... )*");
/*  910:     */     
/*  911:     */ 
/*  912:1110 */     this.currentASTResult = str2;
/*  913:     */   }
/*  914:     */   
/*  915:     */   protected void genAlt(Alternative paramAlternative, AlternativeBlock paramAlternativeBlock)
/*  916:     */   {
/*  917:1119 */     boolean bool1 = this.genAST;
/*  918:1120 */     this.genAST = ((this.genAST) && (paramAlternative.getAutoGen()));
/*  919:     */     
/*  920:1122 */     boolean bool2 = this.saveText;
/*  921:1123 */     this.saveText = ((this.saveText) && (paramAlternative.getAutoGen()));
/*  922:     */     
/*  923:     */ 
/*  924:1126 */     Hashtable localHashtable = this.treeVariableMap;
/*  925:1127 */     this.treeVariableMap = new Hashtable();
/*  926:1130 */     if (paramAlternative.exceptionSpec != null)
/*  927:     */     {
/*  928:1131 */       println("try {      // for error handling");
/*  929:1132 */       this.tabs += 1;
/*  930:     */     }
/*  931:1135 */     AlternativeElement localAlternativeElement = paramAlternative.head;
/*  932:1136 */     while (!(localAlternativeElement instanceof BlockEndElement))
/*  933:     */     {
/*  934:1137 */       localAlternativeElement.generate();
/*  935:1138 */       localAlternativeElement = localAlternativeElement.next;
/*  936:     */     }
/*  937:1141 */     if (this.genAST) {
/*  938:1143 */       if ((paramAlternativeBlock instanceof RuleBlock))
/*  939:     */       {
/*  940:1146 */         RuleBlock localRuleBlock = (RuleBlock)paramAlternativeBlock;
/*  941:1147 */         if (this.usingCustomAST) {
/*  942:1148 */           println(localRuleBlock.getRuleName() + "_AST = " + this.labeledElementASTType + "(currentAST.root);");
/*  943:     */         } else {
/*  944:1150 */           println(localRuleBlock.getRuleName() + "_AST = currentAST.root;");
/*  945:     */         }
/*  946:     */       }
/*  947:1152 */       else if (paramAlternativeBlock.getLabel() != null)
/*  948:     */       {
/*  949:1155 */         this.antlrTool.warning("Labeled subrules are not implemented", this.grammar.getFilename(), paramAlternativeBlock.getLine(), paramAlternativeBlock.getColumn());
/*  950:     */       }
/*  951:     */     }
/*  952:1159 */     if (paramAlternative.exceptionSpec != null)
/*  953:     */     {
/*  954:1162 */       this.tabs -= 1;
/*  955:1163 */       println("}");
/*  956:1164 */       genErrorHandler(paramAlternative.exceptionSpec);
/*  957:     */     }
/*  958:1167 */     this.genAST = bool1;
/*  959:1168 */     this.saveText = bool2;
/*  960:     */     
/*  961:1170 */     this.treeVariableMap = localHashtable;
/*  962:     */   }
/*  963:     */   
/*  964:     */   protected void genBitsets(Vector paramVector, int paramInt, String paramString)
/*  965:     */   {
/*  966:1192 */     TokenManager localTokenManager = this.grammar.tokenManager;
/*  967:     */     
/*  968:1194 */     println("");
/*  969:1196 */     for (int i = 0; i < paramVector.size(); i++)
/*  970:     */     {
/*  971:1198 */       BitSet localBitSet = (BitSet)paramVector.elementAt(i);
/*  972:     */       
/*  973:1200 */       localBitSet.growToInclude(paramInt);
/*  974:     */       
/*  975:     */ 
/*  976:1203 */       println("const unsigned long " + paramString + getBitsetName(i) + "_data_" + "[] = { " + localBitSet.toStringOfHalfWords() + " };");
/*  977:     */       
/*  978:     */ 
/*  979:     */ 
/*  980:     */ 
/*  981:     */ 
/*  982:     */ 
/*  983:1210 */       String str = "// ";
/*  984:1212 */       for (int j = 0; j < localTokenManager.getVocabulary().size(); j++) {
/*  985:1214 */         if (localBitSet.member(j))
/*  986:     */         {
/*  987:1216 */           if ((this.grammar instanceof LexerGrammar))
/*  988:     */           {
/*  989:1219 */             if ((32 <= j) && (j < 127) && (j != 92)) {
/*  990:1220 */               str = str + this.charFormatter.escapeChar(j, true) + " ";
/*  991:     */             } else {
/*  992:1222 */               str = str + "0x" + Integer.toString(j, 16) + " ";
/*  993:     */             }
/*  994:     */           }
/*  995:     */           else {
/*  996:1225 */             str = str + localTokenManager.getTokenStringAt(j) + " ";
/*  997:     */           }
/*  998:1227 */           if (str.length() > 70)
/*  999:     */           {
/* 1000:1229 */             println(str);
/* 1001:1230 */             str = "// ";
/* 1002:     */           }
/* 1003:     */         }
/* 1004:     */       }
/* 1005:1234 */       if (str != "// ") {
/* 1006:1235 */         println(str);
/* 1007:     */       }
/* 1008:1238 */       println("const " + namespaceAntlr + "BitSet " + paramString + getBitsetName(i) + "(" + getBitsetName(i) + "_data_," + localBitSet.size() / 32 + ");");
/* 1009:     */     }
/* 1010:     */   }
/* 1011:     */   
/* 1012:     */   protected void genBitsetsHeader(Vector paramVector, int paramInt)
/* 1013:     */   {
/* 1014:1249 */     println("");
/* 1015:1250 */     for (int i = 0; i < paramVector.size(); i++)
/* 1016:     */     {
/* 1017:1252 */       BitSet localBitSet = (BitSet)paramVector.elementAt(i);
/* 1018:     */       
/* 1019:1254 */       localBitSet.growToInclude(paramInt);
/* 1020:     */       
/* 1021:1256 */       println("static const unsigned long " + getBitsetName(i) + "_data_" + "[];");
/* 1022:     */       
/* 1023:1258 */       println("static const " + namespaceAntlr + "BitSet " + getBitsetName(i) + ";");
/* 1024:     */     }
/* 1025:     */   }
/* 1026:     */   
/* 1027:     */   private void genBlockFinish(CppBlockFinishingInfo paramCppBlockFinishingInfo, String paramString)
/* 1028:     */   {
/* 1029:1269 */     if ((paramCppBlockFinishingInfo.needAnErrorClause) && ((paramCppBlockFinishingInfo.generatedAnIf) || (paramCppBlockFinishingInfo.generatedSwitch)))
/* 1030:     */     {
/* 1031:1271 */       if (paramCppBlockFinishingInfo.generatedAnIf) {
/* 1032:1272 */         println("else {");
/* 1033:     */       } else {
/* 1034:1275 */         println("{");
/* 1035:     */       }
/* 1036:1277 */       this.tabs += 1;
/* 1037:1278 */       println(paramString);
/* 1038:1279 */       this.tabs -= 1;
/* 1039:1280 */       println("}");
/* 1040:     */     }
/* 1041:1283 */     if (paramCppBlockFinishingInfo.postscript != null) {
/* 1042:1284 */       println(paramCppBlockFinishingInfo.postscript);
/* 1043:     */     }
/* 1044:     */   }
/* 1045:     */   
/* 1046:     */   protected void genBlockInitAction(AlternativeBlock paramAlternativeBlock)
/* 1047:     */   {
/* 1048:1294 */     if (paramAlternativeBlock.initAction != null)
/* 1049:     */     {
/* 1050:1295 */       genLineNo(paramAlternativeBlock);
/* 1051:1296 */       printAction(processActionForSpecialSymbols(paramAlternativeBlock.initAction, paramAlternativeBlock.line, this.currentRule, null));
/* 1052:     */       
/* 1053:1298 */       genLineNo2();
/* 1054:     */     }
/* 1055:     */   }
/* 1056:     */   
/* 1057:     */   protected void genBlockPreamble(AlternativeBlock paramAlternativeBlock)
/* 1058:     */   {
/* 1059:1308 */     if ((paramAlternativeBlock instanceof RuleBlock))
/* 1060:     */     {
/* 1061:1309 */       RuleBlock localRuleBlock = (RuleBlock)paramAlternativeBlock;
/* 1062:1310 */       if (localRuleBlock.labeledElements != null) {
/* 1063:1311 */         for (int i = 0; i < localRuleBlock.labeledElements.size(); i++)
/* 1064:     */         {
/* 1065:1313 */           AlternativeElement localAlternativeElement = (AlternativeElement)localRuleBlock.labeledElements.elementAt(i);
/* 1066:1319 */           if (((localAlternativeElement instanceof RuleRefElement)) || (((localAlternativeElement instanceof AlternativeBlock)) && (!(localAlternativeElement instanceof RuleBlock)) && (!(localAlternativeElement instanceof SynPredBlock))))
/* 1067:     */           {
/* 1068:1325 */             if ((!(localAlternativeElement instanceof RuleRefElement)) && (((AlternativeBlock)localAlternativeElement).not) && (this.analyzer.subruleCanBeInverted((AlternativeBlock)localAlternativeElement, this.grammar instanceof LexerGrammar)))
/* 1069:     */             {
/* 1070:1332 */               println(this.labeledElementType + " " + localAlternativeElement.getLabel() + " = " + this.labeledElementInit + ";");
/* 1071:1333 */               if (this.grammar.buildAST) {
/* 1072:1334 */                 genASTDeclaration(localAlternativeElement);
/* 1073:     */               }
/* 1074:     */             }
/* 1075:     */             else
/* 1076:     */             {
/* 1077:1339 */               if (this.grammar.buildAST) {
/* 1078:1343 */                 genASTDeclaration(localAlternativeElement);
/* 1079:     */               }
/* 1080:1345 */               if ((this.grammar instanceof LexerGrammar)) {
/* 1081:1346 */                 println(namespaceAntlr + "RefToken " + localAlternativeElement.getLabel() + ";");
/* 1082:     */               }
/* 1083:1348 */               if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 1084:1350 */                 println(this.labeledElementType + " " + localAlternativeElement.getLabel() + " = " + this.labeledElementInit + ";");
/* 1085:     */               }
/* 1086:     */             }
/* 1087:     */           }
/* 1088:     */           else
/* 1089:     */           {
/* 1090:1358 */             println(this.labeledElementType + " " + localAlternativeElement.getLabel() + " = " + this.labeledElementInit + ";");
/* 1091:1360 */             if (this.grammar.buildAST) {
/* 1092:1362 */               if (((localAlternativeElement instanceof GrammarAtom)) && (((GrammarAtom)localAlternativeElement).getASTNodeType() != null))
/* 1093:     */               {
/* 1094:1365 */                 GrammarAtom localGrammarAtom = (GrammarAtom)localAlternativeElement;
/* 1095:1366 */                 genASTDeclaration(localAlternativeElement, "Ref" + localGrammarAtom.getASTNodeType());
/* 1096:     */               }
/* 1097:     */               else
/* 1098:     */               {
/* 1099:1370 */                 genASTDeclaration(localAlternativeElement);
/* 1100:     */               }
/* 1101:     */             }
/* 1102:     */           }
/* 1103:     */         }
/* 1104:     */       }
/* 1105:     */     }
/* 1106:     */   }
/* 1107:     */   
/* 1108:     */   public void genBody(LexerGrammar paramLexerGrammar)
/* 1109:     */     throws IOException
/* 1110:     */   {
/* 1111:1380 */     this.outputFile = (this.grammar.getClassName() + ".cpp");
/* 1112:1381 */     this.outputLine = 1;
/* 1113:1382 */     this.currentOutput = this.antlrTool.openOutputFile(this.outputFile);
/* 1114:     */     
/* 1115:     */ 
/* 1116:1385 */     this.genAST = false;
/* 1117:1386 */     this.saveText = true;
/* 1118:     */     
/* 1119:1388 */     this.tabs = 0;
/* 1120:     */     
/* 1121:     */ 
/* 1122:1391 */     genHeader(this.outputFile);
/* 1123:     */     
/* 1124:1393 */     printHeaderAction("pre_include_cpp");
/* 1125:     */     
/* 1126:1395 */     println("#include \"" + this.grammar.getClassName() + ".hpp\"");
/* 1127:1396 */     println("#include <antlr/CharBuffer.hpp>");
/* 1128:1397 */     println("#include <antlr/TokenStreamException.hpp>");
/* 1129:1398 */     println("#include <antlr/TokenStreamIOException.hpp>");
/* 1130:1399 */     println("#include <antlr/TokenStreamRecognitionException.hpp>");
/* 1131:1400 */     println("#include <antlr/CharStreamException.hpp>");
/* 1132:1401 */     println("#include <antlr/CharStreamIOException.hpp>");
/* 1133:1402 */     println("#include <antlr/NoViableAltForCharException.hpp>");
/* 1134:1403 */     if (this.grammar.debuggingOutput) {
/* 1135:1404 */       println("#include <antlr/DebuggingInputBuffer.hpp>");
/* 1136:     */     }
/* 1137:1405 */     println("");
/* 1138:1406 */     printHeaderAction("post_include_cpp");
/* 1139:1408 */     if (nameSpace != null) {
/* 1140:1409 */       nameSpace.emitDeclarations(this.currentOutput);
/* 1141:     */     }
/* 1142:1412 */     printAction(this.grammar.preambleAction);
/* 1143:     */     
/* 1144:     */ 
/* 1145:1415 */     String str = null;
/* 1146:1416 */     if (this.grammar.superClass != null)
/* 1147:     */     {
/* 1148:1417 */       str = this.grammar.superClass;
/* 1149:     */     }
/* 1150:     */     else
/* 1151:     */     {
/* 1152:1420 */       str = this.grammar.getSuperClass();
/* 1153:1421 */       if (str.lastIndexOf('.') != -1) {
/* 1154:1422 */         str = str.substring(str.lastIndexOf('.') + 1);
/* 1155:     */       }
/* 1156:1423 */       str = namespaceAntlr + str;
/* 1157:     */     }
/* 1158:1426 */     if (this.noConstructors)
/* 1159:     */     {
/* 1160:1428 */       println("#if 0");
/* 1161:1429 */       println("// constructor creation turned of with 'noConstructor' option");
/* 1162:     */     }
/* 1163:1434 */     println(this.grammar.getClassName() + "::" + this.grammar.getClassName() + "(" + namespaceStd + "istream& in)");
/* 1164:1435 */     this.tabs += 1;
/* 1165:1437 */     if (this.grammar.debuggingOutput) {
/* 1166:1438 */       println(": " + str + "(new " + namespaceAntlr + "DebuggingInputBuffer(new " + namespaceAntlr + "CharBuffer(in))," + paramLexerGrammar.caseSensitive + ")");
/* 1167:     */     } else {
/* 1168:1440 */       println(": " + str + "(new " + namespaceAntlr + "CharBuffer(in)," + paramLexerGrammar.caseSensitive + ")");
/* 1169:     */     }
/* 1170:1441 */     this.tabs -= 1;
/* 1171:1442 */     println("{");
/* 1172:1443 */     this.tabs += 1;
/* 1173:1447 */     if (this.grammar.debuggingOutput)
/* 1174:     */     {
/* 1175:1448 */       println("setRuleNames(_ruleNames);");
/* 1176:1449 */       println("setSemPredNames(_semPredNames);");
/* 1177:1450 */       println("setupDebugging();");
/* 1178:     */     }
/* 1179:1454 */     println("initLiterals();");
/* 1180:1455 */     this.tabs -= 1;
/* 1181:1456 */     println("}");
/* 1182:1457 */     println("");
/* 1183:     */     
/* 1184:     */ 
/* 1185:1460 */     println(this.grammar.getClassName() + "::" + this.grammar.getClassName() + "(" + namespaceAntlr + "InputBuffer& ib)");
/* 1186:1461 */     this.tabs += 1;
/* 1187:1463 */     if (this.grammar.debuggingOutput) {
/* 1188:1464 */       println(": " + str + "(new " + namespaceAntlr + "DebuggingInputBuffer(ib)," + paramLexerGrammar.caseSensitive + ")");
/* 1189:     */     } else {
/* 1190:1466 */       println(": " + str + "(ib," + paramLexerGrammar.caseSensitive + ")");
/* 1191:     */     }
/* 1192:1467 */     this.tabs -= 1;
/* 1193:1468 */     println("{");
/* 1194:1469 */     this.tabs += 1;
/* 1195:1473 */     if (this.grammar.debuggingOutput)
/* 1196:     */     {
/* 1197:1474 */       println("setRuleNames(_ruleNames);");
/* 1198:1475 */       println("setSemPredNames(_semPredNames);");
/* 1199:1476 */       println("setupDebugging();");
/* 1200:     */     }
/* 1201:1480 */     println("initLiterals();");
/* 1202:1481 */     this.tabs -= 1;
/* 1203:1482 */     println("}");
/* 1204:1483 */     println("");
/* 1205:     */     
/* 1206:     */ 
/* 1207:1486 */     println(this.grammar.getClassName() + "::" + this.grammar.getClassName() + "(const " + namespaceAntlr + "LexerSharedInputState& state)");
/* 1208:1487 */     this.tabs += 1;
/* 1209:1488 */     println(": " + str + "(state," + paramLexerGrammar.caseSensitive + ")");
/* 1210:1489 */     this.tabs -= 1;
/* 1211:1490 */     println("{");
/* 1212:1491 */     this.tabs += 1;
/* 1213:1495 */     if (this.grammar.debuggingOutput)
/* 1214:     */     {
/* 1215:1496 */       println("setRuleNames(_ruleNames);");
/* 1216:1497 */       println("setSemPredNames(_semPredNames);");
/* 1217:1498 */       println("setupDebugging();");
/* 1218:     */     }
/* 1219:1502 */     println("initLiterals();");
/* 1220:1503 */     this.tabs -= 1;
/* 1221:1504 */     println("}");
/* 1222:1505 */     println("");
/* 1223:1507 */     if (this.noConstructors)
/* 1224:     */     {
/* 1225:1509 */       println("// constructor creation turned of with 'noConstructor' option");
/* 1226:1510 */       println("#endif");
/* 1227:     */     }
/* 1228:1513 */     println("void " + this.grammar.getClassName() + "::initLiterals()");
/* 1229:1514 */     println("{");
/* 1230:1515 */     this.tabs += 1;
/* 1231:     */     
/* 1232:     */ 
/* 1233:     */ 
/* 1234:1519 */     Enumeration localEnumeration = this.grammar.tokenManager.getTokenSymbolKeys();
/* 1235:     */     Object localObject2;
/* 1236:1520 */     while (localEnumeration.hasMoreElements())
/* 1237:     */     {
/* 1238:1521 */       localObject1 = (String)localEnumeration.nextElement();
/* 1239:1522 */       if (((String)localObject1).charAt(0) == '"')
/* 1240:     */       {
/* 1241:1525 */         TokenSymbol localTokenSymbol = this.grammar.tokenManager.getTokenSymbol((String)localObject1);
/* 1242:1526 */         if ((localTokenSymbol instanceof StringLiteralSymbol))
/* 1243:     */         {
/* 1244:1527 */           localObject2 = (StringLiteralSymbol)localTokenSymbol;
/* 1245:1528 */           println("literals[" + ((StringLiteralSymbol)localObject2).getId() + "] = " + ((StringLiteralSymbol)localObject2).getTokenType() + ";");
/* 1246:     */         }
/* 1247:     */       }
/* 1248:     */     }
/* 1249:1533 */     this.tabs -= 1;
/* 1250:1534 */     println("}");
/* 1251:1538 */     if (this.grammar.debuggingOutput)
/* 1252:     */     {
/* 1253:1539 */       println("const char* " + this.grammar.getClassName() + "::_ruleNames[] = {");
/* 1254:1540 */       this.tabs += 1;
/* 1255:     */       
/* 1256:1542 */       localObject1 = this.grammar.rules.elements();
/* 1257:1543 */       i = 0;
/* 1258:1544 */       while (((Enumeration)localObject1).hasMoreElements())
/* 1259:     */       {
/* 1260:1545 */         localObject2 = (GrammarSymbol)((Enumeration)localObject1).nextElement();
/* 1261:1546 */         if ((localObject2 instanceof RuleSymbol)) {
/* 1262:1547 */           println("\"" + ((RuleSymbol)localObject2).getId() + "\",");
/* 1263:     */         }
/* 1264:     */       }
/* 1265:1549 */       println("0");
/* 1266:1550 */       this.tabs -= 1;
/* 1267:1551 */       println("};");
/* 1268:     */     }
/* 1269:1557 */     genNextToken();
/* 1270:     */     
/* 1271:     */ 
/* 1272:1560 */     Object localObject1 = this.grammar.rules.elements();
/* 1273:1561 */     int i = 0;
/* 1274:1562 */     while (((Enumeration)localObject1).hasMoreElements())
/* 1275:     */     {
/* 1276:1563 */       localObject2 = (RuleSymbol)((Enumeration)localObject1).nextElement();
/* 1277:1565 */       if (!((RuleSymbol)localObject2).getId().equals("mnextToken")) {
/* 1278:1566 */         genRule((RuleSymbol)localObject2, false, i++, this.grammar.getClassName() + "::");
/* 1279:     */       }
/* 1280:1568 */       exitIfError();
/* 1281:     */     }
/* 1282:1572 */     if (this.grammar.debuggingOutput) {
/* 1283:1573 */       genSemPredMap(this.grammar.getClassName() + "::");
/* 1284:     */     }
/* 1285:1576 */     genBitsets(this.bitsetsUsed, ((LexerGrammar)this.grammar).charVocabulary.size(), this.grammar.getClassName() + "::");
/* 1286:     */     
/* 1287:1578 */     println("");
/* 1288:1579 */     if (nameSpace != null) {
/* 1289:1580 */       nameSpace.emitClosures(this.currentOutput);
/* 1290:     */     }
/* 1291:1583 */     this.currentOutput.close();
/* 1292:1584 */     this.currentOutput = null;
/* 1293:     */   }
/* 1294:     */   
/* 1295:     */   public void genInitFactory(Grammar paramGrammar)
/* 1296:     */   {
/* 1297:1590 */     String str1 = "factory ";
/* 1298:1591 */     if (!paramGrammar.buildAST) {
/* 1299:1592 */       str1 = "";
/* 1300:     */     }
/* 1301:1594 */     println("void " + paramGrammar.getClassName() + "::initializeASTFactory( " + namespaceAntlr + "ASTFactory& " + str1 + ")");
/* 1302:1595 */     println("{");
/* 1303:1596 */     this.tabs += 1;
/* 1304:1598 */     if (paramGrammar.buildAST)
/* 1305:     */     {
/* 1306:1603 */       TokenManager localTokenManager = this.grammar.tokenManager;
/* 1307:1604 */       Enumeration localEnumeration = localTokenManager.getTokenSymbolKeys();
/* 1308:     */       Object localObject;
/* 1309:1605 */       while (localEnumeration.hasMoreElements())
/* 1310:     */       {
/* 1311:1607 */         String str2 = (String)localEnumeration.nextElement();
/* 1312:1608 */         localObject = localTokenManager.getTokenSymbol(str2);
/* 1313:1611 */         if (((TokenSymbol)localObject).getASTNodeType() != null)
/* 1314:     */         {
/* 1315:1614 */           this.astTypes.ensureCapacity(((TokenSymbol)localObject).getTokenType());
/* 1316:1615 */           String str3 = (String)this.astTypes.elementAt(((TokenSymbol)localObject).getTokenType());
/* 1317:1616 */           if (str3 == null)
/* 1318:     */           {
/* 1319:1617 */             this.astTypes.setElementAt(((TokenSymbol)localObject).getASTNodeType(), ((TokenSymbol)localObject).getTokenType());
/* 1320:     */           }
/* 1321:1621 */           else if (!((TokenSymbol)localObject).getASTNodeType().equals(str3))
/* 1322:     */           {
/* 1323:1623 */             this.antlrTool.warning("Token " + str2 + " taking most specific AST type", this.grammar.getFilename(), 1, 1);
/* 1324:1624 */             this.antlrTool.warning("  using " + str3 + " ignoring " + ((TokenSymbol)localObject).getASTNodeType(), this.grammar.getFilename(), 1, 1);
/* 1325:     */           }
/* 1326:     */         }
/* 1327:     */       }
/* 1328:1631 */       for (int i = 0; i < this.astTypes.size(); i++)
/* 1329:     */       {
/* 1330:1633 */         localObject = (String)this.astTypes.elementAt(i);
/* 1331:1634 */         if (localObject != null) {
/* 1332:1636 */           println("factory.registerFactory(" + i + ", \"" + (String)localObject + "\", " + (String)localObject + "::factory);");
/* 1333:     */         }
/* 1334:     */       }
/* 1335:1640 */       println("factory.setMaxNodeType(" + this.grammar.tokenManager.maxTokenType() + ");");
/* 1336:     */     }
/* 1337:1642 */     this.tabs -= 1;
/* 1338:1643 */     println("}");
/* 1339:     */   }
/* 1340:     */   
/* 1341:     */   public void genBody(ParserGrammar paramParserGrammar)
/* 1342:     */     throws IOException
/* 1343:     */   {
/* 1344:1650 */     this.outputFile = (this.grammar.getClassName() + ".cpp");
/* 1345:1651 */     this.outputLine = 1;
/* 1346:1652 */     this.currentOutput = this.antlrTool.openOutputFile(this.outputFile);
/* 1347:     */     
/* 1348:1654 */     this.genAST = this.grammar.buildAST;
/* 1349:     */     
/* 1350:1656 */     this.tabs = 0;
/* 1351:     */     
/* 1352:     */ 
/* 1353:1659 */     genHeader(this.outputFile);
/* 1354:     */     
/* 1355:1661 */     printHeaderAction("pre_include_cpp");
/* 1356:     */     
/* 1357:     */ 
/* 1358:1664 */     println("#include \"" + this.grammar.getClassName() + ".hpp\"");
/* 1359:1665 */     println("#include <antlr/NoViableAltException.hpp>");
/* 1360:1666 */     println("#include <antlr/SemanticException.hpp>");
/* 1361:1667 */     println("#include <antlr/ASTFactory.hpp>");
/* 1362:     */     
/* 1363:1669 */     printHeaderAction("post_include_cpp");
/* 1364:1671 */     if (nameSpace != null) {
/* 1365:1672 */       nameSpace.emitDeclarations(this.currentOutput);
/* 1366:     */     }
/* 1367:1675 */     printAction(this.grammar.preambleAction);
/* 1368:     */     
/* 1369:1677 */     String str = null;
/* 1370:1678 */     if (this.grammar.superClass != null)
/* 1371:     */     {
/* 1372:1679 */       str = this.grammar.superClass;
/* 1373:     */     }
/* 1374:     */     else
/* 1375:     */     {
/* 1376:1681 */       str = this.grammar.getSuperClass();
/* 1377:1682 */       if (str.lastIndexOf('.') != -1) {
/* 1378:1683 */         str = str.substring(str.lastIndexOf('.') + 1);
/* 1379:     */       }
/* 1380:1684 */       str = namespaceAntlr + str;
/* 1381:     */     }
/* 1382:     */     GrammarSymbol localGrammarSymbol;
/* 1383:1689 */     if (this.grammar.debuggingOutput)
/* 1384:     */     {
/* 1385:1690 */       println("const char* " + this.grammar.getClassName() + "::_ruleNames[] = {");
/* 1386:1691 */       this.tabs += 1;
/* 1387:     */       
/* 1388:1693 */       localEnumeration = this.grammar.rules.elements();
/* 1389:1694 */       i = 0;
/* 1390:1695 */       while (localEnumeration.hasMoreElements())
/* 1391:     */       {
/* 1392:1696 */         localGrammarSymbol = (GrammarSymbol)localEnumeration.nextElement();
/* 1393:1697 */         if ((localGrammarSymbol instanceof RuleSymbol)) {
/* 1394:1698 */           println("\"" + ((RuleSymbol)localGrammarSymbol).getId() + "\",");
/* 1395:     */         }
/* 1396:     */       }
/* 1397:1700 */       println("0");
/* 1398:1701 */       this.tabs -= 1;
/* 1399:1702 */       println("};");
/* 1400:     */     }
/* 1401:1721 */     if (this.noConstructors)
/* 1402:     */     {
/* 1403:1723 */       println("#if 0");
/* 1404:1724 */       println("// constructor creation turned of with 'noConstructor' option");
/* 1405:     */     }
/* 1406:1728 */     print(this.grammar.getClassName() + "::" + this.grammar.getClassName());
/* 1407:1729 */     println("(" + namespaceAntlr + "TokenBuffer& tokenBuf, int k)");
/* 1408:1730 */     println(": " + str + "(tokenBuf,k)");
/* 1409:1731 */     println("{");
/* 1410:     */     
/* 1411:     */ 
/* 1412:     */ 
/* 1413:1735 */     println("}");
/* 1414:1736 */     println("");
/* 1415:     */     
/* 1416:1738 */     print(this.grammar.getClassName() + "::" + this.grammar.getClassName());
/* 1417:1739 */     println("(" + namespaceAntlr + "TokenBuffer& tokenBuf)");
/* 1418:1740 */     println(": " + str + "(tokenBuf," + this.grammar.maxk + ")");
/* 1419:1741 */     println("{");
/* 1420:     */     
/* 1421:     */ 
/* 1422:     */ 
/* 1423:1745 */     println("}");
/* 1424:1746 */     println("");
/* 1425:     */     
/* 1426:     */ 
/* 1427:1749 */     print(this.grammar.getClassName() + "::" + this.grammar.getClassName());
/* 1428:1750 */     println("(" + namespaceAntlr + "TokenStream& lexer, int k)");
/* 1429:1751 */     println(": " + str + "(lexer,k)");
/* 1430:1752 */     println("{");
/* 1431:     */     
/* 1432:     */ 
/* 1433:     */ 
/* 1434:1756 */     println("}");
/* 1435:1757 */     println("");
/* 1436:     */     
/* 1437:1759 */     print(this.grammar.getClassName() + "::" + this.grammar.getClassName());
/* 1438:1760 */     println("(" + namespaceAntlr + "TokenStream& lexer)");
/* 1439:1761 */     println(": " + str + "(lexer," + this.grammar.maxk + ")");
/* 1440:1762 */     println("{");
/* 1441:     */     
/* 1442:     */ 
/* 1443:     */ 
/* 1444:1766 */     println("}");
/* 1445:1767 */     println("");
/* 1446:     */     
/* 1447:1769 */     print(this.grammar.getClassName() + "::" + this.grammar.getClassName());
/* 1448:1770 */     println("(const " + namespaceAntlr + "ParserSharedInputState& state)");
/* 1449:1771 */     println(": " + str + "(state," + this.grammar.maxk + ")");
/* 1450:1772 */     println("{");
/* 1451:     */     
/* 1452:     */ 
/* 1453:     */ 
/* 1454:1776 */     println("}");
/* 1455:1777 */     println("");
/* 1456:1779 */     if (this.noConstructors)
/* 1457:     */     {
/* 1458:1781 */       println("// constructor creation turned of with 'noConstructor' option");
/* 1459:1782 */       println("#endif");
/* 1460:     */     }
/* 1461:1785 */     this.astTypes = new Vector();
/* 1462:     */     
/* 1463:     */ 
/* 1464:1788 */     Enumeration localEnumeration = this.grammar.rules.elements();
/* 1465:1789 */     int i = 0;
/* 1466:1790 */     while (localEnumeration.hasMoreElements())
/* 1467:     */     {
/* 1468:1791 */       localGrammarSymbol = (GrammarSymbol)localEnumeration.nextElement();
/* 1469:1792 */       if ((localGrammarSymbol instanceof RuleSymbol))
/* 1470:     */       {
/* 1471:1793 */         RuleSymbol localRuleSymbol = (RuleSymbol)localGrammarSymbol;
/* 1472:1794 */         genRule(localRuleSymbol, localRuleSymbol.references.size() == 0, i++, this.grammar.getClassName() + "::");
/* 1473:     */       }
/* 1474:1796 */       exitIfError();
/* 1475:     */     }
/* 1476:1799 */     genInitFactory(paramParserGrammar);
/* 1477:     */     
/* 1478:     */ 
/* 1479:1802 */     genTokenStrings(this.grammar.getClassName() + "::");
/* 1480:     */     
/* 1481:     */ 
/* 1482:1805 */     genBitsets(this.bitsetsUsed, this.grammar.tokenManager.maxTokenType(), this.grammar.getClassName() + "::");
/* 1483:1808 */     if (this.grammar.debuggingOutput) {
/* 1484:1809 */       genSemPredMap(this.grammar.getClassName() + "::");
/* 1485:     */     }
/* 1486:1812 */     println("");
/* 1487:1813 */     println("");
/* 1488:1814 */     if (nameSpace != null) {
/* 1489:1815 */       nameSpace.emitClosures(this.currentOutput);
/* 1490:     */     }
/* 1491:1818 */     this.currentOutput.close();
/* 1492:1819 */     this.currentOutput = null;
/* 1493:     */   }
/* 1494:     */   
/* 1495:     */   public void genBody(TreeWalkerGrammar paramTreeWalkerGrammar)
/* 1496:     */     throws IOException
/* 1497:     */   {
/* 1498:1824 */     this.outputFile = (this.grammar.getClassName() + ".cpp");
/* 1499:1825 */     this.outputLine = 1;
/* 1500:1826 */     this.currentOutput = this.antlrTool.openOutputFile(this.outputFile);
/* 1501:     */     
/* 1502:     */ 
/* 1503:1829 */     this.genAST = this.grammar.buildAST;
/* 1504:1830 */     this.tabs = 0;
/* 1505:     */     
/* 1506:     */ 
/* 1507:1833 */     genHeader(this.outputFile);
/* 1508:     */     
/* 1509:1835 */     printHeaderAction("pre_include_cpp");
/* 1510:     */     
/* 1511:     */ 
/* 1512:1838 */     println("#include \"" + this.grammar.getClassName() + ".hpp\"");
/* 1513:1839 */     println("#include <antlr/Token.hpp>");
/* 1514:1840 */     println("#include <antlr/AST.hpp>");
/* 1515:1841 */     println("#include <antlr/NoViableAltException.hpp>");
/* 1516:1842 */     println("#include <antlr/MismatchedTokenException.hpp>");
/* 1517:1843 */     println("#include <antlr/SemanticException.hpp>");
/* 1518:1844 */     println("#include <antlr/BitSet.hpp>");
/* 1519:     */     
/* 1520:1846 */     printHeaderAction("post_include_cpp");
/* 1521:1848 */     if (nameSpace != null) {
/* 1522:1849 */       nameSpace.emitDeclarations(this.currentOutput);
/* 1523:     */     }
/* 1524:1852 */     printAction(this.grammar.preambleAction);
/* 1525:     */     
/* 1526:     */ 
/* 1527:1855 */     String str1 = null;
/* 1528:1856 */     if (this.grammar.superClass != null)
/* 1529:     */     {
/* 1530:1857 */       str1 = this.grammar.superClass;
/* 1531:     */     }
/* 1532:     */     else
/* 1533:     */     {
/* 1534:1860 */       str1 = this.grammar.getSuperClass();
/* 1535:1861 */       if (str1.lastIndexOf('.') != -1) {
/* 1536:1862 */         str1 = str1.substring(str1.lastIndexOf('.') + 1);
/* 1537:     */       }
/* 1538:1863 */       str1 = namespaceAntlr + str1;
/* 1539:     */     }
/* 1540:1865 */     if (this.noConstructors)
/* 1541:     */     {
/* 1542:1867 */       println("#if 0");
/* 1543:1868 */       println("// constructor creation turned of with 'noConstructor' option");
/* 1544:     */     }
/* 1545:1872 */     println(this.grammar.getClassName() + "::" + this.grammar.getClassName() + "()");
/* 1546:1873 */     println("\t: " + namespaceAntlr + "TreeParser() {");
/* 1547:1874 */     this.tabs += 1;
/* 1548:     */     
/* 1549:1876 */     this.tabs -= 1;
/* 1550:1877 */     println("}");
/* 1551:1879 */     if (this.noConstructors)
/* 1552:     */     {
/* 1553:1881 */       println("// constructor creation turned of with 'noConstructor' option");
/* 1554:1882 */       println("#endif");
/* 1555:     */     }
/* 1556:1884 */     println("");
/* 1557:     */     
/* 1558:1886 */     this.astTypes = new Vector();
/* 1559:     */     
/* 1560:     */ 
/* 1561:1889 */     Enumeration localEnumeration = this.grammar.rules.elements();
/* 1562:1890 */     int i = 0;
/* 1563:1891 */     String str2 = "";
/* 1564:1892 */     while (localEnumeration.hasMoreElements())
/* 1565:     */     {
/* 1566:1893 */       GrammarSymbol localGrammarSymbol = (GrammarSymbol)localEnumeration.nextElement();
/* 1567:1894 */       if ((localGrammarSymbol instanceof RuleSymbol))
/* 1568:     */       {
/* 1569:1895 */         RuleSymbol localRuleSymbol = (RuleSymbol)localGrammarSymbol;
/* 1570:1896 */         genRule(localRuleSymbol, localRuleSymbol.references.size() == 0, i++, this.grammar.getClassName() + "::");
/* 1571:     */       }
/* 1572:1898 */       exitIfError();
/* 1573:     */     }
/* 1574:1902 */     genInitFactory(this.grammar);
/* 1575:     */     
/* 1576:1904 */     genTokenStrings(this.grammar.getClassName() + "::");
/* 1577:     */     
/* 1578:     */ 
/* 1579:1907 */     genBitsets(this.bitsetsUsed, this.grammar.tokenManager.maxTokenType(), this.grammar.getClassName() + "::");
/* 1580:     */     
/* 1581:     */ 
/* 1582:1910 */     println("");
/* 1583:1911 */     println("");
/* 1584:1913 */     if (nameSpace != null) {
/* 1585:1914 */       nameSpace.emitClosures(this.currentOutput);
/* 1586:     */     }
/* 1587:1917 */     this.currentOutput.close();
/* 1588:1918 */     this.currentOutput = null;
/* 1589:     */   }
/* 1590:     */   
/* 1591:     */   protected void genCases(BitSet paramBitSet)
/* 1592:     */   {
/* 1593:1924 */     if ((this.DEBUG_CODE_GENERATOR) || (this.DEBUG_CPP_CODE_GENERATOR)) {
/* 1594:1924 */       System.out.println("genCases(" + paramBitSet + ")");
/* 1595:     */     }
/* 1596:1927 */     int[] arrayOfInt = paramBitSet.toArray();
/* 1597:     */     
/* 1598:1929 */     int i = 1;
/* 1599:1930 */     int j = 1;
/* 1600:1931 */     int k = 1;
/* 1601:1932 */     for (int m = 0; m < arrayOfInt.length; m++)
/* 1602:     */     {
/* 1603:1933 */       if (j == 1) {
/* 1604:1934 */         print("");
/* 1605:     */       } else {
/* 1606:1936 */         _print("  ");
/* 1607:     */       }
/* 1608:1938 */       _print("case " + getValueString(arrayOfInt[m]) + ":");
/* 1609:1940 */       if (j == i)
/* 1610:     */       {
/* 1611:1941 */         _println("");
/* 1612:1942 */         k = 1;
/* 1613:1943 */         j = 1;
/* 1614:     */       }
/* 1615:     */       else
/* 1616:     */       {
/* 1617:1946 */         j++;
/* 1618:1947 */         k = 0;
/* 1619:     */       }
/* 1620:     */     }
/* 1621:1950 */     if (k == 0) {
/* 1622:1951 */       _println("");
/* 1623:     */     }
/* 1624:     */   }
/* 1625:     */   
/* 1626:     */   public CppBlockFinishingInfo genCommonBlock(AlternativeBlock paramAlternativeBlock, boolean paramBoolean)
/* 1627:     */   {
/* 1628:1968 */     int i = 0;
/* 1629:1969 */     int j = 0;
/* 1630:1970 */     int k = 0;
/* 1631:1971 */     CppBlockFinishingInfo localCppBlockFinishingInfo = new CppBlockFinishingInfo();
/* 1632:1972 */     if ((this.DEBUG_CODE_GENERATOR) || (this.DEBUG_CPP_CODE_GENERATOR)) {
/* 1633:1972 */       System.out.println("genCommonBlk(" + paramAlternativeBlock + ")");
/* 1634:     */     }
/* 1635:1975 */     boolean bool1 = this.genAST;
/* 1636:1976 */     this.genAST = ((this.genAST) && (paramAlternativeBlock.getAutoGen()));
/* 1637:     */     
/* 1638:1978 */     boolean bool2 = this.saveText;
/* 1639:1979 */     this.saveText = ((this.saveText) && (paramAlternativeBlock.getAutoGen()));
/* 1640:     */     Object localObject1;
/* 1641:1982 */     if ((paramAlternativeBlock.not) && (this.analyzer.subruleCanBeInverted(paramAlternativeBlock, this.grammar instanceof LexerGrammar)))
/* 1642:     */     {
/* 1643:1985 */       localObject1 = this.analyzer.look(1, paramAlternativeBlock);
/* 1644:1987 */       if ((paramAlternativeBlock.getLabel() != null) && (this.syntacticPredLevel == 0)) {
/* 1645:1988 */         println(paramAlternativeBlock.getLabel() + " = " + this.lt1Value + ";");
/* 1646:     */       }
/* 1647:1992 */       genElementAST(paramAlternativeBlock);
/* 1648:     */       
/* 1649:1994 */       String str1 = "";
/* 1650:1995 */       if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 1651:1996 */         if (this.usingCustomAST) {
/* 1652:1997 */           str1 = namespaceAntlr + "RefAST" + "(_t),";
/* 1653:     */         } else {
/* 1654:1999 */           str1 = "_t,";
/* 1655:     */         }
/* 1656:     */       }
/* 1657:2003 */       println("match(" + str1 + getBitsetName(markBitsetForGen(((Lookahead)localObject1).fset)) + ");");
/* 1658:2006 */       if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 1659:2008 */         println("_t = _t->getNextSibling();");
/* 1660:     */       }
/* 1661:2010 */       return localCppBlockFinishingInfo;
/* 1662:     */     }
/* 1663:2014 */     if (paramAlternativeBlock.getAlternatives().size() == 1)
/* 1664:     */     {
/* 1665:2016 */       localObject1 = paramAlternativeBlock.getAlternativeAt(0);
/* 1666:2018 */       if (((Alternative)localObject1).synPred != null) {
/* 1667:2020 */         this.antlrTool.warning("Syntactic predicate superfluous for single alternative", this.grammar.getFilename(), paramAlternativeBlock.getAlternativeAt(0).synPred.getLine(), paramAlternativeBlock.getAlternativeAt(0).synPred.getColumn());
/* 1668:     */       }
/* 1669:2027 */       if (paramBoolean)
/* 1670:     */       {
/* 1671:2029 */         if (((Alternative)localObject1).semPred != null) {
/* 1672:2032 */           genSemPred(((Alternative)localObject1).semPred, paramAlternativeBlock.line);
/* 1673:     */         }
/* 1674:2034 */         genAlt((Alternative)localObject1, paramAlternativeBlock);
/* 1675:2035 */         return localCppBlockFinishingInfo;
/* 1676:     */       }
/* 1677:     */     }
/* 1678:2049 */     int m = 0;
/* 1679:2050 */     for (int n = 0; n < paramAlternativeBlock.getAlternatives().size(); n++)
/* 1680:     */     {
/* 1681:2052 */       Alternative localAlternative1 = paramAlternativeBlock.getAlternativeAt(n);
/* 1682:2053 */       if (suitableForCaseExpression(localAlternative1)) {
/* 1683:2054 */         m++;
/* 1684:     */       }
/* 1685:     */     }
/* 1686:     */     Object localObject2;
/* 1687:2058 */     if (m >= this.makeSwitchThreshold)
/* 1688:     */     {
/* 1689:2061 */       String str2 = lookaheadString(1);
/* 1690:2062 */       j = 1;
/* 1691:2064 */       if ((this.grammar instanceof TreeWalkerGrammar))
/* 1692:     */       {
/* 1693:2066 */         println("if (_t == " + this.labeledElementASTInit + " )");
/* 1694:2067 */         this.tabs += 1;
/* 1695:2068 */         println("_t = ASTNULL;");
/* 1696:2069 */         this.tabs -= 1;
/* 1697:     */       }
/* 1698:2071 */       println("switch ( " + str2 + ") {");
/* 1699:2072 */       for (i2 = 0; i2 < paramAlternativeBlock.alternatives.size(); i2++)
/* 1700:     */       {
/* 1701:2074 */         Alternative localAlternative2 = paramAlternativeBlock.getAlternativeAt(i2);
/* 1702:2077 */         if (suitableForCaseExpression(localAlternative2))
/* 1703:     */         {
/* 1704:2081 */           localObject2 = localAlternative2.cache[1];
/* 1705:2082 */           if ((((Lookahead)localObject2).fset.degree() == 0) && (!((Lookahead)localObject2).containsEpsilon()))
/* 1706:     */           {
/* 1707:2084 */             this.antlrTool.warning("Alternate omitted due to empty prediction set", this.grammar.getFilename(), localAlternative2.head.getLine(), localAlternative2.head.getColumn());
/* 1708:     */           }
/* 1709:     */           else
/* 1710:     */           {
/* 1711:2090 */             genCases(((Lookahead)localObject2).fset);
/* 1712:2091 */             println("{");
/* 1713:2092 */             this.tabs += 1;
/* 1714:2093 */             genAlt(localAlternative2, paramAlternativeBlock);
/* 1715:2094 */             println("break;");
/* 1716:2095 */             this.tabs -= 1;
/* 1717:2096 */             println("}");
/* 1718:     */           }
/* 1719:     */         }
/* 1720:     */       }
/* 1721:2099 */       println("default:");
/* 1722:2100 */       this.tabs += 1;
/* 1723:     */     }
/* 1724:2117 */     int i1 = (this.grammar instanceof LexerGrammar) ? this.grammar.maxk : 0;
/* 1725:2118 */     for (int i2 = i1; i2 >= 0; i2--)
/* 1726:     */     {
/* 1727:2119 */       if ((this.DEBUG_CODE_GENERATOR) || (this.DEBUG_CPP_CODE_GENERATOR)) {
/* 1728:2119 */         System.out.println("checking depth " + i2);
/* 1729:     */       }
/* 1730:2120 */       for (i3 = 0; i3 < paramAlternativeBlock.alternatives.size(); i3++)
/* 1731:     */       {
/* 1732:2121 */         localObject2 = paramAlternativeBlock.getAlternativeAt(i3);
/* 1733:2122 */         if ((this.DEBUG_CODE_GENERATOR) || (this.DEBUG_CPP_CODE_GENERATOR)) {
/* 1734:2122 */           System.out.println("genAlt: " + i3);
/* 1735:     */         }
/* 1736:2126 */         if ((j != 0) && (suitableForCaseExpression((Alternative)localObject2)))
/* 1737:     */         {
/* 1738:2129 */           if ((this.DEBUG_CODE_GENERATOR) || (this.DEBUG_CPP_CODE_GENERATOR)) {
/* 1739:2130 */             System.out.println("ignoring alt because it was in the switch");
/* 1740:     */           }
/* 1741:     */         }
/* 1742:     */         else
/* 1743:     */         {
/* 1744:2135 */           boolean bool3 = false;
/* 1745:     */           String str4;
/* 1746:2137 */           if ((this.grammar instanceof LexerGrammar))
/* 1747:     */           {
/* 1748:2140 */             int i4 = ((Alternative)localObject2).lookaheadDepth;
/* 1749:2141 */             if (i4 == 2147483647) {
/* 1750:2144 */               i4 = this.grammar.maxk;
/* 1751:     */             }
/* 1752:2146 */             while ((i4 >= 1) && (localObject2.cache[i4].containsEpsilon())) {
/* 1753:2149 */               i4--;
/* 1754:     */             }
/* 1755:2153 */             if (i4 != i2)
/* 1756:     */             {
/* 1757:2155 */               if ((!this.DEBUG_CODE_GENERATOR) && (!this.DEBUG_CPP_CODE_GENERATOR)) {
/* 1758:     */                 continue;
/* 1759:     */               }
/* 1760:2156 */               System.out.println("ignoring alt because effectiveDepth!=altDepth;" + i4 + "!=" + i2); continue;
/* 1761:     */             }
/* 1762:2159 */             bool3 = lookaheadIsEmpty((Alternative)localObject2, i4);
/* 1763:2160 */             str4 = getLookaheadTestExpression((Alternative)localObject2, i4);
/* 1764:     */           }
/* 1765:     */           else
/* 1766:     */           {
/* 1767:2164 */             bool3 = lookaheadIsEmpty((Alternative)localObject2, this.grammar.maxk);
/* 1768:2165 */             str4 = getLookaheadTestExpression((Alternative)localObject2, this.grammar.maxk);
/* 1769:     */           }
/* 1770:2170 */           if ((localObject2.cache[1].fset.degree() > 127) && (suitableForCaseExpression((Alternative)localObject2)))
/* 1771:     */           {
/* 1772:2173 */             if (i == 0)
/* 1773:     */             {
/* 1774:2177 */               if ((this.grammar instanceof TreeWalkerGrammar))
/* 1775:     */               {
/* 1776:2178 */                 println("if (_t == " + this.labeledElementASTInit + " )");
/* 1777:2179 */                 this.tabs += 1;
/* 1778:2180 */                 println("_t = ASTNULL;");
/* 1779:2181 */                 this.tabs -= 1;
/* 1780:     */               }
/* 1781:2183 */               println("if " + str4 + " {");
/* 1782:     */             }
/* 1783:     */             else
/* 1784:     */             {
/* 1785:2186 */               println("else if " + str4 + " {");
/* 1786:     */             }
/* 1787:     */           }
/* 1788:2188 */           else if ((bool3) && (((Alternative)localObject2).semPred == null) && (((Alternative)localObject2).synPred == null))
/* 1789:     */           {
/* 1790:2196 */             if (i == 0) {
/* 1791:2197 */               println("{");
/* 1792:     */             } else {
/* 1793:2200 */               println("else {");
/* 1794:     */             }
/* 1795:2202 */             localCppBlockFinishingInfo.needAnErrorClause = false;
/* 1796:     */           }
/* 1797:     */           else
/* 1798:     */           {
/* 1799:2208 */             if (((Alternative)localObject2).semPred != null)
/* 1800:     */             {
/* 1801:2212 */               ActionTransInfo localActionTransInfo = new ActionTransInfo();
/* 1802:2213 */               String str5 = processActionForSpecialSymbols(((Alternative)localObject2).semPred, paramAlternativeBlock.line, this.currentRule, localActionTransInfo);
/* 1803:2221 */               if ((this.grammar.debuggingOutput) && (((this.grammar instanceof ParserGrammar)) || ((this.grammar instanceof LexerGrammar)))) {
/* 1804:2224 */                 str4 = "(" + str4 + "&& fireSemanticPredicateEvaluated(antlr.debug.SemanticPredicateEvent.PREDICTING," + addSemPred(this.charFormatter.escapeString(str5)) + "," + str5 + "))";
/* 1805:     */               } else {
/* 1806:2227 */                 str4 = "(" + str4 + "&&(" + str5 + "))";
/* 1807:     */               }
/* 1808:     */             }
/* 1809:2231 */             if (i > 0)
/* 1810:     */             {
/* 1811:2232 */               if (((Alternative)localObject2).synPred != null)
/* 1812:     */               {
/* 1813:2233 */                 println("else {");
/* 1814:2234 */                 this.tabs += 1;
/* 1815:2235 */                 genSynPred(((Alternative)localObject2).synPred, str4);
/* 1816:2236 */                 k++;
/* 1817:     */               }
/* 1818:     */               else
/* 1819:     */               {
/* 1820:2239 */                 println("else if " + str4 + " {");
/* 1821:     */               }
/* 1822:     */             }
/* 1823:2243 */             else if (((Alternative)localObject2).synPred != null)
/* 1824:     */             {
/* 1825:2244 */               genSynPred(((Alternative)localObject2).synPred, str4);
/* 1826:     */             }
/* 1827:     */             else
/* 1828:     */             {
/* 1829:2249 */               if ((this.grammar instanceof TreeWalkerGrammar))
/* 1830:     */               {
/* 1831:2250 */                 println("if (_t == " + this.labeledElementASTInit + " )");
/* 1832:2251 */                 this.tabs += 1;
/* 1833:2252 */                 println("_t = ASTNULL;");
/* 1834:2253 */                 this.tabs -= 1;
/* 1835:     */               }
/* 1836:2255 */               println("if " + str4 + " {");
/* 1837:     */             }
/* 1838:     */           }
/* 1839:2261 */           i++;
/* 1840:2262 */           this.tabs += 1;
/* 1841:2263 */           genAlt((Alternative)localObject2, paramAlternativeBlock);
/* 1842:2264 */           this.tabs -= 1;
/* 1843:2265 */           println("}");
/* 1844:     */         }
/* 1845:     */       }
/* 1846:     */     }
/* 1847:2268 */     String str3 = "";
/* 1848:2269 */     for (int i3 = 1; i3 <= k; i3++)
/* 1849:     */     {
/* 1850:2270 */       this.tabs -= 1;
/* 1851:2271 */       str3 = str3 + "}";
/* 1852:     */     }
/* 1853:2275 */     this.genAST = bool1;
/* 1854:     */     
/* 1855:     */ 
/* 1856:2278 */     this.saveText = bool2;
/* 1857:2281 */     if (j != 0)
/* 1858:     */     {
/* 1859:2282 */       this.tabs -= 1;
/* 1860:2283 */       localCppBlockFinishingInfo.postscript = (str3 + "}");
/* 1861:2284 */       localCppBlockFinishingInfo.generatedSwitch = true;
/* 1862:2285 */       localCppBlockFinishingInfo.generatedAnIf = (i > 0);
/* 1863:     */     }
/* 1864:     */     else
/* 1865:     */     {
/* 1866:2290 */       localCppBlockFinishingInfo.postscript = str3;
/* 1867:2291 */       localCppBlockFinishingInfo.generatedSwitch = false;
/* 1868:2292 */       localCppBlockFinishingInfo.generatedAnIf = (i > 0);
/* 1869:     */     }
/* 1870:2295 */     return localCppBlockFinishingInfo;
/* 1871:     */   }
/* 1872:     */   
/* 1873:     */   private static boolean suitableForCaseExpression(Alternative paramAlternative)
/* 1874:     */   {
/* 1875:2299 */     return (paramAlternative.lookaheadDepth == 1) && (paramAlternative.semPred == null) && (!paramAlternative.cache[1].containsEpsilon()) && (paramAlternative.cache[1].fset.degree() <= 127);
/* 1876:     */   }
/* 1877:     */   
/* 1878:     */   private void genElementAST(AlternativeElement paramAlternativeElement)
/* 1879:     */   {
/* 1880:2311 */     if (((this.grammar instanceof TreeWalkerGrammar)) && (!this.grammar.buildAST))
/* 1881:     */     {
/* 1882:2317 */       if (paramAlternativeElement.getLabel() == null)
/* 1883:     */       {
/* 1884:2319 */         String str1 = this.lt1Value;
/* 1885:     */         
/* 1886:2321 */         String str2 = "tmp" + this.astVarNumber + "_AST";
/* 1887:2322 */         this.astVarNumber += 1;
/* 1888:     */         
/* 1889:2324 */         mapTreeVariable(paramAlternativeElement, str2);
/* 1890:     */         
/* 1891:2326 */         println(this.labeledElementASTType + " " + str2 + "_in = " + str1 + ";");
/* 1892:     */       }
/* 1893:2328 */       return;
/* 1894:     */     }
/* 1895:2331 */     if ((this.grammar.buildAST) && (this.syntacticPredLevel == 0))
/* 1896:     */     {
/* 1897:2333 */       int i = (this.genAST) && ((paramAlternativeElement.getLabel() != null) || (paramAlternativeElement.getAutoGenType() != 3)) ? 1 : 0;
/* 1898:2341 */       if ((paramAlternativeElement.getAutoGenType() != 3) && ((paramAlternativeElement instanceof TokenRefElement))) {
/* 1899:2343 */         i = 1;
/* 1900:     */       }
/* 1901:2345 */       int j = (this.grammar.hasSyntacticPredicate) && (i != 0) ? 1 : 0;
/* 1902:     */       String str3;
/* 1903:     */       String str4;
/* 1904:2352 */       if (paramAlternativeElement.getLabel() != null)
/* 1905:     */       {
/* 1906:2355 */         str3 = paramAlternativeElement.getLabel();
/* 1907:2356 */         str4 = paramAlternativeElement.getLabel();
/* 1908:     */       }
/* 1909:     */       else
/* 1910:     */       {
/* 1911:2361 */         str3 = this.lt1Value;
/* 1912:     */         
/* 1913:2363 */         str4 = "tmp" + this.astVarNumber;
/* 1914:2364 */         this.astVarNumber += 1;
/* 1915:     */       }
/* 1916:2368 */       if (i != 0) {
/* 1917:2370 */         if ((paramAlternativeElement instanceof GrammarAtom))
/* 1918:     */         {
/* 1919:2372 */           localObject = (GrammarAtom)paramAlternativeElement;
/* 1920:2373 */           if (((GrammarAtom)localObject).getASTNodeType() != null) {
/* 1921:2375 */             genASTDeclaration(paramAlternativeElement, str4, "Ref" + ((GrammarAtom)localObject).getASTNodeType());
/* 1922:     */           } else {
/* 1923:2380 */             genASTDeclaration(paramAlternativeElement, str4, this.labeledElementASTType);
/* 1924:     */           }
/* 1925:     */         }
/* 1926:     */         else
/* 1927:     */         {
/* 1928:2386 */           genASTDeclaration(paramAlternativeElement, str4, this.labeledElementASTType);
/* 1929:     */         }
/* 1930:     */       }
/* 1931:2392 */       Object localObject = str4 + "_AST";
/* 1932:     */       
/* 1933:     */ 
/* 1934:2395 */       mapTreeVariable(paramAlternativeElement, (String)localObject);
/* 1935:2396 */       if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 1936:2399 */         println(this.labeledElementASTType + " " + (String)localObject + "_in = " + this.labeledElementASTInit + ";");
/* 1937:     */       }
/* 1938:2403 */       if (j != 0)
/* 1939:     */       {
/* 1940:2404 */         println("if ( inputState->guessing == 0 ) {");
/* 1941:2405 */         this.tabs += 1;
/* 1942:     */       }
/* 1943:2410 */       if (paramAlternativeElement.getLabel() != null) {
/* 1944:2412 */         if ((paramAlternativeElement instanceof GrammarAtom)) {
/* 1945:2414 */           println((String)localObject + " = " + getASTCreateString((GrammarAtom)paramAlternativeElement, str3) + ";");
/* 1946:     */         } else {
/* 1947:2419 */           println((String)localObject + " = " + getASTCreateString(str3) + ";");
/* 1948:     */         }
/* 1949:     */       }
/* 1950:2425 */       if ((paramAlternativeElement.getLabel() == null) && (i != 0))
/* 1951:     */       {
/* 1952:2427 */         str3 = this.lt1Value;
/* 1953:2428 */         if ((paramAlternativeElement instanceof GrammarAtom)) {
/* 1954:2430 */           println((String)localObject + " = " + getASTCreateString((GrammarAtom)paramAlternativeElement, str3) + ";");
/* 1955:     */         } else {
/* 1956:2435 */           println((String)localObject + " = " + getASTCreateString(str3) + ";");
/* 1957:     */         }
/* 1958:2439 */         if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 1959:2442 */           println((String)localObject + "_in = " + str3 + ";");
/* 1960:     */         }
/* 1961:     */       }
/* 1962:2446 */       if (this.genAST) {
/* 1963:2448 */         switch (paramAlternativeElement.getAutoGenType())
/* 1964:     */         {
/* 1965:     */         case 1: 
/* 1966:2451 */           if ((this.usingCustomAST) || (((paramAlternativeElement instanceof GrammarAtom)) && (((GrammarAtom)paramAlternativeElement).getASTNodeType() != null))) {
/* 1967:2454 */             println("astFactory->addASTChild(currentAST, " + namespaceAntlr + "RefAST(" + (String)localObject + "));");
/* 1968:     */           } else {
/* 1969:2456 */             println("astFactory->addASTChild(currentAST, " + (String)localObject + ");");
/* 1970:     */           }
/* 1971:2458 */           break;
/* 1972:     */         case 2: 
/* 1973:2460 */           if ((this.usingCustomAST) || (((paramAlternativeElement instanceof GrammarAtom)) && (((GrammarAtom)paramAlternativeElement).getASTNodeType() != null))) {
/* 1974:2463 */             println("astFactory->makeASTRoot(currentAST, " + namespaceAntlr + "RefAST(" + (String)localObject + "));");
/* 1975:     */           } else {
/* 1976:2465 */             println("astFactory->makeASTRoot(currentAST, " + (String)localObject + ");");
/* 1977:     */           }
/* 1978:2466 */           break;
/* 1979:     */         }
/* 1980:     */       }
/* 1981:2471 */       if (j != 0)
/* 1982:     */       {
/* 1983:2473 */         this.tabs -= 1;
/* 1984:2474 */         println("}");
/* 1985:     */       }
/* 1986:     */     }
/* 1987:     */   }
/* 1988:     */   
/* 1989:     */   private void genErrorCatchForElement(AlternativeElement paramAlternativeElement)
/* 1990:     */   {
/* 1991:2482 */     if (paramAlternativeElement.getLabel() == null) {
/* 1992:2482 */       return;
/* 1993:     */     }
/* 1994:2483 */     String str = paramAlternativeElement.enclosingRuleName;
/* 1995:2484 */     if ((this.grammar instanceof LexerGrammar)) {
/* 1996:2485 */       str = CodeGenerator.encodeLexerRuleName(paramAlternativeElement.enclosingRuleName);
/* 1997:     */     }
/* 1998:2487 */     RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(str);
/* 1999:2488 */     if (localRuleSymbol == null) {
/* 2000:2489 */       this.antlrTool.panic("Enclosing rule not found!");
/* 2001:     */     }
/* 2002:2491 */     ExceptionSpec localExceptionSpec = localRuleSymbol.block.findExceptionSpec(paramAlternativeElement.getLabel());
/* 2003:2492 */     if (localExceptionSpec != null)
/* 2004:     */     {
/* 2005:2493 */       this.tabs -= 1;
/* 2006:2494 */       println("}");
/* 2007:2495 */       genErrorHandler(localExceptionSpec);
/* 2008:     */     }
/* 2009:     */   }
/* 2010:     */   
/* 2011:     */   private void genErrorHandler(ExceptionSpec paramExceptionSpec)
/* 2012:     */   {
/* 2013:2502 */     for (int i = 0; i < paramExceptionSpec.handlers.size(); i++)
/* 2014:     */     {
/* 2015:2504 */       ExceptionHandler localExceptionHandler = (ExceptionHandler)paramExceptionSpec.handlers.elementAt(i);
/* 2016:     */       
/* 2017:2506 */       println("catch (" + localExceptionHandler.exceptionTypeAndName.getText() + ") {");
/* 2018:2507 */       this.tabs += 1;
/* 2019:2508 */       if (this.grammar.hasSyntacticPredicate)
/* 2020:     */       {
/* 2021:2509 */         println("if (inputState->guessing==0) {");
/* 2022:2510 */         this.tabs += 1;
/* 2023:     */       }
/* 2024:2514 */       ActionTransInfo localActionTransInfo = new ActionTransInfo();
/* 2025:2515 */       genLineNo(localExceptionHandler.action);
/* 2026:2516 */       printAction(processActionForSpecialSymbols(localExceptionHandler.action.getText(), localExceptionHandler.action.getLine(), this.currentRule, localActionTransInfo));
/* 2027:     */       
/* 2028:     */ 
/* 2029:     */ 
/* 2030:     */ 
/* 2031:2521 */       genLineNo2();
/* 2032:2523 */       if (this.grammar.hasSyntacticPredicate)
/* 2033:     */       {
/* 2034:2525 */         this.tabs -= 1;
/* 2035:2526 */         println("} else {");
/* 2036:2527 */         this.tabs += 1;
/* 2037:     */         
/* 2038:2529 */         println("throw;");
/* 2039:2530 */         this.tabs -= 1;
/* 2040:2531 */         println("}");
/* 2041:     */       }
/* 2042:2534 */       this.tabs -= 1;
/* 2043:2535 */       println("}");
/* 2044:     */     }
/* 2045:     */   }
/* 2046:     */   
/* 2047:     */   private void genErrorTryForElement(AlternativeElement paramAlternativeElement)
/* 2048:     */   {
/* 2049:2540 */     if (paramAlternativeElement.getLabel() == null) {
/* 2050:2540 */       return;
/* 2051:     */     }
/* 2052:2541 */     String str = paramAlternativeElement.enclosingRuleName;
/* 2053:2542 */     if ((this.grammar instanceof LexerGrammar)) {
/* 2054:2543 */       str = CodeGenerator.encodeLexerRuleName(paramAlternativeElement.enclosingRuleName);
/* 2055:     */     }
/* 2056:2545 */     RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(str);
/* 2057:2546 */     if (localRuleSymbol == null) {
/* 2058:2547 */       this.antlrTool.panic("Enclosing rule not found!");
/* 2059:     */     }
/* 2060:2549 */     ExceptionSpec localExceptionSpec = localRuleSymbol.block.findExceptionSpec(paramAlternativeElement.getLabel());
/* 2061:2550 */     if (localExceptionSpec != null)
/* 2062:     */     {
/* 2063:2551 */       println("try { // for error handling");
/* 2064:2552 */       this.tabs += 1;
/* 2065:     */     }
/* 2066:     */   }
/* 2067:     */   
/* 2068:     */   protected void genHeader(String paramString)
/* 2069:     */   {
/* 2070:2558 */     println("/* $ANTLR " + Tool.version + ": " + "\"" + this.antlrTool.fileMinusPath(this.antlrTool.grammarFile) + "\"" + " -> " + "\"" + paramString + "\"$ */");
/* 2071:     */   }
/* 2072:     */   
/* 2073:     */   public void genInclude(LexerGrammar paramLexerGrammar)
/* 2074:     */     throws IOException
/* 2075:     */   {
/* 2076:2567 */     this.outputFile = (this.grammar.getClassName() + ".hpp");
/* 2077:2568 */     this.outputLine = 1;
/* 2078:2569 */     this.currentOutput = this.antlrTool.openOutputFile(this.outputFile);
/* 2079:     */     
/* 2080:     */ 
/* 2081:2572 */     this.genAST = false;
/* 2082:2573 */     this.saveText = true;
/* 2083:     */     
/* 2084:2575 */     this.tabs = 0;
/* 2085:     */     
/* 2086:     */ 
/* 2087:2578 */     println("#ifndef INC_" + this.grammar.getClassName() + "_hpp_");
/* 2088:2579 */     println("#define INC_" + this.grammar.getClassName() + "_hpp_");
/* 2089:2580 */     println("");
/* 2090:     */     
/* 2091:2582 */     printHeaderAction("pre_include_hpp");
/* 2092:     */     
/* 2093:2584 */     println("#include <antlr/config.hpp>");
/* 2094:     */     
/* 2095:     */ 
/* 2096:2587 */     genHeader(this.outputFile);
/* 2097:     */     
/* 2098:     */ 
/* 2099:2590 */     println("#include <antlr/CommonToken.hpp>");
/* 2100:2591 */     println("#include <antlr/InputBuffer.hpp>");
/* 2101:2592 */     println("#include <antlr/BitSet.hpp>");
/* 2102:2593 */     println("#include \"" + this.grammar.tokenManager.getName() + TokenTypesFileSuffix + ".hpp\"");
/* 2103:     */     
/* 2104:     */ 
/* 2105:2596 */     String str = null;
/* 2106:2597 */     if (this.grammar.superClass != null)
/* 2107:     */     {
/* 2108:2598 */       str = this.grammar.superClass;
/* 2109:     */       
/* 2110:2600 */       println("\n// Include correct superclass header with a header statement for example:");
/* 2111:2601 */       println("// header \"post_include_hpp\" {");
/* 2112:2602 */       println("// #include \"" + str + ".hpp\"");
/* 2113:2603 */       println("// }");
/* 2114:2604 */       println("// Or....");
/* 2115:2605 */       println("// header {");
/* 2116:2606 */       println("// #include \"" + str + ".hpp\"");
/* 2117:2607 */       println("// }\n");
/* 2118:     */     }
/* 2119:     */     else
/* 2120:     */     {
/* 2121:2610 */       str = this.grammar.getSuperClass();
/* 2122:2611 */       if (str.lastIndexOf('.') != -1) {
/* 2123:2612 */         str = str.substring(str.lastIndexOf('.') + 1);
/* 2124:     */       }
/* 2125:2613 */       println("#include <antlr/" + str + ".hpp>");
/* 2126:2614 */       str = namespaceAntlr + str;
/* 2127:     */     }
/* 2128:2618 */     printHeaderAction("post_include_hpp");
/* 2129:2620 */     if (nameSpace != null) {
/* 2130:2621 */       nameSpace.emitDeclarations(this.currentOutput);
/* 2131:     */     }
/* 2132:2623 */     printHeaderAction("");
/* 2133:2626 */     if (this.grammar.comment != null) {
/* 2134:2627 */       _println(this.grammar.comment);
/* 2135:     */     }
/* 2136:2631 */     print("class CUSTOM_API " + this.grammar.getClassName() + " : public " + str);
/* 2137:2632 */     println(", public " + this.grammar.tokenManager.getName() + TokenTypesFileSuffix);
/* 2138:     */     
/* 2139:2634 */     Token localToken = (Token)this.grammar.options.get("classHeaderSuffix");
/* 2140:2635 */     if (localToken != null)
/* 2141:     */     {
/* 2142:2636 */       localObject = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/* 2143:2637 */       if (localObject != null) {
/* 2144:2638 */         print(", " + (String)localObject);
/* 2145:     */       }
/* 2146:     */     }
/* 2147:2641 */     println("{");
/* 2148:2644 */     if (this.grammar.classMemberAction != null)
/* 2149:     */     {
/* 2150:2645 */       genLineNo(this.grammar.classMemberAction);
/* 2151:2646 */       print(processActionForSpecialSymbols(this.grammar.classMemberAction.getText(), this.grammar.classMemberAction.getLine(), this.currentRule, null));
/* 2152:     */       
/* 2153:     */ 
/* 2154:     */ 
/* 2155:     */ 
/* 2156:2651 */       genLineNo2();
/* 2157:     */     }
/* 2158:2655 */     this.tabs = 0;
/* 2159:2656 */     println("private:");
/* 2160:2657 */     this.tabs = 1;
/* 2161:2658 */     println("void initLiterals();");
/* 2162:     */     
/* 2163:     */ 
/* 2164:2661 */     this.tabs = 0;
/* 2165:2662 */     println("public:");
/* 2166:2663 */     this.tabs = 1;
/* 2167:2664 */     println("bool getCaseSensitiveLiterals() const");
/* 2168:2665 */     println("{");
/* 2169:2666 */     this.tabs += 1;
/* 2170:2667 */     println("return " + paramLexerGrammar.caseSensitiveLiterals + ";");
/* 2171:2668 */     this.tabs -= 1;
/* 2172:2669 */     println("}");
/* 2173:     */     
/* 2174:     */ 
/* 2175:2672 */     this.tabs = 0;
/* 2176:2673 */     println("public:");
/* 2177:2674 */     this.tabs = 1;
/* 2178:2676 */     if (this.noConstructors)
/* 2179:     */     {
/* 2180:2678 */       this.tabs = 0;
/* 2181:2679 */       println("#if 0");
/* 2182:2680 */       println("// constructor creation turned of with 'noConstructor' option");
/* 2183:2681 */       this.tabs = 1;
/* 2184:     */     }
/* 2185:2685 */     println(this.grammar.getClassName() + "(" + namespaceStd + "istream& in);");
/* 2186:     */     
/* 2187:     */ 
/* 2188:2688 */     println(this.grammar.getClassName() + "(" + namespaceAntlr + "InputBuffer& ib);");
/* 2189:     */     
/* 2190:2690 */     println(this.grammar.getClassName() + "(const " + namespaceAntlr + "LexerSharedInputState& state);");
/* 2191:2691 */     if (this.noConstructors)
/* 2192:     */     {
/* 2193:2693 */       this.tabs = 0;
/* 2194:2694 */       println("// constructor creation turned of with 'noConstructor' option");
/* 2195:2695 */       println("#endif");
/* 2196:2696 */       this.tabs = 1;
/* 2197:     */     }
/* 2198:2702 */     println(namespaceAntlr + "RefToken nextToken();");
/* 2199:     */     
/* 2200:     */ 
/* 2201:2705 */     Object localObject = this.grammar.rules.elements();
/* 2202:2706 */     while (((Enumeration)localObject).hasMoreElements())
/* 2203:     */     {
/* 2204:2707 */       RuleSymbol localRuleSymbol = (RuleSymbol)((Enumeration)localObject).nextElement();
/* 2205:2709 */       if (!localRuleSymbol.getId().equals("mnextToken")) {
/* 2206:2710 */         genRuleHeader(localRuleSymbol, false);
/* 2207:     */       }
/* 2208:2712 */       exitIfError();
/* 2209:     */     }
/* 2210:2716 */     this.tabs = 0;
/* 2211:2717 */     println("private:");
/* 2212:2718 */     this.tabs = 1;
/* 2213:2721 */     if (this.grammar.debuggingOutput) {
/* 2214:2722 */       println("static const char* _ruleNames[];");
/* 2215:     */     }
/* 2216:2726 */     if (this.grammar.debuggingOutput) {
/* 2217:2727 */       println("static const char* _semPredNames[];");
/* 2218:     */     }
/* 2219:2730 */     genBitsetsHeader(this.bitsetsUsed, ((LexerGrammar)this.grammar).charVocabulary.size());
/* 2220:     */     
/* 2221:2732 */     this.tabs = 0;
/* 2222:2733 */     println("};");
/* 2223:2734 */     println("");
/* 2224:2735 */     if (nameSpace != null) {
/* 2225:2736 */       nameSpace.emitClosures(this.currentOutput);
/* 2226:     */     }
/* 2227:2739 */     println("#endif /*INC_" + this.grammar.getClassName() + "_hpp_*/");
/* 2228:     */     
/* 2229:     */ 
/* 2230:2742 */     this.currentOutput.close();
/* 2231:2743 */     this.currentOutput = null;
/* 2232:     */   }
/* 2233:     */   
/* 2234:     */   public void genInclude(ParserGrammar paramParserGrammar)
/* 2235:     */     throws IOException
/* 2236:     */   {
/* 2237:2748 */     this.outputFile = (this.grammar.getClassName() + ".hpp");
/* 2238:2749 */     this.outputLine = 1;
/* 2239:2750 */     this.currentOutput = this.antlrTool.openOutputFile(this.outputFile);
/* 2240:     */     
/* 2241:     */ 
/* 2242:2753 */     this.genAST = this.grammar.buildAST;
/* 2243:     */     
/* 2244:2755 */     this.tabs = 0;
/* 2245:     */     
/* 2246:     */ 
/* 2247:2758 */     println("#ifndef INC_" + this.grammar.getClassName() + "_hpp_");
/* 2248:2759 */     println("#define INC_" + this.grammar.getClassName() + "_hpp_");
/* 2249:2760 */     println("");
/* 2250:2761 */     printHeaderAction("pre_include_hpp");
/* 2251:2762 */     println("#include <antlr/config.hpp>");
/* 2252:     */     
/* 2253:     */ 
/* 2254:2765 */     genHeader(this.outputFile);
/* 2255:     */     
/* 2256:     */ 
/* 2257:2768 */     println("#include <antlr/TokenStream.hpp>");
/* 2258:2769 */     println("#include <antlr/TokenBuffer.hpp>");
/* 2259:2770 */     println("#include \"" + this.grammar.tokenManager.getName() + TokenTypesFileSuffix + ".hpp\"");
/* 2260:     */     
/* 2261:     */ 
/* 2262:2773 */     String str = null;
/* 2263:2774 */     if (this.grammar.superClass != null)
/* 2264:     */     {
/* 2265:2775 */       str = this.grammar.superClass;
/* 2266:2776 */       println("\n// Include correct superclass header with a header statement for example:");
/* 2267:2777 */       println("// header \"post_include_hpp\" {");
/* 2268:2778 */       println("// #include \"" + str + ".hpp\"");
/* 2269:2779 */       println("// }");
/* 2270:2780 */       println("// Or....");
/* 2271:2781 */       println("// header {");
/* 2272:2782 */       println("// #include \"" + str + ".hpp\"");
/* 2273:2783 */       println("// }\n");
/* 2274:     */     }
/* 2275:     */     else
/* 2276:     */     {
/* 2277:2786 */       str = this.grammar.getSuperClass();
/* 2278:2787 */       if (str.lastIndexOf('.') != -1) {
/* 2279:2788 */         str = str.substring(str.lastIndexOf('.') + 1);
/* 2280:     */       }
/* 2281:2789 */       println("#include <antlr/" + str + ".hpp>");
/* 2282:2790 */       str = namespaceAntlr + str;
/* 2283:     */     }
/* 2284:2792 */     println("");
/* 2285:     */     
/* 2286:     */ 
/* 2287:2795 */     printHeaderAction("post_include_hpp");
/* 2288:2797 */     if (nameSpace != null) {
/* 2289:2798 */       nameSpace.emitDeclarations(this.currentOutput);
/* 2290:     */     }
/* 2291:2800 */     printHeaderAction("");
/* 2292:2803 */     if (this.grammar.comment != null) {
/* 2293:2804 */       _println(this.grammar.comment);
/* 2294:     */     }
/* 2295:2808 */     print("class CUSTOM_API " + this.grammar.getClassName() + " : public " + str);
/* 2296:2809 */     println(", public " + this.grammar.tokenManager.getName() + TokenTypesFileSuffix);
/* 2297:     */     
/* 2298:2811 */     Token localToken = (Token)this.grammar.options.get("classHeaderSuffix");
/* 2299:2812 */     if (localToken != null)
/* 2300:     */     {
/* 2301:2813 */       localObject = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/* 2302:2814 */       if (localObject != null) {
/* 2303:2815 */         print(", " + (String)localObject);
/* 2304:     */       }
/* 2305:     */     }
/* 2306:2817 */     println("{");
/* 2307:2821 */     if (this.grammar.debuggingOutput) {
/* 2308:2822 */       println("public: static const char* _ruleNames[];");
/* 2309:     */     }
/* 2310:2825 */     if (this.grammar.classMemberAction != null)
/* 2311:     */     {
/* 2312:2826 */       genLineNo(this.grammar.classMemberAction.getLine());
/* 2313:2827 */       print(processActionForSpecialSymbols(this.grammar.classMemberAction.getText(), this.grammar.classMemberAction.getLine(), this.currentRule, null));
/* 2314:     */       
/* 2315:     */ 
/* 2316:     */ 
/* 2317:     */ 
/* 2318:2832 */       genLineNo2();
/* 2319:     */     }
/* 2320:2834 */     println("public:");
/* 2321:2835 */     this.tabs = 1;
/* 2322:2836 */     println("void initializeASTFactory( " + namespaceAntlr + "ASTFactory& factory );");
/* 2323:     */     
/* 2324:     */ 
/* 2325:     */ 
/* 2326:     */ 
/* 2327:2841 */     this.tabs = 0;
/* 2328:2842 */     if (this.noConstructors)
/* 2329:     */     {
/* 2330:2844 */       println("#if 0");
/* 2331:2845 */       println("// constructor creation turned of with 'noConstructor' option");
/* 2332:     */     }
/* 2333:2847 */     println("protected:");
/* 2334:2848 */     this.tabs = 1;
/* 2335:2849 */     println(this.grammar.getClassName() + "(" + namespaceAntlr + "TokenBuffer& tokenBuf, int k);");
/* 2336:2850 */     this.tabs = 0;
/* 2337:2851 */     println("public:");
/* 2338:2852 */     this.tabs = 1;
/* 2339:2853 */     println(this.grammar.getClassName() + "(" + namespaceAntlr + "TokenBuffer& tokenBuf);");
/* 2340:     */     
/* 2341:     */ 
/* 2342:2856 */     this.tabs = 0;
/* 2343:2857 */     println("protected:");
/* 2344:2858 */     this.tabs = 1;
/* 2345:2859 */     println(this.grammar.getClassName() + "(" + namespaceAntlr + "TokenStream& lexer, int k);");
/* 2346:2860 */     this.tabs = 0;
/* 2347:2861 */     println("public:");
/* 2348:2862 */     this.tabs = 1;
/* 2349:2863 */     println(this.grammar.getClassName() + "(" + namespaceAntlr + "TokenStream& lexer);");
/* 2350:     */     
/* 2351:2865 */     println(this.grammar.getClassName() + "(const " + namespaceAntlr + "ParserSharedInputState& state);");
/* 2352:2866 */     if (this.noConstructors)
/* 2353:     */     {
/* 2354:2868 */       this.tabs = 0;
/* 2355:2869 */       println("// constructor creation turned of with 'noConstructor' option");
/* 2356:2870 */       println("#endif");
/* 2357:2871 */       this.tabs = 1;
/* 2358:     */     }
/* 2359:2874 */     println("int getNumTokens() const");
/* 2360:2875 */     println("{");this.tabs += 1;
/* 2361:2876 */     println("return " + this.grammar.getClassName() + "::NUM_TOKENS;");
/* 2362:2877 */     this.tabs -= 1;println("}");
/* 2363:2878 */     println("const char* getTokenName( int type ) const");
/* 2364:2879 */     println("{");this.tabs += 1;
/* 2365:2880 */     println("if( type > getNumTokens() ) return 0;");
/* 2366:2881 */     println("return " + this.grammar.getClassName() + "::tokenNames[type];");
/* 2367:2882 */     this.tabs -= 1;println("}");
/* 2368:2883 */     println("const char* const* getTokenNames() const");
/* 2369:2884 */     println("{");this.tabs += 1;
/* 2370:2885 */     println("return " + this.grammar.getClassName() + "::tokenNames;");
/* 2371:2886 */     this.tabs -= 1;println("}");
/* 2372:     */     
/* 2373:     */ 
/* 2374:2889 */     Object localObject = this.grammar.rules.elements();
/* 2375:2890 */     while (((Enumeration)localObject).hasMoreElements())
/* 2376:     */     {
/* 2377:2891 */       GrammarSymbol localGrammarSymbol = (GrammarSymbol)((Enumeration)localObject).nextElement();
/* 2378:2892 */       if ((localGrammarSymbol instanceof RuleSymbol))
/* 2379:     */       {
/* 2380:2893 */         RuleSymbol localRuleSymbol = (RuleSymbol)localGrammarSymbol;
/* 2381:2894 */         genRuleHeader(localRuleSymbol, localRuleSymbol.references.size() == 0);
/* 2382:     */       }
/* 2383:2896 */       exitIfError();
/* 2384:     */     }
/* 2385:2904 */     this.tabs = 0;println("public:");this.tabs = 1;
/* 2386:2905 */     println(namespaceAntlr + "RefAST getAST()");
/* 2387:2906 */     println("{");
/* 2388:2907 */     if (this.usingCustomAST)
/* 2389:     */     {
/* 2390:2909 */       this.tabs += 1;
/* 2391:2910 */       println("return " + namespaceAntlr + "RefAST(returnAST);");
/* 2392:2911 */       this.tabs -= 1;
/* 2393:     */     }
/* 2394:     */     else
/* 2395:     */     {
/* 2396:2915 */       this.tabs += 1;
/* 2397:2916 */       println("return returnAST;");
/* 2398:2917 */       this.tabs -= 1;
/* 2399:     */     }
/* 2400:2919 */     println("}");
/* 2401:2920 */     println("");
/* 2402:     */     
/* 2403:2922 */     this.tabs = 0;println("protected:");this.tabs = 1;
/* 2404:2923 */     println(this.labeledElementASTType + " returnAST;");
/* 2405:     */     
/* 2406:     */ 
/* 2407:2926 */     this.tabs = 0;
/* 2408:2927 */     println("private:");
/* 2409:2928 */     this.tabs = 1;
/* 2410:     */     
/* 2411:     */ 
/* 2412:2931 */     println("static const char* tokenNames[];");
/* 2413:     */     
/* 2414:2933 */     _println("#ifndef NO_STATIC_CONSTS");
/* 2415:2934 */     println("static const int NUM_TOKENS = " + this.grammar.tokenManager.getVocabulary().size() + ";");
/* 2416:2935 */     _println("#else");
/* 2417:2936 */     println("enum {");
/* 2418:2937 */     println("\tNUM_TOKENS = " + this.grammar.tokenManager.getVocabulary().size());
/* 2419:2938 */     println("};");
/* 2420:2939 */     _println("#endif");
/* 2421:     */     
/* 2422:     */ 
/* 2423:2942 */     genBitsetsHeader(this.bitsetsUsed, this.grammar.tokenManager.maxTokenType());
/* 2424:2945 */     if (this.grammar.debuggingOutput) {
/* 2425:2946 */       println("static const char* _semPredNames[];");
/* 2426:     */     }
/* 2427:2949 */     this.tabs = 0;
/* 2428:2950 */     println("};");
/* 2429:2951 */     println("");
/* 2430:2952 */     if (nameSpace != null) {
/* 2431:2953 */       nameSpace.emitClosures(this.currentOutput);
/* 2432:     */     }
/* 2433:2956 */     println("#endif /*INC_" + this.grammar.getClassName() + "_hpp_*/");
/* 2434:     */     
/* 2435:     */ 
/* 2436:2959 */     this.currentOutput.close();
/* 2437:2960 */     this.currentOutput = null;
/* 2438:     */   }
/* 2439:     */   
/* 2440:     */   public void genInclude(TreeWalkerGrammar paramTreeWalkerGrammar)
/* 2441:     */     throws IOException
/* 2442:     */   {
/* 2443:2965 */     this.outputFile = (this.grammar.getClassName() + ".hpp");
/* 2444:2966 */     this.outputLine = 1;
/* 2445:2967 */     this.currentOutput = this.antlrTool.openOutputFile(this.outputFile);
/* 2446:     */     
/* 2447:     */ 
/* 2448:2970 */     this.genAST = this.grammar.buildAST;
/* 2449:2971 */     this.tabs = 0;
/* 2450:     */     
/* 2451:     */ 
/* 2452:2974 */     println("#ifndef INC_" + this.grammar.getClassName() + "_hpp_");
/* 2453:2975 */     println("#define INC_" + this.grammar.getClassName() + "_hpp_");
/* 2454:2976 */     println("");
/* 2455:2977 */     printHeaderAction("pre_include_hpp");
/* 2456:2978 */     println("#include <antlr/config.hpp>");
/* 2457:2979 */     println("#include \"" + this.grammar.tokenManager.getName() + TokenTypesFileSuffix + ".hpp\"");
/* 2458:     */     
/* 2459:     */ 
/* 2460:2982 */     genHeader(this.outputFile);
/* 2461:     */     
/* 2462:     */ 
/* 2463:2985 */     String str1 = null;
/* 2464:2986 */     if (this.grammar.superClass != null)
/* 2465:     */     {
/* 2466:2987 */       str1 = this.grammar.superClass;
/* 2467:2988 */       println("\n// Include correct superclass header with a header statement for example:");
/* 2468:2989 */       println("// header \"post_include_hpp\" {");
/* 2469:2990 */       println("// #include \"" + str1 + ".hpp\"");
/* 2470:2991 */       println("// }");
/* 2471:2992 */       println("// Or....");
/* 2472:2993 */       println("// header {");
/* 2473:2994 */       println("// #include \"" + str1 + ".hpp\"");
/* 2474:2995 */       println("// }\n");
/* 2475:     */     }
/* 2476:     */     else
/* 2477:     */     {
/* 2478:2998 */       str1 = this.grammar.getSuperClass();
/* 2479:2999 */       if (str1.lastIndexOf('.') != -1) {
/* 2480:3000 */         str1 = str1.substring(str1.lastIndexOf('.') + 1);
/* 2481:     */       }
/* 2482:3001 */       println("#include <antlr/" + str1 + ".hpp>");
/* 2483:3002 */       str1 = namespaceAntlr + str1;
/* 2484:     */     }
/* 2485:3004 */     println("");
/* 2486:     */     
/* 2487:     */ 
/* 2488:     */ 
/* 2489:     */ 
/* 2490:3009 */     printHeaderAction("post_include_hpp");
/* 2491:3011 */     if (nameSpace != null) {
/* 2492:3012 */       nameSpace.emitDeclarations(this.currentOutput);
/* 2493:     */     }
/* 2494:3014 */     printHeaderAction("");
/* 2495:3017 */     if (this.grammar.comment != null) {
/* 2496:3018 */       _println(this.grammar.comment);
/* 2497:     */     }
/* 2498:3022 */     print("class CUSTOM_API " + this.grammar.getClassName() + " : public " + str1);
/* 2499:3023 */     println(", public " + this.grammar.tokenManager.getName() + TokenTypesFileSuffix);
/* 2500:     */     
/* 2501:3025 */     Token localToken = (Token)this.grammar.options.get("classHeaderSuffix");
/* 2502:3026 */     if (localToken != null)
/* 2503:     */     {
/* 2504:3027 */       localObject = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/* 2505:3028 */       if (localObject != null) {
/* 2506:3029 */         print(", " + (String)localObject);
/* 2507:     */       }
/* 2508:     */     }
/* 2509:3032 */     println("{");
/* 2510:3035 */     if (this.grammar.classMemberAction != null)
/* 2511:     */     {
/* 2512:3036 */       genLineNo(this.grammar.classMemberAction.getLine());
/* 2513:3037 */       print(processActionForSpecialSymbols(this.grammar.classMemberAction.getText(), this.grammar.classMemberAction.getLine(), this.currentRule, null));
/* 2514:     */       
/* 2515:     */ 
/* 2516:     */ 
/* 2517:     */ 
/* 2518:3042 */       genLineNo2();
/* 2519:     */     }
/* 2520:3046 */     this.tabs = 0;
/* 2521:3047 */     println("public:");
/* 2522:3049 */     if (this.noConstructors)
/* 2523:     */     {
/* 2524:3051 */       println("#if 0");
/* 2525:3052 */       println("// constructor creation turned of with 'noConstructor' option");
/* 2526:     */     }
/* 2527:3054 */     this.tabs = 1;
/* 2528:3055 */     println(this.grammar.getClassName() + "();");
/* 2529:3056 */     if (this.noConstructors)
/* 2530:     */     {
/* 2531:3058 */       this.tabs = 0;
/* 2532:3059 */       println("#endif");
/* 2533:3060 */       this.tabs = 1;
/* 2534:     */     }
/* 2535:3064 */     println("static void initializeASTFactory( " + namespaceAntlr + "ASTFactory& factory );");
/* 2536:     */     
/* 2537:3066 */     println("int getNumTokens() const");
/* 2538:3067 */     println("{");this.tabs += 1;
/* 2539:3068 */     println("return " + this.grammar.getClassName() + "::NUM_TOKENS;");
/* 2540:3069 */     this.tabs -= 1;println("}");
/* 2541:3070 */     println("const char* getTokenName( int type ) const");
/* 2542:3071 */     println("{");this.tabs += 1;
/* 2543:3072 */     println("if( type > getNumTokens() ) return 0;");
/* 2544:3073 */     println("return " + this.grammar.getClassName() + "::tokenNames[type];");
/* 2545:3074 */     this.tabs -= 1;println("}");
/* 2546:3075 */     println("const char* const* getTokenNames() const");
/* 2547:3076 */     println("{");this.tabs += 1;
/* 2548:3077 */     println("return " + this.grammar.getClassName() + "::tokenNames;");
/* 2549:3078 */     this.tabs -= 1;println("}");
/* 2550:     */     
/* 2551:     */ 
/* 2552:3081 */     Object localObject = this.grammar.rules.elements();
/* 2553:3082 */     String str2 = "";
/* 2554:3083 */     while (((Enumeration)localObject).hasMoreElements())
/* 2555:     */     {
/* 2556:3084 */       GrammarSymbol localGrammarSymbol = (GrammarSymbol)((Enumeration)localObject).nextElement();
/* 2557:3085 */       if ((localGrammarSymbol instanceof RuleSymbol))
/* 2558:     */       {
/* 2559:3086 */         RuleSymbol localRuleSymbol = (RuleSymbol)localGrammarSymbol;
/* 2560:3087 */         genRuleHeader(localRuleSymbol, localRuleSymbol.references.size() == 0);
/* 2561:     */       }
/* 2562:3089 */       exitIfError();
/* 2563:     */     }
/* 2564:3091 */     this.tabs = 0;println("public:");this.tabs = 1;
/* 2565:3092 */     println(namespaceAntlr + "RefAST getAST()");
/* 2566:3093 */     println("{");
/* 2567:3094 */     if (this.usingCustomAST)
/* 2568:     */     {
/* 2569:3096 */       this.tabs += 1;
/* 2570:3097 */       println("return " + namespaceAntlr + "RefAST(returnAST);");
/* 2571:3098 */       this.tabs -= 1;
/* 2572:     */     }
/* 2573:     */     else
/* 2574:     */     {
/* 2575:3102 */       this.tabs += 1;
/* 2576:3103 */       println("return returnAST;");
/* 2577:3104 */       this.tabs -= 1;
/* 2578:     */     }
/* 2579:3106 */     println("}");
/* 2580:3107 */     println("");
/* 2581:     */     
/* 2582:3109 */     this.tabs = 0;println("protected:");this.tabs = 1;
/* 2583:3110 */     println(this.labeledElementASTType + " returnAST;");
/* 2584:3111 */     println(this.labeledElementASTType + " _retTree;");
/* 2585:     */     
/* 2586:     */ 
/* 2587:3114 */     this.tabs = 0;
/* 2588:3115 */     println("private:");
/* 2589:3116 */     this.tabs = 1;
/* 2590:     */     
/* 2591:     */ 
/* 2592:3119 */     println("static const char* tokenNames[];");
/* 2593:     */     
/* 2594:3121 */     _println("#ifndef NO_STATIC_CONSTS");
/* 2595:3122 */     println("static const int NUM_TOKENS = " + this.grammar.tokenManager.getVocabulary().size() + ";");
/* 2596:3123 */     _println("#else");
/* 2597:3124 */     println("enum {");
/* 2598:3125 */     println("\tNUM_TOKENS = " + this.grammar.tokenManager.getVocabulary().size());
/* 2599:3126 */     println("};");
/* 2600:3127 */     _println("#endif");
/* 2601:     */     
/* 2602:     */ 
/* 2603:3130 */     genBitsetsHeader(this.bitsetsUsed, this.grammar.tokenManager.maxTokenType());
/* 2604:     */     
/* 2605:     */ 
/* 2606:3133 */     this.tabs = 0;
/* 2607:3134 */     println("};");
/* 2608:3135 */     println("");
/* 2609:3136 */     if (nameSpace != null) {
/* 2610:3137 */       nameSpace.emitClosures(this.currentOutput);
/* 2611:     */     }
/* 2612:3140 */     println("#endif /*INC_" + this.grammar.getClassName() + "_hpp_*/");
/* 2613:     */     
/* 2614:     */ 
/* 2615:3143 */     this.currentOutput.close();
/* 2616:3144 */     this.currentOutput = null;
/* 2617:     */   }
/* 2618:     */   
/* 2619:     */   protected void genASTDeclaration(AlternativeElement paramAlternativeElement)
/* 2620:     */   {
/* 2621:3148 */     genASTDeclaration(paramAlternativeElement, this.labeledElementASTType);
/* 2622:     */   }
/* 2623:     */   
/* 2624:     */   protected void genASTDeclaration(AlternativeElement paramAlternativeElement, String paramString)
/* 2625:     */   {
/* 2626:3152 */     genASTDeclaration(paramAlternativeElement, paramAlternativeElement.getLabel(), paramString);
/* 2627:     */   }
/* 2628:     */   
/* 2629:     */   protected void genASTDeclaration(AlternativeElement paramAlternativeElement, String paramString1, String paramString2)
/* 2630:     */   {
/* 2631:3157 */     if (this.declaredASTVariables.contains(paramAlternativeElement)) {
/* 2632:3158 */       return;
/* 2633:     */     }
/* 2634:3160 */     String str = this.labeledElementASTInit;
/* 2635:3162 */     if (((paramAlternativeElement instanceof GrammarAtom)) && (((GrammarAtom)paramAlternativeElement).getASTNodeType() != null)) {
/* 2636:3164 */       str = "Ref" + ((GrammarAtom)paramAlternativeElement).getASTNodeType() + "(" + this.labeledElementASTInit + ")";
/* 2637:     */     }
/* 2638:3167 */     println(paramString2 + " " + paramString1 + "_AST = " + str + ";");
/* 2639:     */     
/* 2640:     */ 
/* 2641:3170 */     this.declaredASTVariables.put(paramAlternativeElement, paramAlternativeElement);
/* 2642:     */   }
/* 2643:     */   
/* 2644:     */   private void genLiteralsTest()
/* 2645:     */   {
/* 2646:3173 */     println("_ttype = testLiteralsTable(_ttype);");
/* 2647:     */   }
/* 2648:     */   
/* 2649:     */   private void genLiteralsTestForPartialToken()
/* 2650:     */   {
/* 2651:3176 */     println("_ttype = testLiteralsTable(text.substr(_begin, text.length()-_begin),_ttype);");
/* 2652:     */   }
/* 2653:     */   
/* 2654:     */   protected void genMatch(BitSet paramBitSet) {}
/* 2655:     */   
/* 2656:     */   protected void genMatch(GrammarAtom paramGrammarAtom)
/* 2657:     */   {
/* 2658:3181 */     if ((paramGrammarAtom instanceof StringLiteralElement))
/* 2659:     */     {
/* 2660:3182 */       if ((this.grammar instanceof LexerGrammar)) {
/* 2661:3183 */         genMatchUsingAtomText(paramGrammarAtom);
/* 2662:     */       } else {
/* 2663:3186 */         genMatchUsingAtomTokenType(paramGrammarAtom);
/* 2664:     */       }
/* 2665:     */     }
/* 2666:3189 */     else if ((paramGrammarAtom instanceof CharLiteralElement)) {
/* 2667:3191 */       this.antlrTool.error("cannot ref character literals in grammar: " + paramGrammarAtom);
/* 2668:3193 */     } else if ((paramGrammarAtom instanceof TokenRefElement)) {
/* 2669:3194 */       genMatchUsingAtomTokenType(paramGrammarAtom);
/* 2670:3195 */     } else if ((paramGrammarAtom instanceof WildcardElement)) {
/* 2671:3196 */       gen((WildcardElement)paramGrammarAtom);
/* 2672:     */     }
/* 2673:     */   }
/* 2674:     */   
/* 2675:     */   protected void genMatchUsingAtomText(GrammarAtom paramGrammarAtom)
/* 2676:     */   {
/* 2677:3201 */     String str1 = "";
/* 2678:3202 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2679:3203 */       if (this.usingCustomAST) {
/* 2680:3204 */         str1 = namespaceAntlr + "RefAST" + "(_t),";
/* 2681:     */       } else {
/* 2682:3206 */         str1 = "_t,";
/* 2683:     */       }
/* 2684:     */     }
/* 2685:3210 */     if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramGrammarAtom.getAutoGenType() == 3))) {
/* 2686:3211 */       println("_saveIndex = text.length();");
/* 2687:     */     }
/* 2688:3214 */     print(paramGrammarAtom.not ? "matchNot(" : "match(");
/* 2689:3215 */     _print(str1);
/* 2690:3218 */     if (paramGrammarAtom.atomText.equals("EOF"))
/* 2691:     */     {
/* 2692:3220 */       _print(namespaceAntlr + "Token::EOF_TYPE");
/* 2693:     */     }
/* 2694:3224 */     else if ((this.grammar instanceof LexerGrammar))
/* 2695:     */     {
/* 2696:3226 */       String str2 = convertJavaToCppString(paramGrammarAtom.atomText, false);
/* 2697:3227 */       _print(str2);
/* 2698:     */     }
/* 2699:     */     else
/* 2700:     */     {
/* 2701:3230 */       _print(paramGrammarAtom.atomText);
/* 2702:     */     }
/* 2703:3233 */     _println(");");
/* 2704:3235 */     if (((this.grammar instanceof LexerGrammar)) && ((!this.saveText) || (paramGrammarAtom.getAutoGenType() == 3))) {
/* 2705:3236 */       println("text.erase(_saveIndex);");
/* 2706:     */     }
/* 2707:     */   }
/* 2708:     */   
/* 2709:     */   protected void genMatchUsingAtomTokenType(GrammarAtom paramGrammarAtom)
/* 2710:     */   {
/* 2711:3241 */     String str1 = "";
/* 2712:3242 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 2713:3243 */       if (this.usingCustomAST) {
/* 2714:3244 */         str1 = namespaceAntlr + "RefAST" + "(_t),";
/* 2715:     */       } else {
/* 2716:3246 */         str1 = "_t,";
/* 2717:     */       }
/* 2718:     */     }
/* 2719:3250 */     String str2 = str1 + getValueString(paramGrammarAtom.getType());
/* 2720:     */     
/* 2721:     */ 
/* 2722:3253 */     println((paramGrammarAtom.not ? "matchNot(" : "match(") + str2 + ");");
/* 2723:     */   }
/* 2724:     */   
/* 2725:     */   public void genNextToken()
/* 2726:     */   {
/* 2727:3263 */     int i = 0;
/* 2728:3264 */     for (int j = 0; j < this.grammar.rules.size(); j++)
/* 2729:     */     {
/* 2730:3265 */       localRuleSymbol1 = (RuleSymbol)this.grammar.rules.elementAt(j);
/* 2731:3266 */       if ((localRuleSymbol1.isDefined()) && (localRuleSymbol1.access.equals("public")))
/* 2732:     */       {
/* 2733:3267 */         i = 1;
/* 2734:3268 */         break;
/* 2735:     */       }
/* 2736:     */     }
/* 2737:3271 */     if (i == 0)
/* 2738:     */     {
/* 2739:3272 */       println("");
/* 2740:3273 */       println(namespaceAntlr + "RefToken " + this.grammar.getClassName() + "::nextToken() { return " + namespaceAntlr + "RefToken(new " + namespaceAntlr + "CommonToken(" + namespaceAntlr + "Token::EOF_TYPE, \"\")); }");
/* 2741:3274 */       println("");
/* 2742:3275 */       return;
/* 2743:     */     }
/* 2744:3279 */     RuleBlock localRuleBlock = MakeGrammar.createNextTokenRule(this.grammar, this.grammar.rules, "nextToken");
/* 2745:     */     
/* 2746:3281 */     RuleSymbol localRuleSymbol1 = new RuleSymbol("mnextToken");
/* 2747:3282 */     localRuleSymbol1.setDefined();
/* 2748:3283 */     localRuleSymbol1.setBlock(localRuleBlock);
/* 2749:3284 */     localRuleSymbol1.access = "private";
/* 2750:3285 */     this.grammar.define(localRuleSymbol1);
/* 2751:     */     
/* 2752:3287 */     boolean bool = this.grammar.theLLkAnalyzer.deterministic(localRuleBlock);
/* 2753:     */     
/* 2754:     */ 
/* 2755:3290 */     String str1 = null;
/* 2756:3291 */     if (((LexerGrammar)this.grammar).filterMode) {
/* 2757:3292 */       str1 = ((LexerGrammar)this.grammar).filterRule;
/* 2758:     */     }
/* 2759:3295 */     println("");
/* 2760:3296 */     println(namespaceAntlr + "RefToken " + this.grammar.getClassName() + "::nextToken()");
/* 2761:3297 */     println("{");
/* 2762:3298 */     this.tabs += 1;
/* 2763:3299 */     println(namespaceAntlr + "RefToken theRetToken;");
/* 2764:3300 */     println("for (;;) {");
/* 2765:3301 */     this.tabs += 1;
/* 2766:3302 */     println(namespaceAntlr + "RefToken theRetToken;");
/* 2767:3303 */     println("int _ttype = " + namespaceAntlr + "Token::INVALID_TYPE;");
/* 2768:3304 */     if (((LexerGrammar)this.grammar).filterMode)
/* 2769:     */     {
/* 2770:3305 */       println("setCommitToPath(false);");
/* 2771:3306 */       if (str1 != null)
/* 2772:     */       {
/* 2773:3308 */         if (!this.grammar.isDefined(CodeGenerator.encodeLexerRuleName(str1)))
/* 2774:     */         {
/* 2775:3309 */           this.grammar.antlrTool.error("Filter rule " + str1 + " does not exist in this lexer");
/* 2776:     */         }
/* 2777:     */         else
/* 2778:     */         {
/* 2779:3312 */           RuleSymbol localRuleSymbol2 = (RuleSymbol)this.grammar.getSymbol(CodeGenerator.encodeLexerRuleName(str1));
/* 2780:3313 */           if (!localRuleSymbol2.isDefined()) {
/* 2781:3314 */             this.grammar.antlrTool.error("Filter rule " + str1 + " does not exist in this lexer");
/* 2782:3316 */           } else if (localRuleSymbol2.access.equals("public")) {
/* 2783:3317 */             this.grammar.antlrTool.error("Filter rule " + str1 + " must be protected");
/* 2784:     */           }
/* 2785:     */         }
/* 2786:3320 */         println("int _m;");
/* 2787:3321 */         println("_m = mark();");
/* 2788:     */       }
/* 2789:     */     }
/* 2790:3324 */     println("resetText();");
/* 2791:     */     
/* 2792:     */ 
/* 2793:3327 */     println("try {   // for lexical and char stream error handling");
/* 2794:3328 */     this.tabs += 1;
/* 2795:3331 */     for (int k = 0; k < localRuleBlock.getAlternatives().size(); k++)
/* 2796:     */     {
/* 2797:3332 */       localObject = localRuleBlock.getAlternativeAt(k);
/* 2798:3333 */       if (localObject.cache[1].containsEpsilon()) {
/* 2799:3334 */         this.antlrTool.warning("found optional path in nextToken()");
/* 2800:     */       }
/* 2801:     */     }
/* 2802:3339 */     String str2 = System.getProperty("line.separator");
/* 2803:3340 */     Object localObject = genCommonBlock(localRuleBlock, false);
/* 2804:3341 */     String str3 = "if (LA(1)==EOF_CHAR)" + str2 + "\t\t\t\t{" + str2 + "\t\t\t\t\tuponEOF();" + str2 + "\t\t\t\t\t_returnToken = makeToken(" + namespaceAntlr + "Token::EOF_TYPE);" + str2 + "\t\t\t\t}";
/* 2805:     */     
/* 2806:     */ 
/* 2807:     */ 
/* 2808:3345 */     str3 = str3 + str2 + "\t\t\t\t";
/* 2809:3346 */     if (((LexerGrammar)this.grammar).filterMode)
/* 2810:     */     {
/* 2811:3347 */       if (str1 == null) {
/* 2812:3348 */         str3 = str3 + "else {consume(); goto tryAgain;}";
/* 2813:     */       } else {
/* 2814:3351 */         str3 = str3 + "else {" + str2 + "\t\t\t\t\tcommit();" + str2 + "\t\t\t\t\ttry {m" + str1 + "(false);}" + str2 + "\t\t\t\t\tcatch(" + namespaceAntlr + "RecognitionException& e) {" + str2 + "\t\t\t\t\t\t// catastrophic failure" + str2 + "\t\t\t\t\t\treportError(e);" + str2 + "\t\t\t\t\t\tconsume();" + str2 + "\t\t\t\t\t}" + str2 + "\t\t\t\t\tgoto tryAgain;" + str2 + "\t\t\t\t}";
/* 2815:     */       }
/* 2816:     */     }
/* 2817:     */     else {
/* 2818:3364 */       str3 = str3 + "else {" + this.throwNoViable + "}";
/* 2819:     */     }
/* 2820:3366 */     genBlockFinish((CppBlockFinishingInfo)localObject, str3);
/* 2821:3369 */     if ((((LexerGrammar)this.grammar).filterMode) && (str1 != null)) {
/* 2822:3370 */       println("commit();");
/* 2823:     */     }
/* 2824:3376 */     println("if ( !_returnToken )" + str2 + "\t\t\t\tgoto tryAgain; // found SKIP token" + str2);
/* 2825:     */     
/* 2826:3378 */     println("_ttype = _returnToken->getType();");
/* 2827:3379 */     if (((LexerGrammar)this.grammar).getTestLiterals()) {
/* 2828:3380 */       genLiteralsTest();
/* 2829:     */     }
/* 2830:3384 */     println("_returnToken->setType(_ttype);");
/* 2831:3385 */     println("return _returnToken;");
/* 2832:     */     
/* 2833:     */ 
/* 2834:3388 */     this.tabs -= 1;
/* 2835:3389 */     println("}");
/* 2836:3390 */     println("catch (" + namespaceAntlr + "RecognitionException& e) {");
/* 2837:3391 */     this.tabs += 1;
/* 2838:3392 */     if (((LexerGrammar)this.grammar).filterMode) {
/* 2839:3393 */       if (str1 == null)
/* 2840:     */       {
/* 2841:3394 */         println("if ( !getCommitToPath() ) {");
/* 2842:3395 */         this.tabs += 1;
/* 2843:3396 */         println("consume();");
/* 2844:3397 */         println("goto tryAgain;");
/* 2845:3398 */         this.tabs -= 1;
/* 2846:3399 */         println("}");
/* 2847:     */       }
/* 2848:     */       else
/* 2849:     */       {
/* 2850:3402 */         println("if ( !getCommitToPath() ) {");
/* 2851:3403 */         this.tabs += 1;
/* 2852:3404 */         println("rewind(_m);");
/* 2853:3405 */         println("resetText();");
/* 2854:3406 */         println("try {m" + str1 + "(false);}");
/* 2855:3407 */         println("catch(" + namespaceAntlr + "RecognitionException& ee) {");
/* 2856:3408 */         println("\t// horrendous failure: error in filter rule");
/* 2857:3409 */         println("\treportError(ee);");
/* 2858:3410 */         println("\tconsume();");
/* 2859:3411 */         println("}");
/* 2860:     */         
/* 2861:3413 */         this.tabs -= 1;
/* 2862:3414 */         println("}");
/* 2863:3415 */         println("else");
/* 2864:     */       }
/* 2865:     */     }
/* 2866:3418 */     if (localRuleBlock.getDefaultErrorHandler())
/* 2867:     */     {
/* 2868:3419 */       println("{");
/* 2869:3420 */       this.tabs += 1;
/* 2870:3421 */       println("reportError(e);");
/* 2871:3422 */       println("consume();");
/* 2872:3423 */       this.tabs -= 1;
/* 2873:3424 */       println("}");
/* 2874:     */     }
/* 2875:     */     else
/* 2876:     */     {
/* 2877:3428 */       this.tabs += 1;
/* 2878:3429 */       println("throw " + namespaceAntlr + "TokenStreamRecognitionException(e);");
/* 2879:3430 */       this.tabs -= 1;
/* 2880:     */     }
/* 2881:3434 */     this.tabs -= 1;
/* 2882:3435 */     println("}");
/* 2883:3436 */     println("catch (" + namespaceAntlr + "CharStreamIOException& csie) {");
/* 2884:3437 */     println("\tthrow " + namespaceAntlr + "TokenStreamIOException(csie.io);");
/* 2885:3438 */     println("}");
/* 2886:3439 */     println("catch (" + namespaceAntlr + "CharStreamException& cse) {");
/* 2887:3440 */     println("\tthrow " + namespaceAntlr + "TokenStreamException(cse.getMessage());");
/* 2888:3441 */     println("}");
/* 2889:     */     
/* 2890:     */ 
/* 2891:3444 */     _println("tryAgain:;");
/* 2892:3445 */     this.tabs -= 1;
/* 2893:3446 */     println("}");
/* 2894:     */     
/* 2895:     */ 
/* 2896:3449 */     this.tabs -= 1;
/* 2897:3450 */     println("}");
/* 2898:3451 */     println("");
/* 2899:     */   }
/* 2900:     */   
/* 2901:     */   public void genRule(RuleSymbol paramRuleSymbol, boolean paramBoolean, int paramInt, String paramString)
/* 2902:     */   {
/* 2903:3471 */     if ((this.DEBUG_CODE_GENERATOR) || (this.DEBUG_CPP_CODE_GENERATOR)) {
/* 2904:3471 */       System.out.println("genRule(" + paramRuleSymbol.getId() + ")");
/* 2905:     */     }
/* 2906:3472 */     if (!paramRuleSymbol.isDefined())
/* 2907:     */     {
/* 2908:3473 */       this.antlrTool.error("undefined rule: " + paramRuleSymbol.getId());
/* 2909:3474 */       return;
/* 2910:     */     }
/* 2911:3478 */     RuleBlock localRuleBlock = paramRuleSymbol.getBlock();
/* 2912:     */     
/* 2913:3480 */     this.currentRule = localRuleBlock;
/* 2914:3481 */     this.currentASTResult = paramRuleSymbol.getId();
/* 2915:     */     
/* 2916:     */ 
/* 2917:3484 */     this.declaredASTVariables.clear();
/* 2918:     */     
/* 2919:     */ 
/* 2920:3487 */     boolean bool1 = this.genAST;
/* 2921:3488 */     this.genAST = ((this.genAST) && (localRuleBlock.getAutoGen()));
/* 2922:     */     
/* 2923:     */ 
/* 2924:3491 */     this.saveText = localRuleBlock.getAutoGen();
/* 2925:3494 */     if (paramRuleSymbol.comment != null) {
/* 2926:3495 */       _println(paramRuleSymbol.comment);
/* 2927:     */     }
/* 2928:3499 */     if (localRuleBlock.returnAction != null) {
/* 2929:3502 */       _print(extractTypeOfAction(localRuleBlock.returnAction, localRuleBlock.getLine(), localRuleBlock.getColumn()) + " ");
/* 2930:     */     } else {
/* 2931:3505 */       _print("void ");
/* 2932:     */     }
/* 2933:3509 */     _print(paramString + paramRuleSymbol.getId() + "(");
/* 2934:     */     
/* 2935:     */ 
/* 2936:3512 */     _print(this.commonExtraParams);
/* 2937:3513 */     if ((this.commonExtraParams.length() != 0) && (localRuleBlock.argAction != null)) {
/* 2938:3514 */       _print(",");
/* 2939:     */     }
/* 2940:     */     Object localObject2;
/* 2941:     */     Object localObject3;
/* 2942:3518 */     if (localRuleBlock.argAction != null)
/* 2943:     */     {
/* 2944:3521 */       _println("");
/* 2945:     */       
/* 2946:     */ 
/* 2947:3524 */       this.tabs += 1;
/* 2948:     */       
/* 2949:     */ 
/* 2950:     */ 
/* 2951:     */ 
/* 2952:     */ 
/* 2953:     */ 
/* 2954:     */ 
/* 2955:     */ 
/* 2956:     */ 
/* 2957:3534 */       localObject1 = localRuleBlock.argAction;
/* 2958:3535 */       localObject2 = "";
/* 2959:     */       
/* 2960:3537 */       localObject3 = "";
/* 2961:3538 */       int i = ((String)localObject1).indexOf('=');
/* 2962:3539 */       if (i != -1)
/* 2963:     */       {
/* 2964:3541 */         int j = 0;
/* 2965:3542 */         while ((j != -1) && (i != -1))
/* 2966:     */         {
/* 2967:3544 */           localObject2 = (String)localObject2 + (String)localObject3 + ((String)localObject1).substring(0, i).trim();
/* 2968:3545 */           localObject3 = ", ";
/* 2969:3546 */           j = ((String)localObject1).indexOf(',', i);
/* 2970:3547 */           if (j != -1)
/* 2971:     */           {
/* 2972:3550 */             localObject1 = ((String)localObject1).substring(j + 1).trim();
/* 2973:3551 */             i = ((String)localObject1).indexOf('=');
/* 2974:3552 */             if (i == -1) {
/* 2975:3553 */               localObject2 = (String)localObject2 + (String)localObject3 + (String)localObject1;
/* 2976:     */             }
/* 2977:     */           }
/* 2978:     */         }
/* 2979:     */       }
/* 2980:3558 */       localObject2 = localObject1;
/* 2981:     */       
/* 2982:3560 */       println((String)localObject2);
/* 2983:     */       
/* 2984:     */ 
/* 2985:3563 */       this.tabs -= 1;
/* 2986:3564 */       print(") ");
/* 2987:     */     }
/* 2988:     */     else
/* 2989:     */     {
/* 2990:3568 */       _print(") ");
/* 2991:     */     }
/* 2992:3570 */     _println("{");
/* 2993:3571 */     this.tabs += 1;
/* 2994:3573 */     if (this.grammar.traceRules) {
/* 2995:3574 */       if ((this.grammar instanceof TreeWalkerGrammar))
/* 2996:     */       {
/* 2997:3575 */         if (this.usingCustomAST) {
/* 2998:3576 */           println("Tracer traceInOut(this,\"" + paramRuleSymbol.getId() + "\"," + namespaceAntlr + "RefAST" + "(_t));");
/* 2999:     */         } else {
/* 3000:3578 */           println("Tracer traceInOut(this,\"" + paramRuleSymbol.getId() + "\",_t);");
/* 3001:     */         }
/* 3002:     */       }
/* 3003:     */       else {
/* 3004:3581 */         println("Tracer traceInOut(this, \"" + paramRuleSymbol.getId() + "\");");
/* 3005:     */       }
/* 3006:     */     }
/* 3007:3586 */     if (localRuleBlock.returnAction != null)
/* 3008:     */     {
/* 3009:3588 */       genLineNo(localRuleBlock);
/* 3010:3589 */       println(localRuleBlock.returnAction + ";");
/* 3011:3590 */       genLineNo2();
/* 3012:     */     }
/* 3013:3594 */     if (!this.commonLocalVars.equals("")) {
/* 3014:3595 */       println(this.commonLocalVars);
/* 3015:     */     }
/* 3016:3597 */     if ((this.grammar instanceof LexerGrammar))
/* 3017:     */     {
/* 3018:3602 */       if (paramRuleSymbol.getId().equals("mEOF")) {
/* 3019:3603 */         println("_ttype = " + namespaceAntlr + "Token::EOF_TYPE;");
/* 3020:     */       } else {
/* 3021:3605 */         println("_ttype = " + paramRuleSymbol.getId().substring(1) + ";");
/* 3022:     */       }
/* 3023:3606 */       println(namespaceStd + "string::size_type _saveIndex;");
/* 3024:     */     }
/* 3025:3616 */     if (this.grammar.debuggingOutput) {
/* 3026:3617 */       if ((this.grammar instanceof ParserGrammar)) {
/* 3027:3618 */         println("fireEnterRule(" + paramInt + ",0);");
/* 3028:3619 */       } else if ((this.grammar instanceof LexerGrammar)) {
/* 3029:3620 */         println("fireEnterRule(" + paramInt + ",_ttype);");
/* 3030:     */       }
/* 3031:     */     }
/* 3032:3629 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 3033:3632 */       println(this.labeledElementASTType + " " + paramRuleSymbol.getId() + "_AST_in = (_t == " + this.labeledElementASTType + "(ASTNULL)) ? " + this.labeledElementASTInit + " : _t;");
/* 3034:     */     }
/* 3035:3634 */     if (this.grammar.buildAST)
/* 3036:     */     {
/* 3037:3636 */       println("returnAST = " + this.labeledElementASTInit + ";");
/* 3038:     */       
/* 3039:3638 */       println(namespaceAntlr + "ASTPair currentAST;");
/* 3040:     */       
/* 3041:3640 */       println(this.labeledElementASTType + " " + paramRuleSymbol.getId() + "_AST = " + this.labeledElementASTInit + ";");
/* 3042:     */     }
/* 3043:3643 */     genBlockPreamble(localRuleBlock);
/* 3044:3644 */     genBlockInitAction(localRuleBlock);
/* 3045:3645 */     println("");
/* 3046:     */     
/* 3047:     */ 
/* 3048:3648 */     Object localObject1 = localRuleBlock.findExceptionSpec("");
/* 3049:3651 */     if ((localObject1 != null) || (localRuleBlock.getDefaultErrorHandler()))
/* 3050:     */     {
/* 3051:3652 */       println("try {      // for error handling");
/* 3052:3653 */       this.tabs += 1;
/* 3053:     */     }
/* 3054:3657 */     if (localRuleBlock.alternatives.size() == 1)
/* 3055:     */     {
/* 3056:3660 */       localObject2 = localRuleBlock.getAlternativeAt(0);
/* 3057:3661 */       localObject3 = ((Alternative)localObject2).semPred;
/* 3058:3662 */       if (localObject3 != null) {
/* 3059:3663 */         genSemPred((String)localObject3, this.currentRule.line);
/* 3060:     */       }
/* 3061:3664 */       if (((Alternative)localObject2).synPred != null) {
/* 3062:3665 */         this.antlrTool.warning("Syntactic predicate ignored for single alternative", this.grammar.getFilename(), ((Alternative)localObject2).synPred.getLine(), ((Alternative)localObject2).synPred.getColumn());
/* 3063:     */       }
/* 3064:3672 */       genAlt((Alternative)localObject2, localRuleBlock);
/* 3065:     */     }
/* 3066:     */     else
/* 3067:     */     {
/* 3068:3677 */       boolean bool2 = this.grammar.theLLkAnalyzer.deterministic(localRuleBlock);
/* 3069:     */       
/* 3070:3679 */       localObject3 = genCommonBlock(localRuleBlock, false);
/* 3071:3680 */       genBlockFinish((CppBlockFinishingInfo)localObject3, this.throwNoViable);
/* 3072:     */     }
/* 3073:3684 */     if ((localObject1 != null) || (localRuleBlock.getDefaultErrorHandler()))
/* 3074:     */     {
/* 3075:3686 */       this.tabs -= 1;
/* 3076:3687 */       println("}");
/* 3077:     */     }
/* 3078:3691 */     if (localObject1 != null)
/* 3079:     */     {
/* 3080:3693 */       genErrorHandler((ExceptionSpec)localObject1);
/* 3081:     */     }
/* 3082:3695 */     else if (localRuleBlock.getDefaultErrorHandler())
/* 3083:     */     {
/* 3084:3698 */       println("catch (" + this.exceptionThrown + "& ex) {");
/* 3085:3699 */       this.tabs += 1;
/* 3086:3701 */       if (this.grammar.hasSyntacticPredicate)
/* 3087:     */       {
/* 3088:3702 */         println("if( inputState->guessing == 0 ) {");
/* 3089:3703 */         this.tabs += 1;
/* 3090:     */       }
/* 3091:3705 */       println("reportError(ex);");
/* 3092:3706 */       if (!(this.grammar instanceof TreeWalkerGrammar))
/* 3093:     */       {
/* 3094:3709 */         Lookahead localLookahead = this.grammar.theLLkAnalyzer.FOLLOW(1, localRuleBlock.endNode);
/* 3095:3710 */         localObject3 = getBitsetName(markBitsetForGen(localLookahead.fset));
/* 3096:3711 */         println("recover(ex," + (String)localObject3 + ");");
/* 3097:     */       }
/* 3098:     */       else
/* 3099:     */       {
/* 3100:3716 */         println("if ( _t != " + this.labeledElementASTInit + " )");
/* 3101:3717 */         this.tabs += 1;
/* 3102:3718 */         println("_t = _t->getNextSibling();");
/* 3103:3719 */         this.tabs -= 1;
/* 3104:     */       }
/* 3105:3721 */       if (this.grammar.hasSyntacticPredicate)
/* 3106:     */       {
/* 3107:3723 */         this.tabs -= 1;
/* 3108:     */         
/* 3109:3725 */         println("} else {");
/* 3110:3726 */         this.tabs += 1;
/* 3111:3727 */         println("throw;");
/* 3112:3728 */         this.tabs -= 1;
/* 3113:3729 */         println("}");
/* 3114:     */       }
/* 3115:3732 */       this.tabs -= 1;
/* 3116:3733 */       println("}");
/* 3117:     */     }
/* 3118:3737 */     if (this.grammar.buildAST) {
/* 3119:3738 */       println("returnAST = " + paramRuleSymbol.getId() + "_AST;");
/* 3120:     */     }
/* 3121:3742 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 3122:3743 */       println("_retTree = _t;");
/* 3123:     */     }
/* 3124:3747 */     if (localRuleBlock.getTestLiterals()) {
/* 3125:3748 */       if (paramRuleSymbol.access.equals("protected")) {
/* 3126:3749 */         genLiteralsTestForPartialToken();
/* 3127:     */       } else {
/* 3128:3752 */         genLiteralsTest();
/* 3129:     */       }
/* 3130:     */     }
/* 3131:3757 */     if ((this.grammar instanceof LexerGrammar))
/* 3132:     */     {
/* 3133:3758 */       println("if ( _createToken && _token==" + namespaceAntlr + "nullToken && _ttype!=" + namespaceAntlr + "Token::SKIP ) {");
/* 3134:3759 */       println("   _token = makeToken(_ttype);");
/* 3135:3760 */       println("   _token->setText(text.substr(_begin, text.length()-_begin));");
/* 3136:3761 */       println("}");
/* 3137:3762 */       println("_returnToken = _token;");
/* 3138:     */       
/* 3139:     */ 
/* 3140:3765 */       println("_saveIndex=0;");
/* 3141:     */     }
/* 3142:3769 */     if (localRuleBlock.returnAction != null) {
/* 3143:3770 */       println("return " + extractIdOfAction(localRuleBlock.returnAction, localRuleBlock.getLine(), localRuleBlock.getColumn()) + ";");
/* 3144:     */     }
/* 3145:3798 */     this.tabs -= 1;
/* 3146:3799 */     println("}");
/* 3147:3800 */     println("");
/* 3148:     */     
/* 3149:     */ 
/* 3150:3803 */     this.genAST = bool1;
/* 3151:     */   }
/* 3152:     */   
/* 3153:     */   public void genRuleHeader(RuleSymbol paramRuleSymbol, boolean paramBoolean)
/* 3154:     */   {
/* 3155:3809 */     this.tabs = 1;
/* 3156:3810 */     if ((this.DEBUG_CODE_GENERATOR) || (this.DEBUG_CPP_CODE_GENERATOR)) {
/* 3157:3810 */       System.out.println("genRuleHeader(" + paramRuleSymbol.getId() + ")");
/* 3158:     */     }
/* 3159:3811 */     if (!paramRuleSymbol.isDefined())
/* 3160:     */     {
/* 3161:3812 */       this.antlrTool.error("undefined rule: " + paramRuleSymbol.getId());
/* 3162:3813 */       return;
/* 3163:     */     }
/* 3164:3817 */     RuleBlock localRuleBlock = paramRuleSymbol.getBlock();
/* 3165:3818 */     this.currentRule = localRuleBlock;
/* 3166:3819 */     this.currentASTResult = paramRuleSymbol.getId();
/* 3167:     */     
/* 3168:     */ 
/* 3169:3822 */     boolean bool = this.genAST;
/* 3170:3823 */     this.genAST = ((this.genAST) && (localRuleBlock.getAutoGen()));
/* 3171:     */     
/* 3172:     */ 
/* 3173:3826 */     this.saveText = localRuleBlock.getAutoGen();
/* 3174:     */     
/* 3175:     */ 
/* 3176:3829 */     print(paramRuleSymbol.access + ": ");
/* 3177:3832 */     if (localRuleBlock.returnAction != null) {
/* 3178:3835 */       _print(extractTypeOfAction(localRuleBlock.returnAction, localRuleBlock.getLine(), localRuleBlock.getColumn()) + " ");
/* 3179:     */     } else {
/* 3180:3838 */       _print("void ");
/* 3181:     */     }
/* 3182:3842 */     _print(paramRuleSymbol.getId() + "(");
/* 3183:     */     
/* 3184:     */ 
/* 3185:3845 */     _print(this.commonExtraParams);
/* 3186:3846 */     if ((this.commonExtraParams.length() != 0) && (localRuleBlock.argAction != null)) {
/* 3187:3847 */       _print(",");
/* 3188:     */     }
/* 3189:3851 */     if (localRuleBlock.argAction != null)
/* 3190:     */     {
/* 3191:3854 */       _println("");
/* 3192:3855 */       this.tabs += 1;
/* 3193:3856 */       println(localRuleBlock.argAction);
/* 3194:3857 */       this.tabs -= 1;
/* 3195:3858 */       print(")");
/* 3196:     */     }
/* 3197:     */     else
/* 3198:     */     {
/* 3199:3861 */       _print(")");
/* 3200:     */     }
/* 3201:3863 */     _println(";");
/* 3202:     */     
/* 3203:3865 */     this.tabs -= 1;
/* 3204:     */     
/* 3205:     */ 
/* 3206:3868 */     this.genAST = bool;
/* 3207:     */   }
/* 3208:     */   
/* 3209:     */   private void GenRuleInvocation(RuleRefElement paramRuleRefElement)
/* 3210:     */   {
/* 3211:3875 */     _print(paramRuleRefElement.targetRule + "(");
/* 3212:3878 */     if ((this.grammar instanceof LexerGrammar))
/* 3213:     */     {
/* 3214:3880 */       if (paramRuleRefElement.getLabel() != null) {
/* 3215:3881 */         _print("true");
/* 3216:     */       } else {
/* 3217:3884 */         _print("false");
/* 3218:     */       }
/* 3219:3886 */       if ((this.commonExtraArgs.length() != 0) || (paramRuleRefElement.args != null)) {
/* 3220:3887 */         _print(",");
/* 3221:     */       }
/* 3222:     */     }
/* 3223:3892 */     _print(this.commonExtraArgs);
/* 3224:3893 */     if ((this.commonExtraArgs.length() != 0) && (paramRuleRefElement.args != null)) {
/* 3225:3894 */       _print(",");
/* 3226:     */     }
/* 3227:3898 */     RuleSymbol localRuleSymbol = (RuleSymbol)this.grammar.getSymbol(paramRuleRefElement.targetRule);
/* 3228:3899 */     if (paramRuleRefElement.args != null)
/* 3229:     */     {
/* 3230:3902 */       ActionTransInfo localActionTransInfo = new ActionTransInfo();
/* 3231:     */       
/* 3232:     */ 
/* 3233:3905 */       String str = processActionForSpecialSymbols(paramRuleRefElement.args, paramRuleRefElement.line, this.currentRule, localActionTransInfo);
/* 3234:3907 */       if ((localActionTransInfo.assignToRoot) || (localActionTransInfo.refRuleRoot != null)) {
/* 3235:3909 */         this.antlrTool.error("Arguments of rule reference '" + paramRuleRefElement.targetRule + "' cannot set or ref #" + this.currentRule.getRuleName() + " on line " + paramRuleRefElement.getLine());
/* 3236:     */       }
/* 3237:3912 */       _print(str);
/* 3238:3915 */       if (localRuleSymbol.block.argAction == null) {
/* 3239:3917 */         this.antlrTool.warning("Rule '" + paramRuleRefElement.targetRule + "' accepts no arguments", this.grammar.getFilename(), paramRuleRefElement.getLine(), paramRuleRefElement.getColumn());
/* 3240:     */       }
/* 3241:     */     }
/* 3242:3930 */     _println(");");
/* 3243:3933 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 3244:3934 */       println("_t = _retTree;");
/* 3245:     */     }
/* 3246:     */   }
/* 3247:     */   
/* 3248:     */   protected void genSemPred(String paramString, int paramInt)
/* 3249:     */   {
/* 3250:3939 */     ActionTransInfo localActionTransInfo = new ActionTransInfo();
/* 3251:3940 */     paramString = processActionForSpecialSymbols(paramString, paramInt, this.currentRule, localActionTransInfo);
/* 3252:     */     
/* 3253:3942 */     String str = this.charFormatter.escapeString(paramString);
/* 3254:3946 */     if ((this.grammar.debuggingOutput) && (((this.grammar instanceof ParserGrammar)) || ((this.grammar instanceof LexerGrammar)))) {
/* 3255:3948 */       paramString = "fireSemanticPredicateEvaluated(antlr.debug.SemanticPredicateEvent.VALIDATING," + addSemPred(str) + "," + paramString + ")";
/* 3256:     */     }
/* 3257:3950 */     println("if (!(" + paramString + "))");
/* 3258:3951 */     this.tabs += 1;
/* 3259:3952 */     println("throw " + namespaceAntlr + "SemanticException(\"" + str + "\");");
/* 3260:3953 */     this.tabs -= 1;
/* 3261:     */   }
/* 3262:     */   
/* 3263:     */   protected void genSemPredMap(String paramString)
/* 3264:     */   {
/* 3265:3959 */     Enumeration localEnumeration = this.semPreds.elements();
/* 3266:3960 */     println("const char* " + paramString + "_semPredNames[] = {");
/* 3267:3961 */     this.tabs += 1;
/* 3268:3962 */     while (localEnumeration.hasMoreElements()) {
/* 3269:3963 */       println("\"" + localEnumeration.nextElement() + "\",");
/* 3270:     */     }
/* 3271:3964 */     println("0");
/* 3272:3965 */     this.tabs -= 1;
/* 3273:3966 */     println("};");
/* 3274:     */   }
/* 3275:     */   
/* 3276:     */   protected void genSynPred(SynPredBlock paramSynPredBlock, String paramString)
/* 3277:     */   {
/* 3278:3969 */     if ((this.DEBUG_CODE_GENERATOR) || (this.DEBUG_CPP_CODE_GENERATOR)) {
/* 3279:3969 */       System.out.println("gen=>(" + paramSynPredBlock + ")");
/* 3280:     */     }
/* 3281:3972 */     println("bool synPredMatched" + paramSynPredBlock.ID + " = false;");
/* 3282:3974 */     if ((this.grammar instanceof TreeWalkerGrammar))
/* 3283:     */     {
/* 3284:3975 */       println("if (_t == " + this.labeledElementASTInit + " )");
/* 3285:3976 */       this.tabs += 1;
/* 3286:3977 */       println("_t = ASTNULL;");
/* 3287:3978 */       this.tabs -= 1;
/* 3288:     */     }
/* 3289:3982 */     println("if (" + paramString + ") {");
/* 3290:3983 */     this.tabs += 1;
/* 3291:3986 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 3292:3987 */       println(this.labeledElementType + " __t" + paramSynPredBlock.ID + " = _t;");
/* 3293:     */     } else {
/* 3294:3990 */       println("int _m" + paramSynPredBlock.ID + " = mark();");
/* 3295:     */     }
/* 3296:3994 */     println("synPredMatched" + paramSynPredBlock.ID + " = true;");
/* 3297:3995 */     println("inputState->guessing++;");
/* 3298:3998 */     if ((this.grammar.debuggingOutput) && (((this.grammar instanceof ParserGrammar)) || ((this.grammar instanceof LexerGrammar)))) {
/* 3299:4000 */       println("fireSyntacticPredicateStarted();");
/* 3300:     */     }
/* 3301:4003 */     this.syntacticPredLevel += 1;
/* 3302:4004 */     println("try {");
/* 3303:4005 */     this.tabs += 1;
/* 3304:4006 */     gen(paramSynPredBlock);
/* 3305:4007 */     this.tabs -= 1;
/* 3306:     */     
/* 3307:4009 */     println("}");
/* 3308:4010 */     println("catch (" + this.exceptionThrown + "& pe) {");
/* 3309:4011 */     this.tabs += 1;
/* 3310:4012 */     println("synPredMatched" + paramSynPredBlock.ID + " = false;");
/* 3311:     */     
/* 3312:4014 */     this.tabs -= 1;
/* 3313:4015 */     println("}");
/* 3314:4018 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 3315:4019 */       println("_t = __t" + paramSynPredBlock.ID + ";");
/* 3316:     */     } else {
/* 3317:4022 */       println("rewind(_m" + paramSynPredBlock.ID + ");");
/* 3318:     */     }
/* 3319:4025 */     println("inputState->guessing--;");
/* 3320:4028 */     if ((this.grammar.debuggingOutput) && (((this.grammar instanceof ParserGrammar)) || ((this.grammar instanceof LexerGrammar))))
/* 3321:     */     {
/* 3322:4030 */       println("if (synPredMatched" + paramSynPredBlock.ID + ")");
/* 3323:4031 */       println("  fireSyntacticPredicateSucceeded();");
/* 3324:4032 */       println("else");
/* 3325:4033 */       println("  fireSyntacticPredicateFailed();");
/* 3326:     */     }
/* 3327:4036 */     this.syntacticPredLevel -= 1;
/* 3328:4037 */     this.tabs -= 1;
/* 3329:     */     
/* 3330:     */ 
/* 3331:4040 */     println("}");
/* 3332:     */     
/* 3333:     */ 
/* 3334:4043 */     println("if ( synPredMatched" + paramSynPredBlock.ID + " ) {");
/* 3335:     */   }
/* 3336:     */   
/* 3337:     */   public void genTokenStrings(String paramString)
/* 3338:     */   {
/* 3339:4057 */     println("const char* " + paramString + "tokenNames[] = {");
/* 3340:4058 */     this.tabs += 1;
/* 3341:     */     
/* 3342:     */ 
/* 3343:     */ 
/* 3344:4062 */     Vector localVector = this.grammar.tokenManager.getVocabulary();
/* 3345:4063 */     for (int i = 0; i < localVector.size(); i++)
/* 3346:     */     {
/* 3347:4065 */       String str = (String)localVector.elementAt(i);
/* 3348:4066 */       if (str == null) {
/* 3349:4068 */         str = "<" + String.valueOf(i) + ">";
/* 3350:     */       }
/* 3351:4070 */       if ((!str.startsWith("\"")) && (!str.startsWith("<")))
/* 3352:     */       {
/* 3353:4071 */         TokenSymbol localTokenSymbol = this.grammar.tokenManager.getTokenSymbol(str);
/* 3354:4072 */         if ((localTokenSymbol != null) && (localTokenSymbol.getParaphrase() != null)) {
/* 3355:4073 */           str = StringUtils.stripFrontBack(localTokenSymbol.getParaphrase(), "\"", "\"");
/* 3356:     */         }
/* 3357:     */       }
/* 3358:4076 */       print(this.charFormatter.literalString(str));
/* 3359:4077 */       _println(",");
/* 3360:     */     }
/* 3361:4079 */     println("0");
/* 3362:     */     
/* 3363:     */ 
/* 3364:4082 */     this.tabs -= 1;
/* 3365:4083 */     println("};");
/* 3366:     */   }
/* 3367:     */   
/* 3368:     */   protected void genTokenTypes(TokenManager paramTokenManager)
/* 3369:     */     throws IOException
/* 3370:     */   {
/* 3371:4088 */     this.outputFile = (paramTokenManager.getName() + TokenTypesFileSuffix + ".hpp");
/* 3372:4089 */     this.outputLine = 1;
/* 3373:4090 */     this.currentOutput = this.antlrTool.openOutputFile(this.outputFile);
/* 3374:     */     
/* 3375:     */ 
/* 3376:4093 */     this.tabs = 0;
/* 3377:     */     
/* 3378:     */ 
/* 3379:4096 */     println("#ifndef INC_" + paramTokenManager.getName() + TokenTypesFileSuffix + "_hpp_");
/* 3380:4097 */     println("#define INC_" + paramTokenManager.getName() + TokenTypesFileSuffix + "_hpp_");
/* 3381:4098 */     println("");
/* 3382:4100 */     if (nameSpace != null) {
/* 3383:4101 */       nameSpace.emitDeclarations(this.currentOutput);
/* 3384:     */     }
/* 3385:4104 */     genHeader(this.outputFile);
/* 3386:     */     
/* 3387:     */ 
/* 3388:     */ 
/* 3389:4108 */     println("");
/* 3390:4109 */     println("#ifndef CUSTOM_API");
/* 3391:4110 */     println("# define CUSTOM_API");
/* 3392:4111 */     println("#endif");
/* 3393:4112 */     println("");
/* 3394:     */     
/* 3395:     */ 
/* 3396:4115 */     println("#ifdef __cplusplus");
/* 3397:4116 */     println("struct CUSTOM_API " + paramTokenManager.getName() + TokenTypesFileSuffix + " {");
/* 3398:4117 */     println("#endif");
/* 3399:4118 */     this.tabs += 1;
/* 3400:4119 */     println("enum {");
/* 3401:4120 */     this.tabs += 1;
/* 3402:     */     
/* 3403:     */ 
/* 3404:4123 */     Vector localVector = paramTokenManager.getVocabulary();
/* 3405:     */     
/* 3406:     */ 
/* 3407:4126 */     println("EOF_ = 1,");
/* 3408:4131 */     for (int i = 4; i < localVector.size(); i++)
/* 3409:     */     {
/* 3410:4132 */       String str1 = (String)localVector.elementAt(i);
/* 3411:4133 */       if (str1 != null) {
/* 3412:4134 */         if (str1.startsWith("\""))
/* 3413:     */         {
/* 3414:4136 */           StringLiteralSymbol localStringLiteralSymbol = (StringLiteralSymbol)paramTokenManager.getTokenSymbol(str1);
/* 3415:4137 */           if (localStringLiteralSymbol == null)
/* 3416:     */           {
/* 3417:4138 */             this.antlrTool.panic("String literal " + str1 + " not in symbol table");
/* 3418:     */           }
/* 3419:4140 */           else if (localStringLiteralSymbol.label != null)
/* 3420:     */           {
/* 3421:4141 */             println(localStringLiteralSymbol.label + " = " + i + ",");
/* 3422:     */           }
/* 3423:     */           else
/* 3424:     */           {
/* 3425:4144 */             String str2 = mangleLiteral(str1);
/* 3426:4145 */             if (str2 != null)
/* 3427:     */             {
/* 3428:4147 */               println(str2 + " = " + i + ",");
/* 3429:     */               
/* 3430:4149 */               localStringLiteralSymbol.label = str2;
/* 3431:     */             }
/* 3432:     */             else
/* 3433:     */             {
/* 3434:4152 */               println("// " + str1 + " = " + i);
/* 3435:     */             }
/* 3436:     */           }
/* 3437:     */         }
/* 3438:4156 */         else if (!str1.startsWith("<"))
/* 3439:     */         {
/* 3440:4157 */           println(str1 + " = " + i + ",");
/* 3441:     */         }
/* 3442:     */       }
/* 3443:     */     }
/* 3444:4163 */     println("NULL_TREE_LOOKAHEAD = 3");
/* 3445:     */     
/* 3446:     */ 
/* 3447:4166 */     this.tabs -= 1;
/* 3448:4167 */     println("};");
/* 3449:     */     
/* 3450:     */ 
/* 3451:4170 */     this.tabs -= 1;
/* 3452:4171 */     println("#ifdef __cplusplus");
/* 3453:4172 */     println("};");
/* 3454:4173 */     println("#endif");
/* 3455:4175 */     if (nameSpace != null) {
/* 3456:4176 */       nameSpace.emitClosures(this.currentOutput);
/* 3457:     */     }
/* 3458:4179 */     println("#endif /*INC_" + paramTokenManager.getName() + TokenTypesFileSuffix + "_hpp_*/");
/* 3459:     */     
/* 3460:     */ 
/* 3461:4182 */     this.currentOutput.close();
/* 3462:4183 */     this.currentOutput = null;
/* 3463:4184 */     exitIfError();
/* 3464:     */   }
/* 3465:     */   
/* 3466:     */   public String processStringForASTConstructor(String paramString)
/* 3467:     */   {
/* 3468:4194 */     if ((this.usingCustomAST) && (((this.grammar instanceof TreeWalkerGrammar)) || ((this.grammar instanceof ParserGrammar))) && (!this.grammar.tokenManager.tokenDefined(paramString))) {
/* 3469:4200 */       return namespaceAntlr + "RefAST(" + paramString + ")";
/* 3470:     */     }
/* 3471:4205 */     return paramString;
/* 3472:     */   }
/* 3473:     */   
/* 3474:     */   public String getASTCreateString(Vector paramVector)
/* 3475:     */   {
/* 3476:4213 */     if (paramVector.size() == 0) {
/* 3477:4214 */       return "";
/* 3478:     */     }
/* 3479:4216 */     StringBuffer localStringBuffer = new StringBuffer();
/* 3480:     */     
/* 3481:     */ 
/* 3482:4219 */     localStringBuffer.append(this.labeledElementASTType + "(astFactory->make((new " + namespaceAntlr + "ASTArray(" + paramVector.size() + "))");
/* 3483:4222 */     for (int i = 0; i < paramVector.size(); i++) {
/* 3484:4223 */       localStringBuffer.append("->add(" + paramVector.elementAt(i) + ")");
/* 3485:     */     }
/* 3486:4225 */     localStringBuffer.append("))");
/* 3487:4226 */     return localStringBuffer.toString();
/* 3488:     */   }
/* 3489:     */   
/* 3490:     */   public String getASTCreateString(GrammarAtom paramGrammarAtom, String paramString)
/* 3491:     */   {
/* 3492:4232 */     if ((paramGrammarAtom != null) && (paramGrammarAtom.getASTNodeType() != null))
/* 3493:     */     {
/* 3494:4238 */       this.astTypes.ensureCapacity(paramGrammarAtom.getType());
/* 3495:4239 */       String str = (String)this.astTypes.elementAt(paramGrammarAtom.getType());
/* 3496:4240 */       if (str == null)
/* 3497:     */       {
/* 3498:4241 */         this.astTypes.setElementAt(paramGrammarAtom.getASTNodeType(), paramGrammarAtom.getType());
/* 3499:     */       }
/* 3500:4245 */       else if (!paramGrammarAtom.getASTNodeType().equals(str))
/* 3501:     */       {
/* 3502:4247 */         this.antlrTool.warning("Attempt to redefine AST type for " + paramGrammarAtom.getText(), this.grammar.getFilename(), paramGrammarAtom.getLine(), paramGrammarAtom.getColumn());
/* 3503:4248 */         this.antlrTool.warning(" from \"" + str + "\" to \"" + paramGrammarAtom.getASTNodeType() + "\" sticking to \"" + str + "\"", this.grammar.getFilename(), paramGrammarAtom.getLine(), paramGrammarAtom.getColumn());
/* 3504:     */       }
/* 3505:     */       else
/* 3506:     */       {
/* 3507:4251 */         this.astTypes.setElementAt(paramGrammarAtom.getASTNodeType(), paramGrammarAtom.getType());
/* 3508:     */       }
/* 3509:4254 */       return "astFactory->create(" + paramString + ")";
/* 3510:     */     }
/* 3511:4262 */     boolean bool = false;
/* 3512:4263 */     if (paramString.indexOf(',') != -1) {
/* 3513:4264 */       bool = this.grammar.tokenManager.tokenDefined(paramString.substring(0, paramString.indexOf(',')));
/* 3514:     */     }
/* 3515:4267 */     if ((this.usingCustomAST) && ((this.grammar instanceof TreeWalkerGrammar)) && (!this.grammar.tokenManager.tokenDefined(paramString)) && (!bool)) {
/* 3516:4271 */       return "astFactory->create(" + namespaceAntlr + "RefAST(" + paramString + "))";
/* 3517:     */     }
/* 3518:4273 */     return "astFactory->create(" + paramString + ")";
/* 3519:     */   }
/* 3520:     */   
/* 3521:     */   public String getASTCreateString(String paramString)
/* 3522:     */   {
/* 3523:4282 */     if (this.usingCustomAST) {
/* 3524:4283 */       return this.labeledElementASTType + "(astFactory->create(" + namespaceAntlr + "RefAST(" + paramString + ")))";
/* 3525:     */     }
/* 3526:4285 */     return "astFactory->create(" + paramString + ")";
/* 3527:     */   }
/* 3528:     */   
/* 3529:     */   protected String getLookaheadTestExpression(Lookahead[] paramArrayOfLookahead, int paramInt)
/* 3530:     */   {
/* 3531:4289 */     StringBuffer localStringBuffer = new StringBuffer(100);
/* 3532:4290 */     int i = 1;
/* 3533:     */     
/* 3534:4292 */     localStringBuffer.append("(");
/* 3535:4293 */     for (int j = 1; j <= paramInt; j++)
/* 3536:     */     {
/* 3537:4294 */       BitSet localBitSet = paramArrayOfLookahead[j].fset;
/* 3538:4295 */       if (i == 0) {
/* 3539:4296 */         localStringBuffer.append(") && (");
/* 3540:     */       }
/* 3541:4298 */       i = 0;
/* 3542:4303 */       if (paramArrayOfLookahead[j].containsEpsilon()) {
/* 3543:4304 */         localStringBuffer.append("true");
/* 3544:     */       } else {
/* 3545:4306 */         localStringBuffer.append(getLookaheadTestTerm(j, localBitSet));
/* 3546:     */       }
/* 3547:     */     }
/* 3548:4309 */     localStringBuffer.append(")");
/* 3549:     */     
/* 3550:4311 */     return localStringBuffer.toString();
/* 3551:     */   }
/* 3552:     */   
/* 3553:     */   protected String getLookaheadTestExpression(Alternative paramAlternative, int paramInt)
/* 3554:     */   {
/* 3555:4318 */     int i = paramAlternative.lookaheadDepth;
/* 3556:4319 */     if (i == 2147483647) {
/* 3557:4322 */       i = this.grammar.maxk;
/* 3558:     */     }
/* 3559:4325 */     if (paramInt == 0) {
/* 3560:4328 */       return "true";
/* 3561:     */     }
/* 3562:4354 */     return "(" + getLookaheadTestExpression(paramAlternative.cache, i) + ")";
/* 3563:     */   }
/* 3564:     */   
/* 3565:     */   protected String getLookaheadTestTerm(int paramInt, BitSet paramBitSet)
/* 3566:     */   {
/* 3567:4366 */     String str1 = lookaheadString(paramInt);
/* 3568:     */     
/* 3569:     */ 
/* 3570:4369 */     int[] arrayOfInt = paramBitSet.toArray();
/* 3571:4370 */     if (elementsAreRange(arrayOfInt)) {
/* 3572:4371 */       return getRangeExpression(paramInt, arrayOfInt);
/* 3573:     */     }
/* 3574:4376 */     int i = paramBitSet.degree();
/* 3575:4377 */     if (i == 0) {
/* 3576:4378 */       return "true";
/* 3577:     */     }
/* 3578:4381 */     if (i >= this.bitsetTestThreshold)
/* 3579:     */     {
/* 3580:4382 */       j = markBitsetForGen(paramBitSet);
/* 3581:4383 */       return getBitsetName(j) + ".member(" + str1 + ")";
/* 3582:     */     }
/* 3583:4387 */     StringBuffer localStringBuffer = new StringBuffer();
/* 3584:4388 */     for (int j = 0; j < arrayOfInt.length; j++)
/* 3585:     */     {
/* 3586:4390 */       String str2 = getValueString(arrayOfInt[j]);
/* 3587:4393 */       if (j > 0) {
/* 3588:4393 */         localStringBuffer.append(" || ");
/* 3589:     */       }
/* 3590:4394 */       localStringBuffer.append(str1);
/* 3591:4395 */       localStringBuffer.append(" == ");
/* 3592:4396 */       localStringBuffer.append(str2);
/* 3593:     */     }
/* 3594:4398 */     return localStringBuffer.toString();
/* 3595:     */   }
/* 3596:     */   
/* 3597:     */   public String getRangeExpression(int paramInt, int[] paramArrayOfInt)
/* 3598:     */   {
/* 3599:4406 */     if (!elementsAreRange(paramArrayOfInt)) {
/* 3600:4407 */       this.antlrTool.panic("getRangeExpression called with non-range");
/* 3601:     */     }
/* 3602:4409 */     int i = paramArrayOfInt[0];
/* 3603:4410 */     int j = paramArrayOfInt[(paramArrayOfInt.length - 1)];
/* 3604:4411 */     return "(" + lookaheadString(paramInt) + " >= " + getValueString(i) + " && " + lookaheadString(paramInt) + " <= " + getValueString(j) + ")";
/* 3605:     */   }
/* 3606:     */   
/* 3607:     */   private String getValueString(int paramInt)
/* 3608:     */   {
/* 3609:     */     Object localObject;
/* 3610:4420 */     if ((this.grammar instanceof LexerGrammar))
/* 3611:     */     {
/* 3612:4421 */       localObject = this.charFormatter.literalChar(paramInt);
/* 3613:     */     }
/* 3614:     */     else
/* 3615:     */     {
/* 3616:4425 */       TokenSymbol localTokenSymbol = this.grammar.tokenManager.getTokenSymbolAt(paramInt);
/* 3617:4426 */       if (localTokenSymbol == null) {
/* 3618:4427 */         return "" + paramInt;
/* 3619:     */       }
/* 3620:4430 */       String str1 = localTokenSymbol.getId();
/* 3621:4431 */       if ((localTokenSymbol instanceof StringLiteralSymbol))
/* 3622:     */       {
/* 3623:4435 */         StringLiteralSymbol localStringLiteralSymbol = (StringLiteralSymbol)localTokenSymbol;
/* 3624:4436 */         String str2 = localStringLiteralSymbol.getLabel();
/* 3625:4437 */         if (str2 != null)
/* 3626:     */         {
/* 3627:4438 */           localObject = str2;
/* 3628:     */         }
/* 3629:     */         else
/* 3630:     */         {
/* 3631:4441 */           localObject = mangleLiteral(str1);
/* 3632:4442 */           if (localObject == null) {
/* 3633:4443 */             localObject = String.valueOf(paramInt);
/* 3634:     */           }
/* 3635:     */         }
/* 3636:     */       }
/* 3637:4448 */       else if (str1.equals("EOF"))
/* 3638:     */       {
/* 3639:4449 */         localObject = namespaceAntlr + "Token::EOF_TYPE";
/* 3640:     */       }
/* 3641:     */       else
/* 3642:     */       {
/* 3643:4451 */         localObject = str1;
/* 3644:     */       }
/* 3645:     */     }
/* 3646:4454 */     return localObject;
/* 3647:     */   }
/* 3648:     */   
/* 3649:     */   protected boolean lookaheadIsEmpty(Alternative paramAlternative, int paramInt)
/* 3650:     */   {
/* 3651:4458 */     int i = paramAlternative.lookaheadDepth;
/* 3652:4459 */     if (i == 2147483647) {
/* 3653:4460 */       i = this.grammar.maxk;
/* 3654:     */     }
/* 3655:4462 */     for (int j = 1; (j <= i) && (j <= paramInt); j++)
/* 3656:     */     {
/* 3657:4463 */       BitSet localBitSet = paramAlternative.cache[j].fset;
/* 3658:4464 */       if (localBitSet.degree() != 0) {
/* 3659:4465 */         return false;
/* 3660:     */       }
/* 3661:     */     }
/* 3662:4468 */     return true;
/* 3663:     */   }
/* 3664:     */   
/* 3665:     */   private String lookaheadString(int paramInt)
/* 3666:     */   {
/* 3667:4471 */     if ((this.grammar instanceof TreeWalkerGrammar)) {
/* 3668:4472 */       return "_t->getType()";
/* 3669:     */     }
/* 3670:4474 */     return "LA(" + paramInt + ")";
/* 3671:     */   }
/* 3672:     */   
/* 3673:     */   private String mangleLiteral(String paramString)
/* 3674:     */   {
/* 3675:4483 */     String str = this.antlrTool.literalsPrefix;
/* 3676:4484 */     for (int i = 1; i < paramString.length() - 1; i++)
/* 3677:     */     {
/* 3678:4485 */       if ((!Character.isLetter(paramString.charAt(i))) && (paramString.charAt(i) != '_')) {
/* 3679:4487 */         return null;
/* 3680:     */       }
/* 3681:4489 */       str = str + paramString.charAt(i);
/* 3682:     */     }
/* 3683:4491 */     if (this.antlrTool.upperCaseMangledLiterals) {
/* 3684:4492 */       str = str.toUpperCase();
/* 3685:     */     }
/* 3686:4494 */     return str;
/* 3687:     */   }
/* 3688:     */   
/* 3689:     */   public String mapTreeId(String paramString, ActionTransInfo paramActionTransInfo)
/* 3690:     */   {
/* 3691:4504 */     if (this.currentRule == null) {
/* 3692:4504 */       return paramString;
/* 3693:     */     }
/* 3694:4507 */     int i = 0;
/* 3695:4508 */     String str1 = paramString;
/* 3696:4509 */     if ((this.grammar instanceof TreeWalkerGrammar))
/* 3697:     */     {
/* 3698:4514 */       if (!this.grammar.buildAST) {
/* 3699:4516 */         i = 1;
/* 3700:     */       }
/* 3701:4521 */       if ((str1.length() > 3) && (str1.lastIndexOf("_in") == str1.length() - 3))
/* 3702:     */       {
/* 3703:4524 */         str1 = str1.substring(0, str1.length() - 3);
/* 3704:4525 */         i = 1;
/* 3705:     */       }
/* 3706:     */     }
/* 3707:     */     Object localObject;
/* 3708:4533 */     for (int j = 0; j < this.currentRule.labeledElements.size(); j++)
/* 3709:     */     {
/* 3710:4535 */       localObject = (AlternativeElement)this.currentRule.labeledElements.elementAt(j);
/* 3711:4536 */       if (((AlternativeElement)localObject).getLabel().equals(str1)) {
/* 3712:4540 */         return str1 + "_AST";
/* 3713:     */       }
/* 3714:     */     }
/* 3715:4547 */     String str2 = (String)this.treeVariableMap.get(str1);
/* 3716:4548 */     if (str2 != null)
/* 3717:     */     {
/* 3718:4550 */       if (str2 == NONUNIQUE)
/* 3719:     */       {
/* 3720:4555 */         this.antlrTool.error("Ambiguous reference to AST element " + str1 + " in rule " + this.currentRule.getRuleName());
/* 3721:     */         
/* 3722:4557 */         return null;
/* 3723:     */       }
/* 3724:4559 */       if (str2.equals(this.currentRule.getRuleName()))
/* 3725:     */       {
/* 3726:4565 */         this.antlrTool.error("Ambiguous reference to AST element " + str1 + " in rule " + this.currentRule.getRuleName());
/* 3727:     */         
/* 3728:4567 */         return null;
/* 3729:     */       }
/* 3730:4573 */       return i != 0 ? str2 + "_in" : str2;
/* 3731:     */     }
/* 3732:4580 */     if (str1.equals(this.currentRule.getRuleName()))
/* 3733:     */     {
/* 3734:4582 */       localObject = str1 + "_AST";
/* 3735:4583 */       if ((paramActionTransInfo != null) && 
/* 3736:4584 */         (i == 0)) {
/* 3737:4585 */         paramActionTransInfo.refRuleRoot = ((String)localObject);
/* 3738:     */       }
/* 3739:4590 */       return localObject;
/* 3740:     */     }
/* 3741:4597 */     return str1;
/* 3742:     */   }
/* 3743:     */   
/* 3744:     */   private void mapTreeVariable(AlternativeElement paramAlternativeElement, String paramString)
/* 3745:     */   {
/* 3746:4606 */     if ((paramAlternativeElement instanceof TreeElement))
/* 3747:     */     {
/* 3748:4607 */       mapTreeVariable(((TreeElement)paramAlternativeElement).root, paramString);
/* 3749:4608 */       return;
/* 3750:     */     }
/* 3751:4612 */     String str = null;
/* 3752:4615 */     if (paramAlternativeElement.getLabel() == null) {
/* 3753:4616 */       if ((paramAlternativeElement instanceof TokenRefElement)) {
/* 3754:4618 */         str = ((TokenRefElement)paramAlternativeElement).atomText;
/* 3755:4620 */       } else if ((paramAlternativeElement instanceof RuleRefElement)) {
/* 3756:4622 */         str = ((RuleRefElement)paramAlternativeElement).targetRule;
/* 3757:     */       }
/* 3758:     */     }
/* 3759:4626 */     if (str != null) {
/* 3760:4627 */       if (this.treeVariableMap.get(str) != null)
/* 3761:     */       {
/* 3762:4629 */         this.treeVariableMap.remove(str);
/* 3763:4630 */         this.treeVariableMap.put(str, NONUNIQUE);
/* 3764:     */       }
/* 3765:     */       else
/* 3766:     */       {
/* 3767:4633 */         this.treeVariableMap.put(str, paramString);
/* 3768:     */       }
/* 3769:     */     }
/* 3770:     */   }
/* 3771:     */   
/* 3772:     */   protected String processActionForSpecialSymbols(String paramString, int paramInt, RuleBlock paramRuleBlock, ActionTransInfo paramActionTransInfo)
/* 3773:     */   {
/* 3774:4647 */     if ((paramString == null) || (paramString.length() == 0)) {
/* 3775:4648 */       return null;
/* 3776:     */     }
/* 3777:4652 */     if (this.grammar == null) {
/* 3778:4653 */       return paramString;
/* 3779:     */     }
/* 3780:4655 */     if (((this.grammar.buildAST) && (paramString.indexOf('#') != -1)) || ((this.grammar instanceof TreeWalkerGrammar)) || ((((this.grammar instanceof LexerGrammar)) || ((this.grammar instanceof ParserGrammar))) && (paramString.indexOf('$') != -1)))
/* 3781:     */     {
/* 3782:4662 */       ActionLexer localActionLexer = new ActionLexer(paramString, paramRuleBlock, this, paramActionTransInfo);
/* 3783:     */       
/* 3784:4664 */       localActionLexer.setLineOffset(paramInt);
/* 3785:4665 */       localActionLexer.setFilename(this.grammar.getFilename());
/* 3786:4666 */       localActionLexer.setTool(this.antlrTool);
/* 3787:     */       try
/* 3788:     */       {
/* 3789:4669 */         localActionLexer.mACTION(true);
/* 3790:4670 */         paramString = localActionLexer.getTokenObject().getText();
/* 3791:     */       }
/* 3792:     */       catch (RecognitionException localRecognitionException)
/* 3793:     */       {
/* 3794:4675 */         localActionLexer.reportError(localRecognitionException);
/* 3795:4676 */         return paramString;
/* 3796:     */       }
/* 3797:     */       catch (TokenStreamException localTokenStreamException)
/* 3798:     */       {
/* 3799:4679 */         this.antlrTool.panic("Error reading action:" + paramString);
/* 3800:4680 */         return paramString;
/* 3801:     */       }
/* 3802:     */       catch (CharStreamException localCharStreamException)
/* 3803:     */       {
/* 3804:4683 */         this.antlrTool.panic("Error reading action:" + paramString);
/* 3805:4684 */         return paramString;
/* 3806:     */       }
/* 3807:     */     }
/* 3808:4687 */     return paramString;
/* 3809:     */   }
/* 3810:     */   
/* 3811:     */   private String fixNameSpaceOption(String paramString)
/* 3812:     */   {
/* 3813:4692 */     paramString = StringUtils.stripFrontBack(paramString, "\"", "\"");
/* 3814:4693 */     if ((paramString.length() > 2) && (!paramString.substring(paramString.length() - 2, paramString.length()).equals("::"))) {
/* 3815:4695 */       paramString = paramString + "::";
/* 3816:     */     }
/* 3817:4696 */     return paramString;
/* 3818:     */   }
/* 3819:     */   
/* 3820:     */   private void setupGrammarParameters(Grammar paramGrammar)
/* 3821:     */   {
/* 3822:     */     Token localToken;
/* 3823:     */     String str;
/* 3824:4700 */     if (((paramGrammar instanceof ParserGrammar)) || ((paramGrammar instanceof LexerGrammar)) || ((paramGrammar instanceof TreeWalkerGrammar)))
/* 3825:     */     {
/* 3826:4710 */       if (this.antlrTool.nameSpace != null) {
/* 3827:4711 */         nameSpace = this.antlrTool.nameSpace;
/* 3828:     */       }
/* 3829:4713 */       if (this.antlrTool.namespaceStd != null) {
/* 3830:4714 */         namespaceStd = fixNameSpaceOption(this.antlrTool.namespaceStd);
/* 3831:     */       }
/* 3832:4716 */       if (this.antlrTool.namespaceAntlr != null) {
/* 3833:4717 */         namespaceAntlr = fixNameSpaceOption(this.antlrTool.namespaceAntlr);
/* 3834:     */       }
/* 3835:4719 */       this.genHashLines = this.antlrTool.genHashLines;
/* 3836:4723 */       if (paramGrammar.hasOption("namespace"))
/* 3837:     */       {
/* 3838:4724 */         localToken = paramGrammar.getOption("namespace");
/* 3839:4725 */         if (localToken != null) {
/* 3840:4726 */           nameSpace = new NameSpace(localToken.getText());
/* 3841:     */         }
/* 3842:     */       }
/* 3843:4729 */       if (paramGrammar.hasOption("namespaceAntlr"))
/* 3844:     */       {
/* 3845:4730 */         localToken = paramGrammar.getOption("namespaceAntlr");
/* 3846:4731 */         if (localToken != null)
/* 3847:     */         {
/* 3848:4732 */           str = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/* 3849:4733 */           if (str != null)
/* 3850:     */           {
/* 3851:4734 */             if ((str.length() > 2) && (!str.substring(str.length() - 2, str.length()).equals("::"))) {
/* 3852:4736 */               str = str + "::";
/* 3853:     */             }
/* 3854:4737 */             namespaceAntlr = str;
/* 3855:     */           }
/* 3856:     */         }
/* 3857:     */       }
/* 3858:4741 */       if (paramGrammar.hasOption("namespaceStd"))
/* 3859:     */       {
/* 3860:4742 */         localToken = paramGrammar.getOption("namespaceStd");
/* 3861:4743 */         if (localToken != null)
/* 3862:     */         {
/* 3863:4744 */           str = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/* 3864:4745 */           if (str != null)
/* 3865:     */           {
/* 3866:4746 */             if ((str.length() > 2) && (!str.substring(str.length() - 2, str.length()).equals("::"))) {
/* 3867:4748 */               str = str + "::";
/* 3868:     */             }
/* 3869:4749 */             namespaceStd = str;
/* 3870:     */           }
/* 3871:     */         }
/* 3872:     */       }
/* 3873:4753 */       if (paramGrammar.hasOption("genHashLines"))
/* 3874:     */       {
/* 3875:4754 */         localToken = paramGrammar.getOption("genHashLines");
/* 3876:4755 */         if (localToken != null)
/* 3877:     */         {
/* 3878:4756 */           str = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/* 3879:4757 */           this.genHashLines = str.equals("true");
/* 3880:     */         }
/* 3881:     */       }
/* 3882:4760 */       this.noConstructors = this.antlrTool.noConstructors;
/* 3883:4761 */       if (paramGrammar.hasOption("noConstructors"))
/* 3884:     */       {
/* 3885:4762 */         localToken = paramGrammar.getOption("noConstructors");
/* 3886:4763 */         if ((localToken != null) && (!localToken.getText().equals("true")) && (!localToken.getText().equals("false"))) {
/* 3887:4764 */           this.antlrTool.error("noConstructors option must be true or false", this.antlrTool.getGrammarFile(), localToken.getLine(), localToken.getColumn());
/* 3888:     */         }
/* 3889:4765 */         this.noConstructors = localToken.getText().equals("true");
/* 3890:     */       }
/* 3891:     */     }
/* 3892:4768 */     if ((paramGrammar instanceof ParserGrammar))
/* 3893:     */     {
/* 3894:4769 */       this.labeledElementASTType = (namespaceAntlr + "RefAST");
/* 3895:4770 */       this.labeledElementASTInit = (namespaceAntlr + "nullAST");
/* 3896:4771 */       if (paramGrammar.hasOption("ASTLabelType"))
/* 3897:     */       {
/* 3898:4772 */         localToken = paramGrammar.getOption("ASTLabelType");
/* 3899:4773 */         if (localToken != null)
/* 3900:     */         {
/* 3901:4774 */           str = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/* 3902:4775 */           if (str != null)
/* 3903:     */           {
/* 3904:4776 */             this.usingCustomAST = true;
/* 3905:4777 */             this.labeledElementASTType = str;
/* 3906:4778 */             this.labeledElementASTInit = (str + "(" + namespaceAntlr + "nullAST)");
/* 3907:     */           }
/* 3908:     */         }
/* 3909:     */       }
/* 3910:4782 */       this.labeledElementType = (namespaceAntlr + "RefToken ");
/* 3911:4783 */       this.labeledElementInit = (namespaceAntlr + "nullToken");
/* 3912:4784 */       this.commonExtraArgs = "";
/* 3913:4785 */       this.commonExtraParams = "";
/* 3914:4786 */       this.commonLocalVars = "";
/* 3915:4787 */       this.lt1Value = "LT(1)";
/* 3916:4788 */       this.exceptionThrown = (namespaceAntlr + "RecognitionException");
/* 3917:4789 */       this.throwNoViable = ("throw " + namespaceAntlr + "NoViableAltException(LT(1), getFilename());");
/* 3918:     */     }
/* 3919:4791 */     else if ((paramGrammar instanceof LexerGrammar))
/* 3920:     */     {
/* 3921:4792 */       this.labeledElementType = "char ";
/* 3922:4793 */       this.labeledElementInit = "'\\0'";
/* 3923:4794 */       this.commonExtraArgs = "";
/* 3924:4795 */       this.commonExtraParams = "bool _createToken";
/* 3925:4796 */       this.commonLocalVars = ("int _ttype; " + namespaceAntlr + "RefToken _token; " + namespaceStd + "string::size_type _begin = text.length();");
/* 3926:4797 */       this.lt1Value = "LA(1)";
/* 3927:4798 */       this.exceptionThrown = (namespaceAntlr + "RecognitionException");
/* 3928:4799 */       this.throwNoViable = ("throw " + namespaceAntlr + "NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());");
/* 3929:     */     }
/* 3930:4801 */     else if ((paramGrammar instanceof TreeWalkerGrammar))
/* 3931:     */     {
/* 3932:4802 */       this.labeledElementInit = (namespaceAntlr + "nullAST");
/* 3933:4803 */       this.labeledElementASTInit = (namespaceAntlr + "nullAST");
/* 3934:4804 */       this.labeledElementASTType = (namespaceAntlr + "RefAST");
/* 3935:4805 */       this.labeledElementType = (namespaceAntlr + "RefAST");
/* 3936:4806 */       this.commonExtraParams = (namespaceAntlr + "RefAST _t");
/* 3937:4807 */       this.throwNoViable = ("throw " + namespaceAntlr + "NoViableAltException(_t);");
/* 3938:4808 */       this.lt1Value = "_t";
/* 3939:4809 */       if (paramGrammar.hasOption("ASTLabelType"))
/* 3940:     */       {
/* 3941:4810 */         localToken = paramGrammar.getOption("ASTLabelType");
/* 3942:4811 */         if (localToken != null)
/* 3943:     */         {
/* 3944:4812 */           str = StringUtils.stripFrontBack(localToken.getText(), "\"", "\"");
/* 3945:4813 */           if (str != null)
/* 3946:     */           {
/* 3947:4814 */             this.usingCustomAST = true;
/* 3948:4815 */             this.labeledElementASTType = str;
/* 3949:4816 */             this.labeledElementType = str;
/* 3950:4817 */             this.labeledElementInit = (str + "(" + namespaceAntlr + "nullAST)");
/* 3951:4818 */             this.labeledElementASTInit = this.labeledElementInit;
/* 3952:4819 */             this.commonExtraParams = (str + " _t");
/* 3953:4820 */             this.throwNoViable = ("throw " + namespaceAntlr + "NoViableAltException(" + namespaceAntlr + "RefAST(_t));");
/* 3954:4821 */             this.lt1Value = "_t";
/* 3955:     */           }
/* 3956:     */         }
/* 3957:     */       }
/* 3958:4825 */       if (!paramGrammar.hasOption("ASTLabelType")) {
/* 3959:4826 */         paramGrammar.setOption("ASTLabelType", new Token(6, namespaceAntlr + "RefAST"));
/* 3960:     */       }
/* 3961:4828 */       this.commonExtraArgs = "_t";
/* 3962:4829 */       this.commonLocalVars = "";
/* 3963:4830 */       this.exceptionThrown = (namespaceAntlr + "RecognitionException");
/* 3964:     */     }
/* 3965:     */     else
/* 3966:     */     {
/* 3967:4833 */       this.antlrTool.panic("Unknown grammar type");
/* 3968:     */     }
/* 3969:     */   }
/* 3970:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.CppCodeGenerator
 * JD-Core Version:    0.7.0.1
 */