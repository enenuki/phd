package org.apache.xerces.impl.xs.opti;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class NamedNodeMapImpl
  implements NamedNodeMap
{
  Attr[] attrs;
  
  public NamedNodeMapImpl(Attr[] paramArrayOfAttr)
  {
    this.attrs = paramArrayOfAttr;
  }
  
  public Node getNamedItem(String paramString)
  {
    for (int i = 0; i < this.attrs.length; i++) {
      if (this.attrs[i].getName().equals(paramString)) {
        return this.attrs[i];
      }
    }
    return null;
  }
  
  public Node item(int paramInt)
  {
    if ((paramInt < 0) && (paramInt > getLength())) {
      return null;
    }
    return this.attrs[paramInt];
  }
  
  public int getLength()
  {
    return this.attrs.length;
  }
  
  public Node getNamedItemNS(String paramString1, String paramString2)
  {
    for (int i = 0; i < this.attrs.length; i++) {
      if ((this.attrs[i].getName().equals(paramString2)) && (this.attrs[i].getNamespaceURI().equals(paramString1))) {
        return this.attrs[i];
      }
    }
    return null;
  }
  
  public Node setNamedItemNS(Node paramNode)
    throws DOMException
  {
    throw new DOMException((short)9, "Method not supported");
  }
  
  public Node setNamedItem(Node paramNode)
    throws DOMException
  {
    throw new DOMException((short)9, "Method not supported");
  }
  
  public Node removeNamedItem(String paramString)
    throws DOMException
  {
    throw new DOMException((short)9, "Method not supported");
  }
  
  public Node removeNamedItemNS(String paramString1, String paramString2)
    throws DOMException
  {
    throw new DOMException((short)9, "Method not supported");
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.opti.NamedNodeMapImpl
 * JD-Core Version:    0.7.0.1
 */