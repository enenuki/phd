/*   1:    */ package org.apache.james.mime4j.field.datetime.parser;
/*   2:    */ 
/*   3:    */ import java.io.InputStream;
/*   4:    */ import java.io.Reader;
/*   5:    */ import java.io.UnsupportedEncodingException;
/*   6:    */ import java.util.Vector;
/*   7:    */ import org.apache.james.mime4j.field.datetime.DateTime;
/*   8:    */ 
/*   9:    */ public class DateTimeParser
/*  10:    */   implements DateTimeParserConstants
/*  11:    */ {
/*  12:    */   private static final boolean ignoreMilitaryZoneOffset = true;
/*  13:    */   public DateTimeParserTokenManager token_source;
/*  14:    */   SimpleCharStream jj_input_stream;
/*  15:    */   public Token token;
/*  16:    */   public Token jj_nt;
/*  17:    */   private int jj_ntk;
/*  18:    */   private int jj_gen;
/*  19:    */   
/*  20:    */   public static void main(String[] args)
/*  21:    */     throws ParseException
/*  22:    */   {
/*  23:    */     try
/*  24:    */     {
/*  25:    */       for (;;)
/*  26:    */       {
/*  27: 30 */         DateTimeParser parser = new DateTimeParser(System.in);
/*  28: 31 */         parser.parseLine();
/*  29:    */       }
/*  30: 34 */       return;
/*  31:    */     }
/*  32:    */     catch (Exception x)
/*  33:    */     {
/*  34: 33 */       x.printStackTrace();
/*  35:    */     }
/*  36:    */   }
/*  37:    */   
/*  38:    */   private static int parseDigits(Token token)
/*  39:    */   {
/*  40: 40 */     return Integer.parseInt(token.image, 10);
/*  41:    */   }
/*  42:    */   
/*  43:    */   private static int getMilitaryZoneOffset(char c)
/*  44:    */   {
/*  45: 45 */     return 0;
/*  46:    */   }
/*  47:    */   
/*  48:    */   private static class Time
/*  49:    */   {
/*  50:    */     private int hour;
/*  51:    */     private int minute;
/*  52:    */     private int second;
/*  53:    */     private int zone;
/*  54:    */     
/*  55:    */     public Time(int hour, int minute, int second, int zone)
/*  56:    */     {
/*  57: 88 */       this.hour = hour;
/*  58: 89 */       this.minute = minute;
/*  59: 90 */       this.second = second;
/*  60: 91 */       this.zone = zone;
/*  61:    */     }
/*  62:    */     
/*  63:    */     public int getHour()
/*  64:    */     {
/*  65: 94 */       return this.hour;
/*  66:    */     }
/*  67:    */     
/*  68:    */     public int getMinute()
/*  69:    */     {
/*  70: 95 */       return this.minute;
/*  71:    */     }
/*  72:    */     
/*  73:    */     public int getSecond()
/*  74:    */     {
/*  75: 96 */       return this.second;
/*  76:    */     }
/*  77:    */     
/*  78:    */     public int getZone()
/*  79:    */     {
/*  80: 97 */       return this.zone;
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   private static class Date
/*  85:    */   {
/*  86:    */     private String year;
/*  87:    */     private int month;
/*  88:    */     private int day;
/*  89:    */     
/*  90:    */     public Date(String year, int month, int day)
/*  91:    */     {
/*  92:106 */       this.year = year;
/*  93:107 */       this.month = month;
/*  94:108 */       this.day = day;
/*  95:    */     }
/*  96:    */     
/*  97:    */     public String getYear()
/*  98:    */     {
/*  99:111 */       return this.year;
/* 100:    */     }
/* 101:    */     
/* 102:    */     public int getMonth()
/* 103:    */     {
/* 104:112 */       return this.month;
/* 105:    */     }
/* 106:    */     
/* 107:    */     public int getDay()
/* 108:    */     {
/* 109:113 */       return this.day;
/* 110:    */     }
/* 111:    */   }
/* 112:    */   
/* 113:    */   public final DateTime parseLine()
/* 114:    */     throws ParseException
/* 115:    */   {
/* 116:118 */     DateTime dt = date_time();
/* 117:119 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 118:    */     {
/* 119:    */     case 1: 
/* 120:121 */       jj_consume_token(1);
/* 121:122 */       break;
/* 122:    */     default: 
/* 123:124 */       this.jj_la1[0] = this.jj_gen;
/* 124:    */     }
/* 125:127 */     jj_consume_token(2);
/* 126:128 */     return dt;
/* 127:    */   }
/* 128:    */   
/* 129:    */   public final DateTime parseAll()
/* 130:    */     throws ParseException
/* 131:    */   {
/* 132:134 */     DateTime dt = date_time();
/* 133:135 */     jj_consume_token(0);
/* 134:136 */     return dt;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public final DateTime date_time()
/* 138:    */     throws ParseException
/* 139:    */   {
/* 140:142 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 141:    */     {
/* 142:    */     case 4: 
/* 143:    */     case 5: 
/* 144:    */     case 6: 
/* 145:    */     case 7: 
/* 146:    */     case 8: 
/* 147:    */     case 9: 
/* 148:    */     case 10: 
/* 149:150 */       day_of_week();
/* 150:151 */       jj_consume_token(3);
/* 151:152 */       break;
/* 152:    */     default: 
/* 153:154 */       this.jj_la1[1] = this.jj_gen;
/* 154:    */     }
/* 155:157 */     Date d = date();
/* 156:158 */     Time t = time();
/* 157:159 */     return new DateTime(d.getYear(), d.getMonth(), d.getDay(), t.getHour(), t.getMinute(), t.getSecond(), t.getZone());
/* 158:    */   }
/* 159:    */   
/* 160:    */   public final String day_of_week()
/* 161:    */     throws ParseException
/* 162:    */   {
/* 163:172 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 164:    */     {
/* 165:    */     case 4: 
/* 166:174 */       jj_consume_token(4);
/* 167:175 */       break;
/* 168:    */     case 5: 
/* 169:177 */       jj_consume_token(5);
/* 170:178 */       break;
/* 171:    */     case 6: 
/* 172:180 */       jj_consume_token(6);
/* 173:181 */       break;
/* 174:    */     case 7: 
/* 175:183 */       jj_consume_token(7);
/* 176:184 */       break;
/* 177:    */     case 8: 
/* 178:186 */       jj_consume_token(8);
/* 179:187 */       break;
/* 180:    */     case 9: 
/* 181:189 */       jj_consume_token(9);
/* 182:190 */       break;
/* 183:    */     case 10: 
/* 184:192 */       jj_consume_token(10);
/* 185:193 */       break;
/* 186:    */     default: 
/* 187:195 */       this.jj_la1[2] = this.jj_gen;
/* 188:196 */       jj_consume_token(-1);
/* 189:197 */       throw new ParseException();
/* 190:    */     }
/* 191:199 */     return this.token.image;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public final Date date()
/* 195:    */     throws ParseException
/* 196:    */   {
/* 197:205 */     int d = day();
/* 198:206 */     int m = month();
/* 199:207 */     String y = year();
/* 200:208 */     return new Date(y, m, d);
/* 201:    */   }
/* 202:    */   
/* 203:    */   public final int day()
/* 204:    */     throws ParseException
/* 205:    */   {
/* 206:214 */     Token t = jj_consume_token(46);
/* 207:215 */     return parseDigits(t);
/* 208:    */   }
/* 209:    */   
/* 210:    */   public final int month()
/* 211:    */     throws ParseException
/* 212:    */   {
/* 213:220 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 214:    */     {
/* 215:    */     case 11: 
/* 216:222 */       jj_consume_token(11);
/* 217:223 */       return 1;
/* 218:    */     case 12: 
/* 219:226 */       jj_consume_token(12);
/* 220:227 */       return 2;
/* 221:    */     case 13: 
/* 222:230 */       jj_consume_token(13);
/* 223:231 */       return 3;
/* 224:    */     case 14: 
/* 225:234 */       jj_consume_token(14);
/* 226:235 */       return 4;
/* 227:    */     case 15: 
/* 228:238 */       jj_consume_token(15);
/* 229:239 */       return 5;
/* 230:    */     case 16: 
/* 231:242 */       jj_consume_token(16);
/* 232:243 */       return 6;
/* 233:    */     case 17: 
/* 234:246 */       jj_consume_token(17);
/* 235:247 */       return 7;
/* 236:    */     case 18: 
/* 237:250 */       jj_consume_token(18);
/* 238:251 */       return 8;
/* 239:    */     case 19: 
/* 240:254 */       jj_consume_token(19);
/* 241:255 */       return 9;
/* 242:    */     case 20: 
/* 243:258 */       jj_consume_token(20);
/* 244:259 */       return 10;
/* 245:    */     case 21: 
/* 246:262 */       jj_consume_token(21);
/* 247:263 */       return 11;
/* 248:    */     case 22: 
/* 249:266 */       jj_consume_token(22);
/* 250:267 */       return 12;
/* 251:    */     }
/* 252:270 */     this.jj_la1[3] = this.jj_gen;
/* 253:271 */     jj_consume_token(-1);
/* 254:272 */     throw new ParseException();
/* 255:    */   }
/* 256:    */   
/* 257:    */   public final String year()
/* 258:    */     throws ParseException
/* 259:    */   {
/* 260:279 */     Token t = jj_consume_token(46);
/* 261:280 */     return t.image;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public final Time time()
/* 265:    */     throws ParseException
/* 266:    */   {
/* 267:285 */     int s = 0;
/* 268:286 */     int h = hour();
/* 269:287 */     jj_consume_token(23);
/* 270:288 */     int m = minute();
/* 271:289 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 272:    */     {
/* 273:    */     case 23: 
/* 274:291 */       jj_consume_token(23);
/* 275:292 */       s = second();
/* 276:293 */       break;
/* 277:    */     default: 
/* 278:295 */       this.jj_la1[4] = this.jj_gen;
/* 279:    */     }
/* 280:298 */     int z = zone();
/* 281:299 */     return new Time(h, m, s, z);
/* 282:    */   }
/* 283:    */   
/* 284:    */   public final int hour()
/* 285:    */     throws ParseException
/* 286:    */   {
/* 287:305 */     Token t = jj_consume_token(46);
/* 288:306 */     return parseDigits(t);
/* 289:    */   }
/* 290:    */   
/* 291:    */   public final int minute()
/* 292:    */     throws ParseException
/* 293:    */   {
/* 294:312 */     Token t = jj_consume_token(46);
/* 295:313 */     return parseDigits(t);
/* 296:    */   }
/* 297:    */   
/* 298:    */   public final int second()
/* 299:    */     throws ParseException
/* 300:    */   {
/* 301:319 */     Token t = jj_consume_token(46);
/* 302:320 */     return parseDigits(t);
/* 303:    */   }
/* 304:    */   
/* 305:    */   public final int zone()
/* 306:    */     throws ParseException
/* 307:    */   {
/* 308:    */     int z;
/* 309:326 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 310:    */     {
/* 311:    */     case 24: 
/* 312:328 */       Token t = jj_consume_token(24);
/* 313:329 */       Token u = jj_consume_token(46);
/* 314:330 */       z = parseDigits(u) * (t.image.equals("-") ? -1 : 1);
/* 315:331 */       break;
/* 316:    */     case 25: 
/* 317:    */     case 26: 
/* 318:    */     case 27: 
/* 319:    */     case 28: 
/* 320:    */     case 29: 
/* 321:    */     case 30: 
/* 322:    */     case 31: 
/* 323:    */     case 32: 
/* 324:    */     case 33: 
/* 325:    */     case 34: 
/* 326:    */     case 35: 
/* 327:343 */       z = obs_zone();
/* 328:344 */       break;
/* 329:    */     default: 
/* 330:346 */       this.jj_la1[5] = this.jj_gen;
/* 331:347 */       jj_consume_token(-1);
/* 332:348 */       throw new ParseException();
/* 333:    */     }
/* 334:350 */     return z;
/* 335:    */   }
/* 336:    */   
/* 337:    */   public final int obs_zone()
/* 338:    */     throws ParseException
/* 339:    */   {
/* 340:    */     int z;
/* 341:356 */     switch (this.jj_ntk == -1 ? jj_ntk() : this.jj_ntk)
/* 342:    */     {
/* 343:    */     case 25: 
/* 344:358 */       jj_consume_token(25);
/* 345:359 */       z = 0;
/* 346:360 */       break;
/* 347:    */     case 26: 
/* 348:362 */       jj_consume_token(26);
/* 349:363 */       z = 0;
/* 350:364 */       break;
/* 351:    */     case 27: 
/* 352:366 */       jj_consume_token(27);
/* 353:367 */       z = -5;
/* 354:368 */       break;
/* 355:    */     case 28: 
/* 356:370 */       jj_consume_token(28);
/* 357:371 */       z = -4;
/* 358:372 */       break;
/* 359:    */     case 29: 
/* 360:374 */       jj_consume_token(29);
/* 361:375 */       z = -6;
/* 362:376 */       break;
/* 363:    */     case 30: 
/* 364:378 */       jj_consume_token(30);
/* 365:379 */       z = -5;
/* 366:380 */       break;
/* 367:    */     case 31: 
/* 368:382 */       jj_consume_token(31);
/* 369:383 */       z = -7;
/* 370:384 */       break;
/* 371:    */     case 32: 
/* 372:386 */       jj_consume_token(32);
/* 373:387 */       z = -6;
/* 374:388 */       break;
/* 375:    */     case 33: 
/* 376:390 */       jj_consume_token(33);
/* 377:391 */       z = -8;
/* 378:392 */       break;
/* 379:    */     case 34: 
/* 380:394 */       jj_consume_token(34);
/* 381:395 */       z = -7;
/* 382:396 */       break;
/* 383:    */     case 35: 
/* 384:398 */       Token t = jj_consume_token(35);
/* 385:399 */       z = getMilitaryZoneOffset(t.image.charAt(0));
/* 386:400 */       break;
/* 387:    */     default: 
/* 388:402 */       this.jj_la1[6] = this.jj_gen;
/* 389:403 */       jj_consume_token(-1);
/* 390:404 */       throw new ParseException();
/* 391:    */     }
/* 392:406 */     return z * 100;
/* 393:    */   }
/* 394:    */   
/* 395:415 */   private final int[] jj_la1 = new int[7];
/* 396:    */   private static int[] jj_la1_0;
/* 397:    */   private static int[] jj_la1_1;
/* 398:    */   
/* 399:    */   static
/* 400:    */   {
/* 401:419 */     jj_la1_0();
/* 402:420 */     jj_la1_1();
/* 403:    */   }
/* 404:    */   
/* 405:    */   private static void jj_la1_0()
/* 406:    */   {
/* 407:423 */     jj_la1_0 = new int[] { 2, 2032, 2032, 8386560, 8388608, -16777216, -33554432 };
/* 408:    */   }
/* 409:    */   
/* 410:    */   private static void jj_la1_1()
/* 411:    */   {
/* 412:426 */     jj_la1_1 = new int[] { 0, 0, 0, 0, 0, 15, 15 };
/* 413:    */   }
/* 414:    */   
/* 415:    */   public DateTimeParser(InputStream stream)
/* 416:    */   {
/* 417:430 */     this(stream, null);
/* 418:    */   }
/* 419:    */   
/* 420:    */   public DateTimeParser(InputStream stream, String encoding)
/* 421:    */   {
/* 422:    */     try
/* 423:    */     {
/* 424:433 */       this.jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1);
/* 425:    */     }
/* 426:    */     catch (UnsupportedEncodingException e)
/* 427:    */     {
/* 428:433 */       throw new RuntimeException(e);
/* 429:    */     }
/* 430:434 */     this.token_source = new DateTimeParserTokenManager(this.jj_input_stream);
/* 431:435 */     this.token = new Token();
/* 432:436 */     this.jj_ntk = -1;
/* 433:437 */     this.jj_gen = 0;
/* 434:438 */     for (int i = 0; i < 7; i++) {
/* 435:438 */       this.jj_la1[i] = -1;
/* 436:    */     }
/* 437:    */   }
/* 438:    */   
/* 439:    */   public void ReInit(InputStream stream)
/* 440:    */   {
/* 441:442 */     ReInit(stream, null);
/* 442:    */   }
/* 443:    */   
/* 444:    */   public void ReInit(InputStream stream, String encoding)
/* 445:    */   {
/* 446:    */     try
/* 447:    */     {
/* 448:445 */       this.jj_input_stream.ReInit(stream, encoding, 1, 1);
/* 449:    */     }
/* 450:    */     catch (UnsupportedEncodingException e)
/* 451:    */     {
/* 452:445 */       throw new RuntimeException(e);
/* 453:    */     }
/* 454:446 */     this.token_source.ReInit(this.jj_input_stream);
/* 455:447 */     this.token = new Token();
/* 456:448 */     this.jj_ntk = -1;
/* 457:449 */     this.jj_gen = 0;
/* 458:450 */     for (int i = 0; i < 7; i++) {
/* 459:450 */       this.jj_la1[i] = -1;
/* 460:    */     }
/* 461:    */   }
/* 462:    */   
/* 463:    */   public DateTimeParser(Reader stream)
/* 464:    */   {
/* 465:454 */     this.jj_input_stream = new SimpleCharStream(stream, 1, 1);
/* 466:455 */     this.token_source = new DateTimeParserTokenManager(this.jj_input_stream);
/* 467:456 */     this.token = new Token();
/* 468:457 */     this.jj_ntk = -1;
/* 469:458 */     this.jj_gen = 0;
/* 470:459 */     for (int i = 0; i < 7; i++) {
/* 471:459 */       this.jj_la1[i] = -1;
/* 472:    */     }
/* 473:    */   }
/* 474:    */   
/* 475:    */   public void ReInit(Reader stream)
/* 476:    */   {
/* 477:463 */     this.jj_input_stream.ReInit(stream, 1, 1);
/* 478:464 */     this.token_source.ReInit(this.jj_input_stream);
/* 479:465 */     this.token = new Token();
/* 480:466 */     this.jj_ntk = -1;
/* 481:467 */     this.jj_gen = 0;
/* 482:468 */     for (int i = 0; i < 7; i++) {
/* 483:468 */       this.jj_la1[i] = -1;
/* 484:    */     }
/* 485:    */   }
/* 486:    */   
/* 487:    */   public DateTimeParser(DateTimeParserTokenManager tm)
/* 488:    */   {
/* 489:472 */     this.token_source = tm;
/* 490:473 */     this.token = new Token();
/* 491:474 */     this.jj_ntk = -1;
/* 492:475 */     this.jj_gen = 0;
/* 493:476 */     for (int i = 0; i < 7; i++) {
/* 494:476 */       this.jj_la1[i] = -1;
/* 495:    */     }
/* 496:    */   }
/* 497:    */   
/* 498:    */   public void ReInit(DateTimeParserTokenManager tm)
/* 499:    */   {
/* 500:480 */     this.token_source = tm;
/* 501:481 */     this.token = new Token();
/* 502:482 */     this.jj_ntk = -1;
/* 503:483 */     this.jj_gen = 0;
/* 504:484 */     for (int i = 0; i < 7; i++) {
/* 505:484 */       this.jj_la1[i] = -1;
/* 506:    */     }
/* 507:    */   }
/* 508:    */   
/* 509:    */   private final Token jj_consume_token(int kind)
/* 510:    */     throws ParseException
/* 511:    */   {
/* 512:    */     Token oldToken;
/* 513:489 */     if ((oldToken = this.token).next != null) {
/* 514:489 */       this.token = this.token.next;
/* 515:    */     } else {
/* 516:490 */       this.token = (this.token.next = this.token_source.getNextToken());
/* 517:    */     }
/* 518:491 */     this.jj_ntk = -1;
/* 519:492 */     if (this.token.kind == kind)
/* 520:    */     {
/* 521:493 */       this.jj_gen += 1;
/* 522:494 */       return this.token;
/* 523:    */     }
/* 524:496 */     this.token = oldToken;
/* 525:497 */     this.jj_kind = kind;
/* 526:498 */     throw generateParseException();
/* 527:    */   }
/* 528:    */   
/* 529:    */   public final Token getNextToken()
/* 530:    */   {
/* 531:502 */     if (this.token.next != null) {
/* 532:502 */       this.token = this.token.next;
/* 533:    */     } else {
/* 534:503 */       this.token = (this.token.next = this.token_source.getNextToken());
/* 535:    */     }
/* 536:504 */     this.jj_ntk = -1;
/* 537:505 */     this.jj_gen += 1;
/* 538:506 */     return this.token;
/* 539:    */   }
/* 540:    */   
/* 541:    */   public final Token getToken(int index)
/* 542:    */   {
/* 543:510 */     Token t = this.token;
/* 544:511 */     for (int i = 0; i < index; i++) {
/* 545:512 */       if (t.next != null) {
/* 546:512 */         t = t.next;
/* 547:    */       } else {
/* 548:513 */         t = t.next = this.token_source.getNextToken();
/* 549:    */       }
/* 550:    */     }
/* 551:515 */     return t;
/* 552:    */   }
/* 553:    */   
/* 554:    */   private final int jj_ntk()
/* 555:    */   {
/* 556:519 */     if ((this.jj_nt = this.token.next) == null) {
/* 557:520 */       return this.jj_ntk = (this.token.next = this.token_source.getNextToken()).kind;
/* 558:    */     }
/* 559:522 */     return this.jj_ntk = this.jj_nt.kind;
/* 560:    */   }
/* 561:    */   
/* 562:525 */   private Vector<int[]> jj_expentries = new Vector();
/* 563:    */   private int[] jj_expentry;
/* 564:527 */   private int jj_kind = -1;
/* 565:    */   
/* 566:    */   public ParseException generateParseException()
/* 567:    */   {
/* 568:530 */     this.jj_expentries.removeAllElements();
/* 569:531 */     boolean[] la1tokens = new boolean[49];
/* 570:532 */     for (int i = 0; i < 49; i++) {
/* 571:533 */       la1tokens[i] = false;
/* 572:    */     }
/* 573:535 */     if (this.jj_kind >= 0)
/* 574:    */     {
/* 575:536 */       la1tokens[this.jj_kind] = true;
/* 576:537 */       this.jj_kind = -1;
/* 577:    */     }
/* 578:539 */     for (int i = 0; i < 7; i++) {
/* 579:540 */       if (this.jj_la1[i] == this.jj_gen) {
/* 580:541 */         for (int j = 0; j < 32; j++)
/* 581:    */         {
/* 582:542 */           if ((jj_la1_0[i] & 1 << j) != 0) {
/* 583:543 */             la1tokens[j] = true;
/* 584:    */           }
/* 585:545 */           if ((jj_la1_1[i] & 1 << j) != 0) {
/* 586:546 */             la1tokens[(32 + j)] = true;
/* 587:    */           }
/* 588:    */         }
/* 589:    */       }
/* 590:    */     }
/* 591:551 */     for (int i = 0; i < 49; i++) {
/* 592:552 */       if (la1tokens[i] != 0)
/* 593:    */       {
/* 594:553 */         this.jj_expentry = new int[1];
/* 595:554 */         this.jj_expentry[0] = i;
/* 596:555 */         this.jj_expentries.addElement(this.jj_expentry);
/* 597:    */       }
/* 598:    */     }
/* 599:558 */     int[][] exptokseq = new int[this.jj_expentries.size()][];
/* 600:559 */     for (int i = 0; i < this.jj_expentries.size(); i++) {
/* 601:560 */       exptokseq[i] = ((int[])(int[])this.jj_expentries.elementAt(i));
/* 602:    */     }
/* 603:562 */     return new ParseException(this.token, exptokseq, tokenImage);
/* 604:    */   }
/* 605:    */   
/* 606:    */   public final void enable_tracing() {}
/* 607:    */   
/* 608:    */   public final void disable_tracing() {}
/* 609:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.james.mime4j.field.datetime.parser.DateTimeParser
 * JD-Core Version:    0.7.0.1
 */