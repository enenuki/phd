package javax.persistence.criteria;

import java.util.List;
import java.util.Set;

public abstract interface CriteriaQuery<T>
  extends AbstractQuery<T>
{
  public abstract CriteriaQuery<T> select(Selection<? extends T> paramSelection);
  
  public abstract CriteriaQuery<T> multiselect(Selection<?>... paramVarArgs);
  
  public abstract CriteriaQuery<T> multiselect(List<Selection<?>> paramList);
  
  public abstract CriteriaQuery<T> where(Expression<Boolean> paramExpression);
  
  public abstract CriteriaQuery<T> where(Predicate... paramVarArgs);
  
  public abstract CriteriaQuery<T> groupBy(Expression<?>... paramVarArgs);
  
  public abstract CriteriaQuery<T> groupBy(List<Expression<?>> paramList);
  
  public abstract CriteriaQuery<T> having(Expression<Boolean> paramExpression);
  
  public abstract CriteriaQuery<T> having(Predicate... paramVarArgs);
  
  public abstract CriteriaQuery<T> orderBy(Order... paramVarArgs);
  
  public abstract CriteriaQuery<T> orderBy(List<Order> paramList);
  
  public abstract CriteriaQuery<T> distinct(boolean paramBoolean);
  
  public abstract List<Order> getOrderList();
  
  public abstract Set<ParameterExpression<?>> getParameters();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.criteria.CriteriaQuery
 * JD-Core Version:    0.7.0.1
 */