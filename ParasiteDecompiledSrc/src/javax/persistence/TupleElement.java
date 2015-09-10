package javax.persistence;

public abstract interface TupleElement<X>
{
  public abstract Class<? extends X> getJavaType();
  
  public abstract String getAlias();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.TupleElement
 * JD-Core Version:    0.7.0.1
 */