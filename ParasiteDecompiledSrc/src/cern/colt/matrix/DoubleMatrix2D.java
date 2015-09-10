package cern.colt.matrix;

import cern.colt.function.Double9Function;
import cern.colt.function.DoubleDoubleFunction;
import cern.colt.function.DoubleFunction;
import cern.colt.function.DoubleProcedure;
import cern.colt.function.IntIntDoubleFunction;
import cern.colt.list.DoubleArrayList;
import cern.colt.list.IntArrayList;
import cern.colt.matrix.doublealgo.Formatter;
import cern.colt.matrix.doublealgo.Sorting;
import cern.colt.matrix.impl.AbstractMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.linalg.Property;
import cern.jet.math.Functions;

public abstract class DoubleMatrix2D
  extends AbstractMatrix2D
{
  public double aggregate(DoubleDoubleFunction paramDoubleDoubleFunction, DoubleFunction paramDoubleFunction)
  {
    if (size() == 0) {
      return (0.0D / 0.0D);
    }
    double d = paramDoubleFunction.apply(getQuick(this.rows - 1, this.columns - 1));
    int i = 1;
    int j = this.rows;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      int k = this.columns - i;
      for (;;)
      {
        k--;
        if (k < 0) {
          break;
        }
        d = paramDoubleDoubleFunction.apply(d, paramDoubleFunction.apply(getQuick(j, k)));
      }
      i = 0;
    }
    return d;
  }
  
  public double aggregate(DoubleMatrix2D paramDoubleMatrix2D, DoubleDoubleFunction paramDoubleDoubleFunction1, DoubleDoubleFunction paramDoubleDoubleFunction2)
  {
    checkShape(paramDoubleMatrix2D);
    if (size() == 0) {
      return (0.0D / 0.0D);
    }
    double d = paramDoubleDoubleFunction2.apply(getQuick(this.rows - 1, this.columns - 1), paramDoubleMatrix2D.getQuick(this.rows - 1, this.columns - 1));
    int i = 1;
    int j = this.rows;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      int k = this.columns - i;
      for (;;)
      {
        k--;
        if (k < 0) {
          break;
        }
        d = paramDoubleDoubleFunction1.apply(d, paramDoubleDoubleFunction2.apply(getQuick(j, k), paramDoubleMatrix2D.getQuick(j, k)));
      }
      i = 0;
    }
    return d;
  }
  
  public DoubleMatrix2D assign(double[][] paramArrayOfDouble)
  {
    if (paramArrayOfDouble.length != this.rows) {
      throw new IllegalArgumentException("Must have same number of rows: rows=" + paramArrayOfDouble.length + "rows()=" + rows());
    }
    int i = this.rows;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      double[] arrayOfDouble = paramArrayOfDouble[i];
      if (arrayOfDouble.length != this.columns) {
        throw new IllegalArgumentException("Must have same number of columns in every row: columns=" + arrayOfDouble.length + "columns()=" + columns());
      }
      int j = this.columns;
      for (;;)
      {
        j--;
        if (j < 0) {
          break;
        }
        setQuick(i, j, arrayOfDouble[j]);
      }
    }
    return this;
  }
  
  public DoubleMatrix2D assign(double paramDouble)
  {
    int i = this.rows;
    int j = this.columns;
    for (int k = 0; k < i; k++) {
      for (int m = 0; m < j; m++) {
        setQuick(k, m, paramDouble);
      }
    }
    return this;
  }
  
  public DoubleMatrix2D assign(DoubleFunction paramDoubleFunction)
  {
    int i = this.rows;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      int j = this.columns;
      for (;;)
      {
        j--;
        if (j < 0) {
          break;
        }
        setQuick(i, j, paramDoubleFunction.apply(getQuick(i, j)));
      }
    }
    return this;
  }
  
  public DoubleMatrix2D assign(DoubleMatrix2D paramDoubleMatrix2D)
  {
    if (paramDoubleMatrix2D == this) {
      return this;
    }
    checkShape(paramDoubleMatrix2D);
    if (haveSharedCells(paramDoubleMatrix2D)) {
      paramDoubleMatrix2D = paramDoubleMatrix2D.copy();
    }
    int i = this.rows;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      int j = this.columns;
      for (;;)
      {
        j--;
        if (j < 0) {
          break;
        }
        setQuick(i, j, paramDoubleMatrix2D.getQuick(i, j));
      }
    }
    return this;
  }
  
  public DoubleMatrix2D assign(DoubleMatrix2D paramDoubleMatrix2D, DoubleDoubleFunction paramDoubleDoubleFunction)
  {
    checkShape(paramDoubleMatrix2D);
    int i = this.rows;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      int j = this.columns;
      for (;;)
      {
        j--;
        if (j < 0) {
          break;
        }
        setQuick(i, j, paramDoubleDoubleFunction.apply(getQuick(i, j), paramDoubleMatrix2D.getQuick(i, j)));
      }
    }
    return this;
  }
  
  public int cardinality()
  {
    int i = 0;
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
        if (getQuick(j, k) != 0.0D) {
          i++;
        }
      }
    }
    return i;
  }
  
  public DoubleMatrix2D copy()
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
    if (!(paramObject instanceof DoubleMatrix2D)) {
      return false;
    }
    return Property.DEFAULT.equals(this, (DoubleMatrix2D)paramObject);
  }
  
  public DoubleMatrix2D forEachNonZero(IntIntDoubleFunction paramIntIntDoubleFunction)
  {
    int i = this.rows;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      int j = this.columns;
      for (;;)
      {
        j--;
        if (j < 0) {
          break;
        }
        double d1 = getQuick(i, j);
        if (d1 != 0.0D)
        {
          double d2 = paramIntIntDoubleFunction.apply(i, j, d1);
          if (d2 != d1) {
            setQuick(i, j, d2);
          }
        }
      }
    }
    return this;
  }
  
  public double get(int paramInt1, int paramInt2)
  {
    if ((paramInt2 < 0) || (paramInt2 >= this.columns) || (paramInt1 < 0) || (paramInt1 >= this.rows)) {
      throw new IndexOutOfBoundsException("row:" + paramInt1 + ", column:" + paramInt2);
    }
    return getQuick(paramInt1, paramInt2);
  }
  
  protected DoubleMatrix2D getContent()
  {
    return this;
  }
  
  public void getNonZeros(IntArrayList paramIntArrayList1, IntArrayList paramIntArrayList2, DoubleArrayList paramDoubleArrayList)
  {
    paramIntArrayList1.clear();
    paramIntArrayList2.clear();
    paramDoubleArrayList.clear();
    int i = this.rows;
    int j = this.columns;
    for (int k = 0; k < i; k++) {
      for (int m = 0; m < j; m++)
      {
        double d = getQuick(k, m);
        if (d != 0.0D)
        {
          paramIntArrayList1.add(k);
          paramIntArrayList2.add(m);
          paramDoubleArrayList.add(d);
        }
      }
    }
  }
  
  public abstract double getQuick(int paramInt1, int paramInt2);
  
  protected boolean haveSharedCells(DoubleMatrix2D paramDoubleMatrix2D)
  {
    if (paramDoubleMatrix2D == null) {
      return false;
    }
    if (this == paramDoubleMatrix2D) {
      return true;
    }
    return getContent().haveSharedCellsRaw(paramDoubleMatrix2D.getContent());
  }
  
  protected boolean haveSharedCellsRaw(DoubleMatrix2D paramDoubleMatrix2D)
  {
    return false;
  }
  
  public DoubleMatrix2D like()
  {
    return like(this.rows, this.columns);
  }
  
  public abstract DoubleMatrix2D like(int paramInt1, int paramInt2);
  
  public abstract DoubleMatrix1D like1D(int paramInt);
  
  protected abstract DoubleMatrix1D like1D(int paramInt1, int paramInt2, int paramInt3);
  
  public void set(int paramInt1, int paramInt2, double paramDouble)
  {
    if ((paramInt2 < 0) || (paramInt2 >= this.columns) || (paramInt1 < 0) || (paramInt1 >= this.rows)) {
      throw new IndexOutOfBoundsException("row:" + paramInt1 + ", column:" + paramInt2);
    }
    setQuick(paramInt1, paramInt2, paramDouble);
  }
  
  public abstract void setQuick(int paramInt1, int paramInt2, double paramDouble);
  
  public double[][] toArray()
  {
    double[][] arrayOfDouble = new double[this.rows][this.columns];
    int i = this.rows;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      double[] arrayOfDouble1 = arrayOfDouble[i];
      int j = this.columns;
      for (;;)
      {
        j--;
        if (j < 0) {
          break;
        }
        arrayOfDouble1[j] = getQuick(i, j);
      }
    }
    return arrayOfDouble;
  }
  
  public String toString()
  {
    return new Formatter().toString(this);
  }
  
  protected DoubleMatrix2D view()
  {
    return (DoubleMatrix2D)clone();
  }
  
  public DoubleMatrix1D viewColumn(int paramInt)
  {
    checkColumn(paramInt);
    int i = this.rows;
    int j = index(0, paramInt);
    int k = this.rowStride;
    return like1D(i, j, k);
  }
  
  public DoubleMatrix2D viewColumnFlip()
  {
    return (DoubleMatrix2D)view().vColumnFlip();
  }
  
  public DoubleMatrix2D viewDice()
  {
    return (DoubleMatrix2D)view().vDice();
  }
  
  public DoubleMatrix2D viewPart(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return (DoubleMatrix2D)view().vPart(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public DoubleMatrix1D viewRow(int paramInt)
  {
    checkRow(paramInt);
    int i = this.columns;
    int j = index(paramInt, 0);
    int k = this.columnStride;
    return like1D(i, j, k);
  }
  
  public DoubleMatrix2D viewRowFlip()
  {
    return (DoubleMatrix2D)view().vRowFlip();
  }
  
  public DoubleMatrix2D viewSelection(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    int i;
    if (paramArrayOfInt1 == null)
    {
      paramArrayOfInt1 = new int[this.rows];
      i = this.rows;
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
      paramArrayOfInt2 = new int[this.columns];
      i = this.columns;
      for (;;)
      {
        i--;
        if (i < 0) {
          break;
        }
        paramArrayOfInt2[i] = i;
      }
    }
    checkRowIndexes(paramArrayOfInt1);
    checkColumnIndexes(paramArrayOfInt2);
    int[] arrayOfInt1 = new int[paramArrayOfInt1.length];
    int[] arrayOfInt2 = new int[paramArrayOfInt2.length];
    int j = paramArrayOfInt1.length;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      arrayOfInt1[j] = _rowOffset(_rowRank(paramArrayOfInt1[j]));
    }
    j = paramArrayOfInt2.length;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      arrayOfInt2[j] = _columnOffset(_columnRank(paramArrayOfInt2[j]));
    }
    return viewSelectionLike(arrayOfInt1, arrayOfInt2);
  }
  
  public DoubleMatrix2D viewSelection(DoubleMatrix1DProcedure paramDoubleMatrix1DProcedure)
  {
    IntArrayList localIntArrayList = new IntArrayList();
    for (int i = 0; i < this.rows; i++) {
      if (paramDoubleMatrix1DProcedure.apply(viewRow(i))) {
        localIntArrayList.add(i);
      }
    }
    localIntArrayList.trimToSize();
    return viewSelection(localIntArrayList.elements(), null);
  }
  
  protected abstract DoubleMatrix2D viewSelectionLike(int[] paramArrayOfInt1, int[] paramArrayOfInt2);
  
  public DoubleMatrix2D viewSorted(int paramInt)
  {
    return Sorting.mergeSort.sort(this, paramInt);
  }
  
  public DoubleMatrix2D viewStrides(int paramInt1, int paramInt2)
  {
    return (DoubleMatrix2D)view().vStrides(paramInt1, paramInt2);
  }
  
  private boolean xforEach(DoubleProcedure paramDoubleProcedure)
  {
    int i;
    int j;
    do
    {
      i = this.rows;
      i--;
      do
      {
        if (i < 0) {
          break;
        }
        j = this.columns;
        j--;
      } while (j < 0);
    } while (paramDoubleProcedure.apply(getQuick(i, j)));
    return false;
    return true;
  }
  
  public void zAssign8Neighbors(DoubleMatrix2D paramDoubleMatrix2D, Double9Function paramDouble9Function)
  {
    if (paramDouble9Function == null) {
      throw new NullPointerException("function must not be null.");
    }
    checkShape(paramDoubleMatrix2D);
    if ((this.rows < 3) || (this.columns < 3)) {
      return;
    }
    int i = this.rows - 1;
    int j = this.columns - 1;
    for (int k = 1; k < i; k++)
    {
      double d1 = getQuick(k - 1, 0);
      double d2 = getQuick(k - 1, 1);
      double d4 = getQuick(k, 0);
      double d5 = getQuick(k, 1);
      double d7 = getQuick(k + 1, 0);
      double d8 = getQuick(k + 1, 1);
      for (int m = 1; m < j; m++)
      {
        double d3 = getQuick(k - 1, m + 1);
        double d6 = getQuick(k, m + 1);
        double d9 = getQuick(k + 1, m + 1);
        paramDoubleMatrix2D.setQuick(k, m, paramDouble9Function.apply(d1, d2, d3, d4, d5, d6, d7, d8, d9));
        d1 = d2;
        d4 = d5;
        d7 = d8;
        d2 = d3;
        d5 = d6;
        d8 = d9;
      }
    }
  }
  
  public DoubleMatrix1D zMult(DoubleMatrix1D paramDoubleMatrix1D1, DoubleMatrix1D paramDoubleMatrix1D2)
  {
    return zMult(paramDoubleMatrix1D1, paramDoubleMatrix1D2, 1.0D, paramDoubleMatrix1D2 == null ? 1 : 0, false);
  }
  
  public DoubleMatrix1D zMult(DoubleMatrix1D paramDoubleMatrix1D1, DoubleMatrix1D paramDoubleMatrix1D2, double paramDouble1, double paramDouble2, boolean paramBoolean)
  {
    if (paramBoolean) {
      return viewDice().zMult(paramDoubleMatrix1D1, paramDoubleMatrix1D2, paramDouble1, paramDouble2, false);
    }
    if (paramDoubleMatrix1D2 == null) {
      paramDoubleMatrix1D2 = new DenseDoubleMatrix1D(this.rows);
    }
    if ((this.columns != paramDoubleMatrix1D1.size()) || (this.rows > paramDoubleMatrix1D2.size())) {
      throw new IllegalArgumentException("Incompatible args: " + toStringShort() + ", " + paramDoubleMatrix1D1.toStringShort() + ", " + paramDoubleMatrix1D2.toStringShort());
    }
    int i = this.rows;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      double d = 0.0D;
      int j = this.columns;
      for (;;)
      {
        j--;
        if (j < 0) {
          break;
        }
        d += getQuick(i, j) * paramDoubleMatrix1D1.getQuick(j);
      }
      paramDoubleMatrix1D2.setQuick(i, paramDouble1 * d + paramDouble2 * paramDoubleMatrix1D2.getQuick(i));
    }
    return paramDoubleMatrix1D2;
  }
  
  public DoubleMatrix2D zMult(DoubleMatrix2D paramDoubleMatrix2D1, DoubleMatrix2D paramDoubleMatrix2D2)
  {
    return zMult(paramDoubleMatrix2D1, paramDoubleMatrix2D2, 1.0D, paramDoubleMatrix2D2 == null ? 1 : 0, false, false);
  }
  
  public DoubleMatrix2D zMult(DoubleMatrix2D paramDoubleMatrix2D1, DoubleMatrix2D paramDoubleMatrix2D2, double paramDouble1, double paramDouble2, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (paramBoolean1) {
      return viewDice().zMult(paramDoubleMatrix2D1, paramDoubleMatrix2D2, paramDouble1, paramDouble2, false, paramBoolean2);
    }
    if (paramBoolean2) {
      return zMult(paramDoubleMatrix2D1.viewDice(), paramDoubleMatrix2D2, paramDouble1, paramDouble2, paramBoolean1, false);
    }
    int i = this.rows;
    int j = this.columns;
    int k = paramDoubleMatrix2D1.columns;
    if (paramDoubleMatrix2D2 == null) {
      paramDoubleMatrix2D2 = new DenseDoubleMatrix2D(i, k);
    }
    if (paramDoubleMatrix2D1.rows != j) {
      throw new IllegalArgumentException("Matrix2D inner dimensions must agree:" + toStringShort() + ", " + paramDoubleMatrix2D1.toStringShort());
    }
    if ((paramDoubleMatrix2D2.rows != i) || (paramDoubleMatrix2D2.columns != k)) {
      throw new IllegalArgumentException("Incompatibel result matrix: " + toStringShort() + ", " + paramDoubleMatrix2D1.toStringShort() + ", " + paramDoubleMatrix2D2.toStringShort());
    }
    if ((this == paramDoubleMatrix2D2) || (paramDoubleMatrix2D1 == paramDoubleMatrix2D2)) {
      throw new IllegalArgumentException("Matrices must not be identical");
    }
    int m = k;
    for (;;)
    {
      m--;
      if (m < 0) {
        break;
      }
      int n = i;
      for (;;)
      {
        n--;
        if (n < 0) {
          break;
        }
        double d = 0.0D;
        int i1 = j;
        for (;;)
        {
          i1--;
          if (i1 < 0) {
            break;
          }
          d += getQuick(n, i1) * paramDoubleMatrix2D1.getQuick(i1, m);
        }
        paramDoubleMatrix2D2.setQuick(n, m, paramDouble1 * d + paramDouble2 * paramDoubleMatrix2D2.getQuick(n, m));
      }
    }
    return paramDoubleMatrix2D2;
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
 * Qualified Name:     cern.colt.matrix.DoubleMatrix2D
 * JD-Core Version:    0.7.0.1
 */