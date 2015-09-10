/*   1:    */ package org.apache.xml.utils;
/*   2:    */ 
/*   3:    */ public class SuballocatedByteVector
/*   4:    */ {
/*   5:    */   protected int m_blocksize;
/*   6: 50 */   protected int m_numblocks = 32;
/*   7:    */   protected byte[][] m_map;
/*   8: 56 */   protected int m_firstFree = 0;
/*   9:    */   protected byte[] m_map0;
/*  10:    */   
/*  11:    */   public SuballocatedByteVector()
/*  12:    */   {
/*  13: 67 */     this(2048);
/*  14:    */   }
/*  15:    */   
/*  16:    */   public SuballocatedByteVector(int blocksize)
/*  17:    */   {
/*  18: 77 */     this.m_blocksize = blocksize;
/*  19: 78 */     this.m_map0 = new byte[blocksize];
/*  20: 79 */     this.m_map = new byte[this.m_numblocks][];
/*  21: 80 */     this.m_map[0] = this.m_map0;
/*  22:    */   }
/*  23:    */   
/*  24:    */   public SuballocatedByteVector(int blocksize, int increaseSize)
/*  25:    */   {
/*  26: 91 */     this(blocksize);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public int size()
/*  30:    */   {
/*  31:102 */     return this.m_firstFree;
/*  32:    */   }
/*  33:    */   
/*  34:    */   private void setSize(int sz)
/*  35:    */   {
/*  36:112 */     if (this.m_firstFree < sz) {
/*  37:113 */       this.m_firstFree = sz;
/*  38:    */     }
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void addElement(byte value)
/*  42:    */   {
/*  43:123 */     if (this.m_firstFree < this.m_blocksize)
/*  44:    */     {
/*  45:124 */       this.m_map0[(this.m_firstFree++)] = value;
/*  46:    */     }
/*  47:    */     else
/*  48:    */     {
/*  49:127 */       int index = this.m_firstFree / this.m_blocksize;
/*  50:128 */       int offset = this.m_firstFree % this.m_blocksize;
/*  51:129 */       this.m_firstFree += 1;
/*  52:131 */       if (index >= this.m_map.length)
/*  53:    */       {
/*  54:133 */         int newsize = index + this.m_numblocks;
/*  55:134 */         byte[][] newMap = new byte[newsize][];
/*  56:135 */         System.arraycopy(this.m_map, 0, newMap, 0, this.m_map.length);
/*  57:136 */         this.m_map = newMap;
/*  58:    */       }
/*  59:138 */       byte[] block = this.m_map[index];
/*  60:139 */       if (null == block) {
/*  61:140 */         block = this.m_map[index] =  = new byte[this.m_blocksize];
/*  62:    */       }
/*  63:141 */       block[offset] = value;
/*  64:    */     }
/*  65:    */   }
/*  66:    */   
/*  67:    */   private void addElements(byte value, int numberOfElements)
/*  68:    */   {
/*  69:152 */     if (this.m_firstFree + numberOfElements < this.m_blocksize)
/*  70:    */     {
/*  71:153 */       for (int i = 0; i < numberOfElements; i++) {
/*  72:155 */         this.m_map0[(this.m_firstFree++)] = value;
/*  73:    */       }
/*  74:    */     }
/*  75:    */     else
/*  76:    */     {
/*  77:159 */       int index = this.m_firstFree / this.m_blocksize;
/*  78:160 */       int offset = this.m_firstFree % this.m_blocksize;
/*  79:161 */       this.m_firstFree += numberOfElements;
/*  80:162 */       for (; numberOfElements > 0; offset = 0)
/*  81:    */       {
/*  82:164 */         if (index >= this.m_map.length)
/*  83:    */         {
/*  84:166 */           int newsize = index + this.m_numblocks;
/*  85:167 */           byte[][] newMap = new byte[newsize][];
/*  86:168 */           System.arraycopy(this.m_map, 0, newMap, 0, this.m_map.length);
/*  87:169 */           this.m_map = newMap;
/*  88:    */         }
/*  89:171 */         byte[] block = this.m_map[index];
/*  90:172 */         if (null == block) {
/*  91:173 */           block = this.m_map[index] =  = new byte[this.m_blocksize];
/*  92:    */         }
/*  93:174 */         int copied = this.m_blocksize - offset < numberOfElements ? this.m_blocksize - offset : numberOfElements;
/*  94:    */         
/*  95:176 */         numberOfElements -= copied;
/*  96:177 */         while (copied-- > 0) {
/*  97:178 */           block[(offset++)] = value;
/*  98:    */         }
/*  99:180 */         index++;
/* 100:    */       }
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   private void addElements(int numberOfElements)
/* 105:    */   {
/* 106:193 */     int newlen = this.m_firstFree + numberOfElements;
/* 107:194 */     if (newlen > this.m_blocksize)
/* 108:    */     {
/* 109:196 */       int index = this.m_firstFree % this.m_blocksize;
/* 110:197 */       int newindex = (this.m_firstFree + numberOfElements) % this.m_blocksize;
/* 111:198 */       for (int i = index + 1; i <= newindex; i++) {
/* 112:199 */         this.m_map[i] = new byte[this.m_blocksize];
/* 113:    */       }
/* 114:    */     }
/* 115:201 */     this.m_firstFree = newlen;
/* 116:    */   }
/* 117:    */   
/* 118:    */   private void insertElementAt(byte value, int at)
/* 119:    */   {
/* 120:217 */     if (at == this.m_firstFree)
/* 121:    */     {
/* 122:218 */       addElement(value);
/* 123:    */     }
/* 124:219 */     else if (at > this.m_firstFree)
/* 125:    */     {
/* 126:221 */       int index = at / this.m_blocksize;
/* 127:222 */       if (index >= this.m_map.length)
/* 128:    */       {
/* 129:224 */         int newsize = index + this.m_numblocks;
/* 130:225 */         byte[][] newMap = new byte[newsize][];
/* 131:226 */         System.arraycopy(this.m_map, 0, newMap, 0, this.m_map.length);
/* 132:227 */         this.m_map = newMap;
/* 133:    */       }
/* 134:229 */       byte[] block = this.m_map[index];
/* 135:230 */       if (null == block) {
/* 136:231 */         block = this.m_map[index] =  = new byte[this.m_blocksize];
/* 137:    */       }
/* 138:232 */       int offset = at % this.m_blocksize;
/* 139:233 */       block[offset] = value;
/* 140:234 */       this.m_firstFree = (offset + 1);
/* 141:    */     }
/* 142:    */     else
/* 143:    */     {
/* 144:238 */       int index = at / this.m_blocksize;
/* 145:239 */       int maxindex = this.m_firstFree + 1 / this.m_blocksize;
/* 146:240 */       this.m_firstFree += 1;
/* 147:241 */       int offset = at % this.m_blocksize;
/* 148:245 */       while (index <= maxindex)
/* 149:    */       {
/* 150:247 */         int copylen = this.m_blocksize - offset - 1;
/* 151:248 */         byte[] block = this.m_map[index];
/* 152:    */         byte push;
/* 153:249 */         if (null == block)
/* 154:    */         {
/* 155:251 */           push = 0;
/* 156:252 */           block = this.m_map[index] =  = new byte[this.m_blocksize];
/* 157:    */         }
/* 158:    */         else
/* 159:    */         {
/* 160:256 */           push = block[(this.m_blocksize - 1)];
/* 161:257 */           System.arraycopy(block, offset, block, offset + 1, copylen);
/* 162:    */         }
/* 163:259 */         block[offset] = value;
/* 164:260 */         value = push;
/* 165:261 */         offset = 0;
/* 166:262 */         index++;
/* 167:    */       }
/* 168:    */     }
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void removeAllElements()
/* 172:    */   {
/* 173:272 */     this.m_firstFree = 0;
/* 174:    */   }
/* 175:    */   
/* 176:    */   private boolean removeElement(byte s)
/* 177:    */   {
/* 178:288 */     int at = indexOf(s, 0);
/* 179:289 */     if (at < 0) {
/* 180:290 */       return false;
/* 181:    */     }
/* 182:291 */     removeElementAt(at);
/* 183:292 */     return true;
/* 184:    */   }
/* 185:    */   
/* 186:    */   private void removeElementAt(int at)
/* 187:    */   {
/* 188:306 */     if (at < this.m_firstFree)
/* 189:    */     {
/* 190:308 */       int index = at / this.m_blocksize;
/* 191:309 */       int maxindex = this.m_firstFree / this.m_blocksize;
/* 192:310 */       int offset = at % this.m_blocksize;
/* 193:312 */       while (index <= maxindex)
/* 194:    */       {
/* 195:314 */         int copylen = this.m_blocksize - offset - 1;
/* 196:315 */         byte[] block = this.m_map[index];
/* 197:316 */         if (null == block) {
/* 198:317 */           block = this.m_map[index] =  = new byte[this.m_blocksize];
/* 199:    */         } else {
/* 200:319 */           System.arraycopy(block, offset + 1, block, offset, copylen);
/* 201:    */         }
/* 202:320 */         if (index < maxindex)
/* 203:    */         {
/* 204:322 */           byte[] next = this.m_map[(index + 1)];
/* 205:323 */           if (next != null) {
/* 206:324 */             block[(this.m_blocksize - 1)] = (next != null ? next[0] : 0);
/* 207:    */           }
/* 208:    */         }
/* 209:    */         else
/* 210:    */         {
/* 211:327 */           block[(this.m_blocksize - 1)] = 0;
/* 212:    */         }
/* 213:328 */         offset = 0;
/* 214:329 */         index++;
/* 215:    */       }
/* 216:    */     }
/* 217:332 */     this.m_firstFree -= 1;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void setElementAt(byte value, int at)
/* 221:    */   {
/* 222:347 */     if (at < this.m_blocksize)
/* 223:    */     {
/* 224:349 */       this.m_map0[at] = value;
/* 225:350 */       return;
/* 226:    */     }
/* 227:353 */     int index = at / this.m_blocksize;
/* 228:354 */     int offset = at % this.m_blocksize;
/* 229:356 */     if (index >= this.m_map.length)
/* 230:    */     {
/* 231:358 */       int newsize = index + this.m_numblocks;
/* 232:359 */       byte[][] newMap = new byte[newsize][];
/* 233:360 */       System.arraycopy(this.m_map, 0, newMap, 0, this.m_map.length);
/* 234:361 */       this.m_map = newMap;
/* 235:    */     }
/* 236:364 */     byte[] block = this.m_map[index];
/* 237:365 */     if (null == block) {
/* 238:366 */       block = this.m_map[index] =  = new byte[this.m_blocksize];
/* 239:    */     }
/* 240:367 */     block[offset] = value;
/* 241:369 */     if (at >= this.m_firstFree) {
/* 242:370 */       this.m_firstFree = (at + 1);
/* 243:    */     }
/* 244:    */   }
/* 245:    */   
/* 246:    */   public byte elementAt(int i)
/* 247:    */   {
/* 248:398 */     if (i < this.m_blocksize) {
/* 249:399 */       return this.m_map0[i];
/* 250:    */     }
/* 251:401 */     return this.m_map[(i / this.m_blocksize)][(i % this.m_blocksize)];
/* 252:    */   }
/* 253:    */   
/* 254:    */   private boolean contains(byte s)
/* 255:    */   {
/* 256:413 */     return indexOf(s, 0) >= 0;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public int indexOf(byte elem, int index)
/* 260:    */   {
/* 261:429 */     if (index >= this.m_firstFree) {
/* 262:430 */       return -1;
/* 263:    */     }
/* 264:432 */     int bindex = index / this.m_blocksize;
/* 265:433 */     int boffset = index % this.m_blocksize;
/* 266:434 */     int maxindex = this.m_firstFree / this.m_blocksize;
/* 267:437 */     for (; bindex < maxindex; bindex++)
/* 268:    */     {
/* 269:439 */       block = this.m_map[bindex];
/* 270:440 */       if (block != null) {
/* 271:441 */         for (int offset = boffset; offset < this.m_blocksize; offset++) {
/* 272:442 */           if (block[offset] == elem) {
/* 273:443 */             return offset + bindex * this.m_blocksize;
/* 274:    */           }
/* 275:    */         }
/* 276:    */       }
/* 277:444 */       boffset = 0;
/* 278:    */     }
/* 279:447 */     int maxoffset = this.m_firstFree % this.m_blocksize;
/* 280:448 */     byte[] block = this.m_map[maxindex];
/* 281:449 */     for (int offset = boffset; offset < maxoffset; offset++) {
/* 282:450 */       if (block[offset] == elem) {
/* 283:451 */         return offset + maxindex * this.m_blocksize;
/* 284:    */       }
/* 285:    */     }
/* 286:453 */     return -1;
/* 287:    */   }
/* 288:    */   
/* 289:    */   public int indexOf(byte elem)
/* 290:    */   {
/* 291:468 */     return indexOf(elem, 0);
/* 292:    */   }
/* 293:    */   
/* 294:    */   private int lastIndexOf(byte elem)
/* 295:    */   {
/* 296:483 */     int boffset = this.m_firstFree % this.m_blocksize;
/* 297:484 */     for (int index = this.m_firstFree / this.m_blocksize; index >= 0; index--)
/* 298:    */     {
/* 299:488 */       byte[] block = this.m_map[index];
/* 300:489 */       if (block != null) {
/* 301:490 */         for (int offset = boffset; offset >= 0; offset--) {
/* 302:491 */           if (block[offset] == elem) {
/* 303:492 */             return offset + index * this.m_blocksize;
/* 304:    */           }
/* 305:    */         }
/* 306:    */       }
/* 307:493 */       boffset = 0;
/* 308:    */     }
/* 309:495 */     return -1;
/* 310:    */   }
/* 311:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.SuballocatedByteVector
 * JD-Core Version:    0.7.0.1
 */