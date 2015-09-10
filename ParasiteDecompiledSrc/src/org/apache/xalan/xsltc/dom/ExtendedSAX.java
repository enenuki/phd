package org.apache.xalan.xsltc.dom;

import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.ext.DeclHandler;
import org.xml.sax.ext.LexicalHandler;

public abstract interface ExtendedSAX
  extends ContentHandler, LexicalHandler, DTDHandler, DeclHandler
{}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.xsltc.dom.ExtendedSAX
 * JD-Core Version:    0.7.0.1
 */