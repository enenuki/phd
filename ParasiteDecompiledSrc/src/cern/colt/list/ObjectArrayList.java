package cern.colt.list;

import cern.colt.Sorting;
import cern.colt.function.ObjectProcedure;
import cern.jet.random.Uniform;
import cern.jet.random.engine.DRand;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;

public class ObjectArrayList
  extends AbstractList
{
  protected Object[] elements;
  protected int size;
  
  public ObjectArrayList()
  {
    this(10);
  }
  
  public ObjectArrayList(Object[] paramArrayOfObject)
  {
    elements(paramArrayOfObject);
  }
  
  public ObjectArrayList(int paramInt)
  {
    this(new Object[paramInt]);
    this.size = 0;
  }
  
  public void add(Object paramObject)
  {
    if (this.size == this.elements.length) {
      ensureCapacity(this.size + 1);
    }
    this.elements[(this.size++)] = paramObject;
  }
  
  public void addAllOfFromTo(ObjectArrayList paramObjectArrayList, int paramInt1, int paramInt2)
  {
    beforeInsertAllOfFromTo(this.size, paramObjectArrayList, paramInt1, paramInt2);
  }
  
  public void beforeInsert(int paramInt, Object paramObject)
  {
    if ((paramInt > this.size) || (paramInt < 0)) {
      throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + this.size);
    }
    ensureCapacity(this.size + 1);
    System.arraycopy(this.elements, paramInt, this.elements, paramInt + 1, this.size - paramInt);
    this.elements[paramInt] = paramObject;
    this.size += 1;
  }
  
  public void beforeInsertAllOfFromTo(int paramInt1, ObjectArrayList paramObjectArrayList, int paramInt2, int paramInt3)
  {
    int i = paramInt3 - paramInt2 + 1;
    beforeInsertDummies(paramInt1, i);
    replaceFromToWithFrom(paramInt1, paramInt1 + i - 1, paramObjectArrayList, paramInt2);
  }
  
  protected void beforeInsertDummies(int paramInt1, int paramInt2)
  {
    if ((paramInt1 > this.size) || (paramInt1 < 0)) {
      throw new IndexOutOfBoundsException("Index: " + paramInt1 + ", Size: " + this.size);
    }
    if (paramInt2 > 0)
    {
      ensureCapacity(this.size + paramInt2);
      System.arraycopy(this.elements, paramInt1, this.elements, paramInt1 + paramInt2, this.size - paramInt1);
      this.size += paramInt2;
    }
  }
  
  public int binarySearch(Object paramObject)
  {
    return binarySearchFromTo(paramObject, 0, this.size - 1);
  }
  
  public int binarySearchFromTo(Object paramObject, int paramInt1, int paramInt2)
  {
    int i = paramInt1;
    int j = paramInt2;
    while (i <= j)
    {
      int k = (i + j) / 2;
      Object localObject = this.elements[k];
      int m = ((Comparable)localObject).compareTo(paramObject);
      if (m < 0) {
        i = k + 1;
      } else if (m > 0) {
        j = k - 1;
      } else {
        return k;
      }
    }
    return -(i + 1);
  }
  
  public int binarySearchFromTo(Object paramObject, int paramInt1, int paramInt2, Comparator paramComparator)
  {
    return Sorting.binarySearchFromTo(this.elements, paramObject, paramInt1, paramInt2, paramComparator);
  }
  
  public Object clone()
  {
    ObjectArrayList localObjectArrayList = (ObjectArrayList)super.clone();
    localObjectArrayList.elements = ((Object[])this.elements.clone());
    return localObjectArrayList;
  }
  
  public boolean contains(Object paramObject, boolean paramBoolean)
  {
    return indexOfFromTo(paramObject, 0, this.size - 1, paramBoolean) >= 0;
  }
  
  public ObjectArrayList copy()
  {
    return (ObjectArrayList)clone();
  }
  
  public void delete(Object paramObject, boolean paramBoolean)
  {
    int i = indexOfFromTo(paramObject, 0, this.size - 1, paramBoolean);
    if (i >= 0) {
      removeFromTo(i, i);
    }
  }
  
  public Object[] elements()
  {
    return this.elements;
  }
  
  public ObjectArrayList elements(Object[] paramArrayOfObject)
  {
    this.elements = paramArrayOfObject;
    this.size = paramArrayOfObject.length;
    return this;
  }
  
  public void ensureCapacity(int paramInt)
  {
    this.elements = cern.colt.Arrays.ensureCapacity(this.elements, paramInt);
  }
  
  public boolean equals(Object paramObject)
  {
    return equals(paramObject, true);
  }
  
  public boolean equals(Object paramObject, boolean paramBoolean)
  {
    if (!(paramObject instanceof ObjectArrayList)) {
      return false;
    }
    if (this == paramObject) {
      return true;
    }
    if (paramObject == null) {
      return false;
    }
    ObjectArrayList localObjectArrayList = (ObjectArrayList)paramObject;
    if (this.elements == localObjectArrayList.elements()) {
      return true;
    }
    if (this.size != localObjectArrayList.size()) {
      return false;
    }
    Object[] arrayOfObject1 = localObjectArrayList.elements();
    Object[] arrayOfObject2 = this.elements;
    if (!paramBoolean)
    {
      i = this.size;
      do
      {
        i--;
        if (i < 0) {
          break;
        }
      } while (arrayOfObject2[i] == arrayOfObject1[i]);
      return false;
    }
    int i = this.size;
    do
    {
      i--;
      if (i < 0) {
        break;
      }
    } while (arrayOfObject2[i] == null ? arrayOfObject1[i] == null : arrayOfObject2[i].equals(arrayOfObject1[i]));
    return false;
    return true;
  }
  
  public void fillFromToWith(int paramInt1, int paramInt2, Object paramObject)
  {
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    int i = paramInt1;
    while (i <= paramInt2) {
      setQuick(i++, paramObject);
    }
  }
  
  public boolean forEach(ObjectProcedure paramObjectProcedure)
  {
    Object[] arrayOfObject = this.elements;
    int i = this.size;
    int j = 0;
    while (j < i) {
      if (!paramObjectProcedure.apply(arrayOfObject[(j++)])) {
        return false;
      }
    }
    return true;
  }
  
  public Object get(int paramInt)
  {
    if ((paramInt >= this.size) || (paramInt < 0)) {
      throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + this.size);
    }
    return this.elements[paramInt];
  }
  
  public Object getQuick(int paramInt)
  {
    return this.elements[paramInt];
  }
  
  public int indexOf(Object paramObject, boolean paramBoolean)
  {
    return indexOfFromTo(paramObject, 0, this.size - 1, paramBoolean);
  }
  
  public int indexOfFromTo(Object paramObject, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if (this.size == 0) {
      return -1;
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    Object[] arrayOfObject = this.elements;
    int i;
    if ((paramBoolean) && (paramObject != null)) {
      i = paramInt1;
    }
    while (i <= paramInt2)
    {
      if (paramObject.equals(arrayOfObject[i])) {
        return i;
      }
      i++;
      continue;
      for (i = paramInt1; i <= paramInt2; i++) {
        if (paramObject == arrayOfObject[i]) {
          return i;
        }
      }
    }
    return -1;
  }
  
  public boolean isSortedFromTo(int paramInt1, int paramInt2)
  {
    if (this.size == 0) {
      return true;
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    Object[] arrayOfObject = this.elements;
    for (int i = paramInt1 + 1; i <= paramInt2; i++) {
      if (((Comparable)arrayOfObject[i]).compareTo((Comparable)arrayOfObject[(i - 1)]) < 0) {
        return false;
      }
    }
    return true;
  }
  
  public int lastIndexOf(Object paramObject, boolean paramBoolean)
  {
    return lastIndexOfFromTo(paramObject, 0, this.size - 1, paramBoolean);
  }
  
  public int lastIndexOfFromTo(Object paramObject, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if (this.size == 0) {
      return -1;
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    Object[] arrayOfObject = this.elements;
    int i;
    if ((paramBoolean) && (paramObject != null)) {
      i = paramInt2;
    }
    while (i >= paramInt1)
    {
      if (paramObject.equals(arrayOfObject[i])) {
        return i;
      }
      i--;
      continue;
      for (i = paramInt2; i >= paramInt1; i--) {
        if (paramObject == arrayOfObject[i]) {
          return i;
        }
      }
    }
    return -1;
  }
  
  public void mergeSortFromTo(int paramInt1, int paramInt2)
  {
    if (this.size == 0) {
      return;
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    java.util.Arrays.sort(this.elements, paramInt1, paramInt2 + 1);
  }
  
  public void mergeSortFromTo(int paramInt1, int paramInt2, Comparator paramComparator)
  {
    if (this.size == 0) {
      return;
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    java.util.Arrays.sort(this.elements, paramInt1, paramInt2 + 1, paramComparator);
  }
  
  public ObjectArrayList partFromTo(int paramInt1, int paramInt2)
  {
    if (this.size == 0) {
      return new ObjectArrayList(0);
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    Object[] arrayOfObject = new Object[paramInt2 - paramInt1 + 1];
    System.arraycopy(this.elements, paramInt1, arrayOfObject, 0, paramInt2 - paramInt1 + 1);
    return new ObjectArrayList(arrayOfObject);
  }
  
  public void quickSortFromTo(int paramInt1, int paramInt2)
  {
    if (this.size == 0) {
      return;
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    Sorting.quickSort(this.elements, paramInt1, paramInt2 + 1);
  }
  
  public void quickSortFromTo(int paramInt1, int paramInt2, Comparator paramComparator)
  {
    if (this.size == 0) {
      return;
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    Sorting.quickSort(this.elements, paramInt1, paramInt2 + 1, paramComparator);
  }
  
  public boolean removeAll(ObjectArrayList paramObjectArrayList, boolean paramBoolean)
  {
    if (paramObjectArrayList.size == 0) {
      return false;
    }
    int i = paramObjectArrayList.size - 1;
    int j = 0;
    Object[] arrayOfObject = this.elements;
    for (int k = 0; k < this.size; k++) {
      if (paramObjectArrayList.indexOfFromTo(arrayOfObject[k], 0, i, paramBoolean) < 0) {
        arrayOfObject[(j++)] = arrayOfObject[k];
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
    if (i >= 0)
    {
      System.arraycopy(this.elements, paramInt2 + 1, this.elements, paramInt1, i);
      fillFromToWith(paramInt1 + i, this.size - 1, null);
    }
    int j = paramInt2 - paramInt1 + 1;
    if (j > 0) {
      this.size -= j;
    }
  }
  
  public void replaceFromToWithFrom(int paramInt1, int paramInt2, ObjectArrayList paramObjectArrayList, int paramInt3)
  {
    int i = paramInt2 - paramInt1 + 1;
    if (i > 0)
    {
      checkRangeFromTo(paramInt1, paramInt2, this.size);
      checkRangeFromTo(paramInt3, paramInt3 + i - 1, paramObjectArrayList.size);
      System.arraycopy(paramObjectArrayList.elements, paramInt3, this.elements, paramInt1, i);
    }
  }
  
  public void replaceFromToWithFromTo(int paramInt1, int paramInt2, ObjectArrayList paramObjectArrayList, int paramInt3, int paramInt4)
  {
    if (paramInt3 > paramInt4) {
      throw new IndexOutOfBoundsException("otherFrom: " + paramInt3 + ", otherTo: " + paramInt4);
    }
    if ((this == paramObjectArrayList) && (paramInt2 - paramInt1 != paramInt4 - paramInt3))
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
      System.arraycopy(paramObjectArrayList.elements, paramInt3, this.elements, paramInt1, i);
    }
  }
  
  public void replaceFromWith(int paramInt, Collection paramCollection)
  {
    checkRange(paramInt, this.size);
    Iterator localIterator = paramCollection.iterator();
    int i = paramInt;
    int j = Math.min(this.size - paramInt, paramCollection.size());
    for (int k = 0; k < j; k++) {
      this.elements[(i++)] = localIterator.next();
    }
  }
  
  public boolean retainAll(ObjectArrayList paramObjectArrayList, boolean paramBoolean)
  {
    if (paramObjectArrayList.size == 0)
    {
      if (this.size == 0) {
        return false;
      }
      setSize(0);
      return true;
    }
    int i = paramObjectArrayList.size - 1;
    int j = 0;
    Object[] arrayOfObject = this.elements;
    for (int k = 0; k < this.size; k++) {
      if (paramObjectArrayList.indexOfFromTo(arrayOfObject[k], 0, i, paramBoolean) >= 0) {
        arrayOfObject[(j++)] = arrayOfObject[k];
      }
    }
    k = j != this.size ? 1 : 0;
    setSize(j);
    return k;
  }
  
  public void reverse()
  {
    int i = this.size / 2;
    int j = this.size - 1;
    Object[] arrayOfObject = this.elements;
    int k = 0;
    while (k < i)
    {
      Object localObject = arrayOfObject[k];
      arrayOfObject[(k++)] = arrayOfObject[j];
      arrayOfObject[(j--)] = localObject;
    }
  }
  
  public void set(int paramInt, Object paramObject)
  {
    if ((paramInt >= this.size) || (paramInt < 0)) {
      throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + this.size);
    }
    this.elements[paramInt] = paramObject;
  }
  
  public void setQuick(int paramInt, Object paramObject)
  {
    this.elements[paramInt] = paramObject;
  }
  
  public void shuffleFromTo(int paramInt1, int paramInt2)
  {
    if (this.size == 0) {
      return;
    }
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    Uniform localUniform = new Uniform(new DRand(new Date()));
    Object[] arrayOfObject = this.elements;
    for (int j = paramInt1; j < paramInt2; j++)
    {
      int i = localUniform.nextIntFromTo(j, paramInt2);
      Object localObject = arrayOfObject[i];
      arrayOfObject[i] = arrayOfObject[j];
      arrayOfObject[j] = localObject;
    }
  }
  
  public int size()
  {
    return this.size;
  }
  
  public ObjectArrayList times(int paramInt)
  {
    ObjectArrayList localObjectArrayList = new ObjectArrayList(paramInt * this.size);
    int i = paramInt;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      localObjectArrayList.addAllOfFromTo(this, 0, size() - 1);
    }
    return localObjectArrayList;
  }
  
  public Object[] toArray(Object[] paramArrayOfObject)
  {
    if (paramArrayOfObject.length < this.size) {
      paramArrayOfObject = (Object[])Array.newInstance(paramArrayOfObject.getClass().getComponentType(), this.size);
    }
    Object[] arrayOfObject = this.elements;
    int i = this.size;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      paramArrayOfObject[i] = arrayOfObject[i];
    }
    if (paramArrayOfObject.length > this.size) {
      paramArrayOfObject[this.size] = null;
    }
    return paramArrayOfObject;
  }
  
  public ArrayList toList()
  {
    int i = size();
    Object[] arrayOfObject = this.elements;
    ArrayList localArrayList = new ArrayList(i);
    for (int j = 0; j < i; j++) {
      localArrayList.add(arrayOfObject[j]);
    }
    return localArrayList;
  }
  
  public String toString()
  {
    return cern.colt.Arrays.toString(partFromTo(0, size() - 1).elements());
  }
  
  public void trimToSize()
  {
    this.elements = cern.colt.Arrays.trimToCapacity(this.elements, size());
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.list.ObjectArrayList
 * JD-Core Version:    0.7.0.1
 */