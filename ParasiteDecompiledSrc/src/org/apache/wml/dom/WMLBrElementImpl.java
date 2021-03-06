package org.apache.wml.dom;

import org.apache.wml.WMLBrElement;
import org.apache.xerces.dom.ElementImpl;

public class WMLBrElementImpl
  extends WMLElementImpl
  implements WMLBrElement
{
  private static final long serialVersionUID = -5047802409550691268L;
  
  public WMLBrElementImpl(WMLDocumentImpl paramWMLDocumentImpl, String paramString)
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
 * Qualified Name:     org.apache.wml.dom.WMLBrElementImpl
 * JD-Core Version:    0.7.0.1
 */