package org.apache.wml.dom;

import org.apache.wml.WMLDOMImplementation;
import org.apache.xerces.dom.CoreDocumentImpl;
import org.apache.xerces.dom.DOMImplementationImpl;
import org.apache.xerces.dom.DOMMessageFormatter;
import org.apache.xerces.dom.NodeImpl;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class WMLDOMImplementationImpl
  extends DOMImplementationImpl
  implements WMLDOMImplementation
{
  static final DOMImplementationImpl singleton = new WMLDOMImplementationImpl();
  
  public static DOMImplementation getDOMImplementation()
  {
    return singleton;
  }
  
  public Document createDocument(String paramString1, String paramString2, DocumentType paramDocumentType)
    throws DOMException
  {
    if ((paramDocumentType != null) && (paramDocumentType.getOwnerDocument() != null)) {
      throw new DOMException((short)4, DOMMessageFormatter.formatMessage("http://www.w3.org/TR/1998/REC-xml-19980210", "WRONG_DOCUMENT_ERR", null));
    }
    WMLDocumentImpl localWMLDocumentImpl = new WMLDocumentImpl(paramDocumentType);
    if ((paramString2 != null) || (paramString1 != null))
    {
      Element localElement = localWMLDocumentImpl.createElementNS(paramString1, paramString2);
      localWMLDocumentImpl.appendChild(localElement);
    }
    return localWMLDocumentImpl;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.wml.dom.WMLDOMImplementationImpl
 * JD-Core Version:    0.7.0.1
 */