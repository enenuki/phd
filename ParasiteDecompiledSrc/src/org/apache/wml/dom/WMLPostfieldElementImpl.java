package org.apache.wml.dom;

import org.apache.wml.WMLPostfieldElement;
import org.apache.xerces.dom.ElementImpl;

public class WMLPostfieldElementImpl
  extends WMLElementImpl
  implements WMLPostfieldElement
{
  private static final long serialVersionUID = -1169432003991642980L;
  
  public WMLPostfieldElementImpl(WMLDocumentImpl paramWMLDocumentImpl, String paramString)
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
  
  public void setId(String paramString)
  {
    setAttribute("id", paramString);
  }
  
  public String getId()
  {
    return getAttribute("id");
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
 * Qualified Name:     org.apache.wml.dom.WMLPostfieldElementImpl
 * JD-Core Version:    0.7.0.1
 */