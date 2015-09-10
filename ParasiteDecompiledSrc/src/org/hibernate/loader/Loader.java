/*    1:     */ package org.hibernate.loader;
/*    2:     */ 
/*    3:     */ import java.io.Serializable;
/*    4:     */ import java.sql.CallableStatement;
/*    5:     */ import java.sql.PreparedStatement;
/*    6:     */ import java.sql.ResultSet;
/*    7:     */ import java.sql.ResultSetMetaData;
/*    8:     */ import java.sql.SQLException;
/*    9:     */ import java.util.ArrayList;
/*   10:     */ import java.util.Arrays;
/*   11:     */ import java.util.HashMap;
/*   12:     */ import java.util.HashSet;
/*   13:     */ import java.util.Iterator;
/*   14:     */ import java.util.List;
/*   15:     */ import java.util.Map;
/*   16:     */ import java.util.Map.Entry;
/*   17:     */ import java.util.Set;
/*   18:     */ import org.hibernate.AssertionFailure;
/*   19:     */ import org.hibernate.CacheMode;
/*   20:     */ import org.hibernate.HibernateException;
/*   21:     */ import org.hibernate.LockMode;
/*   22:     */ import org.hibernate.LockOptions;
/*   23:     */ import org.hibernate.QueryException;
/*   24:     */ import org.hibernate.ScrollMode;
/*   25:     */ import org.hibernate.ScrollableResults;
/*   26:     */ import org.hibernate.StaleObjectStateException;
/*   27:     */ import org.hibernate.WrongClassException;
/*   28:     */ import org.hibernate.cache.spi.FilterKey;
/*   29:     */ import org.hibernate.cache.spi.QueryCache;
/*   30:     */ import org.hibernate.cache.spi.QueryKey;
/*   31:     */ import org.hibernate.cache.spi.QueryResultsRegion;
/*   32:     */ import org.hibernate.cfg.Settings;
/*   33:     */ import org.hibernate.collection.spi.PersistentCollection;
/*   34:     */ import org.hibernate.dialect.Dialect;
/*   35:     */ import org.hibernate.engine.internal.TwoPhaseLoad;
/*   36:     */ import org.hibernate.engine.jdbc.ColumnNameCache;
/*   37:     */ import org.hibernate.engine.jdbc.spi.JdbcCoordinator;
/*   38:     */ import org.hibernate.engine.jdbc.spi.JdbcServices;
/*   39:     */ import org.hibernate.engine.jdbc.spi.ResultSetWrapper;
/*   40:     */ import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
/*   41:     */ import org.hibernate.engine.jdbc.spi.StatementPreparer;
/*   42:     */ import org.hibernate.engine.loading.internal.CollectionLoadContext;
/*   43:     */ import org.hibernate.engine.loading.internal.LoadContexts;
/*   44:     */ import org.hibernate.engine.spi.BatchFetchQueue;
/*   45:     */ import org.hibernate.engine.spi.EntityEntry;
/*   46:     */ import org.hibernate.engine.spi.EntityKey;
/*   47:     */ import org.hibernate.engine.spi.EntityUniqueKey;
/*   48:     */ import org.hibernate.engine.spi.LoadQueryInfluencers;
/*   49:     */ import org.hibernate.engine.spi.PersistenceContext;
/*   50:     */ import org.hibernate.engine.spi.QueryParameters;
/*   51:     */ import org.hibernate.engine.spi.RowSelection;
/*   52:     */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   53:     */ import org.hibernate.engine.spi.SessionImplementor;
/*   54:     */ import org.hibernate.engine.spi.SubselectFetch;
/*   55:     */ import org.hibernate.engine.spi.TypedValue;
/*   56:     */ import org.hibernate.engine.transaction.spi.TransactionCoordinator;
/*   57:     */ import org.hibernate.event.spi.EventSource;
/*   58:     */ import org.hibernate.event.spi.PostLoadEvent;
/*   59:     */ import org.hibernate.event.spi.PreLoadEvent;
/*   60:     */ import org.hibernate.hql.internal.HolderInstantiator;
/*   61:     */ import org.hibernate.internal.CoreMessageLogger;
/*   62:     */ import org.hibernate.internal.FetchingScrollableResultsImpl;
/*   63:     */ import org.hibernate.internal.ScrollableResultsImpl;
/*   64:     */ import org.hibernate.internal.util.StringHelper;
/*   65:     */ import org.hibernate.persister.collection.CollectionPersister;
/*   66:     */ import org.hibernate.persister.entity.EntityPersister;
/*   67:     */ import org.hibernate.persister.entity.Loadable;
/*   68:     */ import org.hibernate.persister.entity.UniqueKeyLoadable;
/*   69:     */ import org.hibernate.pretty.MessageHelper;
/*   70:     */ import org.hibernate.proxy.HibernateProxy;
/*   71:     */ import org.hibernate.proxy.LazyInitializer;
/*   72:     */ import org.hibernate.stat.Statistics;
/*   73:     */ import org.hibernate.stat.spi.StatisticsImplementor;
/*   74:     */ import org.hibernate.transform.CacheableResultTransformer;
/*   75:     */ import org.hibernate.transform.ResultTransformer;
/*   76:     */ import org.hibernate.tuple.entity.EntityMetamodel;
/*   77:     */ import org.hibernate.type.AssociationType;
/*   78:     */ import org.hibernate.type.CollectionType;
/*   79:     */ import org.hibernate.type.EntityType;
/*   80:     */ import org.hibernate.type.Type;
/*   81:     */ import org.hibernate.type.VersionType;
/*   82:     */ import org.jboss.logging.Logger;
/*   83:     */ 
/*   84:     */ public abstract class Loader
/*   85:     */ {
/*   86: 104 */   protected static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, Loader.class.getName());
/*   87:     */   private final SessionFactoryImplementor factory;
/*   88:     */   private ColumnNameCache columnNameCache;
/*   89:     */   
/*   90:     */   public Loader(SessionFactoryImplementor factory)
/*   91:     */   {
/*   92: 110 */     this.factory = factory;
/*   93:     */   }
/*   94:     */   
/*   95:     */   protected abstract String getSQLString();
/*   96:     */   
/*   97:     */   protected abstract Loadable[] getEntityPersisters();
/*   98:     */   
/*   99:     */   protected boolean[] getEntityEagerPropertyFetches()
/*  100:     */   {
/*  101: 135 */     return null;
/*  102:     */   }
/*  103:     */   
/*  104:     */   protected int[] getOwners()
/*  105:     */   {
/*  106: 147 */     return null;
/*  107:     */   }
/*  108:     */   
/*  109:     */   protected EntityType[] getOwnerAssociationTypes()
/*  110:     */   {
/*  111: 157 */     return null;
/*  112:     */   }
/*  113:     */   
/*  114:     */   protected CollectionPersister[] getCollectionPersisters()
/*  115:     */   {
/*  116: 165 */     return null;
/*  117:     */   }
/*  118:     */   
/*  119:     */   protected int[] getCollectionOwners()
/*  120:     */   {
/*  121: 174 */     return null;
/*  122:     */   }
/*  123:     */   
/*  124:     */   protected int[][] getCompositeKeyManyToOneTargetIndices()
/*  125:     */   {
/*  126: 178 */     return (int[][])null;
/*  127:     */   }
/*  128:     */   
/*  129:     */   protected abstract LockMode[] getLockModes(LockOptions paramLockOptions);
/*  130:     */   
/*  131:     */   protected String applyLocks(String sql, LockOptions lockOptions, Dialect dialect)
/*  132:     */     throws HibernateException
/*  133:     */   {
/*  134: 195 */     return sql;
/*  135:     */   }
/*  136:     */   
/*  137:     */   protected boolean upgradeLocks()
/*  138:     */   {
/*  139: 203 */     return false;
/*  140:     */   }
/*  141:     */   
/*  142:     */   protected boolean isSingleRowLoader()
/*  143:     */   {
/*  144: 210 */     return false;
/*  145:     */   }
/*  146:     */   
/*  147:     */   protected String[] getAliases()
/*  148:     */   {
/*  149: 220 */     return null;
/*  150:     */   }
/*  151:     */   
/*  152:     */   protected String preprocessSQL(String sql, QueryParameters parameters, Dialect dialect)
/*  153:     */     throws HibernateException
/*  154:     */   {
/*  155: 229 */     sql = applyLocks(sql, parameters.getLockOptions(), dialect);
/*  156:     */     
/*  157: 231 */     return getFactory().getSettings().isCommentsEnabled() ? prependComment(sql, parameters) : sql;
/*  158:     */   }
/*  159:     */   
/*  160:     */   private String prependComment(String sql, QueryParameters parameters)
/*  161:     */   {
/*  162: 236 */     String comment = parameters.getComment();
/*  163: 237 */     if (comment == null) {
/*  164: 238 */       return sql;
/*  165:     */     }
/*  166: 241 */     return comment.length() + sql.length() + 5 + "/* " + comment + " */ " + sql;
/*  167:     */   }
/*  168:     */   
/*  169:     */   private List doQueryAndInitializeNonLazyCollections(SessionImplementor session, QueryParameters queryParameters, boolean returnProxies)
/*  170:     */     throws HibernateException, SQLException
/*  171:     */   {
/*  172: 259 */     return doQueryAndInitializeNonLazyCollections(session, queryParameters, returnProxies, null);
/*  173:     */   }
/*  174:     */   
/*  175:     */   private List doQueryAndInitializeNonLazyCollections(SessionImplementor session, QueryParameters queryParameters, boolean returnProxies, ResultTransformer forcedResultTransformer)
/*  176:     */     throws HibernateException, SQLException
/*  177:     */   {
/*  178: 273 */     PersistenceContext persistenceContext = session.getPersistenceContext();
/*  179: 274 */     boolean defaultReadOnlyOrig = persistenceContext.isDefaultReadOnly();
/*  180: 275 */     if (queryParameters.isReadOnlyInitialized()) {
/*  181: 278 */       persistenceContext.setDefaultReadOnly(queryParameters.isReadOnly());
/*  182:     */     } else {
/*  183: 283 */       queryParameters.setReadOnly(persistenceContext.isDefaultReadOnly());
/*  184:     */     }
/*  185: 285 */     persistenceContext.beforeLoad();
/*  186:     */     List result;
/*  187:     */     try
/*  188:     */     {
/*  189:     */       try
/*  190:     */       {
/*  191: 289 */         result = doQuery(session, queryParameters, returnProxies, forcedResultTransformer);
/*  192:     */       }
/*  193:     */       finally
/*  194:     */       {
/*  195: 292 */         persistenceContext.afterLoad();
/*  196:     */       }
/*  197: 294 */       persistenceContext.initializeNonLazyCollections();
/*  198:     */     }
/*  199:     */     finally
/*  200:     */     {
/*  201: 298 */       persistenceContext.setDefaultReadOnly(defaultReadOnlyOrig);
/*  202:     */     }
/*  203: 300 */     return result;
/*  204:     */   }
/*  205:     */   
/*  206:     */   public Object loadSingleRow(ResultSet resultSet, SessionImplementor session, QueryParameters queryParameters, boolean returnProxies)
/*  207:     */     throws HibernateException
/*  208:     */   {
/*  209: 320 */     int entitySpan = getEntityPersisters().length;
/*  210: 321 */     List hydratedObjects = entitySpan == 0 ? null : new ArrayList(entitySpan);
/*  211:     */     Object result;
/*  212:     */     try
/*  213:     */     {
/*  214: 326 */       result = getRowFromResultSet(resultSet, session, queryParameters, getLockModes(queryParameters.getLockOptions()), null, hydratedObjects, new EntityKey[entitySpan], returnProxies);
/*  215:     */     }
/*  216:     */     catch (SQLException sqle)
/*  217:     */     {
/*  218: 338 */       throw this.factory.getSQLExceptionHelper().convert(sqle, "could not read next row of results", getSQLString());
/*  219:     */     }
/*  220: 345 */     initializeEntitiesAndCollections(hydratedObjects, resultSet, session, queryParameters.isReadOnly(session));
/*  221:     */     
/*  222:     */ 
/*  223:     */ 
/*  224:     */ 
/*  225:     */ 
/*  226: 351 */     session.getPersistenceContext().initializeNonLazyCollections();
/*  227: 352 */     return result;
/*  228:     */   }
/*  229:     */   
/*  230:     */   private Object sequentialLoad(ResultSet resultSet, SessionImplementor session, QueryParameters queryParameters, boolean returnProxies, EntityKey keyToRead)
/*  231:     */     throws HibernateException
/*  232:     */   {
/*  233: 362 */     int entitySpan = getEntityPersisters().length;
/*  234: 363 */     List hydratedObjects = entitySpan == 0 ? null : new ArrayList(entitySpan);
/*  235:     */     
/*  236:     */ 
/*  237: 366 */     Object result = null;
/*  238: 367 */     EntityKey[] loadedKeys = new EntityKey[entitySpan];
/*  239:     */     try
/*  240:     */     {
/*  241:     */       do
/*  242:     */       {
/*  243: 371 */         Object loaded = getRowFromResultSet(resultSet, session, queryParameters, getLockModes(queryParameters.getLockOptions()), null, hydratedObjects, loadedKeys, returnProxies);
/*  244: 381 */         if (result == null) {
/*  245: 382 */           result = loaded;
/*  246:     */         }
/*  247: 385 */         if (!keyToRead.equals(loadedKeys[0])) {
/*  248:     */           break;
/*  249:     */         }
/*  250: 385 */       } while (resultSet.next());
/*  251:     */     }
/*  252:     */     catch (SQLException sqle)
/*  253:     */     {
/*  254: 388 */       throw this.factory.getSQLExceptionHelper().convert(sqle, "could not doAfterTransactionCompletion sequential read of results (forward)", getSQLString());
/*  255:     */     }
/*  256: 395 */     initializeEntitiesAndCollections(hydratedObjects, resultSet, session, queryParameters.isReadOnly(session));
/*  257:     */     
/*  258:     */ 
/*  259:     */ 
/*  260:     */ 
/*  261:     */ 
/*  262: 401 */     session.getPersistenceContext().initializeNonLazyCollections();
/*  263: 402 */     return result;
/*  264:     */   }
/*  265:     */   
/*  266:     */   public Object loadSequentialRowsForward(ResultSet resultSet, SessionImplementor session, QueryParameters queryParameters, boolean returnProxies)
/*  267:     */     throws HibernateException
/*  268:     */   {
/*  269:     */     try
/*  270:     */     {
/*  271: 428 */       if (resultSet.isAfterLast()) {
/*  272: 430 */         return null;
/*  273:     */       }
/*  274: 433 */       if (resultSet.isBeforeFirst()) {
/*  275: 434 */         resultSet.next();
/*  276:     */       }
/*  277: 443 */       EntityKey currentKey = getKeyFromResultSet(0, getEntityPersisters()[0], null, resultSet, session);
/*  278:     */       
/*  279:     */ 
/*  280:     */ 
/*  281:     */ 
/*  282:     */ 
/*  283:     */ 
/*  284:     */ 
/*  285: 451 */       return sequentialLoad(resultSet, session, queryParameters, returnProxies, currentKey);
/*  286:     */     }
/*  287:     */     catch (SQLException sqle)
/*  288:     */     {
/*  289: 454 */       throw this.factory.getSQLExceptionHelper().convert(sqle, "could not doAfterTransactionCompletion sequential read of results (forward)", getSQLString());
/*  290:     */     }
/*  291:     */   }
/*  292:     */   
/*  293:     */   public Object loadSequentialRowsReverse(ResultSet resultSet, SessionImplementor session, QueryParameters queryParameters, boolean returnProxies, boolean isLogicallyAfterLast)
/*  294:     */     throws HibernateException
/*  295:     */   {
/*  296:     */     try
/*  297:     */     {
/*  298: 486 */       if (resultSet.isFirst()) {
/*  299: 488 */         return null;
/*  300:     */       }
/*  301: 491 */       EntityKey keyToRead = null;
/*  302: 504 */       if ((resultSet.isAfterLast()) && (isLogicallyAfterLast))
/*  303:     */       {
/*  304: 506 */         resultSet.last();
/*  305: 507 */         keyToRead = getKeyFromResultSet(0, getEntityPersisters()[0], null, resultSet, session);
/*  306:     */       }
/*  307:     */       else
/*  308:     */       {
/*  309: 520 */         resultSet.previous();
/*  310:     */         
/*  311:     */ 
/*  312:     */ 
/*  313:     */ 
/*  314:     */ 
/*  315: 526 */         boolean firstPass = true;
/*  316: 527 */         EntityKey lastKey = getKeyFromResultSet(0, getEntityPersisters()[0], null, resultSet, session);
/*  317: 534 */         while (resultSet.previous())
/*  318:     */         {
/*  319: 535 */           EntityKey checkKey = getKeyFromResultSet(0, getEntityPersisters()[0], null, resultSet, session);
/*  320: 543 */           if (firstPass)
/*  321:     */           {
/*  322: 544 */             firstPass = false;
/*  323: 545 */             keyToRead = checkKey;
/*  324:     */           }
/*  325: 548 */           if (!lastKey.equals(checkKey)) {
/*  326:     */             break;
/*  327:     */           }
/*  328:     */         }
/*  329:     */       }
/*  330: 557 */       while (resultSet.previous())
/*  331:     */       {
/*  332: 558 */         EntityKey checkKey = getKeyFromResultSet(0, getEntityPersisters()[0], null, resultSet, session);
/*  333: 566 */         if (!keyToRead.equals(checkKey)) {
/*  334:     */           break;
/*  335:     */         }
/*  336:     */       }
/*  337: 573 */       resultSet.next();
/*  338:     */       
/*  339:     */ 
/*  340: 576 */       return sequentialLoad(resultSet, session, queryParameters, returnProxies, keyToRead);
/*  341:     */     }
/*  342:     */     catch (SQLException sqle)
/*  343:     */     {
/*  344: 579 */       throw this.factory.getSQLExceptionHelper().convert(sqle, "could not doAfterTransactionCompletion sequential read of results (forward)", getSQLString());
/*  345:     */     }
/*  346:     */   }
/*  347:     */   
/*  348:     */   private static EntityKey getOptionalObjectKey(QueryParameters queryParameters, SessionImplementor session)
/*  349:     */   {
/*  350: 588 */     Object optionalObject = queryParameters.getOptionalObject();
/*  351: 589 */     Serializable optionalId = queryParameters.getOptionalId();
/*  352: 590 */     String optionalEntityName = queryParameters.getOptionalEntityName();
/*  353: 592 */     if ((optionalObject != null) && (optionalEntityName != null)) {
/*  354: 593 */       return session.generateEntityKey(optionalId, session.getEntityPersister(optionalEntityName, optionalObject));
/*  355:     */     }
/*  356: 596 */     return null;
/*  357:     */   }
/*  358:     */   
/*  359:     */   private Object getRowFromResultSet(ResultSet resultSet, SessionImplementor session, QueryParameters queryParameters, LockMode[] lockModesArray, EntityKey optionalObjectKey, List hydratedObjects, EntityKey[] keys, boolean returnProxies)
/*  360:     */     throws SQLException, HibernateException
/*  361:     */   {
/*  362: 610 */     return getRowFromResultSet(resultSet, session, queryParameters, lockModesArray, optionalObjectKey, hydratedObjects, keys, returnProxies, null);
/*  363:     */   }
/*  364:     */   
/*  365:     */   private Object getRowFromResultSet(ResultSet resultSet, SessionImplementor session, QueryParameters queryParameters, LockMode[] lockModesArray, EntityKey optionalObjectKey, List hydratedObjects, EntityKey[] keys, boolean returnProxies, ResultTransformer forcedResultTransformer)
/*  366:     */     throws SQLException, HibernateException
/*  367:     */   {
/*  368: 633 */     Loadable[] persisters = getEntityPersisters();
/*  369: 634 */     int entitySpan = persisters.length;
/*  370: 635 */     extractKeysFromResultSet(persisters, queryParameters, resultSet, session, keys, lockModesArray, hydratedObjects);
/*  371:     */     
/*  372: 637 */     registerNonExists(keys, persisters, session);
/*  373:     */     
/*  374:     */ 
/*  375: 640 */     Object[] row = getRow(resultSet, persisters, keys, queryParameters.getOptionalObject(), optionalObjectKey, lockModesArray, hydratedObjects, session);
/*  376:     */     
/*  377:     */ 
/*  378:     */ 
/*  379:     */ 
/*  380:     */ 
/*  381:     */ 
/*  382:     */ 
/*  383:     */ 
/*  384:     */ 
/*  385:     */ 
/*  386: 651 */     readCollectionElements(row, resultSet, session);
/*  387: 653 */     if (returnProxies) {
/*  388: 655 */       for (int i = 0; i < entitySpan; i++)
/*  389:     */       {
/*  390: 656 */         Object entity = row[i];
/*  391: 657 */         Object proxy = session.getPersistenceContext().proxyFor(persisters[i], keys[i], entity);
/*  392: 658 */         if (entity != proxy)
/*  393:     */         {
/*  394: 660 */           ((HibernateProxy)proxy).getHibernateLazyInitializer().setImplementation(entity);
/*  395: 661 */           row[i] = proxy;
/*  396:     */         }
/*  397:     */       }
/*  398:     */     }
/*  399: 666 */     applyPostLoadLocks(row, lockModesArray, session);
/*  400:     */     
/*  401: 668 */     return forcedResultTransformer == null ? getResultColumnOrRow(row, queryParameters.getResultTransformer(), resultSet, session) : forcedResultTransformer.transformTuple(getResultRow(row, resultSet, session), getResultRowAliases());
/*  402:     */   }
/*  403:     */   
/*  404:     */   protected void extractKeysFromResultSet(Loadable[] persisters, QueryParameters queryParameters, ResultSet resultSet, SessionImplementor session, EntityKey[] keys, LockMode[] lockModes, List hydratedObjects)
/*  405:     */     throws SQLException
/*  406:     */   {
/*  407: 685 */     int entitySpan = persisters.length;
/*  408:     */     
/*  409:     */ 
/*  410: 688 */     Serializable optionalId = queryParameters.getOptionalId();
/*  411:     */     int numberOfPersistersToProcess;
/*  412:     */     int numberOfPersistersToProcess;
/*  413: 689 */     if ((isSingleRowLoader()) && (optionalId != null))
/*  414:     */     {
/*  415: 690 */       keys[(entitySpan - 1)] = session.generateEntityKey(optionalId, persisters[(entitySpan - 1)]);
/*  416:     */       
/*  417: 692 */       numberOfPersistersToProcess = entitySpan - 1;
/*  418:     */     }
/*  419:     */     else
/*  420:     */     {
/*  421: 695 */       numberOfPersistersToProcess = entitySpan;
/*  422:     */     }
/*  423: 698 */     Object[] hydratedKeyState = new Object[numberOfPersistersToProcess];
/*  424: 700 */     for (int i = 0; i < numberOfPersistersToProcess; i++)
/*  425:     */     {
/*  426: 701 */       Type idType = persisters[i].getIdentifierType();
/*  427: 702 */       hydratedKeyState[i] = idType.hydrate(resultSet, getEntityAliases()[i].getSuffixedKeyAliases(), session, null);
/*  428:     */     }
/*  429: 705 */     for (int i = 0; i < numberOfPersistersToProcess; i++)
/*  430:     */     {
/*  431: 706 */       Type idType = persisters[i].getIdentifierType();
/*  432: 707 */       if ((idType.isComponentType()) && (getCompositeKeyManyToOneTargetIndices() != null))
/*  433:     */       {
/*  434: 709 */         int[] keyManyToOneTargetIndices = getCompositeKeyManyToOneTargetIndices()[i];
/*  435: 712 */         if (keyManyToOneTargetIndices != null) {
/*  436: 713 */           for (int targetIndex : keyManyToOneTargetIndices)
/*  437:     */           {
/*  438: 714 */             if (targetIndex < numberOfPersistersToProcess)
/*  439:     */             {
/*  440: 715 */               Type targetIdType = persisters[targetIndex].getIdentifierType();
/*  441: 716 */               Serializable targetId = (Serializable)targetIdType.resolve(hydratedKeyState[targetIndex], session, null);
/*  442:     */               
/*  443:     */ 
/*  444:     */ 
/*  445:     */ 
/*  446:     */ 
/*  447: 722 */               keys[targetIndex] = session.generateEntityKey(targetId, persisters[targetIndex]);
/*  448:     */             }
/*  449: 726 */             Object object = session.getEntityUsingInterceptor(keys[targetIndex]);
/*  450: 727 */             if (object != null) {
/*  451: 729 */               instanceAlreadyLoaded(resultSet, targetIndex, persisters[targetIndex], keys[targetIndex], object, lockModes[targetIndex], session);
/*  452:     */             } else {
/*  453: 740 */               instanceNotYetLoaded(resultSet, targetIndex, persisters[targetIndex], getEntityAliases()[targetIndex].getRowIdAlias(), keys[targetIndex], lockModes[targetIndex], getOptionalObjectKey(queryParameters, session), queryParameters.getOptionalObject(), hydratedObjects, session);
/*  454:     */             }
/*  455:     */           }
/*  456:     */         }
/*  457:     */       }
/*  458: 756 */       Serializable resolvedId = (Serializable)idType.resolve(hydratedKeyState[i], session, null);
/*  459: 757 */       keys[i] = (resolvedId == null ? null : session.generateEntityKey(resolvedId, persisters[i]));
/*  460:     */     }
/*  461:     */   }
/*  462:     */   
/*  463:     */   protected void applyPostLoadLocks(Object[] row, LockMode[] lockModesArray, SessionImplementor session) {}
/*  464:     */   
/*  465:     */   private void readCollectionElements(Object[] row, ResultSet resultSet, SessionImplementor session)
/*  466:     */     throws SQLException, HibernateException
/*  467:     */   {
/*  468: 772 */     CollectionPersister[] collectionPersisters = getCollectionPersisters();
/*  469: 773 */     if (collectionPersisters != null)
/*  470:     */     {
/*  471: 775 */       CollectionAliases[] descriptors = getCollectionAliases();
/*  472: 776 */       int[] collectionOwners = getCollectionOwners();
/*  473: 778 */       for (int i = 0; i < collectionPersisters.length; i++)
/*  474:     */       {
/*  475: 780 */         boolean hasCollectionOwners = (collectionOwners != null) && (collectionOwners[i] > -1);
/*  476:     */         
/*  477:     */ 
/*  478:     */ 
/*  479:     */ 
/*  480: 785 */         Object owner = hasCollectionOwners ? row[collectionOwners[i]] : null;
/*  481:     */         
/*  482:     */ 
/*  483:     */ 
/*  484: 789 */         CollectionPersister collectionPersister = collectionPersisters[i];
/*  485:     */         Serializable key;
/*  486:     */         Serializable key;
/*  487: 791 */         if (owner == null) {
/*  488: 792 */           key = null;
/*  489:     */         } else {
/*  490: 795 */           key = collectionPersister.getCollectionType().getKeyOfOwner(owner, session);
/*  491:     */         }
/*  492: 800 */         readCollectionElement(owner, key, collectionPersister, descriptors[i], resultSet, session);
/*  493:     */       }
/*  494:     */     }
/*  495:     */   }
/*  496:     */   
/*  497:     */   private List doQuery(SessionImplementor session, QueryParameters queryParameters, boolean returnProxies, ResultTransformer forcedResultTransformer)
/*  498:     */     throws SQLException, HibernateException
/*  499:     */   {
/*  500: 820 */     RowSelection selection = queryParameters.getRowSelection();
/*  501: 821 */     int maxRows = hasMaxRows(selection) ? selection.getMaxRows().intValue() : 2147483647;
/*  502:     */     
/*  503:     */ 
/*  504:     */ 
/*  505: 825 */     int entitySpan = getEntityPersisters().length;
/*  506:     */     
/*  507: 827 */     ArrayList hydratedObjects = entitySpan == 0 ? null : new ArrayList(entitySpan * 10);
/*  508: 828 */     PreparedStatement st = prepareQueryStatement(queryParameters, false, session);
/*  509: 829 */     ResultSet rs = getResultSet(st, queryParameters.hasAutoDiscoverScalarTypes(), queryParameters.isCallable(), selection, session);
/*  510:     */     
/*  511:     */ 
/*  512:     */ 
/*  513:     */ 
/*  514:     */ 
/*  515:     */ 
/*  516:     */ 
/*  517: 837 */     EntityKey optionalObjectKey = getOptionalObjectKey(queryParameters, session);
/*  518: 838 */     LockMode[] lockModesArray = getLockModes(queryParameters.getLockOptions());
/*  519: 839 */     boolean createSubselects = isSubselectLoadingEnabled();
/*  520: 840 */     List subselectResultKeys = createSubselects ? new ArrayList() : null;
/*  521: 841 */     List results = new ArrayList();
/*  522:     */     try
/*  523:     */     {
/*  524: 845 */       handleEmptyCollections(queryParameters.getCollectionKeys(), rs, session);
/*  525:     */       
/*  526: 847 */       EntityKey[] keys = new EntityKey[entitySpan];
/*  527:     */       
/*  528: 849 */       LOG.trace("Processing result set");
/*  529: 852 */       for (int count = 0; (count < maxRows) && (rs.next()); count++)
/*  530:     */       {
/*  531: 854 */         LOG.debugf("Result set row: %s", Integer.valueOf(count));
/*  532:     */         
/*  533: 856 */         Object result = getRowFromResultSet(rs, session, queryParameters, lockModesArray, optionalObjectKey, hydratedObjects, keys, returnProxies, forcedResultTransformer);
/*  534:     */         
/*  535:     */ 
/*  536:     */ 
/*  537:     */ 
/*  538:     */ 
/*  539:     */ 
/*  540:     */ 
/*  541:     */ 
/*  542:     */ 
/*  543:     */ 
/*  544: 867 */         results.add(result);
/*  545: 869 */         if (createSubselects)
/*  546:     */         {
/*  547: 870 */           subselectResultKeys.add(keys);
/*  548: 871 */           keys = new EntityKey[entitySpan];
/*  549:     */         }
/*  550:     */       }
/*  551: 876 */       LOG.tracev("Done processing result set ({0} rows)", Integer.valueOf(count));
/*  552:     */     }
/*  553:     */     finally
/*  554:     */     {
/*  555: 880 */       st.close();
/*  556:     */     }
/*  557: 883 */     initializeEntitiesAndCollections(hydratedObjects, rs, session, queryParameters.isReadOnly(session));
/*  558: 885 */     if (createSubselects) {
/*  559: 885 */       createSubselects(subselectResultKeys, queryParameters, session);
/*  560:     */     }
/*  561: 887 */     return results;
/*  562:     */   }
/*  563:     */   
/*  564:     */   protected boolean isSubselectLoadingEnabled()
/*  565:     */   {
/*  566: 892 */     return false;
/*  567:     */   }
/*  568:     */   
/*  569:     */   protected boolean hasSubselectLoadableCollections()
/*  570:     */   {
/*  571: 896 */     Loadable[] loadables = getEntityPersisters();
/*  572: 897 */     for (int i = 0; i < loadables.length; i++) {
/*  573: 898 */       if (loadables[i].hasSubselectLoadableCollections()) {
/*  574: 898 */         return true;
/*  575:     */       }
/*  576:     */     }
/*  577: 900 */     return false;
/*  578:     */   }
/*  579:     */   
/*  580:     */   private static Set[] transpose(List keys)
/*  581:     */   {
/*  582: 904 */     Set[] result = new Set[((EntityKey[])keys.get(0)).length];
/*  583: 905 */     for (int j = 0; j < result.length; j++)
/*  584:     */     {
/*  585: 906 */       result[j] = new HashSet(keys.size());
/*  586: 907 */       for (int i = 0; i < keys.size(); i++) {
/*  587: 908 */         result[j].add(((EntityKey[])(EntityKey[])keys.get(i))[j]);
/*  588:     */       }
/*  589:     */     }
/*  590: 911 */     return result;
/*  591:     */   }
/*  592:     */   
/*  593:     */   private void createSubselects(List keys, QueryParameters queryParameters, SessionImplementor session)
/*  594:     */   {
/*  595: 915 */     if (keys.size() > 1)
/*  596:     */     {
/*  597: 917 */       Set[] keySets = transpose(keys);
/*  598:     */       
/*  599: 919 */       Map namedParameterLocMap = buildNamedParameterLocMap(queryParameters);
/*  600:     */       
/*  601: 921 */       Loadable[] loadables = getEntityPersisters();
/*  602: 922 */       String[] aliases = getAliases();
/*  603: 923 */       Iterator iter = keys.iterator();
/*  604: 924 */       while (iter.hasNext())
/*  605:     */       {
/*  606: 926 */         EntityKey[] rowKeys = (EntityKey[])iter.next();
/*  607: 927 */         for (int i = 0; i < rowKeys.length; i++) {
/*  608: 929 */           if ((rowKeys[i] != null) && (loadables[i].hasSubselectLoadableCollections()))
/*  609:     */           {
/*  610: 931 */             SubselectFetch subselectFetch = new SubselectFetch(aliases[i], loadables[i], queryParameters, keySets[i], namedParameterLocMap);
/*  611:     */             
/*  612:     */ 
/*  613:     */ 
/*  614:     */ 
/*  615:     */ 
/*  616:     */ 
/*  617:     */ 
/*  618:     */ 
/*  619: 940 */             session.getPersistenceContext().getBatchFetchQueue().addSubselect(rowKeys[i], subselectFetch);
/*  620:     */           }
/*  621:     */         }
/*  622:     */       }
/*  623:     */     }
/*  624:     */   }
/*  625:     */   
/*  626:     */   private Map buildNamedParameterLocMap(QueryParameters queryParameters)
/*  627:     */   {
/*  628: 952 */     if (queryParameters.getNamedParameters() != null)
/*  629:     */     {
/*  630: 953 */       Map namedParameterLocMap = new HashMap();
/*  631: 954 */       Iterator piter = queryParameters.getNamedParameters().keySet().iterator();
/*  632: 955 */       while (piter.hasNext())
/*  633:     */       {
/*  634: 956 */         String name = (String)piter.next();
/*  635: 957 */         namedParameterLocMap.put(name, getNamedParameterLocs(name));
/*  636:     */       }
/*  637: 962 */       return namedParameterLocMap;
/*  638:     */     }
/*  639: 965 */     return null;
/*  640:     */   }
/*  641:     */   
/*  642:     */   private void initializeEntitiesAndCollections(List hydratedObjects, Object resultSetId, SessionImplementor session, boolean readOnly)
/*  643:     */     throws HibernateException
/*  644:     */   {
/*  645: 976 */     CollectionPersister[] collectionPersisters = getCollectionPersisters();
/*  646: 977 */     if (collectionPersisters != null) {
/*  647: 978 */       for (int i = 0; i < collectionPersisters.length; i++) {
/*  648: 979 */         if (collectionPersisters[i].isArray()) {
/*  649: 985 */           endCollectionLoad(resultSetId, session, collectionPersisters[i]);
/*  650:     */         }
/*  651:     */       }
/*  652:     */     }
/*  653:     */     PostLoadEvent post;
/*  654:     */     PreLoadEvent pre;
/*  655:     */     PostLoadEvent post;
/*  656: 993 */     if (session.isEventSource())
/*  657:     */     {
/*  658: 994 */       PreLoadEvent pre = new PreLoadEvent((EventSource)session);
/*  659: 995 */       post = new PostLoadEvent((EventSource)session);
/*  660:     */     }
/*  661:     */     else
/*  662:     */     {
/*  663: 998 */       pre = null;
/*  664: 999 */       post = null;
/*  665:     */     }
/*  666:1002 */     if (hydratedObjects != null)
/*  667:     */     {
/*  668:1003 */       int hydratedObjectsSize = hydratedObjects.size();
/*  669:1004 */       LOG.tracev("Total objects hydrated: {0}", Integer.valueOf(hydratedObjectsSize));
/*  670:1005 */       for (int i = 0; i < hydratedObjectsSize; i++) {
/*  671:1006 */         TwoPhaseLoad.initializeEntity(hydratedObjects.get(i), readOnly, session, pre, post);
/*  672:     */       }
/*  673:     */     }
/*  674:1010 */     if (collectionPersisters != null) {
/*  675:1011 */       for (int i = 0; i < collectionPersisters.length; i++) {
/*  676:1012 */         if (!collectionPersisters[i].isArray()) {
/*  677:1017 */           endCollectionLoad(resultSetId, session, collectionPersisters[i]);
/*  678:     */         }
/*  679:     */       }
/*  680:     */     }
/*  681:     */   }
/*  682:     */   
/*  683:     */   private void endCollectionLoad(Object resultSetId, SessionImplementor session, CollectionPersister collectionPersister)
/*  684:     */   {
/*  685:1029 */     session.getPersistenceContext().getLoadContexts().getCollectionLoadContext((ResultSet)resultSetId).endLoadingCollections(collectionPersister);
/*  686:     */   }
/*  687:     */   
/*  688:     */   protected ResultTransformer resolveResultTransformer(ResultTransformer resultTransformer)
/*  689:     */   {
/*  690:1043 */     return resultTransformer;
/*  691:     */   }
/*  692:     */   
/*  693:     */   protected List getResultList(List results, ResultTransformer resultTransformer)
/*  694:     */     throws QueryException
/*  695:     */   {
/*  696:1047 */     return results;
/*  697:     */   }
/*  698:     */   
/*  699:     */   protected boolean areResultSetRowsTransformedImmediately()
/*  700:     */   {
/*  701:1055 */     return false;
/*  702:     */   }
/*  703:     */   
/*  704:     */   protected String[] getResultRowAliases()
/*  705:     */   {
/*  706:1063 */     return null;
/*  707:     */   }
/*  708:     */   
/*  709:     */   protected Object getResultColumnOrRow(Object[] row, ResultTransformer transformer, ResultSet rs, SessionImplementor session)
/*  710:     */     throws SQLException, HibernateException
/*  711:     */   {
/*  712:1073 */     return row;
/*  713:     */   }
/*  714:     */   
/*  715:     */   protected boolean[] includeInResultRow()
/*  716:     */   {
/*  717:1077 */     return null;
/*  718:     */   }
/*  719:     */   
/*  720:     */   protected Object[] getResultRow(Object[] row, ResultSet rs, SessionImplementor session)
/*  721:     */     throws SQLException, HibernateException
/*  722:     */   {
/*  723:1084 */     return row;
/*  724:     */   }
/*  725:     */   
/*  726:     */   private void registerNonExists(EntityKey[] keys, Loadable[] persisters, SessionImplementor session)
/*  727:     */   {
/*  728:1097 */     int[] owners = getOwners();
/*  729:1098 */     if (owners != null)
/*  730:     */     {
/*  731:1100 */       EntityType[] ownerAssociationTypes = getOwnerAssociationTypes();
/*  732:1101 */       for (int i = 0; i < keys.length; i++)
/*  733:     */       {
/*  734:1103 */         int owner = owners[i];
/*  735:1104 */         if (owner > -1)
/*  736:     */         {
/*  737:1105 */           EntityKey ownerKey = keys[owner];
/*  738:1106 */           if ((keys[i] == null) && (ownerKey != null))
/*  739:     */           {
/*  740:1108 */             PersistenceContext persistenceContext = session.getPersistenceContext();
/*  741:     */             
/*  742:     */ 
/*  743:     */ 
/*  744:     */ 
/*  745:     */ 
/*  746:     */ 
/*  747:     */ 
/*  748:     */ 
/*  749:     */ 
/*  750:     */ 
/*  751:     */ 
/*  752:     */ 
/*  753:     */ 
/*  754:     */ 
/*  755:     */ 
/*  756:     */ 
/*  757:     */ 
/*  758:     */ 
/*  759:     */ 
/*  760:1128 */             boolean isOneToOneAssociation = (ownerAssociationTypes != null) && (ownerAssociationTypes[i] != null) && (ownerAssociationTypes[i].isOneToOne());
/*  761:1131 */             if (isOneToOneAssociation) {
/*  762:1132 */               persistenceContext.addNullProperty(ownerKey, ownerAssociationTypes[i].getPropertyName());
/*  763:     */             }
/*  764:     */           }
/*  765:     */         }
/*  766:     */       }
/*  767:     */     }
/*  768:     */   }
/*  769:     */   
/*  770:     */   private void readCollectionElement(Object optionalOwner, Serializable optionalKey, CollectionPersister persister, CollectionAliases descriptor, ResultSet rs, SessionImplementor session)
/*  771:     */     throws HibernateException, SQLException
/*  772:     */   {
/*  773:1163 */     PersistenceContext persistenceContext = session.getPersistenceContext();
/*  774:     */     
/*  775:1165 */     Serializable collectionRowKey = (Serializable)persister.readKey(rs, descriptor.getSuffixedKeyAliases(), session);
/*  776:1171 */     if (collectionRowKey != null)
/*  777:     */     {
/*  778:1174 */       if (LOG.isDebugEnabled()) {
/*  779:1175 */         LOG.debugf("Found row of collection: %s", MessageHelper.collectionInfoString(persister, collectionRowKey, getFactory()));
/*  780:     */       }
/*  781:1179 */       Object owner = optionalOwner;
/*  782:1180 */       if (owner == null)
/*  783:     */       {
/*  784:1181 */         owner = persistenceContext.getCollectionOwner(collectionRowKey, persister);
/*  785:1182 */         if (owner != null) {}
/*  786:     */       }
/*  787:1191 */       PersistentCollection rowCollection = persistenceContext.getLoadContexts().getCollectionLoadContext(rs).getLoadingCollection(persister, collectionRowKey);
/*  788:1195 */       if (rowCollection != null) {
/*  789:1196 */         rowCollection.readFrom(rs, persister, descriptor, owner);
/*  790:     */       }
/*  791:     */     }
/*  792:1200 */     else if (optionalKey != null)
/*  793:     */     {
/*  794:1205 */       if (LOG.isDebugEnabled()) {
/*  795:1206 */         LOG.debugf("Result set contains (possibly empty) collection: %s", MessageHelper.collectionInfoString(persister, optionalKey, getFactory()));
/*  796:     */       }
/*  797:1210 */       persistenceContext.getLoadContexts().getCollectionLoadContext(rs).getLoadingCollection(persister, optionalKey);
/*  798:     */     }
/*  799:     */   }
/*  800:     */   
/*  801:     */   private void handleEmptyCollections(Serializable[] keys, Object resultSetId, SessionImplementor session)
/*  802:     */   {
/*  803:1230 */     if (keys != null)
/*  804:     */     {
/*  805:1235 */       CollectionPersister[] collectionPersisters = getCollectionPersisters();
/*  806:1236 */       for (int j = 0; j < collectionPersisters.length; j++) {
/*  807:1237 */         for (int i = 0; i < keys.length; i++)
/*  808:     */         {
/*  809:1240 */           if (LOG.isDebugEnabled()) {
/*  810:1241 */             LOG.debugf("Result set contains (possibly empty) collection: %s", MessageHelper.collectionInfoString(collectionPersisters[j], keys[i], getFactory()));
/*  811:     */           }
/*  812:1245 */           session.getPersistenceContext().getLoadContexts().getCollectionLoadContext((ResultSet)resultSetId).getLoadingCollection(collectionPersisters[j], keys[i]);
/*  813:     */         }
/*  814:     */       }
/*  815:     */     }
/*  816:     */   }
/*  817:     */   
/*  818:     */   private EntityKey getKeyFromResultSet(int i, Loadable persister, Serializable id, ResultSet rs, SessionImplementor session)
/*  819:     */     throws HibernateException, SQLException
/*  820:     */   {
/*  821:     */     Serializable resultId;
/*  822:     */     Serializable resultId;
/*  823:1276 */     if ((isSingleRowLoader()) && (id != null))
/*  824:     */     {
/*  825:1277 */       resultId = id;
/*  826:     */     }
/*  827:     */     else
/*  828:     */     {
/*  829:1281 */       Type idType = persister.getIdentifierType();
/*  830:1282 */       resultId = (Serializable)idType.nullSafeGet(rs, getEntityAliases()[i].getSuffixedKeyAliases(), session, null);
/*  831:     */       
/*  832:     */ 
/*  833:     */ 
/*  834:     */ 
/*  835:     */ 
/*  836:     */ 
/*  837:1289 */       boolean idIsResultId = (id != null) && (resultId != null) && (idType.isEqual(id, resultId, this.factory));
/*  838:1293 */       if (idIsResultId) {
/*  839:1293 */         resultId = id;
/*  840:     */       }
/*  841:     */     }
/*  842:1296 */     return resultId == null ? null : session.generateEntityKey(resultId, persister);
/*  843:     */   }
/*  844:     */   
/*  845:     */   private void checkVersion(int i, Loadable persister, Serializable id, Object entity, ResultSet rs, SessionImplementor session)
/*  846:     */     throws HibernateException, SQLException
/*  847:     */   {
/*  848:1313 */     Object version = session.getPersistenceContext().getEntry(entity).getVersion();
/*  849:1315 */     if (version != null)
/*  850:     */     {
/*  851:1316 */       VersionType versionType = persister.getVersionType();
/*  852:1317 */       Object currentVersion = versionType.nullSafeGet(rs, getEntityAliases()[i].getSuffixedVersionAliases(), session, null);
/*  853:1323 */       if (!versionType.isEqual(version, currentVersion))
/*  854:     */       {
/*  855:1324 */         if (session.getFactory().getStatistics().isStatisticsEnabled()) {
/*  856:1325 */           session.getFactory().getStatisticsImplementor().optimisticFailure(persister.getEntityName());
/*  857:     */         }
/*  858:1328 */         throw new StaleObjectStateException(persister.getEntityName(), id);
/*  859:     */       }
/*  860:     */     }
/*  861:     */   }
/*  862:     */   
/*  863:     */   private Object[] getRow(ResultSet rs, Loadable[] persisters, EntityKey[] keys, Object optionalObject, EntityKey optionalObjectKey, LockMode[] lockModes, List hydratedObjects, SessionImplementor session)
/*  864:     */     throws HibernateException, SQLException
/*  865:     */   {
/*  866:1352 */     int cols = persisters.length;
/*  867:1353 */     EntityAliases[] descriptors = getEntityAliases();
/*  868:1355 */     if (LOG.isDebugEnabled()) {
/*  869:1355 */       LOG.debugf("Result row: %s", StringHelper.toString(keys));
/*  870:     */     }
/*  871:1357 */     Object[] rowResults = new Object[cols];
/*  872:1359 */     for (int i = 0; i < cols; i++)
/*  873:     */     {
/*  874:1361 */       Object object = null;
/*  875:1362 */       EntityKey key = keys[i];
/*  876:1364 */       if (keys[i] != null)
/*  877:     */       {
/*  878:1370 */         object = session.getEntityUsingInterceptor(key);
/*  879:1371 */         if (object != null) {
/*  880:1373 */           instanceAlreadyLoaded(rs, i, persisters[i], key, object, lockModes[i], session);
/*  881:     */         } else {
/*  882:1384 */           object = instanceNotYetLoaded(rs, i, persisters[i], descriptors[i].getRowIdAlias(), key, lockModes[i], optionalObjectKey, optionalObject, hydratedObjects, session);
/*  883:     */         }
/*  884:     */       }
/*  885:1400 */       rowResults[i] = object;
/*  886:     */     }
/*  887:1404 */     return rowResults;
/*  888:     */   }
/*  889:     */   
/*  890:     */   private void instanceAlreadyLoaded(ResultSet rs, int i, Loadable persister, EntityKey key, Object object, LockMode lockMode, SessionImplementor session)
/*  891:     */     throws HibernateException, SQLException
/*  892:     */   {
/*  893:1419 */     if (!persister.isInstance(object)) {
/*  894:1420 */       throw new WrongClassException("loaded object was of wrong class " + object.getClass(), key.getIdentifier(), persister.getEntityName());
/*  895:     */     }
/*  896:1427 */     if ((LockMode.NONE != lockMode) && (upgradeLocks()))
/*  897:     */     {
/*  898:1429 */       boolean isVersionCheckNeeded = (persister.isVersioned()) && (session.getPersistenceContext().getEntry(object).getLockMode().lessThan(lockMode));
/*  899:1435 */       if (isVersionCheckNeeded)
/*  900:     */       {
/*  901:1437 */         checkVersion(i, persister, key.getIdentifier(), object, rs, session);
/*  902:     */         
/*  903:1439 */         session.getPersistenceContext().getEntry(object).setLockMode(lockMode);
/*  904:     */       }
/*  905:     */     }
/*  906:     */   }
/*  907:     */   
/*  908:     */   private Object instanceNotYetLoaded(ResultSet rs, int i, Loadable persister, String rowIdAlias, EntityKey key, LockMode lockMode, EntityKey optionalObjectKey, Object optionalObject, List hydratedObjects, SessionImplementor session)
/*  909:     */     throws HibernateException, SQLException
/*  910:     */   {
/*  911:1460 */     String instanceClass = getInstanceClass(rs, i, persister, key.getIdentifier(), session);
/*  912:     */     Object object;
/*  913:     */     Object object;
/*  914:1469 */     if ((optionalObjectKey != null) && (key.equals(optionalObjectKey))) {
/*  915:1471 */       object = optionalObject;
/*  916:     */     } else {
/*  917:1475 */       object = session.instantiate(instanceClass, key.getIdentifier());
/*  918:     */     }
/*  919:1483 */     LockMode acquiredLockMode = lockMode == LockMode.NONE ? LockMode.READ : lockMode;
/*  920:1484 */     loadFromResultSet(rs, i, object, instanceClass, key, rowIdAlias, acquiredLockMode, persister, session);
/*  921:     */     
/*  922:     */ 
/*  923:     */ 
/*  924:     */ 
/*  925:     */ 
/*  926:     */ 
/*  927:     */ 
/*  928:     */ 
/*  929:     */ 
/*  930:     */ 
/*  931:     */ 
/*  932:     */ 
/*  933:1497 */     hydratedObjects.add(object);
/*  934:     */     
/*  935:1499 */     return object;
/*  936:     */   }
/*  937:     */   
/*  938:     */   private boolean isEagerPropertyFetchEnabled(int i)
/*  939:     */   {
/*  940:1503 */     boolean[] array = getEntityEagerPropertyFetches();
/*  941:1504 */     return (array != null) && (array[i] != 0);
/*  942:     */   }
/*  943:     */   
/*  944:     */   private void loadFromResultSet(ResultSet rs, int i, Object object, String instanceEntityName, EntityKey key, String rowIdAlias, LockMode lockMode, Loadable rootPersister, SessionImplementor session)
/*  945:     */     throws SQLException, HibernateException
/*  946:     */   {
/*  947:1525 */     Serializable id = key.getIdentifier();
/*  948:     */     
/*  949:     */ 
/*  950:1528 */     Loadable persister = (Loadable)getFactory().getEntityPersister(instanceEntityName);
/*  951:1530 */     if (LOG.isTraceEnabled()) {
/*  952:1531 */       LOG.tracev("Initializing object from ResultSet: {0}", MessageHelper.infoString(persister, id, getFactory()));
/*  953:     */     }
/*  954:1533 */     boolean eagerPropertyFetch = isEagerPropertyFetchEnabled(i);
/*  955:     */     
/*  956:     */ 
/*  957:     */ 
/*  958:     */ 
/*  959:1538 */     TwoPhaseLoad.addUninitializedEntity(key, object, persister, lockMode, !eagerPropertyFetch, session);
/*  960:     */     
/*  961:     */ 
/*  962:     */ 
/*  963:     */ 
/*  964:     */ 
/*  965:     */ 
/*  966:     */ 
/*  967:     */ 
/*  968:     */ 
/*  969:1548 */     String[][] cols = persister == rootPersister ? getEntityAliases()[i].getSuffixedPropertyAliases() : getEntityAliases()[i].getSuffixedPropertyAliases(persister);
/*  970:     */     
/*  971:     */ 
/*  972:     */ 
/*  973:1552 */     Object[] values = persister.hydrate(rs, id, object, rootPersister, cols, eagerPropertyFetch, session);
/*  974:     */     
/*  975:     */ 
/*  976:     */ 
/*  977:     */ 
/*  978:     */ 
/*  979:     */ 
/*  980:     */ 
/*  981:     */ 
/*  982:     */ 
/*  983:1562 */     Object rowId = persister.hasRowId() ? rs.getObject(rowIdAlias) : null;
/*  984:     */     
/*  985:1564 */     AssociationType[] ownerAssociationTypes = getOwnerAssociationTypes();
/*  986:1565 */     if ((ownerAssociationTypes != null) && (ownerAssociationTypes[i] != null))
/*  987:     */     {
/*  988:1566 */       String ukName = ownerAssociationTypes[i].getRHSUniqueKeyPropertyName();
/*  989:1567 */       if (ukName != null)
/*  990:     */       {
/*  991:1568 */         int index = ((UniqueKeyLoadable)persister).getPropertyIndex(ukName);
/*  992:1569 */         Type type = persister.getPropertyTypes()[index];
/*  993:     */         
/*  994:     */ 
/*  995:     */ 
/*  996:     */ 
/*  997:     */ 
/*  998:     */ 
/*  999:1576 */         EntityUniqueKey euk = new EntityUniqueKey(rootPersister.getEntityName(), ukName, type.semiResolve(values[index], session, object), type, persister.getEntityMode(), session.getFactory());
/* 1000:     */         
/* 1001:     */ 
/* 1002:     */ 
/* 1003:     */ 
/* 1004:     */ 
/* 1005:     */ 
/* 1006:     */ 
/* 1007:1584 */         session.getPersistenceContext().addEntity(euk, object);
/* 1008:     */       }
/* 1009:     */     }
/* 1010:1588 */     TwoPhaseLoad.postHydrate(persister, id, values, rowId, object, lockMode, !eagerPropertyFetch, session);
/* 1011:     */   }
/* 1012:     */   
/* 1013:     */   private String getInstanceClass(ResultSet rs, int i, Loadable persister, Serializable id, SessionImplementor session)
/* 1014:     */     throws HibernateException, SQLException
/* 1015:     */   {
/* 1016:1612 */     if (persister.hasSubclasses())
/* 1017:     */     {
/* 1018:1615 */       Object discriminatorValue = persister.getDiscriminatorType().nullSafeGet(rs, getEntityAliases()[i].getSuffixedDiscriminatorAlias(), session, null);
/* 1019:     */       
/* 1020:     */ 
/* 1021:     */ 
/* 1022:     */ 
/* 1023:     */ 
/* 1024:     */ 
/* 1025:1622 */       String result = persister.getSubclassForDiscriminatorValue(discriminatorValue);
/* 1026:1624 */       if (result == null) {
/* 1027:1626 */         throw new WrongClassException("Discriminator: " + discriminatorValue, id, persister.getEntityName());
/* 1028:     */       }
/* 1029:1633 */       return result;
/* 1030:     */     }
/* 1031:1637 */     return persister.getEntityName();
/* 1032:     */   }
/* 1033:     */   
/* 1034:     */   private void advance(ResultSet rs, RowSelection selection)
/* 1035:     */     throws SQLException
/* 1036:     */   {
/* 1037:1647 */     int firstRow = getFirstRow(selection);
/* 1038:1648 */     if (firstRow != 0) {
/* 1039:1649 */       if (getFactory().getSettings().isScrollableResultSetsEnabled()) {
/* 1040:1651 */         rs.absolute(firstRow);
/* 1041:     */       } else {
/* 1042:1655 */         for (int m = 0; m < firstRow; m++) {
/* 1043:1655 */           rs.next();
/* 1044:     */         }
/* 1045:     */       }
/* 1046:     */     }
/* 1047:     */   }
/* 1048:     */   
/* 1049:     */   private static boolean hasMaxRows(RowSelection selection)
/* 1050:     */   {
/* 1051:1661 */     return (selection != null) && (selection.getMaxRows() != null) && (selection.getMaxRows().intValue() > 0);
/* 1052:     */   }
/* 1053:     */   
/* 1054:     */   private static int getFirstRow(RowSelection selection)
/* 1055:     */   {
/* 1056:1665 */     if ((selection == null) || (selection.getFirstRow() == null)) {
/* 1057:1666 */       return 0;
/* 1058:     */     }
/* 1059:1669 */     return selection.getFirstRow().intValue();
/* 1060:     */   }
/* 1061:     */   
/* 1062:     */   private int interpretFirstRow(int zeroBasedFirstResult)
/* 1063:     */   {
/* 1064:1674 */     return getFactory().getDialect().convertToFirstRowValue(zeroBasedFirstResult);
/* 1065:     */   }
/* 1066:     */   
/* 1067:     */   private static boolean useLimit(RowSelection selection, Dialect dialect)
/* 1068:     */   {
/* 1069:1682 */     return (dialect.supportsLimit()) && (hasMaxRows(selection));
/* 1070:     */   }
/* 1071:     */   
/* 1072:     */   private ScrollMode getScrollMode(boolean scroll, boolean hasFirstRow, boolean useLimitOffSet, QueryParameters queryParameters)
/* 1073:     */   {
/* 1074:1686 */     boolean canScroll = getFactory().getSettings().isScrollableResultSetsEnabled();
/* 1075:1687 */     if (canScroll)
/* 1076:     */     {
/* 1077:1688 */       if (scroll) {
/* 1078:1689 */         return queryParameters.getScrollMode();
/* 1079:     */       }
/* 1080:1691 */       if ((hasFirstRow) && (!useLimitOffSet)) {
/* 1081:1692 */         return ScrollMode.SCROLL_INSENSITIVE;
/* 1082:     */       }
/* 1083:     */     }
/* 1084:1695 */     return null;
/* 1085:     */   }
/* 1086:     */   
/* 1087:     */   protected final PreparedStatement prepareQueryStatement(QueryParameters queryParameters, boolean scroll, SessionImplementor session)
/* 1088:     */     throws SQLException, HibernateException
/* 1089:     */   {
/* 1090:1707 */     queryParameters.processFilters(getSQLString(), session);
/* 1091:1708 */     String sql = queryParameters.getFilteredSQL();
/* 1092:1709 */     Dialect dialect = getFactory().getDialect();
/* 1093:1710 */     RowSelection selection = queryParameters.getRowSelection();
/* 1094:1711 */     boolean useLimit = useLimit(selection, dialect);
/* 1095:1712 */     boolean hasFirstRow = getFirstRow(selection) > 0;
/* 1096:1713 */     boolean useLimitOffset = (hasFirstRow) && (useLimit) && (dialect.supportsLimitOffset());
/* 1097:1714 */     boolean callable = queryParameters.isCallable();
/* 1098:     */     
/* 1099:1716 */     boolean canScroll = getFactory().getSettings().isScrollableResultSetsEnabled();
/* 1100:1717 */     boolean useScrollableResultSetToSkip = (hasFirstRow) && (!useLimitOffset) && (canScroll);
/* 1101:     */     
/* 1102:1719 */     ScrollMode scrollMode = getScrollMode(scroll, hasFirstRow, useLimit, queryParameters);
/* 1103:1726 */     if (useLimit) {
/* 1104:1727 */       sql = dialect.getLimitString(sql.trim(), useLimitOffset ? getFirstRow(selection) : 0, getMaxOrLimit(selection, dialect));
/* 1105:     */     }
/* 1106:1734 */     sql = preprocessSQL(sql, queryParameters, dialect);
/* 1107:     */     
/* 1108:1736 */     PreparedStatement st = null;
/* 1109:     */     
/* 1110:     */ 
/* 1111:1739 */     st = session.getTransactionCoordinator().getJdbcCoordinator().getStatementPreparer().prepareQueryStatement(sql, callable, scrollMode);
/* 1112:     */     try
/* 1113:     */     {
/* 1114:1747 */       int col = 1;
/* 1115:1749 */       if ((useLimit) && (dialect.bindLimitParametersFirst())) {
/* 1116:1750 */         col += bindLimitParameters(st, col, selection);
/* 1117:     */       }
/* 1118:1752 */       if (callable) {
/* 1119:1753 */         col = dialect.registerResultSetOutParameter((CallableStatement)st, col);
/* 1120:     */       }
/* 1121:1756 */       col += bindParameterValues(st, queryParameters, col, session);
/* 1122:1758 */       if ((useLimit) && (!dialect.bindLimitParametersFirst())) {
/* 1123:1759 */         col += bindLimitParameters(st, col, selection);
/* 1124:     */       }
/* 1125:1762 */       if (!useLimit) {
/* 1126:1763 */         setMaxRows(st, selection);
/* 1127:     */       }
/* 1128:1766 */       if (selection != null)
/* 1129:     */       {
/* 1130:1767 */         if (selection.getTimeout() != null) {
/* 1131:1768 */           st.setQueryTimeout(selection.getTimeout().intValue());
/* 1132:     */         }
/* 1133:1770 */         if (selection.getFetchSize() != null) {
/* 1134:1771 */           st.setFetchSize(selection.getFetchSize().intValue());
/* 1135:     */         }
/* 1136:     */       }
/* 1137:1776 */       LockOptions lockOptions = queryParameters.getLockOptions();
/* 1138:1777 */       if ((lockOptions != null) && 
/* 1139:1778 */         (lockOptions.getTimeOut() != -1)) {
/* 1140:1779 */         if (!dialect.supportsLockTimeouts()) {
/* 1141:1779 */           LOG.debugf("Lock timeout [%s] requested but dialect reported to not support lock timeouts", Integer.valueOf(lockOptions.getTimeOut()));
/* 1142:1781 */         } else if (dialect.isLockTimeoutParameterized()) {
/* 1143:1781 */           st.setInt(col++, lockOptions.getTimeOut());
/* 1144:     */         }
/* 1145:     */       }
/* 1146:1785 */       LOG.tracev("Bound [{0}] parameters total", Integer.valueOf(col));
/* 1147:     */     }
/* 1148:     */     catch (SQLException sqle)
/* 1149:     */     {
/* 1150:1788 */       st.close();
/* 1151:1789 */       throw sqle;
/* 1152:     */     }
/* 1153:     */     catch (HibernateException he)
/* 1154:     */     {
/* 1155:1792 */       st.close();
/* 1156:1793 */       throw he;
/* 1157:     */     }
/* 1158:1796 */     return st;
/* 1159:     */   }
/* 1160:     */   
/* 1161:     */   private static int getMaxOrLimit(RowSelection selection, Dialect dialect)
/* 1162:     */   {
/* 1163:1809 */     int firstRow = dialect.convertToFirstRowValue(getFirstRow(selection));
/* 1164:1810 */     int lastRow = selection.getMaxRows().intValue();
/* 1165:1811 */     if (dialect.useMaxForLimit()) {
/* 1166:1812 */       return lastRow + firstRow;
/* 1167:     */     }
/* 1168:1815 */     return lastRow;
/* 1169:     */   }
/* 1170:     */   
/* 1171:     */   private int bindLimitParameters(PreparedStatement statement, int index, RowSelection selection)
/* 1172:     */     throws SQLException
/* 1173:     */   {
/* 1174:1832 */     Dialect dialect = getFactory().getDialect();
/* 1175:1833 */     if (!dialect.supportsVariableLimit()) {
/* 1176:1834 */       return 0;
/* 1177:     */     }
/* 1178:1836 */     if (!hasMaxRows(selection)) {
/* 1179:1837 */       throw new AssertionFailure("no max results set");
/* 1180:     */     }
/* 1181:1839 */     int firstRow = interpretFirstRow(getFirstRow(selection));
/* 1182:1840 */     int lastRow = getMaxOrLimit(selection, dialect);
/* 1183:1841 */     boolean hasFirstRow = (dialect.supportsLimitOffset()) && ((firstRow > 0) || (dialect.forceLimitUsage()));
/* 1184:1842 */     boolean reverse = dialect.bindLimitParametersInReverseOrder();
/* 1185:1843 */     if (hasFirstRow) {
/* 1186:1844 */       statement.setInt(index + (reverse ? 1 : 0), firstRow);
/* 1187:     */     }
/* 1188:1846 */     statement.setInt(index + ((reverse) || (!hasFirstRow) ? 0 : 1), lastRow);
/* 1189:1847 */     return hasFirstRow ? 2 : 1;
/* 1190:     */   }
/* 1191:     */   
/* 1192:     */   private void setMaxRows(PreparedStatement st, RowSelection selection)
/* 1193:     */     throws SQLException
/* 1194:     */   {
/* 1195:1856 */     if (hasMaxRows(selection)) {
/* 1196:1857 */       st.setMaxRows(selection.getMaxRows().intValue() + interpretFirstRow(getFirstRow(selection)));
/* 1197:     */     }
/* 1198:     */   }
/* 1199:     */   
/* 1200:     */   protected int bindParameterValues(PreparedStatement statement, QueryParameters queryParameters, int startIndex, SessionImplementor session)
/* 1201:     */     throws SQLException
/* 1202:     */   {
/* 1203:1877 */     int span = 0;
/* 1204:1878 */     span += bindPositionalParameters(statement, queryParameters, startIndex, session);
/* 1205:1879 */     span += bindNamedParameters(statement, queryParameters.getNamedParameters(), startIndex + span, session);
/* 1206:1880 */     return span;
/* 1207:     */   }
/* 1208:     */   
/* 1209:     */   protected int bindPositionalParameters(PreparedStatement statement, QueryParameters queryParameters, int startIndex, SessionImplementor session)
/* 1210:     */     throws SQLException, HibernateException
/* 1211:     */   {
/* 1212:1903 */     Object[] values = queryParameters.getFilteredPositionalParameterValues();
/* 1213:1904 */     Type[] types = queryParameters.getFilteredPositionalParameterTypes();
/* 1214:1905 */     int span = 0;
/* 1215:1906 */     for (int i = 0; i < values.length; i++)
/* 1216:     */     {
/* 1217:1907 */       types[i].nullSafeSet(statement, values[i], startIndex + span, session);
/* 1218:1908 */       span += types[i].getColumnSpan(getFactory());
/* 1219:     */     }
/* 1220:1910 */     return span;
/* 1221:     */   }
/* 1222:     */   
/* 1223:     */   protected int bindNamedParameters(PreparedStatement statement, Map namedParams, int startIndex, SessionImplementor session)
/* 1224:     */     throws SQLException, HibernateException
/* 1225:     */   {
/* 1226:1936 */     if (namedParams != null)
/* 1227:     */     {
/* 1228:1938 */       Iterator iter = namedParams.entrySet().iterator();
/* 1229:1939 */       boolean debugEnabled = LOG.isDebugEnabled();
/* 1230:1940 */       int result = 0;
/* 1231:1941 */       while (iter.hasNext())
/* 1232:     */       {
/* 1233:1942 */         Map.Entry e = (Map.Entry)iter.next();
/* 1234:1943 */         String name = (String)e.getKey();
/* 1235:1944 */         TypedValue typedval = (TypedValue)e.getValue();
/* 1236:1945 */         int[] locs = getNamedParameterLocs(name);
/* 1237:1946 */         for (int i = 0; i < locs.length; i++)
/* 1238:     */         {
/* 1239:1947 */           if (debugEnabled) {
/* 1240:1947 */             LOG.debugf("bindNamedParameters() %s -> %s [%s]", typedval.getValue(), name, Integer.valueOf(locs[i] + startIndex));
/* 1241:     */           }
/* 1242:1948 */           typedval.getType().nullSafeSet(statement, typedval.getValue(), locs[i] + startIndex, session);
/* 1243:     */         }
/* 1244:1950 */         result += locs.length;
/* 1245:     */       }
/* 1246:1952 */       return result;
/* 1247:     */     }
/* 1248:1955 */     return 0;
/* 1249:     */   }
/* 1250:     */   
/* 1251:     */   public int[] getNamedParameterLocs(String name)
/* 1252:     */   {
/* 1253:1960 */     throw new AssertionFailure("no named parameters");
/* 1254:     */   }
/* 1255:     */   
/* 1256:     */   protected final ResultSet getResultSet(PreparedStatement st, boolean autodiscovertypes, boolean callable, RowSelection selection, SessionImplementor session)
/* 1257:     */     throws SQLException, HibernateException
/* 1258:     */   {
/* 1259:1975 */     ResultSet rs = null;
/* 1260:     */     try
/* 1261:     */     {
/* 1262:1977 */       Dialect dialect = getFactory().getDialect();
/* 1263:1978 */       rs = st.executeQuery();
/* 1264:1979 */       rs = wrapResultSetIfEnabled(rs, session);
/* 1265:1981 */       if ((!dialect.supportsLimitOffset()) || (!useLimit(selection, dialect))) {
/* 1266:1982 */         advance(rs, selection);
/* 1267:     */       }
/* 1268:1985 */       if (autodiscovertypes) {
/* 1269:1986 */         autoDiscoverTypes(rs);
/* 1270:     */       }
/* 1271:1988 */       return rs;
/* 1272:     */     }
/* 1273:     */     catch (SQLException sqle)
/* 1274:     */     {
/* 1275:1991 */       st.close();
/* 1276:1992 */       throw sqle;
/* 1277:     */     }
/* 1278:     */   }
/* 1279:     */   
/* 1280:     */   protected void autoDiscoverTypes(ResultSet rs)
/* 1281:     */   {
/* 1282:1997 */     throw new AssertionFailure("Auto discover types not supported in this loader");
/* 1283:     */   }
/* 1284:     */   
/* 1285:     */   private synchronized ResultSet wrapResultSetIfEnabled(ResultSet rs, SessionImplementor session)
/* 1286:     */   {
/* 1287:2004 */     if (session.getFactory().getSettings().isWrapResultSetsEnabled()) {
/* 1288:     */       try
/* 1289:     */       {
/* 1290:2006 */         LOG.debugf("Wrapping result set [%s]", rs);
/* 1291:2007 */         return session.getFactory().getJdbcServices().getResultSetWrapper().wrap(rs, retreiveColumnNameToIndexCache(rs));
/* 1292:     */       }
/* 1293:     */       catch (SQLException e)
/* 1294:     */       {
/* 1295:2012 */         LOG.unableToWrapResultSet(e);
/* 1296:2013 */         return rs;
/* 1297:     */       }
/* 1298:     */     }
/* 1299:2017 */     return rs;
/* 1300:     */   }
/* 1301:     */   
/* 1302:     */   private ColumnNameCache retreiveColumnNameToIndexCache(ResultSet rs)
/* 1303:     */     throws SQLException
/* 1304:     */   {
/* 1305:2022 */     if (this.columnNameCache == null)
/* 1306:     */     {
/* 1307:2023 */       LOG.trace("Building columnName->columnIndex cache");
/* 1308:2024 */       this.columnNameCache = new ColumnNameCache(rs.getMetaData().getColumnCount());
/* 1309:     */     }
/* 1310:2027 */     return this.columnNameCache;
/* 1311:     */   }
/* 1312:     */   
/* 1313:     */   protected final List loadEntity(SessionImplementor session, Object id, Type identifierType, Object optionalObject, String optionalEntityName, Serializable optionalIdentifier, EntityPersister persister, LockOptions lockOptions)
/* 1314:     */     throws HibernateException
/* 1315:     */   {
/* 1316:2045 */     if (LOG.isDebugEnabled()) {
/* 1317:2046 */       LOG.debugf("Loading entity: %s", MessageHelper.infoString(persister, id, identifierType, getFactory()));
/* 1318:     */     }
/* 1319:     */     List result;
/* 1320:     */     try
/* 1321:     */     {
/* 1322:2051 */       QueryParameters qp = new QueryParameters();
/* 1323:2052 */       qp.setPositionalParameterTypes(new Type[] { identifierType });
/* 1324:2053 */       qp.setPositionalParameterValues(new Object[] { id });
/* 1325:2054 */       qp.setOptionalObject(optionalObject);
/* 1326:2055 */       qp.setOptionalEntityName(optionalEntityName);
/* 1327:2056 */       qp.setOptionalId(optionalIdentifier);
/* 1328:2057 */       qp.setLockOptions(lockOptions);
/* 1329:2058 */       result = doQueryAndInitializeNonLazyCollections(session, qp, false);
/* 1330:     */     }
/* 1331:     */     catch (SQLException sqle)
/* 1332:     */     {
/* 1333:2061 */       Loadable[] persisters = getEntityPersisters();
/* 1334:2062 */       throw this.factory.getSQLExceptionHelper().convert(sqle, "could not load an entity: " + MessageHelper.infoString(persisters[(persisters.length - 1)], id, identifierType, getFactory()), getSQLString());
/* 1335:     */     }
/* 1336:2070 */     LOG.debug("Done entity load");
/* 1337:     */     
/* 1338:2072 */     return result;
/* 1339:     */   }
/* 1340:     */   
/* 1341:     */   protected final List loadEntity(SessionImplementor session, Object key, Object index, Type keyType, Type indexType, EntityPersister persister)
/* 1342:     */     throws HibernateException
/* 1343:     */   {
/* 1344:2088 */     LOG.debug("Loading collection element by index");
/* 1345:     */     List result;
/* 1346:     */     try
/* 1347:     */     {
/* 1348:2092 */       result = doQueryAndInitializeNonLazyCollections(session, new QueryParameters(new Type[] { keyType, indexType }, new Object[] { key, index }), false);
/* 1349:     */     }
/* 1350:     */     catch (SQLException sqle)
/* 1351:     */     {
/* 1352:2102 */       throw this.factory.getSQLExceptionHelper().convert(sqle, "could not collection element by index", getSQLString());
/* 1353:     */     }
/* 1354:2109 */     LOG.debug("Done entity load");
/* 1355:     */     
/* 1356:2111 */     return result;
/* 1357:     */   }
/* 1358:     */   
/* 1359:     */   public final List loadEntityBatch(SessionImplementor session, Serializable[] ids, Type idType, Object optionalObject, String optionalEntityName, Serializable optionalId, EntityPersister persister, LockOptions lockOptions)
/* 1360:     */     throws HibernateException
/* 1361:     */   {
/* 1362:2130 */     if (LOG.isDebugEnabled()) {
/* 1363:2131 */       LOG.debugf("Batch loading entity: %s", MessageHelper.infoString(persister, ids, getFactory()));
/* 1364:     */     }
/* 1365:2133 */     Type[] types = new Type[ids.length];
/* 1366:2134 */     Arrays.fill(types, idType);
/* 1367:     */     List result;
/* 1368:     */     try
/* 1369:     */     {
/* 1370:2137 */       QueryParameters qp = new QueryParameters();
/* 1371:2138 */       qp.setPositionalParameterTypes(types);
/* 1372:2139 */       qp.setPositionalParameterValues(ids);
/* 1373:2140 */       qp.setOptionalObject(optionalObject);
/* 1374:2141 */       qp.setOptionalEntityName(optionalEntityName);
/* 1375:2142 */       qp.setOptionalId(optionalId);
/* 1376:2143 */       qp.setLockOptions(lockOptions);
/* 1377:2144 */       result = doQueryAndInitializeNonLazyCollections(session, qp, false);
/* 1378:     */     }
/* 1379:     */     catch (SQLException sqle)
/* 1380:     */     {
/* 1381:2147 */       throw this.factory.getSQLExceptionHelper().convert(sqle, "could not load an entity batch: " + MessageHelper.infoString(getEntityPersisters()[0], ids, getFactory()), getSQLString());
/* 1382:     */     }
/* 1383:2155 */     LOG.debug("Done entity batch load");
/* 1384:     */     
/* 1385:2157 */     return result;
/* 1386:     */   }
/* 1387:     */   
/* 1388:     */   public final void loadCollection(SessionImplementor session, Serializable id, Type type)
/* 1389:     */     throws HibernateException
/* 1390:     */   {
/* 1391:2169 */     if (LOG.isDebugEnabled()) {
/* 1392:2170 */       LOG.debugf("Loading collection: %s", MessageHelper.collectionInfoString(getCollectionPersisters()[0], id, getFactory()));
/* 1393:     */     }
/* 1394:2173 */     Serializable[] ids = { id };
/* 1395:     */     try
/* 1396:     */     {
/* 1397:2175 */       doQueryAndInitializeNonLazyCollections(session, new QueryParameters(new Type[] { type }, ids, ids), true);
/* 1398:     */     }
/* 1399:     */     catch (SQLException sqle)
/* 1400:     */     {
/* 1401:2182 */       throw this.factory.getSQLExceptionHelper().convert(sqle, "could not initialize a collection: " + MessageHelper.collectionInfoString(getCollectionPersisters()[0], id, getFactory()), getSQLString());
/* 1402:     */     }
/* 1403:2190 */     LOG.debug("Done loading collection");
/* 1404:     */   }
/* 1405:     */   
/* 1406:     */   public final void loadCollectionBatch(SessionImplementor session, Serializable[] ids, Type type)
/* 1407:     */     throws HibernateException
/* 1408:     */   {
/* 1409:2202 */     if (LOG.isDebugEnabled()) {
/* 1410:2203 */       LOG.debugf("Batch loading collection: %s", MessageHelper.collectionInfoString(getCollectionPersisters()[0], ids, getFactory()));
/* 1411:     */     }
/* 1412:2206 */     Type[] idTypes = new Type[ids.length];
/* 1413:2207 */     Arrays.fill(idTypes, type);
/* 1414:     */     try
/* 1415:     */     {
/* 1416:2209 */       doQueryAndInitializeNonLazyCollections(session, new QueryParameters(idTypes, ids, ids), true);
/* 1417:     */     }
/* 1418:     */     catch (SQLException sqle)
/* 1419:     */     {
/* 1420:2216 */       throw this.factory.getSQLExceptionHelper().convert(sqle, "could not initialize a collection batch: " + MessageHelper.collectionInfoString(getCollectionPersisters()[0], ids, getFactory()), getSQLString());
/* 1421:     */     }
/* 1422:2224 */     LOG.debug("Done batch load");
/* 1423:     */   }
/* 1424:     */   
/* 1425:     */   protected final void loadCollectionSubselect(SessionImplementor session, Serializable[] ids, Object[] parameterValues, Type[] parameterTypes, Map namedParameters, Type type)
/* 1426:     */     throws HibernateException
/* 1427:     */   {
/* 1428:2239 */     Type[] idTypes = new Type[ids.length];
/* 1429:2240 */     Arrays.fill(idTypes, type);
/* 1430:     */     try
/* 1431:     */     {
/* 1432:2242 */       doQueryAndInitializeNonLazyCollections(session, new QueryParameters(parameterTypes, parameterValues, namedParameters, ids), true);
/* 1433:     */     }
/* 1434:     */     catch (SQLException sqle)
/* 1435:     */     {
/* 1436:2248 */       throw this.factory.getSQLExceptionHelper().convert(sqle, "could not load collection by subselect: " + MessageHelper.collectionInfoString(getCollectionPersisters()[0], ids, getFactory()), getSQLString());
/* 1437:     */     }
/* 1438:     */   }
/* 1439:     */   
/* 1440:     */   protected List list(SessionImplementor session, QueryParameters queryParameters, Set querySpaces, Type[] resultTypes)
/* 1441:     */     throws HibernateException
/* 1442:     */   {
/* 1443:2267 */     boolean cacheable = (this.factory.getSettings().isQueryCacheEnabled()) && (queryParameters.isCacheable());
/* 1444:2270 */     if (cacheable) {
/* 1445:2271 */       return listUsingQueryCache(session, queryParameters, querySpaces, resultTypes);
/* 1446:     */     }
/* 1447:2274 */     return listIgnoreQueryCache(session, queryParameters);
/* 1448:     */   }
/* 1449:     */   
/* 1450:     */   private List listIgnoreQueryCache(SessionImplementor session, QueryParameters queryParameters)
/* 1451:     */   {
/* 1452:2279 */     return getResultList(doList(session, queryParameters), queryParameters.getResultTransformer());
/* 1453:     */   }
/* 1454:     */   
/* 1455:     */   private List listUsingQueryCache(SessionImplementor session, QueryParameters queryParameters, Set querySpaces, Type[] resultTypes)
/* 1456:     */   {
/* 1457:2288 */     QueryCache queryCache = this.factory.getQueryCache(queryParameters.getCacheRegion());
/* 1458:     */     
/* 1459:2290 */     QueryKey key = generateQueryKey(session, queryParameters);
/* 1460:2292 */     if ((querySpaces == null) || (querySpaces.size() == 0)) {
/* 1461:2293 */       LOG.tracev("Unexpected querySpaces is {0}", querySpaces == null ? querySpaces : "empty");
/* 1462:     */     } else {
/* 1463:2295 */       LOG.tracev("querySpaces is {0}", querySpaces);
/* 1464:     */     }
/* 1465:2298 */     List result = getResultFromQueryCache(session, queryParameters, querySpaces, resultTypes, queryCache, key);
/* 1466:2307 */     if (result == null)
/* 1467:     */     {
/* 1468:2308 */       result = doList(session, queryParameters, key.getResultTransformer());
/* 1469:     */       
/* 1470:2310 */       putResultInQueryCache(session, queryParameters, resultTypes, queryCache, key, result);
/* 1471:     */     }
/* 1472:2320 */     ResultTransformer resolvedTransformer = resolveResultTransformer(queryParameters.getResultTransformer());
/* 1473:2321 */     if (resolvedTransformer != null) {
/* 1474:2322 */       result = areResultSetRowsTransformedImmediately() ? key.getResultTransformer().retransformResults(result, getResultRowAliases(), queryParameters.getResultTransformer(), includeInResultRow()) : key.getResultTransformer().untransformToTuples(result);
/* 1475:     */     }
/* 1476:2336 */     return getResultList(result, queryParameters.getResultTransformer());
/* 1477:     */   }
/* 1478:     */   
/* 1479:     */   private QueryKey generateQueryKey(SessionImplementor session, QueryParameters queryParameters)
/* 1480:     */   {
/* 1481:2342 */     return QueryKey.generateQueryKey(getSQLString(), queryParameters, FilterKey.createFilterKeys(session.getLoadQueryInfluencers().getEnabledFilters()), session, createCacheableResultTransformer(queryParameters));
/* 1482:     */   }
/* 1483:     */   
/* 1484:     */   private CacheableResultTransformer createCacheableResultTransformer(QueryParameters queryParameters)
/* 1485:     */   {
/* 1486:2352 */     return CacheableResultTransformer.create(queryParameters.getResultTransformer(), getResultRowAliases(), includeInResultRow());
/* 1487:     */   }
/* 1488:     */   
/* 1489:     */   private List getResultFromQueryCache(SessionImplementor session, QueryParameters queryParameters, Set querySpaces, Type[] resultTypes, QueryCache queryCache, QueryKey key)
/* 1490:     */   {
/* 1491:2366 */     List result = null;
/* 1492:2368 */     if (session.getCacheMode().isGetEnabled())
/* 1493:     */     {
/* 1494:2369 */       boolean isImmutableNaturalKeyLookup = (queryParameters.isNaturalKeyLookup()) && (resultTypes.length == 1) && (resultTypes[0].isEntityType()) && (getEntityPersister((EntityType)EntityType.class.cast(resultTypes[0])).getEntityMetamodel().hasImmutableNaturalId());
/* 1495:     */       
/* 1496:     */ 
/* 1497:     */ 
/* 1498:     */ 
/* 1499:     */ 
/* 1500:     */ 
/* 1501:     */ 
/* 1502:2377 */       PersistenceContext persistenceContext = session.getPersistenceContext();
/* 1503:2378 */       boolean defaultReadOnlyOrig = persistenceContext.isDefaultReadOnly();
/* 1504:2379 */       if (queryParameters.isReadOnlyInitialized()) {
/* 1505:2382 */         persistenceContext.setDefaultReadOnly(queryParameters.isReadOnly());
/* 1506:     */       } else {
/* 1507:2387 */         queryParameters.setReadOnly(persistenceContext.isDefaultReadOnly());
/* 1508:     */       }
/* 1509:     */       try
/* 1510:     */       {
/* 1511:2390 */         result = queryCache.get(key, key.getResultTransformer().getCachedResultTypes(resultTypes), isImmutableNaturalKeyLookup, querySpaces, session);
/* 1512:     */       }
/* 1513:     */       finally
/* 1514:     */       {
/* 1515:2399 */         persistenceContext.setDefaultReadOnly(defaultReadOnlyOrig);
/* 1516:     */       }
/* 1517:2402 */       if (this.factory.getStatistics().isStatisticsEnabled()) {
/* 1518:2403 */         if (result == null) {
/* 1519:2404 */           this.factory.getStatisticsImplementor().queryCacheMiss(getQueryIdentifier(), queryCache.getRegion().getName());
/* 1520:     */         } else {
/* 1521:2408 */           this.factory.getStatisticsImplementor().queryCacheHit(getQueryIdentifier(), queryCache.getRegion().getName());
/* 1522:     */         }
/* 1523:     */       }
/* 1524:     */     }
/* 1525:2414 */     return result;
/* 1526:     */   }
/* 1527:     */   
/* 1528:     */   private EntityPersister getEntityPersister(EntityType entityType)
/* 1529:     */   {
/* 1530:2418 */     return this.factory.getEntityPersister(entityType.getAssociatedEntityName());
/* 1531:     */   }
/* 1532:     */   
/* 1533:     */   private void putResultInQueryCache(SessionImplementor session, QueryParameters queryParameters, Type[] resultTypes, QueryCache queryCache, QueryKey key, List result)
/* 1534:     */   {
/* 1535:2428 */     if (session.getCacheMode().isPutEnabled())
/* 1536:     */     {
/* 1537:2429 */       boolean put = queryCache.put(key, key.getResultTransformer().getCachedResultTypes(resultTypes), result, queryParameters.isNaturalKeyLookup(), session);
/* 1538:2436 */       if ((put) && (this.factory.getStatistics().isStatisticsEnabled())) {
/* 1539:2437 */         this.factory.getStatisticsImplementor().queryCachePut(getQueryIdentifier(), queryCache.getRegion().getName());
/* 1540:     */       }
/* 1541:     */     }
/* 1542:     */   }
/* 1543:     */   
/* 1544:     */   protected List doList(SessionImplementor session, QueryParameters queryParameters)
/* 1545:     */     throws HibernateException
/* 1546:     */   {
/* 1547:2449 */     return doList(session, queryParameters, null);
/* 1548:     */   }
/* 1549:     */   
/* 1550:     */   private List doList(SessionImplementor session, QueryParameters queryParameters, ResultTransformer forcedResultTransformer)
/* 1551:     */     throws HibernateException
/* 1552:     */   {
/* 1553:2457 */     boolean stats = getFactory().getStatistics().isStatisticsEnabled();
/* 1554:2458 */     long startTime = 0L;
/* 1555:2459 */     if (stats) {
/* 1556:2459 */       startTime = System.currentTimeMillis();
/* 1557:     */     }
/* 1558:     */     List result;
/* 1559:     */     try
/* 1560:     */     {
/* 1561:2463 */       result = doQueryAndInitializeNonLazyCollections(session, queryParameters, true, forcedResultTransformer);
/* 1562:     */     }
/* 1563:     */     catch (SQLException sqle)
/* 1564:     */     {
/* 1565:2466 */       throw this.factory.getSQLExceptionHelper().convert(sqle, "could not execute query", getSQLString());
/* 1566:     */     }
/* 1567:2473 */     if (stats) {
/* 1568:2474 */       getFactory().getStatisticsImplementor().queryExecuted(getQueryIdentifier(), result.size(), System.currentTimeMillis() - startTime);
/* 1569:     */     }
/* 1570:2481 */     return result;
/* 1571:     */   }
/* 1572:     */   
/* 1573:     */   protected void checkScrollability()
/* 1574:     */     throws HibernateException
/* 1575:     */   {}
/* 1576:     */   
/* 1577:     */   protected boolean needsFetchingScroll()
/* 1578:     */   {
/* 1579:2504 */     return false;
/* 1580:     */   }
/* 1581:     */   
/* 1582:     */   protected ScrollableResults scroll(QueryParameters queryParameters, Type[] returnTypes, HolderInstantiator holderInstantiator, SessionImplementor session)
/* 1583:     */     throws HibernateException
/* 1584:     */   {
/* 1585:2525 */     checkScrollability();
/* 1586:     */     
/* 1587:2527 */     boolean stats = (getQueryIdentifier() != null) && (getFactory().getStatistics().isStatisticsEnabled());
/* 1588:     */     
/* 1589:2529 */     long startTime = 0L;
/* 1590:2530 */     if (stats) {
/* 1591:2530 */       startTime = System.currentTimeMillis();
/* 1592:     */     }
/* 1593:     */     try
/* 1594:     */     {
/* 1595:2534 */       PreparedStatement st = prepareQueryStatement(queryParameters, true, session);
/* 1596:2535 */       ResultSet rs = getResultSet(st, queryParameters.hasAutoDiscoverScalarTypes(), queryParameters.isCallable(), queryParameters.getRowSelection(), session);
/* 1597:2537 */       if (stats) {
/* 1598:2538 */         getFactory().getStatisticsImplementor().queryExecuted(getQueryIdentifier(), 0, System.currentTimeMillis() - startTime);
/* 1599:     */       }
/* 1600:2545 */       if (needsFetchingScroll()) {
/* 1601:2546 */         return new FetchingScrollableResultsImpl(rs, st, session, this, queryParameters, returnTypes, holderInstantiator);
/* 1602:     */       }
/* 1603:2557 */       return new ScrollableResultsImpl(rs, st, session, this, queryParameters, returnTypes, holderInstantiator);
/* 1604:     */     }
/* 1605:     */     catch (SQLException sqle)
/* 1606:     */     {
/* 1607:2570 */       throw this.factory.getSQLExceptionHelper().convert(sqle, "could not execute query using scroll", getSQLString());
/* 1608:     */     }
/* 1609:     */   }
/* 1610:     */   
/* 1611:     */   protected void postInstantiate() {}
/* 1612:     */   
/* 1613:     */   protected abstract EntityAliases[] getEntityAliases();
/* 1614:     */   
/* 1615:     */   protected abstract CollectionAliases[] getCollectionAliases();
/* 1616:     */   
/* 1617:     */   protected String getQueryIdentifier()
/* 1618:     */   {
/* 1619:2597 */     return null;
/* 1620:     */   }
/* 1621:     */   
/* 1622:     */   public final SessionFactoryImplementor getFactory()
/* 1623:     */   {
/* 1624:2601 */     return this.factory;
/* 1625:     */   }
/* 1626:     */   
/* 1627:     */   public String toString()
/* 1628:     */   {
/* 1629:2606 */     return getClass().getName() + '(' + getSQLString() + ')';
/* 1630:     */   }
/* 1631:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.Loader
 * JD-Core Version:    0.7.0.1
 */