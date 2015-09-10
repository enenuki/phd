package org.hibernate.criterion;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.spi.TypedValue;
import org.hibernate.type.Type;

public abstract interface CriteriaQuery
{
  public abstract SessionFactoryImplementor getFactory();
  
  public abstract String getColumn(Criteria paramCriteria, String paramString)
    throws HibernateException;
  
  public abstract String[] getColumns(String paramString, Criteria paramCriteria)
    throws HibernateException;
  
  public abstract String[] findColumns(String paramString, Criteria paramCriteria)
    throws HibernateException;
  
  public abstract Type getType(Criteria paramCriteria, String paramString)
    throws HibernateException;
  
  public abstract String[] getColumnsUsingProjection(Criteria paramCriteria, String paramString)
    throws HibernateException;
  
  public abstract Type getTypeUsingProjection(Criteria paramCriteria, String paramString)
    throws HibernateException;
  
  public abstract TypedValue getTypedValue(Criteria paramCriteria, String paramString, Object paramObject)
    throws HibernateException;
  
  public abstract String getEntityName(Criteria paramCriteria);
  
  public abstract String getEntityName(Criteria paramCriteria, String paramString);
  
  public abstract String getSQLAlias(Criteria paramCriteria);
  
  public abstract String getSQLAlias(Criteria paramCriteria, String paramString);
  
  public abstract String getPropertyName(String paramString);
  
  public abstract String[] getIdentifierColumns(Criteria paramCriteria);
  
  public abstract Type getIdentifierType(Criteria paramCriteria);
  
  public abstract TypedValue getTypedIdentifierValue(Criteria paramCriteria, Object paramObject);
  
  public abstract String generateSQLAlias();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.criterion.CriteriaQuery
 * JD-Core Version:    0.7.0.1
 */