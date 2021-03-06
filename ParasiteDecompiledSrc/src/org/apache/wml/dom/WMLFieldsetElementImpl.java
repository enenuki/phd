package org.apache.wml.dom;

import org.apache.wml.WMLFieldsetElement;
import org.apache.xerces.dom.ElementImpl;

public class WMLFieldsetElementImpl
  extends WMLElementImpl
  implements WMLFieldsetElement
{
  private static final long serialVersionUID = 6941811812235840705L;
  
  public WMLFieldsetElementImpl(WMLDocumentImpl paramWMLDocumentImpl, String paramString)
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
  
  public void setTitle(String paramString)
  {
    setAttribute("title", paramString);
  }
  
  public String getTitle()
  {
    return getAttribute("title");
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
 * Qualified Name:     org.apache.wml.dom.WMLFieldsetElementImpl
 * JD-Core Version:    0.7.0.1
 */