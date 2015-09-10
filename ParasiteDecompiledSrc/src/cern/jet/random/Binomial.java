package cern.jet.random;

import cern.jet.math.Arithmetic;
import cern.jet.random.engine.RandomEngine;
import cern.jet.stat.Probability;

public class Binomial
  extends AbstractDiscreteDistribution
{
  protected int n;
  protected double p;
  private int n_last = -1;
  private int n_prev = -1;
  private double par;
  private double np;
  private double p0;
  private double q;
  private double p_last = -1.0D;
  private double p_prev = -1.0D;
  private int b;
  private int m;
  private int nm;
  private double pq;
  private double rc;
  private double ss;
  private double xm;
  private double xl;
  private double xr;
  private double ll;
  private double lr;
  private double c;
  private double p1;
  private double p2;
  private double p3;
  private double p4;
  private double ch;
  private double log_p;
  private double log_q;
  private double log_n;
  protected static Binomial shared = new Binomial(1, 0.5D, makeDefaultGenerator());
  
  public Binomial(int paramInt, double paramDouble, RandomEngine paramRandomEngine)
  {
    setRandomGenerator(paramRandomEngine);
    setNandP(paramInt, paramDouble);
  }
  
  public double cdf(int paramInt)
  {
    return Probability.binomial(paramInt, this.n, this.p);
  }
  
  private double cdfSlow(int paramInt)
  {
    if (paramInt < 0) {
      throw new IllegalArgumentException();
    }
    double d = 0.0D;
    for (int i = 0; i <= paramInt; i++) {
      d += pdf(i);
    }
    return d;
  }
  
  protected int generateBinomial(int paramInt, double paramDouble)
  {
    int j;
    double d1;
    if ((paramInt != this.n_last) || (paramDouble != this.p_last))
    {
      this.n_last = paramInt;
      this.p_last = paramDouble;
      this.par = Math.min(paramDouble, 1.0D - paramDouble);
      this.q = (1.0D - this.par);
      this.np = (paramInt * this.par);
      if (this.np <= 0.0D) {
        return -1;
      }
      double d2 = this.np + this.par;
      this.m = ((int)d2);
      if (this.np < 10.0D)
      {
        this.p0 = Math.exp(paramInt * Math.log(this.q));
        int i = (int)(this.np + 10.0D * Math.sqrt(this.np * this.q));
        this.b = Math.min(paramInt, i);
      }
      else
      {
        this.rc = ((paramInt + 1.0D) * (this.pq = this.par / this.q));
        this.ss = (this.np * this.q);
        j = (int)(2.195D * Math.sqrt(this.ss) - 4.6D * this.q);
        this.xm = (this.m + 0.5D);
        this.xl = (this.m - j);
        this.xr = (this.m + j + 1L);
        d1 = (d2 - this.xl) / (d2 - this.xl * this.par);
        this.ll = (d1 * (1.0D + 0.5D * d1));
        d1 = (this.xr - d2) / (this.xr * this.q);
        this.lr = (d1 * (1.0D + 0.5D * d1));
        this.c = (0.134D + 20.5D / (15.300000000000001D + this.m));
        this.p1 = (j + 0.5D);
        this.p2 = (this.p1 * (1.0D + this.c + this.c));
        this.p3 = (this.p2 + this.c / this.ll);
        this.p4 = (this.p3 + this.c / this.lr);
      }
    }
    int k;
    double d3;
    if (this.np < 10.0D)
    {
      k = 0;
      double d8 = this.p0;
      d3 = this.randomGenerator.raw();
      while (d3 > d8)
      {
        k++;
        if (k > this.b)
        {
          d3 = this.randomGenerator.raw();
          k = 0;
          d8 = this.p0;
        }
        else
        {
          d3 -= d8;
          d8 = (paramInt - k + 1) * this.par * d8 / (k * this.q);
        }
      }
      return paramDouble > 0.5D ? paramInt - k : k;
    }
    for (;;)
    {
      double d4 = this.randomGenerator.raw();
      if ((d3 = this.randomGenerator.raw() * this.p4) <= this.p1)
      {
        k = (int)(this.xm - d3 + this.p1 * d4);
        return paramDouble > 0.5D ? paramInt - k : k;
      }
      double d5;
      if (d3 <= this.p2)
      {
        d5 = this.xl + (d3 - this.p1) / this.c;
        if ((d4 = d4 * this.c + 1.0D - Math.abs(this.xm - d5) / this.p1) >= 1.0D) {
          continue;
        }
        k = (int)d5;
      }
      else if (d3 <= this.p3)
      {
        if ((d5 = this.xl + Math.log(d4) / this.ll) < 0.0D) {
          continue;
        }
        k = (int)d5;
        d4 *= (d3 - this.p2) * this.ll;
      }
      else
      {
        if ((k = (int)(this.xr - Math.log(d4) / this.lr)) > paramInt) {
          continue;
        }
        d4 *= (d3 - this.p3) * this.lr;
      }
      int i1;
      if (((i1 = Math.abs(k - this.m)) <= 20) || (i1 + i1 + 2L >= this.ss))
      {
        d1 = 1.0D;
        if (this.m < k)
        {
          j = this.m;
          do
          {
            if (j >= k) {
              break;
            }
          } while (d1 *= (this.rc / ++j - this.pq) >= d4);
        }
        else
        {
          j = k;
          while (j < this.m) {
            if (d4 *= (this.rc / ++j - this.pq) > d1) {
              break;
            }
          }
        }
        if (d4 <= d1) {
          break;
        }
      }
      else
      {
        d4 = Math.log(d4);
        double d6 = -i1 * i1 / (this.ss + this.ss);
        double d7 = i1 / this.ss * ((i1 * (i1 * 0.3333333333333333D + 0.625D) + 0.1666666666666667D) / this.ss + 0.5D);
        if (d4 > d6 - d7) {
          if (d4 <= d6 + d7)
          {
            if ((paramInt != this.n_prev) || (this.par != this.p_prev))
            {
              this.n_prev = paramInt;
              this.p_prev = this.par;
              this.nm = (paramInt - this.m + 1);
              this.ch = (this.xm * Math.log((this.m + 1.0D) / (this.pq * this.nm)) + Arithmetic.stirlingCorrection(this.m + 1) + Arithmetic.stirlingCorrection(this.nm));
            }
            int i2 = paramInt - k + 1;
            if (d4 <= this.ch + (paramInt + 1.0D) * Math.log(this.nm / i2) + (k + 0.5D) * Math.log(i2 * this.pq / (k + 1.0D)) - Arithmetic.stirlingCorrection(k + 1) - Arithmetic.stirlingCorrection(i2)) {
              break;
            }
          }
        }
      }
    }
    return paramDouble > 0.5D ? paramInt - k : k;
  }
  
  public int nextInt()
  {
    return generateBinomial(this.n, this.p);
  }
  
  public int nextInt(int paramInt, double paramDouble)
  {
    if (paramInt * Math.min(paramDouble, 1.0D - paramDouble) <= 0.0D) {
      throw new IllegalArgumentException();
    }
    return generateBinomial(paramInt, paramDouble);
  }
  
  public double pdf(int paramInt)
  {
    if (paramInt < 0) {
      throw new IllegalArgumentException();
    }
    int i = this.n - paramInt;
    return Math.exp(this.log_n - Arithmetic.logFactorial(paramInt) - Arithmetic.logFactorial(i) + this.log_p * paramInt + this.log_q * i);
  }
  
  public void setNandP(int paramInt, double paramDouble)
  {
    if (paramInt * Math.min(paramDouble, 1.0D - paramDouble) <= 0.0D) {
      throw new IllegalArgumentException();
    }
    this.n = paramInt;
    this.p = paramDouble;
    this.log_p = Math.log(paramDouble);
    this.log_q = Math.log(1.0D - paramDouble);
    this.log_n = Arithmetic.logFactorial(paramInt);
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
 * Qualified Name:     cern.jet.random.Binomial
 * JD-Core Version:    0.7.0.1
 */