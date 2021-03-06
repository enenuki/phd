package org.apache.xerces.impl.xs.util;

public final class XIntPool
{
  private static final short POOL_SIZE = 10;
  private static final XInt[] fXIntPool = new XInt[10];
  
  public final XInt getXInt(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt < fXIntPool.length)) {
      return fXIntPool[paramInt];
    }
    return new XInt(paramInt);
  }
  
  static
  {
    for (int i = 0; i < 10; i++) {
      fXIntPool[i] = new XInt(i);
    }
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     org.apache.xerces.impl.xs.util.XIntPool
 * JD-Core Version:    0.7.0.1
 */