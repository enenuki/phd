package org.apache.xerces.util;

import org.apache.xerces.xni.XMLLocator;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.parser.XMLErrorHandler;
import org.apache.xerces.xni.parser.XMLParseException;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class ErrorHandlerWrapper
  implements XMLErrorHandler
{
  protected ErrorHandler fErrorHandler;
  
  public ErrorHandlerWrapper() {}
  
  public ErrorHandlerWrapper(ErrorHandler paramErrorHandler)
  {
    setErrorHandler(paramErrorHandler);
  }
  
  public void setErrorHandler(ErrorHandler paramErrorHandler)
  {
    this.fErrorHandler = paramErrorHandler;
  }
  
  public ErrorHandler getErrorHandler()
  {
    return this.fErrorHandler;
  }
  
  public void warning(String paramString1, String paramString2, XMLParseException paramXMLParseException)
    throws XNIException
  {
    if (this.fErrorHandler != null)
    {
      SAXParseException localSAXParseException1 = createSAXParseException(paramXMLParseException);
      try
      {
        this.fErrorHandler.warning(localSAXParseException1);
      }
      catch (SAXParseException localSAXParseException2)
      {
        throw createXMLParseException(localSAXParseException2);
      }
      catch (SAXException localSAXException)
      {
        throw createXNIException(localSAXException);
      }
    }
  }
  
  public void error(String paramString1, String paramString2, XMLParseException paramXMLParseException)
    throws XNIException
  {
    if (this.fErrorHandler != null)
    {
      SAXParseException localSAXParseException1 = createSAXParseException(paramXMLParseException);
      try
      {
        this.fErrorHandler.error(localSAXParseException1);
      }
      catch (SAXParseException localSAXParseException2)
      {
        throw createXMLParseException(localSAXParseException2);
      }
      catch (SAXException localSAXException)
      {
        throw createXNIException(localSAXException);
      }
    }
  }
  
  public void fatalError(String paramString1, String paramString2, XMLParseException paramXMLParseException)
    throws XNIException
  {
    if (this.fErrorHandler != null)
    {
      SAXParseException localSAXParseException1 = createSAXParseException(paramXMLParseException);
      try
      {
        this.fErrorHandler.fatalError(localSAXParseException1);
      }
      catch (SAXParseException localSAXParseException2)
      {
        throw createXMLParseException(localSAXParseException2);
      }
      catch (SAXException localSAXException)
      {
        throw createXNIException(localSAXException);
      }
    }
  }
  
  protected static SAXParseException createSAXParseException(XMLParseException paramXMLParseException)
  {
    return new SAXParseException(paramXMLParseException.getMessage(), paramXMLParseException.getPublicId(), paramXMLParseException.getExpandedSystemId(), paramXMLParseException.getLineNumber(), paramXMLParseException.getColumnNumber(), paramXMLParseException.getException());
  }
  
  protected static XMLParseException createXMLParseException(SAXParseException paramSAXParseException)
  {
    String str1 = paramSAXParseException.getPublicId();
    String str2 = paramSAXParseException.getSystemId();
    int i = paramSAXParseException.getLineNumber();
    int j = paramSAXParseException.getColumnNumber();
    XMLLocator local1 = new XMLLocator()
    {
      private final String val$fPublicId;
      private final String val$fExpandedSystemId;
      private final int val$fColumnNumber;
      private final int val$fLineNumber;
      
      public String getPublicId()
      {
        return this.val$fPublicId;
      }
      
      public String getExpandedSystemId()
      {
        return this.val$fExpandedSystemId;
      }
      
      public String getBaseSystemId()
      {
        return null;
      }
      
      public String getLiteralSystemId()
      {
        return null;
      }
      
      public int getColumnNumber()
      {
        return this.val$fColumnNumber;
      }
      
      public int getLineNumber()
      {
        return this.val$fLineNumber;
      }
      
      public int getCharacterOffset()
      {
        return -1;
      }
      
      public String getEncoding()
      {
        return null;
      }
      
      public String getXMLVersion()
      {
        return null;
      }
    };
    return new XMLParseException(local1, paramSAXParseException.getMessage(), paramSAXParseException);
  }
  
  protected static XNIException createXNIException(SAXException paramSAXException)
  {
    return new XNIException(paramSAXException.getMessage(), paramSAXException);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.util.ErrorHandlerWrapper
 * JD-Core Version:    0.7.0.1
 */