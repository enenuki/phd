package org.hibernate.metamodel.domain;

public abstract interface SingularAttribute
  extends Attribute
{
  public abstract Type getSingularAttributeType();
  
  public abstract boolean isTypeResolved();
  
  public abstract void resolveType(Type paramType);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.domain.SingularAttribute
 * JD-Core Version:    0.7.0.1
 */