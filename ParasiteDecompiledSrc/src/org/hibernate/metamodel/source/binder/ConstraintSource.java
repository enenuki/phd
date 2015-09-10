package org.hibernate.metamodel.source.binder;

public abstract interface ConstraintSource
{
  public abstract String name();
  
  public abstract String getTableName();
  
  public abstract Iterable<String> columnNames();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.source.binder.ConstraintSource
 * JD-Core Version:    0.7.0.1
 */