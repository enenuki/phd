package org.hibernate.metamodel.source.binder;

import org.hibernate.EntityMode;
import org.hibernate.engine.OptimisticLockStyle;
import org.hibernate.metamodel.binding.Caching;

public abstract interface RootEntitySource
  extends EntitySource
{
  public abstract IdentifierSource getIdentifierSource();
  
  public abstract SingularAttributeSource getVersioningAttributeSource();
  
  public abstract DiscriminatorSource getDiscriminatorSource();
  
  public abstract EntityMode getEntityMode();
  
  public abstract boolean isMutable();
  
  public abstract boolean isExplicitPolymorphism();
  
  public abstract String getWhere();
  
  public abstract String getRowId();
  
  public abstract OptimisticLockStyle getOptimisticLockStyle();
  
  public abstract Caching getCaching();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.binder.RootEntitySource
 * JD-Core Version:    0.7.0.1
 */