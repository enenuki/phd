package cern.colt.list;

import cern.colt.Arrays;
import cern.colt.function.BooleanProcedure;
import cern.jet.random.Uniform;
import cern.jet.random.engine.DRand;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

public abstract class AbstractBooleanList
  extends AbstractList
{
  protected int size;
  
  public void add(boolean paramBoolean)
  {
    beforeInsert(this.size, paramBoolean);
  }
  
  public void addAllOfFromTo(AbstractBooleanList paramAbstractBooleanList, int paramInt1, int paramInt2)
  {
    beforeInsertAllOfFromTo(this.size, paramAbstractBooleanList, paramInt1, paramInt2);
  }
  
  public void beforeInsert(int paramInt, boolean paramBoolean)
  {
    beforeInsertDummies(paramInt, 1);
    set(paramInt, paramBoolean);
  }
  
  public void beforeInsertAllOfFromTo(int paramInt1, AbstractBooleanList paramAbstractBooleanList, int paramInt2, int paramInt3)
  {
    int i = paramInt3 - paramInt2 + 1;
    beforeInsertDummies(paramInt1, i);
    replaceFromToWithFrom(paramInt1, paramInt1 + i - 1, paramAbstractBooleanList, paramInt2);
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
  
  public int binarySearch(boolean paramBoolean)
  {
    return binarySearchFromTo(paramBoolean, 0, this.size - 1);
  }
  
  public int binarySearchFromTo(boolean paramBoolean, int paramInt1, int paramInt2)
  {
    int i = paramInt1;
    int j = paramInt2;
    int k = toInt(paramBoolean);
    while (i <= j)
    {
      int m = (i + j) / 2;
      boolean bool = get(m);
      if (toInt(bool) < k) {
        i = m + 1;
      } else if (toInt(bool) > k) {
        j = m - 1;
      } else {
        return m;
      }
    }
    return -(i + 1);
  }
  
  public Object clone()
  {
    return partFromTo(0, this.size - 1);
  }
  
  public boolean contains(boolean paramBoolean)
  {
    return indexOfFromTo(paramBoolean, 0, this.size - 1) >= 0;
  }
  
  public void delete(boolean paramBoolean)
  {
    int i = indexOfFromTo(paramBoolean, 0, this.size - 1);
    if (i >= 0) {
      remove(i);
    }
  }
  
  public boolean[] elements()
  {
    boolean[] arrayOfBoolean = new boolean[this.size];
    int i = this.size;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      arrayOfBoolean[i] = getQuick(i);
    }
    return arrayOfBoolean;
  }
  
  public AbstractBooleanList elements(boolean[] paramArrayOfBoolean)
  {
    clear();
    addAllOfFromTo(new BooleanArrayList(paramArrayOfBoolean), 0, paramArrayOfBoolean.length - 1);
    return this;
  }
  
  public abstract void ensureCapacity(int paramInt);
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof AbstractBooleanList)) {
      return false;
    }
    if (this == paramObject) {
      return true;
    }
    if (paramObject == null) {
      return false;
    }
    AbstractBooleanList localAbstractBooleanList = (AbstractBooleanList)paramObject;
    if (size() != localAbstractBooleanList.size()) {
      return false;
    }
    int i = size();
    do
    {
      i--;
      if (i < 0) {
        break;
      }
    } while (getQuick(i) == localAbstractBooleanList.getQuick(i));
    return false;
    return true;
  }
  
  public void fillFromToWith(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    int i = paramInt1;
    while (i <= paramInt2) {
      setQuick(i++, paramBoolean);
    }
  }
  
  public boolean forEach(BooleanProcedure paramBooleanProcedure)
  {
    int i = 0;
    while (i < this.size) {
      if (!paramBooleanProcedure.apply(get(i++))) {
        return false;
      }
    }
    return true;
  }
  
  public boolean get(int paramInt)
  {
    if ((paramInt >= this.size) || (paramInt < 0)) {
      throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + this.size);
    }
    return getQuick(paramInt);
  }
  
  protected abstract boolean getQuick(int paramInt);
  
  public int indexOf(boolean paramBoolean)
  {
    return indexOfFromTo(paramBoolean, 0, this.size - 1);
  }
  
  public int indexOfFromTo(boolean paramBoolean, int paramInt1, int paramInt2)
  {
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    for (int i = paramInt1; i <= paramInt2; i++) {
      if (paramBoolean == getQuick(i)) {
        return i;
      }
    }
    return -1;
  }
  
  public int lastIndexOf(boolean paramBoolean)
  {
    return lastIndexOfFromTo(paramBoolean, 0, this.size - 1);
  }
  
  public int lastIndexOfFromTo(boolean paramBoolean, int paramInt1, int paramInt2)
  {
    checkRangeFromTo(paramInt1, paramInt2, size());
    for (int i = paramInt2; i >= paramInt1; i--) {
      if (paramBoolean == getQuick(i)) {
        return i;
      }
    }
    return -1;
  }
  
  public AbstractBooleanList partFromTo(int paramInt1, int paramInt2)
  {
    checkRangeFromTo(paramInt1, paramInt2, this.size);
    int i = paramInt2 - paramInt1 + 1;
    BooleanArrayList localBooleanArrayList = new BooleanArrayList(i);
    localBooleanArrayList.addAllOfFromTo(this, paramInt1, paramInt2);
    return localBooleanArrayList;
  }
  
  public boolean removeAll(AbstractBooleanList paramAbstractBooleanList)
  {
    if (paramAbstractBooleanList.size() == 0) {
      return false;
    }
    int i = paramAbstractBooleanList.size() - 1;
    int j = 0;
    for (int k = 0; k < this.size; k++) {
      if (paramAbstractBooleanList.indexOfFromTo(getQuick(k), 0, i) < 0) {
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
  
  public void replaceFromToWithFrom(int paramInt1, int paramInt2, AbstractBooleanList paramAbstractBooleanList, int paramInt3)
  {
    int i = paramInt2 - paramInt1 + 1;
    if (i > 0)
    {
      checkRangeFromTo(paramInt1, paramInt2, size());
      checkRangeFromTo(paramInt3, paramInt3 + i - 1, paramAbstractBooleanList.size());
      if (paramInt1 <= paramInt3) {
        for (;;)
        {
          i--;
          if (i < 0) {
            break;
          }
          setQuick(paramInt1++, paramAbstractBooleanList.getQuick(paramInt3++));
        }
      }
      int j = paramInt3 + i - 1;
      for (;;)
      {
        i--;
        if (i < 0) {
          break;
        }
        setQuick(paramInt2--, paramAbstractBooleanList.getQuick(j--));
      }
    }
  }
  
  public void replaceFromToWithFromTo(int paramInt1, int paramInt2, AbstractBooleanList paramAbstractBooleanList, int paramInt3, int paramInt4)
  {
    if (paramInt3 > paramInt4) {
      throw new IndexOutOfBoundsException("otherFrom: " + paramInt3 + ", otherTo: " + paramInt4);
    }
    if ((this == paramAbstractBooleanList) && (paramInt2 - paramInt1 != paramInt4 - paramInt3))
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
      replaceFromToWithFrom(paramInt1, paramInt1 + i - 1, paramAbstractBooleanList, paramInt3);
    }
  }
  
  public void replaceFromWith(int paramInt, Collection paramCollection)
  {
    checkRange(paramInt, size());
    Iterator localIterator = paramCollection.iterator();
    int i = paramInt;
    int j = Math.min(size() - paramInt, paramCollection.size());
    for (int k = 0; k < j; k++) {
      set(i++, ((Boolean)localIterator.next()).booleanValue());
    }
  }
  
  public boolean retainAll(AbstractBooleanList paramAbstractBooleanList)
  {
    if (paramAbstractBooleanList.size() == 0)
    {
      if (this.size == 0) {
        return false;
      }
      setSize(0);
      return true;
    }
    int i = paramAbstractBooleanList.size() - 1;
    int j = 0;
    for (int k = 0; k < this.size; k++) {
      if (paramAbstractBooleanList.indexOfFromTo(getQuick(k), 0, i) >= 0) {
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
      boolean bool = getQuick(k);
      setQuick(k++, getQuick(j));
      setQuick(j--, bool);
    }
  }
  
  public void set(int paramInt, boolean paramBoolean)
  {
    if ((paramInt >= this.size) || (paramInt < 0)) {
      throw new IndexOutOfBoundsException("Index: " + paramInt + ", Size: " + this.size);
    }
    setQuick(paramInt, paramBoolean);
  }
  
  protected abstract void setQuick(int paramInt, boolean paramBoolean);
  
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
      boolean bool = getQuick(j);
      setQuick(j, getQuick(i));
      setQuick(i, bool);
    }
  }
  
  public int size()
  {
    return this.size;
  }
  
  public AbstractBooleanList times(int paramInt)
  {
    BooleanArrayList localBooleanArrayList = new BooleanArrayList(paramInt * size());
    int i = paramInt;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      localBooleanArrayList.addAllOfFromTo(this, 0, size() - 1);
    }
    return localBooleanArrayList;
  }
  
  protected static int toInt(boolean paramBoolean)
  {
    return paramBoolean ? 1 : 0;
  }
  
  public ArrayList toList()
  {
    int i = size();
    ArrayList localArrayList = new ArrayList(i);
    for (int j = 0; j < i; j++) {
      localArrayList.add(new Boolean(get(j)));
    }
    return localArrayList;
  }
  
  public String toString()
  {
    return Arrays.toString(partFromTo(0, size() - 1).elements());
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.list.AbstractBooleanList
 * JD-Core Version:    0.7.0.1
 */