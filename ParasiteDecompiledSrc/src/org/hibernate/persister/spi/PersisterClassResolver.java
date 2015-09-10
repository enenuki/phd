package org.hibernate.persister.spi;

import org.hibernate.mapping.Collection;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.metamodel.binding.EntityBinding;
import org.hibernate.metamodel.binding.PluralAttributeBinding;
import org.hibernate.persister.collection.CollectionPersister;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.service.Service;

public abstract interface PersisterClassResolver
  extends Service
{
  public abstract Class<? extends EntityPersister> getEntityPersisterClass(PersistentClass paramPersistentClass);
  
  public abstract Class<? extends EntityPersister> getEntityPersisterClass(EntityBinding paramEntityBinding);
  
  public abstract Class<? extends CollectionPersister> getCollectionPersisterClass(Collection paramCollection);
  
  public abstract Class<? extends CollectionPersister> getCollectionPersisterClass(PluralAttributeBinding paramPluralAttributeBinding);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.persister.spi.PersisterClassResolver
 * JD-Core Version:    0.7.0.1
 */