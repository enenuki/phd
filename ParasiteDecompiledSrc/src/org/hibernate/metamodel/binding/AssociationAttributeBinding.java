package org.hibernate.metamodel.binding;

import org.hibernate.FetchMode;
import org.hibernate.engine.FetchStyle;
import org.hibernate.engine.FetchTiming;
import org.hibernate.engine.spi.CascadeStyle;

public abstract interface AssociationAttributeBinding
  extends AttributeBinding
{
  public abstract CascadeStyle getCascadeStyle();
  
  public abstract void setCascadeStyles(Iterable<CascadeStyle> paramIterable);
  
  public abstract FetchTiming getFetchTiming();
  
  public abstract void setFetchTiming(FetchTiming paramFetchTiming);
  
  public abstract FetchStyle getFetchStyle();
  
  public abstract void setFetchStyle(FetchStyle paramFetchStyle);
  
  @Deprecated
  public abstract FetchMode getFetchMode();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.binding.AssociationAttributeBinding
 * JD-Core Version:    0.7.0.1
 */