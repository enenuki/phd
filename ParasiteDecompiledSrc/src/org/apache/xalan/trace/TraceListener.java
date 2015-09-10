package org.apache.xalan.trace;

import java.util.EventListener;
import javax.xml.transform.TransformerException;

public abstract interface TraceListener
  extends EventListener
{
  public abstract void trace(TracerEvent paramTracerEvent);
  
  public abstract void selected(SelectionEvent paramSelectionEvent)
    throws TransformerException;
  
  public abstract void generated(GenerateEvent paramGenerateEvent);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xalan.trace.TraceListener
 * JD-Core Version:    0.7.0.1
 */