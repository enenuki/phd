/*   1:    */ package org.hibernate.hql.internal.ast.tree;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.LinkedList;
/*   5:    */ import java.util.List;
/*   6:    */ import org.hibernate.QueryException;
/*   7:    */ import org.hibernate.engine.internal.JoinSequence;
/*   8:    */ import org.hibernate.hql.internal.CollectionProperties;
/*   9:    */ import org.hibernate.hql.internal.ast.HqlSqlWalker;
/*  10:    */ import org.hibernate.hql.internal.ast.TypeDiscriminatorMetadata;
/*  11:    */ import org.hibernate.hql.internal.ast.util.ASTUtil;
/*  12:    */ import org.hibernate.internal.CoreMessageLogger;
/*  13:    */ import org.hibernate.internal.util.StringHelper;
/*  14:    */ import org.hibernate.param.ParameterSpecification;
/*  15:    */ import org.hibernate.persister.collection.QueryableCollection;
/*  16:    */ import org.hibernate.persister.entity.DiscriminatorMetadata;
/*  17:    */ import org.hibernate.persister.entity.EntityPersister;
/*  18:    */ import org.hibernate.persister.entity.PropertyMapping;
/*  19:    */ import org.hibernate.persister.entity.Queryable;
/*  20:    */ import org.hibernate.persister.entity.Queryable.Declarer;
/*  21:    */ import org.hibernate.tuple.entity.EntityMetamodel;
/*  22:    */ import org.hibernate.type.EntityType;
/*  23:    */ import org.hibernate.type.Type;
/*  24:    */ import org.jboss.logging.Logger;
/*  25:    */ 
/*  26:    */ public class FromElement
/*  27:    */   extends HqlSqlWalkerNode
/*  28:    */   implements DisplayableNode, ParameterContainer
/*  29:    */ {
/*  30: 66 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, FromElement.class.getName());
/*  31:    */   private String className;
/*  32:    */   private String classAlias;
/*  33:    */   private String tableAlias;
/*  34:    */   private String collectionTableAlias;
/*  35:    */   private FromClause fromClause;
/*  36: 73 */   private boolean includeSubclasses = true;
/*  37: 74 */   private boolean collectionJoin = false;
/*  38:    */   private FromElement origin;
/*  39:    */   private String[] columns;
/*  40:    */   private String role;
/*  41:    */   private boolean fetch;
/*  42:    */   private boolean isAllPropertyFetch;
/*  43: 80 */   private boolean filter = false;
/*  44: 81 */   private int sequence = -1;
/*  45: 82 */   private boolean useFromFragment = false;
/*  46: 83 */   private boolean initialized = false;
/*  47:    */   private FromElementType elementType;
/*  48: 85 */   private boolean useWhereFragment = true;
/*  49: 86 */   private List destinations = new LinkedList();
/*  50: 87 */   private boolean manyToMany = false;
/*  51: 88 */   private String withClauseFragment = null;
/*  52:    */   private String withClauseJoinAlias;
/*  53:    */   private boolean dereferencedBySuperclassProperty;
/*  54:    */   private boolean dereferencedBySubclassProperty;
/*  55:    */   public static final String DISCRIMINATOR_PROPERTY_NAME = "class";
/*  56:    */   private TypeDiscriminatorMetadata typeDiscriminatorMetadata;
/*  57:    */   private List embeddedParameters;
/*  58:    */   
/*  59:    */   public FromElement() {}
/*  60:    */   
/*  61:    */   protected FromElement(FromClause fromClause, FromElement origin, String alias)
/*  62:    */   {
/*  63:107 */     this.fromClause = fromClause;
/*  64:108 */     this.origin = origin;
/*  65:109 */     this.classAlias = alias;
/*  66:110 */     this.tableAlias = origin.getTableAlias();
/*  67:111 */     super.initialize(fromClause.getWalker());
/*  68:    */   }
/*  69:    */   
/*  70:    */   protected void initializeComponentJoin(FromElementType elementType)
/*  71:    */   {
/*  72:115 */     this.elementType = elementType;
/*  73:116 */     this.fromClause.registerFromElement(this);
/*  74:117 */     this.initialized = true;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public String getCollectionSuffix()
/*  78:    */   {
/*  79:121 */     return this.elementType.getCollectionSuffix();
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setCollectionSuffix(String suffix)
/*  83:    */   {
/*  84:125 */     this.elementType.setCollectionSuffix(suffix);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void initializeCollection(FromClause fromClause, String classAlias, String tableAlias)
/*  88:    */   {
/*  89:129 */     doInitialize(fromClause, tableAlias, null, classAlias, null, null);
/*  90:130 */     this.initialized = true;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void initializeEntity(FromClause fromClause, String className, EntityPersister persister, EntityType type, String classAlias, String tableAlias)
/*  94:    */   {
/*  95:140 */     doInitialize(fromClause, tableAlias, className, classAlias, persister, type);
/*  96:141 */     this.sequence = fromClause.nextFromElementCounter();
/*  97:142 */     this.initialized = true;
/*  98:    */   }
/*  99:    */   
/* 100:    */   private void doInitialize(FromClause fromClause, String tableAlias, String className, String classAlias, EntityPersister persister, EntityType type)
/* 101:    */   {
/* 102:147 */     if (this.initialized) {
/* 103:148 */       throw new IllegalStateException("Already initialized!!");
/* 104:    */     }
/* 105:150 */     this.fromClause = fromClause;
/* 106:151 */     this.tableAlias = tableAlias;
/* 107:152 */     this.className = className;
/* 108:153 */     this.classAlias = classAlias;
/* 109:154 */     this.elementType = new FromElementType(this, persister, type);
/* 110:    */     
/* 111:156 */     fromClause.registerFromElement(this);
/* 112:157 */     LOG.debugf("%s : %s (%s) -> %s", new Object[] { fromClause, className, classAlias == null ? "<no alias>" : classAlias, tableAlias });
/* 113:    */   }
/* 114:    */   
/* 115:    */   public EntityPersister getEntityPersister()
/* 116:    */   {
/* 117:161 */     return this.elementType.getEntityPersister();
/* 118:    */   }
/* 119:    */   
/* 120:    */   public Type getDataType()
/* 121:    */   {
/* 122:166 */     return this.elementType.getDataType();
/* 123:    */   }
/* 124:    */   
/* 125:    */   public Type getSelectType()
/* 126:    */   {
/* 127:170 */     return this.elementType.getSelectType();
/* 128:    */   }
/* 129:    */   
/* 130:    */   public Queryable getQueryable()
/* 131:    */   {
/* 132:174 */     return this.elementType.getQueryable();
/* 133:    */   }
/* 134:    */   
/* 135:    */   public String getClassName()
/* 136:    */   {
/* 137:178 */     return this.className;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public String getClassAlias()
/* 141:    */   {
/* 142:182 */     return this.classAlias;
/* 143:    */   }
/* 144:    */   
/* 145:    */   private String getTableName()
/* 146:    */   {
/* 147:187 */     Queryable queryable = getQueryable();
/* 148:188 */     return queryable != null ? queryable.getTableName() : "{none}";
/* 149:    */   }
/* 150:    */   
/* 151:    */   public String getTableAlias()
/* 152:    */   {
/* 153:192 */     return this.tableAlias;
/* 154:    */   }
/* 155:    */   
/* 156:    */   String renderScalarIdentifierSelect(int i)
/* 157:    */   {
/* 158:202 */     return this.elementType.renderScalarIdentifierSelect(i);
/* 159:    */   }
/* 160:    */   
/* 161:    */   void checkInitialized()
/* 162:    */   {
/* 163:206 */     if (!this.initialized) {
/* 164:207 */       throw new IllegalStateException("FromElement has not been initialized!");
/* 165:    */     }
/* 166:    */   }
/* 167:    */   
/* 168:    */   String renderIdentifierSelect(int size, int k)
/* 169:    */   {
/* 170:219 */     return this.elementType.renderIdentifierSelect(size, k);
/* 171:    */   }
/* 172:    */   
/* 173:    */   String renderPropertySelect(int size, int k)
/* 174:    */   {
/* 175:230 */     return this.elementType.renderPropertySelect(size, k, this.isAllPropertyFetch);
/* 176:    */   }
/* 177:    */   
/* 178:    */   String renderCollectionSelectFragment(int size, int k)
/* 179:    */   {
/* 180:234 */     return this.elementType.renderCollectionSelectFragment(size, k);
/* 181:    */   }
/* 182:    */   
/* 183:    */   String renderValueCollectionSelectFragment(int size, int k)
/* 184:    */   {
/* 185:238 */     return this.elementType.renderValueCollectionSelectFragment(size, k);
/* 186:    */   }
/* 187:    */   
/* 188:    */   public FromClause getFromClause()
/* 189:    */   {
/* 190:242 */     return this.fromClause;
/* 191:    */   }
/* 192:    */   
/* 193:    */   public boolean isImplied()
/* 194:    */   {
/* 195:252 */     return false;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public String getDisplayText()
/* 199:    */   {
/* 200:261 */     StringBuilder buf = new StringBuilder();
/* 201:262 */     buf.append("FromElement{");
/* 202:263 */     appendDisplayText(buf);
/* 203:264 */     buf.append("}");
/* 204:265 */     return buf.toString();
/* 205:    */   }
/* 206:    */   
/* 207:    */   protected void appendDisplayText(StringBuilder buf)
/* 208:    */   {
/* 209:269 */     buf.append(isImplied() ? "implied" : isImpliedInFromClause() ? "implied in FROM clause" : "explicit");
/* 210:    */     
/* 211:    */ 
/* 212:272 */     buf.append(",").append(isCollectionJoin() ? "collection join" : "not a collection join");
/* 213:273 */     buf.append(",").append(this.fetch ? "fetch join" : "not a fetch join");
/* 214:274 */     buf.append(",").append(this.isAllPropertyFetch ? "fetch all properties" : "fetch non-lazy properties");
/* 215:275 */     buf.append(",classAlias=").append(getClassAlias());
/* 216:276 */     buf.append(",role=").append(this.role);
/* 217:277 */     buf.append(",tableName=").append(getTableName());
/* 218:278 */     buf.append(",tableAlias=").append(getTableAlias());
/* 219:279 */     FromElement origin = getRealOrigin();
/* 220:280 */     buf.append(",origin=").append(origin == null ? "null" : origin.getText());
/* 221:281 */     buf.append(",columns={");
/* 222:282 */     if (this.columns != null) {
/* 223:283 */       for (int i = 0; i < this.columns.length; i++)
/* 224:    */       {
/* 225:284 */         buf.append(this.columns[i]);
/* 226:285 */         if (i < this.columns.length) {
/* 227:286 */           buf.append(" ");
/* 228:    */         }
/* 229:    */       }
/* 230:    */     }
/* 231:290 */     buf.append(",className=").append(this.className);
/* 232:291 */     buf.append("}");
/* 233:    */   }
/* 234:    */   
/* 235:    */   public int hashCode()
/* 236:    */   {
/* 237:296 */     return super.hashCode();
/* 238:    */   }
/* 239:    */   
/* 240:    */   public boolean equals(Object obj)
/* 241:    */   {
/* 242:301 */     return super.equals(obj);
/* 243:    */   }
/* 244:    */   
/* 245:    */   public void setJoinSequence(JoinSequence joinSequence)
/* 246:    */   {
/* 247:306 */     this.elementType.setJoinSequence(joinSequence);
/* 248:    */   }
/* 249:    */   
/* 250:    */   public JoinSequence getJoinSequence()
/* 251:    */   {
/* 252:310 */     return this.elementType.getJoinSequence();
/* 253:    */   }
/* 254:    */   
/* 255:    */   public void setIncludeSubclasses(boolean includeSubclasses)
/* 256:    */   {
/* 257:314 */     if ((LOG.isTraceEnabled()) && (isDereferencedBySuperclassOrSubclassProperty()) && (!includeSubclasses)) {
/* 258:315 */       LOG.trace("Attempt to disable subclass-inclusions : ", new Exception("Stack-trace source"));
/* 259:    */     }
/* 260:316 */     this.includeSubclasses = includeSubclasses;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public boolean isIncludeSubclasses()
/* 264:    */   {
/* 265:320 */     return this.includeSubclasses;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public boolean isDereferencedBySuperclassOrSubclassProperty()
/* 269:    */   {
/* 270:324 */     return (this.dereferencedBySubclassProperty) || (this.dereferencedBySuperclassProperty);
/* 271:    */   }
/* 272:    */   
/* 273:    */   public String getIdentityColumn()
/* 274:    */   {
/* 275:328 */     checkInitialized();
/* 276:329 */     String table = getTableAlias();
/* 277:330 */     if (table == null) {
/* 278:331 */       throw new IllegalStateException("No table alias for node " + this);
/* 279:    */     }
/* 280:    */     String propertyName;
/* 281:    */     String propertyName;
/* 282:335 */     if ((getEntityPersister() != null) && (getEntityPersister().getEntityMetamodel() != null) && (getEntityPersister().getEntityMetamodel().hasNonIdentifierPropertyNamedId())) {
/* 283:337 */       propertyName = getEntityPersister().getIdentifierPropertyName();
/* 284:    */     } else {
/* 285:340 */       propertyName = "id";
/* 286:    */     }
/* 287:    */     String[] cols;
/* 288:    */     String[] cols;
/* 289:342 */     if (getWalker().getStatementType() == 45) {
/* 290:343 */       cols = getPropertyMapping(propertyName).toColumns(table, propertyName);
/* 291:    */     } else {
/* 292:346 */       cols = getPropertyMapping(propertyName).toColumns(propertyName);
/* 293:    */     }
/* 294:348 */     String result = StringHelper.join(", ", cols);
/* 295:349 */     return "(" + result + ")";
/* 296:    */   }
/* 297:    */   
/* 298:    */   public void setCollectionJoin(boolean collectionJoin)
/* 299:    */   {
/* 300:353 */     this.collectionJoin = collectionJoin;
/* 301:    */   }
/* 302:    */   
/* 303:    */   public boolean isCollectionJoin()
/* 304:    */   {
/* 305:357 */     return this.collectionJoin;
/* 306:    */   }
/* 307:    */   
/* 308:    */   public void setRole(String role)
/* 309:    */   {
/* 310:361 */     this.role = role;
/* 311:    */   }
/* 312:    */   
/* 313:    */   public void setQueryableCollection(QueryableCollection queryableCollection)
/* 314:    */   {
/* 315:365 */     this.elementType.setQueryableCollection(queryableCollection);
/* 316:    */   }
/* 317:    */   
/* 318:    */   public QueryableCollection getQueryableCollection()
/* 319:    */   {
/* 320:369 */     return this.elementType.getQueryableCollection();
/* 321:    */   }
/* 322:    */   
/* 323:    */   public void setColumns(String[] columns)
/* 324:    */   {
/* 325:373 */     this.columns = columns;
/* 326:    */   }
/* 327:    */   
/* 328:    */   public void setOrigin(FromElement origin, boolean manyToMany)
/* 329:    */   {
/* 330:377 */     this.origin = origin;
/* 331:378 */     this.manyToMany = manyToMany;
/* 332:379 */     origin.addDestination(this);
/* 333:380 */     if (origin.getFromClause() == getFromClause())
/* 334:    */     {
/* 335:383 */       if (manyToMany) {
/* 336:384 */         ASTUtil.appendSibling(origin, this);
/* 337:387 */       } else if ((!getWalker().isInFrom()) && (!getWalker().isInSelect())) {
/* 338:388 */         getFromClause().addChild(this);
/* 339:    */       } else {
/* 340:391 */         origin.addChild(this);
/* 341:    */       }
/* 342:    */     }
/* 343:395 */     else if (!getWalker().isInFrom()) {
/* 344:398 */       getFromClause().addChild(this);
/* 345:    */     }
/* 346:    */   }
/* 347:    */   
/* 348:    */   public boolean isManyToMany()
/* 349:    */   {
/* 350:407 */     return this.manyToMany;
/* 351:    */   }
/* 352:    */   
/* 353:    */   private void addDestination(FromElement fromElement)
/* 354:    */   {
/* 355:411 */     this.destinations.add(fromElement);
/* 356:    */   }
/* 357:    */   
/* 358:    */   public List getDestinations()
/* 359:    */   {
/* 360:415 */     return this.destinations;
/* 361:    */   }
/* 362:    */   
/* 363:    */   public FromElement getOrigin()
/* 364:    */   {
/* 365:419 */     return this.origin;
/* 366:    */   }
/* 367:    */   
/* 368:    */   public FromElement getRealOrigin()
/* 369:    */   {
/* 370:423 */     if (this.origin == null) {
/* 371:424 */       return null;
/* 372:    */     }
/* 373:426 */     if ((this.origin.getText() == null) || ("".equals(this.origin.getText()))) {
/* 374:427 */       return this.origin.getRealOrigin();
/* 375:    */     }
/* 376:429 */     return this.origin;
/* 377:    */   }
/* 378:    */   
/* 379:    */   private static class TypeDiscriminatorMetadataImpl
/* 380:    */     implements TypeDiscriminatorMetadata
/* 381:    */   {
/* 382:    */     private final DiscriminatorMetadata persisterDiscriminatorMetadata;
/* 383:    */     private final String alias;
/* 384:    */     
/* 385:    */     private TypeDiscriminatorMetadataImpl(DiscriminatorMetadata persisterDiscriminatorMetadata, String alias)
/* 386:    */     {
/* 387:442 */       this.persisterDiscriminatorMetadata = persisterDiscriminatorMetadata;
/* 388:443 */       this.alias = alias;
/* 389:    */     }
/* 390:    */     
/* 391:    */     public String getSqlFragment()
/* 392:    */     {
/* 393:450 */       return this.persisterDiscriminatorMetadata.getSqlFragment(this.alias);
/* 394:    */     }
/* 395:    */     
/* 396:    */     public Type getResolutionType()
/* 397:    */     {
/* 398:457 */       return this.persisterDiscriminatorMetadata.getResolutionType();
/* 399:    */     }
/* 400:    */   }
/* 401:    */   
/* 402:    */   public TypeDiscriminatorMetadata getTypeDiscriminatorMetadata()
/* 403:    */   {
/* 404:462 */     if (this.typeDiscriminatorMetadata == null) {
/* 405:463 */       this.typeDiscriminatorMetadata = buildTypeDiscriminatorMetadata();
/* 406:    */     }
/* 407:465 */     return this.typeDiscriminatorMetadata;
/* 408:    */   }
/* 409:    */   
/* 410:    */   private TypeDiscriminatorMetadata buildTypeDiscriminatorMetadata()
/* 411:    */   {
/* 412:469 */     String aliasToUse = getTableAlias();
/* 413:470 */     Queryable queryable = getQueryable();
/* 414:471 */     if (queryable == null)
/* 415:    */     {
/* 416:472 */       QueryableCollection collection = getQueryableCollection();
/* 417:473 */       if (!collection.getElementType().isEntityType()) {
/* 418:474 */         throw new QueryException("type discrimination cannot be applied to value collection [" + collection.getRole() + "]");
/* 419:    */       }
/* 420:476 */       queryable = (Queryable)collection.getElementPersister();
/* 421:    */     }
/* 422:479 */     handlePropertyBeingDereferenced(getDataType(), "class");
/* 423:    */     
/* 424:481 */     return new TypeDiscriminatorMetadataImpl(queryable.getTypeDiscriminatorMetadata(), aliasToUse, null);
/* 425:    */   }
/* 426:    */   
/* 427:    */   public Type getPropertyType(String propertyName, String propertyPath)
/* 428:    */   {
/* 429:485 */     return this.elementType.getPropertyType(propertyName, propertyPath);
/* 430:    */   }
/* 431:    */   
/* 432:    */   public String[] toColumns(String tableAlias, String path, boolean inSelect)
/* 433:    */   {
/* 434:489 */     return this.elementType.toColumns(tableAlias, path, inSelect);
/* 435:    */   }
/* 436:    */   
/* 437:    */   public String[] toColumns(String tableAlias, String path, boolean inSelect, boolean forceAlias)
/* 438:    */   {
/* 439:493 */     return this.elementType.toColumns(tableAlias, path, inSelect, forceAlias);
/* 440:    */   }
/* 441:    */   
/* 442:    */   public PropertyMapping getPropertyMapping(String propertyName)
/* 443:    */   {
/* 444:497 */     return this.elementType.getPropertyMapping(propertyName);
/* 445:    */   }
/* 446:    */   
/* 447:    */   public void setFetch(boolean fetch)
/* 448:    */   {
/* 449:501 */     this.fetch = fetch;
/* 450:503 */     if ((fetch) && (getWalker().isShallowQuery())) {
/* 451:504 */       throw new QueryException("fetch may not be used with scroll() or iterate()");
/* 452:    */     }
/* 453:    */   }
/* 454:    */   
/* 455:    */   public boolean isFetch()
/* 456:    */   {
/* 457:509 */     return this.fetch;
/* 458:    */   }
/* 459:    */   
/* 460:    */   public int getSequence()
/* 461:    */   {
/* 462:513 */     return this.sequence;
/* 463:    */   }
/* 464:    */   
/* 465:    */   public void setFilter(boolean b)
/* 466:    */   {
/* 467:517 */     this.filter = b;
/* 468:    */   }
/* 469:    */   
/* 470:    */   public boolean isFilter()
/* 471:    */   {
/* 472:521 */     return this.filter;
/* 473:    */   }
/* 474:    */   
/* 475:    */   public boolean useFromFragment()
/* 476:    */   {
/* 477:525 */     checkInitialized();
/* 478:    */     
/* 479:527 */     return (!isImplied()) || (this.useFromFragment);
/* 480:    */   }
/* 481:    */   
/* 482:    */   public void setUseFromFragment(boolean useFromFragment)
/* 483:    */   {
/* 484:531 */     this.useFromFragment = useFromFragment;
/* 485:    */   }
/* 486:    */   
/* 487:    */   public boolean useWhereFragment()
/* 488:    */   {
/* 489:535 */     return this.useWhereFragment;
/* 490:    */   }
/* 491:    */   
/* 492:    */   public void setUseWhereFragment(boolean b)
/* 493:    */   {
/* 494:539 */     this.useWhereFragment = b;
/* 495:    */   }
/* 496:    */   
/* 497:    */   public void setCollectionTableAlias(String collectionTableAlias)
/* 498:    */   {
/* 499:544 */     this.collectionTableAlias = collectionTableAlias;
/* 500:    */   }
/* 501:    */   
/* 502:    */   public String getCollectionTableAlias()
/* 503:    */   {
/* 504:548 */     return this.collectionTableAlias;
/* 505:    */   }
/* 506:    */   
/* 507:    */   public boolean isCollectionOfValuesOrComponents()
/* 508:    */   {
/* 509:552 */     return this.elementType.isCollectionOfValuesOrComponents();
/* 510:    */   }
/* 511:    */   
/* 512:    */   public boolean isEntity()
/* 513:    */   {
/* 514:556 */     return this.elementType.isEntity();
/* 515:    */   }
/* 516:    */   
/* 517:    */   public void setImpliedInFromClause(boolean flag)
/* 518:    */   {
/* 519:560 */     throw new UnsupportedOperationException("Explicit FROM elements can't be implied in the FROM clause!");
/* 520:    */   }
/* 521:    */   
/* 522:    */   public boolean isImpliedInFromClause()
/* 523:    */   {
/* 524:564 */     return false;
/* 525:    */   }
/* 526:    */   
/* 527:    */   public void setInProjectionList(boolean inProjectionList) {}
/* 528:    */   
/* 529:    */   public boolean inProjectionList()
/* 530:    */   {
/* 531:572 */     return (!isImplied()) && (isFromOrJoinFragment());
/* 532:    */   }
/* 533:    */   
/* 534:    */   public boolean isFromOrJoinFragment()
/* 535:    */   {
/* 536:576 */     return (getType() == 134) || (getType() == 136);
/* 537:    */   }
/* 538:    */   
/* 539:    */   public boolean isAllPropertyFetch()
/* 540:    */   {
/* 541:580 */     return this.isAllPropertyFetch;
/* 542:    */   }
/* 543:    */   
/* 544:    */   public void setAllPropertyFetch(boolean fetch)
/* 545:    */   {
/* 546:584 */     this.isAllPropertyFetch = fetch;
/* 547:    */   }
/* 548:    */   
/* 549:    */   public String getWithClauseFragment()
/* 550:    */   {
/* 551:588 */     return this.withClauseFragment;
/* 552:    */   }
/* 553:    */   
/* 554:    */   public String getWithClauseJoinAlias()
/* 555:    */   {
/* 556:592 */     return this.withClauseJoinAlias;
/* 557:    */   }
/* 558:    */   
/* 559:    */   public void setWithClauseFragment(String withClauseJoinAlias, String withClauseFragment)
/* 560:    */   {
/* 561:596 */     this.withClauseJoinAlias = withClauseJoinAlias;
/* 562:597 */     this.withClauseFragment = withClauseFragment;
/* 563:    */   }
/* 564:    */   
/* 565:    */   public boolean hasCacheablePersister()
/* 566:    */   {
/* 567:601 */     if (getQueryableCollection() != null) {
/* 568:602 */       return getQueryableCollection().hasCache();
/* 569:    */     }
/* 570:605 */     return getQueryable().hasCache();
/* 571:    */   }
/* 572:    */   
/* 573:    */   public void handlePropertyBeingDereferenced(Type propertySource, String propertyName)
/* 574:    */   {
/* 575:610 */     if ((getQueryableCollection() != null) && (CollectionProperties.isCollectionProperty(propertyName))) {
/* 576:612 */       return;
/* 577:    */     }
/* 578:614 */     if (propertySource.isComponentType()) {
/* 579:616 */       return;
/* 580:    */     }
/* 581:619 */     Queryable persister = getQueryable();
/* 582:620 */     if (persister != null) {
/* 583:    */       try
/* 584:    */       {
/* 585:622 */         Queryable.Declarer propertyDeclarer = persister.getSubclassPropertyDeclarer(propertyName);
/* 586:623 */         if (LOG.isTraceEnabled()) {
/* 587:624 */           LOG.tracev("Handling property dereference [{0} ({1}) -> {2} ({3})]", new Object[] { persister.getEntityName(), getClassAlias(), propertyName, propertyDeclarer });
/* 588:    */         }
/* 589:627 */         if (propertyDeclarer == Queryable.Declarer.SUBCLASS)
/* 590:    */         {
/* 591:628 */           this.dereferencedBySubclassProperty = true;
/* 592:629 */           this.includeSubclasses = true;
/* 593:    */         }
/* 594:631 */         else if (propertyDeclarer == Queryable.Declarer.SUPERCLASS)
/* 595:    */         {
/* 596:632 */           this.dereferencedBySuperclassProperty = true;
/* 597:    */         }
/* 598:    */       }
/* 599:    */       catch (QueryException ignore) {}
/* 600:    */     }
/* 601:    */   }
/* 602:    */   
/* 603:    */   public boolean isDereferencedBySuperclassProperty()
/* 604:    */   {
/* 605:645 */     return this.dereferencedBySuperclassProperty;
/* 606:    */   }
/* 607:    */   
/* 608:    */   public boolean isDereferencedBySubclassProperty()
/* 609:    */   {
/* 610:649 */     return this.dereferencedBySubclassProperty;
/* 611:    */   }
/* 612:    */   
/* 613:    */   public void addEmbeddedParameter(ParameterSpecification specification)
/* 614:    */   {
/* 615:657 */     if (this.embeddedParameters == null) {
/* 616:658 */       this.embeddedParameters = new ArrayList();
/* 617:    */     }
/* 618:660 */     this.embeddedParameters.add(specification);
/* 619:    */   }
/* 620:    */   
/* 621:    */   public boolean hasEmbeddedParameters()
/* 622:    */   {
/* 623:664 */     return (this.embeddedParameters != null) && (!this.embeddedParameters.isEmpty());
/* 624:    */   }
/* 625:    */   
/* 626:    */   public ParameterSpecification[] getEmbeddedParameters()
/* 627:    */   {
/* 628:668 */     return (ParameterSpecification[])this.embeddedParameters.toArray(new ParameterSpecification[this.embeddedParameters.size()]);
/* 629:    */   }
/* 630:    */   
/* 631:    */   public ParameterSpecification getIndexCollectionSelectorParamSpec()
/* 632:    */   {
/* 633:672 */     return this.elementType.getIndexCollectionSelectorParamSpec();
/* 634:    */   }
/* 635:    */   
/* 636:    */   public void setIndexCollectionSelectorParamSpec(ParameterSpecification indexCollectionSelectorParamSpec)
/* 637:    */   {
/* 638:676 */     if (indexCollectionSelectorParamSpec == null)
/* 639:    */     {
/* 640:677 */       if (this.elementType.getIndexCollectionSelectorParamSpec() != null)
/* 641:    */       {
/* 642:678 */         this.embeddedParameters.remove(this.elementType.getIndexCollectionSelectorParamSpec());
/* 643:679 */         this.elementType.setIndexCollectionSelectorParamSpec(null);
/* 644:    */       }
/* 645:    */     }
/* 646:    */     else
/* 647:    */     {
/* 648:683 */       this.elementType.setIndexCollectionSelectorParamSpec(indexCollectionSelectorParamSpec);
/* 649:684 */       addEmbeddedParameter(indexCollectionSelectorParamSpec);
/* 650:    */     }
/* 651:    */   }
/* 652:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.hql.internal.ast.tree.FromElement
 * JD-Core Version:    0.7.0.1
 */