package org.apache.wml.dom;

import org.apache.wml.WMLTrElement;
import org.apache.xerces.dom.ElementImpl;

public class WMLTrElementImpl
  extends WMLElementImpl
  implements WMLTrElement
{
  private static final long serialVersionUID = -4304021232051604343L;
  
  public WMLTrElementImpl(WMLDocumentImpl paramWMLDocumentImpl, String paramString)
  {
    super(paramWMLDocumentImpl, paramString);
  }
  
  public void setClassName(String paramString)
  {
    setAttribute("class", paramString);
  }
  
  public String getClassName()
  {
    return getAttribute("class");
  }
  
  public void setId(String paramString)
  {
    setAttribute("id", paramString);
  }
  
  public String getId()
  {
    return getAttribute("id");
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.wml.dom.WMLTrElementImpl
 * JD-Core Version:    0.7.0.1
 */