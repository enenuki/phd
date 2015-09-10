/*   1:    */ package org.apache.commons.collections;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.lang.ref.Reference;
/*   7:    */ import java.lang.ref.ReferenceQueue;
/*   8:    */ import java.lang.ref.SoftReference;
/*   9:    */ import java.lang.ref.WeakReference;
/*  10:    */ import java.util.AbstractCollection;
/*  11:    */ import java.util.AbstractMap;
/*  12:    */ import java.util.AbstractSet;
/*  13:    */ import java.util.ArrayList;
/*  14:    */ import java.util.Arrays;
/*  15:    */ import java.util.Collection;
/*  16:    */ import java.util.ConcurrentModificationException;
/*  17:    */ import java.util.Iterator;
/*  18:    */ import java.util.Map.Entry;
/*  19:    */ import java.util.NoSuchElementException;
/*  20:    */ import java.util.Set;
/*  21:    */ import org.apache.commons.collections.keyvalue.DefaultMapEntry;
/*  22:    */ 
/*  23:    */ /**
/*  24:    */  * @deprecated
/*  25:    */  */
/*  26:    */ public class ReferenceMap
/*  27:    */   extends AbstractMap
/*  28:    */ {
/*  29:    */   private static final long serialVersionUID = -3370601314380922368L;
/*  30:    */   public static final int HARD = 0;
/*  31:    */   public static final int SOFT = 1;
/*  32:    */   public static final int WEAK = 2;
/*  33:    */   private int keyType;
/*  34:    */   private int valueType;
/*  35:    */   private float loadFactor;
/*  36:144 */   private boolean purgeValues = false;
/*  37:153 */   private transient ReferenceQueue queue = new ReferenceQueue();
/*  38:    */   private transient Entry[] table;
/*  39:    */   private transient int size;
/*  40:    */   private transient int threshold;
/*  41:    */   private volatile transient int modCount;
/*  42:    */   private transient Set keySet;
/*  43:    */   private transient Set entrySet;
/*  44:    */   private transient Collection values;
/*  45:    */   
/*  46:    */   public ReferenceMap()
/*  47:    */   {
/*  48:204 */     this(0, 1);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public ReferenceMap(int keyType, int valueType, boolean purgeValues)
/*  52:    */   {
/*  53:219 */     this(keyType, valueType);
/*  54:220 */     this.purgeValues = purgeValues;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public ReferenceMap(int keyType, int valueType)
/*  58:    */   {
/*  59:233 */     this(keyType, valueType, 16, 0.75F);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public ReferenceMap(int keyType, int valueType, int capacity, float loadFactor, boolean purgeValues)
/*  63:    */   {
/*  64:256 */     this(keyType, valueType, capacity, loadFactor);
/*  65:257 */     this.purgeValues = purgeValues;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public ReferenceMap(int keyType, int valueType, int capacity, float loadFactor)
/*  69:    */   {
/*  70:275 */     verify("keyType", keyType);
/*  71:276 */     verify("valueType", valueType);
/*  72:278 */     if (capacity <= 0) {
/*  73:279 */       throw new IllegalArgumentException("capacity must be positive");
/*  74:    */     }
/*  75:281 */     if ((loadFactor <= 0.0F) || (loadFactor >= 1.0F)) {
/*  76:282 */       throw new IllegalArgumentException("Load factor must be greater than 0 and less than 1.");
/*  77:    */     }
/*  78:285 */     this.keyType = keyType;
/*  79:286 */     this.valueType = valueType;
/*  80:    */     
/*  81:288 */     int v = 1;
/*  82:289 */     while (v < capacity) {
/*  83:289 */       v *= 2;
/*  84:    */     }
/*  85:291 */     this.table = new Entry[v];
/*  86:292 */     this.loadFactor = loadFactor;
/*  87:293 */     this.threshold = ((int)(v * loadFactor));
/*  88:    */   }
/*  89:    */   
/*  90:    */   private static void verify(String name, int type)
/*  91:    */   {
/*  92:299 */     if ((type < 0) || (type > 2)) {
/*  93:300 */       throw new IllegalArgumentException(name + " must be HARD, SOFT, WEAK.");
/*  94:    */     }
/*  95:    */   }
/*  96:    */   
/*  97:    */   private void writeObject(ObjectOutputStream out)
/*  98:    */     throws IOException
/*  99:    */   {
/* 100:313 */     out.defaultWriteObject();
/* 101:314 */     out.writeInt(this.table.length);
/* 102:319 */     for (Iterator iter = entrySet().iterator(); iter.hasNext();)
/* 103:    */     {
/* 104:320 */       Map.Entry entry = (Map.Entry)iter.next();
/* 105:321 */       out.writeObject(entry.getKey());
/* 106:322 */       out.writeObject(entry.getValue());
/* 107:    */     }
/* 108:324 */     out.writeObject(null);
/* 109:    */   }
/* 110:    */   
/* 111:    */   private void readObject(ObjectInputStream inp)
/* 112:    */     throws IOException, ClassNotFoundException
/* 113:    */   {
/* 114:336 */     inp.defaultReadObject();
/* 115:337 */     this.table = new Entry[inp.readInt()];
/* 116:338 */     this.threshold = ((int)(this.table.length * this.loadFactor));
/* 117:339 */     this.queue = new ReferenceQueue();
/* 118:340 */     Object key = inp.readObject();
/* 119:341 */     while (key != null)
/* 120:    */     {
/* 121:342 */       Object value = inp.readObject();
/* 122:343 */       put(key, value);
/* 123:344 */       key = inp.readObject();
/* 124:    */     }
/* 125:    */   }
/* 126:    */   
/* 127:    */   private Object toReference(int type, Object referent, int hash)
/* 128:    */   {
/* 129:361 */     switch (type)
/* 130:    */     {
/* 131:    */     case 0: 
/* 132:362 */       return referent;
/* 133:    */     case 1: 
/* 134:363 */       return new SoftRef(hash, referent, this.queue);
/* 135:    */     case 2: 
/* 136:364 */       return new WeakRef(hash, referent, this.queue);
/* 137:    */     }
/* 138:365 */     throw new Error();
/* 139:    */   }
/* 140:    */   
/* 141:    */   private Entry getEntry(Object key)
/* 142:    */   {
/* 143:378 */     if (key == null) {
/* 144:378 */       return null;
/* 145:    */     }
/* 146:379 */     int hash = key.hashCode();
/* 147:380 */     int index = indexFor(hash);
/* 148:381 */     for (Entry entry = this.table[index]; entry != null; entry = entry.next) {
/* 149:382 */       if ((entry.hash == hash) && (key.equals(entry.getKey()))) {
/* 150:383 */         return entry;
/* 151:    */       }
/* 152:    */     }
/* 153:386 */     return null;
/* 154:    */   }
/* 155:    */   
/* 156:    */   private int indexFor(int hash)
/* 157:    */   {
/* 158:396 */     hash += (hash << 15 ^ 0xFFFFFFFF);
/* 159:397 */     hash ^= hash >>> 10;
/* 160:398 */     hash += (hash << 3);
/* 161:399 */     hash ^= hash >>> 6;
/* 162:400 */     hash += (hash << 11 ^ 0xFFFFFFFF);
/* 163:401 */     hash ^= hash >>> 16;
/* 164:402 */     return hash & this.table.length - 1;
/* 165:    */   }
/* 166:    */   
/* 167:    */   private void resize()
/* 168:    */   {
/* 169:414 */     Entry[] old = this.table;
/* 170:415 */     this.table = new Entry[old.length * 2];
/* 171:417 */     for (int i = 0; i < old.length; i++)
/* 172:    */     {
/* 173:418 */       Entry next = old[i];
/* 174:419 */       while (next != null)
/* 175:    */       {
/* 176:420 */         Entry entry = next;
/* 177:421 */         next = next.next;
/* 178:422 */         int index = indexFor(entry.hash);
/* 179:423 */         entry.next = this.table[index];
/* 180:424 */         this.table[index] = entry;
/* 181:    */       }
/* 182:426 */       old[i] = null;
/* 183:    */     }
/* 184:428 */     this.threshold = ((int)(this.table.length * this.loadFactor));
/* 185:    */   }
/* 186:    */   
/* 187:    */   private void purge()
/* 188:    */   {
/* 189:446 */     Reference ref = this.queue.poll();
/* 190:447 */     while (ref != null)
/* 191:    */     {
/* 192:448 */       purge(ref);
/* 193:449 */       ref = this.queue.poll();
/* 194:    */     }
/* 195:    */   }
/* 196:    */   
/* 197:    */   private void purge(Reference ref)
/* 198:    */   {
/* 199:458 */     int hash = ref.hashCode();
/* 200:459 */     int index = indexFor(hash);
/* 201:460 */     Entry previous = null;
/* 202:461 */     Entry entry = this.table[index];
/* 203:462 */     while (entry != null)
/* 204:    */     {
/* 205:463 */       if (entry.purge(ref))
/* 206:    */       {
/* 207:464 */         if (previous == null) {
/* 208:464 */           this.table[index] = entry.next;
/* 209:    */         } else {
/* 210:465 */           previous.next = entry.next;
/* 211:    */         }
/* 212:466 */         this.size -= 1;
/* 213:467 */         return;
/* 214:    */       }
/* 215:469 */       previous = entry;
/* 216:470 */       entry = entry.next;
/* 217:    */     }
/* 218:    */   }
/* 219:    */   
/* 220:    */   public int size()
/* 221:    */   {
/* 222:482 */     purge();
/* 223:483 */     return this.size;
/* 224:    */   }
/* 225:    */   
/* 226:    */   public boolean isEmpty()
/* 227:    */   {
/* 228:493 */     purge();
/* 229:494 */     return this.size == 0;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public boolean containsKey(Object key)
/* 233:    */   {
/* 234:504 */     purge();
/* 235:505 */     Entry entry = getEntry(key);
/* 236:506 */     if (entry == null) {
/* 237:506 */       return false;
/* 238:    */     }
/* 239:507 */     return entry.getValue() != null;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public Object get(Object key)
/* 243:    */   {
/* 244:518 */     purge();
/* 245:519 */     Entry entry = getEntry(key);
/* 246:520 */     if (entry == null) {
/* 247:520 */       return null;
/* 248:    */     }
/* 249:521 */     return entry.getValue();
/* 250:    */   }
/* 251:    */   
/* 252:    */   public Object put(Object key, Object value)
/* 253:    */   {
/* 254:537 */     if (key == null) {
/* 255:537 */       throw new NullPointerException("null keys not allowed");
/* 256:    */     }
/* 257:538 */     if (value == null) {
/* 258:538 */       throw new NullPointerException("null values not allowed");
/* 259:    */     }
/* 260:540 */     purge();
/* 261:541 */     if (this.size + 1 > this.threshold) {
/* 262:541 */       resize();
/* 263:    */     }
/* 264:543 */     int hash = key.hashCode();
/* 265:544 */     int index = indexFor(hash);
/* 266:545 */     Entry entry = this.table[index];
/* 267:546 */     while (entry != null)
/* 268:    */     {
/* 269:547 */       if ((hash == entry.hash) && (key.equals(entry.getKey())))
/* 270:    */       {
/* 271:548 */         Object result = entry.getValue();
/* 272:549 */         entry.setValue(value);
/* 273:550 */         return result;
/* 274:    */       }
/* 275:552 */       entry = entry.next;
/* 276:    */     }
/* 277:554 */     this.size += 1;
/* 278:555 */     this.modCount += 1;
/* 279:556 */     key = toReference(this.keyType, key, hash);
/* 280:557 */     value = toReference(this.valueType, value, hash);
/* 281:558 */     this.table[index] = new Entry(key, hash, value, this.table[index]);
/* 282:559 */     return null;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public Object remove(Object key)
/* 286:    */   {
/* 287:571 */     if (key == null) {
/* 288:571 */       return null;
/* 289:    */     }
/* 290:572 */     purge();
/* 291:573 */     int hash = key.hashCode();
/* 292:574 */     int index = indexFor(hash);
/* 293:575 */     Entry previous = null;
/* 294:576 */     Entry entry = this.table[index];
/* 295:577 */     while (entry != null)
/* 296:    */     {
/* 297:578 */       if ((hash == entry.hash) && (key.equals(entry.getKey())))
/* 298:    */       {
/* 299:579 */         if (previous == null) {
/* 300:579 */           this.table[index] = entry.next;
/* 301:    */         } else {
/* 302:580 */           previous.next = entry.next;
/* 303:    */         }
/* 304:581 */         this.size -= 1;
/* 305:582 */         this.modCount += 1;
/* 306:583 */         return entry.getValue();
/* 307:    */       }
/* 308:585 */       previous = entry;
/* 309:586 */       entry = entry.next;
/* 310:    */     }
/* 311:588 */     return null;
/* 312:    */   }
/* 313:    */   
/* 314:    */   public void clear()
/* 315:    */   {
/* 316:596 */     Arrays.fill(this.table, null);
/* 317:597 */     this.size = 0;
/* 318:598 */     while (this.queue.poll() != null) {}
/* 319:    */   }
/* 320:    */   
/* 321:    */   public Set entrySet()
/* 322:    */   {
/* 323:608 */     if (this.entrySet != null) {
/* 324:609 */       return this.entrySet;
/* 325:    */     }
/* 326:611 */     this.entrySet = new AbstractSet()
/* 327:    */     {
/* 328:    */       public int size()
/* 329:    */       {
/* 330:613 */         return ReferenceMap.this.size();
/* 331:    */       }
/* 332:    */       
/* 333:    */       public void clear()
/* 334:    */       {
/* 335:617 */         ReferenceMap.this.clear();
/* 336:    */       }
/* 337:    */       
/* 338:    */       public boolean contains(Object o)
/* 339:    */       {
/* 340:621 */         if (o == null) {
/* 341:621 */           return false;
/* 342:    */         }
/* 343:622 */         if (!(o instanceof Map.Entry)) {
/* 344:622 */           return false;
/* 345:    */         }
/* 346:623 */         Map.Entry e = (Map.Entry)o;
/* 347:624 */         ReferenceMap.Entry e2 = ReferenceMap.this.getEntry(e.getKey());
/* 348:625 */         return (e2 != null) && (e.equals(e2));
/* 349:    */       }
/* 350:    */       
/* 351:    */       public boolean remove(Object o)
/* 352:    */       {
/* 353:629 */         boolean r = contains(o);
/* 354:630 */         if (r)
/* 355:    */         {
/* 356:631 */           Map.Entry e = (Map.Entry)o;
/* 357:632 */           ReferenceMap.this.remove(e.getKey());
/* 358:    */         }
/* 359:634 */         return r;
/* 360:    */       }
/* 361:    */       
/* 362:    */       public Iterator iterator()
/* 363:    */       {
/* 364:638 */         return new ReferenceMap.EntryIterator(ReferenceMap.this);
/* 365:    */       }
/* 366:    */       
/* 367:    */       public Object[] toArray()
/* 368:    */       {
/* 369:642 */         return toArray(new Object[0]);
/* 370:    */       }
/* 371:    */       
/* 372:    */       public Object[] toArray(Object[] arr)
/* 373:    */       {
/* 374:646 */         ArrayList list = new ArrayList();
/* 375:647 */         Iterator iterator = iterator();
/* 376:648 */         while (iterator.hasNext())
/* 377:    */         {
/* 378:649 */           ReferenceMap.Entry e = (ReferenceMap.Entry)iterator.next();
/* 379:650 */           list.add(new DefaultMapEntry(e.getKey(), e.getValue()));
/* 380:    */         }
/* 381:652 */         return list.toArray(arr);
/* 382:    */       }
/* 383:654 */     };
/* 384:655 */     return this.entrySet;
/* 385:    */   }
/* 386:    */   
/* 387:    */   public Set keySet()
/* 388:    */   {
/* 389:665 */     if (this.keySet != null) {
/* 390:665 */       return this.keySet;
/* 391:    */     }
/* 392:666 */     this.keySet = new AbstractSet()
/* 393:    */     {
/* 394:    */       public int size()
/* 395:    */       {
/* 396:668 */         return ReferenceMap.this.size();
/* 397:    */       }
/* 398:    */       
/* 399:    */       public Iterator iterator()
/* 400:    */       {
/* 401:672 */         return new ReferenceMap.KeyIterator(ReferenceMap.this, null);
/* 402:    */       }
/* 403:    */       
/* 404:    */       public boolean contains(Object o)
/* 405:    */       {
/* 406:676 */         return ReferenceMap.this.containsKey(o);
/* 407:    */       }
/* 408:    */       
/* 409:    */       public boolean remove(Object o)
/* 410:    */       {
/* 411:681 */         Object r = ReferenceMap.this.remove(o);
/* 412:682 */         return r != null;
/* 413:    */       }
/* 414:    */       
/* 415:    */       public void clear()
/* 416:    */       {
/* 417:686 */         ReferenceMap.this.clear();
/* 418:    */       }
/* 419:    */       
/* 420:    */       public Object[] toArray()
/* 421:    */       {
/* 422:690 */         return toArray(new Object[0]);
/* 423:    */       }
/* 424:    */       
/* 425:    */       public Object[] toArray(Object[] array)
/* 426:    */       {
/* 427:694 */         Collection c = new ArrayList(size());
/* 428:695 */         for (Iterator it = iterator(); it.hasNext();) {
/* 429:696 */           c.add(it.next());
/* 430:    */         }
/* 431:698 */         return c.toArray(array);
/* 432:    */       }
/* 433:700 */     };
/* 434:701 */     return this.keySet;
/* 435:    */   }
/* 436:    */   
/* 437:    */   public Collection values()
/* 438:    */   {
/* 439:711 */     if (this.values != null) {
/* 440:711 */       return this.values;
/* 441:    */     }
/* 442:712 */     this.values = new AbstractCollection()
/* 443:    */     {
/* 444:    */       public int size()
/* 445:    */       {
/* 446:714 */         return ReferenceMap.this.size();
/* 447:    */       }
/* 448:    */       
/* 449:    */       public void clear()
/* 450:    */       {
/* 451:718 */         ReferenceMap.this.clear();
/* 452:    */       }
/* 453:    */       
/* 454:    */       public Iterator iterator()
/* 455:    */       {
/* 456:722 */         return new ReferenceMap.ValueIterator(ReferenceMap.this, null);
/* 457:    */       }
/* 458:    */       
/* 459:    */       public Object[] toArray()
/* 460:    */       {
/* 461:726 */         return toArray(new Object[0]);
/* 462:    */       }
/* 463:    */       
/* 464:    */       public Object[] toArray(Object[] array)
/* 465:    */       {
/* 466:730 */         Collection c = new ArrayList(size());
/* 467:731 */         for (Iterator it = iterator(); it.hasNext();) {
/* 468:732 */           c.add(it.next());
/* 469:    */         }
/* 470:734 */         return c.toArray(array);
/* 471:    */       }
/* 472:736 */     };
/* 473:737 */     return this.values;
/* 474:    */   }
/* 475:    */   
/* 476:    */   private class Entry
/* 477:    */     implements Map.Entry, KeyValue
/* 478:    */   {
/* 479:    */     Object key;
/* 480:    */     Object value;
/* 481:    */     int hash;
/* 482:    */     Entry next;
/* 483:    */     
/* 484:    */     public Entry(Object key, int hash, Object value, Entry next)
/* 485:    */     {
/* 486:752 */       this.key = key;
/* 487:753 */       this.hash = hash;
/* 488:754 */       this.value = value;
/* 489:755 */       this.next = next;
/* 490:    */     }
/* 491:    */     
/* 492:    */     public Object getKey()
/* 493:    */     {
/* 494:760 */       return ReferenceMap.this.keyType > 0 ? ((Reference)this.key).get() : this.key;
/* 495:    */     }
/* 496:    */     
/* 497:    */     public Object getValue()
/* 498:    */     {
/* 499:765 */       return ReferenceMap.this.valueType > 0 ? ((Reference)this.value).get() : this.value;
/* 500:    */     }
/* 501:    */     
/* 502:    */     public Object setValue(Object object)
/* 503:    */     {
/* 504:770 */       Object old = getValue();
/* 505:771 */       if (ReferenceMap.this.valueType > 0) {
/* 506:771 */         ((Reference)this.value).clear();
/* 507:    */       }
/* 508:772 */       this.value = ReferenceMap.this.toReference(ReferenceMap.this.valueType, object, this.hash);
/* 509:773 */       return old;
/* 510:    */     }
/* 511:    */     
/* 512:    */     public boolean equals(Object o)
/* 513:    */     {
/* 514:778 */       if (o == null) {
/* 515:778 */         return false;
/* 516:    */       }
/* 517:779 */       if (o == this) {
/* 518:779 */         return true;
/* 519:    */       }
/* 520:780 */       if (!(o instanceof Map.Entry)) {
/* 521:780 */         return false;
/* 522:    */       }
/* 523:782 */       Map.Entry entry = (Map.Entry)o;
/* 524:783 */       Object key = entry.getKey();
/* 525:784 */       Object value = entry.getValue();
/* 526:785 */       if ((key == null) || (value == null)) {
/* 527:785 */         return false;
/* 528:    */       }
/* 529:786 */       return (key.equals(getKey())) && (value.equals(getValue()));
/* 530:    */     }
/* 531:    */     
/* 532:    */     public int hashCode()
/* 533:    */     {
/* 534:791 */       Object v = getValue();
/* 535:792 */       return this.hash ^ (v == null ? 0 : v.hashCode());
/* 536:    */     }
/* 537:    */     
/* 538:    */     public String toString()
/* 539:    */     {
/* 540:797 */       return getKey() + "=" + getValue();
/* 541:    */     }
/* 542:    */     
/* 543:    */     boolean purge(Reference ref)
/* 544:    */     {
/* 545:802 */       boolean r = (ReferenceMap.this.keyType > 0) && (this.key == ref);
/* 546:803 */       r = (r) || ((ReferenceMap.this.valueType > 0) && (this.value == ref));
/* 547:804 */       if (r)
/* 548:    */       {
/* 549:805 */         if (ReferenceMap.this.keyType > 0) {
/* 550:805 */           ((Reference)this.key).clear();
/* 551:    */         }
/* 552:806 */         if (ReferenceMap.this.valueType > 0) {
/* 553:807 */           ((Reference)this.value).clear();
/* 554:808 */         } else if (ReferenceMap.this.purgeValues) {
/* 555:809 */           this.value = null;
/* 556:    */         }
/* 557:    */       }
/* 558:812 */       return r;
/* 559:    */     }
/* 560:    */   }
/* 561:    */   
/* 562:    */   private class EntryIterator
/* 563:    */     implements Iterator
/* 564:    */   {
/* 565:    */     int index;
/* 566:    */     ReferenceMap.Entry entry;
/* 567:    */     ReferenceMap.Entry previous;
/* 568:    */     Object nextKey;
/* 569:    */     Object nextValue;
/* 570:    */     Object currentKey;
/* 571:    */     Object currentValue;
/* 572:    */     int expectedModCount;
/* 573:    */     
/* 574:    */     public EntryIterator()
/* 575:    */     {
/* 576:833 */       this.index = (ReferenceMap.this.size() != 0 ? ReferenceMap.this.table.length : 0);
/* 577:    */       
/* 578:    */ 
/* 579:836 */       this.expectedModCount = ReferenceMap.this.modCount;
/* 580:    */     }
/* 581:    */     
/* 582:    */     public boolean hasNext()
/* 583:    */     {
/* 584:841 */       checkMod();
/* 585:842 */       while (nextNull())
/* 586:    */       {
/* 587:843 */         ReferenceMap.Entry e = this.entry;
/* 588:844 */         int i = this.index;
/* 589:845 */         while ((e == null) && (i > 0))
/* 590:    */         {
/* 591:846 */           i--;
/* 592:847 */           e = ReferenceMap.this.table[i];
/* 593:    */         }
/* 594:849 */         this.entry = e;
/* 595:850 */         this.index = i;
/* 596:851 */         if (e == null)
/* 597:    */         {
/* 598:852 */           this.currentKey = null;
/* 599:853 */           this.currentValue = null;
/* 600:854 */           return false;
/* 601:    */         }
/* 602:856 */         this.nextKey = e.getKey();
/* 603:857 */         this.nextValue = e.getValue();
/* 604:858 */         if (nextNull()) {
/* 605:858 */           this.entry = this.entry.next;
/* 606:    */         }
/* 607:    */       }
/* 608:860 */       return true;
/* 609:    */     }
/* 610:    */     
/* 611:    */     private void checkMod()
/* 612:    */     {
/* 613:865 */       if (ReferenceMap.this.modCount != this.expectedModCount) {
/* 614:866 */         throw new ConcurrentModificationException();
/* 615:    */       }
/* 616:    */     }
/* 617:    */     
/* 618:    */     private boolean nextNull()
/* 619:    */     {
/* 620:872 */       return (this.nextKey == null) || (this.nextValue == null);
/* 621:    */     }
/* 622:    */     
/* 623:    */     protected ReferenceMap.Entry nextEntry()
/* 624:    */     {
/* 625:876 */       checkMod();
/* 626:877 */       if ((nextNull()) && (!hasNext())) {
/* 627:877 */         throw new NoSuchElementException();
/* 628:    */       }
/* 629:878 */       this.previous = this.entry;
/* 630:879 */       this.entry = this.entry.next;
/* 631:880 */       this.currentKey = this.nextKey;
/* 632:881 */       this.currentValue = this.nextValue;
/* 633:882 */       this.nextKey = null;
/* 634:883 */       this.nextValue = null;
/* 635:884 */       return this.previous;
/* 636:    */     }
/* 637:    */     
/* 638:    */     public Object next()
/* 639:    */     {
/* 640:889 */       return nextEntry();
/* 641:    */     }
/* 642:    */     
/* 643:    */     public void remove()
/* 644:    */     {
/* 645:894 */       checkMod();
/* 646:895 */       if (this.previous == null) {
/* 647:895 */         throw new IllegalStateException();
/* 648:    */       }
/* 649:896 */       ReferenceMap.this.remove(this.currentKey);
/* 650:897 */       this.previous = null;
/* 651:898 */       this.currentKey = null;
/* 652:899 */       this.currentValue = null;
/* 653:900 */       this.expectedModCount = ReferenceMap.this.modCount;
/* 654:    */     }
/* 655:    */   }
/* 656:    */   
/* 657:    */   private class ValueIterator
/* 658:    */     extends ReferenceMap.EntryIterator
/* 659:    */   {
/* 660:    */     ValueIterator(ReferenceMap.1 x1)
/* 661:    */     {
/* 662:906 */       this();
/* 663:    */     }
/* 664:    */     
/* 665:    */     private ValueIterator()
/* 666:    */     {
/* 667:906 */       super();
/* 668:    */     }
/* 669:    */     
/* 670:    */     public Object next()
/* 671:    */     {
/* 672:908 */       return nextEntry().getValue();
/* 673:    */     }
/* 674:    */   }
/* 675:    */   
/* 676:    */   private class KeyIterator
/* 677:    */     extends ReferenceMap.EntryIterator
/* 678:    */   {
/* 679:    */     KeyIterator(ReferenceMap.1 x1)
/* 680:    */     {
/* 681:913 */       this();
/* 682:    */     }
/* 683:    */     
/* 684:    */     private KeyIterator()
/* 685:    */     {
/* 686:913 */       super();
/* 687:    */     }
/* 688:    */     
/* 689:    */     public Object next()
/* 690:    */     {
/* 691:915 */       return nextEntry().getKey();
/* 692:    */     }
/* 693:    */   }
/* 694:    */   
/* 695:    */   private static class SoftRef
/* 696:    */     extends SoftReference
/* 697:    */   {
/* 698:    */     private int hash;
/* 699:    */     
/* 700:    */     public SoftRef(int hash, Object r, ReferenceQueue q)
/* 701:    */     {
/* 702:931 */       super(q);
/* 703:932 */       this.hash = hash;
/* 704:    */     }
/* 705:    */     
/* 706:    */     public int hashCode()
/* 707:    */     {
/* 708:937 */       return this.hash;
/* 709:    */     }
/* 710:    */   }
/* 711:    */   
/* 712:    */   private static class WeakRef
/* 713:    */     extends WeakReference
/* 714:    */   {
/* 715:    */     private int hash;
/* 716:    */     
/* 717:    */     public WeakRef(int hash, Object r, ReferenceQueue q)
/* 718:    */     {
/* 719:947 */       super(q);
/* 720:948 */       this.hash = hash;
/* 721:    */     }
/* 722:    */     
/* 723:    */     public int hashCode()
/* 724:    */     {
/* 725:953 */       return this.hash;
/* 726:    */     }
/* 727:    */   }
/* 728:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.ReferenceMap
 * JD-Core Version:    0.7.0.1
 */