package cern.colt.matrix.impl;

import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;
import java.io.PrintStream;

class QRTest
{
  public static void main(String[] paramArrayOfString)
  {
    DoubleFactory2D localDoubleFactory2D = DoubleFactory2D.dense;
    DoubleMatrix2D localDoubleMatrix2D1 = localDoubleFactory2D.make(8, 2);
    DoubleMatrix2D localDoubleMatrix2D2 = localDoubleFactory2D.make(8, 1);
    localDoubleMatrix2D1.set(0, 0, 1.0D);
    localDoubleMatrix2D1.set(1, 0, 1.0D);
    localDoubleMatrix2D1.set(2, 0, 1.0D);
    localDoubleMatrix2D1.set(3, 0, 1.0D);
    localDoubleMatrix2D1.set(4, 0, 1.0D);
    localDoubleMatrix2D1.set(5, 0, 1.0D);
    localDoubleMatrix2D1.set(6, 0, 1.0D);
    localDoubleMatrix2D1.set(7, 0, 1.0D);
    localDoubleMatrix2D1.set(0, 1, 80.0D);
    localDoubleMatrix2D1.set(1, 1, 220.0D);
    localDoubleMatrix2D1.set(2, 1, 140.0D);
    localDoubleMatrix2D1.set(3, 1, 120.0D);
    localDoubleMatrix2D1.set(4, 1, 180.0D);
    localDoubleMatrix2D1.set(5, 1, 100.0D);
    localDoubleMatrix2D1.set(6, 1, 200.0D);
    localDoubleMatrix2D1.set(7, 1, 160.0D);
    localDoubleMatrix2D2.set(0, 0, 0.6D);
    localDoubleMatrix2D2.set(1, 0, 6.7D);
    localDoubleMatrix2D2.set(2, 0, 5.3D);
    localDoubleMatrix2D2.set(3, 0, 4.0D);
    localDoubleMatrix2D2.set(4, 0, 6.55D);
    localDoubleMatrix2D2.set(5, 0, 2.15D);
    localDoubleMatrix2D2.set(6, 0, 6.6D);
    localDoubleMatrix2D2.set(7, 0, 5.75D);
    Algebra localAlgebra = new Algebra();
    DoubleMatrix2D localDoubleMatrix2D3 = localAlgebra.solve(localDoubleMatrix2D1, localDoubleMatrix2D2);
    System.err.println(localDoubleMatrix2D1);
    System.err.println(localDoubleMatrix2D2);
    System.err.println(localDoubleMatrix2D3);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.impl.QRTest
 * JD-Core Version:    0.7.0.1
 */