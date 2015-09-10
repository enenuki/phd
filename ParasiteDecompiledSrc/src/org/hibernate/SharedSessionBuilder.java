package org.hibernate;

import java.sql.Connection;

public abstract interface SharedSessionBuilder
  extends SessionBuilder
{
  public abstract SharedSessionBuilder interceptor();
  
  public abstract SharedSessionBuilder connection();
  
  public abstract SharedSessionBuilder connectionReleaseMode();
  
  public abstract SharedSessionBuilder autoJoinTransactions();
  
  public abstract SharedSessionBuilder autoClose();
  
  public abstract SharedSessionBuilder flushBeforeCompletion();
  
  public abstract SharedSessionBuilder transactionContext();
  
  public abstract SharedSessionBuilder interceptor(Interceptor paramInterceptor);
  
  public abstract SharedSessionBuilder noInterceptor();
  
  public abstract SharedSessionBuilder connection(Connection paramConnection);
  
  public abstract SharedSessionBuilder connectionReleaseMode(ConnectionReleaseMode paramConnectionReleaseMode);
  
  public abstract SharedSessionBuilder autoJoinTransactions(boolean paramBoolean);
  
  public abstract SharedSessionBuilder autoClose(boolean paramBoolean);
  
  public abstract SharedSessionBuilder flushBeforeCompletion(boolean paramBoolean);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.SharedSessionBuilder
 * JD-Core Version:    0.7.0.1
 */