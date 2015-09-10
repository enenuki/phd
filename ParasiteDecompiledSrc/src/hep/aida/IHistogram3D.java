package hep.aida;

public abstract interface IHistogram3D
  extends IHistogram
{
  public abstract int binEntries(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract double binError(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract double binHeight(int paramInt1, int paramInt2, int paramInt3);
  
  public abstract void fill(double paramDouble1, double paramDouble2, double paramDouble3);
  
  public abstract void fill(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);
  
  public abstract double meanX();
  
  public abstract double meanY();
  
  public abstract double meanZ();
  
  public abstract int[] minMaxBins();
  
  public abstract IHistogram2D projectionXY();
  
  public abstract IHistogram2D projectionXZ();
  
  public abstract IHistogram2D projectionYZ();
  
  public abstract double rmsX();
  
  public abstract double rmsY();
  
  public abstract double rmsZ();
  
  public abstract IHistogram2D sliceXY(int paramInt);
  
  public abstract IHistogram2D sliceXY(int paramInt1, int paramInt2);
  
  public abstract IHistogram2D sliceXZ(int paramInt);
  
  public abstract IHistogram2D sliceXZ(int paramInt1, int paramInt2);
  
  public abstract IHistogram2D sliceYZ(int paramInt);
  
  public abstract IHistogram2D sliceYZ(int paramInt1, int paramInt2);
  
  public abstract IAxis xAxis();
  
  public abstract IAxis yAxis();
  
  public abstract IAxis zAxis();
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hep.aida.IHistogram3D
 * JD-Core Version:    0.7.0.1
 */