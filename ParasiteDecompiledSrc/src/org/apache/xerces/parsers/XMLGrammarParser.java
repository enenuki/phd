package org.apache.xerces.parsers;

import org.apache.xerces.impl.dv.DTDDVFactory;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.xni.parser.XMLParserConfiguration;

public abstract class XMLGrammarParser
  extends XMLParser
{
  protected DTDDVFactory fDatatypeValidatorFactory;
  
  protected XMLGrammarParser(SymbolTable paramSymbolTable)
  {
    super((XMLParserConfiguration)ObjectFactory.createObject("org.apache.xerces.xni.parser.XMLParserConfiguration", "org.apache.xerces.parsers.XIncludeAwareParserConfiguration"));
    this.fConfiguration.setProperty("http://apache.org/xml/properties/internal/symbol-table", paramSymbolTable);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.parsers.XMLGrammarParser
 * JD-Core Version:    0.7.0.1
 */