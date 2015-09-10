/*    1:     */ package net.sourceforge.htmlunit.corejs.javascript;
/*    2:     */ 
/*    3:     */ import java.math.BigInteger;
/*    4:     */ 
/*    5:     */ class DToA
/*    6:     */ {
/*    7:     */   private static final int DTOBASESTR_BUFFER_SIZE = 1078;
/*    8:     */   static final int DTOSTR_STANDARD = 0;
/*    9:     */   static final int DTOSTR_STANDARD_EXPONENTIAL = 1;
/*   10:     */   static final int DTOSTR_FIXED = 2;
/*   11:     */   static final int DTOSTR_EXPONENTIAL = 3;
/*   12:     */   static final int DTOSTR_PRECISION = 4;
/*   13:     */   private static final int Frac_mask = 1048575;
/*   14:     */   private static final int Exp_shift = 20;
/*   15:     */   private static final int Exp_msk1 = 1048576;
/*   16:     */   private static final long Frac_maskL = 4503599627370495L;
/*   17:     */   private static final int Exp_shiftL = 52;
/*   18:     */   private static final long Exp_msk1L = 4503599627370496L;
/*   19:     */   private static final int Bias = 1023;
/*   20:     */   private static final int P = 53;
/*   21:     */   private static final int Exp_shift1 = 20;
/*   22:     */   private static final int Exp_mask = 2146435072;
/*   23:     */   private static final int Exp_mask_shifted = 2047;
/*   24:     */   private static final int Bndry_mask = 1048575;
/*   25:     */   private static final int Log2P = 1;
/*   26:     */   private static final int Sign_bit = -2147483648;
/*   27:     */   private static final int Exp_11 = 1072693248;
/*   28:     */   private static final int Ten_pmax = 22;
/*   29:     */   private static final int Quick_max = 14;
/*   30:     */   private static final int Bletch = 16;
/*   31:     */   private static final int Frac_mask1 = 1048575;
/*   32:     */   private static final int Int_max = 14;
/*   33:     */   private static final int n_bigtens = 5;
/*   34:     */   
/*   35:     */   private static char BASEDIGIT(int digit)
/*   36:     */   {
/*   37:  74 */     return (char)(digit >= 10 ? 87 + digit : 48 + digit);
/*   38:     */   }
/*   39:     */   
/*   40: 112 */   private static final double[] tens = { 1.0D, 10.0D, 100.0D, 1000.0D, 10000.0D, 100000.0D, 1000000.0D, 10000000.0D, 100000000.0D, 1000000000.0D, 10000000000.0D, 100000000000.0D, 1000000000000.0D, 10000000000000.0D, 100000000000000.0D, 1000000000000000.0D, 10000000000000000.0D, 1.0E+017D, 1.0E+018D, 1.0E+019D, 1.0E+020D, 1.0E+021D, 1.0E+022D };
/*   41: 118 */   private static final double[] bigtens = { 10000000000000000.0D, 1.E+032D, 1.0E+064D, 1.E+128D, 1.0E+256D };
/*   42:     */   
/*   43:     */   private static int lo0bits(int y)
/*   44:     */   {
/*   45: 123 */     int x = y;
/*   46: 125 */     if ((x & 0x7) != 0)
/*   47:     */     {
/*   48: 126 */       if ((x & 0x1) != 0) {
/*   49: 127 */         return 0;
/*   50:     */       }
/*   51: 128 */       if ((x & 0x2) != 0) {
/*   52: 129 */         return 1;
/*   53:     */       }
/*   54: 131 */       return 2;
/*   55:     */     }
/*   56: 133 */     int k = 0;
/*   57: 134 */     if ((x & 0xFFFF) == 0)
/*   58:     */     {
/*   59: 135 */       k = 16;
/*   60: 136 */       x >>>= 16;
/*   61:     */     }
/*   62: 138 */     if ((x & 0xFF) == 0)
/*   63:     */     {
/*   64: 139 */       k += 8;
/*   65: 140 */       x >>>= 8;
/*   66:     */     }
/*   67: 142 */     if ((x & 0xF) == 0)
/*   68:     */     {
/*   69: 143 */       k += 4;
/*   70: 144 */       x >>>= 4;
/*   71:     */     }
/*   72: 146 */     if ((x & 0x3) == 0)
/*   73:     */     {
/*   74: 147 */       k += 2;
/*   75: 148 */       x >>>= 2;
/*   76:     */     }
/*   77: 150 */     if ((x & 0x1) == 0)
/*   78:     */     {
/*   79: 151 */       k++;
/*   80: 152 */       x >>>= 1;
/*   81: 153 */       if ((x & 0x1) == 0) {
/*   82: 154 */         return 32;
/*   83:     */       }
/*   84:     */     }
/*   85: 156 */     return k;
/*   86:     */   }
/*   87:     */   
/*   88:     */   private static int hi0bits(int x)
/*   89:     */   {
/*   90: 162 */     int k = 0;
/*   91: 164 */     if ((x & 0xFFFF0000) == 0)
/*   92:     */     {
/*   93: 165 */       k = 16;
/*   94: 166 */       x <<= 16;
/*   95:     */     }
/*   96: 168 */     if ((x & 0xFF000000) == 0)
/*   97:     */     {
/*   98: 169 */       k += 8;
/*   99: 170 */       x <<= 8;
/*  100:     */     }
/*  101: 172 */     if ((x & 0xF0000000) == 0)
/*  102:     */     {
/*  103: 173 */       k += 4;
/*  104: 174 */       x <<= 4;
/*  105:     */     }
/*  106: 176 */     if ((x & 0xC0000000) == 0)
/*  107:     */     {
/*  108: 177 */       k += 2;
/*  109: 178 */       x <<= 2;
/*  110:     */     }
/*  111: 180 */     if ((x & 0x80000000) == 0)
/*  112:     */     {
/*  113: 181 */       k++;
/*  114: 182 */       if ((x & 0x40000000) == 0) {
/*  115: 183 */         return 32;
/*  116:     */       }
/*  117:     */     }
/*  118: 185 */     return k;
/*  119:     */   }
/*  120:     */   
/*  121:     */   private static void stuffBits(byte[] bits, int offset, int val)
/*  122:     */   {
/*  123: 190 */     bits[offset] = ((byte)(val >> 24));
/*  124: 191 */     bits[(offset + 1)] = ((byte)(val >> 16));
/*  125: 192 */     bits[(offset + 2)] = ((byte)(val >> 8));
/*  126: 193 */     bits[(offset + 3)] = ((byte)val);
/*  127:     */   }
/*  128:     */   
/*  129:     */   private static BigInteger d2b(double d, int[] e, int[] bits)
/*  130:     */   {
/*  131: 203 */     long dBits = Double.doubleToLongBits(d);
/*  132: 204 */     int d0 = (int)(dBits >>> 32);
/*  133: 205 */     int d1 = (int)dBits;
/*  134:     */     
/*  135: 207 */     int z = d0 & 0xFFFFF;
/*  136: 208 */     d0 &= 0x7FFFFFFF;
/*  137:     */     int de;
/*  138: 210 */     if ((de = d0 >>> 20) != 0) {
/*  139: 211 */       z |= 0x100000;
/*  140:     */     }
/*  141:     */     int y;
/*  142:     */     int i;
/*  143:     */     byte[] dbl_bits;
/*  144:     */     int k;
/*  145:     */     int i;
/*  146: 213 */     if ((y = d1) != 0)
/*  147:     */     {
/*  148: 214 */       byte[] dbl_bits = new byte[8];
/*  149: 215 */       int k = lo0bits(y);
/*  150: 216 */       y >>>= k;
/*  151: 217 */       if (k != 0)
/*  152:     */       {
/*  153: 218 */         stuffBits(dbl_bits, 4, y | z << 32 - k);
/*  154: 219 */         z >>= k;
/*  155:     */       }
/*  156:     */       else
/*  157:     */       {
/*  158: 222 */         stuffBits(dbl_bits, 4, y);
/*  159:     */       }
/*  160: 223 */       stuffBits(dbl_bits, 0, z);
/*  161: 224 */       i = z != 0 ? 2 : 1;
/*  162:     */     }
/*  163:     */     else
/*  164:     */     {
/*  165: 228 */       dbl_bits = new byte[4];
/*  166: 229 */       k = lo0bits(z);
/*  167: 230 */       z >>>= k;
/*  168: 231 */       stuffBits(dbl_bits, 0, z);
/*  169: 232 */       k += 32;
/*  170: 233 */       i = 1;
/*  171:     */     }
/*  172: 235 */     if (de != 0)
/*  173:     */     {
/*  174: 236 */       e[0] = (de - 1023 - 52 + k);
/*  175: 237 */       bits[0] = (53 - k);
/*  176:     */     }
/*  177:     */     else
/*  178:     */     {
/*  179: 240 */       e[0] = (de - 1023 - 52 + 1 + k);
/*  180: 241 */       bits[0] = (32 * i - hi0bits(z));
/*  181:     */     }
/*  182: 243 */     return new BigInteger(dbl_bits);
/*  183:     */   }
/*  184:     */   
/*  185:     */   static String JS_dtobasestr(int base, double d)
/*  186:     */   {
/*  187: 248 */     if ((2 > base) || (base > 36)) {
/*  188: 249 */       throw new IllegalArgumentException("Bad base: " + base);
/*  189:     */     }
/*  190: 252 */     if (Double.isNaN(d)) {
/*  191: 253 */       return "NaN";
/*  192:     */     }
/*  193: 254 */     if (Double.isInfinite(d)) {
/*  194: 255 */       return d > 0.0D ? "Infinity" : "-Infinity";
/*  195:     */     }
/*  196: 256 */     if (d == 0.0D) {
/*  197: 258 */       return "0";
/*  198:     */     }
/*  199:     */     boolean negative;
/*  200:     */     boolean negative;
/*  201: 262 */     if (d >= 0.0D)
/*  202:     */     {
/*  203: 263 */       negative = false;
/*  204:     */     }
/*  205:     */     else
/*  206:     */     {
/*  207: 265 */       negative = true;
/*  208: 266 */       d = -d;
/*  209:     */     }
/*  210: 272 */     double dfloor = Math.floor(d);
/*  211: 273 */     long lfloor = dfloor;
/*  212:     */     String intDigits;
/*  213:     */     String intDigits;
/*  214: 274 */     if (lfloor == dfloor)
/*  215:     */     {
/*  216: 276 */       intDigits = Long.toString(negative ? -lfloor : lfloor, base);
/*  217:     */     }
/*  218:     */     else
/*  219:     */     {
/*  220: 279 */       long floorBits = Double.doubleToLongBits(dfloor);
/*  221: 280 */       int exp = (int)(floorBits >> 52) & 0x7FF;
/*  222:     */       long mantissa;
/*  223:     */       long mantissa;
/*  224: 282 */       if (exp == 0) {
/*  225: 283 */         mantissa = (floorBits & 0xFFFFFFFF) << 1;
/*  226:     */       } else {
/*  227: 285 */         mantissa = floorBits & 0xFFFFFFFF | 0x0;
/*  228:     */       }
/*  229: 287 */       if (negative) {
/*  230: 288 */         mantissa = -mantissa;
/*  231:     */       }
/*  232: 290 */       exp -= 1075;
/*  233: 291 */       BigInteger x = BigInteger.valueOf(mantissa);
/*  234: 292 */       if (exp > 0) {
/*  235: 293 */         x = x.shiftLeft(exp);
/*  236: 294 */       } else if (exp < 0) {
/*  237: 295 */         x = x.shiftRight(-exp);
/*  238:     */       }
/*  239: 297 */       intDigits = x.toString(base);
/*  240:     */     }
/*  241: 300 */     if (d == dfloor) {
/*  242: 302 */       return intDigits;
/*  243:     */     }
/*  244: 312 */     char[] buffer = new char[1078];
/*  245: 313 */     int p = 0;
/*  246: 314 */     double df = d - dfloor;
/*  247:     */     
/*  248: 316 */     long dBits = Double.doubleToLongBits(d);
/*  249: 317 */     int word0 = (int)(dBits >> 32);
/*  250: 318 */     int word1 = (int)dBits;
/*  251:     */     
/*  252: 320 */     int[] e = new int[1];
/*  253: 321 */     int[] bbits = new int[1];
/*  254:     */     
/*  255: 323 */     BigInteger b = d2b(df, e, bbits);
/*  256:     */     
/*  257:     */ 
/*  258:     */ 
/*  259: 327 */     int s2 = -(word0 >>> 20 & 0x7FF);
/*  260: 328 */     if (s2 == 0) {
/*  261: 329 */       s2 = -1;
/*  262:     */     }
/*  263: 330 */     s2 += 1076;
/*  264:     */     
/*  265:     */ 
/*  266: 333 */     BigInteger mlo = BigInteger.valueOf(1L);
/*  267: 334 */     BigInteger mhi = mlo;
/*  268: 335 */     if ((word1 == 0) && ((word0 & 0xFFFFF) == 0) && ((word0 & 0x7FE00000) != 0))
/*  269:     */     {
/*  270: 339 */       s2++;
/*  271: 340 */       mhi = BigInteger.valueOf(2L);
/*  272:     */     }
/*  273: 343 */     b = b.shiftLeft(e[0] + s2);
/*  274: 344 */     BigInteger s = BigInteger.valueOf(1L);
/*  275: 345 */     s = s.shiftLeft(s2);
/*  276:     */     
/*  277:     */ 
/*  278:     */ 
/*  279:     */ 
/*  280:     */ 
/*  281: 351 */     BigInteger bigBase = BigInteger.valueOf(base);
/*  282:     */     
/*  283: 353 */     boolean done = false;
/*  284:     */     do
/*  285:     */     {
/*  286: 355 */       b = b.multiply(bigBase);
/*  287: 356 */       BigInteger[] divResult = b.divideAndRemainder(s);
/*  288: 357 */       b = divResult[1];
/*  289: 358 */       int digit = (char)divResult[0].intValue();
/*  290: 359 */       if (mlo == mhi)
/*  291:     */       {
/*  292: 360 */         mlo = mhi = mlo.multiply(bigBase);
/*  293:     */       }
/*  294:     */       else
/*  295:     */       {
/*  296: 362 */         mlo = mlo.multiply(bigBase);
/*  297: 363 */         mhi = mhi.multiply(bigBase);
/*  298:     */       }
/*  299: 367 */       int j = b.compareTo(mlo);
/*  300:     */       
/*  301: 369 */       BigInteger delta = s.subtract(mhi);
/*  302: 370 */       int j1 = delta.signum() <= 0 ? 1 : b.compareTo(delta);
/*  303: 372 */       if ((j1 == 0) && ((word1 & 0x1) == 0))
/*  304:     */       {
/*  305: 373 */         if (j > 0) {
/*  306: 374 */           digit++;
/*  307:     */         }
/*  308: 375 */         done = true;
/*  309:     */       }
/*  310: 377 */       else if ((j < 0) || ((j == 0) && ((word1 & 0x1) == 0)))
/*  311:     */       {
/*  312: 378 */         if (j1 > 0)
/*  313:     */         {
/*  314: 381 */           b = b.shiftLeft(1);
/*  315: 382 */           j1 = b.compareTo(s);
/*  316: 383 */           if (j1 > 0) {
/*  317: 385 */             digit++;
/*  318:     */           }
/*  319:     */         }
/*  320: 387 */         done = true;
/*  321:     */       }
/*  322: 388 */       else if (j1 > 0)
/*  323:     */       {
/*  324: 389 */         digit++;
/*  325: 390 */         done = true;
/*  326:     */       }
/*  327: 393 */       buffer[(p++)] = BASEDIGIT(digit);
/*  328: 394 */     } while (!done);
/*  329: 396 */     StringBuffer sb = new StringBuffer(intDigits.length() + 1 + p);
/*  330: 397 */     sb.append(intDigits);
/*  331: 398 */     sb.append('.');
/*  332: 399 */     sb.append(buffer, 0, p);
/*  333: 400 */     return sb.toString();
/*  334:     */   }
/*  335:     */   
/*  336:     */   static int word0(double d)
/*  337:     */   {
/*  338: 441 */     long dBits = Double.doubleToLongBits(d);
/*  339: 442 */     return (int)(dBits >> 32);
/*  340:     */   }
/*  341:     */   
/*  342:     */   static double setWord0(double d, int i)
/*  343:     */   {
/*  344: 447 */     long dBits = Double.doubleToLongBits(d);
/*  345: 448 */     dBits = i << 32 | dBits & 0xFFFFFFFF;
/*  346: 449 */     return Double.longBitsToDouble(dBits);
/*  347:     */   }
/*  348:     */   
/*  349:     */   static int word1(double d)
/*  350:     */   {
/*  351: 454 */     long dBits = Double.doubleToLongBits(d);
/*  352: 455 */     return (int)dBits;
/*  353:     */   }
/*  354:     */   
/*  355:     */   static BigInteger pow5mult(BigInteger b, int k)
/*  356:     */   {
/*  357: 462 */     return b.multiply(BigInteger.valueOf(5L).pow(k));
/*  358:     */   }
/*  359:     */   
/*  360:     */   static boolean roundOff(StringBuffer buf)
/*  361:     */   {
/*  362: 467 */     int i = buf.length();
/*  363: 468 */     while (i != 0)
/*  364:     */     {
/*  365: 469 */       i--;
/*  366: 470 */       char c = buf.charAt(i);
/*  367: 471 */       if (c != '9')
/*  368:     */       {
/*  369: 472 */         buf.setCharAt(i, (char)(c + '\001'));
/*  370: 473 */         buf.setLength(i + 1);
/*  371: 474 */         return false;
/*  372:     */       }
/*  373:     */     }
/*  374: 477 */     buf.setLength(0);
/*  375: 478 */     return true;
/*  376:     */   }
/*  377:     */   
/*  378:     */   static int JS_dtoa(double d, int mode, boolean biasUp, int ndigits, boolean[] sign, StringBuffer buf)
/*  379:     */   {
/*  380: 532 */     int[] be = new int[1];
/*  381: 533 */     int[] bbits = new int[1];
/*  382: 537 */     if ((word0(d) & 0x80000000) != 0)
/*  383:     */     {
/*  384: 539 */       sign[0] = true;
/*  385:     */       
/*  386: 541 */       d = setWord0(d, word0(d) & 0x7FFFFFFF);
/*  387:     */     }
/*  388:     */     else
/*  389:     */     {
/*  390: 544 */       sign[0] = false;
/*  391:     */     }
/*  392: 546 */     if ((word0(d) & 0x7FF00000) == 2146435072)
/*  393:     */     {
/*  394: 548 */       buf.append((word1(d) == 0) && ((word0(d) & 0xFFFFF) == 0) ? "Infinity" : "NaN");
/*  395: 549 */       return 9999;
/*  396:     */     }
/*  397: 551 */     if (d == 0.0D)
/*  398:     */     {
/*  399: 553 */       buf.setLength(0);
/*  400: 554 */       buf.append('0');
/*  401: 555 */       return 1;
/*  402:     */     }
/*  403: 558 */     BigInteger b = d2b(d, be, bbits);
/*  404:     */     boolean denorm;
/*  405:     */     double d2;
/*  406:     */     boolean denorm;
/*  407: 559 */     if ((i = word0(d) >>> 20 & 0x7FF) != 0)
/*  408:     */     {
/*  409: 560 */       double d2 = setWord0(d, word0(d) & 0xFFFFF | 0x3FF00000);
/*  410:     */       
/*  411:     */ 
/*  412:     */ 
/*  413:     */ 
/*  414:     */ 
/*  415:     */ 
/*  416:     */ 
/*  417:     */ 
/*  418:     */ 
/*  419:     */ 
/*  420:     */ 
/*  421:     */ 
/*  422:     */ 
/*  423:     */ 
/*  424:     */ 
/*  425:     */ 
/*  426:     */ 
/*  427:     */ 
/*  428:     */ 
/*  429:     */ 
/*  430:     */ 
/*  431: 582 */       i -= 1023;
/*  432: 583 */       denorm = false;
/*  433:     */     }
/*  434:     */     else
/*  435:     */     {
/*  436: 587 */       i = bbits[0] + be[0] + 1074;
/*  437: 588 */       long x = i > 32 ? word0(d) << 64 - i | word1(d) >>> i - 32 : word1(d) << 32 - i;
/*  438:     */       
/*  439:     */ 
/*  440: 591 */       d2 = setWord0(x, word0(x) - 32505856);
/*  441: 592 */       i -= 1075;
/*  442: 593 */       denorm = true;
/*  443:     */     }
/*  444: 596 */     double ds = (d2 - 1.5D) * 0.289529654602168D + 0.1760912590558D + i * 0.301029995663981D;
/*  445: 597 */     int k = (int)ds;
/*  446: 598 */     if ((ds < 0.0D) && (ds != k)) {
/*  447: 599 */       k--;
/*  448:     */     }
/*  449: 600 */     boolean k_check = true;
/*  450: 601 */     if ((k >= 0) && (k <= 22))
/*  451:     */     {
/*  452: 602 */       if (d < tens[k]) {
/*  453: 603 */         k--;
/*  454:     */       }
/*  455: 604 */       k_check = false;
/*  456:     */     }
/*  457: 608 */     int j = bbits[0] - i - 1;
/*  458:     */     int s2;
/*  459:     */     int b2;
/*  460:     */     int s2;
/*  461: 610 */     if (j >= 0)
/*  462:     */     {
/*  463: 611 */       int b2 = 0;
/*  464: 612 */       s2 = j;
/*  465:     */     }
/*  466:     */     else
/*  467:     */     {
/*  468: 615 */       b2 = -j;
/*  469: 616 */       s2 = 0;
/*  470:     */     }
/*  471:     */     int b5;
/*  472:     */     int s5;
/*  473: 618 */     if (k >= 0)
/*  474:     */     {
/*  475: 619 */       int b5 = 0;
/*  476: 620 */       int s5 = k;
/*  477: 621 */       s2 += k;
/*  478:     */     }
/*  479:     */     else
/*  480:     */     {
/*  481: 624 */       b2 -= k;
/*  482: 625 */       b5 = -k;
/*  483: 626 */       s5 = 0;
/*  484:     */     }
/*  485: 630 */     if ((mode < 0) || (mode > 9)) {
/*  486: 631 */       mode = 0;
/*  487:     */     }
/*  488: 632 */     boolean try_quick = true;
/*  489: 633 */     if (mode > 5)
/*  490:     */     {
/*  491: 634 */       mode -= 4;
/*  492: 635 */       try_quick = false;
/*  493:     */     }
/*  494: 637 */     boolean leftright = true;
/*  495:     */     int ilim1;
/*  496: 638 */     int ilim = ilim1 = 0;
/*  497: 639 */     switch (mode)
/*  498:     */     {
/*  499:     */     case 0: 
/*  500:     */     case 1: 
/*  501: 642 */       ilim = ilim1 = -1;
/*  502: 643 */       i = 18;
/*  503: 644 */       ndigits = 0;
/*  504: 645 */       break;
/*  505:     */     case 2: 
/*  506: 647 */       leftright = false;
/*  507:     */     case 4: 
/*  508: 650 */       if (ndigits <= 0) {
/*  509: 651 */         ndigits = 1;
/*  510:     */       }
/*  511: 652 */       ilim = ilim1 = i = ndigits;
/*  512: 653 */       break;
/*  513:     */     case 3: 
/*  514: 655 */       leftright = false;
/*  515:     */     case 5: 
/*  516: 658 */       i = ndigits + k + 1;
/*  517: 659 */       ilim = i;
/*  518: 660 */       ilim1 = i - 1;
/*  519: 661 */       if (i <= 0) {
/*  520: 662 */         i = 1;
/*  521:     */       }
/*  522:     */       break;
/*  523:     */     }
/*  524: 668 */     boolean fast_failed = false;
/*  525: 669 */     if ((ilim >= 0) && (ilim <= 14) && (try_quick))
/*  526:     */     {
/*  527: 673 */       i = 0;
/*  528: 674 */       d2 = d;
/*  529: 675 */       int k0 = k;
/*  530: 676 */       int ilim0 = ilim;
/*  531: 677 */       int ieps = 2;
/*  532: 679 */       if (k > 0)
/*  533:     */       {
/*  534: 680 */         ds = tens[(k & 0xF)];
/*  535: 681 */         j = k >> 4;
/*  536: 682 */         if ((j & 0x10) != 0)
/*  537:     */         {
/*  538: 684 */           j &= 0xF;
/*  539: 685 */           d /= bigtens[4];
/*  540: 686 */           ieps++;
/*  541:     */         }
/*  542: 688 */         for (; j != 0; i++)
/*  543:     */         {
/*  544: 689 */           if ((j & 0x1) != 0)
/*  545:     */           {
/*  546: 690 */             ieps++;
/*  547: 691 */             ds *= bigtens[i];
/*  548:     */           }
/*  549: 688 */           j >>= 1;
/*  550:     */         }
/*  551: 693 */         d /= ds;
/*  552:     */       }
/*  553:     */       else
/*  554:     */       {
/*  555:     */         int j1;
/*  556: 695 */         if ((j1 = -k) != 0)
/*  557:     */         {
/*  558: 696 */           d *= tens[(j1 & 0xF)];
/*  559: 697 */           for (j = j1 >> 4; j != 0; i++)
/*  560:     */           {
/*  561: 698 */             if ((j & 0x1) != 0)
/*  562:     */             {
/*  563: 699 */               ieps++;
/*  564: 700 */               d *= bigtens[i];
/*  565:     */             }
/*  566: 697 */             j >>= 1;
/*  567:     */           }
/*  568:     */         }
/*  569:     */       }
/*  570: 704 */       if ((k_check) && (d < 1.0D) && (ilim > 0)) {
/*  571: 705 */         if (ilim1 <= 0)
/*  572:     */         {
/*  573: 706 */           fast_failed = true;
/*  574:     */         }
/*  575:     */         else
/*  576:     */         {
/*  577: 708 */           ilim = ilim1;
/*  578: 709 */           k--;
/*  579: 710 */           d *= 10.0D;
/*  580: 711 */           ieps++;
/*  581:     */         }
/*  582:     */       }
/*  583: 717 */       double eps = ieps * d + 7.0D;
/*  584: 718 */       eps = setWord0(eps, word0(eps) - 54525952);
/*  585: 719 */       if (ilim == 0)
/*  586:     */       {
/*  587:     */         BigInteger mhi;
/*  588: 720 */         BigInteger S = mhi = null;
/*  589: 721 */         d -= 5.0D;
/*  590: 722 */         if (d > eps)
/*  591:     */         {
/*  592: 723 */           buf.append('1');
/*  593: 724 */           k++;
/*  594: 725 */           return k + 1;
/*  595:     */         }
/*  596: 727 */         if (d < -eps)
/*  597:     */         {
/*  598: 728 */           buf.setLength(0);
/*  599: 729 */           buf.append('0');
/*  600: 730 */           return 1;
/*  601:     */         }
/*  602: 732 */         fast_failed = true;
/*  603:     */       }
/*  604: 734 */       if (!fast_failed)
/*  605:     */       {
/*  606: 735 */         fast_failed = true;
/*  607: 736 */         if (leftright)
/*  608:     */         {
/*  609: 740 */           eps = 0.5D / tens[(ilim - 1)] - eps;
/*  610: 741 */           i = 0;
/*  611:     */           for (;;)
/*  612:     */           {
/*  613: 742 */             long L = d;
/*  614: 743 */             d -= L;
/*  615: 744 */             buf.append((char)(int)(48L + L));
/*  616: 745 */             if (d < eps) {
/*  617: 746 */               return k + 1;
/*  618:     */             }
/*  619: 748 */             if (1.0D - d < eps)
/*  620:     */             {
/*  621:     */               do
/*  622:     */               {
/*  623: 752 */                 lastCh = buf.charAt(buf.length() - 1);
/*  624: 753 */                 buf.setLength(buf.length() - 1);
/*  625: 754 */                 if (lastCh != '9') {
/*  626:     */                   break;
/*  627:     */                 }
/*  628: 755 */               } while (buf.length() != 0);
/*  629: 756 */               k++;
/*  630: 757 */               char lastCh = '0';
/*  631:     */               
/*  632:     */ 
/*  633:     */ 
/*  634: 761 */               buf.append((char)(lastCh + '\001'));
/*  635: 762 */               return k + 1;
/*  636:     */             }
/*  637: 764 */             i++;
/*  638: 764 */             if (i >= ilim) {
/*  639:     */               break;
/*  640:     */             }
/*  641: 766 */             eps *= 10.0D;
/*  642: 767 */             d *= 10.0D;
/*  643:     */           }
/*  644:     */         }
/*  645: 772 */         eps *= tens[(ilim - 1)];
/*  646: 773 */         for (i = 1;; d *= 10.0D)
/*  647:     */         {
/*  648: 774 */           long L = d;
/*  649: 775 */           d -= L;
/*  650: 776 */           buf.append((char)(int)(48L + L));
/*  651: 777 */           if (i == ilim)
/*  652:     */           {
/*  653: 778 */             if (d > 0.5D + eps)
/*  654:     */             {
/*  655:     */               do
/*  656:     */               {
/*  657: 782 */                 lastCh = buf.charAt(buf.length() - 1);
/*  658: 783 */                 buf.setLength(buf.length() - 1);
/*  659: 784 */                 if (lastCh != '9') {
/*  660:     */                   break;
/*  661:     */                 }
/*  662: 785 */               } while (buf.length() != 0);
/*  663: 786 */               k++;
/*  664: 787 */               char lastCh = '0';
/*  665:     */               
/*  666:     */ 
/*  667:     */ 
/*  668: 791 */               buf.append((char)(lastCh + '\001'));
/*  669: 792 */               return k + 1;
/*  670:     */             }
/*  671: 795 */             if (d >= 0.5D - eps) {
/*  672:     */               break;
/*  673:     */             }
/*  674: 796 */             stripTrailingZeroes(buf);
/*  675:     */             
/*  676:     */ 
/*  677: 799 */             return k + 1;
/*  678:     */           }
/*  679: 773 */           i++;
/*  680:     */         }
/*  681:     */       }
/*  682: 806 */       if (fast_failed)
/*  683:     */       {
/*  684: 807 */         buf.setLength(0);
/*  685: 808 */         d = d2;
/*  686: 809 */         k = k0;
/*  687: 810 */         ilim = ilim0;
/*  688:     */       }
/*  689:     */     }
/*  690: 816 */     if ((be[0] >= 0) && (k <= 14))
/*  691:     */     {
/*  692: 818 */       ds = tens[k];
/*  693: 819 */       if ((ndigits < 0) && (ilim <= 0))
/*  694:     */       {
/*  695:     */         BigInteger mhi;
/*  696: 820 */         BigInteger S = mhi = null;
/*  697: 821 */         if ((ilim < 0) || (d < 5.0D * ds) || ((!biasUp) && (d == 5.0D * ds)))
/*  698:     */         {
/*  699: 822 */           buf.setLength(0);
/*  700: 823 */           buf.append('0');
/*  701: 824 */           return 1;
/*  702:     */         }
/*  703: 826 */         buf.append('1');
/*  704: 827 */         k++;
/*  705: 828 */         return k + 1;
/*  706:     */       }
/*  707: 830 */       for (i = 1;; i++)
/*  708:     */       {
/*  709: 831 */         long L = (d / ds);
/*  710: 832 */         d -= L * ds;
/*  711: 833 */         buf.append((char)(int)(48L + L));
/*  712: 834 */         if (i == ilim)
/*  713:     */         {
/*  714: 835 */           d += d;
/*  715: 836 */           if ((d > ds) || ((d == ds) && (((L & 1L) != 0L) || (biasUp))))
/*  716:     */           {
/*  717:     */             do
/*  718:     */             {
/*  719: 847 */               lastCh = buf.charAt(buf.length() - 1);
/*  720: 848 */               buf.setLength(buf.length() - 1);
/*  721: 849 */               if (lastCh != '9') {
/*  722:     */                 break;
/*  723:     */               }
/*  724: 850 */             } while (buf.length() != 0);
/*  725: 851 */             k++;
/*  726: 852 */             char lastCh = '0';
/*  727:     */             
/*  728:     */ 
/*  729:     */ 
/*  730: 856 */             buf.append((char)(lastCh + '\001'));
/*  731:     */           }
/*  732:     */         }
/*  733:     */         else
/*  734:     */         {
/*  735: 860 */           d *= 10.0D;
/*  736: 861 */           if (d == 0.0D) {
/*  737:     */             break;
/*  738:     */           }
/*  739:     */         }
/*  740:     */       }
/*  741: 864 */       return k + 1;
/*  742:     */     }
/*  743: 867 */     int m2 = b2;
/*  744: 868 */     int m5 = b5;
/*  745:     */     BigInteger mlo;
/*  746: 869 */     BigInteger mhi = mlo = null;
/*  747: 870 */     if (leftright)
/*  748:     */     {
/*  749: 871 */       if (mode < 2)
/*  750:     */       {
/*  751: 872 */         i = denorm ? be[0] + 1075 : 54 - bbits[0];
/*  752:     */       }
/*  753:     */       else
/*  754:     */       {
/*  755: 877 */         j = ilim - 1;
/*  756: 878 */         if (m5 >= j)
/*  757:     */         {
/*  758: 879 */           m5 -= j;
/*  759:     */         }
/*  760:     */         else
/*  761:     */         {
/*  762: 881 */           s5 += j -= m5;
/*  763: 882 */           b5 += j;
/*  764: 883 */           m5 = 0;
/*  765:     */         }
/*  766: 885 */         if ((i = ilim) < 0)
/*  767:     */         {
/*  768: 886 */           m2 -= i;
/*  769: 887 */           i = 0;
/*  770:     */         }
/*  771:     */       }
/*  772: 891 */       b2 += i;
/*  773: 892 */       s2 += i;
/*  774: 893 */       mhi = BigInteger.valueOf(1L);
/*  775:     */     }
/*  776: 899 */     if ((m2 > 0) && (s2 > 0))
/*  777:     */     {
/*  778: 900 */       i = m2 < s2 ? m2 : s2;
/*  779: 901 */       b2 -= i;
/*  780: 902 */       m2 -= i;
/*  781: 903 */       s2 -= i;
/*  782:     */     }
/*  783: 907 */     if (b5 > 0) {
/*  784: 908 */       if (leftright)
/*  785:     */       {
/*  786: 909 */         if (m5 > 0)
/*  787:     */         {
/*  788: 910 */           mhi = pow5mult(mhi, m5);
/*  789: 911 */           BigInteger b1 = mhi.multiply(b);
/*  790: 912 */           b = b1;
/*  791:     */         }
/*  792: 914 */         if ((j = b5 - m5) != 0) {
/*  793: 915 */           b = pow5mult(b, j);
/*  794:     */         }
/*  795:     */       }
/*  796:     */       else
/*  797:     */       {
/*  798: 918 */         b = pow5mult(b, b5);
/*  799:     */       }
/*  800:     */     }
/*  801: 923 */     BigInteger S = BigInteger.valueOf(1L);
/*  802: 924 */     if (s5 > 0) {
/*  803: 925 */       S = pow5mult(S, s5);
/*  804:     */     }
/*  805: 930 */     boolean spec_case = false;
/*  806: 931 */     if ((mode < 2) && 
/*  807: 932 */       (word1(d) == 0) && ((word0(d) & 0xFFFFF) == 0) && ((word0(d) & 0x7FE00000) != 0))
/*  808:     */     {
/*  809: 937 */       b2++;
/*  810: 938 */       s2++;
/*  811: 939 */       spec_case = true;
/*  812:     */     }
/*  813: 950 */     byte[] S_bytes = S.toByteArray();
/*  814: 951 */     int S_hiWord = 0;
/*  815: 952 */     for (int idx = 0; idx < 4; idx++)
/*  816:     */     {
/*  817: 953 */       S_hiWord <<= 8;
/*  818: 954 */       if (idx < S_bytes.length) {
/*  819: 955 */         S_hiWord |= S_bytes[idx] & 0xFF;
/*  820:     */       }
/*  821:     */     }
/*  822: 957 */     if ((i = (s5 != 0 ? 32 - hi0bits(S_hiWord) : 1) + s2 & 0x1F) != 0) {
/*  823: 958 */       i = 32 - i;
/*  824:     */     }
/*  825: 960 */     if (i > 4)
/*  826:     */     {
/*  827: 961 */       i -= 4;
/*  828: 962 */       b2 += i;
/*  829: 963 */       m2 += i;
/*  830: 964 */       s2 += i;
/*  831:     */     }
/*  832: 966 */     else if (i < 4)
/*  833:     */     {
/*  834: 967 */       i += 28;
/*  835: 968 */       b2 += i;
/*  836: 969 */       m2 += i;
/*  837: 970 */       s2 += i;
/*  838:     */     }
/*  839: 973 */     if (b2 > 0) {
/*  840: 974 */       b = b.shiftLeft(b2);
/*  841:     */     }
/*  842: 975 */     if (s2 > 0) {
/*  843: 976 */       S = S.shiftLeft(s2);
/*  844:     */     }
/*  845: 979 */     if ((k_check) && 
/*  846: 980 */       (b.compareTo(S) < 0))
/*  847:     */     {
/*  848: 981 */       k--;
/*  849: 982 */       b = b.multiply(BigInteger.valueOf(10L));
/*  850: 983 */       if (leftright) {
/*  851: 984 */         mhi = mhi.multiply(BigInteger.valueOf(10L));
/*  852:     */       }
/*  853: 985 */       ilim = ilim1;
/*  854:     */     }
/*  855: 990 */     if ((ilim <= 0) && (mode > 2))
/*  856:     */     {
/*  857: 993 */       if ((ilim < 0) || ((i = b.compareTo(S = S.multiply(BigInteger.valueOf(5L)))) < 0) || ((i == 0) && (!biasUp)))
/*  858:     */       {
/*  859:1001 */         buf.setLength(0);
/*  860:1002 */         buf.append('0');
/*  861:1003 */         return 1;
/*  862:     */       }
/*  863:1007 */       buf.append('1');
/*  864:1008 */       k++;
/*  865:1009 */       return k + 1;
/*  866:     */     }
/*  867:1011 */     if (leftright)
/*  868:     */     {
/*  869:1012 */       if (m2 > 0) {
/*  870:1013 */         mhi = mhi.shiftLeft(m2);
/*  871:     */       }
/*  872:1019 */       mlo = mhi;
/*  873:1020 */       if (spec_case)
/*  874:     */       {
/*  875:1021 */         mhi = mlo;
/*  876:1022 */         mhi = mhi.shiftLeft(1);
/*  877:     */       }
/*  878:1027 */       for (i = 1;; i++)
/*  879:     */       {
/*  880:1028 */         BigInteger[] divResult = b.divideAndRemainder(S);
/*  881:1029 */         b = divResult[1];
/*  882:1030 */         char dig = (char)(divResult[0].intValue() + 48);
/*  883:     */         
/*  884:     */ 
/*  885:     */ 
/*  886:1034 */         j = b.compareTo(mlo);
/*  887:     */         
/*  888:1036 */         BigInteger delta = S.subtract(mhi);
/*  889:1037 */         int j1 = delta.signum() <= 0 ? 1 : b.compareTo(delta);
/*  890:1039 */         if ((j1 == 0) && (mode == 0) && ((word1(d) & 0x1) == 0))
/*  891:     */         {
/*  892:1040 */           if (dig == '9')
/*  893:     */           {
/*  894:1041 */             buf.append('9');
/*  895:1042 */             if (roundOff(buf))
/*  896:     */             {
/*  897:1043 */               k++;
/*  898:1044 */               buf.append('1');
/*  899:     */             }
/*  900:1046 */             return k + 1;
/*  901:     */           }
/*  902:1049 */           if (j > 0) {
/*  903:1050 */             dig = (char)(dig + '\001');
/*  904:     */           }
/*  905:1051 */           buf.append(dig);
/*  906:1052 */           return k + 1;
/*  907:     */         }
/*  908:1054 */         if ((j < 0) || ((j == 0) && (mode == 0) && ((word1(d) & 0x1) == 0)))
/*  909:     */         {
/*  910:1059 */           if (j1 > 0)
/*  911:     */           {
/*  912:1062 */             b = b.shiftLeft(1);
/*  913:1063 */             j1 = b.compareTo(S);
/*  914:1064 */             if ((j1 > 0) || ((j1 == 0) && (((dig & 0x1) == '\001') || (biasUp))))
/*  915:     */             {
/*  916:1064 */               dig = (char)(dig + '\001');
/*  917:1064 */               if (dig == '9')
/*  918:     */               {
/*  919:1066 */                 buf.append('9');
/*  920:1067 */                 if (roundOff(buf))
/*  921:     */                 {
/*  922:1068 */                   k++;
/*  923:1069 */                   buf.append('1');
/*  924:     */                 }
/*  925:1071 */                 return k + 1;
/*  926:     */               }
/*  927:     */             }
/*  928:     */           }
/*  929:1075 */           buf.append(dig);
/*  930:1076 */           return k + 1;
/*  931:     */         }
/*  932:1078 */         if (j1 > 0)
/*  933:     */         {
/*  934:1079 */           if (dig == '9')
/*  935:     */           {
/*  936:1083 */             buf.append('9');
/*  937:1084 */             if (roundOff(buf))
/*  938:     */             {
/*  939:1085 */               k++;
/*  940:1086 */               buf.append('1');
/*  941:     */             }
/*  942:1088 */             return k + 1;
/*  943:     */           }
/*  944:1090 */           buf.append((char)(dig + '\001'));
/*  945:1091 */           return k + 1;
/*  946:     */         }
/*  947:1093 */         buf.append(dig);
/*  948:1094 */         if (i == ilim) {
/*  949:     */           break;
/*  950:     */         }
/*  951:1096 */         b = b.multiply(BigInteger.valueOf(10L));
/*  952:1097 */         if (mlo == mhi)
/*  953:     */         {
/*  954:1098 */           mlo = mhi = mhi.multiply(BigInteger.valueOf(10L));
/*  955:     */         }
/*  956:     */         else
/*  957:     */         {
/*  958:1100 */           mlo = mlo.multiply(BigInteger.valueOf(10L));
/*  959:1101 */           mhi = mhi.multiply(BigInteger.valueOf(10L));
/*  960:     */         }
/*  961:     */       }
/*  962:     */     }
/*  963:     */     char dig;
/*  964:1106 */     for (int i = 1;; i++)
/*  965:     */     {
/*  966:1108 */       BigInteger[] divResult = b.divideAndRemainder(S);
/*  967:1109 */       b = divResult[1];
/*  968:1110 */       dig = (char)(divResult[0].intValue() + 48);
/*  969:1111 */       buf.append(dig);
/*  970:1112 */       if (i >= ilim) {
/*  971:     */         break;
/*  972:     */       }
/*  973:1114 */       b = b.multiply(BigInteger.valueOf(10L));
/*  974:     */     }
/*  975:1119 */     b = b.shiftLeft(1);
/*  976:1120 */     j = b.compareTo(S);
/*  977:1121 */     if ((j > 0) || ((j == 0) && (((dig & 0x1) == '\001') || (biasUp))))
/*  978:     */     {
/*  979:1130 */       if (roundOff(buf))
/*  980:     */       {
/*  981:1131 */         k++;
/*  982:1132 */         buf.append('1');
/*  983:1133 */         return k + 1;
/*  984:     */       }
/*  985:     */     }
/*  986:     */     else {
/*  987:1137 */       stripTrailingZeroes(buf);
/*  988:     */     }
/*  989:1151 */     return k + 1;
/*  990:     */   }
/*  991:     */   
/*  992:     */   private static void stripTrailingZeroes(StringBuffer buf)
/*  993:     */   {
/*  994:1159 */     int bl = buf.length();
/*  995:1160 */     while ((bl-- > 0) && (buf.charAt(bl) == '0')) {}
/*  996:1163 */     buf.setLength(bl + 1);
/*  997:     */   }
/*  998:     */   
/*  999:1167 */   private static final int[] dtoaModes = { 0, 0, 3, 2, 2 };
/* 1000:     */   
/* 1001:     */   static void JS_dtostr(StringBuffer buffer, int mode, int precision, double d)
/* 1002:     */   {
/* 1003:1178 */     boolean[] sign = new boolean[1];
/* 1004:1184 */     if ((mode == 2) && ((d >= 1.0E+021D) || (d <= -1.0E+021D))) {
/* 1005:1185 */       mode = 0;
/* 1006:     */     }
/* 1007:1187 */     int decPt = JS_dtoa(d, dtoaModes[mode], mode >= 2, precision, sign, buffer);
/* 1008:1188 */     int nDigits = buffer.length();
/* 1009:1191 */     if ((mode == 0) && (buffer.charAt(0) > '9'))
/* 1010:     */     {
/* 1011:1192 */       char c0 = buffer.charAt(0);
/* 1012:1193 */       char c1 = buffer.charAt(1);
/* 1013:1194 */       int sum = c0 - 'A' + 10 + (c1 - '0');
/* 1014:1195 */       buffer.replace(0, 1, Integer.toString(sum));
/* 1015:1196 */       decPt++;
/* 1016:     */     }
/* 1017:1200 */     if (decPt != 9999)
/* 1018:     */     {
/* 1019:1201 */       boolean exponentialNotation = false;
/* 1020:1202 */       int minNDigits = 0;
/* 1021:1205 */       switch (mode)
/* 1022:     */       {
/* 1023:     */       case 0: 
/* 1024:1207 */         if ((decPt < -5) || (decPt > 21)) {
/* 1025:1208 */           exponentialNotation = true;
/* 1026:     */         } else {
/* 1027:1210 */           minNDigits = decPt;
/* 1028:     */         }
/* 1029:1211 */         break;
/* 1030:     */       case 2: 
/* 1031:1214 */         if (precision >= 0) {
/* 1032:1215 */           minNDigits = decPt + precision;
/* 1033:     */         } else {
/* 1034:1217 */           minNDigits = decPt;
/* 1035:     */         }
/* 1036:1218 */         break;
/* 1037:     */       case 3: 
/* 1038:1222 */         minNDigits = precision;
/* 1039:     */       case 1: 
/* 1040:1225 */         exponentialNotation = true;
/* 1041:1226 */         break;
/* 1042:     */       case 4: 
/* 1043:1230 */         minNDigits = precision;
/* 1044:1231 */         if ((decPt < -5) || (decPt > precision)) {
/* 1045:1232 */           exponentialNotation = true;
/* 1046:     */         }
/* 1047:     */         break;
/* 1048:     */       }
/* 1049:1237 */       if (nDigits < minNDigits)
/* 1050:     */       {
/* 1051:1238 */         int p = minNDigits;
/* 1052:1239 */         nDigits = minNDigits;
/* 1053:     */         do
/* 1054:     */         {
/* 1055:1241 */           buffer.append('0');
/* 1056:1242 */         } while (buffer.length() != p);
/* 1057:     */       }
/* 1058:1245 */       if (exponentialNotation)
/* 1059:     */       {
/* 1060:1247 */         if (nDigits != 1) {
/* 1061:1248 */           buffer.insert(1, '.');
/* 1062:     */         }
/* 1063:1250 */         buffer.append('e');
/* 1064:1251 */         if (decPt - 1 >= 0) {
/* 1065:1252 */           buffer.append('+');
/* 1066:     */         }
/* 1067:1253 */         buffer.append(decPt - 1);
/* 1068:     */       }
/* 1069:1255 */       else if (decPt != nDigits)
/* 1070:     */       {
/* 1071:1258 */         if (decPt > 0)
/* 1072:     */         {
/* 1073:1260 */           buffer.insert(decPt, '.');
/* 1074:     */         }
/* 1075:     */         else
/* 1076:     */         {
/* 1077:1263 */           for (int i = 0; i < 1 - decPt; i++) {
/* 1078:1264 */             buffer.insert(0, '0');
/* 1079:     */           }
/* 1080:1265 */           buffer.insert(1, '.');
/* 1081:     */         }
/* 1082:     */       }
/* 1083:     */     }
/* 1084:1271 */     if ((sign[0] != 0) && ((word0(d) != -2147483648) || (word1(d) != 0)) && (((word0(d) & 0x7FF00000) != 2146435072) || ((word1(d) == 0) && ((word0(d) & 0xFFFFF) == 0)))) {
/* 1085:1275 */       buffer.insert(0, '-');
/* 1086:     */     }
/* 1087:     */   }
/* 1088:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     net.sourceforge.htmlunit.corejs.javascript.DToA
 * JD-Core Version:    0.7.0.1
 */