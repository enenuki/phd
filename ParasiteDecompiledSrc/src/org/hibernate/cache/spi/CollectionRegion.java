package org.hibernate.cache.spi;

import org.hibernate.cache.CacheException;
import org.hibernate.cache.spi.access.AccessType;
import org.hibernate.cache.spi.access.CollectionRegionAccessStrategy;

public abstract interface CollectionRegion
  extends TransactionalDataRegion
{
  public abstract CollectionRegionAccessStrategy buildAccessStrategy(AccessType paramAccessType)
    throws CacheException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cache.spi.CollectionRegion
 * JD-Core Version:    0.7.0.1
 */