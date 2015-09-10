/*   1:    */ package org.apache.xml.utils;
/*   2:    */ 
/*   3:    */ public class SuballocatedIntVector
/*   4:    */ {
/*   5:    */   protected int m_blocksize;
/*   6:    */   protected int m_SHIFT;
/*   7:    */   protected int m_MASK;
/*   8:    */   protected static final int NUMBLOCKS_DEFAULT = 32;
/*   9: 55 */   protected int m_numblocks = 32;
/*  10:    */   protected int[][] m_map;
/*  11: 61 */   protected int m_firstFree = 0;
/*  12:    */   protected int[] m_map0;
/*  13:    */   protected int[] m_buildCache;
/*  14:    */   protected int m_buildCacheStartIndex;
/*  15:    */   
/*  16:    */   public SuballocatedIntVector()
/*  17:    */   {
/*  18: 81 */     this(2048);
/*  19:    */   }
/*  20:    */   
/*  21:    */   public SuballocatedIntVector(int blocksize, int numblocks)
/*  22:    */   {
/*  23: 95 */     for (this.m_SHIFT = 0; 0 != blocksize >>>= 1; this.m_SHIFT += 1) {}
/*  24: 97 */     this.m_blocksize = (1 << this.m_SHIFT);
/*  25: 98 */     this.m_MASK = (this.m_blocksize - 1);
/*  26: 99 */     this.m_numblocks = numblocks;
/*  27:    */     
/*  28:101 */     this.m_map0 = new int[this.m_blocksize];
/*  29:102 */     this.m_map = new int[numblocks][];
/*  30:103 */     this.m_map[0] = this.m_map0;
/*  31:104 */     this.m_buildCache = this.m_map0;
/*  32:105 */     this.m_buildCacheStartIndex = 0;
/*  33:    */   }
/*  34:    */   
/*  35:    */   public SuballocatedIntVector(int blocksize)
/*  36:    */   {
/*  37:115 */     this(blocksize, 32);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public int size()
/*  41:    */   {
/*  42:125 */     return this.m_firstFree;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void setSize(int sz)
/*  46:    */   {
/*  47:136 */     if (this.m_firstFree > sz) {
/*  48:137 */       this.m_firstFree = sz;
/*  49:    */     }
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void addElement(int value)
/*  53:    */   {
/*  54:147 */     int indexRelativeToCache = this.m_firstFree - this.m_buildCacheStartIndex;
/*  55:150 */     if ((indexRelativeToCache >= 0) && (indexRelativeToCache < this.m_blocksize))
/*  56:    */     {
/*  57:151 */       this.m_buildCache[indexRelativeToCache] = value;
/*  58:152 */       this.m_firstFree += 1;
/*  59:    */     }
/*  60:    */     else
/*  61:    */     {
/*  62:161 */       int index = this.m_firstFree >>> this.m_SHIFT;
/*  63:162 */       int offset = this.m_firstFree & this.m_MASK;
/*  64:164 */       if (index >= this.m_map.length)
/*  65:    */       {
/*  66:166 */         int newsize = index + this.m_numblocks;
/*  67:167 */         int[][] newMap = new int[newsize][];
/*  68:168 */         System.arraycopy(this.m_map, 0, newMap, 0, this.m_map.length);
/*  69:169 */         this.m_map = newMap;
/*  70:    */       }
/*  71:171 */       int[] block = this.m_map[index];
/*  72:172 */       if (null == block) {
/*  73:173 */         block = this.m_map[index] =  = new int[this.m_blocksize];
/*  74:    */       }
/*  75:174 */       block[offset] = value;
/*  76:    */       
/*  77:    */ 
/*  78:    */ 
/*  79:178 */       this.m_buildCache = block;
/*  80:179 */       this.m_buildCacheStartIndex = (this.m_firstFree - offset);
/*  81:    */       
/*  82:181 */       this.m_firstFree += 1;
/*  83:    */     }
/*  84:    */   }
/*  85:    */   
/*  86:    */   private void addElements(int value, int numberOfElements)
/*  87:    */   {
/*  88:192 */     if (this.m_firstFree + numberOfElements < this.m_blocksize)
/*  89:    */     {
/*  90:193 */       for (int i = 0; i < numberOfElements; i++) {
/*  91:195 */         this.m_map0[(this.m_firstFree++)] = value;
/*  92:    */       }
/*  93:    */     }
/*  94:    */     else
/*  95:    */     {
/*  96:199 */       int index = this.m_firstFree >>> this.m_SHIFT;
/*  97:200 */       int offset = this.m_firstFree & this.m_MASK;
/*  98:201 */       this.m_firstFree += numberOfElements;
/*  99:202 */       for (; numberOfElements > 0; offset = 0)
/* 100:    */       {
/* 101:204 */         if (index >= this.m_map.length)
/* 102:    */         {
/* 103:206 */           int newsize = index + this.m_numblocks;
/* 104:207 */           int[][] newMap = new int[newsize][];
/* 105:208 */           System.arraycopy(this.m_map, 0, newMap, 0, this.m_map.length);
/* 106:209 */           this.m_map = newMap;
/* 107:    */         }
/* 108:211 */         int[] block = this.m_map[index];
/* 109:212 */         if (null == block) {
/* 110:213 */           block = this.m_map[index] =  = new int[this.m_blocksize];
/* 111:    */         }
/* 112:214 */         int copied = this.m_blocksize - offset < numberOfElements ? this.m_blocksize - offset : numberOfElements;
/* 113:    */         
/* 114:216 */         numberOfElements -= copied;
/* 115:217 */         while (copied-- > 0) {
/* 116:218 */           block[(offset++)] = value;
/* 117:    */         }
/* 118:220 */         index++;
/* 119:    */       }
/* 120:    */     }
/* 121:    */   }
/* 122:    */   
/* 123:    */   private void addElements(int numberOfElements)
/* 124:    */   {
/* 125:233 */     int newlen = this.m_firstFree + numberOfElements;
/* 126:234 */     if (newlen > this.m_blocksize)
/* 127:    */     {
/* 128:236 */       int index = this.m_firstFree >>> this.m_SHIFT;
/* 129:237 */       int newindex = this.m_firstFree + numberOfElements >>> this.m_SHIFT;
/* 130:238 */       for (int i = index + 1; i <= newindex; i++) {
/* 131:239 */         this.m_map[i] = new int[this.m_blocksize];
/* 132:    */       }
/* 133:    */     }
/* 134:241 */     this.m_firstFree = newlen;
/* 135:    */   }
/* 136:    */   
/* 137:    */   private void insertElementAt(int value, int at)
/* 138:    */   {
/* 139:257 */     if (at == this.m_firstFree)
/* 140:    */     {
/* 141:258 */       addElement(value);
/* 142:    */     }
/* 143:259 */     else if (at > this.m_firstFree)
/* 144:    */     {
/* 145:261 */       int index = at >>> this.m_SHIFT;
/* 146:262 */       if (index >= this.m_map.length)
/* 147:    */       {
/* 148:264 */         int newsize = index + this.m_numblocks;
/* 149:265 */         int[][] newMap = new int[newsize][];
/* 150:266 */         System.arraycopy(this.m_map, 0, newMap, 0, this.m_map.length);
/* 151:267 */         this.m_map = newMap;
/* 152:    */       }
/* 153:269 */       int[] block = this.m_map[index];
/* 154:270 */       if (null == block) {
/* 155:271 */         block = this.m_map[index] =  = new int[this.m_blocksize];
/* 156:    */       }
/* 157:272 */       int offset = at & this.m_MASK;
/* 158:273 */       block[offset] = value;
/* 159:274 */       this.m_firstFree = (offset + 1);
/* 160:    */     }
/* 161:    */     else
/* 162:    */     {
/* 163:278 */       int index = at >>> this.m_SHIFT;
/* 164:279 */       int maxindex = this.m_firstFree >>> this.m_SHIFT;
/* 165:280 */       this.m_firstFree += 1;
/* 166:281 */       int offset = at & this.m_MASK;
/* 167:285 */       while (index <= maxindex)
/* 168:    */       {
/* 169:287 */         int copylen = this.m_blocksize - offset - 1;
/* 170:288 */         int[] block = this.m_map[index];
/* 171:    */         int push;
/* 172:289 */         if (null == block)
/* 173:    */         {
/* 174:291 */           push = 0;
/* 175:292 */           block = this.m_map[index] =  = new int[this.m_blocksize];
/* 176:    */         }
/* 177:    */         else
/* 178:    */         {
/* 179:296 */           push = block[(this.m_blocksize - 1)];
/* 180:297 */           System.arraycopy(block, offset, block, offset + 1, copylen);
/* 181:    */         }
/* 182:299 */         block[offset] = value;
/* 183:300 */         value = push;
/* 184:301 */         offset = 0;
/* 185:302 */         index++;
/* 186:    */       }
/* 187:    */     }
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void removeAllElements()
/* 191:    */   {
/* 192:312 */     this.m_firstFree = 0;
/* 193:313 */     this.m_buildCache = this.m_map0;
/* 194:314 */     this.m_buildCacheStartIndex = 0;
/* 195:    */   }
/* 196:    */   
/* 197:    */   private boolean removeElement(int s)
/* 198:    */   {
/* 199:330 */     int at = indexOf(s, 0);
/* 200:331 */     if (at < 0) {
/* 201:332 */       return false;
/* 202:    */     }
/* 203:333 */     removeElementAt(at);
/* 204:334 */     return true;
/* 205:    */   }
/* 206:    */   
/* 207:    */   private void removeElementAt(int at)
/* 208:    */   {
/* 209:348 */     if (at < this.m_firstFree)
/* 210:    */     {
/* 211:350 */       int index = at >>> this.m_SHIFT;
/* 212:351 */       int maxindex = this.m_firstFree >>> this.m_SHIFT;
/* 213:352 */       int offset = at & this.m_MASK;
/* 214:354 */       while (index <= maxindex)
/* 215:    */       {
/* 216:356 */         int copylen = this.m_blocksize - offset - 1;
/* 217:357 */         int[] block = this.m_map[index];
/* 218:358 */         if (null == block) {
/* 219:359 */           block = this.m_map[index] =  = new int[this.m_blocksize];
/* 220:    */         } else {
/* 221:361 */           System.arraycopy(block, offset + 1, block, offset, copylen);
/* 222:    */         }
/* 223:362 */         if (index < maxindex)
/* 224:    */         {
/* 225:364 */           int[] next = this.m_map[(index + 1)];
/* 226:365 */           if (next != null) {
/* 227:366 */             block[(this.m_blocksize - 1)] = (next != null ? next[0] : 0);
/* 228:    */           }
/* 229:    */         }
/* 230:    */         else
/* 231:    */         {
/* 232:369 */           block[(this.m_blocksize - 1)] = 0;
/* 233:    */         }
/* 234:370 */         offset = 0;
/* 235:371 */         index++;
/* 236:    */       }
/* 237:    */     }
/* 238:374 */     this.m_firstFree -= 1;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void setElementAt(int value, int at)
/* 242:    */   {
/* 243:389 */     if (at < this.m_blocksize)
/* 244:    */     {
/* 245:390 */       this.m_map0[at] = value;
/* 246:    */     }
/* 247:    */     else
/* 248:    */     {
/* 249:393 */       int index = at >>> this.m_SHIFT;
/* 250:394 */       int offset = at & this.m_MASK;
/* 251:396 */       if (index >= this.m_map.length)
/* 252:    */       {
/* 253:398 */         int newsize = index + this.m_numblocks;
/* 254:399 */         int[][] newMap = new int[newsize][];
/* 255:400 */         System.arraycopy(this.m_map, 0, newMap, 0, this.m_map.length);
/* 256:401 */         this.m_map = newMap;
/* 257:    */       }
/* 258:404 */       int[] block = this.m_map[index];
/* 259:405 */       if (null == block) {
/* 260:406 */         block = this.m_map[index] =  = new int[this.m_blocksize];
/* 261:    */       }
/* 262:407 */       block[offset] = value;
/* 263:    */     }
/* 264:410 */     if (at >= this.m_firstFree) {
/* 265:411 */       this.m_firstFree = (at + 1);
/* 266:    */     }
/* 267:    */   }
/* 268:    */   
/* 269:    */   public int elementAt(int i)
/* 270:    */   {
/* 271:439 */     if (i < this.m_blocksize) {
/* 272:440 */       return this.m_map0[i];
/* 273:    */     }
/* 274:442 */     return this.m_map[(i >>> this.m_SHIFT)][(i & this.m_MASK)];
/* 275:    */   }
/* 276:    */   
/* 277:    */   private boolean contains(int s)
/* 278:    */   {
/* 279:454 */     return indexOf(s, 0) >= 0;
/* 280:    */   }
/* 281:    */   
/* 282:    */   public int indexOf(int elem, int index)
/* 283:    */   {
/* 284:470 */     if (index >= this.m_firstFree) {
/* 285:471 */       return -1;
/* 286:    */     }
/* 287:473 */     int bindex = index >>> this.m_SHIFT;
/* 288:474 */     int boffset = index & this.m_MASK;
/* 289:475 */     int maxindex = this.m_firstFree >>> this.m_SHIFT;
/* 290:478 */     for (; bindex < maxindex; bindex++)
/* 291:    */     {
/* 292:480 */       block = this.m_map[bindex];
/* 293:481 */       if (block != null) {
/* 294:482 */         for (int offset = boffset; offset < this.m_blocksize; offset++) {
/* 295:483 */           if (block[offset] == elem) {
/* 296:484 */             return offset + bindex * this.m_blocksize;
/* 297:    */           }
/* 298:    */         }
/* 299:    */       }
/* 300:485 */       boffset = 0;
/* 301:    */     }
/* 302:488 */     int maxoffset = this.m_firstFree & this.m_MASK;
/* 303:489 */     int[] block = this.m_map[maxindex];
/* 304:490 */     for (int offset = boffset; offset < maxoffset; offset++) {
/* 305:491 */       if (block[offset] == elem) {
/* 306:492 */         return offset + maxindex * this.m_blocksize;
/* 307:    */       }
/* 308:    */     }
/* 309:494 */     return -1;
/* 310:    */   }
/* 311:    */   
/* 312:    */   public int indexOf(int elem)
/* 313:    */   {
/* 314:509 */     return indexOf(elem, 0);
/* 315:    */   }
/* 316:    */   
/* 317:    */   private int lastIndexOf(int elem)
/* 318:    */   {
/* 319:524 */     int boffset = this.m_firstFree & this.m_MASK;
/* 320:525 */     for (int index = this.m_firstFree >>> this.m_SHIFT; index >= 0; index--)
/* 321:    */     {
/* 322:529 */       int[] block = this.m_map[index];
/* 323:530 */       if (block != null) {
/* 324:531 */         for (int offset = boffset; offset >= 0; offset--) {
/* 325:532 */           if (block[offset] == elem) {
/* 326:533 */             return offset + index * this.m_blocksize;
/* 327:    */           }
/* 328:    */         }
/* 329:    */       }
/* 330:534 */       boffset = 0;
/* 331:    */     }
/* 332:536 */     return -1;
/* 333:    */   }
/* 334:    */   
/* 335:    */   public final int[] getMap0()
/* 336:    */   {
/* 337:545 */     return this.m_map0;
/* 338:    */   }
/* 339:    */   
/* 340:    */   public final int[][] getMap()
/* 341:    */   {
/* 342:554 */     return this.m_map;
/* 343:    */   }
/* 344:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.SuballocatedIntVector
 * JD-Core Version:    0.7.0.1
 */