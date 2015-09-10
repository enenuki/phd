package antlr;

public abstract interface LLkGrammarAnalyzer
  extends GrammarAnalyzer
{
  public abstract boolean deterministic(AlternativeBlock paramAlternativeBlock);
  
  public abstract boolean deterministic(OneOrMoreBlock paramOneOrMoreBlock);
  
  public abstract boolean deterministic(ZeroOrMoreBlock paramZeroOrMoreBlock);
  
  public abstract Lookahead FOLLOW(int paramInt, RuleEndElement paramRuleEndElement);
  
  public abstract Lookahead look(int paramInt, ActionElement paramActionElement);
  
  public abstract Lookahead look(int paramInt, AlternativeBlock paramAlternativeBlock);
  
  public abstract Lookahead look(int paramInt, BlockEndElement paramBlockEndElement);
  
  public abstract Lookahead look(int paramInt, CharLiteralElement paramCharLiteralElement);
  
  public abstract Lookahead look(int paramInt, CharRangeElement paramCharRangeElement);
  
  public abstract Lookahead look(int paramInt, GrammarAtom paramGrammarAtom);
  
  public abstract Lookahead look(int paramInt, OneOrMoreBlock paramOneOrMoreBlock);
  
  public abstract Lookahead look(int paramInt, RuleBlock paramRuleBlock);
  
  public abstract Lookahead look(int paramInt, RuleEndElement paramRuleEndElement);
  
  public abstract Lookahead look(int paramInt, RuleRefElement paramRuleRefElement);
  
  public abstract Lookahead look(int paramInt, StringLiteralElement paramStringLiteralElement);
  
  public abstract Lookahead look(int paramInt, SynPredBlock paramSynPredBlock);
  
  public abstract Lookahead look(int paramInt, TokenRangeElement paramTokenRangeElement);
  
  public abstract Lookahead look(int paramInt, TreeElement paramTreeElement);
  
  public abstract Lookahead look(int paramInt, WildcardElement paramWildcardElement);
  
  public abstract Lookahead look(int paramInt, ZeroOrMoreBlock paramZeroOrMoreBlock);
  
  public abstract Lookahead look(int paramInt, String paramString);
  
  public abstract void setGrammar(Grammar paramGrammar);
  
  public abstract boolean subruleCanBeInverted(AlternativeBlock paramAlternativeBlock, boolean paramBoolean);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.LLkGrammarAnalyzer
 * JD-Core Version:    0.7.0.1
 */