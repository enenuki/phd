package antlr;

abstract interface ToolErrorHandler
{
  public abstract void warnAltAmbiguity(Grammar paramGrammar, AlternativeBlock paramAlternativeBlock, boolean paramBoolean, int paramInt1, Lookahead[] paramArrayOfLookahead, int paramInt2, int paramInt3);
  
  public abstract void warnAltExitAmbiguity(Grammar paramGrammar, BlockWithImpliedExitPath paramBlockWithImpliedExitPath, boolean paramBoolean, int paramInt1, Lookahead[] paramArrayOfLookahead, int paramInt2);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.ToolErrorHandler
 * JD-Core Version:    0.7.0.1
 */