package org.hibernate.metamodel.source.binder;

public abstract interface AttributeSource
{
  public abstract String getName();
  
  public abstract boolean isSingular();
  
  public abstract ExplicitHibernateTypeSource getTypeInformation();
  
  public abstract String getPropertyAccessorName();
  
  public abstract boolean isIncludedInOptimisticLocking();
  
  public abstract Iterable<MetaAttributeSource> metaAttributes();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.binder.AttributeSource
 * JD-Core Version:    0.7.0.1
 */