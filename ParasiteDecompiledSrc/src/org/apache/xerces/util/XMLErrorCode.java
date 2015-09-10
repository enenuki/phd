package org.apache.xerces.util;

final class XMLErrorCode
{
  private String fDomain;
  private String fKey;
  
  public XMLErrorCode(String paramString1, String paramString2)
  {
    this.fDomain = paramString1;
    this.fKey = paramString2;
  }
  
  public void setValues(String paramString1, String paramString2)
  {
    this.fDomain = paramString1;
    this.fKey = paramString2;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof XMLErrorCode)) {
      return false;
    }
    XMLErrorCode localXMLErrorCode = (XMLErrorCode)paramObject;
    return (this.fDomain.equals(localXMLErrorCode.fDomain)) && (this.fKey.equals(localXMLErrorCode.fKey));
  }
  
  public int hashCode()
  {
    return this.fDomain.hashCode() + this.fKey.hashCode();
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.util.XMLErrorCode
 * JD-Core Version:    0.7.0.1
 */