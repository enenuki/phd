package org.hibernate.stat;

public abstract interface Statistics
{
  public abstract void clear();
  
  public abstract EntityStatistics getEntityStatistics(String paramString);
  
  public abstract CollectionStatistics getCollectionStatistics(String paramString);
  
  public abstract SecondLevelCacheStatistics getSecondLevelCacheStatistics(String paramString);
  
  public abstract QueryStatistics getQueryStatistics(String paramString);
  
  public abstract long getEntityDeleteCount();
  
  public abstract long getEntityInsertCount();
  
  public abstract long getEntityLoadCount();
  
  public abstract long getEntityFetchCount();
  
  public abstract long getEntityUpdateCount();
  
  public abstract long getQueryExecutionCount();
  
  public abstract long getQueryExecutionMaxTime();
  
  public abstract String getQueryExecutionMaxTimeQueryString();
  
  public abstract long getQueryCacheHitCount();
  
  public abstract long getQueryCacheMissCount();
  
  public abstract long getQueryCachePutCount();
  
  public abstract long getUpdateTimestampsCacheHitCount();
  
  public abstract long getUpdateTimestampsCacheMissCount();
  
  public abstract long getUpdateTimestampsCachePutCount();
  
  public abstract long getFlushCount();
  
  public abstract long getConnectCount();
  
  public abstract long getSecondLevelCacheHitCount();
  
  public abstract long getSecondLevelCacheMissCount();
  
  public abstract long getSecondLevelCachePutCount();
  
  public abstract long getSessionCloseCount();
  
  public abstract long getSessionOpenCount();
  
  public abstract long getCollectionLoadCount();
  
  public abstract long getCollectionFetchCount();
  
  public abstract long getCollectionUpdateCount();
  
  public abstract long getCollectionRemoveCount();
  
  public abstract long getCollectionRecreateCount();
  
  public abstract long getStartTime();
  
  public abstract void logSummary();
  
  public abstract boolean isStatisticsEnabled();
  
  public abstract void setStatisticsEnabled(boolean paramBoolean);
  
  public abstract String[] getQueries();
  
  public abstract String[] getEntityNames();
  
  public abstract String[] getCollectionRoleNames();
  
  public abstract String[] getSecondLevelCacheRegionNames();
  
  public abstract long getSuccessfulTransactionCount();
  
  public abstract long getTransactionCount();
  
  public abstract long getPrepareStatementCount();
  
  public abstract long getCloseStatementCount();
  
  public abstract long getOptimisticFailureCount();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.stat.Statistics
 * JD-Core Version:    0.7.0.1
 */