package org.apache.xml.serializer;

import java.io.IOException;
import org.w3c.dom.Node;

public abstract interface DOMSerializer
{
  public abstract void serialize(Node paramNode)
    throws IOException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.DOMSerializer
 * JD-Core Version:    0.7.0.1
 */