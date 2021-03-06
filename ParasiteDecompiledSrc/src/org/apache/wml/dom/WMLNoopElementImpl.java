package org.apache.wml.dom;

import org.apache.wml.WMLNoopElement;
import org.apache.xerces.dom.ElementImpl;

public class WMLNoopElementImpl
  extends WMLElementImpl
  implements WMLNoopElement
{
  private static final long serialVersionUID = -1581314434256075931L;
  
  public WMLNoopElementImpl(WMLDocumentImpl paramWMLDocumentImpl, String paramString)
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
 * Qualified Name:     org.apache.wml.dom.WMLNoopElementImpl
 * JD-Core Version:    0.7.0.1
 */