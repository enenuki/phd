/*   1:    */ package org.hibernate.cfg.annotations;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Map.Entry;
/*   9:    */ import javax.persistence.Access;
/*  10:    */ import javax.persistence.JoinColumn;
/*  11:    */ import javax.persistence.JoinTable;
/*  12:    */ import javax.persistence.PrimaryKeyJoinColumn;
/*  13:    */ import javax.persistence.SecondaryTable;
/*  14:    */ import javax.persistence.SecondaryTables;
/*  15:    */ import org.hibernate.AnnotationException;
/*  16:    */ import org.hibernate.AssertionFailure;
/*  17:    */ import org.hibernate.EntityMode;
/*  18:    */ import org.hibernate.MappingException;
/*  19:    */ import org.hibernate.annotations.BatchSize;
/*  20:    */ import org.hibernate.annotations.Cache;
/*  21:    */ import org.hibernate.annotations.CacheConcurrencyStrategy;
/*  22:    */ import org.hibernate.annotations.FetchMode;
/*  23:    */ import org.hibernate.annotations.ForeignKey;
/*  24:    */ import org.hibernate.annotations.Immutable;
/*  25:    */ import org.hibernate.annotations.Loader;
/*  26:    */ import org.hibernate.annotations.OptimisticLockType;
/*  27:    */ import org.hibernate.annotations.Persister;
/*  28:    */ import org.hibernate.annotations.PolymorphismType;
/*  29:    */ import org.hibernate.annotations.Proxy;
/*  30:    */ import org.hibernate.annotations.ResultCheckStyle;
/*  31:    */ import org.hibernate.annotations.RowId;
/*  32:    */ import org.hibernate.annotations.SQLDelete;
/*  33:    */ import org.hibernate.annotations.SQLDeleteAll;
/*  34:    */ import org.hibernate.annotations.SQLInsert;
/*  35:    */ import org.hibernate.annotations.SQLUpdate;
/*  36:    */ import org.hibernate.annotations.Subselect;
/*  37:    */ import org.hibernate.annotations.Synchronize;
/*  38:    */ import org.hibernate.annotations.Tables;
/*  39:    */ import org.hibernate.annotations.Tuplizer;
/*  40:    */ import org.hibernate.annotations.Tuplizers;
/*  41:    */ import org.hibernate.annotations.Where;
/*  42:    */ import org.hibernate.annotations.common.reflection.ReflectionManager;
/*  43:    */ import org.hibernate.annotations.common.reflection.XAnnotatedElement;
/*  44:    */ import org.hibernate.annotations.common.reflection.XClass;
/*  45:    */ import org.hibernate.cfg.AnnotationBinder;
/*  46:    */ import org.hibernate.cfg.BinderHelper;
/*  47:    */ import org.hibernate.cfg.Ejb3JoinColumn;
/*  48:    */ import org.hibernate.cfg.InheritanceState;
/*  49:    */ import org.hibernate.cfg.Mappings;
/*  50:    */ import org.hibernate.cfg.NamingStrategy;
/*  51:    */ import org.hibernate.cfg.ObjectNameNormalizer.NamingStrategyHelper;
/*  52:    */ import org.hibernate.cfg.ObjectNameSource;
/*  53:    */ import org.hibernate.cfg.PropertyHolder;
/*  54:    */ import org.hibernate.cfg.UniqueConstraintHolder;
/*  55:    */ import org.hibernate.engine.spi.ExecuteUpdateResultCheckStyle;
/*  56:    */ import org.hibernate.engine.spi.FilterDefinition;
/*  57:    */ import org.hibernate.internal.CoreMessageLogger;
/*  58:    */ import org.hibernate.internal.util.ReflectHelper;
/*  59:    */ import org.hibernate.internal.util.StringHelper;
/*  60:    */ import org.hibernate.mapping.DependantValue;
/*  61:    */ import org.hibernate.mapping.Join;
/*  62:    */ import org.hibernate.mapping.PersistentClass;
/*  63:    */ import org.hibernate.mapping.RootClass;
/*  64:    */ import org.hibernate.mapping.SimpleValue;
/*  65:    */ import org.hibernate.mapping.TableOwner;
/*  66:    */ import org.hibernate.mapping.Value;
/*  67:    */ import org.hibernate.type.Type;
/*  68:    */ import org.jboss.logging.Logger;
/*  69:    */ 
/*  70:    */ public class EntityBinder
/*  71:    */ {
/*  72: 99 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, EntityBinder.class.getName());
/*  73:    */   private String name;
/*  74:    */   private XClass annotatedClass;
/*  75:    */   private PersistentClass persistentClass;
/*  76:    */   private Mappings mappings;
/*  77:105 */   private String discriminatorValue = "";
/*  78:    */   private Boolean forceDiscriminator;
/*  79:    */   private Boolean insertableDiscriminator;
/*  80:    */   private boolean dynamicInsert;
/*  81:    */   private boolean dynamicUpdate;
/*  82:    */   private boolean explicitHibernateEntityAnnotation;
/*  83:    */   private OptimisticLockType optimisticLockType;
/*  84:    */   private PolymorphismType polymorphismType;
/*  85:    */   private boolean selectBeforeUpdate;
/*  86:    */   private int batchSize;
/*  87:    */   private boolean lazy;
/*  88:    */   private XClass proxyClass;
/*  89:    */   private String where;
/*  90:118 */   private Map<String, Join> secondaryTables = new HashMap();
/*  91:119 */   private Map<String, Object> secondaryTableJoins = new HashMap();
/*  92:    */   private String cacheConcurrentStrategy;
/*  93:    */   private String cacheRegion;
/*  94:122 */   private Map<String, String> filters = new HashMap();
/*  95:    */   private InheritanceState inheritanceState;
/*  96:    */   private boolean ignoreIdAnnotations;
/*  97:    */   private boolean cacheLazyProperty;
/*  98:126 */   private org.hibernate.cfg.AccessType propertyAccessType = org.hibernate.cfg.AccessType.DEFAULT;
/*  99:    */   private boolean wrapIdsInEmbeddedComponents;
/* 100:    */   private String subselect;
/* 101:    */   
/* 102:    */   public boolean wrapIdsInEmbeddedComponents()
/* 103:    */   {
/* 104:131 */     return this.wrapIdsInEmbeddedComponents;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public EntityBinder() {}
/* 108:    */   
/* 109:    */   public EntityBinder(javax.persistence.Entity ejb3Ann, org.hibernate.annotations.Entity hibAnn, XClass annotatedClass, PersistentClass persistentClass, Mappings mappings)
/* 110:    */   {
/* 111:146 */     this.mappings = mappings;
/* 112:147 */     this.persistentClass = persistentClass;
/* 113:148 */     this.annotatedClass = annotatedClass;
/* 114:149 */     bindEjb3Annotation(ejb3Ann);
/* 115:150 */     bindHibernateAnnotation(hibAnn);
/* 116:    */   }
/* 117:    */   
/* 118:    */   private void bindHibernateAnnotation(org.hibernate.annotations.Entity hibAnn)
/* 119:    */   {
/* 120:154 */     if (hibAnn != null)
/* 121:    */     {
/* 122:155 */       this.dynamicInsert = hibAnn.dynamicInsert();
/* 123:156 */       this.dynamicUpdate = hibAnn.dynamicUpdate();
/* 124:157 */       this.optimisticLockType = hibAnn.optimisticLock();
/* 125:158 */       this.selectBeforeUpdate = hibAnn.selectBeforeUpdate();
/* 126:159 */       this.polymorphismType = hibAnn.polymorphism();
/* 127:160 */       this.explicitHibernateEntityAnnotation = true;
/* 128:    */     }
/* 129:    */     else
/* 130:    */     {
/* 131:165 */       this.dynamicInsert = false;
/* 132:166 */       this.dynamicUpdate = false;
/* 133:167 */       this.optimisticLockType = OptimisticLockType.VERSION;
/* 134:168 */       this.polymorphismType = PolymorphismType.IMPLICIT;
/* 135:169 */       this.selectBeforeUpdate = false;
/* 136:    */     }
/* 137:    */   }
/* 138:    */   
/* 139:    */   private void bindEjb3Annotation(javax.persistence.Entity ejb3Ann)
/* 140:    */   {
/* 141:174 */     if (ejb3Ann == null) {
/* 142:174 */       throw new AssertionFailure("@Entity should always be not null");
/* 143:    */     }
/* 144:175 */     if (BinderHelper.isEmptyAnnotationValue(ejb3Ann.name())) {
/* 145:176 */       this.name = StringHelper.unqualify(this.annotatedClass.getName());
/* 146:    */     } else {
/* 147:179 */       this.name = ejb3Ann.name();
/* 148:    */     }
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setDiscriminatorValue(String discriminatorValue)
/* 152:    */   {
/* 153:184 */     this.discriminatorValue = discriminatorValue;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public void setForceDiscriminator(boolean forceDiscriminator)
/* 157:    */   {
/* 158:188 */     this.forceDiscriminator = Boolean.valueOf(forceDiscriminator);
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setInsertableDiscriminator(boolean insertableDiscriminator)
/* 162:    */   {
/* 163:192 */     this.insertableDiscriminator = Boolean.valueOf(insertableDiscriminator);
/* 164:    */   }
/* 165:    */   
/* 166:    */   public void bindEntity()
/* 167:    */   {
/* 168:196 */     this.persistentClass.setAbstract(Boolean.valueOf(this.annotatedClass.isAbstract()));
/* 169:197 */     this.persistentClass.setClassName(this.annotatedClass.getName());
/* 170:198 */     this.persistentClass.setNodeName(this.name);
/* 171:199 */     this.persistentClass.setJpaEntityName(this.name);
/* 172:    */     
/* 173:201 */     this.persistentClass.setEntityName(this.annotatedClass.getName());
/* 174:202 */     bindDiscriminatorValue();
/* 175:    */     
/* 176:204 */     this.persistentClass.setLazy(this.lazy);
/* 177:205 */     if (this.proxyClass != null) {
/* 178:206 */       this.persistentClass.setProxyInterfaceName(this.proxyClass.getName());
/* 179:    */     }
/* 180:208 */     this.persistentClass.setDynamicInsert(this.dynamicInsert);
/* 181:209 */     this.persistentClass.setDynamicUpdate(this.dynamicUpdate);
/* 182:211 */     if ((this.persistentClass instanceof RootClass))
/* 183:    */     {
/* 184:212 */       RootClass rootClass = (RootClass)this.persistentClass;
/* 185:213 */       boolean mutable = true;
/* 186:215 */       if (this.annotatedClass.isAnnotationPresent(Immutable.class))
/* 187:    */       {
/* 188:216 */         mutable = false;
/* 189:    */       }
/* 190:    */       else
/* 191:    */       {
/* 192:219 */         org.hibernate.annotations.Entity entityAnn = (org.hibernate.annotations.Entity)this.annotatedClass.getAnnotation(org.hibernate.annotations.Entity.class);
/* 193:221 */         if (entityAnn != null) {
/* 194:222 */           mutable = entityAnn.mutable();
/* 195:    */         }
/* 196:    */       }
/* 197:225 */       rootClass.setMutable(mutable);
/* 198:226 */       rootClass.setExplicitPolymorphism(isExplicitPolymorphism(this.polymorphismType));
/* 199:227 */       if (StringHelper.isNotEmpty(this.where)) {
/* 200:227 */         rootClass.setWhere(this.where);
/* 201:    */       }
/* 202:228 */       if (this.cacheConcurrentStrategy != null)
/* 203:    */       {
/* 204:229 */         rootClass.setCacheConcurrencyStrategy(this.cacheConcurrentStrategy);
/* 205:230 */         rootClass.setCacheRegionName(this.cacheRegion);
/* 206:231 */         rootClass.setLazyPropertiesCacheable(this.cacheLazyProperty);
/* 207:    */       }
/* 208:233 */       if (this.forceDiscriminator != null) {
/* 209:234 */         rootClass.setForceDiscriminator(this.forceDiscriminator.booleanValue());
/* 210:    */       }
/* 211:236 */       if (this.insertableDiscriminator != null) {
/* 212:237 */         rootClass.setDiscriminatorInsertable(this.insertableDiscriminator.booleanValue());
/* 213:    */       }
/* 214:    */     }
/* 215:    */     else
/* 216:    */     {
/* 217:241 */       if (this.explicitHibernateEntityAnnotation) {
/* 218:242 */         LOG.entityAnnotationOnNonRoot(this.annotatedClass.getName());
/* 219:    */       }
/* 220:244 */       if (this.annotatedClass.isAnnotationPresent(Immutable.class)) {
/* 221:245 */         LOG.immutableAnnotationOnNonRoot(this.annotatedClass.getName());
/* 222:    */       }
/* 223:    */     }
/* 224:248 */     this.persistentClass.setOptimisticLockMode(getVersioning(this.optimisticLockType));
/* 225:249 */     this.persistentClass.setSelectBeforeUpdate(this.selectBeforeUpdate);
/* 226:    */     
/* 227:    */ 
/* 228:252 */     Persister persisterAnn = (Persister)this.annotatedClass.getAnnotation(Persister.class);
/* 229:253 */     Class persister = null;
/* 230:254 */     if (persisterAnn != null)
/* 231:    */     {
/* 232:255 */       persister = persisterAnn.impl();
/* 233:    */     }
/* 234:    */     else
/* 235:    */     {
/* 236:258 */       org.hibernate.annotations.Entity entityAnn = (org.hibernate.annotations.Entity)this.annotatedClass.getAnnotation(org.hibernate.annotations.Entity.class);
/* 237:259 */       if ((entityAnn != null) && (!BinderHelper.isEmptyAnnotationValue(entityAnn.persister()))) {
/* 238:    */         try
/* 239:    */         {
/* 240:261 */           persister = ReflectHelper.classForName(entityAnn.persister());
/* 241:    */         }
/* 242:    */         catch (ClassNotFoundException cnfe)
/* 243:    */         {
/* 244:264 */           throw new AnnotationException("Could not find persister class: " + persister);
/* 245:    */         }
/* 246:    */       }
/* 247:    */     }
/* 248:268 */     if (persister != null) {
/* 249:269 */       this.persistentClass.setEntityPersisterClass(persister);
/* 250:    */     }
/* 251:272 */     this.persistentClass.setBatchSize(this.batchSize);
/* 252:    */     
/* 253:    */ 
/* 254:275 */     SQLInsert sqlInsert = (SQLInsert)this.annotatedClass.getAnnotation(SQLInsert.class);
/* 255:276 */     SQLUpdate sqlUpdate = (SQLUpdate)this.annotatedClass.getAnnotation(SQLUpdate.class);
/* 256:277 */     SQLDelete sqlDelete = (SQLDelete)this.annotatedClass.getAnnotation(SQLDelete.class);
/* 257:278 */     SQLDeleteAll sqlDeleteAll = (SQLDeleteAll)this.annotatedClass.getAnnotation(SQLDeleteAll.class);
/* 258:279 */     Loader loader = (Loader)this.annotatedClass.getAnnotation(Loader.class);
/* 259:281 */     if (sqlInsert != null) {
/* 260:282 */       this.persistentClass.setCustomSQLInsert(sqlInsert.sql().trim(), sqlInsert.callable(), ExecuteUpdateResultCheckStyle.fromExternalName(sqlInsert.check().toString().toLowerCase()));
/* 261:    */     }
/* 262:287 */     if (sqlUpdate != null) {
/* 263:288 */       this.persistentClass.setCustomSQLUpdate(sqlUpdate.sql(), sqlUpdate.callable(), ExecuteUpdateResultCheckStyle.fromExternalName(sqlUpdate.check().toString().toLowerCase()));
/* 264:    */     }
/* 265:292 */     if (sqlDelete != null) {
/* 266:293 */       this.persistentClass.setCustomSQLDelete(sqlDelete.sql(), sqlDelete.callable(), ExecuteUpdateResultCheckStyle.fromExternalName(sqlDelete.check().toString().toLowerCase()));
/* 267:    */     }
/* 268:297 */     if (sqlDeleteAll != null) {
/* 269:298 */       this.persistentClass.setCustomSQLDelete(sqlDeleteAll.sql(), sqlDeleteAll.callable(), ExecuteUpdateResultCheckStyle.fromExternalName(sqlDeleteAll.check().toString().toLowerCase()));
/* 270:    */     }
/* 271:302 */     if (loader != null) {
/* 272:303 */       this.persistentClass.setLoaderName(loader.namedQuery());
/* 273:    */     }
/* 274:306 */     if (this.annotatedClass.isAnnotationPresent(Synchronize.class))
/* 275:    */     {
/* 276:307 */       Synchronize synchronizedWith = (Synchronize)this.annotatedClass.getAnnotation(Synchronize.class);
/* 277:    */       
/* 278:309 */       String[] tables = synchronizedWith.value();
/* 279:310 */       for (String table : tables) {
/* 280:311 */         this.persistentClass.addSynchronizedTable(table);
/* 281:    */       }
/* 282:    */     }
/* 283:315 */     if (this.annotatedClass.isAnnotationPresent(Subselect.class))
/* 284:    */     {
/* 285:316 */       Subselect subselect = (Subselect)this.annotatedClass.getAnnotation(Subselect.class);
/* 286:317 */       this.subselect = subselect.value();
/* 287:    */     }
/* 288:321 */     if (this.annotatedClass.isAnnotationPresent(Tuplizers.class)) {
/* 289:322 */       for (Tuplizer tuplizer : ((Tuplizers)this.annotatedClass.getAnnotation(Tuplizers.class)).value())
/* 290:    */       {
/* 291:323 */         EntityMode mode = EntityMode.parse(tuplizer.entityMode());
/* 292:    */         
/* 293:325 */         this.persistentClass.addTuplizer(mode, tuplizer.impl().getName());
/* 294:    */       }
/* 295:    */     }
/* 296:328 */     if (this.annotatedClass.isAnnotationPresent(Tuplizer.class))
/* 297:    */     {
/* 298:329 */       Tuplizer tuplizer = (Tuplizer)this.annotatedClass.getAnnotation(Tuplizer.class);
/* 299:330 */       EntityMode mode = EntityMode.parse(tuplizer.entityMode());
/* 300:    */       
/* 301:332 */       this.persistentClass.addTuplizer(mode, tuplizer.impl().getName());
/* 302:    */     }
/* 303:335 */     if (!this.inheritanceState.hasParents()) {
/* 304:336 */       for (Map.Entry<String, String> filter : this.filters.entrySet())
/* 305:    */       {
/* 306:337 */         String filterName = (String)filter.getKey();
/* 307:338 */         String cond = (String)filter.getValue();
/* 308:339 */         if (BinderHelper.isEmptyAnnotationValue(cond))
/* 309:    */         {
/* 310:340 */           FilterDefinition definition = this.mappings.getFilterDefinition(filterName);
/* 311:341 */           cond = definition == null ? null : definition.getDefaultFilterCondition();
/* 312:342 */           if (StringHelper.isEmpty(cond)) {
/* 313:343 */             throw new AnnotationException("no filter condition found for filter " + filterName + " in " + this.name);
/* 314:    */           }
/* 315:    */         }
/* 316:348 */         this.persistentClass.addFilter(filterName, cond);
/* 317:    */       }
/* 318:351 */     } else if (this.filters.size() > 0) {
/* 319:352 */       LOG.filterAnnotationOnSubclass(this.persistentClass.getEntityName());
/* 320:    */     }
/* 321:354 */     LOG.debugf("Import with entity name %s", this.name);
/* 322:    */     try
/* 323:    */     {
/* 324:356 */       this.mappings.addImport(this.persistentClass.getEntityName(), this.name);
/* 325:357 */       String entityName = this.persistentClass.getEntityName();
/* 326:358 */       if (!entityName.equals(this.name)) {
/* 327:359 */         this.mappings.addImport(entityName, entityName);
/* 328:    */       }
/* 329:    */     }
/* 330:    */     catch (MappingException me)
/* 331:    */     {
/* 332:363 */       throw new AnnotationException("Use of the same entity name twice: " + this.name, me);
/* 333:    */     }
/* 334:    */   }
/* 335:    */   
/* 336:    */   public void bindDiscriminatorValue()
/* 337:    */   {
/* 338:368 */     if (StringHelper.isEmpty(this.discriminatorValue))
/* 339:    */     {
/* 340:369 */       Value discriminator = this.persistentClass.getDiscriminator();
/* 341:370 */       if (discriminator == null)
/* 342:    */       {
/* 343:371 */         this.persistentClass.setDiscriminatorValue(this.name);
/* 344:    */       }
/* 345:    */       else
/* 346:    */       {
/* 347:373 */         if ("character".equals(discriminator.getType().getName())) {
/* 348:374 */           throw new AnnotationException("Using default @DiscriminatorValue for a discriminator of type CHAR is not safe");
/* 349:    */         }
/* 350:378 */         if ("integer".equals(discriminator.getType().getName())) {
/* 351:379 */           this.persistentClass.setDiscriminatorValue(String.valueOf(this.name.hashCode()));
/* 352:    */         } else {
/* 353:382 */           this.persistentClass.setDiscriminatorValue(this.name);
/* 354:    */         }
/* 355:    */       }
/* 356:    */     }
/* 357:    */     else
/* 358:    */     {
/* 359:387 */       this.persistentClass.setDiscriminatorValue(this.discriminatorValue);
/* 360:    */     }
/* 361:    */   }
/* 362:    */   
/* 363:    */   int getVersioning(OptimisticLockType type)
/* 364:    */   {
/* 365:392 */     switch (1.$SwitchMap$org$hibernate$annotations$OptimisticLockType[type.ordinal()])
/* 366:    */     {
/* 367:    */     case 1: 
/* 368:394 */       return 0;
/* 369:    */     case 2: 
/* 370:396 */       return -1;
/* 371:    */     case 3: 
/* 372:398 */       return 1;
/* 373:    */     case 4: 
/* 374:400 */       return 2;
/* 375:    */     }
/* 376:402 */     throw new AssertionFailure("optimistic locking not supported: " + type);
/* 377:    */   }
/* 378:    */   
/* 379:    */   private boolean isExplicitPolymorphism(PolymorphismType type)
/* 380:    */   {
/* 381:407 */     switch (type)
/* 382:    */     {
/* 383:    */     case IMPLICIT: 
/* 384:409 */       return false;
/* 385:    */     case EXPLICIT: 
/* 386:411 */       return true;
/* 387:    */     }
/* 388:413 */     throw new AssertionFailure("Unknown polymorphism type: " + type);
/* 389:    */   }
/* 390:    */   
/* 391:    */   public void setBatchSize(BatchSize sizeAnn)
/* 392:    */   {
/* 393:418 */     if (sizeAnn != null) {
/* 394:419 */       this.batchSize = sizeAnn.size();
/* 395:    */     } else {
/* 396:422 */       this.batchSize = -1;
/* 397:    */     }
/* 398:    */   }
/* 399:    */   
/* 400:    */   public void setProxy(Proxy proxy)
/* 401:    */   {
/* 402:428 */     if (proxy != null)
/* 403:    */     {
/* 404:429 */       this.lazy = proxy.lazy();
/* 405:430 */       if (!this.lazy) {
/* 406:431 */         this.proxyClass = null;
/* 407:434 */       } else if (AnnotationBinder.isDefault(this.mappings.getReflectionManager().toXClass(proxy.proxyClass()), this.mappings)) {
/* 408:437 */         this.proxyClass = this.annotatedClass;
/* 409:    */       } else {
/* 410:440 */         this.proxyClass = this.mappings.getReflectionManager().toXClass(proxy.proxyClass());
/* 411:    */       }
/* 412:    */     }
/* 413:    */     else
/* 414:    */     {
/* 415:445 */       this.lazy = true;
/* 416:446 */       this.proxyClass = this.annotatedClass;
/* 417:    */     }
/* 418:    */   }
/* 419:    */   
/* 420:    */   public void setWhere(Where whereAnn)
/* 421:    */   {
/* 422:451 */     if (whereAnn != null) {
/* 423:452 */       this.where = whereAnn.clause();
/* 424:    */     }
/* 425:    */   }
/* 426:    */   
/* 427:    */   public void setWrapIdsInEmbeddedComponents(boolean wrapIdsInEmbeddedComponents)
/* 428:    */   {
/* 429:457 */     this.wrapIdsInEmbeddedComponents = wrapIdsInEmbeddedComponents;
/* 430:    */   }
/* 431:    */   
/* 432:    */   private static class EntityTableObjectNameSource
/* 433:    */     implements ObjectNameSource
/* 434:    */   {
/* 435:    */     private final String explicitName;
/* 436:    */     private final String logicalName;
/* 437:    */     
/* 438:    */     private EntityTableObjectNameSource(String explicitName, String entityName)
/* 439:    */     {
/* 440:466 */       this.explicitName = explicitName;
/* 441:467 */       this.logicalName = (StringHelper.isNotEmpty(explicitName) ? explicitName : StringHelper.unqualify(entityName));
/* 442:    */     }
/* 443:    */     
/* 444:    */     public String getExplicitName()
/* 445:    */     {
/* 446:473 */       return this.explicitName;
/* 447:    */     }
/* 448:    */     
/* 449:    */     public String getLogicalName()
/* 450:    */     {
/* 451:477 */       return this.logicalName;
/* 452:    */     }
/* 453:    */   }
/* 454:    */   
/* 455:    */   private static class EntityTableNamingStrategyHelper
/* 456:    */     implements ObjectNameNormalizer.NamingStrategyHelper
/* 457:    */   {
/* 458:    */     private final String entityName;
/* 459:    */     
/* 460:    */     private EntityTableNamingStrategyHelper(String entityName)
/* 461:    */     {
/* 462:485 */       this.entityName = entityName;
/* 463:    */     }
/* 464:    */     
/* 465:    */     public String determineImplicitName(NamingStrategy strategy)
/* 466:    */     {
/* 467:489 */       return strategy.classToTableName(this.entityName);
/* 468:    */     }
/* 469:    */     
/* 470:    */     public String handleExplicitName(NamingStrategy strategy, String name)
/* 471:    */     {
/* 472:493 */       return strategy.tableName(name);
/* 473:    */     }
/* 474:    */   }
/* 475:    */   
/* 476:    */   public void bindTable(String schema, String catalog, String tableName, List<UniqueConstraintHolder> uniqueConstraints, String constraints, org.hibernate.mapping.Table denormalizedSuperclassTable)
/* 477:    */   {
/* 478:504 */     EntityTableObjectNameSource tableNameContext = new EntityTableObjectNameSource(tableName, this.name, null);
/* 479:505 */     EntityTableNamingStrategyHelper namingStrategyHelper = new EntityTableNamingStrategyHelper(this.name, null);
/* 480:506 */     org.hibernate.mapping.Table table = TableBinder.buildAndFillTable(schema, catalog, tableNameContext, namingStrategyHelper, this.persistentClass.isAbstract().booleanValue(), uniqueConstraints, constraints, denormalizedSuperclassTable, this.mappings, this.subselect);
/* 481:    */     
/* 482:    */ 
/* 483:    */ 
/* 484:    */ 
/* 485:    */ 
/* 486:    */ 
/* 487:    */ 
/* 488:    */ 
/* 489:    */ 
/* 490:    */ 
/* 491:    */ 
/* 492:518 */     RowId rowId = (RowId)this.annotatedClass.getAnnotation(RowId.class);
/* 493:519 */     if (rowId != null) {
/* 494:520 */       table.setRowId(rowId.value());
/* 495:    */     }
/* 496:523 */     if ((this.persistentClass instanceof TableOwner))
/* 497:    */     {
/* 498:524 */       LOG.debugf("Bind entity %s on table %s", this.persistentClass.getEntityName(), table.getName());
/* 499:525 */       ((TableOwner)this.persistentClass).setTable(table);
/* 500:    */     }
/* 501:    */     else
/* 502:    */     {
/* 503:528 */       throw new AssertionFailure("binding a table for a subclass");
/* 504:    */     }
/* 505:    */   }
/* 506:    */   
/* 507:    */   public void finalSecondaryTableBinding(PropertyHolder propertyHolder)
/* 508:    */   {
/* 509:537 */     Iterator joins = this.secondaryTables.values().iterator();
/* 510:538 */     Iterator joinColumns = this.secondaryTableJoins.values().iterator();
/* 511:540 */     while (joins.hasNext())
/* 512:    */     {
/* 513:541 */       Object uncastedColumn = joinColumns.next();
/* 514:542 */       Join join = (Join)joins.next();
/* 515:543 */       createPrimaryColumnsToSecondaryTable(uncastedColumn, propertyHolder, join);
/* 516:    */     }
/* 517:545 */     this.mappings.addJoins(this.persistentClass, this.secondaryTables);
/* 518:    */   }
/* 519:    */   
/* 520:    */   private void createPrimaryColumnsToSecondaryTable(Object uncastedColumn, PropertyHolder propertyHolder, Join join)
/* 521:    */   {
/* 522:550 */     PrimaryKeyJoinColumn[] pkColumnsAnn = null;
/* 523:551 */     JoinColumn[] joinColumnsAnn = null;
/* 524:552 */     if ((uncastedColumn instanceof PrimaryKeyJoinColumn[])) {
/* 525:553 */       pkColumnsAnn = (PrimaryKeyJoinColumn[])uncastedColumn;
/* 526:    */     }
/* 527:555 */     if ((uncastedColumn instanceof JoinColumn[])) {
/* 528:556 */       joinColumnsAnn = (JoinColumn[])uncastedColumn;
/* 529:    */     }
/* 530:    */     Ejb3JoinColumn[] ejb3JoinColumns;
/* 531:558 */     if ((pkColumnsAnn == null) && (joinColumnsAnn == null))
/* 532:    */     {
/* 533:559 */       Ejb3JoinColumn[] ejb3JoinColumns = new Ejb3JoinColumn[1];
/* 534:560 */       ejb3JoinColumns[0] = Ejb3JoinColumn.buildJoinColumn(null, null, this.persistentClass.getIdentifier(), this.secondaryTables, propertyHolder, this.mappings);
/* 535:    */     }
/* 536:    */     else
/* 537:    */     {
/* 538:569 */       int nbrOfJoinColumns = pkColumnsAnn != null ? pkColumnsAnn.length : joinColumnsAnn.length;
/* 539:572 */       if (nbrOfJoinColumns == 0)
/* 540:    */       {
/* 541:573 */         Ejb3JoinColumn[] ejb3JoinColumns = new Ejb3JoinColumn[1];
/* 542:574 */         ejb3JoinColumns[0] = Ejb3JoinColumn.buildJoinColumn(null, null, this.persistentClass.getIdentifier(), this.secondaryTables, propertyHolder, this.mappings);
/* 543:    */       }
/* 544:    */       else
/* 545:    */       {
/* 546:583 */         ejb3JoinColumns = new Ejb3JoinColumn[nbrOfJoinColumns];
/* 547:584 */         if (pkColumnsAnn != null) {
/* 548:585 */           for (int colIndex = 0; colIndex < nbrOfJoinColumns; colIndex++) {
/* 549:586 */             ejb3JoinColumns[colIndex] = Ejb3JoinColumn.buildJoinColumn(pkColumnsAnn[colIndex], null, this.persistentClass.getIdentifier(), this.secondaryTables, propertyHolder, this.mappings);
/* 550:    */           }
/* 551:    */         } else {
/* 552:596 */           for (int colIndex = 0; colIndex < nbrOfJoinColumns; colIndex++) {
/* 553:597 */             ejb3JoinColumns[colIndex] = Ejb3JoinColumn.buildJoinColumn(null, joinColumnsAnn[colIndex], this.persistentClass.getIdentifier(), this.secondaryTables, propertyHolder, this.mappings);
/* 554:    */           }
/* 555:    */         }
/* 556:    */       }
/* 557:    */     }
/* 558:609 */     for (Ejb3JoinColumn joinColumn : ejb3JoinColumns) {
/* 559:610 */       joinColumn.forceNotNull();
/* 560:    */     }
/* 561:612 */     bindJoinToPersistentClass(join, ejb3JoinColumns, this.mappings);
/* 562:    */   }
/* 563:    */   
/* 564:    */   private void bindJoinToPersistentClass(Join join, Ejb3JoinColumn[] ejb3JoinColumns, Mappings mappings)
/* 565:    */   {
/* 566:616 */     SimpleValue key = new DependantValue(mappings, join.getTable(), this.persistentClass.getIdentifier());
/* 567:617 */     join.setKey(key);
/* 568:618 */     setFKNameIfDefined(join);
/* 569:619 */     key.setCascadeDeleteEnabled(false);
/* 570:620 */     TableBinder.bindFk(this.persistentClass, null, ejb3JoinColumns, key, false, mappings);
/* 571:621 */     join.createPrimaryKey();
/* 572:622 */     join.createForeignKey();
/* 573:623 */     this.persistentClass.addJoin(join);
/* 574:    */   }
/* 575:    */   
/* 576:    */   private void setFKNameIfDefined(Join join)
/* 577:    */   {
/* 578:627 */     org.hibernate.annotations.Table matchingTable = findMatchingComplimentTableAnnotation(join);
/* 579:628 */     if ((matchingTable != null) && (!BinderHelper.isEmptyAnnotationValue(matchingTable.foreignKey().name()))) {
/* 580:629 */       ((SimpleValue)join.getKey()).setForeignKeyName(matchingTable.foreignKey().name());
/* 581:    */     }
/* 582:    */   }
/* 583:    */   
/* 584:    */   private org.hibernate.annotations.Table findMatchingComplimentTableAnnotation(Join join)
/* 585:    */   {
/* 586:634 */     String tableName = join.getTable().getQuotedName();
/* 587:635 */     org.hibernate.annotations.Table table = (org.hibernate.annotations.Table)this.annotatedClass.getAnnotation(org.hibernate.annotations.Table.class);
/* 588:636 */     org.hibernate.annotations.Table matchingTable = null;
/* 589:637 */     if ((table != null) && (tableName.equals(table.appliesTo())))
/* 590:    */     {
/* 591:638 */       matchingTable = table;
/* 592:    */     }
/* 593:    */     else
/* 594:    */     {
/* 595:641 */       Tables tables = (Tables)this.annotatedClass.getAnnotation(Tables.class);
/* 596:642 */       if (tables != null) {
/* 597:643 */         for (org.hibernate.annotations.Table current : tables.value()) {
/* 598:644 */           if (tableName.equals(current.appliesTo()))
/* 599:    */           {
/* 600:645 */             matchingTable = current;
/* 601:646 */             break;
/* 602:    */           }
/* 603:    */         }
/* 604:    */       }
/* 605:    */     }
/* 606:651 */     return matchingTable;
/* 607:    */   }
/* 608:    */   
/* 609:    */   public void firstLevelSecondaryTablesBinding(SecondaryTable secTable, SecondaryTables secTables)
/* 610:    */   {
/* 611:657 */     if (secTables != null) {
/* 612:659 */       for (SecondaryTable tab : secTables.value()) {
/* 613:660 */         addJoin(tab, null, null, false);
/* 614:    */       }
/* 615:664 */     } else if (secTable != null) {
/* 616:664 */       addJoin(secTable, null, null, false);
/* 617:    */     }
/* 618:    */   }
/* 619:    */   
/* 620:    */   public Join addJoin(JoinTable joinTable, PropertyHolder holder, boolean noDelayInPkColumnCreation)
/* 621:    */   {
/* 622:670 */     return addJoin(null, joinTable, holder, noDelayInPkColumnCreation);
/* 623:    */   }
/* 624:    */   
/* 625:    */   private static class SecondaryTableNameSource
/* 626:    */     implements ObjectNameSource
/* 627:    */   {
/* 628:    */     private final String explicitName;
/* 629:    */     
/* 630:    */     private SecondaryTableNameSource(String explicitName)
/* 631:    */     {
/* 632:678 */       this.explicitName = explicitName;
/* 633:    */     }
/* 634:    */     
/* 635:    */     public String getExplicitName()
/* 636:    */     {
/* 637:682 */       return this.explicitName;
/* 638:    */     }
/* 639:    */     
/* 640:    */     public String getLogicalName()
/* 641:    */     {
/* 642:686 */       return this.explicitName;
/* 643:    */     }
/* 644:    */   }
/* 645:    */   
/* 646:    */   private static class SecondaryTableNamingStrategyHelper
/* 647:    */     implements ObjectNameNormalizer.NamingStrategyHelper
/* 648:    */   {
/* 649:    */     public String determineImplicitName(NamingStrategy strategy)
/* 650:    */     {
/* 651:693 */       return null;
/* 652:    */     }
/* 653:    */     
/* 654:    */     public String handleExplicitName(NamingStrategy strategy, String name)
/* 655:    */     {
/* 656:697 */       return strategy.tableName(name);
/* 657:    */     }
/* 658:    */   }
/* 659:    */   
/* 660:701 */   private static SecondaryTableNamingStrategyHelper SEC_TBL_NS_HELPER = new SecondaryTableNamingStrategyHelper(null);
/* 661:    */   
/* 662:    */   private Join addJoin(SecondaryTable secondaryTable, JoinTable joinTable, PropertyHolder propertyHolder, boolean noDelayInPkColumnCreation)
/* 663:    */   {
/* 664:709 */     Join join = new Join();
/* 665:710 */     join.setPersistentClass(this.persistentClass);
/* 666:    */     List<UniqueConstraintHolder> uniqueConstraintHolders;
/* 667:718 */     if (secondaryTable != null)
/* 668:    */     {
/* 669:719 */       String schema = secondaryTable.schema();
/* 670:720 */       String catalog = secondaryTable.catalog();
/* 671:721 */       SecondaryTableNameSource secondaryTableNameContext = new SecondaryTableNameSource(secondaryTable.name(), null);
/* 672:722 */       Object joinColumns = secondaryTable.pkJoinColumns();
/* 673:723 */       uniqueConstraintHolders = TableBinder.buildUniqueConstraintHolders(secondaryTable.uniqueConstraints());
/* 674:    */     }
/* 675:    */     else
/* 676:    */     {
/* 677:    */       List<UniqueConstraintHolder> uniqueConstraintHolders;
/* 678:725 */       if (joinTable != null)
/* 679:    */       {
/* 680:726 */         String schema = joinTable.schema();
/* 681:727 */         String catalog = joinTable.catalog();
/* 682:728 */         SecondaryTableNameSource secondaryTableNameContext = new SecondaryTableNameSource(joinTable.name(), null);
/* 683:729 */         Object joinColumns = joinTable.joinColumns();
/* 684:730 */         uniqueConstraintHolders = TableBinder.buildUniqueConstraintHolders(joinTable.uniqueConstraints());
/* 685:    */       }
/* 686:    */       else
/* 687:    */       {
/* 688:733 */         throw new AssertionFailure("Both JoinTable and SecondaryTable are null");
/* 689:    */       }
/* 690:    */     }
/* 691:    */     List<UniqueConstraintHolder> uniqueConstraintHolders;
/* 692:    */     Object joinColumns;
/* 693:    */     SecondaryTableNameSource secondaryTableNameContext;
/* 694:    */     String catalog;
/* 695:    */     String schema;
/* 696:736 */     org.hibernate.mapping.Table table = TableBinder.buildAndFillTable(schema, catalog, secondaryTableNameContext, SEC_TBL_NS_HELPER, false, uniqueConstraintHolders, null, null, this.mappings, null);
/* 697:    */     
/* 698:    */ 
/* 699:    */ 
/* 700:    */ 
/* 701:    */ 
/* 702:    */ 
/* 703:    */ 
/* 704:    */ 
/* 705:    */ 
/* 706:    */ 
/* 707:    */ 
/* 708:    */ 
/* 709:    */ 
/* 710:750 */     join.setTable(table);
/* 711:    */     
/* 712:    */ 
/* 713:    */ 
/* 714:754 */     LOG.debugf("Adding secondary table to entity %s -> %s", this.persistentClass.getEntityName(), join.getTable().getName());
/* 715:755 */     org.hibernate.annotations.Table matchingTable = findMatchingComplimentTableAnnotation(join);
/* 716:756 */     if (matchingTable != null)
/* 717:    */     {
/* 718:757 */       join.setSequentialSelect(FetchMode.JOIN != matchingTable.fetch());
/* 719:758 */       join.setInverse(matchingTable.inverse());
/* 720:759 */       join.setOptional(matchingTable.optional());
/* 721:760 */       if (!BinderHelper.isEmptyAnnotationValue(matchingTable.sqlInsert().sql())) {
/* 722:761 */         join.setCustomSQLInsert(matchingTable.sqlInsert().sql().trim(), matchingTable.sqlInsert().callable(), ExecuteUpdateResultCheckStyle.fromExternalName(matchingTable.sqlInsert().check().toString().toLowerCase()));
/* 723:    */       }
/* 724:768 */       if (!BinderHelper.isEmptyAnnotationValue(matchingTable.sqlUpdate().sql())) {
/* 725:769 */         join.setCustomSQLUpdate(matchingTable.sqlUpdate().sql().trim(), matchingTable.sqlUpdate().callable(), ExecuteUpdateResultCheckStyle.fromExternalName(matchingTable.sqlUpdate().check().toString().toLowerCase()));
/* 726:    */       }
/* 727:776 */       if (!BinderHelper.isEmptyAnnotationValue(matchingTable.sqlDelete().sql())) {
/* 728:777 */         join.setCustomSQLDelete(matchingTable.sqlDelete().sql().trim(), matchingTable.sqlDelete().callable(), ExecuteUpdateResultCheckStyle.fromExternalName(matchingTable.sqlDelete().check().toString().toLowerCase()));
/* 729:    */       }
/* 730:    */     }
/* 731:    */     else
/* 732:    */     {
/* 733:787 */       join.setSequentialSelect(false);
/* 734:788 */       join.setInverse(false);
/* 735:789 */       join.setOptional(true);
/* 736:    */     }
/* 737:792 */     if (noDelayInPkColumnCreation)
/* 738:    */     {
/* 739:793 */       createPrimaryColumnsToSecondaryTable(joinColumns, propertyHolder, join);
/* 740:    */     }
/* 741:    */     else
/* 742:    */     {
/* 743:796 */       this.secondaryTables.put(table.getQuotedName(), join);
/* 744:797 */       this.secondaryTableJoins.put(table.getQuotedName(), joinColumns);
/* 745:    */     }
/* 746:799 */     return join;
/* 747:    */   }
/* 748:    */   
/* 749:    */   public Map<String, Join> getSecondaryTables()
/* 750:    */   {
/* 751:803 */     return this.secondaryTables;
/* 752:    */   }
/* 753:    */   
/* 754:    */   public void setCache(Cache cacheAnn)
/* 755:    */   {
/* 756:807 */     if (cacheAnn != null)
/* 757:    */     {
/* 758:808 */       this.cacheRegion = (BinderHelper.isEmptyAnnotationValue(cacheAnn.region()) ? null : cacheAnn.region());
/* 759:    */       
/* 760:    */ 
/* 761:811 */       this.cacheConcurrentStrategy = getCacheConcurrencyStrategy(cacheAnn.usage());
/* 762:812 */       if ("all".equalsIgnoreCase(cacheAnn.include())) {
/* 763:813 */         this.cacheLazyProperty = true;
/* 764:815 */       } else if ("non-lazy".equalsIgnoreCase(cacheAnn.include())) {
/* 765:816 */         this.cacheLazyProperty = false;
/* 766:    */       } else {
/* 767:819 */         throw new AnnotationException("Unknown lazy property annotations: " + cacheAnn.include());
/* 768:    */       }
/* 769:    */     }
/* 770:    */     else
/* 771:    */     {
/* 772:823 */       this.cacheConcurrentStrategy = null;
/* 773:824 */       this.cacheRegion = null;
/* 774:825 */       this.cacheLazyProperty = true;
/* 775:    */     }
/* 776:    */   }
/* 777:    */   
/* 778:    */   public static String getCacheConcurrencyStrategy(CacheConcurrencyStrategy strategy)
/* 779:    */   {
/* 780:830 */     org.hibernate.cache.spi.access.AccessType accessType = strategy.toAccessType();
/* 781:831 */     return accessType == null ? null : accessType.getExternalName();
/* 782:    */   }
/* 783:    */   
/* 784:    */   public void addFilter(String name, String condition)
/* 785:    */   {
/* 786:835 */     this.filters.put(name, condition);
/* 787:    */   }
/* 788:    */   
/* 789:    */   public void setInheritanceState(InheritanceState inheritanceState)
/* 790:    */   {
/* 791:839 */     this.inheritanceState = inheritanceState;
/* 792:    */   }
/* 793:    */   
/* 794:    */   public boolean isIgnoreIdAnnotations()
/* 795:    */   {
/* 796:843 */     return this.ignoreIdAnnotations;
/* 797:    */   }
/* 798:    */   
/* 799:    */   public void setIgnoreIdAnnotations(boolean ignoreIdAnnotations)
/* 800:    */   {
/* 801:847 */     this.ignoreIdAnnotations = ignoreIdAnnotations;
/* 802:    */   }
/* 803:    */   
/* 804:    */   public void processComplementaryTableDefinitions(org.hibernate.annotations.Table table)
/* 805:    */   {
/* 806:852 */     if (table == null) {
/* 807:852 */       return;
/* 808:    */     }
/* 809:853 */     String appliedTable = table.appliesTo();
/* 810:854 */     Iterator tables = this.persistentClass.getTableClosureIterator();
/* 811:855 */     org.hibernate.mapping.Table hibTable = null;
/* 812:856 */     while (tables.hasNext())
/* 813:    */     {
/* 814:857 */       org.hibernate.mapping.Table pcTable = (org.hibernate.mapping.Table)tables.next();
/* 815:858 */       if (pcTable.getQuotedName().equals(appliedTable))
/* 816:    */       {
/* 817:860 */         hibTable = pcTable;
/* 818:861 */         break;
/* 819:    */       }
/* 820:863 */       hibTable = null;
/* 821:    */     }
/* 822:865 */     if (hibTable == null) {
/* 823:867 */       for (Join join : this.secondaryTables.values()) {
/* 824:868 */         if (join.getTable().getQuotedName().equals(appliedTable))
/* 825:    */         {
/* 826:869 */           hibTable = join.getTable();
/* 827:870 */           break;
/* 828:    */         }
/* 829:    */       }
/* 830:    */     }
/* 831:874 */     if (hibTable == null) {
/* 832:875 */       throw new AnnotationException("@org.hibernate.annotations.Table references an unknown table: " + appliedTable);
/* 833:    */     }
/* 834:879 */     if (!BinderHelper.isEmptyAnnotationValue(table.comment())) {
/* 835:879 */       hibTable.setComment(table.comment());
/* 836:    */     }
/* 837:880 */     TableBinder.addIndexes(hibTable, table.indexes(), this.mappings);
/* 838:    */   }
/* 839:    */   
/* 840:    */   public void processComplementaryTableDefinitions(Tables tables)
/* 841:    */   {
/* 842:884 */     if (tables == null) {
/* 843:884 */       return;
/* 844:    */     }
/* 845:885 */     for (org.hibernate.annotations.Table table : tables.value()) {
/* 846:886 */       processComplementaryTableDefinitions(table);
/* 847:    */     }
/* 848:    */   }
/* 849:    */   
/* 850:    */   public org.hibernate.cfg.AccessType getPropertyAccessType()
/* 851:    */   {
/* 852:891 */     return this.propertyAccessType;
/* 853:    */   }
/* 854:    */   
/* 855:    */   public void setPropertyAccessType(org.hibernate.cfg.AccessType propertyAccessor)
/* 856:    */   {
/* 857:895 */     this.propertyAccessType = getExplicitAccessType(this.annotatedClass);
/* 858:897 */     if (this.propertyAccessType == null) {
/* 859:898 */       this.propertyAccessType = propertyAccessor;
/* 860:    */     }
/* 861:    */   }
/* 862:    */   
/* 863:    */   public org.hibernate.cfg.AccessType getPropertyAccessor(XAnnotatedElement element)
/* 864:    */   {
/* 865:903 */     org.hibernate.cfg.AccessType accessType = getExplicitAccessType(element);
/* 866:904 */     if (accessType == null) {
/* 867:905 */       accessType = this.propertyAccessType;
/* 868:    */     }
/* 869:907 */     return accessType;
/* 870:    */   }
/* 871:    */   
/* 872:    */   public org.hibernate.cfg.AccessType getExplicitAccessType(XAnnotatedElement element)
/* 873:    */   {
/* 874:911 */     org.hibernate.cfg.AccessType accessType = null;
/* 875:    */     
/* 876:913 */     org.hibernate.cfg.AccessType hibernateAccessType = null;
/* 877:914 */     org.hibernate.cfg.AccessType jpaAccessType = null;
/* 878:    */     
/* 879:916 */     org.hibernate.annotations.AccessType accessTypeAnnotation = (org.hibernate.annotations.AccessType)element.getAnnotation(org.hibernate.annotations.AccessType.class);
/* 880:917 */     if (accessTypeAnnotation != null) {
/* 881:918 */       hibernateAccessType = org.hibernate.cfg.AccessType.getAccessStrategy(accessTypeAnnotation.value());
/* 882:    */     }
/* 883:921 */     Access access = (Access)element.getAnnotation(Access.class);
/* 884:922 */     if (access != null) {
/* 885:923 */       jpaAccessType = org.hibernate.cfg.AccessType.getAccessStrategy(access.value());
/* 886:    */     }
/* 887:926 */     if ((hibernateAccessType != null) && (jpaAccessType != null) && (hibernateAccessType != jpaAccessType)) {
/* 888:927 */       throw new MappingException("Found @Access and @AccessType with conflicting values on a property in class " + this.annotatedClass.toString());
/* 889:    */     }
/* 890:932 */     if (hibernateAccessType != null) {
/* 891:933 */       accessType = hibernateAccessType;
/* 892:935 */     } else if (jpaAccessType != null) {
/* 893:936 */       accessType = jpaAccessType;
/* 894:    */     }
/* 895:939 */     return accessType;
/* 896:    */   }
/* 897:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cfg.annotations.EntityBinder
 * JD-Core Version:    0.7.0.1
 */