package antlr.debug;

public abstract interface TraceListener
  extends ListenerBase
{
  public abstract void enterRule(TraceEvent paramTraceEvent);
  
  public abstract void exitRule(TraceEvent paramTraceEvent);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.debug.TraceListener
 * JD-Core Version:    0.7.0.1
 */