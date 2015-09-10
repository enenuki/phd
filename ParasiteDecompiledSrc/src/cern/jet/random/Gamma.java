package cern.jet.random;

import cern.jet.random.engine.RandomEngine;
import cern.jet.stat.Probability;

public class Gamma
  extends AbstractContinousDistribution
{
  protected double alpha;
  protected double lambda;
  protected static Gamma shared = new Gamma(1.0D, 1.0D, makeDefaultGenerator());
  
  public Gamma(double paramDouble1, double paramDouble2, RandomEngine paramRandomEngine)
  {
    setRandomGenerator(paramRandomEngine);
    setState(paramDouble1, paramDouble2);
  }
  
  public double cdf(double paramDouble)
  {
    return Probability.gamma(this.alpha, this.lambda, paramDouble);
  }
  
  public double nextDouble()
  {
    return nextDouble(this.alpha, this.lambda);
  }
  
  public double nextDouble(double paramDouble1, double paramDouble2)
  {
    double d1 = paramDouble1;
    double d2 = -1.0D;
    double d3 = -1.0D;
    double d4 = 0.0D;
    double d5 = 0.0D;
    double d6 = 0.0D;
    double d9 = 0.0D;
    double d10 = 0.0D;
    double d11 = 0.0D;
    double d12 = 0.0D;
    double d13 = 0.0416666664D;
    double d14 = 0.0208333723D;
    double d15 = 0.0079849875D;
    double d16 = 0.0015746717D;
    double d17 = -0.0003349403D;
    double d18 = 0.0003340332D;
    double d19 = 0.0006053049D;
    double d20 = -0.0004701849D;
    double d21 = 0.000171032D;
    double d22 = 0.333333333D;
    double d23 = -0.249999949D;
    double d24 = 0.199999867D;
    double d25 = -0.166677482D;
    double d26 = 0.142873973D;
    double d27 = -0.124385581D;
    double d28 = 0.11036831D;
    double d29 = -0.112750886D;
    double d30 = 0.104089866D;
    double d31 = 1.0D;
    double d32 = 0.499999994D;
    double d33 = 0.166666848D;
    double d34 = 0.041664508D;
    double d35 = 0.008345521999999999D;
    double d36 = 0.001353826D;
    double d37 = 0.000247453D;
    if (d1 <= 0.0D) {
      throw new IllegalArgumentException();
    }
    if (paramDouble2 <= 0.0D) {
      new IllegalArgumentException();
    }
    if (d1 < 1.0D)
    {
      d4 = 1.0D + 0.36788794412D * d1;
      do
      {
        double d39;
        do
        {
          d39 = d4 * this.randomGenerator.raw();
          if (d39 > 1.0D) {
            break;
          }
          d38 = Math.exp(Math.log(d39) / d1);
        } while (Math.log(this.randomGenerator.raw()) > -d38);
        return d38 / paramDouble2;
        d38 = -Math.log((d4 - d39) / d1);
      } while (Math.log(this.randomGenerator.raw()) > (d1 - 1.0D) * Math.log(d38));
      return d38 / paramDouble2;
    }
    if (d1 != d2)
    {
      d2 = d1;
      d11 = d1 - 0.5D;
      d9 = Math.sqrt(d11);
      d6 = 5.656854249D - 12.0D * d9;
    }
    double d47;
    double d49;
    do
    {
      d47 = 2.0D * this.randomGenerator.raw() - 1.0D;
      double d48 = 2.0D * this.randomGenerator.raw() - 1.0D;
      d49 = d47 * d47 + d48 * d48;
    } while (d49 > 1.0D);
    double d41 = d47 * Math.sqrt(-2.0D * Math.log(d49) / d49);
    double d46 = d9 + 0.5D * d41;
    double d38 = d46 * d46;
    if (d41 >= 0.0D) {
      return d38 / paramDouble2;
    }
    double d43 = this.randomGenerator.raw();
    if (d6 * d43 <= d41 * d41 * d41) {
      return d38 / paramDouble2;
    }
    if (d1 != d3)
    {
      d3 = d1;
      double d8 = 1.0D / d1;
      d12 = ((((((((d21 * d8 + d20) * d8 + d19) * d8 + d18) * d8 + d17) * d8 + d16) * d8 + d15) * d8 + d14) * d8 + d13) * d8;
      if (d1 > 3.686D)
      {
        if (d1 > 13.022D)
        {
          d4 = 1.77D;
          d10 = 0.75D;
          d5 = 0.1515D / d9;
        }
        else
        {
          d4 = 1.654D + 0.0076D * d11;
          d10 = 1.68D / d9 + 0.275D;
          d5 = 0.062D / d9 + 0.024D;
        }
      }
      else
      {
        d4 = 0.463D + d9 - 0.178D * d11;
        d10 = 1.235D;
        d5 = 0.195D / d9 - 0.079D + 0.016D * d9;
      }
    }
    double d44;
    double d40;
    if (d46 > 0.0D)
    {
      d44 = d41 / (d9 + d9);
      if (Math.abs(d44) > 0.25D) {
        d40 = d12 - d9 * d41 + 0.25D * d41 * d41 + (d11 + d11) * Math.log(1.0D + d44);
      } else {
        d40 = d12 + 0.5D * d41 * d41 * ((((((((d30 * d44 + d29) * d44 + d28) * d44 + d27) * d44 + d26) * d44 + d25) * d44 + d24) * d44 + d23) * d44 + d22) * d44;
      }
      if (Math.log(1.0D - d43) <= d40) {
        return d38 / paramDouble2;
      }
    }
    double d7;
    double d42;
    double d45;
    do
    {
      do
      {
        do
        {
          d7 = -Math.log(this.randomGenerator.raw());
          d43 = this.randomGenerator.raw();
          d43 = d43 + d43 - 1.0D;
          d42 = d43 > 0.0D ? 1.0D : -1.0D;
          d41 = d4 + d7 * d10 * d42;
        } while (d41 <= -0.71874483771719D);
        d44 = d41 / (d9 + d9);
        if (Math.abs(d44) > 0.25D) {
          d40 = d12 - d9 * d41 + 0.25D * d41 * d41 + (d11 + d11) * Math.log(1.0D + d44);
        } else {
          d40 = d12 + 0.5D * d41 * d41 * ((((((((d30 * d44 + d29) * d44 + d28) * d44 + d27) * d44 + d26) * d44 + d25) * d44 + d24) * d44 + d23) * d44 + d22) * d44;
        }
      } while (d40 <= 0.0D);
      if (d40 > 0.5D) {
        d45 = Math.exp(d40) - 1.0D;
      } else {
        d45 = ((((((d37 * d40 + d36) * d40 + d35) * d40 + d34) * d40 + d33) * d40 + d32) * d40 + d31) * d40;
      }
    } while (d5 * d43 * d42 > d45 * Math.exp(d7 - 0.5D * d41 * d41));
    d46 = d9 + 0.5D * d41;
    return d46 * d46 / paramDouble2;
  }
  
  public double pdf(double paramDouble)
  {
    if (paramDouble < 0.0D) {
      throw new IllegalArgumentException();
    }
    if (paramDouble == 0.0D)
    {
      if (this.alpha == 1.0D) {
        return 1.0D / this.lambda;
      }
      return 0.0D;
    }
    if (this.alpha == 1.0D) {
      return Math.exp(-paramDouble / this.lambda) / this.lambda;
    }
    return Math.exp((this.alpha - 1.0D) * Math.log(paramDouble / this.lambda) - paramDouble / this.lambda - Fun.logGamma(this.alpha)) / this.lambda;
  }
  
  public void setState(double paramDouble1, double paramDouble2)
  {
    if (paramDouble1 <= 0.0D) {
      throw new IllegalArgumentException();
    }
    if (paramDouble2 <= 0.0D) {
      throw new IllegalArgumentException();
    }
    this.alpha = paramDouble1;
    this.lambda = paramDouble2;
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
    return getClass().getName() + "(" + this.alpha + "," + this.lambda + ")";
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
 * Qualified Name:     cern.jet.random.Gamma
 * JD-Core Version:    0.7.0.1
 */