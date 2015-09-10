package cern.jet.random;

import cern.jet.random.engine.RandomEngine;
import cern.jet.stat.Probability;

public class ChiSquare
  extends AbstractContinousDistribution
{
  protected double freedom;
  private double freedom_in = -1.0D;
  private double b;
  private double vm;
  private double vp;
  private double vd;
  protected static ChiSquare shared = new ChiSquare(1.0D, makeDefaultGenerator());
  
  public ChiSquare(double paramDouble, RandomEngine paramRandomEngine)
  {
    setRandomGenerator(paramRandomEngine);
    setState(paramDouble);
  }
  
  public double cdf(double paramDouble)
  {
    return Probability.chiSquare(this.freedom, paramDouble);
  }
  
  public double nextDouble()
  {
    return nextDouble(this.freedom);
  }
  
  public double nextDouble(double paramDouble)
  {
    double d1;
    double d2;
    double d3;
    double d4;
    double d5;
    if (paramDouble == 1.0D)
    {
      do
      {
        do
        {
          d1 = this.randomGenerator.raw();
          d2 = this.randomGenerator.raw() * 0.857763884960707D;
          d3 = d2 / d1;
        } while (d3 < 0.0D);
        d4 = d3 * d3;
        d5 = 2.5D - d4;
        if (d3 < 0.0D) {
          d5 += d4 * d3 / (3.0D * d3);
        }
        if (d1 < d5 * 0.3894003915D) {
          return d3 * d3;
        }
      } while ((d4 > 1.036961043D / d1 + 1.4D) || (2.0D * Math.log(d1) >= -d4 * 0.5D));
      return d3 * d3;
    }
    if (paramDouble != this.freedom_in)
    {
      this.b = Math.sqrt(paramDouble - 1.0D);
      this.vm = (-0.6065306597D * (1.0D - 0.25D / (this.b * this.b + 1.0D)));
      this.vm = (-this.b > this.vm ? -this.b : this.vm);
      this.vp = (0.6065306597D * (0.7071067812D + this.b) / (0.5D + this.b));
      this.vd = (this.vp - this.vm);
      this.freedom_in = paramDouble;
    }
    do
    {
      do
      {
        d1 = this.randomGenerator.raw();
        d2 = this.randomGenerator.raw() * this.vd + this.vm;
        d3 = d2 / d1;
      } while (d3 < -this.b);
      d4 = d3 * d3;
      d5 = 2.5D - d4;
      if (d3 < 0.0D) {
        d5 += d4 * d3 / (3.0D * (d3 + this.b));
      }
      if (d1 < d5 * 0.3894003915D) {
        return (d3 + this.b) * (d3 + this.b);
      }
    } while ((d4 > 1.036961043D / d1 + 1.4D) || (2.0D * Math.log(d1) >= Math.log(1.0D + d3 / this.b) * this.b * this.b - d4 * 0.5D - d3 * this.b));
    return (d3 + this.b) * (d3 + this.b);
  }
  
  public double pdf(double paramDouble)
  {
    if (paramDouble <= 0.0D) {
      throw new IllegalArgumentException();
    }
    double d = Fun.logGamma(this.freedom / 2.0D);
    return Math.exp((this.freedom / 2.0D - 1.0D) * Math.log(paramDouble / 2.0D) - paramDouble / 2.0D - d) / 2.0D;
  }
  
  public void setState(double paramDouble)
  {
    if (paramDouble < 1.0D) {
      throw new IllegalArgumentException();
    }
    this.freedom = paramDouble;
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
    return getClass().getName() + "(" + this.freedom + ")";
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
 * Qualified Name:     cern.jet.random.ChiSquare
 * JD-Core Version:    0.7.0.1
 */