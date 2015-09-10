/*   1:    */ package org.hibernate.metamodel.binding;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.Collections;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.HashSet;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.Map;
/*  11:    */ import java.util.Set;
/*  12:    */ import org.hibernate.AssertionFailure;
/*  13:    */ import org.hibernate.EntityMode;
/*  14:    */ import org.hibernate.engine.spi.FilterDefinition;
/*  15:    */ import org.hibernate.internal.util.Value;
/*  16:    */ import org.hibernate.internal.util.collections.JoinedIterable;
/*  17:    */ import org.hibernate.metamodel.domain.AttributeContainer;
/*  18:    */ import org.hibernate.metamodel.domain.Entity;
/*  19:    */ import org.hibernate.metamodel.domain.PluralAttribute;
/*  20:    */ import org.hibernate.metamodel.domain.PluralAttributeNature;
/*  21:    */ import org.hibernate.metamodel.domain.SingularAttribute;
/*  22:    */ import org.hibernate.metamodel.relational.TableSpecification;
/*  23:    */ import org.hibernate.metamodel.source.MetaAttributeContext;
/*  24:    */ import org.hibernate.metamodel.source.binder.JpaCallbackClass;
/*  25:    */ import org.hibernate.persister.entity.EntityPersister;
/*  26:    */ import org.hibernate.tuple.entity.EntityTuplizer;
/*  27:    */ 
/*  28:    */ public class EntityBinding
/*  29:    */   implements AttributeBindingContainer
/*  30:    */ {
/*  31:    */   private static final String NULL_DISCRIMINATOR_MATCH_VALUE = "null";
/*  32:    */   private static final String NOT_NULL_DISCRIMINATOR_MATCH_VALUE = "not null";
/*  33:    */   private final EntityBinding superEntityBinding;
/*  34: 61 */   private final List<EntityBinding> subEntityBindings = new ArrayList();
/*  35:    */   private final HierarchyDetails hierarchyDetails;
/*  36:    */   private Entity entity;
/*  37:    */   private TableSpecification primaryTable;
/*  38:    */   private String primaryTableName;
/*  39: 67 */   private Map<String, TableSpecification> secondaryTables = new HashMap();
/*  40:    */   private Value<Class<?>> proxyInterfaceType;
/*  41:    */   private String jpaEntityName;
/*  42:    */   private Class<? extends EntityPersister> customEntityPersisterClass;
/*  43:    */   private Class<? extends EntityTuplizer> customEntityTuplizerClass;
/*  44:    */   private String discriminatorMatchValue;
/*  45: 78 */   private Set<FilterDefinition> filterDefinitions = new HashSet();
/*  46: 79 */   private Set<SingularAssociationAttributeBinding> entityReferencingAttributeBindings = new HashSet();
/*  47:    */   private MetaAttributeContext metaAttributeContext;
/*  48:    */   private boolean lazy;
/*  49:    */   private boolean mutable;
/*  50:    */   private String whereFilter;
/*  51:    */   private String rowId;
/*  52:    */   private boolean dynamicUpdate;
/*  53:    */   private boolean dynamicInsert;
/*  54:    */   private int batchSize;
/*  55:    */   private boolean selectBeforeUpdate;
/*  56:    */   private boolean hasSubselectLoadableCollections;
/*  57:    */   private Boolean isAbstract;
/*  58:    */   private String customLoaderName;
/*  59:    */   private CustomSQL customInsert;
/*  60:    */   private CustomSQL customUpdate;
/*  61:    */   private CustomSQL customDelete;
/*  62:102 */   private Set<String> synchronizedTableNames = new HashSet();
/*  63:103 */   private Map<String, AttributeBinding> attributeBindingMap = new HashMap();
/*  64:105 */   private List<JpaCallbackClass> jpaCallbackClasses = new ArrayList();
/*  65:    */   
/*  66:    */   public EntityBinding(InheritanceType inheritanceType, EntityMode entityMode)
/*  67:    */   {
/*  68:114 */     this.superEntityBinding = null;
/*  69:115 */     this.hierarchyDetails = new HierarchyDetails(this, inheritanceType, entityMode);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public EntityBinding(EntityBinding superEntityBinding)
/*  73:    */   {
/*  74:124 */     this.superEntityBinding = superEntityBinding;
/*  75:125 */     this.superEntityBinding.subEntityBindings.add(this);
/*  76:126 */     this.hierarchyDetails = superEntityBinding.getHierarchyDetails();
/*  77:    */   }
/*  78:    */   
/*  79:    */   public HierarchyDetails getHierarchyDetails()
/*  80:    */   {
/*  81:130 */     return this.hierarchyDetails;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public EntityBinding getSuperEntityBinding()
/*  85:    */   {
/*  86:134 */     return this.superEntityBinding;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public boolean isRoot()
/*  90:    */   {
/*  91:138 */     return this.superEntityBinding == null;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public boolean isPolymorphic()
/*  95:    */   {
/*  96:142 */     return (this.superEntityBinding != null) || (this.hierarchyDetails.getEntityDiscriminator() != null) || (!this.subEntityBindings.isEmpty());
/*  97:    */   }
/*  98:    */   
/*  99:    */   public boolean hasSubEntityBindings()
/* 100:    */   {
/* 101:148 */     return this.subEntityBindings.size() > 0;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public int getSubEntityBindingClosureSpan()
/* 105:    */   {
/* 106:152 */     int n = this.subEntityBindings.size();
/* 107:153 */     for (EntityBinding subEntityBinding : this.subEntityBindings) {
/* 108:154 */       n += subEntityBinding.getSubEntityBindingClosureSpan();
/* 109:    */     }
/* 110:156 */     return n;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public Iterable<EntityBinding> getDirectSubEntityBindings()
/* 114:    */   {
/* 115:161 */     return this.subEntityBindings;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public Iterable<EntityBinding> getPostOrderSubEntityBindingClosure()
/* 119:    */   {
/* 120:175 */     List<Iterable<EntityBinding>> subclassIterables = new ArrayList(this.subEntityBindings.size() + 1);
/* 121:176 */     for (EntityBinding subEntityBinding : this.subEntityBindings)
/* 122:    */     {
/* 123:177 */       Iterable<EntityBinding> subSubEntityBindings = subEntityBinding.getPostOrderSubEntityBindingClosure();
/* 124:178 */       if (subSubEntityBindings.iterator().hasNext()) {
/* 125:179 */         subclassIterables.add(subSubEntityBindings);
/* 126:    */       }
/* 127:    */     }
/* 128:182 */     if (!this.subEntityBindings.isEmpty()) {
/* 129:183 */       subclassIterables.add(this.subEntityBindings);
/* 130:    */     }
/* 131:185 */     return new JoinedIterable(subclassIterables);
/* 132:    */   }
/* 133:    */   
/* 134:    */   public Iterable<EntityBinding> getPreOrderSubEntityBindingClosure()
/* 135:    */   {
/* 136:198 */     return getPreOrderSubEntityBindingClosure(false);
/* 137:    */   }
/* 138:    */   
/* 139:    */   private Iterable<EntityBinding> getPreOrderSubEntityBindingClosure(boolean includeThis)
/* 140:    */   {
/* 141:202 */     List<Iterable<EntityBinding>> iterables = new ArrayList();
/* 142:203 */     if (includeThis) {
/* 143:204 */       iterables.add(Collections.singletonList(this));
/* 144:    */     }
/* 145:206 */     for (EntityBinding subEntityBinding : this.subEntityBindings)
/* 146:    */     {
/* 147:207 */       Iterable<EntityBinding> subSubEntityBindingClosure = subEntityBinding.getPreOrderSubEntityBindingClosure(true);
/* 148:208 */       if (subSubEntityBindingClosure.iterator().hasNext()) {
/* 149:209 */         iterables.add(subSubEntityBindingClosure);
/* 150:    */       }
/* 151:    */     }
/* 152:212 */     return new JoinedIterable(iterables);
/* 153:    */   }
/* 154:    */   
/* 155:    */   public Entity getEntity()
/* 156:    */   {
/* 157:216 */     return this.entity;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public void setEntity(Entity entity)
/* 161:    */   {
/* 162:220 */     this.entity = entity;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public TableSpecification getPrimaryTable()
/* 166:    */   {
/* 167:224 */     return this.primaryTable;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public void setPrimaryTable(TableSpecification primaryTable)
/* 171:    */   {
/* 172:228 */     this.primaryTable = primaryTable;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public TableSpecification locateTable(String tableName)
/* 176:    */   {
/* 177:232 */     if ((tableName == null) || (tableName.equals(getPrimaryTableName()))) {
/* 178:233 */       return this.primaryTable;
/* 179:    */     }
/* 180:235 */     TableSpecification tableSpec = (TableSpecification)this.secondaryTables.get(tableName);
/* 181:236 */     if (tableSpec == null) {
/* 182:237 */       throw new AssertionFailure(String.format("Unable to find table %s amongst tables %s", new Object[] { tableName, this.secondaryTables.keySet() }));
/* 183:    */     }
/* 184:245 */     return tableSpec;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public String getPrimaryTableName()
/* 188:    */   {
/* 189:248 */     return this.primaryTableName;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public void setPrimaryTableName(String primaryTableName)
/* 193:    */   {
/* 194:252 */     this.primaryTableName = primaryTableName;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void addSecondaryTable(String tableName, TableSpecification table)
/* 198:    */   {
/* 199:256 */     this.secondaryTables.put(tableName, table);
/* 200:    */   }
/* 201:    */   
/* 202:    */   public boolean isVersioned()
/* 203:    */   {
/* 204:260 */     return getHierarchyDetails().getVersioningAttributeBinding() != null;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public boolean isDiscriminatorMatchValueNull()
/* 208:    */   {
/* 209:264 */     return "null".equals(this.discriminatorMatchValue);
/* 210:    */   }
/* 211:    */   
/* 212:    */   public boolean isDiscriminatorMatchValueNotNull()
/* 213:    */   {
/* 214:268 */     return "not null".equals(this.discriminatorMatchValue);
/* 215:    */   }
/* 216:    */   
/* 217:    */   public String getDiscriminatorMatchValue()
/* 218:    */   {
/* 219:272 */     return this.discriminatorMatchValue;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public void setDiscriminatorMatchValue(String discriminatorMatchValue)
/* 223:    */   {
/* 224:276 */     this.discriminatorMatchValue = discriminatorMatchValue;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public Iterable<FilterDefinition> getFilterDefinitions()
/* 228:    */   {
/* 229:280 */     return this.filterDefinitions;
/* 230:    */   }
/* 231:    */   
/* 232:    */   public void addFilterDefinition(FilterDefinition filterDefinition)
/* 233:    */   {
/* 234:284 */     this.filterDefinitions.add(filterDefinition);
/* 235:    */   }
/* 236:    */   
/* 237:    */   public Iterable<SingularAssociationAttributeBinding> getEntityReferencingAttributeBindings()
/* 238:    */   {
/* 239:288 */     return this.entityReferencingAttributeBindings;
/* 240:    */   }
/* 241:    */   
/* 242:    */   public EntityBinding seekEntityBinding()
/* 243:    */   {
/* 244:293 */     return this;
/* 245:    */   }
/* 246:    */   
/* 247:    */   public String getPathBase()
/* 248:    */   {
/* 249:298 */     return getEntity().getName();
/* 250:    */   }
/* 251:    */   
/* 252:    */   public Class<?> getClassReference()
/* 253:    */   {
/* 254:303 */     return getEntity().getClassReference();
/* 255:    */   }
/* 256:    */   
/* 257:    */   public AttributeContainer getAttributeContainer()
/* 258:    */   {
/* 259:308 */     return getEntity();
/* 260:    */   }
/* 261:    */   
/* 262:    */   protected void registerAttributeBinding(String name, AttributeBinding attributeBinding)
/* 263:    */   {
/* 264:312 */     if (SingularAssociationAttributeBinding.class.isInstance(attributeBinding)) {
/* 265:313 */       this.entityReferencingAttributeBindings.add((SingularAssociationAttributeBinding)attributeBinding);
/* 266:    */     }
/* 267:315 */     this.attributeBindingMap.put(name, attributeBinding);
/* 268:    */   }
/* 269:    */   
/* 270:    */   public MetaAttributeContext getMetaAttributeContext()
/* 271:    */   {
/* 272:320 */     return this.metaAttributeContext;
/* 273:    */   }
/* 274:    */   
/* 275:    */   public void setMetaAttributeContext(MetaAttributeContext metaAttributeContext)
/* 276:    */   {
/* 277:324 */     this.metaAttributeContext = metaAttributeContext;
/* 278:    */   }
/* 279:    */   
/* 280:    */   public boolean isMutable()
/* 281:    */   {
/* 282:328 */     return this.mutable;
/* 283:    */   }
/* 284:    */   
/* 285:    */   public void setMutable(boolean mutable)
/* 286:    */   {
/* 287:332 */     this.mutable = mutable;
/* 288:    */   }
/* 289:    */   
/* 290:    */   public boolean isLazy()
/* 291:    */   {
/* 292:336 */     return this.lazy;
/* 293:    */   }
/* 294:    */   
/* 295:    */   public void setLazy(boolean lazy)
/* 296:    */   {
/* 297:340 */     this.lazy = lazy;
/* 298:    */   }
/* 299:    */   
/* 300:    */   public Value<Class<?>> getProxyInterfaceType()
/* 301:    */   {
/* 302:344 */     return this.proxyInterfaceType;
/* 303:    */   }
/* 304:    */   
/* 305:    */   public void setProxyInterfaceType(Value<Class<?>> proxyInterfaceType)
/* 306:    */   {
/* 307:348 */     this.proxyInterfaceType = proxyInterfaceType;
/* 308:    */   }
/* 309:    */   
/* 310:    */   public String getWhereFilter()
/* 311:    */   {
/* 312:352 */     return this.whereFilter;
/* 313:    */   }
/* 314:    */   
/* 315:    */   public void setWhereFilter(String whereFilter)
/* 316:    */   {
/* 317:356 */     this.whereFilter = whereFilter;
/* 318:    */   }
/* 319:    */   
/* 320:    */   public String getRowId()
/* 321:    */   {
/* 322:360 */     return this.rowId;
/* 323:    */   }
/* 324:    */   
/* 325:    */   public void setRowId(String rowId)
/* 326:    */   {
/* 327:364 */     this.rowId = rowId;
/* 328:    */   }
/* 329:    */   
/* 330:    */   public boolean isDynamicUpdate()
/* 331:    */   {
/* 332:368 */     return this.dynamicUpdate;
/* 333:    */   }
/* 334:    */   
/* 335:    */   public void setDynamicUpdate(boolean dynamicUpdate)
/* 336:    */   {
/* 337:372 */     this.dynamicUpdate = dynamicUpdate;
/* 338:    */   }
/* 339:    */   
/* 340:    */   public boolean isDynamicInsert()
/* 341:    */   {
/* 342:376 */     return this.dynamicInsert;
/* 343:    */   }
/* 344:    */   
/* 345:    */   public void setDynamicInsert(boolean dynamicInsert)
/* 346:    */   {
/* 347:380 */     this.dynamicInsert = dynamicInsert;
/* 348:    */   }
/* 349:    */   
/* 350:    */   public int getBatchSize()
/* 351:    */   {
/* 352:384 */     return this.batchSize;
/* 353:    */   }
/* 354:    */   
/* 355:    */   public void setBatchSize(int batchSize)
/* 356:    */   {
/* 357:388 */     this.batchSize = batchSize;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public boolean isSelectBeforeUpdate()
/* 361:    */   {
/* 362:392 */     return this.selectBeforeUpdate;
/* 363:    */   }
/* 364:    */   
/* 365:    */   public void setSelectBeforeUpdate(boolean selectBeforeUpdate)
/* 366:    */   {
/* 367:396 */     this.selectBeforeUpdate = selectBeforeUpdate;
/* 368:    */   }
/* 369:    */   
/* 370:    */   public boolean hasSubselectLoadableCollections()
/* 371:    */   {
/* 372:400 */     return this.hasSubselectLoadableCollections;
/* 373:    */   }
/* 374:    */   
/* 375:    */   void setSubselectLoadableCollections(boolean hasSubselectLoadableCollections)
/* 376:    */   {
/* 377:405 */     this.hasSubselectLoadableCollections = hasSubselectLoadableCollections;
/* 378:    */   }
/* 379:    */   
/* 380:    */   public Class<? extends EntityPersister> getCustomEntityPersisterClass()
/* 381:    */   {
/* 382:409 */     return this.customEntityPersisterClass;
/* 383:    */   }
/* 384:    */   
/* 385:    */   public void setCustomEntityPersisterClass(Class<? extends EntityPersister> customEntityPersisterClass)
/* 386:    */   {
/* 387:413 */     this.customEntityPersisterClass = customEntityPersisterClass;
/* 388:    */   }
/* 389:    */   
/* 390:    */   public Class<? extends EntityTuplizer> getCustomEntityTuplizerClass()
/* 391:    */   {
/* 392:417 */     return this.customEntityTuplizerClass;
/* 393:    */   }
/* 394:    */   
/* 395:    */   public void setCustomEntityTuplizerClass(Class<? extends EntityTuplizer> customEntityTuplizerClass)
/* 396:    */   {
/* 397:421 */     this.customEntityTuplizerClass = customEntityTuplizerClass;
/* 398:    */   }
/* 399:    */   
/* 400:    */   public Boolean isAbstract()
/* 401:    */   {
/* 402:425 */     return this.isAbstract;
/* 403:    */   }
/* 404:    */   
/* 405:    */   public void setAbstract(Boolean isAbstract)
/* 406:    */   {
/* 407:429 */     this.isAbstract = isAbstract;
/* 408:    */   }
/* 409:    */   
/* 410:    */   public Set<String> getSynchronizedTableNames()
/* 411:    */   {
/* 412:433 */     return this.synchronizedTableNames;
/* 413:    */   }
/* 414:    */   
/* 415:    */   public void addSynchronizedTableNames(Collection<String> synchronizedTableNames)
/* 416:    */   {
/* 417:437 */     this.synchronizedTableNames.addAll(synchronizedTableNames);
/* 418:    */   }
/* 419:    */   
/* 420:    */   public String getJpaEntityName()
/* 421:    */   {
/* 422:441 */     return this.jpaEntityName;
/* 423:    */   }
/* 424:    */   
/* 425:    */   public void setJpaEntityName(String jpaEntityName)
/* 426:    */   {
/* 427:445 */     this.jpaEntityName = jpaEntityName;
/* 428:    */   }
/* 429:    */   
/* 430:    */   public String getCustomLoaderName()
/* 431:    */   {
/* 432:449 */     return this.customLoaderName;
/* 433:    */   }
/* 434:    */   
/* 435:    */   public void setCustomLoaderName(String customLoaderName)
/* 436:    */   {
/* 437:453 */     this.customLoaderName = customLoaderName;
/* 438:    */   }
/* 439:    */   
/* 440:    */   public CustomSQL getCustomInsert()
/* 441:    */   {
/* 442:457 */     return this.customInsert;
/* 443:    */   }
/* 444:    */   
/* 445:    */   public void setCustomInsert(CustomSQL customInsert)
/* 446:    */   {
/* 447:461 */     this.customInsert = customInsert;
/* 448:    */   }
/* 449:    */   
/* 450:    */   public CustomSQL getCustomUpdate()
/* 451:    */   {
/* 452:465 */     return this.customUpdate;
/* 453:    */   }
/* 454:    */   
/* 455:    */   public void setCustomUpdate(CustomSQL customUpdate)
/* 456:    */   {
/* 457:469 */     this.customUpdate = customUpdate;
/* 458:    */   }
/* 459:    */   
/* 460:    */   public CustomSQL getCustomDelete()
/* 461:    */   {
/* 462:473 */     return this.customDelete;
/* 463:    */   }
/* 464:    */   
/* 465:    */   public void setCustomDelete(CustomSQL customDelete)
/* 466:    */   {
/* 467:477 */     this.customDelete = customDelete;
/* 468:    */   }
/* 469:    */   
/* 470:    */   public String toString()
/* 471:    */   {
/* 472:482 */     StringBuilder sb = new StringBuilder();
/* 473:483 */     sb.append("EntityBinding");
/* 474:484 */     sb.append("{entity=").append(this.entity != null ? this.entity.getName() : "not set");
/* 475:485 */     sb.append('}');
/* 476:486 */     return sb.toString();
/* 477:    */   }
/* 478:    */   
/* 479:    */   public BasicAttributeBinding makeBasicAttributeBinding(SingularAttribute attribute)
/* 480:    */   {
/* 481:491 */     return makeSimpleAttributeBinding(attribute, false, false);
/* 482:    */   }
/* 483:    */   
/* 484:    */   private BasicAttributeBinding makeSimpleAttributeBinding(SingularAttribute attribute, boolean forceNonNullable, boolean forceUnique)
/* 485:    */   {
/* 486:495 */     BasicAttributeBinding binding = new BasicAttributeBinding(this, attribute, forceNonNullable, forceUnique);
/* 487:    */     
/* 488:    */ 
/* 489:    */ 
/* 490:    */ 
/* 491:    */ 
/* 492:501 */     registerAttributeBinding(attribute.getName(), binding);
/* 493:502 */     return binding;
/* 494:    */   }
/* 495:    */   
/* 496:    */   public ComponentAttributeBinding makeComponentAttributeBinding(SingularAttribute attribute)
/* 497:    */   {
/* 498:507 */     ComponentAttributeBinding binding = new ComponentAttributeBinding(this, attribute);
/* 499:508 */     registerAttributeBinding(attribute.getName(), binding);
/* 500:509 */     return binding;
/* 501:    */   }
/* 502:    */   
/* 503:    */   public ManyToOneAttributeBinding makeManyToOneAttributeBinding(SingularAttribute attribute)
/* 504:    */   {
/* 505:514 */     ManyToOneAttributeBinding binding = new ManyToOneAttributeBinding(this, attribute);
/* 506:515 */     registerAttributeBinding(attribute.getName(), binding);
/* 507:516 */     return binding;
/* 508:    */   }
/* 509:    */   
/* 510:    */   public BagBinding makeBagAttributeBinding(PluralAttribute attribute, CollectionElementNature nature)
/* 511:    */   {
/* 512:521 */     Helper.checkPluralAttributeNature(attribute, PluralAttributeNature.BAG);
/* 513:522 */     BagBinding binding = new BagBinding(this, attribute, nature);
/* 514:523 */     registerAttributeBinding(attribute.getName(), binding);
/* 515:524 */     return binding;
/* 516:    */   }
/* 517:    */   
/* 518:    */   public SetBinding makeSetAttributeBinding(PluralAttribute attribute, CollectionElementNature nature)
/* 519:    */   {
/* 520:529 */     Helper.checkPluralAttributeNature(attribute, PluralAttributeNature.SET);
/* 521:530 */     SetBinding binding = new SetBinding(this, attribute, nature);
/* 522:531 */     registerAttributeBinding(attribute.getName(), binding);
/* 523:532 */     return binding;
/* 524:    */   }
/* 525:    */   
/* 526:    */   public AttributeBinding locateAttributeBinding(String name)
/* 527:    */   {
/* 528:537 */     return (AttributeBinding)this.attributeBindingMap.get(name);
/* 529:    */   }
/* 530:    */   
/* 531:    */   public Iterable<AttributeBinding> attributeBindings()
/* 532:    */   {
/* 533:542 */     return this.attributeBindingMap.values();
/* 534:    */   }
/* 535:    */   
/* 536:    */   public int getAttributeBindingClosureSpan()
/* 537:    */   {
/* 538:554 */     return this.superEntityBinding != null ? this.superEntityBinding.getAttributeBindingClosureSpan() + this.attributeBindingMap.size() : this.attributeBindingMap.size();
/* 539:    */   }
/* 540:    */   
/* 541:    */   public Iterable<AttributeBinding> getAttributeBindingClosure()
/* 542:    */   {
/* 543:    */     Iterable<AttributeBinding> iterable;
/* 544:    */     Iterable<AttributeBinding> iterable;
/* 545:569 */     if (this.superEntityBinding != null)
/* 546:    */     {
/* 547:570 */       List<Iterable<AttributeBinding>> iterables = new ArrayList(2);
/* 548:571 */       iterables.add(this.superEntityBinding.getAttributeBindingClosure());
/* 549:572 */       iterables.add(attributeBindings());
/* 550:573 */       iterable = new JoinedIterable(iterables);
/* 551:    */     }
/* 552:    */     else
/* 553:    */     {
/* 554:576 */       iterable = attributeBindings();
/* 555:    */     }
/* 556:578 */     return iterable;
/* 557:    */   }
/* 558:    */   
/* 559:    */   public Iterable<AttributeBinding> getSubEntityAttributeBindingClosure()
/* 560:    */   {
/* 561:588 */     List<Iterable<AttributeBinding>> iterables = new ArrayList();
/* 562:589 */     iterables.add(getAttributeBindingClosure());
/* 563:590 */     for (EntityBinding subEntityBinding : getPreOrderSubEntityBindingClosure()) {
/* 564:592 */       iterables.add(subEntityBinding.attributeBindings());
/* 565:    */     }
/* 566:595 */     return new JoinedIterable(iterables);
/* 567:    */   }
/* 568:    */   
/* 569:    */   public void setJpaCallbackClasses(List<JpaCallbackClass> jpaCallbackClasses)
/* 570:    */   {
/* 571:599 */     this.jpaCallbackClasses = jpaCallbackClasses;
/* 572:    */   }
/* 573:    */   
/* 574:    */   public Iterable<JpaCallbackClass> getJpaCallbackClasses()
/* 575:    */   {
/* 576:603 */     return this.jpaCallbackClasses;
/* 577:    */   }
/* 578:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.binding.EntityBinding
 * JD-Core Version:    0.7.0.1
 */