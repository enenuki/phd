package antlr.collections;

import java.util.NoSuchElementException;

public abstract interface Stack
{
  public abstract int height();
  
  public abstract Object pop()
    throws NoSuchElementException;
  
  public abstract void push(Object paramObject);
  
  public abstract Object top()
    throws NoSuchElementException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     antlr.collections.Stack
 * JD-Core Version:    0.7.0.1
 */