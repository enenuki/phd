package org.apache.wml.dom;

import org.apache.wml.WMLOneventElement;
import org.apache.xerces.dom.ElementImpl;

public class WMLOneventElementImpl
  extends WMLElementImpl
  implements WMLOneventElement
{
  private static final long serialVersionUID = -4279215241146570871L;
  
  public WMLOneventElementImpl(WMLDocumentImpl paramWMLDocumentImpl, String paramString)
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
  
  public void setId(String paramString)
  {
    setAttribute("id", paramString);
  }
  
  public String getId()
  {
    return getAttribute("id");
  }
  
  public void setType(String paramString)
  {
    setAttribute("type", paramString);
  }
  
  public String getType()
  {
    return getAttribute("type");
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.wml.dom.WMLOneventElementImpl
 * JD-Core Version:    0.7.0.1
 */