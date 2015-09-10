package org.apache.xerces.impl.dtd;

import java.io.EOFException;
import java.io.IOException;
import java.io.StringReader;
import java.util.Locale;
import org.apache.xerces.impl.XMLDTDScannerImpl;
import org.apache.xerces.impl.XMLEntityManager;
import org.apache.xerces.impl.XMLErrorReporter;
import org.apache.xerces.impl.XMLScanner;
import org.apache.xerces.impl.msg.XMLMessageFormatter;
import org.apache.xerces.util.DefaultErrorHandler;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.xni.XMLResourceIdentifier;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.grammars.Grammar;
import org.apache.xerces.xni.grammars.XMLGrammarLoader;
import org.apache.xerces.xni.grammars.XMLGrammarPool;
import org.apache.xerces.xni.parser.XMLConfigurationException;
import org.apache.xerces.xni.parser.XMLEntityResolver;
import org.apache.xerces.xni.parser.XMLErrorHandler;
import org.apache.xerces.xni.parser.XMLInputSource;

public class XMLDTDLoader
  extends XMLDTDProcessor
  implements XMLGrammarLoader
{
  protected static final String STANDARD_URI_CONFORMANT_FEATURE = "http://apache.org/xml/features/standard-uri-conformant";
  protected static final String BALANCE_SYNTAX_TREES = "http://apache.org/xml/features/validation/balance-syntax-trees";
  private static final String[] LOADER_RECOGNIZED_FEATURES = { "http://xml.org/sax/features/validation", "http://apache.org/xml/features/validation/warn-on-duplicate-attdef", "http://apache.org/xml/features/validation/warn-on-undeclared-elemdef", "http://apache.org/xml/features/scanner/notify-char-refs", "http://apache.org/xml/features/standard-uri-conformant", "http://apache.org/xml/features/validation/balance-syntax-trees" };
  protected static final String ERROR_HANDLER = "http://apache.org/xml/properties/internal/error-handler";
  public static final String ENTITY_RESOLVER = "http://apache.org/xml/properties/internal/entity-resolver";
  private static final String[] LOADER_RECOGNIZED_PROPERTIES = { "http://apache.org/xml/properties/internal/symbol-table", "http://apache.org/xml/properties/internal/error-reporter", "http://apache.org/xml/properties/internal/error-handler", "http://apache.org/xml/properties/internal/entity-resolver", "http://apache.org/xml/properties/internal/grammar-pool", "http://apache.org/xml/properties/internal/validator/dtd" };
  private boolean fStrictURI = false;
  private boolean fBalanceSyntaxTrees = false;
  protected XMLEntityResolver fEntityResolver;
  protected XMLDTDScannerImpl fDTDScanner;
  protected XMLEntityManager fEntityManager;
  protected Locale fLocale;
  
  public XMLDTDLoader()
  {
    this(new SymbolTable());
  }
  
  public XMLDTDLoader(SymbolTable paramSymbolTable)
  {
    this(paramSymbolTable, null);
  }
  
  public XMLDTDLoader(SymbolTable paramSymbolTable, XMLGrammarPool paramXMLGrammarPool)
  {
    this(paramSymbolTable, paramXMLGrammarPool, null, new XMLEntityManager());
  }
  
  XMLDTDLoader(SymbolTable paramSymbolTable, XMLGrammarPool paramXMLGrammarPool, XMLErrorReporter paramXMLErrorReporter, XMLEntityResolver paramXMLEntityResolver)
  {
    this.fSymbolTable = paramSymbolTable;
    this.fGrammarPool = paramXMLGrammarPool;
    if (paramXMLErrorReporter == null)
    {
      paramXMLErrorReporter = new XMLErrorReporter();
      paramXMLErrorReporter.setProperty("http://apache.org/xml/properties/internal/error-handler", new DefaultErrorHandler());
    }
    this.fErrorReporter = paramXMLErrorReporter;
    if (this.fErrorReporter.getMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210") == null)
    {
      XMLMessageFormatter localXMLMessageFormatter = new XMLMessageFormatter();
      this.fErrorReporter.putMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210", localXMLMessageFormatter);
      this.fErrorReporter.putMessageFormatter("http://www.w3.org/TR/1999/REC-xml-names-19990114", localXMLMessageFormatter);
    }
    this.fEntityResolver = paramXMLEntityResolver;
    if ((this.fEntityResolver instanceof XMLEntityManager)) {
      this.fEntityManager = ((XMLEntityManager)this.fEntityResolver);
    } else {
      this.fEntityManager = new XMLEntityManager();
    }
    this.fEntityManager.setProperty("http://apache.org/xml/properties/internal/error-reporter", paramXMLErrorReporter);
    this.fDTDScanner = createDTDScanner(this.fSymbolTable, this.fErrorReporter, this.fEntityManager);
    this.fDTDScanner.setDTDHandler(this);
    this.fDTDScanner.setDTDContentModelHandler(this);
    reset();
  }
  
  public String[] getRecognizedFeatures()
  {
    return (String[])LOADER_RECOGNIZED_FEATURES.clone();
  }
  
  public void setFeature(String paramString, boolean paramBoolean)
    throws XMLConfigurationException
  {
    if (paramString.equals("http://xml.org/sax/features/validation")) {
      this.fValidation = paramBoolean;
    } else if (paramString.equals("http://apache.org/xml/features/validation/warn-on-duplicate-attdef")) {
      this.fWarnDuplicateAttdef = paramBoolean;
    } else if (paramString.equals("http://apache.org/xml/features/validation/warn-on-undeclared-elemdef")) {
      this.fWarnOnUndeclaredElemdef = paramBoolean;
    } else if (paramString.equals("http://apache.org/xml/features/scanner/notify-char-refs")) {
      this.fDTDScanner.setFeature(paramString, paramBoolean);
    } else if (paramString.equals("http://apache.org/xml/features/standard-uri-conformant")) {
      this.fStrictURI = paramBoolean;
    } else if (paramString.equals("http://apache.org/xml/features/validation/balance-syntax-trees")) {
      this.fBalanceSyntaxTrees = paramBoolean;
    } else {
      throw new XMLConfigurationException((short)0, paramString);
    }
  }
  
  public String[] getRecognizedProperties()
  {
    return (String[])LOADER_RECOGNIZED_PROPERTIES.clone();
  }
  
  public Object getProperty(String paramString)
    throws XMLConfigurationException
  {
    if (paramString.equals("http://apache.org/xml/properties/internal/symbol-table")) {
      return this.fSymbolTable;
    }
    if (paramString.equals("http://apache.org/xml/properties/internal/error-reporter")) {
      return this.fErrorReporter;
    }
    if (paramString.equals("http://apache.org/xml/properties/internal/error-handler")) {
      return this.fErrorReporter.getErrorHandler();
    }
    if (paramString.equals("http://apache.org/xml/properties/internal/entity-resolver")) {
      return this.fEntityResolver;
    }
    if (paramString.equals("http://apache.org/xml/properties/internal/grammar-pool")) {
      return this.fGrammarPool;
    }
    if (paramString.equals("http://apache.org/xml/properties/internal/validator/dtd")) {
      return this.fValidator;
    }
    throw new XMLConfigurationException((short)0, paramString);
  }
  
  public void setProperty(String paramString, Object paramObject)
    throws XMLConfigurationException
  {
    if (paramString.equals("http://apache.org/xml/properties/internal/symbol-table"))
    {
      this.fSymbolTable = ((SymbolTable)paramObject);
      this.fDTDScanner.setProperty(paramString, paramObject);
      this.fEntityManager.setProperty(paramString, paramObject);
    }
    else if (paramString.equals("http://apache.org/xml/properties/internal/error-reporter"))
    {
      this.fErrorReporter = ((XMLErrorReporter)paramObject);
      if (this.fErrorReporter.getMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210") == null)
      {
        XMLMessageFormatter localXMLMessageFormatter = new XMLMessageFormatter();
        this.fErrorReporter.putMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210", localXMLMessageFormatter);
        this.fErrorReporter.putMessageFormatter("http://www.w3.org/TR/1999/REC-xml-names-19990114", localXMLMessageFormatter);
      }
      this.fDTDScanner.setProperty(paramString, paramObject);
      this.fEntityManager.setProperty(paramString, paramObject);
    }
    else if (paramString.equals("http://apache.org/xml/properties/internal/error-handler"))
    {
      this.fErrorReporter.setProperty(paramString, paramObject);
    }
    else if (paramString.equals("http://apache.org/xml/properties/internal/entity-resolver"))
    {
      this.fEntityResolver = ((XMLEntityResolver)paramObject);
      this.fEntityManager.setProperty(paramString, paramObject);
    }
    else if (paramString.equals("http://apache.org/xml/properties/internal/grammar-pool"))
    {
      this.fGrammarPool = ((XMLGrammarPool)paramObject);
    }
    else
    {
      throw new XMLConfigurationException((short)0, paramString);
    }
  }
  
  public boolean getFeature(String paramString)
    throws XMLConfigurationException
  {
    if (paramString.equals("http://xml.org/sax/features/validation")) {
      return this.fValidation;
    }
    if (paramString.equals("http://apache.org/xml/features/validation/warn-on-duplicate-attdef")) {
      return this.fWarnDuplicateAttdef;
    }
    if (paramString.equals("http://apache.org/xml/features/validation/warn-on-undeclared-elemdef")) {
      return this.fWarnOnUndeclaredElemdef;
    }
    if (paramString.equals("http://apache.org/xml/features/scanner/notify-char-refs")) {
      return this.fDTDScanner.getFeature(paramString);
    }
    if (paramString.equals("http://apache.org/xml/features/standard-uri-conformant")) {
      return this.fStrictURI;
    }
    if (paramString.equals("http://apache.org/xml/features/validation/balance-syntax-trees")) {
      return this.fBalanceSyntaxTrees;
    }
    throw new XMLConfigurationException((short)0, paramString);
  }
  
  public void setLocale(Locale paramLocale)
  {
    this.fLocale = paramLocale;
  }
  
  public Locale getLocale()
  {
    return this.fLocale;
  }
  
  public void setErrorHandler(XMLErrorHandler paramXMLErrorHandler)
  {
    this.fErrorReporter.setProperty("http://apache.org/xml/properties/internal/error-handler", paramXMLErrorHandler);
  }
  
  public XMLErrorHandler getErrorHandler()
  {
    return this.fErrorReporter.getErrorHandler();
  }
  
  public void setEntityResolver(XMLEntityResolver paramXMLEntityResolver)
  {
    this.fEntityResolver = paramXMLEntityResolver;
    this.fEntityManager.setProperty("http://apache.org/xml/properties/internal/entity-resolver", paramXMLEntityResolver);
  }
  
  public XMLEntityResolver getEntityResolver()
  {
    return this.fEntityResolver;
  }
  
  public Grammar loadGrammar(XMLInputSource paramXMLInputSource)
    throws IOException, XNIException
  {
    reset();
    String str = XMLEntityManager.expandSystemId(paramXMLInputSource.getSystemId(), paramXMLInputSource.getBaseSystemId(), this.fStrictURI);
    XMLDTDDescription localXMLDTDDescription = new XMLDTDDescription(paramXMLInputSource.getPublicId(), paramXMLInputSource.getSystemId(), paramXMLInputSource.getBaseSystemId(), str, null);
    if (!this.fBalanceSyntaxTrees) {
      this.fDTDGrammar = new DTDGrammar(this.fSymbolTable, localXMLDTDDescription);
    } else {
      this.fDTDGrammar = new BalancedDTDGrammar(this.fSymbolTable, localXMLDTDDescription);
    }
    this.fGrammarBucket = new DTDGrammarBucket();
    this.fGrammarBucket.setStandalone(false);
    this.fGrammarBucket.setActiveGrammar(this.fDTDGrammar);
    try
    {
      this.fDTDScanner.setInputSource(paramXMLInputSource);
      this.fDTDScanner.scanDTDExternalSubset(true);
    }
    catch (EOFException localEOFException) {}finally
    {
      this.fEntityManager.closeReaders();
    }
    if ((this.fDTDGrammar != null) && (this.fGrammarPool != null)) {
      this.fGrammarPool.cacheGrammars("http://www.w3.org/TR/REC-xml", new Grammar[] { this.fDTDGrammar });
    }
    return this.fDTDGrammar;
  }
  
  public void loadGrammarWithContext(XMLDTDValidator paramXMLDTDValidator, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
    throws IOException, XNIException
  {
    DTDGrammarBucket localDTDGrammarBucket = paramXMLDTDValidator.getGrammarBucket();
    DTDGrammar localDTDGrammar = localDTDGrammarBucket.getActiveGrammar();
    if ((localDTDGrammar != null) && (!localDTDGrammar.isImmutable()))
    {
      this.fGrammarBucket = localDTDGrammarBucket;
      this.fEntityManager.setScannerVersion(getScannerVersion());
      reset();
      try
      {
        Object localObject1;
        XMLInputSource localXMLInputSource;
        if (paramString5 != null)
        {
          localObject1 = new StringBuffer(paramString5.length() + 2);
          ((StringBuffer)localObject1).append(paramString5).append("]>");
          localXMLInputSource = new XMLInputSource(null, paramString4, null, new StringReader(((StringBuffer)localObject1).toString()), null);
          this.fEntityManager.startDocumentEntity(localXMLInputSource);
          this.fDTDScanner.scanDTDInternalSubset(true, false, paramString3 != null);
        }
        if (paramString3 != null)
        {
          localObject1 = new XMLDTDDescription(paramString2, paramString3, paramString4, null, paramString1);
          localXMLInputSource = this.fEntityManager.resolveEntity((XMLResourceIdentifier)localObject1);
          this.fDTDScanner.setInputSource(localXMLInputSource);
          this.fDTDScanner.scanDTDExternalSubset(true);
        }
      }
      catch (EOFException localEOFException) {}finally
      {
        this.fEntityManager.closeReaders();
      }
    }
  }
  
  protected void reset()
  {
    super.reset();
    this.fDTDScanner.reset();
    this.fEntityManager.reset();
    this.fErrorReporter.setDocumentLocator(this.fEntityManager.getEntityScanner());
  }
  
  protected XMLDTDScannerImpl createDTDScanner(SymbolTable paramSymbolTable, XMLErrorReporter paramXMLErrorReporter, XMLEntityManager paramXMLEntityManager)
  {
    return new XMLDTDScannerImpl(paramSymbolTable, paramXMLErrorReporter, paramXMLEntityManager);
  }
  
  protected short getScannerVersion()
  {
    return 1;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.dtd.XMLDTDLoader
 * JD-Core Version:    0.7.0.1
 */