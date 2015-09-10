package cern.colt.list;

import cern.colt.Sorting;
import cern.colt.function.FloatComparator;
import cern.colt.function.FloatProcedure;
import cern.jet.random.Uniform;
import cern.jet.random.engine.DRand;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

public abstract class AbstractFloatList
  extends AbstractList
{
  protected int size;
  
  public void add(float paramFloat)
  {
    beforeInsert(this.size, paramFloat);
  }
  
  public void addAllOfFromTo(AbstractFloatList paramAbstractFloatList, int paramInt1, int paramInt2)
  {
    beforeInsertAllOfFromTo(this.size, paramAbstractFloatList, paramInt1, paramInt2);
  }
  
  public void beforeInsert(int paramInt, float paramFloat)
  {
    beforeInsertDummies(paramInt, 1);
    set(paramInt, paramFloat);
  }
  
  public void beforeInsertAllOfFromTo(int paramInt1, AbstractFloatList paramAbstractFloatList, int paramInt2, int paramInt3)
  {
    int i = paramInt3 - paramInt2 + 1;
    beforeInsertDummies(paramInt1, i);
    replaceFromToWithFrom(paramInt1, paramInt1 + i - 1, paramAbstractFloatList, paramInt2);
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
  
  public int binarySearch(float paramFloat)
  {
    return binarySearchFromTo(paramFloat, 0, this.size - 1);
  }
  
  public int binarySearchFromTo(float paramFloat, int paramInt1, int paramInt2)
  {
    int i = paramInt1;
    int j = paramInt2;
    while (i <= j)
    {
      int k = (i + j) / 2;
      float f = get(k);
      if (f < paramFloat) {
        i = k + 1;
      } else if (f > paramFloat) {
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
  
  public boolean contains(float paramFloat)
  {
    return indexOfFromTo(paramFloat, 0, this.size - 1) >= 0;
  }
  
  public void delete(float paramFloat)
  {
    int i = indexOfFromTo(paramFloat, 0, this.size - 1);
    if (i >= 0) {
      remove(i);
    }
  }
  
  public float[] elements()
  {
    float[] arrayOfFloat = new float[this.size];
    int i = this.size;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      arrayOfFloat[i] = getQuick(i);
    }
    return arrayOfFloat;
  }
  
  public AbstractFloatList elements(float[] paramArrayOfFloat)
  {
    clear();
    addAllOfFromTo(new FloatArrayList(paramArrayOfFloat), 0, paramArrayOfFloat.length - 1);
    return this;
  }
  
  public abstract void ensureCapacity(int paramInt);
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof AbstractFloatList)) {
      return false;
    }
    if (this == paramObject) {
      return true;
    }
    if (paramObject == null) {
      return false;
    }
    AbstractFloatList localAbstractFloatList = (AbstractFloatList)paramObject;
    if (size() != localAbstractFloatList.size()) {
      return false;
    }
    int i = size();
    do
    {
      i--;
      if (i < 0) {
        break;
      }
    } while (getQuick(i) == localAbstractFloatList.getQuick(i));
    return false;
    return true;
  }
  
  public void fillFromToWith(int paramInt1, int paramInt2, float paramFloat)
  {
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    int i = paramInt1;
    while (i <= paramInt2) {
      setQuick(i++, paramFloat);
    }
  }
  
  public boolean forEach(FloatProcedure paramFloatProcedure)
  {
    int i = 0;
    while (i < this.size) {
      if (!paramFloatProcedure.apply(get(i++))) {
        return false;
      }
    }
    return true;
  }
  
  public float get(int paramInt)
  {
    if ((paramInt >= this.size) || (paramInt < 0)) {
      throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + this.size);
    }
    return getQuick(paramInt);
  }
  
  protected abstract float getQuick(int paramInt);
  
  public int indexOf(float paramFloat)
  {
    return indexOfFromTo(paramFloat, 0, this.size - 1);
  }
  
  public int indexOfFromTo(float paramFloat, int paramInt1, int paramInt2)
  {
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    for (int i = paramInt1; i <= paramInt2; i++) {
      if (paramFloat == getQuick(i)) {
        return i;
      }
    }
    return -1;
  }
  
  public int lastIndexOf(float paramFloat)
  {
    return lastIndexOfFromTo(paramFloat, 0, this.size - 1);
  }
  
  public int lastIndexOfFromTo(float paramFloat, int paramInt1, int paramInt2)
  {
    checkRangeFromTo(paramInt1, paramInt2, size());
    for (int i = paramInt2; i >= paramInt1; i--) {
      if (paramFloat == getQuick(i)) {
        return i;
      }
    }
    return -1;
  }
  
  public void mergeSortFromTo(int paramInt1, int paramInt2)
  {
    int i = size();
    checkRangeFromTo(paramInt1, paramInt2, i);
    float[] arrayOfFloat = elements();
    Sorting.mergeSort(arrayOfFloat, paramInt1, paramInt2 + 1);
    elements(arrayOfFloat);
    setSizeRaw(i);
  }
  
  public void mergeSortFromTo(int paramInt1, int paramInt2, FloatComparator paramFloatComparator)
  {
    int i = size();
    checkRangeFromTo(paramInt1, paramInt2, i);
    float[] arrayOfFloat = elements();
    Sorting.mergeSort(arrayOfFloat, paramInt1, paramInt2 + 1, paramFloatComparator);
    elements(arrayOfFloat);
    setSizeRaw(i);
  }
  
  public AbstractFloatList partFromTo(int paramInt1, int paramInt2)
  {
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    int i = paramInt2 - paramInt1 + 1;
    FloatArrayList localFloatArrayList = new FloatArrayList(i);
    localFloatArrayList.addAllOfFromTo(this, paramInt1, paramInt2);
    return localFloatArrayList;
  }
  
  public void quickSortFromTo(int paramInt1, int paramInt2)
  {
    int i = size();
    checkRangeFromTo(paramInt1, paramInt2, i);
    float[] arrayOfFloat = elements();
    java.util.Arrays.sort(arrayOfFloat, paramInt1, paramInt2 + 1);
    elements(arrayOfFloat);
    setSizeRaw(i);
  }
  
  public void quickSortFromTo(int paramInt1, int paramInt2, FloatComparator paramFloatComparator)
  {
    int i = size();
    checkRangeFromTo(paramInt1, paramInt2, i);
    float[] arrayOfFloat = elements();
    Sorting.quickSort(arrayOfFloat, paramInt1, paramInt2 + 1, paramFloatComparator);
    elements(arrayOfFloat);
    setSizeRaw(i);
  }
  
  public boolean removeAll(AbstractFloatList paramAbstractFloatList)
  {
    if (paramAbstractFloatList.size() == 0) {
      return false;
    }
    int i = paramAbstractFloatList.size() - 1;
    int j = 0;
    for (int k = 0; k < this.size; k++) {
      if (paramAbstractFloatList.indexOfFromTo(getQuick(k), 0, i) < 0) {
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
  
  public void replaceFromToWithFrom(int paramInt1, int paramInt2, AbstractFloatList paramAbstractFloatList, int paramInt3)
  {
    int i = paramInt2 - paramInt1 + 1;
    if (i > 0)
    {
      checkRangeFromTo(paramInt1, paramInt2, size());
      checkRangeFromTo(paramInt3, paramInt3 + i - 1, paramAbstractFloatList.size());
      if (paramInt1 <= paramInt3) {
        for (;;)
        {
          i--;
          if (i < 0) {
            break;
          }
          setQuick(paramInt1++, paramAbstractFloatList.getQuick(paramInt3++));
        }
      }
      int j = paramInt3 + i - 1;
      for (;;)
      {
        i--;
        if (i < 0) {
          break;
        }
        setQuick(paramInt2--, paramAbstractFloatList.getQuick(j--));
      }
    }
  }
  
  public void replaceFromToWithFromTo(int paramInt1, int paramInt2, AbstractFloatList paramAbstractFloatList, int paramInt3, int paramInt4)
  {
    if (paramInt3 > paramInt4) {
      throw new IndexOutOfBoundsException("otherFrom: " + paramInt3 + ", otherTo: " + paramInt4);
    }
    if ((this == paramAbstractFloatList) && (paramInt2 - paramInt1 != paramInt4 - paramInt3))
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
      replaceFromToWithFrom(paramInt1, paramInt1 + i - 1, paramAbstractFloatList, paramInt3);
    }
  }
  
  public void replaceFromWith(int paramInt, Collection paramCollection)
  {
    checkRange(paramInt, size());
    Iterator localIterator = paramCollection.iterator();
    int i = paramInt;
    int j = Math.min(size() - paramInt, paramCollection.size());
    for (int k = 0; k < j; k++) {
      set(i++, ((Number)localIterator.next()).floatValue());
    }
  }
  
  public boolean retainAll(AbstractFloatList paramAbstractFloatList)
  {
    if (paramAbstractFloatList.size() == 0)
    {
      if (this.size == 0) {
        return false;
      }
      setSize(0);
      return true;
    }
    int i = paramAbstractFloatList.size() - 1;
    int j = 0;
    for (int k = 0; k < this.size; k++) {
      if (paramAbstractFloatList.indexOfFromTo(getQuick(k), 0, i) >= 0) {
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
      float f = getQuick(k);
      setQuick(k++, getQuick(j));
      setQuick(j--, f);
    }
  }
  
  public void set(int paramInt, float paramFloat)
  {
    if ((paramInt >= this.size) || (paramInt < 0)) {
      throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + this.size);
    }
    setQuick(paramInt, paramFloat);
  }
  
  protected abstract void setQuick(int paramInt, float paramFloat);
  
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
      float f = getQuick(j);
      setQuick(j, getQuick(i));
      setQuick(i, f);
    }
  }
  
  public int size()
  {
    return this.size;
  }
  
  public AbstractFloatList times(int paramInt)
  {
    FloatArrayList localFloatArrayList = new FloatArrayList(paramInt * size());
    int i = paramInt;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      localFloatArrayList.addAllOfFromTo(this, 0, size() - 1);
    }
    return localFloatArrayList;
  }
  
  public ArrayList toList()
  {
    int i = size();
    ArrayList localArrayList = new ArrayList(i);
    for (int j = 0; j < i; j++) {
      localArrayList.add(new Float(get(j)));
    }
    return localArrayList;
  }
  
  public String toString()
  {
    return cern.colt.Arrays.toString(partFromTo(0, size() - 1).elements());
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.list.AbstractFloatList
 * JD-Core Version:    0.7.0.1
 */