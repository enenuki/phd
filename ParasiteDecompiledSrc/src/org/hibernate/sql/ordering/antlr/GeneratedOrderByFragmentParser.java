/*    1:     */ package org.hibernate.sql.ordering.antlr;
/*    2:     */ 
/*    3:     */ import antlr.ASTFactory;
/*    4:     */ import antlr.ASTPair;
/*    5:     */ import antlr.LLkParser;
/*    6:     */ import antlr.NoViableAltException;
/*    7:     */ import antlr.ParserSharedInputState;
/*    8:     */ import antlr.RecognitionException;
/*    9:     */ import antlr.Token;
/*   10:     */ import antlr.TokenBuffer;
/*   11:     */ import antlr.TokenStream;
/*   12:     */ import antlr.TokenStreamException;
/*   13:     */ import antlr.collections.AST;
/*   14:     */ import antlr.collections.impl.ASTArray;
/*   15:     */ import antlr.collections.impl.BitSet;
/*   16:     */ import java.io.PrintStream;
/*   17:     */ 
/*   18:     */ public class GeneratedOrderByFragmentParser
/*   19:     */   extends LLkParser
/*   20:     */   implements OrderByTemplateTokenTypes
/*   21:     */ {
/*   22:     */   protected void trace(String msg)
/*   23:     */   {
/*   24:  62 */     System.out.println(msg);
/*   25:     */   }
/*   26:     */   
/*   27:     */   protected final String extractText(AST ast)
/*   28:     */   {
/*   29:  75 */     return ast.getText();
/*   30:     */   }
/*   31:     */   
/*   32:     */   protected AST quotedIdentifier(AST ident)
/*   33:     */   {
/*   34:  88 */     return ident;
/*   35:     */   }
/*   36:     */   
/*   37:     */   protected AST quotedString(AST ident)
/*   38:     */   {
/*   39: 100 */     return ident;
/*   40:     */   }
/*   41:     */   
/*   42:     */   protected boolean isFunctionName(AST ast)
/*   43:     */   {
/*   44: 113 */     return false;
/*   45:     */   }
/*   46:     */   
/*   47:     */   protected AST resolveFunction(AST ast)
/*   48:     */   {
/*   49: 124 */     return ast;
/*   50:     */   }
/*   51:     */   
/*   52:     */   protected AST resolveIdent(AST ident)
/*   53:     */   {
/*   54: 135 */     return ident;
/*   55:     */   }
/*   56:     */   
/*   57:     */   protected AST postProcessSortSpecification(AST sortSpec)
/*   58:     */   {
/*   59: 146 */     return sortSpec;
/*   60:     */   }
/*   61:     */   
/*   62:     */   protected GeneratedOrderByFragmentParser(TokenBuffer tokenBuf, int k)
/*   63:     */   {
/*   64: 151 */     super(tokenBuf, k);
/*   65: 152 */     this.tokenNames = _tokenNames;
/*   66: 153 */     buildTokenTypeASTClassMap();
/*   67: 154 */     this.astFactory = new ASTFactory(getTokenTypeToASTClassMap());
/*   68:     */   }
/*   69:     */   
/*   70:     */   public GeneratedOrderByFragmentParser(TokenBuffer tokenBuf)
/*   71:     */   {
/*   72: 158 */     this(tokenBuf, 3);
/*   73:     */   }
/*   74:     */   
/*   75:     */   protected GeneratedOrderByFragmentParser(TokenStream lexer, int k)
/*   76:     */   {
/*   77: 162 */     super(lexer, k);
/*   78: 163 */     this.tokenNames = _tokenNames;
/*   79: 164 */     buildTokenTypeASTClassMap();
/*   80: 165 */     this.astFactory = new ASTFactory(getTokenTypeToASTClassMap());
/*   81:     */   }
/*   82:     */   
/*   83:     */   public GeneratedOrderByFragmentParser(TokenStream lexer)
/*   84:     */   {
/*   85: 169 */     this(lexer, 3);
/*   86:     */   }
/*   87:     */   
/*   88:     */   public GeneratedOrderByFragmentParser(ParserSharedInputState state)
/*   89:     */   {
/*   90: 173 */     super(state, 3);
/*   91: 174 */     this.tokenNames = _tokenNames;
/*   92: 175 */     buildTokenTypeASTClassMap();
/*   93: 176 */     this.astFactory = new ASTFactory(getTokenTypeToASTClassMap());
/*   94:     */   }
/*   95:     */   
/*   96:     */   public final void orderByFragment()
/*   97:     */     throws RecognitionException, TokenStreamException
/*   98:     */   {
/*   99: 184 */     this.returnAST = null;
/*  100: 185 */     ASTPair currentAST = new ASTPair();
/*  101: 186 */     AST orderByFragment_AST = null;
/*  102: 187 */     trace("orderByFragment");
/*  103:     */     try
/*  104:     */     {
/*  105: 190 */       sortSpecification();
/*  106: 191 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/*  107: 195 */       while (LA(1) == 15)
/*  108:     */       {
/*  109: 196 */         match(15);
/*  110: 197 */         sortSpecification();
/*  111: 198 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*  112:     */       }
/*  113: 206 */       if (this.inputState.guessing == 0)
/*  114:     */       {
/*  115: 207 */         orderByFragment_AST = currentAST.root;
/*  116:     */         
/*  117: 209 */         orderByFragment_AST = this.astFactory.make(new ASTArray(2).add(this.astFactory.create(4, "order-by")).add(orderByFragment_AST));
/*  118:     */         
/*  119: 211 */         currentAST.root = orderByFragment_AST;
/*  120: 212 */         currentAST.child = ((orderByFragment_AST != null) && (orderByFragment_AST.getFirstChild() != null) ? orderByFragment_AST.getFirstChild() : orderByFragment_AST);
/*  121:     */         
/*  122: 214 */         currentAST.advanceChildToEnd();
/*  123:     */       }
/*  124: 216 */       orderByFragment_AST = currentAST.root;
/*  125:     */     }
/*  126:     */     catch (RecognitionException ex)
/*  127:     */     {
/*  128: 219 */       if (this.inputState.guessing == 0)
/*  129:     */       {
/*  130: 220 */         reportError(ex);
/*  131: 221 */         recover(ex, _tokenSet_0);
/*  132:     */       }
/*  133:     */       else
/*  134:     */       {
/*  135: 223 */         throw ex;
/*  136:     */       }
/*  137:     */     }
/*  138: 226 */     this.returnAST = orderByFragment_AST;
/*  139:     */   }
/*  140:     */   
/*  141:     */   public final void sortSpecification()
/*  142:     */     throws RecognitionException, TokenStreamException
/*  143:     */   {
/*  144: 235 */     this.returnAST = null;
/*  145: 236 */     ASTPair currentAST = new ASTPair();
/*  146: 237 */     AST sortSpecification_AST = null;
/*  147: 238 */     trace("sortSpecification");
/*  148:     */     try
/*  149:     */     {
/*  150: 241 */       sortKey();
/*  151: 242 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/*  152: 244 */       switch (LA(1))
/*  153:     */       {
/*  154:     */       case 12: 
/*  155: 247 */         collationSpecification();
/*  156: 248 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*  157: 249 */         break;
/*  158:     */       case 1: 
/*  159:     */       case 13: 
/*  160:     */       case 14: 
/*  161:     */       case 15: 
/*  162:     */       case 25: 
/*  163:     */       case 26: 
/*  164:     */         break;
/*  165:     */       default: 
/*  166: 262 */         throw new NoViableAltException(LT(1), getFilename());
/*  167:     */       }
/*  168: 267 */       switch (LA(1))
/*  169:     */       {
/*  170:     */       case 13: 
/*  171:     */       case 14: 
/*  172:     */       case 25: 
/*  173:     */       case 26: 
/*  174: 273 */         orderingSpecification();
/*  175: 274 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*  176: 275 */         break;
/*  177:     */       case 1: 
/*  178:     */       case 15: 
/*  179:     */         break;
/*  180:     */       default: 
/*  181: 284 */         throw new NoViableAltException(LT(1), getFilename());
/*  182:     */       }
/*  183: 288 */       if (this.inputState.guessing == 0)
/*  184:     */       {
/*  185: 289 */         sortSpecification_AST = currentAST.root;
/*  186:     */         
/*  187: 291 */         sortSpecification_AST = this.astFactory.make(new ASTArray(2).add(this.astFactory.create(5, "{sort specification}")).add(sortSpecification_AST));
/*  188: 292 */         sortSpecification_AST = postProcessSortSpecification(sortSpecification_AST);
/*  189:     */         
/*  190: 294 */         currentAST.root = sortSpecification_AST;
/*  191: 295 */         currentAST.child = ((sortSpecification_AST != null) && (sortSpecification_AST.getFirstChild() != null) ? sortSpecification_AST.getFirstChild() : sortSpecification_AST);
/*  192:     */         
/*  193: 297 */         currentAST.advanceChildToEnd();
/*  194:     */       }
/*  195: 299 */       sortSpecification_AST = currentAST.root;
/*  196:     */     }
/*  197:     */     catch (RecognitionException ex)
/*  198:     */     {
/*  199: 302 */       if (this.inputState.guessing == 0)
/*  200:     */       {
/*  201: 303 */         reportError(ex);
/*  202: 304 */         recover(ex, _tokenSet_1);
/*  203:     */       }
/*  204:     */       else
/*  205:     */       {
/*  206: 306 */         throw ex;
/*  207:     */       }
/*  208:     */     }
/*  209: 309 */     this.returnAST = sortSpecification_AST;
/*  210:     */   }
/*  211:     */   
/*  212:     */   public final void sortKey()
/*  213:     */     throws RecognitionException, TokenStreamException
/*  214:     */   {
/*  215: 318 */     this.returnAST = null;
/*  216: 319 */     ASTPair currentAST = new ASTPair();
/*  217: 320 */     AST sortKey_AST = null;
/*  218: 321 */     AST e_AST = null;
/*  219: 322 */     trace("sortKey");
/*  220:     */     try
/*  221:     */     {
/*  222: 325 */       expression();
/*  223: 326 */       e_AST = this.returnAST;
/*  224: 327 */       if (this.inputState.guessing == 0)
/*  225:     */       {
/*  226: 328 */         sortKey_AST = currentAST.root;
/*  227:     */         
/*  228: 330 */         sortKey_AST = this.astFactory.make(new ASTArray(2).add(this.astFactory.create(7, "sort key")).add(e_AST));
/*  229:     */         
/*  230: 332 */         currentAST.root = sortKey_AST;
/*  231: 333 */         currentAST.child = ((sortKey_AST != null) && (sortKey_AST.getFirstChild() != null) ? sortKey_AST.getFirstChild() : sortKey_AST);
/*  232:     */         
/*  233: 335 */         currentAST.advanceChildToEnd();
/*  234:     */       }
/*  235:     */     }
/*  236:     */     catch (RecognitionException ex)
/*  237:     */     {
/*  238: 339 */       if (this.inputState.guessing == 0)
/*  239:     */       {
/*  240: 340 */         reportError(ex);
/*  241: 341 */         recover(ex, _tokenSet_2);
/*  242:     */       }
/*  243:     */       else
/*  244:     */       {
/*  245: 343 */         throw ex;
/*  246:     */       }
/*  247:     */     }
/*  248: 346 */     this.returnAST = sortKey_AST;
/*  249:     */   }
/*  250:     */   
/*  251:     */   public final void collationSpecification()
/*  252:     */     throws RecognitionException, TokenStreamException
/*  253:     */   {
/*  254: 355 */     this.returnAST = null;
/*  255: 356 */     ASTPair currentAST = new ASTPair();
/*  256: 357 */     AST collationSpecification_AST = null;
/*  257: 358 */     Token c = null;
/*  258: 359 */     AST c_AST = null;
/*  259: 360 */     AST cn_AST = null;
/*  260: 361 */     trace("collationSpecification");
/*  261:     */     try
/*  262:     */     {
/*  263: 364 */       c = LT(1);
/*  264: 365 */       c_AST = this.astFactory.create(c);
/*  265: 366 */       match(12);
/*  266: 367 */       collationName();
/*  267: 368 */       cn_AST = this.returnAST;
/*  268: 369 */       if (this.inputState.guessing == 0)
/*  269:     */       {
/*  270: 370 */         collationSpecification_AST = currentAST.root;
/*  271:     */         
/*  272: 372 */         collationSpecification_AST = this.astFactory.make(new ASTArray(1).add(this.astFactory.create(12, extractText(cn_AST))));
/*  273:     */         
/*  274: 374 */         currentAST.root = collationSpecification_AST;
/*  275: 375 */         currentAST.child = ((collationSpecification_AST != null) && (collationSpecification_AST.getFirstChild() != null) ? collationSpecification_AST.getFirstChild() : collationSpecification_AST);
/*  276:     */         
/*  277: 377 */         currentAST.advanceChildToEnd();
/*  278:     */       }
/*  279:     */     }
/*  280:     */     catch (RecognitionException ex)
/*  281:     */     {
/*  282: 381 */       if (this.inputState.guessing == 0)
/*  283:     */       {
/*  284: 382 */         reportError(ex);
/*  285: 383 */         recover(ex, _tokenSet_3);
/*  286:     */       }
/*  287:     */       else
/*  288:     */       {
/*  289: 385 */         throw ex;
/*  290:     */       }
/*  291:     */     }
/*  292: 388 */     this.returnAST = collationSpecification_AST;
/*  293:     */   }
/*  294:     */   
/*  295:     */   public final void orderingSpecification()
/*  296:     */     throws RecognitionException, TokenStreamException
/*  297:     */   {
/*  298: 397 */     this.returnAST = null;
/*  299: 398 */     ASTPair currentAST = new ASTPair();
/*  300: 399 */     AST orderingSpecification_AST = null;
/*  301: 400 */     trace("orderingSpecification");
/*  302:     */     try
/*  303:     */     {
/*  304: 403 */       switch (LA(1))
/*  305:     */       {
/*  306:     */       case 13: 
/*  307:     */       case 25: 
/*  308: 408 */         switch (LA(1))
/*  309:     */         {
/*  310:     */         case 13: 
/*  311: 411 */           match(13);
/*  312: 412 */           break;
/*  313:     */         case 25: 
/*  314: 416 */           match(25);
/*  315: 417 */           break;
/*  316:     */         default: 
/*  317: 421 */           throw new NoViableAltException(LT(1), getFilename());
/*  318:     */         }
/*  319: 425 */         if (this.inputState.guessing == 0)
/*  320:     */         {
/*  321: 426 */           orderingSpecification_AST = currentAST.root;
/*  322:     */           
/*  323: 428 */           orderingSpecification_AST = this.astFactory.make(new ASTArray(1).add(this.astFactory.create(6, "asc")));
/*  324:     */           
/*  325: 430 */           currentAST.root = orderingSpecification_AST;
/*  326: 431 */           currentAST.child = ((orderingSpecification_AST != null) && (orderingSpecification_AST.getFirstChild() != null) ? orderingSpecification_AST.getFirstChild() : orderingSpecification_AST);
/*  327:     */           
/*  328: 433 */           currentAST.advanceChildToEnd();
/*  329:     */         }
/*  330:     */         break;
/*  331:     */       case 14: 
/*  332:     */       case 26: 
/*  333: 441 */         switch (LA(1))
/*  334:     */         {
/*  335:     */         case 14: 
/*  336: 444 */           match(14);
/*  337: 445 */           break;
/*  338:     */         case 26: 
/*  339: 449 */           match(26);
/*  340: 450 */           break;
/*  341:     */         default: 
/*  342: 454 */           throw new NoViableAltException(LT(1), getFilename());
/*  343:     */         }
/*  344: 458 */         if (this.inputState.guessing == 0)
/*  345:     */         {
/*  346: 459 */           orderingSpecification_AST = currentAST.root;
/*  347:     */           
/*  348: 461 */           orderingSpecification_AST = this.astFactory.make(new ASTArray(1).add(this.astFactory.create(6, "desc")));
/*  349:     */           
/*  350: 463 */           currentAST.root = orderingSpecification_AST;
/*  351: 464 */           currentAST.child = ((orderingSpecification_AST != null) && (orderingSpecification_AST.getFirstChild() != null) ? orderingSpecification_AST.getFirstChild() : orderingSpecification_AST);
/*  352:     */           
/*  353: 466 */           currentAST.advanceChildToEnd();
/*  354:     */         }
/*  355:     */         break;
/*  356:     */       default: 
/*  357: 472 */         throw new NoViableAltException(LT(1), getFilename());
/*  358:     */       }
/*  359:     */     }
/*  360:     */     catch (RecognitionException ex)
/*  361:     */     {
/*  362: 477 */       if (this.inputState.guessing == 0)
/*  363:     */       {
/*  364: 478 */         reportError(ex);
/*  365: 479 */         recover(ex, _tokenSet_1);
/*  366:     */       }
/*  367:     */       else
/*  368:     */       {
/*  369: 481 */         throw ex;
/*  370:     */       }
/*  371:     */     }
/*  372: 484 */     this.returnAST = orderingSpecification_AST;
/*  373:     */   }
/*  374:     */   
/*  375:     */   public final void expression()
/*  376:     */     throws RecognitionException, TokenStreamException
/*  377:     */   {
/*  378: 492 */     this.returnAST = null;
/*  379: 493 */     ASTPair currentAST = new ASTPair();
/*  380: 494 */     AST expression_AST = null;
/*  381: 495 */     Token qi = null;
/*  382: 496 */     AST qi_AST = null;
/*  383: 497 */     AST f_AST = null;
/*  384: 498 */     AST p_AST = null;
/*  385: 499 */     Token i = null;
/*  386: 500 */     AST i_AST = null;
/*  387: 501 */     trace("expression");
/*  388:     */     try
/*  389:     */     {
/*  390: 504 */       if (LA(1) == 16)
/*  391:     */       {
/*  392: 505 */         AST tmp6_AST = null;
/*  393: 506 */         tmp6_AST = this.astFactory.create(LT(1));
/*  394: 507 */         match(16);
/*  395: 508 */         qi = LT(1);
/*  396: 509 */         qi_AST = this.astFactory.create(qi);
/*  397: 510 */         match(17);
/*  398: 511 */         AST tmp7_AST = null;
/*  399: 512 */         tmp7_AST = this.astFactory.create(LT(1));
/*  400: 513 */         match(16);
/*  401: 514 */         if (this.inputState.guessing == 0)
/*  402:     */         {
/*  403: 515 */           expression_AST = currentAST.root;
/*  404:     */           
/*  405: 517 */           expression_AST = quotedIdentifier(qi_AST);
/*  406:     */           
/*  407: 519 */           currentAST.root = expression_AST;
/*  408: 520 */           currentAST.child = ((expression_AST != null) && (expression_AST.getFirstChild() != null) ? expression_AST.getFirstChild() : expression_AST);
/*  409:     */           
/*  410: 522 */           currentAST.advanceChildToEnd();
/*  411:     */         }
/*  412:     */       }
/*  413:     */       else
/*  414:     */       {
/*  415: 526 */         boolean synPredMatched12 = false;
/*  416: 527 */         if ((LA(1) == 17) && ((LA(2) == 9) || (LA(2) == 18)) && (_tokenSet_4.member(LA(3))))
/*  417:     */         {
/*  418: 528 */           int _m12 = mark();
/*  419: 529 */           synPredMatched12 = true;
/*  420: 530 */           this.inputState.guessing += 1;
/*  421:     */           try
/*  422:     */           {
/*  423: 533 */             match(17);
/*  424: 537 */             while (LA(1) == 9)
/*  425:     */             {
/*  426: 538 */               match(9);
/*  427: 539 */               match(17);
/*  428:     */             }
/*  429: 547 */             match(18);
/*  430:     */           }
/*  431:     */           catch (RecognitionException pe)
/*  432:     */           {
/*  433: 551 */             synPredMatched12 = false;
/*  434:     */           }
/*  435: 553 */           rewind(_m12);
/*  436: 554 */           this.inputState.guessing -= 1;
/*  437:     */         }
/*  438: 556 */         if (synPredMatched12)
/*  439:     */         {
/*  440: 557 */           functionCall();
/*  441: 558 */           f_AST = this.returnAST;
/*  442: 559 */           if (this.inputState.guessing == 0)
/*  443:     */           {
/*  444: 560 */             expression_AST = currentAST.root;
/*  445:     */             
/*  446: 562 */             expression_AST = f_AST;
/*  447:     */             
/*  448: 564 */             currentAST.root = expression_AST;
/*  449: 565 */             currentAST.child = ((expression_AST != null) && (expression_AST.getFirstChild() != null) ? expression_AST.getFirstChild() : expression_AST);
/*  450:     */             
/*  451: 567 */             currentAST.advanceChildToEnd();
/*  452:     */           }
/*  453:     */         }
/*  454: 570 */         else if ((LA(1) == 17) && (LA(2) == 9) && (LA(3) == 17))
/*  455:     */         {
/*  456: 571 */           simplePropertyPath();
/*  457: 572 */           p_AST = this.returnAST;
/*  458: 573 */           if (this.inputState.guessing == 0)
/*  459:     */           {
/*  460: 574 */             expression_AST = currentAST.root;
/*  461:     */             
/*  462: 576 */             expression_AST = resolveIdent(p_AST);
/*  463:     */             
/*  464: 578 */             currentAST.root = expression_AST;
/*  465: 579 */             currentAST.child = ((expression_AST != null) && (expression_AST.getFirstChild() != null) ? expression_AST.getFirstChild() : expression_AST);
/*  466:     */             
/*  467: 581 */             currentAST.advanceChildToEnd();
/*  468:     */           }
/*  469:     */         }
/*  470: 584 */         else if ((LA(1) == 17) && (_tokenSet_5.member(LA(2))))
/*  471:     */         {
/*  472: 585 */           i = LT(1);
/*  473: 586 */           i_AST = this.astFactory.create(i);
/*  474: 587 */           match(17);
/*  475: 588 */           if (this.inputState.guessing == 0)
/*  476:     */           {
/*  477: 589 */             expression_AST = currentAST.root;
/*  478: 591 */             if (isFunctionName(i_AST)) {
/*  479: 592 */               expression_AST = resolveFunction(i_AST);
/*  480:     */             } else {
/*  481: 595 */               expression_AST = resolveIdent(i_AST);
/*  482:     */             }
/*  483: 598 */             currentAST.root = expression_AST;
/*  484: 599 */             currentAST.child = ((expression_AST != null) && (expression_AST.getFirstChild() != null) ? expression_AST.getFirstChild() : expression_AST);
/*  485:     */             
/*  486: 601 */             currentAST.advanceChildToEnd();
/*  487:     */           }
/*  488:     */         }
/*  489:     */         else
/*  490:     */         {
/*  491: 605 */           throw new NoViableAltException(LT(1), getFilename());
/*  492:     */         }
/*  493:     */       }
/*  494:     */     }
/*  495:     */     catch (RecognitionException ex)
/*  496:     */     {
/*  497: 610 */       if (this.inputState.guessing == 0)
/*  498:     */       {
/*  499: 611 */         reportError(ex);
/*  500: 612 */         recover(ex, _tokenSet_5);
/*  501:     */       }
/*  502:     */       else
/*  503:     */       {
/*  504: 614 */         throw ex;
/*  505:     */       }
/*  506:     */     }
/*  507: 617 */     this.returnAST = expression_AST;
/*  508:     */   }
/*  509:     */   
/*  510:     */   public final void functionCall()
/*  511:     */     throws RecognitionException, TokenStreamException
/*  512:     */   {
/*  513: 625 */     this.returnAST = null;
/*  514: 626 */     ASTPair currentAST = new ASTPair();
/*  515: 627 */     AST functionCall_AST = null;
/*  516: 628 */     AST fn_AST = null;
/*  517: 629 */     AST pl_AST = null;
/*  518: 630 */     trace("functionCall");
/*  519:     */     try
/*  520:     */     {
/*  521: 633 */       functionName();
/*  522: 634 */       fn_AST = this.returnAST;
/*  523: 635 */       AST tmp8_AST = null;
/*  524: 636 */       tmp8_AST = this.astFactory.create(LT(1));
/*  525: 637 */       match(18);
/*  526: 638 */       functionParameterList();
/*  527: 639 */       pl_AST = this.returnAST;
/*  528: 640 */       AST tmp9_AST = null;
/*  529: 641 */       tmp9_AST = this.astFactory.create(LT(1));
/*  530: 642 */       match(19);
/*  531: 643 */       if (this.inputState.guessing == 0)
/*  532:     */       {
/*  533: 644 */         functionCall_AST = currentAST.root;
/*  534:     */         
/*  535: 646 */         functionCall_AST = this.astFactory.make(new ASTArray(2).add(this.astFactory.create(17, extractText(fn_AST))).add(pl_AST));
/*  536: 647 */         functionCall_AST = resolveFunction(functionCall_AST);
/*  537:     */         
/*  538: 649 */         currentAST.root = functionCall_AST;
/*  539: 650 */         currentAST.child = ((functionCall_AST != null) && (functionCall_AST.getFirstChild() != null) ? functionCall_AST.getFirstChild() : functionCall_AST);
/*  540:     */         
/*  541: 652 */         currentAST.advanceChildToEnd();
/*  542:     */       }
/*  543:     */     }
/*  544:     */     catch (RecognitionException ex)
/*  545:     */     {
/*  546: 656 */       if (this.inputState.guessing == 0)
/*  547:     */       {
/*  548: 657 */         reportError(ex);
/*  549: 658 */         recover(ex, _tokenSet_5);
/*  550:     */       }
/*  551:     */       else
/*  552:     */       {
/*  553: 660 */         throw ex;
/*  554:     */       }
/*  555:     */     }
/*  556: 663 */     this.returnAST = functionCall_AST;
/*  557:     */   }
/*  558:     */   
/*  559:     */   public final void simplePropertyPath()
/*  560:     */     throws RecognitionException, TokenStreamException
/*  561:     */   {
/*  562: 671 */     this.returnAST = null;
/*  563: 672 */     ASTPair currentAST = new ASTPair();
/*  564: 673 */     AST simplePropertyPath_AST = null;
/*  565: 674 */     Token i = null;
/*  566: 675 */     AST i_AST = null;
/*  567: 676 */     Token i2 = null;
/*  568: 677 */     AST i2_AST = null;
/*  569:     */     
/*  570: 679 */     trace("simplePropertyPath");
/*  571: 680 */     StringBuffer buffer = new StringBuffer();
/*  572:     */     try
/*  573:     */     {
/*  574: 684 */       i = LT(1);
/*  575: 685 */       i_AST = this.astFactory.create(i);
/*  576: 686 */       this.astFactory.addASTChild(currentAST, i_AST);
/*  577: 687 */       match(17);
/*  578: 688 */       if (this.inputState.guessing == 0) {
/*  579: 689 */         buffer.append(i.getText());
/*  580:     */       }
/*  581: 692 */       int _cnt31 = 0;
/*  582:     */       for (;;)
/*  583:     */       {
/*  584: 695 */         if (LA(1) == 9)
/*  585:     */         {
/*  586: 696 */           AST tmp10_AST = null;
/*  587: 697 */           tmp10_AST = this.astFactory.create(LT(1));
/*  588: 698 */           this.astFactory.addASTChild(currentAST, tmp10_AST);
/*  589: 699 */           match(9);
/*  590: 700 */           i2 = LT(1);
/*  591: 701 */           i2_AST = this.astFactory.create(i2);
/*  592: 702 */           this.astFactory.addASTChild(currentAST, i2_AST);
/*  593: 703 */           match(17);
/*  594: 704 */           if (this.inputState.guessing == 0) {
/*  595: 705 */             buffer.append('.').append(i2.getText());
/*  596:     */           }
/*  597:     */         }
/*  598:     */         else
/*  599:     */         {
/*  600: 709 */           if (_cnt31 >= 1) {
/*  601:     */             break;
/*  602:     */           }
/*  603: 709 */           throw new NoViableAltException(LT(1), getFilename());
/*  604:     */         }
/*  605: 712 */         _cnt31++;
/*  606:     */       }
/*  607: 715 */       if (this.inputState.guessing == 0)
/*  608:     */       {
/*  609: 716 */         simplePropertyPath_AST = currentAST.root;
/*  610:     */         
/*  611: 718 */         simplePropertyPath_AST = this.astFactory.make(new ASTArray(1).add(this.astFactory.create(17, buffer.toString())));
/*  612:     */         
/*  613: 720 */         currentAST.root = simplePropertyPath_AST;
/*  614: 721 */         currentAST.child = ((simplePropertyPath_AST != null) && (simplePropertyPath_AST.getFirstChild() != null) ? simplePropertyPath_AST.getFirstChild() : simplePropertyPath_AST);
/*  615:     */         
/*  616: 723 */         currentAST.advanceChildToEnd();
/*  617:     */       }
/*  618: 725 */       simplePropertyPath_AST = currentAST.root;
/*  619:     */     }
/*  620:     */     catch (RecognitionException ex)
/*  621:     */     {
/*  622: 728 */       if (this.inputState.guessing == 0)
/*  623:     */       {
/*  624: 729 */         reportError(ex);
/*  625: 730 */         recover(ex, _tokenSet_5);
/*  626:     */       }
/*  627:     */       else
/*  628:     */       {
/*  629: 732 */         throw ex;
/*  630:     */       }
/*  631:     */     }
/*  632: 735 */     this.returnAST = simplePropertyPath_AST;
/*  633:     */   }
/*  634:     */   
/*  635:     */   public final void functionCallCheck()
/*  636:     */     throws RecognitionException, TokenStreamException
/*  637:     */   {
/*  638: 743 */     this.returnAST = null;
/*  639: 744 */     ASTPair currentAST = new ASTPair();
/*  640: 745 */     AST functionCallCheck_AST = null;
/*  641: 746 */     trace("functionCallCheck");
/*  642:     */     try
/*  643:     */     {
/*  644: 749 */       AST tmp11_AST = null;
/*  645: 750 */       tmp11_AST = this.astFactory.create(LT(1));
/*  646: 751 */       match(17);
/*  647: 755 */       while (LA(1) == 9)
/*  648:     */       {
/*  649: 756 */         AST tmp12_AST = null;
/*  650: 757 */         tmp12_AST = this.astFactory.create(LT(1));
/*  651: 758 */         match(9);
/*  652: 759 */         AST tmp13_AST = null;
/*  653: 760 */         tmp13_AST = this.astFactory.create(LT(1));
/*  654: 761 */         match(17);
/*  655:     */       }
/*  656: 769 */       AST tmp14_AST = null;
/*  657: 770 */       tmp14_AST = this.astFactory.create(LT(1));
/*  658: 771 */       match(18);
/*  659:     */     }
/*  660:     */     catch (RecognitionException ex)
/*  661:     */     {
/*  662: 776 */       if (this.inputState.guessing == 0)
/*  663:     */       {
/*  664: 777 */         reportError(ex);
/*  665: 778 */         recover(ex, _tokenSet_0);
/*  666:     */       }
/*  667:     */       else
/*  668:     */       {
/*  669: 780 */         throw ex;
/*  670:     */       }
/*  671:     */     }
/*  672: 783 */     this.returnAST = functionCallCheck_AST;
/*  673:     */   }
/*  674:     */   
/*  675:     */   public final void functionName()
/*  676:     */     throws RecognitionException, TokenStreamException
/*  677:     */   {
/*  678: 791 */     this.returnAST = null;
/*  679: 792 */     ASTPair currentAST = new ASTPair();
/*  680: 793 */     AST functionName_AST = null;
/*  681: 794 */     Token i = null;
/*  682: 795 */     AST i_AST = null;
/*  683: 796 */     Token i2 = null;
/*  684: 797 */     AST i2_AST = null;
/*  685:     */     
/*  686: 799 */     trace("functionName");
/*  687: 800 */     StringBuffer buffer = new StringBuffer();
/*  688:     */     try
/*  689:     */     {
/*  690: 804 */       i = LT(1);
/*  691: 805 */       i_AST = this.astFactory.create(i);
/*  692: 806 */       this.astFactory.addASTChild(currentAST, i_AST);
/*  693: 807 */       match(17);
/*  694: 808 */       if (this.inputState.guessing == 0) {
/*  695: 809 */         buffer.append(i.getText());
/*  696:     */       }
/*  697: 814 */       while (LA(1) == 9)
/*  698:     */       {
/*  699: 815 */         AST tmp15_AST = null;
/*  700: 816 */         tmp15_AST = this.astFactory.create(LT(1));
/*  701: 817 */         this.astFactory.addASTChild(currentAST, tmp15_AST);
/*  702: 818 */         match(9);
/*  703: 819 */         i2 = LT(1);
/*  704: 820 */         i2_AST = this.astFactory.create(i2);
/*  705: 821 */         this.astFactory.addASTChild(currentAST, i2_AST);
/*  706: 822 */         match(17);
/*  707: 823 */         if (this.inputState.guessing == 0) {
/*  708: 824 */           buffer.append('.').append(i2.getText());
/*  709:     */         }
/*  710:     */       }
/*  711: 833 */       if (this.inputState.guessing == 0)
/*  712:     */       {
/*  713: 834 */         functionName_AST = currentAST.root;
/*  714:     */         
/*  715: 836 */         functionName_AST = this.astFactory.make(new ASTArray(1).add(this.astFactory.create(17, buffer.toString())));
/*  716:     */         
/*  717: 838 */         currentAST.root = functionName_AST;
/*  718: 839 */         currentAST.child = ((functionName_AST != null) && (functionName_AST.getFirstChild() != null) ? functionName_AST.getFirstChild() : functionName_AST);
/*  719:     */         
/*  720: 841 */         currentAST.advanceChildToEnd();
/*  721:     */       }
/*  722: 843 */       functionName_AST = currentAST.root;
/*  723:     */     }
/*  724:     */     catch (RecognitionException ex)
/*  725:     */     {
/*  726: 846 */       if (this.inputState.guessing == 0)
/*  727:     */       {
/*  728: 847 */         reportError(ex);
/*  729: 848 */         recover(ex, _tokenSet_6);
/*  730:     */       }
/*  731:     */       else
/*  732:     */       {
/*  733: 850 */         throw ex;
/*  734:     */       }
/*  735:     */     }
/*  736: 853 */     this.returnAST = functionName_AST;
/*  737:     */   }
/*  738:     */   
/*  739:     */   public final void functionParameterList()
/*  740:     */     throws RecognitionException, TokenStreamException
/*  741:     */   {
/*  742: 861 */     this.returnAST = null;
/*  743: 862 */     ASTPair currentAST = new ASTPair();
/*  744: 863 */     AST functionParameterList_AST = null;
/*  745: 864 */     trace("functionParameterList");
/*  746:     */     try
/*  747:     */     {
/*  748: 867 */       functionParameter();
/*  749: 868 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/*  750: 872 */       while (LA(1) == 15)
/*  751:     */       {
/*  752: 873 */         match(15);
/*  753: 874 */         functionParameter();
/*  754: 875 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*  755:     */       }
/*  756: 883 */       if (this.inputState.guessing == 0)
/*  757:     */       {
/*  758: 884 */         functionParameterList_AST = currentAST.root;
/*  759:     */         
/*  760: 886 */         functionParameterList_AST = this.astFactory.make(new ASTArray(2).add(this.astFactory.create(8, "{param list}")).add(functionParameterList_AST));
/*  761:     */         
/*  762: 888 */         currentAST.root = functionParameterList_AST;
/*  763: 889 */         currentAST.child = ((functionParameterList_AST != null) && (functionParameterList_AST.getFirstChild() != null) ? functionParameterList_AST.getFirstChild() : functionParameterList_AST);
/*  764:     */         
/*  765: 891 */         currentAST.advanceChildToEnd();
/*  766:     */       }
/*  767: 893 */       functionParameterList_AST = currentAST.root;
/*  768:     */     }
/*  769:     */     catch (RecognitionException ex)
/*  770:     */     {
/*  771: 896 */       if (this.inputState.guessing == 0)
/*  772:     */       {
/*  773: 897 */         reportError(ex);
/*  774: 898 */         recover(ex, _tokenSet_7);
/*  775:     */       }
/*  776:     */       else
/*  777:     */       {
/*  778: 900 */         throw ex;
/*  779:     */       }
/*  780:     */     }
/*  781: 903 */     this.returnAST = functionParameterList_AST;
/*  782:     */   }
/*  783:     */   
/*  784:     */   public final void functionParameter()
/*  785:     */     throws RecognitionException, TokenStreamException
/*  786:     */   {
/*  787: 911 */     this.returnAST = null;
/*  788: 912 */     ASTPair currentAST = new ASTPair();
/*  789: 913 */     AST functionParameter_AST = null;
/*  790: 914 */     trace("functionParameter");
/*  791:     */     try
/*  792:     */     {
/*  793: 917 */       switch (LA(1))
/*  794:     */       {
/*  795:     */       case 16: 
/*  796:     */       case 17: 
/*  797: 921 */         expression();
/*  798: 922 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*  799: 923 */         functionParameter_AST = currentAST.root;
/*  800: 924 */         break;
/*  801:     */       case 20: 
/*  802: 928 */         AST tmp17_AST = null;
/*  803: 929 */         tmp17_AST = this.astFactory.create(LT(1));
/*  804: 930 */         this.astFactory.addASTChild(currentAST, tmp17_AST);
/*  805: 931 */         match(20);
/*  806: 932 */         functionParameter_AST = currentAST.root;
/*  807: 933 */         break;
/*  808:     */       case 21: 
/*  809: 937 */         AST tmp18_AST = null;
/*  810: 938 */         tmp18_AST = this.astFactory.create(LT(1));
/*  811: 939 */         this.astFactory.addASTChild(currentAST, tmp18_AST);
/*  812: 940 */         match(21);
/*  813: 941 */         functionParameter_AST = currentAST.root;
/*  814: 942 */         break;
/*  815:     */       case 22: 
/*  816: 946 */         AST tmp19_AST = null;
/*  817: 947 */         tmp19_AST = this.astFactory.create(LT(1));
/*  818: 948 */         this.astFactory.addASTChild(currentAST, tmp19_AST);
/*  819: 949 */         match(22);
/*  820: 950 */         functionParameter_AST = currentAST.root;
/*  821: 951 */         break;
/*  822:     */       case 23: 
/*  823: 955 */         AST tmp20_AST = null;
/*  824: 956 */         tmp20_AST = this.astFactory.create(LT(1));
/*  825: 957 */         this.astFactory.addASTChild(currentAST, tmp20_AST);
/*  826: 958 */         match(23);
/*  827: 959 */         functionParameter_AST = currentAST.root;
/*  828: 960 */         break;
/*  829:     */       case 24: 
/*  830: 964 */         AST tmp21_AST = null;
/*  831: 965 */         tmp21_AST = this.astFactory.create(LT(1));
/*  832: 966 */         this.astFactory.addASTChild(currentAST, tmp21_AST);
/*  833: 967 */         match(24);
/*  834: 968 */         if (this.inputState.guessing == 0)
/*  835:     */         {
/*  836: 969 */           functionParameter_AST = currentAST.root;
/*  837:     */           
/*  838: 971 */           functionParameter_AST = quotedString(functionParameter_AST);
/*  839:     */           
/*  840: 973 */           currentAST.root = functionParameter_AST;
/*  841: 974 */           currentAST.child = ((functionParameter_AST != null) && (functionParameter_AST.getFirstChild() != null) ? functionParameter_AST.getFirstChild() : functionParameter_AST);
/*  842:     */           
/*  843: 976 */           currentAST.advanceChildToEnd();
/*  844:     */         }
/*  845: 978 */         functionParameter_AST = currentAST.root;
/*  846: 979 */         break;
/*  847:     */       case 18: 
/*  848:     */       case 19: 
/*  849:     */       default: 
/*  850: 983 */         throw new NoViableAltException(LT(1), getFilename());
/*  851:     */       }
/*  852:     */     }
/*  853:     */     catch (RecognitionException ex)
/*  854:     */     {
/*  855: 988 */       if (this.inputState.guessing == 0)
/*  856:     */       {
/*  857: 989 */         reportError(ex);
/*  858: 990 */         recover(ex, _tokenSet_8);
/*  859:     */       }
/*  860:     */       else
/*  861:     */       {
/*  862: 992 */         throw ex;
/*  863:     */       }
/*  864:     */     }
/*  865: 995 */     this.returnAST = functionParameter_AST;
/*  866:     */   }
/*  867:     */   
/*  868:     */   public final void collationName()
/*  869:     */     throws RecognitionException, TokenStreamException
/*  870:     */   {
/*  871:1003 */     this.returnAST = null;
/*  872:1004 */     ASTPair currentAST = new ASTPair();
/*  873:1005 */     AST collationName_AST = null;
/*  874:1006 */     trace("collationSpecification");
/*  875:     */     try
/*  876:     */     {
/*  877:1009 */       AST tmp22_AST = null;
/*  878:1010 */       tmp22_AST = this.astFactory.create(LT(1));
/*  879:1011 */       this.astFactory.addASTChild(currentAST, tmp22_AST);
/*  880:1012 */       match(17);
/*  881:1013 */       collationName_AST = currentAST.root;
/*  882:     */     }
/*  883:     */     catch (RecognitionException ex)
/*  884:     */     {
/*  885:1016 */       if (this.inputState.guessing == 0)
/*  886:     */       {
/*  887:1017 */         reportError(ex);
/*  888:1018 */         recover(ex, _tokenSet_3);
/*  889:     */       }
/*  890:     */       else
/*  891:     */       {
/*  892:1020 */         throw ex;
/*  893:     */       }
/*  894:     */     }
/*  895:1023 */     this.returnAST = collationName_AST;
/*  896:     */   }
/*  897:     */   
/*  898:1027 */   public static final String[] _tokenNames = { "<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "ORDER_BY", "SORT_SPEC", "ORDER_SPEC", "SORT_KEY", "EXPR_LIST", "DOT", "IDENT_LIST", "COLUMN_REF", "\"collate\"", "\"asc\"", "\"desc\"", "COMMA", "HARD_QUOTE", "IDENT", "OPEN_PAREN", "CLOSE_PAREN", "NUM_DOUBLE", "NUM_FLOAT", "NUM_INT", "NUM_LONG", "QUOTED_STRING", "\"ascending\"", "\"descending\"", "ID_START_LETTER", "ID_LETTER", "ESCqs", "HEX_DIGIT", "EXPONENT", "FLOAT_SUFFIX", "WS" };
/*  899:     */   
/*  900:     */   protected void buildTokenTypeASTClassMap()
/*  901:     */   {
/*  902:1065 */     this.tokenTypeToASTClassMap = null;
/*  903:     */   }
/*  904:     */   
/*  905:     */   private static final long[] mk_tokenSet_0()
/*  906:     */   {
/*  907:1069 */     long[] data = { 2L, 0L };
/*  908:1070 */     return data;
/*  909:     */   }
/*  910:     */   
/*  911:1072 */   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
/*  912:     */   
/*  913:     */   private static final long[] mk_tokenSet_1()
/*  914:     */   {
/*  915:1074 */     long[] data = { 32770L, 0L };
/*  916:1075 */     return data;
/*  917:     */   }
/*  918:     */   
/*  919:1077 */   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
/*  920:     */   
/*  921:     */   private static final long[] mk_tokenSet_2()
/*  922:     */   {
/*  923:1079 */     long[] data = { 100724738L, 0L };
/*  924:1080 */     return data;
/*  925:     */   }
/*  926:     */   
/*  927:1082 */   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
/*  928:     */   
/*  929:     */   private static final long[] mk_tokenSet_3()
/*  930:     */   {
/*  931:1084 */     long[] data = { 100720642L, 0L };
/*  932:1085 */     return data;
/*  933:     */   }
/*  934:     */   
/*  935:1087 */   public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
/*  936:     */   
/*  937:     */   private static final long[] mk_tokenSet_4()
/*  938:     */   {
/*  939:1089 */     long[] data = { 32702464L, 0L };
/*  940:1090 */     return data;
/*  941:     */   }
/*  942:     */   
/*  943:1092 */   public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
/*  944:     */   
/*  945:     */   private static final long[] mk_tokenSet_5()
/*  946:     */   {
/*  947:1094 */     long[] data = { 101249026L, 0L };
/*  948:1095 */     return data;
/*  949:     */   }
/*  950:     */   
/*  951:1097 */   public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
/*  952:     */   
/*  953:     */   private static final long[] mk_tokenSet_6()
/*  954:     */   {
/*  955:1099 */     long[] data = { 262144L, 0L };
/*  956:1100 */     return data;
/*  957:     */   }
/*  958:     */   
/*  959:1102 */   public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
/*  960:     */   
/*  961:     */   private static final long[] mk_tokenSet_7()
/*  962:     */   {
/*  963:1104 */     long[] data = { 524288L, 0L };
/*  964:1105 */     return data;
/*  965:     */   }
/*  966:     */   
/*  967:1107 */   public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
/*  968:     */   
/*  969:     */   private static final long[] mk_tokenSet_8()
/*  970:     */   {
/*  971:1109 */     long[] data = { 557056L, 0L };
/*  972:1110 */     return data;
/*  973:     */   }
/*  974:     */   
/*  975:1112 */   public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
/*  976:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.sql.ordering.antlr.GeneratedOrderByFragmentParser
 * JD-Core Version:    0.7.0.1
 */