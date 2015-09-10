package javax.persistence;

import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.Metamodel;

public abstract interface EntityManager
{
  public abstract void persist(Object paramObject);
  
  public abstract <T> T merge(T paramT);
  
  public abstract void remove(Object paramObject);
  
  public abstract <T> T find(Class<T> paramClass, Object paramObject);
  
  public abstract <T> T find(Class<T> paramClass, Object paramObject, Map<String, Object> paramMap);
  
  public abstract <T> T find(Class<T> paramClass, Object paramObject, LockModeType paramLockModeType);
  
  public abstract <T> T find(Class<T> paramClass, Object paramObject, LockModeType paramLockModeType, Map<String, Object> paramMap);
  
  public abstract <T> T getReference(Class<T> paramClass, Object paramObject);
  
  public abstract void flush();
  
  public abstract void setFlushMode(FlushModeType paramFlushModeType);
  
  public abstract FlushModeType getFlushMode();
  
  public abstract void lock(Object paramObject, LockModeType paramLockModeType);
  
  public abstract void lock(Object paramObject, LockModeType paramLockModeType, Map<String, Object> paramMap);
  
  public abstract void refresh(Object paramObject);
  
  public abstract void refresh(Object paramObject, Map<String, Object> paramMap);
  
  public abstract void refresh(Object paramObject, LockModeType paramLockModeType);
  
  public abstract void refresh(Object paramObject, LockModeType paramLockModeType, Map<String, Object> paramMap);
  
  public abstract void clear();
  
  public abstract void detach(Object paramObject);
  
  public abstract boolean contains(Object paramObject);
  
  public abstract LockModeType getLockMode(Object paramObject);
  
  public abstract void setProperty(String paramString, Object paramObject);
  
  public abstract Map<String, Object> getProperties();
  
  public abstract Query createQuery(String paramString);
  
  public abstract <T> TypedQuery<T> createQuery(CriteriaQuery<T> paramCriteriaQuery);
  
  public abstract <T> TypedQuery<T> createQuery(String paramString, Class<T> paramClass);
  
  public abstract Query createNamedQuery(String paramString);
  
  public abstract <T> TypedQuery<T> createNamedQuery(String paramString, Class<T> paramClass);
  
  public abstract Query createNativeQuery(String paramString);
  
  public abstract Query createNativeQuery(String paramString, Class paramClass);
  
  public abstract Query createNativeQuery(String paramString1, String paramString2);
  
  public abstract void joinTransaction();
  
  public abstract <T> T unwrap(Class<T> paramClass);
  
  public abstract Object getDelegate();
  
  public abstract void close();
  
  public abstract boolean isOpen();
  
  public abstract EntityTransaction getTransaction();
  
  public abstract EntityManagerFactory getEntityManagerFactory();
  
  public abstract CriteriaBuilder getCriteriaBuilder();
  
  public abstract Metamodel getMetamodel();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.EntityManager
 * JD-Core Version:    0.7.0.1
 */