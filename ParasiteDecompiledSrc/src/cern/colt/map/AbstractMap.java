package cern.colt.map;

import cern.colt.PersistentObject;

public abstract class AbstractMap
  extends PersistentObject
{
  protected int distinct;
  protected int lowWaterMark;
  protected int highWaterMark;
  protected double minLoadFactor;
  protected double maxLoadFactor;
  protected static final int defaultCapacity = 277;
  protected static final double defaultMinLoadFactor = 0.2D;
  protected static final double defaultMaxLoadFactor = 0.5D;
  
  protected int chooseGrowCapacity(int paramInt, double paramDouble1, double paramDouble2)
  {
    return nextPrime(Math.max(paramInt + 1, (int)(4 * paramInt / (3.0D * paramDouble1 + paramDouble2))));
  }
  
  protected int chooseHighWaterMark(int paramInt, double paramDouble)
  {
    return Math.min(paramInt - 2, (int)(paramInt * paramDouble));
  }
  
  protected int chooseLowWaterMark(int paramInt, double paramDouble)
  {
    return (int)(paramInt * paramDouble);
  }
  
  protected int chooseMeanCapacity(int paramInt, double paramDouble1, double paramDouble2)
  {
    return nextPrime(Math.max(paramInt + 1, (int)(2 * paramInt / (paramDouble1 + paramDouble2))));
  }
  
  protected int chooseShrinkCapacity(int paramInt, double paramDouble1, double paramDouble2)
  {
    return nextPrime(Math.max(paramInt + 1, (int)(4 * paramInt / (paramDouble1 + 3.0D * paramDouble2))));
  }
  
  public abstract void clear();
  
  public void ensureCapacity(int paramInt) {}
  
  public boolean isEmpty()
  {
    return this.distinct == 0;
  }
  
  protected int nextPrime(int paramInt)
  {
    return PrimeFinder.nextPrime(paramInt);
  }
  
  protected void setUp(int paramInt, double paramDouble1, double paramDouble2)
  {
    if (paramInt < 0) {
      throw new IllegalArgumentException("Initial Capacity must not be less than zero: " + paramInt);
    }
    if ((paramDouble1 < 0.0D) || (paramDouble1 >= 1.0D)) {
      throw new IllegalArgumentException("Illegal minLoadFactor: " + paramDouble1);
    }
    if ((paramDouble2 <= 0.0D) || (paramDouble2 >= 1.0D)) {
      throw new IllegalArgumentException("Illegal maxLoadFactor: " + paramDouble2);
    }
    if (paramDouble1 >= paramDouble2) {
      throw new IllegalArgumentException("Illegal minLoadFactor: " + paramDouble1 + " and maxLoadFactor: " + paramDouble2);
    }
  }
  
  public int size()
  {
    return this.distinct;
  }
  
  public void trimToSize() {}
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.map.AbstractMap
 * JD-Core Version:    0.7.0.1
 */