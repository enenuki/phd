package org.hibernate.service.jdbc.connections.spi;

import java.sql.Connection;
import java.sql.SQLException;
import org.hibernate.service.Service;
import org.hibernate.service.spi.Wrapped;

public abstract interface ConnectionProvider
  extends Service, Wrapped
{
  public abstract Connection getConnection()
    throws SQLException;
  
  public abstract void closeConnection(Connection paramConnection)
    throws SQLException;
  
  public abstract boolean supportsAggressiveRelease();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.jdbc.connections.spi.ConnectionProvider
 * JD-Core Version:    0.7.0.1
 */