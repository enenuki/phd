/*    1:     */ package org.hibernate.persister.entity;
/*    2:     */ 
/*    3:     */ import java.io.Serializable;
/*    4:     */ import java.sql.PreparedStatement;
/*    5:     */ import java.sql.ResultSet;
/*    6:     */ import java.sql.SQLException;
/*    7:     */ import java.util.ArrayList;
/*    8:     */ import java.util.Arrays;
/*    9:     */ import java.util.Comparator;
/*   10:     */ import java.util.HashMap;
/*   11:     */ import java.util.HashSet;
/*   12:     */ import java.util.Iterator;
/*   13:     */ import java.util.List;
/*   14:     */ import java.util.Map;
/*   15:     */ import java.util.Set;
/*   16:     */ import org.hibernate.AssertionFailure;
/*   17:     */ import org.hibernate.EntityMode;
/*   18:     */ import org.hibernate.FetchMode;
/*   19:     */ import org.hibernate.HibernateException;
/*   20:     */ import org.hibernate.LockMode;
/*   21:     */ import org.hibernate.LockOptions;
/*   22:     */ import org.hibernate.MappingException;
/*   23:     */ import org.hibernate.QueryException;
/*   24:     */ import org.hibernate.StaleObjectStateException;
/*   25:     */ import org.hibernate.StaleStateException;
/*   26:     */ import org.hibernate.bytecode.instrumentation.internal.FieldInterceptionHelper;
/*   27:     */ import org.hibernate.bytecode.instrumentation.spi.FieldInterceptor;
/*   28:     */ import org.hibernate.bytecode.instrumentation.spi.LazyPropertyInitializer;
/*   29:     */ import org.hibernate.cache.spi.CacheKey;
/*   30:     */ import org.hibernate.cache.spi.access.EntityRegionAccessStrategy;
/*   31:     */ import org.hibernate.cache.spi.entry.CacheEntry;
/*   32:     */ import org.hibernate.cache.spi.entry.CacheEntryStructure;
/*   33:     */ import org.hibernate.cache.spi.entry.StructuredCacheEntry;
/*   34:     */ import org.hibernate.cache.spi.entry.UnstructuredCacheEntry;
/*   35:     */ import org.hibernate.cfg.Settings;
/*   36:     */ import org.hibernate.dialect.Dialect;
/*   37:     */ import org.hibernate.dialect.lock.LockingStrategy;
/*   38:     */ import org.hibernate.engine.OptimisticLockStyle;
/*   39:     */ import org.hibernate.engine.internal.Versioning;
/*   40:     */ import org.hibernate.engine.jdbc.batch.internal.BasicBatchKey;
/*   41:     */ import org.hibernate.engine.jdbc.batch.spi.Batch;
/*   42:     */ import org.hibernate.engine.jdbc.spi.JdbcCoordinator;
/*   43:     */ import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
/*   44:     */ import org.hibernate.engine.jdbc.spi.StatementPreparer;
/*   45:     */ import org.hibernate.engine.spi.CascadeStyle;
/*   46:     */ import org.hibernate.engine.spi.CascadingAction;
/*   47:     */ import org.hibernate.engine.spi.EntityEntry;
/*   48:     */ import org.hibernate.engine.spi.EntityKey;
/*   49:     */ import org.hibernate.engine.spi.ExecuteUpdateResultCheckStyle;
/*   50:     */ import org.hibernate.engine.spi.FilterDefinition;
/*   51:     */ import org.hibernate.engine.spi.IdentifierValue;
/*   52:     */ import org.hibernate.engine.spi.LoadQueryInfluencers;
/*   53:     */ import org.hibernate.engine.spi.Mapping;
/*   54:     */ import org.hibernate.engine.spi.PersistenceContext;
/*   55:     */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   56:     */ import org.hibernate.engine.spi.SessionImplementor;
/*   57:     */ import org.hibernate.engine.spi.ValueInclusion;
/*   58:     */ import org.hibernate.engine.spi.VersionValue;
/*   59:     */ import org.hibernate.engine.transaction.spi.TransactionCoordinator;
/*   60:     */ import org.hibernate.id.IdentifierGenerator;
/*   61:     */ import org.hibernate.id.PostInsertIdentifierGenerator;
/*   62:     */ import org.hibernate.id.PostInsertIdentityPersister;
/*   63:     */ import org.hibernate.id.insert.Binder;
/*   64:     */ import org.hibernate.id.insert.InsertGeneratedIdentifierDelegate;
/*   65:     */ import org.hibernate.internal.CoreMessageLogger;
/*   66:     */ import org.hibernate.internal.FilterHelper;
/*   67:     */ import org.hibernate.internal.util.StringHelper;
/*   68:     */ import org.hibernate.internal.util.collections.ArrayHelper;
/*   69:     */ import org.hibernate.jdbc.Expectation;
/*   70:     */ import org.hibernate.jdbc.Expectations;
/*   71:     */ import org.hibernate.jdbc.TooManyRowsAffectedException;
/*   72:     */ import org.hibernate.loader.entity.BatchingEntityLoader;
/*   73:     */ import org.hibernate.loader.entity.CascadeEntityLoader;
/*   74:     */ import org.hibernate.loader.entity.EntityLoader;
/*   75:     */ import org.hibernate.loader.entity.UniqueEntityLoader;
/*   76:     */ import org.hibernate.mapping.Component;
/*   77:     */ import org.hibernate.mapping.KeyValue;
/*   78:     */ import org.hibernate.mapping.PersistentClass;
/*   79:     */ import org.hibernate.mapping.Property;
/*   80:     */ import org.hibernate.mapping.Selectable;
/*   81:     */ import org.hibernate.mapping.Table;
/*   82:     */ import org.hibernate.metadata.ClassMetadata;
/*   83:     */ import org.hibernate.metamodel.binding.AssociationAttributeBinding;
/*   84:     */ import org.hibernate.metamodel.binding.AttributeBinding;
/*   85:     */ import org.hibernate.metamodel.binding.BasicAttributeBinding;
/*   86:     */ import org.hibernate.metamodel.binding.Caching;
/*   87:     */ import org.hibernate.metamodel.binding.EntityBinding;
/*   88:     */ import org.hibernate.metamodel.binding.EntityIdentifier;
/*   89:     */ import org.hibernate.metamodel.binding.HibernateTypeDescriptor;
/*   90:     */ import org.hibernate.metamodel.binding.HierarchyDetails;
/*   91:     */ import org.hibernate.metamodel.binding.SimpleValueBinding;
/*   92:     */ import org.hibernate.metamodel.binding.SingularAttributeBinding;
/*   93:     */ import org.hibernate.metamodel.domain.Attribute;
/*   94:     */ import org.hibernate.metamodel.domain.Entity;
/*   95:     */ import org.hibernate.metamodel.relational.DerivedValue;
/*   96:     */ import org.hibernate.metamodel.relational.Identifier;
/*   97:     */ import org.hibernate.metamodel.relational.PrimaryKey;
/*   98:     */ import org.hibernate.metamodel.relational.SimpleValue;
/*   99:     */ import org.hibernate.metamodel.relational.TableSpecification;
/*  100:     */ import org.hibernate.pretty.MessageHelper;
/*  101:     */ import org.hibernate.property.BackrefPropertyAccessor;
/*  102:     */ import org.hibernate.service.instrumentation.spi.InstrumentationService;
/*  103:     */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  104:     */ import org.hibernate.sql.Alias;
/*  105:     */ import org.hibernate.sql.Delete;
/*  106:     */ import org.hibernate.sql.Insert;
/*  107:     */ import org.hibernate.sql.JoinFragment;
/*  108:     */ import org.hibernate.sql.JoinType;
/*  109:     */ import org.hibernate.sql.Select;
/*  110:     */ import org.hibernate.sql.SelectFragment;
/*  111:     */ import org.hibernate.sql.SimpleSelect;
/*  112:     */ import org.hibernate.sql.Template;
/*  113:     */ import org.hibernate.sql.Update;
/*  114:     */ import org.hibernate.stat.Statistics;
/*  115:     */ import org.hibernate.stat.spi.StatisticsImplementor;
/*  116:     */ import org.hibernate.tuple.IdentifierProperty;
/*  117:     */ import org.hibernate.tuple.StandardProperty;
/*  118:     */ import org.hibernate.tuple.VersionProperty;
/*  119:     */ import org.hibernate.tuple.entity.EntityMetamodel;
/*  120:     */ import org.hibernate.tuple.entity.EntityTuplizer;
/*  121:     */ import org.hibernate.type.AssociationType;
/*  122:     */ import org.hibernate.type.CompositeType;
/*  123:     */ import org.hibernate.type.EntityType;
/*  124:     */ import org.hibernate.type.Type;
/*  125:     */ import org.hibernate.type.TypeHelper;
/*  126:     */ import org.hibernate.type.VersionType;
/*  127:     */ import org.jboss.logging.Logger;
/*  128:     */ 
/*  129:     */ public abstract class AbstractEntityPersister
/*  130:     */   implements OuterJoinLoadable, Queryable, ClassMetadata, UniqueKeyLoadable, SQLLoadable, LazyPropertyInitializer, PostInsertIdentityPersister, Lockable
/*  131:     */ {
/*  132: 137 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, AbstractEntityPersister.class.getName());
/*  133:     */   public static final String ENTITY_CLASS = "class";
/*  134:     */   private final SessionFactoryImplementor factory;
/*  135:     */   private final EntityRegionAccessStrategy cacheAccessStrategy;
/*  136:     */   private final boolean isLazyPropertiesCacheable;
/*  137:     */   private final CacheEntryStructure cacheEntryStructure;
/*  138:     */   private final EntityMetamodel entityMetamodel;
/*  139:     */   private final EntityTuplizer entityTuplizer;
/*  140:     */   private final String[] rootTableKeyColumnNames;
/*  141:     */   private final String[] rootTableKeyColumnReaders;
/*  142:     */   private final String[] rootTableKeyColumnReaderTemplates;
/*  143:     */   private final String[] identifierAliases;
/*  144:     */   private final int identifierColumnSpan;
/*  145:     */   private final String versionColumnName;
/*  146:     */   private final boolean hasFormulaProperties;
/*  147:     */   private final int batchSize;
/*  148:     */   private final boolean hasSubselectLoadableCollections;
/*  149:     */   protected final String rowIdName;
/*  150:     */   private final Set lazyProperties;
/*  151:     */   private final String sqlWhereString;
/*  152:     */   private final String sqlWhereStringTemplate;
/*  153:     */   private final int[] propertyColumnSpans;
/*  154:     */   private final String[] propertySubclassNames;
/*  155:     */   private final String[][] propertyColumnAliases;
/*  156:     */   private final String[][] propertyColumnNames;
/*  157:     */   private final String[][] propertyColumnFormulaTemplates;
/*  158:     */   private final String[][] propertyColumnReaderTemplates;
/*  159:     */   private final String[][] propertyColumnWriters;
/*  160:     */   private final boolean[][] propertyColumnUpdateable;
/*  161:     */   private final boolean[][] propertyColumnInsertable;
/*  162:     */   private final boolean[] propertyUniqueness;
/*  163:     */   private final boolean[] propertySelectable;
/*  164:     */   private final String[] lazyPropertyNames;
/*  165:     */   private final int[] lazyPropertyNumbers;
/*  166:     */   private final Type[] lazyPropertyTypes;
/*  167:     */   private final String[][] lazyPropertyColumnAliases;
/*  168:     */   private final String[] subclassPropertyNameClosure;
/*  169:     */   private final String[] subclassPropertySubclassNameClosure;
/*  170:     */   private final Type[] subclassPropertyTypeClosure;
/*  171:     */   private final String[][] subclassPropertyFormulaTemplateClosure;
/*  172:     */   private final String[][] subclassPropertyColumnNameClosure;
/*  173:     */   private final String[][] subclassPropertyColumnReaderClosure;
/*  174:     */   private final String[][] subclassPropertyColumnReaderTemplateClosure;
/*  175:     */   private final FetchMode[] subclassPropertyFetchModeClosure;
/*  176:     */   private final boolean[] subclassPropertyNullabilityClosure;
/*  177:     */   private final boolean[] propertyDefinedOnSubclass;
/*  178:     */   private final int[][] subclassPropertyColumnNumberClosure;
/*  179:     */   private final int[][] subclassPropertyFormulaNumberClosure;
/*  180:     */   private final CascadeStyle[] subclassPropertyCascadeStyleClosure;
/*  181:     */   private final String[] subclassColumnClosure;
/*  182:     */   private final boolean[] subclassColumnLazyClosure;
/*  183:     */   private final String[] subclassColumnAliasClosure;
/*  184:     */   private final boolean[] subclassColumnSelectableClosure;
/*  185:     */   private final String[] subclassColumnReaderTemplateClosure;
/*  186:     */   private final String[] subclassFormulaClosure;
/*  187:     */   private final String[] subclassFormulaTemplateClosure;
/*  188:     */   private final String[] subclassFormulaAliasClosure;
/*  189:     */   private final boolean[] subclassFormulaLazyClosure;
/*  190:     */   private final FilterHelper filterHelper;
/*  191: 218 */   private final Set affectingFetchProfileNames = new HashSet();
/*  192: 220 */   private final Map uniqueKeyLoaders = new HashMap();
/*  193: 221 */   private final Map lockers = new HashMap();
/*  194: 222 */   private final Map loaders = new HashMap();
/*  195:     */   private String sqlVersionSelectString;
/*  196:     */   private String sqlSnapshotSelectString;
/*  197:     */   private String sqlLazySelectString;
/*  198:     */   private String sqlIdentityInsertString;
/*  199:     */   private String sqlUpdateByRowIdString;
/*  200:     */   private String sqlLazyUpdateByRowIdString;
/*  201:     */   private String[] sqlDeleteStrings;
/*  202:     */   private String[] sqlInsertStrings;
/*  203:     */   private String[] sqlUpdateStrings;
/*  204:     */   private String[] sqlLazyUpdateStrings;
/*  205:     */   private String sqlInsertGeneratedValuesSelectString;
/*  206:     */   private String sqlUpdateGeneratedValuesSelectString;
/*  207:     */   protected boolean[] insertCallable;
/*  208:     */   protected boolean[] updateCallable;
/*  209:     */   protected boolean[] deleteCallable;
/*  210:     */   protected String[] customSQLInsert;
/*  211:     */   protected String[] customSQLUpdate;
/*  212:     */   protected String[] customSQLDelete;
/*  213:     */   protected ExecuteUpdateResultCheckStyle[] insertResultCheckStyles;
/*  214:     */   protected ExecuteUpdateResultCheckStyle[] updateResultCheckStyles;
/*  215:     */   protected ExecuteUpdateResultCheckStyle[] deleteResultCheckStyles;
/*  216:     */   private InsertGeneratedIdentifierDelegate identityDelegate;
/*  217:     */   private boolean[] tableHasColumns;
/*  218:     */   private final String loaderName;
/*  219:     */   private UniqueEntityLoader queryLoader;
/*  220:     */   private final String temporaryIdTableName;
/*  221:     */   private final String temporaryIdTableDDL;
/*  222: 263 */   private final Map subclassPropertyAliases = new HashMap();
/*  223: 264 */   private final Map subclassPropertyColumnNames = new HashMap();
/*  224:     */   protected final BasicEntityPropertyMapping propertyMapping;
/*  225:     */   private static final String DISCRIMINATOR_ALIAS = "clazz_";
/*  226:     */   private DiscriminatorMetadata discriminatorMetadata;
/*  227:     */   private BasicBatchKey inserBatchKey;
/*  228:     */   private BasicBatchKey updateBatchKey;
/*  229:     */   private BasicBatchKey deleteBatchKey;
/*  230:     */   
/*  231:     */   protected void addDiscriminatorToInsert(Insert insert) {}
/*  232:     */   
/*  233:     */   protected void addDiscriminatorToSelect(SelectFragment select, String name, String suffix) {}
/*  234:     */   
/*  235:     */   protected abstract int[] getSubclassColumnTableNumberClosure();
/*  236:     */   
/*  237:     */   protected abstract int[] getSubclassFormulaTableNumberClosure();
/*  238:     */   
/*  239:     */   public abstract String getSubclassTableName(int paramInt);
/*  240:     */   
/*  241:     */   protected abstract String[] getSubclassTableKeyColumns(int paramInt);
/*  242:     */   
/*  243:     */   protected abstract boolean isClassOrSuperclassTable(int paramInt);
/*  244:     */   
/*  245:     */   protected abstract int getSubclassTableSpan();
/*  246:     */   
/*  247:     */   protected abstract int getTableSpan();
/*  248:     */   
/*  249:     */   protected abstract boolean isTableCascadeDeleteEnabled(int paramInt);
/*  250:     */   
/*  251:     */   protected abstract String getTableName(int paramInt);
/*  252:     */   
/*  253:     */   protected abstract String[] getKeyColumns(int paramInt);
/*  254:     */   
/*  255:     */   protected abstract boolean isPropertyOfTable(int paramInt1, int paramInt2);
/*  256:     */   
/*  257:     */   protected abstract int[] getPropertyTableNumbersInSelect();
/*  258:     */   
/*  259:     */   protected abstract int[] getPropertyTableNumbers();
/*  260:     */   
/*  261:     */   protected abstract int getSubclassPropertyTableNumber(int paramInt);
/*  262:     */   
/*  263:     */   protected abstract String filterFragment(String paramString)
/*  264:     */     throws MappingException;
/*  265:     */   
/*  266:     */   public String getDiscriminatorColumnName()
/*  267:     */   {
/*  268: 305 */     return "clazz_";
/*  269:     */   }
/*  270:     */   
/*  271:     */   public String getDiscriminatorColumnReaders()
/*  272:     */   {
/*  273: 309 */     return "clazz_";
/*  274:     */   }
/*  275:     */   
/*  276:     */   public String getDiscriminatorColumnReaderTemplate()
/*  277:     */   {
/*  278: 313 */     return "clazz_";
/*  279:     */   }
/*  280:     */   
/*  281:     */   protected String getDiscriminatorAlias()
/*  282:     */   {
/*  283: 317 */     return "clazz_";
/*  284:     */   }
/*  285:     */   
/*  286:     */   protected String getDiscriminatorFormulaTemplate()
/*  287:     */   {
/*  288: 321 */     return null;
/*  289:     */   }
/*  290:     */   
/*  291:     */   protected boolean isInverseTable(int j)
/*  292:     */   {
/*  293: 325 */     return false;
/*  294:     */   }
/*  295:     */   
/*  296:     */   protected boolean isNullableTable(int j)
/*  297:     */   {
/*  298: 329 */     return false;
/*  299:     */   }
/*  300:     */   
/*  301:     */   protected boolean isNullableSubclassTable(int j)
/*  302:     */   {
/*  303: 333 */     return false;
/*  304:     */   }
/*  305:     */   
/*  306:     */   protected boolean isInverseSubclassTable(int j)
/*  307:     */   {
/*  308: 337 */     return false;
/*  309:     */   }
/*  310:     */   
/*  311:     */   public boolean isSubclassEntityName(String entityName)
/*  312:     */   {
/*  313: 341 */     return this.entityMetamodel.getSubclassEntityNames().contains(entityName);
/*  314:     */   }
/*  315:     */   
/*  316:     */   private boolean[] getTableHasColumns()
/*  317:     */   {
/*  318: 345 */     return this.tableHasColumns;
/*  319:     */   }
/*  320:     */   
/*  321:     */   public String[] getRootTableKeyColumnNames()
/*  322:     */   {
/*  323: 349 */     return this.rootTableKeyColumnNames;
/*  324:     */   }
/*  325:     */   
/*  326:     */   protected String[] getSQLUpdateByRowIdStrings()
/*  327:     */   {
/*  328: 353 */     if (this.sqlUpdateByRowIdString == null) {
/*  329: 354 */       throw new AssertionFailure("no update by row id");
/*  330:     */     }
/*  331: 356 */     String[] result = new String[getTableSpan() + 1];
/*  332: 357 */     result[0] = this.sqlUpdateByRowIdString;
/*  333: 358 */     System.arraycopy(this.sqlUpdateStrings, 0, result, 1, getTableSpan());
/*  334: 359 */     return result;
/*  335:     */   }
/*  336:     */   
/*  337:     */   protected String[] getSQLLazyUpdateByRowIdStrings()
/*  338:     */   {
/*  339: 363 */     if (this.sqlLazyUpdateByRowIdString == null) {
/*  340: 364 */       throw new AssertionFailure("no update by row id");
/*  341:     */     }
/*  342: 366 */     String[] result = new String[getTableSpan()];
/*  343: 367 */     result[0] = this.sqlLazyUpdateByRowIdString;
/*  344: 368 */     for (int i = 1; i < getTableSpan(); i++) {
/*  345: 369 */       result[i] = this.sqlLazyUpdateStrings[i];
/*  346:     */     }
/*  347: 371 */     return result;
/*  348:     */   }
/*  349:     */   
/*  350:     */   protected String getSQLSnapshotSelectString()
/*  351:     */   {
/*  352: 375 */     return this.sqlSnapshotSelectString;
/*  353:     */   }
/*  354:     */   
/*  355:     */   protected String getSQLLazySelectString()
/*  356:     */   {
/*  357: 379 */     return this.sqlLazySelectString;
/*  358:     */   }
/*  359:     */   
/*  360:     */   protected String[] getSQLDeleteStrings()
/*  361:     */   {
/*  362: 383 */     return this.sqlDeleteStrings;
/*  363:     */   }
/*  364:     */   
/*  365:     */   protected String[] getSQLInsertStrings()
/*  366:     */   {
/*  367: 387 */     return this.sqlInsertStrings;
/*  368:     */   }
/*  369:     */   
/*  370:     */   protected String[] getSQLUpdateStrings()
/*  371:     */   {
/*  372: 391 */     return this.sqlUpdateStrings;
/*  373:     */   }
/*  374:     */   
/*  375:     */   protected String[] getSQLLazyUpdateStrings()
/*  376:     */   {
/*  377: 395 */     return this.sqlLazyUpdateStrings;
/*  378:     */   }
/*  379:     */   
/*  380:     */   protected String getSQLIdentityInsertString()
/*  381:     */   {
/*  382: 404 */     return this.sqlIdentityInsertString;
/*  383:     */   }
/*  384:     */   
/*  385:     */   protected String getVersionSelectString()
/*  386:     */   {
/*  387: 408 */     return this.sqlVersionSelectString;
/*  388:     */   }
/*  389:     */   
/*  390:     */   protected boolean isInsertCallable(int j)
/*  391:     */   {
/*  392: 412 */     return this.insertCallable[j];
/*  393:     */   }
/*  394:     */   
/*  395:     */   protected boolean isUpdateCallable(int j)
/*  396:     */   {
/*  397: 416 */     return this.updateCallable[j];
/*  398:     */   }
/*  399:     */   
/*  400:     */   protected boolean isDeleteCallable(int j)
/*  401:     */   {
/*  402: 420 */     return this.deleteCallable[j];
/*  403:     */   }
/*  404:     */   
/*  405:     */   protected boolean isSubclassPropertyDeferred(String propertyName, String entityName)
/*  406:     */   {
/*  407: 424 */     return false;
/*  408:     */   }
/*  409:     */   
/*  410:     */   protected boolean isSubclassTableSequentialSelect(int j)
/*  411:     */   {
/*  412: 428 */     return false;
/*  413:     */   }
/*  414:     */   
/*  415:     */   public boolean hasSequentialSelect()
/*  416:     */   {
/*  417: 432 */     return false;
/*  418:     */   }
/*  419:     */   
/*  420:     */   protected boolean[] getTableUpdateNeeded(int[] dirtyProperties, boolean hasDirtyCollection)
/*  421:     */   {
/*  422: 448 */     if (dirtyProperties == null) {
/*  423: 449 */       return getTableHasColumns();
/*  424:     */     }
/*  425: 452 */     boolean[] updateability = getPropertyUpdateability();
/*  426: 453 */     int[] propertyTableNumbers = getPropertyTableNumbers();
/*  427: 454 */     boolean[] tableUpdateNeeded = new boolean[getTableSpan()];
/*  428: 455 */     for (int i = 0; i < dirtyProperties.length; i++)
/*  429:     */     {
/*  430: 456 */       int property = dirtyProperties[i];
/*  431: 457 */       int table = propertyTableNumbers[property];
/*  432: 458 */       tableUpdateNeeded[table] = ((tableUpdateNeeded[table] != 0) || ((getPropertyColumnSpan(property) > 0) && (updateability[property] != 0)) ? 1 : false);
/*  433:     */     }
/*  434: 461 */     if (isVersioned()) {
/*  435: 462 */       tableUpdateNeeded[0] = ((tableUpdateNeeded[0] != 0) || (Versioning.isVersionIncrementRequired(dirtyProperties, hasDirtyCollection, getPropertyVersionability())) ? 1 : false);
/*  436:     */     }
/*  437: 465 */     return tableUpdateNeeded;
/*  438:     */   }
/*  439:     */   
/*  440:     */   public boolean hasRowId()
/*  441:     */   {
/*  442: 470 */     return this.rowIdName != null;
/*  443:     */   }
/*  444:     */   
/*  445:     */   protected boolean[][] getPropertyColumnUpdateable()
/*  446:     */   {
/*  447: 474 */     return this.propertyColumnUpdateable;
/*  448:     */   }
/*  449:     */   
/*  450:     */   protected boolean[][] getPropertyColumnInsertable()
/*  451:     */   {
/*  452: 478 */     return this.propertyColumnInsertable;
/*  453:     */   }
/*  454:     */   
/*  455:     */   protected boolean[] getPropertySelectable()
/*  456:     */   {
/*  457: 482 */     return this.propertySelectable;
/*  458:     */   }
/*  459:     */   
/*  460:     */   public AbstractEntityPersister(PersistentClass persistentClass, EntityRegionAccessStrategy cacheAccessStrategy, SessionFactoryImplementor factory)
/*  461:     */     throws HibernateException
/*  462:     */   {
/*  463: 491 */     this.factory = factory;
/*  464: 492 */     this.cacheAccessStrategy = cacheAccessStrategy;
/*  465: 493 */     this.isLazyPropertiesCacheable = persistentClass.isLazyPropertiesCacheable();
/*  466: 494 */     this.cacheEntryStructure = (factory.getSettings().isStructuredCacheEntriesEnabled() ? new StructuredCacheEntry(this) : new UnstructuredCacheEntry());
/*  467:     */     
/*  468:     */ 
/*  469:     */ 
/*  470: 498 */     this.entityMetamodel = new EntityMetamodel(persistentClass, factory);
/*  471: 499 */     this.entityTuplizer = this.entityMetamodel.getTuplizer();
/*  472:     */     
/*  473:     */ 
/*  474: 502 */     int batch = persistentClass.getBatchSize();
/*  475: 503 */     if (batch == -1) {
/*  476: 504 */       batch = factory.getSettings().getDefaultBatchFetchSize();
/*  477:     */     }
/*  478: 506 */     this.batchSize = batch;
/*  479: 507 */     this.hasSubselectLoadableCollections = persistentClass.hasSubselectLoadableCollections();
/*  480:     */     
/*  481: 509 */     this.propertyMapping = new BasicEntityPropertyMapping(this);
/*  482:     */     
/*  483:     */ 
/*  484:     */ 
/*  485: 513 */     this.identifierColumnSpan = persistentClass.getIdentifier().getColumnSpan();
/*  486: 514 */     this.rootTableKeyColumnNames = new String[this.identifierColumnSpan];
/*  487: 515 */     this.rootTableKeyColumnReaders = new String[this.identifierColumnSpan];
/*  488: 516 */     this.rootTableKeyColumnReaderTemplates = new String[this.identifierColumnSpan];
/*  489: 517 */     this.identifierAliases = new String[this.identifierColumnSpan];
/*  490:     */     
/*  491: 519 */     this.rowIdName = persistentClass.getRootTable().getRowId();
/*  492:     */     
/*  493: 521 */     this.loaderName = persistentClass.getLoaderName();
/*  494:     */     
/*  495: 523 */     Iterator iter = persistentClass.getIdentifier().getColumnIterator();
/*  496: 524 */     int i = 0;
/*  497: 525 */     while (iter.hasNext())
/*  498:     */     {
/*  499: 526 */       org.hibernate.mapping.Column col = (org.hibernate.mapping.Column)iter.next();
/*  500: 527 */       this.rootTableKeyColumnNames[i] = col.getQuotedName(factory.getDialect());
/*  501: 528 */       this.rootTableKeyColumnReaders[i] = col.getReadExpr(factory.getDialect());
/*  502: 529 */       this.rootTableKeyColumnReaderTemplates[i] = col.getTemplate(factory.getDialect(), factory.getSqlFunctionRegistry());
/*  503: 530 */       this.identifierAliases[i] = col.getAlias(factory.getDialect(), persistentClass.getRootTable());
/*  504: 531 */       i++;
/*  505:     */     }
/*  506: 536 */     if (persistentClass.isVersioned()) {
/*  507: 537 */       this.versionColumnName = ((org.hibernate.mapping.Column)persistentClass.getVersion().getColumnIterator().next()).getQuotedName(factory.getDialect());
/*  508:     */     } else {
/*  509: 540 */       this.versionColumnName = null;
/*  510:     */     }
/*  511: 545 */     this.sqlWhereString = (StringHelper.isNotEmpty(persistentClass.getWhere()) ? "( " + persistentClass.getWhere() + ") " : null);
/*  512: 546 */     this.sqlWhereStringTemplate = (this.sqlWhereString == null ? null : Template.renderWhereStringTemplate(this.sqlWhereString, factory.getDialect(), factory.getSqlFunctionRegistry()));
/*  513:     */     
/*  514:     */ 
/*  515:     */ 
/*  516:     */ 
/*  517:     */ 
/*  518: 552 */     boolean lazyAvailable = isInstrumented();
/*  519:     */     
/*  520: 554 */     int hydrateSpan = this.entityMetamodel.getPropertySpan();
/*  521: 555 */     this.propertyColumnSpans = new int[hydrateSpan];
/*  522: 556 */     this.propertySubclassNames = new String[hydrateSpan];
/*  523: 557 */     this.propertyColumnAliases = new String[hydrateSpan][];
/*  524: 558 */     this.propertyColumnNames = new String[hydrateSpan][];
/*  525: 559 */     this.propertyColumnFormulaTemplates = new String[hydrateSpan][];
/*  526: 560 */     this.propertyColumnReaderTemplates = new String[hydrateSpan][];
/*  527: 561 */     this.propertyColumnWriters = new String[hydrateSpan][];
/*  528: 562 */     this.propertyUniqueness = new boolean[hydrateSpan];
/*  529: 563 */     this.propertySelectable = new boolean[hydrateSpan];
/*  530: 564 */     this.propertyColumnUpdateable = new boolean[hydrateSpan][];
/*  531: 565 */     this.propertyColumnInsertable = new boolean[hydrateSpan][];
/*  532: 566 */     HashSet thisClassProperties = new HashSet();
/*  533:     */     
/*  534: 568 */     this.lazyProperties = new HashSet();
/*  535: 569 */     ArrayList lazyNames = new ArrayList();
/*  536: 570 */     ArrayList lazyNumbers = new ArrayList();
/*  537: 571 */     ArrayList lazyTypes = new ArrayList();
/*  538: 572 */     ArrayList lazyColAliases = new ArrayList();
/*  539:     */     
/*  540: 574 */     iter = persistentClass.getPropertyClosureIterator();
/*  541: 575 */     i = 0;
/*  542: 576 */     boolean foundFormula = false;
/*  543: 577 */     while (iter.hasNext())
/*  544:     */     {
/*  545: 578 */       Property prop = (Property)iter.next();
/*  546: 579 */       thisClassProperties.add(prop);
/*  547:     */       
/*  548: 581 */       int span = prop.getColumnSpan();
/*  549: 582 */       this.propertyColumnSpans[i] = span;
/*  550: 583 */       this.propertySubclassNames[i] = prop.getPersistentClass().getEntityName();
/*  551: 584 */       String[] colNames = new String[span];
/*  552: 585 */       String[] colAliases = new String[span];
/*  553: 586 */       String[] colReaderTemplates = new String[span];
/*  554: 587 */       String[] colWriters = new String[span];
/*  555: 588 */       String[] formulaTemplates = new String[span];
/*  556: 589 */       Iterator colIter = prop.getColumnIterator();
/*  557: 590 */       int k = 0;
/*  558: 591 */       while (colIter.hasNext())
/*  559:     */       {
/*  560: 592 */         Selectable thing = (Selectable)colIter.next();
/*  561: 593 */         colAliases[k] = thing.getAlias(factory.getDialect(), prop.getValue().getTable());
/*  562: 594 */         if (thing.isFormula())
/*  563:     */         {
/*  564: 595 */           foundFormula = true;
/*  565: 596 */           formulaTemplates[k] = thing.getTemplate(factory.getDialect(), factory.getSqlFunctionRegistry());
/*  566:     */         }
/*  567:     */         else
/*  568:     */         {
/*  569: 599 */           org.hibernate.mapping.Column col = (org.hibernate.mapping.Column)thing;
/*  570: 600 */           colNames[k] = col.getQuotedName(factory.getDialect());
/*  571: 601 */           colReaderTemplates[k] = col.getTemplate(factory.getDialect(), factory.getSqlFunctionRegistry());
/*  572: 602 */           colWriters[k] = col.getWriteExpr();
/*  573:     */         }
/*  574: 604 */         k++;
/*  575:     */       }
/*  576: 606 */       this.propertyColumnNames[i] = colNames;
/*  577: 607 */       this.propertyColumnFormulaTemplates[i] = formulaTemplates;
/*  578: 608 */       this.propertyColumnReaderTemplates[i] = colReaderTemplates;
/*  579: 609 */       this.propertyColumnWriters[i] = colWriters;
/*  580: 610 */       this.propertyColumnAliases[i] = colAliases;
/*  581: 612 */       if ((lazyAvailable) && (prop.isLazy()))
/*  582:     */       {
/*  583: 613 */         this.lazyProperties.add(prop.getName());
/*  584: 614 */         lazyNames.add(prop.getName());
/*  585: 615 */         lazyNumbers.add(Integer.valueOf(i));
/*  586: 616 */         lazyTypes.add(prop.getValue().getType());
/*  587: 617 */         lazyColAliases.add(colAliases);
/*  588:     */       }
/*  589: 620 */       this.propertyColumnUpdateable[i] = prop.getValue().getColumnUpdateability();
/*  590: 621 */       this.propertyColumnInsertable[i] = prop.getValue().getColumnInsertability();
/*  591:     */       
/*  592: 623 */       this.propertySelectable[i] = prop.isSelectable();
/*  593:     */       
/*  594: 625 */       this.propertyUniqueness[i] = prop.getValue().isAlternateUniqueKey();
/*  595:     */       
/*  596: 627 */       i++;
/*  597:     */     }
/*  598: 630 */     this.hasFormulaProperties = foundFormula;
/*  599: 631 */     this.lazyPropertyColumnAliases = ArrayHelper.to2DStringArray(lazyColAliases);
/*  600: 632 */     this.lazyPropertyNames = ArrayHelper.toStringArray(lazyNames);
/*  601: 633 */     this.lazyPropertyNumbers = ArrayHelper.toIntArray(lazyNumbers);
/*  602: 634 */     this.lazyPropertyTypes = ArrayHelper.toTypeArray(lazyTypes);
/*  603:     */     
/*  604:     */ 
/*  605:     */ 
/*  606: 638 */     ArrayList columns = new ArrayList();
/*  607: 639 */     ArrayList columnsLazy = new ArrayList();
/*  608: 640 */     ArrayList columnReaderTemplates = new ArrayList();
/*  609: 641 */     ArrayList aliases = new ArrayList();
/*  610: 642 */     ArrayList formulas = new ArrayList();
/*  611: 643 */     ArrayList formulaAliases = new ArrayList();
/*  612: 644 */     ArrayList formulaTemplates = new ArrayList();
/*  613: 645 */     ArrayList formulasLazy = new ArrayList();
/*  614: 646 */     ArrayList types = new ArrayList();
/*  615: 647 */     ArrayList names = new ArrayList();
/*  616: 648 */     ArrayList classes = new ArrayList();
/*  617: 649 */     ArrayList templates = new ArrayList();
/*  618: 650 */     ArrayList propColumns = new ArrayList();
/*  619: 651 */     ArrayList propColumnReaders = new ArrayList();
/*  620: 652 */     ArrayList propColumnReaderTemplates = new ArrayList();
/*  621: 653 */     ArrayList joinedFetchesList = new ArrayList();
/*  622: 654 */     ArrayList cascades = new ArrayList();
/*  623: 655 */     ArrayList definedBySubclass = new ArrayList();
/*  624: 656 */     ArrayList propColumnNumbers = new ArrayList();
/*  625: 657 */     ArrayList propFormulaNumbers = new ArrayList();
/*  626: 658 */     ArrayList columnSelectables = new ArrayList();
/*  627: 659 */     ArrayList propNullables = new ArrayList();
/*  628:     */     
/*  629: 661 */     iter = persistentClass.getSubclassPropertyClosureIterator();
/*  630: 662 */     while (iter.hasNext())
/*  631:     */     {
/*  632: 663 */       Property prop = (Property)iter.next();
/*  633: 664 */       names.add(prop.getName());
/*  634: 665 */       classes.add(prop.getPersistentClass().getEntityName());
/*  635: 666 */       boolean isDefinedBySubclass = !thisClassProperties.contains(prop);
/*  636: 667 */       definedBySubclass.add(Boolean.valueOf(isDefinedBySubclass));
/*  637: 668 */       propNullables.add(Boolean.valueOf((prop.isOptional()) || (isDefinedBySubclass)));
/*  638: 669 */       types.add(prop.getType());
/*  639:     */       
/*  640: 671 */       Iterator colIter = prop.getColumnIterator();
/*  641: 672 */       String[] cols = new String[prop.getColumnSpan()];
/*  642: 673 */       String[] readers = new String[prop.getColumnSpan()];
/*  643: 674 */       String[] readerTemplates = new String[prop.getColumnSpan()];
/*  644: 675 */       String[] forms = new String[prop.getColumnSpan()];
/*  645: 676 */       int[] colnos = new int[prop.getColumnSpan()];
/*  646: 677 */       int[] formnos = new int[prop.getColumnSpan()];
/*  647: 678 */       int l = 0;
/*  648: 679 */       Boolean lazy = Boolean.valueOf((prop.isLazy()) && (lazyAvailable));
/*  649: 680 */       while (colIter.hasNext())
/*  650:     */       {
/*  651: 681 */         Selectable thing = (Selectable)colIter.next();
/*  652: 682 */         if (thing.isFormula())
/*  653:     */         {
/*  654: 683 */           String template = thing.getTemplate(factory.getDialect(), factory.getSqlFunctionRegistry());
/*  655: 684 */           formnos[l] = formulaTemplates.size();
/*  656: 685 */           colnos[l] = -1;
/*  657: 686 */           formulaTemplates.add(template);
/*  658: 687 */           forms[l] = template;
/*  659: 688 */           formulas.add(thing.getText(factory.getDialect()));
/*  660: 689 */           formulaAliases.add(thing.getAlias(factory.getDialect()));
/*  661: 690 */           formulasLazy.add(lazy);
/*  662:     */         }
/*  663:     */         else
/*  664:     */         {
/*  665: 693 */           org.hibernate.mapping.Column col = (org.hibernate.mapping.Column)thing;
/*  666: 694 */           String colName = col.getQuotedName(factory.getDialect());
/*  667: 695 */           colnos[l] = columns.size();
/*  668: 696 */           formnos[l] = -1;
/*  669: 697 */           columns.add(colName);
/*  670: 698 */           cols[l] = colName;
/*  671: 699 */           aliases.add(thing.getAlias(factory.getDialect(), prop.getValue().getTable()));
/*  672: 700 */           columnsLazy.add(lazy);
/*  673: 701 */           columnSelectables.add(Boolean.valueOf(prop.isSelectable()));
/*  674:     */           
/*  675: 703 */           readers[l] = col.getReadExpr(factory.getDialect());
/*  676: 704 */           String readerTemplate = col.getTemplate(factory.getDialect(), factory.getSqlFunctionRegistry());
/*  677: 705 */           readerTemplates[l] = readerTemplate;
/*  678: 706 */           columnReaderTemplates.add(readerTemplate);
/*  679:     */         }
/*  680: 708 */         l++;
/*  681:     */       }
/*  682: 710 */       propColumns.add(cols);
/*  683: 711 */       propColumnReaders.add(readers);
/*  684: 712 */       propColumnReaderTemplates.add(readerTemplates);
/*  685: 713 */       templates.add(forms);
/*  686: 714 */       propColumnNumbers.add(colnos);
/*  687: 715 */       propFormulaNumbers.add(formnos);
/*  688:     */       
/*  689: 717 */       joinedFetchesList.add(prop.getValue().getFetchMode());
/*  690: 718 */       cascades.add(prop.getCascadeStyle());
/*  691:     */     }
/*  692: 720 */     this.subclassColumnClosure = ArrayHelper.toStringArray(columns);
/*  693: 721 */     this.subclassColumnAliasClosure = ArrayHelper.toStringArray(aliases);
/*  694: 722 */     this.subclassColumnLazyClosure = ArrayHelper.toBooleanArray(columnsLazy);
/*  695: 723 */     this.subclassColumnSelectableClosure = ArrayHelper.toBooleanArray(columnSelectables);
/*  696: 724 */     this.subclassColumnReaderTemplateClosure = ArrayHelper.toStringArray(columnReaderTemplates);
/*  697:     */     
/*  698: 726 */     this.subclassFormulaClosure = ArrayHelper.toStringArray(formulas);
/*  699: 727 */     this.subclassFormulaTemplateClosure = ArrayHelper.toStringArray(formulaTemplates);
/*  700: 728 */     this.subclassFormulaAliasClosure = ArrayHelper.toStringArray(formulaAliases);
/*  701: 729 */     this.subclassFormulaLazyClosure = ArrayHelper.toBooleanArray(formulasLazy);
/*  702:     */     
/*  703: 731 */     this.subclassPropertyNameClosure = ArrayHelper.toStringArray(names);
/*  704: 732 */     this.subclassPropertySubclassNameClosure = ArrayHelper.toStringArray(classes);
/*  705: 733 */     this.subclassPropertyTypeClosure = ArrayHelper.toTypeArray(types);
/*  706: 734 */     this.subclassPropertyNullabilityClosure = ArrayHelper.toBooleanArray(propNullables);
/*  707: 735 */     this.subclassPropertyFormulaTemplateClosure = ArrayHelper.to2DStringArray(templates);
/*  708: 736 */     this.subclassPropertyColumnNameClosure = ArrayHelper.to2DStringArray(propColumns);
/*  709: 737 */     this.subclassPropertyColumnReaderClosure = ArrayHelper.to2DStringArray(propColumnReaders);
/*  710: 738 */     this.subclassPropertyColumnReaderTemplateClosure = ArrayHelper.to2DStringArray(propColumnReaderTemplates);
/*  711: 739 */     this.subclassPropertyColumnNumberClosure = ArrayHelper.to2DIntArray(propColumnNumbers);
/*  712: 740 */     this.subclassPropertyFormulaNumberClosure = ArrayHelper.to2DIntArray(propFormulaNumbers);
/*  713:     */     
/*  714: 742 */     this.subclassPropertyCascadeStyleClosure = new CascadeStyle[cascades.size()];
/*  715: 743 */     iter = cascades.iterator();
/*  716: 744 */     int j = 0;
/*  717: 745 */     while (iter.hasNext()) {
/*  718: 746 */       this.subclassPropertyCascadeStyleClosure[(j++)] = ((CascadeStyle)iter.next());
/*  719:     */     }
/*  720: 748 */     this.subclassPropertyFetchModeClosure = new FetchMode[joinedFetchesList.size()];
/*  721: 749 */     iter = joinedFetchesList.iterator();
/*  722: 750 */     j = 0;
/*  723: 751 */     while (iter.hasNext()) {
/*  724: 752 */       this.subclassPropertyFetchModeClosure[(j++)] = ((FetchMode)iter.next());
/*  725:     */     }
/*  726: 755 */     this.propertyDefinedOnSubclass = new boolean[definedBySubclass.size()];
/*  727: 756 */     iter = definedBySubclass.iterator();
/*  728: 757 */     j = 0;
/*  729: 758 */     while (iter.hasNext()) {
/*  730: 759 */       this.propertyDefinedOnSubclass[(j++)] = ((Boolean)iter.next()).booleanValue();
/*  731:     */     }
/*  732: 763 */     this.filterHelper = new FilterHelper(persistentClass.getFilterMap(), factory.getDialect(), factory.getSqlFunctionRegistry());
/*  733:     */     
/*  734: 765 */     this.temporaryIdTableName = persistentClass.getTemporaryIdTableName();
/*  735: 766 */     this.temporaryIdTableDDL = persistentClass.getTemporaryIdTableDDL();
/*  736:     */   }
/*  737:     */   
/*  738:     */   public AbstractEntityPersister(EntityBinding entityBinding, EntityRegionAccessStrategy cacheAccessStrategy, SessionFactoryImplementor factory)
/*  739:     */     throws HibernateException
/*  740:     */   {
/*  741: 774 */     this.factory = factory;
/*  742: 775 */     this.cacheAccessStrategy = cacheAccessStrategy;
/*  743: 776 */     this.isLazyPropertiesCacheable = (entityBinding.getHierarchyDetails().getCaching() == null ? false : entityBinding.getHierarchyDetails().getCaching().isCacheLazyProperties());
/*  744:     */     
/*  745:     */ 
/*  746:     */ 
/*  747: 780 */     this.cacheEntryStructure = (factory.getSettings().isStructuredCacheEntriesEnabled() ? new StructuredCacheEntry(this) : new UnstructuredCacheEntry());
/*  748:     */     
/*  749:     */ 
/*  750:     */ 
/*  751: 784 */     this.entityMetamodel = new EntityMetamodel(entityBinding, factory);
/*  752: 785 */     this.entityTuplizer = this.entityMetamodel.getTuplizer();
/*  753: 786 */     int batch = entityBinding.getBatchSize();
/*  754: 787 */     if (batch == -1) {
/*  755: 788 */       batch = factory.getSettings().getDefaultBatchFetchSize();
/*  756:     */     }
/*  757: 790 */     this.batchSize = batch;
/*  758: 791 */     this.hasSubselectLoadableCollections = entityBinding.hasSubselectLoadableCollections();
/*  759:     */     
/*  760: 793 */     this.propertyMapping = new BasicEntityPropertyMapping(this);
/*  761:     */     
/*  762:     */ 
/*  763:     */ 
/*  764: 797 */     this.identifierColumnSpan = entityBinding.getHierarchyDetails().getEntityIdentifier().getValueBinding().getSimpleValueSpan();
/*  765: 798 */     this.rootTableKeyColumnNames = new String[this.identifierColumnSpan];
/*  766: 799 */     this.rootTableKeyColumnReaders = new String[this.identifierColumnSpan];
/*  767: 800 */     this.rootTableKeyColumnReaderTemplates = new String[this.identifierColumnSpan];
/*  768: 801 */     this.identifierAliases = new String[this.identifierColumnSpan];
/*  769:     */     
/*  770: 803 */     this.rowIdName = entityBinding.getRowId();
/*  771:     */     
/*  772: 805 */     this.loaderName = entityBinding.getCustomLoaderName();
/*  773:     */     
/*  774: 807 */     int i = 0;
/*  775: 808 */     for (org.hibernate.metamodel.relational.Column col : entityBinding.getPrimaryTable().getPrimaryKey().getColumns())
/*  776:     */     {
/*  777: 809 */       this.rootTableKeyColumnNames[i] = col.getColumnName().encloseInQuotesIfQuoted(factory.getDialect());
/*  778: 810 */       if (col.getReadFragment() == null)
/*  779:     */       {
/*  780: 811 */         this.rootTableKeyColumnReaders[i] = this.rootTableKeyColumnNames[i];
/*  781: 812 */         this.rootTableKeyColumnReaderTemplates[i] = getTemplateFromColumn(col, factory);
/*  782:     */       }
/*  783:     */       else
/*  784:     */       {
/*  785: 815 */         this.rootTableKeyColumnReaders[i] = col.getReadFragment();
/*  786: 816 */         this.rootTableKeyColumnReaderTemplates[i] = getTemplateFromString(col.getReadFragment(), factory);
/*  787:     */       }
/*  788: 818 */       this.identifierAliases[i] = col.getAlias(factory.getDialect());
/*  789: 819 */       i++;
/*  790:     */     }
/*  791: 824 */     if (entityBinding.isVersioned())
/*  792:     */     {
/*  793: 825 */       org.hibernate.metamodel.relational.Value versioningValue = entityBinding.getHierarchyDetails().getVersioningAttributeBinding().getValue();
/*  794: 826 */       if (!org.hibernate.metamodel.relational.Column.class.isInstance(versioningValue)) {
/*  795: 827 */         throw new AssertionFailure("Bad versioning attribute binding : " + versioningValue);
/*  796:     */       }
/*  797: 829 */       org.hibernate.metamodel.relational.Column versionColumn = (org.hibernate.metamodel.relational.Column)org.hibernate.metamodel.relational.Column.class.cast(versioningValue);
/*  798: 830 */       this.versionColumnName = versionColumn.getColumnName().encloseInQuotesIfQuoted(factory.getDialect());
/*  799:     */     }
/*  800:     */     else
/*  801:     */     {
/*  802: 833 */       this.versionColumnName = null;
/*  803:     */     }
/*  804: 838 */     this.sqlWhereString = (StringHelper.isNotEmpty(entityBinding.getWhereFilter()) ? "( " + entityBinding.getWhereFilter() + ") " : null);
/*  805: 839 */     this.sqlWhereStringTemplate = getTemplateFromString(this.sqlWhereString, factory);
/*  806:     */     
/*  807:     */ 
/*  808:     */ 
/*  809: 843 */     boolean lazyAvailable = isInstrumented();
/*  810:     */     
/*  811: 845 */     int hydrateSpan = this.entityMetamodel.getPropertySpan();
/*  812: 846 */     this.propertyColumnSpans = new int[hydrateSpan];
/*  813: 847 */     this.propertySubclassNames = new String[hydrateSpan];
/*  814: 848 */     this.propertyColumnAliases = new String[hydrateSpan][];
/*  815: 849 */     this.propertyColumnNames = new String[hydrateSpan][];
/*  816: 850 */     this.propertyColumnFormulaTemplates = new String[hydrateSpan][];
/*  817: 851 */     this.propertyColumnReaderTemplates = new String[hydrateSpan][];
/*  818: 852 */     this.propertyColumnWriters = new String[hydrateSpan][];
/*  819: 853 */     this.propertyUniqueness = new boolean[hydrateSpan];
/*  820: 854 */     this.propertySelectable = new boolean[hydrateSpan];
/*  821: 855 */     this.propertyColumnUpdateable = new boolean[hydrateSpan][];
/*  822: 856 */     this.propertyColumnInsertable = new boolean[hydrateSpan][];
/*  823: 857 */     HashSet thisClassProperties = new HashSet();
/*  824:     */     
/*  825: 859 */     this.lazyProperties = new HashSet();
/*  826: 860 */     ArrayList lazyNames = new ArrayList();
/*  827: 861 */     ArrayList lazyNumbers = new ArrayList();
/*  828: 862 */     ArrayList lazyTypes = new ArrayList();
/*  829: 863 */     ArrayList lazyColAliases = new ArrayList();
/*  830:     */     
/*  831: 865 */     i = 0;
/*  832: 866 */     boolean foundFormula = false;
/*  833: 867 */     for (AttributeBinding attributeBinding : entityBinding.getAttributeBindingClosure()) {
/*  834: 868 */       if ((attributeBinding != entityBinding.getHierarchyDetails().getEntityIdentifier().getValueBinding()) && 
/*  835:     */       
/*  836:     */ 
/*  837:     */ 
/*  838:     */ 
/*  839: 873 */         (attributeBinding.getAttribute().isSingular()))
/*  840:     */       {
/*  841: 878 */         SingularAttributeBinding singularAttributeBinding = (SingularAttributeBinding)attributeBinding;
/*  842:     */         
/*  843: 880 */         thisClassProperties.add(singularAttributeBinding);
/*  844:     */         
/*  845: 882 */         this.propertySubclassNames[i] = ((EntityBinding)singularAttributeBinding.getContainer()).getEntity().getName();
/*  846:     */         
/*  847: 884 */         int span = singularAttributeBinding.getSimpleValueSpan();
/*  848: 885 */         this.propertyColumnSpans[i] = span;
/*  849:     */         
/*  850: 887 */         String[] colNames = new String[span];
/*  851: 888 */         String[] colAliases = new String[span];
/*  852: 889 */         String[] colReaderTemplates = new String[span];
/*  853: 890 */         String[] colWriters = new String[span];
/*  854: 891 */         String[] formulaTemplates = new String[span];
/*  855: 892 */         boolean[] propertyColumnInsertability = new boolean[span];
/*  856: 893 */         boolean[] propertyColumnUpdatability = new boolean[span];
/*  857:     */         
/*  858: 895 */         int k = 0;
/*  859: 897 */         for (SimpleValueBinding valueBinding : singularAttributeBinding.getSimpleValueBindings())
/*  860:     */         {
/*  861: 898 */           colAliases[k] = valueBinding.getSimpleValue().getAlias(factory.getDialect());
/*  862: 899 */           if (valueBinding.isDerived())
/*  863:     */           {
/*  864: 900 */             foundFormula = true;
/*  865: 901 */             formulaTemplates[k] = getTemplateFromString(((DerivedValue)valueBinding.getSimpleValue()).getExpression(), factory);
/*  866:     */           }
/*  867:     */           else
/*  868:     */           {
/*  869: 904 */             org.hibernate.metamodel.relational.Column col = (org.hibernate.metamodel.relational.Column)valueBinding.getSimpleValue();
/*  870: 905 */             colNames[k] = col.getColumnName().encloseInQuotesIfQuoted(factory.getDialect());
/*  871: 906 */             colReaderTemplates[k] = getTemplateFromColumn(col, factory);
/*  872: 907 */             colWriters[k] = (col.getWriteFragment() == null ? "?" : col.getWriteFragment());
/*  873:     */           }
/*  874: 909 */           propertyColumnInsertability[k] = valueBinding.isIncludeInInsert();
/*  875: 910 */           propertyColumnUpdatability[k] = valueBinding.isIncludeInUpdate();
/*  876: 911 */           k++;
/*  877:     */         }
/*  878: 913 */         this.propertyColumnNames[i] = colNames;
/*  879: 914 */         this.propertyColumnFormulaTemplates[i] = formulaTemplates;
/*  880: 915 */         this.propertyColumnReaderTemplates[i] = colReaderTemplates;
/*  881: 916 */         this.propertyColumnWriters[i] = colWriters;
/*  882: 917 */         this.propertyColumnAliases[i] = colAliases;
/*  883:     */         
/*  884: 919 */         this.propertyColumnUpdateable[i] = propertyColumnUpdatability;
/*  885: 920 */         this.propertyColumnInsertable[i] = propertyColumnInsertability;
/*  886: 922 */         if ((lazyAvailable) && (singularAttributeBinding.isLazy()))
/*  887:     */         {
/*  888: 923 */           this.lazyProperties.add(singularAttributeBinding.getAttribute().getName());
/*  889: 924 */           lazyNames.add(singularAttributeBinding.getAttribute().getName());
/*  890: 925 */           lazyNumbers.add(Integer.valueOf(i));
/*  891: 926 */           lazyTypes.add(singularAttributeBinding.getHibernateTypeDescriptor().getResolvedTypeMapping());
/*  892: 927 */           lazyColAliases.add(colAliases);
/*  893:     */         }
/*  894: 933 */         this.propertySelectable[i] = true;
/*  895:     */         
/*  896: 935 */         this.propertyUniqueness[i] = singularAttributeBinding.isAlternateUniqueKey();
/*  897:     */         
/*  898: 937 */         i++;
/*  899:     */       }
/*  900:     */     }
/*  901: 940 */     this.hasFormulaProperties = foundFormula;
/*  902: 941 */     this.lazyPropertyColumnAliases = ArrayHelper.to2DStringArray(lazyColAliases);
/*  903: 942 */     this.lazyPropertyNames = ArrayHelper.toStringArray(lazyNames);
/*  904: 943 */     this.lazyPropertyNumbers = ArrayHelper.toIntArray(lazyNumbers);
/*  905: 944 */     this.lazyPropertyTypes = ArrayHelper.toTypeArray(lazyTypes);
/*  906:     */     
/*  907:     */ 
/*  908:     */ 
/*  909: 948 */     List<String> columns = new ArrayList();
/*  910: 949 */     List<Boolean> columnsLazy = new ArrayList();
/*  911: 950 */     List<String> columnReaderTemplates = new ArrayList();
/*  912: 951 */     List<String> aliases = new ArrayList();
/*  913: 952 */     List<String> formulas = new ArrayList();
/*  914: 953 */     List<String> formulaAliases = new ArrayList();
/*  915: 954 */     List<String> formulaTemplates = new ArrayList();
/*  916: 955 */     List<Boolean> formulasLazy = new ArrayList();
/*  917: 956 */     List<Type> types = new ArrayList();
/*  918: 957 */     List<String> names = new ArrayList();
/*  919: 958 */     List<String> classes = new ArrayList();
/*  920: 959 */     List<String[]> templates = new ArrayList();
/*  921: 960 */     List<String[]> propColumns = new ArrayList();
/*  922: 961 */     List<String[]> propColumnReaders = new ArrayList();
/*  923: 962 */     List<String[]> propColumnReaderTemplates = new ArrayList();
/*  924: 963 */     List<FetchMode> joinedFetchesList = new ArrayList();
/*  925: 964 */     List<CascadeStyle> cascades = new ArrayList();
/*  926: 965 */     List<Boolean> definedBySubclass = new ArrayList();
/*  927: 966 */     List<int[]> propColumnNumbers = new ArrayList();
/*  928: 967 */     List<int[]> propFormulaNumbers = new ArrayList();
/*  929: 968 */     List<Boolean> columnSelectables = new ArrayList();
/*  930: 969 */     List<Boolean> propNullables = new ArrayList();
/*  931: 971 */     for (AttributeBinding attributeBinding : entityBinding.getSubEntityAttributeBindingClosure()) {
/*  932: 972 */       if ((attributeBinding != entityBinding.getHierarchyDetails().getEntityIdentifier().getValueBinding()) && 
/*  933:     */       
/*  934:     */ 
/*  935:     */ 
/*  936:     */ 
/*  937: 977 */         (attributeBinding.getAttribute().isSingular()))
/*  938:     */       {
/*  939: 982 */         SingularAttributeBinding singularAttributeBinding = (SingularAttributeBinding)attributeBinding;
/*  940:     */         
/*  941: 984 */         names.add(singularAttributeBinding.getAttribute().getName());
/*  942: 985 */         classes.add(((EntityBinding)singularAttributeBinding.getContainer()).getEntity().getName());
/*  943: 986 */         boolean isDefinedBySubclass = !thisClassProperties.contains(singularAttributeBinding);
/*  944: 987 */         definedBySubclass.add(Boolean.valueOf(isDefinedBySubclass));
/*  945: 988 */         propNullables.add(Boolean.valueOf((singularAttributeBinding.isNullable()) || (isDefinedBySubclass)));
/*  946: 989 */         types.add(singularAttributeBinding.getHibernateTypeDescriptor().getResolvedTypeMapping());
/*  947:     */         
/*  948: 991 */         int span = singularAttributeBinding.getSimpleValueSpan();
/*  949: 992 */         String[] cols = new String[span];
/*  950: 993 */         String[] readers = new String[span];
/*  951: 994 */         String[] readerTemplates = new String[span];
/*  952: 995 */         String[] forms = new String[span];
/*  953: 996 */         int[] colnos = new int[span];
/*  954: 997 */         int[] formnos = new int[span];
/*  955: 998 */         int l = 0;
/*  956: 999 */         Boolean lazy = Boolean.valueOf((singularAttributeBinding.isLazy()) && (lazyAvailable));
/*  957:1000 */         for (SimpleValueBinding valueBinding : singularAttributeBinding.getSimpleValueBindings())
/*  958:     */         {
/*  959:1001 */           if (valueBinding.isDerived())
/*  960:     */           {
/*  961:1002 */             DerivedValue derivedValue = (DerivedValue)DerivedValue.class.cast(valueBinding.getSimpleValue());
/*  962:1003 */             String template = getTemplateFromString(derivedValue.getExpression(), factory);
/*  963:1004 */             formnos[l] = formulaTemplates.size();
/*  964:1005 */             colnos[l] = -1;
/*  965:1006 */             formulaTemplates.add(template);
/*  966:1007 */             forms[l] = template;
/*  967:1008 */             formulas.add(derivedValue.getExpression());
/*  968:1009 */             formulaAliases.add(derivedValue.getAlias(factory.getDialect()));
/*  969:1010 */             formulasLazy.add(lazy);
/*  970:     */           }
/*  971:     */           else
/*  972:     */           {
/*  973:1013 */             org.hibernate.metamodel.relational.Column col = (org.hibernate.metamodel.relational.Column)org.hibernate.metamodel.relational.Column.class.cast(valueBinding.getSimpleValue());
/*  974:1014 */             String colName = col.getColumnName().encloseInQuotesIfQuoted(factory.getDialect());
/*  975:1015 */             colnos[l] = columns.size();
/*  976:1016 */             formnos[l] = -1;
/*  977:1017 */             columns.add(colName);
/*  978:1018 */             cols[l] = colName;
/*  979:1019 */             aliases.add(col.getAlias(factory.getDialect()));
/*  980:1020 */             columnsLazy.add(lazy);
/*  981:     */             
/*  982:1022 */             columnSelectables.add(Boolean.valueOf(singularAttributeBinding.getAttribute().isSingular()));
/*  983:     */             
/*  984:1024 */             readers[l] = (col.getReadFragment() == null ? col.getColumnName().encloseInQuotesIfQuoted(factory.getDialect()) : col.getReadFragment());
/*  985:     */             
/*  986:     */ 
/*  987:     */ 
/*  988:1028 */             String readerTemplate = getTemplateFromColumn(col, factory);
/*  989:1029 */             readerTemplates[l] = readerTemplate;
/*  990:1030 */             columnReaderTemplates.add(readerTemplate);
/*  991:     */           }
/*  992:1032 */           l++;
/*  993:     */         }
/*  994:1034 */         propColumns.add(cols);
/*  995:1035 */         propColumnReaders.add(readers);
/*  996:1036 */         propColumnReaderTemplates.add(readerTemplates);
/*  997:1037 */         templates.add(forms);
/*  998:1038 */         propColumnNumbers.add(colnos);
/*  999:1039 */         propFormulaNumbers.add(formnos);
/* 1000:1041 */         if (singularAttributeBinding.isAssociation())
/* 1001:     */         {
/* 1002:1042 */           AssociationAttributeBinding associationAttributeBinding = (AssociationAttributeBinding)singularAttributeBinding;
/* 1003:     */           
/* 1004:1044 */           cascades.add(associationAttributeBinding.getCascadeStyle());
/* 1005:1045 */           joinedFetchesList.add(associationAttributeBinding.getFetchMode());
/* 1006:     */         }
/* 1007:     */         else
/* 1008:     */         {
/* 1009:1048 */           cascades.add(CascadeStyle.NONE);
/* 1010:1049 */           joinedFetchesList.add(FetchMode.SELECT);
/* 1011:     */         }
/* 1012:     */       }
/* 1013:     */     }
/* 1014:1053 */     this.subclassColumnClosure = ArrayHelper.toStringArray(columns);
/* 1015:1054 */     this.subclassColumnAliasClosure = ArrayHelper.toStringArray(aliases);
/* 1016:1055 */     this.subclassColumnLazyClosure = ArrayHelper.toBooleanArray(columnsLazy);
/* 1017:1056 */     this.subclassColumnSelectableClosure = ArrayHelper.toBooleanArray(columnSelectables);
/* 1018:1057 */     this.subclassColumnReaderTemplateClosure = ArrayHelper.toStringArray(columnReaderTemplates);
/* 1019:     */     
/* 1020:1059 */     this.subclassFormulaClosure = ArrayHelper.toStringArray(formulas);
/* 1021:1060 */     this.subclassFormulaTemplateClosure = ArrayHelper.toStringArray(formulaTemplates);
/* 1022:1061 */     this.subclassFormulaAliasClosure = ArrayHelper.toStringArray(formulaAliases);
/* 1023:1062 */     this.subclassFormulaLazyClosure = ArrayHelper.toBooleanArray(formulasLazy);
/* 1024:     */     
/* 1025:1064 */     this.subclassPropertyNameClosure = ArrayHelper.toStringArray(names);
/* 1026:1065 */     this.subclassPropertySubclassNameClosure = ArrayHelper.toStringArray(classes);
/* 1027:1066 */     this.subclassPropertyTypeClosure = ArrayHelper.toTypeArray(types);
/* 1028:1067 */     this.subclassPropertyNullabilityClosure = ArrayHelper.toBooleanArray(propNullables);
/* 1029:1068 */     this.subclassPropertyFormulaTemplateClosure = ArrayHelper.to2DStringArray(templates);
/* 1030:1069 */     this.subclassPropertyColumnNameClosure = ArrayHelper.to2DStringArray(propColumns);
/* 1031:1070 */     this.subclassPropertyColumnReaderClosure = ArrayHelper.to2DStringArray(propColumnReaders);
/* 1032:1071 */     this.subclassPropertyColumnReaderTemplateClosure = ArrayHelper.to2DStringArray(propColumnReaderTemplates);
/* 1033:1072 */     this.subclassPropertyColumnNumberClosure = ArrayHelper.to2DIntArray(propColumnNumbers);
/* 1034:1073 */     this.subclassPropertyFormulaNumberClosure = ArrayHelper.to2DIntArray(propFormulaNumbers);
/* 1035:     */     
/* 1036:1075 */     this.subclassPropertyCascadeStyleClosure = ((CascadeStyle[])cascades.toArray(new CascadeStyle[cascades.size()]));
/* 1037:1076 */     this.subclassPropertyFetchModeClosure = ((FetchMode[])joinedFetchesList.toArray(new FetchMode[joinedFetchesList.size()]));
/* 1038:     */     
/* 1039:1078 */     this.propertyDefinedOnSubclass = ArrayHelper.toBooleanArray(definedBySubclass);
/* 1040:     */     
/* 1041:1080 */     Map<String, String> filterDefaultConditionsByName = new HashMap();
/* 1042:1081 */     for (FilterDefinition filterDefinition : entityBinding.getFilterDefinitions()) {
/* 1043:1082 */       filterDefaultConditionsByName.put(filterDefinition.getFilterName(), filterDefinition.getDefaultFilterCondition());
/* 1044:     */     }
/* 1045:1084 */     this.filterHelper = new FilterHelper(filterDefaultConditionsByName, factory.getDialect(), factory.getSqlFunctionRegistry());
/* 1046:     */     
/* 1047:1086 */     this.temporaryIdTableName = null;
/* 1048:1087 */     this.temporaryIdTableDDL = null;
/* 1049:     */   }
/* 1050:     */   
/* 1051:     */   protected static String getTemplateFromString(String string, SessionFactoryImplementor factory)
/* 1052:     */   {
/* 1053:1091 */     return string == null ? null : Template.renderWhereStringTemplate(string, factory.getDialect(), factory.getSqlFunctionRegistry());
/* 1054:     */   }
/* 1055:     */   
/* 1056:     */   public String getTemplateFromColumn(org.hibernate.metamodel.relational.Column column, SessionFactoryImplementor factory)
/* 1057:     */   {
/* 1058:     */     String templateString;
/* 1059:     */     String templateString;
/* 1060:1098 */     if (column.getReadFragment() != null)
/* 1061:     */     {
/* 1062:1099 */       templateString = getTemplateFromString(column.getReadFragment(), factory);
/* 1063:     */     }
/* 1064:     */     else
/* 1065:     */     {
/* 1066:1102 */       String columnName = column.getColumnName().encloseInQuotesIfQuoted(factory.getDialect());
/* 1067:1103 */       templateString = "$PlaceHolder$." + columnName;
/* 1068:     */     }
/* 1069:1105 */     return templateString;
/* 1070:     */   }
/* 1071:     */   
/* 1072:     */   protected String generateLazySelectString()
/* 1073:     */   {
/* 1074:1110 */     if (!this.entityMetamodel.hasLazyProperties()) {
/* 1075:1111 */       return null;
/* 1076:     */     }
/* 1077:1114 */     HashSet tableNumbers = new HashSet();
/* 1078:1115 */     ArrayList columnNumbers = new ArrayList();
/* 1079:1116 */     ArrayList formulaNumbers = new ArrayList();
/* 1080:1117 */     for (int i = 0; i < this.lazyPropertyNames.length; i++)
/* 1081:     */     {
/* 1082:1122 */       int propertyNumber = getSubclassPropertyIndex(this.lazyPropertyNames[i]);
/* 1083:     */       
/* 1084:1124 */       int tableNumber = getSubclassPropertyTableNumber(propertyNumber);
/* 1085:1125 */       tableNumbers.add(Integer.valueOf(tableNumber));
/* 1086:     */       
/* 1087:1127 */       int[] colNumbers = this.subclassPropertyColumnNumberClosure[propertyNumber];
/* 1088:1128 */       for (int j = 0; j < colNumbers.length; j++) {
/* 1089:1129 */         if (colNumbers[j] != -1) {
/* 1090:1130 */           columnNumbers.add(Integer.valueOf(colNumbers[j]));
/* 1091:     */         }
/* 1092:     */       }
/* 1093:1133 */       int[] formNumbers = this.subclassPropertyFormulaNumberClosure[propertyNumber];
/* 1094:1134 */       for (int j = 0; j < formNumbers.length; j++) {
/* 1095:1135 */         if (formNumbers[j] != -1) {
/* 1096:1136 */           formulaNumbers.add(Integer.valueOf(formNumbers[j]));
/* 1097:     */         }
/* 1098:     */       }
/* 1099:     */     }
/* 1100:1141 */     if ((columnNumbers.size() == 0) && (formulaNumbers.size() == 0)) {
/* 1101:1143 */       return null;
/* 1102:     */     }
/* 1103:1146 */     return renderSelect(ArrayHelper.toIntArray(tableNumbers), ArrayHelper.toIntArray(columnNumbers), ArrayHelper.toIntArray(formulaNumbers));
/* 1104:     */   }
/* 1105:     */   
/* 1106:     */   public Object initializeLazyProperty(String fieldName, Object entity, SessionImplementor session)
/* 1107:     */     throws HibernateException
/* 1108:     */   {
/* 1109:1155 */     Serializable id = session.getContextEntityIdentifier(entity);
/* 1110:     */     
/* 1111:1157 */     EntityEntry entry = session.getPersistenceContext().getEntry(entity);
/* 1112:1158 */     if (entry == null) {
/* 1113:1159 */       throw new HibernateException("entity is not associated with the session: " + id);
/* 1114:     */     }
/* 1115:1162 */     if (LOG.isTraceEnabled()) {
/* 1116:1163 */       LOG.tracev("Initializing lazy properties of: {0}, field access: {1}", MessageHelper.infoString(this, id, getFactory()), fieldName);
/* 1117:     */     }
/* 1118:1166 */     if (hasCache())
/* 1119:     */     {
/* 1120:1167 */       CacheKey cacheKey = session.generateCacheKey(id, getIdentifierType(), getEntityName());
/* 1121:1168 */       Object ce = getCacheAccessStrategy().get(cacheKey, session.getTimestamp());
/* 1122:1169 */       if (ce != null)
/* 1123:     */       {
/* 1124:1170 */         CacheEntry cacheEntry = (CacheEntry)getCacheEntryStructure().destructure(ce, this.factory);
/* 1125:1171 */         if (!cacheEntry.areLazyPropertiesUnfetched()) {
/* 1126:1173 */           return initializeLazyPropertiesFromCache(fieldName, entity, session, entry, cacheEntry);
/* 1127:     */         }
/* 1128:     */       }
/* 1129:     */     }
/* 1130:1178 */     return initializeLazyPropertiesFromDatastore(fieldName, entity, session, id, entry);
/* 1131:     */   }
/* 1132:     */   
/* 1133:     */   private Object initializeLazyPropertiesFromDatastore(String fieldName, Object entity, SessionImplementor session, Serializable id, EntityEntry entry)
/* 1134:     */   {
/* 1135:1189 */     if (!hasLazyProperties()) {
/* 1136:1189 */       throw new AssertionFailure("no lazy properties");
/* 1137:     */     }
/* 1138:1191 */     LOG.trace("Initializing lazy properties from datastore");
/* 1139:     */     try
/* 1140:     */     {
/* 1141:1195 */       Object result = null;
/* 1142:1196 */       PreparedStatement ps = null;
/* 1143:     */       try
/* 1144:     */       {
/* 1145:1198 */         String lazySelect = getSQLLazySelectString();
/* 1146:1199 */         ResultSet rs = null;
/* 1147:     */         try
/* 1148:     */         {
/* 1149:1201 */           if (lazySelect != null)
/* 1150:     */           {
/* 1151:1205 */             ps = session.getTransactionCoordinator().getJdbcCoordinator().getStatementPreparer().prepareStatement(lazySelect);
/* 1152:     */             
/* 1153:     */ 
/* 1154:     */ 
/* 1155:1209 */             getIdentifierType().nullSafeSet(ps, id, 1, session);
/* 1156:1210 */             rs = ps.executeQuery();
/* 1157:1211 */             rs.next();
/* 1158:     */           }
/* 1159:1213 */           Object[] snapshot = entry.getLoadedState();
/* 1160:1214 */           for (int j = 0; j < this.lazyPropertyNames.length; j++)
/* 1161:     */           {
/* 1162:1215 */             Object propValue = this.lazyPropertyTypes[j].nullSafeGet(rs, this.lazyPropertyColumnAliases[j], session, entity);
/* 1163:1216 */             if (initializeLazyProperty(fieldName, entity, session, snapshot, j, propValue)) {
/* 1164:1217 */               result = propValue;
/* 1165:     */             }
/* 1166:     */           }
/* 1167:     */         }
/* 1168:     */         finally {}
/* 1169:     */       }
/* 1170:     */       finally
/* 1171:     */       {
/* 1172:1228 */         if (ps != null) {
/* 1173:1229 */           ps.close();
/* 1174:     */         }
/* 1175:     */       }
/* 1176:1233 */       LOG.trace("Done initializing lazy properties");
/* 1177:     */       
/* 1178:1235 */       return result;
/* 1179:     */     }
/* 1180:     */     catch (SQLException sqle)
/* 1181:     */     {
/* 1182:1239 */       throw getFactory().getSQLExceptionHelper().convert(sqle, "could not initialize lazy properties: " + MessageHelper.infoString(this, id, getFactory()), getSQLLazySelectString());
/* 1183:     */     }
/* 1184:     */   }
/* 1185:     */   
/* 1186:     */   private Object initializeLazyPropertiesFromCache(String fieldName, Object entity, SessionImplementor session, EntityEntry entry, CacheEntry cacheEntry)
/* 1187:     */   {
/* 1188:1256 */     LOG.trace("Initializing lazy properties from second-level cache");
/* 1189:     */     
/* 1190:1258 */     Object result = null;
/* 1191:1259 */     Serializable[] disassembledValues = cacheEntry.getDisassembledState();
/* 1192:1260 */     Object[] snapshot = entry.getLoadedState();
/* 1193:1261 */     for (int j = 0; j < this.lazyPropertyNames.length; j++)
/* 1194:     */     {
/* 1195:1262 */       Object propValue = this.lazyPropertyTypes[j].assemble(disassembledValues[this.lazyPropertyNumbers[j]], session, entity);
/* 1196:1267 */       if (initializeLazyProperty(fieldName, entity, session, snapshot, j, propValue)) {
/* 1197:1268 */         result = propValue;
/* 1198:     */       }
/* 1199:     */     }
/* 1200:1272 */     LOG.trace("Done initializing lazy properties");
/* 1201:     */     
/* 1202:1274 */     return result;
/* 1203:     */   }
/* 1204:     */   
/* 1205:     */   private boolean initializeLazyProperty(String fieldName, Object entity, SessionImplementor session, Object[] snapshot, int j, Object propValue)
/* 1206:     */   {
/* 1207:1284 */     setPropertyValue(entity, this.lazyPropertyNumbers[j], propValue);
/* 1208:1285 */     if (snapshot != null) {
/* 1209:1287 */       snapshot[this.lazyPropertyNumbers[j]] = this.lazyPropertyTypes[j].deepCopy(propValue, this.factory);
/* 1210:     */     }
/* 1211:1289 */     return fieldName.equals(this.lazyPropertyNames[j]);
/* 1212:     */   }
/* 1213:     */   
/* 1214:     */   public boolean isBatchable()
/* 1215:     */   {
/* 1216:1293 */     return (optimisticLockStyle() == OptimisticLockStyle.NONE) || ((!isVersioned()) && (optimisticLockStyle() == OptimisticLockStyle.VERSION)) || (getFactory().getSettings().isJdbcBatchVersionedData());
/* 1217:     */   }
/* 1218:     */   
/* 1219:     */   public Serializable[] getQuerySpaces()
/* 1220:     */   {
/* 1221:1299 */     return getPropertySpaces();
/* 1222:     */   }
/* 1223:     */   
/* 1224:     */   protected Set getLazyProperties()
/* 1225:     */   {
/* 1226:1303 */     return this.lazyProperties;
/* 1227:     */   }
/* 1228:     */   
/* 1229:     */   public boolean isBatchLoadable()
/* 1230:     */   {
/* 1231:1307 */     return this.batchSize > 1;
/* 1232:     */   }
/* 1233:     */   
/* 1234:     */   public String[] getIdentifierColumnNames()
/* 1235:     */   {
/* 1236:1311 */     return this.rootTableKeyColumnNames;
/* 1237:     */   }
/* 1238:     */   
/* 1239:     */   public String[] getIdentifierColumnReaders()
/* 1240:     */   {
/* 1241:1315 */     return this.rootTableKeyColumnReaders;
/* 1242:     */   }
/* 1243:     */   
/* 1244:     */   public String[] getIdentifierColumnReaderTemplates()
/* 1245:     */   {
/* 1246:1319 */     return this.rootTableKeyColumnReaderTemplates;
/* 1247:     */   }
/* 1248:     */   
/* 1249:     */   protected int getIdentifierColumnSpan()
/* 1250:     */   {
/* 1251:1323 */     return this.identifierColumnSpan;
/* 1252:     */   }
/* 1253:     */   
/* 1254:     */   protected String[] getIdentifierAliases()
/* 1255:     */   {
/* 1256:1327 */     return this.identifierAliases;
/* 1257:     */   }
/* 1258:     */   
/* 1259:     */   public String getVersionColumnName()
/* 1260:     */   {
/* 1261:1331 */     return this.versionColumnName;
/* 1262:     */   }
/* 1263:     */   
/* 1264:     */   protected String getVersionedTableName()
/* 1265:     */   {
/* 1266:1335 */     return getTableName(0);
/* 1267:     */   }
/* 1268:     */   
/* 1269:     */   protected boolean[] getSubclassColumnLazyiness()
/* 1270:     */   {
/* 1271:1339 */     return this.subclassColumnLazyClosure;
/* 1272:     */   }
/* 1273:     */   
/* 1274:     */   protected boolean[] getSubclassFormulaLazyiness()
/* 1275:     */   {
/* 1276:1343 */     return this.subclassFormulaLazyClosure;
/* 1277:     */   }
/* 1278:     */   
/* 1279:     */   public boolean isCacheInvalidationRequired()
/* 1280:     */   {
/* 1281:1356 */     return (hasFormulaProperties()) || ((!isVersioned()) && ((this.entityMetamodel.isDynamicUpdate()) || (getTableSpan() > 1)));
/* 1282:     */   }
/* 1283:     */   
/* 1284:     */   public boolean isLazyPropertiesCacheable()
/* 1285:     */   {
/* 1286:1361 */     return this.isLazyPropertiesCacheable;
/* 1287:     */   }
/* 1288:     */   
/* 1289:     */   public String selectFragment(String alias, String suffix)
/* 1290:     */   {
/* 1291:1365 */     return identifierSelectFragment(alias, suffix) + propertySelectFragment(alias, suffix, false);
/* 1292:     */   }
/* 1293:     */   
/* 1294:     */   public String[] getIdentifierAliases(String suffix)
/* 1295:     */   {
/* 1296:1373 */     return new Alias(suffix).toAliasStrings(getIdentifierAliases());
/* 1297:     */   }
/* 1298:     */   
/* 1299:     */   public String[] getPropertyAliases(String suffix, int i)
/* 1300:     */   {
/* 1301:1378 */     return new Alias(suffix).toUnquotedAliasStrings(this.propertyColumnAliases[i]);
/* 1302:     */   }
/* 1303:     */   
/* 1304:     */   public String getDiscriminatorAlias(String suffix)
/* 1305:     */   {
/* 1306:1385 */     return this.entityMetamodel.hasSubclasses() ? new Alias(suffix).toAliasString(getDiscriminatorAlias()) : null;
/* 1307:     */   }
/* 1308:     */   
/* 1309:     */   public String identifierSelectFragment(String name, String suffix)
/* 1310:     */   {
/* 1311:1391 */     return new SelectFragment().setSuffix(suffix).addColumns(name, getIdentifierColumnNames(), getIdentifierAliases()).toFragmentString().substring(2);
/* 1312:     */   }
/* 1313:     */   
/* 1314:     */   public String propertySelectFragment(String tableAlias, String suffix, boolean allProperties)
/* 1315:     */   {
/* 1316:1400 */     return propertySelectFragmentFragment(tableAlias, suffix, allProperties).toFragmentString();
/* 1317:     */   }
/* 1318:     */   
/* 1319:     */   public SelectFragment propertySelectFragmentFragment(String tableAlias, String suffix, boolean allProperties)
/* 1320:     */   {
/* 1321:1407 */     SelectFragment select = new SelectFragment().setSuffix(suffix).setUsedAliases(getIdentifierAliases());
/* 1322:     */     
/* 1323:     */ 
/* 1324:     */ 
/* 1325:1411 */     int[] columnTableNumbers = getSubclassColumnTableNumberClosure();
/* 1326:1412 */     String[] columnAliases = getSubclassColumnAliasClosure();
/* 1327:1413 */     String[] columnReaderTemplates = getSubclassColumnReaderTemplateClosure();
/* 1328:1414 */     for (int i = 0; i < getSubclassColumnClosure().length; i++)
/* 1329:     */     {
/* 1330:1415 */       boolean selectable = ((allProperties) || (this.subclassColumnLazyClosure[i] == 0)) && (!isSubclassTableSequentialSelect(columnTableNumbers[i])) && (this.subclassColumnSelectableClosure[i] != 0);
/* 1331:1418 */       if (selectable)
/* 1332:     */       {
/* 1333:1419 */         String subalias = generateTableAlias(tableAlias, columnTableNumbers[i]);
/* 1334:1420 */         select.addColumnTemplate(subalias, columnReaderTemplates[i], columnAliases[i]);
/* 1335:     */       }
/* 1336:     */     }
/* 1337:1424 */     int[] formulaTableNumbers = getSubclassFormulaTableNumberClosure();
/* 1338:1425 */     String[] formulaTemplates = getSubclassFormulaTemplateClosure();
/* 1339:1426 */     String[] formulaAliases = getSubclassFormulaAliasClosure();
/* 1340:1427 */     for (int i = 0; i < getSubclassFormulaTemplateClosure().length; i++)
/* 1341:     */     {
/* 1342:1428 */       boolean selectable = ((allProperties) || (this.subclassFormulaLazyClosure[i] == 0)) && (!isSubclassTableSequentialSelect(formulaTableNumbers[i]));
/* 1343:1430 */       if (selectable)
/* 1344:     */       {
/* 1345:1431 */         String subalias = generateTableAlias(tableAlias, formulaTableNumbers[i]);
/* 1346:1432 */         select.addFormula(subalias, formulaTemplates[i], formulaAliases[i]);
/* 1347:     */       }
/* 1348:     */     }
/* 1349:1436 */     if (this.entityMetamodel.hasSubclasses()) {
/* 1350:1437 */       addDiscriminatorToSelect(select, tableAlias, suffix);
/* 1351:     */     }
/* 1352:1440 */     if (hasRowId()) {
/* 1353:1441 */       select.addColumn(tableAlias, this.rowIdName, "rowid_");
/* 1354:     */     }
/* 1355:1444 */     return select;
/* 1356:     */   }
/* 1357:     */   
/* 1358:     */   /* Error */
/* 1359:     */   public Object[] getDatabaseSnapshot(Serializable id, SessionImplementor session)
/* 1360:     */     throws HibernateException
/* 1361:     */   {
/* 1362:     */     // Byte code:
/* 1363:     */     //   0: getstatic 287	org/hibernate/persister/entity/AbstractEntityPersister:LOG	Lorg/hibernate/internal/CoreMessageLogger;
/* 1364:     */     //   3: invokeinterface 288 1 0
/* 1365:     */     //   8: ifeq +23 -> 31
/* 1366:     */     //   11: getstatic 287	org/hibernate/persister/entity/AbstractEntityPersister:LOG	Lorg/hibernate/internal/CoreMessageLogger;
/* 1367:     */     //   14: ldc_w 376
/* 1368:     */     //   17: aload_0
/* 1369:     */     //   18: aload_1
/* 1370:     */     //   19: aload_0
/* 1371:     */     //   20: invokevirtual 290	org/hibernate/persister/entity/AbstractEntityPersister:getFactory	()Lorg/hibernate/engine/spi/SessionFactoryImplementor;
/* 1372:     */     //   23: invokestatic 291	org/hibernate/pretty/MessageHelper:infoString	(Lorg/hibernate/persister/entity/EntityPersister;Ljava/lang/Object;Lorg/hibernate/engine/spi/SessionFactoryImplementor;)Ljava/lang/String;
/* 1373:     */     //   26: invokeinterface 377 3 0
/* 1374:     */     //   31: aload_2
/* 1375:     */     //   32: invokeinterface 311 1 0
/* 1376:     */     //   37: invokeinterface 312 1 0
/* 1377:     */     //   42: invokeinterface 313 1 0
/* 1378:     */     //   47: aload_0
/* 1379:     */     //   48: invokevirtual 378	org/hibernate/persister/entity/AbstractEntityPersister:getSQLSnapshotSelectString	()Ljava/lang/String;
/* 1380:     */     //   51: invokeinterface 314 2 0
/* 1381:     */     //   56: astore_3
/* 1382:     */     //   57: aload_0
/* 1383:     */     //   58: invokevirtual 294	org/hibernate/persister/entity/AbstractEntityPersister:getIdentifierType	()Lorg/hibernate/type/Type;
/* 1384:     */     //   61: aload_3
/* 1385:     */     //   62: aload_1
/* 1386:     */     //   63: iconst_1
/* 1387:     */     //   64: aload_2
/* 1388:     */     //   65: invokeinterface 315 5 0
/* 1389:     */     //   70: aload_3
/* 1390:     */     //   71: invokeinterface 316 1 0
/* 1391:     */     //   76: astore 4
/* 1392:     */     //   78: aload 4
/* 1393:     */     //   80: invokeinterface 317 1 0
/* 1394:     */     //   85: ifne +22 -> 107
/* 1395:     */     //   88: aconst_null
/* 1396:     */     //   89: astore 5
/* 1397:     */     //   91: aload 4
/* 1398:     */     //   93: invokeinterface 321 1 0
/* 1399:     */     //   98: aload_3
/* 1400:     */     //   99: invokeinterface 322 1 0
/* 1401:     */     //   104: aload 5
/* 1402:     */     //   106: areturn
/* 1403:     */     //   107: aload_0
/* 1404:     */     //   108: invokevirtual 379	org/hibernate/persister/entity/AbstractEntityPersister:getPropertyTypes	()[Lorg/hibernate/type/Type;
/* 1405:     */     //   111: astore 5
/* 1406:     */     //   113: aload 5
/* 1407:     */     //   115: arraylength
/* 1408:     */     //   116: anewarray 380	java/lang/Object
/* 1409:     */     //   119: astore 6
/* 1410:     */     //   121: aload_0
/* 1411:     */     //   122: invokevirtual 28	org/hibernate/persister/entity/AbstractEntityPersister:getPropertyUpdateability	()[Z
/* 1412:     */     //   125: astore 7
/* 1413:     */     //   127: iconst_0
/* 1414:     */     //   128: istore 8
/* 1415:     */     //   130: iload 8
/* 1416:     */     //   132: aload 5
/* 1417:     */     //   134: arraylength
/* 1418:     */     //   135: if_icmpge +45 -> 180
/* 1419:     */     //   138: aload 7
/* 1420:     */     //   140: iload 8
/* 1421:     */     //   142: baload
/* 1422:     */     //   143: ifeq +31 -> 174
/* 1423:     */     //   146: aload 6
/* 1424:     */     //   148: iload 8
/* 1425:     */     //   150: aload 5
/* 1426:     */     //   152: iload 8
/* 1427:     */     //   154: aaload
/* 1428:     */     //   155: aload 4
/* 1429:     */     //   157: aload_0
/* 1430:     */     //   158: ldc_w 381
/* 1431:     */     //   161: iload 8
/* 1432:     */     //   163: invokevirtual 382	org/hibernate/persister/entity/AbstractEntityPersister:getPropertyAliases	(Ljava/lang/String;I)[Ljava/lang/String;
/* 1433:     */     //   166: aload_2
/* 1434:     */     //   167: aconst_null
/* 1435:     */     //   168: invokeinterface 383 5 0
/* 1436:     */     //   173: aastore
/* 1437:     */     //   174: iinc 8 1
/* 1438:     */     //   177: goto -47 -> 130
/* 1439:     */     //   180: aload 6
/* 1440:     */     //   182: astore 8
/* 1441:     */     //   184: aload 4
/* 1442:     */     //   186: invokeinterface 321 1 0
/* 1443:     */     //   191: aload_3
/* 1444:     */     //   192: invokeinterface 322 1 0
/* 1445:     */     //   197: aload 8
/* 1446:     */     //   199: areturn
/* 1447:     */     //   200: astore 9
/* 1448:     */     //   202: aload 4
/* 1449:     */     //   204: invokeinterface 321 1 0
/* 1450:     */     //   209: aload 9
/* 1451:     */     //   211: athrow
/* 1452:     */     //   212: astore 10
/* 1453:     */     //   214: aload_3
/* 1454:     */     //   215: invokeinterface 322 1 0
/* 1455:     */     //   220: aload 10
/* 1456:     */     //   222: athrow
/* 1457:     */     //   223: astore_3
/* 1458:     */     //   224: aload_0
/* 1459:     */     //   225: invokevirtual 290	org/hibernate/persister/entity/AbstractEntityPersister:getFactory	()Lorg/hibernate/engine/spi/SessionFactoryImplementor;
/* 1460:     */     //   228: invokeinterface 325 1 0
/* 1461:     */     //   233: aload_3
/* 1462:     */     //   234: new 97	java/lang/StringBuilder
/* 1463:     */     //   237: dup
/* 1464:     */     //   238: invokespecial 98	java/lang/StringBuilder:<init>	()V
/* 1465:     */     //   241: ldc_w 384
/* 1466:     */     //   244: invokevirtual 100	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 1467:     */     //   247: aload_0
/* 1468:     */     //   248: aload_1
/* 1469:     */     //   249: aload_0
/* 1470:     */     //   250: invokevirtual 290	org/hibernate/persister/entity/AbstractEntityPersister:getFactory	()Lorg/hibernate/engine/spi/SessionFactoryImplementor;
/* 1471:     */     //   253: invokestatic 291	org/hibernate/pretty/MessageHelper:infoString	(Lorg/hibernate/persister/entity/EntityPersister;Ljava/lang/Object;Lorg/hibernate/engine/spi/SessionFactoryImplementor;)Ljava/lang/String;
/* 1472:     */     //   256: invokevirtual 100	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 1473:     */     //   259: invokevirtual 102	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 1474:     */     //   262: aload_0
/* 1475:     */     //   263: invokevirtual 378	org/hibernate/persister/entity/AbstractEntityPersister:getSQLSnapshotSelectString	()Ljava/lang/String;
/* 1476:     */     //   266: invokevirtual 327	org/hibernate/engine/jdbc/spi/SqlExceptionHelper:convert	(Ljava/sql/SQLException;Ljava/lang/String;Ljava/lang/String;)Lorg/hibernate/JDBCException;
/* 1477:     */     //   269: athrow
/* 1478:     */     // Line number table:
/* 1479:     */     //   Java source line #1450	-> byte code offset #0
/* 1480:     */     //   Java source line #1451	-> byte code offset #11
/* 1481:     */     //   Java source line #1455	-> byte code offset #31
/* 1482:     */     //   Java source line #1460	-> byte code offset #57
/* 1483:     */     //   Java source line #1462	-> byte code offset #70
/* 1484:     */     //   Java source line #1465	-> byte code offset #78
/* 1485:     */     //   Java source line #1466	-> byte code offset #88
/* 1486:     */     //   Java source line #1480	-> byte code offset #91
/* 1487:     */     //   Java source line #1484	-> byte code offset #98
/* 1488:     */     //   Java source line #1469	-> byte code offset #107
/* 1489:     */     //   Java source line #1470	-> byte code offset #113
/* 1490:     */     //   Java source line #1471	-> byte code offset #121
/* 1491:     */     //   Java source line #1472	-> byte code offset #127
/* 1492:     */     //   Java source line #1473	-> byte code offset #138
/* 1493:     */     //   Java source line #1474	-> byte code offset #146
/* 1494:     */     //   Java source line #1472	-> byte code offset #174
/* 1495:     */     //   Java source line #1477	-> byte code offset #180
/* 1496:     */     //   Java source line #1480	-> byte code offset #184
/* 1497:     */     //   Java source line #1484	-> byte code offset #191
/* 1498:     */     //   Java source line #1480	-> byte code offset #200
/* 1499:     */     //   Java source line #1484	-> byte code offset #212
/* 1500:     */     //   Java source line #1487	-> byte code offset #223
/* 1501:     */     //   Java source line #1488	-> byte code offset #224
/* 1502:     */     // Local variable table:
/* 1503:     */     //   start	length	slot	name	signature
/* 1504:     */     //   0	270	0	this	AbstractEntityPersister
/* 1505:     */     //   0	270	1	id	Serializable
/* 1506:     */     //   0	270	2	session	SessionImplementor
/* 1507:     */     //   56	159	3	ps	PreparedStatement
/* 1508:     */     //   223	11	3	e	SQLException
/* 1509:     */     //   76	127	4	rs	ResultSet
/* 1510:     */     //   111	40	5	types	Type[]
/* 1511:     */     //   119	62	6	values	Object[]
/* 1512:     */     //   125	14	7	includeProperty	boolean[]
/* 1513:     */     //   128	70	8	i	int
/* 1514:     */     //   200	10	9	localObject1	Object
/* 1515:     */     //   212	9	10	localObject2	Object
/* 1516:     */     // Exception table:
/* 1517:     */     //   from	to	target	type
/* 1518:     */     //   78	91	200	finally
/* 1519:     */     //   107	184	200	finally
/* 1520:     */     //   200	202	200	finally
/* 1521:     */     //   57	98	212	finally
/* 1522:     */     //   107	191	212	finally
/* 1523:     */     //   200	214	212	finally
/* 1524:     */     //   31	104	223	java/sql/SQLException
/* 1525:     */     //   107	197	223	java/sql/SQLException
/* 1526:     */     //   200	223	223	java/sql/SQLException
/* 1527:     */   }
/* 1528:     */   
/* 1529:     */   protected String generateSelectVersionString()
/* 1530:     */   {
/* 1531:1501 */     SimpleSelect select = new SimpleSelect(getFactory().getDialect()).setTableName(getVersionedTableName());
/* 1532:1503 */     if (isVersioned()) {
/* 1533:1504 */       select.addColumn(this.versionColumnName);
/* 1534:     */     } else {
/* 1535:1507 */       select.addColumns(this.rootTableKeyColumnNames);
/* 1536:     */     }
/* 1537:1509 */     if (getFactory().getSettings().isCommentsEnabled()) {
/* 1538:1510 */       select.setComment("get version " + getEntityName());
/* 1539:     */     }
/* 1540:1512 */     return select.addCondition(this.rootTableKeyColumnNames, "=?").toStatementString();
/* 1541:     */   }
/* 1542:     */   
/* 1543:     */   public boolean[] getPropertyUniqueness()
/* 1544:     */   {
/* 1545:1516 */     return this.propertyUniqueness;
/* 1546:     */   }
/* 1547:     */   
/* 1548:     */   protected String generateInsertGeneratedValuesSelectString()
/* 1549:     */   {
/* 1550:1520 */     return generateGeneratedValuesSelectString(getPropertyInsertGenerationInclusions());
/* 1551:     */   }
/* 1552:     */   
/* 1553:     */   protected String generateUpdateGeneratedValuesSelectString()
/* 1554:     */   {
/* 1555:1524 */     return generateGeneratedValuesSelectString(getPropertyUpdateGenerationInclusions());
/* 1556:     */   }
/* 1557:     */   
/* 1558:     */   private String generateGeneratedValuesSelectString(ValueInclusion[] inclusions)
/* 1559:     */   {
/* 1560:1528 */     Select select = new Select(getFactory().getDialect());
/* 1561:1530 */     if (getFactory().getSettings().isCommentsEnabled()) {
/* 1562:1531 */       select.setComment("get generated state " + getEntityName());
/* 1563:     */     }
/* 1564:1534 */     String[] aliasedIdColumns = StringHelper.qualify(getRootAlias(), getIdentifierColumnNames());
/* 1565:     */     
/* 1566:     */ 
/* 1567:     */ 
/* 1568:     */ 
/* 1569:1539 */     String selectClause = concretePropertySelectFragment(getRootAlias(), inclusions);
/* 1570:1540 */     selectClause = selectClause.substring(2);
/* 1571:     */     
/* 1572:1542 */     String fromClause = fromTableFragment(getRootAlias()) + fromJoinFragment(getRootAlias(), true, false);
/* 1573:     */     
/* 1574:     */ 
/* 1575:1545 */     String whereClause = StringHelper.join("=? and ", aliasedIdColumns) + "=?" + whereJoinFragment(getRootAlias(), true, false);
/* 1576:     */     
/* 1577:     */ 
/* 1578:     */ 
/* 1579:     */ 
/* 1580:     */ 
/* 1581:1551 */     return select.setSelectClause(selectClause).setFromClause(fromClause).setOuterJoins("", "").setWhereClause(whereClause).toStatementString();
/* 1582:     */   }
/* 1583:     */   
/* 1584:     */   protected String concretePropertySelectFragment(String alias, final ValueInclusion[] inclusions)
/* 1585:     */   {
/* 1586:1563 */     concretePropertySelectFragment(alias, new InclusionChecker()
/* 1587:     */     {
/* 1588:     */       public boolean includeProperty(int propertyNumber)
/* 1589:     */       {
/* 1590:1571 */         return inclusions[propertyNumber] != ValueInclusion.NONE;
/* 1591:     */       }
/* 1592:     */     });
/* 1593:     */   }
/* 1594:     */   
/* 1595:     */   protected String concretePropertySelectFragment(String alias, final boolean[] includeProperty)
/* 1596:     */   {
/* 1597:1578 */     concretePropertySelectFragment(alias, new InclusionChecker()
/* 1598:     */     {
/* 1599:     */       public boolean includeProperty(int propertyNumber)
/* 1600:     */       {
/* 1601:1582 */         return includeProperty[propertyNumber];
/* 1602:     */       }
/* 1603:     */     });
/* 1604:     */   }
/* 1605:     */   
/* 1606:     */   protected String concretePropertySelectFragment(String alias, InclusionChecker inclusionChecker)
/* 1607:     */   {
/* 1608:1589 */     int propertyCount = getPropertyNames().length;
/* 1609:1590 */     int[] propertyTableNumbers = getPropertyTableNumbersInSelect();
/* 1610:1591 */     SelectFragment frag = new SelectFragment();
/* 1611:1592 */     for (int i = 0; i < propertyCount; i++) {
/* 1612:1593 */       if (inclusionChecker.includeProperty(i))
/* 1613:     */       {
/* 1614:1594 */         frag.addColumnTemplates(generateTableAlias(alias, propertyTableNumbers[i]), this.propertyColumnReaderTemplates[i], this.propertyColumnAliases[i]);
/* 1615:     */         
/* 1616:     */ 
/* 1617:     */ 
/* 1618:     */ 
/* 1619:1599 */         frag.addFormulas(generateTableAlias(alias, propertyTableNumbers[i]), this.propertyColumnFormulaTemplates[i], this.propertyColumnAliases[i]);
/* 1620:     */       }
/* 1621:     */     }
/* 1622:1606 */     return frag.toFragmentString();
/* 1623:     */   }
/* 1624:     */   
/* 1625:     */   protected String generateSnapshotSelectString()
/* 1626:     */   {
/* 1627:1613 */     Select select = new Select(getFactory().getDialect());
/* 1628:1615 */     if (getFactory().getSettings().isCommentsEnabled()) {
/* 1629:1616 */       select.setComment("get current state " + getEntityName());
/* 1630:     */     }
/* 1631:1619 */     String[] aliasedIdColumns = StringHelper.qualify(getRootAlias(), getIdentifierColumnNames());
/* 1632:1620 */     String selectClause = StringHelper.join(", ", aliasedIdColumns) + concretePropertySelectFragment(getRootAlias(), getPropertyUpdateability());
/* 1633:     */     
/* 1634:     */ 
/* 1635:1623 */     String fromClause = fromTableFragment(getRootAlias()) + fromJoinFragment(getRootAlias(), true, false);
/* 1636:     */     
/* 1637:     */ 
/* 1638:1626 */     String whereClause = StringHelper.join("=? and ", aliasedIdColumns) + "=?" + whereJoinFragment(getRootAlias(), true, false);
/* 1639:     */     
/* 1640:     */ 
/* 1641:     */ 
/* 1642:     */ 
/* 1643:     */ 
/* 1644:     */ 
/* 1645:     */ 
/* 1646:     */ 
/* 1647:     */ 
/* 1648:     */ 
/* 1649:     */ 
/* 1650:     */ 
/* 1651:1639 */     return select.setSelectClause(selectClause).setFromClause(fromClause).setOuterJoins("", "").setWhereClause(whereClause).toStatementString();
/* 1652:     */   }
/* 1653:     */   
/* 1654:     */   public Object forceVersionIncrement(Serializable id, Object currentVersion, SessionImplementor session)
/* 1655:     */   {
/* 1656:1647 */     if (!isVersioned()) {
/* 1657:1648 */       throw new AssertionFailure("cannot force version increment on non-versioned entity");
/* 1658:     */     }
/* 1659:1651 */     if (isVersionPropertyGenerated()) {
/* 1660:1654 */       throw new HibernateException("LockMode.FORCE is currently not supported for generated version properties");
/* 1661:     */     }
/* 1662:1657 */     Object nextVersion = getVersionType().next(currentVersion, session);
/* 1663:1658 */     if (LOG.isTraceEnabled()) {
/* 1664:1658 */       LOG.trace("Forcing version increment [" + MessageHelper.infoString(this, id, getFactory()) + "; " + getVersionType().toLoggableString(currentVersion, getFactory()) + " -> " + getVersionType().toLoggableString(nextVersion, getFactory()) + "]");
/* 1665:     */     }
/* 1666:1663 */     String versionIncrementString = generateVersionIncrementUpdateString();
/* 1667:1664 */     PreparedStatement st = null;
/* 1668:     */     try
/* 1669:     */     {
/* 1670:1666 */       st = session.getTransactionCoordinator().getJdbcCoordinator().getStatementPreparer().prepareStatement(versionIncrementString, false);
/* 1671:     */       try
/* 1672:     */       {
/* 1673:1671 */         getVersionType().nullSafeSet(st, nextVersion, 1, session);
/* 1674:1672 */         getIdentifierType().nullSafeSet(st, id, 2, session);
/* 1675:1673 */         getVersionType().nullSafeSet(st, currentVersion, 2 + getIdentifierColumnSpan(), session);
/* 1676:1674 */         int rows = st.executeUpdate();
/* 1677:1675 */         if (rows != 1) {
/* 1678:1676 */           throw new StaleObjectStateException(getEntityName(), id);
/* 1679:     */         }
/* 1680:     */       }
/* 1681:     */       finally
/* 1682:     */       {
/* 1683:1680 */         st.close();
/* 1684:     */       }
/* 1685:     */     }
/* 1686:     */     catch (SQLException sqle)
/* 1687:     */     {
/* 1688:1684 */       throw getFactory().getSQLExceptionHelper().convert(sqle, "could not retrieve version: " + MessageHelper.infoString(this, id, getFactory()), getVersionSelectString());
/* 1689:     */     }
/* 1690:1692 */     return nextVersion;
/* 1691:     */   }
/* 1692:     */   
/* 1693:     */   private String generateVersionIncrementUpdateString()
/* 1694:     */   {
/* 1695:1696 */     Update update = new Update(getFactory().getDialect());
/* 1696:1697 */     update.setTableName(getTableName(0));
/* 1697:1698 */     if (getFactory().getSettings().isCommentsEnabled()) {
/* 1698:1699 */       update.setComment("forced version increment");
/* 1699:     */     }
/* 1700:1701 */     update.addColumn(getVersionColumnName());
/* 1701:1702 */     update.addPrimaryKeyColumns(getIdentifierColumnNames());
/* 1702:1703 */     update.setVersionColumnName(getVersionColumnName());
/* 1703:1704 */     return update.toStatementString();
/* 1704:     */   }
/* 1705:     */   
/* 1706:     */   /* Error */
/* 1707:     */   public Object getCurrentVersion(Serializable id, SessionImplementor session)
/* 1708:     */     throws HibernateException
/* 1709:     */   {
/* 1710:     */     // Byte code:
/* 1711:     */     //   0: getstatic 287	org/hibernate/persister/entity/AbstractEntityPersister:LOG	Lorg/hibernate/internal/CoreMessageLogger;
/* 1712:     */     //   3: invokeinterface 288 1 0
/* 1713:     */     //   8: ifeq +23 -> 31
/* 1714:     */     //   11: getstatic 287	org/hibernate/persister/entity/AbstractEntityPersister:LOG	Lorg/hibernate/internal/CoreMessageLogger;
/* 1715:     */     //   14: ldc_w 463
/* 1716:     */     //   17: aload_0
/* 1717:     */     //   18: aload_1
/* 1718:     */     //   19: aload_0
/* 1719:     */     //   20: invokevirtual 290	org/hibernate/persister/entity/AbstractEntityPersister:getFactory	()Lorg/hibernate/engine/spi/SessionFactoryImplementor;
/* 1720:     */     //   23: invokestatic 291	org/hibernate/pretty/MessageHelper:infoString	(Lorg/hibernate/persister/entity/EntityPersister;Ljava/lang/Object;Lorg/hibernate/engine/spi/SessionFactoryImplementor;)Ljava/lang/String;
/* 1721:     */     //   26: invokeinterface 377 3 0
/* 1722:     */     //   31: aload_2
/* 1723:     */     //   32: invokeinterface 311 1 0
/* 1724:     */     //   37: invokeinterface 312 1 0
/* 1725:     */     //   42: invokeinterface 313 1 0
/* 1726:     */     //   47: aload_0
/* 1727:     */     //   48: invokevirtual 452	org/hibernate/persister/entity/AbstractEntityPersister:getVersionSelectString	()Ljava/lang/String;
/* 1728:     */     //   51: invokeinterface 314 2 0
/* 1729:     */     //   56: astore_3
/* 1730:     */     //   57: aload_0
/* 1731:     */     //   58: invokevirtual 294	org/hibernate/persister/entity/AbstractEntityPersister:getIdentifierType	()Lorg/hibernate/type/Type;
/* 1732:     */     //   61: aload_3
/* 1733:     */     //   62: aload_1
/* 1734:     */     //   63: iconst_1
/* 1735:     */     //   64: aload_2
/* 1736:     */     //   65: invokeinterface 315 5 0
/* 1737:     */     //   70: aload_3
/* 1738:     */     //   71: invokeinterface 316 1 0
/* 1739:     */     //   76: astore 4
/* 1740:     */     //   78: aload 4
/* 1741:     */     //   80: invokeinterface 317 1 0
/* 1742:     */     //   85: ifne +22 -> 107
/* 1743:     */     //   88: aconst_null
/* 1744:     */     //   89: astore 5
/* 1745:     */     //   91: aload 4
/* 1746:     */     //   93: invokeinterface 321 1 0
/* 1747:     */     //   98: aload_3
/* 1748:     */     //   99: invokeinterface 322 1 0
/* 1749:     */     //   104: aload 5
/* 1750:     */     //   106: areturn
/* 1751:     */     //   107: aload_0
/* 1752:     */     //   108: invokevirtual 31	org/hibernate/persister/entity/AbstractEntityPersister:isVersioned	()Z
/* 1753:     */     //   111: ifne +22 -> 133
/* 1754:     */     //   114: aload_0
/* 1755:     */     //   115: astore 5
/* 1756:     */     //   117: aload 4
/* 1757:     */     //   119: invokeinterface 321 1 0
/* 1758:     */     //   124: aload_3
/* 1759:     */     //   125: invokeinterface 322 1 0
/* 1760:     */     //   130: aload 5
/* 1761:     */     //   132: areturn
/* 1762:     */     //   133: aload_0
/* 1763:     */     //   134: invokevirtual 437	org/hibernate/persister/entity/AbstractEntityPersister:getVersionType	()Lorg/hibernate/type/VersionType;
/* 1764:     */     //   137: aload 4
/* 1765:     */     //   139: aload_0
/* 1766:     */     //   140: invokevirtual 458	org/hibernate/persister/entity/AbstractEntityPersister:getVersionColumnName	()Ljava/lang/String;
/* 1767:     */     //   143: aload_2
/* 1768:     */     //   144: aconst_null
/* 1769:     */     //   145: invokeinterface 464 5 0
/* 1770:     */     //   150: astore 5
/* 1771:     */     //   152: aload 4
/* 1772:     */     //   154: invokeinterface 321 1 0
/* 1773:     */     //   159: aload_3
/* 1774:     */     //   160: invokeinterface 322 1 0
/* 1775:     */     //   165: aload 5
/* 1776:     */     //   167: areturn
/* 1777:     */     //   168: astore 6
/* 1778:     */     //   170: aload 4
/* 1779:     */     //   172: invokeinterface 321 1 0
/* 1780:     */     //   177: aload 6
/* 1781:     */     //   179: athrow
/* 1782:     */     //   180: astore 7
/* 1783:     */     //   182: aload_3
/* 1784:     */     //   183: invokeinterface 322 1 0
/* 1785:     */     //   188: aload 7
/* 1786:     */     //   190: athrow
/* 1787:     */     //   191: astore_3
/* 1788:     */     //   192: aload_0
/* 1789:     */     //   193: invokevirtual 290	org/hibernate/persister/entity/AbstractEntityPersister:getFactory	()Lorg/hibernate/engine/spi/SessionFactoryImplementor;
/* 1790:     */     //   196: invokeinterface 325 1 0
/* 1791:     */     //   201: aload_3
/* 1792:     */     //   202: new 97	java/lang/StringBuilder
/* 1793:     */     //   205: dup
/* 1794:     */     //   206: invokespecial 98	java/lang/StringBuilder:<init>	()V
/* 1795:     */     //   209: ldc_w 451
/* 1796:     */     //   212: invokevirtual 100	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 1797:     */     //   215: aload_0
/* 1798:     */     //   216: aload_1
/* 1799:     */     //   217: aload_0
/* 1800:     */     //   218: invokevirtual 290	org/hibernate/persister/entity/AbstractEntityPersister:getFactory	()Lorg/hibernate/engine/spi/SessionFactoryImplementor;
/* 1801:     */     //   221: invokestatic 291	org/hibernate/pretty/MessageHelper:infoString	(Lorg/hibernate/persister/entity/EntityPersister;Ljava/lang/Object;Lorg/hibernate/engine/spi/SessionFactoryImplementor;)Ljava/lang/String;
/* 1802:     */     //   224: invokevirtual 100	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 1803:     */     //   227: invokevirtual 102	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 1804:     */     //   230: aload_0
/* 1805:     */     //   231: invokevirtual 452	org/hibernate/persister/entity/AbstractEntityPersister:getVersionSelectString	()Ljava/lang/String;
/* 1806:     */     //   234: invokevirtual 327	org/hibernate/engine/jdbc/spi/SqlExceptionHelper:convert	(Ljava/sql/SQLException;Ljava/lang/String;Ljava/lang/String;)Lorg/hibernate/JDBCException;
/* 1807:     */     //   237: athrow
/* 1808:     */     // Line number table:
/* 1809:     */     //   Java source line #1712	-> byte code offset #0
/* 1810:     */     //   Java source line #1713	-> byte code offset #11
/* 1811:     */     //   Java source line #1717	-> byte code offset #31
/* 1812:     */     //   Java source line #1722	-> byte code offset #57
/* 1813:     */     //   Java source line #1723	-> byte code offset #70
/* 1814:     */     //   Java source line #1725	-> byte code offset #78
/* 1815:     */     //   Java source line #1726	-> byte code offset #88
/* 1816:     */     //   Java source line #1734	-> byte code offset #91
/* 1817:     */     //   Java source line #1738	-> byte code offset #98
/* 1818:     */     //   Java source line #1728	-> byte code offset #107
/* 1819:     */     //   Java source line #1729	-> byte code offset #114
/* 1820:     */     //   Java source line #1734	-> byte code offset #117
/* 1821:     */     //   Java source line #1738	-> byte code offset #124
/* 1822:     */     //   Java source line #1731	-> byte code offset #133
/* 1823:     */     //   Java source line #1734	-> byte code offset #152
/* 1824:     */     //   Java source line #1738	-> byte code offset #159
/* 1825:     */     //   Java source line #1734	-> byte code offset #168
/* 1826:     */     //   Java source line #1738	-> byte code offset #180
/* 1827:     */     //   Java source line #1741	-> byte code offset #191
/* 1828:     */     //   Java source line #1742	-> byte code offset #192
/* 1829:     */     // Local variable table:
/* 1830:     */     //   start	length	slot	name	signature
/* 1831:     */     //   0	238	0	this	AbstractEntityPersister
/* 1832:     */     //   0	238	1	id	Serializable
/* 1833:     */     //   0	238	2	session	SessionImplementor
/* 1834:     */     //   56	127	3	st	PreparedStatement
/* 1835:     */     //   191	11	3	e	SQLException
/* 1836:     */     //   76	95	4	rs	ResultSet
/* 1837:     */     //   89	77	5	localObject1	Object
/* 1838:     */     //   168	10	6	localObject2	Object
/* 1839:     */     //   180	9	7	localObject3	Object
/* 1840:     */     // Exception table:
/* 1841:     */     //   from	to	target	type
/* 1842:     */     //   78	91	168	finally
/* 1843:     */     //   107	117	168	finally
/* 1844:     */     //   133	152	168	finally
/* 1845:     */     //   168	170	168	finally
/* 1846:     */     //   57	98	180	finally
/* 1847:     */     //   107	124	180	finally
/* 1848:     */     //   133	159	180	finally
/* 1849:     */     //   168	182	180	finally
/* 1850:     */     //   31	104	191	java/sql/SQLException
/* 1851:     */     //   107	130	191	java/sql/SQLException
/* 1852:     */     //   133	165	191	java/sql/SQLException
/* 1853:     */     //   168	191	191	java/sql/SQLException
/* 1854:     */   }
/* 1855:     */   
/* 1856:     */   protected void initLockers()
/* 1857:     */   {
/* 1858:1751 */     this.lockers.put(LockMode.READ, generateLocker(LockMode.READ));
/* 1859:1752 */     this.lockers.put(LockMode.UPGRADE, generateLocker(LockMode.UPGRADE));
/* 1860:1753 */     this.lockers.put(LockMode.UPGRADE_NOWAIT, generateLocker(LockMode.UPGRADE_NOWAIT));
/* 1861:1754 */     this.lockers.put(LockMode.FORCE, generateLocker(LockMode.FORCE));
/* 1862:1755 */     this.lockers.put(LockMode.PESSIMISTIC_READ, generateLocker(LockMode.PESSIMISTIC_READ));
/* 1863:1756 */     this.lockers.put(LockMode.PESSIMISTIC_WRITE, generateLocker(LockMode.PESSIMISTIC_WRITE));
/* 1864:1757 */     this.lockers.put(LockMode.PESSIMISTIC_FORCE_INCREMENT, generateLocker(LockMode.PESSIMISTIC_FORCE_INCREMENT));
/* 1865:1758 */     this.lockers.put(LockMode.OPTIMISTIC, generateLocker(LockMode.OPTIMISTIC));
/* 1866:1759 */     this.lockers.put(LockMode.OPTIMISTIC_FORCE_INCREMENT, generateLocker(LockMode.OPTIMISTIC_FORCE_INCREMENT));
/* 1867:     */   }
/* 1868:     */   
/* 1869:     */   protected LockingStrategy generateLocker(LockMode lockMode)
/* 1870:     */   {
/* 1871:1763 */     return this.factory.getDialect().getLockingStrategy(this, lockMode);
/* 1872:     */   }
/* 1873:     */   
/* 1874:     */   private LockingStrategy getLocker(LockMode lockMode)
/* 1875:     */   {
/* 1876:1767 */     return (LockingStrategy)this.lockers.get(lockMode);
/* 1877:     */   }
/* 1878:     */   
/* 1879:     */   public void lock(Serializable id, Object version, Object object, LockMode lockMode, SessionImplementor session)
/* 1880:     */     throws HibernateException
/* 1881:     */   {
/* 1882:1776 */     getLocker(lockMode).lock(id, version, object, -1, session);
/* 1883:     */   }
/* 1884:     */   
/* 1885:     */   public void lock(Serializable id, Object version, Object object, LockOptions lockOptions, SessionImplementor session)
/* 1886:     */     throws HibernateException
/* 1887:     */   {
/* 1888:1785 */     getLocker(lockOptions.getLockMode()).lock(id, version, object, lockOptions.getTimeOut(), session);
/* 1889:     */   }
/* 1890:     */   
/* 1891:     */   public String getRootTableName()
/* 1892:     */   {
/* 1893:1789 */     return getSubclassTableName(0);
/* 1894:     */   }
/* 1895:     */   
/* 1896:     */   public String getRootTableAlias(String drivingAlias)
/* 1897:     */   {
/* 1898:1793 */     return drivingAlias;
/* 1899:     */   }
/* 1900:     */   
/* 1901:     */   public String[] getRootTableIdentifierColumnNames()
/* 1902:     */   {
/* 1903:1797 */     return getRootTableKeyColumnNames();
/* 1904:     */   }
/* 1905:     */   
/* 1906:     */   public String[] toColumns(String alias, String propertyName)
/* 1907:     */     throws QueryException
/* 1908:     */   {
/* 1909:1801 */     return this.propertyMapping.toColumns(alias, propertyName);
/* 1910:     */   }
/* 1911:     */   
/* 1912:     */   public String[] toColumns(String propertyName)
/* 1913:     */     throws QueryException
/* 1914:     */   {
/* 1915:1805 */     return this.propertyMapping.getColumnNames(propertyName);
/* 1916:     */   }
/* 1917:     */   
/* 1918:     */   public Type toType(String propertyName)
/* 1919:     */     throws QueryException
/* 1920:     */   {
/* 1921:1809 */     return this.propertyMapping.toType(propertyName);
/* 1922:     */   }
/* 1923:     */   
/* 1924:     */   public String[] getPropertyColumnNames(String propertyName)
/* 1925:     */   {
/* 1926:1813 */     return this.propertyMapping.getColumnNames(propertyName);
/* 1927:     */   }
/* 1928:     */   
/* 1929:     */   public int getSubclassPropertyTableNumber(String propertyPath)
/* 1930:     */   {
/* 1931:1825 */     String rootPropertyName = StringHelper.root(propertyPath);
/* 1932:1826 */     Type type = this.propertyMapping.toType(rootPropertyName);
/* 1933:1827 */     if (type.isAssociationType())
/* 1934:     */     {
/* 1935:1828 */       AssociationType assocType = (AssociationType)type;
/* 1936:1829 */       if (assocType.useLHSPrimaryKey()) {
/* 1937:1831 */         return 0;
/* 1938:     */       }
/* 1939:1833 */       if (type.isCollectionType()) {
/* 1940:1835 */         rootPropertyName = assocType.getLHSPropertyName();
/* 1941:     */       }
/* 1942:     */     }
/* 1943:1846 */     int index = ArrayHelper.indexOf(getSubclassPropertyNameClosure(), rootPropertyName);
/* 1944:1847 */     return index == -1 ? 0 : getSubclassPropertyTableNumber(index);
/* 1945:     */   }
/* 1946:     */   
/* 1947:     */   public Queryable.Declarer getSubclassPropertyDeclarer(String propertyPath)
/* 1948:     */   {
/* 1949:1851 */     int tableIndex = getSubclassPropertyTableNumber(propertyPath);
/* 1950:1852 */     if (tableIndex == 0) {
/* 1951:1853 */       return Queryable.Declarer.CLASS;
/* 1952:     */     }
/* 1953:1855 */     if (isClassOrSuperclassTable(tableIndex)) {
/* 1954:1856 */       return Queryable.Declarer.SUPERCLASS;
/* 1955:     */     }
/* 1956:1859 */     return Queryable.Declarer.SUBCLASS;
/* 1957:     */   }
/* 1958:     */   
/* 1959:     */   public DiscriminatorMetadata getTypeDiscriminatorMetadata()
/* 1960:     */   {
/* 1961:1866 */     if (this.discriminatorMetadata == null) {
/* 1962:1867 */       this.discriminatorMetadata = buildTypeDiscriminatorMetadata();
/* 1963:     */     }
/* 1964:1869 */     return this.discriminatorMetadata;
/* 1965:     */   }
/* 1966:     */   
/* 1967:     */   private DiscriminatorMetadata buildTypeDiscriminatorMetadata()
/* 1968:     */   {
/* 1969:1873 */     new DiscriminatorMetadata()
/* 1970:     */     {
/* 1971:     */       public String getSqlFragment(String sqlQualificationAlias)
/* 1972:     */       {
/* 1973:1875 */         return AbstractEntityPersister.this.toColumns(sqlQualificationAlias, "class")[0];
/* 1974:     */       }
/* 1975:     */       
/* 1976:     */       public Type getResolutionType()
/* 1977:     */       {
/* 1978:1879 */         return new DiscriminatorType(AbstractEntityPersister.this.getDiscriminatorType(), AbstractEntityPersister.this);
/* 1979:     */       }
/* 1980:     */     };
/* 1981:     */   }
/* 1982:     */   
/* 1983:     */   protected String generateTableAlias(String rootAlias, int tableNumber)
/* 1984:     */   {
/* 1985:1885 */     if (tableNumber == 0) {
/* 1986:1886 */       return rootAlias;
/* 1987:     */     }
/* 1988:1888 */     StringBuffer buf = new StringBuffer().append(rootAlias);
/* 1989:1889 */     if (!rootAlias.endsWith("_")) {
/* 1990:1890 */       buf.append('_');
/* 1991:     */     }
/* 1992:1892 */     return tableNumber + '_';
/* 1993:     */   }
/* 1994:     */   
/* 1995:     */   public String[] toColumns(String name, int i)
/* 1996:     */   {
/* 1997:1896 */     String alias = generateTableAlias(name, getSubclassPropertyTableNumber(i));
/* 1998:1897 */     String[] cols = getSubclassPropertyColumnNames(i);
/* 1999:1898 */     String[] templates = getSubclassPropertyFormulaTemplateClosure()[i];
/* 2000:1899 */     String[] result = new String[cols.length];
/* 2001:1900 */     for (int j = 0; j < cols.length; j++) {
/* 2002:1901 */       if (cols[j] == null) {
/* 2003:1902 */         result[j] = StringHelper.replace(templates[j], "$PlaceHolder$", alias);
/* 2004:     */       } else {
/* 2005:1905 */         result[j] = StringHelper.qualify(alias, cols[j]);
/* 2006:     */       }
/* 2007:     */     }
/* 2008:1908 */     return result;
/* 2009:     */   }
/* 2010:     */   
/* 2011:     */   private int getSubclassPropertyIndex(String propertyName)
/* 2012:     */   {
/* 2013:1912 */     return ArrayHelper.indexOf(this.subclassPropertyNameClosure, propertyName);
/* 2014:     */   }
/* 2015:     */   
/* 2016:     */   protected String[] getPropertySubclassNames()
/* 2017:     */   {
/* 2018:1916 */     return this.propertySubclassNames;
/* 2019:     */   }
/* 2020:     */   
/* 2021:     */   public String[] getPropertyColumnNames(int i)
/* 2022:     */   {
/* 2023:1920 */     return this.propertyColumnNames[i];
/* 2024:     */   }
/* 2025:     */   
/* 2026:     */   public String[] getPropertyColumnWriters(int i)
/* 2027:     */   {
/* 2028:1924 */     return this.propertyColumnWriters[i];
/* 2029:     */   }
/* 2030:     */   
/* 2031:     */   protected int getPropertyColumnSpan(int i)
/* 2032:     */   {
/* 2033:1928 */     return this.propertyColumnSpans[i];
/* 2034:     */   }
/* 2035:     */   
/* 2036:     */   protected boolean hasFormulaProperties()
/* 2037:     */   {
/* 2038:1932 */     return this.hasFormulaProperties;
/* 2039:     */   }
/* 2040:     */   
/* 2041:     */   public FetchMode getFetchMode(int i)
/* 2042:     */   {
/* 2043:1936 */     return this.subclassPropertyFetchModeClosure[i];
/* 2044:     */   }
/* 2045:     */   
/* 2046:     */   public CascadeStyle getCascadeStyle(int i)
/* 2047:     */   {
/* 2048:1940 */     return this.subclassPropertyCascadeStyleClosure[i];
/* 2049:     */   }
/* 2050:     */   
/* 2051:     */   public Type getSubclassPropertyType(int i)
/* 2052:     */   {
/* 2053:1944 */     return this.subclassPropertyTypeClosure[i];
/* 2054:     */   }
/* 2055:     */   
/* 2056:     */   public String getSubclassPropertyName(int i)
/* 2057:     */   {
/* 2058:1948 */     return this.subclassPropertyNameClosure[i];
/* 2059:     */   }
/* 2060:     */   
/* 2061:     */   public int countSubclassProperties()
/* 2062:     */   {
/* 2063:1952 */     return this.subclassPropertyTypeClosure.length;
/* 2064:     */   }
/* 2065:     */   
/* 2066:     */   public String[] getSubclassPropertyColumnNames(int i)
/* 2067:     */   {
/* 2068:1956 */     return this.subclassPropertyColumnNameClosure[i];
/* 2069:     */   }
/* 2070:     */   
/* 2071:     */   public boolean isDefinedOnSubclass(int i)
/* 2072:     */   {
/* 2073:1960 */     return this.propertyDefinedOnSubclass[i];
/* 2074:     */   }
/* 2075:     */   
/* 2076:     */   protected String[][] getSubclassPropertyFormulaTemplateClosure()
/* 2077:     */   {
/* 2078:1964 */     return this.subclassPropertyFormulaTemplateClosure;
/* 2079:     */   }
/* 2080:     */   
/* 2081:     */   protected Type[] getSubclassPropertyTypeClosure()
/* 2082:     */   {
/* 2083:1968 */     return this.subclassPropertyTypeClosure;
/* 2084:     */   }
/* 2085:     */   
/* 2086:     */   protected String[][] getSubclassPropertyColumnNameClosure()
/* 2087:     */   {
/* 2088:1972 */     return this.subclassPropertyColumnNameClosure;
/* 2089:     */   }
/* 2090:     */   
/* 2091:     */   public String[][] getSubclassPropertyColumnReaderClosure()
/* 2092:     */   {
/* 2093:1976 */     return this.subclassPropertyColumnReaderClosure;
/* 2094:     */   }
/* 2095:     */   
/* 2096:     */   public String[][] getSubclassPropertyColumnReaderTemplateClosure()
/* 2097:     */   {
/* 2098:1980 */     return this.subclassPropertyColumnReaderTemplateClosure;
/* 2099:     */   }
/* 2100:     */   
/* 2101:     */   protected String[] getSubclassPropertyNameClosure()
/* 2102:     */   {
/* 2103:1984 */     return this.subclassPropertyNameClosure;
/* 2104:     */   }
/* 2105:     */   
/* 2106:     */   protected String[] getSubclassPropertySubclassNameClosure()
/* 2107:     */   {
/* 2108:1988 */     return this.subclassPropertySubclassNameClosure;
/* 2109:     */   }
/* 2110:     */   
/* 2111:     */   protected String[] getSubclassColumnClosure()
/* 2112:     */   {
/* 2113:1992 */     return this.subclassColumnClosure;
/* 2114:     */   }
/* 2115:     */   
/* 2116:     */   protected String[] getSubclassColumnAliasClosure()
/* 2117:     */   {
/* 2118:1996 */     return this.subclassColumnAliasClosure;
/* 2119:     */   }
/* 2120:     */   
/* 2121:     */   public String[] getSubclassColumnReaderTemplateClosure()
/* 2122:     */   {
/* 2123:2000 */     return this.subclassColumnReaderTemplateClosure;
/* 2124:     */   }
/* 2125:     */   
/* 2126:     */   protected String[] getSubclassFormulaClosure()
/* 2127:     */   {
/* 2128:2004 */     return this.subclassFormulaClosure;
/* 2129:     */   }
/* 2130:     */   
/* 2131:     */   protected String[] getSubclassFormulaTemplateClosure()
/* 2132:     */   {
/* 2133:2008 */     return this.subclassFormulaTemplateClosure;
/* 2134:     */   }
/* 2135:     */   
/* 2136:     */   protected String[] getSubclassFormulaAliasClosure()
/* 2137:     */   {
/* 2138:2012 */     return this.subclassFormulaAliasClosure;
/* 2139:     */   }
/* 2140:     */   
/* 2141:     */   public String[] getSubclassPropertyColumnAliases(String propertyName, String suffix)
/* 2142:     */   {
/* 2143:2016 */     String[] rawAliases = (String[])this.subclassPropertyAliases.get(propertyName);
/* 2144:2018 */     if (rawAliases == null) {
/* 2145:2019 */       return null;
/* 2146:     */     }
/* 2147:2022 */     String[] result = new String[rawAliases.length];
/* 2148:2023 */     for (int i = 0; i < rawAliases.length; i++) {
/* 2149:2024 */       result[i] = new Alias(suffix).toUnquotedAliasString(rawAliases[i]);
/* 2150:     */     }
/* 2151:2026 */     return result;
/* 2152:     */   }
/* 2153:     */   
/* 2154:     */   public String[] getSubclassPropertyColumnNames(String propertyName)
/* 2155:     */   {
/* 2156:2031 */     return (String[])this.subclassPropertyColumnNames.get(propertyName);
/* 2157:     */   }
/* 2158:     */   
/* 2159:     */   protected void initSubclassPropertyAliasesMap(PersistentClass model)
/* 2160:     */     throws MappingException
/* 2161:     */   {
/* 2162:2043 */     internalInitSubclassPropertyAliasesMap(null, model.getSubclassPropertyClosureIterator());
/* 2163:2046 */     if (!this.entityMetamodel.hasNonIdentifierPropertyNamedId())
/* 2164:     */     {
/* 2165:2047 */       this.subclassPropertyAliases.put("id", getIdentifierAliases());
/* 2166:2048 */       this.subclassPropertyColumnNames.put("id", getIdentifierColumnNames());
/* 2167:     */     }
/* 2168:2052 */     if (hasIdentifierProperty())
/* 2169:     */     {
/* 2170:2053 */       this.subclassPropertyAliases.put(getIdentifierPropertyName(), getIdentifierAliases());
/* 2171:2054 */       this.subclassPropertyColumnNames.put(getIdentifierPropertyName(), getIdentifierColumnNames());
/* 2172:     */     }
/* 2173:2058 */     if (getIdentifierType().isComponentType())
/* 2174:     */     {
/* 2175:2060 */       CompositeType componentId = (CompositeType)getIdentifierType();
/* 2176:2061 */       String[] idPropertyNames = componentId.getPropertyNames();
/* 2177:2062 */       String[] idAliases = getIdentifierAliases();
/* 2178:2063 */       String[] idColumnNames = getIdentifierColumnNames();
/* 2179:2065 */       for (int i = 0; i < idPropertyNames.length; i++)
/* 2180:     */       {
/* 2181:2066 */         if (this.entityMetamodel.hasNonIdentifierPropertyNamedId())
/* 2182:     */         {
/* 2183:2067 */           this.subclassPropertyAliases.put("id." + idPropertyNames[i], new String[] { idAliases[i] });
/* 2184:     */           
/* 2185:     */ 
/* 2186:     */ 
/* 2187:2071 */           this.subclassPropertyColumnNames.put("id." + getIdentifierPropertyName() + "." + idPropertyNames[i], new String[] { idColumnNames[i] });
/* 2188:     */         }
/* 2189:2077 */         if (hasIdentifierProperty())
/* 2190:     */         {
/* 2191:2078 */           this.subclassPropertyAliases.put(getIdentifierPropertyName() + "." + idPropertyNames[i], new String[] { idAliases[i] });
/* 2192:     */           
/* 2193:     */ 
/* 2194:     */ 
/* 2195:2082 */           this.subclassPropertyColumnNames.put(getIdentifierPropertyName() + "." + idPropertyNames[i], new String[] { idColumnNames[i] });
/* 2196:     */         }
/* 2197:     */         else
/* 2198:     */         {
/* 2199:2089 */           this.subclassPropertyAliases.put(idPropertyNames[i], new String[] { idAliases[i] });
/* 2200:2090 */           this.subclassPropertyColumnNames.put(idPropertyNames[i], new String[] { idColumnNames[i] });
/* 2201:     */         }
/* 2202:     */       }
/* 2203:     */     }
/* 2204:2095 */     if (this.entityMetamodel.isPolymorphic())
/* 2205:     */     {
/* 2206:2096 */       this.subclassPropertyAliases.put("class", new String[] { getDiscriminatorAlias() });
/* 2207:2097 */       this.subclassPropertyColumnNames.put("class", new String[] { getDiscriminatorColumnName() });
/* 2208:     */     }
/* 2209:     */   }
/* 2210:     */   
/* 2211:     */   protected void initSubclassPropertyAliasesMap(EntityBinding model)
/* 2212:     */     throws MappingException
/* 2213:     */   {
/* 2214:2113 */     if (!this.entityMetamodel.hasNonIdentifierPropertyNamedId())
/* 2215:     */     {
/* 2216:2114 */       this.subclassPropertyAliases.put("id", getIdentifierAliases());
/* 2217:2115 */       this.subclassPropertyColumnNames.put("id", getIdentifierColumnNames());
/* 2218:     */     }
/* 2219:2119 */     if (hasIdentifierProperty())
/* 2220:     */     {
/* 2221:2120 */       this.subclassPropertyAliases.put(getIdentifierPropertyName(), getIdentifierAliases());
/* 2222:2121 */       this.subclassPropertyColumnNames.put(getIdentifierPropertyName(), getIdentifierColumnNames());
/* 2223:     */     }
/* 2224:2125 */     if (getIdentifierType().isComponentType())
/* 2225:     */     {
/* 2226:2127 */       CompositeType componentId = (CompositeType)getIdentifierType();
/* 2227:2128 */       String[] idPropertyNames = componentId.getPropertyNames();
/* 2228:2129 */       String[] idAliases = getIdentifierAliases();
/* 2229:2130 */       String[] idColumnNames = getIdentifierColumnNames();
/* 2230:2132 */       for (int i = 0; i < idPropertyNames.length; i++)
/* 2231:     */       {
/* 2232:2133 */         if (this.entityMetamodel.hasNonIdentifierPropertyNamedId())
/* 2233:     */         {
/* 2234:2134 */           this.subclassPropertyAliases.put("id." + idPropertyNames[i], new String[] { idAliases[i] });
/* 2235:     */           
/* 2236:     */ 
/* 2237:     */ 
/* 2238:2138 */           this.subclassPropertyColumnNames.put("id." + getIdentifierPropertyName() + "." + idPropertyNames[i], new String[] { idColumnNames[i] });
/* 2239:     */         }
/* 2240:2144 */         if (hasIdentifierProperty())
/* 2241:     */         {
/* 2242:2145 */           this.subclassPropertyAliases.put(getIdentifierPropertyName() + "." + idPropertyNames[i], new String[] { idAliases[i] });
/* 2243:     */           
/* 2244:     */ 
/* 2245:     */ 
/* 2246:2149 */           this.subclassPropertyColumnNames.put(getIdentifierPropertyName() + "." + idPropertyNames[i], new String[] { idColumnNames[i] });
/* 2247:     */         }
/* 2248:     */         else
/* 2249:     */         {
/* 2250:2156 */           this.subclassPropertyAliases.put(idPropertyNames[i], new String[] { idAliases[i] });
/* 2251:2157 */           this.subclassPropertyColumnNames.put(idPropertyNames[i], new String[] { idColumnNames[i] });
/* 2252:     */         }
/* 2253:     */       }
/* 2254:     */     }
/* 2255:2162 */     if (this.entityMetamodel.isPolymorphic())
/* 2256:     */     {
/* 2257:2163 */       this.subclassPropertyAliases.put("class", new String[] { getDiscriminatorAlias() });
/* 2258:2164 */       this.subclassPropertyColumnNames.put("class", new String[] { getDiscriminatorColumnName() });
/* 2259:     */     }
/* 2260:     */   }
/* 2261:     */   
/* 2262:     */   private void internalInitSubclassPropertyAliasesMap(String path, Iterator propertyIterator)
/* 2263:     */   {
/* 2264:2170 */     while (propertyIterator.hasNext())
/* 2265:     */     {
/* 2266:2172 */       Property prop = (Property)propertyIterator.next();
/* 2267:2173 */       String propname = path + "." + prop.getName();
/* 2268:2174 */       if (prop.isComposite())
/* 2269:     */       {
/* 2270:2175 */         Component component = (Component)prop.getValue();
/* 2271:2176 */         Iterator compProps = component.getPropertyIterator();
/* 2272:2177 */         internalInitSubclassPropertyAliasesMap(propname, compProps);
/* 2273:     */       }
/* 2274:     */       else
/* 2275:     */       {
/* 2276:2180 */         String[] aliases = new String[prop.getColumnSpan()];
/* 2277:2181 */         String[] cols = new String[prop.getColumnSpan()];
/* 2278:2182 */         Iterator colIter = prop.getColumnIterator();
/* 2279:2183 */         int l = 0;
/* 2280:2184 */         while (colIter.hasNext())
/* 2281:     */         {
/* 2282:2185 */           Selectable thing = (Selectable)colIter.next();
/* 2283:2186 */           aliases[l] = thing.getAlias(getFactory().getDialect(), prop.getValue().getTable());
/* 2284:2187 */           cols[l] = thing.getText(getFactory().getDialect());
/* 2285:2188 */           l++;
/* 2286:     */         }
/* 2287:2191 */         this.subclassPropertyAliases.put(propname, aliases);
/* 2288:2192 */         this.subclassPropertyColumnNames.put(propname, cols);
/* 2289:     */       }
/* 2290:     */     }
/* 2291:     */   }
/* 2292:     */   
/* 2293:     */   public Object loadByUniqueKey(String propertyName, Object uniqueKey, SessionImplementor session)
/* 2294:     */     throws HibernateException
/* 2295:     */   {
/* 2296:2202 */     return getAppropriateUniqueKeyLoader(propertyName, session).loadByUniqueKey(session, uniqueKey);
/* 2297:     */   }
/* 2298:     */   
/* 2299:     */   private EntityLoader getAppropriateUniqueKeyLoader(String propertyName, SessionImplementor session)
/* 2300:     */   {
/* 2301:2206 */     boolean useStaticLoader = (!session.getLoadQueryInfluencers().hasEnabledFilters()) && (!session.getLoadQueryInfluencers().hasEnabledFetchProfiles()) && (propertyName.indexOf('.') < 0);
/* 2302:2210 */     if (useStaticLoader) {
/* 2303:2211 */       return (EntityLoader)this.uniqueKeyLoaders.get(propertyName);
/* 2304:     */     }
/* 2305:2214 */     return createUniqueKeyLoader(this.propertyMapping.toType(propertyName), this.propertyMapping.toColumns(propertyName), session.getLoadQueryInfluencers());
/* 2306:     */   }
/* 2307:     */   
/* 2308:     */   public int getPropertyIndex(String propertyName)
/* 2309:     */   {
/* 2310:2223 */     return this.entityMetamodel.getPropertyIndex(propertyName);
/* 2311:     */   }
/* 2312:     */   
/* 2313:     */   protected void createUniqueKeyLoaders()
/* 2314:     */     throws MappingException
/* 2315:     */   {
/* 2316:2227 */     Type[] propertyTypes = getPropertyTypes();
/* 2317:2228 */     String[] propertyNames = getPropertyNames();
/* 2318:2229 */     for (int i = 0; i < this.entityMetamodel.getPropertySpan(); i++) {
/* 2319:2230 */       if (this.propertyUniqueness[i] != 0) {
/* 2320:2232 */         this.uniqueKeyLoaders.put(propertyNames[i], createUniqueKeyLoader(propertyTypes[i], getPropertyColumnNames(i), LoadQueryInfluencers.NONE));
/* 2321:     */       }
/* 2322:     */     }
/* 2323:     */   }
/* 2324:     */   
/* 2325:     */   private EntityLoader createUniqueKeyLoader(Type uniqueKeyType, String[] columns, LoadQueryInfluencers loadQueryInfluencers)
/* 2326:     */   {
/* 2327:2249 */     if (uniqueKeyType.isEntityType())
/* 2328:     */     {
/* 2329:2250 */       String className = ((EntityType)uniqueKeyType).getAssociatedEntityName();
/* 2330:2251 */       uniqueKeyType = getFactory().getEntityPersister(className).getIdentifierType();
/* 2331:     */     }
/* 2332:2253 */     return new EntityLoader(this, columns, uniqueKeyType, 1, LockMode.NONE, getFactory(), loadQueryInfluencers);
/* 2333:     */   }
/* 2334:     */   
/* 2335:     */   protected String getSQLWhereString(String alias)
/* 2336:     */   {
/* 2337:2265 */     return StringHelper.replace(this.sqlWhereStringTemplate, "$PlaceHolder$", alias);
/* 2338:     */   }
/* 2339:     */   
/* 2340:     */   protected boolean hasWhere()
/* 2341:     */   {
/* 2342:2269 */     return this.sqlWhereString != null;
/* 2343:     */   }
/* 2344:     */   
/* 2345:     */   private void initOrdinaryPropertyPaths(Mapping mapping)
/* 2346:     */     throws MappingException
/* 2347:     */   {
/* 2348:2273 */     for (int i = 0; i < getSubclassPropertyNameClosure().length; i++) {
/* 2349:2274 */       this.propertyMapping.initPropertyPaths(getSubclassPropertyNameClosure()[i], getSubclassPropertyTypeClosure()[i], getSubclassPropertyColumnNameClosure()[i], getSubclassPropertyColumnReaderClosure()[i], getSubclassPropertyColumnReaderTemplateClosure()[i], getSubclassPropertyFormulaTemplateClosure()[i], mapping);
/* 2350:     */     }
/* 2351:     */   }
/* 2352:     */   
/* 2353:     */   private void initIdentifierPropertyPaths(Mapping mapping)
/* 2354:     */     throws MappingException
/* 2355:     */   {
/* 2356:2285 */     String idProp = getIdentifierPropertyName();
/* 2357:2286 */     if (idProp != null) {
/* 2358:2287 */       this.propertyMapping.initPropertyPaths(idProp, getIdentifierType(), getIdentifierColumnNames(), getIdentifierColumnReaders(), getIdentifierColumnReaderTemplates(), null, mapping);
/* 2359:     */     }
/* 2360:2290 */     if (this.entityMetamodel.getIdentifierProperty().isEmbedded()) {
/* 2361:2291 */       this.propertyMapping.initPropertyPaths(null, getIdentifierType(), getIdentifierColumnNames(), getIdentifierColumnReaders(), getIdentifierColumnReaderTemplates(), null, mapping);
/* 2362:     */     }
/* 2363:2294 */     if (!this.entityMetamodel.hasNonIdentifierPropertyNamedId()) {
/* 2364:2295 */       this.propertyMapping.initPropertyPaths("id", getIdentifierType(), getIdentifierColumnNames(), getIdentifierColumnReaders(), getIdentifierColumnReaderTemplates(), null, mapping);
/* 2365:     */     }
/* 2366:     */   }
/* 2367:     */   
/* 2368:     */   private void initDiscriminatorPropertyPath(Mapping mapping)
/* 2369:     */     throws MappingException
/* 2370:     */   {
/* 2371:2301 */     this.propertyMapping.initPropertyPaths("class", getDiscriminatorType(), new String[] { getDiscriminatorColumnName() }, new String[] { getDiscriminatorColumnReaders() }, new String[] { getDiscriminatorColumnReaderTemplate() }, new String[] { getDiscriminatorFormulaTemplate() }, getFactory());
/* 2372:     */   }
/* 2373:     */   
/* 2374:     */   protected void initPropertyPaths(Mapping mapping)
/* 2375:     */     throws MappingException
/* 2376:     */   {
/* 2377:2311 */     initOrdinaryPropertyPaths(mapping);
/* 2378:2312 */     initOrdinaryPropertyPaths(mapping);
/* 2379:2313 */     initIdentifierPropertyPaths(mapping);
/* 2380:2314 */     if (this.entityMetamodel.isPolymorphic()) {
/* 2381:2315 */       initDiscriminatorPropertyPath(mapping);
/* 2382:     */     }
/* 2383:     */   }
/* 2384:     */   
/* 2385:     */   protected UniqueEntityLoader createEntityLoader(LockMode lockMode, LoadQueryInfluencers loadQueryInfluencers)
/* 2386:     */     throws MappingException
/* 2387:     */   {
/* 2388:2323 */     return BatchingEntityLoader.createBatchingEntityLoader(this, this.batchSize, lockMode, getFactory(), loadQueryInfluencers);
/* 2389:     */   }
/* 2390:     */   
/* 2391:     */   protected UniqueEntityLoader createEntityLoader(LockOptions lockOptions, LoadQueryInfluencers loadQueryInfluencers)
/* 2392:     */     throws MappingException
/* 2393:     */   {
/* 2394:2336 */     return BatchingEntityLoader.createBatchingEntityLoader(this, this.batchSize, lockOptions, getFactory(), loadQueryInfluencers);
/* 2395:     */   }
/* 2396:     */   
/* 2397:     */   protected UniqueEntityLoader createEntityLoader(LockMode lockMode)
/* 2398:     */     throws MappingException
/* 2399:     */   {
/* 2400:2346 */     return createEntityLoader(lockMode, LoadQueryInfluencers.NONE);
/* 2401:     */   }
/* 2402:     */   
/* 2403:     */   protected boolean check(int rows, Serializable id, int tableNumber, Expectation expectation, PreparedStatement statement)
/* 2404:     */     throws HibernateException
/* 2405:     */   {
/* 2406:     */     try
/* 2407:     */     {
/* 2408:2351 */       expectation.verifyOutcome(rows, statement, -1);
/* 2409:     */     }
/* 2410:     */     catch (StaleStateException e)
/* 2411:     */     {
/* 2412:2354 */       if (!isNullableTable(tableNumber))
/* 2413:     */       {
/* 2414:2355 */         if (getFactory().getStatistics().isStatisticsEnabled()) {
/* 2415:2356 */           getFactory().getStatisticsImplementor().optimisticFailure(getEntityName());
/* 2416:     */         }
/* 2417:2359 */         throw new StaleObjectStateException(getEntityName(), id);
/* 2418:     */       }
/* 2419:2361 */       return false;
/* 2420:     */     }
/* 2421:     */     catch (TooManyRowsAffectedException e)
/* 2422:     */     {
/* 2423:2364 */       throw new HibernateException("Duplicate identifier in table for: " + MessageHelper.infoString(this, id, getFactory()));
/* 2424:     */     }
/* 2425:     */     catch (Throwable t)
/* 2426:     */     {
/* 2427:2370 */       return false;
/* 2428:     */     }
/* 2429:2372 */     return true;
/* 2430:     */   }
/* 2431:     */   
/* 2432:     */   protected String generateUpdateString(boolean[] includeProperty, int j, boolean useRowId)
/* 2433:     */   {
/* 2434:2376 */     return generateUpdateString(includeProperty, j, null, useRowId);
/* 2435:     */   }
/* 2436:     */   
/* 2437:     */   protected String generateUpdateString(boolean[] includeProperty, int j, Object[] oldFields, boolean useRowId)
/* 2438:     */   {
/* 2439:2387 */     Update update = new Update(getFactory().getDialect()).setTableName(getTableName(j));
/* 2440:2390 */     if (useRowId) {
/* 2441:2391 */       update.addPrimaryKeyColumns(new String[] { this.rowIdName });
/* 2442:     */     } else {
/* 2443:2394 */       update.addPrimaryKeyColumns(getKeyColumns(j));
/* 2444:     */     }
/* 2445:2397 */     boolean hasColumns = false;
/* 2446:2398 */     for (int i = 0; i < this.entityMetamodel.getPropertySpan(); i++) {
/* 2447:2399 */       if ((includeProperty[i] != 0) && (isPropertyOfTable(i, j)))
/* 2448:     */       {
/* 2449:2401 */         update.addColumns(getPropertyColumnNames(i), this.propertyColumnUpdateable[i], this.propertyColumnWriters[i]);
/* 2450:2402 */         hasColumns = (hasColumns) || (getPropertyColumnSpan(i) > 0);
/* 2451:     */       }
/* 2452:     */     }
/* 2453:2406 */     if ((j == 0) && (isVersioned()) && (this.entityMetamodel.getOptimisticLockStyle() == OptimisticLockStyle.VERSION))
/* 2454:     */     {
/* 2455:2410 */       if (checkVersion(includeProperty))
/* 2456:     */       {
/* 2457:2411 */         update.setVersionColumnName(getVersionColumnName());
/* 2458:2412 */         hasColumns = true;
/* 2459:     */       }
/* 2460:     */     }
/* 2461:2415 */     else if ((isAllOrDirtyOptLocking()) && (oldFields != null))
/* 2462:     */     {
/* 2463:2418 */       boolean[] includeInWhere = this.entityMetamodel.getOptimisticLockStyle() == OptimisticLockStyle.ALL ? getPropertyUpdateability() : includeProperty;
/* 2464:     */       
/* 2465:     */ 
/* 2466:     */ 
/* 2467:2422 */       boolean[] versionability = getPropertyVersionability();
/* 2468:2423 */       Type[] types = getPropertyTypes();
/* 2469:2424 */       for (int i = 0; i < this.entityMetamodel.getPropertySpan(); i++)
/* 2470:     */       {
/* 2471:2425 */         boolean include = (includeInWhere[i] != 0) && (isPropertyOfTable(i, j)) && (versionability[i] != 0);
/* 2472:2428 */         if (include)
/* 2473:     */         {
/* 2474:2431 */           String[] propertyColumnNames = getPropertyColumnNames(i);
/* 2475:2432 */           String[] propertyColumnWriters = getPropertyColumnWriters(i);
/* 2476:2433 */           boolean[] propertyNullness = types[i].toColumnNullness(oldFields[i], getFactory());
/* 2477:2434 */           for (int k = 0; k < propertyNullness.length; k++) {
/* 2478:2435 */             if (propertyNullness[k] != 0) {
/* 2479:2436 */               update.addWhereColumn(propertyColumnNames[k], "=" + propertyColumnWriters[k]);
/* 2480:     */             } else {
/* 2481:2439 */               update.addWhereColumn(propertyColumnNames[k], " is null");
/* 2482:     */             }
/* 2483:     */           }
/* 2484:     */         }
/* 2485:     */       }
/* 2486:     */     }
/* 2487:2447 */     if (getFactory().getSettings().isCommentsEnabled()) {
/* 2488:2448 */       update.setComment("update " + getEntityName());
/* 2489:     */     }
/* 2490:2451 */     return hasColumns ? update.toStatementString() : null;
/* 2491:     */   }
/* 2492:     */   
/* 2493:     */   private boolean checkVersion(boolean[] includeProperty)
/* 2494:     */   {
/* 2495:2455 */     return (includeProperty[getVersionProperty()] != 0) || (this.entityMetamodel.getPropertyUpdateGenerationInclusions()[getVersionProperty()] != ValueInclusion.NONE);
/* 2496:     */   }
/* 2497:     */   
/* 2498:     */   protected String generateInsertString(boolean[] includeProperty, int j)
/* 2499:     */   {
/* 2500:2460 */     return generateInsertString(false, includeProperty, j);
/* 2501:     */   }
/* 2502:     */   
/* 2503:     */   protected String generateInsertString(boolean identityInsert, boolean[] includeProperty)
/* 2504:     */   {
/* 2505:2464 */     return generateInsertString(identityInsert, includeProperty, 0);
/* 2506:     */   }
/* 2507:     */   
/* 2508:     */   protected String generateInsertString(boolean identityInsert, boolean[] includeProperty, int j)
/* 2509:     */   {
/* 2510:2475 */     Insert insert = new Insert(getFactory().getDialect()).setTableName(getTableName(j));
/* 2511:2479 */     for (int i = 0; i < this.entityMetamodel.getPropertySpan(); i++) {
/* 2512:2480 */       if ((includeProperty[i] != 0) && (isPropertyOfTable(i, j))) {
/* 2513:2482 */         insert.addColumns(getPropertyColumnNames(i), this.propertyColumnInsertable[i], this.propertyColumnWriters[i]);
/* 2514:     */       }
/* 2515:     */     }
/* 2516:2487 */     if (j == 0) {
/* 2517:2488 */       addDiscriminatorToInsert(insert);
/* 2518:     */     }
/* 2519:2492 */     if ((j == 0) && (identityInsert)) {
/* 2520:2493 */       insert.addIdentityColumn(getKeyColumns(0)[0]);
/* 2521:     */     } else {
/* 2522:2496 */       insert.addColumns(getKeyColumns(j));
/* 2523:     */     }
/* 2524:2499 */     if (getFactory().getSettings().isCommentsEnabled()) {
/* 2525:2500 */       insert.setComment("insert " + getEntityName());
/* 2526:     */     }
/* 2527:2503 */     String result = insert.toStatementString();
/* 2528:2506 */     if ((j == 0) && (identityInsert) && (useInsertSelectIdentity())) {
/* 2529:2507 */       result = getFactory().getDialect().appendIdentitySelectToInsert(result);
/* 2530:     */     }
/* 2531:2510 */     return result;
/* 2532:     */   }
/* 2533:     */   
/* 2534:     */   protected String generateIdentityInsertString(boolean[] includeProperty)
/* 2535:     */   {
/* 2536:2523 */     Insert insert = this.identityDelegate.prepareIdentifierGeneratingInsert();
/* 2537:2524 */     insert.setTableName(getTableName(0));
/* 2538:2527 */     for (int i = 0; i < this.entityMetamodel.getPropertySpan(); i++) {
/* 2539:2528 */       if ((includeProperty[i] != 0) && (isPropertyOfTable(i, 0))) {
/* 2540:2530 */         insert.addColumns(getPropertyColumnNames(i), this.propertyColumnInsertable[i], this.propertyColumnWriters[i]);
/* 2541:     */       }
/* 2542:     */     }
/* 2543:2535 */     addDiscriminatorToInsert(insert);
/* 2544:2539 */     if (getFactory().getSettings().isCommentsEnabled()) {
/* 2545:2540 */       insert.setComment("insert " + getEntityName());
/* 2546:     */     }
/* 2547:2543 */     return insert.toStatementString();
/* 2548:     */   }
/* 2549:     */   
/* 2550:     */   protected String generateDeleteString(int j)
/* 2551:     */   {
/* 2552:2550 */     Delete delete = new Delete().setTableName(getTableName(j)).addPrimaryKeyColumns(getKeyColumns(j));
/* 2553:2553 */     if (j == 0) {
/* 2554:2554 */       delete.setVersionColumnName(getVersionColumnName());
/* 2555:     */     }
/* 2556:2556 */     if (getFactory().getSettings().isCommentsEnabled()) {
/* 2557:2557 */       delete.setComment("delete " + getEntityName());
/* 2558:     */     }
/* 2559:2559 */     return delete.toStatementString();
/* 2560:     */   }
/* 2561:     */   
/* 2562:     */   protected int dehydrate(Serializable id, Object[] fields, boolean[] includeProperty, boolean[][] includeColumns, int j, PreparedStatement st, SessionImplementor session)
/* 2563:     */     throws HibernateException, SQLException
/* 2564:     */   {
/* 2565:2570 */     return dehydrate(id, fields, null, includeProperty, includeColumns, j, st, session, 1);
/* 2566:     */   }
/* 2567:     */   
/* 2568:     */   protected int dehydrate(Serializable id, Object[] fields, Object rowId, boolean[] includeProperty, boolean[][] includeColumns, int j, PreparedStatement ps, SessionImplementor session, int index)
/* 2569:     */     throws SQLException, HibernateException
/* 2570:     */   {
/* 2571:2587 */     if (LOG.isTraceEnabled()) {
/* 2572:2588 */       LOG.tracev("Dehydrating entity: {0}", MessageHelper.infoString(this, id, getFactory()));
/* 2573:     */     }
/* 2574:2591 */     for (int i = 0; i < this.entityMetamodel.getPropertySpan(); i++) {
/* 2575:2592 */       if ((includeProperty[i] != 0) && (isPropertyOfTable(i, j)))
/* 2576:     */       {
/* 2577:2593 */         getPropertyTypes()[i].nullSafeSet(ps, fields[i], index, includeColumns[i], session);
/* 2578:     */         
/* 2579:2595 */         index += ArrayHelper.countTrue(includeColumns[i]);
/* 2580:     */       }
/* 2581:     */     }
/* 2582:2599 */     if (rowId != null)
/* 2583:     */     {
/* 2584:2600 */       ps.setObject(index, rowId);
/* 2585:2601 */       index++;
/* 2586:     */     }
/* 2587:2603 */     else if (id != null)
/* 2588:     */     {
/* 2589:2604 */       getIdentifierType().nullSafeSet(ps, id, index, session);
/* 2590:2605 */       index += getIdentifierColumnSpan();
/* 2591:     */     }
/* 2592:2608 */     return index;
/* 2593:     */   }
/* 2594:     */   
/* 2595:     */   public Object[] hydrate(ResultSet rs, Serializable id, Object object, Loadable rootLoadable, String[][] suffixedPropertyColumns, boolean allProperties, SessionImplementor session)
/* 2596:     */     throws SQLException, HibernateException
/* 2597:     */   {
/* 2598:2626 */     if (LOG.isTraceEnabled()) {
/* 2599:2627 */       LOG.tracev("Hydrating entity: {0}", MessageHelper.infoString(this, id, getFactory()));
/* 2600:     */     }
/* 2601:2630 */     AbstractEntityPersister rootPersister = (AbstractEntityPersister)rootLoadable;
/* 2602:     */     
/* 2603:2632 */     boolean hasDeferred = rootPersister.hasSequentialSelect();
/* 2604:2633 */     PreparedStatement sequentialSelect = null;
/* 2605:2634 */     ResultSet sequentialResultSet = null;
/* 2606:2635 */     boolean sequentialSelectEmpty = false;
/* 2607:     */     try
/* 2608:     */     {
/* 2609:2638 */       if (hasDeferred)
/* 2610:     */       {
/* 2611:2639 */         String sql = rootPersister.getSequentialSelect(getEntityName());
/* 2612:2640 */         if (sql != null)
/* 2613:     */         {
/* 2614:2642 */           sequentialSelect = session.getTransactionCoordinator().getJdbcCoordinator().getStatementPreparer().prepareStatement(sql);
/* 2615:     */           
/* 2616:     */ 
/* 2617:     */ 
/* 2618:2646 */           rootPersister.getIdentifierType().nullSafeSet(sequentialSelect, id, 1, session);
/* 2619:2647 */           sequentialResultSet = sequentialSelect.executeQuery();
/* 2620:2648 */           if (!sequentialResultSet.next()) {
/* 2621:2670 */             sequentialSelectEmpty = true;
/* 2622:     */           }
/* 2623:     */         }
/* 2624:     */       }
/* 2625:2675 */       String[] propNames = getPropertyNames();
/* 2626:2676 */       Type[] types = getPropertyTypes();
/* 2627:2677 */       Object[] values = new Object[types.length];
/* 2628:2678 */       boolean[] laziness = getPropertyLaziness();
/* 2629:2679 */       String[] propSubclassNames = getSubclassPropertySubclassNameClosure();
/* 2630:2681 */       for (int i = 0; i < types.length; i++) {
/* 2631:2682 */         if (this.propertySelectable[i] == 0)
/* 2632:     */         {
/* 2633:2683 */           values[i] = BackrefPropertyAccessor.UNKNOWN;
/* 2634:     */         }
/* 2635:2685 */         else if ((allProperties) || (laziness[i] == 0))
/* 2636:     */         {
/* 2637:2687 */           boolean propertyIsDeferred = (hasDeferred) && (rootPersister.isSubclassPropertyDeferred(propNames[i], propSubclassNames[i]));
/* 2638:2689 */           if ((propertyIsDeferred) && (sequentialSelectEmpty))
/* 2639:     */           {
/* 2640:2690 */             values[i] = null;
/* 2641:     */           }
/* 2642:     */           else
/* 2643:     */           {
/* 2644:2693 */             ResultSet propertyResultSet = propertyIsDeferred ? sequentialResultSet : rs;
/* 2645:2694 */             String[] cols = propertyIsDeferred ? this.propertyColumnAliases[i] : suffixedPropertyColumns[i];
/* 2646:2695 */             values[i] = types[i].hydrate(propertyResultSet, cols, session, object);
/* 2647:     */           }
/* 2648:     */         }
/* 2649:     */         else
/* 2650:     */         {
/* 2651:2699 */           values[i] = LazyPropertyInitializer.UNFETCHED_PROPERTY;
/* 2652:     */         }
/* 2653:     */       }
/* 2654:2703 */       if (sequentialResultSet != null) {
/* 2655:2704 */         sequentialResultSet.close();
/* 2656:     */       }
/* 2657:2707 */       return values;
/* 2658:     */     }
/* 2659:     */     finally
/* 2660:     */     {
/* 2661:2711 */       if (sequentialSelect != null) {
/* 2662:2712 */         sequentialSelect.close();
/* 2663:     */       }
/* 2664:     */     }
/* 2665:     */   }
/* 2666:     */   
/* 2667:     */   protected boolean useInsertSelectIdentity()
/* 2668:     */   {
/* 2669:2718 */     return (!useGetGeneratedKeys()) && (getFactory().getDialect().supportsInsertSelectIdentity());
/* 2670:     */   }
/* 2671:     */   
/* 2672:     */   protected boolean useGetGeneratedKeys()
/* 2673:     */   {
/* 2674:2722 */     return getFactory().getSettings().isGetGeneratedKeysEnabled();
/* 2675:     */   }
/* 2676:     */   
/* 2677:     */   protected String getSequentialSelect(String entityName)
/* 2678:     */   {
/* 2679:2726 */     throw new UnsupportedOperationException("no sequential selects");
/* 2680:     */   }
/* 2681:     */   
/* 2682:     */   protected Serializable insert(final Object[] fields, final boolean[] notNull, String sql, final Object object, final SessionImplementor session)
/* 2683:     */     throws HibernateException
/* 2684:     */   {
/* 2685:2742 */     if (LOG.isTraceEnabled())
/* 2686:     */     {
/* 2687:2743 */       LOG.tracev("Inserting entity: {0} (native id)", getEntityName());
/* 2688:2744 */       if (isVersioned()) {
/* 2689:2745 */         LOG.tracev("Version: {0}", Versioning.getVersion(fields, this));
/* 2690:     */       }
/* 2691:     */     }
/* 2692:2749 */     Binder binder = new Binder()
/* 2693:     */     {
/* 2694:     */       public void bindValues(PreparedStatement ps)
/* 2695:     */         throws SQLException
/* 2696:     */       {
/* 2697:2751 */         AbstractEntityPersister.this.dehydrate(null, fields, notNull, AbstractEntityPersister.this.propertyColumnInsertable, 0, ps, session);
/* 2698:     */       }
/* 2699:     */       
/* 2700:     */       public Object getEntity()
/* 2701:     */       {
/* 2702:2754 */         return object;
/* 2703:     */       }
/* 2704:2756 */     };
/* 2705:2757 */     return this.identityDelegate.performInsert(sql, session, binder);
/* 2706:     */   }
/* 2707:     */   
/* 2708:     */   public String getIdentitySelectString()
/* 2709:     */   {
/* 2710:2762 */     return getFactory().getDialect().getIdentitySelectString(getTableName(0), getKeyColumns(0)[0], getIdentifierType().sqlTypes(getFactory())[0]);
/* 2711:     */   }
/* 2712:     */   
/* 2713:     */   public String getSelectByUniqueKeyString(String propertyName)
/* 2714:     */   {
/* 2715:2770 */     return new SimpleSelect(getFactory().getDialect()).setTableName(getTableName(0)).addColumns(getKeyColumns(0)).addCondition(getPropertyColumnNames(propertyName), "=?").toStatementString();
/* 2716:     */   }
/* 2717:     */   
/* 2718:     */   protected void insert(Serializable id, Object[] fields, boolean[] notNull, int j, String sql, Object object, SessionImplementor session)
/* 2719:     */     throws HibernateException
/* 2720:     */   {
/* 2721:2794 */     if (isInverseTable(j)) {
/* 2722:2795 */       return;
/* 2723:     */     }
/* 2724:2800 */     if ((isNullableTable(j)) && (isAllNull(fields, j))) {
/* 2725:2801 */       return;
/* 2726:     */     }
/* 2727:2804 */     if (LOG.isTraceEnabled())
/* 2728:     */     {
/* 2729:2805 */       LOG.tracev("Inserting entity: {0}", MessageHelper.infoString(this, id, getFactory()));
/* 2730:2806 */       if ((j == 0) && (isVersioned())) {
/* 2731:2807 */         LOG.tracev("Version: {0}", Versioning.getVersion(fields, this));
/* 2732:     */       }
/* 2733:     */     }
/* 2734:2811 */     Expectation expectation = Expectations.appropriateExpectation(this.insertResultCheckStyles[j]);
/* 2735:     */     
/* 2736:     */ 
/* 2737:2814 */     boolean useBatch = (j == 0) && (expectation.canBeBatched());
/* 2738:2815 */     if ((useBatch) && (this.inserBatchKey == null)) {
/* 2739:2816 */       this.inserBatchKey = new BasicBatchKey(getEntityName() + "#INSERT", expectation);
/* 2740:     */     }
/* 2741:2821 */     boolean callable = isInsertCallable(j);
/* 2742:     */     try
/* 2743:     */     {
/* 2744:     */       PreparedStatement insert;
/* 2745:     */       PreparedStatement insert;
/* 2746:2826 */       if (useBatch) {
/* 2747:2827 */         insert = session.getTransactionCoordinator().getJdbcCoordinator().getBatch(this.inserBatchKey).getBatchStatement(sql, callable);
/* 2748:     */       } else {
/* 2749:2833 */         insert = session.getTransactionCoordinator().getJdbcCoordinator().getStatementPreparer().prepareStatement(sql, callable);
/* 2750:     */       }
/* 2751:     */       try
/* 2752:     */       {
/* 2753:2840 */         int index = 1;
/* 2754:2841 */         index += expectation.prepare(insert);
/* 2755:     */         
/* 2756:     */ 
/* 2757:     */ 
/* 2758:     */ 
/* 2759:2846 */         dehydrate(id, fields, null, notNull, this.propertyColumnInsertable, j, insert, session, index);
/* 2760:2848 */         if (useBatch) {
/* 2761:2849 */           session.getTransactionCoordinator().getJdbcCoordinator().getBatch(this.inserBatchKey).addToBatch();
/* 2762:     */         } else {
/* 2763:2852 */           expectation.verifyOutcome(insert.executeUpdate(), insert, -1);
/* 2764:     */         }
/* 2765:     */       }
/* 2766:     */       catch (SQLException e)
/* 2767:     */       {
/* 2768:2857 */         if (useBatch) {
/* 2769:2858 */           session.getTransactionCoordinator().getJdbcCoordinator().abortBatch();
/* 2770:     */         }
/* 2771:2860 */         throw e;
/* 2772:     */       }
/* 2773:     */       finally
/* 2774:     */       {
/* 2775:2863 */         if (!useBatch) {
/* 2776:2864 */           insert.close();
/* 2777:     */         }
/* 2778:     */       }
/* 2779:     */     }
/* 2780:     */     catch (SQLException e)
/* 2781:     */     {
/* 2782:2869 */       throw getFactory().getSQLExceptionHelper().convert(e, "could not insert: " + MessageHelper.infoString(this), sql);
/* 2783:     */     }
/* 2784:     */   }
/* 2785:     */   
/* 2786:     */   protected void updateOrInsert(Serializable id, Object[] fields, Object[] oldFields, Object rowId, boolean[] includeProperty, int j, Object oldVersion, Object object, String sql, SessionImplementor session)
/* 2787:     */     throws HibernateException
/* 2788:     */   {
/* 2789:2893 */     if (!isInverseTable(j))
/* 2790:     */     {
/* 2791:     */       boolean isRowToUpdate;
/* 2792:     */       boolean isRowToUpdate;
/* 2793:2896 */       if ((isNullableTable(j)) && (oldFields != null) && (isAllNull(oldFields, j)))
/* 2794:     */       {
/* 2795:2898 */         isRowToUpdate = false;
/* 2796:     */       }
/* 2797:2900 */       else if ((isNullableTable(j)) && (isAllNull(fields, j)))
/* 2798:     */       {
/* 2799:2902 */         boolean isRowToUpdate = true;
/* 2800:2903 */         delete(id, oldVersion, j, object, getSQLDeleteStrings()[j], session, null);
/* 2801:     */       }
/* 2802:     */       else
/* 2803:     */       {
/* 2804:2908 */         isRowToUpdate = update(id, fields, oldFields, rowId, includeProperty, j, oldVersion, object, sql, session);
/* 2805:     */       }
/* 2806:2911 */       if ((!isRowToUpdate) && (!isAllNull(fields, j))) {
/* 2807:2915 */         insert(id, fields, getPropertyInsertability(), j, getSQLInsertStrings()[j], object, session);
/* 2808:     */       }
/* 2809:     */     }
/* 2810:     */   }
/* 2811:     */   
/* 2812:     */   /* Error */
/* 2813:     */   protected boolean update(Serializable id, Object[] fields, Object[] oldFields, Object rowId, boolean[] includeProperty, int j, Object oldVersion, Object object, String sql, SessionImplementor session)
/* 2814:     */     throws HibernateException
/* 2815:     */   {
/* 2816:     */     // Byte code:
/* 2817:     */     //   0: aload_0
/* 2818:     */     //   1: getfield 671	org/hibernate/persister/entity/AbstractEntityPersister:updateResultCheckStyles	[Lorg/hibernate/engine/spi/ExecuteUpdateResultCheckStyle;
/* 2819:     */     //   4: iload 6
/* 2820:     */     //   6: aaload
/* 2821:     */     //   7: invokestatic 651	org/hibernate/jdbc/Expectations:appropriateExpectation	(Lorg/hibernate/engine/spi/ExecuteUpdateResultCheckStyle;)Lorg/hibernate/jdbc/Expectation;
/* 2822:     */     //   10: astore 11
/* 2823:     */     //   12: iload 6
/* 2824:     */     //   14: ifne +24 -> 38
/* 2825:     */     //   17: aload 11
/* 2826:     */     //   19: invokeinterface 652 1 0
/* 2827:     */     //   24: ifeq +14 -> 38
/* 2828:     */     //   27: aload_0
/* 2829:     */     //   28: invokevirtual 672	org/hibernate/persister/entity/AbstractEntityPersister:isBatchable	()Z
/* 2830:     */     //   31: ifeq +7 -> 38
/* 2831:     */     //   34: iconst_1
/* 2832:     */     //   35: goto +4 -> 39
/* 2833:     */     //   38: iconst_0
/* 2834:     */     //   39: istore 12
/* 2835:     */     //   41: iload 12
/* 2836:     */     //   43: ifeq +46 -> 89
/* 2837:     */     //   46: aload_0
/* 2838:     */     //   47: getfield 673	org/hibernate/persister/entity/AbstractEntityPersister:updateBatchKey	Lorg/hibernate/engine/jdbc/batch/internal/BasicBatchKey;
/* 2839:     */     //   50: ifnonnull +39 -> 89
/* 2840:     */     //   53: aload_0
/* 2841:     */     //   54: new 654	org/hibernate/engine/jdbc/batch/internal/BasicBatchKey
/* 2842:     */     //   57: dup
/* 2843:     */     //   58: new 97	java/lang/StringBuilder
/* 2844:     */     //   61: dup
/* 2845:     */     //   62: invokespecial 98	java/lang/StringBuilder:<init>	()V
/* 2846:     */     //   65: aload_0
/* 2847:     */     //   66: invokevirtual 295	org/hibernate/persister/entity/AbstractEntityPersister:getEntityName	()Ljava/lang/String;
/* 2848:     */     //   69: invokevirtual 100	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 2849:     */     //   72: ldc_w 674
/* 2850:     */     //   75: invokevirtual 100	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 2851:     */     //   78: invokevirtual 102	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 2852:     */     //   81: aload 11
/* 2853:     */     //   83: invokespecial 656	org/hibernate/engine/jdbc/batch/internal/BasicBatchKey:<init>	(Ljava/lang/String;Lorg/hibernate/jdbc/Expectation;)V
/* 2854:     */     //   86: putfield 673	org/hibernate/persister/entity/AbstractEntityPersister:updateBatchKey	Lorg/hibernate/engine/jdbc/batch/internal/BasicBatchKey;
/* 2855:     */     //   89: aload_0
/* 2856:     */     //   90: iload 6
/* 2857:     */     //   92: invokevirtual 675	org/hibernate/persister/entity/AbstractEntityPersister:isUpdateCallable	(I)Z
/* 2858:     */     //   95: istore 13
/* 2859:     */     //   97: iload 6
/* 2860:     */     //   99: ifne +14 -> 113
/* 2861:     */     //   102: aload_0
/* 2862:     */     //   103: invokevirtual 31	org/hibernate/persister/entity/AbstractEntityPersister:isVersioned	()Z
/* 2863:     */     //   106: ifeq +7 -> 113
/* 2864:     */     //   109: iconst_1
/* 2865:     */     //   110: goto +4 -> 114
/* 2866:     */     //   113: iconst_0
/* 2867:     */     //   114: istore 14
/* 2868:     */     //   116: getstatic 287	org/hibernate/persister/entity/AbstractEntityPersister:LOG	Lorg/hibernate/internal/CoreMessageLogger;
/* 2869:     */     //   119: invokeinterface 288 1 0
/* 2870:     */     //   124: ifeq +47 -> 171
/* 2871:     */     //   127: getstatic 287	org/hibernate/persister/entity/AbstractEntityPersister:LOG	Lorg/hibernate/internal/CoreMessageLogger;
/* 2872:     */     //   130: ldc_w 676
/* 2873:     */     //   133: aload_0
/* 2874:     */     //   134: aload_1
/* 2875:     */     //   135: aload_0
/* 2876:     */     //   136: invokevirtual 290	org/hibernate/persister/entity/AbstractEntityPersister:getFactory	()Lorg/hibernate/engine/spi/SessionFactoryImplementor;
/* 2877:     */     //   139: invokestatic 291	org/hibernate/pretty/MessageHelper:infoString	(Lorg/hibernate/persister/entity/EntityPersister;Ljava/lang/Object;Lorg/hibernate/engine/spi/SessionFactoryImplementor;)Ljava/lang/String;
/* 2878:     */     //   142: invokeinterface 377 3 0
/* 2879:     */     //   147: iload 14
/* 2880:     */     //   149: ifeq +22 -> 171
/* 2881:     */     //   152: getstatic 287	org/hibernate/persister/entity/AbstractEntityPersister:LOG	Lorg/hibernate/internal/CoreMessageLogger;
/* 2882:     */     //   155: ldc_w 677
/* 2883:     */     //   158: aload 7
/* 2884:     */     //   160: aload_2
/* 2885:     */     //   161: aload_0
/* 2886:     */     //   162: invokevirtual 592	org/hibernate/persister/entity/AbstractEntityPersister:getVersionProperty	()I
/* 2887:     */     //   165: aaload
/* 2888:     */     //   166: invokeinterface 292 4 0
/* 2889:     */     //   171: iconst_1
/* 2890:     */     //   172: istore 15
/* 2891:     */     //   174: iload 12
/* 2892:     */     //   176: ifeq +38 -> 214
/* 2893:     */     //   179: aload 10
/* 2894:     */     //   181: invokeinterface 311 1 0
/* 2895:     */     //   186: invokeinterface 312 1 0
/* 2896:     */     //   191: aload_0
/* 2897:     */     //   192: getfield 673	org/hibernate/persister/entity/AbstractEntityPersister:updateBatchKey	Lorg/hibernate/engine/jdbc/batch/internal/BasicBatchKey;
/* 2898:     */     //   195: invokeinterface 658 2 0
/* 2899:     */     //   200: aload 9
/* 2900:     */     //   202: iload 13
/* 2901:     */     //   204: invokeinterface 659 3 0
/* 2902:     */     //   209: astore 16
/* 2903:     */     //   211: goto +31 -> 242
/* 2904:     */     //   214: aload 10
/* 2905:     */     //   216: invokeinterface 311 1 0
/* 2906:     */     //   221: invokeinterface 312 1 0
/* 2907:     */     //   226: invokeinterface 313 1 0
/* 2908:     */     //   231: aload 9
/* 2909:     */     //   233: iload 13
/* 2910:     */     //   235: invokeinterface 445 3 0
/* 2911:     */     //   240: astore 16
/* 2912:     */     //   242: iload 15
/* 2913:     */     //   244: aload 11
/* 2914:     */     //   246: aload 16
/* 2915:     */     //   248: invokeinterface 660 2 0
/* 2916:     */     //   253: iadd
/* 2917:     */     //   254: istore 15
/* 2918:     */     //   256: aload_0
/* 2919:     */     //   257: aload_1
/* 2920:     */     //   258: aload_2
/* 2921:     */     //   259: aload 4
/* 2922:     */     //   261: aload 5
/* 2923:     */     //   263: aload_0
/* 2924:     */     //   264: getfield 35	org/hibernate/persister/entity/AbstractEntityPersister:propertyColumnUpdateable	[[Z
/* 2925:     */     //   267: iload 6
/* 2926:     */     //   269: aload 16
/* 2927:     */     //   271: aload 10
/* 2928:     */     //   273: iload 15
/* 2929:     */     //   275: invokevirtual 618	org/hibernate/persister/entity/AbstractEntityPersister:dehydrate	(Ljava/io/Serializable;[Ljava/lang/Object;Ljava/lang/Object;[Z[[ZILjava/sql/PreparedStatement;Lorg/hibernate/engine/spi/SessionImplementor;I)I
/* 2930:     */     //   278: istore 15
/* 2931:     */     //   280: iload 14
/* 2932:     */     //   282: ifeq +45 -> 327
/* 2933:     */     //   285: aload_0
/* 2934:     */     //   286: getfield 3	org/hibernate/persister/entity/AbstractEntityPersister:entityMetamodel	Lorg/hibernate/tuple/entity/EntityMetamodel;
/* 2935:     */     //   289: invokevirtual 582	org/hibernate/tuple/entity/EntityMetamodel:getOptimisticLockStyle	()Lorg/hibernate/engine/OptimisticLockStyle;
/* 2936:     */     //   292: getstatic 336	org/hibernate/engine/OptimisticLockStyle:VERSION	Lorg/hibernate/engine/OptimisticLockStyle;
/* 2937:     */     //   295: if_acmpne +32 -> 327
/* 2938:     */     //   298: aload_0
/* 2939:     */     //   299: aload 5
/* 2940:     */     //   301: invokespecial 583	org/hibernate/persister/entity/AbstractEntityPersister:checkVersion	([Z)Z
/* 2941:     */     //   304: ifeq +182 -> 486
/* 2942:     */     //   307: aload_0
/* 2943:     */     //   308: invokevirtual 437	org/hibernate/persister/entity/AbstractEntityPersister:getVersionType	()Lorg/hibernate/type/VersionType;
/* 2944:     */     //   311: aload 16
/* 2945:     */     //   313: aload 7
/* 2946:     */     //   315: iload 15
/* 2947:     */     //   317: aload 10
/* 2948:     */     //   319: invokeinterface 446 5 0
/* 2949:     */     //   324: goto +162 -> 486
/* 2950:     */     //   327: aload_0
/* 2951:     */     //   328: invokespecial 584	org/hibernate/persister/entity/AbstractEntityPersister:isAllOrDirtyOptLocking	()Z
/* 2952:     */     //   331: ifeq +155 -> 486
/* 2953:     */     //   334: aload_3
/* 2954:     */     //   335: ifnull +151 -> 486
/* 2955:     */     //   338: aload_0
/* 2956:     */     //   339: invokevirtual 32	org/hibernate/persister/entity/AbstractEntityPersister:getPropertyVersionability	()[Z
/* 2957:     */     //   342: astore 17
/* 2958:     */     //   344: aload_0
/* 2959:     */     //   345: getfield 3	org/hibernate/persister/entity/AbstractEntityPersister:entityMetamodel	Lorg/hibernate/tuple/entity/EntityMetamodel;
/* 2960:     */     //   348: invokevirtual 582	org/hibernate/tuple/entity/EntityMetamodel:getOptimisticLockStyle	()Lorg/hibernate/engine/OptimisticLockStyle;
/* 2961:     */     //   351: getstatic 585	org/hibernate/engine/OptimisticLockStyle:ALL	Lorg/hibernate/engine/OptimisticLockStyle;
/* 2962:     */     //   354: if_acmpne +10 -> 364
/* 2963:     */     //   357: aload_0
/* 2964:     */     //   358: invokevirtual 28	org/hibernate/persister/entity/AbstractEntityPersister:getPropertyUpdateability	()[Z
/* 2965:     */     //   361: goto +5 -> 366
/* 2966:     */     //   364: aload 5
/* 2967:     */     //   366: astore 18
/* 2968:     */     //   368: aload_0
/* 2969:     */     //   369: invokevirtual 379	org/hibernate/persister/entity/AbstractEntityPersister:getPropertyTypes	()[Lorg/hibernate/type/Type;
/* 2970:     */     //   372: astore 19
/* 2971:     */     //   374: iconst_0
/* 2972:     */     //   375: istore 20
/* 2973:     */     //   377: iload 20
/* 2974:     */     //   379: aload_0
/* 2975:     */     //   380: getfield 3	org/hibernate/persister/entity/AbstractEntityPersister:entityMetamodel	Lorg/hibernate/tuple/entity/EntityMetamodel;
/* 2976:     */     //   383: invokevirtual 107	org/hibernate/tuple/entity/EntityMetamodel:getPropertySpan	()I
/* 2977:     */     //   386: if_icmpge +100 -> 486
/* 2978:     */     //   389: aload 18
/* 2979:     */     //   391: iload 20
/* 2980:     */     //   393: baload
/* 2981:     */     //   394: ifeq +26 -> 420
/* 2982:     */     //   397: aload_0
/* 2983:     */     //   398: iload 20
/* 2984:     */     //   400: iload 6
/* 2985:     */     //   402: invokevirtual 580	org/hibernate/persister/entity/AbstractEntityPersister:isPropertyOfTable	(II)Z
/* 2986:     */     //   405: ifeq +15 -> 420
/* 2987:     */     //   408: aload 17
/* 2988:     */     //   410: iload 20
/* 2989:     */     //   412: baload
/* 2990:     */     //   413: ifeq +7 -> 420
/* 2991:     */     //   416: iconst_1
/* 2992:     */     //   417: goto +4 -> 421
/* 2993:     */     //   420: iconst_0
/* 2994:     */     //   421: istore 21
/* 2995:     */     //   423: iload 21
/* 2996:     */     //   425: ifeq +55 -> 480
/* 2997:     */     //   428: aload 19
/* 2998:     */     //   430: iload 20
/* 2999:     */     //   432: aaload
/* 3000:     */     //   433: aload_3
/* 3001:     */     //   434: iload 20
/* 3002:     */     //   436: aaload
/* 3003:     */     //   437: aload_0
/* 3004:     */     //   438: invokevirtual 290	org/hibernate/persister/entity/AbstractEntityPersister:getFactory	()Lorg/hibernate/engine/spi/SessionFactoryImplementor;
/* 3005:     */     //   441: invokeinterface 587 3 0
/* 3006:     */     //   446: astore 22
/* 3007:     */     //   448: aload 19
/* 3008:     */     //   450: iload 20
/* 3009:     */     //   452: aaload
/* 3010:     */     //   453: aload 16
/* 3011:     */     //   455: aload_3
/* 3012:     */     //   456: iload 20
/* 3013:     */     //   458: aaload
/* 3014:     */     //   459: iload 15
/* 3015:     */     //   461: aload 22
/* 3016:     */     //   463: aload 10
/* 3017:     */     //   465: invokeinterface 620 6 0
/* 3018:     */     //   470: iload 15
/* 3019:     */     //   472: aload 22
/* 3020:     */     //   474: invokestatic 621	org/hibernate/internal/util/collections/ArrayHelper:countTrue	([Z)I
/* 3021:     */     //   477: iadd
/* 3022:     */     //   478: istore 15
/* 3023:     */     //   480: iinc 20 1
/* 3024:     */     //   483: goto -106 -> 377
/* 3025:     */     //   486: iload 12
/* 3026:     */     //   488: ifeq +47 -> 535
/* 3027:     */     //   491: aload 10
/* 3028:     */     //   493: invokeinterface 311 1 0
/* 3029:     */     //   498: invokeinterface 312 1 0
/* 3030:     */     //   503: aload_0
/* 3031:     */     //   504: getfield 673	org/hibernate/persister/entity/AbstractEntityPersister:updateBatchKey	Lorg/hibernate/engine/jdbc/batch/internal/BasicBatchKey;
/* 3032:     */     //   507: invokeinterface 658 2 0
/* 3033:     */     //   512: invokeinterface 661 1 0
/* 3034:     */     //   517: iconst_1
/* 3035:     */     //   518: istore 17
/* 3036:     */     //   520: iload 12
/* 3037:     */     //   522: ifne +10 -> 532
/* 3038:     */     //   525: aload 16
/* 3039:     */     //   527: invokeinterface 322 1 0
/* 3040:     */     //   532: iload 17
/* 3041:     */     //   534: ireturn
/* 3042:     */     //   535: aload_0
/* 3043:     */     //   536: aload 16
/* 3044:     */     //   538: invokeinterface 448 1 0
/* 3045:     */     //   543: aload_1
/* 3046:     */     //   544: iload 6
/* 3047:     */     //   546: aload 11
/* 3048:     */     //   548: aload 16
/* 3049:     */     //   550: invokevirtual 678	org/hibernate/persister/entity/AbstractEntityPersister:check	(ILjava/io/Serializable;ILorg/hibernate/jdbc/Expectation;Ljava/sql/PreparedStatement;)Z
/* 3050:     */     //   553: istore 17
/* 3051:     */     //   555: iload 12
/* 3052:     */     //   557: ifne +10 -> 567
/* 3053:     */     //   560: aload 16
/* 3054:     */     //   562: invokeinterface 322 1 0
/* 3055:     */     //   567: iload 17
/* 3056:     */     //   569: ireturn
/* 3057:     */     //   570: astore 17
/* 3058:     */     //   572: iload 12
/* 3059:     */     //   574: ifeq +20 -> 594
/* 3060:     */     //   577: aload 10
/* 3061:     */     //   579: invokeinterface 311 1 0
/* 3062:     */     //   584: invokeinterface 312 1 0
/* 3063:     */     //   589: invokeinterface 662 1 0
/* 3064:     */     //   594: aload 17
/* 3065:     */     //   596: athrow
/* 3066:     */     //   597: astore 23
/* 3067:     */     //   599: iload 12
/* 3068:     */     //   601: ifne +10 -> 611
/* 3069:     */     //   604: aload 16
/* 3070:     */     //   606: invokeinterface 322 1 0
/* 3071:     */     //   611: aload 23
/* 3072:     */     //   613: athrow
/* 3073:     */     //   614: astore 15
/* 3074:     */     //   616: aload_0
/* 3075:     */     //   617: invokevirtual 290	org/hibernate/persister/entity/AbstractEntityPersister:getFactory	()Lorg/hibernate/engine/spi/SessionFactoryImplementor;
/* 3076:     */     //   620: invokeinterface 325 1 0
/* 3077:     */     //   625: aload 15
/* 3078:     */     //   627: new 97	java/lang/StringBuilder
/* 3079:     */     //   630: dup
/* 3080:     */     //   631: invokespecial 98	java/lang/StringBuilder:<init>	()V
/* 3081:     */     //   634: ldc_w 679
/* 3082:     */     //   637: invokevirtual 100	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 3083:     */     //   640: aload_0
/* 3084:     */     //   641: aload_1
/* 3085:     */     //   642: aload_0
/* 3086:     */     //   643: invokevirtual 290	org/hibernate/persister/entity/AbstractEntityPersister:getFactory	()Lorg/hibernate/engine/spi/SessionFactoryImplementor;
/* 3087:     */     //   646: invokestatic 291	org/hibernate/pretty/MessageHelper:infoString	(Lorg/hibernate/persister/entity/EntityPersister;Ljava/lang/Object;Lorg/hibernate/engine/spi/SessionFactoryImplementor;)Ljava/lang/String;
/* 3088:     */     //   649: invokevirtual 100	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 3089:     */     //   652: invokevirtual 102	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 3090:     */     //   655: aload 9
/* 3091:     */     //   657: invokevirtual 327	org/hibernate/engine/jdbc/spi/SqlExceptionHelper:convert	(Ljava/sql/SQLException;Ljava/lang/String;Ljava/lang/String;)Lorg/hibernate/JDBCException;
/* 3092:     */     //   660: athrow
/* 3093:     */     // Line number table:
/* 3094:     */     //   Java source line #2936	-> byte code offset #0
/* 3095:     */     //   Java source line #2937	-> byte code offset #12
/* 3096:     */     //   Java source line #2938	-> byte code offset #41
/* 3097:     */     //   Java source line #2939	-> byte code offset #53
/* 3098:     */     //   Java source line #2944	-> byte code offset #89
/* 3099:     */     //   Java source line #2945	-> byte code offset #97
/* 3100:     */     //   Java source line #2947	-> byte code offset #116
/* 3101:     */     //   Java source line #2948	-> byte code offset #127
/* 3102:     */     //   Java source line #2949	-> byte code offset #147
/* 3103:     */     //   Java source line #2950	-> byte code offset #152
/* 3104:     */     //   Java source line #2954	-> byte code offset #171
/* 3105:     */     //   Java source line #2956	-> byte code offset #174
/* 3106:     */     //   Java source line #2957	-> byte code offset #179
/* 3107:     */     //   Java source line #2963	-> byte code offset #214
/* 3108:     */     //   Java source line #2970	-> byte code offset #242
/* 3109:     */     //   Java source line #2973	-> byte code offset #256
/* 3110:     */     //   Java source line #2976	-> byte code offset #280
/* 3111:     */     //   Java source line #2977	-> byte code offset #298
/* 3112:     */     //   Java source line #2978	-> byte code offset #307
/* 3113:     */     //   Java source line #2981	-> byte code offset #327
/* 3114:     */     //   Java source line #2982	-> byte code offset #338
/* 3115:     */     //   Java source line #2983	-> byte code offset #344
/* 3116:     */     //   Java source line #2986	-> byte code offset #368
/* 3117:     */     //   Java source line #2987	-> byte code offset #374
/* 3118:     */     //   Java source line #2988	-> byte code offset #389
/* 3119:     */     //   Java source line #2991	-> byte code offset #423
/* 3120:     */     //   Java source line #2992	-> byte code offset #428
/* 3121:     */     //   Java source line #2993	-> byte code offset #448
/* 3122:     */     //   Java source line #3000	-> byte code offset #470
/* 3123:     */     //   Java source line #2987	-> byte code offset #480
/* 3124:     */     //   Java source line #3005	-> byte code offset #486
/* 3125:     */     //   Java source line #3006	-> byte code offset #491
/* 3126:     */     //   Java source line #3007	-> byte code offset #517
/* 3127:     */     //   Java source line #3021	-> byte code offset #520
/* 3128:     */     //   Java source line #3022	-> byte code offset #525
/* 3129:     */     //   Java source line #3010	-> byte code offset #535
/* 3130:     */     //   Java source line #3021	-> byte code offset #555
/* 3131:     */     //   Java source line #3022	-> byte code offset #560
/* 3132:     */     //   Java source line #3014	-> byte code offset #570
/* 3133:     */     //   Java source line #3015	-> byte code offset #572
/* 3134:     */     //   Java source line #3016	-> byte code offset #577
/* 3135:     */     //   Java source line #3018	-> byte code offset #594
/* 3136:     */     //   Java source line #3021	-> byte code offset #597
/* 3137:     */     //   Java source line #3022	-> byte code offset #604
/* 3138:     */     //   Java source line #3027	-> byte code offset #614
/* 3139:     */     //   Java source line #3028	-> byte code offset #616
/* 3140:     */     // Local variable table:
/* 3141:     */     //   start	length	slot	name	signature
/* 3142:     */     //   0	661	0	this	AbstractEntityPersister
/* 3143:     */     //   0	661	1	id	Serializable
/* 3144:     */     //   0	661	2	fields	Object[]
/* 3145:     */     //   0	661	3	oldFields	Object[]
/* 3146:     */     //   0	661	4	rowId	Object
/* 3147:     */     //   0	661	5	includeProperty	boolean[]
/* 3148:     */     //   0	661	6	j	int
/* 3149:     */     //   0	661	7	oldVersion	Object
/* 3150:     */     //   0	661	8	object	Object
/* 3151:     */     //   0	661	9	sql	String
/* 3152:     */     //   0	661	10	session	SessionImplementor
/* 3153:     */     //   10	537	11	expectation	Expectation
/* 3154:     */     //   39	561	12	useBatch	boolean
/* 3155:     */     //   95	139	13	callable	boolean
/* 3156:     */     //   114	167	14	useVersion	boolean
/* 3157:     */     //   172	307	15	index	int
/* 3158:     */     //   614	12	15	e	SQLException
/* 3159:     */     //   209	3	16	update	PreparedStatement
/* 3160:     */     //   240	365	16	update	PreparedStatement
/* 3161:     */     //   342	226	17	versionability	boolean[]
/* 3162:     */     //   570	25	17	e	SQLException
/* 3163:     */     //   366	24	18	includeOldField	boolean[]
/* 3164:     */     //   372	77	19	types	Type[]
/* 3165:     */     //   375	106	20	i	int
/* 3166:     */     //   421	3	21	include	boolean
/* 3167:     */     //   446	27	22	settable	boolean[]
/* 3168:     */     //   597	15	23	localObject	Object
/* 3169:     */     // Exception table:
/* 3170:     */     //   from	to	target	type
/* 3171:     */     //   242	520	570	java/sql/SQLException
/* 3172:     */     //   535	555	570	java/sql/SQLException
/* 3173:     */     //   242	520	597	finally
/* 3174:     */     //   535	555	597	finally
/* 3175:     */     //   570	599	597	finally
/* 3176:     */     //   171	532	614	java/sql/SQLException
/* 3177:     */     //   535	567	614	java/sql/SQLException
/* 3178:     */     //   570	614	614	java/sql/SQLException
/* 3179:     */   }
/* 3180:     */   
/* 3181:     */   protected void delete(Serializable id, Object version, int j, Object object, String sql, SessionImplementor session, Object[] loadedState)
/* 3182:     */     throws HibernateException
/* 3183:     */   {
/* 3184:3050 */     if (isInverseTable(j)) {
/* 3185:3051 */       return;
/* 3186:     */     }
/* 3187:3054 */     boolean useVersion = (j == 0) && (isVersioned());
/* 3188:3055 */     boolean callable = isDeleteCallable(j);
/* 3189:3056 */     Expectation expectation = Expectations.appropriateExpectation(this.deleteResultCheckStyles[j]);
/* 3190:3057 */     boolean useBatch = (j == 0) && (isBatchable()) && (expectation.canBeBatched());
/* 3191:3058 */     if ((useBatch) && (this.deleteBatchKey == null)) {
/* 3192:3059 */       this.deleteBatchKey = new BasicBatchKey(getEntityName() + "#DELETE", expectation);
/* 3193:     */     }
/* 3194:3065 */     if (LOG.isTraceEnabled())
/* 3195:     */     {
/* 3196:3066 */       LOG.tracev("Deleting entity: {0}", MessageHelper.infoString(this, id, getFactory()));
/* 3197:3067 */       if (useVersion) {
/* 3198:3068 */         LOG.tracev("Version: {0}", version);
/* 3199:     */       }
/* 3200:     */     }
/* 3201:3071 */     if (isTableCascadeDeleteEnabled(j))
/* 3202:     */     {
/* 3203:3072 */       if (LOG.isTraceEnabled()) {
/* 3204:3073 */         LOG.tracev("Delete handled by foreign key constraint: {0}", getTableName(j));
/* 3205:     */       }
/* 3206:3075 */       return;
/* 3207:     */     }
/* 3208:     */     try
/* 3209:     */     {
/* 3210:3081 */       int index = 1;
/* 3211:     */       PreparedStatement delete;
/* 3212:     */       PreparedStatement delete;
/* 3213:3082 */       if (useBatch) {
/* 3214:3083 */         delete = session.getTransactionCoordinator().getJdbcCoordinator().getBatch(this.deleteBatchKey).getBatchStatement(sql, callable);
/* 3215:     */       } else {
/* 3216:3089 */         delete = session.getTransactionCoordinator().getJdbcCoordinator().getStatementPreparer().prepareStatement(sql, callable);
/* 3217:     */       }
/* 3218:     */       try
/* 3219:     */       {
/* 3220:3097 */         index += expectation.prepare(delete);
/* 3221:     */         
/* 3222:     */ 
/* 3223:     */ 
/* 3224:3101 */         getIdentifierType().nullSafeSet(delete, id, index, session);
/* 3225:3102 */         index += getIdentifierColumnSpan();
/* 3226:3106 */         if (useVersion)
/* 3227:     */         {
/* 3228:3107 */           getVersionType().nullSafeSet(delete, version, index, session);
/* 3229:     */         }
/* 3230:3109 */         else if ((isAllOrDirtyOptLocking()) && (loadedState != null))
/* 3231:     */         {
/* 3232:3110 */           boolean[] versionability = getPropertyVersionability();
/* 3233:3111 */           Type[] types = getPropertyTypes();
/* 3234:3112 */           for (int i = 0; i < this.entityMetamodel.getPropertySpan(); i++) {
/* 3235:3113 */             if ((isPropertyOfTable(i, j)) && (versionability[i] != 0))
/* 3236:     */             {
/* 3237:3116 */               boolean[] settable = types[i].toColumnNullness(loadedState[i], getFactory());
/* 3238:3117 */               types[i].nullSafeSet(delete, loadedState[i], index, settable, session);
/* 3239:3118 */               index += ArrayHelper.countTrue(settable);
/* 3240:     */             }
/* 3241:     */           }
/* 3242:     */         }
/* 3243:3123 */         if (useBatch) {
/* 3244:3124 */           session.getTransactionCoordinator().getJdbcCoordinator().getBatch(this.deleteBatchKey).addToBatch();
/* 3245:     */         } else {
/* 3246:3127 */           check(delete.executeUpdate(), id, j, expectation, delete);
/* 3247:     */         }
/* 3248:     */       }
/* 3249:     */       catch (SQLException sqle)
/* 3250:     */       {
/* 3251:3132 */         if (useBatch) {
/* 3252:3133 */           session.getTransactionCoordinator().getJdbcCoordinator().abortBatch();
/* 3253:     */         }
/* 3254:3135 */         throw sqle;
/* 3255:     */       }
/* 3256:     */       finally
/* 3257:     */       {
/* 3258:3138 */         if (!useBatch) {
/* 3259:3139 */           delete.close();
/* 3260:     */         }
/* 3261:     */       }
/* 3262:     */     }
/* 3263:     */     catch (SQLException sqle)
/* 3264:     */     {
/* 3265:3145 */       throw getFactory().getSQLExceptionHelper().convert(sqle, "could not delete: " + MessageHelper.infoString(this, id, getFactory()), sql);
/* 3266:     */     }
/* 3267:     */   }
/* 3268:     */   
/* 3269:     */   private String[] getUpdateStrings(boolean byRowId, boolean lazy)
/* 3270:     */   {
/* 3271:3157 */     if (byRowId) {
/* 3272:3158 */       return lazy ? getSQLLazyUpdateByRowIdStrings() : getSQLUpdateByRowIdStrings();
/* 3273:     */     }
/* 3274:3161 */     return lazy ? getSQLLazyUpdateStrings() : getSQLUpdateStrings();
/* 3275:     */   }
/* 3276:     */   
/* 3277:     */   public void update(Serializable id, Object[] fields, int[] dirtyFields, boolean hasDirtyCollection, Object[] oldFields, Object oldVersion, Object object, Object rowId, SessionImplementor session)
/* 3278:     */     throws HibernateException
/* 3279:     */   {
/* 3280:3182 */     boolean[] tableUpdateNeeded = getTableUpdateNeeded(dirtyFields, hasDirtyCollection);
/* 3281:3183 */     int span = getTableSpan();
/* 3282:     */     
/* 3283:     */ 
/* 3284:     */ 
/* 3285:3187 */     EntityEntry entry = session.getPersistenceContext().getEntry(object);
/* 3286:3191 */     if ((entry == null) && (!isMutable())) {
/* 3287:3192 */       throw new IllegalStateException("Updating immutable entity that is not in session yet!");
/* 3288:     */     }
/* 3289:     */     String[] updateStrings;
/* 3290:     */     boolean[] propsToUpdate;
/* 3291:3194 */     if ((this.entityMetamodel.isDynamicUpdate()) && (dirtyFields != null))
/* 3292:     */     {
/* 3293:3196 */       boolean[] propsToUpdate = getPropertiesToUpdate(dirtyFields, hasDirtyCollection);
/* 3294:     */       
/* 3295:3198 */       String[] updateStrings = new String[span];
/* 3296:3199 */       for (int j = 0; j < span; j++) {
/* 3297:3200 */         updateStrings[j] = (tableUpdateNeeded[j] != 0 ? generateUpdateString(propsToUpdate, j, oldFields, (j == 0) && (rowId != null)) : null);
/* 3298:     */       }
/* 3299:     */     }
/* 3300:3205 */     else if (!isModifiableEntity(entry))
/* 3301:     */     {
/* 3302:3213 */       boolean[] propsToUpdate = getPropertiesToUpdate(dirtyFields == null ? ArrayHelper.EMPTY_INT_ARRAY : dirtyFields, hasDirtyCollection);
/* 3303:     */       
/* 3304:     */ 
/* 3305:     */ 
/* 3306:     */ 
/* 3307:3218 */       String[] updateStrings = new String[span];
/* 3308:3219 */       for (int j = 0; j < span; j++) {
/* 3309:3220 */         updateStrings[j] = (tableUpdateNeeded[j] != 0 ? generateUpdateString(propsToUpdate, j, oldFields, (j == 0) && (rowId != null)) : null);
/* 3310:     */       }
/* 3311:     */     }
/* 3312:     */     else
/* 3313:     */     {
/* 3314:3227 */       updateStrings = getUpdateStrings(rowId != null, hasUninitializedLazyProperties(object));
/* 3315:     */       
/* 3316:     */ 
/* 3317:     */ 
/* 3318:3231 */       propsToUpdate = getPropertyUpdateability(object);
/* 3319:     */     }
/* 3320:3234 */     for (int j = 0; j < span; j++) {
/* 3321:3236 */       if (tableUpdateNeeded[j] != 0) {
/* 3322:3237 */         updateOrInsert(id, fields, oldFields, j == 0 ? rowId : null, propsToUpdate, j, oldVersion, object, updateStrings[j], session);
/* 3323:     */       }
/* 3324:     */     }
/* 3325:     */   }
/* 3326:     */   
/* 3327:     */   public Serializable insert(Object[] fields, Object object, SessionImplementor session)
/* 3328:     */     throws HibernateException
/* 3329:     */   {
/* 3330:3256 */     int span = getTableSpan();
/* 3331:     */     Serializable id;
/* 3332:3258 */     if (this.entityMetamodel.isDynamicInsert())
/* 3333:     */     {
/* 3334:3260 */       boolean[] notNull = getPropertiesToInsert(fields);
/* 3335:3261 */       Serializable id = insert(fields, notNull, generateInsertString(true, notNull), object, session);
/* 3336:3262 */       for (int j = 1; j < span; j++) {
/* 3337:3263 */         insert(id, fields, notNull, j, generateInsertString(notNull, j), object, session);
/* 3338:     */       }
/* 3339:     */     }
/* 3340:     */     else
/* 3341:     */     {
/* 3342:3268 */       id = insert(fields, getPropertyInsertability(), getSQLIdentityInsertString(), object, session);
/* 3343:3269 */       for (int j = 1; j < span; j++) {
/* 3344:3270 */         insert(id, fields, getPropertyInsertability(), j, getSQLInsertStrings()[j], object, session);
/* 3345:     */       }
/* 3346:     */     }
/* 3347:3273 */     return id;
/* 3348:     */   }
/* 3349:     */   
/* 3350:     */   public void insert(Serializable id, Object[] fields, Object object, SessionImplementor session)
/* 3351:     */     throws HibernateException
/* 3352:     */   {
/* 3353:3279 */     int span = getTableSpan();
/* 3354:3280 */     if (this.entityMetamodel.isDynamicInsert())
/* 3355:     */     {
/* 3356:3282 */       boolean[] notNull = getPropertiesToInsert(fields);
/* 3357:3283 */       for (int j = 0; j < span; j++) {
/* 3358:3284 */         insert(id, fields, notNull, j, generateInsertString(notNull, j), object, session);
/* 3359:     */       }
/* 3360:     */     }
/* 3361:     */     else
/* 3362:     */     {
/* 3363:3289 */       for (int j = 0; j < span; j++) {
/* 3364:3290 */         insert(id, fields, getPropertyInsertability(), j, getSQLInsertStrings()[j], object, session);
/* 3365:     */       }
/* 3366:     */     }
/* 3367:     */   }
/* 3368:     */   
/* 3369:     */   public void delete(Serializable id, Object version, Object object, SessionImplementor session)
/* 3370:     */     throws HibernateException
/* 3371:     */   {
/* 3372:3300 */     int span = getTableSpan();
/* 3373:3301 */     boolean isImpliedOptimisticLocking = (!this.entityMetamodel.isVersioned()) && (isAllOrDirtyOptLocking());
/* 3374:3302 */     Object[] loadedState = null;
/* 3375:3303 */     if (isImpliedOptimisticLocking)
/* 3376:     */     {
/* 3377:3308 */       EntityKey key = session.generateEntityKey(id, this);
/* 3378:3309 */       Object entity = session.getPersistenceContext().getEntity(key);
/* 3379:3310 */       if (entity != null)
/* 3380:     */       {
/* 3381:3311 */         EntityEntry entry = session.getPersistenceContext().getEntry(entity);
/* 3382:3312 */         loadedState = entry.getLoadedState();
/* 3383:     */       }
/* 3384:     */     }
/* 3385:     */     String[] deleteStrings;
/* 3386:     */     String[] deleteStrings;
/* 3387:3317 */     if ((isImpliedOptimisticLocking) && (loadedState != null)) {
/* 3388:3319 */       deleteStrings = generateSQLDeletStrings(loadedState);
/* 3389:     */     } else {
/* 3390:3323 */       deleteStrings = getSQLDeleteStrings();
/* 3391:     */     }
/* 3392:3326 */     for (int j = span - 1; j >= 0; j--) {
/* 3393:3327 */       delete(id, version, j, object, deleteStrings[j], session, loadedState);
/* 3394:     */     }
/* 3395:     */   }
/* 3396:     */   
/* 3397:     */   private boolean isAllOrDirtyOptLocking()
/* 3398:     */   {
/* 3399:3333 */     return (this.entityMetamodel.getOptimisticLockStyle() == OptimisticLockStyle.DIRTY) || (this.entityMetamodel.getOptimisticLockStyle() == OptimisticLockStyle.ALL);
/* 3400:     */   }
/* 3401:     */   
/* 3402:     */   private String[] generateSQLDeletStrings(Object[] loadedState)
/* 3403:     */   {
/* 3404:3338 */     int span = getTableSpan();
/* 3405:3339 */     String[] deleteStrings = new String[span];
/* 3406:3340 */     for (int j = span - 1; j >= 0; j--)
/* 3407:     */     {
/* 3408:3341 */       Delete delete = new Delete().setTableName(getTableName(j)).addPrimaryKeyColumns(getKeyColumns(j));
/* 3409:3344 */       if (getFactory().getSettings().isCommentsEnabled()) {
/* 3410:3345 */         delete.setComment("delete " + getEntityName() + " [" + j + "]");
/* 3411:     */       }
/* 3412:3348 */       boolean[] versionability = getPropertyVersionability();
/* 3413:3349 */       Type[] types = getPropertyTypes();
/* 3414:3350 */       for (int i = 0; i < this.entityMetamodel.getPropertySpan(); i++) {
/* 3415:3351 */         if ((isPropertyOfTable(i, j)) && (versionability[i] != 0))
/* 3416:     */         {
/* 3417:3354 */           String[] propertyColumnNames = getPropertyColumnNames(i);
/* 3418:3355 */           boolean[] propertyNullness = types[i].toColumnNullness(loadedState[i], getFactory());
/* 3419:3356 */           for (int k = 0; k < propertyNullness.length; k++) {
/* 3420:3357 */             if (propertyNullness[k] != 0) {
/* 3421:3358 */               delete.addWhereFragment(propertyColumnNames[k] + " = ?");
/* 3422:     */             } else {
/* 3423:3361 */               delete.addWhereFragment(propertyColumnNames[k] + " is null");
/* 3424:     */             }
/* 3425:     */           }
/* 3426:     */         }
/* 3427:     */       }
/* 3428:3366 */       deleteStrings[j] = delete.toStatementString();
/* 3429:     */     }
/* 3430:3368 */     return deleteStrings;
/* 3431:     */   }
/* 3432:     */   
/* 3433:     */   protected void logStaticSQL()
/* 3434:     */   {
/* 3435:3372 */     if (LOG.isDebugEnabled())
/* 3436:     */     {
/* 3437:3373 */       LOG.debugf("Static SQL for entity: %s", getEntityName());
/* 3438:3374 */       if (this.sqlLazySelectString != null) {
/* 3439:3374 */         LOG.debugf(" Lazy select: %s", this.sqlLazySelectString);
/* 3440:     */       }
/* 3441:3375 */       if (this.sqlVersionSelectString != null) {
/* 3442:3375 */         LOG.debugf(" Version select: %s", this.sqlVersionSelectString);
/* 3443:     */       }
/* 3444:3376 */       if (this.sqlSnapshotSelectString != null) {
/* 3445:3376 */         LOG.debugf(" Snapshot select: %s", this.sqlSnapshotSelectString);
/* 3446:     */       }
/* 3447:3377 */       for (int j = 0; j < getTableSpan(); j++)
/* 3448:     */       {
/* 3449:3378 */         LOG.debugf(" Insert %s: %s", Integer.valueOf(j), getSQLInsertStrings()[j]);
/* 3450:3379 */         LOG.debugf(" Update %s: %s", Integer.valueOf(j), getSQLUpdateStrings()[j]);
/* 3451:3380 */         LOG.debugf(" Delete %s: %s", Integer.valueOf(j), getSQLDeleteStrings()[j]);
/* 3452:     */       }
/* 3453:3382 */       if (this.sqlIdentityInsertString != null) {
/* 3454:3382 */         LOG.debugf(" Identity insert: %s", this.sqlIdentityInsertString);
/* 3455:     */       }
/* 3456:3383 */       if (this.sqlUpdateByRowIdString != null) {
/* 3457:3383 */         LOG.debugf(" Update by row id (all fields): %s", this.sqlUpdateByRowIdString);
/* 3458:     */       }
/* 3459:3384 */       if (this.sqlLazyUpdateByRowIdString != null) {
/* 3460:3384 */         LOG.debugf(" Update by row id (non-lazy fields): %s", this.sqlLazyUpdateByRowIdString);
/* 3461:     */       }
/* 3462:3386 */       if (this.sqlInsertGeneratedValuesSelectString != null) {
/* 3463:3386 */         LOG.debugf("Insert-generated property select: %s", this.sqlInsertGeneratedValuesSelectString);
/* 3464:     */       }
/* 3465:3388 */       if (this.sqlUpdateGeneratedValuesSelectString != null) {
/* 3466:3388 */         LOG.debugf("Update-generated property select: %s", this.sqlUpdateGeneratedValuesSelectString);
/* 3467:     */       }
/* 3468:     */     }
/* 3469:     */   }
/* 3470:     */   
/* 3471:     */   public String filterFragment(String alias, Map enabledFilters)
/* 3472:     */     throws MappingException
/* 3473:     */   {
/* 3474:3394 */     StringBuilder sessionFilterFragment = new StringBuilder();
/* 3475:3395 */     this.filterHelper.render(sessionFilterFragment, generateFilterConditionAlias(alias), enabledFilters);
/* 3476:     */     
/* 3477:3397 */     return filterFragment(alias);
/* 3478:     */   }
/* 3479:     */   
/* 3480:     */   public String generateFilterConditionAlias(String rootAlias)
/* 3481:     */   {
/* 3482:3401 */     return rootAlias;
/* 3483:     */   }
/* 3484:     */   
/* 3485:     */   public String oneToManyFilterFragment(String alias)
/* 3486:     */     throws MappingException
/* 3487:     */   {
/* 3488:3405 */     return "";
/* 3489:     */   }
/* 3490:     */   
/* 3491:     */   public String fromJoinFragment(String alias, boolean innerJoin, boolean includeSubclasses)
/* 3492:     */   {
/* 3493:3409 */     return getSubclassTableSpan() == 1 ? "" : createJoin(alias, innerJoin, includeSubclasses).toFromFragmentString();
/* 3494:     */   }
/* 3495:     */   
/* 3496:     */   public String whereJoinFragment(String alias, boolean innerJoin, boolean includeSubclasses)
/* 3497:     */   {
/* 3498:3415 */     return getSubclassTableSpan() == 1 ? "" : createJoin(alias, innerJoin, includeSubclasses).toWhereFragmentString();
/* 3499:     */   }
/* 3500:     */   
/* 3501:     */   protected boolean isSubclassTableLazy(int j)
/* 3502:     */   {
/* 3503:3421 */     return false;
/* 3504:     */   }
/* 3505:     */   
/* 3506:     */   protected JoinFragment createJoin(String name, boolean innerJoin, boolean includeSubclasses)
/* 3507:     */   {
/* 3508:3425 */     String[] idCols = StringHelper.qualify(name, getIdentifierColumnNames());
/* 3509:3426 */     JoinFragment join = getFactory().getDialect().createOuterJoinFragment();
/* 3510:3427 */     int tableSpan = getSubclassTableSpan();
/* 3511:3428 */     for (int j = 1; j < tableSpan; j++)
/* 3512:     */     {
/* 3513:3429 */       boolean joinIsIncluded = (isClassOrSuperclassTable(j)) || ((includeSubclasses) && (!isSubclassTableSequentialSelect(j)) && (!isSubclassTableLazy(j)));
/* 3514:3431 */       if (joinIsIncluded) {
/* 3515:3432 */         join.addJoin(getSubclassTableName(j), generateTableAlias(name, j), idCols, getSubclassTableKeyColumns(j), (innerJoin) && (isClassOrSuperclassTable(j)) && (!isInverseTable(j)) && (!isNullableTable(j)) ? JoinType.INNER_JOIN : JoinType.LEFT_OUTER_JOIN);
/* 3516:     */       }
/* 3517:     */     }
/* 3518:3442 */     return join;
/* 3519:     */   }
/* 3520:     */   
/* 3521:     */   protected JoinFragment createJoin(int[] tableNumbers, String drivingAlias)
/* 3522:     */   {
/* 3523:3446 */     String[] keyCols = StringHelper.qualify(drivingAlias, getSubclassTableKeyColumns(tableNumbers[0]));
/* 3524:3447 */     JoinFragment jf = getFactory().getDialect().createOuterJoinFragment();
/* 3525:3448 */     for (int i = 1; i < tableNumbers.length; i++)
/* 3526:     */     {
/* 3527:3449 */       int j = tableNumbers[i];
/* 3528:3450 */       jf.addJoin(getSubclassTableName(j), generateTableAlias(getRootAlias(), j), keyCols, getSubclassTableKeyColumns(j), (isInverseSubclassTable(j)) || (isNullableSubclassTable(j)) ? JoinType.LEFT_OUTER_JOIN : JoinType.INNER_JOIN);
/* 3529:     */     }
/* 3530:3458 */     return jf;
/* 3531:     */   }
/* 3532:     */   
/* 3533:     */   protected SelectFragment createSelect(int[] subclassColumnNumbers, int[] subclassFormulaNumbers)
/* 3534:     */   {
/* 3535:3464 */     SelectFragment selectFragment = new SelectFragment();
/* 3536:     */     
/* 3537:3466 */     int[] columnTableNumbers = getSubclassColumnTableNumberClosure();
/* 3538:3467 */     String[] columnAliases = getSubclassColumnAliasClosure();
/* 3539:3468 */     String[] columnReaderTemplates = getSubclassColumnReaderTemplateClosure();
/* 3540:3469 */     for (int i = 0; i < subclassColumnNumbers.length; i++)
/* 3541:     */     {
/* 3542:3470 */       int columnNumber = subclassColumnNumbers[i];
/* 3543:3471 */       if (this.subclassColumnSelectableClosure[columnNumber] != 0)
/* 3544:     */       {
/* 3545:3472 */         String subalias = generateTableAlias(getRootAlias(), columnTableNumbers[columnNumber]);
/* 3546:3473 */         selectFragment.addColumnTemplate(subalias, columnReaderTemplates[columnNumber], columnAliases[columnNumber]);
/* 3547:     */       }
/* 3548:     */     }
/* 3549:3477 */     int[] formulaTableNumbers = getSubclassFormulaTableNumberClosure();
/* 3550:3478 */     String[] formulaTemplates = getSubclassFormulaTemplateClosure();
/* 3551:3479 */     String[] formulaAliases = getSubclassFormulaAliasClosure();
/* 3552:3480 */     for (int i = 0; i < subclassFormulaNumbers.length; i++)
/* 3553:     */     {
/* 3554:3481 */       int formulaNumber = subclassFormulaNumbers[i];
/* 3555:3482 */       String subalias = generateTableAlias(getRootAlias(), formulaTableNumbers[formulaNumber]);
/* 3556:3483 */       selectFragment.addFormula(subalias, formulaTemplates[formulaNumber], formulaAliases[formulaNumber]);
/* 3557:     */     }
/* 3558:3486 */     return selectFragment;
/* 3559:     */   }
/* 3560:     */   
/* 3561:     */   protected String createFrom(int tableNumber, String alias)
/* 3562:     */   {
/* 3563:3490 */     return getSubclassTableName(tableNumber) + ' ' + alias;
/* 3564:     */   }
/* 3565:     */   
/* 3566:     */   protected String createWhereByKey(int tableNumber, String alias)
/* 3567:     */   {
/* 3568:3495 */     return StringHelper.join("=? and ", StringHelper.qualify(alias, getSubclassTableKeyColumns(tableNumber))) + "=?";
/* 3569:     */   }
/* 3570:     */   
/* 3571:     */   protected String renderSelect(int[] tableNumbers, int[] columnNumbers, int[] formulaNumbers)
/* 3572:     */   {
/* 3573:3504 */     Arrays.sort(tableNumbers);
/* 3574:     */     
/* 3575:     */ 
/* 3576:3507 */     int drivingTable = tableNumbers[0];
/* 3577:3508 */     String drivingAlias = generateTableAlias(getRootAlias(), drivingTable);
/* 3578:3509 */     String where = createWhereByKey(drivingTable, drivingAlias);
/* 3579:3510 */     String from = createFrom(drivingTable, drivingAlias);
/* 3580:     */     
/* 3581:     */ 
/* 3582:3513 */     JoinFragment jf = createJoin(tableNumbers, drivingAlias);
/* 3583:     */     
/* 3584:     */ 
/* 3585:3516 */     SelectFragment selectFragment = createSelect(columnNumbers, formulaNumbers);
/* 3586:     */     
/* 3587:     */ 
/* 3588:3519 */     Select select = new Select(getFactory().getDialect());
/* 3589:3520 */     select.setSelectClause(selectFragment.toFragmentString().substring(2));
/* 3590:3521 */     select.setFromClause(from);
/* 3591:3522 */     select.setWhereClause(where);
/* 3592:3523 */     select.setOuterJoins(jf.toFromFragmentString(), jf.toWhereFragmentString());
/* 3593:3524 */     if (getFactory().getSettings().isCommentsEnabled()) {
/* 3594:3525 */       select.setComment("sequential select " + getEntityName());
/* 3595:     */     }
/* 3596:3527 */     return select.toStatementString();
/* 3597:     */   }
/* 3598:     */   
/* 3599:     */   private String getRootAlias()
/* 3600:     */   {
/* 3601:3531 */     return StringHelper.generateAlias(getEntityName());
/* 3602:     */   }
/* 3603:     */   
/* 3604:     */   protected void postConstruct(Mapping mapping)
/* 3605:     */     throws MappingException
/* 3606:     */   {
/* 3607:3535 */     initPropertyPaths(mapping);
/* 3608:     */     
/* 3609:     */ 
/* 3610:3538 */     int joinSpan = getTableSpan();
/* 3611:3539 */     this.sqlDeleteStrings = new String[joinSpan];
/* 3612:3540 */     this.sqlInsertStrings = new String[joinSpan];
/* 3613:3541 */     this.sqlUpdateStrings = new String[joinSpan];
/* 3614:3542 */     this.sqlLazyUpdateStrings = new String[joinSpan];
/* 3615:     */     
/* 3616:3544 */     this.sqlUpdateByRowIdString = (this.rowIdName == null ? null : generateUpdateString(getPropertyUpdateability(), 0, true));
/* 3617:     */     
/* 3618:     */ 
/* 3619:3547 */     this.sqlLazyUpdateByRowIdString = (this.rowIdName == null ? null : generateUpdateString(getNonLazyPropertyUpdateability(), 0, true));
/* 3620:3551 */     for (int j = 0; j < joinSpan; j++)
/* 3621:     */     {
/* 3622:3552 */       this.sqlInsertStrings[j] = (this.customSQLInsert[j] == null ? generateInsertString(getPropertyInsertability(), j) : this.customSQLInsert[j]);
/* 3623:     */       
/* 3624:     */ 
/* 3625:3555 */       this.sqlUpdateStrings[j] = (this.customSQLUpdate[j] == null ? generateUpdateString(getPropertyUpdateability(), j, false) : this.customSQLUpdate[j]);
/* 3626:     */       
/* 3627:     */ 
/* 3628:3558 */       this.sqlLazyUpdateStrings[j] = (this.customSQLUpdate[j] == null ? generateUpdateString(getNonLazyPropertyUpdateability(), j, false) : this.customSQLUpdate[j]);
/* 3629:     */       
/* 3630:     */ 
/* 3631:3561 */       this.sqlDeleteStrings[j] = (this.customSQLDelete[j] == null ? generateDeleteString(j) : this.customSQLDelete[j]);
/* 3632:     */     }
/* 3633:3566 */     this.tableHasColumns = new boolean[joinSpan];
/* 3634:3567 */     for (int j = 0; j < joinSpan; j++) {
/* 3635:3568 */       this.tableHasColumns[j] = (this.sqlUpdateStrings[j] != null ? 1 : false);
/* 3636:     */     }
/* 3637:3572 */     this.sqlSnapshotSelectString = generateSnapshotSelectString();
/* 3638:3573 */     this.sqlLazySelectString = generateLazySelectString();
/* 3639:3574 */     this.sqlVersionSelectString = generateSelectVersionString();
/* 3640:3575 */     if (hasInsertGeneratedProperties()) {
/* 3641:3576 */       this.sqlInsertGeneratedValuesSelectString = generateInsertGeneratedValuesSelectString();
/* 3642:     */     }
/* 3643:3578 */     if (hasUpdateGeneratedProperties()) {
/* 3644:3579 */       this.sqlUpdateGeneratedValuesSelectString = generateUpdateGeneratedValuesSelectString();
/* 3645:     */     }
/* 3646:3581 */     if (isIdentifierAssignedByInsert())
/* 3647:     */     {
/* 3648:3582 */       this.identityDelegate = ((PostInsertIdentifierGenerator)getIdentifierGenerator()).getInsertGeneratedIdentifierDelegate(this, getFactory().getDialect(), useGetGeneratedKeys());
/* 3649:     */       
/* 3650:3584 */       this.sqlIdentityInsertString = (this.customSQLInsert[0] == null ? generateIdentityInsertString(getPropertyInsertability()) : this.customSQLInsert[0]);
/* 3651:     */     }
/* 3652:     */     else
/* 3653:     */     {
/* 3654:3589 */       this.sqlIdentityInsertString = null;
/* 3655:     */     }
/* 3656:3592 */     logStaticSQL();
/* 3657:     */   }
/* 3658:     */   
/* 3659:     */   public void postInstantiate()
/* 3660:     */     throws MappingException
/* 3661:     */   {
/* 3662:3598 */     createLoaders();
/* 3663:3599 */     createUniqueKeyLoaders();
/* 3664:3600 */     createQueryLoader();
/* 3665:     */   }
/* 3666:     */   
/* 3667:     */   protected Map getLoaders()
/* 3668:     */   {
/* 3669:3606 */     return this.loaders;
/* 3670:     */   }
/* 3671:     */   
/* 3672:     */   protected void createLoaders()
/* 3673:     */   {
/* 3674:3611 */     Map loaders = getLoaders();
/* 3675:3612 */     loaders.put(LockMode.NONE, createEntityLoader(LockMode.NONE));
/* 3676:     */     
/* 3677:3614 */     UniqueEntityLoader readLoader = createEntityLoader(LockMode.READ);
/* 3678:3615 */     loaders.put(LockMode.READ, readLoader);
/* 3679:     */     
/* 3680:     */ 
/* 3681:3618 */     boolean disableForUpdate = (getSubclassTableSpan() > 1) && (hasSubclasses()) && (!getFactory().getDialect().supportsOuterJoinForUpdate());
/* 3682:     */     
/* 3683:     */ 
/* 3684:     */ 
/* 3685:3622 */     loaders.put(LockMode.UPGRADE, disableForUpdate ? readLoader : createEntityLoader(LockMode.UPGRADE));
/* 3686:     */     
/* 3687:     */ 
/* 3688:     */ 
/* 3689:     */ 
/* 3690:     */ 
/* 3691:3628 */     loaders.put(LockMode.UPGRADE_NOWAIT, disableForUpdate ? readLoader : createEntityLoader(LockMode.UPGRADE_NOWAIT));
/* 3692:     */     
/* 3693:     */ 
/* 3694:     */ 
/* 3695:     */ 
/* 3696:     */ 
/* 3697:3634 */     loaders.put(LockMode.FORCE, disableForUpdate ? readLoader : createEntityLoader(LockMode.FORCE));
/* 3698:     */     
/* 3699:     */ 
/* 3700:     */ 
/* 3701:     */ 
/* 3702:     */ 
/* 3703:3640 */     loaders.put(LockMode.PESSIMISTIC_READ, disableForUpdate ? readLoader : createEntityLoader(LockMode.PESSIMISTIC_READ));
/* 3704:     */     
/* 3705:     */ 
/* 3706:     */ 
/* 3707:     */ 
/* 3708:     */ 
/* 3709:3646 */     loaders.put(LockMode.PESSIMISTIC_WRITE, disableForUpdate ? readLoader : createEntityLoader(LockMode.PESSIMISTIC_WRITE));
/* 3710:     */     
/* 3711:     */ 
/* 3712:     */ 
/* 3713:     */ 
/* 3714:     */ 
/* 3715:3652 */     loaders.put(LockMode.PESSIMISTIC_FORCE_INCREMENT, disableForUpdate ? readLoader : createEntityLoader(LockMode.PESSIMISTIC_FORCE_INCREMENT));
/* 3716:     */     
/* 3717:     */ 
/* 3718:     */ 
/* 3719:     */ 
/* 3720:     */ 
/* 3721:3658 */     loaders.put(LockMode.OPTIMISTIC, createEntityLoader(LockMode.OPTIMISTIC));
/* 3722:3659 */     loaders.put(LockMode.OPTIMISTIC_FORCE_INCREMENT, createEntityLoader(LockMode.OPTIMISTIC_FORCE_INCREMENT));
/* 3723:     */     
/* 3724:3661 */     loaders.put("merge", new CascadeEntityLoader(this, CascadingAction.MERGE, getFactory()));
/* 3725:     */     
/* 3726:     */ 
/* 3727:     */ 
/* 3728:3665 */     loaders.put("refresh", new CascadeEntityLoader(this, CascadingAction.REFRESH, getFactory()));
/* 3729:     */   }
/* 3730:     */   
/* 3731:     */   protected void createQueryLoader()
/* 3732:     */   {
/* 3733:3672 */     if (this.loaderName != null) {
/* 3734:3673 */       this.queryLoader = new NamedQueryLoader(this.loaderName, this);
/* 3735:     */     }
/* 3736:     */   }
/* 3737:     */   
/* 3738:     */   public Object load(Serializable id, Object optionalObject, LockMode lockMode, SessionImplementor session)
/* 3739:     */   {
/* 3740:3682 */     return load(id, optionalObject, new LockOptions().setLockMode(lockMode), session);
/* 3741:     */   }
/* 3742:     */   
/* 3743:     */   public Object load(Serializable id, Object optionalObject, LockOptions lockOptions, SessionImplementor session)
/* 3744:     */     throws HibernateException
/* 3745:     */   {
/* 3746:3692 */     if (LOG.isTraceEnabled()) {
/* 3747:3693 */       LOG.tracev("Fetching entity: {0}", MessageHelper.infoString(this, id, getFactory()));
/* 3748:     */     }
/* 3749:3696 */     UniqueEntityLoader loader = getAppropriateLoader(lockOptions, session);
/* 3750:3697 */     return loader.load(id, optionalObject, session, lockOptions);
/* 3751:     */   }
/* 3752:     */   
/* 3753:     */   public void registerAffectingFetchProfile(String fetchProfileName)
/* 3754:     */   {
/* 3755:3701 */     this.affectingFetchProfileNames.add(fetchProfileName);
/* 3756:     */   }
/* 3757:     */   
/* 3758:     */   private boolean isAffectedByEnabledFetchProfiles(SessionImplementor session)
/* 3759:     */   {
/* 3760:3705 */     Iterator itr = session.getLoadQueryInfluencers().getEnabledFetchProfileNames().iterator();
/* 3761:3706 */     while (itr.hasNext()) {
/* 3762:3707 */       if (this.affectingFetchProfileNames.contains(itr.next())) {
/* 3763:3708 */         return true;
/* 3764:     */       }
/* 3765:     */     }
/* 3766:3711 */     return false;
/* 3767:     */   }
/* 3768:     */   
/* 3769:     */   private boolean isAffectedByEnabledFilters(SessionImplementor session)
/* 3770:     */   {
/* 3771:3715 */     return (session.getLoadQueryInfluencers().hasEnabledFilters()) && (this.filterHelper.isAffectedBy(session.getLoadQueryInfluencers().getEnabledFilters()));
/* 3772:     */   }
/* 3773:     */   
/* 3774:     */   private UniqueEntityLoader getAppropriateLoader(LockOptions lockOptions, SessionImplementor session)
/* 3775:     */   {
/* 3776:3720 */     if (this.queryLoader != null) {
/* 3777:3723 */       return this.queryLoader;
/* 3778:     */     }
/* 3779:3725 */     if (isAffectedByEnabledFilters(session)) {
/* 3780:3728 */       return createEntityLoader(lockOptions, session.getLoadQueryInfluencers());
/* 3781:     */     }
/* 3782:3730 */     if ((session.getLoadQueryInfluencers().getInternalFetchProfile() != null) && (LockMode.UPGRADE.greaterThan(lockOptions.getLockMode()))) {
/* 3783:3734 */       return (UniqueEntityLoader)getLoaders().get(session.getLoadQueryInfluencers().getInternalFetchProfile());
/* 3784:     */     }
/* 3785:3736 */     if (isAffectedByEnabledFetchProfiles(session)) {
/* 3786:3739 */       return createEntityLoader(lockOptions, session.getLoadQueryInfluencers());
/* 3787:     */     }
/* 3788:3741 */     if (lockOptions.getTimeOut() != -1) {
/* 3789:3742 */       return createEntityLoader(lockOptions, session.getLoadQueryInfluencers());
/* 3790:     */     }
/* 3791:3745 */     return (UniqueEntityLoader)getLoaders().get(lockOptions.getLockMode());
/* 3792:     */   }
/* 3793:     */   
/* 3794:     */   private boolean isAllNull(Object[] array, int tableNumber)
/* 3795:     */   {
/* 3796:3750 */     for (int i = 0; i < array.length; i++) {
/* 3797:3751 */       if ((isPropertyOfTable(i, tableNumber)) && (array[i] != null)) {
/* 3798:3752 */         return false;
/* 3799:     */       }
/* 3800:     */     }
/* 3801:3755 */     return true;
/* 3802:     */   }
/* 3803:     */   
/* 3804:     */   public boolean isSubclassPropertyNullable(int i)
/* 3805:     */   {
/* 3806:3759 */     return this.subclassPropertyNullabilityClosure[i];
/* 3807:     */   }
/* 3808:     */   
/* 3809:     */   protected final boolean[] getPropertiesToUpdate(int[] dirtyProperties, boolean hasDirtyCollection)
/* 3810:     */   {
/* 3811:3767 */     boolean[] propsToUpdate = new boolean[this.entityMetamodel.getPropertySpan()];
/* 3812:3768 */     boolean[] updateability = getPropertyUpdateability();
/* 3813:3769 */     for (int j = 0; j < dirtyProperties.length; j++)
/* 3814:     */     {
/* 3815:3770 */       int property = dirtyProperties[j];
/* 3816:3771 */       if (updateability[property] != 0) {
/* 3817:3772 */         propsToUpdate[property] = true;
/* 3818:     */       }
/* 3819:     */     }
/* 3820:3775 */     if ((isVersioned()) && (updateability[getVersionProperty()] != 0)) {
/* 3821:3776 */       propsToUpdate[getVersionProperty()] = Versioning.isVersionIncrementRequired(dirtyProperties, hasDirtyCollection, getPropertyVersionability());
/* 3822:     */     }
/* 3823:3779 */     return propsToUpdate;
/* 3824:     */   }
/* 3825:     */   
/* 3826:     */   protected boolean[] getPropertiesToInsert(Object[] fields)
/* 3827:     */   {
/* 3828:3787 */     boolean[] notNull = new boolean[fields.length];
/* 3829:3788 */     boolean[] insertable = getPropertyInsertability();
/* 3830:3789 */     for (int i = 0; i < fields.length; i++) {
/* 3831:3790 */       notNull[i] = ((insertable[i] != 0) && (fields[i] != null) ? 1 : false);
/* 3832:     */     }
/* 3833:3792 */     return notNull;
/* 3834:     */   }
/* 3835:     */   
/* 3836:     */   public int[] findDirty(Object[] currentState, Object[] previousState, Object entity, SessionImplementor session)
/* 3837:     */     throws HibernateException
/* 3838:     */   {
/* 3839:3807 */     int[] props = TypeHelper.findDirty(this.entityMetamodel.getProperties(), currentState, previousState, this.propertyColumnUpdateable, hasUninitializedLazyProperties(entity), session);
/* 3840:3815 */     if (props == null) {
/* 3841:3816 */       return null;
/* 3842:     */     }
/* 3843:3819 */     logDirtyProperties(props);
/* 3844:3820 */     return props;
/* 3845:     */   }
/* 3846:     */   
/* 3847:     */   public int[] findModified(Object[] old, Object[] current, Object entity, SessionImplementor session)
/* 3848:     */     throws HibernateException
/* 3849:     */   {
/* 3850:3836 */     int[] props = TypeHelper.findModified(this.entityMetamodel.getProperties(), current, old, this.propertyColumnUpdateable, hasUninitializedLazyProperties(entity), session);
/* 3851:3844 */     if (props == null) {
/* 3852:3845 */       return null;
/* 3853:     */     }
/* 3854:3848 */     logDirtyProperties(props);
/* 3855:3849 */     return props;
/* 3856:     */   }
/* 3857:     */   
/* 3858:     */   protected boolean[] getPropertyUpdateability(Object entity)
/* 3859:     */   {
/* 3860:3858 */     return hasUninitializedLazyProperties(entity) ? getNonLazyPropertyUpdateability() : getPropertyUpdateability();
/* 3861:     */   }
/* 3862:     */   
/* 3863:     */   private void logDirtyProperties(int[] props)
/* 3864:     */   {
/* 3865:3864 */     if (LOG.isTraceEnabled()) {
/* 3866:3865 */       for (int i = 0; i < props.length; i++)
/* 3867:     */       {
/* 3868:3866 */         String propertyName = this.entityMetamodel.getProperties()[props[i]].getName();
/* 3869:3867 */         LOG.trace(StringHelper.qualify(getEntityName(), propertyName) + " is dirty");
/* 3870:     */       }
/* 3871:     */     }
/* 3872:     */   }
/* 3873:     */   
/* 3874:     */   public SessionFactoryImplementor getFactory()
/* 3875:     */   {
/* 3876:3873 */     return this.factory;
/* 3877:     */   }
/* 3878:     */   
/* 3879:     */   public EntityMetamodel getEntityMetamodel()
/* 3880:     */   {
/* 3881:3877 */     return this.entityMetamodel;
/* 3882:     */   }
/* 3883:     */   
/* 3884:     */   public boolean hasCache()
/* 3885:     */   {
/* 3886:3881 */     return this.cacheAccessStrategy != null;
/* 3887:     */   }
/* 3888:     */   
/* 3889:     */   public EntityRegionAccessStrategy getCacheAccessStrategy()
/* 3890:     */   {
/* 3891:3885 */     return this.cacheAccessStrategy;
/* 3892:     */   }
/* 3893:     */   
/* 3894:     */   public CacheEntryStructure getCacheEntryStructure()
/* 3895:     */   {
/* 3896:3889 */     return this.cacheEntryStructure;
/* 3897:     */   }
/* 3898:     */   
/* 3899:     */   public Comparator getVersionComparator()
/* 3900:     */   {
/* 3901:3893 */     return isVersioned() ? getVersionType().getComparator() : null;
/* 3902:     */   }
/* 3903:     */   
/* 3904:     */   public final String getEntityName()
/* 3905:     */   {
/* 3906:3898 */     return this.entityMetamodel.getName();
/* 3907:     */   }
/* 3908:     */   
/* 3909:     */   public EntityType getEntityType()
/* 3910:     */   {
/* 3911:3902 */     return this.entityMetamodel.getEntityType();
/* 3912:     */   }
/* 3913:     */   
/* 3914:     */   public boolean isPolymorphic()
/* 3915:     */   {
/* 3916:3906 */     return this.entityMetamodel.isPolymorphic();
/* 3917:     */   }
/* 3918:     */   
/* 3919:     */   public boolean isInherited()
/* 3920:     */   {
/* 3921:3910 */     return this.entityMetamodel.isInherited();
/* 3922:     */   }
/* 3923:     */   
/* 3924:     */   public boolean hasCascades()
/* 3925:     */   {
/* 3926:3914 */     return this.entityMetamodel.hasCascades();
/* 3927:     */   }
/* 3928:     */   
/* 3929:     */   public boolean hasIdentifierProperty()
/* 3930:     */   {
/* 3931:3918 */     return !this.entityMetamodel.getIdentifierProperty().isVirtual();
/* 3932:     */   }
/* 3933:     */   
/* 3934:     */   public VersionType getVersionType()
/* 3935:     */   {
/* 3936:3922 */     return (VersionType)locateVersionType();
/* 3937:     */   }
/* 3938:     */   
/* 3939:     */   private Type locateVersionType()
/* 3940:     */   {
/* 3941:3926 */     return this.entityMetamodel.getVersionProperty() == null ? null : this.entityMetamodel.getVersionProperty().getType();
/* 3942:     */   }
/* 3943:     */   
/* 3944:     */   public int getVersionProperty()
/* 3945:     */   {
/* 3946:3932 */     return this.entityMetamodel.getVersionPropertyIndex();
/* 3947:     */   }
/* 3948:     */   
/* 3949:     */   public boolean isVersioned()
/* 3950:     */   {
/* 3951:3936 */     return this.entityMetamodel.isVersioned();
/* 3952:     */   }
/* 3953:     */   
/* 3954:     */   public boolean isIdentifierAssignedByInsert()
/* 3955:     */   {
/* 3956:3940 */     return this.entityMetamodel.getIdentifierProperty().isIdentifierAssignedByInsert();
/* 3957:     */   }
/* 3958:     */   
/* 3959:     */   public boolean hasLazyProperties()
/* 3960:     */   {
/* 3961:3944 */     return this.entityMetamodel.hasLazyProperties();
/* 3962:     */   }
/* 3963:     */   
/* 3964:     */   public void afterReassociate(Object entity, SessionImplementor session)
/* 3965:     */   {
/* 3966:3959 */     InstrumentationService instrumentationService = (InstrumentationService)session.getFactory().getServiceRegistry().getService(InstrumentationService.class);
/* 3967:3962 */     if (instrumentationService.isInstrumented(entity))
/* 3968:     */     {
/* 3969:3963 */       FieldInterceptor interceptor = FieldInterceptionHelper.extractFieldInterceptor(entity);
/* 3970:3964 */       if (interceptor != null)
/* 3971:     */       {
/* 3972:3965 */         interceptor.setSession(session);
/* 3973:     */       }
/* 3974:     */       else
/* 3975:     */       {
/* 3976:3968 */         FieldInterceptor fieldInterceptor = FieldInterceptionHelper.injectFieldInterceptor(entity, getEntityName(), null, session);
/* 3977:     */         
/* 3978:     */ 
/* 3979:     */ 
/* 3980:     */ 
/* 3981:     */ 
/* 3982:3974 */         fieldInterceptor.dirty();
/* 3983:     */       }
/* 3984:     */     }
/* 3985:     */   }
/* 3986:     */   
/* 3987:     */   public Boolean isTransient(Object entity, SessionImplementor session)
/* 3988:     */     throws HibernateException
/* 3989:     */   {
/* 3990:     */     Serializable id;
/* 3991:     */     Serializable id;
/* 3992:3981 */     if (canExtractIdOutOfEntity()) {
/* 3993:3982 */       id = getIdentifier(entity, session);
/* 3994:     */     } else {
/* 3995:3985 */       id = null;
/* 3996:     */     }
/* 3997:3989 */     if (id == null) {
/* 3998:3990 */       return Boolean.TRUE;
/* 3999:     */     }
/* 4000:3994 */     Object version = getVersion(entity);
/* 4001:3995 */     if (isVersioned())
/* 4002:     */     {
/* 4003:3998 */       Boolean result = this.entityMetamodel.getVersionProperty().getUnsavedValue().isUnsaved(version);
/* 4004:4000 */       if (result != null) {
/* 4005:4001 */         return result;
/* 4006:     */       }
/* 4007:     */     }
/* 4008:4006 */     Boolean result = this.entityMetamodel.getIdentifierProperty().getUnsavedValue().isUnsaved(id);
/* 4009:4008 */     if (result != null) {
/* 4010:4009 */       return result;
/* 4011:     */     }
/* 4012:4013 */     if (hasCache())
/* 4013:     */     {
/* 4014:4014 */       CacheKey ck = session.generateCacheKey(id, getIdentifierType(), getRootEntityName());
/* 4015:4015 */       if (getCacheAccessStrategy().get(ck, session.getTimestamp()) != null) {
/* 4016:4016 */         return Boolean.FALSE;
/* 4017:     */       }
/* 4018:     */     }
/* 4019:4020 */     return null;
/* 4020:     */   }
/* 4021:     */   
/* 4022:     */   public boolean hasCollections()
/* 4023:     */   {
/* 4024:4024 */     return this.entityMetamodel.hasCollections();
/* 4025:     */   }
/* 4026:     */   
/* 4027:     */   public boolean hasMutableProperties()
/* 4028:     */   {
/* 4029:4028 */     return this.entityMetamodel.hasMutableProperties();
/* 4030:     */   }
/* 4031:     */   
/* 4032:     */   public boolean isMutable()
/* 4033:     */   {
/* 4034:4032 */     return this.entityMetamodel.isMutable();
/* 4035:     */   }
/* 4036:     */   
/* 4037:     */   private boolean isModifiableEntity(EntityEntry entry)
/* 4038:     */   {
/* 4039:4037 */     return entry == null ? isMutable() : entry.isModifiableEntity();
/* 4040:     */   }
/* 4041:     */   
/* 4042:     */   public boolean isAbstract()
/* 4043:     */   {
/* 4044:4041 */     return this.entityMetamodel.isAbstract();
/* 4045:     */   }
/* 4046:     */   
/* 4047:     */   public boolean hasSubclasses()
/* 4048:     */   {
/* 4049:4045 */     return this.entityMetamodel.hasSubclasses();
/* 4050:     */   }
/* 4051:     */   
/* 4052:     */   public boolean hasProxy()
/* 4053:     */   {
/* 4054:4049 */     return this.entityMetamodel.isLazy();
/* 4055:     */   }
/* 4056:     */   
/* 4057:     */   public IdentifierGenerator getIdentifierGenerator()
/* 4058:     */     throws HibernateException
/* 4059:     */   {
/* 4060:4053 */     return this.entityMetamodel.getIdentifierProperty().getIdentifierGenerator();
/* 4061:     */   }
/* 4062:     */   
/* 4063:     */   public String getRootEntityName()
/* 4064:     */   {
/* 4065:4057 */     return this.entityMetamodel.getRootName();
/* 4066:     */   }
/* 4067:     */   
/* 4068:     */   public ClassMetadata getClassMetadata()
/* 4069:     */   {
/* 4070:4061 */     return this;
/* 4071:     */   }
/* 4072:     */   
/* 4073:     */   public String getMappedSuperclass()
/* 4074:     */   {
/* 4075:4065 */     return this.entityMetamodel.getSuperclass();
/* 4076:     */   }
/* 4077:     */   
/* 4078:     */   public boolean isExplicitPolymorphism()
/* 4079:     */   {
/* 4080:4069 */     return this.entityMetamodel.isExplicitPolymorphism();
/* 4081:     */   }
/* 4082:     */   
/* 4083:     */   protected boolean useDynamicUpdate()
/* 4084:     */   {
/* 4085:4073 */     return this.entityMetamodel.isDynamicUpdate();
/* 4086:     */   }
/* 4087:     */   
/* 4088:     */   protected boolean useDynamicInsert()
/* 4089:     */   {
/* 4090:4077 */     return this.entityMetamodel.isDynamicInsert();
/* 4091:     */   }
/* 4092:     */   
/* 4093:     */   protected boolean hasEmbeddedCompositeIdentifier()
/* 4094:     */   {
/* 4095:4081 */     return this.entityMetamodel.getIdentifierProperty().isEmbedded();
/* 4096:     */   }
/* 4097:     */   
/* 4098:     */   public boolean canExtractIdOutOfEntity()
/* 4099:     */   {
/* 4100:4085 */     return (hasIdentifierProperty()) || (hasEmbeddedCompositeIdentifier()) || (hasIdentifierMapper());
/* 4101:     */   }
/* 4102:     */   
/* 4103:     */   private boolean hasIdentifierMapper()
/* 4104:     */   {
/* 4105:4089 */     return this.entityMetamodel.getIdentifierProperty().hasIdentifierMapper();
/* 4106:     */   }
/* 4107:     */   
/* 4108:     */   public String[] getKeyColumnNames()
/* 4109:     */   {
/* 4110:4093 */     return getIdentifierColumnNames();
/* 4111:     */   }
/* 4112:     */   
/* 4113:     */   public String getName()
/* 4114:     */   {
/* 4115:4097 */     return getEntityName();
/* 4116:     */   }
/* 4117:     */   
/* 4118:     */   public boolean isCollection()
/* 4119:     */   {
/* 4120:4101 */     return false;
/* 4121:     */   }
/* 4122:     */   
/* 4123:     */   public boolean consumesEntityAlias()
/* 4124:     */   {
/* 4125:4105 */     return true;
/* 4126:     */   }
/* 4127:     */   
/* 4128:     */   public boolean consumesCollectionAlias()
/* 4129:     */   {
/* 4130:4109 */     return false;
/* 4131:     */   }
/* 4132:     */   
/* 4133:     */   public Type getPropertyType(String propertyName)
/* 4134:     */     throws MappingException
/* 4135:     */   {
/* 4136:4113 */     return this.propertyMapping.toType(propertyName);
/* 4137:     */   }
/* 4138:     */   
/* 4139:     */   public Type getType()
/* 4140:     */   {
/* 4141:4117 */     return this.entityMetamodel.getEntityType();
/* 4142:     */   }
/* 4143:     */   
/* 4144:     */   public boolean isSelectBeforeUpdateRequired()
/* 4145:     */   {
/* 4146:4121 */     return this.entityMetamodel.isSelectBeforeUpdate();
/* 4147:     */   }
/* 4148:     */   
/* 4149:     */   protected final OptimisticLockStyle optimisticLockStyle()
/* 4150:     */   {
/* 4151:4125 */     return this.entityMetamodel.getOptimisticLockStyle();
/* 4152:     */   }
/* 4153:     */   
/* 4154:     */   public Object createProxy(Serializable id, SessionImplementor session)
/* 4155:     */     throws HibernateException
/* 4156:     */   {
/* 4157:4129 */     return this.entityMetamodel.getTuplizer().createProxy(id, session);
/* 4158:     */   }
/* 4159:     */   
/* 4160:     */   public String toString()
/* 4161:     */   {
/* 4162:4133 */     return StringHelper.unqualify(getClass().getName()) + '(' + this.entityMetamodel.getName() + ')';
/* 4163:     */   }
/* 4164:     */   
/* 4165:     */   public final String selectFragment(Joinable rhs, String rhsAlias, String lhsAlias, String entitySuffix, String collectionSuffix, boolean includeCollectionColumns)
/* 4166:     */   {
/* 4167:4144 */     return selectFragment(lhsAlias, entitySuffix);
/* 4168:     */   }
/* 4169:     */   
/* 4170:     */   public boolean isInstrumented()
/* 4171:     */   {
/* 4172:4148 */     return getEntityTuplizer().isInstrumented();
/* 4173:     */   }
/* 4174:     */   
/* 4175:     */   public boolean hasInsertGeneratedProperties()
/* 4176:     */   {
/* 4177:4152 */     return this.entityMetamodel.hasInsertGeneratedValues();
/* 4178:     */   }
/* 4179:     */   
/* 4180:     */   public boolean hasUpdateGeneratedProperties()
/* 4181:     */   {
/* 4182:4156 */     return this.entityMetamodel.hasUpdateGeneratedValues();
/* 4183:     */   }
/* 4184:     */   
/* 4185:     */   public boolean isVersionPropertyGenerated()
/* 4186:     */   {
/* 4187:4160 */     return (isVersioned()) && (getPropertyUpdateGenerationInclusions()[getVersionProperty()] != ValueInclusion.NONE);
/* 4188:     */   }
/* 4189:     */   
/* 4190:     */   public boolean isVersionPropertyInsertable()
/* 4191:     */   {
/* 4192:4164 */     return (isVersioned()) && (getPropertyInsertability()[getVersionProperty()] != 0);
/* 4193:     */   }
/* 4194:     */   
/* 4195:     */   public void afterInitialize(Object entity, boolean lazyPropertiesAreUnfetched, SessionImplementor session)
/* 4196:     */   {
/* 4197:4168 */     getEntityTuplizer().afterInitialize(entity, lazyPropertiesAreUnfetched, session);
/* 4198:     */   }
/* 4199:     */   
/* 4200:     */   public String[] getPropertyNames()
/* 4201:     */   {
/* 4202:4172 */     return this.entityMetamodel.getPropertyNames();
/* 4203:     */   }
/* 4204:     */   
/* 4205:     */   public Type[] getPropertyTypes()
/* 4206:     */   {
/* 4207:4176 */     return this.entityMetamodel.getPropertyTypes();
/* 4208:     */   }
/* 4209:     */   
/* 4210:     */   public boolean[] getPropertyLaziness()
/* 4211:     */   {
/* 4212:4180 */     return this.entityMetamodel.getPropertyLaziness();
/* 4213:     */   }
/* 4214:     */   
/* 4215:     */   public boolean[] getPropertyUpdateability()
/* 4216:     */   {
/* 4217:4184 */     return this.entityMetamodel.getPropertyUpdateability();
/* 4218:     */   }
/* 4219:     */   
/* 4220:     */   public boolean[] getPropertyCheckability()
/* 4221:     */   {
/* 4222:4188 */     return this.entityMetamodel.getPropertyCheckability();
/* 4223:     */   }
/* 4224:     */   
/* 4225:     */   public boolean[] getNonLazyPropertyUpdateability()
/* 4226:     */   {
/* 4227:4192 */     return this.entityMetamodel.getNonlazyPropertyUpdateability();
/* 4228:     */   }
/* 4229:     */   
/* 4230:     */   public boolean[] getPropertyInsertability()
/* 4231:     */   {
/* 4232:4196 */     return this.entityMetamodel.getPropertyInsertability();
/* 4233:     */   }
/* 4234:     */   
/* 4235:     */   public ValueInclusion[] getPropertyInsertGenerationInclusions()
/* 4236:     */   {
/* 4237:4200 */     return this.entityMetamodel.getPropertyInsertGenerationInclusions();
/* 4238:     */   }
/* 4239:     */   
/* 4240:     */   public ValueInclusion[] getPropertyUpdateGenerationInclusions()
/* 4241:     */   {
/* 4242:4204 */     return this.entityMetamodel.getPropertyUpdateGenerationInclusions();
/* 4243:     */   }
/* 4244:     */   
/* 4245:     */   public boolean[] getPropertyNullability()
/* 4246:     */   {
/* 4247:4208 */     return this.entityMetamodel.getPropertyNullability();
/* 4248:     */   }
/* 4249:     */   
/* 4250:     */   public boolean[] getPropertyVersionability()
/* 4251:     */   {
/* 4252:4212 */     return this.entityMetamodel.getPropertyVersionability();
/* 4253:     */   }
/* 4254:     */   
/* 4255:     */   public CascadeStyle[] getPropertyCascadeStyles()
/* 4256:     */   {
/* 4257:4216 */     return this.entityMetamodel.getCascadeStyles();
/* 4258:     */   }
/* 4259:     */   
/* 4260:     */   public final Class getMappedClass()
/* 4261:     */   {
/* 4262:4220 */     return getEntityTuplizer().getMappedClass();
/* 4263:     */   }
/* 4264:     */   
/* 4265:     */   public boolean implementsLifecycle()
/* 4266:     */   {
/* 4267:4224 */     return getEntityTuplizer().isLifecycleImplementor();
/* 4268:     */   }
/* 4269:     */   
/* 4270:     */   public Class getConcreteProxyClass()
/* 4271:     */   {
/* 4272:4228 */     return getEntityTuplizer().getConcreteProxyClass();
/* 4273:     */   }
/* 4274:     */   
/* 4275:     */   public void setPropertyValues(Object object, Object[] values)
/* 4276:     */   {
/* 4277:4232 */     getEntityTuplizer().setPropertyValues(object, values);
/* 4278:     */   }
/* 4279:     */   
/* 4280:     */   public void setPropertyValue(Object object, int i, Object value)
/* 4281:     */   {
/* 4282:4236 */     getEntityTuplizer().setPropertyValue(object, i, value);
/* 4283:     */   }
/* 4284:     */   
/* 4285:     */   public Object[] getPropertyValues(Object object)
/* 4286:     */   {
/* 4287:4240 */     return getEntityTuplizer().getPropertyValues(object);
/* 4288:     */   }
/* 4289:     */   
/* 4290:     */   public Object getPropertyValue(Object object, int i)
/* 4291:     */   {
/* 4292:4245 */     return getEntityTuplizer().getPropertyValue(object, i);
/* 4293:     */   }
/* 4294:     */   
/* 4295:     */   public Object getPropertyValue(Object object, String propertyName)
/* 4296:     */   {
/* 4297:4250 */     return getEntityTuplizer().getPropertyValue(object, propertyName);
/* 4298:     */   }
/* 4299:     */   
/* 4300:     */   public Serializable getIdentifier(Object object)
/* 4301:     */   {
/* 4302:4255 */     return getEntityTuplizer().getIdentifier(object, null);
/* 4303:     */   }
/* 4304:     */   
/* 4305:     */   public Serializable getIdentifier(Object entity, SessionImplementor session)
/* 4306:     */   {
/* 4307:4260 */     return getEntityTuplizer().getIdentifier(entity, session);
/* 4308:     */   }
/* 4309:     */   
/* 4310:     */   public void setIdentifier(Object entity, Serializable id, SessionImplementor session)
/* 4311:     */   {
/* 4312:4265 */     getEntityTuplizer().setIdentifier(entity, id, session);
/* 4313:     */   }
/* 4314:     */   
/* 4315:     */   public Object getVersion(Object object)
/* 4316:     */   {
/* 4317:4270 */     return getEntityTuplizer().getVersion(object);
/* 4318:     */   }
/* 4319:     */   
/* 4320:     */   public Object instantiate(Serializable id, SessionImplementor session)
/* 4321:     */   {
/* 4322:4275 */     return getEntityTuplizer().instantiate(id, session);
/* 4323:     */   }
/* 4324:     */   
/* 4325:     */   public boolean isInstance(Object object)
/* 4326:     */   {
/* 4327:4280 */     return getEntityTuplizer().isInstance(object);
/* 4328:     */   }
/* 4329:     */   
/* 4330:     */   public boolean hasUninitializedLazyProperties(Object object)
/* 4331:     */   {
/* 4332:4285 */     return getEntityTuplizer().hasUninitializedLazyProperties(object);
/* 4333:     */   }
/* 4334:     */   
/* 4335:     */   public void resetIdentifier(Object entity, Serializable currentId, Object currentVersion, SessionImplementor session)
/* 4336:     */   {
/* 4337:4290 */     getEntityTuplizer().resetIdentifier(entity, currentId, currentVersion, session);
/* 4338:     */   }
/* 4339:     */   
/* 4340:     */   public EntityPersister getSubclassEntityPersister(Object instance, SessionFactoryImplementor factory)
/* 4341:     */   {
/* 4342:4295 */     if (!hasSubclasses()) {
/* 4343:4296 */       return this;
/* 4344:     */     }
/* 4345:4299 */     String concreteEntityName = getEntityTuplizer().determineConcreteSubclassEntityName(instance, factory);
/* 4346:4303 */     if ((concreteEntityName == null) || (getEntityName().equals(concreteEntityName))) {
/* 4347:4306 */       return this;
/* 4348:     */     }
/* 4349:4309 */     return factory.getEntityPersister(concreteEntityName);
/* 4350:     */   }
/* 4351:     */   
/* 4352:     */   public boolean isMultiTable()
/* 4353:     */   {
/* 4354:4315 */     return false;
/* 4355:     */   }
/* 4356:     */   
/* 4357:     */   public String getTemporaryIdTableName()
/* 4358:     */   {
/* 4359:4319 */     return this.temporaryIdTableName;
/* 4360:     */   }
/* 4361:     */   
/* 4362:     */   public String getTemporaryIdTableDDL()
/* 4363:     */   {
/* 4364:4323 */     return this.temporaryIdTableDDL;
/* 4365:     */   }
/* 4366:     */   
/* 4367:     */   protected int getPropertySpan()
/* 4368:     */   {
/* 4369:4327 */     return this.entityMetamodel.getPropertySpan();
/* 4370:     */   }
/* 4371:     */   
/* 4372:     */   public Object[] getPropertyValuesToInsert(Object object, Map mergeMap, SessionImplementor session)
/* 4373:     */     throws HibernateException
/* 4374:     */   {
/* 4375:4331 */     return getEntityTuplizer().getPropertyValuesToInsert(object, mergeMap, session);
/* 4376:     */   }
/* 4377:     */   
/* 4378:     */   public void processInsertGeneratedProperties(Serializable id, Object entity, Object[] state, SessionImplementor session)
/* 4379:     */   {
/* 4380:4335 */     if (!hasInsertGeneratedProperties()) {
/* 4381:4336 */       throw new AssertionFailure("no insert-generated properties");
/* 4382:     */     }
/* 4383:4338 */     processGeneratedProperties(id, entity, state, session, this.sqlInsertGeneratedValuesSelectString, getPropertyInsertGenerationInclusions());
/* 4384:     */   }
/* 4385:     */   
/* 4386:     */   public void processUpdateGeneratedProperties(Serializable id, Object entity, Object[] state, SessionImplementor session)
/* 4387:     */   {
/* 4388:4342 */     if (!hasUpdateGeneratedProperties()) {
/* 4389:4343 */       throw new AssertionFailure("no update-generated properties");
/* 4390:     */     }
/* 4391:4345 */     processGeneratedProperties(id, entity, state, session, this.sqlUpdateGeneratedValuesSelectString, getPropertyUpdateGenerationInclusions());
/* 4392:     */   }
/* 4393:     */   
/* 4394:     */   private void processGeneratedProperties(Serializable id, Object entity, Object[] state, SessionImplementor session, String selectionSQL, ValueInclusion[] includeds)
/* 4395:     */   {
/* 4396:4356 */     session.getTransactionCoordinator().getJdbcCoordinator().executeBatch();
/* 4397:     */     try
/* 4398:     */     {
/* 4399:4359 */       PreparedStatement ps = session.getTransactionCoordinator().getJdbcCoordinator().getStatementPreparer().prepareStatement(selectionSQL);
/* 4400:     */       try
/* 4401:     */       {
/* 4402:4364 */         getIdentifierType().nullSafeSet(ps, id, 1, session);
/* 4403:4365 */         ResultSet rs = ps.executeQuery();
/* 4404:     */         try
/* 4405:     */         {
/* 4406:4367 */           if (!rs.next()) {
/* 4407:4368 */             throw new HibernateException("Unable to locate row for retrieval of generated properties: " + MessageHelper.infoString(this, id, getFactory()));
/* 4408:     */           }
/* 4409:4373 */           for (int i = 0; i < getPropertySpan(); i++) {
/* 4410:4374 */             if (includeds[i] != ValueInclusion.NONE)
/* 4411:     */             {
/* 4412:4375 */               Object hydratedState = getPropertyTypes()[i].hydrate(rs, getPropertyAliases("", i), session, entity);
/* 4413:4376 */               state[i] = getPropertyTypes()[i].resolve(hydratedState, session, entity);
/* 4414:4377 */               setPropertyValue(entity, i, state[i]);
/* 4415:     */             }
/* 4416:     */           }
/* 4417:     */         }
/* 4418:     */         finally
/* 4419:     */         {
/* 4420:4382 */           if (rs == null) {}
/* 4421:     */         }
/* 4422:     */       }
/* 4423:     */       finally
/* 4424:     */       {
/* 4425:4388 */         ps.close();
/* 4426:     */       }
/* 4427:     */     }
/* 4428:     */     catch (SQLException e)
/* 4429:     */     {
/* 4430:4392 */       throw getFactory().getSQLExceptionHelper().convert(e, "unable to select generated column values", selectionSQL);
/* 4431:     */     }
/* 4432:     */   }
/* 4433:     */   
/* 4434:     */   public String getIdentifierPropertyName()
/* 4435:     */   {
/* 4436:4402 */     return this.entityMetamodel.getIdentifierProperty().getName();
/* 4437:     */   }
/* 4438:     */   
/* 4439:     */   public Type getIdentifierType()
/* 4440:     */   {
/* 4441:4406 */     return this.entityMetamodel.getIdentifierProperty().getType();
/* 4442:     */   }
/* 4443:     */   
/* 4444:     */   public boolean hasSubselectLoadableCollections()
/* 4445:     */   {
/* 4446:4410 */     return this.hasSubselectLoadableCollections;
/* 4447:     */   }
/* 4448:     */   
/* 4449:     */   public int[] getNaturalIdentifierProperties()
/* 4450:     */   {
/* 4451:4414 */     return this.entityMetamodel.getNaturalIdentifierProperties();
/* 4452:     */   }
/* 4453:     */   
/* 4454:     */   /* Error */
/* 4455:     */   public Object[] getNaturalIdentifierSnapshot(Serializable id, SessionImplementor session)
/* 4456:     */     throws HibernateException
/* 4457:     */   {
/* 4458:     */     // Byte code:
/* 4459:     */     //   0: aload_0
/* 4460:     */     //   1: invokevirtual 912	org/hibernate/persister/entity/AbstractEntityPersister:hasNaturalIdentifier	()Z
/* 4461:     */     //   4: ifne +34 -> 38
/* 4462:     */     //   7: new 913	org/hibernate/MappingException
/* 4463:     */     //   10: dup
/* 4464:     */     //   11: new 97	java/lang/StringBuilder
/* 4465:     */     //   14: dup
/* 4466:     */     //   15: invokespecial 98	java/lang/StringBuilder:<init>	()V
/* 4467:     */     //   18: ldc_w 914
/* 4468:     */     //   21: invokevirtual 100	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 4469:     */     //   24: aload_0
/* 4470:     */     //   25: invokestatic 664	org/hibernate/pretty/MessageHelper:infoString	(Lorg/hibernate/persister/entity/EntityPersister;)Ljava/lang/String;
/* 4471:     */     //   28: invokevirtual 100	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 4472:     */     //   31: invokevirtual 102	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 4473:     */     //   34: invokespecial 915	org/hibernate/MappingException:<init>	(Ljava/lang/String;)V
/* 4474:     */     //   37: athrow
/* 4475:     */     //   38: getstatic 287	org/hibernate/persister/entity/AbstractEntityPersister:LOG	Lorg/hibernate/internal/CoreMessageLogger;
/* 4476:     */     //   41: invokeinterface 288 1 0
/* 4477:     */     //   46: ifeq +23 -> 69
/* 4478:     */     //   49: getstatic 287	org/hibernate/persister/entity/AbstractEntityPersister:LOG	Lorg/hibernate/internal/CoreMessageLogger;
/* 4479:     */     //   52: ldc_w 916
/* 4480:     */     //   55: aload_0
/* 4481:     */     //   56: aload_1
/* 4482:     */     //   57: aload_0
/* 4483:     */     //   58: invokevirtual 290	org/hibernate/persister/entity/AbstractEntityPersister:getFactory	()Lorg/hibernate/engine/spi/SessionFactoryImplementor;
/* 4484:     */     //   61: invokestatic 291	org/hibernate/pretty/MessageHelper:infoString	(Lorg/hibernate/persister/entity/EntityPersister;Ljava/lang/Object;Lorg/hibernate/engine/spi/SessionFactoryImplementor;)Ljava/lang/String;
/* 4485:     */     //   64: invokeinterface 377 3 0
/* 4486:     */     //   69: aload_0
/* 4487:     */     //   70: invokevirtual 917	org/hibernate/persister/entity/AbstractEntityPersister:getNaturalIdentifierProperties	()[I
/* 4488:     */     //   73: astore_3
/* 4489:     */     //   74: aload_3
/* 4490:     */     //   75: arraylength
/* 4491:     */     //   76: istore 4
/* 4492:     */     //   78: aload_0
/* 4493:     */     //   79: invokevirtual 906	org/hibernate/persister/entity/AbstractEntityPersister:getPropertySpan	()I
/* 4494:     */     //   82: newarray boolean
/* 4495:     */     //   84: astore 5
/* 4496:     */     //   86: iload 4
/* 4497:     */     //   88: anewarray 918	org/hibernate/type/Type
/* 4498:     */     //   91: astore 6
/* 4499:     */     //   93: iconst_0
/* 4500:     */     //   94: istore 7
/* 4501:     */     //   96: iload 7
/* 4502:     */     //   98: iload 4
/* 4503:     */     //   100: if_icmpge +31 -> 131
/* 4504:     */     //   103: aload 6
/* 4505:     */     //   105: iload 7
/* 4506:     */     //   107: aload_0
/* 4507:     */     //   108: invokevirtual 379	org/hibernate/persister/entity/AbstractEntityPersister:getPropertyTypes	()[Lorg/hibernate/type/Type;
/* 4508:     */     //   111: aload_3
/* 4509:     */     //   112: iload 7
/* 4510:     */     //   114: iaload
/* 4511:     */     //   115: aaload
/* 4512:     */     //   116: aastore
/* 4513:     */     //   117: aload 5
/* 4514:     */     //   119: aload_3
/* 4515:     */     //   120: iload 7
/* 4516:     */     //   122: iaload
/* 4517:     */     //   123: iconst_1
/* 4518:     */     //   124: bastore
/* 4519:     */     //   125: iinc 7 1
/* 4520:     */     //   128: goto -32 -> 96
/* 4521:     */     //   131: new 400	org/hibernate/sql/Select
/* 4522:     */     //   134: dup
/* 4523:     */     //   135: aload_0
/* 4524:     */     //   136: invokevirtual 290	org/hibernate/persister/entity/AbstractEntityPersister:getFactory	()Lorg/hibernate/engine/spi/SessionFactoryImplementor;
/* 4525:     */     //   139: invokeinterface 85 1 0
/* 4526:     */     //   144: invokespecial 401	org/hibernate/sql/Select:<init>	(Lorg/hibernate/dialect/Dialect;)V
/* 4527:     */     //   147: astore 7
/* 4528:     */     //   149: aload_0
/* 4529:     */     //   150: invokevirtual 290	org/hibernate/persister/entity/AbstractEntityPersister:getFactory	()Lorg/hibernate/engine/spi/SessionFactoryImplementor;
/* 4530:     */     //   153: invokeinterface 52 1 0
/* 4531:     */     //   158: invokevirtual 391	org/hibernate/cfg/Settings:isCommentsEnabled	()Z
/* 4532:     */     //   161: ifeq +32 -> 193
/* 4533:     */     //   164: aload 7
/* 4534:     */     //   166: new 97	java/lang/StringBuilder
/* 4535:     */     //   169: dup
/* 4536:     */     //   170: invokespecial 98	java/lang/StringBuilder:<init>	()V
/* 4537:     */     //   173: ldc_w 919
/* 4538:     */     //   176: invokevirtual 100	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 4539:     */     //   179: aload_0
/* 4540:     */     //   180: invokevirtual 295	org/hibernate/persister/entity/AbstractEntityPersister:getEntityName	()Ljava/lang/String;
/* 4541:     */     //   183: invokevirtual 100	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 4542:     */     //   186: invokevirtual 102	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 4543:     */     //   189: invokevirtual 403	org/hibernate/sql/Select:setComment	(Ljava/lang/String;)Lorg/hibernate/sql/Select;
/* 4544:     */     //   192: pop
/* 4545:     */     //   193: aload 7
/* 4546:     */     //   195: aload_0
/* 4547:     */     //   196: aload_0
/* 4548:     */     //   197: invokespecial 404	org/hibernate/persister/entity/AbstractEntityPersister:getRootAlias	()Ljava/lang/String;
/* 4549:     */     //   200: aload 5
/* 4550:     */     //   202: invokevirtual 920	org/hibernate/persister/entity/AbstractEntityPersister:concretePropertySelectFragmentSansLeadingComma	(Ljava/lang/String;[Z)Ljava/lang/String;
/* 4551:     */     //   205: invokevirtual 416	org/hibernate/sql/Select:setSelectClause	(Ljava/lang/String;)Lorg/hibernate/sql/Select;
/* 4552:     */     //   208: pop
/* 4553:     */     //   209: aload 7
/* 4554:     */     //   211: new 97	java/lang/StringBuilder
/* 4555:     */     //   214: dup
/* 4556:     */     //   215: invokespecial 98	java/lang/StringBuilder:<init>	()V
/* 4557:     */     //   218: aload_0
/* 4558:     */     //   219: aload_0
/* 4559:     */     //   220: invokespecial 404	org/hibernate/persister/entity/AbstractEntityPersister:getRootAlias	()Ljava/lang/String;
/* 4560:     */     //   223: invokevirtual 407	org/hibernate/persister/entity/AbstractEntityPersister:fromTableFragment	(Ljava/lang/String;)Ljava/lang/String;
/* 4561:     */     //   226: invokevirtual 100	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 4562:     */     //   229: aload_0
/* 4563:     */     //   230: aload_0
/* 4564:     */     //   231: invokespecial 404	org/hibernate/persister/entity/AbstractEntityPersister:getRootAlias	()Ljava/lang/String;
/* 4565:     */     //   234: iconst_1
/* 4566:     */     //   235: iconst_0
/* 4567:     */     //   236: invokevirtual 408	org/hibernate/persister/entity/AbstractEntityPersister:fromJoinFragment	(Ljava/lang/String;ZZ)Ljava/lang/String;
/* 4568:     */     //   239: invokevirtual 100	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 4569:     */     //   242: invokevirtual 102	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 4570:     */     //   245: invokevirtual 417	org/hibernate/sql/Select:setFromClause	(Ljava/lang/String;)Lorg/hibernate/sql/Select;
/* 4571:     */     //   248: pop
/* 4572:     */     //   249: aload_0
/* 4573:     */     //   250: invokespecial 404	org/hibernate/persister/entity/AbstractEntityPersister:getRootAlias	()Ljava/lang/String;
/* 4574:     */     //   253: aload_0
/* 4575:     */     //   254: invokevirtual 355	org/hibernate/persister/entity/AbstractEntityPersister:getIdentifierColumnNames	()[Ljava/lang/String;
/* 4576:     */     //   257: invokestatic 405	org/hibernate/internal/util/StringHelper:qualify	(Ljava/lang/String;[Ljava/lang/String;)[Ljava/lang/String;
/* 4577:     */     //   260: astore 8
/* 4578:     */     //   262: new 409	java/lang/StringBuffer
/* 4579:     */     //   265: dup
/* 4580:     */     //   266: invokespecial 410	java/lang/StringBuffer:<init>	()V
/* 4581:     */     //   269: ldc_w 411
/* 4582:     */     //   272: aload 8
/* 4583:     */     //   274: invokestatic 412	org/hibernate/internal/util/StringHelper:join	(Ljava/lang/String;[Ljava/lang/String;)Ljava/lang/String;
/* 4584:     */     //   277: invokevirtual 413	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
/* 4585:     */     //   280: ldc_w 394
/* 4586:     */     //   283: invokevirtual 413	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
/* 4587:     */     //   286: aload_0
/* 4588:     */     //   287: aload_0
/* 4589:     */     //   288: invokespecial 404	org/hibernate/persister/entity/AbstractEntityPersister:getRootAlias	()Ljava/lang/String;
/* 4590:     */     //   291: iconst_1
/* 4591:     */     //   292: iconst_0
/* 4592:     */     //   293: invokevirtual 414	org/hibernate/persister/entity/AbstractEntityPersister:whereJoinFragment	(Ljava/lang/String;ZZ)Ljava/lang/String;
/* 4593:     */     //   296: invokevirtual 413	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
/* 4594:     */     //   299: invokevirtual 415	java/lang/StringBuffer:toString	()Ljava/lang/String;
/* 4595:     */     //   302: astore 9
/* 4596:     */     //   304: aload 7
/* 4597:     */     //   306: ldc_w 381
/* 4598:     */     //   309: ldc_w 381
/* 4599:     */     //   312: invokevirtual 418	org/hibernate/sql/Select:setOuterJoins	(Ljava/lang/String;Ljava/lang/String;)Lorg/hibernate/sql/Select;
/* 4600:     */     //   315: aload 9
/* 4601:     */     //   317: invokevirtual 419	org/hibernate/sql/Select:setWhereClause	(Ljava/lang/String;)Lorg/hibernate/sql/Select;
/* 4602:     */     //   320: invokevirtual 420	org/hibernate/sql/Select:toStatementString	()Ljava/lang/String;
/* 4603:     */     //   323: astore 10
/* 4604:     */     //   325: iload 4
/* 4605:     */     //   327: anewarray 380	java/lang/Object
/* 4606:     */     //   330: astore 11
/* 4607:     */     //   332: aload_2
/* 4608:     */     //   333: invokeinterface 311 1 0
/* 4609:     */     //   338: invokeinterface 312 1 0
/* 4610:     */     //   343: invokeinterface 313 1 0
/* 4611:     */     //   348: aload 10
/* 4612:     */     //   350: invokeinterface 314 2 0
/* 4613:     */     //   355: astore 12
/* 4614:     */     //   357: aload_0
/* 4615:     */     //   358: invokevirtual 294	org/hibernate/persister/entity/AbstractEntityPersister:getIdentifierType	()Lorg/hibernate/type/Type;
/* 4616:     */     //   361: aload 12
/* 4617:     */     //   363: aload_1
/* 4618:     */     //   364: iconst_1
/* 4619:     */     //   365: aload_2
/* 4620:     */     //   366: invokeinterface 315 5 0
/* 4621:     */     //   371: aload 12
/* 4622:     */     //   373: invokeinterface 316 1 0
/* 4623:     */     //   378: astore 13
/* 4624:     */     //   380: aload 13
/* 4625:     */     //   382: invokeinterface 317 1 0
/* 4626:     */     //   387: ifne +23 -> 410
/* 4627:     */     //   390: aconst_null
/* 4628:     */     //   391: astore 14
/* 4629:     */     //   393: aload 13
/* 4630:     */     //   395: invokeinterface 321 1 0
/* 4631:     */     //   400: aload 12
/* 4632:     */     //   402: invokeinterface 322 1 0
/* 4633:     */     //   407: aload 14
/* 4634:     */     //   409: areturn
/* 4635:     */     //   410: aload_2
/* 4636:     */     //   411: aload_1
/* 4637:     */     //   412: aload_0
/* 4638:     */     //   413: invokeinterface 711 3 0
/* 4639:     */     //   418: astore 14
/* 4640:     */     //   420: aload_2
/* 4641:     */     //   421: invokeinterface 282 1 0
/* 4642:     */     //   426: aload 14
/* 4643:     */     //   428: invokeinterface 712 2 0
/* 4644:     */     //   433: astore 15
/* 4645:     */     //   435: iconst_0
/* 4646:     */     //   436: istore 16
/* 4647:     */     //   438: iload 16
/* 4648:     */     //   440: iload 4
/* 4649:     */     //   442: if_icmpge +75 -> 517
/* 4650:     */     //   445: aload 11
/* 4651:     */     //   447: iload 16
/* 4652:     */     //   449: aload 6
/* 4653:     */     //   451: iload 16
/* 4654:     */     //   453: aaload
/* 4655:     */     //   454: aload 13
/* 4656:     */     //   456: aload_0
/* 4657:     */     //   457: ldc_w 381
/* 4658:     */     //   460: aload_3
/* 4659:     */     //   461: iload 16
/* 4660:     */     //   463: iaload
/* 4661:     */     //   464: invokevirtual 382	org/hibernate/persister/entity/AbstractEntityPersister:getPropertyAliases	(Ljava/lang/String;I)[Ljava/lang/String;
/* 4662:     */     //   467: aload_2
/* 4663:     */     //   468: aconst_null
/* 4664:     */     //   469: invokeinterface 383 5 0
/* 4665:     */     //   474: aastore
/* 4666:     */     //   475: aload 6
/* 4667:     */     //   477: iload 16
/* 4668:     */     //   479: aaload
/* 4669:     */     //   480: invokeinterface 542 1 0
/* 4670:     */     //   485: ifeq +26 -> 511
/* 4671:     */     //   488: aload 11
/* 4672:     */     //   490: iload 16
/* 4673:     */     //   492: aload 6
/* 4674:     */     //   494: iload 16
/* 4675:     */     //   496: aaload
/* 4676:     */     //   497: aload 11
/* 4677:     */     //   499: iload 16
/* 4678:     */     //   501: aaload
/* 4679:     */     //   502: aload_2
/* 4680:     */     //   503: aload 15
/* 4681:     */     //   505: invokeinterface 907 4 0
/* 4682:     */     //   510: aastore
/* 4683:     */     //   511: iinc 16 1
/* 4684:     */     //   514: goto -76 -> 438
/* 4685:     */     //   517: aload 11
/* 4686:     */     //   519: astore 16
/* 4687:     */     //   521: aload 13
/* 4688:     */     //   523: invokeinterface 321 1 0
/* 4689:     */     //   528: aload 12
/* 4690:     */     //   530: invokeinterface 322 1 0
/* 4691:     */     //   535: aload 16
/* 4692:     */     //   537: areturn
/* 4693:     */     //   538: astore 17
/* 4694:     */     //   540: aload 13
/* 4695:     */     //   542: invokeinterface 321 1 0
/* 4696:     */     //   547: aload 17
/* 4697:     */     //   549: athrow
/* 4698:     */     //   550: astore 18
/* 4699:     */     //   552: aload 12
/* 4700:     */     //   554: invokeinterface 322 1 0
/* 4701:     */     //   559: aload 18
/* 4702:     */     //   561: athrow
/* 4703:     */     //   562: astore 12
/* 4704:     */     //   564: aload_0
/* 4705:     */     //   565: invokevirtual 290	org/hibernate/persister/entity/AbstractEntityPersister:getFactory	()Lorg/hibernate/engine/spi/SessionFactoryImplementor;
/* 4706:     */     //   568: invokeinterface 325 1 0
/* 4707:     */     //   573: aload 12
/* 4708:     */     //   575: new 97	java/lang/StringBuilder
/* 4709:     */     //   578: dup
/* 4710:     */     //   579: invokespecial 98	java/lang/StringBuilder:<init>	()V
/* 4711:     */     //   582: ldc_w 384
/* 4712:     */     //   585: invokevirtual 100	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 4713:     */     //   588: aload_0
/* 4714:     */     //   589: aload_1
/* 4715:     */     //   590: aload_0
/* 4716:     */     //   591: invokevirtual 290	org/hibernate/persister/entity/AbstractEntityPersister:getFactory	()Lorg/hibernate/engine/spi/SessionFactoryImplementor;
/* 4717:     */     //   594: invokestatic 291	org/hibernate/pretty/MessageHelper:infoString	(Lorg/hibernate/persister/entity/EntityPersister;Ljava/lang/Object;Lorg/hibernate/engine/spi/SessionFactoryImplementor;)Ljava/lang/String;
/* 4718:     */     //   597: invokevirtual 100	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 4719:     */     //   600: invokevirtual 102	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 4720:     */     //   603: aload 10
/* 4721:     */     //   605: invokevirtual 327	org/hibernate/engine/jdbc/spi/SqlExceptionHelper:convert	(Ljava/sql/SQLException;Ljava/lang/String;Ljava/lang/String;)Lorg/hibernate/JDBCException;
/* 4722:     */     //   608: athrow
/* 4723:     */     // Line number table:
/* 4724:     */     //   Java source line #4418	-> byte code offset #0
/* 4725:     */     //   Java source line #4419	-> byte code offset #7
/* 4726:     */     //   Java source line #4421	-> byte code offset #38
/* 4727:     */     //   Java source line #4422	-> byte code offset #49
/* 4728:     */     //   Java source line #4426	-> byte code offset #69
/* 4729:     */     //   Java source line #4427	-> byte code offset #74
/* 4730:     */     //   Java source line #4428	-> byte code offset #78
/* 4731:     */     //   Java source line #4429	-> byte code offset #86
/* 4732:     */     //   Java source line #4430	-> byte code offset #93
/* 4733:     */     //   Java source line #4431	-> byte code offset #103
/* 4734:     */     //   Java source line #4432	-> byte code offset #117
/* 4735:     */     //   Java source line #4430	-> byte code offset #125
/* 4736:     */     //   Java source line #4437	-> byte code offset #131
/* 4737:     */     //   Java source line #4438	-> byte code offset #149
/* 4738:     */     //   Java source line #4439	-> byte code offset #164
/* 4739:     */     //   Java source line #4441	-> byte code offset #193
/* 4740:     */     //   Java source line #4442	-> byte code offset #209
/* 4741:     */     //   Java source line #4444	-> byte code offset #249
/* 4742:     */     //   Java source line #4445	-> byte code offset #262
/* 4743:     */     //   Java source line #4452	-> byte code offset #304
/* 4744:     */     //   Java source line #4457	-> byte code offset #325
/* 4745:     */     //   Java source line #4459	-> byte code offset #332
/* 4746:     */     //   Java source line #4464	-> byte code offset #357
/* 4747:     */     //   Java source line #4465	-> byte code offset #371
/* 4748:     */     //   Java source line #4468	-> byte code offset #380
/* 4749:     */     //   Java source line #4469	-> byte code offset #390
/* 4750:     */     //   Java source line #4482	-> byte code offset #393
/* 4751:     */     //   Java source line #4486	-> byte code offset #400
/* 4752:     */     //   Java source line #4471	-> byte code offset #410
/* 4753:     */     //   Java source line #4472	-> byte code offset #420
/* 4754:     */     //   Java source line #4473	-> byte code offset #435
/* 4755:     */     //   Java source line #4474	-> byte code offset #445
/* 4756:     */     //   Java source line #4475	-> byte code offset #475
/* 4757:     */     //   Java source line #4476	-> byte code offset #488
/* 4758:     */     //   Java source line #4473	-> byte code offset #511
/* 4759:     */     //   Java source line #4479	-> byte code offset #517
/* 4760:     */     //   Java source line #4482	-> byte code offset #521
/* 4761:     */     //   Java source line #4486	-> byte code offset #528
/* 4762:     */     //   Java source line #4482	-> byte code offset #538
/* 4763:     */     //   Java source line #4486	-> byte code offset #550
/* 4764:     */     //   Java source line #4489	-> byte code offset #562
/* 4765:     */     //   Java source line #4490	-> byte code offset #564
/* 4766:     */     // Local variable table:
/* 4767:     */     //   start	length	slot	name	signature
/* 4768:     */     //   0	609	0	this	AbstractEntityPersister
/* 4769:     */     //   0	609	1	id	Serializable
/* 4770:     */     //   0	609	2	session	SessionImplementor
/* 4771:     */     //   73	388	3	naturalIdPropertyIndexes	int[]
/* 4772:     */     //   76	365	4	naturalIdPropertyCount	int
/* 4773:     */     //   84	117	5	naturalIdMarkers	boolean[]
/* 4774:     */     //   91	402	6	extractionTypes	Type[]
/* 4775:     */     //   94	32	7	i	int
/* 4776:     */     //   147	158	7	select	Select
/* 4777:     */     //   260	13	8	aliasedIdColumns	String[]
/* 4778:     */     //   302	14	9	whereClause	String
/* 4779:     */     //   323	281	10	sql	String
/* 4780:     */     //   330	188	11	snapshot	Object[]
/* 4781:     */     //   355	198	12	ps	PreparedStatement
/* 4782:     */     //   562	12	12	e	SQLException
/* 4783:     */     //   378	163	13	rs	ResultSet
/* 4784:     */     //   418	9	14	key	EntityKey
/* 4785:     */     //   433	71	15	owner	Object
/* 4786:     */     //   436	100	16	i	int
/* 4787:     */     //   538	10	17	localObject1	Object
/* 4788:     */     //   550	10	18	localObject2	Object
/* 4789:     */     // Exception table:
/* 4790:     */     //   from	to	target	type
/* 4791:     */     //   380	393	538	finally
/* 4792:     */     //   410	521	538	finally
/* 4793:     */     //   538	540	538	finally
/* 4794:     */     //   357	400	550	finally
/* 4795:     */     //   410	528	550	finally
/* 4796:     */     //   538	552	550	finally
/* 4797:     */     //   332	407	562	java/sql/SQLException
/* 4798:     */     //   410	535	562	java/sql/SQLException
/* 4799:     */     //   538	562	562	java/sql/SQLException
/* 4800:     */   }
/* 4801:     */   
/* 4802:     */   protected String concretePropertySelectFragmentSansLeadingComma(String alias, boolean[] include)
/* 4803:     */   {
/* 4804:4499 */     String concretePropertySelectFragment = concretePropertySelectFragment(alias, include);
/* 4805:4500 */     int firstComma = concretePropertySelectFragment.indexOf(", ");
/* 4806:4501 */     if (firstComma == 0) {
/* 4807:4502 */       concretePropertySelectFragment = concretePropertySelectFragment.substring(2);
/* 4808:     */     }
/* 4809:4504 */     return concretePropertySelectFragment;
/* 4810:     */   }
/* 4811:     */   
/* 4812:     */   public boolean hasNaturalIdentifier()
/* 4813:     */   {
/* 4814:4508 */     return this.entityMetamodel.hasNaturalIdentifier();
/* 4815:     */   }
/* 4816:     */   
/* 4817:     */   public void setPropertyValue(Object object, String propertyName, Object value)
/* 4818:     */   {
/* 4819:4512 */     getEntityTuplizer().setPropertyValue(object, propertyName, value);
/* 4820:     */   }
/* 4821:     */   
/* 4822:     */   public EntityMode getEntityMode()
/* 4823:     */   {
/* 4824:4517 */     return this.entityMetamodel.getEntityMode();
/* 4825:     */   }
/* 4826:     */   
/* 4827:     */   public EntityTuplizer getEntityTuplizer()
/* 4828:     */   {
/* 4829:4522 */     return this.entityTuplizer;
/* 4830:     */   }
/* 4831:     */   
/* 4832:     */   protected static abstract interface InclusionChecker
/* 4833:     */   {
/* 4834:     */     public abstract boolean includeProperty(int paramInt);
/* 4835:     */   }
/* 4836:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.persister.entity.AbstractEntityPersister
 * JD-Core Version:    0.7.0.1
 */