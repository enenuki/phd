package org.apache.html.dom;

import org.apache.xerces.dom.ElementImpl;
import org.w3c.dom.html.HTMLPreElement;

public class HTMLPreElementImpl
  extends HTMLElementImpl
  implements HTMLPreElement
{
  private static final long serialVersionUID = -4195360849946217644L;
  
  public int getWidth()
  {
    return getInteger(getAttribute("width"));
  }
  
  public void setWidth(int paramInt)
  {
    setAttribute("width", String.valueOf(paramInt));
  }
  
  public HTMLPreElementImpl(HTMLDocumentImpl paramHTMLDocumentImpl, String paramString)
  {
    super(paramHTMLDocumentImpl, paramString);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.html.dom.HTMLPreElementImpl
 * JD-Core Version:    0.7.0.1
 */