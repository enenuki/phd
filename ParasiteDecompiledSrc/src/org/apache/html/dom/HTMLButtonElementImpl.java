package org.apache.html.dom;

import org.apache.xerces.dom.ElementImpl;
import org.w3c.dom.html.HTMLButtonElement;

public class HTMLButtonElementImpl
  extends HTMLElementImpl
  implements HTMLButtonElement, HTMLFormControl
{
  private static final long serialVersionUID = -753685852948076730L;
  
  public String getAccessKey()
  {
    String str = getAttribute("accesskey");
    if ((str != null) && (str.length() > 1)) {
      str = str.substring(0, 1);
    }
    return str;
  }
  
  public void setAccessKey(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 1)) {
      paramString = paramString.substring(0, 1);
    }
    setAttribute("accesskey", paramString);
  }
  
  public boolean getDisabled()
  {
    return getBinary("disabled");
  }
  
  public void setDisabled(boolean paramBoolean)
  {
    setAttribute("disabled", paramBoolean);
  }
  
  public String getName()
  {
    return getAttribute("name");
  }
  
  public void setName(String paramString)
  {
    setAttribute("name", paramString);
  }
  
  public int getTabIndex()
  {
    try
    {
      return Integer.parseInt(getAttribute("tabindex"));
    }
    catch (NumberFormatException localNumberFormatException) {}
    return 0;
  }
  
  public void setTabIndex(int paramInt)
  {
    setAttribute("tabindex", String.valueOf(paramInt));
  }
  
  public String getType()
  {
    return capitalize(getAttribute("type"));
  }
  
  public String getValue()
  {
    return getAttribute("value");
  }
  
  public void setValue(String paramString)
  {
    setAttribute("value", paramString);
  }
  
  public HTMLButtonElementImpl(HTMLDocumentImpl paramHTMLDocumentImpl, String paramString)
  {
    super(paramHTMLDocumentImpl, paramString);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.html.dom.HTMLButtonElementImpl
 * JD-Core Version:    0.7.0.1
 */