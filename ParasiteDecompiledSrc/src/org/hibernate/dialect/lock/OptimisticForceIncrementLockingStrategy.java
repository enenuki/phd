/*  1:   */ package org.hibernate.dialect.lock;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.HibernateException;
/*  5:   */ import org.hibernate.LockMode;
/*  6:   */ import org.hibernate.action.internal.EntityIncrementVersionProcess;
/*  7:   */ import org.hibernate.engine.spi.ActionQueue;
/*  8:   */ import org.hibernate.engine.spi.EntityEntry;
/*  9:   */ import org.hibernate.engine.spi.PersistenceContext;
/* 10:   */ import org.hibernate.engine.spi.SessionImplementor;
/* 11:   */ import org.hibernate.event.spi.EventSource;
/* 12:   */ import org.hibernate.persister.entity.Lockable;
/* 13:   */ 
/* 14:   */ public class OptimisticForceIncrementLockingStrategy
/* 15:   */   implements LockingStrategy
/* 16:   */ {
/* 17:   */   private final Lockable lockable;
/* 18:   */   private final LockMode lockMode;
/* 19:   */   
/* 20:   */   public OptimisticForceIncrementLockingStrategy(Lockable lockable, LockMode lockMode)
/* 21:   */   {
/* 22:56 */     this.lockable = lockable;
/* 23:57 */     this.lockMode = lockMode;
/* 24:58 */     if (lockMode.lessThan(LockMode.OPTIMISTIC_FORCE_INCREMENT)) {
/* 25:59 */       throw new HibernateException("[" + lockMode + "] not valid for [" + lockable.getEntityName() + "]");
/* 26:   */     }
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void lock(Serializable id, Object version, Object object, int timeout, SessionImplementor session)
/* 30:   */   {
/* 31:65 */     if (!this.lockable.isVersioned()) {
/* 32:66 */       throw new HibernateException("[" + this.lockMode + "] not supported for non-versioned entities [" + this.lockable.getEntityName() + "]");
/* 33:   */     }
/* 34:68 */     EntityEntry entry = session.getPersistenceContext().getEntry(object);
/* 35:69 */     EntityIncrementVersionProcess incrementVersion = new EntityIncrementVersionProcess(object, entry);
/* 36:70 */     EventSource source = (EventSource)session;
/* 37:   */     
/* 38:72 */     source.getActionQueue().registerProcess(incrementVersion);
/* 39:   */   }
/* 40:   */   
/* 41:   */   protected LockMode getLockMode()
/* 42:   */   {
/* 43:76 */     return this.lockMode;
/* 44:   */   }
/* 45:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.lock.OptimisticForceIncrementLockingStrategy
 * JD-Core Version:    0.7.0.1
 */