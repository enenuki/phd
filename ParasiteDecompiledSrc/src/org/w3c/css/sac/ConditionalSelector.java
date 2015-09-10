package org.w3c.css.sac;

public abstract interface ConditionalSelector
  extends SimpleSelector
{
  public abstract SimpleSelector getSimpleSelector();
  
  public abstract Condition getCondition();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.w3c.css.sac.ConditionalSelector
 * JD-Core Version:    0.7.0.1
 */