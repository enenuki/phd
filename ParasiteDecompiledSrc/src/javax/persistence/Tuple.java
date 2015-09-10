package javax.persistence;

import java.util.List;

public abstract interface Tuple
{
  public abstract <X> X get(TupleElement<X> paramTupleElement);
  
  public abstract <X> X get(String paramString, Class<X> paramClass);
  
  public abstract Object get(String paramString);
  
  public abstract <X> X get(int paramInt, Class<X> paramClass);
  
  public abstract Object get(int paramInt);
  
  public abstract Object[] toArray();
  
  public abstract List<TupleElement<?>> getElements();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     javax.persistence.Tuple
 * JD-Core Version:    0.7.0.1
 */