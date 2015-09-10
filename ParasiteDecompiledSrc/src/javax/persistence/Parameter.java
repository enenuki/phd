package javax.persistence;

public abstract interface Parameter<T>
{
  public abstract String getName();
  
  public abstract Integer getPosition();
  
  public abstract Class<T> getParameterType();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.Parameter
 * JD-Core Version:    0.7.0.1
 */