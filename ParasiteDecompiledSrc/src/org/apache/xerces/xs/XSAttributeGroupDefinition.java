package org.apache.xerces.xs;

public abstract interface XSAttributeGroupDefinition
  extends XSObject
{
  public abstract XSObjectList getAttributeUses();
  
  public abstract XSWildcard getAttributeWildcard();
  
  public abstract XSAnnotation getAnnotation();
  
  public abstract XSObjectList getAnnotations();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.xs.XSAttributeGroupDefinition
 * JD-Core Version:    0.7.0.1
 */