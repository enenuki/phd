package org.apache.xerces.jaxp.validation;

import org.apache.xerces.xni.grammars.XMLGrammarPool;

public abstract interface XSGrammarPoolContainer
{
  public abstract XMLGrammarPool getGrammarPool();
  
  public abstract boolean isFullyComposed();
  
  public abstract Boolean getFeature(String paramString);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.jaxp.validation.XSGrammarPoolContainer
 * JD-Core Version:    0.7.0.1
 */