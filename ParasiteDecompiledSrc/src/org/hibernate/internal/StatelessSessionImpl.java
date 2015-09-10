/*   1:    */ package org.hibernate.internal;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.sql.Connection;
/*   5:    */ import java.util.Collections;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.List;
/*   8:    */ import java.util.Map;
/*   9:    */ import java.util.Set;
/*  10:    */ import org.hibernate.CacheMode;
/*  11:    */ import org.hibernate.ConnectionReleaseMode;
/*  12:    */ import org.hibernate.Criteria;
/*  13:    */ import org.hibernate.EmptyInterceptor;
/*  14:    */ import org.hibernate.EntityMode;
/*  15:    */ import org.hibernate.FlushMode;
/*  16:    */ import org.hibernate.HibernateException;
/*  17:    */ import org.hibernate.Interceptor;
/*  18:    */ import org.hibernate.LockMode;
/*  19:    */ import org.hibernate.MappingException;
/*  20:    */ import org.hibernate.ScrollMode;
/*  21:    */ import org.hibernate.ScrollableResults;
/*  22:    */ import org.hibernate.SessionException;
/*  23:    */ import org.hibernate.StatelessSession;
/*  24:    */ import org.hibernate.Transaction;
/*  25:    */ import org.hibernate.UnresolvableObjectException;
/*  26:    */ import org.hibernate.cache.spi.CacheKey;
/*  27:    */ import org.hibernate.cache.spi.access.EntityRegionAccessStrategy;
/*  28:    */ import org.hibernate.cfg.Settings;
/*  29:    */ import org.hibernate.collection.spi.PersistentCollection;
/*  30:    */ import org.hibernate.engine.internal.StatefulPersistenceContext;
/*  31:    */ import org.hibernate.engine.internal.Versioning;
/*  32:    */ import org.hibernate.engine.jdbc.spi.JdbcCoordinator;
/*  33:    */ import org.hibernate.engine.jdbc.spi.LogicalConnectionImplementor;
/*  34:    */ import org.hibernate.engine.query.spi.HQLQueryPlan;
/*  35:    */ import org.hibernate.engine.query.spi.NativeSQLQueryPlan;
/*  36:    */ import org.hibernate.engine.query.spi.sql.NativeSQLQuerySpecification;
/*  37:    */ import org.hibernate.engine.spi.EntityKey;
/*  38:    */ import org.hibernate.engine.spi.LoadQueryInfluencers;
/*  39:    */ import org.hibernate.engine.spi.NonFlushedChanges;
/*  40:    */ import org.hibernate.engine.spi.PersistenceContext;
/*  41:    */ import org.hibernate.engine.spi.QueryParameters;
/*  42:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  43:    */ import org.hibernate.engine.transaction.internal.TransactionCoordinatorImpl;
/*  44:    */ import org.hibernate.engine.transaction.spi.TransactionCoordinator;
/*  45:    */ import org.hibernate.engine.transaction.spi.TransactionEnvironment;
/*  46:    */ import org.hibernate.engine.transaction.spi.TransactionImplementor;
/*  47:    */ import org.hibernate.id.IdentifierGenerator;
/*  48:    */ import org.hibernate.id.IdentifierGeneratorHelper;
/*  49:    */ import org.hibernate.internal.util.collections.CollectionHelper;
/*  50:    */ import org.hibernate.loader.criteria.CriteriaLoader;
/*  51:    */ import org.hibernate.loader.custom.CustomLoader;
/*  52:    */ import org.hibernate.loader.custom.CustomQuery;
/*  53:    */ import org.hibernate.persister.entity.EntityPersister;
/*  54:    */ import org.hibernate.persister.entity.OuterJoinLoadable;
/*  55:    */ import org.hibernate.pretty.MessageHelper;
/*  56:    */ import org.hibernate.proxy.HibernateProxy;
/*  57:    */ import org.hibernate.proxy.LazyInitializer;
/*  58:    */ import org.hibernate.type.Type;
/*  59:    */ import org.jboss.logging.Logger;
/*  60:    */ 
/*  61:    */ public class StatelessSessionImpl
/*  62:    */   extends AbstractSessionImpl
/*  63:    */   implements StatelessSession
/*  64:    */ {
/*  65: 84 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, StatelessSessionImpl.class.getName());
/*  66:    */   private TransactionCoordinator transactionCoordinator;
/*  67: 87 */   private PersistenceContext temporaryPersistenceContext = new StatefulPersistenceContext(this);
/*  68:    */   
/*  69:    */   StatelessSessionImpl(Connection connection, String tenantIdentifier, SessionFactoryImpl factory)
/*  70:    */   {
/*  71: 90 */     super(factory, tenantIdentifier);
/*  72: 91 */     this.transactionCoordinator = new TransactionCoordinatorImpl(connection, this);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public TransactionCoordinator getTransactionCoordinator()
/*  76:    */   {
/*  77: 98 */     return this.transactionCoordinator;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public TransactionEnvironment getTransactionEnvironment()
/*  81:    */   {
/*  82:103 */     return this.factory.getTransactionEnvironment();
/*  83:    */   }
/*  84:    */   
/*  85:    */   public Serializable insert(Object entity)
/*  86:    */   {
/*  87:109 */     errorIfClosed();
/*  88:110 */     return insert(null, entity);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public Serializable insert(String entityName, Object entity)
/*  92:    */   {
/*  93:114 */     errorIfClosed();
/*  94:115 */     EntityPersister persister = getEntityPersister(entityName, entity);
/*  95:116 */     Serializable id = persister.getIdentifierGenerator().generate(this, entity);
/*  96:117 */     Object[] state = persister.getPropertyValues(entity);
/*  97:118 */     if (persister.isVersioned())
/*  98:    */     {
/*  99:119 */       boolean substitute = Versioning.seedVersion(state, persister.getVersionProperty(), persister.getVersionType(), this);
/* 100:122 */       if (substitute) {
/* 101:123 */         persister.setPropertyValues(entity, state);
/* 102:    */       }
/* 103:    */     }
/* 104:126 */     if (id == IdentifierGeneratorHelper.POST_INSERT_INDICATOR) {
/* 105:127 */       id = persister.insert(state, entity, this);
/* 106:    */     } else {
/* 107:130 */       persister.insert(id, state, entity, this);
/* 108:    */     }
/* 109:132 */     persister.setIdentifier(entity, id, this);
/* 110:133 */     return id;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void delete(Object entity)
/* 114:    */   {
/* 115:140 */     errorIfClosed();
/* 116:141 */     delete(null, entity);
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void delete(String entityName, Object entity)
/* 120:    */   {
/* 121:145 */     errorIfClosed();
/* 122:146 */     EntityPersister persister = getEntityPersister(entityName, entity);
/* 123:147 */     Serializable id = persister.getIdentifier(entity, this);
/* 124:148 */     Object version = persister.getVersion(entity);
/* 125:149 */     persister.delete(id, version, entity, this);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void update(Object entity)
/* 129:    */   {
/* 130:156 */     errorIfClosed();
/* 131:157 */     update(null, entity);
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void update(String entityName, Object entity)
/* 135:    */   {
/* 136:161 */     errorIfClosed();
/* 137:162 */     EntityPersister persister = getEntityPersister(entityName, entity);
/* 138:163 */     Serializable id = persister.getIdentifier(entity, this);
/* 139:164 */     Object[] state = persister.getPropertyValues(entity);
/* 140:    */     Object oldVersion;
/* 141:166 */     if (persister.isVersioned())
/* 142:    */     {
/* 143:167 */       Object oldVersion = persister.getVersion(entity);
/* 144:168 */       Object newVersion = Versioning.increment(oldVersion, persister.getVersionType(), this);
/* 145:169 */       Versioning.setVersion(state, newVersion, persister);
/* 146:170 */       persister.setPropertyValues(entity, state);
/* 147:    */     }
/* 148:    */     else
/* 149:    */     {
/* 150:173 */       oldVersion = null;
/* 151:    */     }
/* 152:175 */     persister.update(id, state, null, false, null, oldVersion, entity, null, this);
/* 153:    */   }
/* 154:    */   
/* 155:    */   public Object get(Class entityClass, Serializable id)
/* 156:    */   {
/* 157:182 */     return get(entityClass.getName(), id);
/* 158:    */   }
/* 159:    */   
/* 160:    */   public Object get(Class entityClass, Serializable id, LockMode lockMode)
/* 161:    */   {
/* 162:186 */     return get(entityClass.getName(), id, lockMode);
/* 163:    */   }
/* 164:    */   
/* 165:    */   public Object get(String entityName, Serializable id)
/* 166:    */   {
/* 167:190 */     return get(entityName, id, LockMode.NONE);
/* 168:    */   }
/* 169:    */   
/* 170:    */   public Object get(String entityName, Serializable id, LockMode lockMode)
/* 171:    */   {
/* 172:194 */     errorIfClosed();
/* 173:195 */     Object result = getFactory().getEntityPersister(entityName).load(id, null, lockMode, this);
/* 174:197 */     if (this.temporaryPersistenceContext.isLoadFinished()) {
/* 175:198 */       this.temporaryPersistenceContext.clear();
/* 176:    */     }
/* 177:200 */     return result;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void refresh(Object entity)
/* 181:    */   {
/* 182:204 */     refresh(bestGuessEntityName(entity), entity, LockMode.NONE);
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void refresh(String entityName, Object entity)
/* 186:    */   {
/* 187:208 */     refresh(entityName, entity, LockMode.NONE);
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void refresh(Object entity, LockMode lockMode)
/* 191:    */   {
/* 192:212 */     refresh(bestGuessEntityName(entity), entity, lockMode);
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void refresh(String entityName, Object entity, LockMode lockMode)
/* 196:    */   {
/* 197:216 */     EntityPersister persister = getEntityPersister(entityName, entity);
/* 198:217 */     Serializable id = persister.getIdentifier(entity, this);
/* 199:218 */     if (LOG.isTraceEnabled()) {
/* 200:219 */       LOG.tracev("Refreshing transient {0}", MessageHelper.infoString(persister, id, getFactory()));
/* 201:    */     }
/* 202:231 */     if (persister.hasCache())
/* 203:    */     {
/* 204:232 */       CacheKey ck = generateCacheKey(id, persister.getIdentifierType(), persister.getRootEntityName());
/* 205:233 */       persister.getCacheAccessStrategy().evict(ck);
/* 206:    */     }
/* 207:236 */     String previousFetchProfile = getFetchProfile();
/* 208:237 */     Object result = null;
/* 209:    */     try
/* 210:    */     {
/* 211:239 */       setFetchProfile("refresh");
/* 212:240 */       result = persister.load(id, entity, lockMode, this);
/* 213:    */     }
/* 214:    */     finally
/* 215:    */     {
/* 216:243 */       setFetchProfile(previousFetchProfile);
/* 217:    */     }
/* 218:245 */     UnresolvableObjectException.throwIfNull(result, id, persister.getEntityName());
/* 219:    */   }
/* 220:    */   
/* 221:    */   public Object immediateLoad(String entityName, Serializable id)
/* 222:    */     throws HibernateException
/* 223:    */   {
/* 224:250 */     throw new SessionException("proxies cannot be fetched by a stateless session");
/* 225:    */   }
/* 226:    */   
/* 227:    */   public void initializeCollection(PersistentCollection collection, boolean writing)
/* 228:    */     throws HibernateException
/* 229:    */   {
/* 230:256 */     throw new SessionException("collections cannot be fetched by a stateless session");
/* 231:    */   }
/* 232:    */   
/* 233:    */   public Object instantiate(String entityName, Serializable id)
/* 234:    */     throws HibernateException
/* 235:    */   {
/* 236:262 */     errorIfClosed();
/* 237:263 */     return getFactory().getEntityPersister(entityName).instantiate(id, this);
/* 238:    */   }
/* 239:    */   
/* 240:    */   public Object internalLoad(String entityName, Serializable id, boolean eager, boolean nullable)
/* 241:    */     throws HibernateException
/* 242:    */   {
/* 243:272 */     errorIfClosed();
/* 244:273 */     EntityPersister persister = getFactory().getEntityPersister(entityName);
/* 245:    */     
/* 246:275 */     Object loaded = this.temporaryPersistenceContext.getEntity(generateEntityKey(id, persister));
/* 247:276 */     if (loaded != null) {
/* 248:279 */       return loaded;
/* 249:    */     }
/* 250:281 */     if ((!eager) && (persister.hasProxy())) {
/* 251:284 */       return persister.createProxy(id, this);
/* 252:    */     }
/* 253:287 */     return get(entityName, id);
/* 254:    */   }
/* 255:    */   
/* 256:    */   public Iterator iterate(String query, QueryParameters queryParameters)
/* 257:    */     throws HibernateException
/* 258:    */   {
/* 259:291 */     throw new UnsupportedOperationException();
/* 260:    */   }
/* 261:    */   
/* 262:    */   public Iterator iterateFilter(Object collection, String filter, QueryParameters queryParameters)
/* 263:    */     throws HibernateException
/* 264:    */   {
/* 265:296 */     throw new UnsupportedOperationException();
/* 266:    */   }
/* 267:    */   
/* 268:    */   public List listFilter(Object collection, String filter, QueryParameters queryParameters)
/* 269:    */     throws HibernateException
/* 270:    */   {
/* 271:301 */     throw new UnsupportedOperationException();
/* 272:    */   }
/* 273:    */   
/* 274:    */   public boolean isOpen()
/* 275:    */   {
/* 276:306 */     return !isClosed();
/* 277:    */   }
/* 278:    */   
/* 279:    */   public void close()
/* 280:    */   {
/* 281:310 */     managedClose();
/* 282:    */   }
/* 283:    */   
/* 284:    */   public ConnectionReleaseMode getConnectionReleaseMode()
/* 285:    */   {
/* 286:314 */     return this.factory.getSettings().getConnectionReleaseMode();
/* 287:    */   }
/* 288:    */   
/* 289:    */   public boolean shouldAutoJoinTransaction()
/* 290:    */   {
/* 291:319 */     return true;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public boolean isAutoCloseSessionEnabled()
/* 295:    */   {
/* 296:323 */     return this.factory.getSettings().isAutoCloseSessionEnabled();
/* 297:    */   }
/* 298:    */   
/* 299:    */   public boolean isFlushBeforeCompletionEnabled()
/* 300:    */   {
/* 301:327 */     return true;
/* 302:    */   }
/* 303:    */   
/* 304:    */   public boolean isFlushModeNever()
/* 305:    */   {
/* 306:331 */     return false;
/* 307:    */   }
/* 308:    */   
/* 309:    */   public void managedClose()
/* 310:    */   {
/* 311:335 */     if (isClosed()) {
/* 312:336 */       throw new SessionException("Session was already closed!");
/* 313:    */     }
/* 314:338 */     this.transactionCoordinator.close();
/* 315:339 */     setClosed();
/* 316:    */   }
/* 317:    */   
/* 318:    */   public void managedFlush()
/* 319:    */   {
/* 320:343 */     errorIfClosed();
/* 321:344 */     getTransactionCoordinator().getJdbcCoordinator().executeBatch();
/* 322:    */   }
/* 323:    */   
/* 324:    */   public boolean shouldAutoClose()
/* 325:    */   {
/* 326:348 */     return (isAutoCloseSessionEnabled()) && (!isClosed());
/* 327:    */   }
/* 328:    */   
/* 329:    */   public void afterTransactionBegin(TransactionImplementor hibernateTransaction) {}
/* 330:    */   
/* 331:    */   public void beforeTransactionCompletion(TransactionImplementor hibernateTransaction) {}
/* 332:    */   
/* 333:    */   public void afterTransactionCompletion(TransactionImplementor hibernateTransaction, boolean successful) {}
/* 334:    */   
/* 335:    */   public String onPrepareStatement(String sql)
/* 336:    */   {
/* 337:368 */     return sql;
/* 338:    */   }
/* 339:    */   
/* 340:    */   public String bestGuessEntityName(Object object)
/* 341:    */   {
/* 342:372 */     if ((object instanceof HibernateProxy)) {
/* 343:373 */       object = ((HibernateProxy)object).getHibernateLazyInitializer().getImplementation();
/* 344:    */     }
/* 345:375 */     return guessEntityName(object);
/* 346:    */   }
/* 347:    */   
/* 348:    */   public Connection connection()
/* 349:    */   {
/* 350:379 */     errorIfClosed();
/* 351:380 */     return this.transactionCoordinator.getJdbcCoordinator().getLogicalConnection().getDistinctConnectionProxy();
/* 352:    */   }
/* 353:    */   
/* 354:    */   public int executeUpdate(String query, QueryParameters queryParameters)
/* 355:    */     throws HibernateException
/* 356:    */   {
/* 357:385 */     errorIfClosed();
/* 358:386 */     queryParameters.validateParameters();
/* 359:387 */     HQLQueryPlan plan = getHQLQueryPlan(query, false);
/* 360:388 */     boolean success = false;
/* 361:389 */     int result = 0;
/* 362:    */     try
/* 363:    */     {
/* 364:391 */       result = plan.performExecuteUpdate(queryParameters, this);
/* 365:392 */       success = true;
/* 366:    */     }
/* 367:    */     finally
/* 368:    */     {
/* 369:395 */       afterOperation(success);
/* 370:    */     }
/* 371:397 */     this.temporaryPersistenceContext.clear();
/* 372:398 */     return result;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public CacheMode getCacheMode()
/* 376:    */   {
/* 377:402 */     return CacheMode.IGNORE;
/* 378:    */   }
/* 379:    */   
/* 380:    */   public int getDontFlushFromFind()
/* 381:    */   {
/* 382:406 */     return 0;
/* 383:    */   }
/* 384:    */   
/* 385:    */   public Map getEnabledFilters()
/* 386:    */   {
/* 387:410 */     return CollectionHelper.EMPTY_MAP;
/* 388:    */   }
/* 389:    */   
/* 390:    */   public Serializable getContextEntityIdentifier(Object object)
/* 391:    */   {
/* 392:414 */     errorIfClosed();
/* 393:415 */     return null;
/* 394:    */   }
/* 395:    */   
/* 396:    */   public EntityMode getEntityMode()
/* 397:    */   {
/* 398:419 */     return EntityMode.POJO;
/* 399:    */   }
/* 400:    */   
/* 401:    */   public EntityPersister getEntityPersister(String entityName, Object object)
/* 402:    */     throws HibernateException
/* 403:    */   {
/* 404:424 */     errorIfClosed();
/* 405:425 */     if (entityName == null) {
/* 406:426 */       return this.factory.getEntityPersister(guessEntityName(object));
/* 407:    */     }
/* 408:429 */     return this.factory.getEntityPersister(entityName).getSubclassEntityPersister(object, getFactory());
/* 409:    */   }
/* 410:    */   
/* 411:    */   public Object getEntityUsingInterceptor(EntityKey key)
/* 412:    */     throws HibernateException
/* 413:    */   {
/* 414:434 */     errorIfClosed();
/* 415:435 */     return null;
/* 416:    */   }
/* 417:    */   
/* 418:    */   public Type getFilterParameterType(String filterParameterName)
/* 419:    */   {
/* 420:439 */     throw new UnsupportedOperationException();
/* 421:    */   }
/* 422:    */   
/* 423:    */   public Object getFilterParameterValue(String filterParameterName)
/* 424:    */   {
/* 425:443 */     throw new UnsupportedOperationException();
/* 426:    */   }
/* 427:    */   
/* 428:    */   public FlushMode getFlushMode()
/* 429:    */   {
/* 430:447 */     return FlushMode.COMMIT;
/* 431:    */   }
/* 432:    */   
/* 433:    */   public Interceptor getInterceptor()
/* 434:    */   {
/* 435:451 */     return EmptyInterceptor.INSTANCE;
/* 436:    */   }
/* 437:    */   
/* 438:    */   public PersistenceContext getPersistenceContext()
/* 439:    */   {
/* 440:455 */     return this.temporaryPersistenceContext;
/* 441:    */   }
/* 442:    */   
/* 443:    */   public long getTimestamp()
/* 444:    */   {
/* 445:459 */     throw new UnsupportedOperationException();
/* 446:    */   }
/* 447:    */   
/* 448:    */   public String guessEntityName(Object entity)
/* 449:    */     throws HibernateException
/* 450:    */   {
/* 451:463 */     errorIfClosed();
/* 452:464 */     return entity.getClass().getName();
/* 453:    */   }
/* 454:    */   
/* 455:    */   public boolean isConnected()
/* 456:    */   {
/* 457:469 */     return this.transactionCoordinator.getJdbcCoordinator().getLogicalConnection().isPhysicallyConnected();
/* 458:    */   }
/* 459:    */   
/* 460:    */   public boolean isTransactionInProgress()
/* 461:    */   {
/* 462:473 */     return this.transactionCoordinator.isTransactionInProgress();
/* 463:    */   }
/* 464:    */   
/* 465:    */   public void setAutoClear(boolean enabled)
/* 466:    */   {
/* 467:477 */     throw new UnsupportedOperationException();
/* 468:    */   }
/* 469:    */   
/* 470:    */   public void disableTransactionAutoJoin()
/* 471:    */   {
/* 472:482 */     throw new UnsupportedOperationException();
/* 473:    */   }
/* 474:    */   
/* 475:    */   public void setCacheMode(CacheMode cm)
/* 476:    */   {
/* 477:486 */     throw new UnsupportedOperationException();
/* 478:    */   }
/* 479:    */   
/* 480:    */   public void setFlushMode(FlushMode fm)
/* 481:    */   {
/* 482:490 */     throw new UnsupportedOperationException();
/* 483:    */   }
/* 484:    */   
/* 485:    */   public Transaction getTransaction()
/* 486:    */     throws HibernateException
/* 487:    */   {
/* 488:494 */     errorIfClosed();
/* 489:495 */     return this.transactionCoordinator.getTransaction();
/* 490:    */   }
/* 491:    */   
/* 492:    */   public Transaction beginTransaction()
/* 493:    */     throws HibernateException
/* 494:    */   {
/* 495:499 */     errorIfClosed();
/* 496:500 */     Transaction result = getTransaction();
/* 497:501 */     result.begin();
/* 498:502 */     return result;
/* 499:    */   }
/* 500:    */   
/* 501:    */   public boolean isEventSource()
/* 502:    */   {
/* 503:506 */     return false;
/* 504:    */   }
/* 505:    */   
/* 506:    */   public boolean isDefaultReadOnly()
/* 507:    */   {
/* 508:513 */     return false;
/* 509:    */   }
/* 510:    */   
/* 511:    */   public void setDefaultReadOnly(boolean readOnly)
/* 512:    */     throws HibernateException
/* 513:    */   {
/* 514:520 */     if (readOnly == true) {
/* 515:521 */       throw new UnsupportedOperationException();
/* 516:    */     }
/* 517:    */   }
/* 518:    */   
/* 519:    */   public List list(String query, QueryParameters queryParameters)
/* 520:    */     throws HibernateException
/* 521:    */   {
/* 522:530 */     errorIfClosed();
/* 523:531 */     queryParameters.validateParameters();
/* 524:532 */     HQLQueryPlan plan = getHQLQueryPlan(query, false);
/* 525:533 */     boolean success = false;
/* 526:534 */     List results = CollectionHelper.EMPTY_LIST;
/* 527:    */     try
/* 528:    */     {
/* 529:536 */       results = plan.performList(queryParameters, this);
/* 530:537 */       success = true;
/* 531:    */     }
/* 532:    */     finally
/* 533:    */     {
/* 534:540 */       afterOperation(success);
/* 535:    */     }
/* 536:542 */     this.temporaryPersistenceContext.clear();
/* 537:543 */     return results;
/* 538:    */   }
/* 539:    */   
/* 540:    */   public void afterOperation(boolean success)
/* 541:    */   {
/* 542:547 */     if (!this.transactionCoordinator.isTransactionInProgress()) {
/* 543:548 */       this.transactionCoordinator.afterNonTransactionalQuery(success);
/* 544:    */     }
/* 545:    */   }
/* 546:    */   
/* 547:    */   public Criteria createCriteria(Class persistentClass, String alias)
/* 548:    */   {
/* 549:553 */     errorIfClosed();
/* 550:554 */     return new CriteriaImpl(persistentClass.getName(), alias, this);
/* 551:    */   }
/* 552:    */   
/* 553:    */   public Criteria createCriteria(String entityName, String alias)
/* 554:    */   {
/* 555:558 */     errorIfClosed();
/* 556:559 */     return new CriteriaImpl(entityName, alias, this);
/* 557:    */   }
/* 558:    */   
/* 559:    */   public Criteria createCriteria(Class persistentClass)
/* 560:    */   {
/* 561:563 */     errorIfClosed();
/* 562:564 */     return new CriteriaImpl(persistentClass.getName(), this);
/* 563:    */   }
/* 564:    */   
/* 565:    */   public Criteria createCriteria(String entityName)
/* 566:    */   {
/* 567:568 */     errorIfClosed();
/* 568:569 */     return new CriteriaImpl(entityName, this);
/* 569:    */   }
/* 570:    */   
/* 571:    */   public ScrollableResults scroll(CriteriaImpl criteria, ScrollMode scrollMode)
/* 572:    */   {
/* 573:573 */     errorIfClosed();
/* 574:574 */     String entityName = criteria.getEntityOrClassName();
/* 575:575 */     CriteriaLoader loader = new CriteriaLoader(getOuterJoinLoadable(entityName), this.factory, criteria, entityName, getLoadQueryInfluencers());
/* 576:    */     
/* 577:    */ 
/* 578:    */ 
/* 579:    */ 
/* 580:    */ 
/* 581:    */ 
/* 582:582 */     return loader.scroll(this, scrollMode);
/* 583:    */   }
/* 584:    */   
/* 585:    */   public List list(CriteriaImpl criteria)
/* 586:    */     throws HibernateException
/* 587:    */   {
/* 588:586 */     errorIfClosed();
/* 589:587 */     String[] implementors = this.factory.getImplementors(criteria.getEntityOrClassName());
/* 590:588 */     int size = implementors.length;
/* 591:    */     
/* 592:590 */     CriteriaLoader[] loaders = new CriteriaLoader[size];
/* 593:591 */     for (int i = 0; i < size; i++) {
/* 594:592 */       loaders[i] = new CriteriaLoader(getOuterJoinLoadable(implementors[i]), this.factory, criteria, implementors[i], getLoadQueryInfluencers());
/* 595:    */     }
/* 596:602 */     List results = Collections.EMPTY_LIST;
/* 597:603 */     boolean success = false;
/* 598:    */     try
/* 599:    */     {
/* 600:605 */       for (int i = 0; i < size; i++)
/* 601:    */       {
/* 602:606 */         List currentResults = loaders[i].list(this);
/* 603:607 */         currentResults.addAll(results);
/* 604:608 */         results = currentResults;
/* 605:    */       }
/* 606:610 */       success = true;
/* 607:    */     }
/* 608:    */     finally
/* 609:    */     {
/* 610:613 */       afterOperation(success);
/* 611:    */     }
/* 612:615 */     this.temporaryPersistenceContext.clear();
/* 613:616 */     return results;
/* 614:    */   }
/* 615:    */   
/* 616:    */   private OuterJoinLoadable getOuterJoinLoadable(String entityName)
/* 617:    */     throws MappingException
/* 618:    */   {
/* 619:620 */     EntityPersister persister = this.factory.getEntityPersister(entityName);
/* 620:621 */     if (!(persister instanceof OuterJoinLoadable)) {
/* 621:622 */       throw new MappingException("class persister is not OuterJoinLoadable: " + entityName);
/* 622:    */     }
/* 623:624 */     return (OuterJoinLoadable)persister;
/* 624:    */   }
/* 625:    */   
/* 626:    */   public List listCustomQuery(CustomQuery customQuery, QueryParameters queryParameters)
/* 627:    */     throws HibernateException
/* 628:    */   {
/* 629:629 */     errorIfClosed();
/* 630:630 */     CustomLoader loader = new CustomLoader(customQuery, getFactory());
/* 631:    */     
/* 632:632 */     boolean success = false;
/* 633:    */     List results;
/* 634:    */     try
/* 635:    */     {
/* 636:635 */       results = loader.list(this, queryParameters);
/* 637:636 */       success = true;
/* 638:    */     }
/* 639:    */     finally
/* 640:    */     {
/* 641:639 */       afterOperation(success);
/* 642:    */     }
/* 643:641 */     this.temporaryPersistenceContext.clear();
/* 644:642 */     return results;
/* 645:    */   }
/* 646:    */   
/* 647:    */   public ScrollableResults scrollCustomQuery(CustomQuery customQuery, QueryParameters queryParameters)
/* 648:    */     throws HibernateException
/* 649:    */   {
/* 650:647 */     errorIfClosed();
/* 651:648 */     CustomLoader loader = new CustomLoader(customQuery, getFactory());
/* 652:649 */     return loader.scroll(queryParameters, this);
/* 653:    */   }
/* 654:    */   
/* 655:    */   public ScrollableResults scroll(String query, QueryParameters queryParameters)
/* 656:    */     throws HibernateException
/* 657:    */   {
/* 658:653 */     errorIfClosed();
/* 659:654 */     HQLQueryPlan plan = getHQLQueryPlan(query, false);
/* 660:655 */     return plan.performScroll(queryParameters, this);
/* 661:    */   }
/* 662:    */   
/* 663:    */   public void afterScrollOperation()
/* 664:    */   {
/* 665:659 */     this.temporaryPersistenceContext.clear();
/* 666:    */   }
/* 667:    */   
/* 668:    */   public void flush() {}
/* 669:    */   
/* 670:    */   public NonFlushedChanges getNonFlushedChanges()
/* 671:    */   {
/* 672:665 */     throw new UnsupportedOperationException();
/* 673:    */   }
/* 674:    */   
/* 675:    */   public void applyNonFlushedChanges(NonFlushedChanges nonFlushedChanges)
/* 676:    */   {
/* 677:669 */     throw new UnsupportedOperationException();
/* 678:    */   }
/* 679:    */   
/* 680:    */   public String getFetchProfile()
/* 681:    */   {
/* 682:673 */     return null;
/* 683:    */   }
/* 684:    */   
/* 685:    */   public LoadQueryInfluencers getLoadQueryInfluencers()
/* 686:    */   {
/* 687:677 */     return LoadQueryInfluencers.NONE;
/* 688:    */   }
/* 689:    */   
/* 690:    */   public void registerInsertedKey(EntityPersister persister, Serializable id)
/* 691:    */   {
/* 692:681 */     errorIfClosed();
/* 693:    */   }
/* 694:    */   
/* 695:    */   public boolean wasInsertedDuringTransaction(EntityPersister persister, Serializable id)
/* 696:    */   {
/* 697:686 */     errorIfClosed();
/* 698:    */     
/* 699:688 */     return false;
/* 700:    */   }
/* 701:    */   
/* 702:    */   public void setFetchProfile(String name) {}
/* 703:    */   
/* 704:    */   protected boolean autoFlushIfRequired(Set querySpaces)
/* 705:    */     throws HibernateException
/* 706:    */   {
/* 707:695 */     return false;
/* 708:    */   }
/* 709:    */   
/* 710:    */   public int executeNativeUpdate(NativeSQLQuerySpecification nativeSQLQuerySpecification, QueryParameters queryParameters)
/* 711:    */     throws HibernateException
/* 712:    */   {
/* 713:700 */     errorIfClosed();
/* 714:701 */     queryParameters.validateParameters();
/* 715:702 */     NativeSQLQueryPlan plan = getNativeSQLQueryPlan(nativeSQLQuerySpecification);
/* 716:    */     
/* 717:704 */     boolean success = false;
/* 718:705 */     int result = 0;
/* 719:    */     try
/* 720:    */     {
/* 721:707 */       result = plan.performExecuteUpdate(queryParameters, this);
/* 722:708 */       success = true;
/* 723:    */     }
/* 724:    */     finally
/* 725:    */     {
/* 726:710 */       afterOperation(success);
/* 727:    */     }
/* 728:712 */     this.temporaryPersistenceContext.clear();
/* 729:713 */     return result;
/* 730:    */   }
/* 731:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.StatelessSessionImpl
 * JD-Core Version:    0.7.0.1
 */