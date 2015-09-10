package org.hibernate.tool.hbm2ddl;

import java.sql.Connection;
import java.sql.SQLException;

public abstract interface ConnectionHelper
{
  public abstract void prepare(boolean paramBoolean)
    throws SQLException;
  
  public abstract Connection getConnection()
    throws SQLException;
  
  public abstract void release()
    throws SQLException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.tool.hbm2ddl.ConnectionHelper
 * JD-Core Version:    0.7.0.1
 */