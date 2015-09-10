package org.hibernate.metamodel.domain;

public abstract interface IndexedPluralAttribute
  extends PluralAttribute
{
  public abstract Type getIndexType();
  
  public abstract void setIndexType(Type paramType);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.domain.IndexedPluralAttribute
 * JD-Core Version:    0.7.0.1
 */