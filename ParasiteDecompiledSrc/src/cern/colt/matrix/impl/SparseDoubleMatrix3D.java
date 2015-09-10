package cern.colt.matrix.impl;

import cern.colt.map.AbstractIntDoubleMap;
import cern.colt.map.OpenIntDoubleHashMap;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.DoubleMatrix3D;

public class SparseDoubleMatrix3D
  extends DoubleMatrix3D
{
  protected AbstractIntDoubleMap elements;
  
  public SparseDoubleMatrix3D(double[][][] paramArrayOfDouble)
  {
    this(paramArrayOfDouble.length, paramArrayOfDouble.length == 0 ? 0 : paramArrayOfDouble[0].length, paramArrayOfDouble[0].length == 0 ? 0 : paramArrayOfDouble.length == 0 ? 0 : paramArrayOfDouble[0][0].length);
    assign(paramArrayOfDouble);
  }
  
  public SparseDoubleMatrix3D(int paramInt1, int paramInt2, int paramInt3)
  {
    this(paramInt1, paramInt2, paramInt3, paramInt1 * paramInt2 * (paramInt3 / 1000), 0.2D, 0.5D);
  }
  
  public SparseDoubleMatrix3D(int paramInt1, int paramInt2, int paramInt3, int paramInt4, double paramDouble1, double paramDouble2)
  {
    setUp(paramInt1, paramInt2, paramInt3);
    this.elements = new OpenIntDoubleHashMap(paramInt4, paramDouble1, paramDouble2);
  }
  
  protected SparseDoubleMatrix3D(int paramInt1, int paramInt2, int paramInt3, AbstractIntDoubleMap paramAbstractIntDoubleMap, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9)
  {
    setUp(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramInt9);
    this.elements = paramAbstractIntDoubleMap;
    this.isNoView = false;
  }
  
  public DoubleMatrix3D assign(double paramDouble)
  {
    if ((this.isNoView) && (paramDouble == 0.0D)) {
      this.elements.clear();
    } else {
      super.assign(paramDouble);
    }
    return this;
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
  
  public double getQuick(int paramInt1, int paramInt2, int paramInt3)
  {
    return this.elements.get(this.sliceZero + paramInt1 * this.sliceStride + this.rowZero + paramInt2 * this.rowStride + this.columnZero + paramInt3 * this.columnStride);
  }
  
  protected boolean haveSharedCellsRaw(DoubleMatrix3D paramDoubleMatrix3D)
  {
    Object localObject;
    if ((paramDoubleMatrix3D instanceof SelectedSparseDoubleMatrix3D))
    {
      localObject = (SelectedSparseDoubleMatrix3D)paramDoubleMatrix3D;
      return this.elements == ((SelectedSparseDoubleMatrix3D)localObject).elements;
    }
    if ((paramDoubleMatrix3D instanceof SparseDoubleMatrix3D))
    {
      localObject = (SparseDoubleMatrix3D)paramDoubleMatrix3D;
      return this.elements == ((SparseDoubleMatrix3D)localObject).elements;
    }
    return false;
  }
  
  protected int index(int paramInt1, int paramInt2, int paramInt3)
  {
    return this.sliceZero + paramInt1 * this.sliceStride + this.rowZero + paramInt2 * this.rowStride + this.columnZero + paramInt3 * this.columnStride;
  }
  
  public DoubleMatrix3D like(int paramInt1, int paramInt2, int paramInt3)
  {
    return new SparseDoubleMatrix3D(paramInt1, paramInt2, paramInt3);
  }
  
  protected DoubleMatrix2D like2D(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    return new SparseDoubleMatrix2D(paramInt1, paramInt2, this.elements, paramInt3, paramInt4, paramInt5, paramInt6);
  }
  
  public void setQuick(int paramInt1, int paramInt2, int paramInt3, double paramDouble)
  {
    int i = this.sliceZero + paramInt1 * this.sliceStride + this.rowZero + paramInt2 * this.rowStride + this.columnZero + paramInt3 * this.columnStride;
    if (paramDouble == 0.0D) {
      this.elements.removeKey(i);
    } else {
      this.elements.put(i, paramDouble);
    }
  }
  
  public void trimToSize()
  {
    this.elements.trimToSize();
  }
  
  protected DoubleMatrix3D viewSelectionLike(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3)
  {
    return new SelectedSparseDoubleMatrix3D(this.elements, paramArrayOfInt1, paramArrayOfInt2, paramArrayOfInt3, 0);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.impl.SparseDoubleMatrix3D
 * JD-Core Version:    0.7.0.1
 */