package org.apache.xerces.jaxp.validation;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Locale;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.apache.xerces.impl.xs.XMLSchemaLoader;
import org.apache.xerces.util.DOMEntityResolverWrapper;
import org.apache.xerces.util.DOMInputSource;
import org.apache.xerces.util.ErrorHandlerWrapper;
import org.apache.xerces.util.SAXInputSource;
import org.apache.xerces.util.SAXMessageFormatter;
import org.apache.xerces.util.SecurityManager;
import org.apache.xerces.util.XMLGrammarPoolImpl;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.grammars.Grammar;
import org.apache.xerces.xni.grammars.XMLGrammarDescription;
import org.apache.xerces.xni.grammars.XMLGrammarPool;
import org.apache.xerces.xni.parser.XMLConfigurationException;
import org.apache.xerces.xni.parser.XMLInputSource;
import org.w3c.dom.Node;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;

public final class XMLSchemaFactory
  extends SchemaFactory
{
  private static final String SCHEMA_FULL_CHECKING = "http://apache.org/xml/features/validation/schema-full-checking";
  private static final String USE_GRAMMAR_POOL_ONLY = "http://apache.org/xml/features/internal/validation/schema/use-grammar-pool-only";
  private static final String XMLGRAMMAR_POOL = "http://apache.org/xml/properties/internal/grammar-pool";
  private static final String SECURITY_MANAGER = "http://apache.org/xml/properties/security-manager";
  private final XMLSchemaLoader fXMLSchemaLoader = new XMLSchemaLoader();
  private ErrorHandler fErrorHandler;
  private LSResourceResolver fLSResourceResolver;
  private final DOMEntityResolverWrapper fDOMEntityResolverWrapper = new DOMEntityResolverWrapper();
  private final ErrorHandlerWrapper fErrorHandlerWrapper = new ErrorHandlerWrapper(DraconianErrorHandler.getInstance());
  private SecurityManager fSecurityManager;
  private final XMLGrammarPoolWrapper fXMLGrammarPoolWrapper = new XMLGrammarPoolWrapper();
  private boolean fUseGrammarPoolOnly;
  
  public XMLSchemaFactory()
  {
    this.fXMLSchemaLoader.setFeature("http://apache.org/xml/features/validation/schema-full-checking", true);
    this.fXMLSchemaLoader.setProperty("http://apache.org/xml/properties/internal/grammar-pool", this.fXMLGrammarPoolWrapper);
    this.fXMLSchemaLoader.setEntityResolver(this.fDOMEntityResolverWrapper);
    this.fXMLSchemaLoader.setErrorHandler(this.fErrorHandlerWrapper);
    this.fUseGrammarPoolOnly = true;
  }
  
  public boolean isSchemaLanguageSupported(String paramString)
  {
    if (paramString == null) {
      throw new NullPointerException(JAXPValidationMessageFormatter.formatMessage(Locale.getDefault(), "SchemaLanguageNull", null));
    }
    if (paramString.length() == 0) {
      throw new IllegalArgumentException(JAXPValidationMessageFormatter.formatMessage(Locale.getDefault(), "SchemaLanguageLengthZero", null));
    }
    return paramString.equals("http://www.w3.org/2001/XMLSchema");
  }
  
  public LSResourceResolver getResourceResolver()
  {
    return this.fLSResourceResolver;
  }
  
  public void setResourceResolver(LSResourceResolver paramLSResourceResolver)
  {
    this.fLSResourceResolver = paramLSResourceResolver;
    this.fDOMEntityResolverWrapper.setEntityResolver(paramLSResourceResolver);
    this.fXMLSchemaLoader.setEntityResolver(this.fDOMEntityResolverWrapper);
  }
  
  public ErrorHandler getErrorHandler()
  {
    return this.fErrorHandler;
  }
  
  public void setErrorHandler(ErrorHandler paramErrorHandler)
  {
    this.fErrorHandler = paramErrorHandler;
    this.fErrorHandlerWrapper.setErrorHandler(paramErrorHandler != null ? paramErrorHandler : DraconianErrorHandler.getInstance());
    this.fXMLSchemaLoader.setErrorHandler(this.fErrorHandlerWrapper);
  }
  
  public Schema newSchema(Source[] paramArrayOfSource)
    throws SAXException
  {
    XMLGrammarPoolImplExtension localXMLGrammarPoolImplExtension = new XMLGrammarPoolImplExtension();
    this.fXMLGrammarPoolWrapper.setGrammarPool(localXMLGrammarPoolImplExtension);
    XMLInputSource[] arrayOfXMLInputSource = new XMLInputSource[paramArrayOfSource.length];
    Object localObject3;
    for (int i = 0; i < paramArrayOfSource.length; i++)
    {
      Source localSource = paramArrayOfSource[i];
      Object localObject1;
      String str;
      if ((localSource instanceof StreamSource))
      {
        localObject1 = (StreamSource)localSource;
        localObject3 = ((StreamSource)localObject1).getPublicId();
        str = ((StreamSource)localObject1).getSystemId();
        InputStream localInputStream = ((StreamSource)localObject1).getInputStream();
        Reader localReader = ((StreamSource)localObject1).getReader();
        arrayOfXMLInputSource[i] = new XMLInputSource((String)localObject3, str, null);
        arrayOfXMLInputSource[i].setByteStream(localInputStream);
        arrayOfXMLInputSource[i].setCharacterStream(localReader);
      }
      else if ((localSource instanceof SAXSource))
      {
        localObject1 = (SAXSource)localSource;
        localObject3 = ((SAXSource)localObject1).getInputSource();
        if (localObject3 == null) {
          throw new SAXException(JAXPValidationMessageFormatter.formatMessage(Locale.getDefault(), "SAXSourceNullInputSource", null));
        }
        arrayOfXMLInputSource[i] = new SAXInputSource(((SAXSource)localObject1).getXMLReader(), (InputSource)localObject3);
      }
      else if ((localSource instanceof DOMSource))
      {
        localObject1 = (DOMSource)localSource;
        localObject3 = ((DOMSource)localObject1).getNode();
        str = ((DOMSource)localObject1).getSystemId();
        arrayOfXMLInputSource[i] = new DOMInputSource((Node)localObject3, str);
      }
      else
      {
        if (localSource == null) {
          throw new NullPointerException(JAXPValidationMessageFormatter.formatMessage(Locale.getDefault(), "SchemaSourceArrayMemberNull", null));
        }
        throw new IllegalArgumentException(JAXPValidationMessageFormatter.formatMessage(Locale.getDefault(), "SchemaFactorySourceUnrecognized", new Object[] { localSource.getClass().getName() }));
      }
    }
    try
    {
      this.fXMLSchemaLoader.loadGrammar(arrayOfXMLInputSource);
    }
    catch (XNIException localXNIException)
    {
      throw Util.toSAXException(localXNIException);
    }
    catch (IOException localIOException)
    {
      localObject3 = new SAXParseException(localIOException.getMessage(), null, localIOException);
      if (this.fErrorHandler != null) {
        this.fErrorHandler.error((SAXParseException)localObject3);
      }
      throw ((Throwable)localObject3);
    }
    this.fXMLGrammarPoolWrapper.setGrammarPool(null);
    int j = localXMLGrammarPoolImplExtension.getGrammarCount();
    Object localObject2 = null;
    if (this.fUseGrammarPoolOnly)
    {
      if (j > 1)
      {
        localObject2 = new XMLSchema(new ReadOnlyGrammarPool(localXMLGrammarPoolImplExtension));
      }
      else if (j == 1)
      {
        localObject3 = localXMLGrammarPoolImplExtension.retrieveInitialGrammarSet("http://www.w3.org/2001/XMLSchema");
        localObject2 = new SimpleXMLSchema(localObject3[0]);
      }
      else
      {
        localObject2 = new EmptyXMLSchema();
      }
    }
    else {
      localObject2 = new XMLSchema(new ReadOnlyGrammarPool(localXMLGrammarPoolImplExtension), false);
    }
    propagateFeatures((AbstractXMLSchema)localObject2);
    return localObject2;
  }
  
  public Schema newSchema()
    throws SAXException
  {
    WeakReferenceXMLSchema localWeakReferenceXMLSchema = new WeakReferenceXMLSchema();
    propagateFeatures(localWeakReferenceXMLSchema);
    return localWeakReferenceXMLSchema;
  }
  
  public boolean getFeature(String paramString)
    throws SAXNotRecognizedException, SAXNotSupportedException
  {
    if (paramString == null) {
      throw new NullPointerException(JAXPValidationMessageFormatter.formatMessage(Locale.getDefault(), "FeatureNameNull", null));
    }
    if (paramString.equals("http://javax.xml.XMLConstants/feature/secure-processing")) {
      return this.fSecurityManager != null;
    }
    if (paramString.equals("http://apache.org/xml/features/internal/validation/schema/use-grammar-pool-only")) {
      return this.fUseGrammarPoolOnly;
    }
    try
    {
      return this.fXMLSchemaLoader.getFeature(paramString);
    }
    catch (XMLConfigurationException localXMLConfigurationException)
    {
      String str = localXMLConfigurationException.getIdentifier();
      if (localXMLConfigurationException.getType() == 0) {
        throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(Locale.getDefault(), "feature-not-recognized", new Object[] { str }));
      }
      throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(Locale.getDefault(), "feature-not-supported", new Object[] { str }));
    }
  }
  
  public Object getProperty(String paramString)
    throws SAXNotRecognizedException, SAXNotSupportedException
  {
    if (paramString == null) {
      throw new NullPointerException(JAXPValidationMessageFormatter.formatMessage(Locale.getDefault(), "ProperyNameNull", null));
    }
    if (paramString.equals("http://apache.org/xml/properties/security-manager")) {
      return this.fSecurityManager;
    }
    if (paramString.equals("http://apache.org/xml/properties/internal/grammar-pool")) {
      throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(Locale.getDefault(), "property-not-supported", new Object[] { paramString }));
    }
    try
    {
      return this.fXMLSchemaLoader.getProperty(paramString);
    }
    catch (XMLConfigurationException localXMLConfigurationException)
    {
      String str = localXMLConfigurationException.getIdentifier();
      if (localXMLConfigurationException.getType() == 0) {
        throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(Locale.getDefault(), "property-not-recognized", new Object[] { str }));
      }
      throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(Locale.getDefault(), "property-not-supported", new Object[] { str }));
    }
  }
  
  public void setFeature(String paramString, boolean paramBoolean)
    throws SAXNotRecognizedException, SAXNotSupportedException
  {
    if (paramString == null) {
      throw new NullPointerException(JAXPValidationMessageFormatter.formatMessage(Locale.getDefault(), "FeatureNameNull", null));
    }
    if (paramString.equals("http://javax.xml.XMLConstants/feature/secure-processing"))
    {
      this.fSecurityManager = (paramBoolean ? new SecurityManager() : null);
      this.fXMLSchemaLoader.setProperty("http://apache.org/xml/properties/security-manager", this.fSecurityManager);
      return;
    }
    if (paramString.equals("http://apache.org/xml/features/internal/validation/schema/use-grammar-pool-only"))
    {
      this.fUseGrammarPoolOnly = paramBoolean;
      return;
    }
    try
    {
      this.fXMLSchemaLoader.setFeature(paramString, paramBoolean);
    }
    catch (XMLConfigurationException localXMLConfigurationException)
    {
      String str = localXMLConfigurationException.getIdentifier();
      if (localXMLConfigurationException.getType() == 0) {
        throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(Locale.getDefault(), "feature-not-recognized", new Object[] { str }));
      }
      throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(Locale.getDefault(), "feature-not-supported", new Object[] { str }));
    }
  }
  
  public void setProperty(String paramString, Object paramObject)
    throws SAXNotRecognizedException, SAXNotSupportedException
  {
    if (paramString == null) {
      throw new NullPointerException(JAXPValidationMessageFormatter.formatMessage(Locale.getDefault(), "ProperyNameNull", null));
    }
    if (paramString.equals("http://apache.org/xml/properties/security-manager"))
    {
      this.fSecurityManager = ((SecurityManager)paramObject);
      this.fXMLSchemaLoader.setProperty("http://apache.org/xml/properties/security-manager", this.fSecurityManager);
      return;
    }
    if (paramString.equals("http://apache.org/xml/properties/internal/grammar-pool")) {
      throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(Locale.getDefault(), "property-not-supported", new Object[] { paramString }));
    }
    try
    {
      this.fXMLSchemaLoader.setProperty(paramString, paramObject);
    }
    catch (XMLConfigurationException localXMLConfigurationException)
    {
      String str = localXMLConfigurationException.getIdentifier();
      if (localXMLConfigurationException.getType() == 0) {
        throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(Locale.getDefault(), "property-not-recognized", new Object[] { str }));
      }
      throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(Locale.getDefault(), "property-not-supported", new Object[] { str }));
    }
  }
  
  private void propagateFeatures(AbstractXMLSchema paramAbstractXMLSchema)
  {
    paramAbstractXMLSchema.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", this.fSecurityManager != null);
    String[] arrayOfString = this.fXMLSchemaLoader.getRecognizedFeatures();
    for (int i = 0; i < arrayOfString.length; i++)
    {
      boolean bool = this.fXMLSchemaLoader.getFeature(arrayOfString[i]);
      paramAbstractXMLSchema.setFeature(arrayOfString[i], bool);
    }
  }
  
  static class XMLGrammarPoolWrapper
    implements XMLGrammarPool
  {
    private XMLGrammarPool fGrammarPool;
    
    public Grammar[] retrieveInitialGrammarSet(String paramString)
    {
      return this.fGrammarPool.retrieveInitialGrammarSet(paramString);
    }
    
    public void cacheGrammars(String paramString, Grammar[] paramArrayOfGrammar)
    {
      this.fGrammarPool.cacheGrammars(paramString, paramArrayOfGrammar);
    }
    
    public Grammar retrieveGrammar(XMLGrammarDescription paramXMLGrammarDescription)
    {
      return this.fGrammarPool.retrieveGrammar(paramXMLGrammarDescription);
    }
    
    public void lockPool()
    {
      this.fGrammarPool.lockPool();
    }
    
    public void unlockPool()
    {
      this.fGrammarPool.unlockPool();
    }
    
    public void clear()
    {
      this.fGrammarPool.clear();
    }
    
    void setGrammarPool(XMLGrammarPool paramXMLGrammarPool)
    {
      this.fGrammarPool = paramXMLGrammarPool;
    }
    
    XMLGrammarPool getGrammarPool()
    {
      return this.fGrammarPool;
    }
  }
  
  static class XMLGrammarPoolImplExtension
    extends XMLGrammarPoolImpl
  {
    public XMLGrammarPoolImplExtension() {}
    
    public XMLGrammarPoolImplExtension(int paramInt)
    {
      super();
    }
    
    int getGrammarCount()
    {
      return this.fGrammarCount;
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.jaxp.validation.XMLSchemaFactory
 * JD-Core Version:    0.7.0.1
 */