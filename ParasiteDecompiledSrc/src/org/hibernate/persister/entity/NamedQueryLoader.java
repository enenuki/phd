/*  1:   */ package org.hibernate.persister.entity;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.FlushMode;
/*  5:   */ import org.hibernate.LockOptions;
/*  6:   */ import org.hibernate.engine.spi.PersistenceContext;
/*  7:   */ import org.hibernate.engine.spi.SessionImplementor;
/*  8:   */ import org.hibernate.internal.AbstractQueryImpl;
/*  9:   */ import org.hibernate.internal.CoreMessageLogger;
/* 10:   */ import org.hibernate.loader.entity.UniqueEntityLoader;
/* 11:   */ import org.jboss.logging.Logger;
/* 12:   */ 
/* 13:   */ public final class NamedQueryLoader
/* 14:   */   implements UniqueEntityLoader
/* 15:   */ {
/* 16:   */   private final String queryName;
/* 17:   */   private final EntityPersister persister;
/* 18:48 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, NamedQueryLoader.class.getName());
/* 19:   */   
/* 20:   */   public NamedQueryLoader(String queryName, EntityPersister persister)
/* 21:   */   {
/* 22:52 */     this.queryName = queryName;
/* 23:53 */     this.persister = persister;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public Object load(Serializable id, Object optionalObject, SessionImplementor session, LockOptions lockOptions)
/* 27:   */   {
/* 28:57 */     if (lockOptions != null) {
/* 29:57 */       LOG.debug("Ignoring lock-options passed to named query loader");
/* 30:   */     }
/* 31:58 */     return load(id, optionalObject, session);
/* 32:   */   }
/* 33:   */   
/* 34:   */   public Object load(Serializable id, Object optionalObject, SessionImplementor session)
/* 35:   */   {
/* 36:62 */     LOG.debugf("Loading entity: %s using named query: %s", this.persister.getEntityName(), this.queryName);
/* 37:   */     
/* 38:64 */     AbstractQueryImpl query = (AbstractQueryImpl)session.getNamedQuery(this.queryName);
/* 39:65 */     if (query.hasNamedParameters()) {
/* 40:66 */       query.setParameter(query.getNamedParameters()[0], id, this.persister.getIdentifierType());
/* 41:   */     } else {
/* 42:73 */       query.setParameter(0, id, this.persister.getIdentifierType());
/* 43:   */     }
/* 44:75 */     query.setOptionalId(id);
/* 45:76 */     query.setOptionalEntityName(this.persister.getEntityName());
/* 46:77 */     query.setOptionalObject(optionalObject);
/* 47:78 */     query.setFlushMode(FlushMode.MANUAL);
/* 48:79 */     query.list();
/* 49:   */     
/* 50:   */ 
/* 51:   */ 
/* 52:83 */     return session.getPersistenceContext().getEntity(session.generateEntityKey(id, this.persister));
/* 53:   */   }
/* 54:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.persister.entity.NamedQueryLoader
 * JD-Core Version:    0.7.0.1
 */