package org.apache.xerces.xni.parser;

import java.io.IOException;
import org.apache.xerces.xni.XNIException;

public abstract interface XMLDocumentScanner
  extends XMLDocumentSource
{
  public abstract void setInputSource(XMLInputSource paramXMLInputSource)
    throws IOException;
  
  public abstract boolean scanDocument(boolean paramBoolean)
    throws IOException, XNIException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.xni.parser.XMLDocumentScanner
 * JD-Core Version:    0.7.0.1
 */