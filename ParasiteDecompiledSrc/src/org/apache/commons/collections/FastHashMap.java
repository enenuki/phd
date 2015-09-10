/*   1:    */ package org.apache.commons.collections;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.ConcurrentModificationException;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Map.Entry;
/*   9:    */ import java.util.Set;
/*  10:    */ 
/*  11:    */ public class FastHashMap
/*  12:    */   extends HashMap
/*  13:    */ {
/*  14: 71 */   protected HashMap map = null;
/*  15: 76 */   protected boolean fast = false;
/*  16:    */   
/*  17:    */   public FastHashMap()
/*  18:    */   {
/*  19: 86 */     this.map = new HashMap();
/*  20:    */   }
/*  21:    */   
/*  22:    */   public FastHashMap(int capacity)
/*  23:    */   {
/*  24: 96 */     this.map = new HashMap(capacity);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public FastHashMap(int capacity, float factor)
/*  28:    */   {
/*  29:107 */     this.map = new HashMap(capacity, factor);
/*  30:    */   }
/*  31:    */   
/*  32:    */   public FastHashMap(Map map)
/*  33:    */   {
/*  34:117 */     this.map = new HashMap(map);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public boolean getFast()
/*  38:    */   {
/*  39:130 */     return this.fast;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void setFast(boolean fast)
/*  43:    */   {
/*  44:139 */     this.fast = fast;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public Object get(Object key)
/*  48:    */   {
/*  49:158 */     if (this.fast) {
/*  50:159 */       return this.map.get(key);
/*  51:    */     }
/*  52:161 */     synchronized (this.map)
/*  53:    */     {
/*  54:162 */       return this.map.get(key);
/*  55:    */     }
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int size()
/*  59:    */   {
/*  60:173 */     if (this.fast) {
/*  61:174 */       return this.map.size();
/*  62:    */     }
/*  63:176 */     synchronized (this.map)
/*  64:    */     {
/*  65:177 */       return this.map.size();
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   public boolean isEmpty()
/*  70:    */   {
/*  71:188 */     if (this.fast) {
/*  72:189 */       return this.map.isEmpty();
/*  73:    */     }
/*  74:191 */     synchronized (this.map)
/*  75:    */     {
/*  76:192 */       return this.map.isEmpty();
/*  77:    */     }
/*  78:    */   }
/*  79:    */   
/*  80:    */   public boolean containsKey(Object key)
/*  81:    */   {
/*  82:205 */     if (this.fast) {
/*  83:206 */       return this.map.containsKey(key);
/*  84:    */     }
/*  85:208 */     synchronized (this.map)
/*  86:    */     {
/*  87:209 */       return this.map.containsKey(key);
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:    */   public boolean containsValue(Object value)
/*  92:    */   {
/*  93:222 */     if (this.fast) {
/*  94:223 */       return this.map.containsValue(value);
/*  95:    */     }
/*  96:225 */     synchronized (this.map)
/*  97:    */     {
/*  98:226 */       return this.map.containsValue(value);
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:    */   public Object put(Object key, Object value)
/* 103:    */   {
/* 104:247 */     if (this.fast) {
/* 105:248 */       synchronized (this)
/* 106:    */       {
/* 107:249 */         HashMap temp = (HashMap)this.map.clone();
/* 108:250 */         Object result = temp.put(key, value);
/* 109:251 */         this.map = temp;
/* 110:252 */         return result;
/* 111:    */       }
/* 112:    */     }
/* 113:255 */     synchronized (this.map)
/* 114:    */     {
/* 115:256 */       return this.map.put(key, value);
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void putAll(Map in)
/* 120:    */   {
/* 121:268 */     if (this.fast) {
/* 122:269 */       synchronized (this)
/* 123:    */       {
/* 124:270 */         HashMap temp = (HashMap)this.map.clone();
/* 125:271 */         temp.putAll(in);
/* 126:272 */         this.map = temp;
/* 127:    */       }
/* 128:    */     } else {
/* 129:275 */       synchronized (this.map)
/* 130:    */       {
/* 131:276 */         this.map.putAll(in);
/* 132:    */       }
/* 133:    */     }
/* 134:    */   }
/* 135:    */   
/* 136:    */   public Object remove(Object key)
/* 137:    */   {
/* 138:289 */     if (this.fast) {
/* 139:290 */       synchronized (this)
/* 140:    */       {
/* 141:291 */         HashMap temp = (HashMap)this.map.clone();
/* 142:292 */         Object result = temp.remove(key);
/* 143:293 */         this.map = temp;
/* 144:294 */         return result;
/* 145:    */       }
/* 146:    */     }
/* 147:297 */     synchronized (this.map)
/* 148:    */     {
/* 149:298 */       return this.map.remove(key);
/* 150:    */     }
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void clear()
/* 154:    */   {
/* 155:307 */     if (this.fast) {
/* 156:308 */       synchronized (this)
/* 157:    */       {
/* 158:309 */         this.map = new HashMap();
/* 159:    */       }
/* 160:    */     } else {
/* 161:312 */       synchronized (this.map)
/* 162:    */       {
/* 163:313 */         this.map.clear();
/* 164:    */       }
/* 165:    */     }
/* 166:    */   }
/* 167:    */   
/* 168:    */   public boolean equals(Object o)
/* 169:    */   {
/* 170:332 */     if (o == this) {
/* 171:333 */       return true;
/* 172:    */     }
/* 173:334 */     if (!(o instanceof Map)) {
/* 174:335 */       return false;
/* 175:    */     }
/* 176:337 */     Map mo = (Map)o;
/* 177:340 */     if (this.fast)
/* 178:    */     {
/* 179:341 */       if (mo.size() != this.map.size()) {
/* 180:342 */         return false;
/* 181:    */       }
/* 182:344 */       Iterator i = this.map.entrySet().iterator();
/* 183:345 */       while (i.hasNext())
/* 184:    */       {
/* 185:346 */         Map.Entry e = (Map.Entry)i.next();
/* 186:347 */         Object key = e.getKey();
/* 187:348 */         Object value = e.getValue();
/* 188:349 */         if (value == null)
/* 189:    */         {
/* 190:350 */           if ((mo.get(key) != null) || (!mo.containsKey(key))) {
/* 191:351 */             return false;
/* 192:    */           }
/* 193:    */         }
/* 194:354 */         else if (!value.equals(mo.get(key))) {
/* 195:355 */           return false;
/* 196:    */         }
/* 197:    */       }
/* 198:359 */       return true;
/* 199:    */     }
/* 200:362 */     synchronized (this.map)
/* 201:    */     {
/* 202:363 */       if (mo.size() != this.map.size()) {
/* 203:364 */         return false;
/* 204:    */       }
/* 205:366 */       Iterator i = this.map.entrySet().iterator();
/* 206:367 */       while (i.hasNext())
/* 207:    */       {
/* 208:368 */         Map.Entry e = (Map.Entry)i.next();
/* 209:369 */         Object key = e.getKey();
/* 210:370 */         Object value = e.getValue();
/* 211:371 */         if (value == null)
/* 212:    */         {
/* 213:372 */           if ((mo.get(key) != null) || (!mo.containsKey(key))) {
/* 214:373 */             return false;
/* 215:    */           }
/* 216:    */         }
/* 217:376 */         else if (!value.equals(mo.get(key))) {
/* 218:377 */           return false;
/* 219:    */         }
/* 220:    */       }
/* 221:381 */       return true;
/* 222:    */     }
/* 223:    */   }
/* 224:    */   
/* 225:    */   public int hashCode()
/* 226:    */   {
/* 227:394 */     if (this.fast)
/* 228:    */     {
/* 229:395 */       int h = 0;
/* 230:396 */       Iterator i = this.map.entrySet().iterator();
/* 231:397 */       while (i.hasNext()) {
/* 232:398 */         h += i.next().hashCode();
/* 233:    */       }
/* 234:400 */       return h;
/* 235:    */     }
/* 236:402 */     synchronized (this.map)
/* 237:    */     {
/* 238:403 */       int h = 0;
/* 239:404 */       Iterator i = this.map.entrySet().iterator();
/* 240:405 */       while (i.hasNext()) {
/* 241:406 */         h += i.next().hashCode();
/* 242:    */       }
/* 243:408 */       return h;
/* 244:    */     }
/* 245:    */   }
/* 246:    */   
/* 247:    */   public Object clone()
/* 248:    */   {
/* 249:420 */     FastHashMap results = null;
/* 250:421 */     if (this.fast) {
/* 251:422 */       results = new FastHashMap(this.map);
/* 252:    */     } else {
/* 253:424 */       synchronized (this.map)
/* 254:    */       {
/* 255:425 */         results = new FastHashMap(this.map);
/* 256:    */       }
/* 257:    */     }
/* 258:428 */     results.setFast(getFast());
/* 259:429 */     return results;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public Set entrySet()
/* 263:    */   {
/* 264:440 */     return new EntrySet(null);
/* 265:    */   }
/* 266:    */   
/* 267:    */   public Set keySet()
/* 268:    */   {
/* 269:447 */     return new KeySet(null);
/* 270:    */   }
/* 271:    */   
/* 272:    */   public Collection values()
/* 273:    */   {
/* 274:454 */     return new Values(null);
/* 275:    */   }
/* 276:    */   
/* 277:    */   private abstract class CollectionView
/* 278:    */     implements Collection
/* 279:    */   {
/* 280:    */     public CollectionView() {}
/* 281:    */     
/* 282:    */     protected abstract Collection get(Map paramMap);
/* 283:    */     
/* 284:    */     protected abstract Object iteratorNext(Map.Entry paramEntry);
/* 285:    */     
/* 286:    */     public void clear()
/* 287:    */     {
/* 288:473 */       if (FastHashMap.this.fast) {
/* 289:474 */         synchronized (FastHashMap.this)
/* 290:    */         {
/* 291:475 */           FastHashMap.this.map = new HashMap();
/* 292:    */         }
/* 293:    */       } else {
/* 294:478 */         synchronized (FastHashMap.this.map)
/* 295:    */         {
/* 296:479 */           get(FastHashMap.this.map).clear();
/* 297:    */         }
/* 298:    */       }
/* 299:    */     }
/* 300:    */     
/* 301:    */     public boolean remove(Object o)
/* 302:    */     {
/* 303:485 */       if (FastHashMap.this.fast) {
/* 304:486 */         synchronized (FastHashMap.this)
/* 305:    */         {
/* 306:487 */           HashMap temp = (HashMap)FastHashMap.this.map.clone();
/* 307:488 */           boolean r = get(temp).remove(o);
/* 308:489 */           FastHashMap.this.map = temp;
/* 309:490 */           return r;
/* 310:    */         }
/* 311:    */       }
/* 312:493 */       synchronized (FastHashMap.this.map)
/* 313:    */       {
/* 314:494 */         return get(FastHashMap.this.map).remove(o);
/* 315:    */       }
/* 316:    */     }
/* 317:    */     
/* 318:    */     public boolean removeAll(Collection o)
/* 319:    */     {
/* 320:500 */       if (FastHashMap.this.fast) {
/* 321:501 */         synchronized (FastHashMap.this)
/* 322:    */         {
/* 323:502 */           HashMap temp = (HashMap)FastHashMap.this.map.clone();
/* 324:503 */           boolean r = get(temp).removeAll(o);
/* 325:504 */           FastHashMap.this.map = temp;
/* 326:505 */           return r;
/* 327:    */         }
/* 328:    */       }
/* 329:508 */       synchronized (FastHashMap.this.map)
/* 330:    */       {
/* 331:509 */         return get(FastHashMap.this.map).removeAll(o);
/* 332:    */       }
/* 333:    */     }
/* 334:    */     
/* 335:    */     public boolean retainAll(Collection o)
/* 336:    */     {
/* 337:515 */       if (FastHashMap.this.fast) {
/* 338:516 */         synchronized (FastHashMap.this)
/* 339:    */         {
/* 340:517 */           HashMap temp = (HashMap)FastHashMap.this.map.clone();
/* 341:518 */           boolean r = get(temp).retainAll(o);
/* 342:519 */           FastHashMap.this.map = temp;
/* 343:520 */           return r;
/* 344:    */         }
/* 345:    */       }
/* 346:523 */       synchronized (FastHashMap.this.map)
/* 347:    */       {
/* 348:524 */         return get(FastHashMap.this.map).retainAll(o);
/* 349:    */       }
/* 350:    */     }
/* 351:    */     
/* 352:    */     public int size()
/* 353:    */     {
/* 354:530 */       if (FastHashMap.this.fast) {
/* 355:531 */         return get(FastHashMap.this.map).size();
/* 356:    */       }
/* 357:533 */       synchronized (FastHashMap.this.map)
/* 358:    */       {
/* 359:534 */         return get(FastHashMap.this.map).size();
/* 360:    */       }
/* 361:    */     }
/* 362:    */     
/* 363:    */     public boolean isEmpty()
/* 364:    */     {
/* 365:541 */       if (FastHashMap.this.fast) {
/* 366:542 */         return get(FastHashMap.this.map).isEmpty();
/* 367:    */       }
/* 368:544 */       synchronized (FastHashMap.this.map)
/* 369:    */       {
/* 370:545 */         return get(FastHashMap.this.map).isEmpty();
/* 371:    */       }
/* 372:    */     }
/* 373:    */     
/* 374:    */     public boolean contains(Object o)
/* 375:    */     {
/* 376:551 */       if (FastHashMap.this.fast) {
/* 377:552 */         return get(FastHashMap.this.map).contains(o);
/* 378:    */       }
/* 379:554 */       synchronized (FastHashMap.this.map)
/* 380:    */       {
/* 381:555 */         return get(FastHashMap.this.map).contains(o);
/* 382:    */       }
/* 383:    */     }
/* 384:    */     
/* 385:    */     public boolean containsAll(Collection o)
/* 386:    */     {
/* 387:561 */       if (FastHashMap.this.fast) {
/* 388:562 */         return get(FastHashMap.this.map).containsAll(o);
/* 389:    */       }
/* 390:564 */       synchronized (FastHashMap.this.map)
/* 391:    */       {
/* 392:565 */         return get(FastHashMap.this.map).containsAll(o);
/* 393:    */       }
/* 394:    */     }
/* 395:    */     
/* 396:    */     public Object[] toArray(Object[] o)
/* 397:    */     {
/* 398:571 */       if (FastHashMap.this.fast) {
/* 399:572 */         return get(FastHashMap.this.map).toArray(o);
/* 400:    */       }
/* 401:574 */       synchronized (FastHashMap.this.map)
/* 402:    */       {
/* 403:575 */         return get(FastHashMap.this.map).toArray(o);
/* 404:    */       }
/* 405:    */     }
/* 406:    */     
/* 407:    */     public Object[] toArray()
/* 408:    */     {
/* 409:581 */       if (FastHashMap.this.fast) {
/* 410:582 */         return get(FastHashMap.this.map).toArray();
/* 411:    */       }
/* 412:584 */       synchronized (FastHashMap.this.map)
/* 413:    */       {
/* 414:585 */         return get(FastHashMap.this.map).toArray();
/* 415:    */       }
/* 416:    */     }
/* 417:    */     
/* 418:    */     public boolean equals(Object o)
/* 419:    */     {
/* 420:592 */       if (o == this) {
/* 421:592 */         return true;
/* 422:    */       }
/* 423:593 */       if (FastHashMap.this.fast) {
/* 424:594 */         return get(FastHashMap.this.map).equals(o);
/* 425:    */       }
/* 426:596 */       synchronized (FastHashMap.this.map)
/* 427:    */       {
/* 428:597 */         return get(FastHashMap.this.map).equals(o);
/* 429:    */       }
/* 430:    */     }
/* 431:    */     
/* 432:    */     public int hashCode()
/* 433:    */     {
/* 434:603 */       if (FastHashMap.this.fast) {
/* 435:604 */         return get(FastHashMap.this.map).hashCode();
/* 436:    */       }
/* 437:606 */       synchronized (FastHashMap.this.map)
/* 438:    */       {
/* 439:607 */         return get(FastHashMap.this.map).hashCode();
/* 440:    */       }
/* 441:    */     }
/* 442:    */     
/* 443:    */     public boolean add(Object o)
/* 444:    */     {
/* 445:613 */       throw new UnsupportedOperationException();
/* 446:    */     }
/* 447:    */     
/* 448:    */     public boolean addAll(Collection c)
/* 449:    */     {
/* 450:617 */       throw new UnsupportedOperationException();
/* 451:    */     }
/* 452:    */     
/* 453:    */     public Iterator iterator()
/* 454:    */     {
/* 455:621 */       return new CollectionViewIterator();
/* 456:    */     }
/* 457:    */     
/* 458:    */     private class CollectionViewIterator
/* 459:    */       implements Iterator
/* 460:    */     {
/* 461:    */       private Map expected;
/* 462:627 */       private Map.Entry lastReturned = null;
/* 463:    */       private Iterator iterator;
/* 464:    */       
/* 465:    */       public CollectionViewIterator()
/* 466:    */       {
/* 467:631 */         this.expected = FastHashMap.this.map;
/* 468:632 */         this.iterator = this.expected.entrySet().iterator();
/* 469:    */       }
/* 470:    */       
/* 471:    */       public boolean hasNext()
/* 472:    */       {
/* 473:636 */         if (this.expected != FastHashMap.this.map) {
/* 474:637 */           throw new ConcurrentModificationException();
/* 475:    */         }
/* 476:639 */         return this.iterator.hasNext();
/* 477:    */       }
/* 478:    */       
/* 479:    */       public Object next()
/* 480:    */       {
/* 481:643 */         if (this.expected != FastHashMap.this.map) {
/* 482:644 */           throw new ConcurrentModificationException();
/* 483:    */         }
/* 484:646 */         this.lastReturned = ((Map.Entry)this.iterator.next());
/* 485:647 */         return FastHashMap.CollectionView.this.iteratorNext(this.lastReturned);
/* 486:    */       }
/* 487:    */       
/* 488:    */       public void remove()
/* 489:    */       {
/* 490:651 */         if (this.lastReturned == null) {
/* 491:652 */           throw new IllegalStateException();
/* 492:    */         }
/* 493:654 */         if (FastHashMap.this.fast)
/* 494:    */         {
/* 495:655 */           synchronized (FastHashMap.this)
/* 496:    */           {
/* 497:656 */             if (this.expected != FastHashMap.this.map) {
/* 498:657 */               throw new ConcurrentModificationException();
/* 499:    */             }
/* 500:659 */             FastHashMap.this.remove(this.lastReturned.getKey());
/* 501:660 */             this.lastReturned = null;
/* 502:661 */             this.expected = FastHashMap.this.map;
/* 503:    */           }
/* 504:    */         }
/* 505:    */         else
/* 506:    */         {
/* 507:664 */           this.iterator.remove();
/* 508:665 */           this.lastReturned = null;
/* 509:    */         }
/* 510:    */       }
/* 511:    */     }
/* 512:    */   }
/* 513:    */   
/* 514:    */   private class KeySet
/* 515:    */     extends FastHashMap.CollectionView
/* 516:    */     implements Set
/* 517:    */   {
/* 518:    */     KeySet(FastHashMap.1 x1)
/* 519:    */     {
/* 520:674 */       this();
/* 521:    */     }
/* 522:    */     
/* 523:    */     private KeySet()
/* 524:    */     {
/* 525:674 */       super();
/* 526:    */     }
/* 527:    */     
/* 528:    */     protected Collection get(Map map)
/* 529:    */     {
/* 530:677 */       return map.keySet();
/* 531:    */     }
/* 532:    */     
/* 533:    */     protected Object iteratorNext(Map.Entry entry)
/* 534:    */     {
/* 535:681 */       return entry.getKey();
/* 536:    */     }
/* 537:    */   }
/* 538:    */   
/* 539:    */   private class Values
/* 540:    */     extends FastHashMap.CollectionView
/* 541:    */   {
/* 542:    */     Values(FastHashMap.1 x1)
/* 543:    */     {
/* 544:689 */       this();
/* 545:    */     }
/* 546:    */     
/* 547:    */     private Values()
/* 548:    */     {
/* 549:689 */       super();
/* 550:    */     }
/* 551:    */     
/* 552:    */     protected Collection get(Map map)
/* 553:    */     {
/* 554:692 */       return map.values();
/* 555:    */     }
/* 556:    */     
/* 557:    */     protected Object iteratorNext(Map.Entry entry)
/* 558:    */     {
/* 559:696 */       return entry.getValue();
/* 560:    */     }
/* 561:    */   }
/* 562:    */   
/* 563:    */   private class EntrySet
/* 564:    */     extends FastHashMap.CollectionView
/* 565:    */     implements Set
/* 566:    */   {
/* 567:    */     EntrySet(FastHashMap.1 x1)
/* 568:    */     {
/* 569:703 */       this();
/* 570:    */     }
/* 571:    */     
/* 572:    */     private EntrySet()
/* 573:    */     {
/* 574:703 */       super();
/* 575:    */     }
/* 576:    */     
/* 577:    */     protected Collection get(Map map)
/* 578:    */     {
/* 579:706 */       return map.entrySet();
/* 580:    */     }
/* 581:    */     
/* 582:    */     protected Object iteratorNext(Map.Entry entry)
/* 583:    */     {
/* 584:710 */       return entry;
/* 585:    */     }
/* 586:    */   }
/* 587:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.FastHashMap
 * JD-Core Version:    0.7.0.1
 */