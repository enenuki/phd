/*   1:    */ package org.apache.xml.utils;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ 
/*   5:    */ public class NodeVector
/*   6:    */   implements Serializable, Cloneable
/*   7:    */ {
/*   8:    */   static final long serialVersionUID = -713473092200731870L;
/*   9:    */   private int m_blocksize;
/*  10:    */   private int[] m_map;
/*  11: 51 */   protected int m_firstFree = 0;
/*  12:    */   private int m_mapSize;
/*  13:    */   
/*  14:    */   public NodeVector()
/*  15:    */   {
/*  16: 64 */     this.m_blocksize = 32;
/*  17: 65 */     this.m_mapSize = 0;
/*  18:    */   }
/*  19:    */   
/*  20:    */   public NodeVector(int blocksize)
/*  21:    */   {
/*  22: 75 */     this.m_blocksize = blocksize;
/*  23: 76 */     this.m_mapSize = 0;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public Object clone()
/*  27:    */     throws CloneNotSupportedException
/*  28:    */   {
/*  29: 89 */     NodeVector clone = (NodeVector)super.clone();
/*  30: 91 */     if ((null != this.m_map) && (this.m_map == clone.m_map))
/*  31:    */     {
/*  32: 93 */       clone.m_map = new int[this.m_map.length];
/*  33:    */       
/*  34: 95 */       System.arraycopy(this.m_map, 0, clone.m_map, 0, this.m_map.length);
/*  35:    */     }
/*  36: 98 */     return clone;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public int size()
/*  40:    */   {
/*  41:108 */     return this.m_firstFree;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void addElement(int value)
/*  45:    */   {
/*  46:119 */     if (this.m_firstFree + 1 >= this.m_mapSize) {
/*  47:121 */       if (null == this.m_map)
/*  48:    */       {
/*  49:123 */         this.m_map = new int[this.m_blocksize];
/*  50:124 */         this.m_mapSize = this.m_blocksize;
/*  51:    */       }
/*  52:    */       else
/*  53:    */       {
/*  54:128 */         this.m_mapSize += this.m_blocksize;
/*  55:    */         
/*  56:130 */         int[] newMap = new int[this.m_mapSize];
/*  57:    */         
/*  58:132 */         System.arraycopy(this.m_map, 0, newMap, 0, this.m_firstFree + 1);
/*  59:    */         
/*  60:134 */         this.m_map = newMap;
/*  61:    */       }
/*  62:    */     }
/*  63:138 */     this.m_map[this.m_firstFree] = value;
/*  64:    */     
/*  65:140 */     this.m_firstFree += 1;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public final void push(int value)
/*  69:    */   {
/*  70:151 */     int ff = this.m_firstFree;
/*  71:153 */     if (ff + 1 >= this.m_mapSize) {
/*  72:155 */       if (null == this.m_map)
/*  73:    */       {
/*  74:157 */         this.m_map = new int[this.m_blocksize];
/*  75:158 */         this.m_mapSize = this.m_blocksize;
/*  76:    */       }
/*  77:    */       else
/*  78:    */       {
/*  79:162 */         this.m_mapSize += this.m_blocksize;
/*  80:    */         
/*  81:164 */         int[] newMap = new int[this.m_mapSize];
/*  82:    */         
/*  83:166 */         System.arraycopy(this.m_map, 0, newMap, 0, ff + 1);
/*  84:    */         
/*  85:168 */         this.m_map = newMap;
/*  86:    */       }
/*  87:    */     }
/*  88:172 */     this.m_map[ff] = value;
/*  89:    */     
/*  90:174 */     ff++;
/*  91:    */     
/*  92:176 */     this.m_firstFree = ff;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public final int pop()
/*  96:    */   {
/*  97:187 */     this.m_firstFree -= 1;
/*  98:    */     
/*  99:189 */     int n = this.m_map[this.m_firstFree];
/* 100:    */     
/* 101:191 */     this.m_map[this.m_firstFree] = -1;
/* 102:    */     
/* 103:193 */     return n;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public final int popAndTop()
/* 107:    */   {
/* 108:205 */     this.m_firstFree -= 1;
/* 109:    */     
/* 110:207 */     this.m_map[this.m_firstFree] = -1;
/* 111:    */     
/* 112:209 */     return this.m_firstFree == 0 ? -1 : this.m_map[(this.m_firstFree - 1)];
/* 113:    */   }
/* 114:    */   
/* 115:    */   public final void popQuick()
/* 116:    */   {
/* 117:218 */     this.m_firstFree -= 1;
/* 118:    */     
/* 119:220 */     this.m_map[this.m_firstFree] = -1;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public final int peepOrNull()
/* 123:    */   {
/* 124:232 */     return (null != this.m_map) && (this.m_firstFree > 0) ? this.m_map[(this.m_firstFree - 1)] : -1;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public final void pushPair(int v1, int v2)
/* 128:    */   {
/* 129:247 */     if (null == this.m_map)
/* 130:    */     {
/* 131:249 */       this.m_map = new int[this.m_blocksize];
/* 132:250 */       this.m_mapSize = this.m_blocksize;
/* 133:    */     }
/* 134:254 */     else if (this.m_firstFree + 2 >= this.m_mapSize)
/* 135:    */     {
/* 136:256 */       this.m_mapSize += this.m_blocksize;
/* 137:    */       
/* 138:258 */       int[] newMap = new int[this.m_mapSize];
/* 139:    */       
/* 140:260 */       System.arraycopy(this.m_map, 0, newMap, 0, this.m_firstFree);
/* 141:    */       
/* 142:262 */       this.m_map = newMap;
/* 143:    */     }
/* 144:266 */     this.m_map[this.m_firstFree] = v1;
/* 145:267 */     this.m_map[(this.m_firstFree + 1)] = v2;
/* 146:268 */     this.m_firstFree += 2;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public final void popPair()
/* 150:    */   {
/* 151:279 */     this.m_firstFree -= 2;
/* 152:280 */     this.m_map[this.m_firstFree] = -1;
/* 153:281 */     this.m_map[(this.m_firstFree + 1)] = -1;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public final void setTail(int n)
/* 157:    */   {
/* 158:293 */     this.m_map[(this.m_firstFree - 1)] = n;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public final void setTailSub1(int n)
/* 162:    */   {
/* 163:305 */     this.m_map[(this.m_firstFree - 2)] = n;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public final int peepTail()
/* 167:    */   {
/* 168:317 */     return this.m_map[(this.m_firstFree - 1)];
/* 169:    */   }
/* 170:    */   
/* 171:    */   public final int peepTailSub1()
/* 172:    */   {
/* 173:329 */     return this.m_map[(this.m_firstFree - 2)];
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void insertInOrder(int value)
/* 177:    */   {
/* 178:340 */     for (int i = 0; i < this.m_firstFree; i++) {
/* 179:342 */       if (value < this.m_map[i])
/* 180:    */       {
/* 181:344 */         insertElementAt(value, i);
/* 182:    */         
/* 183:346 */         return;
/* 184:    */       }
/* 185:    */     }
/* 186:350 */     addElement(value);
/* 187:    */   }
/* 188:    */   
/* 189:    */   public void insertElementAt(int value, int at)
/* 190:    */   {
/* 191:365 */     if (null == this.m_map)
/* 192:    */     {
/* 193:367 */       this.m_map = new int[this.m_blocksize];
/* 194:368 */       this.m_mapSize = this.m_blocksize;
/* 195:    */     }
/* 196:370 */     else if (this.m_firstFree + 1 >= this.m_mapSize)
/* 197:    */     {
/* 198:372 */       this.m_mapSize += this.m_blocksize;
/* 199:    */       
/* 200:374 */       int[] newMap = new int[this.m_mapSize];
/* 201:    */       
/* 202:376 */       System.arraycopy(this.m_map, 0, newMap, 0, this.m_firstFree + 1);
/* 203:    */       
/* 204:378 */       this.m_map = newMap;
/* 205:    */     }
/* 206:381 */     if (at <= this.m_firstFree - 1) {
/* 207:383 */       System.arraycopy(this.m_map, at, this.m_map, at + 1, this.m_firstFree - at);
/* 208:    */     }
/* 209:386 */     this.m_map[at] = value;
/* 210:    */     
/* 211:388 */     this.m_firstFree += 1;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void appendNodes(NodeVector nodes)
/* 215:    */   {
/* 216:399 */     int nNodes = nodes.size();
/* 217:401 */     if (null == this.m_map)
/* 218:    */     {
/* 219:403 */       this.m_mapSize = (nNodes + this.m_blocksize);
/* 220:404 */       this.m_map = new int[this.m_mapSize];
/* 221:    */     }
/* 222:406 */     else if (this.m_firstFree + nNodes >= this.m_mapSize)
/* 223:    */     {
/* 224:408 */       this.m_mapSize += nNodes + this.m_blocksize;
/* 225:    */       
/* 226:410 */       int[] newMap = new int[this.m_mapSize];
/* 227:    */       
/* 228:412 */       System.arraycopy(this.m_map, 0, newMap, 0, this.m_firstFree + nNodes);
/* 229:    */       
/* 230:414 */       this.m_map = newMap;
/* 231:    */     }
/* 232:417 */     System.arraycopy(nodes.m_map, 0, this.m_map, this.m_firstFree, nNodes);
/* 233:    */     
/* 234:419 */     this.m_firstFree += nNodes;
/* 235:    */   }
/* 236:    */   
/* 237:    */   public void removeAllElements()
/* 238:    */   {
/* 239:431 */     if (null == this.m_map) {
/* 240:432 */       return;
/* 241:    */     }
/* 242:434 */     for (int i = 0; i < this.m_firstFree; i++) {
/* 243:436 */       this.m_map[i] = -1;
/* 244:    */     }
/* 245:439 */     this.m_firstFree = 0;
/* 246:    */   }
/* 247:    */   
/* 248:    */   public void RemoveAllNoClear()
/* 249:    */   {
/* 250:448 */     if (null == this.m_map) {
/* 251:449 */       return;
/* 252:    */     }
/* 253:451 */     this.m_firstFree = 0;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public boolean removeElement(int s)
/* 257:    */   {
/* 258:468 */     if (null == this.m_map) {
/* 259:469 */       return false;
/* 260:    */     }
/* 261:471 */     for (int i = 0; i < this.m_firstFree; i++)
/* 262:    */     {
/* 263:473 */       int node = this.m_map[i];
/* 264:475 */       if (node == s)
/* 265:    */       {
/* 266:477 */         if (i > this.m_firstFree) {
/* 267:478 */           System.arraycopy(this.m_map, i + 1, this.m_map, i - 1, this.m_firstFree - i);
/* 268:    */         } else {
/* 269:480 */           this.m_map[i] = -1;
/* 270:    */         }
/* 271:482 */         this.m_firstFree -= 1;
/* 272:    */         
/* 273:484 */         return true;
/* 274:    */       }
/* 275:    */     }
/* 276:488 */     return false;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public void removeElementAt(int i)
/* 280:    */   {
/* 281:502 */     if (null == this.m_map) {
/* 282:503 */       return;
/* 283:    */     }
/* 284:505 */     if (i > this.m_firstFree) {
/* 285:506 */       System.arraycopy(this.m_map, i + 1, this.m_map, i - 1, this.m_firstFree - i);
/* 286:    */     } else {
/* 287:508 */       this.m_map[i] = -1;
/* 288:    */     }
/* 289:    */   }
/* 290:    */   
/* 291:    */   public void setElementAt(int node, int index)
/* 292:    */   {
/* 293:524 */     if (null == this.m_map)
/* 294:    */     {
/* 295:526 */       this.m_map = new int[this.m_blocksize];
/* 296:527 */       this.m_mapSize = this.m_blocksize;
/* 297:    */     }
/* 298:530 */     if (index == -1) {
/* 299:531 */       addElement(node);
/* 300:    */     }
/* 301:533 */     this.m_map[index] = node;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public int elementAt(int i)
/* 305:    */   {
/* 306:546 */     if (null == this.m_map) {
/* 307:547 */       return -1;
/* 308:    */     }
/* 309:549 */     return this.m_map[i];
/* 310:    */   }
/* 311:    */   
/* 312:    */   public boolean contains(int s)
/* 313:    */   {
/* 314:562 */     if (null == this.m_map) {
/* 315:563 */       return false;
/* 316:    */     }
/* 317:565 */     for (int i = 0; i < this.m_firstFree; i++)
/* 318:    */     {
/* 319:567 */       int node = this.m_map[i];
/* 320:569 */       if (node == s) {
/* 321:570 */         return true;
/* 322:    */       }
/* 323:    */     }
/* 324:573 */     return false;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public int indexOf(int elem, int index)
/* 328:    */   {
/* 329:590 */     if (null == this.m_map) {
/* 330:591 */       return -1;
/* 331:    */     }
/* 332:593 */     for (int i = index; i < this.m_firstFree; i++)
/* 333:    */     {
/* 334:595 */       int node = this.m_map[i];
/* 335:597 */       if (node == elem) {
/* 336:598 */         return i;
/* 337:    */       }
/* 338:    */     }
/* 339:601 */     return -1;
/* 340:    */   }
/* 341:    */   
/* 342:    */   public int indexOf(int elem)
/* 343:    */   {
/* 344:617 */     if (null == this.m_map) {
/* 345:618 */       return -1;
/* 346:    */     }
/* 347:620 */     for (int i = 0; i < this.m_firstFree; i++)
/* 348:    */     {
/* 349:622 */       int node = this.m_map[i];
/* 350:624 */       if (node == elem) {
/* 351:625 */         return i;
/* 352:    */       }
/* 353:    */     }
/* 354:628 */     return -1;
/* 355:    */   }
/* 356:    */   
/* 357:    */   public void sort(int[] a, int lo0, int hi0)
/* 358:    */     throws Exception
/* 359:    */   {
/* 360:643 */     int lo = lo0;
/* 361:644 */     int hi = hi0;
/* 362:647 */     if (lo >= hi) {
/* 363:649 */       return;
/* 364:    */     }
/* 365:651 */     if (lo == hi - 1)
/* 366:    */     {
/* 367:657 */       if (a[lo] > a[hi])
/* 368:    */       {
/* 369:659 */         int T = a[lo];
/* 370:    */         
/* 371:661 */         a[lo] = a[hi];
/* 372:662 */         a[hi] = T;
/* 373:    */       }
/* 374:665 */       return;
/* 375:    */     }
/* 376:671 */     int pivot = a[((lo + hi) / 2)];
/* 377:    */     
/* 378:673 */     a[((lo + hi) / 2)] = a[hi];
/* 379:674 */     a[hi] = pivot;
/* 380:676 */     while (lo < hi)
/* 381:    */     {
/* 382:    */       do
/* 383:    */       {
/* 384:683 */         if (a[lo] > pivot) {
/* 385:    */           break;
/* 386:    */         }
/* 387:683 */       } while (lo < hi);
/* 388:692 */       while ((pivot <= a[hi]) && (lo < hi)) {
/* 389:694 */         hi--;
/* 390:    */       }
/* 391:700 */       if (lo < hi)
/* 392:    */       {
/* 393:702 */         int T = a[lo];
/* 394:    */         
/* 395:704 */         a[lo] = a[hi];
/* 396:705 */         a[hi] = T;
/* 397:    */       }
/* 398:    */     }
/* 399:718 */     a[hi0] = a[hi];
/* 400:719 */     a[hi] = pivot;
/* 401:    */     
/* 402:    */ 
/* 403:    */ 
/* 404:    */ 
/* 405:    */ 
/* 406:    */ 
/* 407:726 */     sort(a, lo0, lo - 1);
/* 408:727 */     sort(a, hi + 1, hi0);
/* 409:    */   }
/* 410:    */   
/* 411:    */   public void sort()
/* 412:    */     throws Exception
/* 413:    */   {
/* 414:737 */     sort(this.m_map, 0, this.m_firstFree - 1);
/* 415:    */   }
/* 416:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.utils.NodeVector
 * JD-Core Version:    0.7.0.1
 */