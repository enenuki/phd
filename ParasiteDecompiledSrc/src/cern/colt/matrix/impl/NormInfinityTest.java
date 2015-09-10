package cern.colt.matrix.impl;

import cern.colt.matrix.DoubleFactory1D;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.linalg.Algebra;
import java.io.PrintStream;

class NormInfinityTest
{
  public static void main(String[] paramArrayOfString)
  {
    DoubleMatrix1D localDoubleMatrix1D1 = DoubleFactory1D.dense.make(new double[] { 1.0D, 2.0D });
    DoubleMatrix1D localDoubleMatrix1D2 = DoubleFactory1D.dense.make(new double[] { 1.0D, -2.0D });
    DoubleMatrix1D localDoubleMatrix1D3 = DoubleFactory1D.dense.make(new double[] { -1.0D, -2.0D });
    System.out.println(Algebra.DEFAULT.normInfinity(localDoubleMatrix1D1));
    System.out.println(Algebra.DEFAULT.normInfinity(localDoubleMatrix1D2));
    System.out.println(Algebra.DEFAULT.normInfinity(localDoubleMatrix1D3));
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.impl.NormInfinityTest
 * JD-Core Version:    0.7.0.1
 */