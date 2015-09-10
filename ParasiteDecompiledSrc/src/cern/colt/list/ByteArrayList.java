package cern.colt.list;

import cern.colt.Arrays;
import cern.colt.Sorting;
import cern.colt.function.ByteProcedure;
import cern.jet.math.Arithmetic;
import cern.jet.random.Uniform;
import cern.jet.random.engine.DRand;
import java.util.Date;

public class ByteArrayList
  extends AbstractByteList
{
  protected byte[] elements;
  
  public ByteArrayList()
  {
    this(10);
  }
  
  public ByteArrayList(byte[] paramArrayOfByte)
  {
    elements(paramArrayOfByte);
  }
  
  public ByteArrayList(int paramInt)
  {
    this(new byte[paramInt]);
    setSizeRaw(0);
  }
  
  public void add(byte paramByte)
  {
    if (this.size == this.elements.length) {
      ensureCapacity(this.size + 1);
    }
    this.elements[(this.size++)] = paramByte;
  }
  
  public void beforeInsert(int paramInt, byte paramByte)
  {
    if ((paramInt > this.size) || (paramInt < 0)) {
      throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + this.size);
    }
    ensureCapacity(this.size + 1);
    System.arraycopy(this.elements, paramInt, this.elements, paramInt + 1, this.size - paramInt);
    this.elements[paramInt] = paramByte;
    this.size += 1;
  }
  
  public int binarySearchFromTo(byte paramByte, int paramInt1, int paramInt2)
  {
    return Sorting.binarySearchFromTo(this.elements, paramByte, paramInt1, paramInt2);
  }
  
  public Object clone()
  {
    ByteArrayList localByteArrayList = new ByteArrayList((byte[])this.elements.clone());
    localByteArrayList.setSizeRaw(this.size);
    return localByteArrayList;
  }
  
  public ByteArrayList copy()
  {
    return (ByteArrayList)clone();
  }
  
  public void countSortFromTo(int paramInt1, int paramInt2)
  {
    if (this.size == 0) {
      return;
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    byte[] arrayOfByte = this.elements;
    int[] arrayOfInt = new int[256];
    for (int i = paramInt1; i <= paramInt2; i++) {
      arrayOfInt[(arrayOfByte[i] + 128)] += 1;
    }
    i = paramInt1;
    byte b = -128;
    int j = 0;
    while (j < 256)
    {
      int k = arrayOfInt[j];
      if (k > 0) {
        if (k == 1)
        {
          arrayOfByte[(i++)] = b;
        }
        else
        {
          int m = i + k - 1;
          fillFromToWith(i, m, b);
          i = m + 1;
        }
      }
      j++;
      b = (byte)(b + 1);
    }
  }
  
  protected void countSortFromTo(int paramInt1, int paramInt2, byte paramByte1, byte paramByte2)
  {
    if (this.size == 0) {
      return;
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    int i = paramByte2 - paramByte1 + 1;
    int[] arrayOfInt = new int[i];
    byte[] arrayOfByte = this.elements;
    int j = paramInt1;
    while (j <= paramInt2) {
      arrayOfInt[(arrayOfByte[(j++)] - paramByte1)] += 1;
    }
    j = paramInt1;
    byte b = paramByte1;
    int k = 0;
    while (k < i)
    {
      int m = arrayOfInt[k];
      if (m > 0) {
        if (m == 1)
        {
          arrayOfByte[(j++)] = b;
        }
        else
        {
          int n = j + m - 1;
          fillFromToWith(j, n, b);
          j = n + 1;
        }
      }
      k++;
      b = (byte)(b + 1);
    }
  }
  
  public byte[] elements()
  {
    return this.elements;
  }
  
  public AbstractByteList elements(byte[] paramArrayOfByte)
  {
    this.elements = paramArrayOfByte;
    this.size = paramArrayOfByte.length;
    return this;
  }
  
  public void ensureCapacity(int paramInt)
  {
    this.elements = Arrays.ensureCapacity(this.elements, paramInt);
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof ByteArrayList)) {
      return super.equals(paramObject);
    }
    if (this == paramObject) {
      return true;
    }
    if (paramObject == null) {
      return false;
    }
    ByteArrayList localByteArrayList = (ByteArrayList)paramObject;
    if (size() != localByteArrayList.size()) {
      return false;
    }
    byte[] arrayOfByte1 = elements();
    byte[] arrayOfByte2 = localByteArrayList.elements();
    int i = size();
    do
    {
      i--;
      if (i < 0) {
        break;
      }
    } while (arrayOfByte1[i] == arrayOfByte2[i]);
    return false;
    return true;
  }
  
  public boolean forEach(ByteProcedure paramByteProcedure)
  {
    byte[] arrayOfByte = this.elements;
    int i = this.size;
    int j = 0;
    while (j < i) {
      if (!paramByteProcedure.apply(arrayOfByte[(j++)])) {
        return false;
      }
    }
    return true;
  }
  
  public byte get(int paramInt)
  {
    if ((paramInt >= this.size) || (paramInt < 0)) {
      throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + this.size);
    }
    return this.elements[paramInt];
  }
  
  public byte getQuick(int paramInt)
  {
    return this.elements[paramInt];
  }
  
  public int indexOfFromTo(byte paramByte, int paramInt1, int paramInt2)
  {
    if (this.size == 0) {
      return -1;
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    byte[] arrayOfByte = this.elements;
    for (int i = paramInt1; i <= paramInt2; i++) {
      if (paramByte == arrayOfByte[i]) {
        return i;
      }
    }
    return -1;
  }
  
  public int lastIndexOfFromTo(byte paramByte, int paramInt1, int paramInt2)
  {
    if (this.size == 0) {
      return -1;
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    byte[] arrayOfByte = this.elements;
    for (int i = paramInt2; i >= paramInt1; i--) {
      if (paramByte == arrayOfByte[i]) {
        return i;
      }
    }
    return -1;
  }
  
  public AbstractByteList partFromTo(int paramInt1, int paramInt2)
  {
    if (this.size == 0) {
      return new ByteArrayList(0);
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    byte[] arrayOfByte = new byte[paramInt2 - paramInt1 + 1];
    System.arraycopy(this.elements, paramInt1, arrayOfByte, 0, paramInt2 - paramInt1 + 1);
    return new ByteArrayList(arrayOfByte);
  }
  
  public boolean removeAll(AbstractByteList paramAbstractByteList)
  {
    if (!(paramAbstractByteList instanceof ByteArrayList)) {
      return super.removeAll(paramAbstractByteList);
    }
    if (paramAbstractByteList.size() == 0) {
      return false;
    }
    int i = paramAbstractByteList.size() - 1;
    int j = 0;
    byte[] arrayOfByte = this.elements;
    int k = size();
    double d1 = paramAbstractByteList.size();
    double d2 = k;
    if ((d1 + d2) * Arithmetic.log2(d1) < d2 * d1)
    {
      ByteArrayList localByteArrayList = (ByteArrayList)paramAbstractByteList.clone();
      localByteArrayList.quickSort();
      for (int n = 0; n < k; n++) {
        if (localByteArrayList.binarySearchFromTo(arrayOfByte[n], 0, i) < 0) {
          arrayOfByte[(j++)] = arrayOfByte[n];
        }
      }
    }
    for (int m = 0; m < k; m++) {
      if (paramAbstractByteList.indexOfFromTo(arrayOfByte[m], 0, i) < 0) {
        arrayOfByte[(j++)] = arrayOfByte[m];
      }
    }
    m = j != k ? 1 : 0;
    setSize(j);
    return m;
  }
  
  public void replaceFromToWithFrom(int paramInt1, int paramInt2, AbstractByteList paramAbstractByteList, int paramInt3)
  {
    if (!(paramAbstractByteList instanceof ByteArrayList))
    {
      super.replaceFromToWithFrom(paramInt1, paramInt2, paramAbstractByteList, paramInt3);
      return;
    }
    int i = paramInt2 - paramInt1 + 1;
    if (i > 0)
    {
      checkRangeFromTo(paramInt1, paramInt2, size());
      checkRangeFromTo(paramInt3, paramInt3 + i - 1, paramAbstractByteList.size());
      System.arraycopy(((ByteArrayList)paramAbstractByteList).elements, paramInt3, this.elements, paramInt1, i);
    }
  }
  
  public boolean retainAll(AbstractByteList paramAbstractByteList)
  {
    if (!(paramAbstractByteList instanceof ByteArrayList)) {
      return super.retainAll(paramAbstractByteList);
    }
    int i = paramAbstractByteList.size() - 1;
    int j = 0;
    byte[] arrayOfByte = this.elements;
    int k = size();
    double d1 = paramAbstractByteList.size();
    double d2 = k;
    if ((d1 + d2) * Arithmetic.log2(d1) < d2 * d1)
    {
      ByteArrayList localByteArrayList = (ByteArrayList)paramAbstractByteList.clone();
      localByteArrayList.quickSort();
      for (int n = 0; n < k; n++) {
        if (localByteArrayList.binarySearchFromTo(arrayOfByte[n], 0, i) >= 0) {
          arrayOfByte[(j++)] = arrayOfByte[n];
        }
      }
    }
    for (int m = 0; m < k; m++) {
      if (paramAbstractByteList.indexOfFromTo(arrayOfByte[m], 0, i) >= 0) {
        arrayOfByte[(j++)] = arrayOfByte[m];
      }
    }
    m = j != k ? 1 : 0;
    setSize(j);
    return m;
  }
  
  public void reverse()
  {
    int j = this.size / 2;
    int k = this.size - 1;
    byte[] arrayOfByte = this.elements;
    int m = 0;
    while (m < j)
    {
      int i = arrayOfByte[m];
      arrayOfByte[(m++)] = arrayOfByte[k];
      arrayOfByte[(k--)] = i;
    }
  }
  
  public void set(int paramInt, byte paramByte)
  {
    if ((paramInt >= this.size) || (paramInt < 0)) {
      throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + this.size);
    }
    this.elements[paramInt] = paramByte;
  }
  
  public void setQuick(int paramInt, byte paramByte)
  {
    this.elements[paramInt] = paramByte;
  }
  
  public void shuffleFromTo(int paramInt1, int paramInt2)
  {
    if (this.size == 0) {
      return;
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    Uniform localUniform = new Uniform(new DRand(new Date()));
    byte[] arrayOfByte = this.elements;
    for (int k = paramInt1; k < paramInt2; k++)
    {
      int j = localUniform.nextIntFromTo(k, paramInt2);
      int i = arrayOfByte[j];
      arrayOfByte[j] = arrayOfByte[k];
      arrayOfByte[k] = i;
    }
  }
  
  public void sortFromTo(int paramInt1, int paramInt2)
  {
    double d1 = paramInt2 - paramInt1 + 1;
    double d2 = d1 * Math.log(d1) / 0.6931471805599453D;
    double d3 = 256.0D;
    double d4 = Math.max(d3, d1);
    if (d4 < d2) {
      countSortFromTo(paramInt1, paramInt2);
    } else {
      quickSortFromTo(paramInt1, paramInt2);
    }
  }
  
  public void trimToSize()
  {
    this.elements = Arrays.trimToCapacity(this.elements, size());
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.list.ByteArrayList
 * JD-Core Version:    0.7.0.1
 */