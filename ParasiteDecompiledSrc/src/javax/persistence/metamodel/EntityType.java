package javax.persistence.metamodel;

public abstract interface EntityType<X>
  extends IdentifiableType<X>, Bindable<X>
{
  public abstract String getName();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.metamodel.EntityType
 * JD-Core Version:    0.7.0.1
 */