/*   1:    */ package org.apache.james.mime4j.field.contentdisposition.parser;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.PrintStream;
/*   5:    */ 
/*   6:    */ public class ContentDispositionParserTokenManager
/*   7:    */   implements ContentDispositionParserConstants
/*   8:    */ {
/*   9:    */   static int commentNest;
/*  10: 33 */   public PrintStream debugStream = System.out;
/*  11:    */   
/*  12:    */   public void setDebugStream(PrintStream ds)
/*  13:    */   {
/*  14: 34 */     this.debugStream = ds;
/*  15:    */   }
/*  16:    */   
/*  17:    */   private final int jjStopStringLiteralDfa_0(int pos, long active0)
/*  18:    */   {
/*  19: 37 */     switch (pos)
/*  20:    */     {
/*  21:    */     }
/*  22: 40 */     return -1;
/*  23:    */   }
/*  24:    */   
/*  25:    */   private final int jjStartNfa_0(int pos, long active0)
/*  26:    */   {
/*  27: 45 */     return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
/*  28:    */   }
/*  29:    */   
/*  30:    */   private final int jjStopAtPos(int pos, int kind)
/*  31:    */   {
/*  32: 49 */     this.jjmatchedKind = kind;
/*  33: 50 */     this.jjmatchedPos = pos;
/*  34: 51 */     return pos + 1;
/*  35:    */   }
/*  36:    */   
/*  37:    */   private final int jjStartNfaWithStates_0(int pos, int kind, int state)
/*  38:    */   {
/*  39: 55 */     this.jjmatchedKind = kind;
/*  40: 56 */     this.jjmatchedPos = pos;
/*  41:    */     try
/*  42:    */     {
/*  43: 57 */       this.curChar = this.input_stream.readChar();
/*  44:    */     }
/*  45:    */     catch (IOException e)
/*  46:    */     {
/*  47: 58 */       return pos + 1;
/*  48:    */     }
/*  49: 59 */     return jjMoveNfa_0(state, pos + 1);
/*  50:    */   }
/*  51:    */   
/*  52:    */   private final int jjMoveStringLiteralDfa0_0()
/*  53:    */   {
/*  54: 63 */     switch (this.curChar)
/*  55:    */     {
/*  56:    */     case '\n': 
/*  57: 66 */       return jjStartNfaWithStates_0(0, 2, 2);
/*  58:    */     case '\r': 
/*  59: 68 */       return jjStartNfaWithStates_0(0, 1, 2);
/*  60:    */     case '"': 
/*  61: 70 */       return jjStopAtPos(0, 15);
/*  62:    */     case '(': 
/*  63: 72 */       return jjStopAtPos(0, 6);
/*  64:    */     case ';': 
/*  65: 74 */       return jjStopAtPos(0, 3);
/*  66:    */     case '=': 
/*  67: 76 */       return jjStopAtPos(0, 4);
/*  68:    */     }
/*  69: 78 */     return jjMoveNfa_0(3, 0);
/*  70:    */   }
/*  71:    */   
/*  72:    */   private final void jjCheckNAdd(int state)
/*  73:    */   {
/*  74: 83 */     if (this.jjrounds[state] != this.jjround)
/*  75:    */     {
/*  76: 85 */       this.jjstateSet[(this.jjnewStateCnt++)] = state;
/*  77: 86 */       this.jjrounds[state] = this.jjround;
/*  78:    */     }
/*  79:    */   }
/*  80:    */   
/*  81:    */   private final void jjAddStates(int start, int end)
/*  82:    */   {
/*  83:    */     do
/*  84:    */     {
/*  85: 92 */       this.jjstateSet[(this.jjnewStateCnt++)] = jjnextStates[start];
/*  86: 93 */     } while (start++ != end);
/*  87:    */   }
/*  88:    */   
/*  89:    */   private final void jjCheckNAddTwoStates(int state1, int state2)
/*  90:    */   {
/*  91: 97 */     jjCheckNAdd(state1);
/*  92: 98 */     jjCheckNAdd(state2);
/*  93:    */   }
/*  94:    */   
/*  95:    */   private final void jjCheckNAddStates(int start, int end)
/*  96:    */   {
/*  97:    */     do
/*  98:    */     {
/*  99:103 */       jjCheckNAdd(jjnextStates[start]);
/* 100:104 */     } while (start++ != end);
/* 101:    */   }
/* 102:    */   
/* 103:    */   private final void jjCheckNAddStates(int start)
/* 104:    */   {
/* 105:108 */     jjCheckNAdd(jjnextStates[start]);
/* 106:109 */     jjCheckNAdd(jjnextStates[(start + 1)]);
/* 107:    */   }
/* 108:    */   
/* 109:111 */   static final long[] jjbitVec0 = { 0L, 0L, -1L, -1L };
/* 110:    */   
/* 111:    */   private final int jjMoveNfa_0(int startState, int curPos)
/* 112:    */   {
/* 113:117 */     int startsAt = 0;
/* 114:118 */     this.jjnewStateCnt = 3;
/* 115:119 */     int i = 1;
/* 116:120 */     this.jjstateSet[0] = startState;
/* 117:121 */     int kind = 2147483647;
/* 118:    */     for (;;)
/* 119:    */     {
/* 120:124 */       if (++this.jjround == 2147483647) {
/* 121:125 */         ReInitRounds();
/* 122:    */       }
/* 123:126 */       if (this.curChar < '@')
/* 124:    */       {
/* 125:128 */         long l = 1L << this.curChar;
/* 126:    */         do
/* 127:    */         {
/* 128:131 */           switch (this.jjstateSet[(--i)])
/* 129:    */           {
/* 130:    */           case 3: 
/* 131:134 */             if ((0xFFFFFDFF & l) != 0L)
/* 132:    */             {
/* 133:136 */               if (kind > 20) {
/* 134:137 */                 kind = 20;
/* 135:    */               }
/* 136:138 */               jjCheckNAdd(2);
/* 137:    */             }
/* 138:140 */             else if ((0x200 & l) != 0L)
/* 139:    */             {
/* 140:142 */               if (kind > 5) {
/* 141:143 */                 kind = 5;
/* 142:    */               }
/* 143:144 */               jjCheckNAdd(0);
/* 144:    */             }
/* 145:146 */             if ((0x0 & l) != 0L)
/* 146:    */             {
/* 147:148 */               if (kind > 19) {
/* 148:149 */                 kind = 19;
/* 149:    */               }
/* 150:150 */               jjCheckNAdd(1);
/* 151:    */             }
/* 152:    */             break;
/* 153:    */           case 0: 
/* 154:154 */             if ((0x200 & l) != 0L)
/* 155:    */             {
/* 156:156 */               kind = 5;
/* 157:157 */               jjCheckNAdd(0);
/* 158:    */             }
/* 159:158 */             break;
/* 160:    */           case 1: 
/* 161:160 */             if ((0x0 & l) != 0L)
/* 162:    */             {
/* 163:162 */               if (kind > 19) {
/* 164:163 */                 kind = 19;
/* 165:    */               }
/* 166:164 */               jjCheckNAdd(1);
/* 167:    */             }
/* 168:165 */             break;
/* 169:    */           case 2: 
/* 170:167 */             if ((0xFFFFFDFF & l) != 0L)
/* 171:    */             {
/* 172:169 */               if (kind > 20) {
/* 173:170 */                 kind = 20;
/* 174:    */               }
/* 175:171 */               jjCheckNAdd(2);
/* 176:    */             }
/* 177:    */             break;
/* 178:    */           }
/* 179:175 */         } while (i != startsAt);
/* 180:    */       }
/* 181:177 */       else if (this.curChar < '')
/* 182:    */       {
/* 183:179 */         long l = 1L << (this.curChar & 0x3F);
/* 184:    */         do
/* 185:    */         {
/* 186:182 */           switch (this.jjstateSet[(--i)])
/* 187:    */           {
/* 188:    */           case 2: 
/* 189:    */           case 3: 
/* 190:186 */             if ((0xC7FFFFFE & l) != 0L)
/* 191:    */             {
/* 192:188 */               kind = 20;
/* 193:189 */               jjCheckNAdd(2);
/* 194:    */             }
/* 195:    */             break;
/* 196:    */           }
/* 197:193 */         } while (i != startsAt);
/* 198:    */       }
/* 199:    */       else
/* 200:    */       {
/* 201:197 */         int i2 = (this.curChar & 0xFF) >> '\006';
/* 202:198 */         long l2 = 1L << (this.curChar & 0x3F);
/* 203:    */         do
/* 204:    */         {
/* 205:201 */           switch (this.jjstateSet[(--i)])
/* 206:    */           {
/* 207:    */           case 2: 
/* 208:    */           case 3: 
/* 209:205 */             if ((jjbitVec0[i2] & l2) != 0L)
/* 210:    */             {
/* 211:207 */               if (kind > 20) {
/* 212:208 */                 kind = 20;
/* 213:    */               }
/* 214:209 */               jjCheckNAdd(2);
/* 215:    */             }
/* 216:    */             break;
/* 217:    */           }
/* 218:213 */         } while (i != startsAt);
/* 219:    */       }
/* 220:215 */       if (kind != 2147483647)
/* 221:    */       {
/* 222:217 */         this.jjmatchedKind = kind;
/* 223:218 */         this.jjmatchedPos = curPos;
/* 224:219 */         kind = 2147483647;
/* 225:    */       }
/* 226:221 */       curPos++;
/* 227:222 */       if ((i = this.jjnewStateCnt) == (startsAt = 3 - (this.jjnewStateCnt = startsAt))) {
/* 228:223 */         return curPos;
/* 229:    */       }
/* 230:    */       try
/* 231:    */       {
/* 232:224 */         this.curChar = this.input_stream.readChar();
/* 233:    */       }
/* 234:    */       catch (IOException e) {}
/* 235:    */     }
/* 236:225 */     return curPos;
/* 237:    */   }
/* 238:    */   
/* 239:    */   private final int jjStopStringLiteralDfa_1(int pos, long active0)
/* 240:    */   {
/* 241:230 */     switch (pos)
/* 242:    */     {
/* 243:    */     }
/* 244:233 */     return -1;
/* 245:    */   }
/* 246:    */   
/* 247:    */   private final int jjStartNfa_1(int pos, long active0)
/* 248:    */   {
/* 249:238 */     return jjMoveNfa_1(jjStopStringLiteralDfa_1(pos, active0), pos + 1);
/* 250:    */   }
/* 251:    */   
/* 252:    */   private final int jjStartNfaWithStates_1(int pos, int kind, int state)
/* 253:    */   {
/* 254:242 */     this.jjmatchedKind = kind;
/* 255:243 */     this.jjmatchedPos = pos;
/* 256:    */     try
/* 257:    */     {
/* 258:244 */       this.curChar = this.input_stream.readChar();
/* 259:    */     }
/* 260:    */     catch (IOException e)
/* 261:    */     {
/* 262:245 */       return pos + 1;
/* 263:    */     }
/* 264:246 */     return jjMoveNfa_1(state, pos + 1);
/* 265:    */   }
/* 266:    */   
/* 267:    */   private final int jjMoveStringLiteralDfa0_1()
/* 268:    */   {
/* 269:250 */     switch (this.curChar)
/* 270:    */     {
/* 271:    */     case '(': 
/* 272:253 */       return jjStopAtPos(0, 9);
/* 273:    */     case ')': 
/* 274:255 */       return jjStopAtPos(0, 7);
/* 275:    */     }
/* 276:257 */     return jjMoveNfa_1(0, 0);
/* 277:    */   }
/* 278:    */   
/* 279:    */   private final int jjMoveNfa_1(int startState, int curPos)
/* 280:    */   {
/* 281:263 */     int startsAt = 0;
/* 282:264 */     this.jjnewStateCnt = 3;
/* 283:265 */     int i = 1;
/* 284:266 */     this.jjstateSet[0] = startState;
/* 285:267 */     int kind = 2147483647;
/* 286:    */     for (;;)
/* 287:    */     {
/* 288:270 */       if (++this.jjround == 2147483647) {
/* 289:271 */         ReInitRounds();
/* 290:    */       }
/* 291:272 */       if (this.curChar < '@')
/* 292:    */       {
/* 293:274 */         long l = 1L << this.curChar;
/* 294:    */         do
/* 295:    */         {
/* 296:277 */           switch (this.jjstateSet[(--i)])
/* 297:    */           {
/* 298:    */           case 0: 
/* 299:280 */             if (kind > 10) {
/* 300:281 */               kind = 10;
/* 301:    */             }
/* 302:    */             break;
/* 303:    */           case 1: 
/* 304:284 */             if (kind > 8) {
/* 305:285 */               kind = 8;
/* 306:    */             }
/* 307:    */             break;
/* 308:    */           }
/* 309:289 */         } while (i != startsAt);
/* 310:    */       }
/* 311:291 */       else if (this.curChar < '')
/* 312:    */       {
/* 313:293 */         long l = 1L << (this.curChar & 0x3F);
/* 314:    */         do
/* 315:    */         {
/* 316:296 */           switch (this.jjstateSet[(--i)])
/* 317:    */           {
/* 318:    */           case 0: 
/* 319:299 */             if (kind > 10) {
/* 320:300 */               kind = 10;
/* 321:    */             }
/* 322:301 */             if (this.curChar == '\\') {
/* 323:302 */               this.jjstateSet[(this.jjnewStateCnt++)] = 1;
/* 324:    */             }
/* 325:    */             break;
/* 326:    */           case 1: 
/* 327:305 */             if (kind > 8) {
/* 328:306 */               kind = 8;
/* 329:    */             }
/* 330:    */             break;
/* 331:    */           case 2: 
/* 332:309 */             if (kind > 10) {
/* 333:310 */               kind = 10;
/* 334:    */             }
/* 335:    */             break;
/* 336:    */           }
/* 337:314 */         } while (i != startsAt);
/* 338:    */       }
/* 339:    */       else
/* 340:    */       {
/* 341:318 */         int i2 = (this.curChar & 0xFF) >> '\006';
/* 342:319 */         long l2 = 1L << (this.curChar & 0x3F);
/* 343:    */         do
/* 344:    */         {
/* 345:322 */           switch (this.jjstateSet[(--i)])
/* 346:    */           {
/* 347:    */           case 0: 
/* 348:325 */             if (((jjbitVec0[i2] & l2) != 0L) && (kind > 10)) {
/* 349:326 */               kind = 10;
/* 350:    */             }
/* 351:    */             break;
/* 352:    */           case 1: 
/* 353:329 */             if (((jjbitVec0[i2] & l2) != 0L) && (kind > 8)) {
/* 354:330 */               kind = 8;
/* 355:    */             }
/* 356:    */             break;
/* 357:    */           }
/* 358:334 */         } while (i != startsAt);
/* 359:    */       }
/* 360:336 */       if (kind != 2147483647)
/* 361:    */       {
/* 362:338 */         this.jjmatchedKind = kind;
/* 363:339 */         this.jjmatchedPos = curPos;
/* 364:340 */         kind = 2147483647;
/* 365:    */       }
/* 366:342 */       curPos++;
/* 367:343 */       if ((i = this.jjnewStateCnt) == (startsAt = 3 - (this.jjnewStateCnt = startsAt))) {
/* 368:344 */         return curPos;
/* 369:    */       }
/* 370:    */       try
/* 371:    */       {
/* 372:345 */         this.curChar = this.input_stream.readChar();
/* 373:    */       }
/* 374:    */       catch (IOException e) {}
/* 375:    */     }
/* 376:346 */     return curPos;
/* 377:    */   }
/* 378:    */   
/* 379:    */   private final int jjStopStringLiteralDfa_3(int pos, long active0)
/* 380:    */   {
/* 381:351 */     switch (pos)
/* 382:    */     {
/* 383:    */     }
/* 384:354 */     return -1;
/* 385:    */   }
/* 386:    */   
/* 387:    */   private final int jjStartNfa_3(int pos, long active0)
/* 388:    */   {
/* 389:359 */     return jjMoveNfa_3(jjStopStringLiteralDfa_3(pos, active0), pos + 1);
/* 390:    */   }
/* 391:    */   
/* 392:    */   private final int jjStartNfaWithStates_3(int pos, int kind, int state)
/* 393:    */   {
/* 394:363 */     this.jjmatchedKind = kind;
/* 395:364 */     this.jjmatchedPos = pos;
/* 396:    */     try
/* 397:    */     {
/* 398:365 */       this.curChar = this.input_stream.readChar();
/* 399:    */     }
/* 400:    */     catch (IOException e)
/* 401:    */     {
/* 402:366 */       return pos + 1;
/* 403:    */     }
/* 404:367 */     return jjMoveNfa_3(state, pos + 1);
/* 405:    */   }
/* 406:    */   
/* 407:    */   private final int jjMoveStringLiteralDfa0_3()
/* 408:    */   {
/* 409:371 */     switch (this.curChar)
/* 410:    */     {
/* 411:    */     case '"': 
/* 412:374 */       return jjStopAtPos(0, 18);
/* 413:    */     }
/* 414:376 */     return jjMoveNfa_3(0, 0);
/* 415:    */   }
/* 416:    */   
/* 417:    */   private final int jjMoveNfa_3(int startState, int curPos)
/* 418:    */   {
/* 419:382 */     int startsAt = 0;
/* 420:383 */     this.jjnewStateCnt = 3;
/* 421:384 */     int i = 1;
/* 422:385 */     this.jjstateSet[0] = startState;
/* 423:386 */     int kind = 2147483647;
/* 424:    */     for (;;)
/* 425:    */     {
/* 426:389 */       if (++this.jjround == 2147483647) {
/* 427:390 */         ReInitRounds();
/* 428:    */       }
/* 429:391 */       if (this.curChar < '@')
/* 430:    */       {
/* 431:393 */         long l = 1L << this.curChar;
/* 432:    */         do
/* 433:    */         {
/* 434:396 */           switch (this.jjstateSet[(--i)])
/* 435:    */           {
/* 436:    */           case 0: 
/* 437:    */           case 2: 
/* 438:400 */             if ((0xFFFFFFFF & l) != 0L)
/* 439:    */             {
/* 440:402 */               if (kind > 17) {
/* 441:403 */                 kind = 17;
/* 442:    */               }
/* 443:404 */               jjCheckNAdd(2);
/* 444:    */             }
/* 445:405 */             break;
/* 446:    */           case 1: 
/* 447:407 */             if (kind > 16) {
/* 448:408 */               kind = 16;
/* 449:    */             }
/* 450:    */             break;
/* 451:    */           }
/* 452:412 */         } while (i != startsAt);
/* 453:    */       }
/* 454:414 */       else if (this.curChar < '')
/* 455:    */       {
/* 456:416 */         long l = 1L << (this.curChar & 0x3F);
/* 457:    */         do
/* 458:    */         {
/* 459:419 */           switch (this.jjstateSet[(--i)])
/* 460:    */           {
/* 461:    */           case 0: 
/* 462:422 */             if ((0xEFFFFFFF & l) != 0L)
/* 463:    */             {
/* 464:424 */               if (kind > 17) {
/* 465:425 */                 kind = 17;
/* 466:    */               }
/* 467:426 */               jjCheckNAdd(2);
/* 468:    */             }
/* 469:428 */             else if (this.curChar == '\\')
/* 470:    */             {
/* 471:429 */               this.jjstateSet[(this.jjnewStateCnt++)] = 1;
/* 472:    */             }
/* 473:    */             break;
/* 474:    */           case 1: 
/* 475:432 */             if (kind > 16) {
/* 476:433 */               kind = 16;
/* 477:    */             }
/* 478:    */             break;
/* 479:    */           case 2: 
/* 480:436 */             if ((0xEFFFFFFF & l) != 0L)
/* 481:    */             {
/* 482:438 */               if (kind > 17) {
/* 483:439 */                 kind = 17;
/* 484:    */               }
/* 485:440 */               jjCheckNAdd(2);
/* 486:    */             }
/* 487:    */             break;
/* 488:    */           }
/* 489:444 */         } while (i != startsAt);
/* 490:    */       }
/* 491:    */       else
/* 492:    */       {
/* 493:448 */         int i2 = (this.curChar & 0xFF) >> '\006';
/* 494:449 */         long l2 = 1L << (this.curChar & 0x3F);
/* 495:    */         do
/* 496:    */         {
/* 497:452 */           switch (this.jjstateSet[(--i)])
/* 498:    */           {
/* 499:    */           case 0: 
/* 500:    */           case 2: 
/* 501:456 */             if ((jjbitVec0[i2] & l2) != 0L)
/* 502:    */             {
/* 503:458 */               if (kind > 17) {
/* 504:459 */                 kind = 17;
/* 505:    */               }
/* 506:460 */               jjCheckNAdd(2);
/* 507:    */             }
/* 508:461 */             break;
/* 509:    */           case 1: 
/* 510:463 */             if (((jjbitVec0[i2] & l2) != 0L) && (kind > 16)) {
/* 511:464 */               kind = 16;
/* 512:    */             }
/* 513:    */             break;
/* 514:    */           }
/* 515:468 */         } while (i != startsAt);
/* 516:    */       }
/* 517:470 */       if (kind != 2147483647)
/* 518:    */       {
/* 519:472 */         this.jjmatchedKind = kind;
/* 520:473 */         this.jjmatchedPos = curPos;
/* 521:474 */         kind = 2147483647;
/* 522:    */       }
/* 523:476 */       curPos++;
/* 524:477 */       if ((i = this.jjnewStateCnt) == (startsAt = 3 - (this.jjnewStateCnt = startsAt))) {
/* 525:478 */         return curPos;
/* 526:    */       }
/* 527:    */       try
/* 528:    */       {
/* 529:479 */         this.curChar = this.input_stream.readChar();
/* 530:    */       }
/* 531:    */       catch (IOException e) {}
/* 532:    */     }
/* 533:480 */     return curPos;
/* 534:    */   }
/* 535:    */   
/* 536:    */   private final int jjStopStringLiteralDfa_2(int pos, long active0)
/* 537:    */   {
/* 538:485 */     switch (pos)
/* 539:    */     {
/* 540:    */     }
/* 541:488 */     return -1;
/* 542:    */   }
/* 543:    */   
/* 544:    */   private final int jjStartNfa_2(int pos, long active0)
/* 545:    */   {
/* 546:493 */     return jjMoveNfa_2(jjStopStringLiteralDfa_2(pos, active0), pos + 1);
/* 547:    */   }
/* 548:    */   
/* 549:    */   private final int jjStartNfaWithStates_2(int pos, int kind, int state)
/* 550:    */   {
/* 551:497 */     this.jjmatchedKind = kind;
/* 552:498 */     this.jjmatchedPos = pos;
/* 553:    */     try
/* 554:    */     {
/* 555:499 */       this.curChar = this.input_stream.readChar();
/* 556:    */     }
/* 557:    */     catch (IOException e)
/* 558:    */     {
/* 559:500 */       return pos + 1;
/* 560:    */     }
/* 561:501 */     return jjMoveNfa_2(state, pos + 1);
/* 562:    */   }
/* 563:    */   
/* 564:    */   private final int jjMoveStringLiteralDfa0_2()
/* 565:    */   {
/* 566:505 */     switch (this.curChar)
/* 567:    */     {
/* 568:    */     case '(': 
/* 569:508 */       return jjStopAtPos(0, 12);
/* 570:    */     case ')': 
/* 571:510 */       return jjStopAtPos(0, 13);
/* 572:    */     }
/* 573:512 */     return jjMoveNfa_2(0, 0);
/* 574:    */   }
/* 575:    */   
/* 576:    */   private final int jjMoveNfa_2(int startState, int curPos)
/* 577:    */   {
/* 578:518 */     int startsAt = 0;
/* 579:519 */     this.jjnewStateCnt = 3;
/* 580:520 */     int i = 1;
/* 581:521 */     this.jjstateSet[0] = startState;
/* 582:522 */     int kind = 2147483647;
/* 583:    */     for (;;)
/* 584:    */     {
/* 585:525 */       if (++this.jjround == 2147483647) {
/* 586:526 */         ReInitRounds();
/* 587:    */       }
/* 588:527 */       if (this.curChar < '@')
/* 589:    */       {
/* 590:529 */         long l = 1L << this.curChar;
/* 591:    */         do
/* 592:    */         {
/* 593:532 */           switch (this.jjstateSet[(--i)])
/* 594:    */           {
/* 595:    */           case 0: 
/* 596:535 */             if (kind > 14) {
/* 597:536 */               kind = 14;
/* 598:    */             }
/* 599:    */             break;
/* 600:    */           case 1: 
/* 601:539 */             if (kind > 11) {
/* 602:540 */               kind = 11;
/* 603:    */             }
/* 604:    */             break;
/* 605:    */           }
/* 606:544 */         } while (i != startsAt);
/* 607:    */       }
/* 608:546 */       else if (this.curChar < '')
/* 609:    */       {
/* 610:548 */         long l = 1L << (this.curChar & 0x3F);
/* 611:    */         do
/* 612:    */         {
/* 613:551 */           switch (this.jjstateSet[(--i)])
/* 614:    */           {
/* 615:    */           case 0: 
/* 616:554 */             if (kind > 14) {
/* 617:555 */               kind = 14;
/* 618:    */             }
/* 619:556 */             if (this.curChar == '\\') {
/* 620:557 */               this.jjstateSet[(this.jjnewStateCnt++)] = 1;
/* 621:    */             }
/* 622:    */             break;
/* 623:    */           case 1: 
/* 624:560 */             if (kind > 11) {
/* 625:561 */               kind = 11;
/* 626:    */             }
/* 627:    */             break;
/* 628:    */           case 2: 
/* 629:564 */             if (kind > 14) {
/* 630:565 */               kind = 14;
/* 631:    */             }
/* 632:    */             break;
/* 633:    */           }
/* 634:569 */         } while (i != startsAt);
/* 635:    */       }
/* 636:    */       else
/* 637:    */       {
/* 638:573 */         int i2 = (this.curChar & 0xFF) >> '\006';
/* 639:574 */         long l2 = 1L << (this.curChar & 0x3F);
/* 640:    */         do
/* 641:    */         {
/* 642:577 */           switch (this.jjstateSet[(--i)])
/* 643:    */           {
/* 644:    */           case 0: 
/* 645:580 */             if (((jjbitVec0[i2] & l2) != 0L) && (kind > 14)) {
/* 646:581 */               kind = 14;
/* 647:    */             }
/* 648:    */             break;
/* 649:    */           case 1: 
/* 650:584 */             if (((jjbitVec0[i2] & l2) != 0L) && (kind > 11)) {
/* 651:585 */               kind = 11;
/* 652:    */             }
/* 653:    */             break;
/* 654:    */           }
/* 655:589 */         } while (i != startsAt);
/* 656:    */       }
/* 657:591 */       if (kind != 2147483647)
/* 658:    */       {
/* 659:593 */         this.jjmatchedKind = kind;
/* 660:594 */         this.jjmatchedPos = curPos;
/* 661:595 */         kind = 2147483647;
/* 662:    */       }
/* 663:597 */       curPos++;
/* 664:598 */       if ((i = this.jjnewStateCnt) == (startsAt = 3 - (this.jjnewStateCnt = startsAt))) {
/* 665:599 */         return curPos;
/* 666:    */       }
/* 667:    */       try
/* 668:    */       {
/* 669:600 */         this.curChar = this.input_stream.readChar();
/* 670:    */       }
/* 671:    */       catch (IOException e) {}
/* 672:    */     }
/* 673:601 */     return curPos;
/* 674:    */   }
/* 675:    */   
/* 676:604 */   static final int[] jjnextStates = new int[0];
/* 677:606 */   public static final String[] jjstrLiteralImages = { "", "\r", "\n", ";", "=", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null };
/* 678:609 */   public static final String[] lexStateNames = { "DEFAULT", "INCOMMENT", "NESTED_COMMENT", "INQUOTEDSTRING" };
/* 679:615 */   public static final int[] jjnewLexState = { -1, -1, -1, -1, -1, -1, 1, 0, -1, 2, -1, -1, -1, -1, -1, 3, -1, -1, 0, -1, -1, -1, -1 };
/* 680:618 */   static final long[] jjtoToken = { 1835039L };
/* 681:621 */   static final long[] jjtoSkip = { 160L };
/* 682:624 */   static final long[] jjtoSpecial = { 32L };
/* 683:627 */   static final long[] jjtoMore = { 261952L };
/* 684:    */   protected SimpleCharStream input_stream;
/* 685:631 */   private final int[] jjrounds = new int[3];
/* 686:632 */   private final int[] jjstateSet = new int[6];
/* 687:    */   StringBuffer image;
/* 688:    */   int jjimageLen;
/* 689:    */   int lengthOfMatch;
/* 690:    */   protected char curChar;
/* 691:    */   
/* 692:    */   public ContentDispositionParserTokenManager(SimpleCharStream stream)
/* 693:    */   {
/* 694:640 */     this.input_stream = stream;
/* 695:    */   }
/* 696:    */   
/* 697:    */   public ContentDispositionParserTokenManager(SimpleCharStream stream, int lexState)
/* 698:    */   {
/* 699:643 */     this(stream);
/* 700:644 */     SwitchTo(lexState);
/* 701:    */   }
/* 702:    */   
/* 703:    */   public void ReInit(SimpleCharStream stream)
/* 704:    */   {
/* 705:648 */     this.jjmatchedPos = (this.jjnewStateCnt = 0);
/* 706:649 */     this.curLexState = this.defaultLexState;
/* 707:650 */     this.input_stream = stream;
/* 708:651 */     ReInitRounds();
/* 709:    */   }
/* 710:    */   
/* 711:    */   private final void ReInitRounds()
/* 712:    */   {
/* 713:656 */     this.jjround = -2147483647;
/* 714:657 */     for (int i = 3; i-- > 0;) {
/* 715:658 */       this.jjrounds[i] = -2147483648;
/* 716:    */     }
/* 717:    */   }
/* 718:    */   
/* 719:    */   public void ReInit(SimpleCharStream stream, int lexState)
/* 720:    */   {
/* 721:662 */     ReInit(stream);
/* 722:663 */     SwitchTo(lexState);
/* 723:    */   }
/* 724:    */   
/* 725:    */   public void SwitchTo(int lexState)
/* 726:    */   {
/* 727:667 */     if ((lexState >= 4) || (lexState < 0)) {
/* 728:668 */       throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", 2);
/* 729:    */     }
/* 730:670 */     this.curLexState = lexState;
/* 731:    */   }
/* 732:    */   
/* 733:    */   protected Token jjFillToken()
/* 734:    */   {
/* 735:675 */     Token t = Token.newToken(this.jjmatchedKind);
/* 736:676 */     t.kind = this.jjmatchedKind;
/* 737:677 */     String im = jjstrLiteralImages[this.jjmatchedKind];
/* 738:678 */     t.image = (im == null ? this.input_stream.GetImage() : im);
/* 739:679 */     t.beginLine = this.input_stream.getBeginLine();
/* 740:680 */     t.beginColumn = this.input_stream.getBeginColumn();
/* 741:681 */     t.endLine = this.input_stream.getEndLine();
/* 742:682 */     t.endColumn = this.input_stream.getEndColumn();
/* 743:683 */     return t;
/* 744:    */   }
/* 745:    */   
/* 746:686 */   int curLexState = 0;
/* 747:687 */   int defaultLexState = 0;
/* 748:    */   int jjnewStateCnt;
/* 749:    */   int jjround;
/* 750:    */   int jjmatchedPos;
/* 751:    */   int jjmatchedKind;
/* 752:    */   
/* 753:    */   public Token getNextToken()
/* 754:    */   {
/* 755:696 */     Token specialToken = null;
/* 756:    */     
/* 757:698 */     int curPos = 0;
/* 758:    */     try
/* 759:    */     {
/* 760:705 */       this.curChar = this.input_stream.BeginToken();
/* 761:    */     }
/* 762:    */     catch (IOException e)
/* 763:    */     {
/* 764:709 */       this.jjmatchedKind = 0;
/* 765:710 */       Token matchedToken = jjFillToken();
/* 766:711 */       matchedToken.specialToken = specialToken;
/* 767:712 */       return matchedToken;
/* 768:    */     }
/* 769:714 */     this.image = null;
/* 770:715 */     this.jjimageLen = 0;
/* 771:    */     for (;;)
/* 772:    */     {
/* 773:719 */       switch (this.curLexState)
/* 774:    */       {
/* 775:    */       case 0: 
/* 776:722 */         this.jjmatchedKind = 2147483647;
/* 777:723 */         this.jjmatchedPos = 0;
/* 778:724 */         curPos = jjMoveStringLiteralDfa0_0();
/* 779:725 */         break;
/* 780:    */       case 1: 
/* 781:727 */         this.jjmatchedKind = 2147483647;
/* 782:728 */         this.jjmatchedPos = 0;
/* 783:729 */         curPos = jjMoveStringLiteralDfa0_1();
/* 784:730 */         break;
/* 785:    */       case 2: 
/* 786:732 */         this.jjmatchedKind = 2147483647;
/* 787:733 */         this.jjmatchedPos = 0;
/* 788:734 */         curPos = jjMoveStringLiteralDfa0_2();
/* 789:735 */         break;
/* 790:    */       case 3: 
/* 791:737 */         this.jjmatchedKind = 2147483647;
/* 792:738 */         this.jjmatchedPos = 0;
/* 793:739 */         curPos = jjMoveStringLiteralDfa0_3();
/* 794:    */       }
/* 795:742 */       if (this.jjmatchedKind != 2147483647)
/* 796:    */       {
/* 797:744 */         if (this.jjmatchedPos + 1 < curPos) {
/* 798:745 */           this.input_stream.backup(curPos - this.jjmatchedPos - 1);
/* 799:    */         }
/* 800:746 */         if ((jjtoToken[(this.jjmatchedKind >> 6)] & 1L << (this.jjmatchedKind & 0x3F)) != 0L)
/* 801:    */         {
/* 802:748 */           Token matchedToken = jjFillToken();
/* 803:749 */           matchedToken.specialToken = specialToken;
/* 804:750 */           TokenLexicalActions(matchedToken);
/* 805:751 */           if (jjnewLexState[this.jjmatchedKind] != -1) {
/* 806:752 */             this.curLexState = jjnewLexState[this.jjmatchedKind];
/* 807:    */           }
/* 808:753 */           return matchedToken;
/* 809:    */         }
/* 810:755 */         if ((jjtoSkip[(this.jjmatchedKind >> 6)] & 1L << (this.jjmatchedKind & 0x3F)) != 0L)
/* 811:    */         {
/* 812:757 */           if ((jjtoSpecial[(this.jjmatchedKind >> 6)] & 1L << (this.jjmatchedKind & 0x3F)) != 0L)
/* 813:    */           {
/* 814:759 */             Token matchedToken = jjFillToken();
/* 815:760 */             if (specialToken == null)
/* 816:    */             {
/* 817:761 */               specialToken = matchedToken;
/* 818:    */             }
/* 819:    */             else
/* 820:    */             {
/* 821:764 */               matchedToken.specialToken = specialToken;
/* 822:765 */               specialToken = specialToken.next = matchedToken;
/* 823:    */             }
/* 824:    */           }
/* 825:768 */           if (jjnewLexState[this.jjmatchedKind] == -1) {
/* 826:    */             break;
/* 827:    */           }
/* 828:769 */           this.curLexState = jjnewLexState[this.jjmatchedKind]; break;
/* 829:    */         }
/* 830:772 */         MoreLexicalActions();
/* 831:773 */         if (jjnewLexState[this.jjmatchedKind] != -1) {
/* 832:774 */           this.curLexState = jjnewLexState[this.jjmatchedKind];
/* 833:    */         }
/* 834:775 */         curPos = 0;
/* 835:776 */         this.jjmatchedKind = 2147483647;
/* 836:    */         try
/* 837:    */         {
/* 838:778 */           this.curChar = this.input_stream.readChar();
/* 839:    */         }
/* 840:    */         catch (IOException e1) {}
/* 841:    */       }
/* 842:    */     }
/* 843:783 */     int error_line = this.input_stream.getEndLine();
/* 844:784 */     int error_column = this.input_stream.getEndColumn();
/* 845:785 */     String error_after = null;
/* 846:786 */     boolean EOFSeen = false;
/* 847:    */     try
/* 848:    */     {
/* 849:787 */       this.input_stream.readChar();this.input_stream.backup(1);
/* 850:    */     }
/* 851:    */     catch (IOException e1)
/* 852:    */     {
/* 853:789 */       EOFSeen = true;
/* 854:790 */       error_after = curPos <= 1 ? "" : this.input_stream.GetImage();
/* 855:791 */       if ((this.curChar == '\n') || (this.curChar == '\r'))
/* 856:    */       {
/* 857:792 */         error_line++;
/* 858:793 */         error_column = 0;
/* 859:    */       }
/* 860:    */       else
/* 861:    */       {
/* 862:796 */         error_column++;
/* 863:    */       }
/* 864:    */     }
/* 865:798 */     if (!EOFSeen)
/* 866:    */     {
/* 867:799 */       this.input_stream.backup(1);
/* 868:800 */       error_after = curPos <= 1 ? "" : this.input_stream.GetImage();
/* 869:    */     }
/* 870:802 */     throw new TokenMgrError(EOFSeen, this.curLexState, error_line, error_column, error_after, this.curChar, 0);
/* 871:    */   }
/* 872:    */   
/* 873:    */   void MoreLexicalActions()
/* 874:    */   {
/* 875:809 */     this.jjimageLen += (this.lengthOfMatch = this.jjmatchedPos + 1);
/* 876:810 */     switch (this.jjmatchedKind)
/* 877:    */     {
/* 878:    */     case 8: 
/* 879:813 */       if (this.image == null) {
/* 880:814 */         this.image = new StringBuffer();
/* 881:    */       }
/* 882:815 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 883:816 */       this.jjimageLen = 0;
/* 884:817 */       this.image.deleteCharAt(this.image.length() - 2);
/* 885:818 */       break;
/* 886:    */     case 9: 
/* 887:820 */       if (this.image == null) {
/* 888:821 */         this.image = new StringBuffer();
/* 889:    */       }
/* 890:822 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 891:823 */       this.jjimageLen = 0;
/* 892:824 */       commentNest = 1;
/* 893:825 */       break;
/* 894:    */     case 11: 
/* 895:827 */       if (this.image == null) {
/* 896:828 */         this.image = new StringBuffer();
/* 897:    */       }
/* 898:829 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 899:830 */       this.jjimageLen = 0;
/* 900:831 */       this.image.deleteCharAt(this.image.length() - 2);
/* 901:832 */       break;
/* 902:    */     case 12: 
/* 903:834 */       if (this.image == null) {
/* 904:835 */         this.image = new StringBuffer();
/* 905:    */       }
/* 906:836 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 907:837 */       this.jjimageLen = 0;
/* 908:838 */       commentNest += 1;
/* 909:839 */       break;
/* 910:    */     case 13: 
/* 911:841 */       if (this.image == null) {
/* 912:842 */         this.image = new StringBuffer();
/* 913:    */       }
/* 914:843 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 915:844 */       this.jjimageLen = 0;
/* 916:845 */       commentNest -= 1;
/* 917:845 */       if (commentNest == 0) {
/* 918:845 */         SwitchTo(1);
/* 919:    */       }
/* 920:    */       break;
/* 921:    */     case 15: 
/* 922:848 */       if (this.image == null) {
/* 923:849 */         this.image = new StringBuffer();
/* 924:    */       }
/* 925:850 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 926:851 */       this.jjimageLen = 0;
/* 927:852 */       this.image.deleteCharAt(this.image.length() - 1);
/* 928:853 */       break;
/* 929:    */     case 16: 
/* 930:855 */       if (this.image == null) {
/* 931:856 */         this.image = new StringBuffer();
/* 932:    */       }
/* 933:857 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 934:858 */       this.jjimageLen = 0;
/* 935:859 */       this.image.deleteCharAt(this.image.length() - 2);
/* 936:860 */       break;
/* 937:    */     }
/* 938:    */   }
/* 939:    */   
/* 940:    */   void TokenLexicalActions(Token matchedToken)
/* 941:    */   {
/* 942:867 */     switch (this.jjmatchedKind)
/* 943:    */     {
/* 944:    */     case 18: 
/* 945:870 */       if (this.image == null) {
/* 946:871 */         this.image = new StringBuffer();
/* 947:    */       }
/* 948:872 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 949:873 */       matchedToken.image = this.image.substring(0, this.image.length() - 1);
/* 950:874 */       break;
/* 951:    */     }
/* 952:    */   }
/* 953:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.contentdisposition.parser.ContentDispositionParserTokenManager
 * JD-Core Version:    0.7.0.1
 */