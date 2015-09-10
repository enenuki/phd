package org.apache.xerces.parsers;

import java.util.HashMap;
import org.apache.xerces.impl.XMLDocumentFragmentScannerImpl;
import org.apache.xerces.impl.XMLDocumentScannerImpl;
import org.apache.xerces.impl.XMLErrorReporter;
import org.apache.xerces.impl.XMLNSDocumentScannerImpl;
import org.apache.xerces.impl.dtd.XMLDTDValidator;
import org.apache.xerces.impl.dtd.XMLNSDTDValidator;
import org.apache.xerces.impl.xs.XMLSchemaValidator;
import org.apache.xerces.impl.xs.XSMessageFormatter;
import org.apache.xerces.util.ParserConfigurationSettings;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.xni.XMLDocumentHandler;
import org.apache.xerces.xni.grammars.XMLGrammarPool;
import org.apache.xerces.xni.parser.XMLComponentManager;
import org.apache.xerces.xni.parser.XMLDocumentScanner;
import org.apache.xerces.xni.parser.XMLDocumentSource;

public class IntegratedParserConfiguration
  extends StandardParserConfiguration
{
  protected XMLNSDocumentScannerImpl fNamespaceScanner;
  protected XMLDocumentScannerImpl fNonNSScanner = new XMLDocumentScannerImpl();
  protected XMLDTDValidator fNonNSDTDValidator = new XMLDTDValidator();
  
  public IntegratedParserConfiguration()
  {
    this(null, null, null);
  }
  
  public IntegratedParserConfiguration(SymbolTable paramSymbolTable)
  {
    this(paramSymbolTable, null, null);
  }
  
  public IntegratedParserConfiguration(SymbolTable paramSymbolTable, XMLGrammarPool paramXMLGrammarPool)
  {
    this(paramSymbolTable, paramXMLGrammarPool, null);
  }
  
  public IntegratedParserConfiguration(SymbolTable paramSymbolTable, XMLGrammarPool paramXMLGrammarPool, XMLComponentManager paramXMLComponentManager)
  {
    super(paramSymbolTable, paramXMLGrammarPool, paramXMLComponentManager);
    addComponent(this.fNonNSScanner);
    addComponent(this.fNonNSDTDValidator);
  }
  
  protected void configurePipeline()
  {
    setProperty("http://apache.org/xml/properties/internal/datatype-validator-factory", this.fDatatypeValidatorFactory);
    configureDTDPipeline();
    if (this.fFeatures.get("http://xml.org/sax/features/namespaces") == Boolean.TRUE)
    {
      this.fProperties.put("http://apache.org/xml/properties/internal/namespace-binder", this.fNamespaceBinder);
      this.fScanner = this.fNamespaceScanner;
      this.fProperties.put("http://apache.org/xml/properties/internal/document-scanner", this.fNamespaceScanner);
      if (this.fDTDValidator != null)
      {
        this.fProperties.put("http://apache.org/xml/properties/internal/validator/dtd", this.fDTDValidator);
        this.fNamespaceScanner.setDTDValidator(this.fDTDValidator);
        this.fNamespaceScanner.setDocumentHandler(this.fDTDValidator);
        this.fDTDValidator.setDocumentSource(this.fNamespaceScanner);
        this.fDTDValidator.setDocumentHandler(this.fDocumentHandler);
        if (this.fDocumentHandler != null) {
          this.fDocumentHandler.setDocumentSource(this.fDTDValidator);
        }
        this.fLastComponent = this.fDTDValidator;
      }
      else
      {
        this.fNamespaceScanner.setDocumentHandler(this.fDocumentHandler);
        this.fNamespaceScanner.setDTDValidator(null);
        if (this.fDocumentHandler != null) {
          this.fDocumentHandler.setDocumentSource(this.fNamespaceScanner);
        }
        this.fLastComponent = this.fNamespaceScanner;
      }
    }
    else
    {
      this.fScanner = this.fNonNSScanner;
      this.fProperties.put("http://apache.org/xml/properties/internal/document-scanner", this.fNonNSScanner);
      if (this.fNonNSDTDValidator != null)
      {
        this.fProperties.put("http://apache.org/xml/properties/internal/validator/dtd", this.fNonNSDTDValidator);
        this.fNonNSScanner.setDocumentHandler(this.fNonNSDTDValidator);
        this.fNonNSDTDValidator.setDocumentSource(this.fNonNSScanner);
        this.fNonNSDTDValidator.setDocumentHandler(this.fDocumentHandler);
        if (this.fDocumentHandler != null) {
          this.fDocumentHandler.setDocumentSource(this.fNonNSDTDValidator);
        }
        this.fLastComponent = this.fNonNSDTDValidator;
      }
      else
      {
        this.fScanner.setDocumentHandler(this.fDocumentHandler);
        if (this.fDocumentHandler != null) {
          this.fDocumentHandler.setDocumentSource(this.fScanner);
        }
        this.fLastComponent = this.fScanner;
      }
    }
    if (this.fFeatures.get("http://apache.org/xml/features/validation/schema") == Boolean.TRUE)
    {
      if (this.fSchemaValidator == null)
      {
        this.fSchemaValidator = new XMLSchemaValidator();
        this.fProperties.put("http://apache.org/xml/properties/internal/validator/schema", this.fSchemaValidator);
        addComponent(this.fSchemaValidator);
        if (this.fErrorReporter.getMessageFormatter("http://www.w3.org/TR/xml-schema-1") == null)
        {
          XSMessageFormatter localXSMessageFormatter = new XSMessageFormatter();
          this.fErrorReporter.putMessageFormatter("http://www.w3.org/TR/xml-schema-1", localXSMessageFormatter);
        }
      }
      this.fLastComponent.setDocumentHandler(this.fSchemaValidator);
      this.fSchemaValidator.setDocumentSource(this.fLastComponent);
      this.fSchemaValidator.setDocumentHandler(this.fDocumentHandler);
      if (this.fDocumentHandler != null) {
        this.fDocumentHandler.setDocumentSource(this.fSchemaValidator);
      }
      this.fLastComponent = this.fSchemaValidator;
    }
  }
  
  protected XMLDocumentScanner createDocumentScanner()
  {
    this.fNamespaceScanner = new XMLNSDocumentScannerImpl();
    return this.fNamespaceScanner;
  }
  
  protected XMLDTDValidator createDTDValidator()
  {
    return new XMLNSDTDValidator();
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.parsers.IntegratedParserConfiguration
 * JD-Core Version:    0.7.0.1
 */