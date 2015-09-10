package cern.colt.matrix.impl;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;

class DelegateDoubleMatrix1D
  extends WrapperDoubleMatrix1D
{
  protected DoubleMatrix2D content;
  protected int row;
  
  public DelegateDoubleMatrix1D(DoubleMatrix2D paramDoubleMatrix2D, int paramInt)
  {
    super(null);
    if ((paramInt < 0) || (paramInt >= paramDoubleMatrix2D.rows())) {
      throw new IllegalArgumentException();
    }
    setUp(paramDoubleMatrix2D.columns());
    this.row = paramInt;
    this.content = paramDoubleMatrix2D;
  }
  
  public double getQuick(int paramInt)
  {
    return this.content.getQuick(this.row, paramInt);
  }
  
  public DoubleMatrix1D like(int paramInt)
  {
    return this.content.like1D(paramInt);
  }
  
  public DoubleMatrix2D like2D(int paramInt1, int paramInt2)
  {
    return this.content.like(paramInt1, paramInt2);
  }
  
  public void setQuick(int paramInt, double paramDouble)
  {
    this.content.setQuick(this.row, paramInt, paramDouble);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.impl.DelegateDoubleMatrix1D
 * JD-Core Version:    0.7.0.1
 */