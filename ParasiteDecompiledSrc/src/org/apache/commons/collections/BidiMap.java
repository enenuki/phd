package org.apache.commons.collections;

public abstract interface BidiMap
  extends IterableMap
{
  public abstract MapIterator mapIterator();
  
  public abstract Object put(Object paramObject1, Object paramObject2);
  
  public abstract Object getKey(Object paramObject);
  
  public abstract Object removeValue(Object paramObject);
  
  public abstract BidiMap inverseBidiMap();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.BidiMap
 * JD-Core Version:    0.7.0.1
 */