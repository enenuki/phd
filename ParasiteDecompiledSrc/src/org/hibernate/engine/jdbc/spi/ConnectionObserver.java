package org.hibernate.engine.jdbc.spi;

import java.sql.Connection;

public abstract interface ConnectionObserver
{
  public abstract void physicalConnectionObtained(Connection paramConnection);
  
  public abstract void physicalConnectionReleased();
  
  public abstract void logicalConnectionClosed();
  
  public abstract void statementPrepared();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.spi.ConnectionObserver
 * JD-Core Version:    0.7.0.1
 */