package org.apache.xerces.impl.xs.util;

import org.apache.xerces.xs.XSObject;
import org.apache.xerces.xs.XSObjectList;

public class XSObjectListImpl
  implements XSObjectList
{
  public static final XSObjectList EMPTY_LIST = new XSObjectList()
  {
    public int getLength()
    {
      return 0;
    }
    
    public XSObject item(int paramAnonymousInt)
    {
      return null;
    }
  };
  private static final int DEFAULT_SIZE = 4;
  private XSObject[] fArray = null;
  private int fLength = 0;
  
  public XSObjectListImpl()
  {
    this.fArray = new XSObject[4];
    this.fLength = 0;
  }
  
  public XSObjectListImpl(XSObject[] paramArrayOfXSObject, int paramInt)
  {
    this.fArray = paramArrayOfXSObject;
    this.fLength = paramInt;
  }
  
  public int getLength()
  {
    return this.fLength;
  }
  
  public XSObject item(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this.fLength)) {
      return null;
    }
    return this.fArray[paramInt];
  }
  
  public void clear()
  {
    for (int i = 0; i < this.fLength; i++) {
      this.fArray[i] = null;
    }
    this.fArray = null;
    this.fLength = 0;
  }
  
  public void add(XSObject paramXSObject)
  {
    if (this.fLength == this.fArray.length)
    {
      XSObject[] arrayOfXSObject = new XSObject[this.fLength + 4];
      System.arraycopy(this.fArray, 0, arrayOfXSObject, 0, this.fLength);
      this.fArray = arrayOfXSObject;
    }
    this.fArray[(this.fLength++)] = paramXSObject;
  }
  
  public void add(int paramInt, XSObject paramXSObject)
  {
    this.fArray[paramInt] = paramXSObject;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.util.XSObjectListImpl
 * JD-Core Version:    0.7.0.1
 */