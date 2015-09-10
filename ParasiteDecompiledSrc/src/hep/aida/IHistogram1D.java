package hep.aida;

public abstract interface IHistogram1D
  extends IHistogram
{
  public abstract int binEntries(int paramInt);
  
  public abstract double binError(int paramInt);
  
  public abstract double binHeight(int paramInt);
  
  public abstract void fill(double paramDouble);
  
  public abstract void fill(double paramDouble1, double paramDouble2);
  
  public abstract double mean();
  
  public abstract int[] minMaxBins();
  
  public abstract double rms();
  
  public abstract IAxis xAxis();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hep.aida.IHistogram1D
 * JD-Core Version:    0.7.0.1
 */