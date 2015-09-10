/*   1:    */ package org.hibernate.event.internal;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Map;
/*   5:    */ import org.hibernate.Interceptor;
/*   6:    */ import org.hibernate.LockMode;
/*   7:    */ import org.hibernate.NonUniqueObjectException;
/*   8:    */ import org.hibernate.action.internal.EntityIdentityInsertAction;
/*   9:    */ import org.hibernate.action.internal.EntityInsertAction;
/*  10:    */ import org.hibernate.bytecode.instrumentation.internal.FieldInterceptionHelper;
/*  11:    */ import org.hibernate.bytecode.instrumentation.spi.FieldInterceptor;
/*  12:    */ import org.hibernate.classic.Lifecycle;
/*  13:    */ import org.hibernate.engine.internal.Cascade;
/*  14:    */ import org.hibernate.engine.internal.ForeignKeys;
/*  15:    */ import org.hibernate.engine.internal.ForeignKeys.Nullifier;
/*  16:    */ import org.hibernate.engine.internal.Nullability;
/*  17:    */ import org.hibernate.engine.internal.Versioning;
/*  18:    */ import org.hibernate.engine.spi.ActionQueue;
/*  19:    */ import org.hibernate.engine.spi.CascadingAction;
/*  20:    */ import org.hibernate.engine.spi.EntityEntry;
/*  21:    */ import org.hibernate.engine.spi.EntityKey;
/*  22:    */ import org.hibernate.engine.spi.PersistenceContext;
/*  23:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  24:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  25:    */ import org.hibernate.engine.spi.Status;
/*  26:    */ import org.hibernate.engine.transaction.spi.TransactionCoordinator;
/*  27:    */ import org.hibernate.event.spi.EventSource;
/*  28:    */ import org.hibernate.id.IdentifierGenerationException;
/*  29:    */ import org.hibernate.id.IdentifierGenerator;
/*  30:    */ import org.hibernate.id.IdentifierGeneratorHelper;
/*  31:    */ import org.hibernate.internal.CoreMessageLogger;
/*  32:    */ import org.hibernate.persister.entity.EntityPersister;
/*  33:    */ import org.hibernate.pretty.MessageHelper;
/*  34:    */ import org.hibernate.service.instrumentation.spi.InstrumentationService;
/*  35:    */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  36:    */ import org.hibernate.type.Type;
/*  37:    */ import org.hibernate.type.TypeHelper;
/*  38:    */ import org.jboss.logging.Logger;
/*  39:    */ 
/*  40:    */ public abstract class AbstractSaveEventListener
/*  41:    */   extends AbstractReassociateEventListener
/*  42:    */ {
/*  43:    */   public static enum EntityState
/*  44:    */   {
/*  45: 64 */     PERSISTENT,  TRANSIENT,  DETACHED,  DELETED;
/*  46:    */     
/*  47:    */     private EntityState() {}
/*  48:    */   }
/*  49:    */   
/*  50: 67 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, AbstractSaveEventListener.class.getName());
/*  51:    */   
/*  52:    */   protected Serializable saveWithRequestedId(Object entity, Serializable requestedId, String entityName, Object anything, EventSource source)
/*  53:    */   {
/*  54: 87 */     return performSave(entity, requestedId, source.getEntityPersister(entityName, entity), false, anything, source, true);
/*  55:    */   }
/*  56:    */   
/*  57:    */   protected Serializable saveWithGeneratedId(Object entity, String entityName, Object anything, EventSource source, boolean requiresImmediateIdAccess)
/*  58:    */   {
/*  59:119 */     EntityPersister persister = source.getEntityPersister(entityName, entity);
/*  60:120 */     Serializable generatedId = persister.getIdentifierGenerator().generate(source, entity);
/*  61:121 */     if (generatedId == null) {
/*  62:122 */       throw new IdentifierGenerationException("null id generated for:" + entity.getClass());
/*  63:    */     }
/*  64:124 */     if (generatedId == IdentifierGeneratorHelper.SHORT_CIRCUIT_INDICATOR) {
/*  65:125 */       return source.getIdentifier(entity);
/*  66:    */     }
/*  67:127 */     if (generatedId == IdentifierGeneratorHelper.POST_INSERT_INDICATOR) {
/*  68:128 */       return performSave(entity, null, persister, true, anything, source, requiresImmediateIdAccess);
/*  69:    */     }
/*  70:132 */     if (LOG.isDebugEnabled()) {
/*  71:133 */       LOG.debugf("Generated identifier: %s, using strategy: %s", persister.getIdentifierType().toLoggableString(generatedId, source.getFactory()), persister.getIdentifierGenerator().getClass().getName());
/*  72:    */     }
/*  73:138 */     return performSave(entity, generatedId, persister, false, anything, source, true);
/*  74:    */   }
/*  75:    */   
/*  76:    */   protected Serializable performSave(Object entity, Serializable id, EntityPersister persister, boolean useIdentityColumn, Object anything, EventSource source, boolean requiresImmediateIdAccess)
/*  77:    */   {
/*  78:169 */     if (LOG.isTraceEnabled()) {
/*  79:170 */       LOG.tracev("Saving {0}", MessageHelper.infoString(persister, id, source.getFactory()));
/*  80:    */     }
/*  81:    */     EntityKey key;
/*  82:174 */     if (!useIdentityColumn)
/*  83:    */     {
/*  84:175 */       EntityKey key = source.generateEntityKey(id, persister);
/*  85:176 */       Object old = source.getPersistenceContext().getEntity(key);
/*  86:177 */       if (old != null) {
/*  87:178 */         if (source.getPersistenceContext().getEntry(old).getStatus() == Status.DELETED) {
/*  88:179 */           source.forceFlush(source.getPersistenceContext().getEntry(old));
/*  89:    */         } else {
/*  90:182 */           throw new NonUniqueObjectException(id, persister.getEntityName());
/*  91:    */         }
/*  92:    */       }
/*  93:185 */       persister.setIdentifier(entity, id, source);
/*  94:    */     }
/*  95:    */     else
/*  96:    */     {
/*  97:188 */       key = null;
/*  98:    */     }
/*  99:191 */     if (invokeSaveLifecycle(entity, persister, source)) {
/* 100:192 */       return id;
/* 101:    */     }
/* 102:195 */     return performSaveOrReplicate(entity, key, persister, useIdentityColumn, anything, source, requiresImmediateIdAccess);
/* 103:    */   }
/* 104:    */   
/* 105:    */   protected boolean invokeSaveLifecycle(Object entity, EntityPersister persister, EventSource source)
/* 106:    */   {
/* 107:209 */     if (persister.implementsLifecycle())
/* 108:    */     {
/* 109:210 */       LOG.debug("Calling onSave()");
/* 110:211 */       if (((Lifecycle)entity).onSave(source))
/* 111:    */       {
/* 112:212 */         LOG.debug("Insertion vetoed by onSave()");
/* 113:213 */         return true;
/* 114:    */       }
/* 115:    */     }
/* 116:216 */     return false;
/* 117:    */   }
/* 118:    */   
/* 119:    */   protected Serializable performSaveOrReplicate(Object entity, EntityKey key, EntityPersister persister, boolean useIdentityColumn, Object anything, EventSource source, boolean requiresImmediateIdAccess)
/* 120:    */   {
/* 121:244 */     Serializable id = key == null ? null : key.getIdentifier();
/* 122:    */     
/* 123:246 */     boolean inTxn = source.getTransactionCoordinator().isTransactionInProgress();
/* 124:247 */     boolean shouldDelayIdentityInserts = (!inTxn) && (!requiresImmediateIdAccess);
/* 125:    */     
/* 126:    */ 
/* 127:    */ 
/* 128:    */ 
/* 129:252 */     source.getPersistenceContext().addEntry(entity, Status.SAVING, null, null, id, null, LockMode.WRITE, useIdentityColumn, persister, false, false);
/* 130:    */     
/* 131:    */ 
/* 132:    */ 
/* 133:    */ 
/* 134:    */ 
/* 135:    */ 
/* 136:    */ 
/* 137:    */ 
/* 138:    */ 
/* 139:    */ 
/* 140:    */ 
/* 141:    */ 
/* 142:    */ 
/* 143:266 */     cascadeBeforeSave(source, persister, entity, anything);
/* 144:268 */     if ((useIdentityColumn) && (!shouldDelayIdentityInserts))
/* 145:    */     {
/* 146:269 */       LOG.trace("Executing insertions");
/* 147:270 */       source.getActionQueue().executeInserts();
/* 148:    */     }
/* 149:273 */     Object[] values = persister.getPropertyValuesToInsert(entity, getMergeMap(anything), source);
/* 150:274 */     Type[] types = persister.getPropertyTypes();
/* 151:    */     
/* 152:276 */     boolean substitute = substituteValuesIfNecessary(entity, id, values, persister, source);
/* 153:278 */     if (persister.hasCollections()) {
/* 154:279 */       substitute = (substitute) || (visitCollectionsBeforeSave(entity, id, values, types, source));
/* 155:    */     }
/* 156:282 */     if (substitute) {
/* 157:283 */       persister.setPropertyValues(entity, values);
/* 158:    */     }
/* 159:286 */     TypeHelper.deepCopy(values, types, persister.getPropertyUpdateability(), values, source);
/* 160:    */     
/* 161:    */ 
/* 162:    */ 
/* 163:    */ 
/* 164:    */ 
/* 165:    */ 
/* 166:    */ 
/* 167:294 */     new ForeignKeys.Nullifier(entity, false, useIdentityColumn, source).nullifyTransientReferences(values, types);
/* 168:    */     
/* 169:296 */     new Nullability(source).checkNullability(values, persister, false);
/* 170:298 */     if (useIdentityColumn)
/* 171:    */     {
/* 172:299 */       EntityIdentityInsertAction insert = new EntityIdentityInsertAction(values, entity, persister, source, shouldDelayIdentityInserts);
/* 173:302 */       if (!shouldDelayIdentityInserts)
/* 174:    */       {
/* 175:303 */         LOG.debug("Executing identity-insert immediately");
/* 176:304 */         source.getActionQueue().execute(insert);
/* 177:305 */         id = insert.getGeneratedId();
/* 178:306 */         key = source.generateEntityKey(id, persister);
/* 179:307 */         source.getPersistenceContext().checkUniqueness(key, entity);
/* 180:    */       }
/* 181:    */       else
/* 182:    */       {
/* 183:310 */         LOG.debug("Delaying identity-insert due to no transaction in progress");
/* 184:311 */         source.getActionQueue().addAction(insert);
/* 185:312 */         key = insert.getDelayedEntityKey();
/* 186:    */       }
/* 187:    */     }
/* 188:316 */     Object version = Versioning.getVersion(values, persister);
/* 189:317 */     source.getPersistenceContext().addEntity(entity, persister.isMutable() ? Status.MANAGED : Status.READ_ONLY, values, key, version, LockMode.WRITE, useIdentityColumn, persister, isVersionIncrementDisabled(), false);
/* 190:331 */     if (!useIdentityColumn) {
/* 191:332 */       source.getActionQueue().addAction(new EntityInsertAction(id, values, entity, version, persister, source));
/* 192:    */     }
/* 193:337 */     cascadeAfterSave(source, persister, entity, anything);
/* 194:    */     
/* 195:339 */     markInterceptorDirty(entity, persister, source);
/* 196:    */     
/* 197:341 */     return id;
/* 198:    */   }
/* 199:    */   
/* 200:    */   private void markInterceptorDirty(Object entity, EntityPersister persister, EventSource source)
/* 201:    */   {
/* 202:345 */     InstrumentationService instrumentationService = (InstrumentationService)persister.getFactory().getServiceRegistry().getService(InstrumentationService.class);
/* 203:348 */     if (instrumentationService.isInstrumented(entity))
/* 204:    */     {
/* 205:349 */       FieldInterceptor interceptor = FieldInterceptionHelper.injectFieldInterceptor(entity, persister.getEntityName(), null, source);
/* 206:    */       
/* 207:    */ 
/* 208:    */ 
/* 209:    */ 
/* 210:    */ 
/* 211:355 */       interceptor.dirty();
/* 212:    */     }
/* 213:    */   }
/* 214:    */   
/* 215:    */   protected Map getMergeMap(Object anything)
/* 216:    */   {
/* 217:360 */     return null;
/* 218:    */   }
/* 219:    */   
/* 220:    */   protected boolean isVersionIncrementDisabled()
/* 221:    */   {
/* 222:371 */     return false;
/* 223:    */   }
/* 224:    */   
/* 225:    */   protected boolean visitCollectionsBeforeSave(Object entity, Serializable id, Object[] values, Type[] types, EventSource source)
/* 226:    */   {
/* 227:375 */     WrapVisitor visitor = new WrapVisitor(source);
/* 228:    */     
/* 229:377 */     visitor.processEntityPropertyValues(values, types);
/* 230:378 */     return visitor.isSubstitutionRequired();
/* 231:    */   }
/* 232:    */   
/* 233:    */   protected boolean substituteValuesIfNecessary(Object entity, Serializable id, Object[] values, EntityPersister persister, SessionImplementor source)
/* 234:    */   {
/* 235:400 */     boolean substitute = source.getInterceptor().onSave(entity, id, values, persister.getPropertyNames(), persister.getPropertyTypes());
/* 236:409 */     if (persister.isVersioned()) {
/* 237:410 */       substitute = (Versioning.seedVersion(values, persister.getVersionProperty(), persister.getVersionType(), source)) || (substitute);
/* 238:    */     }
/* 239:417 */     return substitute;
/* 240:    */   }
/* 241:    */   
/* 242:    */   protected void cascadeBeforeSave(EventSource source, EntityPersister persister, Object entity, Object anything)
/* 243:    */   {
/* 244:435 */     source.getPersistenceContext().incrementCascadeLevel();
/* 245:    */     try
/* 246:    */     {
/* 247:437 */       new Cascade(getCascadeAction(), 2, source).cascade(persister, entity, anything);
/* 248:    */     }
/* 249:    */     finally
/* 250:    */     {
/* 251:441 */       source.getPersistenceContext().decrementCascadeLevel();
/* 252:    */     }
/* 253:    */   }
/* 254:    */   
/* 255:    */   protected void cascadeAfterSave(EventSource source, EntityPersister persister, Object entity, Object anything)
/* 256:    */   {
/* 257:460 */     source.getPersistenceContext().incrementCascadeLevel();
/* 258:    */     try
/* 259:    */     {
/* 260:462 */       new Cascade(getCascadeAction(), 1, source).cascade(persister, entity, anything);
/* 261:    */     }
/* 262:    */     finally
/* 263:    */     {
/* 264:466 */       source.getPersistenceContext().decrementCascadeLevel();
/* 265:    */     }
/* 266:    */   }
/* 267:    */   
/* 268:    */   protected abstract CascadingAction getCascadeAction();
/* 269:    */   
/* 270:    */   protected EntityState getEntityState(Object entity, String entityName, EntityEntry entry, SessionImplementor source)
/* 271:    */   {
/* 272:488 */     if (entry != null)
/* 273:    */     {
/* 274:491 */       if (entry.getStatus() != Status.DELETED)
/* 275:    */       {
/* 276:493 */         if (LOG.isTraceEnabled()) {
/* 277:494 */           LOG.tracev("Persistent instance of: {0}", getLoggableName(entityName, entity));
/* 278:    */         }
/* 279:496 */         return EntityState.PERSISTENT;
/* 280:    */       }
/* 281:499 */       if (LOG.isTraceEnabled()) {
/* 282:500 */         LOG.tracev("Deleted instance of: {0}", getLoggableName(entityName, entity));
/* 283:    */       }
/* 284:502 */       return EntityState.DELETED;
/* 285:    */     }
/* 286:509 */     if (ForeignKeys.isTransient(entityName, entity, getAssumedUnsaved(), source))
/* 287:    */     {
/* 288:510 */       if (LOG.isTraceEnabled()) {
/* 289:511 */         LOG.tracev("Transient instance of: {0}", getLoggableName(entityName, entity));
/* 290:    */       }
/* 291:513 */       return EntityState.TRANSIENT;
/* 292:    */     }
/* 293:515 */     if (LOG.isTraceEnabled()) {
/* 294:516 */       LOG.tracev("Detached instance of: {0}", getLoggableName(entityName, entity));
/* 295:    */     }
/* 296:518 */     return EntityState.DETACHED;
/* 297:    */   }
/* 298:    */   
/* 299:    */   protected String getLoggableName(String entityName, Object entity)
/* 300:    */   {
/* 301:522 */     return entityName == null ? entity.getClass().getName() : entityName;
/* 302:    */   }
/* 303:    */   
/* 304:    */   protected Boolean getAssumedUnsaved()
/* 305:    */   {
/* 306:526 */     return null;
/* 307:    */   }
/* 308:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.internal.AbstractSaveEventListener
 * JD-Core Version:    0.7.0.1
 */