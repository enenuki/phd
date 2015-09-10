package org.hibernate.cache.spi.access;

import org.hibernate.cache.CacheException;

public abstract interface RegionAccessStrategy
{
  public abstract Object get(Object paramObject, long paramLong)
    throws CacheException;
  
  public abstract boolean putFromLoad(Object paramObject1, Object paramObject2, long paramLong, Object paramObject3)
    throws CacheException;
  
  public abstract boolean putFromLoad(Object paramObject1, Object paramObject2, long paramLong, Object paramObject3, boolean paramBoolean)
    throws CacheException;
  
  public abstract SoftLock lockItem(Object paramObject1, Object paramObject2)
    throws CacheException;
  
  public abstract SoftLock lockRegion()
    throws CacheException;
  
  public abstract void unlockItem(Object paramObject, SoftLock paramSoftLock)
    throws CacheException;
  
  public abstract void unlockRegion(SoftLock paramSoftLock)
    throws CacheException;
  
  public abstract void remove(Object paramObject)
    throws CacheException;
  
  public abstract void removeAll()
    throws CacheException;
  
  public abstract void evict(Object paramObject)
    throws CacheException;
  
  public abstract void evictAll()
    throws CacheException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cache.spi.access.RegionAccessStrategy
 * JD-Core Version:    0.7.0.1
 */