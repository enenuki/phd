/*   1:    */ package org.hibernate.action.internal;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.hibernate.action.spi.AfterTransactionCompletionProcess;
/*   5:    */ import org.hibernate.action.spi.BeforeTransactionCompletionProcess;
/*   6:    */ import org.hibernate.action.spi.Executable;
/*   7:    */ import org.hibernate.cache.CacheException;
/*   8:    */ import org.hibernate.cache.spi.CacheKey;
/*   9:    */ import org.hibernate.cache.spi.access.CollectionRegionAccessStrategy;
/*  10:    */ import org.hibernate.cache.spi.access.SoftLock;
/*  11:    */ import org.hibernate.collection.spi.PersistentCollection;
/*  12:    */ import org.hibernate.engine.spi.EntityEntry;
/*  13:    */ import org.hibernate.engine.spi.PersistenceContext;
/*  14:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  15:    */ import org.hibernate.engine.spi.SessionImplementor;
/*  16:    */ import org.hibernate.event.service.spi.EventListenerGroup;
/*  17:    */ import org.hibernate.event.service.spi.EventListenerRegistry;
/*  18:    */ import org.hibernate.event.spi.EventSource;
/*  19:    */ import org.hibernate.event.spi.EventType;
/*  20:    */ import org.hibernate.internal.util.StringHelper;
/*  21:    */ import org.hibernate.persister.collection.CollectionPersister;
/*  22:    */ import org.hibernate.pretty.MessageHelper;
/*  23:    */ import org.hibernate.service.spi.ServiceRegistryImplementor;
/*  24:    */ import org.hibernate.type.Type;
/*  25:    */ 
/*  26:    */ public abstract class CollectionAction
/*  27:    */   implements Executable, Serializable, Comparable
/*  28:    */ {
/*  29:    */   private transient CollectionPersister persister;
/*  30:    */   private transient SessionImplementor session;
/*  31:    */   private final PersistentCollection collection;
/*  32:    */   private final Serializable key;
/*  33:    */   private final String collectionRole;
/*  34:    */   private AfterTransactionCompletionProcess afterTransactionProcess;
/*  35:    */   
/*  36:    */   public CollectionAction(CollectionPersister persister, PersistentCollection collection, Serializable key, SessionImplementor session)
/*  37:    */   {
/*  38: 62 */     this.persister = persister;
/*  39: 63 */     this.session = session;
/*  40: 64 */     this.key = key;
/*  41: 65 */     this.collectionRole = persister.getRole();
/*  42: 66 */     this.collection = collection;
/*  43:    */   }
/*  44:    */   
/*  45:    */   protected PersistentCollection getCollection()
/*  46:    */   {
/*  47: 70 */     return this.collection;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void afterDeserialize(SessionImplementor session)
/*  51:    */   {
/*  52: 79 */     if ((this.session != null) || (this.persister != null)) {
/*  53: 80 */       throw new IllegalStateException("already attached to a session.");
/*  54:    */     }
/*  55: 84 */     if (session != null)
/*  56:    */     {
/*  57: 85 */       this.session = session;
/*  58: 86 */       this.persister = session.getFactory().getCollectionPersister(this.collectionRole);
/*  59:    */     }
/*  60:    */   }
/*  61:    */   
/*  62:    */   public final void beforeExecutions()
/*  63:    */     throws CacheException
/*  64:    */   {
/*  65: 95 */     if (this.persister.hasCache())
/*  66:    */     {
/*  67: 96 */       CacheKey ck = this.session.generateCacheKey(this.key, this.persister.getKeyType(), this.persister.getRole());
/*  68:    */       
/*  69:    */ 
/*  70:    */ 
/*  71:    */ 
/*  72:101 */       SoftLock lock = this.persister.getCacheAccessStrategy().lockItem(ck, null);
/*  73:    */       
/*  74:103 */       this.afterTransactionProcess = new CacheCleanupProcess(this.key, this.persister, lock, null);
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   public BeforeTransactionCompletionProcess getBeforeTransactionCompletionProcess()
/*  79:    */   {
/*  80:109 */     return null;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public AfterTransactionCompletionProcess getAfterTransactionCompletionProcess()
/*  84:    */   {
/*  85:116 */     return this.afterTransactionProcess;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public Serializable[] getPropertySpaces()
/*  89:    */   {
/*  90:121 */     return this.persister.getCollectionSpaces();
/*  91:    */   }
/*  92:    */   
/*  93:    */   protected final CollectionPersister getPersister()
/*  94:    */   {
/*  95:125 */     return this.persister;
/*  96:    */   }
/*  97:    */   
/*  98:    */   protected final Serializable getKey()
/*  99:    */   {
/* 100:129 */     Serializable finalKey = this.key;
/* 101:130 */     if ((this.key instanceof DelayedPostInsertIdentifier))
/* 102:    */     {
/* 103:132 */       finalKey = this.session.getPersistenceContext().getEntry(this.collection.getOwner()).getId();
/* 104:133 */       if (finalKey != this.key) {}
/* 105:    */     }
/* 106:138 */     return finalKey;
/* 107:    */   }
/* 108:    */   
/* 109:    */   protected final SessionImplementor getSession()
/* 110:    */   {
/* 111:142 */     return this.session;
/* 112:    */   }
/* 113:    */   
/* 114:    */   protected final void evict()
/* 115:    */     throws CacheException
/* 116:    */   {
/* 117:146 */     if (this.persister.hasCache())
/* 118:    */     {
/* 119:147 */       CacheKey ck = this.session.generateCacheKey(this.key, this.persister.getKeyType(), this.persister.getRole());
/* 120:    */       
/* 121:    */ 
/* 122:    */ 
/* 123:    */ 
/* 124:152 */       this.persister.getCacheAccessStrategy().remove(ck);
/* 125:    */     }
/* 126:    */   }
/* 127:    */   
/* 128:    */   public String toString()
/* 129:    */   {
/* 130:158 */     return StringHelper.unqualify(getClass().getName()) + MessageHelper.infoString(this.collectionRole, this.key);
/* 131:    */   }
/* 132:    */   
/* 133:    */   public int compareTo(Object other)
/* 134:    */   {
/* 135:164 */     CollectionAction action = (CollectionAction)other;
/* 136:    */     
/* 137:166 */     int roleComparison = this.collectionRole.compareTo(action.collectionRole);
/* 138:167 */     if (roleComparison != 0) {
/* 139:168 */       return roleComparison;
/* 140:    */     }
/* 141:172 */     return this.persister.getKeyType().compare(this.key, action.key);
/* 142:    */   }
/* 143:    */   
/* 144:    */   private static class CacheCleanupProcess
/* 145:    */     implements AfterTransactionCompletionProcess
/* 146:    */   {
/* 147:    */     private final Serializable key;
/* 148:    */     private final CollectionPersister persister;
/* 149:    */     private final SoftLock lock;
/* 150:    */     
/* 151:    */     private CacheCleanupProcess(Serializable key, CollectionPersister persister, SoftLock lock)
/* 152:    */     {
/* 153:183 */       this.key = key;
/* 154:184 */       this.persister = persister;
/* 155:185 */       this.lock = lock;
/* 156:    */     }
/* 157:    */     
/* 158:    */     public void doAfterTransactionCompletion(boolean success, SessionImplementor session)
/* 159:    */     {
/* 160:190 */       CacheKey ck = session.generateCacheKey(this.key, this.persister.getKeyType(), this.persister.getRole());
/* 161:    */       
/* 162:    */ 
/* 163:    */ 
/* 164:    */ 
/* 165:195 */       this.persister.getCacheAccessStrategy().unlockItem(ck, this.lock);
/* 166:    */     }
/* 167:    */   }
/* 168:    */   
/* 169:    */   protected <T> EventListenerGroup<T> listenerGroup(EventType<T> eventType)
/* 170:    */   {
/* 171:200 */     return ((EventListenerRegistry)getSession().getFactory().getServiceRegistry().getService(EventListenerRegistry.class)).getEventListenerGroup(eventType);
/* 172:    */   }
/* 173:    */   
/* 174:    */   protected EventSource eventSource()
/* 175:    */   {
/* 176:208 */     return (EventSource)getSession();
/* 177:    */   }
/* 178:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.action.internal.CollectionAction
 * JD-Core Version:    0.7.0.1
 */