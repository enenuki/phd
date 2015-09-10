package org.apache.xml.serializer;

import java.io.IOException;
import org.w3c.dom.DOMErrorHandler;
import org.w3c.dom.Node;
import org.w3c.dom.ls.LSSerializerFilter;

public abstract interface DOM3Serializer
{
  public abstract void serializeDOM3(Node paramNode)
    throws IOException;
  
  public abstract void setErrorHandler(DOMErrorHandler paramDOMErrorHandler);
  
  public abstract DOMErrorHandler getErrorHandler();
  
  public abstract void setNodeFilter(LSSerializerFilter paramLSSerializerFilter);
  
  public abstract LSSerializerFilter getNodeFilter();
  
  public abstract void setNewLine(char[] paramArrayOfChar);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.DOM3Serializer
 * JD-Core Version:    0.7.0.1
 */