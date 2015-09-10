/*  1:   */ package org.hibernate;
/*  2:   */ 
/*  3:   */ public class HibernateException
/*  4:   */   extends RuntimeException
/*  5:   */ {
/*  6:   */   public HibernateException(String message)
/*  7:   */   {
/*  8:39 */     super(message);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public HibernateException(Throwable root)
/* 12:   */   {
/* 13:43 */     super(root);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public HibernateException(String message, Throwable root)
/* 17:   */   {
/* 18:47 */     super(message, root);
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.HibernateException
 * JD-Core Version:    0.7.0.1
 */