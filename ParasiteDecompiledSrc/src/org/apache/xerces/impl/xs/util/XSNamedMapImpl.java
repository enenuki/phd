package org.apache.xerces.impl.xs.util;

import org.apache.xerces.util.SymbolHash;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xs.XSNamedMap;
import org.apache.xerces.xs.XSObject;

public class XSNamedMapImpl
  implements XSNamedMap
{
  public static final XSNamedMap EMPTY_MAP = new XSNamedMap()
  {
    public int getLength()
    {
      return 0;
    }
    
    public XSObject itemByName(String paramAnonymousString1, String paramAnonymousString2)
    {
      return null;
    }
    
    public XSObject item(int paramAnonymousInt)
    {
      return null;
    }
  };
  String[] fNamespaces;
  int fNSNum;
  SymbolHash[] fMaps;
  XSObject[] fArray = null;
  int fLength = -1;
  QName fName = new QName();
  
  public XSNamedMapImpl(String paramString, SymbolHash paramSymbolHash)
  {
    this.fNamespaces = new String[] { paramString };
    this.fMaps = new SymbolHash[] { paramSymbolHash };
    this.fNSNum = 1;
  }
  
  public XSNamedMapImpl(String[] paramArrayOfString, SymbolHash[] paramArrayOfSymbolHash, int paramInt)
  {
    this.fNamespaces = paramArrayOfString;
    this.fMaps = paramArrayOfSymbolHash;
    this.fNSNum = paramInt;
  }
  
  public XSNamedMapImpl(XSObject[] paramArrayOfXSObject, int paramInt)
  {
    if (paramInt == 0)
    {
      this.fNSNum = 0;
      this.fLength = 0;
      return;
    }
    this.fNamespaces = new String[] { paramArrayOfXSObject[0].getNamespace() };
    this.fMaps = null;
    this.fNSNum = 1;
    this.fArray = paramArrayOfXSObject;
    this.fLength = paramInt;
  }
  
  public synchronized int getLength()
  {
    if (this.fLength == -1)
    {
      this.fLength = 0;
      for (int i = 0; i < this.fNSNum; i++) {
        this.fLength += this.fMaps[i].getLength();
      }
    }
    return this.fLength;
  }
  
  public XSObject itemByName(String paramString1, String paramString2)
  {
    for (int i = 0; i < this.fNSNum; i++) {
      if (isEqual(paramString1, this.fNamespaces[i]))
      {
        if (this.fMaps != null) {
          return (XSObject)this.fMaps[i].get(paramString2);
        }
        for (int j = 0; j < this.fLength; j++)
        {
          XSObject localXSObject = this.fArray[j];
          if (localXSObject.getName().equals(paramString2)) {
            return localXSObject;
          }
        }
        return null;
      }
    }
    return null;
  }
  
  public synchronized XSObject item(int paramInt)
  {
    if (this.fArray == null)
    {
      getLength();
      this.fArray = new XSObject[this.fLength];
      int i = 0;
      for (int j = 0; j < this.fNSNum; j++) {
        i += this.fMaps[j].getValues(this.fArray, i);
      }
    }
    if ((paramInt < 0) || (paramInt >= this.fLength)) {
      return null;
    }
    return this.fArray[paramInt];
  }
  
  final boolean isEqual(String paramString1, String paramString2)
  {
    return paramString2 == null ? true : paramString1 != null ? paramString1.equals(paramString2) : false;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.util.XSNamedMapImpl
 * JD-Core Version:    0.7.0.1
 */