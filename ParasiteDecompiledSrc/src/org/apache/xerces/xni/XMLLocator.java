package org.apache.xerces.xni;

public abstract interface XMLLocator
{
  public abstract String getPublicId();
  
  public abstract String getLiteralSystemId();
  
  public abstract String getBaseSystemId();
  
  public abstract String getExpandedSystemId();
  
  public abstract int getLineNumber();
  
  public abstract int getColumnNumber();
  
  public abstract int getCharacterOffset();
  
  public abstract String getEncoding();
  
  public abstract String getXMLVersion();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.xni.XMLLocator
 * JD-Core Version:    0.7.0.1
 */