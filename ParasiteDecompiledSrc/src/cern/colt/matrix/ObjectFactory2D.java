package cern.colt.matrix;

import cern.colt.PersistentObject;
import cern.colt.matrix.impl.DenseObjectMatrix2D;
import cern.colt.matrix.impl.SparseObjectMatrix2D;

public class ObjectFactory2D
  extends PersistentObject
{
  public static final ObjectFactory2D dense = new ObjectFactory2D();
  public static final ObjectFactory2D sparse = new ObjectFactory2D();
  
  public ObjectMatrix2D appendColumns(ObjectMatrix2D paramObjectMatrix2D1, ObjectMatrix2D paramObjectMatrix2D2)
  {
    if (paramObjectMatrix2D2.rows() > paramObjectMatrix2D1.rows()) {
      paramObjectMatrix2D2 = paramObjectMatrix2D2.viewPart(0, 0, paramObjectMatrix2D1.rows(), paramObjectMatrix2D2.columns());
    } else if (paramObjectMatrix2D2.rows() < paramObjectMatrix2D1.rows()) {
      paramObjectMatrix2D1 = paramObjectMatrix2D1.viewPart(0, 0, paramObjectMatrix2D2.rows(), paramObjectMatrix2D1.columns());
    }
    int i = paramObjectMatrix2D1.columns();
    int j = paramObjectMatrix2D2.columns();
    int k = paramObjectMatrix2D1.rows();
    ObjectMatrix2D localObjectMatrix2D = make(k, i + j);
    localObjectMatrix2D.viewPart(0, 0, k, i).assign(paramObjectMatrix2D1);
    localObjectMatrix2D.viewPart(0, i, k, j).assign(paramObjectMatrix2D2);
    return localObjectMatrix2D;
  }
  
  public ObjectMatrix2D appendRows(ObjectMatrix2D paramObjectMatrix2D1, ObjectMatrix2D paramObjectMatrix2D2)
  {
    if (paramObjectMatrix2D2.columns() > paramObjectMatrix2D1.columns()) {
      paramObjectMatrix2D2 = paramObjectMatrix2D2.viewPart(0, 0, paramObjectMatrix2D2.rows(), paramObjectMatrix2D1.columns());
    } else if (paramObjectMatrix2D2.columns() < paramObjectMatrix2D1.columns()) {
      paramObjectMatrix2D1 = paramObjectMatrix2D1.viewPart(0, 0, paramObjectMatrix2D1.rows(), paramObjectMatrix2D2.columns());
    }
    int i = paramObjectMatrix2D1.rows();
    int j = paramObjectMatrix2D2.rows();
    int k = paramObjectMatrix2D1.columns();
    ObjectMatrix2D localObjectMatrix2D = make(i + j, k);
    localObjectMatrix2D.viewPart(0, 0, i, k).assign(paramObjectMatrix2D1);
    localObjectMatrix2D.viewPart(i, 0, j, k).assign(paramObjectMatrix2D2);
    return localObjectMatrix2D;
  }
  
  protected static void checkRectangularShape(ObjectMatrix2D[][] paramArrayOfObjectMatrix2D)
  {
    int i = -1;
    int j = paramArrayOfObjectMatrix2D.length;
    do
    {
      do
      {
        j--;
        if (j < 0) {
          break;
        }
      } while (paramArrayOfObjectMatrix2D[j] == null);
      if (i == -1) {
        i = paramArrayOfObjectMatrix2D[j].length;
      }
    } while (paramArrayOfObjectMatrix2D[j].length == i);
    throw new IllegalArgumentException("All rows of array must have same number of columns.");
  }
  
  protected static void checkRectangularShape(Object[][] paramArrayOfObject)
  {
    int i = -1;
    int j = paramArrayOfObject.length;
    do
    {
      do
      {
        j--;
        if (j < 0) {
          break;
        }
      } while (paramArrayOfObject[j] == null);
      if (i == -1) {
        i = paramArrayOfObject[j].length;
      }
    } while (paramArrayOfObject[j].length == i);
    throw new IllegalArgumentException("All rows of array must have same number of columns.");
  }
  
  public ObjectMatrix2D compose(ObjectMatrix2D[][] paramArrayOfObjectMatrix2D)
  {
    checkRectangularShape(paramArrayOfObjectMatrix2D);
    int i = paramArrayOfObjectMatrix2D.length;
    ObjectMatrix2D localObjectMatrix2D1 = 0;
    if (paramArrayOfObjectMatrix2D.length > 0) {
      localObjectMatrix2D1 = paramArrayOfObjectMatrix2D[0].length;
    }
    ObjectMatrix2D localObjectMatrix2D2 = make(0, 0);
    if ((i == 0) || (localObjectMatrix2D1 == 0)) {
      return localObjectMatrix2D2;
    }
    int[] arrayOfInt1 = new int[localObjectMatrix2D1];
    int j = localObjectMatrix2D1;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      k = 0;
      m = i;
      for (;;)
      {
        m--;
        if (m < 0) {
          break;
        }
        localObjectMatrix2D3 = paramArrayOfObjectMatrix2D[m][j];
        if (localObjectMatrix2D3 != null)
        {
          int n = localObjectMatrix2D3.columns();
          if ((k > 0) && (n > 0) && (n != k)) {
            throw new IllegalArgumentException("Different number of columns.");
          }
          k = Math.max(k, n);
        }
      }
      arrayOfInt1[j] = k;
    }
    int[] arrayOfInt2 = new int[i];
    int k = i;
    for (;;)
    {
      k--;
      if (k < 0) {
        break;
      }
      m = 0;
      localObjectMatrix2D3 = localObjectMatrix2D1;
      for (;;)
      {
        localObjectMatrix2D3--;
        if (localObjectMatrix2D3 < 0) {
          break;
        }
        ObjectMatrix2D localObjectMatrix2D4 = paramArrayOfObjectMatrix2D[k][localObjectMatrix2D3];
        if (localObjectMatrix2D4 != null)
        {
          i2 = localObjectMatrix2D4.rows();
          if ((m > 0) && (i2 > 0) && (i2 != m)) {
            throw new IllegalArgumentException("Different number of rows.");
          }
          m = Math.max(m, i2);
        }
      }
      arrayOfInt2[k] = m;
    }
    k = 0;
    int m = i;
    for (;;)
    {
      m--;
      if (m < 0) {
        break;
      }
      k += arrayOfInt2[m];
    }
    m = 0;
    ObjectMatrix2D localObjectMatrix2D3 = localObjectMatrix2D1;
    for (;;)
    {
      localObjectMatrix2D3--;
      if (localObjectMatrix2D3 < 0) {
        break;
      }
      m += arrayOfInt1[localObjectMatrix2D3];
    }
    localObjectMatrix2D3 = make(k, m);
    int i1 = 0;
    for (int i2 = 0; i2 < i; i2++)
    {
      int i3 = 0;
      for (int i4 = 0; i4 < localObjectMatrix2D1; i4++)
      {
        ObjectMatrix2D localObjectMatrix2D5 = paramArrayOfObjectMatrix2D[i2][i4];
        if (localObjectMatrix2D5 != null) {
          localObjectMatrix2D3.viewPart(i1, i3, localObjectMatrix2D5.rows(), localObjectMatrix2D5.columns()).assign(localObjectMatrix2D5);
        }
        i3 += arrayOfInt1[i4];
      }
      i1 += arrayOfInt2[i2];
    }
    return localObjectMatrix2D3;
  }
  
  public ObjectMatrix2D composeDiagonal(ObjectMatrix2D paramObjectMatrix2D1, ObjectMatrix2D paramObjectMatrix2D2)
  {
    int i = paramObjectMatrix2D1.rows();
    int j = paramObjectMatrix2D1.columns();
    int k = paramObjectMatrix2D2.rows();
    int m = paramObjectMatrix2D2.columns();
    ObjectMatrix2D localObjectMatrix2D = make(i + k, j + m);
    localObjectMatrix2D.viewPart(0, 0, i, j).assign(paramObjectMatrix2D1);
    localObjectMatrix2D.viewPart(i, j, k, m).assign(paramObjectMatrix2D2);
    return localObjectMatrix2D;
  }
  
  public ObjectMatrix2D composeDiagonal(ObjectMatrix2D paramObjectMatrix2D1, ObjectMatrix2D paramObjectMatrix2D2, ObjectMatrix2D paramObjectMatrix2D3)
  {
    ObjectMatrix2D localObjectMatrix2D = make(paramObjectMatrix2D1.rows() + paramObjectMatrix2D2.rows() + paramObjectMatrix2D3.rows(), paramObjectMatrix2D1.columns() + paramObjectMatrix2D2.columns() + paramObjectMatrix2D3.columns());
    localObjectMatrix2D.viewPart(0, 0, paramObjectMatrix2D1.rows(), paramObjectMatrix2D1.columns()).assign(paramObjectMatrix2D1);
    localObjectMatrix2D.viewPart(paramObjectMatrix2D1.rows(), paramObjectMatrix2D1.columns(), paramObjectMatrix2D2.rows(), paramObjectMatrix2D2.columns()).assign(paramObjectMatrix2D2);
    localObjectMatrix2D.viewPart(paramObjectMatrix2D1.rows() + paramObjectMatrix2D2.rows(), paramObjectMatrix2D1.columns() + paramObjectMatrix2D2.columns(), paramObjectMatrix2D3.rows(), paramObjectMatrix2D3.columns()).assign(paramObjectMatrix2D3);
    return localObjectMatrix2D;
  }
  
  public void decompose(ObjectMatrix2D[][] paramArrayOfObjectMatrix2D, ObjectMatrix2D paramObjectMatrix2D)
  {
    checkRectangularShape(paramArrayOfObjectMatrix2D);
    int i = paramArrayOfObjectMatrix2D.length;
    ObjectMatrix2D localObjectMatrix2D1 = 0;
    if (paramArrayOfObjectMatrix2D.length > 0) {
      localObjectMatrix2D1 = paramArrayOfObjectMatrix2D[0].length;
    }
    if ((i == 0) || (localObjectMatrix2D1 == 0)) {
      return;
    }
    int[] arrayOfInt1 = new int[localObjectMatrix2D1];
    int j = localObjectMatrix2D1;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      k = 0;
      m = i;
      for (;;)
      {
        m--;
        if (m < 0) {
          break;
        }
        localObjectMatrix2D2 = paramArrayOfObjectMatrix2D[m][j];
        if (localObjectMatrix2D2 != null)
        {
          int i1 = localObjectMatrix2D2.columns();
          if ((k > 0) && (i1 > 0) && (i1 != k)) {
            throw new IllegalArgumentException("Different number of columns.");
          }
          k = Math.max(k, i1);
        }
      }
      arrayOfInt1[j] = k;
    }
    int[] arrayOfInt2 = new int[i];
    int k = i;
    int i3;
    for (;;)
    {
      k--;
      if (k < 0) {
        break;
      }
      m = 0;
      localObjectMatrix2D2 = localObjectMatrix2D1;
      for (;;)
      {
        localObjectMatrix2D2--;
        if (localObjectMatrix2D2 < 0) {
          break;
        }
        ObjectMatrix2D localObjectMatrix2D3 = paramArrayOfObjectMatrix2D[k][localObjectMatrix2D2];
        if (localObjectMatrix2D3 != null)
        {
          i3 = localObjectMatrix2D3.rows();
          if ((m > 0) && (i3 > 0) && (i3 != m)) {
            throw new IllegalArgumentException("Different number of rows.");
          }
          m = Math.max(m, i3);
        }
      }
      arrayOfInt2[k] = m;
    }
    k = 0;
    int m = i;
    for (;;)
    {
      m--;
      if (m < 0) {
        break;
      }
      k += arrayOfInt2[m];
    }
    m = 0;
    ObjectMatrix2D localObjectMatrix2D2 = localObjectMatrix2D1;
    for (;;)
    {
      localObjectMatrix2D2--;
      if (localObjectMatrix2D2 < 0) {
        break;
      }
      m += arrayOfInt1[localObjectMatrix2D2];
    }
    if ((paramObjectMatrix2D.rows() < k) || (paramObjectMatrix2D.columns() < m)) {
      throw new IllegalArgumentException("Parts larger than matrix.");
    }
    int n = 0;
    for (int i2 = 0; i2 < i; i2++)
    {
      i3 = 0;
      for (int i4 = 0; i4 < localObjectMatrix2D1; i4++)
      {
        ObjectMatrix2D localObjectMatrix2D4 = paramArrayOfObjectMatrix2D[i2][i4];
        if (localObjectMatrix2D4 != null) {
          localObjectMatrix2D4.assign(paramObjectMatrix2D.viewPart(n, i3, localObjectMatrix2D4.rows(), localObjectMatrix2D4.columns()));
        }
        i3 += arrayOfInt1[i4];
      }
      n += arrayOfInt2[i2];
    }
  }
  
  public ObjectMatrix2D diagonal(ObjectMatrix1D paramObjectMatrix1D)
  {
    int i = paramObjectMatrix1D.size();
    ObjectMatrix2D localObjectMatrix2D = make(i, i);
    int j = i;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      localObjectMatrix2D.setQuick(j, j, paramObjectMatrix1D.getQuick(j));
    }
    return localObjectMatrix2D;
  }
  
  public ObjectMatrix1D diagonal(ObjectMatrix2D paramObjectMatrix2D)
  {
    int i = Math.min(paramObjectMatrix2D.rows(), paramObjectMatrix2D.columns());
    ObjectMatrix1D localObjectMatrix1D = make1D(i);
    int j = i;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      localObjectMatrix1D.setQuick(j, paramObjectMatrix2D.getQuick(j, j));
    }
    return localObjectMatrix1D;
  }
  
  public ObjectMatrix2D make(Object[][] paramArrayOfObject)
  {
    if (this == sparse) {
      return new SparseObjectMatrix2D(paramArrayOfObject);
    }
    return new DenseObjectMatrix2D(paramArrayOfObject);
  }
  
  public ObjectMatrix2D make(Object[] paramArrayOfObject, int paramInt)
  {
    int i = paramInt != 0 ? paramArrayOfObject.length / paramInt : 0;
    if (paramInt * i != paramArrayOfObject.length) {
      throw new IllegalArgumentException("Array length must be a multiple of m.");
    }
    ObjectMatrix2D localObjectMatrix2D = make(paramInt, i);
    for (int j = 0; j < paramInt; j++) {
      for (int k = 0; k < i; k++) {
        localObjectMatrix2D.setQuick(j, k, paramArrayOfObject[(j + k * paramInt)]);
      }
    }
    return localObjectMatrix2D;
  }
  
  public ObjectMatrix2D make(int paramInt1, int paramInt2)
  {
    if (this == sparse) {
      return new SparseObjectMatrix2D(paramInt1, paramInt2);
    }
    return new DenseObjectMatrix2D(paramInt1, paramInt2);
  }
  
  public ObjectMatrix2D make(int paramInt1, int paramInt2, Object paramObject)
  {
    if (paramObject == null) {
      return make(paramInt1, paramInt2);
    }
    return make(paramInt1, paramInt2).assign(paramObject);
  }
  
  protected ObjectMatrix1D make1D(int paramInt)
  {
    return make(0, 0).like1D(paramInt);
  }
  
  public ObjectMatrix2D repeat(ObjectMatrix2D paramObjectMatrix2D, int paramInt1, int paramInt2)
  {
    int i = paramObjectMatrix2D.rows();
    int j = paramObjectMatrix2D.columns();
    ObjectMatrix2D localObjectMatrix2D = make(i * paramInt1, j * paramInt2);
    int k = paramInt1;
    for (;;)
    {
      k--;
      if (k < 0) {
        break;
      }
      int m = paramInt2;
      for (;;)
      {
        m--;
        if (m < 0) {
          break;
        }
        localObjectMatrix2D.viewPart(i * k, j * m, i, j).assign(paramObjectMatrix2D);
      }
    }
    return localObjectMatrix2D;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.ObjectFactory2D
 * JD-Core Version:    0.7.0.1
 */