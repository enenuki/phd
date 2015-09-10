package cern.colt.matrix.impl;

import cern.colt.map.AbstractIntDoubleMap;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;

class SelectedSparseDoubleMatrix2D
  extends DoubleMatrix2D
{
  protected AbstractIntDoubleMap elements;
  protected int[] rowOffsets;
  protected int[] columnOffsets;
  protected int offset;
  
  protected SelectedSparseDoubleMatrix2D(int paramInt1, int paramInt2, AbstractIntDoubleMap paramAbstractIntDoubleMap, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt7)
  {
    setUp(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
    this.elements = paramAbstractIntDoubleMap;
    this.rowOffsets = paramArrayOfInt1;
    this.columnOffsets = paramArrayOfInt2;
    this.offset = paramInt7;
    this.isNoView = false;
  }
  
  protected SelectedSparseDoubleMatrix2D(AbstractIntDoubleMap paramAbstractIntDoubleMap, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt)
  {
    this(paramArrayOfInt1.length, paramArrayOfInt2.length, paramAbstractIntDoubleMap, 0, 0, 1, 1, paramArrayOfInt1, paramArrayOfInt2, paramInt);
  }
  
  protected int _columnOffset(int paramInt)
  {
    return this.columnOffsets[paramInt];
  }
  
  protected int _rowOffset(int paramInt)
  {
    return this.rowOffsets[paramInt];
  }
  
  public double getQuick(int paramInt1, int paramInt2)
  {
    return this.elements.get(this.offset + this.rowOffsets[(this.rowZero + paramInt1 * this.rowStride)] + this.columnOffsets[(this.columnZero + paramInt2 * this.columnStride)]);
  }
  
  protected boolean haveSharedCellsRaw(DoubleMatrix2D paramDoubleMatrix2D)
  {
    Object localObject;
    if ((paramDoubleMatrix2D instanceof SelectedSparseDoubleMatrix2D))
    {
      localObject = (SelectedSparseDoubleMatrix2D)paramDoubleMatrix2D;
      return this.elements == ((SelectedSparseDoubleMatrix2D)localObject).elements;
    }
    if ((paramDoubleMatrix2D instanceof SparseDoubleMatrix2D))
    {
      localObject = (SparseDoubleMatrix2D)paramDoubleMatrix2D;
      return this.elements == ((SparseDoubleMatrix2D)localObject).elements;
    }
    return false;
  }
  
  protected int index(int paramInt1, int paramInt2)
  {
    return this.offset + this.rowOffsets[(this.rowZero + paramInt1 * this.rowStride)] + this.columnOffsets[(this.columnZero + paramInt2 * this.columnStride)];
  }
  
  public DoubleMatrix2D like(int paramInt1, int paramInt2)
  {
    return new SparseDoubleMatrix2D(paramInt1, paramInt2);
  }
  
  public DoubleMatrix1D like1D(int paramInt)
  {
    return new SparseDoubleMatrix1D(paramInt);
  }
  
  protected DoubleMatrix1D like1D(int paramInt1, int paramInt2, int paramInt3)
  {
    throw new InternalError();
  }
  
  public void setQuick(int paramInt1, int paramInt2, double paramDouble)
  {
    int i = this.offset + this.rowOffsets[(this.rowZero + paramInt1 * this.rowStride)] + this.columnOffsets[(this.columnZero + paramInt2 * this.columnStride)];
    if (paramDouble == 0.0D) {
      this.elements.removeKey(i);
    } else {
      this.elements.put(i, paramDouble);
    }
  }
  
  protected void setUp(int paramInt1, int paramInt2)
  {
    super.setUp(paramInt1, paramInt2);
    this.rowStride = 1;
    this.columnStride = 1;
    this.offset = 0;
  }
  
  protected AbstractMatrix2D vDice()
  {
    super.vDice();
    int[] arrayOfInt = this.rowOffsets;
    this.rowOffsets = this.columnOffsets;
    this.columnOffsets = arrayOfInt;
    this.isNoView = false;
    return this;
  }
  
  public DoubleMatrix1D viewColumn(int paramInt)
  {
    checkColumn(paramInt);
    int i = this.rows;
    int j = this.rowZero;
    int k = this.rowStride;
    int[] arrayOfInt = this.rowOffsets;
    int m = this.offset + _columnOffset(_columnRank(paramInt));
    return new SelectedSparseDoubleMatrix1D(i, this.elements, j, k, arrayOfInt, m);
  }
  
  public DoubleMatrix1D viewRow(int paramInt)
  {
    checkRow(paramInt);
    int i = this.columns;
    int j = this.columnZero;
    int k = this.columnStride;
    int[] arrayOfInt = this.columnOffsets;
    int m = this.offset + _rowOffset(_rowRank(paramInt));
    return new SelectedSparseDoubleMatrix1D(i, this.elements, j, k, arrayOfInt, m);
  }
  
  protected DoubleMatrix2D viewSelectionLike(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    return new SelectedSparseDoubleMatrix2D(this.elements, paramArrayOfInt1, paramArrayOfInt2, this.offset);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.impl.SelectedSparseDoubleMatrix2D
 * JD-Core Version:    0.7.0.1
 */