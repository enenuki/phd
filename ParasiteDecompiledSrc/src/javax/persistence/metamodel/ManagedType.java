package javax.persistence.metamodel;

import java.util.Set;

public abstract interface ManagedType<X>
  extends Type<X>
{
  public abstract Set<Attribute<? super X, ?>> getAttributes();
  
  public abstract Set<Attribute<X, ?>> getDeclaredAttributes();
  
  public abstract <Y> SingularAttribute<? super X, Y> getSingularAttribute(String paramString, Class<Y> paramClass);
  
  public abstract <Y> SingularAttribute<X, Y> getDeclaredSingularAttribute(String paramString, Class<Y> paramClass);
  
  public abstract Set<SingularAttribute<? super X, ?>> getSingularAttributes();
  
  public abstract Set<SingularAttribute<X, ?>> getDeclaredSingularAttributes();
  
  public abstract <E> CollectionAttribute<? super X, E> getCollection(String paramString, Class<E> paramClass);
  
  public abstract <E> CollectionAttribute<X, E> getDeclaredCollection(String paramString, Class<E> paramClass);
  
  public abstract <E> SetAttribute<? super X, E> getSet(String paramString, Class<E> paramClass);
  
  public abstract <E> SetAttribute<X, E> getDeclaredSet(String paramString, Class<E> paramClass);
  
  public abstract <E> ListAttribute<? super X, E> getList(String paramString, Class<E> paramClass);
  
  public abstract <E> ListAttribute<X, E> getDeclaredList(String paramString, Class<E> paramClass);
  
  public abstract <K, V> MapAttribute<? super X, K, V> getMap(String paramString, Class<K> paramClass, Class<V> paramClass1);
  
  public abstract <K, V> MapAttribute<X, K, V> getDeclaredMap(String paramString, Class<K> paramClass, Class<V> paramClass1);
  
  public abstract Set<PluralAttribute<? super X, ?, ?>> getPluralAttributes();
  
  public abstract Set<PluralAttribute<X, ?, ?>> getDeclaredPluralAttributes();
  
  public abstract Attribute<? super X, ?> getAttribute(String paramString);
  
  public abstract Attribute<X, ?> getDeclaredAttribute(String paramString);
  
  public abstract SingularAttribute<? super X, ?> getSingularAttribute(String paramString);
  
  public abstract SingularAttribute<X, ?> getDeclaredSingularAttribute(String paramString);
  
  public abstract CollectionAttribute<? super X, ?> getCollection(String paramString);
  
  public abstract CollectionAttribute<X, ?> getDeclaredCollection(String paramString);
  
  public abstract SetAttribute<? super X, ?> getSet(String paramString);
  
  public abstract SetAttribute<X, ?> getDeclaredSet(String paramString);
  
  public abstract ListAttribute<? super X, ?> getList(String paramString);
  
  public abstract ListAttribute<X, ?> getDeclaredList(String paramString);
  
  public abstract MapAttribute<? super X, ?, ?> getMap(String paramString);
  
  public abstract MapAttribute<X, ?, ?> getDeclaredMap(String paramString);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.metamodel.ManagedType
 * JD-Core Version:    0.7.0.1
 */