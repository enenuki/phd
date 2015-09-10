package cern.colt.matrix.impl;

public abstract class AbstractMatrix3D
  extends AbstractMatrix
{
  protected int slices;
  protected int rows;
  protected int columns;
  protected int sliceStride;
  protected int rowStride;
  protected int columnStride;
  protected int sliceZero;
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
  
  protected int _sliceOffset(int paramInt)
  {
    return paramInt;
  }
  
  protected int _sliceRank(int paramInt)
  {
    return this.sliceZero + paramInt * this.sliceStride;
  }
  
  protected void checkBox(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    if ((paramInt1 < 0) || (paramInt4 < 0) || (paramInt1 + paramInt4 > this.slices) || (paramInt2 < 0) || (paramInt5 < 0) || (paramInt2 + paramInt5 > this.rows) || (paramInt3 < 0) || (paramInt6 < 0) || (paramInt3 + paramInt6 > this.columns)) {
      throw new IndexOutOfBoundsException(toStringShort() + ", slice:" + paramInt1 + ", row:" + paramInt2 + " ,column:" + paramInt3 + ", depth:" + paramInt4 + " ,height:" + paramInt5 + ", width:" + paramInt6);
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
  
  public void checkShape(AbstractMatrix3D paramAbstractMatrix3D)
  {
    if ((this.slices != paramAbstractMatrix3D.slices) || (this.rows != paramAbstractMatrix3D.rows) || (this.columns != paramAbstractMatrix3D.columns)) {
      throw new IllegalArgumentException("Incompatible dimensions: " + toStringShort() + " and " + paramAbstractMatrix3D.toStringShort());
    }
  }
  
  public void checkShape(AbstractMatrix3D paramAbstractMatrix3D1, AbstractMatrix3D paramAbstractMatrix3D2)
  {
    if ((this.slices != paramAbstractMatrix3D1.slices) || (this.rows != paramAbstractMatrix3D1.rows) || (this.columns != paramAbstractMatrix3D1.columns) || (this.slices != paramAbstractMatrix3D2.slices) || (this.rows != paramAbstractMatrix3D2.rows) || (this.columns != paramAbstractMatrix3D2.columns)) {
      throw new IllegalArgumentException("Incompatible dimensions: " + toStringShort() + ", " + paramAbstractMatrix3D1.toStringShort() + ", " + paramAbstractMatrix3D2.toStringShort());
    }
  }
  
  protected void checkSlice(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this.slices)) {
      throw new IndexOutOfBoundsException("Attempted to access " + toStringShort() + " at slice=" + paramInt);
    }
  }
  
  protected void checkSliceIndexes(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt.length;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      int j = paramArrayOfInt[i];
      if ((j < 0) || (j >= this.slices)) {
        checkSlice(j);
      }
    }
  }
  
  public int columns()
  {
    return this.columns;
  }
  
  protected int index(int paramInt1, int paramInt2, int paramInt3)
  {
    return _sliceOffset(_sliceRank(paramInt1)) + _rowOffset(_rowRank(paramInt2)) + _columnOffset(_columnRank(paramInt3));
  }
  
  public int rows()
  {
    return this.rows;
  }
  
  protected void setUp(int paramInt1, int paramInt2, int paramInt3)
  {
    setUp(paramInt1, paramInt2, paramInt3, 0, 0, 0, paramInt2 * paramInt3, paramInt3, 1);
  }
  
  protected void setUp(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9)
  {
    if ((paramInt1 < 0) || (paramInt2 < 0) || (paramInt3 < 0)) {
      throw new IllegalArgumentException("negative size");
    }
    if (paramInt1 * paramInt2 * paramInt3 > 2147483647.0D) {
      throw new IllegalArgumentException("matrix too large");
    }
    this.slices = paramInt1;
    this.rows = paramInt2;
    this.columns = paramInt3;
    this.sliceZero = paramInt4;
    this.rowZero = paramInt5;
    this.columnZero = paramInt6;
    this.sliceStride = paramInt7;
    this.rowStride = paramInt8;
    this.columnStride = paramInt9;
    this.isNoView = true;
  }
  
  protected int[] shape()
  {
    int[] arrayOfInt = new int[3];
    arrayOfInt[0] = this.slices;
    arrayOfInt[1] = this.rows;
    arrayOfInt[2] = this.columns;
    return arrayOfInt;
  }
  
  public int size()
  {
    return this.slices * this.rows * this.columns;
  }
  
  public int slices()
  {
    return this.slices;
  }
  
  public String toStringShort()
  {
    return AbstractFormatter.shape(this);
  }
  
  protected AbstractMatrix3D vColumnFlip()
  {
    if (this.columns > 0)
    {
      this.columnZero += (this.columns - 1) * this.columnStride;
      this.columnStride = (-this.columnStride);
      this.isNoView = false;
    }
    return this;
  }
  
  protected AbstractMatrix3D vDice(int paramInt1, int paramInt2, int paramInt3)
  {
    int i = 3;
    if ((paramInt1 < 0) || (paramInt1 >= i) || (paramInt2 < 0) || (paramInt2 >= i) || (paramInt3 < 0) || (paramInt3 >= i) || (paramInt1 == paramInt2) || (paramInt1 == paramInt3) || (paramInt2 == paramInt3)) {
      throw new IllegalArgumentException("Illegal Axes: " + paramInt1 + ", " + paramInt2 + ", " + paramInt3);
    }
    int[] arrayOfInt1 = shape();
    this.slices = arrayOfInt1[paramInt1];
    this.rows = arrayOfInt1[paramInt2];
    this.columns = arrayOfInt1[paramInt3];
    int[] arrayOfInt2 = new int[3];
    arrayOfInt2[0] = this.sliceStride;
    arrayOfInt2[1] = this.rowStride;
    arrayOfInt2[2] = this.columnStride;
    this.sliceStride = arrayOfInt2[paramInt1];
    this.rowStride = arrayOfInt2[paramInt2];
    this.columnStride = arrayOfInt2[paramInt3];
    this.isNoView = false;
    return this;
  }
  
  protected AbstractMatrix3D vPart(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    checkBox(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
    this.sliceZero += this.sliceStride * paramInt1;
    this.rowZero += this.rowStride * paramInt2;
    this.columnZero += this.columnStride * paramInt3;
    this.slices = paramInt4;
    this.rows = paramInt5;
    this.columns = paramInt6;
    this.isNoView = false;
    return this;
  }
  
  protected AbstractMatrix3D vRowFlip()
  {
    if (this.rows > 0)
    {
      this.rowZero += (this.rows - 1) * this.rowStride;
      this.rowStride = (-this.rowStride);
      this.isNoView = false;
    }
    return this;
  }
  
  protected AbstractMatrix3D vSliceFlip()
  {
    if (this.slices > 0)
    {
      this.sliceZero += (this.slices - 1) * this.sliceStride;
      this.sliceStride = (-this.sliceStride);
      this.isNoView = false;
    }
    return this;
  }
  
  protected AbstractMatrix3D vStrides(int paramInt1, int paramInt2, int paramInt3)
  {
    if ((paramInt1 <= 0) || (paramInt2 <= 0) || (paramInt3 <= 0)) {
      throw new IndexOutOfBoundsException("illegal strides: " + paramInt1 + ", " + paramInt2 + ", " + paramInt3);
    }
    this.sliceStride *= paramInt1;
    this.rowStride *= paramInt2;
    this.columnStride *= paramInt3;
    if (this.slices != 0) {
      this.slices = ((this.slices - 1) / paramInt1 + 1);
    }
    if (this.rows != 0) {
      this.rows = ((this.rows - 1) / paramInt2 + 1);
    }
    if (this.columns != 0) {
      this.columns = ((this.columns - 1) / paramInt3 + 1);
    }
    this.isNoView = false;
    return this;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.impl.AbstractMatrix3D
 * JD-Core Version:    0.7.0.1
 */