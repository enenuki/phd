package org.xml.sax;

public abstract interface XMLFilter
  extends XMLReader
{
  public abstract void setParent(XMLReader paramXMLReader);
  
  public abstract XMLReader getParent();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.xml.sax.XMLFilter
 * JD-Core Version:    0.7.0.1
 */