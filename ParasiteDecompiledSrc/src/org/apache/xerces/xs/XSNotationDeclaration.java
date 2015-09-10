package org.apache.xerces.xs;

public abstract interface XSNotationDeclaration
  extends XSObject
{
  public abstract String getSystemId();
  
  public abstract String getPublicId();
  
  public abstract XSAnnotation getAnnotation();
  
  public abstract XSObjectList getAnnotations();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.xs.XSNotationDeclaration
 * JD-Core Version:    0.7.0.1
 */