package cern.colt.list;

import cern.colt.Sorting;
import cern.colt.buffer.DoubleBufferConsumer;
import cern.colt.function.DoubleComparator;
import cern.colt.function.DoubleProcedure;
import cern.jet.random.Uniform;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public abstract class AbstractDoubleList
  extends AbstractList
  implements DoubleBufferConsumer
{
  protected int size;
  
  public void add(double paramDouble)
  {
    beforeInsert(this.size, paramDouble);
  }
  
  public void addAllOf(DoubleArrayList paramDoubleArrayList)
  {
    addAllOfFromTo(paramDoubleArrayList, 0, paramDoubleArrayList.size() - 1);
  }
  
  public void addAllOfFromTo(AbstractDoubleList paramAbstractDoubleList, int paramInt1, int paramInt2)
  {
    beforeInsertAllOfFromTo(this.size, paramAbstractDoubleList, paramInt1, paramInt2);
  }
  
  public void beforeInsert(int paramInt, double paramDouble)
  {
    beforeInsertDummies(paramInt, 1);
    set(paramInt, paramDouble);
  }
  
  public void beforeInsertAllOfFromTo(int paramInt1, AbstractDoubleList paramAbstractDoubleList, int paramInt2, int paramInt3)
  {
    int i = paramInt3 - paramInt2 + 1;
    beforeInsertDummies(paramInt1, i);
    replaceFromToWithFrom(paramInt1, paramInt1 + i - 1, paramAbstractDoubleList, paramInt2);
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
  
  public int binarySearch(double paramDouble)
  {
    return binarySearchFromTo(paramDouble, 0, this.size - 1);
  }
  
  public int binarySearchFromTo(double paramDouble, int paramInt1, int paramInt2)
  {
    int i = paramInt1;
    int j = paramInt2;
    while (i <= j)
    {
      int k = (i + j) / 2;
      double d = get(k);
      if (d < paramDouble) {
        i = k + 1;
      } else if (d > paramDouble) {
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
  
  public boolean contains(double paramDouble)
  {
    return indexOfFromTo(paramDouble, 0, this.size - 1) >= 0;
  }
  
  public void delete(double paramDouble)
  {
    int i = indexOfFromTo(paramDouble, 0, this.size - 1);
    if (i >= 0) {
      remove(i);
    }
  }
  
  public double[] elements()
  {
    double[] arrayOfDouble = new double[this.size];
    int i = this.size;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      arrayOfDouble[i] = getQuick(i);
    }
    return arrayOfDouble;
  }
  
  public AbstractDoubleList elements(double[] paramArrayOfDouble)
  {
    clear();
    addAllOfFromTo(new DoubleArrayList(paramArrayOfDouble), 0, paramArrayOfDouble.length - 1);
    return this;
  }
  
  public abstract void ensureCapacity(int paramInt);
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof AbstractDoubleList)) {
      return false;
    }
    if (this == paramObject) {
      return true;
    }
    if (paramObject == null) {
      return false;
    }
    AbstractDoubleList localAbstractDoubleList = (AbstractDoubleList)paramObject;
    if (size() != localAbstractDoubleList.size()) {
      return false;
    }
    int i = size();
    do
    {
      i--;
      if (i < 0) {
        break;
      }
    } while (getQuick(i) == localAbstractDoubleList.getQuick(i));
    return false;
    return true;
  }
  
  public void fillFromToWith(int paramInt1, int paramInt2, double paramDouble)
  {
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    int i = paramInt1;
    while (i <= paramInt2) {
      setQuick(i++, paramDouble);
    }
  }
  
  public boolean forEach(DoubleProcedure paramDoubleProcedure)
  {
    int i = 0;
    while (i < this.size) {
      if (!paramDoubleProcedure.apply(get(i++))) {
        return false;
      }
    }
    return true;
  }
  
  public double get(int paramInt)
  {
    if ((paramInt >= this.size) || (paramInt < 0)) {
      throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + this.size);
    }
    return getQuick(paramInt);
  }
  
  protected abstract double getQuick(int paramInt);
  
  public int indexOf(double paramDouble)
  {
    return indexOfFromTo(paramDouble, 0, this.size - 1);
  }
  
  public int indexOfFromTo(double paramDouble, int paramInt1, int paramInt2)
  {
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    for (int i = paramInt1; i <= paramInt2; i++) {
      if (paramDouble == getQuick(i)) {
        return i;
      }
    }
    return -1;
  }
  
  public int lastIndexOf(double paramDouble)
  {
    return lastIndexOfFromTo(paramDouble, 0, this.size - 1);
  }
  
  public int lastIndexOfFromTo(double paramDouble, int paramInt1, int paramInt2)
  {
    checkRangeFromTo(paramInt1, paramInt2, size());
    for (int i = paramInt2; i >= paramInt1; i--) {
      if (paramDouble == getQuick(i)) {
        return i;
      }
    }
    return -1;
  }
  
  public void mergeSortFromTo(int paramInt1, int paramInt2)
  {
    int i = size();
    checkRangeFromTo(paramInt1, paramInt2, i);
    double[] arrayOfDouble = elements();
    Sorting.mergeSort(arrayOfDouble, paramInt1, paramInt2 + 1);
    elements(arrayOfDouble);
    setSizeRaw(i);
  }
  
  public void mergeSortFromTo(int paramInt1, int paramInt2, DoubleComparator paramDoubleComparator)
  {
    int i = size();
    checkRangeFromTo(paramInt1, paramInt2, i);
    double[] arrayOfDouble = elements();
    Sorting.mergeSort(arrayOfDouble, paramInt1, paramInt2 + 1, paramDoubleComparator);
    elements(arrayOfDouble);
    setSizeRaw(i);
  }
  
  public AbstractDoubleList partFromTo(int paramInt1, int paramInt2)
  {
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    int i = paramInt2 - paramInt1 + 1;
    DoubleArrayList localDoubleArrayList = new DoubleArrayList(i);
    localDoubleArrayList.addAllOfFromTo(this, paramInt1, paramInt2);
    return localDoubleArrayList;
  }
  
  public void quickSortFromTo(int paramInt1, int paramInt2)
  {
    int i = size();
    checkRangeFromTo(paramInt1, paramInt2, i);
    double[] arrayOfDouble = elements();
    java.util.Arrays.sort(arrayOfDouble, paramInt1, paramInt2 + 1);
    elements(arrayOfDouble);
    setSizeRaw(i);
  }
  
  public void quickSortFromTo(int paramInt1, int paramInt2, DoubleComparator paramDoubleComparator)
  {
    int i = size();
    checkRangeFromTo(paramInt1, paramInt2, i);
    double[] arrayOfDouble = elements();
    Sorting.quickSort(arrayOfDouble, paramInt1, paramInt2 + 1, paramDoubleComparator);
    elements(arrayOfDouble);
    setSizeRaw(i);
  }
  
  public boolean removeAll(AbstractDoubleList paramAbstractDoubleList)
  {
    if (paramAbstractDoubleList.size() == 0) {
      return false;
    }
    int i = paramAbstractDoubleList.size() - 1;
    int j = 0;
    for (int k = 0; k < this.size; k++) {
      if (paramAbstractDoubleList.indexOfFromTo(getQuick(k), 0, i) < 0) {
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
  
  public void replaceFromToWithFrom(int paramInt1, int paramInt2, AbstractDoubleList paramAbstractDoubleList, int paramInt3)
  {
    int i = paramInt2 - paramInt1 + 1;
    if (i > 0)
    {
      checkRangeFromTo(paramInt1, paramInt2, size());
      checkRangeFromTo(paramInt3, paramInt3 + i - 1, paramAbstractDoubleList.size());
      if (paramInt1 <= paramInt3) {
        for (;;)
        {
          i--;
          if (i < 0) {
            break;
          }
          setQuick(paramInt1++, paramAbstractDoubleList.getQuick(paramInt3++));
        }
      }
      int j = paramInt3 + i - 1;
      for (;;)
      {
        i--;
        if (i < 0) {
          break;
        }
        setQuick(paramInt2--, paramAbstractDoubleList.getQuick(j--));
      }
    }
  }
  
  public void replaceFromToWithFromTo(int paramInt1, int paramInt2, AbstractDoubleList paramAbstractDoubleList, int paramInt3, int paramInt4)
  {
    if (paramInt3 > paramInt4) {
      throw new IndexOutOfBoundsException("otherFrom: " + paramInt3 + ", otherTo: " + paramInt4);
    }
    if ((this == paramAbstractDoubleList) && (paramInt2 - paramInt1 != paramInt4 - paramInt3))
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
      replaceFromToWithFrom(paramInt1, paramInt1 + i - 1, paramAbstractDoubleList, paramInt3);
    }
  }
  
  public void replaceFromWith(int paramInt, Collection paramCollection)
  {
    checkRange(paramInt, size());
    Iterator localIterator = paramCollection.iterator();
    int i = paramInt;
    int j = Math.min(size() - paramInt, paramCollection.size());
    for (int k = 0; k < j; k++) {
      set(i++, ((Number)localIterator.next()).doubleValue());
    }
  }
  
  public boolean retainAll(AbstractDoubleList paramAbstractDoubleList)
  {
    if (paramAbstractDoubleList.size() == 0)
    {
      if (this.size == 0) {
        return false;
      }
      setSize(0);
      return true;
    }
    int i = paramAbstractDoubleList.size() - 1;
    int j = 0;
    for (int k = 0; k < this.size; k++) {
      if (paramAbstractDoubleList.indexOfFromTo(getQuick(k), 0, i) >= 0) {
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
      double d = getQuick(k);
      setQuick(k++, getQuick(j));
      setQuick(j--, d);
    }
  }
  
  public void set(int paramInt, double paramDouble)
  {
    if ((paramInt >= this.size) || (paramInt < 0)) {
      throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + this.size);
    }
    setQuick(paramInt, paramDouble);
  }
  
  protected abstract void setQuick(int paramInt, double paramDouble);
  
  protected void setSizeRaw(int paramInt)
  {
    this.size = paramInt;
  }
  
  public void shuffleFromTo(int paramInt1, int paramInt2)
  {
    checkRangeFromTo(paramInt1, paramInt2, size());
    Uniform localUniform = new Uniform(Uniform.makeDefaultGenerator());
    for (int i = paramInt1; i < paramInt2; i++)
    {
      int j = localUniform.nextIntFromTo(i, paramInt2);
      double d = getQuick(j);
      setQuick(j, getQuick(i));
      setQuick(i, d);
    }
  }
  
  public int size()
  {
    return this.size;
  }
  
  public AbstractDoubleList times(int paramInt)
  {
    DoubleArrayList localDoubleArrayList = new DoubleArrayList(paramInt * size());
    int i = paramInt;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      localDoubleArrayList.addAllOfFromTo(this, 0, size() - 1);
    }
    return localDoubleArrayList;
  }
  
  public ArrayList toList()
  {
    int i = size();
    ArrayList localArrayList = new ArrayList(i);
    for (int j = 0; j < i; j++) {
      localArrayList.add(new Double(get(j)));
    }
    return localArrayList;
  }
  
  public String toString()
  {
    return cern.colt.Arrays.toString(partFromTo(0, size() - 1).elements());
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.list.AbstractDoubleList
 * JD-Core Version:    0.7.0.1
 */