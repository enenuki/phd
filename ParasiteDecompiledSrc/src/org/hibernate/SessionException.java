/*  1:   */ package org.hibernate;
/*  2:   */ 
/*  3:   */ public class SessionException
/*  4:   */   extends HibernateException
/*  5:   */ {
/*  6:   */   public SessionException(String message)
/*  7:   */   {
/*  8:39 */     super(message);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public SessionException(String message, Throwable cause)
/* 12:   */   {
/* 13:49 */     super(message, cause);
/* 14:   */   }
/* 15:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.SessionException
 * JD-Core Version:    0.7.0.1
 */