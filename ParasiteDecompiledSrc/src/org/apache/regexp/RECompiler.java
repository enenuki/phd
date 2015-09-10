/*    1:     */ package org.apache.regexp;
/*    2:     */ 
/*    3:     */ import java.util.Hashtable;
/*    4:     */ 
/*    5:     */ public class RECompiler
/*    6:     */ {
/*    7: 101 */   static int brackets = 0;
/*    8: 102 */   static int[] bracketStart = null;
/*    9: 103 */   static int[] bracketEnd = null;
/*   10: 104 */   static int[] bracketMin = null;
/*   11: 105 */   static int[] bracketOpt = null;
/*   12: 110 */   static Hashtable hashPOSIX = new Hashtable();
/*   13:     */   
/*   14:     */   static
/*   15:     */   {
/*   16: 113 */     hashPOSIX.put("alnum", new Character('w'));
/*   17: 114 */     hashPOSIX.put("alpha", new Character('a'));
/*   18: 115 */     hashPOSIX.put("blank", new Character('b'));
/*   19: 116 */     hashPOSIX.put("cntrl", new Character('c'));
/*   20: 117 */     hashPOSIX.put("digit", new Character('d'));
/*   21: 118 */     hashPOSIX.put("graph", new Character('g'));
/*   22: 119 */     hashPOSIX.put("lower", new Character('l'));
/*   23: 120 */     hashPOSIX.put("print", new Character('p'));
/*   24: 121 */     hashPOSIX.put("punct", new Character('!'));
/*   25: 122 */     hashPOSIX.put("space", new Character('s'));
/*   26: 123 */     hashPOSIX.put("upper", new Character('u'));
/*   27: 124 */     hashPOSIX.put("xdigit", new Character('x'));
/*   28: 125 */     hashPOSIX.put("javastart", new Character('j'));
/*   29: 126 */     hashPOSIX.put("javapart", new Character('k'));
/*   30:     */   }
/*   31:     */   
/*   32: 135 */   char[] instruction = new char['Â€'];
/*   33: 136 */   int lenInstruction = 0;
/*   34:     */   String pattern;
/*   35:     */   int len;
/*   36:     */   int idx;
/*   37:     */   int parens;
/*   38:     */   static final int NODE_NORMAL = 0;
/*   39:     */   static final int NODE_NULLABLE = 1;
/*   40:     */   static final int NODE_TOPLEVEL = 2;
/*   41:     */   static final char ESC_MASK = 'ï¿°';
/*   42:     */   static final char ESC_BACKREF = 'ð¿¿';
/*   43:     */   static final char ESC_COMPLEX = 'ï¿¾';
/*   44:     */   static final char ESC_CLASS = 'ï¿½';
/*   45:     */   static final int maxBrackets = 10;
/*   46:     */   static final int bracketUnbounded = -1;
/*   47:     */   static final int bracketFinished = -2;
/*   48:     */   
/*   49:     */   void ensure(int paramInt)
/*   50:     */   {
/*   51: 147 */     int i = this.instruction.length;
/*   52: 150 */     if (this.lenInstruction + paramInt >= i)
/*   53:     */     {
/*   54: 153 */       while (this.lenInstruction + paramInt >= i) {
/*   55: 155 */         i *= 2;
/*   56:     */       }
/*   57: 159 */       char[] arrayOfChar = new char[i];
/*   58: 160 */       System.arraycopy(this.instruction, 0, arrayOfChar, 0, this.lenInstruction);
/*   59: 161 */       this.instruction = arrayOfChar;
/*   60:     */     }
/*   61:     */   }
/*   62:     */   
/*   63:     */   void emit(char paramChar)
/*   64:     */   {
/*   65: 172 */     ensure(1);
/*   66:     */     
/*   67:     */ 
/*   68: 175 */     this.instruction[(this.lenInstruction++)] = paramChar;
/*   69:     */   }
/*   70:     */   
/*   71:     */   void nodeInsert(char paramChar, int paramInt1, int paramInt2)
/*   72:     */   {
/*   73: 188 */     ensure(3);
/*   74:     */     
/*   75:     */ 
/*   76: 191 */     System.arraycopy(this.instruction, paramInt2, this.instruction, paramInt2 + 3, this.lenInstruction - paramInt2);
/*   77: 192 */     this.instruction[paramInt2] = paramChar;
/*   78: 193 */     this.instruction[(paramInt2 + 1)] = ((char)paramInt1);
/*   79: 194 */     this.instruction[(paramInt2 + 2)] = '\000';
/*   80: 195 */     this.lenInstruction += 3;
/*   81:     */   }
/*   82:     */   
/*   83:     */   void setNextOfEnd(int paramInt1, int paramInt2)
/*   84:     */   {
/*   85:     */     int i;
/*   86: 207 */     while ((i = this.instruction[(paramInt1 + 2)]) != 0) {
/*   87: 209 */       paramInt1 += i;
/*   88:     */     }
/*   89: 213 */     this.instruction[(paramInt1 + 2)] = ((char)(short)(paramInt2 - paramInt1));
/*   90:     */   }
/*   91:     */   
/*   92:     */   int node(char paramChar, int paramInt)
/*   93:     */   {
/*   94: 225 */     ensure(3);
/*   95:     */     
/*   96:     */ 
/*   97: 228 */     this.instruction[this.lenInstruction] = paramChar;
/*   98: 229 */     this.instruction[(this.lenInstruction + 1)] = ((char)paramInt);
/*   99: 230 */     this.instruction[(this.lenInstruction + 2)] = '\000';
/*  100: 231 */     this.lenInstruction += 3;
/*  101:     */     
/*  102:     */ 
/*  103: 234 */     return this.lenInstruction - 3;
/*  104:     */   }
/*  105:     */   
/*  106:     */   void internalError()
/*  107:     */     throws Error
/*  108:     */   {
/*  109: 244 */     throw new Error("Internal error!");
/*  110:     */   }
/*  111:     */   
/*  112:     */   void syntaxError(String paramString)
/*  113:     */     throws RESyntaxException
/*  114:     */   {
/*  115: 253 */     throw new RESyntaxException(paramString);
/*  116:     */   }
/*  117:     */   
/*  118:     */   void allocBrackets()
/*  119:     */   {
/*  120: 262 */     if (bracketStart == null)
/*  121:     */     {
/*  122: 265 */       bracketStart = new int[10];
/*  123: 266 */       bracketEnd = new int[10];
/*  124: 267 */       bracketMin = new int[10];
/*  125: 268 */       bracketOpt = new int[10];
/*  126: 271 */       for (int i = 0; i < 10; i++)
/*  127:     */       {
/*  128: 273 */         byte tmp60_59 = (bracketMin[i] = bracketOpt[i] = -1);bracketEnd[i] = tmp60_59;bracketStart[i] = tmp60_59;
/*  129:     */       }
/*  130:     */     }
/*  131:     */   }
/*  132:     */   
/*  133:     */   void bracket()
/*  134:     */     throws RESyntaxException
/*  135:     */   {
/*  136: 285 */     if ((this.idx >= this.len) || (this.pattern.charAt(this.idx++) != '{')) {
/*  137: 287 */       internalError();
/*  138:     */     }
/*  139: 291 */     if ((this.idx >= this.len) || (!Character.isDigit(this.pattern.charAt(this.idx)))) {
/*  140: 293 */       syntaxError("Expected digit");
/*  141:     */     }
/*  142: 297 */     StringBuffer localStringBuffer = new StringBuffer();
/*  143: 298 */     while ((this.idx < this.len) && (Character.isDigit(this.pattern.charAt(this.idx)))) {
/*  144: 300 */       localStringBuffer.append(this.pattern.charAt(this.idx++));
/*  145:     */     }
/*  146:     */     try
/*  147:     */     {
/*  148: 304 */       bracketMin[brackets] = Integer.parseInt(localStringBuffer.toString());
/*  149:     */     }
/*  150:     */     catch (NumberFormatException localNumberFormatException1)
/*  151:     */     {
/*  152: 308 */       syntaxError("Expected valid number");
/*  153:     */     }
/*  154: 312 */     if (this.idx >= this.len) {
/*  155: 314 */       syntaxError("Expected comma or right bracket");
/*  156:     */     }
/*  157: 318 */     if (this.pattern.charAt(this.idx) == '}')
/*  158:     */     {
/*  159: 320 */       this.idx += 1;
/*  160: 321 */       bracketOpt[brackets] = 0;
/*  161: 322 */       return;
/*  162:     */     }
/*  163: 326 */     if ((this.idx >= this.len) || (this.pattern.charAt(this.idx++) != ',')) {
/*  164: 328 */       syntaxError("Expected comma");
/*  165:     */     }
/*  166: 332 */     if (this.idx >= this.len) {
/*  167: 334 */       syntaxError("Expected comma or right bracket");
/*  168:     */     }
/*  169: 338 */     if (this.pattern.charAt(this.idx) == '}')
/*  170:     */     {
/*  171: 340 */       this.idx += 1;
/*  172: 341 */       bracketOpt[brackets] = -1;
/*  173: 342 */       return;
/*  174:     */     }
/*  175: 346 */     if ((this.idx >= this.len) || (!Character.isDigit(this.pattern.charAt(this.idx)))) {
/*  176: 348 */       syntaxError("Expected digit");
/*  177:     */     }
/*  178: 352 */     localStringBuffer.setLength(0);
/*  179: 353 */     while ((this.idx < this.len) && (Character.isDigit(this.pattern.charAt(this.idx)))) {
/*  180: 355 */       localStringBuffer.append(this.pattern.charAt(this.idx++));
/*  181:     */     }
/*  182:     */     try
/*  183:     */     {
/*  184: 359 */       bracketOpt[brackets] = (Integer.parseInt(localStringBuffer.toString()) - bracketMin[brackets]);
/*  185:     */     }
/*  186:     */     catch (NumberFormatException localNumberFormatException2)
/*  187:     */     {
/*  188: 363 */       syntaxError("Expected valid number");
/*  189:     */     }
/*  190: 367 */     if (bracketOpt[brackets] <= 0) {
/*  191: 369 */       syntaxError("Bad range");
/*  192:     */     }
/*  193: 373 */     if ((this.idx >= this.len) || (this.pattern.charAt(this.idx++) != '}')) {
/*  194: 375 */       syntaxError("Missing close brace");
/*  195:     */     }
/*  196:     */   }
/*  197:     */   
/*  198:     */   char escape()
/*  199:     */     throws RESyntaxException
/*  200:     */   {
/*  201: 391 */     if (this.pattern.charAt(this.idx) != '\\') {
/*  202: 393 */       internalError();
/*  203:     */     }
/*  204: 397 */     if (this.idx + 1 == this.len) {
/*  205: 399 */       syntaxError("Escape terminates string");
/*  206:     */     }
/*  207: 403 */     this.idx += 2;
/*  208: 404 */     char c = this.pattern.charAt(this.idx - 1);
/*  209:     */     int i;
/*  210: 405 */     switch (c)
/*  211:     */     {
/*  212:     */     case 'B': 
/*  213:     */     case 'b': 
/*  214: 409 */       return 65534;
/*  215:     */     case 'D': 
/*  216:     */     case 'S': 
/*  217:     */     case 'W': 
/*  218:     */     case 'd': 
/*  219:     */     case 's': 
/*  220:     */     case 'w': 
/*  221: 417 */       return 65533;
/*  222:     */     case 'u': 
/*  223:     */     case 'x': 
/*  224: 423 */       i = c == 'u' ? 4 : 2;
/*  225:     */       
/*  226:     */ 
/*  227: 426 */       int j = 0;
/*  228: 427 */       for (; (this.idx < this.len) && (i-- > 0); this.idx += 1)
/*  229:     */       {
/*  230: 430 */         int k = this.pattern.charAt(this.idx);
/*  231: 433 */         if ((k >= 48) && (k <= 57))
/*  232:     */         {
/*  233: 436 */           j = (j << 4) + k - 48;
/*  234:     */         }
/*  235:     */         else
/*  236:     */         {
/*  237: 441 */           int m = Character.toLowerCase(k);
/*  238: 442 */           if ((m >= 97) && (m <= 102)) {
/*  239: 445 */             j = (j << 4) + (m - 97) + 10;
/*  240:     */           } else {
/*  241: 451 */             syntaxError("Expected " + i + " hexadecimal digits after \\" + c);
/*  242:     */           }
/*  243:     */         }
/*  244:     */       }
/*  245: 455 */       return (char)j;
/*  246:     */     case 't': 
/*  247: 459 */       return '\t';
/*  248:     */     case 'n': 
/*  249: 462 */       return '\n';
/*  250:     */     case 'r': 
/*  251: 465 */       return '\r';
/*  252:     */     case 'f': 
/*  253: 468 */       return '\f';
/*  254:     */     case '0': 
/*  255:     */     case '1': 
/*  256:     */     case '2': 
/*  257:     */     case '3': 
/*  258:     */     case '4': 
/*  259:     */     case '5': 
/*  260:     */     case '6': 
/*  261:     */     case '7': 
/*  262:     */     case '8': 
/*  263:     */     case '9': 
/*  264: 482 */       if (((this.idx < this.len) && (Character.isDigit(this.pattern.charAt(this.idx)))) || (c == '0'))
/*  265:     */       {
/*  266: 485 */         i = c - '0';
/*  267: 486 */         if ((this.idx < this.len) && (Character.isDigit(this.pattern.charAt(this.idx))))
/*  268:     */         {
/*  269: 488 */           i = (i << 3) + (this.pattern.charAt(this.idx++) - '0');
/*  270: 489 */           if ((this.idx < this.len) && (Character.isDigit(this.pattern.charAt(this.idx)))) {
/*  271: 491 */             i = (i << 3) + (this.pattern.charAt(this.idx++) - '0');
/*  272:     */           }
/*  273:     */         }
/*  274: 494 */         return (char)i;
/*  275:     */       }
/*  276: 498 */       return 65535;
/*  277:     */     }
/*  278: 503 */     return c;
/*  279:     */   }
/*  280:     */   
/*  281:     */   int characterClass()
/*  282:     */     throws RESyntaxException
/*  283:     */   {
/*  284: 515 */     if (this.pattern.charAt(this.idx) != '[') {
/*  285: 517 */       internalError();
/*  286:     */     }
/*  287: 521 */     if ((this.idx + 1 >= this.len) || (this.pattern.charAt(++this.idx) == ']')) {
/*  288: 523 */       syntaxError("Empty or unterminated class");
/*  289:     */     }
/*  290: 527 */     if ((this.idx < this.len) && (this.pattern.charAt(this.idx) == ':'))
/*  291:     */     {
/*  292: 530 */       this.idx += 1;
/*  293:     */       
/*  294:     */ 
/*  295: 533 */       i = this.idx;
/*  296: 534 */       while ((this.idx < this.len) && (this.pattern.charAt(this.idx) >= 'a') && (this.pattern.charAt(this.idx) <= 'z')) {
/*  297: 536 */         this.idx += 1;
/*  298:     */       }
/*  299: 540 */       if ((this.idx + 1 < this.len) && (this.pattern.charAt(this.idx) == ':') && (this.pattern.charAt(this.idx + 1) == ']'))
/*  300:     */       {
/*  301: 543 */         String str = this.pattern.substring(i, this.idx);
/*  302:     */         
/*  303:     */ 
/*  304: 546 */         localCharacter2 = (Character)hashPOSIX.get(str);
/*  305: 547 */         if (localCharacter2 != null)
/*  306:     */         {
/*  307: 550 */           this.idx += 2;
/*  308:     */           
/*  309:     */ 
/*  310: 553 */           return node('P', localCharacter2.charValue());
/*  311:     */         }
/*  312: 555 */         syntaxError("Invalid POSIX character class '" + str + "'");
/*  313:     */       }
/*  314: 557 */       syntaxError("Invalid POSIX character class syntax");
/*  315:     */     }
/*  316: 561 */     int i = node('[', 0);
/*  317:     */     
/*  318:     */ 
/*  319: 564 */     Character localCharacter1 = 65535;
/*  320: 565 */     Character localCharacter2 = localCharacter1;
/*  321: 566 */     int k = 0;
/*  322: 567 */     boolean bool = true;
/*  323: 568 */     int m = 0;
/*  324: 569 */     int n = this.idx;
/*  325: 570 */     int i1 = 0;
/*  326:     */     
/*  327: 572 */     RERange localRERange = new RERange();
/*  328: 573 */     while ((this.idx < this.len) && (this.pattern.charAt(this.idx) != ']'))
/*  329:     */     {
/*  330:     */       int i2;
/*  331: 579 */       switch (this.pattern.charAt(this.idx))
/*  332:     */       {
/*  333:     */       case '^': 
/*  334: 582 */         bool ^= true;
/*  335: 583 */         if (this.idx == n) {
/*  336: 585 */           localRERange.include(0, 65535, true);
/*  337:     */         }
/*  338: 587 */         this.idx += 1;
/*  339: 588 */         break;
/*  340:     */       case '\\': 
/*  341: 594 */         switch (i4 = escape())
/*  342:     */         {
/*  343:     */         case 'ï¿¾': 
/*  344:     */         case 'ð¿¿': 
/*  345: 600 */           syntaxError("Bad character class");
/*  346:     */         case 'ï¿½': 
/*  347: 605 */           if (m != 0) {
/*  348: 607 */             syntaxError("Bad character class");
/*  349:     */           }
/*  350: 611 */           switch (this.pattern.charAt(this.idx - 1))
/*  351:     */           {
/*  352:     */           case 'D': 
/*  353:     */           case 'S': 
/*  354:     */           case 'W': 
/*  355: 616 */             syntaxError("Bad character class");
/*  356:     */           case 's': 
/*  357: 619 */             localRERange.include('\t', bool);
/*  358: 620 */             localRERange.include('\r', bool);
/*  359: 621 */             localRERange.include('\f', bool);
/*  360: 622 */             localRERange.include('\n', bool);
/*  361: 623 */             localRERange.include('\b', bool);
/*  362: 624 */             localRERange.include(' ', bool);
/*  363: 625 */             break;
/*  364:     */           case 'w': 
/*  365: 628 */             localRERange.include(97, 122, bool);
/*  366: 629 */             localRERange.include(65, 90, bool);
/*  367: 630 */             localRERange.include('_', bool);
/*  368:     */           case 'd': 
/*  369: 635 */             localRERange.include(48, 57, bool);
/*  370: 636 */             break;
/*  371:     */           }
/*  372: 640 */           localCharacter2 = localCharacter1;
/*  373: 641 */           break;
/*  374:     */         default: 
/*  375: 646 */           k = i4;
/*  376:     */         }
/*  377: 647 */         break;
/*  378:     */       case '-': 
/*  379: 655 */         if (m != 0) {
/*  380: 657 */           syntaxError("Bad class range");
/*  381:     */         }
/*  382: 659 */         m = 1;
/*  383:     */         
/*  384:     */ 
/*  385: 662 */         i2 = localCharacter2 == localCharacter1 ? 0 : localCharacter2;
/*  386: 665 */         if ((this.idx + 1 >= this.len) || (this.pattern.charAt(++this.idx) != ']')) {
/*  387:     */           continue;
/*  388:     */         }
/*  389: 667 */         k = 65535;
/*  390: 668 */         break;
/*  391:     */       default: 
/*  392: 673 */         k = this.pattern.charAt(this.idx++);
/*  393:     */       }
/*  394:     */       int j;
/*  395: 678 */       if (m != 0)
/*  396:     */       {
/*  397: 681 */         int i3 = k;
/*  398: 684 */         if (i2 >= i3) {
/*  399: 686 */           syntaxError("Bad character class");
/*  400:     */         }
/*  401: 688 */         localRERange.include(i2, i3, bool);
/*  402:     */         
/*  403:     */ 
/*  404: 691 */         j = localCharacter1;
/*  405: 692 */         m = 0;
/*  406:     */       }
/*  407:     */       else
/*  408:     */       {
/*  409: 697 */         if ((this.idx + 1 >= this.len) || (this.pattern.charAt(this.idx + 1) != '-')) {
/*  410: 699 */           localRERange.include(k, bool);
/*  411:     */         }
/*  412: 701 */         j = k;
/*  413:     */       }
/*  414:     */     }
/*  415: 706 */     if (this.idx == this.len) {
/*  416: 708 */       syntaxError("Unterminated character class");
/*  417:     */     }
/*  418: 712 */     this.idx += 1;
/*  419:     */     
/*  420:     */ 
/*  421: 715 */     this.instruction[(i + 1)] = ((char)localRERange.num);
/*  422: 716 */     for (int i4 = 0; i4 < localRERange.num; i4++)
/*  423:     */     {
/*  424: 718 */       emit((char)localRERange.minRange[i4]);
/*  425: 719 */       emit((char)localRERange.maxRange[i4]);
/*  426:     */     }
/*  427: 721 */     return i;
/*  428:     */   }
/*  429:     */   
/*  430:     */   int atom()
/*  431:     */     throws RESyntaxException
/*  432:     */   {
/*  433: 735 */     int i = node('A', 0);
/*  434:     */     
/*  435:     */ 
/*  436: 738 */     int j = 0;
/*  437: 744 */     while (this.idx < this.len)
/*  438:     */     {
/*  439:     */       int k;
/*  440:     */       int m;
/*  441: 747 */       if (this.idx + 1 < this.len)
/*  442:     */       {
/*  443: 749 */         k = this.pattern.charAt(this.idx + 1);
/*  444: 752 */         if (this.pattern.charAt(this.idx) == '\\')
/*  445:     */         {
/*  446: 754 */           m = this.idx;
/*  447: 755 */           escape();
/*  448: 756 */           if (this.idx < this.len) {
/*  449: 758 */             k = this.pattern.charAt(this.idx);
/*  450:     */           }
/*  451: 760 */           this.idx = m;
/*  452:     */         }
/*  453: 764 */         switch (k)
/*  454:     */         {
/*  455:     */         case 42: 
/*  456:     */         case 43: 
/*  457:     */         case 63: 
/*  458:     */         case 123: 
/*  459: 773 */           if (j != 0) {
/*  460:     */             break;
/*  461:     */           }
/*  462:     */         }
/*  463:     */       }
/*  464: 781 */       switch (this.pattern.charAt(this.idx))
/*  465:     */       {
/*  466:     */       case '*': 
/*  467:     */       case '+': 
/*  468:     */       case '?': 
/*  469:     */       case '{': 
/*  470: 799 */         if (j != 0) {
/*  471:     */           break label365;
/*  472:     */         }
/*  473: 802 */         syntaxError("Missing operand to closure");
/*  474:     */         
/*  475: 804 */         break;
/*  476:     */       case '\\': 
/*  477: 810 */         k = this.idx;
/*  478: 811 */         m = escape();
/*  479: 814 */         if ((m & 0xFFF0) == 65520)
/*  480:     */         {
/*  481: 817 */           this.idx = k;
/*  482:     */           break label365;
/*  483:     */         }
/*  484: 822 */         emit(m);
/*  485: 823 */         j++;
/*  486:     */         
/*  487: 825 */         break;
/*  488:     */       default: 
/*  489: 830 */         emit(this.pattern.charAt(this.idx++));
/*  490: 831 */         j++;
/*  491:     */       }
/*  492:     */     }
/*  493:     */     label365:
/*  494: 837 */     if (j == 0) {
/*  495: 839 */       internalError();
/*  496:     */     }
/*  497: 843 */     this.instruction[(i + 1)] = ((char)j);
/*  498: 844 */     return i;
/*  499:     */   }
/*  500:     */   
/*  501:     */   int terminal(int[] paramArrayOfInt)
/*  502:     */     throws RESyntaxException
/*  503:     */   {
/*  504: 855 */     switch (this.pattern.charAt(this.idx))
/*  505:     */     {
/*  506:     */     case '$': 
/*  507:     */     case '.': 
/*  508:     */     case '^': 
/*  509: 860 */       return node(this.pattern.charAt(this.idx++), 0);
/*  510:     */     case '[': 
/*  511: 863 */       return characterClass();
/*  512:     */     case '(': 
/*  513: 866 */       return expr(paramArrayOfInt);
/*  514:     */     case ')': 
/*  515: 869 */       syntaxError("Unexpected close paren");
/*  516:     */     case '|': 
/*  517: 872 */       internalError();
/*  518:     */     case ']': 
/*  519: 875 */       syntaxError("Mismatched class");
/*  520:     */     case '\000': 
/*  521: 878 */       syntaxError("Unexpected end of input");
/*  522:     */     case '*': 
/*  523:     */     case '+': 
/*  524:     */     case '?': 
/*  525:     */     case '{': 
/*  526: 884 */       syntaxError("Missing operand to closure");
/*  527:     */     case '\\': 
/*  528: 889 */       int i = this.idx;
/*  529: 892 */       switch (escape())
/*  530:     */       {
/*  531:     */       case 'ï¿½': 
/*  532:     */       case 'ï¿¾': 
/*  533: 896 */         paramArrayOfInt[0] &= 0xFFFFFFFE;
/*  534: 897 */         return node('\\', this.pattern.charAt(this.idx - 1));
/*  535:     */       case 'ð¿¿': 
/*  536: 901 */         int j = (char)(this.pattern.charAt(this.idx - 1) - '0');
/*  537: 902 */         if (this.parens <= j) {
/*  538: 904 */           syntaxError("Bad backreference");
/*  539:     */         }
/*  540: 906 */         paramArrayOfInt[0] |= 0x1;
/*  541: 907 */         return node('#', j);
/*  542:     */       }
/*  543: 914 */       this.idx = i;
/*  544: 915 */       paramArrayOfInt[0] &= 0xFFFFFFFE;
/*  545: 916 */       break;
/*  546:     */     }
/*  547: 923 */     paramArrayOfInt[0] &= 0xFFFFFFFE;
/*  548: 924 */     return atom();
/*  549:     */   }
/*  550:     */   
/*  551:     */   int closure(int[] paramArrayOfInt)
/*  552:     */     throws RESyntaxException
/*  553:     */   {
/*  554: 936 */     int i = this.idx;
/*  555:     */     
/*  556:     */ 
/*  557: 939 */     int[] arrayOfInt = new int[1];
/*  558:     */     
/*  559:     */ 
/*  560: 942 */     int j = terminal(arrayOfInt);
/*  561:     */     
/*  562:     */ 
/*  563: 945 */     paramArrayOfInt[0] |= arrayOfInt[0];
/*  564: 948 */     if (this.idx >= this.len) {
/*  565: 950 */       return j;
/*  566:     */     }
/*  567: 952 */     int k = 1;
/*  568: 953 */     int m = this.pattern.charAt(this.idx);
/*  569:     */     int n;
/*  570: 954 */     switch (m)
/*  571:     */     {
/*  572:     */     case 42: 
/*  573:     */     case 63: 
/*  574: 960 */       paramArrayOfInt[0] |= 0x1;
/*  575:     */     case 43: 
/*  576: 965 */       this.idx += 1;
/*  577:     */     case 123: 
/*  578: 970 */       n = this.instruction[j];
/*  579: 971 */       if ((n == 94) || (n == 36)) {
/*  580: 973 */         syntaxError("Bad closure operand");
/*  581:     */       }
/*  582: 975 */       if ((arrayOfInt[0] & 0x1) != 0) {
/*  583: 977 */         syntaxError("Closure operand can't be nullable");
/*  584:     */       }
/*  585: 979 */       break;
/*  586:     */     }
/*  587: 983 */     if ((this.idx < this.len) && (this.pattern.charAt(this.idx) == '?'))
/*  588:     */     {
/*  589: 985 */       this.idx += 1;
/*  590: 986 */       k = 0;
/*  591:     */     }
/*  592: 989 */     if (k != 0) {}
/*  593: 992 */     switch (m)
/*  594:     */     {
/*  595:     */     case 123: 
/*  596: 997 */       n = 0;
/*  597:     */       
/*  598: 999 */       allocBrackets();
/*  599:1000 */       for (int i1 = 0; i1 < brackets; i1++) {
/*  600:1002 */         if (bracketStart[i1] == this.idx)
/*  601:     */         {
/*  602:1004 */           n = 1;
/*  603:1005 */           break;
/*  604:     */         }
/*  605:     */       }
/*  606:1010 */       if (n == 0)
/*  607:     */       {
/*  608:1012 */         if (brackets >= 10) {
/*  609:1014 */           syntaxError("Too many bracketed closures (limit is 10)");
/*  610:     */         }
/*  611:1016 */         bracketStart[brackets] = this.idx;
/*  612:1017 */         bracket();
/*  613:1018 */         bracketEnd[brackets] = this.idx;
/*  614:1019 */         i1 = brackets++;
/*  615:     */       }
/*  616:1023 */       if (bracketMin[i1] -= 1 > 0)
/*  617:     */       {
/*  618:1026 */         this.idx = i;
/*  619:     */       }
/*  620:1031 */       else if (bracketOpt[i1] == -2)
/*  621:     */       {
/*  622:1035 */         m = 42;
/*  623:1036 */         bracketOpt[i1] = 0;
/*  624:1037 */         this.idx = bracketEnd[i1];
/*  625:     */       }
/*  626:1040 */       else if (bracketOpt[i1] == -1)
/*  627:     */       {
/*  628:1042 */         this.idx = i;
/*  629:1043 */         bracketOpt[i1] = -2;
/*  630:     */       }
/*  631:     */       else
/*  632:     */       {
/*  633:1047 */         int tmp439_437 = i1; int[] tmp439_434 = bracketOpt; int tmp441_440 = tmp439_434[tmp439_437];tmp439_434[tmp439_437] = (tmp441_440 - 1);
/*  634:1047 */         if (tmp441_440 > 0)
/*  635:     */         {
/*  636:1050 */           this.idx = i;
/*  637:1051 */           m = 63;
/*  638:     */         }
/*  639:     */         else
/*  640:     */         {
/*  641:1056 */           this.idx = bracketEnd[i1];
/*  642:     */         }
/*  643:     */       }
/*  644:1057 */       break;
/*  645:     */     case 42: 
/*  646:     */     case 63: 
/*  647:1066 */       if (k != 0)
/*  648:     */       {
/*  649:1071 */         if (m == 63)
/*  650:     */         {
/*  651:1074 */           nodeInsert('|', 0, j);
/*  652:1075 */           setNextOfEnd(j, node('|', 0));
/*  653:1076 */           n = node('N', 0);
/*  654:1077 */           setNextOfEnd(j, n);
/*  655:1078 */           setNextOfEnd(j + 3, n);
/*  656:     */         }
/*  657:1081 */         if (m != 42) {
/*  658:     */           break label778;
/*  659:     */         }
/*  660:1084 */         nodeInsert('|', 0, j);
/*  661:1085 */         setNextOfEnd(j + 3, node('|', 0));
/*  662:1086 */         setNextOfEnd(j + 3, node('G', 0));
/*  663:1087 */         setNextOfEnd(j + 3, j);
/*  664:1088 */         setNextOfEnd(j, node('|', 0));
/*  665:1089 */         setNextOfEnd(j, node('N', 0));
/*  666:     */       }
/*  667:1091 */       break;
/*  668:     */     case 43: 
/*  669:1097 */       n = node('|', 0);
/*  670:1098 */       setNextOfEnd(j, n);
/*  671:1099 */       setNextOfEnd(node('G', 0), j);
/*  672:1100 */       setNextOfEnd(n, node('|', 0));
/*  673:1101 */       setNextOfEnd(j, node('N', 0));
/*  674:     */       break label778;
/*  675:     */       break label778;
/*  676:1109 */       setNextOfEnd(j, node('E', 0));
/*  677:1112 */       switch (m)
/*  678:     */       {
/*  679:     */       case 63: 
/*  680:1115 */         nodeInsert('/', 0, j);
/*  681:1116 */         break;
/*  682:     */       case 42: 
/*  683:1119 */         nodeInsert('8', 0, j);
/*  684:1120 */         break;
/*  685:     */       case 43: 
/*  686:1123 */         nodeInsert('=', 0, j);
/*  687:1124 */         break;
/*  688:     */       }
/*  689:1128 */       setNextOfEnd(j, this.lenInstruction);
/*  690:     */     }
/*  691:     */     label778:
/*  692:1130 */     return j;
/*  693:     */   }
/*  694:     */   
/*  695:     */   int branch(int[] paramArrayOfInt)
/*  696:     */     throws RESyntaxException
/*  697:     */   {
/*  698:1143 */     int j = node('|', 0);
/*  699:1144 */     int k = -1;
/*  700:1145 */     int[] arrayOfInt = new int[1];
/*  701:1146 */     int m = 1;
/*  702:1147 */     while ((this.idx < this.len) && (this.pattern.charAt(this.idx) != '|') && (this.pattern.charAt(this.idx) != ')'))
/*  703:     */     {
/*  704:1150 */       arrayOfInt[0] = 0;
/*  705:1151 */       int i = closure(arrayOfInt);
/*  706:1152 */       if (arrayOfInt[0] == 0) {
/*  707:1154 */         m = 0;
/*  708:     */       }
/*  709:1158 */       if (k != -1) {
/*  710:1160 */         setNextOfEnd(k, i);
/*  711:     */       }
/*  712:1164 */       k = i;
/*  713:     */     }
/*  714:1168 */     if (k == -1) {
/*  715:1170 */       node('N', 0);
/*  716:     */     }
/*  717:1174 */     if (m != 0) {
/*  718:1176 */       paramArrayOfInt[0] |= 0x1;
/*  719:     */     }
/*  720:1178 */     return j;
/*  721:     */   }
/*  722:     */   
/*  723:     */   int expr(int[] paramArrayOfInt)
/*  724:     */     throws RESyntaxException
/*  725:     */   {
/*  726:1191 */     int i = 0;
/*  727:1192 */     int j = -1;
/*  728:1193 */     int k = this.parens;
/*  729:1194 */     if (((paramArrayOfInt[0] & 0x2) == 0) && (this.pattern.charAt(this.idx) == '('))
/*  730:     */     {
/*  731:1196 */       this.idx += 1;
/*  732:1197 */       i = 1;
/*  733:1198 */       j = node('(', this.parens++);
/*  734:     */     }
/*  735:1200 */     paramArrayOfInt[0] &= 0xFFFFFFFD;
/*  736:     */     
/*  737:     */ 
/*  738:1203 */     int m = branch(paramArrayOfInt);
/*  739:1204 */     if (j == -1) {
/*  740:1206 */       j = m;
/*  741:     */     } else {
/*  742:1210 */       setNextOfEnd(j, m);
/*  743:     */     }
/*  744:1214 */     while ((this.idx < this.len) && (this.pattern.charAt(this.idx) == '|'))
/*  745:     */     {
/*  746:1216 */       this.idx += 1;
/*  747:1217 */       m = branch(paramArrayOfInt);
/*  748:1218 */       setNextOfEnd(j, m);
/*  749:     */     }
/*  750:     */     int n;
/*  751:1223 */     if (i != 0)
/*  752:     */     {
/*  753:1225 */       if ((this.idx < this.len) && (this.pattern.charAt(this.idx) == ')')) {
/*  754:1227 */         this.idx += 1;
/*  755:     */       } else {
/*  756:1231 */         syntaxError("Missing close paren");
/*  757:     */       }
/*  758:1233 */       n = node(')', k);
/*  759:     */     }
/*  760:     */     else
/*  761:     */     {
/*  762:1237 */       n = node('E', 0);
/*  763:     */     }
/*  764:1241 */     setNextOfEnd(j, n);
/*  765:     */     
/*  766:     */ 
/*  767:1244 */     int i1 = -1;
/*  768:1244 */     for (int i2 = j; i1 != 0; i2 += i1)
/*  769:     */     {
/*  770:1247 */       if (this.instruction[i2] == '|') {
/*  771:1249 */         setNextOfEnd(i2 + 3, n);
/*  772:     */       }
/*  773:1244 */       i1 = this.instruction[(i2 + 2)];
/*  774:     */     }
/*  775:1254 */     return j;
/*  776:     */   }
/*  777:     */   
/*  778:     */   public REProgram compile(String paramString)
/*  779:     */     throws RESyntaxException
/*  780:     */   {
/*  781:1270 */     this.pattern = paramString;
/*  782:1271 */     this.len = paramString.length();
/*  783:1272 */     this.idx = 0;
/*  784:1273 */     this.lenInstruction = 0;
/*  785:1274 */     this.parens = 1;
/*  786:1275 */     brackets = 0;
/*  787:     */     
/*  788:     */ 
/*  789:1278 */     int[] arrayOfInt = { 2 };
/*  790:     */     
/*  791:     */ 
/*  792:1281 */     expr(arrayOfInt);
/*  793:1284 */     if (this.idx != this.len)
/*  794:     */     {
/*  795:1286 */       if (paramString.charAt(this.idx) == ')') {
/*  796:1288 */         syntaxError("Unmatched close paren");
/*  797:     */       }
/*  798:1290 */       syntaxError("Unexpected input remains");
/*  799:     */     }
/*  800:1294 */     char[] arrayOfChar = new char[this.lenInstruction];
/*  801:1295 */     System.arraycopy(this.instruction, 0, arrayOfChar, 0, this.lenInstruction);
/*  802:1296 */     return new REProgram(arrayOfChar);
/*  803:     */   }
/*  804:     */   
/*  805:     */   class RERange
/*  806:     */   {
/*  807:1304 */     int size = 16;
/*  808:1305 */     int[] minRange = new int[this.size];
/*  809:1306 */     int[] maxRange = new int[this.size];
/*  810:1307 */     int num = 0;
/*  811:     */     
/*  812:     */     void delete(int paramInt)
/*  813:     */     {
/*  814:1316 */       if ((this.num == 0) || (paramInt >= this.num)) {
/*  815:1318 */         return;
/*  816:     */       }
/*  817:1322 */       while (paramInt++ < this.num) {
/*  818:1324 */         if (paramInt - 1 >= 0)
/*  819:     */         {
/*  820:1326 */           this.minRange[(paramInt - 1)] = this.minRange[paramInt];
/*  821:1327 */           this.maxRange[(paramInt - 1)] = this.maxRange[paramInt];
/*  822:     */         }
/*  823:     */       }
/*  824:1332 */       this.num -= 1;
/*  825:     */     }
/*  826:     */     
/*  827:     */     void merge(int paramInt1, int paramInt2)
/*  828:     */     {
/*  829:1343 */       for (int i = 0; i < this.num; i++)
/*  830:     */       {
/*  831:1346 */         if ((paramInt1 >= this.minRange[i]) && (paramInt2 <= this.maxRange[i])) {
/*  832:1348 */           return;
/*  833:     */         }
/*  834:1352 */         if ((paramInt1 <= this.minRange[i]) && (paramInt2 >= this.maxRange[i]))
/*  835:     */         {
/*  836:1354 */           delete(i);
/*  837:1355 */           merge(paramInt1, paramInt2);
/*  838:1356 */           return;
/*  839:     */         }
/*  840:1360 */         if ((paramInt1 >= this.minRange[i]) && (paramInt1 <= this.maxRange[i]))
/*  841:     */         {
/*  842:1362 */           delete(i);
/*  843:1363 */           paramInt1 = this.minRange[i];
/*  844:1364 */           merge(paramInt1, paramInt2);
/*  845:1365 */           return;
/*  846:     */         }
/*  847:1369 */         if ((paramInt2 >= this.minRange[i]) && (paramInt2 <= this.maxRange[i]))
/*  848:     */         {
/*  849:1371 */           delete(i);
/*  850:1372 */           paramInt2 = this.maxRange[i];
/*  851:1373 */           merge(paramInt1, paramInt2);
/*  852:1374 */           return;
/*  853:     */         }
/*  854:     */       }
/*  855:1379 */       if (this.num >= this.size)
/*  856:     */       {
/*  857:1381 */         this.size *= 2;
/*  858:1382 */         int[] arrayOfInt1 = new int[this.size];
/*  859:1383 */         int[] arrayOfInt2 = new int[this.size];
/*  860:1384 */         System.arraycopy(this.minRange, 0, arrayOfInt1, 0, this.num);
/*  861:1385 */         System.arraycopy(this.maxRange, 0, arrayOfInt2, 0, this.num);
/*  862:1386 */         this.minRange = arrayOfInt1;
/*  863:1387 */         this.maxRange = arrayOfInt2;
/*  864:     */       }
/*  865:1389 */       this.minRange[this.num] = paramInt1;
/*  866:1390 */       this.maxRange[this.num] = paramInt2;
/*  867:1391 */       this.num += 1;
/*  868:     */     }
/*  869:     */     
/*  870:     */     void remove(int paramInt1, int paramInt2)
/*  871:     */     {
/*  872:1402 */       for (int i = 0; i < this.num; i++)
/*  873:     */       {
/*  874:1405 */         if ((this.minRange[i] >= paramInt1) && (this.maxRange[i] <= paramInt2))
/*  875:     */         {
/*  876:1407 */           delete(i);
/*  877:1408 */           i--;
/*  878:1409 */           return;
/*  879:     */         }
/*  880:1413 */         if ((paramInt1 >= this.minRange[i]) && (paramInt2 <= this.maxRange[i]))
/*  881:     */         {
/*  882:1415 */           int j = this.minRange[i];
/*  883:1416 */           int k = this.maxRange[i];
/*  884:1417 */           delete(i);
/*  885:1418 */           if (j < paramInt1 - 1) {
/*  886:1420 */             merge(j, paramInt1 - 1);
/*  887:     */           }
/*  888:1422 */           if (paramInt2 + 1 < k) {
/*  889:1424 */             merge(paramInt2 + 1, k);
/*  890:     */           }
/*  891:1426 */           return;
/*  892:     */         }
/*  893:1430 */         if ((this.minRange[i] >= paramInt1) && (this.minRange[i] <= paramInt2))
/*  894:     */         {
/*  895:1432 */           this.minRange[i] = (paramInt2 + 1);
/*  896:1433 */           return;
/*  897:     */         }
/*  898:1437 */         if ((this.maxRange[i] >= paramInt1) && (this.maxRange[i] <= paramInt2))
/*  899:     */         {
/*  900:1439 */           this.maxRange[i] = (paramInt1 - 1); return;
/*  901:     */         }
/*  902:     */       }
/*  903:     */     }
/*  904:     */     
/*  905:     */     void include(int paramInt1, int paramInt2, boolean paramBoolean)
/*  906:     */     {
/*  907:1453 */       if (paramBoolean) {
/*  908:1455 */         merge(paramInt1, paramInt2);
/*  909:     */       } else {
/*  910:1459 */         remove(paramInt1, paramInt2);
/*  911:     */       }
/*  912:     */     }
/*  913:     */     
/*  914:     */     void include(char paramChar, boolean paramBoolean)
/*  915:     */     {
/*  916:1470 */       include(paramChar, paramChar, paramBoolean);
/*  917:     */     }
/*  918:     */     
/*  919:     */     RERange() {}
/*  920:     */   }
/*  921:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.regexp.RECompiler
 * JD-Core Version:    0.7.0.1
 */