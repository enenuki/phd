package org.apache.wml;

public abstract interface WMLTableElement
  extends WMLElement
{
  public abstract void setTitle(String paramString);
  
  public abstract String getTitle();
  
  public abstract void setAlign(String paramString);
  
  public abstract String getAlign();
  
  public abstract void setColumns(int paramInt);
  
  public abstract int getColumns();
  
  public abstract void setXmlLang(String paramString);
  
  public abstract String getXmlLang();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.wml.WMLTableElement
 * JD-Core Version:    0.7.0.1
 */