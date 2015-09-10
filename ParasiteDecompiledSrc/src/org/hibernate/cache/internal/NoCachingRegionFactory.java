/*  1:   */ package org.hibernate.cache.internal;
/*  2:   */ 
/*  3:   */ import java.util.Properties;
/*  4:   */ import org.hibernate.cache.CacheException;
/*  5:   */ import org.hibernate.cache.NoCachingEnabledException;
/*  6:   */ import org.hibernate.cache.spi.CacheDataDescription;
/*  7:   */ import org.hibernate.cache.spi.CollectionRegion;
/*  8:   */ import org.hibernate.cache.spi.EntityRegion;
/*  9:   */ import org.hibernate.cache.spi.QueryResultsRegion;
/* 10:   */ import org.hibernate.cache.spi.RegionFactory;
/* 11:   */ import org.hibernate.cache.spi.TimestampsRegion;
/* 12:   */ import org.hibernate.cache.spi.access.AccessType;
/* 13:   */ import org.hibernate.cfg.Settings;
/* 14:   */ 
/* 15:   */ public class NoCachingRegionFactory
/* 16:   */   implements RegionFactory
/* 17:   */ {
/* 18:   */   public void start(Settings settings, Properties properties)
/* 19:   */     throws CacheException
/* 20:   */   {}
/* 21:   */   
/* 22:   */   public void stop() {}
/* 23:   */   
/* 24:   */   public boolean isMinimalPutsEnabledByDefault()
/* 25:   */   {
/* 26:55 */     return false;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public AccessType getDefaultAccessType()
/* 30:   */   {
/* 31:59 */     return null;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public long nextTimestamp()
/* 35:   */   {
/* 36:63 */     return System.currentTimeMillis() / 100L;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public EntityRegion buildEntityRegion(String regionName, Properties properties, CacheDataDescription metadata)
/* 40:   */     throws CacheException
/* 41:   */   {
/* 42:68 */     throw new NoCachingEnabledException();
/* 43:   */   }
/* 44:   */   
/* 45:   */   public CollectionRegion buildCollectionRegion(String regionName, Properties properties, CacheDataDescription metadata)
/* 46:   */     throws CacheException
/* 47:   */   {
/* 48:73 */     throw new NoCachingEnabledException();
/* 49:   */   }
/* 50:   */   
/* 51:   */   public QueryResultsRegion buildQueryResultsRegion(String regionName, Properties properties)
/* 52:   */     throws CacheException
/* 53:   */   {
/* 54:77 */     throw new NoCachingEnabledException();
/* 55:   */   }
/* 56:   */   
/* 57:   */   public TimestampsRegion buildTimestampsRegion(String regionName, Properties properties)
/* 58:   */     throws CacheException
/* 59:   */   {
/* 60:81 */     throw new NoCachingEnabledException();
/* 61:   */   }
/* 62:   */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cache.internal.NoCachingRegionFactory
 * JD-Core Version:    0.7.0.1
 */