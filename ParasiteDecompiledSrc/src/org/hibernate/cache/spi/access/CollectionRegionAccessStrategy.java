package org.hibernate.cache.spi.access;

import org.hibernate.cache.spi.CollectionRegion;

public abstract interface CollectionRegionAccessStrategy
  extends RegionAccessStrategy
{
  public abstract CollectionRegion getRegion();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cache.spi.access.CollectionRegionAccessStrategy
 * JD-Core Version:    0.7.0.1
 */