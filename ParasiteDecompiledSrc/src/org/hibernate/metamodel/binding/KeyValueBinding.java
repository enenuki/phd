package org.hibernate.metamodel.binding;

public abstract interface KeyValueBinding
  extends AttributeBinding
{
  public abstract boolean isKeyCascadeDeleteEnabled();
  
  public abstract String getUnsavedValue();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.binding.KeyValueBinding
 * JD-Core Version:    0.7.0.1
 */