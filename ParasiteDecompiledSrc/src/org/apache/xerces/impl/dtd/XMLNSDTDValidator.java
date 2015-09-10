package org.apache.xerces.impl.dtd;

import org.apache.xerces.impl.XMLErrorReporter;
import org.apache.xerces.util.SymbolTable;
import org.apache.xerces.util.XMLSymbols;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XMLDocumentHandler;
import org.apache.xerces.xni.XNIException;

public class XMLNSDTDValidator
  extends XMLDTDValidator
{
  private final QName fAttributeQName = new QName();
  
  protected final void startNamespaceScope(QName paramQName, XMLAttributes paramXMLAttributes, Augmentations paramAugmentations)
    throws XNIException
  {
    this.fNamespaceContext.pushContext();
    if (paramQName.prefix == XMLSymbols.PREFIX_XMLNS) {
      this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "ElementXMLNSPrefix", new Object[] { paramQName.rawname }, (short)2);
    }
    int i = paramXMLAttributes.getLength();
    String str3;
    for (int j = 0; j < i; j++)
    {
      str1 = paramXMLAttributes.getLocalName(j);
      String str2 = paramXMLAttributes.getPrefix(j);
      if ((str2 == XMLSymbols.PREFIX_XMLNS) || ((str2 == XMLSymbols.EMPTY_STRING) && (str1 == XMLSymbols.PREFIX_XMLNS)))
      {
        str3 = this.fSymbolTable.addSymbol(paramXMLAttributes.getValue(j));
        if ((str2 == XMLSymbols.PREFIX_XMLNS) && (str1 == XMLSymbols.PREFIX_XMLNS)) {
          this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "CantBindXMLNS", new Object[] { paramXMLAttributes.getQName(j) }, (short)2);
        }
        if (str3 == NamespaceContext.XMLNS_URI) {
          this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "CantBindXMLNS", new Object[] { paramXMLAttributes.getQName(j) }, (short)2);
        }
        if (str1 == XMLSymbols.PREFIX_XML)
        {
          if (str3 != NamespaceContext.XML_URI) {
            this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "CantBindXML", new Object[] { paramXMLAttributes.getQName(j) }, (short)2);
          }
        }
        else if (str3 == NamespaceContext.XML_URI) {
          this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "CantBindXML", new Object[] { paramXMLAttributes.getQName(j) }, (short)2);
        }
        str2 = str1 != XMLSymbols.PREFIX_XMLNS ? str1 : XMLSymbols.EMPTY_STRING;
        if ((str3 == XMLSymbols.EMPTY_STRING) && (str1 != XMLSymbols.PREFIX_XMLNS)) {
          this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "EmptyPrefixedAttName", new Object[] { paramXMLAttributes.getQName(j) }, (short)2);
        } else {
          this.fNamespaceContext.declarePrefix(str2, str3.length() != 0 ? str3 : null);
        }
      }
    }
    String str1 = paramQName.prefix != null ? paramQName.prefix : XMLSymbols.EMPTY_STRING;
    paramQName.uri = this.fNamespaceContext.getURI(str1);
    if ((paramQName.prefix == null) && (paramQName.uri != null)) {
      paramQName.prefix = XMLSymbols.EMPTY_STRING;
    }
    if ((paramQName.prefix != null) && (paramQName.uri == null)) {
      this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "ElementPrefixUnbound", new Object[] { paramQName.prefix, paramQName.rawname }, (short)2);
    }
    for (int k = 0; k < i; k++)
    {
      paramXMLAttributes.getName(k, this.fAttributeQName);
      str3 = this.fAttributeQName.prefix != null ? this.fAttributeQName.prefix : XMLSymbols.EMPTY_STRING;
      String str4 = this.fAttributeQName.rawname;
      if (str4 == XMLSymbols.PREFIX_XMLNS)
      {
        this.fAttributeQName.uri = this.fNamespaceContext.getURI(XMLSymbols.PREFIX_XMLNS);
        paramXMLAttributes.setName(k, this.fAttributeQName);
      }
      else if (str3 != XMLSymbols.EMPTY_STRING)
      {
        this.fAttributeQName.uri = this.fNamespaceContext.getURI(str3);
        if (this.fAttributeQName.uri == null) {
          this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "AttributePrefixUnbound", new Object[] { paramQName.rawname, str4, str3 }, (short)2);
        }
        paramXMLAttributes.setName(k, this.fAttributeQName);
      }
    }
    int m = paramXMLAttributes.getLength();
    for (int n = 0; n < m - 1; n++)
    {
      String str5 = paramXMLAttributes.getURI(n);
      if ((str5 != null) && (str5 != NamespaceContext.XMLNS_URI))
      {
        String str6 = paramXMLAttributes.getLocalName(n);
        for (int i1 = n + 1; i1 < m; i1++)
        {
          String str7 = paramXMLAttributes.getLocalName(i1);
          String str8 = paramXMLAttributes.getURI(i1);
          if ((str6 == str7) && (str5 == str8)) {
            this.fErrorReporter.reportError("http://www.w3.org/TR/1999/REC-xml-names-19990114", "AttributeNSNotUnique", new Object[] { paramQName.rawname, str6, str5 }, (short)2);
          }
        }
      }
    }
  }
  
  protected void endNamespaceScope(QName paramQName, Augmentations paramAugmentations, boolean paramBoolean)
    throws XNIException
  {
    String str = paramQName.prefix != null ? paramQName.prefix : XMLSymbols.EMPTY_STRING;
    paramQName.uri = this.fNamespaceContext.getURI(str);
    if (paramQName.uri != null) {
      paramQName.prefix = str;
    }
    if ((this.fDocumentHandler != null) && (!paramBoolean)) {
      this.fDocumentHandler.endElement(paramQName, paramAugmentations);
    }
    this.fNamespaceContext.popContext();
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.dtd.XMLNSDTDValidator
 * JD-Core Version:    0.7.0.1
 */