package cern.colt.matrix;

import cern.colt.function.Double27Function;
import cern.colt.function.DoubleDoubleFunction;
import cern.colt.function.DoubleFunction;
import cern.colt.function.DoubleProcedure;
import cern.colt.function.IntIntIntProcedure;
import cern.colt.list.DoubleArrayList;
import cern.colt.list.IntArrayList;
import cern.colt.matrix.doublealgo.Formatter;
import cern.colt.matrix.doublealgo.Sorting;
import cern.colt.matrix.impl.AbstractMatrix3D;
import cern.colt.matrix.linalg.Property;
import cern.jet.math.Functions;

public abstract class DoubleMatrix3D
  extends AbstractMatrix3D
{
  public double aggregate(DoubleDoubleFunction paramDoubleDoubleFunction, DoubleFunction paramDoubleFunction)
  {
    if (size() == 0) {
      return (0.0D / 0.0D);
    }
    double d = paramDoubleFunction.apply(getQuick(this.slices - 1, this.rows - 1, this.columns - 1));
    int i = 1;
    int j = this.slices;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      int k = this.rows;
      for (;;)
      {
        k--;
        if (k < 0) {
          break;
        }
        int m = this.columns - i;
        for (;;)
        {
          m--;
          if (m < 0) {
            break;
          }
          d = paramDoubleDoubleFunction.apply(d, paramDoubleFunction.apply(getQuick(j, k, m)));
        }
        i = 0;
      }
    }
    return d;
  }
  
  public double aggregate(DoubleMatrix3D paramDoubleMatrix3D, DoubleDoubleFunction paramDoubleDoubleFunction1, DoubleDoubleFunction paramDoubleDoubleFunction2)
  {
    checkShape(paramDoubleMatrix3D);
    if (size() == 0) {
      return (0.0D / 0.0D);
    }
    double d = paramDoubleDoubleFunction2.apply(getQuick(this.slices - 1, this.rows - 1, this.columns - 1), paramDoubleMatrix3D.getQuick(this.slices - 1, this.rows - 1, this.columns - 1));
    int i = 1;
    int j = this.slices;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      int k = this.rows;
      for (;;)
      {
        k--;
        if (k < 0) {
          break;
        }
        int m = this.columns - i;
        for (;;)
        {
          m--;
          if (m < 0) {
            break;
          }
          d = paramDoubleDoubleFunction1.apply(d, paramDoubleDoubleFunction2.apply(getQuick(j, k, m), paramDoubleMatrix3D.getQuick(j, k, m)));
        }
        i = 0;
      }
    }
    return d;
  }
  
  public DoubleMatrix3D assign(double[][][] paramArrayOfDouble)
  {
    if (paramArrayOfDouble.length != this.slices) {
      throw new IllegalArgumentException("Must have same number of slices: slices=" + paramArrayOfDouble.length + "slices()=" + slices());
    }
    int i = this.slices;
    i--;
    if (i >= 0)
    {
      double[][] arrayOfDouble = paramArrayOfDouble[i];
      if (arrayOfDouble.length != this.rows) {
        throw new IllegalArgumentException("Must have same number of rows in every slice: rows=" + arrayOfDouble.length + "rows()=" + rows());
      }
      int j = this.rows;
      for (;;)
      {
        j--;
        if (j < 0) {
          break;
        }
        double[] arrayOfDouble1 = arrayOfDouble[j];
        if (arrayOfDouble1.length != this.columns) {
          throw new IllegalArgumentException("Must have same number of columns in every row: columns=" + arrayOfDouble1.length + "columns()=" + columns());
        }
        int k = this.columns;
        for (;;)
        {
          k--;
          if (k < 0) {
            break;
          }
          setQuick(i, j, k, arrayOfDouble1[k]);
        }
      }
    }
    return this;
  }
  
  public DoubleMatrix3D assign(double paramDouble)
  {
    int i = this.slices;
    i--;
    if (i >= 0)
    {
      int j = this.rows;
      for (;;)
      {
        j--;
        if (j < 0) {
          break;
        }
        int k = this.columns;
        for (;;)
        {
          k--;
          if (k < 0) {
            break;
          }
          setQuick(i, j, k, paramDouble);
        }
      }
    }
    return this;
  }
  
  public DoubleMatrix3D assign(DoubleFunction paramDoubleFunction)
  {
    int i = this.slices;
    i--;
    if (i >= 0)
    {
      int j = this.rows;
      for (;;)
      {
        j--;
        if (j < 0) {
          break;
        }
        int k = this.columns;
        for (;;)
        {
          k--;
          if (k < 0) {
            break;
          }
          setQuick(i, j, k, paramDoubleFunction.apply(getQuick(i, j, k)));
        }
      }
    }
    return this;
  }
  
  public DoubleMatrix3D assign(DoubleMatrix3D paramDoubleMatrix3D)
  {
    if (paramDoubleMatrix3D == this) {
      return this;
    }
    checkShape(paramDoubleMatrix3D);
    if (haveSharedCells(paramDoubleMatrix3D)) {
      paramDoubleMatrix3D = paramDoubleMatrix3D.copy();
    }
    int i = this.slices;
    i--;
    if (i >= 0)
    {
      int j = this.rows;
      for (;;)
      {
        j--;
        if (j < 0) {
          break;
        }
        int k = this.columns;
        for (;;)
        {
          k--;
          if (k < 0) {
            break;
          }
          setQuick(i, j, k, paramDoubleMatrix3D.getQuick(i, j, k));
        }
      }
    }
    return this;
  }
  
  public DoubleMatrix3D assign(DoubleMatrix3D paramDoubleMatrix3D, DoubleDoubleFunction paramDoubleDoubleFunction)
  {
    checkShape(paramDoubleMatrix3D);
    int i = this.slices;
    i--;
    if (i >= 0)
    {
      int j = this.rows;
      for (;;)
      {
        j--;
        if (j < 0) {
          break;
        }
        int k = this.columns;
        for (;;)
        {
          k--;
          if (k < 0) {
            break;
          }
          setQuick(i, j, k, paramDoubleDoubleFunction.apply(getQuick(i, j, k), paramDoubleMatrix3D.getQuick(i, j, k)));
        }
      }
    }
    return this;
  }
  
  public int cardinality()
  {
    int i = 0;
    int j = this.slices;
    j--;
    if (j >= 0)
    {
      int k = this.rows;
      for (;;)
      {
        k--;
        if (k < 0) {
          break;
        }
        int m = this.columns;
        for (;;)
        {
          m--;
          if (m < 0) {
            break;
          }
          if (getQuick(j, k, m) != 0.0D) {
            i++;
          }
        }
      }
    }
    return i;
  }
  
  public DoubleMatrix3D copy()
  {
    return like().assign(this);
  }
  
  public boolean equals(double paramDouble)
  {
    return Property.DEFAULT.equals(this, paramDouble);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (paramObject == null) {
      return false;
    }
    if (!(paramObject instanceof DoubleMatrix3D)) {
      return false;
    }
    return Property.DEFAULT.equals(this, (DoubleMatrix3D)paramObject);
  }
  
  public double get(int paramInt1, int paramInt2, int paramInt3)
  {
    if ((paramInt1 < 0) || (paramInt1 >= this.slices) || (paramInt2 < 0) || (paramInt2 >= this.rows) || (paramInt3 < 0) || (paramInt3 >= this.columns)) {
      throw new IndexOutOfBoundsException("slice:" + paramInt1 + ", row:" + paramInt2 + ", column:" + paramInt3);
    }
    return getQuick(paramInt1, paramInt2, paramInt3);
  }
  
  protected DoubleMatrix3D getContent()
  {
    return this;
  }
  
  public void getNonZeros(IntArrayList paramIntArrayList1, IntArrayList paramIntArrayList2, IntArrayList paramIntArrayList3, DoubleArrayList paramDoubleArrayList)
  {
    paramIntArrayList1.clear();
    paramIntArrayList2.clear();
    paramIntArrayList3.clear();
    paramDoubleArrayList.clear();
    int i = this.slices;
    int j = this.rows;
    int k = this.columns;
    for (int m = 0; m < i; m++) {
      for (int n = 0; n < j; n++) {
        for (int i1 = 0; i1 < k; i1++)
        {
          double d = getQuick(m, n, i1);
          if (d != 0.0D)
          {
            paramIntArrayList1.add(m);
            paramIntArrayList2.add(n);
            paramIntArrayList3.add(i1);
            paramDoubleArrayList.add(d);
          }
        }
      }
    }
  }
  
  public abstract double getQuick(int paramInt1, int paramInt2, int paramInt3);
  
  protected boolean haveSharedCells(DoubleMatrix3D paramDoubleMatrix3D)
  {
    if (paramDoubleMatrix3D == null) {
      return false;
    }
    if (this == paramDoubleMatrix3D) {
      return true;
    }
    return getContent().haveSharedCellsRaw(paramDoubleMatrix3D.getContent());
  }
  
  protected boolean haveSharedCellsRaw(DoubleMatrix3D paramDoubleMatrix3D)
  {
    return false;
  }
  
  public DoubleMatrix3D like()
  {
    return like(this.slices, this.rows, this.columns);
  }
  
  public abstract DoubleMatrix3D like(int paramInt1, int paramInt2, int paramInt3);
  
  protected abstract DoubleMatrix2D like2D(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
  
  public void set(int paramInt1, int paramInt2, int paramInt3, double paramDouble)
  {
    if ((paramInt1 < 0) || (paramInt1 >= this.slices) || (paramInt2 < 0) || (paramInt2 >= this.rows) || (paramInt3 < 0) || (paramInt3 >= this.columns)) {
      throw new IndexOutOfBoundsException("slice:" + paramInt1 + ", row:" + paramInt2 + ", column:" + paramInt3);
    }
    setQuick(paramInt1, paramInt2, paramInt3, paramDouble);
  }
  
  public abstract void setQuick(int paramInt1, int paramInt2, int paramInt3, double paramDouble);
  
  public double[][][] toArray()
  {
    double[][][] arrayOfDouble = new double[this.slices][this.rows][this.columns];
    int i = this.slices;
    i--;
    if (i >= 0)
    {
      double[][] arrayOfDouble1 = arrayOfDouble[i];
      int j = this.rows;
      for (;;)
      {
        j--;
        if (j < 0) {
          break;
        }
        double[] arrayOfDouble2 = arrayOfDouble1[j];
        int k = this.columns;
        for (;;)
        {
          k--;
          if (k < 0) {
            break;
          }
          arrayOfDouble2[k] = getQuick(i, j, k);
        }
      }
    }
    return arrayOfDouble;
  }
  
  public String toString()
  {
    return new Formatter().toString(this);
  }
  
  protected DoubleMatrix3D view()
  {
    return (DoubleMatrix3D)clone();
  }
  
  public DoubleMatrix2D viewColumn(int paramInt)
  {
    checkColumn(paramInt);
    int i = this.slices;
    int j = this.rows;
    int k = this.sliceZero;
    int m = this.rowZero + _columnOffset(_columnRank(paramInt));
    int n = this.sliceStride;
    int i1 = this.rowStride;
    return like2D(i, j, k, m, n, i1);
  }
  
  public DoubleMatrix3D viewColumnFlip()
  {
    return (DoubleMatrix3D)view().vColumnFlip();
  }
  
  public DoubleMatrix3D viewDice(int paramInt1, int paramInt2, int paramInt3)
  {
    return (DoubleMatrix3D)view().vDice(paramInt1, paramInt2, paramInt3);
  }
  
  public DoubleMatrix3D viewPart(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    return (DoubleMatrix3D)view().vPart(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
  }
  
  public DoubleMatrix2D viewRow(int paramInt)
  {
    checkRow(paramInt);
    int i = this.slices;
    int j = this.columns;
    int k = this.sliceZero;
    int m = this.columnZero + _rowOffset(_rowRank(paramInt));
    int n = this.sliceStride;
    int i1 = this.columnStride;
    return like2D(i, j, k, m, n, i1);
  }
  
  public DoubleMatrix3D viewRowFlip()
  {
    return (DoubleMatrix3D)view().vRowFlip();
  }
  
  public DoubleMatrix3D viewSelection(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3)
  {
    int i;
    if (paramArrayOfInt1 == null)
    {
      paramArrayOfInt1 = new int[this.slices];
      i = this.slices;
      for (;;)
      {
        i--;
        if (i < 0) {
          break;
        }
        paramArrayOfInt1[i] = i;
      }
    }
    if (paramArrayOfInt2 == null)
    {
      paramArrayOfInt2 = new int[this.rows];
      i = this.rows;
      for (;;)
      {
        i--;
        if (i < 0) {
          break;
        }
        paramArrayOfInt2[i] = i;
      }
    }
    if (paramArrayOfInt3 == null)
    {
      paramArrayOfInt3 = new int[this.columns];
      i = this.columns;
      for (;;)
      {
        i--;
        if (i < 0) {
          break;
        }
        paramArrayOfInt3[i] = i;
      }
    }
    checkSliceIndexes(paramArrayOfInt1);
    checkRowIndexes(paramArrayOfInt2);
    checkColumnIndexes(paramArrayOfInt3);
    int[] arrayOfInt1 = new int[paramArrayOfInt1.length];
    int[] arrayOfInt2 = new int[paramArrayOfInt2.length];
    int[] arrayOfInt3 = new int[paramArrayOfInt3.length];
    int j = paramArrayOfInt1.length;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      arrayOfInt1[j] = _sliceOffset(_sliceRank(paramArrayOfInt1[j]));
    }
    j = paramArrayOfInt2.length;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      arrayOfInt2[j] = _rowOffset(_rowRank(paramArrayOfInt2[j]));
    }
    j = paramArrayOfInt3.length;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      arrayOfInt3[j] = _columnOffset(_columnRank(paramArrayOfInt3[j]));
    }
    return viewSelectionLike(arrayOfInt1, arrayOfInt2, arrayOfInt3);
  }
  
  public DoubleMatrix3D viewSelection(DoubleMatrix2DProcedure paramDoubleMatrix2DProcedure)
  {
    IntArrayList localIntArrayList = new IntArrayList();
    for (int i = 0; i < this.slices; i++) {
      if (paramDoubleMatrix2DProcedure.apply(viewSlice(i))) {
        localIntArrayList.add(i);
      }
    }
    localIntArrayList.trimToSize();
    return viewSelection(localIntArrayList.elements(), null, null);
  }
  
  protected abstract DoubleMatrix3D viewSelectionLike(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3);
  
  public DoubleMatrix2D viewSlice(int paramInt)
  {
    checkSlice(paramInt);
    int i = this.rows;
    int j = this.columns;
    int k = this.rowZero;
    int m = this.columnZero + _sliceOffset(_sliceRank(paramInt));
    int n = this.rowStride;
    int i1 = this.columnStride;
    return like2D(i, j, k, m, n, i1);
  }
  
  public DoubleMatrix3D viewSliceFlip()
  {
    return (DoubleMatrix3D)view().vSliceFlip();
  }
  
  public DoubleMatrix3D viewSorted(int paramInt1, int paramInt2)
  {
    return Sorting.mergeSort.sort(this, paramInt1, paramInt2);
  }
  
  public DoubleMatrix3D viewStrides(int paramInt1, int paramInt2, int paramInt3)
  {
    return (DoubleMatrix3D)view().vStrides(paramInt1, paramInt2, paramInt3);
  }
  
  private boolean xforEach(DoubleProcedure paramDoubleProcedure)
  {
    int i = this.slices;
    label5:
    i--;
    if (i >= 0)
    {
      int j;
      int k;
      label30:
      do
      {
        j = this.rows;
        j--;
        break label30;
        if (j < 0) {
          break label5;
        }
        k = this.columns;
        k--;
        if (k < 0) {
          break;
        }
      } while (paramDoubleProcedure.apply(getQuick(i, j, k)));
      return false;
    }
    return true;
  }
  
  private boolean xforEachCoordinate(IntIntIntProcedure paramIntIntIntProcedure)
  {
    int i = this.columns;
    label5:
    i--;
    if (i >= 0)
    {
      int j;
      int k;
      label30:
      do
      {
        j = this.slices;
        j--;
        break label30;
        if (j < 0) {
          break label5;
        }
        k = this.rows;
        k--;
        if (k < 0) {
          break;
        }
      } while (paramIntIntIntProcedure.apply(j, k, i));
      return false;
    }
    return true;
  }
  
  public void zAssign27Neighbors(DoubleMatrix3D paramDoubleMatrix3D, Double27Function paramDouble27Function)
  {
    if (paramDouble27Function == null) {
      throw new NullPointerException("function must not be null.");
    }
    checkShape(paramDoubleMatrix3D);
    if ((this.rows < 3) || (this.columns < 3) || (this.slices < 3)) {
      return;
    }
    int i = this.rows - 1;
    int j = this.columns - 1;
    for (int k = 1; k < this.slices - 1; k++) {
      for (int m = 1; m < i; m++)
      {
        double d1 = getQuick(k - 1, m - 1, 0);
        double d2 = getQuick(k - 1, m - 1, 1);
        double d4 = getQuick(k - 1, m, 0);
        double d5 = getQuick(k - 1, m, 1);
        double d7 = getQuick(k - 1, m + 1, 0);
        double d8 = getQuick(k - 1, m + 1, 1);
        double d10 = getQuick(k - 1, m - 1, 0);
        double d11 = getQuick(k, m - 1, 1);
        double d13 = getQuick(k, m, 0);
        double d14 = getQuick(k, m, 1);
        double d16 = getQuick(k, m + 1, 0);
        double d17 = getQuick(k, m + 1, 1);
        double d19 = getQuick(k + 1, m - 1, 0);
        double d20 = getQuick(k + 1, m - 1, 1);
        double d22 = getQuick(k + 1, m, 0);
        double d23 = getQuick(k + 1, m, 1);
        double d25 = getQuick(k + 1, m + 1, 0);
        double d26 = getQuick(k + 1, m + 1, 1);
        for (int n = 1; n < j; n++)
        {
          double d3 = getQuick(k - 1, m - 1, n + 1);
          double d6 = getQuick(k - 1, m, n + 1);
          double d9 = getQuick(k - 1, m + 1, n + 1);
          double d12 = getQuick(k, m - 1, n + 1);
          double d15 = getQuick(k, m, n + 1);
          double d18 = getQuick(k, m + 1, n + 1);
          double d21 = getQuick(k + 1, m - 1, n + 1);
          double d24 = getQuick(k + 1, m, n + 1);
          double d27 = getQuick(k + 1, m + 1, n + 1);
          paramDoubleMatrix3D.setQuick(k, m, n, paramDouble27Function.apply(d1, d2, d3, d4, d5, d6, d7, d8, d9, d10, d11, d12, d13, d14, d15, d16, d17, d18, d19, d20, d21, d22, d23, d24, d25, d26, d27));
          d1 = d2;
          d2 = d3;
          d4 = d5;
          d5 = d6;
          d7 = d8;
          d8 = d9;
          d10 = d11;
          d11 = d12;
          d13 = d14;
          d14 = d15;
          d16 = d17;
          d17 = d18;
          d19 = d20;
          d20 = d21;
          d22 = d23;
          d23 = d24;
          d25 = d26;
          d26 = d27;
        }
      }
    }
  }
  
  public double zSum()
  {
    if (size() == 0) {
      return 0.0D;
    }
    return aggregate(Functions.plus, Functions.identity);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.DoubleMatrix3D
 * JD-Core Version:    0.7.0.1
 */