package org.apache.xerces.impl.xs.identity;

public abstract interface FieldActivator
{
  public abstract void startValueScopeFor(IdentityConstraint paramIdentityConstraint, int paramInt);
  
  public abstract XPathMatcher activateField(Field paramField, int paramInt);
  
  public abstract void endValueScopeFor(IdentityConstraint paramIdentityConstraint, int paramInt);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.identity.FieldActivator
 * JD-Core Version:    0.7.0.1
 */