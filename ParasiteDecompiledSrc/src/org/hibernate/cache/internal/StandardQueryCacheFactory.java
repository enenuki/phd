/*  1:   */ package org.hibernate.cache.internal;
/*  2:   */ 
/*  3:   */ import java.util.Properties;
/*  4:   */ import org.hibernate.HibernateException;
/*  5:   */ import org.hibernate.cache.spi.QueryCache;
/*  6:   */ import org.hibernate.cache.spi.QueryCacheFactory;
/*  7:   */ import org.hibernate.cache.spi.UpdateTimestampsCache;
/*  8:   */ import org.hibernate.cfg.Settings;
/*  9:   */ 
/* 10:   */ public class StandardQueryCacheFactory
/* 11:   */   implements QueryCacheFactory
/* 12:   */ {
/* 13:   */   public QueryCache getQueryCache(String regionName, UpdateTimestampsCache updateTimestampsCache, Settings settings, Properties props)
/* 14:   */     throws HibernateException
/* 15:   */   {
/* 16:45 */     return new StandardQueryCache(settings, props, updateTimestampsCache, regionName);
/* 17:   */   }
/* 18:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cache.internal.StandardQueryCacheFactory
 * JD-Core Version:    0.7.0.1
 */