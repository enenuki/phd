package org.apache.xerces.util;

import java.io.InputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.xerces.xni.XMLResourceIdentifier;
import org.apache.xerces.xni.parser.XMLInputSource;

public final class HTTPInputSource
  extends XMLInputSource
{
  protected boolean fFollowRedirects = true;
  protected Map fHTTPRequestProperties = new HashMap();
  
  public HTTPInputSource(String paramString1, String paramString2, String paramString3)
  {
    super(paramString1, paramString2, paramString3);
  }
  
  public HTTPInputSource(XMLResourceIdentifier paramXMLResourceIdentifier)
  {
    super(paramXMLResourceIdentifier);
  }
  
  public HTTPInputSource(String paramString1, String paramString2, String paramString3, InputStream paramInputStream, String paramString4)
  {
    super(paramString1, paramString2, paramString3, paramInputStream, paramString4);
  }
  
  public HTTPInputSource(String paramString1, String paramString2, String paramString3, Reader paramReader, String paramString4)
  {
    super(paramString1, paramString2, paramString3, paramReader, paramString4);
  }
  
  public boolean getFollowHTTPRedirects()
  {
    return this.fFollowRedirects;
  }
  
  public void setFollowHTTPRedirects(boolean paramBoolean)
  {
    this.fFollowRedirects = paramBoolean;
  }
  
  public String getHTTPRequestProperty(String paramString)
  {
    return (String)this.fHTTPRequestProperties.get(paramString);
  }
  
  public Iterator getHTTPRequestProperties()
  {
    return this.fHTTPRequestProperties.entrySet().iterator();
  }
  
  public void setHTTPRequestProperty(String paramString1, String paramString2)
  {
    if (paramString2 != null) {
      this.fHTTPRequestProperties.put(paramString1, paramString2);
    } else {
      this.fHTTPRequestProperties.remove(paramString1);
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.util.HTTPInputSource
 * JD-Core Version:    0.7.0.1
 */