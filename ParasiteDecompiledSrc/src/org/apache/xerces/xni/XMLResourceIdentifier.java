package org.apache.xerces.xni;

public abstract interface XMLResourceIdentifier
{
  public abstract void setPublicId(String paramString);
  
  public abstract String getPublicId();
  
  public abstract void setExpandedSystemId(String paramString);
  
  public abstract String getExpandedSystemId();
  
  public abstract void setLiteralSystemId(String paramString);
  
  public abstract String getLiteralSystemId();
  
  public abstract void setBaseSystemId(String paramString);
  
  public abstract String getBaseSystemId();
  
  public abstract void setNamespace(String paramString);
  
  public abstract String getNamespace();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.xni.XMLResourceIdentifier
 * JD-Core Version:    0.7.0.1
 */