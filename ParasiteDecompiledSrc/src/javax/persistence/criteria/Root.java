package javax.persistence.criteria;

import javax.persistence.metamodel.EntityType;

public abstract interface Root<X>
  extends From<X, X>
{
  public abstract EntityType<X> getModel();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.criteria.Root
 * JD-Core Version:    0.7.0.1
 */