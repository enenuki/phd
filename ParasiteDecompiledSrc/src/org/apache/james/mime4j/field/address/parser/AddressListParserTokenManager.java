/*    1:     */ package org.apache.james.mime4j.field.address.parser;
/*    2:     */ 
/*    3:     */ import java.io.IOException;
/*    4:     */ import java.io.PrintStream;
/*    5:     */ 
/*    6:     */ public class AddressListParserTokenManager
/*    7:     */   implements AddressListParserConstants
/*    8:     */ {
/*    9:     */   static int commentNest;
/*   10:  31 */   public PrintStream debugStream = System.out;
/*   11:     */   
/*   12:     */   public void setDebugStream(PrintStream ds)
/*   13:     */   {
/*   14:  32 */     this.debugStream = ds;
/*   15:     */   }
/*   16:     */   
/*   17:     */   private final int jjStopStringLiteralDfa_0(int pos, long active0)
/*   18:     */   {
/*   19:  35 */     switch (pos)
/*   20:     */     {
/*   21:     */     }
/*   22:  38 */     return -1;
/*   23:     */   }
/*   24:     */   
/*   25:     */   private final int jjStartNfa_0(int pos, long active0)
/*   26:     */   {
/*   27:  43 */     return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
/*   28:     */   }
/*   29:     */   
/*   30:     */   private final int jjStopAtPos(int pos, int kind)
/*   31:     */   {
/*   32:  47 */     this.jjmatchedKind = kind;
/*   33:  48 */     this.jjmatchedPos = pos;
/*   34:  49 */     return pos + 1;
/*   35:     */   }
/*   36:     */   
/*   37:     */   private final int jjStartNfaWithStates_0(int pos, int kind, int state)
/*   38:     */   {
/*   39:  53 */     this.jjmatchedKind = kind;
/*   40:  54 */     this.jjmatchedPos = pos;
/*   41:     */     try
/*   42:     */     {
/*   43:  55 */       this.curChar = this.input_stream.readChar();
/*   44:     */     }
/*   45:     */     catch (IOException e)
/*   46:     */     {
/*   47:  56 */       return pos + 1;
/*   48:     */     }
/*   49:  57 */     return jjMoveNfa_0(state, pos + 1);
/*   50:     */   }
/*   51:     */   
/*   52:     */   private final int jjMoveStringLiteralDfa0_0()
/*   53:     */   {
/*   54:  61 */     switch (this.curChar)
/*   55:     */     {
/*   56:     */     case '\n': 
/*   57:  64 */       return jjStopAtPos(0, 2);
/*   58:     */     case '\r': 
/*   59:  66 */       return jjStopAtPos(0, 1);
/*   60:     */     case '"': 
/*   61:  68 */       return jjStopAtPos(0, 28);
/*   62:     */     case '(': 
/*   63:  70 */       return jjStopAtPos(0, 19);
/*   64:     */     case ',': 
/*   65:  72 */       return jjStopAtPos(0, 3);
/*   66:     */     case '.': 
/*   67:  74 */       return jjStopAtPos(0, 9);
/*   68:     */     case ':': 
/*   69:  76 */       return jjStopAtPos(0, 4);
/*   70:     */     case ';': 
/*   71:  78 */       return jjStopAtPos(0, 5);
/*   72:     */     case '<': 
/*   73:  80 */       return jjStopAtPos(0, 6);
/*   74:     */     case '>': 
/*   75:  82 */       return jjStopAtPos(0, 7);
/*   76:     */     case '@': 
/*   77:  84 */       return jjStopAtPos(0, 8);
/*   78:     */     case '[': 
/*   79:  86 */       return jjStopAtPos(0, 15);
/*   80:     */     }
/*   81:  88 */     return jjMoveNfa_0(1, 0);
/*   82:     */   }
/*   83:     */   
/*   84:     */   private final void jjCheckNAdd(int state)
/*   85:     */   {
/*   86:  93 */     if (this.jjrounds[state] != this.jjround)
/*   87:     */     {
/*   88:  95 */       this.jjstateSet[(this.jjnewStateCnt++)] = state;
/*   89:  96 */       this.jjrounds[state] = this.jjround;
/*   90:     */     }
/*   91:     */   }
/*   92:     */   
/*   93:     */   private final void jjAddStates(int start, int end)
/*   94:     */   {
/*   95:     */     do
/*   96:     */     {
/*   97: 102 */       this.jjstateSet[(this.jjnewStateCnt++)] = jjnextStates[start];
/*   98: 103 */     } while (start++ != end);
/*   99:     */   }
/*  100:     */   
/*  101:     */   private final void jjCheckNAddTwoStates(int state1, int state2)
/*  102:     */   {
/*  103: 107 */     jjCheckNAdd(state1);
/*  104: 108 */     jjCheckNAdd(state2);
/*  105:     */   }
/*  106:     */   
/*  107:     */   private final void jjCheckNAddStates(int start, int end)
/*  108:     */   {
/*  109:     */     do
/*  110:     */     {
/*  111: 113 */       jjCheckNAdd(jjnextStates[start]);
/*  112: 114 */     } while (start++ != end);
/*  113:     */   }
/*  114:     */   
/*  115:     */   private final void jjCheckNAddStates(int start)
/*  116:     */   {
/*  117: 118 */     jjCheckNAdd(jjnextStates[start]);
/*  118: 119 */     jjCheckNAdd(jjnextStates[(start + 1)]);
/*  119:     */   }
/*  120:     */   
/*  121:     */   private final int jjMoveNfa_0(int startState, int curPos)
/*  122:     */   {
/*  123: 124 */     int startsAt = 0;
/*  124: 125 */     this.jjnewStateCnt = 3;
/*  125: 126 */     int i = 1;
/*  126: 127 */     this.jjstateSet[0] = startState;
/*  127: 128 */     int kind = 2147483647;
/*  128:     */     for (;;)
/*  129:     */     {
/*  130: 131 */       if (++this.jjround == 2147483647) {
/*  131: 132 */         ReInitRounds();
/*  132:     */       }
/*  133: 133 */       if (this.curChar < '@')
/*  134:     */       {
/*  135: 135 */         long l = 1L << this.curChar;
/*  136:     */         do
/*  137:     */         {
/*  138: 138 */           switch (this.jjstateSet[(--i)])
/*  139:     */           {
/*  140:     */           case 1: 
/*  141: 141 */             if ((0x0 & l) != 0L)
/*  142:     */             {
/*  143: 143 */               if (kind > 14) {
/*  144: 144 */                 kind = 14;
/*  145:     */               }
/*  146: 145 */               jjCheckNAdd(2);
/*  147:     */             }
/*  148: 147 */             else if ((0x200 & l) != 0L)
/*  149:     */             {
/*  150: 149 */               if (kind > 10) {
/*  151: 150 */                 kind = 10;
/*  152:     */               }
/*  153: 151 */               jjCheckNAdd(0);
/*  154:     */             }
/*  155:     */             break;
/*  156:     */           case 0: 
/*  157: 155 */             if ((0x200 & l) != 0L)
/*  158:     */             {
/*  159: 157 */               kind = 10;
/*  160: 158 */               jjCheckNAdd(0);
/*  161:     */             }
/*  162: 159 */             break;
/*  163:     */           case 2: 
/*  164: 161 */             if ((0x0 & l) != 0L)
/*  165:     */             {
/*  166: 163 */               if (kind > 14) {
/*  167: 164 */                 kind = 14;
/*  168:     */               }
/*  169: 165 */               jjCheckNAdd(2);
/*  170:     */             }
/*  171:     */             break;
/*  172:     */           }
/*  173: 169 */         } while (i != startsAt);
/*  174:     */       }
/*  175: 171 */       else if (this.curChar < '')
/*  176:     */       {
/*  177: 173 */         long l = 1L << (this.curChar & 0x3F);
/*  178:     */         do
/*  179:     */         {
/*  180: 176 */           switch (this.jjstateSet[(--i)])
/*  181:     */           {
/*  182:     */           case 1: 
/*  183:     */           case 2: 
/*  184: 180 */             if ((0xC7FFFFFE & l) != 0L)
/*  185:     */             {
/*  186: 182 */               if (kind > 14) {
/*  187: 183 */                 kind = 14;
/*  188:     */               }
/*  189: 184 */               jjCheckNAdd(2);
/*  190:     */             }
/*  191:     */             break;
/*  192:     */           }
/*  193: 188 */         } while (i != startsAt);
/*  194:     */       }
/*  195:     */       else
/*  196:     */       {
/*  197: 192 */         int i2 = (this.curChar & 0xFF) >> '\006';
/*  198: 193 */         long l2 = 1L << (this.curChar & 0x3F);
/*  199:     */         do
/*  200:     */         {
/*  201: 196 */           switch (this.jjstateSet[(--i)])
/*  202:     */           {
/*  203:     */           }
/*  204: 200 */         } while (i != startsAt);
/*  205:     */       }
/*  206: 202 */       if (kind != 2147483647)
/*  207:     */       {
/*  208: 204 */         this.jjmatchedKind = kind;
/*  209: 205 */         this.jjmatchedPos = curPos;
/*  210: 206 */         kind = 2147483647;
/*  211:     */       }
/*  212: 208 */       curPos++;
/*  213: 209 */       if ((i = this.jjnewStateCnt) == (startsAt = 3 - (this.jjnewStateCnt = startsAt))) {
/*  214: 210 */         return curPos;
/*  215:     */       }
/*  216:     */       try
/*  217:     */       {
/*  218: 211 */         this.curChar = this.input_stream.readChar();
/*  219:     */       }
/*  220:     */       catch (IOException e) {}
/*  221:     */     }
/*  222: 212 */     return curPos;
/*  223:     */   }
/*  224:     */   
/*  225:     */   private final int jjStopStringLiteralDfa_2(int pos, long active0)
/*  226:     */   {
/*  227: 217 */     switch (pos)
/*  228:     */     {
/*  229:     */     }
/*  230: 220 */     return -1;
/*  231:     */   }
/*  232:     */   
/*  233:     */   private final int jjStartNfa_2(int pos, long active0)
/*  234:     */   {
/*  235: 225 */     return jjMoveNfa_2(jjStopStringLiteralDfa_2(pos, active0), pos + 1);
/*  236:     */   }
/*  237:     */   
/*  238:     */   private final int jjStartNfaWithStates_2(int pos, int kind, int state)
/*  239:     */   {
/*  240: 229 */     this.jjmatchedKind = kind;
/*  241: 230 */     this.jjmatchedPos = pos;
/*  242:     */     try
/*  243:     */     {
/*  244: 231 */       this.curChar = this.input_stream.readChar();
/*  245:     */     }
/*  246:     */     catch (IOException e)
/*  247:     */     {
/*  248: 232 */       return pos + 1;
/*  249:     */     }
/*  250: 233 */     return jjMoveNfa_2(state, pos + 1);
/*  251:     */   }
/*  252:     */   
/*  253:     */   private final int jjMoveStringLiteralDfa0_2()
/*  254:     */   {
/*  255: 237 */     switch (this.curChar)
/*  256:     */     {
/*  257:     */     case '(': 
/*  258: 240 */       return jjStopAtPos(0, 22);
/*  259:     */     case ')': 
/*  260: 242 */       return jjStopAtPos(0, 20);
/*  261:     */     }
/*  262: 244 */     return jjMoveNfa_2(0, 0);
/*  263:     */   }
/*  264:     */   
/*  265: 247 */   static final long[] jjbitVec0 = { 0L, 0L, -1L, -1L };
/*  266:     */   
/*  267:     */   private final int jjMoveNfa_2(int startState, int curPos)
/*  268:     */   {
/*  269: 253 */     int startsAt = 0;
/*  270: 254 */     this.jjnewStateCnt = 3;
/*  271: 255 */     int i = 1;
/*  272: 256 */     this.jjstateSet[0] = startState;
/*  273: 257 */     int kind = 2147483647;
/*  274:     */     for (;;)
/*  275:     */     {
/*  276: 260 */       if (++this.jjround == 2147483647) {
/*  277: 261 */         ReInitRounds();
/*  278:     */       }
/*  279: 262 */       if (this.curChar < '@')
/*  280:     */       {
/*  281: 264 */         long l = 1L << this.curChar;
/*  282:     */         do
/*  283:     */         {
/*  284: 267 */           switch (this.jjstateSet[(--i)])
/*  285:     */           {
/*  286:     */           case 0: 
/*  287: 270 */             if (kind > 23) {
/*  288: 271 */               kind = 23;
/*  289:     */             }
/*  290:     */             break;
/*  291:     */           case 1: 
/*  292: 274 */             if (kind > 21) {
/*  293: 275 */               kind = 21;
/*  294:     */             }
/*  295:     */             break;
/*  296:     */           }
/*  297: 279 */         } while (i != startsAt);
/*  298:     */       }
/*  299: 281 */       else if (this.curChar < '')
/*  300:     */       {
/*  301: 283 */         long l = 1L << (this.curChar & 0x3F);
/*  302:     */         do
/*  303:     */         {
/*  304: 286 */           switch (this.jjstateSet[(--i)])
/*  305:     */           {
/*  306:     */           case 0: 
/*  307: 289 */             if (kind > 23) {
/*  308: 290 */               kind = 23;
/*  309:     */             }
/*  310: 291 */             if (this.curChar == '\\') {
/*  311: 292 */               this.jjstateSet[(this.jjnewStateCnt++)] = 1;
/*  312:     */             }
/*  313:     */             break;
/*  314:     */           case 1: 
/*  315: 295 */             if (kind > 21) {
/*  316: 296 */               kind = 21;
/*  317:     */             }
/*  318:     */             break;
/*  319:     */           case 2: 
/*  320: 299 */             if (kind > 23) {
/*  321: 300 */               kind = 23;
/*  322:     */             }
/*  323:     */             break;
/*  324:     */           }
/*  325: 304 */         } while (i != startsAt);
/*  326:     */       }
/*  327:     */       else
/*  328:     */       {
/*  329: 308 */         int i2 = (this.curChar & 0xFF) >> '\006';
/*  330: 309 */         long l2 = 1L << (this.curChar & 0x3F);
/*  331:     */         do
/*  332:     */         {
/*  333: 312 */           switch (this.jjstateSet[(--i)])
/*  334:     */           {
/*  335:     */           case 0: 
/*  336: 315 */             if (((jjbitVec0[i2] & l2) != 0L) && (kind > 23)) {
/*  337: 316 */               kind = 23;
/*  338:     */             }
/*  339:     */             break;
/*  340:     */           case 1: 
/*  341: 319 */             if (((jjbitVec0[i2] & l2) != 0L) && (kind > 21)) {
/*  342: 320 */               kind = 21;
/*  343:     */             }
/*  344:     */             break;
/*  345:     */           }
/*  346: 324 */         } while (i != startsAt);
/*  347:     */       }
/*  348: 326 */       if (kind != 2147483647)
/*  349:     */       {
/*  350: 328 */         this.jjmatchedKind = kind;
/*  351: 329 */         this.jjmatchedPos = curPos;
/*  352: 330 */         kind = 2147483647;
/*  353:     */       }
/*  354: 332 */       curPos++;
/*  355: 333 */       if ((i = this.jjnewStateCnt) == (startsAt = 3 - (this.jjnewStateCnt = startsAt))) {
/*  356: 334 */         return curPos;
/*  357:     */       }
/*  358:     */       try
/*  359:     */       {
/*  360: 335 */         this.curChar = this.input_stream.readChar();
/*  361:     */       }
/*  362:     */       catch (IOException e) {}
/*  363:     */     }
/*  364: 336 */     return curPos;
/*  365:     */   }
/*  366:     */   
/*  367:     */   private final int jjStopStringLiteralDfa_4(int pos, long active0)
/*  368:     */   {
/*  369: 341 */     switch (pos)
/*  370:     */     {
/*  371:     */     }
/*  372: 344 */     return -1;
/*  373:     */   }
/*  374:     */   
/*  375:     */   private final int jjStartNfa_4(int pos, long active0)
/*  376:     */   {
/*  377: 349 */     return jjMoveNfa_4(jjStopStringLiteralDfa_4(pos, active0), pos + 1);
/*  378:     */   }
/*  379:     */   
/*  380:     */   private final int jjStartNfaWithStates_4(int pos, int kind, int state)
/*  381:     */   {
/*  382: 353 */     this.jjmatchedKind = kind;
/*  383: 354 */     this.jjmatchedPos = pos;
/*  384:     */     try
/*  385:     */     {
/*  386: 355 */       this.curChar = this.input_stream.readChar();
/*  387:     */     }
/*  388:     */     catch (IOException e)
/*  389:     */     {
/*  390: 356 */       return pos + 1;
/*  391:     */     }
/*  392: 357 */     return jjMoveNfa_4(state, pos + 1);
/*  393:     */   }
/*  394:     */   
/*  395:     */   private final int jjMoveStringLiteralDfa0_4()
/*  396:     */   {
/*  397: 361 */     switch (this.curChar)
/*  398:     */     {
/*  399:     */     case '"': 
/*  400: 364 */       return jjStopAtPos(0, 31);
/*  401:     */     }
/*  402: 366 */     return jjMoveNfa_4(0, 0);
/*  403:     */   }
/*  404:     */   
/*  405:     */   private final int jjMoveNfa_4(int startState, int curPos)
/*  406:     */   {
/*  407: 372 */     int startsAt = 0;
/*  408: 373 */     this.jjnewStateCnt = 3;
/*  409: 374 */     int i = 1;
/*  410: 375 */     this.jjstateSet[0] = startState;
/*  411: 376 */     int kind = 2147483647;
/*  412:     */     for (;;)
/*  413:     */     {
/*  414: 379 */       if (++this.jjround == 2147483647) {
/*  415: 380 */         ReInitRounds();
/*  416:     */       }
/*  417: 381 */       if (this.curChar < '@')
/*  418:     */       {
/*  419: 383 */         long l = 1L << this.curChar;
/*  420:     */         do
/*  421:     */         {
/*  422: 386 */           switch (this.jjstateSet[(--i)])
/*  423:     */           {
/*  424:     */           case 0: 
/*  425:     */           case 2: 
/*  426: 390 */             if ((0xFFFFFFFF & l) != 0L)
/*  427:     */             {
/*  428: 392 */               if (kind > 30) {
/*  429: 393 */                 kind = 30;
/*  430:     */               }
/*  431: 394 */               jjCheckNAdd(2);
/*  432:     */             }
/*  433: 395 */             break;
/*  434:     */           case 1: 
/*  435: 397 */             if (kind > 29) {
/*  436: 398 */               kind = 29;
/*  437:     */             }
/*  438:     */             break;
/*  439:     */           }
/*  440: 402 */         } while (i != startsAt);
/*  441:     */       }
/*  442: 404 */       else if (this.curChar < '')
/*  443:     */       {
/*  444: 406 */         long l = 1L << (this.curChar & 0x3F);
/*  445:     */         do
/*  446:     */         {
/*  447: 409 */           switch (this.jjstateSet[(--i)])
/*  448:     */           {
/*  449:     */           case 0: 
/*  450: 412 */             if ((0xEFFFFFFF & l) != 0L)
/*  451:     */             {
/*  452: 414 */               if (kind > 30) {
/*  453: 415 */                 kind = 30;
/*  454:     */               }
/*  455: 416 */               jjCheckNAdd(2);
/*  456:     */             }
/*  457: 418 */             else if (this.curChar == '\\')
/*  458:     */             {
/*  459: 419 */               this.jjstateSet[(this.jjnewStateCnt++)] = 1;
/*  460:     */             }
/*  461:     */             break;
/*  462:     */           case 1: 
/*  463: 422 */             if (kind > 29) {
/*  464: 423 */               kind = 29;
/*  465:     */             }
/*  466:     */             break;
/*  467:     */           case 2: 
/*  468: 426 */             if ((0xEFFFFFFF & l) != 0L)
/*  469:     */             {
/*  470: 428 */               if (kind > 30) {
/*  471: 429 */                 kind = 30;
/*  472:     */               }
/*  473: 430 */               jjCheckNAdd(2);
/*  474:     */             }
/*  475:     */             break;
/*  476:     */           }
/*  477: 434 */         } while (i != startsAt);
/*  478:     */       }
/*  479:     */       else
/*  480:     */       {
/*  481: 438 */         int i2 = (this.curChar & 0xFF) >> '\006';
/*  482: 439 */         long l2 = 1L << (this.curChar & 0x3F);
/*  483:     */         do
/*  484:     */         {
/*  485: 442 */           switch (this.jjstateSet[(--i)])
/*  486:     */           {
/*  487:     */           case 0: 
/*  488:     */           case 2: 
/*  489: 446 */             if ((jjbitVec0[i2] & l2) != 0L)
/*  490:     */             {
/*  491: 448 */               if (kind > 30) {
/*  492: 449 */                 kind = 30;
/*  493:     */               }
/*  494: 450 */               jjCheckNAdd(2);
/*  495:     */             }
/*  496: 451 */             break;
/*  497:     */           case 1: 
/*  498: 453 */             if (((jjbitVec0[i2] & l2) != 0L) && (kind > 29)) {
/*  499: 454 */               kind = 29;
/*  500:     */             }
/*  501:     */             break;
/*  502:     */           }
/*  503: 458 */         } while (i != startsAt);
/*  504:     */       }
/*  505: 460 */       if (kind != 2147483647)
/*  506:     */       {
/*  507: 462 */         this.jjmatchedKind = kind;
/*  508: 463 */         this.jjmatchedPos = curPos;
/*  509: 464 */         kind = 2147483647;
/*  510:     */       }
/*  511: 466 */       curPos++;
/*  512: 467 */       if ((i = this.jjnewStateCnt) == (startsAt = 3 - (this.jjnewStateCnt = startsAt))) {
/*  513: 468 */         return curPos;
/*  514:     */       }
/*  515:     */       try
/*  516:     */       {
/*  517: 469 */         this.curChar = this.input_stream.readChar();
/*  518:     */       }
/*  519:     */       catch (IOException e) {}
/*  520:     */     }
/*  521: 470 */     return curPos;
/*  522:     */   }
/*  523:     */   
/*  524:     */   private final int jjStopStringLiteralDfa_3(int pos, long active0)
/*  525:     */   {
/*  526: 475 */     switch (pos)
/*  527:     */     {
/*  528:     */     }
/*  529: 478 */     return -1;
/*  530:     */   }
/*  531:     */   
/*  532:     */   private final int jjStartNfa_3(int pos, long active0)
/*  533:     */   {
/*  534: 483 */     return jjMoveNfa_3(jjStopStringLiteralDfa_3(pos, active0), pos + 1);
/*  535:     */   }
/*  536:     */   
/*  537:     */   private final int jjStartNfaWithStates_3(int pos, int kind, int state)
/*  538:     */   {
/*  539: 487 */     this.jjmatchedKind = kind;
/*  540: 488 */     this.jjmatchedPos = pos;
/*  541:     */     try
/*  542:     */     {
/*  543: 489 */       this.curChar = this.input_stream.readChar();
/*  544:     */     }
/*  545:     */     catch (IOException e)
/*  546:     */     {
/*  547: 490 */       return pos + 1;
/*  548:     */     }
/*  549: 491 */     return jjMoveNfa_3(state, pos + 1);
/*  550:     */   }
/*  551:     */   
/*  552:     */   private final int jjMoveStringLiteralDfa0_3()
/*  553:     */   {
/*  554: 495 */     switch (this.curChar)
/*  555:     */     {
/*  556:     */     case '(': 
/*  557: 498 */       return jjStopAtPos(0, 25);
/*  558:     */     case ')': 
/*  559: 500 */       return jjStopAtPos(0, 26);
/*  560:     */     }
/*  561: 502 */     return jjMoveNfa_3(0, 0);
/*  562:     */   }
/*  563:     */   
/*  564:     */   private final int jjMoveNfa_3(int startState, int curPos)
/*  565:     */   {
/*  566: 508 */     int startsAt = 0;
/*  567: 509 */     this.jjnewStateCnt = 3;
/*  568: 510 */     int i = 1;
/*  569: 511 */     this.jjstateSet[0] = startState;
/*  570: 512 */     int kind = 2147483647;
/*  571:     */     for (;;)
/*  572:     */     {
/*  573: 515 */       if (++this.jjround == 2147483647) {
/*  574: 516 */         ReInitRounds();
/*  575:     */       }
/*  576: 517 */       if (this.curChar < '@')
/*  577:     */       {
/*  578: 519 */         long l = 1L << this.curChar;
/*  579:     */         do
/*  580:     */         {
/*  581: 522 */           switch (this.jjstateSet[(--i)])
/*  582:     */           {
/*  583:     */           case 0: 
/*  584: 525 */             if (kind > 27) {
/*  585: 526 */               kind = 27;
/*  586:     */             }
/*  587:     */             break;
/*  588:     */           case 1: 
/*  589: 529 */             if (kind > 24) {
/*  590: 530 */               kind = 24;
/*  591:     */             }
/*  592:     */             break;
/*  593:     */           }
/*  594: 534 */         } while (i != startsAt);
/*  595:     */       }
/*  596: 536 */       else if (this.curChar < '')
/*  597:     */       {
/*  598: 538 */         long l = 1L << (this.curChar & 0x3F);
/*  599:     */         do
/*  600:     */         {
/*  601: 541 */           switch (this.jjstateSet[(--i)])
/*  602:     */           {
/*  603:     */           case 0: 
/*  604: 544 */             if (kind > 27) {
/*  605: 545 */               kind = 27;
/*  606:     */             }
/*  607: 546 */             if (this.curChar == '\\') {
/*  608: 547 */               this.jjstateSet[(this.jjnewStateCnt++)] = 1;
/*  609:     */             }
/*  610:     */             break;
/*  611:     */           case 1: 
/*  612: 550 */             if (kind > 24) {
/*  613: 551 */               kind = 24;
/*  614:     */             }
/*  615:     */             break;
/*  616:     */           case 2: 
/*  617: 554 */             if (kind > 27) {
/*  618: 555 */               kind = 27;
/*  619:     */             }
/*  620:     */             break;
/*  621:     */           }
/*  622: 559 */         } while (i != startsAt);
/*  623:     */       }
/*  624:     */       else
/*  625:     */       {
/*  626: 563 */         int i2 = (this.curChar & 0xFF) >> '\006';
/*  627: 564 */         long l2 = 1L << (this.curChar & 0x3F);
/*  628:     */         do
/*  629:     */         {
/*  630: 567 */           switch (this.jjstateSet[(--i)])
/*  631:     */           {
/*  632:     */           case 0: 
/*  633: 570 */             if (((jjbitVec0[i2] & l2) != 0L) && (kind > 27)) {
/*  634: 571 */               kind = 27;
/*  635:     */             }
/*  636:     */             break;
/*  637:     */           case 1: 
/*  638: 574 */             if (((jjbitVec0[i2] & l2) != 0L) && (kind > 24)) {
/*  639: 575 */               kind = 24;
/*  640:     */             }
/*  641:     */             break;
/*  642:     */           }
/*  643: 579 */         } while (i != startsAt);
/*  644:     */       }
/*  645: 581 */       if (kind != 2147483647)
/*  646:     */       {
/*  647: 583 */         this.jjmatchedKind = kind;
/*  648: 584 */         this.jjmatchedPos = curPos;
/*  649: 585 */         kind = 2147483647;
/*  650:     */       }
/*  651: 587 */       curPos++;
/*  652: 588 */       if ((i = this.jjnewStateCnt) == (startsAt = 3 - (this.jjnewStateCnt = startsAt))) {
/*  653: 589 */         return curPos;
/*  654:     */       }
/*  655:     */       try
/*  656:     */       {
/*  657: 590 */         this.curChar = this.input_stream.readChar();
/*  658:     */       }
/*  659:     */       catch (IOException e) {}
/*  660:     */     }
/*  661: 591 */     return curPos;
/*  662:     */   }
/*  663:     */   
/*  664:     */   private final int jjStopStringLiteralDfa_1(int pos, long active0)
/*  665:     */   {
/*  666: 596 */     switch (pos)
/*  667:     */     {
/*  668:     */     }
/*  669: 599 */     return -1;
/*  670:     */   }
/*  671:     */   
/*  672:     */   private final int jjStartNfa_1(int pos, long active0)
/*  673:     */   {
/*  674: 604 */     return jjMoveNfa_1(jjStopStringLiteralDfa_1(pos, active0), pos + 1);
/*  675:     */   }
/*  676:     */   
/*  677:     */   private final int jjStartNfaWithStates_1(int pos, int kind, int state)
/*  678:     */   {
/*  679: 608 */     this.jjmatchedKind = kind;
/*  680: 609 */     this.jjmatchedPos = pos;
/*  681:     */     try
/*  682:     */     {
/*  683: 610 */       this.curChar = this.input_stream.readChar();
/*  684:     */     }
/*  685:     */     catch (IOException e)
/*  686:     */     {
/*  687: 611 */       return pos + 1;
/*  688:     */     }
/*  689: 612 */     return jjMoveNfa_1(state, pos + 1);
/*  690:     */   }
/*  691:     */   
/*  692:     */   private final int jjMoveStringLiteralDfa0_1()
/*  693:     */   {
/*  694: 616 */     switch (this.curChar)
/*  695:     */     {
/*  696:     */     case ']': 
/*  697: 619 */       return jjStopAtPos(0, 18);
/*  698:     */     }
/*  699: 621 */     return jjMoveNfa_1(0, 0);
/*  700:     */   }
/*  701:     */   
/*  702:     */   private final int jjMoveNfa_1(int startState, int curPos)
/*  703:     */   {
/*  704: 627 */     int startsAt = 0;
/*  705: 628 */     this.jjnewStateCnt = 3;
/*  706: 629 */     int i = 1;
/*  707: 630 */     this.jjstateSet[0] = startState;
/*  708: 631 */     int kind = 2147483647;
/*  709:     */     for (;;)
/*  710:     */     {
/*  711: 634 */       if (++this.jjround == 2147483647) {
/*  712: 635 */         ReInitRounds();
/*  713:     */       }
/*  714: 636 */       if (this.curChar < '@')
/*  715:     */       {
/*  716: 638 */         long l = 1L << this.curChar;
/*  717:     */         do
/*  718:     */         {
/*  719: 641 */           switch (this.jjstateSet[(--i)])
/*  720:     */           {
/*  721:     */           case 0: 
/*  722: 644 */             if (kind > 17) {
/*  723: 645 */               kind = 17;
/*  724:     */             }
/*  725:     */             break;
/*  726:     */           case 1: 
/*  727: 648 */             if (kind > 16) {
/*  728: 649 */               kind = 16;
/*  729:     */             }
/*  730:     */             break;
/*  731:     */           }
/*  732: 653 */         } while (i != startsAt);
/*  733:     */       }
/*  734: 655 */       else if (this.curChar < '')
/*  735:     */       {
/*  736: 657 */         long l = 1L << (this.curChar & 0x3F);
/*  737:     */         do
/*  738:     */         {
/*  739: 660 */           switch (this.jjstateSet[(--i)])
/*  740:     */           {
/*  741:     */           case 0: 
/*  742: 663 */             if ((0xC7FFFFFF & l) != 0L)
/*  743:     */             {
/*  744: 665 */               if (kind > 17) {
/*  745: 666 */                 kind = 17;
/*  746:     */               }
/*  747:     */             }
/*  748: 668 */             else if (this.curChar == '\\') {
/*  749: 669 */               this.jjstateSet[(this.jjnewStateCnt++)] = 1;
/*  750:     */             }
/*  751:     */             break;
/*  752:     */           case 1: 
/*  753: 672 */             if (kind > 16) {
/*  754: 673 */               kind = 16;
/*  755:     */             }
/*  756:     */             break;
/*  757:     */           case 2: 
/*  758: 676 */             if (((0xC7FFFFFF & l) != 0L) && (kind > 17)) {
/*  759: 677 */               kind = 17;
/*  760:     */             }
/*  761:     */             break;
/*  762:     */           }
/*  763: 681 */         } while (i != startsAt);
/*  764:     */       }
/*  765:     */       else
/*  766:     */       {
/*  767: 685 */         int i2 = (this.curChar & 0xFF) >> '\006';
/*  768: 686 */         long l2 = 1L << (this.curChar & 0x3F);
/*  769:     */         do
/*  770:     */         {
/*  771: 689 */           switch (this.jjstateSet[(--i)])
/*  772:     */           {
/*  773:     */           case 0: 
/*  774: 692 */             if (((jjbitVec0[i2] & l2) != 0L) && (kind > 17)) {
/*  775: 693 */               kind = 17;
/*  776:     */             }
/*  777:     */             break;
/*  778:     */           case 1: 
/*  779: 696 */             if (((jjbitVec0[i2] & l2) != 0L) && (kind > 16)) {
/*  780: 697 */               kind = 16;
/*  781:     */             }
/*  782:     */             break;
/*  783:     */           }
/*  784: 701 */         } while (i != startsAt);
/*  785:     */       }
/*  786: 703 */       if (kind != 2147483647)
/*  787:     */       {
/*  788: 705 */         this.jjmatchedKind = kind;
/*  789: 706 */         this.jjmatchedPos = curPos;
/*  790: 707 */         kind = 2147483647;
/*  791:     */       }
/*  792: 709 */       curPos++;
/*  793: 710 */       if ((i = this.jjnewStateCnt) == (startsAt = 3 - (this.jjnewStateCnt = startsAt))) {
/*  794: 711 */         return curPos;
/*  795:     */       }
/*  796:     */       try
/*  797:     */       {
/*  798: 712 */         this.curChar = this.input_stream.readChar();
/*  799:     */       }
/*  800:     */       catch (IOException e) {}
/*  801:     */     }
/*  802: 713 */     return curPos;
/*  803:     */   }
/*  804:     */   
/*  805: 716 */   static final int[] jjnextStates = new int[0];
/*  806: 718 */   public static final String[] jjstrLiteralImages = { "", "\r", "\n", ",", ":", ";", "<", ">", "@", ".", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null };
/*  807: 722 */   public static final String[] lexStateNames = { "DEFAULT", "INDOMAINLITERAL", "INCOMMENT", "NESTED_COMMENT", "INQUOTEDSTRING" };
/*  808: 729 */   public static final int[] jjnewLexState = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 1, -1, -1, 0, 2, 0, -1, 3, -1, -1, -1, -1, -1, 4, -1, -1, 0, -1, -1 };
/*  809: 733 */   static final long[] jjtoToken = { 2147763199L };
/*  810: 736 */   static final long[] jjtoSkip = { 1049600L };
/*  811: 739 */   static final long[] jjtoSpecial = { 1024L };
/*  812: 742 */   static final long[] jjtoMore = { 2146140160L };
/*  813:     */   protected SimpleCharStream input_stream;
/*  814: 746 */   private final int[] jjrounds = new int[3];
/*  815: 747 */   private final int[] jjstateSet = new int[6];
/*  816:     */   StringBuffer image;
/*  817:     */   int jjimageLen;
/*  818:     */   int lengthOfMatch;
/*  819:     */   protected char curChar;
/*  820:     */   
/*  821:     */   public AddressListParserTokenManager(SimpleCharStream stream)
/*  822:     */   {
/*  823: 755 */     this.input_stream = stream;
/*  824:     */   }
/*  825:     */   
/*  826:     */   public AddressListParserTokenManager(SimpleCharStream stream, int lexState)
/*  827:     */   {
/*  828: 758 */     this(stream);
/*  829: 759 */     SwitchTo(lexState);
/*  830:     */   }
/*  831:     */   
/*  832:     */   public void ReInit(SimpleCharStream stream)
/*  833:     */   {
/*  834: 763 */     this.jjmatchedPos = (this.jjnewStateCnt = 0);
/*  835: 764 */     this.curLexState = this.defaultLexState;
/*  836: 765 */     this.input_stream = stream;
/*  837: 766 */     ReInitRounds();
/*  838:     */   }
/*  839:     */   
/*  840:     */   private final void ReInitRounds()
/*  841:     */   {
/*  842: 771 */     this.jjround = -2147483647;
/*  843: 772 */     for (int i = 3; i-- > 0;) {
/*  844: 773 */       this.jjrounds[i] = -2147483648;
/*  845:     */     }
/*  846:     */   }
/*  847:     */   
/*  848:     */   public void ReInit(SimpleCharStream stream, int lexState)
/*  849:     */   {
/*  850: 777 */     ReInit(stream);
/*  851: 778 */     SwitchTo(lexState);
/*  852:     */   }
/*  853:     */   
/*  854:     */   public void SwitchTo(int lexState)
/*  855:     */   {
/*  856: 782 */     if ((lexState >= 5) || (lexState < 0)) {
/*  857: 783 */       throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", 2);
/*  858:     */     }
/*  859: 785 */     this.curLexState = lexState;
/*  860:     */   }
/*  861:     */   
/*  862:     */   protected Token jjFillToken()
/*  863:     */   {
/*  864: 790 */     Token t = Token.newToken(this.jjmatchedKind);
/*  865: 791 */     t.kind = this.jjmatchedKind;
/*  866: 792 */     String im = jjstrLiteralImages[this.jjmatchedKind];
/*  867: 793 */     t.image = (im == null ? this.input_stream.GetImage() : im);
/*  868: 794 */     t.beginLine = this.input_stream.getBeginLine();
/*  869: 795 */     t.beginColumn = this.input_stream.getBeginColumn();
/*  870: 796 */     t.endLine = this.input_stream.getEndLine();
/*  871: 797 */     t.endColumn = this.input_stream.getEndColumn();
/*  872: 798 */     return t;
/*  873:     */   }
/*  874:     */   
/*  875: 801 */   int curLexState = 0;
/*  876: 802 */   int defaultLexState = 0;
/*  877:     */   int jjnewStateCnt;
/*  878:     */   int jjround;
/*  879:     */   int jjmatchedPos;
/*  880:     */   int jjmatchedKind;
/*  881:     */   
/*  882:     */   public Token getNextToken()
/*  883:     */   {
/*  884: 811 */     Token specialToken = null;
/*  885:     */     
/*  886: 813 */     int curPos = 0;
/*  887:     */     try
/*  888:     */     {
/*  889: 820 */       this.curChar = this.input_stream.BeginToken();
/*  890:     */     }
/*  891:     */     catch (IOException e)
/*  892:     */     {
/*  893: 824 */       this.jjmatchedKind = 0;
/*  894: 825 */       Token matchedToken = jjFillToken();
/*  895: 826 */       matchedToken.specialToken = specialToken;
/*  896: 827 */       return matchedToken;
/*  897:     */     }
/*  898: 829 */     this.image = null;
/*  899: 830 */     this.jjimageLen = 0;
/*  900:     */     for (;;)
/*  901:     */     {
/*  902: 834 */       switch (this.curLexState)
/*  903:     */       {
/*  904:     */       case 0: 
/*  905: 837 */         this.jjmatchedKind = 2147483647;
/*  906: 838 */         this.jjmatchedPos = 0;
/*  907: 839 */         curPos = jjMoveStringLiteralDfa0_0();
/*  908: 840 */         break;
/*  909:     */       case 1: 
/*  910: 842 */         this.jjmatchedKind = 2147483647;
/*  911: 843 */         this.jjmatchedPos = 0;
/*  912: 844 */         curPos = jjMoveStringLiteralDfa0_1();
/*  913: 845 */         break;
/*  914:     */       case 2: 
/*  915: 847 */         this.jjmatchedKind = 2147483647;
/*  916: 848 */         this.jjmatchedPos = 0;
/*  917: 849 */         curPos = jjMoveStringLiteralDfa0_2();
/*  918: 850 */         break;
/*  919:     */       case 3: 
/*  920: 852 */         this.jjmatchedKind = 2147483647;
/*  921: 853 */         this.jjmatchedPos = 0;
/*  922: 854 */         curPos = jjMoveStringLiteralDfa0_3();
/*  923: 855 */         break;
/*  924:     */       case 4: 
/*  925: 857 */         this.jjmatchedKind = 2147483647;
/*  926: 858 */         this.jjmatchedPos = 0;
/*  927: 859 */         curPos = jjMoveStringLiteralDfa0_4();
/*  928:     */       }
/*  929: 862 */       if (this.jjmatchedKind != 2147483647)
/*  930:     */       {
/*  931: 864 */         if (this.jjmatchedPos + 1 < curPos) {
/*  932: 865 */           this.input_stream.backup(curPos - this.jjmatchedPos - 1);
/*  933:     */         }
/*  934: 866 */         if ((jjtoToken[(this.jjmatchedKind >> 6)] & 1L << (this.jjmatchedKind & 0x3F)) != 0L)
/*  935:     */         {
/*  936: 868 */           Token matchedToken = jjFillToken();
/*  937: 869 */           matchedToken.specialToken = specialToken;
/*  938: 870 */           TokenLexicalActions(matchedToken);
/*  939: 871 */           if (jjnewLexState[this.jjmatchedKind] != -1) {
/*  940: 872 */             this.curLexState = jjnewLexState[this.jjmatchedKind];
/*  941:     */           }
/*  942: 873 */           return matchedToken;
/*  943:     */         }
/*  944: 875 */         if ((jjtoSkip[(this.jjmatchedKind >> 6)] & 1L << (this.jjmatchedKind & 0x3F)) != 0L)
/*  945:     */         {
/*  946: 877 */           if ((jjtoSpecial[(this.jjmatchedKind >> 6)] & 1L << (this.jjmatchedKind & 0x3F)) != 0L)
/*  947:     */           {
/*  948: 879 */             Token matchedToken = jjFillToken();
/*  949: 880 */             if (specialToken == null)
/*  950:     */             {
/*  951: 881 */               specialToken = matchedToken;
/*  952:     */             }
/*  953:     */             else
/*  954:     */             {
/*  955: 884 */               matchedToken.specialToken = specialToken;
/*  956: 885 */               specialToken = specialToken.next = matchedToken;
/*  957:     */             }
/*  958:     */           }
/*  959: 888 */           if (jjnewLexState[this.jjmatchedKind] == -1) {
/*  960:     */             break;
/*  961:     */           }
/*  962: 889 */           this.curLexState = jjnewLexState[this.jjmatchedKind]; break;
/*  963:     */         }
/*  964: 892 */         MoreLexicalActions();
/*  965: 893 */         if (jjnewLexState[this.jjmatchedKind] != -1) {
/*  966: 894 */           this.curLexState = jjnewLexState[this.jjmatchedKind];
/*  967:     */         }
/*  968: 895 */         curPos = 0;
/*  969: 896 */         this.jjmatchedKind = 2147483647;
/*  970:     */         try
/*  971:     */         {
/*  972: 898 */           this.curChar = this.input_stream.readChar();
/*  973:     */         }
/*  974:     */         catch (IOException e1) {}
/*  975:     */       }
/*  976:     */     }
/*  977: 903 */     int error_line = this.input_stream.getEndLine();
/*  978: 904 */     int error_column = this.input_stream.getEndColumn();
/*  979: 905 */     String error_after = null;
/*  980: 906 */     boolean EOFSeen = false;
/*  981:     */     try
/*  982:     */     {
/*  983: 907 */       this.input_stream.readChar();this.input_stream.backup(1);
/*  984:     */     }
/*  985:     */     catch (IOException e1)
/*  986:     */     {
/*  987: 909 */       EOFSeen = true;
/*  988: 910 */       error_after = curPos <= 1 ? "" : this.input_stream.GetImage();
/*  989: 911 */       if ((this.curChar == '\n') || (this.curChar == '\r'))
/*  990:     */       {
/*  991: 912 */         error_line++;
/*  992: 913 */         error_column = 0;
/*  993:     */       }
/*  994:     */       else
/*  995:     */       {
/*  996: 916 */         error_column++;
/*  997:     */       }
/*  998:     */     }
/*  999: 918 */     if (!EOFSeen)
/* 1000:     */     {
/* 1001: 919 */       this.input_stream.backup(1);
/* 1002: 920 */       error_after = curPos <= 1 ? "" : this.input_stream.GetImage();
/* 1003:     */     }
/* 1004: 922 */     throw new TokenMgrError(EOFSeen, this.curLexState, error_line, error_column, error_after, this.curChar, 0);
/* 1005:     */   }
/* 1006:     */   
/* 1007:     */   void MoreLexicalActions()
/* 1008:     */   {
/* 1009: 929 */     this.jjimageLen += (this.lengthOfMatch = this.jjmatchedPos + 1);
/* 1010: 930 */     switch (this.jjmatchedKind)
/* 1011:     */     {
/* 1012:     */     case 16: 
/* 1013: 933 */       if (this.image == null) {
/* 1014: 934 */         this.image = new StringBuffer();
/* 1015:     */       }
/* 1016: 935 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 1017: 936 */       this.jjimageLen = 0;
/* 1018: 937 */       this.image.deleteCharAt(this.image.length() - 2);
/* 1019: 938 */       break;
/* 1020:     */     case 21: 
/* 1021: 940 */       if (this.image == null) {
/* 1022: 941 */         this.image = new StringBuffer();
/* 1023:     */       }
/* 1024: 942 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 1025: 943 */       this.jjimageLen = 0;
/* 1026: 944 */       this.image.deleteCharAt(this.image.length() - 2);
/* 1027: 945 */       break;
/* 1028:     */     case 22: 
/* 1029: 947 */       if (this.image == null) {
/* 1030: 948 */         this.image = new StringBuffer();
/* 1031:     */       }
/* 1032: 949 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 1033: 950 */       this.jjimageLen = 0;
/* 1034: 951 */       commentNest = 1;
/* 1035: 952 */       break;
/* 1036:     */     case 24: 
/* 1037: 954 */       if (this.image == null) {
/* 1038: 955 */         this.image = new StringBuffer();
/* 1039:     */       }
/* 1040: 956 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 1041: 957 */       this.jjimageLen = 0;
/* 1042: 958 */       this.image.deleteCharAt(this.image.length() - 2);
/* 1043: 959 */       break;
/* 1044:     */     case 25: 
/* 1045: 961 */       if (this.image == null) {
/* 1046: 962 */         this.image = new StringBuffer();
/* 1047:     */       }
/* 1048: 963 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 1049: 964 */       this.jjimageLen = 0;
/* 1050: 965 */       commentNest += 1;
/* 1051: 966 */       break;
/* 1052:     */     case 26: 
/* 1053: 968 */       if (this.image == null) {
/* 1054: 969 */         this.image = new StringBuffer();
/* 1055:     */       }
/* 1056: 970 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 1057: 971 */       this.jjimageLen = 0;
/* 1058: 972 */       commentNest -= 1;
/* 1059: 972 */       if (commentNest == 0) {
/* 1060: 972 */         SwitchTo(2);
/* 1061:     */       }
/* 1062:     */       break;
/* 1063:     */     case 28: 
/* 1064: 975 */       if (this.image == null) {
/* 1065: 976 */         this.image = new StringBuffer();
/* 1066:     */       }
/* 1067: 977 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 1068: 978 */       this.jjimageLen = 0;
/* 1069: 979 */       this.image.deleteCharAt(this.image.length() - 1);
/* 1070: 980 */       break;
/* 1071:     */     case 29: 
/* 1072: 982 */       if (this.image == null) {
/* 1073: 983 */         this.image = new StringBuffer();
/* 1074:     */       }
/* 1075: 984 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen));
/* 1076: 985 */       this.jjimageLen = 0;
/* 1077: 986 */       this.image.deleteCharAt(this.image.length() - 2);
/* 1078: 987 */       break;
/* 1079:     */     }
/* 1080:     */   }
/* 1081:     */   
/* 1082:     */   void TokenLexicalActions(Token matchedToken)
/* 1083:     */   {
/* 1084: 994 */     switch (this.jjmatchedKind)
/* 1085:     */     {
/* 1086:     */     case 18: 
/* 1087: 997 */       if (this.image == null) {
/* 1088: 998 */         this.image = new StringBuffer();
/* 1089:     */       }
/* 1090: 999 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 1091:1000 */       matchedToken.image = this.image.toString();
/* 1092:1001 */       break;
/* 1093:     */     case 31: 
/* 1094:1003 */       if (this.image == null) {
/* 1095:1004 */         this.image = new StringBuffer();
/* 1096:     */       }
/* 1097:1005 */       this.image.append(this.input_stream.GetSuffix(this.jjimageLen + (this.lengthOfMatch = this.jjmatchedPos + 1)));
/* 1098:1006 */       matchedToken.image = this.image.substring(0, this.image.length() - 1);
/* 1099:1007 */       break;
/* 1100:     */     }
/* 1101:     */   }
/* 1102:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.address.parser.AddressListParserTokenManager
 * JD-Core Version:    0.7.0.1
 */