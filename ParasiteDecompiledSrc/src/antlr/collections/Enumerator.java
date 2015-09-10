package antlr.collections;

public abstract interface Enumerator
{
  public abstract Object cursor();
  
  public abstract Object next();
  
  public abstract boolean valid();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.collections.Enumerator
 * JD-Core Version:    0.7.0.1
 */