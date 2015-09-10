package cern.jet.random;

import cern.jet.math.Arithmetic;
import cern.jet.random.engine.RandomEngine;
import cern.jet.stat.Probability;

public class NegativeBinomial
  extends AbstractDiscreteDistribution
{
  protected int n;
  protected double p;
  protected Gamma gamma;
  protected Poisson poisson;
  protected static NegativeBinomial shared = new NegativeBinomial(1, 0.5D, makeDefaultGenerator());
  
  public NegativeBinomial(int paramInt, double paramDouble, RandomEngine paramRandomEngine)
  {
    setRandomGenerator(paramRandomEngine);
    setNandP(paramInt, paramDouble);
    this.gamma = new Gamma(paramInt, 1.0D, paramRandomEngine);
    this.poisson = new Poisson(0.0D, paramRandomEngine);
  }
  
  public double cdf(int paramInt)
  {
    return Probability.negativeBinomial(paramInt, this.n, this.p);
  }
  
  public Object clone()
  {
    NegativeBinomial localNegativeBinomial = (NegativeBinomial)super.clone();
    if (this.poisson != null) {
      localNegativeBinomial.poisson = ((Poisson)this.poisson.clone());
    }
    localNegativeBinomial.poisson.setRandomGenerator(localNegativeBinomial.getRandomGenerator());
    if (this.gamma != null) {
      localNegativeBinomial.gamma = ((Gamma)this.gamma.clone());
    }
    localNegativeBinomial.gamma.setRandomGenerator(localNegativeBinomial.getRandomGenerator());
    return localNegativeBinomial;
  }
  
  public int nextInt()
  {
    return nextInt(this.n, this.p);
  }
  
  public int nextInt(int paramInt, double paramDouble)
  {
    double d1 = paramDouble / (1.0D - paramDouble);
    double d2 = paramDouble;
    double d3 = d1 * this.gamma.nextDouble(paramInt, 1.0D);
    return this.poisson.nextInt(d3);
  }
  
  public double pdf(int paramInt)
  {
    if (paramInt > this.n) {
      throw new IllegalArgumentException();
    }
    return Arithmetic.binomial(this.n, paramInt) * Math.pow(this.p, paramInt) * Math.pow(1.0D - this.p, this.n - paramInt);
  }
  
  public void setNandP(int paramInt, double paramDouble)
  {
    this.n = paramInt;
    this.p = paramDouble;
  }
  
  public static int staticNextInt(int paramInt, double paramDouble)
  {
    synchronized (shared)
    {
      return shared.nextInt(paramInt, paramDouble);
    }
  }
  
  public String toString()
  {
    return getClass().getName() + "(" + this.n + "," + this.p + ")";
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
 * Qualified Name:     cern.jet.random.NegativeBinomial
 * JD-Core Version:    0.7.0.1
 */