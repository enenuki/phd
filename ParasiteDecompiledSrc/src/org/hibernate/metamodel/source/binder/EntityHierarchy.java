package org.hibernate.metamodel.source.binder;

import org.hibernate.metamodel.binding.InheritanceType;

public abstract interface EntityHierarchy
{
  public abstract InheritanceType getHierarchyInheritanceType();
  
  public abstract RootEntitySource getRootEntitySource();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.binder.EntityHierarchy
 * JD-Core Version:    0.7.0.1
 */