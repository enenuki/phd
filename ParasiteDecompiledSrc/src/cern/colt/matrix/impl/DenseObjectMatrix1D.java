package cern.colt.matrix.impl;

import cern.colt.function.ObjectFunction;
import cern.colt.function.ObjectObjectFunction;
import cern.colt.matrix.ObjectMatrix1D;
import cern.colt.matrix.ObjectMatrix2D;

public class DenseObjectMatrix1D
  extends ObjectMatrix1D
{
  protected Object[] elements;
  
  public DenseObjectMatrix1D(Object[] paramArrayOfObject)
  {
    this(paramArrayOfObject.length);
    assign(paramArrayOfObject);
  }
  
  public DenseObjectMatrix1D(int paramInt)
  {
    setUp(paramInt);
    this.elements = new Object[paramInt];
  }
  
  protected DenseObjectMatrix1D(int paramInt1, Object[] paramArrayOfObject, int paramInt2, int paramInt3)
  {
    setUp(paramInt1, paramInt2, paramInt3);
    this.elements = paramArrayOfObject;
    this.isNoView = false;
  }
  
  public ObjectMatrix1D assign(Object[] paramArrayOfObject)
  {
    if (this.isNoView)
    {
      if (paramArrayOfObject.length != this.size) {
        throw new IllegalArgumentException("Must have same number of cells: length=" + paramArrayOfObject.length + "size()=" + size());
      }
      System.arraycopy(paramArrayOfObject, 0, this.elements, 0, paramArrayOfObject.length);
    }
    else
    {
      super.assign(paramArrayOfObject);
    }
    return this;
  }
  
  public ObjectMatrix1D assign(ObjectFunction paramObjectFunction)
  {
    int i = this.stride;
    int j = index(0);
    Object[] arrayOfObject = this.elements;
    if (this.elements == null) {
      throw new InternalError();
    }
    int k = this.size;
    for (;;)
    {
      k--;
      if (k < 0) {
        break;
      }
      arrayOfObject[j] = paramObjectFunction.apply(arrayOfObject[j]);
      j += i;
    }
    return this;
  }
  
  public ObjectMatrix1D assign(ObjectMatrix1D paramObjectMatrix1D)
  {
    if (!(paramObjectMatrix1D instanceof DenseObjectMatrix1D)) {
      return super.assign(paramObjectMatrix1D);
    }
    DenseObjectMatrix1D localDenseObjectMatrix1D = (DenseObjectMatrix1D)paramObjectMatrix1D;
    if (localDenseObjectMatrix1D == this) {
      return this;
    }
    checkSize(localDenseObjectMatrix1D);
    if ((this.isNoView) && (localDenseObjectMatrix1D.isNoView))
    {
      System.arraycopy(localDenseObjectMatrix1D.elements, 0, this.elements, 0, this.elements.length);
      return this;
    }
    if (haveSharedCells(localDenseObjectMatrix1D))
    {
      localObject = localDenseObjectMatrix1D.copy();
      if (!(localObject instanceof DenseObjectMatrix1D)) {
        return super.assign(paramObjectMatrix1D);
      }
      localDenseObjectMatrix1D = (DenseObjectMatrix1D)localObject;
    }
    Object localObject = this.elements;
    Object[] arrayOfObject = localDenseObjectMatrix1D.elements;
    if ((this.elements == null) || (arrayOfObject == null)) {
      throw new InternalError();
    }
    int i = this.stride;
    int j = localDenseObjectMatrix1D.stride;
    int k = index(0);
    int m = localDenseObjectMatrix1D.index(0);
    int n = this.size;
    for (;;)
    {
      n--;
      if (n < 0) {
        break;
      }
      localObject[k] = arrayOfObject[m];
      k += i;
      m += j;
    }
    return this;
  }
  
  public ObjectMatrix1D assign(ObjectMatrix1D paramObjectMatrix1D, ObjectObjectFunction paramObjectObjectFunction)
  {
    if (!(paramObjectMatrix1D instanceof DenseObjectMatrix1D)) {
      return super.assign(paramObjectMatrix1D, paramObjectObjectFunction);
    }
    DenseObjectMatrix1D localDenseObjectMatrix1D = (DenseObjectMatrix1D)paramObjectMatrix1D;
    checkSize(paramObjectMatrix1D);
    Object[] arrayOfObject1 = this.elements;
    Object[] arrayOfObject2 = localDenseObjectMatrix1D.elements;
    if ((this.elements == null) || (arrayOfObject2 == null)) {
      throw new InternalError();
    }
    int i = this.stride;
    int j = localDenseObjectMatrix1D.stride;
    int k = index(0);
    int m = localDenseObjectMatrix1D.index(0);
    int n = this.size;
    for (;;)
    {
      n--;
      if (n < 0) {
        break;
      }
      arrayOfObject1[k] = paramObjectObjectFunction.apply(arrayOfObject1[k], arrayOfObject2[m]);
      k += i;
      m += j;
    }
    return this;
  }
  
  public Object getQuick(int paramInt)
  {
    return this.elements[(this.zero + paramInt * this.stride)];
  }
  
  protected boolean haveSharedCellsRaw(ObjectMatrix1D paramObjectMatrix1D)
  {
    Object localObject;
    if ((paramObjectMatrix1D instanceof SelectedDenseObjectMatrix1D))
    {
      localObject = (SelectedDenseObjectMatrix1D)paramObjectMatrix1D;
      return this.elements == ((SelectedDenseObjectMatrix1D)localObject).elements;
    }
    if ((paramObjectMatrix1D instanceof DenseObjectMatrix1D))
    {
      localObject = (DenseObjectMatrix1D)paramObjectMatrix1D;
      return this.elements == ((DenseObjectMatrix1D)localObject).elements;
    }
    return false;
  }
  
  protected int index(int paramInt)
  {
    return this.zero + paramInt * this.stride;
  }
  
  public ObjectMatrix1D like(int paramInt)
  {
    return new DenseObjectMatrix1D(paramInt);
  }
  
  public ObjectMatrix2D like2D(int paramInt1, int paramInt2)
  {
    return new DenseObjectMatrix2D(paramInt1, paramInt2);
  }
  
  public void setQuick(int paramInt, Object paramObject)
  {
    this.elements[(this.zero + paramInt * this.stride)] = paramObject;
  }
  
  public void swap(ObjectMatrix1D paramObjectMatrix1D)
  {
    if (!(paramObjectMatrix1D instanceof DenseObjectMatrix1D)) {
      super.swap(paramObjectMatrix1D);
    }
    DenseObjectMatrix1D localDenseObjectMatrix1D = (DenseObjectMatrix1D)paramObjectMatrix1D;
    if (localDenseObjectMatrix1D == this) {
      return;
    }
    checkSize(localDenseObjectMatrix1D);
    Object[] arrayOfObject1 = this.elements;
    Object[] arrayOfObject2 = localDenseObjectMatrix1D.elements;
    if ((this.elements == null) || (arrayOfObject2 == null)) {
      throw new InternalError();
    }
    int i = this.stride;
    int j = localDenseObjectMatrix1D.stride;
    int k = index(0);
    int m = localDenseObjectMatrix1D.index(0);
    int n = this.size;
    for (;;)
    {
      n--;
      if (n < 0) {
        break;
      }
      Object localObject = arrayOfObject1[k];
      arrayOfObject1[k] = arrayOfObject2[m];
      arrayOfObject2[m] = localObject;
      k += i;
      m += j;
    }
  }
  
  public void toArray(Object[] paramArrayOfObject)
  {
    if (paramArrayOfObject.length < this.size) {
      throw new IllegalArgumentException("values too small");
    }
    if (this.isNoView) {
      System.arraycopy(this.elements, 0, paramArrayOfObject, 0, this.elements.length);
    } else {
      super.toArray(paramArrayOfObject);
    }
  }
  
  protected ObjectMatrix1D viewSelectionLike(int[] paramArrayOfInt)
  {
    return new SelectedDenseObjectMatrix1D(this.elements, paramArrayOfInt);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.impl.DenseObjectMatrix1D
 * JD-Core Version:    0.7.0.1
 */