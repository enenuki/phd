/*  1:   */ package org.hibernate;
/*  2:   */ 
/*  3:   */ public class UnknownProfileException
/*  4:   */   extends HibernateException
/*  5:   */ {
/*  6:   */   private final String name;
/*  7:   */   
/*  8:   */   public UnknownProfileException(String name)
/*  9:   */   {
/* 10:37 */     super("Unknow fetch profile [" + name + "]");
/* 11:38 */     this.name = name;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public String getName()
/* 15:   */   {
/* 16:47 */     return this.name;
/* 17:   */   }
/* 18:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.UnknownProfileException
 * JD-Core Version:    0.7.0.1
 */