package org.apache.xerces.xs;

public abstract interface ShortList
{
  public abstract int getLength();
  
  public abstract boolean contains(short paramShort);
  
  public abstract short item(int paramInt)
    throws XSException;
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.xs.ShortList
 * JD-Core Version:    0.7.0.1
 */