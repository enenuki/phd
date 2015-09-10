package org.apache.html.dom;

import java.util.Vector;
import org.apache.xerces.dom.ChildNode;
import org.apache.xerces.dom.CoreDocumentImpl;
import org.apache.xerces.dom.ElementImpl;
import org.apache.xerces.dom.NodeImpl;
import org.apache.xerces.dom.ParentNode;
import org.apache.xerces.dom.ProcessingInstructionImpl;
import org.apache.xerces.dom.TextImpl;
import org.w3c.dom.Node;
import org.w3c.dom.html.HTMLDocument;
import org.xml.sax.AttributeList;
import org.xml.sax.DocumentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class HTMLBuilder
  implements DocumentHandler
{
  protected HTMLDocumentImpl _document;
  protected ElementImpl _current;
  private boolean _ignoreWhitespace = true;
  private boolean _done = true;
  protected Vector _preRootNodes;
  
  public void startDocument()
    throws SAXException
  {
    if (!this._done) {
      throw new SAXException("HTM001 State error: startDocument fired twice on one builder.");
    }
    this._document = null;
    this._done = false;
  }
  
  public void endDocument()
    throws SAXException
  {
    if (this._document == null) {
      throw new SAXException("HTM002 State error: document never started or missing document element.");
    }
    if (this._current != null) {
      throw new SAXException("HTM003 State error: document ended before end of document element.");
    }
    this._current = null;
    this._done = true;
  }
  
  public synchronized void startElement(String paramString, AttributeList paramAttributeList)
    throws SAXException
  {
    if (paramString == null) {
      throw new SAXException("HTM004 Argument 'tagName' is null.");
    }
    ElementImpl localElementImpl;
    int i;
    if (this._document == null)
    {
      this._document = new HTMLDocumentImpl();
      localElementImpl = (ElementImpl)this._document.getDocumentElement();
      this._current = localElementImpl;
      if (this._current == null) {
        throw new SAXException("HTM005 State error: Document.getDocumentElement returns null.");
      }
      if (this._preRootNodes != null)
      {
        i = this._preRootNodes.size();
        while (i-- > 0) {
          this._document.insertBefore((Node)this._preRootNodes.elementAt(i), localElementImpl);
        }
        this._preRootNodes = null;
      }
    }
    else
    {
      if (this._current == null) {
        throw new SAXException("HTM006 State error: startElement called after end of document element.");
      }
      localElementImpl = (ElementImpl)this._document.createElement(paramString);
      this._current.appendChild(localElementImpl);
      this._current = localElementImpl;
    }
    if (paramAttributeList != null) {
      for (i = 0; i < paramAttributeList.getLength(); i++) {
        localElementImpl.setAttribute(paramAttributeList.getName(i), paramAttributeList.getValue(i));
      }
    }
  }
  
  public void endElement(String paramString)
    throws SAXException
  {
    if (this._current == null) {
      throw new SAXException("HTM007 State error: endElement called with no current node.");
    }
    if (!this._current.getNodeName().equalsIgnoreCase(paramString)) {
      throw new SAXException("HTM008 State error: mismatch in closing tag name " + paramString + "\n" + paramString);
    }
    if (this._current.getParentNode() == this._current.getOwnerDocument()) {
      this._current = null;
    } else {
      this._current = ((ElementImpl)this._current.getParentNode());
    }
  }
  
  public void characters(String paramString)
    throws SAXException
  {
    if (this._current == null) {
      throw new SAXException("HTM009 State error: character data found outside of root element.");
    }
    this._current.appendChild(new TextImpl(this._document, paramString));
  }
  
  public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    if (this._current == null) {
      throw new SAXException("HTM010 State error: character data found outside of root element.");
    }
    this._current.appendChild(new TextImpl(this._document, new String(paramArrayOfChar, paramInt1, paramInt2)));
  }
  
  public void ignorableWhitespace(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    if (!this._ignoreWhitespace) {
      this._current.appendChild(new TextImpl(this._document, new String(paramArrayOfChar, paramInt1, paramInt2)));
    }
  }
  
  public void processingInstruction(String paramString1, String paramString2)
    throws SAXException
  {
    if ((this._current == null) && (this._document == null))
    {
      if (this._preRootNodes == null) {
        this._preRootNodes = new Vector();
      }
      this._preRootNodes.addElement(new ProcessingInstructionImpl(null, paramString1, paramString2));
    }
    else if ((this._current == null) && (this._document != null))
    {
      this._document.appendChild(new ProcessingInstructionImpl(this._document, paramString1, paramString2));
    }
    else
    {
      this._current.appendChild(new ProcessingInstructionImpl(this._document, paramString1, paramString2));
    }
  }
  
  public HTMLDocument getHTMLDocument()
  {
    return this._document;
  }
  
  public void setDocumentLocator(Locator paramLocator) {}
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.html.dom.HTMLBuilder
 * JD-Core Version:    0.7.0.1
 */