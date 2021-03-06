package org.apache.xerces.xinclude;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map.Entry;
import org.apache.xerces.impl.XMLEntityManager;
import org.apache.xerces.impl.XMLErrorReporter;
import org.apache.xerces.impl.io.ASCIIReader;
import org.apache.xerces.impl.io.Latin1Reader;
import org.apache.xerces.impl.io.UTF8Reader;
import org.apache.xerces.util.EncodingMap;
import org.apache.xerces.util.HTTPInputSource;
import org.apache.xerces.util.MessageFormatter;
import org.apache.xerces.util.XMLChar;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.parser.XMLInputSource;

public class XIncludeTextReader
{
  private Reader fReader;
  private final XIncludeHandler fHandler;
  private XMLInputSource fSource;
  private XMLErrorReporter fErrorReporter;
  private XMLString fTempString = new XMLString();
  
  public XIncludeTextReader(XMLInputSource paramXMLInputSource, XIncludeHandler paramXIncludeHandler, int paramInt)
    throws IOException
  {
    this.fHandler = paramXIncludeHandler;
    this.fSource = paramXMLInputSource;
    this.fTempString = new XMLString(new char[paramInt + 1], 0, 0);
  }
  
  public void setErrorReporter(XMLErrorReporter paramXMLErrorReporter)
  {
    this.fErrorReporter = paramXMLErrorReporter;
  }
  
