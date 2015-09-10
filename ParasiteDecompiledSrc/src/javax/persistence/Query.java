package javax.persistence;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract interface Query
{
  public abstract List getResultList();
  
  public abstract Object getSingleResult();
  
  public abstract int executeUpdate();
  
  public abstract Query setMaxResults(int paramInt);
  
  public abstract int getMaxResults();
  
  public abstract Query setFirstResult(int paramInt);
  
  public abstract int getFirstResult();
  
  public abstract Query setHint(String paramString, Object paramObject);
  
  public abstract Map<String, Object> getHints();
  
  public abstract <T> Query setParameter(Parameter<T> paramParameter, T paramT);
  
  public abstract Query setParameter(Parameter<Calendar> paramParameter, Calendar paramCalendar, TemporalType paramTemporalType);
  
  public abstract Query setParameter(Parameter<Date> paramParameter, Date paramDate, TemporalType paramTemporalType);
  
  public abstract Query setParameter(String paramString, Object paramObject);
  
  public abstract Query setParameter(String paramString, Calendar paramCalendar, TemporalType paramTemporalType);
  
  public abstract Query setParameter(String paramString, Date paramDate, TemporalType paramTemporalType);
  
  public abstract Query setParameter(int paramInt, Object paramObject);
  
  public abstract Query setParameter(int paramInt, Calendar paramCalendar, TemporalType paramTemporalType);
  
  public abstract Query setParameter(int paramInt, Date paramDate, TemporalType paramTemporalType);
  
  public abstract Set<Parameter<?>> getParameters();
  
  public abstract Parameter<?> getParameter(String paramString);
  
  public abstract <T> Parameter<T> getParameter(String paramString, Class<T> paramClass);
  
  public abstract Parameter<?> getParameter(int paramInt);
  
  public abstract <T> Parameter<T> getParameter(int paramInt, Class<T> paramClass);
  
  public abstract boolean isBound(Parameter<?> paramParameter);
  
  public abstract <T> T getParameterValue(Parameter<T> paramParameter);
  
  public abstract Object getParameterValue(String paramString);
  
  public abstract Object getParameterValue(int paramInt);
  
  public abstract Query setFlushMode(FlushModeType paramFlushModeType);
  
  public abstract FlushModeType getFlushMode();
  
  public abstract Query setLockMode(LockModeType paramLockModeType);
  
  public abstract LockModeType getLockMode();
  
  public abstract <T> T unwrap(Class<T> paramClass);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.Query
 * JD-Core Version:    0.7.0.1
 */