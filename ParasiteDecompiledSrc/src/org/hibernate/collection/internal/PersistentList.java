/*   1:    */ package org.hibernate.collection.internal;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.sql.ResultSet;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.Collection;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.ListIterator;
/*  11:    */ import org.hibernate.EntityMode;
/*  12:    */ import org.hibernate.HibernateException;
/*  13:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  14:    */ import org.hibernate.loader.CollectionAliases;
/*  15:    */ import org.hibernate.persister.collection.CollectionPersister;
/*  16:    */ import org.hibernate.persister.entity.EntityPersister;
/*  17:    */ import org.hibernate.type.CollectionType;
/*  18:    */ import org.hibernate.type.Type;
/*  19:    */ 
/*  20:    */ public class PersistentList
/*  21:    */   extends AbstractPersistentCollection
/*  22:    */   implements List
/*  23:    */ {
/*  24:    */   protected List list;
/*  25:    */   
/*  26:    */   public Serializable getSnapshot(CollectionPersister persister)
/*  27:    */     throws HibernateException
/*  28:    */   {
/*  29: 55 */     EntityMode entityMode = persister.getOwnerEntityPersister().getEntityMode();
/*  30:    */     
/*  31: 57 */     ArrayList clonedList = new ArrayList(this.list.size());
/*  32: 58 */     for (Object element : this.list)
/*  33:    */     {
/*  34: 59 */       Object deepCopy = persister.getElementType().deepCopy(element, persister.getFactory());
/*  35: 60 */       clonedList.add(deepCopy);
/*  36:    */     }
/*  37: 62 */     return clonedList;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public Collection getOrphans(Serializable snapshot, String entityName)
/*  41:    */     throws HibernateException
/*  42:    */   {
/*  43: 67 */     List sn = (List)snapshot;
/*  44: 68 */     return getOrphans(sn, this.list, entityName, getSession());
/*  45:    */   }
/*  46:    */   
/*  47:    */   public boolean equalsSnapshot(CollectionPersister persister)
/*  48:    */     throws HibernateException
/*  49:    */   {
/*  50: 73 */     Type elementType = persister.getElementType();
/*  51: 74 */     List sn = (List)getSnapshot();
/*  52: 75 */     if (sn.size() != this.list.size()) {
/*  53: 75 */       return false;
/*  54:    */     }
/*  55: 76 */     Iterator iter = this.list.iterator();
/*  56: 77 */     Iterator sniter = sn.iterator();
/*  57: 78 */     while (iter.hasNext()) {
/*  58: 79 */       if (elementType.isDirty(iter.next(), sniter.next(), getSession())) {
/*  59: 79 */         return false;
/*  60:    */       }
/*  61:    */     }
/*  62: 81 */     return true;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public boolean isSnapshotEmpty(Serializable snapshot)
/*  66:    */   {
/*  67: 86 */     return ((Collection)snapshot).isEmpty();
/*  68:    */   }
/*  69:    */   
/*  70:    */   public PersistentList(SessionImplementor session)
/*  71:    */   {
/*  72: 90 */     super(session);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public PersistentList(SessionImplementor session, List list)
/*  76:    */   {
/*  77: 94 */     super(session);
/*  78: 95 */     this.list = list;
/*  79: 96 */     setInitialized();
/*  80: 97 */     setDirectlyAccessible(true);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void beforeInitialize(CollectionPersister persister, int anticipatedSize)
/*  84:    */   {
/*  85:101 */     this.list = ((List)persister.getCollectionType().instantiate(anticipatedSize));
/*  86:    */   }
/*  87:    */   
/*  88:    */   public boolean isWrapper(Object collection)
/*  89:    */   {
/*  90:105 */     return this.list == collection;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public PersistentList() {}
/*  94:    */   
/*  95:    */   public int size()
/*  96:    */   {
/*  97:114 */     return readSize() ? getCachedSize() : this.list.size();
/*  98:    */   }
/*  99:    */   
/* 100:    */   public boolean isEmpty()
/* 101:    */   {
/* 102:121 */     return readSize() ? false : getCachedSize() == 0 ? true : this.list.isEmpty();
/* 103:    */   }
/* 104:    */   
/* 105:    */   public boolean contains(Object object)
/* 106:    */   {
/* 107:128 */     Boolean exists = readElementExistence(object);
/* 108:129 */     return exists == null ? this.list.contains(object) : exists.booleanValue();
/* 109:    */   }
/* 110:    */   
/* 111:    */   public Iterator iterator()
/* 112:    */   {
/* 113:138 */     read();
/* 114:139 */     return new AbstractPersistentCollection.IteratorProxy(this, this.list.iterator());
/* 115:    */   }
/* 116:    */   
/* 117:    */   public Object[] toArray()
/* 118:    */   {
/* 119:146 */     read();
/* 120:147 */     return this.list.toArray();
/* 121:    */   }
/* 122:    */   
/* 123:    */   public Object[] toArray(Object[] array)
/* 124:    */   {
/* 125:154 */     read();
/* 126:155 */     return this.list.toArray(array);
/* 127:    */   }
/* 128:    */   
/* 129:    */   public boolean add(Object object)
/* 130:    */   {
/* 131:162 */     if (!isOperationQueueEnabled())
/* 132:    */     {
/* 133:163 */       write();
/* 134:164 */       return this.list.add(object);
/* 135:    */     }
/* 136:167 */     queueOperation(new SimpleAdd(object));
/* 137:168 */     return true;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public boolean remove(Object value)
/* 141:    */   {
/* 142:176 */     Boolean exists = isPutQueueEnabled() ? readElementExistence(value) : null;
/* 143:177 */     if (exists == null)
/* 144:    */     {
/* 145:178 */       initialize(true);
/* 146:179 */       if (this.list.remove(value))
/* 147:    */       {
/* 148:180 */         dirty();
/* 149:181 */         return true;
/* 150:    */       }
/* 151:184 */       return false;
/* 152:    */     }
/* 153:187 */     if (exists.booleanValue())
/* 154:    */     {
/* 155:188 */       queueOperation(new SimpleRemove(value));
/* 156:189 */       return true;
/* 157:    */     }
/* 158:192 */     return false;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public boolean containsAll(Collection coll)
/* 162:    */   {
/* 163:200 */     read();
/* 164:201 */     return this.list.containsAll(coll);
/* 165:    */   }
/* 166:    */   
/* 167:    */   public boolean addAll(Collection values)
/* 168:    */   {
/* 169:208 */     if (values.size() == 0) {
/* 170:209 */       return false;
/* 171:    */     }
/* 172:211 */     if (!isOperationQueueEnabled())
/* 173:    */     {
/* 174:212 */       write();
/* 175:213 */       return this.list.addAll(values);
/* 176:    */     }
/* 177:216 */     Iterator iter = values.iterator();
/* 178:217 */     while (iter.hasNext()) {
/* 179:218 */       queueOperation(new SimpleAdd(iter.next()));
/* 180:    */     }
/* 181:220 */     return values.size() > 0;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public boolean addAll(int index, Collection coll)
/* 185:    */   {
/* 186:228 */     if (coll.size() > 0)
/* 187:    */     {
/* 188:229 */       write();
/* 189:230 */       return this.list.addAll(index, coll);
/* 190:    */     }
/* 191:233 */     return false;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public boolean removeAll(Collection coll)
/* 195:    */   {
/* 196:241 */     if (coll.size() > 0)
/* 197:    */     {
/* 198:242 */       initialize(true);
/* 199:243 */       if (this.list.removeAll(coll))
/* 200:    */       {
/* 201:244 */         dirty();
/* 202:245 */         return true;
/* 203:    */       }
/* 204:248 */       return false;
/* 205:    */     }
/* 206:252 */     return false;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public boolean retainAll(Collection coll)
/* 210:    */   {
/* 211:260 */     initialize(true);
/* 212:261 */     if (this.list.retainAll(coll))
/* 213:    */     {
/* 214:262 */       dirty();
/* 215:263 */       return true;
/* 216:    */     }
/* 217:266 */     return false;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void clear()
/* 221:    */   {
/* 222:274 */     if (isClearQueueEnabled())
/* 223:    */     {
/* 224:275 */       queueOperation(new Clear());
/* 225:    */     }
/* 226:    */     else
/* 227:    */     {
/* 228:278 */       initialize(true);
/* 229:279 */       if (!this.list.isEmpty())
/* 230:    */       {
/* 231:280 */         this.list.clear();
/* 232:281 */         dirty();
/* 233:    */       }
/* 234:    */     }
/* 235:    */   }
/* 236:    */   
/* 237:    */   public Object get(int index)
/* 238:    */   {
/* 239:290 */     if (index < 0) {
/* 240:291 */       throw new ArrayIndexOutOfBoundsException("negative index");
/* 241:    */     }
/* 242:293 */     Object result = readElementByIndex(Integer.valueOf(index));
/* 243:294 */     return result == UNKNOWN ? this.list.get(index) : result;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public Object set(int index, Object value)
/* 247:    */   {
/* 248:301 */     if (index < 0) {
/* 249:302 */       throw new ArrayIndexOutOfBoundsException("negative index");
/* 250:    */     }
/* 251:304 */     Object old = isPutQueueEnabled() ? readElementByIndex(Integer.valueOf(index)) : UNKNOWN;
/* 252:305 */     if (old == UNKNOWN)
/* 253:    */     {
/* 254:306 */       write();
/* 255:307 */       return this.list.set(index, value);
/* 256:    */     }
/* 257:310 */     queueOperation(new Set(index, value, old));
/* 258:311 */     return old;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public void add(int index, Object value)
/* 262:    */   {
/* 263:319 */     if (index < 0) {
/* 264:320 */       throw new ArrayIndexOutOfBoundsException("negative index");
/* 265:    */     }
/* 266:322 */     if (!isOperationQueueEnabled())
/* 267:    */     {
/* 268:323 */       write();
/* 269:324 */       this.list.add(index, value);
/* 270:    */     }
/* 271:    */     else
/* 272:    */     {
/* 273:327 */       queueOperation(new Add(index, value));
/* 274:    */     }
/* 275:    */   }
/* 276:    */   
/* 277:    */   public Object remove(int index)
/* 278:    */   {
/* 279:335 */     if (index < 0) {
/* 280:336 */       throw new ArrayIndexOutOfBoundsException("negative index");
/* 281:    */     }
/* 282:338 */     Object old = isPutQueueEnabled() ? readElementByIndex(Integer.valueOf(index)) : UNKNOWN;
/* 283:340 */     if (old == UNKNOWN)
/* 284:    */     {
/* 285:341 */       write();
/* 286:342 */       return this.list.remove(index);
/* 287:    */     }
/* 288:345 */     queueOperation(new Remove(index, old));
/* 289:346 */     return old;
/* 290:    */   }
/* 291:    */   
/* 292:    */   public int indexOf(Object value)
/* 293:    */   {
/* 294:354 */     read();
/* 295:355 */     return this.list.indexOf(value);
/* 296:    */   }
/* 297:    */   
/* 298:    */   public int lastIndexOf(Object value)
/* 299:    */   {
/* 300:362 */     read();
/* 301:363 */     return this.list.lastIndexOf(value);
/* 302:    */   }
/* 303:    */   
/* 304:    */   public ListIterator listIterator()
/* 305:    */   {
/* 306:370 */     read();
/* 307:371 */     return new AbstractPersistentCollection.ListIteratorProxy(this, this.list.listIterator());
/* 308:    */   }
/* 309:    */   
/* 310:    */   public ListIterator listIterator(int index)
/* 311:    */   {
/* 312:378 */     read();
/* 313:379 */     return new AbstractPersistentCollection.ListIteratorProxy(this, this.list.listIterator(index));
/* 314:    */   }
/* 315:    */   
/* 316:    */   public List subList(int from, int to)
/* 317:    */   {
/* 318:386 */     read();
/* 319:387 */     return new AbstractPersistentCollection.ListProxy(this, this.list.subList(from, to));
/* 320:    */   }
/* 321:    */   
/* 322:    */   public boolean empty()
/* 323:    */   {
/* 324:391 */     return this.list.isEmpty();
/* 325:    */   }
/* 326:    */   
/* 327:    */   public String toString()
/* 328:    */   {
/* 329:395 */     read();
/* 330:396 */     return this.list.toString();
/* 331:    */   }
/* 332:    */   
/* 333:    */   public Object readFrom(ResultSet rs, CollectionPersister persister, CollectionAliases descriptor, Object owner)
/* 334:    */     throws HibernateException, SQLException
/* 335:    */   {
/* 336:401 */     Object element = persister.readElement(rs, owner, descriptor.getSuffixedElementAliases(), getSession());
/* 337:402 */     int index = ((Integer)persister.readIndex(rs, descriptor.getSuffixedIndexAliases(), getSession())).intValue();
/* 338:405 */     for (int i = this.list.size(); i <= index; i++) {
/* 339:406 */       this.list.add(i, null);
/* 340:    */     }
/* 341:409 */     this.list.set(index, element);
/* 342:410 */     return element;
/* 343:    */   }
/* 344:    */   
/* 345:    */   public Iterator entries(CollectionPersister persister)
/* 346:    */   {
/* 347:414 */     return this.list.iterator();
/* 348:    */   }
/* 349:    */   
/* 350:    */   public void initializeFromCache(CollectionPersister persister, Serializable disassembled, Object owner)
/* 351:    */     throws HibernateException
/* 352:    */   {
/* 353:419 */     Serializable[] array = (Serializable[])disassembled;
/* 354:420 */     int size = array.length;
/* 355:421 */     beforeInitialize(persister, size);
/* 356:422 */     for (int i = 0; i < size; i++) {
/* 357:423 */       this.list.add(persister.getElementType().assemble(array[i], getSession(), owner));
/* 358:    */     }
/* 359:    */   }
/* 360:    */   
/* 361:    */   public Serializable disassemble(CollectionPersister persister)
/* 362:    */     throws HibernateException
/* 363:    */   {
/* 364:430 */     int length = this.list.size();
/* 365:431 */     Serializable[] result = new Serializable[length];
/* 366:432 */     for (int i = 0; i < length; i++) {
/* 367:433 */       result[i] = persister.getElementType().disassemble(this.list.get(i), getSession(), null);
/* 368:    */     }
/* 369:435 */     return result;
/* 370:    */   }
/* 371:    */   
/* 372:    */   public Iterator getDeletes(CollectionPersister persister, boolean indexIsFormula)
/* 373:    */     throws HibernateException
/* 374:    */   {
/* 375:440 */     List deletes = new ArrayList();
/* 376:441 */     List sn = (List)getSnapshot();
/* 377:    */     int end;
/* 378:    */     int end;
/* 379:443 */     if (sn.size() > this.list.size())
/* 380:    */     {
/* 381:444 */       for (int i = this.list.size(); i < sn.size(); i++) {
/* 382:445 */         deletes.add(indexIsFormula ? sn.get(i) : Integer.valueOf(i));
/* 383:    */       }
/* 384:447 */       end = this.list.size();
/* 385:    */     }
/* 386:    */     else
/* 387:    */     {
/* 388:450 */       end = sn.size();
/* 389:    */     }
/* 390:452 */     for (int i = 0; i < end; i++) {
/* 391:453 */       if ((this.list.get(i) == null) && (sn.get(i) != null)) {
/* 392:454 */         deletes.add(indexIsFormula ? sn.get(i) : Integer.valueOf(i));
/* 393:    */       }
/* 394:    */     }
/* 395:457 */     return deletes.iterator();
/* 396:    */   }
/* 397:    */   
/* 398:    */   public boolean needsInserting(Object entry, int i, Type elemType)
/* 399:    */     throws HibernateException
/* 400:    */   {
/* 401:461 */     List sn = (List)getSnapshot();
/* 402:462 */     return (this.list.get(i) != null) && ((i >= sn.size()) || (sn.get(i) == null));
/* 403:    */   }
/* 404:    */   
/* 405:    */   public boolean needsUpdating(Object entry, int i, Type elemType)
/* 406:    */     throws HibernateException
/* 407:    */   {
/* 408:466 */     List sn = (List)getSnapshot();
/* 409:467 */     return (i < sn.size()) && (sn.get(i) != null) && (this.list.get(i) != null) && (elemType.isDirty(this.list.get(i), sn.get(i), getSession()));
/* 410:    */   }
/* 411:    */   
/* 412:    */   public Object getIndex(Object entry, int i, CollectionPersister persister)
/* 413:    */   {
/* 414:472 */     return Integer.valueOf(i);
/* 415:    */   }
/* 416:    */   
/* 417:    */   public Object getElement(Object entry)
/* 418:    */   {
/* 419:476 */     return entry;
/* 420:    */   }
/* 421:    */   
/* 422:    */   public Object getSnapshotElement(Object entry, int i)
/* 423:    */   {
/* 424:480 */     List sn = (List)getSnapshot();
/* 425:481 */     return sn.get(i);
/* 426:    */   }
/* 427:    */   
/* 428:    */   public boolean equals(Object other)
/* 429:    */   {
/* 430:485 */     read();
/* 431:486 */     return this.list.equals(other);
/* 432:    */   }
/* 433:    */   
/* 434:    */   public int hashCode()
/* 435:    */   {
/* 436:490 */     read();
/* 437:491 */     return this.list.hashCode();
/* 438:    */   }
/* 439:    */   
/* 440:    */   public boolean entryExists(Object entry, int i)
/* 441:    */   {
/* 442:495 */     return entry != null;
/* 443:    */   }
/* 444:    */   
/* 445:    */   final class Clear
/* 446:    */     implements AbstractPersistentCollection.DelayedOperation
/* 447:    */   {
/* 448:    */     Clear() {}
/* 449:    */     
/* 450:    */     public void operate()
/* 451:    */     {
/* 452:500 */       PersistentList.this.list.clear();
/* 453:    */     }
/* 454:    */     
/* 455:    */     public Object getAddedInstance()
/* 456:    */     {
/* 457:503 */       return null;
/* 458:    */     }
/* 459:    */     
/* 460:    */     public Object getOrphan()
/* 461:    */     {
/* 462:506 */       throw new UnsupportedOperationException("queued clear cannot be used with orphan delete");
/* 463:    */     }
/* 464:    */   }
/* 465:    */   
/* 466:    */   final class SimpleAdd
/* 467:    */     implements AbstractPersistentCollection.DelayedOperation
/* 468:    */   {
/* 469:    */     private Object value;
/* 470:    */     
/* 471:    */     public SimpleAdd(Object value)
/* 472:    */     {
/* 473:514 */       this.value = value;
/* 474:    */     }
/* 475:    */     
/* 476:    */     public void operate()
/* 477:    */     {
/* 478:517 */       PersistentList.this.list.add(this.value);
/* 479:    */     }
/* 480:    */     
/* 481:    */     public Object getAddedInstance()
/* 482:    */     {
/* 483:520 */       return this.value;
/* 484:    */     }
/* 485:    */     
/* 486:    */     public Object getOrphan()
/* 487:    */     {
/* 488:523 */       return null;
/* 489:    */     }
/* 490:    */   }
/* 491:    */   
/* 492:    */   final class Add
/* 493:    */     implements AbstractPersistentCollection.DelayedOperation
/* 494:    */   {
/* 495:    */     private int index;
/* 496:    */     private Object value;
/* 497:    */     
/* 498:    */     public Add(int index, Object value)
/* 499:    */     {
/* 500:532 */       this.index = index;
/* 501:533 */       this.value = value;
/* 502:    */     }
/* 503:    */     
/* 504:    */     public void operate()
/* 505:    */     {
/* 506:536 */       PersistentList.this.list.add(this.index, this.value);
/* 507:    */     }
/* 508:    */     
/* 509:    */     public Object getAddedInstance()
/* 510:    */     {
/* 511:539 */       return this.value;
/* 512:    */     }
/* 513:    */     
/* 514:    */     public Object getOrphan()
/* 515:    */     {
/* 516:542 */       return null;
/* 517:    */     }
/* 518:    */   }
/* 519:    */   
/* 520:    */   final class Set
/* 521:    */     implements AbstractPersistentCollection.DelayedOperation
/* 522:    */   {
/* 523:    */     private int index;
/* 524:    */     private Object value;
/* 525:    */     private Object old;
/* 526:    */     
/* 527:    */     public Set(int index, Object value, Object old)
/* 528:    */     {
/* 529:552 */       this.index = index;
/* 530:553 */       this.value = value;
/* 531:554 */       this.old = old;
/* 532:    */     }
/* 533:    */     
/* 534:    */     public void operate()
/* 535:    */     {
/* 536:557 */       PersistentList.this.list.set(this.index, this.value);
/* 537:    */     }
/* 538:    */     
/* 539:    */     public Object getAddedInstance()
/* 540:    */     {
/* 541:560 */       return this.value;
/* 542:    */     }
/* 543:    */     
/* 544:    */     public Object getOrphan()
/* 545:    */     {
/* 546:563 */       return this.old;
/* 547:    */     }
/* 548:    */   }
/* 549:    */   
/* 550:    */   final class Remove
/* 551:    */     implements AbstractPersistentCollection.DelayedOperation
/* 552:    */   {
/* 553:    */     private int index;
/* 554:    */     private Object old;
/* 555:    */     
/* 556:    */     public Remove(int index, Object old)
/* 557:    */     {
/* 558:572 */       this.index = index;
/* 559:573 */       this.old = old;
/* 560:    */     }
/* 561:    */     
/* 562:    */     public void operate()
/* 563:    */     {
/* 564:576 */       PersistentList.this.list.remove(this.index);
/* 565:    */     }
/* 566:    */     
/* 567:    */     public Object getAddedInstance()
/* 568:    */     {
/* 569:579 */       return null;
/* 570:    */     }
/* 571:    */     
/* 572:    */     public Object getOrphan()
/* 573:    */     {
/* 574:582 */       return this.old;
/* 575:    */     }
/* 576:    */   }
/* 577:    */   
/* 578:    */   final class SimpleRemove
/* 579:    */     implements AbstractPersistentCollection.DelayedOperation
/* 580:    */   {
/* 581:    */     private Object value;
/* 582:    */     
/* 583:    */     public SimpleRemove(Object value)
/* 584:    */     {
/* 585:590 */       this.value = value;
/* 586:    */     }
/* 587:    */     
/* 588:    */     public void operate()
/* 589:    */     {
/* 590:593 */       PersistentList.this.list.remove(this.value);
/* 591:    */     }
/* 592:    */     
/* 593:    */     public Object getAddedInstance()
/* 594:    */     {
/* 595:596 */       return null;
/* 596:    */     }
/* 597:    */     
/* 598:    */     public Object getOrphan()
/* 599:    */     {
/* 600:599 */       return this.value;
/* 601:    */     }
/* 602:    */   }
/* 603:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.collection.internal.PersistentList
 * JD-Core Version:    0.7.0.1
 */