package cern.colt.list;

import cern.colt.Sorting;
import cern.colt.function.ShortComparator;
import cern.colt.function.ShortProcedure;
import cern.jet.random.Uniform;
import cern.jet.random.engine.DRand;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

public abstract class AbstractShortList
  extends AbstractList
{
  protected int size;
  
  public void add(short paramShort)
  {
    beforeInsert(this.size, paramShort);
  }
  
  public void addAllOfFromTo(AbstractShortList paramAbstractShortList, int paramInt1, int paramInt2)
  {
    beforeInsertAllOfFromTo(this.size, paramAbstractShortList, paramInt1, paramInt2);
  }
  
  public void beforeInsert(int paramInt, short paramShort)
  {
    beforeInsertDummies(paramInt, 1);
    set(paramInt, paramShort);
  }
  
  public void beforeInsertAllOfFromTo(int paramInt1, AbstractShortList paramAbstractShortList, int paramInt2, int paramInt3)
  {
    int i = paramInt3 - paramInt2 + 1;
    beforeInsertDummies(paramInt1, i);
    replaceFromToWithFrom(paramInt1, paramInt1 + i - 1, paramAbstractShortList, paramInt2);
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
  
  public int binarySearch(short paramShort)
  {
    return binarySearchFromTo(paramShort, 0, this.size - 1);
  }
  
  public int binarySearchFromTo(short paramShort, int paramInt1, int paramInt2)
  {
    int i = paramInt1;
    int j = paramInt2;
    while (i <= j)
    {
      int k = (i + j) / 2;
      short s = get(k);
      if (s < paramShort) {
        i = k + 1;
      } else if (s > paramShort) {
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
  
  public boolean contains(short paramShort)
  {
    return indexOfFromTo(paramShort, 0, this.size - 1) >= 0;
  }
  
  public void delete(short paramShort)
  {
    int i = indexOfFromTo(paramShort, 0, this.size - 1);
    if (i >= 0) {
      remove(i);
    }
  }
  
  public short[] elements()
  {
    short[] arrayOfShort = new short[this.size];
    int i = this.size;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      arrayOfShort[i] = getQuick(i);
    }
    return arrayOfShort;
  }
  
  public AbstractShortList elements(short[] paramArrayOfShort)
  {
    clear();
    addAllOfFromTo(new ShortArrayList(paramArrayOfShort), 0, paramArrayOfShort.length - 1);
    return this;
  }
  
  public abstract void ensureCapacity(int paramInt);
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof AbstractShortList)) {
      return false;
    }
    if (this == paramObject) {
      return true;
    }
    if (paramObject == null) {
      return false;
    }
    AbstractShortList localAbstractShortList = (AbstractShortList)paramObject;
    if (size() != localAbstractShortList.size()) {
      return false;
    }
    int i = size();
    do
    {
      i--;
      if (i < 0) {
        break;
      }
    } while (getQuick(i) == localAbstractShortList.getQuick(i));
    return false;
    return true;
  }
  
  public void fillFromToWith(int paramInt1, int paramInt2, short paramShort)
  {
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    int i = paramInt1;
    while (i <= paramInt2) {
      setQuick(i++, paramShort);
    }
  }
  
  public boolean forEach(ShortProcedure paramShortProcedure)
  {
    int i = 0;
    while (i < this.size) {
      if (!paramShortProcedure.apply(get(i++))) {
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
    return getQuick(paramInt);
  }
  
  protected abstract short getQuick(int paramInt);
  
  public int indexOf(short paramShort)
  {
    return indexOfFromTo(paramShort, 0, this.size - 1);
  }
  
  public int indexOfFromTo(short paramShort, int paramInt1, int paramInt2)
  {
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    for (int i = paramInt1; i <= paramInt2; i++) {
      if (paramShort == getQuick(i)) {
        return i;
      }
    }
    return -1;
  }
  
  public int lastIndexOf(short paramShort)
  {
    return lastIndexOfFromTo(paramShort, 0, this.size - 1);
  }
  
  public int lastIndexOfFromTo(short paramShort, int paramInt1, int paramInt2)
  {
    checkRangeFromTo(paramInt1, paramInt2, size());
    for (int i = paramInt2; i >= paramInt1; i--) {
      if (paramShort == getQuick(i)) {
        return i;
      }
    }
    return -1;
  }
  
  public void mergeSortFromTo(int paramInt1, int paramInt2)
  {
    int i = size();
    checkRangeFromTo(paramInt1, paramInt2, i);
    short[] arrayOfShort = elements();
    Sorting.mergeSort(arrayOfShort, paramInt1, paramInt2 + 1);
    elements(arrayOfShort);
    setSizeRaw(i);
  }
  
  public void mergeSortFromTo(int paramInt1, int paramInt2, ShortComparator paramShortComparator)
  {
    int i = size();
    checkRangeFromTo(paramInt1, paramInt2, i);
    short[] arrayOfShort = elements();
    Sorting.mergeSort(arrayOfShort, paramInt1, paramInt2 + 1, paramShortComparator);
    elements(arrayOfShort);
    setSizeRaw(i);
  }
  
  public AbstractShortList partFromTo(int paramInt1, int paramInt2)
  {
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    int i = paramInt2 - paramInt1 + 1;
    ShortArrayList localShortArrayList = new ShortArrayList(i);
    localShortArrayList.addAllOfFromTo(this, paramInt1, paramInt2);
    return localShortArrayList;
  }
  
  public void quickSortFromTo(int paramInt1, int paramInt2)
  {
    int i = size();
    checkRangeFromTo(paramInt1, paramInt2, i);
    short[] arrayOfShort = elements();
    java.util.Arrays.sort(arrayOfShort, paramInt1, paramInt2 + 1);
    elements(arrayOfShort);
    setSizeRaw(i);
  }
  
  public void quickSortFromTo(int paramInt1, int paramInt2, ShortComparator paramShortComparator)
  {
    int i = size();
    checkRangeFromTo(paramInt1, paramInt2, i);
    short[] arrayOfShort = elements();
    Sorting.quickSort(arrayOfShort, paramInt1, paramInt2 + 1, paramShortComparator);
    elements(arrayOfShort);
    setSizeRaw(i);
  }
  
  public boolean removeAll(AbstractShortList paramAbstractShortList)
  {
    if (paramAbstractShortList.size() == 0) {
      return false;
    }
    int i = paramAbstractShortList.size() - 1;
    int j = 0;
    for (int k = 0; k < this.size; k++) {
      if (paramAbstractShortList.indexOfFromTo(getQuick(k), 0, i) < 0) {
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
  
  public void replaceFromToWithFrom(int paramInt1, int paramInt2, AbstractShortList paramAbstractShortList, int paramInt3)
  {
    int i = paramInt2 - paramInt1 + 1;
    if (i > 0)
    {
      checkRangeFromTo(paramInt1, paramInt2, size());
      checkRangeFromTo(paramInt3, paramInt3 + i - 1, paramAbstractShortList.size());
      if (paramInt1 <= paramInt3) {
        for (;;)
        {
          i--;
          if (i < 0) {
            break;
          }
          setQuick(paramInt1++, paramAbstractShortList.getQuick(paramInt3++));
        }
      }
      int j = paramInt3 + i - 1;
      for (;;)
      {
        i--;
        if (i < 0) {
          break;
        }
        setQuick(paramInt2--, paramAbstractShortList.getQuick(j--));
      }
    }
  }
  
  public void replaceFromToWithFromTo(int paramInt1, int paramInt2, AbstractShortList paramAbstractShortList, int paramInt3, int paramInt4)
  {
    if (paramInt3 > paramInt4) {
      throw new IndexOutOfBoundsException("otherFrom: " + paramInt3 + ", otherTo: " + paramInt4);
    }
    if ((this == paramAbstractShortList) && (paramInt2 - paramInt1 != paramInt4 - paramInt3))
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
      replaceFromToWithFrom(paramInt1, paramInt1 + i - 1, paramAbstractShortList, paramInt3);
    }
  }
  
  public void replaceFromWith(int paramInt, Collection paramCollection)
  {
    checkRange(paramInt, size());
    Iterator localIterator = paramCollection.iterator();
    int i = paramInt;
    int j = Math.min(size() - paramInt, paramCollection.size());
    for (int k = 0; k < j; k++) {
      set(i++, ((Number)localIterator.next()).shortValue());
    }
  }
  
  public boolean retainAll(AbstractShortList paramAbstractShortList)
  {
    if (paramAbstractShortList.size() == 0)
    {
      if (this.size == 0) {
        return false;
      }
      setSize(0);
      return true;
    }
    int i = paramAbstractShortList.size() - 1;
    int j = 0;
    for (int k = 0; k < this.size; k++) {
      if (paramAbstractShortList.indexOfFromTo(getQuick(k), 0, i) >= 0) {
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
      short s = getQuick(k);
      setQuick(k++, getQuick(j));
      setQuick(j--, s);
    }
  }
  
  public void set(int paramInt, short paramShort)
  {
    if ((paramInt >= this.size) || (paramInt < 0)) {
      throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + this.size);
    }
    setQuick(paramInt, paramShort);
  }
  
  protected abstract void setQuick(int paramInt, short paramShort);
  
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
      short s = getQuick(j);
      setQuick(j, getQuick(i));
      setQuick(i, s);
    }
  }
  
  public int size()
  {
    return this.size;
  }
  
  public AbstractShortList times(int paramInt)
  {
    ShortArrayList localShortArrayList = new ShortArrayList(paramInt * size());
    int i = paramInt;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      localShortArrayList.addAllOfFromTo(this, 0, size() - 1);
    }
    return localShortArrayList;
  }
  
  public ArrayList toList()
  {
    int i = size();
    ArrayList localArrayList = new ArrayList(i);
    for (int j = 0; j < i; j++) {
      localArrayList.add(new Short(get(j)));
    }
    return localArrayList;
  }
  
  public String toString()
  {
    return cern.colt.Arrays.toString(partFromTo(0, size() - 1).elements());
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.list.AbstractShortList
 * JD-Core Version:    0.7.0.1
 */