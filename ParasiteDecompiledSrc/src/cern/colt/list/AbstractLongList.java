package cern.colt.list;

import cern.colt.Sorting;
import cern.colt.function.LongComparator;
import cern.colt.function.LongProcedure;
import cern.jet.random.Uniform;
import cern.jet.random.engine.DRand;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

public abstract class AbstractLongList
  extends AbstractList
{
  protected int size;
  
  public void add(long paramLong)
  {
    beforeInsert(this.size, paramLong);
  }
  
  public void addAllOfFromTo(AbstractLongList paramAbstractLongList, int paramInt1, int paramInt2)
  {
    beforeInsertAllOfFromTo(this.size, paramAbstractLongList, paramInt1, paramInt2);
  }
  
  public void beforeInsert(int paramInt, long paramLong)
  {
    beforeInsertDummies(paramInt, 1);
    set(paramInt, paramLong);
  }
  
  public void beforeInsertAllOfFromTo(int paramInt1, AbstractLongList paramAbstractLongList, int paramInt2, int paramInt3)
  {
    int i = paramInt3 - paramInt2 + 1;
    beforeInsertDummies(paramInt1, i);
    replaceFromToWithFrom(paramInt1, paramInt1 + i - 1, paramAbstractLongList, paramInt2);
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
  
  public int binarySearch(long paramLong)
  {
    return binarySearchFromTo(paramLong, 0, this.size - 1);
  }
  
  public int binarySearchFromTo(long paramLong, int paramInt1, int paramInt2)
  {
    int i = paramInt1;
    int j = paramInt2;
    while (i <= j)
    {
      int k = (i + j) / 2;
      long l = get(k);
      if (l < paramLong) {
        i = k + 1;
      } else if (l > paramLong) {
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
  
  public boolean contains(long paramLong)
  {
    return indexOfFromTo(paramLong, 0, this.size - 1) >= 0;
  }
  
  public void delete(long paramLong)
  {
    int i = indexOfFromTo(paramLong, 0, this.size - 1);
    if (i >= 0) {
      remove(i);
    }
  }
  
  public long[] elements()
  {
    long[] arrayOfLong = new long[this.size];
    int i = this.size;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      arrayOfLong[i] = getQuick(i);
    }
    return arrayOfLong;
  }
  
  public AbstractLongList elements(long[] paramArrayOfLong)
  {
    clear();
    addAllOfFromTo(new LongArrayList(paramArrayOfLong), 0, paramArrayOfLong.length - 1);
    return this;
  }
  
  public abstract void ensureCapacity(int paramInt);
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof AbstractLongList)) {
      return false;
    }
    if (this == paramObject) {
      return true;
    }
    if (paramObject == null) {
      return false;
    }
    AbstractLongList localAbstractLongList = (AbstractLongList)paramObject;
    if (size() != localAbstractLongList.size()) {
      return false;
    }
    int i = size();
    do
    {
      i--;
      if (i < 0) {
        break;
      }
    } while (getQuick(i) == localAbstractLongList.getQuick(i));
    return false;
    return true;
  }
  
  public void fillFromToWith(int paramInt1, int paramInt2, long paramLong)
  {
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    int i = paramInt1;
    while (i <= paramInt2) {
      setQuick(i++, paramLong);
    }
  }
  
  public boolean forEach(LongProcedure paramLongProcedure)
  {
    int i = 0;
    while (i < this.size) {
      if (!paramLongProcedure.apply(get(i++))) {
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
    return getQuick(paramInt);
  }
  
  protected abstract long getQuick(int paramInt);
  
  public int indexOf(long paramLong)
  {
    return indexOfFromTo(paramLong, 0, this.size - 1);
  }
  
  public int indexOfFromTo(long paramLong, int paramInt1, int paramInt2)
  {
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    for (int i = paramInt1; i <= paramInt2; i++) {
      if (paramLong == getQuick(i)) {
        return i;
      }
    }
    return -1;
  }
  
  public int lastIndexOf(long paramLong)
  {
    return lastIndexOfFromTo(paramLong, 0, this.size - 1);
  }
  
  public int lastIndexOfFromTo(long paramLong, int paramInt1, int paramInt2)
  {
    checkRangeFromTo(paramInt1, paramInt2, size());
    for (int i = paramInt2; i >= paramInt1; i--) {
      if (paramLong == getQuick(i)) {
        return i;
      }
    }
    return -1;
  }
  
  public void mergeSortFromTo(int paramInt1, int paramInt2)
  {
    int i = size();
    checkRangeFromTo(paramInt1, paramInt2, i);
    long[] arrayOfLong = elements();
    Sorting.mergeSort(arrayOfLong, paramInt1, paramInt2 + 1);
    elements(arrayOfLong);
    setSizeRaw(i);
  }
  
  public void mergeSortFromTo(int paramInt1, int paramInt2, LongComparator paramLongComparator)
  {
    int i = size();
    checkRangeFromTo(paramInt1, paramInt2, i);
    long[] arrayOfLong = elements();
    Sorting.mergeSort(arrayOfLong, paramInt1, paramInt2 + 1, paramLongComparator);
    elements(arrayOfLong);
    setSizeRaw(i);
  }
  
  public AbstractLongList partFromTo(int paramInt1, int paramInt2)
  {
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    int i = paramInt2 - paramInt1 + 1;
    LongArrayList localLongArrayList = new LongArrayList(i);
    localLongArrayList.addAllOfFromTo(this, paramInt1, paramInt2);
    return localLongArrayList;
  }
  
  public void quickSortFromTo(int paramInt1, int paramInt2)
  {
    int i = size();
    checkRangeFromTo(paramInt1, paramInt2, i);
    long[] arrayOfLong = elements();
    java.util.Arrays.sort(arrayOfLong, paramInt1, paramInt2 + 1);
    elements(arrayOfLong);
    setSizeRaw(i);
  }
  
  public void quickSortFromTo(int paramInt1, int paramInt2, LongComparator paramLongComparator)
  {
    int i = size();
    checkRangeFromTo(paramInt1, paramInt2, i);
    long[] arrayOfLong = elements();
    Sorting.quickSort(arrayOfLong, paramInt1, paramInt2 + 1, paramLongComparator);
    elements(arrayOfLong);
    setSizeRaw(i);
  }
  
  public boolean removeAll(AbstractLongList paramAbstractLongList)
  {
    if (paramAbstractLongList.size() == 0) {
      return false;
    }
    int i = paramAbstractLongList.size() - 1;
    int j = 0;
    for (int k = 0; k < this.size; k++) {
      if (paramAbstractLongList.indexOfFromTo(getQuick(k), 0, i) < 0) {
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
  
  public void replaceFromToWithFrom(int paramInt1, int paramInt2, AbstractLongList paramAbstractLongList, int paramInt3)
  {
    int i = paramInt2 - paramInt1 + 1;
    if (i > 0)
    {
      checkRangeFromTo(paramInt1, paramInt2, size());
      checkRangeFromTo(paramInt3, paramInt3 + i - 1, paramAbstractLongList.size());
      if (paramInt1 <= paramInt3) {
        for (;;)
        {
          i--;
          if (i < 0) {
            break;
          }
          setQuick(paramInt1++, paramAbstractLongList.getQuick(paramInt3++));
        }
      }
      int j = paramInt3 + i - 1;
      for (;;)
      {
        i--;
        if (i < 0) {
          break;
        }
        setQuick(paramInt2--, paramAbstractLongList.getQuick(j--));
      }
    }
  }
  
  public void replaceFromToWithFromTo(int paramInt1, int paramInt2, AbstractLongList paramAbstractLongList, int paramInt3, int paramInt4)
  {
    if (paramInt3 > paramInt4) {
      throw new IndexOutOfBoundsException("otherFrom: " + paramInt3 + ", otherTo: " + paramInt4);
    }
    if ((this == paramAbstractLongList) && (paramInt2 - paramInt1 != paramInt4 - paramInt3))
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
      replaceFromToWithFrom(paramInt1, paramInt1 + i - 1, paramAbstractLongList, paramInt3);
    }
  }
  
  public void replaceFromWith(int paramInt, Collection paramCollection)
  {
    checkRange(paramInt, size());
    Iterator localIterator = paramCollection.iterator();
    int i = paramInt;
    int j = Math.min(size() - paramInt, paramCollection.size());
    for (int k = 0; k < j; k++) {
      set(i++, ((Number)localIterator.next()).longValue());
    }
  }
  
  public boolean retainAll(AbstractLongList paramAbstractLongList)
  {
    if (paramAbstractLongList.size() == 0)
    {
      if (this.size == 0) {
        return false;
      }
      setSize(0);
      return true;
    }
    int i = paramAbstractLongList.size() - 1;
    int j = 0;
    for (int k = 0; k < this.size; k++) {
      if (paramAbstractLongList.indexOfFromTo(getQuick(k), 0, i) >= 0) {
        setQuick(j++, getQuick(k));
      }
    }
    k = j != this.size ? 1 : 0;
    setSize(j);
    return k;
  }
  
  public void reverse()
  {
    int i = size() / 2;
    int j = size() - 1;
    int k = 0;
    while (k < i)
    {
      long l = getQuick(k);
      setQuick(k++, getQuick(j));
      setQuick(j--, l);
    }
  }
  
  public void set(int paramInt, long paramLong)
  {
    if ((paramInt >= this.size) || (paramInt < 0)) {
      throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + this.size);
    }
    setQuick(paramInt, paramLong);
  }
  
  protected abstract void setQuick(int paramInt, long paramLong);
  
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
      long l = getQuick(j);
      setQuick(j, getQuick(i));
      setQuick(i, l);
    }
  }
  
  public int size()
  {
    return this.size;
  }
  
  public AbstractLongList times(int paramInt)
  {
    LongArrayList localLongArrayList = new LongArrayList(paramInt * size());
    int i = paramInt;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      localLongArrayList.addAllOfFromTo(this, 0, size() - 1);
    }
    return localLongArrayList;
  }
  
  public ArrayList toList()
  {
    int i = size();
    ArrayList localArrayList = new ArrayList(i);
    for (int j = 0; j < i; j++) {
      localArrayList.add(new Long(get(j)));
    }
    return localArrayList;
  }
  
  public String toString()
  {
    return cern.colt.Arrays.toString(partFromTo(0, size() - 1).elements());
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.list.AbstractLongList
 * JD-Core Version:    0.7.0.1
 */