package org.apache.xerces.jaxp.validation;

import org.apache.xerces.xni.grammars.XMLGrammarPool;

final class XMLSchema
  extends AbstractXMLSchema
{
  private final XMLGrammarPool fGrammarPool;
  private final boolean fFullyComposed;
  
  public XMLSchema(XMLGrammarPool paramXMLGrammarPool)
  {
    this(paramXMLGrammarPool, true);
  }
  
  public XMLSchema(XMLGrammarPool paramXMLGrammarPool, boolean paramBoolean)
  {
    this.fGrammarPool = paramXMLGrammarPool;
    this.fFullyComposed = paramBoolean;
  }
  
  public XMLGrammarPool getGrammarPool()
  {
    return this.fGrammarPool;
  }
  
  public boolean isFullyComposed()
  {
    return this.fFullyComposed;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.jaxp.validation.XMLSchema
 * JD-Core Version:    0.7.0.1
 */