package org.apache.xerces.util;

import org.apache.xerces.xni.XMLLocator;
import org.xml.sax.Locator;
import org.xml.sax.ext.Locator2;

public final class SAXLocatorWrapper
  implements XMLLocator
{
  private Locator fLocator = null;
  private Locator2 fLocator2 = null;
  
  public void setLocator(Locator paramLocator)
  {
    this.fLocator = paramLocator;
    if (((paramLocator instanceof Locator2)) || (paramLocator == null)) {
      this.fLocator2 = ((Locator2)paramLocator);
    }
  }
  
  public Locator getLocator()
  {
    return this.fLocator;
  }
  
  public String getPublicId()
  {
    if (this.fLocator != null) {
      return this.fLocator.getPublicId();
    }
    return null;
  }
  
  public String getLiteralSystemId()
  {
    if (this.fLocator != null) {
      return this.fLocator.getSystemId();
    }
    return null;
  }
  
  public String getBaseSystemId()
  {
    return null;
  }
  
  public String getExpandedSystemId()
  {
    return getLiteralSystemId();
  }
  
  public int getLineNumber()
  {
    if (this.fLocator != null) {
      return this.fLocator.getLineNumber();
    }
    return -1;
  }
  
  public int getColumnNumber()
  {
    if (this.fLocator != null) {
      return this.fLocator.getColumnNumber();
    }
    return -1;
  }
  
  public int getCharacterOffset()
  {
    return -1;
  }
  
  public String getEncoding()
  {
    if (this.fLocator2 != null) {
      return this.fLocator2.getEncoding();
    }
    return null;
  }
  
  public String getXMLVersion()
  {
    if (this.fLocator2 != null) {
      return this.fLocator2.getXMLVersion();
    }
    return null;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.util.SAXLocatorWrapper
 * JD-Core Version:    0.7.0.1
 */