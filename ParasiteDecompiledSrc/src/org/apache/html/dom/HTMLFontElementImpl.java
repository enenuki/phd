package org.apache.html.dom;

import org.apache.xerces.dom.ElementImpl;
import org.w3c.dom.html.HTMLFontElement;

public class HTMLFontElementImpl
  extends HTMLElementImpl
  implements HTMLFontElement
{
  private static final long serialVersionUID = -415914342045846318L;
  
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
  
  public HTMLFontElementImpl(HTMLDocumentImpl paramHTMLDocumentImpl, String paramString)
  {
    super(paramHTMLDocumentImpl, paramString);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.html.dom.HTMLFontElementImpl
 * JD-Core Version:    0.7.0.1
 */