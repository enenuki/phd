package org.hibernate.param;

public abstract interface ExplicitParameterSpecification
  extends ParameterSpecification
{
  public abstract int getSourceLine();
  
  public abstract int getSourceColumn();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.param.ExplicitParameterSpecification
 * JD-Core Version:    0.7.0.1
 */