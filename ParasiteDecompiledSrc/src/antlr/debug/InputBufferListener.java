package antlr.debug;

public abstract interface InputBufferListener
  extends ListenerBase
{
  public abstract void inputBufferConsume(InputBufferEvent paramInputBufferEvent);
  
  public abstract void inputBufferLA(InputBufferEvent paramInputBufferEvent);
  
  public abstract void inputBufferMark(InputBufferEvent paramInputBufferEvent);
  
  public abstract void inputBufferRewind(InputBufferEvent paramInputBufferEvent);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.debug.InputBufferListener
 * JD-Core Version:    0.7.0.1
 */