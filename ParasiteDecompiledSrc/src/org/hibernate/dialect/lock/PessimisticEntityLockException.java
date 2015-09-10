/*  1:   */ package org.hibernate.dialect.lock;
/*  2:   */ 
/*  3:   */ import org.hibernate.JDBCException;
/*  4:   */ 
/*  5:   */ public class PessimisticEntityLockException
/*  6:   */   extends LockingStrategyException
/*  7:   */ {
/*  8:   */   public PessimisticEntityLockException(Object entity, String message, JDBCException root)
/*  9:   */   {
/* 10:35 */     super(entity, message, root);
/* 11:   */   }
/* 12:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.dialect.lock.PessimisticEntityLockException
 * JD-Core Version:    0.7.0.1
 */