/*   1:    */ package org.hibernate.cache.spi;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.util.Properties;
/*   5:    */ import java.util.Set;
/*   6:    */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*   7:    */ import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
/*   8:    */ import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
/*   9:    */ import org.hibernate.HibernateException;
/*  10:    */ import org.hibernate.cache.CacheException;
/*  11:    */ import org.hibernate.cfg.Settings;
/*  12:    */ import org.hibernate.engine.spi.SessionFactoryImplementor;
/*  13:    */ import org.hibernate.internal.CoreMessageLogger;
/*  14:    */ import org.hibernate.stat.Statistics;
/*  15:    */ import org.hibernate.stat.spi.StatisticsImplementor;
/*  16:    */ import org.jboss.logging.Logger;
/*  17:    */ 
/*  18:    */ public class UpdateTimestampsCache
/*  19:    */ {
/*  20: 51 */   public static final String REGION_NAME = UpdateTimestampsCache.class.getName();
/*  21: 52 */   private static final CoreMessageLogger LOG = (CoreMessageLogger)Logger.getMessageLogger(CoreMessageLogger.class, UpdateTimestampsCache.class.getName());
/*  22: 54 */   private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
/*  23:    */   private final TimestampsRegion region;
/*  24:    */   private final SessionFactoryImplementor factory;
/*  25:    */   
/*  26:    */   public UpdateTimestampsCache(Settings settings, Properties props, SessionFactoryImplementor factory)
/*  27:    */     throws HibernateException
/*  28:    */   {
/*  29: 59 */     this.factory = factory;
/*  30: 60 */     String prefix = settings.getCacheRegionPrefix();
/*  31: 61 */     String regionName = prefix + '.' + REGION_NAME;
/*  32: 62 */     LOG.startingUpdateTimestampsCache(regionName);
/*  33: 63 */     this.region = settings.getRegionFactory().buildTimestampsRegion(regionName, props);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public UpdateTimestampsCache(Settings settings, Properties props)
/*  37:    */     throws HibernateException
/*  38:    */   {
/*  39: 68 */     this(settings, props, null);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void preinvalidate(Serializable[] spaces)
/*  43:    */     throws CacheException
/*  44:    */   {
/*  45: 73 */     this.readWriteLock.writeLock().lock();
/*  46:    */     try
/*  47:    */     {
/*  48: 76 */       Long ts = Long.valueOf(this.region.nextTimestamp() + this.region.getTimeout());
/*  49: 77 */       for (Serializable space : spaces)
/*  50:    */       {
/*  51: 78 */         LOG.debugf("Pre-invalidating space [%s]", space);
/*  52:    */         
/*  53:    */ 
/*  54: 81 */         this.region.put(space, ts);
/*  55: 82 */         if ((this.factory != null) && (this.factory.getStatistics().isStatisticsEnabled())) {
/*  56: 83 */           this.factory.getStatisticsImplementor().updateTimestampsCachePut();
/*  57:    */         }
/*  58:    */       }
/*  59:    */     }
/*  60:    */     finally
/*  61:    */     {
/*  62: 88 */       this.readWriteLock.writeLock().unlock();
/*  63:    */     }
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void invalidate(Serializable[] spaces)
/*  67:    */     throws CacheException
/*  68:    */   {
/*  69: 94 */     this.readWriteLock.writeLock().lock();
/*  70:    */     try
/*  71:    */     {
/*  72: 97 */       Long ts = Long.valueOf(this.region.nextTimestamp());
/*  73: 98 */       for (Serializable space : spaces)
/*  74:    */       {
/*  75: 99 */         LOG.debugf("Invalidating space [%s], timestamp: %s", space, ts);
/*  76:    */         
/*  77:    */ 
/*  78:102 */         this.region.put(space, ts);
/*  79:103 */         if ((this.factory != null) && (this.factory.getStatistics().isStatisticsEnabled())) {
/*  80:104 */           this.factory.getStatisticsImplementor().updateTimestampsCachePut();
/*  81:    */         }
/*  82:    */       }
/*  83:    */     }
/*  84:    */     finally
/*  85:    */     {
/*  86:109 */       this.readWriteLock.writeLock().unlock();
/*  87:    */     }
/*  88:    */   }
/*  89:    */   
/*  90:    */   public boolean isUpToDate(Set spaces, Long timestamp)
/*  91:    */     throws HibernateException
/*  92:    */   {
/*  93:115 */     this.readWriteLock.readLock().lock();
/*  94:    */     try
/*  95:    */     {
/*  96:118 */       for (Serializable space : spaces)
/*  97:    */       {
/*  98:119 */         Long lastUpdate = (Long)this.region.get(space);
/*  99:120 */         if (lastUpdate == null)
/* 100:    */         {
/* 101:121 */           if ((this.factory != null) && (this.factory.getStatistics().isStatisticsEnabled())) {
/* 102:122 */             this.factory.getStatisticsImplementor().updateTimestampsCacheMiss();
/* 103:    */           }
/* 104:    */         }
/* 105:    */         else
/* 106:    */         {
/* 107:130 */           if (LOG.isDebugEnabled()) {
/* 108:131 */             LOG.debugf("[%s] last update timestamp: %s", space, lastUpdate + ", result set timestamp: " + timestamp);
/* 109:    */           }
/* 110:137 */           if ((this.factory != null) && (this.factory.getStatistics().isStatisticsEnabled())) {
/* 111:138 */             this.factory.getStatisticsImplementor().updateTimestampsCacheHit();
/* 112:    */           }
/* 113:140 */           if (lastUpdate.longValue() >= timestamp.longValue()) {
/* 114:140 */             return false;
/* 115:    */           }
/* 116:    */         }
/* 117:    */       }
/* 118:143 */       return 1;
/* 119:    */     }
/* 120:    */     finally
/* 121:    */     {
/* 122:146 */       this.readWriteLock.readLock().unlock();
/* 123:    */     }
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void clear()
/* 127:    */     throws CacheException
/* 128:    */   {
/* 129:151 */     this.region.evictAll();
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void destroy()
/* 133:    */   {
/* 134:    */     try
/* 135:    */     {
/* 136:156 */       this.region.destroy();
/* 137:    */     }
/* 138:    */     catch (Exception e)
/* 139:    */     {
/* 140:159 */       LOG.unableToDestroyUpdateTimestampsCache(this.region.getName(), e.getMessage());
/* 141:    */     }
/* 142:    */   }
/* 143:    */   
/* 144:    */   public TimestampsRegion getRegion()
/* 145:    */   {
/* 146:164 */     return this.region;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public String toString()
/* 150:    */   {
/* 151:169 */     return "UpdateTimestampsCache";
/* 152:    */   }
/* 153:    */ }


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cache.spi.UpdateTimestampsCache
 * JD-Core Version:    0.7.0.1
 */