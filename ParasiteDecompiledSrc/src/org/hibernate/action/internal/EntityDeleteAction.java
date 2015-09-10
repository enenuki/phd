/*   1:    */ package org.hibernate.action.internal;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.hibernate.AssertionFailure;
/*   5:    */ import org.hibernate.HibernateException;
/*   6:    */ import org.hibernate.cache.spi.CacheKey;
/*   7:    */ import org.hibernate.cache.spi.access.EntityRegionAccessStrategy;
/*   8:    */ import org.hibernate.cache.spi.access.SoftLock;
/*   9:    */ import org.hibernate.engine.spi.EntityEntry;
/*  10:    */ import org.hibernate.engine.spi.PersistenceContext;
/*  11:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  12:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  13:    */ import org.hibernate.event.service.spi.EventListenerGroup;
/*  14:    */ import org.hibernate.event.spi.EventType;
/*  15:    */ import org.hibernate.event.spi.PostDeleteEvent;
/*  16:    */ import org.hibernate.event.spi.PostDeleteEventListener;
/*  17:    */ import org.hibernate.event.spi.PreDeleteEvent;
/*  18:    */ import org.hibernate.event.spi.PreDeleteEventListener;
/*  19:    */ import org.hibernate.persister.entity.EntityPersister;
/*  20:    */ import org.hibernate.stat.Statistics;
/*  21:    */ import org.hibernate.stat.spi.StatisticsImplementor;
/*  22:    */ 
/*  23:    */ public final class EntityDeleteAction
/*  24:    */   extends EntityAction
/*  25:    */ {
/*  26:    */   private final Object version;
/*  27:    */   private final boolean isCascadeDeleteEnabled;
/*  28:    */   private final Object[] state;
/*  29:    */   private SoftLock lock;
/*  30:    */   
/*  31:    */   public EntityDeleteAction(Serializable id, Object[] state, Object version, Object instance, EntityPersister persister, boolean isCascadeDeleteEnabled, SessionImplementor session)
/*  32:    */   {
/*  33: 58 */     super(session, id, instance, persister);
/*  34: 59 */     this.version = version;
/*  35: 60 */     this.isCascadeDeleteEnabled = isCascadeDeleteEnabled;
/*  36: 61 */     this.state = state;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void execute()
/*  40:    */     throws HibernateException
/*  41:    */   {
/*  42: 66 */     Serializable id = getId();
/*  43: 67 */     EntityPersister persister = getPersister();
/*  44: 68 */     SessionImplementor session = getSession();
/*  45: 69 */     Object instance = getInstance();
/*  46:    */     
/*  47: 71 */     boolean veto = preDelete();
/*  48:    */     
/*  49: 73 */     Object version = this.version;
/*  50: 74 */     if (persister.isVersionPropertyGenerated()) {
/*  51: 78 */       version = persister.getVersion(instance);
/*  52:    */     }
/*  53:    */     CacheKey ck;
/*  54: 82 */     if (persister.hasCache())
/*  55:    */     {
/*  56: 83 */       CacheKey ck = session.generateCacheKey(id, persister.getIdentifierType(), persister.getRootEntityName());
/*  57: 84 */       this.lock = persister.getCacheAccessStrategy().lockItem(ck, version);
/*  58:    */     }
/*  59:    */     else
/*  60:    */     {
/*  61: 87 */       ck = null;
/*  62:    */     }
/*  63: 90 */     if ((!this.isCascadeDeleteEnabled) && (!veto)) {
/*  64: 91 */       persister.delete(id, version, instance, session);
/*  65:    */     }
/*  66: 98 */     PersistenceContext persistenceContext = session.getPersistenceContext();
/*  67: 99 */     EntityEntry entry = persistenceContext.removeEntry(instance);
/*  68:100 */     if (entry == null) {
/*  69:101 */       throw new AssertionFailure("possible nonthreadsafe access to session");
/*  70:    */     }
/*  71:103 */     entry.postDelete();
/*  72:    */     
/*  73:105 */     persistenceContext.removeEntity(entry.getEntityKey());
/*  74:106 */     persistenceContext.removeProxy(entry.getEntityKey());
/*  75:108 */     if (persister.hasCache()) {
/*  76:109 */       persister.getCacheAccessStrategy().remove(ck);
/*  77:    */     }
/*  78:112 */     postDelete();
/*  79:114 */     if ((getSession().getFactory().getStatistics().isStatisticsEnabled()) && (!veto)) {
/*  80:115 */       getSession().getFactory().getStatisticsImplementor().deleteEntity(getPersister().getEntityName());
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   private boolean preDelete()
/*  85:    */   {
/*  86:120 */     boolean veto = false;
/*  87:121 */     EventListenerGroup<PreDeleteEventListener> listenerGroup = listenerGroup(EventType.PRE_DELETE);
/*  88:122 */     if (listenerGroup.isEmpty()) {
/*  89:123 */       return veto;
/*  90:    */     }
/*  91:125 */     PreDeleteEvent event = new PreDeleteEvent(getInstance(), getId(), this.state, getPersister(), eventSource());
/*  92:126 */     for (PreDeleteEventListener listener : listenerGroup.listeners()) {
/*  93:127 */       veto |= listener.onPreDelete(event);
/*  94:    */     }
/*  95:129 */     return veto;
/*  96:    */   }
/*  97:    */   
/*  98:    */   private void postDelete()
/*  99:    */   {
/* 100:133 */     EventListenerGroup<PostDeleteEventListener> listenerGroup = listenerGroup(EventType.POST_DELETE);
/* 101:134 */     if (listenerGroup.isEmpty()) {
/* 102:135 */       return;
/* 103:    */     }
/* 104:137 */     PostDeleteEvent event = new PostDeleteEvent(getInstance(), getId(), this.state, getPersister(), eventSource());
/* 105:144 */     for (PostDeleteEventListener listener : listenerGroup.listeners()) {
/* 106:145 */       listener.onPostDelete(event);
/* 107:    */     }
/* 108:    */   }
/* 109:    */   
/* 110:    */   private void postCommitDelete()
/* 111:    */   {
/* 112:150 */     EventListenerGroup<PostDeleteEventListener> listenerGroup = listenerGroup(EventType.POST_COMMIT_DELETE);
/* 113:151 */     if (listenerGroup.isEmpty()) {
/* 114:152 */       return;
/* 115:    */     }
/* 116:154 */     PostDeleteEvent event = new PostDeleteEvent(getInstance(), getId(), this.state, getPersister(), eventSource());
/* 117:161 */     for (PostDeleteEventListener listener : listenerGroup.listeners()) {
/* 118:162 */       listener.onPostDelete(event);
/* 119:    */     }
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void doAfterTransactionCompletion(boolean success, SessionImplementor session)
/* 123:    */     throws HibernateException
/* 124:    */   {
/* 125:168 */     if (getPersister().hasCache())
/* 126:    */     {
/* 127:169 */       CacheKey ck = getSession().generateCacheKey(getId(), getPersister().getIdentifierType(), getPersister().getRootEntityName());
/* 128:    */       
/* 129:    */ 
/* 130:    */ 
/* 131:    */ 
/* 132:174 */       getPersister().getCacheAccessStrategy().unlockItem(ck, this.lock);
/* 133:    */     }
/* 134:176 */     postCommitDelete();
/* 135:    */   }
/* 136:    */   
/* 137:    */   protected boolean hasPostCommitEventListeners()
/* 138:    */   {
/* 139:181 */     return !listenerGroup(EventType.POST_COMMIT_DELETE).isEmpty();
/* 140:    */   }
/* 141:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.action.internal.EntityDeleteAction
 * JD-Core Version:    0.7.0.1
 */