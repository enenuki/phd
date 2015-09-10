package org.hibernate.metamodel.source.binder;

import org.hibernate.internal.util.Value;

public abstract interface ComponentAttributeSource
  extends SingularAttributeSource, AttributeSourceContainer
{
  public abstract String getClassName();
  
  public abstract Value<Class<?>> getClassReference();
  
  public abstract String getParentReferenceAttributeName();
  
  public abstract String getExplicitTuplizerClassName();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.binder.ComponentAttributeSource
 * JD-Core Version:    0.7.0.1
 */