package antlr.debug;

import java.util.EventListener;

public abstract interface ListenerBase
  extends EventListener
{
  public abstract void doneParsing(TraceEvent paramTraceEvent);
  
  public abstract void refresh();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.debug.ListenerBase
 * JD-Core Version:    0.7.0.1
 */