package org.apache.commons.collections.functors;

import org.apache.commons.collections.Predicate;

public abstract interface PredicateDecorator
  extends Predicate
{
  public abstract Predicate[] getPredicates();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.functors.PredicateDecorator
 * JD-Core Version:    0.7.0.1
 */