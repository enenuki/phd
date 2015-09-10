package org.apache.xerces.impl.dv.xs;

import org.apache.xerces.impl.dv.InvalidDatatypeValueException;
import org.apache.xerces.impl.dv.ValidationContext;
import org.apache.xerces.impl.dv.util.ByteListImpl;
import org.apache.xerces.impl.dv.util.HexBin;

public class HexBinaryDV
  extends TypeValidator
{
  public short getAllowedFacets()
  {
    return 2079;
  }
  
  public Object getActualValue(String paramString, ValidationContext paramValidationContext)
    throws InvalidDatatypeValueException
  {
    byte[] arrayOfByte = HexBin.decode(paramString);
    if (arrayOfByte == null) {
      throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[] { paramString, "hexBinary" });
    }
    return new XHex(arrayOfByte);
  }
  
  public int getDataLength(Object paramObject)
  {
    return ((XHex)paramObject).getLength();
  }
  
  private static final class XHex
    extends ByteListImpl
  {
    public XHex(byte[] paramArrayOfByte)
    {
      super();
    }
    
    public synchronized String toString()
    {
      if (this.canonical == null) {
        this.canonical = HexBin.encode(this.data);
      }
      return this.canonical;
    }
    
    public boolean equals(Object paramObject)
    {
      if (!(paramObject instanceof XHex)) {
        return false;
      }
      byte[] arrayOfByte = ((XHex)paramObject).data;
      int i = this.data.length;
      if (i != arrayOfByte.length) {
        return false;
      }
      for (int j = 0; j < i; j++) {
        if (this.data[j] != arrayOfByte[j]) {
          return false;
        }
      }
      return true;
    }
    
    public int hashCode()
    {
      int i = 0;
      for (int j = 0; j < this.data.length; j++) {
        i = i * 37 + (this.data[j] & 0xFF);
      }
      return i;
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.dv.xs.HexBinaryDV
 * JD-Core Version:    0.7.0.1
 */