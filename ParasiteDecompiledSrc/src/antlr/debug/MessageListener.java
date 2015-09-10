package antlr.debug;

public abstract interface MessageListener
  extends ListenerBase
{
  public abstract void reportError(MessageEvent paramMessageEvent);
  
  public abstract void reportWarning(MessageEvent paramMessageEvent);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.debug.MessageListener
 * JD-Core Version:    0.7.0.1
 */