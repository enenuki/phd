package cern.jet.stat.quantile;

import cern.colt.PersistentObject;
import cern.colt.function.DoubleProcedure;
import cern.colt.list.DoubleArrayList;
import cern.colt.list.ObjectArrayList;

abstract class DoubleQuantileEstimator
  extends PersistentObject
  implements DoubleQuantileFinder
{
  protected DoubleBufferSet bufferSet;
  protected DoubleBuffer currentBufferToFill;
  protected int totalElementsFilled;
  
  public void add(double paramDouble)
  {
    this.totalElementsFilled += 1;
    if (!sampleNextElement()) {
      return;
    }
    if (this.currentBufferToFill == null)
    {
      if (this.bufferSet._getFirstEmptyBuffer() == null) {
        collapse();
      }
      newBuffer();
    }
    this.currentBufferToFill.add(paramDouble);
    if (this.currentBufferToFill.isFull()) {
      this.currentBufferToFill = null;
    }
  }
  
  public void addAllOf(DoubleArrayList paramDoubleArrayList)
  {
    addAllOfFromTo(paramDoubleArrayList, 0, paramDoubleArrayList.size() - 1);
  }
  
  public void addAllOfFromTo(DoubleArrayList paramDoubleArrayList, int paramInt1, int paramInt2)
  {
    double[] arrayOfDouble1 = paramDoubleArrayList.elements();
    int i = this.bufferSet.k();
    int j = i;
    double[] arrayOfDouble2 = null;
    if (this.currentBufferToFill != null)
    {
      arrayOfDouble2 = this.currentBufferToFill.values.elements();
      j = this.currentBufferToFill.size();
    }
    int k = paramInt1 - 1;
    for (;;)
    {
      k++;
      if (k > paramInt2) {
        break;
      }
      if (sampleNextElement())
      {
        if (j == i)
        {
          if (this.bufferSet._getFirstEmptyBuffer() == null) {
            collapse();
          }
          newBuffer();
          if (!this.currentBufferToFill.isAllocated) {
            this.currentBufferToFill.allocate();
          }
          this.currentBufferToFill.isSorted = false;
          arrayOfDouble2 = this.currentBufferToFill.values.elements();
          j = 0;
        }
        arrayOfDouble2[(j++)] = arrayOfDouble1[k];
        if (j == i)
        {
          this.currentBufferToFill.values.setSize(j);
          this.currentBufferToFill = null;
        }
      }
    }
    if (this.currentBufferToFill != null) {
      this.currentBufferToFill.values.setSize(j);
    }
    this.totalElementsFilled += paramInt2 - paramInt1 + 1;
  }
  
  protected DoubleBuffer[] buffersToCollapse()
  {
    int i = this.bufferSet._getMinLevelOfFullOrPartialBuffers();
    return this.bufferSet._getFullOrPartialBuffersWithLevel(i);
  }
  
  public void clear()
  {
    this.totalElementsFilled = 0;
    this.currentBufferToFill = null;
    this.bufferSet.clear();
  }
  
  public Object clone()
  {
    DoubleQuantileEstimator localDoubleQuantileEstimator = (DoubleQuantileEstimator)super.clone();
    if (this.bufferSet != null)
    {
      localDoubleQuantileEstimator.bufferSet = ((DoubleBufferSet)localDoubleQuantileEstimator.bufferSet.clone());
      if (this.currentBufferToFill != null)
      {
        int i = new ObjectArrayList(this.bufferSet.buffers).indexOf(this.currentBufferToFill, true);
        localDoubleQuantileEstimator.currentBufferToFill = localDoubleQuantileEstimator.bufferSet.buffers[i];
      }
    }
    return localDoubleQuantileEstimator;
  }
  
  protected void collapse()
  {
    DoubleBuffer[] arrayOfDoubleBuffer = buffersToCollapse();
    DoubleBuffer localDoubleBuffer = this.bufferSet.collapse(arrayOfDoubleBuffer);
    int i = arrayOfDoubleBuffer[0].level();
    localDoubleBuffer.level(i + 1);
    postCollapse(arrayOfDoubleBuffer);
  }
  
  public boolean contains(double paramDouble)
  {
    return this.bufferSet.contains(paramDouble);
  }
  
  public boolean forEach(DoubleProcedure paramDoubleProcedure)
  {
    return this.bufferSet.forEach(paramDoubleProcedure);
  }
  
  public long memory()
  {
    return this.bufferSet.memory();
  }
  
  protected abstract void newBuffer();
  
  public double phi(double paramDouble)
  {
    return this.bufferSet.phi(paramDouble);
  }
  
  protected abstract void postCollapse(DoubleBuffer[] paramArrayOfDoubleBuffer);
  
  protected DoubleArrayList preProcessPhis(DoubleArrayList paramDoubleArrayList)
  {
    return paramDoubleArrayList;
  }
  
  public DoubleArrayList quantileElements(DoubleArrayList paramDoubleArrayList)
  {
    paramDoubleArrayList = preProcessPhis(paramDoubleArrayList);
    long[] arrayOfLong = new long[paramDoubleArrayList.size()];
    long l = this.bufferSet.totalSize();
    int i = paramDoubleArrayList.size();
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      arrayOfLong[i] = (Utils.epsilonCeiling(paramDoubleArrayList.get(i) * l) - 1L);
    }
    DoubleBuffer[] arrayOfDoubleBuffer = this.bufferSet._getFullOrPartialBuffers();
    double[] arrayOfDouble = new double[paramDoubleArrayList.size()];
    return new DoubleArrayList(this.bufferSet.getValuesAtPositions(arrayOfDoubleBuffer, arrayOfLong));
  }
  
  protected abstract boolean sampleNextElement();
  
  protected void setUp(int paramInt1, int paramInt2)
  {
    if ((paramInt1 < 2) || (paramInt2 < 1)) {
      throw new IllegalArgumentException("Assertion: b>=2 && k>=1");
    }
    this.bufferSet = new DoubleBufferSet(paramInt1, paramInt2);
    clear();
  }
  
  public long size()
  {
    return this.totalElementsFilled;
  }
  
  public String toString()
  {
    String str = getClass().getName();
    str = str.substring(str.lastIndexOf('.') + 1);
    int i = this.bufferSet.b();
    int j = this.bufferSet.k();
    return str + "(mem=" + memory() + ", b=" + i + ", k=" + j + ", size=" + size() + ", totalSize=" + this.bufferSet.totalSize() + ")";
  }
  
  public long totalMemory()
  {
    return this.bufferSet.b() * this.bufferSet.k();
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.jet.stat.quantile.DoubleQuantileEstimator
 * JD-Core Version:    0.7.0.1
 */