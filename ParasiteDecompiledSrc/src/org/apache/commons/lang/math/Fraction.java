/*   1:    */ package org.apache.commons.lang.math;
/*   2:    */ 
/*   3:    */ import java.math.BigInteger;
/*   4:    */ import org.apache.commons.lang.text.StrBuilder;
/*   5:    */ 
/*   6:    */ public final class Fraction
/*   7:    */   extends Number
/*   8:    */   implements Comparable
/*   9:    */ {
/*  10:    */   private static final long serialVersionUID = 65382027393090L;
/*  11: 50 */   public static final Fraction ZERO = new Fraction(0, 1);
/*  12: 54 */   public static final Fraction ONE = new Fraction(1, 1);
/*  13: 58 */   public static final Fraction ONE_HALF = new Fraction(1, 2);
/*  14: 62 */   public static final Fraction ONE_THIRD = new Fraction(1, 3);
/*  15: 66 */   public static final Fraction TWO_THIRDS = new Fraction(2, 3);
/*  16: 70 */   public static final Fraction ONE_QUARTER = new Fraction(1, 4);
/*  17: 74 */   public static final Fraction TWO_QUARTERS = new Fraction(2, 4);
/*  18: 78 */   public static final Fraction THREE_QUARTERS = new Fraction(3, 4);
/*  19: 82 */   public static final Fraction ONE_FIFTH = new Fraction(1, 5);
/*  20: 86 */   public static final Fraction TWO_FIFTHS = new Fraction(2, 5);
/*  21: 90 */   public static final Fraction THREE_FIFTHS = new Fraction(3, 5);
/*  22: 94 */   public static final Fraction FOUR_FIFTHS = new Fraction(4, 5);
/*  23:    */   private final int numerator;
/*  24:    */   private final int denominator;
/*  25:109 */   private transient int hashCode = 0;
/*  26:113 */   private transient String toString = null;
/*  27:117 */   private transient String toProperString = null;
/*  28:    */   
/*  29:    */   private Fraction(int numerator, int denominator)
/*  30:    */   {
/*  31:128 */     this.numerator = numerator;
/*  32:129 */     this.denominator = denominator;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static Fraction getFraction(int numerator, int denominator)
/*  36:    */   {
/*  37:144 */     if (denominator == 0) {
/*  38:145 */       throw new ArithmeticException("The denominator must not be zero");
/*  39:    */     }
/*  40:147 */     if (denominator < 0)
/*  41:    */     {
/*  42:148 */       if ((numerator == -2147483648) || (denominator == -2147483648)) {
/*  43:150 */         throw new ArithmeticException("overflow: can't negate");
/*  44:    */       }
/*  45:152 */       numerator = -numerator;
/*  46:153 */       denominator = -denominator;
/*  47:    */     }
/*  48:155 */     return new Fraction(numerator, denominator);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public static Fraction getFraction(int whole, int numerator, int denominator)
/*  52:    */   {
/*  53:175 */     if (denominator == 0) {
/*  54:176 */       throw new ArithmeticException("The denominator must not be zero");
/*  55:    */     }
/*  56:178 */     if (denominator < 0) {
/*  57:179 */       throw new ArithmeticException("The denominator must not be negative");
/*  58:    */     }
/*  59:181 */     if (numerator < 0) {
/*  60:182 */       throw new ArithmeticException("The numerator must not be negative");
/*  61:    */     }
/*  62:    */     long numeratorValue;
/*  63:    */     long numeratorValue;
/*  64:185 */     if (whole < 0) {
/*  65:186 */       numeratorValue = whole * denominator - numerator;
/*  66:    */     } else {
/*  67:188 */       numeratorValue = whole * denominator + numerator;
/*  68:    */     }
/*  69:190 */     if ((numeratorValue < -2147483648L) || (numeratorValue > 2147483647L)) {
/*  70:192 */       throw new ArithmeticException("Numerator too large to represent as an Integer.");
/*  71:    */     }
/*  72:194 */     return new Fraction((int)numeratorValue, denominator);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public static Fraction getReducedFraction(int numerator, int denominator)
/*  76:    */   {
/*  77:212 */     if (denominator == 0) {
/*  78:213 */       throw new ArithmeticException("The denominator must not be zero");
/*  79:    */     }
/*  80:215 */     if (numerator == 0) {
/*  81:216 */       return ZERO;
/*  82:    */     }
/*  83:219 */     if ((denominator == -2147483648) && ((numerator & 0x1) == 0))
/*  84:    */     {
/*  85:220 */       numerator /= 2;denominator /= 2;
/*  86:    */     }
/*  87:222 */     if (denominator < 0)
/*  88:    */     {
/*  89:223 */       if ((numerator == -2147483648) || (denominator == -2147483648)) {
/*  90:225 */         throw new ArithmeticException("overflow: can't negate");
/*  91:    */       }
/*  92:227 */       numerator = -numerator;
/*  93:228 */       denominator = -denominator;
/*  94:    */     }
/*  95:231 */     int gcd = greatestCommonDivisor(numerator, denominator);
/*  96:232 */     numerator /= gcd;
/*  97:233 */     denominator /= gcd;
/*  98:234 */     return new Fraction(numerator, denominator);
/*  99:    */   }
/* 100:    */   
/* 101:    */   public static Fraction getFraction(double value)
/* 102:    */   {
/* 103:252 */     int sign = value < 0.0D ? -1 : 1;
/* 104:253 */     value = Math.abs(value);
/* 105:254 */     if ((value > 2147483647.0D) || (Double.isNaN(value))) {
/* 106:255 */       throw new ArithmeticException("The value must not be greater than Integer.MAX_VALUE or NaN");
/* 107:    */     }
/* 108:258 */     int wholeNumber = (int)value;
/* 109:259 */     value -= wholeNumber;
/* 110:    */     
/* 111:261 */     int numer0 = 0;
/* 112:262 */     int denom0 = 1;
/* 113:263 */     int numer1 = 1;
/* 114:264 */     int denom1 = 0;
/* 115:265 */     int numer2 = 0;
/* 116:266 */     int denom2 = 0;
/* 117:267 */     int a1 = (int)value;
/* 118:268 */     int a2 = 0;
/* 119:269 */     double x1 = 1.0D;
/* 120:270 */     double x2 = 0.0D;
/* 121:271 */     double y1 = value - a1;
/* 122:272 */     double y2 = 0.0D;
/* 123:273 */     double delta2 = 1.7976931348623157E+308D;
/* 124:    */     
/* 125:275 */     int i = 1;
/* 126:    */     double delta1;
/* 127:    */     do
/* 128:    */     {
/* 129:278 */       delta1 = delta2;
/* 130:279 */       a2 = (int)(x1 / y1);
/* 131:280 */       x2 = y1;
/* 132:281 */       y2 = x1 - a2 * y1;
/* 133:282 */       numer2 = a1 * numer1 + numer0;
/* 134:283 */       denom2 = a1 * denom1 + denom0;
/* 135:284 */       double fraction = numer2 / denom2;
/* 136:285 */       delta2 = Math.abs(value - fraction);
/* 137:    */       
/* 138:287 */       a1 = a2;
/* 139:288 */       x1 = x2;
/* 140:289 */       y1 = y2;
/* 141:290 */       numer0 = numer1;
/* 142:291 */       denom0 = denom1;
/* 143:292 */       numer1 = numer2;
/* 144:293 */       denom1 = denom2;
/* 145:294 */       i++;
/* 146:296 */     } while ((delta1 > delta2) && (denom2 <= 10000) && (denom2 > 0) && (i < 25));
/* 147:297 */     if (i == 25) {
/* 148:298 */       throw new ArithmeticException("Unable to convert double to fraction");
/* 149:    */     }
/* 150:300 */     return getReducedFraction((numer0 + wholeNumber * denom0) * sign, denom0);
/* 151:    */   }
/* 152:    */   
/* 153:    */   public static Fraction getFraction(String str)
/* 154:    */   {
/* 155:322 */     if (str == null) {
/* 156:323 */       throw new IllegalArgumentException("The string must not be null");
/* 157:    */     }
/* 158:326 */     int pos = str.indexOf('.');
/* 159:327 */     if (pos >= 0) {
/* 160:328 */       return getFraction(Double.parseDouble(str));
/* 161:    */     }
/* 162:332 */     pos = str.indexOf(' ');
/* 163:333 */     if (pos > 0)
/* 164:    */     {
/* 165:334 */       int whole = Integer.parseInt(str.substring(0, pos));
/* 166:335 */       str = str.substring(pos + 1);
/* 167:336 */       pos = str.indexOf('/');
/* 168:337 */       if (pos < 0) {
/* 169:338 */         throw new NumberFormatException("The fraction could not be parsed as the format X Y/Z");
/* 170:    */       }
/* 171:340 */       int numer = Integer.parseInt(str.substring(0, pos));
/* 172:341 */       int denom = Integer.parseInt(str.substring(pos + 1));
/* 173:342 */       return getFraction(whole, numer, denom);
/* 174:    */     }
/* 175:347 */     pos = str.indexOf('/');
/* 176:348 */     if (pos < 0) {
/* 177:350 */       return getFraction(Integer.parseInt(str), 1);
/* 178:    */     }
/* 179:352 */     int numer = Integer.parseInt(str.substring(0, pos));
/* 180:353 */     int denom = Integer.parseInt(str.substring(pos + 1));
/* 181:354 */     return getFraction(numer, denom);
/* 182:    */   }
/* 183:    */   
/* 184:    */   public int getNumerator()
/* 185:    */   {
/* 186:370 */     return this.numerator;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public int getDenominator()
/* 190:    */   {
/* 191:379 */     return this.denominator;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public int getProperNumerator()
/* 195:    */   {
/* 196:394 */     return Math.abs(this.numerator % this.denominator);
/* 197:    */   }
/* 198:    */   
/* 199:    */   public int getProperWhole()
/* 200:    */   {
/* 201:409 */     return this.numerator / this.denominator;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public int intValue()
/* 205:    */   {
/* 206:422 */     return this.numerator / this.denominator;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public long longValue()
/* 210:    */   {
/* 211:432 */     return this.numerator / this.denominator;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public float floatValue()
/* 215:    */   {
/* 216:442 */     return this.numerator / this.denominator;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public double doubleValue()
/* 220:    */   {
/* 221:452 */     return this.numerator / this.denominator;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public Fraction reduce()
/* 225:    */   {
/* 226:468 */     if (this.numerator == 0) {
/* 227:469 */       return equals(ZERO) ? this : ZERO;
/* 228:    */     }
/* 229:471 */     int gcd = greatestCommonDivisor(Math.abs(this.numerator), this.denominator);
/* 230:472 */     if (gcd == 1) {
/* 231:473 */       return this;
/* 232:    */     }
/* 233:475 */     return getFraction(this.numerator / gcd, this.denominator / gcd);
/* 234:    */   }
/* 235:    */   
/* 236:    */   public Fraction invert()
/* 237:    */   {
/* 238:488 */     if (this.numerator == 0) {
/* 239:489 */       throw new ArithmeticException("Unable to invert zero.");
/* 240:    */     }
/* 241:491 */     if (this.numerator == -2147483648) {
/* 242:492 */       throw new ArithmeticException("overflow: can't negate numerator");
/* 243:    */     }
/* 244:494 */     if (this.numerator < 0) {
/* 245:495 */       return new Fraction(-this.denominator, -this.numerator);
/* 246:    */     }
/* 247:497 */     return new Fraction(this.denominator, this.numerator);
/* 248:    */   }
/* 249:    */   
/* 250:    */   public Fraction negate()
/* 251:    */   {
/* 252:510 */     if (this.numerator == -2147483648) {
/* 253:511 */       throw new ArithmeticException("overflow: too large to negate");
/* 254:    */     }
/* 255:513 */     return new Fraction(-this.numerator, this.denominator);
/* 256:    */   }
/* 257:    */   
/* 258:    */   public Fraction abs()
/* 259:    */   {
/* 260:526 */     if (this.numerator >= 0) {
/* 261:527 */       return this;
/* 262:    */     }
/* 263:529 */     return negate();
/* 264:    */   }
/* 265:    */   
/* 266:    */   public Fraction pow(int power)
/* 267:    */   {
/* 268:545 */     if (power == 1) {
/* 269:546 */       return this;
/* 270:    */     }
/* 271:547 */     if (power == 0) {
/* 272:548 */       return ONE;
/* 273:    */     }
/* 274:549 */     if (power < 0)
/* 275:    */     {
/* 276:550 */       if (power == -2147483648) {
/* 277:551 */         return invert().pow(2).pow(-(power / 2));
/* 278:    */       }
/* 279:553 */       return invert().pow(-power);
/* 280:    */     }
/* 281:555 */     Fraction f = multiplyBy(this);
/* 282:556 */     if (power % 2 == 0) {
/* 283:557 */       return f.pow(power / 2);
/* 284:    */     }
/* 285:559 */     return f.pow(power / 2).multiplyBy(this);
/* 286:    */   }
/* 287:    */   
/* 288:    */   private static int greatestCommonDivisor(int u, int v)
/* 289:    */   {
/* 290:576 */     if ((Math.abs(u) <= 1) || (Math.abs(v) <= 1)) {
/* 291:577 */       return 1;
/* 292:    */     }
/* 293:583 */     if (u > 0) {
/* 294:583 */       u = -u;
/* 295:    */     }
/* 296:584 */     if (v > 0) {
/* 297:584 */       v = -v;
/* 298:    */     }
/* 299:586 */     for (int k = 0; ((u & 0x1) == 0) && ((v & 0x1) == 0) && (k < 31); k++)
/* 300:    */     {
/* 301:588 */       u /= 2;v /= 2;
/* 302:    */     }
/* 303:590 */     if (k == 31) {
/* 304:591 */       throw new ArithmeticException("overflow: gcd is 2^31");
/* 305:    */     }
/* 306:595 */     int t = (u & 0x1) == 1 ? v : -(u / 2);
/* 307:    */     do
/* 308:    */     {
/* 309:601 */       while ((t & 0x1) == 0) {
/* 310:602 */         t /= 2;
/* 311:    */       }
/* 312:605 */       if (t > 0) {
/* 313:606 */         u = -t;
/* 314:    */       } else {
/* 315:608 */         v = t;
/* 316:    */       }
/* 317:611 */       t = (v - u) / 2;
/* 318:614 */     } while (t != 0);
/* 319:615 */     return -u * (1 << k);
/* 320:    */   }
/* 321:    */   
/* 322:    */   private static int mulAndCheck(int x, int y)
/* 323:    */   {
/* 324:631 */     long m = x * y;
/* 325:632 */     if ((m < -2147483648L) || (m > 2147483647L)) {
/* 326:634 */       throw new ArithmeticException("overflow: mul");
/* 327:    */     }
/* 328:636 */     return (int)m;
/* 329:    */   }
/* 330:    */   
/* 331:    */   private static int mulPosAndCheck(int x, int y)
/* 332:    */   {
/* 333:650 */     long m = x * y;
/* 334:651 */     if (m > 2147483647L) {
/* 335:652 */       throw new ArithmeticException("overflow: mulPos");
/* 336:    */     }
/* 337:654 */     return (int)m;
/* 338:    */   }
/* 339:    */   
/* 340:    */   private static int addAndCheck(int x, int y)
/* 341:    */   {
/* 342:667 */     long s = x + y;
/* 343:668 */     if ((s < -2147483648L) || (s > 2147483647L)) {
/* 344:670 */       throw new ArithmeticException("overflow: add");
/* 345:    */     }
/* 346:672 */     return (int)s;
/* 347:    */   }
/* 348:    */   
/* 349:    */   private static int subAndCheck(int x, int y)
/* 350:    */   {
/* 351:685 */     long s = x - y;
/* 352:686 */     if ((s < -2147483648L) || (s > 2147483647L)) {
/* 353:688 */       throw new ArithmeticException("overflow: add");
/* 354:    */     }
/* 355:690 */     return (int)s;
/* 356:    */   }
/* 357:    */   
/* 358:    */   public Fraction add(Fraction fraction)
/* 359:    */   {
/* 360:704 */     return addSub(fraction, true);
/* 361:    */   }
/* 362:    */   
/* 363:    */   public Fraction subtract(Fraction fraction)
/* 364:    */   {
/* 365:718 */     return addSub(fraction, false);
/* 366:    */   }
/* 367:    */   
/* 368:    */   private Fraction addSub(Fraction fraction, boolean isAdd)
/* 369:    */   {
/* 370:732 */     if (fraction == null) {
/* 371:733 */       throw new IllegalArgumentException("The fraction must not be null");
/* 372:    */     }
/* 373:736 */     if (this.numerator == 0) {
/* 374:737 */       return isAdd ? fraction : fraction.negate();
/* 375:    */     }
/* 376:739 */     if (fraction.numerator == 0) {
/* 377:740 */       return this;
/* 378:    */     }
/* 379:744 */     int d1 = greatestCommonDivisor(this.denominator, fraction.denominator);
/* 380:745 */     if (d1 == 1)
/* 381:    */     {
/* 382:747 */       int uvp = mulAndCheck(this.numerator, fraction.denominator);
/* 383:748 */       int upv = mulAndCheck(fraction.numerator, this.denominator);
/* 384:749 */       return new Fraction(isAdd ? addAndCheck(uvp, upv) : subAndCheck(uvp, upv), mulPosAndCheck(this.denominator, fraction.denominator));
/* 385:    */     }
/* 386:756 */     BigInteger uvp = BigInteger.valueOf(this.numerator).multiply(BigInteger.valueOf(fraction.denominator / d1));
/* 387:    */     
/* 388:758 */     BigInteger upv = BigInteger.valueOf(fraction.numerator).multiply(BigInteger.valueOf(this.denominator / d1));
/* 389:    */     
/* 390:760 */     BigInteger t = isAdd ? uvp.add(upv) : uvp.subtract(upv);
/* 391:    */     
/* 392:    */ 
/* 393:763 */     int tmodd1 = t.mod(BigInteger.valueOf(d1)).intValue();
/* 394:764 */     int d2 = tmodd1 == 0 ? d1 : greatestCommonDivisor(tmodd1, d1);
/* 395:    */     
/* 396:    */ 
/* 397:767 */     BigInteger w = t.divide(BigInteger.valueOf(d2));
/* 398:768 */     if (w.bitLength() > 31) {
/* 399:769 */       throw new ArithmeticException("overflow: numerator too large after multiply");
/* 400:    */     }
/* 401:772 */     return new Fraction(w.intValue(), mulPosAndCheck(this.denominator / d1, fraction.denominator / d2));
/* 402:    */   }
/* 403:    */   
/* 404:    */   public Fraction multiplyBy(Fraction fraction)
/* 405:    */   {
/* 406:788 */     if (fraction == null) {
/* 407:789 */       throw new IllegalArgumentException("The fraction must not be null");
/* 408:    */     }
/* 409:791 */     if ((this.numerator == 0) || (fraction.numerator == 0)) {
/* 410:792 */       return ZERO;
/* 411:    */     }
/* 412:796 */     int d1 = greatestCommonDivisor(this.numerator, fraction.denominator);
/* 413:797 */     int d2 = greatestCommonDivisor(fraction.numerator, this.denominator);
/* 414:798 */     return getReducedFraction(mulAndCheck(this.numerator / d1, fraction.numerator / d2), mulPosAndCheck(this.denominator / d2, fraction.denominator / d1));
/* 415:    */   }
/* 416:    */   
/* 417:    */   public Fraction divideBy(Fraction fraction)
/* 418:    */   {
/* 419:814 */     if (fraction == null) {
/* 420:815 */       throw new IllegalArgumentException("The fraction must not be null");
/* 421:    */     }
/* 422:817 */     if (fraction.numerator == 0) {
/* 423:818 */       throw new ArithmeticException("The fraction to divide by must not be zero");
/* 424:    */     }
/* 425:820 */     return multiplyBy(fraction.invert());
/* 426:    */   }
/* 427:    */   
/* 428:    */   public boolean equals(Object obj)
/* 429:    */   {
/* 430:835 */     if (obj == this) {
/* 431:836 */       return true;
/* 432:    */     }
/* 433:838 */     if (!(obj instanceof Fraction)) {
/* 434:839 */       return false;
/* 435:    */     }
/* 436:841 */     Fraction other = (Fraction)obj;
/* 437:842 */     return (getNumerator() == other.getNumerator()) && (getDenominator() == other.getDenominator());
/* 438:    */   }
/* 439:    */   
/* 440:    */   public int hashCode()
/* 441:    */   {
/* 442:852 */     if (this.hashCode == 0) {
/* 443:854 */       this.hashCode = (37 * (629 + getNumerator()) + getDenominator());
/* 444:    */     }
/* 445:856 */     return this.hashCode;
/* 446:    */   }
/* 447:    */   
/* 448:    */   public int compareTo(Object object)
/* 449:    */   {
/* 450:872 */     Fraction other = (Fraction)object;
/* 451:873 */     if (this == other) {
/* 452:874 */       return 0;
/* 453:    */     }
/* 454:876 */     if ((this.numerator == other.numerator) && (this.denominator == other.denominator)) {
/* 455:877 */       return 0;
/* 456:    */     }
/* 457:881 */     long first = this.numerator * other.denominator;
/* 458:882 */     long second = other.numerator * this.denominator;
/* 459:883 */     if (first == second) {
/* 460:884 */       return 0;
/* 461:    */     }
/* 462:885 */     if (first < second) {
/* 463:886 */       return -1;
/* 464:    */     }
/* 465:888 */     return 1;
/* 466:    */   }
/* 467:    */   
/* 468:    */   public String toString()
/* 469:    */   {
/* 470:900 */     if (this.toString == null) {
/* 471:901 */       this.toString = new StrBuilder(32).append(getNumerator()).append('/').append(getDenominator()).toString();
/* 472:    */     }
/* 473:906 */     return this.toString;
/* 474:    */   }
/* 475:    */   
/* 476:    */   public String toProperString()
/* 477:    */   {
/* 478:919 */     if (this.toProperString == null) {
/* 479:920 */       if (this.numerator == 0)
/* 480:    */       {
/* 481:921 */         this.toProperString = "0";
/* 482:    */       }
/* 483:922 */       else if (this.numerator == this.denominator)
/* 484:    */       {
/* 485:923 */         this.toProperString = "1";
/* 486:    */       }
/* 487:924 */       else if (this.numerator == -1 * this.denominator)
/* 488:    */       {
/* 489:925 */         this.toProperString = "-1";
/* 490:    */       }
/* 491:926 */       else if ((this.numerator > 0 ? -this.numerator : this.numerator) < -this.denominator)
/* 492:    */       {
/* 493:931 */         int properNumerator = getProperNumerator();
/* 494:932 */         if (properNumerator == 0) {
/* 495:933 */           this.toProperString = Integer.toString(getProperWhole());
/* 496:    */         } else {
/* 497:935 */           this.toProperString = new StrBuilder(32).append(getProperWhole()).append(' ').append(properNumerator).append('/').append(getDenominator()).toString();
/* 498:    */         }
/* 499:    */       }
/* 500:    */       else
/* 501:    */       {
/* 502:941 */         this.toProperString = new StrBuilder(32).append(getNumerator()).append('/').append(getDenominator()).toString();
/* 503:    */       }
/* 504:    */     }
/* 505:946 */     return this.toProperString;
/* 506:    */   }
/* 507:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.math.Fraction
 * JD-Core Version:    0.7.0.1
 */