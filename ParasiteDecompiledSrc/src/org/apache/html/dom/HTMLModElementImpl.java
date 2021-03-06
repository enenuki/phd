package org.apache.html.dom;

import org.apache.xerces.dom.ElementImpl;
import org.w3c.dom.html.HTMLModElement;

public class HTMLModElementImpl
  extends HTMLElementImpl
  implements HTMLModElement
{
  private static final long serialVersionUID = 6424581972706750120L;
  
  public String getCite()
  {
    return getAttribute("cite");
  }
  
  public void setCite(String paramString)
  {
    setAttribute("cite", paramString);
  }
  
  public String getDateTime()
  {
    return getAttribute("datetime");
  }
  
  public void setDateTime(String paramString)
  {
    setAttribute("datetime", paramString);
  }
  
  public HTMLModElementImpl(HTMLDocumentImpl paramHTMLDocumentImpl, String paramString)
  {
    super(paramHTMLDocumentImpl, paramString);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.html.dom.HTMLModElementImpl
 * JD-Core Version:    0.7.0.1
 */