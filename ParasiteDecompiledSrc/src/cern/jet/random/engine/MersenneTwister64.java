package cern.jet.random.engine;

import java.util.Date;

public class MersenneTwister64
  extends MersenneTwister
{
  public MersenneTwister64() {}
  
  public MersenneTwister64(int paramInt)
  {
    super(paramInt);
  }
  
  public MersenneTwister64(Date paramDate)
  {
    super(paramDate);
  }
  
  public double raw()
  {
    return nextDouble();
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.jet.random.engine.MersenneTwister64
 * JD-Core Version:    0.7.0.1
 */