package org.hibernate.transform;

import java.io.Serializable;
import java.util.List;

public abstract interface ResultTransformer
  extends Serializable
{
  public abstract Object transformTuple(Object[] paramArrayOfObject, String[] paramArrayOfString);
  
  public abstract List transformList(List paramList);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.transform.ResultTransformer
 * JD-Core Version:    0.7.0.1
 */