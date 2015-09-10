package cern.jet.random;

import cern.jet.math.Arithmetic;
import cern.jet.random.engine.RandomEngine;

public class HyperGeometric
  extends AbstractDiscreteDistribution
{
  protected int my_N;
  protected int my_s;
  protected int my_n;
  private int N_last = -1;
  private int M_last = -1;
  private int n_last = -1;
  private int N_Mn;
  private int m;
  private int mp;
  private int b;
  private double Mp;
  private double np;
  private double fm;
  private int k2;
  private int k4;
  private int k1;
  private int k5;
  private double dl;
  private double dr;
  private double r1;
  private double r2;
  private double r4;
  private double r5;
  private double ll;
  private double lr;
  private double c_pm;
  private double f1;
  private double f2;
  private double f4;
  private double f5;
  private double p1;
  private double p2;
  private double p3;
  private double p4;
  private double p5;
  private double p6;
  protected static HyperGeometric shared = new HyperGeometric(1, 1, 1, makeDefaultGenerator());
  
  public HyperGeometric(int paramInt1, int paramInt2, int paramInt3, RandomEngine paramRandomEngine)
  {
    setRandomGenerator(paramRandomEngine);
    setState(paramInt1, paramInt2, paramInt3);
  }
  
  private static double fc_lnpk(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return Arithmetic.logFactorial(paramInt1) + Arithmetic.logFactorial(paramInt3 - paramInt1) + Arithmetic.logFactorial(paramInt4 - paramInt1) + Arithmetic.logFactorial(paramInt2 + paramInt1);
  }
  
  protected int hmdu(int paramInt1, int paramInt2, int paramInt3, RandomEngine paramRandomEngine)
  {
    if ((paramInt1 != this.N_last) || (paramInt2 != this.M_last) || (paramInt3 != this.n_last))
    {
      this.N_last = paramInt1;
      this.M_last = paramInt2;
      this.n_last = paramInt3;
      this.Mp = (paramInt2 + 1);
      this.np = (paramInt3 + 1);
      this.N_Mn = (paramInt1 - paramInt2 - paramInt3);
      double d1 = this.Mp / (paramInt1 + 2.0D);
      double d2 = this.np * d1;
      if (((this.m = (int)d2) == d2) && (d1 == 0.5D)) {
        this.mp = (this.m--);
      } else {
        this.mp = (this.m + 1);
      }
      this.fm = Math.exp(Arithmetic.logFactorial(paramInt1 - paramInt2) - Arithmetic.logFactorial(this.N_Mn + this.m) - Arithmetic.logFactorial(paramInt3 - this.m) + Arithmetic.logFactorial(paramInt2) - Arithmetic.logFactorial(paramInt2 - this.m) - Arithmetic.logFactorial(this.m) - Arithmetic.logFactorial(paramInt1) + Arithmetic.logFactorial(paramInt1 - paramInt3) + Arithmetic.logFactorial(paramInt3));
      this.b = ((int)(d2 + 11.0D * Math.sqrt(d2 * (1.0D - d1) * (1.0D - paramInt3 / paramInt1) + 1.0D)));
      if (this.b > paramInt3) {
        this.b = paramInt3;
      }
    }
    for (;;)
    {
      double d5;
      if ((d5 = paramRandomEngine.raw() - this.fm) <= 0.0D) {
        return this.m;
      }
      double d4;
      double d3 = d4 = this.fm;
      for (int i = 1; i <= this.m; i++)
      {
        j = this.mp - i;
        d3 *= j / (this.np - j) * ((this.N_Mn + j) / (this.Mp - j));
        if (d5 -= d3 <= 0.0D) {
          return j - 1;
        }
        j = this.m + i;
        d4 *= (this.np - j) / j * ((this.Mp - j) / (this.N_Mn + j));
        if (d5 -= d4 <= 0.0D) {
          return j;
        }
      }
      for (int j = this.mp + this.m; j <= this.b; j++)
      {
        d4 *= (this.np - j) / j * ((this.Mp - j) / (this.N_Mn + j));
        if (d5 -= d4 <= 0.0D) {
          return j;
        }
      }
    }
  }
  
  protected int hprs(int paramInt1, int paramInt2, int paramInt3, RandomEngine paramRandomEngine)
  {
    double d5;
    if ((paramInt1 != this.N_last) || (paramInt2 != this.M_last) || (paramInt3 != this.n_last))
    {
      this.N_last = paramInt1;
      this.M_last = paramInt2;
      this.n_last = paramInt3;
      double d1 = paramInt2 + 1;
      double d2 = paramInt3 + 1;
      this.N_Mn = (paramInt1 - paramInt2 - paramInt3);
      double d3 = d1 / (paramInt1 + 2.0D);
      double d4 = d2 * d3;
      d5 = Math.sqrt(d4 * (1.0D - d3) * (1.0D - (paramInt3 + 2.0D) / (paramInt1 + 3.0D)) + 0.25D);
      this.m = ((int)d4);
      this.k2 = ((int)Math.ceil(d4 - 0.5D - d5));
      if (this.k2 >= this.m) {
        this.k2 = (this.m - 1);
      }
      this.k4 = ((int)(d4 - 0.5D + d5));
      this.k1 = (this.k2 + this.k2 - this.m + 1);
      this.k5 = (this.k4 + this.k4 - this.m);
      this.dl = (this.k2 - this.k1);
      this.dr = (this.k5 - this.k4);
      this.r1 = ((d2 / this.k1 - 1.0D) * (d1 - this.k1) / (this.N_Mn + this.k1));
      this.r2 = ((d2 / this.k2 - 1.0D) * (d1 - this.k2) / (this.N_Mn + this.k2));
      this.r4 = ((d2 / (this.k4 + 1) - 1.0D) * (paramInt2 - this.k4) / (this.N_Mn + this.k4 + 1));
      this.r5 = ((d2 / (this.k5 + 1) - 1.0D) * (paramInt2 - this.k5) / (this.N_Mn + this.k5 + 1));
      this.ll = Math.log(this.r1);
      this.lr = (-Math.log(this.r5));
      this.c_pm = fc_lnpk(this.m, this.N_Mn, paramInt2, paramInt3);
      this.f2 = Math.exp(this.c_pm - fc_lnpk(this.k2, this.N_Mn, paramInt2, paramInt3));
      this.f4 = Math.exp(this.c_pm - fc_lnpk(this.k4, this.N_Mn, paramInt2, paramInt3));
      this.f1 = Math.exp(this.c_pm - fc_lnpk(this.k1, this.N_Mn, paramInt2, paramInt3));
      this.f5 = Math.exp(this.c_pm - fc_lnpk(this.k5, this.N_Mn, paramInt2, paramInt3));
      this.p1 = (this.f2 * (this.dl + 1.0D));
      this.p2 = (this.f2 * this.dl + this.p1);
      this.p3 = (this.f4 * (this.dr + 1.0D) + this.p2);
      this.p4 = (this.f4 * this.dr + this.p3);
      this.p5 = (this.f1 / this.ll + this.p4);
      this.p6 = (this.f5 / this.lr + this.p5);
    }
    double d6;
    int j;
    label1262:
    do
    {
      int i;
      do
      {
        do
        {
          double d7;
          int k;
          if ((d5 = paramRandomEngine.raw() * this.p6) < this.p2)
          {
            if ((d7 = d5 - this.p1) < 0.0D) {
              return this.k2 + (int)(d5 / this.f2);
            }
            if ((d6 = d7 / this.dl) < this.f1) {
              return this.k1 + (int)(d7 / this.f1);
            }
            i = (int)(this.dl * paramRandomEngine.raw()) + 1;
            if (d6 <= this.f2 - i * (this.f2 - this.f2 / this.r2)) {
              return this.k2 - i;
            }
            if ((d7 = this.f2 + this.f2 - d6) < 1.0D)
            {
              k = this.k2 + i;
              if (d7 <= this.f2 + i * (1.0D - this.f2) / (this.dl + 1.0D)) {
                return k;
              }
              if (Math.log(d7) <= this.c_pm - fc_lnpk(k, this.N_Mn, paramInt2, paramInt3)) {
                return k;
              }
            }
            j = this.k2 - i;
            break label1262;
          }
          if (d5 < this.p4)
          {
            if ((d7 = d5 - this.p3) < 0.0D) {
              return this.k4 - (int)((d5 - this.p2) / this.f4);
            }
            if ((d6 = d7 / this.dr) < this.f5) {
              return this.k5 - (int)(d7 / this.f5);
            }
            i = (int)(this.dr * paramRandomEngine.raw()) + 1;
            if (d6 <= this.f4 - i * (this.f4 - this.f4 * this.r4)) {
              return this.k4 + i;
            }
            if ((d7 = this.f4 + this.f4 - d6) < 1.0D)
            {
              k = this.k4 - i;
              if (d7 <= this.f4 + i * (1.0D - this.f4) / this.dr) {
                return k;
              }
              if (Math.log(d7) <= this.c_pm - fc_lnpk(k, this.N_Mn, paramInt2, paramInt3)) {
                return k;
              }
            }
            j = this.k4 + i;
            break label1262;
          }
          d6 = paramRandomEngine.raw();
          if (d5 >= this.p5) {
            break;
          }
          i = (int)(1.0D - Math.log(d6) / this.ll);
        } while ((j = this.k1 - i) < 0);
        d6 *= (d5 - this.p4) * this.ll;
        if (d6 > this.f1 - i * (this.f1 - this.f1 / this.r1)) {
          break;
        }
        return j;
        i = (int)(1.0D - Math.log(d6) / this.lr);
      } while ((j = this.k5 + i) > paramInt3);
      d6 *= (d5 - this.p5) * this.lr;
      if (d6 <= this.f5 - i * (this.f5 - this.f5 * this.r5)) {
        return j;
      }
    } while (Math.log(d6) > this.c_pm - fc_lnpk(j, this.N_Mn, paramInt2, paramInt3));
    return j;
  }
  
  public int nextInt()
  {
    return nextInt(this.my_N, this.my_s, this.my_n, this.randomGenerator);
  }
  
  public int nextInt(int paramInt1, int paramInt2, int paramInt3)
  {
    return nextInt(paramInt1, paramInt2, paramInt3, this.randomGenerator);
  }
  
  protected int nextInt(int paramInt1, int paramInt2, int paramInt3, RandomEngine paramRandomEngine)
  {
    int i = paramInt1 / 2;
    int j = paramInt3 <= i ? paramInt3 : paramInt1 - paramInt3;
    int k = paramInt2 <= i ? paramInt2 : paramInt1 - paramInt2;
    int n;
    if (paramInt3 * paramInt2 / paramInt1 < 10) {
      n = j <= k ? hmdu(paramInt1, k, j, paramRandomEngine) : hmdu(paramInt1, j, k, paramRandomEngine);
    } else {
      n = j <= k ? hprs(paramInt1, k, j, paramRandomEngine) : hprs(paramInt1, j, k, paramRandomEngine);
    }
    if (paramInt3 <= i) {
      return paramInt2 <= i ? n : paramInt3 - n;
    }
    return paramInt2 <= i ? paramInt2 - n : paramInt3 - paramInt1 + paramInt2 + n;
  }
  
  public double pdf(int paramInt)
  {
    return Arithmetic.binomial(this.my_s, paramInt) * Arithmetic.binomial(this.my_N - this.my_s, this.my_n - paramInt) / Arithmetic.binomial(this.my_N, this.my_n);
  }
  
  public void setState(int paramInt1, int paramInt2, int paramInt3)
  {
    this.my_N = paramInt1;
    this.my_s = paramInt2;
    this.my_n = paramInt3;
  }
  
  public static double staticNextInt(int paramInt1, int paramInt2, int paramInt3)
  {
    synchronized (shared)
    {
      return shared.nextInt(paramInt1, paramInt2, paramInt3);
    }
  }
  
  public String toString()
  {
    return getClass().getName() + "(" + this.my_N + "," + this.my_s + "," + this.my_n + ")";
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
 * Qualified Name:     cern.jet.random.HyperGeometric
 * JD-Core Version:    0.7.0.1
 */