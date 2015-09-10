package cern.colt.matrix.impl;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;

class WrapperDoubleMatrix1D
  extends DoubleMatrix1D
{
  protected DoubleMatrix1D content;
  
  public WrapperDoubleMatrix1D(DoubleMatrix1D paramDoubleMatrix1D)
  {
    if (paramDoubleMatrix1D != null) {
      setUp(paramDoubleMatrix1D.size());
    }
    this.content = paramDoubleMatrix1D;
  }
  
  protected DoubleMatrix1D getContent()
  {
    return this.content;
  }
  
  public double getQuick(int paramInt)
  {
    return this.content.getQuick(paramInt);
  }
  
  public DoubleMatrix1D like(int paramInt)
  {
    return this.content.like(paramInt);
  }
  
  public DoubleMatrix2D like2D(int paramInt1, int paramInt2)
  {
    return this.content.like2D(paramInt1, paramInt2);
  }
  
  public void setQuick(int paramInt, double paramDouble)
  {
    this.content.setQuick(paramInt, paramDouble);
  }
  
  public DoubleMatrix1D viewFlip()
  {
    WrapperDoubleMatrix1D local1 = new WrapperDoubleMatrix1D(this)
    {
      public double getQuick(int paramAnonymousInt)
      {
        return this.content.get(this.size - 1 - paramAnonymousInt);
      }
      
      public void setQuick(int paramAnonymousInt, double paramAnonymousDouble)
      {
        this.content.set(this.size - 1 - paramAnonymousInt, paramAnonymousDouble);
      }
    };
    return local1;
  }
  
  public DoubleMatrix1D viewPart(int paramInt1, int paramInt2)
  {
    checkRange(paramInt1, paramInt2);
    WrapperDoubleMatrix1D local2 = new WrapperDoubleMatrix1D(this)
    {
      private final int val$index;
      
      public double getQuick(int paramAnonymousInt)
      {
        return this.content.get(this.val$index + paramAnonymousInt);
      }
      
      public void setQuick(int paramAnonymousInt, double paramAnonymousDouble)
      {
        this.content.set(this.val$index + paramAnonymousInt, paramAnonymousDouble);
      }
    };
    local2.size = paramInt2;
    return local2;
  }
  
  public DoubleMatrix1D viewSelection(int[] paramArrayOfInt)
  {
    if (paramArrayOfInt == null)
    {
      paramArrayOfInt = new int[this.size];
      int i = this.size;
      for (;;)
      {
        i--;
        if (i < 0) {
          break;
        }
        paramArrayOfInt[i] = i;
      }
    }
    checkIndexes(paramArrayOfInt);
    int[] arrayOfInt = paramArrayOfInt;
    WrapperDoubleMatrix1D local3 = new WrapperDoubleMatrix1D(this)
    {
      private final int[] val$idx;
      
      public double getQuick(int paramAnonymousInt)
      {
        return this.content.get(this.val$idx[paramAnonymousInt]);
      }
      
      public void setQuick(int paramAnonymousInt, double paramAnonymousDouble)
      {
        this.content.set(this.val$idx[paramAnonymousInt], paramAnonymousDouble);
      }
    };
    local3.size = paramArrayOfInt.length;
    return local3;
  }
  
  protected DoubleMatrix1D viewSelectionLike(int[] paramArrayOfInt)
  {
    throw new InternalError();
  }
  
  public DoubleMatrix1D viewStrides(int paramInt)
  {
    if (this.stride <= 0) {
      throw new IndexOutOfBoundsException("illegal stride: " + this.stride);
    }
    WrapperDoubleMatrix1D local4 = new WrapperDoubleMatrix1D(this)
    {
      private final int val$_stride;
      
      public double getQuick(int paramAnonymousInt)
      {
        return this.content.get(paramAnonymousInt * this.val$_stride);
      }
      
      public void setQuick(int paramAnonymousInt, double paramAnonymousDouble)
      {
        this.content.set(paramAnonymousInt * this.val$_stride, paramAnonymousDouble);
      }
    };
    local4.size = this.size;
    if (this.size != 0) {
      local4.size = ((this.size - 1) / paramInt + 1);
    }
    return local4;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.impl.WrapperDoubleMatrix1D
 * JD-Core Version:    0.7.0.1
 */