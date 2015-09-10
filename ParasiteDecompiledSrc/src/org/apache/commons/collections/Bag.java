package org.apache.commons.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public abstract interface Bag
  extends Collection
{
  public abstract int getCount(Object paramObject);
  
  public abstract boolean add(Object paramObject);
  
  public abstract boolean add(Object paramObject, int paramInt);
  
  public abstract boolean remove(Object paramObject);
  
  public abstract boolean remove(Object paramObject, int paramInt);
  
  public abstract Set uniqueSet();
  
  public abstract int size();
  
  public abstract boolean containsAll(Collection paramCollection);
  
  public abstract boolean removeAll(Collection paramCollection);
  
  public abstract boolean retainAll(Collection paramCollection);
  
  public abstract Iterator iterator();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.Bag
 * JD-Core Version:    0.7.0.1
 */