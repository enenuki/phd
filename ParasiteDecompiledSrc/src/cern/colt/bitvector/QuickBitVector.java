package cern.colt.bitvector;

public class QuickBitVector
{
  protected static final int ADDRESS_BITS_PER_UNIT = 6;
  protected static final int BITS_PER_UNIT = 64;
  protected static final int BIT_INDEX_MASK = 63;
  private static final long[] pows = ;
  
  public static final long bitMaskWithBitsSetFromTo(int paramInt1, int paramInt2)
  {
    return pows[(paramInt2 - paramInt1 + 1)] << paramInt1;
  }
  
  public static void clear(long[] paramArrayOfLong, int paramInt)
  {
    paramArrayOfLong[(paramInt >> 6)] &= (1L << (paramInt & 0x3F) ^ 0xFFFFFFFF);
  }
  
  public static boolean get(long[] paramArrayOfLong, int paramInt)
  {
    return (paramArrayOfLong[(paramInt >> 6)] & 1L << (paramInt & 0x3F)) != 0L;
  }
  
  public static long getLongFromTo(long[] paramArrayOfLong, int paramInt1, int paramInt2)
  {
    if (paramInt1 > paramInt2) {
      return 0L;
    }
    int i = paramInt1 >> 6;
    int j = paramInt2 >> 6;
    int k = paramInt1 & 0x3F;
    int m = paramInt2 & 0x3F;
    if (i == j)
    {
      l1 = bitMaskWithBitsSetFromTo(k, m);
      return (paramArrayOfLong[i] & l1) >>> k;
    }
    long l1 = bitMaskWithBitsSetFromTo(k, 63);
    long l2 = (paramArrayOfLong[i] & l1) >>> k;
    l1 = bitMaskWithBitsSetFromTo(0, m);
    long l3 = (paramArrayOfLong[j] & l1) << 64 - k;
    return l2 | l3;
  }
  
  public static int leastSignificantBit(int paramInt)
  {
    int i = -1;
    do
    {
      i++;
    } while ((i < 32) && ((1 << i & paramInt) == 0));
    return i;
  }
  
  public static long[] makeBitVector(int paramInt1, int paramInt2)
  {
    int i = paramInt1 * paramInt2;
    int j = i - 1 >> 6;
    long[] arrayOfLong = new long[j + 1];
    return arrayOfLong;
  }
  
  public static int mostSignificantBit(int paramInt)
  {
    int i = 32;
    do
    {
      i--;
    } while ((i >= 0) && ((1 << i & paramInt) == 0));
    return i;
  }
  
  protected static int offset(int paramInt)
  {
    return paramInt & 0x3F;
  }
  
  private static long[] precomputePows()
  {
    long[] arrayOfLong = new long[65];
    long l = -1L;
    int i = 65;
    for (;;)
    {
      i--;
      if (i < 1) {
        break;
      }
      arrayOfLong[i] = (l >>> 64 - i);
    }
    arrayOfLong[0] = 0L;
    return arrayOfLong;
  }
  
  public static void put(long[] paramArrayOfLong, int paramInt, boolean paramBoolean)
  {
    if (paramBoolean) {
      set(paramArrayOfLong, paramInt);
    } else {
      clear(paramArrayOfLong, paramInt);
    }
  }
  
  public static void putLongFromTo(long[] paramArrayOfLong, long paramLong, int paramInt1, int paramInt2)
  {
    if (paramInt1 > paramInt2) {
      return;
    }
    int i = paramInt1 >> 6;
    int j = paramInt2 >> 6;
    int k = paramInt1 & 0x3F;
    int m = paramInt2 & 0x3F;
    long l1 = bitMaskWithBitsSetFromTo(paramInt2 - paramInt1 + 1, 63);
    long l2 = paramLong & (l1 ^ 0xFFFFFFFF);
    if (i == j)
    {
      l3 = l2 << k;
      l1 = bitMaskWithBitsSetFromTo(k, m);
      paramArrayOfLong[i] = (paramArrayOfLong[i] & (l1 ^ 0xFFFFFFFF) | l3);
      return;
    }
    long l3 = l2 << k;
    l1 = bitMaskWithBitsSetFromTo(k, 63);
    paramArrayOfLong[i] = (paramArrayOfLong[i] & (l1 ^ 0xFFFFFFFF) | l3);
    l3 = l2 >>> 64 - k;
    l1 = bitMaskWithBitsSetFromTo(0, m);
    paramArrayOfLong[j] = (paramArrayOfLong[j] & (l1 ^ 0xFFFFFFFF) | l3);
  }
  
  public static void set(long[] paramArrayOfLong, int paramInt)
  {
    paramArrayOfLong[(paramInt >> 6)] |= 1L << (paramInt & 0x3F);
  }
  
  protected static int unit(int paramInt)
  {
    return paramInt >> 6;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.bitvector.QuickBitVector
 * JD-Core Version:    0.7.0.1
 */