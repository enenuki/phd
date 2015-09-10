package org.hibernate.engine.jdbc.spi;

import java.io.Serializable;
import java.sql.Connection;

public abstract interface LogicalConnection
  extends Serializable
{
  public abstract boolean isOpen();
  
  public abstract boolean isPhysicallyConnected();
  
  public abstract Connection getConnection();
  
  public abstract Connection getShareableConnectionProxy();
  
  public abstract Connection getDistinctConnectionProxy();
  
  public abstract Connection close();
  
  public abstract void afterTransaction();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.spi.LogicalConnection
 * JD-Core Version:    0.7.0.1
 */