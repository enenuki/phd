package cern.colt.matrix.impl;

import cern.colt.function.Double9Function;
import cern.colt.function.DoubleDoubleFunction;
import cern.colt.function.DoubleFunction;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.jet.math.Functions;
import cern.jet.math.Mult;
import cern.jet.math.PlusMult;

public class DenseDoubleMatrix2D
  extends DoubleMatrix2D
{
  static final long serialVersionUID = 1020177651L;
  protected double[] elements;
  
  public DenseDoubleMatrix2D(double[][] paramArrayOfDouble)
  {
    this(paramArrayOfDouble.length, paramArrayOfDouble.length == 0 ? 0 : paramArrayOfDouble[0].length);
    assign(paramArrayOfDouble);
  }
  
  public DenseDoubleMatrix2D(int paramInt1, int paramInt2)
  {
    setUp(paramInt1, paramInt2);
    this.elements = new double[paramInt1 * paramInt2];
  }
  
  protected DenseDoubleMatrix2D(int paramInt1, int paramInt2, double[] paramArrayOfDouble, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    setUp(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
    this.elements = paramArrayOfDouble;
    this.isNoView = false;
  }
  
  public DoubleMatrix2D assign(double[][] paramArrayOfDouble)
  {
    if (this.isNoView)
    {
      if (paramArrayOfDouble.length != this.rows) {
        throw new IllegalArgumentException("Must have same number of rows: rows=" + paramArrayOfDouble.length + "rows()=" + rows());
      }
      int i = this.columns * (this.rows - 1);
      int j = this.rows;
      for (;;)
      {
        j--;
        if (j < 0) {
          break;
        }
        double[] arrayOfDouble = paramArrayOfDouble[j];
        if (arrayOfDouble.length != this.columns) {
          throw new IllegalArgumentException("Must have same number of columns in every row: columns=" + arrayOfDouble.length + "columns()=" + columns());
        }
        System.arraycopy(arrayOfDouble, 0, this.elements, i, this.columns);
        i -= this.columns;
      }
    }
    super.assign(paramArrayOfDouble);
    return this;
  }
  
  public DoubleMatrix2D assign(double paramDouble)
  {
    double[] arrayOfDouble = this.elements;
    int i = index(0, 0);
    int j = this.columnStride;
    int k = this.rowStride;
    int m = this.rows;
    for (;;)
    {
      m--;
      if (m < 0) {
        break;
      }
      int n = i;
      int i1 = this.columns;
      for (;;)
      {
        i1--;
        if (i1 < 0) {
          break;
        }
        arrayOfDouble[n] = paramDouble;
        n += j;
      }
      i += k;
    }
    return this;
  }
  
  public DoubleMatrix2D assign(DoubleFunction paramDoubleFunction)
  {
    double[] arrayOfDouble = this.elements;
    if (arrayOfDouble == null) {
      throw new InternalError();
    }
    int i = index(0, 0);
    int j = this.columnStride;
    int k = this.rowStride;
    int i1;
    if ((paramDoubleFunction instanceof Mult))
    {
      double d = ((Mult)paramDoubleFunction).multiplicator;
      if (d == 1.0D) {
        return this;
      }
      if (d == 0.0D) {
        return assign(0.0D);
      }
      i1 = this.rows;
      for (;;)
      {
        i1--;
        if (i1 < 0) {
          break;
        }
        int i2 = i;
        int i3 = this.columns;
        for (;;)
        {
          i3--;
          if (i3 < 0) {
            break;
          }
          arrayOfDouble[i2] *= d;
          i2 += j;
        }
        i += k;
      }
    }
    int m = this.rows;
    for (;;)
    {
      m--;
      if (m < 0) {
        break;
      }
      int n = i;
      i1 = this.columns;
      for (;;)
      {
        i1--;
        if (i1 < 0) {
          break;
        }
        arrayOfDouble[n] = paramDoubleFunction.apply(arrayOfDouble[n]);
        n += j;
      }
      i += k;
    }
    return this;
  }
  
  public DoubleMatrix2D assign(DoubleMatrix2D paramDoubleMatrix2D)
  {
    if (!(paramDoubleMatrix2D instanceof DenseDoubleMatrix2D)) {
      return super.assign(paramDoubleMatrix2D);
    }
    DenseDoubleMatrix2D localDenseDoubleMatrix2D = (DenseDoubleMatrix2D)paramDoubleMatrix2D;
    if (localDenseDoubleMatrix2D == this) {
      return this;
    }
    checkShape(localDenseDoubleMatrix2D);
    if ((this.isNoView) && (localDenseDoubleMatrix2D.isNoView))
    {
      System.arraycopy(localDenseDoubleMatrix2D.elements, 0, this.elements, 0, this.elements.length);
      return this;
    }
    if (haveSharedCells(localDenseDoubleMatrix2D))
    {
      localObject = localDenseDoubleMatrix2D.copy();
      if (!(localObject instanceof DenseDoubleMatrix2D)) {
        return super.assign(localDenseDoubleMatrix2D);
      }
      localDenseDoubleMatrix2D = (DenseDoubleMatrix2D)localObject;
    }
    Object localObject = this.elements;
    double[] arrayOfDouble = localDenseDoubleMatrix2D.elements;
    if ((localObject == null) || (arrayOfDouble == null)) {
      throw new InternalError();
    }
    int i = this.columnStride;
    int j = localDenseDoubleMatrix2D.columnStride;
    int k = this.rowStride;
    int m = localDenseDoubleMatrix2D.rowStride;
    int n = localDenseDoubleMatrix2D.index(0, 0);
    int i1 = index(0, 0);
    int i2 = this.rows;
    for (;;)
    {
      i2--;
      if (i2 < 0) {
        break;
      }
      int i3 = i1;
      int i4 = n;
      int i5 = this.columns;
      for (;;)
      {
        i5--;
        if (i5 < 0) {
          break;
        }
        localObject[i3] = arrayOfDouble[i4];
        i3 += i;
        i4 += j;
      }
      i1 += k;
      n += m;
    }
    return this;
  }
  
  public DoubleMatrix2D assign(DoubleMatrix2D paramDoubleMatrix2D, DoubleDoubleFunction paramDoubleDoubleFunction)
  {
    if (!(paramDoubleMatrix2D instanceof DenseDoubleMatrix2D)) {
      return super.assign(paramDoubleMatrix2D, paramDoubleDoubleFunction);
    }
    DenseDoubleMatrix2D localDenseDoubleMatrix2D = (DenseDoubleMatrix2D)paramDoubleMatrix2D;
    checkShape(paramDoubleMatrix2D);
    double[] arrayOfDouble1 = this.elements;
    double[] arrayOfDouble2 = localDenseDoubleMatrix2D.elements;
    if ((arrayOfDouble1 == null) || (arrayOfDouble2 == null)) {
      throw new InternalError();
    }
    int i = this.columnStride;
    int j = localDenseDoubleMatrix2D.columnStride;
    int k = this.rowStride;
    int m = localDenseDoubleMatrix2D.rowStride;
    int n = localDenseDoubleMatrix2D.index(0, 0);
    int i1 = index(0, 0);
    int i2;
    int i4;
    int i5;
    int i6;
    if (paramDoubleDoubleFunction == Functions.mult)
    {
      i2 = this.rows;
      for (;;)
      {
        i2--;
        if (i2 < 0) {
          break;
        }
        i4 = i1;
        i5 = n;
        i6 = this.columns;
        for (;;)
        {
          i6--;
          if (i6 < 0) {
            break;
          }
          arrayOfDouble1[i4] *= arrayOfDouble2[i5];
          i4 += i;
          i5 += j;
        }
        i1 += k;
        n += m;
      }
    }
    if (paramDoubleDoubleFunction == Functions.div)
    {
      i2 = this.rows;
      for (;;)
      {
        i2--;
        if (i2 < 0) {
          break;
        }
        i4 = i1;
        i5 = n;
        i6 = this.columns;
        for (;;)
        {
          i6--;
          if (i6 < 0) {
            break;
          }
          arrayOfDouble1[i4] /= arrayOfDouble2[i5];
          i4 += i;
          i5 += j;
        }
        i1 += k;
        n += m;
      }
    }
    if ((paramDoubleDoubleFunction instanceof PlusMult))
    {
      double d = ((PlusMult)paramDoubleDoubleFunction).multiplicator;
      if (d == 0.0D) {
        return this;
      }
      int i7;
      int i8;
      if (d == 1.0D)
      {
        i5 = this.rows;
        for (;;)
        {
          i5--;
          if (i5 < 0) {
            break;
          }
          i6 = i1;
          i7 = n;
          i8 = this.columns;
          for (;;)
          {
            i8--;
            if (i8 < 0) {
              break;
            }
            arrayOfDouble1[i6] += arrayOfDouble2[i7];
            i6 += i;
            i7 += j;
          }
          i1 += k;
          n += m;
        }
      }
      if (d == -1.0D)
      {
        i5 = this.rows;
        for (;;)
        {
          i5--;
          if (i5 < 0) {
            break;
          }
          i6 = i1;
          i7 = n;
          i8 = this.columns;
          for (;;)
          {
            i8--;
            if (i8 < 0) {
              break;
            }
            arrayOfDouble1[i6] -= arrayOfDouble2[i7];
            i6 += i;
            i7 += j;
          }
          i1 += k;
          n += m;
        }
      }
      i5 = this.rows;
      for (;;)
      {
        i5--;
        if (i5 < 0) {
          break;
        }
        i6 = i1;
        i7 = n;
        i8 = this.columns;
        for (;;)
        {
          i8--;
          if (i8 < 0) {
            break;
          }
          arrayOfDouble1[i6] += d * arrayOfDouble2[i7];
          i6 += i;
          i7 += j;
        }
        i1 += k;
        n += m;
      }
    }
    int i3 = this.rows;
    for (;;)
    {
      i3--;
      if (i3 < 0) {
        break;
      }
      i4 = i1;
      i5 = n;
      i6 = this.columns;
      for (;;)
      {
        i6--;
        if (i6 < 0) {
          break;
        }
        arrayOfDouble1[i4] = paramDoubleDoubleFunction.apply(arrayOfDouble1[i4], arrayOfDouble2[i5]);
        i4 += i;
        i5 += j;
      }
      i1 += k;
      n += m;
    }
    return this;
  }
  
  public double getQuick(int paramInt1, int paramInt2)
  {
    return this.elements[(this.rowZero + paramInt1 * this.rowStride + this.columnZero + paramInt2 * this.columnStride)];
  }
  
  protected boolean haveSharedCellsRaw(DoubleMatrix2D paramDoubleMatrix2D)
  {
    Object localObject;
    if ((paramDoubleMatrix2D instanceof SelectedDenseDoubleMatrix2D))
    {
      localObject = (SelectedDenseDoubleMatrix2D)paramDoubleMatrix2D;
      return this.elements == ((SelectedDenseDoubleMatrix2D)localObject).elements;
    }
    if ((paramDoubleMatrix2D instanceof DenseDoubleMatrix2D))
    {
      localObject = (DenseDoubleMatrix2D)paramDoubleMatrix2D;
      return this.elements == ((DenseDoubleMatrix2D)localObject).elements;
    }
    return false;
  }
  
  protected int index(int paramInt1, int paramInt2)
  {
    return this.rowZero + paramInt1 * this.rowStride + this.columnZero + paramInt2 * this.columnStride;
  }
  
  public DoubleMatrix2D like(int paramInt1, int paramInt2)
  {
    return new DenseDoubleMatrix2D(paramInt1, paramInt2);
  }
  
  public DoubleMatrix1D like1D(int paramInt)
  {
    return new DenseDoubleMatrix1D(paramInt);
  }
  
  protected DoubleMatrix1D like1D(int paramInt1, int paramInt2, int paramInt3)
  {
    return new DenseDoubleMatrix1D(paramInt1, this.elements, paramInt2, paramInt3);
  }
  
  public void setQuick(int paramInt1, int paramInt2, double paramDouble)
  {
    this.elements[(this.rowZero + paramInt1 * this.rowStride + this.columnZero + paramInt2 * this.columnStride)] = paramDouble;
  }
  
  protected DoubleMatrix2D viewSelectionLike(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    return new SelectedDenseDoubleMatrix2D(this.elements, paramArrayOfInt1, paramArrayOfInt2, 0);
  }
  
  public void zAssign8Neighbors(DoubleMatrix2D paramDoubleMatrix2D, Double9Function paramDouble9Function)
  {
    if (!(paramDoubleMatrix2D instanceof DenseDoubleMatrix2D))
    {
      super.zAssign8Neighbors(paramDoubleMatrix2D, paramDouble9Function);
      return;
    }
    if (paramDouble9Function == null) {
      throw new NullPointerException("function must not be null.");
    }
    checkShape(paramDoubleMatrix2D);
    int i = this.rows - 1;
    int j = this.columns - 1;
    if ((this.rows < 3) || (this.columns < 3)) {
      return;
    }
    DenseDoubleMatrix2D localDenseDoubleMatrix2D = (DenseDoubleMatrix2D)paramDoubleMatrix2D;
    int k = this.rowStride;
    int m = localDenseDoubleMatrix2D.rowStride;
    int n = this.columnStride;
    int i1 = localDenseDoubleMatrix2D.columnStride;
    double[] arrayOfDouble1 = this.elements;
    double[] arrayOfDouble2 = localDenseDoubleMatrix2D.elements;
    if ((arrayOfDouble1 == null) || (arrayOfDouble2 == null)) {
      throw new InternalError();
    }
    int i2 = index(1, 1);
    int i3 = localDenseDoubleMatrix2D.index(1, 1);
    for (int i4 = 1; i4 < i; i4++)
    {
      int i5 = i3;
      int i6 = i2 - k - n;
      int i7 = i6 + k;
      int i8 = i7 + k;
      double d1 = arrayOfDouble1[i6];
      i6 += n;
      double d2 = arrayOfDouble1[i6];
      double d4 = arrayOfDouble1[i7];
      i7 += n;
      double d5 = arrayOfDouble1[i7];
      double d7 = arrayOfDouble1[i8];
      i8 += n;
      double d8 = arrayOfDouble1[i8];
      for (int i9 = 1; i9 < j; i9++)
      {
        double d3 = arrayOfDouble1[(i6 += n)];
        double d6 = arrayOfDouble1[(i7 += n)];
        double d9 = arrayOfDouble1[(i8 += n)];
        arrayOfDouble2[i5] = paramDouble9Function.apply(d1, d2, d3, d4, d5, d6, d7, d8, d9);
        i5 += i1;
        d1 = d2;
        d2 = d3;
        d4 = d5;
        d5 = d6;
        d7 = d8;
        d8 = d9;
      }
      i2 += k;
      i3 += m;
    }
  }
  
  public DoubleMatrix1D zMult(DoubleMatrix1D paramDoubleMatrix1D1, DoubleMatrix1D paramDoubleMatrix1D2, double paramDouble1, double paramDouble2, boolean paramBoolean)
  {
    if (paramBoolean) {
      return viewDice().zMult(paramDoubleMatrix1D1, paramDoubleMatrix1D2, paramDouble1, paramDouble2, false);
    }
    if (paramDoubleMatrix1D2 == null) {
      paramDoubleMatrix1D2 = new DenseDoubleMatrix1D(this.rows);
    }
    if ((!(paramDoubleMatrix1D1 instanceof DenseDoubleMatrix1D)) || (!(paramDoubleMatrix1D2 instanceof DenseDoubleMatrix1D))) {
      return super.zMult(paramDoubleMatrix1D1, paramDoubleMatrix1D2, paramDouble1, paramDouble2, paramBoolean);
    }
    if ((this.columns != paramDoubleMatrix1D1.size) || (this.rows > paramDoubleMatrix1D2.size)) {
      throw new IllegalArgumentException("Incompatible args: " + toStringShort() + ", " + paramDoubleMatrix1D1.toStringShort() + ", " + paramDoubleMatrix1D2.toStringShort());
    }
    DenseDoubleMatrix1D localDenseDoubleMatrix1D1 = (DenseDoubleMatrix1D)paramDoubleMatrix1D1;
    DenseDoubleMatrix1D localDenseDoubleMatrix1D2 = (DenseDoubleMatrix1D)paramDoubleMatrix1D2;
    double[] arrayOfDouble1 = this.elements;
    double[] arrayOfDouble2 = localDenseDoubleMatrix1D1.elements;
    double[] arrayOfDouble3 = localDenseDoubleMatrix1D2.elements;
    if ((arrayOfDouble1 == null) || (arrayOfDouble2 == null) || (arrayOfDouble3 == null)) {
      throw new InternalError();
    }
    int i = this.columnStride;
    int j = localDenseDoubleMatrix1D1.stride;
    int k = localDenseDoubleMatrix1D2.stride;
    int m = index(0, 0);
    int n = localDenseDoubleMatrix1D1.index(0);
    int i1 = localDenseDoubleMatrix1D2.index(0);
    int i2 = this.columns;
    int i3 = this.rows;
    for (;;)
    {
      i3--;
      if (i3 < 0) {
        break;
      }
      double d = 0.0D;
      int i4 = m - i;
      int i5 = n - j;
      int i6 = i2 % 4;
      for (;;)
      {
        i6--;
        if (i6 < 0) {
          break;
        }
        d += arrayOfDouble1[(i4 += i)] * arrayOfDouble2[(i5 += j)];
      }
      i6 = i2 / 4;
      for (;;)
      {
        i6--;
        if (i6 < 0) {
          break;
        }
        d += arrayOfDouble1[(i4 += i)] * arrayOfDouble2[(i5 += j)] + arrayOfDouble1[(i4 += i)] * arrayOfDouble2[(i5 += j)] + arrayOfDouble1[(i4 += i)] * arrayOfDouble2[(i5 += j)] + arrayOfDouble1[(i4 += i)] * arrayOfDouble2[(i5 += j)];
      }
      arrayOfDouble3[i1] = (paramDouble1 * d + paramDouble2 * arrayOfDouble3[i1]);
      m += this.rowStride;
      i1 += k;
    }
    return paramDoubleMatrix1D2;
  }
  
  public DoubleMatrix2D zMult(DoubleMatrix2D paramDoubleMatrix2D1, DoubleMatrix2D paramDoubleMatrix2D2, double paramDouble1, double paramDouble2, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (paramBoolean1) {
      return viewDice().zMult(paramDoubleMatrix2D1, paramDoubleMatrix2D2, paramDouble1, paramDouble2, false, paramBoolean2);
    }
    if (((paramDoubleMatrix2D1 instanceof SparseDoubleMatrix2D)) || ((paramDoubleMatrix2D1 instanceof RCDoubleMatrix2D)))
    {
      if (paramDoubleMatrix2D2 == null) {
        return paramDoubleMatrix2D1.zMult(this, null, paramDouble1, paramDouble2, !paramBoolean2, true).viewDice();
      }
      paramDoubleMatrix2D1.zMult(this, paramDoubleMatrix2D2.viewDice(), paramDouble1, paramDouble2, !paramBoolean2, true);
      return paramDoubleMatrix2D2;
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
    if (!(paramDoubleMatrix2D2 instanceof DenseDoubleMatrix2D)) {
      return super.zMult(paramDoubleMatrix2D1, paramDoubleMatrix2D2, paramDouble1, paramDouble2, paramBoolean1, paramBoolean2);
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
    DenseDoubleMatrix2D localDenseDoubleMatrix2D1 = (DenseDoubleMatrix2D)paramDoubleMatrix2D1;
    DenseDoubleMatrix2D localDenseDoubleMatrix2D2 = (DenseDoubleMatrix2D)paramDoubleMatrix2D2;
    double[] arrayOfDouble1 = this.elements;
    double[] arrayOfDouble2 = localDenseDoubleMatrix2D1.elements;
    double[] arrayOfDouble3 = localDenseDoubleMatrix2D2.elements;
    if ((arrayOfDouble1 == null) || (arrayOfDouble2 == null) || (arrayOfDouble3 == null)) {
      throw new InternalError();
    }
    int m = this.columnStride;
    int n = localDenseDoubleMatrix2D1.columnStride;
    int i1 = localDenseDoubleMatrix2D2.columnStride;
    int i2 = this.rowStride;
    int i3 = localDenseDoubleMatrix2D1.rowStride;
    int i4 = localDenseDoubleMatrix2D2.rowStride;
    int i5 = (30000 - j) / (j + 1);
    if (i5 <= 0) {
      i5 = 1;
    }
    int i6 = i / i5;
    int i7 = 0;
    if (i % i5 != 0) {
      i6++;
    }
    for (;;)
    {
      i6--;
      if (i6 < 0) {
        break;
      }
      int i8 = localDenseDoubleMatrix2D1.index(0, 0);
      int i9 = index(i7, 0);
      int i10 = localDenseDoubleMatrix2D2.index(i7, 0);
      i7 += i5;
      if (i6 == 0) {
        i5 += i - i7;
      }
      int i11 = k;
      for (;;)
      {
        i11--;
        if (i11 < 0) {
          break;
        }
        int i12 = i9;
        int i13 = i10;
        int i14 = i5;
        for (;;)
        {
          i14--;
          if (i14 < 0) {
            break;
          }
          int i15 = i12;
          int i16 = i8;
          double d = 0.0D;
          i15 -= m;
          i16 -= i3;
          int i17 = j % 4;
          for (;;)
          {
            i17--;
            if (i17 < 0) {
              break;
            }
            d += arrayOfDouble1[(i15 += m)] * arrayOfDouble2[(i16 += i3)];
          }
          i17 = j / 4;
          for (;;)
          {
            i17--;
            if (i17 < 0) {
              break;
            }
            d += arrayOfDouble1[(i15 += m)] * arrayOfDouble2[(i16 += i3)] + arrayOfDouble1[(i15 += m)] * arrayOfDouble2[(i16 += i3)] + arrayOfDouble1[(i15 += m)] * arrayOfDouble2[(i16 += i3)] + arrayOfDouble1[(i15 += m)] * arrayOfDouble2[(i16 += i3)];
          }
          arrayOfDouble3[i13] = (paramDouble1 * d + paramDouble2 * arrayOfDouble3[i13]);
          i12 += i2;
          i13 += i4;
        }
        i8 += n;
        i10 += i1;
      }
    }
    return paramDoubleMatrix2D2;
  }
  
  public double zSum()
  {
    double d = 0.0D;
    double[] arrayOfDouble = this.elements;
    if (arrayOfDouble == null) {
      throw new InternalError();
    }
    int i = index(0, 0);
    int j = this.columnStride;
    int k = this.rowStride;
    int m = this.rows;
    for (;;)
    {
      m--;
      if (m < 0) {
        break;
      }
      int n = i;
      int i1 = this.columns;
      for (;;)
      {
        i1--;
        if (i1 < 0) {
          break;
        }
        d += arrayOfDouble[n];
        n += j;
      }
      i += k;
    }
    return d;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.impl.DenseDoubleMatrix2D
 * JD-Core Version:    0.7.0.1
 */