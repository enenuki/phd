package org.apache.xerces.jaxp.validation;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Locale;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import org.apache.xerces.dom.NodeImpl;
import org.apache.xerces.impl.XMLErrorReporter;
import org.apache.xerces.impl.validation.EntityState;
import org.apache.xerces.impl.validation.ValidationManager;
import org.apache.xerces.impl.xs.XMLSchemaValidator;
import org.apache.xerces.impl.xs.util.SimpleLocator;
import org.apache.xerces.util.NamespaceSupport;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.util.XMLAttributesImpl;
import org.apache.xerces.util.XMLSymbols;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.parser.XMLParseException;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Entity;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

final class DOMValidatorHelper
  implements ValidatorHelper, EntityState
{
  private static final int CHUNK_SIZE = 1024;
  private static final int CHUNK_MASK = 1023;
  private static final String ERROR_REPORTER = "http://apache.org/xml/properties/internal/error-reporter";
  private static final String NAMESPACE_CONTEXT = "http://apache.org/xml/properties/internal/namespace-context";
  private static final String SCHEMA_VALIDATOR = "http://apache.org/xml/properties/internal/validator/schema";
  private static final String SYMBOL_TABLE = "http://apache.org/xml/properties/internal/symbol-table";
  private static final String VALIDATION_MANAGER = "http://apache.org/xml/properties/internal/validation-manager";
  private final XMLErrorReporter fErrorReporter;
  private final NamespaceSupport fNamespaceContext;
  private final DOMNamespaceContext fDOMNamespaceContext = new DOMNamespaceContext();
  private final XMLSchemaValidator fSchemaValidator;
  private final SymbolTable fSymbolTable;
  private final ValidationManager fValidationManager;
  private final XMLSchemaValidatorComponentManager fComponentManager;
  private final SimpleLocator fXMLLocator = new SimpleLocator(null, null, -1, -1, -1);
  private DOMDocumentHandler fDOMValidatorHandler;
  private final DOMResultAugmentor fDOMResultAugmentor = new DOMResultAugmentor(this);
  private final DOMResultBuilder fDOMResultBuilder = new DOMResultBuilder();
  private NamedNodeMap fEntities = null;
  private final char[] fCharBuffer = new char[1024];
  private Node fRoot;
  private Node fCurrentElement;
  final QName fElementQName = new QName();
  final QName fAttributeQName = new QName();
  final XMLAttributesImpl fAttributes = new XMLAttributesImpl();
  final XMLString fTempString = new XMLString();
  
  public DOMValidatorHelper(XMLSchemaValidatorComponentManager paramXMLSchemaValidatorComponentManager)
  {
    this.fComponentManager = paramXMLSchemaValidatorComponentManager;
    this.fErrorReporter = ((XMLErrorReporter)this.fComponentManager.getProperty("http://apache.org/xml/properties/internal/error-reporter"));
    this.fNamespaceContext = ((NamespaceSupport)this.fComponentManager.getProperty("http://apache.org/xml/properties/internal/namespace-context"));
    this.fSchemaValidator = ((XMLSchemaValidator)this.fComponentManager.getProperty("http://apache.org/xml/properties/internal/validator/schema"));
    this.fSymbolTable = ((SymbolTable)this.fComponentManager.getProperty("http://apache.org/xml/properties/internal/symbol-table"));
    this.fValidationManager = ((ValidationManager)this.fComponentManager.getProperty("http://apache.org/xml/properties/internal/validation-manager"));
  }
  
  public void validate(Source paramSource, Result paramResult)
    throws SAXException, IOException
  {
    if (((paramResult instanceof DOMResult)) || (paramResult == null))
    {
      DOMSource localDOMSource = (DOMSource)paramSource;
      DOMResult localDOMResult = (DOMResult)paramResult;
      Node localNode = localDOMSource.getNode();
      this.fRoot = localNode;
      if (localNode != null)
      {
        this.fComponentManager.reset();
        this.fValidationManager.setEntityState(this);
        this.fDOMNamespaceContext.reset();
        String str = localDOMSource.getSystemId();
        this.fXMLLocator.setLiteralSystemId(str);
        this.fXMLLocator.setExpandedSystemId(str);
        this.fErrorReporter.setDocumentLocator(this.fXMLLocator);
        try
        {
          setupEntityMap(localNode.getNodeType() == 9 ? (Document)localNode : localNode.getOwnerDocument());
          setupDOMResultHandler(localDOMSource, localDOMResult);
          this.fSchemaValidator.startDocument(this.fXMLLocator, null, this.fDOMNamespaceContext, null);
          validate(localNode);
          this.fSchemaValidator.endDocument(null);
        }
        catch (XMLParseException localXMLParseException)
        {
          throw Util.toSAXParseException(localXMLParseException);
        }
        catch (XNIException localXNIException)
        {
          throw Util.toSAXException(localXNIException);
        }
        finally
        {
          this.fRoot = null;
          this.fCurrentElement = null;
          this.fEntities = null;
          if (this.fDOMValidatorHandler != null) {
            this.fDOMValidatorHandler.setDOMResult(null);
          }
        }
      }
      return;
    }
    throw new IllegalArgumentException(JAXPValidationMessageFormatter.formatMessage(Locale.getDefault(), "SourceResultMismatch", new Object[] { paramSource.getClass().getName(), paramResult.getClass().getName() }));
  }
  
  public boolean isEntityDeclared(String paramString)
  {
    return false;
  }
  
  public boolean isEntityUnparsed(String paramString)
  {
    if (this.fEntities != null)
    {
      Entity localEntity = (Entity)this.fEntities.getNamedItem(paramString);
      if (localEntity != null) {
        return localEntity.getNotationName() != null;
      }
    }
    return false;
  }
  
  private void validate(Node paramNode)
  {
    Node localNode1 = paramNode;
    boolean bool = useIsSameNode(localNode1);
    while (paramNode != null)
    {
      beginNode(paramNode);
      Node localNode2 = paramNode.getFirstChild();
      while (localNode2 == null)
      {
        finishNode(paramNode);
        if (localNode1 == paramNode) {
          break;
        }
        localNode2 = paramNode.getNextSibling();
        if (localNode2 == null)
        {
          paramNode = paramNode.getParentNode();
          if (paramNode != null)
          {
            if ((localNode1 == paramNode ? 1 : bool ? localNode1.isSameNode(paramNode) : 0) == 0) {}
          }
          else
          {
            if (paramNode != null) {
              finishNode(paramNode);
            }
            localNode2 = null;
            break;
          }
        }
      }
      paramNode = localNode2;
    }
  }
  
  private void beginNode(Node paramNode)
  {
    switch (paramNode.getNodeType())
    {
    case 1: 
      this.fCurrentElement = paramNode;
      this.fNamespaceContext.pushContext();
      fillQName(this.fElementQName, paramNode);
      processAttributes(paramNode.getAttributes());
      this.fSchemaValidator.startElement(this.fElementQName, this.fAttributes, null);
      break;
    case 3: 
      if (this.fDOMValidatorHandler != null)
      {
        this.fDOMValidatorHandler.setIgnoringCharacters(true);
        sendCharactersToValidator(paramNode.getNodeValue());
        this.fDOMValidatorHandler.setIgnoringCharacters(false);
        this.fDOMValidatorHandler.characters((Text)paramNode);
      }
      else
      {
        sendCharactersToValidator(paramNode.getNodeValue());
      }
      break;
    case 4: 
      if (this.fDOMValidatorHandler != null)
      {
        this.fDOMValidatorHandler.setIgnoringCharacters(true);
        this.fSchemaValidator.startCDATA(null);
        sendCharactersToValidator(paramNode.getNodeValue());
        this.fSchemaValidator.endCDATA(null);
        this.fDOMValidatorHandler.setIgnoringCharacters(false);
        this.fDOMValidatorHandler.cdata((CDATASection)paramNode);
      }
      else
      {
        this.fSchemaValidator.startCDATA(null);
        sendCharactersToValidator(paramNode.getNodeValue());
        this.fSchemaValidator.endCDATA(null);
      }
      break;
    case 7: 
      if (this.fDOMValidatorHandler != null) {
        this.fDOMValidatorHandler.processingInstruction((ProcessingInstruction)paramNode);
      }
      break;
    case 8: 
      if (this.fDOMValidatorHandler != null) {
        this.fDOMValidatorHandler.comment((Comment)paramNode);
      }
      break;
    case 10: 
      if (this.fDOMValidatorHandler != null) {
        this.fDOMValidatorHandler.doctypeDecl((DocumentType)paramNode);
      }
      break;
    }
  }
  
  private void finishNode(Node paramNode)
  {
    if (paramNode.getNodeType() == 1)
    {
      this.fCurrentElement = paramNode;
      fillQName(this.fElementQName, paramNode);
      this.fSchemaValidator.endElement(this.fElementQName, null);
      this.fNamespaceContext.popContext();
    }
  }
  
  private void setupEntityMap(Document paramDocument)
  {
    if (paramDocument != null)
    {
      DocumentType localDocumentType = paramDocument.getDoctype();
      if (localDocumentType != null)
      {
        this.fEntities = localDocumentType.getEntities();
        return;
      }
    }
    this.fEntities = null;
  }
  
  private void setupDOMResultHandler(DOMSource paramDOMSource, DOMResult paramDOMResult)
    throws SAXException
  {
    if (paramDOMResult == null)
    {
      this.fDOMValidatorHandler = null;
      this.fSchemaValidator.setDocumentHandler(null);
      return;
    }
    Node localNode = paramDOMResult.getNode();
    if (paramDOMSource.getNode() == localNode)
    {
      this.fDOMValidatorHandler = this.fDOMResultAugmentor;
      this.fDOMResultAugmentor.setDOMResult(paramDOMResult);
      this.fSchemaValidator.setDocumentHandler(this.fDOMResultAugmentor);
      return;
    }
    if (paramDOMResult.getNode() == null) {
      try
      {
        DocumentBuilderFactory localDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
        localDocumentBuilderFactory.setNamespaceAware(true);
        DocumentBuilder localDocumentBuilder = localDocumentBuilderFactory.newDocumentBuilder();
        paramDOMResult.setNode(localDocumentBuilder.newDocument());
      }
      catch (ParserConfigurationException localParserConfigurationException)
      {
        throw new SAXException(localParserConfigurationException);
      }
    }
    this.fDOMValidatorHandler = this.fDOMResultBuilder;
    this.fDOMResultBuilder.setDOMResult(paramDOMResult);
    this.fSchemaValidator.setDocumentHandler(this.fDOMResultBuilder);
  }
  
  private void fillQName(QName paramQName, Node paramNode)
  {
    String str1 = paramNode.getPrefix();
    String str2 = paramNode.getLocalName();
    String str3 = paramNode.getNodeName();
    String str4 = paramNode.getNamespaceURI();
    paramQName.prefix = (str1 != null ? this.fSymbolTable.addSymbol(str1) : XMLSymbols.EMPTY_STRING);
    paramQName.localpart = (str2 != null ? this.fSymbolTable.addSymbol(str2) : XMLSymbols.EMPTY_STRING);
    paramQName.rawname = (str3 != null ? this.fSymbolTable.addSymbol(str3) : XMLSymbols.EMPTY_STRING);
    paramQName.uri = ((str4 != null) && (str4.length() > 0) ? this.fSymbolTable.addSymbol(str4) : null);
  }
  
  private void processAttributes(NamedNodeMap paramNamedNodeMap)
  {
    int i = paramNamedNodeMap.getLength();
    this.fAttributes.removeAllAttributes();
    for (int j = 0; j < i; j++)
    {
      Attr localAttr = (Attr)paramNamedNodeMap.item(j);
      String str = localAttr.getValue();
      if (str == null) {
        str = XMLSymbols.EMPTY_STRING;
      }
      fillQName(this.fAttributeQName, localAttr);
      this.fAttributes.addAttributeNS(this.fAttributeQName, XMLSymbols.fCDATASymbol, str);
      this.fAttributes.setSpecified(j, localAttr.getSpecified());
      if (this.fAttributeQName.uri == NamespaceContext.XMLNS_URI) {
        if (this.fAttributeQName.prefix == XMLSymbols.PREFIX_XMLNS) {
          this.fNamespaceContext.declarePrefix(this.fAttributeQName.localpart, str.length() != 0 ? this.fSymbolTable.addSymbol(str) : null);
        } else {
          this.fNamespaceContext.declarePrefix(XMLSymbols.EMPTY_STRING, str.length() != 0 ? this.fSymbolTable.addSymbol(str) : null);
        }
      }
    }
  }
  
  private void sendCharactersToValidator(String paramString)
  {
    if (paramString != null)
    {
      int i = paramString.length();
      int j = i & 0x3FF;
      if (j > 0)
      {
        paramString.getChars(0, j, this.fCharBuffer, 0);
        this.fTempString.setValues(this.fCharBuffer, 0, j);
        this.fSchemaValidator.characters(this.fTempString, null);
      }
      int k = j;
      while (k < i)
      {
        k += 1024;
        paramString.getChars(k, k, this.fCharBuffer, 0);
        this.fTempString.setValues(this.fCharBuffer, 0, 1024);
        this.fSchemaValidator.characters(this.fTempString, null);
      }
    }
  }
  
  private boolean useIsSameNode(Node paramNode)
  {
    if ((paramNode instanceof NodeImpl)) {
      return false;
    }
    Document localDocument = paramNode.getNodeType() == 9 ? (Document)paramNode : paramNode.getOwnerDocument();
    return (localDocument != null) && (localDocument.getImplementation().hasFeature("Core", "3.0"));
  }
  
  Node getCurrentElement()
  {
    return this.fCurrentElement;
  }
  
  final class DOMNamespaceContext
    implements NamespaceContext
  {
    protected String[] fNamespace = new String[32];
    protected int fNamespaceSize = 0;
    protected boolean fDOMContextBuilt = false;
    
    DOMNamespaceContext() {}
    
    public void pushContext()
    {
      DOMValidatorHelper.this.fNamespaceContext.pushContext();
    }
    
    public void popContext()
    {
      DOMValidatorHelper.this.fNamespaceContext.popContext();
    }
    
    public boolean declarePrefix(String paramString1, String paramString2)
    {
      return DOMValidatorHelper.this.fNamespaceContext.declarePrefix(paramString1, paramString2);
    }
    
    public String getURI(String paramString)
    {
      String str = DOMValidatorHelper.this.fNamespaceContext.getURI(paramString);
      if (str == null)
      {
        if (!this.fDOMContextBuilt)
        {
          fillNamespaceContext();
          this.fDOMContextBuilt = true;
        }
        if ((this.fNamespaceSize > 0) && (!DOMValidatorHelper.this.fNamespaceContext.containsPrefix(paramString))) {
          str = getURI0(paramString);
        }
      }
      return str;
    }
    
    public String getPrefix(String paramString)
    {
      return DOMValidatorHelper.this.fNamespaceContext.getPrefix(paramString);
    }
    
    public int getDeclaredPrefixCount()
    {
      return DOMValidatorHelper.this.fNamespaceContext.getDeclaredPrefixCount();
    }
    
    public String getDeclaredPrefixAt(int paramInt)
    {
      return DOMValidatorHelper.this.fNamespaceContext.getDeclaredPrefixAt(paramInt);
    }
    
    public Enumeration getAllPrefixes()
    {
      return DOMValidatorHelper.this.fNamespaceContext.getAllPrefixes();
    }
    
    public void reset()
    {
      this.fDOMContextBuilt = false;
      this.fNamespaceSize = 0;
    }
    
    private void fillNamespaceContext()
    {
      if (DOMValidatorHelper.this.fRoot != null) {
        for (Node localNode = DOMValidatorHelper.this.fRoot.getParentNode(); localNode != null; localNode = localNode.getParentNode()) {
          if (1 == localNode.getNodeType())
          {
            NamedNodeMap localNamedNodeMap = localNode.getAttributes();
            int i = localNamedNodeMap.getLength();
            for (int j = 0; j < i; j++)
            {
              Attr localAttr = (Attr)localNamedNodeMap.item(j);
              String str = localAttr.getValue();
              if (str == null) {
                str = XMLSymbols.EMPTY_STRING;
              }
              DOMValidatorHelper.this.fillQName(DOMValidatorHelper.this.fAttributeQName, localAttr);
              if (DOMValidatorHelper.this.fAttributeQName.uri == NamespaceContext.XMLNS_URI) {
                if (DOMValidatorHelper.this.fAttributeQName.prefix == XMLSymbols.PREFIX_XMLNS) {
                  declarePrefix0(DOMValidatorHelper.this.fAttributeQName.localpart, str.length() != 0 ? DOMValidatorHelper.this.fSymbolTable.addSymbol(str) : null);
                } else {
                  declarePrefix0(XMLSymbols.EMPTY_STRING, str.length() != 0 ? DOMValidatorHelper.this.fSymbolTable.addSymbol(str) : null);
                }
              }
            }
          }
        }
      }
    }
    
    private void declarePrefix0(String paramString1, String paramString2)
    {
      if (this.fNamespaceSize == this.fNamespace.length)
      {
        String[] arrayOfString = new String[this.fNamespaceSize * 2];
        System.arraycopy(this.fNamespace, 0, arrayOfString, 0, this.fNamespaceSize);
        this.fNamespace = arrayOfString;
      }
      this.fNamespace[(this.fNamespaceSize++)] = paramString1;
      this.fNamespace[(this.fNamespaceSize++)] = paramString2;
    }
    
    private String getURI0(String paramString)
    {
      for (int i = 0; i < this.fNamespaceSize; i += 2) {
        if (this.fNamespace[i] == paramString) {
          return this.fNamespace[(i + 1)];
        }
      }
      return null;
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.jaxp.validation.DOMValidatorHelper
 * JD-Core Version:    0.7.0.1
 */