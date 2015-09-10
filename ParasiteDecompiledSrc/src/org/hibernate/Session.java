package org.hibernate;

import java.io.Serializable;
import java.sql.Connection;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.jdbc.Work;
import org.hibernate.stat.SessionStatistics;

public abstract interface Session
  extends SharedSessionContract
{
  public abstract SharedSessionBuilder sessionWithOptions();
  
  public abstract void flush()
    throws HibernateException;
  
  public abstract void setFlushMode(FlushMode paramFlushMode);
  
  public abstract FlushMode getFlushMode();
  
  public abstract void setCacheMode(CacheMode paramCacheMode);
  
  public abstract CacheMode getCacheMode();
  
  public abstract SessionFactory getSessionFactory();
  
  public abstract Connection close()
    throws HibernateException;
  
  public abstract void cancelQuery()
    throws HibernateException;
  
  public abstract boolean isOpen();
  
  public abstract boolean isConnected();
  
  public abstract boolean isDirty()
    throws HibernateException;
  
  public abstract boolean isDefaultReadOnly();
  
  public abstract void setDefaultReadOnly(boolean paramBoolean);
  
  public abstract Serializable getIdentifier(Object paramObject)
    throws HibernateException;
  
  public abstract boolean contains(Object paramObject);
  
  public abstract void evict(Object paramObject)
    throws HibernateException;
  
  @Deprecated
  public abstract Object load(Class paramClass, Serializable paramSerializable, LockMode paramLockMode)
    throws HibernateException;
  
  public abstract Object load(Class paramClass, Serializable paramSerializable, LockOptions paramLockOptions)
    throws HibernateException;
  
  @Deprecated
  public abstract Object load(String paramString, Serializable paramSerializable, LockMode paramLockMode)
    throws HibernateException;
  
  public abstract Object load(String paramString, Serializable paramSerializable, LockOptions paramLockOptions)
    throws HibernateException;
  
  public abstract Object load(Class paramClass, Serializable paramSerializable)
    throws HibernateException;
  
  public abstract Object load(String paramString, Serializable paramSerializable)
    throws HibernateException;
  
  public abstract void load(Object paramObject, Serializable paramSerializable)
    throws HibernateException;
  
  public abstract void replicate(Object paramObject, ReplicationMode paramReplicationMode)
    throws HibernateException;
  
  public abstract void replicate(String paramString, Object paramObject, ReplicationMode paramReplicationMode)
    throws HibernateException;
  
  public abstract Serializable save(Object paramObject)
    throws HibernateException;
  
  public abstract Serializable save(String paramString, Object paramObject)
    throws HibernateException;
  
  public abstract void saveOrUpdate(Object paramObject)
    throws HibernateException;
  
  public abstract void saveOrUpdate(String paramString, Object paramObject)
    throws HibernateException;
  
  public abstract void update(Object paramObject)
    throws HibernateException;
  
  public abstract void update(String paramString, Object paramObject)
    throws HibernateException;
  
  public abstract Object merge(Object paramObject)
    throws HibernateException;
  
  public abstract Object merge(String paramString, Object paramObject)
    throws HibernateException;
  
  public abstract void persist(Object paramObject)
    throws HibernateException;
  
  public abstract void persist(String paramString, Object paramObject)
    throws HibernateException;
  
  public abstract void delete(Object paramObject)
    throws HibernateException;
  
  public abstract void delete(String paramString, Object paramObject)
    throws HibernateException;
  
  @Deprecated
  public abstract void lock(Object paramObject, LockMode paramLockMode)
    throws HibernateException;
  
  @Deprecated
  public abstract void lock(String paramString, Object paramObject, LockMode paramLockMode)
    throws HibernateException;
  
  public abstract LockRequest buildLockRequest(LockOptions paramLockOptions);
  
  public abstract void refresh(Object paramObject)
    throws HibernateException;
  
  public abstract void refresh(String paramString, Object paramObject)
    throws HibernateException;
  
  @Deprecated
  public abstract void refresh(Object paramObject, LockMode paramLockMode)
    throws HibernateException;
  
  public abstract void refresh(Object paramObject, LockOptions paramLockOptions)
    throws HibernateException;
  
  public abstract void refresh(String paramString, Object paramObject, LockOptions paramLockOptions)
    throws HibernateException;
  
  public abstract LockMode getCurrentLockMode(Object paramObject)
    throws HibernateException;
  
  public abstract Query createFilter(Object paramObject, String paramString);
  
  public abstract void clear();
  
  public abstract Object get(Class paramClass, Serializable paramSerializable)
    throws HibernateException;
  
  @Deprecated
  public abstract Object get(Class paramClass, Serializable paramSerializable, LockMode paramLockMode)
    throws HibernateException;
  
  public abstract Object get(Class paramClass, Serializable paramSerializable, LockOptions paramLockOptions)
    throws HibernateException;
  
  public abstract Object get(String paramString, Serializable paramSerializable)
    throws HibernateException;
  
  @Deprecated
  public abstract Object get(String paramString, Serializable paramSerializable, LockMode paramLockMode)
    throws HibernateException;
  
  public abstract Object get(String paramString, Serializable paramSerializable, LockOptions paramLockOptions)
    throws HibernateException;
  
  public abstract String getEntityName(Object paramObject)
    throws HibernateException;
  
  public abstract Filter enableFilter(String paramString);
  
  public abstract Filter getEnabledFilter(String paramString);
  
  public abstract void disableFilter(String paramString);
  
  public abstract SessionStatistics getStatistics();
  
  public abstract boolean isReadOnly(Object paramObject);
  
  public abstract void setReadOnly(Object paramObject, boolean paramBoolean);
  
  public abstract void doWork(Work paramWork)
    throws HibernateException;
  
  public abstract <T> T doReturningWork(ReturningWork<T> paramReturningWork)
    throws HibernateException;
  
  public abstract Connection disconnect()
    throws HibernateException;
  
  public abstract void reconnect(Connection paramConnection)
    throws HibernateException;
  
  public abstract boolean isFetchProfileEnabled(String paramString)
    throws UnknownProfileException;
  
  public abstract void enableFetchProfile(String paramString)
    throws UnknownProfileException;
  
  public abstract void disableFetchProfile(String paramString)
    throws UnknownProfileException;
  
  public abstract TypeHelper getTypeHelper();
  
  public abstract LobHelper getLobHelper();
  
  public static abstract interface LockRequest
  {
    public static final int PESSIMISTIC_NO_WAIT = 0;
    public static final int PESSIMISTIC_WAIT_FOREVER = -1;
    
    public abstract LockMode getLockMode();
    
    public abstract LockRequest setLockMode(LockMode paramLockMode);
    
    public abstract int getTimeOut();
    
    public abstract LockRequest setTimeOut(int paramInt);
    
    public abstract boolean getScope();
    
    public abstract LockRequest setScope(boolean paramBoolean);
    
    public abstract void lock(String paramString, Object paramObject)
      throws HibernateException;
    
    public abstract void lock(Object paramObject)
      throws HibernateException;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.Session
 * JD-Core Version:    0.7.0.1
 */