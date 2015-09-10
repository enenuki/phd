/*   1:    */ package org.hibernate.metamodel.source.internal;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Map.Entry;
/*   9:    */ import org.hibernate.AssertionFailure;
/*  10:    */ import org.hibernate.DuplicateMappingException;
/*  11:    */ import org.hibernate.DuplicateMappingException.Type;
/*  12:    */ import org.hibernate.MappingException;
/*  13:    */ import org.hibernate.SessionFactory;
/*  14:    */ import org.hibernate.cache.spi.RegionFactory;
/*  15:    */ import org.hibernate.cache.spi.access.AccessType;
/*  16:    */ import org.hibernate.cfg.NamingStrategy;
/*  17:    */ import org.hibernate.engine.ResultSetMappingDefinition;
/*  18:    */ import org.hibernate.engine.spi.FilterDefinition;
/*  19:    */ import org.hibernate.engine.spi.NamedQueryDefinition;
/*  20:    */ import org.hibernate.engine.spi.NamedSQLQueryDefinition;
/*  21:    */ import org.hibernate.id.factory.IdentifierGeneratorFactory;
/*  22:    */ import org.hibernate.id.factory.spi.MutableIdentifierGeneratorFactory;
/*  23:    */ import org.hibernate.internal.CoreMessageLogger;
/*  24:    */ import org.hibernate.internal.util.Value;
/*  25:    */ import org.hibernate.internal.util.Value.DeferredInitializer;
/*  26:    */ import org.hibernate.metamodel.Metadata.Options;
/*  27:    */ import org.hibernate.metamodel.MetadataSourceProcessingOrder;
/*  28:    */ import org.hibernate.metamodel.MetadataSources;
/*  29:    */ import org.hibernate.metamodel.SessionFactoryBuilder;
/*  30:    */ import org.hibernate.metamodel.binding.AttributeBinding;
/*  31:    */ import org.hibernate.metamodel.binding.AttributeBindingContainer;
/*  32:    */ import org.hibernate.metamodel.binding.BasicAttributeBinding;
/*  33:    */ import org.hibernate.metamodel.binding.EntityBinding;
/*  34:    */ import org.hibernate.metamodel.binding.EntityIdentifier;
/*  35:    */ import org.hibernate.metamodel.binding.FetchProfile;
/*  36:    */ import org.hibernate.metamodel.binding.HibernateTypeDescriptor;
/*  37:    */ import org.hibernate.metamodel.binding.HierarchyDetails;
/*  38:    */ import org.hibernate.metamodel.binding.IdGenerator;
/*  39:    */ import org.hibernate.metamodel.binding.PluralAttributeBinding;
/*  40:    */ import org.hibernate.metamodel.binding.TypeDef;
/*  41:    */ import org.hibernate.metamodel.domain.Attribute;
/*  42:    */ import org.hibernate.metamodel.domain.BasicType;
/*  43:    */ import org.hibernate.metamodel.domain.Entity;
/*  44:    */ import org.hibernate.metamodel.domain.PluralAttribute;
/*  45:    */ import org.hibernate.metamodel.relational.Database;
/*  46:    */ import org.hibernate.metamodel.source.MappingDefaults;
/*  47:    */ import org.hibernate.metamodel.source.MetaAttributeContext;
/*  48:    */ import org.hibernate.metamodel.source.MetadataImplementor;
/*  49:    */ import org.hibernate.metamodel.source.MetadataSourceProcessor;
/*  50:    */ import org.hibernate.metamodel.source.annotations.AnnotationMetadataSourceProcessorImpl;
/*  51:    */ import org.hibernate.metamodel.source.hbm.HbmMetadataSourceProcessorImpl;
/*  52:    */ import org.hibernate.persister.spi.PersisterClassResolver;
/*  53:    */ import org.hibernate.service.ServiceRegistry;
/*  54:    */ import org.hibernate.service.classloading.spi.ClassLoaderService;
/*  55:    */ import org.hibernate.type.TypeResolver;
/*  56:    */ import org.jboss.logging.Logger;
/*  57:    */ 
/*  58:    */ public class MetadataImpl
/*  59:    */   implements MetadataImplementor, Serializable
/*  60:    */ {
/*  61: 81 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, MetadataImpl.class.getName());
/*  62:    */   private final ServiceRegistry serviceRegistry;
/*  63:    */   private final Metadata.Options options;
/*  64:    */   private final Value<ClassLoaderService> classLoaderService;
/*  65:    */   private final Value<PersisterClassResolver> persisterClassResolverService;
/*  66: 92 */   private TypeResolver typeResolver = new TypeResolver();
/*  67: 94 */   private SessionFactoryBuilder sessionFactoryBuilder = new SessionFactoryBuilderImpl(this);
/*  68:    */   private final MutableIdentifierGeneratorFactory identifierGeneratorFactory;
/*  69:    */   private final Database database;
/*  70:    */   private final MappingDefaults mappingDefaults;
/*  71:105 */   private Map<String, EntityBinding> entityBindingMap = new HashMap();
/*  72:107 */   private Map<String, PluralAttributeBinding> collectionBindingMap = new HashMap();
/*  73:108 */   private Map<String, FetchProfile> fetchProfiles = new HashMap();
/*  74:109 */   private Map<String, String> imports = new HashMap();
/*  75:110 */   private Map<String, TypeDef> typeDefs = new HashMap();
/*  76:111 */   private Map<String, IdGenerator> idGenerators = new HashMap();
/*  77:112 */   private Map<String, NamedQueryDefinition> namedQueryDefs = new HashMap();
/*  78:113 */   private Map<String, NamedSQLQueryDefinition> namedNativeQueryDefs = new HashMap();
/*  79:114 */   private Map<String, ResultSetMappingDefinition> resultSetMappings = new HashMap();
/*  80:115 */   private Map<String, FilterDefinition> filterDefs = new HashMap();
/*  81:117 */   private boolean globallyQuotedIdentifiers = false;
/*  82:    */   
/*  83:    */   public MetadataImpl(MetadataSources metadataSources, Metadata.Options options)
/*  84:    */   {
/*  85:120 */     this.serviceRegistry = metadataSources.getServiceRegistry();
/*  86:121 */     this.options = options;
/*  87:122 */     this.identifierGeneratorFactory = ((MutableIdentifierGeneratorFactory)this.serviceRegistry.getService(MutableIdentifierGeneratorFactory.class));
/*  88:    */     
/*  89:124 */     this.database = new Database(options);
/*  90:    */     
/*  91:126 */     this.mappingDefaults = new MappingDefaultsImpl(null);
/*  92:    */     MetadataSourceProcessor[] metadataSourceProcessors;
/*  93:    */     MetadataSourceProcessor[] metadataSourceProcessors;
/*  94:129 */     if (options.getMetadataSourceProcessingOrder() == MetadataSourceProcessingOrder.HBM_FIRST) {
/*  95:130 */       metadataSourceProcessors = new MetadataSourceProcessor[] { new HbmMetadataSourceProcessorImpl(this), new AnnotationMetadataSourceProcessorImpl(this) };
/*  96:    */     } else {
/*  97:136 */       metadataSourceProcessors = new MetadataSourceProcessor[] { new AnnotationMetadataSourceProcessorImpl(this), new HbmMetadataSourceProcessorImpl(this) };
/*  98:    */     }
/*  99:142 */     this.classLoaderService = new Value(new Value.DeferredInitializer()
/* 100:    */     {
/* 101:    */       public ClassLoaderService initialize()
/* 102:    */       {
/* 103:146 */         return (ClassLoaderService)MetadataImpl.this.serviceRegistry.getService(ClassLoaderService.class);
/* 104:    */       }
/* 105:149 */     });
/* 106:150 */     this.persisterClassResolverService = new Value(new Value.DeferredInitializer()
/* 107:    */     {
/* 108:    */       public PersisterClassResolver initialize()
/* 109:    */       {
/* 110:154 */         return (PersisterClassResolver)MetadataImpl.this.serviceRegistry.getService(PersisterClassResolver.class);
/* 111:    */       }
/* 112:159 */     });
/* 113:160 */     ArrayList<String> processedEntityNames = new ArrayList();
/* 114:    */     
/* 115:162 */     prepare(metadataSourceProcessors, metadataSources);
/* 116:163 */     bindIndependentMetadata(metadataSourceProcessors, metadataSources);
/* 117:164 */     bindTypeDependentMetadata(metadataSourceProcessors, metadataSources);
/* 118:165 */     bindMappingMetadata(metadataSourceProcessors, metadataSources, processedEntityNames);
/* 119:166 */     bindMappingDependentMetadata(metadataSourceProcessors, metadataSources);
/* 120:    */     
/* 121:    */ 
/* 122:169 */     new AssociationResolver(this).resolve();
/* 123:170 */     new HibernateTypeResolver(this).resolve();
/* 124:    */     
/* 125:172 */     new IdentifierGeneratorResolver(this).resolve();
/* 126:    */   }
/* 127:    */   
/* 128:    */   private void prepare(MetadataSourceProcessor[] metadataSourceProcessors, MetadataSources metadataSources)
/* 129:    */   {
/* 130:176 */     for (MetadataSourceProcessor metadataSourceProcessor : metadataSourceProcessors) {
/* 131:177 */       metadataSourceProcessor.prepare(metadataSources);
/* 132:    */     }
/* 133:    */   }
/* 134:    */   
/* 135:    */   private void bindIndependentMetadata(MetadataSourceProcessor[] metadataSourceProcessors, MetadataSources metadataSources)
/* 136:    */   {
/* 137:182 */     for (MetadataSourceProcessor metadataSourceProcessor : metadataSourceProcessors) {
/* 138:183 */       metadataSourceProcessor.processIndependentMetadata(metadataSources);
/* 139:    */     }
/* 140:    */   }
/* 141:    */   
/* 142:    */   private void bindTypeDependentMetadata(MetadataSourceProcessor[] metadataSourceProcessors, MetadataSources metadataSources)
/* 143:    */   {
/* 144:188 */     for (MetadataSourceProcessor metadataSourceProcessor : metadataSourceProcessors) {
/* 145:189 */       metadataSourceProcessor.processTypeDependentMetadata(metadataSources);
/* 146:    */     }
/* 147:    */   }
/* 148:    */   
/* 149:    */   private void bindMappingMetadata(MetadataSourceProcessor[] metadataSourceProcessors, MetadataSources metadataSources, List<String> processedEntityNames)
/* 150:    */   {
/* 151:194 */     for (MetadataSourceProcessor metadataSourceProcessor : metadataSourceProcessors) {
/* 152:195 */       metadataSourceProcessor.processMappingMetadata(metadataSources, processedEntityNames);
/* 153:    */     }
/* 154:    */   }
/* 155:    */   
/* 156:    */   private void bindMappingDependentMetadata(MetadataSourceProcessor[] metadataSourceProcessors, MetadataSources metadataSources)
/* 157:    */   {
/* 158:200 */     for (MetadataSourceProcessor metadataSourceProcessor : metadataSourceProcessors) {
/* 159:201 */       metadataSourceProcessor.processMappingDependentMetadata(metadataSources);
/* 160:    */     }
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void addFetchProfile(FetchProfile profile)
/* 164:    */   {
/* 165:207 */     if ((profile == null) || (profile.getName() == null)) {
/* 166:208 */       throw new IllegalArgumentException("Fetch profile object or name is null: " + profile);
/* 167:    */     }
/* 168:210 */     this.fetchProfiles.put(profile.getName(), profile);
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void addFilterDefinition(FilterDefinition def)
/* 172:    */   {
/* 173:215 */     if ((def == null) || (def.getFilterName() == null)) {
/* 174:216 */       throw new IllegalArgumentException("Filter definition object or name is null: " + def);
/* 175:    */     }
/* 176:218 */     this.filterDefs.put(def.getFilterName(), def);
/* 177:    */   }
/* 178:    */   
/* 179:    */   public Iterable<FilterDefinition> getFilterDefinitions()
/* 180:    */   {
/* 181:222 */     return this.filterDefs.values();
/* 182:    */   }
/* 183:    */   
/* 184:    */   public void addIdGenerator(IdGenerator generator)
/* 185:    */   {
/* 186:227 */     if ((generator == null) || (generator.getName() == null)) {
/* 187:228 */       throw new IllegalArgumentException("ID generator object or name is null.");
/* 188:    */     }
/* 189:230 */     this.idGenerators.put(generator.getName(), generator);
/* 190:    */   }
/* 191:    */   
/* 192:    */   public IdGenerator getIdGenerator(String name)
/* 193:    */   {
/* 194:235 */     if (name == null) {
/* 195:236 */       throw new IllegalArgumentException("null is not a valid generator name");
/* 196:    */     }
/* 197:238 */     return (IdGenerator)this.idGenerators.get(name);
/* 198:    */   }
/* 199:    */   
/* 200:    */   public void registerIdentifierGenerator(String name, String generatorClassName)
/* 201:    */   {
/* 202:242 */     this.identifierGeneratorFactory.register(name, classLoaderService().classForName(generatorClassName));
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void addNamedNativeQuery(NamedSQLQueryDefinition def)
/* 206:    */   {
/* 207:247 */     if ((def == null) || (def.getName() == null)) {
/* 208:248 */       throw new IllegalArgumentException("Named native query definition object or name is null: " + def.getQueryString());
/* 209:    */     }
/* 210:250 */     this.namedNativeQueryDefs.put(def.getName(), def);
/* 211:    */   }
/* 212:    */   
/* 213:    */   public NamedSQLQueryDefinition getNamedNativeQuery(String name)
/* 214:    */   {
/* 215:254 */     if (name == null) {
/* 216:255 */       throw new IllegalArgumentException("null is not a valid native query name");
/* 217:    */     }
/* 218:257 */     return (NamedSQLQueryDefinition)this.namedNativeQueryDefs.get(name);
/* 219:    */   }
/* 220:    */   
/* 221:    */   public Iterable<NamedSQLQueryDefinition> getNamedNativeQueryDefinitions()
/* 222:    */   {
/* 223:262 */     return this.namedNativeQueryDefs.values();
/* 224:    */   }
/* 225:    */   
/* 226:    */   public void addNamedQuery(NamedQueryDefinition def)
/* 227:    */   {
/* 228:267 */     if (def == null) {
/* 229:268 */       throw new IllegalArgumentException("Named query definition is null");
/* 230:    */     }
/* 231:270 */     if (def.getName() == null) {
/* 232:271 */       throw new IllegalArgumentException("Named query definition name is null: " + def.getQueryString());
/* 233:    */     }
/* 234:273 */     this.namedQueryDefs.put(def.getName(), def);
/* 235:    */   }
/* 236:    */   
/* 237:    */   public NamedQueryDefinition getNamedQuery(String name)
/* 238:    */   {
/* 239:277 */     if (name == null) {
/* 240:278 */       throw new IllegalArgumentException("null is not a valid query name");
/* 241:    */     }
/* 242:280 */     return (NamedQueryDefinition)this.namedQueryDefs.get(name);
/* 243:    */   }
/* 244:    */   
/* 245:    */   public Iterable<NamedQueryDefinition> getNamedQueryDefinitions()
/* 246:    */   {
/* 247:285 */     return this.namedQueryDefs.values();
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void addResultSetMapping(ResultSetMappingDefinition resultSetMappingDefinition)
/* 251:    */   {
/* 252:290 */     if ((resultSetMappingDefinition == null) || (resultSetMappingDefinition.getName() == null)) {
/* 253:291 */       throw new IllegalArgumentException("Result-set mapping object or name is null: " + resultSetMappingDefinition);
/* 254:    */     }
/* 255:293 */     this.resultSetMappings.put(resultSetMappingDefinition.getName(), resultSetMappingDefinition);
/* 256:    */   }
/* 257:    */   
/* 258:    */   public Iterable<ResultSetMappingDefinition> getResultSetMappingDefinitions()
/* 259:    */   {
/* 260:298 */     return this.resultSetMappings.values();
/* 261:    */   }
/* 262:    */   
/* 263:    */   public void addTypeDefinition(TypeDef typeDef)
/* 264:    */   {
/* 265:303 */     if (typeDef == null) {
/* 266:304 */       throw new IllegalArgumentException("Type definition is null");
/* 267:    */     }
/* 268:306 */     if (typeDef.getName() == null) {
/* 269:307 */       throw new IllegalArgumentException("Type definition name is null: " + typeDef.getTypeClass());
/* 270:    */     }
/* 271:309 */     TypeDef previous = (TypeDef)this.typeDefs.put(typeDef.getName(), typeDef);
/* 272:310 */     if (previous != null) {
/* 273:311 */       LOG.debugf("Duplicate typedef name [%s] now -> %s", typeDef.getName(), typeDef.getTypeClass());
/* 274:    */     }
/* 275:    */   }
/* 276:    */   
/* 277:    */   public Iterable<TypeDef> getTypeDefinitions()
/* 278:    */   {
/* 279:317 */     return this.typeDefs.values();
/* 280:    */   }
/* 281:    */   
/* 282:    */   public TypeDef getTypeDefinition(String name)
/* 283:    */   {
/* 284:322 */     return (TypeDef)this.typeDefs.get(name);
/* 285:    */   }
/* 286:    */   
/* 287:    */   private ClassLoaderService classLoaderService()
/* 288:    */   {
/* 289:326 */     return (ClassLoaderService)this.classLoaderService.getValue();
/* 290:    */   }
/* 291:    */   
/* 292:    */   private PersisterClassResolver persisterClassResolverService()
/* 293:    */   {
/* 294:330 */     return (PersisterClassResolver)this.persisterClassResolverService.getValue();
/* 295:    */   }
/* 296:    */   
/* 297:    */   public Metadata.Options getOptions()
/* 298:    */   {
/* 299:335 */     return this.options;
/* 300:    */   }
/* 301:    */   
/* 302:    */   public SessionFactory buildSessionFactory()
/* 303:    */   {
/* 304:340 */     return this.sessionFactoryBuilder.buildSessionFactory();
/* 305:    */   }
/* 306:    */   
/* 307:    */   public ServiceRegistry getServiceRegistry()
/* 308:    */   {
/* 309:345 */     return this.serviceRegistry;
/* 310:    */   }
/* 311:    */   
/* 312:    */   public <T> Class<T> locateClassByName(String name)
/* 313:    */   {
/* 314:351 */     return classLoaderService().classForName(name);
/* 315:    */   }
/* 316:    */   
/* 317:    */   public org.hibernate.metamodel.domain.Type makeJavaType(String className)
/* 318:    */   {
/* 319:357 */     return new BasicType(className, makeClassReference(className));
/* 320:    */   }
/* 321:    */   
/* 322:    */   public Value<Class<?>> makeClassReference(final String className)
/* 323:    */   {
/* 324:362 */     new Value(new Value.DeferredInitializer()
/* 325:    */     {
/* 326:    */       public Class<?> initialize()
/* 327:    */       {
/* 328:366 */         return ((ClassLoaderService)MetadataImpl.this.classLoaderService.getValue()).classForName(className);
/* 329:    */       }
/* 330:    */     });
/* 331:    */   }
/* 332:    */   
/* 333:    */   public String qualifyClassName(String name)
/* 334:    */   {
/* 335:374 */     return name;
/* 336:    */   }
/* 337:    */   
/* 338:    */   public Database getDatabase()
/* 339:    */   {
/* 340:379 */     return this.database;
/* 341:    */   }
/* 342:    */   
/* 343:    */   public EntityBinding getEntityBinding(String entityName)
/* 344:    */   {
/* 345:383 */     return (EntityBinding)this.entityBindingMap.get(entityName);
/* 346:    */   }
/* 347:    */   
/* 348:    */   public EntityBinding getRootEntityBinding(String entityName)
/* 349:    */   {
/* 350:388 */     EntityBinding binding = (EntityBinding)this.entityBindingMap.get(entityName);
/* 351:389 */     if (binding == null) {
/* 352:390 */       throw new IllegalStateException("Unknown entity binding: " + entityName);
/* 353:    */     }
/* 354:    */     do
/* 355:    */     {
/* 356:394 */       if (binding.isRoot()) {
/* 357:395 */         return binding;
/* 358:    */       }
/* 359:397 */       binding = binding.getSuperEntityBinding();
/* 360:398 */     } while (binding != null);
/* 361:400 */     throw new AssertionFailure("Entity binding has no root: " + entityName);
/* 362:    */   }
/* 363:    */   
/* 364:    */   public Iterable<EntityBinding> getEntityBindings()
/* 365:    */   {
/* 366:404 */     return this.entityBindingMap.values();
/* 367:    */   }
/* 368:    */   
/* 369:    */   public void addEntity(EntityBinding entityBinding)
/* 370:    */   {
/* 371:408 */     String entityName = entityBinding.getEntity().getName();
/* 372:409 */     if (this.entityBindingMap.containsKey(entityName)) {
/* 373:410 */       throw new DuplicateMappingException(DuplicateMappingException.Type.ENTITY, entityName);
/* 374:    */     }
/* 375:412 */     this.entityBindingMap.put(entityName, entityBinding);
/* 376:    */   }
/* 377:    */   
/* 378:    */   public PluralAttributeBinding getCollection(String collectionRole)
/* 379:    */   {
/* 380:416 */     return (PluralAttributeBinding)this.collectionBindingMap.get(collectionRole);
/* 381:    */   }
/* 382:    */   
/* 383:    */   public Iterable<PluralAttributeBinding> getCollectionBindings()
/* 384:    */   {
/* 385:421 */     return this.collectionBindingMap.values();
/* 386:    */   }
/* 387:    */   
/* 388:    */   public void addCollection(PluralAttributeBinding pluralAttributeBinding)
/* 389:    */   {
/* 390:425 */     String owningEntityName = pluralAttributeBinding.getContainer().getPathBase();
/* 391:426 */     String attributeName = pluralAttributeBinding.getAttribute().getName();
/* 392:427 */     String collectionRole = owningEntityName + '.' + attributeName;
/* 393:428 */     if (this.collectionBindingMap.containsKey(collectionRole)) {
/* 394:429 */       throw new DuplicateMappingException(DuplicateMappingException.Type.ENTITY, collectionRole);
/* 395:    */     }
/* 396:431 */     this.collectionBindingMap.put(collectionRole, pluralAttributeBinding);
/* 397:    */   }
/* 398:    */   
/* 399:    */   public void addImport(String importName, String entityName)
/* 400:    */   {
/* 401:435 */     if ((importName == null) || (entityName == null)) {
/* 402:436 */       throw new IllegalArgumentException("Import name or entity name is null");
/* 403:    */     }
/* 404:438 */     LOG.tracev("Import: {0} -> {1}", importName, entityName);
/* 405:439 */     String old = (String)this.imports.put(importName, entityName);
/* 406:440 */     if (old != null) {
/* 407:441 */       LOG.debug("import name [" + importName + "] overrode previous [{" + old + "}]");
/* 408:    */     }
/* 409:    */   }
/* 410:    */   
/* 411:    */   public Iterable<Map.Entry<String, String>> getImports()
/* 412:    */   {
/* 413:447 */     return this.imports.entrySet();
/* 414:    */   }
/* 415:    */   
/* 416:    */   public Iterable<FetchProfile> getFetchProfiles()
/* 417:    */   {
/* 418:452 */     return this.fetchProfiles.values();
/* 419:    */   }
/* 420:    */   
/* 421:    */   public TypeResolver getTypeResolver()
/* 422:    */   {
/* 423:456 */     return this.typeResolver;
/* 424:    */   }
/* 425:    */   
/* 426:    */   public SessionFactoryBuilder getSessionFactoryBuilder()
/* 427:    */   {
/* 428:461 */     return this.sessionFactoryBuilder;
/* 429:    */   }
/* 430:    */   
/* 431:    */   public NamingStrategy getNamingStrategy()
/* 432:    */   {
/* 433:466 */     return this.options.getNamingStrategy();
/* 434:    */   }
/* 435:    */   
/* 436:    */   public boolean isGloballyQuotedIdentifiers()
/* 437:    */   {
/* 438:471 */     return (this.globallyQuotedIdentifiers) || (getOptions().isGloballyQuotedIdentifiers());
/* 439:    */   }
/* 440:    */   
/* 441:    */   public void setGloballyQuotedIdentifiers(boolean globallyQuotedIdentifiers)
/* 442:    */   {
/* 443:475 */     this.globallyQuotedIdentifiers = globallyQuotedIdentifiers;
/* 444:    */   }
/* 445:    */   
/* 446:    */   public MappingDefaults getMappingDefaults()
/* 447:    */   {
/* 448:480 */     return this.mappingDefaults;
/* 449:    */   }
/* 450:    */   
/* 451:483 */   private final MetaAttributeContext globalMetaAttributeContext = new MetaAttributeContext();
/* 452:    */   private static final String DEFAULT_IDENTIFIER_COLUMN_NAME = "id";
/* 453:    */   private static final String DEFAULT_DISCRIMINATOR_COLUMN_NAME = "class";
/* 454:    */   private static final String DEFAULT_CASCADE = "none";
/* 455:    */   private static final String DEFAULT_PROPERTY_ACCESS = "property";
/* 456:    */   
/* 457:    */   public MetaAttributeContext getGlobalMetaAttributeContext()
/* 458:    */   {
/* 459:487 */     return this.globalMetaAttributeContext;
/* 460:    */   }
/* 461:    */   
/* 462:    */   public MetadataImplementor getMetadataImplementor()
/* 463:    */   {
/* 464:492 */     return this;
/* 465:    */   }
/* 466:    */   
/* 467:    */   public IdentifierGeneratorFactory getIdentifierGeneratorFactory()
/* 468:    */   {
/* 469:502 */     return this.identifierGeneratorFactory;
/* 470:    */   }
/* 471:    */   
/* 472:    */   public org.hibernate.type.Type getIdentifierType(String entityName)
/* 473:    */     throws MappingException
/* 474:    */   {
/* 475:507 */     EntityBinding entityBinding = getEntityBinding(entityName);
/* 476:508 */     if (entityBinding == null) {
/* 477:509 */       throw new MappingException("Entity binding not known: " + entityName);
/* 478:    */     }
/* 479:511 */     return entityBinding.getHierarchyDetails().getEntityIdentifier().getValueBinding().getHibernateTypeDescriptor().getResolvedTypeMapping();
/* 480:    */   }
/* 481:    */   
/* 482:    */   public String getIdentifierPropertyName(String entityName)
/* 483:    */     throws MappingException
/* 484:    */   {
/* 485:521 */     EntityBinding entityBinding = getEntityBinding(entityName);
/* 486:522 */     if (entityBinding == null) {
/* 487:523 */       throw new MappingException("Entity binding not known: " + entityName);
/* 488:    */     }
/* 489:525 */     AttributeBinding idBinding = entityBinding.getHierarchyDetails().getEntityIdentifier().getValueBinding();
/* 490:526 */     return idBinding == null ? null : idBinding.getAttribute().getName();
/* 491:    */   }
/* 492:    */   
/* 493:    */   public org.hibernate.type.Type getReferencedPropertyType(String entityName, String propertyName)
/* 494:    */     throws MappingException
/* 495:    */   {
/* 496:531 */     EntityBinding entityBinding = getEntityBinding(entityName);
/* 497:532 */     if (entityBinding == null) {
/* 498:533 */       throw new MappingException("Entity binding not known: " + entityName);
/* 499:    */     }
/* 500:536 */     AttributeBinding attributeBinding = entityBinding.locateAttributeBinding(propertyName);
/* 501:537 */     if (attributeBinding == null) {
/* 502:538 */       throw new MappingException("unknown property: " + entityName + '.' + propertyName);
/* 503:    */     }
/* 504:540 */     return attributeBinding.getHibernateTypeDescriptor().getResolvedTypeMapping();
/* 505:    */   }
/* 506:    */   
/* 507:    */   private class MappingDefaultsImpl
/* 508:    */     implements MappingDefaults
/* 509:    */   {
/* 510:    */     private MappingDefaultsImpl() {}
/* 511:    */     
/* 512:    */     public String getPackageName()
/* 513:    */     {
/* 514:547 */       return null;
/* 515:    */     }
/* 516:    */     
/* 517:    */     public String getSchemaName()
/* 518:    */     {
/* 519:552 */       return MetadataImpl.this.options.getDefaultSchemaName();
/* 520:    */     }
/* 521:    */     
/* 522:    */     public String getCatalogName()
/* 523:    */     {
/* 524:557 */       return MetadataImpl.this.options.getDefaultCatalogName();
/* 525:    */     }
/* 526:    */     
/* 527:    */     public String getIdColumnName()
/* 528:    */     {
/* 529:562 */       return "id";
/* 530:    */     }
/* 531:    */     
/* 532:    */     public String getDiscriminatorColumnName()
/* 533:    */     {
/* 534:567 */       return "class";
/* 535:    */     }
/* 536:    */     
/* 537:    */     public String getCascadeStyle()
/* 538:    */     {
/* 539:572 */       return "none";
/* 540:    */     }
/* 541:    */     
/* 542:    */     public String getPropertyAccessorName()
/* 543:    */     {
/* 544:577 */       return "property";
/* 545:    */     }
/* 546:    */     
/* 547:    */     public boolean areAssociationsLazy()
/* 548:    */     {
/* 549:582 */       return true;
/* 550:    */     }
/* 551:    */     
/* 552:585 */     private final Value<AccessType> regionFactorySpecifiedDefaultAccessType = new Value(new Value.DeferredInitializer()
/* 553:    */     {
/* 554:    */       public AccessType initialize()
/* 555:    */       {
/* 556:589 */         RegionFactory regionFactory = (RegionFactory)MetadataImpl.this.getServiceRegistry().getService(RegionFactory.class);
/* 557:590 */         return regionFactory.getDefaultAccessType();
/* 558:    */       }
/* 559:585 */     });
/* 560:    */     
/* 561:    */     public AccessType getCacheAccessType()
/* 562:    */     {
/* 563:597 */       return MetadataImpl.this.options.getDefaultAccessType() != null ? MetadataImpl.this.options.getDefaultAccessType() : (AccessType)this.regionFactorySpecifiedDefaultAccessType.getValue();
/* 564:    */     }
/* 565:    */   }
/* 566:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.internal.MetadataImpl
 * JD-Core Version:    0.7.0.1
 */