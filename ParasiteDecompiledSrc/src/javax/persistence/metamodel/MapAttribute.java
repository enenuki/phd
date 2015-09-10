package javax.persistence.metamodel;

import java.util.Map;

public abstract interface MapAttribute<X, K, V>
  extends PluralAttribute<X, Map<K, V>, V>
{
  public abstract Class<K> getKeyJavaType();
  
  public abstract Type<K> getKeyType();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.metamodel.MapAttribute
 * JD-Core Version:    0.7.0.1
 */