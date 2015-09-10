/*   1:    */ package javassist.compiler;
/*   2:    */ 
/*   3:    */ public class Lex
/*   4:    */   implements TokenId
/*   5:    */ {
/*   6:    */   private int lastChar;
/*   7:    */   private StringBuffer textBuffer;
/*   8:    */   private Token currentToken;
/*   9:    */   private Token lookAheadTokens;
/*  10:    */   private String input;
/*  11:    */   private int position;
/*  12:    */   private int maxlen;
/*  13:    */   private int lineNumber;
/*  14:    */   
/*  15:    */   public Lex(String s)
/*  16:    */   {
/*  17: 40 */     this.lastChar = -1;
/*  18: 41 */     this.textBuffer = new StringBuffer();
/*  19: 42 */     this.currentToken = new Token();
/*  20: 43 */     this.lookAheadTokens = null;
/*  21:    */     
/*  22: 45 */     this.input = s;
/*  23: 46 */     this.position = 0;
/*  24: 47 */     this.maxlen = s.length();
/*  25: 48 */     this.lineNumber = 0;
/*  26:    */   }
/*  27:    */   
/*  28:    */   public int get()
/*  29:    */   {
/*  30: 52 */     if (this.lookAheadTokens == null) {
/*  31: 53 */       return get(this.currentToken);
/*  32:    */     }
/*  33:    */     Token t;
/*  34: 56 */     this.currentToken = (t = this.lookAheadTokens);
/*  35: 57 */     this.lookAheadTokens = this.lookAheadTokens.next;
/*  36: 58 */     return t.tokenId;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public int lookAhead()
/*  40:    */   {
/*  41: 66 */     return lookAhead(0);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public int lookAhead(int i)
/*  45:    */   {
/*  46: 70 */     Token tk = this.lookAheadTokens;
/*  47: 71 */     if (tk == null)
/*  48:    */     {
/*  49: 72 */       this.lookAheadTokens = (tk = this.currentToken);
/*  50: 73 */       tk.next = null;
/*  51: 74 */       get(tk);
/*  52:    */     }
/*  53: 77 */     for (; i-- > 0; tk = tk.next) {
/*  54: 78 */       if (tk.next == null)
/*  55:    */       {
/*  56:    */         Token tk2;
/*  57: 80 */         tk.next = (tk2 = new Token());
/*  58: 81 */         get(tk2);
/*  59:    */       }
/*  60:    */     }
/*  61: 84 */     this.currentToken = tk;
/*  62: 85 */     return tk.tokenId;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public String getString()
/*  66:    */   {
/*  67: 89 */     return this.currentToken.textValue;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public long getLong()
/*  71:    */   {
/*  72: 93 */     return this.currentToken.longValue;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public double getDouble()
/*  76:    */   {
/*  77: 97 */     return this.currentToken.doubleValue;
/*  78:    */   }
/*  79:    */   
/*  80:    */   private int get(Token token)
/*  81:    */   {
/*  82:    */     int t;
/*  83:    */     do
/*  84:    */     {
/*  85:103 */       t = readLine(token);
/*  86:104 */     } while (t == 10);
/*  87:105 */     token.tokenId = t;
/*  88:106 */     return t;
/*  89:    */   }
/*  90:    */   
/*  91:    */   private int readLine(Token token)
/*  92:    */   {
/*  93:110 */     int c = getNextNonWhiteChar();
/*  94:111 */     if (c < 0) {
/*  95:112 */       return c;
/*  96:    */     }
/*  97:113 */     if (c == 10)
/*  98:    */     {
/*  99:114 */       this.lineNumber += 1;
/* 100:115 */       return 10;
/* 101:    */     }
/* 102:117 */     if (c == 39) {
/* 103:118 */       return readCharConst(token);
/* 104:    */     }
/* 105:119 */     if (c == 34) {
/* 106:120 */       return readStringL(token);
/* 107:    */     }
/* 108:121 */     if ((48 <= c) && (c <= 57)) {
/* 109:122 */       return readNumber(c, token);
/* 110:    */     }
/* 111:123 */     if (c == 46)
/* 112:    */     {
/* 113:124 */       c = getc();
/* 114:125 */       if ((48 <= c) && (c <= 57))
/* 115:    */       {
/* 116:126 */         StringBuffer tbuf = this.textBuffer;
/* 117:127 */         tbuf.setLength(0);
/* 118:128 */         tbuf.append('.');
/* 119:129 */         return readDouble(tbuf, c, token);
/* 120:    */       }
/* 121:132 */       ungetc(c);
/* 122:133 */       return readSeparator(46);
/* 123:    */     }
/* 124:136 */     if (Character.isJavaIdentifierStart((char)c)) {
/* 125:137 */       return readIdentifier(c, token);
/* 126:    */     }
/* 127:139 */     return readSeparator(c);
/* 128:    */   }
/* 129:    */   
/* 130:    */   private int getNextNonWhiteChar()
/* 131:    */   {
/* 132:    */     int c;
/* 133:    */     do
/* 134:    */     {
/* 135:145 */       c = getc();
/* 136:146 */       if (c == 47)
/* 137:    */       {
/* 138:147 */         c = getc();
/* 139:148 */         if (c == 47)
/* 140:    */         {
/* 141:    */           do
/* 142:    */           {
/* 143:150 */             c = getc();
/* 144:151 */             if ((c == 10) || (c == 13)) {
/* 145:    */               break;
/* 146:    */             }
/* 147:151 */           } while (c != -1);
/* 148:    */         }
/* 149:    */         else
/* 150:    */         {
/* 151:152 */           if (c == 42) {
/* 152:    */             for (;;)
/* 153:    */             {
/* 154:154 */               c = getc();
/* 155:155 */               if (c == -1) {
/* 156:    */                 break;
/* 157:    */               }
/* 158:157 */               if (c == 42)
/* 159:    */               {
/* 160:158 */                 if ((c = getc()) == 47)
/* 161:    */                 {
/* 162:159 */                   c = 32;
/* 163:160 */                   break;
/* 164:    */                 }
/* 165:163 */                 ungetc(c);
/* 166:    */               }
/* 167:    */             }
/* 168:    */           }
/* 169:166 */           ungetc(c);
/* 170:167 */           c = 47;
/* 171:    */         }
/* 172:    */       }
/* 173:170 */     } while (isBlank(c));
/* 174:171 */     return c;
/* 175:    */   }
/* 176:    */   
/* 177:    */   private int readCharConst(Token token)
/* 178:    */   {
/* 179:176 */     int value = 0;
/* 180:    */     int c;
/* 181:177 */     while ((c = getc()) != 39) {
/* 182:178 */       if (c == 92)
/* 183:    */       {
/* 184:179 */         value = readEscapeChar();
/* 185:    */       }
/* 186:    */       else
/* 187:    */       {
/* 188:180 */         if (c < 32)
/* 189:    */         {
/* 190:181 */           if (c == 10) {
/* 191:182 */             this.lineNumber += 1;
/* 192:    */           }
/* 193:184 */           return 500;
/* 194:    */         }
/* 195:187 */         value = c;
/* 196:    */       }
/* 197:    */     }
/* 198:189 */     token.longValue = value;
/* 199:190 */     return 401;
/* 200:    */   }
/* 201:    */   
/* 202:    */   private int readEscapeChar()
/* 203:    */   {
/* 204:194 */     int c = getc();
/* 205:195 */     if (c == 110) {
/* 206:196 */       c = 10;
/* 207:197 */     } else if (c == 116) {
/* 208:198 */       c = 9;
/* 209:199 */     } else if (c == 114) {
/* 210:200 */       c = 13;
/* 211:201 */     } else if (c == 102) {
/* 212:202 */       c = 12;
/* 213:203 */     } else if (c == 10) {
/* 214:204 */       this.lineNumber += 1;
/* 215:    */     }
/* 216:206 */     return c;
/* 217:    */   }
/* 218:    */   
/* 219:    */   private int readStringL(Token token)
/* 220:    */   {
/* 221:211 */     StringBuffer tbuf = this.textBuffer;
/* 222:212 */     tbuf.setLength(0);
/* 223:    */     int c;
/* 224:    */     do
/* 225:    */     {
/* 226:214 */       while ((c = getc()) != 34)
/* 227:    */       {
/* 228:215 */         if (c == 92)
/* 229:    */         {
/* 230:216 */           c = readEscapeChar();
/* 231:    */         }
/* 232:217 */         else if ((c == 10) || (c < 0))
/* 233:    */         {
/* 234:218 */           this.lineNumber += 1;
/* 235:219 */           return 500;
/* 236:    */         }
/* 237:222 */         tbuf.append((char)c);
/* 238:    */       }
/* 239:    */       for (;;)
/* 240:    */       {
/* 241:226 */         c = getc();
/* 242:227 */         if (c == 10) {
/* 243:228 */           this.lineNumber += 1;
/* 244:229 */         } else if (!isBlank(c)) {
/* 245:    */           break;
/* 246:    */         }
/* 247:    */       }
/* 248:233 */     } while (c == 34);
/* 249:234 */     ungetc(c);
/* 250:    */     
/* 251:    */ 
/* 252:    */ 
/* 253:    */ 
/* 254:239 */     token.textValue = tbuf.toString();
/* 255:240 */     return 406;
/* 256:    */   }
/* 257:    */   
/* 258:    */   private int readNumber(int c, Token token)
/* 259:    */   {
/* 260:244 */     long value = 0L;
/* 261:245 */     int c2 = getc();
/* 262:246 */     if (c == 48)
/* 263:    */     {
/* 264:247 */       if ((c2 == 88) || (c2 == 120))
/* 265:    */       {
/* 266:    */         for (;;)
/* 267:    */         {
/* 268:249 */           c = getc();
/* 269:250 */           if ((48 <= c) && (c <= 57))
/* 270:    */           {
/* 271:251 */             value = value * 16L + (c - 48);
/* 272:    */           }
/* 273:252 */           else if ((65 <= c) && (c <= 70))
/* 274:    */           {
/* 275:253 */             value = value * 16L + (c - 65 + 10);
/* 276:    */           }
/* 277:    */           else
/* 278:    */           {
/* 279:254 */             if ((97 > c) || (c > 102)) {
/* 280:    */               break;
/* 281:    */             }
/* 282:255 */             value = value * 16L + (c - 97 + 10);
/* 283:    */           }
/* 284:    */         }
/* 285:257 */         token.longValue = value;
/* 286:258 */         if ((c == 76) || (c == 108)) {
/* 287:259 */           return 403;
/* 288:    */         }
/* 289:261 */         ungetc(c);
/* 290:262 */         return 402;
/* 291:    */       }
/* 292:266 */       if ((48 <= c2) && (c2 <= 55))
/* 293:    */       {
/* 294:267 */         value = c2 - 48;
/* 295:    */         for (;;)
/* 296:    */         {
/* 297:269 */           c = getc();
/* 298:270 */           if ((48 > c) || (c > 55)) {
/* 299:    */             break;
/* 300:    */           }
/* 301:271 */           value = value * 8L + (c - 48);
/* 302:    */         }
/* 303:273 */         token.longValue = value;
/* 304:274 */         if ((c == 76) || (c == 108)) {
/* 305:275 */           return 403;
/* 306:    */         }
/* 307:277 */         ungetc(c);
/* 308:278 */         return 402;
/* 309:    */       }
/* 310:    */     }
/* 311:284 */     value = c - 48;
/* 312:285 */     while ((48 <= c2) && (c2 <= 57))
/* 313:    */     {
/* 314:286 */       value = value * 10L + c2 - 48L;
/* 315:287 */       c2 = getc();
/* 316:    */     }
/* 317:290 */     token.longValue = value;
/* 318:291 */     if ((c2 == 70) || (c2 == 102))
/* 319:    */     {
/* 320:292 */       token.doubleValue = value;
/* 321:293 */       return 404;
/* 322:    */     }
/* 323:295 */     if ((c2 == 69) || (c2 == 101) || (c2 == 68) || (c2 == 100) || (c2 == 46))
/* 324:    */     {
/* 325:297 */       StringBuffer tbuf = this.textBuffer;
/* 326:298 */       tbuf.setLength(0);
/* 327:299 */       tbuf.append(value);
/* 328:300 */       return readDouble(tbuf, c2, token);
/* 329:    */     }
/* 330:302 */     if ((c2 == 76) || (c2 == 108)) {
/* 331:303 */       return 403;
/* 332:    */     }
/* 333:305 */     ungetc(c2);
/* 334:306 */     return 402;
/* 335:    */   }
/* 336:    */   
/* 337:    */   private int readDouble(StringBuffer sbuf, int c, Token token)
/* 338:    */   {
/* 339:311 */     if ((c != 69) && (c != 101) && (c != 68) && (c != 100))
/* 340:    */     {
/* 341:312 */       sbuf.append((char)c);
/* 342:    */       for (;;)
/* 343:    */       {
/* 344:314 */         c = getc();
/* 345:315 */         if ((48 > c) || (c > 57)) {
/* 346:    */           break;
/* 347:    */         }
/* 348:316 */         sbuf.append((char)c);
/* 349:    */       }
/* 350:    */     }
/* 351:322 */     if ((c == 69) || (c == 101))
/* 352:    */     {
/* 353:323 */       sbuf.append((char)c);
/* 354:324 */       c = getc();
/* 355:325 */       if ((c == 43) || (c == 45))
/* 356:    */       {
/* 357:326 */         sbuf.append((char)c);
/* 358:327 */         c = getc();
/* 359:    */       }
/* 360:330 */       while ((48 <= c) && (c <= 57))
/* 361:    */       {
/* 362:331 */         sbuf.append((char)c);
/* 363:332 */         c = getc();
/* 364:    */       }
/* 365:    */     }
/* 366:    */     try
/* 367:    */     {
/* 368:337 */       token.doubleValue = Double.parseDouble(sbuf.toString());
/* 369:    */     }
/* 370:    */     catch (NumberFormatException e)
/* 371:    */     {
/* 372:340 */       return 500;
/* 373:    */     }
/* 374:343 */     if ((c == 70) || (c == 102)) {
/* 375:344 */       return 404;
/* 376:    */     }
/* 377:346 */     if ((c != 68) && (c != 100)) {
/* 378:347 */       ungetc(c);
/* 379:    */     }
/* 380:349 */     return 405;
/* 381:    */   }
/* 382:    */   
/* 383:354 */   private static final int[] equalOps = { 350, 0, 0, 0, 351, 352, 0, 0, 0, 353, 354, 0, 355, 0, 356, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 357, 358, 359, 0 };
/* 384:    */   
/* 385:    */   private int readSeparator(int c)
/* 386:    */   {
/* 387:362 */     if ((33 <= c) && (c <= 63))
/* 388:    */     {
/* 389:363 */       int t = equalOps[(c - 33)];
/* 390:364 */       if (t == 0) {
/* 391:365 */         return c;
/* 392:    */       }
/* 393:367 */       int c2 = getc();
/* 394:368 */       if (c == c2)
/* 395:    */       {
/* 396:    */         int c3;
/* 397:369 */         switch (c)
/* 398:    */         {
/* 399:    */         case 61: 
/* 400:371 */           return 358;
/* 401:    */         case 43: 
/* 402:373 */           return 362;
/* 403:    */         case 45: 
/* 404:375 */           return 363;
/* 405:    */         case 38: 
/* 406:377 */           return 369;
/* 407:    */         case 60: 
/* 408:379 */           c3 = getc();
/* 409:380 */           if (c3 == 61) {
/* 410:381 */             return 365;
/* 411:    */           }
/* 412:383 */           ungetc(c3);
/* 413:384 */           return 364;
/* 414:    */         case 62: 
/* 415:387 */           c3 = getc();
/* 416:388 */           if (c3 == 61) {
/* 417:389 */             return 367;
/* 418:    */           }
/* 419:390 */           if (c3 == 62)
/* 420:    */           {
/* 421:391 */             c3 = getc();
/* 422:392 */             if (c3 == 61) {
/* 423:393 */               return 371;
/* 424:    */             }
/* 425:395 */             ungetc(c3);
/* 426:396 */             return 370;
/* 427:    */           }
/* 428:400 */           ungetc(c3);
/* 429:401 */           return 366;
/* 430:    */         }
/* 431:    */       }
/* 432:406 */       else if (c2 == 61)
/* 433:    */       {
/* 434:407 */         return t;
/* 435:    */       }
/* 436:    */     }
/* 437:410 */     else if (c == 94)
/* 438:    */     {
/* 439:411 */       int c2 = getc();
/* 440:412 */       if (c2 == 61) {
/* 441:413 */         return 360;
/* 442:    */       }
/* 443:    */     }
/* 444:415 */     else if (c == 124)
/* 445:    */     {
/* 446:416 */       int c2 = getc();
/* 447:417 */       if (c2 == 61) {
/* 448:418 */         return 361;
/* 449:    */       }
/* 450:419 */       if (c2 == 124) {
/* 451:420 */         return 368;
/* 452:    */       }
/* 453:    */     }
/* 454:    */     else
/* 455:    */     {
/* 456:423 */       return c;
/* 457:    */     }
/* 458:    */     int c2;
/* 459:425 */     ungetc(c2);
/* 460:426 */     return c;
/* 461:    */   }
/* 462:    */   
/* 463:    */   private int readIdentifier(int c, Token token)
/* 464:    */   {
/* 465:430 */     StringBuffer tbuf = this.textBuffer;
/* 466:431 */     tbuf.setLength(0);
/* 467:    */     do
/* 468:    */     {
/* 469:434 */       tbuf.append((char)c);
/* 470:435 */       c = getc();
/* 471:436 */     } while (Character.isJavaIdentifierPart((char)c));
/* 472:438 */     ungetc(c);
/* 473:    */     
/* 474:440 */     String name = tbuf.toString();
/* 475:441 */     int t = ktable.lookup(name);
/* 476:442 */     if (t >= 0) {
/* 477:443 */       return t;
/* 478:    */     }
/* 479:452 */     token.textValue = name;
/* 480:453 */     return 400;
/* 481:    */   }
/* 482:    */   
/* 483:457 */   private static final KeywordTable ktable = new KeywordTable();
/* 484:    */   
/* 485:    */   static
/* 486:    */   {
/* 487:460 */     ktable.append("abstract", 300);
/* 488:461 */     ktable.append("boolean", 301);
/* 489:462 */     ktable.append("break", 302);
/* 490:463 */     ktable.append("byte", 303);
/* 491:464 */     ktable.append("case", 304);
/* 492:465 */     ktable.append("catch", 305);
/* 493:466 */     ktable.append("char", 306);
/* 494:467 */     ktable.append("class", 307);
/* 495:468 */     ktable.append("const", 308);
/* 496:469 */     ktable.append("continue", 309);
/* 497:470 */     ktable.append("default", 310);
/* 498:471 */     ktable.append("do", 311);
/* 499:472 */     ktable.append("double", 312);
/* 500:473 */     ktable.append("else", 313);
/* 501:474 */     ktable.append("extends", 314);
/* 502:475 */     ktable.append("false", 411);
/* 503:476 */     ktable.append("final", 315);
/* 504:477 */     ktable.append("finally", 316);
/* 505:478 */     ktable.append("float", 317);
/* 506:479 */     ktable.append("for", 318);
/* 507:480 */     ktable.append("goto", 319);
/* 508:481 */     ktable.append("if", 320);
/* 509:482 */     ktable.append("implements", 321);
/* 510:483 */     ktable.append("import", 322);
/* 511:484 */     ktable.append("instanceof", 323);
/* 512:485 */     ktable.append("int", 324);
/* 513:486 */     ktable.append("interface", 325);
/* 514:487 */     ktable.append("long", 326);
/* 515:488 */     ktable.append("native", 327);
/* 516:489 */     ktable.append("new", 328);
/* 517:490 */     ktable.append("null", 412);
/* 518:491 */     ktable.append("package", 329);
/* 519:492 */     ktable.append("private", 330);
/* 520:493 */     ktable.append("protected", 331);
/* 521:494 */     ktable.append("public", 332);
/* 522:495 */     ktable.append("return", 333);
/* 523:496 */     ktable.append("short", 334);
/* 524:497 */     ktable.append("static", 335);
/* 525:498 */     ktable.append("strictfp", 347);
/* 526:499 */     ktable.append("super", 336);
/* 527:500 */     ktable.append("switch", 337);
/* 528:501 */     ktable.append("synchronized", 338);
/* 529:502 */     ktable.append("this", 339);
/* 530:503 */     ktable.append("throw", 340);
/* 531:504 */     ktable.append("throws", 341);
/* 532:505 */     ktable.append("transient", 342);
/* 533:506 */     ktable.append("true", 410);
/* 534:507 */     ktable.append("try", 343);
/* 535:508 */     ktable.append("void", 344);
/* 536:509 */     ktable.append("volatile", 345);
/* 537:510 */     ktable.append("while", 346);
/* 538:    */   }
/* 539:    */   
/* 540:    */   private static boolean isBlank(int c)
/* 541:    */   {
/* 542:514 */     return (c == 32) || (c == 9) || (c == 12) || (c == 13) || (c == 10);
/* 543:    */   }
/* 544:    */   
/* 545:    */   private static boolean isDigit(int c)
/* 546:    */   {
/* 547:519 */     return (48 <= c) && (c <= 57);
/* 548:    */   }
/* 549:    */   
/* 550:    */   private void ungetc(int c)
/* 551:    */   {
/* 552:523 */     this.lastChar = c;
/* 553:    */   }
/* 554:    */   
/* 555:    */   public String getTextAround()
/* 556:    */   {
/* 557:527 */     int begin = this.position - 10;
/* 558:528 */     if (begin < 0) {
/* 559:529 */       begin = 0;
/* 560:    */     }
/* 561:531 */     int end = this.position + 10;
/* 562:532 */     if (end > this.maxlen) {
/* 563:533 */       end = this.maxlen;
/* 564:    */     }
/* 565:535 */     return this.input.substring(begin, end);
/* 566:    */   }
/* 567:    */   
/* 568:    */   private int getc()
/* 569:    */   {
/* 570:539 */     if (this.lastChar < 0)
/* 571:    */     {
/* 572:540 */       if (this.position < this.maxlen) {
/* 573:541 */         return this.input.charAt(this.position++);
/* 574:    */       }
/* 575:543 */       return -1;
/* 576:    */     }
/* 577:545 */     int c = this.lastChar;
/* 578:546 */     this.lastChar = -1;
/* 579:547 */     return c;
/* 580:    */   }
/* 581:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javassist.compiler.Lex
 * JD-Core Version:    0.7.0.1
 */