/*  1:   */ package org.hibernate.dialect.lock;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.HibernateException;
/*  5:   */ import org.hibernate.LockMode;
/*  6:   */ import org.hibernate.engine.spi.EntityEntry;
/*  7:   */ import org.hibernate.engine.spi.PersistenceContext;
/*  8:   */ import org.hibernate.engine.spi.SessionImplementor;
/*  9:   */ import org.hibernate.persister.entity.EntityPersister;
/* 10:   */ import org.hibernate.persister.entity.Lockable;
/* 11:   */ 
/* 12:   */ public class PessimisticForceIncrementLockingStrategy
/* 13:   */   implements LockingStrategy
/* 14:   */ {
/* 15:   */   private final Lockable lockable;
/* 16:   */   private final LockMode lockMode;
/* 17:   */   
/* 18:   */   public PessimisticForceIncrementLockingStrategy(Lockable lockable, LockMode lockMode)
/* 19:   */   {
/* 20:54 */     this.lockable = lockable;
/* 21:55 */     this.lockMode = lockMode;
/* 22:57 */     if (lockMode.lessThan(LockMode.PESSIMISTIC_READ)) {
/* 23:58 */       throw new HibernateException("[" + lockMode + "] not valid for [" + lockable.getEntityName() + "]");
/* 24:   */     }
/* 25:   */   }
/* 26:   */   
/* 27:   */   public void lock(Serializable id, Object version, Object object, int timeout, SessionImplementor session)
/* 28:   */   {
/* 29:64 */     if (!this.lockable.isVersioned()) {
/* 30:65 */       throw new HibernateException("[" + this.lockMode + "] not supported for non-versioned entities [" + this.lockable.getEntityName() + "]");
/* 31:   */     }
/* 32:67 */     EntityEntry entry = session.getPersistenceContext().getEntry(object);
/* 33:68 */     EntityPersister persister = entry.getPersister();
/* 34:69 */     Object nextVersion = persister.forceVersionIncrement(entry.getId(), entry.getVersion(), session);
/* 35:70 */     entry.forceLocked(object, nextVersion);
/* 36:   */   }
/* 37:   */   
/* 38:   */   protected LockMode getLockMode()
/* 39:   */   {
/* 40:79 */     return this.lockMode;
/* 41:   */   }
/* 42:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.lock.PessimisticForceIncrementLockingStrategy
 * JD-Core Version:    0.7.0.1
 */