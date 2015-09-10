package javax.persistence.criteria;

import java.util.Collection;
import javax.persistence.metamodel.CollectionAttribute;

public abstract interface CollectionJoin<Z, E>
  extends PluralJoin<Z, Collection<E>, E>
{
  public abstract CollectionAttribute<? super Z, E> getModel();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.criteria.CollectionJoin
 * JD-Core Version:    0.7.0.1
 */