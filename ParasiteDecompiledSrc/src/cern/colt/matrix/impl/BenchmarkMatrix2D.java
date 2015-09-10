package cern.colt.matrix.impl;

import cern.colt.Timer;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.doublealgo.Transform;
import java.io.PrintStream;

class BenchmarkMatrix2D
{
  protected BenchmarkMatrix2D()
  {
    throw new RuntimeException("Non instantiable");
  }
  
  public static void doubleBenchmark(int paramInt1, int paramInt2, int paramInt3, String paramString, boolean paramBoolean, int paramInt4, double paramDouble1, double paramDouble2)
  {
    System.out.println("benchmarking double matrix");
    Timer localTimer1 = new Timer();
    Timer localTimer2 = new Timer();
    Timer localTimer3 = new Timer();
    Timer localTimer4 = new Timer();
    Timer localTimer5 = new Timer();
    Timer localTimer6 = new Timer();
    localTimer5.start();
    int i = 0;
    for (int j = 0; j < paramInt1; j++) {
      for (int k = 0; k < paramInt3; k++) {
        for (m = 0; m < paramInt2; m++) {
          i++;
        }
      }
    }
    localTimer5.stop();
    System.out.println(i);
    localTimer6.start();
    i = 3;
    double d1 = 0.0D;
    for (int m = 0; m < paramInt1; m++)
    {
      int n = 0;
      for (int i1 = 0; i1 < paramInt3; i1++) {
        for (int i2 = 0; i2 < paramInt2; i2++) {
          d1 += i;
        }
      }
    }
    localTimer6.stop();
    System.out.println(d1);
    long l1 = Runtime.getRuntime().freeMemory();
    long l2 = paramInt2 * paramInt3 * paramInt1;
    Object localObject = null;
    if (paramString.equals("sparse")) {
      localObject = new SparseDoubleMatrix2D(paramInt2, paramInt3, paramInt4, paramDouble1, paramDouble2);
    } else if (paramString.equals("dense")) {
      localObject = new DenseDoubleMatrix2D(paramInt2, paramInt3);
    } else {
      throw new RuntimeException("unknown kind");
    }
    System.out.println("\nNow filling...");
    for (int i3 = 0; i3 < paramInt1; i3++)
    {
      ((DoubleMatrix2D)localObject).assign(0.0D);
      ((DoubleMatrix2D)localObject).ensureCapacity(paramInt4);
      if (paramString.equals("sparse")) {
        ((SparseDoubleMatrix2D)localObject).ensureCapacity(paramInt4);
      }
      localTimer1.start();
      int i4 = 0;
      for (int i5 = 0; i5 < paramInt2; i5++) {
        for (int i6 = 0; i6 < paramInt3; i6++) {
          ((DoubleMatrix2D)localObject).setQuick(i5, i6, i4++);
        }
      }
      localTimer1.stop();
    }
    localTimer1.display();
    localTimer1.minus(localTimer5).display();
    System.out.println((float)l2 / localTimer1.minus(localTimer5).seconds() + " elements / sec");
    Runtime.getRuntime().gc();
    try
    {
      Thread.currentThread();
      Thread.sleep(1000L);
    }
    catch (InterruptedException localInterruptedException1) {}
    long l3 = Runtime.getRuntime().freeMemory();
    System.out.println("KB needed=" + (l1 - l3) / 1024L);
    System.out.println("bytes needed per non-zero=" + (l1 - l3) / ((DoubleMatrix2D)localObject).cardinality());
    if (paramBoolean)
    {
      System.out.println(localObject);
      if (paramString.equals("sparse")) {
        System.out.println("map=" + ((SparseDoubleMatrix2D)localObject).elements);
      }
    }
    System.out.println("\nNow reading...");
    localTimer2.start();
    double d2 = 0.0D;
    int i9;
    for (int i7 = 0; i7 < paramInt1; i7++) {
      for (i8 = 0; i8 < paramInt2; i8++) {
        for (i9 = 0; i9 < paramInt3; i9++) {
          d2 += ((DoubleMatrix2D)localObject).getQuick(i8, i9);
        }
      }
    }
    localTimer2.stop().display();
    localTimer2.minus(localTimer6).display();
    System.out.println((float)l2 / localTimer2.minus(localTimer6).seconds() + " elements / sec");
    if (paramBoolean) {
      System.out.println(localObject);
    }
    System.out.println(d2);
    System.out.println("\nNow reading view...");
    DoubleMatrix2D localDoubleMatrix2D = ((DoubleMatrix2D)localObject).viewPart(0, 0, paramInt2, paramInt3);
    localTimer4.start();
    d2 = 0.0D;
    int i10;
    for (int i8 = 0; i8 < paramInt1; i8++) {
      for (i9 = 0; i9 < paramInt2; i9++) {
        for (i10 = 0; i10 < paramInt3; i10++) {
          d2 += localDoubleMatrix2D.getQuick(i9, i10);
        }
      }
    }
    localTimer4.stop().display();
    localTimer4.minus(localTimer6).display();
    System.out.println((float)l2 / localTimer4.minus(localTimer6).seconds() + " elements / sec");
    if (paramBoolean) {
      System.out.println(localDoubleMatrix2D);
    }
    System.out.println(d2);
    System.out.println("\nNow removing...");
    l1 = Runtime.getRuntime().freeMemory();
    for (i8 = 0; i8 < paramInt1; i8++)
    {
      for (i9 = 0; i9 < paramInt2; i9++) {
        for (i10 = 0; i10 < paramInt3; i10++) {
          ((DoubleMatrix2D)localObject).setQuick(i9, i10, 1.0D);
        }
      }
      localTimer3.start();
      for (i9 = 0; i9 < paramInt2; i9++) {
        for (i10 = 0; i10 < paramInt3; i10++) {
          ((DoubleMatrix2D)localObject).setQuick(i9, i10, 0.0D);
        }
      }
      localTimer3.stop();
    }
    localTimer3.display();
    localTimer3.minus(localTimer5).display();
    System.out.println((float)l2 / localTimer3.minus(localTimer5).seconds() + " elements / sec");
    Runtime.getRuntime().gc();
    try
    {
      Thread.currentThread();
      Thread.sleep(1000L);
    }
    catch (InterruptedException localInterruptedException2) {}
    l3 = Runtime.getRuntime().freeMemory();
    System.out.println("KB needed=" + (l1 - l3) / 1024L);
    System.out.println("KB free=" + l3 / 1024L);
    if (paramBoolean) {
      System.out.println(localObject);
    }
    System.out.println("bye bye.");
  }
  
