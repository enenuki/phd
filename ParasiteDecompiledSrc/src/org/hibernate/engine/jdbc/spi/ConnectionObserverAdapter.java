package org.hibernate.engine.jdbc.spi;

import java.sql.Connection;

public class ConnectionObserverAdapter
  implements ConnectionObserver
{
  public void physicalConnectionObtained(Connection connection) {}
  
  public void physicalConnectionReleased() {}
  
  public void logicalConnectionClosed() {}
  
  public void statementPrepared() {}
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.spi.ConnectionObserverAdapter
 * JD-Core Version:    0.7.0.1
 */