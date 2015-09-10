package org.hibernate.metamodel.source.binder;

public abstract interface ToOneAttributeSource
  extends SingularAttributeSource, AssociationAttributeSource
{
  public abstract String getReferencedEntityName();
  
  public abstract String getReferencedEntityAttributeName();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.binder.ToOneAttributeSource
 * JD-Core Version:    0.7.0.1
 */