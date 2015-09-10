package cern.colt.matrix.linalg;

import EDU.oswego.cs.dl.util.concurrent.FJTask;
import EDU.oswego.cs.dl.util.concurrent.FJTaskRunnerGroup;
import cern.colt.function.DoubleDoubleFunction;
import cern.colt.function.DoubleFunction;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;

public class SmpBlas
  implements Blas
{
  public static Blas smpBlas = SeqBlas.seqBlas;
  protected Blas seqBlas;
  protected Smp smp;
  protected int maxThreads;
  protected static int NN_THRESHOLD = 30000;
  
  protected SmpBlas(int paramInt, Blas paramBlas)
  {
    this.seqBlas = paramBlas;
    this.maxThreads = paramInt;
    this.smp = new Smp(paramInt);
  }
  
  public static void allocateBlas(int paramInt, Blas paramBlas)
  {
    if ((smpBlas instanceof SmpBlas))
    {
      SmpBlas localSmpBlas = (SmpBlas)smpBlas;
      if ((localSmpBlas.maxThreads == paramInt) && (localSmpBlas.seqBlas == paramBlas)) {
        return;
      }
    }
    if (paramInt <= 1) {
      smpBlas = paramBlas;
    } else {
      smpBlas = new SmpBlas(paramInt, paramBlas);
    }
  }
  
  public void assign(DoubleMatrix2D paramDoubleMatrix2D, DoubleFunction paramDoubleFunction)
  {
    run(paramDoubleMatrix2D, false, new Matrix2DMatrix2DFunction()
    {
      private final DoubleFunction val$function;
      
      public double apply(DoubleMatrix2D paramAnonymousDoubleMatrix2D1, DoubleMatrix2D paramAnonymousDoubleMatrix2D2)
      {
        SmpBlas.this.seqBlas.assign(paramAnonymousDoubleMatrix2D1, this.val$function);
        return 0.0D;
      }
    });
  }
  
  public void assign(DoubleMatrix2D paramDoubleMatrix2D1, DoubleMatrix2D paramDoubleMatrix2D2, DoubleDoubleFunction paramDoubleDoubleFunction)
  {
    run(paramDoubleMatrix2D1, paramDoubleMatrix2D2, false, new Matrix2DMatrix2DFunction()
    {
      private final DoubleDoubleFunction val$function;
      
      public double apply(DoubleMatrix2D paramAnonymousDoubleMatrix2D1, DoubleMatrix2D paramAnonymousDoubleMatrix2D2)
      {
        SmpBlas.this.seqBlas.assign(paramAnonymousDoubleMatrix2D1, paramAnonymousDoubleMatrix2D2, this.val$function);
        return 0.0D;
      }
    });
  }
  
  public double dasum(DoubleMatrix1D paramDoubleMatrix1D)
  {
    return this.seqBlas.dasum(paramDoubleMatrix1D);
  }
  
  public void daxpy(double paramDouble, DoubleMatrix1D paramDoubleMatrix1D1, DoubleMatrix1D paramDoubleMatrix1D2)
  {
    this.seqBlas.daxpy(paramDouble, paramDoubleMatrix1D1, paramDoubleMatrix1D2);
  }
  
  public void daxpy(double paramDouble, DoubleMatrix2D paramDoubleMatrix2D1, DoubleMatrix2D paramDoubleMatrix2D2)
  {
    this.seqBlas.daxpy(paramDouble, paramDoubleMatrix2D1, paramDoubleMatrix2D2);
  }
  
  public void dcopy(DoubleMatrix1D paramDoubleMatrix1D1, DoubleMatrix1D paramDoubleMatrix1D2)
  {
    this.seqBlas.dcopy(paramDoubleMatrix1D1, paramDoubleMatrix1D2);
  }
  
  public void dcopy(DoubleMatrix2D paramDoubleMatrix2D1, DoubleMatrix2D paramDoubleMatrix2D2)
  {
    this.seqBlas.dcopy(paramDoubleMatrix2D1, paramDoubleMatrix2D2);
  }
  
  public double ddot(DoubleMatrix1D paramDoubleMatrix1D1, DoubleMatrix1D paramDoubleMatrix1D2)
  {
    return this.seqBlas.ddot(paramDoubleMatrix1D1, paramDoubleMatrix1D2);
  }
  
  public void dgemm(boolean paramBoolean1, boolean paramBoolean2, double paramDouble1, DoubleMatrix2D paramDoubleMatrix2D1, DoubleMatrix2D paramDoubleMatrix2D2, double paramDouble2, DoubleMatrix2D paramDoubleMatrix2D3)
  {
    if (paramBoolean1)
    {
      dgemm(false, paramBoolean2, paramDouble1, paramDoubleMatrix2D1.viewDice(), paramDoubleMatrix2D2, paramDouble2, paramDoubleMatrix2D3);
      return;
    }
    if (paramBoolean2)
    {
      dgemm(paramBoolean1, false, paramDouble1, paramDoubleMatrix2D1, paramDoubleMatrix2D2.viewDice(), paramDouble2, paramDoubleMatrix2D3);
      return;
    }
    int i = paramDoubleMatrix2D1.rows();
    int j = paramDoubleMatrix2D1.columns();
    int k = paramDoubleMatrix2D2.columns();
    if (paramDoubleMatrix2D2.rows() != j) {
      throw new IllegalArgumentException("Matrix2D inner dimensions must agree:" + paramDoubleMatrix2D1.toStringShort() + ", " + paramDoubleMatrix2D2.toStringShort());
    }
    if ((paramDoubleMatrix2D3.rows() != i) || (paramDoubleMatrix2D3.columns() != k)) {
      throw new IllegalArgumentException("Incompatibel result matrix: " + paramDoubleMatrix2D1.toStringShort() + ", " + paramDoubleMatrix2D2.toStringShort() + ", " + paramDoubleMatrix2D3.toStringShort());
    }
    if ((paramDoubleMatrix2D1 == paramDoubleMatrix2D3) || (paramDoubleMatrix2D2 == paramDoubleMatrix2D3)) {
      throw new IllegalArgumentException("Matrices must not be identical");
    }
    long l = 2L * i * j * k;
    int m = (int)Math.min(l / 30000L, this.maxThreads);
    int n = k >= m ? 1 : 0;
    int i1 = n != 0 ? k : i;
    m = Math.min(i1, m);
    if (m < 2)
    {
      this.seqBlas.dgemm(paramBoolean1, paramBoolean2, paramDouble1, paramDoubleMatrix2D1, paramDoubleMatrix2D2, paramDouble2, paramDoubleMatrix2D3);
      return;
    }
    int i2 = i1 / m;
    FJTask[] arrayOfFJTask = new FJTask[m];
    for (int i3 = 0; i3 < m; i3++)
    {
      int i4 = i3 * i2;
      if (i3 == m - 1) {
        i2 = i1 - i2 * i3;
      }
      DoubleMatrix2D localDoubleMatrix2D1;
      DoubleMatrix2D localDoubleMatrix2D2;
      DoubleMatrix2D localDoubleMatrix2D3;
      if (n != 0)
      {
        localDoubleMatrix2D1 = paramDoubleMatrix2D1;
        localDoubleMatrix2D2 = paramDoubleMatrix2D2.viewPart(0, i4, j, i2);
        localDoubleMatrix2D3 = paramDoubleMatrix2D3.viewPart(0, i4, i, i2);
      }
      else
      {
        localDoubleMatrix2D1 = paramDoubleMatrix2D1.viewPart(i4, 0, i2, j);
        localDoubleMatrix2D2 = paramDoubleMatrix2D2;
        localDoubleMatrix2D3 = paramDoubleMatrix2D3.viewPart(i4, 0, i2, k);
      }
      arrayOfFJTask[i3 = new FJTask()
      {
        private final boolean val$transposeA;
        private final boolean val$transposeB;
        private final double val$alpha;
        private final DoubleMatrix2D val$AA;
        private final DoubleMatrix2D val$BB;
        private final double val$beta;
        private final DoubleMatrix2D val$CC;
        
        public void run()
        {
          SmpBlas.this.seqBlas.dgemm(this.val$transposeA, this.val$transposeB, this.val$alpha, this.val$AA, this.val$BB, this.val$beta, this.val$CC);
        }
      };
    }
    try
    {
      this.smp.taskGroup.invoke(new FJTask()
      {
        private final FJTask[] val$subTasks;
        
        public void run()
        {
          coInvoke(this.val$subTasks);
        }
      });
    }
    catch (InterruptedException localInterruptedException) {}
  }
  
  public void dgemv(boolean paramBoolean, double paramDouble1, DoubleMatrix2D paramDoubleMatrix2D, DoubleMatrix1D paramDoubleMatrix1D1, double paramDouble2, DoubleMatrix1D paramDoubleMatrix1D2)
  {
    if (paramBoolean)
    {
      dgemv(false, paramDouble1, paramDoubleMatrix2D.viewDice(), paramDoubleMatrix1D1, paramDouble2, paramDoubleMatrix1D2);
      return;
    }
    int i = paramDoubleMatrix2D.rows();
    int j = paramDoubleMatrix2D.columns();
    long l = 2L * i * j;
    int k = (int)Math.min(l / 30000L, this.maxThreads);
    int m = paramDoubleMatrix2D.rows();
    k = Math.min(m, k);
    if (k < 2)
    {
      this.seqBlas.dgemv(paramBoolean, paramDouble1, paramDoubleMatrix2D, paramDoubleMatrix1D1, paramDouble2, paramDoubleMatrix1D2);
      return;
    }
    int n = m / k;
    FJTask[] arrayOfFJTask = new FJTask[k];
    for (int i1 = 0; i1 < k; i1++)
    {
      int i2 = i1 * n;
      if (i1 == k - 1) {
        n = m - n * i1;
      }
      DoubleMatrix2D localDoubleMatrix2D = paramDoubleMatrix2D.viewPart(i2, 0, n, j);
      DoubleMatrix1D localDoubleMatrix1D = paramDoubleMatrix1D2.viewPart(i2, n);
      arrayOfFJTask[i1 = new FJTask()
      {
        private final boolean val$transposeA;
        private final double val$alpha;
        private final DoubleMatrix2D val$AA;
        private final DoubleMatrix1D val$x;
        private final double val$beta;
        private final DoubleMatrix1D val$yy;
        
        public void run()
        {
          SmpBlas.this.seqBlas.dgemv(this.val$transposeA, this.val$alpha, this.val$AA, this.val$x, this.val$beta, this.val$yy);
        }
      };
    }
    try
    {
      this.smp.taskGroup.invoke(new FJTask()
      {
        private final FJTask[] val$subTasks;
        
        public void run()
        {
          coInvoke(this.val$subTasks);
        }
      });
    }
    catch (InterruptedException localInterruptedException) {}
  }
  
  public void dger(double paramDouble, DoubleMatrix1D paramDoubleMatrix1D1, DoubleMatrix1D paramDoubleMatrix1D2, DoubleMatrix2D paramDoubleMatrix2D)
  {
    this.seqBlas.dger(paramDouble, paramDoubleMatrix1D1, paramDoubleMatrix1D2, paramDoubleMatrix2D);
  }
  
  public double dnrm2(DoubleMatrix1D paramDoubleMatrix1D)
  {
    return this.seqBlas.dnrm2(paramDoubleMatrix1D);
  }
  
  public void drot(DoubleMatrix1D paramDoubleMatrix1D1, DoubleMatrix1D paramDoubleMatrix1D2, double paramDouble1, double paramDouble2)
  {
    this.seqBlas.drot(paramDoubleMatrix1D1, paramDoubleMatrix1D2, paramDouble1, paramDouble2);
  }
  
  public void drotg(double paramDouble1, double paramDouble2, double[] paramArrayOfDouble)
  {
    this.seqBlas.drotg(paramDouble1, paramDouble2, paramArrayOfDouble);
  }
  
  public void dscal(double paramDouble, DoubleMatrix1D paramDoubleMatrix1D)
  {
    this.seqBlas.dscal(paramDouble, paramDoubleMatrix1D);
  }
  
  public void dscal(double paramDouble, DoubleMatrix2D paramDoubleMatrix2D)
  {
    this.seqBlas.dscal(paramDouble, paramDoubleMatrix2D);
  }
  
  public void dswap(DoubleMatrix1D paramDoubleMatrix1D1, DoubleMatrix1D paramDoubleMatrix1D2)
  {
    this.seqBlas.dswap(paramDoubleMatrix1D1, paramDoubleMatrix1D2);
  }
  
  public void dswap(DoubleMatrix2D paramDoubleMatrix2D1, DoubleMatrix2D paramDoubleMatrix2D2)
  {
    this.seqBlas.dswap(paramDoubleMatrix2D1, paramDoubleMatrix2D2);
  }
  
  public void dsymv(boolean paramBoolean, double paramDouble1, DoubleMatrix2D paramDoubleMatrix2D, DoubleMatrix1D paramDoubleMatrix1D1, double paramDouble2, DoubleMatrix1D paramDoubleMatrix1D2)
  {
    this.seqBlas.dsymv(paramBoolean, paramDouble1, paramDoubleMatrix2D, paramDoubleMatrix1D1, paramDouble2, paramDoubleMatrix1D2);
  }
  
  public void dtrmv(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, DoubleMatrix2D paramDoubleMatrix2D, DoubleMatrix1D paramDoubleMatrix1D)
  {
    this.seqBlas.dtrmv(paramBoolean1, paramBoolean2, paramBoolean3, paramDoubleMatrix2D, paramDoubleMatrix1D);
  }
  
  public int idamax(DoubleMatrix1D paramDoubleMatrix1D)
  {
    return this.seqBlas.idamax(paramDoubleMatrix1D);
  }
  
  protected double[] run(DoubleMatrix2D paramDoubleMatrix2D1, DoubleMatrix2D paramDoubleMatrix2D2, boolean paramBoolean, Matrix2DMatrix2DFunction paramMatrix2DMatrix2DFunction)
  {
    DoubleMatrix2D[][] arrayOfDoubleMatrix2D = this.smp.splitBlockedNN(paramDoubleMatrix2D1, paramDoubleMatrix2D2, NN_THRESHOLD, paramDoubleMatrix2D1.rows() * paramDoubleMatrix2D1.columns());
    int i = arrayOfDoubleMatrix2D != null ? arrayOfDoubleMatrix2D[0].length : 1;
    double[] arrayOfDouble = paramBoolean ? new double[i] : null;
    if (arrayOfDoubleMatrix2D == null)
    {
      double d = paramMatrix2DMatrix2DFunction.apply(paramDoubleMatrix2D1, paramDoubleMatrix2D2);
      if (paramBoolean) {
        arrayOfDouble[0] = d;
      }
      return arrayOfDouble;
    }
    this.smp.run(arrayOfDoubleMatrix2D[0], arrayOfDoubleMatrix2D[1], arrayOfDouble, paramMatrix2DMatrix2DFunction);
    return arrayOfDouble;
  }
  
  protected double[] run(DoubleMatrix2D paramDoubleMatrix2D, boolean paramBoolean, Matrix2DMatrix2DFunction paramMatrix2DMatrix2DFunction)
  {
    DoubleMatrix2D[] arrayOfDoubleMatrix2D = this.smp.splitBlockedNN(paramDoubleMatrix2D, NN_THRESHOLD, paramDoubleMatrix2D.rows() * paramDoubleMatrix2D.columns());
    int i = arrayOfDoubleMatrix2D != null ? arrayOfDoubleMatrix2D.length : 1;
    double[] arrayOfDouble = paramBoolean ? new double[i] : null;
    if (arrayOfDoubleMatrix2D == null)
    {
      double d = paramMatrix2DMatrix2DFunction.apply(paramDoubleMatrix2D, null);
      if (paramBoolean) {
        arrayOfDouble[0] = d;
      }
      return arrayOfDouble;
    }
    this.smp.run(arrayOfDoubleMatrix2D, null, arrayOfDouble, paramMatrix2DMatrix2DFunction);
    return arrayOfDouble;
  }
  
  public void stats()
  {
    if (this.smp != null) {
      this.smp.stats();
    }
  }
  
  private double xsum(DoubleMatrix2D paramDoubleMatrix2D)
  {
    double[] arrayOfDouble = run(paramDoubleMatrix2D, true, new Matrix2DMatrix2DFunction()
    {
      public double apply(DoubleMatrix2D paramAnonymousDoubleMatrix2D1, DoubleMatrix2D paramAnonymousDoubleMatrix2D2)
      {
        return paramAnonymousDoubleMatrix2D1.zSum();
      }
    });
    double d = 0.0D;
    int i = arrayOfDouble.length;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      d += arrayOfDouble[i];
    }
    return d;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.linalg.SmpBlas
 * JD-Core Version:    0.7.0.1
 */