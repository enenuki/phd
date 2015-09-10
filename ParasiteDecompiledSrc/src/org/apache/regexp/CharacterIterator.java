package org.apache.regexp;

public abstract interface CharacterIterator
{
  public abstract char charAt(int paramInt);
  
  public abstract boolean isEnd(int paramInt);
  
  public abstract String substring(int paramInt);
  
  public abstract String substring(int paramInt1, int paramInt2);
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.regexp.CharacterIterator
 * JD-Core Version:    0.7.0.1
 */