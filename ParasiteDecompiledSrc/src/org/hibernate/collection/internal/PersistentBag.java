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
/*  11:    */ import org.hibernate.HibernateException;
/*  12:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  13:    */ import org.hibernate.loader.CollectionAliases;
/*  14:    */ import org.hibernate.persister.collection.CollectionPersister;
/*  15:    */ import org.hibernate.type.CollectionType;
/*  16:    */ import org.hibernate.type.Type;
/*  17:    */ 
/*  18:    */ public class PersistentBag
/*  19:    */   extends AbstractPersistentCollection
/*  20:    */   implements List
/*  21:    */ {
/*  22:    */   protected List bag;
/*  23:    */   
/*  24:    */   public PersistentBag(SessionImplementor session)
/*  25:    */   {
/*  26: 54 */     super(session);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public PersistentBag(SessionImplementor session, Collection coll)
/*  30:    */   {
/*  31: 58 */     super(session);
/*  32: 59 */     if ((coll instanceof List))
/*  33:    */     {
/*  34: 60 */       this.bag = ((List)coll);
/*  35:    */     }
/*  36:    */     else
/*  37:    */     {
/*  38: 63 */       this.bag = new ArrayList();
/*  39: 64 */       Iterator iter = coll.iterator();
/*  40: 65 */       while (iter.hasNext()) {
/*  41: 66 */         this.bag.add(iter.next());
/*  42:    */       }
/*  43:    */     }
/*  44: 69 */     setInitialized();
/*  45: 70 */     setDirectlyAccessible(true);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public PersistentBag() {}
/*  49:    */   
/*  50:    */   public boolean isWrapper(Object collection)
/*  51:    */   {
/*  52: 76 */     return this.bag == collection;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public boolean empty()
/*  56:    */   {
/*  57: 79 */     return this.bag.isEmpty();
/*  58:    */   }
/*  59:    */   
/*  60:    */   public Iterator entries(CollectionPersister persister)
/*  61:    */   {
/*  62: 83 */     return this.bag.iterator();
/*  63:    */   }
/*  64:    */   
/*  65:    */   public Object readFrom(ResultSet rs, CollectionPersister persister, CollectionAliases descriptor, Object owner)
/*  66:    */     throws HibernateException, SQLException
/*  67:    */   {
/*  68: 90 */     Object element = persister.readElement(rs, owner, descriptor.getSuffixedElementAliases(), getSession());
/*  69: 91 */     if (element != null) {
/*  70: 91 */       this.bag.add(element);
/*  71:    */     }
/*  72: 92 */     return element;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void beforeInitialize(CollectionPersister persister, int anticipatedSize)
/*  76:    */   {
/*  77: 96 */     this.bag = ((List)persister.getCollectionType().instantiate(anticipatedSize));
/*  78:    */   }
/*  79:    */   
/*  80:    */   public boolean equalsSnapshot(CollectionPersister persister)
/*  81:    */     throws HibernateException
/*  82:    */   {
/*  83:100 */     Type elementType = persister.getElementType();
/*  84:101 */     List sn = (List)getSnapshot();
/*  85:102 */     if (sn.size() != this.bag.size()) {
/*  86:102 */       return false;
/*  87:    */     }
/*  88:103 */     for (Object elt : this.bag)
/*  89:    */     {
/*  90:104 */       boolean unequal = countOccurrences(elt, this.bag, elementType) != countOccurrences(elt, sn, elementType);
/*  91:106 */       if (unequal) {
/*  92:107 */         return false;
/*  93:    */       }
/*  94:    */     }
/*  95:110 */     return true;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public boolean isSnapshotEmpty(Serializable snapshot)
/*  99:    */   {
/* 100:114 */     return ((Collection)snapshot).isEmpty();
/* 101:    */   }
/* 102:    */   
/* 103:    */   private int countOccurrences(Object element, List list, Type elementType)
/* 104:    */     throws HibernateException
/* 105:    */   {
/* 106:119 */     Iterator iter = list.iterator();
/* 107:120 */     int result = 0;
/* 108:121 */     while (iter.hasNext()) {
/* 109:122 */       if (elementType.isSame(element, iter.next())) {
/* 110:122 */         result++;
/* 111:    */       }
/* 112:    */     }
/* 113:124 */     return result;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public Serializable getSnapshot(CollectionPersister persister)
/* 117:    */     throws HibernateException
/* 118:    */   {
/* 119:129 */     ArrayList clonedList = new ArrayList(this.bag.size());
/* 120:130 */     Iterator iter = this.bag.iterator();
/* 121:131 */     while (iter.hasNext()) {
/* 122:132 */       clonedList.add(persister.getElementType().deepCopy(iter.next(), persister.getFactory()));
/* 123:    */     }
/* 124:134 */     return clonedList;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public Collection getOrphans(Serializable snapshot, String entityName)
/* 128:    */     throws HibernateException
/* 129:    */   {
/* 130:138 */     List sn = (List)snapshot;
/* 131:139 */     return getOrphans(sn, this.bag, entityName, getSession());
/* 132:    */   }
/* 133:    */   
/* 134:    */   public Serializable disassemble(CollectionPersister persister)
/* 135:    */     throws HibernateException
/* 136:    */   {
/* 137:146 */     int length = this.bag.size();
/* 138:147 */     Serializable[] result = new Serializable[length];
/* 139:148 */     for (int i = 0; i < length; i++) {
/* 140:149 */       result[i] = persister.getElementType().disassemble(this.bag.get(i), getSession(), null);
/* 141:    */     }
/* 142:151 */     return result;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void initializeFromCache(CollectionPersister persister, Serializable disassembled, Object owner)
/* 146:    */     throws HibernateException
/* 147:    */   {
/* 148:156 */     Serializable[] array = (Serializable[])disassembled;
/* 149:157 */     int size = array.length;
/* 150:158 */     beforeInitialize(persister, size);
/* 151:159 */     for (int i = 0; i < size; i++)
/* 152:    */     {
/* 153:160 */       Object element = persister.getElementType().assemble(array[i], getSession(), owner);
/* 154:161 */       if (element != null) {
/* 155:162 */         this.bag.add(element);
/* 156:    */       }
/* 157:    */     }
/* 158:    */   }
/* 159:    */   
/* 160:    */   public boolean needsRecreate(CollectionPersister persister)
/* 161:    */   {
/* 162:168 */     return !persister.isOneToMany();
/* 163:    */   }
/* 164:    */   
/* 165:    */   public Iterator getDeletes(CollectionPersister persister, boolean indexIsFormula)
/* 166:    */     throws HibernateException
/* 167:    */   {
/* 168:182 */     Type elementType = persister.getElementType();
/* 169:183 */     ArrayList deletes = new ArrayList();
/* 170:184 */     List sn = (List)getSnapshot();
/* 171:185 */     Iterator olditer = sn.iterator();
/* 172:186 */     int i = 0;
/* 173:187 */     while (olditer.hasNext())
/* 174:    */     {
/* 175:188 */       Object old = olditer.next();
/* 176:189 */       Iterator newiter = this.bag.iterator();
/* 177:190 */       boolean found = false;
/* 178:191 */       if ((this.bag.size() > i) && (elementType.isSame(old, this.bag.get(i++)))) {
/* 179:193 */         found = true;
/* 180:    */       } else {
/* 181:198 */         while (newiter.hasNext()) {
/* 182:199 */           if (elementType.isSame(old, newiter.next())) {
/* 183:200 */             found = true;
/* 184:    */           }
/* 185:    */         }
/* 186:    */       }
/* 187:205 */       if (!found) {
/* 188:205 */         deletes.add(old);
/* 189:    */       }
/* 190:    */     }
/* 191:207 */     return deletes.iterator();
/* 192:    */   }
/* 193:    */   
/* 194:    */   public boolean needsInserting(Object entry, int i, Type elemType)
/* 195:    */     throws HibernateException
/* 196:    */   {
/* 197:212 */     List sn = (List)getSnapshot();
/* 198:213 */     if ((sn.size() > i) && (elemType.isSame(sn.get(i), entry))) {
/* 199:215 */       return false;
/* 200:    */     }
/* 201:220 */     Iterator olditer = sn.iterator();
/* 202:221 */     while (olditer.hasNext())
/* 203:    */     {
/* 204:222 */       Object old = olditer.next();
/* 205:223 */       if (elemType.isSame(old, entry)) {
/* 206:223 */         return false;
/* 207:    */       }
/* 208:    */     }
/* 209:225 */     return true;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public boolean isRowUpdatePossible()
/* 213:    */   {
/* 214:230 */     return false;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public boolean needsUpdating(Object entry, int i, Type elemType)
/* 218:    */   {
/* 219:235 */     return false;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public int size()
/* 223:    */   {
/* 224:242 */     return readSize() ? getCachedSize() : this.bag.size();
/* 225:    */   }
/* 226:    */   
/* 227:    */   public boolean isEmpty()
/* 228:    */   {
/* 229:249 */     return readSize() ? false : getCachedSize() == 0 ? true : this.bag.isEmpty();
/* 230:    */   }
/* 231:    */   
/* 232:    */   public boolean contains(Object object)
/* 233:    */   {
/* 234:256 */     Boolean exists = readElementExistence(object);
/* 235:257 */     return exists == null ? this.bag.contains(object) : exists.booleanValue();
/* 236:    */   }
/* 237:    */   
/* 238:    */   public Iterator iterator()
/* 239:    */   {
/* 240:266 */     read();
/* 241:267 */     return new AbstractPersistentCollection.IteratorProxy(this, this.bag.iterator());
/* 242:    */   }
/* 243:    */   
/* 244:    */   public Object[] toArray()
/* 245:    */   {
/* 246:274 */     read();
/* 247:275 */     return this.bag.toArray();
/* 248:    */   }
/* 249:    */   
/* 250:    */   public Object[] toArray(Object[] a)
/* 251:    */   {
/* 252:282 */     read();
/* 253:283 */     return this.bag.toArray(a);
/* 254:    */   }
/* 255:    */   
/* 256:    */   public boolean add(Object object)
/* 257:    */   {
/* 258:290 */     if (!isOperationQueueEnabled())
/* 259:    */     {
/* 260:291 */       write();
/* 261:292 */       return this.bag.add(object);
/* 262:    */     }
/* 263:295 */     queueOperation(new SimpleAdd(object));
/* 264:296 */     return true;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public boolean remove(Object o)
/* 268:    */   {
/* 269:304 */     initialize(true);
/* 270:305 */     if (this.bag.remove(o))
/* 271:    */     {
/* 272:306 */       dirty();
/* 273:307 */       return true;
/* 274:    */     }
/* 275:310 */     return false;
/* 276:    */   }
/* 277:    */   
/* 278:    */   public boolean containsAll(Collection c)
/* 279:    */   {
/* 280:318 */     read();
/* 281:319 */     return this.bag.containsAll(c);
/* 282:    */   }
/* 283:    */   
/* 284:    */   public boolean addAll(Collection values)
/* 285:    */   {
/* 286:326 */     if (values.size() == 0) {
/* 287:326 */       return false;
/* 288:    */     }
/* 289:327 */     if (!isOperationQueueEnabled())
/* 290:    */     {
/* 291:328 */       write();
/* 292:329 */       return this.bag.addAll(values);
/* 293:    */     }
/* 294:332 */     Iterator iter = values.iterator();
/* 295:333 */     while (iter.hasNext()) {
/* 296:334 */       queueOperation(new SimpleAdd(iter.next()));
/* 297:    */     }
/* 298:336 */     return values.size() > 0;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public boolean removeAll(Collection c)
/* 302:    */   {
/* 303:344 */     if (c.size() > 0)
/* 304:    */     {
/* 305:345 */       initialize(true);
/* 306:346 */       if (this.bag.removeAll(c))
/* 307:    */       {
/* 308:347 */         dirty();
/* 309:348 */         return true;
/* 310:    */       }
/* 311:351 */       return false;
/* 312:    */     }
/* 313:355 */     return false;
/* 314:    */   }
/* 315:    */   
/* 316:    */   public boolean retainAll(Collection c)
/* 317:    */   {
/* 318:363 */     initialize(true);
/* 319:364 */     if (this.bag.retainAll(c))
/* 320:    */     {
/* 321:365 */       dirty();
/* 322:366 */       return true;
/* 323:    */     }
/* 324:369 */     return false;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public void clear()
/* 328:    */   {
/* 329:377 */     if (isClearQueueEnabled())
/* 330:    */     {
/* 331:378 */       queueOperation(new Clear());
/* 332:    */     }
/* 333:    */     else
/* 334:    */     {
/* 335:381 */       initialize(true);
/* 336:382 */       if (!this.bag.isEmpty())
/* 337:    */       {
/* 338:383 */         this.bag.clear();
/* 339:384 */         dirty();
/* 340:    */       }
/* 341:    */     }
/* 342:    */   }
/* 343:    */   
/* 344:    */   public Object getIndex(Object entry, int i, CollectionPersister persister)
/* 345:    */   {
/* 346:390 */     throw new UnsupportedOperationException("Bags don't have indexes");
/* 347:    */   }
/* 348:    */   
/* 349:    */   public Object getElement(Object entry)
/* 350:    */   {
/* 351:394 */     return entry;
/* 352:    */   }
/* 353:    */   
/* 354:    */   public Object getSnapshotElement(Object entry, int i)
/* 355:    */   {
/* 356:398 */     List sn = (List)getSnapshot();
/* 357:399 */     return sn.get(i);
/* 358:    */   }
/* 359:    */   
/* 360:    */   public int occurrences(Object o)
/* 361:    */   {
/* 362:403 */     read();
/* 363:404 */     Iterator iter = this.bag.iterator();
/* 364:405 */     int result = 0;
/* 365:406 */     while (iter.hasNext()) {
/* 366:407 */       if (o.equals(iter.next())) {
/* 367:407 */         result++;
/* 368:    */       }
/* 369:    */     }
/* 370:409 */     return result;
/* 371:    */   }
/* 372:    */   
/* 373:    */   public void add(int i, Object o)
/* 374:    */   {
/* 375:418 */     write();
/* 376:419 */     this.bag.add(i, o);
/* 377:    */   }
/* 378:    */   
/* 379:    */   public boolean addAll(int i, Collection c)
/* 380:    */   {
/* 381:426 */     if (c.size() > 0)
/* 382:    */     {
/* 383:427 */       write();
/* 384:428 */       return this.bag.addAll(i, c);
/* 385:    */     }
/* 386:431 */     return false;
/* 387:    */   }
/* 388:    */   
/* 389:    */   public Object get(int i)
/* 390:    */   {
/* 391:439 */     read();
/* 392:440 */     return this.bag.get(i);
/* 393:    */   }
/* 394:    */   
/* 395:    */   public int indexOf(Object o)
/* 396:    */   {
/* 397:447 */     read();
/* 398:448 */     return this.bag.indexOf(o);
/* 399:    */   }
/* 400:    */   
/* 401:    */   public int lastIndexOf(Object o)
/* 402:    */   {
/* 403:455 */     read();
/* 404:456 */     return this.bag.lastIndexOf(o);
/* 405:    */   }
/* 406:    */   
/* 407:    */   public ListIterator listIterator()
/* 408:    */   {
/* 409:463 */     read();
/* 410:464 */     return new AbstractPersistentCollection.ListIteratorProxy(this, this.bag.listIterator());
/* 411:    */   }
/* 412:    */   
/* 413:    */   public ListIterator listIterator(int i)
/* 414:    */   {
/* 415:471 */     read();
/* 416:472 */     return new AbstractPersistentCollection.ListIteratorProxy(this, this.bag.listIterator(i));
/* 417:    */   }
/* 418:    */   
/* 419:    */   public Object remove(int i)
/* 420:    */   {
/* 421:479 */     write();
/* 422:480 */     return this.bag.remove(i);
/* 423:    */   }
/* 424:    */   
/* 425:    */   public Object set(int i, Object o)
/* 426:    */   {
/* 427:487 */     write();
/* 428:488 */     return this.bag.set(i, o);
/* 429:    */   }
/* 430:    */   
/* 431:    */   public List subList(int start, int end)
/* 432:    */   {
/* 433:495 */     read();
/* 434:496 */     return new AbstractPersistentCollection.ListProxy(this, this.bag.subList(start, end));
/* 435:    */   }
/* 436:    */   
/* 437:    */   public String toString()
/* 438:    */   {
/* 439:500 */     read();
/* 440:501 */     return this.bag.toString();
/* 441:    */   }
/* 442:    */   
/* 443:    */   public boolean entryExists(Object entry, int i)
/* 444:    */   {
/* 445:515 */     return entry != null;
/* 446:    */   }
/* 447:    */   
/* 448:    */   public boolean equals(Object obj)
/* 449:    */   {
/* 450:526 */     return super.equals(obj);
/* 451:    */   }
/* 452:    */   
/* 453:    */   public int hashCode()
/* 454:    */   {
/* 455:533 */     return super.hashCode();
/* 456:    */   }
/* 457:    */   
/* 458:    */   final class Clear
/* 459:    */     implements AbstractPersistentCollection.DelayedOperation
/* 460:    */   {
/* 461:    */     Clear() {}
/* 462:    */     
/* 463:    */     public void operate()
/* 464:    */     {
/* 465:538 */       PersistentBag.this.bag.clear();
/* 466:    */     }
/* 467:    */     
/* 468:    */     public Object getAddedInstance()
/* 469:    */     {
/* 470:541 */       return null;
/* 471:    */     }
/* 472:    */     
/* 473:    */     public Object getOrphan()
/* 474:    */     {
/* 475:544 */       throw new UnsupportedOperationException("queued clear cannot be used with orphan delete");
/* 476:    */     }
/* 477:    */   }
/* 478:    */   
/* 479:    */   final class SimpleAdd
/* 480:    */     implements AbstractPersistentCollection.DelayedOperation
/* 481:    */   {
/* 482:    */     private Object value;
/* 483:    */     
/* 484:    */     public SimpleAdd(Object value)
/* 485:    */     {
/* 486:552 */       this.value = value;
/* 487:    */     }
/* 488:    */     
/* 489:    */     public void operate()
/* 490:    */     {
/* 491:555 */       PersistentBag.this.bag.add(this.value);
/* 492:    */     }
/* 493:    */     
/* 494:    */     public Object getAddedInstance()
/* 495:    */     {
/* 496:558 */       return this.value;
/* 497:    */     }
/* 498:    */     
/* 499:    */     public Object getOrphan()
/* 500:    */     {
/* 501:561 */       return null;
/* 502:    */     }
/* 503:    */   }
/* 504:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.collection.internal.PersistentBag
 * JD-Core Version:    0.7.0.1
 */