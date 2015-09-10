package org.hibernate.cache.spi;

import java.util.Map;
import org.hibernate.cache.CacheException;

public abstract interface Region
{
  public abstract String getName();
  
  public abstract void destroy()
    throws CacheException;
  
  public abstract boolean contains(Object paramObject);
  
  public abstract long getSizeInMemory();
  
  public abstract long getElementCountInMemory();
  
  public abstract long getElementCountOnDisk();
  
  public abstract Map toMap();
  
  public abstract long nextTimestamp();
  
  public abstract int getTimeout();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.cache.spi.Region
 * JD-Core Version:    0.7.0.1
 */