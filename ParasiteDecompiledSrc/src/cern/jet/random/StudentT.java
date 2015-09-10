package cern.jet.random;

import cern.jet.random.engine.RandomEngine;
import cern.jet.stat.Probability;

public class StudentT
  extends AbstractContinousDistribution
{
  protected double freedom;
  protected double TERM;
  protected static StudentT shared = new StudentT(1.0D, makeDefaultGenerator());
  
  public StudentT(double paramDouble, RandomEngine paramRandomEngine)
  {
    setRandomGenerator(paramRandomEngine);
    setState(paramDouble);
  }
  
  public double cdf(double paramDouble)
  {
    return Probability.studentT(this.freedom, paramDouble);
  }
  
  public double nextDouble()
  {
    return nextDouble(this.freedom);
  }
  
  public double nextDouble(double paramDouble)
  {
    if (paramDouble <= 0.0D) {
      throw new IllegalArgumentException();
    }
    double d1;
    double d2;
    double d3;
    do
    {
      d1 = 2.0D * this.randomGenerator.raw() - 1.0D;
      d2 = 2.0D * this.randomGenerator.raw() - 1.0D;
    } while ((d3 = d1 * d1 + d2 * d2) > 1.0D);
    return d1 * Math.sqrt(paramDouble * (Math.exp(-2.0D / paramDouble * Math.log(d3)) - 1.0D) / d3);
  }
  
  public double pdf(double paramDouble)
  {
    return this.TERM * Math.pow(1.0D + paramDouble * paramDouble / this.freedom, -(this.freedom + 1.0D) * 0.5D);
  }
  
  public void setState(double paramDouble)
  {
    if (paramDouble <= 0.0D) {
      throw new IllegalArgumentException();
    }
    this.freedom = paramDouble;
    double d = Fun.logGamma((paramDouble + 1.0D) / 2.0D) - Fun.logGamma(paramDouble / 2.0D);
    this.TERM = (Math.exp(d) / Math.sqrt(3.141592653589793D * paramDouble));
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
 * Qualified Name:     cern.jet.random.StudentT
 * JD-Core Version:    0.7.0.1
 */