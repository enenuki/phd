package cern.colt.list;

import cern.colt.Arrays;
import cern.colt.Sorting;
import cern.colt.function.LongProcedure;
import cern.jet.math.Arithmetic;
import cern.jet.random.Uniform;
import cern.jet.random.engine.DRand;
import java.util.Date;

public class LongArrayList
  extends AbstractLongList
{
  protected long[] elements;
  
  public LongArrayList()
  {
    this(10);
  }
  
  public LongArrayList(long[] paramArrayOfLong)
  {
    elements(paramArrayOfLong);
  }
  
  public LongArrayList(int paramInt)
  {
    this(new long[paramInt]);
    setSizeRaw(0);
  }
  
  public void add(long paramLong)
  {
    if (this.size == this.elements.length) {
      ensureCapacity(this.size + 1);
    }
    this.elements[(this.size++)] = paramLong;
  }
  
  public void beforeInsert(int paramInt, long paramLong)
  {
    if ((paramInt > this.size) || (paramInt < 0)) {
      throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + this.size);
    }
    ensureCapacity(this.size + 1);
    System.arraycopy(this.elements, paramInt, this.elements, paramInt + 1, this.size - paramInt);
    this.elements[paramInt] = paramLong;
    this.size += 1;
  }
  
  public int binarySearchFromTo(long paramLong, int paramInt1, int paramInt2)
  {
    return Sorting.binarySearchFromTo(this.elements, paramLong, paramInt1, paramInt2);
  }
  
  public Object clone()
  {
    LongArrayList localLongArrayList = new LongArrayList((long[])this.elements.clone());
    localLongArrayList.setSizeRaw(this.size);
    return localLongArrayList;
  }
  
  public LongArrayList copy()
  {
    return (LongArrayList)clone();
  }
  
  protected void countSortFromTo(int paramInt1, int paramInt2, long paramLong1, long paramLong2)
  {
    if (this.size == 0) {
      return;
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    int i = (int)(paramLong2 - paramLong1 + 1L);
    int[] arrayOfInt = new int[i];
    long[] arrayOfLong = this.elements;
    int j = paramInt1;
    while (j <= paramInt2) {
      arrayOfInt[((int)(arrayOfLong[(j++)] - paramLong1))] += 1;
    }
    j = paramInt1;
    long l = paramLong1;
    int k = 0;
    while (k < i)
    {
      int m = arrayOfInt[k];
      if (m > 0) {
        if (m == 1)
        {
          arrayOfLong[(j++)] = l;
        }
        else
        {
          int n = j + m - 1;
          fillFromToWith(j, n, l);
          j = n + 1;
        }
      }
      k++;
      l += 1L;
    }
  }
  
  public long[] elements()
  {
    return this.elements;
  }
  
  public AbstractLongList elements(long[] paramArrayOfLong)
  {
    this.elements = paramArrayOfLong;
    this.size = paramArrayOfLong.length;
    return this;
  }
  
  public void ensureCapacity(int paramInt)
  {
    this.elements = Arrays.ensureCapacity(this.elements, paramInt);
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof LongArrayList)) {
      return super.equals(paramObject);
    }
    if (this == paramObject) {
      return true;
    }
    if (paramObject == null) {
      return false;
    }
    LongArrayList localLongArrayList = (LongArrayList)paramObject;
    if (size() != localLongArrayList.size()) {
      return false;
    }
    long[] arrayOfLong1 = elements();
    long[] arrayOfLong2 = localLongArrayList.elements();
    int i = size();
    do
    {
      i--;
      if (i < 0) {
        break;
      }
    } while (arrayOfLong1[i] == arrayOfLong2[i]);
    return false;
    return true;
  }
  
  public boolean forEach(LongProcedure paramLongProcedure)
  {
    long[] arrayOfLong = this.elements;
    int i = this.size;
    int j = 0;
    while (j < i) {
      if (!paramLongProcedure.apply(arrayOfLong[(j++)])) {
        return false;
      }
    }
    return true;
  }
  
  public long get(int paramInt)
  {
    if ((paramInt >= this.size) || (paramInt < 0)) {
      throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + this.size);
    }
    return this.elements[paramInt];
  }
  
  public long getQuick(int paramInt)
  {
    return this.elements[paramInt];
  }
  
  public int indexOfFromTo(long paramLong, int paramInt1, int paramInt2)
  {
    if (this.size == 0) {
      return -1;
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    long[] arrayOfLong = this.elements;
    for (int i = paramInt1; i <= paramInt2; i++) {
      if (paramLong == arrayOfLong[i]) {
        return i;
      }
    }
    return -1;
  }
  
  public int lastIndexOfFromTo(long paramLong, int paramInt1, int paramInt2)
  {
    if (this.size == 0) {
      return -1;
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    long[] arrayOfLong = this.elements;
    for (int i = paramInt2; i >= paramInt1; i--) {
      if (paramLong == arrayOfLong[i]) {
        return i;
      }
    }
    return -1;
  }
  
  public AbstractLongList partFromTo(int paramInt1, int paramInt2)
  {
    if (this.size == 0) {
      return new LongArrayList(0);
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    long[] arrayOfLong = new long[paramInt2 - paramInt1 + 1];
    System.arraycopy(this.elements, paramInt1, arrayOfLong, 0, paramInt2 - paramInt1 + 1);
    return new LongArrayList(arrayOfLong);
  }
  
  public boolean removeAll(AbstractLongList paramAbstractLongList)
  {
    if (!(paramAbstractLongList instanceof LongArrayList)) {
      return super.removeAll(paramAbstractLongList);
    }
    if (paramAbstractLongList.size() == 0) {
      return false;
    }
    int i = paramAbstractLongList.size() - 1;
    int j = 0;
    long[] arrayOfLong = this.elements;
    int k = size();
    double d1 = paramAbstractLongList.size();
    double d2 = k;
    if ((d1 + d2) * Arithmetic.log2(d1) < d2 * d1)
    {
      LongArrayList localLongArrayList = (LongArrayList)paramAbstractLongList.clone();
      localLongArrayList.quickSort();
      for (int n = 0; n < k; n++) {
        if (localLongArrayList.binarySearchFromTo(arrayOfLong[n], 0, i) < 0) {
          arrayOfLong[(j++)] = arrayOfLong[n];
        }
      }
    }
    for (int m = 0; m < k; m++) {
      if (paramAbstractLongList.indexOfFromTo(arrayOfLong[m], 0, i) < 0) {
        arrayOfLong[(j++)] = arrayOfLong[m];
      }
    }
    m = j != k ? 1 : 0;
    setSize(j);
    return m;
  }
  
  public void replaceFromToWithFrom(int paramInt1, int paramInt2, AbstractLongList paramAbstractLongList, int paramInt3)
  {
    if (!(paramAbstractLongList instanceof LongArrayList))
    {
      super.replaceFromToWithFrom(paramInt1, paramInt2, paramAbstractLongList, paramInt3);
      return;
    }
    int i = paramInt2 - paramInt1 + 1;
    if (i > 0)
    {
      checkRangeFromTo(paramInt1, paramInt2, size());
      checkRangeFromTo(paramInt3, paramInt3 + i - 1, paramAbstractLongList.size());
      System.arraycopy(((LongArrayList)paramAbstractLongList).elements, paramInt3, this.elements, paramInt1, i);
    }
  }
  
  public boolean retainAll(AbstractLongList paramAbstractLongList)
  {
    if (!(paramAbstractLongList instanceof LongArrayList)) {
      return super.retainAll(paramAbstractLongList);
    }
    int i = paramAbstractLongList.size() - 1;
    int j = 0;
    long[] arrayOfLong = this.elements;
    int k = size();
    double d1 = paramAbstractLongList.size();
    double d2 = k;
    if ((d1 + d2) * Arithmetic.log2(d1) < d2 * d1)
    {
      LongArrayList localLongArrayList = (LongArrayList)paramAbstractLongList.clone();
      localLongArrayList.quickSort();
      for (int n = 0; n < k; n++) {
        if (localLongArrayList.binarySearchFromTo(arrayOfLong[n], 0, i) >= 0) {
          arrayOfLong[(j++)] = arrayOfLong[n];
        }
      }
    }
    for (int m = 0; m < k; m++) {
      if (paramAbstractLongList.indexOfFromTo(arrayOfLong[m], 0, i) >= 0) {
        arrayOfLong[(j++)] = arrayOfLong[m];
      }
    }
    m = j != k ? 1 : 0;
    setSize(j);
    return m;
  }
  
  public void reverse()
  {
    int i = this.size / 2;
    int j = this.size - 1;
    long[] arrayOfLong = this.elements;
    int k = 0;
    while (k < i)
    {
      long l = arrayOfLong[k];
      arrayOfLong[(k++)] = arrayOfLong[j];
      arrayOfLong[(j--)] = l;
    }
  }
  
  public void set(int paramInt, long paramLong)
  {
    if ((paramInt >= this.size) || (paramInt < 0)) {
      throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + this.size);
    }
    this.elements[paramInt] = paramLong;
  }
  
  public void setQuick(int paramInt, long paramLong)
  {
    this.elements[paramInt] = paramLong;
  }
  
  public void shuffleFromTo(int paramInt1, int paramInt2)
  {
    if (this.size == 0) {
      return;
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    Uniform localUniform = new Uniform(new DRand(new Date()));
    long[] arrayOfLong = this.elements;
    for (int j = paramInt1; j < paramInt2; j++)
    {
      int i = localUniform.nextIntFromTo(j, paramInt2);
      long l = arrayOfLong[i];
      arrayOfLong[i] = arrayOfLong[j];
      arrayOfLong[j] = l;
    }
  }
  
  public void sortFromTo(int paramInt1, int paramInt2)
  {
    if (this.size == 0) {
      return;
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    long l1 = this.elements[paramInt1];
    long l2 = this.elements[paramInt1];
    long[] arrayOfLong = this.elements;
    int i = paramInt1 + 1;
    while (i <= paramInt2)
    {
      long l3 = arrayOfLong[(i++)];
      if (l3 > l2) {
        l2 = l3;
      } else if (l3 < l1) {
        l1 = l3;
      }
    }
    double d1 = paramInt2 - paramInt1 + 1.0D;
    double d2 = d1 * Math.log(d1) / 0.6931471805599453D;
    double d3 = l2 - l1 + 1.0D;
    double d4 = Math.max(d3, d1);
    if ((d3 < 10000.0D) && (d4 < d2)) {
      countSortFromTo(paramInt1, paramInt2, l1, l2);
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
 * Qualified Name:     cern.colt.list.LongArrayList
 * JD-Core Version:    0.7.0.1
 */