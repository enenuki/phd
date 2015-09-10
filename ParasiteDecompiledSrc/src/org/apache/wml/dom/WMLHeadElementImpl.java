package org.apache.wml.dom;

import org.apache.wml.WMLHeadElement;
import org.apache.xerces.dom.ElementImpl;

public class WMLHeadElementImpl
  extends WMLElementImpl
  implements WMLHeadElement
{
  private static final long serialVersionUID = 3311307374813188908L;
  
  public WMLHeadElementImpl(WMLDocumentImpl paramWMLDocumentImpl, String paramString)
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
 * Qualified Name:     org.apache.wml.dom.WMLHeadElementImpl
 * JD-Core Version:    0.7.0.1
 */