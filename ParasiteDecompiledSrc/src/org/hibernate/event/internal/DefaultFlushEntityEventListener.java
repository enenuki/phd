/*   1:    */ package org.hibernate.event.internal;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.hibernate.AssertionFailure;
/*   5:    */ import org.hibernate.HibernateException;
/*   6:    */ import org.hibernate.Interceptor;
/*   7:    */ import org.hibernate.StaleObjectStateException;
/*   8:    */ import org.hibernate.action.internal.DelayedPostInsertIdentifier;
/*   9:    */ import org.hibernate.action.internal.EntityUpdateAction;
/*  10:    */ import org.hibernate.bytecode.instrumentation.internal.FieldInterceptionHelper;
/*  11:    */ import org.hibernate.engine.internal.Nullability;
/*  12:    */ import org.hibernate.engine.internal.Versioning;
/*  13:    */ import org.hibernate.engine.spi.ActionQueue;
/*  14:    */ import org.hibernate.engine.spi.EntityEntry;
/*  15:    */ import org.hibernate.engine.spi.EntityKey;
/*  16:    */ import org.hibernate.engine.spi.PersistenceContext;
/*  17:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  18:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  19:    */ import org.hibernate.engine.spi.Status;
/*  20:    */ import org.hibernate.event.spi.EventSource;
/*  21:    */ import org.hibernate.event.spi.FlushEntityEvent;
/*  22:    */ import org.hibernate.event.spi.FlushEntityEventListener;
/*  23:    */ import org.hibernate.internal.CoreMessageLogger;
/*  24:    */ import org.hibernate.internal.util.collections.ArrayHelper;
/*  25:    */ import org.hibernate.persister.entity.EntityPersister;
/*  26:    */ import org.hibernate.pretty.MessageHelper;
/*  27:    */ import org.hibernate.service.instrumentation.spi.InstrumentationService;
/*  28:    */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  29:    */ import org.hibernate.stat.Statistics;
/*  30:    */ import org.hibernate.stat.spi.StatisticsImplementor;
/*  31:    */ import org.hibernate.type.Type;
/*  32:    */ import org.jboss.logging.Logger;
/*  33:    */ 
/*  34:    */ public class DefaultFlushEntityEventListener
/*  35:    */   implements FlushEntityEventListener
/*  36:    */ {
/*  37: 59 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, DefaultFlushEntityEventListener.class.getName());
/*  38:    */   
/*  39:    */   public void checkId(Object object, EntityPersister persister, Serializable id, SessionImplementor session)
/*  40:    */     throws HibernateException
/*  41:    */   {
/*  42: 68 */     if ((id != null) && ((id instanceof DelayedPostInsertIdentifier))) {
/*  43: 71 */       return;
/*  44:    */     }
/*  45: 74 */     if (persister.canExtractIdOutOfEntity())
/*  46:    */     {
/*  47: 76 */       Serializable oid = persister.getIdentifier(object, session);
/*  48: 77 */       if (id == null) {
/*  49: 78 */         throw new AssertionFailure("null id in " + persister.getEntityName() + " entry (don't flush the Session after an exception occurs)");
/*  50:    */       }
/*  51: 80 */       if (!persister.getIdentifierType().isEqual(id, oid, session.getFactory())) {
/*  52: 81 */         throw new HibernateException("identifier of an instance of " + persister.getEntityName() + " was altered from " + id + " to " + oid);
/*  53:    */       }
/*  54:    */     }
/*  55:    */   }
/*  56:    */   
/*  57:    */   private void checkNaturalId(EntityPersister persister, EntityEntry entry, Object[] current, Object[] loaded, SessionImplementor session)
/*  58:    */   {
/*  59: 98 */     if ((persister.hasNaturalIdentifier()) && (entry.getStatus() != Status.READ_ONLY))
/*  60:    */     {
/*  61: 99 */       Object[] snapshot = null;
/*  62:100 */       Type[] types = persister.getPropertyTypes();
/*  63:101 */       int[] props = persister.getNaturalIdentifierProperties();
/*  64:102 */       boolean[] updateable = persister.getPropertyUpdateability();
/*  65:103 */       for (int i = 0; i < props.length; i++)
/*  66:    */       {
/*  67:104 */         int prop = props[i];
/*  68:105 */         if (updateable[prop] == 0)
/*  69:    */         {
/*  70:    */           Object loadedVal;
/*  71:    */           Object loadedVal;
/*  72:107 */           if (loaded == null)
/*  73:    */           {
/*  74:108 */             if (snapshot == null) {
/*  75:109 */               snapshot = session.getPersistenceContext().getNaturalIdSnapshot(entry.getId(), persister);
/*  76:    */             }
/*  77:111 */             loadedVal = snapshot[i];
/*  78:    */           }
/*  79:    */           else
/*  80:    */           {
/*  81:113 */             loadedVal = loaded[prop];
/*  82:    */           }
/*  83:115 */           if (!types[prop].isEqual(current[prop], loadedVal)) {
/*  84:116 */             throw new HibernateException("immutable natural identifier of an instance of " + persister.getEntityName() + " was altered");
/*  85:    */           }
/*  86:    */         }
/*  87:    */       }
/*  88:    */     }
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void onFlushEntity(FlushEntityEvent event)
/*  92:    */     throws HibernateException
/*  93:    */   {
/*  94:132 */     Object entity = event.getEntity();
/*  95:133 */     EntityEntry entry = event.getEntityEntry();
/*  96:134 */     EventSource session = event.getSession();
/*  97:135 */     EntityPersister persister = entry.getPersister();
/*  98:136 */     Status status = entry.getStatus();
/*  99:137 */     Type[] types = persister.getPropertyTypes();
/* 100:    */     
/* 101:139 */     boolean mightBeDirty = entry.requiresDirtyCheck(entity);
/* 102:    */     
/* 103:141 */     Object[] values = getValues(entity, entry, mightBeDirty, session);
/* 104:    */     
/* 105:143 */     event.setPropertyValues(values);
/* 106:    */     
/* 107:    */ 
/* 108:146 */     boolean substitute = wrapCollections(session, persister, types, values);
/* 109:148 */     if (isUpdateNecessary(event, mightBeDirty)) {
/* 110:149 */       substitute = (scheduleUpdate(event)) || (substitute);
/* 111:    */     }
/* 112:152 */     if (status != Status.DELETED)
/* 113:    */     {
/* 114:154 */       if (substitute) {
/* 115:154 */         persister.setPropertyValues(entity, values);
/* 116:    */       }
/* 117:158 */       if (persister.hasCollections()) {
/* 118:159 */         new FlushVisitor(session, entity).processEntityPropertyValues(values, types);
/* 119:    */       }
/* 120:    */     }
/* 121:    */   }
/* 122:    */   
/* 123:    */   private Object[] getValues(Object entity, EntityEntry entry, boolean mightBeDirty, SessionImplementor session)
/* 124:    */   {
/* 125:166 */     Object[] loadedState = entry.getLoadedState();
/* 126:167 */     Status status = entry.getStatus();
/* 127:168 */     EntityPersister persister = entry.getPersister();
/* 128:    */     Object[] values;
/* 129:    */     Object[] values;
/* 130:171 */     if (status == Status.DELETED)
/* 131:    */     {
/* 132:173 */       values = entry.getDeletedState();
/* 133:    */     }
/* 134:    */     else
/* 135:    */     {
/* 136:    */       Object[] values;
/* 137:175 */       if ((!mightBeDirty) && (loadedState != null))
/* 138:    */       {
/* 139:176 */         values = loadedState;
/* 140:    */       }
/* 141:    */       else
/* 142:    */       {
/* 143:179 */         checkId(entity, persister, entry.getId(), session);
/* 144:    */         
/* 145:    */ 
/* 146:182 */         values = persister.getPropertyValues(entity);
/* 147:    */         
/* 148:184 */         checkNaturalId(persister, entry, values, loadedState, session);
/* 149:    */       }
/* 150:    */     }
/* 151:186 */     return values;
/* 152:    */   }
/* 153:    */   
/* 154:    */   private boolean wrapCollections(EventSource session, EntityPersister persister, Type[] types, Object[] values)
/* 155:    */   {
/* 156:195 */     if (persister.hasCollections())
/* 157:    */     {
/* 158:205 */       WrapVisitor visitor = new WrapVisitor(session);
/* 159:    */       
/* 160:207 */       visitor.processEntityPropertyValues(values, types);
/* 161:208 */       return visitor.isSubstitutionRequired();
/* 162:    */     }
/* 163:211 */     return false;
/* 164:    */   }
/* 165:    */   
/* 166:    */   private boolean isUpdateNecessary(FlushEntityEvent event, boolean mightBeDirty)
/* 167:    */   {
/* 168:216 */     Status status = event.getEntityEntry().getStatus();
/* 169:217 */     if ((mightBeDirty) || (status == Status.DELETED))
/* 170:    */     {
/* 171:219 */       dirtyCheck(event);
/* 172:220 */       if (isUpdateNecessary(event)) {
/* 173:221 */         return true;
/* 174:    */       }
/* 175:224 */       InstrumentationService instrumentationService = (InstrumentationService)event.getSession().getFactory().getServiceRegistry().getService(InstrumentationService.class);
/* 176:228 */       if (instrumentationService.isInstrumented(event.getEntity())) {
/* 177:229 */         FieldInterceptionHelper.clearDirty(event.getEntity());
/* 178:    */       }
/* 179:231 */       return false;
/* 180:    */     }
/* 181:235 */     return hasDirtyCollections(event, event.getEntityEntry().getPersister(), status);
/* 182:    */   }
/* 183:    */   
/* 184:    */   private boolean scheduleUpdate(FlushEntityEvent event)
/* 185:    */   {
/* 186:241 */     EntityEntry entry = event.getEntityEntry();
/* 187:242 */     EventSource session = event.getSession();
/* 188:243 */     Object entity = event.getEntity();
/* 189:244 */     Status status = entry.getStatus();
/* 190:245 */     EntityPersister persister = entry.getPersister();
/* 191:246 */     Object[] values = event.getPropertyValues();
/* 192:248 */     if (LOG.isTraceEnabled()) {
/* 193:249 */       if (status == Status.DELETED)
/* 194:    */       {
/* 195:250 */         if (!persister.isMutable()) {
/* 196:251 */           LOG.tracev("Updating immutable, deleted entity: {0}", MessageHelper.infoString(persister, entry.getId(), session.getFactory()));
/* 197:254 */         } else if (!entry.isModifiableEntity()) {
/* 198:255 */           LOG.tracev("Updating non-modifiable, deleted entity: {0}", MessageHelper.infoString(persister, entry.getId(), session.getFactory()));
/* 199:    */         } else {
/* 200:258 */           LOG.tracev("Updating deleted entity: ", MessageHelper.infoString(persister, entry.getId(), session.getFactory()));
/* 201:    */         }
/* 202:    */       }
/* 203:    */       else {
/* 204:262 */         LOG.tracev("Updating entity: {0}", MessageHelper.infoString(persister, entry.getId(), session.getFactory()));
/* 205:    */       }
/* 206:    */     }
/* 207:266 */     boolean intercepted = (!entry.isBeingReplicated()) && (handleInterception(event));
/* 208:    */     
/* 209:    */ 
/* 210:269 */     Object nextVersion = getNextVersion(event);
/* 211:    */     
/* 212:    */ 
/* 213:272 */     int[] dirtyProperties = event.getDirtyProperties();
/* 214:273 */     if ((event.isDirtyCheckPossible()) && (dirtyProperties == null))
/* 215:    */     {
/* 216:274 */       if ((!intercepted) && (!event.hasDirtyCollection())) {
/* 217:275 */         throw new AssertionFailure("dirty, but no dirty properties");
/* 218:    */       }
/* 219:277 */       dirtyProperties = ArrayHelper.EMPTY_INT_ARRAY;
/* 220:    */     }
/* 221:282 */     new Nullability(session).checkNullability(values, persister, true);
/* 222:    */     
/* 223:    */ 
/* 224:    */ 
/* 225:286 */     session.getActionQueue().addAction(new EntityUpdateAction(entry.getId(), values, dirtyProperties, event.hasDirtyCollection(), (status == Status.DELETED) && (!entry.isModifiableEntity()) ? persister.getPropertyValues(entity) : entry.getLoadedState(), entry.getVersion(), nextVersion, entity, entry.getRowId(), persister, session));
/* 226:    */     
/* 227:    */ 
/* 228:    */ 
/* 229:    */ 
/* 230:    */ 
/* 231:    */ 
/* 232:    */ 
/* 233:    */ 
/* 234:    */ 
/* 235:    */ 
/* 236:    */ 
/* 237:    */ 
/* 238:    */ 
/* 239:    */ 
/* 240:    */ 
/* 241:    */ 
/* 242:    */ 
/* 243:304 */     return intercepted;
/* 244:    */   }
/* 245:    */   
/* 246:    */   protected boolean handleInterception(FlushEntityEvent event)
/* 247:    */   {
/* 248:308 */     SessionImplementor session = event.getSession();
/* 249:309 */     EntityEntry entry = event.getEntityEntry();
/* 250:310 */     EntityPersister persister = entry.getPersister();
/* 251:311 */     Object entity = event.getEntity();
/* 252:    */     
/* 253:    */ 
/* 254:314 */     Object[] values = event.getPropertyValues();
/* 255:315 */     boolean intercepted = invokeInterceptor(session, entity, entry, values, persister);
/* 256:318 */     if ((intercepted) && (event.isDirtyCheckPossible()) && (!event.isDirtyCheckHandledByInterceptor()))
/* 257:    */     {
/* 258:    */       int[] dirtyProperties;
/* 259:    */       int[] dirtyProperties;
/* 260:320 */       if (event.hasDatabaseSnapshot()) {
/* 261:321 */         dirtyProperties = persister.findModified(event.getDatabaseSnapshot(), values, entity, session);
/* 262:    */       } else {
/* 263:324 */         dirtyProperties = persister.findDirty(values, entry.getLoadedState(), entity, session);
/* 264:    */       }
/* 265:326 */       event.setDirtyProperties(dirtyProperties);
/* 266:    */     }
/* 267:329 */     return intercepted;
/* 268:    */   }
/* 269:    */   
/* 270:    */   protected boolean invokeInterceptor(SessionImplementor session, Object entity, EntityEntry entry, Object[] values, EntityPersister persister)
/* 271:    */   {
/* 272:338 */     return session.getInterceptor().onFlushDirty(entity, entry.getId(), values, entry.getLoadedState(), persister.getPropertyNames(), persister.getPropertyTypes());
/* 273:    */   }
/* 274:    */   
/* 275:    */   private Object getNextVersion(FlushEntityEvent event)
/* 276:    */     throws HibernateException
/* 277:    */   {
/* 278:353 */     EntityEntry entry = event.getEntityEntry();
/* 279:354 */     EntityPersister persister = entry.getPersister();
/* 280:355 */     if (persister.isVersioned())
/* 281:    */     {
/* 282:357 */       Object[] values = event.getPropertyValues();
/* 283:359 */       if (entry.isBeingReplicated()) {
/* 284:360 */         return Versioning.getVersion(values, persister);
/* 285:    */       }
/* 286:363 */       int[] dirtyProperties = event.getDirtyProperties();
/* 287:    */       
/* 288:365 */       boolean isVersionIncrementRequired = isVersionIncrementRequired(event, entry, persister, dirtyProperties);
/* 289:    */       
/* 290:    */ 
/* 291:    */ 
/* 292:    */ 
/* 293:    */ 
/* 294:    */ 
/* 295:372 */       Object nextVersion = isVersionIncrementRequired ? Versioning.increment(entry.getVersion(), persister.getVersionType(), event.getSession()) : entry.getVersion();
/* 296:    */       
/* 297:    */ 
/* 298:    */ 
/* 299:376 */       Versioning.setVersion(values, nextVersion, persister);
/* 300:    */       
/* 301:378 */       return nextVersion;
/* 302:    */     }
/* 303:382 */     return null;
/* 304:    */   }
/* 305:    */   
/* 306:    */   private boolean isVersionIncrementRequired(FlushEntityEvent event, EntityEntry entry, EntityPersister persister, int[] dirtyProperties)
/* 307:    */   {
/* 308:393 */     boolean isVersionIncrementRequired = (entry.getStatus() != Status.DELETED) && ((dirtyProperties == null) || (Versioning.isVersionIncrementRequired(dirtyProperties, event.hasDirtyCollection(), persister.getPropertyVersionability())));
/* 309:    */     
/* 310:    */ 
/* 311:    */ 
/* 312:    */ 
/* 313:    */ 
/* 314:    */ 
/* 315:    */ 
/* 316:401 */     return isVersionIncrementRequired;
/* 317:    */   }
/* 318:    */   
/* 319:    */   protected final boolean isUpdateNecessary(FlushEntityEvent event)
/* 320:    */     throws HibernateException
/* 321:    */   {
/* 322:411 */     EntityPersister persister = event.getEntityEntry().getPersister();
/* 323:412 */     Status status = event.getEntityEntry().getStatus();
/* 324:414 */     if (!event.isDirtyCheckPossible()) {
/* 325:415 */       return true;
/* 326:    */     }
/* 327:419 */     int[] dirtyProperties = event.getDirtyProperties();
/* 328:420 */     if ((dirtyProperties != null) && (dirtyProperties.length != 0)) {
/* 329:421 */       return true;
/* 330:    */     }
/* 331:424 */     return hasDirtyCollections(event, persister, status);
/* 332:    */   }
/* 333:    */   
/* 334:    */   private boolean hasDirtyCollections(FlushEntityEvent event, EntityPersister persister, Status status)
/* 335:    */   {
/* 336:431 */     if (isCollectionDirtyCheckNecessary(persister, status))
/* 337:    */     {
/* 338:432 */       DirtyCollectionSearchVisitor visitor = new DirtyCollectionSearchVisitor(event.getSession(), persister.getPropertyVersionability());
/* 339:    */       
/* 340:    */ 
/* 341:    */ 
/* 342:436 */       visitor.processEntityPropertyValues(event.getPropertyValues(), persister.getPropertyTypes());
/* 343:437 */       boolean hasDirtyCollections = visitor.wasDirtyCollectionFound();
/* 344:438 */       event.setHasDirtyCollection(hasDirtyCollections);
/* 345:439 */       return hasDirtyCollections;
/* 346:    */     }
/* 347:442 */     return false;
/* 348:    */   }
/* 349:    */   
/* 350:    */   private boolean isCollectionDirtyCheckNecessary(EntityPersister persister, Status status)
/* 351:    */   {
/* 352:447 */     return ((status == Status.MANAGED) || (status == Status.READ_ONLY)) && (persister.isVersioned()) && (persister.hasCollections());
/* 353:    */   }
/* 354:    */   
/* 355:    */   protected void dirtyCheck(FlushEntityEvent event)
/* 356:    */     throws HibernateException
/* 357:    */   {
/* 358:457 */     Object entity = event.getEntity();
/* 359:458 */     Object[] values = event.getPropertyValues();
/* 360:459 */     SessionImplementor session = event.getSession();
/* 361:460 */     EntityEntry entry = event.getEntityEntry();
/* 362:461 */     EntityPersister persister = entry.getPersister();
/* 363:462 */     Serializable id = entry.getId();
/* 364:463 */     Object[] loadedState = entry.getLoadedState();
/* 365:    */     
/* 366:465 */     int[] dirtyProperties = session.getInterceptor().findDirty(entity, id, values, loadedState, persister.getPropertyNames(), persister.getPropertyTypes());
/* 367:    */     
/* 368:    */ 
/* 369:    */ 
/* 370:    */ 
/* 371:    */ 
/* 372:    */ 
/* 373:    */ 
/* 374:    */ 
/* 375:474 */     event.setDatabaseSnapshot(null);
/* 376:    */     boolean cannotDirtyCheck;
/* 377:    */     boolean interceptorHandledDirtyCheck;
/* 378:479 */     if (dirtyProperties == null)
/* 379:    */     {
/* 380:481 */       boolean interceptorHandledDirtyCheck = false;
/* 381:    */       
/* 382:483 */       boolean cannotDirtyCheck = loadedState == null;
/* 383:484 */       if (!cannotDirtyCheck)
/* 384:    */       {
/* 385:486 */         dirtyProperties = persister.findDirty(values, loadedState, entity, session);
/* 386:    */       }
/* 387:488 */       else if ((entry.getStatus() == Status.DELETED) && (!event.getEntityEntry().isModifiableEntity()))
/* 388:    */       {
/* 389:492 */         if (values != entry.getDeletedState()) {
/* 390:493 */           throw new IllegalStateException("Entity has status Status.DELETED but values != entry.getDeletedState");
/* 391:    */         }
/* 392:504 */         Object[] currentState = persister.getPropertyValues(event.getEntity());
/* 393:505 */         dirtyProperties = persister.findDirty(entry.getDeletedState(), currentState, entity, session);
/* 394:506 */         cannotDirtyCheck = false;
/* 395:    */       }
/* 396:    */       else
/* 397:    */       {
/* 398:510 */         Object[] databaseSnapshot = getDatabaseSnapshot(session, persister, id);
/* 399:511 */         if (databaseSnapshot != null)
/* 400:    */         {
/* 401:512 */           dirtyProperties = persister.findModified(databaseSnapshot, values, entity, session);
/* 402:513 */           cannotDirtyCheck = false;
/* 403:514 */           event.setDatabaseSnapshot(databaseSnapshot);
/* 404:    */         }
/* 405:    */       }
/* 406:    */     }
/* 407:    */     else
/* 408:    */     {
/* 409:520 */       cannotDirtyCheck = false;
/* 410:521 */       interceptorHandledDirtyCheck = true;
/* 411:    */     }
/* 412:524 */     logDirtyProperties(id, dirtyProperties, persister);
/* 413:    */     
/* 414:526 */     event.setDirtyProperties(dirtyProperties);
/* 415:527 */     event.setDirtyCheckHandledByInterceptor(interceptorHandledDirtyCheck);
/* 416:528 */     event.setDirtyCheckPossible(!cannotDirtyCheck);
/* 417:    */   }
/* 418:    */   
/* 419:    */   private void logDirtyProperties(Serializable id, int[] dirtyProperties, EntityPersister persister)
/* 420:    */   {
/* 421:533 */     if ((LOG.isTraceEnabled()) && (dirtyProperties != null) && (dirtyProperties.length > 0))
/* 422:    */     {
/* 423:534 */       String[] allPropertyNames = persister.getPropertyNames();
/* 424:535 */       String[] dirtyPropertyNames = new String[dirtyProperties.length];
/* 425:536 */       for (int i = 0; i < dirtyProperties.length; i++) {
/* 426:537 */         dirtyPropertyNames[i] = allPropertyNames[dirtyProperties[i]];
/* 427:    */       }
/* 428:539 */       LOG.tracev("Found dirty properties [{0}] : {1}", MessageHelper.infoString(persister.getEntityName(), id), dirtyPropertyNames);
/* 429:    */     }
/* 430:    */   }
/* 431:    */   
/* 432:    */   private Object[] getDatabaseSnapshot(SessionImplementor session, EntityPersister persister, Serializable id)
/* 433:    */   {
/* 434:546 */     if (persister.isSelectBeforeUpdateRequired())
/* 435:    */     {
/* 436:547 */       Object[] snapshot = session.getPersistenceContext().getDatabaseSnapshot(id, persister);
/* 437:549 */       if (snapshot == null)
/* 438:    */       {
/* 439:551 */         if (session.getFactory().getStatistics().isStatisticsEnabled()) {
/* 440:552 */           session.getFactory().getStatisticsImplementor().optimisticFailure(persister.getEntityName());
/* 441:    */         }
/* 442:555 */         throw new StaleObjectStateException(persister.getEntityName(), id);
/* 443:    */       }
/* 444:557 */       return snapshot;
/* 445:    */     }
/* 446:560 */     EntityKey entityKey = session.generateEntityKey(id, persister);
/* 447:561 */     return session.getPersistenceContext().getCachedDatabaseSnapshot(entityKey);
/* 448:    */   }
/* 449:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.internal.DefaultFlushEntityEventListener
 * JD-Core Version:    0.7.0.1
 */