  public static void doubleBenchmarkMult(int paramInt1, int paramInt2, int paramInt3, String paramString, boolean paramBoolean, int paramInt4, double paramDouble1, double paramDouble2)
  {
    System.out.println("benchmarking double matrix");
    Timer localTimer1 = new Timer();
    Timer localTimer2 = new Timer();
    long l = paramInt2 * paramInt3 * paramInt1;
    Object localObject = null;
    if (paramString.equals("sparse")) {
      localObject = new SparseDoubleMatrix2D(paramInt2, paramInt3, paramInt4, paramDouble1, paramDouble2);
    } else if (paramString.equals("dense")) {
      localObject = new DenseDoubleMatrix2D(paramInt2, paramInt3);
    } else {
      throw new RuntimeException("unknown kind");
    }
    System.out.println("\nNow multiplying...");
    ((DoubleMatrix2D)localObject).assign(1.0D);
    for (int i = 0; i < paramInt1; i++)
    {
      localTimer1.start();
      Transform.mult((DoubleMatrix2D)localObject, 3.0D);
      localTimer1.stop();
    }
    localTimer1.display();
    System.out.println((float)l / localTimer1.seconds() + " elements / sec");
    if (paramBoolean) {
      System.out.println(localObject);
    }
    System.out.println("\nNow multiplying2...");
    ((DoubleMatrix2D)localObject).assign(1.0D);
    for (i = 0; i < paramInt1; i++)
    {
      localTimer2.start();
      Transform.mult((DoubleMatrix2D)localObject, 3.0D);
      localTimer2.stop();
    }
    localTimer2.display();
    System.out.println((float)l / localTimer2.seconds() + " elements / sec");
    if (paramBoolean) {
      System.out.println(localObject);
    }
    System.out.println("bye bye.");
  }
  
