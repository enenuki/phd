package org.apache.html.dom;

import org.apache.xerces.dom.ElementImpl;
import org.w3c.dom.html.HTMLBaseFontElement;

public class HTMLBaseFontElementImpl
  extends HTMLElementImpl
  implements HTMLBaseFontElement
{
  private static final long serialVersionUID = -3650249921091097229L;
  
  public String getColor()
  {
    return capitalize(getAttribute("color"));
  }
  
  public void setColor(String paramString)
  {
    setAttribute("color", paramString);
  }
  
  public String getFace()
  {
    return capitalize(getAttribute("face"));
  }
  
  public void setFace(String paramString)
  {
    setAttribute("face", paramString);
  }
  
  public String getSize()
  {
    return getAttribute("size");
  }
  
  public void setSize(String paramString)
  {
    setAttribute("size", paramString);
  }
  
  public HTMLBaseFontElementImpl(HTMLDocumentImpl paramHTMLDocumentImpl, String paramString)
  {
    super(paramHTMLDocumentImpl, paramString);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.html.dom.HTMLBaseFontElementImpl
 * JD-Core Version:    0.7.0.1
 */