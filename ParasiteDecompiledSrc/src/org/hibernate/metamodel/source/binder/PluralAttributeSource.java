package org.hibernate.metamodel.source.binder;

import org.hibernate.metamodel.binding.Caching;
import org.hibernate.metamodel.binding.CustomSQL;

public abstract interface PluralAttributeSource
  extends AssociationAttributeSource
{
  public abstract PluralAttributeNature getPluralAttributeNature();
  
  public abstract PluralAttributeKeySource getKeySource();
  
  public abstract PluralAttributeElementSource getElementSource();
  
  public abstract String getExplicitSchemaName();
  
  public abstract String getExplicitCatalogName();
  
  public abstract String getExplicitCollectionTableName();
  
  public abstract String getCollectionTableComment();
  
  public abstract String getCollectionTableCheck();
  
  public abstract Caching getCaching();
  
  public abstract String getCustomPersisterClassName();
  
  public abstract String getWhere();
  
  public abstract boolean isInverse();
  
  public abstract String getCustomLoaderName();
  
  public abstract CustomSQL getCustomSqlInsert();
  
  public abstract CustomSQL getCustomSqlUpdate();
  
  public abstract CustomSQL getCustomSqlDelete();
  
  public abstract CustomSQL getCustomSqlDeleteAll();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.binder.PluralAttributeSource
 * JD-Core Version:    0.7.0.1
 */