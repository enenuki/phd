/*   1:    */ package jxl.biff.formula;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.InputStreamReader;
/*   6:    */ import java.io.Reader;
/*   7:    */ import jxl.biff.WorkbookMethods;
/*   8:    */ 
/*   9:    */ class Yylex
/*  10:    */ {
/*  11:    */   public static final int YYEOF = -1;
/*  12:    */   private static final int ZZ_BUFFERSIZE = 16384;
/*  13:    */   public static final int YYSTRING = 1;
/*  14:    */   public static final int YYINITIAL = 0;
/*  15:    */   private static final String ZZ_CMAP_PACKED = "";
/*  16: 63 */   private static final char[] ZZ_CMAP = zzUnpackCMap("");
/*  17: 68 */   private static final int[] ZZ_ACTION = zzUnpackAction();
/*  18:    */   private static final String ZZ_ACTION_PACKED_0 = "";
/*  19:    */   
/*  20:    */   private static int[] zzUnpackAction()
/*  21:    */   {
/*  22: 81 */     int[] result = new int[94];
/*  23: 82 */     int offset = 0;
/*  24: 83 */     offset = zzUnpackAction("", offset, result);
/*  25: 84 */     return result;
/*  26:    */   }
/*  27:    */   
/*  28:    */   private static int zzUnpackAction(String packed, int offset, int[] result)
/*  29:    */   {
/*  30: 88 */     int i = 0;
/*  31: 89 */     int j = offset;
/*  32: 90 */     int l = packed.length();
/*  33:    */     int count;
/*  34: 91 */     for (; i < l; count > 0)
/*  35:    */     {
/*  36: 92 */       count = packed.charAt(i++);
/*  37: 93 */       int value = packed.charAt(i++);
/*  38: 94 */       result[(j++)] = value;count--;
/*  39:    */     }
/*  40: 96 */     return j;
/*  41:    */   }
/*  42:    */   
/*  43:103 */   private static final int[] ZZ_ROWMAP = zzUnpackRowMap();
/*  44:    */   private static final String ZZ_ROWMAP_PACKED_0 = "";
/*  45:    */   
/*  46:    */   private static int[] zzUnpackRowMap()
/*  47:    */   {
/*  48:120 */     int[] result = new int[94];
/*  49:121 */     int offset = 0;
/*  50:122 */     offset = zzUnpackRowMap("", offset, result);
/*  51:123 */     return result;
/*  52:    */   }
/*  53:    */   
/*  54:    */   private static int zzUnpackRowMap(String packed, int offset, int[] result)
/*  55:    */   {
/*  56:127 */     int i = 0;
/*  57:128 */     int j = offset;
/*  58:129 */     int l = packed.length();
/*  59:130 */     while (i < l)
/*  60:    */     {
/*  61:131 */       int high = packed.charAt(i++) << '\020';
/*  62:132 */       result[(j++)] = (high | packed.charAt(i++));
/*  63:    */     }
/*  64:134 */     return j;
/*  65:    */   }
/*  66:    */   
/*  67:140 */   private static final int[] ZZ_TRANS = zzUnpackTrans();
/*  68:    */   private static final String ZZ_TRANS_PACKED_0 = "";
/*  69:    */   private static final int ZZ_UNKNOWN_ERROR = 0;
/*  70:    */   private static final int ZZ_NO_MATCH = 1;
/*  71:    */   private static final int ZZ_PUSHBACK_2BIG = 2;
/*  72:    */   
/*  73:    */   private static int[] zzUnpackTrans()
/*  74:    */   {
/*  75:212 */     int[] result = new int[2627];
/*  76:213 */     int offset = 0;
/*  77:214 */     offset = zzUnpackTrans("", offset, result);
/*  78:215 */     return result;
/*  79:    */   }
/*  80:    */   
/*  81:    */   private static int zzUnpackTrans(String packed, int offset, int[] result)
/*  82:    */   {
/*  83:219 */     int i = 0;
/*  84:220 */     int j = offset;
/*  85:221 */     int l = packed.length();
/*  86:    */     int count;
/*  87:222 */     for (; i < l; count > 0)
/*  88:    */     {
/*  89:223 */       count = packed.charAt(i++);
/*  90:224 */       int value = packed.charAt(i++);
/*  91:225 */       value--;
/*  92:226 */       result[(j++)] = value;count--;
/*  93:    */     }
/*  94:228 */     return j;
/*  95:    */   }
/*  96:    */   
/*  97:238 */   private static final String[] ZZ_ERROR_MSG = { "Unkown internal scanner error", "Error: could not match input", "Error: pushback value was too large" };
/*  98:247 */   private static final int[] ZZ_ATTRIBUTE = zzUnpackAttribute();
/*  99:    */   private static final String ZZ_ATTRIBUTE_PACKED_0 = "";
/* 100:    */   private Reader zzReader;
/* 101:    */   private int zzState;
/* 102:    */   
/* 103:    */   private static int[] zzUnpackAttribute()
/* 104:    */   {
/* 105:259 */     int[] result = new int[94];
/* 106:260 */     int offset = 0;
/* 107:261 */     offset = zzUnpackAttribute("", offset, result);
/* 108:262 */     return result;
/* 109:    */   }
/* 110:    */   
/* 111:    */   private static int zzUnpackAttribute(String packed, int offset, int[] result)
/* 112:    */   {
/* 113:266 */     int i = 0;
/* 114:267 */     int j = offset;
/* 115:268 */     int l = packed.length();
/* 116:    */     int count;
/* 117:269 */     for (; i < l; count > 0)
/* 118:    */     {
/* 119:270 */       count = packed.charAt(i++);
/* 120:271 */       int value = packed.charAt(i++);
/* 121:272 */       result[(j++)] = value;count--;
/* 122:    */     }
/* 123:274 */     return j;
/* 124:    */   }
/* 125:    */   
/* 126:284 */   private int zzLexicalState = 0;
/* 127:288 */   private char[] zzBuffer = new char[16384];
/* 128:    */   private int zzMarkedPos;
/* 129:    */   private int zzPushbackPos;
/* 130:    */   private int zzCurrentPos;
/* 131:    */   private int zzStartRead;
/* 132:    */   private int zzEndRead;
/* 133:    */   private int yyline;
/* 134:    */   private int yychar;
/* 135:    */   private int yycolumn;
/* 136:321 */   private boolean zzAtBOL = true;
/* 137:    */   private boolean zzAtEOF;
/* 138:    */   private boolean emptyString;
/* 139:    */   private ExternalSheet externalSheet;
/* 140:    */   private WorkbookMethods nameTable;
/* 141:    */   
/* 142:    */   int getPos()
/* 143:    */   {
/* 144:327 */     return this.yychar;
/* 145:    */   }
/* 146:    */   
/* 147:    */   void setExternalSheet(ExternalSheet es)
/* 148:    */   {
/* 149:334 */     this.externalSheet = es;
/* 150:    */   }
/* 151:    */   
/* 152:    */   void setNameTable(WorkbookMethods nt)
/* 153:    */   {
/* 154:339 */     this.nameTable = nt;
/* 155:    */   }
/* 156:    */   
/* 157:    */   Yylex(Reader in)
/* 158:    */   {
/* 159:350 */     this.zzReader = in;
/* 160:    */   }
/* 161:    */   
/* 162:    */   Yylex(InputStream in)
/* 163:    */   {
/* 164:360 */     this(new InputStreamReader(in));
/* 165:    */   }
/* 166:    */   
/* 167:    */   private static char[] zzUnpackCMap(String packed)
/* 168:    */   {
/* 169:370 */     char[] map = new char[65536];
/* 170:371 */     int i = 0;
/* 171:372 */     int j = 0;
/* 172:    */     int count;
/* 173:373 */     for (; i < 100; count > 0)
/* 174:    */     {
/* 175:374 */       count = packed.charAt(i++);
/* 176:375 */       char value = packed.charAt(i++);
/* 177:376 */       map[(j++)] = value;count--;
/* 178:    */     }
/* 179:378 */     return map;
/* 180:    */   }
/* 181:    */   
/* 182:    */   private boolean zzRefill()
/* 183:    */     throws IOException
/* 184:    */   {
/* 185:392 */     if (this.zzStartRead > 0)
/* 186:    */     {
/* 187:393 */       System.arraycopy(this.zzBuffer, this.zzStartRead, this.zzBuffer, 0, this.zzEndRead - this.zzStartRead);
/* 188:    */       
/* 189:    */ 
/* 190:    */ 
/* 191:    */ 
/* 192:398 */       this.zzEndRead -= this.zzStartRead;
/* 193:399 */       this.zzCurrentPos -= this.zzStartRead;
/* 194:400 */       this.zzMarkedPos -= this.zzStartRead;
/* 195:401 */       this.zzPushbackPos -= this.zzStartRead;
/* 196:402 */       this.zzStartRead = 0;
/* 197:    */     }
/* 198:406 */     if (this.zzCurrentPos >= this.zzBuffer.length)
/* 199:    */     {
/* 200:408 */       char[] newBuffer = new char[this.zzCurrentPos * 2];
/* 201:409 */       System.arraycopy(this.zzBuffer, 0, newBuffer, 0, this.zzBuffer.length);
/* 202:410 */       this.zzBuffer = newBuffer;
/* 203:    */     }
/* 204:414 */     int numRead = this.zzReader.read(this.zzBuffer, this.zzEndRead, this.zzBuffer.length - this.zzEndRead);
/* 205:417 */     if (numRead < 0) {
/* 206:418 */       return true;
/* 207:    */     }
/* 208:421 */     this.zzEndRead += numRead;
/* 209:422 */     return false;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public final void yyclose()
/* 213:    */     throws IOException
/* 214:    */   {
/* 215:431 */     this.zzAtEOF = true;
/* 216:432 */     this.zzEndRead = this.zzStartRead;
/* 217:434 */     if (this.zzReader != null) {
/* 218:435 */       this.zzReader.close();
/* 219:    */     }
/* 220:    */   }
/* 221:    */   
/* 222:    */   public final void yyreset(Reader reader)
/* 223:    */   {
/* 224:450 */     this.zzReader = reader;
/* 225:451 */     this.zzAtBOL = true;
/* 226:452 */     this.zzAtEOF = false;
/* 227:453 */     this.zzEndRead = (this.zzStartRead = 0);
/* 228:454 */     this.zzCurrentPos = (this.zzMarkedPos = this.zzPushbackPos = 0);
/* 229:455 */     this.yyline = (this.yychar = this.yycolumn = 0);
/* 230:456 */     this.zzLexicalState = 0;
/* 231:    */   }
/* 232:    */   
/* 233:    */   public final int yystate()
/* 234:    */   {
/* 235:464 */     return this.zzLexicalState;
/* 236:    */   }
/* 237:    */   
/* 238:    */   public final void yybegin(int newState)
/* 239:    */   {
/* 240:474 */     this.zzLexicalState = newState;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public final String yytext()
/* 244:    */   {
/* 245:482 */     return new String(this.zzBuffer, this.zzStartRead, this.zzMarkedPos - this.zzStartRead);
/* 246:    */   }
/* 247:    */   
/* 248:    */   public final char yycharat(int pos)
/* 249:    */   {
/* 250:498 */     return this.zzBuffer[(this.zzStartRead + pos)];
/* 251:    */   }
/* 252:    */   
/* 253:    */   public final int yylength()
/* 254:    */   {
/* 255:506 */     return this.zzMarkedPos - this.zzStartRead;
/* 256:    */   }
/* 257:    */   
/* 258:    */   private void zzScanError(int errorCode)
/* 259:    */   {
/* 260:    */     String message;
/* 261:    */     try
/* 262:    */     {
/* 263:527 */       message = ZZ_ERROR_MSG[errorCode];
/* 264:    */     }
/* 265:    */     catch (ArrayIndexOutOfBoundsException e)
/* 266:    */     {
/* 267:530 */       message = ZZ_ERROR_MSG[0];
/* 268:    */     }
/* 269:533 */     throw new Error(message);
/* 270:    */   }
/* 271:    */   
/* 272:    */   public void yypushback(int number)
/* 273:    */   {
/* 274:546 */     if (number > yylength()) {
/* 275:547 */       zzScanError(2);
/* 276:    */     }
/* 277:549 */     this.zzMarkedPos -= number;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public ParseItem yylex()
/* 281:    */     throws IOException, FormulaException
/* 282:    */   {
/* 283:568 */     int zzEndReadL = this.zzEndRead;
/* 284:569 */     char[] zzBufferL = this.zzBuffer;
/* 285:570 */     char[] zzCMapL = ZZ_CMAP;
/* 286:    */     
/* 287:572 */     int[] zzTransL = ZZ_TRANS;
/* 288:573 */     int[] zzRowMapL = ZZ_ROWMAP;
/* 289:574 */     int[] zzAttrL = ZZ_ATTRIBUTE;
/* 290:    */     for (;;)
/* 291:    */     {
/* 292:577 */       int zzMarkedPosL = this.zzMarkedPos;
/* 293:    */       
/* 294:579 */       this.yychar += zzMarkedPosL - this.zzStartRead;
/* 295:    */       
/* 296:581 */       boolean zzR = false;
/* 297:582 */       for (int zzCurrentPosL = this.zzStartRead; zzCurrentPosL < zzMarkedPosL; zzCurrentPosL++) {
/* 298:584 */         switch (zzBufferL[zzCurrentPosL])
/* 299:    */         {
/* 300:    */         case '\013': 
/* 301:    */         case '\f': 
/* 302:    */         case '': 
/* 303:    */         case ' ': 
/* 304:    */         case ' ': 
/* 305:590 */           this.yyline += 1;
/* 306:591 */           zzR = false;
/* 307:592 */           break;
/* 308:    */         case '\r': 
/* 309:594 */           this.yyline += 1;
/* 310:595 */           zzR = true;
/* 311:596 */           break;
/* 312:    */         case '\n': 
/* 313:598 */           if (zzR) {
/* 314:599 */             zzR = false;
/* 315:    */           } else {
/* 316:601 */             this.yyline += 1;
/* 317:    */           }
/* 318:603 */           break;
/* 319:    */         default: 
/* 320:605 */           zzR = false;
/* 321:    */         }
/* 322:    */       }
/* 323:609 */       if (zzR)
/* 324:    */       {
/* 325:    */         boolean zzPeek;
/* 326:    */         boolean zzPeek;
/* 327:612 */         if (zzMarkedPosL < zzEndReadL)
/* 328:    */         {
/* 329:613 */           zzPeek = zzBufferL[zzMarkedPosL] == '\n';
/* 330:    */         }
/* 331:    */         else
/* 332:    */         {
/* 333:    */           boolean zzPeek;
/* 334:614 */           if (this.zzAtEOF)
/* 335:    */           {
/* 336:615 */             zzPeek = false;
/* 337:    */           }
/* 338:    */           else
/* 339:    */           {
/* 340:617 */             boolean eof = zzRefill();
/* 341:618 */             zzEndReadL = this.zzEndRead;
/* 342:619 */             zzMarkedPosL = this.zzMarkedPos;
/* 343:620 */             zzBufferL = this.zzBuffer;
/* 344:    */             boolean zzPeek;
/* 345:621 */             if (eof) {
/* 346:622 */               zzPeek = false;
/* 347:    */             } else {
/* 348:624 */               zzPeek = zzBufferL[zzMarkedPosL] == '\n';
/* 349:    */             }
/* 350:    */           }
/* 351:    */         }
/* 352:626 */         if (zzPeek) {
/* 353:626 */           this.yyline -= 1;
/* 354:    */         }
/* 355:    */       }
/* 356:628 */       int zzAction = -1;
/* 357:    */       
/* 358:630 */       zzCurrentPosL = this.zzCurrentPos = this.zzStartRead = zzMarkedPosL;
/* 359:    */       
/* 360:632 */       this.zzState = this.zzLexicalState;
/* 361:    */       int zzInput;
/* 362:    */       for (;;)
/* 363:    */       {
/* 364:    */         int zzInput;
/* 365:638 */         if (zzCurrentPosL < zzEndReadL)
/* 366:    */         {
/* 367:639 */           zzInput = zzBufferL[(zzCurrentPosL++)];
/* 368:    */         }
/* 369:    */         else
/* 370:    */         {
/* 371:640 */           if (this.zzAtEOF)
/* 372:    */           {
/* 373:641 */             int zzInput = -1;
/* 374:642 */             break;
/* 375:    */           }
/* 376:646 */           this.zzCurrentPos = zzCurrentPosL;
/* 377:647 */           this.zzMarkedPos = zzMarkedPosL;
/* 378:648 */           boolean eof = zzRefill();
/* 379:    */           
/* 380:650 */           zzCurrentPosL = this.zzCurrentPos;
/* 381:651 */           zzMarkedPosL = this.zzMarkedPos;
/* 382:652 */           zzBufferL = this.zzBuffer;
/* 383:653 */           zzEndReadL = this.zzEndRead;
/* 384:654 */           if (eof)
/* 385:    */           {
/* 386:655 */             int zzInput = -1;
/* 387:656 */             break;
/* 388:    */           }
/* 389:659 */           zzInput = zzBufferL[(zzCurrentPosL++)];
/* 390:    */         }
/* 391:662 */         int zzNext = zzTransL[(zzRowMapL[this.zzState] + zzCMapL[zzInput])];
/* 392:663 */         if (zzNext == -1) {
/* 393:    */           break;
/* 394:    */         }
/* 395:664 */         this.zzState = zzNext;
/* 396:    */         
/* 397:666 */         int zzAttributes = zzAttrL[this.zzState];
/* 398:667 */         if ((zzAttributes & 0x1) == 1)
/* 399:    */         {
/* 400:668 */           zzAction = this.zzState;
/* 401:669 */           zzMarkedPosL = zzCurrentPosL;
/* 402:670 */           if ((zzAttributes & 0x8) == 8) {
/* 403:    */             break;
/* 404:    */           }
/* 405:    */         }
/* 406:    */       }
/* 407:677 */       this.zzMarkedPos = zzMarkedPosL;
/* 408:679 */       switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction])
/* 409:    */       {
/* 410:    */       case 12: 
/* 411:681 */         return new Minus();
/* 412:    */       case 31: 
/* 413:    */         break;
/* 414:    */       case 7: 
/* 415:685 */         return new CloseParentheses();
/* 416:    */       case 32: 
/* 417:    */         break;
/* 418:    */       case 3: 
/* 419:689 */         return new IntegerValue(yytext());
/* 420:    */       case 33: 
/* 421:    */         break;
/* 422:    */       case 24: 
/* 423:693 */         return new DoubleValue(yytext());
/* 424:    */       case 34: 
/* 425:    */         break;
/* 426:    */       case 29: 
/* 427:697 */         return new ColumnRange3d(yytext(), this.externalSheet);
/* 428:    */       case 35: 
/* 429:    */         break;
/* 430:    */       case 4: 
/* 431:701 */         return new RangeSeparator();
/* 432:    */       case 36: 
/* 433:    */         break;
/* 434:    */       case 10: 
/* 435:705 */         return new Divide();
/* 436:    */       case 37: 
/* 437:    */         break;
/* 438:    */       case 25: 
/* 439:709 */         return new CellReference3d(yytext(), this.externalSheet);
/* 440:    */       case 38: 
/* 441:    */         break;
/* 442:    */       case 26: 
/* 443:713 */         return new BooleanValue(yytext());
/* 444:    */       case 39: 
/* 445:    */         break;
/* 446:    */       case 15: 
/* 447:717 */         return new Equal();
/* 448:    */       case 40: 
/* 449:    */         break;
/* 450:    */       case 17: 
/* 451:721 */         yybegin(0);
/* 452:721 */         if (this.emptyString) {
/* 453:721 */           return new StringValue("");
/* 454:    */         }
/* 455:    */       case 41: 
/* 456:    */         break;
/* 457:    */       case 8: 
/* 458:725 */         this.emptyString = true;yybegin(1);
/* 459:    */       case 42: 
/* 460:    */         break;
/* 461:    */       case 21: 
/* 462:729 */         return new NotEqual();
/* 463:    */       case 43: 
/* 464:    */         break;
/* 465:    */       case 22: 
/* 466:733 */         return new LessEqual();
/* 467:    */       case 44: 
/* 468:    */         break;
/* 469:    */       case 16: 
/* 470:737 */         return new LessThan();
/* 471:    */       case 45: 
/* 472:    */         break;
/* 473:    */       case 5: 
/* 474:741 */         return new ArgumentSeparator();
/* 475:    */       case 46: 
/* 476:    */         break;
/* 477:    */       case 30: 
/* 478:745 */         return new Area3d(yytext(), this.externalSheet);
/* 479:    */       case 47: 
/* 480:    */         break;
/* 481:    */       case 14: 
/* 482:749 */         return new GreaterThan();
/* 483:    */       case 48: 
/* 484:    */         break;
/* 485:    */       case 18: 
/* 486:753 */         return new CellReference(yytext());
/* 487:    */       case 49: 
/* 488:    */         break;
/* 489:    */       case 20: 
/* 490:757 */         return new GreaterEqual();
/* 491:    */       case 50: 
/* 492:    */         break;
/* 493:    */       case 27: 
/* 494:761 */         return new Area(yytext());
/* 495:    */       case 51: 
/* 496:    */         break;
/* 497:    */       case 23: 
/* 498:765 */         return new ColumnRange(yytext());
/* 499:    */       case 52: 
/* 500:    */         break;
/* 501:    */       case 1: 
/* 502:769 */         this.emptyString = false;return new StringValue(yytext());
/* 503:    */       case 53: 
/* 504:    */         break;
/* 505:    */       case 2: 
/* 506:773 */         return new NameRange(yytext(), this.nameTable);
/* 507:    */       case 54: 
/* 508:    */         break;
/* 509:    */       case 19: 
/* 510:777 */         return new StringFunction(yytext());
/* 511:    */       case 55: 
/* 512:    */         break;
/* 513:    */       case 11: 
/* 514:781 */         return new Plus();
/* 515:    */       case 56: 
/* 516:    */         break;
/* 517:    */       case 28: 
/* 518:785 */         return new ErrorConstant(yytext());
/* 519:    */       case 57: 
/* 520:    */         break;
/* 521:    */       case 9: 
/* 522:    */       case 58: 
/* 523:    */         break;
/* 524:    */       case 13: 
/* 525:793 */         return new Multiply();
/* 526:    */       case 59: 
/* 527:    */         break;
/* 528:    */       case 6: 
/* 529:797 */         return new OpenParentheses();
/* 530:    */       case 60: 
/* 531:    */         break;
/* 532:    */       }
/* 533:801 */       if ((zzInput == -1) && (this.zzStartRead == this.zzCurrentPos))
/* 534:    */       {
/* 535:802 */         this.zzAtEOF = true;
/* 536:803 */         return null;
/* 537:    */       }
/* 538:806 */       zzScanError(1);
/* 539:    */     }
/* 540:    */   }
/* 541:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     jxl.biff.formula.Yylex
 * JD-Core Version:    0.7.0.1
 */