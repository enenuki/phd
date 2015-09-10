package cern.jet.random;

import cern.jet.random.engine.MersenneTwister;
import cern.jet.random.engine.RandomEngine;

public class Uniform
  extends AbstractContinousDistribution
{
  protected double min;
  protected double max;
  protected static Uniform shared = new Uniform(makeDefaultGenerator());
  
  public Uniform(double paramDouble1, double paramDouble2, int paramInt)
  {
    this(paramDouble1, paramDouble2, new MersenneTwister(paramInt));
  }
  
  public Uniform(double paramDouble1, double paramDouble2, RandomEngine paramRandomEngine)
  {
    setRandomGenerator(paramRandomEngine);
    setState(paramDouble1, paramDouble2);
  }
  
  public Uniform(RandomEngine paramRandomEngine)
  {
    this(0.0D, 1.0D, paramRandomEngine);
  }
  
  public double cdf(double paramDouble)
  {
    if (paramDouble <= this.min) {
      return 0.0D;
    }
    if (paramDouble >= this.max) {
      return 1.0D;
    }
    return (paramDouble - this.min) / (this.max - this.min);
  }
  
  public boolean nextBoolean()
  {
    return this.randomGenerator.raw() > 0.5D;
  }
  
  public double nextDouble()
  {
    return this.min + (this.max - this.min) * this.randomGenerator.raw();
  }
  
  public double nextDoubleFromTo(double paramDouble1, double paramDouble2)
  {
    return paramDouble1 + (paramDouble2 - paramDouble1) * this.randomGenerator.raw();
  }
  
  public float nextFloatFromTo(float paramFloat1, float paramFloat2)
  {
    return (float)nextDoubleFromTo(paramFloat1, paramFloat2);
  }
  
  public int nextInt()
  {
    return nextIntFromTo((int)Math.round(this.min), (int)Math.round(this.max));
  }
  
  public int nextIntFromTo(int paramInt1, int paramInt2)
  {
    return (int)(paramInt1 + ((1L + paramInt2 - paramInt1) * this.randomGenerator.raw()));
  }
  
  public long nextLongFromTo(long paramLong1, long paramLong2)
  {
    if ((paramLong1 >= 0L) && (paramLong2 < 9223372036854775807L)) {
      return paramLong1 + nextDoubleFromTo(0.0D, paramLong2 - paramLong1 + 1L);
    }
    double d = paramLong2 - paramLong1 + 1.0D;
    if (d <= 9.223372036854776E+018D) {
      return paramLong1 + nextDoubleFromTo(0.0D, d);
    }
    long l;
    if (paramLong1 == -9223372036854775808L)
    {
      if (paramLong2 == 9223372036854775807L)
      {
        int i = nextIntFromTo(-2147483648, 2147483647);
        int j = nextIntFromTo(-2147483648, 2147483647);
        return (i & 0xFFFFFFFF) << 32 | j & 0xFFFFFFFF;
      }
      l = Math.round(nextDoubleFromTo(paramLong1, paramLong2 + 1L));
      if (l > paramLong2) {
        l = paramLong1;
      }
    }
    else
    {
      l = Math.round(nextDoubleFromTo(paramLong1 - 1L, paramLong2));
      if (l < paramLong1) {
        l = paramLong2;
      }
    }
    return l;
  }
  
  public double pdf(double paramDouble)
  {
    if ((paramDouble <= this.min) || (paramDouble >= this.max)) {
      return 0.0D;
    }
    return 1.0D / (this.max - this.min);
  }
  
  public void setState(double paramDouble1, double paramDouble2)
  {
    if (paramDouble2 < paramDouble1)
    {
      setState(paramDouble2, paramDouble1);
      return;
    }
    this.min = paramDouble1;
    this.max = paramDouble2;
  }
  
  public static boolean staticNextBoolean()
  {
    synchronized (shared)
    {
      return shared.nextBoolean();
    }
  }
  
  public static double staticNextDouble()
  {
    synchronized (shared)
    {
      return shared.nextDouble();
    }
  }
  
  public static double staticNextDoubleFromTo(double paramDouble1, double paramDouble2)
  {
    synchronized (shared)
    {
      return shared.nextDoubleFromTo(paramDouble1, paramDouble2);
    }
  }
  
  public static float staticNextFloatFromTo(float paramFloat1, float paramFloat2)
  {
    synchronized (shared)
    {
      return shared.nextFloatFromTo(paramFloat1, paramFloat2);
    }
  }
  
  public static int staticNextIntFromTo(int paramInt1, int paramInt2)
  {
    synchronized (shared)
    {
      return shared.nextIntFromTo(paramInt1, paramInt2);
    }
  }
  
  public static long staticNextLongFromTo(long paramLong1, long paramLong2)
  {
    synchronized (shared)
    {
      return shared.nextLongFromTo(paramLong1, paramLong2);
    }
  }
  
  public static void staticSetRandomEngine(RandomEngine paramRandomEngine)
  {
    synchronized (shared)
    {
      shared.setRandomGenerator(paramRandomEngine);
    }
  }
  
  public String toString()
  {
    return getClass().getName() + "(" + this.min + "," + this.max + ")";
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.jet.random.Uniform
 * JD-Core Version:    0.7.0.1
 */