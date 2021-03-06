package org.apache.wml.dom;

import org.apache.wml.WMLAccessElement;
import org.apache.xerces.dom.ElementImpl;

public class WMLAccessElementImpl
  extends WMLElementImpl
  implements WMLAccessElement
{
  private static final long serialVersionUID = -235627181817012806L;
  
  public WMLAccessElementImpl(WMLDocumentImpl paramWMLDocumentImpl, String paramString)
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
  
  public void setDomain(String paramString)
  {
    setAttribute("domain", paramString);
  }
  
  public String getDomain()
  {
    return getAttribute("domain");
  }
  
  public void setId(String paramString)
  {
    setAttribute("id", paramString);
  }
  
  public String getId()
  {
    return getAttribute("id");
  }
  
  public void setPath(String paramString)
  {
    setAttribute("path", paramString);
  }
  
  public String getPath()
  {
    return getAttribute("path");
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.wml.dom.WMLAccessElementImpl
 * JD-Core Version:    0.7.0.1
 */