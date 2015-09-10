package org.w3c.dom;

public abstract interface Entity
  extends Node
{
  public abstract String getPublicId();
  
  public abstract String getSystemId();
  
  public abstract String getNotationName();
  
  public abstract String getInputEncoding();
  
  public abstract String getXmlEncoding();
  
  public abstract String getXmlVersion();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.w3c.dom.Entity
 * JD-Core Version:    0.7.0.1
 */