  public static void doubleBenchmarkPrimitive(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    Timer localTimer1 = new Timer();
    Timer localTimer2 = new Timer();
    Timer localTimer3 = new Timer();
    Timer localTimer4 = new Timer();
    Timer localTimer5 = new Timer();
    localTimer4.start();
    int i = 0;
    for (int j = 0; j < paramInt1; j++) {
      for (int k = 0; k < paramInt3; k++) {
        for (m = 0; m < paramInt2; m++) {
          i++;
        }
      }
    }
    localTimer4.stop();
    System.out.println(i);
    localTimer5.start();
    i = 3;
    double d1 = 0.0D;
    for (int m = 0; m < paramInt1; m++) {
      for (int n = 0; n < paramInt3; n++) {
        for (int i1 = 0; i1 < paramInt2; i1++) {
          d1 += i;
        }
      }
    }
    localTimer5.stop();
    System.out.println(d1);
    long l1 = Runtime.getRuntime().freeMemory();
    long l2 = paramInt2 * paramInt3 * paramInt1;
    double[][] arrayOfDouble = new double[paramInt2][paramInt3];
    System.out.println("\nNow filling...");
    for (int i2 = 0; i2 < paramInt1; i2++)
    {
      localTimer1.start();
      int i3 = 0;
      for (int i4 = 0; i4 < paramInt3; i4++) {
        for (int i5 = 0; i5 < paramInt2; i5++) {
          arrayOfDouble[i5][i4] = (i3++);
        }
      }
      localTimer1.stop();
    }
    localTimer1.display();
    localTimer1.minus(localTimer4).display();
    System.out.println((float)l2 / localTimer1.minus(localTimer4).seconds() + " elements / sec");
    Runtime.getRuntime().gc();
    try
    {
      Thread.currentThread();
      Thread.sleep(1000L);
    }
    catch (InterruptedException localInterruptedException1) {}
    long l3 = Runtime.getRuntime().freeMemory();
    System.out.println("KB needed=" + (l1 - l3) / 1024L);
    if (paramBoolean)
    {
      DenseDoubleMatrix2D localDenseDoubleMatrix2D1 = new DenseDoubleMatrix2D(paramInt2, paramInt3);
      localDenseDoubleMatrix2D1.assign(arrayOfDouble);
      System.out.println(localDenseDoubleMatrix2D1);
    }
    System.out.println("\nNow reading...");
    localTimer2.start();
    double d2 = 0.0D;
    int i8;
    int i9;
    for (int i6 = 0; i6 < paramInt1; i6++) {
      for (i8 = 0; i8 < paramInt3; i8++) {
        for (i9 = 0; i9 < paramInt2; i9++) {
          d2 += arrayOfDouble[i9][i8];
        }
      }
    }
    localTimer2.stop().display();
    localTimer2.minus(localTimer5).display();
    System.out.println((float)l2 / localTimer2.minus(localTimer5).seconds() + " elements / sec");
    if (paramBoolean)
    {
      DenseDoubleMatrix2D localDenseDoubleMatrix2D2 = new DenseDoubleMatrix2D(paramInt2, paramInt3);
      localDenseDoubleMatrix2D2.assign(arrayOfDouble);
      System.out.println(localDenseDoubleMatrix2D2);
    }
    System.out.println(d2);
    System.out.println("\nNow removing...");
    l1 = Runtime.getRuntime().freeMemory();
    for (int i7 = 0; i7 < paramInt1; i7++)
    {
      localTimer3.start();
      for (i8 = 0; i8 < paramInt3; i8++) {
        for (i9 = 0; i9 < paramInt2; i9++) {
          arrayOfDouble[i9][i8] = 0.0D;
        }
      }
      localTimer3.stop();
    }
    localTimer3.display();
    localTimer3.minus(localTimer4).display();
    System.out.println((float)l2 / localTimer3.minus(localTimer4).seconds() + " elements / sec");
    Runtime.getRuntime().gc();
    try
    {
      Thread.currentThread();
      Thread.sleep(1000L);
    }
    catch (InterruptedException localInterruptedException2) {}
    l3 = Runtime.getRuntime().freeMemory();
    System.out.println("KB needed=" + (l1 - l3) / 1024L);
    System.out.println("KB free=" + l3 / 1024L);
    if (paramBoolean)
    {
      DenseDoubleMatrix2D localDenseDoubleMatrix2D3 = new DenseDoubleMatrix2D(paramInt2, paramInt3);
      localDenseDoubleMatrix2D3.assign(arrayOfDouble);
      System.out.println(localDenseDoubleMatrix2D3);
    }
    System.out.println("bye bye.");
  }
  
