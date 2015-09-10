package javax.persistence.criteria;

public abstract interface Order
{
  public abstract Order reverse();
  
  public abstract boolean isAscending();
  
  public abstract Expression<?> getExpression();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.criteria.Order
 * JD-Core Version:    0.7.0.1
 */