package org.w3c.dom.html;

public abstract interface HTMLParamElement
  extends HTMLElement
{
  public abstract String getName();
  
  public abstract void setName(String paramString);
  
  public abstract String getType();
  
  public abstract void setType(String paramString);
  
  public abstract String getValue();
  
  public abstract void setValue(String paramString);
  
  public abstract String getValueType();
  
  public abstract void setValueType(String paramString);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.w3c.dom.html.HTMLParamElement
 * JD-Core Version:    0.7.0.1
 */