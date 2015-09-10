package org.apache.wml.dom;

import org.apache.wml.WMLOptionElement;
import org.apache.xerces.dom.ElementImpl;

public class WMLOptionElementImpl
  extends WMLElementImpl
  implements WMLOptionElement
{
  private static final long serialVersionUID = -3432299264888771937L;
  
  public WMLOptionElementImpl(WMLDocumentImpl paramWMLDocumentImpl, String paramString)
  {
    super(paramWMLDocumentImpl, paramString);
  }
  
  public void setValue(String paramString)
  {
    setAttribute("value", paramString);
  }
  
  public String getValue()
  {
    return getAttribute("value");
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
  
  public void setOnPick(String paramString)
  {
    setAttribute("onpick", paramString);
  }
  
  public String getOnPick()
  {
    return getAttribute("onpick");
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.wml.dom.WMLOptionElementImpl
 * JD-Core Version:    0.7.0.1
 */