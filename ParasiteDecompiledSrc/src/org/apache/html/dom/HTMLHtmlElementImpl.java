package org.apache.html.dom;

import org.apache.xerces.dom.ElementImpl;
import org.w3c.dom.html.HTMLHtmlElement;

public class HTMLHtmlElementImpl
  extends HTMLElementImpl
  implements HTMLHtmlElement
{
  private static final long serialVersionUID = -4489734201536616166L;
  
  public String getVersion()
  {
    return capitalize(getAttribute("version"));
  }
  
  public void setVersion(String paramString)
  {
    setAttribute("version", paramString);
  }
  
  public HTMLHtmlElementImpl(HTMLDocumentImpl paramHTMLDocumentImpl, String paramString)
  {
    super(paramHTMLDocumentImpl, paramString);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.html.dom.HTMLHtmlElementImpl
 * JD-Core Version:    0.7.0.1
 */