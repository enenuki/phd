package org.hibernate.metamodel.binding;

import org.hibernate.mapping.PropertyGeneration;
import org.hibernate.metamodel.relational.Value;

public abstract interface SingularAttributeBinding
  extends AttributeBinding
{
  public abstract Value getValue();
  
  public abstract int getSimpleValueSpan();
  
  public abstract Iterable<SimpleValueBinding> getSimpleValueBindings();
  
  public abstract void setSimpleValueBindings(Iterable<SimpleValueBinding> paramIterable);
  
  public abstract boolean hasDerivedValue();
  
  public abstract boolean isNullable();
  
  public abstract PropertyGeneration getGeneration();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.binding.SingularAttributeBinding
 * JD-Core Version:    0.7.0.1
 */