package org.hibernate.integrator.spi;

import org.hibernate.service.ServiceRegistryBuilder;

public abstract interface ServiceContributingIntegrator
  extends Integrator
{
  public abstract void prepareServices(ServiceRegistryBuilder paramServiceRegistryBuilder);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.integrator.spi.ServiceContributingIntegrator
 * JD-Core Version:    0.7.0.1
 */