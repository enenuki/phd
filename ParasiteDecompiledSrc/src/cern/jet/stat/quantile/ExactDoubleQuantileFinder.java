package cern.jet.stat.quantile;

import cern.colt.PersistentObject;
import cern.colt.function.DoubleProcedure;
import cern.colt.list.DoubleArrayList;
import cern.jet.stat.Descriptive;

class ExactDoubleQuantileFinder
  extends PersistentObject
  implements DoubleQuantileFinder
{
  protected DoubleArrayList buffer = new DoubleArrayList(0);
  protected boolean isSorted;
  
  public ExactDoubleQuantileFinder()
  {
    clear();
  }
  
  public void add(double paramDouble)
  {
    this.buffer.add(paramDouble);
    this.isSorted = false;
  }
  
  public void addAllOf(DoubleArrayList paramDoubleArrayList)
  {
    addAllOfFromTo(paramDoubleArrayList, 0, paramDoubleArrayList.size() - 1);
  }
  
  public void addAllOfFromTo(DoubleArrayList paramDoubleArrayList, int paramInt1, int paramInt2)
  {
    this.buffer.addAllOfFromTo(paramDoubleArrayList, paramInt1, paramInt2);
    this.isSorted = false;
  }
  
  public void clear()
  {
    this.buffer.clear();
    this.buffer.trimToSize();
    this.isSorted = false;
  }
  
  public Object clone()
  {
    ExactDoubleQuantileFinder localExactDoubleQuantileFinder = (ExactDoubleQuantileFinder)super.clone();
    if (this.buffer != null) {
      localExactDoubleQuantileFinder.buffer = localExactDoubleQuantileFinder.buffer.copy();
    }
    return localExactDoubleQuantileFinder;
  }
  
  public boolean contains(double paramDouble)
  {
    sort();
    return this.buffer.binarySearch(paramDouble) >= 0;
  }
  
  public boolean forEach(DoubleProcedure paramDoubleProcedure)
  {
    double[] arrayOfDouble = this.buffer.elements();
    int i = (int)size();
    int j = 0;
    while (j < i) {
      if (!paramDoubleProcedure.apply(arrayOfDouble[(j++)])) {
        return false;
      }
    }
    return true;
  }
  
  public long memory()
  {
    return this.buffer.elements().length;
  }
  
  public double phi(double paramDouble)
  {
    sort();
    return Descriptive.rankInterpolated(this.buffer, paramDouble) / size();
  }
  
  public DoubleArrayList quantileElements(DoubleArrayList paramDoubleArrayList)
  {
    sort();
    return Descriptive.quantiles(this.buffer, paramDoubleArrayList);
  }
  
  public long size()
  {
    return this.buffer.size();
  }
  
  protected void sort()
  {
    if (!this.isSorted)
    {
      this.buffer.sort();
      this.isSorted = true;
    }
  }
  
  public String toString()
  {
    String str = getClass().getName();
    str = str.substring(str.lastIndexOf('.') + 1);
    return str + "(mem=" + memory() + ", size=" + size() + ")";
  }
  
  public long totalMemory()
  {
    return memory();
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.jet.stat.quantile.ExactDoubleQuantileFinder
 * JD-Core Version:    0.7.0.1
 */