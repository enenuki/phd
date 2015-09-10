package org.apache.xerces.impl.xs;

import org.apache.xerces.impl.xs.opti.ElementImpl;
import org.apache.xerces.util.NamespaceSupport;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.util.XMLSymbols;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xni.QName;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class SchemaNamespaceSupport
  extends NamespaceSupport
{
  private SchemaRootContext fSchemaRootContext = null;
  
  public SchemaNamespaceSupport(Element paramElement, SymbolTable paramSymbolTable)
  {
    if ((paramElement != null) && (!(paramElement instanceof ElementImpl)))
    {
      Document localDocument = paramElement.getOwnerDocument();
      if ((localDocument != null) && (paramElement != localDocument.getDocumentElement())) {
        this.fSchemaRootContext = new SchemaRootContext(paramElement, paramSymbolTable);
      }
    }
  }
  
  public SchemaNamespaceSupport(SchemaNamespaceSupport paramSchemaNamespaceSupport)
  {
    this.fSchemaRootContext = paramSchemaNamespaceSupport.fSchemaRootContext;
    this.fNamespaceSize = paramSchemaNamespaceSupport.fNamespaceSize;
    if (this.fNamespace.length < this.fNamespaceSize) {
      this.fNamespace = new String[this.fNamespaceSize];
    }
    System.arraycopy(paramSchemaNamespaceSupport.fNamespace, 0, this.fNamespace, 0, this.fNamespaceSize);
    this.fCurrentContext = paramSchemaNamespaceSupport.fCurrentContext;
    if (this.fContext.length <= this.fCurrentContext) {
      this.fContext = new int[this.fCurrentContext + 1];
    }
    System.arraycopy(paramSchemaNamespaceSupport.fContext, 0, this.fContext, 0, this.fCurrentContext + 1);
  }
  
  public void setEffectiveContext(String[] paramArrayOfString)
  {
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0)) {
      return;
    }
    pushContext();
    int i = this.fNamespaceSize + paramArrayOfString.length;
    if (this.fNamespace.length < i)
    {
      String[] arrayOfString = new String[i];
      System.arraycopy(this.fNamespace, 0, arrayOfString, 0, this.fNamespace.length);
      this.fNamespace = arrayOfString;
    }
    System.arraycopy(paramArrayOfString, 0, this.fNamespace, this.fNamespaceSize, paramArrayOfString.length);
    this.fNamespaceSize = i;
  }
  
  public String[] getEffectiveLocalContext()
  {
    String[] arrayOfString = null;
    if (this.fCurrentContext >= 3)
    {
      int i = this.fContext[3];
      int j = this.fNamespaceSize - i;
      if (j > 0)
      {
        arrayOfString = new String[j];
        System.arraycopy(this.fNamespace, i, arrayOfString, 0, j);
      }
    }
    return arrayOfString;
  }
  
  public void makeGlobal()
  {
    if (this.fCurrentContext >= 3)
    {
      this.fCurrentContext = 3;
      this.fNamespaceSize = this.fContext[3];
    }
  }
  
  public String getURI(String paramString)
  {
    String str = super.getURI(paramString);
    if ((str == null) && (this.fSchemaRootContext != null))
    {
      if (!this.fSchemaRootContext.fDOMContextBuilt)
      {
        this.fSchemaRootContext.fillNamespaceContext();
        this.fSchemaRootContext.fDOMContextBuilt = true;
      }
      if ((this.fSchemaRootContext.fNamespaceSize > 0) && (!containsPrefix(paramString))) {
        str = this.fSchemaRootContext.getURI(paramString);
      }
    }
    return str;
  }
  
  static final class SchemaRootContext
  {
    String[] fNamespace = new String[32];
    int fNamespaceSize = 0;
    boolean fDOMContextBuilt = false;
    private final Element fSchemaRoot;
    private final SymbolTable fSymbolTable;
    private final QName fAttributeQName = new QName();
    
    SchemaRootContext(Element paramElement, SymbolTable paramSymbolTable)
    {
      this.fSchemaRoot = paramElement;
      this.fSymbolTable = paramSymbolTable;
    }
    
    void fillNamespaceContext()
    {
      if (this.fSchemaRoot != null) {
        for (Node localNode = this.fSchemaRoot.getParentNode(); localNode != null; localNode = localNode.getParentNode()) {
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
              fillQName(this.fAttributeQName, localAttr);
              if (this.fAttributeQName.uri == NamespaceContext.XMLNS_URI) {
                if (this.fAttributeQName.prefix == XMLSymbols.PREFIX_XMLNS) {
                  declarePrefix(this.fAttributeQName.localpart, str.length() != 0 ? this.fSymbolTable.addSymbol(str) : null);
                } else {
                  declarePrefix(XMLSymbols.EMPTY_STRING, str.length() != 0 ? this.fSymbolTable.addSymbol(str) : null);
                }
              }
            }
          }
        }
      }
    }
    
    String getURI(String paramString)
    {
      for (int i = 0; i < this.fNamespaceSize; i += 2) {
        if (this.fNamespace[i] == paramString) {
          return this.fNamespace[(i + 1)];
        }
      }
      return null;
    }
    
    private void declarePrefix(String paramString1, String paramString2)
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
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.SchemaNamespaceSupport
 * JD-Core Version:    0.7.0.1
 */