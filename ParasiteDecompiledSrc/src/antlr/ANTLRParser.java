/*    1:     */ package antlr;
/*    2:     */ 
/*    3:     */ import antlr.collections.impl.BitSet;
/*    4:     */ 
/*    5:     */ public class ANTLRParser
/*    6:     */   extends LLkParser
/*    7:     */   implements ANTLRTokenTypes
/*    8:     */ {
/*    9:     */   private static final boolean DEBUG_PARSER = false;
/*   10:     */   ANTLRGrammarParseBehavior behavior;
/*   11:     */   Tool antlrTool;
/*   12:  32 */   protected int blockNesting = -1;
/*   13:     */   
/*   14:     */   public ANTLRParser(TokenBuffer paramTokenBuffer, ANTLRGrammarParseBehavior paramANTLRGrammarParseBehavior, Tool paramTool)
/*   15:     */   {
/*   16:  39 */     super(paramTokenBuffer, 1);
/*   17:  40 */     this.tokenNames = _tokenNames;
/*   18:  41 */     this.behavior = paramANTLRGrammarParseBehavior;
/*   19:  42 */     this.antlrTool = paramTool;
/*   20:     */   }
/*   21:     */   
/*   22:     */   public void reportError(String paramString)
/*   23:     */   {
/*   24:  46 */     this.antlrTool.error(paramString, getFilename(), -1, -1);
/*   25:     */   }
/*   26:     */   
/*   27:     */   public void reportError(RecognitionException paramRecognitionException)
/*   28:     */   {
/*   29:  50 */     reportError(paramRecognitionException, paramRecognitionException.getErrorMessage());
/*   30:     */   }
/*   31:     */   
/*   32:     */   public void reportError(RecognitionException paramRecognitionException, String paramString)
/*   33:     */   {
/*   34:  54 */     this.antlrTool.error(paramString, paramRecognitionException.getFilename(), paramRecognitionException.getLine(), paramRecognitionException.getColumn());
/*   35:     */   }
/*   36:     */   
/*   37:     */   public void reportWarning(String paramString)
/*   38:     */   {
/*   39:  58 */     this.antlrTool.warning(paramString, getFilename(), -1, -1);
/*   40:     */   }
/*   41:     */   
/*   42:     */   private boolean lastInRule()
/*   43:     */     throws TokenStreamException
/*   44:     */   {
/*   45:  62 */     if ((this.blockNesting == 0) && ((LA(1) == 16) || (LA(1) == 39) || (LA(1) == 21))) {
/*   46:  63 */       return true;
/*   47:     */     }
/*   48:  65 */     return false;
/*   49:     */   }
/*   50:     */   
/*   51:     */   private void checkForMissingEndRule(Token paramToken)
/*   52:     */   {
/*   53:  69 */     if (paramToken.getColumn() == 1) {
/*   54:  70 */       this.antlrTool.warning("did you forget to terminate previous rule?", getFilename(), paramToken.getLine(), paramToken.getColumn());
/*   55:     */     }
/*   56:     */   }
/*   57:     */   
/*   58:     */   protected ANTLRParser(TokenBuffer paramTokenBuffer, int paramInt)
/*   59:     */   {
/*   60:  75 */     super(paramTokenBuffer, paramInt);
/*   61:  76 */     this.tokenNames = _tokenNames;
/*   62:     */   }
/*   63:     */   
/*   64:     */   public ANTLRParser(TokenBuffer paramTokenBuffer)
/*   65:     */   {
/*   66:  80 */     this(paramTokenBuffer, 2);
/*   67:     */   }
/*   68:     */   
/*   69:     */   protected ANTLRParser(TokenStream paramTokenStream, int paramInt)
/*   70:     */   {
/*   71:  84 */     super(paramTokenStream, paramInt);
/*   72:  85 */     this.tokenNames = _tokenNames;
/*   73:     */   }
/*   74:     */   
/*   75:     */   public ANTLRParser(TokenStream paramTokenStream)
/*   76:     */   {
/*   77:  89 */     this(paramTokenStream, 2);
/*   78:     */   }
/*   79:     */   
/*   80:     */   public ANTLRParser(ParserSharedInputState paramParserSharedInputState)
/*   81:     */   {
/*   82:  93 */     super(paramParserSharedInputState, 2);
/*   83:  94 */     this.tokenNames = _tokenNames;
/*   84:     */   }
/*   85:     */   
/*   86:     */   public final void grammar()
/*   87:     */     throws RecognitionException, TokenStreamException
/*   88:     */   {
/*   89:  99 */     Token localToken1 = null;
/*   90: 100 */     Token localToken2 = null;
/*   91:     */     try
/*   92:     */     {
/*   93: 106 */       while (LA(1) == 5)
/*   94:     */       {
/*   95: 107 */         if (this.inputState.guessing == 0) {
/*   96: 109 */           localToken1 = null;
/*   97:     */         }
/*   98: 113 */         match(5);
/*   99: 115 */         switch (LA(1))
/*  100:     */         {
/*  101:     */         case 6: 
/*  102: 118 */           localToken1 = LT(1);
/*  103: 119 */           match(6);
/*  104: 120 */           break;
/*  105:     */         case 7: 
/*  106:     */           break;
/*  107:     */         default: 
/*  108: 128 */           throw new NoViableAltException(LT(1), getFilename());
/*  109:     */         }
/*  110: 132 */         localToken2 = LT(1);
/*  111: 133 */         match(7);
/*  112: 134 */         if (this.inputState.guessing == 0) {
/*  113: 138 */           this.behavior.refHeaderAction(localToken1, localToken2);
/*  114:     */         }
/*  115:     */       }
/*  116: 149 */       switch (LA(1))
/*  117:     */       {
/*  118:     */       case 14: 
/*  119: 152 */         fileOptionsSpec();
/*  120: 153 */         break;
/*  121:     */       case 1: 
/*  122:     */       case 7: 
/*  123:     */       case 8: 
/*  124:     */       case 9: 
/*  125:     */       case 10: 
/*  126:     */         break;
/*  127:     */       case 2: 
/*  128:     */       case 3: 
/*  129:     */       case 4: 
/*  130:     */       case 5: 
/*  131:     */       case 6: 
/*  132:     */       case 11: 
/*  133:     */       case 12: 
/*  134:     */       case 13: 
/*  135:     */       default: 
/*  136: 165 */         throw new NoViableAltException(LT(1), getFilename());
/*  137:     */       }
/*  138: 172 */       while ((LA(1) >= 7) && (LA(1) <= 10)) {
/*  139: 173 */         classDef();
/*  140:     */       }
/*  141: 181 */       match(1);
/*  142:     */     }
/*  143:     */     catch (RecognitionException localRecognitionException)
/*  144:     */     {
/*  145: 184 */       if (this.inputState.guessing == 0)
/*  146:     */       {
/*  147: 186 */         reportError(localRecognitionException, "rule grammar trapped:\n" + localRecognitionException.toString());
/*  148: 187 */         consumeUntil(1);
/*  149:     */       }
/*  150:     */       else
/*  151:     */       {
/*  152: 190 */         throw localRecognitionException;
/*  153:     */       }
/*  154:     */     }
/*  155:     */   }
/*  156:     */   
/*  157:     */   public final void fileOptionsSpec()
/*  158:     */     throws RecognitionException, TokenStreamException
/*  159:     */   {
/*  160: 199 */     match(14);
/*  161: 203 */     while ((LA(1) == 24) || (LA(1) == 41))
/*  162:     */     {
/*  163: 204 */       Token localToken1 = id();
/*  164: 205 */       match(15);
/*  165: 206 */       Token localToken2 = optionValue();
/*  166: 207 */       if (this.inputState.guessing == 0) {
/*  167: 208 */         this.behavior.setFileOption(localToken1, localToken2, getInputState().filename);
/*  168:     */       }
/*  169: 210 */       match(16);
/*  170:     */     }
/*  171: 218 */     match(17);
/*  172:     */   }
/*  173:     */   
/*  174:     */   public final void classDef()
/*  175:     */     throws RecognitionException, TokenStreamException
/*  176:     */   {
/*  177: 223 */     Token localToken1 = null;
/*  178: 224 */     Token localToken2 = null;
/*  179: 225 */     String str = null;
/*  180:     */     try
/*  181:     */     {
/*  182: 229 */       switch (LA(1))
/*  183:     */       {
/*  184:     */       case 7: 
/*  185: 232 */         localToken1 = LT(1);
/*  186: 233 */         match(7);
/*  187: 234 */         if (this.inputState.guessing == 0) {
/*  188: 235 */           this.behavior.refPreambleAction(localToken1);
/*  189:     */         }
/*  190:     */         break;
/*  191:     */       case 8: 
/*  192:     */       case 9: 
/*  193:     */       case 10: 
/*  194:     */         break;
/*  195:     */       default: 
/*  196: 247 */         throw new NoViableAltException(LT(1), getFilename());
/*  197:     */       }
/*  198: 252 */       switch (LA(1))
/*  199:     */       {
/*  200:     */       case 8: 
/*  201: 255 */         localToken2 = LT(1);
/*  202: 256 */         match(8);
/*  203: 257 */         if (this.inputState.guessing == 0) {
/*  204: 258 */           str = localToken2.getText();
/*  205:     */         }
/*  206:     */         break;
/*  207:     */       case 9: 
/*  208:     */       case 10: 
/*  209:     */         break;
/*  210:     */       default: 
/*  211: 269 */         throw new NoViableAltException(LT(1), getFilename());
/*  212:     */       }
/*  213: 274 */       int i = 0;
/*  214:     */       int j;
/*  215: 275 */       if (((LA(1) == 9) || (LA(1) == 10)) && ((LA(2) == 24) || (LA(2) == 41)))
/*  216:     */       {
/*  217: 276 */         j = mark();
/*  218: 277 */         i = 1;
/*  219: 278 */         this.inputState.guessing += 1;
/*  220:     */         try
/*  221:     */         {
/*  222: 281 */           switch (LA(1))
/*  223:     */           {
/*  224:     */           case 9: 
/*  225: 284 */             match(9);
/*  226: 285 */             break;
/*  227:     */           case 10: 
/*  228: 289 */             match(10);
/*  229: 290 */             id();
/*  230: 291 */             match(11);
/*  231: 292 */             match(12);
/*  232: 293 */             break;
/*  233:     */           default: 
/*  234: 297 */             throw new NoViableAltException(LT(1), getFilename());
/*  235:     */           }
/*  236:     */         }
/*  237:     */         catch (RecognitionException localRecognitionException2)
/*  238:     */         {
/*  239: 303 */           i = 0;
/*  240:     */         }
/*  241: 305 */         rewind(j);
/*  242: 306 */         this.inputState.guessing -= 1;
/*  243:     */       }
/*  244: 308 */       if (i != 0)
/*  245:     */       {
/*  246: 309 */         lexerSpec(str);
/*  247:     */       }
/*  248:     */       else
/*  249:     */       {
/*  250: 312 */         j = 0;
/*  251: 313 */         if ((LA(1) == 10) && ((LA(2) == 24) || (LA(2) == 41)))
/*  252:     */         {
/*  253: 314 */           int m = mark();
/*  254: 315 */           j = 1;
/*  255: 316 */           this.inputState.guessing += 1;
/*  256:     */           try
/*  257:     */           {
/*  258: 319 */             match(10);
/*  259: 320 */             id();
/*  260: 321 */             match(11);
/*  261: 322 */             match(13);
/*  262:     */           }
/*  263:     */           catch (RecognitionException localRecognitionException3)
/*  264:     */           {
/*  265: 326 */             j = 0;
/*  266:     */           }
/*  267: 328 */           rewind(m);
/*  268: 329 */           this.inputState.guessing -= 1;
/*  269:     */         }
/*  270: 331 */         if (j != 0) {
/*  271: 332 */           treeParserSpec(str);
/*  272: 334 */         } else if ((LA(1) == 10) && ((LA(2) == 24) || (LA(2) == 41))) {
/*  273: 335 */           parserSpec(str);
/*  274:     */         } else {
/*  275: 338 */           throw new NoViableAltException(LT(1), getFilename());
/*  276:     */         }
/*  277:     */       }
/*  278: 342 */       rules();
/*  279: 343 */       if (this.inputState.guessing == 0) {
/*  280: 344 */         this.behavior.endGrammar();
/*  281:     */       }
/*  282:     */     }
/*  283:     */     catch (RecognitionException localRecognitionException1)
/*  284:     */     {
/*  285: 348 */       if (this.inputState.guessing == 0)
/*  286:     */       {
/*  287: 350 */         if ((localRecognitionException1 instanceof NoViableAltException))
/*  288:     */         {
/*  289: 351 */           NoViableAltException localNoViableAltException = (NoViableAltException)localRecognitionException1;
/*  290: 354 */           if (localNoViableAltException.token.getType() == 8) {
/*  291: 355 */             reportError(localRecognitionException1, "JAVADOC comments may only prefix rules and grammars");
/*  292:     */           } else {
/*  293: 358 */             reportError(localRecognitionException1, "rule classDef trapped:\n" + localRecognitionException1.toString());
/*  294:     */           }
/*  295:     */         }
/*  296:     */         else
/*  297:     */         {
/*  298: 362 */           reportError(localRecognitionException1, "rule classDef trapped:\n" + localRecognitionException1.toString());
/*  299:     */         }
/*  300: 364 */         this.behavior.abortGrammar();
/*  301: 365 */         int k = 1;
/*  302: 367 */         while (k != 0)
/*  303:     */         {
/*  304: 368 */           consume();
/*  305: 369 */           switch (LA(1))
/*  306:     */           {
/*  307:     */           case 1: 
/*  308:     */           case 9: 
/*  309:     */           case 10: 
/*  310: 373 */             k = 0;
/*  311:     */           }
/*  312:     */         }
/*  313:     */       }
/*  314: 379 */       throw localRecognitionException1;
/*  315:     */     }
/*  316:     */   }
/*  317:     */   
/*  318:     */   public final Token id()
/*  319:     */     throws RecognitionException, TokenStreamException
/*  320:     */   {
/*  321: 387 */     Token localToken2 = null;
/*  322: 388 */     Token localToken3 = null;
/*  323: 389 */     Token localToken1 = null;
/*  324: 391 */     switch (LA(1))
/*  325:     */     {
/*  326:     */     case 24: 
/*  327: 394 */       localToken2 = LT(1);
/*  328: 395 */       match(24);
/*  329: 396 */       if (this.inputState.guessing == 0) {
/*  330: 397 */         localToken1 = localToken2;
/*  331:     */       }
/*  332:     */       break;
/*  333:     */     case 41: 
/*  334: 403 */       localToken3 = LT(1);
/*  335: 404 */       match(41);
/*  336: 405 */       if (this.inputState.guessing == 0) {
/*  337: 406 */         localToken1 = localToken3;
/*  338:     */       }
/*  339:     */       break;
/*  340:     */     default: 
/*  341: 412 */       throw new NoViableAltException(LT(1), getFilename());
/*  342:     */     }
/*  343: 415 */     return localToken1;
/*  344:     */   }
/*  345:     */   
/*  346:     */   public final void lexerSpec(String paramString)
/*  347:     */     throws RecognitionException, TokenStreamException
/*  348:     */   {
/*  349: 422 */     Token localToken1 = null;
/*  350: 423 */     Token localToken2 = null;
/*  351:     */     
/*  352:     */ 
/*  353: 426 */     String str = null;
/*  354:     */     Token localToken3;
/*  355: 430 */     switch (LA(1))
/*  356:     */     {
/*  357:     */     case 9: 
/*  358: 433 */       localToken1 = LT(1);
/*  359: 434 */       match(9);
/*  360: 435 */       localToken3 = id();
/*  361: 436 */       if (this.inputState.guessing == 0) {
/*  362: 438 */         this.antlrTool.warning("lexclass' is deprecated; use 'class X extends Lexer'", getFilename(), localToken1.getLine(), localToken1.getColumn());
/*  363:     */       }
/*  364:     */       break;
/*  365:     */     case 10: 
/*  366: 447 */       match(10);
/*  367: 448 */       localToken3 = id();
/*  368: 449 */       match(11);
/*  369: 450 */       match(12);
/*  370: 452 */       switch (LA(1))
/*  371:     */       {
/*  372:     */       case 27: 
/*  373: 455 */         str = superClass();
/*  374: 456 */         break;
/*  375:     */       case 16: 
/*  376:     */         break;
/*  377:     */       default: 
/*  378: 464 */         throw new NoViableAltException(LT(1), getFilename());
/*  379:     */       }
/*  380:     */       break;
/*  381:     */     default: 
/*  382: 472 */       throw new NoViableAltException(LT(1), getFilename());
/*  383:     */     }
/*  384: 476 */     if (this.inputState.guessing == 0) {
/*  385: 477 */       this.behavior.startLexer(getFilename(), localToken3, str, paramString);
/*  386:     */     }
/*  387: 479 */     match(16);
/*  388: 481 */     switch (LA(1))
/*  389:     */     {
/*  390:     */     case 14: 
/*  391: 484 */       lexerOptionsSpec();
/*  392: 485 */       break;
/*  393:     */     case 7: 
/*  394:     */     case 8: 
/*  395:     */     case 23: 
/*  396:     */     case 24: 
/*  397:     */     case 30: 
/*  398:     */     case 31: 
/*  399:     */     case 32: 
/*  400:     */     case 41: 
/*  401:     */       break;
/*  402:     */     case 9: 
/*  403:     */     case 10: 
/*  404:     */     case 11: 
/*  405:     */     case 12: 
/*  406:     */     case 13: 
/*  407:     */     case 15: 
/*  408:     */     case 16: 
/*  409:     */     case 17: 
/*  410:     */     case 18: 
/*  411:     */     case 19: 
/*  412:     */     case 20: 
/*  413:     */     case 21: 
/*  414:     */     case 22: 
/*  415:     */     case 25: 
/*  416:     */     case 26: 
/*  417:     */     case 27: 
/*  418:     */     case 28: 
/*  419:     */     case 29: 
/*  420:     */     case 33: 
/*  421:     */     case 34: 
/*  422:     */     case 35: 
/*  423:     */     case 36: 
/*  424:     */     case 37: 
/*  425:     */     case 38: 
/*  426:     */     case 39: 
/*  427:     */     case 40: 
/*  428:     */     default: 
/*  429: 500 */       throw new NoViableAltException(LT(1), getFilename());
/*  430:     */     }
/*  431: 504 */     if (this.inputState.guessing == 0) {
/*  432: 505 */       this.behavior.endOptions();
/*  433:     */     }
/*  434: 508 */     switch (LA(1))
/*  435:     */     {
/*  436:     */     case 23: 
/*  437: 511 */       tokensSpec();
/*  438: 512 */       break;
/*  439:     */     case 7: 
/*  440:     */     case 8: 
/*  441:     */     case 24: 
/*  442:     */     case 30: 
/*  443:     */     case 31: 
/*  444:     */     case 32: 
/*  445:     */     case 41: 
/*  446:     */       break;
/*  447:     */     default: 
/*  448: 526 */       throw new NoViableAltException(LT(1), getFilename());
/*  449:     */     }
/*  450: 531 */     switch (LA(1))
/*  451:     */     {
/*  452:     */     case 7: 
/*  453: 534 */       localToken2 = LT(1);
/*  454: 535 */       match(7);
/*  455: 536 */       if (this.inputState.guessing == 0) {
/*  456: 537 */         this.behavior.refMemberAction(localToken2);
/*  457:     */       }
/*  458:     */       break;
/*  459:     */     case 8: 
/*  460:     */     case 24: 
/*  461:     */     case 30: 
/*  462:     */     case 31: 
/*  463:     */     case 32: 
/*  464:     */     case 41: 
/*  465:     */       break;
/*  466:     */     default: 
/*  467: 552 */       throw new NoViableAltException(LT(1), getFilename());
/*  468:     */     }
/*  469:     */   }
/*  470:     */   
/*  471:     */   public final void treeParserSpec(String paramString)
/*  472:     */     throws RecognitionException, TokenStreamException
/*  473:     */   {
/*  474: 562 */     Token localToken1 = null;
/*  475:     */     
/*  476:     */ 
/*  477: 565 */     String str = null;
/*  478:     */     
/*  479:     */ 
/*  480: 568 */     match(10);
/*  481: 569 */     Token localToken2 = id();
/*  482: 570 */     match(11);
/*  483: 571 */     match(13);
/*  484: 573 */     switch (LA(1))
/*  485:     */     {
/*  486:     */     case 27: 
/*  487: 576 */       str = superClass();
/*  488: 577 */       break;
/*  489:     */     case 16: 
/*  490:     */       break;
/*  491:     */     default: 
/*  492: 585 */       throw new NoViableAltException(LT(1), getFilename());
/*  493:     */     }
/*  494: 589 */     if (this.inputState.guessing == 0) {
/*  495: 590 */       this.behavior.startTreeWalker(getFilename(), localToken2, str, paramString);
/*  496:     */     }
/*  497: 592 */     match(16);
/*  498: 594 */     switch (LA(1))
/*  499:     */     {
/*  500:     */     case 14: 
/*  501: 597 */       treeParserOptionsSpec();
/*  502: 598 */       break;
/*  503:     */     case 7: 
/*  504:     */     case 8: 
/*  505:     */     case 23: 
/*  506:     */     case 24: 
/*  507:     */     case 30: 
/*  508:     */     case 31: 
/*  509:     */     case 32: 
/*  510:     */     case 41: 
/*  511:     */       break;
/*  512:     */     case 9: 
/*  513:     */     case 10: 
/*  514:     */     case 11: 
/*  515:     */     case 12: 
/*  516:     */     case 13: 
/*  517:     */     case 15: 
/*  518:     */     case 16: 
/*  519:     */     case 17: 
/*  520:     */     case 18: 
/*  521:     */     case 19: 
/*  522:     */     case 20: 
/*  523:     */     case 21: 
/*  524:     */     case 22: 
/*  525:     */     case 25: 
/*  526:     */     case 26: 
/*  527:     */     case 27: 
/*  528:     */     case 28: 
/*  529:     */     case 29: 
/*  530:     */     case 33: 
/*  531:     */     case 34: 
/*  532:     */     case 35: 
/*  533:     */     case 36: 
/*  534:     */     case 37: 
/*  535:     */     case 38: 
/*  536:     */     case 39: 
/*  537:     */     case 40: 
/*  538:     */     default: 
/*  539: 613 */       throw new NoViableAltException(LT(1), getFilename());
/*  540:     */     }
/*  541: 617 */     if (this.inputState.guessing == 0) {
/*  542: 618 */       this.behavior.endOptions();
/*  543:     */     }
/*  544: 621 */     switch (LA(1))
/*  545:     */     {
/*  546:     */     case 23: 
/*  547: 624 */       tokensSpec();
/*  548: 625 */       break;
/*  549:     */     case 7: 
/*  550:     */     case 8: 
/*  551:     */     case 24: 
/*  552:     */     case 30: 
/*  553:     */     case 31: 
/*  554:     */     case 32: 
/*  555:     */     case 41: 
/*  556:     */       break;
/*  557:     */     default: 
/*  558: 639 */       throw new NoViableAltException(LT(1), getFilename());
/*  559:     */     }
/*  560: 644 */     switch (LA(1))
/*  561:     */     {
/*  562:     */     case 7: 
/*  563: 647 */       localToken1 = LT(1);
/*  564: 648 */       match(7);
/*  565: 649 */       if (this.inputState.guessing == 0) {
/*  566: 650 */         this.behavior.refMemberAction(localToken1);
/*  567:     */       }
/*  568:     */       break;
/*  569:     */     case 8: 
/*  570:     */     case 24: 
/*  571:     */     case 30: 
/*  572:     */     case 31: 
/*  573:     */     case 32: 
/*  574:     */     case 41: 
/*  575:     */       break;
/*  576:     */     default: 
/*  577: 665 */       throw new NoViableAltException(LT(1), getFilename());
/*  578:     */     }
/*  579:     */   }
/*  580:     */   
/*  581:     */   public final void parserSpec(String paramString)
/*  582:     */     throws RecognitionException, TokenStreamException
/*  583:     */   {
/*  584: 675 */     Token localToken1 = null;
/*  585:     */     
/*  586:     */ 
/*  587: 678 */     String str = null;
/*  588:     */     
/*  589:     */ 
/*  590: 681 */     match(10);
/*  591: 682 */     Token localToken2 = id();
/*  592: 684 */     switch (LA(1))
/*  593:     */     {
/*  594:     */     case 11: 
/*  595: 687 */       match(11);
/*  596: 688 */       match(29);
/*  597: 690 */       switch (LA(1))
/*  598:     */       {
/*  599:     */       case 27: 
/*  600: 693 */         str = superClass();
/*  601: 694 */         break;
/*  602:     */       case 16: 
/*  603:     */         break;
/*  604:     */       default: 
/*  605: 702 */         throw new NoViableAltException(LT(1), getFilename());
/*  606:     */       }
/*  607:     */       break;
/*  608:     */     case 16: 
/*  609: 710 */       if (this.inputState.guessing == 0) {
/*  610: 712 */         this.antlrTool.warning("use 'class X extends Parser'", getFilename(), localToken2.getLine(), localToken2.getColumn());
/*  611:     */       }
/*  612:     */       break;
/*  613:     */     default: 
/*  614: 721 */       throw new NoViableAltException(LT(1), getFilename());
/*  615:     */     }
/*  616: 725 */     if (this.inputState.guessing == 0) {
/*  617: 726 */       this.behavior.startParser(getFilename(), localToken2, str, paramString);
/*  618:     */     }
/*  619: 728 */     match(16);
/*  620: 730 */     switch (LA(1))
/*  621:     */     {
/*  622:     */     case 14: 
/*  623: 733 */       parserOptionsSpec();
/*  624: 734 */       break;
/*  625:     */     case 7: 
/*  626:     */     case 8: 
/*  627:     */     case 23: 
/*  628:     */     case 24: 
/*  629:     */     case 30: 
/*  630:     */     case 31: 
/*  631:     */     case 32: 
/*  632:     */     case 41: 
/*  633:     */       break;
/*  634:     */     case 9: 
/*  635:     */     case 10: 
/*  636:     */     case 11: 
/*  637:     */     case 12: 
/*  638:     */     case 13: 
/*  639:     */     case 15: 
/*  640:     */     case 16: 
/*  641:     */     case 17: 
/*  642:     */     case 18: 
/*  643:     */     case 19: 
/*  644:     */     case 20: 
/*  645:     */     case 21: 
/*  646:     */     case 22: 
/*  647:     */     case 25: 
/*  648:     */     case 26: 
/*  649:     */     case 27: 
/*  650:     */     case 28: 
/*  651:     */     case 29: 
/*  652:     */     case 33: 
/*  653:     */     case 34: 
/*  654:     */     case 35: 
/*  655:     */     case 36: 
/*  656:     */     case 37: 
/*  657:     */     case 38: 
/*  658:     */     case 39: 
/*  659:     */     case 40: 
/*  660:     */     default: 
/*  661: 749 */       throw new NoViableAltException(LT(1), getFilename());
/*  662:     */     }
/*  663: 753 */     if (this.inputState.guessing == 0) {
/*  664: 754 */       this.behavior.endOptions();
/*  665:     */     }
/*  666: 757 */     switch (LA(1))
/*  667:     */     {
/*  668:     */     case 23: 
/*  669: 760 */       tokensSpec();
/*  670: 761 */       break;
/*  671:     */     case 7: 
/*  672:     */     case 8: 
/*  673:     */     case 24: 
/*  674:     */     case 30: 
/*  675:     */     case 31: 
/*  676:     */     case 32: 
/*  677:     */     case 41: 
/*  678:     */       break;
/*  679:     */     default: 
/*  680: 775 */       throw new NoViableAltException(LT(1), getFilename());
/*  681:     */     }
/*  682: 780 */     switch (LA(1))
/*  683:     */     {
/*  684:     */     case 7: 
/*  685: 783 */       localToken1 = LT(1);
/*  686: 784 */       match(7);
/*  687: 785 */       if (this.inputState.guessing == 0) {
/*  688: 786 */         this.behavior.refMemberAction(localToken1);
/*  689:     */       }
/*  690:     */       break;
/*  691:     */     case 8: 
/*  692:     */     case 24: 
/*  693:     */     case 30: 
/*  694:     */     case 31: 
/*  695:     */     case 32: 
/*  696:     */     case 41: 
/*  697:     */       break;
/*  698:     */     default: 
/*  699: 801 */       throw new NoViableAltException(LT(1), getFilename());
/*  700:     */     }
/*  701:     */   }
/*  702:     */   
/*  703:     */   public final void rules()
/*  704:     */     throws RecognitionException, TokenStreamException
/*  705:     */   {
/*  706: 811 */     int i = 0;
/*  707:     */     for (;;)
/*  708:     */     {
/*  709: 814 */       if ((_tokenSet_0.member(LA(1))) && (_tokenSet_1.member(LA(2))))
/*  710:     */       {
/*  711: 815 */         rule();
/*  712:     */       }
/*  713:     */       else
/*  714:     */       {
/*  715: 818 */         if (i >= 1) {
/*  716:     */           break;
/*  717:     */         }
/*  718: 818 */         throw new NoViableAltException(LT(1), getFilename());
/*  719:     */       }
/*  720: 821 */       i++;
/*  721:     */     }
/*  722:     */   }
/*  723:     */   
/*  724:     */   public final Token optionValue()
/*  725:     */     throws RecognitionException, TokenStreamException
/*  726:     */   {
/*  727: 829 */     Token localToken2 = null;
/*  728: 830 */     Token localToken3 = null;
/*  729: 831 */     Token localToken4 = null;
/*  730: 832 */     Token localToken1 = null;
/*  731: 834 */     switch (LA(1))
/*  732:     */     {
/*  733:     */     case 24: 
/*  734:     */     case 41: 
/*  735: 838 */       localToken1 = qualifiedID();
/*  736: 839 */       break;
/*  737:     */     case 6: 
/*  738: 843 */       localToken2 = LT(1);
/*  739: 844 */       match(6);
/*  740: 845 */       if (this.inputState.guessing == 0) {
/*  741: 846 */         localToken1 = localToken2;
/*  742:     */       }
/*  743:     */       break;
/*  744:     */     case 19: 
/*  745: 852 */       localToken3 = LT(1);
/*  746: 853 */       match(19);
/*  747: 854 */       if (this.inputState.guessing == 0) {
/*  748: 855 */         localToken1 = localToken3;
/*  749:     */       }
/*  750:     */       break;
/*  751:     */     case 20: 
/*  752: 861 */       localToken4 = LT(1);
/*  753: 862 */       match(20);
/*  754: 863 */       if (this.inputState.guessing == 0) {
/*  755: 864 */         localToken1 = localToken4;
/*  756:     */       }
/*  757:     */       break;
/*  758:     */     default: 
/*  759: 870 */       throw new NoViableAltException(LT(1), getFilename());
/*  760:     */     }
/*  761: 873 */     return localToken1;
/*  762:     */   }
/*  763:     */   
/*  764:     */   public final void parserOptionsSpec()
/*  765:     */     throws RecognitionException, TokenStreamException
/*  766:     */   {
/*  767: 880 */     match(14);
/*  768: 884 */     while ((LA(1) == 24) || (LA(1) == 41))
/*  769:     */     {
/*  770: 885 */       Token localToken1 = id();
/*  771: 886 */       match(15);
/*  772: 887 */       Token localToken2 = optionValue();
/*  773: 888 */       if (this.inputState.guessing == 0) {
/*  774: 889 */         this.behavior.setGrammarOption(localToken1, localToken2);
/*  775:     */       }
/*  776: 891 */       match(16);
/*  777:     */     }
/*  778: 899 */     match(17);
/*  779:     */   }
/*  780:     */   
/*  781:     */   public final void treeParserOptionsSpec()
/*  782:     */     throws RecognitionException, TokenStreamException
/*  783:     */   {
/*  784: 906 */     match(14);
/*  785: 910 */     while ((LA(1) == 24) || (LA(1) == 41))
/*  786:     */     {
/*  787: 911 */       Token localToken1 = id();
/*  788: 912 */       match(15);
/*  789: 913 */       Token localToken2 = optionValue();
/*  790: 914 */       if (this.inputState.guessing == 0) {
/*  791: 915 */         this.behavior.setGrammarOption(localToken1, localToken2);
/*  792:     */       }
/*  793: 917 */       match(16);
/*  794:     */     }
/*  795: 925 */     match(17);
/*  796:     */   }
/*  797:     */   
/*  798:     */   public final void lexerOptionsSpec()
/*  799:     */     throws RecognitionException, TokenStreamException
/*  800:     */   {
/*  801: 932 */     match(14);
/*  802:     */     for (;;)
/*  803:     */     {
/*  804: 936 */       switch (LA(1))
/*  805:     */       {
/*  806:     */       case 18: 
/*  807: 939 */         match(18);
/*  808: 940 */         match(15);
/*  809: 941 */         BitSet localBitSet = charSet();
/*  810: 942 */         match(16);
/*  811: 943 */         if (this.inputState.guessing == 0) {
/*  812: 944 */           this.behavior.setCharVocabulary(localBitSet);
/*  813:     */         }
/*  814:     */         break;
/*  815:     */       case 24: 
/*  816:     */       case 41: 
/*  817: 951 */         Token localToken1 = id();
/*  818: 952 */         match(15);
/*  819: 953 */         Token localToken2 = optionValue();
/*  820: 954 */         if (this.inputState.guessing == 0) {
/*  821: 955 */           this.behavior.setGrammarOption(localToken1, localToken2);
/*  822:     */         }
/*  823: 957 */         match(16);
/*  824:     */       }
/*  825:     */     }
/*  826: 967 */     match(17);
/*  827:     */   }
/*  828:     */   
/*  829:     */   public final BitSet charSet()
/*  830:     */     throws RecognitionException, TokenStreamException
/*  831:     */   {
/*  832: 974 */     BitSet localBitSet1 = null;
/*  833: 975 */     BitSet localBitSet2 = null;
/*  834:     */     
/*  835:     */ 
/*  836: 978 */     localBitSet1 = setBlockElement();
/*  837: 982 */     while (LA(1) == 21)
/*  838:     */     {
/*  839: 983 */       match(21);
/*  840: 984 */       localBitSet2 = setBlockElement();
/*  841: 985 */       if (this.inputState.guessing == 0) {
/*  842: 986 */         localBitSet1.orInPlace(localBitSet2);
/*  843:     */       }
/*  844:     */     }
/*  845: 995 */     return localBitSet1;
/*  846:     */   }
/*  847:     */   
/*  848:     */   public final void subruleOptionsSpec()
/*  849:     */     throws RecognitionException, TokenStreamException
/*  850:     */   {
/*  851:1002 */     match(14);
/*  852:1006 */     while ((LA(1) == 24) || (LA(1) == 41))
/*  853:     */     {
/*  854:1007 */       Token localToken1 = id();
/*  855:1008 */       match(15);
/*  856:1009 */       Token localToken2 = optionValue();
/*  857:1010 */       if (this.inputState.guessing == 0) {
/*  858:1011 */         this.behavior.setSubruleOption(localToken1, localToken2);
/*  859:     */       }
/*  860:1013 */       match(16);
/*  861:     */     }
/*  862:1021 */     match(17);
/*  863:     */   }
/*  864:     */   
/*  865:     */   public final Token qualifiedID()
/*  866:     */     throws RecognitionException, TokenStreamException
/*  867:     */   {
/*  868:1028 */     CommonToken localCommonToken = null;
/*  869:     */     
/*  870:     */ 
/*  871:1031 */     StringBuffer localStringBuffer = new StringBuffer(30);
/*  872:     */     
/*  873:     */ 
/*  874:     */ 
/*  875:1035 */     Token localToken = id();
/*  876:1036 */     if (this.inputState.guessing == 0) {
/*  877:1037 */       localStringBuffer.append(localToken.getText());
/*  878:     */     }
/*  879:1042 */     while (LA(1) == 50)
/*  880:     */     {
/*  881:1043 */       match(50);
/*  882:1044 */       localToken = id();
/*  883:1045 */       if (this.inputState.guessing == 0)
/*  884:     */       {
/*  885:1046 */         localStringBuffer.append('.');localStringBuffer.append(localToken.getText());
/*  886:     */       }
/*  887:     */     }
/*  888:1055 */     if (this.inputState.guessing == 0)
/*  889:     */     {
/*  890:1059 */       localCommonToken = new CommonToken(24, localStringBuffer.toString());
/*  891:1060 */       localCommonToken.setLine(localToken.getLine());
/*  892:     */     }
/*  893:1063 */     return localCommonToken;
/*  894:     */   }
/*  895:     */   
/*  896:     */   public final BitSet setBlockElement()
/*  897:     */     throws RecognitionException, TokenStreamException
/*  898:     */   {
/*  899:1069 */     Token localToken1 = null;
/*  900:1070 */     Token localToken2 = null;
/*  901:     */     
/*  902:1072 */     BitSet localBitSet = null;
/*  903:1073 */     int i = 0;
/*  904:     */     
/*  905:     */ 
/*  906:1076 */     localToken1 = LT(1);
/*  907:1077 */     match(19);
/*  908:1078 */     if (this.inputState.guessing == 0)
/*  909:     */     {
/*  910:1080 */       i = ANTLRLexer.tokenTypeForCharLiteral(localToken1.getText());
/*  911:1081 */       localBitSet = BitSet.of(i);
/*  912:     */     }
/*  913:     */     int k;
/*  914:1085 */     switch (LA(1))
/*  915:     */     {
/*  916:     */     case 22: 
/*  917:1088 */       match(22);
/*  918:1089 */       localToken2 = LT(1);
/*  919:1090 */       match(19);
/*  920:1091 */       if (this.inputState.guessing == 0)
/*  921:     */       {
/*  922:1093 */         int j = ANTLRLexer.tokenTypeForCharLiteral(localToken2.getText());
/*  923:1094 */         if (j < i) {
/*  924:1095 */           this.antlrTool.error("Malformed range line ", getFilename(), localToken1.getLine(), localToken1.getColumn());
/*  925:     */         }
/*  926:1097 */         for (k = i + 1; k <= j;)
/*  927:     */         {
/*  928:1098 */           localBitSet.add(k);k++; continue;
/*  929:     */           
/*  930:     */ 
/*  931:     */ 
/*  932:     */ 
/*  933:     */ 
/*  934:     */ 
/*  935:     */ 
/*  936:     */ 
/*  937:1107 */           break;
/*  938:     */           
/*  939:     */ 
/*  940:     */ 
/*  941:1111 */           throw new NoViableAltException(LT(1), getFilename());
/*  942:     */         }
/*  943:     */       }
/*  944:     */       break;
/*  945:     */     }
/*  946:1115 */     return localBitSet;
/*  947:     */   }
/*  948:     */   
/*  949:     */   public final void tokensSpec()
/*  950:     */     throws RecognitionException, TokenStreamException
/*  951:     */   {
/*  952:1120 */     Token localToken1 = null;
/*  953:1121 */     Token localToken2 = null;
/*  954:1122 */     Token localToken3 = null;
/*  955:     */     
/*  956:1124 */     match(23);
/*  957:     */     
/*  958:1126 */     int i = 0;
/*  959:     */     for (;;)
/*  960:     */     {
/*  961:1129 */       if ((LA(1) == 6) || (LA(1) == 24))
/*  962:     */       {
/*  963:1131 */         switch (LA(1))
/*  964:     */         {
/*  965:     */         case 24: 
/*  966:1134 */           if (this.inputState.guessing == 0) {
/*  967:1135 */             localToken2 = null;
/*  968:     */           }
/*  969:1137 */           localToken1 = LT(1);
/*  970:1138 */           match(24);
/*  971:1140 */           switch (LA(1))
/*  972:     */           {
/*  973:     */           case 15: 
/*  974:1143 */             match(15);
/*  975:1144 */             localToken2 = LT(1);
/*  976:1145 */             match(6);
/*  977:1146 */             break;
/*  978:     */           case 16: 
/*  979:     */           case 25: 
/*  980:     */             break;
/*  981:     */           default: 
/*  982:1155 */             throw new NoViableAltException(LT(1), getFilename());
/*  983:     */           }
/*  984:1159 */           if (this.inputState.guessing == 0) {
/*  985:1160 */             this.behavior.defineToken(localToken1, localToken2);
/*  986:     */           }
/*  987:1163 */           switch (LA(1))
/*  988:     */           {
/*  989:     */           case 25: 
/*  990:1166 */             tokensSpecOptions(localToken1);
/*  991:1167 */             break;
/*  992:     */           case 16: 
/*  993:     */             break;
/*  994:     */           default: 
/*  995:1175 */             throw new NoViableAltException(LT(1), getFilename());
/*  996:     */           }
/*  997:     */           break;
/*  998:     */         case 6: 
/*  999:1183 */           localToken3 = LT(1);
/* 1000:1184 */           match(6);
/* 1001:1185 */           if (this.inputState.guessing == 0) {
/* 1002:1186 */             this.behavior.defineToken(null, localToken3);
/* 1003:     */           }
/* 1004:1189 */           switch (LA(1))
/* 1005:     */           {
/* 1006:     */           case 25: 
/* 1007:1192 */             tokensSpecOptions(localToken3);
/* 1008:1193 */             break;
/* 1009:     */           case 16: 
/* 1010:     */             break;
/* 1011:     */           default: 
/* 1012:1201 */             throw new NoViableAltException(LT(1), getFilename());
/* 1013:     */           }
/* 1014:     */           break;
/* 1015:     */         default: 
/* 1016:1209 */           throw new NoViableAltException(LT(1), getFilename());
/* 1017:     */         }
/* 1018:1213 */         match(16);
/* 1019:     */       }
/* 1020:     */       else
/* 1021:     */       {
/* 1022:1216 */         if (i >= 1) {
/* 1023:     */           break;
/* 1024:     */         }
/* 1025:1216 */         throw new NoViableAltException(LT(1), getFilename());
/* 1026:     */       }
/* 1027:1219 */       i++;
/* 1028:     */     }
/* 1029:1222 */     match(17);
/* 1030:     */   }
/* 1031:     */   
/* 1032:     */   public final void tokensSpecOptions(Token paramToken)
/* 1033:     */     throws RecognitionException, TokenStreamException
/* 1034:     */   {
/* 1035:1230 */     Token localToken1 = null;Token localToken2 = null;
/* 1036:     */     
/* 1037:     */ 
/* 1038:1233 */     match(25);
/* 1039:1234 */     localToken1 = id();
/* 1040:1235 */     match(15);
/* 1041:1236 */     localToken2 = optionValue();
/* 1042:1237 */     if (this.inputState.guessing == 0) {
/* 1043:1238 */       this.behavior.refTokensSpecElementOption(paramToken, localToken1, localToken2);
/* 1044:     */     }
/* 1045:1243 */     while (LA(1) == 16)
/* 1046:     */     {
/* 1047:1244 */       match(16);
/* 1048:1245 */       localToken1 = id();
/* 1049:1246 */       match(15);
/* 1050:1247 */       localToken2 = optionValue();
/* 1051:1248 */       if (this.inputState.guessing == 0) {
/* 1052:1249 */         this.behavior.refTokensSpecElementOption(paramToken, localToken1, localToken2);
/* 1053:     */       }
/* 1054:     */     }
/* 1055:1258 */     match(26);
/* 1056:     */   }
/* 1057:     */   
/* 1058:     */   public final String superClass()
/* 1059:     */     throws RecognitionException, TokenStreamException
/* 1060:     */   {
/* 1061:1264 */     String str = null;
/* 1062:     */     
/* 1063:1266 */     match(27);
/* 1064:1267 */     if (this.inputState.guessing == 0)
/* 1065:     */     {
/* 1066:1269 */       str = LT(1).getText();
/* 1067:1270 */       str = StringUtils.stripFrontBack(str, "\"", "\"");
/* 1068:     */     }
/* 1069:1274 */     match(6);
/* 1070:     */     
/* 1071:1276 */     match(28);
/* 1072:1277 */     return str;
/* 1073:     */   }
/* 1074:     */   
/* 1075:     */   public final void rule()
/* 1076:     */     throws RecognitionException, TokenStreamException
/* 1077:     */   {
/* 1078:1282 */     Token localToken1 = null;
/* 1079:1283 */     Token localToken2 = null;
/* 1080:1284 */     Token localToken3 = null;
/* 1081:1285 */     Token localToken4 = null;
/* 1082:1286 */     Token localToken5 = null;
/* 1083:1287 */     Token localToken6 = null;
/* 1084:1288 */     Token localToken7 = null;
/* 1085:     */     
/* 1086:1290 */     String str1 = "public";
/* 1087:     */     
/* 1088:1292 */     String str2 = null;
/* 1089:1293 */     boolean bool = true;
/* 1090:1294 */     this.blockNesting = -1;
/* 1091:1298 */     switch (LA(1))
/* 1092:     */     {
/* 1093:     */     case 8: 
/* 1094:1301 */       localToken1 = LT(1);
/* 1095:1302 */       match(8);
/* 1096:1303 */       if (this.inputState.guessing == 0) {
/* 1097:1304 */         str2 = localToken1.getText();
/* 1098:     */       }
/* 1099:     */       break;
/* 1100:     */     case 24: 
/* 1101:     */     case 30: 
/* 1102:     */     case 31: 
/* 1103:     */     case 32: 
/* 1104:     */     case 41: 
/* 1105:     */       break;
/* 1106:     */     default: 
/* 1107:1318 */       throw new NoViableAltException(LT(1), getFilename());
/* 1108:     */     }
/* 1109:1323 */     switch (LA(1))
/* 1110:     */     {
/* 1111:     */     case 30: 
/* 1112:1326 */       localToken2 = LT(1);
/* 1113:1327 */       match(30);
/* 1114:1328 */       if (this.inputState.guessing == 0) {
/* 1115:1329 */         str1 = localToken2.getText();
/* 1116:     */       }
/* 1117:     */       break;
/* 1118:     */     case 31: 
/* 1119:1335 */       localToken3 = LT(1);
/* 1120:1336 */       match(31);
/* 1121:1337 */       if (this.inputState.guessing == 0) {
/* 1122:1338 */         str1 = localToken3.getText();
/* 1123:     */       }
/* 1124:     */       break;
/* 1125:     */     case 32: 
/* 1126:1344 */       localToken4 = LT(1);
/* 1127:1345 */       match(32);
/* 1128:1346 */       if (this.inputState.guessing == 0) {
/* 1129:1347 */         str1 = localToken4.getText();
/* 1130:     */       }
/* 1131:     */       break;
/* 1132:     */     case 24: 
/* 1133:     */     case 41: 
/* 1134:     */       break;
/* 1135:     */     default: 
/* 1136:1358 */       throw new NoViableAltException(LT(1), getFilename());
/* 1137:     */     }
/* 1138:1362 */     Token localToken8 = id();
/* 1139:1364 */     switch (LA(1))
/* 1140:     */     {
/* 1141:     */     case 33: 
/* 1142:1367 */       match(33);
/* 1143:1368 */       if (this.inputState.guessing == 0) {
/* 1144:1369 */         bool = false;
/* 1145:     */       }
/* 1146:     */       break;
/* 1147:     */     case 7: 
/* 1148:     */     case 14: 
/* 1149:     */     case 34: 
/* 1150:     */     case 35: 
/* 1151:     */     case 36: 
/* 1152:     */     case 37: 
/* 1153:     */       break;
/* 1154:     */     default: 
/* 1155:1384 */       throw new NoViableAltException(LT(1), getFilename());
/* 1156:     */     }
/* 1157:1388 */     if (this.inputState.guessing == 0) {
/* 1158:1390 */       this.behavior.defineRuleName(localToken8, str1, bool, str2);
/* 1159:     */     }
/* 1160:1394 */     switch (LA(1))
/* 1161:     */     {
/* 1162:     */     case 34: 
/* 1163:1397 */       localToken5 = LT(1);
/* 1164:1398 */       match(34);
/* 1165:1399 */       if (this.inputState.guessing == 0) {
/* 1166:1400 */         this.behavior.refArgAction(localToken5);
/* 1167:     */       }
/* 1168:     */       break;
/* 1169:     */     case 7: 
/* 1170:     */     case 14: 
/* 1171:     */     case 35: 
/* 1172:     */     case 36: 
/* 1173:     */     case 37: 
/* 1174:     */       break;
/* 1175:     */     default: 
/* 1176:1414 */       throw new NoViableAltException(LT(1), getFilename());
/* 1177:     */     }
/* 1178:1419 */     switch (LA(1))
/* 1179:     */     {
/* 1180:     */     case 35: 
/* 1181:1422 */       match(35);
/* 1182:1423 */       localToken6 = LT(1);
/* 1183:1424 */       match(34);
/* 1184:1425 */       if (this.inputState.guessing == 0) {
/* 1185:1426 */         this.behavior.refReturnAction(localToken6);
/* 1186:     */       }
/* 1187:     */       break;
/* 1188:     */     case 7: 
/* 1189:     */     case 14: 
/* 1190:     */     case 36: 
/* 1191:     */     case 37: 
/* 1192:     */       break;
/* 1193:     */     default: 
/* 1194:1439 */       throw new NoViableAltException(LT(1), getFilename());
/* 1195:     */     }
/* 1196:1444 */     switch (LA(1))
/* 1197:     */     {
/* 1198:     */     case 37: 
/* 1199:1447 */       throwsSpec();
/* 1200:1448 */       break;
/* 1201:     */     case 7: 
/* 1202:     */     case 14: 
/* 1203:     */     case 36: 
/* 1204:     */       break;
/* 1205:     */     default: 
/* 1206:1458 */       throw new NoViableAltException(LT(1), getFilename());
/* 1207:     */     }
/* 1208:1463 */     switch (LA(1))
/* 1209:     */     {
/* 1210:     */     case 14: 
/* 1211:1466 */       ruleOptionsSpec();
/* 1212:1467 */       break;
/* 1213:     */     case 7: 
/* 1214:     */     case 36: 
/* 1215:     */       break;
/* 1216:     */     default: 
/* 1217:1476 */       throw new NoViableAltException(LT(1), getFilename());
/* 1218:     */     }
/* 1219:1481 */     switch (LA(1))
/* 1220:     */     {
/* 1221:     */     case 7: 
/* 1222:1484 */       localToken7 = LT(1);
/* 1223:1485 */       match(7);
/* 1224:1486 */       if (this.inputState.guessing == 0) {
/* 1225:1487 */         this.behavior.refInitAction(localToken7);
/* 1226:     */       }
/* 1227:     */       break;
/* 1228:     */     case 36: 
/* 1229:     */       break;
/* 1230:     */     default: 
/* 1231:1497 */       throw new NoViableAltException(LT(1), getFilename());
/* 1232:     */     }
/* 1233:1501 */     match(36);
/* 1234:1502 */     block();
/* 1235:1503 */     match(16);
/* 1236:1505 */     switch (LA(1))
/* 1237:     */     {
/* 1238:     */     case 39: 
/* 1239:1508 */       exceptionGroup();
/* 1240:1509 */       break;
/* 1241:     */     case 1: 
/* 1242:     */     case 7: 
/* 1243:     */     case 8: 
/* 1244:     */     case 9: 
/* 1245:     */     case 10: 
/* 1246:     */     case 24: 
/* 1247:     */     case 30: 
/* 1248:     */     case 31: 
/* 1249:     */     case 32: 
/* 1250:     */     case 41: 
/* 1251:     */       break;
/* 1252:     */     case 2: 
/* 1253:     */     case 3: 
/* 1254:     */     case 4: 
/* 1255:     */     case 5: 
/* 1256:     */     case 6: 
/* 1257:     */     case 11: 
/* 1258:     */     case 12: 
/* 1259:     */     case 13: 
/* 1260:     */     case 14: 
/* 1261:     */     case 15: 
/* 1262:     */     case 16: 
/* 1263:     */     case 17: 
/* 1264:     */     case 18: 
/* 1265:     */     case 19: 
/* 1266:     */     case 20: 
/* 1267:     */     case 21: 
/* 1268:     */     case 22: 
/* 1269:     */     case 23: 
/* 1270:     */     case 25: 
/* 1271:     */     case 26: 
/* 1272:     */     case 27: 
/* 1273:     */     case 28: 
/* 1274:     */     case 29: 
/* 1275:     */     case 33: 
/* 1276:     */     case 34: 
/* 1277:     */     case 35: 
/* 1278:     */     case 36: 
/* 1279:     */     case 37: 
/* 1280:     */     case 38: 
/* 1281:     */     case 40: 
/* 1282:     */     default: 
/* 1283:1526 */       throw new NoViableAltException(LT(1), getFilename());
/* 1284:     */     }
/* 1285:1530 */     if (this.inputState.guessing == 0) {
/* 1286:1531 */       this.behavior.endRule(localToken8.getText());
/* 1287:     */     }
/* 1288:     */   }
/* 1289:     */   
/* 1290:     */   public final void throwsSpec()
/* 1291:     */     throws RecognitionException, TokenStreamException
/* 1292:     */   {
/* 1293:1538 */     String str = null;
/* 1294:     */     
/* 1295:     */ 
/* 1296:     */ 
/* 1297:1542 */     match(37);
/* 1298:1543 */     Token localToken1 = id();
/* 1299:1544 */     if (this.inputState.guessing == 0) {
/* 1300:1545 */       str = localToken1.getText();
/* 1301:     */     }
/* 1302:1550 */     while (LA(1) == 38)
/* 1303:     */     {
/* 1304:1551 */       match(38);
/* 1305:1552 */       Token localToken2 = id();
/* 1306:1553 */       if (this.inputState.guessing == 0) {
/* 1307:1554 */         str = str + "," + localToken2.getText();
/* 1308:     */       }
/* 1309:     */     }
/* 1310:1563 */     if (this.inputState.guessing == 0) {
/* 1311:1564 */       this.behavior.setUserExceptions(str);
/* 1312:     */     }
/* 1313:     */   }
/* 1314:     */   
/* 1315:     */   public final void ruleOptionsSpec()
/* 1316:     */     throws RecognitionException, TokenStreamException
/* 1317:     */   {
/* 1318:1572 */     match(14);
/* 1319:1576 */     while ((LA(1) == 24) || (LA(1) == 41))
/* 1320:     */     {
/* 1321:1577 */       Token localToken1 = id();
/* 1322:1578 */       match(15);
/* 1323:1579 */       Token localToken2 = optionValue();
/* 1324:1580 */       if (this.inputState.guessing == 0) {
/* 1325:1581 */         this.behavior.setRuleOption(localToken1, localToken2);
/* 1326:     */       }
/* 1327:1583 */       match(16);
/* 1328:     */     }
/* 1329:1591 */     match(17);
/* 1330:     */   }
/* 1331:     */   
/* 1332:     */   public final void block()
/* 1333:     */     throws RecognitionException, TokenStreamException
/* 1334:     */   {
/* 1335:1597 */     if (this.inputState.guessing == 0) {
/* 1336:1598 */       this.blockNesting += 1;
/* 1337:     */     }
/* 1338:1600 */     alternative();
/* 1339:1604 */     while (LA(1) == 21)
/* 1340:     */     {
/* 1341:1605 */       match(21);
/* 1342:1606 */       alternative();
/* 1343:     */     }
/* 1344:1614 */     if (this.inputState.guessing == 0) {
/* 1345:1615 */       this.blockNesting -= 1;
/* 1346:     */     }
/* 1347:     */   }
/* 1348:     */   
/* 1349:     */   public final void exceptionGroup()
/* 1350:     */     throws RecognitionException, TokenStreamException
/* 1351:     */   {
/* 1352:1622 */     if (this.inputState.guessing == 0) {
/* 1353:1623 */       this.behavior.beginExceptionGroup();
/* 1354:     */     }
/* 1355:1626 */     int i = 0;
/* 1356:     */     for (;;)
/* 1357:     */     {
/* 1358:1629 */       if (LA(1) == 39)
/* 1359:     */       {
/* 1360:1630 */         exceptionSpec();
/* 1361:     */       }
/* 1362:     */       else
/* 1363:     */       {
/* 1364:1633 */         if (i >= 1) {
/* 1365:     */           break;
/* 1366:     */         }
/* 1367:1633 */         throw new NoViableAltException(LT(1), getFilename());
/* 1368:     */       }
/* 1369:1636 */       i++;
/* 1370:     */     }
/* 1371:1639 */     if (this.inputState.guessing == 0) {
/* 1372:1640 */       this.behavior.endExceptionGroup();
/* 1373:     */     }
/* 1374:     */   }
/* 1375:     */   
/* 1376:     */   public final void alternative()
/* 1377:     */     throws RecognitionException, TokenStreamException
/* 1378:     */   {
/* 1379:1646 */     boolean bool = true;
/* 1380:1649 */     switch (LA(1))
/* 1381:     */     {
/* 1382:     */     case 33: 
/* 1383:1652 */       match(33);
/* 1384:1653 */       if (this.inputState.guessing == 0) {
/* 1385:1654 */         bool = false;
/* 1386:     */       }
/* 1387:     */       break;
/* 1388:     */     case 6: 
/* 1389:     */     case 7: 
/* 1390:     */     case 16: 
/* 1391:     */     case 19: 
/* 1392:     */     case 21: 
/* 1393:     */     case 24: 
/* 1394:     */     case 27: 
/* 1395:     */     case 28: 
/* 1396:     */     case 39: 
/* 1397:     */     case 41: 
/* 1398:     */     case 42: 
/* 1399:     */     case 43: 
/* 1400:     */     case 44: 
/* 1401:     */     case 50: 
/* 1402:     */       break;
/* 1403:     */     case 8: 
/* 1404:     */     case 9: 
/* 1405:     */     case 10: 
/* 1406:     */     case 11: 
/* 1407:     */     case 12: 
/* 1408:     */     case 13: 
/* 1409:     */     case 14: 
/* 1410:     */     case 15: 
/* 1411:     */     case 17: 
/* 1412:     */     case 18: 
/* 1413:     */     case 20: 
/* 1414:     */     case 22: 
/* 1415:     */     case 23: 
/* 1416:     */     case 25: 
/* 1417:     */     case 26: 
/* 1418:     */     case 29: 
/* 1419:     */     case 30: 
/* 1420:     */     case 31: 
/* 1421:     */     case 32: 
/* 1422:     */     case 34: 
/* 1423:     */     case 35: 
/* 1424:     */     case 36: 
/* 1425:     */     case 37: 
/* 1426:     */     case 38: 
/* 1427:     */     case 40: 
/* 1428:     */     case 45: 
/* 1429:     */     case 46: 
/* 1430:     */     case 47: 
/* 1431:     */     case 48: 
/* 1432:     */     case 49: 
/* 1433:     */     default: 
/* 1434:1677 */       throw new NoViableAltException(LT(1), getFilename());
/* 1435:     */     }
/* 1436:1681 */     if (this.inputState.guessing == 0) {
/* 1437:1682 */       this.behavior.beginAlt(bool);
/* 1438:     */     }
/* 1439:1687 */     while (_tokenSet_2.member(LA(1))) {
/* 1440:1688 */       element();
/* 1441:     */     }
/* 1442:1697 */     switch (LA(1))
/* 1443:     */     {
/* 1444:     */     case 39: 
/* 1445:1700 */       exceptionSpecNoLabel();
/* 1446:1701 */       break;
/* 1447:     */     case 16: 
/* 1448:     */     case 21: 
/* 1449:     */     case 28: 
/* 1450:     */       break;
/* 1451:     */     default: 
/* 1452:1711 */       throw new NoViableAltException(LT(1), getFilename());
/* 1453:     */     }
/* 1454:1715 */     if (this.inputState.guessing == 0) {
/* 1455:1716 */       this.behavior.endAlt();
/* 1456:     */     }
/* 1457:     */   }
/* 1458:     */   
/* 1459:     */   public final void element()
/* 1460:     */     throws RecognitionException, TokenStreamException
/* 1461:     */   {
/* 1462:1723 */     elementNoOptionSpec();
/* 1463:1725 */     switch (LA(1))
/* 1464:     */     {
/* 1465:     */     case 25: 
/* 1466:1728 */       elementOptionSpec();
/* 1467:1729 */       break;
/* 1468:     */     case 6: 
/* 1469:     */     case 7: 
/* 1470:     */     case 16: 
/* 1471:     */     case 19: 
/* 1472:     */     case 21: 
/* 1473:     */     case 24: 
/* 1474:     */     case 27: 
/* 1475:     */     case 28: 
/* 1476:     */     case 39: 
/* 1477:     */     case 41: 
/* 1478:     */     case 42: 
/* 1479:     */     case 43: 
/* 1480:     */     case 44: 
/* 1481:     */     case 50: 
/* 1482:     */       break;
/* 1483:     */     case 8: 
/* 1484:     */     case 9: 
/* 1485:     */     case 10: 
/* 1486:     */     case 11: 
/* 1487:     */     case 12: 
/* 1488:     */     case 13: 
/* 1489:     */     case 14: 
/* 1490:     */     case 15: 
/* 1491:     */     case 17: 
/* 1492:     */     case 18: 
/* 1493:     */     case 20: 
/* 1494:     */     case 22: 
/* 1495:     */     case 23: 
/* 1496:     */     case 26: 
/* 1497:     */     case 29: 
/* 1498:     */     case 30: 
/* 1499:     */     case 31: 
/* 1500:     */     case 32: 
/* 1501:     */     case 33: 
/* 1502:     */     case 34: 
/* 1503:     */     case 35: 
/* 1504:     */     case 36: 
/* 1505:     */     case 37: 
/* 1506:     */     case 38: 
/* 1507:     */     case 40: 
/* 1508:     */     case 45: 
/* 1509:     */     case 46: 
/* 1510:     */     case 47: 
/* 1511:     */     case 48: 
/* 1512:     */     case 49: 
/* 1513:     */     default: 
/* 1514:1750 */       throw new NoViableAltException(LT(1), getFilename());
/* 1515:     */     }
/* 1516:     */   }
/* 1517:     */   
/* 1518:     */   public final void exceptionSpecNoLabel()
/* 1519:     */     throws RecognitionException, TokenStreamException
/* 1520:     */   {
/* 1521:1759 */     match(39);
/* 1522:1760 */     if (this.inputState.guessing == 0) {
/* 1523:1761 */       this.behavior.beginExceptionSpec(null);
/* 1524:     */     }
/* 1525:1766 */     while (LA(1) == 40) {
/* 1526:1767 */       exceptionHandler();
/* 1527:     */     }
/* 1528:1775 */     if (this.inputState.guessing == 0) {
/* 1529:1776 */       this.behavior.endExceptionSpec();
/* 1530:     */     }
/* 1531:     */   }
/* 1532:     */   
/* 1533:     */   public final void exceptionSpec()
/* 1534:     */     throws RecognitionException, TokenStreamException
/* 1535:     */   {
/* 1536:1782 */     Token localToken1 = null;
/* 1537:1783 */     Token localToken2 = null;
/* 1538:     */     
/* 1539:1785 */     match(39);
/* 1540:1787 */     switch (LA(1))
/* 1541:     */     {
/* 1542:     */     case 34: 
/* 1543:1790 */       localToken1 = LT(1);
/* 1544:1791 */       match(34);
/* 1545:1792 */       if (this.inputState.guessing == 0) {
/* 1546:1793 */         localToken2 = localToken1;
/* 1547:     */       }
/* 1548:     */       break;
/* 1549:     */     case 1: 
/* 1550:     */     case 7: 
/* 1551:     */     case 8: 
/* 1552:     */     case 9: 
/* 1553:     */     case 10: 
/* 1554:     */     case 24: 
/* 1555:     */     case 30: 
/* 1556:     */     case 31: 
/* 1557:     */     case 32: 
/* 1558:     */     case 39: 
/* 1559:     */     case 40: 
/* 1560:     */     case 41: 
/* 1561:     */       break;
/* 1562:     */     case 2: 
/* 1563:     */     case 3: 
/* 1564:     */     case 4: 
/* 1565:     */     case 5: 
/* 1566:     */     case 6: 
/* 1567:     */     case 11: 
/* 1568:     */     case 12: 
/* 1569:     */     case 13: 
/* 1570:     */     case 14: 
/* 1571:     */     case 15: 
/* 1572:     */     case 16: 
/* 1573:     */     case 17: 
/* 1574:     */     case 18: 
/* 1575:     */     case 19: 
/* 1576:     */     case 20: 
/* 1577:     */     case 21: 
/* 1578:     */     case 22: 
/* 1579:     */     case 23: 
/* 1580:     */     case 25: 
/* 1581:     */     case 26: 
/* 1582:     */     case 27: 
/* 1583:     */     case 28: 
/* 1584:     */     case 29: 
/* 1585:     */     case 33: 
/* 1586:     */     case 35: 
/* 1587:     */     case 36: 
/* 1588:     */     case 37: 
/* 1589:     */     case 38: 
/* 1590:     */     default: 
/* 1591:1814 */       throw new NoViableAltException(LT(1), getFilename());
/* 1592:     */     }
/* 1593:1818 */     if (this.inputState.guessing == 0) {
/* 1594:1819 */       this.behavior.beginExceptionSpec(localToken2);
/* 1595:     */     }
/* 1596:1824 */     while (LA(1) == 40) {
/* 1597:1825 */       exceptionHandler();
/* 1598:     */     }
/* 1599:1833 */     if (this.inputState.guessing == 0) {
/* 1600:1834 */       this.behavior.endExceptionSpec();
/* 1601:     */     }
/* 1602:     */   }
/* 1603:     */   
/* 1604:     */   public final void exceptionHandler()
/* 1605:     */     throws RecognitionException, TokenStreamException
/* 1606:     */   {
/* 1607:1840 */     Token localToken1 = null;
/* 1608:1841 */     Token localToken2 = null;
/* 1609:     */     
/* 1610:     */ 
/* 1611:1844 */     match(40);
/* 1612:1845 */     localToken1 = LT(1);
/* 1613:1846 */     match(34);
/* 1614:1847 */     localToken2 = LT(1);
/* 1615:1848 */     match(7);
/* 1616:1849 */     if (this.inputState.guessing == 0) {
/* 1617:1850 */       this.behavior.refExceptionHandler(localToken1, localToken2);
/* 1618:     */     }
/* 1619:     */   }
/* 1620:     */   
/* 1621:     */   public final void elementNoOptionSpec()
/* 1622:     */     throws RecognitionException, TokenStreamException
/* 1623:     */   {
/* 1624:1856 */     Token localToken1 = null;
/* 1625:1857 */     Token localToken2 = null;
/* 1626:1858 */     Token localToken3 = null;
/* 1627:1859 */     Token localToken4 = null;
/* 1628:1860 */     Token localToken5 = null;
/* 1629:1861 */     Token localToken6 = null;
/* 1630:1862 */     Token localToken7 = null;
/* 1631:1863 */     Token localToken8 = null;
/* 1632:     */     
/* 1633:1865 */     Token localToken9 = null;
/* 1634:1866 */     Token localToken10 = null;
/* 1635:1867 */     Token localToken11 = null;
/* 1636:1868 */     int i = 1;
/* 1637:1871 */     switch (LA(1))
/* 1638:     */     {
/* 1639:     */     case 7: 
/* 1640:1874 */       localToken7 = LT(1);
/* 1641:1875 */       match(7);
/* 1642:1876 */       if (this.inputState.guessing == 0) {
/* 1643:1877 */         this.behavior.refAction(localToken7);
/* 1644:     */       }
/* 1645:     */       break;
/* 1646:     */     case 43: 
/* 1647:1883 */       localToken8 = LT(1);
/* 1648:1884 */       match(43);
/* 1649:1885 */       if (this.inputState.guessing == 0) {
/* 1650:1886 */         this.behavior.refSemPred(localToken8);
/* 1651:     */       }
/* 1652:     */       break;
/* 1653:     */     case 44: 
/* 1654:1892 */       tree();
/* 1655:1893 */       break;
/* 1656:     */     default: 
/* 1657:1896 */       if (((LA(1) == 24) || (LA(1) == 41)) && (LA(2) == 15))
/* 1658:     */       {
/* 1659:1897 */         localToken10 = id();
/* 1660:1898 */         match(15);
/* 1661:1900 */         if (((LA(1) == 24) || (LA(1) == 41)) && (LA(2) == 36))
/* 1662:     */         {
/* 1663:1901 */           localToken9 = id();
/* 1664:1902 */           match(36);
/* 1665:1903 */           if (this.inputState.guessing == 0) {
/* 1666:1904 */             checkForMissingEndRule(localToken9);
/* 1667:     */           }
/* 1668:     */         }
/* 1669:1907 */         else if (((LA(1) != 24) && (LA(1) != 41)) || (!_tokenSet_3.member(LA(2))))
/* 1670:     */         {
/* 1671:1910 */           throw new NoViableAltException(LT(1), getFilename());
/* 1672:     */         }
/* 1673:     */       }
/* 1674:1915 */       switch (LA(1))
/* 1675:     */       {
/* 1676:     */       case 41: 
/* 1677:1918 */         localToken1 = LT(1);
/* 1678:1919 */         match(41);
/* 1679:1921 */         switch (LA(1))
/* 1680:     */         {
/* 1681:     */         case 34: 
/* 1682:1924 */           localToken2 = LT(1);
/* 1683:1925 */           match(34);
/* 1684:1926 */           if (this.inputState.guessing == 0) {
/* 1685:1927 */             localToken11 = localToken2;
/* 1686:     */           }
/* 1687:     */           break;
/* 1688:     */         case 6: 
/* 1689:     */         case 7: 
/* 1690:     */         case 16: 
/* 1691:     */         case 19: 
/* 1692:     */         case 21: 
/* 1693:     */         case 24: 
/* 1694:     */         case 25: 
/* 1695:     */         case 27: 
/* 1696:     */         case 28: 
/* 1697:     */         case 33: 
/* 1698:     */         case 39: 
/* 1699:     */         case 41: 
/* 1700:     */         case 42: 
/* 1701:     */         case 43: 
/* 1702:     */         case 44: 
/* 1703:     */         case 50: 
/* 1704:     */           break;
/* 1705:     */         case 8: 
/* 1706:     */         case 9: 
/* 1707:     */         case 10: 
/* 1708:     */         case 11: 
/* 1709:     */         case 12: 
/* 1710:     */         case 13: 
/* 1711:     */         case 14: 
/* 1712:     */         case 15: 
/* 1713:     */         case 17: 
/* 1714:     */         case 18: 
/* 1715:     */         case 20: 
/* 1716:     */         case 22: 
/* 1717:     */         case 23: 
/* 1718:     */         case 26: 
/* 1719:     */         case 29: 
/* 1720:     */         case 30: 
/* 1721:     */         case 31: 
/* 1722:     */         case 32: 
/* 1723:     */         case 35: 
/* 1724:     */         case 36: 
/* 1725:     */         case 37: 
/* 1726:     */         case 38: 
/* 1727:     */         case 40: 
/* 1728:     */         case 45: 
/* 1729:     */         case 46: 
/* 1730:     */         case 47: 
/* 1731:     */         case 48: 
/* 1732:     */         case 49: 
/* 1733:     */         default: 
/* 1734:1952 */           throw new NoViableAltException(LT(1), getFilename());
/* 1735:     */         }
/* 1736:1957 */         switch (LA(1))
/* 1737:     */         {
/* 1738:     */         case 33: 
/* 1739:1960 */           match(33);
/* 1740:1961 */           if (this.inputState.guessing == 0) {
/* 1741:1962 */             i = 3;
/* 1742:     */           }
/* 1743:     */           break;
/* 1744:     */         case 6: 
/* 1745:     */         case 7: 
/* 1746:     */         case 16: 
/* 1747:     */         case 19: 
/* 1748:     */         case 21: 
/* 1749:     */         case 24: 
/* 1750:     */         case 25: 
/* 1751:     */         case 27: 
/* 1752:     */         case 28: 
/* 1753:     */         case 39: 
/* 1754:     */         case 41: 
/* 1755:     */         case 42: 
/* 1756:     */         case 43: 
/* 1757:     */         case 44: 
/* 1758:     */         case 50: 
/* 1759:     */           break;
/* 1760:     */         case 8: 
/* 1761:     */         case 9: 
/* 1762:     */         case 10: 
/* 1763:     */         case 11: 
/* 1764:     */         case 12: 
/* 1765:     */         case 13: 
/* 1766:     */         case 14: 
/* 1767:     */         case 15: 
/* 1768:     */         case 17: 
/* 1769:     */         case 18: 
/* 1770:     */         case 20: 
/* 1771:     */         case 22: 
/* 1772:     */         case 23: 
/* 1773:     */         case 26: 
/* 1774:     */         case 29: 
/* 1775:     */         case 30: 
/* 1776:     */         case 31: 
/* 1777:     */         case 32: 
/* 1778:     */         case 34: 
/* 1779:     */         case 35: 
/* 1780:     */         case 36: 
/* 1781:     */         case 37: 
/* 1782:     */         case 38: 
/* 1783:     */         case 40: 
/* 1784:     */         case 45: 
/* 1785:     */         case 46: 
/* 1786:     */         case 47: 
/* 1787:     */         case 48: 
/* 1788:     */         case 49: 
/* 1789:     */         default: 
/* 1790:1986 */           throw new NoViableAltException(LT(1), getFilename());
/* 1791:     */         }
/* 1792:1990 */         if (this.inputState.guessing == 0) {
/* 1793:1991 */           this.behavior.refRule(localToken10, localToken1, localToken9, localToken11, i);
/* 1794:     */         }
/* 1795:     */         break;
/* 1796:     */       case 24: 
/* 1797:1997 */         localToken3 = LT(1);
/* 1798:1998 */         match(24);
/* 1799:2000 */         switch (LA(1))
/* 1800:     */         {
/* 1801:     */         case 34: 
/* 1802:2003 */           localToken4 = LT(1);
/* 1803:2004 */           match(34);
/* 1804:2005 */           if (this.inputState.guessing == 0) {
/* 1805:2006 */             localToken11 = localToken4;
/* 1806:     */           }
/* 1807:     */           break;
/* 1808:     */         case 6: 
/* 1809:     */         case 7: 
/* 1810:     */         case 16: 
/* 1811:     */         case 19: 
/* 1812:     */         case 21: 
/* 1813:     */         case 24: 
/* 1814:     */         case 25: 
/* 1815:     */         case 27: 
/* 1816:     */         case 28: 
/* 1817:     */         case 39: 
/* 1818:     */         case 41: 
/* 1819:     */         case 42: 
/* 1820:     */         case 43: 
/* 1821:     */         case 44: 
/* 1822:     */         case 50: 
/* 1823:     */           break;
/* 1824:     */         case 8: 
/* 1825:     */         case 9: 
/* 1826:     */         case 10: 
/* 1827:     */         case 11: 
/* 1828:     */         case 12: 
/* 1829:     */         case 13: 
/* 1830:     */         case 14: 
/* 1831:     */         case 15: 
/* 1832:     */         case 17: 
/* 1833:     */         case 18: 
/* 1834:     */         case 20: 
/* 1835:     */         case 22: 
/* 1836:     */         case 23: 
/* 1837:     */         case 26: 
/* 1838:     */         case 29: 
/* 1839:     */         case 30: 
/* 1840:     */         case 31: 
/* 1841:     */         case 32: 
/* 1842:     */         case 33: 
/* 1843:     */         case 35: 
/* 1844:     */         case 36: 
/* 1845:     */         case 37: 
/* 1846:     */         case 38: 
/* 1847:     */         case 40: 
/* 1848:     */         case 45: 
/* 1849:     */         case 46: 
/* 1850:     */         case 47: 
/* 1851:     */         case 48: 
/* 1852:     */         case 49: 
/* 1853:     */         default: 
/* 1854:2030 */           throw new NoViableAltException(LT(1), getFilename());
/* 1855:     */         }
/* 1856:2034 */         if (this.inputState.guessing == 0) {
/* 1857:2035 */           this.behavior.refToken(localToken10, localToken3, localToken9, localToken11, false, i, lastInRule());
/* 1858:     */         }
/* 1859:     */         break;
/* 1860:     */       default: 
/* 1861:2041 */         throw new NoViableAltException(LT(1), getFilename());
/* 1862:2046 */         if ((_tokenSet_4.member(LA(1))) && (_tokenSet_5.member(LA(2)))) {
/* 1863:2048 */           if (((LA(1) == 24) || (LA(1) == 41)) && (LA(2) == 36))
/* 1864:     */           {
/* 1865:2049 */             localToken9 = id();
/* 1866:2050 */             match(36);
/* 1867:2051 */             if (this.inputState.guessing == 0) {
/* 1868:2052 */               checkForMissingEndRule(localToken9);
/* 1869:     */             }
/* 1870:     */           }
/* 1871:2055 */           else if ((!_tokenSet_4.member(LA(1))) || (!_tokenSet_6.member(LA(2))))
/* 1872:     */           {
/* 1873:2058 */             throw new NoViableAltException(LT(1), getFilename());
/* 1874:     */           }
/* 1875:     */         }
/* 1876:2063 */         switch (LA(1))
/* 1877:     */         {
/* 1878:     */         case 41: 
/* 1879:2066 */           localToken5 = LT(1);
/* 1880:2067 */           match(41);
/* 1881:2069 */           switch (LA(1))
/* 1882:     */           {
/* 1883:     */           case 34: 
/* 1884:2072 */             localToken6 = LT(1);
/* 1885:2073 */             match(34);
/* 1886:2074 */             if (this.inputState.guessing == 0) {
/* 1887:2075 */               localToken11 = localToken6;
/* 1888:     */             }
/* 1889:     */             break;
/* 1890:     */           case 6: 
/* 1891:     */           case 7: 
/* 1892:     */           case 16: 
/* 1893:     */           case 19: 
/* 1894:     */           case 21: 
/* 1895:     */           case 24: 
/* 1896:     */           case 25: 
/* 1897:     */           case 27: 
/* 1898:     */           case 28: 
/* 1899:     */           case 33: 
/* 1900:     */           case 39: 
/* 1901:     */           case 41: 
/* 1902:     */           case 42: 
/* 1903:     */           case 43: 
/* 1904:     */           case 44: 
/* 1905:     */           case 50: 
/* 1906:     */             break;
/* 1907:     */           case 8: 
/* 1908:     */           case 9: 
/* 1909:     */           case 10: 
/* 1910:     */           case 11: 
/* 1911:     */           case 12: 
/* 1912:     */           case 13: 
/* 1913:     */           case 14: 
/* 1914:     */           case 15: 
/* 1915:     */           case 17: 
/* 1916:     */           case 18: 
/* 1917:     */           case 20: 
/* 1918:     */           case 22: 
/* 1919:     */           case 23: 
/* 1920:     */           case 26: 
/* 1921:     */           case 29: 
/* 1922:     */           case 30: 
/* 1923:     */           case 31: 
/* 1924:     */           case 32: 
/* 1925:     */           case 35: 
/* 1926:     */           case 36: 
/* 1927:     */           case 37: 
/* 1928:     */           case 38: 
/* 1929:     */           case 40: 
/* 1930:     */           case 45: 
/* 1931:     */           case 46: 
/* 1932:     */           case 47: 
/* 1933:     */           case 48: 
/* 1934:     */           case 49: 
/* 1935:     */           default: 
/* 1936:2100 */             throw new NoViableAltException(LT(1), getFilename());
/* 1937:     */           }
/* 1938:2105 */           switch (LA(1))
/* 1939:     */           {
/* 1940:     */           case 33: 
/* 1941:2108 */             match(33);
/* 1942:2109 */             if (this.inputState.guessing == 0) {
/* 1943:2110 */               i = 3;
/* 1944:     */             }
/* 1945:     */             break;
/* 1946:     */           case 6: 
/* 1947:     */           case 7: 
/* 1948:     */           case 16: 
/* 1949:     */           case 19: 
/* 1950:     */           case 21: 
/* 1951:     */           case 24: 
/* 1952:     */           case 25: 
/* 1953:     */           case 27: 
/* 1954:     */           case 28: 
/* 1955:     */           case 39: 
/* 1956:     */           case 41: 
/* 1957:     */           case 42: 
/* 1958:     */           case 43: 
/* 1959:     */           case 44: 
/* 1960:     */           case 50: 
/* 1961:     */             break;
/* 1962:     */           case 8: 
/* 1963:     */           case 9: 
/* 1964:     */           case 10: 
/* 1965:     */           case 11: 
/* 1966:     */           case 12: 
/* 1967:     */           case 13: 
/* 1968:     */           case 14: 
/* 1969:     */           case 15: 
/* 1970:     */           case 17: 
/* 1971:     */           case 18: 
/* 1972:     */           case 20: 
/* 1973:     */           case 22: 
/* 1974:     */           case 23: 
/* 1975:     */           case 26: 
/* 1976:     */           case 29: 
/* 1977:     */           case 30: 
/* 1978:     */           case 31: 
/* 1979:     */           case 32: 
/* 1980:     */           case 34: 
/* 1981:     */           case 35: 
/* 1982:     */           case 36: 
/* 1983:     */           case 37: 
/* 1984:     */           case 38: 
/* 1985:     */           case 40: 
/* 1986:     */           case 45: 
/* 1987:     */           case 46: 
/* 1988:     */           case 47: 
/* 1989:     */           case 48: 
/* 1990:     */           case 49: 
/* 1991:     */           default: 
/* 1992:2134 */             throw new NoViableAltException(LT(1), getFilename());
/* 1993:     */           }
/* 1994:2138 */           if (this.inputState.guessing == 0) {
/* 1995:2139 */             this.behavior.refRule(localToken10, localToken5, localToken9, localToken11, i);
/* 1996:     */           }
/* 1997:     */           break;
/* 1998:     */         case 42: 
/* 1999:2145 */           match(42);
/* 2000:2147 */           switch (LA(1))
/* 2001:     */           {
/* 2002:     */           case 19: 
/* 2003:     */           case 24: 
/* 2004:2151 */             notTerminal(localToken9);
/* 2005:2152 */             break;
/* 2006:     */           case 27: 
/* 2007:2156 */             ebnf(localToken9, true);
/* 2008:2157 */             break;
/* 2009:     */           default: 
/* 2010:2161 */             throw new NoViableAltException(LT(1), getFilename());
/* 2011:     */           }
/* 2012:     */           break;
/* 2013:     */         case 27: 
/* 2014:2169 */           ebnf(localToken9, false);
/* 2015:2170 */           break;
/* 2016:     */         default: 
/* 2017:2173 */           if (((LA(1) == 6) || (LA(1) == 19) || (LA(1) == 24)) && (LA(2) == 22))
/* 2018:     */           {
/* 2019:2174 */             range(localToken9);
/* 2020:     */           }
/* 2021:2176 */           else if ((_tokenSet_7.member(LA(1))) && (_tokenSet_8.member(LA(2))))
/* 2022:     */           {
/* 2023:2177 */             terminal(localToken9);
/* 2024:     */           }
/* 2025:     */           else
/* 2026:     */           {
/* 2027:2180 */             throw new NoViableAltException(LT(1), getFilename());
/* 2028:     */             
/* 2029:     */ 
/* 2030:     */ 
/* 2031:     */ 
/* 2032:     */ 
/* 2033:2186 */             throw new NoViableAltException(LT(1), getFilename());
/* 2034:     */           }
/* 2035:     */           break;
/* 2036:     */         }
/* 2037:     */         break;
/* 2038:     */       }
/* 2039:     */       break;
/* 2040:     */     }
/* 2041:     */   }
/* 2042:     */   
/* 2043:     */   public final void elementOptionSpec()
/* 2044:     */     throws RecognitionException, TokenStreamException
/* 2045:     */   {
/* 2046:2194 */     Token localToken1 = null;Token localToken2 = null;
/* 2047:     */     
/* 2048:     */ 
/* 2049:2197 */     match(25);
/* 2050:2198 */     localToken1 = id();
/* 2051:2199 */     match(15);
/* 2052:2200 */     localToken2 = optionValue();
/* 2053:2201 */     if (this.inputState.guessing == 0) {
/* 2054:2202 */       this.behavior.refElementOption(localToken1, localToken2);
/* 2055:     */     }
/* 2056:2207 */     while (LA(1) == 16)
/* 2057:     */     {
/* 2058:2208 */       match(16);
/* 2059:2209 */       localToken1 = id();
/* 2060:2210 */       match(15);
/* 2061:2211 */       localToken2 = optionValue();
/* 2062:2212 */       if (this.inputState.guessing == 0) {
/* 2063:2213 */         this.behavior.refElementOption(localToken1, localToken2);
/* 2064:     */       }
/* 2065:     */     }
/* 2066:2222 */     match(26);
/* 2067:     */   }
/* 2068:     */   
/* 2069:     */   public final void range(Token paramToken)
/* 2070:     */     throws RecognitionException, TokenStreamException
/* 2071:     */   {
/* 2072:2229 */     Token localToken1 = null;
/* 2073:2230 */     Token localToken2 = null;
/* 2074:2231 */     Token localToken3 = null;
/* 2075:2232 */     Token localToken4 = null;
/* 2076:2233 */     Token localToken5 = null;
/* 2077:2234 */     Token localToken6 = null;
/* 2078:     */     
/* 2079:2236 */     Token localToken7 = null;
/* 2080:2237 */     Token localToken8 = null;
/* 2081:2238 */     int i = 1;
/* 2082:2241 */     switch (LA(1))
/* 2083:     */     {
/* 2084:     */     case 19: 
/* 2085:2244 */       localToken1 = LT(1);
/* 2086:2245 */       match(19);
/* 2087:2246 */       match(22);
/* 2088:2247 */       localToken2 = LT(1);
/* 2089:2248 */       match(19);
/* 2090:2250 */       switch (LA(1))
/* 2091:     */       {
/* 2092:     */       case 33: 
/* 2093:2253 */         match(33);
/* 2094:2254 */         if (this.inputState.guessing == 0) {
/* 2095:2255 */           i = 3;
/* 2096:     */         }
/* 2097:     */         break;
/* 2098:     */       case 6: 
/* 2099:     */       case 7: 
/* 2100:     */       case 16: 
/* 2101:     */       case 19: 
/* 2102:     */       case 21: 
/* 2103:     */       case 24: 
/* 2104:     */       case 25: 
/* 2105:     */       case 27: 
/* 2106:     */       case 28: 
/* 2107:     */       case 39: 
/* 2108:     */       case 41: 
/* 2109:     */       case 42: 
/* 2110:     */       case 43: 
/* 2111:     */       case 44: 
/* 2112:     */       case 50: 
/* 2113:     */         break;
/* 2114:     */       case 8: 
/* 2115:     */       case 9: 
/* 2116:     */       case 10: 
/* 2117:     */       case 11: 
/* 2118:     */       case 12: 
/* 2119:     */       case 13: 
/* 2120:     */       case 14: 
/* 2121:     */       case 15: 
/* 2122:     */       case 17: 
/* 2123:     */       case 18: 
/* 2124:     */       case 20: 
/* 2125:     */       case 22: 
/* 2126:     */       case 23: 
/* 2127:     */       case 26: 
/* 2128:     */       case 29: 
/* 2129:     */       case 30: 
/* 2130:     */       case 31: 
/* 2131:     */       case 32: 
/* 2132:     */       case 34: 
/* 2133:     */       case 35: 
/* 2134:     */       case 36: 
/* 2135:     */       case 37: 
/* 2136:     */       case 38: 
/* 2137:     */       case 40: 
/* 2138:     */       case 45: 
/* 2139:     */       case 46: 
/* 2140:     */       case 47: 
/* 2141:     */       case 48: 
/* 2142:     */       case 49: 
/* 2143:     */       default: 
/* 2144:2279 */         throw new NoViableAltException(LT(1), getFilename());
/* 2145:     */       }
/* 2146:2283 */       if (this.inputState.guessing == 0) {
/* 2147:2284 */         this.behavior.refCharRange(localToken1, localToken2, paramToken, i, lastInRule());
/* 2148:     */       }
/* 2149:     */       break;
/* 2150:     */     case 6: 
/* 2151:     */     case 24: 
/* 2152:2292 */       switch (LA(1))
/* 2153:     */       {
/* 2154:     */       case 24: 
/* 2155:2295 */         localToken3 = LT(1);
/* 2156:2296 */         match(24);
/* 2157:2297 */         if (this.inputState.guessing == 0) {
/* 2158:2298 */           localToken7 = localToken3;
/* 2159:     */         }
/* 2160:     */         break;
/* 2161:     */       case 6: 
/* 2162:2304 */         localToken4 = LT(1);
/* 2163:2305 */         match(6);
/* 2164:2306 */         if (this.inputState.guessing == 0) {
/* 2165:2307 */           localToken7 = localToken4;
/* 2166:     */         }
/* 2167:     */         break;
/* 2168:     */       default: 
/* 2169:2313 */         throw new NoViableAltException(LT(1), getFilename());
/* 2170:     */       }
/* 2171:2317 */       match(22);
/* 2172:2319 */       switch (LA(1))
/* 2173:     */       {
/* 2174:     */       case 24: 
/* 2175:2322 */         localToken5 = LT(1);
/* 2176:2323 */         match(24);
/* 2177:2324 */         if (this.inputState.guessing == 0) {
/* 2178:2325 */           localToken8 = localToken5;
/* 2179:     */         }
/* 2180:     */         break;
/* 2181:     */       case 6: 
/* 2182:2331 */         localToken6 = LT(1);
/* 2183:2332 */         match(6);
/* 2184:2333 */         if (this.inputState.guessing == 0) {
/* 2185:2334 */           localToken8 = localToken6;
/* 2186:     */         }
/* 2187:     */         break;
/* 2188:     */       default: 
/* 2189:2340 */         throw new NoViableAltException(LT(1), getFilename());
/* 2190:     */       }
/* 2191:2344 */       i = ast_type_spec();
/* 2192:2345 */       if (this.inputState.guessing == 0) {
/* 2193:2346 */         this.behavior.refTokenRange(localToken7, localToken8, paramToken, i, lastInRule());
/* 2194:     */       }
/* 2195:     */       break;
/* 2196:     */     default: 
/* 2197:2352 */       throw new NoViableAltException(LT(1), getFilename());
/* 2198:     */     }
/* 2199:     */   }
/* 2200:     */   
/* 2201:     */   public final void terminal(Token paramToken)
/* 2202:     */     throws RecognitionException, TokenStreamException
/* 2203:     */   {
/* 2204:2361 */     Token localToken1 = null;
/* 2205:2362 */     Token localToken2 = null;
/* 2206:2363 */     Token localToken3 = null;
/* 2207:2364 */     Token localToken4 = null;
/* 2208:2365 */     Token localToken5 = null;
/* 2209:     */     
/* 2210:2367 */     int i = 1;
/* 2211:2368 */     Token localToken6 = null;
/* 2212:2371 */     switch (LA(1))
/* 2213:     */     {
/* 2214:     */     case 19: 
/* 2215:2374 */       localToken1 = LT(1);
/* 2216:2375 */       match(19);
/* 2217:2377 */       switch (LA(1))
/* 2218:     */       {
/* 2219:     */       case 33: 
/* 2220:2380 */         match(33);
/* 2221:2381 */         if (this.inputState.guessing == 0) {
/* 2222:2382 */           i = 3;
/* 2223:     */         }
/* 2224:     */         break;
/* 2225:     */       case 6: 
/* 2226:     */       case 7: 
/* 2227:     */       case 16: 
/* 2228:     */       case 19: 
/* 2229:     */       case 21: 
/* 2230:     */       case 24: 
/* 2231:     */       case 25: 
/* 2232:     */       case 27: 
/* 2233:     */       case 28: 
/* 2234:     */       case 39: 
/* 2235:     */       case 41: 
/* 2236:     */       case 42: 
/* 2237:     */       case 43: 
/* 2238:     */       case 44: 
/* 2239:     */       case 50: 
/* 2240:     */         break;
/* 2241:     */       case 8: 
/* 2242:     */       case 9: 
/* 2243:     */       case 10: 
/* 2244:     */       case 11: 
/* 2245:     */       case 12: 
/* 2246:     */       case 13: 
/* 2247:     */       case 14: 
/* 2248:     */       case 15: 
/* 2249:     */       case 17: 
/* 2250:     */       case 18: 
/* 2251:     */       case 20: 
/* 2252:     */       case 22: 
/* 2253:     */       case 23: 
/* 2254:     */       case 26: 
/* 2255:     */       case 29: 
/* 2256:     */       case 30: 
/* 2257:     */       case 31: 
/* 2258:     */       case 32: 
/* 2259:     */       case 34: 
/* 2260:     */       case 35: 
/* 2261:     */       case 36: 
/* 2262:     */       case 37: 
/* 2263:     */       case 38: 
/* 2264:     */       case 40: 
/* 2265:     */       case 45: 
/* 2266:     */       case 46: 
/* 2267:     */       case 47: 
/* 2268:     */       case 48: 
/* 2269:     */       case 49: 
/* 2270:     */       default: 
/* 2271:2406 */         throw new NoViableAltException(LT(1), getFilename());
/* 2272:     */       }
/* 2273:2410 */       if (this.inputState.guessing == 0) {
/* 2274:2411 */         this.behavior.refCharLiteral(localToken1, paramToken, false, i, lastInRule());
/* 2275:     */       }
/* 2276:     */       break;
/* 2277:     */     case 24: 
/* 2278:2417 */       localToken2 = LT(1);
/* 2279:2418 */       match(24);
/* 2280:2419 */       i = ast_type_spec();
/* 2281:2421 */       switch (LA(1))
/* 2282:     */       {
/* 2283:     */       case 34: 
/* 2284:2424 */         localToken3 = LT(1);
/* 2285:2425 */         match(34);
/* 2286:2426 */         if (this.inputState.guessing == 0) {
/* 2287:2427 */           localToken6 = localToken3;
/* 2288:     */         }
/* 2289:     */         break;
/* 2290:     */       case 6: 
/* 2291:     */       case 7: 
/* 2292:     */       case 16: 
/* 2293:     */       case 19: 
/* 2294:     */       case 21: 
/* 2295:     */       case 24: 
/* 2296:     */       case 25: 
/* 2297:     */       case 27: 
/* 2298:     */       case 28: 
/* 2299:     */       case 39: 
/* 2300:     */       case 41: 
/* 2301:     */       case 42: 
/* 2302:     */       case 43: 
/* 2303:     */       case 44: 
/* 2304:     */       case 50: 
/* 2305:     */         break;
/* 2306:     */       case 8: 
/* 2307:     */       case 9: 
/* 2308:     */       case 10: 
/* 2309:     */       case 11: 
/* 2310:     */       case 12: 
/* 2311:     */       case 13: 
/* 2312:     */       case 14: 
/* 2313:     */       case 15: 
/* 2314:     */       case 17: 
/* 2315:     */       case 18: 
/* 2316:     */       case 20: 
/* 2317:     */       case 22: 
/* 2318:     */       case 23: 
/* 2319:     */       case 26: 
/* 2320:     */       case 29: 
/* 2321:     */       case 30: 
/* 2322:     */       case 31: 
/* 2323:     */       case 32: 
/* 2324:     */       case 33: 
/* 2325:     */       case 35: 
/* 2326:     */       case 36: 
/* 2327:     */       case 37: 
/* 2328:     */       case 38: 
/* 2329:     */       case 40: 
/* 2330:     */       case 45: 
/* 2331:     */       case 46: 
/* 2332:     */       case 47: 
/* 2333:     */       case 48: 
/* 2334:     */       case 49: 
/* 2335:     */       default: 
/* 2336:2451 */         throw new NoViableAltException(LT(1), getFilename());
/* 2337:     */       }
/* 2338:2455 */       if (this.inputState.guessing == 0) {
/* 2339:2456 */         this.behavior.refToken(null, localToken2, paramToken, localToken6, false, i, lastInRule());
/* 2340:     */       }
/* 2341:     */       break;
/* 2342:     */     case 6: 
/* 2343:2462 */       localToken4 = LT(1);
/* 2344:2463 */       match(6);
/* 2345:2464 */       i = ast_type_spec();
/* 2346:2465 */       if (this.inputState.guessing == 0) {
/* 2347:2466 */         this.behavior.refStringLiteral(localToken4, paramToken, i, lastInRule());
/* 2348:     */       }
/* 2349:     */       break;
/* 2350:     */     case 50: 
/* 2351:2472 */       localToken5 = LT(1);
/* 2352:2473 */       match(50);
/* 2353:2474 */       i = ast_type_spec();
/* 2354:2475 */       if (this.inputState.guessing == 0) {
/* 2355:2476 */         this.behavior.refWildcard(localToken5, paramToken, i);
/* 2356:     */       }
/* 2357:     */       break;
/* 2358:     */     default: 
/* 2359:2482 */       throw new NoViableAltException(LT(1), getFilename());
/* 2360:     */     }
/* 2361:     */   }
/* 2362:     */   
/* 2363:     */   public final void notTerminal(Token paramToken)
/* 2364:     */     throws RecognitionException, TokenStreamException
/* 2365:     */   {
/* 2366:2491 */     Token localToken1 = null;
/* 2367:2492 */     Token localToken2 = null;
/* 2368:2493 */     int i = 1;
/* 2369:2495 */     switch (LA(1))
/* 2370:     */     {
/* 2371:     */     case 19: 
/* 2372:2498 */       localToken1 = LT(1);
/* 2373:2499 */       match(19);
/* 2374:2501 */       switch (LA(1))
/* 2375:     */       {
/* 2376:     */       case 33: 
/* 2377:2504 */         match(33);
/* 2378:2505 */         if (this.inputState.guessing == 0) {
/* 2379:2506 */           i = 3;
/* 2380:     */         }
/* 2381:     */         break;
/* 2382:     */       case 6: 
/* 2383:     */       case 7: 
/* 2384:     */       case 16: 
/* 2385:     */       case 19: 
/* 2386:     */       case 21: 
/* 2387:     */       case 24: 
/* 2388:     */       case 25: 
/* 2389:     */       case 27: 
/* 2390:     */       case 28: 
/* 2391:     */       case 39: 
/* 2392:     */       case 41: 
/* 2393:     */       case 42: 
/* 2394:     */       case 43: 
/* 2395:     */       case 44: 
/* 2396:     */       case 50: 
/* 2397:     */         break;
/* 2398:     */       case 8: 
/* 2399:     */       case 9: 
/* 2400:     */       case 10: 
/* 2401:     */       case 11: 
/* 2402:     */       case 12: 
/* 2403:     */       case 13: 
/* 2404:     */       case 14: 
/* 2405:     */       case 15: 
/* 2406:     */       case 17: 
/* 2407:     */       case 18: 
/* 2408:     */       case 20: 
/* 2409:     */       case 22: 
/* 2410:     */       case 23: 
/* 2411:     */       case 26: 
/* 2412:     */       case 29: 
/* 2413:     */       case 30: 
/* 2414:     */       case 31: 
/* 2415:     */       case 32: 
/* 2416:     */       case 34: 
/* 2417:     */       case 35: 
/* 2418:     */       case 36: 
/* 2419:     */       case 37: 
/* 2420:     */       case 38: 
/* 2421:     */       case 40: 
/* 2422:     */       case 45: 
/* 2423:     */       case 46: 
/* 2424:     */       case 47: 
/* 2425:     */       case 48: 
/* 2426:     */       case 49: 
/* 2427:     */       default: 
/* 2428:2530 */         throw new NoViableAltException(LT(1), getFilename());
/* 2429:     */       }
/* 2430:2534 */       if (this.inputState.guessing == 0) {
/* 2431:2535 */         this.behavior.refCharLiteral(localToken1, paramToken, true, i, lastInRule());
/* 2432:     */       }
/* 2433:     */       break;
/* 2434:     */     case 24: 
/* 2435:2541 */       localToken2 = LT(1);
/* 2436:2542 */       match(24);
/* 2437:2543 */       i = ast_type_spec();
/* 2438:2544 */       if (this.inputState.guessing == 0) {
/* 2439:2545 */         this.behavior.refToken(null, localToken2, paramToken, null, true, i, lastInRule());
/* 2440:     */       }
/* 2441:     */       break;
/* 2442:     */     default: 
/* 2443:2551 */       throw new NoViableAltException(LT(1), getFilename());
/* 2444:     */     }
/* 2445:     */   }
/* 2446:     */   
/* 2447:     */   public final void ebnf(Token paramToken, boolean paramBoolean)
/* 2448:     */     throws RecognitionException, TokenStreamException
/* 2449:     */   {
/* 2450:2560 */     Token localToken1 = null;
/* 2451:2561 */     Token localToken2 = null;
/* 2452:2562 */     Token localToken3 = null;
/* 2453:     */     
/* 2454:2564 */     localToken1 = LT(1);
/* 2455:2565 */     match(27);
/* 2456:2566 */     if (this.inputState.guessing == 0) {
/* 2457:2567 */       this.behavior.beginSubRule(paramToken, localToken1, paramBoolean);
/* 2458:     */     }
/* 2459:2570 */     if (LA(1) == 14)
/* 2460:     */     {
/* 2461:2571 */       subruleOptionsSpec();
/* 2462:2573 */       switch (LA(1))
/* 2463:     */       {
/* 2464:     */       case 7: 
/* 2465:2576 */         localToken2 = LT(1);
/* 2466:2577 */         match(7);
/* 2467:2578 */         if (this.inputState.guessing == 0) {
/* 2468:2579 */           this.behavior.refInitAction(localToken2);
/* 2469:     */         }
/* 2470:     */         break;
/* 2471:     */       case 36: 
/* 2472:     */         break;
/* 2473:     */       default: 
/* 2474:2589 */         throw new NoViableAltException(LT(1), getFilename());
/* 2475:     */       }
/* 2476:2593 */       match(36);
/* 2477:     */     }
/* 2478:2595 */     else if ((LA(1) == 7) && (LA(2) == 36))
/* 2479:     */     {
/* 2480:2596 */       localToken3 = LT(1);
/* 2481:2597 */       match(7);
/* 2482:2598 */       if (this.inputState.guessing == 0) {
/* 2483:2599 */         this.behavior.refInitAction(localToken3);
/* 2484:     */       }
/* 2485:2601 */       match(36);
/* 2486:     */     }
/* 2487:2603 */     else if ((!_tokenSet_9.member(LA(1))) || (!_tokenSet_10.member(LA(2))))
/* 2488:     */     {
/* 2489:2606 */       throw new NoViableAltException(LT(1), getFilename());
/* 2490:     */     }
/* 2491:2610 */     block();
/* 2492:2611 */     match(28);
/* 2493:2613 */     switch (LA(1))
/* 2494:     */     {
/* 2495:     */     case 6: 
/* 2496:     */     case 7: 
/* 2497:     */     case 16: 
/* 2498:     */     case 19: 
/* 2499:     */     case 21: 
/* 2500:     */     case 24: 
/* 2501:     */     case 25: 
/* 2502:     */     case 27: 
/* 2503:     */     case 28: 
/* 2504:     */     case 33: 
/* 2505:     */     case 39: 
/* 2506:     */     case 41: 
/* 2507:     */     case 42: 
/* 2508:     */     case 43: 
/* 2509:     */     case 44: 
/* 2510:     */     case 45: 
/* 2511:     */     case 46: 
/* 2512:     */     case 47: 
/* 2513:     */     case 50: 
/* 2514:2635 */       switch (LA(1))
/* 2515:     */       {
/* 2516:     */       case 45: 
/* 2517:2638 */         match(45);
/* 2518:2639 */         if (this.inputState.guessing == 0) {
/* 2519:2640 */           this.behavior.optionalSubRule();
/* 2520:     */         }
/* 2521:     */         break;
/* 2522:     */       case 46: 
/* 2523:2646 */         match(46);
/* 2524:2647 */         if (this.inputState.guessing == 0) {
/* 2525:2648 */           this.behavior.zeroOrMoreSubRule();
/* 2526:     */         }
/* 2527:     */         break;
/* 2528:     */       case 47: 
/* 2529:2654 */         match(47);
/* 2530:2655 */         if (this.inputState.guessing == 0) {
/* 2531:2656 */           this.behavior.oneOrMoreSubRule();
/* 2532:     */         }
/* 2533:     */         break;
/* 2534:     */       case 6: 
/* 2535:     */       case 7: 
/* 2536:     */       case 16: 
/* 2537:     */       case 19: 
/* 2538:     */       case 21: 
/* 2539:     */       case 24: 
/* 2540:     */       case 25: 
/* 2541:     */       case 27: 
/* 2542:     */       case 28: 
/* 2543:     */       case 33: 
/* 2544:     */       case 39: 
/* 2545:     */       case 41: 
/* 2546:     */       case 42: 
/* 2547:     */       case 43: 
/* 2548:     */       case 44: 
/* 2549:     */       case 50: 
/* 2550:     */         break;
/* 2551:     */       case 8: 
/* 2552:     */       case 9: 
/* 2553:     */       case 10: 
/* 2554:     */       case 11: 
/* 2555:     */       case 12: 
/* 2556:     */       case 13: 
/* 2557:     */       case 14: 
/* 2558:     */       case 15: 
/* 2559:     */       case 17: 
/* 2560:     */       case 18: 
/* 2561:     */       case 20: 
/* 2562:     */       case 22: 
/* 2563:     */       case 23: 
/* 2564:     */       case 26: 
/* 2565:     */       case 29: 
/* 2566:     */       case 30: 
/* 2567:     */       case 31: 
/* 2568:     */       case 32: 
/* 2569:     */       case 34: 
/* 2570:     */       case 35: 
/* 2571:     */       case 36: 
/* 2572:     */       case 37: 
/* 2573:     */       case 38: 
/* 2574:     */       case 40: 
/* 2575:     */       case 48: 
/* 2576:     */       case 49: 
/* 2577:     */       default: 
/* 2578:2681 */         throw new NoViableAltException(LT(1), getFilename());
/* 2579:     */       }
/* 2580:2686 */       switch (LA(1))
/* 2581:     */       {
/* 2582:     */       case 33: 
/* 2583:2689 */         match(33);
/* 2584:2690 */         if (this.inputState.guessing == 0) {
/* 2585:2691 */           this.behavior.noASTSubRule();
/* 2586:     */         }
/* 2587:     */         break;
/* 2588:     */       case 6: 
/* 2589:     */       case 7: 
/* 2590:     */       case 16: 
/* 2591:     */       case 19: 
/* 2592:     */       case 21: 
/* 2593:     */       case 24: 
/* 2594:     */       case 25: 
/* 2595:     */       case 27: 
/* 2596:     */       case 28: 
/* 2597:     */       case 39: 
/* 2598:     */       case 41: 
/* 2599:     */       case 42: 
/* 2600:     */       case 43: 
/* 2601:     */       case 44: 
/* 2602:     */       case 50: 
/* 2603:     */         break;
/* 2604:     */       case 8: 
/* 2605:     */       case 9: 
/* 2606:     */       case 10: 
/* 2607:     */       case 11: 
/* 2608:     */       case 12: 
/* 2609:     */       case 13: 
/* 2610:     */       case 14: 
/* 2611:     */       case 15: 
/* 2612:     */       case 17: 
/* 2613:     */       case 18: 
/* 2614:     */       case 20: 
/* 2615:     */       case 22: 
/* 2616:     */       case 23: 
/* 2617:     */       case 26: 
/* 2618:     */       case 29: 
/* 2619:     */       case 30: 
/* 2620:     */       case 31: 
/* 2621:     */       case 32: 
/* 2622:     */       case 34: 
/* 2623:     */       case 35: 
/* 2624:     */       case 36: 
/* 2625:     */       case 37: 
/* 2626:     */       case 38: 
/* 2627:     */       case 40: 
/* 2628:     */       case 45: 
/* 2629:     */       case 46: 
/* 2630:     */       case 47: 
/* 2631:     */       case 48: 
/* 2632:     */       case 49: 
/* 2633:     */       default: 
/* 2634:2715 */         throw new NoViableAltException(LT(1), getFilename());
/* 2635:     */       }
/* 2636:     */       break;
/* 2637:     */     case 48: 
/* 2638:2723 */       match(48);
/* 2639:2724 */       if (this.inputState.guessing == 0) {
/* 2640:2725 */         this.behavior.synPred();
/* 2641:     */       }
/* 2642:     */       break;
/* 2643:     */     case 8: 
/* 2644:     */     case 9: 
/* 2645:     */     case 10: 
/* 2646:     */     case 11: 
/* 2647:     */     case 12: 
/* 2648:     */     case 13: 
/* 2649:     */     case 14: 
/* 2650:     */     case 15: 
/* 2651:     */     case 17: 
/* 2652:     */     case 18: 
/* 2653:     */     case 20: 
/* 2654:     */     case 22: 
/* 2655:     */     case 23: 
/* 2656:     */     case 26: 
/* 2657:     */     case 29: 
/* 2658:     */     case 30: 
/* 2659:     */     case 31: 
/* 2660:     */     case 32: 
/* 2661:     */     case 34: 
/* 2662:     */     case 35: 
/* 2663:     */     case 36: 
/* 2664:     */     case 37: 
/* 2665:     */     case 38: 
/* 2666:     */     case 40: 
/* 2667:     */     case 49: 
/* 2668:     */     default: 
/* 2669:2731 */       throw new NoViableAltException(LT(1), getFilename());
/* 2670:     */     }
/* 2671:2735 */     if (this.inputState.guessing == 0) {
/* 2672:2736 */       this.behavior.endSubRule();
/* 2673:     */     }
/* 2674:     */   }
/* 2675:     */   
/* 2676:     */   public final void tree()
/* 2677:     */     throws RecognitionException, TokenStreamException
/* 2678:     */   {
/* 2679:2742 */     Token localToken = null;
/* 2680:     */     
/* 2681:2744 */     localToken = LT(1);
/* 2682:2745 */     match(44);
/* 2683:2746 */     if (this.inputState.guessing == 0) {
/* 2684:2747 */       this.behavior.beginTree(localToken);
/* 2685:     */     }
/* 2686:2749 */     rootNode();
/* 2687:2750 */     if (this.inputState.guessing == 0) {
/* 2688:2751 */       this.behavior.beginChildList();
/* 2689:     */     }
/* 2690:2754 */     int i = 0;
/* 2691:     */     for (;;)
/* 2692:     */     {
/* 2693:2757 */       if (_tokenSet_2.member(LA(1)))
/* 2694:     */       {
/* 2695:2758 */         element();
/* 2696:     */       }
/* 2697:     */       else
/* 2698:     */       {
/* 2699:2761 */         if (i >= 1) {
/* 2700:     */           break;
/* 2701:     */         }
/* 2702:2761 */         throw new NoViableAltException(LT(1), getFilename());
/* 2703:     */       }
/* 2704:2764 */       i++;
/* 2705:     */     }
/* 2706:2767 */     if (this.inputState.guessing == 0) {
/* 2707:2768 */       this.behavior.endChildList();
/* 2708:     */     }
/* 2709:2770 */     match(28);
/* 2710:2771 */     if (this.inputState.guessing == 0) {
/* 2711:2772 */       this.behavior.endTree();
/* 2712:     */     }
/* 2713:     */   }
/* 2714:     */   
/* 2715:     */   public final void rootNode()
/* 2716:     */     throws RecognitionException, TokenStreamException
/* 2717:     */   {
/* 2718:2778 */     Token localToken = null;
/* 2719:2781 */     if (((LA(1) == 24) || (LA(1) == 41)) && (LA(2) == 36))
/* 2720:     */     {
/* 2721:2782 */       localToken = id();
/* 2722:2783 */       match(36);
/* 2723:2784 */       if (this.inputState.guessing == 0) {
/* 2724:2785 */         checkForMissingEndRule(localToken);
/* 2725:     */       }
/* 2726:     */     }
/* 2727:2788 */     else if ((!_tokenSet_7.member(LA(1))) || (!_tokenSet_11.member(LA(2))))
/* 2728:     */     {
/* 2729:2791 */       throw new NoViableAltException(LT(1), getFilename());
/* 2730:     */     }
/* 2731:2795 */     terminal(localToken);
/* 2732:     */   }
/* 2733:     */   
/* 2734:     */   public final int ast_type_spec()
/* 2735:     */     throws RecognitionException, TokenStreamException
/* 2736:     */   {
/* 2737:2801 */     int i = 1;
/* 2738:2804 */     switch (LA(1))
/* 2739:     */     {
/* 2740:     */     case 49: 
/* 2741:2807 */       match(49);
/* 2742:2808 */       if (this.inputState.guessing == 0) {
/* 2743:2809 */         i = 2;
/* 2744:     */       }
/* 2745:     */       break;
/* 2746:     */     case 33: 
/* 2747:2815 */       match(33);
/* 2748:2816 */       if (this.inputState.guessing == 0) {
/* 2749:2817 */         i = 3;
/* 2750:     */       }
/* 2751:     */       break;
/* 2752:     */     case 6: 
/* 2753:     */     case 7: 
/* 2754:     */     case 16: 
/* 2755:     */     case 19: 
/* 2756:     */     case 21: 
/* 2757:     */     case 24: 
/* 2758:     */     case 25: 
/* 2759:     */     case 27: 
/* 2760:     */     case 28: 
/* 2761:     */     case 34: 
/* 2762:     */     case 39: 
/* 2763:     */     case 41: 
/* 2764:     */     case 42: 
/* 2765:     */     case 43: 
/* 2766:     */     case 44: 
/* 2767:     */     case 50: 
/* 2768:     */       break;
/* 2769:     */     case 8: 
/* 2770:     */     case 9: 
/* 2771:     */     case 10: 
/* 2772:     */     case 11: 
/* 2773:     */     case 12: 
/* 2774:     */     case 13: 
/* 2775:     */     case 14: 
/* 2776:     */     case 15: 
/* 2777:     */     case 17: 
/* 2778:     */     case 18: 
/* 2779:     */     case 20: 
/* 2780:     */     case 22: 
/* 2781:     */     case 23: 
/* 2782:     */     case 26: 
/* 2783:     */     case 29: 
/* 2784:     */     case 30: 
/* 2785:     */     case 31: 
/* 2786:     */     case 32: 
/* 2787:     */     case 35: 
/* 2788:     */     case 36: 
/* 2789:     */     case 37: 
/* 2790:     */     case 38: 
/* 2791:     */     case 40: 
/* 2792:     */     case 45: 
/* 2793:     */     case 46: 
/* 2794:     */     case 47: 
/* 2795:     */     case 48: 
/* 2796:     */     default: 
/* 2797:2842 */       throw new NoViableAltException(LT(1), getFilename());
/* 2798:     */     }
/* 2799:2846 */     return i;
/* 2800:     */   }
/* 2801:     */   
/* 2802:2850 */   public static final String[] _tokenNames = { "<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "\"tokens\"", "\"header\"", "STRING_LITERAL", "ACTION", "DOC_COMMENT", "\"lexclass\"", "\"class\"", "\"extends\"", "\"Lexer\"", "\"TreeParser\"", "OPTIONS", "ASSIGN", "SEMI", "RCURLY", "\"charVocabulary\"", "CHAR_LITERAL", "INT", "OR", "RANGE", "TOKENS", "TOKEN_REF", "OPEN_ELEMENT_OPTION", "CLOSE_ELEMENT_OPTION", "LPAREN", "RPAREN", "\"Parser\"", "\"protected\"", "\"public\"", "\"private\"", "BANG", "ARG_ACTION", "\"returns\"", "COLON", "\"throws\"", "COMMA", "\"exception\"", "\"catch\"", "RULE_REF", "NOT_OP", "SEMPRED", "TREE_BEGIN", "QUESTION", "STAR", "PLUS", "IMPLIES", "CARET", "WILDCARD", "\"options\"", "WS", "COMMENT", "SL_COMMENT", "ML_COMMENT", "ESC", "DIGIT", "XDIGIT", "NESTED_ARG_ACTION", "NESTED_ACTION", "WS_LOOP", "INTERNAL_RULE_REF", "WS_OPT" };
/* 2803:     */   
/* 2804:     */   private static final long[] mk_tokenSet_0()
/* 2805:     */   {
/* 2806:2918 */     long[] arrayOfLong = { 2206556225792L, 0L };
/* 2807:2919 */     return arrayOfLong;
/* 2808:     */   }
/* 2809:     */   
/* 2810:2921 */   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
/* 2811:     */   
/* 2812:     */   private static final long[] mk_tokenSet_1()
/* 2813:     */   {
/* 2814:2923 */     long[] arrayOfLong = { 2472844214400L, 0L };
/* 2815:2924 */     return arrayOfLong;
/* 2816:     */   }
/* 2817:     */   
/* 2818:2926 */   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
/* 2819:     */   
/* 2820:     */   private static final long[] mk_tokenSet_2()
/* 2821:     */   {
/* 2822:2928 */     long[] arrayOfLong = { 1158885407195328L, 0L };
/* 2823:2929 */     return arrayOfLong;
/* 2824:     */   }
/* 2825:     */   
/* 2826:2931 */   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
/* 2827:     */   
/* 2828:     */   private static final long[] mk_tokenSet_3()
/* 2829:     */   {
/* 2830:2933 */     long[] arrayOfLong = { 1159461236965568L, 0L };
/* 2831:2934 */     return arrayOfLong;
/* 2832:     */   }
/* 2833:     */   
/* 2834:2936 */   public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
/* 2835:     */   
/* 2836:     */   private static final long[] mk_tokenSet_4()
/* 2837:     */   {
/* 2838:2938 */     long[] arrayOfLong = { 1132497128128576L, 0L };
/* 2839:2939 */     return arrayOfLong;
/* 2840:     */   }
/* 2841:     */   
/* 2842:2941 */   public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
/* 2843:     */   
/* 2844:     */   private static final long[] mk_tokenSet_5()
/* 2845:     */   {
/* 2846:2943 */     long[] arrayOfLong = { 1722479914074304L, 0L };
/* 2847:2944 */     return arrayOfLong;
/* 2848:     */   }
/* 2849:     */   
/* 2850:2946 */   public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
/* 2851:     */   
/* 2852:     */   private static final long[] mk_tokenSet_6()
/* 2853:     */   {
/* 2854:2948 */     long[] arrayOfLong = { 1722411194597568L, 0L };
/* 2855:2949 */     return arrayOfLong;
/* 2856:     */   }
/* 2857:     */   
/* 2858:2951 */   public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
/* 2859:     */   
/* 2860:     */   private static final long[] mk_tokenSet_7()
/* 2861:     */   {
/* 2862:2953 */     long[] arrayOfLong = { 1125899924144192L, 0L };
/* 2863:2954 */     return arrayOfLong;
/* 2864:     */   }
/* 2865:     */   
/* 2866:2956 */   public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
/* 2867:     */   
/* 2868:     */   private static final long[] mk_tokenSet_8()
/* 2869:     */   {
/* 2870:2958 */     long[] arrayOfLong = { 1722411190386880L, 0L };
/* 2871:2959 */     return arrayOfLong;
/* 2872:     */   }
/* 2873:     */   
/* 2874:2961 */   public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
/* 2875:     */   
/* 2876:     */   private static final long[] mk_tokenSet_9()
/* 2877:     */   {
/* 2878:2963 */     long[] arrayOfLong = { 1159444023476416L, 0L };
/* 2879:2964 */     return arrayOfLong;
/* 2880:     */   }
/* 2881:     */   
/* 2882:2966 */   public static final BitSet _tokenSet_9 = new BitSet(mk_tokenSet_9());
/* 2883:     */   
/* 2884:     */   private static final long[] mk_tokenSet_10()
/* 2885:     */   {
/* 2886:2968 */     long[] arrayOfLong = { 2251345007067328L, 0L };
/* 2887:2969 */     return arrayOfLong;
/* 2888:     */   }
/* 2889:     */   
/* 2890:2971 */   public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());
/* 2891:     */   
/* 2892:     */   private static final long[] mk_tokenSet_11()
/* 2893:     */   {
/* 2894:2973 */     long[] arrayOfLong = { 1721861130420416L, 0L };
/* 2895:2974 */     return arrayOfLong;
/* 2896:     */   }
/* 2897:     */   
/* 2898:2976 */   public static final BitSet _tokenSet_11 = new BitSet(mk_tokenSet_11());
/* 2899:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.ANTLRParser
 * JD-Core Version:    0.7.0.1
 */