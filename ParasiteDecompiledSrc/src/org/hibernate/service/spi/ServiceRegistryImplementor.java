package org.hibernate.service.spi;

import org.hibernate.service.Service;
import org.hibernate.service.ServiceRegistry;

public abstract interface ServiceRegistryImplementor
  extends ServiceRegistry
{
  public abstract <R extends Service> ServiceBinding<R> locateServiceBinding(Class<R> paramClass);
  
  public abstract void destroy();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.spi.ServiceRegistryImplementor
 * JD-Core Version:    0.7.0.1
 */