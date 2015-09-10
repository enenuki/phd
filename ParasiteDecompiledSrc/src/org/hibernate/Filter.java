package org.hibernate;

import java.util.Collection;
import org.hibernate.engine.spi.FilterDefinition;

public abstract interface Filter
{
  public abstract String getName();
  
  public abstract FilterDefinition getFilterDefinition();
  
  public abstract Filter setParameter(String paramString, Object paramObject);
  
  public abstract Filter setParameterList(String paramString, Collection paramCollection);
  
  public abstract Filter setParameterList(String paramString, Object[] paramArrayOfObject);
  
  public abstract void validate()
    throws HibernateException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.Filter
 * JD-Core Version:    0.7.0.1
 */