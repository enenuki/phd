/*  1:   */ package org.hibernate.dialect.lock;
/*  2:   */ 
/*  3:   */ import org.hibernate.LockMode;
/*  4:   */ import org.hibernate.persister.entity.Lockable;
/*  5:   */ 
/*  6:   */ public abstract class AbstractSelectLockingStrategy
/*  7:   */   implements LockingStrategy
/*  8:   */ {
/*  9:   */   private final Lockable lockable;
/* 10:   */   private final LockMode lockMode;
/* 11:   */   private final String waitForeverSql;
/* 12:   */   private String noWaitSql;
/* 13:   */   
/* 14:   */   protected AbstractSelectLockingStrategy(Lockable lockable, LockMode lockMode)
/* 15:   */   {
/* 16:41 */     this.lockable = lockable;
/* 17:42 */     this.lockMode = lockMode;
/* 18:43 */     this.waitForeverSql = generateLockString(-1);
/* 19:   */   }
/* 20:   */   
/* 21:   */   protected Lockable getLockable()
/* 22:   */   {
/* 23:47 */     return this.lockable;
/* 24:   */   }
/* 25:   */   
/* 26:   */   protected LockMode getLockMode()
/* 27:   */   {
/* 28:51 */     return this.lockMode;
/* 29:   */   }
/* 30:   */   
/* 31:   */   protected abstract String generateLockString(int paramInt);
/* 32:   */   
/* 33:   */   protected String determineSql(int timeout)
/* 34:   */   {
/* 35:57 */     return timeout == 0 ? getNoWaitSql() : timeout == -1 ? this.waitForeverSql : generateLockString(timeout);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public String getNoWaitSql()
/* 39:   */   {
/* 40:67 */     if (this.noWaitSql == null) {
/* 41:68 */       this.noWaitSql = generateLockString(0);
/* 42:   */     }
/* 43:70 */     return this.noWaitSql;
/* 44:   */   }
/* 45:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.lock.AbstractSelectLockingStrategy
 * JD-Core Version:    0.7.0.1
 */