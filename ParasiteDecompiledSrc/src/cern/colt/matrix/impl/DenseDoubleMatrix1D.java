package cern.colt.matrix.impl;

import cern.colt.function.DoubleDoubleFunction;
import cern.colt.function.DoubleFunction;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.jet.math.Functions;
import cern.jet.math.Mult;
import cern.jet.math.PlusMult;

public class DenseDoubleMatrix1D
  extends DoubleMatrix1D
{
  protected double[] elements;
  
  public DenseDoubleMatrix1D(double[] paramArrayOfDouble)
  {
    this(paramArrayOfDouble.length);
    assign(paramArrayOfDouble);
  }
  
  public DenseDoubleMatrix1D(int paramInt)
  {
    setUp(paramInt);
    this.elements = new double[paramInt];
  }
  
  protected DenseDoubleMatrix1D(int paramInt1, double[] paramArrayOfDouble, int paramInt2, int paramInt3)
  {
    setUp(paramInt1, paramInt2, paramInt3);
    this.elements = paramArrayOfDouble;
    this.isNoView = false;
  }
  
  public DoubleMatrix1D assign(double[] paramArrayOfDouble)
  {
    if (this.isNoView)
    {
      if (paramArrayOfDouble.length != this.size) {
        throw new IllegalArgumentException("Must have same number of cells: length=" + paramArrayOfDouble.length + "size()=" + size());
      }
      System.arraycopy(paramArrayOfDouble, 0, this.elements, 0, paramArrayOfDouble.length);
    }
    else
    {
      super.assign(paramArrayOfDouble);
    }
    return this;
  }
  
  public DoubleMatrix1D assign(double paramDouble)
  {
    int i = index(0);
    int j = this.stride;
    double[] arrayOfDouble = this.elements;
    int k = this.size;
    for (;;)
    {
      k--;
      if (k < 0) {
        break;
      }
      arrayOfDouble[i] = paramDouble;
      i += j;
    }
    return this;
  }
  
  public DoubleMatrix1D assign(DoubleFunction paramDoubleFunction)
  {
    int i = this.stride;
    int j = index(0);
    double[] arrayOfDouble = this.elements;
    if (arrayOfDouble == null) {
      throw new InternalError();
    }
    if ((paramDoubleFunction instanceof Mult))
    {
      double d = ((Mult)paramDoubleFunction).multiplicator;
      if (d == 1.0D) {
        return this;
      }
      int m = this.size;
      for (;;)
      {
        m--;
        if (m < 0) {
          break;
        }
        arrayOfDouble[j] *= d;
        j += i;
      }
    }
    int k = this.size;
    for (;;)
    {
      k--;
      if (k < 0) {
        break;
      }
      arrayOfDouble[j] = paramDoubleFunction.apply(arrayOfDouble[j]);
      j += i;
    }
    return this;
  }
  
  public DoubleMatrix1D assign(DoubleMatrix1D paramDoubleMatrix1D)
  {
    if (!(paramDoubleMatrix1D instanceof DenseDoubleMatrix1D)) {
      return super.assign(paramDoubleMatrix1D);
    }
    DenseDoubleMatrix1D localDenseDoubleMatrix1D = (DenseDoubleMatrix1D)paramDoubleMatrix1D;
    if (localDenseDoubleMatrix1D == this) {
      return this;
    }
    checkSize(localDenseDoubleMatrix1D);
    if ((this.isNoView) && (localDenseDoubleMatrix1D.isNoView))
    {
      System.arraycopy(localDenseDoubleMatrix1D.elements, 0, this.elements, 0, this.elements.length);
      return this;
    }
    if (haveSharedCells(localDenseDoubleMatrix1D))
    {
      localObject = localDenseDoubleMatrix1D.copy();
      if (!(localObject instanceof DenseDoubleMatrix1D)) {
        return super.assign(paramDoubleMatrix1D);
      }
      localDenseDoubleMatrix1D = (DenseDoubleMatrix1D)localObject;
    }
    Object localObject = this.elements;
    double[] arrayOfDouble = localDenseDoubleMatrix1D.elements;
    if ((this.elements == null) || (arrayOfDouble == null)) {
      throw new InternalError();
    }
    int i = this.stride;
    int j = localDenseDoubleMatrix1D.stride;
    int k = index(0);
    int m = localDenseDoubleMatrix1D.index(0);
    int n = this.size;
    for (;;)
    {
      n--;
      if (n < 0) {
        break;
      }
      localObject[k] = arrayOfDouble[m];
      k += i;
      m += j;
    }
    return this;
  }
  
  public DoubleMatrix1D assign(DoubleMatrix1D paramDoubleMatrix1D, DoubleDoubleFunction paramDoubleDoubleFunction)
  {
    if (!(paramDoubleMatrix1D instanceof DenseDoubleMatrix1D)) {
      return super.assign(paramDoubleMatrix1D, paramDoubleDoubleFunction);
    }
    DenseDoubleMatrix1D localDenseDoubleMatrix1D = (DenseDoubleMatrix1D)paramDoubleMatrix1D;
    checkSize(paramDoubleMatrix1D);
    double[] arrayOfDouble1 = this.elements;
    double[] arrayOfDouble2 = localDenseDoubleMatrix1D.elements;
    if ((arrayOfDouble1 == null) || (arrayOfDouble2 == null)) {
      throw new InternalError();
    }
    int i = this.stride;
    int j = localDenseDoubleMatrix1D.stride;
    int k = index(0);
    int m = localDenseDoubleMatrix1D.index(0);
    int n;
    if (paramDoubleDoubleFunction == Functions.mult)
    {
      n = this.size;
      for (;;)
      {
        n--;
        if (n < 0) {
          break;
        }
        arrayOfDouble1[k] *= arrayOfDouble2[m];
        k += i;
        m += j;
      }
    }
    if (paramDoubleDoubleFunction == Functions.div)
    {
      n = this.size;
      for (;;)
      {
        n--;
        if (n < 0) {
          break;
        }
        arrayOfDouble1[k] /= arrayOfDouble2[m];
        k += i;
        m += j;
      }
    }
    if ((paramDoubleDoubleFunction instanceof PlusMult))
    {
      double d = ((PlusMult)paramDoubleDoubleFunction).multiplicator;
      if (d == 0.0D) {
        return this;
      }
      if (d == 1.0D)
      {
        i2 = this.size;
        for (;;)
        {
          i2--;
          if (i2 < 0) {
            break;
          }
          arrayOfDouble1[k] += arrayOfDouble2[m];
          k += i;
          m += j;
        }
      }
      if (d == -1.0D)
      {
        i2 = this.size;
        for (;;)
        {
          i2--;
          if (i2 < 0) {
            break;
          }
          arrayOfDouble1[k] -= arrayOfDouble2[m];
          k += i;
          m += j;
        }
      }
      int i2 = this.size;
      for (;;)
      {
        i2--;
        if (i2 < 0) {
          break;
        }
        arrayOfDouble1[k] += d * arrayOfDouble2[m];
        k += i;
        m += j;
      }
    }
    int i1 = this.size;
    for (;;)
    {
      i1--;
      if (i1 < 0) {
        break;
      }
      arrayOfDouble1[k] = paramDoubleDoubleFunction.apply(arrayOfDouble1[k], arrayOfDouble2[m]);
      k += i;
      m += j;
    }
    return this;
  }
  
  protected int cardinality(int paramInt)
  {
    int i = 0;
    int j = index(0);
    int k = this.stride;
    double[] arrayOfDouble = this.elements;
    int m = this.size;
    for (;;)
    {
      m--;
      if ((m < 0) || (i >= paramInt)) {
        break;
      }
      if (arrayOfDouble[j] != 0.0D) {
        i++;
      }
      j += k;
    }
    return i;
  }
  
  public double getQuick(int paramInt)
  {
    return this.elements[(this.zero + paramInt * this.stride)];
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
    return this.zero + paramInt * this.stride;
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
    this.elements[(this.zero + paramInt * this.stride)] = paramDouble;
  }
  
  public void swap(DoubleMatrix1D paramDoubleMatrix1D)
  {
    if (!(paramDoubleMatrix1D instanceof DenseDoubleMatrix1D)) {
      super.swap(paramDoubleMatrix1D);
    }
    DenseDoubleMatrix1D localDenseDoubleMatrix1D = (DenseDoubleMatrix1D)paramDoubleMatrix1D;
    if (localDenseDoubleMatrix1D == this) {
      return;
    }
    checkSize(localDenseDoubleMatrix1D);
    double[] arrayOfDouble1 = this.elements;
    double[] arrayOfDouble2 = localDenseDoubleMatrix1D.elements;
    if ((this.elements == null) || (arrayOfDouble2 == null)) {
      throw new InternalError();
    }
    int i = this.stride;
    int j = localDenseDoubleMatrix1D.stride;
    int k = index(0);
    int m = localDenseDoubleMatrix1D.index(0);
    int n = this.size;
    for (;;)
    {
      n--;
      if (n < 0) {
        break;
      }
      double d = arrayOfDouble1[k];
      arrayOfDouble1[k] = arrayOfDouble2[m];
      arrayOfDouble2[m] = d;
      k += i;
      m += j;
    }
  }
  
  public void toArray(double[] paramArrayOfDouble)
  {
    if (paramArrayOfDouble.length < this.size) {
      throw new IllegalArgumentException("values too small");
    }
    if (this.isNoView) {
      System.arraycopy(this.elements, 0, paramArrayOfDouble, 0, this.elements.length);
    } else {
      super.toArray(paramArrayOfDouble);
    }
  }
  
  protected DoubleMatrix1D viewSelectionLike(int[] paramArrayOfInt)
  {
    return new SelectedDenseDoubleMatrix1D(this.elements, paramArrayOfInt);
  }
  
  public double zDotProduct(DoubleMatrix1D paramDoubleMatrix1D, int paramInt1, int paramInt2)
  {
    if (!(paramDoubleMatrix1D instanceof DenseDoubleMatrix1D)) {
      return super.zDotProduct(paramDoubleMatrix1D, paramInt1, paramInt2);
    }
    DenseDoubleMatrix1D localDenseDoubleMatrix1D = (DenseDoubleMatrix1D)paramDoubleMatrix1D;
    int i = paramInt1 + paramInt2;
    if ((paramInt1 < 0) || (paramInt2 < 0)) {
      return 0.0D;
    }
    if (this.size < i) {
      i = this.size;
    }
    if (paramDoubleMatrix1D.size < i) {
      i = paramDoubleMatrix1D.size;
    }
    int j = i - paramInt1;
    int k = index(paramInt1);
    int m = localDenseDoubleMatrix1D.index(paramInt1);
    int n = this.stride;
    int i1 = localDenseDoubleMatrix1D.stride;
    double[] arrayOfDouble1 = this.elements;
    double[] arrayOfDouble2 = localDenseDoubleMatrix1D.elements;
    if ((arrayOfDouble1 == null) || (arrayOfDouble2 == null)) {
      throw new InternalError();
    }
    double d = 0.0D;
    k -= n;
    m -= i1;
    int i2 = j / 4;
    for (;;)
    {
      i2--;
      if (i2 < 0) {
        break;
      }
      d += arrayOfDouble1[(k += n)] * arrayOfDouble2[(m += i1)] + arrayOfDouble1[(k += n)] * arrayOfDouble2[(m += i1)] + arrayOfDouble1[(k += n)] * arrayOfDouble2[(m += i1)] + arrayOfDouble1[(k += n)] * arrayOfDouble2[(m += i1)];
    }
    i2 = j % 4;
    for (;;)
    {
      i2--;
      if (i2 < 0) {
        break;
      }
      d += arrayOfDouble1[(k += n)] * arrayOfDouble2[(m += i1)];
    }
    return d;
  }
  
  public double zSum()
  {
    double d = 0.0D;
    int i = this.stride;
    int j = index(0);
    double[] arrayOfDouble = this.elements;
    if (arrayOfDouble == null) {
      throw new InternalError();
    }
    int k = this.size;
    for (;;)
    {
      k--;
      if (k < 0) {
        break;
      }
      d += arrayOfDouble[j];
      j += i;
    }
    return d;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.impl.DenseDoubleMatrix1D
 * JD-Core Version:    0.7.0.1
 */