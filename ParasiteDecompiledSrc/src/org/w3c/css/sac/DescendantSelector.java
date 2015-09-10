package org.w3c.css.sac;

public abstract interface DescendantSelector
  extends Selector
{
  public abstract Selector getAncestorSelector();
  
  public abstract SimpleSelector getSimpleSelector();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.w3c.css.sac.DescendantSelector
 * JD-Core Version:    0.7.0.1
 */