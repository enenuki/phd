/*  1:   */ package org.hibernate;
/*  2:   */ 
/*  3:   */ public class NonUniqueResultException
/*  4:   */   extends HibernateException
/*  5:   */ {
/*  6:   */   public NonUniqueResultException(int resultCount)
/*  7:   */   {
/*  8:38 */     super("query did not return a unique result: " + resultCount);
/*  9:   */   }
/* 10:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.NonUniqueResultException
 * JD-Core Version:    0.7.0.1
 */