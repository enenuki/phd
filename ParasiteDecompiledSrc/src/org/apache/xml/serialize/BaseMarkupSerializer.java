package org.apache.xml.serialize;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Vector;
import org.apache.xerces.dom.DOMErrorImpl;
import org.apache.xerces.dom.DOMLocatorImpl;
import org.apache.xerces.dom.DOMMessageFormatter;
import org.apache.xerces.util.XMLChar;
import org.w3c.dom.DOMError;
import org.w3c.dom.DOMErrorHandler;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.ls.LSException;
import org.w3c.dom.ls.LSSerializerFilter;
import org.w3c.dom.traversal.NodeFilter;
import org.xml.sax.AttributeList;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.DocumentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DeclHandler;
import org.xml.sax.ext.LexicalHandler;

/**
 * @deprecated
 */
public abstract class BaseMarkupSerializer
  implements ContentHandler, DocumentHandler, LexicalHandler, DTDHandler, DeclHandler, DOMSerializer, Serializer
{
  protected short features = -1;
  protected DOMErrorHandler fDOMErrorHandler;
  protected final DOMErrorImpl fDOMError = new DOMErrorImpl();
  protected LSSerializerFilter fDOMFilter;
  protected EncodingInfo _encodingInfo;
  private ElementState[] _elementStates = new ElementState[10];
  private int _elementStateCount;
  private Vector _preRoot;
  protected boolean _started;
  private boolean _prepared;
  protected Hashtable _prefixes;
  protected String _docTypePublicId;
  protected String _docTypeSystemId;
  protected OutputFormat _format;
  protected Printer _printer;
  protected boolean _indenting;
  protected final StringBuffer fStrBuffer = new StringBuffer(40);
  private Writer _writer;
  private OutputStream _output;
  protected Node fCurrentNode = null;
  
  protected BaseMarkupSerializer(OutputFormat paramOutputFormat)
  {
    for (int i = 0; i < this._elementStates.length; i++) {
      this._elementStates[i] = new ElementState();
    }
    this._format = paramOutputFormat;
  }
  
  public DocumentHandler asDocumentHandler()
    throws IOException
  {
    prepare();
    return this;
  }
  
  public ContentHandler asContentHandler()
    throws IOException
  {
    prepare();
    return this;
  }
  
  public DOMSerializer asDOMSerializer()
    throws IOException
  {
    prepare();
    return this;
  }
  
  public void setOutputByteStream(OutputStream paramOutputStream)
  {
    if (paramOutputStream == null)
    {
      String str = DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "ArgumentIsNull", new Object[] { "output" });
      throw new NullPointerException(str);
    }
    this._output = paramOutputStream;
    this._writer = null;
    reset();
  }
  
  public void setOutputCharStream(Writer paramWriter)
  {
    if (paramWriter == null)
    {
      String str = DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "ArgumentIsNull", new Object[] { "writer" });
      throw new NullPointerException(str);
    }
    this._writer = paramWriter;
    this._output = null;
    reset();
  }
  
  public void setOutputFormat(OutputFormat paramOutputFormat)
  {
    if (paramOutputFormat == null)
    {
      String str = DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "ArgumentIsNull", new Object[] { "format" });
      throw new NullPointerException(str);
    }
    this._format = paramOutputFormat;
    reset();
  }
  
  public boolean reset()
  {
    if (this._elementStateCount > 1)
    {
      String str = DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "ResetInMiddle", null);
      throw new IllegalStateException(str);
    }
    this._prepared = false;
    this.fCurrentNode = null;
    this.fStrBuffer.setLength(0);
    return true;
  }
  
  protected void prepare()
    throws IOException
  {
    if (this._prepared) {
      return;
    }
    if ((this._writer == null) && (this._output == null))
    {
      localObject = DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "NoWriterSupplied", null);
      throw new IOException((String)localObject);
    }
    this._encodingInfo = this._format.getEncodingInfo();
    if (this._output != null) {
      this._writer = this._encodingInfo.getWriter(this._output);
    }
    if (this._format.getIndenting())
    {
      this._indenting = true;
      this._printer = new IndentPrinter(this._writer, this._format);
    }
    else
    {
      this._indenting = false;
      this._printer = new Printer(this._writer, this._format);
    }
    this._elementStateCount = 0;
    Object localObject = this._elementStates[0];
    ((ElementState)localObject).namespaceURI = null;
    ((ElementState)localObject).localName = null;
    ((ElementState)localObject).rawName = null;
    ((ElementState)localObject).preserveSpace = this._format.getPreserveSpace();
    ((ElementState)localObject).empty = true;
    ((ElementState)localObject).afterElement = false;
    ((ElementState)localObject).afterComment = false;
    ((ElementState)localObject).doCData = (((ElementState)localObject).inCData = 0);
    ((ElementState)localObject).prefixes = null;
    this._docTypePublicId = this._format.getDoctypePublic();
    this._docTypeSystemId = this._format.getDoctypeSystem();
    this._started = false;
    this._prepared = true;
  }
  
  public void serialize(Element paramElement)
    throws IOException
  {
    reset();
    prepare();
    serializeNode(paramElement);
    this._printer.flush();
    if (this._printer.getException() != null) {
      throw this._printer.getException();
    }
  }
  
  public void serialize(DocumentFragment paramDocumentFragment)
    throws IOException
  {
    reset();
    prepare();
    serializeNode(paramDocumentFragment);
    this._printer.flush();
    if (this._printer.getException() != null) {
      throw this._printer.getException();
    }
  }
  
  public void serialize(Document paramDocument)
    throws IOException
  {
    reset();
    prepare();
    serializeNode(paramDocument);
    serializePreRoot();
    this._printer.flush();
    if (this._printer.getException() != null) {
      throw this._printer.getException();
    }
  }
  
  public void startDocument()
    throws SAXException
  {
    try
    {
      prepare();
    }
    catch (IOException localIOException)
    {
      throw new SAXException(localIOException.toString());
    }
  }
  
  public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    try
    {
      ElementState localElementState = content();
      int i;
      if ((localElementState.inCData) || (localElementState.doCData))
      {
        if (!localElementState.inCData)
        {
          this._printer.printText("<![CDATA[");
          localElementState.inCData = true;
        }
        i = this._printer.getNextIndent();
        this._printer.setNextIndent(0);
        int j = paramInt1 + paramInt2;
        for (int k = paramInt1; k < j; k++)
        {
          char c = paramArrayOfChar[k];
          if ((c == ']') && (k + 2 < j) && (paramArrayOfChar[(k + 1)] == ']') && (paramArrayOfChar[(k + 2)] == '>'))
          {
            this._printer.printText("]]]]><![CDATA[>");
            k += 2;
          }
          else if (!XMLChar.isValid(c))
          {
            k++;
            if (k < j) {
              surrogates(c, paramArrayOfChar[k], true);
            } else {
              fatalError("The character '" + c + "' is an invalid XML character");
            }
          }
          else if (((c >= ' ') && (this._encodingInfo.isPrintable(c)) && (c != '÷')) || (c == '\n') || (c == '\r') || (c == '\t'))
          {
            this._printer.printText(c);
          }
          else
          {
            this._printer.printText("]]>&#x");
            this._printer.printText(Integer.toHexString(c));
            this._printer.printText(";<![CDATA[");
          }
        }
        this._printer.setNextIndent(i);
      }
      else if (localElementState.preserveSpace)
      {
        i = this._printer.getNextIndent();
        this._printer.setNextIndent(0);
        printText(paramArrayOfChar, paramInt1, paramInt2, true, localElementState.unescaped);
        this._printer.setNextIndent(i);
      }
      else
      {
        printText(paramArrayOfChar, paramInt1, paramInt2, false, localElementState.unescaped);
      }
    }
    catch (IOException localIOException)
    {
      throw new SAXException(localIOException);
    }
  }
  
  public void ignorableWhitespace(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    try
    {
      content();
      if (this._indenting)
      {
        this._printer.setThisIndent(0);
        for (int i = paramInt1; paramInt2-- > 0; i++) {
          this._printer.printText(paramArrayOfChar[i]);
        }
      }
    }
    catch (IOException localIOException)
    {
      throw new SAXException(localIOException);
    }
  }
  
  public final void processingInstruction(String paramString1, String paramString2)
    throws SAXException
  {
    try
    {
      processingInstructionIO(paramString1, paramString2);
    }
    catch (IOException localIOException)
    {
      throw new SAXException(localIOException);
    }
  }
  
  public void processingInstructionIO(String paramString1, String paramString2)
    throws IOException
  {
    ElementState localElementState = content();
    int i = paramString1.indexOf("?>");
    if (i >= 0) {
      this.fStrBuffer.append("<?").append(paramString1.substring(0, i));
    } else {
      this.fStrBuffer.append("<?").append(paramString1);
    }
    if (paramString2 != null)
    {
      this.fStrBuffer.append(' ');
      i = paramString2.indexOf("?>");
      if (i >= 0) {
        this.fStrBuffer.append(paramString2.substring(0, i));
      } else {
        this.fStrBuffer.append(paramString2);
      }
    }
    this.fStrBuffer.append("?>");
    if (isDocumentState())
    {
      if (this._preRoot == null) {
        this._preRoot = new Vector();
      }
      this._preRoot.addElement(this.fStrBuffer.toString());
    }
    else
    {
      this._printer.indent();
      printText(this.fStrBuffer.toString(), true, true);
      this._printer.unindent();
      if (this._indenting) {
        localElementState.afterElement = true;
      }
    }
    this.fStrBuffer.setLength(0);
  }
  
  public void comment(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    try
    {
      comment(new String(paramArrayOfChar, paramInt1, paramInt2));
    }
    catch (IOException localIOException)
    {
      throw new SAXException(localIOException);
    }
  }
  
  public void comment(String paramString)
    throws IOException
  {
    if (this._format.getOmitComments()) {
      return;
    }
    ElementState localElementState = content();
    int i = paramString.indexOf("-->");
    if (i >= 0) {
      this.fStrBuffer.append("<!--").append(paramString.substring(0, i)).append("-->");
    } else {
      this.fStrBuffer.append("<!--").append(paramString).append("-->");
    }
    if (isDocumentState())
    {
      if (this._preRoot == null) {
        this._preRoot = new Vector();
      }
      this._preRoot.addElement(this.fStrBuffer.toString());
    }
    else
    {
      if ((this._indenting) && (!localElementState.preserveSpace)) {
        this._printer.breakLine();
      }
      this._printer.indent();
      printText(this.fStrBuffer.toString(), true, true);
      this._printer.unindent();
      if (this._indenting) {
        localElementState.afterElement = true;
      }
    }
    this.fStrBuffer.setLength(0);
    localElementState.afterComment = true;
    localElementState.afterElement = false;
  }
  
  public void startCDATA()
  {
    ElementState localElementState = getElementState();
    localElementState.doCData = true;
  }
  
  public void endCDATA()
  {
    ElementState localElementState = getElementState();
    localElementState.doCData = false;
  }
  
  public void startNonEscaping()
  {
    ElementState localElementState = getElementState();
    localElementState.unescaped = true;
  }
  
  public void endNonEscaping()
  {
    ElementState localElementState = getElementState();
    localElementState.unescaped = false;
  }
  
  public void startPreserving()
  {
    ElementState localElementState = getElementState();
    localElementState.preserveSpace = true;
  }
  
  public void endPreserving()
  {
    ElementState localElementState = getElementState();
    localElementState.preserveSpace = false;
  }
  
  public void endDocument()
    throws SAXException
  {
    try
    {
      serializePreRoot();
      this._printer.flush();
    }
    catch (IOException localIOException)
    {
      throw new SAXException(localIOException);
    }
  }
  
  public void startEntity(String paramString) {}
  
  public void endEntity(String paramString) {}
  
  public void setDocumentLocator(Locator paramLocator) {}
  
  public void skippedEntity(String paramString)
    throws SAXException
  {
    try
    {
      endCDATA();
      content();
      this._printer.printText('&');
      this._printer.printText(paramString);
      this._printer.printText(';');
    }
    catch (IOException localIOException)
    {
      throw new SAXException(localIOException);
    }
  }
  
  public void startPrefixMapping(String paramString1, String paramString2)
    throws SAXException
  {
    if (this._prefixes == null) {
      this._prefixes = new Hashtable();
    }
    this._prefixes.put(paramString2, paramString1 == null ? "" : paramString1);
  }
  
  public void endPrefixMapping(String paramString)
    throws SAXException
  {}
  
  public final void startDTD(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    try
    {
      this._printer.enterDTD();
      this._docTypePublicId = paramString2;
      this._docTypeSystemId = paramString3;
    }
    catch (IOException localIOException)
    {
      throw new SAXException(localIOException);
    }
  }
  
  public void endDTD() {}
  
  public void elementDecl(String paramString1, String paramString2)
    throws SAXException
  {
    try
    {
      this._printer.enterDTD();
      this._printer.printText("<!ELEMENT ");
      this._printer.printText(paramString1);
      this._printer.printText(' ');
      this._printer.printText(paramString2);
      this._printer.printText('>');
      if (this._indenting) {
        this._printer.breakLine();
      }
    }
    catch (IOException localIOException)
    {
      throw new SAXException(localIOException);
    }
  }
  
  public void attributeDecl(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
    throws SAXException
  {
    try
    {
      this._printer.enterDTD();
      this._printer.printText("<!ATTLIST ");
      this._printer.printText(paramString1);
      this._printer.printText(' ');
      this._printer.printText(paramString2);
      this._printer.printText(' ');
      this._printer.printText(paramString3);
      if (paramString4 != null)
      {
        this._printer.printText(' ');
        this._printer.printText(paramString4);
      }
      if (paramString5 != null)
      {
        this._printer.printText(" \"");
        printEscaped(paramString5);
        this._printer.printText('"');
      }
      this._printer.printText('>');
      if (this._indenting) {
        this._printer.breakLine();
      }
    }
    catch (IOException localIOException)
    {
      throw new SAXException(localIOException);
    }
  }
  
  public void internalEntityDecl(String paramString1, String paramString2)
    throws SAXException
  {
    try
    {
      this._printer.enterDTD();
      this._printer.printText("<!ENTITY ");
      this._printer.printText(paramString1);
      this._printer.printText(" \"");
      printEscaped(paramString2);
      this._printer.printText("\">");
      if (this._indenting) {
        this._printer.breakLine();
      }
    }
    catch (IOException localIOException)
    {
      throw new SAXException(localIOException);
    }
  }
  
  public void externalEntityDecl(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    try
    {
      this._printer.enterDTD();
      unparsedEntityDecl(paramString1, paramString2, paramString3, null);
    }
    catch (IOException localIOException)
    {
      throw new SAXException(localIOException);
    }
  }
  
  public void unparsedEntityDecl(String paramString1, String paramString2, String paramString3, String paramString4)
    throws SAXException
  {
    try
    {
      this._printer.enterDTD();
      if (paramString2 == null)
      {
        this._printer.printText("<!ENTITY ");
        this._printer.printText(paramString1);
        this._printer.printText(" SYSTEM ");
        printDoctypeURL(paramString3);
      }
      else
      {
        this._printer.printText("<!ENTITY ");
        this._printer.printText(paramString1);
        this._printer.printText(" PUBLIC ");
        printDoctypeURL(paramString2);
        this._printer.printText(' ');
        printDoctypeURL(paramString3);
      }
      if (paramString4 != null)
      {
        this._printer.printText(" NDATA ");
        this._printer.printText(paramString4);
      }
      this._printer.printText('>');
      if (this._indenting) {
        this._printer.breakLine();
      }
    }
    catch (IOException localIOException)
    {
      throw new SAXException(localIOException);
    }
  }
  
  public void notationDecl(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    try
    {
      this._printer.enterDTD();
      if (paramString2 != null)
      {
        this._printer.printText("<!NOTATION ");
        this._printer.printText(paramString1);
        this._printer.printText(" PUBLIC ");
        printDoctypeURL(paramString2);
        if (paramString3 != null)
        {
          this._printer.printText(' ');
          printDoctypeURL(paramString3);
        }
      }
      else
      {
        this._printer.printText("<!NOTATION ");
        this._printer.printText(paramString1);
        this._printer.printText(" SYSTEM ");
        printDoctypeURL(paramString3);
      }
      this._printer.printText('>');
      if (this._indenting) {
        this._printer.breakLine();
      }
    }
    catch (IOException localIOException)
    {
      throw new SAXException(localIOException);
    }
  }
  
  protected void serializeNode(Node paramNode)
    throws IOException
  {
    this.fCurrentNode = paramNode;
    Object localObject1;
    int j;
    int i;
    Object localObject3;
    Object localObject2;
    switch (paramNode.getNodeType())
    {
    case 3: 
      localObject1 = paramNode.getNodeValue();
      if (localObject1 != null)
      {
        if ((this.fDOMFilter != null) && ((this.fDOMFilter.getWhatToShow() & 0x4) != 0)) {
          j = this.fDOMFilter.acceptNode(paramNode);
        }
        switch (j)
        {
        case 2: 
        case 3: 
          break;
        default: 
          characters((String)localObject1);
          break;
          if ((!this._indenting) || (getElementState().preserveSpace) || (((String)localObject1).replace('\n', ' ').trim().length() != 0)) {
            characters((String)localObject1);
          }
          break;
        }
      }
      break;
    case 4: 
      localObject1 = paramNode.getNodeValue();
      if ((this.features & 0x8) != 0)
      {
        if (localObject1 != null)
        {
          if ((this.fDOMFilter != null) && ((this.fDOMFilter.getWhatToShow() & 0x8) != 0))
          {
            j = this.fDOMFilter.acceptNode(paramNode);
            switch (j)
            {
            case 2: 
            case 3: 
              return;
            }
          }
          startCDATA();
          characters((String)localObject1);
          endCDATA();
        }
      }
      else {
        characters((String)localObject1);
      }
      break;
    case 8: 
      if (!this._format.getOmitComments())
      {
        localObject1 = paramNode.getNodeValue();
        if (localObject1 != null)
        {
          if ((this.fDOMFilter != null) && ((this.fDOMFilter.getWhatToShow() & 0x80) != 0))
          {
            j = this.fDOMFilter.acceptNode(paramNode);
            switch (j)
            {
            case 2: 
            case 3: 
              return;
            }
          }
          comment((String)localObject1);
        }
      }
      break;
    case 5: 
      endCDATA();
      content();
      if (((this.features & 0x4) != 0) || (paramNode.getFirstChild() == null))
      {
        if ((this.fDOMFilter != null) && ((this.fDOMFilter.getWhatToShow() & 0x10) != 0))
        {
          j = this.fDOMFilter.acceptNode(paramNode);
          switch (j)
          {
          case 2: 
            return;
          case 3: 
            for (localObject1 = paramNode.getFirstChild(); localObject1 != null; localObject1 = ((Node)localObject1).getNextSibling()) {
              serializeNode((Node)localObject1);
            }
            return;
          }
        }
        checkUnboundNamespacePrefixedNode(paramNode);
        this._printer.printText("&");
        this._printer.printText(paramNode.getNodeName());
        this._printer.printText(";");
      }
      else
      {
        for (localObject1 = paramNode.getFirstChild(); localObject1 != null; localObject1 = ((Node)localObject1).getNextSibling()) {
          serializeNode((Node)localObject1);
        }
      }
      break;
    case 7: 
      if ((this.fDOMFilter != null) && ((this.fDOMFilter.getWhatToShow() & 0x40) != 0))
      {
        i = this.fDOMFilter.acceptNode(paramNode);
        switch (i)
        {
        case 2: 
        case 3: 
          return;
        }
      }
      processingInstructionIO(paramNode.getNodeName(), paramNode.getNodeValue());
      break;
    case 1: 
      if ((this.fDOMFilter != null) && ((this.fDOMFilter.getWhatToShow() & 0x1) != 0))
      {
        i = this.fDOMFilter.acceptNode(paramNode);
        switch (i)
        {
        case 2: 
          return;
        case 3: 
          for (localObject3 = paramNode.getFirstChild(); localObject3 != null; localObject3 = ((Node)localObject3).getNextSibling()) {
            serializeNode((Node)localObject3);
          }
          return;
        }
      }
      serializeElement((Element)paramNode);
      break;
    case 9: 
      localObject2 = ((Document)paramNode).getDoctype();
      if (localObject2 != null) {
        try
        {
          this._printer.enterDTD();
          this._docTypePublicId = ((DocumentType)localObject2).getPublicId();
          this._docTypeSystemId = ((DocumentType)localObject2).getSystemId();
          localObject3 = ((DocumentType)localObject2).getInternalSubset();
          if ((localObject3 != null) && (((String)localObject3).length() > 0)) {
            this._printer.printText((String)localObject3);
          }
          endDTD();
        }
        catch (NoSuchMethodError localNoSuchMethodError)
        {
          Class localClass = localObject2.getClass();
          String str1 = null;
          String str2 = null;
          try
          {
            Method localMethod1 = localClass.getMethod("getPublicId", (Class[])null);
            if (localMethod1.getReturnType().equals(String.class)) {
              str1 = (String)localMethod1.invoke(localObject2, (Object[])null);
            }
          }
          catch (Exception localException1) {}
          try
          {
            Method localMethod2 = localClass.getMethod("getSystemId", (Class[])null);
            if (localMethod2.getReturnType().equals(String.class)) {
              str2 = (String)localMethod2.invoke(localObject2, (Object[])null);
            }
          }
          catch (Exception localException2) {}
          this._printer.enterDTD();
          this._docTypePublicId = str1;
          this._docTypeSystemId = str2;
          endDTD();
        }
      }
    case 11: 
      localObject2 = paramNode.getFirstChild();
      for (;;)
      {
        serializeNode((Node)localObject2);
        localObject2 = ((Node)localObject2).getNextSibling();
        if (localObject2 == null) {
          break;
        }
      }
    }
  }
  
  protected ElementState content()
    throws IOException
  {
    ElementState localElementState = getElementState();
    if (!isDocumentState())
    {
      if ((localElementState.inCData) && (!localElementState.doCData))
      {
        this._printer.printText("]]>");
        localElementState.inCData = false;
      }
      if (localElementState.empty)
      {
        this._printer.printText('>');
        localElementState.empty = false;
      }
      localElementState.afterElement = false;
      localElementState.afterComment = false;
    }
    return localElementState;
  }
  
  protected void characters(String paramString)
    throws IOException
  {
    ElementState localElementState = content();
    int i;
    if ((localElementState.inCData) || (localElementState.doCData))
    {
      if (!localElementState.inCData)
      {
        this._printer.printText("<![CDATA[");
        localElementState.inCData = true;
      }
      i = this._printer.getNextIndent();
      this._printer.setNextIndent(0);
      printCDATAText(paramString);
      this._printer.setNextIndent(i);
    }
    else if (localElementState.preserveSpace)
    {
      i = this._printer.getNextIndent();
      this._printer.setNextIndent(0);
      printText(paramString, true, localElementState.unescaped);
      this._printer.setNextIndent(i);
    }
    else
    {
      printText(paramString, false, localElementState.unescaped);
    }
  }
  
  protected abstract String getEntityRef(int paramInt);
  
  protected abstract void serializeElement(Element paramElement)
    throws IOException;
  
  protected void serializePreRoot()
    throws IOException
  {
    if (this._preRoot != null)
    {
      for (int i = 0; i < this._preRoot.size(); i++)
      {
        printText((String)this._preRoot.elementAt(i), true, true);
        if (this._indenting) {
          this._printer.breakLine();
        }
      }
      this._preRoot.removeAllElements();
    }
  }
  
  protected void printCDATAText(String paramString)
    throws IOException
  {
    int i = paramString.length();
    for (int j = 0; j < i; j++)
    {
      char c = paramString.charAt(j);
      if ((c == ']') && (j + 2 < i) && (paramString.charAt(j + 1) == ']') && (paramString.charAt(j + 2) == '>'))
      {
        if (this.fDOMErrorHandler != null)
        {
          String str;
          if ((this.features & 0x10) == 0)
          {
            str = DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "EndingCDATA", null);
            if ((this.features & 0x2) != 0)
            {
              modifyDOMError(str, (short)3, "wf-invalid-character", this.fCurrentNode);
              this.fDOMErrorHandler.handleError(this.fDOMError);
              throw new LSException((short)82, str);
            }
            modifyDOMError(str, (short)2, "cdata-section-not-splitted", this.fCurrentNode);
            if (!this.fDOMErrorHandler.handleError(this.fDOMError)) {
              throw new LSException((short)82, str);
            }
          }
          else
          {
            str = DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "SplittingCDATA", null);
            modifyDOMError(str, (short)1, null, this.fCurrentNode);
            this.fDOMErrorHandler.handleError(this.fDOMError);
          }
        }
        this._printer.printText("]]]]><![CDATA[>");
        j += 2;
      }
      else if (!XMLChar.isValid(c))
      {
        j++;
        if (j < i) {
          surrogates(c, paramString.charAt(j), true);
        } else {
          fatalError("The character '" + c + "' is an invalid XML character");
        }
      }
      else if (((c >= ' ') && (this._encodingInfo.isPrintable(c)) && (c != '÷')) || (c == '\n') || (c == '\r') || (c == '\t'))
      {
        this._printer.printText(c);
      }
      else
      {
        this._printer.printText("]]>&#x");
        this._printer.printText(Integer.toHexString(c));
        this._printer.printText(";<![CDATA[");
      }
    }
  }
  
  protected void surrogates(int paramInt1, int paramInt2, boolean paramBoolean)
    throws IOException
  {
    if (XMLChar.isHighSurrogate(paramInt1))
    {
      if (!XMLChar.isLowSurrogate(paramInt2))
      {
        fatalError("The character '" + (char)paramInt2 + "' is an invalid XML character");
      }
      else
      {
        int i = XMLChar.supplemental((char)paramInt1, (char)paramInt2);
        if (!XMLChar.isValid(i))
        {
          fatalError("The character '" + (char)i + "' is an invalid XML character");
        }
        else if ((paramBoolean) && (content().inCData))
        {
          this._printer.printText("]]>&#x");
          this._printer.printText(Integer.toHexString(i));
          this._printer.printText(";<![CDATA[");
        }
        else
        {
          printHex(i);
        }
      }
    }
    else {
      fatalError("The character '" + (char)paramInt1 + "' is an invalid XML character");
    }
  }
  
  protected void printText(char[] paramArrayOfChar, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
    throws IOException
  {
    if (paramBoolean1) {
      while (paramInt2-- > 0)
      {
        c = paramArrayOfChar[paramInt1];
        paramInt1++;
        if ((c == '\n') || (c == '\r') || (paramBoolean2)) {
          this._printer.printText(c);
        } else {
          printEscaped(c);
        }
      }
    } else {
      while (paramInt2-- > 0)
      {
        char c = paramArrayOfChar[paramInt1];
        paramInt1++;
        if ((c == ' ') || (c == '\f') || (c == '\t') || (c == '\n') || (c == '\r')) {
          this._printer.printSpace();
        } else if (paramBoolean2) {
          this._printer.printText(c);
        } else {
          printEscaped(c);
        }
      }
    }
  }
  
  protected void printText(String paramString, boolean paramBoolean1, boolean paramBoolean2)
    throws IOException
  {
    int i;
    char c;
    if (paramBoolean1) {
      for (i = 0; i < paramString.length(); i++)
      {
        c = paramString.charAt(i);
        if ((c == '\n') || (c == '\r') || (paramBoolean2)) {
          this._printer.printText(c);
        } else {
          printEscaped(c);
        }
      }
    } else {
      for (i = 0; i < paramString.length(); i++)
      {
        c = paramString.charAt(i);
        if ((c == ' ') || (c == '\f') || (c == '\t') || (c == '\n') || (c == '\r')) {
          this._printer.printSpace();
        } else if (paramBoolean2) {
          this._printer.printText(c);
        } else {
          printEscaped(c);
        }
      }
    }
  }
  
  protected void printDoctypeURL(String paramString)
    throws IOException
  {
    this._printer.printText('"');
    for (int i = 0; i < paramString.length(); i++) {
      if ((paramString.charAt(i) == '"') || (paramString.charAt(i) < ' ') || (paramString.charAt(i) > ''))
      {
        this._printer.printText('%');
        this._printer.printText(Integer.toHexString(paramString.charAt(i)));
      }
      else
      {
        this._printer.printText(paramString.charAt(i));
      }
    }
    this._printer.printText('"');
  }
  
  protected void printEscaped(int paramInt)
    throws IOException
  {
    String str = getEntityRef(paramInt);
    if (str != null)
    {
      this._printer.printText('&');
      this._printer.printText(str);
      this._printer.printText(';');
    }
    else if (((paramInt >= 32) && (this._encodingInfo.isPrintable((char)paramInt)) && (paramInt != 247)) || (paramInt == 10) || (paramInt == 13) || (paramInt == 9))
    {
      if (paramInt < 65536)
      {
        this._printer.printText((char)paramInt);
      }
      else
      {
        this._printer.printText((char)((paramInt - 65536 >> 10) + 55296));
        this._printer.printText((char)((paramInt - 65536 & 0x3FF) + 56320));
      }
    }
    else
    {
      printHex(paramInt);
    }
  }
  
  final void printHex(int paramInt)
    throws IOException
  {
    this._printer.printText("&#x");
    this._printer.printText(Integer.toHexString(paramInt));
    this._printer.printText(';');
  }
  
  protected void printEscaped(String paramString)
    throws IOException
  {
    for (int i = 0; i < paramString.length(); i++)
    {
      int j = paramString.charAt(i);
      if (((j & 0xFC00) == 55296) && (i + 1 < paramString.length()))
      {
        int k = paramString.charAt(i + 1);
        if ((k & 0xFC00) == 56320)
        {
          j = 65536 + (j - 55296 << 10) + k - 56320;
          i++;
        }
      }
      printEscaped(j);
    }
  }
  
  protected ElementState getElementState()
  {
    return this._elementStates[this._elementStateCount];
  }
  
  protected ElementState enterElementState(String paramString1, String paramString2, String paramString3, boolean paramBoolean)
  {
    if (this._elementStateCount + 1 == this._elementStates.length)
    {
      ElementState[] arrayOfElementState = new ElementState[this._elementStates.length + 10];
      for (int i = 0; i < this._elementStates.length; i++) {
        arrayOfElementState[i] = this._elementStates[i];
      }
      for (int j = this._elementStates.length; j < arrayOfElementState.length; j++) {
        arrayOfElementState[j] = new ElementState();
      }
      this._elementStates = arrayOfElementState;
    }
    this._elementStateCount += 1;
    ElementState localElementState = this._elementStates[this._elementStateCount];
    localElementState.namespaceURI = paramString1;
    localElementState.localName = paramString2;
    localElementState.rawName = paramString3;
    localElementState.preserveSpace = paramBoolean;
    localElementState.empty = true;
    localElementState.afterElement = false;
    localElementState.afterComment = false;
    localElementState.doCData = (localElementState.inCData = 0);
    localElementState.unescaped = false;
    localElementState.prefixes = this._prefixes;
    this._prefixes = null;
    return localElementState;
  }
  
  protected ElementState leaveElementState()
  {
    if (this._elementStateCount > 0)
    {
      this._prefixes = null;
      this._elementStateCount -= 1;
      return this._elementStates[this._elementStateCount];
    }
    String str = DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "Internal", null);
    throw new IllegalStateException(str);
  }
  
  protected boolean isDocumentState()
  {
    return this._elementStateCount == 0;
  }
  
  final void clearDocumentState()
  {
    this._elementStateCount = 0;
  }
  
  protected String getPrefix(String paramString)
  {
    String str;
    if (this._prefixes != null)
    {
      str = (String)this._prefixes.get(paramString);
      if (str != null) {
        return str;
      }
    }
    if (this._elementStateCount == 0) {
      return null;
    }
    for (int i = this._elementStateCount; i > 0; i--) {
      if (this._elementStates[i].prefixes != null)
      {
        str = (String)this._elementStates[i].prefixes.get(paramString);
        if (str != null) {
          return str;
        }
      }
    }
    return null;
  }
  
  protected DOMError modifyDOMError(String paramString1, short paramShort, String paramString2, Node paramNode)
  {
    this.fDOMError.reset();
    this.fDOMError.fMessage = paramString1;
    this.fDOMError.fType = paramString2;
    this.fDOMError.fSeverity = paramShort;
    this.fDOMError.fLocator = new DOMLocatorImpl(-1, -1, -1, paramNode, null);
    return this.fDOMError;
  }
  
  protected void fatalError(String paramString)
    throws IOException
  {
    if (this.fDOMErrorHandler != null)
    {
      modifyDOMError(paramString, (short)3, null, this.fCurrentNode);
      this.fDOMErrorHandler.handleError(this.fDOMError);
    }
    else
    {
      throw new IOException(paramString);
    }
  }
  
  protected void checkUnboundNamespacePrefixedNode(Node paramNode)
    throws IOException
  {}
  
  public abstract void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException;
  
  public abstract void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException;
  
  public abstract void endElement(String paramString)
    throws SAXException;
  
  public abstract void startElement(String paramString, AttributeList paramAttributeList)
    throws SAXException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serialize.BaseMarkupSerializer
 * JD-Core Version:    0.7.0.1
 */