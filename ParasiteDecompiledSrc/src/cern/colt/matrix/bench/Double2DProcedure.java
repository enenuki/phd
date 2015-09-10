package cern.colt.matrix.bench;

import cern.colt.matrix.DoubleMatrix2D;

abstract class Double2DProcedure
  implements TimerProcedure
{
  public DoubleMatrix2D A;
  public DoubleMatrix2D B;
  public DoubleMatrix2D C;
  public DoubleMatrix2D D;
  
  public double operations()
  {
    return this.A.rows() * this.A.columns() / 1000000.0D;
  }
  
  public void setParameters(DoubleMatrix2D paramDoubleMatrix2D1, DoubleMatrix2D paramDoubleMatrix2D2)
  {
    this.A = paramDoubleMatrix2D1;
    this.B = paramDoubleMatrix2D2;
    this.C = paramDoubleMatrix2D1.copy();
    this.D = paramDoubleMatrix2D2.copy();
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.bench.Double2DProcedure
 * JD-Core Version:    0.7.0.1
 */