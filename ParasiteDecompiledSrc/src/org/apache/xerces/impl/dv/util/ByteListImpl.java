package org.apache.xerces.impl.dv.util;

import org.apache.xerces.xs.XSException;
import org.apache.xerces.xs.datatypes.ByteList;

public class ByteListImpl
  implements ByteList
{
  protected final byte[] data;
  protected String canonical;
  
  public ByteListImpl(byte[] paramArrayOfByte)
  {
    this.data = paramArrayOfByte;
  }
  
  public int getLength()
  {
    return this.data.length;
  }
  
  public boolean contains(byte paramByte)
  {
    for (int i = 0; i < this.data.length; i++) {
      if (this.data[i] == paramByte) {
        return true;
      }
    }
    return false;
  }
  
  public byte item(int paramInt)
    throws XSException
  {
    if ((paramInt < 0) || (paramInt > this.data.length - 1)) {
      throw new XSException((short)2, null);
    }
    return this.data[paramInt];
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.dv.util.ByteListImpl
 * JD-Core Version:    0.7.0.1
 */