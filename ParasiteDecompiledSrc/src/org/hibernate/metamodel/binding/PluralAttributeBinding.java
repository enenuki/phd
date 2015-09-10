package org.hibernate.metamodel.binding;

import java.util.Comparator;
import java.util.Map;
import org.hibernate.metamodel.domain.PluralAttribute;
import org.hibernate.metamodel.relational.TableSpecification;
import org.hibernate.persister.collection.CollectionPersister;

public abstract interface PluralAttributeBinding
  extends AssociationAttributeBinding
{
  public abstract PluralAttribute getAttribute();
  
  public abstract CollectionKey getCollectionKey();
  
  public abstract AbstractCollectionElement getCollectionElement();
  
  public abstract TableSpecification getCollectionTable();
  
  public abstract boolean isMutable();
  
  public abstract Caching getCaching();
  
  public abstract Class<? extends CollectionPersister> getCollectionPersisterClass();
  
  public abstract String getCustomLoaderName();
  
  public abstract CustomSQL getCustomSqlInsert();
  
  public abstract CustomSQL getCustomSqlUpdate();
  
  public abstract CustomSQL getCustomSqlDelete();
  
  public abstract CustomSQL getCustomSqlDeleteAll();
  
  public abstract boolean isOrphanDelete();
  
  public abstract String getWhere();
  
  public abstract boolean isSorted();
  
  public abstract Comparator getComparator();
  
  public abstract int getBatchSize();
  
  public abstract Map getFilterMap();
  
  public abstract boolean isInverse();
  
  public abstract String getOrderBy();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.binding.PluralAttributeBinding
 * JD-Core Version:    0.7.0.1
 */