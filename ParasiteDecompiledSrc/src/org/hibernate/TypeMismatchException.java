/*  1:   */ package org.hibernate;
/*  2:   */ 
/*  3:   */ public class TypeMismatchException
/*  4:   */   extends HibernateException
/*  5:   */ {
/*  6:   */   public TypeMismatchException(Throwable root)
/*  7:   */   {
/*  8:35 */     super(root);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public TypeMismatchException(String s)
/* 12:   */   {
/* 13:39 */     super(s);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public TypeMismatchException(String string, Throwable root)
/* 17:   */   {
/* 18:43 */     super(string, root);
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.TypeMismatchException
 * JD-Core Version:    0.7.0.1
 */