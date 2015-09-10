/*  1:   */ package org.hibernate.persister.collection;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.FlushMode;
/*  5:   */ import org.hibernate.HibernateException;
/*  6:   */ import org.hibernate.Query;
/*  7:   */ import org.hibernate.engine.spi.SessionImplementor;
/*  8:   */ import org.hibernate.internal.AbstractQueryImpl;
/*  9:   */ import org.hibernate.internal.CoreMessageLogger;
/* 10:   */ import org.hibernate.loader.collection.CollectionInitializer;
/* 11:   */ import org.jboss.logging.Logger;
/* 12:   */ 
/* 13:   */ public final class NamedQueryCollectionInitializer
/* 14:   */   implements CollectionInitializer
/* 15:   */ {
/* 16:43 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, NamedQueryCollectionInitializer.class.getName());
/* 17:   */   private final String queryName;
/* 18:   */   private final CollectionPersister persister;
/* 19:   */   
/* 20:   */   public NamedQueryCollectionInitializer(String queryName, CollectionPersister persister)
/* 21:   */   {
/* 22:51 */     this.queryName = queryName;
/* 23:52 */     this.persister = persister;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void initialize(Serializable key, SessionImplementor session)
/* 27:   */     throws HibernateException
/* 28:   */   {
/* 29:58 */     LOG.debugf("Initializing collection: %s using named query: %s", this.persister.getRole(), this.queryName);
/* 30:   */     
/* 31:   */ 
/* 32:61 */     AbstractQueryImpl query = (AbstractQueryImpl)session.getNamedSQLQuery(this.queryName);
/* 33:62 */     if (query.getNamedParameters().length > 0) {
/* 34:63 */       query.setParameter(query.getNamedParameters()[0], key, this.persister.getKeyType());
/* 35:   */     } else {
/* 36:70 */       query.setParameter(0, key, this.persister.getKeyType());
/* 37:   */     }
/* 38:72 */     query.setCollectionKey(key).setFlushMode(FlushMode.MANUAL).list();
/* 39:   */   }
/* 40:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.persister.collection.NamedQueryCollectionInitializer
 * JD-Core Version:    0.7.0.1
 */