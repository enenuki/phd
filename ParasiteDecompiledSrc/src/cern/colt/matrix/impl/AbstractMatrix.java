package cern.colt.matrix.impl;

import cern.colt.PersistentObject;

public abstract class AbstractMatrix
  extends PersistentObject
{
  protected boolean isNoView = true;
  
  public void ensureCapacity(int paramInt) {}
  
  protected boolean isView()
  {
    return !this.isNoView;
  }
  
  public abstract int size();
  
  public void trimToSize() {}
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.impl.AbstractMatrix
 * JD-Core Version:    0.7.0.1
 */