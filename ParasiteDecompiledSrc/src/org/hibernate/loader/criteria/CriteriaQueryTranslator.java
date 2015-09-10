/*   1:    */ package org.hibernate.loader.criteria;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Arrays;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.HashSet;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.LinkedHashMap;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import java.util.Map.Entry;
/*  13:    */ import java.util.Set;
/*  14:    */ import java.util.StringTokenizer;
/*  15:    */ import org.hibernate.Criteria;
/*  16:    */ import org.hibernate.EntityMode;
/*  17:    */ import org.hibernate.HibernateException;
/*  18:    */ import org.hibernate.LockMode;
/*  19:    */ import org.hibernate.LockOptions;
/*  20:    */ import org.hibernate.MappingException;
/*  21:    */ import org.hibernate.QueryException;
/*  22:    */ import org.hibernate.criterion.CriteriaQuery;
/*  23:    */ import org.hibernate.criterion.Criterion;
/*  24:    */ import org.hibernate.criterion.EnhancedProjection;
/*  25:    */ import org.hibernate.criterion.Order;
/*  26:    */ import org.hibernate.criterion.Projection;
/*  27:    */ import org.hibernate.engine.spi.QueryParameters;
/*  28:    */ import org.hibernate.engine.spi.RowSelection;
/*  29:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  30:    */ import org.hibernate.engine.spi.TypedValue;
/*  31:    */ import org.hibernate.hql.internal.ast.util.SessionFactoryHelper;
/*  32:    */ import org.hibernate.internal.CriteriaImpl;
/*  33:    */ import org.hibernate.internal.CriteriaImpl.CriterionEntry;
/*  34:    */ import org.hibernate.internal.CriteriaImpl.OrderEntry;
/*  35:    */ import org.hibernate.internal.CriteriaImpl.Subcriteria;
/*  36:    */ import org.hibernate.internal.util.StringHelper;
/*  37:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*  38:    */ import org.hibernate.persister.entity.Loadable;
/*  39:    */ import org.hibernate.persister.entity.PropertyMapping;
/*  40:    */ import org.hibernate.persister.entity.Queryable;
/*  41:    */ import org.hibernate.sql.JoinType;
/*  42:    */ import org.hibernate.type.AssociationType;
/*  43:    */ import org.hibernate.type.CollectionType;
/*  44:    */ import org.hibernate.type.StringRepresentableType;
/*  45:    */ import org.hibernate.type.Type;
/*  46:    */ import org.hibernate.type.TypeFactory;
/*  47:    */ import org.hibernate.type.TypeResolver;
/*  48:    */ 
/*  49:    */ public class CriteriaQueryTranslator
/*  50:    */   implements CriteriaQuery
/*  51:    */ {
/*  52:    */   public static final String ROOT_SQL_ALIAS = "this_";
/*  53:    */   private CriteriaQuery outerQueryTranslator;
/*  54:    */   private final CriteriaImpl rootCriteria;
/*  55:    */   private final String rootEntityName;
/*  56:    */   private final String rootSQLAlias;
/*  57: 77 */   private int aliasCount = 0;
/*  58: 79 */   private final Map criteriaInfoMap = new LinkedHashMap();
/*  59: 80 */   private final Map nameCriteriaInfoMap = new LinkedHashMap();
/*  60: 81 */   private final Map criteriaSQLAliasMap = new HashMap();
/*  61: 82 */   private final Map aliasCriteriaMap = new HashMap();
/*  62: 83 */   private final Map associationPathCriteriaMap = new LinkedHashMap();
/*  63: 84 */   private final Map<String, JoinType> associationPathJoinTypesMap = new LinkedHashMap();
/*  64: 85 */   private final Map withClauseMap = new HashMap();
/*  65:    */   private final SessionFactoryImplementor sessionFactory;
/*  66:    */   private final SessionFactoryHelper helper;
/*  67:    */   
/*  68:    */   public CriteriaQueryTranslator(SessionFactoryImplementor factory, CriteriaImpl criteria, String rootEntityName, String rootSQLAlias, CriteriaQuery outerQuery)
/*  69:    */     throws HibernateException
/*  70:    */   {
/*  71: 96 */     this(factory, criteria, rootEntityName, rootSQLAlias);
/*  72: 97 */     this.outerQueryTranslator = outerQuery;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public CriteriaQueryTranslator(SessionFactoryImplementor factory, CriteriaImpl criteria, String rootEntityName, String rootSQLAlias)
/*  76:    */     throws HibernateException
/*  77:    */   {
/*  78:105 */     this.rootCriteria = criteria;
/*  79:106 */     this.rootEntityName = rootEntityName;
/*  80:107 */     this.sessionFactory = factory;
/*  81:108 */     this.rootSQLAlias = rootSQLAlias;
/*  82:109 */     this.helper = new SessionFactoryHelper(factory);
/*  83:110 */     createAliasCriteriaMap();
/*  84:111 */     createAssociationPathCriteriaMap();
/*  85:112 */     createCriteriaEntityNameMap();
/*  86:113 */     createCriteriaSQLAliasMap();
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String generateSQLAlias()
/*  90:    */   {
/*  91:117 */     return StringHelper.generateAlias("this", this.aliasCount) + '_';
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String getRootSQLALias()
/*  95:    */   {
/*  96:121 */     return this.rootSQLAlias;
/*  97:    */   }
/*  98:    */   
/*  99:    */   private Criteria getAliasedCriteria(String alias)
/* 100:    */   {
/* 101:125 */     return (Criteria)this.aliasCriteriaMap.get(alias);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public boolean isJoin(String path)
/* 105:    */   {
/* 106:129 */     return this.associationPathCriteriaMap.containsKey(path);
/* 107:    */   }
/* 108:    */   
/* 109:    */   public JoinType getJoinType(String path)
/* 110:    */   {
/* 111:133 */     JoinType result = (JoinType)this.associationPathJoinTypesMap.get(path);
/* 112:134 */     return result == null ? JoinType.INNER_JOIN : result;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public Criteria getCriteria(String path)
/* 116:    */   {
/* 117:138 */     return (Criteria)this.associationPathCriteriaMap.get(path);
/* 118:    */   }
/* 119:    */   
/* 120:    */   public Set getQuerySpaces()
/* 121:    */   {
/* 122:142 */     Set result = new HashSet();
/* 123:143 */     Iterator iter = this.criteriaInfoMap.values().iterator();
/* 124:144 */     while (iter.hasNext())
/* 125:    */     {
/* 126:145 */       CriteriaInfoProvider info = (CriteriaInfoProvider)iter.next();
/* 127:146 */       result.addAll(Arrays.asList(info.getSpaces()));
/* 128:    */     }
/* 129:148 */     return result;
/* 130:    */   }
/* 131:    */   
/* 132:    */   private void createAliasCriteriaMap()
/* 133:    */   {
/* 134:152 */     this.aliasCriteriaMap.put(this.rootCriteria.getAlias(), this.rootCriteria);
/* 135:153 */     Iterator iter = this.rootCriteria.iterateSubcriteria();
/* 136:154 */     while (iter.hasNext())
/* 137:    */     {
/* 138:155 */       Criteria subcriteria = (Criteria)iter.next();
/* 139:156 */       if (subcriteria.getAlias() != null)
/* 140:    */       {
/* 141:157 */         Object old = this.aliasCriteriaMap.put(subcriteria.getAlias(), subcriteria);
/* 142:158 */         if (old != null) {
/* 143:159 */           throw new QueryException("duplicate alias: " + subcriteria.getAlias());
/* 144:    */         }
/* 145:    */       }
/* 146:    */     }
/* 147:    */   }
/* 148:    */   
/* 149:    */   private void createAssociationPathCriteriaMap()
/* 150:    */   {
/* 151:166 */     Iterator iter = this.rootCriteria.iterateSubcriteria();
/* 152:167 */     while (iter.hasNext())
/* 153:    */     {
/* 154:168 */       CriteriaImpl.Subcriteria crit = (CriteriaImpl.Subcriteria)iter.next();
/* 155:169 */       String wholeAssociationPath = getWholeAssociationPath(crit);
/* 156:170 */       Object old = this.associationPathCriteriaMap.put(wholeAssociationPath, crit);
/* 157:171 */       if (old != null) {
/* 158:172 */         throw new QueryException("duplicate association path: " + wholeAssociationPath);
/* 159:    */       }
/* 160:174 */       JoinType joinType = crit.getJoinType();
/* 161:175 */       old = this.associationPathJoinTypesMap.put(wholeAssociationPath, joinType);
/* 162:176 */       if (old != null) {
/* 163:178 */         throw new QueryException("duplicate association path: " + wholeAssociationPath);
/* 164:    */       }
/* 165:180 */       if (crit.getWithClause() != null) {
/* 166:182 */         this.withClauseMap.put(wholeAssociationPath, crit.getWithClause());
/* 167:    */       }
/* 168:    */     }
/* 169:    */   }
/* 170:    */   
/* 171:    */   private String getWholeAssociationPath(CriteriaImpl.Subcriteria subcriteria)
/* 172:    */   {
/* 173:188 */     String path = subcriteria.getPath();
/* 174:    */     
/* 175:    */ 
/* 176:    */ 
/* 177:192 */     Criteria parent = null;
/* 178:193 */     if (path.indexOf('.') > 0)
/* 179:    */     {
/* 180:195 */       String testAlias = StringHelper.root(path);
/* 181:196 */       if (!testAlias.equals(subcriteria.getAlias())) {
/* 182:200 */         parent = (Criteria)this.aliasCriteriaMap.get(testAlias);
/* 183:    */       }
/* 184:    */     }
/* 185:203 */     if (parent == null) {
/* 186:205 */       parent = subcriteria.getParent();
/* 187:    */     } else {
/* 188:208 */       path = StringHelper.unroot(path);
/* 189:    */     }
/* 190:211 */     if (parent.equals(this.rootCriteria)) {
/* 191:213 */       return path;
/* 192:    */     }
/* 193:217 */     return getWholeAssociationPath((CriteriaImpl.Subcriteria)parent) + '.' + path;
/* 194:    */   }
/* 195:    */   
/* 196:    */   private void createCriteriaEntityNameMap()
/* 197:    */   {
/* 198:223 */     CriteriaInfoProvider rootProvider = new EntityCriteriaInfoProvider((Queryable)this.sessionFactory.getEntityPersister(this.rootEntityName));
/* 199:224 */     this.criteriaInfoMap.put(this.rootCriteria, rootProvider);
/* 200:225 */     this.nameCriteriaInfoMap.put(rootProvider.getName(), rootProvider);
/* 201:    */     
/* 202:227 */     Iterator iter = this.associationPathCriteriaMap.entrySet().iterator();
/* 203:228 */     while (iter.hasNext())
/* 204:    */     {
/* 205:229 */       Map.Entry me = (Map.Entry)iter.next();
/* 206:230 */       CriteriaInfoProvider info = getPathInfo((String)me.getKey());
/* 207:    */       
/* 208:232 */       this.criteriaInfoMap.put(me.getValue(), info);
/* 209:    */       
/* 210:    */ 
/* 211:    */ 
/* 212:    */ 
/* 213:237 */       this.nameCriteriaInfoMap.put(info.getName(), info);
/* 214:    */     }
/* 215:    */   }
/* 216:    */   
/* 217:    */   private CriteriaInfoProvider getPathInfo(String path)
/* 218:    */   {
/* 219:243 */     StringTokenizer tokens = new StringTokenizer(path, ".");
/* 220:244 */     String componentPath = "";
/* 221:    */     
/* 222:    */ 
/* 223:247 */     CriteriaInfoProvider provider = (CriteriaInfoProvider)this.nameCriteriaInfoMap.get(this.rootEntityName);
/* 224:249 */     while (tokens.hasMoreTokens())
/* 225:    */     {
/* 226:250 */       componentPath = componentPath + tokens.nextToken();
/* 227:251 */       Type type = provider.getType(componentPath);
/* 228:252 */       if (type.isAssociationType())
/* 229:    */       {
/* 230:254 */         AssociationType atype = (AssociationType)type;
/* 231:255 */         CollectionType ctype = type.isCollectionType() ? (CollectionType)type : null;
/* 232:256 */         Type elementType = ctype != null ? ctype.getElementType(this.sessionFactory) : null;
/* 233:258 */         if ((ctype != null) && (elementType.isComponentType())) {
/* 234:259 */           provider = new ComponentCollectionCriteriaInfoProvider(this.helper.getCollectionPersister(ctype.getRole()));
/* 235:261 */         } else if ((ctype != null) && (!elementType.isEntityType())) {
/* 236:262 */           provider = new ScalarCollectionCriteriaInfoProvider(this.helper, ctype.getRole());
/* 237:    */         } else {
/* 238:265 */           provider = new EntityCriteriaInfoProvider((Queryable)this.sessionFactory.getEntityPersister(atype.getAssociatedEntityName(this.sessionFactory)));
/* 239:    */         }
/* 240:270 */         componentPath = "";
/* 241:    */       }
/* 242:272 */       else if (type.isComponentType())
/* 243:    */       {
/* 244:273 */         if (!tokens.hasMoreTokens()) {
/* 245:274 */           throw new QueryException("Criteria objects cannot be created directly on components.  Create a criteria on owning entity and use a dotted property to access component property: " + path);
/* 246:    */         }
/* 247:276 */         componentPath = componentPath + '.';
/* 248:    */       }
/* 249:    */       else
/* 250:    */       {
/* 251:280 */         throw new QueryException("not an association: " + componentPath);
/* 252:    */       }
/* 253:    */     }
/* 254:284 */     return provider;
/* 255:    */   }
/* 256:    */   
/* 257:    */   public int getSQLAliasCount()
/* 258:    */   {
/* 259:288 */     return this.criteriaSQLAliasMap.size();
/* 260:    */   }
/* 261:    */   
/* 262:    */   private void createCriteriaSQLAliasMap()
/* 263:    */   {
/* 264:292 */     int i = 0;
/* 265:293 */     Iterator criteriaIterator = this.criteriaInfoMap.entrySet().iterator();
/* 266:294 */     while (criteriaIterator.hasNext())
/* 267:    */     {
/* 268:295 */       Map.Entry me = (Map.Entry)criteriaIterator.next();
/* 269:296 */       Criteria crit = (Criteria)me.getKey();
/* 270:297 */       String alias = crit.getAlias();
/* 271:298 */       if (alias == null) {
/* 272:299 */         alias = ((CriteriaInfoProvider)me.getValue()).getName();
/* 273:    */       }
/* 274:301 */       this.criteriaSQLAliasMap.put(crit, StringHelper.generateAlias(alias, i++));
/* 275:    */     }
/* 276:303 */     this.criteriaSQLAliasMap.put(this.rootCriteria, this.rootSQLAlias);
/* 277:    */   }
/* 278:    */   
/* 279:    */   public CriteriaImpl getRootCriteria()
/* 280:    */   {
/* 281:307 */     return this.rootCriteria;
/* 282:    */   }
/* 283:    */   
/* 284:    */   public QueryParameters getQueryParameters()
/* 285:    */   {
/* 286:311 */     LockOptions lockOptions = new LockOptions();
/* 287:312 */     RowSelection selection = new RowSelection();
/* 288:313 */     selection.setFirstRow(this.rootCriteria.getFirstResult());
/* 289:314 */     selection.setMaxRows(this.rootCriteria.getMaxResults());
/* 290:315 */     selection.setTimeout(this.rootCriteria.getTimeout());
/* 291:316 */     selection.setFetchSize(this.rootCriteria.getFetchSize());
/* 292:    */     
/* 293:318 */     Iterator iter = this.rootCriteria.getLockModes().entrySet().iterator();
/* 294:319 */     while (iter.hasNext())
/* 295:    */     {
/* 296:320 */       Map.Entry me = (Map.Entry)iter.next();
/* 297:321 */       Criteria subcriteria = getAliasedCriteria((String)me.getKey());
/* 298:322 */       lockOptions.setAliasSpecificLockMode(getSQLAlias(subcriteria), (LockMode)me.getValue());
/* 299:    */     }
/* 300:324 */     List values = new ArrayList();
/* 301:325 */     List types = new ArrayList();
/* 302:326 */     iter = this.rootCriteria.iterateSubcriteria();
/* 303:327 */     while (iter.hasNext())
/* 304:    */     {
/* 305:328 */       CriteriaImpl.Subcriteria subcriteria = (CriteriaImpl.Subcriteria)iter.next();
/* 306:329 */       LockMode lm = subcriteria.getLockMode();
/* 307:330 */       if (lm != null) {
/* 308:331 */         lockOptions.setAliasSpecificLockMode(getSQLAlias(subcriteria), lm);
/* 309:    */       }
/* 310:333 */       if (subcriteria.getWithClause() != null)
/* 311:    */       {
/* 312:335 */         TypedValue[] tv = subcriteria.getWithClause().getTypedValues(subcriteria, this);
/* 313:336 */         for (int i = 0; i < tv.length; i++)
/* 314:    */         {
/* 315:337 */           values.add(tv[i].getValue());
/* 316:338 */           types.add(tv[i].getType());
/* 317:    */         }
/* 318:    */       }
/* 319:    */     }
/* 320:346 */     iter = this.rootCriteria.iterateExpressionEntries();
/* 321:347 */     while (iter.hasNext())
/* 322:    */     {
/* 323:348 */       CriteriaImpl.CriterionEntry ce = (CriteriaImpl.CriterionEntry)iter.next();
/* 324:349 */       TypedValue[] tv = ce.getCriterion().getTypedValues(ce.getCriteria(), this);
/* 325:350 */       for (int i = 0; i < tv.length; i++)
/* 326:    */       {
/* 327:351 */         values.add(tv[i].getValue());
/* 328:352 */         types.add(tv[i].getType());
/* 329:    */       }
/* 330:    */     }
/* 331:356 */     Object[] valueArray = values.toArray();
/* 332:357 */     Type[] typeArray = ArrayHelper.toTypeArray(types);
/* 333:358 */     return new QueryParameters(typeArray, valueArray, lockOptions, selection, this.rootCriteria.isReadOnlyInitialized(), this.rootCriteria.isReadOnlyInitialized() ? this.rootCriteria.isReadOnly() : false, this.rootCriteria.getCacheable(), this.rootCriteria.getCacheRegion(), this.rootCriteria.getComment(), this.rootCriteria.isLookupByNaturalKey(), this.rootCriteria.getResultTransformer());
/* 334:    */   }
/* 335:    */   
/* 336:    */   public boolean hasProjection()
/* 337:    */   {
/* 338:374 */     return this.rootCriteria.getProjection() != null;
/* 339:    */   }
/* 340:    */   
/* 341:    */   public String getGroupBy()
/* 342:    */   {
/* 343:378 */     if (this.rootCriteria.getProjection().isGrouped()) {
/* 344:379 */       return this.rootCriteria.getProjection().toGroupSqlString(this.rootCriteria.getProjectionCriteria(), this);
/* 345:    */     }
/* 346:383 */     return "";
/* 347:    */   }
/* 348:    */   
/* 349:    */   public String getSelect()
/* 350:    */   {
/* 351:388 */     return this.rootCriteria.getProjection().toSqlString(this.rootCriteria.getProjectionCriteria(), 0, this);
/* 352:    */   }
/* 353:    */   
/* 354:    */   Type getResultType(Criteria criteria)
/* 355:    */   {
/* 356:397 */     return getFactory().getTypeResolver().getTypeFactory().manyToOne(getEntityName(criteria));
/* 357:    */   }
/* 358:    */   
/* 359:    */   public Type[] getProjectedTypes()
/* 360:    */   {
/* 361:401 */     return this.rootCriteria.getProjection().getTypes(this.rootCriteria, this);
/* 362:    */   }
/* 363:    */   
/* 364:    */   public String[] getProjectedColumnAliases()
/* 365:    */   {
/* 366:405 */     return (this.rootCriteria.getProjection() instanceof EnhancedProjection) ? ((EnhancedProjection)this.rootCriteria.getProjection()).getColumnAliases(0, this.rootCriteria, this) : this.rootCriteria.getProjection().getColumnAliases(0);
/* 367:    */   }
/* 368:    */   
/* 369:    */   public String[] getProjectedAliases()
/* 370:    */   {
/* 371:411 */     return this.rootCriteria.getProjection().getAliases();
/* 372:    */   }
/* 373:    */   
/* 374:    */   public String getWhereCondition()
/* 375:    */   {
/* 376:415 */     StringBuffer condition = new StringBuffer(30);
/* 377:416 */     Iterator criterionIterator = this.rootCriteria.iterateExpressionEntries();
/* 378:417 */     while (criterionIterator.hasNext())
/* 379:    */     {
/* 380:418 */       CriteriaImpl.CriterionEntry entry = (CriteriaImpl.CriterionEntry)criterionIterator.next();
/* 381:419 */       String sqlString = entry.getCriterion().toSqlString(entry.getCriteria(), this);
/* 382:420 */       condition.append(sqlString);
/* 383:421 */       if (criterionIterator.hasNext()) {
/* 384:422 */         condition.append(" and ");
/* 385:    */       }
/* 386:    */     }
/* 387:425 */     return condition.toString();
/* 388:    */   }
/* 389:    */   
/* 390:    */   public String getOrderBy()
/* 391:    */   {
/* 392:429 */     StringBuffer orderBy = new StringBuffer(30);
/* 393:430 */     Iterator criterionIterator = this.rootCriteria.iterateOrderings();
/* 394:431 */     while (criterionIterator.hasNext())
/* 395:    */     {
/* 396:432 */       CriteriaImpl.OrderEntry oe = (CriteriaImpl.OrderEntry)criterionIterator.next();
/* 397:433 */       orderBy.append(oe.getOrder().toSqlString(oe.getCriteria(), this));
/* 398:434 */       if (criterionIterator.hasNext()) {
/* 399:435 */         orderBy.append(", ");
/* 400:    */       }
/* 401:    */     }
/* 402:438 */     return orderBy.toString();
/* 403:    */   }
/* 404:    */   
/* 405:    */   public SessionFactoryImplementor getFactory()
/* 406:    */   {
/* 407:442 */     return this.sessionFactory;
/* 408:    */   }
/* 409:    */   
/* 410:    */   public String getSQLAlias(Criteria criteria)
/* 411:    */   {
/* 412:446 */     return (String)this.criteriaSQLAliasMap.get(criteria);
/* 413:    */   }
/* 414:    */   
/* 415:    */   public String getEntityName(Criteria criteria)
/* 416:    */   {
/* 417:450 */     return ((CriteriaInfoProvider)this.criteriaInfoMap.get(criteria)).getName();
/* 418:    */   }
/* 419:    */   
/* 420:    */   public String getColumn(Criteria criteria, String propertyName)
/* 421:    */   {
/* 422:454 */     String[] cols = getColumns(propertyName, criteria);
/* 423:455 */     if (cols.length != 1) {
/* 424:456 */       throw new QueryException("property does not map to a single column: " + propertyName);
/* 425:    */     }
/* 426:458 */     return cols[0];
/* 427:    */   }
/* 428:    */   
/* 429:    */   public String[] getColumnsUsingProjection(Criteria subcriteria, String propertyName)
/* 430:    */     throws HibernateException
/* 431:    */   {
/* 432:470 */     Projection projection = this.rootCriteria.getProjection();
/* 433:471 */     String[] projectionColumns = null;
/* 434:472 */     if (projection != null) {
/* 435:473 */       projectionColumns = (projection instanceof EnhancedProjection) ? ((EnhancedProjection)projection).getColumnAliases(propertyName, 0, this.rootCriteria, this) : projection.getColumnAliases(propertyName, 0);
/* 436:    */     }
/* 437:478 */     if (projectionColumns == null) {
/* 438:    */       try
/* 439:    */       {
/* 440:482 */         return getColumns(propertyName, subcriteria);
/* 441:    */       }
/* 442:    */       catch (HibernateException he)
/* 443:    */       {
/* 444:486 */         if (this.outerQueryTranslator != null) {
/* 445:487 */           return this.outerQueryTranslator.getColumnsUsingProjection(subcriteria, propertyName);
/* 446:    */         }
/* 447:490 */         throw he;
/* 448:    */       }
/* 449:    */     }
/* 450:496 */     return projectionColumns;
/* 451:    */   }
/* 452:    */   
/* 453:    */   public String[] getIdentifierColumns(Criteria subcriteria)
/* 454:    */   {
/* 455:501 */     String[] idcols = ((Loadable)getPropertyMapping(getEntityName(subcriteria))).getIdentifierColumnNames();
/* 456:    */     
/* 457:503 */     return StringHelper.qualify(getSQLAlias(subcriteria), idcols);
/* 458:    */   }
/* 459:    */   
/* 460:    */   public Type getIdentifierType(Criteria subcriteria)
/* 461:    */   {
/* 462:507 */     return ((Loadable)getPropertyMapping(getEntityName(subcriteria))).getIdentifierType();
/* 463:    */   }
/* 464:    */   
/* 465:    */   public TypedValue getTypedIdentifierValue(Criteria subcriteria, Object value)
/* 466:    */   {
/* 467:511 */     Loadable loadable = (Loadable)getPropertyMapping(getEntityName(subcriteria));
/* 468:512 */     return new TypedValue(loadable.getIdentifierType(), value, EntityMode.POJO);
/* 469:    */   }
/* 470:    */   
/* 471:    */   public String[] getColumns(String propertyName, Criteria subcriteria)
/* 472:    */     throws HibernateException
/* 473:    */   {
/* 474:522 */     return getPropertyMapping(getEntityName(subcriteria, propertyName)).toColumns(getSQLAlias(subcriteria, propertyName), getPropertyName(propertyName));
/* 475:    */   }
/* 476:    */   
/* 477:    */   public String[] findColumns(String propertyName, Criteria subcriteria)
/* 478:    */     throws HibernateException
/* 479:    */   {
/* 480:    */     try
/* 481:    */     {
/* 482:537 */       return getColumns(propertyName, subcriteria);
/* 483:    */     }
/* 484:    */     catch (HibernateException he)
/* 485:    */     {
/* 486:541 */       if (this.outerQueryTranslator != null) {
/* 487:542 */         return this.outerQueryTranslator.findColumns(propertyName, subcriteria);
/* 488:    */       }
/* 489:545 */       throw he;
/* 490:    */     }
/* 491:    */   }
/* 492:    */   
/* 493:    */   public Type getTypeUsingProjection(Criteria subcriteria, String propertyName)
/* 494:    */     throws HibernateException
/* 495:    */   {
/* 496:554 */     Projection projection = this.rootCriteria.getProjection();
/* 497:555 */     Type[] projectionTypes = projection == null ? null : projection.getTypes(propertyName, subcriteria, this);
/* 498:559 */     if (projectionTypes == null) {
/* 499:    */       try
/* 500:    */       {
/* 501:563 */         return getType(subcriteria, propertyName);
/* 502:    */       }
/* 503:    */       catch (HibernateException he)
/* 504:    */       {
/* 505:567 */         if (this.outerQueryTranslator != null) {
/* 506:568 */           return this.outerQueryTranslator.getType(subcriteria, propertyName);
/* 507:    */         }
/* 508:571 */         throw he;
/* 509:    */       }
/* 510:    */     }
/* 511:576 */     if (projectionTypes.length != 1) {
/* 512:578 */       throw new QueryException("not a single-length projection: " + propertyName);
/* 513:    */     }
/* 514:580 */     return projectionTypes[0];
/* 515:    */   }
/* 516:    */   
/* 517:    */   public Type getType(Criteria subcriteria, String propertyName)
/* 518:    */     throws HibernateException
/* 519:    */   {
/* 520:586 */     return getPropertyMapping(getEntityName(subcriteria, propertyName)).toType(getPropertyName(propertyName));
/* 521:    */   }
/* 522:    */   
/* 523:    */   public TypedValue getTypedValue(Criteria subcriteria, String propertyName, Object value)
/* 524:    */     throws HibernateException
/* 525:    */   {
/* 526:596 */     if ((value instanceof Class))
/* 527:    */     {
/* 528:597 */       Class entityClass = (Class)value;
/* 529:598 */       Queryable q = SessionFactoryHelper.findQueryableUsingImports(this.sessionFactory, entityClass.getName());
/* 530:599 */       if (q != null)
/* 531:    */       {
/* 532:600 */         Type type = q.getDiscriminatorType();
/* 533:601 */         String stringValue = q.getDiscriminatorSQLValue();
/* 534:602 */         if ((stringValue != null) && (stringValue.length() > 2) && (stringValue.startsWith("'")) && (stringValue.endsWith("'"))) {
/* 535:606 */           stringValue = stringValue.substring(1, stringValue.length() - 1);
/* 536:    */         }
/* 537:611 */         if ((type instanceof StringRepresentableType))
/* 538:    */         {
/* 539:612 */           StringRepresentableType nullableType = (StringRepresentableType)type;
/* 540:613 */           value = nullableType.fromStringValue(stringValue);
/* 541:    */         }
/* 542:    */         else
/* 543:    */         {
/* 544:616 */           throw new QueryException("Unsupported discriminator type " + type);
/* 545:    */         }
/* 546:618 */         return new TypedValue(type, value, EntityMode.POJO);
/* 547:    */       }
/* 548:    */     }
/* 549:626 */     return new TypedValue(getTypeUsingProjection(subcriteria, propertyName), value, EntityMode.POJO);
/* 550:    */   }
/* 551:    */   
/* 552:    */   private PropertyMapping getPropertyMapping(String entityName)
/* 553:    */     throws MappingException
/* 554:    */   {
/* 555:635 */     CriteriaInfoProvider info = (CriteriaInfoProvider)this.nameCriteriaInfoMap.get(entityName);
/* 556:636 */     return info.getPropertyMapping();
/* 557:    */   }
/* 558:    */   
/* 559:    */   public String getEntityName(Criteria subcriteria, String propertyName)
/* 560:    */   {
/* 561:642 */     if (propertyName.indexOf('.') > 0)
/* 562:    */     {
/* 563:643 */       String root = StringHelper.root(propertyName);
/* 564:644 */       Criteria crit = getAliasedCriteria(root);
/* 565:645 */       if (crit != null) {
/* 566:646 */         return getEntityName(crit);
/* 567:    */       }
/* 568:    */     }
/* 569:649 */     return getEntityName(subcriteria);
/* 570:    */   }
/* 571:    */   
/* 572:    */   public String getSQLAlias(Criteria criteria, String propertyName)
/* 573:    */   {
/* 574:653 */     if (propertyName.indexOf('.') > 0)
/* 575:    */     {
/* 576:654 */       String root = StringHelper.root(propertyName);
/* 577:655 */       Criteria subcriteria = getAliasedCriteria(root);
/* 578:656 */       if (subcriteria != null) {
/* 579:657 */         return getSQLAlias(subcriteria);
/* 580:    */       }
/* 581:    */     }
/* 582:660 */     return getSQLAlias(criteria);
/* 583:    */   }
/* 584:    */   
/* 585:    */   public String getPropertyName(String propertyName)
/* 586:    */   {
/* 587:664 */     if (propertyName.indexOf('.') > 0)
/* 588:    */     {
/* 589:665 */       String root = StringHelper.root(propertyName);
/* 590:666 */       Criteria crit = getAliasedCriteria(root);
/* 591:667 */       if (crit != null) {
/* 592:668 */         return propertyName.substring(root.length() + 1);
/* 593:    */       }
/* 594:    */     }
/* 595:671 */     return propertyName;
/* 596:    */   }
/* 597:    */   
/* 598:    */   public String getWithClause(String path)
/* 599:    */   {
/* 600:676 */     Criterion crit = (Criterion)this.withClauseMap.get(path);
/* 601:677 */     return crit == null ? null : crit.toSqlString(getCriteria(path), this);
/* 602:    */   }
/* 603:    */   
/* 604:    */   public boolean hasRestriction(String path)
/* 605:    */   {
/* 606:682 */     CriteriaImpl.Subcriteria crit = (CriteriaImpl.Subcriteria)getCriteria(path);
/* 607:683 */     return crit == null ? false : crit.hasRestriction();
/* 608:    */   }
/* 609:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.loader.criteria.CriteriaQueryTranslator
 * JD-Core Version:    0.7.0.1
 */