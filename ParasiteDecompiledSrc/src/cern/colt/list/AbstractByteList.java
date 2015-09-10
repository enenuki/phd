package cern.colt.list;

import cern.colt.Sorting;
import cern.colt.function.ByteComparator;
import cern.colt.function.ByteProcedure;
import cern.jet.random.Uniform;
import cern.jet.random.engine.DRand;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

public abstract class AbstractByteList
  extends AbstractList
{
  protected int size;
  
  public void add(byte paramByte)
  {
    beforeInsert(this.size, paramByte);
  }
  
  public void addAllOfFromTo(AbstractByteList paramAbstractByteList, int paramInt1, int paramInt2)
  {
    beforeInsertAllOfFromTo(this.size, paramAbstractByteList, paramInt1, paramInt2);
  }
  
  public void beforeInsert(int paramInt, byte paramByte)
  {
    beforeInsertDummies(paramInt, 1);
    set(paramInt, paramByte);
  }
  
  public void beforeInsertAllOfFromTo(int paramInt1, AbstractByteList paramAbstractByteList, int paramInt2, int paramInt3)
  {
    int i = paramInt3 - paramInt2 + 1;
    beforeInsertDummies(paramInt1, i);
    replaceFromToWithFrom(paramInt1, paramInt1 + i - 1, paramAbstractByteList, paramInt2);
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
  
  public int binarySearch(byte paramByte)
  {
    return binarySearchFromTo(paramByte, 0, this.size - 1);
  }
  
  public int binarySearchFromTo(byte paramByte, int paramInt1, int paramInt2)
  {
    int i = paramInt1;
    int j = paramInt2;
    while (i <= j)
    {
      int k = (i + j) / 2;
      byte b = get(k);
      if (b < paramByte) {
        i = k + 1;
      } else if (b > paramByte) {
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
  
  public boolean contains(byte paramByte)
  {
    return indexOfFromTo(paramByte, 0, this.size - 1) >= 0;
  }
  
  public void delete(byte paramByte)
  {
    int i = indexOfFromTo(paramByte, 0, this.size - 1);
    if (i >= 0) {
      remove(i);
    }
  }
  
  public byte[] elements()
  {
    byte[] arrayOfByte = new byte[this.size];
    int i = this.size;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      arrayOfByte[i] = getQuick(i);
    }
    return arrayOfByte;
  }
  
  public AbstractByteList elements(byte[] paramArrayOfByte)
  {
    clear();
    addAllOfFromTo(new ByteArrayList(paramArrayOfByte), 0, paramArrayOfByte.length - 1);
    return this;
  }
  
  public abstract void ensureCapacity(int paramInt);
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof AbstractByteList)) {
      return false;
    }
    if (this == paramObject) {
      return true;
    }
    if (paramObject == null) {
      return false;
    }
    AbstractByteList localAbstractByteList = (AbstractByteList)paramObject;
    if (size() != localAbstractByteList.size()) {
      return false;
    }
    int i = size();
    do
    {
      i--;
      if (i < 0) {
        break;
      }
    } while (getQuick(i) == localAbstractByteList.getQuick(i));
    return false;
    return true;
  }
  
  public void fillFromToWith(int paramInt1, int paramInt2, byte paramByte)
  {
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    int i = paramInt1;
    while (i <= paramInt2) {
      setQuick(i++, paramByte);
    }
  }
  
  public boolean forEach(ByteProcedure paramByteProcedure)
  {
    int i = 0;
    while (i < this.size) {
      if (!paramByteProcedure.apply(get(i++))) {
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
    return getQuick(paramInt);
  }
  
  protected abstract byte getQuick(int paramInt);
  
  public int indexOf(byte paramByte)
  {
    return indexOfFromTo(paramByte, 0, this.size - 1);
  }
  
  public int indexOfFromTo(byte paramByte, int paramInt1, int paramInt2)
  {
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    for (int i = paramInt1; i <= paramInt2; i++) {
      if (paramByte == getQuick(i)) {
        return i;
      }
    }
    return -1;
  }
  
  public int lastIndexOf(byte paramByte)
  {
    return lastIndexOfFromTo(paramByte, 0, this.size - 1);
  }
  
  public int lastIndexOfFromTo(byte paramByte, int paramInt1, int paramInt2)
  {
    checkRangeFromTo(paramInt1, paramInt2, size());
    for (int i = paramInt2; i >= paramInt1; i--) {
      if (paramByte == getQuick(i)) {
        return i;
      }
    }
    return -1;
  }
  
  public void mergeSortFromTo(int paramInt1, int paramInt2)
  {
    int i = size();
    checkRangeFromTo(paramInt1, paramInt2, i);
    byte[] arrayOfByte = elements();
    Sorting.mergeSort(arrayOfByte, paramInt1, paramInt2 + 1);
    elements(arrayOfByte);
    setSizeRaw(i);
  }
  
  public void mergeSortFromTo(int paramInt1, int paramInt2, ByteComparator paramByteComparator)
  {
    int i = size();
    checkRangeFromTo(paramInt1, paramInt2, i);
    byte[] arrayOfByte = elements();
    Sorting.mergeSort(arrayOfByte, paramInt1, paramInt2 + 1, paramByteComparator);
    elements(arrayOfByte);
    setSizeRaw(i);
  }
  
  public AbstractByteList partFromTo(int paramInt1, int paramInt2)
  {
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    int i = paramInt2 - paramInt1 + 1;
    ByteArrayList localByteArrayList = new ByteArrayList(i);
    localByteArrayList.addAllOfFromTo(this, paramInt1, paramInt2);
    return localByteArrayList;
  }
  
  public void quickSortFromTo(int paramInt1, int paramInt2)
  {
    int i = size();
    checkRangeFromTo(paramInt1, paramInt2, i);
    byte[] arrayOfByte = elements();
    java.util.Arrays.sort(arrayOfByte, paramInt1, paramInt2 + 1);
    elements(arrayOfByte);
    setSizeRaw(i);
  }
  
  public void quickSortFromTo(int paramInt1, int paramInt2, ByteComparator paramByteComparator)
  {
    int i = size();
    checkRangeFromTo(paramInt1, paramInt2, i);
    byte[] arrayOfByte = elements();
    Sorting.quickSort(arrayOfByte, paramInt1, paramInt2 + 1, paramByteComparator);
    elements(arrayOfByte);
    setSizeRaw(i);
  }
  
  public boolean removeAll(AbstractByteList paramAbstractByteList)
  {
    if (paramAbstractByteList.size() == 0) {
      return false;
    }
    int i = paramAbstractByteList.size() - 1;
    int j = 0;
    for (int k = 0; k < this.size; k++) {
      if (paramAbstractByteList.indexOfFromTo(getQuick(k), 0, i) < 0) {
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
  
  public void replaceFromToWithFrom(int paramInt1, int paramInt2, AbstractByteList paramAbstractByteList, int paramInt3)
  {
    int i = paramInt2 - paramInt1 + 1;
    if (i > 0)
    {
      checkRangeFromTo(paramInt1, paramInt2, size());
      checkRangeFromTo(paramInt3, paramInt3 + i - 1, paramAbstractByteList.size());
      if (paramInt1 <= paramInt3) {
        for (;;)
        {
          i--;
          if (i < 0) {
            break;
          }
          setQuick(paramInt1++, paramAbstractByteList.getQuick(paramInt3++));
        }
      }
      int j = paramInt3 + i - 1;
      for (;;)
      {
        i--;
        if (i < 0) {
          break;
        }
        setQuick(paramInt2--, paramAbstractByteList.getQuick(j--));
      }
    }
  }
  
  public void replaceFromToWithFromTo(int paramInt1, int paramInt2, AbstractByteList paramAbstractByteList, int paramInt3, int paramInt4)
  {
    if (paramInt3 > paramInt4) {
      throw new IndexOutOfBoundsException("otherFrom: " + paramInt3 + ", otherTo: " + paramInt4);
    }
    if ((this == paramAbstractByteList) && (paramInt2 - paramInt1 != paramInt4 - paramInt3))
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
      replaceFromToWithFrom(paramInt1, paramInt1 + i - 1, paramAbstractByteList, paramInt3);
    }
  }
  
  public void replaceFromWith(int paramInt, Collection paramCollection)
  {
    checkRange(paramInt, size());
    Iterator localIterator = paramCollection.iterator();
    int i = paramInt;
    int j = Math.min(size() - paramInt, paramCollection.size());
    for (int k = 0; k < j; k++) {
      set(i++, ((Number)localIterator.next()).byteValue());
    }
  }
  
  public boolean retainAll(AbstractByteList paramAbstractByteList)
  {
    if (paramAbstractByteList.size() == 0)
    {
      if (this.size == 0) {
        return false;
      }
      setSize(0);
      return true;
    }
    int i = paramAbstractByteList.size() - 1;
    int j = 0;
    for (int k = 0; k < this.size; k++) {
      if (paramAbstractByteList.indexOfFromTo(getQuick(k), 0, i) >= 0) {
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
      byte b = getQuick(k);
      setQuick(k++, getQuick(j));
      setQuick(j--, b);
    }
  }
  
  public void set(int paramInt, byte paramByte)
  {
    if ((paramInt >= this.size) || (paramInt < 0)) {
      throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + this.size);
    }
    setQuick(paramInt, paramByte);
  }
  
  protected abstract void setQuick(int paramInt, byte paramByte);
  
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
      byte b = getQuick(j);
      setQuick(j, getQuick(i));
      setQuick(i, b);
    }
  }
  
  public int size()
  {
    return this.size;
  }
  
  public AbstractByteList times(int paramInt)
  {
    ByteArrayList localByteArrayList = new ByteArrayList(paramInt * size());
    int i = paramInt;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      localByteArrayList.addAllOfFromTo(this, 0, size() - 1);
    }
    return localByteArrayList;
  }
  
  public ArrayList toList()
  {
    int i = size();
    ArrayList localArrayList = new ArrayList(i);
    for (int j = 0; j < i; j++) {
      localArrayList.add(new Byte(get(j)));
    }
    return localArrayList;
  }
  
  public String toString()
  {
    return cern.colt.Arrays.toString(partFromTo(0, size() - 1).elements());
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.list.AbstractByteList
 * JD-Core Version:    0.7.0.1
 */