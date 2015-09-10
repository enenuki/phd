package javax.persistence.criteria;

import java.util.Set;
import javax.persistence.metamodel.SetAttribute;

public abstract interface SetJoin<Z, E>
  extends PluralJoin<Z, Set<E>, E>
{
  public abstract SetAttribute<? super Z, E> getModel();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.criteria.SetJoin
 * JD-Core Version:    0.7.0.1
 */