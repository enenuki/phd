package org.hibernate.metamodel.relational.state;

public abstract interface ManyToOneRelationalState
  extends ValueRelationalState
{
  public abstract boolean isLogicalOneToOne();
  
  public abstract String getForeignKeyName();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.relational.state.ManyToOneRelationalState
 * JD-Core Version:    0.7.0.1
 */