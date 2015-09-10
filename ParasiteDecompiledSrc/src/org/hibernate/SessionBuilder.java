package org.hibernate;

import java.sql.Connection;

public abstract interface SessionBuilder
{
  public abstract Session openSession();
  
  public abstract SessionBuilder interceptor(Interceptor paramInterceptor);
  
  public abstract SessionBuilder noInterceptor();
  
  public abstract SessionBuilder connection(Connection paramConnection);
  
  public abstract SessionBuilder connectionReleaseMode(ConnectionReleaseMode paramConnectionReleaseMode);
  
  public abstract SessionBuilder autoJoinTransactions(boolean paramBoolean);
  
  public abstract SessionBuilder autoClose(boolean paramBoolean);
  
  public abstract SessionBuilder flushBeforeCompletion(boolean paramBoolean);
  
  public abstract SessionBuilder tenantIdentifier(String paramString);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.SessionBuilder
 * JD-Core Version:    0.7.0.1
 */