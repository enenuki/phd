package org.w3c.css.sac;

public abstract interface AttributeCondition
  extends Condition
{
  public abstract String getNamespaceURI();
  
  public abstract String getLocalName();
  
  public abstract boolean getSpecified();
  
  public abstract String getValue();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.w3c.css.sac.AttributeCondition
 * JD-Core Version:    0.7.0.1
 */