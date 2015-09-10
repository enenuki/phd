package javax.persistence.criteria;

import javax.persistence.metamodel.Attribute;

public abstract interface Join<Z, X>
  extends From<Z, X>
{
  public abstract Attribute<? super Z, ?> getAttribute();
  
  public abstract From<?, Z> getParent();
  
  public abstract JoinType getJoinType();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.criteria.Join
 * JD-Core Version:    0.7.0.1
 */