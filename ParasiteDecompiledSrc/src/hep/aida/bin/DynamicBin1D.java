package hep.aida.bin;

import cern.colt.buffer.DoubleBuffer;
import cern.colt.function.DoubleDoubleFunction;
import cern.colt.function.DoubleFunction;
import cern.colt.list.DoubleArrayList;
import cern.colt.list.IntArrayList;
import cern.colt.map.AbstractDoubleIntMap;
import cern.colt.map.OpenDoubleIntHashMap;
import cern.jet.random.Uniform;
import cern.jet.random.engine.RandomEngine;
import cern.jet.random.sampling.RandomSamplingAssistant;
import cern.jet.stat.Descriptive;

public class DynamicBin1D
  extends QuantileBin1D
{
  protected DoubleArrayList elements = null;
  protected DoubleArrayList sortedElements = null;
  protected boolean fixedOrder = false;
  protected boolean isSorted = true;
  protected boolean isIncrementalStatValid = true;
  protected boolean isSumOfInversionsValid = true;
  protected boolean isSumOfLogarithmsValid = true;
  
  public DynamicBin1D()
  {
    clear();
    this.elements = new DoubleArrayList();
    this.sortedElements = new DoubleArrayList(0);
    this.fixedOrder = false;
    this.hasSumOfLogarithms = true;
    this.hasSumOfInversions = true;
  }
  
  public synchronized void add(double paramDouble)
  {
    this.elements.add(paramDouble);
    invalidateAll();
  }
  
  public synchronized void addAllOfFromTo(DoubleArrayList paramDoubleArrayList, int paramInt1, int paramInt2)
  {
    this.elements.addAllOfFromTo(paramDoubleArrayList, paramInt1, paramInt2);
    invalidateAll();
  }
  
  public synchronized double aggregate(DoubleDoubleFunction paramDoubleDoubleFunction, DoubleFunction paramDoubleFunction)
  {
    int i = size();
    if (i == 0) {
      return (0.0D / 0.0D);
    }
    double d = paramDoubleFunction.apply(this.elements.getQuick(i - 1));
    int j = i - 1;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      d = paramDoubleDoubleFunction.apply(d, paramDoubleFunction.apply(this.elements.getQuick(j)));
    }
    return d;
  }
  
  public synchronized void clear()
  {
    super.clear();
    if (this.elements != null) {
      this.elements.clear();
    }
    if (this.sortedElements != null) {
      this.sortedElements.clear();
    }
    validateAll();
  }
  
  protected void clearAllMeasures()
  {
    super.clearAllMeasures();
  }
  
  public synchronized Object clone()
  {
    DynamicBin1D localDynamicBin1D = (DynamicBin1D)super.clone();
    if (this.elements != null) {
      localDynamicBin1D.elements = localDynamicBin1D.elements.copy();
    }
    if (this.sortedElements != null) {
      localDynamicBin1D.sortedElements = localDynamicBin1D.sortedElements.copy();
    }
    return localDynamicBin1D;
  }
  
  public synchronized double correlation(DynamicBin1D paramDynamicBin1D)
  {
    synchronized (paramDynamicBin1D)
    {
      return covariance(paramDynamicBin1D) / (standardDeviation() * paramDynamicBin1D.standardDeviation());
    }
  }
  
  public synchronized double covariance(DynamicBin1D paramDynamicBin1D)
  {
    synchronized (paramDynamicBin1D)
    {
      if (size() != paramDynamicBin1D.size()) {
        throw new IllegalArgumentException("both bins must have same size");
      }
      double d1 = 0.0D;
      int i = size();
      for (;;)
      {
        i--;
        if (i < 0) {
          break;
        }
        d1 += this.elements.getQuick(i) * paramDynamicBin1D.elements.getQuick(i);
      }
      double d2 = (d1 - sum() * paramDynamicBin1D.sum() / size()) / size();
      return d2;
    }
  }
  
  public synchronized DoubleArrayList elements()
  {
    return elements_unsafe().copy();
  }
  
  protected synchronized DoubleArrayList elements_unsafe()
  {
    return this.elements;
  }
  
  public synchronized boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof DynamicBin1D)) {
      return false;
    }
    if (!super.equals(paramObject)) {
      return false;
    }
    DynamicBin1D localDynamicBin1D = (DynamicBin1D)paramObject;
    double[] arrayOfDouble1 = sortedElements_unsafe().elements();
    synchronized (localDynamicBin1D)
    {
      double[] arrayOfDouble2 = localDynamicBin1D.sortedElements_unsafe().elements();
      int i = size();
      return (includes(arrayOfDouble1, arrayOfDouble2, 0, i, 0, i)) && (includes(arrayOfDouble2, arrayOfDouble1, 0, i, 0, i));
    }
  }
  
  private static boolean includes(double[] paramArrayOfDouble1, double[] paramArrayOfDouble2, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    while ((paramInt1 < paramInt2) && (paramInt3 < paramInt4))
    {
      if (paramArrayOfDouble2[paramInt3] < paramArrayOfDouble1[paramInt1]) {
        return false;
      }
      if (paramArrayOfDouble1[paramInt1] < paramArrayOfDouble2[paramInt3])
      {
        paramInt1++;
      }
      else
      {
        paramInt1++;
        paramInt3++;
      }
    }
    return paramInt3 == paramInt4;
  }
  
  public synchronized void frequencies(DoubleArrayList paramDoubleArrayList, IntArrayList paramIntArrayList)
  {
    Descriptive.frequencies(sortedElements_unsafe(), paramDoubleArrayList, paramIntArrayList);
  }
  
  private synchronized AbstractDoubleIntMap frequencyMap()
  {
    OpenDoubleIntHashMap localOpenDoubleIntHashMap = new OpenDoubleIntHashMap();
    int i = size();
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      double d = this.elements.getQuick(i);
      localOpenDoubleIntHashMap.put(d, 1 + localOpenDoubleIntHashMap.get(d));
    }
    return localOpenDoubleIntHashMap;
  }
  
  public int getMaxOrderForSumOfPowers()
  {
    return 2147483647;
  }
  
  public int getMinOrderForSumOfPowers()
  {
    return -2147483648;
  }
  
  protected void invalidateAll()
  {
    this.isSorted = false;
    this.isIncrementalStatValid = false;
    this.isSumOfInversionsValid = false;
    this.isSumOfLogarithmsValid = false;
  }
  
  public synchronized boolean isRebinnable()
  {
    return true;
  }
  
  public synchronized double max()
  {
    if (!this.isIncrementalStatValid) {
      updateIncrementalStats();
    }
    return this.max;
  }
  
  public synchronized double min()
  {
    if (!this.isIncrementalStatValid) {
      updateIncrementalStats();
    }
    return this.min;
  }
  
  public synchronized double moment(int paramInt, double paramDouble)
  {
    return Descriptive.moment(this.elements, paramInt, paramDouble);
  }
  
  public synchronized double quantile(double paramDouble)
  {
    return Descriptive.quantile(sortedElements_unsafe(), paramDouble);
  }
  
  public synchronized double quantileInverse(double paramDouble)
  {
    return Descriptive.quantileInverse(sortedElements_unsafe(), paramDouble);
  }
  
  public DoubleArrayList quantiles(DoubleArrayList paramDoubleArrayList)
  {
    return Descriptive.quantiles(sortedElements_unsafe(), paramDoubleArrayList);
  }
  
  public synchronized boolean removeAllOf(DoubleArrayList paramDoubleArrayList)
  {
    boolean bool = this.elements.removeAll(paramDoubleArrayList);
    if (bool)
    {
      clearAllMeasures();
      invalidateAll();
      this.size = 0;
      if (this.fixedOrder)
      {
        this.sortedElements.removeAll(paramDoubleArrayList);
        this.isSorted = true;
      }
    }
    return bool;
  }
  
  public synchronized void sample(int paramInt, boolean paramBoolean, RandomEngine paramRandomEngine, DoubleBuffer paramDoubleBuffer)
  {
    if (paramRandomEngine == null) {
      paramRandomEngine = Uniform.makeDefaultGenerator();
    }
    paramDoubleBuffer.clear();
    if (!paramBoolean)
    {
      if (paramInt > size()) {
        throw new IllegalArgumentException("n must be less than or equal to size()");
      }
      localObject = new RandomSamplingAssistant(paramInt, size(), paramRandomEngine);
      i = paramInt;
      for (;;)
      {
        i--;
        if (i < 0) {
          break;
        }
        if (((RandomSamplingAssistant)localObject).sampleNextElement()) {
          paramDoubleBuffer.add(this.elements.getQuick(i));
        }
      }
    }
    Object localObject = new Uniform(paramRandomEngine);
    int i = size();
    int j = paramInt;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      paramDoubleBuffer.add(this.elements.getQuick(((Uniform)localObject).nextIntFromTo(0, i - 1)));
    }
    paramDoubleBuffer.flush();
  }
  
  public synchronized DynamicBin1D sampleBootstrap(DynamicBin1D paramDynamicBin1D, int paramInt, RandomEngine paramRandomEngine, BinBinFunction1D paramBinBinFunction1D)
  {
    if (paramRandomEngine == null) {
      paramRandomEngine = Uniform.makeDefaultGenerator();
    }
    int i = 1000;
    int j = size();
    int k = paramDynamicBin1D.size();
    DynamicBin1D localDynamicBin1D1 = new DynamicBin1D();
    DoubleBuffer localDoubleBuffer1 = localDynamicBin1D1.buffered(Math.min(i, j));
    DynamicBin1D localDynamicBin1D2 = new DynamicBin1D();
    DoubleBuffer localDoubleBuffer2 = localDynamicBin1D2.buffered(Math.min(i, k));
    DynamicBin1D localDynamicBin1D3 = new DynamicBin1D();
    DoubleBuffer localDoubleBuffer3 = localDynamicBin1D3.buffered(Math.min(i, paramInt));
    int m = paramInt;
    for (;;)
    {
      m--;
      if (m < 0) {
        break;
      }
      localDynamicBin1D1.clear();
      localDynamicBin1D2.clear();
      sample(j, true, paramRandomEngine, localDoubleBuffer1);
      paramDynamicBin1D.sample(k, true, paramRandomEngine, localDoubleBuffer2);
      localDoubleBuffer3.add(paramBinBinFunction1D.apply(localDynamicBin1D1, localDynamicBin1D2));
    }
    localDoubleBuffer3.flush();
    return localDynamicBin1D3;
  }
  
  public void setFixedOrder(boolean paramBoolean)
  {
    this.fixedOrder = paramBoolean;
  }
  
  public synchronized int size()
  {
    return this.elements.size();
  }
  
  protected void sort()
  {
    if (!this.isSorted)
    {
      if (this.fixedOrder)
      {
        this.sortedElements.clear();
        this.sortedElements.addAllOfFromTo(this.elements, 0, this.elements.size() - 1);
        this.sortedElements.sort();
      }
      else
      {
        updateIncrementalStats();
        invalidateAll();
        this.elements.sort();
        this.isIncrementalStatValid = true;
      }
      this.isSorted = true;
    }
  }
  
  public synchronized DoubleArrayList sortedElements()
  {
    return sortedElements_unsafe().copy();
  }
  
  protected synchronized DoubleArrayList sortedElements_unsafe()
  {
    sort();
    if (this.fixedOrder) {
      return this.sortedElements;
    }
    return this.elements;
  }
  
  public synchronized void standardize(double paramDouble1, double paramDouble2)
  {
    Descriptive.standardize(this.elements, paramDouble1, paramDouble2);
    clearAllMeasures();
    invalidateAll();
    this.size = 0;
  }
  
  public synchronized double sum()
  {
    if (!this.isIncrementalStatValid) {
      updateIncrementalStats();
    }
    return this.sum;
  }
  
  public synchronized double sumOfInversions()
  {
    if (!this.isSumOfInversionsValid) {
      updateSumOfInversions();
    }
    return this.sumOfInversions;
  }
  
  public synchronized double sumOfLogarithms()
  {
    if (!this.isSumOfLogarithmsValid) {
      updateSumOfLogarithms();
    }
    return this.sumOfLogarithms;
  }
  
  public synchronized double sumOfPowers(int paramInt)
  {
    if ((paramInt >= -1) && (paramInt <= 2)) {
      return super.sumOfPowers(paramInt);
    }
    return Descriptive.sumOfPowers(this.elements, paramInt);
  }
  
  public synchronized double sumOfSquares()
  {
    if (!this.isIncrementalStatValid) {
      updateIncrementalStats();
    }
    return this.sum_xx;
  }
  
  public synchronized String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer(super.toString());
    DoubleArrayList localDoubleArrayList = new DoubleArrayList();
    IntArrayList localIntArrayList = new IntArrayList();
    frequencies(localDoubleArrayList, localIntArrayList);
    if (localDoubleArrayList.size() < 100)
    {
      localStringBuffer.append("Distinct elements: " + localDoubleArrayList + "\n");
      localStringBuffer.append("Frequencies: " + localIntArrayList + "\n");
    }
    else
    {
      localStringBuffer.append("Distinct elements & frequencies not printed (too many).");
    }
    return localStringBuffer.toString();
  }
  
  public synchronized void trim(int paramInt1, int paramInt2)
  {
    DoubleArrayList localDoubleArrayList = sortedElements();
    clear();
    addAllOfFromTo(localDoubleArrayList, paramInt1, localDoubleArrayList.size() - 1 - paramInt2);
  }
  
  public synchronized double trimmedMean(int paramInt1, int paramInt2)
  {
    return Descriptive.trimmedMean(sortedElements_unsafe(), mean(), paramInt1, paramInt2);
  }
  
  public synchronized void trimToSize()
  {
    this.elements.trimToSize();
    this.sortedElements.clear();
    this.sortedElements.trimToSize();
    if (this.fixedOrder) {
      this.isSorted = false;
    }
  }
  
  protected void updateIncrementalStats()
  {
    double[] arrayOfDouble = new double[4];
    arrayOfDouble[0] = this.min;
    arrayOfDouble[1] = this.max;
    arrayOfDouble[2] = this.sum;
    arrayOfDouble[3] = this.sum_xx;
    Descriptive.incrementalUpdate(this.elements, this.size, this.elements.size() - 1, arrayOfDouble);
    this.min = arrayOfDouble[0];
    this.max = arrayOfDouble[1];
    this.sum = arrayOfDouble[2];
    this.sum_xx = arrayOfDouble[3];
    this.isIncrementalStatValid = true;
    this.size = this.elements.size();
  }
  
  protected void updateSumOfInversions()
  {
    this.sumOfInversions = Descriptive.sumOfInversions(this.elements, 0, size() - 1);
    this.isSumOfInversionsValid = true;
  }
  
  protected void updateSumOfLogarithms()
  {
    this.sumOfLogarithms = Descriptive.sumOfLogarithms(this.elements, 0, size() - 1);
    this.isSumOfLogarithmsValid = true;
  }
  
  protected void validateAll()
  {
    this.isSorted = true;
    this.isIncrementalStatValid = true;
    this.isSumOfInversionsValid = true;
    this.isSumOfLogarithmsValid = true;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hep.aida.bin.DynamicBin1D
 * JD-Core Version:    0.7.0.1
 */