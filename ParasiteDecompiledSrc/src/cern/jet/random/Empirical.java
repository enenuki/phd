package cern.jet.random;

import cern.jet.random.engine.RandomEngine;

public class Empirical
  extends AbstractContinousDistribution
{
  protected double[] cdf;
  protected int interpolationType;
  public static final int LINEAR_INTERPOLATION = 0;
  public static final int NO_INTERPOLATION = 1;
  
  public Empirical(double[] paramArrayOfDouble, int paramInt, RandomEngine paramRandomEngine)
  {
    setRandomGenerator(paramRandomEngine);
    setState(paramArrayOfDouble, paramInt);
  }
  
  public double cdf(int paramInt)
  {
    if (paramInt < 0) {
      return 0.0D;
    }
    if (paramInt >= this.cdf.length - 1) {
      return 1.0D;
    }
    return this.cdf[paramInt];
  }
  
  public Object clone()
  {
    Empirical localEmpirical = (Empirical)super.clone();
    if (this.cdf != null) {
      localEmpirical.cdf = ((double[])this.cdf.clone());
    }
    return localEmpirical;
  }
  
  public double nextDouble()
  {
    double d1 = this.randomGenerator.raw();
    if (this.cdf == null) {
      return d1;
    }
    int i = this.cdf.length - 1;
    int j = 0;
    int k = i;
    while (k > j + 1)
    {
      int m = k + j + 1 >> 1;
      if (d1 >= this.cdf[m]) {
        j = m;
      } else {
        k = m;
      }
    }
    if (this.interpolationType == 1) {
      return j / i;
    }
    if (this.interpolationType == 0)
    {
      double d2 = this.cdf[k] - this.cdf[j];
      if (d2 == 0.0D) {
        return (j + 0.5D) / i;
      }
      double d3 = (d1 - this.cdf[j]) / d2;
      return (j + d3) / i;
    }
    throw new InternalError();
  }
  
  public double pdf(double paramDouble)
  {
    throw new RuntimeException("not implemented");
  }
  
  public double pdf(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this.cdf.length - 1)) {
      return 0.0D;
    }
    return this.cdf[(paramInt - 1)] - this.cdf[paramInt];
  }
  
  public void setState(double[] paramArrayOfDouble, int paramInt)
  {
    if ((paramInt != 0) && (paramInt != 1)) {
      throw new IllegalArgumentException("Illegal Interpolation Type");
    }
    this.interpolationType = paramInt;
    if ((paramArrayOfDouble == null) || (paramArrayOfDouble.length == 0))
    {
      this.cdf = null;
      return;
    }
    int i = paramArrayOfDouble.length;
    this.cdf = new double[i + 1];
    this.cdf[0] = 0.0D;
    for (int j = 0; j < i; j++)
    {
      double d = paramArrayOfDouble[j];
      if (d < 0.0D) {
        throw new IllegalArgumentException("Negative probability");
      }
      this.cdf[(j + 1)] = (this.cdf[j] + d);
    }
    if (this.cdf[i] <= 0.0D) {
      throw new IllegalArgumentException("At leat one probability must be > 0.0");
    }
    for (j = 0; j < i + 1; j++) {
      this.cdf[j] /= this.cdf[i];
    }
  }
  
  public String toString()
  {
    String str = null;
    if (this.interpolationType == 1) {
      str = "NO_INTERPOLATION";
    }
    if (this.interpolationType == 0) {
      str = "LINEAR_INTERPOLATION";
    }
    return getClass().getName() + "(" + (this.cdf != null ? this.cdf.length : 0) + "," + str + ")";
  }
  
  private int xnBins()
  {
    return this.cdf.length - 1;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.jet.random.Empirical
 * JD-Core Version:    0.7.0.1
 */