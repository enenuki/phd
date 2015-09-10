package org.hibernate.integrator.spi;

import org.hibernate.cfg.Configuration;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.metamodel.source.MetadataImplementor;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;

public abstract interface Integrator
{
  public abstract void integrate(Configuration paramConfiguration, SessionFactoryImplementor paramSessionFactoryImplementor, SessionFactoryServiceRegistry paramSessionFactoryServiceRegistry);
  
  public abstract void integrate(MetadataImplementor paramMetadataImplementor, SessionFactoryImplementor paramSessionFactoryImplementor, SessionFactoryServiceRegistry paramSessionFactoryServiceRegistry);
  
  public abstract void disintegrate(SessionFactoryImplementor paramSessionFactoryImplementor, SessionFactoryServiceRegistry paramSessionFactoryServiceRegistry);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.integrator.spi.Integrator
 * JD-Core Version:    0.7.0.1
 */