package antlr.ASdebug;

import antlr.Token;

public abstract interface IASDebugStream
{
  public abstract String getEntireText();
  
  public abstract TokenOffsetInfo getOffsetInfo(Token paramToken);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.ASdebug.IASDebugStream
 * JD-Core Version:    0.7.0.1
 */