package cern.jet.random.engine;

import java.util.Date;

public class DRand
  extends RandomEngine
{
  private int current;
  public static final int DEFAULT_SEED = 1;
  
  public DRand()
  {
    this(1);
  }
  
  public DRand(int paramInt)
  {
    setSeed(paramInt);
  }
  
  public DRand(Date paramDate)
  {
    this((int)paramDate.getTime());
  }
  
  public int nextInt()
  {
    this.current *= 663608941;
    return this.current;
  }
  
  protected void setSeed(int paramInt)
  {
    if (paramInt < 0) {
      paramInt = -paramInt;
    }
    int i = (int)((Math.pow(2.0D, 32.0D) - 1.0D) / 4.0D);
    if (paramInt >= i) {
      paramInt >>= 3;
    }
    this.current = (4 * paramInt + 1);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.jet.random.engine.DRand
 * JD-Core Version:    0.7.0.1
 */