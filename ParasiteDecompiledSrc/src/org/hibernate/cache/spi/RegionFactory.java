package org.hibernate.cache.spi;

import java.util.Properties;
import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.cfg.Settings;
import org.hibernate.service.Service;

public abstract interface RegionFactory
  extends Service
{
  public abstract void start(Settings paramSettings, Properties paramProperties)
    throws CacheException;
  
  public abstract void stop();
  
  public abstract boolean isMinimalPutsEnabledByDefault();
  
  public abstract AccessType getDefaultAccessType();
  
  public abstract long nextTimestamp();
  
  public abstract EntityRegion buildEntityRegion(String paramString, Properties paramProperties, CacheDataDescription paramCacheDataDescription)
    throws CacheException;
  
  public abstract CollectionRegion buildCollectionRegion(String paramString, Properties paramProperties, CacheDataDescription paramCacheDataDescription)
    throws CacheException;
  
  public abstract QueryResultsRegion buildQueryResultsRegion(String paramString, Properties paramProperties)
    throws CacheException;
  
  public abstract TimestampsRegion buildTimestampsRegion(String paramString, Properties paramProperties)
    throws CacheException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cache.spi.RegionFactory
 * JD-Core Version:    0.7.0.1
 */