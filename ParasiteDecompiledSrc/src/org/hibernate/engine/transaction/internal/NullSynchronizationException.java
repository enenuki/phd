/*  1:   */ package org.hibernate.engine.transaction.internal;
/*  2:   */ 
/*  3:   */ import org.hibernate.HibernateException;
/*  4:   */ 
/*  5:   */ public class NullSynchronizationException
/*  6:   */   extends HibernateException
/*  7:   */ {
/*  8:   */   public NullSynchronizationException()
/*  9:   */   {
/* 10:35 */     this("Synchronization to register cannot be null");
/* 11:   */   }
/* 12:   */   
/* 13:   */   public NullSynchronizationException(String s)
/* 14:   */   {
/* 15:39 */     super(s);
/* 16:   */   }
/* 17:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.transaction.internal.NullSynchronizationException
 * JD-Core Version:    0.7.0.1
 */