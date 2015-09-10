/*   1:    */ package org.apache.james.mime4j.field.language.parser;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.PrintStream;
/*   5:    */ 
/*   6:    */ public class ContentLanguageParserTokenManager
/*   7:    */   implements ContentLanguageParserConstants
/*   8:    */ {
/*   9:    */   int commentNest;
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
/*  56:    */     case '"': 
/*  57: 66 */       return jjStopAtPos(0, 13);
/*  58:    */     case '(': 
/*  59: 68 */       return jjStopAtPos(0, 4);
/*  60:    */     case ',': 
/*  61: 70 */       return jjStopAtPos(0, 1);
/*  62:    */     case '-': 
/*  63: 72 */       return jjStopAtPos(0, 2);
/*  64:    */     case '.': 
/*  65: 74 */       return jjStopAtPos(0, 20);
/*  66:    */     }
/*  67: 76 */     return jjMoveNfa_0(4, 0);
/*  68:    */   }
/*  69:    */   
/*  70:    */   private final void jjCheckNAdd(int state)
/*  71:    */   {
/*  72: 81 */     if (this.jjrounds[state] != this.jjround)
/*  73:    */     {
/*  74: 83 */       this.jjstateSet[(this.jjnewStateCnt++)] = state;
/*  75: 84 */       this.jjrounds[state] = this.jjround;
/*  76:    */     }
/*  77:    */   }
/*  78:    */   
/*  79:    */   private final void jjAddStates(int start, int end)
/*  80:    */   {
/*  81:    */     do
/*  82:    */     {
/*  83: 90 */       this.jjstateSet[(this.jjnewStateCnt++)] = jjnextStates[start];
/*  84: 91 */     } while (start++ != end);
/*  85:    */   }
/*  86:    */   
/*  87:    */   private final void jjCheckNAddTwoStates(int state1, int state2)
/*  88:    */   {
/*  89: 95 */     jjCheckNAdd(state1);
/*  90: 96 */     jjCheckNAdd(state2);
/*  91:    */   }
/*  92:    */   
/*  93:    */   private final void jjCheckNAddStates(int start, int end)
/*  94:    */   {
/*  95:    */     do
/*  96:    */     {
/*  97:101 */       jjCheckNAdd(jjnextStates[start]);
/*  98:102 */     } while (start++ != end);
/*  99:    */   }
/* 100:    */   
/* 101:    */   private final void jjCheckNAddStates(int start)
/* 102:    */   {
/* 103:106 */     jjCheckNAdd(jjnextStates[start]);
/* 104:107 */     jjCheckNAdd(jjnextStates[(start + 1)]);
/* 105:    */   }
/* 106:    */   
/* 107:    */   private final int jjMoveNfa_0(int startState, int curPos)
/* 108:    */   {
/* 109:112 */     int startsAt = 0;
/* 110:113 */     this.jjnewStateCnt = 4;
/* 111:114 */     int i = 1;
/* 112:115 */     this.jjstateSet[0] = startState;
/* 113:116 */     int kind = 2147483647;
/* 114:    */     for (;;)
/* 115:    */     {
/* 116:119 */       if (++this.jjround == 2147483647) {
/* 117:120 */         ReInitRounds();
/* 118:    */       }
/* 119:121 */       if (this.curChar < '@')
/* 120:    */       {
/* 121:123 */         long l = 1L << this.curChar;
/* 122:    */         do
/* 123:    */         {
/* 124:126 */           switch (this.jjstateSet[(--i)])
/* 125:    */           {
/* 126:    */           case 4: 
/* 127:129 */             if ((0x0 & l) != 0L)
/* 128:    */             {
/* 129:131 */               if (kind > 19) {
/* 130:132 */                 kind = 19;
/* 131:    */               }
/* 132:133 */               jjCheckNAdd(3);
/* 133:    */             }
/* 134:135 */             else if ((0x2600 & l) != 0L)
/* 135:    */             {
/* 136:137 */               if (kind > 3) {
/* 137:138 */                 kind = 3;
/* 138:    */               }
/* 139:139 */               jjCheckNAdd(0);
/* 140:    */             }
/* 141:141 */             if ((0x0 & l) != 0L)
/* 142:    */             {
/* 143:143 */               if (kind > 17) {
/* 144:144 */                 kind = 17;
/* 145:    */               }
/* 146:145 */               jjCheckNAdd(1);
/* 147:    */             }
/* 148:    */             break;
/* 149:    */           case 0: 
/* 150:149 */             if ((0x2600 & l) != 0L)
/* 151:    */             {
/* 152:151 */               kind = 3;
/* 153:152 */               jjCheckNAdd(0);
/* 154:    */             }
/* 155:153 */             break;
/* 156:    */           case 1: 
/* 157:155 */             if ((0x0 & l) != 0L)
/* 158:    */             {
/* 159:157 */               if (kind > 17) {
/* 160:158 */                 kind = 17;
/* 161:    */               }
/* 162:159 */               jjCheckNAdd(1);
/* 163:    */             }
/* 164:160 */             break;
/* 165:    */           case 3: 
/* 166:162 */             if ((0x0 & l) != 0L)
/* 167:    */             {
/* 168:164 */               if (kind > 19) {
/* 169:165 */                 kind = 19;
/* 170:    */               }
/* 171:166 */               jjCheckNAdd(3);
/* 172:    */             }
/* 173:    */             break;
/* 174:    */           }
/* 175:170 */         } while (i != startsAt);
/* 176:    */       }
/* 177:172 */       else if (this.curChar < '')
/* 178:    */       {
/* 179:174 */         long l = 1L << (this.curChar & 0x3F);
/* 180:    */         do
/* 181:    */         {
/* 182:177 */           switch (this.jjstateSet[(--i)])
/* 183:    */           {
/* 184:    */           case 4: 
/* 185:180 */             if ((0x7FFFFFE & l) != 0L)
/* 186:    */             {
/* 187:182 */               if (kind > 19) {
/* 188:183 */                 kind = 19;
/* 189:    */               }
/* 190:184 */               jjCheckNAdd(3);
/* 191:    */             }
/* 192:186 */             if ((0x7FFFFFE & l) != 0L)
/* 193:    */             {
/* 194:188 */               if (kind > 18) {
/* 195:189 */                 kind = 18;
/* 196:    */               }
/* 197:190 */               jjCheckNAdd(2);
/* 198:    */             }
/* 199:    */             break;
/* 200:    */           case 2: 
/* 201:194 */             if ((0x7FFFFFE & l) != 0L)
/* 202:    */             {
/* 203:196 */               if (kind > 18) {
/* 204:197 */                 kind = 18;
/* 205:    */               }
/* 206:198 */               jjCheckNAdd(2);
/* 207:    */             }
/* 208:199 */             break;
/* 209:    */           case 3: 
/* 210:201 */             if ((0x7FFFFFE & l) != 0L)
/* 211:    */             {
/* 212:203 */               if (kind > 19) {
/* 213:204 */                 kind = 19;
/* 214:    */               }
/* 215:205 */               jjCheckNAdd(3);
/* 216:    */             }
/* 217:    */             break;
/* 218:    */           }
/* 219:209 */         } while (i != startsAt);
/* 220:    */       }
/* 221:    */       else
/* 222:    */       {
/* 223:213 */         int i2 = (this.curChar & 0xFF) >> '\006';
/* 224:214 */         long l2 = 1L << (this.curChar & 0x3F);
/* 225:    */         do
/* 226:    */         {
/* 227:217 */           switch (this.jjstateSet[(--i)])
/* 228:    */           {
/* 229:    */           }
/* 230:221 */         } while (i != startsAt);
/* 231:    */       }
/* 232:223 */       if (kind != 2147483647)
/* 233:    */       {
/* 234:225 */         this.jjmatchedKind = kind;
/* 235:226 */         this.jjmatchedPos = curPos;
/* 236:227 */         kind = 2147483647;
/* 237:    */       }
/* 238:229 */       curPos++;
/* 239:230 */       if ((i = this.jjnewStateCnt) == (startsAt = 4 - (this.jjnewStateCnt = startsAt))) {
/* 240:231 */         return curPos;
/* 241:    */       }
/* 242:    */       try
/* 243:    */       {
/* 244:232 */         this.curChar = this.input_stream.readChar();
/* 245:    */       }
/* 246:    */       catch (IOException e) {}
/* 247:    */     }
/* 248:233 */     return curPos;
/* 249:    */   }
/* 250:    */   
/* 251:    */   private final int jjStopStringLiteralDfa_1(int pos, long active0)
/* 252:    */   {
/* 253:238 */     switch (pos)
/* 254:    */     {
/* 255:    */     }
/* 256:241 */     return -1;
/* 257:    */   }
/* 258:    */   
/* 259:    */   private final int jjStartNfa_1(int pos, long active0)
/* 260:    */   {
/* 261:246 */     return jjMoveNfa_1(jjStopStringLiteralDfa_1(pos, active0), pos + 1);
/* 262:    */   }
/* 263:    */   
/* 264:    */   private final int jjStartNfaWithStates_1(int pos, int kind, int state)
/* 265:    */   {
/* 266:250 */     this.jjmatchedKind = kind;
/* 267:251 */     this.jjmatchedPos = pos;
/* 268:    */     try
/* 269:    */     {
/* 270:252 */       this.curChar = this.input_stream.readChar();
/* 271:    */     }
/* 272:    */     catch (IOException e)
/* 273:    */     {
/* 274:253 */       return pos + 1;
/* 275:    */     }
/* 276:254 */     return jjMoveNfa_1(state, pos + 1);
/* 277:    */   }
/* 278:    */   
/* 279:    */   private final int jjMoveStringLiteralDfa0_1()
/* 280:    */   {
/* 281:258 */     switch (this.curChar)
/* 282:    */     {
/* 283:    */     case '(': 
/* 284:261 */       return jjStopAtPos(0, 7);
/* 285:    */     case ')': 
/* 286:263 */       return jjStopAtPos(0, 5);
/* 287:    */     }
/* 288:265 */     return jjMoveNfa_1(0, 0);
/* 289:    */   }
/* 290:    */   
/* 291:268 */   static final long[] jjbitVec0 = { 0L, 0L, -1L, -1L };
/* 292:    */   
/* 293:    */   private final int jjMoveNfa_1(int startState, int curPos)
/* 294:    */   {
/* 295:274 */     int startsAt = 0;
/* 296:275 */     this.jjnewStateCnt = 3;
/* 297:276 */     int i = 1;
/* 298:277 */     this.jjstateSet[0] = startState;
/* 299:278 */     int kind = 2147483647;
/* 300:    */     for (;;)
/* 301:    */     {
/* 302:281 */       if (++this.jjround == 2147483647) {
/* 303:282 */         ReInitRounds();
/* 304:    */       }
/* 305:283 */       if (this.curChar < '@')
/* 306:    */       {
/* 307:285 */         long l = 1L << this.curChar;
/* 308:    */         do
/* 309:    */         {
/* 310:288 */           switch (this.jjstateSet[(--i)])
/* 311:    */           {
/* 312:    */           case 0: 
/* 313:291 */             if (kind > 8) {
/* 314:292 */               kind = 8;
/* 315:    */             }
/* 316:    */             break;
/* 317:    */           case 1: 
/* 318:295 */             if (kind > 6) {
/* 319:296 */               kind = 6;
/* 320:    */             }
/* 321:    */             break;
/* 322:    */           }
/* 323:300 */         } while (i != startsAt);
/* 324:    */       }
/* 325:302 */       else if (this.curChar < '')
/* 326:    */       {
/* 327:304 */         long l = 1L << (this.curChar & 0x3F);
/* 328:    */         do
/* 329:    */         {
/* 330:307 */           switch (this.jjstateSet[(--i)])
/* 331:    */           {
/* 332:    */           case 0: 
/* 333:310 */             if (kind > 8) {
/* 334:311 */               kind = 8;
/* 335:    */             }
/* 336:312 */             if (this.curChar == '\\') {
/* 337:313 */               this.jjstateSet[(this.jjnewStateCnt++)] = 1;
/* 338:    */             }
/* 339:    */             break;
/* 340:    */           case 1: 
/* 341:316 */             if (kind > 6) {
/* 342:317 */               kind = 6;
/* 343:    */             }
/* 344:    */             break;
/* 345:    */           case 2: 
/* 346:320 */             if (kind > 8) {
/* 347:321 */               kind = 8;
/* 348:    */             }
/* 349:    */             break;
/* 350:    */           }
/* 351:325 */         } while (i != startsAt);
/* 352:    */       }
/* 353:    */       else
/* 354:    */       {
/* 355:329 */         int i2 = (this.curChar & 0xFF) >> '\006';
/* 356:330 */         long l2 = 1L << (this.curChar & 0x3F);
/* 357:    */         do
/* 358:    */         {
/* 359:333 */           switch (this.jjstateSet[(--i)])
/* 360:    */           {
/* 361:    */           case 0: 
/* 362:336 */             if (((jjbitVec0[i2] & l2) != 0L) && (kind > 8)) {
/* 363:337 */               kind = 8;
/* 364:    */             }
/* 365:    */             break;
/* 366:    */           case 1: 
/* 367:340 */             if (((jjbitVec0[i2] & l2) != 0L) && (kind > 6)) {
/* 368:341 */               kind = 6;
/* 369:    */             }
/* 370:    */             break;
/* 371:    */           }
/* 372:345 */         } while (i != startsAt);
/* 373:    */       }
/* 374:347 */       if (kind != 2147483647)
/* 375:    */       {
/* 376:349 */         this.jjmatchedKind = kind;
/* 377:350 */         this.jjmatchedPos = curPos;
/* 378:351 */         kind = 2147483647;
/* 379:    */       }
/* 380:353 */       curPos++;
/* 381:354 */       if ((i = this.jjnewStateCnt) == (startsAt = 3 - (this.jjnewStateCnt = startsAt))) {
/* 382:355 */         return curPos;
/* 383:    */       }
/* 384:    */       try
/* 385:    */       {
/* 386:356 */         this.curChar = this.input_stream.readChar();
/* 387:    */       }
/* 388:    */       catch (IOException e) {}
/* 389:    */     }
/* 390:357 */     return curPos;
/* 391:    */   }
/* 392:    */   
/* 393:    */   private final int jjStopStringLiteralDfa_3(int pos, long active0)
/* 394:    */   {
/* 395:362 */     switch (pos)
/* 396:    */     {
/* 397:    */     }
/* 398:365 */     return -1;
/* 399:    */   }
/* 400:    */   
/* 401:    */   private final int jjStartNfa_3(int pos, long active0)
/* 402:    */   {
/* 403:370 */     return jjMoveNfa_3(jjStopStringLiteralDfa_3(pos, active0), pos + 1);
/* 404:    */   }
/* 405:    */   
/* 406:    */   private final int jjStartNfaWithStates_3(int pos, int kind, int state)
/* 407:    */   {
/* 408:374 */     this.jjmatchedKind = kind;
/* 409:375 */     this.jjmatchedPos = pos;
/* 410:    */     try
/* 411:    */     {
/* 412:376 */       this.curChar = this.input_stream.readChar();
/* 413:    */     }
/* 414:    */     catch (IOException e)
/* 415:    */     {
/* 416:377 */       return pos + 1;
/* 417:    */     }
/* 418:378 */     return jjMoveNfa_3(state, pos + 1);
/* 419:    */   }
/* 420:    */   
/* 421:    */   private final int jjMoveStringLiteralDfa0_3()
/* 422:    */   {
/* 423:382 */     switch (this.curChar)
/* 424:    */     {
/* 425:    */     case '"': 
/* 426:385 */       return jjStopAtPos(0, 16);
/* 427:    */     }
/* 428:387 */     return jjMoveNfa_3(0, 0);
/* 429:    */   }
/* 430:    */   
/* 431:    */   private final int jjMoveNfa_3(int startState, int curPos)
/* 432:    */   {
/* 433:393 */     int startsAt = 0;
/* 434:394 */     this.jjnewStateCnt = 3;
/* 435:395 */     int i = 1;
/* 436:396 */     this.jjstateSet[0] = startState;
/* 437:397 */     int kind = 2147483647;
/* 438:    */     for (;;)
/* 439:    */     {
/* 440:400 */       if (++this.jjround == 2147483647) {
/* 441:401 */         ReInitRounds();
/* 442:    */       }
/* 443:402 */       if (this.curChar < '@')
/* 444:    */       {
/* 445:404 */         long l = 1L << this.curChar;
/* 446:    */         do
/* 447:    */         {
/* 448:407 */           switch (this.jjstateSet[(--i)])
/* 449:    */           {
/* 450:    */           case 0: 
/* 451:    */           case 2: 
/* 452:411 */             if ((0xFFFFFFFF & l) != 0L)
/* 453:    */             {
/* 454:413 */               if (kind > 15) {
/* 455:414 */                 kind = 15;
/* 456:    */               }
/* 457:415 */               jjCheckNAdd(2);
/* 458:    */             }
/* 459:416 */             break;
/* 460:    */           case 1: 
/* 461:418 */             if (kind > 14) {
/* 462:419 */               kind = 14;
/* 463:    */             }
/* 464:    */             break;
/* 465:    */           }
/* 466:423 */         } while (i != startsAt);
/* 467:    */       }
/* 468:425 */       else if (this.curChar < '')
/* 469:    */       {
/* 470:427 */         long l = 1L << (this.curChar & 0x3F);
/* 471:    */         do
/* 472:    */         {
/* 473:430 */           switch (this.jjstateSet[(--i)])
/* 474:    */           {
/* 475:    */           case 0: 
/* 476:433 */             if ((0xEFFFFFFF & l) != 0L)
/* 477:    */             {
/* 478:435 */               if (kind > 15) {
/* 479:436 */                 kind = 15;
/* 480:    */               }
/* 481:437 */               jjCheckNAdd(2);
/* 482:    */             }
/* 483:439 */             else if (this.curChar == '\\')
/* 484:    */             {
/* 485:440 */               this.jjstateSet[(this.jjnewStateCnt++)] = 1;
/* 486:    */             }
/* 487:    */             break;
/* 488:    */           case 1: 
/* 489:443 */             if (kind > 14) {
/* 490:444 */               kind = 14;
/* 491:    */             }
/* 492:    */             break;
/* 493:    */           case 2: 
/* 494:447 */             if ((0xEFFFFFFF & l) != 0L)
/* 495:    */             {
/* 496:449 */               if (kind > 15) {
/* 497:450 */                 kind = 15;
/* 498:    */               }
/* 499:451 */               jjCheckNAdd(2);
/* 500:    */             }
/* 501:    */             break;
/* 502:    */           }
/* 503:455 */         } while (i != startsAt);
/* 504:    */       }
/* 505:    */       else
/* 506:    */       {
/* 507:459 */         int i2 = (this.curChar & 0xFF) >> '\006';
/* 508:460 */         long l2 = 1L << (this.curChar & 0x3F);
/* 509:    */         do
/* 510:    */         {
/* 511:463 */           switch (this.jjstateSet[(--i)])
/* 512:    */           {
/* 513:    */           case 0: 
/* 514:    */           case 2: 
/* 515:467 */             if ((jjbitVec0[i2] & l2) != 0L)
/* 516:    */             {
/* 517:469 */               if (kind > 15) {
/* 518:470 */                 kind = 15;
/* 519:    */               }
/* 520:471 */               jjCheckNAdd(2);
/* 521:    */             }
/* 522:472 */             break;
/* 523:    */           case 1: 
/* 524:474 */             if (((jjbitVec0[i2] & l2) != 0L) && (kind > 14)) {
/* 525:475 */               kind = 14;
/* 526:    */             }
/* 527:    */             break;
/* 528:    */           }
/* 529:479 */         } while (i != startsAt);
/* 530:    */       }
/* 531:481 */       if (kind != 2147483647)
/* 532:    */       {
/* 533:483 */         this.jjmatchedKind = kind;
/* 534:484 */         this.jjmatchedPos = curPos;
/* 535:485 */         kind = 2147483647;
/* 536:    */       }
/* 537:487 */       curPos++;
/* 538:488 */       if ((i = this.jjnewStateCnt) == (startsAt = 3 - (this.jjnewStateCnt = startsAt))) {
/* 539:489 */         return curPos;
/* 540:    */       }
/* 541:    */       try
/* 542:    */       {
/* 543:490 */         this.curChar = this.input_stream.readChar();
/* 544:    */       }
/* 545:    */       catch (IOException e) {}
/* 546:    */     }
/* 547:491 */     return curPos;
/* 548:    */   }
/* 549:    */   
/* 550:    */   private final int jjStopStringLiteralDfa_2(int pos, long active0)
/* 551:    */   {
/* 552:496 */     switch (pos)
/* 553:    */     {
/* 554:    */     }
/* 555:499 */     return -1;
/* 556:    */   }
/* 557:    */   
/* 558:    */   private final int jjStartNfa_2(int pos, long active0)
/* 559:    */   {
/* 560:504 */     return jjMoveNfa_2(jjStopStringLiteralDfa_2(pos, active0), pos + 1);
/* 561:    */   }
/* 562:    */   
/* 563:    */   private final int jjStartNfaWithStates_2(int pos, int kind, int state)
/* 564:    */   {
/* 565:508 */     this.jjmatchedKind = kind;
/* 566:509 */     this.jjmatchedPos = pos;
/* 567:    */     try
/* 568:    */     {
/* 569:510 */       this.curChar = this.input_stream.readChar();
/* 570:    */     }
/* 571:    */     catch (IOException e)
/* 572:    */     {
/* 573:511 */       return pos + 1;
/* 574:    */     }
/* 575:512 */     return jjMoveNfa_2(state, pos + 1);
/* 576:    */   }
/* 577:    */   
/* 578:    */   private final int jjMoveStringLiteralDfa0_2()
/* 579:    */   {
/* 580:516 */     switch (this.curChar)
/* 581:    */     {
/* 582:    */     case '(': 
/* 583:519 */       return jjStopAtPos(0, 10);
/* 584:    */     case ')': 
/* 585:521 */       return jjStopAtPos(0, 11);
/* 586:    */     }
/* 587:523 */     return jjMoveNfa_2(0, 0);
/* 588:    */   }
/* 589:    */   
/* 590:    */   private final int jjMoveNfa_2(int startState, int curPos)
/* 591:    */   {
/* 592:529 */     int startsAt = 0;
/* 593:530 */     this.jjnewStateCnt = 3;
/* 594:531 */     int i = 1;
/* 595:532 */     this.jjstateSet[0] = startState;
/* 596:533 */     int kind = 2147483647;
/* 597:    */     for (;;)
/* 598:    */     {
/* 599:536 */       if (++this.jjround == 2147483647) {
/* 600:537 */         ReInitRounds();
/* 601:    */       }
/* 602:538 */       if (this.curChar < '@')
/* 603:    */       {
/* 604:540 */         long l = 1L << this.curChar;
/* 605:    */         do
/* 606:    */         {
/* 607:543 */           switch (this.jjstateSet[(--i)])
/* 608:    */           {
/* 609:    */           case 0: 
/* 610:546 */             if (kind > 12) {
/* 611:547 */               kind = 12;
/* 612:    */             }
/* 613:    */             break;
/* 614:    */           case 1: 
/* 615:550 */             if (kind > 9) {
/* 616:551 */               kind = 9;
/* 617:    */             }
/* 618:    */             break;
/* 619:    */           }
/* 620:555 */         } while (i != startsAt);
/* 621:    */       }
/* 622:557 */       else if (this.curChar < '')
/* 623:    */       {
/* 624:559 */         long l = 1L << (this.curChar & 0x3F);
/* 625:    */         do
/* 626:    */         {
/* 627:562 */           switch (this.jjstateSet[(--i)])
/* 628:    */           {
/* 629:    */           case 0: 
/* 630:565 */             if (kind > 12) {
/* 631:566 */               kind = 12;
/* 632:    */             }
/* 633:567 */             if (this.curChar == '\\') {
/* 634:568 */               this.jjstateSet[(this.jjnewStateCnt++)] = 1;
/* 635:    */             }
/* 636:    */             break;
/* 637:    */           case 1: 
/* 638:571 */             if (kind > 9) {
/* 639:572 */               kind = 9;
/* 640:    */             }
/* 641:    */             break;
/* 642:    */           case 2: 
/* 643:575 */             if (kind > 12) {
/* 644:576 */               kind = 12;
/* 645:    */             }
/* 646:    */             break;
/* 647:    */           }
/* 648:580 */         } while (i != startsAt);
/* 649:    */       }
/* 650:    */       else
/* 651:    */       {
/* 652:584 */         int i2 = (this.curChar & 0xFF) >> '\006';
/* 653:585 */         long l2 = 1L << (this.curChar & 0x3F);
/* 654:    */         do
/* 655:    */         {
/* 656:588 */           switch (this.jjstateSet[(--i)])
/* 657:    */           {
/* 658:    */           case 0: 
/* 659:591 */             if (((jjbitVec0[i2] & l2) != 0L) && (kind > 12)) {
/* 660:592 */               kind = 12;
/* 661:    */             }
/* 662:    */             break;
/* 663:    */           case 1: 
/* 664:595 */             if (((jjbitVec0[i2] & l2) != 0L) && (kind > 9)) {
/* 665:596 */               kind = 9;
/* 666:    */             }
/* 667:    */             break;
/* 668:    */           }
/* 669:600 */         } while (i != startsAt);
/* 670:    */       }
/* 671:602 */       if (kind != 2147483647)
/* 672:    */       {
/* 673:604 */         this.jjmatchedKind = kind;
/* 674:605 */         this.jjmatchedPos = curPos;
/* 675:606 */         kind = 2147483647;
/* 676:    */       }
/* 677:608 */       curPos++;
/* 678:609 */       if ((i = this.jjnewStateCnt) == (startsAt = 3 - (this.jjnewStateCnt = startsAt))) {
/* 679:610 */         return curPos;
/* 680:    */       }
/* 681:    */       try
/* 682:    */       {
/* 683:611 */         this.curChar = this.input_stream.readChar();
/* 684:    */       }
/* 685:    */       catch (IOException e) {}
/* 686:    */     }
/* 687:612 */     return curPos;
/* 688:    */   }
/* 689:    */   
/* 690:615 */   static final int[] jjnextStates = new int[0];
/* 691:617 */   public static final String[] jjstrLiteralImages = { "", ",", "-", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, ".", null, null };
/* 692:620 */   public static final String[] lexStateNames = { "DEFAULT", "INCOMMENT", "NESTED_COMMENT", "INQUOTEDSTRING" };
/* 693:626 */   public static final int[] jjnewLexState = { -1, -1, -1, -1, 1, 0, -1, 2, -1, -1, -1, -1, -1, 3, -1, -1, 0, -1, -1, -1, -1, -1, -1 };
/* 694:629 */   static final long[] jjtoToken = { 2031623L };
/* 695:632 */   static final long[] jjtoSkip = { 40L };
/* 696:635 */   static final long[] jjtoSpecial = { 8L };
/* 697:638 */   static final long[] jjtoMore = { 65488L };
/* 698:    */   protected SimpleCharStream input_stream;
/* 699:642 */   private final int[] jjrounds = new int[4];
/* 700:643 */   private final int[] jjstateSet = new int[8];
/* 701:    */   StringBuffer image;
/* 702:    */   int jjimageLen;
/* 703:    */   int lengthOfMatch;
/* 704:    */   protected char curChar;
/* 705:    */   
/* 706:    */   public ContentLanguageParserTokenManager(SimpleCharStream stream)
/* 707:    */   {
/* 708:651 */     this.input_stream = stream;
/* 709:    */   }
/* 710:    */   
/* 711:    */   public ContentLanguageParserTokenManager(SimpleCharStream stream, int lexState)
/* 712:    */   {
/* 713:654 */     this(stream);
/* 714:655 */     SwitchTo(lexState);
/* 715:    */   }
/* 716:    */   
/* 717:    */   public void ReInit(SimpleCharStream stream)
/* 718:    */   {
/* 719:659 */     this.jjmatchedPos = (this.jjnewStateCnt = 0);
/* 720:660 */     this.curLexState = this.defaultLexState;
/* 721:661 */     this.input_stream = stream;
/* 722:662 */     ReInitRounds();
/* 723:    */   }
/* 724:    */   
/* 725:    */   private final void ReInitRounds()
/* 726:    */   {
/* 727:667 */     this.jjround = -2147483647;
/* 728:668 */     for (int i = 4; i-- > 0;) {
/* 729:669 */       this.jjrounds[i] = -2147483648;
/* 730:    */     }
/* 731:    */   }
/* 732:    */   
/* 733:    */   public void ReInit(SimpleCharStream stream, int lexState)
/* 734:    */   {
/* 735:673 */     ReInit(stream);
/* 736:674 */     SwitchTo(lexState);
/* 737:    */   }
/* 738:    */   
/* 739:    */   public void SwitchTo(int lexState)
/* 740:    */   {
/* 741:678 */     if ((lexState >= 4) || (lexState < 0)) {
/* 742:679 */       throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", 2);
/* 743:    */     }
/* 744:681 */     this.curLexState = lexState;
/* 745:    */   }
/* 746:    */   
/* 747:    */   protected Token jjFillToken()
/* 748:    */   {
/* 749:686 */     Token t = Token.newToken(this.jjmatchedKind);
/* 750:687 */     t.kind = this.jjmatchedKind;
/* 751:688 */     String im = jjstrLiteralImages[this.jjmatchedKind];
/* 752:689 */     t.image = (im == null ? this.input_stream.GetImage() : im);
/* 753:690 */     t.beginLine = this.input_stream.getBeginLine();
/* 754:691 */     t.beginColumn = this.input_stream.getBeginColumn();
/* 755:692 */     t.endLine = this.input_stream.getEndLine();
/* 756:693 */     t.endColumn = this.input_stream.getEndColumn();
/* 757:694 */     return t;
/* 758:    */   }
/* 759:    */   
/* 760:697 */   int curLexState = 0;
/* 761:698 */   int defaultLexState = 0;
/* 762:    */   int jjnewStateCnt;
/* 763:    */   int jjround;
/* 764:    */   int jjmatchedPos;
/* 765:    */   int jjmatchedKind;
/* 766:    */   
/* 767:    */   public Token getNextToken()
/* 768:    */   {
/* 769:707 */     Token specialToken = null;
/* 770:    */     
/* 771:709 */     int curPos = 0;
/* 772:    */     try
/* 773:    */     {
/* 774:716 */       this.curChar = this.input_stream.BeginToken();
/* 775:    */     }
/* 776:    */     catch (IOException e)
/* 777:    */     {
/* 778:720 */       this.jjmatchedKind = 0;
/* 779:721 */       Token matchedToken = jjFillToken();
/* 780:722 */       matchedToken.specialToken = specialToken;
/* 781:723 */       return matchedToken;
/* 782:    */     }
/* 783:725 */     this.image = null;
/* 784:726 */     this.jjimageLen = 0;
/* 785:    */     for (;;)
/* 786:    */     {
/* 787:730 */       switch (this.curLexState)
/* 788:    */       {
/* 789:    */       case 0: 
/* 790:733 */         this.jjmatchedKind = 2147483647;
/* 791:734 */         this.jjmatchedPos = 0;
/* 792:735 */         curPos = jjMoveStringLiteralDfa0_0();
/* 793:736 */         break;
/* 794:    */       case 1: 
/* 795:738 */         this.jjmatchedKind = 2147483647;
/* 796:739 */         this.jjmatchedPos = 0;
/* 797:740 */         curPos = jjMoveStringLiteralDfa0_1();
/* 798:741 */         break;
/* 799:    */       case 2: 
/* 800:743 */         this.jjmatchedKind = 2147483647;
/* 801:744 */         this.jjmatchedPos = 0;
/* 802:745 */         curPos = jjMoveStringLiteralDfa0_2();
/* 803:746 */         break;
/* 804:    */       case 3: 
/* 805:748 */         this.jjmatchedKind = 2147483647;
/* 806:749 */         this.jjmatchedPos = 0;
/* 807:750 */         curPos = jjMoveStringLiteralDfa0_3();
/* 808:    */       }
/* 809:753 */       if (this.jjmatchedKind != 2147483647)
/* 810:    */       {
/* 811:755 */         if (this.jjmatchedPos + 1 < curPos) {
/* 812:756 */           this.input_stream.backup(curPos - this.jjmatchedPos - 1);
/* 813:    */         }
/* 814:757 */         if ((jjtoToken[(this.jjmatchedKind >> 6)] & 1L << (this.jjmatchedKind & 0x3F)) != 0L)
/* 815:    */         {
/* 816:759 */           Token matchedToken = jjFillToken();
/* 817:760 */           matchedToken.specialToken = specialToken;
/* 818:761 */           TokenLexicalActions(matchedToken);
/* 819:762 */           if (jjnewLexState[this.jjmatchedKind] != -1) {
/* 820:763 */             this.curLexState = jjnewLexState[this.jjmatchedKind];
/* 821:    */           }
/* 822:764 */           return matchedToken;
/* 823:    */         }
/* 824:766 */         if ((jjtoSkip[(this.jjmatchedKind >> 6)] & 1L << (this.jjmatchedKind & 0x3F)) != 0L)
/* 825:    */         {
/* 826:768 */           if ((jjtoSpecial[(this.jjmatchedKind >> 6)] & 1L << (this.jjmatchedKind & 0x3F)) != 0L)
/* 827:    */           {
/* 828:770 */             Token matchedToken = jjFillToken();
/* 829:771 */             if (specialToken == null)
/* 830:    */             {
/* 831:772 */               specialToken = matchedToken;
/* 832:    */             }
/* 833:    */             else
/* 834:    */             {
/* 835:775 */               matchedToken.specialToken = specialToken;
/* 836:776 */               specialToken = specialToken.next = matchedToken;
/* 837:    */             }
/* 838:    */           }
/* 839:779 */           if (jjnewLexState[this.jjmatchedKind] == -1) {
/* 840:    */             break;
/* 841:    */           }
/* 842:780 */           this.curLexState = jjnewLexState[this.jjmatchedKind]; break;
/* 843:    */         }
/* 844:783 */         MoreLexicalActions();
/* 845:784 */         if (jjnewLexState[this.jjmatchedKind] != -1) {
/* 846:785 */           this.curLexState = jjnewLexState[this.jjmatchedKind];
/* 847:    */         }
/* 848:786 */         curPos = 0;
/* 849:787 */         this.jjmatchedKind = 2147483647;
/* 850:    */         try
/* 851:    */         {
/* 852:789 */           this.curChar = this.input_stream.readChar();
/* 853:    */         }
/* 854:    */         catch (IOException e1) {}
/* 855:    */       }
/* 856:    */     }
/* 857:794 */     int error_line = this.input_stream.getEndLine();
/* 858:795 */     int error_column = this.input_stream.getEndColumn();
/* 859:796 */     String error_after = null;
/* 860:797 */     boolean EOFSeen = false;
/* 861:    */     try
/* 862:    */     {
/* 863:798 */       this.input_stream.readChar();this.input_stream.backup(1);
/* 864:    */     }
/* 865:    */     catch (IOException e1)
/* 866:    */     {
/* 867:800 */       EOFSeen = true;
/* 868:801 */       error_after = curPos <= 1 ? "" : this.input_stream.GetImage();
/* 869:802 */       if ((this.curChar == '\n') || (this.curChar == '\r'))
/* 870:    */       {
/* 871:803 */         error_line++;
/* 872:804 */         error_column = 0;
/* 873:    */       }
/* 874:    */       else
/* 875:    */       {
/* 876:807 */         error_column++;
/* 877:    */       }
/* 878:    */     }
/* 879:809 */     if (!EOFSeen)
/* 880:    */     {
/* 881:810 */       this.input_stream.backup(1);
/* 882:811 */       error_after = curPos <= 1 ? "" : this.input_stream.GetImage();
/* 883:    */     }
/* 884:813 */     throw new TokenMgrError(EOFSeen, this.curLexState, error_line, error_column, error_after, this.curChar, 0);
/* 885:    */   }
/* 886:    */   
/* 887:    */   void MoreLexicalActions()
/* 888:    */   {
/* 889:820 */     this.jjimageLen += (this.lengthOfMatch = this.jjmatchedPos + 1);
/* 890:821 */     switch (this.jjmatchedKind)
/* 891:    */     {
/* 892:    */     case 6: 
/* 893:824 */       if (this.image == null) {
/* 894:825 */         this.image = new StringBuffer();
/* 895:    */       }
/* 896:826 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 897:827 */       this.jjimageLen = 0;
/* 898:828 */       this.image.deleteCharAt(this.image.length() - 2);
/* 899:829 */       break;
/* 900:    */     case 7: 
/* 901:831 */       if (this.image == null) {
/* 902:832 */         this.image = new StringBuffer();
/* 903:    */       }
/* 904:833 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 905:834 */       this.jjimageLen = 0;
/* 906:835 */       this.commentNest = 1;
/* 907:836 */       break;
/* 908:    */     case 9: 
/* 909:838 */       if (this.image == null) {
/* 910:839 */         this.image = new StringBuffer();
/* 911:    */       }
/* 912:840 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 913:841 */       this.jjimageLen = 0;
/* 914:842 */       this.image.deleteCharAt(this.image.length() - 2);
/* 915:843 */       break;
/* 916:    */     case 10: 
/* 917:845 */       if (this.image == null) {
/* 918:846 */         this.image = new StringBuffer();
/* 919:    */       }
/* 920:847 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 921:848 */       this.jjimageLen = 0;
/* 922:849 */       this.commentNest += 1;
/* 923:850 */       break;
/* 924:    */     case 11: 
/* 925:852 */       if (this.image == null) {
/* 926:853 */         this.image = new StringBuffer();
/* 927:    */       }
/* 928:854 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 929:855 */       this.jjimageLen = 0;
/* 930:856 */       this.commentNest -= 1;
/* 931:856 */       if (this.commentNest == 0) {
/* 932:856 */         SwitchTo(1);
/* 933:    */       }
/* 934:    */       break;
/* 935:    */     case 13: 
/* 936:859 */       if (this.image == null) {
/* 937:860 */         this.image = new StringBuffer();
/* 938:    */       }
/* 939:861 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 940:862 */       this.jjimageLen = 0;
/* 941:863 */       this.image.deleteCharAt(this.image.length() - 1);
/* 942:864 */       break;
/* 943:    */     case 14: 
/* 944:866 */       if (this.image == null) {
/* 945:867 */         this.image = new StringBuffer();
/* 946:    */       }
/* 947:868 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 948:869 */       this.jjimageLen = 0;
/* 949:870 */       this.image.deleteCharAt(this.image.length() - 2);
/* 950:871 */       break;
/* 951:    */     }
/* 952:    */   }
/* 953:    */   
/* 954:    */   void TokenLexicalActions(Token matchedToken)
/* 955:    */   {
/* 956:878 */     switch (this.jjmatchedKind)
/* 957:    */     {
/* 958:    */     case 16: 
/* 959:881 */       if (this.image == null) {
/* 960:882 */         this.image = new StringBuffer();
/* 961:    */       }
/* 962:883 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 963:884 */       matchedToken.image = this.image.substring(0, this.image.length() - 1);
/* 964:885 */       break;
/* 965:    */     }
/* 966:    */   }
/* 967:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.language.parser.ContentLanguageParserTokenManager
 * JD-Core Version:    0.7.0.1
 */