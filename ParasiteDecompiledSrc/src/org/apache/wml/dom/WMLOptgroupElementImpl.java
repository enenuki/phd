package org.apache.wml.dom;

import org.apache.wml.WMLOptgroupElement;
import org.apache.xerces.dom.ElementImpl;

public class WMLOptgroupElementImpl
  extends WMLElementImpl
  implements WMLOptgroupElement
{
  private static final long serialVersionUID = 1592761119479339142L;
  
  public WMLOptgroupElementImpl(WMLDocumentImpl paramWMLDocumentImpl, String paramString)
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
 * Qualified Name:     org.apache.wml.dom.WMLOptgroupElementImpl
 * JD-Core Version:    0.7.0.1
 */