package org.apache.wml.dom;

import org.apache.wml.WMLSelectElement;
import org.apache.xerces.dom.ElementImpl;

public class WMLSelectElementImpl
  extends WMLElementImpl
  implements WMLSelectElement
{
  private static final long serialVersionUID = 6489112443257247261L;
  
  public WMLSelectElementImpl(WMLDocumentImpl paramWMLDocumentImpl, String paramString)
  {
    super(paramWMLDocumentImpl, paramString);
  }
  
  public void setMultiple(boolean paramBoolean)
  {
    setAttribute("multiple", paramBoolean);
  }
  
  public boolean getMultiple()
  {
    return getAttribute("multiple", false);
  }
  
  public void setValue(String paramString)
  {
    setAttribute("value", paramString);
  }
  
  public String getValue()
  {
    return getAttribute("value");
  }
  
  public void setTabIndex(int paramInt)
  {
    setAttribute("tabindex", paramInt);
  }
  
  public int getTabIndex()
  {
    return getAttribute("tabindex", 0);
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
  
  public void setIValue(String paramString)
  {
    setAttribute("ivalue", paramString);
  }
  
  public String getIValue()
  {
    return getAttribute("ivalue");
  }
  
  public void setId(String paramString)
  {
    setAttribute("id", paramString);
  }
  
  public String getId()
  {
    return getAttribute("id");
  }
  
  public void setIName(String paramString)
  {
    setAttribute("iname", paramString);
  }
  
  public String getIName()
  {
    return getAttribute("iname");
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
 * Qualified Name:     org.apache.wml.dom.WMLSelectElementImpl
 * JD-Core Version:    0.7.0.1
 */