package cern.jet.random;

import cern.jet.random.engine.RandomEngine;

public class Zeta
  extends AbstractDiscreteDistribution
{
  protected double ro;
  protected double pk;
  protected double c;
  protected double d;
  protected double ro_prev = -1.0D;
  protected double pk_prev = -1.0D;
  protected double maxlongint = 9.223372036854776E+018D;
  protected static Zeta shared = new Zeta(1.0D, 1.0D, makeDefaultGenerator());
  
  public Zeta(double paramDouble1, double paramDouble2, RandomEngine paramRandomEngine)
  {
    setRandomGenerator(paramRandomEngine);
    setState(paramDouble1, paramDouble2);
  }
  
  protected long generateZeta(double paramDouble1, double paramDouble2, RandomEngine paramRandomEngine)
  {
    if ((paramDouble1 != this.ro_prev) || (paramDouble2 != this.pk_prev))
    {
      this.ro_prev = paramDouble1;
      this.pk_prev = paramDouble2;
      if (paramDouble1 < paramDouble2)
      {
        this.c = (paramDouble2 - 0.5D);
        this.d = 0.0D;
      }
      else
      {
        this.c = (paramDouble1 - 0.5D);
        this.d = ((1.0D + paramDouble1) * Math.log((1.0D + paramDouble2) / (1.0D + paramDouble1)));
      }
    }
    double d4;
    long l;
    double d3;
    do
    {
      double d2;
      do
      {
        double d1 = paramRandomEngine.raw();
        d2 = paramRandomEngine.raw();
        d4 = (this.c + 0.5D) * Math.exp(-Math.log(d1) / paramDouble1) - this.c;
      } while ((d4 <= 0.5D) || (d4 >= this.maxlongint));
      l = (int)(d4 + 0.5D);
      d3 = -Math.log(d2);
    } while (d3 < (1.0D + paramDouble1) * Math.log((l + paramDouble2) / (d4 + this.c)) - this.d);
    return l;
  }
  
  public int nextInt()
  {
    return (int)generateZeta(this.ro, this.pk, this.randomGenerator);
  }
  
  public void setState(double paramDouble1, double paramDouble2)
  {
    this.ro = paramDouble1;
    this.pk = paramDouble2;
  }
  
  public static int staticNextInt(double paramDouble1, double paramDouble2)
  {
    synchronized (shared)
    {
      shared.setState(paramDouble1, paramDouble2);
      return shared.nextInt();
    }
  }
  
  public String toString()
  {
    return getClass().getName() + "(" + this.ro + "," + this.pk + ")";
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
 * Qualified Name:     cern.jet.random.Zeta
 * JD-Core Version:    0.7.0.1
 */