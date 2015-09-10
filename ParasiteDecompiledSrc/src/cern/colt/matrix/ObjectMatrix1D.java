package cern.colt.matrix;

import cern.colt.function.ObjectFunction;
import cern.colt.function.ObjectObjectFunction;
import cern.colt.function.ObjectProcedure;
import cern.colt.list.IntArrayList;
import cern.colt.list.ObjectArrayList;
import cern.colt.matrix.impl.AbstractMatrix1D;
import cern.colt.matrix.objectalgo.Formatter;
import cern.colt.matrix.objectalgo.Sorting;

public abstract class ObjectMatrix1D
  extends AbstractMatrix1D
{
  public Object aggregate(ObjectObjectFunction paramObjectObjectFunction, ObjectFunction paramObjectFunction)
  {
    if (this.size == 0) {
      return null;
    }
    Object localObject = paramObjectFunction.apply(getQuick(this.size - 1));
    int i = this.size - 1;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      localObject = paramObjectObjectFunction.apply(localObject, paramObjectFunction.apply(getQuick(i)));
    }
    return localObject;
  }
  
  public Object aggregate(ObjectMatrix1D paramObjectMatrix1D, ObjectObjectFunction paramObjectObjectFunction1, ObjectObjectFunction paramObjectObjectFunction2)
  {
    checkSize(paramObjectMatrix1D);
    if (this.size == 0) {
      return null;
    }
    Object localObject = paramObjectObjectFunction2.apply(getQuick(this.size - 1), paramObjectMatrix1D.getQuick(this.size - 1));
    int i = this.size - 1;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      localObject = paramObjectObjectFunction1.apply(localObject, paramObjectObjectFunction2.apply(getQuick(i), paramObjectMatrix1D.getQuick(i)));
    }
    return localObject;
  }
  
  public ObjectMatrix1D assign(Object[] paramArrayOfObject)
  {
    if (paramArrayOfObject.length != this.size) {
      throw new IllegalArgumentException("Must have same number of cells: length=" + paramArrayOfObject.length + ", size()=" + size());
    }
    int i = this.size;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      setQuick(i, paramArrayOfObject[i]);
    }
    return this;
  }
  
  public ObjectMatrix1D assign(ObjectFunction paramObjectFunction)
  {
    int i = this.size;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      setQuick(i, paramObjectFunction.apply(getQuick(i)));
    }
    return this;
  }
  
  public ObjectMatrix1D assign(ObjectMatrix1D paramObjectMatrix1D)
  {
    if (paramObjectMatrix1D == this) {
      return this;
    }
    checkSize(paramObjectMatrix1D);
    if (haveSharedCells(paramObjectMatrix1D)) {
      paramObjectMatrix1D = paramObjectMatrix1D.copy();
    }
    int i = this.size;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      setQuick(i, paramObjectMatrix1D.getQuick(i));
    }
    return this;
  }
  
  public ObjectMatrix1D assign(ObjectMatrix1D paramObjectMatrix1D, ObjectObjectFunction paramObjectObjectFunction)
  {
    checkSize(paramObjectMatrix1D);
    int i = this.size;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      setQuick(i, paramObjectObjectFunction.apply(getQuick(i), paramObjectMatrix1D.getQuick(i)));
    }
    return this;
  }
  
  public ObjectMatrix1D assign(Object paramObject)
  {
    int i = this.size;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      setQuick(i, paramObject);
    }
    return this;
  }
  
  public int cardinality()
  {
    int i = 0;
    int j = this.size;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      if (getQuick(j) != null) {
        i++;
      }
    }
    return i;
  }
  
  public ObjectMatrix1D copy()
  {
    ObjectMatrix1D localObjectMatrix1D = like();
    localObjectMatrix1D.assign(this);
    return localObjectMatrix1D;
  }
  
  public boolean equals(Object paramObject)
  {
    return equals(paramObject, true);
  }
  
  public boolean equals(Object paramObject, boolean paramBoolean)
  {
    if (!(paramObject instanceof ObjectMatrix1D)) {
      return false;
    }
    if (this == paramObject) {
      return true;
    }
    if (paramObject == null) {
      return false;
    }
    ObjectMatrix1D localObjectMatrix1D = (ObjectMatrix1D)paramObject;
    if (this.size != localObjectMatrix1D.size()) {
      return false;
    }
    if (!paramBoolean)
    {
      i = this.size;
      do
      {
        i--;
        if (i < 0) {
          break;
        }
      } while (getQuick(i) == localObjectMatrix1D.getQuick(i));
      return false;
    }
    int i = this.size;
    do
    {
      i--;
      if (i < 0) {
        break;
      }
    } while (getQuick(i) == null ? localObjectMatrix1D.getQuick(i) == null : getQuick(i).equals(localObjectMatrix1D.getQuick(i)));
    return false;
    return true;
  }
  
  public Object get(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this.size)) {
      checkIndex(paramInt);
    }
    return getQuick(paramInt);
  }
  
  protected ObjectMatrix1D getContent()
  {
    return this;
  }
  
  public void getNonZeros(IntArrayList paramIntArrayList, ObjectArrayList paramObjectArrayList)
  {
    int i = paramIntArrayList != null ? 1 : 0;
    int j = paramObjectArrayList != null ? 1 : 0;
    if (i != 0) {
      paramIntArrayList.clear();
    }
    if (j != 0) {
      paramObjectArrayList.clear();
    }
    int k = this.size;
    for (int m = 0; m < k; m++)
    {
      Object localObject = getQuick(m);
      if (localObject != null)
      {
        if (i != 0) {
          paramIntArrayList.add(m);
        }
        if (j != 0) {
          paramObjectArrayList.add(localObject);
        }
      }
    }
  }
  
  public abstract Object getQuick(int paramInt);
  
  protected boolean haveSharedCells(ObjectMatrix1D paramObjectMatrix1D)
  {
    if (paramObjectMatrix1D == null) {
      return false;
    }
    if (this == paramObjectMatrix1D) {
      return true;
    }
    return getContent().haveSharedCellsRaw(paramObjectMatrix1D.getContent());
  }
  
  protected boolean haveSharedCellsRaw(ObjectMatrix1D paramObjectMatrix1D)
  {
    return false;
  }
  
  public ObjectMatrix1D like()
  {
    return like(this.size);
  }
  
  public abstract ObjectMatrix1D like(int paramInt);
  
  public abstract ObjectMatrix2D like2D(int paramInt1, int paramInt2);
  
  public void set(int paramInt, Object paramObject)
  {
    if ((paramInt < 0) || (paramInt >= this.size)) {
      checkIndex(paramInt);
    }
    setQuick(paramInt, paramObject);
  }
  
  public abstract void setQuick(int paramInt, Object paramObject);
  
  public void swap(ObjectMatrix1D paramObjectMatrix1D)
  {
    checkSize(paramObjectMatrix1D);
    int i = this.size;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      Object localObject = getQuick(i);
      setQuick(i, paramObjectMatrix1D.getQuick(i));
      paramObjectMatrix1D.setQuick(i, localObject);
    }
  }
  
  public Object[] toArray()
  {
    Object[] arrayOfObject = new Object[this.size];
    toArray(arrayOfObject);
    return arrayOfObject;
  }
  
  public void toArray(Object[] paramArrayOfObject)
  {
    if (paramArrayOfObject.length < this.size) {
      throw new IllegalArgumentException("values too small");
    }
    int i = this.size;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      paramArrayOfObject[i] = getQuick(i);
    }
  }
  
  public String toString()
  {
    return new Formatter().toString(this);
  }
  
  protected ObjectMatrix1D view()
  {
    return (ObjectMatrix1D)clone();
  }
  
  public ObjectMatrix1D viewFlip()
  {
    return (ObjectMatrix1D)view().vFlip();
  }
  
  public ObjectMatrix1D viewPart(int paramInt1, int paramInt2)
  {
    return (ObjectMatrix1D)view().vPart(paramInt1, paramInt2);
  }
  
  public ObjectMatrix1D viewSelection(int[] paramArrayOfInt)
  {
    if (paramArrayOfInt == null)
    {
      paramArrayOfInt = new int[this.size];
      int i = this.size;
      for (;;)
      {
        i--;
        if (i < 0) {
          break;
        }
        paramArrayOfInt[i] = i;
      }
    }
    checkIndexes(paramArrayOfInt);
    int[] arrayOfInt = new int[paramArrayOfInt.length];
    int j = paramArrayOfInt.length;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      arrayOfInt[j] = index(paramArrayOfInt[j]);
    }
    return viewSelectionLike(arrayOfInt);
  }
  
  public ObjectMatrix1D viewSelection(ObjectProcedure paramObjectProcedure)
  {
    IntArrayList localIntArrayList = new IntArrayList();
    for (int i = 0; i < this.size; i++) {
      if (paramObjectProcedure.apply(getQuick(i))) {
        localIntArrayList.add(i);
      }
    }
    localIntArrayList.trimToSize();
    return viewSelection(localIntArrayList.elements());
  }
  
  protected abstract ObjectMatrix1D viewSelectionLike(int[] paramArrayOfInt);
  
  public ObjectMatrix1D viewSorted()
  {
    return Sorting.mergeSort.sort(this);
  }
  
  public ObjectMatrix1D viewStrides(int paramInt)
  {
    return (ObjectMatrix1D)view().vStrides(paramInt);
  }
  
  private boolean xforEach(ObjectProcedure paramObjectProcedure)
  {
    int i = this.size;
    do
    {
      i--;
      if (i < 0) {
        break;
      }
    } while (paramObjectProcedure.apply(getQuick(i)));
    return false;
    return true;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.ObjectMatrix1D
 * JD-Core Version:    0.7.0.1
 */