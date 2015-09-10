package org.hibernate.service.jdbc.connections.spi;

import java.sql.Connection;
import java.sql.SQLException;
import org.hibernate.service.Service;
import org.hibernate.service.spi.Wrapped;

public abstract interface MultiTenantConnectionProvider
  extends Service, Wrapped
{
  public abstract Connection getAnyConnection()
    throws SQLException;
  
  public abstract void releaseAnyConnection(Connection paramConnection)
    throws SQLException;
  
  public abstract Connection getConnection(String paramString)
    throws SQLException;
  
  public abstract void releaseConnection(String paramString, Connection paramConnection)
    throws SQLException;
  
  public abstract boolean supportsAggressiveRelease();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.jdbc.connections.spi.MultiTenantConnectionProvider
 * JD-Core Version:    0.7.0.1
 */