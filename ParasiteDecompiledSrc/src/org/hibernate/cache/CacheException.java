/*  1:   */ package org.hibernate.cache;
/*  2:   */ 
/*  3:   */ import org.hibernate.HibernateException;
/*  4:   */ 
/*  5:   */ public class CacheException
/*  6:   */   extends HibernateException
/*  7:   */ {
/*  8:   */   public CacheException(String s)
/*  9:   */   {
/* 10:34 */     super(s);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public CacheException(String s, Throwable e)
/* 14:   */   {
/* 15:38 */     super(s, e);
/* 16:   */   }
/* 17:   */   
/* 18:   */   public CacheException(Throwable e)
/* 19:   */   {
/* 20:42 */     super(e);
/* 21:   */   }
/* 22:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cache.CacheException
 * JD-Core Version:    0.7.0.1
 */