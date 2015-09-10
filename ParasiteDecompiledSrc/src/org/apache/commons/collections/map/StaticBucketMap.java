/*   1:    */ package org.apache.commons.collections.map;
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
/*  12:    */ import org.apache.commons.collections.KeyValue;
/*  13:    */ 
/*  14:    */ public final class StaticBucketMap
/*  15:    */   implements Map
/*  16:    */ {
/*  17:    */   private static final int DEFAULT_BUCKETS = 255;
/*  18:    */   private Node[] buckets;
/*  19:    */   private Lock[] locks;
/*  20:    */   
/*  21:    */   public StaticBucketMap()
/*  22:    */   {
/*  23:117 */     this(255);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public StaticBucketMap(int numBuckets)
/*  27:    */   {
/*  28:131 */     int size = Math.max(17, numBuckets);
/*  29:134 */     if (size % 2 == 0) {
/*  30:135 */       size--;
/*  31:    */     }
/*  32:138 */     this.buckets = new Node[size];
/*  33:139 */     this.locks = new Lock[size];
/*  34:141 */     for (int i = 0; i < size; i++) {
/*  35:142 */       this.locks[i] = new Lock(null);
/*  36:    */     }
/*  37:    */   }
/*  38:    */   
/*  39:    */   private final int getHash(Object key)
/*  40:    */   {
/*  41:161 */     if (key == null) {
/*  42:162 */       return 0;
/*  43:    */     }
/*  44:164 */     int hash = key.hashCode();
/*  45:165 */     hash += (hash << 15 ^ 0xFFFFFFFF);
/*  46:166 */     hash ^= hash >>> 10;
/*  47:167 */     hash += (hash << 3);
/*  48:168 */     hash ^= hash >>> 6;
/*  49:169 */     hash += (hash << 11 ^ 0xFFFFFFFF);
/*  50:170 */     hash ^= hash >>> 16;
/*  51:171 */     hash %= this.buckets.length;
/*  52:172 */     return hash < 0 ? hash * -1 : hash;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public int size()
/*  56:    */   {
/*  57:182 */     int cnt = 0;
/*  58:184 */     for (int i = 0; i < this.buckets.length; i++) {
/*  59:185 */       cnt += this.locks[i].size;
/*  60:    */     }
/*  61:187 */     return cnt;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public boolean isEmpty()
/*  65:    */   {
/*  66:196 */     return size() == 0;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public Object get(Object key)
/*  70:    */   {
/*  71:206 */     int hash = getHash(key);
/*  72:208 */     synchronized (this.locks[hash])
/*  73:    */     {
/*  74:209 */       Node n = this.buckets[hash];
/*  75:211 */       while (n != null)
/*  76:    */       {
/*  77:212 */         if ((n.key == key) || ((n.key != null) && (n.key.equals(key)))) {
/*  78:213 */           return n.value;
/*  79:    */         }
/*  80:216 */         n = n.next;
/*  81:    */       }
/*  82:    */     }
/*  83:219 */     return null;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public boolean containsKey(Object key)
/*  87:    */   {
/*  88:229 */     int hash = getHash(key);
/*  89:231 */     synchronized (this.locks[hash])
/*  90:    */     {
/*  91:232 */       Node n = this.buckets[hash];
/*  92:234 */       while (n != null)
/*  93:    */       {
/*  94:235 */         if ((n.key == key) || ((n.key != null) && (n.key.equals(key)))) {
/*  95:236 */           return true;
/*  96:    */         }
/*  97:239 */         n = n.next;
/*  98:    */       }
/*  99:    */     }
/* 100:242 */     return false;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public boolean containsValue(Object value)
/* 104:    */   {
/* 105:252 */     for (int i = 0; i < this.buckets.length; i++) {
/* 106:253 */       synchronized (this.locks[i])
/* 107:    */       {
/* 108:254 */         Node n = this.buckets[i];
/* 109:256 */         while (n != null)
/* 110:    */         {
/* 111:257 */           if ((n.value == value) || ((n.value != null) && (n.value.equals(value)))) {
/* 112:258 */             return true;
/* 113:    */           }
/* 114:261 */           n = n.next;
/* 115:    */         }
/* 116:    */       }
/* 117:    */     }
/* 118:265 */     return false;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public Object put(Object key, Object value)
/* 122:    */   {
/* 123:277 */     int hash = getHash(key);
/* 124:279 */     synchronized (this.locks[hash])
/* 125:    */     {
/* 126:280 */       Node n = this.buckets[hash];
/* 127:282 */       if (n == null)
/* 128:    */       {
/* 129:283 */         n = new Node(null);
/* 130:284 */         n.key = key;
/* 131:285 */         n.value = value;
/* 132:286 */         this.buckets[hash] = n;
/* 133:287 */         this.locks[hash].size += 1;
/* 134:288 */         return null;
/* 135:    */       }
/* 136:294 */       for (Node next = n; next != null; next = next.next)
/* 137:    */       {
/* 138:295 */         n = next;
/* 139:297 */         if ((n.key == key) || ((n.key != null) && (n.key.equals(key))))
/* 140:    */         {
/* 141:298 */           Object returnVal = n.value;
/* 142:299 */           n.value = value;
/* 143:300 */           return returnVal;
/* 144:    */         }
/* 145:    */       }
/* 146:306 */       Node newNode = new Node(null);
/* 147:307 */       newNode.key = key;
/* 148:308 */       newNode.value = value;
/* 149:309 */       n.next = newNode;
/* 150:310 */       this.locks[hash].size += 1;
/* 151:    */     }
/* 152:312 */     return null;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public Object remove(Object key)
/* 156:    */   {
/* 157:322 */     int hash = getHash(key);
/* 158:324 */     synchronized (this.locks[hash])
/* 159:    */     {
/* 160:325 */       Node n = this.buckets[hash];
/* 161:326 */       Node prev = null;
/* 162:328 */       while (n != null)
/* 163:    */       {
/* 164:329 */         if ((n.key == key) || ((n.key != null) && (n.key.equals(key))))
/* 165:    */         {
/* 166:331 */           if (null == prev) {
/* 167:333 */             this.buckets[hash] = n.next;
/* 168:    */           } else {
/* 169:336 */             prev.next = n.next;
/* 170:    */           }
/* 171:338 */           this.locks[hash].size -= 1;
/* 172:339 */           return n.value;
/* 173:    */         }
/* 174:342 */         prev = n;
/* 175:343 */         n = n.next;
/* 176:    */       }
/* 177:    */     }
/* 178:346 */     return null;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public Set keySet()
/* 182:    */   {
/* 183:356 */     return new KeySet(null);
/* 184:    */   }
/* 185:    */   
/* 186:    */   public Collection values()
/* 187:    */   {
/* 188:365 */     return new Values(null);
/* 189:    */   }
/* 190:    */   
/* 191:    */   public Set entrySet()
/* 192:    */   {
/* 193:374 */     return new EntrySet(null);
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void putAll(Map map)
/* 197:    */   {
/* 198:385 */     Iterator i = map.keySet().iterator();
/* 199:387 */     while (i.hasNext())
/* 200:    */     {
/* 201:388 */       Object key = i.next();
/* 202:389 */       put(key, map.get(key));
/* 203:    */     }
/* 204:    */   }
/* 205:    */   
/* 206:    */   public void clear()
/* 207:    */   {
/* 208:397 */     for (int i = 0; i < this.buckets.length; i++)
/* 209:    */     {
/* 210:398 */       Lock lock = this.locks[i];
/* 211:399 */       synchronized (lock)
/* 212:    */       {
/* 213:400 */         this.buckets[i] = null;
/* 214:401 */         lock.size = 0;
/* 215:    */       }
/* 216:    */     }
/* 217:    */   }
/* 218:    */   
/* 219:    */   public boolean equals(Object obj)
/* 220:    */   {
/* 221:413 */     if (obj == this) {
/* 222:414 */       return true;
/* 223:    */     }
/* 224:416 */     if (!(obj instanceof Map)) {
/* 225:417 */       return false;
/* 226:    */     }
/* 227:419 */     Map other = (Map)obj;
/* 228:420 */     return entrySet().equals(other.entrySet());
/* 229:    */   }
/* 230:    */   
/* 231:    */   public int hashCode()
/* 232:    */   {
/* 233:429 */     int hashCode = 0;
/* 234:431 */     for (int i = 0; i < this.buckets.length; i++) {
/* 235:432 */       synchronized (this.locks[i])
/* 236:    */       {
/* 237:433 */         Node n = this.buckets[i];
/* 238:435 */         while (n != null)
/* 239:    */         {
/* 240:436 */           hashCode += n.hashCode();
/* 241:437 */           n = n.next;
/* 242:    */         }
/* 243:    */       }
/* 244:    */     }
/* 245:441 */     return hashCode;
/* 246:    */   }
/* 247:    */   
/* 248:    */   private static final class Node
/* 249:    */     implements Map.Entry, KeyValue
/* 250:    */   {
/* 251:    */     protected Object key;
/* 252:    */     protected Object value;
/* 253:    */     protected Node next;
/* 254:    */     
/* 255:    */     Node(StaticBucketMap.1 x0)
/* 256:    */     {
/* 257:448 */       this();
/* 258:    */     }
/* 259:    */     
/* 260:    */     public Object getKey()
/* 261:    */     {
/* 262:454 */       return this.key;
/* 263:    */     }
/* 264:    */     
/* 265:    */     public Object getValue()
/* 266:    */     {
/* 267:458 */       return this.value;
/* 268:    */     }
/* 269:    */     
/* 270:    */     public int hashCode()
/* 271:    */     {
/* 272:462 */       return (this.key == null ? 0 : this.key.hashCode()) ^ (this.value == null ? 0 : this.value.hashCode());
/* 273:    */     }
/* 274:    */     
/* 275:    */     public boolean equals(Object obj)
/* 276:    */     {
/* 277:467 */       if (obj == this) {
/* 278:468 */         return true;
/* 279:    */       }
/* 280:470 */       if (!(obj instanceof Map.Entry)) {
/* 281:471 */         return false;
/* 282:    */       }
/* 283:474 */       Map.Entry e2 = (Map.Entry)obj;
/* 284:475 */       return (this.key == null ? e2.getKey() == null : this.key.equals(e2.getKey())) && (this.value == null ? e2.getValue() == null : this.value.equals(e2.getValue()));
/* 285:    */     }
/* 286:    */     
/* 287:    */     public Object setValue(Object obj)
/* 288:    */     {
/* 289:481 */       Object retVal = this.value;
/* 290:482 */       this.value = obj;
/* 291:483 */       return retVal;
/* 292:    */     }
/* 293:    */     
/* 294:    */     private Node() {}
/* 295:    */   }
/* 296:    */   
/* 297:    */   private static final class Lock
/* 298:    */   {
/* 299:    */     public int size;
/* 300:    */     
/* 301:    */     private Lock() {}
/* 302:    */     
/* 303:    */     Lock(StaticBucketMap.1 x0)
/* 304:    */     {
/* 305:491 */       this();
/* 306:    */     }
/* 307:    */   }
/* 308:    */   
/* 309:    */   private class EntryIterator
/* 310:    */     implements Iterator
/* 311:    */   {
/* 312:    */     EntryIterator(StaticBucketMap.1 x1)
/* 313:    */     {
/* 314:497 */       this();
/* 315:    */     }
/* 316:    */     
/* 317:499 */     private ArrayList current = new ArrayList();
/* 318:    */     private int bucket;
/* 319:    */     private Map.Entry last;
/* 320:    */     
/* 321:    */     public boolean hasNext()
/* 322:    */     {
/* 323:505 */       if (this.current.size() > 0) {
/* 324:505 */         return true;
/* 325:    */       }
/* 326:506 */       while (this.bucket < StaticBucketMap.this.buckets.length) {
/* 327:507 */         synchronized (StaticBucketMap.this.locks[this.bucket])
/* 328:    */         {
/* 329:508 */           StaticBucketMap.Node n = StaticBucketMap.this.buckets[this.bucket];
/* 330:509 */           while (n != null)
/* 331:    */           {
/* 332:510 */             this.current.add(n);
/* 333:511 */             n = n.next;
/* 334:    */           }
/* 335:513 */           this.bucket += 1;
/* 336:514 */           if (this.current.size() > 0) {
/* 337:514 */             return true;
/* 338:    */           }
/* 339:    */         }
/* 340:    */       }
/* 341:517 */       return false;
/* 342:    */     }
/* 343:    */     
/* 344:    */     protected Map.Entry nextEntry()
/* 345:    */     {
/* 346:521 */       if (!hasNext()) {
/* 347:521 */         throw new NoSuchElementException();
/* 348:    */       }
/* 349:522 */       this.last = ((Map.Entry)this.current.remove(this.current.size() - 1));
/* 350:523 */       return this.last;
/* 351:    */     }
/* 352:    */     
/* 353:    */     public Object next()
/* 354:    */     {
/* 355:527 */       return nextEntry();
/* 356:    */     }
/* 357:    */     
/* 358:    */     public void remove()
/* 359:    */     {
/* 360:531 */       if (this.last == null) {
/* 361:531 */         throw new IllegalStateException();
/* 362:    */       }
/* 363:532 */       StaticBucketMap.this.remove(this.last.getKey());
/* 364:533 */       this.last = null;
/* 365:    */     }
/* 366:    */     
/* 367:    */     private EntryIterator() {}
/* 368:    */   }
/* 369:    */   
/* 370:    */   private class ValueIterator
/* 371:    */     extends StaticBucketMap.EntryIterator
/* 372:    */   {
/* 373:    */     ValueIterator(StaticBucketMap.1 x1)
/* 374:    */     {
/* 375:538 */       this();
/* 376:    */     }
/* 377:    */     
/* 378:    */     private ValueIterator()
/* 379:    */     {
/* 380:538 */       super(null);
/* 381:    */     }
/* 382:    */     
/* 383:    */     public Object next()
/* 384:    */     {
/* 385:541 */       return nextEntry().getValue();
/* 386:    */     }
/* 387:    */   }
/* 388:    */   
/* 389:    */   private class KeyIterator
/* 390:    */     extends StaticBucketMap.EntryIterator
/* 391:    */   {
/* 392:    */     KeyIterator(StaticBucketMap.1 x1)
/* 393:    */     {
/* 394:546 */       this();
/* 395:    */     }
/* 396:    */     
/* 397:    */     private KeyIterator()
/* 398:    */     {
/* 399:546 */       super(null);
/* 400:    */     }
/* 401:    */     
/* 402:    */     public Object next()
/* 403:    */     {
/* 404:549 */       return nextEntry().getKey();
/* 405:    */     }
/* 406:    */   }
/* 407:    */   
/* 408:    */   private class EntrySet
/* 409:    */     extends AbstractSet
/* 410:    */   {
/* 411:    */     EntrySet(StaticBucketMap.1 x1)
/* 412:    */     {
/* 413:554 */       this();
/* 414:    */     }
/* 415:    */     
/* 416:    */     public int size()
/* 417:    */     {
/* 418:557 */       return StaticBucketMap.this.size();
/* 419:    */     }
/* 420:    */     
/* 421:    */     public void clear()
/* 422:    */     {
/* 423:561 */       StaticBucketMap.this.clear();
/* 424:    */     }
/* 425:    */     
/* 426:    */     public Iterator iterator()
/* 427:    */     {
/* 428:565 */       return new StaticBucketMap.EntryIterator(StaticBucketMap.this, null);
/* 429:    */     }
/* 430:    */     
/* 431:    */     public boolean contains(Object obj)
/* 432:    */     {
/* 433:569 */       Map.Entry entry = (Map.Entry)obj;
/* 434:570 */       int hash = StaticBucketMap.this.getHash(entry.getKey());
/* 435:571 */       synchronized (StaticBucketMap.this.locks[hash])
/* 436:    */       {
/* 437:572 */         for (StaticBucketMap.Node n = StaticBucketMap.this.buckets[hash]; n != null; n = n.next) {
/* 438:573 */           if (n.equals(entry)) {
/* 439:573 */             return true;
/* 440:    */           }
/* 441:    */         }
/* 442:    */       }
/* 443:576 */       return false;
/* 444:    */     }
/* 445:    */     
/* 446:    */     public boolean remove(Object obj)
/* 447:    */     {
/* 448:580 */       if (!(obj instanceof Map.Entry)) {
/* 449:581 */         return false;
/* 450:    */       }
/* 451:583 */       Map.Entry entry = (Map.Entry)obj;
/* 452:584 */       int hash = StaticBucketMap.this.getHash(entry.getKey());
/* 453:585 */       synchronized (StaticBucketMap.this.locks[hash])
/* 454:    */       {
/* 455:586 */         for (StaticBucketMap.Node n = StaticBucketMap.this.buckets[hash]; n != null; n = n.next) {
/* 456:587 */           if (n.equals(entry))
/* 457:    */           {
/* 458:588 */             StaticBucketMap.this.remove(n.getKey());
/* 459:589 */             return true;
/* 460:    */           }
/* 461:    */         }
/* 462:    */       }
/* 463:593 */       return false;
/* 464:    */     }
/* 465:    */     
/* 466:    */     private EntrySet() {}
/* 467:    */   }
/* 468:    */   
/* 469:    */   private class KeySet
/* 470:    */     extends AbstractSet
/* 471:    */   {
/* 472:    */     KeySet(StaticBucketMap.1 x1)
/* 473:    */     {
/* 474:599 */       this();
/* 475:    */     }
/* 476:    */     
/* 477:    */     public int size()
/* 478:    */     {
/* 479:602 */       return StaticBucketMap.this.size();
/* 480:    */     }
/* 481:    */     
/* 482:    */     public void clear()
/* 483:    */     {
/* 484:606 */       StaticBucketMap.this.clear();
/* 485:    */     }
/* 486:    */     
/* 487:    */     public Iterator iterator()
/* 488:    */     {
/* 489:610 */       return new StaticBucketMap.KeyIterator(StaticBucketMap.this, null);
/* 490:    */     }
/* 491:    */     
/* 492:    */     public boolean contains(Object obj)
/* 493:    */     {
/* 494:614 */       return StaticBucketMap.this.containsKey(obj);
/* 495:    */     }
/* 496:    */     
/* 497:    */     public boolean remove(Object obj)
/* 498:    */     {
/* 499:618 */       int hash = StaticBucketMap.this.getHash(obj);
/* 500:619 */       synchronized (StaticBucketMap.this.locks[hash])
/* 501:    */       {
/* 502:620 */         for (StaticBucketMap.Node n = StaticBucketMap.this.buckets[hash]; n != null; n = n.next)
/* 503:    */         {
/* 504:621 */           Object k = n.getKey();
/* 505:622 */           if ((k == obj) || ((k != null) && (k.equals(obj))))
/* 506:    */           {
/* 507:623 */             StaticBucketMap.this.remove(k);
/* 508:624 */             return true;
/* 509:    */           }
/* 510:    */         }
/* 511:    */       }
/* 512:628 */       return false;
/* 513:    */     }
/* 514:    */     
/* 515:    */     private KeySet() {}
/* 516:    */   }
/* 517:    */   
/* 518:    */   private class Values
/* 519:    */     extends AbstractCollection
/* 520:    */   {
/* 521:    */     Values(StaticBucketMap.1 x1)
/* 522:    */     {
/* 523:635 */       this();
/* 524:    */     }
/* 525:    */     
/* 526:    */     public int size()
/* 527:    */     {
/* 528:638 */       return StaticBucketMap.this.size();
/* 529:    */     }
/* 530:    */     
/* 531:    */     public void clear()
/* 532:    */     {
/* 533:642 */       StaticBucketMap.this.clear();
/* 534:    */     }
/* 535:    */     
/* 536:    */     public Iterator iterator()
/* 537:    */     {
/* 538:646 */       return new StaticBucketMap.ValueIterator(StaticBucketMap.this, null);
/* 539:    */     }
/* 540:    */     
/* 541:    */     private Values() {}
/* 542:    */   }
/* 543:    */   
/* 544:    */   public void atomic(Runnable r)
/* 545:    */   {
/* 546:687 */     if (r == null) {
/* 547:687 */       throw new NullPointerException();
/* 548:    */     }
/* 549:688 */     atomic(r, 0);
/* 550:    */   }
/* 551:    */   
/* 552:    */   private void atomic(Runnable r, int bucket)
/* 553:    */   {
/* 554:692 */     if (bucket >= this.buckets.length)
/* 555:    */     {
/* 556:693 */       r.run();
/* 557:694 */       return;
/* 558:    */     }
/* 559:696 */     synchronized (this.locks[bucket])
/* 560:    */     {
/* 561:697 */       atomic(r, bucket + 1);
/* 562:    */     }
/* 563:    */   }
/* 564:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.map.StaticBucketMap
 * JD-Core Version:    0.7.0.1
 */