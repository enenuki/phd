package org.apache.xml.serialize;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Method;
import java.util.Vector;
import org.apache.xerces.dom.CoreDocumentImpl;
import org.apache.xerces.dom.DOMErrorImpl;
import org.apache.xerces.dom.DOMLocatorImpl;
import org.apache.xerces.dom.DOMMessageFormatter;
import org.apache.xerces.dom.DOMNormalizer;
import org.apache.xerces.dom.DOMStringListImpl;
import org.apache.xerces.impl.XMLEntityManager;
import org.apache.xerces.util.DOMUtil;
import org.apache.xerces.util.NamespaceSupport;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.util.XML11Char;
import org.apache.xerces.util.XMLChar;
import org.w3c.dom.Attr;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMError;
import org.w3c.dom.DOMErrorHandler;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMStringList;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.ls.LSException;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.w3c.dom.ls.LSSerializerFilter;

/**
 * @deprecated
 */
public class DOMSerializerImpl
  implements LSSerializer, DOMConfiguration
{
  private XMLSerializer serializer = new XMLSerializer();
  private XML11Serializer xml11Serializer;
  private DOMStringList fRecognizedParameters;
  protected short features = 0;
  protected static final short NAMESPACES = 1;
  protected static final short WELLFORMED = 2;
  protected static final short ENTITIES = 4;
  protected static final short CDATA = 8;
  protected static final short SPLITCDATA = 16;
  protected static final short COMMENTS = 32;
  protected static final short DISCARDDEFAULT = 64;
  protected static final short INFOSET = 128;
  protected static final short XMLDECL = 256;
  protected static final short NSDECL = 512;
  protected static final short DOM_ELEMENT_CONTENT_WHITESPACE = 1024;
  protected static final short PRETTY_PRINT = 2048;
  private DOMErrorHandler fErrorHandler = null;
  private final DOMErrorImpl fError = new DOMErrorImpl();
  private final DOMLocatorImpl fLocator = new DOMLocatorImpl();
  
  public DOMSerializerImpl()
  {
    initSerializer(this.serializer);
  }
  
  public DOMConfiguration getDomConfig()
  {
    return this;
  }
  
  public void setParameter(String paramString, Object paramObject)
    throws DOMException
  {
    if ((paramObject instanceof Boolean))
    {
      boolean bool = ((Boolean)paramObject).booleanValue();
      if (paramString.equalsIgnoreCase("infoset"))
      {
        if (bool)
        {
          this.features = ((short)(this.features & 0xFFFFFFFB));
          this.features = ((short)(this.features & 0xFFFFFFF7));
          this.features = ((short)(this.features | 0x1));
          this.features = ((short)(this.features | 0x200));
          this.features = ((short)(this.features | 0x2));
          this.features = ((short)(this.features | 0x20));
        }
      }
      else if (paramString.equalsIgnoreCase("xml-declaration"))
      {
        this.features = (bool ? (short)(this.features | 0x100) : (short)(this.features & 0xFFFFFEFF));
      }
      else if (paramString.equalsIgnoreCase("namespaces"))
      {
        this.features = (bool ? (short)(this.features | 0x1) : (short)(this.features & 0xFFFFFFFE));
        this.serializer.fNamespaces = bool;
      }
      else if (paramString.equalsIgnoreCase("split-cdata-sections"))
      {
        this.features = (bool ? (short)(this.features | 0x10) : (short)(this.features & 0xFFFFFFEF));
      }
      else if (paramString.equalsIgnoreCase("discard-default-content"))
      {
        this.features = (bool ? (short)(this.features | 0x40) : (short)(this.features & 0xFFFFFFBF));
      }
      else if (paramString.equalsIgnoreCase("well-formed"))
      {
        this.features = (bool ? (short)(this.features | 0x2) : (short)(this.features & 0xFFFFFFFD));
      }
      else if (paramString.equalsIgnoreCase("entities"))
      {
        this.features = (bool ? (short)(this.features | 0x4) : (short)(this.features & 0xFFFFFFFB));
      }
      else if (paramString.equalsIgnoreCase("cdata-sections"))
      {
        this.features = (bool ? (short)(this.features | 0x8) : (short)(this.features & 0xFFFFFFF7));
      }
      else if (paramString.equalsIgnoreCase("comments"))
      {
        this.features = (bool ? (short)(this.features | 0x20) : (short)(this.features & 0xFFFFFFDF));
      }
      else if (paramString.equalsIgnoreCase("format-pretty-print"))
      {
        this.features = (bool ? (short)(this.features | 0x800) : (short)(this.features & 0xFFFFF7FF));
      }
      else
      {
        String str2;
        if ((paramString.equalsIgnoreCase("canonical-form")) || (paramString.equalsIgnoreCase("validate-if-schema")) || (paramString.equalsIgnoreCase("validate")) || (paramString.equalsIgnoreCase("check-character-normalization")) || (paramString.equalsIgnoreCase("datatype-normalization")) || (paramString.equalsIgnoreCase("normalize-characters")))
        {
          if (bool)
          {
            str2 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "FEATURE_NOT_SUPPORTED", new Object[] { paramString });
            throw new DOMException((short)9, str2);
          }
        }
        else if (paramString.equalsIgnoreCase("namespace-declarations"))
        {
          this.features = (bool ? (short)(this.features | 0x200) : (short)(this.features & 0xFFFFFDFF));
          this.serializer.fNamespacePrefixes = bool;
        }
        else if ((paramString.equalsIgnoreCase("element-content-whitespace")) || (paramString.equalsIgnoreCase("ignore-unknown-character-denormalizations")))
        {
          if (!bool)
          {
            str2 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "FEATURE_NOT_SUPPORTED", new Object[] { paramString });
            throw new DOMException((short)9, str2);
          }
        }
        else
        {
          str2 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "FEATURE_NOT_FOUND", new Object[] { paramString });
          throw new DOMException((short)9, str2);
        }
      }
    }
    else
    {
      String str1;
      if (paramString.equalsIgnoreCase("error-handler"))
      {
        if ((paramObject == null) || ((paramObject instanceof DOMErrorHandler)))
        {
          this.fErrorHandler = ((DOMErrorHandler)paramObject);
        }
        else
        {
          str1 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "TYPE_MISMATCH_ERR", new Object[] { paramString });
          throw new DOMException((short)17, str1);
        }
      }
      else
      {
        if ((paramString.equalsIgnoreCase("resource-resolver")) || (paramString.equalsIgnoreCase("schema-location")) || ((paramString.equalsIgnoreCase("schema-type")) && (paramObject != null)))
        {
          str1 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "FEATURE_NOT_SUPPORTED", new Object[] { paramString });
          throw new DOMException((short)9, str1);
        }
        str1 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "FEATURE_NOT_FOUND", new Object[] { paramString });
        throw new DOMException((short)8, str1);
      }
    }
  }
  
  public boolean canSetParameter(String paramString, Object paramObject)
  {
    if (paramObject == null) {
      return true;
    }
    if ((paramObject instanceof Boolean))
    {
      boolean bool = ((Boolean)paramObject).booleanValue();
      if ((paramString.equalsIgnoreCase("namespaces")) || (paramString.equalsIgnoreCase("split-cdata-sections")) || (paramString.equalsIgnoreCase("discard-default-content")) || (paramString.equalsIgnoreCase("xml-declaration")) || (paramString.equalsIgnoreCase("well-formed")) || (paramString.equalsIgnoreCase("infoset")) || (paramString.equalsIgnoreCase("entities")) || (paramString.equalsIgnoreCase("cdata-sections")) || (paramString.equalsIgnoreCase("comments")) || (paramString.equalsIgnoreCase("format-pretty-print")) || (paramString.equalsIgnoreCase("namespace-declarations"))) {
        return true;
      }
      if ((paramString.equalsIgnoreCase("canonical-form")) || (paramString.equalsIgnoreCase("validate-if-schema")) || (paramString.equalsIgnoreCase("validate")) || (paramString.equalsIgnoreCase("check-character-normalization")) || (paramString.equalsIgnoreCase("datatype-normalization")) || (paramString.equalsIgnoreCase("normalize-characters"))) {
        return !bool;
      }
      if ((paramString.equalsIgnoreCase("element-content-whitespace")) || (paramString.equalsIgnoreCase("ignore-unknown-character-denormalizations"))) {
        return bool;
      }
    }
    else if (((paramString.equalsIgnoreCase("error-handler")) && (paramObject == null)) || ((paramObject instanceof DOMErrorHandler)))
    {
      return true;
    }
    return false;
  }
  
  public DOMStringList getParameterNames()
  {
    if (this.fRecognizedParameters == null)
    {
      Vector localVector = new Vector();
      localVector.add("namespaces");
      localVector.add("split-cdata-sections");
      localVector.add("discard-default-content");
      localVector.add("xml-declaration");
      localVector.add("canonical-form");
      localVector.add("validate-if-schema");
      localVector.add("validate");
      localVector.add("check-character-normalization");
      localVector.add("datatype-normalization");
      localVector.add("format-pretty-print");
      localVector.add("normalize-characters");
      localVector.add("well-formed");
      localVector.add("infoset");
      localVector.add("namespace-declarations");
      localVector.add("element-content-whitespace");
      localVector.add("entities");
      localVector.add("cdata-sections");
      localVector.add("comments");
      localVector.add("ignore-unknown-character-denormalizations");
      localVector.add("error-handler");
      this.fRecognizedParameters = new DOMStringListImpl(localVector);
    }
    return this.fRecognizedParameters;
  }
  
  public Object getParameter(String paramString)
    throws DOMException
  {
    if (paramString.equalsIgnoreCase("comments")) {
      return (this.features & 0x20) != 0 ? Boolean.TRUE : Boolean.FALSE;
    }
    if (paramString.equalsIgnoreCase("namespaces")) {
      return (this.features & 0x1) != 0 ? Boolean.TRUE : Boolean.FALSE;
    }
    if (paramString.equalsIgnoreCase("xml-declaration")) {
      return (this.features & 0x100) != 0 ? Boolean.TRUE : Boolean.FALSE;
    }
    if (paramString.equalsIgnoreCase("cdata-sections")) {
      return (this.features & 0x8) != 0 ? Boolean.TRUE : Boolean.FALSE;
    }
    if (paramString.equalsIgnoreCase("entities")) {
      return (this.features & 0x4) != 0 ? Boolean.TRUE : Boolean.FALSE;
    }
    if (paramString.equalsIgnoreCase("split-cdata-sections")) {
      return (this.features & 0x10) != 0 ? Boolean.TRUE : Boolean.FALSE;
    }
    if (paramString.equalsIgnoreCase("well-formed")) {
      return (this.features & 0x2) != 0 ? Boolean.TRUE : Boolean.FALSE;
    }
    if (paramString.equalsIgnoreCase("namespace-declarations")) {
      return (this.features & 0x200) != 0 ? Boolean.TRUE : Boolean.FALSE;
    }
    if ((paramString.equalsIgnoreCase("element-content-whitespace")) || (paramString.equalsIgnoreCase("ignore-unknown-character-denormalizations"))) {
      return Boolean.TRUE;
    }
    if (paramString.equalsIgnoreCase("discard-default-content")) {
      return (this.features & 0x40) != 0 ? Boolean.TRUE : Boolean.FALSE;
    }
    if (paramString.equalsIgnoreCase("format-pretty-print")) {
      return (this.features & 0x800) != 0 ? Boolean.TRUE : Boolean.FALSE;
    }
    if (paramString.equalsIgnoreCase("infoset"))
    {
      if (((this.features & 0x4) == 0) && ((this.features & 0x8) == 0) && ((this.features & 0x1) != 0) && ((this.features & 0x200) != 0) && ((this.features & 0x2) != 0) && ((this.features & 0x20) != 0)) {
        return Boolean.TRUE;
      }
      return Boolean.FALSE;
    }
    if ((paramString.equalsIgnoreCase("normalize-characters")) || (paramString.equalsIgnoreCase("canonical-form")) || (paramString.equalsIgnoreCase("validate-if-schema")) || (paramString.equalsIgnoreCase("check-character-normalization")) || (paramString.equalsIgnoreCase("validate")) || (paramString.equalsIgnoreCase("validate-if-schema")) || (paramString.equalsIgnoreCase("datatype-normalization"))) {
      return Boolean.FALSE;
    }
    if (paramString.equalsIgnoreCase("error-handler")) {
      return this.fErrorHandler;
    }
    if ((paramString.equalsIgnoreCase("resource-resolver")) || (paramString.equalsIgnoreCase("schema-location")) || (paramString.equalsIgnoreCase("schema-type")))
    {
      str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "FEATURE_NOT_SUPPORTED", new Object[] { paramString });
      throw new DOMException((short)9, str);
    }
    String str = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "FEATURE_NOT_FOUND", new Object[] { paramString });
    throw new DOMException((short)8, str);
  }
  
  public String writeToString(Node paramNode)
    throws DOMException, LSException
  {
    Object localObject1 = null;
    String str1 = _getXmlVersion(paramNode);
    if ((str1 != null) && (str1.equals("1.1")))
    {
      if (this.xml11Serializer == null)
      {
        this.xml11Serializer = new XML11Serializer();
        initSerializer(this.xml11Serializer);
      }
      copySettings(this.serializer, this.xml11Serializer);
      localObject1 = this.xml11Serializer;
    }
    else
    {
      localObject1 = this.serializer;
    }
    StringWriter localStringWriter = new StringWriter();
    try
    {
      prepareForSerialization((XMLSerializer)localObject1, paramNode);
      ((BaseMarkupSerializer)localObject1)._format.setEncoding("UTF-16");
      ((BaseMarkupSerializer)localObject1).setOutputCharStream(localStringWriter);
      if (paramNode.getNodeType() == 9)
      {
        ((BaseMarkupSerializer)localObject1).serialize((Document)paramNode);
      }
      else if (paramNode.getNodeType() == 11)
      {
        ((BaseMarkupSerializer)localObject1).serialize((DocumentFragment)paramNode);
      }
      else if (paramNode.getNodeType() == 1)
      {
        ((BaseMarkupSerializer)localObject1).serialize((Element)paramNode);
      }
      else
      {
        String str2 = DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "unable-to-serialize-node", null);
        if (((BaseMarkupSerializer)localObject1).fDOMErrorHandler != null)
        {
          DOMErrorImpl localDOMErrorImpl = new DOMErrorImpl();
          localDOMErrorImpl.fType = "unable-to-serialize-node";
          localDOMErrorImpl.fMessage = str2;
          localDOMErrorImpl.fSeverity = 3;
          ((BaseMarkupSerializer)localObject1).fDOMErrorHandler.handleError(localDOMErrorImpl);
        }
        throw new LSException((short)82, str2);
      }
    }
    catch (LSException localLSException)
    {
      throw localLSException;
    }
    catch (RuntimeException localRuntimeException)
    {
      if (localRuntimeException == DOMNormalizer.abort)
      {
        String str3 = null;
        return str3;
      }
      throw ((LSException)DOMUtil.createLSException((short)82, localRuntimeException).fillInStackTrace());
    }
    catch (IOException localIOException)
    {
      String str4 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "STRING_TOO_LONG", new Object[] { localIOException.getMessage() });
      throw new DOMException((short)2, str4);
    }
    finally
    {
      ((BaseMarkupSerializer)localObject1).clearDocumentState();
    }
    return localStringWriter.toString();
  }
  
  public void setNewLine(String paramString)
  {
    this.serializer._format.setLineSeparator(paramString);
  }
  
  public String getNewLine()
  {
    return this.serializer._format.getLineSeparator();
  }
  
  public LSSerializerFilter getFilter()
  {
    return this.serializer.fDOMFilter;
  }
  
  public void setFilter(LSSerializerFilter paramLSSerializerFilter)
  {
    this.serializer.fDOMFilter = paramLSSerializerFilter;
  }
  
  private void initSerializer(XMLSerializer paramXMLSerializer)
  {
    paramXMLSerializer.fNSBinder = new NamespaceSupport();
    paramXMLSerializer.fLocalNSBinder = new NamespaceSupport();
    paramXMLSerializer.fSymbolTable = new SymbolTable();
  }
  
  private void copySettings(XMLSerializer paramXMLSerializer1, XMLSerializer paramXMLSerializer2)
  {
    paramXMLSerializer2.fDOMErrorHandler = this.fErrorHandler;
    paramXMLSerializer2._format.setEncoding(paramXMLSerializer1._format.getEncoding());
    paramXMLSerializer2._format.setLineSeparator(paramXMLSerializer1._format.getLineSeparator());
    paramXMLSerializer2.fDOMFilter = paramXMLSerializer1.fDOMFilter;
  }
  
  public boolean write(Node paramNode, LSOutput paramLSOutput)
    throws LSException
  {
    if (paramNode == null) {
      return false;
    }
    Object localObject1 = null;
    String str1 = _getXmlVersion(paramNode);
    if ((str1 != null) && (str1.equals("1.1")))
    {
      if (this.xml11Serializer == null)
      {
        this.xml11Serializer = new XML11Serializer();
        initSerializer(this.xml11Serializer);
      }
      copySettings(this.serializer, this.xml11Serializer);
      localObject1 = this.xml11Serializer;
    }
    else
    {
      localObject1 = this.serializer;
    }
    String str2 = null;
    if ((str2 = paramLSOutput.getEncoding()) == null)
    {
      str2 = _getInputEncoding(paramNode);
      if (str2 == null)
      {
        str2 = _getXmlEncoding(paramNode);
        if (str2 == null) {
          str2 = "UTF-8";
        }
      }
    }
    try
    {
      prepareForSerialization((XMLSerializer)localObject1, paramNode);
      ((BaseMarkupSerializer)localObject1)._format.setEncoding(str2);
      OutputStream localOutputStream = paramLSOutput.getByteStream();
      localObject2 = paramLSOutput.getCharacterStream();
      String str3 = paramLSOutput.getSystemId();
      if (localObject2 == null)
      {
        if (localOutputStream == null)
        {
          if (str3 == null)
          {
            String str4 = DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "no-output-specified", null);
            if (((BaseMarkupSerializer)localObject1).fDOMErrorHandler != null)
            {
              localDOMErrorImpl = new DOMErrorImpl();
              localDOMErrorImpl.fType = "no-output-specified";
              localDOMErrorImpl.fMessage = str4;
              localDOMErrorImpl.fSeverity = 3;
              ((BaseMarkupSerializer)localObject1).fDOMErrorHandler.handleError(localDOMErrorImpl);
            }
            throw new LSException((short)82, str4);
          }
          ((BaseMarkupSerializer)localObject1).setOutputByteStream(XMLEntityManager.createOutputStream(str3));
        }
        else
        {
          ((BaseMarkupSerializer)localObject1).setOutputByteStream(localOutputStream);
        }
      }
      else {
        ((BaseMarkupSerializer)localObject1).setOutputCharStream((Writer)localObject2);
      }
      if (paramNode.getNodeType() == 9)
      {
        ((BaseMarkupSerializer)localObject1).serialize((Document)paramNode);
      }
      else if (paramNode.getNodeType() == 11)
      {
        ((BaseMarkupSerializer)localObject1).serialize((DocumentFragment)paramNode);
      }
      else if (paramNode.getNodeType() == 1)
      {
        ((BaseMarkupSerializer)localObject1).serialize((Element)paramNode);
      }
      else
      {
        bool = false;
        return bool;
      }
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      Object localObject2;
      if (((BaseMarkupSerializer)localObject1).fDOMErrorHandler != null)
      {
        localObject2 = new DOMErrorImpl();
        ((DOMErrorImpl)localObject2).fException = localUnsupportedEncodingException;
        ((DOMErrorImpl)localObject2).fType = "unsupported-encoding";
        ((DOMErrorImpl)localObject2).fMessage = localUnsupportedEncodingException.getMessage();
        ((DOMErrorImpl)localObject2).fSeverity = 3;
        ((BaseMarkupSerializer)localObject1).fDOMErrorHandler.handleError((DOMError)localObject2);
      }
      throw new LSException((short)82, DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "unsupported-encoding", null));
    }
    catch (LSException localLSException)
    {
      throw localLSException;
    }
    catch (RuntimeException localRuntimeException)
    {
      boolean bool;
      if (localRuntimeException == DOMNormalizer.abort)
      {
        bool = false;
        return bool;
      }
      throw ((LSException)DOMUtil.createLSException((short)82, localRuntimeException).fillInStackTrace());
    }
    catch (Exception localException)
    {
      DOMErrorImpl localDOMErrorImpl;
      if (((BaseMarkupSerializer)localObject1).fDOMErrorHandler != null)
      {
        localDOMErrorImpl = new DOMErrorImpl();
        localDOMErrorImpl.fException = localException;
        localDOMErrorImpl.fMessage = localException.getMessage();
        localDOMErrorImpl.fSeverity = 2;
        ((BaseMarkupSerializer)localObject1).fDOMErrorHandler.handleError(localDOMErrorImpl);
      }
      throw ((LSException)DOMUtil.createLSException((short)82, localException).fillInStackTrace());
    }
    finally
    {
      ((BaseMarkupSerializer)localObject1).clearDocumentState();
    }
    return true;
  }
  
  public boolean writeToURI(Node paramNode, String paramString)
    throws LSException
  {
    if (paramNode == null) {
      return false;
    }
    Object localObject1 = null;
    String str1 = _getXmlVersion(paramNode);
    if ((str1 != null) && (str1.equals("1.1")))
    {
      if (this.xml11Serializer == null)
      {
        this.xml11Serializer = new XML11Serializer();
        initSerializer(this.xml11Serializer);
      }
      copySettings(this.serializer, this.xml11Serializer);
      localObject1 = this.xml11Serializer;
    }
    else
    {
      localObject1 = this.serializer;
    }
    String str2 = _getInputEncoding(paramNode);
    if (str2 == null)
    {
      str2 = _getXmlEncoding(paramNode);
      if (str2 == null) {
        str2 = "UTF-8";
      }
    }
    try
    {
      prepareForSerialization((XMLSerializer)localObject1, paramNode);
      ((BaseMarkupSerializer)localObject1)._format.setEncoding(str2);
      ((BaseMarkupSerializer)localObject1).setOutputByteStream(XMLEntityManager.createOutputStream(paramString));
      if (paramNode.getNodeType() == 9)
      {
        ((BaseMarkupSerializer)localObject1).serialize((Document)paramNode);
      }
      else if (paramNode.getNodeType() == 11)
      {
        ((BaseMarkupSerializer)localObject1).serialize((DocumentFragment)paramNode);
      }
      else if (paramNode.getNodeType() == 1)
      {
        ((BaseMarkupSerializer)localObject1).serialize((Element)paramNode);
      }
      else
      {
        boolean bool1 = false;
        return bool1;
      }
    }
    catch (LSException localLSException)
    {
      throw localLSException;
    }
    catch (RuntimeException localRuntimeException)
    {
      if (localRuntimeException == DOMNormalizer.abort)
      {
        boolean bool2 = false;
        return bool2;
      }
      throw ((LSException)DOMUtil.createLSException((short)82, localRuntimeException).fillInStackTrace());
    }
    catch (Exception localException)
    {
      if (((BaseMarkupSerializer)localObject1).fDOMErrorHandler != null)
      {
        DOMErrorImpl localDOMErrorImpl = new DOMErrorImpl();
        localDOMErrorImpl.fException = localException;
        localDOMErrorImpl.fMessage = localException.getMessage();
        localDOMErrorImpl.fSeverity = 2;
        ((BaseMarkupSerializer)localObject1).fDOMErrorHandler.handleError(localDOMErrorImpl);
      }
      throw ((LSException)DOMUtil.createLSException((short)82, localException).fillInStackTrace());
    }
    finally
    {
      ((BaseMarkupSerializer)localObject1).clearDocumentState();
    }
    return true;
  }
  
  private void prepareForSerialization(XMLSerializer paramXMLSerializer, Node paramNode)
  {
    paramXMLSerializer.reset();
    paramXMLSerializer.features = this.features;
    paramXMLSerializer.fDOMErrorHandler = this.fErrorHandler;
    paramXMLSerializer.fNamespaces = ((this.features & 0x1) != 0);
    paramXMLSerializer.fNamespacePrefixes = ((this.features & 0x200) != 0);
    paramXMLSerializer._format.setIndenting((this.features & 0x800) != 0);
    paramXMLSerializer._format.setOmitComments((this.features & 0x20) == 0);
    paramXMLSerializer._format.setOmitXMLDeclaration((this.features & 0x100) == 0);
    if ((this.features & 0x2) != 0)
    {
      Node localNode2 = paramNode;
      boolean bool = true;
      Document localDocument = paramNode.getNodeType() == 9 ? (Document)paramNode : paramNode.getOwnerDocument();
      try
      {
        Method localMethod = localDocument.getClass().getMethod("isXMLVersionChanged()", new Class[0]);
        if (localMethod != null) {
          bool = ((Boolean)localMethod.invoke(localDocument, (Object[])null)).booleanValue();
        }
      }
      catch (Exception localException) {}
      if (paramNode.getFirstChild() != null) {
        while (paramNode != null)
        {
          verify(paramNode, bool, false);
          Node localNode1 = paramNode.getFirstChild();
          while (localNode1 == null)
          {
            localNode1 = paramNode.getNextSibling();
            if (localNode1 == null)
            {
              paramNode = paramNode.getParentNode();
              if (localNode2 == paramNode)
              {
                localNode1 = null;
                break;
              }
              localNode1 = paramNode.getNextSibling();
            }
          }
          paramNode = localNode1;
        }
      } else {
        verify(paramNode, bool, false);
      }
    }
  }
  
  private void verify(Node paramNode, boolean paramBoolean1, boolean paramBoolean2)
  {
    int i = paramNode.getNodeType();
    this.fLocator.fRelatedNode = paramNode;
    boolean bool;
    Object localObject1;
    Object localObject2;
    switch (i)
    {
    case 9: 
      break;
    case 10: 
      break;
    case 1: 
      if (paramBoolean1)
      {
        if ((this.features & 0x1) != 0) {
          bool = CoreDocumentImpl.isValidQName(paramNode.getPrefix(), paramNode.getLocalName(), paramBoolean2);
        } else {
          bool = CoreDocumentImpl.isXMLName(paramNode.getNodeName(), paramBoolean2);
        }
        if ((!bool) && (!bool) && (this.fErrorHandler != null))
        {
          localObject1 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "wf-invalid-character-in-node-name", new Object[] { "Element", paramNode.getNodeName() });
          DOMNormalizer.reportDOMError(this.fErrorHandler, this.fError, this.fLocator, (String)localObject1, (short)3, "wf-invalid-character-in-node-name");
        }
      }
      localObject1 = paramNode.hasAttributes() ? paramNode.getAttributes() : null;
      if (localObject1 != null) {
        for (int j = 0; j < ((NamedNodeMap)localObject1).getLength(); j++)
        {
          localObject2 = (Attr)((NamedNodeMap)localObject1).item(j);
          this.fLocator.fRelatedNode = ((Node)localObject2);
          DOMNormalizer.isAttrValueWF(this.fErrorHandler, this.fError, this.fLocator, (NamedNodeMap)localObject1, (Attr)localObject2, ((Attr)localObject2).getValue(), paramBoolean2);
          if (paramBoolean1)
          {
            bool = CoreDocumentImpl.isXMLName(((Node)localObject2).getNodeName(), paramBoolean2);
            if (!bool)
            {
              String str2 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "wf-invalid-character-in-node-name", new Object[] { "Attr", paramNode.getNodeName() });
              DOMNormalizer.reportDOMError(this.fErrorHandler, this.fError, this.fLocator, str2, (short)3, "wf-invalid-character-in-node-name");
            }
          }
        }
      }
      break;
    case 8: 
      if ((goto 587) && ((this.features & 0x20) != 0)) {
        DOMNormalizer.isCommentWF(this.fErrorHandler, this.fError, this.fLocator, ((Comment)paramNode).getData(), paramBoolean2);
      }
      break;
    case 5: 
      if ((paramBoolean1) && ((this.features & 0x4) != 0)) {
        CoreDocumentImpl.isXMLName(paramNode.getNodeName(), paramBoolean2);
      }
      break;
    case 4: 
      DOMNormalizer.isXMLCharWF(this.fErrorHandler, this.fError, this.fLocator, paramNode.getNodeValue(), paramBoolean2);
      break;
    case 3: 
      DOMNormalizer.isXMLCharWF(this.fErrorHandler, this.fError, this.fLocator, paramNode.getNodeValue(), paramBoolean2);
      break;
    case 7: 
      localObject1 = (ProcessingInstruction)paramNode;
      String str1 = ((ProcessingInstruction)localObject1).getTarget();
      if (paramBoolean1)
      {
        if (paramBoolean2) {
          bool = XML11Char.isXML11ValidName(str1);
        } else {
          bool = XMLChar.isValidName(str1);
        }
        if (!bool)
        {
          localObject2 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "wf-invalid-character-in-node-name", new Object[] { "Element", paramNode.getNodeName() });
          DOMNormalizer.reportDOMError(this.fErrorHandler, this.fError, this.fLocator, (String)localObject2, (short)3, "wf-invalid-character-in-node-name");
        }
      }
      DOMNormalizer.isXMLCharWF(this.fErrorHandler, this.fError, this.fLocator, ((ProcessingInstruction)localObject1).getData(), paramBoolean2);
    }
  }
  
  private String _getXmlVersion(Node paramNode)
  {
    Document localDocument = paramNode.getNodeType() == 9 ? (Document)paramNode : paramNode.getOwnerDocument();
    if ((localDocument != null) && (DocumentMethods.fgDocumentMethodsAvailable)) {
      try
      {
        return (String)DocumentMethods.fgDocumentGetXmlVersionMethod.invoke(localDocument, (Object[])null);
      }
      catch (VirtualMachineError localVirtualMachineError)
      {
        throw localVirtualMachineError;
      }
      catch (ThreadDeath localThreadDeath)
      {
        throw localThreadDeath;
      }
      catch (Throwable localThrowable) {}
    }
    return null;
  }
  
  private String _getInputEncoding(Node paramNode)
  {
    Document localDocument = paramNode.getNodeType() == 9 ? (Document)paramNode : paramNode.getOwnerDocument();
    if ((localDocument != null) && (DocumentMethods.fgDocumentMethodsAvailable)) {
      try
      {
        return (String)DocumentMethods.fgDocumentGetInputEncodingMethod.invoke(localDocument, (Object[])null);
      }
      catch (VirtualMachineError localVirtualMachineError)
      {
        throw localVirtualMachineError;
      }
      catch (ThreadDeath localThreadDeath)
      {
        throw localThreadDeath;
      }
      catch (Throwable localThrowable) {}
    }
    return null;
  }
  
  private String _getXmlEncoding(Node paramNode)
  {
    Document localDocument = paramNode.getNodeType() == 9 ? (Document)paramNode : paramNode.getOwnerDocument();
    if ((localDocument != null) && (DocumentMethods.fgDocumentMethodsAvailable)) {
      try
      {
        return (String)DocumentMethods.fgDocumentGetXmlEncodingMethod.invoke(localDocument, (Object[])null);
      }
      catch (VirtualMachineError localVirtualMachineError)
      {
        throw localVirtualMachineError;
      }
      catch (ThreadDeath localThreadDeath)
      {
        throw localThreadDeath;
      }
      catch (Throwable localThrowable) {}
    }
    return null;
  }
  
  static class DocumentMethods
  {
    private static Method fgDocumentGetXmlVersionMethod = null;
    private static Method fgDocumentGetInputEncodingMethod = null;
    private static Method fgDocumentGetXmlEncodingMethod = null;
    private static boolean fgDocumentMethodsAvailable = false;
    
    static
    {
      try
      {
        fgDocumentGetXmlVersionMethod = Document.class.getMethod("getXmlVersion", new Class[0]);
        fgDocumentGetInputEncodingMethod = Document.class.getMethod("getInputEncoding", new Class[0]);
        fgDocumentGetXmlEncodingMethod = Document.class.getMethod("getXmlEncoding", new Class[0]);
        fgDocumentMethodsAvailable = true;
      }
      catch (Exception localException)
      {
        fgDocumentGetXmlVersionMethod = null;
        fgDocumentGetInputEncodingMethod = null;
        fgDocumentGetXmlEncodingMethod = null;
        fgDocumentMethodsAvailable = false;
      }
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serialize.DOMSerializerImpl
 * JD-Core Version:    0.7.0.1
 */