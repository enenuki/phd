package org.apache.xerces.xni.grammars;

import org.apache.xerces.xs.XSModel;

public abstract interface XSGrammar
  extends Grammar
{
  public abstract XSModel toXSModel();
  
  public abstract XSModel toXSModel(XSGrammar[] paramArrayOfXSGrammar);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.xni.grammars.XSGrammar
 * JD-Core Version:    0.7.0.1
 */