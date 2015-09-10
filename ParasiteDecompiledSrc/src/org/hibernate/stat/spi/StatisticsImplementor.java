package org.hibernate.stat.spi;

import org.hibernate.service.Service;
import org.hibernate.stat.Statistics;

public abstract interface StatisticsImplementor
  extends Statistics, Service
{
  public abstract void openSession();
  
  public abstract void closeSession();
  
  public abstract void flush();
  
  public abstract void connect();
  
  public abstract void prepareStatement();
  
  public abstract void closeStatement();
  
  public abstract void endTransaction(boolean paramBoolean);
  
  public abstract void loadEntity(String paramString);
  
  public abstract void fetchEntity(String paramString);
  
  public abstract void updateEntity(String paramString);
  
  public abstract void insertEntity(String paramString);
  
  public abstract void deleteEntity(String paramString);
  
  public abstract void optimisticFailure(String paramString);
  
  public abstract void loadCollection(String paramString);
  
  public abstract void fetchCollection(String paramString);
  
  public abstract void updateCollection(String paramString);
  
  public abstract void recreateCollection(String paramString);
  
  public abstract void removeCollection(String paramString);
  
  public abstract void secondLevelCachePut(String paramString);
  
  public abstract void secondLevelCacheHit(String paramString);
  
  public abstract void secondLevelCacheMiss(String paramString);
  
  public abstract void queryCachePut(String paramString1, String paramString2);
  
  public abstract void queryCacheHit(String paramString1, String paramString2);
  
  public abstract void queryCacheMiss(String paramString1, String paramString2);
  
  public abstract void queryExecuted(String paramString, int paramInt, long paramLong);
  
  public abstract void updateTimestampsCacheHit();
  
  public abstract void updateTimestampsCacheMiss();
  
  public abstract void updateTimestampsCachePut();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.stat.spi.StatisticsImplementor
 * JD-Core Version:    0.7.0.1
 */