/*   1:    */ package org.hibernate.event.internal;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Map;
/*   5:    */ import java.util.Map.Entry;
/*   6:    */ import org.hibernate.HibernateException;
/*   7:    */ import org.hibernate.Interceptor;
/*   8:    */ import org.hibernate.action.internal.CollectionRecreateAction;
/*   9:    */ import org.hibernate.action.internal.CollectionRemoveAction;
/*  10:    */ import org.hibernate.action.internal.CollectionUpdateAction;
/*  11:    */ import org.hibernate.collection.spi.PersistentCollection;
/*  12:    */ import org.hibernate.engine.internal.Cascade;
/*  13:    */ import org.hibernate.engine.internal.Collections;
/*  14:    */ import org.hibernate.engine.jdbc.spi.JdbcCoordinator;
/*  15:    */ import org.hibernate.engine.spi.ActionQueue;
/*  16:    */ import org.hibernate.engine.spi.BatchFetchQueue;
/*  17:    */ import org.hibernate.engine.spi.CascadingAction;
/*  18:    */ import org.hibernate.engine.spi.CollectionEntry;
/*  19:    */ import org.hibernate.engine.spi.CollectionKey;
/*  20:    */ import org.hibernate.engine.spi.EntityEntry;
/*  21:    */ import org.hibernate.engine.spi.PersistenceContext;
/*  22:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  23:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  24:    */ import org.hibernate.engine.spi.Status;
/*  25:    */ import org.hibernate.engine.transaction.spi.TransactionCoordinator;
/*  26:    */ import org.hibernate.event.service.spi.EventListenerGroup;
/*  27:    */ import org.hibernate.event.service.spi.EventListenerRegistry;
/*  28:    */ import org.hibernate.event.spi.EventSource;
/*  29:    */ import org.hibernate.event.spi.EventType;
/*  30:    */ import org.hibernate.event.spi.FlushEntityEvent;
/*  31:    */ import org.hibernate.event.spi.FlushEntityEventListener;
/*  32:    */ import org.hibernate.event.spi.FlushEvent;
/*  33:    */ import org.hibernate.internal.CoreMessageLogger;
/*  34:    */ import org.hibernate.internal.util.EntityPrinter;
/*  35:    */ import org.hibernate.internal.util.collections.IdentityMap;
/*  36:    */ import org.hibernate.internal.util.collections.LazyIterator;
/*  37:    */ import org.hibernate.persister.entity.EntityPersister;
/*  38:    */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  39:    */ import org.jboss.logging.Logger;
/*  40:    */ 
/*  41:    */ public abstract class AbstractFlushingEventListener
/*  42:    */   implements Serializable
/*  43:    */ {
/*  44: 65 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, AbstractFlushingEventListener.class.getName());
/*  45:    */   
/*  46:    */   protected void flushEverythingToExecutions(FlushEvent event)
/*  47:    */     throws HibernateException
/*  48:    */   {
/*  49: 81 */     LOG.trace("Flushing session");
/*  50:    */     
/*  51: 83 */     EventSource session = event.getSession();
/*  52:    */     
/*  53: 85 */     PersistenceContext persistenceContext = session.getPersistenceContext();
/*  54: 86 */     session.getInterceptor().preFlush(new LazyIterator(persistenceContext.getEntitiesByKey()));
/*  55:    */     
/*  56: 88 */     prepareEntityFlushes(session, persistenceContext);
/*  57:    */     
/*  58:    */ 
/*  59:    */ 
/*  60: 92 */     prepareCollectionFlushes(persistenceContext);
/*  61:    */     
/*  62:    */ 
/*  63:    */ 
/*  64:    */ 
/*  65: 97 */     persistenceContext.setFlushing(true);
/*  66:    */     try
/*  67:    */     {
/*  68: 99 */       flushEntities(event, persistenceContext);
/*  69:100 */       flushCollections(session, persistenceContext);
/*  70:    */     }
/*  71:    */     finally
/*  72:    */     {
/*  73:103 */       persistenceContext.setFlushing(false);
/*  74:    */     }
/*  75:107 */     logFlushResults(event);
/*  76:    */   }
/*  77:    */   
/*  78:    */   private void logFlushResults(FlushEvent event)
/*  79:    */   {
/*  80:112 */     if (!LOG.isDebugEnabled()) {
/*  81:113 */       return;
/*  82:    */     }
/*  83:115 */     EventSource session = event.getSession();
/*  84:116 */     PersistenceContext persistenceContext = session.getPersistenceContext();
/*  85:117 */     LOG.debugf("Flushed: %s insertions, %s updates, %s deletions to %s objects", new Object[] { Integer.valueOf(session.getActionQueue().numberOfInsertions()), Integer.valueOf(session.getActionQueue().numberOfUpdates()), Integer.valueOf(session.getActionQueue().numberOfDeletions()), Integer.valueOf(persistenceContext.getEntityEntries().size()) });
/*  86:    */     
/*  87:    */ 
/*  88:    */ 
/*  89:    */ 
/*  90:    */ 
/*  91:    */ 
/*  92:124 */     LOG.debugf("Flushed: %s (re)creations, %s updates, %s removals to %s collections", new Object[] { Integer.valueOf(session.getActionQueue().numberOfCollectionCreations()), Integer.valueOf(session.getActionQueue().numberOfCollectionUpdates()), Integer.valueOf(session.getActionQueue().numberOfCollectionRemovals()), Integer.valueOf(persistenceContext.getCollectionEntries().size()) });
/*  93:    */     
/*  94:    */ 
/*  95:    */ 
/*  96:    */ 
/*  97:    */ 
/*  98:    */ 
/*  99:131 */     new EntityPrinter(session.getFactory()).toString(persistenceContext.getEntitiesByKey().entrySet());
/* 100:    */   }
/* 101:    */   
/* 102:    */   private void prepareEntityFlushes(EventSource session, PersistenceContext persistenceContext)
/* 103:    */     throws HibernateException
/* 104:    */   {
/* 105:143 */     LOG.debug("Processing flush-time cascades");
/* 106:    */     
/* 107:145 */     Object anything = getAnything();
/* 108:147 */     for (Map.Entry me : IdentityMap.concurrentEntries(persistenceContext.getEntityEntries()))
/* 109:    */     {
/* 110:148 */       EntityEntry entry = (EntityEntry)me.getValue();
/* 111:149 */       Status status = entry.getStatus();
/* 112:150 */       if ((status == Status.MANAGED) || (status == Status.SAVING) || (status == Status.READ_ONLY)) {
/* 113:151 */         cascadeOnFlush(session, entry.getPersister(), me.getKey(), anything);
/* 114:    */       }
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   private void cascadeOnFlush(EventSource session, EntityPersister persister, Object object, Object anything)
/* 119:    */     throws HibernateException
/* 120:    */   {
/* 121:158 */     session.getPersistenceContext().incrementCascadeLevel();
/* 122:    */     try
/* 123:    */     {
/* 124:160 */       new Cascade(getCascadingAction(), 0, session).cascade(persister, object, anything);
/* 125:    */     }
/* 126:    */     finally
/* 127:    */     {
/* 128:164 */       session.getPersistenceContext().decrementCascadeLevel();
/* 129:    */     }
/* 130:    */   }
/* 131:    */   
/* 132:    */   protected Object getAnything()
/* 133:    */   {
/* 134:168 */     return null;
/* 135:    */   }
/* 136:    */   
/* 137:    */   protected CascadingAction getCascadingAction()
/* 138:    */   {
/* 139:171 */     return CascadingAction.SAVE_UPDATE;
/* 140:    */   }
/* 141:    */   
/* 142:    */   private void prepareCollectionFlushes(PersistenceContext persistenceContext)
/* 143:    */     throws HibernateException
/* 144:    */   {
/* 145:183 */     LOG.debug("Dirty checking collections");
/* 146:186 */     for (Map.Entry<PersistentCollection, CollectionEntry> entry : IdentityMap.concurrentEntries(persistenceContext.getCollectionEntries())) {
/* 147:187 */       ((CollectionEntry)entry.getValue()).preFlush((PersistentCollection)entry.getKey());
/* 148:    */     }
/* 149:    */   }
/* 150:    */   
/* 151:    */   private void flushEntities(FlushEvent event, PersistenceContext persistenceContext)
/* 152:    */     throws HibernateException
/* 153:    */   {
/* 154:198 */     LOG.trace("Flushing entities and processing referenced collections");
/* 155:    */     
/* 156:200 */     EventSource source = event.getSession();
/* 157:201 */     Iterable<FlushEntityEventListener> flushListeners = ((EventListenerRegistry)source.getFactory().getServiceRegistry().getService(EventListenerRegistry.class)).getEventListenerGroup(EventType.FLUSH_ENTITY).listeners();
/* 158:    */     FlushEntityEvent entityEvent;
/* 159:215 */     for (Map.Entry me : IdentityMap.concurrentEntries(persistenceContext.getEntityEntries()))
/* 160:    */     {
/* 161:219 */       EntityEntry entry = (EntityEntry)me.getValue();
/* 162:220 */       Status status = entry.getStatus();
/* 163:222 */       if ((status != Status.LOADING) && (status != Status.GONE))
/* 164:    */       {
/* 165:223 */         entityEvent = new FlushEntityEvent(source, me.getKey(), entry);
/* 166:224 */         for (FlushEntityEventListener listener : flushListeners) {
/* 167:225 */           listener.onFlushEntity(entityEvent);
/* 168:    */         }
/* 169:    */       }
/* 170:    */     }
/* 171:230 */     source.getActionQueue().sortActions();
/* 172:    */   }
/* 173:    */   
/* 174:    */   private void flushCollections(EventSource session, PersistenceContext persistenceContext)
/* 175:    */     throws HibernateException
/* 176:    */   {
/* 177:239 */     LOG.trace("Processing unreferenced collections");
/* 178:242 */     for (Map.Entry<PersistentCollection, CollectionEntry> me : IdentityMap.concurrentEntries(persistenceContext.getCollectionEntries()))
/* 179:    */     {
/* 180:243 */       CollectionEntry ce = (CollectionEntry)me.getValue();
/* 181:244 */       if ((!ce.isReached()) && (!ce.isIgnore())) {
/* 182:245 */         Collections.processUnreachableCollection((PersistentCollection)me.getKey(), session);
/* 183:    */       }
/* 184:    */     }
/* 185:251 */     LOG.trace("Scheduling collection removes/(re)creates/updates");
/* 186:    */     
/* 187:253 */     ActionQueue actionQueue = session.getActionQueue();
/* 188:255 */     for (Map.Entry<PersistentCollection, CollectionEntry> me : IdentityMap.concurrentEntries(persistenceContext.getCollectionEntries()))
/* 189:    */     {
/* 190:256 */       PersistentCollection coll = (PersistentCollection)me.getKey();
/* 191:257 */       CollectionEntry ce = (CollectionEntry)me.getValue();
/* 192:259 */       if (ce.isDorecreate())
/* 193:    */       {
/* 194:260 */         session.getInterceptor().onCollectionRecreate(coll, ce.getCurrentKey());
/* 195:261 */         actionQueue.addAction(new CollectionRecreateAction(coll, ce.getCurrentPersister(), ce.getCurrentKey(), session));
/* 196:    */       }
/* 197:270 */       if (ce.isDoremove())
/* 198:    */       {
/* 199:271 */         session.getInterceptor().onCollectionRemove(coll, ce.getLoadedKey());
/* 200:272 */         actionQueue.addAction(new CollectionRemoveAction(coll, ce.getLoadedPersister(), ce.getLoadedKey(), ce.isSnapshotEmpty(coll), session));
/* 201:    */       }
/* 202:282 */       if (ce.isDoupdate())
/* 203:    */       {
/* 204:283 */         session.getInterceptor().onCollectionUpdate(coll, ce.getLoadedKey());
/* 205:284 */         actionQueue.addAction(new CollectionUpdateAction(coll, ce.getLoadedPersister(), ce.getLoadedKey(), ce.isSnapshotEmpty(coll), session));
/* 206:    */       }
/* 207:    */     }
/* 208:297 */     actionQueue.sortCollectionActions();
/* 209:    */   }
/* 210:    */   
/* 211:    */   protected void performExecutions(EventSource session)
/* 212:    */     throws HibernateException
/* 213:    */   {
/* 214:315 */     LOG.trace("Executing flush");
/* 215:    */     try
/* 216:    */     {
/* 217:318 */       session.getTransactionCoordinator().getJdbcCoordinator().flushBeginning();
/* 218:    */       
/* 219:    */ 
/* 220:    */ 
/* 221:322 */       session.getActionQueue().prepareActions();
/* 222:323 */       session.getActionQueue().executeActions();
/* 223:    */     }
/* 224:    */     finally
/* 225:    */     {
/* 226:326 */       session.getTransactionCoordinator().getJdbcCoordinator().flushEnding();
/* 227:    */     }
/* 228:    */   }
/* 229:    */   
/* 230:    */   protected void postFlush(SessionImplementor session)
/* 231:    */     throws HibernateException
/* 232:    */   {
/* 233:342 */     LOG.trace("Post flush");
/* 234:    */     
/* 235:344 */     PersistenceContext persistenceContext = session.getPersistenceContext();
/* 236:345 */     persistenceContext.getCollectionsByKey().clear();
/* 237:346 */     persistenceContext.getBatchFetchQueue().clearSubselects();
/* 238:349 */     for (Map.Entry<PersistentCollection, CollectionEntry> me : IdentityMap.concurrentEntries(persistenceContext.getCollectionEntries()))
/* 239:    */     {
/* 240:350 */       CollectionEntry collectionEntry = (CollectionEntry)me.getValue();
/* 241:351 */       PersistentCollection persistentCollection = (PersistentCollection)me.getKey();
/* 242:352 */       collectionEntry.postFlush(persistentCollection);
/* 243:353 */       if (collectionEntry.getLoadedPersister() == null)
/* 244:    */       {
/* 245:356 */         persistenceContext.getCollectionEntries().remove(persistentCollection);
/* 246:    */       }
/* 247:    */       else
/* 248:    */       {
/* 249:361 */         CollectionKey collectionKey = new CollectionKey(collectionEntry.getLoadedPersister(), collectionEntry.getLoadedKey());
/* 250:    */         
/* 251:    */ 
/* 252:    */ 
/* 253:365 */         persistenceContext.getCollectionsByKey().put(collectionKey, persistentCollection);
/* 254:    */       }
/* 255:    */     }
/* 256:369 */     session.getInterceptor().postFlush(new LazyIterator(persistenceContext.getEntitiesByKey()));
/* 257:    */   }
/* 258:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.internal.AbstractFlushingEventListener
 * JD-Core Version:    0.7.0.1
 */