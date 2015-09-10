/*   1:    */ package org.hibernate.event.internal;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Arrays;
/*   5:    */ import java.util.HashSet;
/*   6:    */ import java.util.Set;
/*   7:    */ import org.hibernate.CacheMode;
/*   8:    */ import org.hibernate.HibernateException;
/*   9:    */ import org.hibernate.Interceptor;
/*  10:    */ import org.hibernate.LockMode;
/*  11:    */ import org.hibernate.TransientObjectException;
/*  12:    */ import org.hibernate.action.internal.EntityDeleteAction;
/*  13:    */ import org.hibernate.cfg.Settings;
/*  14:    */ import org.hibernate.classic.Lifecycle;
/*  15:    */ import org.hibernate.engine.internal.Cascade;
/*  16:    */ import org.hibernate.engine.internal.ForeignKeys;
/*  17:    */ import org.hibernate.engine.internal.ForeignKeys.Nullifier;
/*  18:    */ import org.hibernate.engine.internal.Nullability;
/*  19:    */ import org.hibernate.engine.spi.ActionQueue;
/*  20:    */ import org.hibernate.engine.spi.CascadingAction;
/*  21:    */ import org.hibernate.engine.spi.EntityEntry;
/*  22:    */ import org.hibernate.engine.spi.EntityKey;
/*  23:    */ import org.hibernate.engine.spi.PersistenceContext;
/*  24:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  25:    */ import org.hibernate.engine.spi.Status;
/*  26:    */ import org.hibernate.event.spi.DeleteEvent;
/*  27:    */ import org.hibernate.event.spi.DeleteEventListener;
/*  28:    */ import org.hibernate.event.spi.EventSource;
/*  29:    */ import org.hibernate.internal.CoreMessageLogger;
/*  30:    */ import org.hibernate.internal.util.collections.IdentitySet;
/*  31:    */ import org.hibernate.persister.entity.EntityPersister;
/*  32:    */ import org.hibernate.pretty.MessageHelper;
/*  33:    */ import org.hibernate.type.Type;
/*  34:    */ import org.hibernate.type.TypeHelper;
/*  35:    */ import org.jboss.logging.Logger;
/*  36:    */ 
/*  37:    */ public class DefaultDeleteEventListener
/*  38:    */   implements DeleteEventListener
/*  39:    */ {
/*  40: 63 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, DefaultDeleteEventListener.class.getName());
/*  41:    */   
/*  42:    */   public void onDelete(DeleteEvent event)
/*  43:    */     throws HibernateException
/*  44:    */   {
/*  45: 74 */     onDelete(event, new IdentitySet());
/*  46:    */   }
/*  47:    */   
/*  48:    */   public void onDelete(DeleteEvent event, Set transientEntities)
/*  49:    */     throws HibernateException
/*  50:    */   {
/*  51: 87 */     EventSource source = event.getSession();
/*  52:    */     
/*  53: 89 */     PersistenceContext persistenceContext = source.getPersistenceContext();
/*  54: 90 */     Object entity = persistenceContext.unproxyAndReassociate(event.getObject());
/*  55:    */     
/*  56: 92 */     EntityEntry entityEntry = persistenceContext.getEntry(entity);
/*  57:    */     EntityPersister persister;
/*  58:    */     Serializable id;
/*  59:    */     Object version;
/*  60: 97 */     if (entityEntry == null)
/*  61:    */     {
/*  62: 98 */       LOG.trace("Entity was not persistent in delete processing");
/*  63:    */       
/*  64:100 */       EntityPersister persister = source.getEntityPersister(event.getEntityName(), entity);
/*  65:102 */       if (ForeignKeys.isTransient(persister.getEntityName(), entity, null, source))
/*  66:    */       {
/*  67:103 */         deleteTransientEntity(source, entity, event.isCascadeDeleteEnabled(), persister, transientEntities);
/*  68:    */         
/*  69:105 */         return;
/*  70:    */       }
/*  71:107 */       performDetachedEntityDeletionCheck(event);
/*  72:    */       
/*  73:109 */       Serializable id = persister.getIdentifier(entity, source);
/*  74:111 */       if (id == null) {
/*  75:112 */         throw new TransientObjectException("the detached instance passed to delete() had a null identifier");
/*  76:    */       }
/*  77:117 */       EntityKey key = source.generateEntityKey(id, persister);
/*  78:    */       
/*  79:119 */       persistenceContext.checkUniqueness(key, entity);
/*  80:    */       
/*  81:121 */       new OnUpdateVisitor(source, id, entity).process(entity, persister);
/*  82:    */       
/*  83:123 */       Object version = persister.getVersion(entity);
/*  84:    */       
/*  85:125 */       entityEntry = persistenceContext.addEntity(entity, persister.isMutable() ? Status.MANAGED : Status.READ_ONLY, persister.getPropertyValues(entity), key, version, LockMode.NONE, true, persister, false, false);
/*  86:    */     }
/*  87:    */     else
/*  88:    */     {
/*  89:139 */       LOG.trace("Deleting a persistent instance");
/*  90:141 */       if ((entityEntry.getStatus() == Status.DELETED) || (entityEntry.getStatus() == Status.GONE))
/*  91:    */       {
/*  92:142 */         LOG.trace("Object was already deleted");
/*  93:143 */         return;
/*  94:    */       }
/*  95:145 */       persister = entityEntry.getPersister();
/*  96:146 */       id = entityEntry.getId();
/*  97:147 */       version = entityEntry.getVersion();
/*  98:    */     }
/*  99:157 */     if (invokeDeleteLifecycle(source, entity, persister)) {
/* 100:158 */       return;
/* 101:    */     }
/* 102:161 */     deleteEntity(source, entity, entityEntry, event.isCascadeDeleteEnabled(), persister, transientEntities);
/* 103:163 */     if (source.getFactory().getSettings().isIdentifierRollbackEnabled()) {
/* 104:164 */       persister.resetIdentifier(entity, id, version, source);
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   protected void performDetachedEntityDeletionCheck(DeleteEvent event) {}
/* 109:    */   
/* 110:    */   protected void deleteTransientEntity(EventSource session, Object entity, boolean cascadeDeleteEnabled, EntityPersister persister, Set transientEntities)
/* 111:    */   {
/* 112:202 */     LOG.handlingTransientEntity();
/* 113:203 */     if (transientEntities.contains(entity))
/* 114:    */     {
/* 115:204 */       LOG.trace("Already handled transient entity; skipping");
/* 116:205 */       return;
/* 117:    */     }
/* 118:207 */     transientEntities.add(entity);
/* 119:208 */     cascadeBeforeDelete(session, persister, entity, null, transientEntities);
/* 120:209 */     cascadeAfterDelete(session, persister, entity, transientEntities);
/* 121:    */   }
/* 122:    */   
/* 123:    */   protected final void deleteEntity(EventSource session, Object entity, EntityEntry entityEntry, boolean isCascadeDeleteEnabled, EntityPersister persister, Set transientEntities)
/* 124:    */   {
/* 125:232 */     if (LOG.isTraceEnabled()) {
/* 126:233 */       LOG.tracev("Deleting {0}", MessageHelper.infoString(persister, entityEntry.getId(), session.getFactory()));
/* 127:    */     }
/* 128:236 */     PersistenceContext persistenceContext = session.getPersistenceContext();
/* 129:237 */     Type[] propTypes = persister.getPropertyTypes();
/* 130:238 */     Object version = entityEntry.getVersion();
/* 131:    */     Object[] currentState;
/* 132:    */     Object[] currentState;
/* 133:241 */     if (entityEntry.getLoadedState() == null) {
/* 134:242 */       currentState = persister.getPropertyValues(entity);
/* 135:    */     } else {
/* 136:245 */       currentState = entityEntry.getLoadedState();
/* 137:    */     }
/* 138:248 */     Object[] deletedState = createDeletedState(persister, currentState, session);
/* 139:249 */     entityEntry.setDeletedState(deletedState);
/* 140:    */     
/* 141:251 */     session.getInterceptor().onDelete(entity, entityEntry.getId(), deletedState, persister.getPropertyNames(), propTypes);
/* 142:    */     
/* 143:    */ 
/* 144:    */ 
/* 145:    */ 
/* 146:    */ 
/* 147:    */ 
/* 148:    */ 
/* 149:    */ 
/* 150:260 */     persistenceContext.setEntryStatus(entityEntry, Status.DELETED);
/* 151:261 */     EntityKey key = session.generateEntityKey(entityEntry.getId(), persister);
/* 152:    */     
/* 153:263 */     cascadeBeforeDelete(session, persister, entity, entityEntry, transientEntities);
/* 154:    */     
/* 155:265 */     new ForeignKeys.Nullifier(entity, true, false, session).nullifyTransientReferences(entityEntry.getDeletedState(), propTypes);
/* 156:    */     
/* 157:267 */     new Nullability(session).checkNullability(entityEntry.getDeletedState(), persister, true);
/* 158:268 */     persistenceContext.getNullifiableEntityKeys().add(key);
/* 159:    */     
/* 160:    */ 
/* 161:271 */     session.getActionQueue().addAction(new EntityDeleteAction(entityEntry.getId(), deletedState, version, entity, persister, isCascadeDeleteEnabled, session));
/* 162:    */     
/* 163:    */ 
/* 164:    */ 
/* 165:    */ 
/* 166:    */ 
/* 167:    */ 
/* 168:    */ 
/* 169:    */ 
/* 170:    */ 
/* 171:    */ 
/* 172:    */ 
/* 173:283 */     cascadeAfterDelete(session, persister, entity, transientEntities);
/* 174:    */   }
/* 175:    */   
/* 176:    */   private Object[] createDeletedState(EntityPersister persister, Object[] currentState, EventSource session)
/* 177:    */   {
/* 178:292 */     Type[] propTypes = persister.getPropertyTypes();
/* 179:293 */     Object[] deletedState = new Object[propTypes.length];
/* 180:    */     
/* 181:295 */     boolean[] copyability = new boolean[propTypes.length];
/* 182:296 */     Arrays.fill(copyability, true);
/* 183:297 */     TypeHelper.deepCopy(currentState, propTypes, copyability, deletedState, session);
/* 184:298 */     return deletedState;
/* 185:    */   }
/* 186:    */   
/* 187:    */   protected boolean invokeDeleteLifecycle(EventSource session, Object entity, EntityPersister persister)
/* 188:    */   {
/* 189:302 */     if (persister.implementsLifecycle())
/* 190:    */     {
/* 191:303 */       LOG.debug("Calling onDelete()");
/* 192:304 */       if (((Lifecycle)entity).onDelete(session))
/* 193:    */       {
/* 194:305 */         LOG.debug("Deletion vetoed by onDelete()");
/* 195:306 */         return true;
/* 196:    */       }
/* 197:    */     }
/* 198:309 */     return false;
/* 199:    */   }
/* 200:    */   
/* 201:    */   protected void cascadeBeforeDelete(EventSource session, EntityPersister persister, Object entity, EntityEntry entityEntry, Set transientEntities)
/* 202:    */     throws HibernateException
/* 203:    */   {
/* 204:319 */     CacheMode cacheMode = session.getCacheMode();
/* 205:320 */     session.setCacheMode(CacheMode.GET);
/* 206:321 */     session.getPersistenceContext().incrementCascadeLevel();
/* 207:    */     try
/* 208:    */     {
/* 209:324 */       new Cascade(CascadingAction.DELETE, 1, session).cascade(persister, entity, transientEntities);
/* 210:    */     }
/* 211:    */     finally
/* 212:    */     {
/* 213:328 */       session.getPersistenceContext().decrementCascadeLevel();
/* 214:329 */       session.setCacheMode(cacheMode);
/* 215:    */     }
/* 216:    */   }
/* 217:    */   
/* 218:    */   protected void cascadeAfterDelete(EventSource session, EntityPersister persister, Object entity, Set transientEntities)
/* 219:    */     throws HibernateException
/* 220:    */   {
/* 221:339 */     CacheMode cacheMode = session.getCacheMode();
/* 222:340 */     session.setCacheMode(CacheMode.GET);
/* 223:341 */     session.getPersistenceContext().incrementCascadeLevel();
/* 224:    */     try
/* 225:    */     {
/* 226:344 */       new Cascade(CascadingAction.DELETE, 2, session).cascade(persister, entity, transientEntities);
/* 227:    */     }
/* 228:    */     finally
/* 229:    */     {
/* 230:348 */       session.getPersistenceContext().decrementCascadeLevel();
/* 231:349 */       session.setCacheMode(cacheMode);
/* 232:    */     }
/* 233:    */   }
/* 234:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.internal.DefaultDeleteEventListener
 * JD-Core Version:    0.7.0.1
 */