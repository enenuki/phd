package org.hibernate.metamodel.relational;

public abstract interface ValueContainer
{
  public abstract Iterable<SimpleValue> values();
  
  public abstract String getLoggableValueQualifier();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.relational.ValueContainer
 * JD-Core Version:    0.7.0.1
 */