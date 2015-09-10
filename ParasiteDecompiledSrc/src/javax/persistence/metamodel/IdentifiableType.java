package javax.persistence.metamodel;

import java.util.Set;

public abstract interface IdentifiableType<X>
  extends ManagedType<X>
{
  public abstract <Y> SingularAttribute<? super X, Y> getId(Class<Y> paramClass);
  
  public abstract <Y> SingularAttribute<X, Y> getDeclaredId(Class<Y> paramClass);
  
  public abstract <Y> SingularAttribute<? super X, Y> getVersion(Class<Y> paramClass);
  
  public abstract <Y> SingularAttribute<X, Y> getDeclaredVersion(Class<Y> paramClass);
  
  public abstract IdentifiableType<? super X> getSupertype();
  
  public abstract boolean hasSingleIdAttribute();
  
  public abstract boolean hasVersionAttribute();
  
  public abstract Set<SingularAttribute<? super X, ?>> getIdClassAttributes();
  
  public abstract Type<?> getIdType();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.metamodel.IdentifiableType
 * JD-Core Version:    0.7.0.1
 */