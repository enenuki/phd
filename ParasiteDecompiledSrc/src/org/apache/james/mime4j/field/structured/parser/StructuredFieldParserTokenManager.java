/*   1:    */ package org.apache.james.mime4j.field.structured.parser;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.PrintStream;
/*   5:    */ 
/*   6:    */ public class StructuredFieldParserTokenManager
/*   7:    */   implements StructuredFieldParserConstants
/*   8:    */ {
/*   9:    */   int commentNest;
/*  10: 31 */   public PrintStream debugStream = System.out;
/*  11:    */   
/*  12:    */   public void setDebugStream(PrintStream ds)
/*  13:    */   {
/*  14: 32 */     this.debugStream = ds;
/*  15:    */   }
/*  16:    */   
/*  17:    */   private final int jjStopStringLiteralDfa_0(int pos, long active0)
/*  18:    */   {
/*  19: 35 */     switch (pos)
/*  20:    */     {
/*  21:    */     }
/*  22: 38 */     return -1;
/*  23:    */   }
/*  24:    */   
/*  25:    */   private final int jjStartNfa_0(int pos, long active0)
/*  26:    */   {
/*  27: 43 */     return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
/*  28:    */   }
/*  29:    */   
/*  30:    */   private final int jjStopAtPos(int pos, int kind)
/*  31:    */   {
/*  32: 47 */     this.jjmatchedKind = kind;
/*  33: 48 */     this.jjmatchedPos = pos;
/*  34: 49 */     return pos + 1;
/*  35:    */   }
/*  36:    */   
/*  37:    */   private final int jjStartNfaWithStates_0(int pos, int kind, int state)
/*  38:    */   {
/*  39: 53 */     this.jjmatchedKind = kind;
/*  40: 54 */     this.jjmatchedPos = pos;
/*  41:    */     try
/*  42:    */     {
/*  43: 55 */       this.curChar = this.input_stream.readChar();
/*  44:    */     }
/*  45:    */     catch (IOException e)
/*  46:    */     {
/*  47: 56 */       return pos + 1;
/*  48:    */     }
/*  49: 57 */     return jjMoveNfa_0(state, pos + 1);
/*  50:    */   }
/*  51:    */   
/*  52:    */   private final int jjMoveStringLiteralDfa0_0()
/*  53:    */   {
/*  54: 61 */     switch (this.curChar)
/*  55:    */     {
/*  56:    */     case '"': 
/*  57: 64 */       return jjStopAtPos(0, 9);
/*  58:    */     case '(': 
/*  59: 66 */       return jjStopAtPos(0, 1);
/*  60:    */     }
/*  61: 68 */     return jjMoveNfa_0(2, 0);
/*  62:    */   }
/*  63:    */   
/*  64:    */   private final void jjCheckNAdd(int state)
/*  65:    */   {
/*  66: 73 */     if (this.jjrounds[state] != this.jjround)
/*  67:    */     {
/*  68: 75 */       this.jjstateSet[(this.jjnewStateCnt++)] = state;
/*  69: 76 */       this.jjrounds[state] = this.jjround;
/*  70:    */     }
/*  71:    */   }
/*  72:    */   
/*  73:    */   private final void jjAddStates(int start, int end)
/*  74:    */   {
/*  75:    */     do
/*  76:    */     {
/*  77: 82 */       this.jjstateSet[(this.jjnewStateCnt++)] = jjnextStates[start];
/*  78: 83 */     } while (start++ != end);
/*  79:    */   }
/*  80:    */   
/*  81:    */   private final void jjCheckNAddTwoStates(int state1, int state2)
/*  82:    */   {
/*  83: 87 */     jjCheckNAdd(state1);
/*  84: 88 */     jjCheckNAdd(state2);
/*  85:    */   }
/*  86:    */   
/*  87:    */   private final void jjCheckNAddStates(int start, int end)
/*  88:    */   {
/*  89:    */     do
/*  90:    */     {
/*  91: 93 */       jjCheckNAdd(jjnextStates[start]);
/*  92: 94 */     } while (start++ != end);
/*  93:    */   }
/*  94:    */   
/*  95:    */   private final void jjCheckNAddStates(int start)
/*  96:    */   {
/*  97: 98 */     jjCheckNAdd(jjnextStates[start]);
/*  98: 99 */     jjCheckNAdd(jjnextStates[(start + 1)]);
/*  99:    */   }
/* 100:    */   
/* 101:101 */   static final long[] jjbitVec0 = { 0L, 0L, -1L, -1L };
/* 102:    */   
/* 103:    */   private final int jjMoveNfa_0(int startState, int curPos)
/* 104:    */   {
/* 105:107 */     int startsAt = 0;
/* 106:108 */     this.jjnewStateCnt = 2;
/* 107:109 */     int i = 1;
/* 108:110 */     this.jjstateSet[0] = startState;
/* 109:111 */     int kind = 2147483647;
/* 110:    */     for (;;)
/* 111:    */     {
/* 112:114 */       if (++this.jjround == 2147483647) {
/* 113:115 */         ReInitRounds();
/* 114:    */       }
/* 115:116 */       if (this.curChar < '@')
/* 116:    */       {
/* 117:118 */         long l = 1L << this.curChar;
/* 118:    */         do
/* 119:    */         {
/* 120:121 */           switch (this.jjstateSet[(--i)])
/* 121:    */           {
/* 122:    */           case 2: 
/* 123:124 */             if ((0xFFFFD9FF & l) != 0L)
/* 124:    */             {
/* 125:126 */               if (kind > 15) {
/* 126:127 */                 kind = 15;
/* 127:    */               }
/* 128:128 */               jjCheckNAdd(1);
/* 129:    */             }
/* 130:130 */             else if ((0x2600 & l) != 0L)
/* 131:    */             {
/* 132:132 */               if (kind > 14) {
/* 133:133 */                 kind = 14;
/* 134:    */               }
/* 135:134 */               jjCheckNAdd(0);
/* 136:    */             }
/* 137:    */             break;
/* 138:    */           case 0: 
/* 139:138 */             if ((0x2600 & l) != 0L)
/* 140:    */             {
/* 141:140 */               kind = 14;
/* 142:141 */               jjCheckNAdd(0);
/* 143:    */             }
/* 144:142 */             break;
/* 145:    */           case 1: 
/* 146:144 */             if ((0xFFFFD9FF & l) != 0L)
/* 147:    */             {
/* 148:146 */               kind = 15;
/* 149:147 */               jjCheckNAdd(1);
/* 150:    */             }
/* 151:    */             break;
/* 152:    */           }
/* 153:151 */         } while (i != startsAt);
/* 154:    */       }
/* 155:153 */       else if (this.curChar < '')
/* 156:    */       {
/* 157:155 */         long l = 1L << (this.curChar & 0x3F);
/* 158:    */         do
/* 159:    */         {
/* 160:158 */           switch (this.jjstateSet[(--i)])
/* 161:    */           {
/* 162:    */           case 1: 
/* 163:    */           case 2: 
/* 164:162 */             kind = 15;
/* 165:163 */             jjCheckNAdd(1);
/* 166:    */           }
/* 167:167 */         } while (i != startsAt);
/* 168:    */       }
/* 169:    */       else
/* 170:    */       {
/* 171:171 */         int i2 = (this.curChar & 0xFF) >> '\006';
/* 172:172 */         long l2 = 1L << (this.curChar & 0x3F);
/* 173:    */         do
/* 174:    */         {
/* 175:175 */           switch (this.jjstateSet[(--i)])
/* 176:    */           {
/* 177:    */           case 1: 
/* 178:    */           case 2: 
/* 179:179 */             if ((jjbitVec0[i2] & l2) != 0L)
/* 180:    */             {
/* 181:181 */               if (kind > 15) {
/* 182:182 */                 kind = 15;
/* 183:    */               }
/* 184:183 */               jjCheckNAdd(1);
/* 185:    */             }
/* 186:    */             break;
/* 187:    */           }
/* 188:187 */         } while (i != startsAt);
/* 189:    */       }
/* 190:189 */       if (kind != 2147483647)
/* 191:    */       {
/* 192:191 */         this.jjmatchedKind = kind;
/* 193:192 */         this.jjmatchedPos = curPos;
/* 194:193 */         kind = 2147483647;
/* 195:    */       }
/* 196:195 */       curPos++;
/* 197:196 */       if ((i = this.jjnewStateCnt) == (startsAt = 2 - (this.jjnewStateCnt = startsAt))) {
/* 198:197 */         return curPos;
/* 199:    */       }
/* 200:    */       try
/* 201:    */       {
/* 202:198 */         this.curChar = this.input_stream.readChar();
/* 203:    */       }
/* 204:    */       catch (IOException e) {}
/* 205:    */     }
/* 206:199 */     return curPos;
/* 207:    */   }
/* 208:    */   
/* 209:    */   private final int jjStopStringLiteralDfa_1(int pos, long active0)
/* 210:    */   {
/* 211:204 */     switch (pos)
/* 212:    */     {
/* 213:    */     }
/* 214:207 */     return -1;
/* 215:    */   }
/* 216:    */   
/* 217:    */   private final int jjStartNfa_1(int pos, long active0)
/* 218:    */   {
/* 219:212 */     return jjMoveNfa_1(jjStopStringLiteralDfa_1(pos, active0), pos + 1);
/* 220:    */   }
/* 221:    */   
/* 222:    */   private final int jjStartNfaWithStates_1(int pos, int kind, int state)
/* 223:    */   {
/* 224:216 */     this.jjmatchedKind = kind;
/* 225:217 */     this.jjmatchedPos = pos;
/* 226:    */     try
/* 227:    */     {
/* 228:218 */       this.curChar = this.input_stream.readChar();
/* 229:    */     }
/* 230:    */     catch (IOException e)
/* 231:    */     {
/* 232:219 */       return pos + 1;
/* 233:    */     }
/* 234:220 */     return jjMoveNfa_1(state, pos + 1);
/* 235:    */   }
/* 236:    */   
/* 237:    */   private final int jjMoveStringLiteralDfa0_1()
/* 238:    */   {
/* 239:224 */     switch (this.curChar)
/* 240:    */     {
/* 241:    */     case '(': 
/* 242:227 */       return jjStopAtPos(0, 3);
/* 243:    */     case ')': 
/* 244:229 */       return jjStopAtPos(0, 2);
/* 245:    */     }
/* 246:231 */     return jjMoveNfa_1(0, 0);
/* 247:    */   }
/* 248:    */   
/* 249:    */   private final int jjMoveNfa_1(int startState, int curPos)
/* 250:    */   {
/* 251:237 */     int startsAt = 0;
/* 252:238 */     this.jjnewStateCnt = 1;
/* 253:239 */     int i = 1;
/* 254:240 */     this.jjstateSet[0] = startState;
/* 255:241 */     int kind = 2147483647;
/* 256:    */     for (;;)
/* 257:    */     {
/* 258:244 */       if (++this.jjround == 2147483647) {
/* 259:245 */         ReInitRounds();
/* 260:    */       }
/* 261:246 */       if (this.curChar < '@')
/* 262:    */       {
/* 263:248 */         long l = 1L << this.curChar;
/* 264:    */         do
/* 265:    */         {
/* 266:251 */           switch (this.jjstateSet[(--i)])
/* 267:    */           {
/* 268:    */           case 0: 
/* 269:254 */             if ((0xFFFFFFFF & l) != 0L) {
/* 270:255 */               kind = 4;
/* 271:    */             }
/* 272:    */             break;
/* 273:    */           }
/* 274:259 */         } while (i != startsAt);
/* 275:    */       }
/* 276:261 */       else if (this.curChar < '')
/* 277:    */       {
/* 278:263 */         long l = 1L << (this.curChar & 0x3F);
/* 279:    */         do
/* 280:    */         {
/* 281:266 */           switch (this.jjstateSet[(--i)])
/* 282:    */           {
/* 283:    */           case 0: 
/* 284:269 */             kind = 4;
/* 285:    */           }
/* 286:273 */         } while (i != startsAt);
/* 287:    */       }
/* 288:    */       else
/* 289:    */       {
/* 290:277 */         int i2 = (this.curChar & 0xFF) >> '\006';
/* 291:278 */         long l2 = 1L << (this.curChar & 0x3F);
/* 292:    */         do
/* 293:    */         {
/* 294:281 */           switch (this.jjstateSet[(--i)])
/* 295:    */           {
/* 296:    */           case 0: 
/* 297:284 */             if (((jjbitVec0[i2] & l2) != 0L) && (kind > 4)) {
/* 298:285 */               kind = 4;
/* 299:    */             }
/* 300:    */             break;
/* 301:    */           }
/* 302:289 */         } while (i != startsAt);
/* 303:    */       }
/* 304:291 */       if (kind != 2147483647)
/* 305:    */       {
/* 306:293 */         this.jjmatchedKind = kind;
/* 307:294 */         this.jjmatchedPos = curPos;
/* 308:295 */         kind = 2147483647;
/* 309:    */       }
/* 310:297 */       curPos++;
/* 311:298 */       if ((i = this.jjnewStateCnt) == (startsAt = 1 - (this.jjnewStateCnt = startsAt))) {
/* 312:299 */         return curPos;
/* 313:    */       }
/* 314:    */       try
/* 315:    */       {
/* 316:300 */         this.curChar = this.input_stream.readChar();
/* 317:    */       }
/* 318:    */       catch (IOException e) {}
/* 319:    */     }
/* 320:301 */     return curPos;
/* 321:    */   }
/* 322:    */   
/* 323:    */   private final int jjStopStringLiteralDfa_3(int pos, long active0)
/* 324:    */   {
/* 325:306 */     switch (pos)
/* 326:    */     {
/* 327:    */     }
/* 328:309 */     return -1;
/* 329:    */   }
/* 330:    */   
/* 331:    */   private final int jjStartNfa_3(int pos, long active0)
/* 332:    */   {
/* 333:314 */     return jjMoveNfa_3(jjStopStringLiteralDfa_3(pos, active0), pos + 1);
/* 334:    */   }
/* 335:    */   
/* 336:    */   private final int jjStartNfaWithStates_3(int pos, int kind, int state)
/* 337:    */   {
/* 338:318 */     this.jjmatchedKind = kind;
/* 339:319 */     this.jjmatchedPos = pos;
/* 340:    */     try
/* 341:    */     {
/* 342:320 */       this.curChar = this.input_stream.readChar();
/* 343:    */     }
/* 344:    */     catch (IOException e)
/* 345:    */     {
/* 346:321 */       return pos + 1;
/* 347:    */     }
/* 348:322 */     return jjMoveNfa_3(state, pos + 1);
/* 349:    */   }
/* 350:    */   
/* 351:    */   private final int jjMoveStringLiteralDfa0_3()
/* 352:    */   {
/* 353:326 */     switch (this.curChar)
/* 354:    */     {
/* 355:    */     case '"': 
/* 356:329 */       return jjStopAtPos(0, 13);
/* 357:    */     }
/* 358:331 */     return jjMoveNfa_3(0, 0);
/* 359:    */   }
/* 360:    */   
/* 361:    */   private final int jjMoveNfa_3(int startState, int curPos)
/* 362:    */   {
/* 363:337 */     int startsAt = 0;
/* 364:338 */     this.jjnewStateCnt = 6;
/* 365:339 */     int i = 1;
/* 366:340 */     this.jjstateSet[0] = startState;
/* 367:341 */     int kind = 2147483647;
/* 368:    */     for (;;)
/* 369:    */     {
/* 370:344 */       if (++this.jjround == 2147483647) {
/* 371:345 */         ReInitRounds();
/* 372:    */       }
/* 373:346 */       if (this.curChar < '@')
/* 374:    */       {
/* 375:348 */         long l = 1L << this.curChar;
/* 376:    */         do
/* 377:    */         {
/* 378:351 */           switch (this.jjstateSet[(--i)])
/* 379:    */           {
/* 380:    */           case 0: 
/* 381:354 */             if ((0xFFFFDFFF & l) != 0L)
/* 382:    */             {
/* 383:356 */               if (kind > 11) {
/* 384:357 */                 kind = 11;
/* 385:    */               }
/* 386:358 */               jjCheckNAdd(2);
/* 387:    */             }
/* 388:360 */             else if (this.curChar == '\r')
/* 389:    */             {
/* 390:361 */               this.jjstateSet[(this.jjnewStateCnt++)] = 3;
/* 391:    */             }
/* 392:    */             break;
/* 393:    */           case 1: 
/* 394:364 */             if (kind > 10) {
/* 395:365 */               kind = 10;
/* 396:    */             }
/* 397:366 */             this.jjstateSet[(this.jjnewStateCnt++)] = 1;
/* 398:367 */             break;
/* 399:    */           case 2: 
/* 400:369 */             if ((0xFFFFDFFF & l) != 0L)
/* 401:    */             {
/* 402:371 */               if (kind > 11) {
/* 403:372 */                 kind = 11;
/* 404:    */               }
/* 405:373 */               jjCheckNAdd(2);
/* 406:    */             }
/* 407:374 */             break;
/* 408:    */           case 3: 
/* 409:376 */             if (this.curChar == '\n')
/* 410:    */             {
/* 411:378 */               if (kind > 12) {
/* 412:379 */                 kind = 12;
/* 413:    */               }
/* 414:380 */               jjCheckNAdd(4);
/* 415:    */             }
/* 416:381 */             break;
/* 417:    */           case 4: 
/* 418:383 */             if ((0x200 & l) != 0L)
/* 419:    */             {
/* 420:385 */               if (kind > 12) {
/* 421:386 */                 kind = 12;
/* 422:    */               }
/* 423:387 */               jjCheckNAdd(4);
/* 424:    */             }
/* 425:388 */             break;
/* 426:    */           case 5: 
/* 427:390 */             if (this.curChar == '\r') {
/* 428:391 */               this.jjstateSet[(this.jjnewStateCnt++)] = 3;
/* 429:    */             }
/* 430:    */             break;
/* 431:    */           }
/* 432:395 */         } while (i != startsAt);
/* 433:    */       }
/* 434:397 */       else if (this.curChar < '')
/* 435:    */       {
/* 436:399 */         long l = 1L << (this.curChar & 0x3F);
/* 437:    */         do
/* 438:    */         {
/* 439:402 */           switch (this.jjstateSet[(--i)])
/* 440:    */           {
/* 441:    */           case 0: 
/* 442:405 */             if ((0xEFFFFFFF & l) != 0L)
/* 443:    */             {
/* 444:407 */               if (kind > 11) {
/* 445:408 */                 kind = 11;
/* 446:    */               }
/* 447:409 */               jjCheckNAdd(2);
/* 448:    */             }
/* 449:411 */             else if (this.curChar == '\\')
/* 450:    */             {
/* 451:412 */               jjCheckNAdd(1);
/* 452:    */             }
/* 453:    */             break;
/* 454:    */           case 1: 
/* 455:415 */             if (kind > 10) {
/* 456:416 */               kind = 10;
/* 457:    */             }
/* 458:417 */             jjCheckNAdd(1);
/* 459:418 */             break;
/* 460:    */           case 2: 
/* 461:420 */             if ((0xEFFFFFFF & l) != 0L)
/* 462:    */             {
/* 463:422 */               if (kind > 11) {
/* 464:423 */                 kind = 11;
/* 465:    */               }
/* 466:424 */               jjCheckNAdd(2);
/* 467:    */             }
/* 468:    */             break;
/* 469:    */           }
/* 470:428 */         } while (i != startsAt);
/* 471:    */       }
/* 472:    */       else
/* 473:    */       {
/* 474:432 */         int i2 = (this.curChar & 0xFF) >> '\006';
/* 475:433 */         long l2 = 1L << (this.curChar & 0x3F);
/* 476:    */         do
/* 477:    */         {
/* 478:436 */           switch (this.jjstateSet[(--i)])
/* 479:    */           {
/* 480:    */           case 0: 
/* 481:    */           case 2: 
/* 482:440 */             if ((jjbitVec0[i2] & l2) != 0L)
/* 483:    */             {
/* 484:442 */               if (kind > 11) {
/* 485:443 */                 kind = 11;
/* 486:    */               }
/* 487:444 */               jjCheckNAdd(2);
/* 488:    */             }
/* 489:445 */             break;
/* 490:    */           case 1: 
/* 491:447 */             if ((jjbitVec0[i2] & l2) != 0L)
/* 492:    */             {
/* 493:449 */               if (kind > 10) {
/* 494:450 */                 kind = 10;
/* 495:    */               }
/* 496:451 */               this.jjstateSet[(this.jjnewStateCnt++)] = 1;
/* 497:    */             }
/* 498:    */             break;
/* 499:    */           }
/* 500:455 */         } while (i != startsAt);
/* 501:    */       }
/* 502:457 */       if (kind != 2147483647)
/* 503:    */       {
/* 504:459 */         this.jjmatchedKind = kind;
/* 505:460 */         this.jjmatchedPos = curPos;
/* 506:461 */         kind = 2147483647;
/* 507:    */       }
/* 508:463 */       curPos++;
/* 509:464 */       if ((i = this.jjnewStateCnt) == (startsAt = 6 - (this.jjnewStateCnt = startsAt))) {
/* 510:465 */         return curPos;
/* 511:    */       }
/* 512:    */       try
/* 513:    */       {
/* 514:466 */         this.curChar = this.input_stream.readChar();
/* 515:    */       }
/* 516:    */       catch (IOException e) {}
/* 517:    */     }
/* 518:467 */     return curPos;
/* 519:    */   }
/* 520:    */   
/* 521:    */   private final int jjStopStringLiteralDfa_2(int pos, long active0)
/* 522:    */   {
/* 523:472 */     switch (pos)
/* 524:    */     {
/* 525:    */     }
/* 526:475 */     return -1;
/* 527:    */   }
/* 528:    */   
/* 529:    */   private final int jjStartNfa_2(int pos, long active0)
/* 530:    */   {
/* 531:480 */     return jjMoveNfa_2(jjStopStringLiteralDfa_2(pos, active0), pos + 1);
/* 532:    */   }
/* 533:    */   
/* 534:    */   private final int jjStartNfaWithStates_2(int pos, int kind, int state)
/* 535:    */   {
/* 536:484 */     this.jjmatchedKind = kind;
/* 537:485 */     this.jjmatchedPos = pos;
/* 538:    */     try
/* 539:    */     {
/* 540:486 */       this.curChar = this.input_stream.readChar();
/* 541:    */     }
/* 542:    */     catch (IOException e)
/* 543:    */     {
/* 544:487 */       return pos + 1;
/* 545:    */     }
/* 546:488 */     return jjMoveNfa_2(state, pos + 1);
/* 547:    */   }
/* 548:    */   
/* 549:    */   private final int jjMoveStringLiteralDfa0_2()
/* 550:    */   {
/* 551:492 */     switch (this.curChar)
/* 552:    */     {
/* 553:    */     case '(': 
/* 554:495 */       return jjStopAtPos(0, 5);
/* 555:    */     case ')': 
/* 556:497 */       return jjStopAtPos(0, 6);
/* 557:    */     }
/* 558:499 */     return jjMoveNfa_2(0, 0);
/* 559:    */   }
/* 560:    */   
/* 561:    */   private final int jjMoveNfa_2(int startState, int curPos)
/* 562:    */   {
/* 563:505 */     int startsAt = 0;
/* 564:506 */     this.jjnewStateCnt = 3;
/* 565:507 */     int i = 1;
/* 566:508 */     this.jjstateSet[0] = startState;
/* 567:509 */     int kind = 2147483647;
/* 568:    */     for (;;)
/* 569:    */     {
/* 570:512 */       if (++this.jjround == 2147483647) {
/* 571:513 */         ReInitRounds();
/* 572:    */       }
/* 573:514 */       if (this.curChar < '@')
/* 574:    */       {
/* 575:516 */         long l = 1L << this.curChar;
/* 576:    */         do
/* 577:    */         {
/* 578:519 */           switch (this.jjstateSet[(--i)])
/* 579:    */           {
/* 580:    */           case 0: 
/* 581:522 */             if (((0xFFFFFFFF & l) != 0L) && (kind > 8)) {
/* 582:523 */               kind = 8;
/* 583:    */             }
/* 584:    */             break;
/* 585:    */           case 1: 
/* 586:526 */             if (kind > 7) {
/* 587:527 */               kind = 7;
/* 588:    */             }
/* 589:528 */             this.jjstateSet[(this.jjnewStateCnt++)] = 1;
/* 590:    */           }
/* 591:532 */         } while (i != startsAt);
/* 592:    */       }
/* 593:534 */       else if (this.curChar < '')
/* 594:    */       {
/* 595:536 */         long l = 1L << (this.curChar & 0x3F);
/* 596:    */         do
/* 597:    */         {
/* 598:539 */           switch (this.jjstateSet[(--i)])
/* 599:    */           {
/* 600:    */           case 0: 
/* 601:542 */             if (kind > 8) {
/* 602:543 */               kind = 8;
/* 603:    */             }
/* 604:544 */             if (this.curChar == '\\') {
/* 605:545 */               jjCheckNAdd(1);
/* 606:    */             }
/* 607:    */             break;
/* 608:    */           case 1: 
/* 609:548 */             if (kind > 7) {
/* 610:549 */               kind = 7;
/* 611:    */             }
/* 612:550 */             jjCheckNAdd(1);
/* 613:551 */             break;
/* 614:    */           case 2: 
/* 615:553 */             if (kind > 8) {
/* 616:554 */               kind = 8;
/* 617:    */             }
/* 618:    */             break;
/* 619:    */           }
/* 620:558 */         } while (i != startsAt);
/* 621:    */       }
/* 622:    */       else
/* 623:    */       {
/* 624:562 */         int i2 = (this.curChar & 0xFF) >> '\006';
/* 625:563 */         long l2 = 1L << (this.curChar & 0x3F);
/* 626:    */         do
/* 627:    */         {
/* 628:566 */           switch (this.jjstateSet[(--i)])
/* 629:    */           {
/* 630:    */           case 0: 
/* 631:569 */             if (((jjbitVec0[i2] & l2) != 0L) && (kind > 8)) {
/* 632:570 */               kind = 8;
/* 633:    */             }
/* 634:    */             break;
/* 635:    */           case 1: 
/* 636:573 */             if ((jjbitVec0[i2] & l2) != 0L)
/* 637:    */             {
/* 638:575 */               if (kind > 7) {
/* 639:576 */                 kind = 7;
/* 640:    */               }
/* 641:577 */               this.jjstateSet[(this.jjnewStateCnt++)] = 1;
/* 642:    */             }
/* 643:    */             break;
/* 644:    */           }
/* 645:581 */         } while (i != startsAt);
/* 646:    */       }
/* 647:583 */       if (kind != 2147483647)
/* 648:    */       {
/* 649:585 */         this.jjmatchedKind = kind;
/* 650:586 */         this.jjmatchedPos = curPos;
/* 651:587 */         kind = 2147483647;
/* 652:    */       }
/* 653:589 */       curPos++;
/* 654:590 */       if ((i = this.jjnewStateCnt) == (startsAt = 3 - (this.jjnewStateCnt = startsAt))) {
/* 655:591 */         return curPos;
/* 656:    */       }
/* 657:    */       try
/* 658:    */       {
/* 659:592 */         this.curChar = this.input_stream.readChar();
/* 660:    */       }
/* 661:    */       catch (IOException e) {}
/* 662:    */     }
/* 663:593 */     return curPos;
/* 664:    */   }
/* 665:    */   
/* 666:596 */   static final int[] jjnextStates = new int[0];
/* 667:598 */   public static final String[] jjstrLiteralImages = { "", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null };
/* 668:601 */   public static final String[] lexStateNames = { "DEFAULT", "INCOMMENT", "NESTED_COMMENT", "INQUOTEDSTRING" };
/* 669:607 */   public static final int[] jjnewLexState = { -1, 1, 0, 2, -1, -1, -1, -1, -1, 3, -1, -1, -1, 0, -1, -1, -1, -1 };
/* 670:610 */   static final long[] jjtoToken = { 63489L };
/* 671:613 */   static final long[] jjtoSkip = { 1022L };
/* 672:616 */   static final long[] jjtoMore = { 1024L };
/* 673:    */   protected SimpleCharStream input_stream;
/* 674:620 */   private final int[] jjrounds = new int[6];
/* 675:621 */   private final int[] jjstateSet = new int[12];
/* 676:    */   StringBuffer image;
/* 677:    */   int jjimageLen;
/* 678:    */   int lengthOfMatch;
/* 679:    */   protected char curChar;
/* 680:    */   
/* 681:    */   public StructuredFieldParserTokenManager(SimpleCharStream stream)
/* 682:    */   {
/* 683:629 */     this.input_stream = stream;
/* 684:    */   }
/* 685:    */   
/* 686:    */   public StructuredFieldParserTokenManager(SimpleCharStream stream, int lexState)
/* 687:    */   {
/* 688:632 */     this(stream);
/* 689:633 */     SwitchTo(lexState);
/* 690:    */   }
/* 691:    */   
/* 692:    */   public void ReInit(SimpleCharStream stream)
/* 693:    */   {
/* 694:637 */     this.jjmatchedPos = (this.jjnewStateCnt = 0);
/* 695:638 */     this.curLexState = this.defaultLexState;
/* 696:639 */     this.input_stream = stream;
/* 697:640 */     ReInitRounds();
/* 698:    */   }
/* 699:    */   
/* 700:    */   private final void ReInitRounds()
/* 701:    */   {
/* 702:645 */     this.jjround = -2147483647;
/* 703:646 */     for (int i = 6; i-- > 0;) {
/* 704:647 */       this.jjrounds[i] = -2147483648;
/* 705:    */     }
/* 706:    */   }
/* 707:    */   
/* 708:    */   public void ReInit(SimpleCharStream stream, int lexState)
/* 709:    */   {
/* 710:651 */     ReInit(stream);
/* 711:652 */     SwitchTo(lexState);
/* 712:    */   }
/* 713:    */   
/* 714:    */   public void SwitchTo(int lexState)
/* 715:    */   {
/* 716:656 */     if ((lexState >= 4) || (lexState < 0)) {
/* 717:657 */       throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", 2);
/* 718:    */     }
/* 719:659 */     this.curLexState = lexState;
/* 720:    */   }
/* 721:    */   
/* 722:    */   protected Token jjFillToken()
/* 723:    */   {
/* 724:664 */     Token t = Token.newToken(this.jjmatchedKind);
/* 725:665 */     t.kind = this.jjmatchedKind;
/* 726:666 */     String im = jjstrLiteralImages[this.jjmatchedKind];
/* 727:667 */     t.image = (im == null ? this.input_stream.GetImage() : im);
/* 728:668 */     t.beginLine = this.input_stream.getBeginLine();
/* 729:669 */     t.beginColumn = this.input_stream.getBeginColumn();
/* 730:670 */     t.endLine = this.input_stream.getEndLine();
/* 731:671 */     t.endColumn = this.input_stream.getEndColumn();
/* 732:672 */     return t;
/* 733:    */   }
/* 734:    */   
/* 735:675 */   int curLexState = 0;
/* 736:676 */   int defaultLexState = 0;
/* 737:    */   int jjnewStateCnt;
/* 738:    */   int jjround;
/* 739:    */   int jjmatchedPos;
/* 740:    */   int jjmatchedKind;
/* 741:    */   
/* 742:    */   public Token getNextToken()
/* 743:    */   {
/* 744:685 */     Token specialToken = null;
/* 745:    */     
/* 746:687 */     int curPos = 0;
/* 747:    */     try
/* 748:    */     {
/* 749:694 */       this.curChar = this.input_stream.BeginToken();
/* 750:    */     }
/* 751:    */     catch (IOException e)
/* 752:    */     {
/* 753:698 */       this.jjmatchedKind = 0;
/* 754:699 */       return jjFillToken();
/* 755:    */     }
/* 756:702 */     this.image = null;
/* 757:703 */     this.jjimageLen = 0;
/* 758:    */     for (;;)
/* 759:    */     {
/* 760:707 */       switch (this.curLexState)
/* 761:    */       {
/* 762:    */       case 0: 
/* 763:710 */         this.jjmatchedKind = 2147483647;
/* 764:711 */         this.jjmatchedPos = 0;
/* 765:712 */         curPos = jjMoveStringLiteralDfa0_0();
/* 766:713 */         break;
/* 767:    */       case 1: 
/* 768:715 */         this.jjmatchedKind = 2147483647;
/* 769:716 */         this.jjmatchedPos = 0;
/* 770:717 */         curPos = jjMoveStringLiteralDfa0_1();
/* 771:718 */         break;
/* 772:    */       case 2: 
/* 773:720 */         this.jjmatchedKind = 2147483647;
/* 774:721 */         this.jjmatchedPos = 0;
/* 775:722 */         curPos = jjMoveStringLiteralDfa0_2();
/* 776:723 */         break;
/* 777:    */       case 3: 
/* 778:725 */         this.jjmatchedKind = 2147483647;
/* 779:726 */         this.jjmatchedPos = 0;
/* 780:727 */         curPos = jjMoveStringLiteralDfa0_3();
/* 781:    */       }
/* 782:730 */       if (this.jjmatchedKind != 2147483647)
/* 783:    */       {
/* 784:732 */         if (this.jjmatchedPos + 1 < curPos) {
/* 785:733 */           this.input_stream.backup(curPos - this.jjmatchedPos - 1);
/* 786:    */         }
/* 787:734 */         if ((jjtoToken[(this.jjmatchedKind >> 6)] & 1L << (this.jjmatchedKind & 0x3F)) != 0L)
/* 788:    */         {
/* 789:736 */           Token matchedToken = jjFillToken();
/* 790:737 */           TokenLexicalActions(matchedToken);
/* 791:738 */           if (jjnewLexState[this.jjmatchedKind] != -1) {
/* 792:739 */             this.curLexState = jjnewLexState[this.jjmatchedKind];
/* 793:    */           }
/* 794:740 */           return matchedToken;
/* 795:    */         }
/* 796:742 */         if ((jjtoSkip[(this.jjmatchedKind >> 6)] & 1L << (this.jjmatchedKind & 0x3F)) != 0L)
/* 797:    */         {
/* 798:744 */           SkipLexicalActions(null);
/* 799:745 */           if (jjnewLexState[this.jjmatchedKind] == -1) {
/* 800:    */             break;
/* 801:    */           }
/* 802:746 */           this.curLexState = jjnewLexState[this.jjmatchedKind]; break;
/* 803:    */         }
/* 804:749 */         MoreLexicalActions();
/* 805:750 */         if (jjnewLexState[this.jjmatchedKind] != -1) {
/* 806:751 */           this.curLexState = jjnewLexState[this.jjmatchedKind];
/* 807:    */         }
/* 808:752 */         curPos = 0;
/* 809:753 */         this.jjmatchedKind = 2147483647;
/* 810:    */         try
/* 811:    */         {
/* 812:755 */           this.curChar = this.input_stream.readChar();
/* 813:    */         }
/* 814:    */         catch (IOException e1) {}
/* 815:    */       }
/* 816:    */     }
/* 817:760 */     int error_line = this.input_stream.getEndLine();
/* 818:761 */     int error_column = this.input_stream.getEndColumn();
/* 819:762 */     String error_after = null;
/* 820:763 */     boolean EOFSeen = false;
/* 821:    */     try
/* 822:    */     {
/* 823:764 */       this.input_stream.readChar();this.input_stream.backup(1);
/* 824:    */     }
/* 825:    */     catch (IOException e1)
/* 826:    */     {
/* 827:766 */       EOFSeen = true;
/* 828:767 */       error_after = curPos <= 1 ? "" : this.input_stream.GetImage();
/* 829:768 */       if ((this.curChar == '\n') || (this.curChar == '\r'))
/* 830:    */       {
/* 831:769 */         error_line++;
/* 832:770 */         error_column = 0;
/* 833:    */       }
/* 834:    */       else
/* 835:    */       {
/* 836:773 */         error_column++;
/* 837:    */       }
/* 838:    */     }
/* 839:775 */     if (!EOFSeen)
/* 840:    */     {
/* 841:776 */       this.input_stream.backup(1);
/* 842:777 */       error_after = curPos <= 1 ? "" : this.input_stream.GetImage();
/* 843:    */     }
/* 844:779 */     throw new TokenMgrError(EOFSeen, this.curLexState, error_line, error_column, error_after, this.curChar, 0);
/* 845:    */   }
/* 846:    */   
/* 847:    */   void SkipLexicalActions(Token matchedToken)
/* 848:    */   {
/* 849:786 */     switch (this.jjmatchedKind)
/* 850:    */     {
/* 851:    */     case 3: 
/* 852:789 */       if (this.image == null) {
/* 853:790 */         this.image = new StringBuffer();
/* 854:    */       }
/* 855:791 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 856:792 */       this.commentNest = 1;
/* 857:793 */       break;
/* 858:    */     case 5: 
/* 859:795 */       if (this.image == null) {
/* 860:796 */         this.image = new StringBuffer();
/* 861:    */       }
/* 862:797 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 863:798 */       this.commentNest += 1;System.out.println("+++ COMMENT NEST=" + this.commentNest);
/* 864:799 */       break;
/* 865:    */     case 6: 
/* 866:801 */       if (this.image == null) {
/* 867:802 */         this.image = new StringBuffer();
/* 868:    */       }
/* 869:803 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 870:804 */       this.commentNest -= 1;System.out.println("+++ COMMENT NEST=" + this.commentNest);
/* 871:804 */       if (this.commentNest == 0) {
/* 872:804 */         SwitchTo(1);
/* 873:    */       }
/* 874:    */       break;
/* 875:    */     case 7: 
/* 876:807 */       if (this.image == null) {
/* 877:808 */         this.image = new StringBuffer();
/* 878:    */       }
/* 879:809 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 880:810 */       this.image.deleteCharAt(this.image.length() - 2);
/* 881:811 */       break;
/* 882:    */     }
/* 883:    */   }
/* 884:    */   
/* 885:    */   void MoreLexicalActions()
/* 886:    */   {
/* 887:818 */     this.jjimageLen += (this.lengthOfMatch = this.jjmatchedPos + 1);
/* 888:819 */     switch (this.jjmatchedKind)
/* 889:    */     {
/* 890:    */     case 10: 
/* 891:822 */       if (this.image == null) {
/* 892:823 */         this.image = new StringBuffer();
/* 893:    */       }
/* 894:824 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 895:825 */       this.jjimageLen = 0;
/* 896:826 */       this.image.deleteCharAt(this.image.length() - 2);
/* 897:827 */       break;
/* 898:    */     }
/* 899:    */   }
/* 900:    */   
/* 901:    */   void TokenLexicalActions(Token matchedToken)
/* 902:    */   {
/* 903:834 */     switch (this.jjmatchedKind)
/* 904:    */     {
/* 905:    */     case 13: 
/* 906:837 */       if (this.image == null) {
/* 907:838 */         this.image = new StringBuffer();
/* 908:    */       }
/* 909:839 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 910:840 */       matchedToken.image = this.image.substring(0, this.image.length() - 1);
/* 911:841 */       break;
/* 912:    */     }
/* 913:    */   }
/* 914:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.structured.parser.StructuredFieldParserTokenManager
 * JD-Core Version:    0.7.0.1
 */