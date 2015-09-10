package hep.aida;

import java.io.Serializable;

public abstract interface IHistogram
  extends Serializable
{
  public static final int OVERFLOW = -1;
  public static final int UNDERFLOW = -2;
  public static final long serialVersionUID = 1020L;
  
  public abstract int allEntries();
  
  public abstract int dimensions();
  
  public abstract int entries();
  
  public abstract double equivalentBinEntries();
  
  public abstract int extraEntries();
  
  public abstract void reset();
  
  public abstract double sumAllBinHeights();
  
  public abstract double sumBinHeights();
  
  public abstract double sumExtraBinHeights();
  
  public abstract String title();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hep.aida.IHistogram
 * JD-Core Version:    0.7.0.1
 */