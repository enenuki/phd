/*   1:    */ package org.apache.commons.collections;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.Comparator;
/*   5:    */ import java.util.ConcurrentModificationException;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Map.Entry;
/*   9:    */ import java.util.Set;
/*  10:    */ import java.util.SortedMap;
/*  11:    */ import java.util.TreeMap;
/*  12:    */ 
/*  13:    */ public class FastTreeMap
/*  14:    */   extends TreeMap
/*  15:    */ {
/*  16: 73 */   protected TreeMap map = null;
/*  17: 78 */   protected boolean fast = false;
/*  18:    */   
/*  19:    */   public FastTreeMap()
/*  20:    */   {
/*  21: 89 */     this.map = new TreeMap();
/*  22:    */   }
/*  23:    */   
/*  24:    */   public FastTreeMap(Comparator comparator)
/*  25:    */   {
/*  26: 99 */     this.map = new TreeMap(comparator);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public FastTreeMap(Map map)
/*  30:    */   {
/*  31:110 */     this.map = new TreeMap(map);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public FastTreeMap(SortedMap map)
/*  35:    */   {
/*  36:121 */     this.map = new TreeMap(map);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public boolean getFast()
/*  40:    */   {
/*  41:134 */     return this.fast;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void setFast(boolean fast)
/*  45:    */   {
/*  46:143 */     this.fast = fast;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public Object get(Object key)
/*  50:    */   {
/*  51:162 */     if (this.fast) {
/*  52:163 */       return this.map.get(key);
/*  53:    */     }
/*  54:165 */     synchronized (this.map)
/*  55:    */     {
/*  56:166 */       return this.map.get(key);
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   public int size()
/*  61:    */   {
/*  62:177 */     if (this.fast) {
/*  63:178 */       return this.map.size();
/*  64:    */     }
/*  65:180 */     synchronized (this.map)
/*  66:    */     {
/*  67:181 */       return this.map.size();
/*  68:    */     }
/*  69:    */   }
/*  70:    */   
/*  71:    */   public boolean isEmpty()
/*  72:    */   {
/*  73:192 */     if (this.fast) {
/*  74:193 */       return this.map.isEmpty();
/*  75:    */     }
/*  76:195 */     synchronized (this.map)
/*  77:    */     {
/*  78:196 */       return this.map.isEmpty();
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   public boolean containsKey(Object key)
/*  83:    */   {
/*  84:209 */     if (this.fast) {
/*  85:210 */       return this.map.containsKey(key);
/*  86:    */     }
/*  87:212 */     synchronized (this.map)
/*  88:    */     {
/*  89:213 */       return this.map.containsKey(key);
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   public boolean containsValue(Object value)
/*  94:    */   {
/*  95:226 */     if (this.fast) {
/*  96:227 */       return this.map.containsValue(value);
/*  97:    */     }
/*  98:229 */     synchronized (this.map)
/*  99:    */     {
/* 100:230 */       return this.map.containsValue(value);
/* 101:    */     }
/* 102:    */   }
/* 103:    */   
/* 104:    */   public Comparator comparator()
/* 105:    */   {
/* 106:242 */     if (this.fast) {
/* 107:243 */       return this.map.comparator();
/* 108:    */     }
/* 109:245 */     synchronized (this.map)
/* 110:    */     {
/* 111:246 */       return this.map.comparator();
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   public Object firstKey()
/* 116:    */   {
/* 117:257 */     if (this.fast) {
/* 118:258 */       return this.map.firstKey();
/* 119:    */     }
/* 120:260 */     synchronized (this.map)
/* 121:    */     {
/* 122:261 */       return this.map.firstKey();
/* 123:    */     }
/* 124:    */   }
/* 125:    */   
/* 126:    */   public Object lastKey()
/* 127:    */   {
/* 128:272 */     if (this.fast) {
/* 129:273 */       return this.map.lastKey();
/* 130:    */     }
/* 131:275 */     synchronized (this.map)
/* 132:    */     {
/* 133:276 */       return this.map.lastKey();
/* 134:    */     }
/* 135:    */   }
/* 136:    */   
/* 137:    */   public Object put(Object key, Object value)
/* 138:    */   {
/* 139:298 */     if (this.fast) {
/* 140:299 */       synchronized (this)
/* 141:    */       {
/* 142:300 */         TreeMap temp = (TreeMap)this.map.clone();
/* 143:301 */         Object result = temp.put(key, value);
/* 144:302 */         this.map = temp;
/* 145:303 */         return result;
/* 146:    */       }
/* 147:    */     }
/* 148:306 */     synchronized (this.map)
/* 149:    */     {
/* 150:307 */       return this.map.put(key, value);
/* 151:    */     }
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void putAll(Map in)
/* 155:    */   {
/* 156:319 */     if (this.fast) {
/* 157:320 */       synchronized (this)
/* 158:    */       {
/* 159:321 */         TreeMap temp = (TreeMap)this.map.clone();
/* 160:322 */         temp.putAll(in);
/* 161:323 */         this.map = temp;
/* 162:    */       }
/* 163:    */     } else {
/* 164:326 */       synchronized (this.map)
/* 165:    */       {
/* 166:327 */         this.map.putAll(in);
/* 167:    */       }
/* 168:    */     }
/* 169:    */   }
/* 170:    */   
/* 171:    */   public Object remove(Object key)
/* 172:    */   {
/* 173:340 */     if (this.fast) {
/* 174:341 */       synchronized (this)
/* 175:    */       {
/* 176:342 */         TreeMap temp = (TreeMap)this.map.clone();
/* 177:343 */         Object result = temp.remove(key);
/* 178:344 */         this.map = temp;
/* 179:345 */         return result;
/* 180:    */       }
/* 181:    */     }
/* 182:348 */     synchronized (this.map)
/* 183:    */     {
/* 184:349 */       return this.map.remove(key);
/* 185:    */     }
/* 186:    */   }
/* 187:    */   
/* 188:    */   public void clear()
/* 189:    */   {
/* 190:358 */     if (this.fast) {
/* 191:359 */       synchronized (this)
/* 192:    */       {
/* 193:360 */         this.map = new TreeMap();
/* 194:    */       }
/* 195:    */     } else {
/* 196:363 */       synchronized (this.map)
/* 197:    */       {
/* 198:364 */         this.map.clear();
/* 199:    */       }
/* 200:    */     }
/* 201:    */   }
/* 202:    */   
/* 203:    */   public boolean equals(Object o)
/* 204:    */   {
/* 205:384 */     if (o == this) {
/* 206:385 */       return true;
/* 207:    */     }
/* 208:386 */     if (!(o instanceof Map)) {
/* 209:387 */       return false;
/* 210:    */     }
/* 211:389 */     Map mo = (Map)o;
/* 212:392 */     if (this.fast)
/* 213:    */     {
/* 214:393 */       if (mo.size() != this.map.size()) {
/* 215:394 */         return false;
/* 216:    */       }
/* 217:396 */       Iterator i = this.map.entrySet().iterator();
/* 218:397 */       while (i.hasNext())
/* 219:    */       {
/* 220:398 */         Map.Entry e = (Map.Entry)i.next();
/* 221:399 */         Object key = e.getKey();
/* 222:400 */         Object value = e.getValue();
/* 223:401 */         if (value == null)
/* 224:    */         {
/* 225:402 */           if ((mo.get(key) != null) || (!mo.containsKey(key))) {
/* 226:403 */             return false;
/* 227:    */           }
/* 228:    */         }
/* 229:406 */         else if (!value.equals(mo.get(key))) {
/* 230:407 */           return false;
/* 231:    */         }
/* 232:    */       }
/* 233:411 */       return true;
/* 234:    */     }
/* 235:413 */     synchronized (this.map)
/* 236:    */     {
/* 237:414 */       if (mo.size() != this.map.size()) {
/* 238:415 */         return false;
/* 239:    */       }
/* 240:417 */       Iterator i = this.map.entrySet().iterator();
/* 241:418 */       while (i.hasNext())
/* 242:    */       {
/* 243:419 */         Map.Entry e = (Map.Entry)i.next();
/* 244:420 */         Object key = e.getKey();
/* 245:421 */         Object value = e.getValue();
/* 246:422 */         if (value == null)
/* 247:    */         {
/* 248:423 */           if ((mo.get(key) != null) || (!mo.containsKey(key))) {
/* 249:424 */             return false;
/* 250:    */           }
/* 251:    */         }
/* 252:427 */         else if (!value.equals(mo.get(key))) {
/* 253:428 */           return false;
/* 254:    */         }
/* 255:    */       }
/* 256:432 */       return true;
/* 257:    */     }
/* 258:    */   }
/* 259:    */   
/* 260:    */   public int hashCode()
/* 261:    */   {
/* 262:445 */     if (this.fast)
/* 263:    */     {
/* 264:446 */       int h = 0;
/* 265:447 */       Iterator i = this.map.entrySet().iterator();
/* 266:448 */       while (i.hasNext()) {
/* 267:449 */         h += i.next().hashCode();
/* 268:    */       }
/* 269:451 */       return h;
/* 270:    */     }
/* 271:453 */     synchronized (this.map)
/* 272:    */     {
/* 273:454 */       int h = 0;
/* 274:455 */       Iterator i = this.map.entrySet().iterator();
/* 275:456 */       while (i.hasNext()) {
/* 276:457 */         h += i.next().hashCode();
/* 277:    */       }
/* 278:459 */       return h;
/* 279:    */     }
/* 280:    */   }
/* 281:    */   
/* 282:    */   public Object clone()
/* 283:    */   {
/* 284:471 */     FastTreeMap results = null;
/* 285:472 */     if (this.fast) {
/* 286:473 */       results = new FastTreeMap(this.map);
/* 287:    */     } else {
/* 288:475 */       synchronized (this.map)
/* 289:    */       {
/* 290:476 */         results = new FastTreeMap(this.map);
/* 291:    */       }
/* 292:    */     }
/* 293:479 */     results.setFast(getFast());
/* 294:480 */     return results;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public SortedMap headMap(Object key)
/* 298:    */   {
/* 299:495 */     if (this.fast) {
/* 300:496 */       return this.map.headMap(key);
/* 301:    */     }
/* 302:498 */     synchronized (this.map)
/* 303:    */     {
/* 304:499 */       return this.map.headMap(key);
/* 305:    */     }
/* 306:    */   }
/* 307:    */   
/* 308:    */   public SortedMap subMap(Object fromKey, Object toKey)
/* 309:    */   {
/* 310:513 */     if (this.fast) {
/* 311:514 */       return this.map.subMap(fromKey, toKey);
/* 312:    */     }
/* 313:516 */     synchronized (this.map)
/* 314:    */     {
/* 315:517 */       return this.map.subMap(fromKey, toKey);
/* 316:    */     }
/* 317:    */   }
/* 318:    */   
/* 319:    */   public SortedMap tailMap(Object key)
/* 320:    */   {
/* 321:530 */     if (this.fast) {
/* 322:531 */       return this.map.tailMap(key);
/* 323:    */     }
/* 324:533 */     synchronized (this.map)
/* 325:    */     {
/* 326:534 */       return this.map.tailMap(key);
/* 327:    */     }
/* 328:    */   }
/* 329:    */   
/* 330:    */   public Set entrySet()
/* 331:    */   {
/* 332:548 */     return new EntrySet(null);
/* 333:    */   }
/* 334:    */   
/* 335:    */   public Set keySet()
/* 336:    */   {
/* 337:555 */     return new KeySet(null);
/* 338:    */   }
/* 339:    */   
/* 340:    */   public Collection values()
/* 341:    */   {
/* 342:562 */     return new Values(null);
/* 343:    */   }
/* 344:    */   
/* 345:    */   private abstract class CollectionView
/* 346:    */     implements Collection
/* 347:    */   {
/* 348:    */     public CollectionView() {}
/* 349:    */     
/* 350:    */     protected abstract Collection get(Map paramMap);
/* 351:    */     
/* 352:    */     protected abstract Object iteratorNext(Map.Entry paramEntry);
/* 353:    */     
/* 354:    */     public void clear()
/* 355:    */     {
/* 356:581 */       if (FastTreeMap.this.fast) {
/* 357:582 */         synchronized (FastTreeMap.this)
/* 358:    */         {
/* 359:583 */           FastTreeMap.this.map = new TreeMap();
/* 360:    */         }
/* 361:    */       } else {
/* 362:586 */         synchronized (FastTreeMap.this.map)
/* 363:    */         {
/* 364:587 */           get(FastTreeMap.this.map).clear();
/* 365:    */         }
/* 366:    */       }
/* 367:    */     }
/* 368:    */     
/* 369:    */     public boolean remove(Object o)
/* 370:    */     {
/* 371:593 */       if (FastTreeMap.this.fast) {
/* 372:594 */         synchronized (FastTreeMap.this)
/* 373:    */         {
/* 374:595 */           TreeMap temp = (TreeMap)FastTreeMap.this.map.clone();
/* 375:596 */           boolean r = get(temp).remove(o);
/* 376:597 */           FastTreeMap.this.map = temp;
/* 377:598 */           return r;
/* 378:    */         }
/* 379:    */       }
/* 380:601 */       synchronized (FastTreeMap.this.map)
/* 381:    */       {
/* 382:602 */         return get(FastTreeMap.this.map).remove(o);
/* 383:    */       }
/* 384:    */     }
/* 385:    */     
/* 386:    */     public boolean removeAll(Collection o)
/* 387:    */     {
/* 388:608 */       if (FastTreeMap.this.fast) {
/* 389:609 */         synchronized (FastTreeMap.this)
/* 390:    */         {
/* 391:610 */           TreeMap temp = (TreeMap)FastTreeMap.this.map.clone();
/* 392:611 */           boolean r = get(temp).removeAll(o);
/* 393:612 */           FastTreeMap.this.map = temp;
/* 394:613 */           return r;
/* 395:    */         }
/* 396:    */       }
/* 397:616 */       synchronized (FastTreeMap.this.map)
/* 398:    */       {
/* 399:617 */         return get(FastTreeMap.this.map).removeAll(o);
/* 400:    */       }
/* 401:    */     }
/* 402:    */     
/* 403:    */     public boolean retainAll(Collection o)
/* 404:    */     {
/* 405:623 */       if (FastTreeMap.this.fast) {
/* 406:624 */         synchronized (FastTreeMap.this)
/* 407:    */         {
/* 408:625 */           TreeMap temp = (TreeMap)FastTreeMap.this.map.clone();
/* 409:626 */           boolean r = get(temp).retainAll(o);
/* 410:627 */           FastTreeMap.this.map = temp;
/* 411:628 */           return r;
/* 412:    */         }
/* 413:    */       }
/* 414:631 */       synchronized (FastTreeMap.this.map)
/* 415:    */       {
/* 416:632 */         return get(FastTreeMap.this.map).retainAll(o);
/* 417:    */       }
/* 418:    */     }
/* 419:    */     
/* 420:    */     public int size()
/* 421:    */     {
/* 422:638 */       if (FastTreeMap.this.fast) {
/* 423:639 */         return get(FastTreeMap.this.map).size();
/* 424:    */       }
/* 425:641 */       synchronized (FastTreeMap.this.map)
/* 426:    */       {
/* 427:642 */         return get(FastTreeMap.this.map).size();
/* 428:    */       }
/* 429:    */     }
/* 430:    */     
/* 431:    */     public boolean isEmpty()
/* 432:    */     {
/* 433:649 */       if (FastTreeMap.this.fast) {
/* 434:650 */         return get(FastTreeMap.this.map).isEmpty();
/* 435:    */       }
/* 436:652 */       synchronized (FastTreeMap.this.map)
/* 437:    */       {
/* 438:653 */         return get(FastTreeMap.this.map).isEmpty();
/* 439:    */       }
/* 440:    */     }
/* 441:    */     
/* 442:    */     public boolean contains(Object o)
/* 443:    */     {
/* 444:659 */       if (FastTreeMap.this.fast) {
/* 445:660 */         return get(FastTreeMap.this.map).contains(o);
/* 446:    */       }
/* 447:662 */       synchronized (FastTreeMap.this.map)
/* 448:    */       {
/* 449:663 */         return get(FastTreeMap.this.map).contains(o);
/* 450:    */       }
/* 451:    */     }
/* 452:    */     
/* 453:    */     public boolean containsAll(Collection o)
/* 454:    */     {
/* 455:669 */       if (FastTreeMap.this.fast) {
/* 456:670 */         return get(FastTreeMap.this.map).containsAll(o);
/* 457:    */       }
/* 458:672 */       synchronized (FastTreeMap.this.map)
/* 459:    */       {
/* 460:673 */         return get(FastTreeMap.this.map).containsAll(o);
/* 461:    */       }
/* 462:    */     }
/* 463:    */     
/* 464:    */     public Object[] toArray(Object[] o)
/* 465:    */     {
/* 466:679 */       if (FastTreeMap.this.fast) {
/* 467:680 */         return get(FastTreeMap.this.map).toArray(o);
/* 468:    */       }
/* 469:682 */       synchronized (FastTreeMap.this.map)
/* 470:    */       {
/* 471:683 */         return get(FastTreeMap.this.map).toArray(o);
/* 472:    */       }
/* 473:    */     }
/* 474:    */     
/* 475:    */     public Object[] toArray()
/* 476:    */     {
/* 477:689 */       if (FastTreeMap.this.fast) {
/* 478:690 */         return get(FastTreeMap.this.map).toArray();
/* 479:    */       }
/* 480:692 */       synchronized (FastTreeMap.this.map)
/* 481:    */       {
/* 482:693 */         return get(FastTreeMap.this.map).toArray();
/* 483:    */       }
/* 484:    */     }
/* 485:    */     
/* 486:    */     public boolean equals(Object o)
/* 487:    */     {
/* 488:700 */       if (o == this) {
/* 489:700 */         return true;
/* 490:    */       }
/* 491:701 */       if (FastTreeMap.this.fast) {
/* 492:702 */         return get(FastTreeMap.this.map).equals(o);
/* 493:    */       }
/* 494:704 */       synchronized (FastTreeMap.this.map)
/* 495:    */       {
/* 496:705 */         return get(FastTreeMap.this.map).equals(o);
/* 497:    */       }
/* 498:    */     }
/* 499:    */     
/* 500:    */     public int hashCode()
/* 501:    */     {
/* 502:711 */       if (FastTreeMap.this.fast) {
/* 503:712 */         return get(FastTreeMap.this.map).hashCode();
/* 504:    */       }
/* 505:714 */       synchronized (FastTreeMap.this.map)
/* 506:    */       {
/* 507:715 */         return get(FastTreeMap.this.map).hashCode();
/* 508:    */       }
/* 509:    */     }
/* 510:    */     
/* 511:    */     public boolean add(Object o)
/* 512:    */     {
/* 513:721 */       throw new UnsupportedOperationException();
/* 514:    */     }
/* 515:    */     
/* 516:    */     public boolean addAll(Collection c)
/* 517:    */     {
/* 518:725 */       throw new UnsupportedOperationException();
/* 519:    */     }
/* 520:    */     
/* 521:    */     public Iterator iterator()
/* 522:    */     {
/* 523:729 */       return new CollectionViewIterator();
/* 524:    */     }
/* 525:    */     
/* 526:    */     private class CollectionViewIterator
/* 527:    */       implements Iterator
/* 528:    */     {
/* 529:    */       private Map expected;
/* 530:735 */       private Map.Entry lastReturned = null;
/* 531:    */       private Iterator iterator;
/* 532:    */       
/* 533:    */       public CollectionViewIterator()
/* 534:    */       {
/* 535:739 */         this.expected = FastTreeMap.this.map;
/* 536:740 */         this.iterator = this.expected.entrySet().iterator();
/* 537:    */       }
/* 538:    */       
/* 539:    */       public boolean hasNext()
/* 540:    */       {
/* 541:744 */         if (this.expected != FastTreeMap.this.map) {
/* 542:745 */           throw new ConcurrentModificationException();
/* 543:    */         }
/* 544:747 */         return this.iterator.hasNext();
/* 545:    */       }
/* 546:    */       
/* 547:    */       public Object next()
/* 548:    */       {
/* 549:751 */         if (this.expected != FastTreeMap.this.map) {
/* 550:752 */           throw new ConcurrentModificationException();
/* 551:    */         }
/* 552:754 */         this.lastReturned = ((Map.Entry)this.iterator.next());
/* 553:755 */         return FastTreeMap.CollectionView.this.iteratorNext(this.lastReturned);
/* 554:    */       }
/* 555:    */       
/* 556:    */       public void remove()
/* 557:    */       {
/* 558:759 */         if (this.lastReturned == null) {
/* 559:760 */           throw new IllegalStateException();
/* 560:    */         }
/* 561:762 */         if (FastTreeMap.this.fast)
/* 562:    */         {
/* 563:763 */           synchronized (FastTreeMap.this)
/* 564:    */           {
/* 565:764 */             if (this.expected != FastTreeMap.this.map) {
/* 566:765 */               throw new ConcurrentModificationException();
/* 567:    */             }
/* 568:767 */             FastTreeMap.this.remove(this.lastReturned.getKey());
/* 569:768 */             this.lastReturned = null;
/* 570:769 */             this.expected = FastTreeMap.this.map;
/* 571:    */           }
/* 572:    */         }
/* 573:    */         else
/* 574:    */         {
/* 575:772 */           this.iterator.remove();
/* 576:773 */           this.lastReturned = null;
/* 577:    */         }
/* 578:    */       }
/* 579:    */     }
/* 580:    */   }
/* 581:    */   
/* 582:    */   private class KeySet
/* 583:    */     extends FastTreeMap.CollectionView
/* 584:    */     implements Set
/* 585:    */   {
/* 586:    */     KeySet(FastTreeMap.1 x1)
/* 587:    */     {
/* 588:782 */       this();
/* 589:    */     }
/* 590:    */     
/* 591:    */     private KeySet()
/* 592:    */     {
/* 593:782 */       super();
/* 594:    */     }
/* 595:    */     
/* 596:    */     protected Collection get(Map map)
/* 597:    */     {
/* 598:785 */       return map.keySet();
/* 599:    */     }
/* 600:    */     
/* 601:    */     protected Object iteratorNext(Map.Entry entry)
/* 602:    */     {
/* 603:789 */       return entry.getKey();
/* 604:    */     }
/* 605:    */   }
/* 606:    */   
/* 607:    */   private class Values
/* 608:    */     extends FastTreeMap.CollectionView
/* 609:    */   {
/* 610:    */     Values(FastTreeMap.1 x1)
/* 611:    */     {
/* 612:797 */       this();
/* 613:    */     }
/* 614:    */     
/* 615:    */     private Values()
/* 616:    */     {
/* 617:797 */       super();
/* 618:    */     }
/* 619:    */     
/* 620:    */     protected Collection get(Map map)
/* 621:    */     {
/* 622:800 */       return map.values();
/* 623:    */     }
/* 624:    */     
/* 625:    */     protected Object iteratorNext(Map.Entry entry)
/* 626:    */     {
/* 627:804 */       return entry.getValue();
/* 628:    */     }
/* 629:    */   }
/* 630:    */   
/* 631:    */   private class EntrySet
/* 632:    */     extends FastTreeMap.CollectionView
/* 633:    */     implements Set
/* 634:    */   {
/* 635:    */     EntrySet(FastTreeMap.1 x1)
/* 636:    */     {
/* 637:811 */       this();
/* 638:    */     }
/* 639:    */     
/* 640:    */     private EntrySet()
/* 641:    */     {
/* 642:811 */       super();
/* 643:    */     }
/* 644:    */     
/* 645:    */     protected Collection get(Map map)
/* 646:    */     {
/* 647:814 */       return map.entrySet();
/* 648:    */     }
/* 649:    */     
/* 650:    */     protected Object iteratorNext(Map.Entry entry)
/* 651:    */     {
/* 652:819 */       return entry;
/* 653:    */     }
/* 654:    */   }
/* 655:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.FastTreeMap
 * JD-Core Version:    0.7.0.1
 */