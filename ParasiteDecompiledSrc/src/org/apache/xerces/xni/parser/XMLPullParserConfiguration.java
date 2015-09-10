package org.apache.xerces.xni.parser;

import java.io.IOException;
import org.apache.xerces.xni.XNIException;

public abstract interface XMLPullParserConfiguration
  extends XMLParserConfiguration
{
  public abstract void setInputSource(XMLInputSource paramXMLInputSource)
    throws XMLConfigurationException, IOException;
  
  public abstract boolean parse(boolean paramBoolean)
    throws XNIException, IOException;
  
  public abstract void cleanup();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.xni.parser.XMLPullParserConfiguration
 * JD-Core Version:    0.7.0.1
 */