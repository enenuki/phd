package cern.colt.list;

import java.util.Collection;

public abstract class AbstractList
  extends AbstractCollection
{
  public void addAllOf(Collection paramCollection)
  {
    beforeInsertAllOf(size(), paramCollection);
  }
  
  public void beforeInsertAllOf(int paramInt, Collection paramCollection)
  {
    beforeInsertDummies(paramInt, paramCollection.size());
    replaceFromWith(paramInt, paramCollection);
  }
  
  protected abstract void beforeInsertDummies(int paramInt1, int paramInt2);
  
  protected static void checkRange(int paramInt1, int paramInt2)
  {
    if ((paramInt1 >= paramInt2) || (paramInt1 < 0)) {
      throw new IndexOutOfBoundsException("Index: " + paramInt1 + ", Size: " + paramInt2);
    }
  }
  
  protected static void checkRangeFromTo(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt2 == paramInt1 - 1) {
      return;
    }
    if ((paramInt1 < 0) || (paramInt1 > paramInt2) || (paramInt2 >= paramInt3)) {
      throw new IndexOutOfBoundsException("from: " + paramInt1 + ", to: " + paramInt2 + ", size=" + paramInt3);
    }
  }
  
  public void clear()
  {
    removeFromTo(0, size() - 1);
  }
  
  public final void mergeSort()
  {
    mergeSortFromTo(0, size() - 1);
  }
  
  public abstract void mergeSortFromTo(int paramInt1, int paramInt2);
  
  public final void quickSort()
  {
    quickSortFromTo(0, size() - 1);
  }
  
  public abstract void quickSortFromTo(int paramInt1, int paramInt2);
  
  public void remove(int paramInt)
  {
    removeFromTo(paramInt, paramInt);
  }
  
  public abstract void removeFromTo(int paramInt1, int paramInt2);
  
  public abstract void replaceFromWith(int paramInt, Collection paramCollection);
  
  public abstract void reverse();
  
  public void setSize(int paramInt)
  {
    if (paramInt < 0) {
      throw new IndexOutOfBoundsException("newSize:" + paramInt);
    }
    int i = size();
    if (paramInt != i) {
      if (paramInt > i) {
        beforeInsertDummies(i, paramInt - i);
      } else if (paramInt < i) {
        removeFromTo(paramInt, i - 1);
      }
    }
  }
  
  public final void shuffle()
  {
    shuffleFromTo(0, size() - 1);
  }
  
  public abstract void shuffleFromTo(int paramInt1, int paramInt2);
  
  public final void sort()
  {
    sortFromTo(0, size() - 1);
  }
  
  public void sortFromTo(int paramInt1, int paramInt2)
  {
    quickSortFromTo(paramInt1, paramInt2);
  }
  
  public void trimToSize() {}
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.list.AbstractList
 * JD-Core Version:    0.7.0.1
 */