package org.hibernate.service;

public abstract interface ServiceRegistry
{
  public abstract ServiceRegistry getParentServiceRegistry();
  
  public abstract <R extends Service> R getService(Class<R> paramClass);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.ServiceRegistry
 * JD-Core Version:    0.7.0.1
 */