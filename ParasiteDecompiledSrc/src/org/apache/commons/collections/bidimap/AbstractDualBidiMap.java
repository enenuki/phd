/*   1:    */ package org.apache.commons.collections.bidimap;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.Map;
/*   6:    */ import java.util.Map.Entry;
/*   7:    */ import java.util.Set;
/*   8:    */ import org.apache.commons.collections.BidiMap;
/*   9:    */ import org.apache.commons.collections.MapIterator;
/*  10:    */ import org.apache.commons.collections.ResettableIterator;
/*  11:    */ import org.apache.commons.collections.collection.AbstractCollectionDecorator;
/*  12:    */ import org.apache.commons.collections.iterators.AbstractIteratorDecorator;
/*  13:    */ import org.apache.commons.collections.keyvalue.AbstractMapEntryDecorator;
/*  14:    */ 
/*  15:    */ public abstract class AbstractDualBidiMap
/*  16:    */   implements BidiMap
/*  17:    */ {
/*  18: 51 */   protected final transient Map[] maps = new Map[2];
/*  19: 55 */   protected transient BidiMap inverseBidiMap = null;
/*  20: 59 */   protected transient Set keySet = null;
/*  21: 63 */   protected transient Collection values = null;
/*  22: 67 */   protected transient Set entrySet = null;
/*  23:    */   
/*  24:    */   protected AbstractDualBidiMap()
/*  25:    */   {
/*  26: 78 */     this.maps[0] = createMap();
/*  27: 79 */     this.maps[1] = createMap();
/*  28:    */   }
/*  29:    */   
/*  30:    */   protected AbstractDualBidiMap(Map normalMap, Map reverseMap)
/*  31:    */   {
/*  32: 98 */     this.maps[0] = normalMap;
/*  33: 99 */     this.maps[1] = reverseMap;
/*  34:    */   }
/*  35:    */   
/*  36:    */   protected AbstractDualBidiMap(Map normalMap, Map reverseMap, BidiMap inverseBidiMap)
/*  37:    */   {
/*  38:112 */     this.maps[0] = normalMap;
/*  39:113 */     this.maps[1] = reverseMap;
/*  40:114 */     this.inverseBidiMap = inverseBidiMap;
/*  41:    */   }
/*  42:    */   
/*  43:    */   /**
/*  44:    */    * @deprecated
/*  45:    */    */
/*  46:    */   protected Map createMap()
/*  47:    */   {
/*  48:128 */     return null;
/*  49:    */   }
/*  50:    */   
/*  51:    */   protected abstract BidiMap createBidiMap(Map paramMap1, Map paramMap2, BidiMap paramBidiMap);
/*  52:    */   
/*  53:    */   public Object get(Object key)
/*  54:    */   {
/*  55:144 */     return this.maps[0].get(key);
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int size()
/*  59:    */   {
/*  60:148 */     return this.maps[0].size();
/*  61:    */   }
/*  62:    */   
/*  63:    */   public boolean isEmpty()
/*  64:    */   {
/*  65:152 */     return this.maps[0].isEmpty();
/*  66:    */   }
/*  67:    */   
/*  68:    */   public boolean containsKey(Object key)
/*  69:    */   {
/*  70:156 */     return this.maps[0].containsKey(key);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public boolean equals(Object obj)
/*  74:    */   {
/*  75:160 */     return this.maps[0].equals(obj);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public int hashCode()
/*  79:    */   {
/*  80:164 */     return this.maps[0].hashCode();
/*  81:    */   }
/*  82:    */   
/*  83:    */   public String toString()
/*  84:    */   {
/*  85:168 */     return this.maps[0].toString();
/*  86:    */   }
/*  87:    */   
/*  88:    */   public Object put(Object key, Object value)
/*  89:    */   {
/*  90:174 */     if (this.maps[0].containsKey(key)) {
/*  91:175 */       this.maps[1].remove(this.maps[0].get(key));
/*  92:    */     }
/*  93:177 */     if (this.maps[1].containsKey(value)) {
/*  94:178 */       this.maps[0].remove(this.maps[1].get(value));
/*  95:    */     }
/*  96:180 */     Object obj = this.maps[0].put(key, value);
/*  97:181 */     this.maps[1].put(value, key);
/*  98:182 */     return obj;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void putAll(Map map)
/* 102:    */   {
/* 103:186 */     for (Iterator it = map.entrySet().iterator(); it.hasNext();)
/* 104:    */     {
/* 105:187 */       Map.Entry entry = (Map.Entry)it.next();
/* 106:188 */       put(entry.getKey(), entry.getValue());
/* 107:    */     }
/* 108:    */   }
/* 109:    */   
/* 110:    */   public Object remove(Object key)
/* 111:    */   {
/* 112:193 */     Object value = null;
/* 113:194 */     if (this.maps[0].containsKey(key))
/* 114:    */     {
/* 115:195 */       value = this.maps[0].remove(key);
/* 116:196 */       this.maps[1].remove(value);
/* 117:    */     }
/* 118:198 */     return value;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void clear()
/* 122:    */   {
/* 123:202 */     this.maps[0].clear();
/* 124:203 */     this.maps[1].clear();
/* 125:    */   }
/* 126:    */   
/* 127:    */   public boolean containsValue(Object value)
/* 128:    */   {
/* 129:207 */     return this.maps[1].containsKey(value);
/* 130:    */   }
/* 131:    */   
/* 132:    */   public MapIterator mapIterator()
/* 133:    */   {
/* 134:224 */     return new BidiMapIterator(this);
/* 135:    */   }
/* 136:    */   
/* 137:    */   public Object getKey(Object value)
/* 138:    */   {
/* 139:228 */     return this.maps[1].get(value);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public Object removeValue(Object value)
/* 143:    */   {
/* 144:232 */     Object key = null;
/* 145:233 */     if (this.maps[1].containsKey(value))
/* 146:    */     {
/* 147:234 */       key = this.maps[1].remove(value);
/* 148:235 */       this.maps[0].remove(key);
/* 149:    */     }
/* 150:237 */     return key;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public BidiMap inverseBidiMap()
/* 154:    */   {
/* 155:241 */     if (this.inverseBidiMap == null) {
/* 156:242 */       this.inverseBidiMap = createBidiMap(this.maps[1], this.maps[0], this);
/* 157:    */     }
/* 158:244 */     return this.inverseBidiMap;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public Set keySet()
/* 162:    */   {
/* 163:257 */     if (this.keySet == null) {
/* 164:258 */       this.keySet = new KeySet(this);
/* 165:    */     }
/* 166:260 */     return this.keySet;
/* 167:    */   }
/* 168:    */   
/* 169:    */   protected Iterator createKeySetIterator(Iterator iterator)
/* 170:    */   {
/* 171:271 */     return new KeySetIterator(iterator, this);
/* 172:    */   }
/* 173:    */   
/* 174:    */   public Collection values()
/* 175:    */   {
/* 176:282 */     if (this.values == null) {
/* 177:283 */       this.values = new Values(this);
/* 178:    */     }
/* 179:285 */     return this.values;
/* 180:    */   }
/* 181:    */   
/* 182:    */   protected Iterator createValuesIterator(Iterator iterator)
/* 183:    */   {
/* 184:296 */     return new ValuesIterator(iterator, this);
/* 185:    */   }
/* 186:    */   
/* 187:    */   public Set entrySet()
/* 188:    */   {
/* 189:311 */     if (this.entrySet == null) {
/* 190:312 */       this.entrySet = new EntrySet(this);
/* 191:    */     }
/* 192:314 */     return this.entrySet;
/* 193:    */   }
/* 194:    */   
/* 195:    */   protected Iterator createEntrySetIterator(Iterator iterator)
/* 196:    */   {
/* 197:325 */     return new EntrySetIterator(iterator, this);
/* 198:    */   }
/* 199:    */   
/* 200:    */   protected static abstract class View
/* 201:    */     extends AbstractCollectionDecorator
/* 202:    */   {
/* 203:    */     protected final AbstractDualBidiMap parent;
/* 204:    */     
/* 205:    */     protected View(Collection coll, AbstractDualBidiMap parent)
/* 206:    */     {
/* 207:344 */       super();
/* 208:345 */       this.parent = parent;
/* 209:    */     }
/* 210:    */     
/* 211:    */     public boolean removeAll(Collection coll)
/* 212:    */     {
/* 213:349 */       if ((this.parent.isEmpty()) || (coll.isEmpty())) {
/* 214:350 */         return false;
/* 215:    */       }
/* 216:352 */       boolean modified = false;
/* 217:353 */       Iterator it = iterator();
/* 218:354 */       while (it.hasNext()) {
/* 219:355 */         if (coll.contains(it.next()))
/* 220:    */         {
/* 221:356 */           it.remove();
/* 222:357 */           modified = true;
/* 223:    */         }
/* 224:    */       }
/* 225:360 */       return modified;
/* 226:    */     }
/* 227:    */     
/* 228:    */     public boolean retainAll(Collection coll)
/* 229:    */     {
/* 230:364 */       if (this.parent.isEmpty()) {
/* 231:365 */         return false;
/* 232:    */       }
/* 233:367 */       if (coll.isEmpty())
/* 234:    */       {
/* 235:368 */         this.parent.clear();
/* 236:369 */         return true;
/* 237:    */       }
/* 238:371 */       boolean modified = false;
/* 239:372 */       Iterator it = iterator();
/* 240:373 */       while (it.hasNext()) {
/* 241:374 */         if (!coll.contains(it.next()))
/* 242:    */         {
/* 243:375 */           it.remove();
/* 244:376 */           modified = true;
/* 245:    */         }
/* 246:    */       }
/* 247:379 */       return modified;
/* 248:    */     }
/* 249:    */     
/* 250:    */     public void clear()
/* 251:    */     {
/* 252:383 */       this.parent.clear();
/* 253:    */     }
/* 254:    */   }
/* 255:    */   
/* 256:    */   protected static class KeySet
/* 257:    */     extends AbstractDualBidiMap.View
/* 258:    */     implements Set
/* 259:    */   {
/* 260:    */     protected KeySet(AbstractDualBidiMap parent)
/* 261:    */     {
/* 262:399 */       super(parent);
/* 263:    */     }
/* 264:    */     
/* 265:    */     public Iterator iterator()
/* 266:    */     {
/* 267:403 */       return this.parent.createKeySetIterator(super.iterator());
/* 268:    */     }
/* 269:    */     
/* 270:    */     public boolean contains(Object key)
/* 271:    */     {
/* 272:407 */       return this.parent.maps[0].containsKey(key);
/* 273:    */     }
/* 274:    */     
/* 275:    */     public boolean remove(Object key)
/* 276:    */     {
/* 277:411 */       if (this.parent.maps[0].containsKey(key))
/* 278:    */       {
/* 279:412 */         Object value = this.parent.maps[0].remove(key);
/* 280:413 */         this.parent.maps[1].remove(value);
/* 281:414 */         return true;
/* 282:    */       }
/* 283:416 */       return false;
/* 284:    */     }
/* 285:    */   }
/* 286:    */   
/* 287:    */   protected static class KeySetIterator
/* 288:    */     extends AbstractIteratorDecorator
/* 289:    */   {
/* 290:    */     protected final AbstractDualBidiMap parent;
/* 291:428 */     protected Object lastKey = null;
/* 292:430 */     protected boolean canRemove = false;
/* 293:    */     
/* 294:    */     protected KeySetIterator(Iterator iterator, AbstractDualBidiMap parent)
/* 295:    */     {
/* 296:438 */       super();
/* 297:439 */       this.parent = parent;
/* 298:    */     }
/* 299:    */     
/* 300:    */     public Object next()
/* 301:    */     {
/* 302:443 */       this.lastKey = super.next();
/* 303:444 */       this.canRemove = true;
/* 304:445 */       return this.lastKey;
/* 305:    */     }
/* 306:    */     
/* 307:    */     public void remove()
/* 308:    */     {
/* 309:449 */       if (!this.canRemove) {
/* 310:450 */         throw new IllegalStateException("Iterator remove() can only be called once after next()");
/* 311:    */       }
/* 312:452 */       Object value = this.parent.maps[0].get(this.lastKey);
/* 313:453 */       super.remove();
/* 314:454 */       this.parent.maps[1].remove(value);
/* 315:455 */       this.lastKey = null;
/* 316:456 */       this.canRemove = false;
/* 317:    */     }
/* 318:    */   }
/* 319:    */   
/* 320:    */   protected static class Values
/* 321:    */     extends AbstractDualBidiMap.View
/* 322:    */     implements Set
/* 323:    */   {
/* 324:    */     protected Values(AbstractDualBidiMap parent)
/* 325:    */     {
/* 326:472 */       super(parent);
/* 327:    */     }
/* 328:    */     
/* 329:    */     public Iterator iterator()
/* 330:    */     {
/* 331:476 */       return this.parent.createValuesIterator(super.iterator());
/* 332:    */     }
/* 333:    */     
/* 334:    */     public boolean contains(Object value)
/* 335:    */     {
/* 336:480 */       return this.parent.maps[1].containsKey(value);
/* 337:    */     }
/* 338:    */     
/* 339:    */     public boolean remove(Object value)
/* 340:    */     {
/* 341:484 */       if (this.parent.maps[1].containsKey(value))
/* 342:    */       {
/* 343:485 */         Object key = this.parent.maps[1].remove(value);
/* 344:486 */         this.parent.maps[0].remove(key);
/* 345:487 */         return true;
/* 346:    */       }
/* 347:489 */       return false;
/* 348:    */     }
/* 349:    */   }
/* 350:    */   
/* 351:    */   protected static class ValuesIterator
/* 352:    */     extends AbstractIteratorDecorator
/* 353:    */   {
/* 354:    */     protected final AbstractDualBidiMap parent;
/* 355:501 */     protected Object lastValue = null;
/* 356:503 */     protected boolean canRemove = false;
/* 357:    */     
/* 358:    */     protected ValuesIterator(Iterator iterator, AbstractDualBidiMap parent)
/* 359:    */     {
/* 360:511 */       super();
/* 361:512 */       this.parent = parent;
/* 362:    */     }
/* 363:    */     
/* 364:    */     public Object next()
/* 365:    */     {
/* 366:516 */       this.lastValue = super.next();
/* 367:517 */       this.canRemove = true;
/* 368:518 */       return this.lastValue;
/* 369:    */     }
/* 370:    */     
/* 371:    */     public void remove()
/* 372:    */     {
/* 373:522 */       if (!this.canRemove) {
/* 374:523 */         throw new IllegalStateException("Iterator remove() can only be called once after next()");
/* 375:    */       }
/* 376:525 */       super.remove();
/* 377:526 */       this.parent.maps[1].remove(this.lastValue);
/* 378:527 */       this.lastValue = null;
/* 379:528 */       this.canRemove = false;
/* 380:    */     }
/* 381:    */   }
/* 382:    */   
/* 383:    */   protected static class EntrySet
/* 384:    */     extends AbstractDualBidiMap.View
/* 385:    */     implements Set
/* 386:    */   {
/* 387:    */     protected EntrySet(AbstractDualBidiMap parent)
/* 388:    */     {
/* 389:544 */       super(parent);
/* 390:    */     }
/* 391:    */     
/* 392:    */     public Iterator iterator()
/* 393:    */     {
/* 394:548 */       return this.parent.createEntrySetIterator(super.iterator());
/* 395:    */     }
/* 396:    */     
/* 397:    */     public boolean remove(Object obj)
/* 398:    */     {
/* 399:552 */       if (!(obj instanceof Map.Entry)) {
/* 400:553 */         return false;
/* 401:    */       }
/* 402:555 */       Map.Entry entry = (Map.Entry)obj;
/* 403:556 */       Object key = entry.getKey();
/* 404:557 */       if (this.parent.containsKey(key))
/* 405:    */       {
/* 406:558 */         Object value = this.parent.maps[0].get(key);
/* 407:559 */         if (value == null ? entry.getValue() == null : value.equals(entry.getValue()))
/* 408:    */         {
/* 409:560 */           this.parent.maps[0].remove(key);
/* 410:561 */           this.parent.maps[1].remove(value);
/* 411:562 */           return true;
/* 412:    */         }
/* 413:    */       }
/* 414:565 */       return false;
/* 415:    */     }
/* 416:    */   }
/* 417:    */   
/* 418:    */   protected static class EntrySetIterator
/* 419:    */     extends AbstractIteratorDecorator
/* 420:    */   {
/* 421:    */     protected final AbstractDualBidiMap parent;
/* 422:577 */     protected Map.Entry last = null;
/* 423:579 */     protected boolean canRemove = false;
/* 424:    */     
/* 425:    */     protected EntrySetIterator(Iterator iterator, AbstractDualBidiMap parent)
/* 426:    */     {
/* 427:587 */       super();
/* 428:588 */       this.parent = parent;
/* 429:    */     }
/* 430:    */     
/* 431:    */     public Object next()
/* 432:    */     {
/* 433:592 */       this.last = new AbstractDualBidiMap.MapEntry((Map.Entry)super.next(), this.parent);
/* 434:593 */       this.canRemove = true;
/* 435:594 */       return this.last;
/* 436:    */     }
/* 437:    */     
/* 438:    */     public void remove()
/* 439:    */     {
/* 440:598 */       if (!this.canRemove) {
/* 441:599 */         throw new IllegalStateException("Iterator remove() can only be called once after next()");
/* 442:    */       }
/* 443:602 */       Object value = this.last.getValue();
/* 444:603 */       super.remove();
/* 445:604 */       this.parent.maps[1].remove(value);
/* 446:605 */       this.last = null;
/* 447:606 */       this.canRemove = false;
/* 448:    */     }
/* 449:    */   }
/* 450:    */   
/* 451:    */   protected static class MapEntry
/* 452:    */     extends AbstractMapEntryDecorator
/* 453:    */   {
/* 454:    */     protected final AbstractDualBidiMap parent;
/* 455:    */     
/* 456:    */     protected MapEntry(Map.Entry entry, AbstractDualBidiMap parent)
/* 457:    */     {
/* 458:624 */       super();
/* 459:625 */       this.parent = parent;
/* 460:    */     }
/* 461:    */     
/* 462:    */     public Object setValue(Object value)
/* 463:    */     {
/* 464:629 */       Object key = getKey();
/* 465:630 */       if ((this.parent.maps[1].containsKey(value)) && (this.parent.maps[1].get(value) != key)) {
/* 466:632 */         throw new IllegalArgumentException("Cannot use setValue() when the object being set is already in the map");
/* 467:    */       }
/* 468:634 */       this.parent.put(key, value);
/* 469:635 */       Object oldValue = super.setValue(value);
/* 470:636 */       return oldValue;
/* 471:    */     }
/* 472:    */   }
/* 473:    */   
/* 474:    */   protected static class BidiMapIterator
/* 475:    */     implements MapIterator, ResettableIterator
/* 476:    */   {
/* 477:    */     protected final AbstractDualBidiMap parent;
/* 478:    */     protected Iterator iterator;
/* 479:650 */     protected Map.Entry last = null;
/* 480:652 */     protected boolean canRemove = false;
/* 481:    */     
/* 482:    */     protected BidiMapIterator(AbstractDualBidiMap parent)
/* 483:    */     {
/* 484:660 */       this.parent = parent;
/* 485:661 */       this.iterator = parent.maps[0].entrySet().iterator();
/* 486:    */     }
/* 487:    */     
/* 488:    */     public boolean hasNext()
/* 489:    */     {
/* 490:665 */       return this.iterator.hasNext();
/* 491:    */     }
/* 492:    */     
/* 493:    */     public Object next()
/* 494:    */     {
/* 495:669 */       this.last = ((Map.Entry)this.iterator.next());
/* 496:670 */       this.canRemove = true;
/* 497:671 */       return this.last.getKey();
/* 498:    */     }
/* 499:    */     
/* 500:    */     public void remove()
/* 501:    */     {
/* 502:675 */       if (!this.canRemove) {
/* 503:676 */         throw new IllegalStateException("Iterator remove() can only be called once after next()");
/* 504:    */       }
/* 505:679 */       Object value = this.last.getValue();
/* 506:680 */       this.iterator.remove();
/* 507:681 */       this.parent.maps[1].remove(value);
/* 508:682 */       this.last = null;
/* 509:683 */       this.canRemove = false;
/* 510:    */     }
/* 511:    */     
/* 512:    */     public Object getKey()
/* 513:    */     {
/* 514:687 */       if (this.last == null) {
/* 515:688 */         throw new IllegalStateException("Iterator getKey() can only be called after next() and before remove()");
/* 516:    */       }
/* 517:690 */       return this.last.getKey();
/* 518:    */     }
/* 519:    */     
/* 520:    */     public Object getValue()
/* 521:    */     {
/* 522:694 */       if (this.last == null) {
/* 523:695 */         throw new IllegalStateException("Iterator getValue() can only be called after next() and before remove()");
/* 524:    */       }
/* 525:697 */       return this.last.getValue();
/* 526:    */     }
/* 527:    */     
/* 528:    */     public Object setValue(Object value)
/* 529:    */     {
/* 530:701 */       if (this.last == null) {
/* 531:702 */         throw new IllegalStateException("Iterator setValue() can only be called after next() and before remove()");
/* 532:    */       }
/* 533:704 */       if ((this.parent.maps[1].containsKey(value)) && (this.parent.maps[1].get(value) != this.last.getKey())) {
/* 534:706 */         throw new IllegalArgumentException("Cannot use setValue() when the object being set is already in the map");
/* 535:    */       }
/* 536:708 */       return this.parent.put(this.last.getKey(), value);
/* 537:    */     }
/* 538:    */     
/* 539:    */     public void reset()
/* 540:    */     {
/* 541:712 */       this.iterator = this.parent.maps[0].entrySet().iterator();
/* 542:713 */       this.last = null;
/* 543:714 */       this.canRemove = false;
/* 544:    */     }
/* 545:    */     
/* 546:    */     public String toString()
/* 547:    */     {
/* 548:718 */       if (this.last != null) {
/* 549:719 */         return "MapIterator[" + getKey() + "=" + getValue() + "]";
/* 550:    */       }
/* 551:721 */       return "MapIterator[]";
/* 552:    */     }
/* 553:    */   }
/* 554:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.bidimap.AbstractDualBidiMap
 * JD-Core Version:    0.7.0.1
 */