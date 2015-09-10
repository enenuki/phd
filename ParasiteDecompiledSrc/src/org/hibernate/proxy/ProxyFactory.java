package org.hibernate.proxy;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Set;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.CompositeType;

public abstract interface ProxyFactory
{
  public abstract void postInstantiate(String paramString, Class paramClass, Set paramSet, Method paramMethod1, Method paramMethod2, CompositeType paramCompositeType)
    throws HibernateException;
  
  public abstract HibernateProxy getProxy(Serializable paramSerializable, SessionImplementor paramSessionImplementor)
    throws HibernateException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.proxy.ProxyFactory
 * JD-Core Version:    0.7.0.1
 */