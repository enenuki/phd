package org.apache.xerces.impl;

import org.apache.xerces.xni.XMLResourceIdentifier;

public abstract interface XMLEntityDescription
  extends XMLResourceIdentifier
{
  public abstract void setEntityName(String paramString);
  
  public abstract String getEntityName();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.XMLEntityDescription
 * JD-Core Version:    0.7.0.1
 */