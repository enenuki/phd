package org.hibernate.metamodel.relational;

public abstract interface Constraint
  extends Exportable
{
  public abstract TableSpecification getTable();
  
  public abstract String getName();
  
  public abstract Iterable<Column> getColumns();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.relational.Constraint
 * JD-Core Version:    0.7.0.1
 */