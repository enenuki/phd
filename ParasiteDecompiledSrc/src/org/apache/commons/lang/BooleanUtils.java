/*   1:    */ package org.apache.commons.lang;
/*   2:    */ 
/*   3:    */ import org.apache.commons.lang.math.NumberUtils;
/*   4:    */ 
/*   5:    */ public class BooleanUtils
/*   6:    */ {
/*   7:    */   public static Boolean negate(Boolean bool)
/*   8:    */   {
/*   9: 65 */     if (bool == null) {
/*  10: 66 */       return null;
/*  11:    */     }
/*  12: 68 */     return bool.booleanValue() ? Boolean.FALSE : Boolean.TRUE;
/*  13:    */   }
/*  14:    */   
/*  15:    */   public static boolean isTrue(Boolean bool)
/*  16:    */   {
/*  17: 88 */     if (bool == null) {
/*  18: 89 */       return false;
/*  19:    */     }
/*  20: 91 */     return bool.booleanValue();
/*  21:    */   }
/*  22:    */   
/*  23:    */   public static boolean isNotTrue(Boolean bool)
/*  24:    */   {
/*  25:109 */     return !isTrue(bool);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public static boolean isFalse(Boolean bool)
/*  29:    */   {
/*  30:127 */     if (bool == null) {
/*  31:128 */       return false;
/*  32:    */     }
/*  33:130 */     return !bool.booleanValue();
/*  34:    */   }
/*  35:    */   
/*  36:    */   public static boolean isNotFalse(Boolean bool)
/*  37:    */   {
/*  38:148 */     return !isFalse(bool);
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static Boolean toBooleanObject(boolean bool)
/*  42:    */   {
/*  43:166 */     return bool ? Boolean.TRUE : Boolean.FALSE;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public static boolean toBoolean(Boolean bool)
/*  47:    */   {
/*  48:184 */     if (bool == null) {
/*  49:185 */       return false;
/*  50:    */     }
/*  51:187 */     return bool.booleanValue();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public static boolean toBooleanDefaultIfNull(Boolean bool, boolean valueIfNull)
/*  55:    */   {
/*  56:204 */     if (bool == null) {
/*  57:205 */       return valueIfNull;
/*  58:    */     }
/*  59:207 */     return bool.booleanValue();
/*  60:    */   }
/*  61:    */   
/*  62:    */   public static boolean toBoolean(int value)
/*  63:    */   {
/*  64:227 */     return value != 0;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public static Boolean toBooleanObject(int value)
/*  68:    */   {
/*  69:245 */     return value == 0 ? Boolean.FALSE : Boolean.TRUE;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public static Boolean toBooleanObject(Integer value)
/*  73:    */   {
/*  74:265 */     if (value == null) {
/*  75:266 */       return null;
/*  76:    */     }
/*  77:268 */     return value.intValue() == 0 ? Boolean.FALSE : Boolean.TRUE;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public static boolean toBoolean(int value, int trueValue, int falseValue)
/*  81:    */   {
/*  82:288 */     if (value == trueValue) {
/*  83:289 */       return true;
/*  84:    */     }
/*  85:290 */     if (value == falseValue) {
/*  86:291 */       return false;
/*  87:    */     }
/*  88:294 */     throw new IllegalArgumentException("The Integer did not match either specified value");
/*  89:    */   }
/*  90:    */   
/*  91:    */   public static boolean toBoolean(Integer value, Integer trueValue, Integer falseValue)
/*  92:    */   {
/*  93:317 */     if (value == null)
/*  94:    */     {
/*  95:318 */       if (trueValue == null) {
/*  96:319 */         return true;
/*  97:    */       }
/*  98:320 */       if (falseValue == null) {
/*  99:321 */         return false;
/* 100:    */       }
/* 101:    */     }
/* 102:    */     else
/* 103:    */     {
/* 104:323 */       if (value.equals(trueValue)) {
/* 105:324 */         return true;
/* 106:    */       }
/* 107:325 */       if (value.equals(falseValue)) {
/* 108:326 */         return false;
/* 109:    */       }
/* 110:    */     }
/* 111:329 */     throw new IllegalArgumentException("The Integer did not match either specified value");
/* 112:    */   }
/* 113:    */   
/* 114:    */   public static Boolean toBooleanObject(int value, int trueValue, int falseValue, int nullValue)
/* 115:    */   {
/* 116:349 */     if (value == trueValue) {
/* 117:350 */       return Boolean.TRUE;
/* 118:    */     }
/* 119:351 */     if (value == falseValue) {
/* 120:352 */       return Boolean.FALSE;
/* 121:    */     }
/* 122:353 */     if (value == nullValue) {
/* 123:354 */       return null;
/* 124:    */     }
/* 125:357 */     throw new IllegalArgumentException("The Integer did not match any specified value");
/* 126:    */   }
/* 127:    */   
/* 128:    */   public static Boolean toBooleanObject(Integer value, Integer trueValue, Integer falseValue, Integer nullValue)
/* 129:    */   {
/* 130:380 */     if (value == null)
/* 131:    */     {
/* 132:381 */       if (trueValue == null) {
/* 133:382 */         return Boolean.TRUE;
/* 134:    */       }
/* 135:383 */       if (falseValue == null) {
/* 136:384 */         return Boolean.FALSE;
/* 137:    */       }
/* 138:385 */       if (nullValue == null) {
/* 139:386 */         return null;
/* 140:    */       }
/* 141:    */     }
/* 142:    */     else
/* 143:    */     {
/* 144:388 */       if (value.equals(trueValue)) {
/* 145:389 */         return Boolean.TRUE;
/* 146:    */       }
/* 147:390 */       if (value.equals(falseValue)) {
/* 148:391 */         return Boolean.FALSE;
/* 149:    */       }
/* 150:392 */       if (value.equals(nullValue)) {
/* 151:393 */         return null;
/* 152:    */       }
/* 153:    */     }
/* 154:396 */     throw new IllegalArgumentException("The Integer did not match any specified value");
/* 155:    */   }
/* 156:    */   
/* 157:    */   public static int toInteger(boolean bool)
/* 158:    */   {
/* 159:414 */     return bool ? 1 : 0;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public static Integer toIntegerObject(boolean bool)
/* 163:    */   {
/* 164:430 */     return bool ? NumberUtils.INTEGER_ONE : NumberUtils.INTEGER_ZERO;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public static Integer toIntegerObject(Boolean bool)
/* 168:    */   {
/* 169:448 */     if (bool == null) {
/* 170:449 */       return null;
/* 171:    */     }
/* 172:451 */     return bool.booleanValue() ? NumberUtils.INTEGER_ONE : NumberUtils.INTEGER_ZERO;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public static int toInteger(boolean bool, int trueValue, int falseValue)
/* 176:    */   {
/* 177:468 */     return bool ? trueValue : falseValue;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public static int toInteger(Boolean bool, int trueValue, int falseValue, int nullValue)
/* 181:    */   {
/* 182:487 */     if (bool == null) {
/* 183:488 */       return nullValue;
/* 184:    */     }
/* 185:490 */     return bool.booleanValue() ? trueValue : falseValue;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public static Integer toIntegerObject(boolean bool, Integer trueValue, Integer falseValue)
/* 189:    */   {
/* 190:509 */     return bool ? trueValue : falseValue;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public static Integer toIntegerObject(Boolean bool, Integer trueValue, Integer falseValue, Integer nullValue)
/* 194:    */   {
/* 195:531 */     if (bool == null) {
/* 196:532 */       return nullValue;
/* 197:    */     }
/* 198:534 */     return bool.booleanValue() ? trueValue : falseValue;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public static Boolean toBooleanObject(String str)
/* 202:    */   {
/* 203:570 */     if (str == "true") {
/* 204:571 */       return Boolean.TRUE;
/* 205:    */     }
/* 206:573 */     if (str == null) {
/* 207:574 */       return null;
/* 208:    */     }
/* 209:576 */     switch (str.length())
/* 210:    */     {
/* 211:    */     case 1: 
/* 212:578 */       char ch0 = str.charAt(0);
/* 213:579 */       if ((ch0 == 'y') || (ch0 == 'Y') || (ch0 == 't') || (ch0 == 'T')) {
/* 214:582 */         return Boolean.TRUE;
/* 215:    */       }
/* 216:584 */       if ((ch0 == 'n') || (ch0 == 'N') || (ch0 == 'f') || (ch0 == 'F')) {
/* 217:587 */         return Boolean.FALSE;
/* 218:    */       }
/* 219:    */       break;
/* 220:    */     case 2: 
/* 221:592 */       char ch0 = str.charAt(0);
/* 222:593 */       char ch1 = str.charAt(1);
/* 223:594 */       if (((ch0 == 'o') || (ch0 == 'O')) && ((ch1 == 'n') || (ch1 == 'N'))) {
/* 224:597 */         return Boolean.TRUE;
/* 225:    */       }
/* 226:599 */       if (((ch0 == 'n') || (ch0 == 'N')) && ((ch1 == 'o') || (ch1 == 'O'))) {
/* 227:602 */         return Boolean.FALSE;
/* 228:    */       }
/* 229:    */       break;
/* 230:    */     case 3: 
/* 231:607 */       char ch0 = str.charAt(0);
/* 232:608 */       char ch1 = str.charAt(1);
/* 233:609 */       char ch2 = str.charAt(2);
/* 234:610 */       if (((ch0 == 'y') || (ch0 == 'Y')) && ((ch1 == 'e') || (ch1 == 'E')) && ((ch2 == 's') || (ch2 == 'S'))) {
/* 235:614 */         return Boolean.TRUE;
/* 236:    */       }
/* 237:616 */       if (((ch0 == 'o') || (ch0 == 'O')) && ((ch1 == 'f') || (ch1 == 'F')) && ((ch2 == 'f') || (ch2 == 'F'))) {
/* 238:620 */         return Boolean.FALSE;
/* 239:    */       }
/* 240:    */       break;
/* 241:    */     case 4: 
/* 242:625 */       char ch0 = str.charAt(0);
/* 243:626 */       char ch1 = str.charAt(1);
/* 244:627 */       char ch2 = str.charAt(2);
/* 245:628 */       char ch3 = str.charAt(3);
/* 246:629 */       if (((ch0 == 't') || (ch0 == 'T')) && ((ch1 == 'r') || (ch1 == 'R')) && ((ch2 == 'u') || (ch2 == 'U')) && ((ch3 == 'e') || (ch3 == 'E'))) {
/* 247:634 */         return Boolean.TRUE;
/* 248:    */       }
/* 249:    */       break;
/* 250:    */     case 5: 
/* 251:639 */       char ch0 = str.charAt(0);
/* 252:640 */       char ch1 = str.charAt(1);
/* 253:641 */       char ch2 = str.charAt(2);
/* 254:642 */       char ch3 = str.charAt(3);
/* 255:643 */       char ch4 = str.charAt(4);
/* 256:644 */       if (((ch0 == 'f') || (ch0 == 'F')) && ((ch1 == 'a') || (ch1 == 'A')) && ((ch2 == 'l') || (ch2 == 'L')) && ((ch3 == 's') || (ch3 == 'S')) && ((ch4 == 'e') || (ch4 == 'E'))) {
/* 257:650 */         return Boolean.FALSE;
/* 258:    */       }
/* 259:    */       break;
/* 260:    */     }
/* 261:656 */     return null;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public static Boolean toBooleanObject(String str, String trueString, String falseString, String nullString)
/* 265:    */   {
/* 266:682 */     if (str == null)
/* 267:    */     {
/* 268:683 */       if (trueString == null) {
/* 269:684 */         return Boolean.TRUE;
/* 270:    */       }
/* 271:685 */       if (falseString == null) {
/* 272:686 */         return Boolean.FALSE;
/* 273:    */       }
/* 274:687 */       if (nullString == null) {
/* 275:688 */         return null;
/* 276:    */       }
/* 277:    */     }
/* 278:    */     else
/* 279:    */     {
/* 280:690 */       if (str.equals(trueString)) {
/* 281:691 */         return Boolean.TRUE;
/* 282:    */       }
/* 283:692 */       if (str.equals(falseString)) {
/* 284:693 */         return Boolean.FALSE;
/* 285:    */       }
/* 286:694 */       if (str.equals(nullString)) {
/* 287:695 */         return null;
/* 288:    */       }
/* 289:    */     }
/* 290:698 */     throw new IllegalArgumentException("The String did not match any specified value");
/* 291:    */   }
/* 292:    */   
/* 293:    */   public static boolean toBoolean(String str)
/* 294:    */   {
/* 295:729 */     return toBoolean(toBooleanObject(str));
/* 296:    */   }
/* 297:    */   
/* 298:    */   public static boolean toBoolean(String str, String trueString, String falseString)
/* 299:    */   {
/* 300:751 */     if (str == null)
/* 301:    */     {
/* 302:752 */       if (trueString == null) {
/* 303:753 */         return true;
/* 304:    */       }
/* 305:754 */       if (falseString == null) {
/* 306:755 */         return false;
/* 307:    */       }
/* 308:    */     }
/* 309:    */     else
/* 310:    */     {
/* 311:757 */       if (str.equals(trueString)) {
/* 312:758 */         return true;
/* 313:    */       }
/* 314:759 */       if (str.equals(falseString)) {
/* 315:760 */         return false;
/* 316:    */       }
/* 317:    */     }
/* 318:763 */     throw new IllegalArgumentException("The String did not match either specified value");
/* 319:    */   }
/* 320:    */   
/* 321:    */   public static String toStringTrueFalse(Boolean bool)
/* 322:    */   {
/* 323:783 */     return toString(bool, "true", "false", null);
/* 324:    */   }
/* 325:    */   
/* 326:    */   public static String toStringOnOff(Boolean bool)
/* 327:    */   {
/* 328:801 */     return toString(bool, "on", "off", null);
/* 329:    */   }
/* 330:    */   
/* 331:    */   public static String toStringYesNo(Boolean bool)
/* 332:    */   {
/* 333:819 */     return toString(bool, "yes", "no", null);
/* 334:    */   }
/* 335:    */   
/* 336:    */   public static String toString(Boolean bool, String trueString, String falseString, String nullString)
/* 337:    */   {
/* 338:841 */     if (bool == null) {
/* 339:842 */       return nullString;
/* 340:    */     }
/* 341:844 */     return bool.booleanValue() ? trueString : falseString;
/* 342:    */   }
/* 343:    */   
/* 344:    */   public static String toStringTrueFalse(boolean bool)
/* 345:    */   {
/* 346:863 */     return toString(bool, "true", "false");
/* 347:    */   }
/* 348:    */   
/* 349:    */   public static String toStringOnOff(boolean bool)
/* 350:    */   {
/* 351:880 */     return toString(bool, "on", "off");
/* 352:    */   }
/* 353:    */   
/* 354:    */   public static String toStringYesNo(boolean bool)
/* 355:    */   {
/* 356:897 */     return toString(bool, "yes", "no");
/* 357:    */   }
/* 358:    */   
/* 359:    */   public static String toString(boolean bool, String trueString, String falseString)
/* 360:    */   {
/* 361:916 */     return bool ? trueString : falseString;
/* 362:    */   }
/* 363:    */   
/* 364:    */   public static boolean xor(boolean[] array)
/* 365:    */   {
/* 366:937 */     if (array == null) {
/* 367:938 */       throw new IllegalArgumentException("The Array must not be null");
/* 368:    */     }
/* 369:939 */     if (array.length == 0) {
/* 370:940 */       throw new IllegalArgumentException("Array is empty");
/* 371:    */     }
/* 372:944 */     int trueCount = 0;
/* 373:945 */     for (int i = 0; i < array.length; i++) {
/* 374:948 */       if (array[i] != 0) {
/* 375:949 */         if (trueCount < 1) {
/* 376:950 */           trueCount++;
/* 377:    */         } else {
/* 378:952 */           return false;
/* 379:    */         }
/* 380:    */       }
/* 381:    */     }
/* 382:958 */     return trueCount == 1;
/* 383:    */   }
/* 384:    */   
/* 385:    */   public static Boolean xor(Boolean[] array)
/* 386:    */   {
/* 387:977 */     if (array == null) {
/* 388:978 */       throw new IllegalArgumentException("The Array must not be null");
/* 389:    */     }
/* 390:979 */     if (array.length == 0) {
/* 391:980 */       throw new IllegalArgumentException("Array is empty");
/* 392:    */     }
/* 393:982 */     boolean[] primitive = null;
/* 394:    */     try
/* 395:    */     {
/* 396:984 */       primitive = ArrayUtils.toPrimitive(array);
/* 397:    */     }
/* 398:    */     catch (NullPointerException ex)
/* 399:    */     {
/* 400:986 */       throw new IllegalArgumentException("The array must not contain any null elements");
/* 401:    */     }
/* 402:988 */     return xor(primitive) ? Boolean.TRUE : Boolean.FALSE;
/* 403:    */   }
/* 404:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.lang.BooleanUtils
 * JD-Core Version:    0.7.0.1
 */