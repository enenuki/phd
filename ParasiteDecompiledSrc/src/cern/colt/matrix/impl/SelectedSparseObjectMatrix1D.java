package cern.colt.matrix.impl;

import cern.colt.map.AbstractIntObjectMap;
import cern.colt.matrix.ObjectMatrix1D;
import cern.colt.matrix.ObjectMatrix2D;

class SelectedSparseObjectMatrix1D
  extends ObjectMatrix1D
{
  protected AbstractIntObjectMap elements;
  protected int[] offsets;
  protected int offset;
  
  protected SelectedSparseObjectMatrix1D(int paramInt1, AbstractIntObjectMap paramAbstractIntObjectMap, int paramInt2, int paramInt3, int[] paramArrayOfInt, int paramInt4)
  {
    setUp(paramInt1, paramInt2, paramInt3);
    this.elements = paramAbstractIntObjectMap;
    this.offsets = paramArrayOfInt;
    this.offset = paramInt4;
    this.isNoView = false;
  }
  
  protected SelectedSparseObjectMatrix1D(AbstractIntObjectMap paramAbstractIntObjectMap, int[] paramArrayOfInt)
  {
    this(paramArrayOfInt.length, paramAbstractIntObjectMap, 0, 1, paramArrayOfInt, 0);
  }
  
  protected int _offset(int paramInt)
  {
    return this.offsets[paramInt];
  }
  
  public Object getQuick(int paramInt)
  {
    return this.elements.get(this.offset + this.offsets[(this.zero + paramInt * this.stride)]);
  }
  
  protected boolean haveSharedCellsRaw(ObjectMatrix1D paramObjectMatrix1D)
  {
    Object localObject;
    if ((paramObjectMatrix1D instanceof SelectedSparseObjectMatrix1D))
    {
      localObject = (SelectedSparseObjectMatrix1D)paramObjectMatrix1D;
      return this.elements == ((SelectedSparseObjectMatrix1D)localObject).elements;
    }
    if ((paramObjectMatrix1D instanceof SparseObjectMatrix1D))
    {
      localObject = (SparseObjectMatrix1D)paramObjectMatrix1D;
      return this.elements == ((SparseObjectMatrix1D)localObject).elements;
    }
    return false;
  }
  
  protected int index(int paramInt)
  {
    return this.offset + this.offsets[(this.zero + paramInt * this.stride)];
  }
  
  public ObjectMatrix1D like(int paramInt)
  {
    return new SparseObjectMatrix1D(paramInt);
  }
  
  public ObjectMatrix2D like2D(int paramInt1, int paramInt2)
  {
    return new SparseObjectMatrix2D(paramInt1, paramInt2);
  }
  
  public void setQuick(int paramInt, Object paramObject)
  {
    int i = this.offset + this.offsets[(this.zero + paramInt * this.stride)];
    if (paramObject == null) {
      this.elements.removeKey(i);
    } else {
      this.elements.put(i, paramObject);
    }
  }
  
  protected void setUp(int paramInt)
  {
    super.setUp(paramInt);
    this.stride = 1;
    this.offset = 0;
  }
  
  protected ObjectMatrix1D viewSelectionLike(int[] paramArrayOfInt)
  {
    return new SelectedSparseObjectMatrix1D(this.elements, paramArrayOfInt);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.impl.SelectedSparseObjectMatrix1D
 * JD-Core Version:    0.7.0.1
 */