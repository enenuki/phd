package org.hibernate.engine.jdbc.spi;

import java.sql.Connection;
import org.hibernate.ConnectionReleaseMode;

public abstract interface LogicalConnectionImplementor
  extends LogicalConnection
{
  public abstract JdbcServices getJdbcServices();
  
  public abstract JdbcResourceRegistry getResourceRegistry();
  
  public abstract void addObserver(ConnectionObserver paramConnectionObserver);
  
  public abstract void removeObserver(ConnectionObserver paramConnectionObserver);
  
  public abstract ConnectionReleaseMode getConnectionReleaseMode();
  
  public abstract void afterStatementExecution();
  
  public abstract void afterTransaction();
  
  public abstract void disableReleases();
  
  public abstract void enableReleases();
  
  public abstract Connection manualDisconnect();
  
  public abstract void manualReconnect(Connection paramConnection);
  
  public abstract boolean isAutoCommit();
  
  public abstract boolean isReadyForSerialization();
  
  public abstract void notifyObserversStatementPrepared();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.spi.LogicalConnectionImplementor
 * JD-Core Version:    0.7.0.1
 */