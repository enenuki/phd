package org.apache.html.dom;

import org.apache.xerces.dom.ElementImpl;
import org.w3c.dom.html.HTMLOListElement;

public class HTMLOListElementImpl
  extends HTMLElementImpl
  implements HTMLOListElement
{
  private static final long serialVersionUID = 1293750546025862146L;
  
  public boolean getCompact()
  {
    return getBinary("compact");
  }
  
  public void setCompact(boolean paramBoolean)
  {
    setAttribute("compact", paramBoolean);
  }
  
  public int getStart()
  {
    return getInteger(getAttribute("start"));
  }
  
  public void setStart(int paramInt)
  {
    setAttribute("start", String.valueOf(paramInt));
  }
  
  public String getType()
  {
    return getAttribute("type");
  }
  
  public void setType(String paramString)
  {
    setAttribute("type", paramString);
  }
  
  public HTMLOListElementImpl(HTMLDocumentImpl paramHTMLDocumentImpl, String paramString)
  {
    super(paramHTMLDocumentImpl, paramString);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.html.dom.HTMLOListElementImpl
 * JD-Core Version:    0.7.0.1
 */