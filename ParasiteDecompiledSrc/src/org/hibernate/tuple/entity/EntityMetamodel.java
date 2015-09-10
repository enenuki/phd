/*   1:    */ package org.hibernate.tuple.entity;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.HashSet;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import java.util.Set;
/*  11:    */ import org.hibernate.EntityMode;
/*  12:    */ import org.hibernate.HibernateException;
/*  13:    */ import org.hibernate.MappingException;
/*  14:    */ import org.hibernate.cfg.Settings;
/*  15:    */ import org.hibernate.engine.OptimisticLockStyle;
/*  16:    */ import org.hibernate.engine.spi.CascadeStyle;
/*  17:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  18:    */ import org.hibernate.engine.spi.ValueInclusion;
/*  19:    */ import org.hibernate.internal.CoreMessageLogger;
/*  20:    */ import org.hibernate.internal.util.ReflectHelper;
/*  21:    */ import org.hibernate.internal.util.Value;
/*  22:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*  23:    */ import org.hibernate.mapping.PersistentClass;
/*  24:    */ import org.hibernate.mapping.Property;
/*  25:    */ import org.hibernate.mapping.PropertyGeneration;
/*  26:    */ import org.hibernate.mapping.RootClass;
/*  27:    */ import org.hibernate.metamodel.binding.AttributeBinding;
/*  28:    */ import org.hibernate.metamodel.binding.BasicAttributeBinding;
/*  29:    */ import org.hibernate.metamodel.binding.EntityBinding;
/*  30:    */ import org.hibernate.metamodel.binding.EntityIdentifier;
/*  31:    */ import org.hibernate.metamodel.binding.HierarchyDetails;
/*  32:    */ import org.hibernate.metamodel.domain.Attribute;
/*  33:    */ import org.hibernate.metamodel.domain.Entity;
/*  34:    */ import org.hibernate.metamodel.domain.Hierarchical;
/*  35:    */ import org.hibernate.metamodel.domain.SingularAttribute;
/*  36:    */ import org.hibernate.service.instrumentation.spi.InstrumentationService;
/*  37:    */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  38:    */ import org.hibernate.tuple.IdentifierProperty;
/*  39:    */ import org.hibernate.tuple.PropertyFactory;
/*  40:    */ import org.hibernate.tuple.StandardProperty;
/*  41:    */ import org.hibernate.tuple.VersionProperty;
/*  42:    */ import org.hibernate.type.AssociationType;
/*  43:    */ import org.hibernate.type.CompositeType;
/*  44:    */ import org.hibernate.type.EntityType;
/*  45:    */ import org.hibernate.type.TypeFactory;
/*  46:    */ import org.hibernate.type.TypeResolver;
/*  47:    */ import org.jboss.logging.Logger;
/*  48:    */ 
/*  49:    */ public class EntityMetamodel
/*  50:    */   implements Serializable
/*  51:    */ {
/*  52: 74 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, EntityMetamodel.class.getName());
/*  53:    */   private static final int NO_VERSION_INDX = -66;
/*  54:    */   private final SessionFactoryImplementor sessionFactory;
/*  55:    */   private final String name;
/*  56:    */   private final String rootName;
/*  57:    */   private final EntityType entityType;
/*  58:    */   private final IdentifierProperty identifierProperty;
/*  59:    */   private final boolean versioned;
/*  60:    */   private final int propertySpan;
/*  61:    */   private final int versionPropertyIndex;
/*  62:    */   private final StandardProperty[] properties;
/*  63:    */   private final String[] propertyNames;
/*  64:    */   private final org.hibernate.type.Type[] propertyTypes;
/*  65:    */   private final boolean[] propertyLaziness;
/*  66:    */   private final boolean[] propertyUpdateability;
/*  67:    */   private final boolean[] nonlazyPropertyUpdateability;
/*  68:    */   private final boolean[] propertyCheckability;
/*  69:    */   private final boolean[] propertyInsertability;
/*  70:    */   private final ValueInclusion[] insertInclusions;
/*  71:    */   private final ValueInclusion[] updateInclusions;
/*  72:    */   private final boolean[] propertyNullability;
/*  73:    */   private final boolean[] propertyVersionability;
/*  74:    */   private final CascadeStyle[] cascadeStyles;
/*  75:    */   private final boolean hasInsertGeneratedValues;
/*  76:    */   private final boolean hasUpdateGeneratedValues;
/*  77:106 */   private final Map<String, Integer> propertyIndexes = new HashMap();
/*  78:    */   private final boolean hasCollections;
/*  79:    */   private final boolean hasMutableProperties;
/*  80:    */   private final boolean hasLazyProperties;
/*  81:    */   private final boolean hasNonIdentifierPropertyNamedId;
/*  82:    */   private final int[] naturalIdPropertyNumbers;
/*  83:    */   private final boolean hasImmutableNaturalId;
/*  84:    */   private boolean lazy;
/*  85:    */   private final boolean hasCascades;
/*  86:    */   private final boolean mutable;
/*  87:    */   private final boolean isAbstract;
/*  88:    */   private final boolean selectBeforeUpdate;
/*  89:    */   private final boolean dynamicUpdate;
/*  90:    */   private final boolean dynamicInsert;
/*  91:    */   private final OptimisticLockStyle optimisticLockStyle;
/*  92:    */   private final boolean polymorphic;
/*  93:    */   private final String superclass;
/*  94:    */   private final boolean explicitPolymorphism;
/*  95:    */   private final boolean inherited;
/*  96:    */   private final boolean hasSubclasses;
/*  97:129 */   private final Set subclassEntityNames = new HashSet();
/*  98:130 */   private final Map entityNameByInheritenceClassMap = new HashMap();
/*  99:    */   private final EntityMode entityMode;
/* 100:    */   private final EntityTuplizer entityTuplizer;
/* 101:    */   private boolean lazyAvailable;
/* 102:    */   
/* 103:    */   public EntityMetamodel(PersistentClass persistentClass, SessionFactoryImplementor sessionFactory)
/* 104:    */   {
/* 105:137 */     this.sessionFactory = sessionFactory;
/* 106:    */     
/* 107:139 */     this.name = persistentClass.getEntityName();
/* 108:140 */     this.rootName = persistentClass.getRootClass().getEntityName();
/* 109:141 */     this.entityType = sessionFactory.getTypeResolver().getTypeFactory().manyToOne(this.name);
/* 110:    */     
/* 111:143 */     this.identifierProperty = PropertyFactory.buildIdentifierProperty(persistentClass, sessionFactory.getIdentifierGenerator(this.rootName));
/* 112:    */     
/* 113:    */ 
/* 114:    */ 
/* 115:    */ 
/* 116:148 */     this.versioned = persistentClass.isVersioned();
/* 117:    */     
/* 118:150 */     InstrumentationService instrumentationService = (InstrumentationService)sessionFactory.getServiceRegistry().getService(InstrumentationService.class);
/* 119:151 */     this.lazyAvailable = ((persistentClass.hasPojoRepresentation()) && (instrumentationService.isInstrumented(persistentClass.getMappedClass())));
/* 120:    */     
/* 121:153 */     boolean hasLazy = false;
/* 122:    */     
/* 123:155 */     this.propertySpan = persistentClass.getPropertyClosureSpan();
/* 124:156 */     this.properties = new StandardProperty[this.propertySpan];
/* 125:157 */     List naturalIdNumbers = new ArrayList();
/* 126:    */     
/* 127:159 */     this.propertyNames = new String[this.propertySpan];
/* 128:160 */     this.propertyTypes = new org.hibernate.type.Type[this.propertySpan];
/* 129:161 */     this.propertyUpdateability = new boolean[this.propertySpan];
/* 130:162 */     this.propertyInsertability = new boolean[this.propertySpan];
/* 131:163 */     this.insertInclusions = new ValueInclusion[this.propertySpan];
/* 132:164 */     this.updateInclusions = new ValueInclusion[this.propertySpan];
/* 133:165 */     this.nonlazyPropertyUpdateability = new boolean[this.propertySpan];
/* 134:166 */     this.propertyCheckability = new boolean[this.propertySpan];
/* 135:167 */     this.propertyNullability = new boolean[this.propertySpan];
/* 136:168 */     this.propertyVersionability = new boolean[this.propertySpan];
/* 137:169 */     this.propertyLaziness = new boolean[this.propertySpan];
/* 138:170 */     this.cascadeStyles = new CascadeStyle[this.propertySpan];
/* 139:    */     
/* 140:    */ 
/* 141:    */ 
/* 142:174 */     Iterator iter = persistentClass.getPropertyClosureIterator();
/* 143:175 */     int i = 0;
/* 144:176 */     int tempVersionProperty = -66;
/* 145:177 */     boolean foundCascade = false;
/* 146:178 */     boolean foundCollection = false;
/* 147:179 */     boolean foundMutable = false;
/* 148:180 */     boolean foundNonIdentifierPropertyNamedId = false;
/* 149:181 */     boolean foundInsertGeneratedValue = false;
/* 150:182 */     boolean foundUpdateGeneratedValue = false;
/* 151:183 */     boolean foundUpdateableNaturalIdProperty = false;
/* 152:185 */     while (iter.hasNext())
/* 153:    */     {
/* 154:186 */       Property prop = (Property)iter.next();
/* 155:188 */       if (prop == persistentClass.getVersion())
/* 156:    */       {
/* 157:189 */         tempVersionProperty = i;
/* 158:190 */         this.properties[i] = PropertyFactory.buildVersionProperty(prop, this.lazyAvailable);
/* 159:    */       }
/* 160:    */       else
/* 161:    */       {
/* 162:193 */         this.properties[i] = PropertyFactory.buildStandardProperty(prop, this.lazyAvailable);
/* 163:    */       }
/* 164:196 */       if (prop.isNaturalIdentifier())
/* 165:    */       {
/* 166:197 */         naturalIdNumbers.add(Integer.valueOf(i));
/* 167:198 */         if (prop.isUpdateable()) {
/* 168:199 */           foundUpdateableNaturalIdProperty = true;
/* 169:    */         }
/* 170:    */       }
/* 171:203 */       if ("id".equals(prop.getName())) {
/* 172:204 */         foundNonIdentifierPropertyNamedId = true;
/* 173:    */       }
/* 174:208 */       boolean lazy = (prop.isLazy()) && (this.lazyAvailable);
/* 175:209 */       if (lazy) {
/* 176:209 */         hasLazy = true;
/* 177:    */       }
/* 178:210 */       this.propertyLaziness[i] = lazy;
/* 179:    */       
/* 180:212 */       this.propertyNames[i] = this.properties[i].getName();
/* 181:213 */       this.propertyTypes[i] = this.properties[i].getType();
/* 182:214 */       this.propertyNullability[i] = this.properties[i].isNullable();
/* 183:215 */       this.propertyUpdateability[i] = this.properties[i].isUpdateable();
/* 184:216 */       this.propertyInsertability[i] = this.properties[i].isInsertable();
/* 185:217 */       this.insertInclusions[i] = determineInsertValueGenerationType(prop, this.properties[i]);
/* 186:218 */       this.updateInclusions[i] = determineUpdateValueGenerationType(prop, this.properties[i]);
/* 187:219 */       this.propertyVersionability[i] = this.properties[i].isVersionable();
/* 188:220 */       this.nonlazyPropertyUpdateability[i] = ((this.properties[i].isUpdateable()) && (!lazy) ? 1 : false);
/* 189:221 */       this.propertyCheckability[i] = ((this.propertyUpdateability[i] != 0) || ((this.propertyTypes[i].isAssociationType()) && (((AssociationType)this.propertyTypes[i]).isAlwaysDirtyChecked())) ? 1 : false);
/* 190:    */       
/* 191:    */ 
/* 192:224 */       this.cascadeStyles[i] = this.properties[i].getCascadeStyle();
/* 193:227 */       if (this.properties[i].isLazy()) {
/* 194:228 */         hasLazy = true;
/* 195:    */       }
/* 196:231 */       if (this.properties[i].getCascadeStyle() != CascadeStyle.NONE) {
/* 197:232 */         foundCascade = true;
/* 198:    */       }
/* 199:235 */       if (indicatesCollection(this.properties[i].getType())) {
/* 200:236 */         foundCollection = true;
/* 201:    */       }
/* 202:239 */       if ((this.propertyTypes[i].isMutable()) && (this.propertyCheckability[i] != 0)) {
/* 203:240 */         foundMutable = true;
/* 204:    */       }
/* 205:243 */       if (this.insertInclusions[i] != ValueInclusion.NONE) {
/* 206:244 */         foundInsertGeneratedValue = true;
/* 207:    */       }
/* 208:247 */       if (this.updateInclusions[i] != ValueInclusion.NONE) {
/* 209:248 */         foundUpdateGeneratedValue = true;
/* 210:    */       }
/* 211:251 */       mapPropertyToIndex(prop, i);
/* 212:252 */       i++;
/* 213:    */     }
/* 214:255 */     if (naturalIdNumbers.size() == 0)
/* 215:    */     {
/* 216:256 */       this.naturalIdPropertyNumbers = null;
/* 217:257 */       this.hasImmutableNaturalId = false;
/* 218:    */     }
/* 219:    */     else
/* 220:    */     {
/* 221:260 */       this.naturalIdPropertyNumbers = ArrayHelper.toIntArray(naturalIdNumbers);
/* 222:261 */       this.hasImmutableNaturalId = (!foundUpdateableNaturalIdProperty);
/* 223:    */     }
/* 224:264 */     this.hasInsertGeneratedValues = foundInsertGeneratedValue;
/* 225:265 */     this.hasUpdateGeneratedValues = foundUpdateGeneratedValue;
/* 226:    */     
/* 227:267 */     this.hasCascades = foundCascade;
/* 228:268 */     this.hasNonIdentifierPropertyNamedId = foundNonIdentifierPropertyNamedId;
/* 229:269 */     this.versionPropertyIndex = tempVersionProperty;
/* 230:270 */     this.hasLazyProperties = hasLazy;
/* 231:271 */     if (this.hasLazyProperties) {
/* 232:271 */       LOG.lazyPropertyFetchingAvailable(this.name);
/* 233:    */     }
/* 234:273 */     this.lazy = ((persistentClass.isLazy()) && ((!persistentClass.hasPojoRepresentation()) || (!ReflectHelper.isFinalClass(persistentClass.getProxyInterface()))));
/* 235:    */     
/* 236:    */ 
/* 237:    */ 
/* 238:    */ 
/* 239:278 */     this.mutable = persistentClass.isMutable();
/* 240:279 */     if (persistentClass.isAbstract() == null)
/* 241:    */     {
/* 242:281 */       this.isAbstract = ((persistentClass.hasPojoRepresentation()) && (ReflectHelper.isAbstractClass(persistentClass.getMappedClass())));
/* 243:    */     }
/* 244:    */     else
/* 245:    */     {
/* 246:285 */       this.isAbstract = persistentClass.isAbstract().booleanValue();
/* 247:286 */       if ((!this.isAbstract) && (persistentClass.hasPojoRepresentation()) && (ReflectHelper.isAbstractClass(persistentClass.getMappedClass()))) {
/* 248:288 */         LOG.entityMappedAsNonAbstract(this.name);
/* 249:    */       }
/* 250:    */     }
/* 251:291 */     this.selectBeforeUpdate = persistentClass.hasSelectBeforeUpdate();
/* 252:292 */     this.dynamicUpdate = persistentClass.useDynamicUpdate();
/* 253:293 */     this.dynamicInsert = persistentClass.useDynamicInsert();
/* 254:    */     
/* 255:295 */     this.polymorphic = persistentClass.isPolymorphic();
/* 256:296 */     this.explicitPolymorphism = persistentClass.isExplicitPolymorphism();
/* 257:297 */     this.inherited = persistentClass.isInherited();
/* 258:298 */     this.superclass = (this.inherited ? persistentClass.getSuperclass().getEntityName() : null);
/* 259:    */     
/* 260:    */ 
/* 261:301 */     this.hasSubclasses = persistentClass.hasSubclasses();
/* 262:    */     
/* 263:303 */     this.optimisticLockStyle = interpretOptLockMode(persistentClass.getOptimisticLockMode());
/* 264:304 */     boolean isAllOrDirty = (this.optimisticLockStyle == OptimisticLockStyle.ALL) || (this.optimisticLockStyle == OptimisticLockStyle.DIRTY);
/* 265:307 */     if ((isAllOrDirty) && (!this.dynamicUpdate)) {
/* 266:308 */       throw new MappingException("optimistic-lock=all|dirty requires dynamic-update=\"true\": " + this.name);
/* 267:    */     }
/* 268:310 */     if ((this.versionPropertyIndex != -66) && (isAllOrDirty)) {
/* 269:311 */       throw new MappingException("version and optimistic-lock=all|dirty are not a valid combination : " + this.name);
/* 270:    */     }
/* 271:314 */     this.hasCollections = foundCollection;
/* 272:315 */     this.hasMutableProperties = foundMutable;
/* 273:    */     
/* 274:317 */     iter = persistentClass.getSubclassIterator();
/* 275:318 */     while (iter.hasNext()) {
/* 276:319 */       this.subclassEntityNames.add(((PersistentClass)iter.next()).getEntityName());
/* 277:    */     }
/* 278:321 */     this.subclassEntityNames.add(this.name);
/* 279:323 */     if (persistentClass.hasPojoRepresentation())
/* 280:    */     {
/* 281:324 */       this.entityNameByInheritenceClassMap.put(persistentClass.getMappedClass(), persistentClass.getEntityName());
/* 282:325 */       iter = persistentClass.getSubclassIterator();
/* 283:326 */       while (iter.hasNext())
/* 284:    */       {
/* 285:327 */         PersistentClass pc = (PersistentClass)iter.next();
/* 286:328 */         this.entityNameByInheritenceClassMap.put(pc.getMappedClass(), pc.getEntityName());
/* 287:    */       }
/* 288:    */     }
/* 289:332 */     this.entityMode = (persistentClass.hasPojoRepresentation() ? EntityMode.POJO : EntityMode.MAP);
/* 290:333 */     EntityTuplizerFactory entityTuplizerFactory = sessionFactory.getSettings().getEntityTuplizerFactory();
/* 291:334 */     String tuplizerClassName = persistentClass.getTuplizerImplClassName(this.entityMode);
/* 292:335 */     if (tuplizerClassName == null) {
/* 293:336 */       this.entityTuplizer = entityTuplizerFactory.constructDefaultTuplizer(this.entityMode, this, persistentClass);
/* 294:    */     } else {
/* 295:339 */       this.entityTuplizer = entityTuplizerFactory.constructTuplizer(tuplizerClassName, this, persistentClass);
/* 296:    */     }
/* 297:    */   }
/* 298:    */   
/* 299:    */   private OptimisticLockStyle interpretOptLockMode(int optimisticLockMode)
/* 300:    */   {
/* 301:344 */     switch (optimisticLockMode)
/* 302:    */     {
/* 303:    */     case -1: 
/* 304:346 */       return OptimisticLockStyle.NONE;
/* 305:    */     case 1: 
/* 306:349 */       return OptimisticLockStyle.DIRTY;
/* 307:    */     case 2: 
/* 308:352 */       return OptimisticLockStyle.ALL;
/* 309:    */     }
/* 310:355 */     return OptimisticLockStyle.VERSION;
/* 311:    */   }
/* 312:    */   
/* 313:    */   public EntityMetamodel(EntityBinding entityBinding, SessionFactoryImplementor sessionFactory)
/* 314:    */   {
/* 315:361 */     this.sessionFactory = sessionFactory;
/* 316:    */     
/* 317:363 */     this.name = entityBinding.getEntity().getName();
/* 318:    */     
/* 319:365 */     this.rootName = entityBinding.getHierarchyDetails().getRootEntityBinding().getEntity().getName();
/* 320:366 */     this.entityType = sessionFactory.getTypeResolver().getTypeFactory().manyToOne(this.name);
/* 321:    */     
/* 322:368 */     this.identifierProperty = PropertyFactory.buildIdentifierProperty(entityBinding, sessionFactory.getIdentifierGenerator(this.rootName));
/* 323:    */     
/* 324:    */ 
/* 325:    */ 
/* 326:    */ 
/* 327:373 */     this.versioned = entityBinding.isVersioned();
/* 328:    */     
/* 329:375 */     boolean hasPojoRepresentation = false;
/* 330:376 */     Class<?> mappedClass = null;
/* 331:377 */     Class<?> proxyInterfaceClass = null;
/* 332:378 */     this.lazyAvailable = false;
/* 333:379 */     if (entityBinding.getEntity().getClassReferenceUnresolved() != null)
/* 334:    */     {
/* 335:380 */       hasPojoRepresentation = true;
/* 336:381 */       mappedClass = entityBinding.getEntity().getClassReference();
/* 337:382 */       proxyInterfaceClass = (Class)entityBinding.getProxyInterfaceType().getValue();
/* 338:383 */       InstrumentationService instrumentationService = (InstrumentationService)sessionFactory.getServiceRegistry().getService(InstrumentationService.class);
/* 339:    */       
/* 340:385 */       this.lazyAvailable = instrumentationService.isInstrumented(mappedClass);
/* 341:    */     }
/* 342:388 */     boolean hasLazy = false;
/* 343:    */     
/* 344:    */ 
/* 345:391 */     BasicAttributeBinding rootEntityIdentifier = entityBinding.getHierarchyDetails().getEntityIdentifier().getValueBinding();
/* 346:    */     
/* 347:    */ 
/* 348:394 */     this.propertySpan = (rootEntityIdentifier == null ? entityBinding.getAttributeBindingClosureSpan() : entityBinding.getAttributeBindingClosureSpan() - 1);
/* 349:    */     
/* 350:    */ 
/* 351:    */ 
/* 352:398 */     this.properties = new StandardProperty[this.propertySpan];
/* 353:399 */     List naturalIdNumbers = new ArrayList();
/* 354:    */     
/* 355:401 */     this.propertyNames = new String[this.propertySpan];
/* 356:402 */     this.propertyTypes = new org.hibernate.type.Type[this.propertySpan];
/* 357:403 */     this.propertyUpdateability = new boolean[this.propertySpan];
/* 358:404 */     this.propertyInsertability = new boolean[this.propertySpan];
/* 359:405 */     this.insertInclusions = new ValueInclusion[this.propertySpan];
/* 360:406 */     this.updateInclusions = new ValueInclusion[this.propertySpan];
/* 361:407 */     this.nonlazyPropertyUpdateability = new boolean[this.propertySpan];
/* 362:408 */     this.propertyCheckability = new boolean[this.propertySpan];
/* 363:409 */     this.propertyNullability = new boolean[this.propertySpan];
/* 364:410 */     this.propertyVersionability = new boolean[this.propertySpan];
/* 365:411 */     this.propertyLaziness = new boolean[this.propertySpan];
/* 366:412 */     this.cascadeStyles = new CascadeStyle[this.propertySpan];
/* 367:    */     
/* 368:    */ 
/* 369:    */ 
/* 370:416 */     int i = 0;
/* 371:417 */     int tempVersionProperty = -66;
/* 372:418 */     boolean foundCascade = false;
/* 373:419 */     boolean foundCollection = false;
/* 374:420 */     boolean foundMutable = false;
/* 375:421 */     boolean foundNonIdentifierPropertyNamedId = false;
/* 376:422 */     boolean foundInsertGeneratedValue = false;
/* 377:423 */     boolean foundUpdateGeneratedValue = false;
/* 378:424 */     boolean foundUpdateableNaturalIdProperty = false;
/* 379:426 */     for (AttributeBinding attributeBinding : entityBinding.getAttributeBindingClosure()) {
/* 380:427 */       if (attributeBinding != rootEntityIdentifier)
/* 381:    */       {
/* 382:432 */         if (attributeBinding == entityBinding.getHierarchyDetails().getVersioningAttributeBinding())
/* 383:    */         {
/* 384:433 */           tempVersionProperty = i;
/* 385:434 */           this.properties[i] = PropertyFactory.buildVersionProperty(entityBinding.getHierarchyDetails().getVersioningAttributeBinding(), this.lazyAvailable);
/* 386:    */         }
/* 387:    */         else
/* 388:    */         {
/* 389:439 */           this.properties[i] = PropertyFactory.buildStandardProperty(attributeBinding, this.lazyAvailable);
/* 390:    */         }
/* 391:450 */         if ("id".equals(attributeBinding.getAttribute().getName())) {
/* 392:451 */           foundNonIdentifierPropertyNamedId = true;
/* 393:    */         }
/* 394:455 */         boolean lazy = (attributeBinding.isLazy()) && (this.lazyAvailable);
/* 395:456 */         if (lazy) {
/* 396:456 */           hasLazy = true;
/* 397:    */         }
/* 398:457 */         this.propertyLaziness[i] = lazy;
/* 399:    */         
/* 400:459 */         this.propertyNames[i] = this.properties[i].getName();
/* 401:460 */         this.propertyTypes[i] = this.properties[i].getType();
/* 402:461 */         this.propertyNullability[i] = this.properties[i].isNullable();
/* 403:462 */         this.propertyUpdateability[i] = this.properties[i].isUpdateable();
/* 404:463 */         this.propertyInsertability[i] = this.properties[i].isInsertable();
/* 405:464 */         this.insertInclusions[i] = determineInsertValueGenerationType(attributeBinding, this.properties[i]);
/* 406:465 */         this.updateInclusions[i] = determineUpdateValueGenerationType(attributeBinding, this.properties[i]);
/* 407:466 */         this.propertyVersionability[i] = this.properties[i].isVersionable();
/* 408:467 */         this.nonlazyPropertyUpdateability[i] = ((this.properties[i].isUpdateable()) && (!lazy) ? 1 : false);
/* 409:468 */         this.propertyCheckability[i] = ((this.propertyUpdateability[i] != 0) || ((this.propertyTypes[i].isAssociationType()) && (((AssociationType)this.propertyTypes[i]).isAlwaysDirtyChecked())) ? 1 : false);
/* 410:    */         
/* 411:    */ 
/* 412:471 */         this.cascadeStyles[i] = this.properties[i].getCascadeStyle();
/* 413:474 */         if (this.properties[i].isLazy()) {
/* 414:475 */           hasLazy = true;
/* 415:    */         }
/* 416:478 */         if (this.properties[i].getCascadeStyle() != CascadeStyle.NONE) {
/* 417:479 */           foundCascade = true;
/* 418:    */         }
/* 419:482 */         if (indicatesCollection(this.properties[i].getType())) {
/* 420:483 */           foundCollection = true;
/* 421:    */         }
/* 422:486 */         if ((this.propertyTypes[i].isMutable()) && (this.propertyCheckability[i] != 0)) {
/* 423:487 */           foundMutable = true;
/* 424:    */         }
/* 425:490 */         if (this.insertInclusions[i] != ValueInclusion.NONE) {
/* 426:491 */           foundInsertGeneratedValue = true;
/* 427:    */         }
/* 428:494 */         if (this.updateInclusions[i] != ValueInclusion.NONE) {
/* 429:495 */           foundUpdateGeneratedValue = true;
/* 430:    */         }
/* 431:498 */         mapPropertyToIndex(attributeBinding.getAttribute(), i);
/* 432:499 */         i++;
/* 433:    */       }
/* 434:    */     }
/* 435:502 */     if (naturalIdNumbers.size() == 0)
/* 436:    */     {
/* 437:503 */       this.naturalIdPropertyNumbers = null;
/* 438:504 */       this.hasImmutableNaturalId = false;
/* 439:    */     }
/* 440:    */     else
/* 441:    */     {
/* 442:507 */       this.naturalIdPropertyNumbers = ArrayHelper.toIntArray(naturalIdNumbers);
/* 443:508 */       this.hasImmutableNaturalId = (!foundUpdateableNaturalIdProperty);
/* 444:    */     }
/* 445:511 */     this.hasInsertGeneratedValues = foundInsertGeneratedValue;
/* 446:512 */     this.hasUpdateGeneratedValues = foundUpdateGeneratedValue;
/* 447:    */     
/* 448:514 */     this.hasCascades = foundCascade;
/* 449:515 */     this.hasNonIdentifierPropertyNamedId = foundNonIdentifierPropertyNamedId;
/* 450:516 */     this.versionPropertyIndex = tempVersionProperty;
/* 451:517 */     this.hasLazyProperties = hasLazy;
/* 452:518 */     if (this.hasLazyProperties) {
/* 453:519 */       LOG.lazyPropertyFetchingAvailable(this.name);
/* 454:    */     }
/* 455:522 */     this.lazy = ((entityBinding.isLazy()) && ((!hasPojoRepresentation) || (!ReflectHelper.isFinalClass(proxyInterfaceClass))));
/* 456:    */     
/* 457:    */ 
/* 458:    */ 
/* 459:    */ 
/* 460:527 */     this.mutable = entityBinding.isMutable();
/* 461:528 */     if (entityBinding.isAbstract() == null)
/* 462:    */     {
/* 463:530 */       this.isAbstract = ((hasPojoRepresentation) && (ReflectHelper.isAbstractClass(mappedClass)));
/* 464:    */     }
/* 465:    */     else
/* 466:    */     {
/* 467:534 */       this.isAbstract = entityBinding.isAbstract().booleanValue();
/* 468:535 */       if ((!this.isAbstract) && (hasPojoRepresentation) && (ReflectHelper.isAbstractClass(mappedClass))) {
/* 469:537 */         LOG.entityMappedAsNonAbstract(this.name);
/* 470:    */       }
/* 471:    */     }
/* 472:540 */     this.selectBeforeUpdate = entityBinding.isSelectBeforeUpdate();
/* 473:541 */     this.dynamicUpdate = entityBinding.isDynamicUpdate();
/* 474:542 */     this.dynamicInsert = entityBinding.isDynamicInsert();
/* 475:    */     
/* 476:544 */     this.hasSubclasses = entityBinding.hasSubEntityBindings();
/* 477:545 */     this.polymorphic = entityBinding.isPolymorphic();
/* 478:    */     
/* 479:547 */     this.explicitPolymorphism = entityBinding.getHierarchyDetails().isExplicitPolymorphism();
/* 480:548 */     this.inherited = (!entityBinding.isRoot());
/* 481:549 */     this.superclass = (this.inherited ? entityBinding.getEntity().getSuperType().getName() : null);
/* 482:    */     
/* 483:    */ 
/* 484:    */ 
/* 485:553 */     this.optimisticLockStyle = entityBinding.getHierarchyDetails().getOptimisticLockStyle();
/* 486:554 */     boolean isAllOrDirty = (this.optimisticLockStyle == OptimisticLockStyle.ALL) || (this.optimisticLockStyle == OptimisticLockStyle.DIRTY);
/* 487:557 */     if ((isAllOrDirty) && (!this.dynamicUpdate)) {
/* 488:558 */       throw new MappingException("optimistic-lock=all|dirty requires dynamic-update=\"true\": " + this.name);
/* 489:    */     }
/* 490:560 */     if ((this.versionPropertyIndex != -66) && (isAllOrDirty)) {
/* 491:561 */       throw new MappingException("version and optimistic-lock=all|dirty are not a valid combination : " + this.name);
/* 492:    */     }
/* 493:564 */     this.hasCollections = foundCollection;
/* 494:565 */     this.hasMutableProperties = foundMutable;
/* 495:567 */     for (EntityBinding subEntityBinding : entityBinding.getPostOrderSubEntityBindingClosure())
/* 496:    */     {
/* 497:568 */       this.subclassEntityNames.add(subEntityBinding.getEntity().getName());
/* 498:569 */       if (subEntityBinding.getEntity().getClassReference() != null) {
/* 499:570 */         this.entityNameByInheritenceClassMap.put(subEntityBinding.getEntity().getClassReference(), subEntityBinding.getEntity().getName());
/* 500:    */       }
/* 501:    */     }
/* 502:575 */     this.subclassEntityNames.add(this.name);
/* 503:576 */     if (mappedClass != null) {
/* 504:577 */       this.entityNameByInheritenceClassMap.put(mappedClass, this.name);
/* 505:    */     }
/* 506:580 */     this.entityMode = (hasPojoRepresentation ? EntityMode.POJO : EntityMode.MAP);
/* 507:581 */     EntityTuplizerFactory entityTuplizerFactory = sessionFactory.getSettings().getEntityTuplizerFactory();
/* 508:582 */     Class<? extends EntityTuplizer> tuplizerClass = entityBinding.getCustomEntityTuplizerClass();
/* 509:584 */     if (tuplizerClass == null) {
/* 510:585 */       this.entityTuplizer = entityTuplizerFactory.constructDefaultTuplizer(this.entityMode, this, entityBinding);
/* 511:    */     } else {
/* 512:588 */       this.entityTuplizer = entityTuplizerFactory.constructTuplizer(tuplizerClass, this, entityBinding);
/* 513:    */     }
/* 514:    */   }
/* 515:    */   
/* 516:    */   private ValueInclusion determineInsertValueGenerationType(Property mappingProperty, StandardProperty runtimeProperty)
/* 517:    */   {
/* 518:593 */     if (runtimeProperty.isInsertGenerated()) {
/* 519:594 */       return ValueInclusion.FULL;
/* 520:    */     }
/* 521:596 */     if (((mappingProperty.getValue() instanceof org.hibernate.mapping.Component)) && 
/* 522:597 */       (hasPartialInsertComponentGeneration((org.hibernate.mapping.Component)mappingProperty.getValue()))) {
/* 523:598 */       return ValueInclusion.PARTIAL;
/* 524:    */     }
/* 525:601 */     return ValueInclusion.NONE;
/* 526:    */   }
/* 527:    */   
/* 528:    */   private ValueInclusion determineInsertValueGenerationType(AttributeBinding mappingProperty, StandardProperty runtimeProperty)
/* 529:    */   {
/* 530:605 */     if (runtimeProperty.isInsertGenerated()) {
/* 531:606 */       return ValueInclusion.FULL;
/* 532:    */     }
/* 533:614 */     return ValueInclusion.NONE;
/* 534:    */   }
/* 535:    */   
/* 536:    */   private boolean hasPartialInsertComponentGeneration(org.hibernate.mapping.Component component)
/* 537:    */   {
/* 538:618 */     Iterator subProperties = component.getPropertyIterator();
/* 539:619 */     while (subProperties.hasNext())
/* 540:    */     {
/* 541:620 */       Property prop = (Property)subProperties.next();
/* 542:621 */       if ((prop.getGeneration() == PropertyGeneration.ALWAYS) || (prop.getGeneration() == PropertyGeneration.INSERT)) {
/* 543:622 */         return true;
/* 544:    */       }
/* 545:624 */       if (((prop.getValue() instanceof org.hibernate.mapping.Component)) && 
/* 546:625 */         (hasPartialInsertComponentGeneration((org.hibernate.mapping.Component)prop.getValue()))) {
/* 547:626 */         return true;
/* 548:    */       }
/* 549:    */     }
/* 550:630 */     return false;
/* 551:    */   }
/* 552:    */   
/* 553:    */   private ValueInclusion determineUpdateValueGenerationType(Property mappingProperty, StandardProperty runtimeProperty)
/* 554:    */   {
/* 555:634 */     if (runtimeProperty.isUpdateGenerated()) {
/* 556:635 */       return ValueInclusion.FULL;
/* 557:    */     }
/* 558:637 */     if (((mappingProperty.getValue() instanceof org.hibernate.mapping.Component)) && 
/* 559:638 */       (hasPartialUpdateComponentGeneration((org.hibernate.mapping.Component)mappingProperty.getValue()))) {
/* 560:639 */       return ValueInclusion.PARTIAL;
/* 561:    */     }
/* 562:642 */     return ValueInclusion.NONE;
/* 563:    */   }
/* 564:    */   
/* 565:    */   private ValueInclusion determineUpdateValueGenerationType(AttributeBinding mappingProperty, StandardProperty runtimeProperty)
/* 566:    */   {
/* 567:646 */     if (runtimeProperty.isUpdateGenerated()) {
/* 568:647 */       return ValueInclusion.FULL;
/* 569:    */     }
/* 570:655 */     return ValueInclusion.NONE;
/* 571:    */   }
/* 572:    */   
/* 573:    */   private boolean hasPartialUpdateComponentGeneration(org.hibernate.mapping.Component component)
/* 574:    */   {
/* 575:659 */     Iterator subProperties = component.getPropertyIterator();
/* 576:660 */     while (subProperties.hasNext())
/* 577:    */     {
/* 578:661 */       Property prop = (Property)subProperties.next();
/* 579:662 */       if (prop.getGeneration() == PropertyGeneration.ALWAYS) {
/* 580:663 */         return true;
/* 581:    */       }
/* 582:665 */       if (((prop.getValue() instanceof org.hibernate.mapping.Component)) && 
/* 583:666 */         (hasPartialUpdateComponentGeneration((org.hibernate.mapping.Component)prop.getValue()))) {
/* 584:667 */         return true;
/* 585:    */       }
/* 586:    */     }
/* 587:671 */     return false;
/* 588:    */   }
/* 589:    */   
/* 590:    */   private void mapPropertyToIndex(Property prop, int i)
/* 591:    */   {
/* 592:675 */     this.propertyIndexes.put(prop.getName(), Integer.valueOf(i));
/* 593:676 */     if ((prop.getValue() instanceof org.hibernate.mapping.Component))
/* 594:    */     {
/* 595:677 */       Iterator iter = ((org.hibernate.mapping.Component)prop.getValue()).getPropertyIterator();
/* 596:678 */       while (iter.hasNext())
/* 597:    */       {
/* 598:679 */         Property subprop = (Property)iter.next();
/* 599:680 */         this.propertyIndexes.put(prop.getName() + '.' + subprop.getName(), Integer.valueOf(i));
/* 600:    */       }
/* 601:    */     }
/* 602:    */   }
/* 603:    */   
/* 604:    */   private void mapPropertyToIndex(Attribute attribute, int i)
/* 605:    */   {
/* 606:689 */     this.propertyIndexes.put(attribute.getName(), Integer.valueOf(i));
/* 607:690 */     if ((attribute.isSingular()) && (((SingularAttribute)attribute).getSingularAttributeType().isComponent()))
/* 608:    */     {
/* 609:692 */       org.hibernate.metamodel.domain.Component component = (org.hibernate.metamodel.domain.Component)((SingularAttribute)attribute).getSingularAttributeType();
/* 610:694 */       for (Attribute subAttribute : component.attributes()) {
/* 611:695 */         this.propertyIndexes.put(attribute.getName() + '.' + subAttribute.getName(), Integer.valueOf(i));
/* 612:    */       }
/* 613:    */     }
/* 614:    */   }
/* 615:    */   
/* 616:    */   public EntityTuplizer getTuplizer()
/* 617:    */   {
/* 618:704 */     return this.entityTuplizer;
/* 619:    */   }
/* 620:    */   
/* 621:    */   public int[] getNaturalIdentifierProperties()
/* 622:    */   {
/* 623:708 */     return this.naturalIdPropertyNumbers;
/* 624:    */   }
/* 625:    */   
/* 626:    */   public boolean hasNaturalIdentifier()
/* 627:    */   {
/* 628:712 */     return this.naturalIdPropertyNumbers != null;
/* 629:    */   }
/* 630:    */   
/* 631:    */   public boolean hasImmutableNaturalId()
/* 632:    */   {
/* 633:716 */     return this.hasImmutableNaturalId;
/* 634:    */   }
/* 635:    */   
/* 636:    */   public Set getSubclassEntityNames()
/* 637:    */   {
/* 638:720 */     return this.subclassEntityNames;
/* 639:    */   }
/* 640:    */   
/* 641:    */   private boolean indicatesCollection(org.hibernate.type.Type type)
/* 642:    */   {
/* 643:724 */     if (type.isCollectionType()) {
/* 644:725 */       return true;
/* 645:    */     }
/* 646:727 */     if (type.isComponentType())
/* 647:    */     {
/* 648:728 */       org.hibernate.type.Type[] subtypes = ((CompositeType)type).getSubtypes();
/* 649:729 */       for (int i = 0; i < subtypes.length; i++) {
/* 650:730 */         if (indicatesCollection(subtypes[i])) {
/* 651:731 */           return true;
/* 652:    */         }
/* 653:    */       }
/* 654:    */     }
/* 655:735 */     return false;
/* 656:    */   }
/* 657:    */   
/* 658:    */   public SessionFactoryImplementor getSessionFactory()
/* 659:    */   {
/* 660:739 */     return this.sessionFactory;
/* 661:    */   }
/* 662:    */   
/* 663:    */   public String getName()
/* 664:    */   {
/* 665:743 */     return this.name;
/* 666:    */   }
/* 667:    */   
/* 668:    */   public String getRootName()
/* 669:    */   {
/* 670:747 */     return this.rootName;
/* 671:    */   }
/* 672:    */   
/* 673:    */   public EntityType getEntityType()
/* 674:    */   {
/* 675:751 */     return this.entityType;
/* 676:    */   }
/* 677:    */   
/* 678:    */   public IdentifierProperty getIdentifierProperty()
/* 679:    */   {
/* 680:755 */     return this.identifierProperty;
/* 681:    */   }
/* 682:    */   
/* 683:    */   public int getPropertySpan()
/* 684:    */   {
/* 685:759 */     return this.propertySpan;
/* 686:    */   }
/* 687:    */   
/* 688:    */   public int getVersionPropertyIndex()
/* 689:    */   {
/* 690:763 */     return this.versionPropertyIndex;
/* 691:    */   }
/* 692:    */   
/* 693:    */   public VersionProperty getVersionProperty()
/* 694:    */   {
/* 695:767 */     if (-66 == this.versionPropertyIndex) {
/* 696:768 */       return null;
/* 697:    */     }
/* 698:771 */     return (VersionProperty)this.properties[this.versionPropertyIndex];
/* 699:    */   }
/* 700:    */   
/* 701:    */   public StandardProperty[] getProperties()
/* 702:    */   {
/* 703:776 */     return this.properties;
/* 704:    */   }
/* 705:    */   
/* 706:    */   public int getPropertyIndex(String propertyName)
/* 707:    */   {
/* 708:780 */     Integer index = getPropertyIndexOrNull(propertyName);
/* 709:781 */     if (index == null) {
/* 710:782 */       throw new HibernateException("Unable to resolve property: " + propertyName);
/* 711:    */     }
/* 712:784 */     return index.intValue();
/* 713:    */   }
/* 714:    */   
/* 715:    */   public Integer getPropertyIndexOrNull(String propertyName)
/* 716:    */   {
/* 717:788 */     return (Integer)this.propertyIndexes.get(propertyName);
/* 718:    */   }
/* 719:    */   
/* 720:    */   public boolean hasCollections()
/* 721:    */   {
/* 722:792 */     return this.hasCollections;
/* 723:    */   }
/* 724:    */   
/* 725:    */   public boolean hasMutableProperties()
/* 726:    */   {
/* 727:796 */     return this.hasMutableProperties;
/* 728:    */   }
/* 729:    */   
/* 730:    */   public boolean hasNonIdentifierPropertyNamedId()
/* 731:    */   {
/* 732:800 */     return this.hasNonIdentifierPropertyNamedId;
/* 733:    */   }
/* 734:    */   
/* 735:    */   public boolean hasLazyProperties()
/* 736:    */   {
/* 737:804 */     return this.hasLazyProperties;
/* 738:    */   }
/* 739:    */   
/* 740:    */   public boolean hasCascades()
/* 741:    */   {
/* 742:808 */     return this.hasCascades;
/* 743:    */   }
/* 744:    */   
/* 745:    */   public boolean isMutable()
/* 746:    */   {
/* 747:812 */     return this.mutable;
/* 748:    */   }
/* 749:    */   
/* 750:    */   public boolean isSelectBeforeUpdate()
/* 751:    */   {
/* 752:816 */     return this.selectBeforeUpdate;
/* 753:    */   }
/* 754:    */   
/* 755:    */   public boolean isDynamicUpdate()
/* 756:    */   {
/* 757:820 */     return this.dynamicUpdate;
/* 758:    */   }
/* 759:    */   
/* 760:    */   public boolean isDynamicInsert()
/* 761:    */   {
/* 762:824 */     return this.dynamicInsert;
/* 763:    */   }
/* 764:    */   
/* 765:    */   public OptimisticLockStyle getOptimisticLockStyle()
/* 766:    */   {
/* 767:828 */     return this.optimisticLockStyle;
/* 768:    */   }
/* 769:    */   
/* 770:    */   public boolean isPolymorphic()
/* 771:    */   {
/* 772:832 */     return this.polymorphic;
/* 773:    */   }
/* 774:    */   
/* 775:    */   public String getSuperclass()
/* 776:    */   {
/* 777:836 */     return this.superclass;
/* 778:    */   }
/* 779:    */   
/* 780:    */   public boolean isExplicitPolymorphism()
/* 781:    */   {
/* 782:840 */     return this.explicitPolymorphism;
/* 783:    */   }
/* 784:    */   
/* 785:    */   public boolean isInherited()
/* 786:    */   {
/* 787:844 */     return this.inherited;
/* 788:    */   }
/* 789:    */   
/* 790:    */   public boolean hasSubclasses()
/* 791:    */   {
/* 792:848 */     return this.hasSubclasses;
/* 793:    */   }
/* 794:    */   
/* 795:    */   public boolean isLazy()
/* 796:    */   {
/* 797:852 */     return this.lazy;
/* 798:    */   }
/* 799:    */   
/* 800:    */   public void setLazy(boolean lazy)
/* 801:    */   {
/* 802:856 */     this.lazy = lazy;
/* 803:    */   }
/* 804:    */   
/* 805:    */   public boolean isVersioned()
/* 806:    */   {
/* 807:860 */     return this.versioned;
/* 808:    */   }
/* 809:    */   
/* 810:    */   public boolean isAbstract()
/* 811:    */   {
/* 812:864 */     return this.isAbstract;
/* 813:    */   }
/* 814:    */   
/* 815:    */   public String findEntityNameByEntityClass(Class inheritenceClass)
/* 816:    */   {
/* 817:874 */     return (String)this.entityNameByInheritenceClassMap.get(inheritenceClass);
/* 818:    */   }
/* 819:    */   
/* 820:    */   public String toString()
/* 821:    */   {
/* 822:879 */     return "EntityMetamodel(" + this.name + ':' + ArrayHelper.toString(this.properties) + ')';
/* 823:    */   }
/* 824:    */   
/* 825:    */   public String[] getPropertyNames()
/* 826:    */   {
/* 827:884 */     return this.propertyNames;
/* 828:    */   }
/* 829:    */   
/* 830:    */   public org.hibernate.type.Type[] getPropertyTypes()
/* 831:    */   {
/* 832:888 */     return this.propertyTypes;
/* 833:    */   }
/* 834:    */   
/* 835:    */   public boolean[] getPropertyLaziness()
/* 836:    */   {
/* 837:892 */     return this.propertyLaziness;
/* 838:    */   }
/* 839:    */   
/* 840:    */   public boolean[] getPropertyUpdateability()
/* 841:    */   {
/* 842:896 */     return this.propertyUpdateability;
/* 843:    */   }
/* 844:    */   
/* 845:    */   public boolean[] getPropertyCheckability()
/* 846:    */   {
/* 847:900 */     return this.propertyCheckability;
/* 848:    */   }
/* 849:    */   
/* 850:    */   public boolean[] getNonlazyPropertyUpdateability()
/* 851:    */   {
/* 852:904 */     return this.nonlazyPropertyUpdateability;
/* 853:    */   }
/* 854:    */   
/* 855:    */   public boolean[] getPropertyInsertability()
/* 856:    */   {
/* 857:908 */     return this.propertyInsertability;
/* 858:    */   }
/* 859:    */   
/* 860:    */   public ValueInclusion[] getPropertyInsertGenerationInclusions()
/* 861:    */   {
/* 862:912 */     return this.insertInclusions;
/* 863:    */   }
/* 864:    */   
/* 865:    */   public ValueInclusion[] getPropertyUpdateGenerationInclusions()
/* 866:    */   {
/* 867:916 */     return this.updateInclusions;
/* 868:    */   }
/* 869:    */   
/* 870:    */   public boolean[] getPropertyNullability()
/* 871:    */   {
/* 872:920 */     return this.propertyNullability;
/* 873:    */   }
/* 874:    */   
/* 875:    */   public boolean[] getPropertyVersionability()
/* 876:    */   {
/* 877:924 */     return this.propertyVersionability;
/* 878:    */   }
/* 879:    */   
/* 880:    */   public CascadeStyle[] getCascadeStyles()
/* 881:    */   {
/* 882:928 */     return this.cascadeStyles;
/* 883:    */   }
/* 884:    */   
/* 885:    */   public boolean hasInsertGeneratedValues()
/* 886:    */   {
/* 887:932 */     return this.hasInsertGeneratedValues;
/* 888:    */   }
/* 889:    */   
/* 890:    */   public boolean hasUpdateGeneratedValues()
/* 891:    */   {
/* 892:936 */     return this.hasUpdateGeneratedValues;
/* 893:    */   }
/* 894:    */   
/* 895:    */   public EntityMode getEntityMode()
/* 896:    */   {
/* 897:940 */     return this.entityMode;
/* 898:    */   }
/* 899:    */   
/* 900:    */   public boolean isInstrumented()
/* 901:    */   {
/* 902:947 */     return this.lazyAvailable;
/* 903:    */   }
/* 904:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tuple.entity.EntityMetamodel
 * JD-Core Version:    0.7.0.1
 */