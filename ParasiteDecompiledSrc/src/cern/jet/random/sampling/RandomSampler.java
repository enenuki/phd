package cern.jet.random.sampling;

import cern.colt.PersistentObject;
import cern.colt.Timer;
import cern.jet.random.AbstractDistribution;
import cern.jet.random.engine.RandomEngine;
import java.io.PrintStream;

public class RandomSampler
  extends PersistentObject
{
  long my_n;
  long my_N;
  long my_low;
  RandomEngine my_RandomGenerator;
  
  public RandomSampler(long paramLong1, long paramLong2, long paramLong3, RandomEngine paramRandomEngine)
  {
    if (paramLong1 < 0L) {
      throw new IllegalArgumentException("n must be >= 0");
    }
    if (paramLong1 > paramLong2) {
      throw new IllegalArgumentException("n must by <= N");
    }
    this.my_n = paramLong1;
    this.my_N = paramLong2;
    this.my_low = paramLong3;
    if (paramRandomEngine == null) {
      paramRandomEngine = AbstractDistribution.makeDefaultGenerator();
    }
    this.my_RandomGenerator = paramRandomEngine;
  }
  
  public Object clone()
  {
    RandomSampler localRandomSampler = (RandomSampler)super.clone();
    localRandomSampler.my_RandomGenerator = ((RandomEngine)this.my_RandomGenerator.clone());
    return localRandomSampler;
  }
  
  public static void main(String[] paramArrayOfString)
  {
    long l1 = Long.parseLong(paramArrayOfString[0]);
    long l2 = Long.parseLong(paramArrayOfString[1]);
    long l3 = Long.parseLong(paramArrayOfString[2]);
    int i = Integer.parseInt(paramArrayOfString[3]);
    int j = Integer.parseInt(paramArrayOfString[4]);
    test(l1, l2, l3, i, j);
  }
  
  public void nextBlock(int paramInt1, long[] paramArrayOfLong, int paramInt2)
  {
    if (paramInt1 > this.my_n) {
      throw new IllegalArgumentException("Random sample exhausted.");
    }
    if (paramInt1 < 0) {
      throw new IllegalArgumentException("Negative count.");
    }
    if (paramInt1 == 0) {
      return;
    }
    sample(this.my_n, this.my_N, paramInt1, this.my_low, paramArrayOfLong, paramInt2, this.my_RandomGenerator);
    long l = paramArrayOfLong[(paramInt2 + paramInt1 - 1)];
    this.my_n -= paramInt1;
    this.my_N = (this.my_N - l - 1L + this.my_low);
    this.my_low = (l + 1L);
  }
  
  protected static void rejectMethodD(long paramLong1, long paramLong2, int paramInt1, long paramLong3, long[] paramArrayOfLong, int paramInt2, RandomEngine paramRandomEngine)
  {
    paramLong1 = paramLong2 - paramLong1;
    long l5 = -1L + paramLong3;
    long l6 = -13L;
    double d1 = paramLong1;
    double d3 = 1.0D / d1;
    double d2 = paramLong2;
    double d7 = Math.exp(Math.log(paramRandomEngine.raw()) * d3);
    long l1 = -paramLong1 + 1L + paramLong2;
    long l4;
    double d12;
    int i;
    for (double d13 = -d1 + 1.0D + d2; (paramLong1 > 1L) && (paramInt1 > 0); d13 = d12 + d13)
    {
      double d4 = 1.0D / (-1.0D + d1);
      for (;;)
      {
        double d6 = d2 * (-d7 + 1.0D);
        l4 = d6;
        if (l4 >= l1)
        {
          d7 = Math.exp(Math.log(paramRandomEngine.raw()) * d3);
        }
        else
        {
          double d5 = paramRandomEngine.raw();
          d12 = -l4;
          double d8 = Math.exp(Math.log(d5 * d2 / d13) * d4);
          d7 = d8 * (-d6 / d2 + 1.0D) * (d13 / (d12 + d13));
          if (d7 <= 1.0D) {
            break;
          }
          double d9 = 1.0D;
          double d10 = -1.0D + d2;
          double d11;
          long l3;
          if (paramLong1 - 1L > l4)
          {
            d11 = -d1 + d2;
            l3 = -l4 + paramLong2;
          }
          else
          {
            d11 = -1.0D + d12 + d2;
            l3 = l1;
          }
          for (long l2 = paramLong2 - 1L; l2 >= l3; l2 -= 1L)
          {
            d9 = d9 * d10 / d11;
            d10 -= 1.0D;
            d11 -= 1.0D;
          }
          if (d2 / (-d6 + d2) >= d8 * Math.exp(Math.log(d9) * d4))
          {
            d7 = Math.exp(Math.log(paramRandomEngine.raw()) * d4);
            break;
          }
          d7 = Math.exp(Math.log(paramRandomEngine.raw()) * d3);
        }
      }
      i = paramInt1;
      if (l4 < i) {
        i = (int)l4;
      }
      paramInt1 -= i;
      for (;;)
      {
        i--;
        if (i < 0) {
          break;
        }
        paramArrayOfLong[(paramInt2++)] = (++l5);
      }
      l5 += 1L;
      paramLong2 -= l4 + 1L;
      d2 = d12 + (-1.0D + d2);
      paramLong1 -= 1L;
      d1 -= 1.0D;
      d3 = d4;
      l1 = -l4 + l1;
    }
    if (paramInt1 > 0)
    {
      l4 = (paramLong2 * d7);
      i = paramInt1;
      if (l4 < i) {
        i = (int)l4;
      }
      paramInt1 -= i;
      for (;;)
      {
        i--;
        if (i < 0) {
          break;
        }
        paramArrayOfLong[(paramInt2++)] = (++l5);
      }
      l5 += 1L;
      for (;;)
      {
        paramInt1--;
        if (paramInt1 < 0) {
          break;
        }
        paramArrayOfLong[(paramInt2++)] = (++l5);
      }
    }
  }
  
  public static void sample(long paramLong1, long paramLong2, int paramInt1, long paramLong3, long[] paramArrayOfLong, int paramInt2, RandomEngine paramRandomEngine)
  {
    if ((paramLong1 <= 0L) || (paramInt1 <= 0)) {
      return;
    }
    if (paramInt1 > paramLong1) {
      throw new IllegalArgumentException("count must not be greater than n");
    }
    if (paramRandomEngine == null) {
      paramRandomEngine = AbstractDistribution.makeDefaultGenerator();
    }
    if (paramInt1 == paramLong2)
    {
      long l = paramLong3;
      int i = paramInt2 + paramInt1;
      int j = paramInt2;
      while (j < i) {
        paramArrayOfLong[(j++)] = (l++);
      }
      return;
    }
    if (paramLong1 < paramLong2 * 0.95D) {
      sampleMethodD(paramLong1, paramLong2, paramInt1, paramLong3, paramArrayOfLong, paramInt2, paramRandomEngine);
    } else {
      rejectMethodD(paramLong1, paramLong2, paramInt1, paramLong3, paramArrayOfLong, paramInt2, paramRandomEngine);
    }
  }
  
  protected static void sampleMethodA(long paramLong1, long paramLong2, int paramInt1, long paramLong3, long[] paramArrayOfLong, int paramInt2, RandomEngine paramRandomEngine)
  {
    long l2 = -1L + paramLong3;
    double d4 = paramLong2 - paramLong1;
    double d3 = paramLong2;
    long l1;
    while ((paramLong1 >= 2L) && (paramInt1 > 0))
    {
      double d1 = paramRandomEngine.raw();
      l1 = 0L;
      for (double d2 = d4 / d3; d2 > d1; d2 = d2 * d4 / d3)
      {
        l1 += 1L;
        d4 -= 1.0D;
        d3 -= 1.0D;
      }
      l2 += l1 + 1L;
      paramArrayOfLong[(paramInt2++)] = l2;
      paramInt1--;
      d3 -= 1.0D;
      paramLong1 -= 1L;
    }
    if (paramInt1 > 0)
    {
      l1 = (Math.round(d3) * paramRandomEngine.raw());
      l2 += l1 + 1L;
      paramArrayOfLong[paramInt2] = l2;
    }
  }
  
  protected static void sampleMethodD(long paramLong1, long paramLong2, int paramInt1, long paramLong3, long[] paramArrayOfLong, int paramInt2, RandomEngine paramRandomEngine)
  {
    long l6 = -1L + paramLong3;
    long l7 = -13L;
    double d1 = paramLong1;
    double d3 = 1.0D / d1;
    double d2 = paramLong2;
    double d7 = Math.exp(Math.log(paramRandomEngine.raw()) * d3);
    long l1 = -paramLong1 + 1L + paramLong2;
    double d13 = -d1 + 1.0D + d2;
    long l5;
    for (long l2 = -l7 * paramLong1; (paramLong1 > 1L) && (paramInt1 > 0) && (l2 < paramLong2); l2 += l7)
    {
      double d4 = 1.0D / (-1.0D + d1);
      double d12;
      for (;;)
      {
        double d6 = d2 * (-d7 + 1.0D);
        l5 = d6;
        if (l5 >= l1)
        {
          d7 = Math.exp(Math.log(paramRandomEngine.raw()) * d3);
        }
        else
        {
          double d5 = paramRandomEngine.raw();
          d12 = -l5;
          double d8 = Math.exp(Math.log(d5 * d2 / d13) * d4);
          d7 = d8 * (-d6 / d2 + 1.0D) * (d13 / (d12 + d13));
          if (d7 <= 1.0D) {
            break;
          }
          double d9 = 1.0D;
          double d10 = -1.0D + d2;
          double d11;
          long l4;
          if (paramLong1 - 1L > l5)
          {
            d11 = -d1 + d2;
            l4 = -l5 + paramLong2;
          }
          else
          {
            d11 = -1.0D + d12 + d2;
            l4 = l1;
          }
          for (long l3 = paramLong2 - 1L; l3 >= l4; l3 -= 1L)
          {
            d9 = d9 * d10 / d11;
            d10 -= 1.0D;
            d11 -= 1.0D;
          }
          if (d2 / (-d6 + d2) >= d8 * Math.exp(Math.log(d9) * d4))
          {
            d7 = Math.exp(Math.log(paramRandomEngine.raw()) * d4);
            break;
          }
          d7 = Math.exp(Math.log(paramRandomEngine.raw()) * d3);
        }
      }
      l6 += l5 + 1L;
      paramArrayOfLong[(paramInt2++)] = l6;
      paramInt1--;
      paramLong2 -= l5 + 1L;
      d2 = d12 + (-1.0D + d2);
      paramLong1 -= 1L;
      d1 -= 1.0D;
      d3 = d4;
      l1 = -l5 + l1;
      d13 = d12 + d13;
    }
    if (paramInt1 > 0) {
      if (paramLong1 > 1L)
      {
        sampleMethodA(paramLong1, paramLong2, paramInt1, l6 + 1L, paramArrayOfLong, paramInt2, paramRandomEngine);
      }
      else
      {
        l5 = (paramLong2 * d7);
        l6 += l5 + 1L;
        paramArrayOfLong[(paramInt2++)] = l6;
      }
    }
  }
  
  public static void test(long paramLong1, long paramLong2, long paramLong3, int paramInt1, int paramInt2)
  {
    long[] arrayOfLong = new long[paramInt1];
    long l1 = paramLong1 / paramInt1;
    Timer localTimer = new Timer().start();
    long l2 = paramInt2;
    while (--l2 >= 0L)
    {
      RandomSampler localRandomSampler = new RandomSampler(paramLong1, paramLong2, paramLong3, AbstractDistribution.makeDefaultGenerator());
      for (long l3 = 0L; l3 < l1; l3 += 1L) {
        localRandomSampler.nextBlock(paramInt1, arrayOfLong, 0);
      }
      int i = (int)(paramLong1 - paramInt1 * l1);
      if (i > 0) {
        localRandomSampler.nextBlock(i, arrayOfLong, 0);
      }
    }
    localTimer.stop();
    System.out.println("single run took " + localTimer.elapsedTime() / paramInt2);
    System.out.println("Good bye.\n");
  }
  
  protected static void testNegAlphaInv(String[] paramArrayOfString) {}
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.jet.random.sampling.RandomSampler
 * JD-Core Version:    0.7.0.1
 */