package javax.persistence.criteria;

import java.util.Collection;
import java.util.Map;
import javax.persistence.metamodel.Bindable;
import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;

public abstract interface Path<X>
  extends Expression<X>
{
  public abstract Bindable<X> getModel();
  
  public abstract Path<?> getParentPath();
  
  public abstract <Y> Path<Y> get(SingularAttribute<? super X, Y> paramSingularAttribute);
  
  public abstract <E, C extends Collection<E>> Expression<C> get(PluralAttribute<X, C, E> paramPluralAttribute);
  
  public abstract <K, V, M extends Map<K, V>> Expression<M> get(MapAttribute<X, K, V> paramMapAttribute);
  
  public abstract Expression<Class<? extends X>> type();
  
  public abstract <Y> Path<Y> get(String paramString);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.criteria.Path
 * JD-Core Version:    0.7.0.1
 */