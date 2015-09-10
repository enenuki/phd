package org.w3c.css.sac;

public abstract interface CombinatorCondition
  extends Condition
{
  public abstract Condition getFirstCondition();
  
  public abstract Condition getSecondCondition();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.w3c.css.sac.CombinatorCondition
 * JD-Core Version:    0.7.0.1
 */