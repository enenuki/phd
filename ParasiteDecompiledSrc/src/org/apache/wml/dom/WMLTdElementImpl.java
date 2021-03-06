package org.apache.wml.dom;

import org.apache.wml.WMLTdElement;
import org.apache.xerces.dom.ElementImpl;

public class WMLTdElementImpl
  extends WMLElementImpl
  implements WMLTdElement
{
  private static final long serialVersionUID = 6074218675876025710L;
  
  public WMLTdElementImpl(WMLDocumentImpl paramWMLDocumentImpl, String paramString)
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
 * Qualified Name:     org.apache.wml.dom.WMLTdElementImpl
 * JD-Core Version:    0.7.0.1
 */