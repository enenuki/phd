package javax.persistence.criteria;

import java.util.Set;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;

public abstract interface FetchParent<Z, X>
{
  public abstract Set<Fetch<X, ?>> getFetches();
  
  public abstract <Y> Fetch<X, Y> fetch(SingularAttribute<? super X, Y> paramSingularAttribute);
  
  public abstract <Y> Fetch<X, Y> fetch(SingularAttribute<? super X, Y> paramSingularAttribute, JoinType paramJoinType);
  
  public abstract <Y> Fetch<X, Y> fetch(PluralAttribute<? super X, ?, Y> paramPluralAttribute);
  
  public abstract <Y> Fetch<X, Y> fetch(PluralAttribute<? super X, ?, Y> paramPluralAttribute, JoinType paramJoinType);
  
  public abstract <X, Y> Fetch<X, Y> fetch(String paramString);
  
  public abstract <X, Y> Fetch<X, Y> fetch(String paramString, JoinType paramJoinType);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.criteria.FetchParent
 * JD-Core Version:    0.7.0.1
 */