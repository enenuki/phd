package javax.persistence.metamodel;

public abstract interface SingularAttribute<X, T>
  extends Attribute<X, T>, Bindable<T>
{
  public abstract boolean isId();
  
  public abstract boolean isVersion();
  
  public abstract boolean isOptional();
  
  public abstract Type<T> getType();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.metamodel.SingularAttribute
 * JD-Core Version:    0.7.0.1
 */