/*  1:   */ package org.hibernate.dialect.lock;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ import org.hibernate.HibernateException;
/*  5:   */ import org.hibernate.LockMode;
/*  6:   */ import org.hibernate.OptimisticLockException;
/*  7:   */ import org.hibernate.action.internal.EntityVerifyVersionProcess;
/*  8:   */ import org.hibernate.engine.spi.ActionQueue;
/*  9:   */ import org.hibernate.engine.spi.EntityEntry;
/* 10:   */ import org.hibernate.engine.spi.PersistenceContext;
/* 11:   */ import org.hibernate.engine.spi.SessionImplementor;
/* 12:   */ import org.hibernate.event.spi.EventSource;
/* 13:   */ import org.hibernate.persister.entity.Lockable;
/* 14:   */ 
/* 15:   */ public class OptimisticLockingStrategy
/* 16:   */   implements LockingStrategy
/* 17:   */ {
/* 18:   */   private final Lockable lockable;
/* 19:   */   private final LockMode lockMode;
/* 20:   */   
/* 21:   */   public OptimisticLockingStrategy(Lockable lockable, LockMode lockMode)
/* 22:   */   {
/* 23:59 */     this.lockable = lockable;
/* 24:60 */     this.lockMode = lockMode;
/* 25:61 */     if (lockMode.lessThan(LockMode.OPTIMISTIC)) {
/* 26:62 */       throw new HibernateException("[" + lockMode + "] not valid for [" + lockable.getEntityName() + "]");
/* 27:   */     }
/* 28:   */   }
/* 29:   */   
/* 30:   */   public void lock(Serializable id, Object version, Object object, int timeout, SessionImplementor session)
/* 31:   */   {
/* 32:68 */     if (!this.lockable.isVersioned()) {
/* 33:69 */       throw new OptimisticLockException(object, "[" + this.lockMode + "] not supported for non-versioned entities [" + this.lockable.getEntityName() + "]");
/* 34:   */     }
/* 35:71 */     EntityEntry entry = session.getPersistenceContext().getEntry(object);
/* 36:72 */     EventSource source = (EventSource)session;
/* 37:73 */     EntityVerifyVersionProcess verifyVersion = new EntityVerifyVersionProcess(object, entry);
/* 38:   */     
/* 39:75 */     source.getActionQueue().registerProcess(verifyVersion);
/* 40:   */   }
/* 41:   */   
/* 42:   */   protected LockMode getLockMode()
/* 43:   */   {
/* 44:79 */     return this.lockMode;
/* 45:   */   }
/* 46:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.lock.OptimisticLockingStrategy
 * JD-Core Version:    0.7.0.1
 */