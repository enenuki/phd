package org.apache.xerces.xni.grammars;

public abstract interface XMLGrammarPool
{
  public abstract Grammar[] retrieveInitialGrammarSet(String paramString);
  
  public abstract void cacheGrammars(String paramString, Grammar[] paramArrayOfGrammar);
  
  public abstract Grammar retrieveGrammar(XMLGrammarDescription paramXMLGrammarDescription);
  
  public abstract void lockPool();
  
  public abstract void unlockPool();
  
  public abstract void clear();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.xni.grammars.XMLGrammarPool
 * JD-Core Version:    0.7.0.1
 */