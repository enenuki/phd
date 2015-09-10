package antlr.debug;

public class ParserAdapter
  implements ParserListener
{
  public void doneParsing(TraceEvent paramTraceEvent) {}
  
  public void enterRule(TraceEvent paramTraceEvent) {}
  
  public void exitRule(TraceEvent paramTraceEvent) {}
  
  public void parserConsume(ParserTokenEvent paramParserTokenEvent) {}
  
  public void parserLA(ParserTokenEvent paramParserTokenEvent) {}
  
  public void parserMatch(ParserMatchEvent paramParserMatchEvent) {}
  
  public void parserMatchNot(ParserMatchEvent paramParserMatchEvent) {}
  
  public void parserMismatch(ParserMatchEvent paramParserMatchEvent) {}
  
  public void parserMismatchNot(ParserMatchEvent paramParserMatchEvent) {}
  
  public void refresh() {}
  
  public void reportError(MessageEvent paramMessageEvent) {}
  
  public void reportWarning(MessageEvent paramMessageEvent) {}
  
  public void semanticPredicateEvaluated(SemanticPredicateEvent paramSemanticPredicateEvent) {}
  
  public void syntacticPredicateFailed(SyntacticPredicateEvent paramSyntacticPredicateEvent) {}
  
  public void syntacticPredicateStarted(SyntacticPredicateEvent paramSyntacticPredicateEvent) {}
  
  public void syntacticPredicateSucceeded(SyntacticPredicateEvent paramSyntacticPredicateEvent) {}
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.debug.ParserAdapter
 * JD-Core Version:    0.7.0.1
 */