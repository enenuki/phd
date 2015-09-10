package org.apache.commons.collections;

import java.util.Map;

public abstract interface BoundedMap
  extends Map
{
  public abstract boolean isFull();
  
  public abstract int maxSize();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.BoundedMap
 * JD-Core Version:    0.7.0.1
 */