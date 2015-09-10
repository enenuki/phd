package cern.jet.random;

import cern.jet.math.Arithmetic;
import cern.jet.random.engine.RandomEngine;
import cern.jet.stat.Probability;

public class Poisson
  extends AbstractDiscreteDistribution
{
  protected double mean;
  protected double my_old = -1.0D;
  protected double p;
  protected double q;
  protected double p0;
  protected double[] pp = new double[36];
  protected int llll;
  protected double my_last = -1.0D;
  protected double ll;
  protected int k2;
  protected int k4;
  protected int k1;
  protected int k5;
  protected double dl;
  protected double dr;
  protected double r1;
  protected double r2;
  protected double r4;
  protected double r5;
  protected double lr;
  protected double l_my;
  protected double c_pm;
  protected double f1;
  protected double f2;
  protected double f4;
  protected double f5;
  protected double p1;
  protected double p2;
  protected double p3;
  protected double p4;
  protected double p5;
  protected double p6;
  protected int m;
  protected static final double MEAN_MAX = 2147483647.0D;
  protected static final double SWITCH_MEAN = 10.0D;
  protected static Poisson shared = new Poisson(0.0D, makeDefaultGenerator());
  
  public Poisson(double paramDouble, RandomEngine paramRandomEngine)
  {
    setRandomGenerator(paramRandomEngine);
    setMean(paramDouble);
  }
  
  public double cdf(int paramInt)
  {
    return Probability.poisson(paramInt, this.mean);
  }
  
  public Object clone()
  {
    Poisson localPoisson = (Poisson)super.clone();
    if (this.pp != null) {
      localPoisson.pp = ((double[])this.pp.clone());
    }
    return localPoisson;
  }
  
  private static double f(int paramInt, double paramDouble1, double paramDouble2)
  {
    return Math.exp(paramInt * paramDouble1 - Arithmetic.logFactorial(paramInt) - paramDouble2);
  }
  
  public int nextInt()
  {
    return nextInt(this.mean);
  }
  
  public int nextInt(double paramDouble)
  {
    RandomEngine localRandomEngine = this.randomGenerator;
    double d1 = paramDouble;
    if (d1 < 10.0D)
    {
      if (d1 != this.my_old)
      {
        this.my_old = d1;
        this.llll = 0;
        this.p = Math.exp(-d1);
        this.q = this.p;
        this.p0 = this.p;
      }
      this.m = (d1 > 1.0D ? (int)d1 : 1);
      for (;;)
      {
        double d2 = localRandomEngine.raw();
        int i = 0;
        if (d2 <= this.p0) {
          return i;
        }
        if (this.llll != 0)
        {
          int j = d2 > 0.458D ? Math.min(this.llll, this.m) : 1;
          for (i = j; i <= this.llll; i++) {
            if (d2 <= this.pp[i]) {
              return i;
            }
          }
          if (this.llll == 35) {}
        }
        else
        {
          for (i = this.llll + 1; i <= 35; i++)
          {
            this.p *= d1 / i;
            this.q += this.p;
            this.pp[i] = this.q;
            if (d2 <= this.q)
            {
              this.llll = i;
              return i;
            }
          }
          this.llll = 35;
        }
      }
    }
    if (d1 < 2147483647.0D)
    {
      this.m = ((int)d1);
      if (d1 != this.my_last)
      {
        this.my_last = d1;
        double d3 = Math.sqrt(d1 + 0.25D);
        this.k2 = ((int)Math.ceil(d1 - 0.5D - d3));
        this.k4 = ((int)(d1 - 0.5D + d3));
        this.k1 = (this.k2 + this.k2 - this.m + 1);
        this.k5 = (this.k4 + this.k4 - this.m);
        this.dl = (this.k2 - this.k1);
        this.dr = (this.k5 - this.k4);
        this.r1 = (d1 / this.k1);
        this.r2 = (d1 / this.k2);
        this.r4 = (d1 / (this.k4 + 1));
        this.r5 = (d1 / (this.k5 + 1));
        this.ll = Math.log(this.r1);
        this.lr = (-Math.log(this.r5));
        this.l_my = Math.log(d1);
        this.c_pm = (this.m * this.l_my - Arithmetic.logFactorial(this.m));
        this.f2 = f(this.k2, this.l_my, this.c_pm);
        this.f4 = f(this.k4, this.l_my, this.c_pm);
        this.f1 = f(this.k1, this.l_my, this.c_pm);
        this.f5 = f(this.k5, this.l_my, this.c_pm);
        this.p1 = (this.f2 * (this.dl + 1.0D));
        this.p2 = (this.f2 * this.dl + this.p1);
        this.p3 = (this.f4 * (this.dr + 1.0D) + this.p2);
        this.p4 = (this.f4 * this.dr + this.p3);
        this.p5 = (this.f1 / this.ll + this.p4);
        this.p6 = (this.f5 / this.lr + this.p5);
      }
      double d6;
      int n;
      label1315:
      do
      {
        double d4;
        int k;
        do
        {
          double d5;
          int i1;
          if ((d4 = localRandomEngine.raw() * this.p6) < this.p2)
          {
            if ((d5 = d4 - this.p1) < 0.0D) {
              return this.k2 + (int)(d4 / this.f2);
            }
            if ((d6 = d5 / this.dl) < this.f1) {
              return this.k1 + (int)(d5 / this.f1);
            }
            k = (int)(this.dl * localRandomEngine.raw()) + 1;
            if (d6 <= this.f2 - k * (this.f2 - this.f2 / this.r2)) {
              return this.k2 - k;
            }
            if ((d5 = this.f2 + this.f2 - d6) < 1.0D)
            {
              i1 = this.k2 + k;
              if (d5 <= this.f2 + k * (1.0D - this.f2) / (this.dl + 1.0D)) {
                return i1;
              }
              if (d5 <= f(i1, this.l_my, this.c_pm)) {
                return i1;
              }
            }
            n = this.k2 - k;
            break label1315;
          }
          if (d4 < this.p4)
          {
            if ((d5 = d4 - this.p3) < 0.0D) {
              return this.k4 - (int)((d4 - this.p2) / this.f4);
            }
            if ((d6 = d5 / this.dr) < this.f5) {
              return this.k5 - (int)(d5 / this.f5);
            }
            k = (int)(this.dr * localRandomEngine.raw()) + 1;
            if (d6 <= this.f4 - k * (this.f4 - this.f4 * this.r4)) {
              return this.k4 + k;
            }
            if ((d5 = this.f4 + this.f4 - d6) < 1.0D)
            {
              i1 = this.k4 - k;
              if (d5 <= this.f4 + k * (1.0D - this.f4) / this.dr) {
                return i1;
              }
              if (d5 <= f(i1, this.l_my, this.c_pm)) {
                return i1;
              }
            }
            n = this.k4 + k;
            break label1315;
          }
          d6 = localRandomEngine.raw();
          if (d4 >= this.p5) {
            break;
          }
          k = (int)(1.0D - Math.log(d6) / this.ll);
        } while ((n = this.k1 - k) < 0);
        d6 *= (d4 - this.p4) * this.ll;
        if (d6 <= this.f1 - k * (this.f1 - this.f1 / this.r1))
        {
          return n;
          k = (int)(1.0D - Math.log(d6) / this.lr);
          n = this.k5 + k;
          d6 *= (d4 - this.p5) * this.lr;
          if (d6 <= this.f5 - k * (this.f5 - this.f5 * this.r5)) {
            return n;
          }
        }
      } while (Math.log(d6) > n * this.l_my - Arithmetic.logFactorial(n) - this.c_pm);
      return n;
    }
    return (int)d1;
  }
  
  public double pdf(int paramInt)
  {
    return Math.exp(paramInt * Math.log(this.mean) - Arithmetic.logFactorial(paramInt) - this.mean);
  }
  
  public void setMean(double paramDouble)
  {
    this.mean = paramDouble;
  }
  
  public static int staticNextInt(double paramDouble)
  {
    synchronized (shared)
    {
      shared.setMean(paramDouble);
      return shared.nextInt();
    }
  }
  
  public String toString()
  {
    return getClass().getName() + "(" + this.mean + ")";
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
 * Qualified Name:     cern.jet.random.Poisson
 * JD-Core Version:    0.7.0.1
 */