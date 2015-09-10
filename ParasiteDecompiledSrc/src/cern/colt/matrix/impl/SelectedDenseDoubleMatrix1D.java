package cern.colt.matrix.impl;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;

class SelectedDenseDoubleMatrix1D
  extends DoubleMatrix1D
{
  protected double[] elements;
  protected int[] offsets;
  protected int offset;
  
  protected SelectedDenseDoubleMatrix1D(double[] paramArrayOfDouble, int[] paramArrayOfInt)
  {
    this(paramArrayOfInt.length, paramArrayOfDouble, 0, 1, paramArrayOfInt, 0);
  }
  
  protected SelectedDenseDoubleMatrix1D(int paramInt1, double[] paramArrayOfDouble, int paramInt2, int paramInt3, int[] paramArrayOfInt, int paramInt4)
  {
    setUp(paramInt1, paramInt2, paramInt3);
    this.elements = paramArrayOfDouble;
    this.offsets = paramArrayOfInt;
    this.offset = paramInt4;
    this.isNoView = false;
  }
  
  protected int _offset(int paramInt)
  {
    return this.offsets[paramInt];
  }
  
  public double getQuick(int paramInt)
  {
    return this.elements[(this.offset + this.offsets[(this.zero + paramInt * this.stride)])];
  }
  
  protected boolean haveSharedCellsRaw(DoubleMatrix1D paramDoubleMatrix1D)
  {
    Object localObject;
    if ((paramDoubleMatrix1D instanceof SelectedDenseDoubleMatrix1D))
    {
      localObject = (SelectedDenseDoubleMatrix1D)paramDoubleMatrix1D;
      return this.elements == ((SelectedDenseDoubleMatrix1D)localObject).elements;
    }
    if ((paramDoubleMatrix1D instanceof DenseDoubleMatrix1D))
    {
      localObject = (DenseDoubleMatrix1D)paramDoubleMatrix1D;
      return this.elements == ((DenseDoubleMatrix1D)localObject).elements;
    }
    return false;
  }
  
  protected int index(int paramInt)
  {
    return this.offset + this.offsets[(this.zero + paramInt * this.stride)];
  }
  
  public DoubleMatrix1D like(int paramInt)
  {
    return new DenseDoubleMatrix1D(paramInt);
  }
  
  public DoubleMatrix2D like2D(int paramInt1, int paramInt2)
  {
    return new DenseDoubleMatrix2D(paramInt1, paramInt2);
  }
  
  public void setQuick(int paramInt, double paramDouble)
  {
    this.elements[(this.offset + this.offsets[(this.zero + paramInt * this.stride)])] = paramDouble;
  }
  
  protected void setUp(int paramInt)
  {
    super.setUp(paramInt);
    this.stride = 1;
    this.offset = 0;
  }
  
  protected DoubleMatrix1D viewSelectionLike(int[] paramArrayOfInt)
  {
    return new SelectedDenseDoubleMatrix1D(this.elements, paramArrayOfInt);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.impl.SelectedDenseDoubleMatrix1D
 * JD-Core Version:    0.7.0.1
 */