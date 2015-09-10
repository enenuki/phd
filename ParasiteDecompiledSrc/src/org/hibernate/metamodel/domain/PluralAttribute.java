package org.hibernate.metamodel.domain;

public abstract interface PluralAttribute
  extends Attribute
{
  public abstract String getRole();
  
  public abstract PluralAttributeNature getNature();
  
  public abstract Type getElementType();
  
  public abstract void setElementType(Type paramType);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.domain.PluralAttribute
 * JD-Core Version:    0.7.0.1
 */