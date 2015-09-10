/*   1:    */ package org.hibernate.engine.internal;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.hibernate.AssertionFailure;
/*   5:    */ import org.hibernate.CacheMode;
/*   6:    */ import org.hibernate.HibernateException;
/*   7:    */ import org.hibernate.LockMode;
/*   8:    */ import org.hibernate.bytecode.instrumentation.spi.LazyPropertyInitializer;
/*   9:    */ import org.hibernate.cache.spi.CacheKey;
/*  10:    */ import org.hibernate.cache.spi.EntityRegion;
/*  11:    */ import org.hibernate.cache.spi.access.EntityRegionAccessStrategy;
/*  12:    */ import org.hibernate.cache.spi.entry.CacheEntry;
/*  13:    */ import org.hibernate.cache.spi.entry.CacheEntryStructure;
/*  14:    */ import org.hibernate.cfg.Settings;
/*  15:    */ import org.hibernate.engine.spi.EntityEntry;
/*  16:    */ import org.hibernate.engine.spi.EntityKey;
/*  17:    */ import org.hibernate.engine.spi.PersistenceContext;
/*  18:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  19:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  20:    */ import org.hibernate.engine.spi.Status;
/*  21:    */ import org.hibernate.event.service.spi.EventListenerGroup;
/*  22:    */ import org.hibernate.event.service.spi.EventListenerRegistry;
/*  23:    */ import org.hibernate.event.spi.EventType;
/*  24:    */ import org.hibernate.event.spi.PostLoadEvent;
/*  25:    */ import org.hibernate.event.spi.PostLoadEventListener;
/*  26:    */ import org.hibernate.event.spi.PreLoadEvent;
/*  27:    */ import org.hibernate.event.spi.PreLoadEventListener;
/*  28:    */ import org.hibernate.internal.CoreMessageLogger;
/*  29:    */ import org.hibernate.persister.entity.EntityPersister;
/*  30:    */ import org.hibernate.pretty.MessageHelper;
/*  31:    */ import org.hibernate.property.BackrefPropertyAccessor;
/*  32:    */ import org.hibernate.proxy.HibernateProxy;
/*  33:    */ import org.hibernate.proxy.LazyInitializer;
/*  34:    */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  35:    */ import org.hibernate.stat.Statistics;
/*  36:    */ import org.hibernate.stat.spi.StatisticsImplementor;
/*  37:    */ import org.hibernate.type.Type;
/*  38:    */ import org.hibernate.type.TypeHelper;
/*  39:    */ import org.hibernate.type.VersionType;
/*  40:    */ import org.jboss.logging.Logger;
/*  41:    */ 
/*  42:    */ public final class TwoPhaseLoad
/*  43:    */ {
/*  44: 67 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, TwoPhaseLoad.class.getName());
/*  45:    */   
/*  46:    */   public static void postHydrate(EntityPersister persister, Serializable id, Object[] values, Object rowId, Object object, LockMode lockMode, boolean lazyPropertiesAreUnfetched, SessionImplementor session)
/*  47:    */     throws HibernateException
/*  48:    */   {
/*  49: 89 */     Object version = Versioning.getVersion(values, persister);
/*  50: 90 */     session.getPersistenceContext().addEntry(object, Status.LOADING, values, rowId, id, version, lockMode, true, persister, false, lazyPropertiesAreUnfetched);
/*  51:104 */     if ((LOG.isTraceEnabled()) && (version != null))
/*  52:    */     {
/*  53:105 */       String versionStr = persister.isVersioned() ? persister.getVersionType().toLoggableString(version, session.getFactory()) : "null";
/*  54:    */       
/*  55:    */ 
/*  56:108 */       LOG.tracev("Version: {0}", versionStr);
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   public static void initializeEntity(Object entity, boolean readOnly, SessionImplementor session, PreLoadEvent preLoadEvent, PostLoadEvent postLoadEvent)
/*  61:    */     throws HibernateException
/*  62:    */   {
/*  63:130 */     PersistenceContext persistenceContext = session.getPersistenceContext();
/*  64:131 */     EntityEntry entityEntry = persistenceContext.getEntry(entity);
/*  65:132 */     if (entityEntry == null) {
/*  66:133 */       throw new AssertionFailure("possible non-threadsafe access to the session");
/*  67:    */     }
/*  68:135 */     EntityPersister persister = entityEntry.getPersister();
/*  69:136 */     Serializable id = entityEntry.getId();
/*  70:137 */     Object[] hydratedState = entityEntry.getLoadedState();
/*  71:139 */     if (LOG.isDebugEnabled()) {
/*  72:140 */       LOG.debugf("Resolving associations for %s", MessageHelper.infoString(persister, id, session.getFactory()));
/*  73:    */     }
/*  74:146 */     Type[] types = persister.getPropertyTypes();
/*  75:147 */     for (int i = 0; i < hydratedState.length; i++)
/*  76:    */     {
/*  77:148 */       Object value = hydratedState[i];
/*  78:149 */       if ((value != LazyPropertyInitializer.UNFETCHED_PROPERTY) && (value != BackrefPropertyAccessor.UNKNOWN)) {
/*  79:150 */         hydratedState[i] = types[i].resolve(value, session, entity);
/*  80:    */       }
/*  81:    */     }
/*  82:155 */     if (session.isEventSource())
/*  83:    */     {
/*  84:156 */       preLoadEvent.setEntity(entity).setState(hydratedState).setId(id).setPersister(persister);
/*  85:    */       
/*  86:158 */       EventListenerGroup<PreLoadEventListener> listenerGroup = ((EventListenerRegistry)session.getFactory().getServiceRegistry().getService(EventListenerRegistry.class)).getEventListenerGroup(EventType.PRE_LOAD);
/*  87:163 */       for (PreLoadEventListener listener : listenerGroup.listeners()) {
/*  88:164 */         listener.onPreLoad(preLoadEvent);
/*  89:    */       }
/*  90:    */     }
/*  91:168 */     persister.setPropertyValues(entity, hydratedState);
/*  92:    */     
/*  93:170 */     SessionFactoryImplementor factory = session.getFactory();
/*  94:171 */     if ((persister.hasCache()) && (session.getCacheMode().isPutEnabled()))
/*  95:    */     {
/*  96:173 */       if (LOG.isDebugEnabled()) {
/*  97:174 */         LOG.debugf("Adding entity to second-level cache: %s", MessageHelper.infoString(persister, id, session.getFactory()));
/*  98:    */       }
/*  99:180 */       Object version = Versioning.getVersion(hydratedState, persister);
/* 100:181 */       CacheEntry entry = new CacheEntry(hydratedState, persister, entityEntry.isLoadedWithLazyPropertiesUnfetched(), version, session, entity);
/* 101:    */       
/* 102:    */ 
/* 103:    */ 
/* 104:    */ 
/* 105:    */ 
/* 106:    */ 
/* 107:    */ 
/* 108:189 */       CacheKey cacheKey = session.generateCacheKey(id, persister.getIdentifierType(), persister.getRootEntityName());
/* 109:197 */       if (session.getPersistenceContext().wasInsertedDuringTransaction(persister, id))
/* 110:    */       {
/* 111:198 */         persister.getCacheAccessStrategy().update(cacheKey, persister.getCacheEntryStructure().structure(entry), version, version);
/* 112:    */       }
/* 113:    */       else
/* 114:    */       {
/* 115:206 */         boolean put = persister.getCacheAccessStrategy().putFromLoad(cacheKey, persister.getCacheEntryStructure().structure(entry), session.getTimestamp(), version, useMinimalPuts(session, entityEntry));
/* 116:214 */         if ((put) && (factory.getStatistics().isStatisticsEnabled())) {
/* 117:215 */           factory.getStatisticsImplementor().secondLevelCachePut(persister.getCacheAccessStrategy().getRegion().getName());
/* 118:    */         }
/* 119:    */       }
/* 120:    */     }
/* 121:220 */     boolean isReallyReadOnly = readOnly;
/* 122:221 */     if (!persister.isMutable())
/* 123:    */     {
/* 124:222 */       isReallyReadOnly = true;
/* 125:    */     }
/* 126:    */     else
/* 127:    */     {
/* 128:225 */       Object proxy = persistenceContext.getProxy(entityEntry.getEntityKey());
/* 129:226 */       if (proxy != null) {
/* 130:229 */         isReallyReadOnly = ((HibernateProxy)proxy).getHibernateLazyInitializer().isReadOnly();
/* 131:    */       }
/* 132:    */     }
/* 133:232 */     if (isReallyReadOnly)
/* 134:    */     {
/* 135:237 */       persistenceContext.setEntryStatus(entityEntry, Status.READ_ONLY);
/* 136:    */     }
/* 137:    */     else
/* 138:    */     {
/* 139:241 */       TypeHelper.deepCopy(hydratedState, persister.getPropertyTypes(), persister.getPropertyUpdateability(), hydratedState, session);
/* 140:    */       
/* 141:    */ 
/* 142:    */ 
/* 143:    */ 
/* 144:    */ 
/* 145:    */ 
/* 146:248 */       persistenceContext.setEntryStatus(entityEntry, Status.MANAGED);
/* 147:    */     }
/* 148:251 */     persister.afterInitialize(entity, entityEntry.isLoadedWithLazyPropertiesUnfetched(), session);
/* 149:257 */     if (session.isEventSource())
/* 150:    */     {
/* 151:258 */       postLoadEvent.setEntity(entity).setId(id).setPersister(persister);
/* 152:    */       
/* 153:260 */       EventListenerGroup<PostLoadEventListener> listenerGroup = ((EventListenerRegistry)session.getFactory().getServiceRegistry().getService(EventListenerRegistry.class)).getEventListenerGroup(EventType.POST_LOAD);
/* 154:265 */       for (PostLoadEventListener listener : listenerGroup.listeners()) {
/* 155:266 */         listener.onPostLoad(postLoadEvent);
/* 156:    */       }
/* 157:    */     }
/* 158:270 */     if (LOG.isDebugEnabled()) {
/* 159:271 */       LOG.debugf("Done materializing entity %s", MessageHelper.infoString(persister, id, session.getFactory()));
/* 160:    */     }
/* 161:277 */     if (factory.getStatistics().isStatisticsEnabled()) {
/* 162:278 */       factory.getStatisticsImplementor().loadEntity(persister.getEntityName());
/* 163:    */     }
/* 164:    */   }
/* 165:    */   
/* 166:    */   private static boolean useMinimalPuts(SessionImplementor session, EntityEntry entityEntry)
/* 167:    */   {
/* 168:284 */     return ((session.getFactory().getSettings().isMinimalPutsEnabled()) && (session.getCacheMode() != CacheMode.REFRESH)) || ((entityEntry.getPersister().hasLazyProperties()) && (entityEntry.isLoadedWithLazyPropertiesUnfetched()) && (entityEntry.getPersister().isLazyPropertiesCacheable()));
/* 169:    */   }
/* 170:    */   
/* 171:    */   public static void addUninitializedEntity(EntityKey key, Object object, EntityPersister persister, LockMode lockMode, boolean lazyPropertiesAreUnfetched, SessionImplementor session)
/* 172:    */   {
/* 173:306 */     session.getPersistenceContext().addEntity(object, Status.LOADING, null, key, null, lockMode, true, persister, false, lazyPropertiesAreUnfetched);
/* 174:    */   }
/* 175:    */   
/* 176:    */   public static void addUninitializedCachedEntity(EntityKey key, Object object, EntityPersister persister, LockMode lockMode, boolean lazyPropertiesAreUnfetched, Object version, SessionImplementor session)
/* 177:    */   {
/* 178:329 */     session.getPersistenceContext().addEntity(object, Status.LOADING, null, key, version, lockMode, true, persister, false, lazyPropertiesAreUnfetched);
/* 179:    */   }
/* 180:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.internal.TwoPhaseLoad
 * JD-Core Version:    0.7.0.1
 */