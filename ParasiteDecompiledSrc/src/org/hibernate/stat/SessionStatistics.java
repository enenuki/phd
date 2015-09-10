package org.hibernate.stat;

import java.util.Set;

public abstract interface SessionStatistics
{
  public abstract int getEntityCount();
  
  public abstract int getCollectionCount();
  
  public abstract Set getEntityKeys();
  
  public abstract Set getCollectionKeys();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.stat.SessionStatistics
 * JD-Core Version:    0.7.0.1
 */