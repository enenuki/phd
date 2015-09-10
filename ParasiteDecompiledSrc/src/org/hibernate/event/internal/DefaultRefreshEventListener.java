/*   1:    */ package org.hibernate.event.internal;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.IdentityHashMap;
/*   5:    */ import java.util.Map;
/*   6:    */ import org.hibernate.Cache;
/*   7:    */ import org.hibernate.HibernateException;
/*   8:    */ import org.hibernate.PersistentObjectException;
/*   9:    */ import org.hibernate.UnresolvableObjectException;
/*  10:    */ import org.hibernate.cache.spi.CacheKey;
/*  11:    */ import org.hibernate.cache.spi.access.EntityRegionAccessStrategy;
/*  12:    */ import org.hibernate.engine.internal.Cascade;
/*  13:    */ import org.hibernate.engine.spi.CascadingAction;
/*  14:    */ import org.hibernate.engine.spi.EntityEntry;
/*  15:    */ import org.hibernate.engine.spi.EntityKey;
/*  16:    */ import org.hibernate.engine.spi.LoadQueryInfluencers;
/*  17:    */ import org.hibernate.engine.spi.PersistenceContext;
/*  18:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  19:    */ import org.hibernate.event.spi.EventSource;
/*  20:    */ import org.hibernate.event.spi.RefreshEvent;
/*  21:    */ import org.hibernate.event.spi.RefreshEventListener;
/*  22:    */ import org.hibernate.internal.CoreMessageLogger;
/*  23:    */ import org.hibernate.persister.entity.EntityPersister;
/*  24:    */ import org.hibernate.pretty.MessageHelper;
/*  25:    */ import org.hibernate.type.CollectionType;
/*  26:    */ import org.hibernate.type.CompositeType;
/*  27:    */ import org.hibernate.type.Type;
/*  28:    */ import org.jboss.logging.Logger;
/*  29:    */ 
/*  30:    */ public class DefaultRefreshEventListener
/*  31:    */   implements RefreshEventListener
/*  32:    */ {
/*  33: 60 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, DefaultRefreshEventListener.class.getName());
/*  34:    */   
/*  35:    */   public void onRefresh(RefreshEvent event)
/*  36:    */     throws HibernateException
/*  37:    */   {
/*  38: 64 */     onRefresh(event, new IdentityHashMap(10));
/*  39:    */   }
/*  40:    */   
/*  41:    */   public void onRefresh(RefreshEvent event, Map refreshedAlready)
/*  42:    */   {
/*  43: 74 */     EventSource source = event.getSession();
/*  44:    */     
/*  45: 76 */     boolean isTransient = !source.contains(event.getObject());
/*  46: 77 */     if (source.getPersistenceContext().reassociateIfUninitializedProxy(event.getObject()))
/*  47:    */     {
/*  48: 78 */       if (isTransient) {
/*  49: 79 */         source.setReadOnly(event.getObject(), source.isDefaultReadOnly());
/*  50:    */       }
/*  51: 81 */       return;
/*  52:    */     }
/*  53: 84 */     Object object = source.getPersistenceContext().unproxyAndReassociate(event.getObject());
/*  54: 86 */     if (refreshedAlready.containsKey(object))
/*  55:    */     {
/*  56: 87 */       LOG.trace("Already refreshed");
/*  57: 88 */       return;
/*  58:    */     }
/*  59: 91 */     EntityEntry e = source.getPersistenceContext().getEntry(object);
/*  60:    */     EntityPersister persister;
/*  61:    */     Serializable id;
/*  62: 95 */     if (e == null)
/*  63:    */     {
/*  64: 96 */       EntityPersister persister = source.getEntityPersister(event.getEntityName(), object);
/*  65: 97 */       Serializable id = persister.getIdentifier(object, event.getSession());
/*  66: 98 */       if (LOG.isTraceEnabled()) {
/*  67: 99 */         LOG.tracev("Refreshing transient {0}", MessageHelper.infoString(persister, id, source.getFactory()));
/*  68:    */       }
/*  69:101 */       EntityKey key = source.generateEntityKey(id, persister);
/*  70:102 */       if (source.getPersistenceContext().getEntry(key) != null) {
/*  71:103 */         throw new PersistentObjectException("attempted to refresh transient instance when persistent instance was already associated with the Session: " + MessageHelper.infoString(persister, id, source.getFactory()));
/*  72:    */       }
/*  73:    */     }
/*  74:    */     else
/*  75:    */     {
/*  76:110 */       if (LOG.isTraceEnabled()) {
/*  77:111 */         LOG.tracev("Refreshing ", MessageHelper.infoString(e.getPersister(), e.getId(), source.getFactory()));
/*  78:    */       }
/*  79:113 */       if (!e.isExistsInDatabase()) {
/*  80:114 */         throw new HibernateException("this instance does not yet exist as a row in the database");
/*  81:    */       }
/*  82:117 */       persister = e.getPersister();
/*  83:118 */       id = e.getId();
/*  84:    */     }
/*  85:122 */     refreshedAlready.put(object, object);
/*  86:123 */     new Cascade(CascadingAction.REFRESH, 0, source).cascade(persister, object, refreshedAlready);
/*  87:126 */     if (e != null)
/*  88:    */     {
/*  89:127 */       EntityKey key = source.generateEntityKey(id, persister);
/*  90:128 */       source.getPersistenceContext().removeEntity(key);
/*  91:129 */       if (persister.hasCollections()) {
/*  92:129 */         new EvictVisitor(source).process(object, persister);
/*  93:    */       }
/*  94:    */     }
/*  95:132 */     if (persister.hasCache())
/*  96:    */     {
/*  97:133 */       CacheKey ck = source.generateCacheKey(id, persister.getIdentifierType(), persister.getRootEntityName());
/*  98:    */       
/*  99:    */ 
/* 100:    */ 
/* 101:    */ 
/* 102:138 */       persister.getCacheAccessStrategy().evict(ck);
/* 103:    */     }
/* 104:141 */     evictCachedCollections(persister, id, source.getFactory());
/* 105:    */     
/* 106:143 */     String previousFetchProfile = source.getLoadQueryInfluencers().getInternalFetchProfile();
/* 107:144 */     source.getLoadQueryInfluencers().setInternalFetchProfile("refresh");
/* 108:145 */     Object result = persister.load(id, object, event.getLockOptions(), source);
/* 109:148 */     if (result != null) {
/* 110:149 */       if (!persister.isMutable()) {
/* 111:151 */         source.setReadOnly(result, true);
/* 112:    */       } else {
/* 113:154 */         source.setReadOnly(result, e == null ? source.isDefaultReadOnly() : e.isReadOnly());
/* 114:    */       }
/* 115:    */     }
/* 116:157 */     source.getLoadQueryInfluencers().setInternalFetchProfile(previousFetchProfile);
/* 117:    */     
/* 118:159 */     UnresolvableObjectException.throwIfNull(result, id, persister.getEntityName());
/* 119:    */   }
/* 120:    */   
/* 121:    */   private void evictCachedCollections(EntityPersister persister, Serializable id, SessionFactoryImplementor factory)
/* 122:    */   {
/* 123:164 */     evictCachedCollections(persister.getPropertyTypes(), id, factory);
/* 124:    */   }
/* 125:    */   
/* 126:    */   private void evictCachedCollections(Type[] types, Serializable id, SessionFactoryImplementor factory)
/* 127:    */     throws HibernateException
/* 128:    */   {
/* 129:169 */     for (Type type : types) {
/* 130:170 */       if (type.isCollectionType())
/* 131:    */       {
/* 132:171 */         factory.getCache().evictCollection(((CollectionType)type).getRole(), id);
/* 133:    */       }
/* 134:173 */       else if (type.isComponentType())
/* 135:    */       {
/* 136:174 */         CompositeType actype = (CompositeType)type;
/* 137:175 */         evictCachedCollections(actype.getSubtypes(), id, factory);
/* 138:    */       }
/* 139:    */     }
/* 140:    */   }
/* 141:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.event.internal.DefaultRefreshEventListener
 * JD-Core Version:    0.7.0.1
 */