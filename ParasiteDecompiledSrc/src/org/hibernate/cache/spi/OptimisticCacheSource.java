package org.hibernate.cache.spi;

import java.util.Comparator;

public abstract interface OptimisticCacheSource
{
  public abstract boolean isVersioned();
  
  public abstract Comparator getVersionComparator();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cache.spi.OptimisticCacheSource
 * JD-Core Version:    0.7.0.1
 */