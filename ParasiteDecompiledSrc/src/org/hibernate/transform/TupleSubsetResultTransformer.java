package org.hibernate.transform;

public abstract interface TupleSubsetResultTransformer
  extends ResultTransformer
{
  public abstract boolean isTransformedValueATupleElement(String[] paramArrayOfString, int paramInt);
  
  public abstract boolean[] includeInTransform(String[] paramArrayOfString, int paramInt);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.transform.TupleSubsetResultTransformer
 * JD-Core Version:    0.7.0.1
 */