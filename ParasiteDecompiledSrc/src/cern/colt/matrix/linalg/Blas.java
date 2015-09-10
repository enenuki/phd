package cern.colt.matrix.linalg;

import cern.colt.function.DoubleDoubleFunction;
import cern.colt.function.DoubleFunction;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;

public abstract interface Blas
{
  public abstract void assign(DoubleMatrix2D paramDoubleMatrix2D, DoubleFunction paramDoubleFunction);
  
  public abstract void assign(DoubleMatrix2D paramDoubleMatrix2D1, DoubleMatrix2D paramDoubleMatrix2D2, DoubleDoubleFunction paramDoubleDoubleFunction);
  
  public abstract double dasum(DoubleMatrix1D paramDoubleMatrix1D);
  
  public abstract void daxpy(double paramDouble, DoubleMatrix1D paramDoubleMatrix1D1, DoubleMatrix1D paramDoubleMatrix1D2);
  
  public abstract void daxpy(double paramDouble, DoubleMatrix2D paramDoubleMatrix2D1, DoubleMatrix2D paramDoubleMatrix2D2);
  
  public abstract void dcopy(DoubleMatrix1D paramDoubleMatrix1D1, DoubleMatrix1D paramDoubleMatrix1D2);
  
  public abstract void dcopy(DoubleMatrix2D paramDoubleMatrix2D1, DoubleMatrix2D paramDoubleMatrix2D2);
  
  public abstract double ddot(DoubleMatrix1D paramDoubleMatrix1D1, DoubleMatrix1D paramDoubleMatrix1D2);
  
  public abstract void dgemm(boolean paramBoolean1, boolean paramBoolean2, double paramDouble1, DoubleMatrix2D paramDoubleMatrix2D1, DoubleMatrix2D paramDoubleMatrix2D2, double paramDouble2, DoubleMatrix2D paramDoubleMatrix2D3);
  
  public abstract void dgemv(boolean paramBoolean, double paramDouble1, DoubleMatrix2D paramDoubleMatrix2D, DoubleMatrix1D paramDoubleMatrix1D1, double paramDouble2, DoubleMatrix1D paramDoubleMatrix1D2);
  
  public abstract void dger(double paramDouble, DoubleMatrix1D paramDoubleMatrix1D1, DoubleMatrix1D paramDoubleMatrix1D2, DoubleMatrix2D paramDoubleMatrix2D);
  
  public abstract double dnrm2(DoubleMatrix1D paramDoubleMatrix1D);
  
  public abstract void drot(DoubleMatrix1D paramDoubleMatrix1D1, DoubleMatrix1D paramDoubleMatrix1D2, double paramDouble1, double paramDouble2);
  
  public abstract void drotg(double paramDouble1, double paramDouble2, double[] paramArrayOfDouble);
  
  public abstract void dscal(double paramDouble, DoubleMatrix1D paramDoubleMatrix1D);
  
  public abstract void dscal(double paramDouble, DoubleMatrix2D paramDoubleMatrix2D);
  
  public abstract void dswap(DoubleMatrix1D paramDoubleMatrix1D1, DoubleMatrix1D paramDoubleMatrix1D2);
  
  public abstract void dswap(DoubleMatrix2D paramDoubleMatrix2D1, DoubleMatrix2D paramDoubleMatrix2D2);
  
  public abstract void dsymv(boolean paramBoolean, double paramDouble1, DoubleMatrix2D paramDoubleMatrix2D, DoubleMatrix1D paramDoubleMatrix1D1, double paramDouble2, DoubleMatrix1D paramDoubleMatrix1D2);
  
  public abstract void dtrmv(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, DoubleMatrix2D paramDoubleMatrix2D, DoubleMatrix1D paramDoubleMatrix1D);
  
  public abstract int idamax(DoubleMatrix1D paramDoubleMatrix1D);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.linalg.Blas
 * JD-Core Version:    0.7.0.1
 */