package org.apache.xalan.trace;

import javax.xml.transform.TransformerException;

public abstract interface TraceListenerEx
  extends TraceListener
{
  public abstract void selectEnd(EndSelectionEvent paramEndSelectionEvent)
    throws TransformerException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.trace.TraceListenerEx
 * JD-Core Version:    0.7.0.1
 */