package org.apache.xerces.impl.xs.util;

import org.apache.xerces.util.SymbolHash;
import org.apache.xerces.xs.XSObject;
import org.apache.xerces.xs.XSTypeDefinition;

public class XSNamedMap4Types
  extends XSNamedMapImpl
{
  short fType;
  
  public XSNamedMap4Types(String paramString, SymbolHash paramSymbolHash, short paramShort)
  {
    super(paramString, paramSymbolHash);
    this.fType = paramShort;
  }
  
  public XSNamedMap4Types(String[] paramArrayOfString, SymbolHash[] paramArrayOfSymbolHash, int paramInt, short paramShort)
  {
    super(paramArrayOfString, paramArrayOfSymbolHash, paramInt);
    this.fType = paramShort;
  }
  
  public synchronized int getLength()
  {
    if (this.fLength == -1)
    {
      int i = 0;
      for (int j = 0; j < this.fNSNum; j++) {
        i += this.fMaps[j].getLength();
      }
      int k = 0;
      XSObject[] arrayOfXSObject = new XSObject[i];
      for (int m = 0; m < this.fNSNum; m++) {
        k += this.fMaps[m].getValues(arrayOfXSObject, k);
      }
      this.fLength = 0;
      this.fArray = new XSObject[i];
      for (int n = 0; n < i; n++)
      {
        XSTypeDefinition localXSTypeDefinition = (XSTypeDefinition)arrayOfXSObject[n];
        if (localXSTypeDefinition.getTypeCategory() == this.fType) {
          this.fArray[(this.fLength++)] = localXSTypeDefinition;
        }
      }
    }
    return this.fLength;
  }
  
  public XSObject itemByName(String paramString1, String paramString2)
  {
    for (int i = 0; i < this.fNSNum; i++) {
      if (isEqual(paramString1, this.fNamespaces[i]))
      {
        XSTypeDefinition localXSTypeDefinition = (XSTypeDefinition)this.fMaps[i].get(paramString2);
        if (localXSTypeDefinition.getTypeCategory() == this.fType) {
          return localXSTypeDefinition;
        }
        return null;
      }
    }
    return null;
  }
  
  public synchronized XSObject item(int paramInt)
  {
    if (this.fArray == null) {
      getLength();
    }
    if ((paramInt < 0) || (paramInt >= this.fLength)) {
      return null;
    }
    return this.fArray[paramInt];
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.util.XSNamedMap4Types
 * JD-Core Version:    0.7.0.1
 */