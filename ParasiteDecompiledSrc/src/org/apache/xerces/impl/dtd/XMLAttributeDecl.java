package org.apache.xerces.impl.dtd;

import org.apache.xerces.xni.QName;

public class XMLAttributeDecl
{
  public final QName name = new QName();
  public final XMLSimpleType simpleType = new XMLSimpleType();
  public boolean optional;
  
  public void setValues(QName paramQName, XMLSimpleType paramXMLSimpleType, boolean paramBoolean)
  {
    this.name.setValues(paramQName);
    this.simpleType.setValues(paramXMLSimpleType);
    this.optional = paramBoolean;
  }
  
  public void clear()
  {
    this.name.clear();
    this.simpleType.clear();
    this.optional = false;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.dtd.XMLAttributeDecl
 * JD-Core Version:    0.7.0.1
 */