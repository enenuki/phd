package cern.jet.random;

import cern.jet.random.engine.RandomEngine;

public class BreitWigner
  extends AbstractContinousDistribution
{
  protected double mean;
  protected double gamma;
  protected double cut;
  protected static BreitWigner shared = new BreitWigner(1.0D, 0.2D, 1.0D, makeDefaultGenerator());
  
  public BreitWigner(double paramDouble1, double paramDouble2, double paramDouble3, RandomEngine paramRandomEngine)
  {
    setRandomGenerator(paramRandomEngine);
    setState(paramDouble1, paramDouble2, paramDouble3);
  }
  
  public double nextDouble()
  {
    return nextDouble(this.mean, this.gamma, this.cut);
  }
  
  public double nextDouble(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    if (paramDouble2 == 0.0D) {
      return paramDouble1;
    }
    if (paramDouble3 == (-1.0D / 0.0D))
    {
      d2 = 2.0D * this.randomGenerator.raw() - 1.0D;
      d3 = 0.5D * paramDouble2 * Math.tan(d2 * 1.570796326794897D);
      return paramDouble1 + d3;
    }
    double d1 = Math.atan(2.0D * paramDouble3 / paramDouble2);
    double d2 = 2.0D * this.randomGenerator.raw() - 1.0D;
    double d3 = 0.5D * paramDouble2 * Math.tan(d2 * d1);
    return paramDouble1 + d3;
  }
  
  public void setState(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    this.mean = paramDouble1;
    this.gamma = paramDouble2;
    this.cut = paramDouble3;
  }
  
  public static double staticNextDouble(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    synchronized (shared)
    {
      return shared.nextDouble(paramDouble1, paramDouble2, paramDouble3);
    }
  }
  
  public String toString()
  {
    return getClass().getName() + "(" + this.mean + "," + this.gamma + "," + this.cut + ")";
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
 * Qualified Name:     cern.jet.random.BreitWigner
 * JD-Core Version:    0.7.0.1
 */