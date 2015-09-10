/*   1:    */ package org.jboss.jandex;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.io.PrintStream;
/*   7:    */ import java.io.Serializable;
/*   8:    */ import java.util.ConcurrentModificationException;
/*   9:    */ import java.util.Iterator;
/*  10:    */ import java.util.NoSuchElementException;
/*  11:    */ 
/*  12:    */ class StrongInternPool<E>
/*  13:    */   implements Cloneable, Serializable
/*  14:    */ {
/*  15: 47 */   private static final Object NULL = new Object();
/*  16:    */   private static final long serialVersionUID = 10929568968762L;
/*  17:    */   private static final int DEFAULT_CAPACITY = 8;
/*  18:    */   private static final int MAXIMUM_CAPACITY = 1073741824;
/*  19:    */   private static final float DEFAULT_LOAD_FACTOR = 0.67F;
/*  20:    */   private transient Object[] table;
/*  21:    */   private transient int size;
/*  22:    */   private transient int threshold;
/*  23:    */   private final float loadFactor;
/*  24:    */   private transient int modCount;
/*  25:    */   
/*  26:    */   public StrongInternPool(int initialCapacity, float loadFactor)
/*  27:    */   {
/*  28: 95 */     if (initialCapacity < 0) {
/*  29: 96 */       throw new IllegalArgumentException("Can not have a negative size table!");
/*  30:    */     }
/*  31: 98 */     if (initialCapacity > 1073741824) {
/*  32: 99 */       initialCapacity = 1073741824;
/*  33:    */     }
/*  34:101 */     if ((loadFactor <= 0.0F) || (loadFactor > 1.0F)) {
/*  35:102 */       throw new IllegalArgumentException("Load factor must be greater than 0 and less than or equal to 1");
/*  36:    */     }
/*  37:104 */     this.loadFactor = loadFactor;
/*  38:105 */     init(initialCapacity, loadFactor);
/*  39:    */   }
/*  40:    */   
/*  41:    */   private void init(int initialCapacity, float loadFactor)
/*  42:    */   {
/*  43:110 */     int c = 1;
/*  44:111 */     while (c < initialCapacity) {
/*  45:111 */       c <<= 1;
/*  46:    */     }
/*  47:112 */     this.threshold = ((int)(c * loadFactor));
/*  48:115 */     if ((initialCapacity > this.threshold) && (c < 1073741824))
/*  49:    */     {
/*  50:116 */       c <<= 1;
/*  51:117 */       this.threshold = ((int)(c * loadFactor));
/*  52:    */     }
/*  53:120 */     this.table = new Object[c];
/*  54:    */   }
/*  55:    */   
/*  56:    */   private static boolean eq(Object o1, Object o2)
/*  57:    */   {
/*  58:124 */     return (o1 == o2) || ((o1 != null) && (o1.equals(o2)));
/*  59:    */   }
/*  60:    */   
/*  61:    */   public StrongInternPool(int initialCapacity)
/*  62:    */   {
/*  63:128 */     this(initialCapacity, 0.67F);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public StrongInternPool()
/*  67:    */   {
/*  68:132 */     this(8);
/*  69:    */   }
/*  70:    */   
/*  71:    */   private static final int hash(Object o)
/*  72:    */   {
/*  73:137 */     int h = o.hashCode();
/*  74:138 */     h ^= h >>> 20 ^ h >>> 12;
/*  75:139 */     return h ^ h >>> 7 ^ h >>> 4;
/*  76:    */   }
/*  77:    */   
/*  78:    */   private static final <K> K maskNull(K key)
/*  79:    */   {
/*  80:144 */     return key == null ? NULL : key;
/*  81:    */   }
/*  82:    */   
/*  83:    */   private static final <K> K unmaskNull(K key)
/*  84:    */   {
/*  85:148 */     return key == NULL ? null : key;
/*  86:    */   }
/*  87:    */   
/*  88:    */   private int nextIndex(int index, int length)
/*  89:    */   {
/*  90:152 */     index = index >= length - 1 ? 0 : index + 1;
/*  91:153 */     return index;
/*  92:    */   }
/*  93:    */   
/*  94:    */   private static final int index(int hashCode, int length)
/*  95:    */   {
/*  96:157 */     return hashCode & length - 1;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public int size()
/* 100:    */   {
/* 101:161 */     return this.size;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public boolean isEmpty()
/* 105:    */   {
/* 106:165 */     return this.size == 0;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public boolean contains(Object entry)
/* 110:    */   {
/* 111:169 */     entry = maskNull(entry);
/* 112:    */     
/* 113:171 */     int hash = hash(entry);
/* 114:172 */     int length = this.table.length;
/* 115:173 */     int index = index(hash, length);
/* 116:    */     
/* 117:175 */     int start = index;
/* 118:    */     for (;;)
/* 119:    */     {
/* 120:176 */       Object e = this.table[index];
/* 121:177 */       if (e == null) {
/* 122:178 */         return false;
/* 123:    */       }
/* 124:180 */       if (eq(entry, e)) {
/* 125:181 */         return true;
/* 126:    */       }
/* 127:183 */       index = nextIndex(index, length);
/* 128:184 */       if (index == start) {
/* 129:185 */         return false;
/* 130:    */       }
/* 131:    */     }
/* 132:    */   }
/* 133:    */   
/* 134:    */   private int offset(Object entry)
/* 135:    */   {
/* 136:190 */     entry = maskNull(entry);
/* 137:    */     
/* 138:192 */     int hash = hash(entry);
/* 139:193 */     int length = this.table.length;
/* 140:194 */     int index = index(hash, length);
/* 141:    */     
/* 142:196 */     int start = index;
/* 143:    */     for (;;)
/* 144:    */     {
/* 145:197 */       Object e = this.table[index];
/* 146:198 */       if (e == null) {
/* 147:199 */         return -1;
/* 148:    */       }
/* 149:201 */       if (eq(entry, e)) {
/* 150:202 */         return index;
/* 151:    */       }
/* 152:204 */       index = nextIndex(index, length);
/* 153:205 */       if (index == start) {
/* 154:206 */         return -1;
/* 155:    */       }
/* 156:    */     }
/* 157:    */   }
/* 158:    */   
/* 159:    */   public E intern(E entry)
/* 160:    */   {
/* 161:220 */     entry = maskNull(entry);
/* 162:    */     
/* 163:222 */     Object[] table = this.table;
/* 164:223 */     int hash = hash(entry);
/* 165:224 */     int length = table.length;
/* 166:225 */     int index = index(hash, length);
/* 167:    */     
/* 168:227 */     int start = index;
/* 169:    */     for (;;)
/* 170:    */     {
/* 171:228 */       Object e = table[index];
/* 172:229 */       if (e == null) {
/* 173:    */         break;
/* 174:    */       }
/* 175:232 */       if (eq(entry, e)) {
/* 176:233 */         return unmaskNull(e);
/* 177:    */       }
/* 178:235 */       index = nextIndex(index, length);
/* 179:236 */       if (index == start) {
/* 180:237 */         throw new IllegalStateException("Table is full!");
/* 181:    */       }
/* 182:    */     }
/* 183:240 */     this.modCount += 1;
/* 184:241 */     table[index] = entry;
/* 185:242 */     if (++this.size >= this.threshold) {
/* 186:243 */       resize(length);
/* 187:    */     }
/* 188:245 */     return unmaskNull(entry);
/* 189:    */   }
/* 190:    */   
/* 191:    */   private void resize(int from)
/* 192:    */   {
/* 193:249 */     int newLength = from << 1;
/* 194:252 */     if ((newLength > 1073741824) || (newLength <= from)) {
/* 195:253 */       return;
/* 196:    */     }
/* 197:255 */     Object[] newTable = new Object[newLength];
/* 198:256 */     Object[] old = this.table;
/* 199:258 */     for (Object e : old) {
/* 200:259 */       if (e != null)
/* 201:    */       {
/* 202:262 */         int index = index(hash(e), newLength);
/* 203:263 */         while (newTable[index] != null) {
/* 204:264 */           index = nextIndex(index, newLength);
/* 205:    */         }
/* 206:266 */         newTable[index] = e;
/* 207:    */       }
/* 208:    */     }
/* 209:269 */     this.threshold = ((int)(this.loadFactor * newLength));
/* 210:270 */     this.table = newTable;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public boolean remove(Object o)
/* 214:    */   {
/* 215:274 */     o = maskNull(o);
/* 216:    */     
/* 217:276 */     Object[] table = this.table;
/* 218:277 */     int length = table.length;
/* 219:278 */     int hash = hash(o);
/* 220:279 */     int start = index(hash, length);
/* 221:    */     
/* 222:281 */     int index = start;
/* 223:    */     for (;;)
/* 224:    */     {
/* 225:282 */       Object e = table[index];
/* 226:283 */       if (e == null) {
/* 227:284 */         return false;
/* 228:    */       }
/* 229:286 */       if (eq(e, o))
/* 230:    */       {
/* 231:287 */         table[index] = null;
/* 232:288 */         relocate(index);
/* 233:289 */         this.modCount += 1;
/* 234:290 */         this.size -= 1;
/* 235:291 */         return true;
/* 236:    */       }
/* 237:294 */       index = nextIndex(index, length);
/* 238:295 */       if (index == start) {
/* 239:296 */         return false;
/* 240:    */       }
/* 241:    */     }
/* 242:    */   }
/* 243:    */   
/* 244:    */   private void relocate(int start)
/* 245:    */   {
/* 246:301 */     Object[] table = this.table;
/* 247:302 */     int length = table.length;
/* 248:303 */     int current = nextIndex(start, length);
/* 249:    */     for (;;)
/* 250:    */     {
/* 251:306 */       Object e = table[current];
/* 252:307 */       if (e == null) {
/* 253:308 */         return;
/* 254:    */       }
/* 255:313 */       int prefer = index(hash(e), length);
/* 256:314 */       if (((current < prefer) && ((prefer <= start) || (start <= current))) || ((prefer <= start) && (start <= current)))
/* 257:    */       {
/* 258:315 */         table[start] = e;
/* 259:316 */         table[current] = null;
/* 260:317 */         start = current;
/* 261:    */       }
/* 262:320 */       current = nextIndex(current, length);
/* 263:    */     }
/* 264:    */   }
/* 265:    */   
/* 266:    */   public void clear()
/* 267:    */   {
/* 268:325 */     this.modCount += 1;
/* 269:326 */     Object[] table = this.table;
/* 270:327 */     for (int i = 0; i < table.length; i++) {
/* 271:328 */       table[i] = null;
/* 272:    */     }
/* 273:330 */     this.size = 0;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public StrongInternPool<E> clone()
/* 277:    */   {
/* 278:    */     try
/* 279:    */     {
/* 280:336 */       StrongInternPool<E> clone = (StrongInternPool)super.clone();
/* 281:337 */       clone.table = ((Object[])this.table.clone());
/* 282:338 */       return clone;
/* 283:    */     }
/* 284:    */     catch (CloneNotSupportedException e)
/* 285:    */     {
/* 286:341 */       throw new IllegalStateException(e);
/* 287:    */     }
/* 288:    */   }
/* 289:    */   
/* 290:    */   public Object[] toInternalArray()
/* 291:    */   {
/* 292:355 */     return this.table;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public void printDebugStats()
/* 296:    */   {
/* 297:359 */     int optimal = 0;
/* 298:360 */     int total = 0;
/* 299:361 */     int totalSkew = 0;
/* 300:362 */     int maxSkew = 0;
/* 301:363 */     for (int i = 0; i < this.table.length; i++)
/* 302:    */     {
/* 303:364 */       Object e = this.table[i];
/* 304:365 */       if (e != null)
/* 305:    */       {
/* 306:367 */         total++;
/* 307:368 */         int target = index(hash(e), this.table.length);
/* 308:369 */         if (i == target)
/* 309:    */         {
/* 310:370 */           optimal++;
/* 311:    */         }
/* 312:    */         else
/* 313:    */         {
/* 314:372 */           int skew = Math.abs(i - target);
/* 315:373 */           if (skew > maxSkew) {
/* 316:374 */             maxSkew = skew;
/* 317:    */           }
/* 318:375 */           totalSkew += skew;
/* 319:    */         }
/* 320:    */       }
/* 321:    */     }
/* 322:381 */     System.out.println(" Size:            " + this.size);
/* 323:382 */     System.out.println(" Real Size:       " + total);
/* 324:383 */     System.out.println(" Optimal:         " + optimal + " (" + optimal * 100.0F / total + "%)");
/* 325:384 */     System.out.println(" Average Distnce: " + totalSkew / (total - optimal));
/* 326:385 */     System.out.println(" Max Distance:    " + maxSkew);
/* 327:    */   }
/* 328:    */   
/* 329:    */   private void readObject(ObjectInputStream s)
/* 330:    */     throws IOException, ClassNotFoundException
/* 331:    */   {
/* 332:390 */     s.defaultReadObject();
/* 333:    */     
/* 334:392 */     int size = s.readInt();
/* 335:    */     
/* 336:394 */     init(size, this.loadFactor);
/* 337:396 */     for (int i = 0; i < size; i++) {
/* 338:397 */       putForCreate(s.readObject());
/* 339:    */     }
/* 340:400 */     this.size = size;
/* 341:    */   }
/* 342:    */   
/* 343:    */   private void putForCreate(E entry)
/* 344:    */   {
/* 345:404 */     entry = maskNull(entry);
/* 346:    */     
/* 347:406 */     Object[] table = this.table;
/* 348:407 */     int hash = hash(entry);
/* 349:408 */     int length = table.length;
/* 350:409 */     int index = index(hash, length);
/* 351:    */     
/* 352:411 */     Object e = table[index];
/* 353:412 */     while (e != null)
/* 354:    */     {
/* 355:413 */       index = nextIndex(index, length);
/* 356:414 */       e = table[index];
/* 357:    */     }
/* 358:417 */     table[index] = entry;
/* 359:    */   }
/* 360:    */   
/* 361:    */   private void writeObject(ObjectOutputStream s)
/* 362:    */     throws IOException
/* 363:    */   {
/* 364:421 */     s.defaultWriteObject();
/* 365:422 */     s.writeInt(this.size);
/* 366:424 */     for (Object e : this.table) {
/* 367:425 */       if (e != null) {
/* 368:426 */         s.writeObject(unmaskNull(e));
/* 369:    */       }
/* 370:    */     }
/* 371:    */   }
/* 372:    */   
/* 373:    */   public Iterator<E> iterator()
/* 374:    */   {
/* 375:432 */     return new IdentityHashSetIterator(null);
/* 376:    */   }
/* 377:    */   
/* 378:    */   public StrongInternPool<E>.Index index()
/* 379:    */   {
/* 380:436 */     return new Index();
/* 381:    */   }
/* 382:    */   
/* 383:    */   public String toString()
/* 384:    */   {
/* 385:440 */     Iterator<E> i = iterator();
/* 386:441 */     if (!i.hasNext()) {
/* 387:442 */       return "[]";
/* 388:    */     }
/* 389:444 */     StringBuilder sb = new StringBuilder();
/* 390:445 */     sb.append('[');
/* 391:    */     for (;;)
/* 392:    */     {
/* 393:447 */       E e = i.next();
/* 394:448 */       sb.append(e);
/* 395:449 */       if (!i.hasNext()) {
/* 396:450 */         return ']';
/* 397:    */       }
/* 398:451 */       sb.append(", ");
/* 399:    */     }
/* 400:    */   }
/* 401:    */   
/* 402:    */   public class Index
/* 403:    */   {
/* 404:    */     private int[] offsets;
/* 405:    */     
/* 406:    */     Index()
/* 407:    */     {
/* 408:459 */       this.offsets = new int[StrongInternPool.this.table.length];
/* 409:460 */       int i = 0;
/* 410:460 */       for (int c = 0; i < this.offsets.length; i++) {
/* 411:461 */         if (StrongInternPool.this.table[i] != null) {
/* 412:462 */           this.offsets[i] = (c++);
/* 413:    */         }
/* 414:    */       }
/* 415:    */     }
/* 416:    */     
/* 417:    */     public int positionOf(E e)
/* 418:    */     {
/* 419:468 */       return this.offsets[StrongInternPool.this.offset(e)];
/* 420:    */     }
/* 421:    */   }
/* 422:    */   
/* 423:    */   private class IdentityHashSetIterator
/* 424:    */     implements Iterator<E>
/* 425:    */   {
/* 426:473 */     private int next = 0;
/* 427:474 */     private int expectedCount = StrongInternPool.this.modCount;
/* 428:475 */     private int current = -1;
/* 429:    */     private boolean hasNext;
/* 430:477 */     Object[] table = StrongInternPool.this.table;
/* 431:    */     
/* 432:    */     private IdentityHashSetIterator() {}
/* 433:    */     
/* 434:    */     public boolean hasNext()
/* 435:    */     {
/* 436:480 */       if (this.hasNext == true) {
/* 437:481 */         return true;
/* 438:    */       }
/* 439:483 */       Object[] table = this.table;
/* 440:484 */       for (int i = this.next; i < table.length; i++) {
/* 441:485 */         if (table[i] != null)
/* 442:    */         {
/* 443:486 */           this.next = i;
/* 444:487 */           return this.hasNext = 1;
/* 445:    */         }
/* 446:    */       }
/* 447:491 */       this.next = table.length;
/* 448:492 */       return false;
/* 449:    */     }
/* 450:    */     
/* 451:    */     public E next()
/* 452:    */     {
/* 453:497 */       if (StrongInternPool.this.modCount != this.expectedCount) {
/* 454:498 */         throw new ConcurrentModificationException();
/* 455:    */       }
/* 456:500 */       if ((!this.hasNext) && (!hasNext())) {
/* 457:501 */         throw new NoSuchElementException();
/* 458:    */       }
/* 459:503 */       this.current = (this.next++);
/* 460:504 */       this.hasNext = false;
/* 461:    */       
/* 462:506 */       return StrongInternPool.unmaskNull(this.table[this.current]);
/* 463:    */     }
/* 464:    */     
/* 465:    */     public void remove()
/* 466:    */     {
/* 467:510 */       if (StrongInternPool.this.modCount != this.expectedCount) {
/* 468:511 */         throw new ConcurrentModificationException();
/* 469:    */       }
/* 470:513 */       int current = this.current;
/* 471:514 */       int delete = current;
/* 472:516 */       if (current == -1) {
/* 473:517 */         throw new IllegalStateException();
/* 474:    */       }
/* 475:520 */       this.current = -1;
/* 476:    */       
/* 477:    */ 
/* 478:523 */       this.next = delete;
/* 479:    */       
/* 480:525 */       Object[] table = this.table;
/* 481:526 */       if (table != StrongInternPool.this.table)
/* 482:    */       {
/* 483:527 */         StrongInternPool.this.remove(table[delete]);
/* 484:528 */         table[delete] = null;
/* 485:529 */         this.expectedCount = StrongInternPool.this.modCount;
/* 486:530 */         return;
/* 487:    */       }
/* 488:533 */       int length = table.length;
/* 489:534 */       int i = delete;
/* 490:    */       
/* 491:536 */       table[delete] = null;
/* 492:537 */       StrongInternPool.access$510(StrongInternPool.this);
/* 493:    */       for (;;)
/* 494:    */       {
/* 495:540 */         i = StrongInternPool.this.nextIndex(i, length);
/* 496:541 */         Object e = table[i];
/* 497:542 */         if (e == null) {
/* 498:    */           break;
/* 499:    */         }
/* 500:545 */         int prefer = StrongInternPool.index(StrongInternPool.access$700(e), length);
/* 501:546 */         if (((i < prefer) && ((prefer <= delete) || (delete <= i))) || ((prefer <= delete) && (delete <= i)))
/* 502:    */         {
/* 503:550 */           if ((i < current) && (current <= delete) && (table == StrongInternPool.this.table))
/* 504:    */           {
/* 505:551 */             int remaining = length - current;
/* 506:552 */             Object[] newTable = new Object[remaining];
/* 507:553 */             System.arraycopy(table, current, newTable, 0, remaining);
/* 508:    */             
/* 509:    */ 
/* 510:    */ 
/* 511:557 */             this.table = newTable;
/* 512:558 */             this.next = 0;
/* 513:    */           }
/* 514:562 */           table[delete] = e;
/* 515:563 */           table[i] = null;
/* 516:564 */           delete = i;
/* 517:    */         }
/* 518:    */       }
/* 519:    */     }
/* 520:    */   }
/* 521:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.jboss.jandex.StrongInternPool
 * JD-Core Version:    0.7.0.1
 */