package cern.jet.random;

import cern.colt.PersistentObject;
import cern.colt.Timer;
import cern.colt.list.DoubleArrayList;
import cern.colt.list.IntArrayList;
import cern.jet.random.engine.MersenneTwister;
import cern.jet.random.engine.RandomEngine;
import hep.aida.bin.DynamicBin1D;
import java.io.PrintStream;

public class Benchmark
  extends PersistentObject
{
  protected RandomEngine randomGenerator;
  
  protected Benchmark()
  {
    throw new RuntimeException("Non instantiable");
  }
  
  public static void demo1()
  {
    double d1 = 5.0D;
    double d2 = 1.5D;
    double d3 = d1 * d1 / d2;
    double d4 = 1.0D / (d2 / d1);
    MersenneTwister localMersenneTwister = new MersenneTwister();
    Gamma localGamma = new Gamma(d3, d4, localMersenneTwister);
    int i = 100000;
    DoubleArrayList localDoubleArrayList = new DoubleArrayList(i);
    for (int j = 0; j < i; j++) {
      localDoubleArrayList.add(localGamma.nextDouble());
    }
    DynamicBin1D localDynamicBin1D = new DynamicBin1D();
    localDynamicBin1D.addAllOf(localDoubleArrayList);
    System.out.println(localDynamicBin1D);
  }
  
  public static void main(String[] paramArrayOfString)
  {
    int i = Integer.parseInt(paramArrayOfString[0]);
    boolean bool = new Boolean(paramArrayOfString[1]).booleanValue();
    double d = new Double(paramArrayOfString[2]).doubleValue();
    String str = paramArrayOfString[3];
    random(i, bool, d, str);
  }
  
  public static void random(int paramInt, boolean paramBoolean, double paramDouble, String paramString)
  {
    System.out.println("Generating " + paramInt + " random numbers per distribution...\n");
    int i = 100;
    RandomEngine localRandomEngine;
    try
    {
      localRandomEngine = (RandomEngine)Class.forName(paramString).newInstance();
    }
    catch (Exception localException)
    {
      throw new InternalError(localException.getMessage());
    }
    randomInstance(paramInt, paramBoolean, new Poisson(paramDouble, (RandomEngine)localRandomEngine.clone()));
  }
  
  public static void randomInstance(int paramInt, boolean paramBoolean, AbstractDistribution paramAbstractDistribution)
  {
    System.out.print("\n" + paramAbstractDistribution + " ...");
    Timer localTimer = new Timer().start();
    int i = paramInt;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      double d = paramAbstractDistribution.nextDouble();
      if (paramBoolean)
      {
        if ((paramInt - i - 1) % 8 == 0) {
          System.out.println();
        }
        System.out.print((float)d + ", ");
      }
    }
    localTimer.stop();
    System.out.println("\n" + localTimer);
  }
  
  public static void test(int paramInt, AbstractDistribution paramAbstractDistribution)
  {
    int i = 0;
    int j = paramInt;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      System.out.print(" " + paramAbstractDistribution.nextDouble());
      if (i % 8 == 7) {
        System.out.println();
      }
      i++;
    }
    System.out.println("\n\nGood bye.\n");
  }
  
  public static void test2(int paramInt, AbstractDistribution paramAbstractDistribution)
  {
    DynamicBin1D localDynamicBin1D = new DynamicBin1D();
    int i = 0;
    int j = paramInt;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      localDynamicBin1D.add(paramAbstractDistribution.nextDouble());
      i++;
    }
    System.out.println(localDynamicBin1D);
    System.out.println("\n\nGood bye.\n");
  }
  
  public static void test2(int paramInt, AbstractDistribution paramAbstractDistribution1, AbstractDistribution paramAbstractDistribution2)
  {
    DynamicBin1D localDynamicBin1D1 = new DynamicBin1D();
    DynamicBin1D localDynamicBin1D2 = new DynamicBin1D();
    int i = 0;
    int j = paramInt;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      localDynamicBin1D1.add(paramAbstractDistribution1.nextDouble());
      localDynamicBin1D2.add(paramAbstractDistribution2.nextDouble());
      i++;
    }
    System.out.println("\n\nBenchmarking frequencies...\n");
    IntArrayList localIntArrayList = new IntArrayList();
    DoubleArrayList localDoubleArrayList = new DoubleArrayList();
    Timer localTimer = new Timer();
    localTimer.reset();
    localTimer.start();
    localDynamicBin1D1.frequencies(localDoubleArrayList, localIntArrayList);
    localTimer.stop().display();
    System.out.println("\n\nGood bye.\n");
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.jet.random.Benchmark
 * JD-Core Version:    0.7.0.1
 */