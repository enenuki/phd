package org.apache.wml.dom;

import org.apache.wml.WMLRefreshElement;
import org.apache.xerces.dom.ElementImpl;

public class WMLRefreshElementImpl
  extends WMLElementImpl
  implements WMLRefreshElement
{
  private static final long serialVersionUID = 8781837880806459398L;
  
  public WMLRefreshElementImpl(WMLDocumentImpl paramWMLDocumentImpl, String paramString)
  {
    super(paramWMLDocumentImpl, paramString);
  }
  
  public void setClassName(String paramString)
  {
    setAttribute("class", paramString);
  }
  
  public String getClassName()
  {
    return getAttribute("class");
  }
  
  public void setId(String paramString)
  {
    setAttribute("id", paramString);
  }
  
  public String getId()
  {
    return getAttribute("id");
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.wml.dom.WMLRefreshElementImpl
 * JD-Core Version:    0.7.0.1
 */