  public static void doubleBenchmarkPrimitiveOptimized(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    Timer localTimer1 = new Timer();
    Timer localTimer2 = new Timer();
    Timer localTimer3 = new Timer();
    Timer localTimer4 = new Timer();
    Timer localTimer5 = new Timer();
    localTimer4.start();
    int i = 0;
    for (int j = 0; j < paramInt1; j++) {
      for (int k = 0; k < paramInt3; k++) {
        for (m = 0; m < paramInt2; m++) {
          i++;
        }
      }
    }
    localTimer4.stop();
    System.out.println(i);
    localTimer5.start();
    i = 3;
    double d1 = 0.0D;
    for (int m = 0; m < paramInt1; m++) {
      for (int n = 0; n < paramInt3; n++) {
        for (int i1 = 0; i1 < paramInt2; i1++) {
          d1 += i;
        }
      }
    }
    localTimer5.stop();
    System.out.println(d1);
    long l1 = Runtime.getRuntime().freeMemory();
    long l2 = paramInt2 * paramInt3 * paramInt1;
    double[][] arrayOfDouble = new double[paramInt2][paramInt3];
    System.out.println("\nNow filling...");
    for (int i2 = 0; i2 < paramInt1; i2++)
    {
      localTimer1.start();
      int i3 = 0;
      for (int i4 = 0; i4 < paramInt2; i4++)
      {
        double[] arrayOfDouble1 = arrayOfDouble[i4];
        for (i5 = 0; i5 < paramInt3; i5++) {
          arrayOfDouble1[i5] = (i3++);
        }
      }
      localTimer1.stop();
    }
    localTimer1.display();
    localTimer1.minus(localTimer4).display();
    System.out.println((float)l2 / localTimer1.minus(localTimer4).seconds() + " elements / sec");
    Runtime.getRuntime().gc();
    try
    {
      Thread.currentThread();
      Thread.sleep(1000L);
    }
    catch (InterruptedException localInterruptedException1) {}
    long l3 = Runtime.getRuntime().freeMemory();
    System.out.println("KB needed=" + (l1 - l3) / 1024L);
    if (paramBoolean)
    {
      DenseDoubleMatrix2D localDenseDoubleMatrix2D1 = new DenseDoubleMatrix2D(paramInt2, paramInt3);
      localDenseDoubleMatrix2D1.assign(arrayOfDouble);
      System.out.println(localDenseDoubleMatrix2D1);
    }
    System.out.println("\nNow reading...");
    localTimer2.start();
    double d2 = 0.0D;
    int i7;
    double[] arrayOfDouble2;
    int i8;
    for (int i5 = 0; i5 < paramInt1; i5++) {
      for (i7 = 0; i7 < paramInt2; i7++)
      {
        arrayOfDouble2 = arrayOfDouble[i7];
        for (i8 = 0; i8 < paramInt3; i8++) {
          d2 += arrayOfDouble2[i8];
        }
      }
    }
    localTimer2.stop().display();
    localTimer2.minus(localTimer5).display();
    System.out.println((float)l2 / localTimer2.minus(localTimer5).seconds() + " elements / sec");
    if (paramBoolean)
    {
      DenseDoubleMatrix2D localDenseDoubleMatrix2D2 = new DenseDoubleMatrix2D(paramInt2, paramInt3);
      localDenseDoubleMatrix2D2.assign(arrayOfDouble);
      System.out.println(localDenseDoubleMatrix2D2);
    }
    System.out.println(d2);
    System.out.println("\nNow removing...");
    l1 = Runtime.getRuntime().freeMemory();
    for (int i6 = 0; i6 < paramInt1; i6++)
    {
      localTimer3.start();
      for (i7 = 0; i7 < paramInt2; i7++)
      {
        arrayOfDouble2 = arrayOfDouble[i7];
        for (i8 = 0; i8 < paramInt3; i8++) {
          arrayOfDouble2[i8] = 0.0D;
        }
      }
      localTimer3.stop();
    }
    localTimer3.display();
    localTimer3.minus(localTimer4).display();
    System.out.println((float)l2 / localTimer3.minus(localTimer4).seconds() + " elements / sec");
    Runtime.getRuntime().gc();
    try
    {
      Thread.currentThread();
      Thread.sleep(1000L);
    }
    catch (InterruptedException localInterruptedException2) {}
    l3 = Runtime.getRuntime().freeMemory();
    System.out.println("KB needed=" + (l1 - l3) / 1024L);
    System.out.println("KB free=" + l3 / 1024L);
    if (paramBoolean)
    {
      DenseDoubleMatrix2D localDenseDoubleMatrix2D3 = new DenseDoubleMatrix2D(paramInt2, paramInt3);
      localDenseDoubleMatrix2D3.assign(arrayOfDouble);
      System.out.println(localDenseDoubleMatrix2D3);
    }
    System.out.println("bye bye.");
  }
  
