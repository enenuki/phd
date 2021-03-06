package org.apache.wml;

public abstract interface WMLMetaElement
  extends WMLElement
{
  public abstract void setName(String paramString);
  
  public abstract String getName();
  
  public abstract void setHttpEquiv(String paramString);
  
  public abstract String getHttpEquiv();
  
  public abstract void setForua(boolean paramBoolean);
  
  public abstract boolean getForua();
  
  public abstract void setScheme(String paramString);
  
  public abstract String getScheme();
  
  public abstract void setContent(String paramString);
  
  public abstract String getContent();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.wml.WMLMetaElement
 * JD-Core Version:    0.7.0.1
 */