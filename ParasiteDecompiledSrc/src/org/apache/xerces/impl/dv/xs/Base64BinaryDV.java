package org.apache.xerces.impl.dv.xs;

import org.apache.xerces.impl.dv.InvalidDatatypeValueException;
import org.apache.xerces.impl.dv.ValidationContext;
import org.apache.xerces.impl.dv.util.Base64;
import org.apache.xerces.impl.dv.util.ByteListImpl;

public class Base64BinaryDV
  extends TypeValidator
{
  public short getAllowedFacets()
  {
    return 2079;
  }
  
  public Object getActualValue(String paramString, ValidationContext paramValidationContext)
    throws InvalidDatatypeValueException
  {
    byte[] arrayOfByte = Base64.decode(paramString);
    if (arrayOfByte == null) {
      throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[] { paramString, "base64Binary" });
    }
    return new XBase64(arrayOfByte);
  }
  
  public int getDataLength(Object paramObject)
  {
    return ((XBase64)paramObject).getLength();
  }
  
  private static final class XBase64
    extends ByteListImpl
  {
    public XBase64(byte[] paramArrayOfByte)
    {
      super();
    }
    
    public synchronized String toString()
    {
      if (this.canonical == null) {
        this.canonical = Base64.encode(this.data);
      }
      return this.canonical;
    }
    
    public boolean equals(Object paramObject)
    {
      if (!(paramObject instanceof XBase64)) {
        return false;
      }
      byte[] arrayOfByte = ((XBase64)paramObject).data;
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
 * Qualified Name:     org.apache.xerces.impl.dv.xs.Base64BinaryDV
 * JD-Core Version:    0.7.0.1
 */