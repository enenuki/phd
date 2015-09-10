/*   1:    */ package antlr.preprocessor;
/*   2:    */ 
/*   3:    */ import antlr.LLkParser;
/*   4:    */ import antlr.NoViableAltException;
/*   5:    */ import antlr.ParserSharedInputState;
/*   6:    */ import antlr.RecognitionException;
/*   7:    */ import antlr.SemanticException;
/*   8:    */ import antlr.Token;
/*   9:    */ import antlr.TokenBuffer;
/*  10:    */ import antlr.TokenStream;
/*  11:    */ import antlr.TokenStreamException;
/*  12:    */ import antlr.Tool;
/*  13:    */ import antlr.collections.impl.BitSet;
/*  14:    */ import antlr.collections.impl.IndexedVector;
/*  15:    */ 
/*  16:    */ public class Preprocessor
/*  17:    */   extends LLkParser
/*  18:    */   implements PreprocessorTokenTypes
/*  19:    */ {
/*  20:    */   private Tool antlrTool;
/*  21:    */   
/*  22:    */   public void setTool(Tool paramTool)
/*  23:    */   {
/*  24: 37 */     if (this.antlrTool == null) {
/*  25: 38 */       this.antlrTool = paramTool;
/*  26:    */     } else {
/*  27: 41 */       throw new IllegalStateException("antlr.Tool already registered");
/*  28:    */     }
/*  29:    */   }
/*  30:    */   
/*  31:    */   protected Tool getTool()
/*  32:    */   {
/*  33: 47 */     return this.antlrTool;
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void reportError(String paramString)
/*  37:    */   {
/*  38: 55 */     if (getTool() != null) {
/*  39: 56 */       getTool().error(paramString, getFilename(), -1, -1);
/*  40:    */     } else {
/*  41: 59 */       super.reportError(paramString);
/*  42:    */     }
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void reportError(RecognitionException paramRecognitionException)
/*  46:    */   {
/*  47: 68 */     if (getTool() != null) {
/*  48: 69 */       getTool().error(paramRecognitionException.getErrorMessage(), paramRecognitionException.getFilename(), paramRecognitionException.getLine(), paramRecognitionException.getColumn());
/*  49:    */     } else {
/*  50: 72 */       super.reportError(paramRecognitionException);
/*  51:    */     }
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void reportWarning(String paramString)
/*  55:    */   {
/*  56: 81 */     if (getTool() != null) {
/*  57: 82 */       getTool().warning(paramString, getFilename(), -1, -1);
/*  58:    */     } else {
/*  59: 85 */       super.reportWarning(paramString);
/*  60:    */     }
/*  61:    */   }
/*  62:    */   
/*  63:    */   protected Preprocessor(TokenBuffer paramTokenBuffer, int paramInt)
/*  64:    */   {
/*  65: 90 */     super(paramTokenBuffer, paramInt);
/*  66: 91 */     this.tokenNames = _tokenNames;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public Preprocessor(TokenBuffer paramTokenBuffer)
/*  70:    */   {
/*  71: 95 */     this(paramTokenBuffer, 1);
/*  72:    */   }
/*  73:    */   
/*  74:    */   protected Preprocessor(TokenStream paramTokenStream, int paramInt)
/*  75:    */   {
/*  76: 99 */     super(paramTokenStream, paramInt);
/*  77:100 */     this.tokenNames = _tokenNames;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public Preprocessor(TokenStream paramTokenStream)
/*  81:    */   {
/*  82:104 */     this(paramTokenStream, 1);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public Preprocessor(ParserSharedInputState paramParserSharedInputState)
/*  86:    */   {
/*  87:108 */     super(paramParserSharedInputState, 1);
/*  88:109 */     this.tokenNames = _tokenNames;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public final void grammarFile(Hierarchy paramHierarchy, String paramString)
/*  92:    */     throws RecognitionException, TokenStreamException
/*  93:    */   {
/*  94:116 */     Token localToken = null;
/*  95:    */     
/*  96:    */ 
/*  97:119 */     IndexedVector localIndexedVector = null;
/*  98:    */     try
/*  99:    */     {
/* 100:126 */       while (LA(1) == 5)
/* 101:    */       {
/* 102:127 */         localToken = LT(1);
/* 103:128 */         match(5);
/* 104:129 */         paramHierarchy.getFile(paramString).addHeaderAction(localToken.getText());
/* 105:    */       }
/* 106:138 */       switch (LA(1))
/* 107:    */       {
/* 108:    */       case 13: 
/* 109:141 */         localIndexedVector = optionSpec(null);
/* 110:142 */         break;
/* 111:    */       case 1: 
/* 112:    */       case 7: 
/* 113:    */       case 8: 
/* 114:    */         break;
/* 115:    */       default: 
/* 116:152 */         throw new NoViableAltException(LT(1), getFilename());
/* 117:    */       }
/* 118:159 */       while ((LA(1) == 7) || (LA(1) == 8))
/* 119:    */       {
/* 120:160 */         Grammar localGrammar = class_def(paramString, paramHierarchy);
/* 121:162 */         if ((localGrammar != null) && (localIndexedVector != null)) {
/* 122:163 */           paramHierarchy.getFile(paramString).setOptions(localIndexedVector);
/* 123:    */         }
/* 124:165 */         if (localGrammar != null)
/* 125:    */         {
/* 126:166 */           localGrammar.setFileName(paramString);
/* 127:167 */           paramHierarchy.addGrammar(localGrammar);
/* 128:    */         }
/* 129:    */       }
/* 130:177 */       match(1);
/* 131:    */     }
/* 132:    */     catch (RecognitionException localRecognitionException)
/* 133:    */     {
/* 134:180 */       reportError(localRecognitionException);
/* 135:181 */       consume();
/* 136:182 */       consumeUntil(_tokenSet_0);
/* 137:    */     }
/* 138:    */   }
/* 139:    */   
/* 140:    */   public final IndexedVector optionSpec(Grammar paramGrammar)
/* 141:    */     throws RecognitionException, TokenStreamException
/* 142:    */   {
/* 143:191 */     Token localToken1 = null;
/* 144:192 */     Token localToken2 = null;
/* 145:    */     
/* 146:194 */     IndexedVector localIndexedVector = new IndexedVector();
/* 147:    */     try
/* 148:    */     {
/* 149:198 */       match(13);
/* 150:202 */       while (LA(1) == 9)
/* 151:    */       {
/* 152:203 */         localToken1 = LT(1);
/* 153:204 */         match(9);
/* 154:205 */         localToken2 = LT(1);
/* 155:206 */         match(14);
/* 156:    */         
/* 157:208 */         Option localOption = new Option(localToken1.getText(), localToken2.getText(), paramGrammar);
/* 158:209 */         localIndexedVector.appendElement(localOption.getName(), localOption);
/* 159:210 */         if ((paramGrammar != null) && (localToken1.getText().equals("importVocab")))
/* 160:    */         {
/* 161:211 */           paramGrammar.specifiedVocabulary = true;
/* 162:212 */           paramGrammar.importVocab = localToken2.getText();
/* 163:    */         }
/* 164:214 */         else if ((paramGrammar != null) && (localToken1.getText().equals("exportVocab")))
/* 165:    */         {
/* 166:217 */           paramGrammar.exportVocab = localToken2.getText().substring(0, localToken2.getText().length() - 1);
/* 167:218 */           paramGrammar.exportVocab = paramGrammar.exportVocab.trim();
/* 168:    */         }
/* 169:    */       }
/* 170:228 */       match(15);
/* 171:    */     }
/* 172:    */     catch (RecognitionException localRecognitionException)
/* 173:    */     {
/* 174:231 */       reportError(localRecognitionException);
/* 175:232 */       consume();
/* 176:233 */       consumeUntil(_tokenSet_1);
/* 177:    */     }
/* 178:235 */     return localIndexedVector;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public final Grammar class_def(String paramString, Hierarchy paramHierarchy)
/* 182:    */     throws RecognitionException, TokenStreamException
/* 183:    */   {
/* 184:243 */     Token localToken1 = null;
/* 185:244 */     Token localToken2 = null;
/* 186:245 */     Token localToken3 = null;
/* 187:246 */     Token localToken4 = null;
/* 188:247 */     Token localToken5 = null;
/* 189:    */     
/* 190:249 */     Grammar localGrammar = null;
/* 191:250 */     IndexedVector localIndexedVector1 = new IndexedVector(100);
/* 192:251 */     IndexedVector localIndexedVector2 = null;
/* 193:252 */     String str = null;
/* 194:    */     try
/* 195:    */     {
/* 196:257 */       switch (LA(1))
/* 197:    */       {
/* 198:    */       case 7: 
/* 199:260 */         localToken1 = LT(1);
/* 200:261 */         match(7);
/* 201:262 */         break;
/* 202:    */       case 8: 
/* 203:    */         break;
/* 204:    */       default: 
/* 205:270 */         throw new NoViableAltException(LT(1), getFilename());
/* 206:    */       }
/* 207:274 */       match(8);
/* 208:275 */       localToken2 = LT(1);
/* 209:276 */       match(9);
/* 210:277 */       match(10);
/* 211:278 */       localToken3 = LT(1);
/* 212:279 */       match(9);
/* 213:281 */       switch (LA(1))
/* 214:    */       {
/* 215:    */       case 6: 
/* 216:284 */         str = superClass();
/* 217:285 */         break;
/* 218:    */       case 11: 
/* 219:    */         break;
/* 220:    */       default: 
/* 221:293 */         throw new NoViableAltException(LT(1), getFilename());
/* 222:    */       }
/* 223:297 */       match(11);
/* 224:    */       
/* 225:299 */       localGrammar = paramHierarchy.getGrammar(localToken2.getText());
/* 226:300 */       if (localGrammar != null)
/* 227:    */       {
/* 228:302 */         localGrammar = null;
/* 229:303 */         throw new SemanticException("redefinition of grammar " + localToken2.getText(), paramString, localToken2.getLine(), localToken2.getColumn());
/* 230:    */       }
/* 231:306 */       localGrammar = new Grammar(paramHierarchy.getTool(), localToken2.getText(), localToken3.getText(), localIndexedVector1);
/* 232:307 */       localGrammar.superClass = str;
/* 233:308 */       if (localToken1 != null) {
/* 234:309 */         localGrammar.setPreambleAction(localToken1.getText());
/* 235:    */       }
/* 236:314 */       switch (LA(1))
/* 237:    */       {
/* 238:    */       case 13: 
/* 239:317 */         localIndexedVector2 = optionSpec(localGrammar);
/* 240:318 */         break;
/* 241:    */       case 7: 
/* 242:    */       case 9: 
/* 243:    */       case 12: 
/* 244:    */       case 16: 
/* 245:    */       case 17: 
/* 246:    */       case 18: 
/* 247:    */         break;
/* 248:    */       case 8: 
/* 249:    */       case 10: 
/* 250:    */       case 11: 
/* 251:    */       case 14: 
/* 252:    */       case 15: 
/* 253:    */       default: 
/* 254:331 */         throw new NoViableAltException(LT(1), getFilename());
/* 255:    */       }
/* 256:336 */       if (localGrammar != null) {
/* 257:337 */         localGrammar.setOptions(localIndexedVector2);
/* 258:    */       }
/* 259:341 */       switch (LA(1))
/* 260:    */       {
/* 261:    */       case 12: 
/* 262:344 */         localToken4 = LT(1);
/* 263:345 */         match(12);
/* 264:346 */         localGrammar.setTokenSection(localToken4.getText());
/* 265:347 */         break;
/* 266:    */       case 7: 
/* 267:    */       case 9: 
/* 268:    */       case 16: 
/* 269:    */       case 17: 
/* 270:    */       case 18: 
/* 271:    */         break;
/* 272:    */       case 8: 
/* 273:    */       case 10: 
/* 274:    */       case 11: 
/* 275:    */       case 13: 
/* 276:    */       case 14: 
/* 277:    */       case 15: 
/* 278:    */       default: 
/* 279:359 */         throw new NoViableAltException(LT(1), getFilename());
/* 280:    */       }
/* 281:364 */       switch (LA(1))
/* 282:    */       {
/* 283:    */       case 7: 
/* 284:367 */         localToken5 = LT(1);
/* 285:368 */         match(7);
/* 286:369 */         localGrammar.setMemberAction(localToken5.getText());
/* 287:370 */         break;
/* 288:    */       case 9: 
/* 289:    */       case 16: 
/* 290:    */       case 17: 
/* 291:    */       case 18: 
/* 292:    */         break;
/* 293:    */       case 8: 
/* 294:    */       case 10: 
/* 295:    */       case 11: 
/* 296:    */       case 12: 
/* 297:    */       case 13: 
/* 298:    */       case 14: 
/* 299:    */       case 15: 
/* 300:    */       default: 
/* 301:381 */         throw new NoViableAltException(LT(1), getFilename());
/* 302:    */       }
/* 303:386 */       int i = 0;
/* 304:    */       for (;;)
/* 305:    */       {
/* 306:389 */         if (_tokenSet_2.member(LA(1)))
/* 307:    */         {
/* 308:390 */           rule(localGrammar);
/* 309:    */         }
/* 310:    */         else
/* 311:    */         {
/* 312:393 */           if (i >= 1) {
/* 313:    */             break;
/* 314:    */           }
/* 315:393 */           throw new NoViableAltException(LT(1), getFilename());
/* 316:    */         }
/* 317:396 */         i++;
/* 318:    */       }
/* 319:    */     }
/* 320:    */     catch (RecognitionException localRecognitionException)
/* 321:    */     {
/* 322:401 */       reportError(localRecognitionException);
/* 323:402 */       consume();
/* 324:403 */       consumeUntil(_tokenSet_3);
/* 325:    */     }
/* 326:405 */     return localGrammar;
/* 327:    */   }
/* 328:    */   
/* 329:    */   public final String superClass()
/* 330:    */     throws RecognitionException, TokenStreamException
/* 331:    */   {
/* 332:411 */     String str = LT(1).getText();
/* 333:    */     try
/* 334:    */     {
/* 335:414 */       match(6);
/* 336:    */     }
/* 337:    */     catch (RecognitionException localRecognitionException)
/* 338:    */     {
/* 339:417 */       reportError(localRecognitionException);
/* 340:418 */       consume();
/* 341:419 */       consumeUntil(_tokenSet_4);
/* 342:    */     }
/* 343:421 */     return str;
/* 344:    */   }
/* 345:    */   
/* 346:    */   public final void rule(Grammar paramGrammar)
/* 347:    */     throws RecognitionException, TokenStreamException
/* 348:    */   {
/* 349:428 */     Token localToken1 = null;
/* 350:429 */     Token localToken2 = null;
/* 351:430 */     Token localToken3 = null;
/* 352:431 */     Token localToken4 = null;
/* 353:432 */     Token localToken5 = null;
/* 354:    */     
/* 355:434 */     IndexedVector localIndexedVector = null;
/* 356:435 */     String str1 = null;
/* 357:436 */     int i = 0;
/* 358:437 */     String str2 = null;String str3 = "";
/* 359:    */     try
/* 360:    */     {
/* 361:442 */       switch (LA(1))
/* 362:    */       {
/* 363:    */       case 16: 
/* 364:445 */         match(16);
/* 365:446 */         str1 = "protected";
/* 366:447 */         break;
/* 367:    */       case 17: 
/* 368:451 */         match(17);
/* 369:452 */         str1 = "private";
/* 370:453 */         break;
/* 371:    */       case 18: 
/* 372:457 */         match(18);
/* 373:458 */         str1 = "public";
/* 374:459 */         break;
/* 375:    */       case 9: 
/* 376:    */         break;
/* 377:    */       case 10: 
/* 378:    */       case 11: 
/* 379:    */       case 12: 
/* 380:    */       case 13: 
/* 381:    */       case 14: 
/* 382:    */       case 15: 
/* 383:    */       default: 
/* 384:467 */         throw new NoViableAltException(LT(1), getFilename());
/* 385:    */       }
/* 386:471 */       localToken1 = LT(1);
/* 387:472 */       match(9);
/* 388:474 */       switch (LA(1))
/* 389:    */       {
/* 390:    */       case 19: 
/* 391:477 */         match(19);
/* 392:478 */         i = 1;
/* 393:479 */         break;
/* 394:    */       case 7: 
/* 395:    */       case 13: 
/* 396:    */       case 20: 
/* 397:    */       case 21: 
/* 398:    */       case 22: 
/* 399:    */       case 23: 
/* 400:    */         break;
/* 401:    */       case 8: 
/* 402:    */       case 9: 
/* 403:    */       case 10: 
/* 404:    */       case 11: 
/* 405:    */       case 12: 
/* 406:    */       case 14: 
/* 407:    */       case 15: 
/* 408:    */       case 16: 
/* 409:    */       case 17: 
/* 410:    */       case 18: 
/* 411:    */       default: 
/* 412:492 */         throw new NoViableAltException(LT(1), getFilename());
/* 413:    */       }
/* 414:497 */       switch (LA(1))
/* 415:    */       {
/* 416:    */       case 20: 
/* 417:500 */         localToken2 = LT(1);
/* 418:501 */         match(20);
/* 419:502 */         break;
/* 420:    */       case 7: 
/* 421:    */       case 13: 
/* 422:    */       case 21: 
/* 423:    */       case 22: 
/* 424:    */       case 23: 
/* 425:    */         break;
/* 426:    */       case 8: 
/* 427:    */       case 9: 
/* 428:    */       case 10: 
/* 429:    */       case 11: 
/* 430:    */       case 12: 
/* 431:    */       case 14: 
/* 432:    */       case 15: 
/* 433:    */       case 16: 
/* 434:    */       case 17: 
/* 435:    */       case 18: 
/* 436:    */       case 19: 
/* 437:    */       default: 
/* 438:514 */         throw new NoViableAltException(LT(1), getFilename());
/* 439:    */       }
/* 440:519 */       switch (LA(1))
/* 441:    */       {
/* 442:    */       case 21: 
/* 443:522 */         match(21);
/* 444:523 */         localToken3 = LT(1);
/* 445:524 */         match(20);
/* 446:525 */         break;
/* 447:    */       case 7: 
/* 448:    */       case 13: 
/* 449:    */       case 22: 
/* 450:    */       case 23: 
/* 451:    */         break;
/* 452:    */       default: 
/* 453:536 */         throw new NoViableAltException(LT(1), getFilename());
/* 454:    */       }
/* 455:541 */       switch (LA(1))
/* 456:    */       {
/* 457:    */       case 23: 
/* 458:544 */         str3 = throwsSpec();
/* 459:545 */         break;
/* 460:    */       case 7: 
/* 461:    */       case 13: 
/* 462:    */       case 22: 
/* 463:    */         break;
/* 464:    */       default: 
/* 465:555 */         throw new NoViableAltException(LT(1), getFilename());
/* 466:    */       }
/* 467:560 */       switch (LA(1))
/* 468:    */       {
/* 469:    */       case 13: 
/* 470:563 */         localIndexedVector = optionSpec(null);
/* 471:564 */         break;
/* 472:    */       case 7: 
/* 473:    */       case 22: 
/* 474:    */         break;
/* 475:    */       default: 
/* 476:573 */         throw new NoViableAltException(LT(1), getFilename());
/* 477:    */       }
/* 478:578 */       switch (LA(1))
/* 479:    */       {
/* 480:    */       case 7: 
/* 481:581 */         localToken4 = LT(1);
/* 482:582 */         match(7);
/* 483:583 */         break;
/* 484:    */       case 22: 
/* 485:    */         break;
/* 486:    */       default: 
/* 487:591 */         throw new NoViableAltException(LT(1), getFilename());
/* 488:    */       }
/* 489:595 */       localToken5 = LT(1);
/* 490:596 */       match(22);
/* 491:597 */       str2 = exceptionGroup();
/* 492:    */       
/* 493:599 */       String str4 = localToken5.getText() + str2;
/* 494:600 */       Rule localRule = new Rule(localToken1.getText(), str4, localIndexedVector, paramGrammar);
/* 495:601 */       localRule.setThrowsSpec(str3);
/* 496:602 */       if (localToken2 != null) {
/* 497:603 */         localRule.setArgs(localToken2.getText());
/* 498:    */       }
/* 499:605 */       if (localToken3 != null) {
/* 500:606 */         localRule.setReturnValue(localToken3.getText());
/* 501:    */       }
/* 502:608 */       if (localToken4 != null) {
/* 503:609 */         localRule.setInitAction(localToken4.getText());
/* 504:    */       }
/* 505:611 */       if (i != 0) {
/* 506:612 */         localRule.setBang();
/* 507:    */       }
/* 508:614 */       localRule.setVisibility(str1);
/* 509:615 */       if (paramGrammar != null) {
/* 510:616 */         paramGrammar.addRule(localRule);
/* 511:    */       }
/* 512:    */     }
/* 513:    */     catch (RecognitionException localRecognitionException)
/* 514:    */     {
/* 515:621 */       reportError(localRecognitionException);
/* 516:622 */       consume();
/* 517:623 */       consumeUntil(_tokenSet_5);
/* 518:    */     }
/* 519:    */   }
/* 520:    */   
/* 521:    */   public final String throwsSpec()
/* 522:    */     throws RecognitionException, TokenStreamException
/* 523:    */   {
/* 524:630 */     Token localToken1 = null;
/* 525:631 */     Token localToken2 = null;
/* 526:632 */     String str = "throws ";
/* 527:    */     try
/* 528:    */     {
/* 529:635 */       match(23);
/* 530:636 */       localToken1 = LT(1);
/* 531:637 */       match(9);
/* 532:638 */       str = str + localToken1.getText();
/* 533:642 */       while (LA(1) == 24)
/* 534:    */       {
/* 535:643 */         match(24);
/* 536:644 */         localToken2 = LT(1);
/* 537:645 */         match(9);
/* 538:646 */         str = str + "," + localToken2.getText();
/* 539:    */       }
/* 540:    */     }
/* 541:    */     catch (RecognitionException localRecognitionException)
/* 542:    */     {
/* 543:656 */       reportError(localRecognitionException);
/* 544:657 */       consume();
/* 545:658 */       consumeUntil(_tokenSet_6);
/* 546:    */     }
/* 547:660 */     return str;
/* 548:    */   }
/* 549:    */   
/* 550:    */   public final String exceptionGroup()
/* 551:    */     throws RecognitionException, TokenStreamException
/* 552:    */   {
/* 553:666 */     String str2 = null;String str1 = "";
/* 554:    */     try
/* 555:    */     {
/* 556:672 */       while (LA(1) == 25)
/* 557:    */       {
/* 558:673 */         str2 = exceptionSpec();
/* 559:674 */         str1 = str1 + str2;
/* 560:    */       }
/* 561:    */     }
/* 562:    */     catch (RecognitionException localRecognitionException)
/* 563:    */     {
/* 564:684 */       reportError(localRecognitionException);
/* 565:685 */       consume();
/* 566:686 */       consumeUntil(_tokenSet_5);
/* 567:    */     }
/* 568:688 */     return str1;
/* 569:    */   }
/* 570:    */   
/* 571:    */   public final String exceptionSpec()
/* 572:    */     throws RecognitionException, TokenStreamException
/* 573:    */   {
/* 574:694 */     Token localToken = null;
/* 575:695 */     String str2 = null;
/* 576:696 */     String str1 = System.getProperty("line.separator") + "exception ";
/* 577:    */     try
/* 578:    */     {
/* 579:700 */       match(25);
/* 580:702 */       switch (LA(1))
/* 581:    */       {
/* 582:    */       case 20: 
/* 583:705 */         localToken = LT(1);
/* 584:706 */         match(20);
/* 585:707 */         str1 = str1 + localToken.getText();
/* 586:708 */         break;
/* 587:    */       case 1: 
/* 588:    */       case 7: 
/* 589:    */       case 8: 
/* 590:    */       case 9: 
/* 591:    */       case 16: 
/* 592:    */       case 17: 
/* 593:    */       case 18: 
/* 594:    */       case 25: 
/* 595:    */       case 26: 
/* 596:    */         break;
/* 597:    */       case 2: 
/* 598:    */       case 3: 
/* 599:    */       case 4: 
/* 600:    */       case 5: 
/* 601:    */       case 6: 
/* 602:    */       case 10: 
/* 603:    */       case 11: 
/* 604:    */       case 12: 
/* 605:    */       case 13: 
/* 606:    */       case 14: 
/* 607:    */       case 15: 
/* 608:    */       case 19: 
/* 609:    */       case 21: 
/* 610:    */       case 22: 
/* 611:    */       case 23: 
/* 612:    */       case 24: 
/* 613:    */       default: 
/* 614:724 */         throw new NoViableAltException(LT(1), getFilename());
/* 615:    */       }
/* 616:731 */       while (LA(1) == 26)
/* 617:    */       {
/* 618:732 */         str2 = exceptionHandler();
/* 619:733 */         str1 = str1 + str2;
/* 620:    */       }
/* 621:    */     }
/* 622:    */     catch (RecognitionException localRecognitionException)
/* 623:    */     {
/* 624:743 */       reportError(localRecognitionException);
/* 625:744 */       consume();
/* 626:745 */       consumeUntil(_tokenSet_7);
/* 627:    */     }
/* 628:747 */     return str1;
/* 629:    */   }
/* 630:    */   
/* 631:    */   public final String exceptionHandler()
/* 632:    */     throws RecognitionException, TokenStreamException
/* 633:    */   {
/* 634:753 */     Token localToken1 = null;
/* 635:754 */     Token localToken2 = null;
/* 636:755 */     String str = null;
/* 637:    */     try
/* 638:    */     {
/* 639:758 */       match(26);
/* 640:759 */       localToken1 = LT(1);
/* 641:760 */       match(20);
/* 642:761 */       localToken2 = LT(1);
/* 643:762 */       match(7);
/* 644:763 */       str = System.getProperty("line.separator") + "catch " + localToken1.getText() + " " + localToken2.getText();
/* 645:    */     }
/* 646:    */     catch (RecognitionException localRecognitionException)
/* 647:    */     {
/* 648:767 */       reportError(localRecognitionException);
/* 649:768 */       consume();
/* 650:769 */       consumeUntil(_tokenSet_8);
/* 651:    */     }
/* 652:771 */     return str;
/* 653:    */   }
/* 654:    */   
/* 655:775 */   public static final String[] _tokenNames = { "<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "\"tokens\"", "HEADER_ACTION", "SUBRULE_BLOCK", "ACTION", "\"class\"", "ID", "\"extends\"", "SEMI", "TOKENS_SPEC", "OPTIONS_START", "ASSIGN_RHS", "RCURLY", "\"protected\"", "\"private\"", "\"public\"", "BANG", "ARG_ACTION", "\"returns\"", "RULE_BLOCK", "\"throws\"", "COMMA", "\"exception\"", "\"catch\"", "ALT", "ELEMENT", "LPAREN", "RPAREN", "ID_OR_KEYWORD", "CURLY_BLOCK_SCARF", "WS", "NEWLINE", "COMMENT", "SL_COMMENT", "ML_COMMENT", "CHAR_LITERAL", "STRING_LITERAL", "ESC", "DIGIT", "XDIGIT" };
/* 656:    */   
/* 657:    */   private static final long[] mk_tokenSet_0()
/* 658:    */   {
/* 659:822 */     long[] arrayOfLong = { 2L, 0L };
/* 660:823 */     return arrayOfLong;
/* 661:    */   }
/* 662:    */   
/* 663:825 */   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
/* 664:    */   
/* 665:    */   private static final long[] mk_tokenSet_1()
/* 666:    */   {
/* 667:827 */     long[] arrayOfLong = { 4658050L, 0L };
/* 668:828 */     return arrayOfLong;
/* 669:    */   }
/* 670:    */   
/* 671:830 */   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
/* 672:    */   
/* 673:    */   private static final long[] mk_tokenSet_2()
/* 674:    */   {
/* 675:832 */     long[] arrayOfLong = { 459264L, 0L };
/* 676:833 */     return arrayOfLong;
/* 677:    */   }
/* 678:    */   
/* 679:835 */   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
/* 680:    */   
/* 681:    */   private static final long[] mk_tokenSet_3()
/* 682:    */   {
/* 683:837 */     long[] arrayOfLong = { 386L, 0L };
/* 684:838 */     return arrayOfLong;
/* 685:    */   }
/* 686:    */   
/* 687:840 */   public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
/* 688:    */   
/* 689:    */   private static final long[] mk_tokenSet_4()
/* 690:    */   {
/* 691:842 */     long[] arrayOfLong = { 2048L, 0L };
/* 692:843 */     return arrayOfLong;
/* 693:    */   }
/* 694:    */   
/* 695:845 */   public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
/* 696:    */   
/* 697:    */   private static final long[] mk_tokenSet_5()
/* 698:    */   {
/* 699:847 */     long[] arrayOfLong = { 459650L, 0L };
/* 700:848 */     return arrayOfLong;
/* 701:    */   }
/* 702:    */   
/* 703:850 */   public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
/* 704:    */   
/* 705:    */   private static final long[] mk_tokenSet_6()
/* 706:    */   {
/* 707:852 */     long[] arrayOfLong = { 4202624L, 0L };
/* 708:853 */     return arrayOfLong;
/* 709:    */   }
/* 710:    */   
/* 711:855 */   public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
/* 712:    */   
/* 713:    */   private static final long[] mk_tokenSet_7()
/* 714:    */   {
/* 715:857 */     long[] arrayOfLong = { 34014082L, 0L };
/* 716:858 */     return arrayOfLong;
/* 717:    */   }
/* 718:    */   
/* 719:860 */   public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
/* 720:    */   
/* 721:    */   private static final long[] mk_tokenSet_8()
/* 722:    */   {
/* 723:862 */     long[] arrayOfLong = { 101122946L, 0L };
/* 724:863 */     return arrayOfLong;
/* 725:    */   }
/* 726:    */   
/* 727:865 */   public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
/* 728:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.preprocessor.Preprocessor
 * JD-Core Version:    0.7.0.1
 */