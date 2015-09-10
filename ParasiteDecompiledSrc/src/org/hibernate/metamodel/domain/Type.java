package org.hibernate.metamodel.domain;

import org.hibernate.internal.util.Value;

public abstract interface Type
{
  public abstract String getName();
  
  public abstract String getClassName();
  
  public abstract Class<?> getClassReference();
  
  public abstract Value<Class<?>> getClassReferenceUnresolved();
  
  public abstract boolean isAssociation();
  
  public abstract boolean isComponent();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.domain.Type
 * JD-Core Version:    0.7.0.1
 */