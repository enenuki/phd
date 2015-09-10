package cern.jet.random;

import cern.jet.random.engine.RandomEngine;

public class VonMises
  extends AbstractContinousDistribution
{
  protected double my_k;
  private double k_set = -1.0D;
  private double tau;
  private double rho;
  private double r;
  protected static VonMises shared = new VonMises(1.0D, makeDefaultGenerator());
  
  public VonMises(double paramDouble, RandomEngine paramRandomEngine)
  {
    setRandomGenerator(paramRandomEngine);
    setState(paramDouble);
  }
  
  public double nextDouble()
  {
    return nextDouble(this.my_k);
  }
  
  public double nextDouble(double paramDouble)
  {
    if (paramDouble <= 0.0D) {
      throw new IllegalArgumentException();
    }
    if (this.k_set != paramDouble)
    {
      this.tau = (1.0D + Math.sqrt(1.0D + 4.0D * paramDouble * paramDouble));
      this.rho = ((this.tau - Math.sqrt(2.0D * this.tau)) / (2.0D * paramDouble));
      this.r = ((1.0D + this.rho * this.rho) / (2.0D * this.rho));
      this.k_set = paramDouble;
    }
    double d2;
    double d3;
    double d4;
    do
    {
      double d1 = this.randomGenerator.raw();
      d2 = this.randomGenerator.raw();
      double d5 = Math.cos(3.141592653589793D * d1);
      d3 = (1.0D + this.r * d5) / (this.r + d5);
      d4 = paramDouble * (this.r - d3);
    } while ((d4 * (2.0D - d4) < d2) && (Math.log(d4 / d2) + 1.0D < d4));
    return this.randomGenerator.raw() > 0.5D ? Math.acos(d3) : -Math.acos(d3);
  }
  
  public void setState(double paramDouble)
  {
    if (paramDouble <= 0.0D) {
      throw new IllegalArgumentException();
    }
    this.my_k = paramDouble;
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
    return getClass().getName() + "(" + this.my_k + ")";
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
 * Qualified Name:     cern.jet.random.VonMises
 * JD-Core Version:    0.7.0.1
 */