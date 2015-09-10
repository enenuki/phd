/*   1:    */ package org.apache.james.mime4j.field.datetime.parser;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.PrintStream;
/*   5:    */ 
/*   6:    */ public class DateTimeParserTokenManager
/*   7:    */   implements DateTimeParserConstants
/*   8:    */ {
/*   9:    */   static int commentNest;
/*  10: 32 */   public PrintStream debugStream = System.out;
/*  11:    */   
/*  12:    */   public void setDebugStream(PrintStream ds)
/*  13:    */   {
/*  14: 33 */     this.debugStream = ds;
/*  15:    */   }
/*  16:    */   
/*  17:    */   private final int jjStopStringLiteralDfa_0(int pos, long active0)
/*  18:    */   {
/*  19: 36 */     switch (pos)
/*  20:    */     {
/*  21:    */     case 0: 
/*  22: 39 */       if ((active0 & 0xFE7CF7F0) != 0L)
/*  23:    */       {
/*  24: 41 */         this.jjmatchedKind = 35;
/*  25: 42 */         return -1;
/*  26:    */       }
/*  27: 44 */       return -1;
/*  28:    */     case 1: 
/*  29: 46 */       if ((active0 & 0xFE7CF7F0) != 0L)
/*  30:    */       {
/*  31: 48 */         if (this.jjmatchedPos == 0)
/*  32:    */         {
/*  33: 50 */           this.jjmatchedKind = 35;
/*  34: 51 */           this.jjmatchedPos = 0;
/*  35:    */         }
/*  36: 53 */         return -1;
/*  37:    */       }
/*  38: 55 */       return -1;
/*  39:    */     }
/*  40: 57 */     return -1;
/*  41:    */   }
/*  42:    */   
/*  43:    */   private final int jjStartNfa_0(int pos, long active0)
/*  44:    */   {
/*  45: 62 */     return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
/*  46:    */   }
/*  47:    */   
/*  48:    */   private final int jjStopAtPos(int pos, int kind)
/*  49:    */   {
/*  50: 66 */     this.jjmatchedKind = kind;
/*  51: 67 */     this.jjmatchedPos = pos;
/*  52: 68 */     return pos + 1;
/*  53:    */   }
/*  54:    */   
/*  55:    */   private final int jjStartNfaWithStates_0(int pos, int kind, int state)
/*  56:    */   {
/*  57: 72 */     this.jjmatchedKind = kind;
/*  58: 73 */     this.jjmatchedPos = pos;
/*  59:    */     try
/*  60:    */     {
/*  61: 74 */       this.curChar = this.input_stream.readChar();
/*  62:    */     }
/*  63:    */     catch (IOException e)
/*  64:    */     {
/*  65: 75 */       return pos + 1;
/*  66:    */     }
/*  67: 76 */     return jjMoveNfa_0(state, pos + 1);
/*  68:    */   }
/*  69:    */   
/*  70:    */   private final int jjMoveStringLiteralDfa0_0()
/*  71:    */   {
/*  72: 80 */     switch (this.curChar)
/*  73:    */     {
/*  74:    */     case '\n': 
/*  75: 83 */       return jjStopAtPos(0, 2);
/*  76:    */     case '\r': 
/*  77: 85 */       return jjStopAtPos(0, 1);
/*  78:    */     case '(': 
/*  79: 87 */       return jjStopAtPos(0, 37);
/*  80:    */     case ',': 
/*  81: 89 */       return jjStopAtPos(0, 3);
/*  82:    */     case ':': 
/*  83: 91 */       return jjStopAtPos(0, 23);
/*  84:    */     case 'A': 
/*  85: 93 */       return jjMoveStringLiteralDfa1_0(278528L);
/*  86:    */     case 'C': 
/*  87: 95 */       return jjMoveStringLiteralDfa1_0(1610612736L);
/*  88:    */     case 'D': 
/*  89: 97 */       return jjMoveStringLiteralDfa1_0(4194304L);
/*  90:    */     case 'E': 
/*  91: 99 */       return jjMoveStringLiteralDfa1_0(402653184L);
/*  92:    */     case 'F': 
/*  93:101 */       return jjMoveStringLiteralDfa1_0(4352L);
/*  94:    */     case 'G': 
/*  95:103 */       return jjMoveStringLiteralDfa1_0(67108864L);
/*  96:    */     case 'J': 
/*  97:105 */       return jjMoveStringLiteralDfa1_0(198656L);
/*  98:    */     case 'M': 
/*  99:107 */       return jjMoveStringLiteralDfa1_0(6442491920L);
/* 100:    */     case 'N': 
/* 101:109 */       return jjMoveStringLiteralDfa1_0(2097152L);
/* 102:    */     case 'O': 
/* 103:111 */       return jjMoveStringLiteralDfa1_0(1048576L);
/* 104:    */     case 'P': 
/* 105:113 */       return jjMoveStringLiteralDfa1_0(25769803776L);
/* 106:    */     case 'S': 
/* 107:115 */       return jjMoveStringLiteralDfa1_0(525824L);
/* 108:    */     case 'T': 
/* 109:117 */       return jjMoveStringLiteralDfa1_0(160L);
/* 110:    */     case 'U': 
/* 111:119 */       return jjMoveStringLiteralDfa1_0(33554432L);
/* 112:    */     case 'W': 
/* 113:121 */       return jjMoveStringLiteralDfa1_0(64L);
/* 114:    */     }
/* 115:123 */     return jjMoveNfa_0(0, 0);
/* 116:    */   }
/* 117:    */   
/* 118:    */   private final int jjMoveStringLiteralDfa1_0(long active0)
/* 119:    */   {
/* 120:    */     try
/* 121:    */     {
/* 122:128 */       this.curChar = this.input_stream.readChar();
/* 123:    */     }
/* 124:    */     catch (IOException e)
/* 125:    */     {
/* 126:130 */       jjStopStringLiteralDfa_0(0, active0);
/* 127:131 */       return 1;
/* 128:    */     }
/* 129:133 */     switch (this.curChar)
/* 130:    */     {
/* 131:    */     case 'D': 
/* 132:136 */       return jjMoveStringLiteralDfa2_0(active0, 22817013760L);
/* 133:    */     case 'M': 
/* 134:138 */       return jjMoveStringLiteralDfa2_0(active0, 67108864L);
/* 135:    */     case 'S': 
/* 136:140 */       return jjMoveStringLiteralDfa2_0(active0, 11408506880L);
/* 137:    */     case 'T': 
/* 138:142 */       if ((active0 & 0x2000000) != 0L) {
/* 139:143 */         return jjStopAtPos(1, 25);
/* 140:    */       }
/* 141:    */       break;
/* 142:    */     case 'a': 
/* 143:146 */       return jjMoveStringLiteralDfa2_0(active0, 43520L);
/* 144:    */     case 'c': 
/* 145:148 */       return jjMoveStringLiteralDfa2_0(active0, 1048576L);
/* 146:    */     case 'e': 
/* 147:150 */       return jjMoveStringLiteralDfa2_0(active0, 4722752L);
/* 148:    */     case 'h': 
/* 149:152 */       return jjMoveStringLiteralDfa2_0(active0, 128L);
/* 150:    */     case 'o': 
/* 151:154 */       return jjMoveStringLiteralDfa2_0(active0, 2097168L);
/* 152:    */     case 'p': 
/* 153:156 */       return jjMoveStringLiteralDfa2_0(active0, 16384L);
/* 154:    */     case 'r': 
/* 155:158 */       return jjMoveStringLiteralDfa2_0(active0, 256L);
/* 156:    */     case 'u': 
/* 157:160 */       return jjMoveStringLiteralDfa2_0(active0, 459808L);
/* 158:    */     }
/* 159:164 */     return jjStartNfa_0(0, active0);
/* 160:    */   }
/* 161:    */   
/* 162:    */   private final int jjMoveStringLiteralDfa2_0(long old0, long active0)
/* 163:    */   {
/* 164:168 */     if ((active0 &= old0) == 0L) {
/* 165:169 */       return jjStartNfa_0(0, old0);
/* 166:    */     }
/* 167:    */     try
/* 168:    */     {
/* 169:170 */       this.curChar = this.input_stream.readChar();
/* 170:    */     }
/* 171:    */     catch (IOException e)
/* 172:    */     {
/* 173:172 */       jjStopStringLiteralDfa_0(1, active0);
/* 174:173 */       return 2;
/* 175:    */     }
/* 176:175 */     switch (this.curChar)
/* 177:    */     {
/* 178:    */     case 'T': 
/* 179:178 */       if ((active0 & 0x4000000) != 0L) {
/* 180:179 */         return jjStopAtPos(2, 26);
/* 181:    */       }
/* 182:180 */       if ((active0 & 0x8000000) != 0L) {
/* 183:181 */         return jjStopAtPos(2, 27);
/* 184:    */       }
/* 185:182 */       if ((active0 & 0x10000000) != 0L) {
/* 186:183 */         return jjStopAtPos(2, 28);
/* 187:    */       }
/* 188:184 */       if ((active0 & 0x20000000) != 0L) {
/* 189:185 */         return jjStopAtPos(2, 29);
/* 190:    */       }
/* 191:186 */       if ((active0 & 0x40000000) != 0L) {
/* 192:187 */         return jjStopAtPos(2, 30);
/* 193:    */       }
/* 194:188 */       if ((active0 & 0x80000000) != 0L) {
/* 195:189 */         return jjStopAtPos(2, 31);
/* 196:    */       }
/* 197:190 */       if ((active0 & 0x0) != 0L) {
/* 198:191 */         return jjStopAtPos(2, 32);
/* 199:    */       }
/* 200:192 */       if ((active0 & 0x0) != 0L) {
/* 201:193 */         return jjStopAtPos(2, 33);
/* 202:    */       }
/* 203:194 */       if ((active0 & 0x0) != 0L) {
/* 204:195 */         return jjStopAtPos(2, 34);
/* 205:    */       }
/* 206:    */       break;
/* 207:    */     case 'b': 
/* 208:198 */       if ((active0 & 0x1000) != 0L) {
/* 209:199 */         return jjStopAtPos(2, 12);
/* 210:    */       }
/* 211:    */       break;
/* 212:    */     case 'c': 
/* 213:202 */       if ((active0 & 0x400000) != 0L) {
/* 214:203 */         return jjStopAtPos(2, 22);
/* 215:    */       }
/* 216:    */       break;
/* 217:    */     case 'd': 
/* 218:206 */       if ((active0 & 0x40) != 0L) {
/* 219:207 */         return jjStopAtPos(2, 6);
/* 220:    */       }
/* 221:    */       break;
/* 222:    */     case 'e': 
/* 223:210 */       if ((active0 & 0x20) != 0L) {
/* 224:211 */         return jjStopAtPos(2, 5);
/* 225:    */       }
/* 226:    */       break;
/* 227:    */     case 'g': 
/* 228:214 */       if ((active0 & 0x40000) != 0L) {
/* 229:215 */         return jjStopAtPos(2, 18);
/* 230:    */       }
/* 231:    */       break;
/* 232:    */     case 'i': 
/* 233:218 */       if ((active0 & 0x100) != 0L) {
/* 234:219 */         return jjStopAtPos(2, 8);
/* 235:    */       }
/* 236:    */       break;
/* 237:    */     case 'l': 
/* 238:222 */       if ((active0 & 0x20000) != 0L) {
/* 239:223 */         return jjStopAtPos(2, 17);
/* 240:    */       }
/* 241:    */       break;
/* 242:    */     case 'n': 
/* 243:226 */       if ((active0 & 0x10) != 0L) {
/* 244:227 */         return jjStopAtPos(2, 4);
/* 245:    */       }
/* 246:228 */       if ((active0 & 0x400) != 0L) {
/* 247:229 */         return jjStopAtPos(2, 10);
/* 248:    */       }
/* 249:230 */       if ((active0 & 0x800) != 0L) {
/* 250:231 */         return jjStopAtPos(2, 11);
/* 251:    */       }
/* 252:232 */       if ((active0 & 0x10000) != 0L) {
/* 253:233 */         return jjStopAtPos(2, 16);
/* 254:    */       }
/* 255:    */       break;
/* 256:    */     case 'p': 
/* 257:236 */       if ((active0 & 0x80000) != 0L) {
/* 258:237 */         return jjStopAtPos(2, 19);
/* 259:    */       }
/* 260:    */       break;
/* 261:    */     case 'r': 
/* 262:240 */       if ((active0 & 0x2000) != 0L) {
/* 263:241 */         return jjStopAtPos(2, 13);
/* 264:    */       }
/* 265:242 */       if ((active0 & 0x4000) != 0L) {
/* 266:243 */         return jjStopAtPos(2, 14);
/* 267:    */       }
/* 268:    */       break;
/* 269:    */     case 't': 
/* 270:246 */       if ((active0 & 0x200) != 0L) {
/* 271:247 */         return jjStopAtPos(2, 9);
/* 272:    */       }
/* 273:248 */       if ((active0 & 0x100000) != 0L) {
/* 274:249 */         return jjStopAtPos(2, 20);
/* 275:    */       }
/* 276:    */       break;
/* 277:    */     case 'u': 
/* 278:252 */       if ((active0 & 0x80) != 0L) {
/* 279:253 */         return jjStopAtPos(2, 7);
/* 280:    */       }
/* 281:    */       break;
/* 282:    */     case 'v': 
/* 283:256 */       if ((active0 & 0x200000) != 0L) {
/* 284:257 */         return jjStopAtPos(2, 21);
/* 285:    */       }
/* 286:    */       break;
/* 287:    */     case 'y': 
/* 288:260 */       if ((active0 & 0x8000) != 0L) {
/* 289:261 */         return jjStopAtPos(2, 15);
/* 290:    */       }
/* 291:    */       break;
/* 292:    */     }
/* 293:266 */     return jjStartNfa_0(1, active0);
/* 294:    */   }
/* 295:    */   
/* 296:    */   private final void jjCheckNAdd(int state)
/* 297:    */   {
/* 298:270 */     if (this.jjrounds[state] != this.jjround)
/* 299:    */     {
/* 300:272 */       this.jjstateSet[(this.jjnewStateCnt++)] = state;
/* 301:273 */       this.jjrounds[state] = this.jjround;
/* 302:    */     }
/* 303:    */   }
/* 304:    */   
/* 305:    */   private final void jjAddStates(int start, int end)
/* 306:    */   {
/* 307:    */     do
/* 308:    */     {
/* 309:279 */       this.jjstateSet[(this.jjnewStateCnt++)] = jjnextStates[start];
/* 310:280 */     } while (start++ != end);
/* 311:    */   }
/* 312:    */   
/* 313:    */   private final void jjCheckNAddTwoStates(int state1, int state2)
/* 314:    */   {
/* 315:284 */     jjCheckNAdd(state1);
/* 316:285 */     jjCheckNAdd(state2);
/* 317:    */   }
/* 318:    */   
/* 319:    */   private final void jjCheckNAddStates(int start, int end)
/* 320:    */   {
/* 321:    */     do
/* 322:    */     {
/* 323:290 */       jjCheckNAdd(jjnextStates[start]);
/* 324:291 */     } while (start++ != end);
/* 325:    */   }
/* 326:    */   
/* 327:    */   private final void jjCheckNAddStates(int start)
/* 328:    */   {
/* 329:295 */     jjCheckNAdd(jjnextStates[start]);
/* 330:296 */     jjCheckNAdd(jjnextStates[(start + 1)]);
/* 331:    */   }
/* 332:    */   
/* 333:    */   private final int jjMoveNfa_0(int startState, int curPos)
/* 334:    */   {
/* 335:301 */     int startsAt = 0;
/* 336:302 */     this.jjnewStateCnt = 4;
/* 337:303 */     int i = 1;
/* 338:304 */     this.jjstateSet[0] = startState;
/* 339:305 */     int kind = 2147483647;
/* 340:    */     for (;;)
/* 341:    */     {
/* 342:308 */       if (++this.jjround == 2147483647) {
/* 343:309 */         ReInitRounds();
/* 344:    */       }
/* 345:310 */       if (this.curChar < '@')
/* 346:    */       {
/* 347:312 */         long l = 1L << this.curChar;
/* 348:    */         do
/* 349:    */         {
/* 350:315 */           switch (this.jjstateSet[(--i)])
/* 351:    */           {
/* 352:    */           case 0: 
/* 353:318 */             if ((0x0 & l) != 0L)
/* 354:    */             {
/* 355:320 */               if (kind > 46) {
/* 356:321 */                 kind = 46;
/* 357:    */               }
/* 358:322 */               jjCheckNAdd(3);
/* 359:    */             }
/* 360:324 */             else if ((0x200 & l) != 0L)
/* 361:    */             {
/* 362:326 */               if (kind > 36) {
/* 363:327 */                 kind = 36;
/* 364:    */               }
/* 365:328 */               jjCheckNAdd(2);
/* 366:    */             }
/* 367:330 */             else if ((0x0 & l) != 0L)
/* 368:    */             {
/* 369:332 */               if (kind > 24) {
/* 370:333 */                 kind = 24;
/* 371:    */               }
/* 372:    */             }
/* 373:    */             break;
/* 374:    */           case 2: 
/* 375:337 */             if ((0x200 & l) != 0L)
/* 376:    */             {
/* 377:339 */               kind = 36;
/* 378:340 */               jjCheckNAdd(2);
/* 379:    */             }
/* 380:341 */             break;
/* 381:    */           case 3: 
/* 382:343 */             if ((0x0 & l) != 0L)
/* 383:    */             {
/* 384:345 */               kind = 46;
/* 385:346 */               jjCheckNAdd(3);
/* 386:    */             }
/* 387:    */             break;
/* 388:    */           }
/* 389:350 */         } while (i != startsAt);
/* 390:    */       }
/* 391:352 */       else if (this.curChar < '')
/* 392:    */       {
/* 393:354 */         long l = 1L << (this.curChar & 0x3F);
/* 394:    */         do
/* 395:    */         {
/* 396:357 */           switch (this.jjstateSet[(--i)])
/* 397:    */           {
/* 398:    */           case 0: 
/* 399:360 */             if ((0x7FFFBFE & l) != 0L) {
/* 400:361 */               kind = 35;
/* 401:    */             }
/* 402:    */             break;
/* 403:    */           }
/* 404:365 */         } while (i != startsAt);
/* 405:    */       }
/* 406:    */       else
/* 407:    */       {
/* 408:369 */         int i2 = (this.curChar & 0xFF) >> '\006';
/* 409:370 */         long l2 = 1L << (this.curChar & 0x3F);
/* 410:    */         do
/* 411:    */         {
/* 412:373 */           switch (this.jjstateSet[(--i)])
/* 413:    */           {
/* 414:    */           }
/* 415:377 */         } while (i != startsAt);
/* 416:    */       }
/* 417:379 */       if (kind != 2147483647)
/* 418:    */       {
/* 419:381 */         this.jjmatchedKind = kind;
/* 420:382 */         this.jjmatchedPos = curPos;
/* 421:383 */         kind = 2147483647;
/* 422:    */       }
/* 423:385 */       curPos++;
/* 424:386 */       if ((i = this.jjnewStateCnt) == (startsAt = 4 - (this.jjnewStateCnt = startsAt))) {
/* 425:387 */         return curPos;
/* 426:    */       }
/* 427:    */       try
/* 428:    */       {
/* 429:388 */         this.curChar = this.input_stream.readChar();
/* 430:    */       }
/* 431:    */       catch (IOException e) {}
/* 432:    */     }
/* 433:389 */     return curPos;
/* 434:    */   }
/* 435:    */   
/* 436:    */   private final int jjStopStringLiteralDfa_1(int pos, long active0)
/* 437:    */   {
/* 438:394 */     switch (pos)
/* 439:    */     {
/* 440:    */     }
/* 441:397 */     return -1;
/* 442:    */   }
/* 443:    */   
/* 444:    */   private final int jjStartNfa_1(int pos, long active0)
/* 445:    */   {
/* 446:402 */     return jjMoveNfa_1(jjStopStringLiteralDfa_1(pos, active0), pos + 1);
/* 447:    */   }
/* 448:    */   
/* 449:    */   private final int jjStartNfaWithStates_1(int pos, int kind, int state)
/* 450:    */   {
/* 451:406 */     this.jjmatchedKind = kind;
/* 452:407 */     this.jjmatchedPos = pos;
/* 453:    */     try
/* 454:    */     {
/* 455:408 */       this.curChar = this.input_stream.readChar();
/* 456:    */     }
/* 457:    */     catch (IOException e)
/* 458:    */     {
/* 459:409 */       return pos + 1;
/* 460:    */     }
/* 461:410 */     return jjMoveNfa_1(state, pos + 1);
/* 462:    */   }
/* 463:    */   
/* 464:    */   private final int jjMoveStringLiteralDfa0_1()
/* 465:    */   {
/* 466:414 */     switch (this.curChar)
/* 467:    */     {
/* 468:    */     case '(': 
/* 469:417 */       return jjStopAtPos(0, 40);
/* 470:    */     case ')': 
/* 471:419 */       return jjStopAtPos(0, 38);
/* 472:    */     }
/* 473:421 */     return jjMoveNfa_1(0, 0);
/* 474:    */   }
/* 475:    */   
/* 476:424 */   static final long[] jjbitVec0 = { 0L, 0L, -1L, -1L };
/* 477:    */   
/* 478:    */   private final int jjMoveNfa_1(int startState, int curPos)
/* 479:    */   {
/* 480:430 */     int startsAt = 0;
/* 481:431 */     this.jjnewStateCnt = 3;
/* 482:432 */     int i = 1;
/* 483:433 */     this.jjstateSet[0] = startState;
/* 484:434 */     int kind = 2147483647;
/* 485:    */     for (;;)
/* 486:    */     {
/* 487:437 */       if (++this.jjround == 2147483647) {
/* 488:438 */         ReInitRounds();
/* 489:    */       }
/* 490:439 */       if (this.curChar < '@')
/* 491:    */       {
/* 492:441 */         long l = 1L << this.curChar;
/* 493:    */         do
/* 494:    */         {
/* 495:444 */           switch (this.jjstateSet[(--i)])
/* 496:    */           {
/* 497:    */           case 0: 
/* 498:447 */             if (kind > 41) {
/* 499:448 */               kind = 41;
/* 500:    */             }
/* 501:    */             break;
/* 502:    */           case 1: 
/* 503:451 */             if (kind > 39) {
/* 504:452 */               kind = 39;
/* 505:    */             }
/* 506:    */             break;
/* 507:    */           }
/* 508:456 */         } while (i != startsAt);
/* 509:    */       }
/* 510:458 */       else if (this.curChar < '')
/* 511:    */       {
/* 512:460 */         long l = 1L << (this.curChar & 0x3F);
/* 513:    */         do
/* 514:    */         {
/* 515:463 */           switch (this.jjstateSet[(--i)])
/* 516:    */           {
/* 517:    */           case 0: 
/* 518:466 */             if (kind > 41) {
/* 519:467 */               kind = 41;
/* 520:    */             }
/* 521:468 */             if (this.curChar == '\\') {
/* 522:469 */               this.jjstateSet[(this.jjnewStateCnt++)] = 1;
/* 523:    */             }
/* 524:    */             break;
/* 525:    */           case 1: 
/* 526:472 */             if (kind > 39) {
/* 527:473 */               kind = 39;
/* 528:    */             }
/* 529:    */             break;
/* 530:    */           case 2: 
/* 531:476 */             if (kind > 41) {
/* 532:477 */               kind = 41;
/* 533:    */             }
/* 534:    */             break;
/* 535:    */           }
/* 536:481 */         } while (i != startsAt);
/* 537:    */       }
/* 538:    */       else
/* 539:    */       {
/* 540:485 */         int i2 = (this.curChar & 0xFF) >> '\006';
/* 541:486 */         long l2 = 1L << (this.curChar & 0x3F);
/* 542:    */         do
/* 543:    */         {
/* 544:489 */           switch (this.jjstateSet[(--i)])
/* 545:    */           {
/* 546:    */           case 0: 
/* 547:492 */             if (((jjbitVec0[i2] & l2) != 0L) && (kind > 41)) {
/* 548:493 */               kind = 41;
/* 549:    */             }
/* 550:    */             break;
/* 551:    */           case 1: 
/* 552:496 */             if (((jjbitVec0[i2] & l2) != 0L) && (kind > 39)) {
/* 553:497 */               kind = 39;
/* 554:    */             }
/* 555:    */             break;
/* 556:    */           }
/* 557:501 */         } while (i != startsAt);
/* 558:    */       }
/* 559:503 */       if (kind != 2147483647)
/* 560:    */       {
/* 561:505 */         this.jjmatchedKind = kind;
/* 562:506 */         this.jjmatchedPos = curPos;
/* 563:507 */         kind = 2147483647;
/* 564:    */       }
/* 565:509 */       curPos++;
/* 566:510 */       if ((i = this.jjnewStateCnt) == (startsAt = 3 - (this.jjnewStateCnt = startsAt))) {
/* 567:511 */         return curPos;
/* 568:    */       }
/* 569:    */       try
/* 570:    */       {
/* 571:512 */         this.curChar = this.input_stream.readChar();
/* 572:    */       }
/* 573:    */       catch (IOException e) {}
/* 574:    */     }
/* 575:513 */     return curPos;
/* 576:    */   }
/* 577:    */   
/* 578:    */   private final int jjStopStringLiteralDfa_2(int pos, long active0)
/* 579:    */   {
/* 580:518 */     switch (pos)
/* 581:    */     {
/* 582:    */     }
/* 583:521 */     return -1;
/* 584:    */   }
/* 585:    */   
/* 586:    */   private final int jjStartNfa_2(int pos, long active0)
/* 587:    */   {
/* 588:526 */     return jjMoveNfa_2(jjStopStringLiteralDfa_2(pos, active0), pos + 1);
/* 589:    */   }
/* 590:    */   
/* 591:    */   private final int jjStartNfaWithStates_2(int pos, int kind, int state)
/* 592:    */   {
/* 593:530 */     this.jjmatchedKind = kind;
/* 594:531 */     this.jjmatchedPos = pos;
/* 595:    */     try
/* 596:    */     {
/* 597:532 */       this.curChar = this.input_stream.readChar();
/* 598:    */     }
/* 599:    */     catch (IOException e)
/* 600:    */     {
/* 601:533 */       return pos + 1;
/* 602:    */     }
/* 603:534 */     return jjMoveNfa_2(state, pos + 1);
/* 604:    */   }
/* 605:    */   
/* 606:    */   private final int jjMoveStringLiteralDfa0_2()
/* 607:    */   {
/* 608:538 */     switch (this.curChar)
/* 609:    */     {
/* 610:    */     case '(': 
/* 611:541 */       return jjStopAtPos(0, 43);
/* 612:    */     case ')': 
/* 613:543 */       return jjStopAtPos(0, 44);
/* 614:    */     }
/* 615:545 */     return jjMoveNfa_2(0, 0);
/* 616:    */   }
/* 617:    */   
/* 618:    */   private final int jjMoveNfa_2(int startState, int curPos)
/* 619:    */   {
/* 620:551 */     int startsAt = 0;
/* 621:552 */     this.jjnewStateCnt = 3;
/* 622:553 */     int i = 1;
/* 623:554 */     this.jjstateSet[0] = startState;
/* 624:555 */     int kind = 2147483647;
/* 625:    */     for (;;)
/* 626:    */     {
/* 627:558 */       if (++this.jjround == 2147483647) {
/* 628:559 */         ReInitRounds();
/* 629:    */       }
/* 630:560 */       if (this.curChar < '@')
/* 631:    */       {
/* 632:562 */         long l = 1L << this.curChar;
/* 633:    */         do
/* 634:    */         {
/* 635:565 */           switch (this.jjstateSet[(--i)])
/* 636:    */           {
/* 637:    */           case 0: 
/* 638:568 */             if (kind > 45) {
/* 639:569 */               kind = 45;
/* 640:    */             }
/* 641:    */             break;
/* 642:    */           case 1: 
/* 643:572 */             if (kind > 42) {
/* 644:573 */               kind = 42;
/* 645:    */             }
/* 646:    */             break;
/* 647:    */           }
/* 648:577 */         } while (i != startsAt);
/* 649:    */       }
/* 650:579 */       else if (this.curChar < '')
/* 651:    */       {
/* 652:581 */         long l = 1L << (this.curChar & 0x3F);
/* 653:    */         do
/* 654:    */         {
/* 655:584 */           switch (this.jjstateSet[(--i)])
/* 656:    */           {
/* 657:    */           case 0: 
/* 658:587 */             if (kind > 45) {
/* 659:588 */               kind = 45;
/* 660:    */             }
/* 661:589 */             if (this.curChar == '\\') {
/* 662:590 */               this.jjstateSet[(this.jjnewStateCnt++)] = 1;
/* 663:    */             }
/* 664:    */             break;
/* 665:    */           case 1: 
/* 666:593 */             if (kind > 42) {
/* 667:594 */               kind = 42;
/* 668:    */             }
/* 669:    */             break;
/* 670:    */           case 2: 
/* 671:597 */             if (kind > 45) {
/* 672:598 */               kind = 45;
/* 673:    */             }
/* 674:    */             break;
/* 675:    */           }
/* 676:602 */         } while (i != startsAt);
/* 677:    */       }
/* 678:    */       else
/* 679:    */       {
/* 680:606 */         int i2 = (this.curChar & 0xFF) >> '\006';
/* 681:607 */         long l2 = 1L << (this.curChar & 0x3F);
/* 682:    */         do
/* 683:    */         {
/* 684:610 */           switch (this.jjstateSet[(--i)])
/* 685:    */           {
/* 686:    */           case 0: 
/* 687:613 */             if (((jjbitVec0[i2] & l2) != 0L) && (kind > 45)) {
/* 688:614 */               kind = 45;
/* 689:    */             }
/* 690:    */             break;
/* 691:    */           case 1: 
/* 692:617 */             if (((jjbitVec0[i2] & l2) != 0L) && (kind > 42)) {
/* 693:618 */               kind = 42;
/* 694:    */             }
/* 695:    */             break;
/* 696:    */           }
/* 697:622 */         } while (i != startsAt);
/* 698:    */       }
/* 699:624 */       if (kind != 2147483647)
/* 700:    */       {
/* 701:626 */         this.jjmatchedKind = kind;
/* 702:627 */         this.jjmatchedPos = curPos;
/* 703:628 */         kind = 2147483647;
/* 704:    */       }
/* 705:630 */       curPos++;
/* 706:631 */       if ((i = this.jjnewStateCnt) == (startsAt = 3 - (this.jjnewStateCnt = startsAt))) {
/* 707:632 */         return curPos;
/* 708:    */       }
/* 709:    */       try
/* 710:    */       {
/* 711:633 */         this.curChar = this.input_stream.readChar();
/* 712:    */       }
/* 713:    */       catch (IOException e) {}
/* 714:    */     }
/* 715:634 */     return curPos;
/* 716:    */   }
/* 717:    */   
/* 718:637 */   static final int[] jjnextStates = new int[0];
/* 719:639 */   public static final String[] jjstrLiteralImages = { "", "\r", "\n", ",", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec", ":", null, "UT", "GMT", "EST", "EDT", "CST", "CDT", "MST", "MDT", "PST", "PDT", null, null, null, null, null, null, null, null, null, null, null, null, null, null };
/* 720:648 */   public static final String[] lexStateNames = { "DEFAULT", "INCOMMENT", "NESTED_COMMENT" };
/* 721:653 */   public static final int[] jjnewLexState = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 1, 0, -1, 2, -1, -1, -1, -1, -1, -1, -1, -1 };
/* 722:657 */   static final long[] jjtoToken = { 70437463654399L };
/* 723:660 */   static final long[] jjtoSkip = { 343597383680L };
/* 724:663 */   static final long[] jjtoSpecial = { 68719476736L };
/* 725:666 */   static final long[] jjtoMore = { 69956427317248L };
/* 726:    */   protected SimpleCharStream input_stream;
/* 727:670 */   private final int[] jjrounds = new int[4];
/* 728:671 */   private final int[] jjstateSet = new int[8];
/* 729:    */   StringBuffer image;
/* 730:    */   int jjimageLen;
/* 731:    */   int lengthOfMatch;
/* 732:    */   protected char curChar;
/* 733:    */   
/* 734:    */   public DateTimeParserTokenManager(SimpleCharStream stream)
/* 735:    */   {
/* 736:679 */     this.input_stream = stream;
/* 737:    */   }
/* 738:    */   
/* 739:    */   public DateTimeParserTokenManager(SimpleCharStream stream, int lexState)
/* 740:    */   {
/* 741:682 */     this(stream);
/* 742:683 */     SwitchTo(lexState);
/* 743:    */   }
/* 744:    */   
/* 745:    */   public void ReInit(SimpleCharStream stream)
/* 746:    */   {
/* 747:687 */     this.jjmatchedPos = (this.jjnewStateCnt = 0);
/* 748:688 */     this.curLexState = this.defaultLexState;
/* 749:689 */     this.input_stream = stream;
/* 750:690 */     ReInitRounds();
/* 751:    */   }
/* 752:    */   
/* 753:    */   private final void ReInitRounds()
/* 754:    */   {
/* 755:695 */     this.jjround = -2147483647;
/* 756:696 */     for (int i = 4; i-- > 0;) {
/* 757:697 */       this.jjrounds[i] = -2147483648;
/* 758:    */     }
/* 759:    */   }
/* 760:    */   
/* 761:    */   public void ReInit(SimpleCharStream stream, int lexState)
/* 762:    */   {
/* 763:701 */     ReInit(stream);
/* 764:702 */     SwitchTo(lexState);
/* 765:    */   }
/* 766:    */   
/* 767:    */   public void SwitchTo(int lexState)
/* 768:    */   {
/* 769:706 */     if ((lexState >= 3) || (lexState < 0)) {
/* 770:707 */       throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", 2);
/* 771:    */     }
/* 772:709 */     this.curLexState = lexState;
/* 773:    */   }
/* 774:    */   
/* 775:    */   protected Token jjFillToken()
/* 776:    */   {
/* 777:714 */     Token t = Token.newToken(this.jjmatchedKind);
/* 778:715 */     t.kind = this.jjmatchedKind;
/* 779:716 */     String im = jjstrLiteralImages[this.jjmatchedKind];
/* 780:717 */     t.image = (im == null ? this.input_stream.GetImage() : im);
/* 781:718 */     t.beginLine = this.input_stream.getBeginLine();
/* 782:719 */     t.beginColumn = this.input_stream.getBeginColumn();
/* 783:720 */     t.endLine = this.input_stream.getEndLine();
/* 784:721 */     t.endColumn = this.input_stream.getEndColumn();
/* 785:722 */     return t;
/* 786:    */   }
/* 787:    */   
/* 788:725 */   int curLexState = 0;
/* 789:726 */   int defaultLexState = 0;
/* 790:    */   int jjnewStateCnt;
/* 791:    */   int jjround;
/* 792:    */   int jjmatchedPos;
/* 793:    */   int jjmatchedKind;
/* 794:    */   
/* 795:    */   public Token getNextToken()
/* 796:    */   {
/* 797:735 */     Token specialToken = null;
/* 798:    */     
/* 799:737 */     int curPos = 0;
/* 800:    */     try
/* 801:    */     {
/* 802:744 */       this.curChar = this.input_stream.BeginToken();
/* 803:    */     }
/* 804:    */     catch (IOException e)
/* 805:    */     {
/* 806:748 */       this.jjmatchedKind = 0;
/* 807:749 */       Token matchedToken = jjFillToken();
/* 808:750 */       matchedToken.specialToken = specialToken;
/* 809:751 */       return matchedToken;
/* 810:    */     }
/* 811:753 */     this.image = null;
/* 812:754 */     this.jjimageLen = 0;
/* 813:    */     for (;;)
/* 814:    */     {
/* 815:758 */       switch (this.curLexState)
/* 816:    */       {
/* 817:    */       case 0: 
/* 818:761 */         this.jjmatchedKind = 2147483647;
/* 819:762 */         this.jjmatchedPos = 0;
/* 820:763 */         curPos = jjMoveStringLiteralDfa0_0();
/* 821:764 */         break;
/* 822:    */       case 1: 
/* 823:766 */         this.jjmatchedKind = 2147483647;
/* 824:767 */         this.jjmatchedPos = 0;
/* 825:768 */         curPos = jjMoveStringLiteralDfa0_1();
/* 826:769 */         break;
/* 827:    */       case 2: 
/* 828:771 */         this.jjmatchedKind = 2147483647;
/* 829:772 */         this.jjmatchedPos = 0;
/* 830:773 */         curPos = jjMoveStringLiteralDfa0_2();
/* 831:    */       }
/* 832:776 */       if (this.jjmatchedKind != 2147483647)
/* 833:    */       {
/* 834:778 */         if (this.jjmatchedPos + 1 < curPos) {
/* 835:779 */           this.input_stream.backup(curPos - this.jjmatchedPos - 1);
/* 836:    */         }
/* 837:780 */         if ((jjtoToken[(this.jjmatchedKind >> 6)] & 1L << (this.jjmatchedKind & 0x3F)) != 0L)
/* 838:    */         {
/* 839:782 */           Token matchedToken = jjFillToken();
/* 840:783 */           matchedToken.specialToken = specialToken;
/* 841:784 */           if (jjnewLexState[this.jjmatchedKind] != -1) {
/* 842:785 */             this.curLexState = jjnewLexState[this.jjmatchedKind];
/* 843:    */           }
/* 844:786 */           return matchedToken;
/* 845:    */         }
/* 846:788 */         if ((jjtoSkip[(this.jjmatchedKind >> 6)] & 1L << (this.jjmatchedKind & 0x3F)) != 0L)
/* 847:    */         {
/* 848:790 */           if ((jjtoSpecial[(this.jjmatchedKind >> 6)] & 1L << (this.jjmatchedKind & 0x3F)) != 0L)
/* 849:    */           {
/* 850:792 */             Token matchedToken = jjFillToken();
/* 851:793 */             if (specialToken == null)
/* 852:    */             {
/* 853:794 */               specialToken = matchedToken;
/* 854:    */             }
/* 855:    */             else
/* 856:    */             {
/* 857:797 */               matchedToken.specialToken = specialToken;
/* 858:798 */               specialToken = specialToken.next = matchedToken;
/* 859:    */             }
/* 860:    */           }
/* 861:801 */           if (jjnewLexState[this.jjmatchedKind] == -1) {
/* 862:    */             break;
/* 863:    */           }
/* 864:802 */           this.curLexState = jjnewLexState[this.jjmatchedKind]; break;
/* 865:    */         }
/* 866:805 */         MoreLexicalActions();
/* 867:806 */         if (jjnewLexState[this.jjmatchedKind] != -1) {
/* 868:807 */           this.curLexState = jjnewLexState[this.jjmatchedKind];
/* 869:    */         }
/* 870:808 */         curPos = 0;
/* 871:809 */         this.jjmatchedKind = 2147483647;
/* 872:    */         try
/* 873:    */         {
/* 874:811 */           this.curChar = this.input_stream.readChar();
/* 875:    */         }
/* 876:    */         catch (IOException e1) {}
/* 877:    */       }
/* 878:    */     }
/* 879:816 */     int error_line = this.input_stream.getEndLine();
/* 880:817 */     int error_column = this.input_stream.getEndColumn();
/* 881:818 */     String error_after = null;
/* 882:819 */     boolean EOFSeen = false;
/* 883:    */     try
/* 884:    */     {
/* 885:820 */       this.input_stream.readChar();this.input_stream.backup(1);
/* 886:    */     }
/* 887:    */     catch (IOException e1)
/* 888:    */     {
/* 889:822 */       EOFSeen = true;
/* 890:823 */       error_after = curPos <= 1 ? "" : this.input_stream.GetImage();
/* 891:824 */       if ((this.curChar == '\n') || (this.curChar == '\r'))
/* 892:    */       {
/* 893:825 */         error_line++;
/* 894:826 */         error_column = 0;
/* 895:    */       }
/* 896:    */       else
/* 897:    */       {
/* 898:829 */         error_column++;
/* 899:    */       }
/* 900:    */     }
/* 901:831 */     if (!EOFSeen)
/* 902:    */     {
/* 903:832 */       this.input_stream.backup(1);
/* 904:833 */       error_after = curPos <= 1 ? "" : this.input_stream.GetImage();
/* 905:    */     }
/* 906:835 */     throw new TokenMgrError(EOFSeen, this.curLexState, error_line, error_column, error_after, this.curChar, 0);
/* 907:    */   }
/* 908:    */   
/* 909:    */   void MoreLexicalActions()
/* 910:    */   {
/* 911:842 */     this.jjimageLen += (this.lengthOfMatch = this.jjmatchedPos + 1);
/* 912:843 */     switch (this.jjmatchedKind)
/* 913:    */     {
/* 914:    */     case 39: 
/* 915:846 */       if (this.image == null) {
/* 916:847 */         this.image = new StringBuffer();
/* 917:    */       }
/* 918:848 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 919:849 */       this.jjimageLen = 0;
/* 920:850 */       this.image.deleteCharAt(this.image.length() - 2);
/* 921:851 */       break;
/* 922:    */     case 40: 
/* 923:853 */       if (this.image == null) {
/* 924:854 */         this.image = new StringBuffer();
/* 925:    */       }
/* 926:855 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 927:856 */       this.jjimageLen = 0;
/* 928:857 */       commentNest = 1;
/* 929:858 */       break;
/* 930:    */     case 42: 
/* 931:860 */       if (this.image == null) {
/* 932:861 */         this.image = new StringBuffer();
/* 933:    */       }
/* 934:862 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 935:863 */       this.jjimageLen = 0;
/* 936:864 */       this.image.deleteCharAt(this.image.length() - 2);
/* 937:865 */       break;
/* 938:    */     case 43: 
/* 939:867 */       if (this.image == null) {
/* 940:868 */         this.image = new StringBuffer();
/* 941:    */       }
/* 942:869 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 943:870 */       this.jjimageLen = 0;
/* 944:871 */       commentNest += 1;
/* 945:872 */       break;
/* 946:    */     case 44: 
/* 947:874 */       if (this.image == null) {
/* 948:875 */         this.image = new StringBuffer();
/* 949:    */       }
/* 950:876 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 951:877 */       this.jjimageLen = 0;
/* 952:878 */       commentNest -= 1;
/* 953:878 */       if (commentNest == 0) {
/* 954:878 */         SwitchTo(1);
/* 955:    */       }
/* 956:    */       break;
/* 957:    */     }
/* 958:    */   }
/* 959:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.datetime.parser.DateTimeParserTokenManager
 * JD-Core Version:    0.7.0.1
 */