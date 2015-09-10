package cern.jet.stat.quantile;

import cern.colt.list.DoubleArrayList;
import cern.jet.stat.Descriptive;

class DoubleBuffer
  extends Buffer
{
  protected DoubleArrayList values = new DoubleArrayList(0);
  protected boolean isSorted = false;
  
  public DoubleBuffer(int paramInt)
  {
    super(paramInt);
  }
  
  public void add(double paramDouble)
  {
    if (!this.isAllocated) {
      allocate();
    }
    this.values.add(paramDouble);
    this.isSorted = false;
  }
  
  public void addAllOfFromTo(DoubleArrayList paramDoubleArrayList, int paramInt1, int paramInt2)
  {
    if (!this.isAllocated) {
      allocate();
    }
    this.values.addAllOfFromTo(paramDoubleArrayList, paramInt1, paramInt2);
    this.isSorted = false;
  }
  
  protected void allocate()
  {
    this.isAllocated = true;
    this.values.ensureCapacity(this.k);
  }
  
  public void clear()
  {
    this.values.clear();
  }
  
  public Object clone()
  {
    DoubleBuffer localDoubleBuffer = (DoubleBuffer)super.clone();
    if (this.values != null) {
      localDoubleBuffer.values = localDoubleBuffer.values.copy();
    }
    return localDoubleBuffer;
  }
  
  public boolean contains(double paramDouble)
  {
    sort();
    return this.values.contains(paramDouble);
  }
  
  public boolean isEmpty()
  {
    return this.values.size() == 0;
  }
  
  public boolean isFull()
  {
    return this.values.size() == this.k;
  }
  
  public int memory()
  {
    return this.values.elements().length;
  }
  
  public double rank(double paramDouble)
  {
    sort();
    return Descriptive.rankInterpolated(this.values, paramDouble);
  }
  
  public int size()
  {
    return this.values.size();
  }
  
  public void sort()
  {
    if (!this.isSorted)
    {
      this.values.sort();
      this.isSorted = true;
    }
  }
  
  public String toString()
  {
    return "k=" + this.k + ", w=" + Long.toString(weight()) + ", l=" + Integer.toString(level()) + ", size=" + this.values.size();
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.jet.stat.quantile.DoubleBuffer
 * JD-Core Version:    0.7.0.1
 */