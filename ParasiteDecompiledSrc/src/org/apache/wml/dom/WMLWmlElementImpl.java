package org.apache.wml.dom;

import org.apache.wml.WMLWmlElement;
import org.apache.xerces.dom.ElementImpl;

public class WMLWmlElementImpl
  extends WMLElementImpl
  implements WMLWmlElement
{
  private static final long serialVersionUID = -7008023851895920651L;
  
  public WMLWmlElementImpl(WMLDocumentImpl paramWMLDocumentImpl, String paramString)
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
  
  public void setXmlLang(String paramString)
  {
    setAttribute("xml:lang", paramString);
  }
  
  public String getXmlLang()
  {
    return getAttribute("xml:lang");
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
 * Qualified Name:     org.apache.wml.dom.WMLWmlElementImpl
 * JD-Core Version:    0.7.0.1
 */