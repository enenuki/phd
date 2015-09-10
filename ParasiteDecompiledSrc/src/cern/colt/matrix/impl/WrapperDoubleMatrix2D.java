package cern.colt.matrix.impl;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;

class WrapperDoubleMatrix2D
  extends DoubleMatrix2D
{
  protected DoubleMatrix2D content;
  
  public WrapperDoubleMatrix2D(DoubleMatrix2D paramDoubleMatrix2D)
  {
    if (paramDoubleMatrix2D != null) {
      setUp(paramDoubleMatrix2D.rows(), paramDoubleMatrix2D.columns());
    }
    this.content = paramDoubleMatrix2D;
  }
  
  protected DoubleMatrix2D getContent()
  {
    return this.content;
  }
  
  public double getQuick(int paramInt1, int paramInt2)
  {
    return this.content.getQuick(paramInt1, paramInt2);
  }
  
  public DoubleMatrix2D like(int paramInt1, int paramInt2)
  {
    return this.content.like(paramInt1, paramInt2);
  }
  
  public DoubleMatrix1D like1D(int paramInt)
  {
    return this.content.like1D(paramInt);
  }
  
  protected DoubleMatrix1D like1D(int paramInt1, int paramInt2, int paramInt3)
  {
    throw new InternalError();
  }
  
  public void setQuick(int paramInt1, int paramInt2, double paramDouble)
  {
    this.content.setQuick(paramInt1, paramInt2, paramDouble);
  }
  
  public DoubleMatrix1D viewColumn(int paramInt)
  {
    return viewDice().viewRow(paramInt);
  }
  
  public DoubleMatrix2D viewColumnFlip()
  {
    if (this.columns == 0) {
      return this;
    }
    WrapperDoubleMatrix2D local1 = new WrapperDoubleMatrix2D(this)
    {
      public double getQuick(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        return this.content.get(paramAnonymousInt1, this.columns - 1 - paramAnonymousInt2);
      }
      
      public void setQuick(int paramAnonymousInt1, int paramAnonymousInt2, double paramAnonymousDouble)
      {
        this.content.set(paramAnonymousInt1, this.columns - 1 - paramAnonymousInt2, paramAnonymousDouble);
      }
    };
    return local1;
  }
  
  public DoubleMatrix2D viewDice()
  {
    WrapperDoubleMatrix2D local2 = new WrapperDoubleMatrix2D(this)
    {
      public double getQuick(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        return this.content.get(paramAnonymousInt2, paramAnonymousInt1);
      }
      
      public void setQuick(int paramAnonymousInt1, int paramAnonymousInt2, double paramAnonymousDouble)
      {
        this.content.set(paramAnonymousInt2, paramAnonymousInt1, paramAnonymousDouble);
      }
    };
    local2.rows = this.columns;
    local2.columns = this.rows;
    return local2;
  }
  
  public DoubleMatrix2D viewPart(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    checkBox(paramInt1, paramInt2, paramInt3, paramInt4);
    WrapperDoubleMatrix2D local3 = new WrapperDoubleMatrix2D(this)
    {
      private final int val$row;
      private final int val$column;
      
      public double getQuick(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        return this.content.get(this.val$row + paramAnonymousInt1, this.val$column + paramAnonymousInt2);
      }
      
      public void setQuick(int paramAnonymousInt1, int paramAnonymousInt2, double paramAnonymousDouble)
      {
        this.content.set(this.val$row + paramAnonymousInt1, this.val$column + paramAnonymousInt2, paramAnonymousDouble);
      }
    };
    local3.rows = paramInt3;
    local3.columns = paramInt4;
    return local3;
  }
  
  public DoubleMatrix1D viewRow(int paramInt)
  {
    checkRow(paramInt);
    return new DelegateDoubleMatrix1D(this, paramInt);
  }
  
  public DoubleMatrix2D viewRowFlip()
  {
    if (this.rows == 0) {
      return this;
    }
    WrapperDoubleMatrix2D local4 = new WrapperDoubleMatrix2D(this)
    {
      public double getQuick(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        return this.content.get(this.rows - 1 - paramAnonymousInt1, paramAnonymousInt2);
      }
      
      public void setQuick(int paramAnonymousInt1, int paramAnonymousInt2, double paramAnonymousDouble)
      {
        this.content.set(this.rows - 1 - paramAnonymousInt1, paramAnonymousInt2, paramAnonymousDouble);
      }
    };
    return local4;
  }
  
  public DoubleMatrix2D viewSelection(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    int i;
    if (paramArrayOfInt1 == null)
    {
      paramArrayOfInt1 = new int[this.rows];
      i = this.rows;
      for (;;)
      {
        i--;
        if (i < 0) {
          break;
        }
        paramArrayOfInt1[i] = i;
      }
    }
    if (paramArrayOfInt2 == null)
    {
      paramArrayOfInt2 = new int[this.columns];
      i = this.columns;
      for (;;)
      {
        i--;
        if (i < 0) {
          break;
        }
        paramArrayOfInt2[i] = i;
      }
    }
    checkRowIndexes(paramArrayOfInt1);
    checkColumnIndexes(paramArrayOfInt2);
    int[] arrayOfInt1 = paramArrayOfInt1;
    int[] arrayOfInt2 = paramArrayOfInt2;
    WrapperDoubleMatrix2D local5 = new WrapperDoubleMatrix2D(this)
    {
      private final int[] val$rix;
      private final int[] val$cix;
      
      public double getQuick(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        return this.content.get(this.val$rix[paramAnonymousInt1], this.val$cix[paramAnonymousInt2]);
      }
      
      public void setQuick(int paramAnonymousInt1, int paramAnonymousInt2, double paramAnonymousDouble)
      {
        this.content.set(this.val$rix[paramAnonymousInt1], this.val$cix[paramAnonymousInt2], paramAnonymousDouble);
      }
    };
    local5.rows = paramArrayOfInt1.length;
    local5.columns = paramArrayOfInt2.length;
    return local5;
  }
  
  protected DoubleMatrix2D viewSelectionLike(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    throw new InternalError();
  }
  
  public DoubleMatrix2D viewStrides(int paramInt1, int paramInt2)
  {
    if ((paramInt1 <= 0) || (paramInt2 <= 0)) {
      throw new IndexOutOfBoundsException("illegal stride");
    }
    WrapperDoubleMatrix2D local6 = new WrapperDoubleMatrix2D(this)
    {
      private final int val$_rowStride;
      private final int val$_columnStride;
      
      public double getQuick(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        return this.content.get(this.val$_rowStride * paramAnonymousInt1, this.val$_columnStride * paramAnonymousInt2);
      }
      
      public void setQuick(int paramAnonymousInt1, int paramAnonymousInt2, double paramAnonymousDouble)
      {
        this.content.set(this.val$_rowStride * paramAnonymousInt1, this.val$_columnStride * paramAnonymousInt2, paramAnonymousDouble);
      }
    };
    local6.rows = this.rows;
    local6.columns = this.columns;
    if (this.rows != 0) {
      local6.rows = ((this.rows - 1) / paramInt1 + 1);
    }
    if (this.columns != 0) {
      local6.columns = ((this.columns - 1) / paramInt2 + 1);
    }
    return local6;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.impl.WrapperDoubleMatrix2D
 * JD-Core Version:    0.7.0.1
 */