package org.apache.xerces.parsers;

import java.util.HashMap;
import org.apache.xerces.impl.XMLErrorReporter;
import org.apache.xerces.impl.XMLNamespaceBinder;
import org.apache.xerces.impl.xs.XMLSchemaValidator;
import org.apache.xerces.impl.xs.XSMessageFormatter;
import org.apache.xerces.util.ParserConfigurationSettings;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.xni.grammars.XMLGrammarPool;
import org.apache.xerces.xni.parser.XMLComponentManager;
import org.apache.xerces.xni.parser.XMLConfigurationException;

public class StandardParserConfiguration
  extends DTDConfiguration
{
  protected static final String NORMALIZE_DATA = "http://apache.org/xml/features/validation/schema/normalized-value";
  protected static final String SCHEMA_ELEMENT_DEFAULT = "http://apache.org/xml/features/validation/schema/element-default";
  protected static final String SCHEMA_AUGMENT_PSVI = "http://apache.org/xml/features/validation/schema/augment-psvi";
  protected static final String XMLSCHEMA_VALIDATION = "http://apache.org/xml/features/validation/schema";
  protected static final String XMLSCHEMA_FULL_CHECKING = "http://apache.org/xml/features/validation/schema-full-checking";
  protected static final String GENERATE_SYNTHETIC_ANNOTATIONS = "http://apache.org/xml/features/generate-synthetic-annotations";
  protected static final String VALIDATE_ANNOTATIONS = "http://apache.org/xml/features/validate-annotations";
  protected static final String HONOUR_ALL_SCHEMALOCATIONS = "http://apache.org/xml/features/honour-all-schemaLocations";
  protected static final String IGNORE_XSI_TYPE = "http://apache.org/xml/features/validation/schema/ignore-xsi-type-until-elemdecl";
  protected static final String ID_IDREF_CHECKING = "http://apache.org/xml/features/validation/id-idref-checking";
  protected static final String UNPARSED_ENTITY_CHECKING = "http://apache.org/xml/features/validation/unparsed-entity-checking";
  protected static final String IDENTITY_CONSTRAINT_CHECKING = "http://apache.org/xml/features/validation/identity-constraint-checking";
  protected static final String SCHEMA_VALIDATOR = "http://apache.org/xml/properties/internal/validator/schema";
  protected static final String SCHEMA_LOCATION = "http://apache.org/xml/properties/schema/external-schemaLocation";
  protected static final String SCHEMA_NONS_LOCATION = "http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation";
  protected static final String ROOT_TYPE_DEF = "http://apache.org/xml/properties/validation/schema/root-type-definition";
  protected XMLSchemaValidator fSchemaValidator;
  
  public StandardParserConfiguration()
  {
    this(null, null, null);
  }
  
  public StandardParserConfiguration(SymbolTable paramSymbolTable)
  {
    this(paramSymbolTable, null, null);
  }
  
  public StandardParserConfiguration(SymbolTable paramSymbolTable, XMLGrammarPool paramXMLGrammarPool)
  {
    this(paramSymbolTable, paramXMLGrammarPool, null);
  }
  
  public StandardParserConfiguration(SymbolTable paramSymbolTable, XMLGrammarPool paramXMLGrammarPool, XMLComponentManager paramXMLComponentManager)
  {
    super(paramSymbolTable, paramXMLGrammarPool, paramXMLComponentManager);
    String[] arrayOfString1 = { "http://apache.org/xml/features/validation/schema/normalized-value", "http://apache.org/xml/features/validation/schema/element-default", "http://apache.org/xml/features/validation/schema/augment-psvi", "http://apache.org/xml/features/generate-synthetic-annotations", "http://apache.org/xml/features/validate-annotations", "http://apache.org/xml/features/honour-all-schemaLocations", "http://apache.org/xml/features/validation/schema", "http://apache.org/xml/features/validation/schema-full-checking", "http://apache.org/xml/features/validation/schema/ignore-xsi-type-until-elemdecl", "http://apache.org/xml/features/validation/id-idref-checking", "http://apache.org/xml/features/validation/identity-constraint-checking", "http://apache.org/xml/features/validation/unparsed-entity-checking" };
    addRecognizedFeatures(arrayOfString1);
    setFeature("http://apache.org/xml/features/validation/schema/element-default", true);
    setFeature("http://apache.org/xml/features/validation/schema/normalized-value", true);
    setFeature("http://apache.org/xml/features/validation/schema/augment-psvi", true);
    setFeature("http://apache.org/xml/features/generate-synthetic-annotations", false);
    setFeature("http://apache.org/xml/features/validate-annotations", false);
    setFeature("http://apache.org/xml/features/honour-all-schemaLocations", false);
    setFeature("http://apache.org/xml/features/validation/schema/ignore-xsi-type-until-elemdecl", false);
    setFeature("http://apache.org/xml/features/validation/id-idref-checking", true);
    setFeature("http://apache.org/xml/features/validation/identity-constraint-checking", true);
    setFeature("http://apache.org/xml/features/validation/unparsed-entity-checking", true);
    String[] arrayOfString2 = { "http://apache.org/xml/properties/schema/external-schemaLocation", "http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation", "http://apache.org/xml/properties/validation/schema/root-type-definition" };
    addRecognizedProperties(arrayOfString2);
  }
  
  protected void configurePipeline()
  {
    super.configurePipeline();
    if (getFeature("http://apache.org/xml/features/validation/schema"))
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
      this.fLastComponent = this.fSchemaValidator;
      this.fNamespaceBinder.setDocumentHandler(this.fSchemaValidator);
      this.fSchemaValidator.setDocumentHandler(this.fDocumentHandler);
      this.fSchemaValidator.setDocumentSource(this.fNamespaceBinder);
    }
  }
  
  protected void checkFeature(String paramString)
    throws XMLConfigurationException
  {
    if (paramString.startsWith("http://apache.org/xml/features/"))
    {
      int i = paramString.length() - "http://apache.org/xml/features/".length();
      if ((i == "validation/schema".length()) && (paramString.endsWith("validation/schema"))) {
        return;
      }
      if ((i == "validation/schema-full-checking".length()) && (paramString.endsWith("validation/schema-full-checking"))) {
        return;
      }
      if ((i == "validation/schema/normalized-value".length()) && (paramString.endsWith("validation/schema/normalized-value"))) {
        return;
      }
      if ((i == "validation/schema/element-default".length()) && (paramString.endsWith("validation/schema/element-default"))) {
        return;
      }
    }
    super.checkFeature(paramString);
  }
  
  protected void checkProperty(String paramString)
    throws XMLConfigurationException
  {
    int i;
    if (paramString.startsWith("http://apache.org/xml/properties/"))
    {
      i = paramString.length() - "http://apache.org/xml/properties/".length();
      if ((i == "schema/external-schemaLocation".length()) && (paramString.endsWith("schema/external-schemaLocation"))) {
        return;
      }
      if ((i == "schema/external-noNamespaceSchemaLocation".length()) && (paramString.endsWith("schema/external-noNamespaceSchemaLocation"))) {
        return;
      }
    }
    if (paramString.startsWith("http://java.sun.com/xml/jaxp/properties/"))
    {
      i = paramString.length() - "http://java.sun.com/xml/jaxp/properties/".length();
      if ((i == "schemaSource".length()) && (paramString.endsWith("schemaSource"))) {
        return;
      }
    }
    super.checkProperty(paramString);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.parsers.StandardParserConfiguration
 * JD-Core Version:    0.7.0.1
 */