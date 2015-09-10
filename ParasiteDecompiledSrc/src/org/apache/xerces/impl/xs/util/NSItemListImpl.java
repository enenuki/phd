package org.apache.xerces.impl.xs.util;

import java.util.Vector;
import org.apache.xerces.xs.XSNamespaceItem;
import org.apache.xerces.xs.XSNamespaceItemList;

public class NSItemListImpl
  implements XSNamespaceItemList
{
  private XSNamespaceItem[] fArray = null;
  private int fLength = 0;
  private Vector fVector;
  
  public NSItemListImpl(Vector paramVector)
  {
    this.fVector = paramVector;
    this.fLength = paramVector.size();
  }
  
  public NSItemListImpl(XSNamespaceItem[] paramArrayOfXSNamespaceItem, int paramInt)
  {
    this.fArray = paramArrayOfXSNamespaceItem;
    this.fLength = paramInt;
  }
  
  public int getLength()
  {
    return this.fLength;
  }
  
  public XSNamespaceItem item(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this.fLength)) {
      return null;
    }
    if (this.fVector != null) {
      return (XSNamespaceItem)this.fVector.elementAt(paramInt);
    }
    return this.fArray[paramInt];
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.util.NSItemListImpl
 * JD-Core Version:    0.7.0.1
 */