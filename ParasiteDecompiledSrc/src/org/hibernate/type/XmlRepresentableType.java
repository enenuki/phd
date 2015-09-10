package org.hibernate.type;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.SessionFactoryImplementor;

public abstract interface XmlRepresentableType<T>
{
  public abstract String toXMLString(T paramT, SessionFactoryImplementor paramSessionFactoryImplementor)
    throws HibernateException;
  
  public abstract T fromXMLString(String paramString, Mapping paramMapping)
    throws HibernateException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.type.XmlRepresentableType
 * JD-Core Version:    0.7.0.1
 */