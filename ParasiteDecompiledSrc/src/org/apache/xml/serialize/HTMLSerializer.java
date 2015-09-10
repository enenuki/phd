package org.apache.xml.serialize;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import org.apache.xerces.dom.DOMMessageFormatter;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.AttributeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * @deprecated
 */
public class HTMLSerializer
  extends BaseMarkupSerializer
{
  private boolean _xhtml;
  public static final String XHTMLNamespace = "http://www.w3.org/1999/xhtml";
  private String fUserXHTMLNamespace = null;
  
  protected HTMLSerializer(boolean paramBoolean, OutputFormat paramOutputFormat)
  {
    super(paramOutputFormat);
    this._xhtml = paramBoolean;
  }
  
  public HTMLSerializer()
  {
    this(false, new OutputFormat("html", "ISO-8859-1", false));
  }
  
  public HTMLSerializer(OutputFormat paramOutputFormat)
  {
    this(false, paramOutputFormat != null ? paramOutputFormat : new OutputFormat("html", "ISO-8859-1", false));
  }
  
  public HTMLSerializer(Writer paramWriter, OutputFormat paramOutputFormat)
  {
    this(false, paramOutputFormat != null ? paramOutputFormat : new OutputFormat("html", "ISO-8859-1", false));
    setOutputCharStream(paramWriter);
  }
  
  public HTMLSerializer(OutputStream paramOutputStream, OutputFormat paramOutputFormat)
  {
    this(false, paramOutputFormat != null ? paramOutputFormat : new OutputFormat("html", "ISO-8859-1", false));
    setOutputByteStream(paramOutputStream);
  }
  
  public void setOutputFormat(OutputFormat paramOutputFormat)
  {
    super.setOutputFormat(paramOutputFormat != null ? paramOutputFormat : new OutputFormat("html", "ISO-8859-1", false));
  }
  
  public void setXHTMLNamespace(String paramString)
  {
    this.fUserXHTMLNamespace = paramString;
  }
  
  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    int j = 0;
    try
    {
      if (this._printer == null) {
        throw new IllegalStateException(DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "NoWriterSupplied", null));
      }
      ElementState localElementState = getElementState();
      if (isDocumentState())
      {
        if (!this._started) {
          startDocument((paramString2 == null) || (paramString2.length() == 0) ? paramString3 : paramString2);
        }
      }
      else
      {
        if (localElementState.empty) {
          this._printer.printText('>');
        }
        if ((this._indenting) && (!localElementState.preserveSpace) && ((localElementState.empty) || (localElementState.afterElement))) {
          this._printer.breakLine();
        }
      }
      boolean bool = localElementState.preserveSpace;
      int k = (paramString1 != null) && (paramString1.length() != 0) ? 1 : 0;
      Object localObject;
      if ((paramString3 == null) || (paramString3.length() == 0))
      {
        paramString3 = paramString2;
        if (k != 0)
        {
          localObject = getPrefix(paramString1);
          if ((localObject != null) && (((String)localObject).length() != 0)) {
            paramString3 = (String)localObject + ":" + paramString2;
          }
        }
        j = 1;
      }
      String str3;
      if (k == 0) {
        str3 = paramString3;
      } else if ((paramString1.equals("http://www.w3.org/1999/xhtml")) || ((this.fUserXHTMLNamespace != null) && (this.fUserXHTMLNamespace.equals(paramString1)))) {
        str3 = paramString2;
      } else {
        str3 = null;
      }
      this._printer.printText('<');
      if (this._xhtml) {
        this._printer.printText(paramString3.toLowerCase(Locale.ENGLISH));
      } else {
        this._printer.printText(paramString3);
      }
      this._printer.indent();
      String str1;
      String str2;
      if (paramAttributes != null) {
        for (int i = 0; i < paramAttributes.getLength(); i++)
        {
          this._printer.printSpace();
          str1 = paramAttributes.getQName(i).toLowerCase(Locale.ENGLISH);
          str2 = paramAttributes.getValue(i);
          if ((this._xhtml) || (k != 0))
          {
            if (str2 == null)
            {
              this._printer.printText(str1);
              this._printer.printText("=\"\"");
            }
            else
            {
              this._printer.printText(str1);
              this._printer.printText("=\"");
              printEscaped(str2);
              this._printer.printText('"');
            }
          }
          else
          {
            if (str2 == null) {
              str2 = "";
            }
            if ((!this._format.getPreserveEmptyAttributes()) && (str2.length() == 0))
            {
              this._printer.printText(str1);
            }
            else if (HTMLdtd.isURI(paramString3, str1))
            {
              this._printer.printText(str1);
              this._printer.printText("=\"");
              this._printer.printText(escapeURI(str2));
              this._printer.printText('"');
            }
            else if (HTMLdtd.isBoolean(paramString3, str1))
            {
              this._printer.printText(str1);
            }
            else
            {
              this._printer.printText(str1);
              this._printer.printText("=\"");
              printEscaped(str2);
              this._printer.printText('"');
            }
          }
        }
      }
      if ((str3 != null) && (HTMLdtd.isPreserveSpace(str3))) {
        bool = true;
      }
      if (j != 0)
      {
        localObject = this._prefixes.keys();
        while (((Enumeration)localObject).hasMoreElements())
        {
          this._printer.printSpace();
          str2 = (String)((Enumeration)localObject).nextElement();
          str1 = (String)this._prefixes.get(str2);
          if (str1.length() == 0)
          {
            this._printer.printText("xmlns=\"");
            printEscaped(str2);
            this._printer.printText('"');
          }
          else
          {
            this._printer.printText("xmlns:");
            this._printer.printText(str1);
            this._printer.printText("=\"");
            printEscaped(str2);
            this._printer.printText('"');
          }
        }
      }
      localElementState = enterElementState(paramString1, paramString2, paramString3, bool);
      if ((str3 != null) && ((str3.equalsIgnoreCase("A")) || (str3.equalsIgnoreCase("TD"))))
      {
        localElementState.empty = false;
        this._printer.printText('>');
      }
      if ((str3 != null) && ((paramString3.equalsIgnoreCase("SCRIPT")) || (paramString3.equalsIgnoreCase("STYLE")))) {
        if (this._xhtml) {
          localElementState.doCData = true;
        } else {
          localElementState.unescaped = true;
        }
      }
    }
    catch (IOException localIOException)
    {
      throw new SAXException(localIOException);
    }
  }
  
  public void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    try
    {
      endElementIO(paramString1, paramString2, paramString3);
    }
    catch (IOException localIOException)
    {
      throw new SAXException(localIOException);
    }
  }
  
  public void endElementIO(String paramString1, String paramString2, String paramString3)
    throws IOException
  {
    this._printer.unindent();
    ElementState localElementState = getElementState();
    String str;
    if ((localElementState.namespaceURI == null) || (localElementState.namespaceURI.length() == 0)) {
      str = localElementState.rawName;
    } else if ((localElementState.namespaceURI.equals("http://www.w3.org/1999/xhtml")) || ((this.fUserXHTMLNamespace != null) && (this.fUserXHTMLNamespace.equals(localElementState.namespaceURI)))) {
      str = localElementState.localName;
    } else {
      str = null;
    }
    if (this._xhtml)
    {
      if (localElementState.empty)
      {
        this._printer.printText(" />");
      }
      else
      {
        if (localElementState.inCData) {
          this._printer.printText("]]>");
        }
        this._printer.printText("</");
        this._printer.printText(localElementState.rawName.toLowerCase(Locale.ENGLISH));
        this._printer.printText('>');
      }
    }
    else
    {
      if (localElementState.empty) {
        this._printer.printText('>');
      }
      if ((str == null) || (!HTMLdtd.isOnlyOpening(str)))
      {
        if ((this._indenting) && (!localElementState.preserveSpace) && (localElementState.afterElement)) {
          this._printer.breakLine();
        }
        if (localElementState.inCData) {
          this._printer.printText("]]>");
        }
        this._printer.printText("</");
        this._printer.printText(localElementState.rawName);
        this._printer.printText('>');
      }
    }
    localElementState = leaveElementState();
    if ((str == null) || ((!str.equalsIgnoreCase("A")) && (!str.equalsIgnoreCase("TD")))) {
      localElementState.afterElement = true;
    }
    localElementState.empty = false;
    if (isDocumentState()) {
      this._printer.flush();
    }
  }
  
  public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    try
    {
      ElementState localElementState = content();
      localElementState.doCData = false;
      super.characters(paramArrayOfChar, paramInt1, paramInt2);
    }
    catch (IOException localIOException)
    {
      throw new SAXException(localIOException);
    }
  }
  
  public void startElement(String paramString, AttributeList paramAttributeList)
    throws SAXException
  {
    try
    {
      if (this._printer == null) {
        throw new IllegalStateException(DOMMessageFormatter.formatMessage("http://apache.org/xml/serializer", "NoWriterSupplied", null));
      }
      ElementState localElementState = getElementState();
      if (isDocumentState())
      {
        if (!this._started) {
          startDocument(paramString);
        }
      }
      else
      {
        if (localElementState.empty) {
          this._printer.printText('>');
        }
        if ((this._indenting) && (!localElementState.preserveSpace) && ((localElementState.empty) || (localElementState.afterElement))) {
          this._printer.breakLine();
        }
      }
      boolean bool = localElementState.preserveSpace;
      this._printer.printText('<');
      if (this._xhtml) {
        this._printer.printText(paramString.toLowerCase(Locale.ENGLISH));
      } else {
        this._printer.printText(paramString);
      }
      this._printer.indent();
      if (paramAttributeList != null) {
        for (int i = 0; i < paramAttributeList.getLength(); i++)
        {
          this._printer.printSpace();
          String str1 = paramAttributeList.getName(i).toLowerCase(Locale.ENGLISH);
          String str2 = paramAttributeList.getValue(i);
          if (this._xhtml)
          {
            if (str2 == null)
            {
              this._printer.printText(str1);
              this._printer.printText("=\"\"");
            }
            else
            {
              this._printer.printText(str1);
              this._printer.printText("=\"");
              printEscaped(str2);
              this._printer.printText('"');
            }
          }
          else
          {
            if (str2 == null) {
              str2 = "";
            }
            if ((!this._format.getPreserveEmptyAttributes()) && (str2.length() == 0))
            {
              this._printer.printText(str1);
            }
            else if (HTMLdtd.isURI(paramString, str1))
            {
              this._printer.printText(str1);
              this._printer.printText("=\"");
              this._printer.printText(escapeURI(str2));
              this._printer.printText('"');
            }
            else if (HTMLdtd.isBoolean(paramString, str1))
            {
              this._printer.printText(str1);
            }
            else
            {
              this._printer.printText(str1);
              this._printer.printText("=\"");
              printEscaped(str2);
              this._printer.printText('"');
            }
          }
        }
      }
      if (HTMLdtd.isPreserveSpace(paramString)) {
        bool = true;
      }
      localElementState = enterElementState(null, null, paramString, bool);
      if ((paramString.equalsIgnoreCase("A")) || (paramString.equalsIgnoreCase("TD")))
      {
        localElementState.empty = false;
        this._printer.printText('>');
      }
      if ((paramString.equalsIgnoreCase("SCRIPT")) || (paramString.equalsIgnoreCase("STYLE"))) {
        if (this._xhtml) {
          localElementState.doCData = true;
        } else {
          localElementState.unescaped = true;
        }
      }
    }
    catch (IOException localIOException)
    {
      throw new SAXException(localIOException);
    }
  }
  
  public void endElement(String paramString)
    throws SAXException
  {
    endElement(null, null, paramString);
  }
  
  protected void startDocument(String paramString)
    throws IOException
  {
    this._printer.leaveDTD();
    if (!this._started)
    {
      if ((this._docTypePublicId == null) && (this._docTypeSystemId == null)) {
        if (this._xhtml)
        {
          this._docTypePublicId = "-//W3C//DTD XHTML 1.0 Strict//EN";
          this._docTypeSystemId = "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd";
        }
        else
        {
          this._docTypePublicId = "-//W3C//DTD HTML 4.01//EN";
          this._docTypeSystemId = "http://www.w3.org/TR/html4/strict.dtd";
        }
      }
      if (!this._format.getOmitDocumentType()) {
        if ((this._docTypePublicId != null) && ((!this._xhtml) || (this._docTypeSystemId != null)))
        {
          if (this._xhtml) {
            this._printer.printText("<!DOCTYPE html PUBLIC ");
          } else {
            this._printer.printText("<!DOCTYPE HTML PUBLIC ");
          }
          printDoctypeURL(this._docTypePublicId);
          if (this._docTypeSystemId != null)
          {
            if (this._indenting)
            {
              this._printer.breakLine();
              this._printer.printText("                      ");
            }
            else
            {
              this._printer.printText(' ');
            }
            printDoctypeURL(this._docTypeSystemId);
          }
          this._printer.printText('>');
          this._printer.breakLine();
        }
        else if (this._docTypeSystemId != null)
        {
          if (this._xhtml) {
            this._printer.printText("<!DOCTYPE html SYSTEM ");
          } else {
            this._printer.printText("<!DOCTYPE HTML SYSTEM ");
          }
          printDoctypeURL(this._docTypeSystemId);
          this._printer.printText('>');
          this._printer.breakLine();
        }
      }
    }
    this._started = true;
    serializePreRoot();
  }
  
  protected void serializeElement(Element paramElement)
    throws IOException
  {
    String str3 = paramElement.getTagName();
    ElementState localElementState = getElementState();
    if (isDocumentState())
    {
      if (!this._started) {
        startDocument(str3);
      }
    }
    else
    {
      if (localElementState.empty) {
        this._printer.printText('>');
      }
      if ((this._indenting) && (!localElementState.preserveSpace) && ((localElementState.empty) || (localElementState.afterElement))) {
        this._printer.breakLine();
      }
    }
    boolean bool = localElementState.preserveSpace;
    this._printer.printText('<');
    if (this._xhtml) {
      this._printer.printText(str3.toLowerCase(Locale.ENGLISH));
    } else {
      this._printer.printText(str3);
    }
    this._printer.indent();
    NamedNodeMap localNamedNodeMap = paramElement.getAttributes();
    if (localNamedNodeMap != null) {
      for (int i = 0; i < localNamedNodeMap.getLength(); i++)
      {
        Attr localAttr = (Attr)localNamedNodeMap.item(i);
        String str1 = localAttr.getName().toLowerCase(Locale.ENGLISH);
        String str2 = localAttr.getValue();
        if (localAttr.getSpecified())
        {
          this._printer.printSpace();
          if (this._xhtml)
          {
            if (str2 == null)
            {
              this._printer.printText(str1);
              this._printer.printText("=\"\"");
            }
            else
            {
              this._printer.printText(str1);
              this._printer.printText("=\"");
              printEscaped(str2);
              this._printer.printText('"');
            }
          }
          else
          {
            if (str2 == null) {
              str2 = "";
            }
            if ((!this._format.getPreserveEmptyAttributes()) && (str2.length() == 0))
            {
              this._printer.printText(str1);
            }
            else if (HTMLdtd.isURI(str3, str1))
            {
              this._printer.printText(str1);
              this._printer.printText("=\"");
              this._printer.printText(escapeURI(str2));
              this._printer.printText('"');
            }
            else if (HTMLdtd.isBoolean(str3, str1))
            {
              this._printer.printText(str1);
            }
            else
            {
              this._printer.printText(str1);
              this._printer.printText("=\"");
              printEscaped(str2);
              this._printer.printText('"');
            }
          }
        }
      }
    }
    if (HTMLdtd.isPreserveSpace(str3)) {
      bool = true;
    }
    if ((paramElement.hasChildNodes()) || (!HTMLdtd.isEmptyTag(str3)))
    {
      localElementState = enterElementState(null, null, str3, bool);
      if ((str3.equalsIgnoreCase("A")) || (str3.equalsIgnoreCase("TD")))
      {
        localElementState.empty = false;
        this._printer.printText('>');
      }
      if ((str3.equalsIgnoreCase("SCRIPT")) || (str3.equalsIgnoreCase("STYLE"))) {
        if (this._xhtml) {
          localElementState.doCData = true;
        } else {
          localElementState.unescaped = true;
        }
      }
      for (Node localNode = paramElement.getFirstChild(); localNode != null; localNode = localNode.getNextSibling()) {
        serializeNode(localNode);
      }
      endElementIO(null, null, str3);
    }
    else
    {
      this._printer.unindent();
      if (this._xhtml) {
        this._printer.printText(" />");
      } else {
        this._printer.printText('>');
      }
      localElementState.afterElement = true;
      localElementState.empty = false;
      if (isDocumentState()) {
        this._printer.flush();
      }
    }
  }
  
  protected void characters(String paramString)
    throws IOException
  {
    content();
    super.characters(paramString);
  }
  
  protected String getEntityRef(int paramInt)
  {
    return HTMLdtd.fromChar(paramInt);
  }
  
  protected String escapeURI(String paramString)
  {
    int i = paramString.indexOf("\"");
    if (i >= 0) {
      return paramString.substring(0, i);
    }
    return paramString;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serialize.HTMLSerializer
 * JD-Core Version:    0.7.0.1
 */