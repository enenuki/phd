/*   1:    */ package antlr.collections.impl;
/*   2:    */ 
/*   3:    */ import antlr.CharFormatter;
/*   4:    */ 
/*   5:    */ public class BitSet
/*   6:    */   implements Cloneable
/*   7:    */ {
/*   8:    */   protected static final int BITS = 64;
/*   9:    */   protected static final int NIBBLE = 4;
/*  10:    */   protected static final int LOG_BITS = 6;
/*  11:    */   protected static final int MOD_MASK = 63;
/*  12:    */   protected long[] bits;
/*  13:    */   
/*  14:    */   public BitSet()
/*  15:    */   {
/*  16: 44 */     this(64);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public BitSet(long[] paramArrayOfLong)
/*  20:    */   {
/*  21: 49 */     this.bits = paramArrayOfLong;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public BitSet(int paramInt)
/*  25:    */   {
/*  26: 56 */     this.bits = new long[(paramInt - 1 >> 6) + 1];
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void add(int paramInt)
/*  30:    */   {
/*  31: 62 */     int i = wordNumber(paramInt);
/*  32: 65 */     if (i >= this.bits.length) {
/*  33: 66 */       growToInclude(paramInt);
/*  34:    */     }
/*  35: 68 */     this.bits[i] |= bitMask(paramInt);
/*  36:    */   }
/*  37:    */   
/*  38:    */   public BitSet and(BitSet paramBitSet)
/*  39:    */   {
/*  40: 72 */     BitSet localBitSet = (BitSet)clone();
/*  41: 73 */     localBitSet.andInPlace(paramBitSet);
/*  42: 74 */     return localBitSet;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void andInPlace(BitSet paramBitSet)
/*  46:    */   {
/*  47: 78 */     int i = Math.min(this.bits.length, paramBitSet.bits.length);
/*  48: 79 */     for (int j = i - 1; j >= 0; j--) {
/*  49: 80 */       this.bits[j] &= paramBitSet.bits[j];
/*  50:    */     }
/*  51: 83 */     for (j = i; j < this.bits.length; j++) {
/*  52: 84 */       this.bits[j] = 0L;
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   private static final long bitMask(int paramInt)
/*  57:    */   {
/*  58: 89 */     int i = paramInt & 0x3F;
/*  59: 90 */     return 1L << i;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void clear()
/*  63:    */   {
/*  64: 94 */     for (int i = this.bits.length - 1; i >= 0; i--) {
/*  65: 95 */       this.bits[i] = 0L;
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void clear(int paramInt)
/*  70:    */   {
/*  71:100 */     int i = wordNumber(paramInt);
/*  72:101 */     if (i >= this.bits.length) {
/*  73:102 */       growToInclude(paramInt);
/*  74:    */     }
/*  75:104 */     this.bits[i] &= (bitMask(paramInt) ^ 0xFFFFFFFF);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public Object clone()
/*  79:    */   {
/*  80:    */     BitSet localBitSet;
/*  81:    */     try
/*  82:    */     {
/*  83:110 */       localBitSet = (BitSet)super.clone();
/*  84:111 */       localBitSet.bits = new long[this.bits.length];
/*  85:112 */       System.arraycopy(this.bits, 0, localBitSet.bits, 0, this.bits.length);
/*  86:    */     }
/*  87:    */     catch (CloneNotSupportedException localCloneNotSupportedException)
/*  88:    */     {
/*  89:115 */       throw new InternalError();
/*  90:    */     }
/*  91:117 */     return localBitSet;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public int degree()
/*  95:    */   {
/*  96:121 */     int i = 0;
/*  97:122 */     for (int j = this.bits.length - 1; j >= 0; j--)
/*  98:    */     {
/*  99:123 */       long l = this.bits[j];
/* 100:124 */       if (l != 0L) {
/* 101:125 */         for (int k = 63; k >= 0; k--) {
/* 102:126 */           if ((l & 1L << k) != 0L) {
/* 103:127 */             i++;
/* 104:    */           }
/* 105:    */         }
/* 106:    */       }
/* 107:    */     }
/* 108:132 */     return i;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public boolean equals(Object paramObject)
/* 112:    */   {
/* 113:137 */     if ((paramObject != null) && ((paramObject instanceof BitSet)))
/* 114:    */     {
/* 115:138 */       BitSet localBitSet = (BitSet)paramObject;
/* 116:    */       
/* 117:140 */       int i = Math.min(this.bits.length, localBitSet.bits.length);
/* 118:141 */       for (int j = i; j-- > 0;) {
/* 119:142 */         if (this.bits[j] != localBitSet.bits[j]) {
/* 120:143 */           return false;
/* 121:    */         }
/* 122:    */       }
/* 123:146 */       if (this.bits.length > i)
/* 124:    */       {
/* 125:147 */         j = this.bits.length;
/* 126:    */         do
/* 127:    */         {
/* 128:147 */           if (j-- <= i) {
/* 129:    */             break;
/* 130:    */           }
/* 131:148 */         } while (this.bits[j] == 0L);
/* 132:149 */         return false;
/* 133:    */       }
/* 134:153 */       if (localBitSet.bits.length > i) {
/* 135:154 */         for (j = localBitSet.bits.length; j-- > i;) {
/* 136:155 */           if (localBitSet.bits[j] != 0L) {
/* 137:156 */             return false;
/* 138:    */           }
/* 139:    */         }
/* 140:    */       }
/* 141:160 */       return true;
/* 142:    */     }
/* 143:162 */     return false;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public static Vector getRanges(int[] paramArrayOfInt)
/* 147:    */   {
/* 148:170 */     if (paramArrayOfInt.length == 0) {
/* 149:171 */       return null;
/* 150:    */     }
/* 151:173 */     int i = paramArrayOfInt[0];
/* 152:174 */     int j = paramArrayOfInt[(paramArrayOfInt.length - 1)];
/* 153:175 */     if (paramArrayOfInt.length <= 2) {
/* 154:177 */       return null;
/* 155:    */     }
/* 156:180 */     Vector localVector = new Vector(5);
/* 157:182 */     for (int k = 0; k < paramArrayOfInt.length - 2; k++)
/* 158:    */     {
/* 159:184 */       int m = paramArrayOfInt.length - 1;
/* 160:185 */       for (int n = k + 1; n < paramArrayOfInt.length; n++) {
/* 161:186 */         if (paramArrayOfInt[n] != paramArrayOfInt[(n - 1)] + 1)
/* 162:    */         {
/* 163:187 */           m = n - 1;
/* 164:188 */           break;
/* 165:    */         }
/* 166:    */       }
/* 167:192 */       if (m - k > 2) {
/* 168:193 */         localVector.appendElement(new IntRange(paramArrayOfInt[k], paramArrayOfInt[m]));
/* 169:    */       }
/* 170:    */     }
/* 171:196 */     return localVector;
/* 172:    */   }
/* 173:    */   
/* 174:    */   public void growToInclude(int paramInt)
/* 175:    */   {
/* 176:204 */     int i = Math.max(this.bits.length << 1, numWordsToHold(paramInt));
/* 177:205 */     long[] arrayOfLong = new long[i];
/* 178:206 */     System.arraycopy(this.bits, 0, arrayOfLong, 0, this.bits.length);
/* 179:207 */     this.bits = arrayOfLong;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public boolean member(int paramInt)
/* 183:    */   {
/* 184:211 */     int i = wordNumber(paramInt);
/* 185:212 */     if (i >= this.bits.length) {
/* 186:212 */       return false;
/* 187:    */     }
/* 188:213 */     return (this.bits[i] & bitMask(paramInt)) != 0L;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public boolean nil()
/* 192:    */   {
/* 193:217 */     for (int i = this.bits.length - 1; i >= 0; i--) {
/* 194:218 */       if (this.bits[i] != 0L) {
/* 195:218 */         return false;
/* 196:    */       }
/* 197:    */     }
/* 198:220 */     return true;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public BitSet not()
/* 202:    */   {
/* 203:224 */     BitSet localBitSet = (BitSet)clone();
/* 204:225 */     localBitSet.notInPlace();
/* 205:226 */     return localBitSet;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void notInPlace()
/* 209:    */   {
/* 210:230 */     for (int i = this.bits.length - 1; i >= 0; i--) {
/* 211:231 */       this.bits[i] ^= 0xFFFFFFFF;
/* 212:    */     }
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void notInPlace(int paramInt)
/* 216:    */   {
/* 217:237 */     notInPlace(0, paramInt);
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void notInPlace(int paramInt1, int paramInt2)
/* 221:    */   {
/* 222:243 */     growToInclude(paramInt2);
/* 223:244 */     for (int i = paramInt1; i <= paramInt2; i++)
/* 224:    */     {
/* 225:245 */       int j = wordNumber(i);
/* 226:246 */       this.bits[j] ^= bitMask(i);
/* 227:    */     }
/* 228:    */   }
/* 229:    */   
/* 230:    */   private final int numWordsToHold(int paramInt)
/* 231:    */   {
/* 232:251 */     return (paramInt >> 6) + 1;
/* 233:    */   }
/* 234:    */   
/* 235:    */   public static BitSet of(int paramInt)
/* 236:    */   {
/* 237:255 */     BitSet localBitSet = new BitSet(paramInt + 1);
/* 238:256 */     localBitSet.add(paramInt);
/* 239:257 */     return localBitSet;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public BitSet or(BitSet paramBitSet)
/* 243:    */   {
/* 244:262 */     BitSet localBitSet = (BitSet)clone();
/* 245:263 */     localBitSet.orInPlace(paramBitSet);
/* 246:264 */     return localBitSet;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public void orInPlace(BitSet paramBitSet)
/* 250:    */   {
/* 251:269 */     if (paramBitSet.bits.length > this.bits.length) {
/* 252:270 */       setSize(paramBitSet.bits.length);
/* 253:    */     }
/* 254:272 */     int i = Math.min(this.bits.length, paramBitSet.bits.length);
/* 255:273 */     for (int j = i - 1; j >= 0; j--) {
/* 256:274 */       this.bits[j] |= paramBitSet.bits[j];
/* 257:    */     }
/* 258:    */   }
/* 259:    */   
/* 260:    */   public void remove(int paramInt)
/* 261:    */   {
/* 262:280 */     int i = wordNumber(paramInt);
/* 263:281 */     if (i >= this.bits.length) {
/* 264:282 */       growToInclude(paramInt);
/* 265:    */     }
/* 266:284 */     this.bits[i] &= (bitMask(paramInt) ^ 0xFFFFFFFF);
/* 267:    */   }
/* 268:    */   
/* 269:    */   private void setSize(int paramInt)
/* 270:    */   {
/* 271:292 */     long[] arrayOfLong = new long[paramInt];
/* 272:293 */     int i = Math.min(paramInt, this.bits.length);
/* 273:294 */     System.arraycopy(this.bits, 0, arrayOfLong, 0, i);
/* 274:295 */     this.bits = arrayOfLong;
/* 275:    */   }
/* 276:    */   
/* 277:    */   public int size()
/* 278:    */   {
/* 279:299 */     return this.bits.length << 6;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public int lengthInLongWords()
/* 283:    */   {
/* 284:306 */     return this.bits.length;
/* 285:    */   }
/* 286:    */   
/* 287:    */   public boolean subset(BitSet paramBitSet)
/* 288:    */   {
/* 289:311 */     if ((paramBitSet == null) || (!(paramBitSet instanceof BitSet))) {
/* 290:311 */       return false;
/* 291:    */     }
/* 292:312 */     return and(paramBitSet).equals(this);
/* 293:    */   }
/* 294:    */   
/* 295:    */   public void subtractInPlace(BitSet paramBitSet)
/* 296:    */   {
/* 297:319 */     if (paramBitSet == null) {
/* 298:319 */       return;
/* 299:    */     }
/* 300:321 */     for (int i = 0; (i < this.bits.length) && (i < paramBitSet.bits.length); i++) {
/* 301:322 */       this.bits[i] &= (paramBitSet.bits[i] ^ 0xFFFFFFFF);
/* 302:    */     }
/* 303:    */   }
/* 304:    */   
/* 305:    */   public int[] toArray()
/* 306:    */   {
/* 307:327 */     int[] arrayOfInt = new int[degree()];
/* 308:328 */     int i = 0;
/* 309:329 */     for (int j = 0; j < this.bits.length << 6; j++) {
/* 310:330 */       if (member(j)) {
/* 311:331 */         arrayOfInt[(i++)] = j;
/* 312:    */       }
/* 313:    */     }
/* 314:334 */     return arrayOfInt;
/* 315:    */   }
/* 316:    */   
/* 317:    */   public long[] toPackedArray()
/* 318:    */   {
/* 319:338 */     return this.bits;
/* 320:    */   }
/* 321:    */   
/* 322:    */   public String toString()
/* 323:    */   {
/* 324:342 */     return toString(",");
/* 325:    */   }
/* 326:    */   
/* 327:    */   public String toString(String paramString)
/* 328:    */   {
/* 329:350 */     String str = "";
/* 330:351 */     for (int i = 0; i < this.bits.length << 6; i++) {
/* 331:352 */       if (member(i))
/* 332:    */       {
/* 333:353 */         if (str.length() > 0) {
/* 334:354 */           str = str + paramString;
/* 335:    */         }
/* 336:356 */         str = str + i;
/* 337:    */       }
/* 338:    */     }
/* 339:359 */     return str;
/* 340:    */   }
/* 341:    */   
/* 342:    */   public String toString(String paramString, CharFormatter paramCharFormatter)
/* 343:    */   {
/* 344:368 */     String str = "";
/* 345:370 */     for (int i = 0; i < this.bits.length << 6; i++) {
/* 346:371 */       if (member(i))
/* 347:    */       {
/* 348:372 */         if (str.length() > 0) {
/* 349:373 */           str = str + paramString;
/* 350:    */         }
/* 351:375 */         str = str + paramCharFormatter.literalChar(i);
/* 352:    */       }
/* 353:    */     }
/* 354:378 */     return str;
/* 355:    */   }
/* 356:    */   
/* 357:    */   public String toString(String paramString, Vector paramVector)
/* 358:    */   {
/* 359:388 */     if (paramVector == null) {
/* 360:389 */       return toString(paramString);
/* 361:    */     }
/* 362:391 */     String str = "";
/* 363:392 */     for (int i = 0; i < this.bits.length << 6; i++) {
/* 364:393 */       if (member(i))
/* 365:    */       {
/* 366:394 */         if (str.length() > 0) {
/* 367:395 */           str = str + paramString;
/* 368:    */         }
/* 369:397 */         if (i >= paramVector.size()) {
/* 370:398 */           str = str + "<bad element " + i + ">";
/* 371:400 */         } else if (paramVector.elementAt(i) == null) {
/* 372:401 */           str = str + "<" + i + ">";
/* 373:    */         } else {
/* 374:404 */           str = str + (String)paramVector.elementAt(i);
/* 375:    */         }
/* 376:    */       }
/* 377:    */     }
/* 378:408 */     return str;
/* 379:    */   }
/* 380:    */   
/* 381:    */   public String toStringOfHalfWords()
/* 382:    */   {
/* 383:417 */     String str = new String();
/* 384:418 */     for (int i = 0; i < this.bits.length; i++)
/* 385:    */     {
/* 386:419 */       if (i != 0) {
/* 387:419 */         str = str + ", ";
/* 388:    */       }
/* 389:420 */       long l = this.bits[i];
/* 390:421 */       l &= 0xFFFFFFFF;
/* 391:422 */       str = str + l + "UL";
/* 392:423 */       str = str + ", ";
/* 393:424 */       l = this.bits[i] >>> 32;
/* 394:425 */       l &= 0xFFFFFFFF;
/* 395:426 */       str = str + l + "UL";
/* 396:    */     }
/* 397:428 */     return str;
/* 398:    */   }
/* 399:    */   
/* 400:    */   public String toStringOfWords()
/* 401:    */   {
/* 402:436 */     String str = new String();
/* 403:437 */     for (int i = 0; i < this.bits.length; i++)
/* 404:    */     {
/* 405:438 */       if (i != 0) {
/* 406:438 */         str = str + ", ";
/* 407:    */       }
/* 408:439 */       str = str + this.bits[i] + "L";
/* 409:    */     }
/* 410:441 */     return str;
/* 411:    */   }
/* 412:    */   
/* 413:    */   public String toStringWithRanges(String paramString, CharFormatter paramCharFormatter)
/* 414:    */   {
/* 415:446 */     String str = "";
/* 416:447 */     int[] arrayOfInt = toArray();
/* 417:448 */     if (arrayOfInt.length == 0) {
/* 418:449 */       return "";
/* 419:    */     }
/* 420:452 */     int i = 0;
/* 421:453 */     while (i < arrayOfInt.length)
/* 422:    */     {
/* 423:455 */       int j = 0;
/* 424:456 */       for (int k = i + 1; k < arrayOfInt.length; k++)
/* 425:    */       {
/* 426:457 */         if (arrayOfInt[k] != arrayOfInt[(k - 1)] + 1) {
/* 427:    */           break;
/* 428:    */         }
/* 429:460 */         j = k;
/* 430:    */       }
/* 431:463 */       if (str.length() > 0) {
/* 432:464 */         str = str + paramString;
/* 433:    */       }
/* 434:466 */       if (j - i >= 2)
/* 435:    */       {
/* 436:467 */         str = str + paramCharFormatter.literalChar(arrayOfInt[i]);
/* 437:468 */         str = str + "..";
/* 438:469 */         str = str + paramCharFormatter.literalChar(arrayOfInt[j]);
/* 439:470 */         i = j;
/* 440:    */       }
/* 441:    */       else
/* 442:    */       {
/* 443:473 */         str = str + paramCharFormatter.literalChar(arrayOfInt[i]);
/* 444:    */       }
/* 445:475 */       i++;
/* 446:    */     }
/* 447:477 */     return str;
/* 448:    */   }
/* 449:    */   
/* 450:    */   private static final int wordNumber(int paramInt)
/* 451:    */   {
/* 452:481 */     return paramInt >> 6;
/* 453:    */   }
/* 454:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.collections.impl.BitSet
 * JD-Core Version:    0.7.0.1
 */