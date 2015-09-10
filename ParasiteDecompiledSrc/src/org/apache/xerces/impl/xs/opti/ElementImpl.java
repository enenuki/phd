package org.apache.xerces.impl.xs.opti;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class ElementImpl
  extends DefaultElement
{
  SchemaDOM schemaDOM;
  Attr[] attrs;
  int row = -1;
  int col = -1;
  int parentRow = -1;
  int line;
  int column;
  int charOffset;
  String fAnnotation;
  String fSyntheticAnnotation;
  
  public ElementImpl(int paramInt1, int paramInt2, int paramInt3)
  {
    this.nodeType = 1;
    this.line = paramInt1;
    this.column = paramInt2;
    this.charOffset = paramInt3;
  }
  
  public ElementImpl(int paramInt1, int paramInt2)
  {
    this(paramInt1, paramInt2, -1);
  }
  
  public ElementImpl(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, int paramInt2, int paramInt3)
  {
    super(paramString1, paramString2, paramString3, paramString4, (short)1);
    this.line = paramInt1;
    this.column = paramInt2;
    this.charOffset = paramInt3;
  }
  
  public ElementImpl(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, int paramInt2)
  {
    this(paramString1, paramString2, paramString3, paramString4, paramInt1, paramInt2, -1);
  }
  
  public Document getOwnerDocument()
  {
    return this.schemaDOM;
  }
  
  public Node getParentNode()
  {
    return this.schemaDOM.relations[this.row][0];
  }
  
  public boolean hasChildNodes()
  {
    return this.parentRow != -1;
  }
  
  public Node getFirstChild()
  {
    if (this.parentRow == -1) {
      return null;
    }
    return this.schemaDOM.relations[this.parentRow][1];
  }
  
  public Node getLastChild()
  {
    if (this.parentRow == -1) {
      return null;
    }
    for (int i = 1; i < this.schemaDOM.relations[this.parentRow].length; i++) {
      if (this.schemaDOM.relations[this.parentRow][i] == null) {
        return this.schemaDOM.relations[this.parentRow][(i - 1)];
      }
    }
    if (i == 1) {
      i++;
    }
    return this.schemaDOM.relations[this.parentRow][(i - 1)];
  }
  
  public Node getPreviousSibling()
  {
    if (this.col == 1) {
      return null;
    }
    return this.schemaDOM.relations[this.row][(this.col - 1)];
  }
  
  public Node getNextSibling()
  {
    if (this.col == this.schemaDOM.relations[this.row].length - 1) {
      return null;
    }
    return this.schemaDOM.relations[this.row][(this.col + 1)];
  }
  
  public NamedNodeMap getAttributes()
  {
    return new NamedNodeMapImpl(this.attrs);
  }
  
  public boolean hasAttributes()
  {
    return this.attrs.length != 0;
  }
  
  public String getTagName()
  {
    return this.rawname;
  }
  
  public String getAttribute(String paramString)
  {
    for (int i = 0; i < this.attrs.length; i++) {
      if (this.attrs[i].getName().equals(paramString)) {
        return this.attrs[i].getValue();
      }
    }
    return "";
  }
  
  public Attr getAttributeNode(String paramString)
  {
    for (int i = 0; i < this.attrs.length; i++) {
      if (this.attrs[i].getName().equals(paramString)) {
        return this.attrs[i];
      }
    }
    return null;
  }
  
  public String getAttributeNS(String paramString1, String paramString2)
  {
    for (int i = 0; i < this.attrs.length; i++) {
      if ((this.attrs[i].getLocalName().equals(paramString2)) && (this.attrs[i].getNamespaceURI().equals(paramString1))) {
        return this.attrs[i].getValue();
      }
    }
    return "";
  }
  
  public Attr getAttributeNodeNS(String paramString1, String paramString2)
  {
    for (int i = 0; i < this.attrs.length; i++) {
      if ((this.attrs[i].getName().equals(paramString2)) && (this.attrs[i].getNamespaceURI().equals(paramString1))) {
        return this.attrs[i];
      }
    }
    return null;
  }
  
  public boolean hasAttribute(String paramString)
  {
    for (int i = 0; i < this.attrs.length; i++) {
      if (this.attrs[i].getName().equals(paramString)) {
        return true;
      }
    }
    return false;
  }
  
  public boolean hasAttributeNS(String paramString1, String paramString2)
  {
    for (int i = 0; i < this.attrs.length; i++) {
      if ((this.attrs[i].getName().equals(paramString2)) && (this.attrs[i].getNamespaceURI().equals(paramString1))) {
        return true;
      }
    }
    return false;
  }
  
  public void setAttribute(String paramString1, String paramString2)
  {
    for (int i = 0; i < this.attrs.length; i++) {
      if (this.attrs[i].getName().equals(paramString1))
      {
        this.attrs[i].setValue(paramString2);
        return;
      }
    }
  }
  
  public int getLineNumber()
  {
    return this.line;
  }
  
  public int getColumnNumber()
  {
    return this.column;
  }
  
  public int getCharacterOffset()
  {
    return this.charOffset;
  }
  
  public String getAnnotation()
  {
    return this.fAnnotation;
  }
  
  public String getSyntheticAnnotation()
  {
    return this.fSyntheticAnnotation;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.opti.ElementImpl
 * JD-Core Version:    0.7.0.1
 */