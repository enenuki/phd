package cern.colt.matrix.impl;

import cern.colt.map.AbstractIntDoubleMap;
import cern.colt.map.OpenIntDoubleHashMap;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;

public class SparseDoubleMatrix1D
  extends DoubleMatrix1D
{
  protected AbstractIntDoubleMap elements;
  
  public SparseDoubleMatrix1D(double[] paramArrayOfDouble)
  {
    this(paramArrayOfDouble.length);
    assign(paramArrayOfDouble);
  }
  
  public SparseDoubleMatrix1D(int paramInt)
  {
    this(paramInt, paramInt / 1000, 0.2D, 0.5D);
  }
  
  public SparseDoubleMatrix1D(int paramInt1, int paramInt2, double paramDouble1, double paramDouble2)
  {
    setUp(paramInt1);
    this.elements = new OpenIntDoubleHashMap(paramInt2, paramDouble1, paramDouble2);
  }
  
  protected SparseDoubleMatrix1D(int paramInt1, AbstractIntDoubleMap paramAbstractIntDoubleMap, int paramInt2, int paramInt3)
  {
    setUp(paramInt1, paramInt2, paramInt3);
    this.elements = paramAbstractIntDoubleMap;
    this.isNoView = false;
  }
  
  public DoubleMatrix1D assign(double paramDouble)
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
  
  public double getQuick(int paramInt)
  {
    return this.elements.get(this.zero + paramInt * this.stride);
  }
  
  protected boolean haveSharedCellsRaw(DoubleMatrix1D paramDoubleMatrix1D)
  {
    Object localObject;
    if ((paramDoubleMatrix1D instanceof SelectedSparseDoubleMatrix1D))
    {
      localObject = (SelectedSparseDoubleMatrix1D)paramDoubleMatrix1D;
      return this.elements == ((SelectedSparseDoubleMatrix1D)localObject).elements;
    }
    if ((paramDoubleMatrix1D instanceof SparseDoubleMatrix1D))
    {
      localObject = (SparseDoubleMatrix1D)paramDoubleMatrix1D;
      return this.elements == ((SparseDoubleMatrix1D)localObject).elements;
    }
    return false;
  }
  
  protected int index(int paramInt)
  {
    return this.zero + paramInt * this.stride;
  }
  
  public DoubleMatrix1D like(int paramInt)
  {
    return new SparseDoubleMatrix1D(paramInt);
  }
  
  public DoubleMatrix2D like2D(int paramInt1, int paramInt2)
  {
    return new SparseDoubleMatrix2D(paramInt1, paramInt2);
  }
  
  public void setQuick(int paramInt, double paramDouble)
  {
    int i = this.zero + paramInt * this.stride;
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
  
  protected DoubleMatrix1D viewSelectionLike(int[] paramArrayOfInt)
  {
    return new SelectedSparseDoubleMatrix1D(this.elements, paramArrayOfInt);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.impl.SparseDoubleMatrix1D
 * JD-Core Version:    0.7.0.1
 */