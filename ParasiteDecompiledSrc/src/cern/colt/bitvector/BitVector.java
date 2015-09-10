package cern.colt.bitvector;

import cern.colt.PersistentObject;
import cern.colt.function.IntProcedure;

public class BitVector
  extends PersistentObject
{
  protected long[] bits;
  protected int nbits;
  
  public BitVector(long[] paramArrayOfLong, int paramInt)
  {
    elements(paramArrayOfLong, paramInt);
  }
  
  public BitVector(int paramInt)
  {
    this(QuickBitVector.makeBitVector(paramInt, 1), paramInt);
  }
  
  public void and(BitVector paramBitVector)
  {
    if (this == paramBitVector) {
      return;
    }
    checkSize(paramBitVector);
    long[] arrayOfLong1 = this.bits;
    long[] arrayOfLong2 = paramBitVector.bits;
    int i = arrayOfLong1.length;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      arrayOfLong1[i] &= arrayOfLong2[i];
    }
  }
  
  public void andNot(BitVector paramBitVector)
  {
    checkSize(paramBitVector);
    long[] arrayOfLong1 = this.bits;
    long[] arrayOfLong2 = paramBitVector.bits;
    int i = arrayOfLong1.length;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      arrayOfLong1[i] &= (arrayOfLong2[i] ^ 0xFFFFFFFF);
    }
  }
  
  public int cardinality()
  {
    int i = 0;
    int j = numberOfFullUnits();
    long[] arrayOfLong = this.bits;
    int k = j;
    for (;;)
    {
      k--;
      if (k < 0) {
        break;
      }
      long l = arrayOfLong[k];
      if (l == -1L)
      {
        i += 64;
      }
      else if (l != 0L)
      {
        int m = 64;
        for (;;)
        {
          m--;
          if (m < 0) {
            break;
          }
          if ((l & 1L << m) != 0L) {
            i++;
          }
        }
      }
    }
    k = numberOfBitsInPartialUnit();
    for (;;)
    {
      k--;
      if (k < 0) {
        break;
      }
      if ((arrayOfLong[j] & 1L << k) != 0L) {
        i++;
      }
    }
    return i;
  }
  
  protected static void checkRangeFromTo(int paramInt1, int paramInt2, int paramInt3)
  {
    if ((paramInt1 < 0) || (paramInt1 > paramInt2) || (paramInt2 >= paramInt3)) {
      throw new IndexOutOfBoundsException("from: " + paramInt1 + ", to: " + paramInt2 + ", size=" + paramInt3);
    }
  }
  
  protected void checkSize(BitVector paramBitVector)
  {
    if (this.nbits > paramBitVector.size()) {
      throw new IllegalArgumentException("Incompatible sizes: size=" + this.nbits + ", other.size()=" + paramBitVector.size());
    }
  }
  
  public void clear()
  {
    long[] arrayOfLong = this.bits;
    int i = arrayOfLong.length;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      arrayOfLong[i] = 0L;
    }
  }
  
  public void clear(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this.nbits)) {
      throw new IndexOutOfBoundsException(String.valueOf(paramInt));
    }
    QuickBitVector.clear(this.bits, paramInt);
  }
  
  public Object clone()
  {
    BitVector localBitVector = (BitVector)super.clone();
    if (this.bits != null) {
      localBitVector.bits = ((long[])this.bits.clone());
    }
    return localBitVector;
  }
  
  public BitVector copy()
  {
    return (BitVector)clone();
  }
  
  public long[] elements()
  {
    return this.bits;
  }
  
  public void elements(long[] paramArrayOfLong, int paramInt)
  {
    if ((paramInt < 0) || (paramInt > paramArrayOfLong.length * 64)) {
      throw new IllegalArgumentException();
    }
    this.bits = paramArrayOfLong;
    this.nbits = paramInt;
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject == null) || (!(paramObject instanceof BitVector))) {
      return false;
    }
    if (this == paramObject) {
      return true;
    }
    BitVector localBitVector = (BitVector)paramObject;
    if (size() != localBitVector.size()) {
      return false;
    }
    int i = numberOfFullUnits();
    int j = i;
    do
    {
      j--;
      if (j < 0) {
        break;
      }
    } while (this.bits[j] == localBitVector.bits[j]);
    return false;
    j = i * 64;
    int k = numberOfBitsInPartialUnit();
    for (;;)
    {
      k--;
      if (k < 0) {
        break;
      }
      if (get(j) != localBitVector.get(j)) {
        return false;
      }
      j++;
    }
    return true;
  }
  
  public boolean forEachIndexFromToInState(int paramInt1, int paramInt2, boolean paramBoolean, IntProcedure paramIntProcedure)
  {
    if (this.nbits == 0) {
      return true;
    }
    checkRangeFromTo(paramInt1, paramInt2, this.nbits);
    long[] arrayOfLong = this.bits;
    int i = QuickBitVector.unit(paramInt1);
    int j = QuickBitVector.unit(paramInt2);
    int k = paramInt1;
    int m = QuickBitVector.offset(paramInt1);
    int n;
    if (m > 0)
    {
      n = Math.min(paramInt2 - paramInt1 + 1, 64 - m);
      for (;;)
      {
        n--;
        if (n < 0) {
          break;
        }
        if ((QuickBitVector.get(arrayOfLong, k) == paramBoolean) && (!paramIntProcedure.apply(k))) {
          return false;
        }
        k++;
      }
      i++;
    }
    if (k > paramInt2) {
      return true;
    }
    m = QuickBitVector.offset(paramInt2);
    if (m < 63)
    {
      j--;
      n = m + 1;
    }
    else
    {
      n = 0;
    }
    long l1;
    if (paramBoolean) {
      l1 = 0L;
    } else {
      l1 = -1L;
    }
    for (int i1 = i; i1 <= j; i1++)
    {
      long l2 = arrayOfLong[i1];
      if (l2 != l1)
      {
        if (paramBoolean)
        {
          i2 = 0;
          i3 = 64;
          for (;;)
          {
            i3--;
            if (i3 < 0) {
              break;
            }
            if (((l2 & 1L << i2++) != 0L) && (!paramIntProcedure.apply(k))) {
              return false;
            }
            k++;
          }
        }
        int i2 = 0;
        int i3 = 64;
        for (;;)
        {
          i3--;
          if (i3 < 0) {
            break;
          }
          if (((l2 & 1L << i2++) == 0L) && (!paramIntProcedure.apply(k))) {
            return false;
          }
          k++;
        }
      }
      k += 64;
    }
    for (;;)
    {
      n--;
      if (n < 0) {
        break;
      }
      if ((QuickBitVector.get(arrayOfLong, k) == paramBoolean) && (!paramIntProcedure.apply(k))) {
        return false;
      }
      k++;
    }
    return true;
  }
  
  public boolean get(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this.nbits)) {
      throw new IndexOutOfBoundsException(String.valueOf(paramInt));
    }
    return QuickBitVector.get(this.bits, paramInt);
  }
  
  public long getLongFromTo(int paramInt1, int paramInt2)
  {
    int i = paramInt2 - paramInt1 + 1;
    if (i == 0) {
      return 0L;
    }
    if ((paramInt1 < 0) || (paramInt1 >= this.nbits) || (paramInt2 < 0) || (paramInt2 >= this.nbits) || (i < 0) || (i > 64)) {
      throw new IndexOutOfBoundsException("from:" + paramInt1 + ", to:" + paramInt2);
    }
    return QuickBitVector.getLongFromTo(this.bits, paramInt1, paramInt2);
  }
  
  public boolean getQuick(int paramInt)
  {
    return QuickBitVector.get(this.bits, paramInt);
  }
  
  public int hashCode()
  {
    long l = 1234L;
    int i = this.bits.length;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      l ^= this.bits[i] * (i + 1);
    }
    return (int)(l >> 32 ^ l);
  }
  
  public int indexOfFromTo(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    IndexProcedure localIndexProcedure = new IndexProcedure(null);
    forEachIndexFromToInState(paramInt1, paramInt2, paramBoolean, localIndexProcedure);
    return localIndexProcedure.foundPos;
  }
  
  public void not()
  {
    long[] arrayOfLong = this.bits;
    int i = arrayOfLong.length;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      arrayOfLong[i] ^= 0xFFFFFFFF;
    }
  }
  
  protected int numberOfBitsInPartialUnit()
  {
    return QuickBitVector.offset(this.nbits);
  }
  
  protected int numberOfFullUnits()
  {
    return QuickBitVector.unit(this.nbits);
  }
  
  public void or(BitVector paramBitVector)
  {
    if (this == paramBitVector) {
      return;
    }
    checkSize(paramBitVector);
    long[] arrayOfLong1 = this.bits;
    long[] arrayOfLong2 = paramBitVector.bits;
    int i = arrayOfLong1.length;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      arrayOfLong1[i] |= arrayOfLong2[i];
    }
  }
  
  public BitVector partFromTo(int paramInt1, int paramInt2)
  {
    if ((this.nbits == 0) || (paramInt2 == paramInt1 - 1)) {
      return new BitVector(0);
    }
    checkRangeFromTo(paramInt1, paramInt2, this.nbits);
    int i = paramInt2 - paramInt1 + 1;
    BitVector localBitVector = new BitVector(i);
    localBitVector.replaceFromToWith(0, i - 1, this, paramInt1);
    return localBitVector;
  }
  
  public void put(int paramInt, boolean paramBoolean)
  {
    if ((paramInt < 0) || (paramInt >= this.nbits)) {
      throw new IndexOutOfBoundsException(String.valueOf(paramInt));
    }
    if (paramBoolean) {
      QuickBitVector.set(this.bits, paramInt);
    } else {
      QuickBitVector.clear(this.bits, paramInt);
    }
  }
  
  public void putLongFromTo(long paramLong, int paramInt1, int paramInt2)
  {
    int i = paramInt2 - paramInt1 + 1;
    if (i == 0) {
      return;
    }
    if ((paramInt1 < 0) || (paramInt1 >= this.nbits) || (paramInt2 < 0) || (paramInt2 >= this.nbits) || (i < 0) || (i > 64)) {
      throw new IndexOutOfBoundsException("from:" + paramInt1 + ", to:" + paramInt2);
    }
    QuickBitVector.putLongFromTo(this.bits, paramLong, paramInt1, paramInt2);
  }
  
  public void putQuick(int paramInt, boolean paramBoolean)
  {
    if (paramBoolean) {
      QuickBitVector.set(this.bits, paramInt);
    } else {
      QuickBitVector.clear(this.bits, paramInt);
    }
  }
  
  public void replaceFromToWith(int paramInt1, int paramInt2, BitVector paramBitVector, int paramInt3)
  {
    if ((this.nbits == 0) || (paramInt2 == paramInt1 - 1)) {
      return;
    }
    checkRangeFromTo(paramInt1, paramInt2, this.nbits);
    int i = paramInt2 - paramInt1 + 1;
    if ((paramInt3 < 0) || (paramInt3 + i > paramBitVector.size())) {
      throw new IndexOutOfBoundsException();
    }
    if ((paramBitVector.bits == this.bits) && (paramInt1 <= paramInt3) && (paramInt3 <= paramInt2)) {
      paramBitVector = paramBitVector.copy();
    }
    long[] arrayOfLong1 = this.bits;
    long[] arrayOfLong2 = paramBitVector.bits;
    int j = paramInt2 - paramInt1 + 1;
    int k = QuickBitVector.unit(j);
    int m = k;
    for (;;)
    {
      m--;
      if (m < 0) {
        break;
      }
      l = QuickBitVector.getLongFromTo(arrayOfLong2, paramInt3, paramInt3 + 63);
      QuickBitVector.putLongFromTo(arrayOfLong1, l, paramInt1, paramInt1 + 63);
      paramInt3 += 64;
      paramInt1 += 64;
    }
    m = QuickBitVector.offset(j);
    long l = QuickBitVector.getLongFromTo(arrayOfLong2, paramInt3, paramInt3 + m - 1);
    QuickBitVector.putLongFromTo(arrayOfLong1, l, paramInt1, paramInt1 + m - 1);
  }
  
  public void replaceFromToWith(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if ((this.nbits == 0) || (paramInt2 == paramInt1 - 1)) {
      return;
    }
    checkRangeFromTo(paramInt1, paramInt2, this.nbits);
    long[] arrayOfLong = this.bits;
    int i = QuickBitVector.unit(paramInt1);
    int j = QuickBitVector.offset(paramInt1);
    int k = QuickBitVector.unit(paramInt2);
    int m = QuickBitVector.offset(paramInt2);
    int n = 64;
    long l;
    if (paramBoolean) {
      l = -1L;
    } else {
      l = 0L;
    }
    int i1 = paramInt1;
    if (i == k)
    {
      QuickBitVector.putLongFromTo(arrayOfLong, l, i1, i1 + paramInt2 - paramInt1);
      return;
    }
    if (j > 0)
    {
      QuickBitVector.putLongFromTo(arrayOfLong, l, i1, i1 + n - j);
      i1 += n - j + 1;
      i++;
    }
    if (m < n - 1) {
      k--;
    }
    int i2 = i;
    while (i2 <= k) {
      arrayOfLong[(i2++)] = l;
    }
    if (i <= k) {
      i1 += (k - i + 1) * n;
    }
    if (m < n - 1) {
      QuickBitVector.putLongFromTo(arrayOfLong, l, i1, paramInt2);
    }
  }
  
  public void set(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this.nbits)) {
      throw new IndexOutOfBoundsException(String.valueOf(paramInt));
    }
    QuickBitVector.set(this.bits, paramInt);
  }
  
  public void setSize(int paramInt)
  {
    if (paramInt != size())
    {
      BitVector localBitVector = new BitVector(paramInt);
      localBitVector.replaceFromToWith(0, Math.min(size(), paramInt) - 1, this, 0);
      elements(localBitVector.elements(), paramInt);
    }
  }
  
  public int size()
  {
    return this.nbits;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer(this.nbits);
    String str = "";
    localStringBuffer.append('{');
    for (int i = 0; i < this.nbits; i++) {
      if (get(i))
      {
        localStringBuffer.append(str);
        str = ", ";
        localStringBuffer.append(i);
      }
    }
    localStringBuffer.append('}');
    return localStringBuffer.toString();
  }
  
  public void xor(BitVector paramBitVector)
  {
    checkSize(paramBitVector);
    long[] arrayOfLong1 = this.bits;
    long[] arrayOfLong2 = paramBitVector.bits;
    int i = arrayOfLong1.length;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      arrayOfLong1[i] ^= arrayOfLong2[i];
    }
  }
  
  private class IndexProcedure
    implements IntProcedure
  {
    private int foundPos = -1;
    
    private IndexProcedure() {}
    
    public boolean apply(int paramInt)
    {
      this.foundPos = paramInt;
      return false;
    }
    
    IndexProcedure(BitVector.1 param1)
    {
      this();
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.bitvector.BitVector
 * JD-Core Version:    0.7.0.1
 */