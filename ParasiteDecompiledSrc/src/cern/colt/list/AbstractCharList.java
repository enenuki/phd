package cern.colt.list;

import cern.colt.Sorting;
import cern.colt.function.CharComparator;
import cern.colt.function.CharProcedure;
import cern.jet.random.Uniform;
import cern.jet.random.engine.DRand;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

public abstract class AbstractCharList
  extends AbstractList
{
  protected int size;
  
  public void add(char paramChar)
  {
    beforeInsert(this.size, paramChar);
  }
  
  public void addAllOfFromTo(AbstractCharList paramAbstractCharList, int paramInt1, int paramInt2)
  {
    beforeInsertAllOfFromTo(this.size, paramAbstractCharList, paramInt1, paramInt2);
  }
  
  public void beforeInsert(int paramInt, char paramChar)
  {
    beforeInsertDummies(paramInt, 1);
    set(paramInt, paramChar);
  }
  
  public void beforeInsertAllOfFromTo(int paramInt1, AbstractCharList paramAbstractCharList, int paramInt2, int paramInt3)
  {
    int i = paramInt3 - paramInt2 + 1;
    beforeInsertDummies(paramInt1, i);
    replaceFromToWithFrom(paramInt1, paramInt1 + i - 1, paramAbstractCharList, paramInt2);
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
  
  public int binarySearch(char paramChar)
  {
    return binarySearchFromTo(paramChar, 0, this.size - 1);
  }
  
  public int binarySearchFromTo(char paramChar, int paramInt1, int paramInt2)
  {
    int i = paramInt1;
    int j = paramInt2;
    while (i <= j)
    {
      int k = (i + j) / 2;
      char c = get(k);
      if (c < paramChar) {
        i = k + 1;
      } else if (c > paramChar) {
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
  
  public boolean contains(char paramChar)
  {
    return indexOfFromTo(paramChar, 0, this.size - 1) >= 0;
  }
  
  public void delete(char paramChar)
  {
    int i = indexOfFromTo(paramChar, 0, this.size - 1);
    if (i >= 0) {
      remove(i);
    }
  }
  
  public char[] elements()
  {
    char[] arrayOfChar = new char[this.size];
    int i = this.size;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      arrayOfChar[i] = getQuick(i);
    }
    return arrayOfChar;
  }
  
  public AbstractCharList elements(char[] paramArrayOfChar)
  {
    clear();
    addAllOfFromTo(new CharArrayList(paramArrayOfChar), 0, paramArrayOfChar.length - 1);
    return this;
  }
  
  public abstract void ensureCapacity(int paramInt);
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof AbstractCharList)) {
      return false;
    }
    if (this == paramObject) {
      return true;
    }
    if (paramObject == null) {
      return false;
    }
    AbstractCharList localAbstractCharList = (AbstractCharList)paramObject;
    if (size() != localAbstractCharList.size()) {
      return false;
    }
    int i = size();
    do
    {
      i--;
      if (i < 0) {
        break;
      }
    } while (getQuick(i) == localAbstractCharList.getQuick(i));
    return false;
    return true;
  }
  
  public void fillFromToWith(int paramInt1, int paramInt2, char paramChar)
  {
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    int i = paramInt1;
    while (i <= paramInt2) {
      setQuick(i++, paramChar);
    }
  }
  
  public boolean forEach(CharProcedure paramCharProcedure)
  {
    int i = 0;
    while (i < this.size) {
      if (!paramCharProcedure.apply(get(i++))) {
        return false;
      }
    }
    return true;
  }
  
  public char get(int paramInt)
  {
    if ((paramInt >= this.size) || (paramInt < 0)) {
      throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + this.size);
    }
    return getQuick(paramInt);
  }
  
  protected abstract char getQuick(int paramInt);
  
  public int indexOf(char paramChar)
  {
    return indexOfFromTo(paramChar, 0, this.size - 1);
  }
  
  public int indexOfFromTo(char paramChar, int paramInt1, int paramInt2)
  {
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    for (int i = paramInt1; i <= paramInt2; i++) {
      if (paramChar == getQuick(i)) {
        return i;
      }
    }
    return -1;
  }
  
  public int lastIndexOf(char paramChar)
  {
    return lastIndexOfFromTo(paramChar, 0, this.size - 1);
  }
  
  public int lastIndexOfFromTo(char paramChar, int paramInt1, int paramInt2)
  {
    checkRangeFromTo(paramInt1, paramInt2, size());
    for (int i = paramInt2; i >= paramInt1; i--) {
      if (paramChar == getQuick(i)) {
        return i;
      }
    }
    return -1;
  }
  
  public void mergeSortFromTo(int paramInt1, int paramInt2)
  {
    int i = size();
    checkRangeFromTo(paramInt1, paramInt2, i);
    char[] arrayOfChar = elements();
    Sorting.mergeSort(arrayOfChar, paramInt1, paramInt2 + 1);
    elements(arrayOfChar);
    setSizeRaw(i);
  }
  
  public void mergeSortFromTo(int paramInt1, int paramInt2, CharComparator paramCharComparator)
  {
    int i = size();
    checkRangeFromTo(paramInt1, paramInt2, i);
    char[] arrayOfChar = elements();
    Sorting.mergeSort(arrayOfChar, paramInt1, paramInt2 + 1, paramCharComparator);
    elements(arrayOfChar);
    setSizeRaw(i);
  }
  
  public AbstractCharList partFromTo(int paramInt1, int paramInt2)
  {
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    int i = paramInt2 - paramInt1 + 1;
    CharArrayList localCharArrayList = new CharArrayList(i);
    localCharArrayList.addAllOfFromTo(this, paramInt1, paramInt2);
    return localCharArrayList;
  }
  
  public void quickSortFromTo(int paramInt1, int paramInt2)
  {
    int i = size();
    checkRangeFromTo(paramInt1, paramInt2, i);
    char[] arrayOfChar = elements();
    java.util.Arrays.sort(arrayOfChar, paramInt1, paramInt2 + 1);
    elements(arrayOfChar);
    setSizeRaw(i);
  }
  
  public void quickSortFromTo(int paramInt1, int paramInt2, CharComparator paramCharComparator)
  {
    int i = size();
    checkRangeFromTo(paramInt1, paramInt2, i);
    char[] arrayOfChar = elements();
    Sorting.quickSort(arrayOfChar, paramInt1, paramInt2 + 1, paramCharComparator);
    elements(arrayOfChar);
    setSizeRaw(i);
  }
  
  public boolean removeAll(AbstractCharList paramAbstractCharList)
  {
    if (paramAbstractCharList.size() == 0) {
      return false;
    }
    int i = paramAbstractCharList.size() - 1;
    int j = 0;
    for (int k = 0; k < this.size; k++) {
      if (paramAbstractCharList.indexOfFromTo(getQuick(k), 0, i) < 0) {
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
  
  public void replaceFromToWithFrom(int paramInt1, int paramInt2, AbstractCharList paramAbstractCharList, int paramInt3)
  {
    int i = paramInt2 - paramInt1 + 1;
    if (i > 0)
    {
      checkRangeFromTo(paramInt1, paramInt2, size());
      checkRangeFromTo(paramInt3, paramInt3 + i - 1, paramAbstractCharList.size());
      if (paramInt1 <= paramInt3) {
        for (;;)
        {
          i--;
          if (i < 0) {
            break;
          }
          setQuick(paramInt1++, paramAbstractCharList.getQuick(paramInt3++));
        }
      }
      int j = paramInt3 + i - 1;
      for (;;)
      {
        i--;
        if (i < 0) {
          break;
        }
        setQuick(paramInt2--, paramAbstractCharList.getQuick(j--));
      }
    }
  }
  
  public void replaceFromToWithFromTo(int paramInt1, int paramInt2, AbstractCharList paramAbstractCharList, int paramInt3, int paramInt4)
  {
    if (paramInt3 > paramInt4) {
      throw new IndexOutOfBoundsException("otherFrom: " + paramInt3 + ", otherTo: " + paramInt4);
    }
    if ((this == paramAbstractCharList) && (paramInt2 - paramInt1 != paramInt4 - paramInt3))
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
      replaceFromToWithFrom(paramInt1, paramInt1 + i - 1, paramAbstractCharList, paramInt3);
    }
  }
  
  public void replaceFromWith(int paramInt, Collection paramCollection)
  {
    checkRange(paramInt, size());
    Iterator localIterator = paramCollection.iterator();
    int i = paramInt;
    int j = Math.min(size() - paramInt, paramCollection.size());
    for (int k = 0; k < j; k++) {
      set(i++, ((Character)localIterator.next()).charValue());
    }
  }
  
  public boolean retainAll(AbstractCharList paramAbstractCharList)
  {
    if (paramAbstractCharList.size() == 0)
    {
      if (this.size == 0) {
        return false;
      }
      setSize(0);
      return true;
    }
    int i = paramAbstractCharList.size() - 1;
    int j = 0;
    for (int k = 0; k < this.size; k++) {
      if (paramAbstractCharList.indexOfFromTo(getQuick(k), 0, i) >= 0) {
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
      char c = getQuick(k);
      setQuick(k++, getQuick(j));
      setQuick(j--, c);
    }
  }
  
  public void set(int paramInt, char paramChar)
  {
    if ((paramInt >= this.size) || (paramInt < 0)) {
      throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + this.size);
    }
    setQuick(paramInt, paramChar);
  }
  
  protected abstract void setQuick(int paramInt, char paramChar);
  
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
      char c = getQuick(j);
      setQuick(j, getQuick(i));
      setQuick(i, c);
    }
  }
  
  public int size()
  {
    return this.size;
  }
  
  public AbstractCharList times(int paramInt)
  {
    CharArrayList localCharArrayList = new CharArrayList(paramInt * size());
    int i = paramInt;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      localCharArrayList.addAllOfFromTo(this, 0, size() - 1);
    }
    return localCharArrayList;
  }
  
  public ArrayList toList()
  {
    int i = size();
    ArrayList localArrayList = new ArrayList(i);
    for (int j = 0; j < i; j++) {
      localArrayList.add(new Character(get(j)));
    }
    return localArrayList;
  }
  
  public String toString()
  {
    return cern.colt.Arrays.toString(partFromTo(0, size() - 1).elements());
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.list.AbstractCharList
 * JD-Core Version:    0.7.0.1
 */