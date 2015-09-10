package org.hibernate.criterion;

import java.io.Serializable;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.type.Type;

public abstract interface Projection
  extends Serializable
{
  public abstract String toSqlString(Criteria paramCriteria, int paramInt, CriteriaQuery paramCriteriaQuery)
    throws HibernateException;
  
  public abstract String toGroupSqlString(Criteria paramCriteria, CriteriaQuery paramCriteriaQuery)
    throws HibernateException;
  
  public abstract Type[] getTypes(Criteria paramCriteria, CriteriaQuery paramCriteriaQuery)
    throws HibernateException;
  
  public abstract Type[] getTypes(String paramString, Criteria paramCriteria, CriteriaQuery paramCriteriaQuery)
    throws HibernateException;
  
  public abstract String[] getColumnAliases(int paramInt);
  
  public abstract String[] getColumnAliases(String paramString, int paramInt);
  
  public abstract String[] getAliases();
  
  public abstract boolean isGrouped();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.criterion.Projection
 * JD-Core Version:    0.7.0.1
 */