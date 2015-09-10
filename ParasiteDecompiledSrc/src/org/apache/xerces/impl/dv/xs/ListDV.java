package org.apache.xerces.impl.dv.xs;

import org.apache.xerces.impl.dv.InvalidDatatypeValueException;
import org.apache.xerces.impl.dv.ValidationContext;
import org.apache.xerces.xs.datatypes.ObjectList;

public class ListDV
  extends TypeValidator
{
  public short getAllowedFacets()
  {
    return 2079;
  }
  
  public Object getActualValue(String paramString, ValidationContext paramValidationContext)
    throws InvalidDatatypeValueException
  {
    return paramString;
  }
  
  public int getDataLength(Object paramObject)
  {
    return ((ListData)paramObject).getLength();
  }
  
  static final class ListData
    implements ObjectList
  {
    final Object[] data;
    private String canonical;
    
    public ListData(Object[] paramArrayOfObject)
    {
      this.data = paramArrayOfObject;
    }
    
    public synchronized String toString()
    {
      if (this.canonical == null)
      {
        int i = this.data.length;
        StringBuffer localStringBuffer = new StringBuffer();
        if (i > 0) {
          localStringBuffer.append(this.data[0].toString());
        }
        for (int j = 1; j < i; j++)
        {
          localStringBuffer.append(' ');
          localStringBuffer.append(this.data[j].toString());
        }
        this.canonical = localStringBuffer.toString();
      }
      return this.canonical;
    }
    
    public int getLength()
    {
      return this.data.length;
    }
    
    public boolean equals(Object paramObject)
    {
      if (!(paramObject instanceof ListData)) {
        return false;
      }
      Object[] arrayOfObject = ((ListData)paramObject).data;
      int i = this.data.length;
      if (i != arrayOfObject.length) {
        return false;
      }
      for (int j = 0; j < i; j++) {
        if (!this.data[j].equals(arrayOfObject[j])) {
          return false;
        }
      }
      return true;
    }
    
    public int hashCode()
    {
      int i = 0;
      for (int j = 0; j < this.data.length; j++) {
        i ^= this.data[j].hashCode();
      }
      return i;
    }
    
    public boolean contains(Object paramObject)
    {
      for (int i = 0; i < this.data.length; i++) {
        if (paramObject == this.data[i]) {
          return true;
        }
      }
      return false;
    }
    
    public Object item(int paramInt)
    {
      if ((paramInt < 0) || (paramInt >= this.data.length)) {
        return null;
      }
      return this.data[paramInt];
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.dv.xs.ListDV
 * JD-Core Version:    0.7.0.1
 */