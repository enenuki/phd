package javax.persistence.criteria;

import java.util.List;
import java.util.Set;

public abstract interface Subquery<T>
  extends AbstractQuery<T>, Expression<T>
{
  public abstract Subquery<T> select(Expression<T> paramExpression);
  
  public abstract Subquery<T> where(Expression<Boolean> paramExpression);
  
  public abstract Subquery<T> where(Predicate... paramVarArgs);
  
  public abstract Subquery<T> groupBy(Expression<?>... paramVarArgs);
  
  public abstract Subquery<T> groupBy(List<Expression<?>> paramList);
  
  public abstract Subquery<T> having(Expression<Boolean> paramExpression);
  
  public abstract Subquery<T> having(Predicate... paramVarArgs);
  
  public abstract Subquery<T> distinct(boolean paramBoolean);
  
  public abstract <Y> Root<Y> correlate(Root<Y> paramRoot);
  
  public abstract <X, Y> Join<X, Y> correlate(Join<X, Y> paramJoin);
  
  public abstract <X, Y> CollectionJoin<X, Y> correlate(CollectionJoin<X, Y> paramCollectionJoin);
  
  public abstract <X, Y> SetJoin<X, Y> correlate(SetJoin<X, Y> paramSetJoin);
  
  public abstract <X, Y> ListJoin<X, Y> correlate(ListJoin<X, Y> paramListJoin);
  
  public abstract <X, K, V> MapJoin<X, K, V> correlate(MapJoin<X, K, V> paramMapJoin);
  
  public abstract AbstractQuery<?> getParent();
  
  public abstract Expression<T> getSelection();
  
  public abstract Set<Join<?, ?>> getCorrelatedJoins();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.criteria.Subquery
 * JD-Core Version:    0.7.0.1
 */