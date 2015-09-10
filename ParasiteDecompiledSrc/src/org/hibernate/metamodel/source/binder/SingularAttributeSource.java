package org.hibernate.metamodel.source.binder;

import org.hibernate.mapping.PropertyGeneration;

public abstract interface SingularAttributeSource
  extends AttributeSource, RelationalValueSourceContainer
{
  public abstract boolean isVirtualAttribute();
  
  public abstract SingularAttributeNature getNature();
  
  public abstract boolean isInsertable();
  
  public abstract boolean isUpdatable();
  
  public abstract PropertyGeneration getGeneration();
  
  public abstract boolean isLazy();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.binder.SingularAttributeSource
 * JD-Core Version:    0.7.0.1
 */