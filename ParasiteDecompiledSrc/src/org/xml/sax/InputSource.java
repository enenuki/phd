package org.xml.sax;

import java.io.InputStream;
import java.io.Reader;

public class InputSource
{
  private String publicId;
  private String systemId;
  private InputStream byteStream;
  private String encoding;
  private Reader characterStream;
  
  public InputSource() {}
  
  public InputSource(String paramString)
  {
    setSystemId(paramString);
  }
  
  public InputSource(InputStream paramInputStream)
  {
    setByteStream(paramInputStream);
  }
  
  public InputSource(Reader paramReader)
  {
    setCharacterStream(paramReader);
  }
  
  public void setPublicId(String paramString)
  {
    this.publicId = paramString;
  }
  
  public String getPublicId()
  {
    return this.publicId;
  }
  
  public void setSystemId(String paramString)
  {
    this.systemId = paramString;
  }
  
  public String getSystemId()
  {
    return this.systemId;
  }
  
  public void setByteStream(InputStream paramInputStream)
  {
    this.byteStream = paramInputStream;
  }
  
  public InputStream getByteStream()
  {
    return this.byteStream;
  }
  
  public void setEncoding(String paramString)
  {
    this.encoding = paramString;
  }
  
  public String getEncoding()
  {
    return this.encoding;
  }
  
  public void setCharacterStream(Reader paramReader)
  {
    this.characterStream = paramReader;
  }
  
  public Reader getCharacterStream()
  {
    return this.characterStream;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.xml.sax.InputSource
 * JD-Core Version:    0.7.0.1
 */