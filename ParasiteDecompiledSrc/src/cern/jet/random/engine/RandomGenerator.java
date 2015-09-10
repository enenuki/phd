package cern.jet.random.engine;

public abstract interface RandomGenerator
{
  public abstract double raw();
  
  public abstract double nextDouble();
  
  public abstract int nextInt();
  
  public abstract long nextLong();
  
  public abstract float nextFloat();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.jet.random.engine.RandomGenerator
 * JD-Core Version:    0.7.0.1
 */