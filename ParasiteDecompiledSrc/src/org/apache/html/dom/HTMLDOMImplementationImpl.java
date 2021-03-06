package org.apache.html.dom;

import org.apache.xerces.dom.DOMImplementationImpl;
import org.w3c.dom.DOMException;
import org.w3c.dom.html.HTMLDOMImplementation;
import org.w3c.dom.html.HTMLDocument;

public class HTMLDOMImplementationImpl
  extends DOMImplementationImpl
  implements HTMLDOMImplementation
{
  private static HTMLDOMImplementation _instance = new HTMLDOMImplementationImpl();
  
  public final HTMLDocument createHTMLDocument(String paramString)
    throws DOMException
  {
    if (paramString == null) {
      throw new NullPointerException("HTM014 Argument 'title' is null.");
    }
    HTMLDocumentImpl localHTMLDocumentImpl = new HTMLDocumentImpl();
    localHTMLDocumentImpl.setTitle(paramString);
    return localHTMLDocumentImpl;
  }
  
  public static HTMLDOMImplementation getHTMLDOMImplementation()
  {
    return _instance;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.html.dom.HTMLDOMImplementationImpl
 * JD-Core Version:    0.7.0.1
 */