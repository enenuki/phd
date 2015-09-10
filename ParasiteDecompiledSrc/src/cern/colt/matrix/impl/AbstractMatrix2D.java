package cern.colt.matrix.impl;

public abstract class AbstractMatrix2D
  extends AbstractMatrix
{
  protected int columns;
  protected int rows;
  protected int rowStride;
  protected int columnStride;
  protected int rowZero;
  protected int columnZero;
  
  protected int _columnOffset(int paramInt)
  {
    return paramInt;
  }
  
  protected int _columnRank(int paramInt)
  {
    return this.columnZero + paramInt * this.columnStride;
  }
  
  protected int _rowOffset(int paramInt)
  {
    return paramInt;
  }
  
  protected int _rowRank(int paramInt)
  {
    return this.rowZero + paramInt * this.rowStride;
  }
  
  protected void checkBox(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((paramInt2 < 0) || (paramInt4 < 0) || (paramInt2 + paramInt4 > this.columns) || (paramInt1 < 0) || (paramInt3 < 0) || (paramInt1 + paramInt3 > this.rows)) {
      throw new IndexOutOfBoundsException(toStringShort() + ", column:" + paramInt2 + ", row:" + paramInt1 + " ,width:" + paramInt4 + ", height:" + paramInt3);
    }
  }
  
  protected void checkColumn(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this.columns)) {
      throw new IndexOutOfBoundsException("Attempted to access " + toStringShort() + " at column=" + paramInt);
    }
  }
  
  protected void checkColumnIndexes(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt.length;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      int j = paramArrayOfInt[i];
      if ((j < 0) || (j >= this.columns)) {
        checkColumn(j);
      }
    }
  }
  
  protected void checkRow(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this.rows)) {
      throw new IndexOutOfBoundsException("Attempted to access " + toStringShort() + " at row=" + paramInt);
    }
  }
  
  protected void checkRowIndexes(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt.length;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      int j = paramArrayOfInt[i];
      if ((j < 0) || (j >= this.rows)) {
        checkRow(j);
      }
    }
  }
  
  public void checkShape(AbstractMatrix2D paramAbstractMatrix2D)
  {
    if ((this.columns != paramAbstractMatrix2D.columns) || (this.rows != paramAbstractMatrix2D.rows)) {
      throw new IllegalArgumentException("Incompatible dimensions: " + toStringShort() + " and " + paramAbstractMatrix2D.toStringShort());
    }
  }
  
  public void checkShape(AbstractMatrix2D paramAbstractMatrix2D1, AbstractMatrix2D paramAbstractMatrix2D2)
  {
    if ((this.columns != paramAbstractMatrix2D1.columns) || (this.rows != paramAbstractMatrix2D1.rows) || (this.columns != paramAbstractMatrix2D2.columns) || (this.rows != paramAbstractMatrix2D2.rows)) {
      throw new IllegalArgumentException("Incompatible dimensions: " + toStringShort() + ", " + paramAbstractMatrix2D1.toStringShort() + ", " + paramAbstractMatrix2D2.toStringShort());
    }
  }
  
  public int columns()
  {
    return this.columns;
  }
  
  protected int index(int paramInt1, int paramInt2)
  {
    return _rowOffset(_rowRank(paramInt1)) + _columnOffset(_columnRank(paramInt2));
  }
  
  public int rows()
  {
    return this.rows;
  }
  
  protected void setUp(int paramInt1, int paramInt2)
  {
    setUp(paramInt1, paramInt2, 0, 0, paramInt2, 1);
  }
  
  protected void setUp(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    if ((paramInt1 < 0) || (paramInt2 < 0)) {
      throw new IllegalArgumentException("negative size");
    }
    this.rows = paramInt1;
    this.columns = paramInt2;
    this.rowZero = paramInt3;
    this.columnZero = paramInt4;
    this.rowStride = paramInt5;
    this.columnStride = paramInt6;
    this.isNoView = true;
    if (paramInt2 * paramInt1 > 2147483647.0D) {
      throw new IllegalArgumentException("matrix too large");
    }
  }
  
  public int size()
  {
    return this.rows * this.columns;
  }
  
  public String toStringShort()
  {
    return AbstractFormatter.shape(this);
  }
  
  protected AbstractMatrix2D vColumnFlip()
  {
    if (this.columns > 0)
    {
      this.columnZero += (this.columns - 1) * this.columnStride;
      this.columnStride = (-this.columnStride);
      this.isNoView = false;
    }
    return this;
  }
  
  protected AbstractMatrix2D vDice()
  {
    int i = this.rows;
    this.rows = this.columns;
    this.columns = i;
    i = this.rowStride;
    this.rowStride = this.columnStride;
    this.columnStride = i;
    i = this.rowZero;
    this.rowZero = this.columnZero;
    this.columnZero = i;
    this.isNoView = false;
    return this;
  }
  
  protected AbstractMatrix2D vPart(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    checkBox(paramInt1, paramInt2, paramInt3, paramInt4);
    this.rowZero += this.rowStride * paramInt1;
    this.columnZero += this.columnStride * paramInt2;
    this.rows = paramInt3;
    this.columns = paramInt4;
    this.isNoView = false;
    return this;
  }
  
  protected AbstractMatrix2D vRowFlip()
  {
    if (this.rows > 0)
    {
      this.rowZero += (this.rows - 1) * this.rowStride;
      this.rowStride = (-this.rowStride);
      this.isNoView = false;
    }
    return this;
  }
  
  protected AbstractMatrix2D vStrides(int paramInt1, int paramInt2)
  {
    if ((paramInt1 <= 0) || (paramInt2 <= 0)) {
      throw new IndexOutOfBoundsException("illegal strides: " + paramInt1 + ", " + paramInt2);
    }
    this.rowStride *= paramInt1;
    this.columnStride *= paramInt2;
    if (this.rows != 0) {
      this.rows = ((this.rows - 1) / paramInt1 + 1);
    }
    if (this.columns != 0) {
      this.columns = ((this.columns - 1) / paramInt2 + 1);
    }
    this.isNoView = false;
    return this;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.impl.AbstractMatrix2D
 * JD-Core Version:    0.7.0.1
 */