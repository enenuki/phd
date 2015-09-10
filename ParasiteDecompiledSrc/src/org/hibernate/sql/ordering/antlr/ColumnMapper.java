package org.hibernate.sql.ordering.antlr;

import org.hibernate.HibernateException;

public abstract interface ColumnMapper
{
  public abstract String[] map(String paramString)
    throws HibernateException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.sql.ordering.antlr.ColumnMapper
 * JD-Core Version:    0.7.0.1
 */