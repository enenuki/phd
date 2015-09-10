package cern.colt.matrix.impl;

import cern.colt.matrix.ObjectMatrix1D;
import cern.colt.matrix.ObjectMatrix2D;

class SelectedDenseObjectMatrix2D
  extends ObjectMatrix2D
{
  protected Object[] elements;
  protected int[] rowOffsets;
  protected int[] columnOffsets;
  protected int offset;
  
  protected SelectedDenseObjectMatrix2D(Object[] paramArrayOfObject, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt)
  {
    this(paramArrayOfInt1.length, paramArrayOfInt2.length, paramArrayOfObject, 0, 0, 1, 1, paramArrayOfInt1, paramArrayOfInt2, paramInt);
  }
  
  protected SelectedDenseObjectMatrix2D(int paramInt1, int paramInt2, Object[] paramArrayOfObject, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt7)
  {
    setUp(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
    this.elements = paramArrayOfObject;
    this.rowOffsets = paramArrayOfInt1;
    this.columnOffsets = paramArrayOfInt2;
    this.offset = paramInt7;
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
  
  public Object getQuick(int paramInt1, int paramInt2)
  {
    return this.elements[(this.offset + this.rowOffsets[(this.rowZero + paramInt1 * this.rowStride)] + this.columnOffsets[(this.columnZero + paramInt2 * this.columnStride)])];
  }
  
  protected boolean haveSharedCellsRaw(ObjectMatrix2D paramObjectMatrix2D)
  {
    Object localObject;
    if ((paramObjectMatrix2D instanceof SelectedDenseObjectMatrix2D))
    {
      localObject = (SelectedDenseObjectMatrix2D)paramObjectMatrix2D;
      return this.elements == ((SelectedDenseObjectMatrix2D)localObject).elements;
    }
    if ((paramObjectMatrix2D instanceof DenseObjectMatrix2D))
    {
      localObject = (DenseObjectMatrix2D)paramObjectMatrix2D;
      return this.elements == ((DenseObjectMatrix2D)localObject).elements;
    }
    return false;
  }
  
  protected int index(int paramInt1, int paramInt2)
  {
    return this.offset + this.rowOffsets[(this.rowZero + paramInt1 * this.rowStride)] + this.columnOffsets[(this.columnZero + paramInt2 * this.columnStride)];
  }
  
  public ObjectMatrix2D like(int paramInt1, int paramInt2)
  {
    return new DenseObjectMatrix2D(paramInt1, paramInt2);
  }
  
  public ObjectMatrix1D like1D(int paramInt)
  {
    return new DenseObjectMatrix1D(paramInt);
  }
  
  protected ObjectMatrix1D like1D(int paramInt1, int paramInt2, int paramInt3)
  {
    throw new InternalError();
  }
  
  public void setQuick(int paramInt1, int paramInt2, Object paramObject)
  {
    this.elements[(this.offset + this.rowOffsets[(this.rowZero + paramInt1 * this.rowStride)] + this.columnOffsets[(this.columnZero + paramInt2 * this.columnStride)])] = paramObject;
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
  
  public ObjectMatrix1D viewColumn(int paramInt)
  {
    checkColumn(paramInt);
    int i = this.rows;
    int j = this.rowZero;
    int k = this.rowStride;
    int[] arrayOfInt = this.rowOffsets;
    int m = this.offset + _columnOffset(_columnRank(paramInt));
    return new SelectedDenseObjectMatrix1D(i, this.elements, j, k, arrayOfInt, m);
  }
  
  public ObjectMatrix1D viewRow(int paramInt)
  {
    checkRow(paramInt);
    int i = this.columns;
    int j = this.columnZero;
    int k = this.columnStride;
    int[] arrayOfInt = this.columnOffsets;
    int m = this.offset + _rowOffset(_rowRank(paramInt));
    return new SelectedDenseObjectMatrix1D(i, this.elements, j, k, arrayOfInt, m);
  }
  
  protected ObjectMatrix2D viewSelectionLike(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    return new SelectedDenseObjectMatrix2D(this.elements, paramArrayOfInt1, paramArrayOfInt2, this.offset);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.impl.SelectedDenseObjectMatrix2D
 * JD-Core Version:    0.7.0.1
 */