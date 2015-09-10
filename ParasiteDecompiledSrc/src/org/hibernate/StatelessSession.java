package org.hibernate;

import java.io.Serializable;
import java.sql.Connection;

public abstract interface StatelessSession
  extends SharedSessionContract
{
  public abstract void close();
  
  public abstract Serializable insert(Object paramObject);
  
  public abstract Serializable insert(String paramString, Object paramObject);
  
  public abstract void update(Object paramObject);
  
  public abstract void update(String paramString, Object paramObject);
  
  public abstract void delete(Object paramObject);
  
  public abstract void delete(String paramString, Object paramObject);
  
  public abstract Object get(String paramString, Serializable paramSerializable);
  
  public abstract Object get(Class paramClass, Serializable paramSerializable);
  
  public abstract Object get(String paramString, Serializable paramSerializable, LockMode paramLockMode);
  
  public abstract Object get(Class paramClass, Serializable paramSerializable, LockMode paramLockMode);
  
  public abstract void refresh(Object paramObject);
  
  public abstract void refresh(String paramString, Object paramObject);
  
  public abstract void refresh(Object paramObject, LockMode paramLockMode);
  
  public abstract void refresh(String paramString, Object paramObject, LockMode paramLockMode);
  
  @Deprecated
  public abstract Connection connection();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.StatelessSession
 * JD-Core Version:    0.7.0.1
 */