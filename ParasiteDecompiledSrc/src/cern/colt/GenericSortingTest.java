package cern.colt;

import cern.colt.function.IntComparator;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.doublealgo.Sorting;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.jet.random.Uniform;
import cern.jet.random.engine.MersenneTwister;
import java.io.PrintStream;

class GenericSortingTest
{
  public static void demo1()
  {
    int[] arrayOfInt = { 3, 2, 1 };
    double[] arrayOfDouble1 = { 3.0D, 2.0D, 1.0D };
    double[] arrayOfDouble2 = { 6.0D, 7.0D, 8.0D };
    Swapper local1 = new Swapper()
    {
      private final int[] val$x;
      private final double[] val$y;
      private final double[] val$z;
      
      public void swap(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        int i = this.val$x[paramAnonymousInt1];
        this.val$x[paramAnonymousInt1] = this.val$x[paramAnonymousInt2];
        this.val$x[paramAnonymousInt2] = i;
        double d1 = this.val$y[paramAnonymousInt1];
        this.val$y[paramAnonymousInt1] = this.val$y[paramAnonymousInt2];
        this.val$y[paramAnonymousInt2] = d1;
        double d2 = this.val$z[paramAnonymousInt1];
        this.val$z[paramAnonymousInt1] = this.val$z[paramAnonymousInt2];
        this.val$z[paramAnonymousInt2] = d2;
      }
    };
    IntComparator local2 = new IntComparator()
    {
      private final int[] val$x;
      
      public int compare(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        return this.val$x[paramAnonymousInt1] < this.val$x[paramAnonymousInt2] ? -1 : this.val$x[paramAnonymousInt1] == this.val$x[paramAnonymousInt2] ? 0 : 1;
      }
    };
    System.out.println("before:");
    System.out.println("X=" + Arrays.toString(arrayOfInt));
    System.out.println("Y=" + Arrays.toString(arrayOfDouble1));
    System.out.println("Z=" + Arrays.toString(arrayOfDouble2));
    int i = 0;
    int j = arrayOfInt.length;
    GenericSorting.quickSort(i, j, local2, local1);
    System.out.println("after:");
    System.out.println("X=" + Arrays.toString(arrayOfInt));
    System.out.println("Y=" + Arrays.toString(arrayOfDouble1));
    System.out.println("Z=" + Arrays.toString(arrayOfDouble2));
    System.out.println("\n\n");
  }
  
  public static void demo2()
  {
    int[] arrayOfInt = { 6, 7, 8, 9 };
    double[] arrayOfDouble1 = { 3.0D, 2.0D, 1.0D, 3.0D };
    double[] arrayOfDouble2 = { 5.0D, 4.0D, 4.0D, 1.0D };
    Swapper local3 = new Swapper()
    {
      private final int[] val$x;
      private final double[] val$y;
      private final double[] val$z;
      
      public void swap(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        int i = this.val$x[paramAnonymousInt1];
        this.val$x[paramAnonymousInt1] = this.val$x[paramAnonymousInt2];
        this.val$x[paramAnonymousInt2] = i;
        double d1 = this.val$y[paramAnonymousInt1];
        this.val$y[paramAnonymousInt1] = this.val$y[paramAnonymousInt2];
        this.val$y[paramAnonymousInt2] = d1;
        double d2 = this.val$z[paramAnonymousInt1];
        this.val$z[paramAnonymousInt1] = this.val$z[paramAnonymousInt2];
        this.val$z[paramAnonymousInt2] = d2;
      }
    };
    IntComparator local4 = new IntComparator()
    {
      private final double[] val$y;
      private final double[] val$z;
      
      public int compare(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        if (this.val$y[paramAnonymousInt1] == this.val$y[paramAnonymousInt2]) {
          return this.val$z[paramAnonymousInt1] < this.val$z[paramAnonymousInt2] ? -1 : this.val$z[paramAnonymousInt1] == this.val$z[paramAnonymousInt2] ? 0 : 1;
        }
        return this.val$y[paramAnonymousInt1] < this.val$y[paramAnonymousInt2] ? -1 : 1;
      }
    };
    System.out.println("before:");
    System.out.println("X=" + Arrays.toString(arrayOfInt));
    System.out.println("Y=" + Arrays.toString(arrayOfDouble1));
    System.out.println("Z=" + Arrays.toString(arrayOfDouble2));
    int i = 0;
    int j = arrayOfInt.length;
    GenericSorting.quickSort(i, j, local4, local3);
    System.out.println("after:");
    System.out.println("X=" + Arrays.toString(arrayOfInt));
    System.out.println("Y=" + Arrays.toString(arrayOfDouble1));
    System.out.println("Z=" + Arrays.toString(arrayOfDouble2));
    System.out.println("\n\n");
  }
  
  public static void testRandomly(int paramInt)
  {
    MersenneTwister localMersenneTwister = new MersenneTwister();
    Uniform localUniform = new Uniform(localMersenneTwister);
    for (int i = 0; i < paramInt; i++)
    {
      int j = 50;
      int k = 2 * j;
      int m = localUniform.nextIntFromTo(1, j);
      int n;
      int i1;
      if (m == 0)
      {
        n = 0;
        i1 = -1;
      }
      else
      {
        n = localUniform.nextIntFromTo(0, m - 1);
        i1 = localUniform.nextIntFromTo(Math.min(n, m - 1), m - 1);
      }
      DenseDoubleMatrix2D localDenseDoubleMatrix2D = new DenseDoubleMatrix2D(m, m);
      DoubleMatrix2D localDoubleMatrix2D1 = localDenseDoubleMatrix2D.viewPart(n, n, m - i1, m - i1);
      int i2 = localUniform.nextIntFromTo(m / 2, 2 * m);
      int i3 = localUniform.nextIntFromTo(i2, 2 * m);
      for (int i4 = 0; i4 < m; i4++) {
        for (int i5 = 0; i5 < m; i5++) {
          localDenseDoubleMatrix2D.set(i4, i5, localUniform.nextIntFromTo(i2, i3));
        }
      }
      DoubleMatrix2D localDoubleMatrix2D2 = localDenseDoubleMatrix2D.copy();
      DoubleMatrix2D localDoubleMatrix2D3 = localDoubleMatrix2D2.viewPart(n, n, m - i1, m - i1);
      int i6 = 0;
      DoubleMatrix2D localDoubleMatrix2D4 = Sorting.quickSort.sort(localDoubleMatrix2D1, i6);
      DoubleMatrix2D localDoubleMatrix2D5 = Sorting.mergeSort.sort(localDoubleMatrix2D3, i6);
      if (!localDoubleMatrix2D4.viewColumn(i6).equals(localDoubleMatrix2D5.viewColumn(i6))) {
        throw new InternalError();
      }
    }
    System.out.println("All tests passed. No bug detected.");
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.GenericSortingTest
 * JD-Core Version:    0.7.0.1
 */