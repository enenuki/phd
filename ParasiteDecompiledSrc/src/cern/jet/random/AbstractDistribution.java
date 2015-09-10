package cern.jet.random;

import cern.colt.PersistentObject;
import cern.colt.function.DoubleFunction;
import cern.colt.function.IntFunction;
import cern.jet.random.engine.RandomEngine;

public abstract class AbstractDistribution
  extends PersistentObject
  implements DoubleFunction, IntFunction
{
  protected RandomEngine randomGenerator;
  
  public double apply(double paramDouble)
  {
    return nextDouble();
  }
  
  public int apply(int paramInt)
  {
    return nextInt();
  }
  
  public Object clone()
  {
    AbstractDistribution localAbstractDistribution = (AbstractDistribution)super.clone();
    if (this.randomGenerator != null) {
      localAbstractDistribution.randomGenerator = ((RandomEngine)this.randomGenerator.clone());
    }
    return localAbstractDistribution;
  }
  
  protected RandomEngine getRandomGenerator()
  {
    return this.randomGenerator;
  }
  
  public static RandomEngine makeDefaultGenerator()
  {
    return RandomEngine.makeDefault();
  }
  
  public abstract double nextDouble();
  
  public int nextInt()
  {
    return (int)Math.round(nextDouble());
  }
  
  protected void setRandomGenerator(RandomEngine paramRandomEngine)
  {
    this.randomGenerator = paramRandomEngine;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.jet.random.AbstractDistribution
 * JD-Core Version:    0.7.0.1
 */