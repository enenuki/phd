package org.apache.wml.dom;

import org.apache.wml.WMLMetaElement;
import org.apache.xerces.dom.ElementImpl;

public class WMLMetaElementImpl
  extends WMLElementImpl
  implements WMLMetaElement
{
  private static final long serialVersionUID = -2791663042188681846L;
  
  public WMLMetaElementImpl(WMLDocumentImpl paramWMLDocumentImpl, String paramString)
  {
    super(paramWMLDocumentImpl, paramString);
  }
  
  public void setForua(boolean paramBoolean)
  {
    setAttribute("forua", paramBoolean);
  }
  
  public boolean getForua()
  {
    return getAttribute("forua", false);
  }
  
  public void setScheme(String paramString)
  {
    setAttribute("scheme", paramString);
  }
  
  public String getScheme()
  {
    return getAttribute("scheme");
  }
  
  public void setClassName(String paramString)
  {
    setAttribute("class", paramString);
  }
  
  public String getClassName()
  {
    return getAttribute("class");
  }
  
  public void setHttpEquiv(String paramString)
  {
    setAttribute("http-equiv", paramString);
  }
  
  public String getHttpEquiv()
  {
    return getAttribute("http-equiv");
  }
  
  public void setId(String paramString)
  {
    setAttribute("id", paramString);
  }
  
  public String getId()
  {
    return getAttribute("id");
  }
  
  public void setContent(String paramString)
  {
    setAttribute("content", paramString);
  }
  
  public String getContent()
  {
    return getAttribute("content");
  }
  
  public void setName(String paramString)
  {
    setAttribute("name", paramString);
  }
  
  public String getName()
  {
    return getAttribute("name");
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.wml.dom.WMLMetaElementImpl
 * JD-Core Version:    0.7.0.1
 */