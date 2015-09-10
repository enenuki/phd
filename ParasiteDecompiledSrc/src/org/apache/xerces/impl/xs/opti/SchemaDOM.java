package org.apache.xerces.impl.xs.opti;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Enumeration;
import org.apache.xerces.util.XMLSymbols;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XMLString;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class SchemaDOM
  extends DefaultDocument
{
  static final int relationsRowResizeFactor = 15;
  static final int relationsColResizeFactor = 10;
  NodeImpl[][] relations;
  ElementImpl parent;
  int currLoc;
  int nextFreeLoc;
  boolean hidden;
  boolean inCDATA;
  StringBuffer fAnnotationBuffer = null;
  
  public SchemaDOM()
  {
    reset();
  }
  
  public ElementImpl startElement(QName paramQName, XMLAttributes paramXMLAttributes, int paramInt1, int paramInt2, int paramInt3)
  {
    ElementImpl localElementImpl = new ElementImpl(paramInt1, paramInt2, paramInt3);
    processElement(paramQName, paramXMLAttributes, localElementImpl);
    this.parent = localElementImpl;
    return localElementImpl;
  }
  
  public ElementImpl emptyElement(QName paramQName, XMLAttributes paramXMLAttributes, int paramInt1, int paramInt2, int paramInt3)
  {
    ElementImpl localElementImpl = new ElementImpl(paramInt1, paramInt2, paramInt3);
    processElement(paramQName, paramXMLAttributes, localElementImpl);
    return localElementImpl;
  }
  
  public ElementImpl startElement(QName paramQName, XMLAttributes paramXMLAttributes, int paramInt1, int paramInt2)
  {
    return startElement(paramQName, paramXMLAttributes, paramInt1, paramInt2, -1);
  }
  
  public ElementImpl emptyElement(QName paramQName, XMLAttributes paramXMLAttributes, int paramInt1, int paramInt2)
  {
    return emptyElement(paramQName, paramXMLAttributes, paramInt1, paramInt2, -1);
  }
  
  private void processElement(QName paramQName, XMLAttributes paramXMLAttributes, ElementImpl paramElementImpl)
  {
    paramElementImpl.prefix = paramQName.prefix;
    paramElementImpl.localpart = paramQName.localpart;
    paramElementImpl.rawname = paramQName.rawname;
    paramElementImpl.uri = paramQName.uri;
    paramElementImpl.schemaDOM = this;
    Attr[] arrayOfAttr = new Attr[paramXMLAttributes.getLength()];
    for (int i = 0; i < paramXMLAttributes.getLength(); i++) {
      arrayOfAttr[i] = new AttrImpl(null, paramXMLAttributes.getPrefix(i), paramXMLAttributes.getLocalName(i), paramXMLAttributes.getQName(i), paramXMLAttributes.getURI(i), paramXMLAttributes.getValue(i));
    }
    paramElementImpl.attrs = arrayOfAttr;
    if (this.nextFreeLoc == this.relations.length) {
      resizeRelations();
    }
    if (this.relations[this.currLoc][0] != this.parent)
    {
      this.relations[this.nextFreeLoc][0] = this.parent;
      this.currLoc = (this.nextFreeLoc++);
    }
    int j = 0;
    int k = 1;
    for (k = 1; k < this.relations[this.currLoc].length; k++) {
      if (this.relations[this.currLoc][k] == null)
      {
        j = 1;
        break;
      }
    }
    if (j == 0) {
      resizeRelations(this.currLoc);
    }
    this.relations[this.currLoc][k] = paramElementImpl;
    this.parent.parentRow = this.currLoc;
    paramElementImpl.row = this.currLoc;
    paramElementImpl.col = k;
  }
  
  public void endElement()
  {
    this.currLoc = this.parent.row;
    this.parent = ((ElementImpl)this.relations[this.currLoc][0]);
  }
  
  void comment(XMLString paramXMLString)
  {
    this.fAnnotationBuffer.append("<!--");
    if (paramXMLString.length > 0) {
      this.fAnnotationBuffer.append(paramXMLString.ch, paramXMLString.offset, paramXMLString.length);
    }
    this.fAnnotationBuffer.append("-->");
  }
  
  void processingInstruction(String paramString, XMLString paramXMLString)
  {
    this.fAnnotationBuffer.append("<?").append(paramString);
    if (paramXMLString.length > 0) {
      this.fAnnotationBuffer.append(' ').append(paramXMLString.ch, paramXMLString.offset, paramXMLString.length);
    }
    this.fAnnotationBuffer.append("?>");
  }
  
  void characters(XMLString paramXMLString)
  {
    if (!this.inCDATA) {
      for (int i = paramXMLString.offset; i < paramXMLString.offset + paramXMLString.length; i++)
      {
        char c = paramXMLString.ch[i];
        if (c == '&') {
          this.fAnnotationBuffer.append("&amp;");
        } else if (c == '<') {
          this.fAnnotationBuffer.append("&lt;");
        } else if (c == '>') {
          this.fAnnotationBuffer.append("&gt;");
        } else if (c == '\r') {
          this.fAnnotationBuffer.append("&#xD;");
        } else {
          this.fAnnotationBuffer.append(c);
        }
      }
    } else {
      this.fAnnotationBuffer.append(paramXMLString.ch, paramXMLString.offset, paramXMLString.length);
    }
  }
  
  void endAnnotation(QName paramQName, ElementImpl paramElementImpl)
  {
    this.fAnnotationBuffer.append("\n</").append(paramQName.rawname).append(">");
    paramElementImpl.fAnnotation = this.fAnnotationBuffer.toString();
    this.fAnnotationBuffer = null;
  }
  
  void endAnnotationElement(QName paramQName)
  {
    this.fAnnotationBuffer.append("</").append(paramQName.rawname).append(">");
  }
  
  void endSyntheticAnnotationElement(QName paramQName, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.fAnnotationBuffer.append("\n</").append(paramQName.rawname).append(">");
      this.parent.fSyntheticAnnotation = this.fAnnotationBuffer.toString();
      this.fAnnotationBuffer = null;
    }
    else
    {
      this.fAnnotationBuffer.append("</").append(paramQName.rawname).append(">");
    }
  }
  
  void startAnnotationCDATA()
  {
    this.inCDATA = true;
    this.fAnnotationBuffer.append("<![CDATA[");
  }
  
  void endAnnotationCDATA()
  {
    this.fAnnotationBuffer.append("]]>");
    this.inCDATA = false;
  }
  
  private void resizeRelations()
  {
    NodeImpl[][] arrayOfNodeImpl; = new NodeImpl[this.relations.length + 15][];
    System.arraycopy(this.relations, 0, arrayOfNodeImpl;, 0, this.relations.length);
    for (int i = this.relations.length; i < arrayOfNodeImpl;.length; i++) {
      arrayOfNodeImpl;[i] = new NodeImpl[10];
    }
    this.relations = arrayOfNodeImpl;;
  }
  
  private void resizeRelations(int paramInt)
  {
    NodeImpl[] arrayOfNodeImpl = new NodeImpl[this.relations[paramInt].length + 10];
    System.arraycopy(this.relations[paramInt], 0, arrayOfNodeImpl, 0, this.relations[paramInt].length);
    this.relations[paramInt] = arrayOfNodeImpl;
  }
  
  public void reset()
  {
    if (this.relations != null) {
      for (int i = 0; i < this.relations.length; i++) {
        for (int j = 0; j < this.relations[i].length; j++) {
          this.relations[i][j] = null;
        }
      }
    }
    this.relations = new NodeImpl[15][];
    this.parent = new ElementImpl(0, 0, 0);
    this.parent.rawname = "DOCUMENT_NODE";
    this.currLoc = 0;
    this.nextFreeLoc = 1;
    this.inCDATA = false;
    for (int k = 0; k < 15; k++) {
      this.relations[k] = new NodeImpl[10];
    }
    this.relations[this.currLoc][0] = this.parent;
  }
  
  public void printDOM() {}
  
  public static void traverse(Node paramNode, int paramInt)
  {
    indent(paramInt);
    System.out.print("<" + paramNode.getNodeName());
    Object localObject;
    if (paramNode.hasAttributes())
    {
      localObject = paramNode.getAttributes();
      for (int i = 0; i < ((NamedNodeMap)localObject).getLength(); i++) {
        System.out.print("  " + ((Attr)((NamedNodeMap)localObject).item(i)).getName() + "=\"" + ((Attr)((NamedNodeMap)localObject).item(i)).getValue() + "\"");
      }
    }
    if (paramNode.hasChildNodes())
    {
      System.out.println(">");
      paramInt += 4;
      for (localObject = paramNode.getFirstChild(); localObject != null; localObject = ((Node)localObject).getNextSibling()) {
        traverse((Node)localObject, paramInt);
      }
      paramInt -= 4;
      indent(paramInt);
      System.out.println("</" + paramNode.getNodeName() + ">");
    }
    else
    {
      System.out.println("/>");
    }
  }
  
  public static void indent(int paramInt)
  {
    for (int i = 0; i < paramInt; i++) {
      System.out.print(' ');
    }
  }
  
  public Element getDocumentElement()
  {
    return (ElementImpl)this.relations[0][1];
  }
  
  void startAnnotation(QName paramQName, XMLAttributes paramXMLAttributes, NamespaceContext paramNamespaceContext)
  {
    if (this.fAnnotationBuffer == null) {
      this.fAnnotationBuffer = new StringBuffer(256);
    }
    this.fAnnotationBuffer.append("<").append(paramQName.rawname).append(" ");
    ArrayList localArrayList = new ArrayList();
    String str1;
    String str2;
    for (int i = 0; i < paramXMLAttributes.getLength(); i++)
    {
      localObject = paramXMLAttributes.getValue(i);
      str1 = paramXMLAttributes.getPrefix(i);
      str2 = paramXMLAttributes.getQName(i);
      if ((str1 == XMLSymbols.PREFIX_XMLNS) || (str2 == XMLSymbols.PREFIX_XMLNS)) {
        localArrayList.add(str1 == XMLSymbols.PREFIX_XMLNS ? paramXMLAttributes.getLocalName(i) : XMLSymbols.EMPTY_STRING);
      }
      this.fAnnotationBuffer.append(str2).append("=\"").append(processAttValue((String)localObject)).append("\" ");
    }
    Object localObject = paramNamespaceContext.getAllPrefixes();
    while (((Enumeration)localObject).hasMoreElements())
    {
      str1 = (String)((Enumeration)localObject).nextElement();
      str2 = paramNamespaceContext.getURI(str1);
      if (str2 == null) {
        str2 = XMLSymbols.EMPTY_STRING;
      }
      if (!localArrayList.contains(str1)) {
        if (str1 == XMLSymbols.EMPTY_STRING) {
          this.fAnnotationBuffer.append("xmlns").append("=\"").append(processAttValue(str2)).append("\" ");
        } else {
          this.fAnnotationBuffer.append("xmlns:").append(str1).append("=\"").append(processAttValue(str2)).append("\" ");
        }
      }
    }
    this.fAnnotationBuffer.append(">\n");
  }
  
  void startAnnotationElement(QName paramQName, XMLAttributes paramXMLAttributes)
  {
    this.fAnnotationBuffer.append("<").append(paramQName.rawname);
    for (int i = 0; i < paramXMLAttributes.getLength(); i++)
    {
      String str = paramXMLAttributes.getValue(i);
      this.fAnnotationBuffer.append(" ").append(paramXMLAttributes.getQName(i)).append("=\"").append(processAttValue(str)).append("\"");
    }
    this.fAnnotationBuffer.append(">");
  }
  
  private static String processAttValue(String paramString)
  {
    int i = paramString.length();
    for (int j = 0; j < i; j++)
    {
      int k = paramString.charAt(j);
      if ((k == 34) || (k == 60) || (k == 38) || (k == 9) || (k == 10) || (k == 13)) {
        return escapeAttValue(paramString, j);
      }
    }
    return paramString;
  }
  
  private static String escapeAttValue(String paramString, int paramInt)
  {
    int j = paramString.length();
    StringBuffer localStringBuffer = new StringBuffer(j);
    localStringBuffer.append(paramString.substring(0, paramInt));
    for (int i = paramInt; i < j; i++)
    {
      char c = paramString.charAt(i);
      if (c == '"') {
        localStringBuffer.append("&quot;");
      } else if (c == '<') {
        localStringBuffer.append("&lt;");
      } else if (c == '&') {
        localStringBuffer.append("&amp;");
      } else if (c == '\t') {
        localStringBuffer.append("&#x9;");
      } else if (c == '\n') {
        localStringBuffer.append("&#xA;");
      } else if (c == '\r') {
        localStringBuffer.append("&#xD;");
      } else {
        localStringBuffer.append(c);
      }
    }
    return localStringBuffer.toString();
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.opti.SchemaDOM
 * JD-Core Version:    0.7.0.1
 */