package hep.aida.ref;

import hep.aida.IAxis;
import hep.aida.IHistogram1D;

public class Histogram1D
  extends AbstractHistogram1D
  implements IHistogram1D
{
  private double[] errors;
  private double[] heights;
  private int[] entries;
  private int nEntry;
  private double sumWeight;
  private double sumWeightSquared;
  private double mean;
  private double rms;
  
  public Histogram1D(String paramString, double[] paramArrayOfDouble)
  {
    this(paramString, new VariableAxis(paramArrayOfDouble));
  }
  
  public Histogram1D(String paramString, int paramInt, double paramDouble1, double paramDouble2)
  {
    this(paramString, new FixedAxis(paramInt, paramDouble1, paramDouble2));
  }
  
  public Histogram1D(String paramString, IAxis paramIAxis)
  {
    super(paramString);
    this.xAxis = paramIAxis;
    int i = paramIAxis.bins();
    this.entries = new int[i + 2];
    this.heights = new double[i + 2];
    this.errors = new double[i + 2];
  }
  
  public int allEntries()
  {
    return this.nEntry;
  }
  
  public int binEntries(int paramInt)
  {
    return this.entries[map(paramInt)];
  }
  
  public double binError(int paramInt)
  {
    return Math.sqrt(this.errors[map(paramInt)]);
  }
  
  public double binHeight(int paramInt)
  {
    return this.heights[map(paramInt)];
  }
  
  public double equivalentBinEntries()
  {
    return this.sumWeight * this.sumWeight / this.sumWeightSquared;
  }
  
  public void fill(double paramDouble)
  {
    int i = map(this.xAxis.coordToIndex(paramDouble));
    this.entries[i] += 1;
    this.heights[i] += 1.0D;
    this.errors[i] += 1.0D;
    this.nEntry += 1;
    this.sumWeight += 1.0D;
    this.sumWeightSquared += 1.0D;
    this.mean += paramDouble;
    this.rms += paramDouble * paramDouble;
  }
  
  public void fill(double paramDouble1, double paramDouble2)
  {
    int i = map(this.xAxis.coordToIndex(paramDouble1));
    this.entries[i] += 1;
    this.heights[i] += paramDouble2;
    this.errors[i] += paramDouble2 * paramDouble2;
    this.nEntry += 1;
    this.sumWeight += paramDouble2;
    this.sumWeightSquared += paramDouble2 * paramDouble2;
    this.mean += paramDouble1 * paramDouble2;
    this.rms += paramDouble1 * paramDouble2 * paramDouble2;
  }
  
  public double mean()
  {
    return this.mean / this.sumWeight;
  }
  
  public void reset()
  {
    for (int i = 0; i < this.entries.length; i++)
    {
      this.entries[i] = 0;
      this.heights[i] = 0.0D;
      this.errors[i] = 0.0D;
    }
    this.nEntry = 0;
    this.sumWeight = 0.0D;
    this.sumWeightSquared = 0.0D;
    this.mean = 0.0D;
    this.rms = 0.0D;
  }
  
  public double rms()
  {
    return Math.sqrt(this.rms / this.sumWeight - this.mean * this.mean / this.sumWeight / this.sumWeight);
  }
  
  void setContents(int[] paramArrayOfInt, double[] paramArrayOfDouble1, double[] paramArrayOfDouble2)
  {
    this.entries = paramArrayOfInt;
    this.heights = paramArrayOfDouble1;
    this.errors = paramArrayOfDouble2;
    for (int i = 0; i < paramArrayOfInt.length; i++)
    {
      this.nEntry += paramArrayOfInt[i];
      this.sumWeight += paramArrayOfDouble1[i];
    }
    this.sumWeightSquared = (0.0D / 0.0D);
    this.mean = (0.0D / 0.0D);
    this.rms = (0.0D / 0.0D);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hep.aida.ref.Histogram1D
 * JD-Core Version:    0.7.0.1
 */