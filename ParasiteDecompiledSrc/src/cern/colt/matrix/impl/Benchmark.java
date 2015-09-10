package cern.colt.matrix.impl;

import cern.colt.Timer;
import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix2D;
import java.io.PrintStream;

class Benchmark
{
  protected Benchmark()
  {
    throw new RuntimeException("Non instantiable");
  }
  
  public static void benchmark(int paramInt1, int paramInt2, String paramString, boolean paramBoolean, int paramInt3, double paramDouble1, double paramDouble2, double paramDouble3)
  {
    Timer localTimer1 = new Timer();
    Timer localTimer2 = new Timer();
    Timer localTimer3 = new Timer();
    Timer localTimer4 = new Timer();
    Timer localTimer5 = new Timer();
    Timer localTimer6 = new Timer();
    Object localObject = null;
    if (paramString.equals("sparse")) {
      localObject = new SparseDoubleMatrix2D(paramInt2, paramInt2, paramInt3, paramDouble1, paramDouble2);
    } else if (paramString.equals("dense")) {
      localObject = DoubleFactory2D.dense.make(paramInt2, paramInt2);
    } else {
      throw new RuntimeException("unknown kind");
    }
    System.out.println("\nNow initializing...");
    double d1 = 2.0D;
    DoubleMatrix2D localDoubleMatrix2D1 = DoubleFactory2D.dense.sample(((DoubleMatrix2D)localObject).rows(), ((DoubleMatrix2D)localObject).columns(), d1, paramDouble3);
    ((DoubleMatrix2D)localObject).assign(localDoubleMatrix2D1);
    localDoubleMatrix2D1 = null;
    System.out.println("\ntesting...");
    if (paramBoolean) {
      System.out.println(localObject);
    }
    DoubleMatrix2D localDoubleMatrix2D2 = DoubleFactory2D.dense.make(paramInt2, paramInt2);
    localDoubleMatrix2D2.assign((DoubleMatrix2D)localObject);
    if (!localDoubleMatrix2D2.equals(localObject)) {
      throw new InternalError();
    }
    DoubleMatrix2D localDoubleMatrix2D3 = localDoubleMatrix2D2.copy();
    DoubleMatrix2D localDoubleMatrix2D4 = localDoubleMatrix2D2.copy();
    DoubleMatrix2D localDoubleMatrix2D5 = localDoubleMatrix2D2.copy();
    localDoubleMatrix2D3.zMult(localDoubleMatrix2D4, localDoubleMatrix2D5);
    System.out.println("\nNext testing...");
    DoubleMatrix2D localDoubleMatrix2D6 = ((DoubleMatrix2D)localObject).copy();
    DoubleMatrix2D localDoubleMatrix2D7 = ((DoubleMatrix2D)localObject).copy();
    DoubleMatrix2D localDoubleMatrix2D8 = ((DoubleMatrix2D)localObject).copy();
    localDoubleMatrix2D6.zMult(localDoubleMatrix2D7, localDoubleMatrix2D8);
    if (!localDoubleMatrix2D8.equals(localDoubleMatrix2D5)) {
      throw new InternalError();
    }
    localDoubleMatrix2D8.assign((DoubleMatrix2D)localObject);
    System.out.println("\nNow benchmarking...");
    localTimer3.start();
    for (int i = 0; i < paramInt1; i++) {
      localDoubleMatrix2D6.zMult(localDoubleMatrix2D7, localDoubleMatrix2D8);
    }
    localTimer3.stop();
    localTimer3.display();
    i = localDoubleMatrix2D6.rows();
    int j = localDoubleMatrix2D6.columns();
    int k = localDoubleMatrix2D7.rows();
    int m = paramInt1;
    double d2 = 0.001D * (2.0D * i * j * k * m) / localTimer3.millis();
    System.out.println("mflops: " + d2);
    if (paramBoolean) {
      System.out.println(localObject);
    }
    System.out.println("bye bye.");
  }
  
  protected static double cubicLoop(int paramInt1, int paramInt2)
  {
    double d1 = 1.123D;
    double d2 = 1.000000000012345D;
    for (int i = 0; i < paramInt1; i++)
    {
      int j = paramInt2;
      for (;;)
      {
        j--;
        if (j < 0) {
          break;
        }
        int k = paramInt2;
        for (;;)
        {
          k--;
          if (k < 0) {
            break;
          }
          int m = paramInt2;
          for (;;)
          {
            m--;
            if (m < 0) {
              break;
            }
            d1 *= d2;
          }
        }
      }
    }
    return d1;
  }
  
  public static void main(String[] paramArrayOfString)
  {
    int i = Integer.parseInt(paramArrayOfString[0]);
    int j = Integer.parseInt(paramArrayOfString[1]);
    int k = Integer.parseInt(paramArrayOfString[2]);
    String str = paramArrayOfString[3];
    int m = Integer.parseInt(paramArrayOfString[4]);
    double d1 = new Double(paramArrayOfString[5]).doubleValue();
    double d2 = new Double(paramArrayOfString[6]).doubleValue();
    boolean bool = paramArrayOfString[7].equals("print");
    double d3 = new Double(paramArrayOfString[8]).doubleValue();
    int n = j;
    benchmark(i, n, str, bool, m, d1, d2, d3);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.impl.Benchmark
 * JD-Core Version:    0.7.0.1
 */