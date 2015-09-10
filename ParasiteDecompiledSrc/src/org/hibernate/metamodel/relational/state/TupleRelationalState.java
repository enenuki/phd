package org.hibernate.metamodel.relational.state;

import java.util.List;

public abstract interface TupleRelationalState
  extends ValueRelationalState
{
  public abstract List<SimpleValueRelationalState> getRelationalStates();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.metamodel.relational.state.TupleRelationalState
 * JD-Core Version:    0.7.0.1
 */