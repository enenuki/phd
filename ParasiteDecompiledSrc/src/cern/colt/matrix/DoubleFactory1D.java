package cern.colt.matrix;

import cern.colt.PersistentObject;
import cern.colt.list.AbstractDoubleList;
import cern.colt.list.DoubleArrayList;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import cern.colt.matrix.impl.SparseDoubleMatrix1D;
import cern.jet.math.Functions;
import cern.jet.random.engine.MersenneTwister;
import cern.jet.random.sampling.RandomSamplingAssistant;

public class DoubleFactory1D
  extends PersistentObject
{
  public static final DoubleFactory1D dense = new DoubleFactory1D();
  public static final DoubleFactory1D sparse = new DoubleFactory1D();
  
  public DoubleMatrix1D append(DoubleMatrix1D paramDoubleMatrix1D1, DoubleMatrix1D paramDoubleMatrix1D2)
  {
    DoubleMatrix1D localDoubleMatrix1D = make(paramDoubleMatrix1D1.size() + paramDoubleMatrix1D2.size());
    localDoubleMatrix1D.viewPart(0, paramDoubleMatrix1D1.size()).assign(paramDoubleMatrix1D1);
    localDoubleMatrix1D.viewPart(paramDoubleMatrix1D1.size(), paramDoubleMatrix1D2.size()).assign(paramDoubleMatrix1D2);
    return localDoubleMatrix1D;
  }
  
  public DoubleMatrix1D ascending(int paramInt)
  {
    Functions localFunctions = Functions.functions;
    return descending(paramInt).assign(Functions.chain(Functions.neg, Functions.minus(paramInt)));
  }
  
  public DoubleMatrix1D descending(int paramInt)
  {
    DoubleMatrix1D localDoubleMatrix1D = make(paramInt);
    int i = 0;
    int j = paramInt;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      localDoubleMatrix1D.setQuick(j, i++);
    }
    return localDoubleMatrix1D;
  }
  
  public DoubleMatrix1D make(double[] paramArrayOfDouble)
  {
    if (this == sparse) {
      return new SparseDoubleMatrix1D(paramArrayOfDouble);
    }
    return new DenseDoubleMatrix1D(paramArrayOfDouble);
  }
  
  public DoubleMatrix1D make(DoubleMatrix1D[] paramArrayOfDoubleMatrix1D)
  {
    if (paramArrayOfDoubleMatrix1D.length == 0) {
      return make(0);
    }
    int i = 0;
    for (int j = 0; j < paramArrayOfDoubleMatrix1D.length; j++) {
      i += paramArrayOfDoubleMatrix1D[j].size();
    }
    DoubleMatrix1D localDoubleMatrix1D = make(i);
    i = 0;
    for (int k = 0; k < paramArrayOfDoubleMatrix1D.length; k++)
    {
      localDoubleMatrix1D.viewPart(i, paramArrayOfDoubleMatrix1D[k].size()).assign(paramArrayOfDoubleMatrix1D[k]);
      i += paramArrayOfDoubleMatrix1D[k].size();
    }
    return localDoubleMatrix1D;
  }
  
  public DoubleMatrix1D make(int paramInt)
  {
    if (this == sparse) {
      return new SparseDoubleMatrix1D(paramInt);
    }
    return new DenseDoubleMatrix1D(paramInt);
  }
  
  public DoubleMatrix1D make(int paramInt, double paramDouble)
  {
    return make(paramInt).assign(paramDouble);
  }
  
  public DoubleMatrix1D make(AbstractDoubleList paramAbstractDoubleList)
  {
    int i = paramAbstractDoubleList.size();
    DoubleMatrix1D localDoubleMatrix1D = make(i);
    int j = i;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      localDoubleMatrix1D.set(j, paramAbstractDoubleList.get(j));
    }
    return localDoubleMatrix1D;
  }
  
  public DoubleMatrix1D random(int paramInt)
  {
    return make(paramInt).assign(Functions.random());
  }
  
  public DoubleMatrix1D repeat(DoubleMatrix1D paramDoubleMatrix1D, int paramInt)
  {
    int i = paramDoubleMatrix1D.size();
    DoubleMatrix1D localDoubleMatrix1D = make(paramInt * i);
    int j = paramInt;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      localDoubleMatrix1D.viewPart(i * j, i).assign(paramDoubleMatrix1D);
    }
    return localDoubleMatrix1D;
  }
  
  public DoubleMatrix1D sample(int paramInt, double paramDouble1, double paramDouble2)
  {
    double d = 1.E-009D;
    if ((paramDouble2 < 0.0D - d) || (paramDouble2 > 1.0D + d)) {
      throw new IllegalArgumentException();
    }
    if (paramDouble2 < 0.0D) {
      paramDouble2 = 0.0D;
    }
    if (paramDouble2 > 1.0D) {
      paramDouble2 = 1.0D;
    }
    DoubleMatrix1D localDoubleMatrix1D = make(paramInt);
    int i = (int)Math.round(paramInt * paramDouble2);
    if (i == 0) {
      return localDoubleMatrix1D;
    }
    RandomSamplingAssistant localRandomSamplingAssistant = new RandomSamplingAssistant(i, paramInt, new MersenneTwister());
    int j = paramInt;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      if (localRandomSamplingAssistant.sampleNextElement()) {
        localDoubleMatrix1D.set(j, paramDouble1);
      }
    }
    return localDoubleMatrix1D;
  }
  
  public DoubleArrayList toList(DoubleMatrix1D paramDoubleMatrix1D)
  {
    int i = paramDoubleMatrix1D.size();
    DoubleArrayList localDoubleArrayList = new DoubleArrayList(i);
    localDoubleArrayList.setSize(i);
    int j = i;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      localDoubleArrayList.set(j, paramDoubleMatrix1D.get(j));
    }
    return localDoubleArrayList;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.DoubleFactory1D
 * JD-Core Version:    0.7.0.1
 */