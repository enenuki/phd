package org.hibernate.persister.entity;

import org.hibernate.QueryException;
import org.hibernate.type.Type;

public abstract interface PropertyMapping
{
  public abstract Type toType(String paramString)
    throws QueryException;
  
  public abstract String[] toColumns(String paramString1, String paramString2)
    throws QueryException;
  
  public abstract String[] toColumns(String paramString)
    throws QueryException, UnsupportedOperationException;
  
  public abstract Type getType();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.persister.entity.PropertyMapping
 * JD-Core Version:    0.7.0.1
 */