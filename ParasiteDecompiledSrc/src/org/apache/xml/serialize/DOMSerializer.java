package org.apache.xml.serialize;

import java.io.IOException;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;

/**
 * @deprecated
 */
public abstract interface DOMSerializer
{
  public abstract void serialize(Element paramElement)
    throws IOException;
  
  public abstract void serialize(Document paramDocument)
    throws IOException;
  
  public abstract void serialize(DocumentFragment paramDocumentFragment)
    throws IOException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serialize.DOMSerializer
 * JD-Core Version:    0.7.0.1
 */