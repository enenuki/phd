package org.apache.xml.dtm.ref;

import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;

public abstract interface IncrementalSAXSource
{
  public abstract void setContentHandler(ContentHandler paramContentHandler);
  
  public abstract void setLexicalHandler(LexicalHandler paramLexicalHandler);
  
  public abstract void setDTDHandler(DTDHandler paramDTDHandler);
  
  public abstract Object deliverMoreNodes(boolean paramBoolean);
  
  public abstract void startParse(InputSource paramInputSource)
    throws SAXException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xml.dtm.ref.IncrementalSAXSource
 * JD-Core Version:    0.7.0.1
 */