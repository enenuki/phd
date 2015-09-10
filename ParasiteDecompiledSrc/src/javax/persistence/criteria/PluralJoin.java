package javax.persistence.criteria;

import javax.persistence.metamodel.PluralAttribute;

public abstract interface PluralJoin<Z, C, E>
  extends Join<Z, E>
{
  public abstract PluralAttribute<? super Z, C, E> getModel();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.criteria.PluralJoin
 * JD-Core Version:    0.7.0.1
 */