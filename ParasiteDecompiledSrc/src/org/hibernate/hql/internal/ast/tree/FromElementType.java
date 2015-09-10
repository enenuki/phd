/*   1:    */ package org.hibernate.hql.internal.ast.tree;
/*   2:    */ 
/*   3:    */ import java.util.Arrays;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.Map;
/*   6:    */ import org.hibernate.MappingException;
/*   7:    */ import org.hibernate.QueryException;
/*   8:    */ import org.hibernate.engine.internal.JoinSequence;
/*   9:    */ import org.hibernate.engine.internal.JoinSequence.Join;
/*  10:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  11:    */ import org.hibernate.hql.internal.CollectionProperties;
/*  12:    */ import org.hibernate.hql.internal.CollectionSubqueryFactory;
/*  13:    */ import org.hibernate.hql.internal.NameGenerator;
/*  14:    */ import org.hibernate.hql.internal.ast.HqlSqlWalker;
/*  15:    */ import org.hibernate.hql.internal.ast.util.SessionFactoryHelper;
/*  16:    */ import org.hibernate.internal.CoreMessageLogger;
/*  17:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*  18:    */ import org.hibernate.param.ParameterSpecification;
/*  19:    */ import org.hibernate.persister.collection.CollectionPropertyMapping;
/*  20:    */ import org.hibernate.persister.collection.QueryableCollection;
/*  21:    */ import org.hibernate.persister.entity.EntityPersister;
/*  22:    */ import org.hibernate.persister.entity.Joinable;
/*  23:    */ import org.hibernate.persister.entity.PropertyMapping;
/*  24:    */ import org.hibernate.persister.entity.Queryable;
/*  25:    */ import org.hibernate.type.EntityType;
/*  26:    */ import org.hibernate.type.Type;
/*  27:    */ import org.hibernate.type.TypeFactory;
/*  28:    */ import org.hibernate.type.TypeResolver;
/*  29:    */ import org.jboss.logging.Logger;
/*  30:    */ 
/*  31:    */ class FromElementType
/*  32:    */ {
/*  33: 59 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, FromElementType.class.getName());
/*  34:    */   private FromElement fromElement;
/*  35:    */   private EntityType entityType;
/*  36:    */   private EntityPersister persister;
/*  37:    */   private QueryableCollection queryableCollection;
/*  38:    */   private CollectionPropertyMapping collectionPropertyMapping;
/*  39:    */   private JoinSequence joinSequence;
/*  40:    */   private String collectionSuffix;
/*  41:    */   private ParameterSpecification indexCollectionSelectorParamSpec;
/*  42:    */   
/*  43:    */   public FromElementType(FromElement fromElement, EntityPersister persister, EntityType entityType)
/*  44:    */   {
/*  45: 71 */     this.fromElement = fromElement;
/*  46: 72 */     this.persister = persister;
/*  47: 73 */     this.entityType = entityType;
/*  48: 74 */     if (persister != null) {
/*  49: 75 */       fromElement.setText(((Queryable)persister).getTableName() + " " + getTableAlias());
/*  50:    */     }
/*  51:    */   }
/*  52:    */   
/*  53:    */   protected FromElementType(FromElement fromElement)
/*  54:    */   {
/*  55: 80 */     this.fromElement = fromElement;
/*  56:    */   }
/*  57:    */   
/*  58:    */   private String getTableAlias()
/*  59:    */   {
/*  60: 84 */     return this.fromElement.getTableAlias();
/*  61:    */   }
/*  62:    */   
/*  63:    */   private String getCollectionTableAlias()
/*  64:    */   {
/*  65: 88 */     return this.fromElement.getCollectionTableAlias();
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String getCollectionSuffix()
/*  69:    */   {
/*  70: 92 */     return this.collectionSuffix;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setCollectionSuffix(String suffix)
/*  74:    */   {
/*  75: 96 */     this.collectionSuffix = suffix;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public EntityPersister getEntityPersister()
/*  79:    */   {
/*  80:100 */     return this.persister;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public Type getDataType()
/*  84:    */   {
/*  85:104 */     if (this.persister == null)
/*  86:    */     {
/*  87:105 */       if (this.queryableCollection == null) {
/*  88:106 */         return null;
/*  89:    */       }
/*  90:108 */       return this.queryableCollection.getType();
/*  91:    */     }
/*  92:111 */     return this.entityType;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public Type getSelectType()
/*  96:    */   {
/*  97:116 */     if (this.entityType == null) {
/*  98:116 */       return null;
/*  99:    */     }
/* 100:117 */     boolean shallow = this.fromElement.getFromClause().getWalker().isShallowQuery();
/* 101:118 */     return this.fromElement.getSessionFactoryHelper().getFactory().getTypeResolver().getTypeFactory().manyToOne(this.entityType.getAssociatedEntityName(), shallow);
/* 102:    */   }
/* 103:    */   
/* 104:    */   public Queryable getQueryable()
/* 105:    */   {
/* 106:130 */     return (this.persister instanceof Queryable) ? (Queryable)this.persister : null;
/* 107:    */   }
/* 108:    */   
/* 109:    */   String renderScalarIdentifierSelect(int i)
/* 110:    */   {
/* 111:140 */     checkInitialized();
/* 112:141 */     String[] cols = getPropertyMapping("id").toColumns(getTableAlias(), "id");
/* 113:142 */     StringBuffer buf = new StringBuffer();
/* 114:144 */     for (int j = 0; j < cols.length; j++)
/* 115:    */     {
/* 116:145 */       String column = cols[j];
/* 117:146 */       if (j > 0) {
/* 118:147 */         buf.append(", ");
/* 119:    */       }
/* 120:149 */       buf.append(column).append(" as ").append(NameGenerator.scalarName(i, j));
/* 121:    */     }
/* 122:151 */     return buf.toString();
/* 123:    */   }
/* 124:    */   
/* 125:    */   String renderIdentifierSelect(int size, int k)
/* 126:    */   {
/* 127:162 */     checkInitialized();
/* 128:164 */     if (this.fromElement.getFromClause().isSubQuery())
/* 129:    */     {
/* 130:166 */       String[] idColumnNames = this.persister != null ? ((Queryable)this.persister).getIdentifierColumnNames() : new String[0];
/* 131:    */       
/* 132:168 */       StringBuffer buf = new StringBuffer();
/* 133:169 */       for (int i = 0; i < idColumnNames.length; i++)
/* 134:    */       {
/* 135:170 */         buf.append(this.fromElement.getTableAlias()).append('.').append(idColumnNames[i]);
/* 136:171 */         if (i != idColumnNames.length - 1) {
/* 137:171 */           buf.append(", ");
/* 138:    */         }
/* 139:    */       }
/* 140:173 */       return buf.toString();
/* 141:    */     }
/* 142:176 */     if (this.persister == null) {
/* 143:177 */       throw new QueryException("not an entity");
/* 144:    */     }
/* 145:179 */     String fragment = ((Queryable)this.persister).identifierSelectFragment(getTableAlias(), getSuffix(size, k));
/* 146:180 */     return trimLeadingCommaAndSpaces(fragment);
/* 147:    */   }
/* 148:    */   
/* 149:    */   private String getSuffix(int size, int sequence)
/* 150:    */   {
/* 151:185 */     return generateSuffix(size, sequence);
/* 152:    */   }
/* 153:    */   
/* 154:    */   private static String generateSuffix(int size, int k)
/* 155:    */   {
/* 156:189 */     String suffix = Integer.toString(k) + '_';
/* 157:190 */     return suffix;
/* 158:    */   }
/* 159:    */   
/* 160:    */   private void checkInitialized()
/* 161:    */   {
/* 162:194 */     this.fromElement.checkInitialized();
/* 163:    */   }
/* 164:    */   
/* 165:    */   String renderPropertySelect(int size, int k, boolean allProperties)
/* 166:    */   {
/* 167:204 */     checkInitialized();
/* 168:205 */     if (this.persister == null) {
/* 169:206 */       return "";
/* 170:    */     }
/* 171:209 */     String fragment = ((Queryable)this.persister).propertySelectFragment(getTableAlias(), getSuffix(size, k), allProperties);
/* 172:    */     
/* 173:    */ 
/* 174:    */ 
/* 175:    */ 
/* 176:214 */     return trimLeadingCommaAndSpaces(fragment);
/* 177:    */   }
/* 178:    */   
/* 179:    */   String renderCollectionSelectFragment(int size, int k)
/* 180:    */   {
/* 181:219 */     if (this.queryableCollection == null) {
/* 182:220 */       return "";
/* 183:    */     }
/* 184:223 */     if (this.collectionSuffix == null) {
/* 185:224 */       this.collectionSuffix = generateSuffix(size, k);
/* 186:    */     }
/* 187:226 */     String fragment = this.queryableCollection.selectFragment(getCollectionTableAlias(), this.collectionSuffix);
/* 188:227 */     return trimLeadingCommaAndSpaces(fragment);
/* 189:    */   }
/* 190:    */   
/* 191:    */   public String renderValueCollectionSelectFragment(int size, int k)
/* 192:    */   {
/* 193:232 */     if (this.queryableCollection == null) {
/* 194:233 */       return "";
/* 195:    */     }
/* 196:236 */     if (this.collectionSuffix == null) {
/* 197:237 */       this.collectionSuffix = generateSuffix(size, k);
/* 198:    */     }
/* 199:239 */     String fragment = this.queryableCollection.selectFragment(getTableAlias(), this.collectionSuffix);
/* 200:240 */     return trimLeadingCommaAndSpaces(fragment);
/* 201:    */   }
/* 202:    */   
/* 203:    */   private static String trimLeadingCommaAndSpaces(String fragment)
/* 204:    */   {
/* 205:252 */     if ((fragment.length() > 0) && (fragment.charAt(0) == ',')) {
/* 206:253 */       fragment = fragment.substring(1);
/* 207:    */     }
/* 208:255 */     fragment = fragment.trim();
/* 209:256 */     return fragment.trim();
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void setJoinSequence(JoinSequence joinSequence)
/* 213:    */   {
/* 214:260 */     this.joinSequence = joinSequence;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public JoinSequence getJoinSequence()
/* 218:    */   {
/* 219:264 */     if (this.joinSequence != null) {
/* 220:265 */       return this.joinSequence;
/* 221:    */     }
/* 222:269 */     if ((this.persister instanceof Joinable))
/* 223:    */     {
/* 224:270 */       Joinable joinable = (Joinable)this.persister;
/* 225:271 */       return this.fromElement.getSessionFactoryHelper().createJoinSequence().setRoot(joinable, getTableAlias());
/* 226:    */     }
/* 227:274 */     return null;
/* 228:    */   }
/* 229:    */   
/* 230:    */   public void setQueryableCollection(QueryableCollection queryableCollection)
/* 231:    */   {
/* 232:279 */     if (this.queryableCollection != null) {
/* 233:280 */       throw new IllegalStateException("QueryableCollection is already defined for " + this + "!");
/* 234:    */     }
/* 235:282 */     this.queryableCollection = queryableCollection;
/* 236:283 */     if (!queryableCollection.isOneToMany()) {
/* 237:285 */       this.fromElement.setText(queryableCollection.getTableName() + " " + getTableAlias());
/* 238:    */     }
/* 239:    */   }
/* 240:    */   
/* 241:    */   public QueryableCollection getQueryableCollection()
/* 242:    */   {
/* 243:290 */     return this.queryableCollection;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public Type getPropertyType(String propertyName, String propertyPath)
/* 247:    */   {
/* 248:301 */     checkInitialized();
/* 249:302 */     Type type = null;
/* 250:308 */     if ((this.persister != null) && (propertyName.equals(propertyPath)) && (propertyName.equals(this.persister.getIdentifierPropertyName())))
/* 251:    */     {
/* 252:309 */       type = this.persister.getIdentifierType();
/* 253:    */     }
/* 254:    */     else
/* 255:    */     {
/* 256:312 */       PropertyMapping mapping = getPropertyMapping(propertyName);
/* 257:313 */       type = mapping.toType(propertyPath);
/* 258:    */     }
/* 259:315 */     if (type == null) {
/* 260:316 */       throw new MappingException("Property " + propertyName + " does not exist in " + (this.queryableCollection == null ? "class" : "collection") + " " + (this.queryableCollection == null ? this.fromElement.getClassName() : this.queryableCollection.getRole()));
/* 261:    */     }
/* 262:320 */     return type;
/* 263:    */   }
/* 264:    */   
/* 265:    */   String[] toColumns(String tableAlias, String path, boolean inSelect)
/* 266:    */   {
/* 267:324 */     return toColumns(tableAlias, path, inSelect, false);
/* 268:    */   }
/* 269:    */   
/* 270:    */   String[] toColumns(String tableAlias, String path, boolean inSelect, boolean forceAlias)
/* 271:    */   {
/* 272:328 */     checkInitialized();
/* 273:329 */     PropertyMapping propertyMapping = getPropertyMapping(path);
/* 274:338 */     if ((!inSelect) && (this.queryableCollection != null) && (CollectionProperties.isCollectionProperty(path)))
/* 275:    */     {
/* 276:339 */       Map enabledFilters = this.fromElement.getWalker().getEnabledFilters();
/* 277:340 */       String subquery = CollectionSubqueryFactory.createCollectionSubquery(this.joinSequence.copy().setUseThetaStyle(true), enabledFilters, propertyMapping.toColumns(tableAlias, path));
/* 278:    */       
/* 279:    */ 
/* 280:    */ 
/* 281:    */ 
/* 282:345 */       LOG.debugf("toColumns(%s,%s) : subquery = %s", tableAlias, path, subquery);
/* 283:346 */       return new String[] { "(" + subquery + ")" };
/* 284:    */     }
/* 285:348 */     if (forceAlias) {
/* 286:349 */       return propertyMapping.toColumns(tableAlias, path);
/* 287:    */     }
/* 288:350 */     if (this.fromElement.getWalker().getStatementType() == 45) {
/* 289:351 */       return propertyMapping.toColumns(tableAlias, path);
/* 290:    */     }
/* 291:352 */     if (this.fromElement.getWalker().getCurrentClauseType() == 45) {
/* 292:353 */       return propertyMapping.toColumns(tableAlias, path);
/* 293:    */     }
/* 294:354 */     if (this.fromElement.getWalker().isSubQuery())
/* 295:    */     {
/* 296:368 */       if (isCorrelation())
/* 297:    */       {
/* 298:369 */         if (isMultiTable()) {
/* 299:369 */           return propertyMapping.toColumns(tableAlias, path);
/* 300:    */         }
/* 301:370 */         return propertyMapping.toColumns(extractTableName(), path);
/* 302:    */       }
/* 303:372 */       return propertyMapping.toColumns(tableAlias, path);
/* 304:    */     }
/* 305:374 */     String[] columns = propertyMapping.toColumns(path);
/* 306:375 */     LOG.tracev("Using non-qualified column reference [{0} -> ({1})]", path, ArrayHelper.toString(columns));
/* 307:376 */     return columns;
/* 308:    */   }
/* 309:    */   
/* 310:    */   private boolean isCorrelation()
/* 311:    */   {
/* 312:381 */     FromClause top = this.fromElement.getWalker().getFinalFromClause();
/* 313:382 */     return (this.fromElement.getFromClause() != this.fromElement.getWalker().getCurrentFromClause()) && (this.fromElement.getFromClause() == top);
/* 314:    */   }
/* 315:    */   
/* 316:    */   private boolean isMultiTable()
/* 317:    */   {
/* 318:388 */     return (this.fromElement.getQueryable() != null) && (this.fromElement.getQueryable().isMultiTable());
/* 319:    */   }
/* 320:    */   
/* 321:    */   private String extractTableName()
/* 322:    */   {
/* 323:394 */     return this.fromElement.getQueryable().getTableName();
/* 324:    */   }
/* 325:    */   
/* 326:397 */   private static final List SPECIAL_MANY2MANY_TREATMENT_FUNCTION_NAMES = Arrays.asList(new String[] { "index", "minIndex", "maxIndex" });
/* 327:    */   
/* 328:    */   PropertyMapping getPropertyMapping(String propertyName)
/* 329:    */   {
/* 330:406 */     checkInitialized();
/* 331:407 */     if (this.queryableCollection == null) {
/* 332:408 */       return (PropertyMapping)this.persister;
/* 333:    */     }
/* 334:414 */     if ((this.queryableCollection.isManyToMany()) && (this.queryableCollection.hasIndex()) && (SPECIAL_MANY2MANY_TREATMENT_FUNCTION_NAMES.contains(propertyName))) {
/* 335:417 */       return new SpecialManyToManyCollectionPropertyMapping(null);
/* 336:    */     }
/* 337:421 */     if (CollectionProperties.isCollectionProperty(propertyName))
/* 338:    */     {
/* 339:422 */       if (this.collectionPropertyMapping == null) {
/* 340:423 */         this.collectionPropertyMapping = new CollectionPropertyMapping(this.queryableCollection);
/* 341:    */       }
/* 342:425 */       return this.collectionPropertyMapping;
/* 343:    */     }
/* 344:428 */     if (this.queryableCollection.getElementType().isAnyType()) {
/* 345:431 */       return this.queryableCollection;
/* 346:    */     }
/* 347:435 */     if (this.queryableCollection.getElementType().isComponentType()) {
/* 348:437 */       if (propertyName.equals("id")) {
/* 349:438 */         return (PropertyMapping)this.queryableCollection.getOwnerEntityPersister();
/* 350:    */       }
/* 351:    */     }
/* 352:441 */     return this.queryableCollection;
/* 353:    */   }
/* 354:    */   
/* 355:    */   public boolean isCollectionOfValuesOrComponents()
/* 356:    */   {
/* 357:445 */     return (this.persister == null) && (this.queryableCollection != null) && (!this.queryableCollection.getElementType().isEntityType());
/* 358:    */   }
/* 359:    */   
/* 360:    */   public boolean isEntity()
/* 361:    */   {
/* 362:451 */     return this.persister != null;
/* 363:    */   }
/* 364:    */   
/* 365:    */   public ParameterSpecification getIndexCollectionSelectorParamSpec()
/* 366:    */   {
/* 367:455 */     return this.indexCollectionSelectorParamSpec;
/* 368:    */   }
/* 369:    */   
/* 370:    */   public void setIndexCollectionSelectorParamSpec(ParameterSpecification indexCollectionSelectorParamSpec)
/* 371:    */   {
/* 372:459 */     this.indexCollectionSelectorParamSpec = indexCollectionSelectorParamSpec;
/* 373:    */   }
/* 374:    */   
/* 375:    */   private class SpecialManyToManyCollectionPropertyMapping
/* 376:    */     implements PropertyMapping
/* 377:    */   {
/* 378:    */     private SpecialManyToManyCollectionPropertyMapping() {}
/* 379:    */     
/* 380:    */     public Type getType()
/* 381:    */     {
/* 382:467 */       return FromElementType.this.queryableCollection.getCollectionType();
/* 383:    */     }
/* 384:    */     
/* 385:    */     private void validate(String propertyName)
/* 386:    */     {
/* 387:471 */       if ((!"index".equals(propertyName)) && (!"maxIndex".equals(propertyName)) && (!"minIndex".equals(propertyName))) {
/* 388:474 */         throw new IllegalArgumentException("Expecting index-related function call");
/* 389:    */       }
/* 390:    */     }
/* 391:    */     
/* 392:    */     public Type toType(String propertyName)
/* 393:    */       throws QueryException
/* 394:    */     {
/* 395:482 */       validate(propertyName);
/* 396:483 */       return FromElementType.this.queryableCollection.getIndexType();
/* 397:    */     }
/* 398:    */     
/* 399:    */     public String[] toColumns(String alias, String propertyName)
/* 400:    */       throws QueryException
/* 401:    */     {
/* 402:490 */       validate(propertyName);
/* 403:491 */       String joinTableAlias = FromElementType.this.joinSequence.getFirstJoin().getAlias();
/* 404:492 */       if ("index".equals(propertyName)) {
/* 405:493 */         return FromElementType.this.queryableCollection.toColumns(joinTableAlias, propertyName);
/* 406:    */       }
/* 407:496 */       String[] cols = FromElementType.this.queryableCollection.getIndexColumnNames(joinTableAlias);
/* 408:497 */       if ("minIndex".equals(propertyName))
/* 409:    */       {
/* 410:498 */         if (cols.length != 1) {
/* 411:499 */           throw new QueryException("composite collection index in minIndex()");
/* 412:    */         }
/* 413:501 */         return new String[] { "min(" + cols[0] + ')' };
/* 414:    */       }
/* 415:504 */       if (cols.length != 1) {
/* 416:505 */         throw new QueryException("composite collection index in maxIndex()");
/* 417:    */       }
/* 418:507 */       return new String[] { "max(" + cols[0] + ')' };
/* 419:    */     }
/* 420:    */     
/* 421:    */     public String[] toColumns(String propertyName)
/* 422:    */       throws QueryException, UnsupportedOperationException
/* 423:    */     {
/* 424:515 */       validate(propertyName);
/* 425:516 */       return FromElementType.this.queryableCollection.toColumns(propertyName);
/* 426:    */     }
/* 427:    */   }
/* 428:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.FromElementType
 * JD-Core Version:    0.7.0.1
 */