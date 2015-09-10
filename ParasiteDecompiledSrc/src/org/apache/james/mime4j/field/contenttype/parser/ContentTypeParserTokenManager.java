/*   1:    */ package org.apache.james.mime4j.field.contenttype.parser;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.PrintStream;
/*   5:    */ 
/*   6:    */ public class ContentTypeParserTokenManager
/*   7:    */   implements ContentTypeParserConstants
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
/*  61: 70 */       return jjStopAtPos(0, 16);
/*  62:    */     case '(': 
/*  63: 72 */       return jjStopAtPos(0, 7);
/*  64:    */     case '/': 
/*  65: 74 */       return jjStopAtPos(0, 3);
/*  66:    */     case ';': 
/*  67: 76 */       return jjStopAtPos(0, 4);
/*  68:    */     case '=': 
/*  69: 78 */       return jjStopAtPos(0, 5);
/*  70:    */     }
/*  71: 80 */     return jjMoveNfa_0(3, 0);
/*  72:    */   }
/*  73:    */   
/*  74:    */   private final void jjCheckNAdd(int state)
/*  75:    */   {
/*  76: 85 */     if (this.jjrounds[state] != this.jjround)
/*  77:    */     {
/*  78: 87 */       this.jjstateSet[(this.jjnewStateCnt++)] = state;
/*  79: 88 */       this.jjrounds[state] = this.jjround;
/*  80:    */     }
/*  81:    */   }
/*  82:    */   
/*  83:    */   private final void jjAddStates(int start, int end)
/*  84:    */   {
/*  85:    */     do
/*  86:    */     {
/*  87: 94 */       this.jjstateSet[(this.jjnewStateCnt++)] = jjnextStates[start];
/*  88: 95 */     } while (start++ != end);
/*  89:    */   }
/*  90:    */   
/*  91:    */   private final void jjCheckNAddTwoStates(int state1, int state2)
/*  92:    */   {
/*  93: 99 */     jjCheckNAdd(state1);
/*  94:100 */     jjCheckNAdd(state2);
/*  95:    */   }
/*  96:    */   
/*  97:    */   private final void jjCheckNAddStates(int start, int end)
/*  98:    */   {
/*  99:    */     do
/* 100:    */     {
/* 101:105 */       jjCheckNAdd(jjnextStates[start]);
/* 102:106 */     } while (start++ != end);
/* 103:    */   }
/* 104:    */   
/* 105:    */   private final void jjCheckNAddStates(int start)
/* 106:    */   {
/* 107:110 */     jjCheckNAdd(jjnextStates[start]);
/* 108:111 */     jjCheckNAdd(jjnextStates[(start + 1)]);
/* 109:    */   }
/* 110:    */   
/* 111:113 */   static final long[] jjbitVec0 = { 0L, 0L, -1L, -1L };
/* 112:    */   
/* 113:    */   private final int jjMoveNfa_0(int startState, int curPos)
/* 114:    */   {
/* 115:119 */     int startsAt = 0;
/* 116:120 */     this.jjnewStateCnt = 3;
/* 117:121 */     int i = 1;
/* 118:122 */     this.jjstateSet[0] = startState;
/* 119:123 */     int kind = 2147483647;
/* 120:    */     for (;;)
/* 121:    */     {
/* 122:126 */       if (++this.jjround == 2147483647) {
/* 123:127 */         ReInitRounds();
/* 124:    */       }
/* 125:128 */       if (this.curChar < '@')
/* 126:    */       {
/* 127:130 */         long l = 1L << this.curChar;
/* 128:    */         do
/* 129:    */         {
/* 130:133 */           switch (this.jjstateSet[(--i)])
/* 131:    */           {
/* 132:    */           case 3: 
/* 133:136 */             if ((0xFFFFFDFF & l) != 0L)
/* 134:    */             {
/* 135:138 */               if (kind > 21) {
/* 136:139 */                 kind = 21;
/* 137:    */               }
/* 138:140 */               jjCheckNAdd(2);
/* 139:    */             }
/* 140:142 */             else if ((0x200 & l) != 0L)
/* 141:    */             {
/* 142:144 */               if (kind > 6) {
/* 143:145 */                 kind = 6;
/* 144:    */               }
/* 145:146 */               jjCheckNAdd(0);
/* 146:    */             }
/* 147:148 */             if ((0x0 & l) != 0L)
/* 148:    */             {
/* 149:150 */               if (kind > 20) {
/* 150:151 */                 kind = 20;
/* 151:    */               }
/* 152:152 */               jjCheckNAdd(1);
/* 153:    */             }
/* 154:    */             break;
/* 155:    */           case 0: 
/* 156:156 */             if ((0x200 & l) != 0L)
/* 157:    */             {
/* 158:158 */               kind = 6;
/* 159:159 */               jjCheckNAdd(0);
/* 160:    */             }
/* 161:160 */             break;
/* 162:    */           case 1: 
/* 163:162 */             if ((0x0 & l) != 0L)
/* 164:    */             {
/* 165:164 */               if (kind > 20) {
/* 166:165 */                 kind = 20;
/* 167:    */               }
/* 168:166 */               jjCheckNAdd(1);
/* 169:    */             }
/* 170:167 */             break;
/* 171:    */           case 2: 
/* 172:169 */             if ((0xFFFFFDFF & l) != 0L)
/* 173:    */             {
/* 174:171 */               if (kind > 21) {
/* 175:172 */                 kind = 21;
/* 176:    */               }
/* 177:173 */               jjCheckNAdd(2);
/* 178:    */             }
/* 179:    */             break;
/* 180:    */           }
/* 181:177 */         } while (i != startsAt);
/* 182:    */       }
/* 183:179 */       else if (this.curChar < '')
/* 184:    */       {
/* 185:181 */         long l = 1L << (this.curChar & 0x3F);
/* 186:    */         do
/* 187:    */         {
/* 188:184 */           switch (this.jjstateSet[(--i)])
/* 189:    */           {
/* 190:    */           case 2: 
/* 191:    */           case 3: 
/* 192:188 */             if ((0xC7FFFFFE & l) != 0L)
/* 193:    */             {
/* 194:190 */               kind = 21;
/* 195:191 */               jjCheckNAdd(2);
/* 196:    */             }
/* 197:    */             break;
/* 198:    */           }
/* 199:195 */         } while (i != startsAt);
/* 200:    */       }
/* 201:    */       else
/* 202:    */       {
/* 203:199 */         int i2 = (this.curChar & 0xFF) >> '\006';
/* 204:200 */         long l2 = 1L << (this.curChar & 0x3F);
/* 205:    */         do
/* 206:    */         {
/* 207:203 */           switch (this.jjstateSet[(--i)])
/* 208:    */           {
/* 209:    */           case 2: 
/* 210:    */           case 3: 
/* 211:207 */             if ((jjbitVec0[i2] & l2) != 0L)
/* 212:    */             {
/* 213:209 */               if (kind > 21) {
/* 214:210 */                 kind = 21;
/* 215:    */               }
/* 216:211 */               jjCheckNAdd(2);
/* 217:    */             }
/* 218:    */             break;
/* 219:    */           }
/* 220:215 */         } while (i != startsAt);
/* 221:    */       }
/* 222:217 */       if (kind != 2147483647)
/* 223:    */       {
/* 224:219 */         this.jjmatchedKind = kind;
/* 225:220 */         this.jjmatchedPos = curPos;
/* 226:221 */         kind = 2147483647;
/* 227:    */       }
/* 228:223 */       curPos++;
/* 229:224 */       if ((i = this.jjnewStateCnt) == (startsAt = 3 - (this.jjnewStateCnt = startsAt))) {
/* 230:225 */         return curPos;
/* 231:    */       }
/* 232:    */       try
/* 233:    */       {
/* 234:226 */         this.curChar = this.input_stream.readChar();
/* 235:    */       }
/* 236:    */       catch (IOException e) {}
/* 237:    */     }
/* 238:227 */     return curPos;
/* 239:    */   }
/* 240:    */   
/* 241:    */   private final int jjStopStringLiteralDfa_1(int pos, long active0)
/* 242:    */   {
/* 243:232 */     switch (pos)
/* 244:    */     {
/* 245:    */     }
/* 246:235 */     return -1;
/* 247:    */   }
/* 248:    */   
/* 249:    */   private final int jjStartNfa_1(int pos, long active0)
/* 250:    */   {
/* 251:240 */     return jjMoveNfa_1(jjStopStringLiteralDfa_1(pos, active0), pos + 1);
/* 252:    */   }
/* 253:    */   
/* 254:    */   private final int jjStartNfaWithStates_1(int pos, int kind, int state)
/* 255:    */   {
/* 256:244 */     this.jjmatchedKind = kind;
/* 257:245 */     this.jjmatchedPos = pos;
/* 258:    */     try
/* 259:    */     {
/* 260:246 */       this.curChar = this.input_stream.readChar();
/* 261:    */     }
/* 262:    */     catch (IOException e)
/* 263:    */     {
/* 264:247 */       return pos + 1;
/* 265:    */     }
/* 266:248 */     return jjMoveNfa_1(state, pos + 1);
/* 267:    */   }
/* 268:    */   
/* 269:    */   private final int jjMoveStringLiteralDfa0_1()
/* 270:    */   {
/* 271:252 */     switch (this.curChar)
/* 272:    */     {
/* 273:    */     case '(': 
/* 274:255 */       return jjStopAtPos(0, 10);
/* 275:    */     case ')': 
/* 276:257 */       return jjStopAtPos(0, 8);
/* 277:    */     }
/* 278:259 */     return jjMoveNfa_1(0, 0);
/* 279:    */   }
/* 280:    */   
/* 281:    */   private final int jjMoveNfa_1(int startState, int curPos)
/* 282:    */   {
/* 283:265 */     int startsAt = 0;
/* 284:266 */     this.jjnewStateCnt = 3;
/* 285:267 */     int i = 1;
/* 286:268 */     this.jjstateSet[0] = startState;
/* 287:269 */     int kind = 2147483647;
/* 288:    */     for (;;)
/* 289:    */     {
/* 290:272 */       if (++this.jjround == 2147483647) {
/* 291:273 */         ReInitRounds();
/* 292:    */       }
/* 293:274 */       if (this.curChar < '@')
/* 294:    */       {
/* 295:276 */         long l = 1L << this.curChar;
/* 296:    */         do
/* 297:    */         {
/* 298:279 */           switch (this.jjstateSet[(--i)])
/* 299:    */           {
/* 300:    */           case 0: 
/* 301:282 */             if (kind > 11) {
/* 302:283 */               kind = 11;
/* 303:    */             }
/* 304:    */             break;
/* 305:    */           case 1: 
/* 306:286 */             if (kind > 9) {
/* 307:287 */               kind = 9;
/* 308:    */             }
/* 309:    */             break;
/* 310:    */           }
/* 311:291 */         } while (i != startsAt);
/* 312:    */       }
/* 313:293 */       else if (this.curChar < '')
/* 314:    */       {
/* 315:295 */         long l = 1L << (this.curChar & 0x3F);
/* 316:    */         do
/* 317:    */         {
/* 318:298 */           switch (this.jjstateSet[(--i)])
/* 319:    */           {
/* 320:    */           case 0: 
/* 321:301 */             if (kind > 11) {
/* 322:302 */               kind = 11;
/* 323:    */             }
/* 324:303 */             if (this.curChar == '\\') {
/* 325:304 */               this.jjstateSet[(this.jjnewStateCnt++)] = 1;
/* 326:    */             }
/* 327:    */             break;
/* 328:    */           case 1: 
/* 329:307 */             if (kind > 9) {
/* 330:308 */               kind = 9;
/* 331:    */             }
/* 332:    */             break;
/* 333:    */           case 2: 
/* 334:311 */             if (kind > 11) {
/* 335:312 */               kind = 11;
/* 336:    */             }
/* 337:    */             break;
/* 338:    */           }
/* 339:316 */         } while (i != startsAt);
/* 340:    */       }
/* 341:    */       else
/* 342:    */       {
/* 343:320 */         int i2 = (this.curChar & 0xFF) >> '\006';
/* 344:321 */         long l2 = 1L << (this.curChar & 0x3F);
/* 345:    */         do
/* 346:    */         {
/* 347:324 */           switch (this.jjstateSet[(--i)])
/* 348:    */           {
/* 349:    */           case 0: 
/* 350:327 */             if (((jjbitVec0[i2] & l2) != 0L) && (kind > 11)) {
/* 351:328 */               kind = 11;
/* 352:    */             }
/* 353:    */             break;
/* 354:    */           case 1: 
/* 355:331 */             if (((jjbitVec0[i2] & l2) != 0L) && (kind > 9)) {
/* 356:332 */               kind = 9;
/* 357:    */             }
/* 358:    */             break;
/* 359:    */           }
/* 360:336 */         } while (i != startsAt);
/* 361:    */       }
/* 362:338 */       if (kind != 2147483647)
/* 363:    */       {
/* 364:340 */         this.jjmatchedKind = kind;
/* 365:341 */         this.jjmatchedPos = curPos;
/* 366:342 */         kind = 2147483647;
/* 367:    */       }
/* 368:344 */       curPos++;
/* 369:345 */       if ((i = this.jjnewStateCnt) == (startsAt = 3 - (this.jjnewStateCnt = startsAt))) {
/* 370:346 */         return curPos;
/* 371:    */       }
/* 372:    */       try
/* 373:    */       {
/* 374:347 */         this.curChar = this.input_stream.readChar();
/* 375:    */       }
/* 376:    */       catch (IOException e) {}
/* 377:    */     }
/* 378:348 */     return curPos;
/* 379:    */   }
/* 380:    */   
/* 381:    */   private final int jjStopStringLiteralDfa_3(int pos, long active0)
/* 382:    */   {
/* 383:353 */     switch (pos)
/* 384:    */     {
/* 385:    */     }
/* 386:356 */     return -1;
/* 387:    */   }
/* 388:    */   
/* 389:    */   private final int jjStartNfa_3(int pos, long active0)
/* 390:    */   {
/* 391:361 */     return jjMoveNfa_3(jjStopStringLiteralDfa_3(pos, active0), pos + 1);
/* 392:    */   }
/* 393:    */   
/* 394:    */   private final int jjStartNfaWithStates_3(int pos, int kind, int state)
/* 395:    */   {
/* 396:365 */     this.jjmatchedKind = kind;
/* 397:366 */     this.jjmatchedPos = pos;
/* 398:    */     try
/* 399:    */     {
/* 400:367 */       this.curChar = this.input_stream.readChar();
/* 401:    */     }
/* 402:    */     catch (IOException e)
/* 403:    */     {
/* 404:368 */       return pos + 1;
/* 405:    */     }
/* 406:369 */     return jjMoveNfa_3(state, pos + 1);
/* 407:    */   }
/* 408:    */   
/* 409:    */   private final int jjMoveStringLiteralDfa0_3()
/* 410:    */   {
/* 411:373 */     switch (this.curChar)
/* 412:    */     {
/* 413:    */     case '"': 
/* 414:376 */       return jjStopAtPos(0, 19);
/* 415:    */     }
/* 416:378 */     return jjMoveNfa_3(0, 0);
/* 417:    */   }
/* 418:    */   
/* 419:    */   private final int jjMoveNfa_3(int startState, int curPos)
/* 420:    */   {
/* 421:384 */     int startsAt = 0;
/* 422:385 */     this.jjnewStateCnt = 3;
/* 423:386 */     int i = 1;
/* 424:387 */     this.jjstateSet[0] = startState;
/* 425:388 */     int kind = 2147483647;
/* 426:    */     for (;;)
/* 427:    */     {
/* 428:391 */       if (++this.jjround == 2147483647) {
/* 429:392 */         ReInitRounds();
/* 430:    */       }
/* 431:393 */       if (this.curChar < '@')
/* 432:    */       {
/* 433:395 */         long l = 1L << this.curChar;
/* 434:    */         do
/* 435:    */         {
/* 436:398 */           switch (this.jjstateSet[(--i)])
/* 437:    */           {
/* 438:    */           case 0: 
/* 439:    */           case 2: 
/* 440:402 */             if ((0xFFFFFFFF & l) != 0L)
/* 441:    */             {
/* 442:404 */               if (kind > 18) {
/* 443:405 */                 kind = 18;
/* 444:    */               }
/* 445:406 */               jjCheckNAdd(2);
/* 446:    */             }
/* 447:407 */             break;
/* 448:    */           case 1: 
/* 449:409 */             if (kind > 17) {
/* 450:410 */               kind = 17;
/* 451:    */             }
/* 452:    */             break;
/* 453:    */           }
/* 454:414 */         } while (i != startsAt);
/* 455:    */       }
/* 456:416 */       else if (this.curChar < '')
/* 457:    */       {
/* 458:418 */         long l = 1L << (this.curChar & 0x3F);
/* 459:    */         do
/* 460:    */         {
/* 461:421 */           switch (this.jjstateSet[(--i)])
/* 462:    */           {
/* 463:    */           case 0: 
/* 464:424 */             if ((0xEFFFFFFF & l) != 0L)
/* 465:    */             {
/* 466:426 */               if (kind > 18) {
/* 467:427 */                 kind = 18;
/* 468:    */               }
/* 469:428 */               jjCheckNAdd(2);
/* 470:    */             }
/* 471:430 */             else if (this.curChar == '\\')
/* 472:    */             {
/* 473:431 */               this.jjstateSet[(this.jjnewStateCnt++)] = 1;
/* 474:    */             }
/* 475:    */             break;
/* 476:    */           case 1: 
/* 477:434 */             if (kind > 17) {
/* 478:435 */               kind = 17;
/* 479:    */             }
/* 480:    */             break;
/* 481:    */           case 2: 
/* 482:438 */             if ((0xEFFFFFFF & l) != 0L)
/* 483:    */             {
/* 484:440 */               if (kind > 18) {
/* 485:441 */                 kind = 18;
/* 486:    */               }
/* 487:442 */               jjCheckNAdd(2);
/* 488:    */             }
/* 489:    */             break;
/* 490:    */           }
/* 491:446 */         } while (i != startsAt);
/* 492:    */       }
/* 493:    */       else
/* 494:    */       {
/* 495:450 */         int i2 = (this.curChar & 0xFF) >> '\006';
/* 496:451 */         long l2 = 1L << (this.curChar & 0x3F);
/* 497:    */         do
/* 498:    */         {
/* 499:454 */           switch (this.jjstateSet[(--i)])
/* 500:    */           {
/* 501:    */           case 0: 
/* 502:    */           case 2: 
/* 503:458 */             if ((jjbitVec0[i2] & l2) != 0L)
/* 504:    */             {
/* 505:460 */               if (kind > 18) {
/* 506:461 */                 kind = 18;
/* 507:    */               }
/* 508:462 */               jjCheckNAdd(2);
/* 509:    */             }
/* 510:463 */             break;
/* 511:    */           case 1: 
/* 512:465 */             if (((jjbitVec0[i2] & l2) != 0L) && (kind > 17)) {
/* 513:466 */               kind = 17;
/* 514:    */             }
/* 515:    */             break;
/* 516:    */           }
/* 517:470 */         } while (i != startsAt);
/* 518:    */       }
/* 519:472 */       if (kind != 2147483647)
/* 520:    */       {
/* 521:474 */         this.jjmatchedKind = kind;
/* 522:475 */         this.jjmatchedPos = curPos;
/* 523:476 */         kind = 2147483647;
/* 524:    */       }
/* 525:478 */       curPos++;
/* 526:479 */       if ((i = this.jjnewStateCnt) == (startsAt = 3 - (this.jjnewStateCnt = startsAt))) {
/* 527:480 */         return curPos;
/* 528:    */       }
/* 529:    */       try
/* 530:    */       {
/* 531:481 */         this.curChar = this.input_stream.readChar();
/* 532:    */       }
/* 533:    */       catch (IOException e) {}
/* 534:    */     }
/* 535:482 */     return curPos;
/* 536:    */   }
/* 537:    */   
/* 538:    */   private final int jjStopStringLiteralDfa_2(int pos, long active0)
/* 539:    */   {
/* 540:487 */     switch (pos)
/* 541:    */     {
/* 542:    */     }
/* 543:490 */     return -1;
/* 544:    */   }
/* 545:    */   
/* 546:    */   private final int jjStartNfa_2(int pos, long active0)
/* 547:    */   {
/* 548:495 */     return jjMoveNfa_2(jjStopStringLiteralDfa_2(pos, active0), pos + 1);
/* 549:    */   }
/* 550:    */   
/* 551:    */   private final int jjStartNfaWithStates_2(int pos, int kind, int state)
/* 552:    */   {
/* 553:499 */     this.jjmatchedKind = kind;
/* 554:500 */     this.jjmatchedPos = pos;
/* 555:    */     try
/* 556:    */     {
/* 557:501 */       this.curChar = this.input_stream.readChar();
/* 558:    */     }
/* 559:    */     catch (IOException e)
/* 560:    */     {
/* 561:502 */       return pos + 1;
/* 562:    */     }
/* 563:503 */     return jjMoveNfa_2(state, pos + 1);
/* 564:    */   }
/* 565:    */   
/* 566:    */   private final int jjMoveStringLiteralDfa0_2()
/* 567:    */   {
/* 568:507 */     switch (this.curChar)
/* 569:    */     {
/* 570:    */     case '(': 
/* 571:510 */       return jjStopAtPos(0, 13);
/* 572:    */     case ')': 
/* 573:512 */       return jjStopAtPos(0, 14);
/* 574:    */     }
/* 575:514 */     return jjMoveNfa_2(0, 0);
/* 576:    */   }
/* 577:    */   
/* 578:    */   private final int jjMoveNfa_2(int startState, int curPos)
/* 579:    */   {
/* 580:520 */     int startsAt = 0;
/* 581:521 */     this.jjnewStateCnt = 3;
/* 582:522 */     int i = 1;
/* 583:523 */     this.jjstateSet[0] = startState;
/* 584:524 */     int kind = 2147483647;
/* 585:    */     for (;;)
/* 586:    */     {
/* 587:527 */       if (++this.jjround == 2147483647) {
/* 588:528 */         ReInitRounds();
/* 589:    */       }
/* 590:529 */       if (this.curChar < '@')
/* 591:    */       {
/* 592:531 */         long l = 1L << this.curChar;
/* 593:    */         do
/* 594:    */         {
/* 595:534 */           switch (this.jjstateSet[(--i)])
/* 596:    */           {
/* 597:    */           case 0: 
/* 598:537 */             if (kind > 15) {
/* 599:538 */               kind = 15;
/* 600:    */             }
/* 601:    */             break;
/* 602:    */           case 1: 
/* 603:541 */             if (kind > 12) {
/* 604:542 */               kind = 12;
/* 605:    */             }
/* 606:    */             break;
/* 607:    */           }
/* 608:546 */         } while (i != startsAt);
/* 609:    */       }
/* 610:548 */       else if (this.curChar < '')
/* 611:    */       {
/* 612:550 */         long l = 1L << (this.curChar & 0x3F);
/* 613:    */         do
/* 614:    */         {
/* 615:553 */           switch (this.jjstateSet[(--i)])
/* 616:    */           {
/* 617:    */           case 0: 
/* 618:556 */             if (kind > 15) {
/* 619:557 */               kind = 15;
/* 620:    */             }
/* 621:558 */             if (this.curChar == '\\') {
/* 622:559 */               this.jjstateSet[(this.jjnewStateCnt++)] = 1;
/* 623:    */             }
/* 624:    */             break;
/* 625:    */           case 1: 
/* 626:562 */             if (kind > 12) {
/* 627:563 */               kind = 12;
/* 628:    */             }
/* 629:    */             break;
/* 630:    */           case 2: 
/* 631:566 */             if (kind > 15) {
/* 632:567 */               kind = 15;
/* 633:    */             }
/* 634:    */             break;
/* 635:    */           }
/* 636:571 */         } while (i != startsAt);
/* 637:    */       }
/* 638:    */       else
/* 639:    */       {
/* 640:575 */         int i2 = (this.curChar & 0xFF) >> '\006';
/* 641:576 */         long l2 = 1L << (this.curChar & 0x3F);
/* 642:    */         do
/* 643:    */         {
/* 644:579 */           switch (this.jjstateSet[(--i)])
/* 645:    */           {
/* 646:    */           case 0: 
/* 647:582 */             if (((jjbitVec0[i2] & l2) != 0L) && (kind > 15)) {
/* 648:583 */               kind = 15;
/* 649:    */             }
/* 650:    */             break;
/* 651:    */           case 1: 
/* 652:586 */             if (((jjbitVec0[i2] & l2) != 0L) && (kind > 12)) {
/* 653:587 */               kind = 12;
/* 654:    */             }
/* 655:    */             break;
/* 656:    */           }
/* 657:591 */         } while (i != startsAt);
/* 658:    */       }
/* 659:593 */       if (kind != 2147483647)
/* 660:    */       {
/* 661:595 */         this.jjmatchedKind = kind;
/* 662:596 */         this.jjmatchedPos = curPos;
/* 663:597 */         kind = 2147483647;
/* 664:    */       }
/* 665:599 */       curPos++;
/* 666:600 */       if ((i = this.jjnewStateCnt) == (startsAt = 3 - (this.jjnewStateCnt = startsAt))) {
/* 667:601 */         return curPos;
/* 668:    */       }
/* 669:    */       try
/* 670:    */       {
/* 671:602 */         this.curChar = this.input_stream.readChar();
/* 672:    */       }
/* 673:    */       catch (IOException e) {}
/* 674:    */     }
/* 675:603 */     return curPos;
/* 676:    */   }
/* 677:    */   
/* 678:606 */   static final int[] jjnextStates = new int[0];
/* 679:608 */   public static final String[] jjstrLiteralImages = { "", "\r", "\n", "/", ";", "=", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null };
/* 680:611 */   public static final String[] lexStateNames = { "DEFAULT", "INCOMMENT", "NESTED_COMMENT", "INQUOTEDSTRING" };
/* 681:617 */   public static final int[] jjnewLexState = { -1, -1, -1, -1, -1, -1, -1, 1, 0, -1, 2, -1, -1, -1, -1, -1, 3, -1, -1, 0, -1, -1, -1, -1 };
/* 682:620 */   static final long[] jjtoToken = { 3670079L };
/* 683:623 */   static final long[] jjtoSkip = { 320L };
/* 684:626 */   static final long[] jjtoSpecial = { 64L };
/* 685:629 */   static final long[] jjtoMore = { 523904L };
/* 686:    */   protected SimpleCharStream input_stream;
/* 687:633 */   private final int[] jjrounds = new int[3];
/* 688:634 */   private final int[] jjstateSet = new int[6];
/* 689:    */   StringBuffer image;
/* 690:    */   int jjimageLen;
/* 691:    */   int lengthOfMatch;
/* 692:    */   protected char curChar;
/* 693:    */   
/* 694:    */   public ContentTypeParserTokenManager(SimpleCharStream stream)
/* 695:    */   {
/* 696:642 */     this.input_stream = stream;
/* 697:    */   }
/* 698:    */   
/* 699:    */   public ContentTypeParserTokenManager(SimpleCharStream stream, int lexState)
/* 700:    */   {
/* 701:645 */     this(stream);
/* 702:646 */     SwitchTo(lexState);
/* 703:    */   }
/* 704:    */   
/* 705:    */   public void ReInit(SimpleCharStream stream)
/* 706:    */   {
/* 707:650 */     this.jjmatchedPos = (this.jjnewStateCnt = 0);
/* 708:651 */     this.curLexState = this.defaultLexState;
/* 709:652 */     this.input_stream = stream;
/* 710:653 */     ReInitRounds();
/* 711:    */   }
/* 712:    */   
/* 713:    */   private final void ReInitRounds()
/* 714:    */   {
/* 715:658 */     this.jjround = -2147483647;
/* 716:659 */     for (int i = 3; i-- > 0;) {
/* 717:660 */       this.jjrounds[i] = -2147483648;
/* 718:    */     }
/* 719:    */   }
/* 720:    */   
/* 721:    */   public void ReInit(SimpleCharStream stream, int lexState)
/* 722:    */   {
/* 723:664 */     ReInit(stream);
/* 724:665 */     SwitchTo(lexState);
/* 725:    */   }
/* 726:    */   
/* 727:    */   public void SwitchTo(int lexState)
/* 728:    */   {
/* 729:669 */     if ((lexState >= 4) || (lexState < 0)) {
/* 730:670 */       throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", 2);
/* 731:    */     }
/* 732:672 */     this.curLexState = lexState;
/* 733:    */   }
/* 734:    */   
/* 735:    */   protected Token jjFillToken()
/* 736:    */   {
/* 737:677 */     Token t = Token.newToken(this.jjmatchedKind);
/* 738:678 */     t.kind = this.jjmatchedKind;
/* 739:679 */     String im = jjstrLiteralImages[this.jjmatchedKind];
/* 740:680 */     t.image = (im == null ? this.input_stream.GetImage() : im);
/* 741:681 */     t.beginLine = this.input_stream.getBeginLine();
/* 742:682 */     t.beginColumn = this.input_stream.getBeginColumn();
/* 743:683 */     t.endLine = this.input_stream.getEndLine();
/* 744:684 */     t.endColumn = this.input_stream.getEndColumn();
/* 745:685 */     return t;
/* 746:    */   }
/* 747:    */   
/* 748:688 */   int curLexState = 0;
/* 749:689 */   int defaultLexState = 0;
/* 750:    */   int jjnewStateCnt;
/* 751:    */   int jjround;
/* 752:    */   int jjmatchedPos;
/* 753:    */   int jjmatchedKind;
/* 754:    */   
/* 755:    */   public Token getNextToken()
/* 756:    */   {
/* 757:698 */     Token specialToken = null;
/* 758:    */     
/* 759:700 */     int curPos = 0;
/* 760:    */     try
/* 761:    */     {
/* 762:707 */       this.curChar = this.input_stream.BeginToken();
/* 763:    */     }
/* 764:    */     catch (IOException e)
/* 765:    */     {
/* 766:711 */       this.jjmatchedKind = 0;
/* 767:712 */       Token matchedToken = jjFillToken();
/* 768:713 */       matchedToken.specialToken = specialToken;
/* 769:714 */       return matchedToken;
/* 770:    */     }
/* 771:716 */     this.image = null;
/* 772:717 */     this.jjimageLen = 0;
/* 773:    */     for (;;)
/* 774:    */     {
/* 775:721 */       switch (this.curLexState)
/* 776:    */       {
/* 777:    */       case 0: 
/* 778:724 */         this.jjmatchedKind = 2147483647;
/* 779:725 */         this.jjmatchedPos = 0;
/* 780:726 */         curPos = jjMoveStringLiteralDfa0_0();
/* 781:727 */         break;
/* 782:    */       case 1: 
/* 783:729 */         this.jjmatchedKind = 2147483647;
/* 784:730 */         this.jjmatchedPos = 0;
/* 785:731 */         curPos = jjMoveStringLiteralDfa0_1();
/* 786:732 */         break;
/* 787:    */       case 2: 
/* 788:734 */         this.jjmatchedKind = 2147483647;
/* 789:735 */         this.jjmatchedPos = 0;
/* 790:736 */         curPos = jjMoveStringLiteralDfa0_2();
/* 791:737 */         break;
/* 792:    */       case 3: 
/* 793:739 */         this.jjmatchedKind = 2147483647;
/* 794:740 */         this.jjmatchedPos = 0;
/* 795:741 */         curPos = jjMoveStringLiteralDfa0_3();
/* 796:    */       }
/* 797:744 */       if (this.jjmatchedKind != 2147483647)
/* 798:    */       {
/* 799:746 */         if (this.jjmatchedPos + 1 < curPos) {
/* 800:747 */           this.input_stream.backup(curPos - this.jjmatchedPos - 1);
/* 801:    */         }
/* 802:748 */         if ((jjtoToken[(this.jjmatchedKind >> 6)] & 1L << (this.jjmatchedKind & 0x3F)) != 0L)
/* 803:    */         {
/* 804:750 */           Token matchedToken = jjFillToken();
/* 805:751 */           matchedToken.specialToken = specialToken;
/* 806:752 */           TokenLexicalActions(matchedToken);
/* 807:753 */           if (jjnewLexState[this.jjmatchedKind] != -1) {
/* 808:754 */             this.curLexState = jjnewLexState[this.jjmatchedKind];
/* 809:    */           }
/* 810:755 */           return matchedToken;
/* 811:    */         }
/* 812:757 */         if ((jjtoSkip[(this.jjmatchedKind >> 6)] & 1L << (this.jjmatchedKind & 0x3F)) != 0L)
/* 813:    */         {
/* 814:759 */           if ((jjtoSpecial[(this.jjmatchedKind >> 6)] & 1L << (this.jjmatchedKind & 0x3F)) != 0L)
/* 815:    */           {
/* 816:761 */             Token matchedToken = jjFillToken();
/* 817:762 */             if (specialToken == null)
/* 818:    */             {
/* 819:763 */               specialToken = matchedToken;
/* 820:    */             }
/* 821:    */             else
/* 822:    */             {
/* 823:766 */               matchedToken.specialToken = specialToken;
/* 824:767 */               specialToken = specialToken.next = matchedToken;
/* 825:    */             }
/* 826:    */           }
/* 827:770 */           if (jjnewLexState[this.jjmatchedKind] == -1) {
/* 828:    */             break;
/* 829:    */           }
/* 830:771 */           this.curLexState = jjnewLexState[this.jjmatchedKind]; break;
/* 831:    */         }
/* 832:774 */         MoreLexicalActions();
/* 833:775 */         if (jjnewLexState[this.jjmatchedKind] != -1) {
/* 834:776 */           this.curLexState = jjnewLexState[this.jjmatchedKind];
/* 835:    */         }
/* 836:777 */         curPos = 0;
/* 837:778 */         this.jjmatchedKind = 2147483647;
/* 838:    */         try
/* 839:    */         {
/* 840:780 */           this.curChar = this.input_stream.readChar();
/* 841:    */         }
/* 842:    */         catch (IOException e1) {}
/* 843:    */       }
/* 844:    */     }
/* 845:785 */     int error_line = this.input_stream.getEndLine();
/* 846:786 */     int error_column = this.input_stream.getEndColumn();
/* 847:787 */     String error_after = null;
/* 848:788 */     boolean EOFSeen = false;
/* 849:    */     try
/* 850:    */     {
/* 851:789 */       this.input_stream.readChar();this.input_stream.backup(1);
/* 852:    */     }
/* 853:    */     catch (IOException e1)
/* 854:    */     {
/* 855:791 */       EOFSeen = true;
/* 856:792 */       error_after = curPos <= 1 ? "" : this.input_stream.GetImage();
/* 857:793 */       if ((this.curChar == '\n') || (this.curChar == '\r'))
/* 858:    */       {
/* 859:794 */         error_line++;
/* 860:795 */         error_column = 0;
/* 861:    */       }
/* 862:    */       else
/* 863:    */       {
/* 864:798 */         error_column++;
/* 865:    */       }
/* 866:    */     }
/* 867:800 */     if (!EOFSeen)
/* 868:    */     {
/* 869:801 */       this.input_stream.backup(1);
/* 870:802 */       error_after = curPos <= 1 ? "" : this.input_stream.GetImage();
/* 871:    */     }
/* 872:804 */     throw new TokenMgrError(EOFSeen, this.curLexState, error_line, error_column, error_after, this.curChar, 0);
/* 873:    */   }
/* 874:    */   
/* 875:    */   void MoreLexicalActions()
/* 876:    */   {
/* 877:811 */     this.jjimageLen += (this.lengthOfMatch = this.jjmatchedPos + 1);
/* 878:812 */     switch (this.jjmatchedKind)
/* 879:    */     {
/* 880:    */     case 9: 
/* 881:815 */       if (this.image == null) {
/* 882:816 */         this.image = new StringBuffer();
/* 883:    */       }
/* 884:817 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 885:818 */       this.jjimageLen = 0;
/* 886:819 */       this.image.deleteCharAt(this.image.length() - 2);
/* 887:820 */       break;
/* 888:    */     case 10: 
/* 889:822 */       if (this.image == null) {
/* 890:823 */         this.image = new StringBuffer();
/* 891:    */       }
/* 892:824 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 893:825 */       this.jjimageLen = 0;
/* 894:826 */       commentNest = 1;
/* 895:827 */       break;
/* 896:    */     case 12: 
/* 897:829 */       if (this.image == null) {
/* 898:830 */         this.image = new StringBuffer();
/* 899:    */       }
/* 900:831 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 901:832 */       this.jjimageLen = 0;
/* 902:833 */       this.image.deleteCharAt(this.image.length() - 2);
/* 903:834 */       break;
/* 904:    */     case 13: 
/* 905:836 */       if (this.image == null) {
/* 906:837 */         this.image = new StringBuffer();
/* 907:    */       }
/* 908:838 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 909:839 */       this.jjimageLen = 0;
/* 910:840 */       commentNest += 1;
/* 911:841 */       break;
/* 912:    */     case 14: 
/* 913:843 */       if (this.image == null) {
/* 914:844 */         this.image = new StringBuffer();
/* 915:    */       }
/* 916:845 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 917:846 */       this.jjimageLen = 0;
/* 918:847 */       commentNest -= 1;
/* 919:847 */       if (commentNest == 0) {
/* 920:847 */         SwitchTo(1);
/* 921:    */       }
/* 922:    */       break;
/* 923:    */     case 16: 
/* 924:850 */       if (this.image == null) {
/* 925:851 */         this.image = new StringBuffer();
/* 926:    */       }
/* 927:852 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 928:853 */       this.jjimageLen = 0;
/* 929:854 */       this.image.deleteCharAt(this.image.length() - 1);
/* 930:855 */       break;
/* 931:    */     case 17: 
/* 932:857 */       if (this.image == null) {
/* 933:858 */         this.image = new StringBuffer();
/* 934:    */       }
/* 935:859 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 936:860 */       this.jjimageLen = 0;
/* 937:861 */       this.image.deleteCharAt(this.image.length() - 2);
/* 938:862 */       break;
/* 939:    */     }
/* 940:    */   }
/* 941:    */   
/* 942:    */   void TokenLexicalActions(Token matchedToken)
/* 943:    */   {
/* 944:869 */     switch (this.jjmatchedKind)
/* 945:    */     {
/* 946:    */     case 19: 
/* 947:872 */       if (this.image == null) {
/* 948:873 */         this.image = new StringBuffer();
/* 949:    */       }
/* 950:874 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 951:875 */       matchedToken.image = this.image.substring(0, this.image.length() - 1);
/* 952:876 */       break;
/* 953:    */     }
/* 954:    */   }
/* 955:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.contenttype.parser.ContentTypeParserTokenManager
 * JD-Core Version:    0.7.0.1
 */