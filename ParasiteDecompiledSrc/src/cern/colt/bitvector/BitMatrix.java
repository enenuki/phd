package cern.colt.bitvector;

import cern.colt.PersistentObject;
import cern.colt.function.IntIntProcedure;
import java.awt.Rectangle;

public class BitMatrix
  extends PersistentObject
{
  protected int columns;
  protected int rows;
  protected long[] bits;
  
  public BitMatrix(int paramInt1, int paramInt2)
  {
    elements(QuickBitVector.makeBitVector(paramInt1 * paramInt2, 1), paramInt1, paramInt2);
  }
  
  public void and(BitMatrix paramBitMatrix)
  {
    checkDimensionCompatibility(paramBitMatrix);
    toBitVector().and(paramBitMatrix.toBitVector());
  }
  
  public void andNot(BitMatrix paramBitMatrix)
  {
    checkDimensionCompatibility(paramBitMatrix);
    toBitVector().andNot(paramBitMatrix.toBitVector());
  }
  
  public int cardinality()
  {
    return toBitVector().cardinality();
  }
  
  protected void checkDimensionCompatibility(BitMatrix paramBitMatrix)
  {
    if ((this.columns != paramBitMatrix.columns()) || (this.rows != paramBitMatrix.rows())) {
      throw new IllegalArgumentException("Incompatible dimensions: (columns,rows)=(" + this.columns + "," + this.rows + "), (other.columns,other.rows)=(" + paramBitMatrix.columns() + "," + paramBitMatrix.rows() + ")");
    }
  }
  
  public void clear()
  {
    toBitVector().clear();
  }
  
  public Object clone()
  {
    BitMatrix localBitMatrix = (BitMatrix)super.clone();
    if (this.bits != null) {
      localBitMatrix.bits = ((long[])this.bits.clone());
    }
    return localBitMatrix;
  }
  
  public int columns()
  {
    return this.columns;
  }
  
  protected void containsBox(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((paramInt1 < 0) || (paramInt1 + paramInt3 > this.columns) || (paramInt2 < 0) || (paramInt2 + paramInt4 > this.rows)) {
      throw new IndexOutOfBoundsException("column:" + paramInt1 + ", row:" + paramInt2 + " ,width:" + paramInt3 + ", height:" + paramInt4);
    }
  }
  
  public BitMatrix copy()
  {
    return (BitMatrix)clone();
  }
  
  protected long[] elements()
  {
    return this.bits;
  }
  
  protected void elements(long[] paramArrayOfLong, int paramInt1, int paramInt2)
  {
    if ((paramInt1 < 0) || (paramInt1 < 0) || (paramInt1 * paramInt2 > paramArrayOfLong.length * 64)) {
      throw new IllegalArgumentException();
    }
    this.bits = paramArrayOfLong;
    this.columns = paramInt1;
    this.rows = paramInt2;
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject == null) || (!(paramObject instanceof BitMatrix))) {
      return false;
    }
    if (this == paramObject) {
      return true;
    }
    BitMatrix localBitMatrix = (BitMatrix)paramObject;
    if ((this.columns != localBitMatrix.columns()) || (this.rows != localBitMatrix.rows())) {
      return false;
    }
    return toBitVector().equals(localBitMatrix.toBitVector());
  }
  
  public boolean forEachCoordinateInState(boolean paramBoolean, IntIntProcedure paramIntIntProcedure)
  {
    if (size() == 0) {
      return true;
    }
    BitVector localBitVector = new BitVector(this.bits, size());
    long[] arrayOfLong = this.bits;
    int i = this.columns - 1;
    int j = this.rows - 1;
    long l1 = arrayOfLong[(this.bits.length - 1)];
    int k = localBitVector.numberOfBitsInPartialUnit();
    long l2;
    for (;;)
    {
      k--;
      if (k < 0) {
        break;
      }
      l2 = l1 & 1L << k;
      if (((paramBoolean) && (l2 != 0L)) || ((!paramBoolean) && (l2 == 0L) && (!paramIntIntProcedure.apply(i, j)))) {
        return false;
      }
      i--;
      if (i < 0)
      {
        i = this.columns - 1;
        j--;
      }
    }
    if (paramBoolean) {
      l2 = 0L;
    } else {
      l2 = -1L;
    }
    int m = localBitVector.numberOfFullUnits();
    for (;;)
    {
      m--;
      if (m < 0) {
        break;
      }
      l1 = arrayOfLong[m];
      int n;
      if (l1 != l2)
      {
        if (paramBoolean)
        {
          n = 64;
          for (;;)
          {
            n--;
            if (n < 0) {
              break;
            }
            if (((l1 & 1L << n) != 0L) && (!paramIntIntProcedure.apply(i, j))) {
              return false;
            }
            i--;
            if (i < 0)
            {
              i = this.columns - 1;
              j--;
            }
          }
        }
        else
        {
          n = 64;
          for (;;)
          {
            n--;
            if (n < 0) {
              break;
            }
            if (((l1 & 1L << n) == 0L) && (!paramIntIntProcedure.apply(i, j))) {
              return false;
            }
            i--;
            if (i < 0)
            {
              i = this.columns - 1;
              j--;
            }
          }
        }
      }
      else
      {
        i -= 64;
        if (i < 0)
        {
          i += 64;
          n = 64;
          for (;;)
          {
            n--;
            if (n < 0) {
              break;
            }
            i--;
            if (i < 0)
            {
              i = this.columns - 1;
              j--;
            }
          }
        }
      }
    }
    return true;
  }
  
  public boolean get(int paramInt1, int paramInt2)
  {
    if ((paramInt1 < 0) || (paramInt1 >= this.columns) || (paramInt2 < 0) || (paramInt2 >= this.rows)) {
      throw new IndexOutOfBoundsException("column:" + paramInt1 + ", row:" + paramInt2);
    }
    return QuickBitVector.get(this.bits, paramInt2 * this.columns + paramInt1);
  }
  
  public boolean getQuick(int paramInt1, int paramInt2)
  {
    return QuickBitVector.get(this.bits, paramInt2 * this.columns + paramInt1);
  }
  
  public int hashCode()
  {
    return toBitVector().hashCode();
  }
  
  public void not()
  {
    toBitVector().not();
  }
  
  public void or(BitMatrix paramBitMatrix)
  {
    checkDimensionCompatibility(paramBitMatrix);
    toBitVector().or(paramBitMatrix.toBitVector());
  }
  
  public BitMatrix part(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((paramInt1 < 0) || (paramInt1 + paramInt3 > this.columns) || (paramInt2 < 0) || (paramInt2 + paramInt4 > this.rows)) {
      throw new IndexOutOfBoundsException("column:" + paramInt1 + ", row:" + paramInt2 + " ,width:" + paramInt3 + ", height:" + paramInt4);
    }
    if ((paramInt3 <= 0) || (paramInt4 <= 0)) {
      return new BitMatrix(0, 0);
    }
    BitMatrix localBitMatrix = new BitMatrix(paramInt3, paramInt4);
    localBitMatrix.replaceBoxWith(0, 0, paramInt3, paramInt4, this, paramInt1, paramInt2);
    return localBitMatrix;
  }
  
  public void put(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if ((paramInt1 < 0) || (paramInt1 >= this.columns) || (paramInt2 < 0) || (paramInt2 >= this.rows)) {
      throw new IndexOutOfBoundsException("column:" + paramInt1 + ", row:" + paramInt2);
    }
    QuickBitVector.put(this.bits, paramInt2 * this.columns + paramInt1, paramBoolean);
  }
  
  public void putQuick(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    QuickBitVector.put(this.bits, paramInt2 * this.columns + paramInt1, paramBoolean);
  }
  
  public void replaceBoxWith(int paramInt1, int paramInt2, int paramInt3, int paramInt4, BitMatrix paramBitMatrix, int paramInt5, int paramInt6)
  {
    containsBox(paramInt1, paramInt2, paramInt3, paramInt4);
    paramBitMatrix.containsBox(paramInt5, paramInt6, paramInt3, paramInt4);
    if ((paramInt3 <= 0) || (paramInt4 <= 0)) {
      return;
    }
    if (paramBitMatrix == this)
    {
      localObject1 = new Rectangle(paramInt1, paramInt2, paramInt3, paramInt4);
      localObject2 = new Rectangle(paramInt5, paramInt6, paramInt3, paramInt4);
      if (((Rectangle)localObject1).intersects((Rectangle)localObject2)) {
        paramBitMatrix = paramBitMatrix.copy();
      }
    }
    Object localObject1 = paramBitMatrix.toBitVector();
    Object localObject2 = toBitVector();
    int i = paramBitMatrix.columns();
    for (;;)
    {
      paramInt4--;
      if (paramInt4 < 0) {
        break;
      }
      int j = paramInt2 * this.columns + paramInt1;
      int k = paramInt6 * i + paramInt5;
      ((BitVector)localObject2).replaceFromToWith(j, j + paramInt3 - 1, (BitVector)localObject1, k);
      paramInt2++;
      paramInt6++;
    }
  }
  
  public void replaceBoxWith(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
  {
    containsBox(paramInt1, paramInt2, paramInt3, paramInt4);
    if ((paramInt3 <= 0) || (paramInt4 <= 0)) {
      return;
    }
    BitVector localBitVector = toBitVector();
    for (;;)
    {
      paramInt4--;
      if (paramInt4 < 0) {
        break;
      }
      int i = paramInt2 * this.columns + paramInt1;
      localBitVector.replaceFromToWith(i, i + paramInt3 - 1, paramBoolean);
      paramInt2++;
    }
  }
  
  public int rows()
  {
    return this.rows;
  }
  
  public int size()
  {
    return this.columns * this.rows;
  }
  
  public BitVector toBitVector()
  {
    return new BitVector(this.bits, size());
  }
  
  public String toString()
  {
    return toBitVector().toString();
  }
  
  public void xor(BitMatrix paramBitMatrix)
  {
    checkDimensionCompatibility(paramBitMatrix);
    toBitVector().xor(paramBitMatrix.toBitVector());
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.bitvector.BitMatrix
 * JD-Core Version:    0.7.0.1
 */