package org.apache.xerces.jaxp;

import org.apache.xerces.impl.XMLErrorReporter;
import org.apache.xerces.impl.validation.ValidationManager;
import org.apache.xerces.impl.xs.XSMessageFormatter;
import org.apache.xerces.jaxp.validation.XSGrammarPoolContainer;
import org.apache.xerces.xni.grammars.XMLGrammarPool;
import org.apache.xerces.xni.parser.XMLComponentManager;
import org.apache.xerces.xni.parser.XMLConfigurationException;

final class SchemaValidatorConfiguration
  implements XMLComponentManager
{
  private static final String SCHEMA_VALIDATION = "http://apache.org/xml/features/validation/schema";
  private static final String VALIDATION = "http://xml.org/sax/features/validation";
  private static final String USE_GRAMMAR_POOL_ONLY = "http://apache.org/xml/features/internal/validation/schema/use-grammar-pool-only";
  private static final String PARSER_SETTINGS = "http://apache.org/xml/features/internal/parser-settings";
  private static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
  private static final String VALIDATION_MANAGER = "http://apache.org/xml/properties/internal/validation-manager";
  private static final String XMLGRAMMAR_POOL = "http://apache.org/xml/properties/internal/grammar-pool";
  private final XMLComponentManager fParentComponentManager;
  private final XMLGrammarPool fGrammarPool;
  private final boolean fUseGrammarPoolOnly;
  private final ValidationManager fValidationManager;
  
  public SchemaValidatorConfiguration(XMLComponentManager paramXMLComponentManager, XSGrammarPoolContainer paramXSGrammarPoolContainer, ValidationManager paramValidationManager)
  {
    this.fParentComponentManager = paramXMLComponentManager;
    this.fGrammarPool = paramXSGrammarPoolContainer.getGrammarPool();
    this.fUseGrammarPoolOnly = paramXSGrammarPoolContainer.isFullyComposed();
    this.fValidationManager = paramValidationManager;
    try
    {
      XMLErrorReporter localXMLErrorReporter = (XMLErrorReporter)this.fParentComponentManager.getProperty("http://apache.org/xml/properties/internal/error-reporter");
      if (localXMLErrorReporter != null) {
        localXMLErrorReporter.putMessageFormatter("http://www.w3.org/TR/xml-schema-1", new XSMessageFormatter());
      }
    }
    catch (XMLConfigurationException localXMLConfigurationException) {}
  }
  
  public boolean getFeature(String paramString)
    throws XMLConfigurationException
  {
    if ("http://apache.org/xml/features/internal/parser-settings".equals(paramString)) {
      return this.fParentComponentManager.getFeature(paramString);
    }
    if (("http://xml.org/sax/features/validation".equals(paramString)) || ("http://apache.org/xml/features/validation/schema".equals(paramString))) {
      return true;
    }
    if ("http://apache.org/xml/features/internal/validation/schema/use-grammar-pool-only".equals(paramString)) {
      return this.fUseGrammarPoolOnly;
    }
    return this.fParentComponentManager.getFeature(paramString);
  }
  
  public Object getProperty(String paramString)
    throws XMLConfigurationException
  {
    if ("http://apache.org/xml/properties/internal/grammar-pool".equals(paramString)) {
      return this.fGrammarPool;
    }
    if ("http://apache.org/xml/properties/internal/validation-manager".equals(paramString)) {
      return this.fValidationManager;
    }
    return this.fParentComponentManager.getProperty(paramString);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.jaxp.SchemaValidatorConfiguration
 * JD-Core Version:    0.7.0.1
 */