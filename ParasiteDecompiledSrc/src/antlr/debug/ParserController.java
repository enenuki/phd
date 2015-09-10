package antlr.debug;

public abstract interface ParserController
  extends ParserListener
{
  public abstract void checkBreak();
  
  public abstract void setParserEventSupport(ParserEventSupport paramParserEventSupport);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.debug.ParserController
 * JD-Core Version:    0.7.0.1
 */