/*   1:    */ package org.apache.xml.utils;
/*   2:    */ 
/*   3:    */ public class XMLChar
/*   4:    */ {
/*   5: 54 */   private static final byte[] CHARS = new byte[65536];
/*   6:    */   public static final int MASK_VALID = 1;
/*   7:    */   public static final int MASK_SPACE = 2;
/*   8:    */   public static final int MASK_NAME_START = 4;
/*   9:    */   public static final int MASK_NAME = 8;
/*  10:    */   public static final int MASK_PUBID = 16;
/*  11:    */   public static final int MASK_CONTENT = 32;
/*  12:    */   public static final int MASK_NCNAME_START = 64;
/*  13:    */   public static final int MASK_NCNAME = 128;
/*  14:    */   
/*  15:    */   static
/*  16:    */   {
/*  17: 98 */     int[] charRange = { 9, 10, 13, 13, 32, 55295, 57344, 65533 };
/*  18:    */     
/*  19:    */ 
/*  20:    */ 
/*  21:    */ 
/*  22:    */ 
/*  23:    */ 
/*  24:    */ 
/*  25:106 */     int[] spaceChar = { 32, 9, 13, 10 };
/*  26:    */     
/*  27:    */ 
/*  28:    */ 
/*  29:    */ 
/*  30:    */ 
/*  31:    */ 
/*  32:    */ 
/*  33:    */ 
/*  34:115 */     int[] nameChar = { 45, 46 };
/*  35:    */     
/*  36:    */ 
/*  37:    */ 
/*  38:    */ 
/*  39:    */ 
/*  40:    */ 
/*  41:    */ 
/*  42:123 */     int[] nameStartChar = { 58, 95 };
/*  43:    */     
/*  44:    */ 
/*  45:    */ 
/*  46:    */ 
/*  47:    */ 
/*  48:    */ 
/*  49:    */ 
/*  50:131 */     int[] pubidChar = { 10, 13, 32, 33, 35, 36, 37, 61, 95 };
/*  51:    */     
/*  52:    */ 
/*  53:    */ 
/*  54:    */ 
/*  55:136 */     int[] pubidRange = { 39, 59, 63, 90, 97, 122 };
/*  56:    */     
/*  57:    */ 
/*  58:    */ 
/*  59:    */ 
/*  60:    */ 
/*  61:    */ 
/*  62:    */ 
/*  63:144 */     int[] letterRange = { 65, 90, 97, 122, 192, 214, 216, 246, 248, 305, 308, 318, 321, 328, 330, 382, 384, 451, 461, 496, 500, 501, 506, 535, 592, 680, 699, 705, 904, 906, 910, 929, 931, 974, 976, 982, 994, 1011, 1025, 1036, 1038, 1103, 1105, 1116, 1118, 1153, 1168, 1220, 1223, 1224, 1227, 1228, 1232, 1259, 1262, 1269, 1272, 1273, 1329, 1366, 1377, 1414, 1488, 1514, 1520, 1522, 1569, 1594, 1601, 1610, 1649, 1719, 1722, 1726, 1728, 1742, 1744, 1747, 1765, 1766, 2309, 2361, 2392, 2401, 2437, 2444, 2447, 2448, 2451, 2472, 2474, 2480, 2486, 2489, 2524, 2525, 2527, 2529, 2544, 2545, 2565, 2570, 2575, 2576, 2579, 2600, 2602, 2608, 2610, 2611, 2613, 2614, 2616, 2617, 2649, 2652, 2674, 2676, 2693, 2699, 2703, 2705, 2707, 2728, 2730, 2736, 2738, 2739, 2741, 2745, 2821, 2828, 2831, 2832, 2835, 2856, 2858, 2864, 2866, 2867, 2870, 2873, 2908, 2909, 2911, 2913, 2949, 2954, 2958, 2960, 2962, 2965, 2969, 2970, 2974, 2975, 2979, 2980, 2984, 2986, 2990, 2997, 2999, 3001, 3077, 3084, 3086, 3088, 3090, 3112, 3114, 3123, 3125, 3129, 3168, 3169, 3205, 3212, 3214, 3216, 3218, 3240, 3242, 3251, 3253, 3257, 3296, 3297, 3333, 3340, 3342, 3344, 3346, 3368, 3370, 3385, 3424, 3425, 3585, 3630, 3634, 3635, 3648, 3653, 3713, 3714, 3719, 3720, 3732, 3735, 3737, 3743, 3745, 3747, 3754, 3755, 3757, 3758, 3762, 3763, 3776, 3780, 3904, 3911, 3913, 3945, 4256, 4293, 4304, 4342, 4354, 4355, 4357, 4359, 4363, 4364, 4366, 4370, 4436, 4437, 4447, 4449, 4461, 4462, 4466, 4467, 4526, 4527, 4535, 4536, 4540, 4546, 7680, 7835, 7840, 7929, 7936, 7957, 7960, 7965, 7968, 8005, 8008, 8013, 8016, 8023, 8031, 8061, 8064, 8116, 8118, 8124, 8130, 8132, 8134, 8140, 8144, 8147, 8150, 8155, 8160, 8172, 8178, 8180, 8182, 8188, 8490, 8491, 8576, 8578, 12353, 12436, 12449, 12538, 12549, 12588, 44032, 55203, 12321, 12329, 19968, 40869 };
/*  64:    */     
/*  65:    */ 
/*  66:    */ 
/*  67:    */ 
/*  68:    */ 
/*  69:    */ 
/*  70:    */ 
/*  71:    */ 
/*  72:    */ 
/*  73:    */ 
/*  74:    */ 
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
/*  93:    */ 
/*  94:    */ 
/*  95:    */ 
/*  96:    */ 
/*  97:    */ 
/*  98:    */ 
/*  99:    */ 
/* 100:    */ 
/* 101:    */ 
/* 102:    */ 
/* 103:    */ 
/* 104:    */ 
/* 105:    */ 
/* 106:187 */     int[] letterChar = { 902, 908, 986, 988, 990, 992, 1369, 1749, 2365, 2482, 2654, 2701, 2749, 2784, 2877, 2972, 3294, 3632, 3716, 3722, 3725, 3749, 3751, 3760, 3773, 4352, 4361, 4412, 4414, 4416, 4428, 4430, 4432, 4441, 4451, 4453, 4455, 4457, 4469, 4510, 4520, 4523, 4538, 4587, 4592, 4601, 8025, 8027, 8029, 8126, 8486, 8494, 12295 };
/* 107:    */     
/* 108:    */ 
/* 109:    */ 
/* 110:    */ 
/* 111:    */ 
/* 112:    */ 
/* 113:    */ 
/* 114:    */ 
/* 115:    */ 
/* 116:    */ 
/* 117:    */ 
/* 118:    */ 
/* 119:    */ 
/* 120:    */ 
/* 121:    */ 
/* 122:    */ 
/* 123:204 */     int[] combiningCharRange = { 768, 837, 864, 865, 1155, 1158, 1425, 1441, 1443, 1465, 1467, 1469, 1473, 1474, 1611, 1618, 1750, 1756, 1757, 1759, 1760, 1764, 1767, 1768, 1770, 1773, 2305, 2307, 2366, 2380, 2385, 2388, 2402, 2403, 2433, 2435, 2496, 2500, 2503, 2504, 2507, 2509, 2530, 2531, 2624, 2626, 2631, 2632, 2635, 2637, 2672, 2673, 2689, 2691, 2750, 2757, 2759, 2761, 2763, 2765, 2817, 2819, 2878, 2883, 2887, 2888, 2891, 2893, 2902, 2903, 2946, 2947, 3006, 3010, 3014, 3016, 3018, 3021, 3073, 3075, 3134, 3140, 3142, 3144, 3146, 3149, 3157, 3158, 3202, 3203, 3262, 3268, 3270, 3272, 3274, 3277, 3285, 3286, 3330, 3331, 3390, 3395, 3398, 3400, 3402, 3405, 3636, 3642, 3655, 3662, 3764, 3769, 3771, 3772, 3784, 3789, 3864, 3865, 3953, 3972, 3974, 3979, 3984, 3989, 3993, 4013, 4017, 4023, 8400, 8412, 12330, 12335 };
/* 124:    */     
/* 125:    */ 
/* 126:    */ 
/* 127:    */ 
/* 128:    */ 
/* 129:    */ 
/* 130:    */ 
/* 131:    */ 
/* 132:    */ 
/* 133:    */ 
/* 134:    */ 
/* 135:    */ 
/* 136:    */ 
/* 137:    */ 
/* 138:    */ 
/* 139:    */ 
/* 140:    */ 
/* 141:    */ 
/* 142:    */ 
/* 143:224 */     int[] combiningCharChar = { 1471, 1476, 1648, 2364, 2381, 2492, 2494, 2495, 2519, 2562, 2620, 2622, 2623, 2748, 2876, 3031, 3415, 3633, 3761, 3893, 3895, 3897, 3902, 3903, 3991, 4025, 8417, 12441, 12442 };
/* 144:    */     
/* 145:    */ 
/* 146:    */ 
/* 147:    */ 
/* 148:    */ 
/* 149:    */ 
/* 150:    */ 
/* 151:    */ 
/* 152:    */ 
/* 153:    */ 
/* 154:235 */     int[] digitRange = { 48, 57, 1632, 1641, 1776, 1785, 2406, 2415, 2534, 2543, 2662, 2671, 2790, 2799, 2918, 2927, 3047, 3055, 3174, 3183, 3302, 3311, 3430, 3439, 3664, 3673, 3792, 3801, 3872, 3881 };
/* 155:    */     
/* 156:    */ 
/* 157:    */ 
/* 158:    */ 
/* 159:    */ 
/* 160:    */ 
/* 161:    */ 
/* 162:    */ 
/* 163:    */ 
/* 164:    */ 
/* 165:246 */     int[] extenderRange = { 12337, 12341, 12445, 12446, 12540, 12542 };
/* 166:    */     
/* 167:    */ 
/* 168:    */ 
/* 169:250 */     int[] extenderChar = { 183, 720, 721, 903, 1600, 3654, 3782, 12293 };
/* 170:    */     
/* 171:    */ 
/* 172:    */ 
/* 173:    */ 
/* 174:    */ 
/* 175:    */ 
/* 176:    */ 
/* 177:258 */     int[] specialChar = { 60, 38, 10, 13, 93 };
/* 178:267 */     for (int i = 0; i < charRange.length; i += 2) {
/* 179:268 */       for (int j = charRange[i]; j <= charRange[(i + 1)]; j++)
/* 180:    */       {
/* 181:269 */         int tmp4337_4335 = j; byte[] tmp4337_4332 = CHARS;tmp4337_4332[tmp4337_4335] = ((byte)(tmp4337_4332[tmp4337_4335] | 0x21));
/* 182:    */       }
/* 183:    */     }
/* 184:274 */     for (int i = 0; i < specialChar.length; i++) {
/* 185:275 */       CHARS[specialChar[i]] = ((byte)(CHARS[specialChar[i]] & 0xFFFFFFDF));
/* 186:    */     }
/* 187:279 */     for (int i = 0; i < spaceChar.length; i++)
/* 188:    */     {
/* 189:280 */       int tmp4420_4419 = spaceChar[i]; byte[] tmp4420_4413 = CHARS;tmp4420_4413[tmp4420_4419] = ((byte)(tmp4420_4413[tmp4420_4419] | 0x2));
/* 190:    */     }
/* 191:284 */     for (int i = 0; i < nameStartChar.length; i++)
/* 192:    */     {
/* 193:285 */       int tmp4449_4448 = nameStartChar[i]; byte[] tmp4449_4442 = CHARS;tmp4449_4442[tmp4449_4448] = ((byte)(tmp4449_4442[tmp4449_4448] | 0xCC));
/* 194:    */     }
/* 195:288 */     for (int i = 0; i < letterRange.length; i += 2) {
/* 196:289 */       for (int j = letterRange[i]; j <= letterRange[(i + 1)]; j++)
/* 197:    */       {
/* 198:290 */         int tmp4488_4486 = j; byte[] tmp4488_4483 = CHARS;tmp4488_4483[tmp4488_4486] = ((byte)(tmp4488_4483[tmp4488_4486] | 0xCC));
/* 199:    */       }
/* 200:    */     }
/* 201:294 */     for (int i = 0; i < letterChar.length; i++)
/* 202:    */     {
/* 203:295 */       int tmp4536_4535 = letterChar[i]; byte[] tmp4536_4528 = CHARS;tmp4536_4528[tmp4536_4535] = ((byte)(tmp4536_4528[tmp4536_4535] | 0xCC));
/* 204:    */     }
/* 205:300 */     for (int i = 0; i < nameChar.length; i++)
/* 206:    */     {
/* 207:301 */       int tmp4568_4567 = nameChar[i]; byte[] tmp4568_4561 = CHARS;tmp4568_4561[tmp4568_4567] = ((byte)(tmp4568_4561[tmp4568_4567] | 0x88));
/* 208:    */     }
/* 209:303 */     for (int i = 0; i < digitRange.length; i += 2) {
/* 210:304 */       for (int j = digitRange[i]; j <= digitRange[(i + 1)]; j++)
/* 211:    */       {
/* 212:305 */         int tmp4607_4605 = j; byte[] tmp4607_4602 = CHARS;tmp4607_4602[tmp4607_4605] = ((byte)(tmp4607_4602[tmp4607_4605] | 0x88));
/* 213:    */       }
/* 214:    */     }
/* 215:308 */     for (int i = 0; i < combiningCharRange.length; i += 2) {
/* 216:309 */       for (int j = combiningCharRange[i]; j <= combiningCharRange[(i + 1)]; j++)
/* 217:    */       {
/* 218:310 */         int tmp4662_4660 = j; byte[] tmp4662_4657 = CHARS;tmp4662_4657[tmp4662_4660] = ((byte)(tmp4662_4657[tmp4662_4660] | 0x88));
/* 219:    */       }
/* 220:    */     }
/* 221:313 */     for (int i = 0; i < combiningCharChar.length; i++)
/* 222:    */     {
/* 223:314 */       int tmp4710_4709 = combiningCharChar[i]; byte[] tmp4710_4702 = CHARS;tmp4710_4702[tmp4710_4709] = ((byte)(tmp4710_4702[tmp4710_4709] | 0x88));
/* 224:    */     }
/* 225:316 */     for (int i = 0; i < extenderRange.length; i += 2) {
/* 226:317 */       for (int j = extenderRange[i]; j <= extenderRange[(i + 1)]; j++)
/* 227:    */       {
/* 228:318 */         int tmp4750_4748 = j; byte[] tmp4750_4745 = CHARS;tmp4750_4745[tmp4750_4748] = ((byte)(tmp4750_4745[tmp4750_4748] | 0x88));
/* 229:    */       }
/* 230:    */     }
/* 231:321 */     for (int i = 0; i < extenderChar.length; i++)
/* 232:    */     {
/* 233:322 */       int tmp4798_4797 = extenderChar[i]; byte[] tmp4798_4790 = CHARS;tmp4798_4790[tmp4798_4797] = ((byte)(tmp4798_4790[tmp4798_4797] | 0x88));
/* 234:    */     }
/* 235:326 */     byte[] tmp4822_4817 = CHARS;tmp4822_4817[58] = ((byte)(tmp4822_4817[58] & 0xFFFFFF3F));
/* 236:329 */     for (int i = 0; i < pubidChar.length; i++)
/* 237:    */     {
/* 238:330 */       int tmp4844_4843 = pubidChar[i]; byte[] tmp4844_4836 = CHARS;tmp4844_4836[tmp4844_4843] = ((byte)(tmp4844_4836[tmp4844_4843] | 0x10));
/* 239:    */     }
/* 240:332 */     for (int i = 0; i < pubidRange.length; i += 2) {
/* 241:333 */       for (int j = pubidRange[i]; j <= pubidRange[(i + 1)]; j++)
/* 242:    */       {
/* 243:334 */         int tmp4883_4881 = j; byte[] tmp4883_4878 = CHARS;tmp4883_4878[tmp4883_4881] = ((byte)(tmp4883_4878[tmp4883_4881] | 0x10));
/* 244:    */       }
/* 245:    */     }
/* 246:    */   }
/* 247:    */   
/* 248:    */   public static boolean isSupplemental(int c)
/* 249:    */   {
/* 250:350 */     return (c >= 65536) && (c <= 1114111);
/* 251:    */   }
/* 252:    */   
/* 253:    */   public static int supplemental(char h, char l)
/* 254:    */   {
/* 255:361 */     return (h - 55296) * 1024 + (l - 56320) + 65536;
/* 256:    */   }
/* 257:    */   
/* 258:    */   public static char highSurrogate(int c)
/* 259:    */   {
/* 260:370 */     return (char)((c - 65536 >> 10) + 55296);
/* 261:    */   }
/* 262:    */   
/* 263:    */   public static char lowSurrogate(int c)
/* 264:    */   {
/* 265:379 */     return (char)((c - 65536 & 0x3FF) + 56320);
/* 266:    */   }
/* 267:    */   
/* 268:    */   public static boolean isHighSurrogate(int c)
/* 269:    */   {
/* 270:388 */     return (55296 <= c) && (c <= 56319);
/* 271:    */   }
/* 272:    */   
/* 273:    */   public static boolean isLowSurrogate(int c)
/* 274:    */   {
/* 275:397 */     return (56320 <= c) && (c <= 57343);
/* 276:    */   }
/* 277:    */   
/* 278:    */   public static boolean isValid(int c)
/* 279:    */   {
/* 280:412 */     return ((c < 65536) && ((CHARS[c] & 0x1) != 0)) || ((65536 <= c) && (c <= 1114111));
/* 281:    */   }
/* 282:    */   
/* 283:    */   public static boolean isInvalid(int c)
/* 284:    */   {
/* 285:422 */     return !isValid(c);
/* 286:    */   }
/* 287:    */   
/* 288:    */   public static boolean isContent(int c)
/* 289:    */   {
/* 290:431 */     return ((c < 65536) && ((CHARS[c] & 0x20) != 0)) || ((65536 <= c) && (c <= 1114111));
/* 291:    */   }
/* 292:    */   
/* 293:    */   public static boolean isMarkup(int c)
/* 294:    */   {
/* 295:442 */     return (c == 60) || (c == 38) || (c == 37);
/* 296:    */   }
/* 297:    */   
/* 298:    */   public static boolean isSpace(int c)
/* 299:    */   {
/* 300:452 */     return (c < 65536) && ((CHARS[c] & 0x2) != 0);
/* 301:    */   }
/* 302:    */   
/* 303:    */   public static boolean isNameStart(int c)
/* 304:    */   {
/* 305:463 */     return (c < 65536) && ((CHARS[c] & 0x4) != 0);
/* 306:    */   }
/* 307:    */   
/* 308:    */   public static boolean isName(int c)
/* 309:    */   {
/* 310:474 */     return (c < 65536) && ((CHARS[c] & 0x8) != 0);
/* 311:    */   }
/* 312:    */   
/* 313:    */   public static boolean isNCNameStart(int c)
/* 314:    */   {
/* 315:485 */     return (c < 65536) && ((CHARS[c] & 0x40) != 0);
/* 316:    */   }
/* 317:    */   
/* 318:    */   public static boolean isNCName(int c)
/* 319:    */   {
/* 320:496 */     return (c < 65536) && ((CHARS[c] & 0x80) != 0);
/* 321:    */   }
/* 322:    */   
/* 323:    */   public static boolean isPubid(int c)
/* 324:    */   {
/* 325:507 */     return (c < 65536) && ((CHARS[c] & 0x10) != 0);
/* 326:    */   }
/* 327:    */   
/* 328:    */   public static boolean isValidName(String name)
/* 329:    */   {
/* 330:521 */     if (name.length() == 0) {
/* 331:522 */       return false;
/* 332:    */     }
/* 333:523 */     char ch = name.charAt(0);
/* 334:524 */     if (!isNameStart(ch)) {
/* 335:525 */       return false;
/* 336:    */     }
/* 337:526 */     for (int i = 1; i < name.length(); i++)
/* 338:    */     {
/* 339:527 */       ch = name.charAt(i);
/* 340:528 */       if (!isName(ch)) {
/* 341:529 */         return false;
/* 342:    */       }
/* 343:    */     }
/* 344:532 */     return true;
/* 345:    */   }
/* 346:    */   
/* 347:    */   public static boolean isValidNCName(String ncName)
/* 348:    */   {
/* 349:548 */     if (ncName.length() == 0) {
/* 350:549 */       return false;
/* 351:    */     }
/* 352:550 */     char ch = ncName.charAt(0);
/* 353:551 */     if (!isNCNameStart(ch)) {
/* 354:552 */       return false;
/* 355:    */     }
/* 356:553 */     for (int i = 1; i < ncName.length(); i++)
/* 357:    */     {
/* 358:554 */       ch = ncName.charAt(i);
/* 359:555 */       if (!isNCName(ch)) {
/* 360:556 */         return false;
/* 361:    */       }
/* 362:    */     }
/* 363:559 */     return true;
/* 364:    */   }
/* 365:    */   
/* 366:    */   public static boolean isValidNmtoken(String nmtoken)
/* 367:    */   {
/* 368:573 */     if (nmtoken.length() == 0) {
/* 369:574 */       return false;
/* 370:    */     }
/* 371:575 */     for (int i = 0; i < nmtoken.length(); i++)
/* 372:    */     {
/* 373:576 */       char ch = nmtoken.charAt(i);
/* 374:577 */       if (!isName(ch)) {
/* 375:578 */         return false;
/* 376:    */       }
/* 377:    */     }
/* 378:581 */     return true;
/* 379:    */   }
/* 380:    */   
/* 381:    */   public static boolean isValidIANAEncoding(String ianaEncoding)
/* 382:    */   {
/* 383:599 */     if (ianaEncoding != null)
/* 384:    */     {
/* 385:600 */       int length = ianaEncoding.length();
/* 386:601 */       if (length > 0)
/* 387:    */       {
/* 388:602 */         char c = ianaEncoding.charAt(0);
/* 389:603 */         if (((c >= 'A') && (c <= 'Z')) || ((c >= 'a') && (c <= 'z')))
/* 390:    */         {
/* 391:604 */           for (int i = 1; i < length; i++)
/* 392:    */           {
/* 393:605 */             c = ianaEncoding.charAt(i);
/* 394:606 */             if (((c < 'A') || (c > 'Z')) && ((c < 'a') || (c > 'z')) && ((c < '0') || (c > '9')) && (c != '.') && (c != '_') && (c != '-')) {
/* 395:609 */               return false;
/* 396:    */             }
/* 397:    */           }
/* 398:612 */           return true;
/* 399:    */         }
/* 400:    */       }
/* 401:    */     }
/* 402:616 */     return false;
/* 403:    */   }
/* 404:    */   
/* 405:    */   public static boolean isValidJavaEncoding(String javaEncoding)
/* 406:    */   {
/* 407:628 */     if (javaEncoding != null)
/* 408:    */     {
/* 409:629 */       int length = javaEncoding.length();
/* 410:630 */       if (length > 0)
/* 411:    */       {
/* 412:631 */         for (int i = 1; i < length; i++)
/* 413:    */         {
/* 414:632 */           char c = javaEncoding.charAt(i);
/* 415:633 */           if (((c < 'A') || (c > 'Z')) && ((c < 'a') || (c > 'z')) && ((c < '0') || (c > '9')) && (c != '.') && (c != '_') && (c != '-')) {
/* 416:636 */             return false;
/* 417:    */           }
/* 418:    */         }
/* 419:639 */         return true;
/* 420:    */       }
/* 421:    */     }
/* 422:642 */     return false;
/* 423:    */   }
/* 424:    */   
/* 425:    */   public static boolean isValidQName(String str)
/* 426:    */   {
/* 427:652 */     int colon = str.indexOf(':');
/* 428:654 */     if ((colon == 0) || (colon == str.length() - 1)) {
/* 429:655 */       return false;
/* 430:    */     }
/* 431:658 */     if (colon > 0)
/* 432:    */     {
/* 433:659 */       String prefix = str.substring(0, colon);
/* 434:660 */       String localPart = str.substring(colon + 1);
/* 435:661 */       return (isValidNCName(prefix)) && (isValidNCName(localPart));
/* 436:    */     }
/* 437:664 */     return isValidNCName(str);
/* 438:    */   }
/* 439:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.XMLChar
 * JD-Core Version:    0.7.0.1
 */