  public static void intBenchmark(int paramInt1, int paramInt2, int paramInt3, String paramString, boolean paramBoolean, int paramInt4, double paramDouble1, double paramDouble2)
  {
    throw new InternalError();
  }
  
  public static void intBenchmarkPrimitive(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    throw new InternalError();
  }
  
  public static void intBenchmarkPrimitiveOptimized(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    throw new InternalError();
  }
  
  public static void main(String[] paramArrayOfString)
  {
    int i = Integer.parseInt(paramArrayOfString[0]);
    int j = Integer.parseInt(paramArrayOfString[1]);
    int k = Integer.parseInt(paramArrayOfString[2]);
    String str1 = paramArrayOfString[3];
    int m = Integer.parseInt(paramArrayOfString[4]);
    double d1 = new Double(paramArrayOfString[5]).doubleValue();
    double d2 = new Double(paramArrayOfString[6]).doubleValue();
    boolean bool = paramArrayOfString[7].equals("print");
    String str2 = paramArrayOfString[8];
    String str3 = paramArrayOfString[9];
    if (str2.equals("int"))
    {
      if (str1.equals("primitive")) {
        intBenchmarkPrimitive(i, j, k, bool);
      } else if (str1.equals("primitiveOpt")) {
        intBenchmarkPrimitiveOptimized(i, j, k, bool);
      } else {
        intBenchmark(i, j, k, str1, bool, m, d1, d2);
      }
    }
    else if (str2.equals("double")) {
      if (str1.equals("primitive")) {
        doubleBenchmarkPrimitive(i, j, k, bool);
      } else if (str1.equals("primitiveOpt")) {
        doubleBenchmarkPrimitiveOptimized(i, j, k, bool);
      } else if (str3.equals("mult")) {
        doubleBenchmarkMult(i, j, k, str1, bool, m, d1, d2);
      } else {
        doubleBenchmark(i, j, k, str1, bool, m, d1, d2);
      }
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.impl.BenchmarkMatrix2D
 * JD-Core Version:    0.7.0.1
 */