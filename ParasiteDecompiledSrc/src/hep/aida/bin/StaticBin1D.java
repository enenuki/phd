package hep.aida.bin;

import cern.colt.list.DoubleArrayList;
import cern.jet.stat.Descriptive;

public class StaticBin1D
  extends AbstractBin1D
{
  protected int size = 0;
  protected double min = 0.0D;
  protected double max = 0.0D;
  protected double sum = 0.0D;
  protected double sum_xx = 0.0D;
  protected static transient double[] arguments = new double[20];
  
  public StaticBin1D()
  {
    clear();
  }
  
  public synchronized void add(double paramDouble)
  {
    addAllOf(new DoubleArrayList(new double[] { paramDouble }));
  }
  
  public synchronized void addAllOfFromTo(DoubleArrayList paramDoubleArrayList, int paramInt1, int paramInt2)
  {
    synchronized (arguments)
    {
      arguments[0] = this.min;
      arguments[1] = this.max;
      arguments[2] = this.sum;
      arguments[3] = this.sum_xx;
      Descriptive.incrementalUpdate(paramDoubleArrayList, paramInt1, paramInt2, arguments);
      this.min = arguments[0];
      this.max = arguments[1];
      this.sum = arguments[2];
      this.sum_xx = arguments[3];
      this.size += paramInt2 - paramInt1 + 1;
    }
  }
  
  public synchronized void clear()
  {
    clearAllMeasures();
    this.size = 0;
  }
  
  protected void clearAllMeasures()
  {
    this.min = (1.0D / 0.0D);
    this.max = (-1.0D / 0.0D);
    this.sum = 0.0D;
    this.sum_xx = 0.0D;
  }
  
  public synchronized boolean isRebinnable()
  {
    return false;
  }
  
  public synchronized double max()
  {
    return this.max;
  }
  
  public synchronized double min()
  {
    return this.min;
  }
  
  public synchronized int size()
  {
    return this.size;
  }
  
  public synchronized double sum()
  {
    return this.sum;
  }
  
  public synchronized double sumOfSquares()
  {
    return this.sum_xx;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hep.aida.bin.StaticBin1D
 * JD-Core Version:    0.7.0.1
 */