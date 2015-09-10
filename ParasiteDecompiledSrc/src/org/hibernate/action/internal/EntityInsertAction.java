/*   1:    */ package org.hibernate.action.internal;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.hibernate.AssertionFailure;
/*   5:    */ import org.hibernate.CacheMode;
/*   6:    */ import org.hibernate.HibernateException;
/*   7:    */ import org.hibernate.cache.spi.CacheKey;
/*   8:    */ import org.hibernate.cache.spi.EntityRegion;
/*   9:    */ import org.hibernate.cache.spi.access.EntityRegionAccessStrategy;
/*  10:    */ import org.hibernate.cache.spi.entry.CacheEntry;
/*  11:    */ import org.hibernate.cache.spi.entry.CacheEntryStructure;
/*  12:    */ import org.hibernate.engine.internal.Versioning;
/*  13:    */ import org.hibernate.engine.spi.EntityEntry;
/*  14:    */ import org.hibernate.engine.spi.PersistenceContext;
/*  15:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  16:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  17:    */ import org.hibernate.event.service.spi.EventListenerGroup;
/*  18:    */ import org.hibernate.event.spi.EventType;
/*  19:    */ import org.hibernate.event.spi.PostInsertEvent;
/*  20:    */ import org.hibernate.event.spi.PostInsertEventListener;
/*  21:    */ import org.hibernate.event.spi.PreInsertEvent;
/*  22:    */ import org.hibernate.event.spi.PreInsertEventListener;
/*  23:    */ import org.hibernate.persister.entity.EntityPersister;
/*  24:    */ import org.hibernate.stat.Statistics;
/*  25:    */ import org.hibernate.stat.spi.StatisticsImplementor;
/*  26:    */ 
/*  27:    */ public final class EntityInsertAction
/*  28:    */   extends EntityAction
/*  29:    */ {
/*  30:    */   private Object[] state;
/*  31:    */   private Object version;
/*  32:    */   private Object cacheEntry;
/*  33:    */   
/*  34:    */   public EntityInsertAction(Serializable id, Object[] state, Object instance, Object version, EntityPersister persister, SessionImplementor session)
/*  35:    */     throws HibernateException
/*  36:    */   {
/*  37: 57 */     super(session, id, instance, persister);
/*  38: 58 */     this.state = state;
/*  39: 59 */     this.version = version;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public Object[] getState()
/*  43:    */   {
/*  44: 63 */     return this.state;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void execute()
/*  48:    */     throws HibernateException
/*  49:    */   {
/*  50: 68 */     EntityPersister persister = getPersister();
/*  51: 69 */     SessionImplementor session = getSession();
/*  52: 70 */     Object instance = getInstance();
/*  53: 71 */     Serializable id = getId();
/*  54:    */     
/*  55: 73 */     boolean veto = preInsert();
/*  56: 78 */     if (!veto)
/*  57:    */     {
/*  58: 80 */       persister.insert(id, this.state, instance, session);
/*  59:    */       
/*  60: 82 */       EntityEntry entry = session.getPersistenceContext().getEntry(instance);
/*  61: 83 */       if (entry == null) {
/*  62: 84 */         throw new AssertionFailure("possible nonthreadsafe access to session");
/*  63:    */       }
/*  64: 87 */       entry.postInsert();
/*  65: 89 */       if (persister.hasInsertGeneratedProperties())
/*  66:    */       {
/*  67: 90 */         persister.processInsertGeneratedProperties(id, instance, this.state, session);
/*  68: 91 */         if (persister.isVersionPropertyGenerated()) {
/*  69: 92 */           this.version = Versioning.getVersion(this.state, persister);
/*  70:    */         }
/*  71: 94 */         entry.postUpdate(instance, this.state, this.version);
/*  72:    */       }
/*  73: 97 */       getSession().getPersistenceContext().registerInsertedKey(getPersister(), getId());
/*  74:    */     }
/*  75:100 */     SessionFactoryImplementor factory = getSession().getFactory();
/*  76:102 */     if (isCachePutEnabled(persister, session))
/*  77:    */     {
/*  78:104 */       CacheEntry ce = new CacheEntry(this.state, persister, persister.hasUninitializedLazyProperties(instance), this.version, session, instance);
/*  79:    */       
/*  80:    */ 
/*  81:    */ 
/*  82:    */ 
/*  83:    */ 
/*  84:    */ 
/*  85:    */ 
/*  86:    */ 
/*  87:113 */       this.cacheEntry = persister.getCacheEntryStructure().structure(ce);
/*  88:114 */       CacheKey ck = session.generateCacheKey(id, persister.getIdentifierType(), persister.getRootEntityName());
/*  89:115 */       boolean put = persister.getCacheAccessStrategy().insert(ck, this.cacheEntry, this.version);
/*  90:117 */       if ((put) && (factory.getStatistics().isStatisticsEnabled())) {
/*  91:118 */         factory.getStatisticsImplementor().secondLevelCachePut(getPersister().getCacheAccessStrategy().getRegion().getName());
/*  92:    */       }
/*  93:    */     }
/*  94:123 */     postInsert();
/*  95:125 */     if ((factory.getStatistics().isStatisticsEnabled()) && (!veto)) {
/*  96:126 */       factory.getStatisticsImplementor().insertEntity(getPersister().getEntityName());
/*  97:    */     }
/*  98:    */   }
/*  99:    */   
/* 100:    */   private void postInsert()
/* 101:    */   {
/* 102:133 */     EventListenerGroup<PostInsertEventListener> listenerGroup = listenerGroup(EventType.POST_INSERT);
/* 103:134 */     if (listenerGroup.isEmpty()) {
/* 104:135 */       return;
/* 105:    */     }
/* 106:137 */     PostInsertEvent event = new PostInsertEvent(getInstance(), getId(), this.state, getPersister(), eventSource());
/* 107:144 */     for (PostInsertEventListener listener : listenerGroup.listeners()) {
/* 108:145 */       listener.onPostInsert(event);
/* 109:    */     }
/* 110:    */   }
/* 111:    */   
/* 112:    */   private void postCommitInsert()
/* 113:    */   {
/* 114:150 */     EventListenerGroup<PostInsertEventListener> listenerGroup = listenerGroup(EventType.POST_COMMIT_INSERT);
/* 115:151 */     if (listenerGroup.isEmpty()) {
/* 116:152 */       return;
/* 117:    */     }
/* 118:154 */     PostInsertEvent event = new PostInsertEvent(getInstance(), getId(), this.state, getPersister(), eventSource());
/* 119:161 */     for (PostInsertEventListener listener : listenerGroup.listeners()) {
/* 120:162 */       listener.onPostInsert(event);
/* 121:    */     }
/* 122:    */   }
/* 123:    */   
/* 124:    */   private boolean preInsert()
/* 125:    */   {
/* 126:167 */     boolean veto = false;
/* 127:    */     
/* 128:169 */     EventListenerGroup<PreInsertEventListener> listenerGroup = listenerGroup(EventType.PRE_INSERT);
/* 129:170 */     if (listenerGroup.isEmpty()) {
/* 130:171 */       return veto;
/* 131:    */     }
/* 132:173 */     PreInsertEvent event = new PreInsertEvent(getInstance(), getId(), this.state, getPersister(), eventSource());
/* 133:174 */     for (PreInsertEventListener listener : listenerGroup.listeners()) {
/* 134:175 */       veto |= listener.onPreInsert(event);
/* 135:    */     }
/* 136:177 */     return veto;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void doAfterTransactionCompletion(boolean success, SessionImplementor session)
/* 140:    */     throws HibernateException
/* 141:    */   {
/* 142:182 */     EntityPersister persister = getPersister();
/* 143:183 */     if ((success) && (isCachePutEnabled(persister, getSession())))
/* 144:    */     {
/* 145:184 */       CacheKey ck = getSession().generateCacheKey(getId(), persister.getIdentifierType(), persister.getRootEntityName());
/* 146:185 */       boolean put = persister.getCacheAccessStrategy().afterInsert(ck, this.cacheEntry, this.version);
/* 147:187 */       if ((put) && (getSession().getFactory().getStatistics().isStatisticsEnabled())) {
/* 148:188 */         getSession().getFactory().getStatisticsImplementor().secondLevelCachePut(getPersister().getCacheAccessStrategy().getRegion().getName());
/* 149:    */       }
/* 150:    */     }
/* 151:192 */     postCommitInsert();
/* 152:    */   }
/* 153:    */   
/* 154:    */   protected boolean hasPostCommitEventListeners()
/* 155:    */   {
/* 156:197 */     return !listenerGroup(EventType.POST_COMMIT_INSERT).isEmpty();
/* 157:    */   }
/* 158:    */   
/* 159:    */   private boolean isCachePutEnabled(EntityPersister persister, SessionImplementor session)
/* 160:    */   {
/* 161:201 */     return (persister.hasCache()) && (!persister.isCacheInvalidationRequired()) && (session.getCacheMode().isPutEnabled());
/* 162:    */   }
/* 163:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.action.internal.EntityInsertAction
 * JD-Core Version:    0.7.0.1
 */