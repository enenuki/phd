package cern.colt.matrix.impl;

public abstract class AbstractMatrix1D
  extends AbstractMatrix
{
  protected int size;
  protected int zero;
  protected int stride;
  
  protected int _offset(int paramInt)
  {
    return paramInt;
  }
  
  protected int _rank(int paramInt)
  {
    return this.zero + paramInt * this.stride;
  }
  
  protected void checkIndex(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this.size)) {
      throw new IndexOutOfBoundsException("Attempted to access " + toStringShort() + " at index=" + paramInt);
    }
  }
  
  protected void checkIndexes(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt.length;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      int j = paramArrayOfInt[i];
      if ((j < 0) || (j >= this.size)) {
        checkIndex(j);
      }
    }
  }
  
  protected void checkRange(int paramInt1, int paramInt2)
  {
    if ((paramInt1 < 0) || (paramInt1 + paramInt2 > this.size)) {
      throw new IndexOutOfBoundsException("index: " + paramInt1 + ", width: " + paramInt2 + ", size=" + this.size);
    }
  }
  
  protected void checkSize(double[] paramArrayOfDouble)
  {
    if (this.size != paramArrayOfDouble.length) {
      throw new IllegalArgumentException("Incompatible sizes: " + toStringShort() + " and " + paramArrayOfDouble.length);
    }
  }
  
  public void checkSize(AbstractMatrix1D paramAbstractMatrix1D)
  {
    if (this.size != paramAbstractMatrix1D.size) {
      throw new IllegalArgumentException("Incompatible sizes: " + toStringShort() + " and " + paramAbstractMatrix1D.toStringShort());
    }
  }
  
  protected int index(int paramInt)
  {
    return _offset(_rank(paramInt));
  }
  
  protected void setUp(int paramInt)
  {
    setUp(paramInt, 0, 1);
  }
  
  protected void setUp(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt1 < 0) {
      throw new IllegalArgumentException("negative size");
    }
    this.size = paramInt1;
    this.zero = paramInt2;
    this.stride = paramInt3;
    this.isNoView = true;
  }
  
  public int size()
  {
    return this.size;
  }
  
  protected int stride(int paramInt)
  {
    if (paramInt != 0) {
      throw new IllegalArgumentException("invalid dimension: " + paramInt + "used to access" + toStringShort());
    }
    return this.stride;
  }
  
  public String toStringShort()
  {
    return AbstractFormatter.shape(this);
  }
  
  protected AbstractMatrix1D vFlip()
  {
    if (this.size > 0)
    {
      this.zero += (this.size - 1) * this.stride;
      this.stride = (-this.stride);
      this.isNoView = false;
    }
    return this;
  }
  
  protected AbstractMatrix1D vPart(int paramInt1, int paramInt2)
  {
    checkRange(paramInt1, paramInt2);
    this.zero += this.stride * paramInt1;
    this.size = paramInt2;
    this.isNoView = false;
    return this;
  }
  
  protected AbstractMatrix1D vStrides(int paramInt)
  {
    if (paramInt <= 0) {
      throw new IndexOutOfBoundsException("illegal stride: " + paramInt);
    }
    this.stride *= paramInt;
    if (this.size != 0) {
      this.size = ((this.size - 1) / paramInt + 1);
    }
    this.isNoView = false;
    return this;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.impl.AbstractMatrix1D
 * JD-Core Version:    0.7.0.1
 */