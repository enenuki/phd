package cern.colt.matrix.impl;

import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.DoubleMatrix3D;

class SelectedDenseDoubleMatrix3D
  extends DoubleMatrix3D
{
  protected double[] elements;
  protected int[] sliceOffsets;
  protected int[] rowOffsets;
  protected int[] columnOffsets;
  protected int offset;
  
  protected SelectedDenseDoubleMatrix3D(double[] paramArrayOfDouble, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3, int paramInt)
  {
    int i = paramArrayOfInt1.length;
    int j = paramArrayOfInt2.length;
    int k = paramArrayOfInt3.length;
    setUp(i, j, k);
    this.elements = paramArrayOfDouble;
    this.sliceOffsets = paramArrayOfInt1;
    this.rowOffsets = paramArrayOfInt2;
    this.columnOffsets = paramArrayOfInt3;
    this.offset = paramInt;
    this.isNoView = false;
  }
  
  protected int _columnOffset(int paramInt)
  {
    return this.columnOffsets[paramInt];
  }
  
  protected int _rowOffset(int paramInt)
  {
    return this.rowOffsets[paramInt];
  }
  
  protected int _sliceOffset(int paramInt)
  {
    return this.sliceOffsets[paramInt];
  }
  
  public double getQuick(int paramInt1, int paramInt2, int paramInt3)
  {
    return this.elements[(this.offset + this.sliceOffsets[(this.sliceZero + paramInt1 * this.sliceStride)] + this.rowOffsets[(this.rowZero + paramInt2 * this.rowStride)] + this.columnOffsets[(this.columnZero + paramInt3 * this.columnStride)])];
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
    return this.offset + this.sliceOffsets[(this.sliceZero + paramInt1 * this.sliceStride)] + this.rowOffsets[(this.rowZero + paramInt2 * this.rowStride)] + this.columnOffsets[(this.columnZero + paramInt3 * this.columnStride)];
  }
  
  public DoubleMatrix3D like(int paramInt1, int paramInt2, int paramInt3)
  {
    return new DenseDoubleMatrix3D(paramInt1, paramInt2, paramInt3);
  }
  
  protected DoubleMatrix2D like2D(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    throw new InternalError();
  }
  
  public void setQuick(int paramInt1, int paramInt2, int paramInt3, double paramDouble)
  {
    this.elements[(this.offset + this.sliceOffsets[(this.sliceZero + paramInt1 * this.sliceStride)] + this.rowOffsets[(this.rowZero + paramInt2 * this.rowStride)] + this.columnOffsets[(this.columnZero + paramInt3 * this.columnStride)])] = paramDouble;
  }
  
  protected void setUp(int paramInt1, int paramInt2, int paramInt3)
  {
    super.setUp(paramInt1, paramInt2, paramInt3);
    this.sliceStride = 1;
    this.rowStride = 1;
    this.columnStride = 1;
    this.offset = 0;
  }
  
  protected AbstractMatrix3D vDice(int paramInt1, int paramInt2, int paramInt3)
  {
    super.vDice(paramInt1, paramInt2, paramInt3);
    int[][] arrayOfInt = new int[3][];
    arrayOfInt[0] = this.sliceOffsets;
    arrayOfInt[1] = this.rowOffsets;
    arrayOfInt[2] = this.columnOffsets;
    this.sliceOffsets = arrayOfInt[paramInt1];
    this.rowOffsets = arrayOfInt[paramInt2];
    this.columnOffsets = arrayOfInt[paramInt3];
    return this;
  }
  
  public DoubleMatrix2D viewColumn(int paramInt)
  {
    checkColumn(paramInt);
    int i = this.slices;
    int j = this.rows;
    int k = this.sliceZero;
    int m = this.rowZero;
    int n = this.offset + _columnOffset(_columnRank(paramInt));
    int i1 = this.sliceStride;
    int i2 = this.rowStride;
    int[] arrayOfInt1 = this.sliceOffsets;
    int[] arrayOfInt2 = this.rowOffsets;
    return new SelectedDenseDoubleMatrix2D(i, j, this.elements, k, m, i1, i2, arrayOfInt1, arrayOfInt2, n);
  }
  
  public DoubleMatrix2D viewRow(int paramInt)
  {
    checkRow(paramInt);
    int i = this.slices;
    int j = this.columns;
    int k = this.sliceZero;
    int m = this.columnZero;
    int n = this.offset + _rowOffset(_rowRank(paramInt));
    int i1 = this.sliceStride;
    int i2 = this.columnStride;
    int[] arrayOfInt1 = this.sliceOffsets;
    int[] arrayOfInt2 = this.columnOffsets;
    return new SelectedDenseDoubleMatrix2D(i, j, this.elements, k, m, i1, i2, arrayOfInt1, arrayOfInt2, n);
  }
  
  protected DoubleMatrix3D viewSelectionLike(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3)
  {
    return new SelectedDenseDoubleMatrix3D(this.elements, paramArrayOfInt1, paramArrayOfInt2, paramArrayOfInt3, this.offset);
  }
  
  public DoubleMatrix2D viewSlice(int paramInt)
  {
    checkSlice(paramInt);
    int i = this.rows;
    int j = this.columns;
    int k = this.rowZero;
    int m = this.columnZero;
    int n = this.offset + _sliceOffset(_sliceRank(paramInt));
    int i1 = this.rowStride;
    int i2 = this.columnStride;
    int[] arrayOfInt1 = this.rowOffsets;
    int[] arrayOfInt2 = this.columnOffsets;
    return new SelectedDenseDoubleMatrix2D(i, j, this.elements, k, m, i1, i2, arrayOfInt1, arrayOfInt2, n);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.impl.SelectedDenseDoubleMatrix3D
 * JD-Core Version:    0.7.0.1
 */