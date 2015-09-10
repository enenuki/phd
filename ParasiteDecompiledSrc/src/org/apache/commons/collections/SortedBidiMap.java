package org.apache.commons.collections;

import java.util.SortedMap;

public abstract interface SortedBidiMap
  extends OrderedBidiMap, SortedMap
{
  public abstract BidiMap inverseBidiMap();
  
  public abstract SortedBidiMap inverseSortedBidiMap();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.commons.collections.SortedBidiMap
 * JD-Core Version:    0.7.0.1
 */