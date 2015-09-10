package cern.colt.matrix.impl;

import cern.colt.matrix.ObjectMatrix1D;
import cern.colt.matrix.ObjectMatrix2D;

class SelectedDenseObjectMatrix1D
  extends ObjectMatrix1D
{
  protected Object[] elements;
  protected int[] offsets;
  protected int offset;
  
  protected SelectedDenseObjectMatrix1D(Object[] paramArrayOfObject, int[] paramArrayOfInt)
  {
    this(paramArrayOfInt.length, paramArrayOfObject, 0, 1, paramArrayOfInt, 0);
  }
  
  protected SelectedDenseObjectMatrix1D(int paramInt1, Object[] paramArrayOfObject, int paramInt2, int paramInt3, int[] paramArrayOfInt, int paramInt4)
  {
    setUp(paramInt1, paramInt2, paramInt3);
    this.elements = paramArrayOfObject;
    this.offsets = paramArrayOfInt;
    this.offset = paramInt4;
    this.isNoView = false;
  }
  
  protected int _offset(int paramInt)
  {
    return this.offsets[paramInt];
  }
  
  public Object getQuick(int paramInt)
  {
    return this.elements[(this.offset + this.offsets[(this.zero + paramInt * this.stride)])];
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
    return this.offset + this.offsets[(this.zero + paramInt * this.stride)];
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
    this.elements[(this.offset + this.offsets[(this.zero + paramInt * this.stride)])] = paramObject;
  }
  
  protected void setUp(int paramInt)
  {
    super.setUp(paramInt);
    this.stride = 1;
    this.offset = 0;
  }
  
  protected ObjectMatrix1D viewSelectionLike(int[] paramArrayOfInt)
  {
    return new SelectedDenseObjectMatrix1D(this.elements, paramArrayOfInt);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.impl.SelectedDenseObjectMatrix1D
 * JD-Core Version:    0.7.0.1
 */