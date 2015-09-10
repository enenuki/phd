package org.hibernate.metamodel.source;

import org.hibernate.cache.spi.access.AccessType;

public abstract interface MappingDefaults
{
  public abstract String getPackageName();
  
  public abstract String getSchemaName();
  
  public abstract String getCatalogName();
  
  public abstract String getIdColumnName();
  
  public abstract String getDiscriminatorColumnName();
  
  public abstract String getCascadeStyle();
  
  public abstract String getPropertyAccessorName();
  
  public abstract boolean areAssociationsLazy();
  
  public abstract AccessType getCacheAccessType();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.MappingDefaults
 * JD-Core Version:    0.7.0.1
 */