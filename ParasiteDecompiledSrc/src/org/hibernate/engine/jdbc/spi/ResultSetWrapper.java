package org.hibernate.engine.jdbc.spi;

import java.sql.ResultSet;
import org.hibernate.engine.jdbc.ColumnNameCache;

public abstract interface ResultSetWrapper
{
  public abstract ResultSet wrap(ResultSet paramResultSet, ColumnNameCache paramColumnNameCache);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.engine.jdbc.spi.ResultSetWrapper
 * JD-Core Version:    0.7.0.1
 */