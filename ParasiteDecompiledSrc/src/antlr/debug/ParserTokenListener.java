package antlr.debug;

public abstract interface ParserTokenListener
  extends ListenerBase
{
  public abstract void parserConsume(ParserTokenEvent paramParserTokenEvent);
  
  public abstract void parserLA(ParserTokenEvent paramParserTokenEvent);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.debug.ParserTokenListener
 * JD-Core Version:    0.7.0.1
 */