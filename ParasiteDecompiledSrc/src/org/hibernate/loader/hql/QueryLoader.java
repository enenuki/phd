/*   1:    */ package org.hibernate.loader.hql;
/*   2:    */ 
/*   3:    */ import java.sql.PreparedStatement;
/*   4:    */ import java.sql.ResultSet;
/*   5:    */ import java.sql.SQLException;
/*   6:    */ import java.util.Arrays;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import java.util.Map.Entry;
/*  12:    */ import java.util.Set;
/*  13:    */ import org.hibernate.HibernateException;
/*  14:    */ import org.hibernate.LockMode;
/*  15:    */ import org.hibernate.LockOptions;
/*  16:    */ import org.hibernate.QueryException;
/*  17:    */ import org.hibernate.ScrollableResults;
/*  18:    */ import org.hibernate.dialect.Dialect;
/*  19:    */ import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
/*  20:    */ import org.hibernate.engine.spi.QueryParameters;
/*  21:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  22:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  23:    */ import org.hibernate.event.spi.EventSource;
/*  24:    */ import org.hibernate.hql.internal.HolderInstantiator;
/*  25:    */ import org.hibernate.hql.internal.ast.QueryTranslatorImpl;
/*  26:    */ import org.hibernate.hql.internal.ast.tree.AggregatedSelectExpression;
/*  27:    */ import org.hibernate.hql.internal.ast.tree.FromClause;
/*  28:    */ import org.hibernate.hql.internal.ast.tree.FromElement;
/*  29:    */ import org.hibernate.hql.internal.ast.tree.QueryNode;
/*  30:    */ import org.hibernate.hql.internal.ast.tree.SelectClause;
/*  31:    */ import org.hibernate.hql.spi.ParameterTranslations;
/*  32:    */ import org.hibernate.internal.IteratorImpl;
/*  33:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*  34:    */ import org.hibernate.loader.BasicLoader;
/*  35:    */ import org.hibernate.param.ParameterSpecification;
/*  36:    */ import org.hibernate.persister.collection.CollectionPersister;
/*  37:    */ import org.hibernate.persister.collection.QueryableCollection;
/*  38:    */ import org.hibernate.persister.entity.Loadable;
/*  39:    */ import org.hibernate.persister.entity.Lockable;
/*  40:    */ import org.hibernate.persister.entity.Queryable;
/*  41:    */ import org.hibernate.stat.Statistics;
/*  42:    */ import org.hibernate.stat.spi.StatisticsImplementor;
/*  43:    */ import org.hibernate.transform.ResultTransformer;
/*  44:    */ import org.hibernate.type.EntityType;
/*  45:    */ import org.hibernate.type.Type;
/*  46:    */ 
/*  47:    */ public class QueryLoader
/*  48:    */   extends BasicLoader
/*  49:    */ {
/*  50:    */   private QueryTranslatorImpl queryTranslator;
/*  51:    */   private Queryable[] entityPersisters;
/*  52:    */   private String[] entityAliases;
/*  53:    */   private String[] sqlAliases;
/*  54:    */   private String[] sqlAliasSuffixes;
/*  55:    */   private boolean[] includeInSelect;
/*  56:    */   private String[] collectionSuffixes;
/*  57:    */   private boolean hasScalars;
/*  58:    */   private String[][] scalarColumnNames;
/*  59:    */   private Type[] queryReturnTypes;
/*  60: 89 */   private final Map sqlAliasByEntityAlias = new HashMap(8);
/*  61:    */   private EntityType[] ownerAssociationTypes;
/*  62:    */   private int[] owners;
/*  63:    */   private boolean[] entityEagerPropertyFetches;
/*  64:    */   private int[] collectionOwners;
/*  65:    */   private QueryableCollection[] collectionPersisters;
/*  66:    */   private int selectLength;
/*  67:    */   private AggregatedSelectExpression aggregatedSelectExpression;
/*  68:    */   private String[] queryReturnAliases;
/*  69:    */   private LockMode[] defaultLockModes;
/*  70:    */   
/*  71:    */   public QueryLoader(QueryTranslatorImpl queryTranslator, SessionFactoryImplementor factory, SelectClause selectClause)
/*  72:    */   {
/*  73:117 */     super(factory);
/*  74:118 */     this.queryTranslator = queryTranslator;
/*  75:119 */     initialize(selectClause);
/*  76:120 */     postInstantiate();
/*  77:    */   }
/*  78:    */   
/*  79:    */   private void initialize(SelectClause selectClause)
/*  80:    */   {
/*  81:125 */     List fromElementList = selectClause.getFromElementsForLoad();
/*  82:    */     
/*  83:127 */     this.hasScalars = selectClause.isScalarSelect();
/*  84:128 */     this.scalarColumnNames = selectClause.getColumnNames();
/*  85:    */     
/*  86:130 */     this.queryReturnTypes = selectClause.getQueryReturnTypes();
/*  87:    */     
/*  88:132 */     this.aggregatedSelectExpression = selectClause.getAggregatedSelectExpression();
/*  89:133 */     this.queryReturnAliases = selectClause.getQueryReturnAliases();
/*  90:    */     
/*  91:135 */     List collectionFromElements = selectClause.getCollectionFromElements();
/*  92:136 */     if ((collectionFromElements != null) && (collectionFromElements.size() != 0))
/*  93:    */     {
/*  94:137 */       int length = collectionFromElements.size();
/*  95:138 */       this.collectionPersisters = new QueryableCollection[length];
/*  96:139 */       this.collectionOwners = new int[length];
/*  97:140 */       this.collectionSuffixes = new String[length];
/*  98:141 */       for (int i = 0; i < length; i++)
/*  99:    */       {
/* 100:142 */         FromElement collectionFromElement = (FromElement)collectionFromElements.get(i);
/* 101:143 */         this.collectionPersisters[i] = collectionFromElement.getQueryableCollection();
/* 102:144 */         this.collectionOwners[i] = fromElementList.indexOf(collectionFromElement.getOrigin());
/* 103:    */         
/* 104:    */ 
/* 105:147 */         this.collectionSuffixes[i] = collectionFromElement.getCollectionSuffix();
/* 106:    */       }
/* 107:    */     }
/* 108:151 */     int size = fromElementList.size();
/* 109:152 */     this.entityPersisters = new Queryable[size];
/* 110:153 */     this.entityEagerPropertyFetches = new boolean[size];
/* 111:154 */     this.entityAliases = new String[size];
/* 112:155 */     this.sqlAliases = new String[size];
/* 113:156 */     this.sqlAliasSuffixes = new String[size];
/* 114:157 */     this.includeInSelect = new boolean[size];
/* 115:158 */     this.owners = new int[size];
/* 116:159 */     this.ownerAssociationTypes = new EntityType[size];
/* 117:161 */     for (int i = 0; i < size; i++)
/* 118:    */     {
/* 119:162 */       FromElement element = (FromElement)fromElementList.get(i);
/* 120:163 */       this.entityPersisters[i] = ((Queryable)element.getEntityPersister());
/* 121:165 */       if (this.entityPersisters[i] == null) {
/* 122:166 */         throw new IllegalStateException("No entity persister for " + element.toString());
/* 123:    */       }
/* 124:169 */       this.entityEagerPropertyFetches[i] = element.isAllPropertyFetch();
/* 125:170 */       this.sqlAliases[i] = element.getTableAlias();
/* 126:171 */       this.entityAliases[i] = element.getClassAlias();
/* 127:172 */       this.sqlAliasByEntityAlias.put(this.entityAliases[i], this.sqlAliases[i]);
/* 128:    */       
/* 129:174 */       this.sqlAliasSuffixes[i] = (Integer.toString(i) + "_");
/* 130:    */       
/* 131:176 */       this.includeInSelect[i] = (!element.isFetch() ? 1 : false);
/* 132:177 */       if (this.includeInSelect[i] != 0) {
/* 133:178 */         this.selectLength += 1;
/* 134:    */       }
/* 135:181 */       this.owners[i] = -1;
/* 136:182 */       if ((element.isFetch()) && 
/* 137:183 */         (!element.isCollectionJoin()) && (element.getQueryableCollection() == null)) {
/* 138:186 */         if (element.getDataType().isEntityType())
/* 139:    */         {
/* 140:187 */           EntityType entityType = (EntityType)element.getDataType();
/* 141:188 */           if (entityType.isOneToOne()) {
/* 142:189 */             this.owners[i] = fromElementList.indexOf(element.getOrigin());
/* 143:    */           }
/* 144:191 */           this.ownerAssociationTypes[i] = entityType;
/* 145:    */         }
/* 146:    */       }
/* 147:    */     }
/* 148:197 */     this.defaultLockModes = ArrayHelper.fillArray(LockMode.NONE, size);
/* 149:    */   }
/* 150:    */   
/* 151:    */   public AggregatedSelectExpression getAggregatedSelectExpression()
/* 152:    */   {
/* 153:201 */     return this.aggregatedSelectExpression;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public final void validateScrollability()
/* 157:    */     throws HibernateException
/* 158:    */   {
/* 159:208 */     this.queryTranslator.validateScrollability();
/* 160:    */   }
/* 161:    */   
/* 162:    */   protected boolean needsFetchingScroll()
/* 163:    */   {
/* 164:212 */     return this.queryTranslator.containsCollectionFetches();
/* 165:    */   }
/* 166:    */   
/* 167:    */   public Loadable[] getEntityPersisters()
/* 168:    */   {
/* 169:216 */     return this.entityPersisters;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public String[] getAliases()
/* 173:    */   {
/* 174:220 */     return this.sqlAliases;
/* 175:    */   }
/* 176:    */   
/* 177:    */   public String[] getSqlAliasSuffixes()
/* 178:    */   {
/* 179:224 */     return this.sqlAliasSuffixes;
/* 180:    */   }
/* 181:    */   
/* 182:    */   public String[] getSuffixes()
/* 183:    */   {
/* 184:228 */     return getSqlAliasSuffixes();
/* 185:    */   }
/* 186:    */   
/* 187:    */   public String[] getCollectionSuffixes()
/* 188:    */   {
/* 189:232 */     return this.collectionSuffixes;
/* 190:    */   }
/* 191:    */   
/* 192:    */   protected String getQueryIdentifier()
/* 193:    */   {
/* 194:236 */     return this.queryTranslator.getQueryIdentifier();
/* 195:    */   }
/* 196:    */   
/* 197:    */   protected String getSQLString()
/* 198:    */   {
/* 199:243 */     return this.queryTranslator.getSQLString();
/* 200:    */   }
/* 201:    */   
/* 202:    */   protected CollectionPersister[] getCollectionPersisters()
/* 203:    */   {
/* 204:251 */     return this.collectionPersisters;
/* 205:    */   }
/* 206:    */   
/* 207:    */   protected int[] getCollectionOwners()
/* 208:    */   {
/* 209:255 */     return this.collectionOwners;
/* 210:    */   }
/* 211:    */   
/* 212:    */   protected boolean[] getEntityEagerPropertyFetches()
/* 213:    */   {
/* 214:259 */     return this.entityEagerPropertyFetches;
/* 215:    */   }
/* 216:    */   
/* 217:    */   protected int[] getOwners()
/* 218:    */   {
/* 219:267 */     return this.owners;
/* 220:    */   }
/* 221:    */   
/* 222:    */   protected EntityType[] getOwnerAssociationTypes()
/* 223:    */   {
/* 224:271 */     return this.ownerAssociationTypes;
/* 225:    */   }
/* 226:    */   
/* 227:    */   protected boolean isSubselectLoadingEnabled()
/* 228:    */   {
/* 229:277 */     return hasSubselectLoadableCollections();
/* 230:    */   }
/* 231:    */   
/* 232:    */   protected LockMode[] getLockModes(LockOptions lockOptions)
/* 233:    */   {
/* 234:284 */     if (lockOptions == null) {
/* 235:285 */       return this.defaultLockModes;
/* 236:    */     }
/* 237:288 */     if ((lockOptions.getAliasLockCount() == 0) && ((lockOptions.getLockMode() == null) || (LockMode.NONE.equals(lockOptions.getLockMode())))) {
/* 238:290 */       return this.defaultLockModes;
/* 239:    */     }
/* 240:297 */     LockMode[] lockModesArray = new LockMode[this.entityAliases.length];
/* 241:298 */     for (int i = 0; i < this.entityAliases.length; i++)
/* 242:    */     {
/* 243:299 */       LockMode lockMode = lockOptions.getEffectiveLockMode(this.entityAliases[i]);
/* 244:300 */       if (lockMode == null) {
/* 245:302 */         lockMode = LockMode.NONE;
/* 246:    */       }
/* 247:304 */       lockModesArray[i] = lockMode;
/* 248:    */     }
/* 249:307 */     return lockModesArray;
/* 250:    */   }
/* 251:    */   
/* 252:    */   protected String applyLocks(String sql, LockOptions lockOptions, Dialect dialect)
/* 253:    */     throws QueryException
/* 254:    */   {
/* 255:315 */     if ((lockOptions == null) || ((lockOptions.getLockMode() == LockMode.NONE) && (lockOptions.getAliasLockCount() == 0))) {
/* 256:317 */       return sql;
/* 257:    */     }
/* 258:322 */     LockOptions locks = new LockOptions(lockOptions.getLockMode());
/* 259:323 */     Map keyColumnNames = dialect.forUpdateOfColumns() ? new HashMap() : null;
/* 260:    */     
/* 261:325 */     locks.setScope(lockOptions.getScope());
/* 262:326 */     locks.setTimeOut(lockOptions.getTimeOut());
/* 263:    */     
/* 264:328 */     Iterator itr = this.sqlAliasByEntityAlias.entrySet().iterator();
/* 265:329 */     while (itr.hasNext())
/* 266:    */     {
/* 267:330 */       Map.Entry entry = (Map.Entry)itr.next();
/* 268:331 */       String userAlias = (String)entry.getKey();
/* 269:332 */       String drivingSqlAlias = (String)entry.getValue();
/* 270:333 */       if (drivingSqlAlias == null) {
/* 271:334 */         throw new IllegalArgumentException("could not locate alias to apply lock mode : " + userAlias);
/* 272:    */       }
/* 273:342 */       QueryNode select = (QueryNode)this.queryTranslator.getSqlAST();
/* 274:343 */       Lockable drivingPersister = (Lockable)select.getFromClause().findFromElementByUserOrSqlAlias(userAlias, drivingSqlAlias).getQueryable();
/* 275:    */       
/* 276:    */ 
/* 277:346 */       String sqlAlias = drivingPersister.getRootTableAlias(drivingSqlAlias);
/* 278:    */       
/* 279:348 */       LockMode effectiveLockMode = lockOptions.getEffectiveLockMode(userAlias);
/* 280:349 */       locks.setAliasSpecificLockMode(sqlAlias, effectiveLockMode);
/* 281:351 */       if (keyColumnNames != null) {
/* 282:352 */         keyColumnNames.put(sqlAlias, drivingPersister.getRootTableIdentifierColumnNames());
/* 283:    */       }
/* 284:    */     }
/* 285:357 */     return dialect.applyLocksToSql(sql, locks, keyColumnNames);
/* 286:    */   }
/* 287:    */   
/* 288:    */   protected void applyPostLoadLocks(Object[] row, LockMode[] lockModesArray, SessionImplementor session) {}
/* 289:    */   
/* 290:    */   protected boolean upgradeLocks()
/* 291:    */   {
/* 292:377 */     return true;
/* 293:    */   }
/* 294:    */   
/* 295:    */   private boolean hasSelectNew()
/* 296:    */   {
/* 297:381 */     return (this.aggregatedSelectExpression != null) && (this.aggregatedSelectExpression.getResultTransformer() != null);
/* 298:    */   }
/* 299:    */   
/* 300:    */   protected String[] getResultRowAliases()
/* 301:    */   {
/* 302:385 */     return this.queryReturnAliases;
/* 303:    */   }
/* 304:    */   
/* 305:    */   protected ResultTransformer resolveResultTransformer(ResultTransformer resultTransformer)
/* 306:    */   {
/* 307:389 */     ResultTransformer implicitResultTransformer = this.aggregatedSelectExpression == null ? null : this.aggregatedSelectExpression.getResultTransformer();
/* 308:    */     
/* 309:    */ 
/* 310:392 */     return HolderInstantiator.resolveResultTransformer(implicitResultTransformer, resultTransformer);
/* 311:    */   }
/* 312:    */   
/* 313:    */   protected boolean[] includeInResultRow()
/* 314:    */   {
/* 315:396 */     boolean[] includeInResultTuple = this.includeInSelect;
/* 316:397 */     if (this.hasScalars)
/* 317:    */     {
/* 318:398 */       includeInResultTuple = new boolean[this.queryReturnTypes.length];
/* 319:399 */       Arrays.fill(includeInResultTuple, true);
/* 320:    */     }
/* 321:401 */     return includeInResultTuple;
/* 322:    */   }
/* 323:    */   
/* 324:    */   protected Object getResultColumnOrRow(Object[] row, ResultTransformer transformer, ResultSet rs, SessionImplementor session)
/* 325:    */     throws SQLException, HibernateException
/* 326:    */   {
/* 327:407 */     Object[] resultRow = getResultRow(row, rs, session);
/* 328:408 */     boolean hasTransform = (hasSelectNew()) || (transformer != null);
/* 329:409 */     return (!hasTransform) && (resultRow.length == 1) ? resultRow[0] : resultRow;
/* 330:    */   }
/* 331:    */   
/* 332:    */   protected Object[] getResultRow(Object[] row, ResultSet rs, SessionImplementor session)
/* 333:    */     throws SQLException, HibernateException
/* 334:    */   {
/* 335:    */     Object[] resultRow;
/* 336:418 */     if (this.hasScalars)
/* 337:    */     {
/* 338:419 */       String[][] scalarColumns = this.scalarColumnNames;
/* 339:420 */       int queryCols = this.queryReturnTypes.length;
/* 340:421 */       Object[] resultRow = new Object[queryCols];
/* 341:422 */       for (int i = 0; i < queryCols; i++) {
/* 342:423 */         resultRow[i] = this.queryReturnTypes[i].nullSafeGet(rs, scalarColumns[i], session, null);
/* 343:    */       }
/* 344:    */     }
/* 345:    */     else
/* 346:    */     {
/* 347:427 */       resultRow = toResultRow(row);
/* 348:    */     }
/* 349:429 */     return resultRow;
/* 350:    */   }
/* 351:    */   
/* 352:    */   protected List getResultList(List results, ResultTransformer resultTransformer)
/* 353:    */     throws QueryException
/* 354:    */   {
/* 355:434 */     HolderInstantiator holderInstantiator = buildHolderInstantiator(resultTransformer);
/* 356:435 */     if (holderInstantiator.isRequired())
/* 357:    */     {
/* 358:436 */       for (int i = 0; i < results.size(); i++)
/* 359:    */       {
/* 360:437 */         Object[] row = (Object[])results.get(i);
/* 361:438 */         Object result = holderInstantiator.instantiate(row);
/* 362:439 */         results.set(i, result);
/* 363:    */       }
/* 364:442 */       if ((!hasSelectNew()) && (resultTransformer != null)) {
/* 365:443 */         return resultTransformer.transformList(results);
/* 366:    */       }
/* 367:446 */       return results;
/* 368:    */     }
/* 369:450 */     return results;
/* 370:    */   }
/* 371:    */   
/* 372:    */   private HolderInstantiator buildHolderInstantiator(ResultTransformer queryLocalResultTransformer)
/* 373:    */   {
/* 374:455 */     ResultTransformer implicitResultTransformer = this.aggregatedSelectExpression == null ? null : this.aggregatedSelectExpression.getResultTransformer();
/* 375:    */     
/* 376:    */ 
/* 377:458 */     return HolderInstantiator.getHolderInstantiator(implicitResultTransformer, queryLocalResultTransformer, this.queryReturnAliases);
/* 378:    */   }
/* 379:    */   
/* 380:    */   public List list(SessionImplementor session, QueryParameters queryParameters)
/* 381:    */     throws HibernateException
/* 382:    */   {
/* 383:469 */     checkQuery(queryParameters);
/* 384:470 */     return list(session, queryParameters, this.queryTranslator.getQuerySpaces(), this.queryReturnTypes);
/* 385:    */   }
/* 386:    */   
/* 387:    */   private void checkQuery(QueryParameters queryParameters)
/* 388:    */   {
/* 389:474 */     if ((hasSelectNew()) && (queryParameters.getResultTransformer() != null)) {
/* 390:475 */       throw new QueryException("ResultTransformer is not allowed for 'select new' queries.");
/* 391:    */     }
/* 392:    */   }
/* 393:    */   
/* 394:    */   public Iterator iterate(QueryParameters queryParameters, EventSource session)
/* 395:    */     throws HibernateException
/* 396:    */   {
/* 397:482 */     checkQuery(queryParameters);
/* 398:483 */     boolean stats = session.getFactory().getStatistics().isStatisticsEnabled();
/* 399:484 */     long startTime = 0L;
/* 400:485 */     if (stats) {
/* 401:486 */       startTime = System.currentTimeMillis();
/* 402:    */     }
/* 403:    */     try
/* 404:    */     {
/* 405:490 */       PreparedStatement st = prepareQueryStatement(queryParameters, false, session);
/* 406:491 */       if (queryParameters.isCallable()) {
/* 407:492 */         throw new QueryException("iterate() not supported for callable statements");
/* 408:    */       }
/* 409:494 */       ResultSet rs = getResultSet(st, queryParameters.hasAutoDiscoverScalarTypes(), false, queryParameters.getRowSelection(), session);
/* 410:495 */       Iterator result = new IteratorImpl(rs, st, session, queryParameters.isReadOnly(session), this.queryReturnTypes, this.queryTranslator.getColumnNames(), buildHolderInstantiator(queryParameters.getResultTransformer()));
/* 411:505 */       if (stats) {
/* 412:506 */         session.getFactory().getStatisticsImplementor().queryExecuted(getQueryIdentifier(), 0, System.currentTimeMillis() - startTime);
/* 413:    */       }
/* 414:514 */       return result;
/* 415:    */     }
/* 416:    */     catch (SQLException sqle)
/* 417:    */     {
/* 418:518 */       throw getFactory().getSQLExceptionHelper().convert(sqle, "could not execute query using iterate", getSQLString());
/* 419:    */     }
/* 420:    */   }
/* 421:    */   
/* 422:    */   public ScrollableResults scroll(QueryParameters queryParameters, SessionImplementor session)
/* 423:    */     throws HibernateException
/* 424:    */   {
/* 425:530 */     checkQuery(queryParameters);
/* 426:531 */     return scroll(queryParameters, this.queryReturnTypes, buildHolderInstantiator(queryParameters.getResultTransformer()), session);
/* 427:    */   }
/* 428:    */   
/* 429:    */   private Object[] toResultRow(Object[] row)
/* 430:    */   {
/* 431:542 */     if (this.selectLength == row.length) {
/* 432:543 */       return row;
/* 433:    */     }
/* 434:546 */     Object[] result = new Object[this.selectLength];
/* 435:547 */     int j = 0;
/* 436:548 */     for (int i = 0; i < row.length; i++) {
/* 437:549 */       if (this.includeInSelect[i] != 0) {
/* 438:550 */         result[(j++)] = row[i];
/* 439:    */       }
/* 440:    */     }
/* 441:553 */     return result;
/* 442:    */   }
/* 443:    */   
/* 444:    */   public int[] getNamedParameterLocs(String name)
/* 445:    */     throws QueryException
/* 446:    */   {
/* 447:561 */     return this.queryTranslator.getParameterTranslations().getNamedParameterSqlLocations(name);
/* 448:    */   }
/* 449:    */   
/* 450:    */   protected int bindParameterValues(PreparedStatement statement, QueryParameters queryParameters, int startIndex, SessionImplementor session)
/* 451:    */     throws SQLException
/* 452:    */   {
/* 453:582 */     int position = startIndex;
/* 454:    */     
/* 455:584 */     List parameterSpecs = this.queryTranslator.getCollectedParameterSpecifications();
/* 456:585 */     Iterator itr = parameterSpecs.iterator();
/* 457:586 */     while (itr.hasNext())
/* 458:    */     {
/* 459:587 */       ParameterSpecification spec = (ParameterSpecification)itr.next();
/* 460:588 */       position += spec.bind(statement, queryParameters, session, position);
/* 461:    */     }
/* 462:590 */     return position - startIndex;
/* 463:    */   }
/* 464:    */   
/* 465:    */   private int bindFilterParameterValues(PreparedStatement st, QueryParameters queryParameters, int position, SessionImplementor session)
/* 466:    */     throws SQLException
/* 467:    */   {
/* 468:601 */     int filteredParamCount = queryParameters.getFilteredPositionalParameterTypes() == null ? 0 : queryParameters.getFilteredPositionalParameterTypes().length;
/* 469:    */     
/* 470:    */ 
/* 471:604 */     int nonfilteredParamCount = queryParameters.getPositionalParameterTypes() == null ? 0 : queryParameters.getPositionalParameterTypes().length;
/* 472:    */     
/* 473:    */ 
/* 474:607 */     int filterParamCount = filteredParamCount - nonfilteredParamCount;
/* 475:608 */     for (int i = 0; i < filterParamCount; i++)
/* 476:    */     {
/* 477:609 */       Type type = queryParameters.getFilteredPositionalParameterTypes()[i];
/* 478:610 */       Object value = queryParameters.getFilteredPositionalParameterValues()[i];
/* 479:611 */       type.nullSafeSet(st, value, position, session);
/* 480:612 */       position += type.getColumnSpan(getFactory());
/* 481:    */     }
/* 482:615 */     return position;
/* 483:    */   }
/* 484:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.hql.QueryLoader
 * JD-Core Version:    0.7.0.1
 */