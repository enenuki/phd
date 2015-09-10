/*   1:    */ package org.apache.commons.lang;
/*   2:    */ 
/*   3:    */ import java.math.BigDecimal;
/*   4:    */ import java.math.BigInteger;
/*   5:    */ 
/*   6:    */ /**
/*   7:    */  * @deprecated
/*   8:    */  */
/*   9:    */ public final class NumberUtils
/*  10:    */ {
/*  11:    */   public static int stringToInt(String str)
/*  12:    */   {
/*  13: 61 */     return stringToInt(str, 0);
/*  14:    */   }
/*  15:    */   
/*  16:    */   public static int stringToInt(String str, int defaultValue)
/*  17:    */   {
/*  18:    */     try
/*  19:    */     {
/*  20: 74 */       return Integer.parseInt(str);
/*  21:    */     }
/*  22:    */     catch (NumberFormatException nfe) {}
/*  23: 76 */     return defaultValue;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public static Number createNumber(String val)
/*  27:    */     throws NumberFormatException
/*  28:    */   {
/*  29:139 */     if (val == null) {
/*  30:140 */       return null;
/*  31:    */     }
/*  32:142 */     if (val.length() == 0) {
/*  33:143 */       throw new NumberFormatException("\"\" is not a valid number.");
/*  34:    */     }
/*  35:145 */     if ((val.length() == 1) && (!Character.isDigit(val.charAt(0)))) {
/*  36:146 */       throw new NumberFormatException(val + " is not a valid number.");
/*  37:    */     }
/*  38:148 */     if (val.startsWith("--")) {
/*  39:153 */       return null;
/*  40:    */     }
/*  41:155 */     if ((val.startsWith("0x")) || (val.startsWith("-0x"))) {
/*  42:156 */       return createInteger(val);
/*  43:    */     }
/*  44:158 */     char lastChar = val.charAt(val.length() - 1);
/*  45:    */     
/*  46:    */ 
/*  47:    */ 
/*  48:162 */     int decPos = val.indexOf('.');
/*  49:163 */     int expPos = val.indexOf('e') + val.indexOf('E') + 1;
/*  50:    */     String mant;
/*  51:    */     String mant;
/*  52:    */     String dec;
/*  53:165 */     if (decPos > -1)
/*  54:    */     {
/*  55:    */       String dec;
/*  56:    */       String dec;
/*  57:167 */       if (expPos > -1)
/*  58:    */       {
/*  59:168 */         if (expPos < decPos) {
/*  60:169 */           throw new NumberFormatException(val + " is not a valid number.");
/*  61:    */         }
/*  62:171 */         dec = val.substring(decPos + 1, expPos);
/*  63:    */       }
/*  64:    */       else
/*  65:    */       {
/*  66:173 */         dec = val.substring(decPos + 1);
/*  67:    */       }
/*  68:175 */       mant = val.substring(0, decPos);
/*  69:    */     }
/*  70:    */     else
/*  71:    */     {
/*  72:    */       String mant;
/*  73:177 */       if (expPos > -1) {
/*  74:178 */         mant = val.substring(0, expPos);
/*  75:    */       } else {
/*  76:180 */         mant = val;
/*  77:    */       }
/*  78:182 */       dec = null;
/*  79:    */     }
/*  80:184 */     if (!Character.isDigit(lastChar))
/*  81:    */     {
/*  82:    */       String exp;
/*  83:    */       String exp;
/*  84:185 */       if ((expPos > -1) && (expPos < val.length() - 1)) {
/*  85:186 */         exp = val.substring(expPos + 1, val.length() - 1);
/*  86:    */       } else {
/*  87:188 */         exp = null;
/*  88:    */       }
/*  89:191 */       String numeric = val.substring(0, val.length() - 1);
/*  90:192 */       boolean allZeros = (isAllZeros(mant)) && (isAllZeros(exp));
/*  91:193 */       switch (lastChar)
/*  92:    */       {
/*  93:    */       case 'L': 
/*  94:    */       case 'l': 
/*  95:196 */         if ((dec == null) && (exp == null) && (((numeric.charAt(0) == '-') && (isDigits(numeric.substring(1)))) || (isDigits(numeric)))) {
/*  96:    */           try
/*  97:    */           {
/*  98:200 */             return createLong(numeric);
/*  99:    */           }
/* 100:    */           catch (NumberFormatException nfe)
/* 101:    */           {
/* 102:204 */             return createBigInteger(numeric);
/* 103:    */           }
/* 104:    */         }
/* 105:207 */         throw new NumberFormatException(val + " is not a valid number.");
/* 106:    */       case 'F': 
/* 107:    */       case 'f': 
/* 108:    */         try
/* 109:    */         {
/* 110:211 */           Float f = createFloat(numeric);
/* 111:212 */           if ((!f.isInfinite()) && ((f.floatValue() != 0.0F) || (allZeros))) {
/* 112:215 */             return f;
/* 113:    */           }
/* 114:    */         }
/* 115:    */         catch (NumberFormatException e) {}
/* 116:    */       case 'D': 
/* 117:    */       case 'd': 
/* 118:    */         try
/* 119:    */         {
/* 120:225 */           Double d = createDouble(numeric);
/* 121:226 */           if ((!d.isInfinite()) && ((d.floatValue() != 0.0D) || (allZeros))) {
/* 122:227 */             return d;
/* 123:    */           }
/* 124:    */         }
/* 125:    */         catch (NumberFormatException nfe) {}
/* 126:    */         try
/* 127:    */         {
/* 128:233 */           return createBigDecimal(numeric);
/* 129:    */         }
/* 130:    */         catch (NumberFormatException e) {}
/* 131:    */       }
/* 132:239 */       throw new NumberFormatException(val + " is not a valid number.");
/* 133:    */     }
/* 134:    */     String exp;
/* 135:    */     String exp;
/* 136:245 */     if ((expPos > -1) && (expPos < val.length() - 1)) {
/* 137:246 */       exp = val.substring(expPos + 1, val.length());
/* 138:    */     } else {
/* 139:248 */       exp = null;
/* 140:    */     }
/* 141:250 */     if ((dec == null) && (exp == null)) {
/* 142:    */       try
/* 143:    */       {
/* 144:253 */         return createInteger(val);
/* 145:    */       }
/* 146:    */       catch (NumberFormatException nfe)
/* 147:    */       {
/* 148:    */         try
/* 149:    */         {
/* 150:258 */           return createLong(val);
/* 151:    */         }
/* 152:    */         catch (NumberFormatException nfe)
/* 153:    */         {
/* 154:262 */           return createBigInteger(val);
/* 155:    */         }
/* 156:    */       }
/* 157:    */     }
/* 158:266 */     boolean allZeros = (isAllZeros(mant)) && (isAllZeros(exp));
/* 159:    */     try
/* 160:    */     {
/* 161:268 */       Float f = createFloat(val);
/* 162:269 */       if ((!f.isInfinite()) && ((f.floatValue() != 0.0F) || (allZeros))) {
/* 163:270 */         return f;
/* 164:    */       }
/* 165:    */     }
/* 166:    */     catch (NumberFormatException nfe) {}
/* 167:    */     try
/* 168:    */     {
/* 169:276 */       Double d = createDouble(val);
/* 170:277 */       if ((!d.isInfinite()) && ((d.doubleValue() != 0.0D) || (allZeros))) {
/* 171:278 */         return d;
/* 172:    */       }
/* 173:    */     }
/* 174:    */     catch (NumberFormatException nfe) {}
/* 175:284 */     return createBigDecimal(val);
/* 176:    */   }
/* 177:    */   
/* 178:    */   private static boolean isAllZeros(String s)
/* 179:    */   {
/* 180:300 */     if (s == null) {
/* 181:301 */       return true;
/* 182:    */     }
/* 183:303 */     for (int i = s.length() - 1; i >= 0; i--) {
/* 184:304 */       if (s.charAt(i) != '0') {
/* 185:305 */         return false;
/* 186:    */       }
/* 187:    */     }
/* 188:308 */     return s.length() > 0;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public static Float createFloat(String val)
/* 192:    */   {
/* 193:321 */     return Float.valueOf(val);
/* 194:    */   }
/* 195:    */   
/* 196:    */   public static Double createDouble(String val)
/* 197:    */   {
/* 198:332 */     return Double.valueOf(val);
/* 199:    */   }
/* 200:    */   
/* 201:    */   public static Integer createInteger(String val)
/* 202:    */   {
/* 203:345 */     return Integer.decode(val);
/* 204:    */   }
/* 205:    */   
/* 206:    */   public static Long createLong(String val)
/* 207:    */   {
/* 208:356 */     return Long.valueOf(val);
/* 209:    */   }
/* 210:    */   
/* 211:    */   public static BigInteger createBigInteger(String val)
/* 212:    */   {
/* 213:367 */     BigInteger bi = new BigInteger(val);
/* 214:368 */     return bi;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public static BigDecimal createBigDecimal(String val)
/* 218:    */   {
/* 219:379 */     BigDecimal bd = new BigDecimal(val);
/* 220:380 */     return bd;
/* 221:    */   }
/* 222:    */   
/* 223:    */   public static long minimum(long a, long b, long c)
/* 224:    */   {
/* 225:394 */     if (b < a) {
/* 226:395 */       a = b;
/* 227:    */     }
/* 228:397 */     if (c < a) {
/* 229:398 */       a = c;
/* 230:    */     }
/* 231:400 */     return a;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public static int minimum(int a, int b, int c)
/* 235:    */   {
/* 236:412 */     if (b < a) {
/* 237:413 */       a = b;
/* 238:    */     }
/* 239:415 */     if (c < a) {
/* 240:416 */       a = c;
/* 241:    */     }
/* 242:418 */     return a;
/* 243:    */   }
/* 244:    */   
/* 245:    */   public static long maximum(long a, long b, long c)
/* 246:    */   {
/* 247:430 */     if (b > a) {
/* 248:431 */       a = b;
/* 249:    */     }
/* 250:433 */     if (c > a) {
/* 251:434 */       a = c;
/* 252:    */     }
/* 253:436 */     return a;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public static int maximum(int a, int b, int c)
/* 257:    */   {
/* 258:448 */     if (b > a) {
/* 259:449 */       a = b;
/* 260:    */     }
/* 261:451 */     if (c > a) {
/* 262:452 */       a = c;
/* 263:    */     }
/* 264:454 */     return a;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public static int compare(double lhs, double rhs)
/* 268:    */   {
/* 269:494 */     if (lhs < rhs) {
/* 270:495 */       return -1;
/* 271:    */     }
/* 272:497 */     if (lhs > rhs) {
/* 273:498 */       return 1;
/* 274:    */     }
/* 275:504 */     long lhsBits = Double.doubleToLongBits(lhs);
/* 276:505 */     long rhsBits = Double.doubleToLongBits(rhs);
/* 277:506 */     if (lhsBits == rhsBits) {
/* 278:507 */       return 0;
/* 279:    */     }
/* 280:515 */     if (lhsBits < rhsBits) {
/* 281:516 */       return -1;
/* 282:    */     }
/* 283:518 */     return 1;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public static int compare(float lhs, float rhs)
/* 287:    */   {
/* 288:555 */     if (lhs < rhs) {
/* 289:556 */       return -1;
/* 290:    */     }
/* 291:558 */     if (lhs > rhs) {
/* 292:559 */       return 1;
/* 293:    */     }
/* 294:565 */     int lhsBits = Float.floatToIntBits(lhs);
/* 295:566 */     int rhsBits = Float.floatToIntBits(rhs);
/* 296:567 */     if (lhsBits == rhsBits) {
/* 297:568 */       return 0;
/* 298:    */     }
/* 299:576 */     if (lhsBits < rhsBits) {
/* 300:577 */       return -1;
/* 301:    */     }
/* 302:579 */     return 1;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public static boolean isDigits(String str)
/* 306:    */   {
/* 307:596 */     if ((str == null) || (str.length() == 0)) {
/* 308:597 */       return false;
/* 309:    */     }
/* 310:599 */     for (int i = 0; i < str.length(); i++) {
/* 311:600 */       if (!Character.isDigit(str.charAt(i))) {
/* 312:601 */         return false;
/* 313:    */       }
/* 314:    */     }
/* 315:604 */     return true;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public static boolean isNumber(String str)
/* 319:    */   {
/* 320:621 */     if (StringUtils.isEmpty(str)) {
/* 321:622 */       return false;
/* 322:    */     }
/* 323:624 */     char[] chars = str.toCharArray();
/* 324:625 */     int sz = chars.length;
/* 325:626 */     boolean hasExp = false;
/* 326:627 */     boolean hasDecPoint = false;
/* 327:628 */     boolean allowSigns = false;
/* 328:629 */     boolean foundDigit = false;
/* 329:    */     
/* 330:631 */     int start = chars[0] == '-' ? 1 : 0;
/* 331:632 */     if ((sz > start + 1) && 
/* 332:633 */       (chars[start] == '0') && (chars[(start + 1)] == 'x'))
/* 333:    */     {
/* 334:634 */       int i = start + 2;
/* 335:635 */       if (i == sz) {
/* 336:636 */         return false;
/* 337:    */       }
/* 338:639 */       for (; i < chars.length; i++) {
/* 339:640 */         if (((chars[i] < '0') || (chars[i] > '9')) && ((chars[i] < 'a') || (chars[i] > 'f')) && ((chars[i] < 'A') || (chars[i] > 'F'))) {
/* 340:643 */           return false;
/* 341:    */         }
/* 342:    */       }
/* 343:646 */       return true;
/* 344:    */     }
/* 345:649 */     sz--;
/* 346:    */     
/* 347:651 */     int i = start;
/* 348:654 */     while ((i < sz) || ((i < sz + 1) && (allowSigns) && (!foundDigit)))
/* 349:    */     {
/* 350:655 */       if ((chars[i] >= '0') && (chars[i] <= '9'))
/* 351:    */       {
/* 352:656 */         foundDigit = true;
/* 353:657 */         allowSigns = false;
/* 354:    */       }
/* 355:659 */       else if (chars[i] == '.')
/* 356:    */       {
/* 357:660 */         if ((hasDecPoint) || (hasExp)) {
/* 358:662 */           return false;
/* 359:    */         }
/* 360:664 */         hasDecPoint = true;
/* 361:    */       }
/* 362:665 */       else if ((chars[i] == 'e') || (chars[i] == 'E'))
/* 363:    */       {
/* 364:667 */         if (hasExp) {
/* 365:669 */           return false;
/* 366:    */         }
/* 367:671 */         if (!foundDigit) {
/* 368:672 */           return false;
/* 369:    */         }
/* 370:674 */         hasExp = true;
/* 371:675 */         allowSigns = true;
/* 372:    */       }
/* 373:676 */       else if ((chars[i] == '+') || (chars[i] == '-'))
/* 374:    */       {
/* 375:677 */         if (!allowSigns) {
/* 376:678 */           return false;
/* 377:    */         }
/* 378:680 */         allowSigns = false;
/* 379:681 */         foundDigit = false;
/* 380:    */       }
/* 381:    */       else
/* 382:    */       {
/* 383:683 */         return false;
/* 384:    */       }
/* 385:685 */       i++;
/* 386:    */     }
/* 387:687 */     if (i < chars.length)
/* 388:    */     {
/* 389:688 */       if ((chars[i] >= '0') && (chars[i] <= '9')) {
/* 390:690 */         return true;
/* 391:    */       }
/* 392:692 */       if ((chars[i] == 'e') || (chars[i] == 'E')) {
/* 393:694 */         return false;
/* 394:    */       }
/* 395:696 */       if ((!allowSigns) && ((chars[i] == 'd') || (chars[i] == 'D') || (chars[i] == 'f') || (chars[i] == 'F'))) {
/* 396:701 */         return foundDigit;
/* 397:    */       }
/* 398:703 */       if ((chars[i] == 'l') || (chars[i] == 'L')) {
/* 399:706 */         return (foundDigit) && (!hasExp);
/* 400:    */       }
/* 401:709 */       return false;
/* 402:    */     }
/* 403:713 */     return (!allowSigns) && (foundDigit);
/* 404:    */   }
/* 405:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.NumberUtils
 * JD-Core Version:    0.7.0.1
 */