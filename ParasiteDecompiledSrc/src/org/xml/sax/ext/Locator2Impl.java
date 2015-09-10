package org.xml.sax.ext;

import org.xml.sax.Locator;
import org.xml.sax.helpers.LocatorImpl;

public class Locator2Impl
  extends LocatorImpl
  implements Locator2
{
  private String encoding;
  private String version;
  
  public Locator2Impl() {}
  
  public Locator2Impl(Locator paramLocator)
  {
    super(paramLocator);
    if ((paramLocator instanceof Locator2))
    {
      Locator2 localLocator2 = (Locator2)paramLocator;
      this.version = localLocator2.getXMLVersion();
      this.encoding = localLocator2.getEncoding();
    }
  }
  
  public String getXMLVersion()
  {
    return this.version;
  }
  
  public String getEncoding()
  {
    return this.encoding;
  }
  
  public void setXMLVersion(String paramString)
  {
    this.version = paramString;
  }
  
  public void setEncoding(String paramString)
  {
    this.encoding = paramString;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.xml.sax.ext.Locator2Impl
 * JD-Core Version:    0.7.0.1
 */