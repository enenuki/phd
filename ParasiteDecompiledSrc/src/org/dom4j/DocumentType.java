package org.dom4j;

import java.util.List;

public abstract interface DocumentType
  extends Node
{
  public abstract String getElementName();
  
  public abstract void setElementName(String paramString);
  
  public abstract String getPublicID();
  
  public abstract void setPublicID(String paramString);
  
  public abstract String getSystemID();
  
  public abstract void setSystemID(String paramString);
  
  public abstract List getInternalDeclarations();
  
  public abstract void setInternalDeclarations(List paramList);
  
  public abstract List getExternalDeclarations();
  
  public abstract void setExternalDeclarations(List paramList);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.dom4j.DocumentType
 * JD-Core Version:    0.7.0.1
 */