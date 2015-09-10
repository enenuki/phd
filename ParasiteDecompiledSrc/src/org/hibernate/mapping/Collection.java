/*   1:    */ package org.hibernate.mapping;
/*   2:    */ 
/*   3:    */ import java.util.Comparator;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.HashSet;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Properties;
/*   9:    */ import java.util.Set;
/*  10:    */ import org.hibernate.FetchMode;
/*  11:    */ import org.hibernate.MappingException;
/*  12:    */ import org.hibernate.cfg.Mappings;
/*  13:    */ import org.hibernate.engine.spi.ExecuteUpdateResultCheckStyle;
/*  14:    */ import org.hibernate.engine.spi.Mapping;
/*  15:    */ import org.hibernate.internal.util.ReflectHelper;
/*  16:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*  17:    */ import org.hibernate.internal.util.collections.EmptyIterator;
/*  18:    */ import org.hibernate.type.CollectionType;
/*  19:    */ import org.hibernate.type.Type;
/*  20:    */ import org.hibernate.type.TypeFactory;
/*  21:    */ import org.hibernate.type.TypeResolver;
/*  22:    */ 
/*  23:    */ public abstract class Collection
/*  24:    */   implements Fetchable, Value, Filterable
/*  25:    */ {
/*  26:    */   public static final String DEFAULT_ELEMENT_COLUMN_NAME = "elt";
/*  27:    */   public static final String DEFAULT_KEY_COLUMN_NAME = "id";
/*  28:    */   private final Mappings mappings;
/*  29:    */   private PersistentClass owner;
/*  30:    */   private KeyValue key;
/*  31:    */   private Value element;
/*  32:    */   private Table collectionTable;
/*  33:    */   private String role;
/*  34:    */   private boolean lazy;
/*  35:    */   private boolean extraLazy;
/*  36:    */   private boolean inverse;
/*  37: 62 */   private boolean mutable = true;
/*  38:    */   private boolean subselectLoadable;
/*  39:    */   private String cacheConcurrencyStrategy;
/*  40:    */   private String cacheRegionName;
/*  41:    */   private String orderBy;
/*  42:    */   private String where;
/*  43:    */   private String manyToManyWhere;
/*  44:    */   private String manyToManyOrderBy;
/*  45:    */   private String referencedPropertyName;
/*  46:    */   private String nodeName;
/*  47:    */   private String elementNodeName;
/*  48:    */   private boolean sorted;
/*  49:    */   private Comparator comparator;
/*  50:    */   private String comparatorClassName;
/*  51:    */   private boolean orphanDelete;
/*  52: 77 */   private int batchSize = -1;
/*  53:    */   private FetchMode fetchMode;
/*  54: 79 */   private boolean embedded = true;
/*  55: 80 */   private boolean optimisticLocked = true;
/*  56:    */   private Class collectionPersisterClass;
/*  57:    */   private String typeName;
/*  58:    */   private Properties typeParameters;
/*  59: 84 */   private final Map filters = new HashMap();
/*  60: 85 */   private final Map manyToManyFilters = new HashMap();
/*  61: 86 */   private final Set synchronizedTables = new HashSet();
/*  62:    */   private String customSQLInsert;
/*  63:    */   private boolean customInsertCallable;
/*  64:    */   private ExecuteUpdateResultCheckStyle insertCheckStyle;
/*  65:    */   private String customSQLUpdate;
/*  66:    */   private boolean customUpdateCallable;
/*  67:    */   private ExecuteUpdateResultCheckStyle updateCheckStyle;
/*  68:    */   private String customSQLDelete;
/*  69:    */   private boolean customDeleteCallable;
/*  70:    */   private ExecuteUpdateResultCheckStyle deleteCheckStyle;
/*  71:    */   private String customSQLDeleteAll;
/*  72:    */   private boolean customDeleteAllCallable;
/*  73:    */   private ExecuteUpdateResultCheckStyle deleteAllCheckStyle;
/*  74:    */   private String loaderName;
/*  75:    */   
/*  76:    */   protected Collection(Mappings mappings, PersistentClass owner)
/*  77:    */   {
/*  78:104 */     this.mappings = mappings;
/*  79:105 */     this.owner = owner;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public Mappings getMappings()
/*  83:    */   {
/*  84:109 */     return this.mappings;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public boolean isSet()
/*  88:    */   {
/*  89:113 */     return false;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public KeyValue getKey()
/*  93:    */   {
/*  94:117 */     return this.key;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public Value getElement()
/*  98:    */   {
/*  99:121 */     return this.element;
/* 100:    */   }
/* 101:    */   
/* 102:    */   public boolean isIndexed()
/* 103:    */   {
/* 104:125 */     return false;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public Table getCollectionTable()
/* 108:    */   {
/* 109:129 */     return this.collectionTable;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setCollectionTable(Table table)
/* 113:    */   {
/* 114:133 */     this.collectionTable = table;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public boolean isSorted()
/* 118:    */   {
/* 119:137 */     return this.sorted;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public Comparator getComparator()
/* 123:    */   {
/* 124:141 */     if ((this.comparator == null) && (this.comparatorClassName != null)) {
/* 125:    */       try
/* 126:    */       {
/* 127:143 */         setComparator((Comparator)ReflectHelper.classForName(this.comparatorClassName).newInstance());
/* 128:    */       }
/* 129:    */       catch (Exception e)
/* 130:    */       {
/* 131:146 */         throw new MappingException("Could not instantiate comparator class [" + this.comparatorClassName + "] for collection " + getRole());
/* 132:    */       }
/* 133:    */     }
/* 134:152 */     return this.comparator;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public boolean isLazy()
/* 138:    */   {
/* 139:156 */     return this.lazy;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setLazy(boolean lazy)
/* 143:    */   {
/* 144:160 */     this.lazy = lazy;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public String getRole()
/* 148:    */   {
/* 149:164 */     return this.role;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public abstract CollectionType getDefaultCollectionType()
/* 153:    */     throws MappingException;
/* 154:    */   
/* 155:    */   public boolean isPrimitiveArray()
/* 156:    */   {
/* 157:170 */     return false;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public boolean isArray()
/* 161:    */   {
/* 162:174 */     return false;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public boolean hasFormula()
/* 166:    */   {
/* 167:178 */     return false;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public boolean isOneToMany()
/* 171:    */   {
/* 172:182 */     return this.element instanceof OneToMany;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public boolean isInverse()
/* 176:    */   {
/* 177:186 */     return this.inverse;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public String getOwnerEntityName()
/* 181:    */   {
/* 182:190 */     return this.owner.getEntityName();
/* 183:    */   }
/* 184:    */   
/* 185:    */   public String getOrderBy()
/* 186:    */   {
/* 187:194 */     return this.orderBy;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void setComparator(Comparator comparator)
/* 191:    */   {
/* 192:198 */     this.comparator = comparator;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setElement(Value element)
/* 196:    */   {
/* 197:202 */     this.element = element;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void setKey(KeyValue key)
/* 201:    */   {
/* 202:206 */     this.key = key;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void setOrderBy(String orderBy)
/* 206:    */   {
/* 207:210 */     this.orderBy = orderBy;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public void setRole(String role)
/* 211:    */   {
/* 212:214 */     this.role = (role == null ? null : role.intern());
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void setSorted(boolean sorted)
/* 216:    */   {
/* 217:218 */     this.sorted = sorted;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void setInverse(boolean inverse)
/* 221:    */   {
/* 222:222 */     this.inverse = inverse;
/* 223:    */   }
/* 224:    */   
/* 225:    */   public PersistentClass getOwner()
/* 226:    */   {
/* 227:226 */     return this.owner;
/* 228:    */   }
/* 229:    */   
/* 230:    */   @Deprecated
/* 231:    */   public void setOwner(PersistentClass owner)
/* 232:    */   {
/* 233:236 */     this.owner = owner;
/* 234:    */   }
/* 235:    */   
/* 236:    */   public String getWhere()
/* 237:    */   {
/* 238:240 */     return this.where;
/* 239:    */   }
/* 240:    */   
/* 241:    */   public void setWhere(String where)
/* 242:    */   {
/* 243:244 */     this.where = where;
/* 244:    */   }
/* 245:    */   
/* 246:    */   public String getManyToManyWhere()
/* 247:    */   {
/* 248:248 */     return this.manyToManyWhere;
/* 249:    */   }
/* 250:    */   
/* 251:    */   public void setManyToManyWhere(String manyToManyWhere)
/* 252:    */   {
/* 253:252 */     this.manyToManyWhere = manyToManyWhere;
/* 254:    */   }
/* 255:    */   
/* 256:    */   public String getManyToManyOrdering()
/* 257:    */   {
/* 258:256 */     return this.manyToManyOrderBy;
/* 259:    */   }
/* 260:    */   
/* 261:    */   public void setManyToManyOrdering(String orderFragment)
/* 262:    */   {
/* 263:260 */     this.manyToManyOrderBy = orderFragment;
/* 264:    */   }
/* 265:    */   
/* 266:    */   public boolean isIdentified()
/* 267:    */   {
/* 268:264 */     return false;
/* 269:    */   }
/* 270:    */   
/* 271:    */   public boolean hasOrphanDelete()
/* 272:    */   {
/* 273:268 */     return this.orphanDelete;
/* 274:    */   }
/* 275:    */   
/* 276:    */   public void setOrphanDelete(boolean orphanDelete)
/* 277:    */   {
/* 278:272 */     this.orphanDelete = orphanDelete;
/* 279:    */   }
/* 280:    */   
/* 281:    */   public int getBatchSize()
/* 282:    */   {
/* 283:276 */     return this.batchSize;
/* 284:    */   }
/* 285:    */   
/* 286:    */   public void setBatchSize(int i)
/* 287:    */   {
/* 288:280 */     this.batchSize = i;
/* 289:    */   }
/* 290:    */   
/* 291:    */   public FetchMode getFetchMode()
/* 292:    */   {
/* 293:284 */     return this.fetchMode;
/* 294:    */   }
/* 295:    */   
/* 296:    */   public void setFetchMode(FetchMode fetchMode)
/* 297:    */   {
/* 298:288 */     this.fetchMode = fetchMode;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public void setCollectionPersisterClass(Class persister)
/* 302:    */   {
/* 303:292 */     this.collectionPersisterClass = persister;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public Class getCollectionPersisterClass()
/* 307:    */   {
/* 308:296 */     return this.collectionPersisterClass;
/* 309:    */   }
/* 310:    */   
/* 311:    */   public void validate(Mapping mapping)
/* 312:    */     throws MappingException
/* 313:    */   {
/* 314:300 */     if ((getKey().isCascadeDeleteEnabled()) && ((!isInverse()) || (!isOneToMany()))) {
/* 315:301 */       throw new MappingException("only inverse one-to-many associations may use on-delete=\"cascade\": " + getRole());
/* 316:    */     }
/* 317:305 */     if (!getKey().isValid(mapping)) {
/* 318:306 */       throw new MappingException("collection foreign key mapping has wrong number of columns: " + getRole() + " type: " + getKey().getType().getName());
/* 319:    */     }
/* 320:312 */     if (!getElement().isValid(mapping)) {
/* 321:313 */       throw new MappingException("collection element mapping has wrong number of columns: " + getRole() + " type: " + getElement().getType().getName());
/* 322:    */     }
/* 323:320 */     checkColumnDuplication();
/* 324:322 */     if ((this.elementNodeName != null) && (this.elementNodeName.startsWith("@"))) {
/* 325:323 */       throw new MappingException("element node must not be an attribute: " + this.elementNodeName);
/* 326:    */     }
/* 327:325 */     if ((this.elementNodeName != null) && (this.elementNodeName.equals("."))) {
/* 328:326 */       throw new MappingException("element node must not be the parent: " + this.elementNodeName);
/* 329:    */     }
/* 330:328 */     if ((this.nodeName != null) && (this.nodeName.indexOf('@') > -1)) {
/* 331:329 */       throw new MappingException("collection node must not be an attribute: " + this.elementNodeName);
/* 332:    */     }
/* 333:    */   }
/* 334:    */   
/* 335:    */   private void checkColumnDuplication(Set distinctColumns, Iterator columns)
/* 336:    */     throws MappingException
/* 337:    */   {
/* 338:335 */     while (columns.hasNext())
/* 339:    */     {
/* 340:336 */       Selectable s = (Selectable)columns.next();
/* 341:337 */       if (!s.isFormula())
/* 342:    */       {
/* 343:338 */         Column col = (Column)s;
/* 344:339 */         if (!distinctColumns.add(col.getName())) {
/* 345:340 */           throw new MappingException("Repeated column in mapping for collection: " + getRole() + " column: " + col.getName());
/* 346:    */         }
/* 347:    */       }
/* 348:    */     }
/* 349:    */   }
/* 350:    */   
/* 351:    */   private void checkColumnDuplication()
/* 352:    */     throws MappingException
/* 353:    */   {
/* 354:350 */     HashSet cols = new HashSet();
/* 355:351 */     checkColumnDuplication(cols, getKey().getColumnIterator());
/* 356:352 */     if (isIndexed()) {
/* 357:353 */       checkColumnDuplication(cols, ((IndexedCollection)this).getIndex().getColumnIterator());
/* 358:    */     }
/* 359:357 */     if (isIdentified()) {
/* 360:358 */       checkColumnDuplication(cols, ((IdentifierCollection)this).getIdentifier().getColumnIterator());
/* 361:    */     }
/* 362:362 */     if (!isOneToMany()) {
/* 363:363 */       checkColumnDuplication(cols, getElement().getColumnIterator());
/* 364:    */     }
/* 365:    */   }
/* 366:    */   
/* 367:    */   public Iterator getColumnIterator()
/* 368:    */   {
/* 369:368 */     return EmptyIterator.INSTANCE;
/* 370:    */   }
/* 371:    */   
/* 372:    */   public int getColumnSpan()
/* 373:    */   {
/* 374:372 */     return 0;
/* 375:    */   }
/* 376:    */   
/* 377:    */   public Type getType()
/* 378:    */     throws MappingException
/* 379:    */   {
/* 380:376 */     return getCollectionType();
/* 381:    */   }
/* 382:    */   
/* 383:    */   public CollectionType getCollectionType()
/* 384:    */   {
/* 385:380 */     if (this.typeName == null) {
/* 386:381 */       return getDefaultCollectionType();
/* 387:    */     }
/* 388:384 */     return this.mappings.getTypeResolver().getTypeFactory().customCollection(this.typeName, this.typeParameters, this.role, this.referencedPropertyName, isEmbedded());
/* 389:    */   }
/* 390:    */   
/* 391:    */   public boolean isNullable()
/* 392:    */   {
/* 393:391 */     return true;
/* 394:    */   }
/* 395:    */   
/* 396:    */   public boolean isAlternateUniqueKey()
/* 397:    */   {
/* 398:395 */     return false;
/* 399:    */   }
/* 400:    */   
/* 401:    */   public Table getTable()
/* 402:    */   {
/* 403:399 */     return this.owner.getTable();
/* 404:    */   }
/* 405:    */   
/* 406:    */   public void createForeignKey() {}
/* 407:    */   
/* 408:    */   public boolean isSimpleValue()
/* 409:    */   {
/* 410:406 */     return false;
/* 411:    */   }
/* 412:    */   
/* 413:    */   public boolean isValid(Mapping mapping)
/* 414:    */     throws MappingException
/* 415:    */   {
/* 416:410 */     return true;
/* 417:    */   }
/* 418:    */   
/* 419:    */   private void createForeignKeys()
/* 420:    */     throws MappingException
/* 421:    */   {
/* 422:415 */     if (this.referencedPropertyName == null)
/* 423:    */     {
/* 424:416 */       getElement().createForeignKey();
/* 425:417 */       this.key.createForeignKeyOfEntity(getOwner().getEntityName());
/* 426:    */     }
/* 427:    */   }
/* 428:    */   
/* 429:    */   abstract void createPrimaryKey();
/* 430:    */   
/* 431:    */   public void createAllKeys()
/* 432:    */     throws MappingException
/* 433:    */   {
/* 434:425 */     createForeignKeys();
/* 435:426 */     if (!isInverse()) {
/* 436:426 */       createPrimaryKey();
/* 437:    */     }
/* 438:    */   }
/* 439:    */   
/* 440:    */   public String getCacheConcurrencyStrategy()
/* 441:    */   {
/* 442:430 */     return this.cacheConcurrencyStrategy;
/* 443:    */   }
/* 444:    */   
/* 445:    */   public void setCacheConcurrencyStrategy(String cacheConcurrencyStrategy)
/* 446:    */   {
/* 447:434 */     this.cacheConcurrencyStrategy = cacheConcurrencyStrategy;
/* 448:    */   }
/* 449:    */   
/* 450:    */   public void setTypeUsingReflection(String className, String propertyName) {}
/* 451:    */   
/* 452:    */   public String getCacheRegionName()
/* 453:    */   {
/* 454:441 */     return this.cacheRegionName == null ? this.role : this.cacheRegionName;
/* 455:    */   }
/* 456:    */   
/* 457:    */   public void setCacheRegionName(String cacheRegionName)
/* 458:    */   {
/* 459:445 */     this.cacheRegionName = cacheRegionName;
/* 460:    */   }
/* 461:    */   
/* 462:    */   public void setCustomSQLInsert(String customSQLInsert, boolean callable, ExecuteUpdateResultCheckStyle checkStyle)
/* 463:    */   {
/* 464:451 */     this.customSQLInsert = customSQLInsert;
/* 465:452 */     this.customInsertCallable = callable;
/* 466:453 */     this.insertCheckStyle = checkStyle;
/* 467:    */   }
/* 468:    */   
/* 469:    */   public String getCustomSQLInsert()
/* 470:    */   {
/* 471:457 */     return this.customSQLInsert;
/* 472:    */   }
/* 473:    */   
/* 474:    */   public boolean isCustomInsertCallable()
/* 475:    */   {
/* 476:461 */     return this.customInsertCallable;
/* 477:    */   }
/* 478:    */   
/* 479:    */   public ExecuteUpdateResultCheckStyle getCustomSQLInsertCheckStyle()
/* 480:    */   {
/* 481:465 */     return this.insertCheckStyle;
/* 482:    */   }
/* 483:    */   
/* 484:    */   public void setCustomSQLUpdate(String customSQLUpdate, boolean callable, ExecuteUpdateResultCheckStyle checkStyle)
/* 485:    */   {
/* 486:469 */     this.customSQLUpdate = customSQLUpdate;
/* 487:470 */     this.customUpdateCallable = callable;
/* 488:471 */     this.updateCheckStyle = checkStyle;
/* 489:    */   }
/* 490:    */   
/* 491:    */   public String getCustomSQLUpdate()
/* 492:    */   {
/* 493:475 */     return this.customSQLUpdate;
/* 494:    */   }
/* 495:    */   
/* 496:    */   public boolean isCustomUpdateCallable()
/* 497:    */   {
/* 498:479 */     return this.customUpdateCallable;
/* 499:    */   }
/* 500:    */   
/* 501:    */   public ExecuteUpdateResultCheckStyle getCustomSQLUpdateCheckStyle()
/* 502:    */   {
/* 503:483 */     return this.updateCheckStyle;
/* 504:    */   }
/* 505:    */   
/* 506:    */   public void setCustomSQLDelete(String customSQLDelete, boolean callable, ExecuteUpdateResultCheckStyle checkStyle)
/* 507:    */   {
/* 508:487 */     this.customSQLDelete = customSQLDelete;
/* 509:488 */     this.customDeleteCallable = callable;
/* 510:489 */     this.deleteCheckStyle = checkStyle;
/* 511:    */   }
/* 512:    */   
/* 513:    */   public String getCustomSQLDelete()
/* 514:    */   {
/* 515:493 */     return this.customSQLDelete;
/* 516:    */   }
/* 517:    */   
/* 518:    */   public boolean isCustomDeleteCallable()
/* 519:    */   {
/* 520:497 */     return this.customDeleteCallable;
/* 521:    */   }
/* 522:    */   
/* 523:    */   public ExecuteUpdateResultCheckStyle getCustomSQLDeleteCheckStyle()
/* 524:    */   {
/* 525:501 */     return this.deleteCheckStyle;
/* 526:    */   }
/* 527:    */   
/* 528:    */   public void setCustomSQLDeleteAll(String customSQLDeleteAll, boolean callable, ExecuteUpdateResultCheckStyle checkStyle)
/* 529:    */   {
/* 530:505 */     this.customSQLDeleteAll = customSQLDeleteAll;
/* 531:506 */     this.customDeleteAllCallable = callable;
/* 532:507 */     this.deleteAllCheckStyle = checkStyle;
/* 533:    */   }
/* 534:    */   
/* 535:    */   public String getCustomSQLDeleteAll()
/* 536:    */   {
/* 537:511 */     return this.customSQLDeleteAll;
/* 538:    */   }
/* 539:    */   
/* 540:    */   public boolean isCustomDeleteAllCallable()
/* 541:    */   {
/* 542:515 */     return this.customDeleteAllCallable;
/* 543:    */   }
/* 544:    */   
/* 545:    */   public ExecuteUpdateResultCheckStyle getCustomSQLDeleteAllCheckStyle()
/* 546:    */   {
/* 547:519 */     return this.deleteAllCheckStyle;
/* 548:    */   }
/* 549:    */   
/* 550:    */   public void addFilter(String name, String condition)
/* 551:    */   {
/* 552:523 */     this.filters.put(name, condition);
/* 553:    */   }
/* 554:    */   
/* 555:    */   public Map getFilterMap()
/* 556:    */   {
/* 557:527 */     return this.filters;
/* 558:    */   }
/* 559:    */   
/* 560:    */   public void addManyToManyFilter(String name, String condition)
/* 561:    */   {
/* 562:531 */     this.manyToManyFilters.put(name, condition);
/* 563:    */   }
/* 564:    */   
/* 565:    */   public Map getManyToManyFilterMap()
/* 566:    */   {
/* 567:535 */     return this.manyToManyFilters;
/* 568:    */   }
/* 569:    */   
/* 570:    */   public String toString()
/* 571:    */   {
/* 572:540 */     return getClass().getName() + '(' + getRole() + ')';
/* 573:    */   }
/* 574:    */   
/* 575:    */   public Set getSynchronizedTables()
/* 576:    */   {
/* 577:544 */     return this.synchronizedTables;
/* 578:    */   }
/* 579:    */   
/* 580:    */   public String getLoaderName()
/* 581:    */   {
/* 582:548 */     return this.loaderName;
/* 583:    */   }
/* 584:    */   
/* 585:    */   public void setLoaderName(String name)
/* 586:    */   {
/* 587:552 */     this.loaderName = (name == null ? null : name.intern());
/* 588:    */   }
/* 589:    */   
/* 590:    */   public String getReferencedPropertyName()
/* 591:    */   {
/* 592:556 */     return this.referencedPropertyName;
/* 593:    */   }
/* 594:    */   
/* 595:    */   public void setReferencedPropertyName(String propertyRef)
/* 596:    */   {
/* 597:560 */     this.referencedPropertyName = (propertyRef == null ? null : propertyRef.intern());
/* 598:    */   }
/* 599:    */   
/* 600:    */   public boolean isOptimisticLocked()
/* 601:    */   {
/* 602:564 */     return this.optimisticLocked;
/* 603:    */   }
/* 604:    */   
/* 605:    */   public void setOptimisticLocked(boolean optimisticLocked)
/* 606:    */   {
/* 607:568 */     this.optimisticLocked = optimisticLocked;
/* 608:    */   }
/* 609:    */   
/* 610:    */   public boolean isMap()
/* 611:    */   {
/* 612:572 */     return false;
/* 613:    */   }
/* 614:    */   
/* 615:    */   public String getTypeName()
/* 616:    */   {
/* 617:576 */     return this.typeName;
/* 618:    */   }
/* 619:    */   
/* 620:    */   public void setTypeName(String typeName)
/* 621:    */   {
/* 622:580 */     this.typeName = typeName;
/* 623:    */   }
/* 624:    */   
/* 625:    */   public Properties getTypeParameters()
/* 626:    */   {
/* 627:584 */     return this.typeParameters;
/* 628:    */   }
/* 629:    */   
/* 630:    */   public void setTypeParameters(Properties parameterMap)
/* 631:    */   {
/* 632:588 */     this.typeParameters = parameterMap;
/* 633:    */   }
/* 634:    */   
/* 635:    */   public boolean[] getColumnInsertability()
/* 636:    */   {
/* 637:592 */     return ArrayHelper.EMPTY_BOOLEAN_ARRAY;
/* 638:    */   }
/* 639:    */   
/* 640:    */   public boolean[] getColumnUpdateability()
/* 641:    */   {
/* 642:596 */     return ArrayHelper.EMPTY_BOOLEAN_ARRAY;
/* 643:    */   }
/* 644:    */   
/* 645:    */   public String getNodeName()
/* 646:    */   {
/* 647:600 */     return this.nodeName;
/* 648:    */   }
/* 649:    */   
/* 650:    */   public void setNodeName(String nodeName)
/* 651:    */   {
/* 652:604 */     this.nodeName = nodeName;
/* 653:    */   }
/* 654:    */   
/* 655:    */   public String getElementNodeName()
/* 656:    */   {
/* 657:608 */     return this.elementNodeName;
/* 658:    */   }
/* 659:    */   
/* 660:    */   public void setElementNodeName(String elementNodeName)
/* 661:    */   {
/* 662:612 */     this.elementNodeName = elementNodeName;
/* 663:    */   }
/* 664:    */   
/* 665:    */   public boolean isEmbedded()
/* 666:    */   {
/* 667:616 */     return this.embedded;
/* 668:    */   }
/* 669:    */   
/* 670:    */   public void setEmbedded(boolean embedded)
/* 671:    */   {
/* 672:620 */     this.embedded = embedded;
/* 673:    */   }
/* 674:    */   
/* 675:    */   public boolean isSubselectLoadable()
/* 676:    */   {
/* 677:624 */     return this.subselectLoadable;
/* 678:    */   }
/* 679:    */   
/* 680:    */   public void setSubselectLoadable(boolean subqueryLoadable)
/* 681:    */   {
/* 682:629 */     this.subselectLoadable = subqueryLoadable;
/* 683:    */   }
/* 684:    */   
/* 685:    */   public boolean isMutable()
/* 686:    */   {
/* 687:633 */     return this.mutable;
/* 688:    */   }
/* 689:    */   
/* 690:    */   public void setMutable(boolean mutable)
/* 691:    */   {
/* 692:637 */     this.mutable = mutable;
/* 693:    */   }
/* 694:    */   
/* 695:    */   public boolean isExtraLazy()
/* 696:    */   {
/* 697:641 */     return this.extraLazy;
/* 698:    */   }
/* 699:    */   
/* 700:    */   public void setExtraLazy(boolean extraLazy)
/* 701:    */   {
/* 702:645 */     this.extraLazy = extraLazy;
/* 703:    */   }
/* 704:    */   
/* 705:    */   public boolean hasOrder()
/* 706:    */   {
/* 707:649 */     return (this.orderBy != null) || (this.manyToManyOrderBy != null);
/* 708:    */   }
/* 709:    */   
/* 710:    */   public void setComparatorClassName(String comparatorClassName)
/* 711:    */   {
/* 712:653 */     this.comparatorClassName = comparatorClassName;
/* 713:    */   }
/* 714:    */   
/* 715:    */   public String getComparatorClassName()
/* 716:    */   {
/* 717:657 */     return this.comparatorClassName;
/* 718:    */   }
/* 719:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.Collection
 * JD-Core Version:    0.7.0.1
 */