package cern.colt.matrix.impl;

import cern.colt.function.DoubleDoubleFunction;
import cern.colt.function.DoubleFunction;
import cern.colt.function.IntIntDoubleFunction;
import cern.colt.list.DoubleArrayList;
import cern.colt.list.IntArrayList;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.jet.math.Functions;
import cern.jet.math.Mult;
import cern.jet.math.PlusMult;

public class RCDoubleMatrix2D
  extends WrapperDoubleMatrix2D
{
  protected IntArrayList indexes;
  protected DoubleArrayList values;
  protected int[] starts;
  
  public RCDoubleMatrix2D(double[][] paramArrayOfDouble)
  {
    this(paramArrayOfDouble.length, paramArrayOfDouble.length == 0 ? 0 : paramArrayOfDouble[0].length);
    assign(paramArrayOfDouble);
  }
  
  public RCDoubleMatrix2D(int paramInt1, int paramInt2)
  {
    super(null);
    try
    {
      setUp(paramInt1, paramInt2);
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      if (!"matrix too large".equals(localIllegalArgumentException.getMessage())) {
        throw localIllegalArgumentException;
      }
    }
    this.indexes = new IntArrayList();
    this.values = new DoubleArrayList();
    this.starts = new int[paramInt1 + 1];
  }
  
  public DoubleMatrix2D assign(double paramDouble)
  {
    if (paramDouble == 0.0D)
    {
      this.indexes.clear();
      this.values.clear();
      int i = this.starts.length;
      for (;;)
      {
        i--;
        if (i < 0) {
          break;
        }
        this.starts[i] = 0;
      }
    }
    super.assign(paramDouble);
    return this;
  }
  
  public DoubleMatrix2D assign(DoubleFunction paramDoubleFunction)
  {
    if ((paramDoubleFunction instanceof Mult))
    {
      double d = ((Mult)paramDoubleFunction).multiplicator;
      if (d == 1.0D) {
        return this;
      }
      if (d == 0.0D) {
        return assign(0.0D);
      }
      if (d != d) {
        return assign(d);
      }
      double[] arrayOfDouble = this.values.elements();
      int i = this.values.size();
      for (;;)
      {
        i--;
        if (i < 0) {
          break;
        }
        arrayOfDouble[i] *= d;
      }
    }
    super.assign(paramDoubleFunction);
    return this;
  }
  
  public DoubleMatrix2D assign(DoubleMatrix2D paramDoubleMatrix2D)
  {
    if (paramDoubleMatrix2D == this) {
      return this;
    }
    checkShape(paramDoubleMatrix2D);
    if (!(paramDoubleMatrix2D instanceof RCDoubleMatrix2D))
    {
      assign(0.0D);
      paramDoubleMatrix2D.forEachNonZero(new IntIntDoubleFunction()
      {
        public double apply(int paramAnonymousInt1, int paramAnonymousInt2, double paramAnonymousDouble)
        {
          RCDoubleMatrix2D.this.setQuick(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousDouble);
          return paramAnonymousDouble;
        }
      });
      return this;
    }
    RCDoubleMatrix2D localRCDoubleMatrix2D = (RCDoubleMatrix2D)paramDoubleMatrix2D;
    System.arraycopy(localRCDoubleMatrix2D.starts, 0, this.starts, 0, this.starts.length);
    int i = localRCDoubleMatrix2D.indexes.size();
    this.indexes.setSize(i);
    this.values.setSize(i);
    this.indexes.replaceFromToWithFrom(0, i - 1, localRCDoubleMatrix2D.indexes, 0);
    this.values.replaceFromToWithFrom(0, i - 1, localRCDoubleMatrix2D.values, 0);
    return this;
  }
  
  public DoubleMatrix2D assign(DoubleMatrix2D paramDoubleMatrix2D, DoubleDoubleFunction paramDoubleDoubleFunction)
  {
    checkShape(paramDoubleMatrix2D);
    if ((paramDoubleDoubleFunction instanceof PlusMult))
    {
      double d = ((PlusMult)paramDoubleDoubleFunction).multiplicator;
      if (d == 0.0D) {
        return this;
      }
      paramDoubleMatrix2D.forEachNonZero(new IntIntDoubleFunction()
      {
        private final double val$alpha;
        
        public double apply(int paramAnonymousInt1, int paramAnonymousInt2, double paramAnonymousDouble)
        {
          RCDoubleMatrix2D.this.setQuick(paramAnonymousInt1, paramAnonymousInt2, RCDoubleMatrix2D.this.getQuick(paramAnonymousInt1, paramAnonymousInt2) + this.val$alpha * paramAnonymousDouble);
          return paramAnonymousDouble;
        }
      });
      return this;
    }
    int[] arrayOfInt;
    double[] arrayOfDouble;
    int i;
    int j;
    int k;
    int m;
    if (paramDoubleDoubleFunction == Functions.mult)
    {
      arrayOfInt = this.indexes.elements();
      arrayOfDouble = this.values.elements();
      i = this.starts.length - 1;
      for (;;)
      {
        i--;
        if (i < 0) {
          break;
        }
        j = this.starts[i];
        k = this.starts[(i + 1)];
        for (;;)
        {
          k--;
          if (k < j) {
            break;
          }
          m = arrayOfInt[k];
          arrayOfDouble[k] *= paramDoubleMatrix2D.getQuick(i, m);
          if (arrayOfDouble[k] == 0.0D) {
            remove(i, m);
          }
        }
      }
      return this;
    }
    if (paramDoubleDoubleFunction == Functions.div)
    {
      arrayOfInt = this.indexes.elements();
      arrayOfDouble = this.values.elements();
      i = this.starts.length - 1;
      for (;;)
      {
        i--;
        if (i < 0) {
          break;
        }
        j = this.starts[i];
        k = this.starts[(i + 1)];
        for (;;)
        {
          k--;
          if (k < j) {
            break;
          }
          m = arrayOfInt[k];
          arrayOfDouble[k] /= paramDoubleMatrix2D.getQuick(i, m);
          if (arrayOfDouble[k] == 0.0D) {
            remove(i, m);
          }
        }
      }
      return this;
    }
    return super.assign(paramDoubleMatrix2D, paramDoubleDoubleFunction);
  }
  
  public DoubleMatrix2D forEachNonZero(IntIntDoubleFunction paramIntIntDoubleFunction)
  {
    int[] arrayOfInt = this.indexes.elements();
    double[] arrayOfDouble = this.values.elements();
    int i = this.starts.length - 1;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      int j = this.starts[i];
      int k = this.starts[(i + 1)];
      for (;;)
      {
        k--;
        if (k < j) {
          break;
        }
        int m = arrayOfInt[k];
        double d1 = arrayOfDouble[k];
        double d2 = paramIntIntDoubleFunction.apply(i, m, d1);
        if (d2 != d1) {
          arrayOfDouble[k] = d2;
        }
      }
    }
    return this;
  }
  
  protected DoubleMatrix2D getContent()
  {
    return this;
  }
  
  public double getQuick(int paramInt1, int paramInt2)
  {
    int i = this.indexes.binarySearchFromTo(paramInt2, this.starts[paramInt1], this.starts[(paramInt1 + 1)] - 1);
    double d = 0.0D;
    if (i >= 0) {
      d = this.values.getQuick(i);
    }
    return d;
  }
  
  protected void insert(int paramInt1, int paramInt2, int paramInt3, double paramDouble)
  {
    this.indexes.beforeInsert(paramInt3, paramInt2);
    this.values.beforeInsert(paramInt3, paramDouble);
    int i = this.starts.length;
    for (;;)
    {
      i--;
      if (i <= paramInt1) {
        break;
      }
      this.starts[i] += 1;
    }
  }
  
  public DoubleMatrix2D like(int paramInt1, int paramInt2)
  {
    return new RCDoubleMatrix2D(paramInt1, paramInt2);
  }
  
  public DoubleMatrix1D like1D(int paramInt)
  {
    return new SparseDoubleMatrix1D(paramInt);
  }
  
  protected void remove(int paramInt1, int paramInt2)
  {
    this.indexes.remove(paramInt2);
    this.values.remove(paramInt2);
    int i = this.starts.length;
    for (;;)
    {
      i--;
      if (i <= paramInt1) {
        break;
      }
      this.starts[i] -= 1;
    }
  }
  
  public void setQuick(int paramInt1, int paramInt2, double paramDouble)
  {
    int i = this.indexes.binarySearchFromTo(paramInt2, this.starts[paramInt1], this.starts[(paramInt1 + 1)] - 1);
    if (i >= 0)
    {
      if (paramDouble == 0.0D) {
        remove(paramInt1, i);
      } else {
        this.values.setQuick(i, paramDouble);
      }
      return;
    }
    if (paramDouble != 0.0D)
    {
      i = -i - 1;
      insert(paramInt1, paramInt2, i, paramDouble);
    }
  }
  
  public void trimToSize()
  {
    this.indexes.trimToSize();
    this.values.trimToSize();
  }
  
  public DoubleMatrix1D zMult(DoubleMatrix1D paramDoubleMatrix1D1, DoubleMatrix1D paramDoubleMatrix1D2, double paramDouble1, double paramDouble2, boolean paramBoolean)
  {
    int i = this.rows;
    int j = this.columns;
    if (paramBoolean)
    {
      i = this.columns;
      j = this.rows;
    }
    int k = (paramDoubleMatrix1D2 == null) || (!paramBoolean) ? 1 : 0;
    if (paramDoubleMatrix1D2 == null) {
      paramDoubleMatrix1D2 = new DenseDoubleMatrix1D(i);
    }
    if ((!(paramDoubleMatrix1D1 instanceof DenseDoubleMatrix1D)) || (!(paramDoubleMatrix1D2 instanceof DenseDoubleMatrix1D))) {
      return super.zMult(paramDoubleMatrix1D1, paramDoubleMatrix1D2, paramDouble1, paramDouble2, paramBoolean);
    }
    if ((j != paramDoubleMatrix1D1.size()) || (i > paramDoubleMatrix1D2.size())) {
      throw new IllegalArgumentException("Incompatible args: " + (paramBoolean ? viewDice() : this).toStringShort() + ", " + paramDoubleMatrix1D1.toStringShort() + ", " + paramDoubleMatrix1D2.toStringShort());
    }
    DenseDoubleMatrix1D localDenseDoubleMatrix1D1 = (DenseDoubleMatrix1D)paramDoubleMatrix1D2;
    double[] arrayOfDouble1 = localDenseDoubleMatrix1D1.elements;
    int m = localDenseDoubleMatrix1D1.stride;
    int n = paramDoubleMatrix1D2.index(0);
    DenseDoubleMatrix1D localDenseDoubleMatrix1D2 = (DenseDoubleMatrix1D)paramDoubleMatrix1D1;
    double[] arrayOfDouble2 = localDenseDoubleMatrix1D2.elements;
    int i1 = localDenseDoubleMatrix1D2.stride;
    int i2 = paramDoubleMatrix1D1.index(0);
    if ((arrayOfDouble2 == null) || (arrayOfDouble1 == null)) {
      throw new InternalError();
    }
    int[] arrayOfInt = this.indexes.elements();
    double[] arrayOfDouble3 = this.values.elements();
    int i3 = this.starts.length - 1;
    int i5;
    double d;
    int i6;
    int i7;
    if (!paramBoolean) {
      for (i4 = 0; i4 < i3; i4++)
      {
        i5 = this.starts[(i4 + 1)];
        d = 0.0D;
        for (i6 = this.starts[i4]; i6 < i5; i6++)
        {
          i7 = arrayOfInt[i6];
          d += arrayOfDouble3[i6] * arrayOfDouble2[(i2 + i1 * i7)];
        }
        arrayOfDouble1[n] = (paramDouble1 * d + paramDouble2 * arrayOfDouble1[n]);
        n += m;
      }
    }
    if (k == 0) {
      paramDoubleMatrix1D2.assign(Functions.mult(paramDouble2));
    }
    for (int i4 = 0; i4 < i3; i4++)
    {
      i5 = this.starts[(i4 + 1)];
      d = paramDouble1 * arrayOfDouble2[(i2 + i1 * i4)];
      for (i6 = this.starts[i4]; i6 < i5; i6++)
      {
        i7 = arrayOfInt[i6];
        arrayOfDouble1[(n + m * i7)] += arrayOfDouble3[i6] * d;
      }
    }
    return paramDoubleMatrix1D2;
  }
  
  public DoubleMatrix2D zMult(DoubleMatrix2D paramDoubleMatrix2D1, DoubleMatrix2D paramDoubleMatrix2D2, double paramDouble1, double paramDouble2, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (paramBoolean2) {
      paramDoubleMatrix2D1 = paramDoubleMatrix2D1.viewDice();
    }
    int i = this.rows;
    int j = this.columns;
    if (paramBoolean1)
    {
      i = this.columns;
      j = this.rows;
    }
    int k = paramDoubleMatrix2D1.columns;
    int m = paramDoubleMatrix2D2 == null ? 1 : 0;
    if (paramDoubleMatrix2D2 == null) {
      paramDoubleMatrix2D2 = new DenseDoubleMatrix2D(i, k);
    }
    if (paramDoubleMatrix2D1.rows != j) {
      throw new IllegalArgumentException("Matrix2D inner dimensions must agree:" + toStringShort() + ", " + (paramBoolean2 ? paramDoubleMatrix2D1.viewDice() : paramDoubleMatrix2D1).toStringShort());
    }
    if ((paramDoubleMatrix2D2.rows != i) || (paramDoubleMatrix2D2.columns != k)) {
      throw new IllegalArgumentException("Incompatibel result matrix: " + toStringShort() + ", " + (paramBoolean2 ? paramDoubleMatrix2D1.viewDice() : paramDoubleMatrix2D1).toStringShort() + ", " + paramDoubleMatrix2D2.toStringShort());
    }
    if ((this == paramDoubleMatrix2D2) || (paramDoubleMatrix2D1 == paramDoubleMatrix2D2)) {
      throw new IllegalArgumentException("Matrices must not be identical");
    }
    if (m == 0) {
      paramDoubleMatrix2D2.assign(Functions.mult(paramDouble2));
    }
    DoubleMatrix1D[] arrayOfDoubleMatrix1D1 = new DoubleMatrix1D[j];
    int n = j;
    for (;;)
    {
      n--;
      if (n < 0) {
        break;
      }
      arrayOfDoubleMatrix1D1[n] = paramDoubleMatrix2D1.viewRow(n);
    }
    DoubleMatrix1D[] arrayOfDoubleMatrix1D2 = new DoubleMatrix1D[i];
    int i1 = i;
    for (;;)
    {
      i1--;
      if (i1 < 0) {
        break;
      }
      arrayOfDoubleMatrix1D2[i1] = paramDoubleMatrix2D2.viewRow(i1);
    }
    PlusMult localPlusMult = PlusMult.plusMult(0.0D);
    int[] arrayOfInt = this.indexes.elements();
    double[] arrayOfDouble = this.values.elements();
    int i2 = this.starts.length - 1;
    for (;;)
    {
      i2--;
      if (i2 < 0) {
        break;
      }
      int i3 = this.starts[i2];
      int i4 = this.starts[(i2 + 1)];
      for (;;)
      {
        i4--;
        if (i4 < i3) {
          break;
        }
        int i5 = arrayOfInt[i4];
        localPlusMult.multiplicator = (arrayOfDouble[i4] * paramDouble1);
        if (!paramBoolean1) {
          arrayOfDoubleMatrix1D2[i2].assign(arrayOfDoubleMatrix1D1[i5], localPlusMult);
        } else {
          arrayOfDoubleMatrix1D2[i5].assign(arrayOfDoubleMatrix1D1[i2], localPlusMult);
        }
      }
    }
    return paramDoubleMatrix2D2;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.impl.RCDoubleMatrix2D
 * JD-Core Version:    0.7.0.1
 */