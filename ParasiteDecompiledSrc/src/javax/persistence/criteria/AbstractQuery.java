package javax.persistence.criteria;

import java.util.List;
import java.util.Set;
import javax.persistence.metamodel.EntityType;

public abstract interface AbstractQuery<T>
{
  public abstract <X> Root<X> from(Class<X> paramClass);
  
  public abstract <X> Root<X> from(EntityType<X> paramEntityType);
  
  public abstract AbstractQuery<T> where(Expression<Boolean> paramExpression);
  
  public abstract AbstractQuery<T> where(Predicate... paramVarArgs);
  
  public abstract AbstractQuery<T> groupBy(Expression<?>... paramVarArgs);
  
  public abstract AbstractQuery<T> groupBy(List<Expression<?>> paramList);
  
  public abstract AbstractQuery<T> having(Expression<Boolean> paramExpression);
  
  public abstract AbstractQuery<T> having(Predicate... paramVarArgs);
  
  public abstract AbstractQuery<T> distinct(boolean paramBoolean);
  
  public abstract <U> Subquery<U> subquery(Class<U> paramClass);
  
  public abstract Set<Root<?>> getRoots();
  
  public abstract Selection<T> getSelection();
  
  public abstract Predicate getRestriction();
  
  public abstract List<Expression<?>> getGroupList();
  
  public abstract Predicate getGroupRestriction();
  
  public abstract boolean isDistinct();
  
  public abstract Class<T> getResultType();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.criteria.AbstractQuery
 * JD-Core Version:    0.7.0.1
 */