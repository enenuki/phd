package cern.jet.random;

import cern.jet.random.engine.RandomEngine;

public class Hyperbolic
  extends AbstractContinousDistribution
{
  protected double alpha;
  protected double beta;
  protected double a_setup = 0.0D;
  protected double b_setup = -1.0D;
  protected double x;
  protected double u;
  protected double v;
  protected double e;
  protected double hr;
  protected double hl;
  protected double s;
  protected double pm;
  protected double pr;
  protected double samb;
  protected double pmr;
  protected double mpa_1;
  protected double mmb_1;
  protected static Hyperbolic shared = new Hyperbolic(10.0D, 10.0D, makeDefaultGenerator());
  
  public Hyperbolic(double paramDouble1, double paramDouble2, RandomEngine paramRandomEngine)
  {
    setRandomGenerator(paramRandomEngine);
    setState(paramDouble1, paramDouble2);
  }
  
  public double nextDouble()
  {
    return nextDouble(this.alpha, this.beta);
  }
  
  public double nextDouble(double paramDouble1, double paramDouble2)
  {
    double d1 = paramDouble1;
    double d2 = paramDouble2;
    if ((this.a_setup != d1) || (this.b_setup != d2))
    {
      double d6 = d1 * d1 - d2 * d2;
      this.samb = Math.sqrt(d6);
      double d5 = d2 / this.samb;
      double d11 = d1 * Math.sqrt(2.0D * this.samb + 1.0D);
      double d12 = d2 * (this.samb + 1.0D);
      double d3 = (d12 + d11) / d6;
      double d4 = (d12 - d11) / d6;
      double d7 = d3 - d5;
      double d8 = -d4 + d5;
      this.hr = (-1.0D / (-d1 * d3 / Math.sqrt(1.0D + d3 * d3) + d2));
      this.hl = (1.0D / (-d1 * d4 / Math.sqrt(1.0D + d4 * d4) + d2));
      double d9 = d7 - this.hr;
      double d10 = d8 - this.hl;
      this.mmb_1 = (d5 - d10);
      this.mpa_1 = (d5 + d9);
      this.s = (d7 + d8);
      this.pm = ((d9 + d10) / this.s);
      this.pr = (this.hr / this.s);
      this.pmr = (this.pm + this.pr);
      this.a_setup = d1;
      this.b_setup = d2;
    }
    for (;;)
    {
      this.u = this.randomGenerator.raw();
      this.v = this.randomGenerator.raw();
      if (this.u <= this.pm)
      {
        this.x = (this.mmb_1 + this.u * this.s);
        if (Math.log(this.v) <= -d1 * Math.sqrt(1.0D + this.x * this.x) + d2 * this.x + this.samb) {
          break;
        }
      }
      else if (this.u <= this.pmr)
      {
        this.e = (-Math.log((this.u - this.pm) / this.pr));
        this.x = (this.mpa_1 + this.hr * this.e);
        if (Math.log(this.v) - this.e <= -d1 * Math.sqrt(1.0D + this.x * this.x) + d2 * this.x + this.samb) {
          break;
        }
      }
      else
      {
        this.e = Math.log((this.u - this.pmr) / (1.0D - this.pmr));
        this.x = (this.mmb_1 + this.hl * this.e);
        if (Math.log(this.v) + this.e <= -d1 * Math.sqrt(1.0D + this.x * this.x) + d2 * this.x + this.samb) {
          break;
        }
      }
    }
    return this.x;
  }
  
  public void setState(double paramDouble1, double paramDouble2)
  {
    this.alpha = paramDouble1;
    this.beta = paramDouble2;
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
    return getClass().getName() + "(" + this.alpha + "," + this.beta + ")";
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
 * Qualified Name:     cern.jet.random.Hyperbolic
 * JD-Core Version:    0.7.0.1
 */