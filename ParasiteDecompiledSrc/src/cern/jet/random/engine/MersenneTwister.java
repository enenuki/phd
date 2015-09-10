package cern.jet.random.engine;

import java.util.Date;

public class MersenneTwister
  extends RandomEngine
{
  private int mti;
  private int[] mt = new int[624];
  private static final int N = 624;
  private static final int M = 397;
  private static final int MATRIX_A = -1727483681;
  private static final int UPPER_MASK = -2147483648;
  private static final int LOWER_MASK = 2147483647;
  private static final int TEMPERING_MASK_B = -1658038656;
  private static final int TEMPERING_MASK_C = -272236544;
  private static final int mag0 = 0;
  private static final int mag1 = -1727483681;
  public static final int DEFAULT_SEED = 4357;
  
  public MersenneTwister()
  {
    this(4357);
  }
  
  public MersenneTwister(int paramInt)
  {
    setSeed(paramInt);
  }
  
  public MersenneTwister(Date paramDate)
  {
    this((int)paramDate.getTime());
  }
  
  public Object clone()
  {
    MersenneTwister localMersenneTwister = (MersenneTwister)super.clone();
    localMersenneTwister.mt = ((int[])this.mt.clone());
    return localMersenneTwister;
  }
  
  protected void nextBlock()
  {
    for (int j = 0; j < 227; j++)
    {
      i = this.mt[j] & 0x80000000 | this.mt[(j + 1)] & 0x7FFFFFFF;
      this.mt[j] = (this.mt[(j + 397)] ^ i >>> 1 ^ ((i & 0x1) == 0 ? 0 : -1727483681));
    }
    while (j < 623)
    {
      i = this.mt[j] & 0x80000000 | this.mt[(j + 1)] & 0x7FFFFFFF;
      this.mt[j] = (this.mt[(j + -227)] ^ i >>> 1 ^ ((i & 0x1) == 0 ? 0 : -1727483681));
      j++;
    }
    int i = this.mt[623] & 0x80000000 | this.mt[0] & 0x7FFFFFFF;
    this.mt[623] = (this.mt[396] ^ i >>> 1 ^ ((i & 0x1) == 0 ? 0 : -1727483681));
    this.mti = 0;
  }
  
  public int nextInt()
  {
    if (this.mti == 624) {
      nextBlock();
    }
    int i = this.mt[(this.mti++)];
    i ^= i >>> 11;
    i ^= i << 7 & 0x9D2C5680;
    i ^= i << 15 & 0xEFC60000;
    i ^= i >>> 18;
    return i;
  }
  
  protected void setSeed(int paramInt)
  {
    this.mt[0] = (paramInt & 0xFFFFFFFF);
    for (int i = 1; i < 624; i++)
    {
      this.mt[i] = (1812433253 * (this.mt[(i - 1)] ^ this.mt[(i - 1)] >> 30) + i);
      this.mt[i] &= 0xFFFFFFFF;
    }
    this.mti = 624;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.jet.random.engine.MersenneTwister
 * JD-Core Version:    0.7.0.1
 */