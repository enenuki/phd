package org.hibernate.metamodel.domain;

public abstract interface Attribute
{
  public abstract String getName();
  
  public abstract AttributeContainer getAttributeContainer();
  
  public abstract boolean isSingular();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.domain.Attribute
 * JD-Core Version:    0.7.0.1
 */