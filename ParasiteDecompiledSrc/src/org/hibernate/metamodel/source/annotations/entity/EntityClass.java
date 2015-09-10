/*   1:    */ package org.hibernate.metamodel.source.annotations.entity;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Modifier;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Arrays;
/*   6:    */ import java.util.Collection;
/*   7:    */ import java.util.Collections;
/*   8:    */ import java.util.HashMap;
/*   9:    */ import java.util.HashSet;
/*  10:    */ import java.util.List;
/*  11:    */ import java.util.Map;
/*  12:    */ import java.util.Set;
/*  13:    */ import javax.persistence.DiscriminatorType;
/*  14:    */ import javax.persistence.PersistenceException;
/*  15:    */ import javax.persistence.PostLoad;
/*  16:    */ import javax.persistence.PostPersist;
/*  17:    */ import javax.persistence.PostRemove;
/*  18:    */ import javax.persistence.PostUpdate;
/*  19:    */ import javax.persistence.PrePersist;
/*  20:    */ import javax.persistence.PreRemove;
/*  21:    */ import javax.persistence.PreUpdate;
/*  22:    */ import javax.persistence.SharedCacheMode;
/*  23:    */ import org.hibernate.AnnotationException;
/*  24:    */ import org.hibernate.MappingException;
/*  25:    */ import org.hibernate.annotations.CacheConcurrencyStrategy;
/*  26:    */ import org.hibernate.annotations.OptimisticLockType;
/*  27:    */ import org.hibernate.annotations.PolymorphismType;
/*  28:    */ import org.hibernate.engine.OptimisticLockStyle;
/*  29:    */ import org.hibernate.engine.spi.ExecuteUpdateResultCheckStyle;
/*  30:    */ import org.hibernate.internal.util.StringHelper;
/*  31:    */ import org.hibernate.metamodel.binding.Caching;
/*  32:    */ import org.hibernate.metamodel.binding.CustomSQL;
/*  33:    */ import org.hibernate.metamodel.binding.InheritanceType;
/*  34:    */ import org.hibernate.metamodel.source.MappingDefaults;
/*  35:    */ import org.hibernate.metamodel.source.annotations.AnnotationBindingContext;
/*  36:    */ import org.hibernate.metamodel.source.annotations.HibernateDotNames;
/*  37:    */ import org.hibernate.metamodel.source.annotations.JPADotNames;
/*  38:    */ import org.hibernate.metamodel.source.annotations.JandexHelper;
/*  39:    */ import org.hibernate.metamodel.source.annotations.attribute.ColumnValues;
/*  40:    */ import org.hibernate.metamodel.source.annotations.attribute.FormulaValue;
/*  41:    */ import org.hibernate.metamodel.source.annotations.xml.PseudoJpaDotNames;
/*  42:    */ import org.hibernate.metamodel.source.binder.ConstraintSource;
/*  43:    */ import org.hibernate.metamodel.source.binder.JpaCallbackClass;
/*  44:    */ import org.hibernate.metamodel.source.binder.TableSource;
/*  45:    */ import org.jboss.jandex.AnnotationInstance;
/*  46:    */ import org.jboss.jandex.AnnotationValue;
/*  47:    */ import org.jboss.jandex.ClassInfo;
/*  48:    */ import org.jboss.jandex.DotName;
/*  49:    */ import org.jboss.jandex.Index;
/*  50:    */ import org.jboss.jandex.MethodInfo;
/*  51:    */ import org.jboss.jandex.Type;
/*  52:    */ import org.jboss.jandex.Type.Kind;
/*  53:    */ 
/*  54:    */ public class EntityClass
/*  55:    */   extends ConfiguredClass
/*  56:    */ {
/*  57:    */   private final IdType idType;
/*  58:    */   private final InheritanceType inheritanceType;
/*  59:    */   private final String explicitEntityName;
/*  60:    */   private final String customLoaderQueryName;
/*  61:    */   private final List<String> synchronizedTableNames;
/*  62:    */   private final int batchSize;
/*  63:    */   private final TableSource primaryTableSource;
/*  64:    */   private final Set<TableSource> secondaryTableSources;
/*  65:    */   private final Set<ConstraintSource> constraintSources;
/*  66:    */   private boolean isMutable;
/*  67:    */   private boolean isExplicitPolymorphism;
/*  68:    */   private OptimisticLockStyle optimisticLockStyle;
/*  69:    */   private String whereClause;
/*  70:    */   private String rowId;
/*  71:    */   private Caching caching;
/*  72:    */   private boolean isDynamicInsert;
/*  73:    */   private boolean isDynamicUpdate;
/*  74:    */   private boolean isSelectBeforeUpdate;
/*  75:    */   private String customPersister;
/*  76:    */   private CustomSQL customInsert;
/*  77:    */   private CustomSQL customUpdate;
/*  78:    */   private CustomSQL customDelete;
/*  79:    */   private boolean isLazy;
/*  80:    */   private String proxy;
/*  81:    */   private ColumnValues discriminatorColumnValues;
/*  82:    */   private FormulaValue discriminatorFormula;
/*  83:    */   private Class<?> discriminatorType;
/*  84:    */   private String discriminatorMatchValue;
/*  85:116 */   private boolean isDiscriminatorForced = true;
/*  86:117 */   private boolean isDiscriminatorIncludedInSql = true;
/*  87:    */   private final List<JpaCallbackClass> jpaCallbacks;
/*  88:    */   
/*  89:    */   public EntityClass(ClassInfo classInfo, EntityClass parent, javax.persistence.AccessType hierarchyAccessType, InheritanceType inheritanceType, AnnotationBindingContext context)
/*  90:    */   {
/*  91:127 */     super(classInfo, hierarchyAccessType, parent, context);
/*  92:128 */     this.inheritanceType = inheritanceType;
/*  93:129 */     this.idType = determineIdType();
/*  94:130 */     boolean hasOwnTable = definesItsOwnTable();
/*  95:131 */     this.explicitEntityName = determineExplicitEntityName();
/*  96:132 */     this.constraintSources = new HashSet();
/*  97:134 */     if (hasOwnTable)
/*  98:    */     {
/*  99:135 */       AnnotationInstance tableAnnotation = JandexHelper.getSingleAnnotation(getClassInfo(), JPADotNames.TABLE);
/* 100:    */       
/* 101:    */ 
/* 102:    */ 
/* 103:139 */       this.primaryTableSource = createTableSource(tableAnnotation);
/* 104:    */     }
/* 105:    */     else
/* 106:    */     {
/* 107:142 */       this.primaryTableSource = null;
/* 108:    */     }
/* 109:145 */     this.secondaryTableSources = createSecondaryTableSources();
/* 110:146 */     this.customLoaderQueryName = determineCustomLoader();
/* 111:147 */     this.synchronizedTableNames = determineSynchronizedTableNames();
/* 112:148 */     this.batchSize = determineBatchSize();
/* 113:149 */     this.jpaCallbacks = determineEntityListeners();
/* 114:    */     
/* 115:151 */     processHibernateEntitySpecificAnnotations();
/* 116:152 */     processCustomSqlAnnotations();
/* 117:153 */     processProxyGeneration();
/* 118:154 */     processDiscriminator();
/* 119:    */   }
/* 120:    */   
/* 121:    */   public ColumnValues getDiscriminatorColumnValues()
/* 122:    */   {
/* 123:158 */     return this.discriminatorColumnValues;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public FormulaValue getDiscriminatorFormula()
/* 127:    */   {
/* 128:162 */     return this.discriminatorFormula;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public Class<?> getDiscriminatorType()
/* 132:    */   {
/* 133:166 */     return this.discriminatorType;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public IdType getIdType()
/* 137:    */   {
/* 138:170 */     return this.idType;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public boolean isExplicitPolymorphism()
/* 142:    */   {
/* 143:174 */     return this.isExplicitPolymorphism;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public boolean isMutable()
/* 147:    */   {
/* 148:178 */     return this.isMutable;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public OptimisticLockStyle getOptimisticLockStyle()
/* 152:    */   {
/* 153:182 */     return this.optimisticLockStyle;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public String getWhereClause()
/* 157:    */   {
/* 158:186 */     return this.whereClause;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public String getRowId()
/* 162:    */   {
/* 163:190 */     return this.rowId;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public Caching getCaching()
/* 167:    */   {
/* 168:194 */     return this.caching;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public TableSource getPrimaryTableSource()
/* 172:    */   {
/* 173:198 */     if (definesItsOwnTable()) {
/* 174:199 */       return this.primaryTableSource;
/* 175:    */     }
/* 176:202 */     return ((EntityClass)getParent()).getPrimaryTableSource();
/* 177:    */   }
/* 178:    */   
/* 179:    */   public Set<TableSource> getSecondaryTableSources()
/* 180:    */   {
/* 181:207 */     return this.secondaryTableSources;
/* 182:    */   }
/* 183:    */   
/* 184:    */   public Set<ConstraintSource> getConstraintSources()
/* 185:    */   {
/* 186:211 */     return this.constraintSources;
/* 187:    */   }
/* 188:    */   
/* 189:    */   public String getExplicitEntityName()
/* 190:    */   {
/* 191:215 */     return this.explicitEntityName;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public String getEntityName()
/* 195:    */   {
/* 196:219 */     return getConfiguredClass().getSimpleName();
/* 197:    */   }
/* 198:    */   
/* 199:    */   public boolean isDynamicInsert()
/* 200:    */   {
/* 201:223 */     return this.isDynamicInsert;
/* 202:    */   }
/* 203:    */   
/* 204:    */   public boolean isDynamicUpdate()
/* 205:    */   {
/* 206:227 */     return this.isDynamicUpdate;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public boolean isSelectBeforeUpdate()
/* 210:    */   {
/* 211:231 */     return this.isSelectBeforeUpdate;
/* 212:    */   }
/* 213:    */   
/* 214:    */   public String getCustomLoaderQueryName()
/* 215:    */   {
/* 216:235 */     return this.customLoaderQueryName;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public CustomSQL getCustomInsert()
/* 220:    */   {
/* 221:239 */     return this.customInsert;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public CustomSQL getCustomUpdate()
/* 225:    */   {
/* 226:243 */     return this.customUpdate;
/* 227:    */   }
/* 228:    */   
/* 229:    */   public CustomSQL getCustomDelete()
/* 230:    */   {
/* 231:247 */     return this.customDelete;
/* 232:    */   }
/* 233:    */   
/* 234:    */   public List<String> getSynchronizedTableNames()
/* 235:    */   {
/* 236:251 */     return this.synchronizedTableNames;
/* 237:    */   }
/* 238:    */   
/* 239:    */   public String getCustomPersister()
/* 240:    */   {
/* 241:255 */     return this.customPersister;
/* 242:    */   }
/* 243:    */   
/* 244:    */   public boolean isLazy()
/* 245:    */   {
/* 246:259 */     return this.isLazy;
/* 247:    */   }
/* 248:    */   
/* 249:    */   public String getProxy()
/* 250:    */   {
/* 251:263 */     return this.proxy;
/* 252:    */   }
/* 253:    */   
/* 254:    */   public int getBatchSize()
/* 255:    */   {
/* 256:267 */     return this.batchSize;
/* 257:    */   }
/* 258:    */   
/* 259:    */   public boolean isEntityRoot()
/* 260:    */   {
/* 261:271 */     return getParent() == null;
/* 262:    */   }
/* 263:    */   
/* 264:    */   public boolean isDiscriminatorForced()
/* 265:    */   {
/* 266:275 */     return this.isDiscriminatorForced;
/* 267:    */   }
/* 268:    */   
/* 269:    */   public boolean isDiscriminatorIncludedInSql()
/* 270:    */   {
/* 271:279 */     return this.isDiscriminatorIncludedInSql;
/* 272:    */   }
/* 273:    */   
/* 274:    */   public String getDiscriminatorMatchValue()
/* 275:    */   {
/* 276:283 */     return this.discriminatorMatchValue;
/* 277:    */   }
/* 278:    */   
/* 279:    */   public List<JpaCallbackClass> getJpaCallbacks()
/* 280:    */   {
/* 281:287 */     return this.jpaCallbacks;
/* 282:    */   }
/* 283:    */   
/* 284:    */   private String determineExplicitEntityName()
/* 285:    */   {
/* 286:291 */     AnnotationInstance jpaEntityAnnotation = JandexHelper.getSingleAnnotation(getClassInfo(), JPADotNames.ENTITY);
/* 287:    */     
/* 288:    */ 
/* 289:294 */     return (String)JandexHelper.getValue(jpaEntityAnnotation, "name", String.class);
/* 290:    */   }
/* 291:    */   
/* 292:    */   private boolean definesItsOwnTable()
/* 293:    */   {
/* 294:299 */     return (!InheritanceType.SINGLE_TABLE.equals(this.inheritanceType)) || (isEntityRoot());
/* 295:    */   }
/* 296:    */   
/* 297:    */   private IdType determineIdType()
/* 298:    */   {
/* 299:303 */     List<AnnotationInstance> idAnnotations = findIdAnnotations(JPADotNames.ID);
/* 300:304 */     List<AnnotationInstance> embeddedIdAnnotations = findIdAnnotations(JPADotNames.EMBEDDED_ID);
/* 301:306 */     if ((!idAnnotations.isEmpty()) && (!embeddedIdAnnotations.isEmpty())) {
/* 302:307 */       throw new MappingException("@EmbeddedId and @Id cannot be used together. Check the configuration for " + getName() + ".");
/* 303:    */     }
/* 304:312 */     if (!embeddedIdAnnotations.isEmpty())
/* 305:    */     {
/* 306:313 */       if (embeddedIdAnnotations.size() == 1) {
/* 307:314 */         return IdType.EMBEDDED;
/* 308:    */       }
/* 309:317 */       throw new AnnotationException("Multiple @EmbeddedId annotations are not allowed");
/* 310:    */     }
/* 311:321 */     if (!idAnnotations.isEmpty()) {
/* 312:322 */       return idAnnotations.size() == 1 ? IdType.SIMPLE : IdType.COMPOSED;
/* 313:    */     }
/* 314:324 */     return IdType.NONE;
/* 315:    */   }
/* 316:    */   
/* 317:    */   private List<AnnotationInstance> findIdAnnotations(DotName idAnnotationType)
/* 318:    */   {
/* 319:328 */     List<AnnotationInstance> idAnnotationList = new ArrayList();
/* 320:329 */     if (getClassInfo().annotations().containsKey(idAnnotationType)) {
/* 321:330 */       idAnnotationList.addAll((Collection)getClassInfo().annotations().get(idAnnotationType));
/* 322:    */     }
/* 323:332 */     ConfiguredClass parent = getParent();
/* 324:333 */     while (parent != null)
/* 325:    */     {
/* 326:334 */       if (parent.getClassInfo().annotations().containsKey(idAnnotationType)) {
/* 327:335 */         idAnnotationList.addAll((Collection)parent.getClassInfo().annotations().get(idAnnotationType));
/* 328:    */       }
/* 329:337 */       parent = parent.getParent();
/* 330:    */     }
/* 331:339 */     return idAnnotationList;
/* 332:    */   }
/* 333:    */   
/* 334:    */   private void processDiscriminator()
/* 335:    */   {
/* 336:343 */     if (!InheritanceType.SINGLE_TABLE.equals(this.inheritanceType)) {
/* 337:344 */       return;
/* 338:    */     }
/* 339:347 */     AnnotationInstance discriminatorValueAnnotation = JandexHelper.getSingleAnnotation(getClassInfo(), JPADotNames.DISCRIMINATOR_VALUE);
/* 340:350 */     if (discriminatorValueAnnotation != null) {
/* 341:351 */       this.discriminatorMatchValue = discriminatorValueAnnotation.value().asString();
/* 342:    */     }
/* 343:354 */     AnnotationInstance discriminatorColumnAnnotation = JandexHelper.getSingleAnnotation(getClassInfo(), JPADotNames.DISCRIMINATOR_COLUMN);
/* 344:    */     
/* 345:    */ 
/* 346:    */ 
/* 347:358 */     AnnotationInstance discriminatorFormulaAnnotation = JandexHelper.getSingleAnnotation(getClassInfo(), HibernateDotNames.DISCRIMINATOR_FORMULA);
/* 348:    */     
/* 349:    */ 
/* 350:    */ 
/* 351:    */ 
/* 352:    */ 
/* 353:364 */     Class<?> type = String.class;
/* 354:365 */     if (discriminatorFormulaAnnotation != null)
/* 355:    */     {
/* 356:366 */       String expression = (String)JandexHelper.getValue(discriminatorFormulaAnnotation, "value", String.class);
/* 357:367 */       this.discriminatorFormula = new FormulaValue(getPrimaryTableSource().getExplicitTableName(), expression);
/* 358:    */     }
/* 359:369 */     this.discriminatorColumnValues = new ColumnValues(null);
/* 360:370 */     this.discriminatorColumnValues.setNullable(false);
/* 361:371 */     if (discriminatorColumnAnnotation != null)
/* 362:    */     {
/* 363:373 */       DiscriminatorType discriminatorType = (DiscriminatorType)Enum.valueOf(DiscriminatorType.class, discriminatorColumnAnnotation.value("discriminatorType").asEnum());
/* 364:376 */       switch (1.$SwitchMap$javax$persistence$DiscriminatorType[discriminatorType.ordinal()])
/* 365:    */       {
/* 366:    */       case 1: 
/* 367:378 */         type = String.class;
/* 368:379 */         break;
/* 369:    */       case 2: 
/* 370:382 */         type = Character.class;
/* 371:383 */         break;
/* 372:    */       case 3: 
/* 373:386 */         type = Integer.class;
/* 374:387 */         break;
/* 375:    */       default: 
/* 376:390 */         throw new AnnotationException("Unsupported discriminator type: " + discriminatorType);
/* 377:    */       }
/* 378:394 */       this.discriminatorColumnValues.setName((String)JandexHelper.getValue(discriminatorColumnAnnotation, "name", String.class));
/* 379:    */       
/* 380:    */ 
/* 381:    */ 
/* 382:    */ 
/* 383:    */ 
/* 384:    */ 
/* 385:401 */       this.discriminatorColumnValues.setLength(((Integer)JandexHelper.getValue(discriminatorColumnAnnotation, "length", Integer.class)).intValue());
/* 386:    */       
/* 387:    */ 
/* 388:    */ 
/* 389:    */ 
/* 390:    */ 
/* 391:    */ 
/* 392:408 */       this.discriminatorColumnValues.setColumnDefinition((String)JandexHelper.getValue(discriminatorColumnAnnotation, "columnDefinition", String.class));
/* 393:    */     }
/* 394:416 */     this.discriminatorType = type;
/* 395:    */     
/* 396:418 */     AnnotationInstance discriminatorOptionsAnnotation = JandexHelper.getSingleAnnotation(getClassInfo(), HibernateDotNames.DISCRIMINATOR_OPTIONS);
/* 397:421 */     if (discriminatorOptionsAnnotation != null)
/* 398:    */     {
/* 399:422 */       this.isDiscriminatorForced = discriminatorOptionsAnnotation.value("force").asBoolean();
/* 400:423 */       this.isDiscriminatorIncludedInSql = discriminatorOptionsAnnotation.value("insert").asBoolean();
/* 401:    */     }
/* 402:    */     else
/* 403:    */     {
/* 404:426 */       this.isDiscriminatorForced = false;
/* 405:427 */       this.isDiscriminatorIncludedInSql = true;
/* 406:    */     }
/* 407:    */   }
/* 408:    */   
/* 409:    */   private void processHibernateEntitySpecificAnnotations()
/* 410:    */   {
/* 411:432 */     AnnotationInstance hibernateEntityAnnotation = JandexHelper.getSingleAnnotation(getClassInfo(), HibernateDotNames.ENTITY);
/* 412:    */     
/* 413:    */ 
/* 414:    */ 
/* 415:    */ 
/* 416:437 */     PolymorphismType polymorphism = PolymorphismType.IMPLICIT;
/* 417:438 */     if ((hibernateEntityAnnotation != null) && (hibernateEntityAnnotation.value("polymorphism") != null)) {
/* 418:439 */       polymorphism = PolymorphismType.valueOf(hibernateEntityAnnotation.value("polymorphism").asEnum());
/* 419:    */     }
/* 420:441 */     this.isExplicitPolymorphism = (polymorphism == PolymorphismType.EXPLICIT);
/* 421:    */     
/* 422:    */ 
/* 423:444 */     OptimisticLockType optimisticLockType = OptimisticLockType.VERSION;
/* 424:445 */     if ((hibernateEntityAnnotation != null) && (hibernateEntityAnnotation.value("optimisticLock") != null)) {
/* 425:446 */       optimisticLockType = OptimisticLockType.valueOf(hibernateEntityAnnotation.value("optimisticLock").asEnum());
/* 426:    */     }
/* 427:451 */     this.optimisticLockStyle = OptimisticLockStyle.valueOf(optimisticLockType.name());
/* 428:    */     
/* 429:453 */     AnnotationInstance hibernateImmutableAnnotation = JandexHelper.getSingleAnnotation(getClassInfo(), HibernateDotNames.IMMUTABLE);
/* 430:    */     
/* 431:    */ 
/* 432:456 */     this.isMutable = ((hibernateImmutableAnnotation == null) && (hibernateEntityAnnotation != null) && (hibernateEntityAnnotation.value("mutable") != null) && (hibernateEntityAnnotation.value("mutable").asBoolean()));
/* 433:    */     
/* 434:    */ 
/* 435:    */ 
/* 436:    */ 
/* 437:    */ 
/* 438:462 */     AnnotationInstance whereAnnotation = JandexHelper.getSingleAnnotation(getClassInfo(), HibernateDotNames.WHERE);
/* 439:    */     
/* 440:    */ 
/* 441:465 */     this.whereClause = ((whereAnnotation != null) && (whereAnnotation.value("clause") != null) ? whereAnnotation.value("clause").asString() : null);
/* 442:    */     
/* 443:    */ 
/* 444:468 */     AnnotationInstance rowIdAnnotation = JandexHelper.getSingleAnnotation(getClassInfo(), HibernateDotNames.ROW_ID);
/* 445:    */     
/* 446:    */ 
/* 447:471 */     this.rowId = ((rowIdAnnotation != null) && (rowIdAnnotation.value() != null) ? rowIdAnnotation.value().asString() : null);
/* 448:    */     
/* 449:    */ 
/* 450:474 */     this.caching = determineCachingSettings();
/* 451:    */     
/* 452:    */ 
/* 453:477 */     this.isDynamicInsert = ((hibernateEntityAnnotation != null) && (hibernateEntityAnnotation.value("dynamicInsert") != null) && (hibernateEntityAnnotation.value("dynamicInsert").asBoolean()));
/* 454:    */     
/* 455:    */ 
/* 456:    */ 
/* 457:    */ 
/* 458:    */ 
/* 459:483 */     this.isDynamicUpdate = ((hibernateEntityAnnotation != null) && (hibernateEntityAnnotation.value("dynamicUpdate") != null) && (hibernateEntityAnnotation.value("dynamicUpdate").asBoolean()));
/* 460:    */     
/* 461:    */ 
/* 462:    */ 
/* 463:    */ 
/* 464:    */ 
/* 465:    */ 
/* 466:490 */     this.isSelectBeforeUpdate = ((hibernateEntityAnnotation != null) && (hibernateEntityAnnotation.value("selectBeforeUpdate") != null) && (hibernateEntityAnnotation.value("selectBeforeUpdate").asBoolean()));
/* 467:    */     
/* 468:    */ 
/* 469:    */ 
/* 470:    */ 
/* 471:    */ 
/* 472:    */ 
/* 473:497 */     AnnotationInstance persisterAnnotation = JandexHelper.getSingleAnnotation(getClassInfo(), HibernateDotNames.PERSISTER);
/* 474:    */     String entityPersisterClass;
/* 475:    */     String entityPersisterClass;
/* 476:500 */     if ((persisterAnnotation == null) || (persisterAnnotation.value("impl") == null))
/* 477:    */     {
/* 478:    */       String entityPersisterClass;
/* 479:501 */       if ((hibernateEntityAnnotation != null) && (hibernateEntityAnnotation.value("persister") != null)) {
/* 480:502 */         entityPersisterClass = hibernateEntityAnnotation.value("persister").asString();
/* 481:    */       } else {
/* 482:505 */         entityPersisterClass = null;
/* 483:    */       }
/* 484:    */     }
/* 485:    */     else
/* 486:    */     {
/* 487:509 */       if ((hibernateEntityAnnotation != null) && (hibernateEntityAnnotation.value("persister") != null)) {}
/* 488:512 */       entityPersisterClass = persisterAnnotation.value("impl").asString();
/* 489:    */     }
/* 490:514 */     this.customPersister = entityPersisterClass;
/* 491:    */   }
/* 492:    */   
/* 493:    */   private Caching determineCachingSettings()
/* 494:    */   {
/* 495:518 */     AnnotationInstance hibernateCacheAnnotation = JandexHelper.getSingleAnnotation(getClassInfo(), HibernateDotNames.CACHE);
/* 496:521 */     if (hibernateCacheAnnotation != null)
/* 497:    */     {
/* 498:522 */       org.hibernate.cache.spi.access.AccessType accessType = hibernateCacheAnnotation.value("usage") == null ? getLocalBindingContext().getMappingDefaults().getCacheAccessType() : CacheConcurrencyStrategy.parse(hibernateCacheAnnotation.value("usage").asEnum()).toAccessType();
/* 499:    */       
/* 500:    */ 
/* 501:    */ 
/* 502:526 */       return new Caching(hibernateCacheAnnotation.value("region") == null ? getName() : hibernateCacheAnnotation.value("region").asString(), accessType, (hibernateCacheAnnotation.value("include") != null) && ("all".equals(hibernateCacheAnnotation.value("include").asString())));
/* 503:    */     }
/* 504:536 */     AnnotationInstance jpaCacheableAnnotation = JandexHelper.getSingleAnnotation(getClassInfo(), JPADotNames.CACHEABLE);
/* 505:    */     
/* 506:    */ 
/* 507:    */ 
/* 508:540 */     boolean cacheable = true;
/* 509:541 */     if ((jpaCacheableAnnotation != null) && (jpaCacheableAnnotation.value() != null)) {
/* 510:542 */       cacheable = jpaCacheableAnnotation.value().asBoolean();
/* 511:    */     }
/* 512:    */     boolean doCaching;
/* 513:546 */     switch (1.$SwitchMap$javax$persistence$SharedCacheMode[getLocalBindingContext().getMetadataImplementor().getOptions().getSharedCacheMode().ordinal()])
/* 514:    */     {
/* 515:    */     case 1: 
/* 516:548 */       doCaching = true;
/* 517:549 */       break;
/* 518:    */     case 2: 
/* 519:552 */       doCaching = cacheable;
/* 520:553 */       break;
/* 521:    */     case 3: 
/* 522:556 */       doCaching = (jpaCacheableAnnotation == null) || (cacheable);
/* 523:557 */       break;
/* 524:    */     default: 
/* 525:561 */       doCaching = false;
/* 526:    */     }
/* 527:566 */     if (!doCaching) {
/* 528:567 */       return null;
/* 529:    */     }
/* 530:570 */     return new Caching(getName(), getLocalBindingContext().getMappingDefaults().getCacheAccessType(), true);
/* 531:    */   }
/* 532:    */   
/* 533:    */   private TableSource createTableSource(AnnotationInstance tableAnnotation)
/* 534:    */   {
/* 535:585 */     String schema = null;
/* 536:586 */     String catalog = null;
/* 537:587 */     if (tableAnnotation != null)
/* 538:    */     {
/* 539:588 */       schema = (String)JandexHelper.getValue(tableAnnotation, "schema", String.class);
/* 540:589 */       catalog = (String)JandexHelper.getValue(tableAnnotation, "catalog", String.class);
/* 541:    */     }
/* 542:592 */     String tableName = null;
/* 543:593 */     String logicalTableName = null;
/* 544:595 */     if (tableAnnotation != null)
/* 545:    */     {
/* 546:596 */       logicalTableName = (String)JandexHelper.getValue(tableAnnotation, "name", String.class);
/* 547:597 */       if (StringHelper.isNotEmpty(logicalTableName)) {
/* 548:598 */         tableName = logicalTableName;
/* 549:    */       }
/* 550:600 */       createUniqueConstraints(tableAnnotation, tableName);
/* 551:    */     }
/* 552:    */     TableSourceImpl tableSourceImpl;
/* 553:    */     TableSourceImpl tableSourceImpl;
/* 554:604 */     if ((tableAnnotation == null) || (JPADotNames.TABLE.equals(tableAnnotation.name()))) {
/* 555:606 */       tableSourceImpl = new TableSourceImpl(schema, catalog, tableName, null);
/* 556:    */     } else {
/* 557:610 */       tableSourceImpl = new TableSourceImpl(schema, catalog, tableName, logicalTableName);
/* 558:    */     }
/* 559:612 */     return tableSourceImpl;
/* 560:    */   }
/* 561:    */   
/* 562:    */   private Set<TableSource> createSecondaryTableSources()
/* 563:    */   {
/* 564:616 */     Set<TableSource> secondaryTableSources = new HashSet();
/* 565:617 */     AnnotationInstance secondaryTables = JandexHelper.getSingleAnnotation(getClassInfo(), JPADotNames.SECONDARY_TABLES);
/* 566:    */     
/* 567:    */ 
/* 568:    */ 
/* 569:621 */     AnnotationInstance secondaryTable = JandexHelper.getSingleAnnotation(getClassInfo(), JPADotNames.SECONDARY_TABLE);
/* 570:    */     
/* 571:    */ 
/* 572:    */ 
/* 573:    */ 
/* 574:626 */     List<AnnotationInstance> secondaryTableAnnotations = new ArrayList();
/* 575:627 */     if (secondaryTable != null) {
/* 576:628 */       secondaryTableAnnotations.add(secondaryTable);
/* 577:    */     }
/* 578:633 */     if (secondaryTables != null) {
/* 579:634 */       secondaryTableAnnotations.addAll(Arrays.asList((Object[])JandexHelper.getValue(secondaryTables, "value", [Lorg.jboss.jandex.AnnotationInstance.class)));
/* 580:    */     }
/* 581:642 */     for (AnnotationInstance annotationInstance : secondaryTableAnnotations) {
/* 582:643 */       secondaryTableSources.add(createTableSource(annotationInstance));
/* 583:    */     }
/* 584:646 */     return secondaryTableSources;
/* 585:    */   }
/* 586:    */   
/* 587:    */   private void createUniqueConstraints(AnnotationInstance tableAnnotation, String tableName)
/* 588:    */   {
/* 589:651 */     AnnotationValue value = tableAnnotation.value("uniqueConstraints");
/* 590:652 */     if (value == null) {
/* 591:653 */       return;
/* 592:    */     }
/* 593:656 */     AnnotationInstance[] uniqueConstraints = value.asNestedArray();
/* 594:657 */     for (AnnotationInstance unique : uniqueConstraints)
/* 595:    */     {
/* 596:658 */       String name = unique.value("name") == null ? null : unique.value("name").asString();
/* 597:659 */       String[] columnNames = unique.value("columnNames").asStringArray();
/* 598:660 */       UniqueConstraintSourceImpl uniqueConstraintSource = new UniqueConstraintSourceImpl(name, tableName, Arrays.asList(columnNames));
/* 599:    */       
/* 600:    */ 
/* 601:    */ 
/* 602:664 */       this.constraintSources.add(uniqueConstraintSource);
/* 603:    */     }
/* 604:    */   }
/* 605:    */   
/* 606:    */   private String determineCustomLoader()
/* 607:    */   {
/* 608:669 */     String customLoader = null;
/* 609:    */     
/* 610:671 */     AnnotationInstance sqlLoaderAnnotation = JandexHelper.getSingleAnnotation(getClassInfo(), HibernateDotNames.LOADER);
/* 611:674 */     if (sqlLoaderAnnotation != null) {
/* 612:675 */       customLoader = sqlLoaderAnnotation.value("namedQuery").asString();
/* 613:    */     }
/* 614:677 */     return customLoader;
/* 615:    */   }
/* 616:    */   
/* 617:    */   private CustomSQL createCustomSQL(AnnotationInstance customSqlAnnotation)
/* 618:    */   {
/* 619:681 */     if (customSqlAnnotation == null) {
/* 620:682 */       return null;
/* 621:    */     }
/* 622:685 */     String sql = customSqlAnnotation.value("sql").asString();
/* 623:686 */     boolean isCallable = (customSqlAnnotation.value("callable") != null) && (customSqlAnnotation.value("callable").asBoolean());
/* 624:    */     
/* 625:    */ 
/* 626:689 */     ExecuteUpdateResultCheckStyle checkStyle = customSqlAnnotation.value("check") == null ? ExecuteUpdateResultCheckStyle.COUNT : isCallable ? ExecuteUpdateResultCheckStyle.NONE : ExecuteUpdateResultCheckStyle.valueOf(customSqlAnnotation.value("check").asEnum());
/* 627:    */     
/* 628:    */ 
/* 629:    */ 
/* 630:    */ 
/* 631:    */ 
/* 632:695 */     return new CustomSQL(sql, isCallable, checkStyle);
/* 633:    */   }
/* 634:    */   
/* 635:    */   private void processCustomSqlAnnotations()
/* 636:    */   {
/* 637:700 */     AnnotationInstance sqlInsertAnnotation = JandexHelper.getSingleAnnotation(getClassInfo(), HibernateDotNames.SQL_INSERT);
/* 638:    */     
/* 639:    */ 
/* 640:703 */     this.customInsert = createCustomSQL(sqlInsertAnnotation);
/* 641:    */     
/* 642:    */ 
/* 643:706 */     AnnotationInstance sqlUpdateAnnotation = JandexHelper.getSingleAnnotation(getClassInfo(), HibernateDotNames.SQL_UPDATE);
/* 644:    */     
/* 645:    */ 
/* 646:709 */     this.customUpdate = createCustomSQL(sqlUpdateAnnotation);
/* 647:    */     
/* 648:    */ 
/* 649:712 */     AnnotationInstance sqlDeleteAnnotation = JandexHelper.getSingleAnnotation(getClassInfo(), HibernateDotNames.SQL_DELETE);
/* 650:    */     
/* 651:    */ 
/* 652:715 */     this.customDelete = createCustomSQL(sqlDeleteAnnotation);
/* 653:    */   }
/* 654:    */   
/* 655:    */   private List<String> determineSynchronizedTableNames()
/* 656:    */   {
/* 657:719 */     AnnotationInstance synchronizeAnnotation = JandexHelper.getSingleAnnotation(getClassInfo(), HibernateDotNames.SYNCHRONIZE);
/* 658:722 */     if (synchronizeAnnotation != null)
/* 659:    */     {
/* 660:723 */       String[] tableNames = synchronizeAnnotation.value().asStringArray();
/* 661:724 */       return Arrays.asList(tableNames);
/* 662:    */     }
/* 663:727 */     return Collections.emptyList();
/* 664:    */   }
/* 665:    */   
/* 666:    */   private void processProxyGeneration()
/* 667:    */   {
/* 668:733 */     AnnotationInstance hibernateProxyAnnotation = JandexHelper.getSingleAnnotation(getClassInfo(), HibernateDotNames.PROXY);
/* 669:736 */     if (hibernateProxyAnnotation != null)
/* 670:    */     {
/* 671:737 */       this.isLazy = ((hibernateProxyAnnotation.value("lazy") == null) || (hibernateProxyAnnotation.value("lazy").asBoolean()));
/* 672:739 */       if (this.isLazy)
/* 673:    */       {
/* 674:740 */         AnnotationValue proxyClassValue = hibernateProxyAnnotation.value("proxyClass");
/* 675:741 */         if (proxyClassValue == null) {
/* 676:742 */           this.proxy = getName();
/* 677:    */         } else {
/* 678:745 */           this.proxy = proxyClassValue.asString();
/* 679:    */         }
/* 680:    */       }
/* 681:    */       else
/* 682:    */       {
/* 683:749 */         this.proxy = null;
/* 684:    */       }
/* 685:    */     }
/* 686:    */     else
/* 687:    */     {
/* 688:753 */       this.isLazy = true;
/* 689:754 */       this.proxy = getName();
/* 690:    */     }
/* 691:    */   }
/* 692:    */   
/* 693:    */   private int determineBatchSize()
/* 694:    */   {
/* 695:759 */     AnnotationInstance batchSizeAnnotation = JandexHelper.getSingleAnnotation(getClassInfo(), HibernateDotNames.BATCH_SIZE);
/* 696:    */     
/* 697:    */ 
/* 698:762 */     return batchSizeAnnotation == null ? -1 : batchSizeAnnotation.value("size").asInt();
/* 699:    */   }
/* 700:    */   
/* 701:    */   private List<JpaCallbackClass> determineEntityListeners()
/* 702:    */   {
/* 703:766 */     List<JpaCallbackClass> callbackClassList = new ArrayList();
/* 704:769 */     if (JandexHelper.getSingleAnnotation(getClassInfo(), JPADotNames.EXCLUDE_DEFAULT_LISTENERS) == null)
/* 705:    */     {
/* 706:770 */       List<AnnotationInstance> defaultEntityListenerAnnotations = getLocalBindingContext().getIndex().getAnnotations(PseudoJpaDotNames.DEFAULT_ENTITY_LISTENERS);
/* 707:772 */       for (AnnotationInstance annotation : defaultEntityListenerAnnotations) {
/* 708:773 */         for (Type callbackClass : annotation.value().asClassArray())
/* 709:    */         {
/* 710:774 */           String callbackClassName = callbackClass.name().toString();
/* 711:    */           try
/* 712:    */           {
/* 713:776 */             processDefaultJpaCallbacks(callbackClassName, callbackClassList);
/* 714:    */           }
/* 715:    */           catch (PersistenceException error)
/* 716:    */           {
/* 717:779 */             throw new PersistenceException(error.getMessage() + "default entity listener " + callbackClassName);
/* 718:    */           }
/* 719:    */         }
/* 720:    */       }
/* 721:    */     }
/* 722:786 */     List<AnnotationInstance> annotationList = (List)getClassInfo().annotations().get(JPADotNames.ENTITY_LISTENERS);
/* 723:787 */     if (annotationList != null) {
/* 724:788 */       for (AnnotationInstance annotation : annotationList) {
/* 725:789 */         for (Type callbackClass : annotation.value().asClassArray())
/* 726:    */         {
/* 727:790 */           String callbackClassName = callbackClass.name().toString();
/* 728:    */           try
/* 729:    */           {
/* 730:792 */             processJpaCallbacks(callbackClassName, true, callbackClassList);
/* 731:    */           }
/* 732:    */           catch (PersistenceException error)
/* 733:    */           {
/* 734:795 */             throw new PersistenceException(error.getMessage() + "entity listener " + callbackClassName);
/* 735:    */           }
/* 736:    */         }
/* 737:    */       }
/* 738:    */     }
/* 739:    */     try
/* 740:    */     {
/* 741:803 */       processJpaCallbacks(getName(), false, callbackClassList);
/* 742:    */     }
/* 743:    */     catch (PersistenceException error)
/* 744:    */     {
/* 745:806 */       throw new PersistenceException(error.getMessage() + "entity/mapped superclass " + getClassInfo().name().toString());
/* 746:    */     }
/* 747:811 */     return callbackClassList;
/* 748:    */   }
/* 749:    */   
/* 750:    */   private void processDefaultJpaCallbacks(String instanceCallbackClassName, List<JpaCallbackClass> jpaCallbackClassList)
/* 751:    */   {
/* 752:815 */     ClassInfo callbackClassInfo = getLocalBindingContext().getClassInfo(instanceCallbackClassName);
/* 753:818 */     if (JandexHelper.getSingleAnnotation(callbackClassInfo, JPADotNames.EXCLUDE_SUPERCLASS_LISTENERS) != null)
/* 754:    */     {
/* 755:819 */       DotName superName = callbackClassInfo.superName();
/* 756:820 */       if (superName != null) {
/* 757:821 */         processDefaultJpaCallbacks(instanceCallbackClassName, jpaCallbackClassList);
/* 758:    */       }
/* 759:    */     }
/* 760:825 */     String callbackClassName = callbackClassInfo.name().toString();
/* 761:826 */     Map<Class<?>, String> callbacksByType = new HashMap();
/* 762:827 */     createDefaultCallback(PrePersist.class, PseudoJpaDotNames.DEFAULT_PRE_PERSIST, callbackClassName, callbacksByType);
/* 763:    */     
/* 764:    */ 
/* 765:830 */     createDefaultCallback(PreRemove.class, PseudoJpaDotNames.DEFAULT_PRE_REMOVE, callbackClassName, callbacksByType);
/* 766:    */     
/* 767:    */ 
/* 768:833 */     createDefaultCallback(PreUpdate.class, PseudoJpaDotNames.DEFAULT_PRE_UPDATE, callbackClassName, callbacksByType);
/* 769:    */     
/* 770:    */ 
/* 771:836 */     createDefaultCallback(PostLoad.class, PseudoJpaDotNames.DEFAULT_POST_LOAD, callbackClassName, callbacksByType);
/* 772:    */     
/* 773:    */ 
/* 774:839 */     createDefaultCallback(PostPersist.class, PseudoJpaDotNames.DEFAULT_POST_PERSIST, callbackClassName, callbacksByType);
/* 775:    */     
/* 776:    */ 
/* 777:842 */     createDefaultCallback(PostRemove.class, PseudoJpaDotNames.DEFAULT_POST_REMOVE, callbackClassName, callbacksByType);
/* 778:    */     
/* 779:    */ 
/* 780:845 */     createDefaultCallback(PostUpdate.class, PseudoJpaDotNames.DEFAULT_POST_UPDATE, callbackClassName, callbacksByType);
/* 781:848 */     if (!callbacksByType.isEmpty()) {
/* 782:849 */       jpaCallbackClassList.add(new JpaCallbackClassImpl(instanceCallbackClassName, callbacksByType, true, null));
/* 783:    */     }
/* 784:    */   }
/* 785:    */   
/* 786:    */   private void processJpaCallbacks(String instanceCallbackClassName, boolean isListener, List<JpaCallbackClass> callbackClassList)
/* 787:    */   {
/* 788:855 */     ClassInfo callbackClassInfo = getLocalBindingContext().getClassInfo(instanceCallbackClassName);
/* 789:858 */     if (JandexHelper.getSingleAnnotation(callbackClassInfo, JPADotNames.EXCLUDE_SUPERCLASS_LISTENERS) != null)
/* 790:    */     {
/* 791:859 */       DotName superName = callbackClassInfo.superName();
/* 792:860 */       if (superName != null) {
/* 793:861 */         processJpaCallbacks(instanceCallbackClassName, isListener, callbackClassList);
/* 794:    */       }
/* 795:    */     }
/* 796:869 */     Map<Class<?>, String> callbacksByType = new HashMap();
/* 797:870 */     createCallback(PrePersist.class, JPADotNames.PRE_PERSIST, callbacksByType, callbackClassInfo, isListener);
/* 798:871 */     createCallback(PreRemove.class, JPADotNames.PRE_REMOVE, callbacksByType, callbackClassInfo, isListener);
/* 799:872 */     createCallback(PreUpdate.class, JPADotNames.PRE_UPDATE, callbacksByType, callbackClassInfo, isListener);
/* 800:873 */     createCallback(PostLoad.class, JPADotNames.POST_LOAD, callbacksByType, callbackClassInfo, isListener);
/* 801:874 */     createCallback(PostPersist.class, JPADotNames.POST_PERSIST, callbacksByType, callbackClassInfo, isListener);
/* 802:875 */     createCallback(PostRemove.class, JPADotNames.POST_REMOVE, callbacksByType, callbackClassInfo, isListener);
/* 803:876 */     createCallback(PostUpdate.class, JPADotNames.POST_UPDATE, callbacksByType, callbackClassInfo, isListener);
/* 804:877 */     if (!callbacksByType.isEmpty()) {
/* 805:878 */       callbackClassList.add(new JpaCallbackClassImpl(instanceCallbackClassName, callbacksByType, isListener, null));
/* 806:    */     }
/* 807:    */   }
/* 808:    */   
/* 809:    */   private void createDefaultCallback(Class callbackTypeClass, DotName callbackTypeName, String callbackClassName, Map<Class<?>, String> callbacksByClass)
/* 810:    */   {
/* 811:886 */     for (AnnotationInstance callback : getLocalBindingContext().getIndex().getAnnotations(callbackTypeName))
/* 812:    */     {
/* 813:887 */       MethodInfo methodInfo = (MethodInfo)callback.target();
/* 814:888 */       validateMethod(methodInfo, callbackTypeClass, callbacksByClass, true);
/* 815:889 */       if (methodInfo.declaringClass().name().toString().equals(callbackClassName))
/* 816:    */       {
/* 817:890 */         if (methodInfo.args().length != 1) {
/* 818:891 */           throw new PersistenceException(String.format("Callback method %s must have exactly one argument defined as either Object or %s in ", new Object[] { methodInfo.name(), getEntityName() }));
/* 819:    */         }
/* 820:899 */         callbacksByClass.put(callbackTypeClass, methodInfo.name());
/* 821:    */       }
/* 822:    */     }
/* 823:    */   }
/* 824:    */   
/* 825:    */   private void createCallback(Class callbackTypeClass, DotName callbackTypeName, Map<Class<?>, String> callbacksByClass, ClassInfo callbackClassInfo, boolean isListener)
/* 826:    */   {
/* 827:909 */     Map<DotName, List<AnnotationInstance>> annotations = callbackClassInfo.annotations();
/* 828:910 */     List<AnnotationInstance> annotationInstances = (List)annotations.get(callbackTypeName);
/* 829:911 */     if (annotationInstances == null) {
/* 830:912 */       return;
/* 831:    */     }
/* 832:914 */     for (AnnotationInstance callbackAnnotation : annotationInstances)
/* 833:    */     {
/* 834:915 */       MethodInfo methodInfo = (MethodInfo)callbackAnnotation.target();
/* 835:916 */       validateMethod(methodInfo, callbackTypeClass, callbacksByClass, isListener);
/* 836:917 */       callbacksByClass.put(callbackTypeClass, methodInfo.name());
/* 837:    */     }
/* 838:    */   }
/* 839:    */   
/* 840:    */   private void validateMethod(MethodInfo methodInfo, Class callbackTypeClass, Map<Class<?>, String> callbacksByClass, boolean isListener)
/* 841:    */   {
/* 842:925 */     if (methodInfo.returnType().kind() != Type.Kind.VOID) {
/* 843:926 */       throw new PersistenceException("Callback method " + methodInfo.name() + " must have a void return type in ");
/* 844:    */     }
/* 845:928 */     if ((Modifier.isStatic(methodInfo.flags())) || (Modifier.isFinal(methodInfo.flags()))) {
/* 846:929 */       throw new PersistenceException("Callback method " + methodInfo.name() + " must not be static or final in ");
/* 847:    */     }
/* 848:931 */     Type[] argTypes = methodInfo.args();
/* 849:932 */     if (isListener)
/* 850:    */     {
/* 851:933 */       if (argTypes.length != 1) {
/* 852:934 */         throw new PersistenceException("Callback method " + methodInfo.name() + " must have exactly one argument in ");
/* 853:    */       }
/* 854:936 */       String argTypeName = argTypes[0].name().toString();
/* 855:937 */       if ((!argTypeName.equals(Object.class.getName())) && (!argTypeName.equals(getName()))) {
/* 856:938 */         throw new PersistenceException("The argument for callback method " + methodInfo.name() + " must be defined as either Object or " + getEntityName() + " in ");
/* 857:    */       }
/* 858:    */     }
/* 859:944 */     else if (argTypes.length != 0)
/* 860:    */     {
/* 861:945 */       throw new PersistenceException("Callback method " + methodInfo.name() + " must have no arguments in ");
/* 862:    */     }
/* 863:947 */     if (callbacksByClass.containsKey(callbackTypeClass)) {
/* 864:948 */       throw new PersistenceException("Only one method may be annotated as a " + callbackTypeClass.getSimpleName() + " callback method in ");
/* 865:    */     }
/* 866:    */   }
/* 867:    */   
/* 868:    */   private class JpaCallbackClassImpl
/* 869:    */     implements JpaCallbackClass
/* 870:    */   {
/* 871:    */     private final Map<Class<?>, String> callbacksByType;
/* 872:    */     private final String name;
/* 873:    */     private final boolean isListener;
/* 874:    */     
/* 875:    */     private JpaCallbackClassImpl(Map<Class<?>, String> name, boolean callbacksByType)
/* 876:    */     {
/* 877:966 */       this.name = name;
/* 878:967 */       this.callbacksByType = callbacksByType;
/* 879:968 */       this.isListener = isListener;
/* 880:    */     }
/* 881:    */     
/* 882:    */     public String getCallbackMethod(Class<?> callbackType)
/* 883:    */     {
/* 884:973 */       return (String)this.callbacksByType.get(callbackType);
/* 885:    */     }
/* 886:    */     
/* 887:    */     public String getName()
/* 888:    */     {
/* 889:978 */       return this.name;
/* 890:    */     }
/* 891:    */     
/* 892:    */     public boolean isListener()
/* 893:    */     {
/* 894:983 */       return this.isListener;
/* 895:    */     }
/* 896:    */   }
/* 897:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.annotations.entity.EntityClass
 * JD-Core Version:    0.7.0.1
 */