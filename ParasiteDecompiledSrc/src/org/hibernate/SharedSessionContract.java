package org.hibernate;

import java.io.Serializable;

public abstract interface SharedSessionContract
  extends Serializable
{
  public abstract String getTenantIdentifier();
  
  public abstract Transaction beginTransaction();
  
  public abstract Transaction getTransaction();
  
  public abstract Query getNamedQuery(String paramString);
  
  public abstract Query createQuery(String paramString);
  
  public abstract SQLQuery createSQLQuery(String paramString);
  
  public abstract Criteria createCriteria(Class paramClass);
  
  public abstract Criteria createCriteria(Class paramClass, String paramString);
  
  public abstract Criteria createCriteria(String paramString);
  
  public abstract Criteria createCriteria(String paramString1, String paramString2);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.SharedSessionContract
 * JD-Core Version:    0.7.0.1
 */