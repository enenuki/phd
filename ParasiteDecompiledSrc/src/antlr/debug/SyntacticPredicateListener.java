package antlr.debug;

public abstract interface SyntacticPredicateListener
  extends ListenerBase
{
  public abstract void syntacticPredicateFailed(SyntacticPredicateEvent paramSyntacticPredicateEvent);
  
  public abstract void syntacticPredicateStarted(SyntacticPredicateEvent paramSyntacticPredicateEvent);
  
  public abstract void syntacticPredicateSucceeded(SyntacticPredicateEvent paramSyntacticPredicateEvent);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.debug.SyntacticPredicateListener
 * JD-Core Version:    0.7.0.1
 */