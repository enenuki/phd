package org.hibernate.mapping;

import org.hibernate.HibernateException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.Mapping;

public abstract interface RelationalModel
{
  public abstract String sqlCreateString(Dialect paramDialect, Mapping paramMapping, String paramString1, String paramString2)
    throws HibernateException;
  
  public abstract String sqlDropString(Dialect paramDialect, String paramString1, String paramString2);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.RelationalModel
 * JD-Core Version:    0.7.0.1
 */