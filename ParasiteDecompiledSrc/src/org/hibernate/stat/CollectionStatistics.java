package org.hibernate.stat;

import java.io.Serializable;

public abstract interface CollectionStatistics
  extends Serializable
{
  public abstract long getLoadCount();
  
  public abstract long getFetchCount();
  
  public abstract long getRecreateCount();
  
  public abstract long getRemoveCount();
  
  public abstract long getUpdateCount();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.stat.CollectionStatistics
 * JD-Core Version:    0.7.0.1
 */