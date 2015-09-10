package org.apache.xerces.impl.dv;

public abstract interface ValidationContext
{
  public abstract boolean needFacetChecking();
  
  public abstract boolean needExtraChecking();
  
  public abstract boolean needToNormalize();
  
  public abstract boolean useNamespaces();
  
  public abstract boolean isEntityDeclared(String paramString);
  
  public abstract boolean isEntityUnparsed(String paramString);
  
  public abstract boolean isIdDeclared(String paramString);
  
  public abstract void addId(String paramString);
  
  public abstract void addIdRef(String paramString);
  
  public abstract String getSymbol(String paramString);
  
  public abstract String getURI(String paramString);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.dv.ValidationContext
 * JD-Core Version:    0.7.0.1
 */