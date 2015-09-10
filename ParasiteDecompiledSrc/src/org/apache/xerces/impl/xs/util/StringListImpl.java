package org.apache.xerces.impl.xs.util;

import java.util.Vector;
import org.apache.xerces.xs.StringList;

public class StringListImpl
  implements StringList
{
  public static final StringList EMPTY_LIST = new StringList()
  {
    public int getLength()
    {
      return 0;
    }
    
    public boolean contains(String paramAnonymousString)
    {
      return false;
    }
    
    public String item(int paramAnonymousInt)
    {
      return null;
    }
  };
  private String[] fArray = null;
  private int fLength = 0;
  private Vector fVector;
  
  public StringListImpl(Vector paramVector)
  {
    this.fVector = paramVector;
    this.fLength = (paramVector == null ? 0 : paramVector.size());
  }
  
  public StringListImpl(String[] paramArrayOfString, int paramInt)
  {
    this.fArray = paramArrayOfString;
    this.fLength = paramInt;
  }
  
  public int getLength()
  {
    return this.fLength;
  }
  
  public boolean contains(String paramString)
  {
    if (this.fVector != null) {
      return this.fVector.contains(paramString);
    }
    int i;
    if (paramString == null) {
      for (i = 0; i < this.fLength; i++) {
        if (this.fArray[i] == null) {
          return true;
        }
      }
    } else {
      for (i = 0; i < this.fLength; i++) {
        if (paramString.equals(this.fArray[i])) {
          return true;
        }
      }
    }
    return false;
  }
  
  public String item(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this.fLength)) {
      return null;
    }
    if (this.fVector != null) {
      return (String)this.fVector.elementAt(paramInt);
    }
    return this.fArray[paramInt];
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.util.StringListImpl
 * JD-Core Version:    0.7.0.1
 */