package org.hibernate.type;

import org.hibernate.dialect.Dialect;

public abstract interface LiteralType<T>
{
  public abstract String objectToSQLString(T paramT, Dialect paramDialect)
    throws Exception;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.LiteralType
 * JD-Core Version:    0.7.0.1
 */