package org.hibernate.mapping;

public abstract interface ValueVisitor
{
  public abstract Object accept(Bag paramBag);
  
  public abstract Object accept(IdentifierBag paramIdentifierBag);
  
  public abstract Object accept(List paramList);
  
  public abstract Object accept(PrimitiveArray paramPrimitiveArray);
  
  public abstract Object accept(Array paramArray);
  
  public abstract Object accept(Map paramMap);
  
  public abstract Object accept(OneToMany paramOneToMany);
  
  public abstract Object accept(Set paramSet);
  
  public abstract Object accept(Any paramAny);
  
  public abstract Object accept(SimpleValue paramSimpleValue);
  
  public abstract Object accept(DependantValue paramDependantValue);
  
  public abstract Object accept(Component paramComponent);
  
  public abstract Object accept(ManyToOne paramManyToOne);
  
  public abstract Object accept(OneToOne paramOneToOne);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.hibernate.mapping.ValueVisitor
 * JD-Core Version:    0.7.0.1
 */