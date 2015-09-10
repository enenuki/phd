/*   1:    */ package antlr;
/*   2:    */ 
/*   3:    */ import antlr.collections.impl.BitSet;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.Reader;
/*   6:    */ import java.util.Hashtable;
/*   7:    */ 
/*   8:    */ public class ANTLRTokdefLexer
/*   9:    */   extends CharScanner
/*  10:    */   implements ANTLRTokdefParserTokenTypes, TokenStream
/*  11:    */ {
/*  12:    */   public ANTLRTokdefLexer(InputStream paramInputStream)
/*  13:    */   {
/*  14: 30 */     this(new ByteBuffer(paramInputStream));
/*  15:    */   }
/*  16:    */   
/*  17:    */   public ANTLRTokdefLexer(Reader paramReader)
/*  18:    */   {
/*  19: 33 */     this(new CharBuffer(paramReader));
/*  20:    */   }
/*  21:    */   
/*  22:    */   public ANTLRTokdefLexer(InputBuffer paramInputBuffer)
/*  23:    */   {
/*  24: 36 */     this(new LexerSharedInputState(paramInputBuffer));
/*  25:    */   }
/*  26:    */   
/*  27:    */   public ANTLRTokdefLexer(LexerSharedInputState paramLexerSharedInputState)
/*  28:    */   {
/*  29: 39 */     super(paramLexerSharedInputState);
/*  30: 40 */     this.caseSensitiveLiterals = true;
/*  31: 41 */     setCaseSensitive(true);
/*  32: 42 */     this.literals = new Hashtable();
/*  33:    */   }
/*  34:    */   
/*  35:    */   public Token nextToken()
/*  36:    */     throws TokenStreamException
/*  37:    */   {
/*  38: 46 */     Token localToken = null;
/*  39:    */     for (;;)
/*  40:    */     {
/*  41: 49 */       Object localObject = null;
/*  42: 50 */       int i = 0;
/*  43: 51 */       resetText();
/*  44:    */       try
/*  45:    */       {
/*  46: 54 */         switch (LA(1))
/*  47:    */         {
/*  48:    */         case '\t': 
/*  49:    */         case '\n': 
/*  50:    */         case '\r': 
/*  51:    */         case ' ': 
/*  52: 57 */           mWS(true);
/*  53: 58 */           localToken = this._returnToken;
/*  54: 59 */           break;
/*  55:    */         case '(': 
/*  56: 63 */           mLPAREN(true);
/*  57: 64 */           localToken = this._returnToken;
/*  58: 65 */           break;
/*  59:    */         case ')': 
/*  60: 69 */           mRPAREN(true);
/*  61: 70 */           localToken = this._returnToken;
/*  62: 71 */           break;
/*  63:    */         case '=': 
/*  64: 75 */           mASSIGN(true);
/*  65: 76 */           localToken = this._returnToken;
/*  66: 77 */           break;
/*  67:    */         case '"': 
/*  68: 81 */           mSTRING(true);
/*  69: 82 */           localToken = this._returnToken;
/*  70: 83 */           break;
/*  71:    */         case 'A': 
/*  72:    */         case 'B': 
/*  73:    */         case 'C': 
/*  74:    */         case 'D': 
/*  75:    */         case 'E': 
/*  76:    */         case 'F': 
/*  77:    */         case 'G': 
/*  78:    */         case 'H': 
/*  79:    */         case 'I': 
/*  80:    */         case 'J': 
/*  81:    */         case 'K': 
/*  82:    */         case 'L': 
/*  83:    */         case 'M': 
/*  84:    */         case 'N': 
/*  85:    */         case 'O': 
/*  86:    */         case 'P': 
/*  87:    */         case 'Q': 
/*  88:    */         case 'R': 
/*  89:    */         case 'S': 
/*  90:    */         case 'T': 
/*  91:    */         case 'U': 
/*  92:    */         case 'V': 
/*  93:    */         case 'W': 
/*  94:    */         case 'X': 
/*  95:    */         case 'Y': 
/*  96:    */         case 'Z': 
/*  97:    */         case 'a': 
/*  98:    */         case 'b': 
/*  99:    */         case 'c': 
/* 100:    */         case 'd': 
/* 101:    */         case 'e': 
/* 102:    */         case 'f': 
/* 103:    */         case 'g': 
/* 104:    */         case 'h': 
/* 105:    */         case 'i': 
/* 106:    */         case 'j': 
/* 107:    */         case 'k': 
/* 108:    */         case 'l': 
/* 109:    */         case 'm': 
/* 110:    */         case 'n': 
/* 111:    */         case 'o': 
/* 112:    */         case 'p': 
/* 113:    */         case 'q': 
/* 114:    */         case 'r': 
/* 115:    */         case 's': 
/* 116:    */         case 't': 
/* 117:    */         case 'u': 
/* 118:    */         case 'v': 
/* 119:    */         case 'w': 
/* 120:    */         case 'x': 
/* 121:    */         case 'y': 
/* 122:    */         case 'z': 
/* 123: 99 */           mID(true);
/* 124:100 */           localToken = this._returnToken;
/* 125:101 */           break;
/* 126:    */         case '0': 
/* 127:    */         case '1': 
/* 128:    */         case '2': 
/* 129:    */         case '3': 
/* 130:    */         case '4': 
/* 131:    */         case '5': 
/* 132:    */         case '6': 
/* 133:    */         case '7': 
/* 134:    */         case '8': 
/* 135:    */         case '9': 
/* 136:107 */           mINT(true);
/* 137:108 */           localToken = this._returnToken;
/* 138:109 */           break;
/* 139:    */         case '\013': 
/* 140:    */         case '\f': 
/* 141:    */         case '\016': 
/* 142:    */         case '\017': 
/* 143:    */         case '\020': 
/* 144:    */         case '\021': 
/* 145:    */         case '\022': 
/* 146:    */         case '\023': 
/* 147:    */         case '\024': 
/* 148:    */         case '\025': 
/* 149:    */         case '\026': 
/* 150:    */         case '\027': 
/* 151:    */         case '\030': 
/* 152:    */         case '\031': 
/* 153:    */         case '\032': 
/* 154:    */         case '\033': 
/* 155:    */         case '\034': 
/* 156:    */         case '\035': 
/* 157:    */         case '\036': 
/* 158:    */         case '\037': 
/* 159:    */         case '!': 
/* 160:    */         case '#': 
/* 161:    */         case '$': 
/* 162:    */         case '%': 
/* 163:    */         case '&': 
/* 164:    */         case '\'': 
/* 165:    */         case '*': 
/* 166:    */         case '+': 
/* 167:    */         case ',': 
/* 168:    */         case '-': 
/* 169:    */         case '.': 
/* 170:    */         case '/': 
/* 171:    */         case ':': 
/* 172:    */         case ';': 
/* 173:    */         case '<': 
/* 174:    */         case '>': 
/* 175:    */         case '?': 
/* 176:    */         case '@': 
/* 177:    */         case '[': 
/* 178:    */         case '\\': 
/* 179:    */         case ']': 
/* 180:    */         case '^': 
/* 181:    */         case '_': 
/* 182:    */         case '`': 
/* 183:    */         default: 
/* 184:112 */           if ((LA(1) == '/') && (LA(2) == '/'))
/* 185:    */           {
/* 186:113 */             mSL_COMMENT(true);
/* 187:114 */             localToken = this._returnToken;
/* 188:    */           }
/* 189:116 */           else if ((LA(1) == '/') && (LA(2) == '*'))
/* 190:    */           {
/* 191:117 */             mML_COMMENT(true);
/* 192:118 */             localToken = this._returnToken;
/* 193:    */           }
/* 194:121 */           else if (LA(1) == 65535)
/* 195:    */           {
/* 196:121 */             uponEOF();this._returnToken = makeToken(1);
/* 197:    */           }
/* 198:    */           else
/* 199:    */           {
/* 200:122 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 201:    */           }
/* 202:    */           break;
/* 203:    */         }
/* 204:125 */         if (this._returnToken == null) {
/* 205:    */           continue;
/* 206:    */         }
/* 207:126 */         i = this._returnToken.getType();
/* 208:127 */         this._returnToken.setType(i);
/* 209:128 */         return this._returnToken;
/* 210:    */       }
/* 211:    */       catch (RecognitionException localRecognitionException)
/* 212:    */       {
/* 213:131 */         throw new TokenStreamRecognitionException(localRecognitionException);
/* 214:    */       }
/* 215:    */       catch (CharStreamException localCharStreamException)
/* 216:    */       {
/* 217:135 */         if ((localCharStreamException instanceof CharStreamIOException)) {
/* 218:136 */           throw new TokenStreamIOException(((CharStreamIOException)localCharStreamException).io);
/* 219:    */         }
/* 220:139 */         throw new TokenStreamException(localCharStreamException.getMessage());
/* 221:    */       }
/* 222:    */     }
/* 223:    */   }
/* 224:    */   
/* 225:    */   public final void mWS(boolean paramBoolean)
/* 226:    */     throws RecognitionException, CharStreamException, TokenStreamException
/* 227:    */   {
/* 228:146 */     Token localToken = null;int j = this.text.length();
/* 229:147 */     int i = 10;
/* 230:151 */     switch (LA(1))
/* 231:    */     {
/* 232:    */     case ' ': 
/* 233:154 */       match(' ');
/* 234:155 */       break;
/* 235:    */     case '\t': 
/* 236:159 */       match('\t');
/* 237:160 */       break;
/* 238:    */     case '\r': 
/* 239:164 */       match('\r');
/* 240:166 */       if (LA(1) == '\n') {
/* 241:167 */         match('\n');
/* 242:    */       }
/* 243:173 */       newline();
/* 244:174 */       break;
/* 245:    */     case '\n': 
/* 246:178 */       match('\n');
/* 247:179 */       newline();
/* 248:180 */       break;
/* 249:    */     default: 
/* 250:184 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 251:    */     }
/* 252:188 */     i = -1;
/* 253:189 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 254:    */     {
/* 255:190 */       localToken = makeToken(i);
/* 256:191 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 257:    */     }
/* 258:193 */     this._returnToken = localToken;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public final void mSL_COMMENT(boolean paramBoolean)
/* 262:    */     throws RecognitionException, CharStreamException, TokenStreamException
/* 263:    */   {
/* 264:197 */     Token localToken = null;int j = this.text.length();
/* 265:198 */     int i = 11;
/* 266:    */     
/* 267:    */ 
/* 268:201 */     match("//");
/* 269:205 */     while (_tokenSet_0.member(LA(1))) {
/* 270:207 */       match(_tokenSet_0);
/* 271:    */     }
/* 272:217 */     switch (LA(1))
/* 273:    */     {
/* 274:    */     case '\n': 
/* 275:220 */       match('\n');
/* 276:221 */       break;
/* 277:    */     case '\r': 
/* 278:225 */       match('\r');
/* 279:227 */       if (LA(1) == '\n') {
/* 280:228 */         match('\n');
/* 281:    */       }
/* 282:    */       break;
/* 283:    */     default: 
/* 284:238 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 285:    */     }
/* 286:242 */     i = -1;newline();
/* 287:243 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 288:    */     {
/* 289:244 */       localToken = makeToken(i);
/* 290:245 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 291:    */     }
/* 292:247 */     this._returnToken = localToken;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public final void mML_COMMENT(boolean paramBoolean)
/* 296:    */     throws RecognitionException, CharStreamException, TokenStreamException
/* 297:    */   {
/* 298:251 */     Token localToken = null;int j = this.text.length();
/* 299:252 */     int i = 12;
/* 300:    */     
/* 301:    */ 
/* 302:255 */     match("/*");
/* 303:    */     for (;;)
/* 304:    */     {
/* 305:259 */       if ((LA(1) == '*') && (_tokenSet_1.member(LA(2))))
/* 306:    */       {
/* 307:260 */         match('*');
/* 308:261 */         matchNot('/');
/* 309:    */       }
/* 310:263 */       else if (LA(1) == '\n')
/* 311:    */       {
/* 312:264 */         match('\n');
/* 313:265 */         newline();
/* 314:    */       }
/* 315:    */       else
/* 316:    */       {
/* 317:267 */         if (!_tokenSet_2.member(LA(1))) {
/* 318:    */           break;
/* 319:    */         }
/* 320:268 */         matchNot('*');
/* 321:    */       }
/* 322:    */     }
/* 323:276 */     match("*/");
/* 324:277 */     i = -1;
/* 325:278 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 326:    */     {
/* 327:279 */       localToken = makeToken(i);
/* 328:280 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 329:    */     }
/* 330:282 */     this._returnToken = localToken;
/* 331:    */   }
/* 332:    */   
/* 333:    */   public final void mLPAREN(boolean paramBoolean)
/* 334:    */     throws RecognitionException, CharStreamException, TokenStreamException
/* 335:    */   {
/* 336:286 */     Token localToken = null;int j = this.text.length();
/* 337:287 */     int i = 7;
/* 338:    */     
/* 339:    */ 
/* 340:290 */     match('(');
/* 341:291 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 342:    */     {
/* 343:292 */       localToken = makeToken(i);
/* 344:293 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 345:    */     }
/* 346:295 */     this._returnToken = localToken;
/* 347:    */   }
/* 348:    */   
/* 349:    */   public final void mRPAREN(boolean paramBoolean)
/* 350:    */     throws RecognitionException, CharStreamException, TokenStreamException
/* 351:    */   {
/* 352:299 */     Token localToken = null;int j = this.text.length();
/* 353:300 */     int i = 8;
/* 354:    */     
/* 355:    */ 
/* 356:303 */     match(')');
/* 357:304 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 358:    */     {
/* 359:305 */       localToken = makeToken(i);
/* 360:306 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 361:    */     }
/* 362:308 */     this._returnToken = localToken;
/* 363:    */   }
/* 364:    */   
/* 365:    */   public final void mASSIGN(boolean paramBoolean)
/* 366:    */     throws RecognitionException, CharStreamException, TokenStreamException
/* 367:    */   {
/* 368:312 */     Token localToken = null;int j = this.text.length();
/* 369:313 */     int i = 6;
/* 370:    */     
/* 371:    */ 
/* 372:316 */     match('=');
/* 373:317 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 374:    */     {
/* 375:318 */       localToken = makeToken(i);
/* 376:319 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 377:    */     }
/* 378:321 */     this._returnToken = localToken;
/* 379:    */   }
/* 380:    */   
/* 381:    */   public final void mSTRING(boolean paramBoolean)
/* 382:    */     throws RecognitionException, CharStreamException, TokenStreamException
/* 383:    */   {
/* 384:325 */     Token localToken = null;int j = this.text.length();
/* 385:326 */     int i = 5;
/* 386:    */     
/* 387:    */ 
/* 388:329 */     match('"');
/* 389:    */     for (;;)
/* 390:    */     {
/* 391:333 */       if (LA(1) == '\\')
/* 392:    */       {
/* 393:334 */         mESC(false);
/* 394:    */       }
/* 395:    */       else
/* 396:    */       {
/* 397:336 */         if (!_tokenSet_3.member(LA(1))) {
/* 398:    */           break;
/* 399:    */         }
/* 400:337 */         matchNot('"');
/* 401:    */       }
/* 402:    */     }
/* 403:345 */     match('"');
/* 404:346 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 405:    */     {
/* 406:347 */       localToken = makeToken(i);
/* 407:348 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 408:    */     }
/* 409:350 */     this._returnToken = localToken;
/* 410:    */   }
/* 411:    */   
/* 412:    */   protected final void mESC(boolean paramBoolean)
/* 413:    */     throws RecognitionException, CharStreamException, TokenStreamException
/* 414:    */   {
/* 415:354 */     Token localToken = null;int j = this.text.length();
/* 416:355 */     int i = 13;
/* 417:    */     
/* 418:    */ 
/* 419:358 */     match('\\');
/* 420:360 */     switch (LA(1))
/* 421:    */     {
/* 422:    */     case 'n': 
/* 423:363 */       match('n');
/* 424:364 */       break;
/* 425:    */     case 'r': 
/* 426:368 */       match('r');
/* 427:369 */       break;
/* 428:    */     case 't': 
/* 429:373 */       match('t');
/* 430:374 */       break;
/* 431:    */     case 'b': 
/* 432:378 */       match('b');
/* 433:379 */       break;
/* 434:    */     case 'f': 
/* 435:383 */       match('f');
/* 436:384 */       break;
/* 437:    */     case '"': 
/* 438:388 */       match('"');
/* 439:389 */       break;
/* 440:    */     case '\'': 
/* 441:393 */       match('\'');
/* 442:394 */       break;
/* 443:    */     case '\\': 
/* 444:398 */       match('\\');
/* 445:399 */       break;
/* 446:    */     case '0': 
/* 447:    */     case '1': 
/* 448:    */     case '2': 
/* 449:    */     case '3': 
/* 450:404 */       matchRange('0', '3');
/* 451:407 */       if ((LA(1) >= '0') && (LA(1) <= '9') && (LA(2) >= '\003') && (LA(2) <= 'ÿ'))
/* 452:    */       {
/* 453:408 */         mDIGIT(false);
/* 454:410 */         if ((LA(1) >= '0') && (LA(1) <= '9') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 455:411 */           mDIGIT(false);
/* 456:413 */         } else if ((LA(1) < '\003') || (LA(1) > 'ÿ')) {
/* 457:416 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 458:    */         }
/* 459:    */       }
/* 460:421 */       else if ((LA(1) < '\003') || (LA(1) > 'ÿ'))
/* 461:    */       {
/* 462:424 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 463:    */       }
/* 464:    */       break;
/* 465:    */     case '4': 
/* 466:    */     case '5': 
/* 467:    */     case '6': 
/* 468:    */     case '7': 
/* 469:433 */       matchRange('4', '7');
/* 470:436 */       if ((LA(1) >= '0') && (LA(1) <= '9') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 471:437 */         mDIGIT(false);
/* 472:439 */       } else if ((LA(1) < '\003') || (LA(1) > 'ÿ')) {
/* 473:442 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 474:    */       }
/* 475:    */       break;
/* 476:    */     case 'u': 
/* 477:450 */       match('u');
/* 478:451 */       mXDIGIT(false);
/* 479:452 */       mXDIGIT(false);
/* 480:453 */       mXDIGIT(false);
/* 481:454 */       mXDIGIT(false);
/* 482:455 */       break;
/* 483:    */     default: 
/* 484:459 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 485:    */     }
/* 486:463 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 487:    */     {
/* 488:464 */       localToken = makeToken(i);
/* 489:465 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 490:    */     }
/* 491:467 */     this._returnToken = localToken;
/* 492:    */   }
/* 493:    */   
/* 494:    */   protected final void mDIGIT(boolean paramBoolean)
/* 495:    */     throws RecognitionException, CharStreamException, TokenStreamException
/* 496:    */   {
/* 497:471 */     Token localToken = null;int j = this.text.length();
/* 498:472 */     int i = 14;
/* 499:    */     
/* 500:    */ 
/* 501:475 */     matchRange('0', '9');
/* 502:476 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 503:    */     {
/* 504:477 */       localToken = makeToken(i);
/* 505:478 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 506:    */     }
/* 507:480 */     this._returnToken = localToken;
/* 508:    */   }
/* 509:    */   
/* 510:    */   protected final void mXDIGIT(boolean paramBoolean)
/* 511:    */     throws RecognitionException, CharStreamException, TokenStreamException
/* 512:    */   {
/* 513:484 */     Token localToken = null;int j = this.text.length();
/* 514:485 */     int i = 15;
/* 515:488 */     switch (LA(1))
/* 516:    */     {
/* 517:    */     case '0': 
/* 518:    */     case '1': 
/* 519:    */     case '2': 
/* 520:    */     case '3': 
/* 521:    */     case '4': 
/* 522:    */     case '5': 
/* 523:    */     case '6': 
/* 524:    */     case '7': 
/* 525:    */     case '8': 
/* 526:    */     case '9': 
/* 527:493 */       matchRange('0', '9');
/* 528:494 */       break;
/* 529:    */     case 'a': 
/* 530:    */     case 'b': 
/* 531:    */     case 'c': 
/* 532:    */     case 'd': 
/* 533:    */     case 'e': 
/* 534:    */     case 'f': 
/* 535:499 */       matchRange('a', 'f');
/* 536:500 */       break;
/* 537:    */     case 'A': 
/* 538:    */     case 'B': 
/* 539:    */     case 'C': 
/* 540:    */     case 'D': 
/* 541:    */     case 'E': 
/* 542:    */     case 'F': 
/* 543:505 */       matchRange('A', 'F');
/* 544:506 */       break;
/* 545:    */     case ':': 
/* 546:    */     case ';': 
/* 547:    */     case '<': 
/* 548:    */     case '=': 
/* 549:    */     case '>': 
/* 550:    */     case '?': 
/* 551:    */     case '@': 
/* 552:    */     case 'G': 
/* 553:    */     case 'H': 
/* 554:    */     case 'I': 
/* 555:    */     case 'J': 
/* 556:    */     case 'K': 
/* 557:    */     case 'L': 
/* 558:    */     case 'M': 
/* 559:    */     case 'N': 
/* 560:    */     case 'O': 
/* 561:    */     case 'P': 
/* 562:    */     case 'Q': 
/* 563:    */     case 'R': 
/* 564:    */     case 'S': 
/* 565:    */     case 'T': 
/* 566:    */     case 'U': 
/* 567:    */     case 'V': 
/* 568:    */     case 'W': 
/* 569:    */     case 'X': 
/* 570:    */     case 'Y': 
/* 571:    */     case 'Z': 
/* 572:    */     case '[': 
/* 573:    */     case '\\': 
/* 574:    */     case ']': 
/* 575:    */     case '^': 
/* 576:    */     case '_': 
/* 577:    */     case '`': 
/* 578:    */     default: 
/* 579:510 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 580:    */     }
/* 581:513 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 582:    */     {
/* 583:514 */       localToken = makeToken(i);
/* 584:515 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 585:    */     }
/* 586:517 */     this._returnToken = localToken;
/* 587:    */   }
/* 588:    */   
/* 589:    */   public final void mID(boolean paramBoolean)
/* 590:    */     throws RecognitionException, CharStreamException, TokenStreamException
/* 591:    */   {
/* 592:521 */     Token localToken = null;int j = this.text.length();
/* 593:522 */     int i = 4;
/* 594:526 */     switch (LA(1))
/* 595:    */     {
/* 596:    */     case 'a': 
/* 597:    */     case 'b': 
/* 598:    */     case 'c': 
/* 599:    */     case 'd': 
/* 600:    */     case 'e': 
/* 601:    */     case 'f': 
/* 602:    */     case 'g': 
/* 603:    */     case 'h': 
/* 604:    */     case 'i': 
/* 605:    */     case 'j': 
/* 606:    */     case 'k': 
/* 607:    */     case 'l': 
/* 608:    */     case 'm': 
/* 609:    */     case 'n': 
/* 610:    */     case 'o': 
/* 611:    */     case 'p': 
/* 612:    */     case 'q': 
/* 613:    */     case 'r': 
/* 614:    */     case 's': 
/* 615:    */     case 't': 
/* 616:    */     case 'u': 
/* 617:    */     case 'v': 
/* 618:    */     case 'w': 
/* 619:    */     case 'x': 
/* 620:    */     case 'y': 
/* 621:    */     case 'z': 
/* 622:535 */       matchRange('a', 'z');
/* 623:536 */       break;
/* 624:    */     case 'A': 
/* 625:    */     case 'B': 
/* 626:    */     case 'C': 
/* 627:    */     case 'D': 
/* 628:    */     case 'E': 
/* 629:    */     case 'F': 
/* 630:    */     case 'G': 
/* 631:    */     case 'H': 
/* 632:    */     case 'I': 
/* 633:    */     case 'J': 
/* 634:    */     case 'K': 
/* 635:    */     case 'L': 
/* 636:    */     case 'M': 
/* 637:    */     case 'N': 
/* 638:    */     case 'O': 
/* 639:    */     case 'P': 
/* 640:    */     case 'Q': 
/* 641:    */     case 'R': 
/* 642:    */     case 'S': 
/* 643:    */     case 'T': 
/* 644:    */     case 'U': 
/* 645:    */     case 'V': 
/* 646:    */     case 'W': 
/* 647:    */     case 'X': 
/* 648:    */     case 'Y': 
/* 649:    */     case 'Z': 
/* 650:546 */       matchRange('A', 'Z');
/* 651:547 */       break;
/* 652:    */     case '[': 
/* 653:    */     case '\\': 
/* 654:    */     case ']': 
/* 655:    */     case '^': 
/* 656:    */     case '_': 
/* 657:    */     case '`': 
/* 658:    */     default: 
/* 659:551 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 660:    */     }
/* 661:    */     for (;;)
/* 662:    */     {
/* 663:558 */       switch (LA(1))
/* 664:    */       {
/* 665:    */       case 'a': 
/* 666:    */       case 'b': 
/* 667:    */       case 'c': 
/* 668:    */       case 'd': 
/* 669:    */       case 'e': 
/* 670:    */       case 'f': 
/* 671:    */       case 'g': 
/* 672:    */       case 'h': 
/* 673:    */       case 'i': 
/* 674:    */       case 'j': 
/* 675:    */       case 'k': 
/* 676:    */       case 'l': 
/* 677:    */       case 'm': 
/* 678:    */       case 'n': 
/* 679:    */       case 'o': 
/* 680:    */       case 'p': 
/* 681:    */       case 'q': 
/* 682:    */       case 'r': 
/* 683:    */       case 's': 
/* 684:    */       case 't': 
/* 685:    */       case 'u': 
/* 686:    */       case 'v': 
/* 687:    */       case 'w': 
/* 688:    */       case 'x': 
/* 689:    */       case 'y': 
/* 690:    */       case 'z': 
/* 691:567 */         matchRange('a', 'z');
/* 692:568 */         break;
/* 693:    */       case 'A': 
/* 694:    */       case 'B': 
/* 695:    */       case 'C': 
/* 696:    */       case 'D': 
/* 697:    */       case 'E': 
/* 698:    */       case 'F': 
/* 699:    */       case 'G': 
/* 700:    */       case 'H': 
/* 701:    */       case 'I': 
/* 702:    */       case 'J': 
/* 703:    */       case 'K': 
/* 704:    */       case 'L': 
/* 705:    */       case 'M': 
/* 706:    */       case 'N': 
/* 707:    */       case 'O': 
/* 708:    */       case 'P': 
/* 709:    */       case 'Q': 
/* 710:    */       case 'R': 
/* 711:    */       case 'S': 
/* 712:    */       case 'T': 
/* 713:    */       case 'U': 
/* 714:    */       case 'V': 
/* 715:    */       case 'W': 
/* 716:    */       case 'X': 
/* 717:    */       case 'Y': 
/* 718:    */       case 'Z': 
/* 719:578 */         matchRange('A', 'Z');
/* 720:579 */         break;
/* 721:    */       case '_': 
/* 722:583 */         match('_');
/* 723:584 */         break;
/* 724:    */       case '0': 
/* 725:    */       case '1': 
/* 726:    */       case '2': 
/* 727:    */       case '3': 
/* 728:    */       case '4': 
/* 729:    */       case '5': 
/* 730:    */       case '6': 
/* 731:    */       case '7': 
/* 732:    */       case '8': 
/* 733:    */       case '9': 
/* 734:590 */         matchRange('0', '9');
/* 735:    */       }
/* 736:    */     }
/* 737:600 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 738:    */     {
/* 739:601 */       localToken = makeToken(i);
/* 740:602 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 741:    */     }
/* 742:604 */     this._returnToken = localToken;
/* 743:    */   }
/* 744:    */   
/* 745:    */   public final void mINT(boolean paramBoolean)
/* 746:    */     throws RecognitionException, CharStreamException, TokenStreamException
/* 747:    */   {
/* 748:608 */     Token localToken = null;int j = this.text.length();
/* 749:609 */     int i = 9;
/* 750:    */     
/* 751:    */ 
/* 752:    */ 
/* 753:613 */     int k = 0;
/* 754:    */     for (;;)
/* 755:    */     {
/* 756:616 */       if ((LA(1) >= '0') && (LA(1) <= '9'))
/* 757:    */       {
/* 758:617 */         mDIGIT(false);
/* 759:    */       }
/* 760:    */       else
/* 761:    */       {
/* 762:620 */         if (k >= 1) {
/* 763:    */           break;
/* 764:    */         }
/* 765:620 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/* 766:    */       }
/* 767:623 */       k++;
/* 768:    */     }
/* 769:626 */     if ((paramBoolean) && (localToken == null) && (i != -1))
/* 770:    */     {
/* 771:627 */       localToken = makeToken(i);
/* 772:628 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/* 773:    */     }
/* 774:630 */     this._returnToken = localToken;
/* 775:    */   }
/* 776:    */   
/* 777:    */   private static final long[] mk_tokenSet_0()
/* 778:    */   {
/* 779:635 */     long[] arrayOfLong = new long[8];
/* 780:636 */     arrayOfLong[0] = -9224L;
/* 781:637 */     for (int i = 1; i <= 3; i++) {
/* 782:637 */       arrayOfLong[i] = -1L;
/* 783:    */     }
/* 784:638 */     return arrayOfLong;
/* 785:    */   }
/* 786:    */   
/* 787:640 */   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
/* 788:    */   
/* 789:    */   private static final long[] mk_tokenSet_1()
/* 790:    */   {
/* 791:642 */     long[] arrayOfLong = new long[8];
/* 792:643 */     arrayOfLong[0] = -140737488355336L;
/* 793:644 */     for (int i = 1; i <= 3; i++) {
/* 794:644 */       arrayOfLong[i] = -1L;
/* 795:    */     }
/* 796:645 */     return arrayOfLong;
/* 797:    */   }
/* 798:    */   
/* 799:647 */   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
/* 800:    */   
/* 801:    */   private static final long[] mk_tokenSet_2()
/* 802:    */   {
/* 803:649 */     long[] arrayOfLong = new long[8];
/* 804:650 */     arrayOfLong[0] = -4398046512136L;
/* 805:651 */     for (int i = 1; i <= 3; i++) {
/* 806:651 */       arrayOfLong[i] = -1L;
/* 807:    */     }
/* 808:652 */     return arrayOfLong;
/* 809:    */   }
/* 810:    */   
/* 811:654 */   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
/* 812:    */   
/* 813:    */   private static final long[] mk_tokenSet_3()
/* 814:    */   {
/* 815:656 */     long[] arrayOfLong = new long[8];
/* 816:657 */     arrayOfLong[0] = -17179869192L;
/* 817:658 */     arrayOfLong[1] = -268435457L;
/* 818:659 */     for (int i = 2; i <= 3; i++) {
/* 819:659 */       arrayOfLong[i] = -1L;
/* 820:    */     }
/* 821:660 */     return arrayOfLong;
/* 822:    */   }
/* 823:    */   
/* 824:662 */   public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
/* 825:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.ANTLRTokdefLexer
 * JD-Core Version:    0.7.0.1
 */