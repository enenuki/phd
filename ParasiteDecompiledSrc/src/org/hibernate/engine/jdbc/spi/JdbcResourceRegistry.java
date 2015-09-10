package org.hibernate.engine.jdbc.spi;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.Statement;

public abstract interface JdbcResourceRegistry
  extends Serializable
{
  public abstract void register(Statement paramStatement);
  
  public abstract void registerLastQuery(Statement paramStatement);
  
  public abstract void cancelLastQuery();
  
  public abstract void release(Statement paramStatement);
  
  public abstract void register(ResultSet paramResultSet);
  
  public abstract void release(ResultSet paramResultSet);
  
  public abstract boolean hasRegisteredResources();
  
  public abstract void releaseResources();
  
  public abstract void close();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.spi.JdbcResourceRegistry
 * JD-Core Version:    0.7.0.1
 */