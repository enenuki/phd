package org.apache.commons.collections;

import java.util.Iterator;

public abstract interface MapIterator
  extends Iterator
{
  public abstract boolean hasNext();
  
  public abstract Object next();
  
  public abstract Object getKey();
  
  public abstract Object getValue();
  
  public abstract void remove();
  
  public abstract Object setValue(Object paramObject);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.MapIterator
 * JD-Core Version:    0.7.0.1
 */