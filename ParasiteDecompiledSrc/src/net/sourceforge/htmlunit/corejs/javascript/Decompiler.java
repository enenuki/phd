/*   1:    */ package net.sourceforge.htmlunit.corejs.javascript;
/*   2:    */ 
/*   3:    */ public class Decompiler
/*   4:    */ {
/*   5:    */   public static final int ONLY_BODY_FLAG = 1;
/*   6:    */   public static final int TO_SOURCE_FLAG = 2;
/*   7:    */   public static final int INITIAL_INDENT_PROP = 1;
/*   8:    */   public static final int INDENT_GAP_PROP = 2;
/*   9:    */   public static final int CASE_GAP_PROP = 3;
/*  10:    */   private static final int FUNCTION_END = 163;
/*  11:    */   
/*  12:    */   String getEncodedSource()
/*  13:    */   {
/*  14:113 */     return sourceToString(0);
/*  15:    */   }
/*  16:    */   
/*  17:    */   int getCurrentOffset()
/*  18:    */   {
/*  19:118 */     return this.sourceTop;
/*  20:    */   }
/*  21:    */   
/*  22:    */   int markFunctionStart(int functionType)
/*  23:    */   {
/*  24:123 */     int savedOffset = getCurrentOffset();
/*  25:124 */     addToken(109);
/*  26:125 */     append((char)functionType);
/*  27:126 */     return savedOffset;
/*  28:    */   }
/*  29:    */   
/*  30:    */   int markFunctionEnd(int functionStart)
/*  31:    */   {
/*  32:131 */     int offset = getCurrentOffset();
/*  33:132 */     append('£');
/*  34:133 */     return offset;
/*  35:    */   }
/*  36:    */   
/*  37:    */   void addToken(int token)
/*  38:    */   {
/*  39:138 */     if ((0 > token) || (token > 162)) {
/*  40:139 */       throw new IllegalArgumentException();
/*  41:    */     }
/*  42:141 */     append((char)token);
/*  43:    */   }
/*  44:    */   
/*  45:    */   void addEOL(int token)
/*  46:    */   {
/*  47:146 */     if ((0 > token) || (token > 162)) {
/*  48:147 */       throw new IllegalArgumentException();
/*  49:    */     }
/*  50:149 */     append((char)token);
/*  51:150 */     append('\001');
/*  52:    */   }
/*  53:    */   
/*  54:    */   void addName(String str)
/*  55:    */   {
/*  56:155 */     addToken(39);
/*  57:156 */     appendString(str);
/*  58:    */   }
/*  59:    */   
/*  60:    */   void addString(String str)
/*  61:    */   {
/*  62:161 */     addToken(41);
/*  63:162 */     appendString(str);
/*  64:    */   }
/*  65:    */   
/*  66:    */   void addRegexp(String regexp, String flags)
/*  67:    */   {
/*  68:167 */     addToken(48);
/*  69:168 */     appendString('/' + regexp + '/' + flags);
/*  70:    */   }
/*  71:    */   
/*  72:    */   void addNumber(double n)
/*  73:    */   {
/*  74:173 */     addToken(40);
/*  75:    */     
/*  76:    */ 
/*  77:    */ 
/*  78:    */ 
/*  79:    */ 
/*  80:    */ 
/*  81:    */ 
/*  82:    */ 
/*  83:    */ 
/*  84:    */ 
/*  85:    */ 
/*  86:    */ 
/*  87:    */ 
/*  88:    */ 
/*  89:    */ 
/*  90:    */ 
/*  91:    */ 
/*  92:    */ 
/*  93:192 */     long lbits = n;
/*  94:193 */     if (lbits != n)
/*  95:    */     {
/*  96:196 */       lbits = Double.doubleToLongBits(n);
/*  97:197 */       append('D');
/*  98:198 */       append((char)(int)(lbits >> 48));
/*  99:199 */       append((char)(int)(lbits >> 32));
/* 100:200 */       append((char)(int)(lbits >> 16));
/* 101:201 */       append((char)(int)lbits);
/* 102:    */     }
/* 103:    */     else
/* 104:    */     {
/* 105:206 */       if (lbits < 0L) {
/* 106:206 */         Kit.codeBug();
/* 107:    */       }
/* 108:210 */       if (lbits <= 65535L)
/* 109:    */       {
/* 110:211 */         append('S');
/* 111:212 */         append((char)(int)lbits);
/* 112:    */       }
/* 113:    */       else
/* 114:    */       {
/* 115:215 */         append('J');
/* 116:216 */         append((char)(int)(lbits >> 48));
/* 117:217 */         append((char)(int)(lbits >> 32));
/* 118:218 */         append((char)(int)(lbits >> 16));
/* 119:219 */         append((char)(int)lbits);
/* 120:    */       }
/* 121:    */     }
/* 122:    */   }
/* 123:    */   
/* 124:    */   private void appendString(String str)
/* 125:    */   {
/* 126:226 */     int L = str.length();
/* 127:227 */     int lengthEncodingSize = 1;
/* 128:228 */     if (L >= 32768) {
/* 129:229 */       lengthEncodingSize = 2;
/* 130:    */     }
/* 131:231 */     int nextTop = this.sourceTop + lengthEncodingSize + L;
/* 132:232 */     if (nextTop > this.sourceBuffer.length) {
/* 133:233 */       increaseSourceCapacity(nextTop);
/* 134:    */     }
/* 135:235 */     if (L >= 32768)
/* 136:    */     {
/* 137:238 */       this.sourceBuffer[this.sourceTop] = ((char)(0x8000 | L >>> 16));
/* 138:239 */       this.sourceTop += 1;
/* 139:    */     }
/* 140:241 */     this.sourceBuffer[this.sourceTop] = ((char)L);
/* 141:242 */     this.sourceTop += 1;
/* 142:243 */     str.getChars(0, L, this.sourceBuffer, this.sourceTop);
/* 143:244 */     this.sourceTop = nextTop;
/* 144:    */   }
/* 145:    */   
/* 146:    */   private void append(char c)
/* 147:    */   {
/* 148:249 */     if (this.sourceTop == this.sourceBuffer.length) {
/* 149:250 */       increaseSourceCapacity(this.sourceTop + 1);
/* 150:    */     }
/* 151:252 */     this.sourceBuffer[this.sourceTop] = c;
/* 152:253 */     this.sourceTop += 1;
/* 153:    */   }
/* 154:    */   
/* 155:    */   private void increaseSourceCapacity(int minimalCapacity)
/* 156:    */   {
/* 157:259 */     if (minimalCapacity <= this.sourceBuffer.length) {
/* 158:259 */       Kit.codeBug();
/* 159:    */     }
/* 160:260 */     int newCapacity = this.sourceBuffer.length * 2;
/* 161:261 */     if (newCapacity < minimalCapacity) {
/* 162:262 */       newCapacity = minimalCapacity;
/* 163:    */     }
/* 164:264 */     char[] tmp = new char[newCapacity];
/* 165:265 */     System.arraycopy(this.sourceBuffer, 0, tmp, 0, this.sourceTop);
/* 166:266 */     this.sourceBuffer = tmp;
/* 167:    */   }
/* 168:    */   
/* 169:    */   private String sourceToString(int offset)
/* 170:    */   {
/* 171:271 */     if ((offset < 0) || (this.sourceTop < offset)) {
/* 172:271 */       Kit.codeBug();
/* 173:    */     }
/* 174:272 */     return new String(this.sourceBuffer, offset, this.sourceTop - offset);
/* 175:    */   }
/* 176:    */   
/* 177:    */   public static String decompile(String source, int flags, UintMap properties)
/* 178:    */   {
/* 179:294 */     int length = source.length();
/* 180:295 */     if (length == 0) {
/* 181:295 */       return "";
/* 182:    */     }
/* 183:297 */     int indent = properties.getInt(1, 0);
/* 184:298 */     if (indent < 0) {
/* 185:298 */       throw new IllegalArgumentException();
/* 186:    */     }
/* 187:299 */     int indentGap = properties.getInt(2, 4);
/* 188:300 */     if (indentGap < 0) {
/* 189:300 */       throw new IllegalArgumentException();
/* 190:    */     }
/* 191:301 */     int caseGap = properties.getInt(3, 2);
/* 192:302 */     if (caseGap < 0) {
/* 193:302 */       throw new IllegalArgumentException();
/* 194:    */     }
/* 195:304 */     StringBuffer result = new StringBuffer();
/* 196:305 */     boolean justFunctionBody = 0 != (flags & 0x1);
/* 197:306 */     boolean toSource = 0 != (flags & 0x2);
/* 198:    */     
/* 199:    */ 
/* 200:    */ 
/* 201:    */ 
/* 202:    */ 
/* 203:    */ 
/* 204:    */ 
/* 205:    */ 
/* 206:    */ 
/* 207:    */ 
/* 208:    */ 
/* 209:    */ 
/* 210:    */ 
/* 211:    */ 
/* 212:    */ 
/* 213:    */ 
/* 214:    */ 
/* 215:    */ 
/* 216:    */ 
/* 217:    */ 
/* 218:    */ 
/* 219:    */ 
/* 220:    */ 
/* 221:    */ 
/* 222:    */ 
/* 223:    */ 
/* 224:    */ 
/* 225:    */ 
/* 226:335 */     int braceNesting = 0;
/* 227:336 */     boolean afterFirstEOL = false;
/* 228:337 */     int i = 0;
/* 229:    */     int topFunctionType;
/* 230:    */     int topFunctionType;
/* 231:339 */     if (source.charAt(i) == '')
/* 232:    */     {
/* 233:340 */       i++;
/* 234:341 */       topFunctionType = -1;
/* 235:    */     }
/* 236:    */     else
/* 237:    */     {
/* 238:343 */       topFunctionType = source.charAt(i + 1);
/* 239:    */     }
/* 240:346 */     if (!toSource)
/* 241:    */     {
/* 242:348 */       result.append('\n');
/* 243:349 */       for (int j = 0; j < indent; j++) {
/* 244:350 */         result.append(' ');
/* 245:    */       }
/* 246:    */     }
/* 247:352 */     else if (topFunctionType == 2)
/* 248:    */     {
/* 249:353 */       result.append('(');
/* 250:    */     }
/* 251:357 */     while (i < length) {
/* 252:358 */       switch (source.charAt(i))
/* 253:    */       {
/* 254:    */       case '': 
/* 255:    */       case '': 
/* 256:361 */         result.append(source.charAt(i) == '' ? "get " : "set ");
/* 257:362 */         i++;
/* 258:363 */         i = printSourceString(source, i + 1, false, result);
/* 259:    */         
/* 260:365 */         i++;
/* 261:366 */         break;
/* 262:    */       case '\'': 
/* 263:    */       case '0': 
/* 264:370 */         i = printSourceString(source, i + 1, false, result);
/* 265:371 */         break;
/* 266:    */       case ')': 
/* 267:374 */         i = printSourceString(source, i + 1, true, result);
/* 268:375 */         break;
/* 269:    */       case '(': 
/* 270:378 */         i = printSourceNumber(source, i + 1, result);
/* 271:379 */         break;
/* 272:    */       case '-': 
/* 273:382 */         result.append("true");
/* 274:383 */         break;
/* 275:    */       case ',': 
/* 276:386 */         result.append("false");
/* 277:387 */         break;
/* 278:    */       case '*': 
/* 279:390 */         result.append("null");
/* 280:391 */         break;
/* 281:    */       case '+': 
/* 282:394 */         result.append("this");
/* 283:395 */         break;
/* 284:    */       case 'm': 
/* 285:398 */         i++;
/* 286:399 */         result.append("function ");
/* 287:400 */         break;
/* 288:    */       case '£': 
/* 289:    */         break;
/* 290:    */       case 'Y': 
/* 291:407 */         result.append(", ");
/* 292:408 */         break;
/* 293:    */       case 'U': 
/* 294:411 */         braceNesting++;
/* 295:412 */         if (1 == getNext(source, length, i)) {
/* 296:413 */           indent += indentGap;
/* 297:    */         }
/* 298:414 */         result.append('{');
/* 299:415 */         break;
/* 300:    */       case 'V': 
/* 301:418 */         braceNesting--;
/* 302:423 */         if ((!justFunctionBody) || (braceNesting != 0))
/* 303:    */         {
/* 304:426 */           result.append('}');
/* 305:427 */           switch (getNext(source, length, i))
/* 306:    */           {
/* 307:    */           case 1: 
/* 308:    */           case 163: 
/* 309:430 */             indent -= indentGap;
/* 310:431 */             break;
/* 311:    */           case 113: 
/* 312:    */           case 117: 
/* 313:434 */             indent -= indentGap;
/* 314:435 */             result.append(' ');
/* 315:    */           }
/* 316:    */         }
/* 317:438 */         break;
/* 318:    */       case 'W': 
/* 319:441 */         result.append('(');
/* 320:442 */         break;
/* 321:    */       case 'X': 
/* 322:445 */         result.append(')');
/* 323:446 */         if (85 == getNext(source, length, i)) {
/* 324:447 */           result.append(' ');
/* 325:    */         }
/* 326:    */         break;
/* 327:    */       case 'S': 
/* 328:451 */         result.append('[');
/* 329:452 */         break;
/* 330:    */       case 'T': 
/* 331:455 */         result.append(']');
/* 332:456 */         break;
/* 333:    */       case '\001': 
/* 334:459 */         if (!toSource)
/* 335:    */         {
/* 336:460 */           boolean newLine = true;
/* 337:461 */           if (!afterFirstEOL)
/* 338:    */           {
/* 339:462 */             afterFirstEOL = true;
/* 340:463 */             if (justFunctionBody)
/* 341:    */             {
/* 342:467 */               result.setLength(0);
/* 343:468 */               indent -= indentGap;
/* 344:469 */               newLine = false;
/* 345:    */             }
/* 346:    */           }
/* 347:472 */           if (newLine) {
/* 348:473 */             result.append('\n');
/* 349:    */           }
/* 350:480 */           if (i + 1 < length)
/* 351:    */           {
/* 352:481 */             int less = 0;
/* 353:482 */             int nextToken = source.charAt(i + 1);
/* 354:483 */             if ((nextToken == 115) || (nextToken == 116))
/* 355:    */             {
/* 356:486 */               less = indentGap - caseGap;
/* 357:    */             }
/* 358:487 */             else if (nextToken == 86)
/* 359:    */             {
/* 360:488 */               less = indentGap;
/* 361:    */             }
/* 362:494 */             else if (nextToken == 39)
/* 363:    */             {
/* 364:495 */               int afterName = getSourceStringEnd(source, i + 2);
/* 365:496 */               if (source.charAt(afterName) != 'g') {}
/* 366:    */             }
/* 367:497 */             for (less = indentGap; less < indent; less++) {
/* 368:501 */               result.append(' ');
/* 369:    */             }
/* 370:    */           }
/* 371:    */         }
/* 372:502 */         break;
/* 373:    */       case 'l': 
/* 374:506 */         result.append('.');
/* 375:507 */         break;
/* 376:    */       case '\036': 
/* 377:510 */         result.append("new ");
/* 378:511 */         break;
/* 379:    */       case '\037': 
/* 380:514 */         result.append("delete ");
/* 381:515 */         break;
/* 382:    */       case 'p': 
/* 383:518 */         result.append("if ");
/* 384:519 */         break;
/* 385:    */       case 'q': 
/* 386:522 */         result.append("else ");
/* 387:523 */         break;
/* 388:    */       case 'w': 
/* 389:526 */         result.append("for ");
/* 390:527 */         break;
/* 391:    */       case '4': 
/* 392:530 */         result.append(" in ");
/* 393:531 */         break;
/* 394:    */       case '{': 
/* 395:534 */         result.append("with ");
/* 396:535 */         break;
/* 397:    */       case 'u': 
/* 398:538 */         result.append("while ");
/* 399:539 */         break;
/* 400:    */       case 'v': 
/* 401:542 */         result.append("do ");
/* 402:543 */         break;
/* 403:    */       case 'Q': 
/* 404:546 */         result.append("try ");
/* 405:547 */         break;
/* 406:    */       case '|': 
/* 407:550 */         result.append("catch ");
/* 408:551 */         break;
/* 409:    */       case '}': 
/* 410:554 */         result.append("finally ");
/* 411:555 */         break;
/* 412:    */       case '2': 
/* 413:558 */         result.append("throw ");
/* 414:559 */         break;
/* 415:    */       case 'r': 
/* 416:562 */         result.append("switch ");
/* 417:563 */         break;
/* 418:    */       case 'x': 
/* 419:566 */         result.append("break");
/* 420:567 */         if (39 == getNext(source, length, i)) {
/* 421:568 */           result.append(' ');
/* 422:    */         }
/* 423:    */         break;
/* 424:    */       case 'y': 
/* 425:572 */         result.append("continue");
/* 426:573 */         if (39 == getNext(source, length, i)) {
/* 427:574 */           result.append(' ');
/* 428:    */         }
/* 429:    */         break;
/* 430:    */       case 's': 
/* 431:578 */         result.append("case ");
/* 432:579 */         break;
/* 433:    */       case 't': 
/* 434:582 */         result.append("default");
/* 435:583 */         break;
/* 436:    */       case '\004': 
/* 437:586 */         result.append("return");
/* 438:587 */         if (82 != getNext(source, length, i)) {
/* 439:588 */           result.append(' ');
/* 440:    */         }
/* 441:    */         break;
/* 442:    */       case 'z': 
/* 443:592 */         result.append("var ");
/* 444:593 */         break;
/* 445:    */       case '': 
/* 446:596 */         result.append("let ");
/* 447:597 */         break;
/* 448:    */       case 'R': 
/* 449:600 */         result.append(';');
/* 450:601 */         if (1 != getNext(source, length, i)) {
/* 451:603 */           result.append(' ');
/* 452:    */         }
/* 453:    */         break;
/* 454:    */       case 'Z': 
/* 455:608 */         result.append(" = ");
/* 456:609 */         break;
/* 457:    */       case 'a': 
/* 458:612 */         result.append(" += ");
/* 459:613 */         break;
/* 460:    */       case 'b': 
/* 461:616 */         result.append(" -= ");
/* 462:617 */         break;
/* 463:    */       case 'c': 
/* 464:620 */         result.append(" *= ");
/* 465:621 */         break;
/* 466:    */       case 'd': 
/* 467:624 */         result.append(" /= ");
/* 468:625 */         break;
/* 469:    */       case 'e': 
/* 470:628 */         result.append(" %= ");
/* 471:629 */         break;
/* 472:    */       case '[': 
/* 473:632 */         result.append(" |= ");
/* 474:633 */         break;
/* 475:    */       case '\\': 
/* 476:636 */         result.append(" ^= ");
/* 477:637 */         break;
/* 478:    */       case ']': 
/* 479:640 */         result.append(" &= ");
/* 480:641 */         break;
/* 481:    */       case '^': 
/* 482:644 */         result.append(" <<= ");
/* 483:645 */         break;
/* 484:    */       case '_': 
/* 485:648 */         result.append(" >>= ");
/* 486:649 */         break;
/* 487:    */       case '`': 
/* 488:652 */         result.append(" >>>= ");
/* 489:653 */         break;
/* 490:    */       case 'f': 
/* 491:656 */         result.append(" ? ");
/* 492:657 */         break;
/* 493:    */       case 'B': 
/* 494:665 */         result.append(':');
/* 495:666 */         break;
/* 496:    */       case 'g': 
/* 497:669 */         if (1 == getNext(source, length, i)) {
/* 498:671 */           result.append(':');
/* 499:    */         } else {
/* 500:674 */           result.append(" : ");
/* 501:    */         }
/* 502:675 */         break;
/* 503:    */       case 'h': 
/* 504:678 */         result.append(" || ");
/* 505:679 */         break;
/* 506:    */       case 'i': 
/* 507:682 */         result.append(" && ");
/* 508:683 */         break;
/* 509:    */       case '\t': 
/* 510:686 */         result.append(" | ");
/* 511:687 */         break;
/* 512:    */       case '\n': 
/* 513:690 */         result.append(" ^ ");
/* 514:691 */         break;
/* 515:    */       case '\013': 
/* 516:694 */         result.append(" & ");
/* 517:695 */         break;
/* 518:    */       case '.': 
/* 519:698 */         result.append(" === ");
/* 520:699 */         break;
/* 521:    */       case '/': 
/* 522:702 */         result.append(" !== ");
/* 523:703 */         break;
/* 524:    */       case '\f': 
/* 525:706 */         result.append(" == ");
/* 526:707 */         break;
/* 527:    */       case '\r': 
/* 528:710 */         result.append(" != ");
/* 529:711 */         break;
/* 530:    */       case '\017': 
/* 531:714 */         result.append(" <= ");
/* 532:715 */         break;
/* 533:    */       case '\016': 
/* 534:718 */         result.append(" < ");
/* 535:719 */         break;
/* 536:    */       case '\021': 
/* 537:722 */         result.append(" >= ");
/* 538:723 */         break;
/* 539:    */       case '\020': 
/* 540:726 */         result.append(" > ");
/* 541:727 */         break;
/* 542:    */       case '5': 
/* 543:730 */         result.append(" instanceof ");
/* 544:731 */         break;
/* 545:    */       case '\022': 
/* 546:734 */         result.append(" << ");
/* 547:735 */         break;
/* 548:    */       case '\023': 
/* 549:738 */         result.append(" >> ");
/* 550:739 */         break;
/* 551:    */       case '\024': 
/* 552:742 */         result.append(" >>> ");
/* 553:743 */         break;
/* 554:    */       case ' ': 
/* 555:746 */         result.append("typeof ");
/* 556:747 */         break;
/* 557:    */       case '~': 
/* 558:750 */         result.append("void ");
/* 559:751 */         break;
/* 560:    */       case '': 
/* 561:754 */         result.append("const ");
/* 562:755 */         break;
/* 563:    */       case 'H': 
/* 564:758 */         result.append("yield ");
/* 565:759 */         break;
/* 566:    */       case '\032': 
/* 567:762 */         result.append('!');
/* 568:763 */         break;
/* 569:    */       case '\033': 
/* 570:766 */         result.append('~');
/* 571:767 */         break;
/* 572:    */       case '\034': 
/* 573:770 */         result.append('+');
/* 574:771 */         break;
/* 575:    */       case '\035': 
/* 576:774 */         result.append('-');
/* 577:775 */         break;
/* 578:    */       case 'j': 
/* 579:778 */         result.append("++");
/* 580:779 */         break;
/* 581:    */       case 'k': 
/* 582:782 */         result.append("--");
/* 583:783 */         break;
/* 584:    */       case '\025': 
/* 585:786 */         result.append(" + ");
/* 586:787 */         break;
/* 587:    */       case '\026': 
/* 588:790 */         result.append(" - ");
/* 589:791 */         break;
/* 590:    */       case '\027': 
/* 591:794 */         result.append(" * ");
/* 592:795 */         break;
/* 593:    */       case '\030': 
/* 594:798 */         result.append(" / ");
/* 595:799 */         break;
/* 596:    */       case '\031': 
/* 597:802 */         result.append(" % ");
/* 598:803 */         break;
/* 599:    */       case '': 
/* 600:806 */         result.append("::");
/* 601:807 */         break;
/* 602:    */       case '': 
/* 603:810 */         result.append("..");
/* 604:811 */         break;
/* 605:    */       case '': 
/* 606:814 */         result.append(".(");
/* 607:815 */         break;
/* 608:    */       case '': 
/* 609:818 */         result.append('@');
/* 610:819 */         break;
/* 611:    */       case ' ': 
/* 612:822 */         result.append("debugger;\n");
/* 613:823 */         break;
/* 614:    */       case '\002': 
/* 615:    */       case '\003': 
/* 616:    */       case '\005': 
/* 617:    */       case '\006': 
/* 618:    */       case '\007': 
/* 619:    */       case '\b': 
/* 620:    */       case '!': 
/* 621:    */       case '"': 
/* 622:    */       case '#': 
/* 623:    */       case '$': 
/* 624:    */       case '%': 
/* 625:    */       case '&': 
/* 626:    */       case '1': 
/* 627:    */       case '3': 
/* 628:    */       case '6': 
/* 629:    */       case '7': 
/* 630:    */       case '8': 
/* 631:    */       case '9': 
/* 632:    */       case ':': 
/* 633:    */       case ';': 
/* 634:    */       case '<': 
/* 635:    */       case '=': 
/* 636:    */       case '>': 
/* 637:    */       case '?': 
/* 638:    */       case '@': 
/* 639:    */       case 'A': 
/* 640:    */       case 'C': 
/* 641:    */       case 'D': 
/* 642:    */       case 'E': 
/* 643:    */       case 'F': 
/* 644:    */       case 'G': 
/* 645:    */       case 'I': 
/* 646:    */       case 'J': 
/* 647:    */       case 'K': 
/* 648:    */       case 'L': 
/* 649:    */       case 'M': 
/* 650:    */       case 'N': 
/* 651:    */       case 'O': 
/* 652:    */       case 'P': 
/* 653:    */       case 'n': 
/* 654:    */       case 'o': 
/* 655:    */       case '': 
/* 656:    */       case '': 
/* 657:    */       case '': 
/* 658:    */       case '': 
/* 659:    */       case '': 
/* 660:    */       case '': 
/* 661:    */       case '': 
/* 662:    */       case '': 
/* 663:    */       case '': 
/* 664:    */       case '': 
/* 665:    */       case '': 
/* 666:    */       case '': 
/* 667:    */       case '': 
/* 668:    */       case '': 
/* 669:    */       case '': 
/* 670:    */       case '': 
/* 671:    */       case '': 
/* 672:    */       case '': 
/* 673:    */       case '': 
/* 674:    */       case '': 
/* 675:    */       case '': 
/* 676:    */       case '': 
/* 677:    */       case '': 
/* 678:    */       case '': 
/* 679:    */       case '': 
/* 680:    */       case '¡': 
/* 681:    */       case '¢': 
/* 682:    */       default: 
/* 683:827 */         throw new RuntimeException("Token: " + Token.name(source.charAt(i)));
/* 684:    */         
/* 685:    */ 
/* 686:830 */         i++;
/* 687:    */       }
/* 688:    */     }
/* 689:833 */     if (!toSource)
/* 690:    */     {
/* 691:835 */       if (!justFunctionBody) {
/* 692:836 */         result.append('\n');
/* 693:    */       }
/* 694:    */     }
/* 695:838 */     else if (topFunctionType == 2) {
/* 696:839 */       result.append(')');
/* 697:    */     }
/* 698:843 */     return result.toString();
/* 699:    */   }
/* 700:    */   
/* 701:    */   private static int getNext(String source, int length, int i)
/* 702:    */   {
/* 703:848 */     return i + 1 < length ? source.charAt(i + 1) : 0;
/* 704:    */   }
/* 705:    */   
/* 706:    */   private static int getSourceStringEnd(String source, int offset)
/* 707:    */   {
/* 708:853 */     return printSourceString(source, offset, false, null);
/* 709:    */   }
/* 710:    */   
/* 711:    */   private static int printSourceString(String source, int offset, boolean asQuotedString, StringBuffer sb)
/* 712:    */   {
/* 713:860 */     int length = source.charAt(offset);
/* 714:861 */     offset++;
/* 715:862 */     if ((0x8000 & length) != 0)
/* 716:    */     {
/* 717:863 */       length = (0x7FFF & length) << 16 | source.charAt(offset);
/* 718:864 */       offset++;
/* 719:    */     }
/* 720:866 */     if (sb != null)
/* 721:    */     {
/* 722:867 */       String str = source.substring(offset, offset + length);
/* 723:868 */       if (!asQuotedString)
/* 724:    */       {
/* 725:869 */         sb.append(str);
/* 726:    */       }
/* 727:    */       else
/* 728:    */       {
/* 729:871 */         sb.append('"');
/* 730:872 */         sb.append(ScriptRuntime.escapeString(str));
/* 731:873 */         sb.append('"');
/* 732:    */       }
/* 733:    */     }
/* 734:876 */     return offset + length;
/* 735:    */   }
/* 736:    */   
/* 737:    */   private static int printSourceNumber(String source, int offset, StringBuffer sb)
/* 738:    */   {
/* 739:882 */     double number = 0.0D;
/* 740:883 */     char type = source.charAt(offset);
/* 741:884 */     offset++;
/* 742:885 */     if (type == 'S')
/* 743:    */     {
/* 744:886 */       if (sb != null)
/* 745:    */       {
/* 746:887 */         int ival = source.charAt(offset);
/* 747:888 */         number = ival;
/* 748:    */       }
/* 749:890 */       offset++;
/* 750:    */     }
/* 751:891 */     else if ((type == 'J') || (type == 'D'))
/* 752:    */     {
/* 753:892 */       if (sb != null)
/* 754:    */       {
/* 755:894 */         long lbits = source.charAt(offset) << 48;
/* 756:895 */         lbits |= source.charAt(offset + 1) << 32;
/* 757:896 */         lbits |= source.charAt(offset + 2) << 16;
/* 758:897 */         lbits |= source.charAt(offset + 3);
/* 759:898 */         if (type == 'J') {
/* 760:899 */           number = lbits;
/* 761:    */         } else {
/* 762:901 */           number = Double.longBitsToDouble(lbits);
/* 763:    */         }
/* 764:    */       }
/* 765:904 */       offset += 4;
/* 766:    */     }
/* 767:    */     else
/* 768:    */     {
/* 769:907 */       throw new RuntimeException();
/* 770:    */     }
/* 771:909 */     if (sb != null) {
/* 772:910 */       sb.append(ScriptRuntime.numberToString(number, 10));
/* 773:    */     }
/* 774:912 */     return offset;
/* 775:    */   }
/* 776:    */   
/* 777:915 */   private char[] sourceBuffer = new char[''];
/* 778:    */   private int sourceTop;
/* 779:    */   private static final boolean printSource = false;
/* 780:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.Decompiler
 * JD-Core Version:    0.7.0.1
 */