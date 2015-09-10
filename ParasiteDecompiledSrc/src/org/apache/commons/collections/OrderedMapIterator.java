package org.apache.commons.collections;

public abstract interface OrderedMapIterator
  extends MapIterator, OrderedIterator
{
  public abstract boolean hasPrevious();
  
  public abstract Object previous();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.OrderedMapIterator
 * JD-Core Version:    0.7.0.1
 */