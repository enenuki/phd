package org.hibernate.service.spi;

import org.hibernate.service.Service;

public abstract interface ServiceInitiator<R extends Service>
{
  public abstract Class<R> getServiceInitiated();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.spi.ServiceInitiator
 * JD-Core Version:    0.7.0.1
 */