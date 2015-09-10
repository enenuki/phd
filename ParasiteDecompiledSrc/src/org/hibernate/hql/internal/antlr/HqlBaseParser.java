/*    1:     */ package org.hibernate.hql.internal.antlr;
/*    2:     */ 
/*    3:     */ import antlr.ASTFactory;
/*    4:     */ import antlr.ASTPair;
/*    5:     */ import antlr.LLkParser;
/*    6:     */ import antlr.NoViableAltException;
/*    7:     */ import antlr.ParserSharedInputState;
/*    8:     */ import antlr.RecognitionException;
/*    9:     */ import antlr.SemanticException;
/*   10:     */ import antlr.Token;
/*   11:     */ import antlr.TokenBuffer;
/*   12:     */ import antlr.TokenStream;
/*   13:     */ import antlr.TokenStreamException;
/*   14:     */ import antlr.collections.AST;
/*   15:     */ import antlr.collections.impl.ASTArray;
/*   16:     */ import antlr.collections.impl.BitSet;
/*   17:     */ import org.hibernate.hql.internal.ast.util.ASTUtil;
/*   18:     */ 
/*   19:     */ public class HqlBaseParser
/*   20:     */   extends LLkParser
/*   21:     */   implements HqlTokenTypes
/*   22:     */ {
/*   23:  42 */   private boolean filter = false;
/*   24:     */   
/*   25:     */   public void setFilter(boolean f)
/*   26:     */   {
/*   27:  49 */     this.filter = f;
/*   28:     */   }
/*   29:     */   
/*   30:     */   public boolean isFilter()
/*   31:     */   {
/*   32:  57 */     return this.filter;
/*   33:     */   }
/*   34:     */   
/*   35:     */   public AST handleIdentifierError(Token token, RecognitionException ex)
/*   36:     */     throws RecognitionException, TokenStreamException
/*   37:     */   {
/*   38:  68 */     throw ex;
/*   39:     */   }
/*   40:     */   
/*   41:     */   public void handleDotIdent()
/*   42:     */     throws TokenStreamException
/*   43:     */   {}
/*   44:     */   
/*   45:     */   public AST negateNode(AST x)
/*   46:     */   {
/*   47:  84 */     return ASTUtil.createParent(this.astFactory, 38, "not", x);
/*   48:     */   }
/*   49:     */   
/*   50:     */   public AST processEqualityExpression(AST x)
/*   51:     */     throws RecognitionException
/*   52:     */   {
/*   53:  92 */     return x;
/*   54:     */   }
/*   55:     */   
/*   56:     */   public void weakKeywords()
/*   57:     */     throws TokenStreamException
/*   58:     */   {}
/*   59:     */   
/*   60:     */   public void processMemberOf(Token n, AST p, ASTPair currentAST) {}
/*   61:     */   
/*   62:     */   protected HqlBaseParser(TokenBuffer tokenBuf, int k)
/*   63:     */   {
/*   64: 101 */     super(tokenBuf, k);
/*   65: 102 */     this.tokenNames = _tokenNames;
/*   66: 103 */     buildTokenTypeASTClassMap();
/*   67: 104 */     this.astFactory = new ASTFactory(getTokenTypeToASTClassMap());
/*   68:     */   }
/*   69:     */   
/*   70:     */   public HqlBaseParser(TokenBuffer tokenBuf)
/*   71:     */   {
/*   72: 108 */     this(tokenBuf, 3);
/*   73:     */   }
/*   74:     */   
/*   75:     */   protected HqlBaseParser(TokenStream lexer, int k)
/*   76:     */   {
/*   77: 112 */     super(lexer, k);
/*   78: 113 */     this.tokenNames = _tokenNames;
/*   79: 114 */     buildTokenTypeASTClassMap();
/*   80: 115 */     this.astFactory = new ASTFactory(getTokenTypeToASTClassMap());
/*   81:     */   }
/*   82:     */   
/*   83:     */   public HqlBaseParser(TokenStream lexer)
/*   84:     */   {
/*   85: 119 */     this(lexer, 3);
/*   86:     */   }
/*   87:     */   
/*   88:     */   public HqlBaseParser(ParserSharedInputState state)
/*   89:     */   {
/*   90: 123 */     super(state, 3);
/*   91: 124 */     this.tokenNames = _tokenNames;
/*   92: 125 */     buildTokenTypeASTClassMap();
/*   93: 126 */     this.astFactory = new ASTFactory(getTokenTypeToASTClassMap());
/*   94:     */   }
/*   95:     */   
/*   96:     */   public final void statement()
/*   97:     */     throws RecognitionException, TokenStreamException
/*   98:     */   {
/*   99: 131 */     this.returnAST = null;
/*  100: 132 */     ASTPair currentAST = new ASTPair();
/*  101: 133 */     AST statement_AST = null;
/*  102:     */     try
/*  103:     */     {
/*  104: 137 */       switch (LA(1))
/*  105:     */       {
/*  106:     */       case 51: 
/*  107: 140 */         updateStatement();
/*  108: 141 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*  109: 142 */         break;
/*  110:     */       case 13: 
/*  111: 146 */         deleteStatement();
/*  112: 147 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*  113: 148 */         break;
/*  114:     */       case 1: 
/*  115:     */       case 22: 
/*  116:     */       case 24: 
/*  117:     */       case 41: 
/*  118:     */       case 45: 
/*  119:     */       case 53: 
/*  120: 157 */         selectStatement();
/*  121: 158 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*  122: 159 */         break;
/*  123:     */       case 29: 
/*  124: 163 */         insertStatement();
/*  125: 164 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*  126: 165 */         break;
/*  127:     */       default: 
/*  128: 169 */         throw new NoViableAltException(LT(1), getFilename());
/*  129:     */       }
/*  130: 173 */       statement_AST = currentAST.root;
/*  131:     */     }
/*  132:     */     catch (RecognitionException ex)
/*  133:     */     {
/*  134: 176 */       reportError(ex);
/*  135: 177 */       recover(ex, _tokenSet_0);
/*  136:     */     }
/*  137: 179 */     this.returnAST = statement_AST;
/*  138:     */   }
/*  139:     */   
/*  140:     */   public final void updateStatement()
/*  141:     */     throws RecognitionException, TokenStreamException
/*  142:     */   {
/*  143: 184 */     this.returnAST = null;
/*  144: 185 */     ASTPair currentAST = new ASTPair();
/*  145: 186 */     AST updateStatement_AST = null;
/*  146:     */     try
/*  147:     */     {
/*  148: 189 */       AST tmp1_AST = null;
/*  149: 190 */       tmp1_AST = this.astFactory.create(LT(1));
/*  150: 191 */       this.astFactory.makeASTRoot(currentAST, tmp1_AST);
/*  151: 192 */       match(51);
/*  152: 194 */       switch (LA(1))
/*  153:     */       {
/*  154:     */       case 52: 
/*  155: 197 */         AST tmp2_AST = null;
/*  156: 198 */         tmp2_AST = this.astFactory.create(LT(1));
/*  157: 199 */         this.astFactory.addASTChild(currentAST, tmp2_AST);
/*  158: 200 */         match(52);
/*  159: 201 */         break;
/*  160:     */       case 22: 
/*  161:     */       case 126: 
/*  162:     */         break;
/*  163:     */       default: 
/*  164: 210 */         throw new NoViableAltException(LT(1), getFilename());
/*  165:     */       }
/*  166: 214 */       optionalFromTokenFromClause();
/*  167: 215 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/*  168: 216 */       setClause();
/*  169: 217 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/*  170: 219 */       switch (LA(1))
/*  171:     */       {
/*  172:     */       case 53: 
/*  173: 222 */         whereClause();
/*  174: 223 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*  175: 224 */         break;
/*  176:     */       case 1: 
/*  177:     */         break;
/*  178:     */       default: 
/*  179: 232 */         throw new NoViableAltException(LT(1), getFilename());
/*  180:     */       }
/*  181: 236 */       updateStatement_AST = currentAST.root;
/*  182:     */     }
/*  183:     */     catch (RecognitionException ex)
/*  184:     */     {
/*  185: 239 */       reportError(ex);
/*  186: 240 */       recover(ex, _tokenSet_0);
/*  187:     */     }
/*  188: 242 */     this.returnAST = updateStatement_AST;
/*  189:     */   }
/*  190:     */   
/*  191:     */   public final void deleteStatement()
/*  192:     */     throws RecognitionException, TokenStreamException
/*  193:     */   {
/*  194: 247 */     this.returnAST = null;
/*  195: 248 */     ASTPair currentAST = new ASTPair();
/*  196: 249 */     AST deleteStatement_AST = null;
/*  197:     */     try
/*  198:     */     {
/*  199: 252 */       AST tmp3_AST = null;
/*  200: 253 */       tmp3_AST = this.astFactory.create(LT(1));
/*  201: 254 */       this.astFactory.makeASTRoot(currentAST, tmp3_AST);
/*  202: 255 */       match(13);
/*  203:     */       
/*  204: 257 */       optionalFromTokenFromClause();
/*  205: 258 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/*  206: 261 */       switch (LA(1))
/*  207:     */       {
/*  208:     */       case 53: 
/*  209: 264 */         whereClause();
/*  210: 265 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*  211: 266 */         break;
/*  212:     */       case 1: 
/*  213:     */         break;
/*  214:     */       default: 
/*  215: 274 */         throw new NoViableAltException(LT(1), getFilename());
/*  216:     */       }
/*  217: 278 */       deleteStatement_AST = currentAST.root;
/*  218:     */     }
/*  219:     */     catch (RecognitionException ex)
/*  220:     */     {
/*  221: 281 */       reportError(ex);
/*  222: 282 */       recover(ex, _tokenSet_0);
/*  223:     */     }
/*  224: 284 */     this.returnAST = deleteStatement_AST;
/*  225:     */   }
/*  226:     */   
/*  227:     */   public final void selectStatement()
/*  228:     */     throws RecognitionException, TokenStreamException
/*  229:     */   {
/*  230: 289 */     this.returnAST = null;
/*  231: 290 */     ASTPair currentAST = new ASTPair();
/*  232: 291 */     AST selectStatement_AST = null;
/*  233:     */     try
/*  234:     */     {
/*  235: 294 */       queryRule();
/*  236: 295 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/*  237: 296 */       selectStatement_AST = currentAST.root;
/*  238:     */       
/*  239: 298 */       selectStatement_AST = this.astFactory.make(new ASTArray(2).add(this.astFactory.create(86, "query")).add(selectStatement_AST));
/*  240:     */       
/*  241: 300 */       currentAST.root = selectStatement_AST;
/*  242: 301 */       currentAST.child = ((selectStatement_AST != null) && (selectStatement_AST.getFirstChild() != null) ? selectStatement_AST.getFirstChild() : selectStatement_AST);
/*  243:     */       
/*  244: 303 */       currentAST.advanceChildToEnd();
/*  245: 304 */       selectStatement_AST = currentAST.root;
/*  246:     */     }
/*  247:     */     catch (RecognitionException ex)
/*  248:     */     {
/*  249: 307 */       reportError(ex);
/*  250: 308 */       recover(ex, _tokenSet_0);
/*  251:     */     }
/*  252: 310 */     this.returnAST = selectStatement_AST;
/*  253:     */   }
/*  254:     */   
/*  255:     */   public final void insertStatement()
/*  256:     */     throws RecognitionException, TokenStreamException
/*  257:     */   {
/*  258: 315 */     this.returnAST = null;
/*  259: 316 */     ASTPair currentAST = new ASTPair();
/*  260: 317 */     AST insertStatement_AST = null;
/*  261:     */     try
/*  262:     */     {
/*  263: 320 */       AST tmp4_AST = null;
/*  264: 321 */       tmp4_AST = this.astFactory.create(LT(1));
/*  265: 322 */       this.astFactory.makeASTRoot(currentAST, tmp4_AST);
/*  266: 323 */       match(29);
/*  267: 324 */       intoClause();
/*  268: 325 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/*  269: 326 */       selectStatement();
/*  270: 327 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/*  271: 328 */       insertStatement_AST = currentAST.root;
/*  272:     */     }
/*  273:     */     catch (RecognitionException ex)
/*  274:     */     {
/*  275: 331 */       reportError(ex);
/*  276: 332 */       recover(ex, _tokenSet_0);
/*  277:     */     }
/*  278: 334 */     this.returnAST = insertStatement_AST;
/*  279:     */   }
/*  280:     */   
/*  281:     */   public final void optionalFromTokenFromClause()
/*  282:     */     throws RecognitionException, TokenStreamException
/*  283:     */   {
/*  284: 339 */     this.returnAST = null;
/*  285: 340 */     ASTPair currentAST = new ASTPair();
/*  286: 341 */     AST optionalFromTokenFromClause_AST = null;
/*  287: 342 */     AST f_AST = null;
/*  288: 343 */     AST a_AST = null;
/*  289:     */     try
/*  290:     */     {
/*  291: 347 */       switch (LA(1))
/*  292:     */       {
/*  293:     */       case 22: 
/*  294: 350 */         match(22);
/*  295: 351 */         break;
/*  296:     */       case 126: 
/*  297:     */         break;
/*  298:     */       default: 
/*  299: 359 */         throw new NoViableAltException(LT(1), getFilename());
/*  300:     */       }
/*  301: 363 */       path();
/*  302: 364 */       f_AST = this.returnAST;
/*  303: 366 */       switch (LA(1))
/*  304:     */       {
/*  305:     */       case 7: 
/*  306:     */       case 126: 
/*  307: 370 */         asAlias();
/*  308: 371 */         a_AST = this.returnAST;
/*  309: 372 */         break;
/*  310:     */       case 1: 
/*  311:     */       case 46: 
/*  312:     */       case 53: 
/*  313:     */         break;
/*  314:     */       default: 
/*  315: 382 */         throw new NoViableAltException(LT(1), getFilename());
/*  316:     */       }
/*  317: 386 */       optionalFromTokenFromClause_AST = currentAST.root;
/*  318:     */       
/*  319: 388 */       AST range = this.astFactory.make(new ASTArray(3).add(this.astFactory.create(87, "RANGE")).add(f_AST).add(a_AST));
/*  320: 389 */       optionalFromTokenFromClause_AST = this.astFactory.make(new ASTArray(2).add(this.astFactory.create(22, "FROM")).add(range));
/*  321:     */       
/*  322: 391 */       currentAST.root = optionalFromTokenFromClause_AST;
/*  323: 392 */       currentAST.child = ((optionalFromTokenFromClause_AST != null) && (optionalFromTokenFromClause_AST.getFirstChild() != null) ? optionalFromTokenFromClause_AST.getFirstChild() : optionalFromTokenFromClause_AST);
/*  324:     */       
/*  325: 394 */       currentAST.advanceChildToEnd();
/*  326:     */     }
/*  327:     */     catch (RecognitionException ex)
/*  328:     */     {
/*  329: 397 */       reportError(ex);
/*  330: 398 */       recover(ex, _tokenSet_1);
/*  331:     */     }
/*  332: 400 */     this.returnAST = optionalFromTokenFromClause_AST;
/*  333:     */   }
/*  334:     */   
/*  335:     */   public final void setClause()
/*  336:     */     throws RecognitionException, TokenStreamException
/*  337:     */   {
/*  338: 405 */     this.returnAST = null;
/*  339: 406 */     ASTPair currentAST = new ASTPair();
/*  340: 407 */     AST setClause_AST = null;
/*  341:     */     try
/*  342:     */     {
/*  343: 411 */       AST tmp6_AST = null;
/*  344: 412 */       tmp6_AST = this.astFactory.create(LT(1));
/*  345: 413 */       this.astFactory.makeASTRoot(currentAST, tmp6_AST);
/*  346: 414 */       match(46);
/*  347: 415 */       assignment();
/*  348: 416 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/*  349: 420 */       while (LA(1) == 101)
/*  350:     */       {
/*  351: 421 */         match(101);
/*  352: 422 */         assignment();
/*  353: 423 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*  354:     */       }
/*  355: 432 */       setClause_AST = currentAST.root;
/*  356:     */     }
/*  357:     */     catch (RecognitionException ex)
/*  358:     */     {
/*  359: 435 */       reportError(ex);
/*  360: 436 */       recover(ex, _tokenSet_2);
/*  361:     */     }
/*  362: 438 */     this.returnAST = setClause_AST;
/*  363:     */   }
/*  364:     */   
/*  365:     */   public final void whereClause()
/*  366:     */     throws RecognitionException, TokenStreamException
/*  367:     */   {
/*  368: 443 */     this.returnAST = null;
/*  369: 444 */     ASTPair currentAST = new ASTPair();
/*  370: 445 */     AST whereClause_AST = null;
/*  371:     */     try
/*  372:     */     {
/*  373: 448 */       AST tmp8_AST = null;
/*  374: 449 */       tmp8_AST = this.astFactory.create(LT(1));
/*  375: 450 */       this.astFactory.makeASTRoot(currentAST, tmp8_AST);
/*  376: 451 */       match(53);
/*  377: 452 */       logicalExpression();
/*  378: 453 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/*  379: 454 */       whereClause_AST = currentAST.root;
/*  380:     */     }
/*  381:     */     catch (RecognitionException ex)
/*  382:     */     {
/*  383: 457 */       reportError(ex);
/*  384: 458 */       recover(ex, _tokenSet_3);
/*  385:     */     }
/*  386: 460 */     this.returnAST = whereClause_AST;
/*  387:     */   }
/*  388:     */   
/*  389:     */   public final void assignment()
/*  390:     */     throws RecognitionException, TokenStreamException
/*  391:     */   {
/*  392: 465 */     this.returnAST = null;
/*  393: 466 */     ASTPair currentAST = new ASTPair();
/*  394: 467 */     AST assignment_AST = null;
/*  395:     */     try
/*  396:     */     {
/*  397: 470 */       stateField();
/*  398: 471 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/*  399: 472 */       AST tmp9_AST = null;
/*  400: 473 */       tmp9_AST = this.astFactory.create(LT(1));
/*  401: 474 */       this.astFactory.makeASTRoot(currentAST, tmp9_AST);
/*  402: 475 */       match(102);
/*  403: 476 */       newValue();
/*  404: 477 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/*  405: 478 */       assignment_AST = currentAST.root;
/*  406:     */     }
/*  407:     */     catch (RecognitionException ex)
/*  408:     */     {
/*  409: 481 */       reportError(ex);
/*  410: 482 */       recover(ex, _tokenSet_4);
/*  411:     */     }
/*  412: 484 */     this.returnAST = assignment_AST;
/*  413:     */   }
/*  414:     */   
/*  415:     */   public final void stateField()
/*  416:     */     throws RecognitionException, TokenStreamException
/*  417:     */   {
/*  418: 489 */     this.returnAST = null;
/*  419: 490 */     ASTPair currentAST = new ASTPair();
/*  420: 491 */     AST stateField_AST = null;
/*  421:     */     try
/*  422:     */     {
/*  423: 494 */       path();
/*  424: 495 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/*  425: 496 */       stateField_AST = currentAST.root;
/*  426:     */     }
/*  427:     */     catch (RecognitionException ex)
/*  428:     */     {
/*  429: 499 */       reportError(ex);
/*  430: 500 */       recover(ex, _tokenSet_5);
/*  431:     */     }
/*  432: 502 */     this.returnAST = stateField_AST;
/*  433:     */   }
/*  434:     */   
/*  435:     */   public final void newValue()
/*  436:     */     throws RecognitionException, TokenStreamException
/*  437:     */   {
/*  438: 507 */     this.returnAST = null;
/*  439: 508 */     ASTPair currentAST = new ASTPair();
/*  440: 509 */     AST newValue_AST = null;
/*  441:     */     try
/*  442:     */     {
/*  443: 512 */       concatenation();
/*  444: 513 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/*  445: 514 */       newValue_AST = currentAST.root;
/*  446:     */     }
/*  447:     */     catch (RecognitionException ex)
/*  448:     */     {
/*  449: 517 */       reportError(ex);
/*  450: 518 */       recover(ex, _tokenSet_4);
/*  451:     */     }
/*  452: 520 */     this.returnAST = newValue_AST;
/*  453:     */   }
/*  454:     */   
/*  455:     */   public final void path()
/*  456:     */     throws RecognitionException, TokenStreamException
/*  457:     */   {
/*  458: 525 */     this.returnAST = null;
/*  459: 526 */     ASTPair currentAST = new ASTPair();
/*  460: 527 */     AST path_AST = null;
/*  461:     */     try
/*  462:     */     {
/*  463: 530 */       identifier();
/*  464: 531 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/*  465: 535 */       while (LA(1) == 15)
/*  466:     */       {
/*  467: 536 */         AST tmp10_AST = null;
/*  468: 537 */         tmp10_AST = this.astFactory.create(LT(1));
/*  469: 538 */         this.astFactory.makeASTRoot(currentAST, tmp10_AST);
/*  470: 539 */         match(15);
/*  471: 540 */         weakKeywords();
/*  472: 541 */         identifier();
/*  473: 542 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*  474:     */       }
/*  475: 550 */       path_AST = currentAST.root;
/*  476:     */     }
/*  477:     */     catch (RecognitionException ex)
/*  478:     */     {
/*  479: 553 */       reportError(ex);
/*  480: 554 */       recover(ex, _tokenSet_6);
/*  481:     */     }
/*  482: 556 */     this.returnAST = path_AST;
/*  483:     */   }
/*  484:     */   
/*  485:     */   public final void concatenation()
/*  486:     */     throws RecognitionException, TokenStreamException
/*  487:     */   {
/*  488: 561 */     this.returnAST = null;
/*  489: 562 */     ASTPair currentAST = new ASTPair();
/*  490: 563 */     AST concatenation_AST = null;
/*  491: 564 */     Token c = null;
/*  492: 565 */     AST c_AST = null;
/*  493:     */     try
/*  494:     */     {
/*  495: 568 */       additiveExpression();
/*  496: 569 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/*  497: 571 */       switch (LA(1))
/*  498:     */       {
/*  499:     */       case 114: 
/*  500: 574 */         c = LT(1);
/*  501: 575 */         c_AST = this.astFactory.create(c);
/*  502: 576 */         this.astFactory.makeASTRoot(currentAST, c_AST);
/*  503: 577 */         match(114);
/*  504: 578 */         c_AST.setType(75);c_AST.setText("concatList");
/*  505: 579 */         additiveExpression();
/*  506: 580 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*  507: 584 */         while (LA(1) == 114)
/*  508:     */         {
/*  509: 585 */           match(114);
/*  510: 586 */           additiveExpression();
/*  511: 587 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/*  512:     */         }
/*  513: 595 */         concatenation_AST = currentAST.root;
/*  514: 596 */         concatenation_AST = this.astFactory.make(new ASTArray(3).add(this.astFactory.create(81, "||")).add(this.astFactory.make(new ASTArray(1).add(this.astFactory.create(126, "concat")))).add(c_AST));
/*  515: 597 */         currentAST.root = concatenation_AST;
/*  516: 598 */         currentAST.child = ((concatenation_AST != null) && (concatenation_AST.getFirstChild() != null) ? concatenation_AST.getFirstChild() : concatenation_AST);
/*  517:     */         
/*  518: 600 */         currentAST.advanceChildToEnd();
/*  519: 601 */         break;
/*  520:     */       case 1: 
/*  521:     */       case 6: 
/*  522:     */       case 7: 
/*  523:     */       case 8: 
/*  524:     */       case 10: 
/*  525:     */       case 14: 
/*  526:     */       case 18: 
/*  527:     */       case 22: 
/*  528:     */       case 23: 
/*  529:     */       case 24: 
/*  530:     */       case 25: 
/*  531:     */       case 26: 
/*  532:     */       case 28: 
/*  533:     */       case 31: 
/*  534:     */       case 32: 
/*  535:     */       case 33: 
/*  536:     */       case 34: 
/*  537:     */       case 38: 
/*  538:     */       case 40: 
/*  539:     */       case 41: 
/*  540:     */       case 44: 
/*  541:     */       case 50: 
/*  542:     */       case 53: 
/*  543:     */       case 57: 
/*  544:     */       case 64: 
/*  545:     */       case 101: 
/*  546:     */       case 102: 
/*  547:     */       case 104: 
/*  548:     */       case 106: 
/*  549:     */       case 107: 
/*  550:     */       case 108: 
/*  551:     */       case 109: 
/*  552:     */       case 110: 
/*  553:     */       case 111: 
/*  554:     */       case 112: 
/*  555:     */       case 113: 
/*  556:     */       case 121: 
/*  557:     */         break;
/*  558:     */       case 2: 
/*  559:     */       case 3: 
/*  560:     */       case 4: 
/*  561:     */       case 5: 
/*  562:     */       case 9: 
/*  563:     */       case 11: 
/*  564:     */       case 12: 
/*  565:     */       case 13: 
/*  566:     */       case 15: 
/*  567:     */       case 16: 
/*  568:     */       case 17: 
/*  569:     */       case 19: 
/*  570:     */       case 20: 
/*  571:     */       case 21: 
/*  572:     */       case 27: 
/*  573:     */       case 29: 
/*  574:     */       case 30: 
/*  575:     */       case 35: 
/*  576:     */       case 36: 
/*  577:     */       case 37: 
/*  578:     */       case 39: 
/*  579:     */       case 42: 
/*  580:     */       case 43: 
/*  581:     */       case 45: 
/*  582:     */       case 46: 
/*  583:     */       case 47: 
/*  584:     */       case 48: 
/*  585:     */       case 49: 
/*  586:     */       case 51: 
/*  587:     */       case 52: 
/*  588:     */       case 54: 
/*  589:     */       case 55: 
/*  590:     */       case 56: 
/*  591:     */       case 58: 
/*  592:     */       case 59: 
/*  593:     */       case 60: 
/*  594:     */       case 61: 
/*  595:     */       case 62: 
/*  596:     */       case 63: 
/*  597:     */       case 65: 
/*  598:     */       case 66: 
/*  599:     */       case 67: 
/*  600:     */       case 68: 
/*  601:     */       case 69: 
/*  602:     */       case 70: 
/*  603:     */       case 71: 
/*  604:     */       case 72: 
/*  605:     */       case 73: 
/*  606:     */       case 74: 
/*  607:     */       case 75: 
/*  608:     */       case 76: 
/*  609:     */       case 77: 
/*  610:     */       case 78: 
/*  611:     */       case 79: 
/*  612:     */       case 80: 
/*  613:     */       case 81: 
/*  614:     */       case 82: 
/*  615:     */       case 83: 
/*  616:     */       case 84: 
/*  617:     */       case 85: 
/*  618:     */       case 86: 
/*  619:     */       case 87: 
/*  620:     */       case 88: 
/*  621:     */       case 89: 
/*  622:     */       case 90: 
/*  623:     */       case 91: 
/*  624:     */       case 92: 
/*  625:     */       case 93: 
/*  626:     */       case 94: 
/*  627:     */       case 95: 
/*  628:     */       case 96: 
/*  629:     */       case 97: 
/*  630:     */       case 98: 
/*  631:     */       case 99: 
/*  632:     */       case 100: 
/*  633:     */       case 103: 
/*  634:     */       case 105: 
/*  635:     */       case 115: 
/*  636:     */       case 116: 
/*  637:     */       case 117: 
/*  638:     */       case 118: 
/*  639:     */       case 119: 
/*  640:     */       case 120: 
/*  641:     */       default: 
/*  642: 645 */         throw new NoViableAltException(LT(1), getFilename());
/*  643:     */       }
/*  644: 649 */       concatenation_AST = currentAST.root;
/*  645:     */     }
/*  646:     */     catch (RecognitionException ex)
/*  647:     */     {
/*  648: 652 */       reportError(ex);
/*  649: 653 */       recover(ex, _tokenSet_7);
/*  650:     */     }
/*  651: 655 */     this.returnAST = concatenation_AST;
/*  652:     */   }
/*  653:     */   
/*  654:     */   public final void asAlias()
/*  655:     */     throws RecognitionException, TokenStreamException
/*  656:     */   {
/*  657: 660 */     this.returnAST = null;
/*  658: 661 */     ASTPair currentAST = new ASTPair();
/*  659: 662 */     AST asAlias_AST = null;
/*  660:     */     try
/*  661:     */     {
/*  662: 666 */       switch (LA(1))
/*  663:     */       {
/*  664:     */       case 7: 
/*  665: 669 */         match(7);
/*  666: 670 */         break;
/*  667:     */       case 126: 
/*  668:     */         break;
/*  669:     */       default: 
/*  670: 678 */         throw new NoViableAltException(LT(1), getFilename());
/*  671:     */       }
/*  672: 682 */       alias();
/*  673: 683 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/*  674: 684 */       asAlias_AST = currentAST.root;
/*  675:     */     }
/*  676:     */     catch (RecognitionException ex)
/*  677:     */     {
/*  678: 687 */       reportError(ex);
/*  679: 688 */       recover(ex, _tokenSet_8);
/*  680:     */     }
/*  681: 690 */     this.returnAST = asAlias_AST;
/*  682:     */   }
/*  683:     */   
/*  684:     */   public final void queryRule()
/*  685:     */     throws RecognitionException, TokenStreamException
/*  686:     */   {
/*  687: 695 */     this.returnAST = null;
/*  688: 696 */     ASTPair currentAST = new ASTPair();
/*  689: 697 */     AST queryRule_AST = null;
/*  690:     */     try
/*  691:     */     {
/*  692: 700 */       selectFrom();
/*  693: 701 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/*  694: 703 */       switch (LA(1))
/*  695:     */       {
/*  696:     */       case 53: 
/*  697: 706 */         whereClause();
/*  698: 707 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*  699: 708 */         break;
/*  700:     */       case 1: 
/*  701:     */       case 24: 
/*  702:     */       case 41: 
/*  703:     */       case 50: 
/*  704:     */       case 104: 
/*  705:     */         break;
/*  706:     */       default: 
/*  707: 720 */         throw new NoViableAltException(LT(1), getFilename());
/*  708:     */       }
/*  709: 725 */       switch (LA(1))
/*  710:     */       {
/*  711:     */       case 24: 
/*  712: 728 */         groupByClause();
/*  713: 729 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*  714: 730 */         break;
/*  715:     */       case 1: 
/*  716:     */       case 41: 
/*  717:     */       case 50: 
/*  718:     */       case 104: 
/*  719:     */         break;
/*  720:     */       default: 
/*  721: 741 */         throw new NoViableAltException(LT(1), getFilename());
/*  722:     */       }
/*  723: 746 */       switch (LA(1))
/*  724:     */       {
/*  725:     */       case 41: 
/*  726: 749 */         orderByClause();
/*  727: 750 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*  728: 751 */         break;
/*  729:     */       case 1: 
/*  730:     */       case 50: 
/*  731:     */       case 104: 
/*  732:     */         break;
/*  733:     */       default: 
/*  734: 761 */         throw new NoViableAltException(LT(1), getFilename());
/*  735:     */       }
/*  736: 765 */       queryRule_AST = currentAST.root;
/*  737:     */     }
/*  738:     */     catch (RecognitionException ex)
/*  739:     */     {
/*  740: 768 */       reportError(ex);
/*  741: 769 */       recover(ex, _tokenSet_9);
/*  742:     */     }
/*  743: 771 */     this.returnAST = queryRule_AST;
/*  744:     */   }
/*  745:     */   
/*  746:     */   public final void intoClause()
/*  747:     */     throws RecognitionException, TokenStreamException
/*  748:     */   {
/*  749: 776 */     this.returnAST = null;
/*  750: 777 */     ASTPair currentAST = new ASTPair();
/*  751: 778 */     AST intoClause_AST = null;
/*  752:     */     try
/*  753:     */     {
/*  754: 781 */       AST tmp13_AST = null;
/*  755: 782 */       tmp13_AST = this.astFactory.create(LT(1));
/*  756: 783 */       this.astFactory.makeASTRoot(currentAST, tmp13_AST);
/*  757: 784 */       match(30);
/*  758: 785 */       path();
/*  759: 786 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/*  760: 787 */       weakKeywords();
/*  761: 788 */       insertablePropertySpec();
/*  762: 789 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/*  763: 790 */       intoClause_AST = currentAST.root;
/*  764:     */     }
/*  765:     */     catch (RecognitionException ex)
/*  766:     */     {
/*  767: 793 */       reportError(ex);
/*  768: 794 */       recover(ex, _tokenSet_10);
/*  769:     */     }
/*  770: 796 */     this.returnAST = intoClause_AST;
/*  771:     */   }
/*  772:     */   
/*  773:     */   public final void insertablePropertySpec()
/*  774:     */     throws RecognitionException, TokenStreamException
/*  775:     */   {
/*  776: 801 */     this.returnAST = null;
/*  777: 802 */     ASTPair currentAST = new ASTPair();
/*  778: 803 */     AST insertablePropertySpec_AST = null;
/*  779:     */     try
/*  780:     */     {
/*  781: 806 */       match(103);
/*  782: 807 */       primaryExpression();
/*  783: 808 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/*  784: 812 */       while (LA(1) == 101)
/*  785:     */       {
/*  786: 813 */         match(101);
/*  787: 814 */         primaryExpression();
/*  788: 815 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*  789:     */       }
/*  790: 823 */       match(104);
/*  791: 824 */       insertablePropertySpec_AST = currentAST.root;
/*  792:     */       
/*  793:     */ 
/*  794: 827 */       insertablePropertySpec_AST = this.astFactory.make(new ASTArray(2).add(this.astFactory.create(87, "column-spec")).add(insertablePropertySpec_AST));
/*  795:     */       
/*  796: 829 */       currentAST.root = insertablePropertySpec_AST;
/*  797: 830 */       currentAST.child = ((insertablePropertySpec_AST != null) && (insertablePropertySpec_AST.getFirstChild() != null) ? insertablePropertySpec_AST.getFirstChild() : insertablePropertySpec_AST);
/*  798:     */       
/*  799: 832 */       currentAST.advanceChildToEnd();
/*  800: 833 */       insertablePropertySpec_AST = currentAST.root;
/*  801:     */     }
/*  802:     */     catch (RecognitionException ex)
/*  803:     */     {
/*  804: 836 */       reportError(ex);
/*  805: 837 */       recover(ex, _tokenSet_10);
/*  806:     */     }
/*  807: 839 */     this.returnAST = insertablePropertySpec_AST;
/*  808:     */   }
/*  809:     */   
/*  810:     */   public final void primaryExpression()
/*  811:     */     throws RecognitionException, TokenStreamException
/*  812:     */   {
/*  813: 844 */     this.returnAST = null;
/*  814: 845 */     ASTPair currentAST = new ASTPair();
/*  815: 846 */     AST primaryExpression_AST = null;
/*  816:     */     try
/*  817:     */     {
/*  818: 849 */       switch (LA(1))
/*  819:     */       {
/*  820:     */       case 9: 
/*  821:     */       case 12: 
/*  822:     */       case 17: 
/*  823:     */       case 27: 
/*  824:     */       case 35: 
/*  825:     */       case 36: 
/*  826:     */       case 48: 
/*  827:     */       case 126: 
/*  828: 859 */         identPrimary();
/*  829: 860 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*  830: 862 */         if ((LA(1) == 15) && (LA(2) == 11))
/*  831:     */         {
/*  832: 863 */           AST tmp17_AST = null;
/*  833: 864 */           tmp17_AST = this.astFactory.create(LT(1));
/*  834: 865 */           this.astFactory.makeASTRoot(currentAST, tmp17_AST);
/*  835: 866 */           match(15);
/*  836: 867 */           AST tmp18_AST = null;
/*  837: 868 */           tmp18_AST = this.astFactory.create(LT(1));
/*  838: 869 */           this.astFactory.addASTChild(currentAST, tmp18_AST);
/*  839: 870 */           match(11);
/*  840:     */         }
/*  841: 872 */         else if ((!_tokenSet_11.member(LA(1))) || (!_tokenSet_12.member(LA(2))))
/*  842:     */         {
/*  843: 875 */           throw new NoViableAltException(LT(1), getFilename());
/*  844:     */         }
/*  845: 879 */         primaryExpression_AST = currentAST.root;
/*  846: 880 */         break;
/*  847:     */       case 20: 
/*  848:     */       case 39: 
/*  849:     */       case 49: 
/*  850:     */       case 62: 
/*  851:     */       case 95: 
/*  852:     */       case 96: 
/*  853:     */       case 97: 
/*  854:     */       case 98: 
/*  855:     */       case 99: 
/*  856:     */       case 124: 
/*  857:     */       case 125: 
/*  858: 894 */         constant();
/*  859: 895 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*  860: 896 */         primaryExpression_AST = currentAST.root;
/*  861: 897 */         break;
/*  862:     */       case 122: 
/*  863:     */       case 123: 
/*  864: 902 */         parameter();
/*  865: 903 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*  866: 904 */         primaryExpression_AST = currentAST.root;
/*  867: 905 */         break;
/*  868:     */       case 103: 
/*  869: 909 */         match(103);
/*  870: 911 */         switch (LA(1))
/*  871:     */         {
/*  872:     */         case 4: 
/*  873:     */         case 5: 
/*  874:     */         case 9: 
/*  875:     */         case 12: 
/*  876:     */         case 17: 
/*  877:     */         case 19: 
/*  878:     */         case 20: 
/*  879:     */         case 27: 
/*  880:     */         case 35: 
/*  881:     */         case 36: 
/*  882:     */         case 38: 
/*  883:     */         case 39: 
/*  884:     */         case 47: 
/*  885:     */         case 48: 
/*  886:     */         case 49: 
/*  887:     */         case 54: 
/*  888:     */         case 62: 
/*  889:     */         case 95: 
/*  890:     */         case 96: 
/*  891:     */         case 97: 
/*  892:     */         case 98: 
/*  893:     */         case 99: 
/*  894:     */         case 103: 
/*  895:     */         case 115: 
/*  896:     */         case 116: 
/*  897:     */         case 122: 
/*  898:     */         case 123: 
/*  899:     */         case 124: 
/*  900:     */         case 125: 
/*  901:     */         case 126: 
/*  902: 943 */           expressionOrVector();
/*  903: 944 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/*  904: 945 */           break;
/*  905:     */         case 22: 
/*  906:     */         case 24: 
/*  907:     */         case 41: 
/*  908:     */         case 45: 
/*  909:     */         case 50: 
/*  910:     */         case 53: 
/*  911:     */         case 104: 
/*  912: 955 */           subQuery();
/*  913: 956 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/*  914: 957 */           break;
/*  915:     */         case 6: 
/*  916:     */         case 7: 
/*  917:     */         case 8: 
/*  918:     */         case 10: 
/*  919:     */         case 11: 
/*  920:     */         case 13: 
/*  921:     */         case 14: 
/*  922:     */         case 15: 
/*  923:     */         case 16: 
/*  924:     */         case 18: 
/*  925:     */         case 21: 
/*  926:     */         case 23: 
/*  927:     */         case 25: 
/*  928:     */         case 26: 
/*  929:     */         case 28: 
/*  930:     */         case 29: 
/*  931:     */         case 30: 
/*  932:     */         case 31: 
/*  933:     */         case 32: 
/*  934:     */         case 33: 
/*  935:     */         case 34: 
/*  936:     */         case 37: 
/*  937:     */         case 40: 
/*  938:     */         case 42: 
/*  939:     */         case 43: 
/*  940:     */         case 44: 
/*  941:     */         case 46: 
/*  942:     */         case 51: 
/*  943:     */         case 52: 
/*  944:     */         case 55: 
/*  945:     */         case 56: 
/*  946:     */         case 57: 
/*  947:     */         case 58: 
/*  948:     */         case 59: 
/*  949:     */         case 60: 
/*  950:     */         case 61: 
/*  951:     */         case 63: 
/*  952:     */         case 64: 
/*  953:     */         case 65: 
/*  954:     */         case 66: 
/*  955:     */         case 67: 
/*  956:     */         case 68: 
/*  957:     */         case 69: 
/*  958:     */         case 70: 
/*  959:     */         case 71: 
/*  960:     */         case 72: 
/*  961:     */         case 73: 
/*  962:     */         case 74: 
/*  963:     */         case 75: 
/*  964:     */         case 76: 
/*  965:     */         case 77: 
/*  966:     */         case 78: 
/*  967:     */         case 79: 
/*  968:     */         case 80: 
/*  969:     */         case 81: 
/*  970:     */         case 82: 
/*  971:     */         case 83: 
/*  972:     */         case 84: 
/*  973:     */         case 85: 
/*  974:     */         case 86: 
/*  975:     */         case 87: 
/*  976:     */         case 88: 
/*  977:     */         case 89: 
/*  978:     */         case 90: 
/*  979:     */         case 91: 
/*  980:     */         case 92: 
/*  981:     */         case 93: 
/*  982:     */         case 94: 
/*  983:     */         case 100: 
/*  984:     */         case 101: 
/*  985:     */         case 102: 
/*  986:     */         case 105: 
/*  987:     */         case 106: 
/*  988:     */         case 107: 
/*  989:     */         case 108: 
/*  990:     */         case 109: 
/*  991:     */         case 110: 
/*  992:     */         case 111: 
/*  993:     */         case 112: 
/*  994:     */         case 113: 
/*  995:     */         case 114: 
/*  996:     */         case 117: 
/*  997:     */         case 118: 
/*  998:     */         case 119: 
/*  999:     */         case 120: 
/* 1000:     */         case 121: 
/* 1001:     */         default: 
/* 1002: 961 */           throw new NoViableAltException(LT(1), getFilename());
/* 1003:     */         }
/* 1004: 965 */         match(104);
/* 1005: 966 */         primaryExpression_AST = currentAST.root;
/* 1006: 967 */         break;
/* 1007:     */       default: 
/* 1008: 971 */         throw new NoViableAltException(LT(1), getFilename());
/* 1009:     */       }
/* 1010:     */     }
/* 1011:     */     catch (RecognitionException ex)
/* 1012:     */     {
/* 1013: 976 */       reportError(ex);
/* 1014: 977 */       recover(ex, _tokenSet_11);
/* 1015:     */     }
/* 1016: 979 */     this.returnAST = primaryExpression_AST;
/* 1017:     */   }
/* 1018:     */   
/* 1019:     */   public final void union()
/* 1020:     */     throws RecognitionException, TokenStreamException
/* 1021:     */   {
/* 1022: 984 */     this.returnAST = null;
/* 1023: 985 */     ASTPair currentAST = new ASTPair();
/* 1024: 986 */     AST union_AST = null;
/* 1025:     */     try
/* 1026:     */     {
/* 1027: 989 */       queryRule();
/* 1028: 990 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1029: 994 */       while (LA(1) == 50)
/* 1030:     */       {
/* 1031: 995 */         AST tmp21_AST = null;
/* 1032: 996 */         tmp21_AST = this.astFactory.create(LT(1));
/* 1033: 997 */         this.astFactory.addASTChild(currentAST, tmp21_AST);
/* 1034: 998 */         match(50);
/* 1035: 999 */         queryRule();
/* 1036:1000 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1037:     */       }
/* 1038:1008 */       union_AST = currentAST.root;
/* 1039:     */     }
/* 1040:     */     catch (RecognitionException ex)
/* 1041:     */     {
/* 1042:1011 */       reportError(ex);
/* 1043:1012 */       recover(ex, _tokenSet_13);
/* 1044:     */     }
/* 1045:1014 */     this.returnAST = union_AST;
/* 1046:     */   }
/* 1047:     */   
/* 1048:     */   public final void selectFrom()
/* 1049:     */     throws RecognitionException, TokenStreamException
/* 1050:     */   {
/* 1051:1019 */     this.returnAST = null;
/* 1052:1020 */     ASTPair currentAST = new ASTPair();
/* 1053:1021 */     AST selectFrom_AST = null;
/* 1054:1022 */     AST s_AST = null;
/* 1055:1023 */     AST f_AST = null;
/* 1056:     */     try
/* 1057:     */     {
/* 1058:1027 */       switch (LA(1))
/* 1059:     */       {
/* 1060:     */       case 45: 
/* 1061:1030 */         selectClause();
/* 1062:1031 */         s_AST = this.returnAST;
/* 1063:1032 */         break;
/* 1064:     */       case 1: 
/* 1065:     */       case 22: 
/* 1066:     */       case 24: 
/* 1067:     */       case 41: 
/* 1068:     */       case 50: 
/* 1069:     */       case 53: 
/* 1070:     */       case 104: 
/* 1071:     */         break;
/* 1072:     */       default: 
/* 1073:1046 */         throw new NoViableAltException(LT(1), getFilename());
/* 1074:     */       }
/* 1075:1051 */       switch (LA(1))
/* 1076:     */       {
/* 1077:     */       case 22: 
/* 1078:1054 */         fromClause();
/* 1079:1055 */         f_AST = this.returnAST;
/* 1080:1056 */         break;
/* 1081:     */       case 1: 
/* 1082:     */       case 24: 
/* 1083:     */       case 41: 
/* 1084:     */       case 50: 
/* 1085:     */       case 53: 
/* 1086:     */       case 104: 
/* 1087:     */         break;
/* 1088:     */       default: 
/* 1089:1069 */         throw new NoViableAltException(LT(1), getFilename());
/* 1090:     */       }
/* 1091:1073 */       selectFrom_AST = currentAST.root;
/* 1092:1077 */       if (f_AST == null) {
/* 1093:1078 */         if (this.filter) {
/* 1094:1079 */           f_AST = this.astFactory.make(new ASTArray(1).add(this.astFactory.create(22, "{filter-implied FROM}")));
/* 1095:     */         } else {
/* 1096:1082 */           throw new SemanticException("FROM expected (non-filter queries must contain a FROM clause)");
/* 1097:     */         }
/* 1098:     */       }
/* 1099:1088 */       selectFrom_AST = this.astFactory.make(new ASTArray(3).add(this.astFactory.create(89, "SELECT_FROM")).add(f_AST).add(s_AST));
/* 1100:     */       
/* 1101:1090 */       currentAST.root = selectFrom_AST;
/* 1102:1091 */       currentAST.child = ((selectFrom_AST != null) && (selectFrom_AST.getFirstChild() != null) ? selectFrom_AST.getFirstChild() : selectFrom_AST);
/* 1103:     */       
/* 1104:1093 */       currentAST.advanceChildToEnd();
/* 1105:     */     }
/* 1106:     */     catch (RecognitionException ex)
/* 1107:     */     {
/* 1108:1096 */       reportError(ex);
/* 1109:1097 */       recover(ex, _tokenSet_14);
/* 1110:     */     }
/* 1111:1099 */     this.returnAST = selectFrom_AST;
/* 1112:     */   }
/* 1113:     */   
/* 1114:     */   public final void groupByClause()
/* 1115:     */     throws RecognitionException, TokenStreamException
/* 1116:     */   {
/* 1117:1104 */     this.returnAST = null;
/* 1118:1105 */     ASTPair currentAST = new ASTPair();
/* 1119:1106 */     AST groupByClause_AST = null;
/* 1120:     */     try
/* 1121:     */     {
/* 1122:1109 */       AST tmp22_AST = null;
/* 1123:1110 */       tmp22_AST = this.astFactory.create(LT(1));
/* 1124:1111 */       this.astFactory.makeASTRoot(currentAST, tmp22_AST);
/* 1125:1112 */       match(24);
/* 1126:1113 */       match(105);
/* 1127:1114 */       expression();
/* 1128:1115 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1129:1119 */       while (LA(1) == 101)
/* 1130:     */       {
/* 1131:1120 */         match(101);
/* 1132:1121 */         expression();
/* 1133:1122 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1134:     */       }
/* 1135:1131 */       switch (LA(1))
/* 1136:     */       {
/* 1137:     */       case 25: 
/* 1138:1134 */         havingClause();
/* 1139:1135 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1140:1136 */         break;
/* 1141:     */       case 1: 
/* 1142:     */       case 41: 
/* 1143:     */       case 50: 
/* 1144:     */       case 104: 
/* 1145:     */         break;
/* 1146:     */       default: 
/* 1147:1147 */         throw new NoViableAltException(LT(1), getFilename());
/* 1148:     */       }
/* 1149:1151 */       groupByClause_AST = currentAST.root;
/* 1150:     */     }
/* 1151:     */     catch (RecognitionException ex)
/* 1152:     */     {
/* 1153:1154 */       reportError(ex);
/* 1154:1155 */       recover(ex, _tokenSet_15);
/* 1155:     */     }
/* 1156:1157 */     this.returnAST = groupByClause_AST;
/* 1157:     */   }
/* 1158:     */   
/* 1159:     */   public final void orderByClause()
/* 1160:     */     throws RecognitionException, TokenStreamException
/* 1161:     */   {
/* 1162:1162 */     this.returnAST = null;
/* 1163:1163 */     ASTPair currentAST = new ASTPair();
/* 1164:1164 */     AST orderByClause_AST = null;
/* 1165:     */     try
/* 1166:     */     {
/* 1167:1167 */       AST tmp25_AST = null;
/* 1168:1168 */       tmp25_AST = this.astFactory.create(LT(1));
/* 1169:1169 */       this.astFactory.makeASTRoot(currentAST, tmp25_AST);
/* 1170:1170 */       match(41);
/* 1171:1171 */       match(105);
/* 1172:1172 */       orderElement();
/* 1173:1173 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1174:1177 */       while (LA(1) == 101)
/* 1175:     */       {
/* 1176:1178 */         match(101);
/* 1177:1179 */         orderElement();
/* 1178:1180 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1179:     */       }
/* 1180:1188 */       orderByClause_AST = currentAST.root;
/* 1181:     */     }
/* 1182:     */     catch (RecognitionException ex)
/* 1183:     */     {
/* 1184:1191 */       reportError(ex);
/* 1185:1192 */       recover(ex, _tokenSet_9);
/* 1186:     */     }
/* 1187:1194 */     this.returnAST = orderByClause_AST;
/* 1188:     */   }
/* 1189:     */   
/* 1190:     */   public final void selectClause()
/* 1191:     */     throws RecognitionException, TokenStreamException
/* 1192:     */   {
/* 1193:1199 */     this.returnAST = null;
/* 1194:1200 */     ASTPair currentAST = new ASTPair();
/* 1195:1201 */     AST selectClause_AST = null;
/* 1196:     */     try
/* 1197:     */     {
/* 1198:1204 */       AST tmp28_AST = null;
/* 1199:1205 */       tmp28_AST = this.astFactory.create(LT(1));
/* 1200:1206 */       this.astFactory.makeASTRoot(currentAST, tmp28_AST);
/* 1201:1207 */       match(45);
/* 1202:1208 */       weakKeywords();
/* 1203:1210 */       switch (LA(1))
/* 1204:     */       {
/* 1205:     */       case 16: 
/* 1206:1213 */         AST tmp29_AST = null;
/* 1207:1214 */         tmp29_AST = this.astFactory.create(LT(1));
/* 1208:1215 */         this.astFactory.addASTChild(currentAST, tmp29_AST);
/* 1209:1216 */         match(16);
/* 1210:1217 */         break;
/* 1211:     */       case 4: 
/* 1212:     */       case 5: 
/* 1213:     */       case 9: 
/* 1214:     */       case 12: 
/* 1215:     */       case 17: 
/* 1216:     */       case 19: 
/* 1217:     */       case 20: 
/* 1218:     */       case 27: 
/* 1219:     */       case 35: 
/* 1220:     */       case 36: 
/* 1221:     */       case 37: 
/* 1222:     */       case 38: 
/* 1223:     */       case 39: 
/* 1224:     */       case 47: 
/* 1225:     */       case 48: 
/* 1226:     */       case 49: 
/* 1227:     */       case 54: 
/* 1228:     */       case 62: 
/* 1229:     */       case 65: 
/* 1230:     */       case 95: 
/* 1231:     */       case 96: 
/* 1232:     */       case 97: 
/* 1233:     */       case 98: 
/* 1234:     */       case 99: 
/* 1235:     */       case 103: 
/* 1236:     */       case 115: 
/* 1237:     */       case 116: 
/* 1238:     */       case 122: 
/* 1239:     */       case 123: 
/* 1240:     */       case 124: 
/* 1241:     */       case 125: 
/* 1242:     */       case 126: 
/* 1243:     */         break;
/* 1244:     */       case 6: 
/* 1245:     */       case 7: 
/* 1246:     */       case 8: 
/* 1247:     */       case 10: 
/* 1248:     */       case 11: 
/* 1249:     */       case 13: 
/* 1250:     */       case 14: 
/* 1251:     */       case 15: 
/* 1252:     */       case 18: 
/* 1253:     */       case 21: 
/* 1254:     */       case 22: 
/* 1255:     */       case 23: 
/* 1256:     */       case 24: 
/* 1257:     */       case 25: 
/* 1258:     */       case 26: 
/* 1259:     */       case 28: 
/* 1260:     */       case 29: 
/* 1261:     */       case 30: 
/* 1262:     */       case 31: 
/* 1263:     */       case 32: 
/* 1264:     */       case 33: 
/* 1265:     */       case 34: 
/* 1266:     */       case 40: 
/* 1267:     */       case 41: 
/* 1268:     */       case 42: 
/* 1269:     */       case 43: 
/* 1270:     */       case 44: 
/* 1271:     */       case 45: 
/* 1272:     */       case 46: 
/* 1273:     */       case 50: 
/* 1274:     */       case 51: 
/* 1275:     */       case 52: 
/* 1276:     */       case 53: 
/* 1277:     */       case 55: 
/* 1278:     */       case 56: 
/* 1279:     */       case 57: 
/* 1280:     */       case 58: 
/* 1281:     */       case 59: 
/* 1282:     */       case 60: 
/* 1283:     */       case 61: 
/* 1284:     */       case 63: 
/* 1285:     */       case 64: 
/* 1286:     */       case 66: 
/* 1287:     */       case 67: 
/* 1288:     */       case 68: 
/* 1289:     */       case 69: 
/* 1290:     */       case 70: 
/* 1291:     */       case 71: 
/* 1292:     */       case 72: 
/* 1293:     */       case 73: 
/* 1294:     */       case 74: 
/* 1295:     */       case 75: 
/* 1296:     */       case 76: 
/* 1297:     */       case 77: 
/* 1298:     */       case 78: 
/* 1299:     */       case 79: 
/* 1300:     */       case 80: 
/* 1301:     */       case 81: 
/* 1302:     */       case 82: 
/* 1303:     */       case 83: 
/* 1304:     */       case 84: 
/* 1305:     */       case 85: 
/* 1306:     */       case 86: 
/* 1307:     */       case 87: 
/* 1308:     */       case 88: 
/* 1309:     */       case 89: 
/* 1310:     */       case 90: 
/* 1311:     */       case 91: 
/* 1312:     */       case 92: 
/* 1313:     */       case 93: 
/* 1314:     */       case 94: 
/* 1315:     */       case 100: 
/* 1316:     */       case 101: 
/* 1317:     */       case 102: 
/* 1318:     */       case 104: 
/* 1319:     */       case 105: 
/* 1320:     */       case 106: 
/* 1321:     */       case 107: 
/* 1322:     */       case 108: 
/* 1323:     */       case 109: 
/* 1324:     */       case 110: 
/* 1325:     */       case 111: 
/* 1326:     */       case 112: 
/* 1327:     */       case 113: 
/* 1328:     */       case 114: 
/* 1329:     */       case 117: 
/* 1330:     */       case 118: 
/* 1331:     */       case 119: 
/* 1332:     */       case 120: 
/* 1333:     */       case 121: 
/* 1334:     */       default: 
/* 1335:1256 */         throw new NoViableAltException(LT(1), getFilename());
/* 1336:     */       }
/* 1337:1261 */       switch (LA(1))
/* 1338:     */       {
/* 1339:     */       case 4: 
/* 1340:     */       case 5: 
/* 1341:     */       case 9: 
/* 1342:     */       case 12: 
/* 1343:     */       case 17: 
/* 1344:     */       case 19: 
/* 1345:     */       case 20: 
/* 1346:     */       case 27: 
/* 1347:     */       case 35: 
/* 1348:     */       case 36: 
/* 1349:     */       case 38: 
/* 1350:     */       case 39: 
/* 1351:     */       case 47: 
/* 1352:     */       case 48: 
/* 1353:     */       case 49: 
/* 1354:     */       case 54: 
/* 1355:     */       case 62: 
/* 1356:     */       case 95: 
/* 1357:     */       case 96: 
/* 1358:     */       case 97: 
/* 1359:     */       case 98: 
/* 1360:     */       case 99: 
/* 1361:     */       case 103: 
/* 1362:     */       case 115: 
/* 1363:     */       case 116: 
/* 1364:     */       case 122: 
/* 1365:     */       case 123: 
/* 1366:     */       case 124: 
/* 1367:     */       case 125: 
/* 1368:     */       case 126: 
/* 1369:1293 */         selectedPropertiesList();
/* 1370:1294 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1371:1295 */         break;
/* 1372:     */       case 37: 
/* 1373:1299 */         newExpression();
/* 1374:1300 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1375:1301 */         break;
/* 1376:     */       case 65: 
/* 1377:1305 */         selectObject();
/* 1378:1306 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1379:1307 */         break;
/* 1380:     */       case 6: 
/* 1381:     */       case 7: 
/* 1382:     */       case 8: 
/* 1383:     */       case 10: 
/* 1384:     */       case 11: 
/* 1385:     */       case 13: 
/* 1386:     */       case 14: 
/* 1387:     */       case 15: 
/* 1388:     */       case 16: 
/* 1389:     */       case 18: 
/* 1390:     */       case 21: 
/* 1391:     */       case 22: 
/* 1392:     */       case 23: 
/* 1393:     */       case 24: 
/* 1394:     */       case 25: 
/* 1395:     */       case 26: 
/* 1396:     */       case 28: 
/* 1397:     */       case 29: 
/* 1398:     */       case 30: 
/* 1399:     */       case 31: 
/* 1400:     */       case 32: 
/* 1401:     */       case 33: 
/* 1402:     */       case 34: 
/* 1403:     */       case 40: 
/* 1404:     */       case 41: 
/* 1405:     */       case 42: 
/* 1406:     */       case 43: 
/* 1407:     */       case 44: 
/* 1408:     */       case 45: 
/* 1409:     */       case 46: 
/* 1410:     */       case 50: 
/* 1411:     */       case 51: 
/* 1412:     */       case 52: 
/* 1413:     */       case 53: 
/* 1414:     */       case 55: 
/* 1415:     */       case 56: 
/* 1416:     */       case 57: 
/* 1417:     */       case 58: 
/* 1418:     */       case 59: 
/* 1419:     */       case 60: 
/* 1420:     */       case 61: 
/* 1421:     */       case 63: 
/* 1422:     */       case 64: 
/* 1423:     */       case 66: 
/* 1424:     */       case 67: 
/* 1425:     */       case 68: 
/* 1426:     */       case 69: 
/* 1427:     */       case 70: 
/* 1428:     */       case 71: 
/* 1429:     */       case 72: 
/* 1430:     */       case 73: 
/* 1431:     */       case 74: 
/* 1432:     */       case 75: 
/* 1433:     */       case 76: 
/* 1434:     */       case 77: 
/* 1435:     */       case 78: 
/* 1436:     */       case 79: 
/* 1437:     */       case 80: 
/* 1438:     */       case 81: 
/* 1439:     */       case 82: 
/* 1440:     */       case 83: 
/* 1441:     */       case 84: 
/* 1442:     */       case 85: 
/* 1443:     */       case 86: 
/* 1444:     */       case 87: 
/* 1445:     */       case 88: 
/* 1446:     */       case 89: 
/* 1447:     */       case 90: 
/* 1448:     */       case 91: 
/* 1449:     */       case 92: 
/* 1450:     */       case 93: 
/* 1451:     */       case 94: 
/* 1452:     */       case 100: 
/* 1453:     */       case 101: 
/* 1454:     */       case 102: 
/* 1455:     */       case 104: 
/* 1456:     */       case 105: 
/* 1457:     */       case 106: 
/* 1458:     */       case 107: 
/* 1459:     */       case 108: 
/* 1460:     */       case 109: 
/* 1461:     */       case 110: 
/* 1462:     */       case 111: 
/* 1463:     */       case 112: 
/* 1464:     */       case 113: 
/* 1465:     */       case 114: 
/* 1466:     */       case 117: 
/* 1467:     */       case 118: 
/* 1468:     */       case 119: 
/* 1469:     */       case 120: 
/* 1470:     */       case 121: 
/* 1471:     */       default: 
/* 1472:1311 */         throw new NoViableAltException(LT(1), getFilename());
/* 1473:     */       }
/* 1474:1315 */       selectClause_AST = currentAST.root;
/* 1475:     */     }
/* 1476:     */     catch (RecognitionException ex)
/* 1477:     */     {
/* 1478:1318 */       reportError(ex);
/* 1479:1319 */       recover(ex, _tokenSet_16);
/* 1480:     */     }
/* 1481:1321 */     this.returnAST = selectClause_AST;
/* 1482:     */   }
/* 1483:     */   
/* 1484:     */   public final void fromClause()
/* 1485:     */     throws RecognitionException, TokenStreamException
/* 1486:     */   {
/* 1487:1326 */     this.returnAST = null;
/* 1488:1327 */     ASTPair currentAST = new ASTPair();
/* 1489:1328 */     AST fromClause_AST = null;
/* 1490:     */     try
/* 1491:     */     {
/* 1492:1331 */       AST tmp30_AST = null;
/* 1493:1332 */       tmp30_AST = this.astFactory.create(LT(1));
/* 1494:1333 */       this.astFactory.makeASTRoot(currentAST, tmp30_AST);
/* 1495:1334 */       match(22);
/* 1496:1335 */       weakKeywords();
/* 1497:1336 */       fromRange();
/* 1498:1337 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1499:     */       for (;;)
/* 1500:     */       {
/* 1501:1341 */         switch (LA(1))
/* 1502:     */         {
/* 1503:     */         case 23: 
/* 1504:     */         case 28: 
/* 1505:     */         case 32: 
/* 1506:     */         case 33: 
/* 1507:     */         case 44: 
/* 1508:1348 */           fromJoin();
/* 1509:1349 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1510:1350 */           break;
/* 1511:     */         case 101: 
/* 1512:1354 */           match(101);
/* 1513:1355 */           weakKeywords();
/* 1514:1356 */           fromRange();
/* 1515:1357 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1516:     */         }
/* 1517:     */       }
/* 1518:1367 */       fromClause_AST = currentAST.root;
/* 1519:     */     }
/* 1520:     */     catch (RecognitionException ex)
/* 1521:     */     {
/* 1522:1370 */       reportError(ex);
/* 1523:1371 */       recover(ex, _tokenSet_14);
/* 1524:     */     }
/* 1525:1373 */     this.returnAST = fromClause_AST;
/* 1526:     */   }
/* 1527:     */   
/* 1528:     */   public final void selectedPropertiesList()
/* 1529:     */     throws RecognitionException, TokenStreamException
/* 1530:     */   {
/* 1531:1378 */     this.returnAST = null;
/* 1532:1379 */     ASTPair currentAST = new ASTPair();
/* 1533:1380 */     AST selectedPropertiesList_AST = null;
/* 1534:     */     try
/* 1535:     */     {
/* 1536:1383 */       aliasedExpression();
/* 1537:1384 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1538:1388 */       while (LA(1) == 101)
/* 1539:     */       {
/* 1540:1389 */         match(101);
/* 1541:1390 */         aliasedExpression();
/* 1542:1391 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1543:     */       }
/* 1544:1399 */       selectedPropertiesList_AST = currentAST.root;
/* 1545:     */     }
/* 1546:     */     catch (RecognitionException ex)
/* 1547:     */     {
/* 1548:1402 */       reportError(ex);
/* 1549:1403 */       recover(ex, _tokenSet_16);
/* 1550:     */     }
/* 1551:1405 */     this.returnAST = selectedPropertiesList_AST;
/* 1552:     */   }
/* 1553:     */   
/* 1554:     */   public final void newExpression()
/* 1555:     */     throws RecognitionException, TokenStreamException
/* 1556:     */   {
/* 1557:1410 */     this.returnAST = null;
/* 1558:1411 */     ASTPair currentAST = new ASTPair();
/* 1559:1412 */     AST newExpression_AST = null;
/* 1560:1413 */     Token op = null;
/* 1561:1414 */     AST op_AST = null;
/* 1562:     */     try
/* 1563:     */     {
/* 1564:1418 */       match(37);
/* 1565:1419 */       path();
/* 1566:1420 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1567:     */       
/* 1568:1422 */       op = LT(1);
/* 1569:1423 */       op_AST = this.astFactory.create(op);
/* 1570:1424 */       this.astFactory.makeASTRoot(currentAST, op_AST);
/* 1571:1425 */       match(103);
/* 1572:1426 */       op_AST.setType(73);
/* 1573:1427 */       selectedPropertiesList();
/* 1574:1428 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1575:1429 */       match(104);
/* 1576:1430 */       newExpression_AST = currentAST.root;
/* 1577:     */     }
/* 1578:     */     catch (RecognitionException ex)
/* 1579:     */     {
/* 1580:1433 */       reportError(ex);
/* 1581:1434 */       recover(ex, _tokenSet_16);
/* 1582:     */     }
/* 1583:1436 */     this.returnAST = newExpression_AST;
/* 1584:     */   }
/* 1585:     */   
/* 1586:     */   public final void selectObject()
/* 1587:     */     throws RecognitionException, TokenStreamException
/* 1588:     */   {
/* 1589:1441 */     this.returnAST = null;
/* 1590:1442 */     ASTPair currentAST = new ASTPair();
/* 1591:1443 */     AST selectObject_AST = null;
/* 1592:     */     try
/* 1593:     */     {
/* 1594:1446 */       AST tmp35_AST = null;
/* 1595:1447 */       tmp35_AST = this.astFactory.create(LT(1));
/* 1596:1448 */       this.astFactory.makeASTRoot(currentAST, tmp35_AST);
/* 1597:1449 */       match(65);
/* 1598:1450 */       match(103);
/* 1599:1451 */       identifier();
/* 1600:1452 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1601:1453 */       match(104);
/* 1602:1454 */       selectObject_AST = currentAST.root;
/* 1603:     */     }
/* 1604:     */     catch (RecognitionException ex)
/* 1605:     */     {
/* 1606:1457 */       reportError(ex);
/* 1607:1458 */       recover(ex, _tokenSet_16);
/* 1608:     */     }
/* 1609:1460 */     this.returnAST = selectObject_AST;
/* 1610:     */   }
/* 1611:     */   
/* 1612:     */   public final void identifier()
/* 1613:     */     throws RecognitionException, TokenStreamException
/* 1614:     */   {
/* 1615:1465 */     this.returnAST = null;
/* 1616:1466 */     ASTPair currentAST = new ASTPair();
/* 1617:1467 */     AST identifier_AST = null;
/* 1618:     */     try
/* 1619:     */     {
/* 1620:1470 */       AST tmp38_AST = null;
/* 1621:1471 */       tmp38_AST = this.astFactory.create(LT(1));
/* 1622:1472 */       this.astFactory.addASTChild(currentAST, tmp38_AST);
/* 1623:1473 */       match(126);
/* 1624:1474 */       identifier_AST = currentAST.root;
/* 1625:     */     }
/* 1626:     */     catch (RecognitionException ex)
/* 1627:     */     {
/* 1628:1478 */       identifier_AST = handleIdentifierError(LT(1), ex);
/* 1629:     */     }
/* 1630:1481 */     this.returnAST = identifier_AST;
/* 1631:     */   }
/* 1632:     */   
/* 1633:     */   public final void fromRange()
/* 1634:     */     throws RecognitionException, TokenStreamException
/* 1635:     */   {
/* 1636:1486 */     this.returnAST = null;
/* 1637:1487 */     ASTPair currentAST = new ASTPair();
/* 1638:1488 */     AST fromRange_AST = null;
/* 1639:     */     try
/* 1640:     */     {
/* 1641:1491 */       if ((LA(1) == 126) && (_tokenSet_17.member(LA(2))))
/* 1642:     */       {
/* 1643:1492 */         fromClassOrOuterQueryPath();
/* 1644:1493 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1645:1494 */         fromRange_AST = currentAST.root;
/* 1646:     */       }
/* 1647:1496 */       else if ((LA(1) == 126) && (LA(2) == 26) && (LA(3) == 11))
/* 1648:     */       {
/* 1649:1497 */         inClassDeclaration();
/* 1650:1498 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1651:1499 */         fromRange_AST = currentAST.root;
/* 1652:     */       }
/* 1653:1501 */       else if (LA(1) == 26)
/* 1654:     */       {
/* 1655:1502 */         inCollectionDeclaration();
/* 1656:1503 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1657:1504 */         fromRange_AST = currentAST.root;
/* 1658:     */       }
/* 1659:1506 */       else if ((LA(1) == 126) && (LA(2) == 26) && (LA(3) == 17))
/* 1660:     */       {
/* 1661:1507 */         inCollectionElementsDeclaration();
/* 1662:1508 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1663:1509 */         fromRange_AST = currentAST.root;
/* 1664:     */       }
/* 1665:     */       else
/* 1666:     */       {
/* 1667:1512 */         throw new NoViableAltException(LT(1), getFilename());
/* 1668:     */       }
/* 1669:     */     }
/* 1670:     */     catch (RecognitionException ex)
/* 1671:     */     {
/* 1672:1517 */       reportError(ex);
/* 1673:1518 */       recover(ex, _tokenSet_18);
/* 1674:     */     }
/* 1675:1520 */     this.returnAST = fromRange_AST;
/* 1676:     */   }
/* 1677:     */   
/* 1678:     */   public final void fromJoin()
/* 1679:     */     throws RecognitionException, TokenStreamException
/* 1680:     */   {
/* 1681:1525 */     this.returnAST = null;
/* 1682:1526 */     ASTPair currentAST = new ASTPair();
/* 1683:1527 */     AST fromJoin_AST = null;
/* 1684:     */     try
/* 1685:     */     {
/* 1686:1531 */       switch (LA(1))
/* 1687:     */       {
/* 1688:     */       case 33: 
/* 1689:     */       case 44: 
/* 1690:1537 */         switch (LA(1))
/* 1691:     */         {
/* 1692:     */         case 33: 
/* 1693:1540 */           AST tmp39_AST = null;
/* 1694:1541 */           tmp39_AST = this.astFactory.create(LT(1));
/* 1695:1542 */           this.astFactory.addASTChild(currentAST, tmp39_AST);
/* 1696:1543 */           match(33);
/* 1697:1544 */           break;
/* 1698:     */         case 44: 
/* 1699:1548 */           AST tmp40_AST = null;
/* 1700:1549 */           tmp40_AST = this.astFactory.create(LT(1));
/* 1701:1550 */           this.astFactory.addASTChild(currentAST, tmp40_AST);
/* 1702:1551 */           match(44);
/* 1703:1552 */           break;
/* 1704:     */         default: 
/* 1705:1556 */           throw new NoViableAltException(LT(1), getFilename());
/* 1706:     */         }
/* 1707:1561 */         switch (LA(1))
/* 1708:     */         {
/* 1709:     */         case 42: 
/* 1710:1564 */           AST tmp41_AST = null;
/* 1711:1565 */           tmp41_AST = this.astFactory.create(LT(1));
/* 1712:1566 */           this.astFactory.addASTChild(currentAST, tmp41_AST);
/* 1713:1567 */           match(42);
/* 1714:1568 */           break;
/* 1715:     */         case 32: 
/* 1716:     */           break;
/* 1717:     */         default: 
/* 1718:1576 */           throw new NoViableAltException(LT(1), getFilename());
/* 1719:     */         }
/* 1720:     */         break;
/* 1721:     */       case 23: 
/* 1722:1585 */         AST tmp42_AST = null;
/* 1723:1586 */         tmp42_AST = this.astFactory.create(LT(1));
/* 1724:1587 */         this.astFactory.addASTChild(currentAST, tmp42_AST);
/* 1725:1588 */         match(23);
/* 1726:1589 */         break;
/* 1727:     */       case 28: 
/* 1728:1593 */         AST tmp43_AST = null;
/* 1729:1594 */         tmp43_AST = this.astFactory.create(LT(1));
/* 1730:1595 */         this.astFactory.addASTChild(currentAST, tmp43_AST);
/* 1731:1596 */         match(28);
/* 1732:1597 */         break;
/* 1733:     */       case 32: 
/* 1734:     */         break;
/* 1735:     */       default: 
/* 1736:1605 */         throw new NoViableAltException(LT(1), getFilename());
/* 1737:     */       }
/* 1738:1609 */       AST tmp44_AST = null;
/* 1739:1610 */       tmp44_AST = this.astFactory.create(LT(1));
/* 1740:1611 */       this.astFactory.makeASTRoot(currentAST, tmp44_AST);
/* 1741:1612 */       match(32);
/* 1742:1614 */       switch (LA(1))
/* 1743:     */       {
/* 1744:     */       case 21: 
/* 1745:1617 */         AST tmp45_AST = null;
/* 1746:1618 */         tmp45_AST = this.astFactory.create(LT(1));
/* 1747:1619 */         this.astFactory.addASTChild(currentAST, tmp45_AST);
/* 1748:1620 */         match(21);
/* 1749:1621 */         break;
/* 1750:     */       case 126: 
/* 1751:     */         break;
/* 1752:     */       default: 
/* 1753:1629 */         throw new NoViableAltException(LT(1), getFilename());
/* 1754:     */       }
/* 1755:1633 */       path();
/* 1756:1634 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1757:1636 */       switch (LA(1))
/* 1758:     */       {
/* 1759:     */       case 7: 
/* 1760:     */       case 126: 
/* 1761:1640 */         asAlias();
/* 1762:1641 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1763:1642 */         break;
/* 1764:     */       case 1: 
/* 1765:     */       case 21: 
/* 1766:     */       case 23: 
/* 1767:     */       case 24: 
/* 1768:     */       case 28: 
/* 1769:     */       case 32: 
/* 1770:     */       case 33: 
/* 1771:     */       case 41: 
/* 1772:     */       case 44: 
/* 1773:     */       case 50: 
/* 1774:     */       case 53: 
/* 1775:     */       case 60: 
/* 1776:     */       case 101: 
/* 1777:     */       case 104: 
/* 1778:     */         break;
/* 1779:     */       default: 
/* 1780:1663 */         throw new NoViableAltException(LT(1), getFilename());
/* 1781:     */       }
/* 1782:1668 */       switch (LA(1))
/* 1783:     */       {
/* 1784:     */       case 21: 
/* 1785:1671 */         propertyFetch();
/* 1786:1672 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1787:1673 */         break;
/* 1788:     */       case 1: 
/* 1789:     */       case 23: 
/* 1790:     */       case 24: 
/* 1791:     */       case 28: 
/* 1792:     */       case 32: 
/* 1793:     */       case 33: 
/* 1794:     */       case 41: 
/* 1795:     */       case 44: 
/* 1796:     */       case 50: 
/* 1797:     */       case 53: 
/* 1798:     */       case 60: 
/* 1799:     */       case 101: 
/* 1800:     */       case 104: 
/* 1801:     */         break;
/* 1802:     */       default: 
/* 1803:1693 */         throw new NoViableAltException(LT(1), getFilename());
/* 1804:     */       }
/* 1805:1698 */       switch (LA(1))
/* 1806:     */       {
/* 1807:     */       case 60: 
/* 1808:1701 */         withClause();
/* 1809:1702 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1810:1703 */         break;
/* 1811:     */       case 1: 
/* 1812:     */       case 23: 
/* 1813:     */       case 24: 
/* 1814:     */       case 28: 
/* 1815:     */       case 32: 
/* 1816:     */       case 33: 
/* 1817:     */       case 41: 
/* 1818:     */       case 44: 
/* 1819:     */       case 50: 
/* 1820:     */       case 53: 
/* 1821:     */       case 101: 
/* 1822:     */       case 104: 
/* 1823:     */         break;
/* 1824:     */       default: 
/* 1825:1722 */         throw new NoViableAltException(LT(1), getFilename());
/* 1826:     */       }
/* 1827:1726 */       fromJoin_AST = currentAST.root;
/* 1828:     */     }
/* 1829:     */     catch (RecognitionException ex)
/* 1830:     */     {
/* 1831:1729 */       reportError(ex);
/* 1832:1730 */       recover(ex, _tokenSet_18);
/* 1833:     */     }
/* 1834:1732 */     this.returnAST = fromJoin_AST;
/* 1835:     */   }
/* 1836:     */   
/* 1837:     */   public final void propertyFetch()
/* 1838:     */     throws RecognitionException, TokenStreamException
/* 1839:     */   {
/* 1840:1737 */     this.returnAST = null;
/* 1841:1738 */     ASTPair currentAST = new ASTPair();
/* 1842:1739 */     AST propertyFetch_AST = null;
/* 1843:     */     try
/* 1844:     */     {
/* 1845:1742 */       AST tmp46_AST = null;
/* 1846:1743 */       tmp46_AST = this.astFactory.create(LT(1));
/* 1847:1744 */       this.astFactory.addASTChild(currentAST, tmp46_AST);
/* 1848:1745 */       match(21);
/* 1849:1746 */       match(4);
/* 1850:1747 */       match(43);
/* 1851:1748 */       propertyFetch_AST = currentAST.root;
/* 1852:     */     }
/* 1853:     */     catch (RecognitionException ex)
/* 1854:     */     {
/* 1855:1751 */       reportError(ex);
/* 1856:1752 */       recover(ex, _tokenSet_19);
/* 1857:     */     }
/* 1858:1754 */     this.returnAST = propertyFetch_AST;
/* 1859:     */   }
/* 1860:     */   
/* 1861:     */   public final void withClause()
/* 1862:     */     throws RecognitionException, TokenStreamException
/* 1863:     */   {
/* 1864:1759 */     this.returnAST = null;
/* 1865:1760 */     ASTPair currentAST = new ASTPair();
/* 1866:1761 */     AST withClause_AST = null;
/* 1867:     */     try
/* 1868:     */     {
/* 1869:1764 */       AST tmp49_AST = null;
/* 1870:1765 */       tmp49_AST = this.astFactory.create(LT(1));
/* 1871:1766 */       this.astFactory.makeASTRoot(currentAST, tmp49_AST);
/* 1872:1767 */       match(60);
/* 1873:1768 */       logicalExpression();
/* 1874:1769 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1875:1770 */       withClause_AST = currentAST.root;
/* 1876:     */     }
/* 1877:     */     catch (RecognitionException ex)
/* 1878:     */     {
/* 1879:1773 */       reportError(ex);
/* 1880:1774 */       recover(ex, _tokenSet_18);
/* 1881:     */     }
/* 1882:1776 */     this.returnAST = withClause_AST;
/* 1883:     */   }
/* 1884:     */   
/* 1885:     */   public final void logicalExpression()
/* 1886:     */     throws RecognitionException, TokenStreamException
/* 1887:     */   {
/* 1888:1781 */     this.returnAST = null;
/* 1889:1782 */     ASTPair currentAST = new ASTPair();
/* 1890:1783 */     AST logicalExpression_AST = null;
/* 1891:     */     try
/* 1892:     */     {
/* 1893:1786 */       expression();
/* 1894:1787 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1895:1788 */       logicalExpression_AST = currentAST.root;
/* 1896:     */     }
/* 1897:     */     catch (RecognitionException ex)
/* 1898:     */     {
/* 1899:1791 */       reportError(ex);
/* 1900:1792 */       recover(ex, _tokenSet_20);
/* 1901:     */     }
/* 1902:1794 */     this.returnAST = logicalExpression_AST;
/* 1903:     */   }
/* 1904:     */   
/* 1905:     */   public final void fromClassOrOuterQueryPath()
/* 1906:     */     throws RecognitionException, TokenStreamException
/* 1907:     */   {
/* 1908:1799 */     this.returnAST = null;
/* 1909:1800 */     ASTPair currentAST = new ASTPair();
/* 1910:1801 */     AST fromClassOrOuterQueryPath_AST = null;
/* 1911:1802 */     AST c_AST = null;
/* 1912:1803 */     AST a_AST = null;
/* 1913:1804 */     AST p_AST = null;
/* 1914:     */     try
/* 1915:     */     {
/* 1916:1807 */       path();
/* 1917:1808 */       c_AST = this.returnAST;
/* 1918:1809 */       weakKeywords();
/* 1919:1811 */       switch (LA(1))
/* 1920:     */       {
/* 1921:     */       case 7: 
/* 1922:     */       case 126: 
/* 1923:1815 */         asAlias();
/* 1924:1816 */         a_AST = this.returnAST;
/* 1925:1817 */         break;
/* 1926:     */       case 1: 
/* 1927:     */       case 21: 
/* 1928:     */       case 23: 
/* 1929:     */       case 24: 
/* 1930:     */       case 28: 
/* 1931:     */       case 32: 
/* 1932:     */       case 33: 
/* 1933:     */       case 41: 
/* 1934:     */       case 44: 
/* 1935:     */       case 50: 
/* 1936:     */       case 53: 
/* 1937:     */       case 101: 
/* 1938:     */       case 104: 
/* 1939:     */         break;
/* 1940:     */       default: 
/* 1941:1837 */         throw new NoViableAltException(LT(1), getFilename());
/* 1942:     */       }
/* 1943:1842 */       switch (LA(1))
/* 1944:     */       {
/* 1945:     */       case 21: 
/* 1946:1845 */         propertyFetch();
/* 1947:1846 */         p_AST = this.returnAST;
/* 1948:1847 */         break;
/* 1949:     */       case 1: 
/* 1950:     */       case 23: 
/* 1951:     */       case 24: 
/* 1952:     */       case 28: 
/* 1953:     */       case 32: 
/* 1954:     */       case 33: 
/* 1955:     */       case 41: 
/* 1956:     */       case 44: 
/* 1957:     */       case 50: 
/* 1958:     */       case 53: 
/* 1959:     */       case 101: 
/* 1960:     */       case 104: 
/* 1961:     */         break;
/* 1962:     */       default: 
/* 1963:1866 */         throw new NoViableAltException(LT(1), getFilename());
/* 1964:     */       }
/* 1965:1870 */       fromClassOrOuterQueryPath_AST = currentAST.root;
/* 1966:     */       
/* 1967:1872 */       fromClassOrOuterQueryPath_AST = this.astFactory.make(new ASTArray(4).add(this.astFactory.create(87, "RANGE")).add(c_AST).add(a_AST).add(p_AST));
/* 1968:     */       
/* 1969:1874 */       currentAST.root = fromClassOrOuterQueryPath_AST;
/* 1970:1875 */       currentAST.child = ((fromClassOrOuterQueryPath_AST != null) && (fromClassOrOuterQueryPath_AST.getFirstChild() != null) ? fromClassOrOuterQueryPath_AST.getFirstChild() : fromClassOrOuterQueryPath_AST);
/* 1971:     */       
/* 1972:1877 */       currentAST.advanceChildToEnd();
/* 1973:     */     }
/* 1974:     */     catch (RecognitionException ex)
/* 1975:     */     {
/* 1976:1880 */       reportError(ex);
/* 1977:1881 */       recover(ex, _tokenSet_18);
/* 1978:     */     }
/* 1979:1883 */     this.returnAST = fromClassOrOuterQueryPath_AST;
/* 1980:     */   }
/* 1981:     */   
/* 1982:     */   public final void inClassDeclaration()
/* 1983:     */     throws RecognitionException, TokenStreamException
/* 1984:     */   {
/* 1985:1888 */     this.returnAST = null;
/* 1986:1889 */     ASTPair currentAST = new ASTPair();
/* 1987:1890 */     AST inClassDeclaration_AST = null;
/* 1988:1891 */     AST a_AST = null;
/* 1989:1892 */     AST c_AST = null;
/* 1990:     */     try
/* 1991:     */     {
/* 1992:1895 */       alias();
/* 1993:1896 */       a_AST = this.returnAST;
/* 1994:1897 */       match(26);
/* 1995:1898 */       match(11);
/* 1996:1899 */       path();
/* 1997:1900 */       c_AST = this.returnAST;
/* 1998:1901 */       inClassDeclaration_AST = currentAST.root;
/* 1999:     */       
/* 2000:1903 */       inClassDeclaration_AST = this.astFactory.make(new ASTArray(3).add(this.astFactory.create(87, "RANGE")).add(c_AST).add(a_AST));
/* 2001:     */       
/* 2002:1905 */       currentAST.root = inClassDeclaration_AST;
/* 2003:1906 */       currentAST.child = ((inClassDeclaration_AST != null) && (inClassDeclaration_AST.getFirstChild() != null) ? inClassDeclaration_AST.getFirstChild() : inClassDeclaration_AST);
/* 2004:     */       
/* 2005:1908 */       currentAST.advanceChildToEnd();
/* 2006:     */     }
/* 2007:     */     catch (RecognitionException ex)
/* 2008:     */     {
/* 2009:1911 */       reportError(ex);
/* 2010:1912 */       recover(ex, _tokenSet_18);
/* 2011:     */     }
/* 2012:1914 */     this.returnAST = inClassDeclaration_AST;
/* 2013:     */   }
/* 2014:     */   
/* 2015:     */   public final void inCollectionDeclaration()
/* 2016:     */     throws RecognitionException, TokenStreamException
/* 2017:     */   {
/* 2018:1919 */     this.returnAST = null;
/* 2019:1920 */     ASTPair currentAST = new ASTPair();
/* 2020:1921 */     AST inCollectionDeclaration_AST = null;
/* 2021:1922 */     AST p_AST = null;
/* 2022:1923 */     AST a_AST = null;
/* 2023:     */     try
/* 2024:     */     {
/* 2025:1926 */       match(26);
/* 2026:1927 */       match(103);
/* 2027:1928 */       path();
/* 2028:1929 */       p_AST = this.returnAST;
/* 2029:1930 */       match(104);
/* 2030:1931 */       asAlias();
/* 2031:1932 */       a_AST = this.returnAST;
/* 2032:1933 */       inCollectionDeclaration_AST = currentAST.root;
/* 2033:     */       
/* 2034:1935 */       inCollectionDeclaration_AST = this.astFactory.make(new ASTArray(4).add(this.astFactory.create(32, "join")).add(this.astFactory.create(28, "inner")).add(p_AST).add(a_AST));
/* 2035:     */       
/* 2036:1937 */       currentAST.root = inCollectionDeclaration_AST;
/* 2037:1938 */       currentAST.child = ((inCollectionDeclaration_AST != null) && (inCollectionDeclaration_AST.getFirstChild() != null) ? inCollectionDeclaration_AST.getFirstChild() : inCollectionDeclaration_AST);
/* 2038:     */       
/* 2039:1940 */       currentAST.advanceChildToEnd();
/* 2040:     */     }
/* 2041:     */     catch (RecognitionException ex)
/* 2042:     */     {
/* 2043:1943 */       reportError(ex);
/* 2044:1944 */       recover(ex, _tokenSet_18);
/* 2045:     */     }
/* 2046:1946 */     this.returnAST = inCollectionDeclaration_AST;
/* 2047:     */   }
/* 2048:     */   
/* 2049:     */   public final void inCollectionElementsDeclaration()
/* 2050:     */     throws RecognitionException, TokenStreamException
/* 2051:     */   {
/* 2052:1951 */     this.returnAST = null;
/* 2053:1952 */     ASTPair currentAST = new ASTPair();
/* 2054:1953 */     AST inCollectionElementsDeclaration_AST = null;
/* 2055:1954 */     AST a_AST = null;
/* 2056:1955 */     AST p_AST = null;
/* 2057:     */     try
/* 2058:     */     {
/* 2059:1958 */       alias();
/* 2060:1959 */       a_AST = this.returnAST;
/* 2061:1960 */       match(26);
/* 2062:1961 */       match(17);
/* 2063:1962 */       match(103);
/* 2064:1963 */       path();
/* 2065:1964 */       p_AST = this.returnAST;
/* 2066:1965 */       match(104);
/* 2067:1966 */       inCollectionElementsDeclaration_AST = currentAST.root;
/* 2068:     */       
/* 2069:1968 */       inCollectionElementsDeclaration_AST = this.astFactory.make(new ASTArray(4).add(this.astFactory.create(32, "join")).add(this.astFactory.create(28, "inner")).add(p_AST).add(a_AST));
/* 2070:     */       
/* 2071:1970 */       currentAST.root = inCollectionElementsDeclaration_AST;
/* 2072:1971 */       currentAST.child = ((inCollectionElementsDeclaration_AST != null) && (inCollectionElementsDeclaration_AST.getFirstChild() != null) ? inCollectionElementsDeclaration_AST.getFirstChild() : inCollectionElementsDeclaration_AST);
/* 2073:     */       
/* 2074:1973 */       currentAST.advanceChildToEnd();
/* 2075:     */     }
/* 2076:     */     catch (RecognitionException ex)
/* 2077:     */     {
/* 2078:1976 */       reportError(ex);
/* 2079:1977 */       recover(ex, _tokenSet_18);
/* 2080:     */     }
/* 2081:1979 */     this.returnAST = inCollectionElementsDeclaration_AST;
/* 2082:     */   }
/* 2083:     */   
/* 2084:     */   public final void alias()
/* 2085:     */     throws RecognitionException, TokenStreamException
/* 2086:     */   {
/* 2087:1984 */     this.returnAST = null;
/* 2088:1985 */     ASTPair currentAST = new ASTPair();
/* 2089:1986 */     AST alias_AST = null;
/* 2090:1987 */     AST a_AST = null;
/* 2091:     */     try
/* 2092:     */     {
/* 2093:1990 */       identifier();
/* 2094:1991 */       a_AST = this.returnAST;
/* 2095:1992 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2096:1993 */       a_AST.setType(72);
/* 2097:1994 */       alias_AST = currentAST.root;
/* 2098:     */     }
/* 2099:     */     catch (RecognitionException ex)
/* 2100:     */     {
/* 2101:1997 */       reportError(ex);
/* 2102:1998 */       recover(ex, _tokenSet_21);
/* 2103:     */     }
/* 2104:2000 */     this.returnAST = alias_AST;
/* 2105:     */   }
/* 2106:     */   
/* 2107:     */   public final void expression()
/* 2108:     */     throws RecognitionException, TokenStreamException
/* 2109:     */   {
/* 2110:2005 */     this.returnAST = null;
/* 2111:2006 */     ASTPair currentAST = new ASTPair();
/* 2112:2007 */     AST expression_AST = null;
/* 2113:     */     try
/* 2114:     */     {
/* 2115:2010 */       logicalOrExpression();
/* 2116:2011 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2117:2012 */       expression_AST = currentAST.root;
/* 2118:     */     }
/* 2119:     */     catch (RecognitionException ex)
/* 2120:     */     {
/* 2121:2015 */       reportError(ex);
/* 2122:2016 */       recover(ex, _tokenSet_22);
/* 2123:     */     }
/* 2124:2018 */     this.returnAST = expression_AST;
/* 2125:     */   }
/* 2126:     */   
/* 2127:     */   public final void havingClause()
/* 2128:     */     throws RecognitionException, TokenStreamException
/* 2129:     */   {
/* 2130:2023 */     this.returnAST = null;
/* 2131:2024 */     ASTPair currentAST = new ASTPair();
/* 2132:2025 */     AST havingClause_AST = null;
/* 2133:     */     try
/* 2134:     */     {
/* 2135:2028 */       AST tmp59_AST = null;
/* 2136:2029 */       tmp59_AST = this.astFactory.create(LT(1));
/* 2137:2030 */       this.astFactory.makeASTRoot(currentAST, tmp59_AST);
/* 2138:2031 */       match(25);
/* 2139:2032 */       logicalExpression();
/* 2140:2033 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2141:2034 */       havingClause_AST = currentAST.root;
/* 2142:     */     }
/* 2143:     */     catch (RecognitionException ex)
/* 2144:     */     {
/* 2145:2037 */       reportError(ex);
/* 2146:2038 */       recover(ex, _tokenSet_15);
/* 2147:     */     }
/* 2148:2040 */     this.returnAST = havingClause_AST;
/* 2149:     */   }
/* 2150:     */   
/* 2151:     */   public final void orderElement()
/* 2152:     */     throws RecognitionException, TokenStreamException
/* 2153:     */   {
/* 2154:2045 */     this.returnAST = null;
/* 2155:2046 */     ASTPair currentAST = new ASTPair();
/* 2156:2047 */     AST orderElement_AST = null;
/* 2157:     */     try
/* 2158:     */     {
/* 2159:2050 */       expression();
/* 2160:2051 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2161:2053 */       switch (LA(1))
/* 2162:     */       {
/* 2163:     */       case 8: 
/* 2164:     */       case 14: 
/* 2165:     */       case 106: 
/* 2166:     */       case 107: 
/* 2167:2059 */         ascendingOrDescending();
/* 2168:2060 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2169:2061 */         break;
/* 2170:     */       case 1: 
/* 2171:     */       case 50: 
/* 2172:     */       case 101: 
/* 2173:     */       case 104: 
/* 2174:     */         break;
/* 2175:     */       default: 
/* 2176:2072 */         throw new NoViableAltException(LT(1), getFilename());
/* 2177:     */       }
/* 2178:2076 */       orderElement_AST = currentAST.root;
/* 2179:     */     }
/* 2180:     */     catch (RecognitionException ex)
/* 2181:     */     {
/* 2182:2079 */       reportError(ex);
/* 2183:2080 */       recover(ex, _tokenSet_23);
/* 2184:     */     }
/* 2185:2082 */     this.returnAST = orderElement_AST;
/* 2186:     */   }
/* 2187:     */   
/* 2188:     */   public final void ascendingOrDescending()
/* 2189:     */     throws RecognitionException, TokenStreamException
/* 2190:     */   {
/* 2191:2087 */     this.returnAST = null;
/* 2192:2088 */     ASTPair currentAST = new ASTPair();
/* 2193:2089 */     AST ascendingOrDescending_AST = null;
/* 2194:     */     try
/* 2195:     */     {
/* 2196:2092 */       switch (LA(1))
/* 2197:     */       {
/* 2198:     */       case 8: 
/* 2199:     */       case 106: 
/* 2200:2097 */         switch (LA(1))
/* 2201:     */         {
/* 2202:     */         case 8: 
/* 2203:2100 */           AST tmp60_AST = null;
/* 2204:2101 */           tmp60_AST = this.astFactory.create(LT(1));
/* 2205:2102 */           this.astFactory.addASTChild(currentAST, tmp60_AST);
/* 2206:2103 */           match(8);
/* 2207:2104 */           break;
/* 2208:     */         case 106: 
/* 2209:2108 */           AST tmp61_AST = null;
/* 2210:2109 */           tmp61_AST = this.astFactory.create(LT(1));
/* 2211:2110 */           this.astFactory.addASTChild(currentAST, tmp61_AST);
/* 2212:2111 */           match(106);
/* 2213:2112 */           break;
/* 2214:     */         default: 
/* 2215:2116 */           throw new NoViableAltException(LT(1), getFilename());
/* 2216:     */         }
/* 2217:2120 */         ascendingOrDescending_AST = currentAST.root;
/* 2218:2121 */         ascendingOrDescending_AST.setType(8);
/* 2219:2122 */         ascendingOrDescending_AST = currentAST.root;
/* 2220:2123 */         break;
/* 2221:     */       case 14: 
/* 2222:     */       case 107: 
/* 2223:2129 */         switch (LA(1))
/* 2224:     */         {
/* 2225:     */         case 14: 
/* 2226:2132 */           AST tmp62_AST = null;
/* 2227:2133 */           tmp62_AST = this.astFactory.create(LT(1));
/* 2228:2134 */           this.astFactory.addASTChild(currentAST, tmp62_AST);
/* 2229:2135 */           match(14);
/* 2230:2136 */           break;
/* 2231:     */         case 107: 
/* 2232:2140 */           AST tmp63_AST = null;
/* 2233:2141 */           tmp63_AST = this.astFactory.create(LT(1));
/* 2234:2142 */           this.astFactory.addASTChild(currentAST, tmp63_AST);
/* 2235:2143 */           match(107);
/* 2236:2144 */           break;
/* 2237:     */         default: 
/* 2238:2148 */           throw new NoViableAltException(LT(1), getFilename());
/* 2239:     */         }
/* 2240:2152 */         ascendingOrDescending_AST = currentAST.root;
/* 2241:2153 */         ascendingOrDescending_AST.setType(14);
/* 2242:2154 */         ascendingOrDescending_AST = currentAST.root;
/* 2243:2155 */         break;
/* 2244:     */       default: 
/* 2245:2159 */         throw new NoViableAltException(LT(1), getFilename());
/* 2246:     */       }
/* 2247:     */     }
/* 2248:     */     catch (RecognitionException ex)
/* 2249:     */     {
/* 2250:2164 */       reportError(ex);
/* 2251:2165 */       recover(ex, _tokenSet_23);
/* 2252:     */     }
/* 2253:2167 */     this.returnAST = ascendingOrDescending_AST;
/* 2254:     */   }
/* 2255:     */   
/* 2256:     */   public final void aliasedExpression()
/* 2257:     */     throws RecognitionException, TokenStreamException
/* 2258:     */   {
/* 2259:2172 */     this.returnAST = null;
/* 2260:2173 */     ASTPair currentAST = new ASTPair();
/* 2261:2174 */     AST aliasedExpression_AST = null;
/* 2262:     */     try
/* 2263:     */     {
/* 2264:2177 */       expression();
/* 2265:2178 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2266:2180 */       switch (LA(1))
/* 2267:     */       {
/* 2268:     */       case 7: 
/* 2269:2183 */         AST tmp64_AST = null;
/* 2270:2184 */         tmp64_AST = this.astFactory.create(LT(1));
/* 2271:2185 */         this.astFactory.makeASTRoot(currentAST, tmp64_AST);
/* 2272:2186 */         match(7);
/* 2273:2187 */         identifier();
/* 2274:2188 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2275:2189 */         break;
/* 2276:     */       case 1: 
/* 2277:     */       case 22: 
/* 2278:     */       case 24: 
/* 2279:     */       case 41: 
/* 2280:     */       case 50: 
/* 2281:     */       case 53: 
/* 2282:     */       case 101: 
/* 2283:     */       case 104: 
/* 2284:     */         break;
/* 2285:     */       default: 
/* 2286:2204 */         throw new NoViableAltException(LT(1), getFilename());
/* 2287:     */       }
/* 2288:2208 */       aliasedExpression_AST = currentAST.root;
/* 2289:     */     }
/* 2290:     */     catch (RecognitionException ex)
/* 2291:     */     {
/* 2292:2211 */       reportError(ex);
/* 2293:2212 */       recover(ex, _tokenSet_24);
/* 2294:     */     }
/* 2295:2214 */     this.returnAST = aliasedExpression_AST;
/* 2296:     */   }
/* 2297:     */   
/* 2298:     */   public final void logicalOrExpression()
/* 2299:     */     throws RecognitionException, TokenStreamException
/* 2300:     */   {
/* 2301:2219 */     this.returnAST = null;
/* 2302:2220 */     ASTPair currentAST = new ASTPair();
/* 2303:2221 */     AST logicalOrExpression_AST = null;
/* 2304:     */     try
/* 2305:     */     {
/* 2306:2224 */       logicalAndExpression();
/* 2307:2225 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2308:2229 */       while (LA(1) == 40)
/* 2309:     */       {
/* 2310:2230 */         AST tmp65_AST = null;
/* 2311:2231 */         tmp65_AST = this.astFactory.create(LT(1));
/* 2312:2232 */         this.astFactory.makeASTRoot(currentAST, tmp65_AST);
/* 2313:2233 */         match(40);
/* 2314:2234 */         logicalAndExpression();
/* 2315:2235 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2316:     */       }
/* 2317:2243 */       logicalOrExpression_AST = currentAST.root;
/* 2318:     */     }
/* 2319:     */     catch (RecognitionException ex)
/* 2320:     */     {
/* 2321:2246 */       reportError(ex);
/* 2322:2247 */       recover(ex, _tokenSet_22);
/* 2323:     */     }
/* 2324:2249 */     this.returnAST = logicalOrExpression_AST;
/* 2325:     */   }
/* 2326:     */   
/* 2327:     */   public final void logicalAndExpression()
/* 2328:     */     throws RecognitionException, TokenStreamException
/* 2329:     */   {
/* 2330:2254 */     this.returnAST = null;
/* 2331:2255 */     ASTPair currentAST = new ASTPair();
/* 2332:2256 */     AST logicalAndExpression_AST = null;
/* 2333:     */     try
/* 2334:     */     {
/* 2335:2259 */       negatedExpression();
/* 2336:2260 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2337:2264 */       while (LA(1) == 6)
/* 2338:     */       {
/* 2339:2265 */         AST tmp66_AST = null;
/* 2340:2266 */         tmp66_AST = this.astFactory.create(LT(1));
/* 2341:2267 */         this.astFactory.makeASTRoot(currentAST, tmp66_AST);
/* 2342:2268 */         match(6);
/* 2343:2269 */         negatedExpression();
/* 2344:2270 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2345:     */       }
/* 2346:2278 */       logicalAndExpression_AST = currentAST.root;
/* 2347:     */     }
/* 2348:     */     catch (RecognitionException ex)
/* 2349:     */     {
/* 2350:2281 */       reportError(ex);
/* 2351:2282 */       recover(ex, _tokenSet_25);
/* 2352:     */     }
/* 2353:2284 */     this.returnAST = logicalAndExpression_AST;
/* 2354:     */   }
/* 2355:     */   
/* 2356:     */   public final void negatedExpression()
/* 2357:     */     throws RecognitionException, TokenStreamException
/* 2358:     */   {
/* 2359:2289 */     this.returnAST = null;
/* 2360:2290 */     ASTPair currentAST = new ASTPair();
/* 2361:2291 */     AST negatedExpression_AST = null;
/* 2362:2292 */     AST x_AST = null;
/* 2363:2293 */     AST y_AST = null;
/* 2364:2294 */     weakKeywords();
/* 2365:     */     try
/* 2366:     */     {
/* 2367:2297 */       switch (LA(1))
/* 2368:     */       {
/* 2369:     */       case 38: 
/* 2370:2300 */         AST tmp67_AST = null;
/* 2371:2301 */         tmp67_AST = this.astFactory.create(LT(1));
/* 2372:2302 */         match(38);
/* 2373:2303 */         negatedExpression();
/* 2374:2304 */         x_AST = this.returnAST;
/* 2375:2305 */         negatedExpression_AST = currentAST.root;
/* 2376:2306 */         negatedExpression_AST = negateNode(x_AST);
/* 2377:2307 */         currentAST.root = negatedExpression_AST;
/* 2378:2308 */         currentAST.child = ((negatedExpression_AST != null) && (negatedExpression_AST.getFirstChild() != null) ? negatedExpression_AST.getFirstChild() : negatedExpression_AST);
/* 2379:     */         
/* 2380:2310 */         currentAST.advanceChildToEnd();
/* 2381:2311 */         break;
/* 2382:     */       case 4: 
/* 2383:     */       case 5: 
/* 2384:     */       case 9: 
/* 2385:     */       case 12: 
/* 2386:     */       case 17: 
/* 2387:     */       case 19: 
/* 2388:     */       case 20: 
/* 2389:     */       case 27: 
/* 2390:     */       case 35: 
/* 2391:     */       case 36: 
/* 2392:     */       case 39: 
/* 2393:     */       case 47: 
/* 2394:     */       case 48: 
/* 2395:     */       case 49: 
/* 2396:     */       case 54: 
/* 2397:     */       case 62: 
/* 2398:     */       case 95: 
/* 2399:     */       case 96: 
/* 2400:     */       case 97: 
/* 2401:     */       case 98: 
/* 2402:     */       case 99: 
/* 2403:     */       case 103: 
/* 2404:     */       case 115: 
/* 2405:     */       case 116: 
/* 2406:     */       case 122: 
/* 2407:     */       case 123: 
/* 2408:     */       case 124: 
/* 2409:     */       case 125: 
/* 2410:     */       case 126: 
/* 2411:2343 */         equalityExpression();
/* 2412:2344 */         y_AST = this.returnAST;
/* 2413:2345 */         negatedExpression_AST = currentAST.root;
/* 2414:2346 */         negatedExpression_AST = y_AST;
/* 2415:2347 */         currentAST.root = negatedExpression_AST;
/* 2416:2348 */         currentAST.child = ((negatedExpression_AST != null) && (negatedExpression_AST.getFirstChild() != null) ? negatedExpression_AST.getFirstChild() : negatedExpression_AST);
/* 2417:     */         
/* 2418:2350 */         currentAST.advanceChildToEnd();
/* 2419:2351 */         break;
/* 2420:     */       case 6: 
/* 2421:     */       case 7: 
/* 2422:     */       case 8: 
/* 2423:     */       case 10: 
/* 2424:     */       case 11: 
/* 2425:     */       case 13: 
/* 2426:     */       case 14: 
/* 2427:     */       case 15: 
/* 2428:     */       case 16: 
/* 2429:     */       case 18: 
/* 2430:     */       case 21: 
/* 2431:     */       case 22: 
/* 2432:     */       case 23: 
/* 2433:     */       case 24: 
/* 2434:     */       case 25: 
/* 2435:     */       case 26: 
/* 2436:     */       case 28: 
/* 2437:     */       case 29: 
/* 2438:     */       case 30: 
/* 2439:     */       case 31: 
/* 2440:     */       case 32: 
/* 2441:     */       case 33: 
/* 2442:     */       case 34: 
/* 2443:     */       case 37: 
/* 2444:     */       case 40: 
/* 2445:     */       case 41: 
/* 2446:     */       case 42: 
/* 2447:     */       case 43: 
/* 2448:     */       case 44: 
/* 2449:     */       case 45: 
/* 2450:     */       case 46: 
/* 2451:     */       case 50: 
/* 2452:     */       case 51: 
/* 2453:     */       case 52: 
/* 2454:     */       case 53: 
/* 2455:     */       case 55: 
/* 2456:     */       case 56: 
/* 2457:     */       case 57: 
/* 2458:     */       case 58: 
/* 2459:     */       case 59: 
/* 2460:     */       case 60: 
/* 2461:     */       case 61: 
/* 2462:     */       case 63: 
/* 2463:     */       case 64: 
/* 2464:     */       case 65: 
/* 2465:     */       case 66: 
/* 2466:     */       case 67: 
/* 2467:     */       case 68: 
/* 2468:     */       case 69: 
/* 2469:     */       case 70: 
/* 2470:     */       case 71: 
/* 2471:     */       case 72: 
/* 2472:     */       case 73: 
/* 2473:     */       case 74: 
/* 2474:     */       case 75: 
/* 2475:     */       case 76: 
/* 2476:     */       case 77: 
/* 2477:     */       case 78: 
/* 2478:     */       case 79: 
/* 2479:     */       case 80: 
/* 2480:     */       case 81: 
/* 2481:     */       case 82: 
/* 2482:     */       case 83: 
/* 2483:     */       case 84: 
/* 2484:     */       case 85: 
/* 2485:     */       case 86: 
/* 2486:     */       case 87: 
/* 2487:     */       case 88: 
/* 2488:     */       case 89: 
/* 2489:     */       case 90: 
/* 2490:     */       case 91: 
/* 2491:     */       case 92: 
/* 2492:     */       case 93: 
/* 2493:     */       case 94: 
/* 2494:     */       case 100: 
/* 2495:     */       case 101: 
/* 2496:     */       case 102: 
/* 2497:     */       case 104: 
/* 2498:     */       case 105: 
/* 2499:     */       case 106: 
/* 2500:     */       case 107: 
/* 2501:     */       case 108: 
/* 2502:     */       case 109: 
/* 2503:     */       case 110: 
/* 2504:     */       case 111: 
/* 2505:     */       case 112: 
/* 2506:     */       case 113: 
/* 2507:     */       case 114: 
/* 2508:     */       case 117: 
/* 2509:     */       case 118: 
/* 2510:     */       case 119: 
/* 2511:     */       case 120: 
/* 2512:     */       case 121: 
/* 2513:     */       default: 
/* 2514:2355 */         throw new NoViableAltException(LT(1), getFilename());
/* 2515:     */       }
/* 2516:     */     }
/* 2517:     */     catch (RecognitionException ex)
/* 2518:     */     {
/* 2519:2360 */       reportError(ex);
/* 2520:2361 */       recover(ex, _tokenSet_26);
/* 2521:     */     }
/* 2522:2363 */     this.returnAST = negatedExpression_AST;
/* 2523:     */   }
/* 2524:     */   
/* 2525:     */   public final void equalityExpression()
/* 2526:     */     throws RecognitionException, TokenStreamException
/* 2527:     */   {
/* 2528:2368 */     this.returnAST = null;
/* 2529:2369 */     ASTPair currentAST = new ASTPair();
/* 2530:2370 */     AST equalityExpression_AST = null;
/* 2531:2371 */     AST x_AST = null;
/* 2532:2372 */     Token is = null;
/* 2533:2373 */     AST is_AST = null;
/* 2534:2374 */     Token ne = null;
/* 2535:2375 */     AST ne_AST = null;
/* 2536:2376 */     AST y_AST = null;
/* 2537:     */     try
/* 2538:     */     {
/* 2539:2379 */       relationalExpression();
/* 2540:2380 */       x_AST = this.returnAST;
/* 2541:2381 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2542:2385 */       while (_tokenSet_27.member(LA(1)))
/* 2543:     */       {
/* 2544:2387 */         switch (LA(1))
/* 2545:     */         {
/* 2546:     */         case 102: 
/* 2547:2390 */           AST tmp68_AST = null;
/* 2548:2391 */           tmp68_AST = this.astFactory.create(LT(1));
/* 2549:2392 */           this.astFactory.makeASTRoot(currentAST, tmp68_AST);
/* 2550:2393 */           match(102);
/* 2551:2394 */           break;
/* 2552:     */         case 31: 
/* 2553:2398 */           is = LT(1);
/* 2554:2399 */           is_AST = this.astFactory.create(is);
/* 2555:2400 */           this.astFactory.makeASTRoot(currentAST, is_AST);
/* 2556:2401 */           match(31);
/* 2557:2402 */           is_AST.setType(102);
/* 2558:2404 */           switch (LA(1))
/* 2559:     */           {
/* 2560:     */           case 38: 
/* 2561:2407 */             match(38);
/* 2562:2408 */             is_AST.setType(108);
/* 2563:2409 */             break;
/* 2564:     */           case 4: 
/* 2565:     */           case 5: 
/* 2566:     */           case 9: 
/* 2567:     */           case 12: 
/* 2568:     */           case 17: 
/* 2569:     */           case 19: 
/* 2570:     */           case 20: 
/* 2571:     */           case 27: 
/* 2572:     */           case 35: 
/* 2573:     */           case 36: 
/* 2574:     */           case 39: 
/* 2575:     */           case 47: 
/* 2576:     */           case 48: 
/* 2577:     */           case 49: 
/* 2578:     */           case 54: 
/* 2579:     */           case 62: 
/* 2580:     */           case 95: 
/* 2581:     */           case 96: 
/* 2582:     */           case 97: 
/* 2583:     */           case 98: 
/* 2584:     */           case 99: 
/* 2585:     */           case 103: 
/* 2586:     */           case 115: 
/* 2587:     */           case 116: 
/* 2588:     */           case 122: 
/* 2589:     */           case 123: 
/* 2590:     */           case 124: 
/* 2591:     */           case 125: 
/* 2592:     */           case 126: 
/* 2593:     */             break;
/* 2594:     */           case 6: 
/* 2595:     */           case 7: 
/* 2596:     */           case 8: 
/* 2597:     */           case 10: 
/* 2598:     */           case 11: 
/* 2599:     */           case 13: 
/* 2600:     */           case 14: 
/* 2601:     */           case 15: 
/* 2602:     */           case 16: 
/* 2603:     */           case 18: 
/* 2604:     */           case 21: 
/* 2605:     */           case 22: 
/* 2606:     */           case 23: 
/* 2607:     */           case 24: 
/* 2608:     */           case 25: 
/* 2609:     */           case 26: 
/* 2610:     */           case 28: 
/* 2611:     */           case 29: 
/* 2612:     */           case 30: 
/* 2613:     */           case 31: 
/* 2614:     */           case 32: 
/* 2615:     */           case 33: 
/* 2616:     */           case 34: 
/* 2617:     */           case 37: 
/* 2618:     */           case 40: 
/* 2619:     */           case 41: 
/* 2620:     */           case 42: 
/* 2621:     */           case 43: 
/* 2622:     */           case 44: 
/* 2623:     */           case 45: 
/* 2624:     */           case 46: 
/* 2625:     */           case 50: 
/* 2626:     */           case 51: 
/* 2627:     */           case 52: 
/* 2628:     */           case 53: 
/* 2629:     */           case 55: 
/* 2630:     */           case 56: 
/* 2631:     */           case 57: 
/* 2632:     */           case 58: 
/* 2633:     */           case 59: 
/* 2634:     */           case 60: 
/* 2635:     */           case 61: 
/* 2636:     */           case 63: 
/* 2637:     */           case 64: 
/* 2638:     */           case 65: 
/* 2639:     */           case 66: 
/* 2640:     */           case 67: 
/* 2641:     */           case 68: 
/* 2642:     */           case 69: 
/* 2643:     */           case 70: 
/* 2644:     */           case 71: 
/* 2645:     */           case 72: 
/* 2646:     */           case 73: 
/* 2647:     */           case 74: 
/* 2648:     */           case 75: 
/* 2649:     */           case 76: 
/* 2650:     */           case 77: 
/* 2651:     */           case 78: 
/* 2652:     */           case 79: 
/* 2653:     */           case 80: 
/* 2654:     */           case 81: 
/* 2655:     */           case 82: 
/* 2656:     */           case 83: 
/* 2657:     */           case 84: 
/* 2658:     */           case 85: 
/* 2659:     */           case 86: 
/* 2660:     */           case 87: 
/* 2661:     */           case 88: 
/* 2662:     */           case 89: 
/* 2663:     */           case 90: 
/* 2664:     */           case 91: 
/* 2665:     */           case 92: 
/* 2666:     */           case 93: 
/* 2667:     */           case 94: 
/* 2668:     */           case 100: 
/* 2669:     */           case 101: 
/* 2670:     */           case 102: 
/* 2671:     */           case 104: 
/* 2672:     */           case 105: 
/* 2673:     */           case 106: 
/* 2674:     */           case 107: 
/* 2675:     */           case 108: 
/* 2676:     */           case 109: 
/* 2677:     */           case 110: 
/* 2678:     */           case 111: 
/* 2679:     */           case 112: 
/* 2680:     */           case 113: 
/* 2681:     */           case 114: 
/* 2682:     */           case 117: 
/* 2683:     */           case 118: 
/* 2684:     */           case 119: 
/* 2685:     */           case 120: 
/* 2686:     */           case 121: 
/* 2687:     */           default: 
/* 2688:2445 */             throw new NoViableAltException(LT(1), getFilename());
/* 2689:     */           }
/* 2690:     */           break;
/* 2691:     */         case 108: 
/* 2692:2453 */           AST tmp70_AST = null;
/* 2693:2454 */           tmp70_AST = this.astFactory.create(LT(1));
/* 2694:2455 */           this.astFactory.makeASTRoot(currentAST, tmp70_AST);
/* 2695:2456 */           match(108);
/* 2696:2457 */           break;
/* 2697:     */         case 109: 
/* 2698:2461 */           ne = LT(1);
/* 2699:2462 */           ne_AST = this.astFactory.create(ne);
/* 2700:2463 */           this.astFactory.makeASTRoot(currentAST, ne_AST);
/* 2701:2464 */           match(109);
/* 2702:2465 */           ne_AST.setType(108);
/* 2703:2466 */           break;
/* 2704:     */         default: 
/* 2705:2470 */           throw new NoViableAltException(LT(1), getFilename());
/* 2706:     */         }
/* 2707:2474 */         relationalExpression();
/* 2708:2475 */         y_AST = this.returnAST;
/* 2709:2476 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2710:     */       }
/* 2711:2484 */       equalityExpression_AST = currentAST.root;
/* 2712:     */       
/* 2713:     */ 
/* 2714:2487 */       equalityExpression_AST = processEqualityExpression(equalityExpression_AST);
/* 2715:     */       
/* 2716:2489 */       currentAST.root = equalityExpression_AST;
/* 2717:2490 */       currentAST.child = ((equalityExpression_AST != null) && (equalityExpression_AST.getFirstChild() != null) ? equalityExpression_AST.getFirstChild() : equalityExpression_AST);
/* 2718:     */       
/* 2719:2492 */       currentAST.advanceChildToEnd();
/* 2720:2493 */       equalityExpression_AST = currentAST.root;
/* 2721:     */     }
/* 2722:     */     catch (RecognitionException ex)
/* 2723:     */     {
/* 2724:2496 */       reportError(ex);
/* 2725:2497 */       recover(ex, _tokenSet_26);
/* 2726:     */     }
/* 2727:2499 */     this.returnAST = equalityExpression_AST;
/* 2728:     */   }
/* 2729:     */   
/* 2730:     */   public final void relationalExpression()
/* 2731:     */     throws RecognitionException, TokenStreamException
/* 2732:     */   {
/* 2733:2504 */     this.returnAST = null;
/* 2734:2505 */     ASTPair currentAST = new ASTPair();
/* 2735:2506 */     AST relationalExpression_AST = null;
/* 2736:2507 */     Token n = null;
/* 2737:2508 */     AST n_AST = null;
/* 2738:2509 */     Token i = null;
/* 2739:2510 */     AST i_AST = null;
/* 2740:2511 */     Token b = null;
/* 2741:2512 */     AST b_AST = null;
/* 2742:2513 */     Token l = null;
/* 2743:2514 */     AST l_AST = null;
/* 2744:2515 */     AST p_AST = null;
/* 2745:     */     try
/* 2746:     */     {
/* 2747:2518 */       concatenation();
/* 2748:2519 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2749:2521 */       switch (LA(1))
/* 2750:     */       {
/* 2751:     */       }
/* 2752:2558 */       while ((LA(1) >= 110) && (LA(1) <= 113))
/* 2753:     */       {
/* 2754:2560 */         switch (LA(1))
/* 2755:     */         {
/* 2756:     */         case 110: 
/* 2757:2563 */           AST tmp71_AST = null;
/* 2758:2564 */           tmp71_AST = this.astFactory.create(LT(1));
/* 2759:2565 */           this.astFactory.makeASTRoot(currentAST, tmp71_AST);
/* 2760:2566 */           match(110);
/* 2761:2567 */           break;
/* 2762:     */         case 111: 
/* 2763:2571 */           AST tmp72_AST = null;
/* 2764:2572 */           tmp72_AST = this.astFactory.create(LT(1));
/* 2765:2573 */           this.astFactory.makeASTRoot(currentAST, tmp72_AST);
/* 2766:2574 */           match(111);
/* 2767:2575 */           break;
/* 2768:     */         case 112: 
/* 2769:2579 */           AST tmp73_AST = null;
/* 2770:2580 */           tmp73_AST = this.astFactory.create(LT(1));
/* 2771:2581 */           this.astFactory.makeASTRoot(currentAST, tmp73_AST);
/* 2772:2582 */           match(112);
/* 2773:2583 */           break;
/* 2774:     */         case 113: 
/* 2775:2587 */           AST tmp74_AST = null;
/* 2776:2588 */           tmp74_AST = this.astFactory.create(LT(1));
/* 2777:2589 */           this.astFactory.makeASTRoot(currentAST, tmp74_AST);
/* 2778:2590 */           match(113);
/* 2779:2591 */           break;
/* 2780:     */         default: 
/* 2781:2595 */           throw new NoViableAltException(LT(1), getFilename());
/* 2782:     */         }
/* 2783:2599 */         additiveExpression();
/* 2784:2600 */         this.astFactory.addASTChild(currentAST, this.returnAST); continue;
/* 2785:2618 */         switch (LA(1))
/* 2786:     */         {
/* 2787:     */         case 38: 
/* 2788:2621 */           n = LT(1);
/* 2789:2622 */           n_AST = this.astFactory.create(n);
/* 2790:2623 */           match(38);
/* 2791:2624 */           break;
/* 2792:     */         case 10: 
/* 2793:     */         case 26: 
/* 2794:     */         case 34: 
/* 2795:     */         case 64: 
/* 2796:     */           break;
/* 2797:     */         default: 
/* 2798:2635 */           throw new NoViableAltException(LT(1), getFilename());
/* 2799:     */         }
/* 2800:2640 */         switch (LA(1))
/* 2801:     */         {
/* 2802:     */         case 26: 
/* 2803:2644 */           i = LT(1);
/* 2804:2645 */           i_AST = this.astFactory.create(i);
/* 2805:2646 */           this.astFactory.makeASTRoot(currentAST, i_AST);
/* 2806:2647 */           match(26);
/* 2807:     */           
/* 2808:2649 */           i_AST.setType(n == null ? 26 : 83);
/* 2809:2650 */           i_AST.setText(n == null ? "in" : "not in");
/* 2810:     */           
/* 2811:2652 */           inList();
/* 2812:2653 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2813:     */           
/* 2814:2655 */           break;
/* 2815:     */         case 10: 
/* 2816:2660 */           b = LT(1);
/* 2817:2661 */           b_AST = this.astFactory.create(b);
/* 2818:2662 */           this.astFactory.makeASTRoot(currentAST, b_AST);
/* 2819:2663 */           match(10);
/* 2820:     */           
/* 2821:2665 */           b_AST.setType(n == null ? 10 : 82);
/* 2822:2666 */           b_AST.setText(n == null ? "between" : "not between");
/* 2823:     */           
/* 2824:2668 */           betweenList();
/* 2825:2669 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2826:     */           
/* 2827:2671 */           break;
/* 2828:     */         case 34: 
/* 2829:2676 */           l = LT(1);
/* 2830:2677 */           l_AST = this.astFactory.create(l);
/* 2831:2678 */           this.astFactory.makeASTRoot(currentAST, l_AST);
/* 2832:2679 */           match(34);
/* 2833:     */           
/* 2834:2681 */           l_AST.setType(n == null ? 34 : 84);
/* 2835:2682 */           l_AST.setText(n == null ? "like" : "not like");
/* 2836:     */           
/* 2837:2684 */           concatenation();
/* 2838:2685 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2839:2686 */           likeEscape();
/* 2840:2687 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2841:     */           
/* 2842:2689 */           break;
/* 2843:     */         case 64: 
/* 2844:2694 */           match(64);
/* 2845:2696 */           switch (LA(1))
/* 2846:     */           {
/* 2847:     */           case 66: 
/* 2848:2699 */             match(66);
/* 2849:2700 */             break;
/* 2850:     */           case 126: 
/* 2851:     */             break;
/* 2852:     */           default: 
/* 2853:2708 */             throw new NoViableAltException(LT(1), getFilename());
/* 2854:     */           }
/* 2855:2712 */           path();
/* 2856:2713 */           p_AST = this.returnAST;
/* 2857:     */           
/* 2858:2715 */           processMemberOf(n, p_AST, currentAST);
/* 2859:     */           
/* 2860:     */ 
/* 2861:2718 */           break;
/* 2862:     */         default: 
/* 2863:2722 */           throw new NoViableAltException(LT(1), getFilename());
/* 2864:     */           
/* 2865:     */ 
/* 2866:     */ 
/* 2867:     */ 
/* 2868:     */ 
/* 2869:     */ 
/* 2870:     */ 
/* 2871:2730 */           throw new NoViableAltException(LT(1), getFilename());
/* 2872:     */         }
/* 2873:     */       }
/* 2874:2734 */       relationalExpression_AST = currentAST.root;
/* 2875:     */     }
/* 2876:     */     catch (RecognitionException ex)
/* 2877:     */     {
/* 2878:2737 */       reportError(ex);
/* 2879:2738 */       recover(ex, _tokenSet_28);
/* 2880:     */     }
/* 2881:2740 */     this.returnAST = relationalExpression_AST;
/* 2882:     */   }
/* 2883:     */   
/* 2884:     */   public final void additiveExpression()
/* 2885:     */     throws RecognitionException, TokenStreamException
/* 2886:     */   {
/* 2887:2745 */     this.returnAST = null;
/* 2888:2746 */     ASTPair currentAST = new ASTPair();
/* 2889:2747 */     AST additiveExpression_AST = null;
/* 2890:     */     try
/* 2891:     */     {
/* 2892:2750 */       multiplyExpression();
/* 2893:2751 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2894:2755 */       while ((LA(1) == 115) || (LA(1) == 116))
/* 2895:     */       {
/* 2896:2757 */         switch (LA(1))
/* 2897:     */         {
/* 2898:     */         case 115: 
/* 2899:2760 */           AST tmp77_AST = null;
/* 2900:2761 */           tmp77_AST = this.astFactory.create(LT(1));
/* 2901:2762 */           this.astFactory.makeASTRoot(currentAST, tmp77_AST);
/* 2902:2763 */           match(115);
/* 2903:2764 */           break;
/* 2904:     */         case 116: 
/* 2905:2768 */           AST tmp78_AST = null;
/* 2906:2769 */           tmp78_AST = this.astFactory.create(LT(1));
/* 2907:2770 */           this.astFactory.makeASTRoot(currentAST, tmp78_AST);
/* 2908:2771 */           match(116);
/* 2909:2772 */           break;
/* 2910:     */         default: 
/* 2911:2776 */           throw new NoViableAltException(LT(1), getFilename());
/* 2912:     */         }
/* 2913:2780 */         multiplyExpression();
/* 2914:2781 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2915:     */       }
/* 2916:2789 */       additiveExpression_AST = currentAST.root;
/* 2917:     */     }
/* 2918:     */     catch (RecognitionException ex)
/* 2919:     */     {
/* 2920:2792 */       reportError(ex);
/* 2921:2793 */       recover(ex, _tokenSet_29);
/* 2922:     */     }
/* 2923:2795 */     this.returnAST = additiveExpression_AST;
/* 2924:     */   }
/* 2925:     */   
/* 2926:     */   public final void inList()
/* 2927:     */     throws RecognitionException, TokenStreamException
/* 2928:     */   {
/* 2929:2800 */     this.returnAST = null;
/* 2930:2801 */     ASTPair currentAST = new ASTPair();
/* 2931:2802 */     AST inList_AST = null;
/* 2932:2803 */     AST x_AST = null;
/* 2933:     */     try
/* 2934:     */     {
/* 2935:2806 */       compoundExpr();
/* 2936:2807 */       x_AST = this.returnAST;
/* 2937:2808 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2938:2809 */       inList_AST = currentAST.root;
/* 2939:2810 */       inList_AST = this.astFactory.make(new ASTArray(2).add(this.astFactory.create(77, "inList")).add(inList_AST));
/* 2940:2811 */       currentAST.root = inList_AST;
/* 2941:2812 */       currentAST.child = ((inList_AST != null) && (inList_AST.getFirstChild() != null) ? inList_AST.getFirstChild() : inList_AST);
/* 2942:     */       
/* 2943:2814 */       currentAST.advanceChildToEnd();
/* 2944:2815 */       inList_AST = currentAST.root;
/* 2945:     */     }
/* 2946:     */     catch (RecognitionException ex)
/* 2947:     */     {
/* 2948:2818 */       reportError(ex);
/* 2949:2819 */       recover(ex, _tokenSet_28);
/* 2950:     */     }
/* 2951:2821 */     this.returnAST = inList_AST;
/* 2952:     */   }
/* 2953:     */   
/* 2954:     */   public final void betweenList()
/* 2955:     */     throws RecognitionException, TokenStreamException
/* 2956:     */   {
/* 2957:2826 */     this.returnAST = null;
/* 2958:2827 */     ASTPair currentAST = new ASTPair();
/* 2959:2828 */     AST betweenList_AST = null;
/* 2960:     */     try
/* 2961:     */     {
/* 2962:2831 */       concatenation();
/* 2963:2832 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2964:2833 */       match(6);
/* 2965:2834 */       concatenation();
/* 2966:2835 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2967:2836 */       betweenList_AST = currentAST.root;
/* 2968:     */     }
/* 2969:     */     catch (RecognitionException ex)
/* 2970:     */     {
/* 2971:2839 */       reportError(ex);
/* 2972:2840 */       recover(ex, _tokenSet_28);
/* 2973:     */     }
/* 2974:2842 */     this.returnAST = betweenList_AST;
/* 2975:     */   }
/* 2976:     */   
/* 2977:     */   public final void likeEscape()
/* 2978:     */     throws RecognitionException, TokenStreamException
/* 2979:     */   {
/* 2980:2847 */     this.returnAST = null;
/* 2981:2848 */     ASTPair currentAST = new ASTPair();
/* 2982:2849 */     AST likeEscape_AST = null;
/* 2983:     */     try
/* 2984:     */     {
/* 2985:2853 */       switch (LA(1))
/* 2986:     */       {
/* 2987:     */       case 18: 
/* 2988:2856 */         AST tmp80_AST = null;
/* 2989:2857 */         tmp80_AST = this.astFactory.create(LT(1));
/* 2990:2858 */         this.astFactory.makeASTRoot(currentAST, tmp80_AST);
/* 2991:2859 */         match(18);
/* 2992:2860 */         concatenation();
/* 2993:2861 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2994:2862 */         break;
/* 2995:     */       case 1: 
/* 2996:     */       case 6: 
/* 2997:     */       case 7: 
/* 2998:     */       case 8: 
/* 2999:     */       case 14: 
/* 3000:     */       case 22: 
/* 3001:     */       case 23: 
/* 3002:     */       case 24: 
/* 3003:     */       case 25: 
/* 3004:     */       case 28: 
/* 3005:     */       case 31: 
/* 3006:     */       case 32: 
/* 3007:     */       case 33: 
/* 3008:     */       case 40: 
/* 3009:     */       case 41: 
/* 3010:     */       case 44: 
/* 3011:     */       case 50: 
/* 3012:     */       case 53: 
/* 3013:     */       case 57: 
/* 3014:     */       case 101: 
/* 3015:     */       case 102: 
/* 3016:     */       case 104: 
/* 3017:     */       case 106: 
/* 3018:     */       case 107: 
/* 3019:     */       case 108: 
/* 3020:     */       case 109: 
/* 3021:     */       case 121: 
/* 3022:     */         break;
/* 3023:     */       case 2: 
/* 3024:     */       case 3: 
/* 3025:     */       case 4: 
/* 3026:     */       case 5: 
/* 3027:     */       case 9: 
/* 3028:     */       case 10: 
/* 3029:     */       case 11: 
/* 3030:     */       case 12: 
/* 3031:     */       case 13: 
/* 3032:     */       case 15: 
/* 3033:     */       case 16: 
/* 3034:     */       case 17: 
/* 3035:     */       case 19: 
/* 3036:     */       case 20: 
/* 3037:     */       case 21: 
/* 3038:     */       case 26: 
/* 3039:     */       case 27: 
/* 3040:     */       case 29: 
/* 3041:     */       case 30: 
/* 3042:     */       case 34: 
/* 3043:     */       case 35: 
/* 3044:     */       case 36: 
/* 3045:     */       case 37: 
/* 3046:     */       case 38: 
/* 3047:     */       case 39: 
/* 3048:     */       case 42: 
/* 3049:     */       case 43: 
/* 3050:     */       case 45: 
/* 3051:     */       case 46: 
/* 3052:     */       case 47: 
/* 3053:     */       case 48: 
/* 3054:     */       case 49: 
/* 3055:     */       case 51: 
/* 3056:     */       case 52: 
/* 3057:     */       case 54: 
/* 3058:     */       case 55: 
/* 3059:     */       case 56: 
/* 3060:     */       case 58: 
/* 3061:     */       case 59: 
/* 3062:     */       case 60: 
/* 3063:     */       case 61: 
/* 3064:     */       case 62: 
/* 3065:     */       case 63: 
/* 3066:     */       case 64: 
/* 3067:     */       case 65: 
/* 3068:     */       case 66: 
/* 3069:     */       case 67: 
/* 3070:     */       case 68: 
/* 3071:     */       case 69: 
/* 3072:     */       case 70: 
/* 3073:     */       case 71: 
/* 3074:     */       case 72: 
/* 3075:     */       case 73: 
/* 3076:     */       case 74: 
/* 3077:     */       case 75: 
/* 3078:     */       case 76: 
/* 3079:     */       case 77: 
/* 3080:     */       case 78: 
/* 3081:     */       case 79: 
/* 3082:     */       case 80: 
/* 3083:     */       case 81: 
/* 3084:     */       case 82: 
/* 3085:     */       case 83: 
/* 3086:     */       case 84: 
/* 3087:     */       case 85: 
/* 3088:     */       case 86: 
/* 3089:     */       case 87: 
/* 3090:     */       case 88: 
/* 3091:     */       case 89: 
/* 3092:     */       case 90: 
/* 3093:     */       case 91: 
/* 3094:     */       case 92: 
/* 3095:     */       case 93: 
/* 3096:     */       case 94: 
/* 3097:     */       case 95: 
/* 3098:     */       case 96: 
/* 3099:     */       case 97: 
/* 3100:     */       case 98: 
/* 3101:     */       case 99: 
/* 3102:     */       case 100: 
/* 3103:     */       case 103: 
/* 3104:     */       case 105: 
/* 3105:     */       case 110: 
/* 3106:     */       case 111: 
/* 3107:     */       case 112: 
/* 3108:     */       case 113: 
/* 3109:     */       case 114: 
/* 3110:     */       case 115: 
/* 3111:     */       case 116: 
/* 3112:     */       case 117: 
/* 3113:     */       case 118: 
/* 3114:     */       case 119: 
/* 3115:     */       case 120: 
/* 3116:     */       default: 
/* 3117:2896 */         throw new NoViableAltException(LT(1), getFilename());
/* 3118:     */       }
/* 3119:2900 */       likeEscape_AST = currentAST.root;
/* 3120:     */     }
/* 3121:     */     catch (RecognitionException ex)
/* 3122:     */     {
/* 3123:2903 */       reportError(ex);
/* 3124:2904 */       recover(ex, _tokenSet_28);
/* 3125:     */     }
/* 3126:2906 */     this.returnAST = likeEscape_AST;
/* 3127:     */   }
/* 3128:     */   
/* 3129:     */   public final void compoundExpr()
/* 3130:     */     throws RecognitionException, TokenStreamException
/* 3131:     */   {
/* 3132:2911 */     this.returnAST = null;
/* 3133:2912 */     ASTPair currentAST = new ASTPair();
/* 3134:2913 */     AST compoundExpr_AST = null;
/* 3135:     */     try
/* 3136:     */     {
/* 3137:2916 */       switch (LA(1))
/* 3138:     */       {
/* 3139:     */       case 17: 
/* 3140:     */       case 27: 
/* 3141:2920 */         collectionExpr();
/* 3142:2921 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3143:2922 */         compoundExpr_AST = currentAST.root;
/* 3144:2923 */         break;
/* 3145:     */       case 126: 
/* 3146:2927 */         path();
/* 3147:2928 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3148:2929 */         compoundExpr_AST = currentAST.root;
/* 3149:2930 */         break;
/* 3150:     */       case 103: 
/* 3151:2935 */         match(103);
/* 3152:2937 */         switch (LA(1))
/* 3153:     */         {
/* 3154:     */         case 4: 
/* 3155:     */         case 5: 
/* 3156:     */         case 9: 
/* 3157:     */         case 12: 
/* 3158:     */         case 17: 
/* 3159:     */         case 19: 
/* 3160:     */         case 20: 
/* 3161:     */         case 27: 
/* 3162:     */         case 35: 
/* 3163:     */         case 36: 
/* 3164:     */         case 38: 
/* 3165:     */         case 39: 
/* 3166:     */         case 47: 
/* 3167:     */         case 48: 
/* 3168:     */         case 49: 
/* 3169:     */         case 54: 
/* 3170:     */         case 62: 
/* 3171:     */         case 95: 
/* 3172:     */         case 96: 
/* 3173:     */         case 97: 
/* 3174:     */         case 98: 
/* 3175:     */         case 99: 
/* 3176:     */         case 103: 
/* 3177:     */         case 115: 
/* 3178:     */         case 116: 
/* 3179:     */         case 122: 
/* 3180:     */         case 123: 
/* 3181:     */         case 124: 
/* 3182:     */         case 125: 
/* 3183:     */         case 126: 
/* 3184:2970 */           expression();
/* 3185:2971 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3186:     */         }
/* 3187:2975 */         while (LA(1) == 101)
/* 3188:     */         {
/* 3189:2976 */           match(101);
/* 3190:2977 */           expression();
/* 3191:2978 */           this.astFactory.addASTChild(currentAST, this.returnAST); continue;
/* 3192:     */           
/* 3193:     */ 
/* 3194:     */ 
/* 3195:     */ 
/* 3196:     */ 
/* 3197:     */ 
/* 3198:     */ 
/* 3199:     */ 
/* 3200:     */ 
/* 3201:     */ 
/* 3202:     */ 
/* 3203:     */ 
/* 3204:     */ 
/* 3205:     */ 
/* 3206:     */ 
/* 3207:     */ 
/* 3208:     */ 
/* 3209:     */ 
/* 3210:2997 */           subQuery();
/* 3211:2998 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3212:2999 */           break;
/* 3213:     */           
/* 3214:     */ 
/* 3215:     */ 
/* 3216:3003 */           throw new NoViableAltException(LT(1), getFilename());
/* 3217:     */         }
/* 3218:3007 */         match(104);
/* 3219:     */         
/* 3220:3009 */         compoundExpr_AST = currentAST.root;
/* 3221:3010 */         break;
/* 3222:     */       case 122: 
/* 3223:     */       case 123: 
/* 3224:3015 */         parameter();
/* 3225:3016 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3226:3017 */         compoundExpr_AST = currentAST.root;
/* 3227:3018 */         break;
/* 3228:     */       default: 
/* 3229:3022 */         throw new NoViableAltException(LT(1), getFilename());
/* 3230:     */       }
/* 3231:     */     }
/* 3232:     */     catch (RecognitionException ex)
/* 3233:     */     {
/* 3234:3027 */       reportError(ex);
/* 3235:3028 */       recover(ex, _tokenSet_28);
/* 3236:     */     }
/* 3237:3030 */     this.returnAST = compoundExpr_AST;
/* 3238:     */   }
/* 3239:     */   
/* 3240:     */   public final void multiplyExpression()
/* 3241:     */     throws RecognitionException, TokenStreamException
/* 3242:     */   {
/* 3243:3035 */     this.returnAST = null;
/* 3244:3036 */     ASTPair currentAST = new ASTPair();
/* 3245:3037 */     AST multiplyExpression_AST = null;
/* 3246:     */     try
/* 3247:     */     {
/* 3248:3040 */       unaryExpression();
/* 3249:3041 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3250:3045 */       while ((LA(1) >= 117) && (LA(1) <= 119))
/* 3251:     */       {
/* 3252:3047 */         switch (LA(1))
/* 3253:     */         {
/* 3254:     */         case 117: 
/* 3255:3050 */           AST tmp84_AST = null;
/* 3256:3051 */           tmp84_AST = this.astFactory.create(LT(1));
/* 3257:3052 */           this.astFactory.makeASTRoot(currentAST, tmp84_AST);
/* 3258:3053 */           match(117);
/* 3259:3054 */           break;
/* 3260:     */         case 118: 
/* 3261:3058 */           AST tmp85_AST = null;
/* 3262:3059 */           tmp85_AST = this.astFactory.create(LT(1));
/* 3263:3060 */           this.astFactory.makeASTRoot(currentAST, tmp85_AST);
/* 3264:3061 */           match(118);
/* 3265:3062 */           break;
/* 3266:     */         case 119: 
/* 3267:3066 */           AST tmp86_AST = null;
/* 3268:3067 */           tmp86_AST = this.astFactory.create(LT(1));
/* 3269:3068 */           this.astFactory.makeASTRoot(currentAST, tmp86_AST);
/* 3270:3069 */           match(119);
/* 3271:3070 */           break;
/* 3272:     */         default: 
/* 3273:3074 */           throw new NoViableAltException(LT(1), getFilename());
/* 3274:     */         }
/* 3275:3078 */         unaryExpression();
/* 3276:3079 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3277:     */       }
/* 3278:3087 */       multiplyExpression_AST = currentAST.root;
/* 3279:     */     }
/* 3280:     */     catch (RecognitionException ex)
/* 3281:     */     {
/* 3282:3090 */       reportError(ex);
/* 3283:3091 */       recover(ex, _tokenSet_30);
/* 3284:     */     }
/* 3285:3093 */     this.returnAST = multiplyExpression_AST;
/* 3286:     */   }
/* 3287:     */   
/* 3288:     */   public final void unaryExpression()
/* 3289:     */     throws RecognitionException, TokenStreamException
/* 3290:     */   {
/* 3291:3098 */     this.returnAST = null;
/* 3292:3099 */     ASTPair currentAST = new ASTPair();
/* 3293:3100 */     AST unaryExpression_AST = null;
/* 3294:     */     try
/* 3295:     */     {
/* 3296:3103 */       switch (LA(1))
/* 3297:     */       {
/* 3298:     */       case 116: 
/* 3299:3106 */         AST tmp87_AST = null;
/* 3300:3107 */         tmp87_AST = this.astFactory.create(LT(1));
/* 3301:3108 */         this.astFactory.makeASTRoot(currentAST, tmp87_AST);
/* 3302:3109 */         match(116);
/* 3303:3110 */         tmp87_AST.setType(90);
/* 3304:3111 */         unaryExpression();
/* 3305:3112 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3306:3113 */         unaryExpression_AST = currentAST.root;
/* 3307:3114 */         break;
/* 3308:     */       case 115: 
/* 3309:3118 */         AST tmp88_AST = null;
/* 3310:3119 */         tmp88_AST = this.astFactory.create(LT(1));
/* 3311:3120 */         this.astFactory.makeASTRoot(currentAST, tmp88_AST);
/* 3312:3121 */         match(115);
/* 3313:3122 */         tmp88_AST.setType(91);
/* 3314:3123 */         unaryExpression();
/* 3315:3124 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3316:3125 */         unaryExpression_AST = currentAST.root;
/* 3317:3126 */         break;
/* 3318:     */       case 54: 
/* 3319:3130 */         caseExpression();
/* 3320:3131 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3321:3132 */         unaryExpression_AST = currentAST.root;
/* 3322:3133 */         break;
/* 3323:     */       case 4: 
/* 3324:     */       case 5: 
/* 3325:     */       case 19: 
/* 3326:     */       case 47: 
/* 3327:3140 */         quantifiedExpression();
/* 3328:3141 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3329:3142 */         unaryExpression_AST = currentAST.root;
/* 3330:3143 */         break;
/* 3331:     */       case 9: 
/* 3332:     */       case 12: 
/* 3333:     */       case 17: 
/* 3334:     */       case 20: 
/* 3335:     */       case 27: 
/* 3336:     */       case 35: 
/* 3337:     */       case 36: 
/* 3338:     */       case 39: 
/* 3339:     */       case 48: 
/* 3340:     */       case 49: 
/* 3341:     */       case 62: 
/* 3342:     */       case 95: 
/* 3343:     */       case 96: 
/* 3344:     */       case 97: 
/* 3345:     */       case 98: 
/* 3346:     */       case 99: 
/* 3347:     */       case 103: 
/* 3348:     */       case 122: 
/* 3349:     */       case 123: 
/* 3350:     */       case 124: 
/* 3351:     */       case 125: 
/* 3352:     */       case 126: 
/* 3353:3168 */         atom();
/* 3354:3169 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3355:3170 */         unaryExpression_AST = currentAST.root;
/* 3356:3171 */         break;
/* 3357:     */       case 6: 
/* 3358:     */       case 7: 
/* 3359:     */       case 8: 
/* 3360:     */       case 10: 
/* 3361:     */       case 11: 
/* 3362:     */       case 13: 
/* 3363:     */       case 14: 
/* 3364:     */       case 15: 
/* 3365:     */       case 16: 
/* 3366:     */       case 18: 
/* 3367:     */       case 21: 
/* 3368:     */       case 22: 
/* 3369:     */       case 23: 
/* 3370:     */       case 24: 
/* 3371:     */       case 25: 
/* 3372:     */       case 26: 
/* 3373:     */       case 28: 
/* 3374:     */       case 29: 
/* 3375:     */       case 30: 
/* 3376:     */       case 31: 
/* 3377:     */       case 32: 
/* 3378:     */       case 33: 
/* 3379:     */       case 34: 
/* 3380:     */       case 37: 
/* 3381:     */       case 38: 
/* 3382:     */       case 40: 
/* 3383:     */       case 41: 
/* 3384:     */       case 42: 
/* 3385:     */       case 43: 
/* 3386:     */       case 44: 
/* 3387:     */       case 45: 
/* 3388:     */       case 46: 
/* 3389:     */       case 50: 
/* 3390:     */       case 51: 
/* 3391:     */       case 52: 
/* 3392:     */       case 53: 
/* 3393:     */       case 55: 
/* 3394:     */       case 56: 
/* 3395:     */       case 57: 
/* 3396:     */       case 58: 
/* 3397:     */       case 59: 
/* 3398:     */       case 60: 
/* 3399:     */       case 61: 
/* 3400:     */       case 63: 
/* 3401:     */       case 64: 
/* 3402:     */       case 65: 
/* 3403:     */       case 66: 
/* 3404:     */       case 67: 
/* 3405:     */       case 68: 
/* 3406:     */       case 69: 
/* 3407:     */       case 70: 
/* 3408:     */       case 71: 
/* 3409:     */       case 72: 
/* 3410:     */       case 73: 
/* 3411:     */       case 74: 
/* 3412:     */       case 75: 
/* 3413:     */       case 76: 
/* 3414:     */       case 77: 
/* 3415:     */       case 78: 
/* 3416:     */       case 79: 
/* 3417:     */       case 80: 
/* 3418:     */       case 81: 
/* 3419:     */       case 82: 
/* 3420:     */       case 83: 
/* 3421:     */       case 84: 
/* 3422:     */       case 85: 
/* 3423:     */       case 86: 
/* 3424:     */       case 87: 
/* 3425:     */       case 88: 
/* 3426:     */       case 89: 
/* 3427:     */       case 90: 
/* 3428:     */       case 91: 
/* 3429:     */       case 92: 
/* 3430:     */       case 93: 
/* 3431:     */       case 94: 
/* 3432:     */       case 100: 
/* 3433:     */       case 101: 
/* 3434:     */       case 102: 
/* 3435:     */       case 104: 
/* 3436:     */       case 105: 
/* 3437:     */       case 106: 
/* 3438:     */       case 107: 
/* 3439:     */       case 108: 
/* 3440:     */       case 109: 
/* 3441:     */       case 110: 
/* 3442:     */       case 111: 
/* 3443:     */       case 112: 
/* 3444:     */       case 113: 
/* 3445:     */       case 114: 
/* 3446:     */       case 117: 
/* 3447:     */       case 118: 
/* 3448:     */       case 119: 
/* 3449:     */       case 120: 
/* 3450:     */       case 121: 
/* 3451:     */       default: 
/* 3452:3175 */         throw new NoViableAltException(LT(1), getFilename());
/* 3453:     */       }
/* 3454:     */     }
/* 3455:     */     catch (RecognitionException ex)
/* 3456:     */     {
/* 3457:3180 */       reportError(ex);
/* 3458:3181 */       recover(ex, _tokenSet_31);
/* 3459:     */     }
/* 3460:3183 */     this.returnAST = unaryExpression_AST;
/* 3461:     */   }
/* 3462:     */   
/* 3463:     */   public final void caseExpression()
/* 3464:     */     throws RecognitionException, TokenStreamException
/* 3465:     */   {
/* 3466:3188 */     this.returnAST = null;
/* 3467:3189 */     ASTPair currentAST = new ASTPair();
/* 3468:3190 */     AST caseExpression_AST = null;
/* 3469:     */     try
/* 3470:     */     {
/* 3471:3193 */       if ((LA(1) == 54) && (LA(2) == 58))
/* 3472:     */       {
/* 3473:3194 */         AST tmp89_AST = null;
/* 3474:3195 */         tmp89_AST = this.astFactory.create(LT(1));
/* 3475:3196 */         this.astFactory.makeASTRoot(currentAST, tmp89_AST);
/* 3476:3197 */         match(54);
/* 3477:     */         
/* 3478:3199 */         int _cnt130 = 0;
/* 3479:     */         for (;;)
/* 3480:     */         {
/* 3481:3202 */           if (LA(1) == 58)
/* 3482:     */           {
/* 3483:3203 */             whenClause();
/* 3484:3204 */             this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3485:     */           }
/* 3486:     */           else
/* 3487:     */           {
/* 3488:3207 */             if (_cnt130 >= 1) {
/* 3489:     */               break;
/* 3490:     */             }
/* 3491:3207 */             throw new NoViableAltException(LT(1), getFilename());
/* 3492:     */           }
/* 3493:3210 */           _cnt130++;
/* 3494:     */         }
/* 3495:3214 */         switch (LA(1))
/* 3496:     */         {
/* 3497:     */         case 56: 
/* 3498:3217 */           elseClause();
/* 3499:3218 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3500:3219 */           break;
/* 3501:     */         case 55: 
/* 3502:     */           break;
/* 3503:     */         default: 
/* 3504:3227 */           throw new NoViableAltException(LT(1), getFilename());
/* 3505:     */         }
/* 3506:3231 */         match(55);
/* 3507:3232 */         caseExpression_AST = currentAST.root;
/* 3508:     */       }
/* 3509:3234 */       else if ((LA(1) == 54) && (_tokenSet_32.member(LA(2))))
/* 3510:     */       {
/* 3511:3235 */         AST tmp91_AST = null;
/* 3512:3236 */         tmp91_AST = this.astFactory.create(LT(1));
/* 3513:3237 */         this.astFactory.makeASTRoot(currentAST, tmp91_AST);
/* 3514:3238 */         match(54);
/* 3515:3239 */         tmp91_AST.setType(74);
/* 3516:3240 */         unaryExpression();
/* 3517:3241 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3518:     */         
/* 3519:3243 */         int _cnt133 = 0;
/* 3520:     */         for (;;)
/* 3521:     */         {
/* 3522:3246 */           if (LA(1) == 58)
/* 3523:     */           {
/* 3524:3247 */             altWhenClause();
/* 3525:3248 */             this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3526:     */           }
/* 3527:     */           else
/* 3528:     */           {
/* 3529:3251 */             if (_cnt133 >= 1) {
/* 3530:     */               break;
/* 3531:     */             }
/* 3532:3251 */             throw new NoViableAltException(LT(1), getFilename());
/* 3533:     */           }
/* 3534:3254 */           _cnt133++;
/* 3535:     */         }
/* 3536:3258 */         switch (LA(1))
/* 3537:     */         {
/* 3538:     */         case 56: 
/* 3539:3261 */           elseClause();
/* 3540:3262 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3541:3263 */           break;
/* 3542:     */         case 55: 
/* 3543:     */           break;
/* 3544:     */         default: 
/* 3545:3271 */           throw new NoViableAltException(LT(1), getFilename());
/* 3546:     */         }
/* 3547:3275 */         match(55);
/* 3548:3276 */         caseExpression_AST = currentAST.root;
/* 3549:     */       }
/* 3550:     */       else
/* 3551:     */       {
/* 3552:3279 */         throw new NoViableAltException(LT(1), getFilename());
/* 3553:     */       }
/* 3554:     */     }
/* 3555:     */     catch (RecognitionException ex)
/* 3556:     */     {
/* 3557:3284 */       reportError(ex);
/* 3558:3285 */       recover(ex, _tokenSet_31);
/* 3559:     */     }
/* 3560:3287 */     this.returnAST = caseExpression_AST;
/* 3561:     */   }
/* 3562:     */   
/* 3563:     */   public final void quantifiedExpression()
/* 3564:     */     throws RecognitionException, TokenStreamException
/* 3565:     */   {
/* 3566:3292 */     this.returnAST = null;
/* 3567:3293 */     ASTPair currentAST = new ASTPair();
/* 3568:3294 */     AST quantifiedExpression_AST = null;
/* 3569:     */     try
/* 3570:     */     {
/* 3571:3298 */       switch (LA(1))
/* 3572:     */       {
/* 3573:     */       case 47: 
/* 3574:3301 */         AST tmp93_AST = null;
/* 3575:3302 */         tmp93_AST = this.astFactory.create(LT(1));
/* 3576:3303 */         this.astFactory.makeASTRoot(currentAST, tmp93_AST);
/* 3577:3304 */         match(47);
/* 3578:3305 */         break;
/* 3579:     */       case 19: 
/* 3580:3309 */         AST tmp94_AST = null;
/* 3581:3310 */         tmp94_AST = this.astFactory.create(LT(1));
/* 3582:3311 */         this.astFactory.makeASTRoot(currentAST, tmp94_AST);
/* 3583:3312 */         match(19);
/* 3584:3313 */         break;
/* 3585:     */       case 4: 
/* 3586:3317 */         AST tmp95_AST = null;
/* 3587:3318 */         tmp95_AST = this.astFactory.create(LT(1));
/* 3588:3319 */         this.astFactory.makeASTRoot(currentAST, tmp95_AST);
/* 3589:3320 */         match(4);
/* 3590:3321 */         break;
/* 3591:     */       case 5: 
/* 3592:3325 */         AST tmp96_AST = null;
/* 3593:3326 */         tmp96_AST = this.astFactory.create(LT(1));
/* 3594:3327 */         this.astFactory.makeASTRoot(currentAST, tmp96_AST);
/* 3595:3328 */         match(5);
/* 3596:3329 */         break;
/* 3597:     */       default: 
/* 3598:3333 */         throw new NoViableAltException(LT(1), getFilename());
/* 3599:     */       }
/* 3600:3338 */       switch (LA(1))
/* 3601:     */       {
/* 3602:     */       case 126: 
/* 3603:3341 */         identifier();
/* 3604:3342 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3605:3343 */         break;
/* 3606:     */       case 17: 
/* 3607:     */       case 27: 
/* 3608:3348 */         collectionExpr();
/* 3609:3349 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3610:3350 */         break;
/* 3611:     */       case 103: 
/* 3612:3355 */         match(103);
/* 3613:     */         
/* 3614:3357 */         subQuery();
/* 3615:3358 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3616:     */         
/* 3617:3360 */         match(104);
/* 3618:     */         
/* 3619:3362 */         break;
/* 3620:     */       default: 
/* 3621:3366 */         throw new NoViableAltException(LT(1), getFilename());
/* 3622:     */       }
/* 3623:3370 */       quantifiedExpression_AST = currentAST.root;
/* 3624:     */     }
/* 3625:     */     catch (RecognitionException ex)
/* 3626:     */     {
/* 3627:3373 */       reportError(ex);
/* 3628:3374 */       recover(ex, _tokenSet_31);
/* 3629:     */     }
/* 3630:3376 */     this.returnAST = quantifiedExpression_AST;
/* 3631:     */   }
/* 3632:     */   
/* 3633:     */   public final void atom()
/* 3634:     */     throws RecognitionException, TokenStreamException
/* 3635:     */   {
/* 3636:3381 */     this.returnAST = null;
/* 3637:3382 */     ASTPair currentAST = new ASTPair();
/* 3638:3383 */     AST atom_AST = null;
/* 3639:3384 */     Token op = null;
/* 3640:3385 */     AST op_AST = null;
/* 3641:3386 */     Token lb = null;
/* 3642:3387 */     AST lb_AST = null;
/* 3643:     */     try
/* 3644:     */     {
/* 3645:3390 */       primaryExpression();
/* 3646:3391 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3647:     */       for (;;)
/* 3648:     */       {
/* 3649:3395 */         switch (LA(1))
/* 3650:     */         {
/* 3651:     */         case 15: 
/* 3652:3398 */           AST tmp99_AST = null;
/* 3653:3399 */           tmp99_AST = this.astFactory.create(LT(1));
/* 3654:3400 */           this.astFactory.makeASTRoot(currentAST, tmp99_AST);
/* 3655:3401 */           match(15);
/* 3656:3402 */           identifier();
/* 3657:3403 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3658:3405 */           switch (LA(1))
/* 3659:     */           {
/* 3660:     */           case 103: 
/* 3661:3409 */             op = LT(1);
/* 3662:3410 */             op_AST = this.astFactory.create(op);
/* 3663:3411 */             this.astFactory.makeASTRoot(currentAST, op_AST);
/* 3664:3412 */             match(103);
/* 3665:3413 */             op_AST.setType(81);
/* 3666:3414 */             exprList();
/* 3667:3415 */             this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3668:3416 */             match(104);
/* 3669:     */             
/* 3670:3418 */             break;
/* 3671:     */           case 1: 
/* 3672:     */           case 6: 
/* 3673:     */           case 7: 
/* 3674:     */           case 8: 
/* 3675:     */           case 10: 
/* 3676:     */           case 14: 
/* 3677:     */           case 15: 
/* 3678:     */           case 18: 
/* 3679:     */           case 22: 
/* 3680:     */           case 23: 
/* 3681:     */           case 24: 
/* 3682:     */           case 25: 
/* 3683:     */           case 26: 
/* 3684:     */           case 28: 
/* 3685:     */           case 31: 
/* 3686:     */           case 32: 
/* 3687:     */           case 33: 
/* 3688:     */           case 34: 
/* 3689:     */           case 38: 
/* 3690:     */           case 40: 
/* 3691:     */           case 41: 
/* 3692:     */           case 44: 
/* 3693:     */           case 50: 
/* 3694:     */           case 53: 
/* 3695:     */           case 55: 
/* 3696:     */           case 56: 
/* 3697:     */           case 57: 
/* 3698:     */           case 58: 
/* 3699:     */           case 64: 
/* 3700:     */           case 101: 
/* 3701:     */           case 102: 
/* 3702:     */           case 104: 
/* 3703:     */           case 106: 
/* 3704:     */           case 107: 
/* 3705:     */           case 108: 
/* 3706:     */           case 109: 
/* 3707:     */           case 110: 
/* 3708:     */           case 111: 
/* 3709:     */           case 112: 
/* 3710:     */           case 113: 
/* 3711:     */           case 114: 
/* 3712:     */           case 115: 
/* 3713:     */           case 116: 
/* 3714:     */           case 117: 
/* 3715:     */           case 118: 
/* 3716:     */           case 119: 
/* 3717:     */           case 120: 
/* 3718:     */           case 121: 
/* 3719:     */             break;
/* 3720:     */           case 2: 
/* 3721:     */           case 3: 
/* 3722:     */           case 4: 
/* 3723:     */           case 5: 
/* 3724:     */           case 9: 
/* 3725:     */           case 11: 
/* 3726:     */           case 12: 
/* 3727:     */           case 13: 
/* 3728:     */           case 16: 
/* 3729:     */           case 17: 
/* 3730:     */           case 19: 
/* 3731:     */           case 20: 
/* 3732:     */           case 21: 
/* 3733:     */           case 27: 
/* 3734:     */           case 29: 
/* 3735:     */           case 30: 
/* 3736:     */           case 35: 
/* 3737:     */           case 36: 
/* 3738:     */           case 37: 
/* 3739:     */           case 39: 
/* 3740:     */           case 42: 
/* 3741:     */           case 43: 
/* 3742:     */           case 45: 
/* 3743:     */           case 46: 
/* 3744:     */           case 47: 
/* 3745:     */           case 48: 
/* 3746:     */           case 49: 
/* 3747:     */           case 51: 
/* 3748:     */           case 52: 
/* 3749:     */           case 54: 
/* 3750:     */           case 59: 
/* 3751:     */           case 60: 
/* 3752:     */           case 61: 
/* 3753:     */           case 62: 
/* 3754:     */           case 63: 
/* 3755:     */           case 65: 
/* 3756:     */           case 66: 
/* 3757:     */           case 67: 
/* 3758:     */           case 68: 
/* 3759:     */           case 69: 
/* 3760:     */           case 70: 
/* 3761:     */           case 71: 
/* 3762:     */           case 72: 
/* 3763:     */           case 73: 
/* 3764:     */           case 74: 
/* 3765:     */           case 75: 
/* 3766:     */           case 76: 
/* 3767:     */           case 77: 
/* 3768:     */           case 78: 
/* 3769:     */           case 79: 
/* 3770:     */           case 80: 
/* 3771:     */           case 81: 
/* 3772:     */           case 82: 
/* 3773:     */           case 83: 
/* 3774:     */           case 84: 
/* 3775:     */           case 85: 
/* 3776:     */           case 86: 
/* 3777:     */           case 87: 
/* 3778:     */           case 88: 
/* 3779:     */           case 89: 
/* 3780:     */           case 90: 
/* 3781:     */           case 91: 
/* 3782:     */           case 92: 
/* 3783:     */           case 93: 
/* 3784:     */           case 94: 
/* 3785:     */           case 95: 
/* 3786:     */           case 96: 
/* 3787:     */           case 97: 
/* 3788:     */           case 98: 
/* 3789:     */           case 99: 
/* 3790:     */           case 100: 
/* 3791:     */           case 105: 
/* 3792:     */           default: 
/* 3793:3473 */             throw new NoViableAltException(LT(1), getFilename());
/* 3794:     */           }
/* 3795:     */           break;
/* 3796:     */         case 120: 
/* 3797:3481 */           lb = LT(1);
/* 3798:3482 */           lb_AST = this.astFactory.create(lb);
/* 3799:3483 */           this.astFactory.makeASTRoot(currentAST, lb_AST);
/* 3800:3484 */           match(120);
/* 3801:3485 */           lb_AST.setType(78);
/* 3802:3486 */           expression();
/* 3803:3487 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3804:3488 */           match(121);
/* 3805:     */         }
/* 3806:     */       }
/* 3807:3498 */       atom_AST = currentAST.root;
/* 3808:     */     }
/* 3809:     */     catch (RecognitionException ex)
/* 3810:     */     {
/* 3811:3501 */       reportError(ex);
/* 3812:3502 */       recover(ex, _tokenSet_31);
/* 3813:     */     }
/* 3814:3504 */     this.returnAST = atom_AST;
/* 3815:     */   }
/* 3816:     */   
/* 3817:     */   public final void whenClause()
/* 3818:     */     throws RecognitionException, TokenStreamException
/* 3819:     */   {
/* 3820:3509 */     this.returnAST = null;
/* 3821:3510 */     ASTPair currentAST = new ASTPair();
/* 3822:3511 */     AST whenClause_AST = null;
/* 3823:     */     try
/* 3824:     */     {
/* 3825:3515 */       AST tmp102_AST = null;
/* 3826:3516 */       tmp102_AST = this.astFactory.create(LT(1));
/* 3827:3517 */       this.astFactory.makeASTRoot(currentAST, tmp102_AST);
/* 3828:3518 */       match(58);
/* 3829:3519 */       logicalExpression();
/* 3830:3520 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3831:3521 */       match(57);
/* 3832:3522 */       unaryExpression();
/* 3833:3523 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3834:     */       
/* 3835:3525 */       whenClause_AST = currentAST.root;
/* 3836:     */     }
/* 3837:     */     catch (RecognitionException ex)
/* 3838:     */     {
/* 3839:3528 */       reportError(ex);
/* 3840:3529 */       recover(ex, _tokenSet_33);
/* 3841:     */     }
/* 3842:3531 */     this.returnAST = whenClause_AST;
/* 3843:     */   }
/* 3844:     */   
/* 3845:     */   public final void elseClause()
/* 3846:     */     throws RecognitionException, TokenStreamException
/* 3847:     */   {
/* 3848:3536 */     this.returnAST = null;
/* 3849:3537 */     ASTPair currentAST = new ASTPair();
/* 3850:3538 */     AST elseClause_AST = null;
/* 3851:     */     try
/* 3852:     */     {
/* 3853:3542 */       AST tmp104_AST = null;
/* 3854:3543 */       tmp104_AST = this.astFactory.create(LT(1));
/* 3855:3544 */       this.astFactory.makeASTRoot(currentAST, tmp104_AST);
/* 3856:3545 */       match(56);
/* 3857:3546 */       unaryExpression();
/* 3858:3547 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3859:     */       
/* 3860:3549 */       elseClause_AST = currentAST.root;
/* 3861:     */     }
/* 3862:     */     catch (RecognitionException ex)
/* 3863:     */     {
/* 3864:3552 */       reportError(ex);
/* 3865:3553 */       recover(ex, _tokenSet_34);
/* 3866:     */     }
/* 3867:3555 */     this.returnAST = elseClause_AST;
/* 3868:     */   }
/* 3869:     */   
/* 3870:     */   public final void altWhenClause()
/* 3871:     */     throws RecognitionException, TokenStreamException
/* 3872:     */   {
/* 3873:3560 */     this.returnAST = null;
/* 3874:3561 */     ASTPair currentAST = new ASTPair();
/* 3875:3562 */     AST altWhenClause_AST = null;
/* 3876:     */     try
/* 3877:     */     {
/* 3878:3566 */       AST tmp105_AST = null;
/* 3879:3567 */       tmp105_AST = this.astFactory.create(LT(1));
/* 3880:3568 */       this.astFactory.makeASTRoot(currentAST, tmp105_AST);
/* 3881:3569 */       match(58);
/* 3882:3570 */       unaryExpression();
/* 3883:3571 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3884:3572 */       match(57);
/* 3885:3573 */       unaryExpression();
/* 3886:3574 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3887:     */       
/* 3888:3576 */       altWhenClause_AST = currentAST.root;
/* 3889:     */     }
/* 3890:     */     catch (RecognitionException ex)
/* 3891:     */     {
/* 3892:3579 */       reportError(ex);
/* 3893:3580 */       recover(ex, _tokenSet_33);
/* 3894:     */     }
/* 3895:3582 */     this.returnAST = altWhenClause_AST;
/* 3896:     */   }
/* 3897:     */   
/* 3898:     */   public final void collectionExpr()
/* 3899:     */     throws RecognitionException, TokenStreamException
/* 3900:     */   {
/* 3901:3587 */     this.returnAST = null;
/* 3902:3588 */     ASTPair currentAST = new ASTPair();
/* 3903:3589 */     AST collectionExpr_AST = null;
/* 3904:     */     try
/* 3905:     */     {
/* 3906:3593 */       switch (LA(1))
/* 3907:     */       {
/* 3908:     */       case 17: 
/* 3909:3596 */         AST tmp107_AST = null;
/* 3910:3597 */         tmp107_AST = this.astFactory.create(LT(1));
/* 3911:3598 */         this.astFactory.makeASTRoot(currentAST, tmp107_AST);
/* 3912:3599 */         match(17);
/* 3913:3600 */         break;
/* 3914:     */       case 27: 
/* 3915:3604 */         AST tmp108_AST = null;
/* 3916:3605 */         tmp108_AST = this.astFactory.create(LT(1));
/* 3917:3606 */         this.astFactory.makeASTRoot(currentAST, tmp108_AST);
/* 3918:3607 */         match(27);
/* 3919:3608 */         break;
/* 3920:     */       default: 
/* 3921:3612 */         throw new NoViableAltException(LT(1), getFilename());
/* 3922:     */       }
/* 3923:3616 */       match(103);
/* 3924:3617 */       path();
/* 3925:3618 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3926:3619 */       match(104);
/* 3927:3620 */       collectionExpr_AST = currentAST.root;
/* 3928:     */     }
/* 3929:     */     catch (RecognitionException ex)
/* 3930:     */     {
/* 3931:3623 */       reportError(ex);
/* 3932:3624 */       recover(ex, _tokenSet_11);
/* 3933:     */     }
/* 3934:3626 */     this.returnAST = collectionExpr_AST;
/* 3935:     */   }
/* 3936:     */   
/* 3937:     */   public final void subQuery()
/* 3938:     */     throws RecognitionException, TokenStreamException
/* 3939:     */   {
/* 3940:3631 */     this.returnAST = null;
/* 3941:3632 */     ASTPair currentAST = new ASTPair();
/* 3942:3633 */     AST subQuery_AST = null;
/* 3943:     */     try
/* 3944:     */     {
/* 3945:3636 */       union();
/* 3946:3637 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3947:3638 */       subQuery_AST = currentAST.root;
/* 3948:3639 */       subQuery_AST = this.astFactory.make(new ASTArray(2).add(this.astFactory.create(86, "query")).add(subQuery_AST));
/* 3949:3640 */       currentAST.root = subQuery_AST;
/* 3950:3641 */       currentAST.child = ((subQuery_AST != null) && (subQuery_AST.getFirstChild() != null) ? subQuery_AST.getFirstChild() : subQuery_AST);
/* 3951:     */       
/* 3952:3643 */       currentAST.advanceChildToEnd();
/* 3953:3644 */       subQuery_AST = currentAST.root;
/* 3954:     */     }
/* 3955:     */     catch (RecognitionException ex)
/* 3956:     */     {
/* 3957:3647 */       reportError(ex);
/* 3958:3648 */       recover(ex, _tokenSet_13);
/* 3959:     */     }
/* 3960:3650 */     this.returnAST = subQuery_AST;
/* 3961:     */   }
/* 3962:     */   
/* 3963:     */   public final void exprList()
/* 3964:     */     throws RecognitionException, TokenStreamException
/* 3965:     */   {
/* 3966:3655 */     this.returnAST = null;
/* 3967:3656 */     ASTPair currentAST = new ASTPair();
/* 3968:3657 */     AST exprList_AST = null;
/* 3969:3658 */     Token t = null;
/* 3970:3659 */     AST t_AST = null;
/* 3971:3660 */     Token l = null;
/* 3972:3661 */     AST l_AST = null;
/* 3973:3662 */     Token b = null;
/* 3974:3663 */     AST b_AST = null;
/* 3975:     */     
/* 3976:3665 */     AST trimSpec = null;
/* 3977:     */     try
/* 3978:     */     {
/* 3979:3670 */       switch (LA(1))
/* 3980:     */       {
/* 3981:     */       case 67: 
/* 3982:3673 */         t = LT(1);
/* 3983:3674 */         t_AST = this.astFactory.create(t);
/* 3984:3675 */         this.astFactory.addASTChild(currentAST, t_AST);
/* 3985:3676 */         match(67);
/* 3986:3677 */         trimSpec = t_AST;
/* 3987:3678 */         break;
/* 3988:     */       case 63: 
/* 3989:3682 */         l = LT(1);
/* 3990:3683 */         l_AST = this.astFactory.create(l);
/* 3991:3684 */         this.astFactory.addASTChild(currentAST, l_AST);
/* 3992:3685 */         match(63);
/* 3993:3686 */         trimSpec = l_AST;
/* 3994:3687 */         break;
/* 3995:     */       case 61: 
/* 3996:3691 */         b = LT(1);
/* 3997:3692 */         b_AST = this.astFactory.create(b);
/* 3998:3693 */         this.astFactory.addASTChild(currentAST, b_AST);
/* 3999:3694 */         match(61);
/* 4000:3695 */         trimSpec = b_AST;
/* 4001:3696 */         break;
/* 4002:     */       case 4: 
/* 4003:     */       case 5: 
/* 4004:     */       case 9: 
/* 4005:     */       case 12: 
/* 4006:     */       case 17: 
/* 4007:     */       case 19: 
/* 4008:     */       case 20: 
/* 4009:     */       case 22: 
/* 4010:     */       case 27: 
/* 4011:     */       case 35: 
/* 4012:     */       case 36: 
/* 4013:     */       case 38: 
/* 4014:     */       case 39: 
/* 4015:     */       case 47: 
/* 4016:     */       case 48: 
/* 4017:     */       case 49: 
/* 4018:     */       case 54: 
/* 4019:     */       case 62: 
/* 4020:     */       case 95: 
/* 4021:     */       case 96: 
/* 4022:     */       case 97: 
/* 4023:     */       case 98: 
/* 4024:     */       case 99: 
/* 4025:     */       case 103: 
/* 4026:     */       case 104: 
/* 4027:     */       case 115: 
/* 4028:     */       case 116: 
/* 4029:     */       case 122: 
/* 4030:     */       case 123: 
/* 4031:     */       case 124: 
/* 4032:     */       case 125: 
/* 4033:     */       case 126: 
/* 4034:     */         break;
/* 4035:     */       case 6: 
/* 4036:     */       case 7: 
/* 4037:     */       case 8: 
/* 4038:     */       case 10: 
/* 4039:     */       case 11: 
/* 4040:     */       case 13: 
/* 4041:     */       case 14: 
/* 4042:     */       case 15: 
/* 4043:     */       case 16: 
/* 4044:     */       case 18: 
/* 4045:     */       case 21: 
/* 4046:     */       case 23: 
/* 4047:     */       case 24: 
/* 4048:     */       case 25: 
/* 4049:     */       case 26: 
/* 4050:     */       case 28: 
/* 4051:     */       case 29: 
/* 4052:     */       case 30: 
/* 4053:     */       case 31: 
/* 4054:     */       case 32: 
/* 4055:     */       case 33: 
/* 4056:     */       case 34: 
/* 4057:     */       case 37: 
/* 4058:     */       case 40: 
/* 4059:     */       case 41: 
/* 4060:     */       case 42: 
/* 4061:     */       case 43: 
/* 4062:     */       case 44: 
/* 4063:     */       case 45: 
/* 4064:     */       case 46: 
/* 4065:     */       case 50: 
/* 4066:     */       case 51: 
/* 4067:     */       case 52: 
/* 4068:     */       case 53: 
/* 4069:     */       case 55: 
/* 4070:     */       case 56: 
/* 4071:     */       case 57: 
/* 4072:     */       case 58: 
/* 4073:     */       case 59: 
/* 4074:     */       case 60: 
/* 4075:     */       case 64: 
/* 4076:     */       case 65: 
/* 4077:     */       case 66: 
/* 4078:     */       case 68: 
/* 4079:     */       case 69: 
/* 4080:     */       case 70: 
/* 4081:     */       case 71: 
/* 4082:     */       case 72: 
/* 4083:     */       case 73: 
/* 4084:     */       case 74: 
/* 4085:     */       case 75: 
/* 4086:     */       case 76: 
/* 4087:     */       case 77: 
/* 4088:     */       case 78: 
/* 4089:     */       case 79: 
/* 4090:     */       case 80: 
/* 4091:     */       case 81: 
/* 4092:     */       case 82: 
/* 4093:     */       case 83: 
/* 4094:     */       case 84: 
/* 4095:     */       case 85: 
/* 4096:     */       case 86: 
/* 4097:     */       case 87: 
/* 4098:     */       case 88: 
/* 4099:     */       case 89: 
/* 4100:     */       case 90: 
/* 4101:     */       case 91: 
/* 4102:     */       case 92: 
/* 4103:     */       case 93: 
/* 4104:     */       case 94: 
/* 4105:     */       case 100: 
/* 4106:     */       case 101: 
/* 4107:     */       case 102: 
/* 4108:     */       case 105: 
/* 4109:     */       case 106: 
/* 4110:     */       case 107: 
/* 4111:     */       case 108: 
/* 4112:     */       case 109: 
/* 4113:     */       case 110: 
/* 4114:     */       case 111: 
/* 4115:     */       case 112: 
/* 4116:     */       case 113: 
/* 4117:     */       case 114: 
/* 4118:     */       case 117: 
/* 4119:     */       case 118: 
/* 4120:     */       case 119: 
/* 4121:     */       case 120: 
/* 4122:     */       case 121: 
/* 4123:     */       default: 
/* 4124:3735 */         throw new NoViableAltException(LT(1), getFilename());
/* 4125:     */       }
/* 4126:3739 */       if (trimSpec != null) {
/* 4127:3739 */         trimSpec.setType(126);
/* 4128:     */       }
/* 4129:3741 */       switch (LA(1))
/* 4130:     */       {
/* 4131:     */       case 4: 
/* 4132:     */       case 5: 
/* 4133:     */       case 9: 
/* 4134:     */       case 12: 
/* 4135:     */       case 17: 
/* 4136:     */       case 19: 
/* 4137:     */       case 20: 
/* 4138:     */       case 27: 
/* 4139:     */       case 35: 
/* 4140:     */       case 36: 
/* 4141:     */       case 38: 
/* 4142:     */       case 39: 
/* 4143:     */       case 47: 
/* 4144:     */       case 48: 
/* 4145:     */       case 49: 
/* 4146:     */       case 54: 
/* 4147:     */       case 62: 
/* 4148:     */       case 95: 
/* 4149:     */       case 96: 
/* 4150:     */       case 97: 
/* 4151:     */       case 98: 
/* 4152:     */       case 99: 
/* 4153:     */       case 103: 
/* 4154:     */       case 115: 
/* 4155:     */       case 116: 
/* 4156:     */       case 122: 
/* 4157:     */       case 123: 
/* 4158:     */       case 124: 
/* 4159:     */       case 125: 
/* 4160:     */       case 126: 
/* 4161:3773 */         expression();
/* 4162:3774 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4163:3776 */         switch (LA(1))
/* 4164:     */         {
/* 4165:     */         case 101: 
/* 4166:3780 */           int _cnt187 = 0;
/* 4167:     */           for (;;)
/* 4168:     */           {
/* 4169:3783 */             if (LA(1) == 101)
/* 4170:     */             {
/* 4171:3784 */               match(101);
/* 4172:3785 */               expression();
/* 4173:3786 */               this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4174:     */             }
/* 4175:     */             else
/* 4176:     */             {
/* 4177:3789 */               if (_cnt187 >= 1) {
/* 4178:     */                 break;
/* 4179:     */               }
/* 4180:3789 */               throw new NoViableAltException(LT(1), getFilename());
/* 4181:     */             }
/* 4182:3792 */             _cnt187++;
/* 4183:     */           }
/* 4184:3795 */           break;
/* 4185:     */         case 22: 
/* 4186:3799 */           AST tmp112_AST = null;
/* 4187:3800 */           tmp112_AST = this.astFactory.create(LT(1));
/* 4188:3801 */           this.astFactory.addASTChild(currentAST, tmp112_AST);
/* 4189:3802 */           match(22);
/* 4190:3803 */           tmp112_AST.setType(126);
/* 4191:3804 */           expression();
/* 4192:3805 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4193:3806 */           break;
/* 4194:     */         case 7: 
/* 4195:3810 */           match(7);
/* 4196:3811 */           identifier();
/* 4197:3812 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4198:3813 */           break;
/* 4199:     */         case 104: 
/* 4200:     */           break;
/* 4201:     */         default: 
/* 4202:3821 */           throw new NoViableAltException(LT(1), getFilename());
/* 4203:     */         }
/* 4204:     */         break;
/* 4205:     */       case 22: 
/* 4206:3829 */         AST tmp114_AST = null;
/* 4207:3830 */         tmp114_AST = this.astFactory.create(LT(1));
/* 4208:3831 */         this.astFactory.addASTChild(currentAST, tmp114_AST);
/* 4209:3832 */         match(22);
/* 4210:3833 */         tmp114_AST.setType(126);
/* 4211:3834 */         expression();
/* 4212:3835 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4213:3836 */         break;
/* 4214:     */       case 104: 
/* 4215:     */         break;
/* 4216:     */       case 6: 
/* 4217:     */       case 7: 
/* 4218:     */       case 8: 
/* 4219:     */       case 10: 
/* 4220:     */       case 11: 
/* 4221:     */       case 13: 
/* 4222:     */       case 14: 
/* 4223:     */       case 15: 
/* 4224:     */       case 16: 
/* 4225:     */       case 18: 
/* 4226:     */       case 21: 
/* 4227:     */       case 23: 
/* 4228:     */       case 24: 
/* 4229:     */       case 25: 
/* 4230:     */       case 26: 
/* 4231:     */       case 28: 
/* 4232:     */       case 29: 
/* 4233:     */       case 30: 
/* 4234:     */       case 31: 
/* 4235:     */       case 32: 
/* 4236:     */       case 33: 
/* 4237:     */       case 34: 
/* 4238:     */       case 37: 
/* 4239:     */       case 40: 
/* 4240:     */       case 41: 
/* 4241:     */       case 42: 
/* 4242:     */       case 43: 
/* 4243:     */       case 44: 
/* 4244:     */       case 45: 
/* 4245:     */       case 46: 
/* 4246:     */       case 50: 
/* 4247:     */       case 51: 
/* 4248:     */       case 52: 
/* 4249:     */       case 53: 
/* 4250:     */       case 55: 
/* 4251:     */       case 56: 
/* 4252:     */       case 57: 
/* 4253:     */       case 58: 
/* 4254:     */       case 59: 
/* 4255:     */       case 60: 
/* 4256:     */       case 61: 
/* 4257:     */       case 63: 
/* 4258:     */       case 64: 
/* 4259:     */       case 65: 
/* 4260:     */       case 66: 
/* 4261:     */       case 67: 
/* 4262:     */       case 68: 
/* 4263:     */       case 69: 
/* 4264:     */       case 70: 
/* 4265:     */       case 71: 
/* 4266:     */       case 72: 
/* 4267:     */       case 73: 
/* 4268:     */       case 74: 
/* 4269:     */       case 75: 
/* 4270:     */       case 76: 
/* 4271:     */       case 77: 
/* 4272:     */       case 78: 
/* 4273:     */       case 79: 
/* 4274:     */       case 80: 
/* 4275:     */       case 81: 
/* 4276:     */       case 82: 
/* 4277:     */       case 83: 
/* 4278:     */       case 84: 
/* 4279:     */       case 85: 
/* 4280:     */       case 86: 
/* 4281:     */       case 87: 
/* 4282:     */       case 88: 
/* 4283:     */       case 89: 
/* 4284:     */       case 90: 
/* 4285:     */       case 91: 
/* 4286:     */       case 92: 
/* 4287:     */       case 93: 
/* 4288:     */       case 94: 
/* 4289:     */       case 100: 
/* 4290:     */       case 101: 
/* 4291:     */       case 102: 
/* 4292:     */       case 105: 
/* 4293:     */       case 106: 
/* 4294:     */       case 107: 
/* 4295:     */       case 108: 
/* 4296:     */       case 109: 
/* 4297:     */       case 110: 
/* 4298:     */       case 111: 
/* 4299:     */       case 112: 
/* 4300:     */       case 113: 
/* 4301:     */       case 114: 
/* 4302:     */       case 117: 
/* 4303:     */       case 118: 
/* 4304:     */       case 119: 
/* 4305:     */       case 120: 
/* 4306:     */       case 121: 
/* 4307:     */       default: 
/* 4308:3844 */         throw new NoViableAltException(LT(1), getFilename());
/* 4309:     */       }
/* 4310:3848 */       exprList_AST = currentAST.root;
/* 4311:3849 */       exprList_AST = this.astFactory.make(new ASTArray(2).add(this.astFactory.create(75, "exprList")).add(exprList_AST));
/* 4312:3850 */       currentAST.root = exprList_AST;
/* 4313:3851 */       currentAST.child = ((exprList_AST != null) && (exprList_AST.getFirstChild() != null) ? exprList_AST.getFirstChild() : exprList_AST);
/* 4314:     */       
/* 4315:3853 */       currentAST.advanceChildToEnd();
/* 4316:3854 */       exprList_AST = currentAST.root;
/* 4317:     */     }
/* 4318:     */     catch (RecognitionException ex)
/* 4319:     */     {
/* 4320:3857 */       reportError(ex);
/* 4321:3858 */       recover(ex, _tokenSet_13);
/* 4322:     */     }
/* 4323:3860 */     this.returnAST = exprList_AST;
/* 4324:     */   }
/* 4325:     */   
/* 4326:     */   public final void identPrimary()
/* 4327:     */     throws RecognitionException, TokenStreamException
/* 4328:     */   {
/* 4329:3865 */     this.returnAST = null;
/* 4330:3866 */     ASTPair currentAST = new ASTPair();
/* 4331:3867 */     AST identPrimary_AST = null;
/* 4332:3868 */     AST i_AST = null;
/* 4333:3869 */     Token o = null;
/* 4334:3870 */     AST o_AST = null;
/* 4335:3871 */     Token op = null;
/* 4336:3872 */     AST op_AST = null;
/* 4337:3873 */     AST e_AST = null;
/* 4338:     */     try
/* 4339:     */     {
/* 4340:3876 */       switch (LA(1))
/* 4341:     */       {
/* 4342:     */       case 126: 
/* 4343:3879 */         identifier();
/* 4344:3880 */         i_AST = this.returnAST;
/* 4345:3881 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4346:3882 */         handleDotIdent();
/* 4347:3886 */         while ((LA(1) == 15) && ((LA(2) == 17) || (LA(2) == 65) || (LA(2) == 126)) && (_tokenSet_35.member(LA(3))))
/* 4348:     */         {
/* 4349:3887 */           AST tmp115_AST = null;
/* 4350:3888 */           tmp115_AST = this.astFactory.create(LT(1));
/* 4351:3889 */           this.astFactory.makeASTRoot(currentAST, tmp115_AST);
/* 4352:3890 */           match(15);
/* 4353:3892 */           switch (LA(1))
/* 4354:     */           {
/* 4355:     */           case 126: 
/* 4356:3895 */             identifier();
/* 4357:3896 */             this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4358:3897 */             break;
/* 4359:     */           case 17: 
/* 4360:3901 */             AST tmp116_AST = null;
/* 4361:3902 */             tmp116_AST = this.astFactory.create(LT(1));
/* 4362:3903 */             this.astFactory.addASTChild(currentAST, tmp116_AST);
/* 4363:3904 */             match(17);
/* 4364:3905 */             break;
/* 4365:     */           case 65: 
/* 4366:3909 */             o = LT(1);
/* 4367:3910 */             o_AST = this.astFactory.create(o);
/* 4368:3911 */             this.astFactory.addASTChild(currentAST, o_AST);
/* 4369:3912 */             match(65);
/* 4370:3913 */             o_AST.setType(126);
/* 4371:3914 */             break;
/* 4372:     */           default: 
/* 4373:3918 */             throw new NoViableAltException(LT(1), getFilename());
/* 4374:     */           }
/* 4375:     */         }
/* 4376:3930 */         switch (LA(1))
/* 4377:     */         {
/* 4378:     */         case 103: 
/* 4379:3934 */           op = LT(1);
/* 4380:3935 */           op_AST = this.astFactory.create(op);
/* 4381:3936 */           this.astFactory.makeASTRoot(currentAST, op_AST);
/* 4382:3937 */           match(103);
/* 4383:3938 */           op_AST.setType(81);
/* 4384:3939 */           exprList();
/* 4385:3940 */           e_AST = this.returnAST;
/* 4386:3941 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4387:3942 */           match(104);
/* 4388:     */           
/* 4389:3944 */           identPrimary_AST = currentAST.root;
/* 4390:     */           
/* 4391:3946 */           AST path = e_AST.getFirstChild();
/* 4392:3947 */           if (i_AST.getText().equals("key")) {
/* 4393:3948 */             identPrimary_AST = this.astFactory.make(new ASTArray(2).add(this.astFactory.create(68)).add(path));
/* 4394:3950 */           } else if (i_AST.getText().equals("value")) {
/* 4395:3951 */             identPrimary_AST = this.astFactory.make(new ASTArray(2).add(this.astFactory.create(69)).add(path));
/* 4396:3953 */           } else if (i_AST.getText().equals("entry")) {
/* 4397:3954 */             identPrimary_AST = this.astFactory.make(new ASTArray(2).add(this.astFactory.create(70)).add(path));
/* 4398:     */           }
/* 4399:3957 */           currentAST.root = identPrimary_AST;
/* 4400:3958 */           currentAST.child = ((identPrimary_AST != null) && (identPrimary_AST.getFirstChild() != null) ? identPrimary_AST.getFirstChild() : identPrimary_AST);
/* 4401:     */           
/* 4402:3960 */           currentAST.advanceChildToEnd();
/* 4403:3961 */           break;
/* 4404:     */         case 1: 
/* 4405:     */         case 6: 
/* 4406:     */         case 7: 
/* 4407:     */         case 8: 
/* 4408:     */         case 10: 
/* 4409:     */         case 14: 
/* 4410:     */         case 15: 
/* 4411:     */         case 18: 
/* 4412:     */         case 22: 
/* 4413:     */         case 23: 
/* 4414:     */         case 24: 
/* 4415:     */         case 25: 
/* 4416:     */         case 26: 
/* 4417:     */         case 28: 
/* 4418:     */         case 31: 
/* 4419:     */         case 32: 
/* 4420:     */         case 33: 
/* 4421:     */         case 34: 
/* 4422:     */         case 38: 
/* 4423:     */         case 40: 
/* 4424:     */         case 41: 
/* 4425:     */         case 44: 
/* 4426:     */         case 50: 
/* 4427:     */         case 53: 
/* 4428:     */         case 55: 
/* 4429:     */         case 56: 
/* 4430:     */         case 57: 
/* 4431:     */         case 58: 
/* 4432:     */         case 64: 
/* 4433:     */         case 101: 
/* 4434:     */         case 102: 
/* 4435:     */         case 104: 
/* 4436:     */         case 106: 
/* 4437:     */         case 107: 
/* 4438:     */         case 108: 
/* 4439:     */         case 109: 
/* 4440:     */         case 110: 
/* 4441:     */         case 111: 
/* 4442:     */         case 112: 
/* 4443:     */         case 113: 
/* 4444:     */         case 114: 
/* 4445:     */         case 115: 
/* 4446:     */         case 116: 
/* 4447:     */         case 117: 
/* 4448:     */         case 118: 
/* 4449:     */         case 119: 
/* 4450:     */         case 120: 
/* 4451:     */         case 121: 
/* 4452:     */           break;
/* 4453:     */         case 2: 
/* 4454:     */         case 3: 
/* 4455:     */         case 4: 
/* 4456:     */         case 5: 
/* 4457:     */         case 9: 
/* 4458:     */         case 11: 
/* 4459:     */         case 12: 
/* 4460:     */         case 13: 
/* 4461:     */         case 16: 
/* 4462:     */         case 17: 
/* 4463:     */         case 19: 
/* 4464:     */         case 20: 
/* 4465:     */         case 21: 
/* 4466:     */         case 27: 
/* 4467:     */         case 29: 
/* 4468:     */         case 30: 
/* 4469:     */         case 35: 
/* 4470:     */         case 36: 
/* 4471:     */         case 37: 
/* 4472:     */         case 39: 
/* 4473:     */         case 42: 
/* 4474:     */         case 43: 
/* 4475:     */         case 45: 
/* 4476:     */         case 46: 
/* 4477:     */         case 47: 
/* 4478:     */         case 48: 
/* 4479:     */         case 49: 
/* 4480:     */         case 51: 
/* 4481:     */         case 52: 
/* 4482:     */         case 54: 
/* 4483:     */         case 59: 
/* 4484:     */         case 60: 
/* 4485:     */         case 61: 
/* 4486:     */         case 62: 
/* 4487:     */         case 63: 
/* 4488:     */         case 65: 
/* 4489:     */         case 66: 
/* 4490:     */         case 67: 
/* 4491:     */         case 68: 
/* 4492:     */         case 69: 
/* 4493:     */         case 70: 
/* 4494:     */         case 71: 
/* 4495:     */         case 72: 
/* 4496:     */         case 73: 
/* 4497:     */         case 74: 
/* 4498:     */         case 75: 
/* 4499:     */         case 76: 
/* 4500:     */         case 77: 
/* 4501:     */         case 78: 
/* 4502:     */         case 79: 
/* 4503:     */         case 80: 
/* 4504:     */         case 81: 
/* 4505:     */         case 82: 
/* 4506:     */         case 83: 
/* 4507:     */         case 84: 
/* 4508:     */         case 85: 
/* 4509:     */         case 86: 
/* 4510:     */         case 87: 
/* 4511:     */         case 88: 
/* 4512:     */         case 89: 
/* 4513:     */         case 90: 
/* 4514:     */         case 91: 
/* 4515:     */         case 92: 
/* 4516:     */         case 93: 
/* 4517:     */         case 94: 
/* 4518:     */         case 95: 
/* 4519:     */         case 96: 
/* 4520:     */         case 97: 
/* 4521:     */         case 98: 
/* 4522:     */         case 99: 
/* 4523:     */         case 100: 
/* 4524:     */         case 105: 
/* 4525:     */         default: 
/* 4526:4016 */           throw new NoViableAltException(LT(1), getFilename());
/* 4527:     */         }
/* 4528:4020 */         identPrimary_AST = currentAST.root;
/* 4529:4021 */         break;
/* 4530:     */       case 9: 
/* 4531:     */       case 12: 
/* 4532:     */       case 17: 
/* 4533:     */       case 27: 
/* 4534:     */       case 35: 
/* 4535:     */       case 36: 
/* 4536:     */       case 48: 
/* 4537:4031 */         aggregate();
/* 4538:4032 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4539:4033 */         identPrimary_AST = currentAST.root;
/* 4540:4034 */         break;
/* 4541:     */       default: 
/* 4542:4038 */         throw new NoViableAltException(LT(1), getFilename());
/* 4543:     */       }
/* 4544:     */     }
/* 4545:     */     catch (RecognitionException ex)
/* 4546:     */     {
/* 4547:4043 */       reportError(ex);
/* 4548:4044 */       recover(ex, _tokenSet_11);
/* 4549:     */     }
/* 4550:4046 */     this.returnAST = identPrimary_AST;
/* 4551:     */   }
/* 4552:     */   
/* 4553:     */   public final void constant()
/* 4554:     */     throws RecognitionException, TokenStreamException
/* 4555:     */   {
/* 4556:4051 */     this.returnAST = null;
/* 4557:4052 */     ASTPair currentAST = new ASTPair();
/* 4558:4053 */     AST constant_AST = null;
/* 4559:     */     try
/* 4560:     */     {
/* 4561:4056 */       switch (LA(1))
/* 4562:     */       {
/* 4563:     */       case 124: 
/* 4564:4059 */         AST tmp118_AST = null;
/* 4565:4060 */         tmp118_AST = this.astFactory.create(LT(1));
/* 4566:4061 */         this.astFactory.addASTChild(currentAST, tmp118_AST);
/* 4567:4062 */         match(124);
/* 4568:4063 */         constant_AST = currentAST.root;
/* 4569:4064 */         break;
/* 4570:     */       case 96: 
/* 4571:4068 */         AST tmp119_AST = null;
/* 4572:4069 */         tmp119_AST = this.astFactory.create(LT(1));
/* 4573:4070 */         this.astFactory.addASTChild(currentAST, tmp119_AST);
/* 4574:4071 */         match(96);
/* 4575:4072 */         constant_AST = currentAST.root;
/* 4576:4073 */         break;
/* 4577:     */       case 97: 
/* 4578:4077 */         AST tmp120_AST = null;
/* 4579:4078 */         tmp120_AST = this.astFactory.create(LT(1));
/* 4580:4079 */         this.astFactory.addASTChild(currentAST, tmp120_AST);
/* 4581:4080 */         match(97);
/* 4582:4081 */         constant_AST = currentAST.root;
/* 4583:4082 */         break;
/* 4584:     */       case 95: 
/* 4585:4086 */         AST tmp121_AST = null;
/* 4586:4087 */         tmp121_AST = this.astFactory.create(LT(1));
/* 4587:4088 */         this.astFactory.addASTChild(currentAST, tmp121_AST);
/* 4588:4089 */         match(95);
/* 4589:4090 */         constant_AST = currentAST.root;
/* 4590:4091 */         break;
/* 4591:     */       case 98: 
/* 4592:4095 */         AST tmp122_AST = null;
/* 4593:4096 */         tmp122_AST = this.astFactory.create(LT(1));
/* 4594:4097 */         this.astFactory.addASTChild(currentAST, tmp122_AST);
/* 4595:4098 */         match(98);
/* 4596:4099 */         constant_AST = currentAST.root;
/* 4597:4100 */         break;
/* 4598:     */       case 99: 
/* 4599:4104 */         AST tmp123_AST = null;
/* 4600:4105 */         tmp123_AST = this.astFactory.create(LT(1));
/* 4601:4106 */         this.astFactory.addASTChild(currentAST, tmp123_AST);
/* 4602:4107 */         match(99);
/* 4603:4108 */         constant_AST = currentAST.root;
/* 4604:4109 */         break;
/* 4605:     */       case 125: 
/* 4606:4113 */         AST tmp124_AST = null;
/* 4607:4114 */         tmp124_AST = this.astFactory.create(LT(1));
/* 4608:4115 */         this.astFactory.addASTChild(currentAST, tmp124_AST);
/* 4609:4116 */         match(125);
/* 4610:4117 */         constant_AST = currentAST.root;
/* 4611:4118 */         break;
/* 4612:     */       case 39: 
/* 4613:4122 */         AST tmp125_AST = null;
/* 4614:4123 */         tmp125_AST = this.astFactory.create(LT(1));
/* 4615:4124 */         this.astFactory.addASTChild(currentAST, tmp125_AST);
/* 4616:4125 */         match(39);
/* 4617:4126 */         constant_AST = currentAST.root;
/* 4618:4127 */         break;
/* 4619:     */       case 49: 
/* 4620:4131 */         AST tmp126_AST = null;
/* 4621:4132 */         tmp126_AST = this.astFactory.create(LT(1));
/* 4622:4133 */         this.astFactory.addASTChild(currentAST, tmp126_AST);
/* 4623:4134 */         match(49);
/* 4624:4135 */         constant_AST = currentAST.root;
/* 4625:4136 */         break;
/* 4626:     */       case 20: 
/* 4627:4140 */         AST tmp127_AST = null;
/* 4628:4141 */         tmp127_AST = this.astFactory.create(LT(1));
/* 4629:4142 */         this.astFactory.addASTChild(currentAST, tmp127_AST);
/* 4630:4143 */         match(20);
/* 4631:4144 */         constant_AST = currentAST.root;
/* 4632:4145 */         break;
/* 4633:     */       case 62: 
/* 4634:4149 */         AST tmp128_AST = null;
/* 4635:4150 */         tmp128_AST = this.astFactory.create(LT(1));
/* 4636:4151 */         this.astFactory.addASTChild(currentAST, tmp128_AST);
/* 4637:4152 */         match(62);
/* 4638:4153 */         constant_AST = currentAST.root;
/* 4639:4154 */         break;
/* 4640:     */       default: 
/* 4641:4158 */         throw new NoViableAltException(LT(1), getFilename());
/* 4642:     */       }
/* 4643:     */     }
/* 4644:     */     catch (RecognitionException ex)
/* 4645:     */     {
/* 4646:4163 */       reportError(ex);
/* 4647:4164 */       recover(ex, _tokenSet_11);
/* 4648:     */     }
/* 4649:4166 */     this.returnAST = constant_AST;
/* 4650:     */   }
/* 4651:     */   
/* 4652:     */   public final void parameter()
/* 4653:     */     throws RecognitionException, TokenStreamException
/* 4654:     */   {
/* 4655:4171 */     this.returnAST = null;
/* 4656:4172 */     ASTPair currentAST = new ASTPair();
/* 4657:4173 */     AST parameter_AST = null;
/* 4658:     */     try
/* 4659:     */     {
/* 4660:4176 */       switch (LA(1))
/* 4661:     */       {
/* 4662:     */       case 122: 
/* 4663:4179 */         AST tmp129_AST = null;
/* 4664:4180 */         tmp129_AST = this.astFactory.create(LT(1));
/* 4665:4181 */         this.astFactory.makeASTRoot(currentAST, tmp129_AST);
/* 4666:4182 */         match(122);
/* 4667:4183 */         identifier();
/* 4668:4184 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4669:4185 */         parameter_AST = currentAST.root;
/* 4670:4186 */         break;
/* 4671:     */       case 123: 
/* 4672:4190 */         AST tmp130_AST = null;
/* 4673:4191 */         tmp130_AST = this.astFactory.create(LT(1));
/* 4674:4192 */         this.astFactory.makeASTRoot(currentAST, tmp130_AST);
/* 4675:4193 */         match(123);
/* 4676:4195 */         switch (LA(1))
/* 4677:     */         {
/* 4678:     */         case 124: 
/* 4679:4198 */           AST tmp131_AST = null;
/* 4680:4199 */           tmp131_AST = this.astFactory.create(LT(1));
/* 4681:4200 */           this.astFactory.addASTChild(currentAST, tmp131_AST);
/* 4682:4201 */           match(124);
/* 4683:4202 */           break;
/* 4684:     */         case 1: 
/* 4685:     */         case 6: 
/* 4686:     */         case 7: 
/* 4687:     */         case 8: 
/* 4688:     */         case 10: 
/* 4689:     */         case 14: 
/* 4690:     */         case 15: 
/* 4691:     */         case 18: 
/* 4692:     */         case 22: 
/* 4693:     */         case 23: 
/* 4694:     */         case 24: 
/* 4695:     */         case 25: 
/* 4696:     */         case 26: 
/* 4697:     */         case 28: 
/* 4698:     */         case 31: 
/* 4699:     */         case 32: 
/* 4700:     */         case 33: 
/* 4701:     */         case 34: 
/* 4702:     */         case 38: 
/* 4703:     */         case 40: 
/* 4704:     */         case 41: 
/* 4705:     */         case 44: 
/* 4706:     */         case 50: 
/* 4707:     */         case 53: 
/* 4708:     */         case 55: 
/* 4709:     */         case 56: 
/* 4710:     */         case 57: 
/* 4711:     */         case 58: 
/* 4712:     */         case 64: 
/* 4713:     */         case 101: 
/* 4714:     */         case 102: 
/* 4715:     */         case 104: 
/* 4716:     */         case 106: 
/* 4717:     */         case 107: 
/* 4718:     */         case 108: 
/* 4719:     */         case 109: 
/* 4720:     */         case 110: 
/* 4721:     */         case 111: 
/* 4722:     */         case 112: 
/* 4723:     */         case 113: 
/* 4724:     */         case 114: 
/* 4725:     */         case 115: 
/* 4726:     */         case 116: 
/* 4727:     */         case 117: 
/* 4728:     */         case 118: 
/* 4729:     */         case 119: 
/* 4730:     */         case 120: 
/* 4731:     */         case 121: 
/* 4732:     */           break;
/* 4733:     */         case 2: 
/* 4734:     */         case 3: 
/* 4735:     */         case 4: 
/* 4736:     */         case 5: 
/* 4737:     */         case 9: 
/* 4738:     */         case 11: 
/* 4739:     */         case 12: 
/* 4740:     */         case 13: 
/* 4741:     */         case 16: 
/* 4742:     */         case 17: 
/* 4743:     */         case 19: 
/* 4744:     */         case 20: 
/* 4745:     */         case 21: 
/* 4746:     */         case 27: 
/* 4747:     */         case 29: 
/* 4748:     */         case 30: 
/* 4749:     */         case 35: 
/* 4750:     */         case 36: 
/* 4751:     */         case 37: 
/* 4752:     */         case 39: 
/* 4753:     */         case 42: 
/* 4754:     */         case 43: 
/* 4755:     */         case 45: 
/* 4756:     */         case 46: 
/* 4757:     */         case 47: 
/* 4758:     */         case 48: 
/* 4759:     */         case 49: 
/* 4760:     */         case 51: 
/* 4761:     */         case 52: 
/* 4762:     */         case 54: 
/* 4763:     */         case 59: 
/* 4764:     */         case 60: 
/* 4765:     */         case 61: 
/* 4766:     */         case 62: 
/* 4767:     */         case 63: 
/* 4768:     */         case 65: 
/* 4769:     */         case 66: 
/* 4770:     */         case 67: 
/* 4771:     */         case 68: 
/* 4772:     */         case 69: 
/* 4773:     */         case 70: 
/* 4774:     */         case 71: 
/* 4775:     */         case 72: 
/* 4776:     */         case 73: 
/* 4777:     */         case 74: 
/* 4778:     */         case 75: 
/* 4779:     */         case 76: 
/* 4780:     */         case 77: 
/* 4781:     */         case 78: 
/* 4782:     */         case 79: 
/* 4783:     */         case 80: 
/* 4784:     */         case 81: 
/* 4785:     */         case 82: 
/* 4786:     */         case 83: 
/* 4787:     */         case 84: 
/* 4788:     */         case 85: 
/* 4789:     */         case 86: 
/* 4790:     */         case 87: 
/* 4791:     */         case 88: 
/* 4792:     */         case 89: 
/* 4793:     */         case 90: 
/* 4794:     */         case 91: 
/* 4795:     */         case 92: 
/* 4796:     */         case 93: 
/* 4797:     */         case 94: 
/* 4798:     */         case 95: 
/* 4799:     */         case 96: 
/* 4800:     */         case 97: 
/* 4801:     */         case 98: 
/* 4802:     */         case 99: 
/* 4803:     */         case 100: 
/* 4804:     */         case 103: 
/* 4805:     */         case 105: 
/* 4806:     */         case 122: 
/* 4807:     */         case 123: 
/* 4808:     */         default: 
/* 4809:4257 */           throw new NoViableAltException(LT(1), getFilename());
/* 4810:     */         }
/* 4811:4261 */         parameter_AST = currentAST.root;
/* 4812:4262 */         break;
/* 4813:     */       default: 
/* 4814:4266 */         throw new NoViableAltException(LT(1), getFilename());
/* 4815:     */       }
/* 4816:     */     }
/* 4817:     */     catch (RecognitionException ex)
/* 4818:     */     {
/* 4819:4271 */       reportError(ex);
/* 4820:4272 */       recover(ex, _tokenSet_11);
/* 4821:     */     }
/* 4822:4274 */     this.returnAST = parameter_AST;
/* 4823:     */   }
/* 4824:     */   
/* 4825:     */   public final void expressionOrVector()
/* 4826:     */     throws RecognitionException, TokenStreamException
/* 4827:     */   {
/* 4828:4279 */     this.returnAST = null;
/* 4829:4280 */     ASTPair currentAST = new ASTPair();
/* 4830:4281 */     AST expressionOrVector_AST = null;
/* 4831:4282 */     AST e_AST = null;
/* 4832:4283 */     AST v_AST = null;
/* 4833:     */     try
/* 4834:     */     {
/* 4835:4286 */       expression();
/* 4836:4287 */       e_AST = this.returnAST;
/* 4837:4289 */       switch (LA(1))
/* 4838:     */       {
/* 4839:     */       case 101: 
/* 4840:4292 */         vectorExpr();
/* 4841:4293 */         v_AST = this.returnAST;
/* 4842:4294 */         break;
/* 4843:     */       case 104: 
/* 4844:     */         break;
/* 4845:     */       default: 
/* 4846:4302 */         throw new NoViableAltException(LT(1), getFilename());
/* 4847:     */       }
/* 4848:4306 */       expressionOrVector_AST = currentAST.root;
/* 4849:4309 */       if (v_AST != null) {
/* 4850:4310 */         expressionOrVector_AST = this.astFactory.make(new ASTArray(3).add(this.astFactory.create(92, "{vector}")).add(e_AST).add(v_AST));
/* 4851:     */       } else {
/* 4852:4312 */         expressionOrVector_AST = e_AST;
/* 4853:     */       }
/* 4854:4314 */       currentAST.root = expressionOrVector_AST;
/* 4855:4315 */       currentAST.child = ((expressionOrVector_AST != null) && (expressionOrVector_AST.getFirstChild() != null) ? expressionOrVector_AST.getFirstChild() : expressionOrVector_AST);
/* 4856:     */       
/* 4857:4317 */       currentAST.advanceChildToEnd();
/* 4858:     */     }
/* 4859:     */     catch (RecognitionException ex)
/* 4860:     */     {
/* 4861:4320 */       reportError(ex);
/* 4862:4321 */       recover(ex, _tokenSet_13);
/* 4863:     */     }
/* 4864:4323 */     this.returnAST = expressionOrVector_AST;
/* 4865:     */   }
/* 4866:     */   
/* 4867:     */   public final void vectorExpr()
/* 4868:     */     throws RecognitionException, TokenStreamException
/* 4869:     */   {
/* 4870:4328 */     this.returnAST = null;
/* 4871:4329 */     ASTPair currentAST = new ASTPair();
/* 4872:4330 */     AST vectorExpr_AST = null;
/* 4873:     */     try
/* 4874:     */     {
/* 4875:4333 */       match(101);
/* 4876:4334 */       expression();
/* 4877:4335 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4878:4339 */       while (LA(1) == 101)
/* 4879:     */       {
/* 4880:4340 */         match(101);
/* 4881:4341 */         expression();
/* 4882:4342 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4883:     */       }
/* 4884:4350 */       vectorExpr_AST = currentAST.root;
/* 4885:     */     }
/* 4886:     */     catch (RecognitionException ex)
/* 4887:     */     {
/* 4888:4353 */       reportError(ex);
/* 4889:4354 */       recover(ex, _tokenSet_13);
/* 4890:     */     }
/* 4891:4356 */     this.returnAST = vectorExpr_AST;
/* 4892:     */   }
/* 4893:     */   
/* 4894:     */   public final void aggregate()
/* 4895:     */     throws RecognitionException, TokenStreamException
/* 4896:     */   {
/* 4897:4361 */     this.returnAST = null;
/* 4898:4362 */     ASTPair currentAST = new ASTPair();
/* 4899:4363 */     AST aggregate_AST = null;
/* 4900:     */     try
/* 4901:     */     {
/* 4902:4366 */       switch (LA(1))
/* 4903:     */       {
/* 4904:     */       case 9: 
/* 4905:     */       case 35: 
/* 4906:     */       case 36: 
/* 4907:     */       case 48: 
/* 4908:4373 */         switch (LA(1))
/* 4909:     */         {
/* 4910:     */         case 48: 
/* 4911:4376 */           AST tmp134_AST = null;
/* 4912:4377 */           tmp134_AST = this.astFactory.create(LT(1));
/* 4913:4378 */           this.astFactory.makeASTRoot(currentAST, tmp134_AST);
/* 4914:4379 */           match(48);
/* 4915:4380 */           break;
/* 4916:     */         case 9: 
/* 4917:4384 */           AST tmp135_AST = null;
/* 4918:4385 */           tmp135_AST = this.astFactory.create(LT(1));
/* 4919:4386 */           this.astFactory.makeASTRoot(currentAST, tmp135_AST);
/* 4920:4387 */           match(9);
/* 4921:4388 */           break;
/* 4922:     */         case 35: 
/* 4923:4392 */           AST tmp136_AST = null;
/* 4924:4393 */           tmp136_AST = this.astFactory.create(LT(1));
/* 4925:4394 */           this.astFactory.makeASTRoot(currentAST, tmp136_AST);
/* 4926:4395 */           match(35);
/* 4927:4396 */           break;
/* 4928:     */         case 36: 
/* 4929:4400 */           AST tmp137_AST = null;
/* 4930:4401 */           tmp137_AST = this.astFactory.create(LT(1));
/* 4931:4402 */           this.astFactory.makeASTRoot(currentAST, tmp137_AST);
/* 4932:4403 */           match(36);
/* 4933:4404 */           break;
/* 4934:     */         default: 
/* 4935:4408 */           throw new NoViableAltException(LT(1), getFilename());
/* 4936:     */         }
/* 4937:4412 */         match(103);
/* 4938:4413 */         additiveExpression();
/* 4939:4414 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4940:4415 */         match(104);
/* 4941:4416 */         aggregate_AST = currentAST.root;
/* 4942:4417 */         aggregate_AST.setType(71);
/* 4943:4418 */         aggregate_AST = currentAST.root;
/* 4944:4419 */         break;
/* 4945:     */       case 12: 
/* 4946:4423 */         AST tmp140_AST = null;
/* 4947:4424 */         tmp140_AST = this.astFactory.create(LT(1));
/* 4948:4425 */         this.astFactory.makeASTRoot(currentAST, tmp140_AST);
/* 4949:4426 */         match(12);
/* 4950:4427 */         match(103);
/* 4951:4429 */         switch (LA(1))
/* 4952:     */         {
/* 4953:     */         case 117: 
/* 4954:4432 */           AST tmp142_AST = null;
/* 4955:4433 */           tmp142_AST = this.astFactory.create(LT(1));
/* 4956:4434 */           this.astFactory.addASTChild(currentAST, tmp142_AST);
/* 4957:4435 */           match(117);
/* 4958:4436 */           tmp142_AST.setType(88);
/* 4959:4437 */           break;
/* 4960:     */         case 4: 
/* 4961:     */         case 16: 
/* 4962:     */         case 17: 
/* 4963:     */         case 27: 
/* 4964:     */         case 126: 
/* 4965:4447 */           switch (LA(1))
/* 4966:     */           {
/* 4967:     */           case 16: 
/* 4968:4450 */             AST tmp143_AST = null;
/* 4969:4451 */             tmp143_AST = this.astFactory.create(LT(1));
/* 4970:4452 */             this.astFactory.addASTChild(currentAST, tmp143_AST);
/* 4971:4453 */             match(16);
/* 4972:4454 */             break;
/* 4973:     */           case 4: 
/* 4974:4458 */             AST tmp144_AST = null;
/* 4975:4459 */             tmp144_AST = this.astFactory.create(LT(1));
/* 4976:4460 */             this.astFactory.addASTChild(currentAST, tmp144_AST);
/* 4977:4461 */             match(4);
/* 4978:4462 */             break;
/* 4979:     */           case 17: 
/* 4980:     */           case 27: 
/* 4981:     */           case 126: 
/* 4982:     */             break;
/* 4983:     */           default: 
/* 4984:4472 */             throw new NoViableAltException(LT(1), getFilename());
/* 4985:     */           }
/* 4986:4477 */           switch (LA(1))
/* 4987:     */           {
/* 4988:     */           case 126: 
/* 4989:4480 */             path();
/* 4990:4481 */             this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4991:4482 */             break;
/* 4992:     */           case 17: 
/* 4993:     */           case 27: 
/* 4994:4487 */             collectionExpr();
/* 4995:4488 */             this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4996:4489 */             break;
/* 4997:     */           default: 
/* 4998:4493 */             throw new NoViableAltException(LT(1), getFilename());
/* 4999:     */           }
/* 5000:     */           break;
/* 5001:     */         default: 
/* 5002:4502 */           throw new NoViableAltException(LT(1), getFilename());
/* 5003:     */         }
/* 5004:4506 */         match(104);
/* 5005:4507 */         aggregate_AST = currentAST.root;
/* 5006:4508 */         break;
/* 5007:     */       case 17: 
/* 5008:     */       case 27: 
/* 5009:4513 */         collectionExpr();
/* 5010:4514 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 5011:4515 */         aggregate_AST = currentAST.root;
/* 5012:4516 */         break;
/* 5013:     */       default: 
/* 5014:4520 */         throw new NoViableAltException(LT(1), getFilename());
/* 5015:     */       }
/* 5016:     */     }
/* 5017:     */     catch (RecognitionException ex)
/* 5018:     */     {
/* 5019:4525 */       reportError(ex);
/* 5020:4526 */       recover(ex, _tokenSet_11);
/* 5021:     */     }
/* 5022:4528 */     this.returnAST = aggregate_AST;
/* 5023:     */   }
/* 5024:     */   
/* 5025:4532 */   public static final String[] _tokenNames = { "<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "\"all\"", "\"any\"", "\"and\"", "\"as\"", "\"asc\"", "\"avg\"", "\"between\"", "\"class\"", "\"count\"", "\"delete\"", "\"desc\"", "DOT", "\"distinct\"", "\"elements\"", "\"escape\"", "\"exists\"", "\"false\"", "\"fetch\"", "\"from\"", "\"full\"", "\"group\"", "\"having\"", "\"in\"", "\"indices\"", "\"inner\"", "\"insert\"", "\"into\"", "\"is\"", "\"join\"", "\"left\"", "\"like\"", "\"max\"", "\"min\"", "\"new\"", "\"not\"", "\"null\"", "\"or\"", "\"order\"", "\"outer\"", "\"properties\"", "\"right\"", "\"select\"", "\"set\"", "\"some\"", "\"sum\"", "\"true\"", "\"union\"", "\"update\"", "\"versioned\"", "\"where\"", "\"case\"", "\"end\"", "\"else\"", "\"then\"", "\"when\"", "\"on\"", "\"with\"", "\"both\"", "\"empty\"", "\"leading\"", "\"member\"", "\"object\"", "\"of\"", "\"trailing\"", "KEY", "VALUE", "ENTRY", "AGGREGATE", "ALIAS", "CONSTRUCTOR", "CASE2", "EXPR_LIST", "FILTER_ENTITY", "IN_LIST", "INDEX_OP", "IS_NOT_NULL", "IS_NULL", "METHOD_CALL", "NOT_BETWEEN", "NOT_IN", "NOT_LIKE", "ORDER_ELEMENT", "QUERY", "RANGE", "ROW_STAR", "SELECT_FROM", "UNARY_MINUS", "UNARY_PLUS", "VECTOR_EXPR", "WEIRD_IDENT", "CONSTANT", "NUM_DOUBLE", "NUM_FLOAT", "NUM_LONG", "NUM_BIG_INTEGER", "NUM_BIG_DECIMAL", "JAVA_CONSTANT", "COMMA", "EQ", "OPEN", "CLOSE", "\"by\"", "\"ascending\"", "\"descending\"", "NE", "SQL_NE", "LT", "GT", "LE", "GE", "CONCAT", "PLUS", "MINUS", "STAR", "DIV", "MOD", "OPEN_BRACKET", "CLOSE_BRACKET", "COLON", "PARAM", "NUM_INT", "QUOTED_STRING", "IDENT", "ID_START_LETTER", "ID_LETTER", "ESCqs", "WS", "HEX_DIGIT", "EXPONENT", "FLOAT_SUFFIX" };
/* 5026:     */   
/* 5027:     */   protected void buildTokenTypeASTClassMap()
/* 5028:     */   {
/* 5029:4670 */     this.tokenTypeToASTClassMap = null;
/* 5030:     */   }
/* 5031:     */   
/* 5032:     */   private static final long[] mk_tokenSet_0()
/* 5033:     */   {
/* 5034:4674 */     long[] data = { 2L, 0L, 0L };
/* 5035:4675 */     return data;
/* 5036:     */   }
/* 5037:     */   
/* 5038:4677 */   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
/* 5039:     */   
/* 5040:     */   private static final long[] mk_tokenSet_1()
/* 5041:     */   {
/* 5042:4679 */     long[] data = { 9077567998918658L, 0L, 0L };
/* 5043:4680 */     return data;
/* 5044:     */   }
/* 5045:     */   
/* 5046:4682 */   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
/* 5047:     */   
/* 5048:     */   private static final long[] mk_tokenSet_2()
/* 5049:     */   {
/* 5050:4684 */     long[] data = { 9007199254740994L, 0L, 0L };
/* 5051:4685 */     return data;
/* 5052:     */   }
/* 5053:     */   
/* 5054:4687 */   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
/* 5055:     */   
/* 5056:     */   private static final long[] mk_tokenSet_3()
/* 5057:     */   {
/* 5058:4689 */     long[] data = { 1128098946875394L, 1099511627776L, 0L, 0L };
/* 5059:4690 */     return data;
/* 5060:     */   }
/* 5061:     */   
/* 5062:4692 */   public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
/* 5063:     */   
/* 5064:     */   private static final long[] mk_tokenSet_4()
/* 5065:     */   {
/* 5066:4694 */     long[] data = { 9007199254740994L, 137438953472L, 0L, 0L };
/* 5067:4695 */     return data;
/* 5068:     */   }
/* 5069:     */   
/* 5070:4697 */   public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
/* 5071:     */   
/* 5072:     */   private static final long[] mk_tokenSet_5()
/* 5073:     */   {
/* 5074:4699 */     long[] data = { 0L, 274877906944L, 0L, 0L };
/* 5075:4700 */     return data;
/* 5076:     */   }
/* 5077:     */   
/* 5078:4702 */   public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
/* 5079:     */   
/* 5080:     */   private static final long[] mk_tokenSet_6()
/* 5081:     */   {
/* 5082:4704 */     long[] data = { 1307261066675241410L, 4755869238785212416L, 0L, 0L };
/* 5083:4705 */     return data;
/* 5084:     */   }
/* 5085:     */   
/* 5086:4707 */   public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
/* 5087:     */   
/* 5088:     */   private static final long[] mk_tokenSet_7()
/* 5089:     */   {
/* 5090:4709 */     long[] data = { 154269485447267778L, 145238201764675585L, 0L, 0L };
/* 5091:4710 */     return data;
/* 5092:     */   }
/* 5093:     */   
/* 5094:4712 */   public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
/* 5095:     */   
/* 5096:     */   private static final long[] mk_tokenSet_8()
/* 5097:     */   {
/* 5098:4714 */     long[] data = { 1163144776902508546L, 1236950581248L, 0L, 0L };
/* 5099:4715 */     return data;
/* 5100:     */   }
/* 5101:     */   
/* 5102:4717 */   public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
/* 5103:     */   
/* 5104:     */   private static final long[] mk_tokenSet_9()
/* 5105:     */   {
/* 5106:4719 */     long[] data = { 1125899906842626L, 1099511627776L, 0L, 0L };
/* 5107:4720 */     return data;
/* 5108:     */   }
/* 5109:     */   
/* 5110:4722 */   public static final BitSet _tokenSet_9 = new BitSet(mk_tokenSet_9());
/* 5111:     */   
/* 5112:     */   private static final long[] mk_tokenSet_10()
/* 5113:     */   {
/* 5114:4724 */     long[] data = { 9044582671056898L, 0L, 0L };
/* 5115:4725 */     return data;
/* 5116:     */   }
/* 5117:     */   
/* 5118:4727 */   public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());
/* 5119:     */   
/* 5120:     */   private static final long[] mk_tokenSet_11()
/* 5121:     */   {
/* 5122:4729 */     long[] data = { 550586252655904194L, 288227489933688833L, 0L, 0L };
/* 5123:4730 */     return data;
/* 5124:     */   }
/* 5125:     */   
/* 5126:4732 */   public static final BitSet _tokenSet_11 = new BitSet(mk_tokenSet_11());
/* 5127:     */   
/* 5128:     */   private static final long[] mk_tokenSet_12()
/* 5129:     */   {
/* 5130:4734 */     long[] data = { 5181312067402913778L, 9223371965987815429L, 0L, 0L };
/* 5131:4735 */     return data;
/* 5132:     */   }
/* 5133:     */   
/* 5134:4737 */   public static final BitSet _tokenSet_12 = new BitSet(mk_tokenSet_12());
/* 5135:     */   
/* 5136:     */   private static final long[] mk_tokenSet_13()
/* 5137:     */   {
/* 5138:4739 */     long[] data = { 0L, 1099511627776L, 0L, 0L };
/* 5139:4740 */     return data;
/* 5140:     */   }
/* 5141:     */   
/* 5142:4742 */   public static final BitSet _tokenSet_13 = new BitSet(mk_tokenSet_13());
/* 5143:     */   
/* 5144:     */   private static final long[] mk_tokenSet_14()
/* 5145:     */   {
/* 5146:4744 */     long[] data = { 10135298201616386L, 1099511627776L, 0L, 0L };
/* 5147:4745 */     return data;
/* 5148:     */   }
/* 5149:     */   
/* 5150:4747 */   public static final BitSet _tokenSet_14 = new BitSet(mk_tokenSet_14());
/* 5151:     */   
/* 5152:     */   private static final long[] mk_tokenSet_15()
/* 5153:     */   {
/* 5154:4749 */     long[] data = { 1128098930098178L, 1099511627776L, 0L, 0L };
/* 5155:4750 */     return data;
/* 5156:     */   }
/* 5157:     */   
/* 5158:4752 */   public static final BitSet _tokenSet_15 = new BitSet(mk_tokenSet_15());
/* 5159:     */   
/* 5160:     */   private static final long[] mk_tokenSet_16()
/* 5161:     */   {
/* 5162:4754 */     long[] data = { 10135298205810690L, 1099511627776L, 0L, 0L };
/* 5163:4755 */     return data;
/* 5164:     */   }
/* 5165:     */   
/* 5166:4757 */   public static final BitSet _tokenSet_16 = new BitSet(mk_tokenSet_16());
/* 5167:     */   
/* 5168:     */   private static final long[] mk_tokenSet_17()
/* 5169:     */   {
/* 5170:4759 */     long[] data = { 10152903551516802L, 4611687255377969152L, 0L, 0L };
/* 5171:4760 */     return data;
/* 5172:     */   }
/* 5173:     */   
/* 5174:4762 */   public static final BitSet _tokenSet_17 = new BitSet(mk_tokenSet_17());
/* 5175:     */   
/* 5176:     */   private static final long[] mk_tokenSet_18()
/* 5177:     */   {
/* 5178:4764 */     long[] data = { 10152903549386754L, 1236950581248L, 0L, 0L };
/* 5179:4765 */     return data;
/* 5180:     */   }
/* 5181:     */   
/* 5182:4767 */   public static final BitSet _tokenSet_18 = new BitSet(mk_tokenSet_18());
/* 5183:     */   
/* 5184:     */   private static final long[] mk_tokenSet_19()
/* 5185:     */   {
/* 5186:4769 */     long[] data = { 1163074408156233730L, 1236950581248L, 0L, 0L };
/* 5187:4770 */     return data;
/* 5188:     */   }
/* 5189:     */   
/* 5190:4772 */   public static final BitSet _tokenSet_19 = new BitSet(mk_tokenSet_19());
/* 5191:     */   
/* 5192:     */   private static final long[] mk_tokenSet_20()
/* 5193:     */   {
/* 5194:4774 */     long[] data = { 154268091625242626L, 1236950581248L, 0L, 0L };
/* 5195:4775 */     return data;
/* 5196:     */   }
/* 5197:     */   
/* 5198:4777 */   public static final BitSet _tokenSet_20 = new BitSet(mk_tokenSet_20());
/* 5199:     */   
/* 5200:     */   private static final long[] mk_tokenSet_21()
/* 5201:     */   {
/* 5202:4779 */     long[] data = { 1163144776969617410L, 1236950581248L, 0L, 0L };
/* 5203:4780 */     return data;
/* 5204:     */   }
/* 5205:     */   
/* 5206:4782 */   public static final BitSet _tokenSet_21 = new BitSet(mk_tokenSet_21());
/* 5207:     */   
/* 5208:     */   private static final long[] mk_tokenSet_22()
/* 5209:     */   {
/* 5210:4784 */     long[] data = { 154268091663008130L, 144129619165970432L, 0L, 0L };
/* 5211:4785 */     return data;
/* 5212:     */   }
/* 5213:     */   
/* 5214:4787 */   public static final BitSet _tokenSet_22 = new BitSet(mk_tokenSet_22());
/* 5215:     */   
/* 5216:     */   private static final long[] mk_tokenSet_23()
/* 5217:     */   {
/* 5218:4789 */     long[] data = { 1125899906842626L, 1236950581248L, 0L, 0L };
/* 5219:4790 */     return data;
/* 5220:     */   }
/* 5221:     */   
/* 5222:4792 */   public static final BitSet _tokenSet_23 = new BitSet(mk_tokenSet_23());
/* 5223:     */   
/* 5224:     */   private static final long[] mk_tokenSet_24()
/* 5225:     */   {
/* 5226:4794 */     long[] data = { 10135298205810690L, 1236950581248L, 0L, 0L };
/* 5227:4795 */     return data;
/* 5228:     */   }
/* 5229:     */   
/* 5230:4797 */   public static final BitSet _tokenSet_24 = new BitSet(mk_tokenSet_24());
/* 5231:     */   
/* 5232:     */   private static final long[] mk_tokenSet_25()
/* 5233:     */   {
/* 5234:4799 */     long[] data = { 154269191174635906L, 144129619165970432L, 0L, 0L };
/* 5235:4800 */     return data;
/* 5236:     */   }
/* 5237:     */   
/* 5238:4802 */   public static final BitSet _tokenSet_25 = new BitSet(mk_tokenSet_25());
/* 5239:     */   
/* 5240:     */   private static final long[] mk_tokenSet_26()
/* 5241:     */   {
/* 5242:4804 */     long[] data = { 154269191174635970L, 144129619165970432L, 0L, 0L };
/* 5243:4805 */     return data;
/* 5244:     */   }
/* 5245:     */   
/* 5246:4807 */   public static final BitSet _tokenSet_26 = new BitSet(mk_tokenSet_26());
/* 5247:     */   
/* 5248:     */   private static final long[] mk_tokenSet_27()
/* 5249:     */   {
/* 5250:4809 */     long[] data = { 2147483648L, 53051436040192L, 0L, 0L };
/* 5251:4810 */     return data;
/* 5252:     */   }
/* 5253:     */   
/* 5254:4812 */   public static final BitSet _tokenSet_27 = new BitSet(mk_tokenSet_27());
/* 5255:     */   
/* 5256:     */   private static final long[] mk_tokenSet_28()
/* 5257:     */   {
/* 5258:4814 */     long[] data = { 154269193322119618L, 144182670602010624L, 0L, 0L };
/* 5259:4815 */     return data;
/* 5260:     */   }
/* 5261:     */   
/* 5262:4817 */   public static final BitSet _tokenSet_28 = new BitSet(mk_tokenSet_28());
/* 5263:     */   
/* 5264:     */   private static final long[] mk_tokenSet_29()
/* 5265:     */   {
/* 5266:4819 */     long[] data = { 154269485447267778L, 146364101671518209L, 0L, 0L };
/* 5267:4820 */     return data;
/* 5268:     */   }
/* 5269:     */   
/* 5270:4822 */   public static final BitSet _tokenSet_29 = new BitSet(mk_tokenSet_29());
/* 5271:     */   
/* 5272:     */   private static final long[] mk_tokenSet_30()
/* 5273:     */   {
/* 5274:4824 */     long[] data = { 154269485447267778L, 153119501112573953L, 0L, 0L };
/* 5275:4825 */     return data;
/* 5276:     */   }
/* 5277:     */   
/* 5278:4827 */   public static final BitSet _tokenSet_30 = new BitSet(mk_tokenSet_30());
/* 5279:     */   
/* 5280:     */   private static final long[] mk_tokenSet_31()
/* 5281:     */   {
/* 5282:4829 */     long[] data = { 550586252655871426L, 216169895895760897L, 0L, 0L };
/* 5283:4830 */     return data;
/* 5284:     */   }
/* 5285:     */   
/* 5286:4832 */   public static final BitSet _tokenSet_31 = new BitSet(mk_tokenSet_31());
/* 5287:     */   
/* 5288:     */   private static final long[] mk_tokenSet_32()
/* 5289:     */   {
/* 5290:4834 */     long[] data = { 4630686232326312496L, 8941897676471926784L, 0L, 0L };
/* 5291:4835 */     return data;
/* 5292:     */   }
/* 5293:     */   
/* 5294:4837 */   public static final BitSet _tokenSet_32 = new BitSet(mk_tokenSet_32());
/* 5295:     */   
/* 5296:     */   private static final long[] mk_tokenSet_33()
/* 5297:     */   {
/* 5298:4839 */     long[] data = { 396316767208603648L, 0L, 0L };
/* 5299:4840 */     return data;
/* 5300:     */   }
/* 5301:     */   
/* 5302:4842 */   public static final BitSet _tokenSet_33 = new BitSet(mk_tokenSet_33());
/* 5303:     */   
/* 5304:     */   private static final long[] mk_tokenSet_34()
/* 5305:     */   {
/* 5306:4844 */     long[] data = { 36028797018963968L, 0L, 0L };
/* 5307:4845 */     return data;
/* 5308:     */   }
/* 5309:     */   
/* 5310:4847 */   public static final BitSet _tokenSet_34 = new BitSet(mk_tokenSet_34());
/* 5311:     */   
/* 5312:     */   private static final long[] mk_tokenSet_35()
/* 5313:     */   {
/* 5314:4849 */     long[] data = { 550586252655904194L, 288228039689502721L, 0L, 0L };
/* 5315:4850 */     return data;
/* 5316:     */   }
/* 5317:     */   
/* 5318:4852 */   public static final BitSet _tokenSet_35 = new BitSet(mk_tokenSet_35());
/* 5319:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.antlr.HqlBaseParser
 * JD-Core Version:    0.7.0.1
 */