/*  1:   */ package org.hibernate;
/*  2:   */ 
/*  3:   */ public class MappingException
/*  4:   */   extends HibernateException
/*  5:   */ {
/*  6:   */   public MappingException(String msg, Throwable root)
/*  7:   */   {
/*  8:35 */     super(msg, root);
/*  9:   */   }
/* 10:   */   
/* 11:   */   public MappingException(Throwable root)
/* 12:   */   {
/* 13:39 */     super(root);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public MappingException(String s)
/* 17:   */   {
/* 18:43 */     super(s);
/* 19:   */   }
/* 20:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.MappingException
 * JD-Core Version:    0.7.0.1
 */