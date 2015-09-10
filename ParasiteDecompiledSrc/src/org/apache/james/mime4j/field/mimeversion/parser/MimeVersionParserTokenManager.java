/*   1:    */ package org.apache.james.mime4j.field.mimeversion.parser;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.PrintStream;
/*   5:    */ 
/*   6:    */ public class MimeVersionParserTokenManager
/*   7:    */   implements MimeVersionParserConstants
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
/*  56:    */     case '\n': 
/*  57: 64 */       return jjStartNfaWithStates_0(0, 2, 0);
/*  58:    */     case '\r': 
/*  59: 66 */       return jjStartNfaWithStates_0(0, 1, 0);
/*  60:    */     case '"': 
/*  61: 68 */       return jjStopAtPos(0, 13);
/*  62:    */     case '(': 
/*  63: 70 */       return jjStopAtPos(0, 4);
/*  64:    */     case '.': 
/*  65: 72 */       return jjStopAtPos(0, 18);
/*  66:    */     }
/*  67: 74 */     return jjMoveNfa_0(2, 0);
/*  68:    */   }
/*  69:    */   
/*  70:    */   private final void jjCheckNAdd(int state)
/*  71:    */   {
/*  72: 79 */     if (this.jjrounds[state] != this.jjround)
/*  73:    */     {
/*  74: 81 */       this.jjstateSet[(this.jjnewStateCnt++)] = state;
/*  75: 82 */       this.jjrounds[state] = this.jjround;
/*  76:    */     }
/*  77:    */   }
/*  78:    */   
/*  79:    */   private final void jjAddStates(int start, int end)
/*  80:    */   {
/*  81:    */     do
/*  82:    */     {
/*  83: 88 */       this.jjstateSet[(this.jjnewStateCnt++)] = jjnextStates[start];
/*  84: 89 */     } while (start++ != end);
/*  85:    */   }
/*  86:    */   
/*  87:    */   private final void jjCheckNAddTwoStates(int state1, int state2)
/*  88:    */   {
/*  89: 93 */     jjCheckNAdd(state1);
/*  90: 94 */     jjCheckNAdd(state2);
/*  91:    */   }
/*  92:    */   
/*  93:    */   private final void jjCheckNAddStates(int start, int end)
/*  94:    */   {
/*  95:    */     do
/*  96:    */     {
/*  97: 99 */       jjCheckNAdd(jjnextStates[start]);
/*  98:100 */     } while (start++ != end);
/*  99:    */   }
/* 100:    */   
/* 101:    */   private final void jjCheckNAddStates(int start)
/* 102:    */   {
/* 103:104 */     jjCheckNAdd(jjnextStates[start]);
/* 104:105 */     jjCheckNAdd(jjnextStates[(start + 1)]);
/* 105:    */   }
/* 106:    */   
/* 107:    */   private final int jjMoveNfa_0(int startState, int curPos)
/* 108:    */   {
/* 109:110 */     int startsAt = 0;
/* 110:111 */     this.jjnewStateCnt = 2;
/* 111:112 */     int i = 1;
/* 112:113 */     this.jjstateSet[0] = startState;
/* 113:114 */     int kind = 2147483647;
/* 114:    */     for (;;)
/* 115:    */     {
/* 116:117 */       if (++this.jjround == 2147483647) {
/* 117:118 */         ReInitRounds();
/* 118:    */       }
/* 119:119 */       if (this.curChar < '@')
/* 120:    */       {
/* 121:121 */         long l = 1L << this.curChar;
/* 122:    */         do
/* 123:    */         {
/* 124:124 */           switch (this.jjstateSet[(--i)])
/* 125:    */           {
/* 126:    */           case 2: 
/* 127:127 */             if ((0x0 & l) != 0L)
/* 128:    */             {
/* 129:129 */               if (kind > 17) {
/* 130:130 */                 kind = 17;
/* 131:    */               }
/* 132:131 */               jjCheckNAdd(1);
/* 133:    */             }
/* 134:133 */             else if ((0x2600 & l) != 0L)
/* 135:    */             {
/* 136:135 */               if (kind > 3) {
/* 137:136 */                 kind = 3;
/* 138:    */               }
/* 139:137 */               jjCheckNAdd(0);
/* 140:    */             }
/* 141:    */             break;
/* 142:    */           case 0: 
/* 143:141 */             if ((0x2600 & l) != 0L)
/* 144:    */             {
/* 145:143 */               kind = 3;
/* 146:144 */               jjCheckNAdd(0);
/* 147:    */             }
/* 148:145 */             break;
/* 149:    */           case 1: 
/* 150:147 */             if ((0x0 & l) != 0L)
/* 151:    */             {
/* 152:149 */               kind = 17;
/* 153:150 */               jjCheckNAdd(1);
/* 154:    */             }
/* 155:    */             break;
/* 156:    */           }
/* 157:154 */         } while (i != startsAt);
/* 158:    */       }
/* 159:156 */       else if (this.curChar < '')
/* 160:    */       {
/* 161:158 */         long l = 1L << (this.curChar & 0x3F);
/* 162:    */         do
/* 163:    */         {
/* 164:161 */           switch (this.jjstateSet[(--i)])
/* 165:    */           {
/* 166:    */           }
/* 167:165 */         } while (i != startsAt);
/* 168:    */       }
/* 169:    */       else
/* 170:    */       {
/* 171:169 */         int i2 = (this.curChar & 0xFF) >> '\006';
/* 172:170 */         long l2 = 1L << (this.curChar & 0x3F);
/* 173:    */         do
/* 174:    */         {
/* 175:173 */           switch (this.jjstateSet[(--i)])
/* 176:    */           {
/* 177:    */           }
/* 178:177 */         } while (i != startsAt);
/* 179:    */       }
/* 180:179 */       if (kind != 2147483647)
/* 181:    */       {
/* 182:181 */         this.jjmatchedKind = kind;
/* 183:182 */         this.jjmatchedPos = curPos;
/* 184:183 */         kind = 2147483647;
/* 185:    */       }
/* 186:185 */       curPos++;
/* 187:186 */       if ((i = this.jjnewStateCnt) == (startsAt = 2 - (this.jjnewStateCnt = startsAt))) {
/* 188:187 */         return curPos;
/* 189:    */       }
/* 190:    */       try
/* 191:    */       {
/* 192:188 */         this.curChar = this.input_stream.readChar();
/* 193:    */       }
/* 194:    */       catch (IOException e) {}
/* 195:    */     }
/* 196:189 */     return curPos;
/* 197:    */   }
/* 198:    */   
/* 199:    */   private final int jjStopStringLiteralDfa_1(int pos, long active0)
/* 200:    */   {
/* 201:194 */     switch (pos)
/* 202:    */     {
/* 203:    */     }
/* 204:197 */     return -1;
/* 205:    */   }
/* 206:    */   
/* 207:    */   private final int jjStartNfa_1(int pos, long active0)
/* 208:    */   {
/* 209:202 */     return jjMoveNfa_1(jjStopStringLiteralDfa_1(pos, active0), pos + 1);
/* 210:    */   }
/* 211:    */   
/* 212:    */   private final int jjStartNfaWithStates_1(int pos, int kind, int state)
/* 213:    */   {
/* 214:206 */     this.jjmatchedKind = kind;
/* 215:207 */     this.jjmatchedPos = pos;
/* 216:    */     try
/* 217:    */     {
/* 218:208 */       this.curChar = this.input_stream.readChar();
/* 219:    */     }
/* 220:    */     catch (IOException e)
/* 221:    */     {
/* 222:209 */       return pos + 1;
/* 223:    */     }
/* 224:210 */     return jjMoveNfa_1(state, pos + 1);
/* 225:    */   }
/* 226:    */   
/* 227:    */   private final int jjMoveStringLiteralDfa0_1()
/* 228:    */   {
/* 229:214 */     switch (this.curChar)
/* 230:    */     {
/* 231:    */     case '(': 
/* 232:217 */       return jjStopAtPos(0, 7);
/* 233:    */     case ')': 
/* 234:219 */       return jjStopAtPos(0, 5);
/* 235:    */     }
/* 236:221 */     return jjMoveNfa_1(0, 0);
/* 237:    */   }
/* 238:    */   
/* 239:224 */   static final long[] jjbitVec0 = { 0L, 0L, -1L, -1L };
/* 240:    */   
/* 241:    */   private final int jjMoveNfa_1(int startState, int curPos)
/* 242:    */   {
/* 243:230 */     int startsAt = 0;
/* 244:231 */     this.jjnewStateCnt = 3;
/* 245:232 */     int i = 1;
/* 246:233 */     this.jjstateSet[0] = startState;
/* 247:234 */     int kind = 2147483647;
/* 248:    */     for (;;)
/* 249:    */     {
/* 250:237 */       if (++this.jjround == 2147483647) {
/* 251:238 */         ReInitRounds();
/* 252:    */       }
/* 253:239 */       if (this.curChar < '@')
/* 254:    */       {
/* 255:241 */         long l = 1L << this.curChar;
/* 256:    */         do
/* 257:    */         {
/* 258:244 */           switch (this.jjstateSet[(--i)])
/* 259:    */           {
/* 260:    */           case 0: 
/* 261:247 */             if (kind > 8) {
/* 262:248 */               kind = 8;
/* 263:    */             }
/* 264:    */             break;
/* 265:    */           case 1: 
/* 266:251 */             if (kind > 6) {
/* 267:252 */               kind = 6;
/* 268:    */             }
/* 269:    */             break;
/* 270:    */           }
/* 271:256 */         } while (i != startsAt);
/* 272:    */       }
/* 273:258 */       else if (this.curChar < '')
/* 274:    */       {
/* 275:260 */         long l = 1L << (this.curChar & 0x3F);
/* 276:    */         do
/* 277:    */         {
/* 278:263 */           switch (this.jjstateSet[(--i)])
/* 279:    */           {
/* 280:    */           case 0: 
/* 281:266 */             if (kind > 8) {
/* 282:267 */               kind = 8;
/* 283:    */             }
/* 284:268 */             if (this.curChar == '\\') {
/* 285:269 */               this.jjstateSet[(this.jjnewStateCnt++)] = 1;
/* 286:    */             }
/* 287:    */             break;
/* 288:    */           case 1: 
/* 289:272 */             if (kind > 6) {
/* 290:273 */               kind = 6;
/* 291:    */             }
/* 292:    */             break;
/* 293:    */           case 2: 
/* 294:276 */             if (kind > 8) {
/* 295:277 */               kind = 8;
/* 296:    */             }
/* 297:    */             break;
/* 298:    */           }
/* 299:281 */         } while (i != startsAt);
/* 300:    */       }
/* 301:    */       else
/* 302:    */       {
/* 303:285 */         int i2 = (this.curChar & 0xFF) >> '\006';
/* 304:286 */         long l2 = 1L << (this.curChar & 0x3F);
/* 305:    */         do
/* 306:    */         {
/* 307:289 */           switch (this.jjstateSet[(--i)])
/* 308:    */           {
/* 309:    */           case 0: 
/* 310:292 */             if (((jjbitVec0[i2] & l2) != 0L) && (kind > 8)) {
/* 311:293 */               kind = 8;
/* 312:    */             }
/* 313:    */             break;
/* 314:    */           case 1: 
/* 315:296 */             if (((jjbitVec0[i2] & l2) != 0L) && (kind > 6)) {
/* 316:297 */               kind = 6;
/* 317:    */             }
/* 318:    */             break;
/* 319:    */           }
/* 320:301 */         } while (i != startsAt);
/* 321:    */       }
/* 322:303 */       if (kind != 2147483647)
/* 323:    */       {
/* 324:305 */         this.jjmatchedKind = kind;
/* 325:306 */         this.jjmatchedPos = curPos;
/* 326:307 */         kind = 2147483647;
/* 327:    */       }
/* 328:309 */       curPos++;
/* 329:310 */       if ((i = this.jjnewStateCnt) == (startsAt = 3 - (this.jjnewStateCnt = startsAt))) {
/* 330:311 */         return curPos;
/* 331:    */       }
/* 332:    */       try
/* 333:    */       {
/* 334:312 */         this.curChar = this.input_stream.readChar();
/* 335:    */       }
/* 336:    */       catch (IOException e) {}
/* 337:    */     }
/* 338:313 */     return curPos;
/* 339:    */   }
/* 340:    */   
/* 341:    */   private final int jjStopStringLiteralDfa_3(int pos, long active0)
/* 342:    */   {
/* 343:318 */     switch (pos)
/* 344:    */     {
/* 345:    */     }
/* 346:321 */     return -1;
/* 347:    */   }
/* 348:    */   
/* 349:    */   private final int jjStartNfa_3(int pos, long active0)
/* 350:    */   {
/* 351:326 */     return jjMoveNfa_3(jjStopStringLiteralDfa_3(pos, active0), pos + 1);
/* 352:    */   }
/* 353:    */   
/* 354:    */   private final int jjStartNfaWithStates_3(int pos, int kind, int state)
/* 355:    */   {
/* 356:330 */     this.jjmatchedKind = kind;
/* 357:331 */     this.jjmatchedPos = pos;
/* 358:    */     try
/* 359:    */     {
/* 360:332 */       this.curChar = this.input_stream.readChar();
/* 361:    */     }
/* 362:    */     catch (IOException e)
/* 363:    */     {
/* 364:333 */       return pos + 1;
/* 365:    */     }
/* 366:334 */     return jjMoveNfa_3(state, pos + 1);
/* 367:    */   }
/* 368:    */   
/* 369:    */   private final int jjMoveStringLiteralDfa0_3()
/* 370:    */   {
/* 371:338 */     switch (this.curChar)
/* 372:    */     {
/* 373:    */     case '"': 
/* 374:341 */       return jjStopAtPos(0, 16);
/* 375:    */     }
/* 376:343 */     return jjMoveNfa_3(0, 0);
/* 377:    */   }
/* 378:    */   
/* 379:    */   private final int jjMoveNfa_3(int startState, int curPos)
/* 380:    */   {
/* 381:349 */     int startsAt = 0;
/* 382:350 */     this.jjnewStateCnt = 3;
/* 383:351 */     int i = 1;
/* 384:352 */     this.jjstateSet[0] = startState;
/* 385:353 */     int kind = 2147483647;
/* 386:    */     for (;;)
/* 387:    */     {
/* 388:356 */       if (++this.jjround == 2147483647) {
/* 389:357 */         ReInitRounds();
/* 390:    */       }
/* 391:358 */       if (this.curChar < '@')
/* 392:    */       {
/* 393:360 */         long l = 1L << this.curChar;
/* 394:    */         do
/* 395:    */         {
/* 396:363 */           switch (this.jjstateSet[(--i)])
/* 397:    */           {
/* 398:    */           case 0: 
/* 399:    */           case 2: 
/* 400:367 */             if ((0xFFFFFFFF & l) != 0L)
/* 401:    */             {
/* 402:369 */               if (kind > 15) {
/* 403:370 */                 kind = 15;
/* 404:    */               }
/* 405:371 */               jjCheckNAdd(2);
/* 406:    */             }
/* 407:372 */             break;
/* 408:    */           case 1: 
/* 409:374 */             if (kind > 14) {
/* 410:375 */               kind = 14;
/* 411:    */             }
/* 412:    */             break;
/* 413:    */           }
/* 414:379 */         } while (i != startsAt);
/* 415:    */       }
/* 416:381 */       else if (this.curChar < '')
/* 417:    */       {
/* 418:383 */         long l = 1L << (this.curChar & 0x3F);
/* 419:    */         do
/* 420:    */         {
/* 421:386 */           switch (this.jjstateSet[(--i)])
/* 422:    */           {
/* 423:    */           case 0: 
/* 424:389 */             if ((0xEFFFFFFF & l) != 0L)
/* 425:    */             {
/* 426:391 */               if (kind > 15) {
/* 427:392 */                 kind = 15;
/* 428:    */               }
/* 429:393 */               jjCheckNAdd(2);
/* 430:    */             }
/* 431:395 */             else if (this.curChar == '\\')
/* 432:    */             {
/* 433:396 */               this.jjstateSet[(this.jjnewStateCnt++)] = 1;
/* 434:    */             }
/* 435:    */             break;
/* 436:    */           case 1: 
/* 437:399 */             if (kind > 14) {
/* 438:400 */               kind = 14;
/* 439:    */             }
/* 440:    */             break;
/* 441:    */           case 2: 
/* 442:403 */             if ((0xEFFFFFFF & l) != 0L)
/* 443:    */             {
/* 444:405 */               if (kind > 15) {
/* 445:406 */                 kind = 15;
/* 446:    */               }
/* 447:407 */               jjCheckNAdd(2);
/* 448:    */             }
/* 449:    */             break;
/* 450:    */           }
/* 451:411 */         } while (i != startsAt);
/* 452:    */       }
/* 453:    */       else
/* 454:    */       {
/* 455:415 */         int i2 = (this.curChar & 0xFF) >> '\006';
/* 456:416 */         long l2 = 1L << (this.curChar & 0x3F);
/* 457:    */         do
/* 458:    */         {
/* 459:419 */           switch (this.jjstateSet[(--i)])
/* 460:    */           {
/* 461:    */           case 0: 
/* 462:    */           case 2: 
/* 463:423 */             if ((jjbitVec0[i2] & l2) != 0L)
/* 464:    */             {
/* 465:425 */               if (kind > 15) {
/* 466:426 */                 kind = 15;
/* 467:    */               }
/* 468:427 */               jjCheckNAdd(2);
/* 469:    */             }
/* 470:428 */             break;
/* 471:    */           case 1: 
/* 472:430 */             if (((jjbitVec0[i2] & l2) != 0L) && (kind > 14)) {
/* 473:431 */               kind = 14;
/* 474:    */             }
/* 475:    */             break;
/* 476:    */           }
/* 477:435 */         } while (i != startsAt);
/* 478:    */       }
/* 479:437 */       if (kind != 2147483647)
/* 480:    */       {
/* 481:439 */         this.jjmatchedKind = kind;
/* 482:440 */         this.jjmatchedPos = curPos;
/* 483:441 */         kind = 2147483647;
/* 484:    */       }
/* 485:443 */       curPos++;
/* 486:444 */       if ((i = this.jjnewStateCnt) == (startsAt = 3 - (this.jjnewStateCnt = startsAt))) {
/* 487:445 */         return curPos;
/* 488:    */       }
/* 489:    */       try
/* 490:    */       {
/* 491:446 */         this.curChar = this.input_stream.readChar();
/* 492:    */       }
/* 493:    */       catch (IOException e) {}
/* 494:    */     }
/* 495:447 */     return curPos;
/* 496:    */   }
/* 497:    */   
/* 498:    */   private final int jjStopStringLiteralDfa_2(int pos, long active0)
/* 499:    */   {
/* 500:452 */     switch (pos)
/* 501:    */     {
/* 502:    */     }
/* 503:455 */     return -1;
/* 504:    */   }
/* 505:    */   
/* 506:    */   private final int jjStartNfa_2(int pos, long active0)
/* 507:    */   {
/* 508:460 */     return jjMoveNfa_2(jjStopStringLiteralDfa_2(pos, active0), pos + 1);
/* 509:    */   }
/* 510:    */   
/* 511:    */   private final int jjStartNfaWithStates_2(int pos, int kind, int state)
/* 512:    */   {
/* 513:464 */     this.jjmatchedKind = kind;
/* 514:465 */     this.jjmatchedPos = pos;
/* 515:    */     try
/* 516:    */     {
/* 517:466 */       this.curChar = this.input_stream.readChar();
/* 518:    */     }
/* 519:    */     catch (IOException e)
/* 520:    */     {
/* 521:467 */       return pos + 1;
/* 522:    */     }
/* 523:468 */     return jjMoveNfa_2(state, pos + 1);
/* 524:    */   }
/* 525:    */   
/* 526:    */   private final int jjMoveStringLiteralDfa0_2()
/* 527:    */   {
/* 528:472 */     switch (this.curChar)
/* 529:    */     {
/* 530:    */     case '(': 
/* 531:475 */       return jjStopAtPos(0, 10);
/* 532:    */     case ')': 
/* 533:477 */       return jjStopAtPos(0, 11);
/* 534:    */     }
/* 535:479 */     return jjMoveNfa_2(0, 0);
/* 536:    */   }
/* 537:    */   
/* 538:    */   private final int jjMoveNfa_2(int startState, int curPos)
/* 539:    */   {
/* 540:485 */     int startsAt = 0;
/* 541:486 */     this.jjnewStateCnt = 3;
/* 542:487 */     int i = 1;
/* 543:488 */     this.jjstateSet[0] = startState;
/* 544:489 */     int kind = 2147483647;
/* 545:    */     for (;;)
/* 546:    */     {
/* 547:492 */       if (++this.jjround == 2147483647) {
/* 548:493 */         ReInitRounds();
/* 549:    */       }
/* 550:494 */       if (this.curChar < '@')
/* 551:    */       {
/* 552:496 */         long l = 1L << this.curChar;
/* 553:    */         do
/* 554:    */         {
/* 555:499 */           switch (this.jjstateSet[(--i)])
/* 556:    */           {
/* 557:    */           case 0: 
/* 558:502 */             if (kind > 12) {
/* 559:503 */               kind = 12;
/* 560:    */             }
/* 561:    */             break;
/* 562:    */           case 1: 
/* 563:506 */             if (kind > 9) {
/* 564:507 */               kind = 9;
/* 565:    */             }
/* 566:    */             break;
/* 567:    */           }
/* 568:511 */         } while (i != startsAt);
/* 569:    */       }
/* 570:513 */       else if (this.curChar < '')
/* 571:    */       {
/* 572:515 */         long l = 1L << (this.curChar & 0x3F);
/* 573:    */         do
/* 574:    */         {
/* 575:518 */           switch (this.jjstateSet[(--i)])
/* 576:    */           {
/* 577:    */           case 0: 
/* 578:521 */             if (kind > 12) {
/* 579:522 */               kind = 12;
/* 580:    */             }
/* 581:523 */             if (this.curChar == '\\') {
/* 582:524 */               this.jjstateSet[(this.jjnewStateCnt++)] = 1;
/* 583:    */             }
/* 584:    */             break;
/* 585:    */           case 1: 
/* 586:527 */             if (kind > 9) {
/* 587:528 */               kind = 9;
/* 588:    */             }
/* 589:    */             break;
/* 590:    */           case 2: 
/* 591:531 */             if (kind > 12) {
/* 592:532 */               kind = 12;
/* 593:    */             }
/* 594:    */             break;
/* 595:    */           }
/* 596:536 */         } while (i != startsAt);
/* 597:    */       }
/* 598:    */       else
/* 599:    */       {
/* 600:540 */         int i2 = (this.curChar & 0xFF) >> '\006';
/* 601:541 */         long l2 = 1L << (this.curChar & 0x3F);
/* 602:    */         do
/* 603:    */         {
/* 604:544 */           switch (this.jjstateSet[(--i)])
/* 605:    */           {
/* 606:    */           case 0: 
/* 607:547 */             if (((jjbitVec0[i2] & l2) != 0L) && (kind > 12)) {
/* 608:548 */               kind = 12;
/* 609:    */             }
/* 610:    */             break;
/* 611:    */           case 1: 
/* 612:551 */             if (((jjbitVec0[i2] & l2) != 0L) && (kind > 9)) {
/* 613:552 */               kind = 9;
/* 614:    */             }
/* 615:    */             break;
/* 616:    */           }
/* 617:556 */         } while (i != startsAt);
/* 618:    */       }
/* 619:558 */       if (kind != 2147483647)
/* 620:    */       {
/* 621:560 */         this.jjmatchedKind = kind;
/* 622:561 */         this.jjmatchedPos = curPos;
/* 623:562 */         kind = 2147483647;
/* 624:    */       }
/* 625:564 */       curPos++;
/* 626:565 */       if ((i = this.jjnewStateCnt) == (startsAt = 3 - (this.jjnewStateCnt = startsAt))) {
/* 627:566 */         return curPos;
/* 628:    */       }
/* 629:    */       try
/* 630:    */       {
/* 631:567 */         this.curChar = this.input_stream.readChar();
/* 632:    */       }
/* 633:    */       catch (IOException e) {}
/* 634:    */     }
/* 635:568 */     return curPos;
/* 636:    */   }
/* 637:    */   
/* 638:571 */   static final int[] jjnextStates = new int[0];
/* 639:573 */   public static final String[] jjstrLiteralImages = { "", "\r", "\n", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, ".", null, null };
/* 640:576 */   public static final String[] lexStateNames = { "DEFAULT", "INCOMMENT", "NESTED_COMMENT", "INQUOTEDSTRING" };
/* 641:582 */   public static final int[] jjnewLexState = { -1, -1, -1, -1, 1, 0, -1, 2, -1, -1, -1, -1, -1, 3, -1, -1, 0, -1, -1, -1, -1 };
/* 642:585 */   static final long[] jjtoToken = { 458759L };
/* 643:588 */   static final long[] jjtoSkip = { 40L };
/* 644:591 */   static final long[] jjtoSpecial = { 8L };
/* 645:594 */   static final long[] jjtoMore = { 65488L };
/* 646:    */   protected SimpleCharStream input_stream;
/* 647:598 */   private final int[] jjrounds = new int[3];
/* 648:599 */   private final int[] jjstateSet = new int[6];
/* 649:    */   StringBuffer image;
/* 650:    */   int jjimageLen;
/* 651:    */   int lengthOfMatch;
/* 652:    */   protected char curChar;
/* 653:    */   
/* 654:    */   public MimeVersionParserTokenManager(SimpleCharStream stream)
/* 655:    */   {
/* 656:607 */     this.input_stream = stream;
/* 657:    */   }
/* 658:    */   
/* 659:    */   public MimeVersionParserTokenManager(SimpleCharStream stream, int lexState)
/* 660:    */   {
/* 661:610 */     this(stream);
/* 662:611 */     SwitchTo(lexState);
/* 663:    */   }
/* 664:    */   
/* 665:    */   public void ReInit(SimpleCharStream stream)
/* 666:    */   {
/* 667:615 */     this.jjmatchedPos = (this.jjnewStateCnt = 0);
/* 668:616 */     this.curLexState = this.defaultLexState;
/* 669:617 */     this.input_stream = stream;
/* 670:618 */     ReInitRounds();
/* 671:    */   }
/* 672:    */   
/* 673:    */   private final void ReInitRounds()
/* 674:    */   {
/* 675:623 */     this.jjround = -2147483647;
/* 676:624 */     for (int i = 3; i-- > 0;) {
/* 677:625 */       this.jjrounds[i] = -2147483648;
/* 678:    */     }
/* 679:    */   }
/* 680:    */   
/* 681:    */   public void ReInit(SimpleCharStream stream, int lexState)
/* 682:    */   {
/* 683:629 */     ReInit(stream);
/* 684:630 */     SwitchTo(lexState);
/* 685:    */   }
/* 686:    */   
/* 687:    */   public void SwitchTo(int lexState)
/* 688:    */   {
/* 689:634 */     if ((lexState >= 4) || (lexState < 0)) {
/* 690:635 */       throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", 2);
/* 691:    */     }
/* 692:637 */     this.curLexState = lexState;
/* 693:    */   }
/* 694:    */   
/* 695:    */   protected Token jjFillToken()
/* 696:    */   {
/* 697:642 */     Token t = Token.newToken(this.jjmatchedKind);
/* 698:643 */     t.kind = this.jjmatchedKind;
/* 699:644 */     String im = jjstrLiteralImages[this.jjmatchedKind];
/* 700:645 */     t.image = (im == null ? this.input_stream.GetImage() : im);
/* 701:646 */     t.beginLine = this.input_stream.getBeginLine();
/* 702:647 */     t.beginColumn = this.input_stream.getBeginColumn();
/* 703:648 */     t.endLine = this.input_stream.getEndLine();
/* 704:649 */     t.endColumn = this.input_stream.getEndColumn();
/* 705:650 */     return t;
/* 706:    */   }
/* 707:    */   
/* 708:653 */   int curLexState = 0;
/* 709:654 */   int defaultLexState = 0;
/* 710:    */   int jjnewStateCnt;
/* 711:    */   int jjround;
/* 712:    */   int jjmatchedPos;
/* 713:    */   int jjmatchedKind;
/* 714:    */   
/* 715:    */   public Token getNextToken()
/* 716:    */   {
/* 717:663 */     Token specialToken = null;
/* 718:    */     
/* 719:665 */     int curPos = 0;
/* 720:    */     try
/* 721:    */     {
/* 722:672 */       this.curChar = this.input_stream.BeginToken();
/* 723:    */     }
/* 724:    */     catch (IOException e)
/* 725:    */     {
/* 726:676 */       this.jjmatchedKind = 0;
/* 727:677 */       Token matchedToken = jjFillToken();
/* 728:678 */       matchedToken.specialToken = specialToken;
/* 729:679 */       return matchedToken;
/* 730:    */     }
/* 731:681 */     this.image = null;
/* 732:682 */     this.jjimageLen = 0;
/* 733:    */     for (;;)
/* 734:    */     {
/* 735:686 */       switch (this.curLexState)
/* 736:    */       {
/* 737:    */       case 0: 
/* 738:689 */         this.jjmatchedKind = 2147483647;
/* 739:690 */         this.jjmatchedPos = 0;
/* 740:691 */         curPos = jjMoveStringLiteralDfa0_0();
/* 741:692 */         break;
/* 742:    */       case 1: 
/* 743:694 */         this.jjmatchedKind = 2147483647;
/* 744:695 */         this.jjmatchedPos = 0;
/* 745:696 */         curPos = jjMoveStringLiteralDfa0_1();
/* 746:697 */         break;
/* 747:    */       case 2: 
/* 748:699 */         this.jjmatchedKind = 2147483647;
/* 749:700 */         this.jjmatchedPos = 0;
/* 750:701 */         curPos = jjMoveStringLiteralDfa0_2();
/* 751:702 */         break;
/* 752:    */       case 3: 
/* 753:704 */         this.jjmatchedKind = 2147483647;
/* 754:705 */         this.jjmatchedPos = 0;
/* 755:706 */         curPos = jjMoveStringLiteralDfa0_3();
/* 756:    */       }
/* 757:709 */       if (this.jjmatchedKind != 2147483647)
/* 758:    */       {
/* 759:711 */         if (this.jjmatchedPos + 1 < curPos) {
/* 760:712 */           this.input_stream.backup(curPos - this.jjmatchedPos - 1);
/* 761:    */         }
/* 762:713 */         if ((jjtoToken[(this.jjmatchedKind >> 6)] & 1L << (this.jjmatchedKind & 0x3F)) != 0L)
/* 763:    */         {
/* 764:715 */           Token matchedToken = jjFillToken();
/* 765:716 */           matchedToken.specialToken = specialToken;
/* 766:717 */           TokenLexicalActions(matchedToken);
/* 767:718 */           if (jjnewLexState[this.jjmatchedKind] != -1) {
/* 768:719 */             this.curLexState = jjnewLexState[this.jjmatchedKind];
/* 769:    */           }
/* 770:720 */           return matchedToken;
/* 771:    */         }
/* 772:722 */         if ((jjtoSkip[(this.jjmatchedKind >> 6)] & 1L << (this.jjmatchedKind & 0x3F)) != 0L)
/* 773:    */         {
/* 774:724 */           if ((jjtoSpecial[(this.jjmatchedKind >> 6)] & 1L << (this.jjmatchedKind & 0x3F)) != 0L)
/* 775:    */           {
/* 776:726 */             Token matchedToken = jjFillToken();
/* 777:727 */             if (specialToken == null)
/* 778:    */             {
/* 779:728 */               specialToken = matchedToken;
/* 780:    */             }
/* 781:    */             else
/* 782:    */             {
/* 783:731 */               matchedToken.specialToken = specialToken;
/* 784:732 */               specialToken = specialToken.next = matchedToken;
/* 785:    */             }
/* 786:    */           }
/* 787:735 */           if (jjnewLexState[this.jjmatchedKind] == -1) {
/* 788:    */             break;
/* 789:    */           }
/* 790:736 */           this.curLexState = jjnewLexState[this.jjmatchedKind]; break;
/* 791:    */         }
/* 792:739 */         MoreLexicalActions();
/* 793:740 */         if (jjnewLexState[this.jjmatchedKind] != -1) {
/* 794:741 */           this.curLexState = jjnewLexState[this.jjmatchedKind];
/* 795:    */         }
/* 796:742 */         curPos = 0;
/* 797:743 */         this.jjmatchedKind = 2147483647;
/* 798:    */         try
/* 799:    */         {
/* 800:745 */           this.curChar = this.input_stream.readChar();
/* 801:    */         }
/* 802:    */         catch (IOException e1) {}
/* 803:    */       }
/* 804:    */     }
/* 805:750 */     int error_line = this.input_stream.getEndLine();
/* 806:751 */     int error_column = this.input_stream.getEndColumn();
/* 807:752 */     String error_after = null;
/* 808:753 */     boolean EOFSeen = false;
/* 809:    */     try
/* 810:    */     {
/* 811:754 */       this.input_stream.readChar();this.input_stream.backup(1);
/* 812:    */     }
/* 813:    */     catch (IOException e1)
/* 814:    */     {
/* 815:756 */       EOFSeen = true;
/* 816:757 */       error_after = curPos <= 1 ? "" : this.input_stream.GetImage();
/* 817:758 */       if ((this.curChar == '\n') || (this.curChar == '\r'))
/* 818:    */       {
/* 819:759 */         error_line++;
/* 820:760 */         error_column = 0;
/* 821:    */       }
/* 822:    */       else
/* 823:    */       {
/* 824:763 */         error_column++;
/* 825:    */       }
/* 826:    */     }
/* 827:765 */     if (!EOFSeen)
/* 828:    */     {
/* 829:766 */       this.input_stream.backup(1);
/* 830:767 */       error_after = curPos <= 1 ? "" : this.input_stream.GetImage();
/* 831:    */     }
/* 832:769 */     throw new TokenMgrError(EOFSeen, this.curLexState, error_line, error_column, error_after, this.curChar, 0);
/* 833:    */   }
/* 834:    */   
/* 835:    */   void MoreLexicalActions()
/* 836:    */   {
/* 837:776 */     this.jjimageLen += (this.lengthOfMatch = this.jjmatchedPos + 1);
/* 838:777 */     switch (this.jjmatchedKind)
/* 839:    */     {
/* 840:    */     case 6: 
/* 841:780 */       if (this.image == null) {
/* 842:781 */         this.image = new StringBuffer();
/* 843:    */       }
/* 844:782 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 845:783 */       this.jjimageLen = 0;
/* 846:784 */       this.image.deleteCharAt(this.image.length() - 2);
/* 847:785 */       break;
/* 848:    */     case 7: 
/* 849:787 */       if (this.image == null) {
/* 850:788 */         this.image = new StringBuffer();
/* 851:    */       }
/* 852:789 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 853:790 */       this.jjimageLen = 0;
/* 854:791 */       this.commentNest = 1;
/* 855:792 */       break;
/* 856:    */     case 9: 
/* 857:794 */       if (this.image == null) {
/* 858:795 */         this.image = new StringBuffer();
/* 859:    */       }
/* 860:796 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 861:797 */       this.jjimageLen = 0;
/* 862:798 */       this.image.deleteCharAt(this.image.length() - 2);
/* 863:799 */       break;
/* 864:    */     case 10: 
/* 865:801 */       if (this.image == null) {
/* 866:802 */         this.image = new StringBuffer();
/* 867:    */       }
/* 868:803 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 869:804 */       this.jjimageLen = 0;
/* 870:805 */       this.commentNest += 1;
/* 871:806 */       break;
/* 872:    */     case 11: 
/* 873:808 */       if (this.image == null) {
/* 874:809 */         this.image = new StringBuffer();
/* 875:    */       }
/* 876:810 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 877:811 */       this.jjimageLen = 0;
/* 878:812 */       this.commentNest -= 1;
/* 879:812 */       if (this.commentNest == 0) {
/* 880:812 */         SwitchTo(1);
/* 881:    */       }
/* 882:    */       break;
/* 883:    */     case 13: 
/* 884:815 */       if (this.image == null) {
/* 885:816 */         this.image = new StringBuffer();
/* 886:    */       }
/* 887:817 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 888:818 */       this.jjimageLen = 0;
/* 889:819 */       this.image.deleteCharAt(this.image.length() - 1);
/* 890:820 */       break;
/* 891:    */     case 14: 
/* 892:822 */       if (this.image == null) {
/* 893:823 */         this.image = new StringBuffer();
/* 894:    */       }
/* 895:824 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 896:825 */       this.jjimageLen = 0;
/* 897:826 */       this.image.deleteCharAt(this.image.length() - 2);
/* 898:827 */       break;
/* 899:    */     }
/* 900:    */   }
/* 901:    */   
/* 902:    */   void TokenLexicalActions(Token matchedToken)
/* 903:    */   {
/* 904:834 */     switch (this.jjmatchedKind)
/* 905:    */     {
/* 906:    */     case 16: 
/* 907:837 */       if (this.image == null) {
/* 908:838 */         this.image = new StringBuffer();
/* 909:    */       }
/* 910:839 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 911:840 */       matchedToken.image = this.image.substring(0, this.image.length() - 1);
/* 912:841 */       break;
/* 913:    */     }
/* 914:    */   }
/* 915:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.mimeversion.parser.MimeVersionParserTokenManager
 * JD-Core Version:    0.7.0.1
 */