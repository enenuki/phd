package cern.colt.matrix;

import cern.colt.PersistentObject;
import cern.colt.list.ObjectArrayList;
import cern.colt.matrix.impl.DenseObjectMatrix1D;
import cern.colt.matrix.impl.SparseObjectMatrix1D;

public class ObjectFactory1D
  extends PersistentObject
{
  public static final ObjectFactory1D dense = new ObjectFactory1D();
  public static final ObjectFactory1D sparse = new ObjectFactory1D();
  
  public ObjectMatrix1D append(ObjectMatrix1D paramObjectMatrix1D1, ObjectMatrix1D paramObjectMatrix1D2)
  {
    ObjectMatrix1D localObjectMatrix1D = make(paramObjectMatrix1D1.size() + paramObjectMatrix1D2.size());
    localObjectMatrix1D.viewPart(0, paramObjectMatrix1D1.size()).assign(paramObjectMatrix1D1);
    localObjectMatrix1D.viewPart(paramObjectMatrix1D1.size(), paramObjectMatrix1D2.size()).assign(paramObjectMatrix1D2);
    return localObjectMatrix1D;
  }
  
  public ObjectMatrix1D make(ObjectMatrix1D[] paramArrayOfObjectMatrix1D)
  {
    if (paramArrayOfObjectMatrix1D.length == 0) {
      return make(0);
    }
    int i = 0;
    for (int j = 0; j < paramArrayOfObjectMatrix1D.length; j++) {
      i += paramArrayOfObjectMatrix1D[j].size();
    }
    ObjectMatrix1D localObjectMatrix1D = make(i);
    i = 0;
    for (int k = 0; k < paramArrayOfObjectMatrix1D.length; k++)
    {
      localObjectMatrix1D.viewPart(i, paramArrayOfObjectMatrix1D[k].size()).assign(paramArrayOfObjectMatrix1D[k]);
      i += paramArrayOfObjectMatrix1D[k].size();
    }
    return localObjectMatrix1D;
  }
  
  public ObjectMatrix1D make(Object[] paramArrayOfObject)
  {
    if (this == sparse) {
      return new SparseObjectMatrix1D(paramArrayOfObject);
    }
    return new DenseObjectMatrix1D(paramArrayOfObject);
  }
  
  public ObjectMatrix1D make(int paramInt)
  {
    if (this == sparse) {
      return new SparseObjectMatrix1D(paramInt);
    }
    return new DenseObjectMatrix1D(paramInt);
  }
  
  public ObjectMatrix1D make(int paramInt, Object paramObject)
  {
    return make(paramInt).assign(paramObject);
  }
  
  public ObjectMatrix1D make(ObjectArrayList paramObjectArrayList)
  {
    int i = paramObjectArrayList.size();
    ObjectMatrix1D localObjectMatrix1D = make(i);
    int j = i;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      localObjectMatrix1D.set(j, paramObjectArrayList.get(j));
    }
    return localObjectMatrix1D;
  }
  
  public ObjectMatrix1D repeat(ObjectMatrix1D paramObjectMatrix1D, int paramInt)
  {
    int i = paramObjectMatrix1D.size();
    ObjectMatrix1D localObjectMatrix1D = make(paramInt * i);
    int j = paramInt;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      localObjectMatrix1D.viewPart(i * j, i).assign(paramObjectMatrix1D);
    }
    return localObjectMatrix1D;
  }
  
  public ObjectArrayList toList(ObjectMatrix1D paramObjectMatrix1D)
  {
    int i = paramObjectMatrix1D.size();
    ObjectArrayList localObjectArrayList = new ObjectArrayList(i);
    localObjectArrayList.setSize(i);
    int j = i;
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      localObjectArrayList.set(j, paramObjectMatrix1D.get(j));
    }
    return localObjectArrayList;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.ObjectFactory1D
 * JD-Core Version:    0.7.0.1
 */