package org.apache.xerces.jaxp.validation;

import org.apache.xerces.xni.grammars.Grammar;
import org.apache.xerces.xni.grammars.XMLGrammarDescription;
import org.apache.xerces.xni.grammars.XMLGrammarPool;

final class ReadOnlyGrammarPool
  implements XMLGrammarPool
{
  private final XMLGrammarPool core;
  
  public ReadOnlyGrammarPool(XMLGrammarPool paramXMLGrammarPool)
  {
    this.core = paramXMLGrammarPool;
  }
  
  public void cacheGrammars(String paramString, Grammar[] paramArrayOfGrammar) {}
  
  public void clear() {}
  
  public void lockPool() {}
  
  public Grammar retrieveGrammar(XMLGrammarDescription paramXMLGrammarDescription)
  {
    return this.core.retrieveGrammar(paramXMLGrammarDescription);
  }
  
  public Grammar[] retrieveInitialGrammarSet(String paramString)
  {
    return this.core.retrieveInitialGrammarSet(paramString);
  }
  
  public void unlockPool() {}
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.jaxp.validation.ReadOnlyGrammarPool
 * JD-Core Version:    0.7.0.1
 */