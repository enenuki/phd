package org.apache.xerces.impl.xs.util;

import org.apache.xerces.xs.ShortList;
import org.apache.xerces.xs.XSException;

public class ShortListImpl
  implements ShortList
{
  public static final ShortList EMPTY_LIST = new ShortList()
  {
    public int getLength()
    {
      return 0;
    }
    
    public boolean contains(short paramAnonymousShort)
    {
      return false;
    }
    
    public short item(int paramAnonymousInt)
      throws XSException
    {
      throw new XSException((short)2, null);
    }
  };
  private short[] fArray = null;
  private int fLength = 0;
  
  public ShortListImpl(short[] paramArrayOfShort, int paramInt)
  {
    this.fArray = paramArrayOfShort;
    this.fLength = paramInt;
  }
  
  public int getLength()
  {
    return this.fLength;
  }
  
  public boolean contains(short paramShort)
  {
    for (int i = 0; i < this.fLength; i++) {
      if (this.fArray[i] == paramShort) {
        return true;
      }
    }
    return false;
  }
  
  public short item(int paramInt)
    throws XSException
  {
    if ((paramInt < 0) || (paramInt >= this.fLength)) {
      throw new XSException((short)2, null);
    }
    return this.fArray[paramInt];
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject == null) || (!(paramObject instanceof ShortList))) {
      return false;
    }
    ShortList localShortList = (ShortList)paramObject;
    if (this.fLength != localShortList.getLength()) {
      return false;
    }
    for (int i = 0; i < this.fLength; i++) {
      if (this.fArray[i] != localShortList.item(i)) {
        return false;
      }
    }
    return true;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.util.ShortListImpl
 * JD-Core Version:    0.7.0.1
 */