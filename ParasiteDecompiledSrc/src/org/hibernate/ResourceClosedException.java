/*  1:   */ package org.hibernate;
/*  2:   */ 
/*  3:   */ public class ResourceClosedException
/*  4:   */   extends HibernateException
/*  5:   */ {
/*  6:   */   public ResourceClosedException(String s)
/*  7:   */   {
/*  8:33 */     super(s);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public ResourceClosedException(String string, Throwable root)
/* 12:   */   {
/* 13:37 */     super(string, root);
/* 14:   */   }
/* 15:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.ResourceClosedException
 * JD-Core Version:    0.7.0.1
 */