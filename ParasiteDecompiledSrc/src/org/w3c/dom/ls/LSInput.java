package org.w3c.dom.ls;

import java.io.InputStream;
import java.io.Reader;

public abstract interface LSInput
{
  public abstract Reader getCharacterStream();
  
  public abstract void setCharacterStream(Reader paramReader);
  
  public abstract InputStream getByteStream();
  
  public abstract void setByteStream(InputStream paramInputStream);
  
  public abstract String getStringData();
  
  public abstract void setStringData(String paramString);
  
  public abstract String getSystemId();
  
  public abstract void setSystemId(String paramString);
  
  public abstract String getPublicId();
  
  public abstract void setPublicId(String paramString);
  
  public abstract String getBaseURI();
  
  public abstract void setBaseURI(String paramString);
  
  public abstract String getEncoding();
  
  public abstract void setEncoding(String paramString);
  
  public abstract boolean getCertifiedText();
  
  public abstract void setCertifiedText(boolean paramBoolean);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.w3c.dom.ls.LSInput
 * JD-Core Version:    0.7.0.1
 */