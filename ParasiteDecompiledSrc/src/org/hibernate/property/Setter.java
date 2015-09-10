package org.hibernate.property;

import java.io.Serializable;
import java.lang.reflect.Method;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionFactoryImplementor;

public abstract interface Setter
  extends Serializable
{
  public abstract void set(Object paramObject1, Object paramObject2, SessionFactoryImplementor paramSessionFactoryImplementor)
    throws HibernateException;
  
  public abstract String getMethodName();
  
  public abstract Method getMethod();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.property.Setter
 * JD-Core Version:    0.7.0.1
 */