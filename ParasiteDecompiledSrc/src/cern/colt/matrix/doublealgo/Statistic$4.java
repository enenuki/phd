package cern.colt.matrix.doublealgo;

import cern.colt.function.DoubleDoubleFunction;

class Statistic$4
  implements DoubleDoubleFunction
{
  private final Statistic.3 this$0;
  
  Statistic$4(Statistic.3 param3)
  {
    this.this$0 = param3;
  }
  
  public final double apply(double paramDouble1, double paramDouble2)
  {
    return Math.abs(paramDouble1 - paramDouble2) / Math.abs(paramDouble1 + paramDouble2);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.doublealgo.Statistic.4
 * JD-Core Version:    0.7.0.1
 */