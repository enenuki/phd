package cern.colt.matrix.linalg;

import EDU.oswego.cs.dl.util.concurrent.FJTask;
import EDU.oswego.cs.dl.util.concurrent.FJTaskRunnerGroup;
import cern.colt.matrix.DoubleMatrix2D;

class Smp
{
  protected FJTaskRunnerGroup taskGroup;
  protected int maxThreads;
  
  protected Smp(int paramInt)
  {
    paramInt = Math.max(1, paramInt);
    this.maxThreads = paramInt;
    if (paramInt > 1) {
      this.taskGroup = new FJTaskRunnerGroup(paramInt);
    } else {
      this.taskGroup = null;
    }
  }
  
  public void finalize()
  {
    if (this.taskGroup != null) {
      this.taskGroup.interruptAll();
    }
  }
  
  protected void run(DoubleMatrix2D[] paramArrayOfDoubleMatrix2D1, DoubleMatrix2D[] paramArrayOfDoubleMatrix2D2, double[] paramArrayOfDouble, Matrix2DMatrix2DFunction paramMatrix2DMatrix2DFunction)
  {
    FJTask[] arrayOfFJTask = new FJTask[paramArrayOfDoubleMatrix2D1.length];
    for (int i = 0; i < paramArrayOfDoubleMatrix2D1.length; i++)
    {
      int j = i;
      arrayOfFJTask[i = new FJTask()
      {
        private final Matrix2DMatrix2DFunction val$function;
        private final DoubleMatrix2D[] val$blocksA;
        private final int val$k;
        private final DoubleMatrix2D[] val$blocksB;
        private final double[] val$results;
        
        public void run()
        {
          double d = this.val$function.apply(this.val$blocksA[this.val$k], this.val$blocksB != null ? this.val$blocksB[this.val$k] : null);
          if (this.val$results != null) {
            this.val$results[this.val$k] = d;
          }
        }
      };
    }
    try
    {
      this.taskGroup.invoke(new FJTask()
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
  
  protected DoubleMatrix2D[] splitBlockedNN(DoubleMatrix2D paramDoubleMatrix2D, int paramInt, long paramLong)
  {
    int i = (int)Math.min(paramLong / paramInt, this.maxThreads);
    int j = paramDoubleMatrix2D.columns() < i ? 1 : 0;
    int k = j != 0 ? paramDoubleMatrix2D.rows() : paramDoubleMatrix2D.columns();
    i = Math.min(k, i);
    if (i < 2) {
      return null;
    }
    int m = k / i;
    DoubleMatrix2D[] arrayOfDoubleMatrix2D = new DoubleMatrix2D[i];
    for (int n = 0; n < i; n++)
    {
      int i1 = n * m;
      if (n == i - 1) {
        m = k - m * n;
      }
      if (j == 0) {
        arrayOfDoubleMatrix2D[n] = paramDoubleMatrix2D.viewPart(0, i1, paramDoubleMatrix2D.rows(), m);
      } else {
        arrayOfDoubleMatrix2D[n] = paramDoubleMatrix2D.viewPart(i1, 0, m, paramDoubleMatrix2D.columns());
      }
    }
    return arrayOfDoubleMatrix2D;
  }
  
  protected DoubleMatrix2D[][] splitBlockedNN(DoubleMatrix2D paramDoubleMatrix2D1, DoubleMatrix2D paramDoubleMatrix2D2, int paramInt, long paramLong)
  {
    DoubleMatrix2D[] arrayOfDoubleMatrix2D1 = splitBlockedNN(paramDoubleMatrix2D1, paramInt, paramLong);
    if (arrayOfDoubleMatrix2D1 == null) {
      return (DoubleMatrix2D[][])null;
    }
    DoubleMatrix2D[] arrayOfDoubleMatrix2D2 = splitBlockedNN(paramDoubleMatrix2D2, paramInt, paramLong);
    if (arrayOfDoubleMatrix2D2 == null) {
      return (DoubleMatrix2D[][])null;
    }
    DoubleMatrix2D[][] arrayOfDoubleMatrix2D; = { arrayOfDoubleMatrix2D1, arrayOfDoubleMatrix2D2 };
    return arrayOfDoubleMatrix2D;;
  }
  
  protected DoubleMatrix2D[] splitStridedNN(DoubleMatrix2D paramDoubleMatrix2D, int paramInt, long paramLong)
  {
    int i = (int)Math.min(paramLong / paramInt, this.maxThreads);
    int j = paramDoubleMatrix2D.columns() < i ? 1 : 0;
    int k = j != 0 ? paramDoubleMatrix2D.rows() : paramDoubleMatrix2D.columns();
    i = Math.min(k, i);
    if (i < 2) {
      return null;
    }
    int m = k / i;
    DoubleMatrix2D[] arrayOfDoubleMatrix2D = new DoubleMatrix2D[i];
    for (int n = 0; n < i; n++)
    {
      int i1 = n * m;
      if (n == i - 1) {
        m = k - m * n;
      }
      if (j == 0) {
        arrayOfDoubleMatrix2D[n] = paramDoubleMatrix2D.viewPart(0, n, paramDoubleMatrix2D.rows(), paramDoubleMatrix2D.columns() - n).viewStrides(1, i);
      } else {
        arrayOfDoubleMatrix2D[n] = paramDoubleMatrix2D.viewPart(n, 0, paramDoubleMatrix2D.rows() - n, paramDoubleMatrix2D.columns()).viewStrides(i, 1);
      }
    }
    return arrayOfDoubleMatrix2D;
  }
  
  public void stats()
  {
    if (this.taskGroup != null) {
      this.taskGroup.stats();
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.linalg.Smp
 * JD-Core Version:    0.7.0.1
 */