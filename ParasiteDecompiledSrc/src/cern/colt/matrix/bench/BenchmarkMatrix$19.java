package cern.colt.matrix.bench;

import cern.colt.function.Double9Function;

class BenchmarkMatrix$19
  implements Double9Function
{
  private final BenchmarkMatrix.18 this$0;
  
  BenchmarkMatrix$19(BenchmarkMatrix.18 param18)
  {
    this.this$0 = param18;
  }
  
  public final double apply(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9)
  {
    return this.this$0.alpha * paramDouble5 + this.this$0.beta * (paramDouble2 + paramDouble4 + paramDouble6 + paramDouble8);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.bench.BenchmarkMatrix.19
 * JD-Core Version:    0.7.0.1
 */