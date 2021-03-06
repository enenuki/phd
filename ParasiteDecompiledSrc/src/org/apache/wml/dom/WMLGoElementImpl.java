package org.apache.wml.dom;

import org.apache.wml.WMLGoElement;
import org.apache.xerces.dom.ElementImpl;

public class WMLGoElementImpl
  extends WMLElementImpl
  implements WMLGoElement
{
  private static final long serialVersionUID = -2052250142899797905L;
  
  public WMLGoElementImpl(WMLDocumentImpl paramWMLDocumentImpl, String paramString)
  {
    super(paramWMLDocumentImpl, paramString);
  }
  
  public void setSendreferer(String paramString)
  {
    setAttribute("sendreferer", paramString);
  }
  
  public String getSendreferer()
  {
    return getAttribute("sendreferer");
  }
  
  public void setAcceptCharset(String paramString)
  {
    setAttribute("accept-charset", paramString);
  }
  
  public String getAcceptCharset()
  {
    return getAttribute("accept-charset");
  }
  
  public void setHref(String paramString)
  {
    setAttribute("href", paramString);
  }
  
  public String getHref()
  {
    return getAttribute("href");
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
  
  public void setMethod(String paramString)
  {
    setAttribute("method", paramString);
  }
  
  public String getMethod()
  {
    return getAttribute("method");
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.wml.dom.WMLGoElementImpl
 * JD-Core Version:    0.7.0.1
 */