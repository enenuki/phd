package antlr;

public abstract interface CharFormatter
{
  public abstract String escapeChar(int paramInt, boolean paramBoolean);
  
  public abstract String escapeString(String paramString);
  
  public abstract String literalChar(int paramInt);
  
  public abstract String literalString(String paramString);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.CharFormatter
 * JD-Core Version:    0.7.0.1
 */