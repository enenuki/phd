package org.hibernate.metamodel.source.binder;

import org.hibernate.FetchMode;
import org.hibernate.engine.FetchStyle;
import org.hibernate.engine.FetchTiming;
import org.hibernate.engine.spi.CascadeStyle;

public abstract interface AssociationAttributeSource
  extends AttributeSource
{
  public abstract Iterable<CascadeStyle> getCascadeStyles();
  
  public abstract FetchMode getFetchMode();
  
  public abstract FetchTiming getFetchTiming();
  
  public abstract FetchStyle getFetchStyle();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.binder.AssociationAttributeSource
 * JD-Core Version:    0.7.0.1
 */