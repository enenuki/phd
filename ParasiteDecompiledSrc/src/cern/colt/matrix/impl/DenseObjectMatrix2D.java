package cern.colt.matrix.impl;

import cern.colt.function.ObjectFunction;
import cern.colt.function.ObjectObjectFunction;
import cern.colt.matrix.ObjectMatrix1D;
import cern.colt.matrix.ObjectMatrix2D;

public class DenseObjectMatrix2D
  extends ObjectMatrix2D
{
  protected Object[] elements;
  
  public DenseObjectMatrix2D(Object[][] paramArrayOfObject)
  {
    this(paramArrayOfObject.length, paramArrayOfObject.length == 0 ? 0 : paramArrayOfObject[0].length);
    assign(paramArrayOfObject);
  }
  
  public DenseObjectMatrix2D(int paramInt1, int paramInt2)
  {
    setUp(paramInt1, paramInt2);
    this.elements = new Object[paramInt1 * paramInt2];
  }
  
  protected DenseObjectMatrix2D(int paramInt1, int paramInt2, Object[] paramArrayOfObject, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    setUp(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
    this.elements = paramArrayOfObject;
    this.isNoView = false;
  }
  
  public ObjectMatrix2D assign(Object[][] paramArrayOfObject)
  {
    if (this.isNoView)
    {
      if (paramArrayOfObject.length != this.rows) {
        throw new IllegalArgumentException("Must have same number of rows: rows=" + paramArrayOfObject.length + "rows()=" + rows());
      }
      int i = this.columns * (this.rows - 1);
      int j = this.rows;
      for (;;)
      {
        j--;
        if (j < 0) {
          break;
        }
        Object[] arrayOfObject = paramArrayOfObject[j];
        if (arrayOfObject.length != this.columns) {
          throw new IllegalArgumentException("Must have same number of columns in every row: columns=" + arrayOfObject.length + "columns()=" + columns());
        }
        System.arraycopy(arrayOfObject, 0, this.elements, i, this.columns);
        i -= this.columns;
      }
    }
    super.assign(paramArrayOfObject);
    return this;
  }
  
  public ObjectMatrix2D assign(ObjectFunction paramObjectFunction)
  {
    Object[] arrayOfObject = this.elements;
    if (arrayOfObject == null) {
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
        arrayOfObject[n] = paramObjectFunction.apply(arrayOfObject[n]);
        n += j;
      }
      i += k;
    }
    return this;
  }
  
  public ObjectMatrix2D assign(ObjectMatrix2D paramObjectMatrix2D)
  {
    if (!(paramObjectMatrix2D instanceof DenseObjectMatrix2D)) {
      return super.assign(paramObjectMatrix2D);
    }
    DenseObjectMatrix2D localDenseObjectMatrix2D = (DenseObjectMatrix2D)paramObjectMatrix2D;
    if (localDenseObjectMatrix2D == this) {
      return this;
    }
    checkShape(localDenseObjectMatrix2D);
    if ((this.isNoView) && (localDenseObjectMatrix2D.isNoView))
    {
      System.arraycopy(localDenseObjectMatrix2D.elements, 0, this.elements, 0, this.elements.length);
      return this;
    }
    if (haveSharedCells(localDenseObjectMatrix2D))
    {
      localObject = localDenseObjectMatrix2D.copy();
      if (!(localObject instanceof DenseObjectMatrix2D)) {
        return super.assign(localDenseObjectMatrix2D);
      }
      localDenseObjectMatrix2D = (DenseObjectMatrix2D)localObject;
    }
    Object localObject = this.elements;
    Object[] arrayOfObject = localDenseObjectMatrix2D.elements;
    if ((this.elements == null) || (arrayOfObject == null)) {
      throw new InternalError();
    }
    int i = this.columnStride;
    int j = localDenseObjectMatrix2D.columnStride;
    int k = this.rowStride;
    int m = localDenseObjectMatrix2D.rowStride;
    int n = localDenseObjectMatrix2D.index(0, 0);
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
        localObject[i3] = arrayOfObject[i4];
        i3 += i;
        i4 += j;
      }
      i1 += k;
      n += m;
    }
    return this;
  }
  
  public ObjectMatrix2D assign(ObjectMatrix2D paramObjectMatrix2D, ObjectObjectFunction paramObjectObjectFunction)
  {
    if (!(paramObjectMatrix2D instanceof DenseObjectMatrix2D)) {
      return super.assign(paramObjectMatrix2D, paramObjectObjectFunction);
    }
    DenseObjectMatrix2D localDenseObjectMatrix2D = (DenseObjectMatrix2D)paramObjectMatrix2D;
    checkShape(paramObjectMatrix2D);
    Object[] arrayOfObject1 = this.elements;
    Object[] arrayOfObject2 = localDenseObjectMatrix2D.elements;
    if ((arrayOfObject1 == null) || (arrayOfObject2 == null)) {
      throw new InternalError();
    }
    int i = this.columnStride;
    int j = localDenseObjectMatrix2D.columnStride;
    int k = this.rowStride;
    int m = localDenseObjectMatrix2D.rowStride;
    int n = localDenseObjectMatrix2D.index(0, 0);
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
        arrayOfObject1[i3] = paramObjectObjectFunction.apply(arrayOfObject1[i3], arrayOfObject2[i4]);
        i3 += i;
        i4 += j;
      }
      i1 += k;
      n += m;
    }
    return this;
  }
  
  public Object getQuick(int paramInt1, int paramInt2)
  {
    return this.elements[(this.rowZero + paramInt1 * this.rowStride + this.columnZero + paramInt2 * this.columnStride)];
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
    return this.rowZero + paramInt1 * this.rowStride + this.columnZero + paramInt2 * this.columnStride;
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
    return new DenseObjectMatrix1D(paramInt1, this.elements, paramInt2, paramInt3);
  }
  
  public void setQuick(int paramInt1, int paramInt2, Object paramObject)
  {
    this.elements[(this.rowZero + paramInt1 * this.rowStride + this.columnZero + paramInt2 * this.columnStride)] = paramObject;
  }
  
  protected ObjectMatrix2D viewSelectionLike(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    return new SelectedDenseObjectMatrix2D(this.elements, paramArrayOfInt1, paramArrayOfInt2, 0);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.impl.DenseObjectMatrix2D
 * JD-Core Version:    0.7.0.1
 */