package cern.colt.matrix.doublealgo;

import cern.colt.GenericSorting;
import cern.colt.PersistentObject;
import cern.colt.Swapper;
import cern.colt.Timer;
import cern.colt.function.DoubleComparator;
import cern.colt.function.IntComparator;
import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleFactory3D;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.DoubleMatrix3D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import cern.jet.math.Functions;
import cern.jet.random.engine.DRand;
import hep.aida.bin.BinFunction1D;
import hep.aida.bin.BinFunctions1D;
import hep.aida.bin.DynamicBin1D;
import java.io.PrintStream;

public class Sorting
  extends PersistentObject
{
  public static final Sorting quickSort = new Sorting();
  public static final Sorting mergeSort = new Sorting()
  {
    protected void runSort(int[] paramAnonymousArrayOfInt, int paramAnonymousInt1, int paramAnonymousInt2, IntComparator paramAnonymousIntComparator)
    {
      cern.colt.Sorting.mergeSort(paramAnonymousArrayOfInt, paramAnonymousInt1, paramAnonymousInt2, paramAnonymousIntComparator);
    }
    
    protected void runSort(int paramAnonymousInt1, int paramAnonymousInt2, IntComparator paramAnonymousIntComparator, Swapper paramAnonymousSwapper)
    {
      GenericSorting.mergeSort(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousIntComparator, paramAnonymousSwapper);
    }
  };
  
  private static final int compareNaN(double paramDouble1, double paramDouble2)
  {
    if (paramDouble1 != paramDouble1)
    {
      if (paramDouble2 != paramDouble2) {
        return 0;
      }
      return 1;
    }
    return -1;
  }
  
  protected void runSort(int[] paramArrayOfInt, int paramInt1, int paramInt2, IntComparator paramIntComparator)
  {
    cern.colt.Sorting.quickSort(paramArrayOfInt, paramInt1, paramInt2, paramIntComparator);
  }
  
  protected void runSort(int paramInt1, int paramInt2, IntComparator paramIntComparator, Swapper paramSwapper)
  {
    GenericSorting.quickSort(paramInt1, paramInt2, paramIntComparator, paramSwapper);
  }
  
  public DoubleMatrix1D sort(DoubleMatrix1D paramDoubleMatrix1D)
  {
    int[] arrayOfInt = new int[paramDoubleMatrix1D.size()];
    int i = arrayOfInt.length;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      arrayOfInt[i] = i;
    }
    IntComparator local2 = new IntComparator()
    {
      private final DoubleMatrix1D val$vector;
      
      public int compare(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        double d1 = this.val$vector.getQuick(paramAnonymousInt1);
        double d2 = this.val$vector.getQuick(paramAnonymousInt2);
        if ((d1 != d1) || (d2 != d2)) {
          return Sorting.compareNaN(d1, d2);
        }
        return d1 == d2 ? 0 : d1 < d2 ? -1 : 1;
      }
    };
    runSort(arrayOfInt, 0, arrayOfInt.length, local2);
    return paramDoubleMatrix1D.viewSelection(arrayOfInt);
  }
  
  public DoubleMatrix1D sort(DoubleMatrix1D paramDoubleMatrix1D, DoubleComparator paramDoubleComparator)
  {
    int[] arrayOfInt = new int[paramDoubleMatrix1D.size()];
    int i = arrayOfInt.length;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      arrayOfInt[i] = i;
    }
    IntComparator local3 = new IntComparator()
    {
      private final DoubleComparator val$c;
      private final DoubleMatrix1D val$vector;
      
      public int compare(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        return this.val$c.compare(this.val$vector.getQuick(paramAnonymousInt1), this.val$vector.getQuick(paramAnonymousInt2));
      }
    };
    runSort(arrayOfInt, 0, arrayOfInt.length, local3);
    return paramDoubleMatrix1D.viewSelection(arrayOfInt);
  }
  
  public DoubleMatrix2D sort(DoubleMatrix2D paramDoubleMatrix2D, double[] paramArrayOfDouble)
  {
    int i = paramDoubleMatrix2D.rows();
    if (paramArrayOfDouble.length != i) {
      throw new IndexOutOfBoundsException("aggregates.length != matrix.rows()");
    }
    int[] arrayOfInt = new int[i];
    int j = i;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      arrayOfInt[j] = j;
    }
    IntComparator local4 = new IntComparator()
    {
      private final double[] val$aggregates;
      
      public int compare(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        double d1 = this.val$aggregates[paramAnonymousInt1];
        double d2 = this.val$aggregates[paramAnonymousInt2];
        if ((d1 != d1) || (d2 != d2)) {
          return Sorting.compareNaN(d1, d2);
        }
        return d1 == d2 ? 0 : d1 < d2 ? -1 : 1;
      }
    };
    Swapper local5 = new Swapper()
    {
      private final int[] val$indexes;
      private final double[] val$aggregates;
      
      public void swap(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        int i = this.val$indexes[paramAnonymousInt1];
        this.val$indexes[paramAnonymousInt1] = this.val$indexes[paramAnonymousInt2];
        this.val$indexes[paramAnonymousInt2] = i;
        double d = this.val$aggregates[paramAnonymousInt1];
        this.val$aggregates[paramAnonymousInt1] = this.val$aggregates[paramAnonymousInt2];
        this.val$aggregates[paramAnonymousInt2] = d;
      }
    };
    runSort(0, i, local4, local5);
    return paramDoubleMatrix2D.viewSelection(arrayOfInt, null);
  }
  
  public DoubleMatrix2D sort(DoubleMatrix2D paramDoubleMatrix2D, int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= paramDoubleMatrix2D.columns())) {
      throw new IndexOutOfBoundsException("column=" + paramInt + ", matrix=" + Formatter.shape(paramDoubleMatrix2D));
    }
    int[] arrayOfInt = new int[paramDoubleMatrix2D.rows()];
    int i = arrayOfInt.length;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      arrayOfInt[i] = i;
    }
    DoubleMatrix1D localDoubleMatrix1D = paramDoubleMatrix2D.viewColumn(paramInt);
    IntComparator local6 = new IntComparator()
    {
      private final DoubleMatrix1D val$col;
      
      public int compare(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        double d1 = this.val$col.getQuick(paramAnonymousInt1);
        double d2 = this.val$col.getQuick(paramAnonymousInt2);
        if ((d1 != d1) || (d2 != d2)) {
          return Sorting.compareNaN(d1, d2);
        }
        return d1 == d2 ? 0 : d1 < d2 ? -1 : 1;
      }
    };
    runSort(arrayOfInt, 0, arrayOfInt.length, local6);
    return paramDoubleMatrix2D.viewSelection(arrayOfInt, null);
  }
  
  public DoubleMatrix2D sort(DoubleMatrix2D paramDoubleMatrix2D, DoubleMatrix1DComparator paramDoubleMatrix1DComparator)
  {
    int[] arrayOfInt = new int[paramDoubleMatrix2D.rows()];
    int i = arrayOfInt.length;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      arrayOfInt[i] = i;
    }
    DoubleMatrix1D[] arrayOfDoubleMatrix1D = new DoubleMatrix1D[paramDoubleMatrix2D.rows()];
    int j = arrayOfDoubleMatrix1D.length;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      arrayOfDoubleMatrix1D[j] = paramDoubleMatrix2D.viewRow(j);
    }
    IntComparator local7 = new IntComparator()
    {
      private final DoubleMatrix1DComparator val$c;
      private final DoubleMatrix1D[] val$views;
      
      public int compare(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        return this.val$c.compare(this.val$views[paramAnonymousInt1], this.val$views[paramAnonymousInt2]);
      }
    };
    runSort(arrayOfInt, 0, arrayOfInt.length, local7);
    return paramDoubleMatrix2D.viewSelection(arrayOfInt, null);
  }
  
  public DoubleMatrix2D sort(DoubleMatrix2D paramDoubleMatrix2D, BinFunction1D paramBinFunction1D)
  {
    DoubleMatrix2D localDoubleMatrix2D = paramDoubleMatrix2D.like(1, paramDoubleMatrix2D.rows());
    BinFunction1D[] arrayOfBinFunction1D = { paramBinFunction1D };
    Statistic.aggregate(paramDoubleMatrix2D.viewDice(), arrayOfBinFunction1D, localDoubleMatrix2D);
    double[] arrayOfDouble = localDoubleMatrix2D.viewRow(0).toArray();
    return sort(paramDoubleMatrix2D, arrayOfDouble);
  }
  
  public DoubleMatrix3D sort(DoubleMatrix3D paramDoubleMatrix3D, int paramInt1, int paramInt2)
  {
    if ((paramInt1 < 0) || (paramInt1 >= paramDoubleMatrix3D.rows())) {
      throw new IndexOutOfBoundsException("row=" + paramInt1 + ", matrix=" + Formatter.shape(paramDoubleMatrix3D));
    }
    if ((paramInt2 < 0) || (paramInt2 >= paramDoubleMatrix3D.columns())) {
      throw new IndexOutOfBoundsException("column=" + paramInt2 + ", matrix=" + Formatter.shape(paramDoubleMatrix3D));
    }
    int[] arrayOfInt = new int[paramDoubleMatrix3D.slices()];
    int i = arrayOfInt.length;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      arrayOfInt[i] = i;
    }
    DoubleMatrix1D localDoubleMatrix1D = paramDoubleMatrix3D.viewRow(paramInt1).viewColumn(paramInt2);
    IntComparator local8 = new IntComparator()
    {
      private final DoubleMatrix1D val$sliceView;
      
      public int compare(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        double d1 = this.val$sliceView.getQuick(paramAnonymousInt1);
        double d2 = this.val$sliceView.getQuick(paramAnonymousInt2);
        if ((d1 != d1) || (d2 != d2)) {
          return Sorting.compareNaN(d1, d2);
        }
        return d1 == d2 ? 0 : d1 < d2 ? -1 : 1;
      }
    };
    runSort(arrayOfInt, 0, arrayOfInt.length, local8);
    return paramDoubleMatrix3D.viewSelection(arrayOfInt, null, null);
  }
  
  public DoubleMatrix3D sort(DoubleMatrix3D paramDoubleMatrix3D, DoubleMatrix2DComparator paramDoubleMatrix2DComparator)
  {
    int[] arrayOfInt = new int[paramDoubleMatrix3D.slices()];
    int i = arrayOfInt.length;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      arrayOfInt[i] = i;
    }
    DoubleMatrix2D[] arrayOfDoubleMatrix2D = new DoubleMatrix2D[paramDoubleMatrix3D.slices()];
    int j = arrayOfDoubleMatrix2D.length;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      arrayOfDoubleMatrix2D[j] = paramDoubleMatrix3D.viewSlice(j);
    }
    IntComparator local9 = new IntComparator()
    {
      private final DoubleMatrix2DComparator val$c;
      private final DoubleMatrix2D[] val$views;
      
      public int compare(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        return this.val$c.compare(this.val$views[paramAnonymousInt1], this.val$views[paramAnonymousInt2]);
      }
    };
    runSort(arrayOfInt, 0, arrayOfInt.length, local9);
    return paramDoubleMatrix3D.viewSelection(arrayOfInt, null, null);
  }
  
  public static void zdemo1()
  {
    Sorting localSorting = quickSort;
    DoubleMatrix2D localDoubleMatrix2D = DoubleFactory2D.dense.descending(4, 3);
    DoubleMatrix1DComparator local10 = new DoubleMatrix1DComparator()
    {
      public int compare(DoubleMatrix1D paramAnonymousDoubleMatrix1D1, DoubleMatrix1D paramAnonymousDoubleMatrix1D2)
      {
        double d1 = paramAnonymousDoubleMatrix1D1.zSum();
        double d2 = paramAnonymousDoubleMatrix1D2.zSum();
        return d1 == d2 ? 0 : d1 < d2 ? -1 : 1;
      }
    };
    System.out.println("unsorted:" + localDoubleMatrix2D);
    System.out.println("sorted  :" + localSorting.sort(localDoubleMatrix2D, local10));
  }
  
  public static void zdemo2()
  {
    Sorting localSorting = quickSort;
    DoubleMatrix3D localDoubleMatrix3D = DoubleFactory3D.dense.descending(4, 3, 2);
    DoubleMatrix2DComparator local11 = new DoubleMatrix2DComparator()
    {
      public int compare(DoubleMatrix2D paramAnonymousDoubleMatrix2D1, DoubleMatrix2D paramAnonymousDoubleMatrix2D2)
      {
        double d1 = paramAnonymousDoubleMatrix2D1.zSum();
        double d2 = paramAnonymousDoubleMatrix2D2.zSum();
        return d1 == d2 ? 0 : d1 < d2 ? -1 : 1;
      }
    };
    System.out.println("unsorted:" + localDoubleMatrix3D);
    System.out.println("sorted  :" + localSorting.sort(localDoubleMatrix3D, local11));
  }
  
  public static void zdemo3()
  {
    Sorting localSorting = quickSort;
    double[] arrayOfDouble = { 0.5D, 1.5D, 2.5D, 3.5D };
    DenseDoubleMatrix1D localDenseDoubleMatrix1D = new DenseDoubleMatrix1D(arrayOfDouble);
    DoubleComparator local12 = new DoubleComparator()
    {
      public int compare(double paramAnonymousDouble1, double paramAnonymousDouble2)
      {
        double d1 = Math.sin(paramAnonymousDouble1);
        double d2 = Math.sin(paramAnonymousDouble2);
        return d1 == d2 ? 0 : d1 < d2 ? -1 : 1;
      }
    };
    System.out.println("unsorted:" + localDenseDoubleMatrix1D);
    DoubleMatrix1D localDoubleMatrix1D = localSorting.sort(localDenseDoubleMatrix1D, local12);
    System.out.println("sorted  :" + localDoubleMatrix1D);
    localDoubleMatrix1D.assign(Functions.sin);
    System.out.println("sined  :" + localDoubleMatrix1D);
  }
  
  protected static void zdemo4()
  {
    double[] arrayOfDouble1 = { 0.0D, 1.0D, 2.0D, 3.0D };
    double[] arrayOfDouble2 = { 0.0D, 2.0D, 4.0D, 6.0D };
    DenseDoubleMatrix1D localDenseDoubleMatrix1D1 = new DenseDoubleMatrix1D(arrayOfDouble1);
    DenseDoubleMatrix1D localDenseDoubleMatrix1D2 = new DenseDoubleMatrix1D(arrayOfDouble2);
    System.out.println("m1:" + localDenseDoubleMatrix1D1);
    System.out.println("m2:" + localDenseDoubleMatrix1D2);
    localDenseDoubleMatrix1D1.assign(localDenseDoubleMatrix1D2, Functions.pow);
    System.out.println("applied:" + localDenseDoubleMatrix1D1);
  }
  
  public static void zdemo5(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    Sorting localSorting = quickSort;
    System.out.println("\n\n");
    System.out.print("now initializing... ");
    Timer localTimer = new Timer().start();
    Functions localFunctions = Functions.functions;
    Object localObject = DoubleFactory2D.dense.make(paramInt1, paramInt2);
    ((DoubleMatrix2D)localObject).assign(new DRand());
    localTimer.stop().display();
    DoubleMatrix2D localDoubleMatrix2D = ((DoubleMatrix2D)localObject).like();
    localTimer.reset().start();
    System.out.print("now copying... ");
    localDoubleMatrix2D.assign((DoubleMatrix2D)localObject);
    localTimer.stop().display();
    localTimer.reset().start();
    System.out.print("now copying subrange... ");
    localDoubleMatrix2D.viewPart(0, 0, paramInt1, paramInt2).assign(((DoubleMatrix2D)localObject).viewPart(0, 0, paramInt1, paramInt2));
    localTimer.stop().display();
    localTimer.reset().start();
    System.out.print("now copying selected... ");
    localDoubleMatrix2D.viewSelection(null, null).assign(((DoubleMatrix2D)localObject).viewSelection(null, null));
    localTimer.stop().display();
    System.out.print("now sorting - quick version with precomputation... ");
    localTimer.reset().start();
    localObject = localSorting.sort((DoubleMatrix2D)localObject, BinFunctions1D.median);
    localTimer.stop().display();
    if (paramBoolean)
    {
      int i = Math.min(paramInt1, 5);
      BinFunction1D[] arrayOfBinFunction1D = { BinFunctions1D.median, BinFunctions1D.sumLog, BinFunctions1D.geometricMean };
      String[] arrayOfString1 = new String[i];
      String[] arrayOfString2 = new String[paramInt2];
      int j = paramInt2;
      for (;;)
      {
        j--;
        if (j < 0) {
          break;
        }
        arrayOfString2[j] = Integer.toString(j);
      }
      j = i;
      for (;;)
      {
        j--;
        if (j < 0) {
          break;
        }
        arrayOfString1[j] = Integer.toString(j);
      }
      System.out.println("first part of sorted result = \n" + new Formatter("%G").toTitleString(((DoubleMatrix2D)localObject).viewPart(0, 0, i, paramInt2), arrayOfString1, arrayOfString2, null, null, null, arrayOfBinFunction1D));
    }
    System.out.print("now sorting - slow version... ");
    localObject = localDoubleMatrix2D;
    DoubleMatrix1DComparator local13 = new DoubleMatrix1DComparator()
    {
      public int compare(DoubleMatrix1D paramAnonymousDoubleMatrix1D1, DoubleMatrix1D paramAnonymousDoubleMatrix1D2)
      {
        double d1 = Statistic.bin(paramAnonymousDoubleMatrix1D1).median();
        double d2 = Statistic.bin(paramAnonymousDoubleMatrix1D2).median();
        return d1 == d2 ? 0 : d1 < d2 ? -1 : 1;
      }
    };
    localTimer.reset().start();
    localObject = localSorting.sort((DoubleMatrix2D)localObject, local13);
    localTimer.stop().display();
  }
  
  public static void zdemo6()
  {
    Sorting localSorting = quickSort;
    double[][] arrayOfDouble = { { 3.0D, 7.0D, 0.0D }, { 2.0D, 1.0D, 0.0D }, { 2.0D, 2.0D, 0.0D }, { 1.0D, 8.0D, 0.0D }, { 2.0D, 5.0D, 0.0D }, { 7.0D, 0.0D, 0.0D }, { 2.0D, 3.0D, 0.0D }, { 1.0D, 0.0D, 0.0D }, { 4.0D, 0.0D, 0.0D }, { 2.0D, 0.0D, 0.0D } };
    DoubleMatrix2D localDoubleMatrix2D1 = DoubleFactory2D.dense.make(arrayOfDouble);
    System.out.println("\n\nunsorted:" + localDoubleMatrix2D1);
    DoubleMatrix2D localDoubleMatrix2D2 = quickSort.sort(localDoubleMatrix2D1, 1);
    DoubleMatrix2D localDoubleMatrix2D3 = quickSort.sort(localDoubleMatrix2D2, 0);
    System.out.println("quick sorted  :" + localDoubleMatrix2D3);
    localDoubleMatrix2D2 = mergeSort.sort(localDoubleMatrix2D1, 1);
    localDoubleMatrix2D3 = mergeSort.sort(localDoubleMatrix2D2, 0);
    System.out.println("merge sorted  :" + localDoubleMatrix2D3);
  }
  
  public static void zdemo7(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    System.out.println("\n\n");
    System.out.println("now initializing... ");
    Functions localFunctions = Functions.functions;
    DoubleMatrix2D localDoubleMatrix2D1 = DoubleFactory2D.dense.make(paramInt1, paramInt2);
    localDoubleMatrix2D1.assign(new DRand());
    DoubleMatrix2D localDoubleMatrix2D2 = localDoubleMatrix2D1.copy();
    double[] arrayOfDouble1 = localDoubleMatrix2D1.viewColumn(0).toArray();
    double[] arrayOfDouble2 = localDoubleMatrix2D1.viewColumn(0).toArray();
    System.out.print("now quick sorting... ");
    Timer localTimer = new Timer().start();
    quickSort.sort(localDoubleMatrix2D1, 0);
    localTimer.stop().display();
    System.out.print("now merge sorting... ");
    localTimer.reset().start();
    mergeSort.sort(localDoubleMatrix2D1, 0);
    localTimer.stop().display();
    System.out.print("now quick sorting with simple aggregation... ");
    localTimer.reset().start();
    quickSort.sort(localDoubleMatrix2D1, arrayOfDouble1);
    localTimer.stop().display();
    System.out.print("now merge sorting with simple aggregation... ");
    localTimer.reset().start();
    mergeSort.sort(localDoubleMatrix2D1, arrayOfDouble2);
    localTimer.stop().display();
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.doublealgo.Sorting
 * JD-Core Version:    0.7.0.1
 */