package cern.colt.matrix;

import cern.colt.function.IntIntIntProcedure;
import cern.colt.function.ObjectFunction;
import cern.colt.function.ObjectObjectFunction;
import cern.colt.function.ObjectProcedure;
import cern.colt.list.IntArrayList;
import cern.colt.list.ObjectArrayList;
import cern.colt.matrix.impl.AbstractMatrix3D;
import cern.colt.matrix.objectalgo.Formatter;
import cern.colt.matrix.objectalgo.Sorting;

public abstract class ObjectMatrix3D
  extends AbstractMatrix3D
{
  public Object aggregate(ObjectObjectFunction paramObjectObjectFunction, ObjectFunction paramObjectFunction)
  {
    if (size() == 0) {
      return null;
    }
    Object localObject = paramObjectFunction.apply(getQuick(this.slices - 1, this.rows - 1, this.columns - 1));
    int i = 1;
    int j = this.slices;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      int k = this.rows;
      for (;;)
      {
        k--;
        if (k < 0) {
          break;
        }
        int m = this.columns - i;
        for (;;)
        {
          m--;
          if (m < 0) {
            break;
          }
          localObject = paramObjectObjectFunction.apply(localObject, paramObjectFunction.apply(getQuick(j, k, m)));
        }
        i = 0;
      }
    }
    return localObject;
  }
  
  public Object aggregate(ObjectMatrix3D paramObjectMatrix3D, ObjectObjectFunction paramObjectObjectFunction1, ObjectObjectFunction paramObjectObjectFunction2)
  {
    checkShape(paramObjectMatrix3D);
    if (size() == 0) {
      return null;
    }
    Object localObject = paramObjectObjectFunction2.apply(getQuick(this.slices - 1, this.rows - 1, this.columns - 1), paramObjectMatrix3D.getQuick(this.slices - 1, this.rows - 1, this.columns - 1));
    int i = 1;
    int j = this.slices;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      int k = this.rows;
      for (;;)
      {
        k--;
        if (k < 0) {
          break;
        }
        int m = this.columns - i;
        for (;;)
        {
          m--;
          if (m < 0) {
            break;
          }
          localObject = paramObjectObjectFunction1.apply(localObject, paramObjectObjectFunction2.apply(getQuick(j, k, m), paramObjectMatrix3D.getQuick(j, k, m)));
        }
        i = 0;
      }
    }
    return localObject;
  }
  
  public ObjectMatrix3D assign(Object[][][] paramArrayOfObject)
  {
    if (paramArrayOfObject.length != this.slices) {
      throw new IllegalArgumentException("Must have same number of slices: slices=" + paramArrayOfObject.length + "slices()=" + slices());
    }
    int i = this.slices;
    i--;
    if (i >= 0)
    {
      Object[][] arrayOfObject = paramArrayOfObject[i];
      if (arrayOfObject.length != this.rows) {
        throw new IllegalArgumentException("Must have same number of rows in every slice: rows=" + arrayOfObject.length + "rows()=" + rows());
      }
      int j = this.rows;
      for (;;)
      {
        j--;
        if (j < 0) {
          break;
        }
        Object[] arrayOfObject1 = arrayOfObject[j];
        if (arrayOfObject1.length != this.columns) {
          throw new IllegalArgumentException("Must have same number of columns in every row: columns=" + arrayOfObject1.length + "columns()=" + columns());
        }
        int k = this.columns;
        for (;;)
        {
          k--;
          if (k < 0) {
            break;
          }
          setQuick(i, j, k, arrayOfObject1[k]);
        }
      }
    }
    return this;
  }
  
  public ObjectMatrix3D assign(ObjectFunction paramObjectFunction)
  {
    int i = this.slices;
    i--;
    if (i >= 0)
    {
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
          setQuick(i, j, k, paramObjectFunction.apply(getQuick(i, j, k)));
        }
      }
    }
    return this;
  }
  
  public ObjectMatrix3D assign(ObjectMatrix3D paramObjectMatrix3D)
  {
    if (paramObjectMatrix3D == this) {
      return this;
    }
    checkShape(paramObjectMatrix3D);
    if (haveSharedCells(paramObjectMatrix3D)) {
      paramObjectMatrix3D = paramObjectMatrix3D.copy();
    }
    int i = this.slices;
    i--;
    if (i >= 0)
    {
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
          setQuick(i, j, k, paramObjectMatrix3D.getQuick(i, j, k));
        }
      }
    }
    return this;
  }
  
  public ObjectMatrix3D assign(ObjectMatrix3D paramObjectMatrix3D, ObjectObjectFunction paramObjectObjectFunction)
  {
    checkShape(paramObjectMatrix3D);
    int i = this.slices;
    i--;
    if (i >= 0)
    {
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
          setQuick(i, j, k, paramObjectObjectFunction.apply(getQuick(i, j, k), paramObjectMatrix3D.getQuick(i, j, k)));
        }
      }
    }
    return this;
  }
  
  public ObjectMatrix3D assign(Object paramObject)
  {
    int i = this.slices;
    i--;
    if (i >= 0)
    {
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
          setQuick(i, j, k, paramObject);
        }
      }
    }
    return this;
  }
  
  public int cardinality()
  {
    int i = 0;
    int j = this.slices;
    j--;
    if (j >= 0)
    {
      int k = this.rows;
      for (;;)
      {
        k--;
        if (k < 0) {
          break;
        }
        int m = this.columns;
        for (;;)
        {
          m--;
          if (m < 0) {
            break;
          }
          if (getQuick(j, k, m) != null) {
            i++;
          }
        }
      }
    }
    return i;
  }
  
  public ObjectMatrix3D copy()
  {
    return like().assign(this);
  }
  
  public boolean equals(Object paramObject)
  {
    return equals(paramObject, true);
  }
  
  public boolean equals(Object paramObject, boolean paramBoolean)
  {
    if (!(paramObject instanceof ObjectMatrix3D)) {
      return false;
    }
    if (this == paramObject) {
      return true;
    }
    if (paramObject == null) {
      return false;
    }
    ObjectMatrix3D localObjectMatrix3D = (ObjectMatrix3D)paramObject;
    if (this.rows != localObjectMatrix3D.rows()) {
      return false;
    }
    if (this.columns != localObjectMatrix3D.columns()) {
      return false;
    }
    int i;
    label63:
    int j;
    int k;
    if (!paramBoolean)
    {
      i = this.slices;
      i--;
      if (i >= 0)
      {
        j = this.rows;
        label91:
        do
        {
          j--;
          break label91;
          if (j < 0) {
            break label63;
          }
          k = this.columns;
          k--;
          if (k < 0) {
            break;
          }
        } while (getQuick(i, j, k) == localObjectMatrix3D.getQuick(i, j, k));
        return false;
      }
    }
    else
    {
      i = this.slices;
      label130:
      i--;
      if (i >= 0)
      {
        j = this.rows;
        label158:
        do
        {
          j--;
          break label158;
          if (j < 0) {
            break label130;
          }
          k = this.columns;
          k--;
          if (k < 0) {
            break;
          }
        } while (getQuick(i, j, k) == null ? localObjectMatrix3D.getQuick(i, j, k) == null : getQuick(i, j, k).equals(localObjectMatrix3D.getQuick(i, j, k)));
        return false;
      }
    }
    return true;
  }
  
  public Object get(int paramInt1, int paramInt2, int paramInt3)
  {
    if ((paramInt1 < 0) || (paramInt1 >= this.slices) || (paramInt2 < 0) || (paramInt2 >= this.rows) || (paramInt3 < 0) || (paramInt3 >= this.columns)) {
      throw new IndexOutOfBoundsException("slice:" + paramInt1 + ", row:" + paramInt2 + ", column:" + paramInt3);
    }
    return getQuick(paramInt1, paramInt2, paramInt3);
  }
  
  protected ObjectMatrix3D getContent()
  {
    return this;
  }
  
  public void getNonZeros(IntArrayList paramIntArrayList1, IntArrayList paramIntArrayList2, IntArrayList paramIntArrayList3, ObjectArrayList paramObjectArrayList)
  {
    paramIntArrayList1.clear();
    paramIntArrayList2.clear();
    paramIntArrayList3.clear();
    paramObjectArrayList.clear();
    int i = this.slices;
    int j = this.rows;
    int k = this.columns;
    for (int m = 0; m < i; m++) {
      for (int n = 0; n < j; n++) {
        for (int i1 = 0; i1 < k; i1++)
        {
          Object localObject = getQuick(m, n, i1);
          if (localObject != null)
          {
            paramIntArrayList1.add(m);
            paramIntArrayList2.add(n);
            paramIntArrayList3.add(i1);
            paramObjectArrayList.add(localObject);
          }
        }
      }
    }
  }
  
  public abstract Object getQuick(int paramInt1, int paramInt2, int paramInt3);
  
  protected boolean haveSharedCells(ObjectMatrix3D paramObjectMatrix3D)
  {
    if (paramObjectMatrix3D == null) {
      return false;
    }
    if (this == paramObjectMatrix3D) {
      return true;
    }
    return getContent().haveSharedCellsRaw(paramObjectMatrix3D.getContent());
  }
  
  protected boolean haveSharedCellsRaw(ObjectMatrix3D paramObjectMatrix3D)
  {
    return false;
  }
  
  public ObjectMatrix3D like()
  {
    return like(this.slices, this.rows, this.columns);
  }
  
  public abstract ObjectMatrix3D like(int paramInt1, int paramInt2, int paramInt3);
  
  protected abstract ObjectMatrix2D like2D(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
  
  public void set(int paramInt1, int paramInt2, int paramInt3, Object paramObject)
  {
    if ((paramInt1 < 0) || (paramInt1 >= this.slices) || (paramInt2 < 0) || (paramInt2 >= this.rows) || (paramInt3 < 0) || (paramInt3 >= this.columns)) {
      throw new IndexOutOfBoundsException("slice:" + paramInt1 + ", row:" + paramInt2 + ", column:" + paramInt3);
    }
    setQuick(paramInt1, paramInt2, paramInt3, paramObject);
  }
  
  public abstract void setQuick(int paramInt1, int paramInt2, int paramInt3, Object paramObject);
  
  public Object[][][] toArray()
  {
    Object[][][] arrayOfObject = new Object[this.slices][this.rows][this.columns];
    int i = this.slices;
    i--;
    if (i >= 0)
    {
      Object[][] arrayOfObject1 = arrayOfObject[i];
      int j = this.rows;
      for (;;)
      {
        j--;
        if (j < 0) {
          break;
        }
        Object[] arrayOfObject2 = arrayOfObject1[j];
        int k = this.columns;
        for (;;)
        {
          k--;
          if (k < 0) {
            break;
          }
          arrayOfObject2[k] = getQuick(i, j, k);
        }
      }
    }
    return arrayOfObject;
  }
  
  public String toString()
  {
    return new Formatter().toString(this);
  }
  
  protected ObjectMatrix3D view()
  {
    return (ObjectMatrix3D)clone();
  }
  
  public ObjectMatrix2D viewColumn(int paramInt)
  {
    checkColumn(paramInt);
    int i = this.slices;
    int j = this.rows;
    int k = this.sliceZero;
    int m = this.rowZero + _columnOffset(_columnRank(paramInt));
    int n = this.sliceStride;
    int i1 = this.rowStride;
    return like2D(i, j, k, m, n, i1);
  }
  
  public ObjectMatrix3D viewColumnFlip()
  {
    return (ObjectMatrix3D)view().vColumnFlip();
  }
  
  public ObjectMatrix3D viewDice(int paramInt1, int paramInt2, int paramInt3)
  {
    return (ObjectMatrix3D)view().vDice(paramInt1, paramInt2, paramInt3);
  }
  
  public ObjectMatrix3D viewPart(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    return (ObjectMatrix3D)view().vPart(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
  }
  
  public ObjectMatrix2D viewRow(int paramInt)
  {
    checkRow(paramInt);
    int i = this.slices;
    int j = this.columns;
    int k = this.sliceZero;
    int m = this.columnZero + _rowOffset(_rowRank(paramInt));
    int n = this.sliceStride;
    int i1 = this.columnStride;
    return like2D(i, j, k, m, n, i1);
  }
  
  public ObjectMatrix3D viewRowFlip()
  {
    return (ObjectMatrix3D)view().vRowFlip();
  }
  
  public ObjectMatrix3D viewSelection(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3)
  {
    int i;
    if (paramArrayOfInt1 == null)
    {
      paramArrayOfInt1 = new int[this.slices];
      i = this.slices;
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
      paramArrayOfInt2 = new int[this.rows];
      i = this.rows;
      for (;;)
      {
        i--;
        if (i < 0) {
          break;
        }
        paramArrayOfInt2[i] = i;
      }
    }
    if (paramArrayOfInt3 == null)
    {
      paramArrayOfInt3 = new int[this.columns];
      i = this.columns;
      for (;;)
      {
        i--;
        if (i < 0) {
          break;
        }
        paramArrayOfInt3[i] = i;
      }
    }
    checkSliceIndexes(paramArrayOfInt1);
    checkRowIndexes(paramArrayOfInt2);
    checkColumnIndexes(paramArrayOfInt3);
    int[] arrayOfInt1 = new int[paramArrayOfInt1.length];
    int[] arrayOfInt2 = new int[paramArrayOfInt2.length];
    int[] arrayOfInt3 = new int[paramArrayOfInt3.length];
    int j = paramArrayOfInt1.length;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      arrayOfInt1[j] = _sliceOffset(_sliceRank(paramArrayOfInt1[j]));
    }
    j = paramArrayOfInt2.length;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      arrayOfInt2[j] = _rowOffset(_rowRank(paramArrayOfInt2[j]));
    }
    j = paramArrayOfInt3.length;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      arrayOfInt3[j] = _columnOffset(_columnRank(paramArrayOfInt3[j]));
    }
    return viewSelectionLike(arrayOfInt1, arrayOfInt2, arrayOfInt3);
  }
  
  public ObjectMatrix3D viewSelection(ObjectMatrix2DProcedure paramObjectMatrix2DProcedure)
  {
    IntArrayList localIntArrayList = new IntArrayList();
    for (int i = 0; i < this.slices; i++) {
      if (paramObjectMatrix2DProcedure.apply(viewSlice(i))) {
        localIntArrayList.add(i);
      }
    }
    localIntArrayList.trimToSize();
    return viewSelection(localIntArrayList.elements(), null, null);
  }
  
  protected abstract ObjectMatrix3D viewSelectionLike(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3);
  
  public ObjectMatrix2D viewSlice(int paramInt)
  {
    checkSlice(paramInt);
    int i = this.rows;
    int j = this.columns;
    int k = this.rowZero;
    int m = this.columnZero + _sliceOffset(_sliceRank(paramInt));
    int n = this.rowStride;
    int i1 = this.columnStride;
    return like2D(i, j, k, m, n, i1);
  }
  
  public ObjectMatrix3D viewSliceFlip()
  {
    return (ObjectMatrix3D)view().vSliceFlip();
  }
  
  public ObjectMatrix3D viewSorted(int paramInt1, int paramInt2)
  {
    return Sorting.mergeSort.sort(this, paramInt1, paramInt2);
  }
  
  public ObjectMatrix3D viewStrides(int paramInt1, int paramInt2, int paramInt3)
  {
    return (ObjectMatrix3D)view().vStrides(paramInt1, paramInt2, paramInt3);
  }
  
  private boolean xforEach(ObjectProcedure paramObjectProcedure)
  {
    int i = this.slices;
    label5:
    i--;
    if (i >= 0)
    {
      int j;
      int k;
      label30:
      do
      {
        j = this.rows;
        j--;
        break label30;
        if (j < 0) {
          break label5;
        }
        k = this.columns;
        k--;
        if (k < 0) {
          break;
        }
      } while (paramObjectProcedure.apply(getQuick(i, j, k)));
      return false;
    }
    return true;
  }
  
  private boolean xforEachCoordinate(IntIntIntProcedure paramIntIntIntProcedure)
  {
    int i = this.columns;
    label5:
    i--;
    if (i >= 0)
    {
      int j;
      int k;
      label30:
      do
      {
        j = this.slices;
        j--;
        break label30;
        if (j < 0) {
          break label5;
        }
        k = this.rows;
        k--;
        if (k < 0) {
          break;
        }
      } while (paramIntIntIntProcedure.apply(j, k, i));
      return false;
    }
    return true;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.ObjectMatrix3D
 * JD-Core Version:    0.7.0.1
 */