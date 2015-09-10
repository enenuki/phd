package org.hibernate.cache.spi.access;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.EntityRegion;

public abstract interface EntityRegionAccessStrategy
  extends RegionAccessStrategy
{
  public abstract EntityRegion getRegion();
  
  public abstract boolean insert(Object paramObject1, Object paramObject2, Object paramObject3)
    throws CacheException;
  
  public abstract boolean afterInsert(Object paramObject1, Object paramObject2, Object paramObject3)
    throws CacheException;
  
  public abstract boolean update(Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4)
    throws CacheException;
  
  public abstract boolean afterUpdate(Object paramObject1, Object paramObject2, Object paramObject3, Object paramObject4, SoftLock paramSoftLock)
    throws CacheException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cache.spi.access.EntityRegionAccessStrategy
 * JD-Core Version:    0.7.0.1
 */