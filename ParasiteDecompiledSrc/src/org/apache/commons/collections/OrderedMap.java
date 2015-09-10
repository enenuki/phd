package org.apache.commons.collections;

public abstract interface OrderedMap
  extends IterableMap
{
  public abstract OrderedMapIterator orderedMapIterator();
  
  public abstract Object firstKey();
  
  public abstract Object lastKey();
  
  public abstract Object nextKey(Object paramObject);
  
  public abstract Object previousKey(Object paramObject);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.OrderedMap
 * JD-Core Version:    0.7.0.1
 */