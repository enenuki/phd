package org.apache.xerces.impl.dtd;

import org.apache.xerces.impl.XML11DTDScannerImpl;
import org.apache.xerces.impl.XMLDTDScannerImpl;
import org.apache.xerces.impl.XMLEntityManager;
import org.apache.xerces.impl.XMLErrorReporter;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.util.XML11Char;
import org.apache.xerces.xni.grammars.XMLGrammarPool;
import org.apache.xerces.xni.parser.XMLEntityResolver;

public class XML11DTDProcessor
  extends XMLDTDLoader
{
  public XML11DTDProcessor() {}
  
  public XML11DTDProcessor(SymbolTable paramSymbolTable)
  {
    super(paramSymbolTable);
  }
  
  public XML11DTDProcessor(SymbolTable paramSymbolTable, XMLGrammarPool paramXMLGrammarPool)
  {
    super(paramSymbolTable, paramXMLGrammarPool);
  }
  
  XML11DTDProcessor(SymbolTable paramSymbolTable, XMLGrammarPool paramXMLGrammarPool, XMLErrorReporter paramXMLErrorReporter, XMLEntityResolver paramXMLEntityResolver)
  {
    super(paramSymbolTable, paramXMLGrammarPool, paramXMLErrorReporter, paramXMLEntityResolver);
  }
  
  protected boolean isValidNmtoken(String paramString)
  {
    return XML11Char.isXML11ValidNmtoken(paramString);
  }
  
  protected boolean isValidName(String paramString)
  {
    return XML11Char.isXML11ValidName(paramString);
  }
  
  protected XMLDTDScannerImpl createDTDScanner(SymbolTable paramSymbolTable, XMLErrorReporter paramXMLErrorReporter, XMLEntityManager paramXMLEntityManager)
  {
    return new XML11DTDScannerImpl(paramSymbolTable, paramXMLErrorReporter, paramXMLEntityManager);
  }
  
  protected short getScannerVersion()
  {
    return 2;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.dtd.XML11DTDProcessor
 * JD-Core Version:    0.7.0.1
 */