package cern.jet.random;

import cern.jet.random.engine.RandomEngine;
import cern.jet.stat.Probability;

public class Beta
  extends AbstractContinousDistribution
{
  protected double alpha;
  protected double beta;
  double PDF_CONST;
  double a_last = 0.0D;
  double b_last = 0.0D;
  double a_;
  double b_;
  double t;
  double fa;
  double fb;
  double p1;
  double p2;
  double c;
  double ml;
  double mu;
  double p_last = 0.0D;
  double q_last = 0.0D;
  double a;
  double b;
  double s;
  double m;
  double D;
  double Dl;
  double x1;
  double x2;
  double x4;
  double x5;
  double f1;
  double f2;
  double f4;
  double f5;
  double ll;
  double lr;
  double z2;
  double z4;
  double p3;
  double p4;
  protected static Beta shared = new Beta(10.0D, 10.0D, makeDefaultGenerator());
  
  public Beta(double paramDouble1, double paramDouble2, RandomEngine paramRandomEngine)
  {
    setRandomGenerator(paramRandomEngine);
    setState(paramDouble1, paramDouble2);
  }
  
  protected double b00(double paramDouble1, double paramDouble2, RandomEngine paramRandomEngine)
  {
    if ((paramDouble1 != this.a_last) || (paramDouble2 != this.b_last))
    {
      this.a_last = paramDouble1;
      this.b_last = paramDouble2;
      this.a_ = (paramDouble1 - 1.0D);
      this.b_ = (paramDouble2 - 1.0D);
      this.c = (paramDouble2 * this.b_ / (paramDouble1 * this.a_));
      this.t = (this.c == 1.0D ? 0.5D : (1.0D - Math.sqrt(this.c)) / (1.0D - this.c));
      this.fa = Math.exp(this.a_ * Math.log(this.t));
      this.fb = Math.exp(this.b_ * Math.log(1.0D - this.t));
      this.p1 = (this.t / paramDouble1);
      this.p2 = ((1.0D - this.t) / paramDouble2 + this.p1);
    }
    double d3;
    for (;;)
    {
      double d1;
      double d4;
      double d2;
      if ((d1 = paramRandomEngine.raw() * this.p2) <= this.p1)
      {
        d4 = Math.exp(Math.log(d1 / this.p1) / paramDouble1);
        d3 = this.t * d4;
        if ((d2 = paramRandomEngine.raw() * this.fb) > 1.0D - this.b_ * d3) {
          if ((d2 <= 1.0D + (this.fb - 1.0D) * d4) && (Math.log(d2) <= this.b_ * Math.log(1.0D - d3))) {
            break;
          }
        }
      }
      else
      {
        d4 = Math.exp(Math.log((d1 - this.p1) / (this.p2 - this.p1)) / paramDouble2);
        d3 = 1.0D - (1.0D - this.t) * d4;
        if ((d2 = paramRandomEngine.raw() * this.fa) > 1.0D - this.a_ * (1.0D - d3)) {
          if ((d2 <= 1.0D + (this.fa - 1.0D) * d4) && (Math.log(d2) <= this.a_ * Math.log(d3))) {
            break;
          }
        }
      }
    }
    return d3;
  }
  
  protected double b01(double paramDouble1, double paramDouble2, RandomEngine paramRandomEngine)
  {
    if ((paramDouble1 != this.a_last) || (paramDouble2 != this.b_last))
    {
      this.a_last = paramDouble1;
      this.b_last = paramDouble2;
      this.a_ = (paramDouble1 - 1.0D);
      this.b_ = (paramDouble2 - 1.0D);
      this.t = (this.a_ / (paramDouble1 - paramDouble2));
      this.fb = Math.exp((this.b_ - 1.0D) * Math.log(1.0D - this.t));
      this.fa = (paramDouble1 - (paramDouble1 + this.b_) * this.t);
      this.t -= (this.t - (1.0D - this.fa) * (1.0D - this.t) * this.fb / paramDouble2) / (1.0D - this.fa * this.fb);
      this.fa = Math.exp(this.a_ * Math.log(this.t));
      this.fb = Math.exp(this.b_ * Math.log(1.0D - this.t));
      if (this.b_ <= 1.0D)
      {
        this.ml = ((1.0D - this.fb) / this.t);
        this.mu = (this.b_ * this.t);
      }
      else
      {
        this.ml = this.b_;
        this.mu = (1.0D - this.fb);
      }
      this.p1 = (this.t / paramDouble1);
      this.p2 = (this.fb * (1.0D - this.t) / paramDouble2 + this.p1);
    }
    double d3;
    for (;;)
    {
      double d1;
      double d4;
      double d2;
      if ((d1 = paramRandomEngine.raw() * this.p2) <= this.p1)
      {
        d4 = Math.exp(Math.log(d1 / this.p1) / paramDouble1);
        d3 = this.t * d4;
        if ((d2 = paramRandomEngine.raw()) > 1.0D - this.ml * d3) {
          if ((d2 <= 1.0D - this.mu * d4) && (Math.log(d2) <= this.b_ * Math.log(1.0D - d3))) {
            break;
          }
        }
      }
      else
      {
        d4 = Math.exp(Math.log((d1 - this.p1) / (this.p2 - this.p1)) / paramDouble2);
        d3 = 1.0D - (1.0D - this.t) * d4;
        if ((d2 = paramRandomEngine.raw() * this.fa) > 1.0D - this.a_ * (1.0D - d3)) {
          if ((d2 <= 1.0D + (this.fa - 1.0D) * d4) && (Math.log(d2) <= this.a_ * Math.log(d3))) {
            break;
          }
        }
      }
    }
    return d3;
  }
  
  protected double b1prs(double paramDouble1, double paramDouble2, RandomEngine paramRandomEngine)
  {
    if ((paramDouble1 != this.p_last) || (paramDouble2 != this.q_last))
    {
      this.p_last = paramDouble1;
      this.q_last = paramDouble2;
      this.a = (paramDouble1 - 1.0D);
      this.b = (paramDouble2 - 1.0D);
      this.s = (this.a + this.b);
      this.m = (this.a / this.s);
      if ((this.a > 1.0D) || (this.b > 1.0D)) {
        this.D = Math.sqrt(this.m * (1.0D - this.m) / (this.s - 1.0D));
      }
      if (this.a <= 1.0D)
      {
        this.x2 = (this.Dl = this.m * 0.5D);
        this.x1 = (this.z2 = 0.0D);
        this.f1 = (this.ll = 0.0D);
      }
      else
      {
        this.x2 = (this.m - this.D);
        this.x1 = (this.x2 - this.D);
        this.z2 = (this.x2 * (1.0D - (1.0D - this.x2) / (this.s * this.D)));
        if ((this.x1 <= 0.0D) || ((this.s - 6.0D) * this.x2 - this.a + 3.0D > 0.0D))
        {
          this.x1 = this.z2;
          this.x2 = ((this.x1 + this.m) * 0.5D);
          this.Dl = (this.m - this.x2);
        }
        else
        {
          this.Dl = this.D;
        }
        this.f1 = f(this.x1, this.a, this.b, this.m);
        this.ll = (this.x1 * (1.0D - this.x1) / (this.s * (this.m - this.x1)));
      }
      this.f2 = f(this.x2, this.a, this.b, this.m);
      if (this.b <= 1.0D)
      {
        this.x4 = (1.0D - (this.D = (1.0D - this.m) * 0.5D));
        this.x5 = (this.z4 = 1.0D);
        this.f5 = (this.lr = 0.0D);
      }
      else
      {
        this.x4 = (this.m + this.D);
        this.x5 = (this.x4 + this.D);
        this.z4 = (this.x4 * (1.0D + (1.0D - this.x4) / (this.s * this.D)));
        if ((this.x5 >= 1.0D) || ((this.s - 6.0D) * this.x4 - this.a + 3.0D < 0.0D))
        {
          this.x5 = this.z4;
          this.x4 = ((this.m + this.x5) * 0.5D);
          this.D = (this.x4 - this.m);
        }
        this.f5 = f(this.x5, this.a, this.b, this.m);
        this.lr = (this.x5 * (1.0D - this.x5) / (this.s * (this.x5 - this.m)));
      }
      this.f4 = f(this.x4, this.a, this.b, this.m);
      this.p1 = (this.f2 * (this.Dl + this.Dl));
      this.p2 = (this.f4 * (this.D + this.D) + this.p1);
      this.p3 = (this.f1 * this.ll + this.p2);
      this.p4 = (this.f5 * this.lr + this.p3);
    }
    double d3;
    double d4;
    label1308:
    do
    {
      double d1;
      double d5;
      do
      {
        do
        {
          double d2;
          if ((d1 = paramRandomEngine.raw() * this.p4) <= this.p1)
          {
            if ((d3 = d1 / this.Dl - this.f2) <= 0.0D) {
              return this.m - d1 / this.f2;
            }
            if (d3 <= this.f1) {
              return this.x2 - d3 / this.f1 * this.Dl;
            }
            d2 = this.Dl * (d1 = paramRandomEngine.raw());
            d4 = this.x2 - d2;
            d5 = this.x2 + d2;
            if (d3 * (this.x2 - this.z2) <= this.f2 * (d4 - this.z2)) {
              return d4;
            }
            if ((d2 = this.f2 + this.f2 - d3) >= 1.0D) {
              break label1308;
            }
            if (d2 <= this.f2 + (1.0D - this.f2) * d1) {
              return d5;
            }
            if (d2 > f(d5, this.a, this.b, this.m)) {
              break label1308;
            }
            return d5;
          }
          if (d1 <= this.p2)
          {
            d1 -= this.p1;
            if ((d3 = d1 / this.D - this.f4) <= 0.0D) {
              return this.m + d1 / this.f4;
            }
            if (d3 <= this.f5) {
              return this.x4 + d3 / this.f5 * this.D;
            }
            d2 = this.D * (d1 = paramRandomEngine.raw());
            d4 = this.x4 + d2;
            d5 = this.x4 - d2;
            if (d3 * (this.z4 - this.x4) <= this.f4 * (this.z4 - d4)) {
              return d4;
            }
            if ((d2 = this.f4 + this.f4 - d3) >= 1.0D) {
              break label1308;
            }
            if (d2 <= this.f4 + (1.0D - this.f4) * d1) {
              return d5;
            }
            if (d2 > f(d5, this.a, this.b, this.m)) {
              break label1308;
            }
            return d5;
          }
          if (d1 > this.p3) {
            break;
          }
          d5 = Math.log(d1 = (d1 - this.p2) / (this.p3 - this.p2));
        } while ((d4 = this.x1 + this.ll * d5) <= 0.0D);
        d3 = paramRandomEngine.raw() * d1;
        if (d3 <= 1.0D + d5) {
          return d4;
        }
        d3 *= this.f1;
        break;
        d5 = Math.log(d1 = (d1 - this.p3) / (this.p4 - this.p3));
      } while ((d4 = this.x5 - this.lr * d5) >= 1.0D);
      d3 = paramRandomEngine.raw() * d1;
      if (d3 <= 1.0D + d5) {
        return d4;
      }
      d3 *= this.f5;
    } while (Math.log(d3) > this.a * Math.log(d4 / this.m) + this.b * Math.log((1.0D - d4) / (1.0D - this.m)));
    return d4;
  }
  
  public double cdf(double paramDouble)
  {
    return Probability.beta(this.alpha, this.beta, paramDouble);
  }
  
  private static double f(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
  {
    return Math.exp(paramDouble2 * Math.log(paramDouble1 / paramDouble4) + paramDouble3 * Math.log((1.0D - paramDouble1) / (1.0D - paramDouble4)));
  }
  
  public double nextDouble()
  {
    return nextDouble(this.alpha, this.beta);
  }
  
  public double nextDouble(double paramDouble1, double paramDouble2)
  {
    double d1 = paramDouble1;
    double d2 = paramDouble2;
    if (d1 > 1.0D)
    {
      if (d2 > 1.0D) {
        return b1prs(d1, d2, this.randomGenerator);
      }
      if (d2 < 1.0D) {
        return 1.0D - b01(d2, d1, this.randomGenerator);
      }
      if (d2 == 1.0D) {
        return Math.exp(Math.log(this.randomGenerator.raw()) / d1);
      }
    }
    if (d1 < 1.0D)
    {
      if (d2 > 1.0D) {
        return b01(d1, d2, this.randomGenerator);
      }
      if (d2 < 1.0D) {
        return b00(d1, d2, this.randomGenerator);
      }
      if (d2 == 1.0D) {
        return Math.exp(Math.log(this.randomGenerator.raw()) / d1);
      }
    }
    if (d1 == 1.0D)
    {
      if (d2 != 1.0D) {
        return 1.0D - Math.exp(Math.log(this.randomGenerator.raw()) / d2);
      }
      if (d2 == 1.0D) {
        return this.randomGenerator.raw();
      }
    }
    return 0.0D;
  }
  
  public double pdf(double paramDouble)
  {
    if ((paramDouble < 0.0D) || (paramDouble > 1.0D)) {
      return 0.0D;
    }
    return Math.exp(this.PDF_CONST) * Math.pow(paramDouble, this.alpha - 1.0D) * Math.pow(1.0D - paramDouble, this.beta - 1.0D);
  }
  
  public void setState(double paramDouble1, double paramDouble2)
  {
    this.alpha = paramDouble1;
    this.beta = paramDouble2;
    this.PDF_CONST = (Fun.logGamma(paramDouble1 + paramDouble2) - Fun.logGamma(paramDouble1) - Fun.logGamma(paramDouble2));
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
 * Qualified Name:     cern.jet.random.Beta
 * JD-Core Version:    0.7.0.1
 */