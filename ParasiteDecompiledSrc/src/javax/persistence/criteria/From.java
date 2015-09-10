package javax.persistence.criteria;

import java.util.Set;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;

public abstract interface From<Z, X>
  extends Path<X>, FetchParent<Z, X>
{
  public abstract Set<Join<X, ?>> getJoins();
  
  public abstract boolean isCorrelated();
  
  public abstract From<Z, X> getCorrelationParent();
  
  public abstract <Y> Join<X, Y> join(SingularAttribute<? super X, Y> paramSingularAttribute);
  
  public abstract <Y> Join<X, Y> join(SingularAttribute<? super X, Y> paramSingularAttribute, JoinType paramJoinType);
  
  public abstract <Y> CollectionJoin<X, Y> join(CollectionAttribute<? super X, Y> paramCollectionAttribute);
  
  public abstract <Y> SetJoin<X, Y> join(SetAttribute<? super X, Y> paramSetAttribute);
  
  public abstract <Y> ListJoin<X, Y> join(ListAttribute<? super X, Y> paramListAttribute);
  
  public abstract <K, V> MapJoin<X, K, V> join(MapAttribute<? super X, K, V> paramMapAttribute);
  
  public abstract <Y> CollectionJoin<X, Y> join(CollectionAttribute<? super X, Y> paramCollectionAttribute, JoinType paramJoinType);
  
  public abstract <Y> SetJoin<X, Y> join(SetAttribute<? super X, Y> paramSetAttribute, JoinType paramJoinType);
  
  public abstract <Y> ListJoin<X, Y> join(ListAttribute<? super X, Y> paramListAttribute, JoinType paramJoinType);
  
  public abstract <K, V> MapJoin<X, K, V> join(MapAttribute<? super X, K, V> paramMapAttribute, JoinType paramJoinType);
  
  public abstract <X, Y> Join<X, Y> join(String paramString);
  
  public abstract <X, Y> CollectionJoin<X, Y> joinCollection(String paramString);
  
  public abstract <X, Y> SetJoin<X, Y> joinSet(String paramString);
  
  public abstract <X, Y> ListJoin<X, Y> joinList(String paramString);
  
  public abstract <X, K, V> MapJoin<X, K, V> joinMap(String paramString);
  
  public abstract <X, Y> Join<X, Y> join(String paramString, JoinType paramJoinType);
  
  public abstract <X, Y> CollectionJoin<X, Y> joinCollection(String paramString, JoinType paramJoinType);
  
  public abstract <X, Y> SetJoin<X, Y> joinSet(String paramString, JoinType paramJoinType);
  
  public abstract <X, Y> ListJoin<X, Y> joinList(String paramString, JoinType paramJoinType);
  
  public abstract <X, K, V> MapJoin<X, K, V> joinMap(String paramString, JoinType paramJoinType);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.criteria.From
 * JD-Core Version:    0.7.0.1
 */