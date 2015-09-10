package org.apache.xerces.xni.grammars;

import org.apache.xerces.xni.XMLResourceIdentifier;

public abstract interface XMLGrammarDescription
  extends XMLResourceIdentifier
{
  public static final String XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
  public static final String XML_DTD = "http://www.w3.org/TR/REC-xml";
  
  public abstract String getGrammarType();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.xni.grammars.XMLGrammarDescription
 * JD-Core Version:    0.7.0.1
 */