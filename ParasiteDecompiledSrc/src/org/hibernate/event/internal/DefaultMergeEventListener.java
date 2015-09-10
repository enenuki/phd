/*   1:    */ package org.hibernate.event.internal;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.HashSet;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.Map;
/*   7:    */ import java.util.Map.Entry;
/*   8:    */ import java.util.Set;
/*   9:    */ import org.hibernate.AssertionFailure;
/*  10:    */ import org.hibernate.HibernateException;
/*  11:    */ import org.hibernate.ObjectDeletedException;
/*  12:    */ import org.hibernate.PropertyValueException;
/*  13:    */ import org.hibernate.StaleObjectStateException;
/*  14:    */ import org.hibernate.TransientObjectException;
/*  15:    */ import org.hibernate.WrongClassException;
/*  16:    */ import org.hibernate.bytecode.instrumentation.internal.FieldInterceptionHelper;
/*  17:    */ import org.hibernate.bytecode.instrumentation.spi.FieldInterceptor;
/*  18:    */ import org.hibernate.cfg.Settings;
/*  19:    */ import org.hibernate.engine.internal.Cascade;
/*  20:    */ import org.hibernate.engine.spi.CascadingAction;
/*  21:    */ import org.hibernate.engine.spi.EntityEntry;
/*  22:    */ import org.hibernate.engine.spi.EntityKey;
/*  23:    */ import org.hibernate.engine.spi.PersistenceContext;
/*  24:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  25:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  26:    */ import org.hibernate.engine.spi.Status;
/*  27:    */ import org.hibernate.event.spi.EventSource;
/*  28:    */ import org.hibernate.event.spi.MergeEvent;
/*  29:    */ import org.hibernate.event.spi.MergeEventListener;
/*  30:    */ import org.hibernate.internal.CoreMessageLogger;
/*  31:    */ import org.hibernate.persister.entity.EntityPersister;
/*  32:    */ import org.hibernate.proxy.HibernateProxy;
/*  33:    */ import org.hibernate.proxy.LazyInitializer;
/*  34:    */ import org.hibernate.service.instrumentation.spi.InstrumentationService;
/*  35:    */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  36:    */ import org.hibernate.stat.Statistics;
/*  37:    */ import org.hibernate.stat.spi.StatisticsImplementor;
/*  38:    */ import org.hibernate.type.ForeignKeyDirection;
/*  39:    */ import org.hibernate.type.Type;
/*  40:    */ import org.hibernate.type.TypeHelper;
/*  41:    */ import org.hibernate.type.VersionType;
/*  42:    */ import org.jboss.logging.Logger;
/*  43:    */ 
/*  44:    */ public class DefaultMergeEventListener
/*  45:    */   extends AbstractSaveEventListener
/*  46:    */   implements MergeEventListener
/*  47:    */ {
/*  48: 70 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, DefaultMergeEventListener.class.getName());
/*  49:    */   
/*  50:    */   protected Map getMergeMap(Object anything)
/*  51:    */   {
/*  52: 75 */     return ((EventCache)anything).invertMap();
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void onMerge(MergeEvent event)
/*  56:    */     throws HibernateException
/*  57:    */   {
/*  58: 85 */     EventCache copyCache = new EventCache();
/*  59: 86 */     onMerge(event, copyCache);
/*  60:    */     
/*  61:    */ 
/*  62:    */ 
/*  63:    */ 
/*  64:    */ 
/*  65: 92 */     Map transientCopyCache = getTransientCopyCache(event, copyCache);
/*  66: 93 */     if (transientCopyCache.size() > 0)
/*  67:    */     {
/*  68: 94 */       retryMergeTransientEntities(event, transientCopyCache, copyCache, true);
/*  69:    */       
/*  70: 96 */       transientCopyCache = getTransientCopyCache(event, copyCache);
/*  71: 97 */       if (transientCopyCache.size() > 0)
/*  72:    */       {
/*  73: 98 */         Set transientEntityNames = new HashSet();
/*  74: 99 */         for (Iterator it = transientCopyCache.entrySet().iterator(); it.hasNext();)
/*  75:    */         {
/*  76:100 */           Object transientEntity = ((Map.Entry)it.next()).getKey();
/*  77:101 */           String transientEntityName = event.getSession().guessEntityName(transientEntity);
/*  78:102 */           transientEntityNames.add(transientEntityName);
/*  79:103 */           LOG.tracev("Transient instance could not be processed by merge when checking nullability: {0} [{1}]", transientEntityName, transientEntity);
/*  80:    */         }
/*  81:107 */         if (isNullabilityCheckedGlobal(event.getSession())) {
/*  82:108 */           throw new TransientObjectException("one or more objects is an unsaved transient instance - save transient instance(s) before merging: " + transientEntityNames);
/*  83:    */         }
/*  84:111 */         LOG.trace("Retry saving transient instances without checking nullability");
/*  85:    */         
/*  86:113 */         retryMergeTransientEntities(event, transientCopyCache, copyCache, false);
/*  87:    */       }
/*  88:    */     }
/*  89:116 */     copyCache.clear();
/*  90:117 */     copyCache = null;
/*  91:    */   }
/*  92:    */   
/*  93:    */   protected EventCache getTransientCopyCache(MergeEvent event, EventCache copyCache)
/*  94:    */   {
/*  95:121 */     EventCache transientCopyCache = new EventCache();
/*  96:122 */     for (Iterator it = copyCache.entrySet().iterator(); it.hasNext();)
/*  97:    */     {
/*  98:123 */       Map.Entry mapEntry = (Map.Entry)it.next();
/*  99:124 */       Object entity = mapEntry.getKey();
/* 100:125 */       Object copy = mapEntry.getValue();
/* 101:126 */       if ((copy instanceof HibernateProxy)) {
/* 102:127 */         copy = ((HibernateProxy)copy).getHibernateLazyInitializer().getImplementation();
/* 103:    */       }
/* 104:129 */       EntityEntry copyEntry = event.getSession().getPersistenceContext().getEntry(copy);
/* 105:130 */       if (copyEntry == null)
/* 106:    */       {
/* 107:132 */         if (LOG.isTraceEnabled()) {
/* 108:133 */           LOG.tracev("Transient instance could not be processed by merge: {0} [{1}]", event.getSession().guessEntityName(copy), entity);
/* 109:    */         }
/* 110:142 */         if (isNullabilityCheckedGlobal(event.getSession())) {
/* 111:143 */           throw new TransientObjectException("object is an unsaved transient instance - save the transient instance before merging: " + event.getSession().guessEntityName(copy));
/* 112:    */         }
/* 113:    */       }
/* 114:149 */       else if (copyEntry.getStatus() == Status.SAVING)
/* 115:    */       {
/* 116:150 */         transientCopyCache.put(entity, copy, copyCache.isOperatedOn(entity));
/* 117:    */       }
/* 118:152 */       else if ((copyEntry.getStatus() != Status.MANAGED) && (copyEntry.getStatus() != Status.READ_ONLY))
/* 119:    */       {
/* 120:153 */         throw new AssertionFailure("Merged entity does not have status set to MANAGED or READ_ONLY; " + copy + " status=" + copyEntry.getStatus());
/* 121:    */       }
/* 122:    */     }
/* 123:156 */     return transientCopyCache;
/* 124:    */   }
/* 125:    */   
/* 126:    */   protected void retryMergeTransientEntities(MergeEvent event, Map transientCopyCache, EventCache copyCache, boolean isNullabilityChecked)
/* 127:    */   {
/* 128:170 */     for (Iterator it = transientCopyCache.entrySet().iterator(); it.hasNext();)
/* 129:    */     {
/* 130:171 */       Map.Entry mapEntry = (Map.Entry)it.next();
/* 131:172 */       Object entity = mapEntry.getKey();
/* 132:173 */       Object copy = transientCopyCache.get(entity);
/* 133:174 */       EntityEntry copyEntry = event.getSession().getPersistenceContext().getEntry(copy);
/* 134:175 */       mergeTransientEntity(entity, copyEntry.getEntityName(), entity == event.getEntity() ? event.getRequestedId() : copyEntry.getId(), event.getSession(), copyCache, isNullabilityChecked);
/* 135:    */     }
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void onMerge(MergeEvent event, Map copiedAlready)
/* 139:    */     throws HibernateException
/* 140:    */   {
/* 141:194 */     EventCache copyCache = (EventCache)copiedAlready;
/* 142:195 */     EventSource source = event.getSession();
/* 143:196 */     Object original = event.getOriginal();
/* 144:198 */     if (original != null)
/* 145:    */     {
/* 146:    */       Object entity;
/* 147:    */       Object entity;
/* 148:201 */       if ((original instanceof HibernateProxy))
/* 149:    */       {
/* 150:202 */         LazyInitializer li = ((HibernateProxy)original).getHibernateLazyInitializer();
/* 151:203 */         if (li.isUninitialized())
/* 152:    */         {
/* 153:204 */           LOG.trace("Ignoring uninitialized proxy");
/* 154:205 */           event.setResult(source.load(li.getEntityName(), li.getIdentifier()));
/* 155:206 */           return;
/* 156:    */         }
/* 157:209 */         entity = li.getImplementation();
/* 158:    */       }
/* 159:    */       else
/* 160:    */       {
/* 161:213 */         entity = original;
/* 162:    */       }
/* 163:216 */       if ((copyCache.containsKey(entity)) && (copyCache.isOperatedOn(entity)))
/* 164:    */       {
/* 165:218 */         LOG.trace("Already in merge process");
/* 166:219 */         event.setResult(entity);
/* 167:    */       }
/* 168:    */       else
/* 169:    */       {
/* 170:222 */         if (copyCache.containsKey(entity))
/* 171:    */         {
/* 172:223 */           LOG.trace("Already in copyCache; setting in merge process");
/* 173:224 */           copyCache.setOperatedOn(entity, true);
/* 174:    */         }
/* 175:226 */         event.setEntity(entity);
/* 176:227 */         AbstractSaveEventListener.EntityState entityState = null;
/* 177:    */         
/* 178:    */ 
/* 179:    */ 
/* 180:231 */         EntityEntry entry = source.getPersistenceContext().getEntry(entity);
/* 181:232 */         if (entry == null)
/* 182:    */         {
/* 183:233 */           EntityPersister persister = source.getEntityPersister(event.getEntityName(), entity);
/* 184:234 */           Serializable id = persister.getIdentifier(entity, source);
/* 185:235 */           if (id != null)
/* 186:    */           {
/* 187:236 */             EntityKey key = source.generateEntityKey(id, persister);
/* 188:237 */             Object managedEntity = source.getPersistenceContext().getEntity(key);
/* 189:238 */             entry = source.getPersistenceContext().getEntry(managedEntity);
/* 190:239 */             if (entry != null) {
/* 191:245 */               entityState = AbstractSaveEventListener.EntityState.DETACHED;
/* 192:    */             }
/* 193:    */           }
/* 194:    */         }
/* 195:250 */         if (entityState == null) {
/* 196:251 */           entityState = getEntityState(entity, event.getEntityName(), entry, source);
/* 197:    */         }
/* 198:254 */         switch (1.$SwitchMap$org$hibernate$event$internal$AbstractSaveEventListener$EntityState[entityState.ordinal()])
/* 199:    */         {
/* 200:    */         case 1: 
/* 201:256 */           entityIsDetached(event, copyCache);
/* 202:257 */           break;
/* 203:    */         case 2: 
/* 204:259 */           entityIsTransient(event, copyCache);
/* 205:260 */           break;
/* 206:    */         case 3: 
/* 207:262 */           entityIsPersistent(event, copyCache);
/* 208:263 */           break;
/* 209:    */         default: 
/* 210:265 */           throw new ObjectDeletedException("deleted instance passed to merge", null, getLoggableName(event.getEntityName(), entity));
/* 211:    */         }
/* 212:    */       }
/* 213:    */     }
/* 214:    */   }
/* 215:    */   
/* 216:    */   protected void entityIsPersistent(MergeEvent event, Map copyCache)
/* 217:    */   {
/* 218:278 */     LOG.trace("Ignoring persistent instance");
/* 219:    */     
/* 220:    */ 
/* 221:    */ 
/* 222:282 */     Object entity = event.getEntity();
/* 223:283 */     EventSource source = event.getSession();
/* 224:284 */     EntityPersister persister = source.getEntityPersister(event.getEntityName(), entity);
/* 225:    */     
/* 226:286 */     ((EventCache)copyCache).put(entity, entity, true);
/* 227:    */     
/* 228:288 */     cascadeOnMerge(source, persister, entity, copyCache);
/* 229:289 */     copyValues(persister, entity, entity, source, copyCache);
/* 230:    */     
/* 231:291 */     event.setResult(entity);
/* 232:    */   }
/* 233:    */   
/* 234:    */   protected void entityIsTransient(MergeEvent event, Map copyCache)
/* 235:    */   {
/* 236:296 */     LOG.trace("Merging transient instance");
/* 237:    */     
/* 238:298 */     Object entity = event.getEntity();
/* 239:299 */     EventSource source = event.getSession();
/* 240:    */     
/* 241:301 */     EntityPersister persister = source.getEntityPersister(event.getEntityName(), entity);
/* 242:302 */     String entityName = persister.getEntityName();
/* 243:    */     
/* 244:304 */     event.setResult(mergeTransientEntity(entity, entityName, event.getRequestedId(), source, copyCache, true));
/* 245:    */   }
/* 246:    */   
/* 247:    */   protected Object mergeTransientEntity(Object entity, String entityName, Serializable requestedId, EventSource source, Map copyCache)
/* 248:    */   {
/* 249:308 */     return mergeTransientEntity(entity, entityName, requestedId, source, copyCache, true);
/* 250:    */   }
/* 251:    */   
/* 252:    */   private Object mergeTransientEntity(Object entity, String entityName, Serializable requestedId, EventSource source, Map copyCache, boolean isNullabilityChecked)
/* 253:    */   {
/* 254:319 */     LOG.trace("Merging transient instance");
/* 255:    */     
/* 256:321 */     EntityPersister persister = source.getEntityPersister(entityName, entity);
/* 257:    */     
/* 258:323 */     Serializable id = persister.hasIdentifierProperty() ? persister.getIdentifier(entity, source) : null;
/* 259:326 */     if (copyCache.containsKey(entity)) {
/* 260:327 */       persister.setIdentifier(copyCache.get(entity), id, source);
/* 261:    */     } else {
/* 262:330 */       ((EventCache)copyCache).put(entity, source.instantiate(persister, id), true);
/* 263:    */     }
/* 264:332 */     Object copy = copyCache.get(entity);
/* 265:    */     
/* 266:    */ 
/* 267:    */ 
/* 268:    */ 
/* 269:337 */     super.cascadeBeforeSave(source, persister, entity, copyCache);
/* 270:338 */     copyValues(persister, entity, copy, source, copyCache, ForeignKeyDirection.FOREIGN_KEY_FROM_PARENT);
/* 271:    */     try
/* 272:    */     {
/* 273:342 */       saveTransientEntity(copy, entityName, requestedId, source, copyCache, isNullabilityChecked);
/* 274:    */     }
/* 275:    */     catch (PropertyValueException ex)
/* 276:    */     {
/* 277:345 */       String propertyName = ex.getPropertyName();
/* 278:346 */       Object propertyFromCopy = persister.getPropertyValue(copy, propertyName);
/* 279:347 */       Object propertyFromEntity = persister.getPropertyValue(entity, propertyName);
/* 280:348 */       Type propertyType = persister.getPropertyType(propertyName);
/* 281:349 */       EntityEntry copyEntry = source.getPersistenceContext().getEntry(copy);
/* 282:350 */       if ((propertyFromCopy == null) || (propertyFromEntity == null) || (!propertyType.isEntityType()) || (!copyCache.containsKey(propertyFromEntity)))
/* 283:    */       {
/* 284:354 */         if (LOG.isTraceEnabled())
/* 285:    */         {
/* 286:355 */           LOG.trace("Property '" + copyEntry.getEntityName() + "." + propertyName + "' in copy is " + (propertyFromCopy == null ? "null" : propertyFromCopy));
/* 287:    */           
/* 288:357 */           LOG.trace("Property '" + copyEntry.getEntityName() + "." + propertyName + "' in original is " + (propertyFromCopy == null ? "null" : propertyFromCopy));
/* 289:    */           
/* 290:359 */           LOG.trace("Property '" + copyEntry.getEntityName() + "." + propertyName + "' is" + (propertyType.isEntityType() ? "" : " not") + " an entity type");
/* 291:361 */           if ((propertyFromEntity != null) && (!copyCache.containsKey(propertyFromEntity))) {
/* 292:362 */             LOG.tracef("Property '%s.%s' is not in copy cache", copyEntry.getEntityName(), propertyName);
/* 293:    */           }
/* 294:    */         }
/* 295:369 */         if (isNullabilityCheckedGlobal(source)) {
/* 296:370 */           throw ex;
/* 297:    */         }
/* 298:375 */         saveTransientEntity(copy, entityName, requestedId, source, copyCache, false);
/* 299:    */       }
/* 300:378 */       if ((LOG.isTraceEnabled()) && (propertyFromEntity != null)) {
/* 301:379 */         if (((EventCache)copyCache).isOperatedOn(propertyFromEntity)) {
/* 302:379 */           LOG.trace("Property '" + copyEntry.getEntityName() + "." + propertyName + "' from original entity is in copyCache and is in the process of being merged; " + propertyName + " =[" + propertyFromEntity + "]");
/* 303:    */         } else {
/* 304:386 */           LOG.trace("Property '" + copyEntry.getEntityName() + "." + propertyName + "' from original entity is in copyCache and is not in the process of being merged; " + propertyName + " =[" + propertyFromEntity + "]");
/* 305:    */         }
/* 306:    */       }
/* 307:    */     }
/* 308:395 */     super.cascadeAfterSave(source, persister, entity, copyCache);
/* 309:396 */     copyValues(persister, entity, copy, source, copyCache, ForeignKeyDirection.FOREIGN_KEY_TO_PARENT);
/* 310:    */     
/* 311:398 */     return copy;
/* 312:    */   }
/* 313:    */   
/* 314:    */   private boolean isNullabilityCheckedGlobal(EventSource source)
/* 315:    */   {
/* 316:403 */     return source.getFactory().getSettings().isCheckNullability();
/* 317:    */   }
/* 318:    */   
/* 319:    */   private void saveTransientEntity(Object entity, String entityName, Serializable requestedId, EventSource source, Map copyCache, boolean isNullabilityChecked)
/* 320:    */   {
/* 321:414 */     boolean isNullabilityCheckedOrig = source.getFactory().getSettings().isCheckNullability();
/* 322:    */     try
/* 323:    */     {
/* 324:417 */       source.getFactory().getSettings().setCheckNullability(isNullabilityChecked);
/* 325:421 */       if (requestedId == null) {
/* 326:422 */         saveWithGeneratedId(entity, entityName, copyCache, source, false);
/* 327:    */       } else {
/* 328:425 */         saveWithRequestedId(entity, requestedId, entityName, copyCache, source);
/* 329:    */       }
/* 330:    */     }
/* 331:    */     finally
/* 332:    */     {
/* 333:429 */       source.getFactory().getSettings().setCheckNullability(isNullabilityCheckedOrig);
/* 334:    */     }
/* 335:    */   }
/* 336:    */   
/* 337:    */   protected void entityIsDetached(MergeEvent event, Map copyCache)
/* 338:    */   {
/* 339:434 */     LOG.trace("Merging detached instance");
/* 340:    */     
/* 341:436 */     Object entity = event.getEntity();
/* 342:437 */     EventSource source = event.getSession();
/* 343:    */     
/* 344:439 */     EntityPersister persister = source.getEntityPersister(event.getEntityName(), entity);
/* 345:440 */     String entityName = persister.getEntityName();
/* 346:    */     
/* 347:442 */     Serializable id = event.getRequestedId();
/* 348:443 */     if (id == null)
/* 349:    */     {
/* 350:444 */       id = persister.getIdentifier(entity, source);
/* 351:    */     }
/* 352:    */     else
/* 353:    */     {
/* 354:448 */       Serializable entityId = persister.getIdentifier(entity, source);
/* 355:449 */       if (!persister.getIdentifierType().isEqual(id, entityId, source.getFactory())) {
/* 356:450 */         throw new HibernateException("merge requested with id not matching id of passed entity");
/* 357:    */       }
/* 358:    */     }
/* 359:454 */     String previousFetchProfile = source.getFetchProfile();
/* 360:455 */     source.setFetchProfile("merge");
/* 361:    */     
/* 362:    */ 
/* 363:458 */     Serializable clonedIdentifier = (Serializable)persister.getIdentifierType().deepCopy(id, source.getFactory());
/* 364:    */     
/* 365:460 */     Object result = source.get(entityName, clonedIdentifier);
/* 366:461 */     source.setFetchProfile(previousFetchProfile);
/* 367:463 */     if (result == null)
/* 368:    */     {
/* 369:471 */       entityIsTransient(event, copyCache);
/* 370:    */     }
/* 371:    */     else
/* 372:    */     {
/* 373:474 */       ((EventCache)copyCache).put(entity, result, true);
/* 374:    */       
/* 375:476 */       Object target = source.getPersistenceContext().unproxy(result);
/* 376:477 */       if (target == entity) {
/* 377:478 */         throw new AssertionFailure("entity was not detached");
/* 378:    */       }
/* 379:480 */       if (!source.getEntityName(target).equals(entityName)) {
/* 380:481 */         throw new WrongClassException("class of the given object did not match class of persistent copy", event.getRequestedId(), entityName);
/* 381:    */       }
/* 382:487 */       if (isVersionChanged(entity, source, persister, target))
/* 383:    */       {
/* 384:488 */         if (source.getFactory().getStatistics().isStatisticsEnabled()) {
/* 385:489 */           source.getFactory().getStatisticsImplementor().optimisticFailure(entityName);
/* 386:    */         }
/* 387:492 */         throw new StaleObjectStateException(entityName, id);
/* 388:    */       }
/* 389:497 */       cascadeOnMerge(source, persister, entity, copyCache);
/* 390:498 */       copyValues(persister, entity, target, source, copyCache);
/* 391:    */       
/* 392:    */ 
/* 393:501 */       markInterceptorDirty(entity, target, persister.getFactory());
/* 394:    */       
/* 395:503 */       event.setResult(result);
/* 396:    */     }
/* 397:    */   }
/* 398:    */   
/* 399:    */   private void markInterceptorDirty(Object entity, Object target, SessionFactoryImplementor factory)
/* 400:    */   {
/* 401:509 */     InstrumentationService instrumentationService = (InstrumentationService)factory.getServiceRegistry().getService(InstrumentationService.class);
/* 402:512 */     if (instrumentationService.isInstrumented(entity))
/* 403:    */     {
/* 404:513 */       FieldInterceptor interceptor = FieldInterceptionHelper.extractFieldInterceptor(target);
/* 405:514 */       if (interceptor != null) {
/* 406:515 */         interceptor.dirty();
/* 407:    */       }
/* 408:    */     }
/* 409:    */   }
/* 410:    */   
/* 411:    */   private boolean isVersionChanged(Object entity, EventSource source, EntityPersister persister, Object target)
/* 412:    */   {
/* 413:521 */     if (!persister.isVersioned()) {
/* 414:522 */       return false;
/* 415:    */     }
/* 416:534 */     boolean changed = !persister.getVersionType().isSame(persister.getVersion(target), persister.getVersion(entity));
/* 417:    */     
/* 418:    */ 
/* 419:    */ 
/* 420:    */ 
/* 421:    */ 
/* 422:    */ 
/* 423:541 */     return (changed) && (existsInDatabase(target, source, persister));
/* 424:    */   }
/* 425:    */   
/* 426:    */   private boolean existsInDatabase(Object entity, EventSource source, EntityPersister persister)
/* 427:    */   {
/* 428:545 */     EntityEntry entry = source.getPersistenceContext().getEntry(entity);
/* 429:546 */     if (entry == null)
/* 430:    */     {
/* 431:547 */       Serializable id = persister.getIdentifier(entity, source);
/* 432:548 */       if (id != null)
/* 433:    */       {
/* 434:549 */         EntityKey key = source.generateEntityKey(id, persister);
/* 435:550 */         Object managedEntity = source.getPersistenceContext().getEntity(key);
/* 436:551 */         entry = source.getPersistenceContext().getEntry(managedEntity);
/* 437:    */       }
/* 438:    */     }
/* 439:555 */     return (entry != null) && (entry.isExistsInDatabase());
/* 440:    */   }
/* 441:    */   
/* 442:    */   protected void copyValues(EntityPersister persister, Object entity, Object target, SessionImplementor source, Map copyCache)
/* 443:    */   {
/* 444:564 */     Object[] copiedValues = TypeHelper.replace(persister.getPropertyValues(entity), persister.getPropertyValues(target), persister.getPropertyTypes(), source, target, copyCache);
/* 445:    */     
/* 446:    */ 
/* 447:    */ 
/* 448:    */ 
/* 449:    */ 
/* 450:    */ 
/* 451:    */ 
/* 452:    */ 
/* 453:573 */     persister.setPropertyValues(target, copiedValues);
/* 454:    */   }
/* 455:    */   
/* 456:    */   protected void copyValues(EntityPersister persister, Object entity, Object target, SessionImplementor source, Map copyCache, ForeignKeyDirection foreignKeyDirection)
/* 457:    */   {
/* 458:    */     Object[] copiedValues;
/* 459:    */     Object[] copiedValues;
/* 460:586 */     if (foreignKeyDirection == ForeignKeyDirection.FOREIGN_KEY_TO_PARENT) {
/* 461:590 */       copiedValues = TypeHelper.replaceAssociations(persister.getPropertyValues(entity), persister.getPropertyValues(target), persister.getPropertyTypes(), source, target, copyCache, foreignKeyDirection);
/* 462:    */     } else {
/* 463:601 */       copiedValues = TypeHelper.replace(persister.getPropertyValues(entity), persister.getPropertyValues(target), persister.getPropertyTypes(), source, target, copyCache, foreignKeyDirection);
/* 464:    */     }
/* 465:612 */     persister.setPropertyValues(target, copiedValues);
/* 466:    */   }
/* 467:    */   
/* 468:    */   protected void cascadeOnMerge(EventSource source, EntityPersister persister, Object entity, Map copyCache)
/* 469:    */   {
/* 470:629 */     source.getPersistenceContext().incrementCascadeLevel();
/* 471:    */     try
/* 472:    */     {
/* 473:631 */       new Cascade(getCascadeAction(), 0, source).cascade(persister, entity, copyCache);
/* 474:    */     }
/* 475:    */     finally
/* 476:    */     {
/* 477:635 */       source.getPersistenceContext().decrementCascadeLevel();
/* 478:    */     }
/* 479:    */   }
/* 480:    */   
/* 481:    */   protected CascadingAction getCascadeAction()
/* 482:    */   {
/* 483:642 */     return CascadingAction.MERGE;
/* 484:    */   }
/* 485:    */   
/* 486:    */   protected Boolean getAssumedUnsaved()
/* 487:    */   {
/* 488:647 */     return Boolean.FALSE;
/* 489:    */   }
/* 490:    */   
/* 491:    */   protected void cascadeAfterSave(EventSource source, EntityPersister persister, Object entity, Object anything)
/* 492:    */     throws HibernateException
/* 493:    */   {}
/* 494:    */   
/* 495:    */   protected void cascadeBeforeSave(EventSource source, EntityPersister persister, Object entity, Object anything)
/* 496:    */     throws HibernateException
/* 497:    */   {}
/* 498:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.internal.DefaultMergeEventListener
 * JD-Core Version:    0.7.0.1
 */