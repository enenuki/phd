package cern.jet.random.engine;

import cern.colt.PersistentObject;
import cern.colt.function.DoubleFunction;
import cern.colt.function.IntFunction;

public abstract class RandomEngine
  extends PersistentObject
  implements DoubleFunction, IntFunction
{
  public double apply(double paramDouble)
  {
    return raw();
  }
  
  public int apply(int paramInt)
  {
    return nextInt();
  }
  
  public static RandomEngine makeDefault()
  {
    return new MersenneTwister((int)System.currentTimeMillis());
  }
  
  public double nextDouble()
  {
    double d;
    do
    {
      d = (nextLong() - -9.223372036854776E+018D) * 5.421010862427522E-020D;
    } while ((d <= 0.0D) || (d >= 1.0D));
    return d;
  }
  
  public float nextFloat()
  {
    float f;
    do
    {
      f = (float)raw();
    } while (f >= 1.0F);
    return f;
  }
  
  public abstract int nextInt();
  
  public long nextLong()
  {
    return (nextInt() & 0xFFFFFFFF) << 32 | nextInt() & 0xFFFFFFFF;
  }
  
  public double raw()
  {
    int i;
    do
    {
      i = nextInt();
    } while (i == 0);
    return (i & 0xFFFFFFFF) * 2.328306436538696E-010D;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.jet.random.engine.RandomEngine
 * JD-Core Version:    0.7.0.1
 */