package org.hibernate.stat;

import java.io.Serializable;

public abstract interface QueryStatistics
  extends Serializable
{
  public abstract long getExecutionCount();
  
  public abstract long getCacheHitCount();
  
  public abstract long getCachePutCount();
  
  public abstract long getCacheMissCount();
  
  public abstract long getExecutionRowCount();
  
  public abstract long getExecutionAvgTime();
  
  public abstract long getExecutionMaxTime();
  
  public abstract long getExecutionMinTime();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.stat.QueryStatistics
 * JD-Core Version:    0.7.0.1
 */