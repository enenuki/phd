package org.hibernate.property;

import java.io.Serializable;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Map;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;

public abstract interface Getter
  extends Serializable
{
  public abstract Object get(Object paramObject)
    throws HibernateException;
  
  public abstract Object getForInsert(Object paramObject, Map paramMap, SessionImplementor paramSessionImplementor)
    throws HibernateException;
  
  public abstract Member getMember();
  
  public abstract Class getReturnType();
  
  public abstract String getMethodName();
  
  public abstract Method getMethod();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.property.Getter
 * JD-Core Version:    0.7.0.1
 */