package cern.jet.stat.quantile;

import cern.colt.list.DoubleArrayList;
import cern.colt.list.ObjectArrayList;
import cern.jet.random.engine.RandomEngine;
import cern.jet.random.sampling.WeightedRandomSampler;
import java.util.Comparator;

class UnknownDoubleQuantileEstimator
  extends DoubleQuantileEstimator
{
  protected int currentTreeHeight;
  protected final int treeHeightStartingSampling;
  protected WeightedRandomSampler sampler;
  protected double precomputeEpsilon;
  
  public UnknownDoubleQuantileEstimator(int paramInt1, int paramInt2, int paramInt3, double paramDouble, RandomEngine paramRandomEngine)
  {
    this.sampler = new WeightedRandomSampler(1, paramRandomEngine);
    setUp(paramInt1, paramInt2);
    this.treeHeightStartingSampling = paramInt3;
    this.precomputeEpsilon = paramDouble;
    clear();
  }
  
  protected DoubleBuffer[] buffersToCollapse()
  {
    DoubleBuffer[] arrayOfDoubleBuffer = this.bufferSet._getFullOrPartialBuffers();
    sortAscendingByLevel(arrayOfDoubleBuffer);
    int i = arrayOfDoubleBuffer[1].level();
    if (arrayOfDoubleBuffer[0].level() < i) {
      arrayOfDoubleBuffer[0].level(i);
    }
    return this.bufferSet._getFullOrPartialBuffersWithLevel(i);
  }
  
  public synchronized void clear()
  {
    super.clear();
    this.currentTreeHeight = 1;
    this.sampler.setWeight(1);
  }
  
  public Object clone()
  {
    UnknownDoubleQuantileEstimator localUnknownDoubleQuantileEstimator = (UnknownDoubleQuantileEstimator)super.clone();
    if (this.sampler != null) {
      localUnknownDoubleQuantileEstimator.sampler = ((WeightedRandomSampler)localUnknownDoubleQuantileEstimator.sampler.clone());
    }
    return localUnknownDoubleQuantileEstimator;
  }
  
  protected void newBuffer()
  {
    this.currentBufferToFill = this.bufferSet._getFirstEmptyBuffer();
    if (this.currentBufferToFill == null) {
      throw new RuntimeException("Oops, no empty buffer.");
    }
    this.currentBufferToFill.level(this.currentTreeHeight - 1);
    this.currentBufferToFill.weight(this.sampler.getWeight());
  }
  
  protected void postCollapse(DoubleBuffer[] paramArrayOfDoubleBuffer)
  {
    if (paramArrayOfDoubleBuffer.length == this.bufferSet.b())
    {
      this.currentTreeHeight += 1;
      if (this.currentTreeHeight >= this.treeHeightStartingSampling) {
        this.sampler.setWeight(this.sampler.getWeight() * 2);
      }
    }
  }
  
  public DoubleArrayList quantileElements(DoubleArrayList paramDoubleArrayList)
  {
    if (this.precomputeEpsilon <= 0.0D) {
      return super.quantileElements(paramDoubleArrayList);
    }
    int i = (int)Utils.epsilonCeiling(1.0D / this.precomputeEpsilon);
    paramDoubleArrayList = paramDoubleArrayList.copy();
    double d1 = this.precomputeEpsilon;
    int j = paramDoubleArrayList.size();
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      double d2 = paramDoubleArrayList.get(j);
      int k = (int)Math.round((2.0D * d2 / d1 - 1.0D) / 2.0D);
      k = Math.min(i - 1, Math.max(0, k));
      double d3 = d1 / 2.0D * (1 + 2 * k);
      paramDoubleArrayList.set(j, d3);
    }
    return super.quantileElements(paramDoubleArrayList);
  }
  
  protected boolean sampleNextElement()
  {
    return this.sampler.sampleNextElement();
  }
  
  protected static void sortAscendingByLevel(DoubleBuffer[] paramArrayOfDoubleBuffer)
  {
    new ObjectArrayList(paramArrayOfDoubleBuffer).quickSortFromTo(0, paramArrayOfDoubleBuffer.length - 1, new Comparator()
    {
      public int compare(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        int i = ((DoubleBuffer)paramAnonymousObject1).level();
        int j = ((DoubleBuffer)paramAnonymousObject2).level();
        return i == j ? 0 : i < j ? -1 : 1;
      }
    });
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer(super.toString());
    localStringBuffer.setLength(localStringBuffer.length() - 1);
    return localStringBuffer + ", h=" + this.currentTreeHeight + ", hStartSampling=" + this.treeHeightStartingSampling + ", precomputeEpsilon=" + this.precomputeEpsilon + ")";
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.jet.stat.quantile.UnknownDoubleQuantileEstimator
 * JD-Core Version:    0.7.0.1
 */