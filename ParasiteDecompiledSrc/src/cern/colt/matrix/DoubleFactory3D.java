package cern.colt.matrix;

import cern.colt.PersistentObject;
import cern.colt.matrix.impl.DenseDoubleMatrix3D;
import cern.colt.matrix.impl.SparseDoubleMatrix3D;
import cern.jet.math.Functions;

public class DoubleFactory3D
  extends PersistentObject
{
  public static final DoubleFactory3D dense = new DoubleFactory3D();
  public static final DoubleFactory3D sparse = new DoubleFactory3D();
  
  public DoubleMatrix3D ascending(int paramInt1, int paramInt2, int paramInt3)
  {
    Functions localFunctions = Functions.functions;
    return descending(paramInt1, paramInt2, paramInt3).assign(Functions.chain(Functions.neg, Functions.minus(paramInt1 * paramInt2 * paramInt3)));
  }
  
  public DoubleMatrix3D descending(int paramInt1, int paramInt2, int paramInt3)
  {
    DoubleMatrix3D localDoubleMatrix3D = make(paramInt1, paramInt2, paramInt3);
    int i = 0;
    int j = paramInt1;
    j--;
    if (j >= 0)
    {
      int k = paramInt2;
      for (;;)
      {
        k--;
        if (k < 0) {
          break;
        }
        int m = paramInt3;
        for (;;)
        {
          m--;
          if (m < 0) {
            break;
          }
          localDoubleMatrix3D.setQuick(j, k, m, i++);
        }
      }
    }
    return localDoubleMatrix3D;
  }
  
  public DoubleMatrix3D make(double[][][] paramArrayOfDouble)
  {
    if (this == sparse) {
      return new SparseDoubleMatrix3D(paramArrayOfDouble);
    }
    return new DenseDoubleMatrix3D(paramArrayOfDouble);
  }
  
  public DoubleMatrix3D make(int paramInt1, int paramInt2, int paramInt3)
  {
    if (this == sparse) {
      return new SparseDoubleMatrix3D(paramInt1, paramInt2, paramInt3);
    }
    return new DenseDoubleMatrix3D(paramInt1, paramInt2, paramInt3);
  }
  
  public DoubleMatrix3D make(int paramInt1, int paramInt2, int paramInt3, double paramDouble)
  {
    return make(paramInt1, paramInt2, paramInt3).assign(paramDouble);
  }
  
  public DoubleMatrix3D random(int paramInt1, int paramInt2, int paramInt3)
  {
    return make(paramInt1, paramInt2, paramInt3).assign(Functions.random());
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.DoubleFactory3D
 * JD-Core Version:    0.7.0.1
 */