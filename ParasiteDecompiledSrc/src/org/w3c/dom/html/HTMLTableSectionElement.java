package org.w3c.dom.html;

public abstract interface HTMLTableSectionElement
  extends HTMLElement
{
  public abstract String getAlign();
  
  public abstract void setAlign(String paramString);
  
  public abstract String getCh();
  
  public abstract void setCh(String paramString);
  
  public abstract String getChOff();
  
  public abstract void setChOff(String paramString);
  
  public abstract String getVAlign();
  
  public abstract void setVAlign(String paramString);
  
  public abstract HTMLCollection getRows();
  
  public abstract HTMLElement insertRow(int paramInt);
  
  public abstract void deleteRow(int paramInt);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.w3c.dom.html.HTMLTableSectionElement
 * JD-Core Version:    0.7.0.1
 */