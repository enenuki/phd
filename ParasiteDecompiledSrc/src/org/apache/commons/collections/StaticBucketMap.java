/*   1:    */ package org.apache.commons.collections;
/*   2:    */ 
/*   3:    */ import java.util.AbstractCollection;
/*   4:    */ import java.util.AbstractSet;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Collection;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Map.Entry;
/*  10:    */ import java.util.NoSuchElementException;
/*  11:    */ import java.util.Set;
/*  12:    */ 
/*  13:    */ /**
/*  14:    */  * @deprecated
/*  15:    */  */
/*  16:    */ public final class StaticBucketMap
/*  17:    */   implements Map
/*  18:    */ {
/*  19:    */   private static final int DEFAULT_BUCKETS = 255;
/*  20:    */   private Node[] m_buckets;
/*  21:    */   private Lock[] m_locks;
/*  22:    */   
/*  23:    */   public StaticBucketMap()
/*  24:    */   {
/*  25:114 */     this(255);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public StaticBucketMap(int numBuckets)
/*  29:    */   {
/*  30:129 */     int size = Math.max(17, numBuckets);
/*  31:132 */     if (size % 2 == 0) {
/*  32:134 */       size--;
/*  33:    */     }
/*  34:137 */     this.m_buckets = new Node[size];
/*  35:138 */     this.m_locks = new Lock[size];
/*  36:140 */     for (int i = 0; i < size; i++) {
/*  37:142 */       this.m_locks[i] = new Lock(null);
/*  38:    */     }
/*  39:    */   }
/*  40:    */   
/*  41:    */   private final int getHash(Object key)
/*  42:    */   {
/*  43:161 */     if (key == null) {
/*  44:161 */       return 0;
/*  45:    */     }
/*  46:162 */     int hash = key.hashCode();
/*  47:163 */     hash += (hash << 15 ^ 0xFFFFFFFF);
/*  48:164 */     hash ^= hash >>> 10;
/*  49:165 */     hash += (hash << 3);
/*  50:166 */     hash ^= hash >>> 6;
/*  51:167 */     hash += (hash << 11 ^ 0xFFFFFFFF);
/*  52:168 */     hash ^= hash >>> 16;
/*  53:169 */     hash %= this.m_buckets.length;
/*  54:170 */     return hash < 0 ? hash * -1 : hash;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Set keySet()
/*  58:    */   {
/*  59:178 */     return new KeySet(null);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public int size()
/*  63:    */   {
/*  64:186 */     int cnt = 0;
/*  65:188 */     for (int i = 0; i < this.m_buckets.length; i++) {
/*  66:190 */       cnt += this.m_locks[i].size;
/*  67:    */     }
/*  68:193 */     return cnt;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public Object put(Object key, Object value)
/*  72:    */   {
/*  73:201 */     int hash = getHash(key);
/*  74:203 */     synchronized (this.m_locks[hash])
/*  75:    */     {
/*  76:205 */       Node n = this.m_buckets[hash];
/*  77:207 */       if (n == null)
/*  78:    */       {
/*  79:209 */         n = new Node(null);
/*  80:210 */         n.key = key;
/*  81:211 */         n.value = value;
/*  82:212 */         this.m_buckets[hash] = n;
/*  83:213 */         this.m_locks[hash].size += 1;
/*  84:214 */         return null;
/*  85:    */       }
/*  86:220 */       for (Node next = n; next != null; next = next.next)
/*  87:    */       {
/*  88:222 */         n = next;
/*  89:224 */         if ((n.key == key) || ((n.key != null) && (n.key.equals(key))))
/*  90:    */         {
/*  91:226 */           Object returnVal = n.value;
/*  92:227 */           n.value = value;
/*  93:228 */           return returnVal;
/*  94:    */         }
/*  95:    */       }
/*  96:234 */       Node newNode = new Node(null);
/*  97:235 */       newNode.key = key;
/*  98:236 */       newNode.value = value;
/*  99:237 */       n.next = newNode;
/* 100:238 */       this.m_locks[hash].size += 1;
/* 101:    */     }
/* 102:241 */     return null;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public Object get(Object key)
/* 106:    */   {
/* 107:249 */     int hash = getHash(key);
/* 108:251 */     synchronized (this.m_locks[hash])
/* 109:    */     {
/* 110:253 */       Node n = this.m_buckets[hash];
/* 111:255 */       while (n != null)
/* 112:    */       {
/* 113:257 */         if ((n.key == key) || ((n.key != null) && (n.key.equals(key)))) {
/* 114:259 */           return n.value;
/* 115:    */         }
/* 116:262 */         n = n.next;
/* 117:    */       }
/* 118:    */     }
/* 119:266 */     return null;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public boolean containsKey(Object key)
/* 123:    */   {
/* 124:274 */     int hash = getHash(key);
/* 125:276 */     synchronized (this.m_locks[hash])
/* 126:    */     {
/* 127:278 */       Node n = this.m_buckets[hash];
/* 128:280 */       while (n != null)
/* 129:    */       {
/* 130:282 */         if ((n.key == key) || ((n.key != null) && (n.key.equals(key)))) {
/* 131:284 */           return true;
/* 132:    */         }
/* 133:287 */         n = n.next;
/* 134:    */       }
/* 135:    */     }
/* 136:291 */     return false;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public boolean containsValue(Object value)
/* 140:    */   {
/* 141:299 */     for (int i = 0; i < this.m_buckets.length; i++) {
/* 142:301 */       synchronized (this.m_locks[i])
/* 143:    */       {
/* 144:303 */         Node n = this.m_buckets[i];
/* 145:305 */         while (n != null)
/* 146:    */         {
/* 147:307 */           if ((n.value == value) || ((n.value != null) && (n.value.equals(value)))) {
/* 148:310 */             return true;
/* 149:    */           }
/* 150:313 */           n = n.next;
/* 151:    */         }
/* 152:    */       }
/* 153:    */     }
/* 154:318 */     return false;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public Collection values()
/* 158:    */   {
/* 159:326 */     return new Values(null);
/* 160:    */   }
/* 161:    */   
/* 162:    */   public Set entrySet()
/* 163:    */   {
/* 164:334 */     return new EntrySet(null);
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void putAll(Map other)
/* 168:    */   {
/* 169:342 */     Iterator i = other.keySet().iterator();
/* 170:344 */     while (i.hasNext())
/* 171:    */     {
/* 172:346 */       Object key = i.next();
/* 173:347 */       put(key, other.get(key));
/* 174:    */     }
/* 175:    */   }
/* 176:    */   
/* 177:    */   public Object remove(Object key)
/* 178:    */   {
/* 179:356 */     int hash = getHash(key);
/* 180:358 */     synchronized (this.m_locks[hash])
/* 181:    */     {
/* 182:360 */       Node n = this.m_buckets[hash];
/* 183:361 */       Node prev = null;
/* 184:363 */       while (n != null)
/* 185:    */       {
/* 186:365 */         if ((n.key == key) || ((n.key != null) && (n.key.equals(key))))
/* 187:    */         {
/* 188:368 */           if (null == prev) {
/* 189:371 */             this.m_buckets[hash] = n.next;
/* 190:    */           } else {
/* 191:376 */             prev.next = n.next;
/* 192:    */           }
/* 193:378 */           this.m_locks[hash].size -= 1;
/* 194:379 */           return n.value;
/* 195:    */         }
/* 196:382 */         prev = n;
/* 197:383 */         n = n.next;
/* 198:    */       }
/* 199:    */     }
/* 200:387 */     return null;
/* 201:    */   }
/* 202:    */   
/* 203:    */   public final boolean isEmpty()
/* 204:    */   {
/* 205:395 */     return size() == 0;
/* 206:    */   }
/* 207:    */   
/* 208:    */   public final void clear()
/* 209:    */   {
/* 210:403 */     for (int i = 0; i < this.m_buckets.length; i++)
/* 211:    */     {
/* 212:405 */       Lock lock = this.m_locks[i];
/* 213:406 */       synchronized (lock)
/* 214:    */       {
/* 215:407 */         this.m_buckets[i] = null;
/* 216:408 */         lock.size = 0;
/* 217:    */       }
/* 218:    */     }
/* 219:    */   }
/* 220:    */   
/* 221:    */   public final boolean equals(Object obj)
/* 222:    */   {
/* 223:418 */     if (obj == null) {
/* 224:418 */       return false;
/* 225:    */     }
/* 226:419 */     if (obj == this) {
/* 227:419 */       return true;
/* 228:    */     }
/* 229:421 */     if (!(obj instanceof Map)) {
/* 230:421 */       return false;
/* 231:    */     }
/* 232:423 */     Map other = (Map)obj;
/* 233:    */     
/* 234:425 */     return entrySet().equals(other.entrySet());
/* 235:    */   }
/* 236:    */   
/* 237:    */   public final int hashCode()
/* 238:    */   {
/* 239:433 */     int hashCode = 0;
/* 240:435 */     for (int i = 0; i < this.m_buckets.length; i++) {
/* 241:437 */       synchronized (this.m_locks[i])
/* 242:    */       {
/* 243:439 */         Node n = this.m_buckets[i];
/* 244:441 */         while (n != null)
/* 245:    */         {
/* 246:443 */           hashCode += n.hashCode();
/* 247:444 */           n = n.next;
/* 248:    */         }
/* 249:    */       }
/* 250:    */     }
/* 251:448 */     return hashCode;
/* 252:    */   }
/* 253:    */   
/* 254:    */   private static final class Node
/* 255:    */     implements Map.Entry, KeyValue
/* 256:    */   {
/* 257:    */     protected Object key;
/* 258:    */     protected Object value;
/* 259:    */     protected Node next;
/* 260:    */     
/* 261:    */     Node(StaticBucketMap.1 x0)
/* 262:    */     {
/* 263:454 */       this();
/* 264:    */     }
/* 265:    */     
/* 266:    */     public Object getKey()
/* 267:    */     {
/* 268:462 */       return this.key;
/* 269:    */     }
/* 270:    */     
/* 271:    */     public Object getValue()
/* 272:    */     {
/* 273:467 */       return this.value;
/* 274:    */     }
/* 275:    */     
/* 276:    */     public int hashCode()
/* 277:    */     {
/* 278:472 */       return (this.key == null ? 0 : this.key.hashCode()) ^ (this.value == null ? 0 : this.value.hashCode());
/* 279:    */     }
/* 280:    */     
/* 281:    */     public boolean equals(Object o)
/* 282:    */     {
/* 283:477 */       if (o == null) {
/* 284:477 */         return false;
/* 285:    */       }
/* 286:478 */       if (o == this) {
/* 287:478 */         return true;
/* 288:    */       }
/* 289:480 */       if (!(o instanceof Map.Entry)) {
/* 290:481 */         return false;
/* 291:    */       }
/* 292:483 */       Map.Entry e2 = (Map.Entry)o;
/* 293:    */       
/* 294:485 */       return (this.key == null ? e2.getKey() == null : this.key.equals(e2.getKey())) && (this.value == null ? e2.getValue() == null : this.value.equals(e2.getValue()));
/* 295:    */     }
/* 296:    */     
/* 297:    */     public Object setValue(Object val)
/* 298:    */     {
/* 299:493 */       Object retVal = this.value;
/* 300:494 */       this.value = val;
/* 301:495 */       return retVal;
/* 302:    */     }
/* 303:    */     
/* 304:    */     private Node() {}
/* 305:    */   }
/* 306:    */   
/* 307:    */   private static final class Lock
/* 308:    */   {
/* 309:    */     public int size;
/* 310:    */     
/* 311:    */     private Lock() {}
/* 312:    */     
/* 313:    */     Lock(StaticBucketMap.1 x0)
/* 314:    */     {
/* 315:499 */       this();
/* 316:    */     }
/* 317:    */   }
/* 318:    */   
/* 319:    */   private class EntryIterator
/* 320:    */     implements Iterator
/* 321:    */   {
/* 322:    */     EntryIterator(StaticBucketMap.1 x1)
/* 323:    */     {
/* 324:506 */       this();
/* 325:    */     }
/* 326:    */     
/* 327:508 */     private ArrayList current = new ArrayList();
/* 328:    */     private int bucket;
/* 329:    */     private Map.Entry last;
/* 330:    */     
/* 331:    */     public boolean hasNext()
/* 332:    */     {
/* 333:514 */       if (this.current.size() > 0) {
/* 334:514 */         return true;
/* 335:    */       }
/* 336:515 */       while (this.bucket < StaticBucketMap.this.m_buckets.length) {
/* 337:516 */         synchronized (StaticBucketMap.this.m_locks[this.bucket])
/* 338:    */         {
/* 339:517 */           StaticBucketMap.Node n = StaticBucketMap.this.m_buckets[this.bucket];
/* 340:518 */           while (n != null)
/* 341:    */           {
/* 342:519 */             this.current.add(n);
/* 343:520 */             n = n.next;
/* 344:    */           }
/* 345:522 */           this.bucket += 1;
/* 346:523 */           if (this.current.size() > 0) {
/* 347:523 */             return true;
/* 348:    */           }
/* 349:    */         }
/* 350:    */       }
/* 351:526 */       return false;
/* 352:    */     }
/* 353:    */     
/* 354:    */     protected Map.Entry nextEntry()
/* 355:    */     {
/* 356:530 */       if (!hasNext()) {
/* 357:530 */         throw new NoSuchElementException();
/* 358:    */       }
/* 359:531 */       this.last = ((Map.Entry)this.current.remove(this.current.size() - 1));
/* 360:532 */       return this.last;
/* 361:    */     }
/* 362:    */     
/* 363:    */     public Object next()
/* 364:    */     {
/* 365:536 */       return nextEntry();
/* 366:    */     }
/* 367:    */     
/* 368:    */     public void remove()
/* 369:    */     {
/* 370:540 */       if (this.last == null) {
/* 371:540 */         throw new IllegalStateException();
/* 372:    */       }
/* 373:541 */       StaticBucketMap.this.remove(this.last.getKey());
/* 374:542 */       this.last = null;
/* 375:    */     }
/* 376:    */     
/* 377:    */     private EntryIterator() {}
/* 378:    */   }
/* 379:    */   
/* 380:    */   private class ValueIterator
/* 381:    */     extends StaticBucketMap.EntryIterator
/* 382:    */   {
/* 383:    */     ValueIterator(StaticBucketMap.1 x1)
/* 384:    */     {
/* 385:547 */       this();
/* 386:    */     }
/* 387:    */     
/* 388:    */     private ValueIterator()
/* 389:    */     {
/* 390:547 */       super(null);
/* 391:    */     }
/* 392:    */     
/* 393:    */     public Object next()
/* 394:    */     {
/* 395:550 */       return nextEntry().getValue();
/* 396:    */     }
/* 397:    */   }
/* 398:    */   
/* 399:    */   private class KeyIterator
/* 400:    */     extends StaticBucketMap.EntryIterator
/* 401:    */   {
/* 402:    */     KeyIterator(StaticBucketMap.1 x1)
/* 403:    */     {
/* 404:555 */       this();
/* 405:    */     }
/* 406:    */     
/* 407:    */     private KeyIterator()
/* 408:    */     {
/* 409:555 */       super(null);
/* 410:    */     }
/* 411:    */     
/* 412:    */     public Object next()
/* 413:    */     {
/* 414:558 */       return nextEntry().getKey();
/* 415:    */     }
/* 416:    */   }
/* 417:    */   
/* 418:    */   private class EntrySet
/* 419:    */     extends AbstractSet
/* 420:    */   {
/* 421:    */     EntrySet(StaticBucketMap.1 x1)
/* 422:    */     {
/* 423:563 */       this();
/* 424:    */     }
/* 425:    */     
/* 426:    */     public int size()
/* 427:    */     {
/* 428:566 */       return StaticBucketMap.this.size();
/* 429:    */     }
/* 430:    */     
/* 431:    */     public void clear()
/* 432:    */     {
/* 433:570 */       StaticBucketMap.this.clear();
/* 434:    */     }
/* 435:    */     
/* 436:    */     public Iterator iterator()
/* 437:    */     {
/* 438:574 */       return new StaticBucketMap.EntryIterator(StaticBucketMap.this, null);
/* 439:    */     }
/* 440:    */     
/* 441:    */     public boolean contains(Object o)
/* 442:    */     {
/* 443:578 */       Map.Entry entry = (Map.Entry)o;
/* 444:579 */       int hash = StaticBucketMap.this.getHash(entry.getKey());
/* 445:580 */       synchronized (StaticBucketMap.this.m_locks[hash])
/* 446:    */       {
/* 447:581 */         for (StaticBucketMap.Node n = StaticBucketMap.this.m_buckets[hash]; n != null; n = n.next) {
/* 448:582 */           if (n.equals(entry)) {
/* 449:582 */             return true;
/* 450:    */           }
/* 451:    */         }
/* 452:    */       }
/* 453:585 */       return false;
/* 454:    */     }
/* 455:    */     
/* 456:    */     public boolean remove(Object obj)
/* 457:    */     {
/* 458:589 */       if (!(obj instanceof Map.Entry)) {
/* 459:590 */         return false;
/* 460:    */       }
/* 461:592 */       Map.Entry entry = (Map.Entry)obj;
/* 462:593 */       int hash = StaticBucketMap.this.getHash(entry.getKey());
/* 463:594 */       synchronized (StaticBucketMap.this.m_locks[hash])
/* 464:    */       {
/* 465:595 */         for (StaticBucketMap.Node n = StaticBucketMap.this.m_buckets[hash]; n != null; n = n.next) {
/* 466:596 */           if (n.equals(entry))
/* 467:    */           {
/* 468:597 */             StaticBucketMap.this.remove(n.getKey());
/* 469:598 */             return true;
/* 470:    */           }
/* 471:    */         }
/* 472:    */       }
/* 473:602 */       return false;
/* 474:    */     }
/* 475:    */     
/* 476:    */     private EntrySet() {}
/* 477:    */   }
/* 478:    */   
/* 479:    */   private class KeySet
/* 480:    */     extends AbstractSet
/* 481:    */   {
/* 482:    */     KeySet(StaticBucketMap.1 x1)
/* 483:    */     {
/* 484:608 */       this();
/* 485:    */     }
/* 486:    */     
/* 487:    */     public int size()
/* 488:    */     {
/* 489:611 */       return StaticBucketMap.this.size();
/* 490:    */     }
/* 491:    */     
/* 492:    */     public void clear()
/* 493:    */     {
/* 494:615 */       StaticBucketMap.this.clear();
/* 495:    */     }
/* 496:    */     
/* 497:    */     public Iterator iterator()
/* 498:    */     {
/* 499:619 */       return new StaticBucketMap.KeyIterator(StaticBucketMap.this, null);
/* 500:    */     }
/* 501:    */     
/* 502:    */     public boolean contains(Object o)
/* 503:    */     {
/* 504:623 */       return StaticBucketMap.this.containsKey(o);
/* 505:    */     }
/* 506:    */     
/* 507:    */     public boolean remove(Object o)
/* 508:    */     {
/* 509:627 */       int hash = StaticBucketMap.this.getHash(o);
/* 510:628 */       synchronized (StaticBucketMap.this.m_locks[hash])
/* 511:    */       {
/* 512:629 */         for (StaticBucketMap.Node n = StaticBucketMap.this.m_buckets[hash]; n != null; n = n.next)
/* 513:    */         {
/* 514:630 */           Object k = n.getKey();
/* 515:631 */           if ((k == o) || ((k != null) && (k.equals(o))))
/* 516:    */           {
/* 517:632 */             StaticBucketMap.this.remove(k);
/* 518:633 */             return true;
/* 519:    */           }
/* 520:    */         }
/* 521:    */       }
/* 522:637 */       return false;
/* 523:    */     }
/* 524:    */     
/* 525:    */     private KeySet() {}
/* 526:    */   }
/* 527:    */   
/* 528:    */   private class Values
/* 529:    */     extends AbstractCollection
/* 530:    */   {
/* 531:    */     Values(StaticBucketMap.1 x1)
/* 532:    */     {
/* 533:644 */       this();
/* 534:    */     }
/* 535:    */     
/* 536:    */     public int size()
/* 537:    */     {
/* 538:647 */       return StaticBucketMap.this.size();
/* 539:    */     }
/* 540:    */     
/* 541:    */     public void clear()
/* 542:    */     {
/* 543:651 */       StaticBucketMap.this.clear();
/* 544:    */     }
/* 545:    */     
/* 546:    */     public Iterator iterator()
/* 547:    */     {
/* 548:655 */       return new StaticBucketMap.ValueIterator(StaticBucketMap.this, null);
/* 549:    */     }
/* 550:    */     
/* 551:    */     private Values() {}
/* 552:    */   }
/* 553:    */   
/* 554:    */   public void atomic(Runnable r)
/* 555:    */   {
/* 556:696 */     if (r == null) {
/* 557:696 */       throw new NullPointerException();
/* 558:    */     }
/* 559:697 */     atomic(r, 0);
/* 560:    */   }
/* 561:    */   
/* 562:    */   private void atomic(Runnable r, int bucket)
/* 563:    */   {
/* 564:701 */     if (bucket >= this.m_buckets.length)
/* 565:    */     {
/* 566:702 */       r.run();
/* 567:703 */       return;
/* 568:    */     }
/* 569:705 */     synchronized (this.m_locks[bucket])
/* 570:    */     {
/* 571:706 */       atomic(r, bucket + 1);
/* 572:    */     }
/* 573:    */   }
/* 574:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.StaticBucketMap
 * JD-Core Version:    0.7.0.1
 */