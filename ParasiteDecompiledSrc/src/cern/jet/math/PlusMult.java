package cern.jet.math;

import cern.colt.function.DoubleDoubleFunction;

public final class PlusMult
  implements DoubleDoubleFunction
{
  public double multiplicator;
  
  protected PlusMult(double paramDouble)
  {
    this.multiplicator = paramDouble;
  }
  
  public final double apply(double paramDouble1, double paramDouble2)
  {
    return paramDouble1 + paramDouble2 * this.multiplicator;
  }
  
  public static PlusMult minusDiv(double paramDouble)
  {
    return new PlusMult(-1.0D / paramDouble);
  }
  
  public static PlusMult minusMult(double paramDouble)
  {
    return new PlusMult(-paramDouble);
  }
  
  public static PlusMult plusDiv(double paramDouble)
  {
    return new PlusMult(1.0D / paramDouble);
  }
  
  public static PlusMult plusMult(double paramDouble)
  {
    return new PlusMult(paramDouble);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.jet.math.PlusMult
 * JD-Core Version:    0.7.0.1
 */