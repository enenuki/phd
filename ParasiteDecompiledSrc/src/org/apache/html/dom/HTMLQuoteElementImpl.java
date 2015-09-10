package org.apache.html.dom;

import org.apache.xerces.dom.ElementImpl;
import org.w3c.dom.html.HTMLQuoteElement;

public class HTMLQuoteElementImpl
  extends HTMLElementImpl
  implements HTMLQuoteElement
{
  private static final long serialVersionUID = -67544811597906132L;
  
  public String getCite()
  {
    return getAttribute("cite");
  }
  
  public void setCite(String paramString)
  {
    setAttribute("cite", paramString);
  }
  
  public HTMLQuoteElementImpl(HTMLDocumentImpl paramHTMLDocumentImpl, String paramString)
  {
    super(paramHTMLDocumentImpl, paramString);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.html.dom.HTMLQuoteElementImpl
 * JD-Core Version:    0.7.0.1
 */