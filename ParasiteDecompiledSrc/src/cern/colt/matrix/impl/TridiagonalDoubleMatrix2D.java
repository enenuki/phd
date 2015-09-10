package cern.colt.matrix.impl;

import cern.colt.function.DoubleDoubleFunction;
import cern.colt.function.DoubleFunction;
import cern.colt.function.IntIntDoubleFunction;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.jet.math.Functions;
import cern.jet.math.Mult;
import cern.jet.math.PlusMult;

class TridiagonalDoubleMatrix2D
  extends WrapperDoubleMatrix2D
{
  protected double[] values;
  protected int[] dims;
  protected static final int NONZERO = 4;
  
  public TridiagonalDoubleMatrix2D(double[][] paramArrayOfDouble)
  {
    this(paramArrayOfDouble.length, paramArrayOfDouble.length == 0 ? 0 : paramArrayOfDouble[0].length);
    assign(paramArrayOfDouble);
  }
  
  public TridiagonalDoubleMatrix2D(int paramInt1, int paramInt2)
  {
    super(null);
    setUp(paramInt1, paramInt2);
    int i = Math.min(paramInt1, paramInt2);
    int j = i - 1;
    int k = i - 1;
    if (paramInt1 > paramInt2) {
      k++;
    }
    if (paramInt1 < paramInt2) {
      j++;
    }
    this.values = new double[k + i + j];
    int[] arrayOfInt = { 0, k, k + i, k + i + j, 0, 0, 0 };
    this.dims = arrayOfInt;
  }
  
  public DoubleMatrix2D assign(double paramDouble)
  {
    if (paramDouble == 0.0D)
    {
      int i = this.values.length;
      for (;;)
      {
        i--;
        if (i < 0) {
          break;
        }
        this.values[i] = 0.0D;
      }
      i = this.dims.length;
      for (;;)
      {
        i--;
        if (i < 4) {
          break;
        }
        this.dims[i] = 0;
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
      forEachNonZero(new IntIntDoubleFunction()
      {
        private final DoubleFunction val$function;
        
        public double apply(int paramAnonymousInt1, int paramAnonymousInt2, double paramAnonymousDouble)
        {
          return this.val$function.apply(paramAnonymousDouble);
        }
      });
    }
    else
    {
      super.assign(paramDoubleFunction);
    }
    return this;
  }
  
  public DoubleMatrix2D assign(DoubleMatrix2D paramDoubleMatrix2D)
  {
    if (paramDoubleMatrix2D == this) {
      return this;
    }
    checkShape(paramDoubleMatrix2D);
    if ((paramDoubleMatrix2D instanceof TridiagonalDoubleMatrix2D))
    {
      TridiagonalDoubleMatrix2D localTridiagonalDoubleMatrix2D = (TridiagonalDoubleMatrix2D)paramDoubleMatrix2D;
      System.arraycopy(localTridiagonalDoubleMatrix2D.values, 0, this.values, 0, this.values.length);
      System.arraycopy(localTridiagonalDoubleMatrix2D.dims, 0, this.dims, 0, this.dims.length);
      return this;
    }
    if (((paramDoubleMatrix2D instanceof RCDoubleMatrix2D)) || ((paramDoubleMatrix2D instanceof SparseDoubleMatrix2D)))
    {
      assign(0.0D);
      paramDoubleMatrix2D.forEachNonZero(new IntIntDoubleFunction()
      {
        public double apply(int paramAnonymousInt1, int paramAnonymousInt2, double paramAnonymousDouble)
        {
          TridiagonalDoubleMatrix2D.this.setQuick(paramAnonymousInt1, paramAnonymousInt2, paramAnonymousDouble);
          return paramAnonymousDouble;
        }
      });
      return this;
    }
    return super.assign(paramDoubleMatrix2D);
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
          TridiagonalDoubleMatrix2D.this.setQuick(paramAnonymousInt1, paramAnonymousInt2, TridiagonalDoubleMatrix2D.this.getQuick(paramAnonymousInt1, paramAnonymousInt2) + this.val$alpha * paramAnonymousDouble);
          return paramAnonymousDouble;
        }
      });
      return this;
    }
    if (paramDoubleDoubleFunction == Functions.mult)
    {
      forEachNonZero(new IntIntDoubleFunction()
      {
        private final DoubleMatrix2D val$y;
        
        public double apply(int paramAnonymousInt1, int paramAnonymousInt2, double paramAnonymousDouble)
        {
          TridiagonalDoubleMatrix2D.this.setQuick(paramAnonymousInt1, paramAnonymousInt2, TridiagonalDoubleMatrix2D.this.getQuick(paramAnonymousInt1, paramAnonymousInt2) * this.val$y.getQuick(paramAnonymousInt1, paramAnonymousInt2));
          return paramAnonymousDouble;
        }
      });
      return this;
    }
    if (paramDoubleDoubleFunction == Functions.div)
    {
      forEachNonZero(new IntIntDoubleFunction()
      {
        private final DoubleMatrix2D val$y;
        
        public double apply(int paramAnonymousInt1, int paramAnonymousInt2, double paramAnonymousDouble)
        {
          TridiagonalDoubleMatrix2D.this.setQuick(paramAnonymousInt1, paramAnonymousInt2, TridiagonalDoubleMatrix2D.this.getQuick(paramAnonymousInt1, paramAnonymousInt2) / this.val$y.getQuick(paramAnonymousInt1, paramAnonymousInt2));
          return paramAnonymousDouble;
        }
      });
      return this;
    }
    return super.assign(paramDoubleMatrix2D, paramDoubleDoubleFunction);
  }
  
  public DoubleMatrix2D forEachNonZero(IntIntDoubleFunction paramIntIntDoubleFunction)
  {
    for (int i = 0; i <= 2; i++)
    {
      int j = 0;
      int k = 0;
      switch (i)
      {
      case 0: 
        j = 1;
      case 2: 
        k = 1;
      }
      int m = this.dims[i];
      int n = this.dims[(i + 1)];
      int i1 = m;
      while (i1 < n)
      {
        double d1 = this.values[i1];
        if (d1 != 0.0D)
        {
          double d2 = paramIntIntDoubleFunction.apply(j, k, d1);
          if (d2 != d1)
          {
            if (d2 == 0.0D) {
              this.dims[(i + 4)] += 1;
            }
            this.values[i1] = d2;
          }
        }
        i1++;
        j++;
        k++;
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
    int i = paramInt1;
    int j = paramInt2;
    int k = j - i + 1;
    int m = i;
    if (k == 0) {
      m = j;
    }
    if ((k >= 0) && (k <= 2)) {
      return this.values[(this.dims[k] + m)];
    }
    return 0.0D;
  }
  
  public DoubleMatrix2D like(int paramInt1, int paramInt2)
  {
    return new TridiagonalDoubleMatrix2D(paramInt1, paramInt2);
  }
  
  public DoubleMatrix1D like1D(int paramInt)
  {
    return new SparseDoubleMatrix1D(paramInt);
  }
  
  public void setQuick(int paramInt1, int paramInt2, double paramDouble)
  {
    int i = paramInt1;
    int j = paramInt2;
    int k = paramDouble == 0.0D ? 1 : 0;
    int m = j - i + 1;
    int n = i;
    if (m == 0) {
      n = j;
    }
    if ((m >= 0) && (m <= 2))
    {
      int i1 = this.dims[m] + n;
      if (this.values[i1] != 0.0D)
      {
        if (k != 0) {
          this.dims[(m + 4)] -= 1;
        }
      }
      else if (k == 0) {
        this.dims[(m + 4)] += 1;
      }
      this.values[i1] = paramDouble;
      return;
    }
    if (k == 0) {
      throw new IllegalArgumentException("Can't store non-zero value to non-tridiagonal coordinate: row=" + paramInt1 + ", column=" + paramInt2 + ", value=" + paramDouble);
    }
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
    int k = paramDoubleMatrix1D2 == null ? 1 : 0;
    if (paramDoubleMatrix1D2 == null) {
      paramDoubleMatrix1D2 = new DenseDoubleMatrix1D(i);
    }
    if ((!this.isNoView) || (!(paramDoubleMatrix1D1 instanceof DenseDoubleMatrix1D)) || (!(paramDoubleMatrix1D2 instanceof DenseDoubleMatrix1D))) {
      return super.zMult(paramDoubleMatrix1D1, paramDoubleMatrix1D2, paramDouble1, paramDouble2, paramBoolean);
    }
    if ((j != paramDoubleMatrix1D1.size()) || (i > paramDoubleMatrix1D2.size())) {
      throw new IllegalArgumentException("Incompatible args: " + (paramBoolean ? viewDice() : this).toStringShort() + ", " + paramDoubleMatrix1D1.toStringShort() + ", " + paramDoubleMatrix1D2.toStringShort());
    }
    if (k == 0) {
      paramDoubleMatrix1D2.assign(Functions.mult(paramDouble2 / paramDouble1));
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
    forEachNonZero(new IntIntDoubleFunction()
    {
      private final boolean val$transposeA;
      private final double[] val$zElements;
      private final int val$zi;
      private final int val$zStride;
      private final double[] val$yElements;
      private final int val$yi;
      private final int val$yStride;
      
      public double apply(int paramAnonymousInt1, int paramAnonymousInt2, double paramAnonymousDouble)
      {
        if (this.val$transposeA)
        {
          int i = paramAnonymousInt1;
          paramAnonymousInt1 = paramAnonymousInt2;
          paramAnonymousInt2 = i;
        }
        this.val$zElements[(this.val$zi + this.val$zStride * paramAnonymousInt1)] += paramAnonymousDouble * this.val$yElements[(this.val$yi + this.val$yStride * paramAnonymousInt2)];
        return paramAnonymousDouble;
      }
    });
    if (paramDouble1 != 1.0D) {
      paramDoubleMatrix1D2.assign(Functions.mult(paramDouble1));
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
    forEachNonZero(new IntIntDoubleFunction()
    {
      private final PlusMult val$fun;
      private final double val$alpha;
      private final boolean val$transposeA;
      private final DoubleMatrix1D[] val$Crows;
      private final DoubleMatrix1D[] val$Brows;
      
      public double apply(int paramAnonymousInt1, int paramAnonymousInt2, double paramAnonymousDouble)
      {
        this.val$fun.multiplicator = (paramAnonymousDouble * this.val$alpha);
        if (!this.val$transposeA) {
          this.val$Crows[paramAnonymousInt1].assign(this.val$Brows[paramAnonymousInt2], this.val$fun);
        } else {
          this.val$Crows[paramAnonymousInt2].assign(this.val$Brows[paramAnonymousInt1], this.val$fun);
        }
        return paramAnonymousDouble;
      }
    });
    return paramDoubleMatrix2D2;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.impl.TridiagonalDoubleMatrix2D
 * JD-Core Version:    0.7.0.1
 */