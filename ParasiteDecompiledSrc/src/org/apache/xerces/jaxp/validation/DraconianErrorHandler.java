package org.apache.xerces.jaxp.validation;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

final class DraconianErrorHandler
  implements ErrorHandler
{
  private static final DraconianErrorHandler ERROR_HANDLER_INSTANCE = new DraconianErrorHandler();
  
  public static DraconianErrorHandler getInstance()
  {
    return ERROR_HANDLER_INSTANCE;
  }
  
  public void warning(SAXParseException paramSAXParseException)
    throws SAXException
  {}
  
  public void error(SAXParseException paramSAXParseException)
    throws SAXException
  {
    throw paramSAXParseException;
  }
  
  public void fatalError(SAXParseException paramSAXParseException)
    throws SAXException
  {
    throw paramSAXParseException;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.jaxp.validation.DraconianErrorHandler
 * JD-Core Version:    0.7.0.1
 */