  protected Reader getReader(XMLInputSource paramXMLInputSource)
    throws IOException
  {
    if (paramXMLInputSource.getCharacterStream() != null) {
      return paramXMLInputSource.getCharacterStream();
    }
    Object localObject1 = null;
    Object localObject2 = paramXMLInputSource.getEncoding();
    if (localObject2 == null) {
      localObject2 = "UTF-8";
    }
    Object localObject3;
    Object localObject4;
    if (paramXMLInputSource.getByteStream() != null)
    {
      localObject1 = paramXMLInputSource.getByteStream();
      if (!(localObject1 instanceof BufferedInputStream)) {
        localObject1 = new BufferedInputStream((InputStream)localObject1, this.fTempString.ch.length);
      }
    }
    else
    {
      str1 = XMLEntityManager.expandSystemId(paramXMLInputSource.getSystemId(), paramXMLInputSource.getBaseSystemId(), false);
      localObject3 = new URL(str1);
      localObject4 = ((URL)localObject3).openConnection();
      if (((localObject4 instanceof HttpURLConnection)) && ((paramXMLInputSource instanceof HTTPInputSource)))
      {
        localObject5 = (HttpURLConnection)localObject4;
        HTTPInputSource localHTTPInputSource = (HTTPInputSource)paramXMLInputSource;
        localObject6 = localHTTPInputSource.getHTTPRequestProperties();
        while (((Iterator)localObject6).hasNext())
        {
          Map.Entry localEntry = (Map.Entry)((Iterator)localObject6).next();
          ((URLConnection)localObject5).setRequestProperty((String)localEntry.getKey(), (String)localEntry.getValue());
        }
        boolean bool = localHTTPInputSource.getFollowHTTPRedirects();
        if (!bool) {
          XMLEntityManager.setInstanceFollowRedirects((HttpURLConnection)localObject5, bool);
        }
      }
      localObject1 = new BufferedInputStream(((URLConnection)localObject4).getInputStream());
      Object localObject5 = ((URLConnection)localObject4).getContentType();
      int i = localObject5 != null ? ((String)localObject5).indexOf(';') : -1;
      Object localObject6 = null;
      String str2 = null;
      if (i != -1)
      {
        localObject6 = ((String)localObject5).substring(0, i).trim();
        str2 = ((String)localObject5).substring(i + 1).trim();
        if (str2.startsWith("charset="))
        {
          str2 = str2.substring(8).trim();
          if (((str2.charAt(0) == '"') && (str2.charAt(str2.length() - 1) == '"')) || ((str2.charAt(0) == '\'') && (str2.charAt(str2.length() - 1) == '\''))) {
            str2 = str2.substring(1, str2.length() - 1);
          }
        }
        else
        {
          str2 = null;
        }
      }
      else
      {
        localObject6 = ((String)localObject5).trim();
      }
      String str3 = null;
      if (((String)localObject6).equals("text/xml"))
      {
        if (str2 != null) {
          str3 = str2;
        } else {
          str3 = "US-ASCII";
        }
      }
      else if (((String)localObject6).equals("application/xml"))
      {
        if (str2 != null) {
          str3 = str2;
        } else {
          str3 = getEncodingName((InputStream)localObject1);
        }
      }
      else if (((String)localObject6).endsWith("+xml")) {
        str3 = getEncodingName((InputStream)localObject1);
      }
      if (str3 != null) {
        localObject2 = str3;
      }
    }
    localObject2 = ((String)localObject2).toUpperCase(Locale.ENGLISH);
    localObject2 = consumeBOM((InputStream)localObject1, (String)localObject2);
    if (((String)localObject2).equals("UTF-8")) {
      return new UTF8Reader((InputStream)localObject1, this.fTempString.ch.length, this.fErrorReporter.getMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210"), this.fErrorReporter.getLocale());
    }
    String str1 = EncodingMap.getIANA2JavaMapping((String)localObject2);
    if (str1 == null)
    {
      localObject3 = this.fErrorReporter.getMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210");
      localObject4 = this.fErrorReporter.getLocale();
      throw new IOException(((MessageFormatter)localObject3).formatMessage((Locale)localObject4, "EncodingDeclInvalid", new Object[] { localObject2 }));
    }
    if (str1.equals("ASCII")) {
      return new ASCIIReader((InputStream)localObject1, this.fTempString.ch.length, this.fErrorReporter.getMessageFormatter("http://www.w3.org/TR/1998/REC-xml-19980210"), this.fErrorReporter.getLocale());
    }
    if (str1.equals("ISO8859_1")) {
      return new Latin1Reader((InputStream)localObject1, this.fTempString.ch.length);
    }
    return new InputStreamReader((InputStream)localObject1, str1);
  }
  
  protected String getEncodingName(InputStream paramInputStream)
    throws IOException
  {
    byte[] arrayOfByte = new byte[4];
    String str = null;
    paramInputStream.mark(4);
    int i = paramInputStream.read(arrayOfByte, 0, 4);
    paramInputStream.reset();
    if (i == 4) {
      str = getEncodingName(arrayOfByte);
    }
    return str;
  }
  
  protected String consumeBOM(InputStream paramInputStream, String paramString)
    throws IOException
  {
    byte[] arrayOfByte = new byte[3];
    int i = 0;
    paramInputStream.mark(3);
    int j;
    int k;
    if (paramString.equals("UTF-8"))
    {
      i = paramInputStream.read(arrayOfByte, 0, 3);
      if (i == 3)
      {
        j = arrayOfByte[0] & 0xFF;
        k = arrayOfByte[1] & 0xFF;
        int m = arrayOfByte[2] & 0xFF;
        if ((j != 239) || (k != 187) || (m != 191)) {
          paramInputStream.reset();
        }
      }
      else
      {
        paramInputStream.reset();
      }
    }
    else if (paramString.startsWith("UTF-16"))
    {
      i = paramInputStream.read(arrayOfByte, 0, 2);
      if (i == 2)
      {
        j = arrayOfByte[0] & 0xFF;
        k = arrayOfByte[1] & 0xFF;
        if ((j == 254) && (k == 255)) {
          return "UTF-16BE";
        }
        if ((j == 255) && (k == 254)) {
          return "UTF-16LE";
        }
      }
      paramInputStream.reset();
    }
    return paramString;
  }
  
  protected String getEncodingName(byte[] paramArrayOfByte)
  {
    int i = paramArrayOfByte[0] & 0xFF;
    int j = paramArrayOfByte[1] & 0xFF;
    if ((i == 254) && (j == 255)) {
      return "UTF-16BE";
    }
    if ((i == 255) && (j == 254)) {
      return "UTF-16LE";
    }
    int k = paramArrayOfByte[2] & 0xFF;
    if ((i == 239) && (j == 187) && (k == 191)) {
      return "UTF-8";
    }
    int m = paramArrayOfByte[3] & 0xFF;
    if ((i == 0) && (j == 0) && (k == 0) && (m == 60)) {
      return "ISO-10646-UCS-4";
    }
    if ((i == 60) && (j == 0) && (k == 0) && (m == 0)) {
      return "ISO-10646-UCS-4";
    }
    if ((i == 0) && (j == 0) && (k == 60) && (m == 0)) {
      return "ISO-10646-UCS-4";
    }
    if ((i == 0) && (j == 60) && (k == 0) && (m == 0)) {
      return "ISO-10646-UCS-4";
    }
    if ((i == 0) && (j == 60) && (k == 0) && (m == 63)) {
      return "UTF-16BE";
    }
    if ((i == 60) && (j == 0) && (k == 63) && (m == 0)) {
      return "UTF-16LE";
    }
    if ((i == 76) && (j == 111) && (k == 167) && (m == 148)) {
      return "CP037";
    }
    return null;
  }
  
  public void parse()
    throws IOException
  {
    this.fReader = getReader(this.fSource);
    this.fSource = null;
    int i = this.fReader.read(this.fTempString.ch, 0, this.fTempString.ch.length - 1);
    this.fHandler.fHasIncludeReportedContent = true;
    while (i != -1)
    {
      for (int j = 0; j < i; j++)
      {
        char c = this.fTempString.ch[j];
        if (!isValid(c)) {
          if (XMLChar.isHighSurrogate(c))
          {
            j++;
            int k;
            if (j < i)
            {
              k = this.fTempString.ch[j];
            }
            else
            {
              k = this.fReader.read();
              if (k != -1) {
                this.fTempString.ch[(i++)] = ((char)k);
              }
            }
            if (XMLChar.isLowSurrogate(k))
            {
              int m = XMLChar.supplemental(c, (char)k);
              if (!isValid(m)) {
                this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "InvalidCharInContent", new Object[] { Integer.toString(m, 16) }, (short)2);
              }
            }
            else
            {
              this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "InvalidCharInContent", new Object[] { Integer.toString(k, 16) }, (short)2);
            }
          }
          else
          {
            this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "InvalidCharInContent", new Object[] { Integer.toString(c, 16) }, (short)2);
          }
        }
      }
      if ((this.fHandler != null) && (i > 0))
      {
        this.fTempString.offset = 0;
        this.fTempString.length = i;
        this.fHandler.characters(this.fTempString, this.fHandler.modifyAugmentations(null, true));
      }
      i = this.fReader.read(this.fTempString.ch, 0, this.fTempString.ch.length - 1);
    }
  }
  
  public void setInputSource(XMLInputSource paramXMLInputSource)
  {
    this.fSource = paramXMLInputSource;
  }
  
  public void close()
    throws IOException
  {
    if (this.fReader != null)
    {
      this.fReader.close();
      this.fReader = null;
    }
  }
  
  protected boolean isValid(int paramInt)
  {
    return XMLChar.isValid(paramInt);
  }
  
  protected void setBufferSize(int paramInt)
  {
    if (this.fTempString.ch.length != ++paramInt) {
      this.fTempString.ch = new char[paramInt];
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.xinclude.XIncludeTextReader
 * JD-Core Version:    0.7.0.1
 */