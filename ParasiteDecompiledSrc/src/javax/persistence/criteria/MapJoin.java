package javax.persistence.criteria;

import java.util.Map;
import java.util.Map.Entry;
import javax.persistence.metamodel.MapAttribute;

public abstract interface MapJoin<Z, K, V>
  extends PluralJoin<Z, Map<K, V>, V>
{
  public abstract MapAttribute<? super Z, K, V> getModel();
  
  public abstract Path<K> key();
  
  public abstract Path<V> value();
  
  public abstract Expression<Map.Entry<K, V>> entry();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.criteria.MapJoin
 * JD-Core Version:    0.7.0.1
 */