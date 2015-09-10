package cern.colt;

import java.io.Serializable;

public abstract class PersistentObject
  implements Serializable, Cloneable
{
  public static final long serialVersionUID = 1020L;
  
  public Object clone()
  {
    try
    {
      return super.clone();
    }
    catch (CloneNotSupportedException localCloneNotSupportedException)
    {
      throw new InternalError();
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.PersistentObject
 * JD-Core Version:    0.7.0.1
 */