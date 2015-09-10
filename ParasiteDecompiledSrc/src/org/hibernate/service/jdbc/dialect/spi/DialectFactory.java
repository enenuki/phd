package org.hibernate.service.jdbc.dialect.spi;

import java.sql.Connection;
import java.util.Map;
import org.hibernate.HibernateException;
import org.hibernate.dialect.Dialect;
import org.hibernate.service.Service;

public abstract interface DialectFactory
  extends Service
{
  public abstract Dialect buildDialect(Map paramMap, Connection paramConnection)
    throws HibernateException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.jdbc.dialect.spi.DialectFactory
 * JD-Core Version:    0.7.0.1
 */