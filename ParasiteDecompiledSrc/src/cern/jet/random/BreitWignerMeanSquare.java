package cern.jet.random;

import cern.jet.random.engine.RandomEngine;

public class BreitWignerMeanSquare
  extends BreitWigner
{
  protected Uniform uniform;
  protected static BreitWigner shared = new BreitWignerMeanSquare(1.0D, 0.2D, 1.0D, makeDefaultGenerator());
  
  public BreitWignerMeanSquare(double paramDouble1, double paramDouble2, double paramDouble3, RandomEngine paramRandomEngine)
  {
    super(paramDouble1, paramDouble2, paramDouble3, paramRandomEngine);
    this.uniform = new Uniform(paramRandomEngine);
  }
  
  public Object clone()
  {
    BreitWignerMeanSquare localBreitWignerMeanSquare = (BreitWignerMeanSquare)super.clone();
    if (this.uniform != null) {
      localBreitWignerMeanSquare.uniform = new Uniform(localBreitWignerMeanSquare.randomGenerator);
    }
    return localBreitWignerMeanSquare;
  }
  
  public double nextDouble(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    if (paramDouble2 == 0.0D) {
      return paramDouble1;
    }
    if (paramDouble3 == (-1.0D / 0.0D))
    {
      d1 = Math.atan(-paramDouble1 / paramDouble2);
      d2 = this.uniform.nextDoubleFromTo(d1, 1.570796326794897D);
      d3 = paramDouble2 * Math.tan(d2);
      return Math.sqrt(paramDouble1 * paramDouble1 + paramDouble1 * d3);
    }
    double d1 = Math.max(0.0D, paramDouble1 - paramDouble3);
    double d2 = Math.atan((d1 * d1 - paramDouble1 * paramDouble1) / (paramDouble1 * paramDouble2));
    double d3 = Math.atan(((paramDouble1 + paramDouble3) * (paramDouble1 + paramDouble3) - paramDouble1 * paramDouble1) / (paramDouble1 * paramDouble2));
    double d4 = this.uniform.nextDoubleFromTo(d2, d3);
    double d5 = paramDouble2 * Math.tan(d4);
    return Math.sqrt(Math.max(0.0D, paramDouble1 * paramDouble1 + paramDouble1 * d5));
  }
  
  public static double staticNextDouble(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    synchronized (shared)
    {
      return shared.nextDouble(paramDouble1, paramDouble2, paramDouble3);
    }
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
 * Qualified Name:     cern.jet.random.BreitWignerMeanSquare
 * JD-Core Version:    0.7.0.1
 */