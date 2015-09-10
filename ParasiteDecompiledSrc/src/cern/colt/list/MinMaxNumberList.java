package cern.colt.list;

import cern.colt.bitvector.BitVector;
import cern.colt.bitvector.QuickBitVector;
import cern.jet.math.Arithmetic;

public class MinMaxNumberList
  extends AbstractLongList
{
  protected long minValue;
  protected int bitsPerElement;
  protected long[] bits;
  protected int capacity;
  
  public MinMaxNumberList(long paramLong1, long paramLong2, int paramInt)
  {
    setUp(paramLong1, paramLong2, paramInt);
  }
  
  public void add(long paramLong)
  {
    if (this.size == this.capacity) {
      ensureCapacity(this.size + 1);
    }
    int i = this.size * this.bitsPerElement;
    QuickBitVector.putLongFromTo(this.bits, paramLong - this.minValue, i, i + this.bitsPerElement - 1);
    this.size += 1;
  }
  
  public void addAllOfFromTo(long[] paramArrayOfLong, int paramInt1, int paramInt2)
  {
    int i = this.bitsPerElement;
    int j = i - 1;
    long l = this.minValue;
    long[] arrayOfLong = this.bits;
    ensureCapacity(this.size + paramInt2 - paramInt1 + 1);
    int k = this.size * i;
    int m = paramInt1;
    int n = paramInt2 - paramInt1 + 1;
    for (;;)
    {
      n--;
      if (n < 0) {
        break;
      }
      QuickBitVector.putLongFromTo(arrayOfLong, paramArrayOfLong[(m++)] - l, k, k + j);
      k += i;
    }
    this.size += paramInt2 - paramInt1 + 1;
  }
  
  public int bitsPerElement()
  {
    return this.bitsPerElement;
  }
  
  public static int bitsPerElement(long paramLong1, long paramLong2)
  {
    int i;
    if (1L + paramLong2 - paramLong1 > 0L) {
      i = (int)Math.round(Math.ceil(Arithmetic.log(2.0D, 1L + paramLong2 - paramLong1)));
    } else {
      i = 64;
    }
    return i;
  }
  
  public void ensureCapacity(int paramInt)
  {
    int i = this.capacity;
    if (paramInt > i)
    {
      int j = i * 3 / 2 + 1;
      if (j < paramInt) {
        j = paramInt;
      }
      BitVector localBitVector = toBitVector();
      localBitVector.setSize(j * this.bitsPerElement);
      this.bits = localBitVector.elements();
      this.capacity = j;
    }
  }
  
  public long getQuick(int paramInt)
  {
    int i = paramInt * this.bitsPerElement;
    return this.minValue + QuickBitVector.getLongFromTo(this.bits, i, i + this.bitsPerElement - 1);
  }
  
  public void partFromTo(int paramInt1, int paramInt2, BitVector paramBitVector, int paramInt3, long[] paramArrayOfLong, int paramInt4)
  {
    int i = paramInt2 - paramInt1 + 1;
    if ((paramInt1 < 0) || (paramInt1 > paramInt2) || (paramInt2 >= this.size) || (paramInt3 < 0) || ((paramBitVector != null) && (paramInt3 + i > paramBitVector.size()))) {
      throw new IndexOutOfBoundsException();
    }
    if ((paramInt4 < 0) || (paramInt4 + i > paramArrayOfLong.length)) {
      throw new IndexOutOfBoundsException();
    }
    long l = this.minValue;
    int j = this.bitsPerElement;
    long[] arrayOfLong = this.bits;
    int k = paramInt3;
    int m = paramInt4;
    int n = paramInt1 * j;
    int i1 = paramInt1;
    while (i1 <= paramInt2)
    {
      if ((paramBitVector == null) || (paramBitVector.get(k))) {
        paramArrayOfLong[m] = (l + QuickBitVector.getLongFromTo(arrayOfLong, n, n + j - 1));
      }
      i1++;
      k++;
      m++;
      n += j;
    }
  }
  
  public void setQuick(int paramInt, long paramLong)
  {
    int i = paramInt * this.bitsPerElement;
    QuickBitVector.putLongFromTo(this.bits, paramLong - this.minValue, i, i + this.bitsPerElement - 1);
  }
  
  protected void setSizeRaw(int paramInt)
  {
    super.setSizeRaw(paramInt);
  }
  
  protected void setUp(long paramLong1, long paramLong2, int paramInt)
  {
    setUpBitsPerEntry(paramLong1, paramLong2);
    this.bits = QuickBitVector.makeBitVector(paramInt, this.bitsPerElement);
    this.capacity = paramInt;
    this.size = 0;
  }
  
  protected void setUpBitsPerEntry(long paramLong1, long paramLong2)
  {
    this.bitsPerElement = bitsPerElement(paramLong1, paramLong2);
    if (this.bitsPerElement != 64) {
      this.minValue = paramLong1;
    } else {
      this.minValue = 0L;
    }
  }
  
  public BitVector toBitVector()
  {
    return new BitVector(this.bits, this.capacity * this.bitsPerElement);
  }
  
  public void trimToSize()
  {
    int i = this.capacity;
    if (this.size < i)
    {
      BitVector localBitVector = toBitVector();
      localBitVector.setSize(this.size);
      this.bits = localBitVector.elements();
      this.capacity = this.size;
    }
  }
  
  /**
   * @deprecated
   */
  public long xminimum()
  {
    return this.minValue;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.list.MinMaxNumberList
 * JD-Core Version:    0.7.0.1
 */