package org.hibernate.cache.spi;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.cache.spi.access.EntityRegionAccessStrategy;

public abstract interface EntityRegion
  extends TransactionalDataRegion
{
  public abstract EntityRegionAccessStrategy buildAccessStrategy(AccessType paramAccessType)
    throws CacheException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cache.spi.EntityRegion
 * JD-Core Version:    0.7.0.1
 */