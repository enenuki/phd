package org.apache.html.dom;

import org.apache.xerces.dom.ElementImpl;
import org.w3c.dom.html.HTMLHeadElement;

public class HTMLHeadElementImpl
  extends HTMLElementImpl
  implements HTMLHeadElement
{
  private static final long serialVersionUID = 6438668473721292232L;
  
  public String getProfile()
  {
    return getAttribute("profile");
  }
  
  public void setProfile(String paramString)
  {
    setAttribute("profile", paramString);
  }
  
  public HTMLHeadElementImpl(HTMLDocumentImpl paramHTMLDocumentImpl, String paramString)
  {
    super(paramHTMLDocumentImpl, paramString);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.html.dom.HTMLHeadElementImpl
 * JD-Core Version:    0.7.0.1
 */