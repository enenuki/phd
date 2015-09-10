/*   1:    */ package org.hibernate.action.internal;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.hibernate.AssertionFailure;
/*   5:    */ import org.hibernate.HibernateException;
/*   6:    */ import org.hibernate.cache.CacheException;
/*   7:    */ import org.hibernate.cache.spi.CacheKey;
/*   8:    */ import org.hibernate.cache.spi.EntityRegion;
/*   9:    */ import org.hibernate.cache.spi.access.EntityRegionAccessStrategy;
/*  10:    */ import org.hibernate.cache.spi.access.SoftLock;
/*  11:    */ import org.hibernate.cache.spi.entry.CacheEntry;
/*  12:    */ import org.hibernate.cache.spi.entry.CacheEntryStructure;
/*  13:    */ import org.hibernate.engine.internal.Versioning;
/*  14:    */ import org.hibernate.engine.spi.EntityEntry;
/*  15:    */ import org.hibernate.engine.spi.PersistenceContext;
/*  16:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  17:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  18:    */ import org.hibernate.engine.spi.Status;
/*  19:    */ import org.hibernate.event.service.spi.EventListenerGroup;
/*  20:    */ import org.hibernate.event.spi.EventType;
/*  21:    */ import org.hibernate.event.spi.PostUpdateEvent;
/*  22:    */ import org.hibernate.event.spi.PostUpdateEventListener;
/*  23:    */ import org.hibernate.event.spi.PreUpdateEvent;
/*  24:    */ import org.hibernate.event.spi.PreUpdateEventListener;
/*  25:    */ import org.hibernate.persister.entity.EntityPersister;
/*  26:    */ import org.hibernate.stat.Statistics;
/*  27:    */ import org.hibernate.stat.spi.StatisticsImplementor;
/*  28:    */ import org.hibernate.type.TypeHelper;
/*  29:    */ 
/*  30:    */ public final class EntityUpdateAction
/*  31:    */   extends EntityAction
/*  32:    */ {
/*  33:    */   private final Object[] state;
/*  34:    */   private final Object[] previousState;
/*  35:    */   private final Object previousVersion;
/*  36:    */   private final int[] dirtyFields;
/*  37:    */   private final boolean hasDirtyCollection;
/*  38:    */   private final Object rowId;
/*  39:    */   private Object nextVersion;
/*  40:    */   private Object cacheEntry;
/*  41:    */   private SoftLock lock;
/*  42:    */   
/*  43:    */   public EntityUpdateAction(Serializable id, Object[] state, int[] dirtyProperties, boolean hasDirtyCollection, Object[] previousState, Object previousVersion, Object nextVersion, Object instance, Object rowId, EntityPersister persister, SessionImplementor session)
/*  44:    */     throws HibernateException
/*  45:    */   {
/*  46: 71 */     super(session, id, instance, persister);
/*  47: 72 */     this.state = state;
/*  48: 73 */     this.previousState = previousState;
/*  49: 74 */     this.previousVersion = previousVersion;
/*  50: 75 */     this.nextVersion = nextVersion;
/*  51: 76 */     this.dirtyFields = dirtyProperties;
/*  52: 77 */     this.hasDirtyCollection = hasDirtyCollection;
/*  53: 78 */     this.rowId = rowId;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void execute()
/*  57:    */     throws HibernateException
/*  58:    */   {
/*  59: 83 */     Serializable id = getId();
/*  60: 84 */     EntityPersister persister = getPersister();
/*  61: 85 */     SessionImplementor session = getSession();
/*  62: 86 */     Object instance = getInstance();
/*  63:    */     
/*  64: 88 */     boolean veto = preUpdate();
/*  65:    */     
/*  66: 90 */     SessionFactoryImplementor factory = getSession().getFactory();
/*  67: 91 */     Object previousVersion = this.previousVersion;
/*  68: 92 */     if (persister.isVersionPropertyGenerated()) {
/*  69: 96 */       previousVersion = persister.getVersion(instance);
/*  70:    */     }
/*  71:    */     CacheKey ck;
/*  72:100 */     if (persister.hasCache())
/*  73:    */     {
/*  74:101 */       CacheKey ck = session.generateCacheKey(id, persister.getIdentifierType(), persister.getRootEntityName());
/*  75:    */       
/*  76:    */ 
/*  77:    */ 
/*  78:    */ 
/*  79:106 */       this.lock = persister.getCacheAccessStrategy().lockItem(ck, previousVersion);
/*  80:    */     }
/*  81:    */     else
/*  82:    */     {
/*  83:109 */       ck = null;
/*  84:    */     }
/*  85:112 */     if (!veto) {
/*  86:113 */       persister.update(id, this.state, this.dirtyFields, this.hasDirtyCollection, this.previousState, previousVersion, instance, this.rowId, session);
/*  87:    */     }
/*  88:126 */     EntityEntry entry = getSession().getPersistenceContext().getEntry(instance);
/*  89:127 */     if (entry == null) {
/*  90:128 */       throw new AssertionFailure("possible nonthreadsafe access to session");
/*  91:    */     }
/*  92:131 */     if ((entry.getStatus() == Status.MANAGED) || (persister.isVersionPropertyGenerated()))
/*  93:    */     {
/*  94:135 */       TypeHelper.deepCopy(this.state, persister.getPropertyTypes(), persister.getPropertyCheckability(), this.state, session);
/*  95:142 */       if (persister.hasUpdateGeneratedProperties())
/*  96:    */       {
/*  97:145 */         persister.processUpdateGeneratedProperties(id, instance, this.state, session);
/*  98:146 */         if (persister.isVersionPropertyGenerated()) {
/*  99:147 */           this.nextVersion = Versioning.getVersion(this.state, persister);
/* 100:    */         }
/* 101:    */       }
/* 102:152 */       entry.postUpdate(instance, this.state, this.nextVersion);
/* 103:    */     }
/* 104:155 */     if (persister.hasCache()) {
/* 105:156 */       if ((persister.isCacheInvalidationRequired()) || (entry.getStatus() != Status.MANAGED))
/* 106:    */       {
/* 107:157 */         persister.getCacheAccessStrategy().remove(ck);
/* 108:    */       }
/* 109:    */       else
/* 110:    */       {
/* 111:161 */         CacheEntry ce = new CacheEntry(this.state, persister, persister.hasUninitializedLazyProperties(instance), this.nextVersion, getSession(), instance);
/* 112:    */         
/* 113:    */ 
/* 114:    */ 
/* 115:    */ 
/* 116:    */ 
/* 117:    */ 
/* 118:    */ 
/* 119:169 */         this.cacheEntry = persister.getCacheEntryStructure().structure(ce);
/* 120:170 */         boolean put = persister.getCacheAccessStrategy().update(ck, this.cacheEntry, this.nextVersion, previousVersion);
/* 121:171 */         if ((put) && (factory.getStatistics().isStatisticsEnabled())) {
/* 122:172 */           factory.getStatisticsImplementor().secondLevelCachePut(getPersister().getCacheAccessStrategy().getRegion().getName());
/* 123:    */         }
/* 124:    */       }
/* 125:    */     }
/* 126:177 */     postUpdate();
/* 127:179 */     if ((factory.getStatistics().isStatisticsEnabled()) && (!veto)) {
/* 128:180 */       factory.getStatisticsImplementor().updateEntity(getPersister().getEntityName());
/* 129:    */     }
/* 130:    */   }
/* 131:    */   
/* 132:    */   private boolean preUpdate()
/* 133:    */   {
/* 134:186 */     boolean veto = false;
/* 135:187 */     EventListenerGroup<PreUpdateEventListener> listenerGroup = listenerGroup(EventType.PRE_UPDATE);
/* 136:188 */     if (listenerGroup.isEmpty()) {
/* 137:189 */       return veto;
/* 138:    */     }
/* 139:191 */     PreUpdateEvent event = new PreUpdateEvent(getInstance(), getId(), this.state, this.previousState, getPersister(), eventSource());
/* 140:199 */     for (PreUpdateEventListener listener : listenerGroup.listeners()) {
/* 141:200 */       veto |= listener.onPreUpdate(event);
/* 142:    */     }
/* 143:202 */     return veto;
/* 144:    */   }
/* 145:    */   
/* 146:    */   private void postUpdate()
/* 147:    */   {
/* 148:206 */     EventListenerGroup<PostUpdateEventListener> listenerGroup = listenerGroup(EventType.POST_UPDATE);
/* 149:207 */     if (listenerGroup.isEmpty()) {
/* 150:208 */       return;
/* 151:    */     }
/* 152:210 */     PostUpdateEvent event = new PostUpdateEvent(getInstance(), getId(), this.state, this.previousState, this.dirtyFields, getPersister(), eventSource());
/* 153:219 */     for (PostUpdateEventListener listener : listenerGroup.listeners()) {
/* 154:220 */       listener.onPostUpdate(event);
/* 155:    */     }
/* 156:    */   }
/* 157:    */   
/* 158:    */   private void postCommitUpdate()
/* 159:    */   {
/* 160:225 */     EventListenerGroup<PostUpdateEventListener> listenerGroup = listenerGroup(EventType.POST_COMMIT_UPDATE);
/* 161:226 */     if (listenerGroup.isEmpty()) {
/* 162:227 */       return;
/* 163:    */     }
/* 164:229 */     PostUpdateEvent event = new PostUpdateEvent(getInstance(), getId(), this.state, this.previousState, this.dirtyFields, getPersister(), eventSource());
/* 165:238 */     for (PostUpdateEventListener listener : listenerGroup.listeners()) {
/* 166:239 */       listener.onPostUpdate(event);
/* 167:    */     }
/* 168:    */   }
/* 169:    */   
/* 170:    */   protected boolean hasPostCommitEventListeners()
/* 171:    */   {
/* 172:245 */     return !listenerGroup(EventType.POST_COMMIT_UPDATE).isEmpty();
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void doAfterTransactionCompletion(boolean success, SessionImplementor session)
/* 176:    */     throws CacheException
/* 177:    */   {
/* 178:250 */     EntityPersister persister = getPersister();
/* 179:251 */     if (persister.hasCache())
/* 180:    */     {
/* 181:253 */       CacheKey ck = getSession().generateCacheKey(getId(), persister.getIdentifierType(), persister.getRootEntityName());
/* 182:259 */       if ((success) && (this.cacheEntry != null))
/* 183:    */       {
/* 184:260 */         boolean put = persister.getCacheAccessStrategy().afterUpdate(ck, this.cacheEntry, this.nextVersion, this.previousVersion, this.lock);
/* 185:262 */         if ((put) && (getSession().getFactory().getStatistics().isStatisticsEnabled())) {
/* 186:263 */           getSession().getFactory().getStatisticsImplementor().secondLevelCachePut(getPersister().getCacheAccessStrategy().getRegion().getName());
/* 187:    */         }
/* 188:    */       }
/* 189:    */       else
/* 190:    */       {
/* 191:267 */         persister.getCacheAccessStrategy().unlockItem(ck, this.lock);
/* 192:    */       }
/* 193:    */     }
/* 194:270 */     postCommitUpdate();
/* 195:    */   }
/* 196:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.action.internal.EntityUpdateAction
 * JD-Core Version:    0.7.0.1
 */