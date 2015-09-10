/*   1:    */ package org.hibernate.tuple.entity;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.Map;
/*   6:    */ import java.util.Set;
/*   7:    */ import org.hibernate.EntityMode;
/*   8:    */ import org.hibernate.HibernateException;
/*   9:    */ import org.hibernate.MappingException;
/*  10:    */ import org.hibernate.bytecode.instrumentation.spi.LazyPropertyInitializer;
/*  11:    */ import org.hibernate.engine.spi.EntityEntry;
/*  12:    */ import org.hibernate.engine.spi.EntityKey;
/*  13:    */ import org.hibernate.engine.spi.IdentifierValue;
/*  14:    */ import org.hibernate.engine.spi.PersistenceContext;
/*  15:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  16:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  17:    */ import org.hibernate.engine.spi.VersionValue;
/*  18:    */ import org.hibernate.event.service.spi.EventListenerGroup;
/*  19:    */ import org.hibernate.event.service.spi.EventListenerRegistry;
/*  20:    */ import org.hibernate.event.spi.EventSource;
/*  21:    */ import org.hibernate.event.spi.EventType;
/*  22:    */ import org.hibernate.event.spi.PersistEvent;
/*  23:    */ import org.hibernate.event.spi.PersistEventListener;
/*  24:    */ import org.hibernate.id.Assigned;
/*  25:    */ import org.hibernate.internal.CoreMessageLogger;
/*  26:    */ import org.hibernate.mapping.Component;
/*  27:    */ import org.hibernate.mapping.PersistentClass;
/*  28:    */ import org.hibernate.mapping.Property;
/*  29:    */ import org.hibernate.metamodel.binding.AttributeBinding;
/*  30:    */ import org.hibernate.metamodel.binding.EntityBinding;
/*  31:    */ import org.hibernate.metamodel.binding.EntityIdentifier;
/*  32:    */ import org.hibernate.metamodel.binding.HierarchyDetails;
/*  33:    */ import org.hibernate.property.Getter;
/*  34:    */ import org.hibernate.property.Setter;
/*  35:    */ import org.hibernate.proxy.HibernateProxy;
/*  36:    */ import org.hibernate.proxy.LazyInitializer;
/*  37:    */ import org.hibernate.proxy.ProxyFactory;
/*  38:    */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  39:    */ import org.hibernate.tuple.IdentifierProperty;
/*  40:    */ import org.hibernate.tuple.Instantiator;
/*  41:    */ import org.hibernate.tuple.StandardProperty;
/*  42:    */ import org.hibernate.tuple.VersionProperty;
/*  43:    */ import org.hibernate.type.ComponentType;
/*  44:    */ import org.hibernate.type.CompositeType;
/*  45:    */ import org.hibernate.type.EntityType;
/*  46:    */ import org.hibernate.type.Type;
/*  47:    */ import org.jboss.logging.Logger;
/*  48:    */ 
/*  49:    */ public abstract class AbstractEntityTuplizer
/*  50:    */   implements EntityTuplizer
/*  51:    */ {
/*  52: 75 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, AbstractEntityTuplizer.class.getName());
/*  53:    */   private final EntityMetamodel entityMetamodel;
/*  54:    */   private final Getter idGetter;
/*  55:    */   private final Setter idSetter;
/*  56:    */   protected final Getter[] getters;
/*  57:    */   protected final Setter[] setters;
/*  58:    */   protected final int propertySpan;
/*  59:    */   protected final boolean hasCustomAccessors;
/*  60:    */   private final Instantiator instantiator;
/*  61:    */   private final ProxyFactory proxyFactory;
/*  62:    */   private final CompositeType identifierMapperType;
/*  63:    */   private final MappedIdentifierValueMarshaller mappedIdentifierValueMarshaller;
/*  64:    */   
/*  65:    */   public Type getIdentifierMapperType()
/*  66:    */   {
/*  67: 96 */     return this.identifierMapperType;
/*  68:    */   }
/*  69:    */   
/*  70:    */   protected abstract Getter buildPropertyGetter(Property paramProperty, PersistentClass paramPersistentClass);
/*  71:    */   
/*  72:    */   protected abstract Setter buildPropertySetter(Property paramProperty, PersistentClass paramPersistentClass);
/*  73:    */   
/*  74:    */   protected abstract Instantiator buildInstantiator(PersistentClass paramPersistentClass);
/*  75:    */   
/*  76:    */   protected abstract ProxyFactory buildProxyFactory(PersistentClass paramPersistentClass, Getter paramGetter, Setter paramSetter);
/*  77:    */   
/*  78:    */   protected abstract Getter buildPropertyGetter(AttributeBinding paramAttributeBinding);
/*  79:    */   
/*  80:    */   protected abstract Setter buildPropertySetter(AttributeBinding paramAttributeBinding);
/*  81:    */   
/*  82:    */   protected abstract Instantiator buildInstantiator(EntityBinding paramEntityBinding);
/*  83:    */   
/*  84:    */   protected abstract ProxyFactory buildProxyFactory(EntityBinding paramEntityBinding, Getter paramGetter, Setter paramSetter);
/*  85:    */   
/*  86:    */   public AbstractEntityTuplizer(EntityMetamodel entityMetamodel, PersistentClass mappingInfo)
/*  87:    */   {
/*  88:178 */     this.entityMetamodel = entityMetamodel;
/*  89:180 */     if (!entityMetamodel.getIdentifierProperty().isVirtual())
/*  90:    */     {
/*  91:181 */       this.idGetter = buildPropertyGetter(mappingInfo.getIdentifierProperty(), mappingInfo);
/*  92:182 */       this.idSetter = buildPropertySetter(mappingInfo.getIdentifierProperty(), mappingInfo);
/*  93:    */     }
/*  94:    */     else
/*  95:    */     {
/*  96:185 */       this.idGetter = null;
/*  97:186 */       this.idSetter = null;
/*  98:    */     }
/*  99:189 */     this.propertySpan = entityMetamodel.getPropertySpan();
/* 100:    */     
/* 101:191 */     this.getters = new Getter[this.propertySpan];
/* 102:192 */     this.setters = new Setter[this.propertySpan];
/* 103:    */     
/* 104:194 */     Iterator itr = mappingInfo.getPropertyClosureIterator();
/* 105:195 */     boolean foundCustomAccessor = false;
/* 106:196 */     int i = 0;
/* 107:197 */     while (itr.hasNext())
/* 108:    */     {
/* 109:199 */       Property property = (Property)itr.next();
/* 110:200 */       this.getters[i] = buildPropertyGetter(property, mappingInfo);
/* 111:201 */       this.setters[i] = buildPropertySetter(property, mappingInfo);
/* 112:202 */       if (!property.isBasicPropertyAccessor()) {
/* 113:203 */         foundCustomAccessor = true;
/* 114:    */       }
/* 115:205 */       i++;
/* 116:    */     }
/* 117:207 */     this.hasCustomAccessors = foundCustomAccessor;
/* 118:    */     
/* 119:209 */     this.instantiator = buildInstantiator(mappingInfo);
/* 120:211 */     if (entityMetamodel.isLazy())
/* 121:    */     {
/* 122:212 */       this.proxyFactory = buildProxyFactory(mappingInfo, this.idGetter, this.idSetter);
/* 123:213 */       if (this.proxyFactory == null) {
/* 124:214 */         entityMetamodel.setLazy(false);
/* 125:    */       }
/* 126:    */     }
/* 127:    */     else
/* 128:    */     {
/* 129:218 */       this.proxyFactory = null;
/* 130:    */     }
/* 131:221 */     Component mapper = mappingInfo.getIdentifierMapper();
/* 132:222 */     if (mapper == null)
/* 133:    */     {
/* 134:223 */       this.identifierMapperType = null;
/* 135:224 */       this.mappedIdentifierValueMarshaller = null;
/* 136:    */     }
/* 137:    */     else
/* 138:    */     {
/* 139:227 */       this.identifierMapperType = ((CompositeType)mapper.getType());
/* 140:228 */       this.mappedIdentifierValueMarshaller = buildMappedIdentifierValueMarshaller((ComponentType)entityMetamodel.getIdentifierProperty().getType(), (ComponentType)this.identifierMapperType);
/* 141:    */     }
/* 142:    */   }
/* 143:    */   
/* 144:    */   public AbstractEntityTuplizer(EntityMetamodel entityMetamodel, EntityBinding mappingInfo)
/* 145:    */   {
/* 146:242 */     this.entityMetamodel = entityMetamodel;
/* 147:244 */     if (!entityMetamodel.getIdentifierProperty().isVirtual())
/* 148:    */     {
/* 149:245 */       this.idGetter = buildPropertyGetter(mappingInfo.getHierarchyDetails().getEntityIdentifier().getValueBinding());
/* 150:246 */       this.idSetter = buildPropertySetter(mappingInfo.getHierarchyDetails().getEntityIdentifier().getValueBinding());
/* 151:    */     }
/* 152:    */     else
/* 153:    */     {
/* 154:249 */       this.idGetter = null;
/* 155:250 */       this.idSetter = null;
/* 156:    */     }
/* 157:253 */     this.propertySpan = entityMetamodel.getPropertySpan();
/* 158:    */     
/* 159:255 */     this.getters = new Getter[this.propertySpan];
/* 160:256 */     this.setters = new Setter[this.propertySpan];
/* 161:    */     
/* 162:258 */     boolean foundCustomAccessor = false;
/* 163:259 */     int i = 0;
/* 164:260 */     for (AttributeBinding property : mappingInfo.getAttributeBindingClosure()) {
/* 165:261 */       if (property != mappingInfo.getHierarchyDetails().getEntityIdentifier().getValueBinding())
/* 166:    */       {
/* 167:266 */         this.getters[i] = buildPropertyGetter(property);
/* 168:267 */         this.setters[i] = buildPropertySetter(property);
/* 169:268 */         if (!property.isBasicPropertyAccessor()) {
/* 170:269 */           foundCustomAccessor = true;
/* 171:    */         }
/* 172:271 */         i++;
/* 173:    */       }
/* 174:    */     }
/* 175:273 */     this.hasCustomAccessors = foundCustomAccessor;
/* 176:    */     
/* 177:275 */     this.instantiator = buildInstantiator(mappingInfo);
/* 178:277 */     if (entityMetamodel.isLazy())
/* 179:    */     {
/* 180:278 */       this.proxyFactory = buildProxyFactory(mappingInfo, this.idGetter, this.idSetter);
/* 181:279 */       if (this.proxyFactory == null) {
/* 182:280 */         entityMetamodel.setLazy(false);
/* 183:    */       }
/* 184:    */     }
/* 185:    */     else
/* 186:    */     {
/* 187:284 */       this.proxyFactory = null;
/* 188:    */     }
/* 189:290 */     Component mapper = null;
/* 190:291 */     if (mapper == null)
/* 191:    */     {
/* 192:292 */       this.identifierMapperType = null;
/* 193:293 */       this.mappedIdentifierValueMarshaller = null;
/* 194:    */     }
/* 195:    */     else
/* 196:    */     {
/* 197:296 */       this.identifierMapperType = ((CompositeType)mapper.getType());
/* 198:297 */       this.mappedIdentifierValueMarshaller = buildMappedIdentifierValueMarshaller((ComponentType)entityMetamodel.getIdentifierProperty().getType(), (ComponentType)this.identifierMapperType);
/* 199:    */     }
/* 200:    */   }
/* 201:    */   
/* 202:    */   protected String getEntityName()
/* 203:    */   {
/* 204:309 */     return this.entityMetamodel.getName();
/* 205:    */   }
/* 206:    */   
/* 207:    */   protected Set getSubclassEntityNames()
/* 208:    */   {
/* 209:319 */     return this.entityMetamodel.getSubclassEntityNames();
/* 210:    */   }
/* 211:    */   
/* 212:    */   public Serializable getIdentifier(Object entity)
/* 213:    */     throws HibernateException
/* 214:    */   {
/* 215:323 */     return getIdentifier(entity, null);
/* 216:    */   }
/* 217:    */   
/* 218:    */   public Serializable getIdentifier(Object entity, SessionImplementor session)
/* 219:    */   {
/* 220:    */     Object id;
/* 221:    */     Object id;
/* 222:328 */     if (this.entityMetamodel.getIdentifierProperty().isEmbedded())
/* 223:    */     {
/* 224:329 */       id = entity;
/* 225:    */     }
/* 226:    */     else
/* 227:    */     {
/* 228:    */       Object id;
/* 229:332 */       if (this.idGetter == null)
/* 230:    */       {
/* 231:333 */         if (this.identifierMapperType == null) {
/* 232:334 */           throw new HibernateException("The class has no identifier property: " + getEntityName());
/* 233:    */         }
/* 234:337 */         id = this.mappedIdentifierValueMarshaller.getIdentifier(entity, getEntityMode(), session);
/* 235:    */       }
/* 236:    */       else
/* 237:    */       {
/* 238:341 */         id = this.idGetter.get(entity);
/* 239:    */       }
/* 240:    */     }
/* 241:    */     try
/* 242:    */     {
/* 243:346 */       return (Serializable)id;
/* 244:    */     }
/* 245:    */     catch (ClassCastException cce)
/* 246:    */     {
/* 247:349 */       StringBuffer msg = new StringBuffer("Identifier classes must be serializable. ");
/* 248:350 */       if (id != null) {
/* 249:351 */         msg.append(id.getClass().getName()).append(" is not serializable. ");
/* 250:    */       }
/* 251:353 */       if (cce.getMessage() != null) {
/* 252:354 */         msg.append(cce.getMessage());
/* 253:    */       }
/* 254:356 */       throw new ClassCastException(msg.toString());
/* 255:    */     }
/* 256:    */   }
/* 257:    */   
/* 258:    */   public void setIdentifier(Object entity, Serializable id)
/* 259:    */     throws HibernateException
/* 260:    */   {
/* 261:366 */     setIdentifier(entity, id, null);
/* 262:    */   }
/* 263:    */   
/* 264:    */   public void setIdentifier(Object entity, Serializable id, SessionImplementor session)
/* 265:    */   {
/* 266:374 */     if (this.entityMetamodel.getIdentifierProperty().isEmbedded())
/* 267:    */     {
/* 268:375 */       if (entity != id)
/* 269:    */       {
/* 270:376 */         CompositeType copier = (CompositeType)this.entityMetamodel.getIdentifierProperty().getType();
/* 271:377 */         copier.setPropertyValues(entity, copier.getPropertyValues(id, getEntityMode()), getEntityMode());
/* 272:    */       }
/* 273:    */     }
/* 274:380 */     else if (this.idSetter != null) {
/* 275:381 */       this.idSetter.set(entity, id, getFactory());
/* 276:383 */     } else if (this.identifierMapperType != null) {
/* 277:384 */       this.mappedIdentifierValueMarshaller.setIdentifier(entity, id, getEntityMode(), session);
/* 278:    */     }
/* 279:    */   }
/* 280:    */   
/* 281:    */   private static MappedIdentifierValueMarshaller buildMappedIdentifierValueMarshaller(ComponentType mappedIdClassComponentType, ComponentType virtualIdComponent)
/* 282:    */   {
/* 283:409 */     boolean wereAllEquivalent = true;
/* 284:411 */     for (int i = 0; i < virtualIdComponent.getSubtypes().length; i++) {
/* 285:412 */       if ((virtualIdComponent.getSubtypes()[i].isEntityType()) && (!mappedIdClassComponentType.getSubtypes()[i].isEntityType()))
/* 286:    */       {
/* 287:414 */         wereAllEquivalent = false;
/* 288:415 */         break;
/* 289:    */       }
/* 290:    */     }
/* 291:419 */     return wereAllEquivalent ? new NormalMappedIdentifierValueMarshaller(virtualIdComponent, mappedIdClassComponentType, null) : new IncrediblySillyJpaMapsIdMappedIdentifierValueMarshaller(virtualIdComponent, mappedIdClassComponentType, null);
/* 292:    */   }
/* 293:    */   
/* 294:    */   private static abstract interface MappedIdentifierValueMarshaller
/* 295:    */   {
/* 296:    */     public abstract Object getIdentifier(Object paramObject, EntityMode paramEntityMode, SessionImplementor paramSessionImplementor);
/* 297:    */     
/* 298:    */     public abstract void setIdentifier(Object paramObject, Serializable paramSerializable, EntityMode paramEntityMode, SessionImplementor paramSessionImplementor);
/* 299:    */   }
/* 300:    */   
/* 301:    */   private static class NormalMappedIdentifierValueMarshaller
/* 302:    */     implements AbstractEntityTuplizer.MappedIdentifierValueMarshaller
/* 303:    */   {
/* 304:    */     private final ComponentType virtualIdComponent;
/* 305:    */     private final ComponentType mappedIdentifierType;
/* 306:    */     
/* 307:    */     private NormalMappedIdentifierValueMarshaller(ComponentType virtualIdComponent, ComponentType mappedIdentifierType)
/* 308:    */     {
/* 309:429 */       this.virtualIdComponent = virtualIdComponent;
/* 310:430 */       this.mappedIdentifierType = mappedIdentifierType;
/* 311:    */     }
/* 312:    */     
/* 313:    */     public Object getIdentifier(Object entity, EntityMode entityMode, SessionImplementor session)
/* 314:    */     {
/* 315:434 */       Object id = this.mappedIdentifierType.instantiate(entityMode);
/* 316:435 */       Object[] propertyValues = this.virtualIdComponent.getPropertyValues(entity, entityMode);
/* 317:436 */       this.mappedIdentifierType.setPropertyValues(id, propertyValues, entityMode);
/* 318:437 */       return id;
/* 319:    */     }
/* 320:    */     
/* 321:    */     public void setIdentifier(Object entity, Serializable id, EntityMode entityMode, SessionImplementor session)
/* 322:    */     {
/* 323:441 */       this.virtualIdComponent.setPropertyValues(entity, this.mappedIdentifierType.getPropertyValues(id, session), entityMode);
/* 324:    */     }
/* 325:    */   }
/* 326:    */   
/* 327:    */   private static class IncrediblySillyJpaMapsIdMappedIdentifierValueMarshaller
/* 328:    */     implements AbstractEntityTuplizer.MappedIdentifierValueMarshaller
/* 329:    */   {
/* 330:    */     private final ComponentType virtualIdComponent;
/* 331:    */     private final ComponentType mappedIdentifierType;
/* 332:    */     
/* 333:    */     private IncrediblySillyJpaMapsIdMappedIdentifierValueMarshaller(ComponentType virtualIdComponent, ComponentType mappedIdentifierType)
/* 334:    */     {
/* 335:454 */       this.virtualIdComponent = virtualIdComponent;
/* 336:455 */       this.mappedIdentifierType = mappedIdentifierType;
/* 337:    */     }
/* 338:    */     
/* 339:    */     public Object getIdentifier(Object entity, EntityMode entityMode, SessionImplementor session)
/* 340:    */     {
/* 341:459 */       Object id = this.mappedIdentifierType.instantiate(entityMode);
/* 342:460 */       Object[] propertyValues = this.virtualIdComponent.getPropertyValues(entity, entityMode);
/* 343:461 */       Type[] subTypes = this.virtualIdComponent.getSubtypes();
/* 344:462 */       Type[] copierSubTypes = this.mappedIdentifierType.getSubtypes();
/* 345:463 */       Iterable<PersistEventListener> persistEventListeners = AbstractEntityTuplizer.persistEventListeners(session);
/* 346:464 */       PersistenceContext persistenceContext = session.getPersistenceContext();
/* 347:465 */       int length = subTypes.length;
/* 348:466 */       for (int i = 0; i < length; i++)
/* 349:    */       {
/* 350:467 */         if (propertyValues[i] == null) {
/* 351:468 */           throw new HibernateException("No part of a composite identifier may be null");
/* 352:    */         }
/* 353:471 */         if ((subTypes[i].isAssociationType()) && (!copierSubTypes[i].isAssociationType()))
/* 354:    */         {
/* 355:473 */           if (session == null) {
/* 356:474 */             throw new AssertionError("Deprecated version of getIdentifier (no session) was used but session was required");
/* 357:    */           }
/* 358:    */           Object subId;
/* 359:    */           Object subId;
/* 360:479 */           if (HibernateProxy.class.isInstance(propertyValues[i]))
/* 361:    */           {
/* 362:480 */             subId = ((HibernateProxy)propertyValues[i]).getHibernateLazyInitializer().getIdentifier();
/* 363:    */           }
/* 364:    */           else
/* 365:    */           {
/* 366:483 */             EntityEntry pcEntry = session.getPersistenceContext().getEntry(propertyValues[i]);
/* 367:    */             Object subId;
/* 368:484 */             if (pcEntry != null)
/* 369:    */             {
/* 370:485 */               subId = pcEntry.getId();
/* 371:    */             }
/* 372:    */             else
/* 373:    */             {
/* 374:488 */               AbstractEntityTuplizer.LOG.debug("Performing implicit derived identity cascade");
/* 375:489 */               PersistEvent event = new PersistEvent(null, propertyValues[i], (EventSource)session);
/* 376:490 */               for (PersistEventListener listener : persistEventListeners) {
/* 377:491 */                 listener.onPersist(event);
/* 378:    */               }
/* 379:493 */               pcEntry = persistenceContext.getEntry(propertyValues[i]);
/* 380:494 */               if ((pcEntry == null) || (pcEntry.getId() == null)) {
/* 381:495 */                 throw new HibernateException("Unable to process implicit derived identity cascade");
/* 382:    */               }
/* 383:498 */               subId = pcEntry.getId();
/* 384:    */             }
/* 385:    */           }
/* 386:502 */           propertyValues[i] = subId;
/* 387:    */         }
/* 388:    */       }
/* 389:505 */       this.mappedIdentifierType.setPropertyValues(id, propertyValues, entityMode);
/* 390:506 */       return id;
/* 391:    */     }
/* 392:    */     
/* 393:    */     public void setIdentifier(Object entity, Serializable id, EntityMode entityMode, SessionImplementor session)
/* 394:    */     {
/* 395:510 */       Object[] extractedValues = this.mappedIdentifierType.getPropertyValues(id, entityMode);
/* 396:511 */       Object[] injectionValues = new Object[extractedValues.length];
/* 397:512 */       PersistenceContext persistenceContext = session.getPersistenceContext();
/* 398:513 */       for (int i = 0; i < this.virtualIdComponent.getSubtypes().length; i++)
/* 399:    */       {
/* 400:514 */         Type virtualPropertyType = this.virtualIdComponent.getSubtypes()[i];
/* 401:515 */         Type idClassPropertyType = this.mappedIdentifierType.getSubtypes()[i];
/* 402:516 */         if ((virtualPropertyType.isEntityType()) && (!idClassPropertyType.isEntityType()))
/* 403:    */         {
/* 404:517 */           if (session == null) {
/* 405:518 */             throw new AssertionError("Deprecated version of getIdentifier (no session) was used but session was required");
/* 406:    */           }
/* 407:522 */           String associatedEntityName = ((EntityType)virtualPropertyType).getAssociatedEntityName();
/* 408:523 */           EntityKey entityKey = session.generateEntityKey((Serializable)extractedValues[i], session.getFactory().getEntityPersister(associatedEntityName));
/* 409:    */           
/* 410:    */ 
/* 411:    */ 
/* 412:    */ 
/* 413:528 */           Object association = persistenceContext.getProxy(entityKey);
/* 414:529 */           if (association == null) {
/* 415:531 */             association = persistenceContext.getEntity(entityKey);
/* 416:    */           }
/* 417:533 */           injectionValues[i] = association;
/* 418:    */         }
/* 419:    */         else
/* 420:    */         {
/* 421:536 */           injectionValues[i] = extractedValues[i];
/* 422:    */         }
/* 423:    */       }
/* 424:539 */       this.virtualIdComponent.setPropertyValues(entity, injectionValues, entityMode);
/* 425:    */     }
/* 426:    */   }
/* 427:    */   
/* 428:    */   private static Iterable<PersistEventListener> persistEventListeners(SessionImplementor session)
/* 429:    */   {
/* 430:544 */     return ((EventListenerRegistry)session.getFactory().getServiceRegistry().getService(EventListenerRegistry.class)).getEventListenerGroup(EventType.PERSIST).listeners();
/* 431:    */   }
/* 432:    */   
/* 433:    */   public void resetIdentifier(Object entity, Serializable currentId, Object currentVersion)
/* 434:    */   {
/* 435:558 */     resetIdentifier(entity, currentId, currentVersion, null);
/* 436:    */   }
/* 437:    */   
/* 438:    */   public void resetIdentifier(Object entity, Serializable currentId, Object currentVersion, SessionImplementor session)
/* 439:    */   {
/* 440:569 */     if (!(this.entityMetamodel.getIdentifierProperty().getIdentifierGenerator() instanceof Assigned))
/* 441:    */     {
/* 442:573 */       Serializable result = this.entityMetamodel.getIdentifierProperty().getUnsavedValue().getDefaultValue(currentId);
/* 443:    */       
/* 444:    */ 
/* 445:576 */       setIdentifier(entity, result, session);
/* 446:    */       
/* 447:578 */       VersionProperty versionProperty = this.entityMetamodel.getVersionProperty();
/* 448:579 */       if (this.entityMetamodel.isVersioned()) {
/* 449:580 */         setPropertyValue(entity, this.entityMetamodel.getVersionPropertyIndex(), versionProperty.getUnsavedValue().getDefaultValue(currentVersion));
/* 450:    */       }
/* 451:    */     }
/* 452:    */   }
/* 453:    */   
/* 454:    */   public Object getVersion(Object entity)
/* 455:    */     throws HibernateException
/* 456:    */   {
/* 457:590 */     if (!this.entityMetamodel.isVersioned()) {
/* 458:590 */       return null;
/* 459:    */     }
/* 460:591 */     return this.getters[this.entityMetamodel.getVersionPropertyIndex()].get(entity);
/* 461:    */   }
/* 462:    */   
/* 463:    */   protected boolean shouldGetAllProperties(Object entity)
/* 464:    */   {
/* 465:595 */     return !hasUninitializedLazyProperties(entity);
/* 466:    */   }
/* 467:    */   
/* 468:    */   public Object[] getPropertyValues(Object entity)
/* 469:    */     throws HibernateException
/* 470:    */   {
/* 471:599 */     boolean getAll = shouldGetAllProperties(entity);
/* 472:600 */     int span = this.entityMetamodel.getPropertySpan();
/* 473:601 */     Object[] result = new Object[span];
/* 474:603 */     for (int j = 0; j < span; j++)
/* 475:    */     {
/* 476:604 */       StandardProperty property = this.entityMetamodel.getProperties()[j];
/* 477:605 */       if ((getAll) || (!property.isLazy())) {
/* 478:606 */         result[j] = this.getters[j].get(entity);
/* 479:    */       } else {
/* 480:609 */         result[j] = LazyPropertyInitializer.UNFETCHED_PROPERTY;
/* 481:    */       }
/* 482:    */     }
/* 483:612 */     return result;
/* 484:    */   }
/* 485:    */   
/* 486:    */   public Object[] getPropertyValuesToInsert(Object entity, Map mergeMap, SessionImplementor session)
/* 487:    */     throws HibernateException
/* 488:    */   {
/* 489:617 */     int span = this.entityMetamodel.getPropertySpan();
/* 490:618 */     Object[] result = new Object[span];
/* 491:620 */     for (int j = 0; j < span; j++) {
/* 492:621 */       result[j] = this.getters[j].getForInsert(entity, mergeMap, session);
/* 493:    */     }
/* 494:623 */     return result;
/* 495:    */   }
/* 496:    */   
/* 497:    */   public Object getPropertyValue(Object entity, int i)
/* 498:    */     throws HibernateException
/* 499:    */   {
/* 500:627 */     return this.getters[i].get(entity);
/* 501:    */   }
/* 502:    */   
/* 503:    */   public Object getPropertyValue(Object entity, String propertyPath)
/* 504:    */     throws HibernateException
/* 505:    */   {
/* 506:631 */     int loc = propertyPath.indexOf('.');
/* 507:632 */     String basePropertyName = loc > 0 ? propertyPath.substring(0, loc) : propertyPath;
/* 508:    */     
/* 509:    */ 
/* 510:    */ 
/* 511:636 */     Integer index = this.entityMetamodel.getPropertyIndexOrNull(basePropertyName);
/* 512:637 */     if (index == null)
/* 513:    */     {
/* 514:638 */       propertyPath = "_identifierMapper." + propertyPath;
/* 515:639 */       loc = propertyPath.indexOf('.');
/* 516:640 */       basePropertyName = loc > 0 ? propertyPath.substring(0, loc) : propertyPath;
/* 517:    */     }
/* 518:644 */     index = this.entityMetamodel.getPropertyIndexOrNull(basePropertyName);
/* 519:645 */     Object baseValue = getPropertyValue(entity, index.intValue());
/* 520:646 */     if (loc > 0)
/* 521:    */     {
/* 522:647 */       if (baseValue == null) {
/* 523:648 */         return null;
/* 524:    */       }
/* 525:650 */       return getComponentValue((ComponentType)this.entityMetamodel.getPropertyTypes()[index.intValue()], baseValue, propertyPath.substring(loc + 1));
/* 526:    */     }
/* 527:657 */     return baseValue;
/* 528:    */   }
/* 529:    */   
/* 530:    */   protected Object getComponentValue(ComponentType type, Object component, String propertyPath)
/* 531:    */   {
/* 532:670 */     int loc = propertyPath.indexOf('.');
/* 533:671 */     String basePropertyName = loc > 0 ? propertyPath.substring(0, loc) : propertyPath;
/* 534:    */     
/* 535:    */ 
/* 536:674 */     int index = findSubPropertyIndex(type, basePropertyName);
/* 537:675 */     Object baseValue = type.getPropertyValue(component, index, getEntityMode());
/* 538:676 */     if (loc > 0)
/* 539:    */     {
/* 540:677 */       if (baseValue == null) {
/* 541:678 */         return null;
/* 542:    */       }
/* 543:680 */       return getComponentValue((ComponentType)type.getSubtypes()[index], baseValue, propertyPath.substring(loc + 1));
/* 544:    */     }
/* 545:687 */     return baseValue;
/* 546:    */   }
/* 547:    */   
/* 548:    */   private int findSubPropertyIndex(ComponentType type, String subPropertyName)
/* 549:    */   {
/* 550:693 */     String[] propertyNames = type.getPropertyNames();
/* 551:694 */     for (int index = 0; index < propertyNames.length; index++) {
/* 552:695 */       if (subPropertyName.equals(propertyNames[index])) {
/* 553:696 */         return index;
/* 554:    */       }
/* 555:    */     }
/* 556:699 */     throw new MappingException("component property not found: " + subPropertyName);
/* 557:    */   }
/* 558:    */   
/* 559:    */   public void setPropertyValues(Object entity, Object[] values)
/* 560:    */     throws HibernateException
/* 561:    */   {
/* 562:703 */     boolean setAll = !this.entityMetamodel.hasLazyProperties();
/* 563:705 */     for (int j = 0; j < this.entityMetamodel.getPropertySpan(); j++) {
/* 564:706 */       if ((setAll) || (values[j] != LazyPropertyInitializer.UNFETCHED_PROPERTY)) {
/* 565:707 */         this.setters[j].set(entity, values[j], getFactory());
/* 566:    */       }
/* 567:    */     }
/* 568:    */   }
/* 569:    */   
/* 570:    */   public void setPropertyValue(Object entity, int i, Object value)
/* 571:    */     throws HibernateException
/* 572:    */   {
/* 573:713 */     this.setters[i].set(entity, value, getFactory());
/* 574:    */   }
/* 575:    */   
/* 576:    */   public void setPropertyValue(Object entity, String propertyName, Object value)
/* 577:    */     throws HibernateException
/* 578:    */   {
/* 579:717 */     this.setters[this.entityMetamodel.getPropertyIndex(propertyName)].set(entity, value, getFactory());
/* 580:    */   }
/* 581:    */   
/* 582:    */   public final Object instantiate(Serializable id)
/* 583:    */     throws HibernateException
/* 584:    */   {
/* 585:723 */     return instantiate(id, null);
/* 586:    */   }
/* 587:    */   
/* 588:    */   public final Object instantiate(Serializable id, SessionImplementor session)
/* 589:    */   {
/* 590:727 */     Object result = getInstantiator().instantiate(id);
/* 591:728 */     if (id != null) {
/* 592:729 */       setIdentifier(result, id, session);
/* 593:    */     }
/* 594:731 */     return result;
/* 595:    */   }
/* 596:    */   
/* 597:    */   public final Object instantiate()
/* 598:    */     throws HibernateException
/* 599:    */   {
/* 600:735 */     return instantiate(null, null);
/* 601:    */   }
/* 602:    */   
/* 603:    */   public void afterInitialize(Object entity, boolean lazyPropertiesAreUnfetched, SessionImplementor session) {}
/* 604:    */   
/* 605:    */   public boolean hasUninitializedLazyProperties(Object entity)
/* 606:    */   {
/* 607:742 */     return false;
/* 608:    */   }
/* 609:    */   
/* 610:    */   public final boolean isInstance(Object object)
/* 611:    */   {
/* 612:746 */     return getInstantiator().isInstance(object);
/* 613:    */   }
/* 614:    */   
/* 615:    */   public boolean hasProxy()
/* 616:    */   {
/* 617:750 */     return this.entityMetamodel.isLazy();
/* 618:    */   }
/* 619:    */   
/* 620:    */   public final Object createProxy(Serializable id, SessionImplementor session)
/* 621:    */     throws HibernateException
/* 622:    */   {
/* 623:755 */     return getProxyFactory().getProxy(id, session);
/* 624:    */   }
/* 625:    */   
/* 626:    */   public boolean isLifecycleImplementor()
/* 627:    */   {
/* 628:759 */     return false;
/* 629:    */   }
/* 630:    */   
/* 631:    */   protected final EntityMetamodel getEntityMetamodel()
/* 632:    */   {
/* 633:763 */     return this.entityMetamodel;
/* 634:    */   }
/* 635:    */   
/* 636:    */   protected final SessionFactoryImplementor getFactory()
/* 637:    */   {
/* 638:767 */     return this.entityMetamodel.getSessionFactory();
/* 639:    */   }
/* 640:    */   
/* 641:    */   protected final Instantiator getInstantiator()
/* 642:    */   {
/* 643:771 */     return this.instantiator;
/* 644:    */   }
/* 645:    */   
/* 646:    */   protected final ProxyFactory getProxyFactory()
/* 647:    */   {
/* 648:775 */     return this.proxyFactory;
/* 649:    */   }
/* 650:    */   
/* 651:    */   public String toString()
/* 652:    */   {
/* 653:780 */     return getClass().getName() + '(' + getEntityMetamodel().getName() + ')';
/* 654:    */   }
/* 655:    */   
/* 656:    */   public Getter getIdentifierGetter()
/* 657:    */   {
/* 658:784 */     return this.idGetter;
/* 659:    */   }
/* 660:    */   
/* 661:    */   public Getter getVersionGetter()
/* 662:    */   {
/* 663:788 */     if (getEntityMetamodel().isVersioned()) {
/* 664:789 */       return getGetter(getEntityMetamodel().getVersionPropertyIndex());
/* 665:    */     }
/* 666:791 */     return null;
/* 667:    */   }
/* 668:    */   
/* 669:    */   public Getter getGetter(int i)
/* 670:    */   {
/* 671:795 */     return this.getters[i];
/* 672:    */   }
/* 673:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tuple.entity.AbstractEntityTuplizer
 * JD-Core Version:    0.7.0.1
 */