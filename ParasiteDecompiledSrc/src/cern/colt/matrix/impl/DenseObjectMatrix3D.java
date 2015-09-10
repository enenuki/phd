package cern.colt.matrix.impl;

import cern.colt.matrix.ObjectMatrix2D;
import cern.colt.matrix.ObjectMatrix3D;

public class DenseObjectMatrix3D
  extends ObjectMatrix3D
{
  protected Object[] elements;
  
  public DenseObjectMatrix3D(Object[][][] paramArrayOfObject)
  {
    this(paramArrayOfObject.length, paramArrayOfObject.length == 0 ? 0 : paramArrayOfObject[0].length, paramArrayOfObject[0].length == 0 ? 0 : paramArrayOfObject.length == 0 ? 0 : paramArrayOfObject[0][0].length);
    assign(paramArrayOfObject);
  }
  
  public DenseObjectMatrix3D(int paramInt1, int paramInt2, int paramInt3)
  {
    setUp(paramInt1, paramInt2, paramInt3);
    this.elements = new Object[paramInt1 * paramInt2 * paramInt3];
  }
  
  protected DenseObjectMatrix3D(int paramInt1, int paramInt2, int paramInt3, Object[] paramArrayOfObject, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9)
  {
    setUp(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramInt9);
    this.elements = paramArrayOfObject;
    this.isNoView = false;
  }
  
  public ObjectMatrix3D assign(Object[][][] paramArrayOfObject)
  {
    if (this.isNoView)
    {
      if (paramArrayOfObject.length != this.slices) {
        throw new IllegalArgumentException("Must have same number of slices: slices=" + paramArrayOfObject.length + "slices()=" + slices());
      }
      int i = this.slices * this.rows * this.columns - this.columns;
      int j = this.slices;
      for (;;)
      {
        j--;
        if (j < 0) {
          break;
        }
        Object[][] arrayOfObject = paramArrayOfObject[j];
        if (arrayOfObject.length != this.rows) {
          throw new IllegalArgumentException("Must have same number of rows in every slice: rows=" + arrayOfObject.length + "rows()=" + rows());
        }
        int k = this.rows;
        for (;;)
        {
          k--;
          if (k < 0) {
            break;
          }
          Object[] arrayOfObject1 = arrayOfObject[k];
          if (arrayOfObject1.length != this.columns) {
            throw new IllegalArgumentException("Must have same number of columns in every row: columns=" + arrayOfObject1.length + "columns()=" + columns());
          }
          System.arraycopy(arrayOfObject1, 0, this.elements, i, this.columns);
          i -= this.columns;
        }
      }
    }
    super.assign(paramArrayOfObject);
    return this;
  }
  
  public ObjectMatrix3D assign(ObjectMatrix3D paramObjectMatrix3D)
  {
    if (!(paramObjectMatrix3D instanceof DenseObjectMatrix3D)) {
      return super.assign(paramObjectMatrix3D);
    }
    DenseObjectMatrix3D localDenseObjectMatrix3D = (DenseObjectMatrix3D)paramObjectMatrix3D;
    if (localDenseObjectMatrix3D == this) {
      return this;
    }
    checkShape(localDenseObjectMatrix3D);
    if (haveSharedCells(localDenseObjectMatrix3D))
    {
      ObjectMatrix3D localObjectMatrix3D = localDenseObjectMatrix3D.copy();
      if (!(localObjectMatrix3D instanceof DenseObjectMatrix3D)) {
        return super.assign(paramObjectMatrix3D);
      }
      localDenseObjectMatrix3D = (DenseObjectMatrix3D)localObjectMatrix3D;
    }
    if ((this.isNoView) && (localDenseObjectMatrix3D.isNoView))
    {
      System.arraycopy(localDenseObjectMatrix3D.elements, 0, this.elements, 0, this.elements.length);
      return this;
    }
    return super.assign(localDenseObjectMatrix3D);
  }
  
  public Object getQuick(int paramInt1, int paramInt2, int paramInt3)
  {
    return this.elements[(this.sliceZero + paramInt1 * this.sliceStride + this.rowZero + paramInt2 * this.rowStride + this.columnZero + paramInt3 * this.columnStride)];
  }
  
  protected boolean haveSharedCellsRaw(ObjectMatrix3D paramObjectMatrix3D)
  {
    Object localObject;
    if ((paramObjectMatrix3D instanceof SelectedDenseObjectMatrix3D))
    {
      localObject = (SelectedDenseObjectMatrix3D)paramObjectMatrix3D;
      return this.elements == ((SelectedDenseObjectMatrix3D)localObject).elements;
    }
    if ((paramObjectMatrix3D instanceof DenseObjectMatrix3D))
    {
      localObject = (DenseObjectMatrix3D)paramObjectMatrix3D;
      return this.elements == ((DenseObjectMatrix3D)localObject).elements;
    }
    return false;
  }
  
  protected int index(int paramInt1, int paramInt2, int paramInt3)
  {
    return this.sliceZero + paramInt1 * this.sliceStride + this.rowZero + paramInt2 * this.rowStride + this.columnZero + paramInt3 * this.columnStride;
  }
  
  public ObjectMatrix3D like(int paramInt1, int paramInt2, int paramInt3)
  {
    return new DenseObjectMatrix3D(paramInt1, paramInt2, paramInt3);
  }
  
  protected ObjectMatrix2D like2D(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    return new DenseObjectMatrix2D(paramInt1, paramInt2, this.elements, paramInt3, paramInt4, paramInt5, paramInt6);
  }
  
  public void setQuick(int paramInt1, int paramInt2, int paramInt3, Object paramObject)
  {
    this.elements[(this.sliceZero + paramInt1 * this.sliceStride + this.rowZero + paramInt2 * this.rowStride + this.columnZero + paramInt3 * this.columnStride)] = paramObject;
  }
  
  protected ObjectMatrix3D viewSelectionLike(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3)
  {
    return new SelectedDenseObjectMatrix3D(this.elements, paramArrayOfInt1, paramArrayOfInt2, paramArrayOfInt3, 0);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.impl.DenseObjectMatrix3D
 * JD-Core Version:    0.7.0.1
 */