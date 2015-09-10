package cern.jet.random;

import cern.jet.random.engine.RandomEngine;

public class ExponentialPower
  extends AbstractContinousDistribution
{
  protected double tau;
  private double s;
  private double sm1;
  private double tau_set = -1.0D;
  protected static ExponentialPower shared = new ExponentialPower(1.0D, makeDefaultGenerator());
  
  public ExponentialPower(double paramDouble, RandomEngine paramRandomEngine)
  {
    setRandomGenerator(paramRandomEngine);
    setState(paramDouble);
  }
  
  public double nextDouble()
  {
    return nextDouble(this.tau);
  }
  
  public double nextDouble(double paramDouble)
  {
    if (paramDouble != this.tau_set)
    {
      this.s = (1.0D / paramDouble);
      this.sm1 = (1.0D - this.s);
      this.tau_set = paramDouble;
    }
    double d1;
    double d3;
    double d4;
    do
    {
      d1 = this.randomGenerator.raw();
      d1 = 2.0D * d1 - 1.0D;
      double d2 = Math.abs(d1);
      d3 = this.randomGenerator.raw();
      if (d2 <= this.sm1)
      {
        d4 = d2;
      }
      else
      {
        double d5 = paramDouble * (1.0D - d2);
        d4 = this.sm1 - this.s * Math.log(d5);
        d3 *= d5;
      }
    } while (Math.log(d3) > -Math.exp(Math.log(d4) * paramDouble));
    if (d1 < 0.0D) {
      return d4;
    }
    return -d4;
  }
  
  public void setState(double paramDouble)
  {
    if (paramDouble < 1.0D) {
      throw new IllegalArgumentException();
    }
    this.tau = paramDouble;
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
    return getClass().getName() + "(" + this.tau + ")";
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
 * Qualified Name:     cern.jet.random.ExponentialPower
 * JD-Core Version:    0.7.0.1
 */