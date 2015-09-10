package cern.jet.stat.quantile;

import cern.colt.PersistentObject;

abstract class Buffer
  extends PersistentObject
{
  protected int weight;
  protected int level;
  protected int k;
  protected boolean isAllocated;
  
  public Buffer(int paramInt)
  {
    this.k = paramInt;
    this.weight = 1;
    this.level = 0;
    this.isAllocated = false;
  }
  
  public abstract void clear();
  
  public boolean isAllocated()
  {
    return this.isAllocated;
  }
  
  public abstract boolean isEmpty();
  
  public abstract boolean isFull();
  
  public boolean isPartial()
  {
    return (!isEmpty()) && (!isFull());
  }
  
  public int level()
  {
    return this.level;
  }
  
  public void level(int paramInt)
  {
    this.level = paramInt;
  }
  
  public abstract int size();
  
  public abstract void sort();
  
  public int weight()
  {
    return this.weight;
  }
  
  public void weight(int paramInt)
  {
    this.weight = paramInt;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.jet.stat.quantile.Buffer
 * JD-Core Version:    0.7.0.1
 */