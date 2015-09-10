package hep.aida.bin;

import cern.colt.list.DoubleArrayList;
import cern.jet.random.engine.DRand;
import cern.jet.random.engine.RandomEngine;
import cern.jet.stat.quantile.DoubleQuantileFinder;
import cern.jet.stat.quantile.QuantileFinderFactory;
import hep.aida.IAxis;
import hep.aida.ref.Converter;
import java.util.Date;

public class QuantileBin1D
  extends MightyStaticBin1D
{
  protected DoubleQuantileFinder finder = null;
  
  protected QuantileBin1D()
  {
    super(false, false, 2);
  }
  
  public QuantileBin1D(double paramDouble)
  {
    this(false, 9223372036854775807L, paramDouble, 0.001D, 10000, new DRand(new Date()));
  }
  
  public QuantileBin1D(boolean paramBoolean, long paramLong, double paramDouble1, double paramDouble2, int paramInt, RandomEngine paramRandomEngine)
  {
    this(paramBoolean, paramLong, paramDouble1, paramDouble2, paramInt, paramRandomEngine, false, false, 2);
  }
  
  public QuantileBin1D(boolean paramBoolean1, long paramLong, double paramDouble1, double paramDouble2, int paramInt1, RandomEngine paramRandomEngine, boolean paramBoolean2, boolean paramBoolean3, int paramInt2)
  {
    super(paramBoolean2, paramBoolean3, paramInt2);
    this.finder = QuantileFinderFactory.newDoubleQuantileFinder(paramBoolean1, paramLong, paramDouble1, paramDouble2, paramInt1, paramRandomEngine);
    clear();
  }
  
  public synchronized void addAllOfFromTo(DoubleArrayList paramDoubleArrayList, int paramInt1, int paramInt2)
  {
    super.addAllOfFromTo(paramDoubleArrayList, paramInt1, paramInt2);
    if (this.finder != null) {
      this.finder.addAllOfFromTo(paramDoubleArrayList, paramInt1, paramInt2);
    }
  }
  
  public synchronized void clear()
  {
    super.clear();
    if (this.finder != null) {
      this.finder.clear();
    }
  }
  
  public synchronized Object clone()
  {
    QuantileBin1D localQuantileBin1D = (QuantileBin1D)super.clone();
    if (this.finder != null) {
      localQuantileBin1D.finder = ((DoubleQuantileFinder)localQuantileBin1D.finder.clone());
    }
    return localQuantileBin1D;
  }
  
  public String compareWith(AbstractBin1D paramAbstractBin1D)
  {
    StringBuffer localStringBuffer = new StringBuffer(super.compareWith(paramAbstractBin1D));
    if ((paramAbstractBin1D instanceof QuantileBin1D))
    {
      QuantileBin1D localQuantileBin1D = (QuantileBin1D)paramAbstractBin1D;
      localStringBuffer.append("25%, 50% and 75% Quantiles: " + relError(quantile(0.25D), localQuantileBin1D.quantile(0.25D)) + ", " + relError(quantile(0.5D), localQuantileBin1D.quantile(0.5D)) + ", " + relError(quantile(0.75D), localQuantileBin1D.quantile(0.75D)));
      localStringBuffer.append("\nquantileInverse(mean): " + relError(quantileInverse(mean()), localQuantileBin1D.quantileInverse(localQuantileBin1D.mean())) + " %");
      localStringBuffer.append("\n");
    }
    return localStringBuffer.toString();
  }
  
  public double median()
  {
    return quantile(0.5D);
  }
  
  public synchronized double quantile(double paramDouble)
  {
    return quantiles(new DoubleArrayList(new double[] { paramDouble })).get(0);
  }
  
  public synchronized double quantileInverse(double paramDouble)
  {
    return this.finder.phi(paramDouble);
  }
  
  public synchronized DoubleArrayList quantiles(DoubleArrayList paramDoubleArrayList)
  {
    return this.finder.quantileElements(paramDoubleArrayList);
  }
  
  public int sizeOfRange(double paramDouble1, double paramDouble2)
  {
    return (int)Math.round(size() * (quantileInverse(paramDouble2) - quantileInverse(paramDouble1)));
  }
  
  public synchronized MightyStaticBin1D[] splitApproximately(DoubleArrayList paramDoubleArrayList, int paramInt)
  {
    int i = paramDoubleArrayList.size();
    if ((paramInt < 1) || (i < 2)) {
      throw new IllegalArgumentException();
    }
    double[] arrayOfDouble1 = paramDoubleArrayList.elements();
    int j = i - 1;
    double[] arrayOfDouble2 = new double[1 + paramInt * (i - 1)];
    arrayOfDouble2[0] = arrayOfDouble1[0];
    int k = 1;
    for (int m = 0; m < j; m++)
    {
      double d1 = (arrayOfDouble1[(m + 1)] - arrayOfDouble1[m]) / paramInt;
      for (i1 = 1; i1 <= paramInt; i1++) {
        arrayOfDouble2[(k++)] = (arrayOfDouble1[m] + i1 * d1);
      }
    }
    double[] arrayOfDouble3 = quantiles(new DoubleArrayList(arrayOfDouble2)).elements();
    MightyStaticBin1D[] arrayOfMightyStaticBin1D = new MightyStaticBin1D[j];
    int n = getMaxOrderForSumOfPowers();
    n = Math.min(10, n);
    int i1 = size();
    k = 0;
    for (int i2 = 0; i2 < j; i2++)
    {
      double d2 = (arrayOfDouble1[(i2 + 1)] - arrayOfDouble1[i2]) / paramInt;
      double d3 = 0.0D;
      double d4 = 0.0D;
      double d5 = 0.0D;
      double d6 = 0.0D;
      double[] arrayOfDouble4 = null;
      if (n > 2) {
        arrayOfDouble4 = new double[n - 2];
      }
      double d7 = arrayOfDouble3[(k++)];
      double d8 = d7;
      double d9 = i1 * d2;
      for (int i3 = 1; i3 <= paramInt; i3++)
      {
        d10 = arrayOfDouble3[(k++)];
        double d11 = (d7 + d10) / 2.0D;
        d3 += d11 * d9;
        d4 += d11 * d11 * d9;
        if (this.hasSumOfLogarithms) {
          d5 += Math.log(d11) * d9;
        }
        if (this.hasSumOfInversions) {
          d6 += 1.0D / d11 * d9;
        }
        if (n >= 3) {
          arrayOfDouble4[0] += d11 * d11 * d11 * d9;
        }
        if (n >= 4) {
          arrayOfDouble4[1] += d11 * d11 * d11 * d11 * d9;
        }
        for (int i4 = 5; i4 <= n; i4++) {
          arrayOfDouble4[(i4 - 3)] += Math.pow(d11, i4) * d9;
        }
        d7 = d10;
      }
      k--;
      i3 = (int)Math.round((arrayOfDouble1[(i2 + 1)] - arrayOfDouble1[i2]) * i1);
      double d10 = d7;
      d7 = d8;
      arrayOfMightyStaticBin1D[i2] = new MightyStaticBin1D(this.hasSumOfLogarithms, this.hasSumOfInversions, n);
      if (i3 > 0)
      {
        arrayOfMightyStaticBin1D[i2].size = i3;
        arrayOfMightyStaticBin1D[i2].min = d7;
        arrayOfMightyStaticBin1D[i2].max = d10;
        arrayOfMightyStaticBin1D[i2].sum = d3;
        arrayOfMightyStaticBin1D[i2].sum_xx = d4;
        arrayOfMightyStaticBin1D[i2].sumOfLogarithms = d5;
        arrayOfMightyStaticBin1D[i2].sumOfInversions = d6;
        arrayOfMightyStaticBin1D[i2].sumOfPowers = arrayOfDouble4;
      }
    }
    return arrayOfMightyStaticBin1D;
  }
  
  public synchronized MightyStaticBin1D[] splitApproximately(IAxis paramIAxis, int paramInt)
  {
    DoubleArrayList localDoubleArrayList = new DoubleArrayList(new Converter().edges(paramIAxis));
    localDoubleArrayList.beforeInsert(0, (-1.0D / 0.0D));
    localDoubleArrayList.add((1.0D / 0.0D));
    int i = localDoubleArrayList.size();
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      localDoubleArrayList.set(i, quantileInverse(localDoubleArrayList.get(i)));
    }
    return splitApproximately(localDoubleArrayList, paramInt);
  }
  
  public synchronized String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer(super.toString());
    localStringBuffer.append("25%, 50%, 75% Quantiles: " + quantile(0.25D) + ", " + quantile(0.5D) + ", " + quantile(0.75D));
    localStringBuffer.append("\nquantileInverse(median): " + quantileInverse(median()));
    localStringBuffer.append("\n");
    return localStringBuffer.toString();
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hep.aida.bin.QuantileBin1D
 * JD-Core Version:    0.7.0.1
 */