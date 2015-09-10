package org.hibernate.type;

import org.hibernate.HibernateException;

public abstract interface StringRepresentableType<T>
{
  public abstract String toString(T paramT)
    throws HibernateException;
  
  public abstract T fromStringValue(String paramString)
    throws HibernateException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.StringRepresentableType
 * JD-Core Version:    0.7.0.1
 */