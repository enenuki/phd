package cern.colt.matrix.bench;

import cern.colt.matrix.ObjectMatrix1D;
import cern.colt.matrix.ObjectMatrix2D;
import cern.colt.matrix.impl.DenseObjectMatrix2D;
import cern.colt.matrix.objectalgo.Formatter;

class BenchmarkKernel
{
  public static float run(double paramDouble, TimerProcedure paramTimerProcedure)
  {
    long l1 = 0L;
    long l2 = (paramDouble * 1000.0D);
    long l3 = System.currentTimeMillis();
    long l4 = l3 + l2;
    while (System.currentTimeMillis() < l4)
    {
      paramTimerProcedure.init();
      paramTimerProcedure.apply(null);
      l1 += 1L;
    }
    long l5 = System.currentTimeMillis();
    if (paramDouble / l1 < 0.1D)
    {
      l3 = System.currentTimeMillis();
      l6 = l1;
      while (--l6 >= 0L)
      {
        paramTimerProcedure.init();
        paramTimerProcedure.apply(null);
      }
      l5 = System.currentTimeMillis();
    }
    long l6 = System.currentTimeMillis();
    int i = 1;
    long l7 = l1;
    while (--l7 >= 0L)
    {
      i = (int)(i * l7);
      paramTimerProcedure.init();
    }
    l7 = System.currentTimeMillis();
    long l8 = l5 - l3 - (l7 - l6);
    return (float)l8 / 1000.0F / (float)l1;
  }
  
  public static String systemInfo()
  {
    String[] arrayOfString = { "java.vm.vendor", "java.vm.version", "java.vm.name", "os.name", "os.version", "os.arch", "java.version", "java.vendor", "java.vendor.url" };
    DenseObjectMatrix2D localDenseObjectMatrix2D = new DenseObjectMatrix2D(arrayOfString.length, 2);
    localDenseObjectMatrix2D.viewColumn(0).assign(arrayOfString);
    for (int i = 0; i < arrayOfString.length; i++)
    {
      String str = System.getProperty(arrayOfString[i]);
      if (str == null) {
        str = "?";
      }
      localDenseObjectMatrix2D.set(i, 1, str);
    }
    Formatter localFormatter = new Formatter();
    localFormatter.setPrintShape(false);
    return localFormatter.toString(localDenseObjectMatrix2D);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.bench.BenchmarkKernel
 * JD-Core Version:    0.7.0.1
 */