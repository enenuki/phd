package hep.aida.bin;

import cern.colt.PersistentObject;

public abstract class AbstractBin
  extends PersistentObject
{
  public final double center()
  {
    return center(0);
  }
  
  public synchronized double center(int paramInt)
  {
    return 0.5D;
  }
  
  public abstract void clear();
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof AbstractBin)) {
      return false;
    }
    AbstractBin localAbstractBin = (AbstractBin)paramObject;
    return (size() == localAbstractBin.size()) && (value() == localAbstractBin.value()) && (error() == localAbstractBin.error()) && (center() == localAbstractBin.center());
  }
  
  public final double error()
  {
    return error(0);
  }
  
  public synchronized double error(int paramInt)
  {
    return 0.0D;
  }
  
  public abstract boolean isRebinnable();
  
  public final double offset()
  {
    return offset(0);
  }
  
  public double offset(int paramInt)
  {
    return 1.0D;
  }
  
  public abstract int size();
  
  public synchronized String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(getClass().getName());
    localStringBuffer.append("\n-------------");
    localStringBuffer.append("\n");
    return localStringBuffer.toString();
  }
  
  public synchronized void trimToSize() {}
  
  public final double value()
  {
    return value(0);
  }
  
  public double value(int paramInt)
  {
    return 0.0D;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hep.aida.bin.AbstractBin
 * JD-Core Version:    0.7.0.1
 */