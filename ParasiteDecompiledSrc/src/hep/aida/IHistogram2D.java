package hep.aida;

public abstract interface IHistogram2D
  extends IHistogram
{
  public abstract int binEntries(int paramInt1, int paramInt2);
  
  public abstract int binEntriesX(int paramInt);
  
  public abstract int binEntriesY(int paramInt);
  
  public abstract double binError(int paramInt1, int paramInt2);
  
  public abstract double binHeight(int paramInt1, int paramInt2);
  
  public abstract double binHeightX(int paramInt);
  
  public abstract double binHeightY(int paramInt);
  
  public abstract void fill(double paramDouble1, double paramDouble2);
  
  public abstract void fill(double paramDouble1, double paramDouble2, double paramDouble3);
  
  public abstract double meanX();
  
  public abstract double meanY();
  
  public abstract int[] minMaxBins();
  
  public abstract IHistogram1D projectionX();
  
  public abstract IHistogram1D projectionY();
  
  public abstract double rmsX();
  
  public abstract double rmsY();
  
  public abstract IHistogram1D sliceX(int paramInt);
  
  public abstract IHistogram1D sliceX(int paramInt1, int paramInt2);
  
  public abstract IHistogram1D sliceY(int paramInt);
  
  public abstract IHistogram1D sliceY(int paramInt1, int paramInt2);
  
  public abstract IAxis xAxis();
  
  public abstract IAxis yAxis();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hep.aida.IHistogram2D
 * JD-Core Version:    0.7.0.1
 */