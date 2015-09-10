package org.hibernate.jmx;

import org.hibernate.HibernateException;

@Deprecated
public abstract interface HibernateServiceMBean
{
  public abstract String getMapResources();
  
  public abstract void setMapResources(String paramString);
  
  public abstract void addMapResource(String paramString);
  
  public abstract void setProperty(String paramString1, String paramString2);
  
  public abstract String getProperty(String paramString);
  
  public abstract String getPropertyList();
  
  public abstract String getDatasource();
  
  public abstract void setDatasource(String paramString);
  
  public abstract String getUserName();
  
  public abstract void setUserName(String paramString);
  
  public abstract String getPassword();
  
  public abstract void setPassword(String paramString);
  
  public abstract String getDialect();
  
  public abstract void setDialect(String paramString);
  
  public abstract String getJndiName();
  
  public abstract void setJndiName(String paramString);
  
  public abstract String getTransactionStrategy();
  
  public abstract void setTransactionStrategy(String paramString);
  
  public abstract String getUserTransactionName();
  
  public abstract void setUserTransactionName(String paramString);
  
  public abstract String getJtaPlatformName();
  
  public abstract void setJtaPlatformName(String paramString);
  
  public abstract String getShowSqlEnabled();
  
  public abstract void setShowSqlEnabled(String paramString);
  
  public abstract String getMaximumFetchDepth();
  
  public abstract void setMaximumFetchDepth(String paramString);
  
  public abstract String getJdbcBatchSize();
  
  public abstract void setJdbcBatchSize(String paramString);
  
  public abstract String getJdbcFetchSize();
  
  public abstract void setJdbcFetchSize(String paramString);
  
  public abstract String getQuerySubstitutions();
  
  public abstract void setQuerySubstitutions(String paramString);
  
  public abstract String getDefaultSchema();
  
  public abstract void setDefaultSchema(String paramString);
  
  public abstract String getDefaultCatalog();
  
  public abstract void setDefaultCatalog(String paramString);
  
  public abstract String getJdbcScrollableResultSetEnabled();
  
  public abstract void setJdbcScrollableResultSetEnabled(String paramString);
  
  public abstract String getGetGeneratedKeysEnabled();
  
  public abstract void setGetGeneratedKeysEnabled(String paramString);
  
  public abstract String getCacheRegionFactory();
  
  public abstract void setCacheRegionFactory(String paramString);
  
  public abstract String getCacheProviderConfig();
  
  public abstract void setCacheProviderConfig(String paramString);
  
  public abstract String getQueryCacheEnabled();
  
  public abstract void setQueryCacheEnabled(String paramString);
  
  public abstract String getSecondLevelCacheEnabled();
  
  public abstract void setSecondLevelCacheEnabled(String paramString);
  
  public abstract String getCacheRegionPrefix();
  
  public abstract void setCacheRegionPrefix(String paramString);
  
  public abstract String getMinimalPutsEnabled();
  
  public abstract void setMinimalPutsEnabled(String paramString);
  
  public abstract String getCommentsEnabled();
  
  public abstract void setCommentsEnabled(String paramString);
  
  public abstract String getBatchVersionedDataEnabled();
  
  public abstract void setBatchVersionedDataEnabled(String paramString);
  
  public abstract void setFlushBeforeCompletionEnabled(String paramString);
  
  public abstract String getFlushBeforeCompletionEnabled();
  
  public abstract void setAutoCloseSessionEnabled(String paramString);
  
  public abstract String getAutoCloseSessionEnabled();
  
  public abstract void createSchema()
    throws HibernateException;
  
  public abstract void dropSchema()
    throws HibernateException;
  
  public abstract void start()
    throws HibernateException;
  
  public abstract void stop();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.jmx.HibernateServiceMBean
 * JD-Core Version:    0.7.0.1
 */