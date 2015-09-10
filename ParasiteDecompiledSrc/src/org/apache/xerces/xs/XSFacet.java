package org.apache.xerces.xs;

public abstract interface XSFacet
  extends XSObject
{
  public abstract short getFacetKind();
  
  public abstract String getLexicalFacetValue();
  
  public abstract boolean getFixed();
  
  public abstract XSAnnotation getAnnotation();
  
  public abstract XSObjectList getAnnotations();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.xs.XSFacet
 * JD-Core Version:    0.7.0.1
 */