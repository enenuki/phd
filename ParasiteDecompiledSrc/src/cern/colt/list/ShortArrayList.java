package cern.colt.list;

import cern.colt.Arrays;
import cern.colt.Sorting;
import cern.colt.function.ShortProcedure;
import cern.jet.math.Arithmetic;
import cern.jet.random.Uniform;
import cern.jet.random.engine.DRand;
import java.util.Date;

public class ShortArrayList
  extends AbstractShortList
{
  protected short[] elements;
  
  public ShortArrayList()
  {
    this(10);
  }
  
  public ShortArrayList(short[] paramArrayOfShort)
  {
    elements(paramArrayOfShort);
  }
  
  public ShortArrayList(int paramInt)
  {
    this(new short[paramInt]);
    setSizeRaw(0);
  }
  
  public void add(short paramShort)
  {
    if (this.size == this.elements.length) {
      ensureCapacity(this.size + 1);
    }
    this.elements[(this.size++)] = paramShort;
  }
  
  public void beforeInsert(int paramInt, short paramShort)
  {
    if ((paramInt > this.size) || (paramInt < 0)) {
      throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + this.size);
    }
    ensureCapacity(this.size + 1);
    System.arraycopy(this.elements, paramInt, this.elements, paramInt + 1, this.size - paramInt);
    this.elements[paramInt] = paramShort;
    this.size += 1;
  }
  
  public int binarySearchFromTo(short paramShort, int paramInt1, int paramInt2)
  {
    return Sorting.binarySearchFromTo(this.elements, paramShort, paramInt1, paramInt2);
  }
  
  public Object clone()
  {
    ShortArrayList localShortArrayList = new ShortArrayList((short[])this.elements.clone());
    localShortArrayList.setSizeRaw(this.size);
    return localShortArrayList;
  }
  
  public ShortArrayList copy()
  {
    return (ShortArrayList)clone();
  }
  
  protected void countSortFromTo(int paramInt1, int paramInt2, short paramShort1, short paramShort2)
  {
    if (this.size == 0) {
      return;
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    int i = paramShort2 - paramShort1 + 1;
    int[] arrayOfInt = new int[i];
    short[] arrayOfShort = this.elements;
    int j = paramInt1;
    while (j <= paramInt2) {
      arrayOfInt[(arrayOfShort[(j++)] - paramShort1)] += 1;
    }
    j = paramInt1;
    short s = paramShort1;
    int k = 0;
    while (k < i)
    {
      int m = arrayOfInt[k];
      if (m > 0) {
        if (m == 1)
        {
          arrayOfShort[(j++)] = s;
        }
        else
        {
          int n = j + m - 1;
          fillFromToWith(j, n, s);
          j = n + 1;
        }
      }
      k++;
      s = (short)(s + 1);
    }
  }
  
  public short[] elements()
  {
    return this.elements;
  }
  
  public AbstractShortList elements(short[] paramArrayOfShort)
  {
    this.elements = paramArrayOfShort;
    this.size = paramArrayOfShort.length;
    return this;
  }
  
  public void ensureCapacity(int paramInt)
  {
    this.elements = Arrays.ensureCapacity(this.elements, paramInt);
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof ShortArrayList)) {
      return super.equals(paramObject);
    }
    if (this == paramObject) {
      return true;
    }
    if (paramObject == null) {
      return false;
    }
    ShortArrayList localShortArrayList = (ShortArrayList)paramObject;
    if (size() != localShortArrayList.size()) {
      return false;
    }
    short[] arrayOfShort1 = elements();
    short[] arrayOfShort2 = localShortArrayList.elements();
    int i = size();
    do
    {
      i--;
      if (i < 0) {
        break;
      }
    } while (arrayOfShort1[i] == arrayOfShort2[i]);
    return false;
    return true;
  }
  
  public boolean forEach(ShortProcedure paramShortProcedure)
  {
    short[] arrayOfShort = this.elements;
    int i = this.size;
    int j = 0;
    while (j < i) {
      if (!paramShortProcedure.apply(arrayOfShort[(j++)])) {
        return false;
      }
    }
    return true;
  }
  
  public short get(int paramInt)
  {
    if ((paramInt >= this.size) || (paramInt < 0)) {
      throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + this.size);
    }
    return this.elements[paramInt];
  }
  
  public short getQuick(int paramInt)
  {
    return this.elements[paramInt];
  }
  
  public int indexOfFromTo(short paramShort, int paramInt1, int paramInt2)
  {
    if (this.size == 0) {
      return -1;
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    short[] arrayOfShort = this.elements;
    for (int i = paramInt1; i <= paramInt2; i++) {
      if (paramShort == arrayOfShort[i]) {
        return i;
      }
    }
    return -1;
  }
  
  public int lastIndexOfFromTo(short paramShort, int paramInt1, int paramInt2)
  {
    if (this.size == 0) {
      return -1;
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    short[] arrayOfShort = this.elements;
    for (int i = paramInt2; i >= paramInt1; i--) {
      if (paramShort == arrayOfShort[i]) {
        return i;
      }
    }
    return -1;
  }
  
  public AbstractShortList partFromTo(int paramInt1, int paramInt2)
  {
    if (this.size == 0) {
      return new ShortArrayList(0);
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    short[] arrayOfShort = new short[paramInt2 - paramInt1 + 1];
    System.arraycopy(this.elements, paramInt1, arrayOfShort, 0, paramInt2 - paramInt1 + 1);
    return new ShortArrayList(arrayOfShort);
  }
  
  public boolean removeAll(AbstractShortList paramAbstractShortList)
  {
    if (!(paramAbstractShortList instanceof ShortArrayList)) {
      return super.removeAll(paramAbstractShortList);
    }
    if (paramAbstractShortList.size() == 0) {
      return false;
    }
    int i = paramAbstractShortList.size() - 1;
    int j = 0;
    short[] arrayOfShort = this.elements;
    int k = size();
    double d1 = paramAbstractShortList.size();
    double d2 = k;
    if ((d1 + d2) * Arithmetic.log2(d1) < d2 * d1)
    {
      ShortArrayList localShortArrayList = (ShortArrayList)paramAbstractShortList.clone();
      localShortArrayList.quickSort();
      for (int n = 0; n < k; n++) {
        if (localShortArrayList.binarySearchFromTo(arrayOfShort[n], 0, i) < 0) {
          arrayOfShort[(j++)] = arrayOfShort[n];
        }
      }
    }
    for (int m = 0; m < k; m++) {
      if (paramAbstractShortList.indexOfFromTo(arrayOfShort[m], 0, i) < 0) {
        arrayOfShort[(j++)] = arrayOfShort[m];
      }
    }
    m = j != k ? 1 : 0;
    setSize(j);
    return m;
  }
  
  public void replaceFromToWithFrom(int paramInt1, int paramInt2, AbstractShortList paramAbstractShortList, int paramInt3)
  {
    if (!(paramAbstractShortList instanceof ShortArrayList))
    {
      super.replaceFromToWithFrom(paramInt1, paramInt2, paramAbstractShortList, paramInt3);
      return;
    }
    int i = paramInt2 - paramInt1 + 1;
    if (i > 0)
    {
      checkRangeFromTo(paramInt1, paramInt2, size());
      checkRangeFromTo(paramInt3, paramInt3 + i - 1, paramAbstractShortList.size());
      System.arraycopy(((ShortArrayList)paramAbstractShortList).elements, paramInt3, this.elements, paramInt1, i);
    }
  }
  
  public boolean retainAll(AbstractShortList paramAbstractShortList)
  {
    if (!(paramAbstractShortList instanceof ShortArrayList)) {
      return super.retainAll(paramAbstractShortList);
    }
    int i = paramAbstractShortList.size() - 1;
    int j = 0;
    short[] arrayOfShort = this.elements;
    int k = size();
    double d1 = paramAbstractShortList.size();
    double d2 = k;
    if ((d1 + d2) * Arithmetic.log2(d1) < d2 * d1)
    {
      ShortArrayList localShortArrayList = (ShortArrayList)paramAbstractShortList.clone();
      localShortArrayList.quickSort();
      for (int n = 0; n < k; n++) {
        if (localShortArrayList.binarySearchFromTo(arrayOfShort[n], 0, i) >= 0) {
          arrayOfShort[(j++)] = arrayOfShort[n];
        }
      }
    }
    for (int m = 0; m < k; m++) {
      if (paramAbstractShortList.indexOfFromTo(arrayOfShort[m], 0, i) >= 0) {
        arrayOfShort[(j++)] = arrayOfShort[m];
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
    short[] arrayOfShort = this.elements;
    int m = 0;
    while (m < j)
    {
      int i = arrayOfShort[m];
      arrayOfShort[(m++)] = arrayOfShort[k];
      arrayOfShort[(k--)] = i;
    }
  }
  
  public void set(int paramInt, short paramShort)
  {
    if ((paramInt >= this.size) || (paramInt < 0)) {
      throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + this.size);
    }
    this.elements[paramInt] = paramShort;
  }
  
  public void setQuick(int paramInt, short paramShort)
  {
    this.elements[paramInt] = paramShort;
  }
  
  public void shuffleFromTo(int paramInt1, int paramInt2)
  {
    if (this.size == 0) {
      return;
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    Uniform localUniform = new Uniform(new DRand(new Date()));
    short[] arrayOfShort = this.elements;
    for (int k = paramInt1; k < paramInt2; k++)
    {
      int j = localUniform.nextIntFromTo(k, paramInt2);
      int i = arrayOfShort[j];
      arrayOfShort[j] = arrayOfShort[k];
      arrayOfShort[k] = i;
    }
  }
  
  public void sortFromTo(int paramInt1, int paramInt2)
  {
    if (this.size == 0) {
      return;
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    short s1 = this.elements[paramInt1];
    short s2 = this.elements[paramInt1];
    short[] arrayOfShort = this.elements;
    int i = paramInt1 + 1;
    while (i <= paramInt2)
    {
      short s3 = arrayOfShort[(i++)];
      if (s3 > s2) {
        s2 = s3;
      } else if (s3 < s1) {
        s1 = s3;
      }
    }
    double d1 = paramInt2 - paramInt1 + 1.0D;
    double d2 = d1 * Math.log(d1) / 0.6931471805599453D;
    double d3 = s2 - s1 + 1.0D;
    double d4 = Math.max(d3, d1);
    if ((d3 < 10000.0D) && (d4 < d2)) {
      countSortFromTo(paramInt1, paramInt2, s1, s2);
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
 * Qualified Name:     cern.colt.list.ShortArrayList
 * JD-Core Version:    0.7.0.1
 */