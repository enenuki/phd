/*   1:    */ package org.hibernate.tuple.entity;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Method;
/*   4:    */ import java.lang.reflect.Modifier;
/*   5:    */ import java.util.HashSet;
/*   6:    */ import java.util.Iterator;
/*   7:    */ import java.util.Map;
/*   8:    */ import java.util.Set;
/*   9:    */ import org.hibernate.EntityMode;
/*  10:    */ import org.hibernate.EntityNameResolver;
/*  11:    */ import org.hibernate.HibernateException;
/*  12:    */ import org.hibernate.MappingException;
/*  13:    */ import org.hibernate.PropertyNotFoundException;
/*  14:    */ import org.hibernate.bytecode.instrumentation.internal.FieldInterceptionHelper;
/*  15:    */ import org.hibernate.bytecode.instrumentation.spi.FieldInterceptor;
/*  16:    */ import org.hibernate.bytecode.spi.BytecodeProvider;
/*  17:    */ import org.hibernate.bytecode.spi.ProxyFactoryFactory;
/*  18:    */ import org.hibernate.bytecode.spi.ReflectionOptimizer;
/*  19:    */ import org.hibernate.bytecode.spi.ReflectionOptimizer.AccessOptimizer;
/*  20:    */ import org.hibernate.cfg.Environment;
/*  21:    */ import org.hibernate.classic.Lifecycle;
/*  22:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  23:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  24:    */ import org.hibernate.internal.CoreMessageLogger;
/*  25:    */ import org.hibernate.internal.util.ReflectHelper;
/*  26:    */ import org.hibernate.internal.util.Value;
/*  27:    */ import org.hibernate.mapping.KeyValue;
/*  28:    */ import org.hibernate.mapping.PersistentClass;
/*  29:    */ import org.hibernate.mapping.Property;
/*  30:    */ import org.hibernate.mapping.Subclass;
/*  31:    */ import org.hibernate.metamodel.binding.AttributeBinding;
/*  32:    */ import org.hibernate.metamodel.binding.AttributeBindingContainer;
/*  33:    */ import org.hibernate.metamodel.binding.BasicAttributeBinding;
/*  34:    */ import org.hibernate.metamodel.binding.EntityBinding;
/*  35:    */ import org.hibernate.metamodel.binding.EntityIdentifier;
/*  36:    */ import org.hibernate.metamodel.binding.HibernateTypeDescriptor;
/*  37:    */ import org.hibernate.metamodel.binding.HierarchyDetails;
/*  38:    */ import org.hibernate.metamodel.domain.Attribute;
/*  39:    */ import org.hibernate.metamodel.domain.Entity;
/*  40:    */ import org.hibernate.property.Getter;
/*  41:    */ import org.hibernate.property.PropertyAccessor;
/*  42:    */ import org.hibernate.property.PropertyAccessorFactory;
/*  43:    */ import org.hibernate.property.Setter;
/*  44:    */ import org.hibernate.proxy.HibernateProxy;
/*  45:    */ import org.hibernate.proxy.ProxyFactory;
/*  46:    */ import org.hibernate.tuple.Instantiator;
/*  47:    */ import org.hibernate.tuple.PojoInstantiator;
/*  48:    */ import org.hibernate.type.CompositeType;
/*  49:    */ import org.jboss.logging.Logger;
/*  50:    */ 
/*  51:    */ public class PojoEntityTuplizer
/*  52:    */   extends AbstractEntityTuplizer
/*  53:    */ {
/*  54: 72 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, PojoEntityTuplizer.class.getName());
/*  55:    */   private final Class mappedClass;
/*  56:    */   private final Class proxyInterface;
/*  57:    */   private final boolean lifecycleImplementor;
/*  58: 77 */   private final Set lazyPropertyNames = new HashSet();
/*  59:    */   private final ReflectionOptimizer optimizer;
/*  60:    */   private final boolean isInstrumented;
/*  61:    */   
/*  62:    */   public PojoEntityTuplizer(EntityMetamodel entityMetamodel, PersistentClass mappedEntity)
/*  63:    */   {
/*  64: 82 */     super(entityMetamodel, mappedEntity);
/*  65: 83 */     this.mappedClass = mappedEntity.getMappedClass();
/*  66: 84 */     this.proxyInterface = mappedEntity.getProxyInterface();
/*  67: 85 */     this.lifecycleImplementor = Lifecycle.class.isAssignableFrom(this.mappedClass);
/*  68: 86 */     this.isInstrumented = entityMetamodel.isInstrumented();
/*  69:    */     
/*  70: 88 */     Iterator iter = mappedEntity.getPropertyClosureIterator();
/*  71: 89 */     while (iter.hasNext())
/*  72:    */     {
/*  73: 90 */       Property property = (Property)iter.next();
/*  74: 91 */       if (property.isLazy()) {
/*  75: 92 */         this.lazyPropertyNames.add(property.getName());
/*  76:    */       }
/*  77:    */     }
/*  78: 96 */     String[] getterNames = new String[this.propertySpan];
/*  79: 97 */     String[] setterNames = new String[this.propertySpan];
/*  80: 98 */     Class[] propTypes = new Class[this.propertySpan];
/*  81: 99 */     for (int i = 0; i < this.propertySpan; i++)
/*  82:    */     {
/*  83:100 */       getterNames[i] = this.getters[i].getMethodName();
/*  84:101 */       setterNames[i] = this.setters[i].getMethodName();
/*  85:102 */       propTypes[i] = this.getters[i].getReturnType();
/*  86:    */     }
/*  87:105 */     if ((this.hasCustomAccessors) || (!Environment.useReflectionOptimizer())) {
/*  88:106 */       this.optimizer = null;
/*  89:    */     } else {
/*  90:110 */       this.optimizer = Environment.getBytecodeProvider().getReflectionOptimizer(this.mappedClass, getterNames, setterNames, propTypes);
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   public PojoEntityTuplizer(EntityMetamodel entityMetamodel, EntityBinding mappedEntity)
/*  95:    */   {
/*  96:119 */     super(entityMetamodel, mappedEntity);
/*  97:120 */     this.mappedClass = mappedEntity.getEntity().getClassReference();
/*  98:121 */     this.proxyInterface = ((Class)mappedEntity.getProxyInterfaceType().getValue());
/*  99:122 */     this.lifecycleImplementor = Lifecycle.class.isAssignableFrom(this.mappedClass);
/* 100:123 */     this.isInstrumented = entityMetamodel.isInstrumented();
/* 101:125 */     for (AttributeBinding property : mappedEntity.getAttributeBindingClosure()) {
/* 102:126 */       if (property.isLazy()) {
/* 103:127 */         this.lazyPropertyNames.add(property.getAttribute().getName());
/* 104:    */       }
/* 105:    */     }
/* 106:131 */     String[] getterNames = new String[this.propertySpan];
/* 107:132 */     String[] setterNames = new String[this.propertySpan];
/* 108:133 */     Class[] propTypes = new Class[this.propertySpan];
/* 109:134 */     for (int i = 0; i < this.propertySpan; i++)
/* 110:    */     {
/* 111:135 */       getterNames[i] = this.getters[i].getMethodName();
/* 112:136 */       setterNames[i] = this.setters[i].getMethodName();
/* 113:137 */       propTypes[i] = this.getters[i].getReturnType();
/* 114:    */     }
/* 115:140 */     if ((this.hasCustomAccessors) || (!Environment.useReflectionOptimizer())) {
/* 116:141 */       this.optimizer = null;
/* 117:    */     } else {
/* 118:145 */       this.optimizer = Environment.getBytecodeProvider().getReflectionOptimizer(this.mappedClass, getterNames, setterNames, propTypes);
/* 119:    */     }
/* 120:    */   }
/* 121:    */   
/* 122:    */   protected ProxyFactory buildProxyFactory(PersistentClass persistentClass, Getter idGetter, Setter idSetter)
/* 123:    */   {
/* 124:161 */     HashSet<Class> proxyInterfaces = new HashSet();
/* 125:162 */     proxyInterfaces.add(HibernateProxy.class);
/* 126:    */     
/* 127:164 */     Class mappedClass = persistentClass.getMappedClass();
/* 128:165 */     Class proxyInterface = persistentClass.getProxyInterface();
/* 129:167 */     if ((proxyInterface != null) && (!mappedClass.equals(proxyInterface)))
/* 130:    */     {
/* 131:168 */       if (!proxyInterface.isInterface()) {
/* 132:169 */         throw new MappingException("proxy must be either an interface, or the class itself: " + getEntityName());
/* 133:    */       }
/* 134:173 */       proxyInterfaces.add(proxyInterface);
/* 135:    */     }
/* 136:176 */     if (mappedClass.isInterface()) {
/* 137:177 */       proxyInterfaces.add(mappedClass);
/* 138:    */     }
/* 139:180 */     Iterator subclasses = persistentClass.getSubclassIterator();
/* 140:181 */     while (subclasses.hasNext())
/* 141:    */     {
/* 142:182 */       Subclass subclass = (Subclass)subclasses.next();
/* 143:183 */       Class subclassProxy = subclass.getProxyInterface();
/* 144:184 */       Class subclassClass = subclass.getMappedClass();
/* 145:185 */       if ((subclassProxy != null) && (!subclassClass.equals(subclassProxy)))
/* 146:    */       {
/* 147:186 */         if (!subclassProxy.isInterface()) {
/* 148:187 */           throw new MappingException("proxy must be either an interface, or the class itself: " + subclass.getEntityName());
/* 149:    */         }
/* 150:191 */         proxyInterfaces.add(subclassProxy);
/* 151:    */       }
/* 152:    */     }
/* 153:195 */     Iterator properties = persistentClass.getPropertyIterator();
/* 154:196 */     Class clazz = persistentClass.getMappedClass();
/* 155:197 */     while (properties.hasNext())
/* 156:    */     {
/* 157:198 */       Property property = (Property)properties.next();
/* 158:199 */       Method method = property.getGetter(clazz).getMethod();
/* 159:200 */       if ((method != null) && (Modifier.isFinal(method.getModifiers()))) {
/* 160:201 */         LOG.gettersOfLazyClassesCannotBeFinal(persistentClass.getEntityName(), property.getName());
/* 161:    */       }
/* 162:203 */       method = property.getSetter(clazz).getMethod();
/* 163:204 */       if ((method != null) && (Modifier.isFinal(method.getModifiers()))) {
/* 164:205 */         LOG.settersOfLazyClassesCannotBeFinal(persistentClass.getEntityName(), property.getName());
/* 165:    */       }
/* 166:    */     }
/* 167:209 */     Method idGetterMethod = idGetter == null ? null : idGetter.getMethod();
/* 168:210 */     Method idSetterMethod = idSetter == null ? null : idSetter.getMethod();
/* 169:    */     
/* 170:212 */     Method proxyGetIdentifierMethod = (idGetterMethod == null) || (proxyInterface == null) ? null : ReflectHelper.getMethod(proxyInterface, idGetterMethod);
/* 171:    */     
/* 172:    */ 
/* 173:215 */     Method proxySetIdentifierMethod = (idSetterMethod == null) || (proxyInterface == null) ? null : ReflectHelper.getMethod(proxyInterface, idSetterMethod);
/* 174:    */     
/* 175:    */ 
/* 176:    */ 
/* 177:219 */     ProxyFactory pf = buildProxyFactoryInternal(persistentClass, idGetter, idSetter);
/* 178:    */     try
/* 179:    */     {
/* 180:221 */       pf.postInstantiate(getEntityName(), mappedClass, proxyInterfaces, proxyGetIdentifierMethod, proxySetIdentifierMethod, persistentClass.hasEmbeddedIdentifier() ? (CompositeType)persistentClass.getIdentifier().getType() : null);
/* 181:    */     }
/* 182:    */     catch (HibernateException he)
/* 183:    */     {
/* 184:233 */       LOG.unableToCreateProxyFactory(getEntityName(), he);
/* 185:234 */       pf = null;
/* 186:    */     }
/* 187:236 */     return pf;
/* 188:    */   }
/* 189:    */   
/* 190:    */   protected ProxyFactory buildProxyFactoryInternal(PersistentClass persistentClass, Getter idGetter, Setter idSetter)
/* 191:    */   {
/* 192:241 */     return Environment.getBytecodeProvider().getProxyFactoryFactory().buildProxyFactory();
/* 193:    */   }
/* 194:    */   
/* 195:    */   protected Instantiator buildInstantiator(PersistentClass persistentClass)
/* 196:    */   {
/* 197:250 */     if (this.optimizer == null) {
/* 198:251 */       return new PojoInstantiator(persistentClass, null);
/* 199:    */     }
/* 200:254 */     return new PojoInstantiator(persistentClass, this.optimizer.getInstantiationOptimizer());
/* 201:    */   }
/* 202:    */   
/* 203:    */   protected ProxyFactory buildProxyFactory(EntityBinding entityBinding, Getter idGetter, Setter idSetter)
/* 204:    */   {
/* 205:265 */     HashSet<Class> proxyInterfaces = new HashSet();
/* 206:266 */     proxyInterfaces.add(HibernateProxy.class);
/* 207:    */     
/* 208:268 */     Class mappedClass = entityBinding.getEntity().getClassReference();
/* 209:269 */     Class proxyInterface = (Class)entityBinding.getProxyInterfaceType().getValue();
/* 210:271 */     if ((proxyInterface != null) && (!mappedClass.equals(proxyInterface)))
/* 211:    */     {
/* 212:272 */       if (!proxyInterface.isInterface()) {
/* 213:273 */         throw new MappingException("proxy must be either an interface, or the class itself: " + getEntityName());
/* 214:    */       }
/* 215:277 */       proxyInterfaces.add(proxyInterface);
/* 216:    */     }
/* 217:280 */     if (mappedClass.isInterface()) {
/* 218:281 */       proxyInterfaces.add(mappedClass);
/* 219:    */     }
/* 220:284 */     for (EntityBinding subEntityBinding : entityBinding.getPostOrderSubEntityBindingClosure())
/* 221:    */     {
/* 222:285 */       Class subclassProxy = (Class)subEntityBinding.getProxyInterfaceType().getValue();
/* 223:286 */       Class subclassClass = subEntityBinding.getClassReference();
/* 224:287 */       if ((subclassProxy != null) && (!subclassClass.equals(subclassProxy)))
/* 225:    */       {
/* 226:288 */         if (!subclassProxy.isInterface()) {
/* 227:289 */           throw new MappingException("proxy must be either an interface, or the class itself: " + subEntityBinding.getEntity().getName());
/* 228:    */         }
/* 229:293 */         proxyInterfaces.add(subclassProxy);
/* 230:    */       }
/* 231:    */     }
/* 232:297 */     for (AttributeBinding property : entityBinding.attributeBindings())
/* 233:    */     {
/* 234:298 */       Method method = getGetter(property).getMethod();
/* 235:299 */       if ((method != null) && (Modifier.isFinal(method.getModifiers()))) {
/* 236:300 */         LOG.gettersOfLazyClassesCannotBeFinal(entityBinding.getEntity().getName(), property.getAttribute().getName());
/* 237:    */       }
/* 238:302 */       method = getSetter(property).getMethod();
/* 239:303 */       if ((method != null) && (Modifier.isFinal(method.getModifiers()))) {
/* 240:304 */         LOG.settersOfLazyClassesCannotBeFinal(entityBinding.getEntity().getName(), property.getAttribute().getName());
/* 241:    */       }
/* 242:    */     }
/* 243:308 */     Method idGetterMethod = idGetter == null ? null : idGetter.getMethod();
/* 244:309 */     Method idSetterMethod = idSetter == null ? null : idSetter.getMethod();
/* 245:    */     
/* 246:311 */     Method proxyGetIdentifierMethod = (idGetterMethod == null) || (proxyInterface == null) ? null : ReflectHelper.getMethod(proxyInterface, idGetterMethod);
/* 247:    */     
/* 248:    */ 
/* 249:314 */     Method proxySetIdentifierMethod = (idSetterMethod == null) || (proxyInterface == null) ? null : ReflectHelper.getMethod(proxyInterface, idSetterMethod);
/* 250:    */     
/* 251:    */ 
/* 252:    */ 
/* 253:318 */     ProxyFactory pf = buildProxyFactoryInternal(entityBinding, idGetter, idSetter);
/* 254:    */     try
/* 255:    */     {
/* 256:320 */       pf.postInstantiate(getEntityName(), mappedClass, proxyInterfaces, proxyGetIdentifierMethod, proxySetIdentifierMethod, entityBinding.getHierarchyDetails().getEntityIdentifier().isEmbedded() ? (CompositeType)entityBinding.getHierarchyDetails().getEntityIdentifier().getValueBinding().getHibernateTypeDescriptor().getResolvedTypeMapping() : null);
/* 257:    */     }
/* 258:    */     catch (HibernateException he)
/* 259:    */     {
/* 260:337 */       LOG.unableToCreateProxyFactory(getEntityName(), he);
/* 261:338 */       pf = null;
/* 262:    */     }
/* 263:340 */     return pf;
/* 264:    */   }
/* 265:    */   
/* 266:    */   protected ProxyFactory buildProxyFactoryInternal(EntityBinding entityBinding, Getter idGetter, Setter idSetter)
/* 267:    */   {
/* 268:345 */     return Environment.getBytecodeProvider().getProxyFactoryFactory().buildProxyFactory();
/* 269:    */   }
/* 270:    */   
/* 271:    */   protected Instantiator buildInstantiator(EntityBinding entityBinding)
/* 272:    */   {
/* 273:354 */     if (this.optimizer == null) {
/* 274:355 */       return new PojoInstantiator(entityBinding, null);
/* 275:    */     }
/* 276:358 */     return new PojoInstantiator(entityBinding, this.optimizer.getInstantiationOptimizer());
/* 277:    */   }
/* 278:    */   
/* 279:    */   public void setPropertyValues(Object entity, Object[] values)
/* 280:    */     throws HibernateException
/* 281:    */   {
/* 282:367 */     if ((!getEntityMetamodel().hasLazyProperties()) && (this.optimizer != null) && (this.optimizer.getAccessOptimizer() != null)) {
/* 283:368 */       setPropertyValuesWithOptimizer(entity, values);
/* 284:    */     } else {
/* 285:371 */       super.setPropertyValues(entity, values);
/* 286:    */     }
/* 287:    */   }
/* 288:    */   
/* 289:    */   public Object[] getPropertyValues(Object entity)
/* 290:    */     throws HibernateException
/* 291:    */   {
/* 292:380 */     if ((shouldGetAllProperties(entity)) && (this.optimizer != null) && (this.optimizer.getAccessOptimizer() != null)) {
/* 293:381 */       return getPropertyValuesWithOptimizer(entity);
/* 294:    */     }
/* 295:384 */     return super.getPropertyValues(entity);
/* 296:    */   }
/* 297:    */   
/* 298:    */   public Object[] getPropertyValuesToInsert(Object entity, Map mergeMap, SessionImplementor session)
/* 299:    */     throws HibernateException
/* 300:    */   {
/* 301:393 */     if ((shouldGetAllProperties(entity)) && (this.optimizer != null) && (this.optimizer.getAccessOptimizer() != null)) {
/* 302:394 */       return getPropertyValuesWithOptimizer(entity);
/* 303:    */     }
/* 304:397 */     return super.getPropertyValuesToInsert(entity, mergeMap, session);
/* 305:    */   }
/* 306:    */   
/* 307:    */   protected void setPropertyValuesWithOptimizer(Object object, Object[] values)
/* 308:    */   {
/* 309:402 */     this.optimizer.getAccessOptimizer().setPropertyValues(object, values);
/* 310:    */   }
/* 311:    */   
/* 312:    */   protected Object[] getPropertyValuesWithOptimizer(Object object)
/* 313:    */   {
/* 314:406 */     return this.optimizer.getAccessOptimizer().getPropertyValues(object);
/* 315:    */   }
/* 316:    */   
/* 317:    */   public EntityMode getEntityMode()
/* 318:    */   {
/* 319:413 */     return EntityMode.POJO;
/* 320:    */   }
/* 321:    */   
/* 322:    */   public Class getMappedClass()
/* 323:    */   {
/* 324:420 */     return this.mappedClass;
/* 325:    */   }
/* 326:    */   
/* 327:    */   public boolean isLifecycleImplementor()
/* 328:    */   {
/* 329:428 */     return this.lifecycleImplementor;
/* 330:    */   }
/* 331:    */   
/* 332:    */   protected Getter buildPropertyGetter(Property mappedProperty, PersistentClass mappedEntity)
/* 333:    */   {
/* 334:436 */     return mappedProperty.getGetter(mappedEntity.getMappedClass());
/* 335:    */   }
/* 336:    */   
/* 337:    */   protected Setter buildPropertySetter(Property mappedProperty, PersistentClass mappedEntity)
/* 338:    */   {
/* 339:444 */     return mappedProperty.getSetter(mappedEntity.getMappedClass());
/* 340:    */   }
/* 341:    */   
/* 342:    */   protected Getter buildPropertyGetter(AttributeBinding mappedProperty)
/* 343:    */   {
/* 344:452 */     return getGetter(mappedProperty);
/* 345:    */   }
/* 346:    */   
/* 347:    */   protected Setter buildPropertySetter(AttributeBinding mappedProperty)
/* 348:    */   {
/* 349:460 */     return getSetter(mappedProperty);
/* 350:    */   }
/* 351:    */   
/* 352:    */   private Getter getGetter(AttributeBinding mappedProperty)
/* 353:    */     throws PropertyNotFoundException, MappingException
/* 354:    */   {
/* 355:464 */     return getPropertyAccessor(mappedProperty).getGetter(mappedProperty.getContainer().getClassReference(), mappedProperty.getAttribute().getName());
/* 356:    */   }
/* 357:    */   
/* 358:    */   private Setter getSetter(AttributeBinding mappedProperty)
/* 359:    */     throws PropertyNotFoundException, MappingException
/* 360:    */   {
/* 361:471 */     return getPropertyAccessor(mappedProperty).getSetter(mappedProperty.getContainer().getClassReference(), mappedProperty.getAttribute().getName());
/* 362:    */   }
/* 363:    */   
/* 364:    */   private PropertyAccessor getPropertyAccessor(AttributeBinding mappedProperty)
/* 365:    */     throws MappingException
/* 366:    */   {
/* 367:479 */     return PropertyAccessorFactory.getPropertyAccessor(mappedProperty.getContainer().getClassReference(), mappedProperty.getPropertyAccessorName());
/* 368:    */   }
/* 369:    */   
/* 370:    */   public Class getConcreteProxyClass()
/* 371:    */   {
/* 372:489 */     return this.proxyInterface;
/* 373:    */   }
/* 374:    */   
/* 375:    */   public void afterInitialize(Object entity, boolean lazyPropertiesAreUnfetched, SessionImplementor session)
/* 376:    */   {
/* 377:499 */     if (isInstrumented())
/* 378:    */     {
/* 379:500 */       Set lazyProps = (lazyPropertiesAreUnfetched) && (getEntityMetamodel().hasLazyProperties()) ? this.lazyPropertyNames : null;
/* 380:    */       
/* 381:    */ 
/* 382:    */ 
/* 383:504 */       FieldInterceptionHelper.injectFieldInterceptor(entity, getEntityName(), lazyProps, session);
/* 384:    */     }
/* 385:    */   }
/* 386:    */   
/* 387:    */   public boolean hasUninitializedLazyProperties(Object entity)
/* 388:    */   {
/* 389:513 */     if (getEntityMetamodel().hasLazyProperties())
/* 390:    */     {
/* 391:514 */       FieldInterceptor callback = FieldInterceptionHelper.extractFieldInterceptor(entity);
/* 392:515 */       return (callback != null) && (!callback.isInitialized());
/* 393:    */     }
/* 394:518 */     return false;
/* 395:    */   }
/* 396:    */   
/* 397:    */   public boolean isInstrumented()
/* 398:    */   {
/* 399:526 */     return this.isInstrumented;
/* 400:    */   }
/* 401:    */   
/* 402:    */   public String determineConcreteSubclassEntityName(Object entityInstance, SessionFactoryImplementor factory)
/* 403:    */   {
/* 404:533 */     Class concreteEntityClass = entityInstance.getClass();
/* 405:534 */     if (concreteEntityClass == getMappedClass()) {
/* 406:535 */       return getEntityName();
/* 407:    */     }
/* 408:538 */     String entityName = getEntityMetamodel().findEntityNameByEntityClass(concreteEntityClass);
/* 409:539 */     if (entityName == null) {
/* 410:540 */       throw new HibernateException("Unable to resolve entity name from Class [" + concreteEntityClass.getName() + "]" + " expected instance/subclass of [" + getEntityName() + "]");
/* 411:    */     }
/* 412:545 */     return entityName;
/* 413:    */   }
/* 414:    */   
/* 415:    */   public EntityNameResolver[] getEntityNameResolvers()
/* 416:    */   {
/* 417:553 */     return null;
/* 418:    */   }
/* 419:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tuple.entity.PojoEntityTuplizer
 * JD-Core Version:    0.7.0.1
 */