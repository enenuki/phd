package cern.jet.random;

import cern.jet.random.engine.RandomEngine;

public class Exponential
  extends AbstractContinousDistribution
{
  protected double lambda;
  protected static Exponential shared = new Exponential(1.0D, makeDefaultGenerator());
  
  public Exponential(double paramDouble, RandomEngine paramRandomEngine)
  {
    setRandomGenerator(paramRandomEngine);
    setState(paramDouble);
  }
  
  public double cdf(double paramDouble)
  {
    if (paramDouble <= 0.0D) {
      return 0.0D;
    }
    return 1.0D - Math.exp(-paramDouble * this.lambda);
  }
  
  public double nextDouble()
  {
    return nextDouble(this.lambda);
  }
  
  public double nextDouble(double paramDouble)
  {
    return -Math.log(this.randomGenerator.raw()) / paramDouble;
  }
  
  public double pdf(double paramDouble)
  {
    if (paramDouble < 0.0D) {
      return 0.0D;
    }
    return this.lambda * Math.exp(-paramDouble * this.lambda);
  }
  
  public void setState(double paramDouble)
  {
    this.lambda = paramDouble;
  }
  
  public static double staticNextDouble(double paramDouble)
  {
    synchronized (shared)
    {
      return shared.nextDouble(paramDouble);
    }
  }
  
  public String toString()
  {
    return getClass().getName() + "(" + this.lambda + ")";
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
 * Qualified Name:     cern.jet.random.Exponential
 * JD-Core Version:    0.7.0.1
 */