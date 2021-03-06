package org.apache.html.dom;

import org.apache.xerces.dom.ElementImpl;
import org.w3c.dom.html.HTMLHeadingElement;

public class HTMLHeadingElementImpl
  extends HTMLElementImpl
  implements HTMLHeadingElement
{
  private static final long serialVersionUID = 6605827989383069095L;
  
  public String getAlign()
  {
    return getCapitalized("align");
  }
  
  public void setAlign(String paramString)
  {
    setAttribute("align", paramString);
  }
  
  public HTMLHeadingElementImpl(HTMLDocumentImpl paramHTMLDocumentImpl, String paramString)
  {
    super(paramHTMLDocumentImpl, paramString);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.html.dom.HTMLHeadingElementImpl
 * JD-Core Version:    0.7.0.1
 */