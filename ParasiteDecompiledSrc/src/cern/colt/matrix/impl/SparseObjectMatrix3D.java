package cern.colt.matrix.impl;

import cern.colt.map.AbstractIntObjectMap;
import cern.colt.map.OpenIntObjectHashMap;
import cern.colt.matrix.ObjectMatrix2D;
import cern.colt.matrix.ObjectMatrix3D;

public class SparseObjectMatrix3D
  extends ObjectMatrix3D
{
  protected AbstractIntObjectMap elements;
  
  public SparseObjectMatrix3D(Object[][][] paramArrayOfObject)
  {
    this(paramArrayOfObject.length, paramArrayOfObject.length == 0 ? 0 : paramArrayOfObject[0].length, paramArrayOfObject[0].length == 0 ? 0 : paramArrayOfObject.length == 0 ? 0 : paramArrayOfObject[0][0].length);
    assign(paramArrayOfObject);
  }
  
  public SparseObjectMatrix3D(int paramInt1, int paramInt2, int paramInt3)
  {
    this(paramInt1, paramInt2, paramInt3, paramInt1 * paramInt2 * (paramInt3 / 1000), 0.2D, 0.5D);
  }
  
  public SparseObjectMatrix3D(int paramInt1, int paramInt2, int paramInt3, int paramInt4, double paramDouble1, double paramDouble2)
  {
    setUp(paramInt1, paramInt2, paramInt3);
    this.elements = new OpenIntObjectHashMap(paramInt4, paramDouble1, paramDouble2);
  }
  
  protected SparseObjectMatrix3D(int paramInt1, int paramInt2, int paramInt3, AbstractIntObjectMap paramAbstractIntObjectMap, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9)
  {
    setUp(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramInt9);
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
  
  public Object getQuick(int paramInt1, int paramInt2, int paramInt3)
  {
    return this.elements.get(this.sliceZero + paramInt1 * this.sliceStride + this.rowZero + paramInt2 * this.rowStride + this.columnZero + paramInt3 * this.columnStride);
  }
  
  protected boolean haveSharedCellsRaw(ObjectMatrix3D paramObjectMatrix3D)
  {
    Object localObject;
    if ((paramObjectMatrix3D instanceof SelectedSparseObjectMatrix3D))
    {
      localObject = (SelectedSparseObjectMatrix3D)paramObjectMatrix3D;
      return this.elements == ((SelectedSparseObjectMatrix3D)localObject).elements;
    }
    if ((paramObjectMatrix3D instanceof SparseObjectMatrix3D))
    {
      localObject = (SparseObjectMatrix3D)paramObjectMatrix3D;
      return this.elements == ((SparseObjectMatrix3D)localObject).elements;
    }
    return false;
  }
  
  protected int index(int paramInt1, int paramInt2, int paramInt3)
  {
    return this.sliceZero + paramInt1 * this.sliceStride + this.rowZero + paramInt2 * this.rowStride + this.columnZero + paramInt3 * this.columnStride;
  }
  
  public ObjectMatrix3D like(int paramInt1, int paramInt2, int paramInt3)
  {
    return new SparseObjectMatrix3D(paramInt1, paramInt2, paramInt3);
  }
  
  protected ObjectMatrix2D like2D(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    return new SparseObjectMatrix2D(paramInt1, paramInt2, this.elements, paramInt3, paramInt4, paramInt5, paramInt6);
  }
  
  public void setQuick(int paramInt1, int paramInt2, int paramInt3, Object paramObject)
  {
    int i = this.sliceZero + paramInt1 * this.sliceStride + this.rowZero + paramInt2 * this.rowStride + this.columnZero + paramInt3 * this.columnStride;
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
  
  protected ObjectMatrix3D viewSelectionLike(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3)
  {
    return new SelectedSparseObjectMatrix3D(this.elements, paramArrayOfInt1, paramArrayOfInt2, paramArrayOfInt3, 0);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.impl.SparseObjectMatrix3D
 * JD-Core Version:    0.7.0.1
 */