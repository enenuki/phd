package cern.colt.list;

import cern.colt.Sorting;
import cern.colt.buffer.IntBufferConsumer;
import cern.colt.function.IntComparator;
import cern.colt.function.IntProcedure;
import cern.jet.random.Uniform;
import cern.jet.random.engine.DRand;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

public abstract class AbstractIntList
  extends AbstractList
  implements IntBufferConsumer
{
  protected int size;
  
  public void add(int paramInt)
  {
    beforeInsert(this.size, paramInt);
  }
  
  public void addAllOf(IntArrayList paramIntArrayList)
  {
    addAllOfFromTo(paramIntArrayList, 0, paramIntArrayList.size() - 1);
  }
  
  public void addAllOfFromTo(AbstractIntList paramAbstractIntList, int paramInt1, int paramInt2)
  {
    beforeInsertAllOfFromTo(this.size, paramAbstractIntList, paramInt1, paramInt2);
  }
  
  public void beforeInsert(int paramInt1, int paramInt2)
  {
    beforeInsertDummies(paramInt1, 1);
    set(paramInt1, paramInt2);
  }
  
  public void beforeInsertAllOfFromTo(int paramInt1, AbstractIntList paramAbstractIntList, int paramInt2, int paramInt3)
  {
    int i = paramInt3 - paramInt2 + 1;
    beforeInsertDummies(paramInt1, i);
    replaceFromToWithFrom(paramInt1, paramInt1 + i - 1, paramAbstractIntList, paramInt2);
  }
  
  protected void beforeInsertDummies(int paramInt1, int paramInt2)
  {
    if ((paramInt1 > this.size) || (paramInt1 < 0)) {
      throw new IndexOutOfBoundsException("Index: " + paramInt1 + ", Size: " + this.size);
    }
    if (paramInt2 > 0)
    {
      ensureCapacity(this.size + paramInt2);
      setSizeRaw(this.size + paramInt2);
      replaceFromToWithFrom(paramInt1 + paramInt2, this.size - 1, this, paramInt1);
    }
  }
  
  public int binarySearch(int paramInt)
  {
    return binarySearchFromTo(paramInt, 0, this.size - 1);
  }
  
  public int binarySearchFromTo(int paramInt1, int paramInt2, int paramInt3)
  {
    int i = paramInt2;
    int j = paramInt3;
    while (i <= j)
    {
      int k = (i + j) / 2;
      int m = get(k);
      if (m < paramInt1) {
        i = k + 1;
      } else if (m > paramInt1) {
        j = k - 1;
      } else {
        return k;
      }
    }
    return -(i + 1);
  }
  
  public Object clone()
  {
    return partFromTo(0, this.size - 1);
  }
  
  public boolean contains(int paramInt)
  {
    return indexOfFromTo(paramInt, 0, this.size - 1) >= 0;
  }
  
  public void delete(int paramInt)
  {
    int i = indexOfFromTo(paramInt, 0, this.size - 1);
    if (i >= 0) {
      remove(i);
    }
  }
  
  public int[] elements()
  {
    int[] arrayOfInt = new int[this.size];
    int i = this.size;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      arrayOfInt[i] = getQuick(i);
    }
    return arrayOfInt;
  }
  
  public AbstractIntList elements(int[] paramArrayOfInt)
  {
    clear();
    addAllOfFromTo(new IntArrayList(paramArrayOfInt), 0, paramArrayOfInt.length - 1);
    return this;
  }
  
  public abstract void ensureCapacity(int paramInt);
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof AbstractIntList)) {
      return false;
    }
    if (this == paramObject) {
      return true;
    }
    if (paramObject == null) {
      return false;
    }
    AbstractIntList localAbstractIntList = (AbstractIntList)paramObject;
    if (size() != localAbstractIntList.size()) {
      return false;
    }
    int i = size();
    do
    {
      i--;
      if (i < 0) {
        break;
      }
    } while (getQuick(i) == localAbstractIntList.getQuick(i));
    return false;
    return true;
  }
  
  public void fillFromToWith(int paramInt1, int paramInt2, int paramInt3)
  {
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    int i = paramInt1;
    while (i <= paramInt2) {
      setQuick(i++, paramInt3);
    }
  }
  
  public boolean forEach(IntProcedure paramIntProcedure)
  {
    int i = 0;
    while (i < this.size) {
      if (!paramIntProcedure.apply(get(i++))) {
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
    return getQuick(paramInt);
  }
  
  protected abstract int getQuick(int paramInt);
  
  public int indexOf(int paramInt)
  {
    return indexOfFromTo(paramInt, 0, this.size - 1);
  }
  
  public int indexOfFromTo(int paramInt1, int paramInt2, int paramInt3)
  {
    checkRangeFromTo(paramInt2, paramInt3, this.size);
    for (int i = paramInt2; i <= paramInt3; i++) {
      if (paramInt1 == getQuick(i)) {
        return i;
      }
    }
    return -1;
  }
  
  public int lastIndexOf(int paramInt)
  {
    return lastIndexOfFromTo(paramInt, 0, this.size - 1);
  }
  
  public int lastIndexOfFromTo(int paramInt1, int paramInt2, int paramInt3)
  {
    checkRangeFromTo(paramInt2, paramInt3, size());
    for (int i = paramInt3; i >= paramInt2; i--) {
      if (paramInt1 == getQuick(i)) {
        return i;
      }
    }
    return -1;
  }
  
  public void mergeSortFromTo(int paramInt1, int paramInt2)
  {
    int i = size();
    checkRangeFromTo(paramInt1, paramInt2, i);
    int[] arrayOfInt = elements();
    Sorting.mergeSort(arrayOfInt, paramInt1, paramInt2 + 1);
    elements(arrayOfInt);
    setSizeRaw(i);
  }
  
  public void mergeSortFromTo(int paramInt1, int paramInt2, IntComparator paramIntComparator)
  {
    int i = size();
    checkRangeFromTo(paramInt1, paramInt2, i);
    int[] arrayOfInt = elements();
    Sorting.mergeSort(arrayOfInt, paramInt1, paramInt2 + 1, paramIntComparator);
    elements(arrayOfInt);
    setSizeRaw(i);
  }
  
  public AbstractIntList partFromTo(int paramInt1, int paramInt2)
  {
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    int i = paramInt2 - paramInt1 + 1;
    IntArrayList localIntArrayList = new IntArrayList(i);
    localIntArrayList.addAllOfFromTo(this, paramInt1, paramInt2);
    return localIntArrayList;
  }
  
  public void quickSortFromTo(int paramInt1, int paramInt2)
  {
    int i = size();
    checkRangeFromTo(paramInt1, paramInt2, i);
    int[] arrayOfInt = elements();
    java.util.Arrays.sort(arrayOfInt, paramInt1, paramInt2 + 1);
    elements(arrayOfInt);
    setSizeRaw(i);
  }
  
  public void quickSortFromTo(int paramInt1, int paramInt2, IntComparator paramIntComparator)
  {
    int i = size();
    checkRangeFromTo(paramInt1, paramInt2, i);
    int[] arrayOfInt = elements();
    Sorting.quickSort(arrayOfInt, paramInt1, paramInt2 + 1, paramIntComparator);
    elements(arrayOfInt);
    setSizeRaw(i);
  }
  
  public boolean removeAll(AbstractIntList paramAbstractIntList)
  {
    if (paramAbstractIntList.size() == 0) {
      return false;
    }
    int i = paramAbstractIntList.size() - 1;
    int j = 0;
    for (int k = 0; k < this.size; k++) {
      if (paramAbstractIntList.indexOfFromTo(getQuick(k), 0, i) < 0) {
        setQuick(j++, getQuick(k));
      }
    }
    k = j != this.size ? 1 : 0;
    setSize(j);
    return k;
  }
  
  public void removeFromTo(int paramInt1, int paramInt2)
  {
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    int i = this.size - paramInt2 - 1;
    if (i > 0) {
      replaceFromToWithFrom(paramInt1, paramInt1 - 1 + i, this, paramInt2 + 1);
    }
    int j = paramInt2 - paramInt1 + 1;
    if (j > 0) {
      setSizeRaw(this.size - j);
    }
  }
  
  public void replaceFromToWithFrom(int paramInt1, int paramInt2, AbstractIntList paramAbstractIntList, int paramInt3)
  {
    int i = paramInt2 - paramInt1 + 1;
    if (i > 0)
    {
      checkRangeFromTo(paramInt1, paramInt2, size());
      checkRangeFromTo(paramInt3, paramInt3 + i - 1, paramAbstractIntList.size());
      if (paramInt1 <= paramInt3) {
        for (;;)
        {
          i--;
          if (i < 0) {
            break;
          }
          setQuick(paramInt1++, paramAbstractIntList.getQuick(paramInt3++));
        }
      }
      int j = paramInt3 + i - 1;
      for (;;)
      {
        i--;
        if (i < 0) {
          break;
        }
        setQuick(paramInt2--, paramAbstractIntList.getQuick(j--));
      }
    }
  }
  
  public void replaceFromToWithFromTo(int paramInt1, int paramInt2, AbstractIntList paramAbstractIntList, int paramInt3, int paramInt4)
  {
    if (paramInt3 > paramInt4) {
      throw new IndexOutOfBoundsException("otherFrom: " + paramInt3 + ", otherTo: " + paramInt4);
    }
    if ((this == paramAbstractIntList) && (paramInt2 - paramInt1 != paramInt4 - paramInt3))
    {
      replaceFromToWithFromTo(paramInt1, paramInt2, partFromTo(paramInt3, paramInt4), 0, paramInt4 - paramInt3);
      return;
    }
    int i = paramInt4 - paramInt3 + 1;
    int j = i;
    int k = paramInt1 - 1;
    if (paramInt2 >= paramInt1)
    {
      j -= paramInt2 - paramInt1 + 1;
      k = paramInt2;
    }
    if (j > 0) {
      beforeInsertDummies(k + 1, j);
    } else if (j < 0) {
      removeFromTo(k + j, k - 1);
    }
    if (i > 0) {
      replaceFromToWithFrom(paramInt1, paramInt1 + i - 1, paramAbstractIntList, paramInt3);
    }
  }
  
  public void replaceFromWith(int paramInt, Collection paramCollection)
  {
    checkRange(paramInt, size());
    Iterator localIterator = paramCollection.iterator();
    int i = paramInt;
    int j = Math.min(size() - paramInt, paramCollection.size());
    for (int k = 0; k < j; k++) {
      set(i++, ((Number)localIterator.next()).intValue());
    }
  }
  
  public boolean retainAll(AbstractIntList paramAbstractIntList)
  {
    if (paramAbstractIntList.size() == 0)
    {
      if (this.size == 0) {
        return false;
      }
      setSize(0);
      return true;
    }
    int i = paramAbstractIntList.size() - 1;
    int j = 0;
    for (int k = 0; k < this.size; k++) {
      if (paramAbstractIntList.indexOfFromTo(getQuick(k), 0, i) >= 0) {
        setQuick(j++, getQuick(k));
      }
    }
    k = j != this.size ? 1 : 0;
    setSize(j);
    return k;
  }
  
  public void reverse()
  {
    int j = size() / 2;
    int k = size() - 1;
    int m = 0;
    while (m < j)
    {
      int i = getQuick(m);
      setQuick(m++, getQuick(k));
      setQuick(k--, i);
    }
  }
  
  public void set(int paramInt1, int paramInt2)
  {
    if ((paramInt1 >= this.size) || (paramInt1 < 0)) {
      throw new IndexOutOfBoundsException("Index: " + paramInt1 + ", Size: " + this.size);
    }
    setQuick(paramInt1, paramInt2);
  }
  
  protected abstract void setQuick(int paramInt1, int paramInt2);
  
  protected void setSizeRaw(int paramInt)
  {
    this.size = paramInt;
  }
  
  public void shuffleFromTo(int paramInt1, int paramInt2)
  {
    checkRangeFromTo(paramInt1, paramInt2, size());
    Uniform localUniform = new Uniform(new DRand(new Date()));
    for (int i = paramInt1; i < paramInt2; i++)
    {
      int j = localUniform.nextIntFromTo(i, paramInt2);
      int k = getQuick(j);
      setQuick(j, getQuick(i));
      setQuick(i, k);
    }
  }
  
  public int size()
  {
    return this.size;
  }
  
  public AbstractIntList times(int paramInt)
  {
    IntArrayList localIntArrayList = new IntArrayList(paramInt * size());
    int i = paramInt;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      localIntArrayList.addAllOfFromTo(this, 0, size() - 1);
    }
    return localIntArrayList;
  }
  
  public ArrayList toList()
  {
    int i = size();
    ArrayList localArrayList = new ArrayList(i);
    for (int j = 0; j < i; j++) {
      localArrayList.add(new Integer(get(j)));
    }
    return localArrayList;
  }
  
  public String toString()
  {
    return cern.colt.Arrays.toString(partFromTo(0, size() - 1).elements());
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.list.AbstractIntList
 * JD-Core Version:    0.7.0.1
 */