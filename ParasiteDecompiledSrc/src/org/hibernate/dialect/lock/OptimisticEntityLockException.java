/*  1:   */ package org.hibernate.dialect.lock;
/*  2:   */ 
/*  3:   */ public class OptimisticEntityLockException
/*  4:   */   extends LockingStrategyException
/*  5:   */ {
/*  6:   */   public OptimisticEntityLockException(Object entity, String message)
/*  7:   */   {
/*  8:33 */     super(entity, message);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public OptimisticEntityLockException(Object entity, String message, Throwable root)
/* 12:   */   {
/* 13:37 */     super(entity, message, root);
/* 14:   */   }
/* 15:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.lock.OptimisticEntityLockException
 * JD-Core Version:    0.7.0.1
 */