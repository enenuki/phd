package cern.colt.matrix;

import cern.colt.function.ObjectFunction;
import cern.colt.function.ObjectObjectFunction;
import cern.colt.function.ObjectProcedure;
import cern.colt.list.IntArrayList;
import cern.colt.list.ObjectArrayList;
import cern.colt.matrix.impl.AbstractMatrix2D;
import cern.colt.matrix.objectalgo.Formatter;
import cern.colt.matrix.objectalgo.Sorting;

public abstract class ObjectMatrix2D
  extends AbstractMatrix2D
{
  public Object aggregate(ObjectObjectFunction paramObjectObjectFunction, ObjectFunction paramObjectFunction)
  {
    if (size() == 0) {
      return null;
    }
    Object localObject = paramObjectFunction.apply(getQuick(this.rows - 1, this.columns - 1));
    int i = 1;
    int j = this.rows;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      int k = this.columns - i;
      for (;;)
      {
        k--;
        if (k < 0) {
          break;
        }
        localObject = paramObjectObjectFunction.apply(localObject, paramObjectFunction.apply(getQuick(j, k)));
      }
      i = 0;
    }
    return localObject;
  }
  
  public Object aggregate(ObjectMatrix2D paramObjectMatrix2D, ObjectObjectFunction paramObjectObjectFunction1, ObjectObjectFunction paramObjectObjectFunction2)
  {
    checkShape(paramObjectMatrix2D);
    if (size() == 0) {
      return null;
    }
    Object localObject = paramObjectObjectFunction2.apply(getQuick(this.rows - 1, this.columns - 1), paramObjectMatrix2D.getQuick(this.rows - 1, this.columns - 1));
    int i = 1;
    int j = this.rows;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      int k = this.columns - i;
      for (;;)
      {
        k--;
        if (k < 0) {
          break;
        }
        localObject = paramObjectObjectFunction1.apply(localObject, paramObjectObjectFunction2.apply(getQuick(j, k), paramObjectMatrix2D.getQuick(j, k)));
      }
      i = 0;
    }
    return localObject;
  }
  
  public ObjectMatrix2D assign(Object[][] paramArrayOfObject)
  {
    if (paramArrayOfObject.length != this.rows) {
      throw new IllegalArgumentException("Must have same number of rows: rows=" + paramArrayOfObject.length + "rows()=" + rows());
    }
    int i = this.rows;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      Object[] arrayOfObject = paramArrayOfObject[i];
      if (arrayOfObject.length != this.columns) {
        throw new IllegalArgumentException("Must have same number of columns in every row: columns=" + arrayOfObject.length + "columns()=" + columns());
      }
      int j = this.columns;
      for (;;)
      {
        j--;
        if (j < 0) {
          break;
        }
        setQuick(i, j, arrayOfObject[j]);
      }
    }
    return this;
  }
  
  public ObjectMatrix2D assign(ObjectFunction paramObjectFunction)
  {
    int i = this.rows;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      int j = this.columns;
      for (;;)
      {
        j--;
        if (j < 0) {
          break;
        }
        setQuick(i, j, paramObjectFunction.apply(getQuick(i, j)));
      }
    }
    return this;
  }
  
  public ObjectMatrix2D assign(ObjectMatrix2D paramObjectMatrix2D)
  {
    if (paramObjectMatrix2D == this) {
      return this;
    }
    checkShape(paramObjectMatrix2D);
    if (haveSharedCells(paramObjectMatrix2D)) {
      paramObjectMatrix2D = paramObjectMatrix2D.copy();
    }
    int i = this.rows;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      int j = this.columns;
      for (;;)
      {
        j--;
        if (j < 0) {
          break;
        }
        setQuick(i, j, paramObjectMatrix2D.getQuick(i, j));
      }
    }
    return this;
  }
  
  public ObjectMatrix2D assign(ObjectMatrix2D paramObjectMatrix2D, ObjectObjectFunction paramObjectObjectFunction)
  {
    checkShape(paramObjectMatrix2D);
    int i = this.rows;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      int j = this.columns;
      for (;;)
      {
        j--;
        if (j < 0) {
          break;
        }
        setQuick(i, j, paramObjectObjectFunction.apply(getQuick(i, j), paramObjectMatrix2D.getQuick(i, j)));
      }
    }
    return this;
  }
  
  public ObjectMatrix2D assign(Object paramObject)
  {
    int i = this.rows;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      int j = this.columns;
      for (;;)
      {
        j--;
        if (j < 0) {
          break;
        }
        setQuick(i, j, paramObject);
      }
    }
    return this;
  }
  
  public int cardinality()
  {
    int i = 0;
    int j = this.rows;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      int k = this.columns;
      for (;;)
      {
        k--;
        if (k < 0) {
          break;
        }
        if (getQuick(j, k) != null) {
          i++;
        }
      }
    }
    return i;
  }
  
  public ObjectMatrix2D copy()
  {
    return like().assign(this);
  }
  
  public boolean equals(Object paramObject)
  {
    return equals(paramObject, true);
  }
  
  public boolean equals(Object paramObject, boolean paramBoolean)
  {
    if (!(paramObject instanceof ObjectMatrix2D)) {
      return false;
    }
    if (this == paramObject) {
      return true;
    }
    if (paramObject == null) {
      return false;
    }
    ObjectMatrix2D localObjectMatrix2D = (ObjectMatrix2D)paramObject;
    if (this.rows != localObjectMatrix2D.rows()) {
      return false;
    }
    if (this.columns != localObjectMatrix2D.columns()) {
      return false;
    }
    int j;
    if (!paramBoolean)
    {
      i = this.rows;
      do
      {
        i--;
        do
        {
          if (i < 0) {
            break;
          }
          j = this.columns;
          j--;
        } while (j < 0);
      } while (getQuick(i, j) == localObjectMatrix2D.getQuick(i, j));
      return false;
    }
    int i = this.rows;
    do
    {
      i--;
      do
      {
        if (i < 0) {
          break;
        }
        j = this.columns;
        j--;
      } while (j < 0);
    } while (getQuick(i, j) == null ? localObjectMatrix2D.getQuick(i, j) == null : getQuick(i, j).equals(localObjectMatrix2D.getQuick(i, j)));
    return false;
    return true;
  }
  
  public Object get(int paramInt1, int paramInt2)
  {
    if ((paramInt2 < 0) || (paramInt2 >= this.columns) || (paramInt1 < 0) || (paramInt1 >= this.rows)) {
      throw new IndexOutOfBoundsException("row:" + paramInt1 + ", column:" + paramInt2);
    }
    return getQuick(paramInt1, paramInt2);
  }
  
  protected ObjectMatrix2D getContent()
  {
    return this;
  }
  
  public void getNonZeros(IntArrayList paramIntArrayList1, IntArrayList paramIntArrayList2, ObjectArrayList paramObjectArrayList)
  {
    paramIntArrayList1.clear();
    paramIntArrayList2.clear();
    paramObjectArrayList.clear();
    int i = this.rows;
    int j = this.columns;
    for (int k = 0; k < i; k++) {
      for (int m = 0; m < j; m++)
      {
        Object localObject = getQuick(k, m);
        if (localObject != null)
        {
          paramIntArrayList1.add(k);
          paramIntArrayList2.add(m);
          paramObjectArrayList.add(localObject);
        }
      }
    }
  }
  
  public abstract Object getQuick(int paramInt1, int paramInt2);
  
  protected boolean haveSharedCells(ObjectMatrix2D paramObjectMatrix2D)
  {
    if (paramObjectMatrix2D == null) {
      return false;
    }
    if (this == paramObjectMatrix2D) {
      return true;
    }
    return getContent().haveSharedCellsRaw(paramObjectMatrix2D.getContent());
  }
  
  protected boolean haveSharedCellsRaw(ObjectMatrix2D paramObjectMatrix2D)
  {
    return false;
  }
  
  public ObjectMatrix2D like()
  {
    return like(this.rows, this.columns);
  }
  
  public abstract ObjectMatrix2D like(int paramInt1, int paramInt2);
  
  public abstract ObjectMatrix1D like1D(int paramInt);
  
  protected abstract ObjectMatrix1D like1D(int paramInt1, int paramInt2, int paramInt3);
  
  public void set(int paramInt1, int paramInt2, Object paramObject)
  {
    if ((paramInt2 < 0) || (paramInt2 >= this.columns) || (paramInt1 < 0) || (paramInt1 >= this.rows)) {
      throw new IndexOutOfBoundsException("row:" + paramInt1 + ", column:" + paramInt2);
    }
    setQuick(paramInt1, paramInt2, paramObject);
  }
  
  public abstract void setQuick(int paramInt1, int paramInt2, Object paramObject);
  
  public Object[][] toArray()
  {
    Object[][] arrayOfObject = new Object[this.rows][this.columns];
    int i = this.rows;
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      Object[] arrayOfObject1 = arrayOfObject[i];
      int j = this.columns;
      for (;;)
      {
        j--;
        if (j < 0) {
          break;
        }
        arrayOfObject1[j] = getQuick(i, j);
      }
    }
    return arrayOfObject;
  }
  
  public String toString()
  {
    return new Formatter().toString(this);
  }
  
  protected ObjectMatrix2D view()
  {
    return (ObjectMatrix2D)clone();
  }
  
  public ObjectMatrix1D viewColumn(int paramInt)
  {
    checkColumn(paramInt);
    int i = this.rows;
    int j = index(0, paramInt);
    int k = this.rowStride;
    return like1D(i, j, k);
  }
  
  public ObjectMatrix2D viewColumnFlip()
  {
    return (ObjectMatrix2D)view().vColumnFlip();
  }
  
  public ObjectMatrix2D viewDice()
  {
    return (ObjectMatrix2D)view().vDice();
  }
  
  public ObjectMatrix2D viewPart(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return (ObjectMatrix2D)view().vPart(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public ObjectMatrix1D viewRow(int paramInt)
  {
    checkRow(paramInt);
    int i = this.columns;
    int j = index(paramInt, 0);
    int k = this.columnStride;
    return like1D(i, j, k);
  }
  
  public ObjectMatrix2D viewRowFlip()
  {
    return (ObjectMatrix2D)view().vRowFlip();
  }
  
  public ObjectMatrix2D viewSelection(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    int i;
    if (paramArrayOfInt1 == null)
    {
      paramArrayOfInt1 = new int[this.rows];
      i = this.rows;
      for (;;)
      {
        i--;
        if (i < 0) {
          break;
        }
        paramArrayOfInt1[i] = i;
      }
    }
    if (paramArrayOfInt2 == null)
    {
      paramArrayOfInt2 = new int[this.columns];
      i = this.columns;
      for (;;)
      {
        i--;
        if (i < 0) {
          break;
        }
        paramArrayOfInt2[i] = i;
      }
    }
    checkRowIndexes(paramArrayOfInt1);
    checkColumnIndexes(paramArrayOfInt2);
    int[] arrayOfInt1 = new int[paramArrayOfInt1.length];
    int[] arrayOfInt2 = new int[paramArrayOfInt2.length];
    int j = paramArrayOfInt1.length;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      arrayOfInt1[j] = _rowOffset(_rowRank(paramArrayOfInt1[j]));
    }
    j = paramArrayOfInt2.length;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      arrayOfInt2[j] = _columnOffset(_columnRank(paramArrayOfInt2[j]));
    }
    return viewSelectionLike(arrayOfInt1, arrayOfInt2);
  }
  
  public ObjectMatrix2D viewSelection(ObjectMatrix1DProcedure paramObjectMatrix1DProcedure)
  {
    IntArrayList localIntArrayList = new IntArrayList();
    for (int i = 0; i < this.rows; i++) {
      if (paramObjectMatrix1DProcedure.apply(viewRow(i))) {
        localIntArrayList.add(i);
      }
    }
    localIntArrayList.trimToSize();
    return viewSelection(localIntArrayList.elements(), null);
  }
  
  protected abstract ObjectMatrix2D viewSelectionLike(int[] paramArrayOfInt1, int[] paramArrayOfInt2);
  
  public ObjectMatrix2D viewSorted(int paramInt)
  {
    return Sorting.mergeSort.sort(this, paramInt);
  }
  
  public ObjectMatrix2D viewStrides(int paramInt1, int paramInt2)
  {
    return (ObjectMatrix2D)view().vStrides(paramInt1, paramInt2);
  }
  
  private boolean xforEach(ObjectProcedure paramObjectProcedure)
  {
    int i;
    int j;
    do
    {
      i = this.rows;
      i--;
      do
      {
        if (i < 0) {
          break;
        }
        j = this.columns;
        j--;
      } while (j < 0);
    } while (paramObjectProcedure.apply(getQuick(i, j)));
    return false;
    return true;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.ObjectMatrix2D
 * JD-Core Version:    0.7.0.1
 */