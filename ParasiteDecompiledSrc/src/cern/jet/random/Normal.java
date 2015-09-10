package cern.jet.random;

import cern.jet.random.engine.RandomEngine;
import cern.jet.stat.Probability;

public class Normal
  extends AbstractContinousDistribution
{
  protected double mean;
  protected double variance;
  protected double standardDeviation;
  protected double cache;
  protected boolean cacheFilled;
  protected double SQRT_INV;
  protected static Normal shared = new Normal(0.0D, 1.0D, makeDefaultGenerator());
  
  public Normal(double paramDouble1, double paramDouble2, RandomEngine paramRandomEngine)
  {
    setRandomGenerator(paramRandomEngine);
    setState(paramDouble1, paramDouble2);
  }
  
  public double cdf(double paramDouble)
  {
    return Probability.normal(this.mean, this.variance, paramDouble);
  }
  
  public double nextDouble()
  {
    return nextDouble(this.mean, this.standardDeviation);
  }
  
  public double nextDouble(double paramDouble1, double paramDouble2)
  {
    if ((this.cacheFilled) && (this.mean == paramDouble1) && (this.standardDeviation == paramDouble2))
    {
      this.cacheFilled = false;
      return this.cache;
    }
    double d1;
    double d2;
    double d3;
    do
    {
      d1 = 2.0D * this.randomGenerator.raw() - 1.0D;
      d2 = 2.0D * this.randomGenerator.raw() - 1.0D;
      d3 = d1 * d1 + d2 * d2;
    } while (d3 >= 1.0D);
    double d4 = Math.sqrt(-2.0D * Math.log(d3) / d3);
    this.cache = (paramDouble1 + paramDouble2 * d1 * d4);
    this.cacheFilled = true;
    return paramDouble1 + paramDouble2 * d2 * d4;
  }
  
  public double pdf(double paramDouble)
  {
    double d = paramDouble - this.mean;
    return this.SQRT_INV * Math.exp(-(d * d) / (2.0D * this.variance));
  }
  
  protected void setRandomGenerator(RandomEngine paramRandomEngine)
  {
    super.setRandomGenerator(paramRandomEngine);
    this.cacheFilled = false;
  }
  
  public void setState(double paramDouble1, double paramDouble2)
  {
    if ((paramDouble1 != this.mean) || (paramDouble2 != this.standardDeviation))
    {
      this.mean = paramDouble1;
      this.standardDeviation = paramDouble2;
      this.variance = (paramDouble2 * paramDouble2);
      this.cacheFilled = false;
      this.SQRT_INV = (1.0D / Math.sqrt(6.283185307179586D * this.variance));
    }
  }
  
  public static double staticNextDouble(double paramDouble1, double paramDouble2)
  {
    synchronized (shared)
    {
      return shared.nextDouble(paramDouble1, paramDouble2);
    }
  }
  
  public String toString()
  {
    return getClass().getName() + "(" + this.mean + "," + this.standardDeviation + ")";
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
 * Qualified Name:     cern.jet.random.Normal
 * JD-Core Version:    0.7.0.1
 */