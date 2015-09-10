package org.apache.commons.collections;

import java.util.Collection;
import java.util.Map;

public abstract interface MultiMap
  extends Map
{
  public abstract Object remove(Object paramObject1, Object paramObject2);
  
  public abstract int size();
  
  public abstract Object get(Object paramObject);
  
  public abstract boolean containsValue(Object paramObject);
  
  public abstract Object put(Object paramObject1, Object paramObject2);
  
  public abstract Object remove(Object paramObject);
  
  public abstract Collection values();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.MultiMap
 * JD-Core Version:    0.7.0.1
 */