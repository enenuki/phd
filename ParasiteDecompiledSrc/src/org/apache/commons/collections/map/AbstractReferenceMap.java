/*   1:    */ package org.apache.commons.collections.map;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.ObjectInputStream;
/*   5:    */ import java.io.ObjectOutputStream;
/*   6:    */ import java.lang.ref.Reference;
/*   7:    */ import java.lang.ref.ReferenceQueue;
/*   8:    */ import java.lang.ref.SoftReference;
/*   9:    */ import java.lang.ref.WeakReference;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.Collection;
/*  12:    */ import java.util.ConcurrentModificationException;
/*  13:    */ import java.util.Iterator;
/*  14:    */ import java.util.List;
/*  15:    */ import java.util.Map.Entry;
/*  16:    */ import java.util.NoSuchElementException;
/*  17:    */ import java.util.Set;
/*  18:    */ import org.apache.commons.collections.MapIterator;
/*  19:    */ import org.apache.commons.collections.keyvalue.DefaultMapEntry;
/*  20:    */ 
/*  21:    */ public abstract class AbstractReferenceMap
/*  22:    */   extends AbstractHashedMap
/*  23:    */ {
/*  24:    */   public static final int HARD = 0;
/*  25:    */   public static final int SOFT = 1;
/*  26:    */   public static final int WEAK = 2;
/*  27:    */   protected int keyType;
/*  28:    */   protected int valueType;
/*  29:    */   protected boolean purgeValues;
/*  30:    */   private transient ReferenceQueue queue;
/*  31:    */   
/*  32:    */   protected AbstractReferenceMap() {}
/*  33:    */   
/*  34:    */   protected AbstractReferenceMap(int keyType, int valueType, int capacity, float loadFactor, boolean purgeValues)
/*  35:    */   {
/*  36:143 */     super(capacity, loadFactor);
/*  37:144 */     verify("keyType", keyType);
/*  38:145 */     verify("valueType", valueType);
/*  39:146 */     this.keyType = keyType;
/*  40:147 */     this.valueType = valueType;
/*  41:148 */     this.purgeValues = purgeValues;
/*  42:    */   }
/*  43:    */   
/*  44:    */   protected void init()
/*  45:    */   {
/*  46:155 */     this.queue = new ReferenceQueue();
/*  47:    */   }
/*  48:    */   
/*  49:    */   private static void verify(String name, int type)
/*  50:    */   {
/*  51:167 */     if ((type < 0) || (type > 2)) {
/*  52:168 */       throw new IllegalArgumentException(name + " must be HARD, SOFT, WEAK.");
/*  53:    */     }
/*  54:    */   }
/*  55:    */   
/*  56:    */   public int size()
/*  57:    */   {
/*  58:179 */     purgeBeforeRead();
/*  59:180 */     return super.size();
/*  60:    */   }
/*  61:    */   
/*  62:    */   public boolean isEmpty()
/*  63:    */   {
/*  64:189 */     purgeBeforeRead();
/*  65:190 */     return super.isEmpty();
/*  66:    */   }
/*  67:    */   
/*  68:    */   public boolean containsKey(Object key)
/*  69:    */   {
/*  70:200 */     purgeBeforeRead();
/*  71:201 */     Map.Entry entry = getEntry(key);
/*  72:202 */     if (entry == null) {
/*  73:203 */       return false;
/*  74:    */     }
/*  75:205 */     return entry.getValue() != null;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public boolean containsValue(Object value)
/*  79:    */   {
/*  80:215 */     purgeBeforeRead();
/*  81:216 */     if (value == null) {
/*  82:217 */       return false;
/*  83:    */     }
/*  84:219 */     return super.containsValue(value);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public Object get(Object key)
/*  88:    */   {
/*  89:229 */     purgeBeforeRead();
/*  90:230 */     Map.Entry entry = getEntry(key);
/*  91:231 */     if (entry == null) {
/*  92:232 */       return null;
/*  93:    */     }
/*  94:234 */     return entry.getValue();
/*  95:    */   }
/*  96:    */   
/*  97:    */   public Object put(Object key, Object value)
/*  98:    */   {
/*  99:248 */     if (key == null) {
/* 100:249 */       throw new NullPointerException("null keys not allowed");
/* 101:    */     }
/* 102:251 */     if (value == null) {
/* 103:252 */       throw new NullPointerException("null values not allowed");
/* 104:    */     }
/* 105:255 */     purgeBeforeWrite();
/* 106:256 */     return super.put(key, value);
/* 107:    */   }
/* 108:    */   
/* 109:    */   public Object remove(Object key)
/* 110:    */   {
/* 111:266 */     if (key == null) {
/* 112:267 */       return null;
/* 113:    */     }
/* 114:269 */     purgeBeforeWrite();
/* 115:270 */     return super.remove(key);
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void clear()
/* 119:    */   {
/* 120:277 */     super.clear();
/* 121:278 */     while (this.queue.poll() != null) {}
/* 122:    */   }
/* 123:    */   
/* 124:    */   public MapIterator mapIterator()
/* 125:    */   {
/* 126:289 */     return new ReferenceMapIterator(this);
/* 127:    */   }
/* 128:    */   
/* 129:    */   public Set entrySet()
/* 130:    */   {
/* 131:300 */     if (this.entrySet == null) {
/* 132:301 */       this.entrySet = new ReferenceEntrySet(this);
/* 133:    */     }
/* 134:303 */     return this.entrySet;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public Set keySet()
/* 138:    */   {
/* 139:312 */     if (this.keySet == null) {
/* 140:313 */       this.keySet = new ReferenceKeySet(this);
/* 141:    */     }
/* 142:315 */     return this.keySet;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public Collection values()
/* 146:    */   {
/* 147:324 */     if (this.values == null) {
/* 148:325 */       this.values = new ReferenceValues(this);
/* 149:    */     }
/* 150:327 */     return this.values;
/* 151:    */   }
/* 152:    */   
/* 153:    */   protected void purgeBeforeRead()
/* 154:    */   {
/* 155:337 */     purge();
/* 156:    */   }
/* 157:    */   
/* 158:    */   protected void purgeBeforeWrite()
/* 159:    */   {
/* 160:346 */     purge();
/* 161:    */   }
/* 162:    */   
/* 163:    */   protected void purge()
/* 164:    */   {
/* 165:358 */     Reference ref = this.queue.poll();
/* 166:359 */     while (ref != null)
/* 167:    */     {
/* 168:360 */       purge(ref);
/* 169:361 */       ref = this.queue.poll();
/* 170:    */     }
/* 171:    */   }
/* 172:    */   
/* 173:    */   protected void purge(Reference ref)
/* 174:    */   {
/* 175:374 */     int hash = ref.hashCode();
/* 176:375 */     int index = hashIndex(hash, this.data.length);
/* 177:376 */     AbstractHashedMap.HashEntry previous = null;
/* 178:377 */     AbstractHashedMap.HashEntry entry = this.data[index];
/* 179:378 */     while (entry != null)
/* 180:    */     {
/* 181:379 */       if (((ReferenceEntry)entry).purge(ref))
/* 182:    */       {
/* 183:380 */         if (previous == null) {
/* 184:381 */           this.data[index] = entry.next;
/* 185:    */         } else {
/* 186:383 */           previous.next = entry.next;
/* 187:    */         }
/* 188:385 */         this.size -= 1;
/* 189:386 */         return;
/* 190:    */       }
/* 191:388 */       previous = entry;
/* 192:389 */       entry = entry.next;
/* 193:    */     }
/* 194:    */   }
/* 195:    */   
/* 196:    */   protected AbstractHashedMap.HashEntry getEntry(Object key)
/* 197:    */   {
/* 198:402 */     if (key == null) {
/* 199:403 */       return null;
/* 200:    */     }
/* 201:405 */     return super.getEntry(key);
/* 202:    */   }
/* 203:    */   
/* 204:    */   protected int hashEntry(Object key, Object value)
/* 205:    */   {
/* 206:418 */     return (key == null ? 0 : key.hashCode()) ^ (value == null ? 0 : value.hashCode());
/* 207:    */   }
/* 208:    */   
/* 209:    */   protected boolean isEqualKey(Object key1, Object key2)
/* 210:    */   {
/* 211:433 */     key2 = this.keyType > 0 ? ((Reference)key2).get() : key2;
/* 212:434 */     return (key1 == key2) || (key1.equals(key2));
/* 213:    */   }
/* 214:    */   
/* 215:    */   protected AbstractHashedMap.HashEntry createEntry(AbstractHashedMap.HashEntry next, int hashCode, Object key, Object value)
/* 216:    */   {
/* 217:447 */     return new ReferenceEntry(this, next, hashCode, key, value);
/* 218:    */   }
/* 219:    */   
/* 220:    */   protected Iterator createEntrySetIterator()
/* 221:    */   {
/* 222:456 */     return new ReferenceEntrySetIterator(this);
/* 223:    */   }
/* 224:    */   
/* 225:    */   protected Iterator createKeySetIterator()
/* 226:    */   {
/* 227:465 */     return new ReferenceKeySetIterator(this);
/* 228:    */   }
/* 229:    */   
/* 230:    */   protected Iterator createValuesIterator()
/* 231:    */   {
/* 232:474 */     return new ReferenceValuesIterator(this);
/* 233:    */   }
/* 234:    */   
/* 235:    */   static class ReferenceEntrySet
/* 236:    */     extends AbstractHashedMap.EntrySet
/* 237:    */   {
/* 238:    */     protected ReferenceEntrySet(AbstractHashedMap parent)
/* 239:    */     {
/* 240:484 */       super();
/* 241:    */     }
/* 242:    */     
/* 243:    */     public Object[] toArray()
/* 244:    */     {
/* 245:488 */       return toArray(new Object[0]);
/* 246:    */     }
/* 247:    */     
/* 248:    */     public Object[] toArray(Object[] arr)
/* 249:    */     {
/* 250:493 */       ArrayList list = new ArrayList();
/* 251:494 */       Iterator iterator = iterator();
/* 252:495 */       while (iterator.hasNext())
/* 253:    */       {
/* 254:496 */         Map.Entry e = (Map.Entry)iterator.next();
/* 255:497 */         list.add(new DefaultMapEntry(e.getKey(), e.getValue()));
/* 256:    */       }
/* 257:499 */       return list.toArray(arr);
/* 258:    */     }
/* 259:    */   }
/* 260:    */   
/* 261:    */   static class ReferenceKeySet
/* 262:    */     extends AbstractHashedMap.KeySet
/* 263:    */   {
/* 264:    */     protected ReferenceKeySet(AbstractHashedMap parent)
/* 265:    */     {
/* 266:510 */       super();
/* 267:    */     }
/* 268:    */     
/* 269:    */     public Object[] toArray()
/* 270:    */     {
/* 271:514 */       return toArray(new Object[0]);
/* 272:    */     }
/* 273:    */     
/* 274:    */     public Object[] toArray(Object[] arr)
/* 275:    */     {
/* 276:519 */       List list = new ArrayList(this.parent.size());
/* 277:520 */       for (Iterator it = iterator(); it.hasNext();) {
/* 278:521 */         list.add(it.next());
/* 279:    */       }
/* 280:523 */       return list.toArray(arr);
/* 281:    */     }
/* 282:    */   }
/* 283:    */   
/* 284:    */   static class ReferenceValues
/* 285:    */     extends AbstractHashedMap.Values
/* 286:    */   {
/* 287:    */     protected ReferenceValues(AbstractHashedMap parent)
/* 288:    */     {
/* 289:534 */       super();
/* 290:    */     }
/* 291:    */     
/* 292:    */     public Object[] toArray()
/* 293:    */     {
/* 294:538 */       return toArray(new Object[0]);
/* 295:    */     }
/* 296:    */     
/* 297:    */     public Object[] toArray(Object[] arr)
/* 298:    */     {
/* 299:543 */       List list = new ArrayList(this.parent.size());
/* 300:544 */       for (Iterator it = iterator(); it.hasNext();) {
/* 301:545 */         list.add(it.next());
/* 302:    */       }
/* 303:547 */       return list.toArray(arr);
/* 304:    */     }
/* 305:    */   }
/* 306:    */   
/* 307:    */   protected static class ReferenceEntry
/* 308:    */     extends AbstractHashedMap.HashEntry
/* 309:    */   {
/* 310:    */     protected final AbstractReferenceMap parent;
/* 311:    */     
/* 312:    */     public ReferenceEntry(AbstractReferenceMap parent, AbstractHashedMap.HashEntry next, int hashCode, Object key, Object value)
/* 313:    */     {
/* 314:574 */       super(hashCode, null, null);
/* 315:575 */       this.parent = parent;
/* 316:576 */       this.key = toReference(parent.keyType, key, hashCode);
/* 317:577 */       this.value = toReference(parent.valueType, value, hashCode);
/* 318:    */     }
/* 319:    */     
/* 320:    */     public Object getKey()
/* 321:    */     {
/* 322:587 */       return this.parent.keyType > 0 ? ((Reference)this.key).get() : this.key;
/* 323:    */     }
/* 324:    */     
/* 325:    */     public Object getValue()
/* 326:    */     {
/* 327:597 */       return this.parent.valueType > 0 ? ((Reference)this.value).get() : this.value;
/* 328:    */     }
/* 329:    */     
/* 330:    */     public Object setValue(Object obj)
/* 331:    */     {
/* 332:607 */       Object old = getValue();
/* 333:608 */       if (this.parent.valueType > 0) {
/* 334:609 */         ((Reference)this.value).clear();
/* 335:    */       }
/* 336:611 */       this.value = toReference(this.parent.valueType, obj, this.hashCode);
/* 337:612 */       return old;
/* 338:    */     }
/* 339:    */     
/* 340:    */     public boolean equals(Object obj)
/* 341:    */     {
/* 342:625 */       if (obj == this) {
/* 343:626 */         return true;
/* 344:    */       }
/* 345:628 */       if (!(obj instanceof Map.Entry)) {
/* 346:629 */         return false;
/* 347:    */       }
/* 348:632 */       Map.Entry entry = (Map.Entry)obj;
/* 349:633 */       Object entryKey = entry.getKey();
/* 350:634 */       Object entryValue = entry.getValue();
/* 351:635 */       if ((entryKey == null) || (entryValue == null)) {
/* 352:636 */         return false;
/* 353:    */       }
/* 354:640 */       return (this.parent.isEqualKey(entryKey, this.key)) && (this.parent.isEqualValue(entryValue, getValue()));
/* 355:    */     }
/* 356:    */     
/* 357:    */     public int hashCode()
/* 358:    */     {
/* 359:652 */       return this.parent.hashEntry(getKey(), getValue());
/* 360:    */     }
/* 361:    */     
/* 362:    */     protected Object toReference(int type, Object referent, int hash)
/* 363:    */     {
/* 364:666 */       switch (type)
/* 365:    */       {
/* 366:    */       case 0: 
/* 367:667 */         return referent;
/* 368:    */       case 1: 
/* 369:668 */         return new AbstractReferenceMap.SoftRef(hash, referent, this.parent.queue);
/* 370:    */       case 2: 
/* 371:669 */         return new AbstractReferenceMap.WeakRef(hash, referent, this.parent.queue);
/* 372:    */       }
/* 373:670 */       throw new Error();
/* 374:    */     }
/* 375:    */     
/* 376:    */     boolean purge(Reference ref)
/* 377:    */     {
/* 378:680 */       boolean r = (this.parent.keyType > 0) && (this.key == ref);
/* 379:681 */       r = (r) || ((this.parent.valueType > 0) && (this.value == ref));
/* 380:682 */       if (r)
/* 381:    */       {
/* 382:683 */         if (this.parent.keyType > 0) {
/* 383:684 */           ((Reference)this.key).clear();
/* 384:    */         }
/* 385:686 */         if (this.parent.valueType > 0) {
/* 386:687 */           ((Reference)this.value).clear();
/* 387:688 */         } else if (this.parent.purgeValues) {
/* 388:689 */           this.value = null;
/* 389:    */         }
/* 390:    */       }
/* 391:692 */       return r;
/* 392:    */     }
/* 393:    */     
/* 394:    */     protected ReferenceEntry next()
/* 395:    */     {
/* 396:701 */       return (ReferenceEntry)this.next;
/* 397:    */     }
/* 398:    */   }
/* 399:    */   
/* 400:    */   static class ReferenceEntrySetIterator
/* 401:    */     implements Iterator
/* 402:    */   {
/* 403:    */     final AbstractReferenceMap parent;
/* 404:    */     int index;
/* 405:    */     AbstractReferenceMap.ReferenceEntry entry;
/* 406:    */     AbstractReferenceMap.ReferenceEntry previous;
/* 407:    */     Object nextKey;
/* 408:    */     Object nextValue;
/* 409:    */     Object currentKey;
/* 410:    */     Object currentValue;
/* 411:    */     int expectedModCount;
/* 412:    */     
/* 413:    */     public ReferenceEntrySetIterator(AbstractReferenceMap parent)
/* 414:    */     {
/* 415:728 */       this.parent = parent;
/* 416:729 */       this.index = (parent.size() != 0 ? parent.data.length : 0);
/* 417:    */       
/* 418:    */ 
/* 419:732 */       this.expectedModCount = parent.modCount;
/* 420:    */     }
/* 421:    */     
/* 422:    */     public boolean hasNext()
/* 423:    */     {
/* 424:736 */       checkMod();
/* 425:737 */       while (nextNull())
/* 426:    */       {
/* 427:738 */         AbstractReferenceMap.ReferenceEntry e = this.entry;
/* 428:739 */         int i = this.index;
/* 429:740 */         while ((e == null) && (i > 0))
/* 430:    */         {
/* 431:741 */           i--;
/* 432:742 */           e = (AbstractReferenceMap.ReferenceEntry)this.parent.data[i];
/* 433:    */         }
/* 434:744 */         this.entry = e;
/* 435:745 */         this.index = i;
/* 436:746 */         if (e == null)
/* 437:    */         {
/* 438:747 */           this.currentKey = null;
/* 439:748 */           this.currentValue = null;
/* 440:749 */           return false;
/* 441:    */         }
/* 442:751 */         this.nextKey = e.getKey();
/* 443:752 */         this.nextValue = e.getValue();
/* 444:753 */         if (nextNull()) {
/* 445:754 */           this.entry = this.entry.next();
/* 446:    */         }
/* 447:    */       }
/* 448:757 */       return true;
/* 449:    */     }
/* 450:    */     
/* 451:    */     private void checkMod()
/* 452:    */     {
/* 453:761 */       if (this.parent.modCount != this.expectedModCount) {
/* 454:762 */         throw new ConcurrentModificationException();
/* 455:    */       }
/* 456:    */     }
/* 457:    */     
/* 458:    */     private boolean nextNull()
/* 459:    */     {
/* 460:767 */       return (this.nextKey == null) || (this.nextValue == null);
/* 461:    */     }
/* 462:    */     
/* 463:    */     protected AbstractReferenceMap.ReferenceEntry nextEntry()
/* 464:    */     {
/* 465:771 */       checkMod();
/* 466:772 */       if ((nextNull()) && (!hasNext())) {
/* 467:773 */         throw new NoSuchElementException();
/* 468:    */       }
/* 469:775 */       this.previous = this.entry;
/* 470:776 */       this.entry = this.entry.next();
/* 471:777 */       this.currentKey = this.nextKey;
/* 472:778 */       this.currentValue = this.nextValue;
/* 473:779 */       this.nextKey = null;
/* 474:780 */       this.nextValue = null;
/* 475:781 */       return this.previous;
/* 476:    */     }
/* 477:    */     
/* 478:    */     protected AbstractReferenceMap.ReferenceEntry currentEntry()
/* 479:    */     {
/* 480:785 */       checkMod();
/* 481:786 */       return this.previous;
/* 482:    */     }
/* 483:    */     
/* 484:    */     public Object next()
/* 485:    */     {
/* 486:790 */       return nextEntry();
/* 487:    */     }
/* 488:    */     
/* 489:    */     public void remove()
/* 490:    */     {
/* 491:794 */       checkMod();
/* 492:795 */       if (this.previous == null) {
/* 493:796 */         throw new IllegalStateException();
/* 494:    */       }
/* 495:798 */       this.parent.remove(this.currentKey);
/* 496:799 */       this.previous = null;
/* 497:800 */       this.currentKey = null;
/* 498:801 */       this.currentValue = null;
/* 499:802 */       this.expectedModCount = this.parent.modCount;
/* 500:    */     }
/* 501:    */   }
/* 502:    */   
/* 503:    */   static class ReferenceKeySetIterator
/* 504:    */     extends AbstractReferenceMap.ReferenceEntrySetIterator
/* 505:    */   {
/* 506:    */     ReferenceKeySetIterator(AbstractReferenceMap parent)
/* 507:    */     {
/* 508:812 */       super();
/* 509:    */     }
/* 510:    */     
/* 511:    */     public Object next()
/* 512:    */     {
/* 513:816 */       return nextEntry().getKey();
/* 514:    */     }
/* 515:    */   }
/* 516:    */   
/* 517:    */   static class ReferenceValuesIterator
/* 518:    */     extends AbstractReferenceMap.ReferenceEntrySetIterator
/* 519:    */   {
/* 520:    */     ReferenceValuesIterator(AbstractReferenceMap parent)
/* 521:    */     {
/* 522:826 */       super();
/* 523:    */     }
/* 524:    */     
/* 525:    */     public Object next()
/* 526:    */     {
/* 527:830 */       return nextEntry().getValue();
/* 528:    */     }
/* 529:    */   }
/* 530:    */   
/* 531:    */   static class ReferenceMapIterator
/* 532:    */     extends AbstractReferenceMap.ReferenceEntrySetIterator
/* 533:    */     implements MapIterator
/* 534:    */   {
/* 535:    */     protected ReferenceMapIterator(AbstractReferenceMap parent)
/* 536:    */     {
/* 537:840 */       super();
/* 538:    */     }
/* 539:    */     
/* 540:    */     public Object next()
/* 541:    */     {
/* 542:844 */       return nextEntry().getKey();
/* 543:    */     }
/* 544:    */     
/* 545:    */     public Object getKey()
/* 546:    */     {
/* 547:848 */       AbstractHashedMap.HashEntry current = currentEntry();
/* 548:849 */       if (current == null) {
/* 549:850 */         throw new IllegalStateException("getKey() can only be called after next() and before remove()");
/* 550:    */       }
/* 551:852 */       return current.getKey();
/* 552:    */     }
/* 553:    */     
/* 554:    */     public Object getValue()
/* 555:    */     {
/* 556:856 */       AbstractHashedMap.HashEntry current = currentEntry();
/* 557:857 */       if (current == null) {
/* 558:858 */         throw new IllegalStateException("getValue() can only be called after next() and before remove()");
/* 559:    */       }
/* 560:860 */       return current.getValue();
/* 561:    */     }
/* 562:    */     
/* 563:    */     public Object setValue(Object value)
/* 564:    */     {
/* 565:864 */       AbstractHashedMap.HashEntry current = currentEntry();
/* 566:865 */       if (current == null) {
/* 567:866 */         throw new IllegalStateException("setValue() can only be called after next() and before remove()");
/* 568:    */       }
/* 569:868 */       return current.setValue(value);
/* 570:    */     }
/* 571:    */   }
/* 572:    */   
/* 573:    */   static class SoftRef
/* 574:    */     extends SoftReference
/* 575:    */   {
/* 576:    */     private int hash;
/* 577:    */     
/* 578:    */     public SoftRef(int hash, Object r, ReferenceQueue q)
/* 579:    */     {
/* 580:885 */       super(q);
/* 581:886 */       this.hash = hash;
/* 582:    */     }
/* 583:    */     
/* 584:    */     public int hashCode()
/* 585:    */     {
/* 586:890 */       return this.hash;
/* 587:    */     }
/* 588:    */   }
/* 589:    */   
/* 590:    */   static class WeakRef
/* 591:    */     extends WeakReference
/* 592:    */   {
/* 593:    */     private int hash;
/* 594:    */     
/* 595:    */     public WeakRef(int hash, Object r, ReferenceQueue q)
/* 596:    */     {
/* 597:902 */       super(q);
/* 598:903 */       this.hash = hash;
/* 599:    */     }
/* 600:    */     
/* 601:    */     public int hashCode()
/* 602:    */     {
/* 603:907 */       return this.hash;
/* 604:    */     }
/* 605:    */   }
/* 606:    */   
/* 607:    */   protected void doWriteObject(ObjectOutputStream out)
/* 608:    */     throws IOException
/* 609:    */   {
/* 610:931 */     out.writeInt(this.keyType);
/* 611:932 */     out.writeInt(this.valueType);
/* 612:933 */     out.writeBoolean(this.purgeValues);
/* 613:934 */     out.writeFloat(this.loadFactor);
/* 614:935 */     out.writeInt(this.data.length);
/* 615:936 */     for (MapIterator it = mapIterator(); it.hasNext();)
/* 616:    */     {
/* 617:937 */       out.writeObject(it.next());
/* 618:938 */       out.writeObject(it.getValue());
/* 619:    */     }
/* 620:940 */     out.writeObject(null);
/* 621:    */   }
/* 622:    */   
/* 623:    */   protected void doReadObject(ObjectInputStream in)
/* 624:    */     throws IOException, ClassNotFoundException
/* 625:    */   {
/* 626:962 */     this.keyType = in.readInt();
/* 627:963 */     this.valueType = in.readInt();
/* 628:964 */     this.purgeValues = in.readBoolean();
/* 629:965 */     this.loadFactor = in.readFloat();
/* 630:966 */     int capacity = in.readInt();
/* 631:967 */     init();
/* 632:968 */     this.data = new AbstractHashedMap.HashEntry[capacity];
/* 633:    */     for (;;)
/* 634:    */     {
/* 635:970 */       Object key = in.readObject();
/* 636:971 */       if (key == null) {
/* 637:    */         break;
/* 638:    */       }
/* 639:974 */       Object value = in.readObject();
/* 640:975 */       put(key, value);
/* 641:    */     }
/* 642:977 */     this.threshold = calculateThreshold(this.data.length, this.loadFactor);
/* 643:    */   }
/* 644:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.map.AbstractReferenceMap
 * JD-Core Version:    0.7.0.1
 */