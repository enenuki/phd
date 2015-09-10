package org.hibernate.integrator.spi;

import org.hibernate.service.Service;

public abstract interface IntegratorService
  extends Service
{
  public abstract Iterable<Integrator> getIntegrators();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.integrator.spi.IntegratorService
 * JD-Core Version:    0.7.0.1
 */