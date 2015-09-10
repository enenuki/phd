package org.apache.xerces.xni.parser;

public abstract interface XMLComponentManager
{
  public abstract boolean getFeature(String paramString)
    throws XMLConfigurationException;
  
  public abstract Object getProperty(String paramString)
    throws XMLConfigurationException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.xni.parser.XMLComponentManager
 * JD-Core Version:    0.7.0.1
 */