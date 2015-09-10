package cern.colt.matrix.impl;

import cern.colt.map.AbstractIntObjectMap;
import cern.colt.map.OpenIntObjectHashMap;
import cern.colt.matrix.ObjectMatrix1D;
import cern.colt.matrix.ObjectMatrix2D;

public class SparseObjectMatrix1D
  extends ObjectMatrix1D
{
  protected AbstractIntObjectMap elements;
  
  public SparseObjectMatrix1D(Object[] paramArrayOfObject)
  {
    this(paramArrayOfObject.length);
    assign(paramArrayOfObject);
  }
  
  public SparseObjectMatrix1D(int paramInt)
  {
    this(paramInt, paramInt / 1000, 0.2D, 0.5D);
  }
  
  public SparseObjectMatrix1D(int paramInt1, int paramInt2, double paramDouble1, double paramDouble2)
  {
    setUp(paramInt1);
    this.elements = new OpenIntObjectHashMap(paramInt2, paramDouble1, paramDouble2);
  }
  
  protected SparseObjectMatrix1D(int paramInt1, AbstractIntObjectMap paramAbstractIntObjectMap, int paramInt2, int paramInt3)
  {
    setUp(paramInt1, paramInt2, paramInt3);
    this.elements = paramAbstractIntObjectMap;
    this.isNoView = false;
  }
  
  public int cardinality()
  {
    if (this.isNoView) {
      return this.elements.size();
    }
    return super.cardinality();
  }
  
  public void ensureCapacity(int paramInt)
  {
    this.elements.ensureCapacity(paramInt);
  }
  
  public Object getQuick(int paramInt)
  {
    return this.elements.get(this.zero + paramInt * this.stride);
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
    return this.zero + paramInt * this.stride;
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
    int i = this.zero + paramInt * this.stride;
    if (paramObject == null) {
      this.elements.removeKey(i);
    } else {
      this.elements.put(i, paramObject);
    }
  }
  
  public void trimToSize()
  {
    this.elements.trimToSize();
  }
  
  protected ObjectMatrix1D viewSelectionLike(int[] paramArrayOfInt)
  {
    return new SelectedSparseObjectMatrix1D(this.elements, paramArrayOfInt);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.impl.SparseObjectMatrix1D
 * JD-Core Version:    0.7.0.1
 */