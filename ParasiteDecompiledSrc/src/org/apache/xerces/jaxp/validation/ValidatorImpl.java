package org.apache.xerces.jaxp.validation;

import java.io.IOException;
import java.util.Locale;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Validator;
import org.apache.xerces.util.SAXMessageFormatter;
import org.apache.xerces.xni.parser.XMLConfigurationException;
import org.apache.xerces.xs.AttributePSVI;
import org.apache.xerces.xs.ElementPSVI;
import org.apache.xerces.xs.PSVIProvider;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

final class ValidatorImpl
  extends Validator
  implements PSVIProvider
{
  private static final String CURRENT_ELEMENT_NODE = "http://apache.org/xml/properties/dom/current-element-node";
  private XMLSchemaValidatorComponentManager fComponentManager;
  private ValidatorHandlerImpl fSAXValidatorHelper;
  private DOMValidatorHelper fDOMValidatorHelper;
  private StreamValidatorHelper fStreamValidatorHelper;
  private boolean fConfigurationChanged = false;
  private boolean fErrorHandlerChanged = false;
  private boolean fResourceResolverChanged = false;
  
  public ValidatorImpl(XSGrammarPoolContainer paramXSGrammarPoolContainer)
  {
    this.fComponentManager = new XMLSchemaValidatorComponentManager(paramXSGrammarPoolContainer);
    setErrorHandler(null);
    setResourceResolver(null);
  }
  
  public void validate(Source paramSource, Result paramResult)
    throws SAXException, IOException
  {
    if ((paramSource instanceof SAXSource))
    {
      if (this.fSAXValidatorHelper == null) {
        this.fSAXValidatorHelper = new ValidatorHandlerImpl(this.fComponentManager);
      }
      this.fSAXValidatorHelper.validate(paramSource, paramResult);
    }
    else if ((paramSource instanceof DOMSource))
    {
      if (this.fDOMValidatorHelper == null) {
        this.fDOMValidatorHelper = new DOMValidatorHelper(this.fComponentManager);
      }
      this.fDOMValidatorHelper.validate(paramSource, paramResult);
    }
    else if ((paramSource instanceof StreamSource))
    {
      if (this.fStreamValidatorHelper == null) {
        this.fStreamValidatorHelper = new StreamValidatorHelper(this.fComponentManager);
      }
      this.fStreamValidatorHelper.validate(paramSource, paramResult);
    }
    else
    {
      if (paramSource == null) {
        throw new NullPointerException(JAXPValidationMessageFormatter.formatMessage(Locale.getDefault(), "SourceParameterNull", null));
      }
      throw new IllegalArgumentException(JAXPValidationMessageFormatter.formatMessage(Locale.getDefault(), "SourceNotAccepted", new Object[] { paramSource.getClass().getName() }));
    }
  }
  
  public void setErrorHandler(ErrorHandler paramErrorHandler)
  {
    this.fErrorHandlerChanged = (paramErrorHandler != null);
    this.fComponentManager.setErrorHandler(paramErrorHandler);
  }
  
  public ErrorHandler getErrorHandler()
  {
    return this.fComponentManager.getErrorHandler();
  }
  
  public void setResourceResolver(LSResourceResolver paramLSResourceResolver)
  {
    this.fResourceResolverChanged = (paramLSResourceResolver != null);
    this.fComponentManager.setResourceResolver(paramLSResourceResolver);
  }
  
  public LSResourceResolver getResourceResolver()
  {
    return this.fComponentManager.getResourceResolver();
  }
  
  public boolean getFeature(String paramString)
    throws SAXNotRecognizedException, SAXNotSupportedException
  {
    if (paramString == null) {
      throw new NullPointerException();
    }
    try
    {
      return this.fComponentManager.getFeature(paramString);
    }
    catch (XMLConfigurationException localXMLConfigurationException)
    {
      String str1 = localXMLConfigurationException.getIdentifier();
      String str2 = localXMLConfigurationException.getType() == 0 ? "feature-not-recognized" : "feature-not-supported";
      throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(Locale.getDefault(), str2, new Object[] { str1 }));
    }
  }
  
  public void setFeature(String paramString, boolean paramBoolean)
    throws SAXNotRecognizedException, SAXNotSupportedException
  {
    if (paramString == null) {
      throw new NullPointerException();
    }
    try
    {
      this.fComponentManager.setFeature(paramString, paramBoolean);
    }
    catch (XMLConfigurationException localXMLConfigurationException)
    {
      String str1 = localXMLConfigurationException.getIdentifier();
      String str2 = localXMLConfigurationException.getType() == 0 ? "feature-not-recognized" : "feature-not-supported";
      throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(Locale.getDefault(), str2, new Object[] { str1 }));
    }
    this.fConfigurationChanged = true;
  }
  
  public Object getProperty(String paramString)
    throws SAXNotRecognizedException, SAXNotSupportedException
  {
    if (paramString == null) {
      throw new NullPointerException();
    }
    if ("http://apache.org/xml/properties/dom/current-element-node".equals(paramString)) {
      return this.fDOMValidatorHelper != null ? this.fDOMValidatorHelper.getCurrentElement() : null;
    }
    try
    {
      return this.fComponentManager.getProperty(paramString);
    }
    catch (XMLConfigurationException localXMLConfigurationException)
    {
      String str1 = localXMLConfigurationException.getIdentifier();
      String str2 = localXMLConfigurationException.getType() == 0 ? "property-not-recognized" : "property-not-supported";
      throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(Locale.getDefault(), str2, new Object[] { str1 }));
    }
  }
  
  public void setProperty(String paramString, Object paramObject)
    throws SAXNotRecognizedException, SAXNotSupportedException
  {
    if (paramString == null) {
      throw new NullPointerException();
    }
    if ("http://apache.org/xml/properties/dom/current-element-node".equals(paramString)) {
      throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(Locale.getDefault(), "property-read-only", new Object[] { paramString }));
    }
    try
    {
      this.fComponentManager.setProperty(paramString, paramObject);
    }
    catch (XMLConfigurationException localXMLConfigurationException)
    {
      String str1 = localXMLConfigurationException.getIdentifier();
      String str2 = localXMLConfigurationException.getType() == 0 ? "property-not-recognized" : "property-not-supported";
      throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(Locale.getDefault(), str2, new Object[] { str1 }));
    }
    this.fConfigurationChanged = true;
  }
  
  public void reset()
  {
    if (this.fConfigurationChanged)
    {
      this.fComponentManager.restoreInitialState();
      setErrorHandler(null);
      setResourceResolver(null);
      this.fConfigurationChanged = false;
      this.fErrorHandlerChanged = false;
      this.fResourceResolverChanged = false;
    }
    else
    {
      if (this.fErrorHandlerChanged)
      {
        setErrorHandler(null);
        this.fErrorHandlerChanged = false;
      }
      if (this.fResourceResolverChanged)
      {
        setResourceResolver(null);
        this.fResourceResolverChanged = false;
      }
    }
  }
  
  public ElementPSVI getElementPSVI()
  {
    return this.fSAXValidatorHelper != null ? this.fSAXValidatorHelper.getElementPSVI() : null;
  }
  
  public AttributePSVI getAttributePSVI(int paramInt)
  {
    return this.fSAXValidatorHelper != null ? this.fSAXValidatorHelper.getAttributePSVI(paramInt) : null;
  }
  
  public AttributePSVI getAttributePSVIByName(String paramString1, String paramString2)
  {
    return this.fSAXValidatorHelper != null ? this.fSAXValidatorHelper.getAttributePSVIByName(paramString1, paramString2) : null;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.jaxp.validation.ValidatorImpl
 * JD-Core Version:    0.7.0.1
 */