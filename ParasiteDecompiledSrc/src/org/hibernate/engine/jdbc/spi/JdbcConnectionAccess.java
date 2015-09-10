package org.hibernate.engine.jdbc.spi;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

public abstract interface JdbcConnectionAccess
  extends Serializable
{
  public abstract Connection obtainConnection()
    throws SQLException;
  
  public abstract void releaseConnection(Connection paramConnection)
    throws SQLException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.spi.JdbcConnectionAccess
 * JD-Core Version:    0.7.0.1
 */