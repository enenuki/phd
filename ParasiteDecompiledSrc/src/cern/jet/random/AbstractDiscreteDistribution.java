package cern.jet.random;

public abstract class AbstractDiscreteDistribution
  extends AbstractDistribution
{
  public double nextDouble()
  {
    return nextInt();
  }
  
  public abstract int nextInt();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.jet.random.AbstractDiscreteDistribution
 * JD-Core Version:    0.7.0.1
 */