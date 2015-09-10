package org.xml.sax;

public abstract interface ErrorHandler
{
  public abstract void warning(SAXParseException paramSAXParseException)
    throws SAXException;
  
  public abstract void error(SAXParseException paramSAXParseException)
    throws SAXException;
  
  public abstract void fatalError(SAXParseException paramSAXParseException)
    throws SAXException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.xml.sax.ErrorHandler
 * JD-Core Version:    0.7.0.1
 */