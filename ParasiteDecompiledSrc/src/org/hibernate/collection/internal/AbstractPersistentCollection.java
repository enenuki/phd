/*   1:    */ package org.hibernate.collection.internal;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.HashSet;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.ListIterator;
/*  10:    */ import java.util.Set;
/*  11:    */ import org.hibernate.AssertionFailure;
/*  12:    */ import org.hibernate.HibernateException;
/*  13:    */ import org.hibernate.LazyInitializationException;
/*  14:    */ import org.hibernate.collection.spi.PersistentCollection;
/*  15:    */ import org.hibernate.engine.internal.ForeignKeys;
/*  16:    */ import org.hibernate.engine.spi.CollectionEntry;
/*  17:    */ import org.hibernate.engine.spi.EntityEntry;
/*  18:    */ import org.hibernate.engine.spi.PersistenceContext;
/*  19:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  20:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  21:    */ import org.hibernate.engine.spi.Status;
/*  22:    */ import org.hibernate.engine.spi.TypedValue;
/*  23:    */ import org.hibernate.internal.util.MarkerObject;
/*  24:    */ import org.hibernate.internal.util.collections.CollectionHelper;
/*  25:    */ import org.hibernate.internal.util.collections.EmptyIterator;
/*  26:    */ import org.hibernate.internal.util.collections.IdentitySet;
/*  27:    */ import org.hibernate.persister.collection.CollectionPersister;
/*  28:    */ import org.hibernate.persister.entity.EntityPersister;
/*  29:    */ import org.hibernate.pretty.MessageHelper;
/*  30:    */ import org.hibernate.type.Type;
/*  31:    */ 
/*  32:    */ public abstract class AbstractPersistentCollection
/*  33:    */   implements Serializable, PersistentCollection
/*  34:    */ {
/*  35:    */   private transient SessionImplementor session;
/*  36:    */   private boolean initialized;
/*  37:    */   private transient List<DelayedOperation> operationQueue;
/*  38:    */   private transient boolean directlyAccessible;
/*  39:    */   private transient boolean initializing;
/*  40:    */   private Object owner;
/*  41: 66 */   private int cachedSize = -1;
/*  42:    */   private String role;
/*  43:    */   private Serializable key;
/*  44:    */   private boolean dirty;
/*  45:    */   private Serializable storedSnapshot;
/*  46:    */   
/*  47:    */   public final String getRole()
/*  48:    */   {
/*  49: 76 */     return this.role;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public final Serializable getKey()
/*  53:    */   {
/*  54: 80 */     return this.key;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public final boolean isUnreferenced()
/*  58:    */   {
/*  59: 84 */     return this.role == null;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public final boolean isDirty()
/*  63:    */   {
/*  64: 88 */     return this.dirty;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public final void clearDirty()
/*  68:    */   {
/*  69: 92 */     this.dirty = false;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public final void dirty()
/*  73:    */   {
/*  74: 96 */     this.dirty = true;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public final Serializable getStoredSnapshot()
/*  78:    */   {
/*  79:100 */     return this.storedSnapshot;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public abstract boolean empty();
/*  83:    */   
/*  84:    */   protected final void read()
/*  85:    */   {
/*  86:112 */     initialize(false);
/*  87:    */   }
/*  88:    */   
/*  89:    */   protected boolean readSize()
/*  90:    */   {
/*  91:120 */     if (!this.initialized)
/*  92:    */     {
/*  93:121 */       if ((this.cachedSize != -1) && (!hasQueuedOperations())) {
/*  94:122 */         return true;
/*  95:    */       }
/*  96:125 */       throwLazyInitializationExceptionIfNotConnected();
/*  97:126 */       CollectionEntry entry = this.session.getPersistenceContext().getCollectionEntry(this);
/*  98:127 */       CollectionPersister persister = entry.getLoadedPersister();
/*  99:128 */       if (persister.isExtraLazy())
/* 100:    */       {
/* 101:129 */         if (hasQueuedOperations()) {
/* 102:130 */           this.session.flush();
/* 103:    */         }
/* 104:132 */         this.cachedSize = persister.getSize(entry.getLoadedKey(), this.session);
/* 105:133 */         return true;
/* 106:    */       }
/* 107:    */     }
/* 108:137 */     read();
/* 109:138 */     return false;
/* 110:    */   }
/* 111:    */   
/* 112:    */   protected Boolean readIndexExistence(Object index)
/* 113:    */   {
/* 114:142 */     if (!this.initialized)
/* 115:    */     {
/* 116:143 */       throwLazyInitializationExceptionIfNotConnected();
/* 117:144 */       CollectionEntry entry = this.session.getPersistenceContext().getCollectionEntry(this);
/* 118:145 */       CollectionPersister persister = entry.getLoadedPersister();
/* 119:146 */       if (persister.isExtraLazy())
/* 120:    */       {
/* 121:147 */         if (hasQueuedOperations()) {
/* 122:148 */           this.session.flush();
/* 123:    */         }
/* 124:150 */         return Boolean.valueOf(persister.indexExists(entry.getLoadedKey(), index, this.session));
/* 125:    */       }
/* 126:    */     }
/* 127:153 */     read();
/* 128:154 */     return null;
/* 129:    */   }
/* 130:    */   
/* 131:    */   protected Boolean readElementExistence(Object element)
/* 132:    */   {
/* 133:159 */     if (!this.initialized)
/* 134:    */     {
/* 135:160 */       throwLazyInitializationExceptionIfNotConnected();
/* 136:161 */       CollectionEntry entry = this.session.getPersistenceContext().getCollectionEntry(this);
/* 137:162 */       CollectionPersister persister = entry.getLoadedPersister();
/* 138:163 */       if (persister.isExtraLazy())
/* 139:    */       {
/* 140:164 */         if (hasQueuedOperations()) {
/* 141:165 */           this.session.flush();
/* 142:    */         }
/* 143:167 */         return Boolean.valueOf(persister.elementExists(entry.getLoadedKey(), element, this.session));
/* 144:    */       }
/* 145:    */     }
/* 146:170 */     read();
/* 147:171 */     return null;
/* 148:    */   }
/* 149:    */   
/* 150:175 */   protected static final Object UNKNOWN = new MarkerObject("UNKNOWN");
/* 151:    */   
/* 152:    */   protected Object readElementByIndex(Object index)
/* 153:    */   {
/* 154:178 */     if (!this.initialized)
/* 155:    */     {
/* 156:179 */       throwLazyInitializationExceptionIfNotConnected();
/* 157:180 */       CollectionEntry entry = this.session.getPersistenceContext().getCollectionEntry(this);
/* 158:181 */       CollectionPersister persister = entry.getLoadedPersister();
/* 159:182 */       if (persister.isExtraLazy())
/* 160:    */       {
/* 161:183 */         if (hasQueuedOperations()) {
/* 162:184 */           this.session.flush();
/* 163:    */         }
/* 164:186 */         return persister.getElementByIndex(entry.getLoadedKey(), index, this.session, this.owner);
/* 165:    */       }
/* 166:    */     }
/* 167:189 */     read();
/* 168:190 */     return UNKNOWN;
/* 169:    */   }
/* 170:    */   
/* 171:    */   protected int getCachedSize()
/* 172:    */   {
/* 173:195 */     return this.cachedSize;
/* 174:    */   }
/* 175:    */   
/* 176:    */   private boolean isConnectedToSession()
/* 177:    */   {
/* 178:199 */     return (this.session != null) && (this.session.isOpen()) && (this.session.getPersistenceContext().containsCollection(this));
/* 179:    */   }
/* 180:    */   
/* 181:    */   protected final void write()
/* 182:    */   {
/* 183:208 */     initialize(true);
/* 184:209 */     dirty();
/* 185:    */   }
/* 186:    */   
/* 187:    */   protected boolean isOperationQueueEnabled()
/* 188:    */   {
/* 189:218 */     return (!this.initialized) && (isConnectedToSession()) && (isInverseCollection());
/* 190:    */   }
/* 191:    */   
/* 192:    */   protected boolean isPutQueueEnabled()
/* 193:    */   {
/* 194:229 */     return (!this.initialized) && (isConnectedToSession()) && (isInverseOneToManyOrNoOrphanDelete());
/* 195:    */   }
/* 196:    */   
/* 197:    */   protected boolean isClearQueueEnabled()
/* 198:    */   {
/* 199:240 */     return (!this.initialized) && (isConnectedToSession()) && (isInverseCollectionNoOrphanDelete());
/* 200:    */   }
/* 201:    */   
/* 202:    */   private boolean isInverseCollection()
/* 203:    */   {
/* 204:250 */     CollectionEntry ce = this.session.getPersistenceContext().getCollectionEntry(this);
/* 205:251 */     return (ce != null) && (ce.getLoadedPersister().isInverse());
/* 206:    */   }
/* 207:    */   
/* 208:    */   private boolean isInverseCollectionNoOrphanDelete()
/* 209:    */   {
/* 210:260 */     CollectionEntry ce = this.session.getPersistenceContext().getCollectionEntry(this);
/* 211:261 */     return (ce != null) && (ce.getLoadedPersister().isInverse()) && (!ce.getLoadedPersister().hasOrphanDelete());
/* 212:    */   }
/* 213:    */   
/* 214:    */   private boolean isInverseOneToManyOrNoOrphanDelete()
/* 215:    */   {
/* 216:272 */     CollectionEntry ce = this.session.getPersistenceContext().getCollectionEntry(this);
/* 217:273 */     return (ce != null) && (ce.getLoadedPersister().isInverse()) && ((ce.getLoadedPersister().isOneToMany()) || (!ce.getLoadedPersister().hasOrphanDelete()));
/* 218:    */   }
/* 219:    */   
/* 220:    */   protected final void queueOperation(DelayedOperation operation)
/* 221:    */   {
/* 222:284 */     if (this.operationQueue == null) {
/* 223:285 */       this.operationQueue = new ArrayList(10);
/* 224:    */     }
/* 225:287 */     this.operationQueue.add(operation);
/* 226:288 */     this.dirty = true;
/* 227:    */   }
/* 228:    */   
/* 229:    */   protected final void performQueuedOperations()
/* 230:    */   {
/* 231:296 */     for (DelayedOperation operation : this.operationQueue) {
/* 232:297 */       operation.operate();
/* 233:    */     }
/* 234:    */   }
/* 235:    */   
/* 236:    */   public void setSnapshot(Serializable key, String role, Serializable snapshot)
/* 237:    */   {
/* 238:305 */     this.key = key;
/* 239:306 */     this.role = role;
/* 240:307 */     this.storedSnapshot = snapshot;
/* 241:    */   }
/* 242:    */   
/* 243:    */   public void postAction()
/* 244:    */   {
/* 245:315 */     this.operationQueue = null;
/* 246:316 */     this.cachedSize = -1;
/* 247:317 */     clearDirty();
/* 248:    */   }
/* 249:    */   
/* 250:    */   public AbstractPersistentCollection() {}
/* 251:    */   
/* 252:    */   protected AbstractPersistentCollection(SessionImplementor session)
/* 253:    */   {
/* 254:327 */     this.session = session;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public Object getValue()
/* 258:    */   {
/* 259:334 */     return this;
/* 260:    */   }
/* 261:    */   
/* 262:    */   public void beginRead()
/* 263:    */   {
/* 264:342 */     this.initializing = true;
/* 265:    */   }
/* 266:    */   
/* 267:    */   public boolean endRead()
/* 268:    */   {
/* 269:350 */     return afterInitialize();
/* 270:    */   }
/* 271:    */   
/* 272:    */   public boolean afterInitialize()
/* 273:    */   {
/* 274:354 */     setInitialized();
/* 275:356 */     if (this.operationQueue != null)
/* 276:    */     {
/* 277:357 */       performQueuedOperations();
/* 278:358 */       this.operationQueue = null;
/* 279:359 */       this.cachedSize = -1;
/* 280:360 */       return false;
/* 281:    */     }
/* 282:363 */     return true;
/* 283:    */   }
/* 284:    */   
/* 285:    */   protected final void initialize(boolean writing)
/* 286:    */   {
/* 287:374 */     if (!this.initialized)
/* 288:    */     {
/* 289:375 */       if (this.initializing) {
/* 290:376 */         throw new LazyInitializationException("illegal access to loading collection");
/* 291:    */       }
/* 292:378 */       throwLazyInitializationExceptionIfNotConnected();
/* 293:379 */       this.session.initializeCollection(this, writing);
/* 294:    */     }
/* 295:    */   }
/* 296:    */   
/* 297:    */   private void throwLazyInitializationExceptionIfNotConnected()
/* 298:    */   {
/* 299:384 */     if (!isConnectedToSession()) {
/* 300:385 */       throwLazyInitializationException("no session or session was closed");
/* 301:    */     }
/* 302:387 */     if (!this.session.isConnected()) {
/* 303:388 */       throwLazyInitializationException("session is disconnected");
/* 304:    */     }
/* 305:    */   }
/* 306:    */   
/* 307:    */   private void throwLazyInitializationException(String message)
/* 308:    */   {
/* 309:393 */     throw new LazyInitializationException("failed to lazily initialize a collection" + (this.role == null ? "" : new StringBuilder().append(" of role: ").append(this.role).toString()) + ", " + message);
/* 310:    */   }
/* 311:    */   
/* 312:    */   protected final void setInitialized()
/* 313:    */   {
/* 314:401 */     this.initializing = false;
/* 315:402 */     this.initialized = true;
/* 316:    */   }
/* 317:    */   
/* 318:    */   protected final void setDirectlyAccessible(boolean directlyAccessible)
/* 319:    */   {
/* 320:406 */     this.directlyAccessible = directlyAccessible;
/* 321:    */   }
/* 322:    */   
/* 323:    */   public boolean isDirectlyAccessible()
/* 324:    */   {
/* 325:414 */     return this.directlyAccessible;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public final boolean unsetSession(SessionImplementor currentSession)
/* 329:    */   {
/* 330:422 */     if (currentSession == this.session)
/* 331:    */     {
/* 332:423 */       this.session = null;
/* 333:424 */       return true;
/* 334:    */     }
/* 335:427 */     return false;
/* 336:    */   }
/* 337:    */   
/* 338:    */   public final boolean setCurrentSession(SessionImplementor session)
/* 339:    */     throws HibernateException
/* 340:    */   {
/* 341:438 */     if (session == this.session) {
/* 342:439 */       return false;
/* 343:    */     }
/* 344:442 */     if (isConnectedToSession())
/* 345:    */     {
/* 346:443 */       CollectionEntry ce = session.getPersistenceContext().getCollectionEntry(this);
/* 347:444 */       if (ce == null) {
/* 348:445 */         throw new HibernateException("Illegal attempt to associate a collection with two open sessions");
/* 349:    */       }
/* 350:450 */       throw new HibernateException("Illegal attempt to associate a collection with two open sessions: " + MessageHelper.collectionInfoString(ce.getLoadedPersister(), ce.getLoadedKey(), session.getFactory()));
/* 351:    */     }
/* 352:461 */     this.session = session;
/* 353:462 */     return true;
/* 354:    */   }
/* 355:    */   
/* 356:    */   public boolean needsRecreate(CollectionPersister persister)
/* 357:    */   {
/* 358:471 */     return false;
/* 359:    */   }
/* 360:    */   
/* 361:    */   public final void forceInitialization()
/* 362:    */     throws HibernateException
/* 363:    */   {
/* 364:479 */     if (!this.initialized)
/* 365:    */     {
/* 366:480 */       if (this.initializing) {
/* 367:481 */         throw new AssertionFailure("force initialize loading collection");
/* 368:    */       }
/* 369:483 */       if (this.session == null) {
/* 370:484 */         throw new HibernateException("collection is not associated with any session");
/* 371:    */       }
/* 372:486 */       if (!this.session.isConnected()) {
/* 373:487 */         throw new HibernateException("disconnected session");
/* 374:    */       }
/* 375:489 */       this.session.initializeCollection(this, false);
/* 376:    */     }
/* 377:    */   }
/* 378:    */   
/* 379:    */   protected final Serializable getSnapshot()
/* 380:    */   {
/* 381:499 */     return this.session.getPersistenceContext().getSnapshot(this);
/* 382:    */   }
/* 383:    */   
/* 384:    */   public final boolean wasInitialized()
/* 385:    */   {
/* 386:506 */     return this.initialized;
/* 387:    */   }
/* 388:    */   
/* 389:    */   public boolean isRowUpdatePossible()
/* 390:    */   {
/* 391:510 */     return true;
/* 392:    */   }
/* 393:    */   
/* 394:    */   public final boolean hasQueuedOperations()
/* 395:    */   {
/* 396:517 */     return this.operationQueue != null;
/* 397:    */   }
/* 398:    */   
/* 399:    */   public final Iterator queuedAdditionIterator()
/* 400:    */   {
/* 401:523 */     if (hasQueuedOperations()) {
/* 402:524 */       new Iterator()
/* 403:    */       {
/* 404:525 */         int i = 0;
/* 405:    */         
/* 406:    */         public Object next()
/* 407:    */         {
/* 408:527 */           return ((AbstractPersistentCollection.DelayedOperation)AbstractPersistentCollection.this.operationQueue.get(this.i++)).getAddedInstance();
/* 409:    */         }
/* 410:    */         
/* 411:    */         public boolean hasNext()
/* 412:    */         {
/* 413:530 */           return this.i < AbstractPersistentCollection.this.operationQueue.size();
/* 414:    */         }
/* 415:    */         
/* 416:    */         public void remove()
/* 417:    */         {
/* 418:533 */           throw new UnsupportedOperationException();
/* 419:    */         }
/* 420:    */       };
/* 421:    */     }
/* 422:538 */     return EmptyIterator.INSTANCE;
/* 423:    */   }
/* 424:    */   
/* 425:    */   public final Collection getQueuedOrphans(String entityName)
/* 426:    */   {
/* 427:546 */     if (hasQueuedOperations())
/* 428:    */     {
/* 429:547 */       Collection additions = new ArrayList(this.operationQueue.size());
/* 430:548 */       Collection removals = new ArrayList(this.operationQueue.size());
/* 431:549 */       for (DelayedOperation operation : this.operationQueue)
/* 432:    */       {
/* 433:550 */         additions.add(operation.getAddedInstance());
/* 434:551 */         removals.add(operation.getOrphan());
/* 435:    */       }
/* 436:553 */       return getOrphans(removals, additions, entityName, this.session);
/* 437:    */     }
/* 438:556 */     return CollectionHelper.EMPTY_COLLECTION;
/* 439:    */   }
/* 440:    */   
/* 441:    */   public void preInsert(CollectionPersister persister)
/* 442:    */     throws HibernateException
/* 443:    */   {}
/* 444:    */   
/* 445:    */   public void afterRowInsert(CollectionPersister persister, Object entry, int i)
/* 446:    */     throws HibernateException
/* 447:    */   {}
/* 448:    */   
/* 449:    */   public abstract Collection getOrphans(Serializable paramSerializable, String paramString)
/* 450:    */     throws HibernateException;
/* 451:    */   
/* 452:    */   public final SessionImplementor getSession()
/* 453:    */   {
/* 454:579 */     return this.session;
/* 455:    */   }
/* 456:    */   
/* 457:    */   protected final class IteratorProxy
/* 458:    */     implements Iterator
/* 459:    */   {
/* 460:    */     protected final Iterator itr;
/* 461:    */     
/* 462:    */     public IteratorProxy(Iterator itr)
/* 463:    */     {
/* 464:586 */       this.itr = itr;
/* 465:    */     }
/* 466:    */     
/* 467:    */     public boolean hasNext()
/* 468:    */     {
/* 469:589 */       return this.itr.hasNext();
/* 470:    */     }
/* 471:    */     
/* 472:    */     public Object next()
/* 473:    */     {
/* 474:593 */       return this.itr.next();
/* 475:    */     }
/* 476:    */     
/* 477:    */     public void remove()
/* 478:    */     {
/* 479:597 */       AbstractPersistentCollection.this.write();
/* 480:598 */       this.itr.remove();
/* 481:    */     }
/* 482:    */   }
/* 483:    */   
/* 484:    */   protected final class ListIteratorProxy
/* 485:    */     implements ListIterator
/* 486:    */   {
/* 487:    */     protected final ListIterator itr;
/* 488:    */     
/* 489:    */     public ListIteratorProxy(ListIterator itr)
/* 490:    */     {
/* 491:607 */       this.itr = itr;
/* 492:    */     }
/* 493:    */     
/* 494:    */     public void add(Object o)
/* 495:    */     {
/* 496:612 */       AbstractPersistentCollection.this.write();
/* 497:613 */       this.itr.add(o);
/* 498:    */     }
/* 499:    */     
/* 500:    */     public boolean hasNext()
/* 501:    */     {
/* 502:617 */       return this.itr.hasNext();
/* 503:    */     }
/* 504:    */     
/* 505:    */     public boolean hasPrevious()
/* 506:    */     {
/* 507:621 */       return this.itr.hasPrevious();
/* 508:    */     }
/* 509:    */     
/* 510:    */     public Object next()
/* 511:    */     {
/* 512:625 */       return this.itr.next();
/* 513:    */     }
/* 514:    */     
/* 515:    */     public int nextIndex()
/* 516:    */     {
/* 517:629 */       return this.itr.nextIndex();
/* 518:    */     }
/* 519:    */     
/* 520:    */     public Object previous()
/* 521:    */     {
/* 522:633 */       return this.itr.previous();
/* 523:    */     }
/* 524:    */     
/* 525:    */     public int previousIndex()
/* 526:    */     {
/* 527:637 */       return this.itr.previousIndex();
/* 528:    */     }
/* 529:    */     
/* 530:    */     public void remove()
/* 531:    */     {
/* 532:641 */       AbstractPersistentCollection.this.write();
/* 533:642 */       this.itr.remove();
/* 534:    */     }
/* 535:    */     
/* 536:    */     public void set(Object o)
/* 537:    */     {
/* 538:647 */       AbstractPersistentCollection.this.write();
/* 539:648 */       this.itr.set(o);
/* 540:    */     }
/* 541:    */   }
/* 542:    */   
/* 543:    */   protected class SetProxy
/* 544:    */     implements Set
/* 545:    */   {
/* 546:    */     protected final Collection set;
/* 547:    */     
/* 548:    */     public SetProxy(Collection set)
/* 549:    */     {
/* 550:657 */       this.set = set;
/* 551:    */     }
/* 552:    */     
/* 553:    */     public boolean add(Object o)
/* 554:    */     {
/* 555:662 */       AbstractPersistentCollection.this.write();
/* 556:663 */       return this.set.add(o);
/* 557:    */     }
/* 558:    */     
/* 559:    */     public boolean addAll(Collection c)
/* 560:    */     {
/* 561:668 */       AbstractPersistentCollection.this.write();
/* 562:669 */       return this.set.addAll(c);
/* 563:    */     }
/* 564:    */     
/* 565:    */     public void clear()
/* 566:    */     {
/* 567:673 */       AbstractPersistentCollection.this.write();
/* 568:674 */       this.set.clear();
/* 569:    */     }
/* 570:    */     
/* 571:    */     public boolean contains(Object o)
/* 572:    */     {
/* 573:678 */       return this.set.contains(o);
/* 574:    */     }
/* 575:    */     
/* 576:    */     public boolean containsAll(Collection c)
/* 577:    */     {
/* 578:682 */       return this.set.containsAll(c);
/* 579:    */     }
/* 580:    */     
/* 581:    */     public boolean isEmpty()
/* 582:    */     {
/* 583:686 */       return this.set.isEmpty();
/* 584:    */     }
/* 585:    */     
/* 586:    */     public Iterator iterator()
/* 587:    */     {
/* 588:690 */       return new AbstractPersistentCollection.IteratorProxy(AbstractPersistentCollection.this, this.set.iterator());
/* 589:    */     }
/* 590:    */     
/* 591:    */     public boolean remove(Object o)
/* 592:    */     {
/* 593:694 */       AbstractPersistentCollection.this.write();
/* 594:695 */       return this.set.remove(o);
/* 595:    */     }
/* 596:    */     
/* 597:    */     public boolean removeAll(Collection c)
/* 598:    */     {
/* 599:699 */       AbstractPersistentCollection.this.write();
/* 600:700 */       return this.set.removeAll(c);
/* 601:    */     }
/* 602:    */     
/* 603:    */     public boolean retainAll(Collection c)
/* 604:    */     {
/* 605:704 */       AbstractPersistentCollection.this.write();
/* 606:705 */       return this.set.retainAll(c);
/* 607:    */     }
/* 608:    */     
/* 609:    */     public int size()
/* 610:    */     {
/* 611:709 */       return this.set.size();
/* 612:    */     }
/* 613:    */     
/* 614:    */     public Object[] toArray()
/* 615:    */     {
/* 616:713 */       return this.set.toArray();
/* 617:    */     }
/* 618:    */     
/* 619:    */     public Object[] toArray(Object[] array)
/* 620:    */     {
/* 621:718 */       return this.set.toArray(array);
/* 622:    */     }
/* 623:    */   }
/* 624:    */   
/* 625:    */   protected final class ListProxy
/* 626:    */     implements List
/* 627:    */   {
/* 628:    */     protected final List list;
/* 629:    */     
/* 630:    */     public ListProxy(List list)
/* 631:    */     {
/* 632:727 */       this.list = list;
/* 633:    */     }
/* 634:    */     
/* 635:    */     public void add(int index, Object value)
/* 636:    */     {
/* 637:733 */       AbstractPersistentCollection.this.write();
/* 638:734 */       this.list.add(index, value);
/* 639:    */     }
/* 640:    */     
/* 641:    */     public boolean add(Object o)
/* 642:    */     {
/* 643:740 */       AbstractPersistentCollection.this.write();
/* 644:741 */       return this.list.add(o);
/* 645:    */     }
/* 646:    */     
/* 647:    */     public boolean addAll(Collection c)
/* 648:    */     {
/* 649:747 */       AbstractPersistentCollection.this.write();
/* 650:748 */       return this.list.addAll(c);
/* 651:    */     }
/* 652:    */     
/* 653:    */     public boolean addAll(int i, Collection c)
/* 654:    */     {
/* 655:754 */       AbstractPersistentCollection.this.write();
/* 656:755 */       return this.list.addAll(i, c);
/* 657:    */     }
/* 658:    */     
/* 659:    */     public void clear()
/* 660:    */     {
/* 661:760 */       AbstractPersistentCollection.this.write();
/* 662:761 */       this.list.clear();
/* 663:    */     }
/* 664:    */     
/* 665:    */     public boolean contains(Object o)
/* 666:    */     {
/* 667:766 */       return this.list.contains(o);
/* 668:    */     }
/* 669:    */     
/* 670:    */     public boolean containsAll(Collection c)
/* 671:    */     {
/* 672:771 */       return this.list.containsAll(c);
/* 673:    */     }
/* 674:    */     
/* 675:    */     public Object get(int i)
/* 676:    */     {
/* 677:776 */       return this.list.get(i);
/* 678:    */     }
/* 679:    */     
/* 680:    */     public int indexOf(Object o)
/* 681:    */     {
/* 682:781 */       return this.list.indexOf(o);
/* 683:    */     }
/* 684:    */     
/* 685:    */     public boolean isEmpty()
/* 686:    */     {
/* 687:786 */       return this.list.isEmpty();
/* 688:    */     }
/* 689:    */     
/* 690:    */     public Iterator iterator()
/* 691:    */     {
/* 692:791 */       return new AbstractPersistentCollection.IteratorProxy(AbstractPersistentCollection.this, this.list.iterator());
/* 693:    */     }
/* 694:    */     
/* 695:    */     public int lastIndexOf(Object o)
/* 696:    */     {
/* 697:796 */       return this.list.lastIndexOf(o);
/* 698:    */     }
/* 699:    */     
/* 700:    */     public ListIterator listIterator()
/* 701:    */     {
/* 702:801 */       return new AbstractPersistentCollection.ListIteratorProxy(AbstractPersistentCollection.this, this.list.listIterator());
/* 703:    */     }
/* 704:    */     
/* 705:    */     public ListIterator listIterator(int i)
/* 706:    */     {
/* 707:806 */       return new AbstractPersistentCollection.ListIteratorProxy(AbstractPersistentCollection.this, this.list.listIterator(i));
/* 708:    */     }
/* 709:    */     
/* 710:    */     public Object remove(int i)
/* 711:    */     {
/* 712:811 */       AbstractPersistentCollection.this.write();
/* 713:812 */       return this.list.remove(i);
/* 714:    */     }
/* 715:    */     
/* 716:    */     public boolean remove(Object o)
/* 717:    */     {
/* 718:817 */       AbstractPersistentCollection.this.write();
/* 719:818 */       return this.list.remove(o);
/* 720:    */     }
/* 721:    */     
/* 722:    */     public boolean removeAll(Collection c)
/* 723:    */     {
/* 724:823 */       AbstractPersistentCollection.this.write();
/* 725:824 */       return this.list.removeAll(c);
/* 726:    */     }
/* 727:    */     
/* 728:    */     public boolean retainAll(Collection c)
/* 729:    */     {
/* 730:829 */       AbstractPersistentCollection.this.write();
/* 731:830 */       return this.list.retainAll(c);
/* 732:    */     }
/* 733:    */     
/* 734:    */     public Object set(int i, Object o)
/* 735:    */     {
/* 736:836 */       AbstractPersistentCollection.this.write();
/* 737:837 */       return this.list.set(i, o);
/* 738:    */     }
/* 739:    */     
/* 740:    */     public int size()
/* 741:    */     {
/* 742:842 */       return this.list.size();
/* 743:    */     }
/* 744:    */     
/* 745:    */     public List subList(int i, int j)
/* 746:    */     {
/* 747:847 */       return this.list.subList(i, j);
/* 748:    */     }
/* 749:    */     
/* 750:    */     public Object[] toArray()
/* 751:    */     {
/* 752:852 */       return this.list.toArray();
/* 753:    */     }
/* 754:    */     
/* 755:    */     public Object[] toArray(Object[] array)
/* 756:    */     {
/* 757:858 */       return this.list.toArray(array);
/* 758:    */     }
/* 759:    */   }
/* 760:    */   
/* 761:    */   protected static Collection getOrphans(Collection oldElements, Collection currentElements, String entityName, SessionImplementor session)
/* 762:    */     throws HibernateException
/* 763:    */   {
/* 764:885 */     if (currentElements.size() == 0) {
/* 765:886 */       return oldElements;
/* 766:    */     }
/* 767:888 */     if (oldElements.size() == 0) {
/* 768:889 */       return oldElements;
/* 769:    */     }
/* 770:892 */     EntityPersister entityPersister = session.getFactory().getEntityPersister(entityName);
/* 771:893 */     Type idType = entityPersister.getIdentifierType();
/* 772:    */     
/* 773:    */ 
/* 774:896 */     Collection res = new ArrayList();
/* 775:    */     
/* 776:    */ 
/* 777:899 */     Set currentIds = new HashSet();
/* 778:900 */     Set currentSaving = new IdentitySet();
/* 779:901 */     for (Object current : currentElements) {
/* 780:902 */       if ((current != null) && (ForeignKeys.isNotTransient(entityName, current, null, session)))
/* 781:    */       {
/* 782:903 */         EntityEntry ee = session.getPersistenceContext().getEntry(current);
/* 783:904 */         if ((ee != null) && (ee.getStatus() == Status.SAVING))
/* 784:    */         {
/* 785:905 */           currentSaving.add(current);
/* 786:    */         }
/* 787:    */         else
/* 788:    */         {
/* 789:908 */           Serializable currentId = ForeignKeys.getEntityIdentifierIfNotUnsaved(entityName, current, session);
/* 790:    */           
/* 791:    */ 
/* 792:    */ 
/* 793:    */ 
/* 794:913 */           currentIds.add(new TypedValue(idType, currentId, entityPersister.getEntityMode()));
/* 795:    */         }
/* 796:    */       }
/* 797:    */     }
/* 798:919 */     for (Object old : oldElements) {
/* 799:920 */       if (!currentSaving.contains(old))
/* 800:    */       {
/* 801:921 */         Serializable oldId = ForeignKeys.getEntityIdentifierIfNotUnsaved(entityName, old, session);
/* 802:922 */         if (!currentIds.contains(new TypedValue(idType, oldId, entityPersister.getEntityMode()))) {
/* 803:923 */           res.add(old);
/* 804:    */         }
/* 805:    */       }
/* 806:    */     }
/* 807:928 */     return res;
/* 808:    */   }
/* 809:    */   
/* 810:    */   public static void identityRemove(Collection list, Object object, String entityName, SessionImplementor session)
/* 811:    */     throws HibernateException
/* 812:    */   {
/* 813:937 */     if ((object != null) && (ForeignKeys.isNotTransient(entityName, object, null, session)))
/* 814:    */     {
/* 815:938 */       EntityPersister entityPersister = session.getFactory().getEntityPersister(entityName);
/* 816:939 */       Type idType = entityPersister.getIdentifierType();
/* 817:    */       
/* 818:941 */       Serializable idOfCurrent = ForeignKeys.getEntityIdentifierIfNotUnsaved(entityName, object, session);
/* 819:942 */       Iterator itr = list.iterator();
/* 820:943 */       while (itr.hasNext())
/* 821:    */       {
/* 822:944 */         Serializable idOfOld = ForeignKeys.getEntityIdentifierIfNotUnsaved(entityName, itr.next(), session);
/* 823:945 */         if (idType.isEqual(idOfCurrent, idOfOld, session.getFactory()))
/* 824:    */         {
/* 825:946 */           itr.remove();
/* 826:947 */           break;
/* 827:    */         }
/* 828:    */       }
/* 829:    */     }
/* 830:    */   }
/* 831:    */   
/* 832:    */   public Object getIdentifier(Object entry, int i)
/* 833:    */   {
/* 834:955 */     throw new UnsupportedOperationException();
/* 835:    */   }
/* 836:    */   
/* 837:    */   public Object getOwner()
/* 838:    */   {
/* 839:959 */     return this.owner;
/* 840:    */   }
/* 841:    */   
/* 842:    */   public void setOwner(Object owner)
/* 843:    */   {
/* 844:963 */     this.owner = owner;
/* 845:    */   }
/* 846:    */   
/* 847:    */   protected static abstract interface DelayedOperation
/* 848:    */   {
/* 849:    */     public abstract void operate();
/* 850:    */     
/* 851:    */     public abstract Object getAddedInstance();
/* 852:    */     
/* 853:    */     public abstract Object getOrphan();
/* 854:    */   }
/* 855:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.collection.internal.AbstractPersistentCollection
 * JD-Core Version:    0.7.0.1
 */