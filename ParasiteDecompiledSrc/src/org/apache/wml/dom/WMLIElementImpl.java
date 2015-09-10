package org.apache.wml.dom;

import org.apache.wml.WMLIElement;
import org.apache.xerces.dom.ElementImpl;

public class WMLIElementImpl
  extends WMLElementImpl
  implements WMLIElement
{
  private static final long serialVersionUID = 5008873415065802109L;
  
  public WMLIElementImpl(WMLDocumentImpl paramWMLDocumentImpl, String paramString)
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
 * Qualified Name:     org.apache.wml.dom.WMLIElementImpl
 * JD-Core Version:    0.7.0.1
 */