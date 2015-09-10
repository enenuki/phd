package org.apache.xml.serializer;

import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;

public abstract interface ExtendedLexicalHandler
  extends LexicalHandler
{
  public abstract void comment(String paramString)
    throws SAXException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.serializer.ExtendedLexicalHandler
 * JD-Core Version:    0.7.0.1
 */