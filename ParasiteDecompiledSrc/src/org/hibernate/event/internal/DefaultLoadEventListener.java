/*   1:    */ package org.hibernate.event.internal;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.hibernate.CacheMode;
/*   5:    */ import org.hibernate.HibernateException;
/*   6:    */ import org.hibernate.LockMode;
/*   7:    */ import org.hibernate.NonUniqueObjectException;
/*   8:    */ import org.hibernate.PersistentObjectException;
/*   9:    */ import org.hibernate.TypeMismatchException;
/*  10:    */ import org.hibernate.cache.spi.CacheKey;
/*  11:    */ import org.hibernate.cache.spi.EntityRegion;
/*  12:    */ import org.hibernate.cache.spi.access.EntityRegionAccessStrategy;
/*  13:    */ import org.hibernate.cache.spi.access.SoftLock;
/*  14:    */ import org.hibernate.cache.spi.entry.CacheEntry;
/*  15:    */ import org.hibernate.cache.spi.entry.CacheEntryStructure;
/*  16:    */ import org.hibernate.engine.internal.TwoPhaseLoad;
/*  17:    */ import org.hibernate.engine.internal.Versioning;
/*  18:    */ import org.hibernate.engine.spi.BatchFetchQueue;
/*  19:    */ import org.hibernate.engine.spi.EntityEntry;
/*  20:    */ import org.hibernate.engine.spi.EntityKey;
/*  21:    */ import org.hibernate.engine.spi.PersistenceContext;
/*  22:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  23:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  24:    */ import org.hibernate.engine.spi.Status;
/*  25:    */ import org.hibernate.event.service.spi.EventListenerGroup;
/*  26:    */ import org.hibernate.event.service.spi.EventListenerRegistry;
/*  27:    */ import org.hibernate.event.spi.EventSource;
/*  28:    */ import org.hibernate.event.spi.EventType;
/*  29:    */ import org.hibernate.event.spi.LoadEvent;
/*  30:    */ import org.hibernate.event.spi.LoadEventListener;
/*  31:    */ import org.hibernate.event.spi.LoadEventListener.LoadType;
/*  32:    */ import org.hibernate.event.spi.PostLoadEvent;
/*  33:    */ import org.hibernate.event.spi.PostLoadEventListener;
/*  34:    */ import org.hibernate.internal.CoreMessageLogger;
/*  35:    */ import org.hibernate.persister.entity.EntityPersister;
/*  36:    */ import org.hibernate.pretty.MessageHelper;
/*  37:    */ import org.hibernate.proxy.EntityNotFoundDelegate;
/*  38:    */ import org.hibernate.proxy.HibernateProxy;
/*  39:    */ import org.hibernate.proxy.LazyInitializer;
/*  40:    */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  41:    */ import org.hibernate.stat.Statistics;
/*  42:    */ import org.hibernate.stat.spi.StatisticsImplementor;
/*  43:    */ import org.hibernate.tuple.IdentifierProperty;
/*  44:    */ import org.hibernate.tuple.entity.EntityMetamodel;
/*  45:    */ import org.hibernate.type.EmbeddedComponentType;
/*  46:    */ import org.hibernate.type.EntityType;
/*  47:    */ import org.hibernate.type.Type;
/*  48:    */ import org.hibernate.type.TypeHelper;
/*  49:    */ import org.jboss.logging.Logger;
/*  50:    */ 
/*  51:    */ public class DefaultLoadEventListener
/*  52:    */   extends AbstractLockUpgradeEventListener
/*  53:    */   implements LoadEventListener
/*  54:    */ {
/*  55: 71 */   public static final Object REMOVED_ENTITY_MARKER = new Object();
/*  56: 72 */   public static final Object INCONSISTENT_RTN_CLASS_MARKER = new Object();
/*  57: 73 */   public static final LockMode DEFAULT_LOCK_MODE = LockMode.NONE;
/*  58: 75 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, DefaultLoadEventListener.class.getName());
/*  59:    */   
/*  60:    */   public void onLoad(LoadEvent event, LoadEventListener.LoadType loadType)
/*  61:    */     throws HibernateException
/*  62:    */   {
/*  63: 87 */     SessionImplementor source = event.getSession();
/*  64:    */     EntityPersister persister;
/*  65: 90 */     if (event.getInstanceToLoad() != null)
/*  66:    */     {
/*  67: 91 */       EntityPersister persister = source.getEntityPersister(null, event.getInstanceToLoad());
/*  68: 92 */       event.setEntityClassName(event.getInstanceToLoad().getClass().getName());
/*  69:    */     }
/*  70:    */     else
/*  71:    */     {
/*  72: 95 */       persister = source.getFactory().getEntityPersister(event.getEntityClassName());
/*  73:    */     }
/*  74: 98 */     if (persister == null) {
/*  75: 99 */       throw new HibernateException("Unable to locate persister: " + event.getEntityClassName());
/*  76:    */     }
/*  77:105 */     Class idClass = persister.getIdentifierType().getReturnedClass();
/*  78:106 */     if ((idClass != null) && (!idClass.isInstance(event.getEntityId())))
/*  79:    */     {
/*  80:110 */       if (persister.getEntityMetamodel().getIdentifierProperty().isEmbedded())
/*  81:    */       {
/*  82:111 */         EmbeddedComponentType dependentIdType = (EmbeddedComponentType)persister.getEntityMetamodel().getIdentifierProperty().getType();
/*  83:113 */         if (dependentIdType.getSubtypes().length == 1)
/*  84:    */         {
/*  85:114 */           Type singleSubType = dependentIdType.getSubtypes()[0];
/*  86:115 */           if (singleSubType.isEntityType())
/*  87:    */           {
/*  88:116 */             EntityType dependentParentType = (EntityType)singleSubType;
/*  89:117 */             Type dependentParentIdType = dependentParentType.getIdentifierOrUniqueKeyType(source.getFactory());
/*  90:118 */             if (dependentParentIdType.getReturnedClass().isInstance(event.getEntityId()))
/*  91:    */             {
/*  92:120 */               loadByDerivedIdentitySimplePkValue(event, loadType, persister, dependentIdType, source.getFactory().getEntityPersister(dependentParentType.getAssociatedEntityName()));
/*  93:    */               
/*  94:    */ 
/*  95:    */ 
/*  96:    */ 
/*  97:    */ 
/*  98:    */ 
/*  99:127 */               return;
/* 100:    */             }
/* 101:    */           }
/* 102:    */         }
/* 103:    */       }
/* 104:132 */       throw new TypeMismatchException("Provided id of the wrong type for class " + persister.getEntityName() + ". Expected: " + idClass + ", got " + event.getEntityId().getClass());
/* 105:    */     }
/* 106:137 */     EntityKey keyToLoad = source.generateEntityKey(event.getEntityId(), persister);
/* 107:    */     try
/* 108:    */     {
/* 109:140 */       if (loadType.isNakedEntityReturned()) {
/* 110:143 */         event.setResult(load(event, persister, keyToLoad, loadType));
/* 111:147 */       } else if (event.getLockMode() == LockMode.NONE) {
/* 112:148 */         event.setResult(proxyOrLoad(event, persister, keyToLoad, loadType));
/* 113:    */       } else {
/* 114:151 */         event.setResult(lockAndLoad(event, persister, keyToLoad, loadType, source));
/* 115:    */       }
/* 116:    */     }
/* 117:    */     catch (HibernateException e)
/* 118:    */     {
/* 119:156 */       LOG.unableToLoadCommand(e);
/* 120:157 */       throw e;
/* 121:    */     }
/* 122:    */   }
/* 123:    */   
/* 124:    */   private void loadByDerivedIdentitySimplePkValue(LoadEvent event, LoadEventListener.LoadType options, EntityPersister dependentPersister, EmbeddedComponentType dependentIdType, EntityPersister parentPersister)
/* 125:    */   {
/* 126:167 */     EntityKey parentEntityKey = event.getSession().generateEntityKey(event.getEntityId(), parentPersister);
/* 127:168 */     Object parent = doLoad(event, parentPersister, parentEntityKey, options);
/* 128:    */     
/* 129:170 */     Serializable dependent = (Serializable)dependentIdType.instantiate(parent, event.getSession());
/* 130:171 */     dependentIdType.setPropertyValues(dependent, new Object[] { parent }, dependentPersister.getEntityMode());
/* 131:172 */     EntityKey dependentEntityKey = event.getSession().generateEntityKey(dependent, dependentPersister);
/* 132:173 */     event.setEntityId(dependent);
/* 133:    */     
/* 134:175 */     event.setResult(doLoad(event, dependentPersister, dependentEntityKey, options));
/* 135:    */   }
/* 136:    */   
/* 137:    */   protected Object load(LoadEvent event, EntityPersister persister, EntityKey keyToLoad, LoadEventListener.LoadType options)
/* 138:    */   {
/* 139:194 */     if (event.getInstanceToLoad() != null)
/* 140:    */     {
/* 141:195 */       if (event.getSession().getPersistenceContext().getEntry(event.getInstanceToLoad()) != null) {
/* 142:196 */         throw new PersistentObjectException("attempted to load into an instance that was already associated with the session: " + MessageHelper.infoString(persister, event.getEntityId(), event.getSession().getFactory()));
/* 143:    */       }
/* 144:201 */       persister.setIdentifier(event.getInstanceToLoad(), event.getEntityId(), event.getSession());
/* 145:    */     }
/* 146:204 */     Object entity = doLoad(event, persister, keyToLoad, options);
/* 147:    */     
/* 148:206 */     boolean isOptionalInstance = event.getInstanceToLoad() != null;
/* 149:208 */     if (((!options.isAllowNulls()) || (isOptionalInstance)) && 
/* 150:209 */       (entity == null)) {
/* 151:210 */       event.getSession().getFactory().getEntityNotFoundDelegate().handleEntityNotFound(event.getEntityClassName(), event.getEntityId());
/* 152:    */     }
/* 153:214 */     if ((isOptionalInstance) && (entity != event.getInstanceToLoad())) {
/* 154:215 */       throw new NonUniqueObjectException(event.getEntityId(), event.getEntityClassName());
/* 155:    */     }
/* 156:218 */     return entity;
/* 157:    */   }
/* 158:    */   
/* 159:    */   protected Object proxyOrLoad(LoadEvent event, EntityPersister persister, EntityKey keyToLoad, LoadEventListener.LoadType options)
/* 160:    */   {
/* 161:237 */     if (LOG.isTraceEnabled()) {
/* 162:238 */       LOG.tracev("Loading entity: {0}", MessageHelper.infoString(persister, event.getEntityId(), event.getSession().getFactory()));
/* 163:    */     }
/* 164:243 */     if (!persister.hasProxy()) {
/* 165:243 */       return load(event, persister, keyToLoad, options);
/* 166:    */     }
/* 167:244 */     PersistenceContext persistenceContext = event.getSession().getPersistenceContext();
/* 168:    */     
/* 169:    */ 
/* 170:247 */     Object proxy = persistenceContext.getProxy(keyToLoad);
/* 171:248 */     if (proxy != null) {
/* 172:248 */       return returnNarrowedProxy(event, persister, keyToLoad, options, persistenceContext, proxy);
/* 173:    */     }
/* 174:249 */     if (options.isAllowProxyCreation()) {
/* 175:249 */       return createProxyIfNecessary(event, persister, keyToLoad, options, persistenceContext);
/* 176:    */     }
/* 177:251 */     return load(event, persister, keyToLoad, options);
/* 178:    */   }
/* 179:    */   
/* 180:    */   private Object returnNarrowedProxy(LoadEvent event, EntityPersister persister, EntityKey keyToLoad, LoadEventListener.LoadType options, PersistenceContext persistenceContext, Object proxy)
/* 181:    */   {
/* 182:273 */     LOG.trace("Entity proxy found in session cache");
/* 183:274 */     LazyInitializer li = ((HibernateProxy)proxy).getHibernateLazyInitializer();
/* 184:275 */     if (li.isUnwrap()) {
/* 185:276 */       return li.getImplementation();
/* 186:    */     }
/* 187:278 */     Object impl = null;
/* 188:279 */     if (!options.isAllowProxyCreation())
/* 189:    */     {
/* 190:280 */       impl = load(event, persister, keyToLoad, options);
/* 191:281 */       if (impl == null) {
/* 192:282 */         event.getSession().getFactory().getEntityNotFoundDelegate().handleEntityNotFound(persister.getEntityName(), keyToLoad.getIdentifier());
/* 193:    */       }
/* 194:    */     }
/* 195:285 */     return persistenceContext.narrowProxy(proxy, persister, keyToLoad, impl);
/* 196:    */   }
/* 197:    */   
/* 198:    */   private Object createProxyIfNecessary(LoadEvent event, EntityPersister persister, EntityKey keyToLoad, LoadEventListener.LoadType options, PersistenceContext persistenceContext)
/* 199:    */   {
/* 200:306 */     Object existing = persistenceContext.getEntity(keyToLoad);
/* 201:307 */     if (existing != null)
/* 202:    */     {
/* 203:309 */       LOG.trace("Entity found in session cache");
/* 204:310 */       if (options.isCheckDeleted())
/* 205:    */       {
/* 206:311 */         EntityEntry entry = persistenceContext.getEntry(existing);
/* 207:312 */         Status status = entry.getStatus();
/* 208:313 */         if ((status == Status.DELETED) || (status == Status.GONE)) {
/* 209:314 */           return null;
/* 210:    */         }
/* 211:    */       }
/* 212:317 */       return existing;
/* 213:    */     }
/* 214:319 */     LOG.trace("Creating new proxy for entity");
/* 215:    */     
/* 216:321 */     Object proxy = persister.createProxy(event.getEntityId(), event.getSession());
/* 217:322 */     persistenceContext.getBatchFetchQueue().addBatchLoadableEntityKey(keyToLoad);
/* 218:323 */     persistenceContext.addProxy(keyToLoad, proxy);
/* 219:324 */     return proxy;
/* 220:    */   }
/* 221:    */   
/* 222:    */   protected Object lockAndLoad(LoadEvent event, EntityPersister persister, EntityKey keyToLoad, LoadEventListener.LoadType options, SessionImplementor source)
/* 223:    */   {
/* 224:345 */     SoftLock lock = null;
/* 225:    */     CacheKey ck;
/* 226:347 */     if (persister.hasCache())
/* 227:    */     {
/* 228:348 */       CacheKey ck = source.generateCacheKey(event.getEntityId(), persister.getIdentifierType(), persister.getRootEntityName());
/* 229:    */       
/* 230:    */ 
/* 231:    */ 
/* 232:    */ 
/* 233:353 */       lock = persister.getCacheAccessStrategy().lockItem(ck, null);
/* 234:    */     }
/* 235:    */     else
/* 236:    */     {
/* 237:356 */       ck = null;
/* 238:    */     }
/* 239:    */     Object entity;
/* 240:    */     try
/* 241:    */     {
/* 242:361 */       entity = load(event, persister, keyToLoad, options);
/* 243:    */     }
/* 244:    */     finally
/* 245:    */     {
/* 246:364 */       if (persister.hasCache()) {
/* 247:365 */         persister.getCacheAccessStrategy().unlockItem(ck, lock);
/* 248:    */       }
/* 249:    */     }
/* 250:369 */     return event.getSession().getPersistenceContext().proxyFor(persister, keyToLoad, entity);
/* 251:    */   }
/* 252:    */   
/* 253:    */   protected Object doLoad(LoadEvent event, EntityPersister persister, EntityKey keyToLoad, LoadEventListener.LoadType options)
/* 254:    */   {
/* 255:391 */     boolean traceEnabled = LOG.isTraceEnabled();
/* 256:392 */     if (traceEnabled) {
/* 257:392 */       LOG.tracev("Attempting to resolve: {0}", MessageHelper.infoString(persister, event.getEntityId(), event.getSession().getFactory()));
/* 258:    */     }
/* 259:395 */     Object entity = loadFromSessionCache(event, keyToLoad, options);
/* 260:396 */     if (entity == REMOVED_ENTITY_MARKER)
/* 261:    */     {
/* 262:397 */       LOG.debug("Load request found matching entity in context, but it is scheduled for removal; returning null");
/* 263:398 */       return null;
/* 264:    */     }
/* 265:400 */     if (entity == INCONSISTENT_RTN_CLASS_MARKER)
/* 266:    */     {
/* 267:401 */       LOG.debug("Load request found matching entity in context, but the matched entity was of an inconsistent return type; returning null");
/* 268:402 */       return null;
/* 269:    */     }
/* 270:404 */     if (entity != null)
/* 271:    */     {
/* 272:405 */       if (traceEnabled) {
/* 273:405 */         LOG.tracev("Resolved object in session cache: {0}", MessageHelper.infoString(persister, event.getEntityId(), event.getSession().getFactory()));
/* 274:    */       }
/* 275:407 */       return entity;
/* 276:    */     }
/* 277:410 */     entity = loadFromSecondLevelCache(event, persister, options);
/* 278:411 */     if (entity != null)
/* 279:    */     {
/* 280:412 */       if (traceEnabled) {
/* 281:412 */         LOG.tracev("Resolved object in second-level cache: {0}", MessageHelper.infoString(persister, event.getEntityId(), event.getSession().getFactory()));
/* 282:    */       }
/* 283:414 */       return entity;
/* 284:    */     }
/* 285:417 */     if (traceEnabled) {
/* 286:417 */       LOG.tracev("Object not resolved in any cache: {0}", MessageHelper.infoString(persister, event.getEntityId(), event.getSession().getFactory()));
/* 287:    */     }
/* 288:420 */     return loadFromDatasource(event, persister, keyToLoad, options);
/* 289:    */   }
/* 290:    */   
/* 291:    */   protected Object loadFromDatasource(LoadEvent event, EntityPersister persister, EntityKey keyToLoad, LoadEventListener.LoadType options)
/* 292:    */   {
/* 293:438 */     SessionImplementor source = event.getSession();
/* 294:439 */     Object entity = persister.load(event.getEntityId(), event.getInstanceToLoad(), event.getLockOptions(), source);
/* 295:446 */     if ((event.isAssociationFetch()) && (source.getFactory().getStatistics().isStatisticsEnabled())) {
/* 296:447 */       source.getFactory().getStatisticsImplementor().fetchEntity(event.getEntityClassName());
/* 297:    */     }
/* 298:450 */     return entity;
/* 299:    */   }
/* 300:    */   
/* 301:    */   protected Object loadFromSessionCache(LoadEvent event, EntityKey keyToLoad, LoadEventListener.LoadType options)
/* 302:    */     throws HibernateException
/* 303:    */   {
/* 304:475 */     SessionImplementor session = event.getSession();
/* 305:476 */     Object old = session.getEntityUsingInterceptor(keyToLoad);
/* 306:478 */     if (old != null)
/* 307:    */     {
/* 308:480 */       EntityEntry oldEntry = session.getPersistenceContext().getEntry(old);
/* 309:481 */       if (options.isCheckDeleted())
/* 310:    */       {
/* 311:482 */         Status status = oldEntry.getStatus();
/* 312:483 */         if ((status == Status.DELETED) || (status == Status.GONE)) {
/* 313:484 */           return REMOVED_ENTITY_MARKER;
/* 314:    */         }
/* 315:    */       }
/* 316:487 */       if (options.isAllowNulls())
/* 317:    */       {
/* 318:488 */         EntityPersister persister = event.getSession().getFactory().getEntityPersister(keyToLoad.getEntityName());
/* 319:489 */         if (!persister.isInstance(old)) {
/* 320:490 */           return INCONSISTENT_RTN_CLASS_MARKER;
/* 321:    */         }
/* 322:    */       }
/* 323:493 */       upgradeLock(old, oldEntry, event.getLockOptions(), event.getSession());
/* 324:    */     }
/* 325:496 */     return old;
/* 326:    */   }
/* 327:    */   
/* 328:    */   protected Object loadFromSecondLevelCache(LoadEvent event, EntityPersister persister, LoadEventListener.LoadType options)
/* 329:    */   {
/* 330:512 */     SessionImplementor source = event.getSession();
/* 331:    */     
/* 332:514 */     boolean useCache = (persister.hasCache()) && (source.getCacheMode().isGetEnabled()) && (event.getLockMode().lessThan(LockMode.READ));
/* 333:518 */     if (useCache)
/* 334:    */     {
/* 335:520 */       SessionFactoryImplementor factory = source.getFactory();
/* 336:    */       
/* 337:522 */       CacheKey ck = source.generateCacheKey(event.getEntityId(), persister.getIdentifierType(), persister.getRootEntityName());
/* 338:    */       
/* 339:    */ 
/* 340:    */ 
/* 341:    */ 
/* 342:527 */       Object ce = persister.getCacheAccessStrategy().get(ck, source.getTimestamp());
/* 343:528 */       if (factory.getStatistics().isStatisticsEnabled()) {
/* 344:529 */         if (ce == null) {
/* 345:530 */           factory.getStatisticsImplementor().secondLevelCacheMiss(persister.getCacheAccessStrategy().getRegion().getName());
/* 346:    */         } else {
/* 347:535 */           factory.getStatisticsImplementor().secondLevelCacheHit(persister.getCacheAccessStrategy().getRegion().getName());
/* 348:    */         }
/* 349:    */       }
/* 350:541 */       if (ce != null)
/* 351:    */       {
/* 352:542 */         CacheEntry entry = (CacheEntry)persister.getCacheEntryStructure().destructure(ce, factory);
/* 353:    */         
/* 354:    */ 
/* 355:545 */         return assembleCacheEntry(entry, event.getEntityId(), persister, event);
/* 356:    */       }
/* 357:    */     }
/* 358:554 */     return null;
/* 359:    */   }
/* 360:    */   
/* 361:    */   private Object assembleCacheEntry(CacheEntry entry, Serializable id, EntityPersister persister, LoadEvent event)
/* 362:    */     throws HibernateException
/* 363:    */   {
/* 364:563 */     Object optionalObject = event.getInstanceToLoad();
/* 365:564 */     EventSource session = event.getSession();
/* 366:565 */     SessionFactoryImplementor factory = session.getFactory();
/* 367:567 */     if (LOG.isTraceEnabled()) {
/* 368:568 */       LOG.tracev("Assembling entity from second-level cache: {0}", MessageHelper.infoString(persister, id, factory));
/* 369:    */     }
/* 370:572 */     EntityPersister subclassPersister = factory.getEntityPersister(entry.getSubclass());
/* 371:573 */     Object result = optionalObject == null ? session.instantiate(subclassPersister, id) : optionalObject;
/* 372:    */     
/* 373:    */ 
/* 374:    */ 
/* 375:577 */     EntityKey entityKey = session.generateEntityKey(id, subclassPersister);
/* 376:578 */     TwoPhaseLoad.addUninitializedCachedEntity(entityKey, result, subclassPersister, LockMode.NONE, entry.areLazyPropertiesUnfetched(), entry.getVersion(), session);
/* 377:    */     
/* 378:    */ 
/* 379:    */ 
/* 380:    */ 
/* 381:    */ 
/* 382:    */ 
/* 383:    */ 
/* 384:    */ 
/* 385:    */ 
/* 386:588 */     Type[] types = subclassPersister.getPropertyTypes();
/* 387:589 */     Object[] values = entry.assemble(result, id, subclassPersister, session.getInterceptor(), session);
/* 388:590 */     TypeHelper.deepCopy(values, types, subclassPersister.getPropertyUpdateability(), values, session);
/* 389:    */     
/* 390:    */ 
/* 391:    */ 
/* 392:    */ 
/* 393:    */ 
/* 394:    */ 
/* 395:    */ 
/* 396:598 */     Object version = Versioning.getVersion(values, subclassPersister);
/* 397:599 */     LOG.tracev("Cached Version: {0}", version);
/* 398:    */     
/* 399:601 */     PersistenceContext persistenceContext = session.getPersistenceContext();
/* 400:602 */     boolean isReadOnly = session.isDefaultReadOnly();
/* 401:603 */     if (persister.isMutable())
/* 402:    */     {
/* 403:604 */       Object proxy = persistenceContext.getProxy(entityKey);
/* 404:605 */       if (proxy != null) {
/* 405:608 */         isReadOnly = ((HibernateProxy)proxy).getHibernateLazyInitializer().isReadOnly();
/* 406:    */       }
/* 407:    */     }
/* 408:    */     else
/* 409:    */     {
/* 410:612 */       isReadOnly = true;
/* 411:    */     }
/* 412:614 */     persistenceContext.addEntry(result, isReadOnly ? Status.READ_ONLY : Status.MANAGED, values, null, id, version, LockMode.NONE, true, subclassPersister, false, entry.areLazyPropertiesUnfetched());
/* 413:    */     
/* 414:    */ 
/* 415:    */ 
/* 416:    */ 
/* 417:    */ 
/* 418:    */ 
/* 419:    */ 
/* 420:    */ 
/* 421:    */ 
/* 422:    */ 
/* 423:    */ 
/* 424:    */ 
/* 425:627 */     subclassPersister.afterInitialize(result, entry.areLazyPropertiesUnfetched(), session);
/* 426:628 */     persistenceContext.initializeNonLazyCollections();
/* 427:    */     
/* 428:    */ 
/* 429:    */ 
/* 430:    */ 
/* 431:    */ 
/* 432:634 */     PostLoadEvent postLoadEvent = new PostLoadEvent(session).setEntity(result).setId(id).setPersister(persister);
/* 433:639 */     for (PostLoadEventListener listener : postLoadEventListeners(session)) {
/* 434:640 */       listener.onPostLoad(postLoadEvent);
/* 435:    */     }
/* 436:643 */     return result;
/* 437:    */   }
/* 438:    */   
/* 439:    */   private Iterable<PostLoadEventListener> postLoadEventListeners(EventSource session)
/* 440:    */   {
/* 441:647 */     return ((EventListenerRegistry)session.getFactory().getServiceRegistry().getService(EventListenerRegistry.class)).getEventListenerGroup(EventType.POST_LOAD).listeners();
/* 442:    */   }
/* 443:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.internal.DefaultLoadEventListener
 * JD-Core Version:    0.7.0.1
 */