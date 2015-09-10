package org.apache.xerces.xs;

public abstract interface XSNamedMap
{
  public abstract int getLength();
  
  public abstract XSObject item(int paramInt);
  
  public abstract XSObject itemByName(String paramString1, String paramString2);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.xs.XSNamedMap
 * JD-Core Version:    0.7.0.1
 */