/*  1:   */ package org.hibernate;
/*  2:   */ 
/*  3:   */ public class TransactionException
/*  4:   */   extends HibernateException
/*  5:   */ {
/*  6:   */   public TransactionException(String message, Throwable root)
/*  7:   */   {
/*  8:39 */     super(message, root);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public TransactionException(String message)
/* 12:   */   {
/* 13:43 */     super(message);
/* 14:   */   }
/* 15:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.TransactionException
 * JD-Core Version:    0.7.0.1
 */