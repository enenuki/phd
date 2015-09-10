package javax.persistence.criteria;

import java.util.List;
import javax.persistence.metamodel.ListAttribute;

public abstract interface ListJoin<Z, E>
  extends PluralJoin<Z, List<E>, E>
{
  public abstract ListAttribute<? super Z, E> getModel();
  
  public abstract Expression<Integer> index();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.criteria.ListJoin
 * JD-Core Version:    0.7.0.1
 */