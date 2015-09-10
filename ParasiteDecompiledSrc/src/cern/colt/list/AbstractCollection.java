package cern.colt.list;

import cern.colt.PersistentObject;
import java.util.ArrayList;

public abstract class AbstractCollection
  extends PersistentObject
{
  public abstract void clear();
  
  public boolean isEmpty()
  {
    return size() == 0;
  }
  
  public abstract int size();
  
  public abstract ArrayList toList();
  
  public String toString()
  {
    return toList().toString();
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.list.AbstractCollection
 * JD-Core Version:    0.7.0.1
 */