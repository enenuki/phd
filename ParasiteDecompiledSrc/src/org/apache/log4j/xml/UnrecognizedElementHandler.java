package org.apache.log4j.xml;

import java.util.Properties;
import org.w3c.dom.Element;

public abstract interface UnrecognizedElementHandler
{
  public abstract boolean parseUnrecognizedElement(Element paramElement, Properties paramProperties)
    throws Exception;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.log4j.xml.UnrecognizedElementHandler
 * JD-Core Version:    0.7.0.1
 */