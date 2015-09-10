/*    1:     */ package org.hibernate.internal;
/*    2:     */ 
/*    3:     */ import java.io.ByteArrayInputStream;
/*    4:     */ import java.io.ByteArrayOutputStream;
/*    5:     */ import java.io.IOException;
/*    6:     */ import java.io.InputStream;
/*    7:     */ import java.io.ObjectInputStream;
/*    8:     */ import java.io.ObjectOutputStream;
/*    9:     */ import java.io.Reader;
/*   10:     */ import java.io.Serializable;
/*   11:     */ import java.sql.Blob;
/*   12:     */ import java.sql.Clob;
/*   13:     */ import java.sql.Connection;
/*   14:     */ import java.sql.NClob;
/*   15:     */ import java.sql.SQLException;
/*   16:     */ import java.util.Collections;
/*   17:     */ import java.util.HashSet;
/*   18:     */ import java.util.Iterator;
/*   19:     */ import java.util.List;
/*   20:     */ import java.util.Map;
/*   21:     */ import java.util.Set;
/*   22:     */ import org.hibernate.AssertionFailure;
/*   23:     */ import org.hibernate.CacheMode;
/*   24:     */ import org.hibernate.ConnectionReleaseMode;
/*   25:     */ import org.hibernate.Criteria;
/*   26:     */ import org.hibernate.EmptyInterceptor;
/*   27:     */ import org.hibernate.EntityNameResolver;
/*   28:     */ import org.hibernate.Filter;
/*   29:     */ import org.hibernate.FlushMode;
/*   30:     */ import org.hibernate.HibernateException;
/*   31:     */ import org.hibernate.Interceptor;
/*   32:     */ import org.hibernate.LobHelper;
/*   33:     */ import org.hibernate.LockMode;
/*   34:     */ import org.hibernate.LockOptions;
/*   35:     */ import org.hibernate.MappingException;
/*   36:     */ import org.hibernate.ObjectDeletedException;
/*   37:     */ import org.hibernate.Query;
/*   38:     */ import org.hibernate.QueryException;
/*   39:     */ import org.hibernate.ReplicationMode;
/*   40:     */ import org.hibernate.SQLQuery;
/*   41:     */ import org.hibernate.ScrollMode;
/*   42:     */ import org.hibernate.ScrollableResults;
/*   43:     */ import org.hibernate.Session.LockRequest;
/*   44:     */ import org.hibernate.SessionBuilder;
/*   45:     */ import org.hibernate.SessionException;
/*   46:     */ import org.hibernate.SharedSessionBuilder;
/*   47:     */ import org.hibernate.Transaction;
/*   48:     */ import org.hibernate.TransientObjectException;
/*   49:     */ import org.hibernate.TypeHelper;
/*   50:     */ import org.hibernate.UnknownProfileException;
/*   51:     */ import org.hibernate.UnresolvableObjectException;
/*   52:     */ import org.hibernate.collection.spi.PersistentCollection;
/*   53:     */ import org.hibernate.engine.internal.StatefulPersistenceContext;
/*   54:     */ import org.hibernate.engine.jdbc.LobCreator;
/*   55:     */ import org.hibernate.engine.jdbc.spi.JdbcCoordinator;
/*   56:     */ import org.hibernate.engine.jdbc.spi.JdbcServices;
/*   57:     */ import org.hibernate.engine.jdbc.spi.LogicalConnectionImplementor;
/*   58:     */ import org.hibernate.engine.query.spi.FilterQueryPlan;
/*   59:     */ import org.hibernate.engine.query.spi.HQLQueryPlan;
/*   60:     */ import org.hibernate.engine.query.spi.NativeSQLQueryPlan;
/*   61:     */ import org.hibernate.engine.query.spi.QueryPlanCache;
/*   62:     */ import org.hibernate.engine.query.spi.sql.NativeSQLQuerySpecification;
/*   63:     */ import org.hibernate.engine.spi.ActionQueue;
/*   64:     */ import org.hibernate.engine.spi.CollectionEntry;
/*   65:     */ import org.hibernate.engine.spi.EntityEntry;
/*   66:     */ import org.hibernate.engine.spi.EntityKey;
/*   67:     */ import org.hibernate.engine.spi.LoadQueryInfluencers;
/*   68:     */ import org.hibernate.engine.spi.NonFlushedChanges;
/*   69:     */ import org.hibernate.engine.spi.PersistenceContext;
/*   70:     */ import org.hibernate.engine.spi.QueryParameters;
/*   71:     */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   72:     */ import org.hibernate.engine.spi.Status;
/*   73:     */ import org.hibernate.engine.transaction.internal.TransactionCoordinatorImpl;
/*   74:     */ import org.hibernate.engine.transaction.spi.TransactionCoordinator;
/*   75:     */ import org.hibernate.engine.transaction.spi.TransactionImplementor;
/*   76:     */ import org.hibernate.event.service.spi.EventListenerGroup;
/*   77:     */ import org.hibernate.event.service.spi.EventListenerRegistry;
/*   78:     */ import org.hibernate.event.spi.AutoFlushEvent;
/*   79:     */ import org.hibernate.event.spi.AutoFlushEventListener;
/*   80:     */ import org.hibernate.event.spi.DeleteEvent;
/*   81:     */ import org.hibernate.event.spi.DeleteEventListener;
/*   82:     */ import org.hibernate.event.spi.DirtyCheckEvent;
/*   83:     */ import org.hibernate.event.spi.DirtyCheckEventListener;
/*   84:     */ import org.hibernate.event.spi.EventSource;
/*   85:     */ import org.hibernate.event.spi.EventType;
/*   86:     */ import org.hibernate.event.spi.EvictEvent;
/*   87:     */ import org.hibernate.event.spi.EvictEventListener;
/*   88:     */ import org.hibernate.event.spi.FlushEvent;
/*   89:     */ import org.hibernate.event.spi.FlushEventListener;
/*   90:     */ import org.hibernate.event.spi.InitializeCollectionEvent;
/*   91:     */ import org.hibernate.event.spi.InitializeCollectionEventListener;
/*   92:     */ import org.hibernate.event.spi.LoadEvent;
/*   93:     */ import org.hibernate.event.spi.LoadEventListener;
/*   94:     */ import org.hibernate.event.spi.LoadEventListener.LoadType;
/*   95:     */ import org.hibernate.event.spi.LockEvent;
/*   96:     */ import org.hibernate.event.spi.LockEventListener;
/*   97:     */ import org.hibernate.event.spi.MergeEvent;
/*   98:     */ import org.hibernate.event.spi.MergeEventListener;
/*   99:     */ import org.hibernate.event.spi.PersistEvent;
/*  100:     */ import org.hibernate.event.spi.PersistEventListener;
/*  101:     */ import org.hibernate.event.spi.RefreshEvent;
/*  102:     */ import org.hibernate.event.spi.RefreshEventListener;
/*  103:     */ import org.hibernate.event.spi.ReplicateEvent;
/*  104:     */ import org.hibernate.event.spi.ReplicateEventListener;
/*  105:     */ import org.hibernate.event.spi.SaveOrUpdateEvent;
/*  106:     */ import org.hibernate.event.spi.SaveOrUpdateEventListener;
/*  107:     */ import org.hibernate.internal.util.collections.CollectionHelper;
/*  108:     */ import org.hibernate.jdbc.ReturningWork;
/*  109:     */ import org.hibernate.jdbc.Work;
/*  110:     */ import org.hibernate.jdbc.WorkExecutor;
/*  111:     */ import org.hibernate.jdbc.WorkExecutorVisitable;
/*  112:     */ import org.hibernate.loader.criteria.CriteriaLoader;
/*  113:     */ import org.hibernate.loader.custom.CustomLoader;
/*  114:     */ import org.hibernate.loader.custom.CustomQuery;
/*  115:     */ import org.hibernate.loader.custom.sql.SQLCustomQuery;
/*  116:     */ import org.hibernate.persister.collection.CollectionPersister;
/*  117:     */ import org.hibernate.persister.entity.EntityPersister;
/*  118:     */ import org.hibernate.persister.entity.OuterJoinLoadable;
/*  119:     */ import org.hibernate.pretty.MessageHelper;
/*  120:     */ import org.hibernate.proxy.EntityNotFoundDelegate;
/*  121:     */ import org.hibernate.proxy.HibernateProxy;
/*  122:     */ import org.hibernate.proxy.LazyInitializer;
/*  123:     */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  124:     */ import org.hibernate.stat.SessionStatistics;
/*  125:     */ import org.hibernate.stat.Statistics;
/*  126:     */ import org.hibernate.stat.internal.SessionStatisticsImpl;
/*  127:     */ import org.hibernate.stat.spi.StatisticsImplementor;
/*  128:     */ import org.hibernate.tuple.entity.EntityMetamodel;
/*  129:     */ import org.hibernate.type.SerializationException;
/*  130:     */ import org.hibernate.type.Type;
/*  131:     */ import org.jboss.logging.Logger;
/*  132:     */ 
/*  133:     */ public final class SessionImpl
/*  134:     */   extends AbstractSessionImpl
/*  135:     */   implements EventSource
/*  136:     */ {
/*  137: 166 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, SessionImpl.class.getName());
/*  138:     */   private transient long timestamp;
/*  139:     */   private transient ActionQueue actionQueue;
/*  140:     */   private transient StatefulPersistenceContext persistenceContext;
/*  141:     */   private transient TransactionCoordinatorImpl transactionCoordinator;
/*  142:     */   private transient Interceptor interceptor;
/*  143: 174 */   private transient EntityNameResolver entityNameResolver = new CoordinatingEntityNameResolver(null);
/*  144:     */   private transient ConnectionReleaseMode connectionReleaseMode;
/*  145: 177 */   private transient FlushMode flushMode = FlushMode.AUTO;
/*  146: 178 */   private transient CacheMode cacheMode = CacheMode.NORMAL;
/*  147:     */   private transient boolean autoClear;
/*  148: 181 */   private transient boolean autoJoinTransactions = true;
/*  149:     */   private transient boolean flushBeforeCompletionEnabled;
/*  150:     */   private transient boolean autoCloseSessionEnabled;
/*  151: 185 */   private transient int dontFlushFromFind = 0;
/*  152:     */   private transient LoadQueryInfluencers loadQueryInfluencers;
/*  153:     */   private transient LobHelperImpl lobHelper;
/*  154:     */   
/*  155:     */   SessionImpl(Connection connection, SessionFactoryImpl factory, TransactionCoordinatorImpl transactionCoordinator, boolean autoJoinTransactions, long timestamp, Interceptor interceptor, boolean flushBeforeCompletionEnabled, boolean autoCloseSessionEnabled, ConnectionReleaseMode connectionReleaseMode, String tenantIdentifier)
/*  156:     */   {
/*  157: 216 */     super(factory, tenantIdentifier);
/*  158: 217 */     this.timestamp = timestamp;
/*  159: 218 */     this.interceptor = (interceptor == null ? EmptyInterceptor.INSTANCE : interceptor);
/*  160: 219 */     this.actionQueue = new ActionQueue(this);
/*  161: 220 */     this.persistenceContext = new StatefulPersistenceContext(this);
/*  162: 221 */     this.flushBeforeCompletionEnabled = flushBeforeCompletionEnabled;
/*  163: 222 */     this.autoCloseSessionEnabled = autoCloseSessionEnabled;
/*  164: 223 */     this.connectionReleaseMode = connectionReleaseMode;
/*  165: 224 */     this.autoJoinTransactions = autoJoinTransactions;
/*  166: 226 */     if (transactionCoordinator == null)
/*  167:     */     {
/*  168: 227 */       this.transactionCoordinator = new TransactionCoordinatorImpl(connection, this);
/*  169: 228 */       this.transactionCoordinator.getJdbcCoordinator().getLogicalConnection().addObserver(new ConnectionObserverStatsBridge(factory));
/*  170:     */     }
/*  171:     */     else
/*  172:     */     {
/*  173: 233 */       if (connection != null) {
/*  174: 234 */         throw new SessionException("Cannot simultaneously share transaction context and specify connection");
/*  175:     */       }
/*  176: 236 */       this.transactionCoordinator = transactionCoordinator;
/*  177:     */     }
/*  178: 239 */     this.loadQueryInfluencers = new LoadQueryInfluencers(factory);
/*  179: 241 */     if (factory.getStatistics().isStatisticsEnabled()) {
/*  180: 241 */       factory.getStatisticsImplementor().openSession();
/*  181:     */     }
/*  182: 243 */     LOG.debugf("Opened session at timestamp: %s", Long.valueOf(timestamp));
/*  183:     */   }
/*  184:     */   
/*  185:     */   public SharedSessionBuilder sessionWithOptions()
/*  186:     */   {
/*  187: 248 */     return new SharedSessionBuilderImpl(this, null);
/*  188:     */   }
/*  189:     */   
/*  190:     */   public void clear()
/*  191:     */   {
/*  192: 252 */     errorIfClosed();
/*  193: 253 */     checkTransactionSynchStatus();
/*  194: 254 */     this.persistenceContext.clear();
/*  195: 255 */     this.actionQueue.clear();
/*  196:     */   }
/*  197:     */   
/*  198:     */   public long getTimestamp()
/*  199:     */   {
/*  200: 259 */     checkTransactionSynchStatus();
/*  201: 260 */     return this.timestamp;
/*  202:     */   }
/*  203:     */   
/*  204:     */   public Connection close()
/*  205:     */     throws HibernateException
/*  206:     */   {
/*  207: 264 */     LOG.trace("Closing session");
/*  208: 265 */     if (isClosed()) {
/*  209: 266 */       throw new SessionException("Session was already closed");
/*  210:     */     }
/*  211: 270 */     if (this.factory.getStatistics().isStatisticsEnabled()) {
/*  212: 271 */       this.factory.getStatisticsImplementor().closeSession();
/*  213:     */     }
/*  214:     */     try
/*  215:     */     {
/*  216: 275 */       return this.transactionCoordinator.close();
/*  217:     */     }
/*  218:     */     finally
/*  219:     */     {
/*  220: 278 */       setClosed();
/*  221: 279 */       cleanup();
/*  222:     */     }
/*  223:     */   }
/*  224:     */   
/*  225:     */   public ConnectionReleaseMode getConnectionReleaseMode()
/*  226:     */   {
/*  227: 284 */     return this.connectionReleaseMode;
/*  228:     */   }
/*  229:     */   
/*  230:     */   public boolean shouldAutoJoinTransaction()
/*  231:     */   {
/*  232: 289 */     return this.autoJoinTransactions;
/*  233:     */   }
/*  234:     */   
/*  235:     */   public boolean isAutoCloseSessionEnabled()
/*  236:     */   {
/*  237: 293 */     return this.autoCloseSessionEnabled;
/*  238:     */   }
/*  239:     */   
/*  240:     */   public boolean isOpen()
/*  241:     */   {
/*  242: 297 */     checkTransactionSynchStatus();
/*  243: 298 */     return !isClosed();
/*  244:     */   }
/*  245:     */   
/*  246:     */   public boolean isFlushModeNever()
/*  247:     */   {
/*  248: 302 */     return FlushMode.isManualFlushMode(getFlushMode());
/*  249:     */   }
/*  250:     */   
/*  251:     */   public boolean isFlushBeforeCompletionEnabled()
/*  252:     */   {
/*  253: 306 */     return this.flushBeforeCompletionEnabled;
/*  254:     */   }
/*  255:     */   
/*  256:     */   public void managedFlush()
/*  257:     */   {
/*  258: 310 */     if (isClosed())
/*  259:     */     {
/*  260: 311 */       LOG.trace("Skipping auto-flush due to session closed");
/*  261: 312 */       return;
/*  262:     */     }
/*  263: 314 */     LOG.trace("Automatically flushing session");
/*  264: 315 */     flush();
/*  265:     */   }
/*  266:     */   
/*  267:     */   public NonFlushedChanges getNonFlushedChanges()
/*  268:     */     throws HibernateException
/*  269:     */   {
/*  270: 324 */     errorIfClosed();
/*  271: 325 */     checkTransactionSynchStatus();
/*  272: 326 */     return new NonFlushedChangesImpl(this);
/*  273:     */   }
/*  274:     */   
/*  275:     */   public void applyNonFlushedChanges(NonFlushedChanges nonFlushedChanges)
/*  276:     */     throws HibernateException
/*  277:     */   {
/*  278: 338 */     errorIfClosed();
/*  279: 339 */     checkTransactionSynchStatus();
/*  280:     */     
/*  281: 341 */     replacePersistenceContext(((NonFlushedChangesImpl)nonFlushedChanges).getPersistenceContext());
/*  282: 342 */     replaceActionQueue(((NonFlushedChangesImpl)nonFlushedChanges).getActionQueue());
/*  283:     */   }
/*  284:     */   
/*  285:     */   private void replacePersistenceContext(StatefulPersistenceContext persistenceContextNew)
/*  286:     */   {
/*  287: 346 */     if (persistenceContextNew.getSession() != null) {
/*  288: 347 */       throw new IllegalStateException("new persistence context is already connected to a session ");
/*  289:     */     }
/*  290: 349 */     this.persistenceContext.clear();
/*  291: 350 */     ObjectInputStream ois = null;
/*  292:     */     try
/*  293:     */     {
/*  294: 352 */       ois = new ObjectInputStream(new ByteArrayInputStream(serializePersistenceContext(persistenceContextNew)));
/*  295: 353 */       this.persistenceContext = StatefulPersistenceContext.deserialize(ois, this); return;
/*  296:     */     }
/*  297:     */     catch (IOException ex)
/*  298:     */     {
/*  299: 356 */       throw new SerializationException("could not deserialize the persistence context", ex);
/*  300:     */     }
/*  301:     */     catch (ClassNotFoundException ex)
/*  302:     */     {
/*  303: 359 */       throw new SerializationException("could not deserialize the persistence context", ex);
/*  304:     */     }
/*  305:     */     finally
/*  306:     */     {
/*  307:     */       try
/*  308:     */       {
/*  309: 363 */         if (ois != null) {
/*  310: 363 */           ois.close();
/*  311:     */         }
/*  312:     */       }
/*  313:     */       catch (IOException ex) {}
/*  314:     */     }
/*  315:     */   }
/*  316:     */   
/*  317:     */   private static byte[] serializePersistenceContext(StatefulPersistenceContext pc)
/*  318:     */   {
/*  319: 370 */     baos = new ByteArrayOutputStream(512);
/*  320: 371 */     ObjectOutputStream oos = null;
/*  321:     */     try
/*  322:     */     {
/*  323: 373 */       oos = new ObjectOutputStream(baos);
/*  324: 374 */       pc.serialize(oos);
/*  325:     */       
/*  326:     */ 
/*  327:     */ 
/*  328:     */ 
/*  329:     */ 
/*  330:     */ 
/*  331:     */ 
/*  332:     */ 
/*  333:     */ 
/*  334:     */ 
/*  335:     */ 
/*  336:     */ 
/*  337:     */ 
/*  338:     */ 
/*  339: 389 */       return baos.toByteArray();
/*  340:     */     }
/*  341:     */     catch (IOException ex)
/*  342:     */     {
/*  343: 377 */       throw new SerializationException("could not serialize persistence context", ex);
/*  344:     */     }
/*  345:     */     finally
/*  346:     */     {
/*  347: 380 */       if (oos != null) {
/*  348:     */         try
/*  349:     */         {
/*  350: 382 */           oos.close();
/*  351:     */         }
/*  352:     */         catch (IOException ex) {}
/*  353:     */       }
/*  354:     */     }
/*  355:     */   }
/*  356:     */   
/*  357:     */   private void replaceActionQueue(ActionQueue actionQueueNew)
/*  358:     */   {
/*  359: 393 */     if (this.actionQueue.hasAnyQueuedActions()) {
/*  360: 394 */       throw new IllegalStateException("cannot replace an ActionQueue with queued actions ");
/*  361:     */     }
/*  362: 396 */     this.actionQueue.clear();
/*  363: 397 */     ObjectInputStream ois = null;
/*  364:     */     try
/*  365:     */     {
/*  366: 399 */       ois = new ObjectInputStream(new ByteArrayInputStream(serializeActionQueue(actionQueueNew)));
/*  367: 400 */       this.actionQueue = ActionQueue.deserialize(ois, this); return;
/*  368:     */     }
/*  369:     */     catch (IOException ex)
/*  370:     */     {
/*  371: 403 */       throw new SerializationException("could not deserialize the action queue", ex);
/*  372:     */     }
/*  373:     */     catch (ClassNotFoundException ex)
/*  374:     */     {
/*  375: 406 */       throw new SerializationException("could not deserialize the action queue", ex);
/*  376:     */     }
/*  377:     */     finally
/*  378:     */     {
/*  379:     */       try
/*  380:     */       {
/*  381: 410 */         if (ois != null) {
/*  382: 410 */           ois.close();
/*  383:     */         }
/*  384:     */       }
/*  385:     */       catch (IOException ex) {}
/*  386:     */     }
/*  387:     */   }
/*  388:     */   
/*  389:     */   private static byte[] serializeActionQueue(ActionQueue actionQueue)
/*  390:     */   {
/*  391: 417 */     baos = new ByteArrayOutputStream(512);
/*  392: 418 */     ObjectOutputStream oos = null;
/*  393:     */     try
/*  394:     */     {
/*  395: 420 */       oos = new ObjectOutputStream(baos);
/*  396: 421 */       actionQueue.serialize(oos);
/*  397:     */       
/*  398:     */ 
/*  399:     */ 
/*  400:     */ 
/*  401:     */ 
/*  402:     */ 
/*  403:     */ 
/*  404:     */ 
/*  405:     */ 
/*  406:     */ 
/*  407:     */ 
/*  408:     */ 
/*  409:     */ 
/*  410:     */ 
/*  411: 436 */       return baos.toByteArray();
/*  412:     */     }
/*  413:     */     catch (IOException ex)
/*  414:     */     {
/*  415: 424 */       throw new SerializationException("could not serialize action queue", ex);
/*  416:     */     }
/*  417:     */     finally
/*  418:     */     {
/*  419: 427 */       if (oos != null) {
/*  420:     */         try
/*  421:     */         {
/*  422: 429 */           oos.close();
/*  423:     */         }
/*  424:     */         catch (IOException ex) {}
/*  425:     */       }
/*  426:     */     }
/*  427:     */   }
/*  428:     */   
/*  429:     */   public boolean shouldAutoClose()
/*  430:     */   {
/*  431: 440 */     return (isAutoCloseSessionEnabled()) && (!isClosed());
/*  432:     */   }
/*  433:     */   
/*  434:     */   public void managedClose()
/*  435:     */   {
/*  436: 444 */     LOG.trace("Automatically closing session");
/*  437: 445 */     close();
/*  438:     */   }
/*  439:     */   
/*  440:     */   public Connection connection()
/*  441:     */     throws HibernateException
/*  442:     */   {
/*  443: 449 */     errorIfClosed();
/*  444: 450 */     return this.transactionCoordinator.getJdbcCoordinator().getLogicalConnection().getDistinctConnectionProxy();
/*  445:     */   }
/*  446:     */   
/*  447:     */   public boolean isConnected()
/*  448:     */   {
/*  449: 454 */     checkTransactionSynchStatus();
/*  450: 455 */     return (!isClosed()) && (this.transactionCoordinator.getJdbcCoordinator().getLogicalConnection().isOpen());
/*  451:     */   }
/*  452:     */   
/*  453:     */   public boolean isTransactionInProgress()
/*  454:     */   {
/*  455: 459 */     checkTransactionSynchStatus();
/*  456: 460 */     return (!isClosed()) && (this.transactionCoordinator.isTransactionInProgress());
/*  457:     */   }
/*  458:     */   
/*  459:     */   public Connection disconnect()
/*  460:     */     throws HibernateException
/*  461:     */   {
/*  462: 465 */     errorIfClosed();
/*  463: 466 */     LOG.debug("Disconnecting session");
/*  464: 467 */     return this.transactionCoordinator.getJdbcCoordinator().getLogicalConnection().manualDisconnect();
/*  465:     */   }
/*  466:     */   
/*  467:     */   public void reconnect(Connection conn)
/*  468:     */     throws HibernateException
/*  469:     */   {
/*  470: 472 */     errorIfClosed();
/*  471: 473 */     LOG.debug("Reconnecting session");
/*  472: 474 */     checkTransactionSynchStatus();
/*  473: 475 */     this.transactionCoordinator.getJdbcCoordinator().getLogicalConnection().manualReconnect(conn);
/*  474:     */   }
/*  475:     */   
/*  476:     */   public void setAutoClear(boolean enabled)
/*  477:     */   {
/*  478: 479 */     errorIfClosed();
/*  479: 480 */     this.autoClear = enabled;
/*  480:     */   }
/*  481:     */   
/*  482:     */   public void disableTransactionAutoJoin()
/*  483:     */   {
/*  484: 485 */     errorIfClosed();
/*  485: 486 */     this.autoJoinTransactions = false;
/*  486:     */   }
/*  487:     */   
/*  488:     */   public void afterOperation(boolean success)
/*  489:     */   {
/*  490: 496 */     if (!this.transactionCoordinator.isTransactionInProgress()) {
/*  491: 497 */       this.transactionCoordinator.afterNonTransactionalQuery(success);
/*  492:     */     }
/*  493:     */   }
/*  494:     */   
/*  495:     */   public void afterTransactionBegin(TransactionImplementor hibernateTransaction)
/*  496:     */   {
/*  497: 503 */     errorIfClosed();
/*  498: 504 */     this.interceptor.afterTransactionBegin(hibernateTransaction);
/*  499:     */   }
/*  500:     */   
/*  501:     */   public void beforeTransactionCompletion(TransactionImplementor hibernateTransaction)
/*  502:     */   {
/*  503: 509 */     LOG.trace("before transaction completion");
/*  504: 510 */     this.actionQueue.beforeTransactionCompletion();
/*  505:     */     try
/*  506:     */     {
/*  507: 512 */       this.interceptor.beforeTransactionCompletion(hibernateTransaction);
/*  508:     */     }
/*  509:     */     catch (Throwable t)
/*  510:     */     {
/*  511: 515 */       LOG.exceptionInBeforeTransactionCompletionInterceptor(t);
/*  512:     */     }
/*  513:     */   }
/*  514:     */   
/*  515:     */   public void afterTransactionCompletion(TransactionImplementor hibernateTransaction, boolean successful)
/*  516:     */   {
/*  517: 521 */     LOG.trace("after transaction completion");
/*  518: 522 */     this.persistenceContext.afterTransactionCompletion();
/*  519: 523 */     this.actionQueue.afterTransactionCompletion(successful);
/*  520: 524 */     if (hibernateTransaction != null) {
/*  521:     */       try
/*  522:     */       {
/*  523: 526 */         this.interceptor.afterTransactionCompletion(hibernateTransaction);
/*  524:     */       }
/*  525:     */       catch (Throwable t)
/*  526:     */       {
/*  527: 529 */         LOG.exceptionInAfterTransactionCompletionInterceptor(t);
/*  528:     */       }
/*  529:     */     }
/*  530: 532 */     if (this.autoClear) {
/*  531: 533 */       clear();
/*  532:     */     }
/*  533:     */   }
/*  534:     */   
/*  535:     */   public String onPrepareStatement(String sql)
/*  536:     */   {
/*  537: 539 */     errorIfClosed();
/*  538: 540 */     sql = this.interceptor.onPrepareStatement(sql);
/*  539: 541 */     if ((sql == null) || (sql.length() == 0)) {
/*  540: 542 */       throw new AssertionFailure("Interceptor.onPrepareStatement() returned null or empty string.");
/*  541:     */     }
/*  542: 544 */     return sql;
/*  543:     */   }
/*  544:     */   
/*  545:     */   private void cleanup()
/*  546:     */   {
/*  547: 554 */     this.persistenceContext.clear();
/*  548:     */   }
/*  549:     */   
/*  550:     */   public LockMode getCurrentLockMode(Object object)
/*  551:     */     throws HibernateException
/*  552:     */   {
/*  553: 558 */     errorIfClosed();
/*  554: 559 */     checkTransactionSynchStatus();
/*  555: 560 */     if (object == null) {
/*  556: 561 */       throw new NullPointerException("null object passed to getCurrentLockMode()");
/*  557:     */     }
/*  558: 563 */     if ((object instanceof HibernateProxy))
/*  559:     */     {
/*  560: 564 */       object = ((HibernateProxy)object).getHibernateLazyInitializer().getImplementation(this);
/*  561: 565 */       if (object == null) {
/*  562: 566 */         return LockMode.NONE;
/*  563:     */       }
/*  564:     */     }
/*  565: 569 */     EntityEntry e = this.persistenceContext.getEntry(object);
/*  566: 570 */     if (e == null) {
/*  567: 571 */       throw new TransientObjectException("Given object not associated with the session");
/*  568:     */     }
/*  569: 573 */     if (e.getStatus() != Status.MANAGED) {
/*  570: 574 */       throw new ObjectDeletedException("The given object was deleted", e.getId(), e.getPersister().getEntityName());
/*  571:     */     }
/*  572: 580 */     return e.getLockMode();
/*  573:     */   }
/*  574:     */   
/*  575:     */   public Object getEntityUsingInterceptor(EntityKey key)
/*  576:     */     throws HibernateException
/*  577:     */   {
/*  578: 584 */     errorIfClosed();
/*  579:     */     
/*  580:     */ 
/*  581: 587 */     Object result = this.persistenceContext.getEntity(key);
/*  582: 588 */     if (result == null)
/*  583:     */     {
/*  584: 589 */       Object newObject = this.interceptor.getEntity(key.getEntityName(), key.getIdentifier());
/*  585: 590 */       if (newObject != null) {
/*  586: 591 */         lock(newObject, LockMode.NONE);
/*  587:     */       }
/*  588: 593 */       return newObject;
/*  589:     */     }
/*  590: 596 */     return result;
/*  591:     */   }
/*  592:     */   
/*  593:     */   public void saveOrUpdate(Object object)
/*  594:     */     throws HibernateException
/*  595:     */   {
/*  596: 604 */     saveOrUpdate(null, object);
/*  597:     */   }
/*  598:     */   
/*  599:     */   public void saveOrUpdate(String entityName, Object obj)
/*  600:     */     throws HibernateException
/*  601:     */   {
/*  602: 608 */     fireSaveOrUpdate(new SaveOrUpdateEvent(entityName, obj, this));
/*  603:     */   }
/*  604:     */   
/*  605:     */   private void fireSaveOrUpdate(SaveOrUpdateEvent event)
/*  606:     */   {
/*  607: 612 */     errorIfClosed();
/*  608: 613 */     checkTransactionSynchStatus();
/*  609: 614 */     for (SaveOrUpdateEventListener listener : listeners(EventType.SAVE_UPDATE)) {
/*  610: 615 */       listener.onSaveOrUpdate(event);
/*  611:     */     }
/*  612:     */   }
/*  613:     */   
/*  614:     */   private <T> Iterable<T> listeners(EventType<T> type)
/*  615:     */   {
/*  616: 620 */     return eventListenerGroup(type).listeners();
/*  617:     */   }
/*  618:     */   
/*  619:     */   private <T> EventListenerGroup<T> eventListenerGroup(EventType<T> type)
/*  620:     */   {
/*  621: 624 */     return ((EventListenerRegistry)this.factory.getServiceRegistry().getService(EventListenerRegistry.class)).getEventListenerGroup(type);
/*  622:     */   }
/*  623:     */   
/*  624:     */   public Serializable save(Object obj)
/*  625:     */     throws HibernateException
/*  626:     */   {
/*  627: 631 */     return save(null, obj);
/*  628:     */   }
/*  629:     */   
/*  630:     */   public Serializable save(String entityName, Object object)
/*  631:     */     throws HibernateException
/*  632:     */   {
/*  633: 635 */     return fireSave(new SaveOrUpdateEvent(entityName, object, this));
/*  634:     */   }
/*  635:     */   
/*  636:     */   private Serializable fireSave(SaveOrUpdateEvent event)
/*  637:     */   {
/*  638: 639 */     errorIfClosed();
/*  639: 640 */     checkTransactionSynchStatus();
/*  640: 641 */     for (SaveOrUpdateEventListener listener : listeners(EventType.SAVE)) {
/*  641: 642 */       listener.onSaveOrUpdate(event);
/*  642:     */     }
/*  643: 644 */     return event.getResultId();
/*  644:     */   }
/*  645:     */   
/*  646:     */   public void update(Object obj)
/*  647:     */     throws HibernateException
/*  648:     */   {
/*  649: 651 */     update(null, obj);
/*  650:     */   }
/*  651:     */   
/*  652:     */   public void update(String entityName, Object object)
/*  653:     */     throws HibernateException
/*  654:     */   {
/*  655: 655 */     fireUpdate(new SaveOrUpdateEvent(entityName, object, this));
/*  656:     */   }
/*  657:     */   
/*  658:     */   private void fireUpdate(SaveOrUpdateEvent event)
/*  659:     */   {
/*  660: 659 */     errorIfClosed();
/*  661: 660 */     checkTransactionSynchStatus();
/*  662: 661 */     for (SaveOrUpdateEventListener listener : listeners(EventType.UPDATE)) {
/*  663: 662 */       listener.onSaveOrUpdate(event);
/*  664:     */     }
/*  665:     */   }
/*  666:     */   
/*  667:     */   public void lock(String entityName, Object object, LockMode lockMode)
/*  668:     */     throws HibernateException
/*  669:     */   {
/*  670: 670 */     fireLock(new LockEvent(entityName, object, lockMode, this));
/*  671:     */   }
/*  672:     */   
/*  673:     */   public Session.LockRequest buildLockRequest(LockOptions lockOptions)
/*  674:     */   {
/*  675: 674 */     return new LockRequestImpl(lockOptions, null);
/*  676:     */   }
/*  677:     */   
/*  678:     */   public void lock(Object object, LockMode lockMode)
/*  679:     */     throws HibernateException
/*  680:     */   {
/*  681: 678 */     fireLock(new LockEvent(object, lockMode, this));
/*  682:     */   }
/*  683:     */   
/*  684:     */   private void fireLock(String entityName, Object object, LockOptions options)
/*  685:     */   {
/*  686: 682 */     fireLock(new LockEvent(entityName, object, options, this));
/*  687:     */   }
/*  688:     */   
/*  689:     */   private void fireLock(Object object, LockOptions options)
/*  690:     */   {
/*  691: 686 */     fireLock(new LockEvent(object, options, this));
/*  692:     */   }
/*  693:     */   
/*  694:     */   private void fireLock(LockEvent event)
/*  695:     */   {
/*  696: 690 */     errorIfClosed();
/*  697: 691 */     checkTransactionSynchStatus();
/*  698: 692 */     for (LockEventListener listener : listeners(EventType.LOCK)) {
/*  699: 693 */       listener.onLock(event);
/*  700:     */     }
/*  701:     */   }
/*  702:     */   
/*  703:     */   public void persist(String entityName, Object object)
/*  704:     */     throws HibernateException
/*  705:     */   {
/*  706: 701 */     firePersist(new PersistEvent(entityName, object, this));
/*  707:     */   }
/*  708:     */   
/*  709:     */   public void persist(Object object)
/*  710:     */     throws HibernateException
/*  711:     */   {
/*  712: 705 */     persist(null, object);
/*  713:     */   }
/*  714:     */   
/*  715:     */   public void persist(String entityName, Object object, Map copiedAlready)
/*  716:     */     throws HibernateException
/*  717:     */   {
/*  718: 710 */     firePersist(copiedAlready, new PersistEvent(entityName, object, this));
/*  719:     */   }
/*  720:     */   
/*  721:     */   private void firePersist(Map copiedAlready, PersistEvent event)
/*  722:     */   {
/*  723: 714 */     errorIfClosed();
/*  724: 715 */     checkTransactionSynchStatus();
/*  725: 716 */     for (PersistEventListener listener : listeners(EventType.PERSIST)) {
/*  726: 717 */       listener.onPersist(event, copiedAlready);
/*  727:     */     }
/*  728:     */   }
/*  729:     */   
/*  730:     */   private void firePersist(PersistEvent event)
/*  731:     */   {
/*  732: 722 */     errorIfClosed();
/*  733: 723 */     checkTransactionSynchStatus();
/*  734: 724 */     for (PersistEventListener listener : listeners(EventType.PERSIST)) {
/*  735: 725 */       listener.onPersist(event);
/*  736:     */     }
/*  737:     */   }
/*  738:     */   
/*  739:     */   public void persistOnFlush(String entityName, Object object)
/*  740:     */     throws HibernateException
/*  741:     */   {
/*  742: 734 */     firePersistOnFlush(new PersistEvent(entityName, object, this));
/*  743:     */   }
/*  744:     */   
/*  745:     */   public void persistOnFlush(Object object)
/*  746:     */     throws HibernateException
/*  747:     */   {
/*  748: 738 */     persist(null, object);
/*  749:     */   }
/*  750:     */   
/*  751:     */   public void persistOnFlush(String entityName, Object object, Map copiedAlready)
/*  752:     */     throws HibernateException
/*  753:     */   {
/*  754: 743 */     firePersistOnFlush(copiedAlready, new PersistEvent(entityName, object, this));
/*  755:     */   }
/*  756:     */   
/*  757:     */   private void firePersistOnFlush(Map copiedAlready, PersistEvent event)
/*  758:     */   {
/*  759: 747 */     errorIfClosed();
/*  760: 748 */     checkTransactionSynchStatus();
/*  761: 749 */     for (PersistEventListener listener : listeners(EventType.PERSIST_ONFLUSH)) {
/*  762: 750 */       listener.onPersist(event, copiedAlready);
/*  763:     */     }
/*  764:     */   }
/*  765:     */   
/*  766:     */   private void firePersistOnFlush(PersistEvent event)
/*  767:     */   {
/*  768: 755 */     errorIfClosed();
/*  769: 756 */     checkTransactionSynchStatus();
/*  770: 757 */     for (PersistEventListener listener : listeners(EventType.PERSIST_ONFLUSH)) {
/*  771: 758 */       listener.onPersist(event);
/*  772:     */     }
/*  773:     */   }
/*  774:     */   
/*  775:     */   public Object merge(String entityName, Object object)
/*  776:     */     throws HibernateException
/*  777:     */   {
/*  778: 766 */     return fireMerge(new MergeEvent(entityName, object, this));
/*  779:     */   }
/*  780:     */   
/*  781:     */   public Object merge(Object object)
/*  782:     */     throws HibernateException
/*  783:     */   {
/*  784: 770 */     return merge(null, object);
/*  785:     */   }
/*  786:     */   
/*  787:     */   public void merge(String entityName, Object object, Map copiedAlready)
/*  788:     */     throws HibernateException
/*  789:     */   {
/*  790: 774 */     fireMerge(copiedAlready, new MergeEvent(entityName, object, this));
/*  791:     */   }
/*  792:     */   
/*  793:     */   private Object fireMerge(MergeEvent event)
/*  794:     */   {
/*  795: 778 */     errorIfClosed();
/*  796: 779 */     checkTransactionSynchStatus();
/*  797: 780 */     for (MergeEventListener listener : listeners(EventType.MERGE)) {
/*  798: 781 */       listener.onMerge(event);
/*  799:     */     }
/*  800: 783 */     return event.getResult();
/*  801:     */   }
/*  802:     */   
/*  803:     */   private void fireMerge(Map copiedAlready, MergeEvent event)
/*  804:     */   {
/*  805: 787 */     errorIfClosed();
/*  806: 788 */     checkTransactionSynchStatus();
/*  807: 789 */     for (MergeEventListener listener : listeners(EventType.MERGE)) {
/*  808: 790 */       listener.onMerge(event, copiedAlready);
/*  809:     */     }
/*  810:     */   }
/*  811:     */   
/*  812:     */   public void delete(Object object)
/*  813:     */     throws HibernateException
/*  814:     */   {
/*  815: 801 */     fireDelete(new DeleteEvent(object, this));
/*  816:     */   }
/*  817:     */   
/*  818:     */   public void delete(String entityName, Object object)
/*  819:     */     throws HibernateException
/*  820:     */   {
/*  821: 808 */     fireDelete(new DeleteEvent(entityName, object, this));
/*  822:     */   }
/*  823:     */   
/*  824:     */   public void delete(String entityName, Object object, boolean isCascadeDeleteEnabled, Set transientEntities)
/*  825:     */     throws HibernateException
/*  826:     */   {
/*  827: 815 */     fireDelete(new DeleteEvent(entityName, object, isCascadeDeleteEnabled, this), transientEntities);
/*  828:     */   }
/*  829:     */   
/*  830:     */   private void fireDelete(DeleteEvent event)
/*  831:     */   {
/*  832: 819 */     errorIfClosed();
/*  833: 820 */     checkTransactionSynchStatus();
/*  834: 821 */     for (DeleteEventListener listener : listeners(EventType.DELETE)) {
/*  835: 822 */       listener.onDelete(event);
/*  836:     */     }
/*  837:     */   }
/*  838:     */   
/*  839:     */   private void fireDelete(DeleteEvent event, Set transientEntities)
/*  840:     */   {
/*  841: 827 */     errorIfClosed();
/*  842: 828 */     checkTransactionSynchStatus();
/*  843: 829 */     for (DeleteEventListener listener : listeners(EventType.DELETE)) {
/*  844: 830 */       listener.onDelete(event, transientEntities);
/*  845:     */     }
/*  846:     */   }
/*  847:     */   
/*  848:     */   public void load(Object object, Serializable id)
/*  849:     */     throws HibernateException
/*  850:     */   {
/*  851: 838 */     LoadEvent event = new LoadEvent(id, object, this);
/*  852: 839 */     fireLoad(event, LoadEventListener.RELOAD);
/*  853:     */   }
/*  854:     */   
/*  855:     */   public Object load(Class entityClass, Serializable id)
/*  856:     */     throws HibernateException
/*  857:     */   {
/*  858: 843 */     return load(entityClass.getName(), id);
/*  859:     */   }
/*  860:     */   
/*  861:     */   public Object load(String entityName, Serializable id)
/*  862:     */     throws HibernateException
/*  863:     */   {
/*  864: 847 */     LoadEvent event = new LoadEvent(id, entityName, false, this);
/*  865: 848 */     boolean success = false;
/*  866:     */     try
/*  867:     */     {
/*  868: 850 */       fireLoad(event, LoadEventListener.LOAD);
/*  869: 851 */       if (event.getResult() == null) {
/*  870: 852 */         getFactory().getEntityNotFoundDelegate().handleEntityNotFound(entityName, id);
/*  871:     */       }
/*  872: 854 */       success = true;
/*  873: 855 */       return event.getResult();
/*  874:     */     }
/*  875:     */     finally
/*  876:     */     {
/*  877: 858 */       afterOperation(success);
/*  878:     */     }
/*  879:     */   }
/*  880:     */   
/*  881:     */   public Object get(Class entityClass, Serializable id)
/*  882:     */     throws HibernateException
/*  883:     */   {
/*  884: 863 */     return get(entityClass.getName(), id);
/*  885:     */   }
/*  886:     */   
/*  887:     */   public Object get(String entityName, Serializable id)
/*  888:     */     throws HibernateException
/*  889:     */   {
/*  890: 867 */     LoadEvent event = new LoadEvent(id, entityName, false, this);
/*  891: 868 */     boolean success = false;
/*  892:     */     try
/*  893:     */     {
/*  894: 870 */       fireLoad(event, LoadEventListener.GET);
/*  895: 871 */       success = true;
/*  896: 872 */       return event.getResult();
/*  897:     */     }
/*  898:     */     finally
/*  899:     */     {
/*  900: 875 */       afterOperation(success);
/*  901:     */     }
/*  902:     */   }
/*  903:     */   
/*  904:     */   public Object immediateLoad(String entityName, Serializable id)
/*  905:     */     throws HibernateException
/*  906:     */   {
/*  907: 885 */     if (LOG.isDebugEnabled())
/*  908:     */     {
/*  909: 886 */       EntityPersister persister = getFactory().getEntityPersister(entityName);
/*  910: 887 */       LOG.debugf("Initializing proxy: %s", MessageHelper.infoString(persister, id, getFactory()));
/*  911:     */     }
/*  912: 890 */     LoadEvent event = new LoadEvent(id, entityName, true, this);
/*  913: 891 */     fireLoad(event, LoadEventListener.IMMEDIATE_LOAD);
/*  914: 892 */     return event.getResult();
/*  915:     */   }
/*  916:     */   
/*  917:     */   public Object internalLoad(String entityName, Serializable id, boolean eager, boolean nullable)
/*  918:     */     throws HibernateException
/*  919:     */   {
/*  920: 897 */     LoadEventListener.LoadType type = eager ? LoadEventListener.INTERNAL_LOAD_EAGER : nullable ? LoadEventListener.INTERNAL_LOAD_NULLABLE : LoadEventListener.INTERNAL_LOAD_LAZY;
/*  921:     */     
/*  922:     */ 
/*  923:     */ 
/*  924:     */ 
/*  925: 902 */     LoadEvent event = new LoadEvent(id, entityName, true, this);
/*  926: 903 */     fireLoad(event, type);
/*  927: 904 */     if (!nullable) {
/*  928: 905 */       UnresolvableObjectException.throwIfNull(event.getResult(), id, entityName);
/*  929:     */     }
/*  930: 907 */     return event.getResult();
/*  931:     */   }
/*  932:     */   
/*  933:     */   public Object load(Class entityClass, Serializable id, LockMode lockMode)
/*  934:     */     throws HibernateException
/*  935:     */   {
/*  936: 911 */     return load(entityClass.getName(), id, lockMode);
/*  937:     */   }
/*  938:     */   
/*  939:     */   public Object load(Class entityClass, Serializable id, LockOptions lockOptions)
/*  940:     */     throws HibernateException
/*  941:     */   {
/*  942: 915 */     return load(entityClass.getName(), id, lockOptions);
/*  943:     */   }
/*  944:     */   
/*  945:     */   public Object load(String entityName, Serializable id, LockMode lockMode)
/*  946:     */     throws HibernateException
/*  947:     */   {
/*  948: 919 */     LoadEvent event = new LoadEvent(id, entityName, lockMode, this);
/*  949: 920 */     fireLoad(event, LoadEventListener.LOAD);
/*  950: 921 */     return event.getResult();
/*  951:     */   }
/*  952:     */   
/*  953:     */   public Object load(String entityName, Serializable id, LockOptions lockOptions)
/*  954:     */     throws HibernateException
/*  955:     */   {
/*  956: 925 */     LoadEvent event = new LoadEvent(id, entityName, lockOptions, this);
/*  957: 926 */     fireLoad(event, LoadEventListener.LOAD);
/*  958: 927 */     return event.getResult();
/*  959:     */   }
/*  960:     */   
/*  961:     */   public Object get(Class entityClass, Serializable id, LockMode lockMode)
/*  962:     */     throws HibernateException
/*  963:     */   {
/*  964: 931 */     return get(entityClass.getName(), id, lockMode);
/*  965:     */   }
/*  966:     */   
/*  967:     */   public Object get(Class entityClass, Serializable id, LockOptions lockOptions)
/*  968:     */     throws HibernateException
/*  969:     */   {
/*  970: 935 */     return get(entityClass.getName(), id, lockOptions);
/*  971:     */   }
/*  972:     */   
/*  973:     */   public Object get(String entityName, Serializable id, LockMode lockMode)
/*  974:     */     throws HibernateException
/*  975:     */   {
/*  976: 939 */     LoadEvent event = new LoadEvent(id, entityName, lockMode, this);
/*  977: 940 */     fireLoad(event, LoadEventListener.GET);
/*  978: 941 */     return event.getResult();
/*  979:     */   }
/*  980:     */   
/*  981:     */   public Object get(String entityName, Serializable id, LockOptions lockOptions)
/*  982:     */     throws HibernateException
/*  983:     */   {
/*  984: 945 */     LoadEvent event = new LoadEvent(id, entityName, lockOptions, this);
/*  985: 946 */     fireLoad(event, LoadEventListener.GET);
/*  986: 947 */     return event.getResult();
/*  987:     */   }
/*  988:     */   
/*  989:     */   private void fireLoad(LoadEvent event, LoadEventListener.LoadType loadType)
/*  990:     */   {
/*  991: 951 */     errorIfClosed();
/*  992: 952 */     checkTransactionSynchStatus();
/*  993: 953 */     for (LoadEventListener listener : listeners(EventType.LOAD)) {
/*  994: 954 */       listener.onLoad(event, loadType);
/*  995:     */     }
/*  996:     */   }
/*  997:     */   
/*  998:     */   public void refresh(Object object)
/*  999:     */     throws HibernateException
/* 1000:     */   {
/* 1001: 962 */     refresh(null, object);
/* 1002:     */   }
/* 1003:     */   
/* 1004:     */   public void refresh(String entityName, Object object)
/* 1005:     */     throws HibernateException
/* 1006:     */   {
/* 1007: 967 */     fireRefresh(new RefreshEvent(entityName, object, this));
/* 1008:     */   }
/* 1009:     */   
/* 1010:     */   public void refresh(Object object, LockMode lockMode)
/* 1011:     */     throws HibernateException
/* 1012:     */   {
/* 1013: 971 */     fireRefresh(new RefreshEvent(object, lockMode, this));
/* 1014:     */   }
/* 1015:     */   
/* 1016:     */   public void refresh(Object object, LockOptions lockOptions)
/* 1017:     */     throws HibernateException
/* 1018:     */   {
/* 1019: 975 */     refresh(null, object, lockOptions);
/* 1020:     */   }
/* 1021:     */   
/* 1022:     */   public void refresh(String entityName, Object object, LockOptions lockOptions)
/* 1023:     */     throws HibernateException
/* 1024:     */   {
/* 1025: 979 */     fireRefresh(new RefreshEvent(entityName, object, lockOptions, this));
/* 1026:     */   }
/* 1027:     */   
/* 1028:     */   public void refresh(Object object, Map refreshedAlready)
/* 1029:     */     throws HibernateException
/* 1030:     */   {
/* 1031: 983 */     fireRefresh(refreshedAlready, new RefreshEvent(object, this));
/* 1032:     */   }
/* 1033:     */   
/* 1034:     */   private void fireRefresh(RefreshEvent event)
/* 1035:     */   {
/* 1036: 987 */     errorIfClosed();
/* 1037: 988 */     checkTransactionSynchStatus();
/* 1038: 989 */     for (RefreshEventListener listener : listeners(EventType.REFRESH)) {
/* 1039: 990 */       listener.onRefresh(event);
/* 1040:     */     }
/* 1041:     */   }
/* 1042:     */   
/* 1043:     */   private void fireRefresh(Map refreshedAlready, RefreshEvent event)
/* 1044:     */   {
/* 1045: 995 */     errorIfClosed();
/* 1046: 996 */     checkTransactionSynchStatus();
/* 1047: 997 */     for (RefreshEventListener listener : listeners(EventType.REFRESH)) {
/* 1048: 998 */       listener.onRefresh(event, refreshedAlready);
/* 1049:     */     }
/* 1050:     */   }
/* 1051:     */   
/* 1052:     */   public void replicate(Object obj, ReplicationMode replicationMode)
/* 1053:     */     throws HibernateException
/* 1054:     */   {
/* 1055:1006 */     fireReplicate(new ReplicateEvent(obj, replicationMode, this));
/* 1056:     */   }
/* 1057:     */   
/* 1058:     */   public void replicate(String entityName, Object obj, ReplicationMode replicationMode)
/* 1059:     */     throws HibernateException
/* 1060:     */   {
/* 1061:1011 */     fireReplicate(new ReplicateEvent(entityName, obj, replicationMode, this));
/* 1062:     */   }
/* 1063:     */   
/* 1064:     */   private void fireReplicate(ReplicateEvent event)
/* 1065:     */   {
/* 1066:1015 */     errorIfClosed();
/* 1067:1016 */     checkTransactionSynchStatus();
/* 1068:1017 */     for (ReplicateEventListener listener : listeners(EventType.REPLICATE)) {
/* 1069:1018 */       listener.onReplicate(event);
/* 1070:     */     }
/* 1071:     */   }
/* 1072:     */   
/* 1073:     */   public void evict(Object object)
/* 1074:     */     throws HibernateException
/* 1075:     */   {
/* 1076:1030 */     fireEvict(new EvictEvent(object, this));
/* 1077:     */   }
/* 1078:     */   
/* 1079:     */   private void fireEvict(EvictEvent event)
/* 1080:     */   {
/* 1081:1034 */     errorIfClosed();
/* 1082:1035 */     checkTransactionSynchStatus();
/* 1083:1036 */     for (EvictEventListener listener : listeners(EventType.EVICT)) {
/* 1084:1037 */       listener.onEvict(event);
/* 1085:     */     }
/* 1086:     */   }
/* 1087:     */   
/* 1088:     */   protected boolean autoFlushIfRequired(Set querySpaces)
/* 1089:     */     throws HibernateException
/* 1090:     */   {
/* 1091:1046 */     errorIfClosed();
/* 1092:1047 */     if (!isTransactionInProgress()) {
/* 1093:1049 */       return false;
/* 1094:     */     }
/* 1095:1051 */     AutoFlushEvent event = new AutoFlushEvent(querySpaces, this);
/* 1096:1052 */     for (AutoFlushEventListener listener : listeners(EventType.AUTO_FLUSH)) {
/* 1097:1053 */       listener.onAutoFlush(event);
/* 1098:     */     }
/* 1099:1055 */     return event.isFlushRequired();
/* 1100:     */   }
/* 1101:     */   
/* 1102:     */   public boolean isDirty()
/* 1103:     */     throws HibernateException
/* 1104:     */   {
/* 1105:1059 */     errorIfClosed();
/* 1106:1060 */     checkTransactionSynchStatus();
/* 1107:1061 */     LOG.debug("Checking session dirtiness");
/* 1108:1062 */     if (this.actionQueue.areInsertionsOrDeletionsQueued())
/* 1109:     */     {
/* 1110:1063 */       LOG.debug("Session dirty (scheduled updates and insertions)");
/* 1111:1064 */       return true;
/* 1112:     */     }
/* 1113:1066 */     DirtyCheckEvent event = new DirtyCheckEvent(this);
/* 1114:1067 */     for (DirtyCheckEventListener listener : listeners(EventType.DIRTY_CHECK)) {
/* 1115:1068 */       listener.onDirtyCheck(event);
/* 1116:     */     }
/* 1117:1070 */     return event.isDirty();
/* 1118:     */   }
/* 1119:     */   
/* 1120:     */   public void flush()
/* 1121:     */     throws HibernateException
/* 1122:     */   {
/* 1123:1074 */     errorIfClosed();
/* 1124:1075 */     checkTransactionSynchStatus();
/* 1125:1076 */     if (this.persistenceContext.getCascadeLevel() > 0) {
/* 1126:1077 */       throw new HibernateException("Flush during cascade is dangerous");
/* 1127:     */     }
/* 1128:1079 */     FlushEvent flushEvent = new FlushEvent(this);
/* 1129:1080 */     for (FlushEventListener listener : listeners(EventType.FLUSH)) {
/* 1130:1081 */       listener.onFlush(flushEvent);
/* 1131:     */     }
/* 1132:     */   }
/* 1133:     */   
/* 1134:     */   public void forceFlush(EntityEntry entityEntry)
/* 1135:     */     throws HibernateException
/* 1136:     */   {
/* 1137:1086 */     errorIfClosed();
/* 1138:1087 */     if (LOG.isDebugEnabled()) {
/* 1139:1088 */       LOG.debugf("Flushing to force deletion of re-saved object: %s", MessageHelper.infoString(entityEntry.getPersister(), entityEntry.getId(), getFactory()));
/* 1140:     */     }
/* 1141:1092 */     if (this.persistenceContext.getCascadeLevel() > 0) {
/* 1142:1093 */       throw new ObjectDeletedException("deleted object would be re-saved by cascade (remove deleted object from associations)", entityEntry.getId(), entityEntry.getPersister().getEntityName());
/* 1143:     */     }
/* 1144:1100 */     flush();
/* 1145:     */   }
/* 1146:     */   
/* 1147:     */   public List list(String query, QueryParameters queryParameters)
/* 1148:     */     throws HibernateException
/* 1149:     */   {
/* 1150:1104 */     errorIfClosed();
/* 1151:1105 */     checkTransactionSynchStatus();
/* 1152:1106 */     queryParameters.validateParameters();
/* 1153:1107 */     HQLQueryPlan plan = getHQLQueryPlan(query, false);
/* 1154:1108 */     autoFlushIfRequired(plan.getQuerySpaces());
/* 1155:     */     
/* 1156:1110 */     List results = CollectionHelper.EMPTY_LIST;
/* 1157:1111 */     boolean success = false;
/* 1158:     */     
/* 1159:1113 */     this.dontFlushFromFind += 1;
/* 1160:     */     try
/* 1161:     */     {
/* 1162:1115 */       results = plan.performList(queryParameters, this);
/* 1163:1116 */       success = true;
/* 1164:     */     }
/* 1165:     */     finally
/* 1166:     */     {
/* 1167:1119 */       this.dontFlushFromFind -= 1;
/* 1168:1120 */       afterOperation(success);
/* 1169:     */     }
/* 1170:1122 */     return results;
/* 1171:     */   }
/* 1172:     */   
/* 1173:     */   public int executeUpdate(String query, QueryParameters queryParameters)
/* 1174:     */     throws HibernateException
/* 1175:     */   {
/* 1176:1126 */     errorIfClosed();
/* 1177:1127 */     checkTransactionSynchStatus();
/* 1178:1128 */     queryParameters.validateParameters();
/* 1179:1129 */     HQLQueryPlan plan = getHQLQueryPlan(query, false);
/* 1180:1130 */     autoFlushIfRequired(plan.getQuerySpaces());
/* 1181:     */     
/* 1182:1132 */     boolean success = false;
/* 1183:1133 */     int result = 0;
/* 1184:     */     try
/* 1185:     */     {
/* 1186:1135 */       result = plan.performExecuteUpdate(queryParameters, this);
/* 1187:1136 */       success = true;
/* 1188:     */     }
/* 1189:     */     finally
/* 1190:     */     {
/* 1191:1139 */       afterOperation(success);
/* 1192:     */     }
/* 1193:1141 */     return result;
/* 1194:     */   }
/* 1195:     */   
/* 1196:     */   public int executeNativeUpdate(NativeSQLQuerySpecification nativeQuerySpecification, QueryParameters queryParameters)
/* 1197:     */     throws HibernateException
/* 1198:     */   {
/* 1199:1146 */     errorIfClosed();
/* 1200:1147 */     checkTransactionSynchStatus();
/* 1201:1148 */     queryParameters.validateParameters();
/* 1202:1149 */     NativeSQLQueryPlan plan = getNativeSQLQueryPlan(nativeQuerySpecification);
/* 1203:     */     
/* 1204:     */ 
/* 1205:1152 */     autoFlushIfRequired(plan.getCustomQuery().getQuerySpaces());
/* 1206:     */     
/* 1207:1154 */     boolean success = false;
/* 1208:1155 */     int result = 0;
/* 1209:     */     try
/* 1210:     */     {
/* 1211:1157 */       result = plan.performExecuteUpdate(queryParameters, this);
/* 1212:1158 */       success = true;
/* 1213:     */     }
/* 1214:     */     finally
/* 1215:     */     {
/* 1216:1160 */       afterOperation(success);
/* 1217:     */     }
/* 1218:1162 */     return result;
/* 1219:     */   }
/* 1220:     */   
/* 1221:     */   public Iterator iterate(String query, QueryParameters queryParameters)
/* 1222:     */     throws HibernateException
/* 1223:     */   {
/* 1224:1166 */     errorIfClosed();
/* 1225:1167 */     checkTransactionSynchStatus();
/* 1226:1168 */     queryParameters.validateParameters();
/* 1227:1169 */     HQLQueryPlan plan = getHQLQueryPlan(query, true);
/* 1228:1170 */     autoFlushIfRequired(plan.getQuerySpaces());
/* 1229:     */     
/* 1230:1172 */     this.dontFlushFromFind += 1;
/* 1231:     */     try
/* 1232:     */     {
/* 1233:1174 */       return plan.performIterate(queryParameters, this);
/* 1234:     */     }
/* 1235:     */     finally
/* 1236:     */     {
/* 1237:1177 */       this.dontFlushFromFind -= 1;
/* 1238:     */     }
/* 1239:     */   }
/* 1240:     */   
/* 1241:     */   public ScrollableResults scroll(String query, QueryParameters queryParameters)
/* 1242:     */     throws HibernateException
/* 1243:     */   {
/* 1244:1182 */     errorIfClosed();
/* 1245:1183 */     checkTransactionSynchStatus();
/* 1246:1184 */     HQLQueryPlan plan = getHQLQueryPlan(query, false);
/* 1247:1185 */     autoFlushIfRequired(plan.getQuerySpaces());
/* 1248:1186 */     this.dontFlushFromFind += 1;
/* 1249:     */     try
/* 1250:     */     {
/* 1251:1188 */       return plan.performScroll(queryParameters, this);
/* 1252:     */     }
/* 1253:     */     finally
/* 1254:     */     {
/* 1255:1191 */       this.dontFlushFromFind -= 1;
/* 1256:     */     }
/* 1257:     */   }
/* 1258:     */   
/* 1259:     */   public Query createFilter(Object collection, String queryString)
/* 1260:     */   {
/* 1261:1196 */     errorIfClosed();
/* 1262:1197 */     checkTransactionSynchStatus();
/* 1263:1198 */     CollectionFilterImpl filter = new CollectionFilterImpl(queryString, collection, this, getFilterQueryPlan(collection, queryString, null, false).getParameterMetadata());
/* 1264:     */     
/* 1265:     */ 
/* 1266:     */ 
/* 1267:     */ 
/* 1268:     */ 
/* 1269:1204 */     filter.setComment(queryString);
/* 1270:1205 */     return filter;
/* 1271:     */   }
/* 1272:     */   
/* 1273:     */   public Query getNamedQuery(String queryName)
/* 1274:     */     throws MappingException
/* 1275:     */   {
/* 1276:1209 */     errorIfClosed();
/* 1277:1210 */     checkTransactionSynchStatus();
/* 1278:1211 */     return super.getNamedQuery(queryName);
/* 1279:     */   }
/* 1280:     */   
/* 1281:     */   public Object instantiate(String entityName, Serializable id)
/* 1282:     */     throws HibernateException
/* 1283:     */   {
/* 1284:1215 */     return instantiate(this.factory.getEntityPersister(entityName), id);
/* 1285:     */   }
/* 1286:     */   
/* 1287:     */   public Object instantiate(EntityPersister persister, Serializable id)
/* 1288:     */     throws HibernateException
/* 1289:     */   {
/* 1290:1222 */     errorIfClosed();
/* 1291:1223 */     checkTransactionSynchStatus();
/* 1292:1224 */     Object result = this.interceptor.instantiate(persister.getEntityName(), persister.getEntityMetamodel().getEntityMode(), id);
/* 1293:1225 */     if (result == null) {
/* 1294:1226 */       result = persister.instantiate(id, this);
/* 1295:     */     }
/* 1296:1228 */     return result;
/* 1297:     */   }
/* 1298:     */   
/* 1299:     */   public void setFlushMode(FlushMode flushMode)
/* 1300:     */   {
/* 1301:1232 */     errorIfClosed();
/* 1302:1233 */     checkTransactionSynchStatus();
/* 1303:1234 */     LOG.tracev("Setting flush mode to: {0}", flushMode);
/* 1304:1235 */     this.flushMode = flushMode;
/* 1305:     */   }
/* 1306:     */   
/* 1307:     */   public FlushMode getFlushMode()
/* 1308:     */   {
/* 1309:1239 */     checkTransactionSynchStatus();
/* 1310:1240 */     return this.flushMode;
/* 1311:     */   }
/* 1312:     */   
/* 1313:     */   public CacheMode getCacheMode()
/* 1314:     */   {
/* 1315:1244 */     checkTransactionSynchStatus();
/* 1316:1245 */     return this.cacheMode;
/* 1317:     */   }
/* 1318:     */   
/* 1319:     */   public void setCacheMode(CacheMode cacheMode)
/* 1320:     */   {
/* 1321:1249 */     errorIfClosed();
/* 1322:1250 */     checkTransactionSynchStatus();
/* 1323:1251 */     LOG.tracev("Setting cache mode to: {0}", cacheMode);
/* 1324:1252 */     this.cacheMode = cacheMode;
/* 1325:     */   }
/* 1326:     */   
/* 1327:     */   public Transaction getTransaction()
/* 1328:     */     throws HibernateException
/* 1329:     */   {
/* 1330:1256 */     errorIfClosed();
/* 1331:1257 */     return this.transactionCoordinator.getTransaction();
/* 1332:     */   }
/* 1333:     */   
/* 1334:     */   public Transaction beginTransaction()
/* 1335:     */     throws HibernateException
/* 1336:     */   {
/* 1337:1261 */     errorIfClosed();
/* 1338:1262 */     Transaction result = getTransaction();
/* 1339:1263 */     result.begin();
/* 1340:1264 */     return result;
/* 1341:     */   }
/* 1342:     */   
/* 1343:     */   public EntityPersister getEntityPersister(String entityName, Object object)
/* 1344:     */   {
/* 1345:1268 */     errorIfClosed();
/* 1346:1269 */     if (entityName == null) {
/* 1347:1270 */       return this.factory.getEntityPersister(guessEntityName(object));
/* 1348:     */     }
/* 1349:     */     try
/* 1350:     */     {
/* 1351:1279 */       return this.factory.getEntityPersister(entityName).getSubclassEntityPersister(object, getFactory());
/* 1352:     */     }
/* 1353:     */     catch (HibernateException e)
/* 1354:     */     {
/* 1355:     */       try
/* 1356:     */       {
/* 1357:1283 */         return getEntityPersister(null, object);
/* 1358:     */       }
/* 1359:     */       catch (HibernateException e2)
/* 1360:     */       {
/* 1361:1286 */         throw e;
/* 1362:     */       }
/* 1363:     */     }
/* 1364:     */   }
/* 1365:     */   
/* 1366:     */   public Serializable getIdentifier(Object object)
/* 1367:     */     throws HibernateException
/* 1368:     */   {
/* 1369:1294 */     errorIfClosed();
/* 1370:1295 */     checkTransactionSynchStatus();
/* 1371:1296 */     if ((object instanceof HibernateProxy))
/* 1372:     */     {
/* 1373:1297 */       LazyInitializer li = ((HibernateProxy)object).getHibernateLazyInitializer();
/* 1374:1298 */       if (li.getSession() != this) {
/* 1375:1299 */         throw new TransientObjectException("The proxy was not associated with this session");
/* 1376:     */       }
/* 1377:1301 */       return li.getIdentifier();
/* 1378:     */     }
/* 1379:1304 */     EntityEntry entry = this.persistenceContext.getEntry(object);
/* 1380:1305 */     if (entry == null) {
/* 1381:1306 */       throw new TransientObjectException("The instance was not associated with this session");
/* 1382:     */     }
/* 1383:1308 */     return entry.getId();
/* 1384:     */   }
/* 1385:     */   
/* 1386:     */   public Serializable getContextEntityIdentifier(Object object)
/* 1387:     */   {
/* 1388:1317 */     errorIfClosed();
/* 1389:1318 */     if ((object instanceof HibernateProxy)) {
/* 1390:1319 */       return getProxyIdentifier(object);
/* 1391:     */     }
/* 1392:1322 */     EntityEntry entry = this.persistenceContext.getEntry(object);
/* 1393:1323 */     return entry != null ? entry.getId() : null;
/* 1394:     */   }
/* 1395:     */   
/* 1396:     */   private Serializable getProxyIdentifier(Object proxy)
/* 1397:     */   {
/* 1398:1328 */     return ((HibernateProxy)proxy).getHibernateLazyInitializer().getIdentifier();
/* 1399:     */   }
/* 1400:     */   
/* 1401:     */   private FilterQueryPlan getFilterQueryPlan(Object collection, String filter, QueryParameters parameters, boolean shallow)
/* 1402:     */     throws HibernateException
/* 1403:     */   {
/* 1404:1336 */     if (collection == null) {
/* 1405:1337 */       throw new NullPointerException("null collection passed to filter");
/* 1406:     */     }
/* 1407:1340 */     CollectionEntry entry = this.persistenceContext.getCollectionEntryOrNull(collection);
/* 1408:1341 */     CollectionPersister roleBeforeFlush = entry == null ? null : entry.getLoadedPersister();
/* 1409:     */     
/* 1410:1343 */     FilterQueryPlan plan = null;
/* 1411:1344 */     if (roleBeforeFlush == null)
/* 1412:     */     {
/* 1413:1347 */       flush();
/* 1414:1348 */       entry = this.persistenceContext.getCollectionEntryOrNull(collection);
/* 1415:1349 */       CollectionPersister roleAfterFlush = entry == null ? null : entry.getLoadedPersister();
/* 1416:1350 */       if (roleAfterFlush == null) {
/* 1417:1351 */         throw new QueryException("The collection was unreferenced");
/* 1418:     */       }
/* 1419:1353 */       plan = this.factory.getQueryPlanCache().getFilterQueryPlan(filter, roleAfterFlush.getRole(), shallow, getEnabledFilters());
/* 1420:     */     }
/* 1421:     */     else
/* 1422:     */     {
/* 1423:1358 */       plan = this.factory.getQueryPlanCache().getFilterQueryPlan(filter, roleBeforeFlush.getRole(), shallow, getEnabledFilters());
/* 1424:1359 */       if (autoFlushIfRequired(plan.getQuerySpaces()))
/* 1425:     */       {
/* 1426:1362 */         entry = this.persistenceContext.getCollectionEntryOrNull(collection);
/* 1427:1363 */         CollectionPersister roleAfterFlush = entry == null ? null : entry.getLoadedPersister();
/* 1428:1364 */         if (roleBeforeFlush != roleAfterFlush)
/* 1429:     */         {
/* 1430:1365 */           if (roleAfterFlush == null) {
/* 1431:1366 */             throw new QueryException("The collection was dereferenced");
/* 1432:     */           }
/* 1433:1368 */           plan = this.factory.getQueryPlanCache().getFilterQueryPlan(filter, roleAfterFlush.getRole(), shallow, getEnabledFilters());
/* 1434:     */         }
/* 1435:     */       }
/* 1436:     */     }
/* 1437:1373 */     if (parameters != null)
/* 1438:     */     {
/* 1439:1374 */       parameters.getPositionalParameterValues()[0] = entry.getLoadedKey();
/* 1440:1375 */       parameters.getPositionalParameterTypes()[0] = entry.getLoadedPersister().getKeyType();
/* 1441:     */     }
/* 1442:1378 */     return plan;
/* 1443:     */   }
/* 1444:     */   
/* 1445:     */   public List listFilter(Object collection, String filter, QueryParameters queryParameters)
/* 1446:     */     throws HibernateException
/* 1447:     */   {
/* 1448:1383 */     errorIfClosed();
/* 1449:1384 */     checkTransactionSynchStatus();
/* 1450:1385 */     FilterQueryPlan plan = getFilterQueryPlan(collection, filter, queryParameters, false);
/* 1451:1386 */     List results = CollectionHelper.EMPTY_LIST;
/* 1452:     */     
/* 1453:1388 */     boolean success = false;
/* 1454:1389 */     this.dontFlushFromFind += 1;
/* 1455:     */     try
/* 1456:     */     {
/* 1457:1391 */       results = plan.performList(queryParameters, this);
/* 1458:1392 */       success = true;
/* 1459:     */     }
/* 1460:     */     finally
/* 1461:     */     {
/* 1462:1395 */       this.dontFlushFromFind -= 1;
/* 1463:1396 */       afterOperation(success);
/* 1464:     */     }
/* 1465:1398 */     return results;
/* 1466:     */   }
/* 1467:     */   
/* 1468:     */   public Iterator iterateFilter(Object collection, String filter, QueryParameters queryParameters)
/* 1469:     */     throws HibernateException
/* 1470:     */   {
/* 1471:1403 */     errorIfClosed();
/* 1472:1404 */     checkTransactionSynchStatus();
/* 1473:1405 */     FilterQueryPlan plan = getFilterQueryPlan(collection, filter, queryParameters, true);
/* 1474:1406 */     return plan.performIterate(queryParameters, this);
/* 1475:     */   }
/* 1476:     */   
/* 1477:     */   public Criteria createCriteria(Class persistentClass, String alias)
/* 1478:     */   {
/* 1479:1410 */     errorIfClosed();
/* 1480:1411 */     checkTransactionSynchStatus();
/* 1481:1412 */     return new CriteriaImpl(persistentClass.getName(), alias, this);
/* 1482:     */   }
/* 1483:     */   
/* 1484:     */   public Criteria createCriteria(String entityName, String alias)
/* 1485:     */   {
/* 1486:1416 */     errorIfClosed();
/* 1487:1417 */     checkTransactionSynchStatus();
/* 1488:1418 */     return new CriteriaImpl(entityName, alias, this);
/* 1489:     */   }
/* 1490:     */   
/* 1491:     */   public Criteria createCriteria(Class persistentClass)
/* 1492:     */   {
/* 1493:1422 */     errorIfClosed();
/* 1494:1423 */     checkTransactionSynchStatus();
/* 1495:1424 */     return new CriteriaImpl(persistentClass.getName(), this);
/* 1496:     */   }
/* 1497:     */   
/* 1498:     */   public Criteria createCriteria(String entityName)
/* 1499:     */   {
/* 1500:1428 */     errorIfClosed();
/* 1501:1429 */     checkTransactionSynchStatus();
/* 1502:1430 */     return new CriteriaImpl(entityName, this);
/* 1503:     */   }
/* 1504:     */   
/* 1505:     */   public ScrollableResults scroll(CriteriaImpl criteria, ScrollMode scrollMode)
/* 1506:     */   {
/* 1507:1434 */     errorIfClosed();
/* 1508:1435 */     checkTransactionSynchStatus();
/* 1509:1436 */     String entityName = criteria.getEntityOrClassName();
/* 1510:1437 */     CriteriaLoader loader = new CriteriaLoader(getOuterJoinLoadable(entityName), this.factory, criteria, entityName, getLoadQueryInfluencers());
/* 1511:     */     
/* 1512:     */ 
/* 1513:     */ 
/* 1514:     */ 
/* 1515:     */ 
/* 1516:     */ 
/* 1517:1444 */     autoFlushIfRequired(loader.getQuerySpaces());
/* 1518:1445 */     this.dontFlushFromFind += 1;
/* 1519:     */     try
/* 1520:     */     {
/* 1521:1447 */       return loader.scroll(this, scrollMode);
/* 1522:     */     }
/* 1523:     */     finally
/* 1524:     */     {
/* 1525:1450 */       this.dontFlushFromFind -= 1;
/* 1526:     */     }
/* 1527:     */   }
/* 1528:     */   
/* 1529:     */   public List list(CriteriaImpl criteria)
/* 1530:     */     throws HibernateException
/* 1531:     */   {
/* 1532:1455 */     errorIfClosed();
/* 1533:1456 */     checkTransactionSynchStatus();
/* 1534:1457 */     String[] implementors = this.factory.getImplementors(criteria.getEntityOrClassName());
/* 1535:1458 */     int size = implementors.length;
/* 1536:     */     
/* 1537:1460 */     CriteriaLoader[] loaders = new CriteriaLoader[size];
/* 1538:1461 */     Set spaces = new HashSet();
/* 1539:1462 */     for (int i = 0; i < size; i++)
/* 1540:     */     {
/* 1541:1464 */       loaders[i] = new CriteriaLoader(getOuterJoinLoadable(implementors[i]), this.factory, criteria, implementors[i], getLoadQueryInfluencers());
/* 1542:     */       
/* 1543:     */ 
/* 1544:     */ 
/* 1545:     */ 
/* 1546:     */ 
/* 1547:     */ 
/* 1548:     */ 
/* 1549:1472 */       spaces.addAll(loaders[i].getQuerySpaces());
/* 1550:     */     }
/* 1551:1476 */     autoFlushIfRequired(spaces);
/* 1552:     */     
/* 1553:1478 */     List results = Collections.EMPTY_LIST;
/* 1554:1479 */     this.dontFlushFromFind += 1;
/* 1555:1480 */     boolean success = false;
/* 1556:     */     try
/* 1557:     */     {
/* 1558:1482 */       for (int i = 0; i < size; i++)
/* 1559:     */       {
/* 1560:1483 */         List currentResults = loaders[i].list(this);
/* 1561:1484 */         currentResults.addAll(results);
/* 1562:1485 */         results = currentResults;
/* 1563:     */       }
/* 1564:1487 */       success = true;
/* 1565:     */     }
/* 1566:     */     finally
/* 1567:     */     {
/* 1568:1490 */       this.dontFlushFromFind -= 1;
/* 1569:1491 */       afterOperation(success);
/* 1570:     */     }
/* 1571:1494 */     return results;
/* 1572:     */   }
/* 1573:     */   
/* 1574:     */   private OuterJoinLoadable getOuterJoinLoadable(String entityName)
/* 1575:     */     throws MappingException
/* 1576:     */   {
/* 1577:1498 */     EntityPersister persister = this.factory.getEntityPersister(entityName);
/* 1578:1499 */     if (!(persister instanceof OuterJoinLoadable)) {
/* 1579:1500 */       throw new MappingException("class persister is not OuterJoinLoadable: " + entityName);
/* 1580:     */     }
/* 1581:1502 */     return (OuterJoinLoadable)persister;
/* 1582:     */   }
/* 1583:     */   
/* 1584:     */   public boolean contains(Object object)
/* 1585:     */   {
/* 1586:1506 */     errorIfClosed();
/* 1587:1507 */     checkTransactionSynchStatus();
/* 1588:1508 */     if ((object instanceof HibernateProxy))
/* 1589:     */     {
/* 1590:1512 */       LazyInitializer li = ((HibernateProxy)object).getHibernateLazyInitializer();
/* 1591:1513 */       if (li.isUninitialized()) {
/* 1592:1517 */         return li.getSession() == this;
/* 1593:     */       }
/* 1594:1524 */       object = li.getImplementation();
/* 1595:     */     }
/* 1596:1530 */     EntityEntry entry = this.persistenceContext.getEntry(object);
/* 1597:1531 */     return (entry != null) && (entry.getStatus() != Status.DELETED) && (entry.getStatus() != Status.GONE);
/* 1598:     */   }
/* 1599:     */   
/* 1600:     */   public Query createQuery(String queryString)
/* 1601:     */   {
/* 1602:1535 */     errorIfClosed();
/* 1603:1536 */     checkTransactionSynchStatus();
/* 1604:1537 */     return super.createQuery(queryString);
/* 1605:     */   }
/* 1606:     */   
/* 1607:     */   public SQLQuery createSQLQuery(String sql)
/* 1608:     */   {
/* 1609:1541 */     errorIfClosed();
/* 1610:1542 */     checkTransactionSynchStatus();
/* 1611:1543 */     return super.createSQLQuery(sql);
/* 1612:     */   }
/* 1613:     */   
/* 1614:     */   public ScrollableResults scrollCustomQuery(CustomQuery customQuery, QueryParameters queryParameters)
/* 1615:     */     throws HibernateException
/* 1616:     */   {
/* 1617:1548 */     errorIfClosed();
/* 1618:1549 */     checkTransactionSynchStatus();
/* 1619:1551 */     if (LOG.isTraceEnabled()) {
/* 1620:1552 */       LOG.tracev("Scroll SQL query: {0}", customQuery.getSQL());
/* 1621:     */     }
/* 1622:1555 */     CustomLoader loader = new CustomLoader(customQuery, getFactory());
/* 1623:     */     
/* 1624:1557 */     autoFlushIfRequired(loader.getQuerySpaces());
/* 1625:     */     
/* 1626:1559 */     this.dontFlushFromFind += 1;
/* 1627:     */     try
/* 1628:     */     {
/* 1629:1561 */       return loader.scroll(queryParameters, this);
/* 1630:     */     }
/* 1631:     */     finally
/* 1632:     */     {
/* 1633:1564 */       this.dontFlushFromFind -= 1;
/* 1634:     */     }
/* 1635:     */   }
/* 1636:     */   
/* 1637:     */   public List listCustomQuery(CustomQuery customQuery, QueryParameters queryParameters)
/* 1638:     */     throws HibernateException
/* 1639:     */   {
/* 1640:1571 */     errorIfClosed();
/* 1641:1572 */     checkTransactionSynchStatus();
/* 1642:1574 */     if (LOG.isTraceEnabled()) {
/* 1643:1575 */       LOG.tracev("SQL query: {0}", customQuery.getSQL());
/* 1644:     */     }
/* 1645:1578 */     CustomLoader loader = new CustomLoader(customQuery, getFactory());
/* 1646:     */     
/* 1647:1580 */     autoFlushIfRequired(loader.getQuerySpaces());
/* 1648:     */     
/* 1649:1582 */     this.dontFlushFromFind += 1;
/* 1650:1583 */     boolean success = false;
/* 1651:     */     try
/* 1652:     */     {
/* 1653:1585 */       List results = loader.list(this, queryParameters);
/* 1654:1586 */       success = true;
/* 1655:1587 */       return results;
/* 1656:     */     }
/* 1657:     */     finally
/* 1658:     */     {
/* 1659:1590 */       this.dontFlushFromFind -= 1;
/* 1660:1591 */       afterOperation(success);
/* 1661:     */     }
/* 1662:     */   }
/* 1663:     */   
/* 1664:     */   public SessionFactoryImplementor getSessionFactory()
/* 1665:     */   {
/* 1666:1596 */     checkTransactionSynchStatus();
/* 1667:1597 */     return this.factory;
/* 1668:     */   }
/* 1669:     */   
/* 1670:     */   public void initializeCollection(PersistentCollection collection, boolean writing)
/* 1671:     */     throws HibernateException
/* 1672:     */   {
/* 1673:1602 */     errorIfClosed();
/* 1674:1603 */     checkTransactionSynchStatus();
/* 1675:1604 */     InitializeCollectionEvent event = new InitializeCollectionEvent(collection, this);
/* 1676:1605 */     for (InitializeCollectionEventListener listener : listeners(EventType.INIT_COLLECTION)) {
/* 1677:1606 */       listener.onInitializeCollection(event);
/* 1678:     */     }
/* 1679:     */   }
/* 1680:     */   
/* 1681:     */   public String bestGuessEntityName(Object object)
/* 1682:     */   {
/* 1683:1611 */     if ((object instanceof HibernateProxy))
/* 1684:     */     {
/* 1685:1612 */       LazyInitializer initializer = ((HibernateProxy)object).getHibernateLazyInitializer();
/* 1686:1615 */       if (initializer.isUninitialized()) {
/* 1687:1616 */         return initializer.getEntityName();
/* 1688:     */       }
/* 1689:1618 */       object = initializer.getImplementation();
/* 1690:     */     }
/* 1691:1620 */     EntityEntry entry = this.persistenceContext.getEntry(object);
/* 1692:1621 */     if (entry == null) {
/* 1693:1622 */       return guessEntityName(object);
/* 1694:     */     }
/* 1695:1625 */     return entry.getPersister().getEntityName();
/* 1696:     */   }
/* 1697:     */   
/* 1698:     */   public String getEntityName(Object object)
/* 1699:     */   {
/* 1700:1630 */     errorIfClosed();
/* 1701:1631 */     checkTransactionSynchStatus();
/* 1702:1632 */     if ((object instanceof HibernateProxy))
/* 1703:     */     {
/* 1704:1633 */       if (!this.persistenceContext.containsProxy(object)) {
/* 1705:1634 */         throw new TransientObjectException("proxy was not associated with the session");
/* 1706:     */       }
/* 1707:1636 */       object = ((HibernateProxy)object).getHibernateLazyInitializer().getImplementation();
/* 1708:     */     }
/* 1709:1639 */     EntityEntry entry = this.persistenceContext.getEntry(object);
/* 1710:1640 */     if (entry == null) {
/* 1711:1641 */       throwTransientObjectException(object);
/* 1712:     */     }
/* 1713:1643 */     return entry.getPersister().getEntityName();
/* 1714:     */   }
/* 1715:     */   
/* 1716:     */   private void throwTransientObjectException(Object object)
/* 1717:     */     throws HibernateException
/* 1718:     */   {
/* 1719:1647 */     throw new TransientObjectException("object references an unsaved transient instance - save the transient instance before flushing: " + guessEntityName(object));
/* 1720:     */   }
/* 1721:     */   
/* 1722:     */   public String guessEntityName(Object object)
/* 1723:     */     throws HibernateException
/* 1724:     */   {
/* 1725:1654 */     errorIfClosed();
/* 1726:1655 */     return this.entityNameResolver.resolveEntityName(object);
/* 1727:     */   }
/* 1728:     */   
/* 1729:     */   public void cancelQuery()
/* 1730:     */     throws HibernateException
/* 1731:     */   {
/* 1732:1659 */     errorIfClosed();
/* 1733:1660 */     getTransactionCoordinator().getJdbcCoordinator().cancelLastQuery();
/* 1734:     */   }
/* 1735:     */   
/* 1736:     */   public Interceptor getInterceptor()
/* 1737:     */   {
/* 1738:1664 */     checkTransactionSynchStatus();
/* 1739:1665 */     return this.interceptor;
/* 1740:     */   }
/* 1741:     */   
/* 1742:     */   public int getDontFlushFromFind()
/* 1743:     */   {
/* 1744:1669 */     return this.dontFlushFromFind;
/* 1745:     */   }
/* 1746:     */   
/* 1747:     */   public String toString()
/* 1748:     */   {
/* 1749:1673 */     StringBuffer buf = new StringBuffer(500).append("SessionImpl(");
/* 1750:1675 */     if (!isClosed()) {
/* 1751:1676 */       buf.append(this.persistenceContext).append(";").append(this.actionQueue);
/* 1752:     */     } else {
/* 1753:1681 */       buf.append("<closed>");
/* 1754:     */     }
/* 1755:1683 */     return ')';
/* 1756:     */   }
/* 1757:     */   
/* 1758:     */   public ActionQueue getActionQueue()
/* 1759:     */   {
/* 1760:1687 */     errorIfClosed();
/* 1761:1688 */     checkTransactionSynchStatus();
/* 1762:1689 */     return this.actionQueue;
/* 1763:     */   }
/* 1764:     */   
/* 1765:     */   public PersistenceContext getPersistenceContext()
/* 1766:     */   {
/* 1767:1693 */     errorIfClosed();
/* 1768:1694 */     checkTransactionSynchStatus();
/* 1769:1695 */     return this.persistenceContext;
/* 1770:     */   }
/* 1771:     */   
/* 1772:     */   public SessionStatistics getStatistics()
/* 1773:     */   {
/* 1774:1699 */     checkTransactionSynchStatus();
/* 1775:1700 */     return new SessionStatisticsImpl(this);
/* 1776:     */   }
/* 1777:     */   
/* 1778:     */   public boolean isEventSource()
/* 1779:     */   {
/* 1780:1704 */     checkTransactionSynchStatus();
/* 1781:1705 */     return true;
/* 1782:     */   }
/* 1783:     */   
/* 1784:     */   public boolean isDefaultReadOnly()
/* 1785:     */   {
/* 1786:1712 */     return this.persistenceContext.isDefaultReadOnly();
/* 1787:     */   }
/* 1788:     */   
/* 1789:     */   public void setDefaultReadOnly(boolean defaultReadOnly)
/* 1790:     */   {
/* 1791:1719 */     this.persistenceContext.setDefaultReadOnly(defaultReadOnly);
/* 1792:     */   }
/* 1793:     */   
/* 1794:     */   public boolean isReadOnly(Object entityOrProxy)
/* 1795:     */   {
/* 1796:1723 */     errorIfClosed();
/* 1797:1724 */     checkTransactionSynchStatus();
/* 1798:1725 */     return this.persistenceContext.isReadOnly(entityOrProxy);
/* 1799:     */   }
/* 1800:     */   
/* 1801:     */   public void setReadOnly(Object entity, boolean readOnly)
/* 1802:     */   {
/* 1803:1729 */     errorIfClosed();
/* 1804:1730 */     checkTransactionSynchStatus();
/* 1805:1731 */     this.persistenceContext.setReadOnly(entity, readOnly);
/* 1806:     */   }
/* 1807:     */   
/* 1808:     */   public void doWork(final Work work)
/* 1809:     */     throws HibernateException
/* 1810:     */   {
/* 1811:1735 */     WorkExecutorVisitable<Void> realWork = new WorkExecutorVisitable()
/* 1812:     */     {
/* 1813:     */       public Void accept(WorkExecutor<Void> workExecutor, Connection connection)
/* 1814:     */         throws SQLException
/* 1815:     */       {
/* 1816:1738 */         workExecutor.executeWork(work, connection);
/* 1817:1739 */         return null;
/* 1818:     */       }
/* 1819:1741 */     };
/* 1820:1742 */     doWork(realWork);
/* 1821:     */   }
/* 1822:     */   
/* 1823:     */   public <T> T doReturningWork(final ReturningWork<T> work)
/* 1824:     */     throws HibernateException
/* 1825:     */   {
/* 1826:1746 */     WorkExecutorVisitable<T> realWork = new WorkExecutorVisitable()
/* 1827:     */     {
/* 1828:     */       public T accept(WorkExecutor<T> workExecutor, Connection connection)
/* 1829:     */         throws SQLException
/* 1830:     */       {
/* 1831:1749 */         return workExecutor.executeReturningWork(work, connection);
/* 1832:     */       }
/* 1833:1751 */     };
/* 1834:1752 */     return doWork(realWork);
/* 1835:     */   }
/* 1836:     */   
/* 1837:     */   private <T> T doWork(WorkExecutorVisitable<T> work)
/* 1838:     */     throws HibernateException
/* 1839:     */   {
/* 1840:1756 */     return this.transactionCoordinator.getJdbcCoordinator().coordinateWork(work);
/* 1841:     */   }
/* 1842:     */   
/* 1843:     */   public void afterScrollOperation() {}
/* 1844:     */   
/* 1845:     */   public TransactionCoordinator getTransactionCoordinator()
/* 1846:     */   {
/* 1847:1765 */     errorIfClosed();
/* 1848:1766 */     return this.transactionCoordinator;
/* 1849:     */   }
/* 1850:     */   
/* 1851:     */   public LoadQueryInfluencers getLoadQueryInfluencers()
/* 1852:     */   {
/* 1853:1770 */     return this.loadQueryInfluencers;
/* 1854:     */   }
/* 1855:     */   
/* 1856:     */   public Filter getEnabledFilter(String filterName)
/* 1857:     */   {
/* 1858:1779 */     checkTransactionSynchStatus();
/* 1859:1780 */     return this.loadQueryInfluencers.getEnabledFilter(filterName);
/* 1860:     */   }
/* 1861:     */   
/* 1862:     */   public Filter enableFilter(String filterName)
/* 1863:     */   {
/* 1864:1787 */     errorIfClosed();
/* 1865:1788 */     checkTransactionSynchStatus();
/* 1866:1789 */     return this.loadQueryInfluencers.enableFilter(filterName);
/* 1867:     */   }
/* 1868:     */   
/* 1869:     */   public void disableFilter(String filterName)
/* 1870:     */   {
/* 1871:1796 */     errorIfClosed();
/* 1872:1797 */     checkTransactionSynchStatus();
/* 1873:1798 */     this.loadQueryInfluencers.disableFilter(filterName);
/* 1874:     */   }
/* 1875:     */   
/* 1876:     */   public Object getFilterParameterValue(String filterParameterName)
/* 1877:     */   {
/* 1878:1805 */     errorIfClosed();
/* 1879:1806 */     checkTransactionSynchStatus();
/* 1880:1807 */     return this.loadQueryInfluencers.getFilterParameterValue(filterParameterName);
/* 1881:     */   }
/* 1882:     */   
/* 1883:     */   public Type getFilterParameterType(String filterParameterName)
/* 1884:     */   {
/* 1885:1814 */     errorIfClosed();
/* 1886:1815 */     checkTransactionSynchStatus();
/* 1887:1816 */     return this.loadQueryInfluencers.getFilterParameterType(filterParameterName);
/* 1888:     */   }
/* 1889:     */   
/* 1890:     */   public Map getEnabledFilters()
/* 1891:     */   {
/* 1892:1823 */     errorIfClosed();
/* 1893:1824 */     checkTransactionSynchStatus();
/* 1894:1825 */     return this.loadQueryInfluencers.getEnabledFilters();
/* 1895:     */   }
/* 1896:     */   
/* 1897:     */   public String getFetchProfile()
/* 1898:     */   {
/* 1899:1835 */     checkTransactionSynchStatus();
/* 1900:1836 */     return this.loadQueryInfluencers.getInternalFetchProfile();
/* 1901:     */   }
/* 1902:     */   
/* 1903:     */   public void setFetchProfile(String fetchProfile)
/* 1904:     */   {
/* 1905:1843 */     errorIfClosed();
/* 1906:1844 */     checkTransactionSynchStatus();
/* 1907:1845 */     this.loadQueryInfluencers.setInternalFetchProfile(fetchProfile);
/* 1908:     */   }
/* 1909:     */   
/* 1910:     */   public boolean isFetchProfileEnabled(String name)
/* 1911:     */     throws UnknownProfileException
/* 1912:     */   {
/* 1913:1852 */     return this.loadQueryInfluencers.isFetchProfileEnabled(name);
/* 1914:     */   }
/* 1915:     */   
/* 1916:     */   public void enableFetchProfile(String name)
/* 1917:     */     throws UnknownProfileException
/* 1918:     */   {
/* 1919:1856 */     this.loadQueryInfluencers.enableFetchProfile(name);
/* 1920:     */   }
/* 1921:     */   
/* 1922:     */   public void disableFetchProfile(String name)
/* 1923:     */     throws UnknownProfileException
/* 1924:     */   {
/* 1925:1860 */     this.loadQueryInfluencers.disableFetchProfile(name);
/* 1926:     */   }
/* 1927:     */   
/* 1928:     */   private void checkTransactionSynchStatus()
/* 1929:     */   {
/* 1930:1865 */     if (!isClosed()) {
/* 1931:1866 */       this.transactionCoordinator.pulse();
/* 1932:     */     }
/* 1933:     */   }
/* 1934:     */   
/* 1935:     */   private void readObject(ObjectInputStream ois)
/* 1936:     */     throws IOException, ClassNotFoundException
/* 1937:     */   {
/* 1938:1878 */     LOG.trace("Deserializing session");
/* 1939:     */     
/* 1940:1880 */     ois.defaultReadObject();
/* 1941:     */     
/* 1942:1882 */     this.entityNameResolver = new CoordinatingEntityNameResolver(null);
/* 1943:     */     
/* 1944:1884 */     this.connectionReleaseMode = ConnectionReleaseMode.parse((String)ois.readObject());
/* 1945:1885 */     this.autoClear = ois.readBoolean();
/* 1946:1886 */     this.autoJoinTransactions = ois.readBoolean();
/* 1947:1887 */     this.flushMode = FlushMode.valueOf((String)ois.readObject());
/* 1948:1888 */     this.cacheMode = CacheMode.valueOf((String)ois.readObject());
/* 1949:1889 */     this.flushBeforeCompletionEnabled = ois.readBoolean();
/* 1950:1890 */     this.autoCloseSessionEnabled = ois.readBoolean();
/* 1951:1891 */     this.interceptor = ((Interceptor)ois.readObject());
/* 1952:     */     
/* 1953:1893 */     this.factory = SessionFactoryImpl.deserialize(ois);
/* 1954:     */     
/* 1955:1895 */     this.transactionCoordinator = TransactionCoordinatorImpl.deserialize(ois, this);
/* 1956:     */     
/* 1957:1897 */     this.persistenceContext = StatefulPersistenceContext.deserialize(ois, this);
/* 1958:1898 */     this.actionQueue = ActionQueue.deserialize(ois, this);
/* 1959:     */     
/* 1960:1900 */     this.loadQueryInfluencers = ((LoadQueryInfluencers)ois.readObject());
/* 1961:1905 */     for (String filterName : this.loadQueryInfluencers.getEnabledFilterNames()) {
/* 1962:1906 */       ((FilterImpl)this.loadQueryInfluencers.getEnabledFilter(filterName)).afterDeserialize(this.factory);
/* 1963:     */     }
/* 1964:     */   }
/* 1965:     */   
/* 1966:     */   private void writeObject(ObjectOutputStream oos)
/* 1967:     */     throws IOException
/* 1968:     */   {
/* 1969:1917 */     if (!this.transactionCoordinator.getJdbcCoordinator().getLogicalConnection().isReadyForSerialization()) {
/* 1970:1918 */       throw new IllegalStateException("Cannot serialize a session while connected");
/* 1971:     */     }
/* 1972:1921 */     LOG.trace("Serializing session");
/* 1973:     */     
/* 1974:1923 */     oos.defaultWriteObject();
/* 1975:     */     
/* 1976:1925 */     oos.writeObject(this.connectionReleaseMode.toString());
/* 1977:1926 */     oos.writeBoolean(this.autoClear);
/* 1978:1927 */     oos.writeBoolean(this.autoJoinTransactions);
/* 1979:1928 */     oos.writeObject(this.flushMode.toString());
/* 1980:1929 */     oos.writeObject(this.cacheMode.name());
/* 1981:1930 */     oos.writeBoolean(this.flushBeforeCompletionEnabled);
/* 1982:1931 */     oos.writeBoolean(this.autoCloseSessionEnabled);
/* 1983:     */     
/* 1984:1933 */     oos.writeObject(this.interceptor);
/* 1985:     */     
/* 1986:1935 */     this.factory.serialize(oos);
/* 1987:     */     
/* 1988:1937 */     this.transactionCoordinator.serialize(oos);
/* 1989:     */     
/* 1990:1939 */     this.persistenceContext.serialize(oos);
/* 1991:1940 */     this.actionQueue.serialize(oos);
/* 1992:     */     
/* 1993:     */ 
/* 1994:1943 */     oos.writeObject(this.loadQueryInfluencers);
/* 1995:     */   }
/* 1996:     */   
/* 1997:     */   public TypeHelper getTypeHelper()
/* 1998:     */   {
/* 1999:1950 */     return getSessionFactory().getTypeHelper();
/* 2000:     */   }
/* 2001:     */   
/* 2002:     */   public LobHelper getLobHelper()
/* 2003:     */   {
/* 2004:1955 */     if (this.lobHelper == null) {
/* 2005:1956 */       this.lobHelper = new LobHelperImpl(this, null);
/* 2006:     */     }
/* 2007:1958 */     return this.lobHelper;
/* 2008:     */   }
/* 2009:     */   
/* 2010:     */   private static class LobHelperImpl
/* 2011:     */     implements LobHelper
/* 2012:     */   {
/* 2013:     */     private final SessionImpl session;
/* 2014:     */     
/* 2015:     */     private LobHelperImpl(SessionImpl session)
/* 2016:     */     {
/* 2017:1967 */       this.session = session;
/* 2018:     */     }
/* 2019:     */     
/* 2020:     */     public Blob createBlob(byte[] bytes)
/* 2021:     */     {
/* 2022:1972 */       return lobCreator().createBlob(bytes);
/* 2023:     */     }
/* 2024:     */     
/* 2025:     */     private LobCreator lobCreator()
/* 2026:     */     {
/* 2027:1976 */       return this.session.getFactory().getJdbcServices().getLobCreator(this.session);
/* 2028:     */     }
/* 2029:     */     
/* 2030:     */     public Blob createBlob(InputStream stream, long length)
/* 2031:     */     {
/* 2032:1981 */       return lobCreator().createBlob(stream, length);
/* 2033:     */     }
/* 2034:     */     
/* 2035:     */     public Clob createClob(String string)
/* 2036:     */     {
/* 2037:1986 */       return lobCreator().createClob(string);
/* 2038:     */     }
/* 2039:     */     
/* 2040:     */     public Clob createClob(Reader reader, long length)
/* 2041:     */     {
/* 2042:1991 */       return lobCreator().createClob(reader, length);
/* 2043:     */     }
/* 2044:     */     
/* 2045:     */     public NClob createNClob(String string)
/* 2046:     */     {
/* 2047:1996 */       return lobCreator().createNClob(string);
/* 2048:     */     }
/* 2049:     */     
/* 2050:     */     public NClob createNClob(Reader reader, long length)
/* 2051:     */     {
/* 2052:2001 */       return lobCreator().createNClob(reader, length);
/* 2053:     */     }
/* 2054:     */   }
/* 2055:     */   
/* 2056:     */   private static class SharedSessionBuilderImpl
/* 2057:     */     extends SessionFactoryImpl.SessionBuilderImpl
/* 2058:     */     implements SharedSessionBuilder
/* 2059:     */   {
/* 2060:     */     private final SessionImpl session;
/* 2061:     */     private boolean shareTransactionContext;
/* 2062:     */     
/* 2063:     */     private SharedSessionBuilderImpl(SessionImpl session)
/* 2064:     */     {
/* 2065:2010 */       super();
/* 2066:2011 */       this.session = session;
/* 2067:2012 */       super.tenantIdentifier(session.getTenantIdentifier());
/* 2068:     */     }
/* 2069:     */     
/* 2070:     */     public SessionBuilder tenantIdentifier(String tenantIdentifier)
/* 2071:     */     {
/* 2072:2018 */       throw new SessionException("Cannot redefine tenant identifier on child session");
/* 2073:     */     }
/* 2074:     */     
/* 2075:     */     protected TransactionCoordinatorImpl getTransactionCoordinator()
/* 2076:     */     {
/* 2077:2023 */       return this.shareTransactionContext ? this.session.transactionCoordinator : super.getTransactionCoordinator();
/* 2078:     */     }
/* 2079:     */     
/* 2080:     */     public SharedSessionBuilder interceptor()
/* 2081:     */     {
/* 2082:2028 */       return interceptor(this.session.interceptor);
/* 2083:     */     }
/* 2084:     */     
/* 2085:     */     public SharedSessionBuilder connection()
/* 2086:     */     {
/* 2087:2033 */       return connection(this.session.transactionCoordinator.getJdbcCoordinator().getLogicalConnection().getDistinctConnectionProxy());
/* 2088:     */     }
/* 2089:     */     
/* 2090:     */     public SharedSessionBuilder connectionReleaseMode()
/* 2091:     */     {
/* 2092:2043 */       return connectionReleaseMode(this.session.connectionReleaseMode);
/* 2093:     */     }
/* 2094:     */     
/* 2095:     */     public SharedSessionBuilder autoJoinTransactions()
/* 2096:     */     {
/* 2097:2048 */       return autoJoinTransactions(this.session.autoJoinTransactions);
/* 2098:     */     }
/* 2099:     */     
/* 2100:     */     public SharedSessionBuilder autoClose()
/* 2101:     */     {
/* 2102:2053 */       return autoClose(this.session.autoCloseSessionEnabled);
/* 2103:     */     }
/* 2104:     */     
/* 2105:     */     public SharedSessionBuilder flushBeforeCompletion()
/* 2106:     */     {
/* 2107:2058 */       return flushBeforeCompletion(this.session.flushBeforeCompletionEnabled);
/* 2108:     */     }
/* 2109:     */     
/* 2110:     */     public SharedSessionBuilder transactionContext()
/* 2111:     */     {
/* 2112:2063 */       this.shareTransactionContext = true;
/* 2113:2064 */       return this;
/* 2114:     */     }
/* 2115:     */     
/* 2116:     */     public SharedSessionBuilder interceptor(Interceptor interceptor)
/* 2117:     */     {
/* 2118:2069 */       return (SharedSessionBuilder)super.interceptor(interceptor);
/* 2119:     */     }
/* 2120:     */     
/* 2121:     */     public SharedSessionBuilder noInterceptor()
/* 2122:     */     {
/* 2123:2074 */       return (SharedSessionBuilder)super.noInterceptor();
/* 2124:     */     }
/* 2125:     */     
/* 2126:     */     public SharedSessionBuilder connection(Connection connection)
/* 2127:     */     {
/* 2128:2079 */       return (SharedSessionBuilder)super.connection(connection);
/* 2129:     */     }
/* 2130:     */     
/* 2131:     */     public SharedSessionBuilder connectionReleaseMode(ConnectionReleaseMode connectionReleaseMode)
/* 2132:     */     {
/* 2133:2084 */       return (SharedSessionBuilder)super.connectionReleaseMode(connectionReleaseMode);
/* 2134:     */     }
/* 2135:     */     
/* 2136:     */     public SharedSessionBuilder autoJoinTransactions(boolean autoJoinTransactions)
/* 2137:     */     {
/* 2138:2089 */       return (SharedSessionBuilder)super.autoJoinTransactions(autoJoinTransactions);
/* 2139:     */     }
/* 2140:     */     
/* 2141:     */     public SharedSessionBuilder autoClose(boolean autoClose)
/* 2142:     */     {
/* 2143:2094 */       return (SharedSessionBuilder)super.autoClose(autoClose);
/* 2144:     */     }
/* 2145:     */     
/* 2146:     */     public SharedSessionBuilder flushBeforeCompletion(boolean flushBeforeCompletion)
/* 2147:     */     {
/* 2148:2099 */       return (SharedSessionBuilder)super.flushBeforeCompletion(flushBeforeCompletion);
/* 2149:     */     }
/* 2150:     */   }
/* 2151:     */   
/* 2152:     */   private class CoordinatingEntityNameResolver
/* 2153:     */     implements EntityNameResolver
/* 2154:     */   {
/* 2155:     */     private CoordinatingEntityNameResolver() {}
/* 2156:     */     
/* 2157:     */     public String resolveEntityName(Object entity)
/* 2158:     */     {
/* 2159:2105 */       String entityName = SessionImpl.this.interceptor.getEntityName(entity);
/* 2160:2106 */       if (entityName != null) {
/* 2161:2107 */         return entityName;
/* 2162:     */       }
/* 2163:2110 */       for (EntityNameResolver resolver : SessionImpl.this.factory.iterateEntityNameResolvers())
/* 2164:     */       {
/* 2165:2111 */         entityName = resolver.resolveEntityName(entity);
/* 2166:2112 */         if (entityName != null) {
/* 2167:     */           break;
/* 2168:     */         }
/* 2169:     */       }
/* 2170:2117 */       if (entityName != null) {
/* 2171:2118 */         return entityName;
/* 2172:     */       }
/* 2173:2122 */       return entity.getClass().getName();
/* 2174:     */     }
/* 2175:     */   }
/* 2176:     */   
/* 2177:     */   private class LockRequestImpl
/* 2178:     */     implements Session.LockRequest
/* 2179:     */   {
/* 2180:     */     private final LockOptions lockOptions;
/* 2181:     */     
/* 2182:     */     private LockRequestImpl(LockOptions lo)
/* 2183:     */     {
/* 2184:2129 */       this.lockOptions = new LockOptions();
/* 2185:2130 */       LockOptions.copy(lo, this.lockOptions);
/* 2186:     */     }
/* 2187:     */     
/* 2188:     */     public LockMode getLockMode()
/* 2189:     */     {
/* 2190:2134 */       return this.lockOptions.getLockMode();
/* 2191:     */     }
/* 2192:     */     
/* 2193:     */     public Session.LockRequest setLockMode(LockMode lockMode)
/* 2194:     */     {
/* 2195:2138 */       this.lockOptions.setLockMode(lockMode);
/* 2196:2139 */       return this;
/* 2197:     */     }
/* 2198:     */     
/* 2199:     */     public int getTimeOut()
/* 2200:     */     {
/* 2201:2143 */       return this.lockOptions.getTimeOut();
/* 2202:     */     }
/* 2203:     */     
/* 2204:     */     public Session.LockRequest setTimeOut(int timeout)
/* 2205:     */     {
/* 2206:2147 */       this.lockOptions.setTimeOut(timeout);
/* 2207:2148 */       return this;
/* 2208:     */     }
/* 2209:     */     
/* 2210:     */     public boolean getScope()
/* 2211:     */     {
/* 2212:2152 */       return this.lockOptions.getScope();
/* 2213:     */     }
/* 2214:     */     
/* 2215:     */     public Session.LockRequest setScope(boolean scope)
/* 2216:     */     {
/* 2217:2156 */       this.lockOptions.setScope(scope);
/* 2218:2157 */       return this;
/* 2219:     */     }
/* 2220:     */     
/* 2221:     */     public void lock(String entityName, Object object)
/* 2222:     */       throws HibernateException
/* 2223:     */     {
/* 2224:2161 */       SessionImpl.this.fireLock(entityName, object, this.lockOptions);
/* 2225:     */     }
/* 2226:     */     
/* 2227:     */     public void lock(Object object)
/* 2228:     */       throws HibernateException
/* 2229:     */     {
/* 2230:2164 */       SessionImpl.this.fireLock(object, this.lockOptions);
/* 2231:     */     }
/* 2232:     */   }
/* 2233:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.SessionImpl
 * JD-Core Version:    0.7.0.1
 */