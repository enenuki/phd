package org.hibernate.metamodel.source.binder;

public abstract interface TableSource
{
  public abstract String getExplicitSchemaName();
  
  public abstract String getExplicitCatalogName();
  
  public abstract String getExplicitTableName();
  
  public abstract String getLogicalName();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.binder.TableSource
 * JD-Core Version:    0.7.0.1
 */