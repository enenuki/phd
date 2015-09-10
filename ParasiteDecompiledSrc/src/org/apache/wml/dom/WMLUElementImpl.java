package org.apache.wml.dom;

import org.apache.wml.WMLUElement;
import org.apache.xerces.dom.ElementImpl;

public class WMLUElementImpl
  extends WMLElementImpl
  implements WMLUElement
{
  private static final long serialVersionUID = 6350194387815102797L;
  
  public WMLUElementImpl(WMLDocumentImpl paramWMLDocumentImpl, String paramString)
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
 * Qualified Name:     org.apache.wml.dom.WMLUElementImpl
 * JD-Core Version:    0.7.0.1
 */