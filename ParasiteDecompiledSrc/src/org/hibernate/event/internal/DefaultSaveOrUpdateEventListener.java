/*   1:    */ package org.hibernate.event.internal;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.hibernate.AssertionFailure;
/*   5:    */ import org.hibernate.HibernateException;
/*   6:    */ import org.hibernate.LockMode;
/*   7:    */ import org.hibernate.PersistentObjectException;
/*   8:    */ import org.hibernate.TransientObjectException;
/*   9:    */ import org.hibernate.classic.Lifecycle;
/*  10:    */ import org.hibernate.engine.internal.Cascade;
/*  11:    */ import org.hibernate.engine.spi.CascadingAction;
/*  12:    */ import org.hibernate.engine.spi.EntityEntry;
/*  13:    */ import org.hibernate.engine.spi.EntityKey;
/*  14:    */ import org.hibernate.engine.spi.PersistenceContext;
/*  15:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  16:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  17:    */ import org.hibernate.engine.spi.Status;
/*  18:    */ import org.hibernate.event.spi.EventSource;
/*  19:    */ import org.hibernate.event.spi.SaveOrUpdateEvent;
/*  20:    */ import org.hibernate.event.spi.SaveOrUpdateEventListener;
/*  21:    */ import org.hibernate.internal.CoreMessageLogger;
/*  22:    */ import org.hibernate.persister.entity.EntityPersister;
/*  23:    */ import org.hibernate.pretty.MessageHelper;
/*  24:    */ import org.hibernate.proxy.HibernateProxy;
/*  25:    */ import org.hibernate.proxy.LazyInitializer;
/*  26:    */ import org.hibernate.type.Type;
/*  27:    */ import org.jboss.logging.Logger;
/*  28:    */ 
/*  29:    */ public class DefaultSaveOrUpdateEventListener
/*  30:    */   extends AbstractSaveEventListener
/*  31:    */   implements SaveOrUpdateEventListener
/*  32:    */ {
/*  33: 60 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, DefaultSaveOrUpdateEventListener.class.getName());
/*  34:    */   
/*  35:    */   public void onSaveOrUpdate(SaveOrUpdateEvent event)
/*  36:    */   {
/*  37: 68 */     SessionImplementor source = event.getSession();
/*  38: 69 */     Object object = event.getObject();
/*  39: 70 */     Serializable requestedId = event.getRequestedId();
/*  40: 72 */     if (requestedId != null) {
/*  41: 75 */       if ((object instanceof HibernateProxy)) {
/*  42: 76 */         ((HibernateProxy)object).getHibernateLazyInitializer().setIdentifier(requestedId);
/*  43:    */       }
/*  44:    */     }
/*  45: 81 */     if (reassociateIfUninitializedProxy(object, source))
/*  46:    */     {
/*  47: 82 */       LOG.trace("Reassociated uninitialized proxy");
/*  48:    */     }
/*  49:    */     else
/*  50:    */     {
/*  51: 86 */       Object entity = source.getPersistenceContext().unproxyAndReassociate(object);
/*  52: 87 */       event.setEntity(entity);
/*  53: 88 */       event.setEntry(source.getPersistenceContext().getEntry(entity));
/*  54:    */       
/*  55: 90 */       event.setResultId(performSaveOrUpdate(event));
/*  56:    */     }
/*  57:    */   }
/*  58:    */   
/*  59:    */   protected boolean reassociateIfUninitializedProxy(Object object, SessionImplementor source)
/*  60:    */   {
/*  61: 96 */     return source.getPersistenceContext().reassociateIfUninitializedProxy(object);
/*  62:    */   }
/*  63:    */   
/*  64:    */   protected Serializable performSaveOrUpdate(SaveOrUpdateEvent event)
/*  65:    */   {
/*  66:100 */     AbstractSaveEventListener.EntityState entityState = getEntityState(event.getEntity(), event.getEntityName(), event.getEntry(), event.getSession());
/*  67:107 */     switch (1.$SwitchMap$org$hibernate$event$internal$AbstractSaveEventListener$EntityState[entityState.ordinal()])
/*  68:    */     {
/*  69:    */     case 1: 
/*  70:109 */       entityIsDetached(event);
/*  71:110 */       return null;
/*  72:    */     case 2: 
/*  73:112 */       return entityIsPersistent(event);
/*  74:    */     }
/*  75:114 */     return entityIsTransient(event);
/*  76:    */   }
/*  77:    */   
/*  78:    */   protected Serializable entityIsPersistent(SaveOrUpdateEvent event)
/*  79:    */     throws HibernateException
/*  80:    */   {
/*  81:119 */     LOG.trace("Ignoring persistent instance");
/*  82:    */     
/*  83:121 */     EntityEntry entityEntry = event.getEntry();
/*  84:122 */     if (entityEntry == null) {
/*  85:123 */       throw new AssertionFailure("entity was transient or detached");
/*  86:    */     }
/*  87:127 */     if (entityEntry.getStatus() == Status.DELETED) {
/*  88:128 */       throw new AssertionFailure("entity was deleted");
/*  89:    */     }
/*  90:131 */     SessionFactoryImplementor factory = event.getSession().getFactory();
/*  91:    */     
/*  92:133 */     Serializable requestedId = event.getRequestedId();
/*  93:    */     Serializable savedId;
/*  94:    */     Serializable savedId;
/*  95:136 */     if (requestedId == null)
/*  96:    */     {
/*  97:137 */       savedId = entityEntry.getId();
/*  98:    */     }
/*  99:    */     else
/* 100:    */     {
/* 101:141 */       boolean isEqual = !entityEntry.getPersister().getIdentifierType().isEqual(requestedId, entityEntry.getId(), factory);
/* 102:144 */       if (isEqual) {
/* 103:145 */         throw new PersistentObjectException("object passed to save() was already persistent: " + MessageHelper.infoString(entityEntry.getPersister(), requestedId, factory));
/* 104:    */       }
/* 105:151 */       savedId = requestedId;
/* 106:    */     }
/* 107:155 */     if (LOG.isTraceEnabled()) {
/* 108:156 */       LOG.tracev("Object already associated with session: {0}", MessageHelper.infoString(entityEntry.getPersister(), savedId, factory));
/* 109:    */     }
/* 110:159 */     return savedId;
/* 111:    */   }
/* 112:    */   
/* 113:    */   protected Serializable entityIsTransient(SaveOrUpdateEvent event)
/* 114:    */   {
/* 115:175 */     LOG.trace("Saving transient instance");
/* 116:    */     
/* 117:177 */     EventSource source = event.getSession();
/* 118:    */     
/* 119:179 */     EntityEntry entityEntry = event.getEntry();
/* 120:180 */     if (entityEntry != null) {
/* 121:181 */       if (entityEntry.getStatus() == Status.DELETED) {
/* 122:182 */         source.forceFlush(entityEntry);
/* 123:    */       } else {
/* 124:185 */         throw new AssertionFailure("entity was persistent");
/* 125:    */       }
/* 126:    */     }
/* 127:189 */     Serializable id = saveWithGeneratedOrRequestedId(event);
/* 128:    */     
/* 129:191 */     source.getPersistenceContext().reassociateProxy(event.getObject(), id);
/* 130:    */     
/* 131:193 */     return id;
/* 132:    */   }
/* 133:    */   
/* 134:    */   protected Serializable saveWithGeneratedOrRequestedId(SaveOrUpdateEvent event)
/* 135:    */   {
/* 136:204 */     return saveWithGeneratedId(event.getEntity(), event.getEntityName(), null, event.getSession(), true);
/* 137:    */   }
/* 138:    */   
/* 139:    */   protected void entityIsDetached(SaveOrUpdateEvent event)
/* 140:    */   {
/* 141:222 */     LOG.trace("Updating detached instance");
/* 142:224 */     if (event.getSession().getPersistenceContext().isEntryFor(event.getEntity())) {
/* 143:226 */       throw new AssertionFailure("entity was persistent");
/* 144:    */     }
/* 145:229 */     Object entity = event.getEntity();
/* 146:    */     
/* 147:231 */     EntityPersister persister = event.getSession().getEntityPersister(event.getEntityName(), entity);
/* 148:    */     
/* 149:233 */     event.setRequestedId(getUpdateId(entity, persister, event.getRequestedId(), event.getSession()));
/* 150:    */     
/* 151:    */ 
/* 152:    */ 
/* 153:    */ 
/* 154:    */ 
/* 155:239 */     performUpdate(event, entity, persister);
/* 156:    */   }
/* 157:    */   
/* 158:    */   protected Serializable getUpdateId(Object entity, EntityPersister persister, Serializable requestedId, SessionImplementor session)
/* 159:    */   {
/* 160:261 */     Serializable id = persister.getIdentifier(entity, session);
/* 161:262 */     if (id == null) {
/* 162:265 */       throw new TransientObjectException("The given object has a null identifier: " + persister.getEntityName());
/* 163:    */     }
/* 164:271 */     return id;
/* 165:    */   }
/* 166:    */   
/* 167:    */   protected void performUpdate(SaveOrUpdateEvent event, Object entity, EntityPersister persister)
/* 168:    */     throws HibernateException
/* 169:    */   {
/* 170:281 */     if (!persister.isMutable()) {
/* 171:282 */       LOG.trace("Immutable instance passed to performUpdate()");
/* 172:    */     }
/* 173:285 */     if (LOG.isTraceEnabled()) {
/* 174:286 */       LOG.tracev("Updating {0}", MessageHelper.infoString(persister, event.getRequestedId(), event.getSession().getFactory()));
/* 175:    */     }
/* 176:290 */     EventSource source = event.getSession();
/* 177:291 */     EntityKey key = source.generateEntityKey(event.getRequestedId(), persister);
/* 178:    */     
/* 179:293 */     source.getPersistenceContext().checkUniqueness(key, entity);
/* 180:295 */     if (invokeUpdateLifecycle(entity, persister, source))
/* 181:    */     {
/* 182:296 */       reassociate(event, event.getObject(), event.getRequestedId(), persister);
/* 183:297 */       return;
/* 184:    */     }
/* 185:302 */     new OnUpdateVisitor(source, event.getRequestedId(), entity).process(entity, persister);
/* 186:    */     
/* 187:    */ 
/* 188:    */ 
/* 189:    */ 
/* 190:    */ 
/* 191:    */ 
/* 192:    */ 
/* 193:    */ 
/* 194:    */ 
/* 195:    */ 
/* 196:    */ 
/* 197:    */ 
/* 198:    */ 
/* 199:316 */     source.getPersistenceContext().addEntity(entity, persister.isMutable() ? Status.MANAGED : Status.READ_ONLY, null, key, persister.getVersion(entity), LockMode.NONE, true, persister, false, true);
/* 200:    */     
/* 201:    */ 
/* 202:    */ 
/* 203:    */ 
/* 204:    */ 
/* 205:    */ 
/* 206:    */ 
/* 207:    */ 
/* 208:    */ 
/* 209:    */ 
/* 210:    */ 
/* 211:    */ 
/* 212:329 */     persister.afterReassociate(entity, source);
/* 213:331 */     if (LOG.isTraceEnabled()) {
/* 214:332 */       LOG.tracev("Updating {0}", MessageHelper.infoString(persister, event.getRequestedId(), source.getFactory()));
/* 215:    */     }
/* 216:335 */     cascadeOnUpdate(event, persister, entity);
/* 217:    */   }
/* 218:    */   
/* 219:    */   protected boolean invokeUpdateLifecycle(Object entity, EntityPersister persister, EventSource source)
/* 220:    */   {
/* 221:339 */     if (persister.implementsLifecycle())
/* 222:    */     {
/* 223:340 */       LOG.debug("Calling onUpdate()");
/* 224:341 */       if (((Lifecycle)entity).onUpdate(source))
/* 225:    */       {
/* 226:342 */         LOG.debug("Update vetoed by onUpdate()");
/* 227:343 */         return true;
/* 228:    */       }
/* 229:    */     }
/* 230:346 */     return false;
/* 231:    */   }
/* 232:    */   
/* 233:    */   private void cascadeOnUpdate(SaveOrUpdateEvent event, EntityPersister persister, Object entity)
/* 234:    */   {
/* 235:358 */     EventSource source = event.getSession();
/* 236:359 */     source.getPersistenceContext().incrementCascadeLevel();
/* 237:    */     try
/* 238:    */     {
/* 239:361 */       new Cascade(CascadingAction.SAVE_UPDATE, 0, source).cascade(persister, entity);
/* 240:    */     }
/* 241:    */     finally
/* 242:    */     {
/* 243:365 */       source.getPersistenceContext().decrementCascadeLevel();
/* 244:    */     }
/* 245:    */   }
/* 246:    */   
/* 247:    */   protected CascadingAction getCascadeAction()
/* 248:    */   {
/* 249:371 */     return CascadingAction.SAVE_UPDATE;
/* 250:    */   }
/* 251:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.internal.DefaultSaveOrUpdateEventListener
 * JD-Core Version:    0.7.0.1
 */