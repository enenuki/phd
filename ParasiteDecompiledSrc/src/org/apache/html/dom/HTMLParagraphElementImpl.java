package org.apache.html.dom;

import org.apache.xerces.dom.ElementImpl;
import org.w3c.dom.html.HTMLParagraphElement;

public class HTMLParagraphElementImpl
  extends HTMLElementImpl
  implements HTMLParagraphElement
{
  private static final long serialVersionUID = 8075287150683866287L;
  
  public String getAlign()
  {
    return getAttribute("align");
  }
  
  public void setAlign(String paramString)
  {
    setAttribute("align", paramString);
  }
  
  public HTMLParagraphElementImpl(HTMLDocumentImpl paramHTMLDocumentImpl, String paramString)
  {
    super(paramHTMLDocumentImpl, paramString);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.html.dom.HTMLParagraphElementImpl
 * JD-Core Version:    0.7.0.1
 */