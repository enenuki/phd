package org.hibernate.metamodel.source.binder;

public abstract interface DiscriminatorSource
{
  public abstract RelationalValueSource getDiscriminatorRelationalValueSource();
  
  public abstract String getExplicitHibernateTypeName();
  
  public abstract boolean isForced();
  
  public abstract boolean isInserted();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.binder.DiscriminatorSource
 * JD-Core Version:    0.7.0.1
 */