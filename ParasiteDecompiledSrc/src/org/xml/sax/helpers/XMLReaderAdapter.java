package org.xml.sax.helpers;

import java.io.IOException;
import java.util.Locale;
import org.xml.sax.AttributeList;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.DocumentHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;

public class XMLReaderAdapter
  implements Parser, ContentHandler
{
  XMLReader xmlReader;
  DocumentHandler documentHandler;
  AttributesAdapter qAtts;
  
  public XMLReaderAdapter()
    throws SAXException
  {
    setup(XMLReaderFactory.createXMLReader());
  }
  
  public XMLReaderAdapter(XMLReader paramXMLReader)
  {
    setup(paramXMLReader);
  }
  
  private void setup(XMLReader paramXMLReader)
  {
    if (paramXMLReader == null) {
      throw new NullPointerException("XMLReader must not be null");
    }
    this.xmlReader = paramXMLReader;
    this.qAtts = new AttributesAdapter();
  }
  
  public void setLocale(Locale paramLocale)
    throws SAXException
  {
    throw new SAXNotSupportedException("setLocale not supported");
  }
  
  public void setEntityResolver(EntityResolver paramEntityResolver)
  {
    this.xmlReader.setEntityResolver(paramEntityResolver);
  }
  
  public void setDTDHandler(DTDHandler paramDTDHandler)
  {
    this.xmlReader.setDTDHandler(paramDTDHandler);
  }
  
  public void setDocumentHandler(DocumentHandler paramDocumentHandler)
  {
    this.documentHandler = paramDocumentHandler;
  }
  
  public void setErrorHandler(ErrorHandler paramErrorHandler)
  {
    this.xmlReader.setErrorHandler(paramErrorHandler);
  }
  
  public void parse(String paramString)
    throws IOException, SAXException
  {
    parse(new InputSource(paramString));
  }
  
  public void parse(InputSource paramInputSource)
    throws IOException, SAXException
  {
    setupXMLReader();
    this.xmlReader.parse(paramInputSource);
  }
  
  private void setupXMLReader()
    throws SAXException
  {
    this.xmlReader.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
    try
    {
      this.xmlReader.setFeature("http://xml.org/sax/features/namespaces", false);
    }
    catch (SAXException localSAXException) {}
    this.xmlReader.setContentHandler(this);
  }
  
  public void setDocumentLocator(Locator paramLocator)
  {
    if (this.documentHandler != null) {
      this.documentHandler.setDocumentLocator(paramLocator);
    }
  }
  
  public void startDocument()
    throws SAXException
  {
    if (this.documentHandler != null) {
      this.documentHandler.startDocument();
    }
  }
  
  public void endDocument()
    throws SAXException
  {
    if (this.documentHandler != null) {
      this.documentHandler.endDocument();
    }
  }
  
  public void startPrefixMapping(String paramString1, String paramString2) {}
  
  public void endPrefixMapping(String paramString) {}
  
  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    if (this.documentHandler != null)
    {
      this.qAtts.setAttributes(paramAttributes);
      this.documentHandler.startElement(paramString3, this.qAtts);
    }
  }
  
  public void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    if (this.documentHandler != null) {
      this.documentHandler.endElement(paramString3);
    }
  }
  
  public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    if (this.documentHandler != null) {
      this.documentHandler.characters(paramArrayOfChar, paramInt1, paramInt2);
    }
  }
  
  public void ignorableWhitespace(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    if (this.documentHandler != null) {
      this.documentHandler.ignorableWhitespace(paramArrayOfChar, paramInt1, paramInt2);
    }
  }
  
  public void processingInstruction(String paramString1, String paramString2)
    throws SAXException
  {
    if (this.documentHandler != null) {
      this.documentHandler.processingInstruction(paramString1, paramString2);
    }
  }
  
  public void skippedEntity(String paramString)
    throws SAXException
  {}
  
  final class AttributesAdapter
    implements AttributeList
  {
    private Attributes attributes;
    
    AttributesAdapter() {}
    
    void setAttributes(Attributes paramAttributes)
    {
      this.attributes = paramAttributes;
    }
    
    public int getLength()
    {
      return this.attributes.getLength();
    }
    
    public String getName(int paramInt)
    {
      return this.attributes.getQName(paramInt);
    }
    
    public String getType(int paramInt)
    {
      return this.attributes.getType(paramInt);
    }
    
    public String getValue(int paramInt)
    {
      return this.attributes.getValue(paramInt);
    }
    
    public String getType(String paramString)
    {
      return this.attributes.getType(paramString);
    }
    
    public String getValue(String paramString)
    {
      return this.attributes.getValue(paramString);
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.xml.sax.helpers.XMLReaderAdapter
 * JD-Core Version:    0.7.0.1
 */