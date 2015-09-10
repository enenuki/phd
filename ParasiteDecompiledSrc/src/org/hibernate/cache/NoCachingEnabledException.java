/*  1:   */ package org.hibernate.cache;
/*  2:   */ 
/*  3:   */ public class NoCachingEnabledException
/*  4:   */   extends CacheException
/*  5:   */ {
/*  6:   */   private static final String MSG = "Second-level cache is not enabled for usage [hibernate.cache.use_second_level_cache | hibernate.cache.use_query_cache]";
/*  7:   */   
/*  8:   */   public NoCachingEnabledException()
/*  9:   */   {
/* 10:40 */     super("Second-level cache is not enabled for usage [hibernate.cache.use_second_level_cache | hibernate.cache.use_query_cache]");
/* 11:   */   }
/* 12:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cache.NoCachingEnabledException
 * JD-Core Version:    0.7.0.1
 */