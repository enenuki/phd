package org.apache.xerces.xs;

public abstract interface XSParticle
  extends XSObject
{
  public abstract int getMinOccurs();
  
  public abstract int getMaxOccurs();
  
  public abstract boolean getMaxOccursUnbounded();
  
  public abstract XSTerm getTerm();
  
  public abstract XSObjectList getAnnotations();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.xs.XSParticle
 * JD-Core Version:    0.7.0.1
 */