package cern.colt.matrix.impl;

import cern.colt.map.AbstractIntObjectMap;
import cern.colt.map.OpenIntObjectHashMap;
import cern.colt.matrix.ObjectMatrix1D;
import cern.colt.matrix.ObjectMatrix2D;

public class SparseObjectMatrix2D
  extends ObjectMatrix2D
{
  protected AbstractIntObjectMap elements;
  
  public SparseObjectMatrix2D(Object[][] paramArrayOfObject)
  {
    this(paramArrayOfObject.length, paramArrayOfObject.length == 0 ? 0 : paramArrayOfObject[0].length);
    assign(paramArrayOfObject);
  }
  
  public SparseObjectMatrix2D(int paramInt1, int paramInt2)
  {
    this(paramInt1, paramInt2, paramInt1 * (paramInt2 / 1000), 0.2D, 0.5D);
  }
  
  public SparseObjectMatrix2D(int paramInt1, int paramInt2, int paramInt3, double paramDouble1, double paramDouble2)
  {
    setUp(paramInt1, paramInt2);
    this.elements = new OpenIntObjectHashMap(paramInt3, paramDouble1, paramDouble2);
  }
  
  protected SparseObjectMatrix2D(int paramInt1, int paramInt2, AbstractIntObjectMap paramAbstractIntObjectMap, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    setUp(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
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
  
  public Object getQuick(int paramInt1, int paramInt2)
  {
    return this.elements.get(this.rowZero + paramInt1 * this.rowStride + this.columnZero + paramInt2 * this.columnStride);
  }
  
  protected boolean haveSharedCellsRaw(ObjectMatrix2D paramObjectMatrix2D)
  {
    Object localObject;
    if ((paramObjectMatrix2D instanceof SelectedSparseObjectMatrix2D))
    {
      localObject = (SelectedSparseObjectMatrix2D)paramObjectMatrix2D;
      return this.elements == ((SelectedSparseObjectMatrix2D)localObject).elements;
    }
    if ((paramObjectMatrix2D instanceof SparseObjectMatrix2D))
    {
      localObject = (SparseObjectMatrix2D)paramObjectMatrix2D;
      return this.elements == ((SparseObjectMatrix2D)localObject).elements;
    }
    return false;
  }
  
  protected int index(int paramInt1, int paramInt2)
  {
    return this.rowZero + paramInt1 * this.rowStride + this.columnZero + paramInt2 * this.columnStride;
  }
  
  public ObjectMatrix2D like(int paramInt1, int paramInt2)
  {
    return new SparseObjectMatrix2D(paramInt1, paramInt2);
  }
  
  public ObjectMatrix1D like1D(int paramInt)
  {
    return new SparseObjectMatrix1D(paramInt);
  }
  
  protected ObjectMatrix1D like1D(int paramInt1, int paramInt2, int paramInt3)
  {
    return new SparseObjectMatrix1D(paramInt1, this.elements, paramInt2, paramInt3);
  }
  
  public void setQuick(int paramInt1, int paramInt2, Object paramObject)
  {
    int i = this.rowZero + paramInt1 * this.rowStride + this.columnZero + paramInt2 * this.columnStride;
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
  
  protected ObjectMatrix2D viewSelectionLike(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    return new SelectedSparseObjectMatrix2D(this.elements, paramArrayOfInt1, paramArrayOfInt2, 0);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.impl.SparseObjectMatrix2D
 * JD-Core Version:    0.7.0.1
 */