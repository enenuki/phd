package org.hibernate.cache.spi;

import org.hibernate.cache.CacheException;

public abstract interface GeneralDataRegion
  extends Region
{
  public abstract Object get(Object paramObject)
    throws CacheException;
  
  public abstract void put(Object paramObject1, Object paramObject2)
    throws CacheException;
  
  public abstract void evict(Object paramObject)
    throws CacheException;
  
  public abstract void evictAll()
    throws CacheException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cache.spi.GeneralDataRegion
 * JD-Core Version:    0.7.0.1
 */