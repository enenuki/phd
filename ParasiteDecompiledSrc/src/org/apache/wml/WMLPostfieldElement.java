package org.apache.wml;

public abstract interface WMLPostfieldElement
  extends WMLElement
{
  public abstract void setValue(String paramString);
  
  public abstract String getValue();
  
  public abstract void setName(String paramString);
  
  public abstract String getName();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.wml.WMLPostfieldElement
 * JD-Core Version:    0.7.0.1
 */