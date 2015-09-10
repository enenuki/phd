package org.hibernate.service.jdbc.dialect.spi;

import java.sql.DatabaseMetaData;
import org.hibernate.dialect.Dialect;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.service.Service;

public abstract interface DialectResolver
  extends Service
{
  public abstract Dialect resolveDialect(DatabaseMetaData paramDatabaseMetaData)
    throws JDBCConnectionException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.jdbc.dialect.spi.DialectResolver
 * JD-Core Version:    0.7.0.1
 */