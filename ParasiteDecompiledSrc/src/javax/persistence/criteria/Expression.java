package javax.persistence.criteria;

import java.util.Collection;

public abstract interface Expression<T>
  extends Selection<T>
{
  public abstract Predicate isNull();
  
  public abstract Predicate isNotNull();
  
  public abstract Predicate in(Object... paramVarArgs);
  
  public abstract Predicate in(Expression<?>... paramVarArgs);
  
  public abstract Predicate in(Collection<?> paramCollection);
  
  public abstract Predicate in(Expression<Collection<?>> paramExpression);
  
  public abstract <X> Expression<X> as(Class<X> paramClass);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.criteria.Expression
 * JD-Core Version:    0.7.0.1
 */