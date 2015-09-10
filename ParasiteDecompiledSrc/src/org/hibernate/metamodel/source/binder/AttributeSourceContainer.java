package org.hibernate.metamodel.source.binder;

import org.hibernate.metamodel.source.LocalBindingContext;

public abstract interface AttributeSourceContainer
{
  public abstract String getPath();
  
  public abstract Iterable<AttributeSource> attributeSources();
  
  public abstract LocalBindingContext getLocalBindingContext();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.binder.AttributeSourceContainer
 * JD-Core Version:    0.7.0.1
 */