package antlr.debug;

public abstract interface ParserMatchListener
  extends ListenerBase
{
  public abstract void parserMatch(ParserMatchEvent paramParserMatchEvent);
  
  public abstract void parserMatchNot(ParserMatchEvent paramParserMatchEvent);
  
  public abstract void parserMismatch(ParserMatchEvent paramParserMatchEvent);
  
  public abstract void parserMismatchNot(ParserMatchEvent paramParserMatchEvent);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.debug.ParserMatchListener
 * JD-Core Version:    0.7.0.1
 */