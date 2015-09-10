package javax.persistence.criteria;

import javax.persistence.metamodel.Attribute;

public abstract interface Fetch<Z, X>
  extends FetchParent<Z, X>
{
  public abstract Attribute<? super Z, ?> getAttribute();
  
  public abstract FetchParent<?, Z> getParent();
  
  public abstract JoinType getJoinType();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.criteria.Fetch
 * JD-Core Version:    0.7.0.1
 */