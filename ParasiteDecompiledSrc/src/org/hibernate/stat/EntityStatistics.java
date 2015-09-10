package org.hibernate.stat;

import java.io.Serializable;

public abstract interface EntityStatistics
  extends Serializable
{
  public abstract long getDeleteCount();
  
  public abstract long getInsertCount();
  
  public abstract long getLoadCount();
  
  public abstract long getUpdateCount();
  
  public abstract long getFetchCount();
  
  public abstract long getOptimisticFailureCount();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.stat.EntityStatistics
 * JD-Core Version:    0.7.0.1
 */