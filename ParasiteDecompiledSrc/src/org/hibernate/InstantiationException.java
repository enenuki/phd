/*  1:   */ package org.hibernate;
/*  2:   */ 
/*  3:   */ public class InstantiationException
/*  4:   */   extends HibernateException
/*  5:   */ {
/*  6:   */   private final Class clazz;
/*  7:   */   
/*  8:   */   public InstantiationException(String s, Class clazz, Throwable root)
/*  9:   */   {
/* 10:40 */     super(s, root);
/* 11:41 */     this.clazz = clazz;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public InstantiationException(String s, Class clazz)
/* 15:   */   {
/* 16:45 */     super(s);
/* 17:46 */     this.clazz = clazz;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public InstantiationException(String s, Class clazz, Exception e)
/* 21:   */   {
/* 22:50 */     super(s, e);
/* 23:51 */     this.clazz = clazz;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public Class getPersistentClass()
/* 27:   */   {
/* 28:55 */     return this.clazz;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public String getMessage()
/* 32:   */   {
/* 33:59 */     return super.getMessage() + this.clazz.getName();
/* 34:   */   }
/* 35:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.InstantiationException
 * JD-Core Version:    0.7.0.1
 */