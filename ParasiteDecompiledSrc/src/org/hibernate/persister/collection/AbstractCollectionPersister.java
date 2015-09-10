/*    1:     */ package org.hibernate.persister.collection;
/*    2:     */ 
/*    3:     */ import java.io.Serializable;
/*    4:     */ import java.sql.PreparedStatement;
/*    5:     */ import java.sql.ResultSet;
/*    6:     */ import java.sql.SQLException;
/*    7:     */ import java.util.Arrays;
/*    8:     */ import java.util.HashMap;
/*    9:     */ import java.util.Iterator;
/*   10:     */ import java.util.Map;
/*   11:     */ import java.util.Set;
/*   12:     */ import org.hibernate.AssertionFailure;
/*   13:     */ import org.hibernate.FetchMode;
/*   14:     */ import org.hibernate.HibernateException;
/*   15:     */ import org.hibernate.MappingException;
/*   16:     */ import org.hibernate.QueryException;
/*   17:     */ import org.hibernate.cache.CacheException;
/*   18:     */ import org.hibernate.cache.spi.access.CollectionRegionAccessStrategy;
/*   19:     */ import org.hibernate.cache.spi.entry.CacheEntryStructure;
/*   20:     */ import org.hibernate.cache.spi.entry.StructuredCollectionCacheEntry;
/*   21:     */ import org.hibernate.cache.spi.entry.StructuredMapCacheEntry;
/*   22:     */ import org.hibernate.cache.spi.entry.UnstructuredCacheEntry;
/*   23:     */ import org.hibernate.cfg.Configuration;
/*   24:     */ import org.hibernate.cfg.Settings;
/*   25:     */ import org.hibernate.collection.spi.PersistentCollection;
/*   26:     */ import org.hibernate.dialect.Dialect;
/*   27:     */ import org.hibernate.engine.jdbc.batch.internal.BasicBatchKey;
/*   28:     */ import org.hibernate.engine.jdbc.batch.spi.Batch;
/*   29:     */ import org.hibernate.engine.jdbc.spi.JdbcCoordinator;
/*   30:     */ import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
/*   31:     */ import org.hibernate.engine.jdbc.spi.StatementPreparer;
/*   32:     */ import org.hibernate.engine.spi.BatchFetchQueue;
/*   33:     */ import org.hibernate.engine.spi.EntityKey;
/*   34:     */ import org.hibernate.engine.spi.ExecuteUpdateResultCheckStyle;
/*   35:     */ import org.hibernate.engine.spi.LoadQueryInfluencers;
/*   36:     */ import org.hibernate.engine.spi.PersistenceContext;
/*   37:     */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*   38:     */ import org.hibernate.engine.spi.SessionImplementor;
/*   39:     */ import org.hibernate.engine.spi.SubselectFetch;
/*   40:     */ import org.hibernate.engine.transaction.spi.TransactionCoordinator;
/*   41:     */ import org.hibernate.exception.spi.SQLExceptionConverter;
/*   42:     */ import org.hibernate.id.IdentifierGenerator;
/*   43:     */ import org.hibernate.internal.CoreMessageLogger;
/*   44:     */ import org.hibernate.internal.FilterHelper;
/*   45:     */ import org.hibernate.internal.util.StringHelper;
/*   46:     */ import org.hibernate.internal.util.collections.ArrayHelper;
/*   47:     */ import org.hibernate.jdbc.Expectation;
/*   48:     */ import org.hibernate.jdbc.Expectations;
/*   49:     */ import org.hibernate.loader.collection.CollectionInitializer;
/*   50:     */ import org.hibernate.mapping.Array;
/*   51:     */ import org.hibernate.mapping.Collection;
/*   52:     */ import org.hibernate.mapping.Column;
/*   53:     */ import org.hibernate.mapping.Formula;
/*   54:     */ import org.hibernate.mapping.IdentifierCollection;
/*   55:     */ import org.hibernate.mapping.IndexedCollection;
/*   56:     */ import org.hibernate.mapping.KeyValue;
/*   57:     */ import org.hibernate.mapping.List;
/*   58:     */ import org.hibernate.mapping.PersistentClass;
/*   59:     */ import org.hibernate.mapping.Selectable;
/*   60:     */ import org.hibernate.mapping.Table;
/*   61:     */ import org.hibernate.mapping.Value;
/*   62:     */ import org.hibernate.metadata.CollectionMetadata;
/*   63:     */ import org.hibernate.persister.entity.EntityPersister;
/*   64:     */ import org.hibernate.persister.entity.PropertyMapping;
/*   65:     */ import org.hibernate.pretty.MessageHelper;
/*   66:     */ import org.hibernate.sql.Alias;
/*   67:     */ import org.hibernate.sql.SelectFragment;
/*   68:     */ import org.hibernate.sql.SimpleSelect;
/*   69:     */ import org.hibernate.sql.Template;
/*   70:     */ import org.hibernate.sql.ordering.antlr.ColumnMapper;
/*   71:     */ import org.hibernate.type.CollectionType;
/*   72:     */ import org.hibernate.type.CompositeType;
/*   73:     */ import org.hibernate.type.EntityType;
/*   74:     */ import org.hibernate.type.Type;
/*   75:     */ import org.jboss.logging.Logger;
/*   76:     */ 
/*   77:     */ public abstract class AbstractCollectionPersister
/*   78:     */   implements CollectionMetadata, SQLLoadableCollection
/*   79:     */ {
/*   80: 102 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, AbstractCollectionPersister.class.getName());
/*   81:     */   private final String role;
/*   82:     */   private final String sqlDeleteString;
/*   83:     */   private final String sqlInsertRowString;
/*   84:     */   private final String sqlUpdateRowString;
/*   85:     */   private final String sqlDeleteRowString;
/*   86:     */   private final String sqlSelectSizeString;
/*   87:     */   private final String sqlSelectRowByIndexString;
/*   88:     */   private final String sqlDetectRowByIndexString;
/*   89:     */   private final String sqlDetectRowByElementString;
/*   90:     */   protected final String sqlWhereString;
/*   91:     */   private final String sqlOrderByStringTemplate;
/*   92:     */   private final String sqlWhereStringTemplate;
/*   93:     */   private final boolean hasOrder;
/*   94:     */   protected final boolean hasWhere;
/*   95:     */   private final int baseIndex;
/*   96:     */   private final String nodeName;
/*   97:     */   private final String elementNodeName;
/*   98:     */   private final String indexNodeName;
/*   99:     */   protected final boolean indexContainsFormula;
/*  100:     */   protected final boolean elementIsPureFormula;
/*  101:     */   private final Type keyType;
/*  102:     */   private final Type indexType;
/*  103:     */   protected final Type elementType;
/*  104:     */   private final Type identifierType;
/*  105:     */   protected final String[] keyColumnNames;
/*  106:     */   protected final String[] indexColumnNames;
/*  107:     */   protected final String[] indexFormulaTemplates;
/*  108:     */   protected final String[] indexFormulas;
/*  109:     */   protected final boolean[] indexColumnIsSettable;
/*  110:     */   protected final String[] elementColumnNames;
/*  111:     */   protected final String[] elementColumnWriters;
/*  112:     */   protected final String[] elementColumnReaders;
/*  113:     */   protected final String[] elementColumnReaderTemplates;
/*  114:     */   protected final String[] elementFormulaTemplates;
/*  115:     */   protected final String[] elementFormulas;
/*  116:     */   protected final boolean[] elementColumnIsSettable;
/*  117:     */   protected final boolean[] elementColumnIsInPrimaryKey;
/*  118:     */   protected final String[] indexColumnAliases;
/*  119:     */   protected final String[] elementColumnAliases;
/*  120:     */   protected final String[] keyColumnAliases;
/*  121:     */   protected final String identifierColumnName;
/*  122:     */   private final String identifierColumnAlias;
/*  123:     */   protected final String qualifiedTableName;
/*  124:     */   private final String queryLoaderName;
/*  125:     */   private final boolean isPrimitiveArray;
/*  126:     */   private final boolean isArray;
/*  127:     */   protected final boolean hasIndex;
/*  128:     */   protected final boolean hasIdentifier;
/*  129:     */   private final boolean isLazy;
/*  130:     */   private final boolean isExtraLazy;
/*  131:     */   private final boolean isInverse;
/*  132:     */   private final boolean isMutable;
/*  133:     */   private final boolean isVersioned;
/*  134:     */   protected final int batchSize;
/*  135:     */   private final FetchMode fetchMode;
/*  136:     */   private final boolean hasOrphanDelete;
/*  137:     */   private final boolean subselectLoadable;
/*  138:     */   private final Class elementClass;
/*  139:     */   private final String entityName;
/*  140:     */   private final Dialect dialect;
/*  141:     */   private final SqlExceptionHelper sqlExceptionHelper;
/*  142:     */   private final SessionFactoryImplementor factory;
/*  143:     */   private final EntityPersister ownerPersister;
/*  144:     */   private final IdentifierGenerator identifierGenerator;
/*  145:     */   private final PropertyMapping elementPropertyMapping;
/*  146:     */   private final EntityPersister elementPersister;
/*  147:     */   private final CollectionRegionAccessStrategy cacheAccessStrategy;
/*  148:     */   private final CollectionType collectionType;
/*  149:     */   private CollectionInitializer initializer;
/*  150:     */   private final CacheEntryStructure cacheEntryStructure;
/*  151:     */   private final FilterHelper filterHelper;
/*  152:     */   private final FilterHelper manyToManyFilterHelper;
/*  153:     */   private final String manyToManyWhereString;
/*  154:     */   private final String manyToManyWhereTemplate;
/*  155:     */   private final boolean hasManyToManyOrder;
/*  156:     */   private final String manyToManyOrderByTemplate;
/*  157:     */   private final boolean insertCallable;
/*  158:     */   private final boolean updateCallable;
/*  159:     */   private final boolean deleteCallable;
/*  160:     */   private final boolean deleteAllCallable;
/*  161:     */   private ExecuteUpdateResultCheckStyle insertCheckStyle;
/*  162:     */   private ExecuteUpdateResultCheckStyle updateCheckStyle;
/*  163:     */   private ExecuteUpdateResultCheckStyle deleteCheckStyle;
/*  164:     */   private ExecuteUpdateResultCheckStyle deleteAllCheckStyle;
/*  165:     */   private final Serializable[] spaces;
/*  166: 220 */   private Map collectionPropertyColumnAliases = new HashMap();
/*  167: 221 */   private Map collectionPropertyColumnNames = new HashMap();
/*  168:     */   private BasicBatchKey removeBatchKey;
/*  169:     */   private BasicBatchKey recreateBatchKey;
/*  170:     */   private BasicBatchKey deleteBatchKey;
/*  171:     */   private BasicBatchKey insertBatchKey;
/*  172:     */   private String[] indexFragments;
/*  173:     */   
/*  174:     */   public AbstractCollectionPersister(Collection collection, CollectionRegionAccessStrategy cacheAccessStrategy, Configuration cfg, SessionFactoryImplementor factory)
/*  175:     */     throws MappingException, CacheException
/*  176:     */   {
/*  177: 229 */     this.factory = factory;
/*  178: 230 */     this.cacheAccessStrategy = cacheAccessStrategy;
/*  179: 231 */     if (factory.getSettings().isStructuredCacheEntriesEnabled()) {
/*  180: 232 */       this.cacheEntryStructure = (collection.isMap() ? new StructuredMapCacheEntry() : new StructuredCollectionCacheEntry());
/*  181:     */     } else {
/*  182: 237 */       this.cacheEntryStructure = new UnstructuredCacheEntry();
/*  183:     */     }
/*  184: 240 */     this.dialect = factory.getDialect();
/*  185: 241 */     this.sqlExceptionHelper = factory.getSQLExceptionHelper();
/*  186: 242 */     this.collectionType = collection.getCollectionType();
/*  187: 243 */     this.role = collection.getRole();
/*  188: 244 */     this.entityName = collection.getOwnerEntityName();
/*  189: 245 */     this.ownerPersister = factory.getEntityPersister(this.entityName);
/*  190: 246 */     this.queryLoaderName = collection.getLoaderName();
/*  191: 247 */     this.nodeName = collection.getNodeName();
/*  192: 248 */     this.isMutable = collection.isMutable();
/*  193:     */     
/*  194: 250 */     Table table = collection.getCollectionTable();
/*  195: 251 */     this.fetchMode = collection.getElement().getFetchMode();
/*  196: 252 */     this.elementType = collection.getElement().getType();
/*  197:     */     
/*  198:     */ 
/*  199: 255 */     this.isPrimitiveArray = collection.isPrimitiveArray();
/*  200: 256 */     this.isArray = collection.isArray();
/*  201: 257 */     this.subselectLoadable = collection.isSubselectLoadable();
/*  202:     */     
/*  203: 259 */     this.qualifiedTableName = table.getQualifiedName(this.dialect, factory.getSettings().getDefaultCatalogName(), factory.getSettings().getDefaultSchemaName());
/*  204:     */     
/*  205:     */ 
/*  206:     */ 
/*  207:     */ 
/*  208:     */ 
/*  209: 265 */     int spacesSize = 1 + collection.getSynchronizedTables().size();
/*  210: 266 */     this.spaces = new String[spacesSize];
/*  211: 267 */     this.spaces[0] = this.qualifiedTableName;
/*  212: 268 */     Iterator iter = collection.getSynchronizedTables().iterator();
/*  213: 269 */     for (int i = 1; i < spacesSize; i++) {
/*  214: 270 */       this.spaces[i] = ((String)iter.next());
/*  215:     */     }
/*  216: 273 */     this.sqlWhereString = (StringHelper.isNotEmpty(collection.getWhere()) ? "( " + collection.getWhere() + ") " : null);
/*  217: 274 */     this.hasWhere = (this.sqlWhereString != null);
/*  218: 275 */     this.sqlWhereStringTemplate = (this.hasWhere ? Template.renderWhereStringTemplate(this.sqlWhereString, this.dialect, factory.getSqlFunctionRegistry()) : null);
/*  219:     */     
/*  220:     */ 
/*  221:     */ 
/*  222: 279 */     this.hasOrphanDelete = collection.hasOrphanDelete();
/*  223:     */     
/*  224: 281 */     int batch = collection.getBatchSize();
/*  225: 282 */     if (batch == -1) {
/*  226: 283 */       batch = factory.getSettings().getDefaultBatchFetchSize();
/*  227:     */     }
/*  228: 285 */     this.batchSize = batch;
/*  229:     */     
/*  230: 287 */     this.isVersioned = collection.isOptimisticLocked();
/*  231:     */     
/*  232:     */ 
/*  233:     */ 
/*  234: 291 */     this.keyType = collection.getKey().getType();
/*  235: 292 */     iter = collection.getKey().getColumnIterator();
/*  236: 293 */     int keySpan = collection.getKey().getColumnSpan();
/*  237: 294 */     this.keyColumnNames = new String[keySpan];
/*  238: 295 */     this.keyColumnAliases = new String[keySpan];
/*  239: 296 */     int k = 0;
/*  240: 297 */     while (iter.hasNext())
/*  241:     */     {
/*  242: 299 */       Column col = (Column)iter.next();
/*  243: 300 */       this.keyColumnNames[k] = col.getQuotedName(this.dialect);
/*  244: 301 */       this.keyColumnAliases[k] = col.getAlias(this.dialect, collection.getOwner().getRootTable());
/*  245: 302 */       k++;
/*  246:     */     }
/*  247: 309 */     String elemNode = collection.getElementNodeName();
/*  248: 310 */     if (this.elementType.isEntityType())
/*  249:     */     {
/*  250: 311 */       String entityName = ((EntityType)this.elementType).getAssociatedEntityName();
/*  251: 312 */       this.elementPersister = factory.getEntityPersister(entityName);
/*  252: 313 */       if (elemNode == null) {
/*  253: 314 */         elemNode = cfg.getClassMapping(entityName).getNodeName();
/*  254:     */       }
/*  255:     */     }
/*  256:     */     else
/*  257:     */     {
/*  258: 320 */       this.elementPersister = null;
/*  259:     */     }
/*  260: 322 */     this.elementNodeName = elemNode;
/*  261:     */     
/*  262: 324 */     int elementSpan = collection.getElement().getColumnSpan();
/*  263: 325 */     this.elementColumnAliases = new String[elementSpan];
/*  264: 326 */     this.elementColumnNames = new String[elementSpan];
/*  265: 327 */     this.elementColumnWriters = new String[elementSpan];
/*  266: 328 */     this.elementColumnReaders = new String[elementSpan];
/*  267: 329 */     this.elementColumnReaderTemplates = new String[elementSpan];
/*  268: 330 */     this.elementFormulaTemplates = new String[elementSpan];
/*  269: 331 */     this.elementFormulas = new String[elementSpan];
/*  270: 332 */     this.elementColumnIsSettable = new boolean[elementSpan];
/*  271: 333 */     this.elementColumnIsInPrimaryKey = new boolean[elementSpan];
/*  272: 334 */     boolean isPureFormula = true;
/*  273: 335 */     boolean hasNotNullableColumns = false;
/*  274: 336 */     int j = 0;
/*  275: 337 */     iter = collection.getElement().getColumnIterator();
/*  276: 338 */     while (iter.hasNext())
/*  277:     */     {
/*  278: 339 */       Selectable selectable = (Selectable)iter.next();
/*  279: 340 */       this.elementColumnAliases[j] = selectable.getAlias(this.dialect);
/*  280: 341 */       if (selectable.isFormula())
/*  281:     */       {
/*  282: 342 */         Formula form = (Formula)selectable;
/*  283: 343 */         this.elementFormulaTemplates[j] = form.getTemplate(this.dialect, factory.getSqlFunctionRegistry());
/*  284: 344 */         this.elementFormulas[j] = form.getFormula();
/*  285:     */       }
/*  286:     */       else
/*  287:     */       {
/*  288: 347 */         Column col = (Column)selectable;
/*  289: 348 */         this.elementColumnNames[j] = col.getQuotedName(this.dialect);
/*  290: 349 */         this.elementColumnWriters[j] = col.getWriteExpr();
/*  291: 350 */         this.elementColumnReaders[j] = col.getReadExpr(this.dialect);
/*  292: 351 */         this.elementColumnReaderTemplates[j] = col.getTemplate(this.dialect, factory.getSqlFunctionRegistry());
/*  293: 352 */         this.elementColumnIsSettable[j] = true;
/*  294: 353 */         this.elementColumnIsInPrimaryKey[j] = (!col.isNullable() ? 1 : false);
/*  295: 354 */         if (!col.isNullable()) {
/*  296: 355 */           hasNotNullableColumns = true;
/*  297:     */         }
/*  298: 357 */         isPureFormula = false;
/*  299:     */       }
/*  300: 359 */       j++;
/*  301:     */     }
/*  302: 361 */     this.elementIsPureFormula = isPureFormula;
/*  303: 366 */     if (!hasNotNullableColumns) {
/*  304: 367 */       Arrays.fill(this.elementColumnIsInPrimaryKey, true);
/*  305:     */     }
/*  306: 372 */     this.hasIndex = collection.isIndexed();
/*  307: 373 */     if (this.hasIndex)
/*  308:     */     {
/*  309: 375 */       IndexedCollection indexedCollection = (IndexedCollection)collection;
/*  310: 376 */       this.indexType = indexedCollection.getIndex().getType();
/*  311: 377 */       int indexSpan = indexedCollection.getIndex().getColumnSpan();
/*  312: 378 */       iter = indexedCollection.getIndex().getColumnIterator();
/*  313: 379 */       this.indexColumnNames = new String[indexSpan];
/*  314: 380 */       this.indexFormulaTemplates = new String[indexSpan];
/*  315: 381 */       this.indexFormulas = new String[indexSpan];
/*  316: 382 */       this.indexColumnIsSettable = new boolean[indexSpan];
/*  317: 383 */       this.indexColumnAliases = new String[indexSpan];
/*  318: 384 */       int i = 0;
/*  319: 385 */       boolean hasFormula = false;
/*  320: 386 */       while (iter.hasNext())
/*  321:     */       {
/*  322: 387 */         Selectable s = (Selectable)iter.next();
/*  323: 388 */         this.indexColumnAliases[i] = s.getAlias(this.dialect);
/*  324: 389 */         if (s.isFormula())
/*  325:     */         {
/*  326: 390 */           Formula indexForm = (Formula)s;
/*  327: 391 */           this.indexFormulaTemplates[i] = indexForm.getTemplate(this.dialect, factory.getSqlFunctionRegistry());
/*  328: 392 */           this.indexFormulas[i] = indexForm.getFormula();
/*  329: 393 */           hasFormula = true;
/*  330:     */         }
/*  331:     */         else
/*  332:     */         {
/*  333: 396 */           Column indexCol = (Column)s;
/*  334: 397 */           this.indexColumnNames[i] = indexCol.getQuotedName(this.dialect);
/*  335: 398 */           this.indexColumnIsSettable[i] = true;
/*  336:     */         }
/*  337: 400 */         i++;
/*  338:     */       }
/*  339: 402 */       this.indexContainsFormula = hasFormula;
/*  340: 403 */       this.baseIndex = (indexedCollection.isList() ? ((List)indexedCollection).getBaseIndex() : 0);
/*  341:     */       
/*  342:     */ 
/*  343: 406 */       this.indexNodeName = indexedCollection.getIndexNodeName();
/*  344:     */     }
/*  345:     */     else
/*  346:     */     {
/*  347: 410 */       this.indexContainsFormula = false;
/*  348: 411 */       this.indexColumnIsSettable = null;
/*  349: 412 */       this.indexFormulaTemplates = null;
/*  350: 413 */       this.indexFormulas = null;
/*  351: 414 */       this.indexType = null;
/*  352: 415 */       this.indexColumnNames = null;
/*  353: 416 */       this.indexColumnAliases = null;
/*  354: 417 */       this.baseIndex = 0;
/*  355: 418 */       this.indexNodeName = null;
/*  356:     */     }
/*  357: 421 */     this.hasIdentifier = collection.isIdentified();
/*  358: 422 */     if (this.hasIdentifier)
/*  359:     */     {
/*  360: 423 */       if (collection.isOneToMany()) {
/*  361: 424 */         throw new MappingException("one-to-many collections with identifiers are not supported");
/*  362:     */       }
/*  363: 426 */       IdentifierCollection idColl = (IdentifierCollection)collection;
/*  364: 427 */       this.identifierType = idColl.getIdentifier().getType();
/*  365: 428 */       iter = idColl.getIdentifier().getColumnIterator();
/*  366: 429 */       Column col = (Column)iter.next();
/*  367: 430 */       this.identifierColumnName = col.getQuotedName(this.dialect);
/*  368: 431 */       this.identifierColumnAlias = col.getAlias(this.dialect);
/*  369:     */       
/*  370: 433 */       this.identifierGenerator = idColl.getIdentifier().createIdentifierGenerator(cfg.getIdentifierGeneratorFactory(), factory.getDialect(), factory.getSettings().getDefaultCatalogName(), factory.getSettings().getDefaultSchemaName(), null);
/*  371:     */     }
/*  372:     */     else
/*  373:     */     {
/*  374: 442 */       this.identifierType = null;
/*  375: 443 */       this.identifierColumnName = null;
/*  376: 444 */       this.identifierColumnAlias = null;
/*  377:     */       
/*  378: 446 */       this.identifierGenerator = null;
/*  379:     */     }
/*  380: 454 */     if (collection.getCustomSQLInsert() == null)
/*  381:     */     {
/*  382: 455 */       this.sqlInsertRowString = generateInsertRowString();
/*  383: 456 */       this.insertCallable = false;
/*  384: 457 */       this.insertCheckStyle = ExecuteUpdateResultCheckStyle.COUNT;
/*  385:     */     }
/*  386:     */     else
/*  387:     */     {
/*  388: 460 */       this.sqlInsertRowString = collection.getCustomSQLInsert();
/*  389: 461 */       this.insertCallable = collection.isCustomInsertCallable();
/*  390: 462 */       this.insertCheckStyle = (collection.getCustomSQLInsertCheckStyle() == null ? ExecuteUpdateResultCheckStyle.determineDefault(collection.getCustomSQLInsert(), this.insertCallable) : collection.getCustomSQLInsertCheckStyle());
/*  391:     */     }
/*  392: 467 */     if (collection.getCustomSQLUpdate() == null)
/*  393:     */     {
/*  394: 468 */       this.sqlUpdateRowString = generateUpdateRowString();
/*  395: 469 */       this.updateCallable = false;
/*  396: 470 */       this.updateCheckStyle = ExecuteUpdateResultCheckStyle.COUNT;
/*  397:     */     }
/*  398:     */     else
/*  399:     */     {
/*  400: 473 */       this.sqlUpdateRowString = collection.getCustomSQLUpdate();
/*  401: 474 */       this.updateCallable = collection.isCustomUpdateCallable();
/*  402: 475 */       this.updateCheckStyle = (collection.getCustomSQLUpdateCheckStyle() == null ? ExecuteUpdateResultCheckStyle.determineDefault(collection.getCustomSQLUpdate(), this.insertCallable) : collection.getCustomSQLUpdateCheckStyle());
/*  403:     */     }
/*  404: 480 */     if (collection.getCustomSQLDelete() == null)
/*  405:     */     {
/*  406: 481 */       this.sqlDeleteRowString = generateDeleteRowString();
/*  407: 482 */       this.deleteCallable = false;
/*  408: 483 */       this.deleteCheckStyle = ExecuteUpdateResultCheckStyle.NONE;
/*  409:     */     }
/*  410:     */     else
/*  411:     */     {
/*  412: 486 */       this.sqlDeleteRowString = collection.getCustomSQLDelete();
/*  413: 487 */       this.deleteCallable = collection.isCustomDeleteCallable();
/*  414: 488 */       this.deleteCheckStyle = ExecuteUpdateResultCheckStyle.NONE;
/*  415:     */     }
/*  416: 491 */     if (collection.getCustomSQLDeleteAll() == null)
/*  417:     */     {
/*  418: 492 */       this.sqlDeleteString = generateDeleteString();
/*  419: 493 */       this.deleteAllCallable = false;
/*  420: 494 */       this.deleteAllCheckStyle = ExecuteUpdateResultCheckStyle.NONE;
/*  421:     */     }
/*  422:     */     else
/*  423:     */     {
/*  424: 497 */       this.sqlDeleteString = collection.getCustomSQLDeleteAll();
/*  425: 498 */       this.deleteAllCallable = collection.isCustomDeleteAllCallable();
/*  426: 499 */       this.deleteAllCheckStyle = ExecuteUpdateResultCheckStyle.NONE;
/*  427:     */     }
/*  428: 502 */     this.sqlSelectSizeString = generateSelectSizeString((collection.isIndexed()) && (!collection.isMap()));
/*  429: 503 */     this.sqlDetectRowByIndexString = generateDetectRowByIndexString();
/*  430: 504 */     this.sqlDetectRowByElementString = generateDetectRowByElementString();
/*  431: 505 */     this.sqlSelectRowByIndexString = generateSelectRowByIndexString();
/*  432:     */     
/*  433: 507 */     logStaticSQL();
/*  434:     */     
/*  435: 509 */     this.isLazy = collection.isLazy();
/*  436: 510 */     this.isExtraLazy = collection.isExtraLazy();
/*  437:     */     
/*  438: 512 */     this.isInverse = collection.isInverse();
/*  439: 514 */     if (collection.isArray()) {
/*  440: 515 */       this.elementClass = ((Array)collection).getElementClass();
/*  441:     */     } else {
/*  442: 519 */       this.elementClass = null;
/*  443:     */     }
/*  444: 522 */     if (this.elementType.isComponentType()) {
/*  445: 523 */       this.elementPropertyMapping = new CompositeElementPropertyMapping(this.elementColumnNames, this.elementColumnReaders, this.elementColumnReaderTemplates, this.elementFormulaTemplates, (CompositeType)this.elementType, factory);
/*  446: 532 */     } else if (!this.elementType.isEntityType()) {
/*  447: 533 */       this.elementPropertyMapping = new ElementPropertyMapping(this.elementColumnNames, this.elementType);
/*  448: 539 */     } else if ((this.elementPersister instanceof PropertyMapping)) {
/*  449: 540 */       this.elementPropertyMapping = ((PropertyMapping)this.elementPersister);
/*  450:     */     } else {
/*  451: 543 */       this.elementPropertyMapping = new ElementPropertyMapping(this.elementColumnNames, this.elementType);
/*  452:     */     }
/*  453: 550 */     this.hasOrder = (collection.getOrderBy() != null);
/*  454: 551 */     if (this.hasOrder)
/*  455:     */     {
/*  456: 552 */       ColumnMapper mapper = new ColumnMapper()
/*  457:     */       {
/*  458:     */         public String[] map(String reference)
/*  459:     */         {
/*  460: 555 */           return AbstractCollectionPersister.this.elementPropertyMapping.toColumns(reference);
/*  461:     */         }
/*  462: 557 */       };
/*  463: 558 */       this.sqlOrderByStringTemplate = Template.renderOrderByStringTemplate(collection.getOrderBy(), mapper, factory, this.dialect, factory.getSqlFunctionRegistry());
/*  464:     */     }
/*  465:     */     else
/*  466:     */     {
/*  467: 567 */       this.sqlOrderByStringTemplate = null;
/*  468:     */     }
/*  469: 571 */     this.filterHelper = new FilterHelper(collection.getFilterMap(), this.dialect, factory.getSqlFunctionRegistry());
/*  470:     */     
/*  471:     */ 
/*  472: 574 */     this.manyToManyFilterHelper = new FilterHelper(collection.getManyToManyFilterMap(), this.dialect, factory.getSqlFunctionRegistry());
/*  473: 575 */     this.manyToManyWhereString = (StringHelper.isNotEmpty(collection.getManyToManyWhere()) ? "( " + collection.getManyToManyWhere() + ")" : null);
/*  474:     */     
/*  475:     */ 
/*  476: 578 */     this.manyToManyWhereTemplate = (this.manyToManyWhereString == null ? null : Template.renderWhereStringTemplate(this.manyToManyWhereString, factory.getDialect(), factory.getSqlFunctionRegistry()));
/*  477:     */     
/*  478:     */ 
/*  479:     */ 
/*  480: 582 */     this.hasManyToManyOrder = (collection.getManyToManyOrdering() != null);
/*  481: 583 */     if (this.hasManyToManyOrder)
/*  482:     */     {
/*  483: 584 */       ColumnMapper mapper = new ColumnMapper()
/*  484:     */       {
/*  485:     */         public String[] map(String reference)
/*  486:     */         {
/*  487: 587 */           return AbstractCollectionPersister.this.elementPropertyMapping.toColumns(reference);
/*  488:     */         }
/*  489: 589 */       };
/*  490: 590 */       this.manyToManyOrderByTemplate = Template.renderOrderByStringTemplate(collection.getManyToManyOrdering(), mapper, factory, this.dialect, factory.getSqlFunctionRegistry());
/*  491:     */     }
/*  492:     */     else
/*  493:     */     {
/*  494: 599 */       this.manyToManyOrderByTemplate = null;
/*  495:     */     }
/*  496: 602 */     initCollectionPropertyMap();
/*  497:     */   }
/*  498:     */   
/*  499:     */   public void postInstantiate()
/*  500:     */     throws MappingException
/*  501:     */   {
/*  502: 606 */     this.initializer = (this.queryLoaderName == null ? createCollectionInitializer(LoadQueryInfluencers.NONE) : new NamedQueryCollectionInitializer(this.queryLoaderName, this));
/*  503:     */   }
/*  504:     */   
/*  505:     */   protected void logStaticSQL()
/*  506:     */   {
/*  507: 612 */     if (LOG.isDebugEnabled())
/*  508:     */     {
/*  509: 613 */       LOG.debugf("Static SQL for collection: %s", getRole());
/*  510: 614 */       if (getSQLInsertRowString() != null) {
/*  511: 614 */         LOG.debugf(" Row insert: %s", getSQLInsertRowString());
/*  512:     */       }
/*  513: 615 */       if (getSQLUpdateRowString() != null) {
/*  514: 615 */         LOG.debugf(" Row update: %s", getSQLUpdateRowString());
/*  515:     */       }
/*  516: 616 */       if (getSQLDeleteRowString() != null) {
/*  517: 616 */         LOG.debugf(" Row delete: %s", getSQLDeleteRowString());
/*  518:     */       }
/*  519: 617 */       if (getSQLDeleteString() != null) {
/*  520: 617 */         LOG.debugf(" One-shot delete: %s", getSQLDeleteString());
/*  521:     */       }
/*  522:     */     }
/*  523:     */   }
/*  524:     */   
/*  525:     */   public void initialize(Serializable key, SessionImplementor session)
/*  526:     */     throws HibernateException
/*  527:     */   {
/*  528: 622 */     getAppropriateInitializer(key, session).initialize(key, session);
/*  529:     */   }
/*  530:     */   
/*  531:     */   protected CollectionInitializer getAppropriateInitializer(Serializable key, SessionImplementor session)
/*  532:     */   {
/*  533: 626 */     if (this.queryLoaderName != null) {
/*  534: 629 */       return this.initializer;
/*  535:     */     }
/*  536: 631 */     CollectionInitializer subselectInitializer = getSubselectInitializer(key, session);
/*  537: 632 */     if (subselectInitializer != null) {
/*  538: 633 */       return subselectInitializer;
/*  539:     */     }
/*  540: 635 */     if (session.getEnabledFilters().isEmpty()) {
/*  541: 636 */       return this.initializer;
/*  542:     */     }
/*  543: 639 */     return createCollectionInitializer(session.getLoadQueryInfluencers());
/*  544:     */   }
/*  545:     */   
/*  546:     */   private CollectionInitializer getSubselectInitializer(Serializable key, SessionImplementor session)
/*  547:     */   {
/*  548: 645 */     if (!isSubselectLoadable()) {
/*  549: 646 */       return null;
/*  550:     */     }
/*  551: 649 */     PersistenceContext persistenceContext = session.getPersistenceContext();
/*  552:     */     
/*  553: 651 */     SubselectFetch subselect = persistenceContext.getBatchFetchQueue().getSubselect(session.generateEntityKey(key, getOwnerEntityPersister()));
/*  554: 654 */     if (subselect == null) {
/*  555: 655 */       return null;
/*  556:     */     }
/*  557: 661 */     Iterator iter = subselect.getResult().iterator();
/*  558: 662 */     while (iter.hasNext()) {
/*  559: 663 */       if (!persistenceContext.containsEntity((EntityKey)iter.next())) {
/*  560: 664 */         iter.remove();
/*  561:     */       }
/*  562:     */     }
/*  563: 669 */     return createSubselectInitializer(subselect, session);
/*  564:     */   }
/*  565:     */   
/*  566:     */   protected abstract CollectionInitializer createSubselectInitializer(SubselectFetch paramSubselectFetch, SessionImplementor paramSessionImplementor);
/*  567:     */   
/*  568:     */   protected abstract CollectionInitializer createCollectionInitializer(LoadQueryInfluencers paramLoadQueryInfluencers)
/*  569:     */     throws MappingException;
/*  570:     */   
/*  571:     */   public CollectionRegionAccessStrategy getCacheAccessStrategy()
/*  572:     */   {
/*  573: 679 */     return this.cacheAccessStrategy;
/*  574:     */   }
/*  575:     */   
/*  576:     */   public boolean hasCache()
/*  577:     */   {
/*  578: 683 */     return this.cacheAccessStrategy != null;
/*  579:     */   }
/*  580:     */   
/*  581:     */   public CollectionType getCollectionType()
/*  582:     */   {
/*  583: 687 */     return this.collectionType;
/*  584:     */   }
/*  585:     */   
/*  586:     */   protected String getSQLWhereString(String alias)
/*  587:     */   {
/*  588: 691 */     return StringHelper.replace(this.sqlWhereStringTemplate, "$PlaceHolder$", alias);
/*  589:     */   }
/*  590:     */   
/*  591:     */   public String getSQLOrderByString(String alias)
/*  592:     */   {
/*  593: 695 */     return hasOrdering() ? StringHelper.replace(this.sqlOrderByStringTemplate, "$PlaceHolder$", alias) : "";
/*  594:     */   }
/*  595:     */   
/*  596:     */   public String getManyToManyOrderByString(String alias)
/*  597:     */   {
/*  598: 701 */     return hasManyToManyOrdering() ? StringHelper.replace(this.manyToManyOrderByTemplate, "$PlaceHolder$", alias) : "";
/*  599:     */   }
/*  600:     */   
/*  601:     */   public FetchMode getFetchMode()
/*  602:     */   {
/*  603: 707 */     return this.fetchMode;
/*  604:     */   }
/*  605:     */   
/*  606:     */   public boolean hasOrdering()
/*  607:     */   {
/*  608: 711 */     return this.hasOrder;
/*  609:     */   }
/*  610:     */   
/*  611:     */   public boolean hasManyToManyOrdering()
/*  612:     */   {
/*  613: 715 */     return (isManyToMany()) && (this.hasManyToManyOrder);
/*  614:     */   }
/*  615:     */   
/*  616:     */   public boolean hasWhere()
/*  617:     */   {
/*  618: 719 */     return this.hasWhere;
/*  619:     */   }
/*  620:     */   
/*  621:     */   protected String getSQLDeleteString()
/*  622:     */   {
/*  623: 723 */     return this.sqlDeleteString;
/*  624:     */   }
/*  625:     */   
/*  626:     */   protected String getSQLInsertRowString()
/*  627:     */   {
/*  628: 727 */     return this.sqlInsertRowString;
/*  629:     */   }
/*  630:     */   
/*  631:     */   protected String getSQLUpdateRowString()
/*  632:     */   {
/*  633: 731 */     return this.sqlUpdateRowString;
/*  634:     */   }
/*  635:     */   
/*  636:     */   protected String getSQLDeleteRowString()
/*  637:     */   {
/*  638: 735 */     return this.sqlDeleteRowString;
/*  639:     */   }
/*  640:     */   
/*  641:     */   public Type getKeyType()
/*  642:     */   {
/*  643: 739 */     return this.keyType;
/*  644:     */   }
/*  645:     */   
/*  646:     */   public Type getIndexType()
/*  647:     */   {
/*  648: 743 */     return this.indexType;
/*  649:     */   }
/*  650:     */   
/*  651:     */   public Type getElementType()
/*  652:     */   {
/*  653: 747 */     return this.elementType;
/*  654:     */   }
/*  655:     */   
/*  656:     */   public Class getElementClass()
/*  657:     */   {
/*  658: 754 */     return this.elementClass;
/*  659:     */   }
/*  660:     */   
/*  661:     */   public Object readElement(ResultSet rs, Object owner, String[] aliases, SessionImplementor session)
/*  662:     */     throws HibernateException, SQLException
/*  663:     */   {
/*  664: 759 */     return getElementType().nullSafeGet(rs, aliases, session, owner);
/*  665:     */   }
/*  666:     */   
/*  667:     */   public Object readIndex(ResultSet rs, String[] aliases, SessionImplementor session)
/*  668:     */     throws HibernateException, SQLException
/*  669:     */   {
/*  670: 764 */     Object index = getIndexType().nullSafeGet(rs, aliases, session, null);
/*  671: 765 */     if (index == null) {
/*  672: 766 */       throw new HibernateException("null index column for collection: " + this.role);
/*  673:     */     }
/*  674: 768 */     index = decrementIndexByBase(index);
/*  675: 769 */     return index;
/*  676:     */   }
/*  677:     */   
/*  678:     */   protected Object decrementIndexByBase(Object index)
/*  679:     */   {
/*  680: 773 */     if (this.baseIndex != 0) {
/*  681: 774 */       index = Integer.valueOf(((Integer)index).intValue() - this.baseIndex);
/*  682:     */     }
/*  683: 776 */     return index;
/*  684:     */   }
/*  685:     */   
/*  686:     */   public Object readIdentifier(ResultSet rs, String alias, SessionImplementor session)
/*  687:     */     throws HibernateException, SQLException
/*  688:     */   {
/*  689: 781 */     Object id = getIdentifierType().nullSafeGet(rs, alias, session, null);
/*  690: 782 */     if (id == null) {
/*  691: 783 */       throw new HibernateException("null identifier column for collection: " + this.role);
/*  692:     */     }
/*  693: 785 */     return id;
/*  694:     */   }
/*  695:     */   
/*  696:     */   public Object readKey(ResultSet rs, String[] aliases, SessionImplementor session)
/*  697:     */     throws HibernateException, SQLException
/*  698:     */   {
/*  699: 790 */     return getKeyType().nullSafeGet(rs, aliases, session, null);
/*  700:     */   }
/*  701:     */   
/*  702:     */   protected int writeKey(PreparedStatement st, Serializable key, int i, SessionImplementor session)
/*  703:     */     throws HibernateException, SQLException
/*  704:     */   {
/*  705: 799 */     if (key == null) {
/*  706: 800 */       throw new NullPointerException("null key for collection: " + this.role);
/*  707:     */     }
/*  708: 802 */     getKeyType().nullSafeSet(st, key, i, session);
/*  709: 803 */     return i + this.keyColumnAliases.length;
/*  710:     */   }
/*  711:     */   
/*  712:     */   protected int writeElement(PreparedStatement st, Object elt, int i, SessionImplementor session)
/*  713:     */     throws HibernateException, SQLException
/*  714:     */   {
/*  715: 811 */     getElementType().nullSafeSet(st, elt, i, this.elementColumnIsSettable, session);
/*  716: 812 */     return i + ArrayHelper.countTrue(this.elementColumnIsSettable);
/*  717:     */   }
/*  718:     */   
/*  719:     */   protected int writeIndex(PreparedStatement st, Object index, int i, SessionImplementor session)
/*  720:     */     throws HibernateException, SQLException
/*  721:     */   {
/*  722: 821 */     getIndexType().nullSafeSet(st, incrementIndexByBase(index), i, this.indexColumnIsSettable, session);
/*  723: 822 */     return i + ArrayHelper.countTrue(this.indexColumnIsSettable);
/*  724:     */   }
/*  725:     */   
/*  726:     */   protected Object incrementIndexByBase(Object index)
/*  727:     */   {
/*  728: 826 */     if (this.baseIndex != 0) {
/*  729: 827 */       index = Integer.valueOf(((Integer)index).intValue() + this.baseIndex);
/*  730:     */     }
/*  731: 829 */     return index;
/*  732:     */   }
/*  733:     */   
/*  734:     */   protected int writeElementToWhere(PreparedStatement st, Object elt, int i, SessionImplementor session)
/*  735:     */     throws HibernateException, SQLException
/*  736:     */   {
/*  737: 837 */     if (this.elementIsPureFormula) {
/*  738: 838 */       throw new AssertionFailure("cannot use a formula-based element in the where condition");
/*  739:     */     }
/*  740: 840 */     getElementType().nullSafeSet(st, elt, i, this.elementColumnIsInPrimaryKey, session);
/*  741: 841 */     return i + this.elementColumnAliases.length;
/*  742:     */   }
/*  743:     */   
/*  744:     */   protected int writeIndexToWhere(PreparedStatement st, Object index, int i, SessionImplementor session)
/*  745:     */     throws HibernateException, SQLException
/*  746:     */   {
/*  747: 850 */     if (this.indexContainsFormula) {
/*  748: 851 */       throw new AssertionFailure("cannot use a formula-based index in the where condition");
/*  749:     */     }
/*  750: 853 */     getIndexType().nullSafeSet(st, incrementIndexByBase(index), i, session);
/*  751: 854 */     return i + this.indexColumnAliases.length;
/*  752:     */   }
/*  753:     */   
/*  754:     */   public int writeIdentifier(PreparedStatement st, Object id, int i, SessionImplementor session)
/*  755:     */     throws HibernateException, SQLException
/*  756:     */   {
/*  757: 863 */     getIdentifierType().nullSafeSet(st, id, i, session);
/*  758: 864 */     return i + 1;
/*  759:     */   }
/*  760:     */   
/*  761:     */   public boolean isPrimitiveArray()
/*  762:     */   {
/*  763: 868 */     return this.isPrimitiveArray;
/*  764:     */   }
/*  765:     */   
/*  766:     */   public boolean isArray()
/*  767:     */   {
/*  768: 872 */     return this.isArray;
/*  769:     */   }
/*  770:     */   
/*  771:     */   public String[] getKeyColumnAliases(String suffix)
/*  772:     */   {
/*  773: 876 */     return new Alias(suffix).toAliasStrings(this.keyColumnAliases);
/*  774:     */   }
/*  775:     */   
/*  776:     */   public String[] getElementColumnAliases(String suffix)
/*  777:     */   {
/*  778: 880 */     return new Alias(suffix).toAliasStrings(this.elementColumnAliases);
/*  779:     */   }
/*  780:     */   
/*  781:     */   public String[] getIndexColumnAliases(String suffix)
/*  782:     */   {
/*  783: 884 */     if (this.hasIndex) {
/*  784: 885 */       return new Alias(suffix).toAliasStrings(this.indexColumnAliases);
/*  785:     */     }
/*  786: 888 */     return null;
/*  787:     */   }
/*  788:     */   
/*  789:     */   public String getIdentifierColumnAlias(String suffix)
/*  790:     */   {
/*  791: 893 */     if (this.hasIdentifier) {
/*  792: 894 */       return new Alias(suffix).toAliasString(this.identifierColumnAlias);
/*  793:     */     }
/*  794: 897 */     return null;
/*  795:     */   }
/*  796:     */   
/*  797:     */   public String getIdentifierColumnName()
/*  798:     */   {
/*  799: 902 */     if (this.hasIdentifier) {
/*  800: 903 */       return this.identifierColumnName;
/*  801:     */     }
/*  802: 906 */     return null;
/*  803:     */   }
/*  804:     */   
/*  805:     */   public String selectFragment(String alias, String columnSuffix)
/*  806:     */   {
/*  807: 914 */     SelectFragment frag = generateSelectFragment(alias, columnSuffix);
/*  808: 915 */     appendElementColumns(frag, alias);
/*  809: 916 */     appendIndexColumns(frag, alias);
/*  810: 917 */     appendIdentifierColumns(frag, alias);
/*  811:     */     
/*  812: 919 */     return frag.toFragmentString().substring(2);
/*  813:     */   }
/*  814:     */   
/*  815:     */   protected String generateSelectSizeString(boolean isIntegerIndexed)
/*  816:     */   {
/*  817: 924 */     String selectValue = "count(" + getElementColumnNames()[0] + ")";
/*  818:     */     
/*  819:     */ 
/*  820: 927 */     return new SimpleSelect(this.dialect).setTableName(getTableName()).addCondition(getKeyColumnNames(), "=?").addColumn(selectValue).toStatementString();
/*  821:     */   }
/*  822:     */   
/*  823:     */   protected String generateDetectRowByIndexString()
/*  824:     */   {
/*  825: 935 */     if (!hasIndex()) {
/*  826: 936 */       return null;
/*  827:     */     }
/*  828: 938 */     return new SimpleSelect(this.dialect).setTableName(getTableName()).addCondition(getKeyColumnNames(), "=?").addCondition(getIndexColumnNames(), "=?").addCondition(this.indexFormulas, "=?").addColumn("1").toStatementString();
/*  829:     */   }
/*  830:     */   
/*  831:     */   protected String generateSelectRowByIndexString()
/*  832:     */   {
/*  833: 948 */     if (!hasIndex()) {
/*  834: 949 */       return null;
/*  835:     */     }
/*  836: 951 */     return new SimpleSelect(this.dialect).setTableName(getTableName()).addCondition(getKeyColumnNames(), "=?").addCondition(getIndexColumnNames(), "=?").addCondition(this.indexFormulas, "=?").addColumns(getElementColumnNames(), this.elementColumnAliases).addColumns(this.indexFormulas, this.indexColumnAliases).toStatementString();
/*  837:     */   }
/*  838:     */   
/*  839:     */   protected String generateDetectRowByElementString()
/*  840:     */   {
/*  841: 962 */     return new SimpleSelect(this.dialect).setTableName(getTableName()).addCondition(getKeyColumnNames(), "=?").addCondition(getElementColumnNames(), "=?").addCondition(this.elementFormulas, "=?").addColumn("1").toStatementString();
/*  842:     */   }
/*  843:     */   
/*  844:     */   protected SelectFragment generateSelectFragment(String alias, String columnSuffix)
/*  845:     */   {
/*  846: 972 */     return new SelectFragment().setSuffix(columnSuffix).addColumns(alias, this.keyColumnNames, this.keyColumnAliases);
/*  847:     */   }
/*  848:     */   
/*  849:     */   protected void appendElementColumns(SelectFragment frag, String elemAlias)
/*  850:     */   {
/*  851: 978 */     for (int i = 0; i < this.elementColumnIsSettable.length; i++) {
/*  852: 979 */       if (this.elementColumnIsSettable[i] != 0) {
/*  853: 980 */         frag.addColumnTemplate(elemAlias, this.elementColumnReaderTemplates[i], this.elementColumnAliases[i]);
/*  854:     */       } else {
/*  855: 983 */         frag.addFormula(elemAlias, this.elementFormulaTemplates[i], this.elementColumnAliases[i]);
/*  856:     */       }
/*  857:     */     }
/*  858:     */   }
/*  859:     */   
/*  860:     */   protected void appendIndexColumns(SelectFragment frag, String alias)
/*  861:     */   {
/*  862: 989 */     if (this.hasIndex) {
/*  863: 990 */       for (int i = 0; i < this.indexColumnIsSettable.length; i++) {
/*  864: 991 */         if (this.indexColumnIsSettable[i] != 0) {
/*  865: 992 */           frag.addColumn(alias, this.indexColumnNames[i], this.indexColumnAliases[i]);
/*  866:     */         } else {
/*  867: 995 */           frag.addFormula(alias, this.indexFormulaTemplates[i], this.indexColumnAliases[i]);
/*  868:     */         }
/*  869:     */       }
/*  870:     */     }
/*  871:     */   }
/*  872:     */   
/*  873:     */   protected void appendIdentifierColumns(SelectFragment frag, String alias)
/*  874:     */   {
/*  875:1002 */     if (this.hasIdentifier) {
/*  876:1003 */       frag.addColumn(alias, this.identifierColumnName, this.identifierColumnAlias);
/*  877:     */     }
/*  878:     */   }
/*  879:     */   
/*  880:     */   public String[] getIndexColumnNames()
/*  881:     */   {
/*  882:1008 */     return this.indexColumnNames;
/*  883:     */   }
/*  884:     */   
/*  885:     */   public String[] getIndexFormulas()
/*  886:     */   {
/*  887:1012 */     return this.indexFormulas;
/*  888:     */   }
/*  889:     */   
/*  890:     */   public String[] getIndexColumnNames(String alias)
/*  891:     */   {
/*  892:1016 */     return qualify(alias, this.indexColumnNames, this.indexFormulaTemplates);
/*  893:     */   }
/*  894:     */   
/*  895:     */   public String[] getElementColumnNames(String alias)
/*  896:     */   {
/*  897:1021 */     return qualify(alias, this.elementColumnNames, this.elementFormulaTemplates);
/*  898:     */   }
/*  899:     */   
/*  900:     */   private static String[] qualify(String alias, String[] columnNames, String[] formulaTemplates)
/*  901:     */   {
/*  902:1025 */     int span = columnNames.length;
/*  903:1026 */     String[] result = new String[span];
/*  904:1027 */     for (int i = 0; i < span; i++) {
/*  905:1028 */       if (columnNames[i] == null) {
/*  906:1029 */         result[i] = StringHelper.replace(formulaTemplates[i], "$PlaceHolder$", alias);
/*  907:     */       } else {
/*  908:1032 */         result[i] = StringHelper.qualify(alias, columnNames[i]);
/*  909:     */       }
/*  910:     */     }
/*  911:1035 */     return result;
/*  912:     */   }
/*  913:     */   
/*  914:     */   public String[] getElementColumnNames()
/*  915:     */   {
/*  916:1039 */     return this.elementColumnNames;
/*  917:     */   }
/*  918:     */   
/*  919:     */   public String[] getKeyColumnNames()
/*  920:     */   {
/*  921:1043 */     return this.keyColumnNames;
/*  922:     */   }
/*  923:     */   
/*  924:     */   public boolean hasIndex()
/*  925:     */   {
/*  926:1047 */     return this.hasIndex;
/*  927:     */   }
/*  928:     */   
/*  929:     */   public boolean isLazy()
/*  930:     */   {
/*  931:1051 */     return this.isLazy;
/*  932:     */   }
/*  933:     */   
/*  934:     */   public boolean isInverse()
/*  935:     */   {
/*  936:1055 */     return this.isInverse;
/*  937:     */   }
/*  938:     */   
/*  939:     */   public String getTableName()
/*  940:     */   {
/*  941:1059 */     return this.qualifiedTableName;
/*  942:     */   }
/*  943:     */   
/*  944:     */   public void remove(Serializable id, SessionImplementor session)
/*  945:     */     throws HibernateException
/*  946:     */   {
/*  947:1065 */     if ((!this.isInverse) && (isRowDeleteEnabled()))
/*  948:     */     {
/*  949:1067 */       if (LOG.isDebugEnabled()) {
/*  950:1068 */         LOG.debugf("Deleting collection: %s", MessageHelper.collectionInfoString(this, id, getFactory()));
/*  951:     */       }
/*  952:     */       try
/*  953:     */       {
/*  954:1075 */         int offset = 1;
/*  955:1076 */         PreparedStatement st = null;
/*  956:1077 */         Expectation expectation = Expectations.appropriateExpectation(getDeleteAllCheckStyle());
/*  957:1078 */         boolean callable = isDeleteAllCallable();
/*  958:1079 */         boolean useBatch = expectation.canBeBatched();
/*  959:1080 */         String sql = getSQLDeleteString();
/*  960:1081 */         if (useBatch)
/*  961:     */         {
/*  962:1082 */           if (this.removeBatchKey == null) {
/*  963:1083 */             this.removeBatchKey = new BasicBatchKey(getRole() + "#REMOVE", expectation);
/*  964:     */           }
/*  965:1088 */           st = session.getTransactionCoordinator().getJdbcCoordinator().getBatch(this.removeBatchKey).getBatchStatement(sql, callable);
/*  966:     */         }
/*  967:     */         else
/*  968:     */         {
/*  969:1094 */           st = session.getTransactionCoordinator().getJdbcCoordinator().getStatementPreparer().prepareStatement(sql, callable);
/*  970:     */         }
/*  971:     */         try
/*  972:     */         {
/*  973:1101 */           offset += expectation.prepare(st);
/*  974:     */           
/*  975:1103 */           writeKey(st, id, offset, session);
/*  976:1104 */           if (useBatch) {
/*  977:1105 */             session.getTransactionCoordinator().getJdbcCoordinator().getBatch(this.removeBatchKey).addToBatch();
/*  978:     */           } else {
/*  979:1111 */             expectation.verifyOutcome(st.executeUpdate(), st, -1);
/*  980:     */           }
/*  981:     */         }
/*  982:     */         catch (SQLException sqle)
/*  983:     */         {
/*  984:1115 */           if (useBatch) {
/*  985:1116 */             session.getTransactionCoordinator().getJdbcCoordinator().abortBatch();
/*  986:     */           }
/*  987:1118 */           throw sqle;
/*  988:     */         }
/*  989:     */         finally
/*  990:     */         {
/*  991:1121 */           if (!useBatch) {
/*  992:1122 */             st.close();
/*  993:     */           }
/*  994:     */         }
/*  995:1126 */         LOG.debug("Done deleting collection");
/*  996:     */       }
/*  997:     */       catch (SQLException sqle)
/*  998:     */       {
/*  999:1129 */         throw this.sqlExceptionHelper.convert(sqle, "could not delete collection: " + MessageHelper.collectionInfoString(this, id, getFactory()), getSQLDeleteString());
/* 1000:     */       }
/* 1001:     */     }
/* 1002:     */   }
/* 1003:     */   
/* 1004:     */   public void recreate(PersistentCollection collection, Serializable id, SessionImplementor session)
/* 1005:     */     throws HibernateException
/* 1006:     */   {
/* 1007:1146 */     if ((!this.isInverse) && (isRowInsertEnabled()))
/* 1008:     */     {
/* 1009:1148 */       if (LOG.isDebugEnabled()) {
/* 1010:1149 */         LOG.debugf("Inserting collection: %s", MessageHelper.collectionInfoString(this, id, getFactory()));
/* 1011:     */       }
/* 1012:     */       try
/* 1013:     */       {
/* 1014:1155 */         Iterator entries = collection.entries(this);
/* 1015:1156 */         if (entries.hasNext())
/* 1016:     */         {
/* 1017:1157 */           Expectation expectation = Expectations.appropriateExpectation(getInsertCheckStyle());
/* 1018:1158 */           collection.preInsert(this);
/* 1019:1159 */           int i = 0;
/* 1020:1160 */           int count = 0;
/* 1021:1161 */           while (entries.hasNext())
/* 1022:     */           {
/* 1023:1163 */             Object entry = entries.next();
/* 1024:1164 */             if (collection.entryExists(entry, i))
/* 1025:     */             {
/* 1026:1165 */               int offset = 1;
/* 1027:1166 */               PreparedStatement st = null;
/* 1028:1167 */               boolean callable = isInsertCallable();
/* 1029:1168 */               boolean useBatch = expectation.canBeBatched();
/* 1030:1169 */               String sql = getSQLInsertRowString();
/* 1031:1171 */               if (useBatch)
/* 1032:     */               {
/* 1033:1172 */                 if (this.recreateBatchKey == null) {
/* 1034:1173 */                   this.recreateBatchKey = new BasicBatchKey(getRole() + "#RECREATE", expectation);
/* 1035:     */                 }
/* 1036:1178 */                 st = session.getTransactionCoordinator().getJdbcCoordinator().getBatch(this.recreateBatchKey).getBatchStatement(sql, callable);
/* 1037:     */               }
/* 1038:     */               else
/* 1039:     */               {
/* 1040:1184 */                 st = session.getTransactionCoordinator().getJdbcCoordinator().getStatementPreparer().prepareStatement(sql, callable);
/* 1041:     */               }
/* 1042:     */               try
/* 1043:     */               {
/* 1044:1191 */                 offset += expectation.prepare(st);
/* 1045:     */                 
/* 1046:     */ 
/* 1047:1194 */                 int loc = writeKey(st, id, offset, session);
/* 1048:1195 */                 if (this.hasIdentifier) {
/* 1049:1196 */                   loc = writeIdentifier(st, collection.getIdentifier(entry, i), loc, session);
/* 1050:     */                 }
/* 1051:1198 */                 if (this.hasIndex) {
/* 1052:1199 */                   loc = writeIndex(st, collection.getIndex(entry, i, this), loc, session);
/* 1053:     */                 }
/* 1054:1201 */                 loc = writeElement(st, collection.getElement(entry), loc, session);
/* 1055:1203 */                 if (useBatch) {
/* 1056:1204 */                   session.getTransactionCoordinator().getJdbcCoordinator().getBatch(this.recreateBatchKey).addToBatch();
/* 1057:     */                 } else {
/* 1058:1210 */                   expectation.verifyOutcome(st.executeUpdate(), st, -1);
/* 1059:     */                 }
/* 1060:1213 */                 collection.afterRowInsert(this, entry, i);
/* 1061:1214 */                 count++;
/* 1062:     */               }
/* 1063:     */               catch (SQLException sqle)
/* 1064:     */               {
/* 1065:1217 */                 if (useBatch) {
/* 1066:1218 */                   session.getTransactionCoordinator().getJdbcCoordinator().abortBatch();
/* 1067:     */                 }
/* 1068:1220 */                 throw sqle;
/* 1069:     */               }
/* 1070:     */               finally
/* 1071:     */               {
/* 1072:1223 */                 if (!useBatch) {
/* 1073:1224 */                   st.close();
/* 1074:     */                 }
/* 1075:     */               }
/* 1076:     */             }
/* 1077:1229 */             i++;
/* 1078:     */           }
/* 1079:1232 */           LOG.debugf("Done inserting collection: %s rows inserted", Integer.valueOf(count));
/* 1080:     */         }
/* 1081:     */         else
/* 1082:     */         {
/* 1083:1236 */           LOG.debug("Collection was empty");
/* 1084:     */         }
/* 1085:     */       }
/* 1086:     */       catch (SQLException sqle)
/* 1087:     */       {
/* 1088:1240 */         throw this.sqlExceptionHelper.convert(sqle, "could not insert collection: " + MessageHelper.collectionInfoString(this, id, getFactory()), getSQLInsertRowString());
/* 1089:     */       }
/* 1090:     */     }
/* 1091:     */   }
/* 1092:     */   
/* 1093:     */   protected boolean isRowDeleteEnabled()
/* 1094:     */   {
/* 1095:1251 */     return true;
/* 1096:     */   }
/* 1097:     */   
/* 1098:     */   public void deleteRows(PersistentCollection collection, Serializable id, SessionImplementor session)
/* 1099:     */     throws HibernateException
/* 1100:     */   {
/* 1101:1259 */     if ((!this.isInverse) && (isRowDeleteEnabled()))
/* 1102:     */     {
/* 1103:1261 */       if (LOG.isDebugEnabled()) {
/* 1104:1262 */         LOG.debugf("Deleting rows of collection: %s", MessageHelper.collectionInfoString(this, id, getFactory()));
/* 1105:     */       }
/* 1106:1266 */       boolean deleteByIndex = (!isOneToMany()) && (this.hasIndex) && (!this.indexContainsFormula);
/* 1107:1267 */       Expectation expectation = Expectations.appropriateExpectation(getDeleteCheckStyle());
/* 1108:     */       try
/* 1109:     */       {
/* 1110:1270 */         Iterator deletes = collection.getDeletes(this, !deleteByIndex);
/* 1111:1271 */         if (deletes.hasNext())
/* 1112:     */         {
/* 1113:1272 */           int offset = 1;
/* 1114:1273 */           int count = 0;
/* 1115:1274 */           while (deletes.hasNext())
/* 1116:     */           {
/* 1117:1275 */             PreparedStatement st = null;
/* 1118:1276 */             boolean callable = isDeleteCallable();
/* 1119:1277 */             boolean useBatch = expectation.canBeBatched();
/* 1120:1278 */             String sql = getSQLDeleteRowString();
/* 1121:1280 */             if (useBatch)
/* 1122:     */             {
/* 1123:1281 */               if (this.deleteBatchKey == null) {
/* 1124:1282 */                 this.deleteBatchKey = new BasicBatchKey(getRole() + "#DELETE", expectation);
/* 1125:     */               }
/* 1126:1287 */               st = session.getTransactionCoordinator().getJdbcCoordinator().getBatch(this.deleteBatchKey).getBatchStatement(sql, callable);
/* 1127:     */             }
/* 1128:     */             else
/* 1129:     */             {
/* 1130:1293 */               st = session.getTransactionCoordinator().getJdbcCoordinator().getStatementPreparer().prepareStatement(sql, callable);
/* 1131:     */             }
/* 1132:     */             try
/* 1133:     */             {
/* 1134:1300 */               expectation.prepare(st);
/* 1135:     */               
/* 1136:1302 */               Object entry = deletes.next();
/* 1137:1303 */               int loc = offset;
/* 1138:1304 */               if (this.hasIdentifier)
/* 1139:     */               {
/* 1140:1305 */                 writeIdentifier(st, entry, loc, session);
/* 1141:     */               }
/* 1142:     */               else
/* 1143:     */               {
/* 1144:1308 */                 loc = writeKey(st, id, loc, session);
/* 1145:1309 */                 if (deleteByIndex) {
/* 1146:1310 */                   writeIndexToWhere(st, entry, loc, session);
/* 1147:     */                 } else {
/* 1148:1313 */                   writeElementToWhere(st, entry, loc, session);
/* 1149:     */                 }
/* 1150:     */               }
/* 1151:1317 */               if (useBatch) {
/* 1152:1318 */                 session.getTransactionCoordinator().getJdbcCoordinator().getBatch(this.deleteBatchKey).addToBatch();
/* 1153:     */               } else {
/* 1154:1324 */                 expectation.verifyOutcome(st.executeUpdate(), st, -1);
/* 1155:     */               }
/* 1156:1326 */               count++;
/* 1157:     */             }
/* 1158:     */             catch (SQLException sqle)
/* 1159:     */             {
/* 1160:1329 */               if (useBatch) {
/* 1161:1330 */                 session.getTransactionCoordinator().getJdbcCoordinator().abortBatch();
/* 1162:     */               }
/* 1163:1332 */               throw sqle;
/* 1164:     */             }
/* 1165:     */             finally
/* 1166:     */             {
/* 1167:1335 */               if (!useBatch) {
/* 1168:1336 */                 st.close();
/* 1169:     */               }
/* 1170:     */             }
/* 1171:1340 */             LOG.debugf("Done deleting collection rows: %s deleted", Integer.valueOf(count));
/* 1172:     */           }
/* 1173:     */         }
/* 1174:     */         else
/* 1175:     */         {
/* 1176:1344 */           LOG.debug("No rows to delete");
/* 1177:     */         }
/* 1178:     */       }
/* 1179:     */       catch (SQLException sqle)
/* 1180:     */       {
/* 1181:1348 */         throw this.sqlExceptionHelper.convert(sqle, "could not delete collection rows: " + MessageHelper.collectionInfoString(this, id, getFactory()), getSQLDeleteRowString());
/* 1182:     */       }
/* 1183:     */     }
/* 1184:     */   }
/* 1185:     */   
/* 1186:     */   protected boolean isRowInsertEnabled()
/* 1187:     */   {
/* 1188:1359 */     return true;
/* 1189:     */   }
/* 1190:     */   
/* 1191:     */   public void insertRows(PersistentCollection collection, Serializable id, SessionImplementor session)
/* 1192:     */     throws HibernateException
/* 1193:     */   {
/* 1194:1367 */     if ((!this.isInverse) && (isRowInsertEnabled()))
/* 1195:     */     {
/* 1196:1369 */       if (LOG.isDebugEnabled()) {
/* 1197:1369 */         LOG.debugf("Inserting rows of collection: %s", MessageHelper.collectionInfoString(this, id, getFactory()));
/* 1198:     */       }
/* 1199:     */       try
/* 1200:     */       {
/* 1201:1374 */         collection.preInsert(this);
/* 1202:1375 */         Iterator entries = collection.entries(this);
/* 1203:1376 */         Expectation expectation = Expectations.appropriateExpectation(getInsertCheckStyle());
/* 1204:1377 */         boolean callable = isInsertCallable();
/* 1205:1378 */         boolean useBatch = expectation.canBeBatched();
/* 1206:1379 */         String sql = getSQLInsertRowString();
/* 1207:1380 */         int i = 0;
/* 1208:1381 */         int count = 0;
/* 1209:1382 */         while (entries.hasNext())
/* 1210:     */         {
/* 1211:1383 */           int offset = 1;
/* 1212:1384 */           Object entry = entries.next();
/* 1213:1385 */           PreparedStatement st = null;
/* 1214:1386 */           if (collection.needsInserting(entry, i, this.elementType))
/* 1215:     */           {
/* 1216:1388 */             if (useBatch)
/* 1217:     */             {
/* 1218:1389 */               if (this.insertBatchKey == null) {
/* 1219:1390 */                 this.insertBatchKey = new BasicBatchKey(getRole() + "#INSERT", expectation);
/* 1220:     */               }
/* 1221:1395 */               if (st == null) {
/* 1222:1396 */                 st = session.getTransactionCoordinator().getJdbcCoordinator().getBatch(this.insertBatchKey).getBatchStatement(sql, callable);
/* 1223:     */               }
/* 1224:     */             }
/* 1225:     */             else
/* 1226:     */             {
/* 1227:1403 */               st = session.getTransactionCoordinator().getJdbcCoordinator().getStatementPreparer().prepareStatement(sql, callable);
/* 1228:     */             }
/* 1229:     */             try
/* 1230:     */             {
/* 1231:1410 */               offset += expectation.prepare(st);
/* 1232:     */               
/* 1233:1412 */               offset = writeKey(st, id, offset, session);
/* 1234:1413 */               if (this.hasIdentifier) {
/* 1235:1414 */                 offset = writeIdentifier(st, collection.getIdentifier(entry, i), offset, session);
/* 1236:     */               }
/* 1237:1416 */               if (this.hasIndex) {
/* 1238:1417 */                 offset = writeIndex(st, collection.getIndex(entry, i, this), offset, session);
/* 1239:     */               }
/* 1240:1419 */               writeElement(st, collection.getElement(entry), offset, session);
/* 1241:1421 */               if (useBatch) {
/* 1242:1422 */                 session.getTransactionCoordinator().getJdbcCoordinator().getBatch(this.insertBatchKey).addToBatch();
/* 1243:     */               } else {
/* 1244:1425 */                 expectation.verifyOutcome(st.executeUpdate(), st, -1);
/* 1245:     */               }
/* 1246:1427 */               collection.afterRowInsert(this, entry, i);
/* 1247:1428 */               count++;
/* 1248:     */             }
/* 1249:     */             catch (SQLException sqle)
/* 1250:     */             {
/* 1251:1431 */               if (useBatch) {
/* 1252:1432 */                 session.getTransactionCoordinator().getJdbcCoordinator().abortBatch();
/* 1253:     */               }
/* 1254:1434 */               throw sqle;
/* 1255:     */             }
/* 1256:     */             finally
/* 1257:     */             {
/* 1258:1437 */               if (!useBatch) {
/* 1259:1438 */                 st.close();
/* 1260:     */               }
/* 1261:     */             }
/* 1262:     */           }
/* 1263:1442 */           i++;
/* 1264:     */         }
/* 1265:1444 */         LOG.debugf("Done inserting rows: %s inserted", Integer.valueOf(count));
/* 1266:     */       }
/* 1267:     */       catch (SQLException sqle)
/* 1268:     */       {
/* 1269:1447 */         throw this.sqlExceptionHelper.convert(sqle, "could not insert collection rows: " + MessageHelper.collectionInfoString(this, id, getFactory()), getSQLInsertRowString());
/* 1270:     */       }
/* 1271:     */     }
/* 1272:     */   }
/* 1273:     */   
/* 1274:     */   public String getRole()
/* 1275:     */   {
/* 1276:1459 */     return this.role;
/* 1277:     */   }
/* 1278:     */   
/* 1279:     */   public String getOwnerEntityName()
/* 1280:     */   {
/* 1281:1463 */     return this.entityName;
/* 1282:     */   }
/* 1283:     */   
/* 1284:     */   public EntityPersister getOwnerEntityPersister()
/* 1285:     */   {
/* 1286:1467 */     return this.ownerPersister;
/* 1287:     */   }
/* 1288:     */   
/* 1289:     */   public IdentifierGenerator getIdentifierGenerator()
/* 1290:     */   {
/* 1291:1471 */     return this.identifierGenerator;
/* 1292:     */   }
/* 1293:     */   
/* 1294:     */   public Type getIdentifierType()
/* 1295:     */   {
/* 1296:1475 */     return this.identifierType;
/* 1297:     */   }
/* 1298:     */   
/* 1299:     */   public boolean hasOrphanDelete()
/* 1300:     */   {
/* 1301:1479 */     return this.hasOrphanDelete;
/* 1302:     */   }
/* 1303:     */   
/* 1304:     */   public Type toType(String propertyName)
/* 1305:     */     throws QueryException
/* 1306:     */   {
/* 1307:1483 */     if ("index".equals(propertyName)) {
/* 1308:1484 */       return this.indexType;
/* 1309:     */     }
/* 1310:1486 */     return this.elementPropertyMapping.toType(propertyName);
/* 1311:     */   }
/* 1312:     */   
/* 1313:     */   public abstract boolean isManyToMany();
/* 1314:     */   
/* 1315:     */   public String getManyToManyFilterFragment(String alias, Map enabledFilters)
/* 1316:     */   {
/* 1317:1492 */     StringBuilder buffer = new StringBuilder();
/* 1318:1493 */     this.manyToManyFilterHelper.render(buffer, alias, enabledFilters);
/* 1319:1495 */     if (this.manyToManyWhereString != null) {
/* 1320:1496 */       buffer.append(" and ").append(StringHelper.replace(this.manyToManyWhereTemplate, "$PlaceHolder$", alias));
/* 1321:     */     }
/* 1322:1500 */     return buffer.toString();
/* 1323:     */   }
/* 1324:     */   
/* 1325:     */   public String[] toColumns(String alias, String propertyName)
/* 1326:     */     throws QueryException
/* 1327:     */   {
/* 1328:1507 */     if ("index".equals(propertyName)) {
/* 1329:1508 */       return qualify(alias, this.indexColumnNames, this.indexFormulaTemplates);
/* 1330:     */     }
/* 1331:1510 */     return this.elementPropertyMapping.toColumns(alias, propertyName);
/* 1332:     */   }
/* 1333:     */   
/* 1334:     */   public String[] toColumns(String propertyName)
/* 1335:     */     throws QueryException
/* 1336:     */   {
/* 1337:1519 */     if ("index".equals(propertyName))
/* 1338:     */     {
/* 1339:1520 */       if (this.indexFragments == null)
/* 1340:     */       {
/* 1341:1521 */         String[] tmp = new String[this.indexColumnNames.length];
/* 1342:1522 */         for (int i = 0; i < this.indexColumnNames.length; i++)
/* 1343:     */         {
/* 1344:1523 */           tmp[i] = (this.indexColumnNames[i] == null ? this.indexFormulas[i] : this.indexColumnNames[i]);
/* 1345:     */           
/* 1346:     */ 
/* 1347:1526 */           this.indexFragments = tmp;
/* 1348:     */         }
/* 1349:     */       }
/* 1350:1529 */       return this.indexFragments;
/* 1351:     */     }
/* 1352:1532 */     return this.elementPropertyMapping.toColumns(propertyName);
/* 1353:     */   }
/* 1354:     */   
/* 1355:     */   public Type getType()
/* 1356:     */   {
/* 1357:1536 */     return this.elementPropertyMapping.getType();
/* 1358:     */   }
/* 1359:     */   
/* 1360:     */   public String getName()
/* 1361:     */   {
/* 1362:1540 */     return getRole();
/* 1363:     */   }
/* 1364:     */   
/* 1365:     */   public EntityPersister getElementPersister()
/* 1366:     */   {
/* 1367:1544 */     if (this.elementPersister == null) {
/* 1368:1545 */       throw new AssertionFailure("not an association");
/* 1369:     */     }
/* 1370:1547 */     return this.elementPersister;
/* 1371:     */   }
/* 1372:     */   
/* 1373:     */   public boolean isCollection()
/* 1374:     */   {
/* 1375:1551 */     return true;
/* 1376:     */   }
/* 1377:     */   
/* 1378:     */   public Serializable[] getCollectionSpaces()
/* 1379:     */   {
/* 1380:1555 */     return this.spaces;
/* 1381:     */   }
/* 1382:     */   
/* 1383:     */   protected abstract String generateDeleteString();
/* 1384:     */   
/* 1385:     */   protected abstract String generateDeleteRowString();
/* 1386:     */   
/* 1387:     */   protected abstract String generateUpdateRowString();
/* 1388:     */   
/* 1389:     */   protected abstract String generateInsertRowString();
/* 1390:     */   
/* 1391:     */   public void updateRows(PersistentCollection collection, Serializable id, SessionImplementor session)
/* 1392:     */     throws HibernateException
/* 1393:     */   {
/* 1394:1569 */     if ((!this.isInverse) && (collection.isRowUpdatePossible()))
/* 1395:     */     {
/* 1396:1571 */       LOG.debugf("Updating rows of collection: %s#%s", this.role, id);
/* 1397:     */       
/* 1398:     */ 
/* 1399:1574 */       int count = doUpdateRows(id, collection, session);
/* 1400:     */       
/* 1401:1576 */       LOG.debugf("Done updating rows: %s updated", Integer.valueOf(count));
/* 1402:     */     }
/* 1403:     */   }
/* 1404:     */   
/* 1405:     */   protected abstract int doUpdateRows(Serializable paramSerializable, PersistentCollection paramPersistentCollection, SessionImplementor paramSessionImplementor)
/* 1406:     */     throws HibernateException;
/* 1407:     */   
/* 1408:     */   public CollectionMetadata getCollectionMetadata()
/* 1409:     */   {
/* 1410:1584 */     return this;
/* 1411:     */   }
/* 1412:     */   
/* 1413:     */   public SessionFactoryImplementor getFactory()
/* 1414:     */   {
/* 1415:1588 */     return this.factory;
/* 1416:     */   }
/* 1417:     */   
/* 1418:     */   protected String filterFragment(String alias)
/* 1419:     */     throws MappingException
/* 1420:     */   {
/* 1421:1592 */     return hasWhere() ? " and " + getSQLWhereString(alias) : "";
/* 1422:     */   }
/* 1423:     */   
/* 1424:     */   public String filterFragment(String alias, Map enabledFilters)
/* 1425:     */     throws MappingException
/* 1426:     */   {
/* 1427:1597 */     StringBuilder sessionFilterFragment = new StringBuilder();
/* 1428:1598 */     this.filterHelper.render(sessionFilterFragment, alias, enabledFilters);
/* 1429:     */     
/* 1430:1600 */     return filterFragment(alias);
/* 1431:     */   }
/* 1432:     */   
/* 1433:     */   public String oneToManyFilterFragment(String alias)
/* 1434:     */     throws MappingException
/* 1435:     */   {
/* 1436:1604 */     return "";
/* 1437:     */   }
/* 1438:     */   
/* 1439:     */   protected boolean isInsertCallable()
/* 1440:     */   {
/* 1441:1608 */     return this.insertCallable;
/* 1442:     */   }
/* 1443:     */   
/* 1444:     */   protected ExecuteUpdateResultCheckStyle getInsertCheckStyle()
/* 1445:     */   {
/* 1446:1612 */     return this.insertCheckStyle;
/* 1447:     */   }
/* 1448:     */   
/* 1449:     */   protected boolean isUpdateCallable()
/* 1450:     */   {
/* 1451:1616 */     return this.updateCallable;
/* 1452:     */   }
/* 1453:     */   
/* 1454:     */   protected ExecuteUpdateResultCheckStyle getUpdateCheckStyle()
/* 1455:     */   {
/* 1456:1620 */     return this.updateCheckStyle;
/* 1457:     */   }
/* 1458:     */   
/* 1459:     */   protected boolean isDeleteCallable()
/* 1460:     */   {
/* 1461:1624 */     return this.deleteCallable;
/* 1462:     */   }
/* 1463:     */   
/* 1464:     */   protected ExecuteUpdateResultCheckStyle getDeleteCheckStyle()
/* 1465:     */   {
/* 1466:1628 */     return this.deleteCheckStyle;
/* 1467:     */   }
/* 1468:     */   
/* 1469:     */   protected boolean isDeleteAllCallable()
/* 1470:     */   {
/* 1471:1632 */     return this.deleteAllCallable;
/* 1472:     */   }
/* 1473:     */   
/* 1474:     */   protected ExecuteUpdateResultCheckStyle getDeleteAllCheckStyle()
/* 1475:     */   {
/* 1476:1636 */     return this.deleteAllCheckStyle;
/* 1477:     */   }
/* 1478:     */   
/* 1479:     */   public String toString()
/* 1480:     */   {
/* 1481:1640 */     return StringHelper.unqualify(getClass().getName()) + '(' + this.role + ')';
/* 1482:     */   }
/* 1483:     */   
/* 1484:     */   public boolean isVersioned()
/* 1485:     */   {
/* 1486:1644 */     return (this.isVersioned) && (getOwnerEntityPersister().isVersioned());
/* 1487:     */   }
/* 1488:     */   
/* 1489:     */   public String getNodeName()
/* 1490:     */   {
/* 1491:1648 */     return this.nodeName;
/* 1492:     */   }
/* 1493:     */   
/* 1494:     */   public String getElementNodeName()
/* 1495:     */   {
/* 1496:1652 */     return this.elementNodeName;
/* 1497:     */   }
/* 1498:     */   
/* 1499:     */   public String getIndexNodeName()
/* 1500:     */   {
/* 1501:1656 */     return this.indexNodeName;
/* 1502:     */   }
/* 1503:     */   
/* 1504:     */   protected SQLExceptionConverter getSQLExceptionConverter()
/* 1505:     */   {
/* 1506:1661 */     return getSQLExceptionHelper().getSqlExceptionConverter();
/* 1507:     */   }
/* 1508:     */   
/* 1509:     */   protected SqlExceptionHelper getSQLExceptionHelper()
/* 1510:     */   {
/* 1511:1666 */     return this.sqlExceptionHelper;
/* 1512:     */   }
/* 1513:     */   
/* 1514:     */   public CacheEntryStructure getCacheEntryStructure()
/* 1515:     */   {
/* 1516:1670 */     return this.cacheEntryStructure;
/* 1517:     */   }
/* 1518:     */   
/* 1519:     */   public boolean isAffectedByEnabledFilters(SessionImplementor session)
/* 1520:     */   {
/* 1521:1674 */     return (this.filterHelper.isAffectedBy(session.getEnabledFilters())) || ((isManyToMany()) && (this.manyToManyFilterHelper.isAffectedBy(session.getEnabledFilters())));
/* 1522:     */   }
/* 1523:     */   
/* 1524:     */   public boolean isSubselectLoadable()
/* 1525:     */   {
/* 1526:1679 */     return this.subselectLoadable;
/* 1527:     */   }
/* 1528:     */   
/* 1529:     */   public boolean isMutable()
/* 1530:     */   {
/* 1531:1683 */     return this.isMutable;
/* 1532:     */   }
/* 1533:     */   
/* 1534:     */   public String[] getCollectionPropertyColumnAliases(String propertyName, String suffix)
/* 1535:     */   {
/* 1536:1687 */     String[] rawAliases = (String[])this.collectionPropertyColumnAliases.get(propertyName);
/* 1537:1689 */     if (rawAliases == null) {
/* 1538:1690 */       return null;
/* 1539:     */     }
/* 1540:1693 */     String[] result = new String[rawAliases.length];
/* 1541:1694 */     for (int i = 0; i < rawAliases.length; i++) {
/* 1542:1695 */       result[i] = new Alias(suffix).toUnquotedAliasString(rawAliases[i]);
/* 1543:     */     }
/* 1544:1697 */     return result;
/* 1545:     */   }
/* 1546:     */   
/* 1547:     */   public void initCollectionPropertyMap()
/* 1548:     */   {
/* 1549:1703 */     initCollectionPropertyMap("key", this.keyType, this.keyColumnAliases, this.keyColumnNames);
/* 1550:1704 */     initCollectionPropertyMap("element", this.elementType, this.elementColumnAliases, this.elementColumnNames);
/* 1551:1705 */     if (this.hasIndex) {
/* 1552:1706 */       initCollectionPropertyMap("index", this.indexType, this.indexColumnAliases, this.indexColumnNames);
/* 1553:     */     }
/* 1554:1708 */     if (this.hasIdentifier) {
/* 1555:1709 */       initCollectionPropertyMap("id", this.identifierType, new String[] { this.identifierColumnAlias }, new String[] { this.identifierColumnName });
/* 1556:     */     }
/* 1557:     */   }
/* 1558:     */   
/* 1559:     */   private void initCollectionPropertyMap(String aliasName, Type type, String[] columnAliases, String[] columnNames)
/* 1560:     */   {
/* 1561:1719 */     this.collectionPropertyColumnAliases.put(aliasName, columnAliases);
/* 1562:1720 */     this.collectionPropertyColumnNames.put(aliasName, columnNames);
/* 1563:1722 */     if (type.isComponentType())
/* 1564:     */     {
/* 1565:1723 */       CompositeType ct = (CompositeType)type;
/* 1566:1724 */       String[] propertyNames = ct.getPropertyNames();
/* 1567:1725 */       for (int i = 0; i < propertyNames.length; i++)
/* 1568:     */       {
/* 1569:1726 */         String name = propertyNames[i];
/* 1570:1727 */         this.collectionPropertyColumnAliases.put(aliasName + "." + name, columnAliases[i]);
/* 1571:1728 */         this.collectionPropertyColumnNames.put(aliasName + "." + name, columnNames[i]);
/* 1572:     */       }
/* 1573:     */     }
/* 1574:     */   }
/* 1575:     */   
/* 1576:     */   /* Error */
/* 1577:     */   public int getSize(Serializable key, SessionImplementor session)
/* 1578:     */   {
/* 1579:     */     // Byte code:
/* 1580:     */     //   0: aload_2
/* 1581:     */     //   1: invokeinterface 345 1 0
/* 1582:     */     //   6: invokeinterface 346 1 0
/* 1583:     */     //   11: invokeinterface 349 1 0
/* 1584:     */     //   16: aload_0
/* 1585:     */     //   17: getfield 185	org/hibernate/persister/collection/AbstractCollectionPersister:sqlSelectSizeString	Ljava/lang/String;
/* 1586:     */     //   20: invokeinterface 436 2 0
/* 1587:     */     //   25: astore_3
/* 1588:     */     //   26: aload_0
/* 1589:     */     //   27: invokevirtual 285	org/hibernate/persister/collection/AbstractCollectionPersister:getKeyType	()Lorg/hibernate/type/Type;
/* 1590:     */     //   30: aload_3
/* 1591:     */     //   31: aload_1
/* 1592:     */     //   32: iconst_1
/* 1593:     */     //   33: aload_2
/* 1594:     */     //   34: invokeinterface 289 5 0
/* 1595:     */     //   39: aload_3
/* 1596:     */     //   40: invokeinterface 437 1 0
/* 1597:     */     //   45: astore 4
/* 1598:     */     //   47: aload 4
/* 1599:     */     //   49: invokeinterface 438 1 0
/* 1600:     */     //   54: ifeq +19 -> 73
/* 1601:     */     //   57: aload 4
/* 1602:     */     //   59: iconst_1
/* 1603:     */     //   60: invokeinterface 439 2 0
/* 1604:     */     //   65: aload_0
/* 1605:     */     //   66: getfield 137	org/hibernate/persister/collection/AbstractCollectionPersister:baseIndex	I
/* 1606:     */     //   69: isub
/* 1607:     */     //   70: goto +4 -> 74
/* 1608:     */     //   73: iconst_0
/* 1609:     */     //   74: istore 5
/* 1610:     */     //   76: aload 4
/* 1611:     */     //   78: invokeinterface 440 1 0
/* 1612:     */     //   83: aload_3
/* 1613:     */     //   84: invokeinterface 356 1 0
/* 1614:     */     //   89: iload 5
/* 1615:     */     //   91: ireturn
/* 1616:     */     //   92: astore 6
/* 1617:     */     //   94: aload 4
/* 1618:     */     //   96: invokeinterface 440 1 0
/* 1619:     */     //   101: aload 6
/* 1620:     */     //   103: athrow
/* 1621:     */     //   104: astore 7
/* 1622:     */     //   106: aload_3
/* 1623:     */     //   107: invokeinterface 356 1 0
/* 1624:     */     //   112: aload 7
/* 1625:     */     //   114: athrow
/* 1626:     */     //   115: astore_3
/* 1627:     */     //   116: aload_0
/* 1628:     */     //   117: invokevirtual 335	org/hibernate/persister/collection/AbstractCollectionPersister:getFactory	()Lorg/hibernate/engine/spi/SessionFactoryImplementor;
/* 1629:     */     //   120: invokeinterface 21 1 0
/* 1630:     */     //   125: aload_3
/* 1631:     */     //   126: new 61	java/lang/StringBuilder
/* 1632:     */     //   129: dup
/* 1633:     */     //   130: invokespecial 62	java/lang/StringBuilder:<init>	()V
/* 1634:     */     //   133: ldc_w 441
/* 1635:     */     //   136: invokevirtual 64	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 1636:     */     //   139: aload_0
/* 1637:     */     //   140: aload_1
/* 1638:     */     //   141: aload_0
/* 1639:     */     //   142: invokevirtual 335	org/hibernate/persister/collection/AbstractCollectionPersister:getFactory	()Lorg/hibernate/engine/spi/SessionFactoryImplementor;
/* 1640:     */     //   145: invokestatic 336	org/hibernate/pretty/MessageHelper:collectionInfoString	(Lorg/hibernate/persister/collection/CollectionPersister;Ljava/io/Serializable;Lorg/hibernate/engine/spi/SessionFactoryImplementor;)Ljava/lang/String;
/* 1641:     */     //   148: invokevirtual 64	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 1642:     */     //   151: invokevirtual 66	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 1643:     */     //   154: aload_0
/* 1644:     */     //   155: getfield 185	org/hibernate/persister/collection/AbstractCollectionPersister:sqlSelectSizeString	Ljava/lang/String;
/* 1645:     */     //   158: invokevirtual 362	org/hibernate/engine/jdbc/spi/SqlExceptionHelper:convert	(Ljava/sql/SQLException;Ljava/lang/String;Ljava/lang/String;)Lorg/hibernate/JDBCException;
/* 1646:     */     //   161: athrow
/* 1647:     */     // Line number table:
/* 1648:     */     //   Java source line #1736	-> byte code offset #0
/* 1649:     */     //   Java source line #1741	-> byte code offset #26
/* 1650:     */     //   Java source line #1742	-> byte code offset #39
/* 1651:     */     //   Java source line #1744	-> byte code offset #47
/* 1652:     */     //   Java source line #1747	-> byte code offset #76
/* 1653:     */     //   Java source line #1751	-> byte code offset #83
/* 1654:     */     //   Java source line #1747	-> byte code offset #92
/* 1655:     */     //   Java source line #1751	-> byte code offset #104
/* 1656:     */     //   Java source line #1754	-> byte code offset #115
/* 1657:     */     //   Java source line #1755	-> byte code offset #116
/* 1658:     */     // Local variable table:
/* 1659:     */     //   start	length	slot	name	signature
/* 1660:     */     //   0	162	0	this	AbstractCollectionPersister
/* 1661:     */     //   0	162	1	key	Serializable
/* 1662:     */     //   0	162	2	session	SessionImplementor
/* 1663:     */     //   25	82	3	st	PreparedStatement
/* 1664:     */     //   115	11	3	sqle	SQLException
/* 1665:     */     //   45	50	4	rs	ResultSet
/* 1666:     */     //   92	10	6	localObject1	Object
/* 1667:     */     //   104	9	7	localObject2	Object
/* 1668:     */     // Exception table:
/* 1669:     */     //   from	to	target	type
/* 1670:     */     //   47	76	92	finally
/* 1671:     */     //   92	94	92	finally
/* 1672:     */     //   26	83	104	finally
/* 1673:     */     //   92	106	104	finally
/* 1674:     */     //   0	89	115	java/sql/SQLException
/* 1675:     */     //   92	115	115	java/sql/SQLException
/* 1676:     */   }
/* 1677:     */   
/* 1678:     */   public boolean indexExists(Serializable key, Object index, SessionImplementor session)
/* 1679:     */   {
/* 1680:1765 */     return exists(key, incrementIndexByBase(index), getIndexType(), this.sqlDetectRowByIndexString, session);
/* 1681:     */   }
/* 1682:     */   
/* 1683:     */   public boolean elementExists(Serializable key, Object element, SessionImplementor session)
/* 1684:     */   {
/* 1685:1769 */     return exists(key, element, getElementType(), this.sqlDetectRowByElementString, session);
/* 1686:     */   }
/* 1687:     */   
/* 1688:     */   /* Error */
/* 1689:     */   private boolean exists(Serializable key, Object indexOrElement, Type indexOrElementType, String sql, SessionImplementor session)
/* 1690:     */   {
/* 1691:     */     // Byte code:
/* 1692:     */     //   0: aload 5
/* 1693:     */     //   2: invokeinterface 345 1 0
/* 1694:     */     //   7: invokeinterface 346 1 0
/* 1695:     */     //   12: invokeinterface 349 1 0
/* 1696:     */     //   17: aload 4
/* 1697:     */     //   19: invokeinterface 436 2 0
/* 1698:     */     //   24: astore 6
/* 1699:     */     //   26: aload_0
/* 1700:     */     //   27: invokevirtual 285	org/hibernate/persister/collection/AbstractCollectionPersister:getKeyType	()Lorg/hibernate/type/Type;
/* 1701:     */     //   30: aload 6
/* 1702:     */     //   32: aload_1
/* 1703:     */     //   33: iconst_1
/* 1704:     */     //   34: aload 5
/* 1705:     */     //   36: invokeinterface 289 5 0
/* 1706:     */     //   41: aload_3
/* 1707:     */     //   42: aload 6
/* 1708:     */     //   44: aload_2
/* 1709:     */     //   45: aload_0
/* 1710:     */     //   46: getfield 84	org/hibernate/persister/collection/AbstractCollectionPersister:keyColumnNames	[Ljava/lang/String;
/* 1711:     */     //   49: arraylength
/* 1712:     */     //   50: iconst_1
/* 1713:     */     //   51: iadd
/* 1714:     */     //   52: aload 5
/* 1715:     */     //   54: invokeinterface 289 5 0
/* 1716:     */     //   59: aload 6
/* 1717:     */     //   61: invokeinterface 437 1 0
/* 1718:     */     //   66: astore 7
/* 1719:     */     //   68: aload 7
/* 1720:     */     //   70: invokeinterface 438 1 0
/* 1721:     */     //   75: istore 8
/* 1722:     */     //   77: aload 7
/* 1723:     */     //   79: invokeinterface 440 1 0
/* 1724:     */     //   84: aload 6
/* 1725:     */     //   86: invokeinterface 356 1 0
/* 1726:     */     //   91: iload 8
/* 1727:     */     //   93: ireturn
/* 1728:     */     //   94: astore 9
/* 1729:     */     //   96: aload 7
/* 1730:     */     //   98: invokeinterface 440 1 0
/* 1731:     */     //   103: aload 9
/* 1732:     */     //   105: athrow
/* 1733:     */     //   106: astore 7
/* 1734:     */     //   108: iconst_0
/* 1735:     */     //   109: istore 8
/* 1736:     */     //   111: aload 6
/* 1737:     */     //   113: invokeinterface 356 1 0
/* 1738:     */     //   118: iload 8
/* 1739:     */     //   120: ireturn
/* 1740:     */     //   121: astore 10
/* 1741:     */     //   123: aload 6
/* 1742:     */     //   125: invokeinterface 356 1 0
/* 1743:     */     //   130: aload 10
/* 1744:     */     //   132: athrow
/* 1745:     */     //   133: astore 6
/* 1746:     */     //   135: aload_0
/* 1747:     */     //   136: invokevirtual 335	org/hibernate/persister/collection/AbstractCollectionPersister:getFactory	()Lorg/hibernate/engine/spi/SessionFactoryImplementor;
/* 1748:     */     //   139: invokeinterface 21 1 0
/* 1749:     */     //   144: aload 6
/* 1750:     */     //   146: new 61	java/lang/StringBuilder
/* 1751:     */     //   149: dup
/* 1752:     */     //   150: invokespecial 62	java/lang/StringBuilder:<init>	()V
/* 1753:     */     //   153: ldc_w 444
/* 1754:     */     //   156: invokevirtual 64	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 1755:     */     //   159: aload_0
/* 1756:     */     //   160: aload_1
/* 1757:     */     //   161: aload_0
/* 1758:     */     //   162: invokevirtual 335	org/hibernate/persister/collection/AbstractCollectionPersister:getFactory	()Lorg/hibernate/engine/spi/SessionFactoryImplementor;
/* 1759:     */     //   165: invokestatic 336	org/hibernate/pretty/MessageHelper:collectionInfoString	(Lorg/hibernate/persister/collection/CollectionPersister;Ljava/io/Serializable;Lorg/hibernate/engine/spi/SessionFactoryImplementor;)Ljava/lang/String;
/* 1760:     */     //   168: invokevirtual 64	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 1761:     */     //   171: invokevirtual 66	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 1762:     */     //   174: aload_0
/* 1763:     */     //   175: getfield 185	org/hibernate/persister/collection/AbstractCollectionPersister:sqlSelectSizeString	Ljava/lang/String;
/* 1764:     */     //   178: invokevirtual 362	org/hibernate/engine/jdbc/spi/SqlExceptionHelper:convert	(Ljava/sql/SQLException;Ljava/lang/String;Ljava/lang/String;)Lorg/hibernate/JDBCException;
/* 1765:     */     //   181: athrow
/* 1766:     */     // Line number table:
/* 1767:     */     //   Java source line #1774	-> byte code offset #0
/* 1768:     */     //   Java source line #1779	-> byte code offset #26
/* 1769:     */     //   Java source line #1780	-> byte code offset #41
/* 1770:     */     //   Java source line #1781	-> byte code offset #59
/* 1771:     */     //   Java source line #1783	-> byte code offset #68
/* 1772:     */     //   Java source line #1786	-> byte code offset #77
/* 1773:     */     //   Java source line #1793	-> byte code offset #84
/* 1774:     */     //   Java source line #1786	-> byte code offset #94
/* 1775:     */     //   Java source line #1789	-> byte code offset #106
/* 1776:     */     //   Java source line #1790	-> byte code offset #108
/* 1777:     */     //   Java source line #1793	-> byte code offset #111
/* 1778:     */     //   Java source line #1796	-> byte code offset #133
/* 1779:     */     //   Java source line #1797	-> byte code offset #135
/* 1780:     */     // Local variable table:
/* 1781:     */     //   start	length	slot	name	signature
/* 1782:     */     //   0	182	0	this	AbstractCollectionPersister
/* 1783:     */     //   0	182	1	key	Serializable
/* 1784:     */     //   0	182	2	indexOrElement	Object
/* 1785:     */     //   0	182	3	indexOrElementType	Type
/* 1786:     */     //   0	182	4	sql	String
/* 1787:     */     //   0	182	5	session	SessionImplementor
/* 1788:     */     //   24	100	6	st	PreparedStatement
/* 1789:     */     //   133	12	6	sqle	SQLException
/* 1790:     */     //   66	31	7	rs	ResultSet
/* 1791:     */     //   106	3	7	e	org.hibernate.TransientObjectException
/* 1792:     */     //   75	44	8	bool	boolean
/* 1793:     */     //   94	10	9	localObject1	Object
/* 1794:     */     //   121	10	10	localObject2	Object
/* 1795:     */     // Exception table:
/* 1796:     */     //   from	to	target	type
/* 1797:     */     //   68	77	94	finally
/* 1798:     */     //   94	96	94	finally
/* 1799:     */     //   26	84	106	org/hibernate/TransientObjectException
/* 1800:     */     //   94	106	106	org/hibernate/TransientObjectException
/* 1801:     */     //   26	84	121	finally
/* 1802:     */     //   94	111	121	finally
/* 1803:     */     //   121	123	121	finally
/* 1804:     */     //   0	91	133	java/sql/SQLException
/* 1805:     */     //   94	118	133	java/sql/SQLException
/* 1806:     */     //   121	133	133	java/sql/SQLException
/* 1807:     */   }
/* 1808:     */   
/* 1809:     */   /* Error */
/* 1810:     */   public Object getElementByIndex(Serializable key, Object index, SessionImplementor session, Object owner)
/* 1811:     */   {
/* 1812:     */     // Byte code:
/* 1813:     */     //   0: aload_3
/* 1814:     */     //   1: invokeinterface 345 1 0
/* 1815:     */     //   6: invokeinterface 346 1 0
/* 1816:     */     //   11: invokeinterface 349 1 0
/* 1817:     */     //   16: aload_0
/* 1818:     */     //   17: getfield 191	org/hibernate/persister/collection/AbstractCollectionPersister:sqlSelectRowByIndexString	Ljava/lang/String;
/* 1819:     */     //   20: invokeinterface 436 2 0
/* 1820:     */     //   25: astore 5
/* 1821:     */     //   27: aload_0
/* 1822:     */     //   28: invokevirtual 285	org/hibernate/persister/collection/AbstractCollectionPersister:getKeyType	()Lorg/hibernate/type/Type;
/* 1823:     */     //   31: aload 5
/* 1824:     */     //   33: aload_1
/* 1825:     */     //   34: iconst_1
/* 1826:     */     //   35: aload_3
/* 1827:     */     //   36: invokeinterface 289 5 0
/* 1828:     */     //   41: aload_0
/* 1829:     */     //   42: invokevirtual 274	org/hibernate/persister/collection/AbstractCollectionPersister:getIndexType	()Lorg/hibernate/type/Type;
/* 1830:     */     //   45: aload 5
/* 1831:     */     //   47: aload_0
/* 1832:     */     //   48: aload_2
/* 1833:     */     //   49: invokevirtual 292	org/hibernate/persister/collection/AbstractCollectionPersister:incrementIndexByBase	(Ljava/lang/Object;)Ljava/lang/Object;
/* 1834:     */     //   52: aload_0
/* 1835:     */     //   53: getfield 84	org/hibernate/persister/collection/AbstractCollectionPersister:keyColumnNames	[Ljava/lang/String;
/* 1836:     */     //   56: arraylength
/* 1837:     */     //   57: iconst_1
/* 1838:     */     //   58: iadd
/* 1839:     */     //   59: aload_3
/* 1840:     */     //   60: invokeinterface 289 5 0
/* 1841:     */     //   65: aload 5
/* 1842:     */     //   67: invokeinterface 437 1 0
/* 1843:     */     //   72: astore 6
/* 1844:     */     //   74: aload 6
/* 1845:     */     //   76: invokeinterface 438 1 0
/* 1846:     */     //   81: ifeq +40 -> 121
/* 1847:     */     //   84: aload_0
/* 1848:     */     //   85: invokevirtual 272	org/hibernate/persister/collection/AbstractCollectionPersister:getElementType	()Lorg/hibernate/type/Type;
/* 1849:     */     //   88: aload 6
/* 1850:     */     //   90: aload_0
/* 1851:     */     //   91: getfield 101	org/hibernate/persister/collection/AbstractCollectionPersister:elementColumnAliases	[Ljava/lang/String;
/* 1852:     */     //   94: aload_3
/* 1853:     */     //   95: aload 4
/* 1854:     */     //   97: invokeinterface 273 5 0
/* 1855:     */     //   102: astore 7
/* 1856:     */     //   104: aload 6
/* 1857:     */     //   106: invokeinterface 440 1 0
/* 1858:     */     //   111: aload 5
/* 1859:     */     //   113: invokeinterface 356 1 0
/* 1860:     */     //   118: aload 7
/* 1861:     */     //   120: areturn
/* 1862:     */     //   121: aconst_null
/* 1863:     */     //   122: astore 7
/* 1864:     */     //   124: aload 6
/* 1865:     */     //   126: invokeinterface 440 1 0
/* 1866:     */     //   131: aload 5
/* 1867:     */     //   133: invokeinterface 356 1 0
/* 1868:     */     //   138: aload 7
/* 1869:     */     //   140: areturn
/* 1870:     */     //   141: astore 8
/* 1871:     */     //   143: aload 6
/* 1872:     */     //   145: invokeinterface 440 1 0
/* 1873:     */     //   150: aload 8
/* 1874:     */     //   152: athrow
/* 1875:     */     //   153: astore 9
/* 1876:     */     //   155: aload 5
/* 1877:     */     //   157: invokeinterface 356 1 0
/* 1878:     */     //   162: aload 9
/* 1879:     */     //   164: athrow
/* 1880:     */     //   165: astore 5
/* 1881:     */     //   167: aload_0
/* 1882:     */     //   168: invokevirtual 335	org/hibernate/persister/collection/AbstractCollectionPersister:getFactory	()Lorg/hibernate/engine/spi/SessionFactoryImplementor;
/* 1883:     */     //   171: invokeinterface 21 1 0
/* 1884:     */     //   176: aload 5
/* 1885:     */     //   178: new 61	java/lang/StringBuilder
/* 1886:     */     //   181: dup
/* 1887:     */     //   182: invokespecial 62	java/lang/StringBuilder:<init>	()V
/* 1888:     */     //   185: ldc_w 445
/* 1889:     */     //   188: invokevirtual 64	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 1890:     */     //   191: aload_0
/* 1891:     */     //   192: aload_1
/* 1892:     */     //   193: aload_0
/* 1893:     */     //   194: invokevirtual 335	org/hibernate/persister/collection/AbstractCollectionPersister:getFactory	()Lorg/hibernate/engine/spi/SessionFactoryImplementor;
/* 1894:     */     //   197: invokestatic 336	org/hibernate/pretty/MessageHelper:collectionInfoString	(Lorg/hibernate/persister/collection/CollectionPersister;Ljava/io/Serializable;Lorg/hibernate/engine/spi/SessionFactoryImplementor;)Ljava/lang/String;
/* 1895:     */     //   200: invokevirtual 64	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/* 1896:     */     //   203: invokevirtual 66	java/lang/StringBuilder:toString	()Ljava/lang/String;
/* 1897:     */     //   206: aload_0
/* 1898:     */     //   207: getfield 185	org/hibernate/persister/collection/AbstractCollectionPersister:sqlSelectSizeString	Ljava/lang/String;
/* 1899:     */     //   210: invokevirtual 362	org/hibernate/engine/jdbc/spi/SqlExceptionHelper:convert	(Ljava/sql/SQLException;Ljava/lang/String;Ljava/lang/String;)Lorg/hibernate/JDBCException;
/* 1900:     */     //   213: athrow
/* 1901:     */     // Line number table:
/* 1902:     */     //   Java source line #1808	-> byte code offset #0
/* 1903:     */     //   Java source line #1813	-> byte code offset #27
/* 1904:     */     //   Java source line #1814	-> byte code offset #41
/* 1905:     */     //   Java source line #1815	-> byte code offset #65
/* 1906:     */     //   Java source line #1817	-> byte code offset #74
/* 1907:     */     //   Java source line #1818	-> byte code offset #84
/* 1908:     */     //   Java source line #1825	-> byte code offset #104
/* 1909:     */     //   Java source line #1829	-> byte code offset #111
/* 1910:     */     //   Java source line #1821	-> byte code offset #121
/* 1911:     */     //   Java source line #1825	-> byte code offset #124
/* 1912:     */     //   Java source line #1829	-> byte code offset #131
/* 1913:     */     //   Java source line #1825	-> byte code offset #141
/* 1914:     */     //   Java source line #1829	-> byte code offset #153
/* 1915:     */     //   Java source line #1832	-> byte code offset #165
/* 1916:     */     //   Java source line #1833	-> byte code offset #167
/* 1917:     */     // Local variable table:
/* 1918:     */     //   start	length	slot	name	signature
/* 1919:     */     //   0	214	0	this	AbstractCollectionPersister
/* 1920:     */     //   0	214	1	key	Serializable
/* 1921:     */     //   0	214	2	index	Object
/* 1922:     */     //   0	214	3	session	SessionImplementor
/* 1923:     */     //   0	214	4	owner	Object
/* 1924:     */     //   25	131	5	st	PreparedStatement
/* 1925:     */     //   165	12	5	sqle	SQLException
/* 1926:     */     //   72	72	6	rs	ResultSet
/* 1927:     */     //   102	37	7	localObject1	Object
/* 1928:     */     //   141	10	8	localObject2	Object
/* 1929:     */     //   153	10	9	localObject3	Object
/* 1930:     */     // Exception table:
/* 1931:     */     //   from	to	target	type
/* 1932:     */     //   74	104	141	finally
/* 1933:     */     //   121	124	141	finally
/* 1934:     */     //   141	143	141	finally
/* 1935:     */     //   27	111	153	finally
/* 1936:     */     //   121	131	153	finally
/* 1937:     */     //   141	155	153	finally
/* 1938:     */     //   0	118	165	java/sql/SQLException
/* 1939:     */     //   121	138	165	java/sql/SQLException
/* 1940:     */     //   141	165	165	java/sql/SQLException
/* 1941:     */   }
/* 1942:     */   
/* 1943:     */   public boolean isExtraLazy()
/* 1944:     */   {
/* 1945:1843 */     return this.isExtraLazy;
/* 1946:     */   }
/* 1947:     */   
/* 1948:     */   protected Dialect getDialect()
/* 1949:     */   {
/* 1950:1847 */     return this.dialect;
/* 1951:     */   }
/* 1952:     */   
/* 1953:     */   public CollectionInitializer getInitializer()
/* 1954:     */   {
/* 1955:1857 */     return this.initializer;
/* 1956:     */   }
/* 1957:     */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.persister.collection.AbstractCollectionPersister
 * JD-Core Version:    0.7.0.1
 */