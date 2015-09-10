package cern.colt.list;

import cern.colt.Arrays;
import cern.colt.Sorting;
import cern.colt.function.IntProcedure;
import cern.jet.math.Arithmetic;
import cern.jet.random.Uniform;
import cern.jet.random.engine.DRand;
import java.util.Date;

public class IntArrayList
  extends AbstractIntList
{
  protected int[] elements;
  
  public IntArrayList()
  {
    this(10);
  }
  
  public IntArrayList(int[] paramArrayOfInt)
  {
    elements(paramArrayOfInt);
  }
  
  public IntArrayList(int paramInt)
  {
    this(new int[paramInt]);
    setSizeRaw(0);
  }
  
  public void add(int paramInt)
  {
    if (this.size == this.elements.length) {
      ensureCapacity(this.size + 1);
    }
    this.elements[(this.size++)] = paramInt;
  }
  
  public void beforeInsert(int paramInt1, int paramInt2)
  {
    if (this.size == paramInt1)
    {
      add(paramInt2);
      return;
    }
    if ((paramInt1 > this.size) || (paramInt1 < 0)) {
      throw new IndexOutOfBoundsException("Index: " + paramInt1 + ", Size: " + this.size);
    }
    ensureCapacity(this.size + 1);
    System.arraycopy(this.elements, paramInt1, this.elements, paramInt1 + 1, this.size - paramInt1);
    this.elements[paramInt1] = paramInt2;
    this.size += 1;
  }
  
  public int binarySearchFromTo(int paramInt1, int paramInt2, int paramInt3)
  {
    return Sorting.binarySearchFromTo(this.elements, paramInt1, paramInt2, paramInt3);
  }
  
  public Object clone()
  {
    IntArrayList localIntArrayList = new IntArrayList((int[])this.elements.clone());
    localIntArrayList.setSizeRaw(this.size);
    return localIntArrayList;
  }
  
  public IntArrayList copy()
  {
    return (IntArrayList)clone();
  }
  
  protected void countSortFromTo(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.size == 0) {
      return;
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    int i = paramInt4 - paramInt3 + 1;
    int[] arrayOfInt1 = new int[i];
    int[] arrayOfInt2 = this.elements;
    int j = paramInt1;
    while (j <= paramInt2) {
      arrayOfInt1[(arrayOfInt2[(j++)] - paramInt3)] += 1;
    }
    j = paramInt1;
    int k = paramInt3;
    int m = 0;
    while (m < i)
    {
      int n = arrayOfInt1[m];
      if (n > 0) {
        if (n == 1)
        {
          arrayOfInt2[(j++)] = k;
        }
        else
        {
          int i1 = j + n - 1;
          fillFromToWith(j, i1, k);
          j = i1 + 1;
        }
      }
      m++;
      k++;
    }
  }
  
  public int[] elements()
  {
    return this.elements;
  }
  
  public AbstractIntList elements(int[] paramArrayOfInt)
  {
    this.elements = paramArrayOfInt;
    this.size = paramArrayOfInt.length;
    return this;
  }
  
  public void ensureCapacity(int paramInt)
  {
    this.elements = Arrays.ensureCapacity(this.elements, paramInt);
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof IntArrayList)) {
      return super.equals(paramObject);
    }
    if (this == paramObject) {
      return true;
    }
    if (paramObject == null) {
      return false;
    }
    IntArrayList localIntArrayList = (IntArrayList)paramObject;
    if (size() != localIntArrayList.size()) {
      return false;
    }
    int[] arrayOfInt1 = elements();
    int[] arrayOfInt2 = localIntArrayList.elements();
    int i = size();
    do
    {
      i--;
      if (i < 0) {
        break;
      }
    } while (arrayOfInt1[i] == arrayOfInt2[i]);
    return false;
    return true;
  }
  
  public boolean forEach(IntProcedure paramIntProcedure)
  {
    int[] arrayOfInt = this.elements;
    int i = this.size;
    int j = 0;
    while (j < i) {
      if (!paramIntProcedure.apply(arrayOfInt[(j++)])) {
        return false;
      }
    }
    return true;
  }
  
  public int get(int paramInt)
  {
    if ((paramInt >= this.size) || (paramInt < 0)) {
      throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + this.size);
    }
    return this.elements[paramInt];
  }
  
  public int getQuick(int paramInt)
  {
    return this.elements[paramInt];
  }
  
  public int indexOfFromTo(int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.size == 0) {
      return -1;
    }
    checkRangeFromTo(paramInt2, paramInt3, this.size);
    int[] arrayOfInt = this.elements;
    for (int i = paramInt2; i <= paramInt3; i++) {
      if (paramInt1 == arrayOfInt[i]) {
        return i;
      }
    }
    return -1;
  }
  
  public int lastIndexOfFromTo(int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.size == 0) {
      return -1;
    }
    checkRangeFromTo(paramInt2, paramInt3, this.size);
    int[] arrayOfInt = this.elements;
    for (int i = paramInt3; i >= paramInt2; i--) {
      if (paramInt1 == arrayOfInt[i]) {
        return i;
      }
    }
    return -1;
  }
  
  public AbstractIntList partFromTo(int paramInt1, int paramInt2)
  {
    if (this.size == 0) {
      return new IntArrayList(0);
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    int[] arrayOfInt = new int[paramInt2 - paramInt1 + 1];
    System.arraycopy(this.elements, paramInt1, arrayOfInt, 0, paramInt2 - paramInt1 + 1);
    return new IntArrayList(arrayOfInt);
  }
  
  public boolean removeAll(AbstractIntList paramAbstractIntList)
  {
    if (!(paramAbstractIntList instanceof IntArrayList)) {
      return super.removeAll(paramAbstractIntList);
    }
    if (paramAbstractIntList.size() == 0) {
      return false;
    }
    int i = paramAbstractIntList.size() - 1;
    int j = 0;
    int[] arrayOfInt = this.elements;
    int k = size();
    double d1 = paramAbstractIntList.size();
    double d2 = k;
    if ((d1 + d2) * Arithmetic.log2(d1) < d2 * d1)
    {
      IntArrayList localIntArrayList = (IntArrayList)paramAbstractIntList.clone();
      localIntArrayList.quickSort();
      for (int n = 0; n < k; n++) {
        if (localIntArrayList.binarySearchFromTo(arrayOfInt[n], 0, i) < 0) {
          arrayOfInt[(j++)] = arrayOfInt[n];
        }
      }
    }
    for (int m = 0; m < k; m++) {
      if (paramAbstractIntList.indexOfFromTo(arrayOfInt[m], 0, i) < 0) {
        arrayOfInt[(j++)] = arrayOfInt[m];
      }
    }
    m = j != k ? 1 : 0;
    setSize(j);
    return m;
  }
  
  public void replaceFromToWithFrom(int paramInt1, int paramInt2, AbstractIntList paramAbstractIntList, int paramInt3)
  {
    if (!(paramAbstractIntList instanceof IntArrayList))
    {
      super.replaceFromToWithFrom(paramInt1, paramInt2, paramAbstractIntList, paramInt3);
      return;
    }
    int i = paramInt2 - paramInt1 + 1;
    if (i > 0)
    {
      checkRangeFromTo(paramInt1, paramInt2, size());
      checkRangeFromTo(paramInt3, paramInt3 + i - 1, paramAbstractIntList.size());
      System.arraycopy(((IntArrayList)paramAbstractIntList).elements, paramInt3, this.elements, paramInt1, i);
    }
  }
  
  public boolean retainAll(AbstractIntList paramAbstractIntList)
  {
    if (!(paramAbstractIntList instanceof IntArrayList)) {
      return super.retainAll(paramAbstractIntList);
    }
    int i = paramAbstractIntList.size() - 1;
    int j = 0;
    int[] arrayOfInt = this.elements;
    int k = size();
    double d1 = paramAbstractIntList.size();
    double d2 = k;
    if ((d1 + d2) * Arithmetic.log2(d1) < d2 * d1)
    {
      IntArrayList localIntArrayList = (IntArrayList)paramAbstractIntList.clone();
      localIntArrayList.quickSort();
      for (int n = 0; n < k; n++) {
        if (localIntArrayList.binarySearchFromTo(arrayOfInt[n], 0, i) >= 0) {
          arrayOfInt[(j++)] = arrayOfInt[n];
        }
      }
    }
    for (int m = 0; m < k; m++) {
      if (paramAbstractIntList.indexOfFromTo(arrayOfInt[m], 0, i) >= 0) {
        arrayOfInt[(j++)] = arrayOfInt[m];
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
    int[] arrayOfInt = this.elements;
    int m = 0;
    while (m < j)
    {
      int i = arrayOfInt[m];
      arrayOfInt[(m++)] = arrayOfInt[k];
      arrayOfInt[(k--)] = i;
    }
  }
  
  public void set(int paramInt1, int paramInt2)
  {
    if ((paramInt1 >= this.size) || (paramInt1 < 0)) {
      throw new IndexOutOfBoundsException("Index: " + paramInt1 + ", Size: " + this.size);
    }
    this.elements[paramInt1] = paramInt2;
  }
  
  public void setQuick(int paramInt1, int paramInt2)
  {
    this.elements[paramInt1] = paramInt2;
  }
  
  public void shuffleFromTo(int paramInt1, int paramInt2)
  {
    if (this.size == 0) {
      return;
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    Uniform localUniform = new Uniform(new DRand(new Date()));
    int[] arrayOfInt = this.elements;
    for (int k = paramInt1; k < paramInt2; k++)
    {
      int j = localUniform.nextIntFromTo(k, paramInt2);
      int i = arrayOfInt[j];
      arrayOfInt[j] = arrayOfInt[k];
      arrayOfInt[k] = i;
    }
  }
  
  public void sortFromTo(int paramInt1, int paramInt2)
  {
    if (this.size == 0) {
      return;
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    int i = this.elements[paramInt1];
    int j = this.elements[paramInt1];
    int[] arrayOfInt = this.elements;
    int k = paramInt1 + 1;
    while (k <= paramInt2)
    {
      int m = arrayOfInt[(k++)];
      if (m > j) {
        j = m;
      } else if (m < i) {
        i = m;
      }
    }
    double d1 = paramInt2 - paramInt1 + 1.0D;
    double d2 = d1 * Math.log(d1) / 0.6931471805599453D;
    double d3 = j - i + 1.0D;
    double d4 = Math.max(d3, d1);
    if ((d3 < 10000.0D) && (d4 < d2)) {
      countSortFromTo(paramInt1, paramInt2, i, j);
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
 * Qualified Name:     cern.colt.list.IntArrayList
 * JD-Core Version:    0.7.0.1
 */