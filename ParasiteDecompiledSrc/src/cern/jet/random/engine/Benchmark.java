package cern.jet.random.engine;

import cern.colt.Timer;
import java.io.PrintStream;
import java.util.Random;

public class Benchmark
{
  protected Benchmark()
  {
    throw new RuntimeException("Non instantiable");
  }
  
  public static void benchmark(int paramInt)
  {
    Timer localTimer = new Timer();
    localTimer.reset().start();
    int i = paramInt;
    do
    {
      i--;
    } while (i >= 0);
    localTimer.stop().display();
    float f = localTimer.elapsedTime();
    System.out.println("empty loop timing done.");
    Object localObject = new MersenneTwister();
    System.out.println("\n MersenneTwister:");
    localTimer.reset().start();
    int j = paramInt;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      ((RandomEngine)localObject).raw();
    }
    localTimer.stop().display();
    System.out.println(paramInt / (localTimer.elapsedTime() - f) + " numbers per second.");
    localObject = new MersenneTwister64();
    System.out.println("\n MersenneTwister64:");
    localTimer.reset().start();
    j = paramInt;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      ((RandomEngine)localObject).raw();
    }
    localTimer.stop().display();
    System.out.println(paramInt / (localTimer.elapsedTime() - f) + " numbers per second.");
    localObject = new DRand();
    System.out.println("\nDRand:");
    localTimer.reset().start();
    j = paramInt;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      ((RandomEngine)localObject).raw();
    }
    localTimer.stop().display();
    System.out.println(paramInt / (localTimer.elapsedTime() - f) + " numbers per second.");
    Random localRandom = new Random();
    System.out.println("\njava.util.Random.nextFloat():");
    localTimer.reset().start();
    int k = paramInt;
    for (;;)
    {
      k--;
      if (k < 0) {
        break;
      }
      localRandom.nextFloat();
    }
    localTimer.stop().display();
    System.out.println(paramInt / (localTimer.elapsedTime() - f) + " numbers per second.");
    System.out.println("\nGood bye.\n");
  }
  
  public static void main(String[] paramArrayOfString)
  {
    long l1 = Long.parseLong(paramArrayOfString[0]);
    long l2 = Long.parseLong(paramArrayOfString[1]);
    int i = Integer.parseInt(paramArrayOfString[2]);
    int j = Integer.parseInt(paramArrayOfString[3]);
    for (int k = 0; k < j; k++) {
      benchmark(i);
    }
  }
  
  public static void test(int paramInt, RandomEngine paramRandomEngine)
  {
    System.out.println("\n\nint():");
    RandomEngine localRandomEngine = (RandomEngine)paramRandomEngine.clone();
    int i = 0;
    int j = paramInt;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      System.out.print(" " + localRandomEngine.nextInt());
      if (i % 8 == 7) {
        System.out.println();
      }
      i++;
    }
    System.out.println("\n\nGood bye.\n");
  }
  
  private static void xtestRandomFromTo(long paramLong1, long paramLong2, int paramInt)
  {
    System.out.println("from=" + paramLong1 + ", to=" + paramLong2);
    Random localRandom = new Random();
    MersenneTwister localMersenneTwister = new MersenneTwister();
    int i = (int)paramLong1;
    int j = (int)paramLong2;
    Timer localTimer = new Timer().start();
    int k = 0;
    int m = paramInt;
    for (;;)
    {
      m--;
      if (m < 0) {
        break;
      }
      System.out.print(" " + localMersenneTwister.raw());
      if (k % 8 == 7) {
        System.out.println();
      }
      k++;
    }
    localTimer.stop().display();
    System.out.println("Good bye.\n");
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.jet.random.engine.Benchmark
 * JD-Core Version:    0.7.0.1
 */