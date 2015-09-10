package org.apache.commons.collections;

public abstract interface OrderedBidiMap
  extends BidiMap, OrderedMap
{
  public abstract BidiMap inverseBidiMap();
  
  public abstract OrderedBidiMap inverseOrderedBidiMap();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.OrderedBidiMap
 * JD-Core Version:    0.7.0.1
 */