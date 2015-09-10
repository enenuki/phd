package cern.colt.matrix.impl;

import cern.colt.function.DoubleDoubleFunction;
import cern.colt.function.DoubleFunction;
import cern.colt.function.IntDoubleProcedure;
import cern.colt.function.IntIntDoubleFunction;
import cern.colt.map.AbstractIntDoubleMap;
import cern.colt.map.OpenIntDoubleHashMap;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.jet.math.Functions;
import cern.jet.math.Mult;
import cern.jet.math.PlusMult;

public class SparseDoubleMatrix2D
  extends DoubleMatrix2D
{
  protected AbstractIntDoubleMap elements;
  protected int dummy;
  
  public SparseDoubleMatrix2D(double[][] paramArrayOfDouble)
  {
    this(paramArrayOfDouble.length, paramArrayOfDouble.length == 0 ? 0 : paramArrayOfDouble[0].length);
    assign(paramArrayOfDouble);
  }
  
  public SparseDoubleMatrix2D(int paramInt1, int paramInt2)
  {
    this(paramInt1, paramInt2, paramInt1 * (paramInt2 / 1000), 0.2D, 0.5D);
  }
  
  public SparseDoubleMatrix2D(int paramInt1, int paramInt2, int paramInt3, double paramDouble1, double paramDouble2)
  {
    setUp(paramInt1, paramInt2);
    this.elements = new OpenIntDoubleHashMap(paramInt3, paramDouble1, paramDouble2);
  }
  
  protected SparseDoubleMatrix2D(int paramInt1, int paramInt2, AbstractIntDoubleMap paramAbstractIntDoubleMap, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    setUp(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
    this.elements = paramAbstractIntDoubleMap;
    this.isNoView = false;
  }
  
  public DoubleMatrix2D assign(double paramDouble)
  {
    if ((this.isNoView) && (paramDouble == 0.0D)) {
      this.elements.clear();
    } else {
      super.assign(paramDouble);
    }
    return this;
  }
  
  public DoubleMatrix2D assign(DoubleFunction paramDoubleFunction)
  {
    if ((this.isNoView) && ((paramDoubleFunction instanceof Mult))) {
      this.elements.assign(paramDoubleFunction);
    } else {
      super.assign(paramDoubleFunction);
    }
    return this;
  }
  
  public DoubleMatrix2D assign(DoubleMatrix2D paramDoubleMatrix2D)
  {
    if (!(paramDoubleMatrix2D instanceof SparseDoubleMatrix2D)) {
      return super.assign(paramDoubleMatrix2D);
    }
    SparseDoubleMatrix2D localSparseDoubleMatrix2D = (SparseDoubleMatrix2D)paramDoubleMatrix2D;
    if (localSparseDoubleMatrix2D == this) {
      return this;
    }
    checkShape(localSparseDoubleMatrix2D);
    if ((this.isNoView) && (localSparseDoubleMatrix2D.isNoView))
    {
      this.elements.assign(localSparseDoubleMatrix2D.elements);
      return this;
    }
    return super.assign(paramDoubleMatrix2D);
  }
  
  public DoubleMatrix2D assign(DoubleMatrix2D paramDoubleMatrix2D, DoubleDoubleFunction paramDoubleDoubleFunction)
  {
    if (!this.isNoView) {
      return super.assign(paramDoubleMatrix2D, paramDoubleDoubleFunction);
    }
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
          SparseDoubleMatrix2D.this.setQuick(paramAnonymousInt1, paramAnonymousInt2, SparseDoubleMatrix2D.this.getQuick(paramAnonymousInt1, paramAnonymousInt2) + this.val$alpha * paramAnonymousDouble);
          return paramAnonymousDouble;
        }
      });
      return this;
    }
    if (paramDoubleDoubleFunction == Functions.mult) {
      this.elements.forEachPair(new IntDoubleProcedure()
      {
        private final DoubleMatrix2D val$y;
        
        public boolean apply(int paramAnonymousInt, double paramAnonymousDouble)
        {
          int i = paramAnonymousInt / SparseDoubleMatrix2D.this.columns;
          int j = paramAnonymousInt % SparseDoubleMatrix2D.this.columns;
          double d = paramAnonymousDouble * this.val$y.getQuick(i, j);
          if (d != paramAnonymousDouble) {
            SparseDoubleMatrix2D.this.elements.put(paramAnonymousInt, d);
          }
          return true;
        }
      });
    }
    if (paramDoubleDoubleFunction == Functions.div) {
      this.elements.forEachPair(new IntDoubleProcedure()
      {
        private final DoubleMatrix2D val$y;
        
        public boolean apply(int paramAnonymousInt, double paramAnonymousDouble)
        {
          int i = paramAnonymousInt / SparseDoubleMatrix2D.this.columns;
          int j = paramAnonymousInt % SparseDoubleMatrix2D.this.columns;
          double d = paramAnonymousDouble / this.val$y.getQuick(i, j);
          if (d != paramAnonymousDouble) {
            SparseDoubleMatrix2D.this.elements.put(paramAnonymousInt, d);
          }
          return true;
        }
      });
    }
    return super.assign(paramDoubleMatrix2D, paramDoubleDoubleFunction);
  }
  
  public int cardinality()
  {
    if (this.isNoView) {
      return this.elements.size();
    }
    return super.cardinality();
  }
  
  public void ensureCapacity(int paramInt)
  {
    this.elements.ensureCapacity(paramInt);
  }
  
  public DoubleMatrix2D forEachNonZero(IntIntDoubleFunction paramIntIntDoubleFunction)
  {
    if (this.isNoView) {
      this.elements.forEachPair(new IntDoubleProcedure()
      {
        private final IntIntDoubleFunction val$function;
        
        public boolean apply(int paramAnonymousInt, double paramAnonymousDouble)
        {
          int i = paramAnonymousInt / SparseDoubleMatrix2D.this.columns;
          int j = paramAnonymousInt % SparseDoubleMatrix2D.this.columns;
          double d = this.val$function.apply(i, j, paramAnonymousDouble);
          if (d != paramAnonymousDouble) {
            SparseDoubleMatrix2D.this.elements.put(paramAnonymousInt, d);
          }
          return true;
        }
      });
    } else {
      super.forEachNonZero(paramIntIntDoubleFunction);
    }
    return this;
  }
  
  public double getQuick(int paramInt1, int paramInt2)
  {
    return this.elements.get(this.rowZero + paramInt1 * this.rowStride + this.columnZero + paramInt2 * this.columnStride);
  }
  
  protected boolean haveSharedCellsRaw(DoubleMatrix2D paramDoubleMatrix2D)
  {
    Object localObject;
    if ((paramDoubleMatrix2D instanceof SelectedSparseDoubleMatrix2D))
    {
      localObject = (SelectedSparseDoubleMatrix2D)paramDoubleMatrix2D;
      return this.elements == ((SelectedSparseDoubleMatrix2D)localObject).elements;
    }
    if ((paramDoubleMatrix2D instanceof SparseDoubleMatrix2D))
    {
      localObject = (SparseDoubleMatrix2D)paramDoubleMatrix2D;
      return this.elements == ((SparseDoubleMatrix2D)localObject).elements;
    }
    return false;
  }
  
  protected int index(int paramInt1, int paramInt2)
  {
    return this.rowZero + paramInt1 * this.rowStride + this.columnZero + paramInt2 * this.columnStride;
  }
  
  public DoubleMatrix2D like(int paramInt1, int paramInt2)
  {
    return new SparseDoubleMatrix2D(paramInt1, paramInt2);
  }
  
  public DoubleMatrix1D like1D(int paramInt)
  {
    return new SparseDoubleMatrix1D(paramInt);
  }
  
  protected DoubleMatrix1D like1D(int paramInt1, int paramInt2, int paramInt3)
  {
    return new SparseDoubleMatrix1D(paramInt1, this.elements, paramInt2, paramInt3);
  }
  
  public void setQuick(int paramInt1, int paramInt2, double paramDouble)
  {
    int i = this.rowZero + paramInt1 * this.rowStride + this.columnZero + paramInt2 * this.columnStride;
    if (paramDouble == 0.0D) {
      this.elements.removeKey(i);
    } else {
      this.elements.put(i, paramDouble);
    }
  }
  
  public void trimToSize()
  {
    this.elements.trimToSize();
  }
  
  protected DoubleMatrix2D viewSelectionLike(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    return new SelectedSparseDoubleMatrix2D(this.elements, paramArrayOfInt1, paramArrayOfInt2, 0);
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
    this.elements.forEachPair(new IntDoubleProcedure()
    {
      private final boolean val$transposeA;
      private final double[] val$zElements;
      private final int val$zi;
      private final int val$zStride;
      private final double[] val$yElements;
      private final int val$yi;
      private final int val$yStride;
      
      public boolean apply(int paramAnonymousInt, double paramAnonymousDouble)
      {
        int i = paramAnonymousInt / SparseDoubleMatrix2D.this.columns;
        int j = paramAnonymousInt % SparseDoubleMatrix2D.this.columns;
        if (this.val$transposeA)
        {
          int k = i;
          i = j;
          j = k;
        }
        this.val$zElements[(this.val$zi + this.val$zStride * i)] += paramAnonymousDouble * this.val$yElements[(this.val$yi + this.val$yStride * j)];
        return true;
      }
    });
    if (paramDouble1 != 1.0D) {
      paramDoubleMatrix1D2.assign(Functions.mult(paramDouble1));
    }
    return paramDoubleMatrix1D2;
  }
  
  public DoubleMatrix2D zMult(DoubleMatrix2D paramDoubleMatrix2D1, DoubleMatrix2D paramDoubleMatrix2D2, double paramDouble1, double paramDouble2, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (!this.isNoView) {
      return super.zMult(paramDoubleMatrix2D1, paramDoubleMatrix2D2, paramDouble1, paramDouble2, paramBoolean1, paramBoolean2);
    }
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
    this.elements.forEachPair(new IntDoubleProcedure()
    {
      private final PlusMult val$fun;
      private final double val$alpha;
      private final boolean val$transposeA;
      private final DoubleMatrix1D[] val$Crows;
      private final DoubleMatrix1D[] val$Brows;
      
      public boolean apply(int paramAnonymousInt, double paramAnonymousDouble)
      {
        int i = paramAnonymousInt / SparseDoubleMatrix2D.this.columns;
        int j = paramAnonymousInt % SparseDoubleMatrix2D.this.columns;
        this.val$fun.multiplicator = (paramAnonymousDouble * this.val$alpha);
        if (!this.val$transposeA) {
          this.val$Crows[i].assign(this.val$Brows[j], this.val$fun);
        } else {
          this.val$Crows[j].assign(this.val$Brows[i], this.val$fun);
        }
        return true;
      }
    });
    return paramDoubleMatrix2D2;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.impl.SparseDoubleMatrix2D
 * JD-Core Version:    0.7.0.1
 */