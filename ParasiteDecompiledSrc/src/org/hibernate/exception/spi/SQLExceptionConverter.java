package org.hibernate.exception.spi;

import java.io.Serializable;
import java.sql.SQLException;
import org.hibernate.JDBCException;

public abstract interface SQLExceptionConverter
  extends Serializable
{
  public abstract JDBCException convert(SQLException paramSQLException, String paramString1, String paramString2);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.exception.spi.SQLExceptionConverter
 * JD-Core Version:    0.7.0.1
 */