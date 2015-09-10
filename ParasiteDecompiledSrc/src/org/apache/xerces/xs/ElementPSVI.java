package org.apache.xerces.xs;

public abstract interface ElementPSVI
  extends ItemPSVI
{
  public abstract XSElementDeclaration getElementDeclaration();
  
  public abstract XSNotationDeclaration getNotation();
  
  public abstract boolean getNil();
  
  public abstract XSModel getSchemaInformation();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.xs.ElementPSVI
 * JD-Core Version:    0.7.0.1
 */