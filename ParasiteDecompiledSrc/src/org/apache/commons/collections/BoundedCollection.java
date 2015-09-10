package org.apache.commons.collections;

import java.util.Collection;

public abstract interface BoundedCollection
  extends Collection
{
  public abstract boolean isFull();
  
  public abstract int maxSize();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.BoundedCollection
 * JD-Core Version:    0.7.0.1
 */