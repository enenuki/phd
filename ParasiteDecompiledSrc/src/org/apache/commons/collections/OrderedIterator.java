package org.apache.commons.collections;

import java.util.Iterator;

public abstract interface OrderedIterator
  extends Iterator
{
  public abstract boolean hasPrevious();
  
  public abstract Object previous();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.OrderedIterator
 * JD-Core Version:    0.7.0.1
 */