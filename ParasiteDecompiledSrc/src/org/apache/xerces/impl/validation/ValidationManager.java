package org.apache.xerces.impl.validation;

import java.util.Vector;

public class ValidationManager
{
  protected final Vector fVSs = new Vector();
  protected boolean fGrammarFound = false;
  protected boolean fCachedDTD = false;
  
  public final void addValidationState(ValidationState paramValidationState)
  {
    this.fVSs.addElement(paramValidationState);
  }
  
  public final void setEntityState(EntityState paramEntityState)
  {
    for (int i = this.fVSs.size() - 1; i >= 0; i--) {
      ((ValidationState)this.fVSs.elementAt(i)).setEntityState(paramEntityState);
    }
  }
  
  public final void setGrammarFound(boolean paramBoolean)
  {
    this.fGrammarFound = paramBoolean;
  }
  
  public final boolean isGrammarFound()
  {
    return this.fGrammarFound;
  }
  
  public final void setCachedDTD(boolean paramBoolean)
  {
    this.fCachedDTD = paramBoolean;
  }
  
  public final boolean isCachedDTD()
  {
    return this.fCachedDTD;
  }
  
  public final void reset()
  {
    this.fVSs.removeAllElements();
    this.fGrammarFound = false;
    this.fCachedDTD = false;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.validation.ValidationManager
 * JD-Core Version:    0.7.0.1
 */