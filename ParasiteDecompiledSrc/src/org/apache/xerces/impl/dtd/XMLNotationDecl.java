package org.apache.xerces.impl.dtd;

public class XMLNotationDecl
{
  public String name;
  public String publicId;
  public String systemId;
  public String baseSystemId;
  
  public void setValues(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.name = paramString1;
    this.publicId = paramString2;
    this.systemId = paramString3;
    this.baseSystemId = paramString4;
  }
  
  public void clear()
  {
    this.name = null;
    this.publicId = null;
    this.systemId = null;
    this.baseSystemId = null;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.dtd.XMLNotationDecl
 * JD-Core Version:    0.7.0.1
 */