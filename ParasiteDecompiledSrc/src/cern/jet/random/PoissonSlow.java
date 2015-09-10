package cern.jet.random;

import cern.jet.random.engine.RandomEngine;

public class PoissonSlow
  extends AbstractDiscreteDistribution
{
  protected double mean;
  protected double cached_sq;
  protected double cached_alxm;
  protected double cached_g;
  protected static final double MEAN_MAX = 2147483647.0D;
  protected static final double SWITCH_MEAN = 12.0D;
  protected static final double[] cof = { 76.180091729471457D, -86.505320329416776D, 24.014098240830911D, -1.231739572450155D, 0.00120865097386618D, -5.395239384953E-006D };
  protected static PoissonSlow shared = new PoissonSlow(0.0D, makeDefaultGenerator());
  
  public PoissonSlow(double paramDouble, RandomEngine paramRandomEngine)
  {
    setRandomGenerator(paramRandomEngine);
    setMean(paramDouble);
  }
  
  public static double logGamma(double paramDouble)
  {
    double d1 = paramDouble - 1.0D;
    double d2 = d1 + 5.5D;
    d2 -= (d1 + 0.5D) * Math.log(d2);
    double d3 = 1.000000000190015D;
    double[] arrayOfDouble = cof;
    for (int i = 0; i <= 5; i++)
    {
      d1 += 1.0D;
      d3 += arrayOfDouble[i] / d1;
    }
    return -d2 + Math.log(2.506628274631001D * d3);
  }
  
  public int nextInt()
  {
    return nextInt(this.mean);
  }
  
  private int nextInt(double paramDouble)
  {
    double d1 = paramDouble;
    double d2 = this.cached_g;
    if (d1 == -1.0D) {
      return 0;
    }
    if (d1 < 12.0D)
    {
      int i = -1;
      double d4 = 1.0D;
      do
      {
        i++;
        d4 *= this.randomGenerator.raw();
      } while (d4 >= d2);
      return i;
    }
    if (d1 < 2147483647.0D)
    {
      double d6 = this.cached_sq;
      double d7 = this.cached_alxm;
      RandomEngine localRandomEngine = this.randomGenerator;
      double d5;
      double d3;
      do
      {
        double d8;
        do
        {
          d8 = Math.tan(3.141592653589793D * localRandomEngine.raw());
          d5 = d6 * d8 + d1;
        } while (d5 < 0.0D);
        d5 = (int)d5;
        d3 = 0.9D * (1.0D + d8 * d8) * Math.exp(d5 * d7 - logGamma(d5 + 1.0D) - d2);
      } while (localRandomEngine.raw() > d3);
      return (int)d5;
    }
    return (int)d1;
  }
  
  protected int nextIntSlow()
  {
    double d1 = Math.exp(-this.mean);
    int i = 0;
    double d2 = 1.0D;
    while ((d2 >= d1) && (d2 > 0.0D))
    {
      d2 *= this.randomGenerator.raw();
      i++;
    }
    if ((d2 <= 0.0D) && (d1 > 0.0D)) {
      return (int)Math.round(this.mean);
    }
    return i - 1;
  }
  
  public void setMean(double paramDouble)
  {
    if (paramDouble != this.mean)
    {
      this.mean = paramDouble;
      if (paramDouble == -1.0D) {
        return;
      }
      if (paramDouble < 12.0D)
      {
        this.cached_g = Math.exp(-paramDouble);
      }
      else
      {
        this.cached_sq = Math.sqrt(2.0D * paramDouble);
        this.cached_alxm = Math.log(paramDouble);
        this.cached_g = (paramDouble * this.cached_alxm - logGamma(paramDouble + 1.0D));
      }
    }
  }
  
  public static int staticNextInt(double paramDouble)
  {
    synchronized (shared)
    {
      shared.setMean(paramDouble);
      return shared.nextInt();
    }
  }
  
  public String toString()
  {
    return getClass().getName() + "(" + this.mean + ")";
  }
  
  private static void xstaticSetRandomGenerator(RandomEngine paramRandomEngine)
  {
    synchronized (shared)
    {
      shared.setRandomGenerator(paramRandomEngine);
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.jet.random.PoissonSlow
 * JD-Core Version:    0.7.0.1
 */