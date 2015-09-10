/*    1:     */ package org.hibernate.engine.internal;
/*    2:     */ 
/*    3:     */ import java.io.IOException;
/*    4:     */ import java.io.InvalidObjectException;
/*    5:     */ import java.io.ObjectInputStream;
/*    6:     */ import java.io.ObjectOutputStream;
/*    7:     */ import java.io.Serializable;
/*    8:     */ import java.util.ArrayList;
/*    9:     */ import java.util.Collection;
/*   10:     */ import java.util.HashMap;
/*   11:     */ import java.util.HashSet;
/*   12:     */ import java.util.IdentityHashMap;
/*   13:     */ import java.util.Iterator;
/*   14:     */ import java.util.List;
/*   15:     */ import java.util.Map;
/*   16:     */ import java.util.Map.Entry;
/*   17:     */ import java.util.Set;
/*   18:     */ import org.apache.commons.collections.map.ReferenceMap;
/*   19:     */ import org.hibernate.AssertionFailure;
/*   20:     */ import org.hibernate.Hibernate;
/*   21:     */ import org.hibernate.HibernateException;
/*   22:     */ import org.hibernate.LockMode;
/*   23:     */ import org.hibernate.MappingException;
/*   24:     */ import org.hibernate.NonUniqueObjectException;
/*   25:     */ import org.hibernate.PersistentObjectException;
/*   26:     */ import org.hibernate.TransientObjectException;
/*   27:     */ import org.hibernate.collection.spi.PersistentCollection;
/*   28:     */ import org.hibernate.engine.loading.internal.LoadContexts;
/*   29:     */ import org.hibernate.engine.spi.AssociationKey;
/*   30:     */ import org.hibernate.engine.spi.BatchFetchQueue;
/*   31:     */ import org.hibernate.engine.spi.CollectionEntry;
/*   32:     */ import org.hibernate.engine.spi.CollectionKey;
/*   33:     */ import org.hibernate.engine.spi.EntityEntry;
/*   34:     */ import org.hibernate.engine.spi.EntityKey;
/*   35:     */ import org.hibernate.engine.spi.EntityUniqueKey;
/*   36:     */ import org.hibernate.engine.spi.PersistenceContext;
/*   37:     */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   38:     */ import org.hibernate.engine.spi.SessionImplementor;
/*   39:     */ import org.hibernate.engine.spi.Status;
/*   40:     */ import org.hibernate.internal.CoreMessageLogger;
/*   41:     */ import org.hibernate.internal.util.MarkerObject;
/*   42:     */ import org.hibernate.internal.util.collections.IdentityMap;
/*   43:     */ import org.hibernate.persister.collection.CollectionPersister;
/*   44:     */ import org.hibernate.persister.entity.EntityPersister;
/*   45:     */ import org.hibernate.pretty.MessageHelper;
/*   46:     */ import org.hibernate.proxy.HibernateProxy;
/*   47:     */ import org.hibernate.proxy.LazyInitializer;
/*   48:     */ import org.hibernate.tuple.ElementWrapper;
/*   49:     */ import org.hibernate.type.CollectionType;
/*   50:     */ import org.jboss.logging.Logger;
/*   51:     */ 
/*   52:     */ public class StatefulPersistenceContext
/*   53:     */   implements PersistenceContext
/*   54:     */ {
/*   55:  88 */   public static final Object NO_ROW = new MarkerObject("NO_ROW");
/*   56:  90 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, StatefulPersistenceContext.class.getName());
/*   57:     */   private static final int INIT_COLL_SIZE = 8;
/*   58:     */   private SessionImplementor session;
/*   59:     */   private Map<EntityKey, Object> entitiesByKey;
/*   60:     */   private Map<EntityUniqueKey, Object> entitiesByUniqueKey;
/*   61:     */   private Map<Object, EntityEntry> entityEntries;
/*   62:     */   private Map<EntityKey, Object> proxiesByKey;
/*   63:     */   private Map<EntityKey, Object> entitySnapshotsByKey;
/*   64:     */   private Map<Object, PersistentCollection> arrayHolders;
/*   65:     */   private IdentityMap<PersistentCollection, CollectionEntry> collectionEntries;
/*   66:     */   private Map<CollectionKey, PersistentCollection> collectionsByKey;
/*   67:     */   private HashSet<EntityKey> nullifiableEntityKeys;
/*   68:     */   private HashSet<AssociationKey> nullAssociations;
/*   69:     */   private List<PersistentCollection> nonlazyCollections;
/*   70:     */   private Map<CollectionKey, PersistentCollection> unownedCollections;
/*   71:     */   private Map parentsByChild;
/*   72: 139 */   private int cascading = 0;
/*   73: 140 */   private int loadCounter = 0;
/*   74: 141 */   private boolean flushing = false;
/*   75: 143 */   private boolean defaultReadOnly = false;
/*   76: 144 */   private boolean hasNonReadOnlyEntities = false;
/*   77:     */   private LoadContexts loadContexts;
/*   78:     */   private BatchFetchQueue batchFetchQueue;
/*   79:     */   private HashMap<String, List<Serializable>> insertedKeysMap;
/*   80:     */   
/*   81:     */   public StatefulPersistenceContext(SessionImplementor session)
/*   82:     */   {
/*   83: 157 */     this.session = session;
/*   84:     */     
/*   85: 159 */     this.entitiesByKey = new HashMap(8);
/*   86: 160 */     this.entitiesByUniqueKey = new HashMap(8);
/*   87: 161 */     this.proxiesByKey = new ReferenceMap(0, 2);
/*   88: 162 */     this.entitySnapshotsByKey = new HashMap(8);
/*   89:     */     
/*   90: 164 */     this.entityEntries = IdentityMap.instantiateSequenced(8);
/*   91: 165 */     this.collectionEntries = IdentityMap.instantiateSequenced(8);
/*   92: 166 */     this.parentsByChild = IdentityMap.instantiateSequenced(8);
/*   93:     */     
/*   94: 168 */     this.collectionsByKey = new HashMap(8);
/*   95: 169 */     this.arrayHolders = new IdentityHashMap(8);
/*   96:     */     
/*   97: 171 */     this.nullifiableEntityKeys = new HashSet();
/*   98:     */     
/*   99: 173 */     initTransientState();
/*  100:     */   }
/*  101:     */   
/*  102:     */   private void initTransientState()
/*  103:     */   {
/*  104: 177 */     this.nullAssociations = new HashSet(8);
/*  105: 178 */     this.nonlazyCollections = new ArrayList(8);
/*  106:     */   }
/*  107:     */   
/*  108:     */   public boolean isStateless()
/*  109:     */   {
/*  110: 182 */     return false;
/*  111:     */   }
/*  112:     */   
/*  113:     */   public SessionImplementor getSession()
/*  114:     */   {
/*  115: 186 */     return this.session;
/*  116:     */   }
/*  117:     */   
/*  118:     */   public LoadContexts getLoadContexts()
/*  119:     */   {
/*  120: 190 */     if (this.loadContexts == null) {
/*  121: 191 */       this.loadContexts = new LoadContexts(this);
/*  122:     */     }
/*  123: 193 */     return this.loadContexts;
/*  124:     */   }
/*  125:     */   
/*  126:     */   public void addUnownedCollection(CollectionKey key, PersistentCollection collection)
/*  127:     */   {
/*  128: 197 */     if (this.unownedCollections == null) {
/*  129: 198 */       this.unownedCollections = new HashMap(8);
/*  130:     */     }
/*  131: 200 */     this.unownedCollections.put(key, collection);
/*  132:     */   }
/*  133:     */   
/*  134:     */   public PersistentCollection useUnownedCollection(CollectionKey key)
/*  135:     */   {
/*  136: 204 */     if (this.unownedCollections == null) {
/*  137: 205 */       return null;
/*  138:     */     }
/*  139: 208 */     return (PersistentCollection)this.unownedCollections.remove(key);
/*  140:     */   }
/*  141:     */   
/*  142:     */   public BatchFetchQueue getBatchFetchQueue()
/*  143:     */   {
/*  144: 217 */     if (this.batchFetchQueue == null) {
/*  145: 218 */       this.batchFetchQueue = new BatchFetchQueue(this);
/*  146:     */     }
/*  147: 220 */     return this.batchFetchQueue;
/*  148:     */   }
/*  149:     */   
/*  150:     */   public void clear()
/*  151:     */   {
/*  152: 224 */     for (Object o : this.proxiesByKey.values())
/*  153:     */     {
/*  154: 225 */       LazyInitializer li = ((HibernateProxy)o).getHibernateLazyInitializer();
/*  155: 226 */       li.unsetSession();
/*  156:     */     }
/*  157: 228 */     for (Map.Entry<PersistentCollection, CollectionEntry> aCollectionEntryArray : IdentityMap.concurrentEntries(this.collectionEntries)) {
/*  158: 229 */       ((PersistentCollection)aCollectionEntryArray.getKey()).unsetSession(getSession());
/*  159:     */     }
/*  160: 231 */     this.arrayHolders.clear();
/*  161: 232 */     this.entitiesByKey.clear();
/*  162: 233 */     this.entitiesByUniqueKey.clear();
/*  163: 234 */     this.entityEntries.clear();
/*  164: 235 */     this.parentsByChild.clear();
/*  165: 236 */     this.entitySnapshotsByKey.clear();
/*  166: 237 */     this.collectionsByKey.clear();
/*  167: 238 */     this.collectionEntries.clear();
/*  168: 239 */     if (this.unownedCollections != null) {
/*  169: 240 */       this.unownedCollections.clear();
/*  170:     */     }
/*  171: 242 */     this.proxiesByKey.clear();
/*  172: 243 */     this.nullifiableEntityKeys.clear();
/*  173: 244 */     if (this.batchFetchQueue != null) {
/*  174: 245 */       this.batchFetchQueue.clear();
/*  175:     */     }
/*  176: 248 */     this.hasNonReadOnlyEntities = false;
/*  177: 249 */     if (this.loadContexts != null) {
/*  178: 250 */       this.loadContexts.cleanup();
/*  179:     */     }
/*  180:     */   }
/*  181:     */   
/*  182:     */   public boolean isDefaultReadOnly()
/*  183:     */   {
/*  184: 258 */     return this.defaultReadOnly;
/*  185:     */   }
/*  186:     */   
/*  187:     */   public void setDefaultReadOnly(boolean defaultReadOnly)
/*  188:     */   {
/*  189: 265 */     this.defaultReadOnly = defaultReadOnly;
/*  190:     */   }
/*  191:     */   
/*  192:     */   public boolean hasNonReadOnlyEntities()
/*  193:     */   {
/*  194: 269 */     return this.hasNonReadOnlyEntities;
/*  195:     */   }
/*  196:     */   
/*  197:     */   public void setEntryStatus(EntityEntry entry, Status status)
/*  198:     */   {
/*  199: 273 */     entry.setStatus(status);
/*  200: 274 */     setHasNonReadOnlyEnties(status);
/*  201:     */   }
/*  202:     */   
/*  203:     */   private void setHasNonReadOnlyEnties(Status status)
/*  204:     */   {
/*  205: 278 */     if ((status == Status.DELETED) || (status == Status.MANAGED) || (status == Status.SAVING)) {
/*  206: 279 */       this.hasNonReadOnlyEntities = true;
/*  207:     */     }
/*  208:     */   }
/*  209:     */   
/*  210:     */   public void afterTransactionCompletion()
/*  211:     */   {
/*  212: 284 */     cleanUpInsertedKeysAfterTransaction();
/*  213: 286 */     for (EntityEntry o : this.entityEntries.values()) {
/*  214: 287 */       o.setLockMode(LockMode.NONE);
/*  215:     */     }
/*  216:     */   }
/*  217:     */   
/*  218:     */   public Object[] getDatabaseSnapshot(Serializable id, EntityPersister persister)
/*  219:     */     throws HibernateException
/*  220:     */   {
/*  221: 297 */     EntityKey key = this.session.generateEntityKey(id, persister);
/*  222: 298 */     Object cached = this.entitySnapshotsByKey.get(key);
/*  223: 299 */     if (cached != null) {
/*  224: 300 */       return cached == NO_ROW ? null : (Object[])cached;
/*  225:     */     }
/*  226: 303 */     Object[] snapshot = persister.getDatabaseSnapshot(id, this.session);
/*  227: 304 */     this.entitySnapshotsByKey.put(key, snapshot == null ? NO_ROW : snapshot);
/*  228: 305 */     return snapshot;
/*  229:     */   }
/*  230:     */   
/*  231:     */   public Object[] getNaturalIdSnapshot(Serializable id, EntityPersister persister)
/*  232:     */     throws HibernateException
/*  233:     */   {
/*  234: 311 */     if (!persister.hasNaturalIdentifier()) {
/*  235: 312 */       return null;
/*  236:     */     }
/*  237: 317 */     int[] props = persister.getNaturalIdentifierProperties();
/*  238: 318 */     boolean[] updateable = persister.getPropertyUpdateability();
/*  239: 319 */     boolean allNatualIdPropsAreUpdateable = true;
/*  240: 320 */     for (int i = 0; i < props.length; i++) {
/*  241: 321 */       if (updateable[props[i]] == 0)
/*  242:     */       {
/*  243: 322 */         allNatualIdPropsAreUpdateable = false;
/*  244: 323 */         break;
/*  245:     */       }
/*  246:     */     }
/*  247: 327 */     if (allNatualIdPropsAreUpdateable)
/*  248:     */     {
/*  249: 331 */       Object[] entitySnapshot = getDatabaseSnapshot(id, persister);
/*  250: 332 */       if (entitySnapshot == NO_ROW) {
/*  251: 333 */         return null;
/*  252:     */       }
/*  253: 335 */       Object[] naturalIdSnapshot = new Object[props.length];
/*  254: 336 */       for (int i = 0; i < props.length; i++) {
/*  255: 337 */         naturalIdSnapshot[i] = entitySnapshot[props[i]];
/*  256:     */       }
/*  257: 339 */       return naturalIdSnapshot;
/*  258:     */     }
/*  259: 342 */     return persister.getNaturalIdentifierSnapshot(id, this.session);
/*  260:     */   }
/*  261:     */   
/*  262:     */   public Object[] getCachedDatabaseSnapshot(EntityKey key)
/*  263:     */   {
/*  264: 358 */     Object snapshot = this.entitySnapshotsByKey.get(key);
/*  265: 359 */     if (snapshot == NO_ROW) {
/*  266: 360 */       throw new IllegalStateException("persistence context reported no row snapshot for " + MessageHelper.infoString(key.getEntityName(), key.getIdentifier()));
/*  267:     */     }
/*  268: 362 */     return (Object[])snapshot;
/*  269:     */   }
/*  270:     */   
/*  271:     */   public void addEntity(EntityKey key, Object entity)
/*  272:     */   {
/*  273: 370 */     this.entitiesByKey.put(key, entity);
/*  274: 371 */     getBatchFetchQueue().removeBatchLoadableEntityKey(key);
/*  275:     */   }
/*  276:     */   
/*  277:     */   public Object getEntity(EntityKey key)
/*  278:     */   {
/*  279: 379 */     return this.entitiesByKey.get(key);
/*  280:     */   }
/*  281:     */   
/*  282:     */   public boolean containsEntity(EntityKey key)
/*  283:     */   {
/*  284: 383 */     return this.entitiesByKey.containsKey(key);
/*  285:     */   }
/*  286:     */   
/*  287:     */   public Object removeEntity(EntityKey key)
/*  288:     */   {
/*  289: 392 */     Object entity = this.entitiesByKey.remove(key);
/*  290: 393 */     Iterator iter = this.entitiesByUniqueKey.values().iterator();
/*  291: 394 */     while (iter.hasNext()) {
/*  292: 395 */       if (iter.next() == entity) {
/*  293: 395 */         iter.remove();
/*  294:     */       }
/*  295:     */     }
/*  296: 398 */     this.parentsByChild.clear();
/*  297: 399 */     this.entitySnapshotsByKey.remove(key);
/*  298: 400 */     this.nullifiableEntityKeys.remove(key);
/*  299: 401 */     getBatchFetchQueue().removeBatchLoadableEntityKey(key);
/*  300: 402 */     getBatchFetchQueue().removeSubselect(key);
/*  301: 403 */     return entity;
/*  302:     */   }
/*  303:     */   
/*  304:     */   public Object getEntity(EntityUniqueKey euk)
/*  305:     */   {
/*  306: 410 */     return this.entitiesByUniqueKey.get(euk);
/*  307:     */   }
/*  308:     */   
/*  309:     */   public void addEntity(EntityUniqueKey euk, Object entity)
/*  310:     */   {
/*  311: 417 */     this.entitiesByUniqueKey.put(euk, entity);
/*  312:     */   }
/*  313:     */   
/*  314:     */   public EntityEntry getEntry(Object entity)
/*  315:     */   {
/*  316: 427 */     return (EntityEntry)this.entityEntries.get(entity);
/*  317:     */   }
/*  318:     */   
/*  319:     */   public EntityEntry removeEntry(Object entity)
/*  320:     */   {
/*  321: 434 */     return (EntityEntry)this.entityEntries.remove(entity);
/*  322:     */   }
/*  323:     */   
/*  324:     */   public boolean isEntryFor(Object entity)
/*  325:     */   {
/*  326: 441 */     return this.entityEntries.containsKey(entity);
/*  327:     */   }
/*  328:     */   
/*  329:     */   public CollectionEntry getCollectionEntry(PersistentCollection coll)
/*  330:     */   {
/*  331: 448 */     return (CollectionEntry)this.collectionEntries.get(coll);
/*  332:     */   }
/*  333:     */   
/*  334:     */   public EntityEntry addEntity(Object entity, Status status, Object[] loadedState, EntityKey entityKey, Object version, LockMode lockMode, boolean existsInDatabase, EntityPersister persister, boolean disableVersionIncrement, boolean lazyPropertiesAreUnfetched)
/*  335:     */   {
/*  336: 467 */     addEntity(entityKey, entity);
/*  337:     */     
/*  338: 469 */     return addEntry(entity, status, loadedState, null, entityKey.getIdentifier(), version, lockMode, existsInDatabase, persister, disableVersionIncrement, lazyPropertiesAreUnfetched);
/*  339:     */   }
/*  340:     */   
/*  341:     */   public EntityEntry addEntry(Object entity, Status status, Object[] loadedState, Object rowId, Serializable id, Object version, LockMode lockMode, boolean existsInDatabase, EntityPersister persister, boolean disableVersionIncrement, boolean lazyPropertiesAreUnfetched)
/*  342:     */   {
/*  343: 502 */     EntityEntry e = new EntityEntry(status, loadedState, rowId, id, version, lockMode, existsInDatabase, persister, persister.getEntityMode(), this.session.getTenantIdentifier(), disableVersionIncrement, lazyPropertiesAreUnfetched);
/*  344:     */     
/*  345:     */ 
/*  346:     */ 
/*  347:     */ 
/*  348:     */ 
/*  349:     */ 
/*  350:     */ 
/*  351:     */ 
/*  352:     */ 
/*  353:     */ 
/*  354:     */ 
/*  355:     */ 
/*  356:     */ 
/*  357: 516 */     this.entityEntries.put(entity, e);
/*  358:     */     
/*  359: 518 */     setHasNonReadOnlyEnties(status);
/*  360: 519 */     return e;
/*  361:     */   }
/*  362:     */   
/*  363:     */   public boolean containsCollection(PersistentCollection collection)
/*  364:     */   {
/*  365: 523 */     return this.collectionEntries.containsKey(collection);
/*  366:     */   }
/*  367:     */   
/*  368:     */   public boolean containsProxy(Object entity)
/*  369:     */   {
/*  370: 527 */     return this.proxiesByKey.containsValue(entity);
/*  371:     */   }
/*  372:     */   
/*  373:     */   public boolean reassociateIfUninitializedProxy(Object value)
/*  374:     */     throws MappingException
/*  375:     */   {
/*  376: 538 */     if ((value instanceof ElementWrapper)) {
/*  377: 539 */       value = ((ElementWrapper)value).getElement();
/*  378:     */     }
/*  379: 542 */     if (!Hibernate.isInitialized(value))
/*  380:     */     {
/*  381: 543 */       HibernateProxy proxy = (HibernateProxy)value;
/*  382: 544 */       LazyInitializer li = proxy.getHibernateLazyInitializer();
/*  383: 545 */       reassociateProxy(li, proxy);
/*  384: 546 */       return true;
/*  385:     */     }
/*  386: 549 */     return false;
/*  387:     */   }
/*  388:     */   
/*  389:     */   public void reassociateProxy(Object value, Serializable id)
/*  390:     */     throws MappingException
/*  391:     */   {
/*  392: 558 */     if ((value instanceof ElementWrapper)) {
/*  393: 559 */       value = ((ElementWrapper)value).getElement();
/*  394:     */     }
/*  395: 562 */     if ((value instanceof HibernateProxy))
/*  396:     */     {
/*  397: 563 */       LOG.debugf("Setting proxy identifier: %s", id);
/*  398: 564 */       HibernateProxy proxy = (HibernateProxy)value;
/*  399: 565 */       LazyInitializer li = proxy.getHibernateLazyInitializer();
/*  400: 566 */       li.setIdentifier(id);
/*  401: 567 */       reassociateProxy(li, proxy);
/*  402:     */     }
/*  403:     */   }
/*  404:     */   
/*  405:     */   private void reassociateProxy(LazyInitializer li, HibernateProxy proxy)
/*  406:     */   {
/*  407: 578 */     if (li.getSession() != getSession())
/*  408:     */     {
/*  409: 579 */       EntityPersister persister = this.session.getFactory().getEntityPersister(li.getEntityName());
/*  410: 580 */       EntityKey key = this.session.generateEntityKey(li.getIdentifier(), persister);
/*  411: 582 */       if (!this.proxiesByKey.containsKey(key)) {
/*  412: 583 */         this.proxiesByKey.put(key, proxy);
/*  413:     */       }
/*  414: 585 */       proxy.getHibernateLazyInitializer().setSession(this.session);
/*  415:     */     }
/*  416:     */   }
/*  417:     */   
/*  418:     */   public Object unproxy(Object maybeProxy)
/*  419:     */     throws HibernateException
/*  420:     */   {
/*  421: 595 */     if ((maybeProxy instanceof ElementWrapper)) {
/*  422: 596 */       maybeProxy = ((ElementWrapper)maybeProxy).getElement();
/*  423:     */     }
/*  424: 599 */     if ((maybeProxy instanceof HibernateProxy))
/*  425:     */     {
/*  426: 600 */       HibernateProxy proxy = (HibernateProxy)maybeProxy;
/*  427: 601 */       LazyInitializer li = proxy.getHibernateLazyInitializer();
/*  428: 602 */       if (li.isUninitialized()) {
/*  429: 603 */         throw new PersistentObjectException("object was an uninitialized proxy for " + li.getEntityName());
/*  430:     */       }
/*  431: 608 */       return li.getImplementation();
/*  432:     */     }
/*  433: 611 */     return maybeProxy;
/*  434:     */   }
/*  435:     */   
/*  436:     */   public Object unproxyAndReassociate(Object maybeProxy)
/*  437:     */     throws HibernateException
/*  438:     */   {
/*  439: 623 */     if ((maybeProxy instanceof ElementWrapper)) {
/*  440: 624 */       maybeProxy = ((ElementWrapper)maybeProxy).getElement();
/*  441:     */     }
/*  442: 627 */     if ((maybeProxy instanceof HibernateProxy))
/*  443:     */     {
/*  444: 628 */       HibernateProxy proxy = (HibernateProxy)maybeProxy;
/*  445: 629 */       LazyInitializer li = proxy.getHibernateLazyInitializer();
/*  446: 630 */       reassociateProxy(li, proxy);
/*  447: 631 */       return li.getImplementation();
/*  448:     */     }
/*  449: 634 */     return maybeProxy;
/*  450:     */   }
/*  451:     */   
/*  452:     */   public void checkUniqueness(EntityKey key, Object object)
/*  453:     */     throws HibernateException
/*  454:     */   {
/*  455: 645 */     Object entity = getEntity(key);
/*  456: 646 */     if (entity == object) {
/*  457: 647 */       throw new AssertionFailure("object already associated, but no entry was found");
/*  458:     */     }
/*  459: 649 */     if (entity != null) {
/*  460: 650 */       throw new NonUniqueObjectException(key.getIdentifier(), key.getEntityName());
/*  461:     */     }
/*  462:     */   }
/*  463:     */   
/*  464:     */   public Object narrowProxy(Object proxy, EntityPersister persister, EntityKey key, Object object)
/*  465:     */     throws HibernateException
/*  466:     */   {
/*  467: 670 */     Class concreteProxyClass = persister.getConcreteProxyClass();
/*  468: 671 */     boolean alreadyNarrow = concreteProxyClass.isAssignableFrom(proxy.getClass());
/*  469: 673 */     if (!alreadyNarrow)
/*  470:     */     {
/*  471: 674 */       LOG.narrowingProxy(concreteProxyClass);
/*  472: 676 */       if (object != null)
/*  473:     */       {
/*  474: 677 */         this.proxiesByKey.remove(key);
/*  475: 678 */         return object;
/*  476:     */       }
/*  477: 681 */       proxy = persister.createProxy(key.getIdentifier(), this.session);
/*  478: 682 */       Object proxyOrig = this.proxiesByKey.put(key, proxy);
/*  479: 683 */       if (proxyOrig != null)
/*  480:     */       {
/*  481: 684 */         if (!(proxyOrig instanceof HibernateProxy)) {
/*  482: 685 */           throw new AssertionFailure("proxy not of type HibernateProxy; it is " + proxyOrig.getClass());
/*  483:     */         }
/*  484: 690 */         boolean readOnlyOrig = ((HibernateProxy)proxyOrig).getHibernateLazyInitializer().isReadOnly();
/*  485: 691 */         ((HibernateProxy)proxy).getHibernateLazyInitializer().setReadOnly(readOnlyOrig);
/*  486:     */       }
/*  487: 693 */       return proxy;
/*  488:     */     }
/*  489: 698 */     if (object != null)
/*  490:     */     {
/*  491: 699 */       LazyInitializer li = ((HibernateProxy)proxy).getHibernateLazyInitializer();
/*  492: 700 */       li.setImplementation(object);
/*  493:     */     }
/*  494: 703 */     return proxy;
/*  495:     */   }
/*  496:     */   
/*  497:     */   public Object proxyFor(EntityPersister persister, EntityKey key, Object impl)
/*  498:     */     throws HibernateException
/*  499:     */   {
/*  500: 716 */     if (!persister.hasProxy()) {
/*  501: 716 */       return impl;
/*  502:     */     }
/*  503: 717 */     Object proxy = this.proxiesByKey.get(key);
/*  504: 718 */     if (proxy != null) {
/*  505: 719 */       return narrowProxy(proxy, persister, key, impl);
/*  506:     */     }
/*  507: 722 */     return impl;
/*  508:     */   }
/*  509:     */   
/*  510:     */   public Object proxyFor(Object impl)
/*  511:     */     throws HibernateException
/*  512:     */   {
/*  513: 732 */     EntityEntry e = getEntry(impl);
/*  514: 733 */     return proxyFor(e.getPersister(), e.getEntityKey(), impl);
/*  515:     */   }
/*  516:     */   
/*  517:     */   public Object getCollectionOwner(Serializable key, CollectionPersister collectionPersister)
/*  518:     */     throws MappingException
/*  519:     */   {
/*  520: 740 */     return getEntity(this.session.generateEntityKey(key, collectionPersister.getOwnerEntityPersister()));
/*  521:     */   }
/*  522:     */   
/*  523:     */   public Object getLoadedCollectionOwnerOrNull(PersistentCollection collection)
/*  524:     */   {
/*  525: 751 */     CollectionEntry ce = getCollectionEntry(collection);
/*  526: 752 */     if (ce.getLoadedPersister() == null) {
/*  527: 753 */       return null;
/*  528:     */     }
/*  529: 755 */     Object loadedOwner = null;
/*  530:     */     
/*  531:     */ 
/*  532: 758 */     Serializable entityId = getLoadedCollectionOwnerIdOrNull(ce);
/*  533: 759 */     if (entityId != null) {
/*  534: 760 */       loadedOwner = getCollectionOwner(entityId, ce.getLoadedPersister());
/*  535:     */     }
/*  536: 762 */     return loadedOwner;
/*  537:     */   }
/*  538:     */   
/*  539:     */   public Serializable getLoadedCollectionOwnerIdOrNull(PersistentCollection collection)
/*  540:     */   {
/*  541: 772 */     return getLoadedCollectionOwnerIdOrNull(getCollectionEntry(collection));
/*  542:     */   }
/*  543:     */   
/*  544:     */   private Serializable getLoadedCollectionOwnerIdOrNull(CollectionEntry ce)
/*  545:     */   {
/*  546: 782 */     if ((ce == null) || (ce.getLoadedKey() == null) || (ce.getLoadedPersister() == null)) {
/*  547: 783 */       return null;
/*  548:     */     }
/*  549: 787 */     return ce.getLoadedPersister().getCollectionType().getIdOfOwnerOrNull(ce.getLoadedKey(), this.session);
/*  550:     */   }
/*  551:     */   
/*  552:     */   public void addUninitializedCollection(CollectionPersister persister, PersistentCollection collection, Serializable id)
/*  553:     */   {
/*  554: 794 */     CollectionEntry ce = new CollectionEntry(collection, persister, id, this.flushing);
/*  555: 795 */     addCollection(collection, ce, id);
/*  556:     */   }
/*  557:     */   
/*  558:     */   public void addUninitializedDetachedCollection(CollectionPersister persister, PersistentCollection collection)
/*  559:     */   {
/*  560: 802 */     CollectionEntry ce = new CollectionEntry(persister, collection.getKey());
/*  561: 803 */     addCollection(collection, ce, collection.getKey());
/*  562:     */   }
/*  563:     */   
/*  564:     */   public void addNewCollection(CollectionPersister persister, PersistentCollection collection)
/*  565:     */     throws HibernateException
/*  566:     */   {
/*  567: 813 */     addCollection(collection, persister);
/*  568:     */   }
/*  569:     */   
/*  570:     */   private void addCollection(PersistentCollection coll, CollectionEntry entry, Serializable key)
/*  571:     */   {
/*  572: 824 */     this.collectionEntries.put(coll, entry);
/*  573: 825 */     CollectionKey collectionKey = new CollectionKey(entry.getLoadedPersister(), key);
/*  574: 826 */     PersistentCollection old = (PersistentCollection)this.collectionsByKey.put(collectionKey, coll);
/*  575: 827 */     if (old != null)
/*  576:     */     {
/*  577: 828 */       if (old == coll) {
/*  578: 829 */         throw new AssertionFailure("bug adding collection twice");
/*  579:     */       }
/*  580: 832 */       old.unsetSession(this.session);
/*  581: 833 */       this.collectionEntries.remove(old);
/*  582:     */     }
/*  583:     */   }
/*  584:     */   
/*  585:     */   private void addCollection(PersistentCollection collection, CollectionPersister persister)
/*  586:     */   {
/*  587: 846 */     CollectionEntry ce = new CollectionEntry(persister, collection);
/*  588: 847 */     this.collectionEntries.put(collection, ce);
/*  589:     */   }
/*  590:     */   
/*  591:     */   public void addInitializedDetachedCollection(CollectionPersister collectionPersister, PersistentCollection collection)
/*  592:     */     throws HibernateException
/*  593:     */   {
/*  594: 856 */     if (collection.isUnreferenced())
/*  595:     */     {
/*  596: 858 */       addCollection(collection, collectionPersister);
/*  597:     */     }
/*  598:     */     else
/*  599:     */     {
/*  600: 861 */       CollectionEntry ce = new CollectionEntry(collection, this.session.getFactory());
/*  601: 862 */       addCollection(collection, ce, collection.getKey());
/*  602:     */     }
/*  603:     */   }
/*  604:     */   
/*  605:     */   public CollectionEntry addInitializedCollection(CollectionPersister persister, PersistentCollection collection, Serializable id)
/*  606:     */     throws HibernateException
/*  607:     */   {
/*  608: 871 */     CollectionEntry ce = new CollectionEntry(collection, persister, id, this.flushing);
/*  609: 872 */     ce.postInitialize(collection);
/*  610: 873 */     addCollection(collection, ce, id);
/*  611: 874 */     return ce;
/*  612:     */   }
/*  613:     */   
/*  614:     */   public PersistentCollection getCollection(CollectionKey collectionKey)
/*  615:     */   {
/*  616: 881 */     return (PersistentCollection)this.collectionsByKey.get(collectionKey);
/*  617:     */   }
/*  618:     */   
/*  619:     */   public void addNonLazyCollection(PersistentCollection collection)
/*  620:     */   {
/*  621: 889 */     this.nonlazyCollections.add(collection);
/*  622:     */   }
/*  623:     */   
/*  624:     */   public void initializeNonLazyCollections()
/*  625:     */     throws HibernateException
/*  626:     */   {
/*  627: 898 */     if (this.loadCounter == 0)
/*  628:     */     {
/*  629: 899 */       LOG.debug("Initializing non-lazy collections");
/*  630:     */       
/*  631: 901 */       this.loadCounter += 1;
/*  632:     */       try
/*  633:     */       {
/*  634:     */         int size;
/*  635: 904 */         while ((size = this.nonlazyCollections.size()) > 0) {
/*  636: 906 */           ((PersistentCollection)this.nonlazyCollections.remove(size - 1)).forceInitialization();
/*  637:     */         }
/*  638:     */       }
/*  639:     */       finally
/*  640:     */       {
/*  641: 910 */         this.loadCounter -= 1;
/*  642: 911 */         clearNullProperties();
/*  643:     */       }
/*  644:     */     }
/*  645:     */   }
/*  646:     */   
/*  647:     */   public PersistentCollection getCollectionHolder(Object array)
/*  648:     */   {
/*  649: 921 */     return (PersistentCollection)this.arrayHolders.get(array);
/*  650:     */   }
/*  651:     */   
/*  652:     */   public void addCollectionHolder(PersistentCollection holder)
/*  653:     */   {
/*  654: 931 */     this.arrayHolders.put(holder.getValue(), holder);
/*  655:     */   }
/*  656:     */   
/*  657:     */   public PersistentCollection removeCollectionHolder(Object array)
/*  658:     */   {
/*  659: 935 */     return (PersistentCollection)this.arrayHolders.remove(array);
/*  660:     */   }
/*  661:     */   
/*  662:     */   public Serializable getSnapshot(PersistentCollection coll)
/*  663:     */   {
/*  664: 942 */     return getCollectionEntry(coll).getSnapshot();
/*  665:     */   }
/*  666:     */   
/*  667:     */   public CollectionEntry getCollectionEntryOrNull(Object collection)
/*  668:     */   {
/*  669:     */     PersistentCollection coll;
/*  670:     */     PersistentCollection coll;
/*  671: 952 */     if ((collection instanceof PersistentCollection))
/*  672:     */     {
/*  673: 953 */       coll = (PersistentCollection)collection;
/*  674:     */     }
/*  675:     */     else
/*  676:     */     {
/*  677: 957 */       coll = getCollectionHolder(collection);
/*  678: 958 */       if (coll == null)
/*  679:     */       {
/*  680: 961 */         Iterator<PersistentCollection> wrappers = this.collectionEntries.keyIterator();
/*  681: 962 */         while (wrappers.hasNext())
/*  682:     */         {
/*  683: 963 */           PersistentCollection pc = (PersistentCollection)wrappers.next();
/*  684: 964 */           if (pc.isWrapper(collection))
/*  685:     */           {
/*  686: 965 */             coll = pc;
/*  687: 966 */             break;
/*  688:     */           }
/*  689:     */         }
/*  690:     */       }
/*  691:     */     }
/*  692: 972 */     return coll == null ? null : getCollectionEntry(coll);
/*  693:     */   }
/*  694:     */   
/*  695:     */   public Object getProxy(EntityKey key)
/*  696:     */   {
/*  697: 979 */     return this.proxiesByKey.get(key);
/*  698:     */   }
/*  699:     */   
/*  700:     */   public void addProxy(EntityKey key, Object proxy)
/*  701:     */   {
/*  702: 986 */     this.proxiesByKey.put(key, proxy);
/*  703:     */   }
/*  704:     */   
/*  705:     */   public Object removeProxy(EntityKey key)
/*  706:     */   {
/*  707: 999 */     if (this.batchFetchQueue != null)
/*  708:     */     {
/*  709:1000 */       this.batchFetchQueue.removeBatchLoadableEntityKey(key);
/*  710:1001 */       this.batchFetchQueue.removeSubselect(key);
/*  711:     */     }
/*  712:1003 */     return this.proxiesByKey.remove(key);
/*  713:     */   }
/*  714:     */   
/*  715:     */   public HashSet getNullifiableEntityKeys()
/*  716:     */   {
/*  717:1032 */     return this.nullifiableEntityKeys;
/*  718:     */   }
/*  719:     */   
/*  720:     */   public Map getEntitiesByKey()
/*  721:     */   {
/*  722:1036 */     return this.entitiesByKey;
/*  723:     */   }
/*  724:     */   
/*  725:     */   public Map getProxiesByKey()
/*  726:     */   {
/*  727:1040 */     return this.proxiesByKey;
/*  728:     */   }
/*  729:     */   
/*  730:     */   public Map getEntityEntries()
/*  731:     */   {
/*  732:1044 */     return this.entityEntries;
/*  733:     */   }
/*  734:     */   
/*  735:     */   public Map getCollectionEntries()
/*  736:     */   {
/*  737:1048 */     return this.collectionEntries;
/*  738:     */   }
/*  739:     */   
/*  740:     */   public Map getCollectionsByKey()
/*  741:     */   {
/*  742:1052 */     return this.collectionsByKey;
/*  743:     */   }
/*  744:     */   
/*  745:     */   public int getCascadeLevel()
/*  746:     */   {
/*  747:1072 */     return this.cascading;
/*  748:     */   }
/*  749:     */   
/*  750:     */   public int incrementCascadeLevel()
/*  751:     */   {
/*  752:1076 */     return ++this.cascading;
/*  753:     */   }
/*  754:     */   
/*  755:     */   public int decrementCascadeLevel()
/*  756:     */   {
/*  757:1080 */     return --this.cascading;
/*  758:     */   }
/*  759:     */   
/*  760:     */   public boolean isFlushing()
/*  761:     */   {
/*  762:1084 */     return this.flushing;
/*  763:     */   }
/*  764:     */   
/*  765:     */   public void setFlushing(boolean flushing)
/*  766:     */   {
/*  767:1088 */     this.flushing = flushing;
/*  768:     */   }
/*  769:     */   
/*  770:     */   public void beforeLoad()
/*  771:     */   {
/*  772:1095 */     this.loadCounter += 1;
/*  773:     */   }
/*  774:     */   
/*  775:     */   public void afterLoad()
/*  776:     */   {
/*  777:1102 */     this.loadCounter -= 1;
/*  778:     */   }
/*  779:     */   
/*  780:     */   public boolean isLoadFinished()
/*  781:     */   {
/*  782:1106 */     return this.loadCounter == 0;
/*  783:     */   }
/*  784:     */   
/*  785:     */   public String toString()
/*  786:     */   {
/*  787:1115 */     return "PersistenceContext[entityKeys=" + this.entitiesByKey.keySet() + ",collectionKeys=" + this.collectionsByKey.keySet() + "]";
/*  788:     */   }
/*  789:     */   
/*  790:     */   public Serializable getOwnerId(String entityName, String propertyName, Object childEntity, Map mergeMap)
/*  791:     */   {
/*  792:1146 */     String collectionRole = entityName + '.' + propertyName;
/*  793:1147 */     EntityPersister persister = this.session.getFactory().getEntityPersister(entityName);
/*  794:1148 */     CollectionPersister collectionPersister = this.session.getFactory().getCollectionPersister(collectionRole);
/*  795:     */     
/*  796:     */ 
/*  797:1151 */     Object parent = this.parentsByChild.get(childEntity);
/*  798:1152 */     if (parent != null)
/*  799:     */     {
/*  800:1153 */       EntityEntry entityEntry = (EntityEntry)this.entityEntries.get(parent);
/*  801:1155 */       if ((persister.isSubclassEntityName(entityEntry.getEntityName())) && (isFoundInParent(propertyName, childEntity, persister, collectionPersister, parent))) {
/*  802:1157 */         return getEntry(parent).getId();
/*  803:     */       }
/*  804:1160 */       this.parentsByChild.remove(childEntity);
/*  805:     */     }
/*  806:1166 */     for (Map.Entry<Object, EntityEntry> me : IdentityMap.concurrentEntries(this.entityEntries))
/*  807:     */     {
/*  808:1167 */       EntityEntry entityEntry = (EntityEntry)me.getValue();
/*  809:1169 */       if (persister.isSubclassEntityName(entityEntry.getEntityName()))
/*  810:     */       {
/*  811:1170 */         Object entityEntryInstance = me.getKey();
/*  812:     */         
/*  813:     */ 
/*  814:1173 */         boolean found = isFoundInParent(propertyName, childEntity, persister, collectionPersister, entityEntryInstance);
/*  815:1181 */         if ((!found) && (mergeMap != null))
/*  816:     */         {
/*  817:1183 */           Object unmergedInstance = mergeMap.get(entityEntryInstance);
/*  818:1184 */           Object unmergedChild = mergeMap.get(childEntity);
/*  819:1185 */           if ((unmergedInstance != null) && (unmergedChild != null)) {
/*  820:1186 */             found = isFoundInParent(propertyName, unmergedChild, persister, collectionPersister, unmergedInstance);
/*  821:     */           }
/*  822:     */         }
/*  823:1196 */         if (found) {
/*  824:1197 */           return entityEntry.getId();
/*  825:     */         }
/*  826:     */       }
/*  827:     */     }
/*  828:1206 */     if (mergeMap != null)
/*  829:     */     {
/*  830:1207 */       Iterator mergeMapItr = mergeMap.entrySet().iterator();
/*  831:1208 */       while (mergeMapItr.hasNext())
/*  832:     */       {
/*  833:1209 */         Map.Entry mergeMapEntry = (Map.Entry)mergeMapItr.next();
/*  834:1210 */         if ((mergeMapEntry.getKey() instanceof HibernateProxy))
/*  835:     */         {
/*  836:1211 */           HibernateProxy proxy = (HibernateProxy)mergeMapEntry.getKey();
/*  837:1212 */           if (persister.isSubclassEntityName(proxy.getHibernateLazyInitializer().getEntityName()))
/*  838:     */           {
/*  839:1213 */             boolean found = isFoundInParent(propertyName, childEntity, persister, collectionPersister, mergeMap.get(proxy));
/*  840:1220 */             if (!found) {
/*  841:1221 */               found = isFoundInParent(propertyName, mergeMap.get(childEntity), persister, collectionPersister, mergeMap.get(proxy));
/*  842:     */             }
/*  843:1229 */             if (found) {
/*  844:1230 */               return proxy.getHibernateLazyInitializer().getIdentifier();
/*  845:     */             }
/*  846:     */           }
/*  847:     */         }
/*  848:     */       }
/*  849:     */     }
/*  850:1237 */     return null;
/*  851:     */   }
/*  852:     */   
/*  853:     */   private boolean isFoundInParent(String property, Object childEntity, EntityPersister persister, CollectionPersister collectionPersister, Object potentialParent)
/*  854:     */   {
/*  855:1246 */     Object collection = persister.getPropertyValue(potentialParent, property);
/*  856:1247 */     return (collection != null) && (Hibernate.isInitialized(collection)) && (collectionPersister.getCollectionType().contains(collection, childEntity, this.session));
/*  857:     */   }
/*  858:     */   
/*  859:     */   public Object getIndexInOwner(String entity, String property, Object childEntity, Map mergeMap)
/*  860:     */   {
/*  861:1258 */     EntityPersister persister = this.session.getFactory().getEntityPersister(entity);
/*  862:     */     
/*  863:1260 */     CollectionPersister cp = this.session.getFactory().getCollectionPersister(entity + '.' + property);
/*  864:     */     
/*  865:     */ 
/*  866:     */ 
/*  867:1264 */     Object parent = this.parentsByChild.get(childEntity);
/*  868:1265 */     if (parent != null)
/*  869:     */     {
/*  870:1266 */       EntityEntry entityEntry = (EntityEntry)this.entityEntries.get(parent);
/*  871:1268 */       if (persister.isSubclassEntityName(entityEntry.getEntityName()))
/*  872:     */       {
/*  873:1269 */         Object index = getIndexInParent(property, childEntity, persister, cp, parent);
/*  874:1271 */         if ((index == null) && (mergeMap != null))
/*  875:     */         {
/*  876:1272 */           Object unmergedInstance = mergeMap.get(parent);
/*  877:1273 */           Object unmergedChild = mergeMap.get(childEntity);
/*  878:1274 */           if ((unmergedInstance != null) && (unmergedChild != null)) {
/*  879:1275 */             index = getIndexInParent(property, unmergedChild, persister, cp, unmergedInstance);
/*  880:     */           }
/*  881:     */         }
/*  882:1278 */         if (index != null) {
/*  883:1279 */           return index;
/*  884:     */         }
/*  885:     */       }
/*  886:     */       else
/*  887:     */       {
/*  888:1283 */         this.parentsByChild.remove(childEntity);
/*  889:     */       }
/*  890:     */     }
/*  891:1288 */     for (Map.Entry<Object, EntityEntry> me : IdentityMap.concurrentEntries(this.entityEntries))
/*  892:     */     {
/*  893:1289 */       EntityEntry ee = (EntityEntry)me.getValue();
/*  894:1290 */       if (persister.isSubclassEntityName(ee.getEntityName()))
/*  895:     */       {
/*  896:1291 */         Object instance = me.getKey();
/*  897:     */         
/*  898:1293 */         Object index = getIndexInParent(property, childEntity, persister, cp, instance);
/*  899:1295 */         if ((index == null) && (mergeMap != null))
/*  900:     */         {
/*  901:1296 */           Object unmergedInstance = mergeMap.get(instance);
/*  902:1297 */           Object unmergedChild = mergeMap.get(childEntity);
/*  903:1298 */           if ((unmergedInstance != null) && (unmergedChild != null)) {
/*  904:1299 */             index = getIndexInParent(property, unmergedChild, persister, cp, unmergedInstance);
/*  905:     */           }
/*  906:     */         }
/*  907:1303 */         if (index != null) {
/*  908:1303 */           return index;
/*  909:     */         }
/*  910:     */       }
/*  911:     */     }
/*  912:1306 */     return null;
/*  913:     */   }
/*  914:     */   
/*  915:     */   private Object getIndexInParent(String property, Object childEntity, EntityPersister persister, CollectionPersister collectionPersister, Object potentialParent)
/*  916:     */   {
/*  917:1316 */     Object collection = persister.getPropertyValue(potentialParent, property);
/*  918:1317 */     if ((collection != null) && (Hibernate.isInitialized(collection))) {
/*  919:1318 */       return collectionPersister.getCollectionType().indexOf(collection, childEntity);
/*  920:     */     }
/*  921:1321 */     return null;
/*  922:     */   }
/*  923:     */   
/*  924:     */   public void addNullProperty(EntityKey ownerKey, String propertyName)
/*  925:     */   {
/*  926:1330 */     this.nullAssociations.add(new AssociationKey(ownerKey, propertyName));
/*  927:     */   }
/*  928:     */   
/*  929:     */   public boolean isPropertyNull(EntityKey ownerKey, String propertyName)
/*  930:     */   {
/*  931:1337 */     return this.nullAssociations.contains(new AssociationKey(ownerKey, propertyName));
/*  932:     */   }
/*  933:     */   
/*  934:     */   private void clearNullProperties()
/*  935:     */   {
/*  936:1341 */     this.nullAssociations.clear();
/*  937:     */   }
/*  938:     */   
/*  939:     */   public boolean isReadOnly(Object entityOrProxy)
/*  940:     */   {
/*  941:1345 */     if (entityOrProxy == null) {
/*  942:1346 */       throw new AssertionFailure("object must be non-null.");
/*  943:     */     }
/*  944:     */     boolean isReadOnly;
/*  945:     */     boolean isReadOnly;
/*  946:1349 */     if ((entityOrProxy instanceof HibernateProxy))
/*  947:     */     {
/*  948:1350 */       isReadOnly = ((HibernateProxy)entityOrProxy).getHibernateLazyInitializer().isReadOnly();
/*  949:     */     }
/*  950:     */     else
/*  951:     */     {
/*  952:1353 */       EntityEntry ee = getEntry(entityOrProxy);
/*  953:1354 */       if (ee == null) {
/*  954:1355 */         throw new TransientObjectException("Instance was not associated with this persistence context");
/*  955:     */       }
/*  956:1357 */       isReadOnly = ee.isReadOnly();
/*  957:     */     }
/*  958:1359 */     return isReadOnly;
/*  959:     */   }
/*  960:     */   
/*  961:     */   public void setReadOnly(Object object, boolean readOnly)
/*  962:     */   {
/*  963:1363 */     if (object == null) {
/*  964:1364 */       throw new AssertionFailure("object must be non-null.");
/*  965:     */     }
/*  966:1366 */     if (isReadOnly(object) == readOnly) {
/*  967:1367 */       return;
/*  968:     */     }
/*  969:1369 */     if ((object instanceof HibernateProxy))
/*  970:     */     {
/*  971:1370 */       HibernateProxy proxy = (HibernateProxy)object;
/*  972:1371 */       setProxyReadOnly(proxy, readOnly);
/*  973:1372 */       if (Hibernate.isInitialized(proxy)) {
/*  974:1373 */         setEntityReadOnly(proxy.getHibernateLazyInitializer().getImplementation(), readOnly);
/*  975:     */       }
/*  976:     */     }
/*  977:     */     else
/*  978:     */     {
/*  979:1380 */       setEntityReadOnly(object, readOnly);
/*  980:     */       
/*  981:     */ 
/*  982:1383 */       Object maybeProxy = getSession().getPersistenceContext().proxyFor(object);
/*  983:1384 */       if ((maybeProxy instanceof HibernateProxy)) {
/*  984:1385 */         setProxyReadOnly((HibernateProxy)maybeProxy, readOnly);
/*  985:     */       }
/*  986:     */     }
/*  987:     */   }
/*  988:     */   
/*  989:     */   private void setProxyReadOnly(HibernateProxy proxy, boolean readOnly)
/*  990:     */   {
/*  991:1391 */     if (proxy.getHibernateLazyInitializer().getSession() != getSession()) {
/*  992:1392 */       throw new AssertionFailure("Attempt to set a proxy to read-only that is associated with a different session");
/*  993:     */     }
/*  994:1395 */     proxy.getHibernateLazyInitializer().setReadOnly(readOnly);
/*  995:     */   }
/*  996:     */   
/*  997:     */   private void setEntityReadOnly(Object entity, boolean readOnly)
/*  998:     */   {
/*  999:1399 */     EntityEntry entry = getEntry(entity);
/* 1000:1400 */     if (entry == null) {
/* 1001:1401 */       throw new TransientObjectException("Instance was not associated with this persistence context");
/* 1002:     */     }
/* 1003:1403 */     entry.setReadOnly(readOnly, entity);
/* 1004:1404 */     this.hasNonReadOnlyEntities = ((this.hasNonReadOnlyEntities) || (!readOnly));
/* 1005:     */   }
/* 1006:     */   
/* 1007:     */   public void replaceDelayedEntityIdentityInsertKeys(EntityKey oldKey, Serializable generatedId)
/* 1008:     */   {
/* 1009:1408 */     Object entity = this.entitiesByKey.remove(oldKey);
/* 1010:1409 */     EntityEntry oldEntry = (EntityEntry)this.entityEntries.remove(entity);
/* 1011:1410 */     this.parentsByChild.clear();
/* 1012:     */     
/* 1013:1412 */     EntityKey newKey = this.session.generateEntityKey(generatedId, oldEntry.getPersister());
/* 1014:1413 */     addEntity(newKey, entity);
/* 1015:1414 */     addEntry(entity, oldEntry.getStatus(), oldEntry.getLoadedState(), oldEntry.getRowId(), generatedId, oldEntry.getVersion(), oldEntry.getLockMode(), oldEntry.isExistsInDatabase(), oldEntry.getPersister(), oldEntry.isBeingReplicated(), oldEntry.isLoadedWithLazyPropertiesUnfetched());
/* 1016:     */   }
/* 1017:     */   
/* 1018:     */   public void serialize(ObjectOutputStream oos)
/* 1019:     */     throws IOException
/* 1020:     */   {
/* 1021:1437 */     boolean tracing = LOG.isTraceEnabled();
/* 1022:1438 */     if (tracing) {
/* 1023:1438 */       LOG.trace("Serializing persistent-context");
/* 1024:     */     }
/* 1025:1440 */     oos.writeBoolean(this.defaultReadOnly);
/* 1026:1441 */     oos.writeBoolean(this.hasNonReadOnlyEntities);
/* 1027:     */     
/* 1028:1443 */     oos.writeInt(this.entitiesByKey.size());
/* 1029:1444 */     if (tracing) {
/* 1030:1444 */       LOG.trace("Starting serialization of [" + this.entitiesByKey.size() + "] entitiesByKey entries");
/* 1031:     */     }
/* 1032:1445 */     Iterator itr = this.entitiesByKey.entrySet().iterator();
/* 1033:1446 */     while (itr.hasNext())
/* 1034:     */     {
/* 1035:1447 */       Map.Entry entry = (Map.Entry)itr.next();
/* 1036:1448 */       ((EntityKey)entry.getKey()).serialize(oos);
/* 1037:1449 */       oos.writeObject(entry.getValue());
/* 1038:     */     }
/* 1039:1452 */     oos.writeInt(this.entitiesByUniqueKey.size());
/* 1040:1453 */     if (tracing) {
/* 1041:1453 */       LOG.trace("Starting serialization of [" + this.entitiesByUniqueKey.size() + "] entitiesByUniqueKey entries");
/* 1042:     */     }
/* 1043:1454 */     itr = this.entitiesByUniqueKey.entrySet().iterator();
/* 1044:1455 */     while (itr.hasNext())
/* 1045:     */     {
/* 1046:1456 */       Map.Entry entry = (Map.Entry)itr.next();
/* 1047:1457 */       ((EntityUniqueKey)entry.getKey()).serialize(oos);
/* 1048:1458 */       oos.writeObject(entry.getValue());
/* 1049:     */     }
/* 1050:1461 */     oos.writeInt(this.proxiesByKey.size());
/* 1051:1462 */     if (tracing) {
/* 1052:1462 */       LOG.trace("Starting serialization of [" + this.proxiesByKey.size() + "] proxiesByKey entries");
/* 1053:     */     }
/* 1054:1463 */     itr = this.proxiesByKey.entrySet().iterator();
/* 1055:1464 */     while (itr.hasNext())
/* 1056:     */     {
/* 1057:1465 */       Map.Entry entry = (Map.Entry)itr.next();
/* 1058:1466 */       ((EntityKey)entry.getKey()).serialize(oos);
/* 1059:1467 */       oos.writeObject(entry.getValue());
/* 1060:     */     }
/* 1061:1470 */     oos.writeInt(this.entitySnapshotsByKey.size());
/* 1062:1471 */     if (tracing) {
/* 1063:1471 */       LOG.trace("Starting serialization of [" + this.entitySnapshotsByKey.size() + "] entitySnapshotsByKey entries");
/* 1064:     */     }
/* 1065:1472 */     itr = this.entitySnapshotsByKey.entrySet().iterator();
/* 1066:1473 */     while (itr.hasNext())
/* 1067:     */     {
/* 1068:1474 */       Map.Entry entry = (Map.Entry)itr.next();
/* 1069:1475 */       ((EntityKey)entry.getKey()).serialize(oos);
/* 1070:1476 */       oos.writeObject(entry.getValue());
/* 1071:     */     }
/* 1072:1479 */     oos.writeInt(this.entityEntries.size());
/* 1073:1480 */     if (tracing) {
/* 1074:1480 */       LOG.trace("Starting serialization of [" + this.entityEntries.size() + "] entityEntries entries");
/* 1075:     */     }
/* 1076:1481 */     itr = this.entityEntries.entrySet().iterator();
/* 1077:1482 */     while (itr.hasNext())
/* 1078:     */     {
/* 1079:1483 */       Map.Entry entry = (Map.Entry)itr.next();
/* 1080:1484 */       oos.writeObject(entry.getKey());
/* 1081:1485 */       ((EntityEntry)entry.getValue()).serialize(oos);
/* 1082:     */     }
/* 1083:1488 */     oos.writeInt(this.collectionsByKey.size());
/* 1084:1489 */     if (tracing) {
/* 1085:1489 */       LOG.trace("Starting serialization of [" + this.collectionsByKey.size() + "] collectionsByKey entries");
/* 1086:     */     }
/* 1087:1490 */     itr = this.collectionsByKey.entrySet().iterator();
/* 1088:1491 */     while (itr.hasNext())
/* 1089:     */     {
/* 1090:1492 */       Map.Entry entry = (Map.Entry)itr.next();
/* 1091:1493 */       ((CollectionKey)entry.getKey()).serialize(oos);
/* 1092:1494 */       oos.writeObject(entry.getValue());
/* 1093:     */     }
/* 1094:1497 */     oos.writeInt(this.collectionEntries.size());
/* 1095:1498 */     if (tracing) {
/* 1096:1498 */       LOG.trace("Starting serialization of [" + this.collectionEntries.size() + "] collectionEntries entries");
/* 1097:     */     }
/* 1098:1499 */     itr = this.collectionEntries.entrySet().iterator();
/* 1099:1500 */     while (itr.hasNext())
/* 1100:     */     {
/* 1101:1501 */       Map.Entry entry = (Map.Entry)itr.next();
/* 1102:1502 */       oos.writeObject(entry.getKey());
/* 1103:1503 */       ((CollectionEntry)entry.getValue()).serialize(oos);
/* 1104:     */     }
/* 1105:1506 */     oos.writeInt(this.arrayHolders.size());
/* 1106:1507 */     if (tracing) {
/* 1107:1507 */       LOG.trace("Starting serialization of [" + this.arrayHolders.size() + "] arrayHolders entries");
/* 1108:     */     }
/* 1109:1508 */     itr = this.arrayHolders.entrySet().iterator();
/* 1110:1509 */     while (itr.hasNext())
/* 1111:     */     {
/* 1112:1510 */       Map.Entry entry = (Map.Entry)itr.next();
/* 1113:1511 */       oos.writeObject(entry.getKey());
/* 1114:1512 */       oos.writeObject(entry.getValue());
/* 1115:     */     }
/* 1116:1515 */     oos.writeInt(this.nullifiableEntityKeys.size());
/* 1117:1516 */     if (tracing) {
/* 1118:1516 */       LOG.trace("Starting serialization of [" + this.nullifiableEntityKeys.size() + "] nullifiableEntityKey entries");
/* 1119:     */     }
/* 1120:1517 */     Iterator<EntityKey> entityKeyIterator = this.nullifiableEntityKeys.iterator();
/* 1121:1518 */     while (entityKeyIterator.hasNext())
/* 1122:     */     {
/* 1123:1519 */       EntityKey entry = (EntityKey)entityKeyIterator.next();
/* 1124:1520 */       entry.serialize(oos);
/* 1125:     */     }
/* 1126:     */   }
/* 1127:     */   
/* 1128:     */   public static StatefulPersistenceContext deserialize(ObjectInputStream ois, SessionImplementor session)
/* 1129:     */     throws IOException, ClassNotFoundException
/* 1130:     */   {
/* 1131:1527 */     boolean tracing = LOG.isTraceEnabled();
/* 1132:1528 */     if (tracing) {
/* 1133:1528 */       LOG.trace("Serializing persistent-context");
/* 1134:     */     }
/* 1135:1529 */     StatefulPersistenceContext rtn = new StatefulPersistenceContext(session);
/* 1136:     */     try
/* 1137:     */     {
/* 1138:1537 */       rtn.defaultReadOnly = ois.readBoolean();
/* 1139:     */       
/* 1140:1539 */       rtn.hasNonReadOnlyEntities = ois.readBoolean();
/* 1141:     */       
/* 1142:1541 */       int count = ois.readInt();
/* 1143:1542 */       if (tracing) {
/* 1144:1542 */         LOG.trace("Starting deserialization of [" + count + "] entitiesByKey entries");
/* 1145:     */       }
/* 1146:1543 */       rtn.entitiesByKey = new HashMap(count < 8 ? 8 : count);
/* 1147:1544 */       for (int i = 0; i < count; i++) {
/* 1148:1545 */         rtn.entitiesByKey.put(EntityKey.deserialize(ois, session), ois.readObject());
/* 1149:     */       }
/* 1150:1548 */       count = ois.readInt();
/* 1151:1549 */       if (tracing) {
/* 1152:1549 */         LOG.trace("Starting deserialization of [" + count + "] entitiesByUniqueKey entries");
/* 1153:     */       }
/* 1154:1550 */       rtn.entitiesByUniqueKey = new HashMap(count < 8 ? 8 : count);
/* 1155:1551 */       for (int i = 0; i < count; i++) {
/* 1156:1552 */         rtn.entitiesByUniqueKey.put(EntityUniqueKey.deserialize(ois, session), ois.readObject());
/* 1157:     */       }
/* 1158:1555 */       count = ois.readInt();
/* 1159:1556 */       if (tracing) {
/* 1160:1556 */         LOG.trace("Starting deserialization of [" + count + "] proxiesByKey entries");
/* 1161:     */       }
/* 1162:1557 */       rtn.proxiesByKey = new ReferenceMap(0, 2, count < 8 ? 8 : count, 0.75F);
/* 1163:1558 */       for (int i = 0; i < count; i++)
/* 1164:     */       {
/* 1165:1559 */         EntityKey ek = EntityKey.deserialize(ois, session);
/* 1166:1560 */         Object proxy = ois.readObject();
/* 1167:1561 */         if ((proxy instanceof HibernateProxy))
/* 1168:     */         {
/* 1169:1562 */           ((HibernateProxy)proxy).getHibernateLazyInitializer().setSession(session);
/* 1170:1563 */           rtn.proxiesByKey.put(ek, proxy);
/* 1171:     */         }
/* 1172:1565 */         else if (tracing)
/* 1173:     */         {
/* 1174:1565 */           LOG.trace("Encountered prunded proxy");
/* 1175:     */         }
/* 1176:     */       }
/* 1177:1570 */       count = ois.readInt();
/* 1178:1571 */       if (tracing) {
/* 1179:1571 */         LOG.trace("Starting deserialization of [" + count + "] entitySnapshotsByKey entries");
/* 1180:     */       }
/* 1181:1572 */       rtn.entitySnapshotsByKey = new HashMap(count < 8 ? 8 : count);
/* 1182:1573 */       for (int i = 0; i < count; i++) {
/* 1183:1574 */         rtn.entitySnapshotsByKey.put(EntityKey.deserialize(ois, session), ois.readObject());
/* 1184:     */       }
/* 1185:1577 */       count = ois.readInt();
/* 1186:1578 */       if (tracing) {
/* 1187:1578 */         LOG.trace("Starting deserialization of [" + count + "] entityEntries entries");
/* 1188:     */       }
/* 1189:1579 */       rtn.entityEntries = IdentityMap.instantiateSequenced(count < 8 ? 8 : count);
/* 1190:1580 */       for (int i = 0; i < count; i++)
/* 1191:     */       {
/* 1192:1581 */         Object entity = ois.readObject();
/* 1193:1582 */         EntityEntry entry = EntityEntry.deserialize(ois, session);
/* 1194:1583 */         rtn.entityEntries.put(entity, entry);
/* 1195:     */       }
/* 1196:1586 */       count = ois.readInt();
/* 1197:1587 */       if (tracing) {
/* 1198:1587 */         LOG.trace("Starting deserialization of [" + count + "] collectionsByKey entries");
/* 1199:     */       }
/* 1200:1588 */       rtn.collectionsByKey = new HashMap(count < 8 ? 8 : count);
/* 1201:1589 */       for (int i = 0; i < count; i++) {
/* 1202:1590 */         rtn.collectionsByKey.put(CollectionKey.deserialize(ois, session), (PersistentCollection)ois.readObject());
/* 1203:     */       }
/* 1204:1593 */       count = ois.readInt();
/* 1205:1594 */       if (tracing) {
/* 1206:1594 */         LOG.trace("Starting deserialization of [" + count + "] collectionEntries entries");
/* 1207:     */       }
/* 1208:1595 */       rtn.collectionEntries = IdentityMap.instantiateSequenced(count < 8 ? 8 : count);
/* 1209:1596 */       for (int i = 0; i < count; i++)
/* 1210:     */       {
/* 1211:1597 */         PersistentCollection pc = (PersistentCollection)ois.readObject();
/* 1212:1598 */         CollectionEntry ce = CollectionEntry.deserialize(ois, session);
/* 1213:1599 */         pc.setCurrentSession(session);
/* 1214:1600 */         rtn.collectionEntries.put(pc, ce);
/* 1215:     */       }
/* 1216:1603 */       count = ois.readInt();
/* 1217:1604 */       if (tracing) {
/* 1218:1604 */         LOG.trace("Starting deserialization of [" + count + "] arrayHolders entries");
/* 1219:     */       }
/* 1220:1605 */       rtn.arrayHolders = new IdentityHashMap(count < 8 ? 8 : count);
/* 1221:1606 */       for (int i = 0; i < count; i++) {
/* 1222:1607 */         rtn.arrayHolders.put(ois.readObject(), (PersistentCollection)ois.readObject());
/* 1223:     */       }
/* 1224:1610 */       count = ois.readInt();
/* 1225:1611 */       if (tracing) {
/* 1226:1611 */         LOG.trace("Starting deserialization of [" + count + "] nullifiableEntityKey entries");
/* 1227:     */       }
/* 1228:1612 */       rtn.nullifiableEntityKeys = new HashSet();
/* 1229:1613 */       for (int i = 0; i < count; i++) {
/* 1230:1614 */         rtn.nullifiableEntityKeys.add(EntityKey.deserialize(ois, session));
/* 1231:     */       }
/* 1232:     */     }
/* 1233:     */     catch (HibernateException he)
/* 1234:     */     {
/* 1235:1619 */       throw new InvalidObjectException(he.getMessage());
/* 1236:     */     }
/* 1237:1622 */     return rtn;
/* 1238:     */   }
/* 1239:     */   
/* 1240:     */   public void addChildParent(Object child, Object parent)
/* 1241:     */   {
/* 1242:1629 */     this.parentsByChild.put(child, parent);
/* 1243:     */   }
/* 1244:     */   
/* 1245:     */   public void removeChildParent(Object child)
/* 1246:     */   {
/* 1247:1636 */     this.parentsByChild.remove(child);
/* 1248:     */   }
/* 1249:     */   
/* 1250:     */   public void registerInsertedKey(EntityPersister persister, Serializable id)
/* 1251:     */   {
/* 1252:1647 */     if (persister.hasCache())
/* 1253:     */     {
/* 1254:1648 */       if (this.insertedKeysMap == null) {
/* 1255:1649 */         this.insertedKeysMap = new HashMap();
/* 1256:     */       }
/* 1257:1651 */       String rootEntityName = persister.getRootEntityName();
/* 1258:1652 */       List<Serializable> insertedEntityIds = (List)this.insertedKeysMap.get(rootEntityName);
/* 1259:1653 */       if (insertedEntityIds == null)
/* 1260:     */       {
/* 1261:1654 */         insertedEntityIds = new ArrayList();
/* 1262:1655 */         this.insertedKeysMap.put(rootEntityName, insertedEntityIds);
/* 1263:     */       }
/* 1264:1657 */       insertedEntityIds.add(id);
/* 1265:     */     }
/* 1266:     */   }
/* 1267:     */   
/* 1268:     */   public boolean wasInsertedDuringTransaction(EntityPersister persister, Serializable id)
/* 1269:     */   {
/* 1270:1666 */     if ((persister.hasCache()) && 
/* 1271:1667 */       (this.insertedKeysMap != null))
/* 1272:     */     {
/* 1273:1668 */       List<Serializable> insertedEntityIds = (List)this.insertedKeysMap.get(persister.getRootEntityName());
/* 1274:1669 */       if (insertedEntityIds != null) {
/* 1275:1670 */         return insertedEntityIds.contains(id);
/* 1276:     */       }
/* 1277:     */     }
/* 1278:1674 */     return false;
/* 1279:     */   }
/* 1280:     */   
/* 1281:     */   private void cleanUpInsertedKeysAfterTransaction()
/* 1282:     */   {
/* 1283:1678 */     if (this.insertedKeysMap != null) {
/* 1284:1679 */       this.insertedKeysMap.clear();
/* 1285:     */     }
/* 1286:     */   }
/* 1287:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.internal.StatefulPersistenceContext
 * JD-Core Version:    0.7.0.1
 */