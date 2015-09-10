package cern.colt.map;

import cern.colt.Timer;
import cern.jet.random.Uniform;
import cern.jet.random.engine.MersenneTwister;
import java.io.PrintStream;

public class Benchmark
{
  public static void benchmark(int paramInt1, int paramInt2, String paramString)
  {
    System.out.println("initializing...");
    QuickOpenIntIntHashMap localQuickOpenIntIntHashMap = new QuickOpenIntIntHashMap();
    for (int i = 0; i < paramInt2; i++) {
      localQuickOpenIntIntHashMap.put(i, i);
    }
    Runtime.getRuntime().gc();
    try
    {
      Thread.currentThread();
      Thread.sleep(1000L);
    }
    catch (InterruptedException localInterruptedException) {}
    System.out.println("Now benchmarking...");
    int j = 0;
    Timer localTimer1 = new Timer();
    Timer localTimer2 = new Timer();
    Timer localTimer3 = new Timer();
    int k = paramInt1;
    for (;;)
    {
      k--;
      if (k < 0) {
        break;
      }
      int m;
      if (paramString.equals("add"))
      {
        localQuickOpenIntIntHashMap.clear();
        localTimer1.start();
        m = paramInt2;
        for (;;)
        {
          m--;
          if (m < 0) {
            break;
          }
          localQuickOpenIntIntHashMap.put(m, m);
        }
        localTimer1.stop();
      }
      if (paramString.equals("get"))
      {
        localTimer1.start();
        m = paramInt2;
        for (;;)
        {
          m--;
          if (m < 0) {
            break;
          }
          j += localQuickOpenIntIntHashMap.get(m);
        }
        localTimer1.stop();
      }
      else
      {
        localTimer2.start();
        localQuickOpenIntIntHashMap.rehash(PrimeFinder.nextPrime(paramInt2 * 2));
        localTimer2.stop();
        localTimer3.start();
        localQuickOpenIntIntHashMap.rehash(PrimeFinder.nextPrime((int)(paramInt2 * 1.5D)));
        localTimer3.stop();
      }
    }
    System.out.println("adding: " + localTimer1);
    System.out.println("growing: " + localTimer2);
    System.out.println("shrinking: " + localTimer3);
    System.out.println("total: " + localTimer2.plus(localTimer3));
    System.out.print(j);
  }
  
  public static void main(String[] paramArrayOfString)
  {
    int i = Integer.parseInt(paramArrayOfString[0]);
    int j = Integer.parseInt(paramArrayOfString[1]);
    String str = paramArrayOfString[2];
    benchmark(i, j, str);
  }
  
  public static void test2(int paramInt)
  {
    Uniform localUniform = new Uniform(new MersenneTwister());
    int[] arrayOfInt1 = new int[paramInt];
    int i = 10000000;
    for (int j = 0; j < paramInt; j++) {
      arrayOfInt1[j] = localUniform.nextIntFromTo(0, i);
    }
    int[] arrayOfInt2 = (int[])arrayOfInt1.clone();
    int k = arrayOfInt1.length;
    OpenIntIntHashMap localOpenIntIntHashMap = new OpenIntIntHashMap();
    for (int m = 0; m < arrayOfInt1.length; m++) {
      localOpenIntIntHashMap.put(arrayOfInt1[m], arrayOfInt2[m]);
    }
    m = 0;
    for (int n = 0; n < arrayOfInt1.length; n++) {
      m += localOpenIntIntHashMap.get(arrayOfInt1[n]);
    }
    System.out.println(localOpenIntIntHashMap);
    System.out.println(m);
    System.out.println("\n\n");
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.map.Benchmark
 * JD-Core Version:    0.7.0.1
 */