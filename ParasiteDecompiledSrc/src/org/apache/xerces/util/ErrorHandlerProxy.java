package org.apache.xerces.util;

import org.apache.xerces.xni.parser.XMLErrorHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public abstract class ErrorHandlerProxy
  implements ErrorHandler
{
  public void error(SAXParseException paramSAXParseException)
    throws SAXException
  {
    XMLErrorHandler localXMLErrorHandler = getErrorHandler();
    if ((localXMLErrorHandler instanceof ErrorHandlerWrapper)) {
      ((ErrorHandlerWrapper)localXMLErrorHandler).fErrorHandler.error(paramSAXParseException);
    } else {
      localXMLErrorHandler.error("", "", ErrorHandlerWrapper.createXMLParseException(paramSAXParseException));
    }
  }
  
  public void fatalError(SAXParseException paramSAXParseException)
    throws SAXException
  {
    XMLErrorHandler localXMLErrorHandler = getErrorHandler();
    if ((localXMLErrorHandler instanceof ErrorHandlerWrapper)) {
      ((ErrorHandlerWrapper)localXMLErrorHandler).fErrorHandler.fatalError(paramSAXParseException);
    } else {
      localXMLErrorHandler.fatalError("", "", ErrorHandlerWrapper.createXMLParseException(paramSAXParseException));
    }
  }
  
  public void warning(SAXParseException paramSAXParseException)
    throws SAXException
  {
    XMLErrorHandler localXMLErrorHandler = getErrorHandler();
    if ((localXMLErrorHandler instanceof ErrorHandlerWrapper)) {
      ((ErrorHandlerWrapper)localXMLErrorHandler).fErrorHandler.warning(paramSAXParseException);
    } else {
      localXMLErrorHandler.warning("", "", ErrorHandlerWrapper.createXMLParseException(paramSAXParseException));
    }
  }
  
  protected abstract XMLErrorHandler getErrorHandler();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.util.ErrorHandlerProxy
 * JD-Core Version:    0.7.0.1
 */