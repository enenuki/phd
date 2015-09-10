package org.hibernate.service.spi;

import org.hibernate.cfg.Configuration;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.metamodel.source.MetadataImplementor;
import org.hibernate.service.Service;
import org.hibernate.service.internal.SessionFactoryServiceRegistryImpl;

public abstract interface SessionFactoryServiceRegistryFactory
  extends Service
{
  public abstract SessionFactoryServiceRegistryImpl buildServiceRegistry(SessionFactoryImplementor paramSessionFactoryImplementor, Configuration paramConfiguration);
  
  public abstract SessionFactoryServiceRegistryImpl buildServiceRegistry(SessionFactoryImplementor paramSessionFactoryImplementor, MetadataImplementor paramMetadataImplementor);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.service.spi.SessionFactoryServiceRegistryFactory
 * JD-Core Version:    0.7.0.1
 */