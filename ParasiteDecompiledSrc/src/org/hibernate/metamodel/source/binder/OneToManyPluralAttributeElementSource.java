package org.hibernate.metamodel.source.binder;

public abstract interface OneToManyPluralAttributeElementSource
  extends PluralAttributeElementSource
{
  public abstract String getReferencedEntityName();
  
  public abstract boolean isNotFoundAnException();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.binder.OneToManyPluralAttributeElementSource
 * JD-Core Version:    0.7.0.1
 */