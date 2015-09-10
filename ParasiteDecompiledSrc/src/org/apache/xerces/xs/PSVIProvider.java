package org.apache.xerces.xs;

public abstract interface PSVIProvider
{
  public abstract ElementPSVI getElementPSVI();
  
  public abstract AttributePSVI getAttributePSVI(int paramInt);
  
  public abstract AttributePSVI getAttributePSVIByName(String paramString1, String paramString2);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.xs.PSVIProvider
 * JD-Core Version:    0.7.0.1
 */