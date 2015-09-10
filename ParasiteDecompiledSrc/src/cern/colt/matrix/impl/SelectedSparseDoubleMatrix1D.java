package cern.colt.matrix.impl;

import cern.colt.map.AbstractIntDoubleMap;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;

class SelectedSparseDoubleMatrix1D
  extends DoubleMatrix1D
{
  protected AbstractIntDoubleMap elements;
  protected int[] offsets;
  protected int offset;
  
  protected SelectedSparseDoubleMatrix1D(int paramInt1, AbstractIntDoubleMap paramAbstractIntDoubleMap, int paramInt2, int paramInt3, int[] paramArrayOfInt, int paramInt4)
  {
    setUp(paramInt1, paramInt2, paramInt3);
    this.elements = paramAbstractIntDoubleMap;
    this.offsets = paramArrayOfInt;
    this.offset = paramInt4;
    this.isNoView = false;
  }
  
  protected SelectedSparseDoubleMatrix1D(AbstractIntDoubleMap paramAbstractIntDoubleMap, int[] paramArrayOfInt)
  {
    this(paramArrayOfInt.length, paramAbstractIntDoubleMap, 0, 1, paramArrayOfInt, 0);
  }
  
  protected int _offset(int paramInt)
  {
    return this.offsets[paramInt];
  }
  
  public double getQuick(int paramInt)
  {
    return this.elements.get(this.offset + this.offsets[(this.zero + paramInt * this.stride)]);
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
    return this.offset + this.offsets[(this.zero + paramInt * this.stride)];
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
    int i = this.offset + this.offsets[(this.zero + paramInt * this.stride)];
    if (paramDouble == 0.0D) {
      this.elements.removeKey(i);
    } else {
      this.elements.put(i, paramDouble);
    }
  }
  
  protected void setUp(int paramInt)
  {
    super.setUp(paramInt);
    this.stride = 1;
    this.offset = 0;
  }
  
  protected DoubleMatrix1D viewSelectionLike(int[] paramArrayOfInt)
  {
    return new SelectedSparseDoubleMatrix1D(this.elements, paramArrayOfInt);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.impl.SelectedSparseDoubleMatrix1D
 * JD-Core Version:    0.7.0.1
 */