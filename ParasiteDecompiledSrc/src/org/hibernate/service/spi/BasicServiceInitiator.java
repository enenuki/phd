package org.hibernate.service.spi;

import java.util.Map;
import org.hibernate.service.Service;

public abstract interface BasicServiceInitiator<R extends Service>
  extends ServiceInitiator<R>
{
  public abstract R initiateService(Map paramMap, ServiceRegistryImplementor paramServiceRegistryImplementor);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.spi.BasicServiceInitiator
 * JD-Core Version:    0.7.0.1
 */