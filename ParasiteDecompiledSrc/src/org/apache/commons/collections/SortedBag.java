package org.apache.commons.collections;

import java.util.Comparator;

public abstract interface SortedBag
  extends Bag
{
  public abstract Comparator comparator();
  
  public abstract Object first();
  
  public abstract Object last();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.SortedBag
 * JD-Core Version:    0.7.0.1
 */