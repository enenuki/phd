/*    1:     */ package org.hibernate.internal;
/*    2:     */ 
/*    3:     */ import java.io.IOException;
/*    4:     */ import java.io.InvalidObjectException;
/*    5:     */ import java.io.ObjectInputStream;
/*    6:     */ import java.io.ObjectOutputStream;
/*    7:     */ import java.io.ObjectStreamException;
/*    8:     */ import java.io.Serializable;
/*    9:     */ import java.lang.reflect.Constructor;
/*   10:     */ import java.sql.Connection;
/*   11:     */ import java.util.ArrayList;
/*   12:     */ import java.util.Collections;
/*   13:     */ import java.util.HashMap;
/*   14:     */ import java.util.HashSet;
/*   15:     */ import java.util.Iterator;
/*   16:     */ import java.util.LinkedHashSet;
/*   17:     */ import java.util.Map;
/*   18:     */ import java.util.Map.Entry;
/*   19:     */ import java.util.Properties;
/*   20:     */ import java.util.Set;
/*   21:     */ import java.util.concurrent.ConcurrentHashMap;
/*   22:     */ import java.util.concurrent.ConcurrentMap;
/*   23:     */ import javax.naming.Reference;
/*   24:     */ import javax.naming.StringRefAddr;
/*   25:     */ import org.hibernate.AssertionFailure;
/*   26:     */ import org.hibernate.Cache;
/*   27:     */ import org.hibernate.ConnectionReleaseMode;
/*   28:     */ import org.hibernate.EmptyInterceptor;
/*   29:     */ import org.hibernate.EntityNameResolver;
/*   30:     */ import org.hibernate.HibernateException;
/*   31:     */ import org.hibernate.Interceptor;
/*   32:     */ import org.hibernate.MappingException;
/*   33:     */ import org.hibernate.ObjectNotFoundException;
/*   34:     */ import org.hibernate.QueryException;
/*   35:     */ import org.hibernate.Session;
/*   36:     */ import org.hibernate.SessionBuilder;
/*   37:     */ import org.hibernate.SessionFactory;
/*   38:     */ import org.hibernate.SessionFactory.SessionFactoryOptions;
/*   39:     */ import org.hibernate.SessionFactoryObserver;
/*   40:     */ import org.hibernate.StatelessSession;
/*   41:     */ import org.hibernate.StatelessSessionBuilder;
/*   42:     */ import org.hibernate.TypeHelper;
/*   43:     */ import org.hibernate.cache.internal.CacheDataDescriptionImpl;
/*   44:     */ import org.hibernate.cache.spi.CacheKey;
/*   45:     */ import org.hibernate.cache.spi.CollectionRegion;
/*   46:     */ import org.hibernate.cache.spi.EntityRegion;
/*   47:     */ import org.hibernate.cache.spi.QueryCache;
/*   48:     */ import org.hibernate.cache.spi.QueryCacheFactory;
/*   49:     */ import org.hibernate.cache.spi.QueryResultsRegion;
/*   50:     */ import org.hibernate.cache.spi.Region;
/*   51:     */ import org.hibernate.cache.spi.RegionFactory;
/*   52:     */ import org.hibernate.cache.spi.TimestampsRegion;
/*   53:     */ import org.hibernate.cache.spi.UpdateTimestampsCache;
/*   54:     */ import org.hibernate.cache.spi.access.AccessType;
/*   55:     */ import org.hibernate.cache.spi.access.CollectionRegionAccessStrategy;
/*   56:     */ import org.hibernate.cache.spi.access.EntityRegionAccessStrategy;
/*   57:     */ import org.hibernate.cache.spi.access.RegionAccessStrategy;
/*   58:     */ import org.hibernate.cfg.Configuration;
/*   59:     */ import org.hibernate.cfg.Settings;
/*   60:     */ import org.hibernate.cfg.SettingsFactory;
/*   61:     */ import org.hibernate.context.internal.JTASessionContext;
/*   62:     */ import org.hibernate.context.internal.ManagedSessionContext;
/*   63:     */ import org.hibernate.context.internal.ThreadLocalSessionContext;
/*   64:     */ import org.hibernate.context.spi.CurrentSessionContext;
/*   65:     */ import org.hibernate.dialect.Dialect;
/*   66:     */ import org.hibernate.dialect.function.SQLFunctionRegistry;
/*   67:     */ import org.hibernate.engine.ResultSetMappingDefinition;
/*   68:     */ import org.hibernate.engine.jdbc.spi.JdbcServices;
/*   69:     */ import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
/*   70:     */ import org.hibernate.engine.profile.Association;
/*   71:     */ import org.hibernate.engine.profile.Fetch.Style;
/*   72:     */ import org.hibernate.engine.query.spi.HQLQueryPlan;
/*   73:     */ import org.hibernate.engine.query.spi.QueryPlanCache;
/*   74:     */ import org.hibernate.engine.query.spi.ReturnMetadata;
/*   75:     */ import org.hibernate.engine.query.spi.sql.NativeSQLQuerySpecification;
/*   76:     */ import org.hibernate.engine.spi.FilterDefinition;
/*   77:     */ import org.hibernate.engine.spi.Mapping;
/*   78:     */ import org.hibernate.engine.spi.NamedQueryDefinition;
/*   79:     */ import org.hibernate.engine.spi.NamedSQLQueryDefinition;
/*   80:     */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   81:     */ import org.hibernate.engine.transaction.internal.TransactionCoordinatorImpl;
/*   82:     */ import org.hibernate.engine.transaction.spi.TransactionEnvironment;
/*   83:     */ import org.hibernate.engine.transaction.spi.TransactionFactory;
/*   84:     */ import org.hibernate.exception.spi.SQLExceptionConverter;
/*   85:     */ import org.hibernate.id.IdentifierGenerator;
/*   86:     */ import org.hibernate.id.UUIDGenerator;
/*   87:     */ import org.hibernate.id.factory.IdentifierGeneratorFactory;
/*   88:     */ import org.hibernate.integrator.spi.Integrator;
/*   89:     */ import org.hibernate.integrator.spi.IntegratorService;
/*   90:     */ import org.hibernate.internal.util.ReflectHelper;
/*   91:     */ import org.hibernate.internal.util.collections.CollectionHelper;
/*   92:     */ import org.hibernate.mapping.KeyValue;
/*   93:     */ import org.hibernate.mapping.PersistentClass;
/*   94:     */ import org.hibernate.mapping.RootClass;
/*   95:     */ import org.hibernate.metadata.ClassMetadata;
/*   96:     */ import org.hibernate.metadata.CollectionMetadata;
/*   97:     */ import org.hibernate.metamodel.binding.Caching;
/*   98:     */ import org.hibernate.metamodel.binding.EntityBinding;
/*   99:     */ import org.hibernate.metamodel.binding.EntityIdentifier;
/*  100:     */ import org.hibernate.metamodel.binding.HierarchyDetails;
/*  101:     */ import org.hibernate.metamodel.binding.PluralAttributeBinding;
/*  102:     */ import org.hibernate.metamodel.domain.Entity;
/*  103:     */ import org.hibernate.metamodel.domain.PluralAttribute;
/*  104:     */ import org.hibernate.metamodel.source.MetadataImplementor;
/*  105:     */ import org.hibernate.persister.collection.CollectionPersister;
/*  106:     */ import org.hibernate.persister.entity.EntityPersister;
/*  107:     */ import org.hibernate.persister.entity.Loadable;
/*  108:     */ import org.hibernate.persister.entity.Queryable;
/*  109:     */ import org.hibernate.persister.spi.PersisterFactory;
/*  110:     */ import org.hibernate.pretty.MessageHelper;
/*  111:     */ import org.hibernate.proxy.EntityNotFoundDelegate;
/*  112:     */ import org.hibernate.service.ServiceRegistry;
/*  113:     */ import org.hibernate.service.config.spi.ConfigurationService;
/*  114:     */ import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;
/*  115:     */ import org.hibernate.service.jndi.spi.JndiService;
/*  116:     */ import org.hibernate.service.jta.platform.spi.JtaPlatform;
/*  117:     */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  118:     */ import org.hibernate.service.spi.SessionFactoryServiceRegistry;
/*  119:     */ import org.hibernate.service.spi.SessionFactoryServiceRegistryFactory;
/*  120:     */ import org.hibernate.stat.Statistics;
/*  121:     */ import org.hibernate.stat.spi.StatisticsImplementor;
/*  122:     */ import org.hibernate.tool.hbm2ddl.ImportSqlCommandExtractor;
/*  123:     */ import org.hibernate.tool.hbm2ddl.SchemaExport;
/*  124:     */ import org.hibernate.tool.hbm2ddl.SchemaUpdate;
/*  125:     */ import org.hibernate.tool.hbm2ddl.SchemaValidator;
/*  126:     */ import org.hibernate.tuple.entity.EntityMetamodel;
/*  127:     */ import org.hibernate.tuple.entity.EntityTuplizer;
/*  128:     */ import org.hibernate.type.AssociationType;
/*  129:     */ import org.hibernate.type.Type;
/*  130:     */ import org.hibernate.type.TypeResolver;
/*  131:     */ import org.jboss.logging.Logger;
/*  132:     */ 
/*  133:     */ public final class SessionFactoryImpl
/*  134:     */   implements SessionFactoryImplementor
/*  135:     */ {
/*  136: 171 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, SessionFactoryImpl.class.getName());
/*  137: 172 */   private static final IdentifierGenerator UUID_GENERATOR = UUIDGenerator.buildSessionFactoryUniqueIdentifierGenerator();
/*  138:     */   private final String name;
/*  139:     */   private final String uuid;
/*  140:     */   private final transient Map entityPersisters;
/*  141:     */   private final transient Map<String, ClassMetadata> classMetadata;
/*  142:     */   private final transient Map collectionPersisters;
/*  143:     */   private final transient Map collectionMetadata;
/*  144:     */   private final transient Map<String, Set<String>> collectionRolesByEntityParticipant;
/*  145:     */   private final transient Map<String, IdentifierGenerator> identifierGenerators;
/*  146:     */   private final transient Map<String, NamedQueryDefinition> namedQueries;
/*  147:     */   private final transient Map<String, NamedSQLQueryDefinition> namedSqlQueries;
/*  148:     */   private final transient Map<String, ResultSetMappingDefinition> sqlResultSetMappings;
/*  149:     */   private final transient Map<String, FilterDefinition> filters;
/*  150:     */   private final transient Map<String, org.hibernate.engine.profile.FetchProfile> fetchProfiles;
/*  151:     */   private final transient Map<String, String> imports;
/*  152:     */   private final transient SessionFactoryServiceRegistry serviceRegistry;
/*  153:     */   private final transient JdbcServices jdbcServices;
/*  154:     */   private final transient Dialect dialect;
/*  155:     */   private final transient Settings settings;
/*  156:     */   private final transient Properties properties;
/*  157:     */   private transient SchemaExport schemaExport;
/*  158:     */   private final transient QueryCache queryCache;
/*  159:     */   private final transient UpdateTimestampsCache updateTimestampsCache;
/*  160:     */   private final transient ConcurrentMap<String, QueryCache> queryCaches;
/*  161: 198 */   private final transient ConcurrentMap<String, Region> allCacheRegions = new ConcurrentHashMap();
/*  162:     */   private final transient CurrentSessionContext currentSessionContext;
/*  163:     */   private final transient SQLFunctionRegistry sqlFunctionRegistry;
/*  164: 201 */   private final transient SessionFactoryObserverChain observer = new SessionFactoryObserverChain();
/*  165: 202 */   private final transient ConcurrentHashMap<EntityNameResolver, Object> entityNameResolvers = new ConcurrentHashMap();
/*  166:     */   private final transient QueryPlanCache queryPlanCache;
/*  167: 204 */   private final transient Cache cacheAccess = new CacheImpl(null);
/*  168: 205 */   private transient boolean isClosed = false;
/*  169:     */   private final transient TypeResolver typeResolver;
/*  170:     */   private final transient TypeHelper typeHelper;
/*  171:     */   private final transient TransactionEnvironment transactionEnvironment;
/*  172:     */   private final transient SessionFactory.SessionFactoryOptions sessionFactoryOptions;
/*  173:     */   
/*  174:     */   public SessionFactoryImpl(final Configuration cfg, Mapping mapping, ServiceRegistry serviceRegistry, Settings settings, SessionFactoryObserver observer)
/*  175:     */     throws HibernateException
/*  176:     */   {
/*  177: 218 */     LOG.debug("Building session factory");
/*  178:     */     
/*  179: 220 */     this.sessionFactoryOptions = new SessionFactory.SessionFactoryOptions()
/*  180:     */     {
/*  181:     */       private EntityNotFoundDelegate entityNotFoundDelegate;
/*  182:     */       
/*  183:     */       public Interceptor getInterceptor()
/*  184:     */       {
/*  185: 225 */         return cfg.getInterceptor();
/*  186:     */       }
/*  187:     */       
/*  188:     */       public EntityNotFoundDelegate getEntityNotFoundDelegate()
/*  189:     */       {
/*  190: 230 */         if (this.entityNotFoundDelegate == null) {
/*  191: 231 */           if (cfg.getEntityNotFoundDelegate() != null) {
/*  192: 232 */             this.entityNotFoundDelegate = cfg.getEntityNotFoundDelegate();
/*  193:     */           } else {
/*  194: 235 */             this.entityNotFoundDelegate = new EntityNotFoundDelegate()
/*  195:     */             {
/*  196:     */               public void handleEntityNotFound(String entityName, Serializable id)
/*  197:     */               {
/*  198: 237 */                 throw new ObjectNotFoundException(id, entityName);
/*  199:     */               }
/*  200:     */             };
/*  201:     */           }
/*  202:     */         }
/*  203: 242 */         return this.entityNotFoundDelegate;
/*  204:     */       }
/*  205: 245 */     };
/*  206: 246 */     this.settings = settings;
/*  207:     */     
/*  208: 248 */     this.properties = new Properties();
/*  209: 249 */     this.properties.putAll(cfg.getProperties());
/*  210:     */     
/*  211: 251 */     this.serviceRegistry = ((SessionFactoryServiceRegistryFactory)serviceRegistry.getService(SessionFactoryServiceRegistryFactory.class)).buildServiceRegistry(this, cfg);
/*  212:     */     
/*  213:     */ 
/*  214:     */ 
/*  215: 255 */     this.jdbcServices = ((JdbcServices)this.serviceRegistry.getService(JdbcServices.class));
/*  216: 256 */     this.dialect = this.jdbcServices.getDialect();
/*  217: 257 */     this.sqlFunctionRegistry = new SQLFunctionRegistry(getDialect(), cfg.getSqlFunctions());
/*  218: 258 */     if (observer != null) {
/*  219: 259 */       this.observer.addObserver(observer);
/*  220:     */     }
/*  221: 262 */     this.typeResolver = cfg.getTypeResolver().scope(this);
/*  222: 263 */     this.typeHelper = new TypeLocatorImpl(this.typeResolver);
/*  223:     */     
/*  224: 265 */     this.filters = new HashMap();
/*  225: 266 */     this.filters.putAll(cfg.getFilterDefinitions());
/*  226:     */     
/*  227: 268 */     LOG.debugf("Session factory constructed with filter configurations : %s", this.filters);
/*  228: 269 */     LOG.debugf("Instantiating session factory with properties: %s", this.properties);
/*  229:     */     
/*  230:     */ 
/*  231: 272 */     settings.getRegionFactory().start(settings, this.properties);
/*  232: 273 */     this.queryPlanCache = new QueryPlanCache(this);
/*  233:     */     
/*  234:     */ 
/*  235:     */ 
/*  236:     */ 
/*  237:     */ 
/*  238:     */ 
/*  239:     */ 
/*  240:     */ 
/*  241:     */ 
/*  242:     */ 
/*  243:     */ 
/*  244:     */ 
/*  245:     */ 
/*  246:     */ 
/*  247:     */ 
/*  248:     */ 
/*  249:     */ 
/*  250:     */ 
/*  251: 292 */     SessionFactoryObserver integratorObserver = new SessionFactoryObserver()
/*  252:     */     {
/*  253: 278 */       private ArrayList<Integrator> integrators = new ArrayList();
/*  254:     */       
/*  255:     */       public void sessionFactoryCreated(SessionFactory factory) {}
/*  256:     */       
/*  257:     */       public void sessionFactoryClosed(SessionFactory factory)
/*  258:     */       {
/*  259: 286 */         for (Integrator integrator : this.integrators) {
/*  260: 287 */           integrator.disintegrate(SessionFactoryImpl.this, SessionFactoryImpl.this.serviceRegistry);
/*  261:     */         }
/*  262:     */       }
/*  263: 292 */     };
/*  264: 293 */     this.observer.addObserver(integratorObserver);
/*  265: 294 */     for (Integrator integrator : ((IntegratorService)serviceRegistry.getService(IntegratorService.class)).getIntegrators())
/*  266:     */     {
/*  267: 295 */       integrator.integrate(cfg, this, this.serviceRegistry);
/*  268: 296 */       integratorObserver.integrators.add(integrator);
/*  269:     */     }
/*  270: 301 */     this.identifierGenerators = new HashMap();
/*  271: 302 */     Iterator classes = cfg.getClassMappings();
/*  272: 303 */     while (classes.hasNext())
/*  273:     */     {
/*  274: 304 */       PersistentClass model = (PersistentClass)classes.next();
/*  275: 305 */       if (!model.isInherited())
/*  276:     */       {
/*  277: 306 */         IdentifierGenerator generator = model.getIdentifier().createIdentifierGenerator(cfg.getIdentifierGeneratorFactory(), getDialect(), settings.getDefaultCatalogName(), settings.getDefaultSchemaName(), (RootClass)model);
/*  278:     */         
/*  279:     */ 
/*  280:     */ 
/*  281:     */ 
/*  282:     */ 
/*  283:     */ 
/*  284: 313 */         this.identifierGenerators.put(model.getEntityName(), generator);
/*  285:     */       }
/*  286:     */     }
/*  287: 322 */     String cacheRegionPrefix = settings.getCacheRegionPrefix() + ".";
/*  288:     */     
/*  289: 324 */     this.entityPersisters = new HashMap();
/*  290: 325 */     Map entityAccessStrategies = new HashMap();
/*  291: 326 */     Map<String, ClassMetadata> classMeta = new HashMap();
/*  292: 327 */     classes = cfg.getClassMappings();
/*  293: 328 */     while (classes.hasNext())
/*  294:     */     {
/*  295: 329 */       PersistentClass model = (PersistentClass)classes.next();
/*  296: 330 */       model.prepareTemporaryTables(mapping, getDialect());
/*  297: 331 */       String cacheRegionName = cacheRegionPrefix + model.getRootClass().getCacheRegionName();
/*  298:     */       
/*  299: 333 */       EntityRegionAccessStrategy accessStrategy = (EntityRegionAccessStrategy)entityAccessStrategies.get(cacheRegionName);
/*  300: 334 */       if ((accessStrategy == null) && (settings.isSecondLevelCacheEnabled()))
/*  301:     */       {
/*  302: 335 */         AccessType accessType = AccessType.fromExternalName(model.getCacheConcurrencyStrategy());
/*  303: 336 */         if (accessType != null)
/*  304:     */         {
/*  305: 337 */           if (LOG.isTraceEnabled()) {
/*  306: 338 */             LOG.tracev("Building cache for entity data [{0}]", model.getEntityName());
/*  307:     */           }
/*  308: 340 */           EntityRegion entityRegion = settings.getRegionFactory().buildEntityRegion(cacheRegionName, this.properties, CacheDataDescriptionImpl.decode(model));
/*  309: 341 */           accessStrategy = entityRegion.buildAccessStrategy(accessType);
/*  310: 342 */           entityAccessStrategies.put(cacheRegionName, accessStrategy);
/*  311: 343 */           this.allCacheRegions.put(cacheRegionName, entityRegion);
/*  312:     */         }
/*  313:     */       }
/*  314: 346 */       EntityPersister cp = ((PersisterFactory)serviceRegistry.getService(PersisterFactory.class)).createEntityPersister(model, accessStrategy, this, mapping);
/*  315:     */       
/*  316:     */ 
/*  317:     */ 
/*  318:     */ 
/*  319:     */ 
/*  320: 352 */       this.entityPersisters.put(model.getEntityName(), cp);
/*  321: 353 */       classMeta.put(model.getEntityName(), cp.getClassMetadata());
/*  322:     */     }
/*  323: 355 */     this.classMetadata = Collections.unmodifiableMap(classMeta);
/*  324:     */     
/*  325: 357 */     Map<String, Set<String>> tmpEntityToCollectionRoleMap = new HashMap();
/*  326: 358 */     this.collectionPersisters = new HashMap();
/*  327: 359 */     Iterator collections = cfg.getCollectionMappings();
/*  328: 360 */     while (collections.hasNext())
/*  329:     */     {
/*  330: 361 */       org.hibernate.mapping.Collection model = (org.hibernate.mapping.Collection)collections.next();
/*  331: 362 */       String cacheRegionName = cacheRegionPrefix + model.getCacheRegionName();
/*  332: 363 */       AccessType accessType = AccessType.fromExternalName(model.getCacheConcurrencyStrategy());
/*  333: 364 */       CollectionRegionAccessStrategy accessStrategy = null;
/*  334: 365 */       if ((accessType != null) && (settings.isSecondLevelCacheEnabled()))
/*  335:     */       {
/*  336: 366 */         if (LOG.isTraceEnabled()) {
/*  337: 367 */           LOG.tracev("Building cache for collection data [{0}]", model.getRole());
/*  338:     */         }
/*  339: 369 */         CollectionRegion collectionRegion = settings.getRegionFactory().buildCollectionRegion(cacheRegionName, this.properties, CacheDataDescriptionImpl.decode(model));
/*  340:     */         
/*  341: 371 */         accessStrategy = collectionRegion.buildAccessStrategy(accessType);
/*  342: 372 */         entityAccessStrategies.put(cacheRegionName, accessStrategy);
/*  343: 373 */         this.allCacheRegions.put(cacheRegionName, collectionRegion);
/*  344:     */       }
/*  345: 375 */       CollectionPersister persister = ((PersisterFactory)serviceRegistry.getService(PersisterFactory.class)).createCollectionPersister(cfg, model, accessStrategy, this);
/*  346:     */       
/*  347:     */ 
/*  348:     */ 
/*  349:     */ 
/*  350:     */ 
/*  351: 381 */       this.collectionPersisters.put(model.getRole(), persister.getCollectionMetadata());
/*  352: 382 */       Type indexType = persister.getIndexType();
/*  353: 383 */       if ((indexType != null) && (indexType.isAssociationType()) && (!indexType.isAnyType()))
/*  354:     */       {
/*  355: 384 */         String entityName = ((AssociationType)indexType).getAssociatedEntityName(this);
/*  356: 385 */         Set roles = (Set)tmpEntityToCollectionRoleMap.get(entityName);
/*  357: 386 */         if (roles == null)
/*  358:     */         {
/*  359: 387 */           roles = new HashSet();
/*  360: 388 */           tmpEntityToCollectionRoleMap.put(entityName, roles);
/*  361:     */         }
/*  362: 390 */         roles.add(persister.getRole());
/*  363:     */       }
/*  364: 392 */       Type elementType = persister.getElementType();
/*  365: 393 */       if ((elementType.isAssociationType()) && (!elementType.isAnyType()))
/*  366:     */       {
/*  367: 394 */         String entityName = ((AssociationType)elementType).getAssociatedEntityName(this);
/*  368: 395 */         Set roles = (Set)tmpEntityToCollectionRoleMap.get(entityName);
/*  369: 396 */         if (roles == null)
/*  370:     */         {
/*  371: 397 */           roles = new HashSet();
/*  372: 398 */           tmpEntityToCollectionRoleMap.put(entityName, roles);
/*  373:     */         }
/*  374: 400 */         roles.add(persister.getRole());
/*  375:     */       }
/*  376:     */     }
/*  377: 403 */     this.collectionMetadata = Collections.unmodifiableMap(this.collectionPersisters);
/*  378: 404 */     Iterator itr = tmpEntityToCollectionRoleMap.entrySet().iterator();
/*  379: 405 */     while (itr.hasNext())
/*  380:     */     {
/*  381: 406 */       Map.Entry entry = (Map.Entry)itr.next();
/*  382: 407 */       entry.setValue(Collections.unmodifiableSet((Set)entry.getValue()));
/*  383:     */     }
/*  384: 409 */     this.collectionRolesByEntityParticipant = Collections.unmodifiableMap(tmpEntityToCollectionRoleMap);
/*  385:     */     
/*  386:     */ 
/*  387: 412 */     this.namedQueries = new HashMap(cfg.getNamedQueries());
/*  388: 413 */     this.namedSqlQueries = new HashMap(cfg.getNamedSQLQueries());
/*  389: 414 */     this.sqlResultSetMappings = new HashMap(cfg.getSqlResultSetMappings());
/*  390: 415 */     this.imports = new HashMap(cfg.getImports());
/*  391:     */     
/*  392:     */ 
/*  393: 418 */     Iterator iter = this.entityPersisters.values().iterator();
/*  394: 419 */     while (iter.hasNext())
/*  395:     */     {
/*  396: 420 */       EntityPersister persister = (EntityPersister)iter.next();
/*  397: 421 */       persister.postInstantiate();
/*  398: 422 */       registerEntityNameResolvers(persister);
/*  399:     */     }
/*  400: 425 */     iter = this.collectionPersisters.values().iterator();
/*  401: 426 */     while (iter.hasNext())
/*  402:     */     {
/*  403: 427 */       CollectionPersister persister = (CollectionPersister)iter.next();
/*  404: 428 */       persister.postInstantiate();
/*  405:     */     }
/*  406: 433 */     this.name = settings.getSessionFactoryName();
/*  407:     */     try
/*  408:     */     {
/*  409: 435 */       this.uuid = ((String)UUID_GENERATOR.generate(null, null));
/*  410:     */     }
/*  411:     */     catch (Exception e)
/*  412:     */     {
/*  413: 438 */       throw new AssertionFailure("Could not generate UUID");
/*  414:     */     }
/*  415: 440 */     SessionFactoryRegistry.INSTANCE.addSessionFactory(this.uuid, this.name, this, (JndiService)serviceRegistry.getService(JndiService.class));
/*  416:     */     
/*  417: 442 */     LOG.debug("Instantiated session factory");
/*  418: 444 */     if (settings.isAutoCreateSchema()) {
/*  419: 445 */       new SchemaExport(serviceRegistry, cfg).setImportSqlCommandExtractor((ImportSqlCommandExtractor)serviceRegistry.getService(ImportSqlCommandExtractor.class)).create(false, true);
/*  420:     */     }
/*  421: 449 */     if (settings.isAutoUpdateSchema()) {
/*  422: 450 */       new SchemaUpdate(serviceRegistry, cfg).execute(false, true);
/*  423:     */     }
/*  424: 452 */     if (settings.isAutoValidateSchema()) {
/*  425: 453 */       new SchemaValidator(serviceRegistry, cfg).validate();
/*  426:     */     }
/*  427: 455 */     if (settings.isAutoDropSchema()) {
/*  428: 456 */       this.schemaExport = new SchemaExport(serviceRegistry, cfg).setImportSqlCommandExtractor((ImportSqlCommandExtractor)serviceRegistry.getService(ImportSqlCommandExtractor.class));
/*  429:     */     }
/*  430: 460 */     this.currentSessionContext = buildCurrentSessionContext();
/*  431: 462 */     if (settings.isQueryCacheEnabled())
/*  432:     */     {
/*  433: 463 */       this.updateTimestampsCache = new UpdateTimestampsCache(settings, this.properties, this);
/*  434: 464 */       this.queryCache = settings.getQueryCacheFactory().getQueryCache(null, this.updateTimestampsCache, settings, this.properties);
/*  435:     */       
/*  436: 466 */       this.queryCaches = new ConcurrentHashMap();
/*  437: 467 */       this.allCacheRegions.put(this.updateTimestampsCache.getRegion().getName(), this.updateTimestampsCache.getRegion());
/*  438: 468 */       this.allCacheRegions.put(this.queryCache.getRegion().getName(), this.queryCache.getRegion());
/*  439:     */     }
/*  440:     */     else
/*  441:     */     {
/*  442: 471 */       this.updateTimestampsCache = null;
/*  443: 472 */       this.queryCache = null;
/*  444: 473 */       this.queryCaches = null;
/*  445:     */     }
/*  446: 477 */     if (settings.isNamedQueryStartupCheckingEnabled())
/*  447:     */     {
/*  448: 478 */       Map errors = checkNamedQueries();
/*  449: 479 */       if (!errors.isEmpty())
/*  450:     */       {
/*  451: 480 */         Set keys = errors.keySet();
/*  452: 481 */         StringBuffer failingQueries = new StringBuffer("Errors in named queries: ");
/*  453: 482 */         for (Iterator iterator = keys.iterator(); iterator.hasNext();)
/*  454:     */         {
/*  455: 483 */           String queryName = (String)iterator.next();
/*  456: 484 */           HibernateException e = (HibernateException)errors.get(queryName);
/*  457: 485 */           failingQueries.append(queryName);
/*  458: 486 */           if (iterator.hasNext()) {
/*  459: 486 */             failingQueries.append(", ");
/*  460:     */           }
/*  461: 487 */           LOG.namedQueryError(queryName, e);
/*  462:     */         }
/*  463: 489 */         throw new HibernateException(failingQueries.toString());
/*  464:     */       }
/*  465:     */     }
/*  466: 494 */     this.fetchProfiles = new HashMap();
/*  467: 495 */     itr = cfg.iterateFetchProfiles();
/*  468: 496 */     while (itr.hasNext())
/*  469:     */     {
/*  470: 497 */       org.hibernate.mapping.FetchProfile mappingProfile = (org.hibernate.mapping.FetchProfile)itr.next();
/*  471:     */       
/*  472: 499 */       org.hibernate.engine.profile.FetchProfile fetchProfile = new org.hibernate.engine.profile.FetchProfile(mappingProfile.getName());
/*  473: 500 */       Iterator fetches = mappingProfile.getFetches().iterator();
/*  474: 501 */       while (fetches.hasNext())
/*  475:     */       {
/*  476: 502 */         org.hibernate.mapping.FetchProfile.Fetch mappingFetch = (org.hibernate.mapping.FetchProfile.Fetch)fetches.next();
/*  477:     */         
/*  478:     */ 
/*  479: 505 */         String entityName = getImportedClassName(mappingFetch.getEntity());
/*  480: 506 */         EntityPersister owner = (EntityPersister)(entityName == null ? null : this.entityPersisters.get(entityName));
/*  481: 507 */         if (owner == null) {
/*  482: 508 */           throw new HibernateException("Unable to resolve entity reference [" + mappingFetch.getEntity() + "] in fetch profile [" + fetchProfile.getName() + "]");
/*  483:     */         }
/*  484: 515 */         Type associationType = owner.getPropertyType(mappingFetch.getAssociation());
/*  485: 516 */         if ((associationType == null) || (!associationType.isAssociationType())) {
/*  486: 517 */           throw new HibernateException("Fetch profile [" + fetchProfile.getName() + "] specified an invalid association");
/*  487:     */         }
/*  488: 521 */         Fetch.Style fetchStyle = Fetch.Style.parse(mappingFetch.getStyle());
/*  489:     */         
/*  490:     */ 
/*  491: 524 */         fetchProfile.addFetch(new Association(owner, mappingFetch.getAssociation()), fetchStyle);
/*  492: 525 */         ((Loadable)owner).registerAffectingFetchProfile(fetchProfile.getName());
/*  493:     */       }
/*  494: 527 */       this.fetchProfiles.put(fetchProfile.getName(), fetchProfile);
/*  495:     */     }
/*  496: 530 */     this.transactionEnvironment = new TransactionEnvironmentImpl(this);
/*  497: 531 */     this.observer.sessionFactoryCreated(this);
/*  498:     */   }
/*  499:     */   
/*  500:     */   public SessionFactoryImpl(MetadataImplementor metadata, SessionFactory.SessionFactoryOptions sessionFactoryOptions, SessionFactoryObserver observer)
/*  501:     */     throws HibernateException
/*  502:     */   {
/*  503: 538 */     LOG.debug("Building session factory");
/*  504:     */     
/*  505: 540 */     this.sessionFactoryOptions = sessionFactoryOptions;
/*  506:     */     
/*  507: 542 */     this.properties = createPropertiesFromMap(((ConfigurationService)metadata.getServiceRegistry().getService(ConfigurationService.class)).getSettings());
/*  508:     */     
/*  509:     */ 
/*  510:     */ 
/*  511:     */ 
/*  512: 547 */     this.settings = new SettingsFactory().buildSettings(this.properties, metadata.getServiceRegistry());
/*  513:     */     
/*  514:     */ 
/*  515:     */ 
/*  516:     */ 
/*  517: 552 */     this.serviceRegistry = ((SessionFactoryServiceRegistryFactory)metadata.getServiceRegistry().getService(SessionFactoryServiceRegistryFactory.class)).buildServiceRegistry(this, metadata);
/*  518:     */     
/*  519:     */ 
/*  520:     */ 
/*  521:     */ 
/*  522: 557 */     this.jdbcServices = ((JdbcServices)this.serviceRegistry.getService(JdbcServices.class));
/*  523: 558 */     this.dialect = this.jdbcServices.getDialect();
/*  524:     */     
/*  525:     */ 
/*  526:     */ 
/*  527: 562 */     this.sqlFunctionRegistry = new SQLFunctionRegistry(this.dialect, new HashMap());
/*  528: 567 */     if (observer != null) {
/*  529: 568 */       this.observer.addObserver(observer);
/*  530:     */     }
/*  531: 571 */     this.typeResolver = metadata.getTypeResolver().scope(this);
/*  532: 572 */     this.typeHelper = new TypeLocatorImpl(this.typeResolver);
/*  533:     */     
/*  534: 574 */     this.filters = new HashMap();
/*  535: 575 */     for (FilterDefinition filterDefinition : metadata.getFilterDefinitions()) {
/*  536: 576 */       this.filters.put(filterDefinition.getFilterName(), filterDefinition);
/*  537:     */     }
/*  538: 579 */     LOG.debugf("Session factory constructed with filter configurations : %s", this.filters);
/*  539: 580 */     LOG.debugf("Instantiating session factory with properties: %s", this.properties);
/*  540:     */     
/*  541:     */ 
/*  542: 583 */     this.settings.getRegionFactory().start(this.settings, this.properties);
/*  543: 584 */     this.queryPlanCache = new QueryPlanCache(this);
/*  544:     */     
/*  545:     */ 
/*  546:     */ 
/*  547:     */ 
/*  548:     */ 
/*  549:     */ 
/*  550:     */ 
/*  551:     */ 
/*  552:     */ 
/*  553:     */ 
/*  554:     */ 
/*  555:     */ 
/*  556:     */ 
/*  557:     */ 
/*  558:     */ 
/*  559:     */ 
/*  560: 601 */     SessionFactoryObserver integratorObserver = new SessionFactoryObserver()
/*  561:     */     {
/*  562: 587 */       private ArrayList<Integrator> integrators = new ArrayList();
/*  563:     */       
/*  564:     */       public void sessionFactoryCreated(SessionFactory factory) {}
/*  565:     */       
/*  566:     */       public void sessionFactoryClosed(SessionFactory factory)
/*  567:     */       {
/*  568: 595 */         for (Integrator integrator : this.integrators) {
/*  569: 596 */           integrator.disintegrate(SessionFactoryImpl.this, SessionFactoryImpl.this.serviceRegistry);
/*  570:     */         }
/*  571:     */       }
/*  572: 601 */     };
/*  573: 602 */     this.observer.addObserver(integratorObserver);
/*  574: 603 */     for (Integrator integrator : ((IntegratorService)this.serviceRegistry.getService(IntegratorService.class)).getIntegrators())
/*  575:     */     {
/*  576: 604 */       integrator.integrate(metadata, this, this.serviceRegistry);
/*  577: 605 */       integratorObserver.integrators.add(integrator);
/*  578:     */     }
/*  579: 611 */     this.identifierGenerators = new HashMap();
/*  580: 612 */     for (EntityBinding entityBinding : metadata.getEntityBindings()) {
/*  581: 613 */       if (entityBinding.isRoot()) {
/*  582: 614 */         this.identifierGenerators.put(entityBinding.getEntity().getName(), entityBinding.getHierarchyDetails().getEntityIdentifier().getIdentifierGenerator());
/*  583:     */       }
/*  584:     */     }
/*  585: 625 */     StringBuilder stringBuilder = new StringBuilder();
/*  586: 626 */     if (this.settings.getCacheRegionPrefix() != null) {
/*  587: 627 */       stringBuilder.append(this.settings.getCacheRegionPrefix()).append('.');
/*  588:     */     }
/*  589: 631 */     String cacheRegionPrefix = stringBuilder.toString();
/*  590:     */     
/*  591: 633 */     this.entityPersisters = new HashMap();
/*  592: 634 */     Map<String, RegionAccessStrategy> entityAccessStrategies = new HashMap();
/*  593: 635 */     Map<String, ClassMetadata> classMeta = new HashMap();
/*  594: 636 */     for (EntityBinding model : metadata.getEntityBindings())
/*  595:     */     {
/*  596: 640 */       EntityBinding rootEntityBinding = metadata.getRootEntityBinding(model.getEntity().getName());
/*  597: 641 */       EntityRegionAccessStrategy accessStrategy = null;
/*  598: 642 */       if ((this.settings.isSecondLevelCacheEnabled()) && (rootEntityBinding.getHierarchyDetails().getCaching() != null) && (model.getHierarchyDetails().getCaching() != null) && (model.getHierarchyDetails().getCaching().getAccessType() != null))
/*  599:     */       {
/*  600: 646 */         String cacheRegionName = cacheRegionPrefix + rootEntityBinding.getHierarchyDetails().getCaching().getRegion();
/*  601: 647 */         accessStrategy = (EntityRegionAccessStrategy)EntityRegionAccessStrategy.class.cast(entityAccessStrategies.get(cacheRegionName));
/*  602: 648 */         if (accessStrategy == null)
/*  603:     */         {
/*  604: 649 */           AccessType accessType = model.getHierarchyDetails().getCaching().getAccessType();
/*  605: 650 */           if (LOG.isTraceEnabled()) {
/*  606: 651 */             LOG.tracev("Building cache for entity data [{0}]", model.getEntity().getName());
/*  607:     */           }
/*  608: 653 */           EntityRegion entityRegion = this.settings.getRegionFactory().buildEntityRegion(cacheRegionName, this.properties, CacheDataDescriptionImpl.decode(model));
/*  609:     */           
/*  610:     */ 
/*  611: 656 */           accessStrategy = entityRegion.buildAccessStrategy(accessType);
/*  612: 657 */           entityAccessStrategies.put(cacheRegionName, accessStrategy);
/*  613: 658 */           this.allCacheRegions.put(cacheRegionName, entityRegion);
/*  614:     */         }
/*  615:     */       }
/*  616: 661 */       EntityPersister cp = ((PersisterFactory)this.serviceRegistry.getService(PersisterFactory.class)).createEntityPersister(model, accessStrategy, this, metadata);
/*  617:     */       
/*  618:     */ 
/*  619: 664 */       this.entityPersisters.put(model.getEntity().getName(), cp);
/*  620: 665 */       classMeta.put(model.getEntity().getName(), cp.getClassMetadata());
/*  621:     */     }
/*  622: 667 */     this.classMetadata = Collections.unmodifiableMap(classMeta);
/*  623:     */     
/*  624: 669 */     Map<String, Set<String>> tmpEntityToCollectionRoleMap = new HashMap();
/*  625: 670 */     this.collectionPersisters = new HashMap();
/*  626: 671 */     for (PluralAttributeBinding model : metadata.getCollectionBindings())
/*  627:     */     {
/*  628: 672 */       if (model.getAttribute() == null) {
/*  629: 673 */         throw new IllegalStateException("No attribute defined for a AbstractPluralAttributeBinding: " + model);
/*  630:     */       }
/*  631: 675 */       if (model.getAttribute().isSingular()) {
/*  632: 676 */         throw new IllegalStateException("AbstractPluralAttributeBinding has a Singular attribute defined: " + model.getAttribute().getName());
/*  633:     */       }
/*  634: 680 */       String cacheRegionName = cacheRegionPrefix + model.getCaching().getRegion();
/*  635: 681 */       AccessType accessType = model.getCaching().getAccessType();
/*  636: 682 */       CollectionRegionAccessStrategy accessStrategy = null;
/*  637: 683 */       if ((accessType != null) && (this.settings.isSecondLevelCacheEnabled()))
/*  638:     */       {
/*  639: 684 */         if (LOG.isTraceEnabled()) {
/*  640: 685 */           LOG.tracev("Building cache for collection data [{0}]", model.getAttribute().getRole());
/*  641:     */         }
/*  642: 687 */         CollectionRegion collectionRegion = this.settings.getRegionFactory().buildCollectionRegion(cacheRegionName, this.properties, CacheDataDescriptionImpl.decode(model));
/*  643:     */         
/*  644:     */ 
/*  645: 690 */         accessStrategy = collectionRegion.buildAccessStrategy(accessType);
/*  646: 691 */         entityAccessStrategies.put(cacheRegionName, accessStrategy);
/*  647: 692 */         this.allCacheRegions.put(cacheRegionName, collectionRegion);
/*  648:     */       }
/*  649: 694 */       CollectionPersister persister = ((PersisterFactory)this.serviceRegistry.getService(PersisterFactory.class)).createCollectionPersister(metadata, model, accessStrategy, this);
/*  650:     */       
/*  651:     */ 
/*  652: 697 */       this.collectionPersisters.put(model.getAttribute().getRole(), persister.getCollectionMetadata());
/*  653: 698 */       Type indexType = persister.getIndexType();
/*  654: 699 */       if ((indexType != null) && (indexType.isAssociationType()) && (!indexType.isAnyType()))
/*  655:     */       {
/*  656: 700 */         String entityName = ((AssociationType)indexType).getAssociatedEntityName(this);
/*  657: 701 */         Set roles = (Set)tmpEntityToCollectionRoleMap.get(entityName);
/*  658: 702 */         if (roles == null)
/*  659:     */         {
/*  660: 703 */           roles = new HashSet();
/*  661: 704 */           tmpEntityToCollectionRoleMap.put(entityName, roles);
/*  662:     */         }
/*  663: 706 */         roles.add(persister.getRole());
/*  664:     */       }
/*  665: 708 */       Type elementType = persister.getElementType();
/*  666: 709 */       if ((elementType.isAssociationType()) && (!elementType.isAnyType()))
/*  667:     */       {
/*  668: 710 */         String entityName = ((AssociationType)elementType).getAssociatedEntityName(this);
/*  669: 711 */         Set roles = (Set)tmpEntityToCollectionRoleMap.get(entityName);
/*  670: 712 */         if (roles == null)
/*  671:     */         {
/*  672: 713 */           roles = new HashSet();
/*  673: 714 */           tmpEntityToCollectionRoleMap.put(entityName, roles);
/*  674:     */         }
/*  675: 716 */         roles.add(persister.getRole());
/*  676:     */       }
/*  677:     */     }
/*  678: 719 */     this.collectionMetadata = Collections.unmodifiableMap(this.collectionPersisters);
/*  679: 720 */     Iterator itr = tmpEntityToCollectionRoleMap.entrySet().iterator();
/*  680: 721 */     while (itr.hasNext())
/*  681:     */     {
/*  682: 722 */       Map.Entry entry = (Map.Entry)itr.next();
/*  683: 723 */       entry.setValue(Collections.unmodifiableSet((Set)entry.getValue()));
/*  684:     */     }
/*  685: 725 */     this.collectionRolesByEntityParticipant = Collections.unmodifiableMap(tmpEntityToCollectionRoleMap);
/*  686:     */     
/*  687:     */ 
/*  688: 728 */     this.namedQueries = new HashMap();
/*  689: 729 */     for (NamedQueryDefinition namedQueryDefinition : metadata.getNamedQueryDefinitions()) {
/*  690: 730 */       this.namedQueries.put(namedQueryDefinition.getName(), namedQueryDefinition);
/*  691:     */     }
/*  692: 732 */     this.namedSqlQueries = new HashMap();
/*  693: 733 */     for (NamedSQLQueryDefinition namedNativeQueryDefinition : metadata.getNamedNativeQueryDefinitions()) {
/*  694: 734 */       this.namedSqlQueries.put(namedNativeQueryDefinition.getName(), namedNativeQueryDefinition);
/*  695:     */     }
/*  696: 736 */     this.sqlResultSetMappings = new HashMap();
/*  697: 737 */     for (ResultSetMappingDefinition resultSetMappingDefinition : metadata.getResultSetMappingDefinitions()) {
/*  698: 738 */       this.sqlResultSetMappings.put(resultSetMappingDefinition.getName(), resultSetMappingDefinition);
/*  699:     */     }
/*  700: 740 */     this.imports = new HashMap();
/*  701: 741 */     for (Map.Entry<String, String> importEntry : metadata.getImports()) {
/*  702: 742 */       this.imports.put(importEntry.getKey(), importEntry.getValue());
/*  703:     */     }
/*  704: 746 */     Iterator iter = this.entityPersisters.values().iterator();
/*  705: 747 */     while (iter.hasNext())
/*  706:     */     {
/*  707: 748 */       EntityPersister persister = (EntityPersister)iter.next();
/*  708: 749 */       persister.postInstantiate();
/*  709: 750 */       registerEntityNameResolvers(persister);
/*  710:     */     }
/*  711: 753 */     iter = this.collectionPersisters.values().iterator();
/*  712: 754 */     while (iter.hasNext())
/*  713:     */     {
/*  714: 755 */       CollectionPersister persister = (CollectionPersister)iter.next();
/*  715: 756 */       persister.postInstantiate();
/*  716:     */     }
/*  717: 761 */     this.name = this.settings.getSessionFactoryName();
/*  718:     */     try
/*  719:     */     {
/*  720: 763 */       this.uuid = ((String)UUID_GENERATOR.generate(null, null));
/*  721:     */     }
/*  722:     */     catch (Exception e)
/*  723:     */     {
/*  724: 766 */       throw new AssertionFailure("Could not generate UUID");
/*  725:     */     }
/*  726: 768 */     SessionFactoryRegistry.INSTANCE.addSessionFactory(this.uuid, this.name, this, (JndiService)this.serviceRegistry.getService(JndiService.class));
/*  727:     */     
/*  728: 770 */     LOG.debug("Instantiated session factory");
/*  729: 772 */     if (this.settings.isAutoCreateSchema()) {
/*  730: 773 */       new SchemaExport(metadata).setImportSqlCommandExtractor((ImportSqlCommandExtractor)this.serviceRegistry.getService(ImportSqlCommandExtractor.class)).create(false, true);
/*  731:     */     }
/*  732: 785 */     if (this.settings.isAutoDropSchema()) {
/*  733: 786 */       this.schemaExport = new SchemaExport(metadata).setImportSqlCommandExtractor((ImportSqlCommandExtractor)this.serviceRegistry.getService(ImportSqlCommandExtractor.class));
/*  734:     */     }
/*  735: 790 */     this.currentSessionContext = buildCurrentSessionContext();
/*  736: 792 */     if (this.settings.isQueryCacheEnabled())
/*  737:     */     {
/*  738: 793 */       this.updateTimestampsCache = new UpdateTimestampsCache(this.settings, this.properties, this);
/*  739: 794 */       this.queryCache = this.settings.getQueryCacheFactory().getQueryCache(null, this.updateTimestampsCache, this.settings, this.properties);
/*  740:     */       
/*  741: 796 */       this.queryCaches = new ConcurrentHashMap();
/*  742: 797 */       this.allCacheRegions.put(this.updateTimestampsCache.getRegion().getName(), this.updateTimestampsCache.getRegion());
/*  743: 798 */       this.allCacheRegions.put(this.queryCache.getRegion().getName(), this.queryCache.getRegion());
/*  744:     */     }
/*  745:     */     else
/*  746:     */     {
/*  747: 801 */       this.updateTimestampsCache = null;
/*  748: 802 */       this.queryCache = null;
/*  749: 803 */       this.queryCaches = null;
/*  750:     */     }
/*  751: 807 */     if (this.settings.isNamedQueryStartupCheckingEnabled())
/*  752:     */     {
/*  753: 808 */       Map errors = checkNamedQueries();
/*  754: 809 */       if (!errors.isEmpty())
/*  755:     */       {
/*  756: 810 */         Set keys = errors.keySet();
/*  757: 811 */         StringBuffer failingQueries = new StringBuffer("Errors in named queries: ");
/*  758: 812 */         for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();)
/*  759:     */         {
/*  760: 813 */           String queryName = (String)iterator.next();
/*  761: 814 */           HibernateException e = (HibernateException)errors.get(queryName);
/*  762: 815 */           failingQueries.append(queryName);
/*  763: 816 */           if (iterator.hasNext()) {
/*  764: 816 */             failingQueries.append(", ");
/*  765:     */           }
/*  766: 817 */           LOG.namedQueryError(queryName, e);
/*  767:     */         }
/*  768: 819 */         throw new HibernateException(failingQueries.toString());
/*  769:     */       }
/*  770:     */     }
/*  771: 824 */     this.fetchProfiles = new HashMap();
/*  772: 825 */     for (org.hibernate.metamodel.binding.FetchProfile mappingProfile : metadata.getFetchProfiles())
/*  773:     */     {
/*  774: 826 */       org.hibernate.engine.profile.FetchProfile fetchProfile = new org.hibernate.engine.profile.FetchProfile(mappingProfile.getName());
/*  775: 827 */       for (org.hibernate.metamodel.binding.FetchProfile.Fetch mappingFetch : mappingProfile.getFetches())
/*  776:     */       {
/*  777: 829 */         String entityName = getImportedClassName(mappingFetch.getEntity());
/*  778: 830 */         EntityPersister owner = (EntityPersister)(entityName == null ? null : this.entityPersisters.get(entityName));
/*  779: 831 */         if (owner == null) {
/*  780: 832 */           throw new HibernateException("Unable to resolve entity reference [" + mappingFetch.getEntity() + "] in fetch profile [" + fetchProfile.getName() + "]");
/*  781:     */         }
/*  782: 839 */         Type associationType = owner.getPropertyType(mappingFetch.getAssociation());
/*  783: 840 */         if ((associationType == null) || (!associationType.isAssociationType())) {
/*  784: 841 */           throw new HibernateException("Fetch profile [" + fetchProfile.getName() + "] specified an invalid association");
/*  785:     */         }
/*  786: 845 */         Fetch.Style fetchStyle = Fetch.Style.parse(mappingFetch.getStyle());
/*  787:     */         
/*  788:     */ 
/*  789: 848 */         fetchProfile.addFetch(new Association(owner, mappingFetch.getAssociation()), fetchStyle);
/*  790: 849 */         ((Loadable)owner).registerAffectingFetchProfile(fetchProfile.getName());
/*  791:     */       }
/*  792: 851 */       this.fetchProfiles.put(fetchProfile.getName(), fetchProfile);
/*  793:     */     }
/*  794: 854 */     this.transactionEnvironment = new TransactionEnvironmentImpl(this);
/*  795: 855 */     this.observer.sessionFactoryCreated(this);
/*  796:     */   }
/*  797:     */   
/*  798:     */   private static Properties createPropertiesFromMap(Map map)
/*  799:     */   {
/*  800: 860 */     Properties properties = new Properties();
/*  801: 861 */     properties.putAll(map);
/*  802: 862 */     return properties;
/*  803:     */   }
/*  804:     */   
/*  805:     */   public Session openSession()
/*  806:     */     throws HibernateException
/*  807:     */   {
/*  808: 866 */     return withOptions().openSession();
/*  809:     */   }
/*  810:     */   
/*  811:     */   public Session openTemporarySession()
/*  812:     */     throws HibernateException
/*  813:     */   {
/*  814: 870 */     return withOptions().autoClose(false).flushBeforeCompletion(false).connectionReleaseMode(ConnectionReleaseMode.AFTER_STATEMENT).openSession();
/*  815:     */   }
/*  816:     */   
/*  817:     */   public Session getCurrentSession()
/*  818:     */     throws HibernateException
/*  819:     */   {
/*  820: 878 */     if (this.currentSessionContext == null) {
/*  821: 879 */       throw new HibernateException("No CurrentSessionContext configured!");
/*  822:     */     }
/*  823: 881 */     return this.currentSessionContext.currentSession();
/*  824:     */   }
/*  825:     */   
/*  826:     */   public SessionBuilder withOptions()
/*  827:     */   {
/*  828: 886 */     return new SessionBuilderImpl(this);
/*  829:     */   }
/*  830:     */   
/*  831:     */   public StatelessSessionBuilder withStatelessOptions()
/*  832:     */   {
/*  833: 891 */     return new StatelessSessionBuilderImpl(this);
/*  834:     */   }
/*  835:     */   
/*  836:     */   public StatelessSession openStatelessSession()
/*  837:     */   {
/*  838: 895 */     return withStatelessOptions().openStatelessSession();
/*  839:     */   }
/*  840:     */   
/*  841:     */   public StatelessSession openStatelessSession(Connection connection)
/*  842:     */   {
/*  843: 899 */     return withStatelessOptions().connection(connection).openStatelessSession();
/*  844:     */   }
/*  845:     */   
/*  846:     */   public void addObserver(SessionFactoryObserver observer)
/*  847:     */   {
/*  848: 904 */     this.observer.addObserver(observer);
/*  849:     */   }
/*  850:     */   
/*  851:     */   public TransactionEnvironment getTransactionEnvironment()
/*  852:     */   {
/*  853: 908 */     return this.transactionEnvironment;
/*  854:     */   }
/*  855:     */   
/*  856:     */   public Properties getProperties()
/*  857:     */   {
/*  858: 912 */     return this.properties;
/*  859:     */   }
/*  860:     */   
/*  861:     */   public IdentifierGeneratorFactory getIdentifierGeneratorFactory()
/*  862:     */   {
/*  863: 916 */     return null;
/*  864:     */   }
/*  865:     */   
/*  866:     */   public TypeResolver getTypeResolver()
/*  867:     */   {
/*  868: 920 */     return this.typeResolver;
/*  869:     */   }
/*  870:     */   
/*  871:     */   private void registerEntityNameResolvers(EntityPersister persister)
/*  872:     */   {
/*  873: 924 */     if ((persister.getEntityMetamodel() == null) || (persister.getEntityMetamodel().getTuplizer() == null)) {
/*  874: 925 */       return;
/*  875:     */     }
/*  876: 927 */     registerEntityNameResolvers(persister.getEntityMetamodel().getTuplizer());
/*  877:     */   }
/*  878:     */   
/*  879:     */   private void registerEntityNameResolvers(EntityTuplizer tuplizer)
/*  880:     */   {
/*  881: 931 */     EntityNameResolver[] resolvers = tuplizer.getEntityNameResolvers();
/*  882: 932 */     if (resolvers == null) {
/*  883: 933 */       return;
/*  884:     */     }
/*  885: 936 */     for (EntityNameResolver resolver : resolvers) {
/*  886: 937 */       registerEntityNameResolver(resolver);
/*  887:     */     }
/*  888:     */   }
/*  889:     */   
/*  890: 941 */   private static final Object ENTITY_NAME_RESOLVER_MAP_VALUE = new Object();
/*  891:     */   
/*  892:     */   public void registerEntityNameResolver(EntityNameResolver resolver)
/*  893:     */   {
/*  894: 944 */     this.entityNameResolvers.put(resolver, ENTITY_NAME_RESOLVER_MAP_VALUE);
/*  895:     */   }
/*  896:     */   
/*  897:     */   public Iterable<EntityNameResolver> iterateEntityNameResolvers()
/*  898:     */   {
/*  899: 948 */     return this.entityNameResolvers.keySet();
/*  900:     */   }
/*  901:     */   
/*  902:     */   public QueryPlanCache getQueryPlanCache()
/*  903:     */   {
/*  904: 952 */     return this.queryPlanCache;
/*  905:     */   }
/*  906:     */   
/*  907:     */   private Map checkNamedQueries()
/*  908:     */     throws HibernateException
/*  909:     */   {
/*  910: 956 */     Map errors = new HashMap();
/*  911: 959 */     if (LOG.isDebugEnabled()) {
/*  912: 960 */       LOG.debugf("Checking %s named HQL queries", Integer.valueOf(this.namedQueries.size()));
/*  913:     */     }
/*  914: 962 */     Iterator itr = this.namedQueries.entrySet().iterator();
/*  915: 963 */     while (itr.hasNext())
/*  916:     */     {
/*  917: 964 */       Map.Entry entry = (Map.Entry)itr.next();
/*  918: 965 */       String queryName = (String)entry.getKey();
/*  919: 966 */       NamedQueryDefinition qd = (NamedQueryDefinition)entry.getValue();
/*  920:     */       try
/*  921:     */       {
/*  922: 969 */         LOG.debugf("Checking named query: %s", queryName);
/*  923:     */         
/*  924: 971 */         this.queryPlanCache.getHQLQueryPlan(qd.getQueryString(), false, CollectionHelper.EMPTY_MAP);
/*  925:     */       }
/*  926:     */       catch (QueryException e)
/*  927:     */       {
/*  928: 974 */         errors.put(queryName, e);
/*  929:     */       }
/*  930:     */       catch (MappingException e)
/*  931:     */       {
/*  932: 977 */         errors.put(queryName, e);
/*  933:     */       }
/*  934:     */     }
/*  935: 980 */     if (LOG.isDebugEnabled()) {
/*  936: 981 */       LOG.debugf("Checking %s named SQL queries", Integer.valueOf(this.namedSqlQueries.size()));
/*  937:     */     }
/*  938: 983 */     itr = this.namedSqlQueries.entrySet().iterator();
/*  939: 984 */     while (itr.hasNext())
/*  940:     */     {
/*  941: 985 */       Map.Entry entry = (Map.Entry)itr.next();
/*  942: 986 */       String queryName = (String)entry.getKey();
/*  943: 987 */       NamedSQLQueryDefinition qd = (NamedSQLQueryDefinition)entry.getValue();
/*  944:     */       try
/*  945:     */       {
/*  946: 990 */         LOG.debugf("Checking named SQL query: %s", queryName);
/*  947:     */         NativeSQLQuerySpecification spec;
/*  948:     */         NativeSQLQuerySpecification spec;
/*  949: 994 */         if (qd.getResultSetRef() != null)
/*  950:     */         {
/*  951: 995 */           ResultSetMappingDefinition definition = (ResultSetMappingDefinition)this.sqlResultSetMappings.get(qd.getResultSetRef());
/*  952: 996 */           if (definition == null) {
/*  953: 997 */             throw new MappingException("Unable to find resultset-ref definition: " + qd.getResultSetRef());
/*  954:     */           }
/*  955: 999 */           spec = new NativeSQLQuerySpecification(qd.getQueryString(), definition.getQueryReturns(), qd.getQuerySpaces());
/*  956:     */         }
/*  957:     */         else
/*  958:     */         {
/*  959:1006 */           spec = new NativeSQLQuerySpecification(qd.getQueryString(), qd.getQueryReturns(), qd.getQuerySpaces());
/*  960:     */         }
/*  961:1012 */         this.queryPlanCache.getNativeSQLQueryPlan(spec);
/*  962:     */       }
/*  963:     */       catch (QueryException e)
/*  964:     */       {
/*  965:1015 */         errors.put(queryName, e);
/*  966:     */       }
/*  967:     */       catch (MappingException e)
/*  968:     */       {
/*  969:1018 */         errors.put(queryName, e);
/*  970:     */       }
/*  971:     */     }
/*  972:1022 */     return errors;
/*  973:     */   }
/*  974:     */   
/*  975:     */   public EntityPersister getEntityPersister(String entityName)
/*  976:     */     throws MappingException
/*  977:     */   {
/*  978:1026 */     EntityPersister result = (EntityPersister)this.entityPersisters.get(entityName);
/*  979:1027 */     if (result == null) {
/*  980:1028 */       throw new MappingException("Unknown entity: " + entityName);
/*  981:     */     }
/*  982:1030 */     return result;
/*  983:     */   }
/*  984:     */   
/*  985:     */   public CollectionPersister getCollectionPersister(String role)
/*  986:     */     throws MappingException
/*  987:     */   {
/*  988:1034 */     CollectionPersister result = (CollectionPersister)this.collectionPersisters.get(role);
/*  989:1035 */     if (result == null) {
/*  990:1036 */       throw new MappingException("Unknown collection role: " + role);
/*  991:     */     }
/*  992:1038 */     return result;
/*  993:     */   }
/*  994:     */   
/*  995:     */   public Settings getSettings()
/*  996:     */   {
/*  997:1042 */     return this.settings;
/*  998:     */   }
/*  999:     */   
/* 1000:     */   public SessionFactory.SessionFactoryOptions getSessionFactoryOptions()
/* 1001:     */   {
/* 1002:1047 */     return this.sessionFactoryOptions;
/* 1003:     */   }
/* 1004:     */   
/* 1005:     */   public JdbcServices getJdbcServices()
/* 1006:     */   {
/* 1007:1051 */     return this.jdbcServices;
/* 1008:     */   }
/* 1009:     */   
/* 1010:     */   public Dialect getDialect()
/* 1011:     */   {
/* 1012:1055 */     if (this.serviceRegistry == null) {
/* 1013:1056 */       throw new IllegalStateException("Cannot determine dialect because serviceRegistry is null.");
/* 1014:     */     }
/* 1015:1058 */     return this.dialect;
/* 1016:     */   }
/* 1017:     */   
/* 1018:     */   public Interceptor getInterceptor()
/* 1019:     */   {
/* 1020:1062 */     return this.sessionFactoryOptions.getInterceptor();
/* 1021:     */   }
/* 1022:     */   
/* 1023:     */   public SQLExceptionConverter getSQLExceptionConverter()
/* 1024:     */   {
/* 1025:1066 */     return getSQLExceptionHelper().getSqlExceptionConverter();
/* 1026:     */   }
/* 1027:     */   
/* 1028:     */   public SqlExceptionHelper getSQLExceptionHelper()
/* 1029:     */   {
/* 1030:1070 */     return getJdbcServices().getSqlExceptionHelper();
/* 1031:     */   }
/* 1032:     */   
/* 1033:     */   public Set<String> getCollectionRolesByEntityParticipant(String entityName)
/* 1034:     */   {
/* 1035:1074 */     return (Set)this.collectionRolesByEntityParticipant.get(entityName);
/* 1036:     */   }
/* 1037:     */   
/* 1038:     */   public Reference getReference()
/* 1039:     */   {
/* 1040:1080 */     LOG.debug("Returning a Reference to the SessionFactory");
/* 1041:1081 */     return new Reference(SessionFactoryImpl.class.getName(), new StringRefAddr("uuid", this.uuid), SessionFactoryRegistry.ObjectFactoryImpl.class.getName(), null);
/* 1042:     */   }
/* 1043:     */   
/* 1044:     */   private Object readResolve()
/* 1045:     */     throws ObjectStreamException
/* 1046:     */   {
/* 1047:1090 */     LOG.trace("Resolving serialized SessionFactory");
/* 1048:     */     
/* 1049:1092 */     Object result = SessionFactoryRegistry.INSTANCE.getSessionFactory(this.uuid);
/* 1050:1093 */     if (result == null)
/* 1051:     */     {
/* 1052:1096 */       result = SessionFactoryRegistry.INSTANCE.getNamedSessionFactory(this.name);
/* 1053:1097 */       if (result == null) {
/* 1054:1098 */         throw new InvalidObjectException("Could not find a SessionFactory [uuid=" + this.uuid + ",name=" + this.name + "]");
/* 1055:     */       }
/* 1056:1100 */       LOG.debug("Resolved SessionFactory by name");
/* 1057:     */     }
/* 1058:     */     else
/* 1059:     */     {
/* 1060:1103 */       LOG.debug("Resolved SessionFactory by UUID");
/* 1061:     */     }
/* 1062:1105 */     return result;
/* 1063:     */   }
/* 1064:     */   
/* 1065:     */   public NamedQueryDefinition getNamedQuery(String queryName)
/* 1066:     */   {
/* 1067:1109 */     return (NamedQueryDefinition)this.namedQueries.get(queryName);
/* 1068:     */   }
/* 1069:     */   
/* 1070:     */   public NamedSQLQueryDefinition getNamedSQLQuery(String queryName)
/* 1071:     */   {
/* 1072:1113 */     return (NamedSQLQueryDefinition)this.namedSqlQueries.get(queryName);
/* 1073:     */   }
/* 1074:     */   
/* 1075:     */   public ResultSetMappingDefinition getResultSetMapping(String resultSetName)
/* 1076:     */   {
/* 1077:1117 */     return (ResultSetMappingDefinition)this.sqlResultSetMappings.get(resultSetName);
/* 1078:     */   }
/* 1079:     */   
/* 1080:     */   public Type getIdentifierType(String className)
/* 1081:     */     throws MappingException
/* 1082:     */   {
/* 1083:1121 */     return getEntityPersister(className).getIdentifierType();
/* 1084:     */   }
/* 1085:     */   
/* 1086:     */   public String getIdentifierPropertyName(String className)
/* 1087:     */     throws MappingException
/* 1088:     */   {
/* 1089:1124 */     return getEntityPersister(className).getIdentifierPropertyName();
/* 1090:     */   }
/* 1091:     */   
/* 1092:     */   private void readObject(ObjectInputStream in)
/* 1093:     */     throws IOException, ClassNotFoundException
/* 1094:     */   {
/* 1095:1128 */     LOG.trace("Deserializing");
/* 1096:1129 */     in.defaultReadObject();
/* 1097:1130 */     LOG.debugf("Deserialized: %s", this.uuid);
/* 1098:     */   }
/* 1099:     */   
/* 1100:     */   private void writeObject(ObjectOutputStream out)
/* 1101:     */     throws IOException
/* 1102:     */   {
/* 1103:1134 */     LOG.debugf("Serializing: %s", this.uuid);
/* 1104:1135 */     out.defaultWriteObject();
/* 1105:1136 */     LOG.trace("Serialized");
/* 1106:     */   }
/* 1107:     */   
/* 1108:     */   public Type[] getReturnTypes(String queryString)
/* 1109:     */     throws HibernateException
/* 1110:     */   {
/* 1111:1140 */     return this.queryPlanCache.getHQLQueryPlan(queryString, false, CollectionHelper.EMPTY_MAP).getReturnMetadata().getReturnTypes();
/* 1112:     */   }
/* 1113:     */   
/* 1114:     */   public String[] getReturnAliases(String queryString)
/* 1115:     */     throws HibernateException
/* 1116:     */   {
/* 1117:1144 */     return this.queryPlanCache.getHQLQueryPlan(queryString, false, CollectionHelper.EMPTY_MAP).getReturnMetadata().getReturnAliases();
/* 1118:     */   }
/* 1119:     */   
/* 1120:     */   public ClassMetadata getClassMetadata(Class persistentClass)
/* 1121:     */     throws HibernateException
/* 1122:     */   {
/* 1123:1148 */     return getClassMetadata(persistentClass.getName());
/* 1124:     */   }
/* 1125:     */   
/* 1126:     */   public CollectionMetadata getCollectionMetadata(String roleName)
/* 1127:     */     throws HibernateException
/* 1128:     */   {
/* 1129:1152 */     return (CollectionMetadata)this.collectionMetadata.get(roleName);
/* 1130:     */   }
/* 1131:     */   
/* 1132:     */   public ClassMetadata getClassMetadata(String entityName)
/* 1133:     */     throws HibernateException
/* 1134:     */   {
/* 1135:1156 */     return (ClassMetadata)this.classMetadata.get(entityName);
/* 1136:     */   }
/* 1137:     */   
/* 1138:     */   public String[] getImplementors(String className)
/* 1139:     */     throws MappingException
/* 1140:     */   {
/* 1141:     */     Class clazz;
/* 1142:     */     try
/* 1143:     */     {
/* 1144:1170 */       clazz = ReflectHelper.classForName(className);
/* 1145:     */     }
/* 1146:     */     catch (ClassNotFoundException cnfe)
/* 1147:     */     {
/* 1148:1173 */       return new String[] { className };
/* 1149:     */     }
/* 1150:1176 */     ArrayList results = new ArrayList();
/* 1151:1177 */     Iterator iter = this.entityPersisters.values().iterator();
/* 1152:1178 */     while (iter.hasNext())
/* 1153:     */     {
/* 1154:1180 */       EntityPersister testPersister = (EntityPersister)iter.next();
/* 1155:1181 */       if ((testPersister instanceof Queryable))
/* 1156:     */       {
/* 1157:1182 */         Queryable testQueryable = (Queryable)testPersister;
/* 1158:1183 */         String testClassName = testQueryable.getEntityName();
/* 1159:1184 */         boolean isMappedClass = className.equals(testClassName);
/* 1160:1185 */         if (testQueryable.isExplicitPolymorphism())
/* 1161:     */         {
/* 1162:1186 */           if (isMappedClass) {
/* 1163:1187 */             return new String[] { className };
/* 1164:     */           }
/* 1165:     */         }
/* 1166:1191 */         else if (isMappedClass)
/* 1167:     */         {
/* 1168:1192 */           results.add(testClassName);
/* 1169:     */         }
/* 1170:     */         else
/* 1171:     */         {
/* 1172:1195 */           Class mappedClass = testQueryable.getMappedClass();
/* 1173:1196 */           if ((mappedClass != null) && (clazz.isAssignableFrom(mappedClass)))
/* 1174:     */           {
/* 1175:     */             boolean assignableSuperclass;
/* 1176:     */             boolean assignableSuperclass;
/* 1177:1198 */             if (testQueryable.isInherited())
/* 1178:     */             {
/* 1179:1199 */               Class mappedSuperclass = getEntityPersister(testQueryable.getMappedSuperclass()).getMappedClass();
/* 1180:1200 */               assignableSuperclass = clazz.isAssignableFrom(mappedSuperclass);
/* 1181:     */             }
/* 1182:     */             else
/* 1183:     */             {
/* 1184:1203 */               assignableSuperclass = false;
/* 1185:     */             }
/* 1186:1205 */             if (!assignableSuperclass) {
/* 1187:1206 */               results.add(testClassName);
/* 1188:     */             }
/* 1189:     */           }
/* 1190:     */         }
/* 1191:     */       }
/* 1192:     */     }
/* 1193:1213 */     return (String[])results.toArray(new String[results.size()]);
/* 1194:     */   }
/* 1195:     */   
/* 1196:     */   public String getImportedClassName(String className)
/* 1197:     */   {
/* 1198:1217 */     String result = (String)this.imports.get(className);
/* 1199:1218 */     if (result == null) {
/* 1200:     */       try
/* 1201:     */       {
/* 1202:1220 */         ReflectHelper.classForName(className);
/* 1203:1221 */         return className;
/* 1204:     */       }
/* 1205:     */       catch (ClassNotFoundException cnfe)
/* 1206:     */       {
/* 1207:1224 */         return null;
/* 1208:     */       }
/* 1209:     */     }
/* 1210:1228 */     return result;
/* 1211:     */   }
/* 1212:     */   
/* 1213:     */   public Map<String, ClassMetadata> getAllClassMetadata()
/* 1214:     */     throws HibernateException
/* 1215:     */   {
/* 1216:1233 */     return this.classMetadata;
/* 1217:     */   }
/* 1218:     */   
/* 1219:     */   public Map getAllCollectionMetadata()
/* 1220:     */     throws HibernateException
/* 1221:     */   {
/* 1222:1237 */     return this.collectionMetadata;
/* 1223:     */   }
/* 1224:     */   
/* 1225:     */   public Type getReferencedPropertyType(String className, String propertyName)
/* 1226:     */     throws MappingException
/* 1227:     */   {
/* 1228:1242 */     return getEntityPersister(className).getPropertyType(propertyName);
/* 1229:     */   }
/* 1230:     */   
/* 1231:     */   public ConnectionProvider getConnectionProvider()
/* 1232:     */   {
/* 1233:1246 */     return this.jdbcServices.getConnectionProvider();
/* 1234:     */   }
/* 1235:     */   
/* 1236:     */   public void close()
/* 1237:     */     throws HibernateException
/* 1238:     */   {
/* 1239:1266 */     if (this.isClosed)
/* 1240:     */     {
/* 1241:1267 */       LOG.trace("Already closed");
/* 1242:1268 */       return;
/* 1243:     */     }
/* 1244:1271 */     LOG.closing();
/* 1245:     */     
/* 1246:1273 */     this.isClosed = true;
/* 1247:     */     
/* 1248:1275 */     Iterator iter = this.entityPersisters.values().iterator();
/* 1249:1276 */     while (iter.hasNext())
/* 1250:     */     {
/* 1251:1277 */       EntityPersister p = (EntityPersister)iter.next();
/* 1252:1278 */       if (p.hasCache()) {
/* 1253:1279 */         p.getCacheAccessStrategy().getRegion().destroy();
/* 1254:     */       }
/* 1255:     */     }
/* 1256:1283 */     iter = this.collectionPersisters.values().iterator();
/* 1257:1284 */     while (iter.hasNext())
/* 1258:     */     {
/* 1259:1285 */       CollectionPersister p = (CollectionPersister)iter.next();
/* 1260:1286 */       if (p.hasCache()) {
/* 1261:1287 */         p.getCacheAccessStrategy().getRegion().destroy();
/* 1262:     */       }
/* 1263:     */     }
/* 1264:1291 */     if (this.settings.isQueryCacheEnabled())
/* 1265:     */     {
/* 1266:1292 */       this.queryCache.destroy();
/* 1267:     */       
/* 1268:1294 */       iter = this.queryCaches.values().iterator();
/* 1269:1295 */       while (iter.hasNext())
/* 1270:     */       {
/* 1271:1296 */         QueryCache cache = (QueryCache)iter.next();
/* 1272:1297 */         cache.destroy();
/* 1273:     */       }
/* 1274:1299 */       this.updateTimestampsCache.destroy();
/* 1275:     */     }
/* 1276:1302 */     this.settings.getRegionFactory().stop();
/* 1277:1304 */     if (this.settings.isAutoDropSchema()) {
/* 1278:1305 */       this.schemaExport.drop(false, true);
/* 1279:     */     }
/* 1280:1308 */     SessionFactoryRegistry.INSTANCE.removeSessionFactory(this.uuid, this.name, (JndiService)this.serviceRegistry.getService(JndiService.class));
/* 1281:     */     
/* 1282:     */ 
/* 1283:     */ 
/* 1284:1312 */     this.observer.sessionFactoryClosed(this);
/* 1285:1313 */     this.serviceRegistry.destroy();
/* 1286:     */   }
/* 1287:     */   
/* 1288:     */   private class CacheImpl
/* 1289:     */     implements Cache
/* 1290:     */   {
/* 1291:     */     private CacheImpl() {}
/* 1292:     */     
/* 1293:     */     public boolean containsEntity(Class entityClass, Serializable identifier)
/* 1294:     */     {
/* 1295:1318 */       return containsEntity(entityClass.getName(), identifier);
/* 1296:     */     }
/* 1297:     */     
/* 1298:     */     public boolean containsEntity(String entityName, Serializable identifier)
/* 1299:     */     {
/* 1300:1322 */       EntityPersister p = SessionFactoryImpl.this.getEntityPersister(entityName);
/* 1301:1323 */       return (p.hasCache()) && (p.getCacheAccessStrategy().getRegion().contains(buildCacheKey(identifier, p)));
/* 1302:     */     }
/* 1303:     */     
/* 1304:     */     public void evictEntity(Class entityClass, Serializable identifier)
/* 1305:     */     {
/* 1306:1328 */       evictEntity(entityClass.getName(), identifier);
/* 1307:     */     }
/* 1308:     */     
/* 1309:     */     public void evictEntity(String entityName, Serializable identifier)
/* 1310:     */     {
/* 1311:1332 */       EntityPersister p = SessionFactoryImpl.this.getEntityPersister(entityName);
/* 1312:1333 */       if (p.hasCache())
/* 1313:     */       {
/* 1314:1334 */         if (SessionFactoryImpl.LOG.isDebugEnabled()) {
/* 1315:1335 */           SessionFactoryImpl.LOG.debugf("Evicting second-level cache: %s", MessageHelper.infoString(p, identifier, SessionFactoryImpl.this));
/* 1316:     */         }
/* 1317:1338 */         p.getCacheAccessStrategy().evict(buildCacheKey(identifier, p));
/* 1318:     */       }
/* 1319:     */     }
/* 1320:     */     
/* 1321:     */     private CacheKey buildCacheKey(Serializable identifier, EntityPersister p)
/* 1322:     */     {
/* 1323:1343 */       return new CacheKey(identifier, p.getIdentifierType(), p.getRootEntityName(), null, SessionFactoryImpl.this);
/* 1324:     */     }
/* 1325:     */     
/* 1326:     */     public void evictEntityRegion(Class entityClass)
/* 1327:     */     {
/* 1328:1353 */       evictEntityRegion(entityClass.getName());
/* 1329:     */     }
/* 1330:     */     
/* 1331:     */     public void evictEntityRegion(String entityName)
/* 1332:     */     {
/* 1333:1357 */       EntityPersister p = SessionFactoryImpl.this.getEntityPersister(entityName);
/* 1334:1358 */       if (p.hasCache())
/* 1335:     */       {
/* 1336:1359 */         if (SessionFactoryImpl.LOG.isDebugEnabled()) {
/* 1337:1360 */           SessionFactoryImpl.LOG.debugf("Evicting second-level cache: %s", p.getEntityName());
/* 1338:     */         }
/* 1339:1362 */         p.getCacheAccessStrategy().evictAll();
/* 1340:     */       }
/* 1341:     */     }
/* 1342:     */     
/* 1343:     */     public void evictEntityRegions()
/* 1344:     */     {
/* 1345:1367 */       Iterator entityNames = SessionFactoryImpl.this.entityPersisters.keySet().iterator();
/* 1346:1368 */       while (entityNames.hasNext()) {
/* 1347:1369 */         evictEntityRegion((String)entityNames.next());
/* 1348:     */       }
/* 1349:     */     }
/* 1350:     */     
/* 1351:     */     public boolean containsCollection(String role, Serializable ownerIdentifier)
/* 1352:     */     {
/* 1353:1374 */       CollectionPersister p = SessionFactoryImpl.this.getCollectionPersister(role);
/* 1354:1375 */       return (p.hasCache()) && (p.getCacheAccessStrategy().getRegion().contains(buildCacheKey(ownerIdentifier, p)));
/* 1355:     */     }
/* 1356:     */     
/* 1357:     */     public void evictCollection(String role, Serializable ownerIdentifier)
/* 1358:     */     {
/* 1359:1380 */       CollectionPersister p = SessionFactoryImpl.this.getCollectionPersister(role);
/* 1360:1381 */       if (p.hasCache())
/* 1361:     */       {
/* 1362:1382 */         if (SessionFactoryImpl.LOG.isDebugEnabled()) {
/* 1363:1383 */           SessionFactoryImpl.LOG.debugf("Evicting second-level cache: %s", MessageHelper.collectionInfoString(p, ownerIdentifier, SessionFactoryImpl.this));
/* 1364:     */         }
/* 1365:1386 */         CacheKey cacheKey = buildCacheKey(ownerIdentifier, p);
/* 1366:1387 */         p.getCacheAccessStrategy().evict(cacheKey);
/* 1367:     */       }
/* 1368:     */     }
/* 1369:     */     
/* 1370:     */     private CacheKey buildCacheKey(Serializable ownerIdentifier, CollectionPersister p)
/* 1371:     */     {
/* 1372:1392 */       return new CacheKey(ownerIdentifier, p.getKeyType(), p.getRole(), null, SessionFactoryImpl.this);
/* 1373:     */     }
/* 1374:     */     
/* 1375:     */     public void evictCollectionRegion(String role)
/* 1376:     */     {
/* 1377:1402 */       CollectionPersister p = SessionFactoryImpl.this.getCollectionPersister(role);
/* 1378:1403 */       if (p.hasCache())
/* 1379:     */       {
/* 1380:1404 */         if (SessionFactoryImpl.LOG.isDebugEnabled()) {
/* 1381:1405 */           SessionFactoryImpl.LOG.debugf("Evicting second-level cache: %s", p.getRole());
/* 1382:     */         }
/* 1383:1407 */         p.getCacheAccessStrategy().evictAll();
/* 1384:     */       }
/* 1385:     */     }
/* 1386:     */     
/* 1387:     */     public void evictCollectionRegions()
/* 1388:     */     {
/* 1389:1412 */       Iterator collectionRoles = SessionFactoryImpl.this.collectionPersisters.keySet().iterator();
/* 1390:1413 */       while (collectionRoles.hasNext()) {
/* 1391:1414 */         evictCollectionRegion((String)collectionRoles.next());
/* 1392:     */       }
/* 1393:     */     }
/* 1394:     */     
/* 1395:     */     public boolean containsQuery(String regionName)
/* 1396:     */     {
/* 1397:1419 */       return SessionFactoryImpl.this.queryCaches.containsKey(regionName);
/* 1398:     */     }
/* 1399:     */     
/* 1400:     */     public void evictDefaultQueryRegion()
/* 1401:     */     {
/* 1402:1423 */       if (SessionFactoryImpl.this.settings.isQueryCacheEnabled()) {
/* 1403:1424 */         SessionFactoryImpl.this.queryCache.clear();
/* 1404:     */       }
/* 1405:     */     }
/* 1406:     */     
/* 1407:     */     public void evictQueryRegion(String regionName)
/* 1408:     */     {
/* 1409:1429 */       if (regionName == null) {
/* 1410:1429 */         throw new NullPointerException("Region-name cannot be null (use Cache#evictDefaultQueryRegion to evict the default query cache)");
/* 1411:     */       }
/* 1412:1431 */       if (SessionFactoryImpl.this.settings.isQueryCacheEnabled())
/* 1413:     */       {
/* 1414:1432 */         QueryCache namedQueryCache = (QueryCache)SessionFactoryImpl.this.queryCaches.get(regionName);
/* 1415:1434 */         if (namedQueryCache != null) {
/* 1416:1434 */           namedQueryCache.clear();
/* 1417:     */         }
/* 1418:     */       }
/* 1419:     */     }
/* 1420:     */     
/* 1421:     */     public void evictQueryRegions()
/* 1422:     */     {
/* 1423:1439 */       if (SessionFactoryImpl.this.queryCaches != null) {
/* 1424:1440 */         for (QueryCache queryCache : SessionFactoryImpl.this.queryCaches.values()) {
/* 1425:1441 */           queryCache.clear();
/* 1426:     */         }
/* 1427:     */       }
/* 1428:     */     }
/* 1429:     */   }
/* 1430:     */   
/* 1431:     */   public Cache getCache()
/* 1432:     */   {
/* 1433:1449 */     return this.cacheAccess;
/* 1434:     */   }
/* 1435:     */   
/* 1436:     */   public void evictEntity(String entityName, Serializable id)
/* 1437:     */     throws HibernateException
/* 1438:     */   {
/* 1439:1453 */     getCache().evictEntity(entityName, id);
/* 1440:     */   }
/* 1441:     */   
/* 1442:     */   public void evictEntity(String entityName)
/* 1443:     */     throws HibernateException
/* 1444:     */   {
/* 1445:1457 */     getCache().evictEntityRegion(entityName);
/* 1446:     */   }
/* 1447:     */   
/* 1448:     */   public void evict(Class persistentClass, Serializable id)
/* 1449:     */     throws HibernateException
/* 1450:     */   {
/* 1451:1461 */     getCache().evictEntity(persistentClass, id);
/* 1452:     */   }
/* 1453:     */   
/* 1454:     */   public void evict(Class persistentClass)
/* 1455:     */     throws HibernateException
/* 1456:     */   {
/* 1457:1465 */     getCache().evictEntityRegion(persistentClass);
/* 1458:     */   }
/* 1459:     */   
/* 1460:     */   public void evictCollection(String roleName, Serializable id)
/* 1461:     */     throws HibernateException
/* 1462:     */   {
/* 1463:1469 */     getCache().evictCollection(roleName, id);
/* 1464:     */   }
/* 1465:     */   
/* 1466:     */   public void evictCollection(String roleName)
/* 1467:     */     throws HibernateException
/* 1468:     */   {
/* 1469:1473 */     getCache().evictCollectionRegion(roleName);
/* 1470:     */   }
/* 1471:     */   
/* 1472:     */   public void evictQueries()
/* 1473:     */     throws HibernateException
/* 1474:     */   {
/* 1475:1477 */     if (this.settings.isQueryCacheEnabled()) {
/* 1476:1478 */       this.queryCache.clear();
/* 1477:     */     }
/* 1478:     */   }
/* 1479:     */   
/* 1480:     */   public void evictQueries(String regionName)
/* 1481:     */     throws HibernateException
/* 1482:     */   {
/* 1483:1483 */     getCache().evictQueryRegion(regionName);
/* 1484:     */   }
/* 1485:     */   
/* 1486:     */   public UpdateTimestampsCache getUpdateTimestampsCache()
/* 1487:     */   {
/* 1488:1487 */     return this.updateTimestampsCache;
/* 1489:     */   }
/* 1490:     */   
/* 1491:     */   public QueryCache getQueryCache()
/* 1492:     */   {
/* 1493:1491 */     return this.queryCache;
/* 1494:     */   }
/* 1495:     */   
/* 1496:     */   public QueryCache getQueryCache(String regionName)
/* 1497:     */     throws HibernateException
/* 1498:     */   {
/* 1499:1495 */     if (regionName == null) {
/* 1500:1496 */       return getQueryCache();
/* 1501:     */     }
/* 1502:1499 */     if (!this.settings.isQueryCacheEnabled()) {
/* 1503:1500 */       return null;
/* 1504:     */     }
/* 1505:1503 */     QueryCache currentQueryCache = (QueryCache)this.queryCaches.get(regionName);
/* 1506:1504 */     if (currentQueryCache == null) {
/* 1507:1505 */       synchronized (this.allCacheRegions)
/* 1508:     */       {
/* 1509:1506 */         currentQueryCache = (QueryCache)this.queryCaches.get(regionName);
/* 1510:1507 */         if (currentQueryCache == null)
/* 1511:     */         {
/* 1512:1508 */           currentQueryCache = this.settings.getQueryCacheFactory().getQueryCache(regionName, this.updateTimestampsCache, this.settings, this.properties);
/* 1513:     */           
/* 1514:1510 */           this.queryCaches.put(regionName, currentQueryCache);
/* 1515:1511 */           this.allCacheRegions.put(currentQueryCache.getRegion().getName(), currentQueryCache.getRegion());
/* 1516:     */         }
/* 1517:     */         else
/* 1518:     */         {
/* 1519:1513 */           return currentQueryCache;
/* 1520:     */         }
/* 1521:     */       }
/* 1522:     */     }
/* 1523:1517 */     return currentQueryCache;
/* 1524:     */   }
/* 1525:     */   
/* 1526:     */   public Region getSecondLevelCacheRegion(String regionName)
/* 1527:     */   {
/* 1528:1521 */     return (Region)this.allCacheRegions.get(regionName);
/* 1529:     */   }
/* 1530:     */   
/* 1531:     */   public Map getAllSecondLevelCacheRegions()
/* 1532:     */   {
/* 1533:1525 */     return new HashMap(this.allCacheRegions);
/* 1534:     */   }
/* 1535:     */   
/* 1536:     */   public boolean isClosed()
/* 1537:     */   {
/* 1538:1529 */     return this.isClosed;
/* 1539:     */   }
/* 1540:     */   
/* 1541:     */   public Statistics getStatistics()
/* 1542:     */   {
/* 1543:1533 */     return getStatisticsImplementor();
/* 1544:     */   }
/* 1545:     */   
/* 1546:     */   public StatisticsImplementor getStatisticsImplementor()
/* 1547:     */   {
/* 1548:1537 */     return (StatisticsImplementor)this.serviceRegistry.getService(StatisticsImplementor.class);
/* 1549:     */   }
/* 1550:     */   
/* 1551:     */   public FilterDefinition getFilterDefinition(String filterName)
/* 1552:     */     throws HibernateException
/* 1553:     */   {
/* 1554:1541 */     FilterDefinition def = (FilterDefinition)this.filters.get(filterName);
/* 1555:1542 */     if (def == null) {
/* 1556:1543 */       throw new HibernateException("No such filter configured [" + filterName + "]");
/* 1557:     */     }
/* 1558:1545 */     return def;
/* 1559:     */   }
/* 1560:     */   
/* 1561:     */   public boolean containsFetchProfileDefinition(String name)
/* 1562:     */   {
/* 1563:1549 */     return this.fetchProfiles.containsKey(name);
/* 1564:     */   }
/* 1565:     */   
/* 1566:     */   public Set getDefinedFilterNames()
/* 1567:     */   {
/* 1568:1553 */     return this.filters.keySet();
/* 1569:     */   }
/* 1570:     */   
/* 1571:     */   public IdentifierGenerator getIdentifierGenerator(String rootEntityName)
/* 1572:     */   {
/* 1573:1557 */     return (IdentifierGenerator)this.identifierGenerators.get(rootEntityName);
/* 1574:     */   }
/* 1575:     */   
/* 1576:     */   private TransactionFactory transactionFactory()
/* 1577:     */   {
/* 1578:1561 */     return (TransactionFactory)this.serviceRegistry.getService(TransactionFactory.class);
/* 1579:     */   }
/* 1580:     */   
/* 1581:     */   private boolean canAccessTransactionManager()
/* 1582:     */   {
/* 1583:     */     try
/* 1584:     */     {
/* 1585:1566 */       return ((JtaPlatform)this.serviceRegistry.getService(JtaPlatform.class)).retrieveTransactionManager() != null;
/* 1586:     */     }
/* 1587:     */     catch (Exception e) {}
/* 1588:1569 */     return false;
/* 1589:     */   }
/* 1590:     */   
/* 1591:     */   private CurrentSessionContext buildCurrentSessionContext()
/* 1592:     */   {
/* 1593:1574 */     String impl = this.properties.getProperty("hibernate.current_session_context_class");
/* 1594:1576 */     if (impl == null) {
/* 1595:1577 */       if (canAccessTransactionManager()) {
/* 1596:1578 */         impl = "jta";
/* 1597:     */       } else {
/* 1598:1581 */         return null;
/* 1599:     */       }
/* 1600:     */     }
/* 1601:1585 */     if ("jta".equals(impl))
/* 1602:     */     {
/* 1603:1586 */       if (!transactionFactory().compatibleWithJtaSynchronization()) {
/* 1604:1587 */         LOG.autoFlushWillNotWork();
/* 1605:     */       }
/* 1606:1589 */       return new JTASessionContext(this);
/* 1607:     */     }
/* 1608:1591 */     if ("thread".equals(impl)) {
/* 1609:1592 */       return new ThreadLocalSessionContext(this);
/* 1610:     */     }
/* 1611:1594 */     if ("managed".equals(impl)) {
/* 1612:1595 */       return new ManagedSessionContext(this);
/* 1613:     */     }
/* 1614:     */     try
/* 1615:     */     {
/* 1616:1599 */       Class implClass = ReflectHelper.classForName(impl);
/* 1617:1600 */       return (CurrentSessionContext)implClass.getConstructor(new Class[] { SessionFactoryImplementor.class }).newInstance(new Object[] { this });
/* 1618:     */     }
/* 1619:     */     catch (Throwable t)
/* 1620:     */     {
/* 1621:1605 */       LOG.unableToConstructCurrentSessionContext(impl, t);
/* 1622:     */     }
/* 1623:1606 */     return null;
/* 1624:     */   }
/* 1625:     */   
/* 1626:     */   public ServiceRegistryImplementor getServiceRegistry()
/* 1627:     */   {
/* 1628:1613 */     return this.serviceRegistry;
/* 1629:     */   }
/* 1630:     */   
/* 1631:     */   public EntityNotFoundDelegate getEntityNotFoundDelegate()
/* 1632:     */   {
/* 1633:1618 */     return this.sessionFactoryOptions.getEntityNotFoundDelegate();
/* 1634:     */   }
/* 1635:     */   
/* 1636:     */   public SQLFunctionRegistry getSqlFunctionRegistry()
/* 1637:     */   {
/* 1638:1622 */     return this.sqlFunctionRegistry;
/* 1639:     */   }
/* 1640:     */   
/* 1641:     */   public org.hibernate.engine.profile.FetchProfile getFetchProfile(String name)
/* 1642:     */   {
/* 1643:1626 */     return (org.hibernate.engine.profile.FetchProfile)this.fetchProfiles.get(name);
/* 1644:     */   }
/* 1645:     */   
/* 1646:     */   public TypeHelper getTypeHelper()
/* 1647:     */   {
/* 1648:1630 */     return this.typeHelper;
/* 1649:     */   }
/* 1650:     */   
/* 1651:     */   void serialize(ObjectOutputStream oos)
/* 1652:     */     throws IOException
/* 1653:     */   {
/* 1654:1640 */     oos.writeUTF(this.uuid);
/* 1655:1641 */     oos.writeBoolean(this.name != null);
/* 1656:1642 */     if (this.name != null) {
/* 1657:1643 */       oos.writeUTF(this.name);
/* 1658:     */     }
/* 1659:     */   }
/* 1660:     */   
/* 1661:     */   static SessionFactoryImpl deserialize(ObjectInputStream ois)
/* 1662:     */     throws IOException, ClassNotFoundException
/* 1663:     */   {
/* 1664:1656 */     String uuid = ois.readUTF();
/* 1665:1657 */     boolean isNamed = ois.readBoolean();
/* 1666:1658 */     String name = isNamed ? ois.readUTF() : null;
/* 1667:1659 */     Object result = SessionFactoryRegistry.INSTANCE.getSessionFactory(uuid);
/* 1668:1660 */     if (result == null)
/* 1669:     */     {
/* 1670:1661 */       LOG.tracev("Could not locate session factory by uuid [{0}] during session deserialization; trying name", uuid);
/* 1671:1662 */       if (isNamed) {
/* 1672:1663 */         result = SessionFactoryRegistry.INSTANCE.getNamedSessionFactory(name);
/* 1673:     */       }
/* 1674:1665 */       if (result == null) {
/* 1675:1666 */         throw new InvalidObjectException("could not resolve session factory during session deserialization [uuid=" + uuid + ", name=" + name + "]");
/* 1676:     */       }
/* 1677:     */     }
/* 1678:1669 */     return (SessionFactoryImpl)result;
/* 1679:     */   }
/* 1680:     */   
/* 1681:     */   static class SessionBuilderImpl
/* 1682:     */     implements SessionBuilder
/* 1683:     */   {
/* 1684:     */     private final SessionFactoryImpl sessionFactory;
/* 1685:     */     private Interceptor interceptor;
/* 1686:     */     private Connection connection;
/* 1687:     */     private ConnectionReleaseMode connectionReleaseMode;
/* 1688:     */     private boolean autoClose;
/* 1689:1678 */     private boolean autoJoinTransactions = true;
/* 1690:     */     private boolean flushBeforeCompletion;
/* 1691:     */     private String tenantIdentifier;
/* 1692:     */     
/* 1693:     */     SessionBuilderImpl(SessionFactoryImpl sessionFactory)
/* 1694:     */     {
/* 1695:1683 */       this.sessionFactory = sessionFactory;
/* 1696:1684 */       Settings settings = sessionFactory.settings;
/* 1697:     */       
/* 1698:     */ 
/* 1699:1687 */       this.interceptor = sessionFactory.getInterceptor();
/* 1700:1688 */       this.connectionReleaseMode = settings.getConnectionReleaseMode();
/* 1701:1689 */       this.autoClose = settings.isAutoCloseSessionEnabled();
/* 1702:1690 */       this.flushBeforeCompletion = settings.isFlushBeforeCompletionEnabled();
/* 1703:     */     }
/* 1704:     */     
/* 1705:     */     protected TransactionCoordinatorImpl getTransactionCoordinator()
/* 1706:     */     {
/* 1707:1694 */       return null;
/* 1708:     */     }
/* 1709:     */     
/* 1710:     */     public Session openSession()
/* 1711:     */     {
/* 1712:1699 */       return new SessionImpl(this.connection, this.sessionFactory, getTransactionCoordinator(), this.autoJoinTransactions, this.sessionFactory.settings.getRegionFactory().nextTimestamp(), this.interceptor, this.flushBeforeCompletion, this.autoClose, this.connectionReleaseMode, this.tenantIdentifier);
/* 1713:     */     }
/* 1714:     */     
/* 1715:     */     public SessionBuilder interceptor(Interceptor interceptor)
/* 1716:     */     {
/* 1717:1715 */       this.interceptor = interceptor;
/* 1718:1716 */       return this;
/* 1719:     */     }
/* 1720:     */     
/* 1721:     */     public SessionBuilder noInterceptor()
/* 1722:     */     {
/* 1723:1721 */       this.interceptor = EmptyInterceptor.INSTANCE;
/* 1724:1722 */       return this;
/* 1725:     */     }
/* 1726:     */     
/* 1727:     */     public SessionBuilder connection(Connection connection)
/* 1728:     */     {
/* 1729:1727 */       this.connection = connection;
/* 1730:1728 */       return this;
/* 1731:     */     }
/* 1732:     */     
/* 1733:     */     public SessionBuilder connectionReleaseMode(ConnectionReleaseMode connectionReleaseMode)
/* 1734:     */     {
/* 1735:1733 */       this.connectionReleaseMode = connectionReleaseMode;
/* 1736:1734 */       return this;
/* 1737:     */     }
/* 1738:     */     
/* 1739:     */     public SessionBuilder autoJoinTransactions(boolean autoJoinTransactions)
/* 1740:     */     {
/* 1741:1739 */       this.autoJoinTransactions = autoJoinTransactions;
/* 1742:1740 */       return this;
/* 1743:     */     }
/* 1744:     */     
/* 1745:     */     public SessionBuilder autoClose(boolean autoClose)
/* 1746:     */     {
/* 1747:1745 */       this.autoClose = autoClose;
/* 1748:1746 */       return this;
/* 1749:     */     }
/* 1750:     */     
/* 1751:     */     public SessionBuilder flushBeforeCompletion(boolean flushBeforeCompletion)
/* 1752:     */     {
/* 1753:1751 */       this.flushBeforeCompletion = flushBeforeCompletion;
/* 1754:1752 */       return this;
/* 1755:     */     }
/* 1756:     */     
/* 1757:     */     public SessionBuilder tenantIdentifier(String tenantIdentifier)
/* 1758:     */     {
/* 1759:1757 */       this.tenantIdentifier = tenantIdentifier;
/* 1760:1758 */       return this;
/* 1761:     */     }
/* 1762:     */   }
/* 1763:     */   
/* 1764:     */   public static class StatelessSessionBuilderImpl
/* 1765:     */     implements StatelessSessionBuilder
/* 1766:     */   {
/* 1767:     */     private final SessionFactoryImpl sessionFactory;
/* 1768:     */     private Connection connection;
/* 1769:     */     private String tenantIdentifier;
/* 1770:     */     
/* 1771:     */     public StatelessSessionBuilderImpl(SessionFactoryImpl sessionFactory)
/* 1772:     */     {
/* 1773:1768 */       this.sessionFactory = sessionFactory;
/* 1774:     */     }
/* 1775:     */     
/* 1776:     */     public StatelessSession openStatelessSession()
/* 1777:     */     {
/* 1778:1773 */       return new StatelessSessionImpl(this.connection, this.tenantIdentifier, this.sessionFactory);
/* 1779:     */     }
/* 1780:     */     
/* 1781:     */     public StatelessSessionBuilder connection(Connection connection)
/* 1782:     */     {
/* 1783:1778 */       this.connection = connection;
/* 1784:1779 */       return this;
/* 1785:     */     }
/* 1786:     */     
/* 1787:     */     public StatelessSessionBuilder tenantIdentifier(String tenantIdentifier)
/* 1788:     */     {
/* 1789:1784 */       this.tenantIdentifier = tenantIdentifier;
/* 1790:1785 */       return this;
/* 1791:     */     }
/* 1792:     */   }
/* 1793:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.internal.SessionFactoryImpl
 * JD-Core Version:    0.7.0.1
 */