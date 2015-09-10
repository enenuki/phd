package hep.aida;

import java.io.Serializable;

public abstract interface IAxis
  extends Serializable
{
  public static final long serialVersionUID = 1020L;
  
  public abstract double binCentre(int paramInt);
  
  public abstract double binLowerEdge(int paramInt);
  
  public abstract int bins();
  
  public abstract double binUpperEdge(int paramInt);
  
  public abstract double binWidth(int paramInt);
  
  public abstract int coordToIndex(double paramDouble);
  
  public abstract double lowerEdge();
  
  public abstract double upperEdge();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hep.aida.IAxis
 * JD-Core Version:    0.7.0.1
 */