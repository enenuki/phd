package javax.persistence;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public abstract interface TypedQuery<X>
  extends Query
{
  public abstract List<X> getResultList();
  
  public abstract X getSingleResult();
  
  public abstract TypedQuery<X> setMaxResults(int paramInt);
  
  public abstract TypedQuery<X> setFirstResult(int paramInt);
  
  public abstract TypedQuery<X> setHint(String paramString, Object paramObject);
  
  public abstract <T> TypedQuery<X> setParameter(Parameter<T> paramParameter, T paramT);
  
  public abstract TypedQuery<X> setParameter(Parameter<Calendar> paramParameter, Calendar paramCalendar, TemporalType paramTemporalType);
  
  public abstract TypedQuery<X> setParameter(Parameter<Date> paramParameter, Date paramDate, TemporalType paramTemporalType);
  
  public abstract TypedQuery<X> setParameter(String paramString, Object paramObject);
  
  public abstract TypedQuery<X> setParameter(String paramString, Calendar paramCalendar, TemporalType paramTemporalType);
  
  public abstract TypedQuery<X> setParameter(String paramString, Date paramDate, TemporalType paramTemporalType);
  
  public abstract TypedQuery<X> setParameter(int paramInt, Object paramObject);
  
  public abstract TypedQuery<X> setParameter(int paramInt, Calendar paramCalendar, TemporalType paramTemporalType);
  
  public abstract TypedQuery<X> setParameter(int paramInt, Date paramDate, TemporalType paramTemporalType);
  
  public abstract TypedQuery<X> setFlushMode(FlushModeType paramFlushModeType);
  
  public abstract TypedQuery<X> setLockMode(LockModeType paramLockModeType);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.TypedQuery
 * JD-Core Version:    0.7.0.1
 */