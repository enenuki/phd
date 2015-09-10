package org.apache.xerces.jaxp;

import java.util.Hashtable;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;

public class SAXParserFactoryImpl
  extends SAXParserFactory
{
  private static final String NAMESPACES_FEATURE = "http://xml.org/sax/features/namespaces";
  private static final String VALIDATION_FEATURE = "http://xml.org/sax/features/validation";
  private static final String XINCLUDE_FEATURE = "http://apache.org/xml/features/xinclude";
  private Hashtable features;
  private Schema grammar;
  private boolean isXIncludeAware;
  private boolean fSecureProcess = false;
  
  public SAXParser newSAXParser()
    throws ParserConfigurationException
  {
    SAXParserImpl localSAXParserImpl;
    try
    {
      localSAXParserImpl = new SAXParserImpl(this, this.features, this.fSecureProcess);
    }
    catch (SAXException localSAXException)
    {
      throw new ParserConfigurationException(localSAXException.getMessage());
    }
    return localSAXParserImpl;
  }
  
  private SAXParserImpl newSAXParserImpl()
    throws ParserConfigurationException, SAXNotRecognizedException, SAXNotSupportedException
  {
    SAXParserImpl localSAXParserImpl;
    try
    {
      localSAXParserImpl = new SAXParserImpl(this, this.features);
    }
    catch (SAXNotSupportedException localSAXNotSupportedException)
    {
      throw localSAXNotSupportedException;
    }
    catch (SAXNotRecognizedException localSAXNotRecognizedException)
    {
      throw localSAXNotRecognizedException;
    }
    catch (SAXException localSAXException)
    {
      throw new ParserConfigurationException(localSAXException.getMessage());
    }
    return localSAXParserImpl;
  }
  
  public void setFeature(String paramString, boolean paramBoolean)
    throws ParserConfigurationException, SAXNotRecognizedException, SAXNotSupportedException
  {
    if (paramString == null) {
      throw new NullPointerException();
    }
    if (paramString.equals("http://javax.xml.XMLConstants/feature/secure-processing"))
    {
      this.fSecureProcess = paramBoolean;
      return;
    }
    if (paramString.equals("http://xml.org/sax/features/namespaces"))
    {
      setNamespaceAware(paramBoolean);
      return;
    }
    if (paramString.equals("http://xml.org/sax/features/validation"))
    {
      setValidating(paramBoolean);
      return;
    }
    if (paramString.equals("http://apache.org/xml/features/xinclude"))
    {
      setXIncludeAware(paramBoolean);
      return;
    }
    if (this.features == null) {
      this.features = new Hashtable();
    }
    this.features.put(paramString, paramBoolean ? Boolean.TRUE : Boolean.FALSE);
    try
    {
      newSAXParserImpl();
    }
    catch (SAXNotSupportedException localSAXNotSupportedException)
    {
      this.features.remove(paramString);
      throw localSAXNotSupportedException;
    }
    catch (SAXNotRecognizedException localSAXNotRecognizedException)
    {
      this.features.remove(paramString);
      throw localSAXNotRecognizedException;
    }
  }
  
  public boolean getFeature(String paramString)
    throws ParserConfigurationException, SAXNotRecognizedException, SAXNotSupportedException
  {
    if (paramString == null) {
      throw new NullPointerException();
    }
    if (paramString.equals("http://javax.xml.XMLConstants/feature/secure-processing")) {
      return this.fSecureProcess;
    }
    if (paramString.equals("http://xml.org/sax/features/namespaces")) {
      return isNamespaceAware();
    }
    if (paramString.equals("http://xml.org/sax/features/validation")) {
      return isValidating();
    }
    if (paramString.equals("http://apache.org/xml/features/xinclude")) {
      return isXIncludeAware();
    }
    return newSAXParserImpl().getXMLReader().getFeature(paramString);
  }
  
  public Schema getSchema()
  {
    return this.grammar;
  }
  
  public void setSchema(Schema paramSchema)
  {
    this.grammar = paramSchema;
  }
  
  public boolean isXIncludeAware()
  {
    return this.isXIncludeAware;
  }
  
  public void setXIncludeAware(boolean paramBoolean)
  {
    this.isXIncludeAware = paramBoolean;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.jaxp.SAXParserFactoryImpl
 * JD-Core Version:    0.7.0.1
 */