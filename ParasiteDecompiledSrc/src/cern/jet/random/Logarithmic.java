package cern.jet.random;

import cern.jet.random.engine.RandomEngine;

public class Logarithmic
  extends AbstractContinousDistribution
{
  protected double my_p;
  private double t;
  private double h;
  private double a_prev = -1.0D;
  protected static Logarithmic shared = new Logarithmic(0.5D, makeDefaultGenerator());
  
  public Logarithmic(double paramDouble, RandomEngine paramRandomEngine)
  {
    setRandomGenerator(paramRandomEngine);
    setState(paramDouble);
  }
  
  public double nextDouble()
  {
    return nextDouble(this.my_p);
  }
  
  public double nextDouble(double paramDouble)
  {
    if (paramDouble != this.a_prev)
    {
      this.a_prev = paramDouble;
      if (paramDouble < 0.97D) {
        this.t = (-paramDouble / Math.log(1.0D - paramDouble));
      } else {
        this.h = Math.log(1.0D - paramDouble);
      }
    }
    double d1 = this.randomGenerator.raw();
    int i;
    if (paramDouble < 0.97D)
    {
      i = 1;
      for (double d3 = this.t; d1 > d3; d3 *= paramDouble * (i - 1.0D) / i)
      {
        d1 -= d3;
        i++;
      }
      return i;
    }
    if (d1 > paramDouble) {
      return 1.0D;
    }
    d1 = this.randomGenerator.raw();
    double d2 = d1;
    double d4 = 1.0D - Math.exp(d2 * this.h);
    if (d1 <= d4 * d4)
    {
      i = (int)(1.0D + Math.log(d1) / Math.log(d4));
      return i;
    }
    if (d1 > d4) {
      return 1.0D;
    }
    return 2.0D;
  }
  
  public void setState(double paramDouble)
  {
    this.my_p = paramDouble;
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
    return getClass().getName() + "(" + this.my_p + ")";
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
 * Qualified Name:     cern.jet.random.Logarithmic
 * JD-Core Version:    0.7.0.1
 */