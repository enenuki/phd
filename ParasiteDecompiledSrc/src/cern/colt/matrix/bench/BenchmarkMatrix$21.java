package cern.colt.matrix.bench;

import cern.colt.function.Double9Function;

class BenchmarkMatrix$21
  implements Double9Function
{
  private final BenchmarkMatrix.20 this$0;
  
  BenchmarkMatrix$21(BenchmarkMatrix.20 param20)
  {
    this.this$0 = param20;
  }
  
  public final double apply(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9)
  {
    return this.this$0.alpha * paramDouble5 + this.this$0.beta * (paramDouble1 + paramDouble4 + paramDouble7 + paramDouble2 + paramDouble8 + paramDouble3 + paramDouble6 + paramDouble9);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.bench.BenchmarkMatrix.21
 * JD-Core Version:    0.7.0.1
 */