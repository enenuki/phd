package javax.persistence.metamodel;

import java.util.Set;

public abstract interface Metamodel
{
  public abstract <X> EntityType<X> entity(Class<X> paramClass);
  
  public abstract <X> ManagedType<X> managedType(Class<X> paramClass);
  
  public abstract <X> EmbeddableType<X> embeddable(Class<X> paramClass);
  
  public abstract Set<ManagedType<?>> getManagedTypes();
  
  public abstract Set<EntityType<?>> getEntities();
  
  public abstract Set<EmbeddableType<?>> getEmbeddables();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.metamodel.Metamodel
 * JD-Core Version:    0.7.0.1
 */