package org.hibernate.service.spi;

import org.hibernate.cfg.Configuration;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.metamodel.source.MetadataImplementor;
import org.hibernate.service.Service;

public abstract interface SessionFactoryServiceInitiator<R extends Service>
  extends ServiceInitiator<R>
{
  public abstract R initiateService(SessionFactoryImplementor paramSessionFactoryImplementor, Configuration paramConfiguration, ServiceRegistryImplementor paramServiceRegistryImplementor);
  
  public abstract R initiateService(SessionFactoryImplementor paramSessionFactoryImplementor, MetadataImplementor paramMetadataImplementor, ServiceRegistryImplementor paramServiceRegistryImplementor);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.spi.SessionFactoryServiceInitiator
 * JD-Core Version:    0.7.0.1
 */