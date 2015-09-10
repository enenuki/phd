package cern.colt.matrix.impl;

import cern.colt.list.DoubleArrayList;
import cern.colt.list.IntArrayList;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.jet.math.Functions;

class RCMDoubleMatrix2D
  extends WrapperDoubleMatrix2D
{
  private IntArrayList[] indexes;
  private DoubleArrayList[] values;
  
  public RCMDoubleMatrix2D(double[][] paramArrayOfDouble)
  {
    this(paramArrayOfDouble.length, paramArrayOfDouble.length == 0 ? 0 : paramArrayOfDouble[0].length);
    assign(paramArrayOfDouble);
  }
  
  public RCMDoubleMatrix2D(int paramInt1, int paramInt2)
  {
    super(null);
    setUp(paramInt1, paramInt2);
    this.indexes = new IntArrayList[paramInt1];
    this.values = new DoubleArrayList[paramInt1];
  }
  
  public DoubleMatrix2D assign(double paramDouble)
  {
    if (paramDouble == 0.0D)
    {
      int i = this.rows;
      for (;;)
      {
        i--;
        if (i < 0) {
          break;
        }
        this.indexes[i] = null;
        this.values[i] = null;
      }
    }
    super.assign(paramDouble);
    return this;
  }
  
  protected DoubleMatrix2D getContent()
  {
    return this;
  }
  
  public double getQuick(int paramInt1, int paramInt2)
  {
    int i = -1;
    if (this.indexes[paramInt1] != null) {
      i = this.indexes[paramInt1].binarySearch(paramInt2);
    }
    if (i < 0) {
      return 0.0D;
    }
    return this.values[paramInt1].getQuick(i);
  }
  
  public DoubleMatrix2D like(int paramInt1, int paramInt2)
  {
    return new RCMDoubleMatrix2D(paramInt1, paramInt2);
  }
  
  public DoubleMatrix1D like1D(int paramInt)
  {
    return new SparseDoubleMatrix1D(paramInt);
  }
  
  public void setQuick(int paramInt1, int paramInt2, double paramDouble)
  {
    int i = paramInt1;
    int j = paramInt2;
    int k = -1;
    IntArrayList localIntArrayList = this.indexes[i];
    if (localIntArrayList != null) {
      k = localIntArrayList.binarySearch(j);
    }
    if (k >= 0)
    {
      if (paramDouble == 0.0D)
      {
        DoubleArrayList localDoubleArrayList = this.values[i];
        localIntArrayList.remove(k);
        localDoubleArrayList.remove(k);
        int m = localIntArrayList.size();
        if ((m > 2) && (m * 3 < localIntArrayList.elements().length))
        {
          localIntArrayList.setSize(m * 3 / 2);
          localIntArrayList.trimToSize();
          localIntArrayList.setSize(m);
          localDoubleArrayList.setSize(m * 3 / 2);
          localDoubleArrayList.trimToSize();
          localDoubleArrayList.setSize(m);
        }
      }
      else
      {
        this.values[i].setQuick(k, paramDouble);
      }
    }
    else
    {
      if (paramDouble == 0.0D) {
        return;
      }
      k = -k - 1;
      if (localIntArrayList == null)
      {
        this.indexes[i] = new IntArrayList(3);
        this.values[i] = new DoubleArrayList(3);
      }
      this.indexes[i].beforeInsert(k, j);
      this.values[i].beforeInsert(k, paramDouble);
    }
  }
  
  protected void zMult(DoubleMatrix1D paramDoubleMatrix1D1, DoubleMatrix1D paramDoubleMatrix1D2, IntArrayList paramIntArrayList, DoubleMatrix1D[] paramArrayOfDoubleMatrix1D, double paramDouble1, double paramDouble2)
  {
    if ((this.columns != paramDoubleMatrix1D1.size()) || (this.rows > paramDoubleMatrix1D2.size())) {
      throw new IllegalArgumentException("Incompatible args: " + toStringShort() + ", " + paramDoubleMatrix1D1.toStringShort() + ", " + paramDoubleMatrix1D2.toStringShort());
    }
    paramDoubleMatrix1D2.assign(Functions.mult(paramDouble2 / paramDouble1));
    int i = this.indexes.length;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      if (this.indexes[i] != null)
      {
        int j = this.indexes[i].size();
        for (;;)
        {
          j--;
          if (j < 0) {
            break;
          }
          int k = this.indexes[i].getQuick(j);
          double d = this.values[i].getQuick(j);
          paramDoubleMatrix1D2.setQuick(i, paramDoubleMatrix1D2.getQuick(i) + d * paramDoubleMatrix1D1.getQuick(k));
        }
      }
    }
    paramDoubleMatrix1D2.assign(Functions.mult(paramDouble1));
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.impl.RCMDoubleMatrix2D
 * JD-Core Version:    0.7.0.1
 */