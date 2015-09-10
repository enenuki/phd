package org.hibernate;

import java.util.List;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.ResultTransformer;

public abstract interface Criteria
  extends CriteriaSpecification
{
  public abstract String getAlias();
  
  public abstract Criteria setProjection(Projection paramProjection);
  
  public abstract Criteria add(Criterion paramCriterion);
  
  public abstract Criteria addOrder(Order paramOrder);
  
  public abstract Criteria setFetchMode(String paramString, FetchMode paramFetchMode)
    throws HibernateException;
  
  public abstract Criteria setLockMode(LockMode paramLockMode);
  
  public abstract Criteria setLockMode(String paramString, LockMode paramLockMode);
  
  public abstract Criteria createAlias(String paramString1, String paramString2)
    throws HibernateException;
  
  public abstract Criteria createAlias(String paramString1, String paramString2, JoinType paramJoinType)
    throws HibernateException;
  
  @Deprecated
  public abstract Criteria createAlias(String paramString1, String paramString2, int paramInt)
    throws HibernateException;
  
  public abstract Criteria createAlias(String paramString1, String paramString2, JoinType paramJoinType, Criterion paramCriterion)
    throws HibernateException;
  
  @Deprecated
  public abstract Criteria createAlias(String paramString1, String paramString2, int paramInt, Criterion paramCriterion)
    throws HibernateException;
  
  public abstract Criteria createCriteria(String paramString)
    throws HibernateException;
  
  public abstract Criteria createCriteria(String paramString, JoinType paramJoinType)
    throws HibernateException;
  
  @Deprecated
  public abstract Criteria createCriteria(String paramString, int paramInt)
    throws HibernateException;
  
  public abstract Criteria createCriteria(String paramString1, String paramString2)
    throws HibernateException;
  
  public abstract Criteria createCriteria(String paramString1, String paramString2, JoinType paramJoinType)
    throws HibernateException;
  
  @Deprecated
  public abstract Criteria createCriteria(String paramString1, String paramString2, int paramInt)
    throws HibernateException;
  
  public abstract Criteria createCriteria(String paramString1, String paramString2, JoinType paramJoinType, Criterion paramCriterion)
    throws HibernateException;
  
  @Deprecated
  public abstract Criteria createCriteria(String paramString1, String paramString2, int paramInt, Criterion paramCriterion)
    throws HibernateException;
  
  public abstract Criteria setResultTransformer(ResultTransformer paramResultTransformer);
  
  public abstract Criteria setMaxResults(int paramInt);
  
  public abstract Criteria setFirstResult(int paramInt);
  
  public abstract boolean isReadOnlyInitialized();
  
  public abstract boolean isReadOnly();
  
  public abstract Criteria setReadOnly(boolean paramBoolean);
  
  public abstract Criteria setFetchSize(int paramInt);
  
  public abstract Criteria setTimeout(int paramInt);
  
  public abstract Criteria setCacheable(boolean paramBoolean);
  
  public abstract Criteria setCacheRegion(String paramString);
  
  public abstract Criteria setComment(String paramString);
  
  public abstract Criteria setFlushMode(FlushMode paramFlushMode);
  
  public abstract Criteria setCacheMode(CacheMode paramCacheMode);
  
  public abstract List list()
    throws HibernateException;
  
  public abstract ScrollableResults scroll()
    throws HibernateException;
  
  public abstract ScrollableResults scroll(ScrollMode paramScrollMode)
    throws HibernateException;
  
  public abstract Object uniqueResult()
    throws HibernateException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.Criteria
 * JD-Core Version:    0.7.0.1
 */