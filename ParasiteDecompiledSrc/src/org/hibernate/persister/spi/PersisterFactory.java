package org.hibernate.persister.spi;

import org.hibernate.HibernateException;
import org.hibernate.cache.spi.access.CollectionRegionAccessStrategy;
import org.hibernate.cache.spi.access.EntityRegionAccessStrategy;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.mapping.Collection;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.metamodel.binding.EntityBinding;
import org.hibernate.metamodel.binding.PluralAttributeBinding;
import org.hibernate.metamodel.source.MetadataImplementor;
import org.hibernate.persister.collection.CollectionPersister;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.service.Service;

public abstract interface PersisterFactory
  extends Service
{
  public abstract EntityPersister createEntityPersister(PersistentClass paramPersistentClass, EntityRegionAccessStrategy paramEntityRegionAccessStrategy, SessionFactoryImplementor paramSessionFactoryImplementor, Mapping paramMapping)
    throws HibernateException;
  
  public abstract EntityPersister createEntityPersister(EntityBinding paramEntityBinding, EntityRegionAccessStrategy paramEntityRegionAccessStrategy, SessionFactoryImplementor paramSessionFactoryImplementor, Mapping paramMapping)
    throws HibernateException;
  
  public abstract CollectionPersister createCollectionPersister(Configuration paramConfiguration, Collection paramCollection, CollectionRegionAccessStrategy paramCollectionRegionAccessStrategy, SessionFactoryImplementor paramSessionFactoryImplementor)
    throws HibernateException;
  
  public abstract CollectionPersister createCollectionPersister(MetadataImplementor paramMetadataImplementor, PluralAttributeBinding paramPluralAttributeBinding, CollectionRegionAccessStrategy paramCollectionRegionAccessStrategy, SessionFactoryImplementor paramSessionFactoryImplementor)
    throws HibernateException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.persister.spi.PersisterFactory
 * JD-Core Version:    0.7.0.1
 */