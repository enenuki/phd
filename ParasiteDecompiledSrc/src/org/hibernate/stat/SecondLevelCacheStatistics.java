package org.hibernate.stat;

import java.io.Serializable;
import java.util.Map;

public abstract interface SecondLevelCacheStatistics
  extends Serializable
{
  public abstract long getHitCount();
  
  public abstract long getMissCount();
  
  public abstract long getPutCount();
  
  public abstract long getElementCountInMemory();
  
  public abstract long getElementCountOnDisk();
  
  public abstract long getSizeInMemory();
  
  public abstract Map getEntries();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.stat.SecondLevelCacheStatistics
 * JD-Core Version:    0.7.0.1
 */