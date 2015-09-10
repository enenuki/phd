package cern.colt.matrix.doublealgo;

import cern.colt.Swapper;
import cern.colt.function.IntComparator;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;

public class Partitioning
{
  public static void partition(DoubleMatrix2D paramDoubleMatrix2D, int[] paramArrayOfInt1, int paramInt1, int paramInt2, int paramInt3, double[] paramArrayOfDouble, int paramInt4, int paramInt5, int[] paramArrayOfInt2)
  {
    if ((paramInt1 < 0) || (paramInt2 >= paramDoubleMatrix2D.rows()) || (paramInt2 >= paramArrayOfInt1.length)) {
      throw new IllegalArgumentException();
    }
    if ((paramInt3 < 0) || (paramInt3 >= paramDoubleMatrix2D.columns())) {
      throw new IllegalArgumentException();
    }
    if ((paramInt4 < 0) || (paramInt5 >= paramArrayOfDouble.length)) {
      throw new IllegalArgumentException();
    }
    if (paramArrayOfInt2.length < paramArrayOfDouble.length) {
      throw new IllegalArgumentException();
    }
    int[] arrayOfInt = paramArrayOfInt1;
    Swapper local1 = new Swapper()
    {
      private final int[] val$g;
      
      public void swap(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        int i = this.val$g[paramAnonymousInt1];
        this.val$g[paramAnonymousInt1] = this.val$g[paramAnonymousInt2];
        this.val$g[paramAnonymousInt2] = i;
      }
    };
    DoubleMatrix1D localDoubleMatrix1D = paramDoubleMatrix2D.viewColumn(paramInt3);
    IntComparator local2 = new IntComparator()
    {
      private final double[] val$splitters;
      private final DoubleMatrix1D val$columnView;
      private final int[] val$g;
      
      public int compare(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        double d1 = this.val$splitters[paramAnonymousInt1];
        double d2 = this.val$columnView.getQuick(this.val$g[paramAnonymousInt2]);
        return d1 == d2 ? 0 : d1 < d2 ? -1 : 1;
      }
    };
    IntComparator local3 = new IntComparator()
    {
      private final DoubleMatrix1D val$columnView;
      private final int[] val$g;
      
      public int compare(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        double d1 = this.val$columnView.getQuick(this.val$g[paramAnonymousInt1]);
        double d2 = this.val$columnView.getQuick(this.val$g[paramAnonymousInt2]);
        return d1 == d2 ? 0 : d1 < d2 ? -1 : 1;
      }
    };
    IntComparator local4 = new IntComparator()
    {
      private final double[] val$splitters;
      
      public int compare(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        double d1 = this.val$splitters[paramAnonymousInt1];
        double d2 = this.val$splitters[paramAnonymousInt2];
        return d1 == d2 ? 0 : d1 < d2 ? -1 : 1;
      }
    };
    cern.colt.Partitioning.genericPartition(paramInt1, paramInt2, paramInt4, paramInt5, paramArrayOfInt2, local2, local3, local4, local1);
  }
  
  public static DoubleMatrix2D partition(DoubleMatrix2D paramDoubleMatrix2D, int paramInt, double[] paramArrayOfDouble, int[] paramArrayOfInt)
  {
    int i = 0;
    int j = paramDoubleMatrix2D.rows() - 1;
    int k = 0;
    int m = paramArrayOfDouble.length - 1;
    int[] arrayOfInt1 = new int[paramDoubleMatrix2D.rows()];
    int n = arrayOfInt1.length;
    for (;;)
    {
      n--;
      if (n < 0) {
        break;
      }
      arrayOfInt1[n] = n;
    }
    partition(paramDoubleMatrix2D, arrayOfInt1, i, j, paramInt, paramArrayOfDouble, k, m, paramArrayOfInt);
    int[] arrayOfInt2 = new int[paramDoubleMatrix2D.columns()];
    int i1 = arrayOfInt2.length;
    for (;;)
    {
      i1--;
      if (i1 < 0) {
        break;
      }
      arrayOfInt2[i1] = i1;
    }
    return paramDoubleMatrix2D.viewSelection(arrayOfInt1, arrayOfInt2);
  }
  
  private static void xPartitionOld(DoubleMatrix2D paramDoubleMatrix2D, DoubleMatrix1D paramDoubleMatrix1D, int paramInt1, int paramInt2, double[] paramArrayOfDouble, int paramInt3, int paramInt4, int[] paramArrayOfInt) {}
  
  private static int xPartitionOld(DoubleMatrix2D paramDoubleMatrix2D, DoubleMatrix1D paramDoubleMatrix1D, int paramInt1, int paramInt2, double paramDouble)
  {
    return 0;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.doublealgo.Partitioning
 * JD-Core Version:    0.7.0.1
 */