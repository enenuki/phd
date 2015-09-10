package org.apache.xerces.impl.xs.util;

public final class XInt
{
  private int fValue;
  
  XInt(int paramInt)
  {
    this.fValue = paramInt;
  }
  
  public final int intValue()
  {
    return this.fValue;
  }
  
  public final short shortValue()
  {
    return (short)this.fValue;
  }
  
  public final boolean equals(XInt paramXInt)
  {
    return this.fValue == paramXInt.fValue;
  }
  
  public String toString()
  {
    return Integer.toString(this.fValue);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.util.XInt
 * JD-Core Version:    0.7.0.1
 */