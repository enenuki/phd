/*  1:   */ package org.hibernate;
/*  2:   */ 
/*  3:   */ public class CallbackException
/*  4:   */   extends HibernateException
/*  5:   */ {
/*  6:   */   public CallbackException(Exception root)
/*  7:   */   {
/*  8:40 */     super("An exception occurred in a callback", root);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public CallbackException(String message)
/* 12:   */   {
/* 13:44 */     super(message);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public CallbackException(String message, Exception e)
/* 17:   */   {
/* 18:48 */     super(message, e);
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.CallbackException
 * JD-Core Version:    0.7.0.1
 */