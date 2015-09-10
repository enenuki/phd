/*  1:   */ package org.hibernate;
/*  2:   */ 
/*  3:   */ import org.hibernate.dialect.lock.OptimisticEntityLockException;
/*  4:   */ 
/*  5:   */ /**
/*  6:   */  * @deprecated
/*  7:   */  */
/*  8:   */ public class OptimisticLockException
/*  9:   */   extends OptimisticEntityLockException
/* 10:   */ {
/* 11:   */   public OptimisticLockException(Object entity, String message)
/* 12:   */   {
/* 13:37 */     super(entity, message);
/* 14:   */   }
/* 15:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.OptimisticLockException
 * JD-Core Version:    0.7.0.1
 */