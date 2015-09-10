package cern.jet.math;

import cern.colt.function.DoubleFunction;

public final class Mult
  implements DoubleFunction
{
  public double multiplicator;
  
  protected Mult(double paramDouble)
  {
    this.multiplicator = paramDouble;
  }
  
  public final double apply(double paramDouble)
  {
    return paramDouble * this.multiplicator;
  }
  
  public static Mult div(double paramDouble)
  {
    return mult(1.0D / paramDouble);
  }
  
  public static Mult mult(double paramDouble)
  {
    return new Mult(paramDouble);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.jet.math.Mult
 * JD-Core Version:    0.7.0.1
 */