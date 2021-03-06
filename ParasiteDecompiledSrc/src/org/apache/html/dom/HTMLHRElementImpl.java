package org.apache.html.dom;

import org.apache.xerces.dom.ElementImpl;
import org.w3c.dom.html.HTMLHRElement;

public class HTMLHRElementImpl
  extends HTMLElementImpl
  implements HTMLHRElement
{
  private static final long serialVersionUID = -4210053417678939270L;
  
  public String getAlign()
  {
    return capitalize(getAttribute("align"));
  }
  
  public void setAlign(String paramString)
  {
    setAttribute("align", paramString);
  }
  
  public boolean getNoShade()
  {
    return getBinary("noshade");
  }
  
  public void setNoShade(boolean paramBoolean)
  {
    setAttribute("noshade", paramBoolean);
  }
  
  public String getSize()
  {
    return getAttribute("size");
  }
  
  public void setSize(String paramString)
  {
    setAttribute("size", paramString);
  }
  
  public String getWidth()
  {
    return getAttribute("width");
  }
  
  public void setWidth(String paramString)
  {
    setAttribute("width", paramString);
  }
  
  public HTMLHRElementImpl(HTMLDocumentImpl paramHTMLDocumentImpl, String paramString)
  {
    super(paramHTMLDocumentImpl, paramString);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.html.dom.HTMLHRElementImpl
 * JD-Core Version:    0.7.0.1
 */