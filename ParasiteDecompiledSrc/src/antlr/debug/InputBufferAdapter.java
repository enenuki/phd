package antlr.debug;

public abstract class InputBufferAdapter
  implements InputBufferListener
{
  public void doneParsing(TraceEvent paramTraceEvent) {}
  
  public void inputBufferConsume(InputBufferEvent paramInputBufferEvent) {}
  
  public void inputBufferLA(InputBufferEvent paramInputBufferEvent) {}
  
  public void inputBufferMark(InputBufferEvent paramInputBufferEvent) {}
  
  public void inputBufferRewind(InputBufferEvent paramInputBufferEvent) {}
  
  public void refresh() {}
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.debug.InputBufferAdapter
 * JD-Core Version:    0.7.0.1
 */