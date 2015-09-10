package org.hibernate.proxy;

import java.io.Serializable;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;

public abstract interface LazyInitializer
{
  public abstract void initialize()
    throws HibernateException;
  
  public abstract Serializable getIdentifier();
  
  public abstract void setIdentifier(Serializable paramSerializable);
  
  public abstract String getEntityName();
  
  public abstract Class getPersistentClass();
  
  public abstract boolean isUninitialized();
  
  public abstract Object getImplementation();
  
  public abstract Object getImplementation(SessionImplementor paramSessionImplementor)
    throws HibernateException;
  
  public abstract void setImplementation(Object paramObject);
  
  public abstract boolean isReadOnlySettingAvailable();
  
  public abstract boolean isReadOnly();
  
  public abstract void setReadOnly(boolean paramBoolean);
  
  public abstract SessionImplementor getSession();
  
  public abstract void setSession(SessionImplementor paramSessionImplementor)
    throws HibernateException;
  
  public abstract void unsetSession();
  
  public abstract void setUnwrap(boolean paramBoolean);
  
  public abstract boolean isUnwrap();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.proxy.LazyInitializer
 * JD-Core Version:    0.7.0.1
 */