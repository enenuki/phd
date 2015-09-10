package org.apache.html.dom;

import org.apache.xerces.dom.ElementImpl;
import org.w3c.dom.html.HTMLBodyElement;

public class HTMLBodyElementImpl
  extends HTMLElementImpl
  implements HTMLBodyElement
{
  private static final long serialVersionUID = 9058852459426595202L;
  
  public String getALink()
  {
    return getAttribute("alink");
  }
  
  public void setALink(String paramString)
  {
    setAttribute("alink", paramString);
  }
  
  public String getBackground()
  {
    return getAttribute("background");
  }
  
  public void setBackground(String paramString)
  {
    setAttribute("background", paramString);
  }
  
  public String getBgColor()
  {
    return getAttribute("bgcolor");
  }
  
  public void setBgColor(String paramString)
  {
    setAttribute("bgcolor", paramString);
  }
  
  public String getLink()
  {
    return getAttribute("link");
  }
  
  public void setLink(String paramString)
  {
    setAttribute("link", paramString);
  }
  
  public String getText()
  {
    return getAttribute("text");
  }
  
  public void setText(String paramString)
  {
    setAttribute("text", paramString);
  }
  
  public String getVLink()
  {
    return getAttribute("vlink");
  }
  
  public void setVLink(String paramString)
  {
    setAttribute("vlink", paramString);
  }
  
  public HTMLBodyElementImpl(HTMLDocumentImpl paramHTMLDocumentImpl, String paramString)
  {
    super(paramHTMLDocumentImpl, paramString);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.html.dom.HTMLBodyElementImpl
 * JD-Core Version:    0.7.0.1
 */