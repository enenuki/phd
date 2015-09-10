package cern.colt.matrix;

import cern.colt.PersistentObject;
import cern.colt.matrix.impl.DenseObjectMatrix3D;
import cern.colt.matrix.impl.SparseObjectMatrix3D;

public class ObjectFactory3D
  extends PersistentObject
{
  public static final ObjectFactory3D dense = new ObjectFactory3D();
  public static final ObjectFactory3D sparse = new ObjectFactory3D();
  
  public ObjectMatrix3D make(Object[][][] paramArrayOfObject)
  {
    if (this == sparse) {
      return new SparseObjectMatrix3D(paramArrayOfObject);
    }
    return new DenseObjectMatrix3D(paramArrayOfObject);
  }
  
  public ObjectMatrix3D make(int paramInt1, int paramInt2, int paramInt3)
  {
    if (this == sparse) {
      return new SparseObjectMatrix3D(paramInt1, paramInt2, paramInt3);
    }
    return new DenseObjectMatrix3D(paramInt1, paramInt2, paramInt3);
  }
  
  public ObjectMatrix3D make(int paramInt1, int paramInt2, int paramInt3, Object paramObject)
  {
    return make(paramInt1, paramInt2, paramInt3).assign(paramObject);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.ObjectFactory3D
 * JD-Core Version:    0.7.0.1
 */