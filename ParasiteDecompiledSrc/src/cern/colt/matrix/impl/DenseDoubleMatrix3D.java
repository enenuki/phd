package cern.colt.matrix.impl;

import cern.colt.function.Double27Function;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.DoubleMatrix3D;

public class DenseDoubleMatrix3D
  extends DoubleMatrix3D
{
  protected double[] elements;
  
  public DenseDoubleMatrix3D(double[][][] paramArrayOfDouble)
  {
    this(paramArrayOfDouble.length, paramArrayOfDouble.length == 0 ? 0 : paramArrayOfDouble[0].length, paramArrayOfDouble[0].length == 0 ? 0 : paramArrayOfDouble.length == 0 ? 0 : paramArrayOfDouble[0][0].length);
    assign(paramArrayOfDouble);
  }
  
  public DenseDoubleMatrix3D(int paramInt1, int paramInt2, int paramInt3)
  {
    setUp(paramInt1, paramInt2, paramInt3);
    this.elements = new double[paramInt1 * paramInt2 * paramInt3];
  }
  
  protected DenseDoubleMatrix3D(int paramInt1, int paramInt2, int paramInt3, double[] paramArrayOfDouble, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9)
  {
    setUp(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramInt9);
    this.elements = paramArrayOfDouble;
    this.isNoView = false;
  }
  
  public DoubleMatrix3D assign(double[][][] paramArrayOfDouble)
  {
    if (this.isNoView)
    {
      if (paramArrayOfDouble.length != this.slices) {
        throw new IllegalArgumentException("Must have same number of slices: slices=" + paramArrayOfDouble.length + "slices()=" + slices());
      }
      int i = this.slices * this.rows * this.columns - this.columns;
      int j = this.slices;
      for (;;)
      {
        j--;
        if (j < 0) {
          break;
        }
        double[][] arrayOfDouble = paramArrayOfDouble[j];
        if (arrayOfDouble.length != this.rows) {
          throw new IllegalArgumentException("Must have same number of rows in every slice: rows=" + arrayOfDouble.length + "rows()=" + rows());
        }
        int k = this.rows;
        for (;;)
        {
          k--;
          if (k < 0) {
            break;
          }
          double[] arrayOfDouble1 = arrayOfDouble[k];
          if (arrayOfDouble1.length != this.columns) {
            throw new IllegalArgumentException("Must have same number of columns in every row: columns=" + arrayOfDouble1.length + "columns()=" + columns());
          }
          System.arraycopy(arrayOfDouble1, 0, this.elements, i, this.columns);
          i -= this.columns;
        }
      }
    }
    super.assign(paramArrayOfDouble);
    return this;
  }
  
  public DoubleMatrix3D assign(DoubleMatrix3D paramDoubleMatrix3D)
  {
    if (!(paramDoubleMatrix3D instanceof DenseDoubleMatrix3D)) {
      return super.assign(paramDoubleMatrix3D);
    }
    DenseDoubleMatrix3D localDenseDoubleMatrix3D = (DenseDoubleMatrix3D)paramDoubleMatrix3D;
    if (localDenseDoubleMatrix3D == this) {
      return this;
    }
    checkShape(localDenseDoubleMatrix3D);
    if (haveSharedCells(localDenseDoubleMatrix3D))
    {
      DoubleMatrix3D localDoubleMatrix3D = localDenseDoubleMatrix3D.copy();
      if (!(localDoubleMatrix3D instanceof DenseDoubleMatrix3D)) {
        return super.assign(paramDoubleMatrix3D);
      }
      localDenseDoubleMatrix3D = (DenseDoubleMatrix3D)localDoubleMatrix3D;
    }
    if ((this.isNoView) && (localDenseDoubleMatrix3D.isNoView))
    {
      System.arraycopy(localDenseDoubleMatrix3D.elements, 0, this.elements, 0, this.elements.length);
      return this;
    }
    return super.assign(localDenseDoubleMatrix3D);
  }
  
  public double getQuick(int paramInt1, int paramInt2, int paramInt3)
  {
    return this.elements[(this.sliceZero + paramInt1 * this.sliceStride + this.rowZero + paramInt2 * this.rowStride + this.columnZero + paramInt3 * this.columnStride)];
  }
  
  protected boolean haveSharedCellsRaw(DoubleMatrix3D paramDoubleMatrix3D)
  {
    Object localObject;
    if ((paramDoubleMatrix3D instanceof SelectedDenseDoubleMatrix3D))
    {
      localObject = (SelectedDenseDoubleMatrix3D)paramDoubleMatrix3D;
      return this.elements == ((SelectedDenseDoubleMatrix3D)localObject).elements;
    }
    if ((paramDoubleMatrix3D instanceof DenseDoubleMatrix3D))
    {
      localObject = (DenseDoubleMatrix3D)paramDoubleMatrix3D;
      return this.elements == ((DenseDoubleMatrix3D)localObject).elements;
    }
    return false;
  }
  
  protected int index(int paramInt1, int paramInt2, int paramInt3)
  {
    return this.sliceZero + paramInt1 * this.sliceStride + this.rowZero + paramInt2 * this.rowStride + this.columnZero + paramInt3 * this.columnStride;
  }
  
  public DoubleMatrix3D like(int paramInt1, int paramInt2, int paramInt3)
  {
    return new DenseDoubleMatrix3D(paramInt1, paramInt2, paramInt3);
  }
  
  protected DoubleMatrix2D like2D(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    return new DenseDoubleMatrix2D(paramInt1, paramInt2, this.elements, paramInt3, paramInt4, paramInt5, paramInt6);
  }
  
  public void setQuick(int paramInt1, int paramInt2, int paramInt3, double paramDouble)
  {
    this.elements[(this.sliceZero + paramInt1 * this.sliceStride + this.rowZero + paramInt2 * this.rowStride + this.columnZero + paramInt3 * this.columnStride)] = paramDouble;
  }
  
  protected DoubleMatrix3D viewSelectionLike(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3)
  {
    return new SelectedDenseDoubleMatrix3D(this.elements, paramArrayOfInt1, paramArrayOfInt2, paramArrayOfInt3, 0);
  }
  
  public void zAssign27Neighbors(DoubleMatrix3D paramDoubleMatrix3D, Double27Function paramDouble27Function)
  {
    if (!(paramDoubleMatrix3D instanceof DenseDoubleMatrix3D))
    {
      super.zAssign27Neighbors(paramDoubleMatrix3D, paramDouble27Function);
      return;
    }
    if (paramDouble27Function == null) {
      throw new NullPointerException("function must not be null.");
    }
    checkShape(paramDoubleMatrix3D);
    int i = this.rows - 1;
    int j = this.columns - 1;
    if ((this.rows < 3) || (this.columns < 3) || (this.slices < 3)) {
      return;
    }
    DenseDoubleMatrix3D localDenseDoubleMatrix3D = (DenseDoubleMatrix3D)paramDoubleMatrix3D;
    int k = this.sliceStride;
    int m = this.rowStride;
    int n = localDenseDoubleMatrix3D.rowStride;
    int i1 = this.columnStride;
    int i2 = localDenseDoubleMatrix3D.columnStride;
    double[] arrayOfDouble1 = this.elements;
    double[] arrayOfDouble2 = localDenseDoubleMatrix3D.elements;
    if ((arrayOfDouble1 == null) || (arrayOfDouble2 == null)) {
      throw new InternalError();
    }
    for (int i3 = 1; i3 < this.slices - 1; i3++)
    {
      int i4 = index(i3, 1, 1);
      int i5 = localDenseDoubleMatrix3D.index(i3, 1, 1);
      for (int i6 = 1; i6 < i; i6++)
      {
        int i7 = i4 - k - m - i1;
        int i8 = i7 + m;
        int i9 = i8 + m;
        int i10 = i7 + k;
        int i11 = i10 + m;
        int i12 = i11 + m;
        int i13 = i10 + k;
        int i14 = i13 + m;
        int i15 = i14 + m;
        double d1 = arrayOfDouble1[i7];
        i7 += i1;
        double d2 = arrayOfDouble1[i7];
        double d4 = arrayOfDouble1[i8];
        i8 += i1;
        double d5 = arrayOfDouble1[i8];
        double d7 = arrayOfDouble1[i9];
        i9 += i1;
        double d8 = arrayOfDouble1[i9];
        double d10 = arrayOfDouble1[i10];
        i10 += i1;
        double d11 = arrayOfDouble1[i10];
        double d13 = arrayOfDouble1[i11];
        i11 += i1;
        double d14 = arrayOfDouble1[i11];
        double d16 = arrayOfDouble1[i12];
        i12 += i1;
        double d17 = arrayOfDouble1[i12];
        double d19 = arrayOfDouble1[i13];
        i13 += i1;
        double d20 = arrayOfDouble1[i13];
        double d22 = arrayOfDouble1[i14];
        i14 += i1;
        double d23 = arrayOfDouble1[i14];
        double d25 = arrayOfDouble1[i15];
        i15 += i1;
        double d26 = arrayOfDouble1[i15];
        int i16 = i5;
        for (int i17 = 1; i17 < j; i17++)
        {
          double d3 = arrayOfDouble1[(i7 += i1)];
          double d6 = arrayOfDouble1[(i8 += i1)];
          double d9 = arrayOfDouble1[(i9 += i1)];
          double d12 = arrayOfDouble1[(i10 += i1)];
          double d15 = arrayOfDouble1[(i11 += i1)];
          double d18 = arrayOfDouble1[(i12 += i1)];
          double d21 = arrayOfDouble1[(i13 += i1)];
          double d24 = arrayOfDouble1[(i14 += i1)];
          double d27 = arrayOfDouble1[(i15 += i1)];
          arrayOfDouble2[i16] = paramDouble27Function.apply(d1, d2, d3, d4, d5, d6, d7, d8, d9, d10, d11, d12, d13, d14, d15, d16, d17, d18, d19, d20, d21, d22, d23, d24, d25, d26, d27);
          i16 += i2;
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
        i4 += m;
        i5 += n;
      }
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.impl.DenseDoubleMatrix3D
 * JD-Core Version:    0.7.0.1
 */