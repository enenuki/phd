package org.apache.xerces.impl.xs.opti;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

public class TextImpl
  extends DefaultText
{
  String fData = null;
  SchemaDOM fSchemaDOM = null;
  int fRow;
  int fCol;
  
  public TextImpl(StringBuffer paramStringBuffer, SchemaDOM paramSchemaDOM, int paramInt1, int paramInt2)
  {
    this.fData = paramStringBuffer.toString();
    this.fSchemaDOM = paramSchemaDOM;
    this.fRow = paramInt1;
    this.fCol = paramInt2;
    this.rawname = (this.prefix = this.localpart = this.uri = null);
    this.nodeType = 3;
  }
  
  public Node getParentNode()
  {
    return this.fSchemaDOM.relations[this.fRow][0];
  }
  
  public Node getPreviousSibling()
  {
    if (this.fCol == 1) {
      return null;
    }
    return this.fSchemaDOM.relations[this.fRow][(this.fCol - 1)];
  }
  
  public Node getNextSibling()
  {
    if (this.fCol == this.fSchemaDOM.relations[this.fRow].length - 1) {
      return null;
    }
    return this.fSchemaDOM.relations[this.fRow][(this.fCol + 1)];
  }
  
  public String getData()
    throws DOMException
  {
    return this.fData;
  }
  
  public int getLength()
  {
    if (this.fData == null) {
      return 0;
    }
    return this.fData.length();
  }
  
  public String substringData(int paramInt1, int paramInt2)
    throws DOMException
  {
    if (this.fData == null) {
      return null;
    }
    if ((paramInt2 < 0) || (paramInt1 < 0) || (paramInt1 > this.fData.length())) {
      throw new DOMException((short)1, "parameter error");
    }
    if (paramInt1 + paramInt2 >= this.fData.length()) {
      return this.fData.substring(paramInt1);
    }
    return this.fData.substring(paramInt1, paramInt1 + paramInt2);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.opti.TextImpl
 * JD-Core Version:    0.7.0.1
 */