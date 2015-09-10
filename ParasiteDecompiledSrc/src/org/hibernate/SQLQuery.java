package org.hibernate;

import org.hibernate.type.Type;

public abstract interface SQLQuery
  extends Query
{
  public abstract SQLQuery addSynchronizedQuerySpace(String paramString);
  
  public abstract SQLQuery addSynchronizedEntityName(String paramString)
    throws MappingException;
  
  public abstract SQLQuery addSynchronizedEntityClass(Class paramClass)
    throws MappingException;
  
  public abstract SQLQuery setResultSetMapping(String paramString);
  
  public abstract SQLQuery addScalar(String paramString);
  
  public abstract SQLQuery addScalar(String paramString, Type paramType);
  
  public abstract RootReturn addRoot(String paramString1, String paramString2);
  
  public abstract RootReturn addRoot(String paramString, Class paramClass);
  
  public abstract SQLQuery addEntity(String paramString);
  
  public abstract SQLQuery addEntity(String paramString1, String paramString2);
  
  public abstract SQLQuery addEntity(String paramString1, String paramString2, LockMode paramLockMode);
  
  public abstract SQLQuery addEntity(Class paramClass);
  
  public abstract SQLQuery addEntity(String paramString, Class paramClass);
  
  public abstract SQLQuery addEntity(String paramString, Class paramClass, LockMode paramLockMode);
  
  public abstract FetchReturn addFetch(String paramString1, String paramString2, String paramString3);
  
  public abstract SQLQuery addJoin(String paramString1, String paramString2);
  
  public abstract SQLQuery addJoin(String paramString1, String paramString2, String paramString3);
  
  public abstract SQLQuery addJoin(String paramString1, String paramString2, LockMode paramLockMode);
  
  public static abstract interface FetchReturn
  {
    public abstract FetchReturn setLockMode(LockMode paramLockMode);
    
    public abstract FetchReturn addProperty(String paramString1, String paramString2);
    
    public abstract SQLQuery.ReturnProperty addProperty(String paramString);
  }
  
  public static abstract interface RootReturn
  {
    public abstract RootReturn setLockMode(LockMode paramLockMode);
    
    public abstract RootReturn setDiscriminatorAlias(String paramString);
    
    public abstract RootReturn addProperty(String paramString1, String paramString2);
    
    public abstract SQLQuery.ReturnProperty addProperty(String paramString);
  }
  
  public static abstract interface ReturnProperty
  {
    public abstract ReturnProperty addColumnAlias(String paramString);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.SQLQuery
 * JD-Core Version:    0.7.0.1
 */