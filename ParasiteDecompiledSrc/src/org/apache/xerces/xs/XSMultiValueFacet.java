package org.apache.xerces.xs;

public abstract interface XSMultiValueFacet
  extends XSObject
{
  public abstract short getFacetKind();
  
  public abstract StringList getLexicalFacetValues();
  
  public abstract XSObjectList getAnnotations();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.xs.XSMultiValueFacet
 * JD-Core Version:    0.7.0.1
 */