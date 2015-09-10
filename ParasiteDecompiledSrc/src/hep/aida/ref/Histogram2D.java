package hep.aida.ref;

import hep.aida.IAxis;
import hep.aida.IHistogram1D;
import hep.aida.IHistogram2D;

public class Histogram2D
  extends AbstractHistogram2D
  implements IHistogram2D
{
  private double[][] heights;
  private double[][] errors;
  private int[][] entries;
  private int nEntry;
  private double sumWeight;
  private double sumWeightSquared;
  private double meanX;
  private double rmsX;
  private double meanY;
  private double rmsY;
  
  public Histogram2D(String paramString, double[] paramArrayOfDouble1, double[] paramArrayOfDouble2)
  {
    this(paramString, new VariableAxis(paramArrayOfDouble1), new VariableAxis(paramArrayOfDouble2));
  }
  
  public Histogram2D(String paramString, int paramInt1, double paramDouble1, double paramDouble2, int paramInt2, double paramDouble3, double paramDouble4)
  {
    this(paramString, new FixedAxis(paramInt1, paramDouble1, paramDouble2), new FixedAxis(paramInt2, paramDouble3, paramDouble4));
  }
  
  public Histogram2D(String paramString, IAxis paramIAxis1, IAxis paramIAxis2)
  {
    super(paramString);
    this.xAxis = paramIAxis1;
    this.yAxis = paramIAxis2;
    int i = paramIAxis1.bins();
    int j = paramIAxis2.bins();
    this.entries = new int[i + 2][j + 2];
    this.heights = new double[i + 2][j + 2];
    this.errors = new double[i + 2][j + 2];
  }
  
  public int allEntries()
  {
    return this.nEntry;
  }
  
  public int binEntries(int paramInt1, int paramInt2)
  {
    return this.entries[mapX(paramInt1)][mapY(paramInt2)];
  }
  
  public double binError(int paramInt1, int paramInt2)
  {
    return Math.sqrt(this.errors[mapX(paramInt1)][mapY(paramInt2)]);
  }
  
  public double binHeight(int paramInt1, int paramInt2)
  {
    return this.heights[mapX(paramInt1)][mapY(paramInt2)];
  }
  
  public double equivalentBinEntries()
  {
    return this.sumWeight * this.sumWeight / this.sumWeightSquared;
  }
  
  public void fill(double paramDouble1, double paramDouble2)
  {
    int i = mapX(this.xAxis.coordToIndex(paramDouble1));
    int j = mapY(this.yAxis.coordToIndex(paramDouble2));
    this.entries[i][j] += 1;
    this.heights[i][j] += 1.0D;
    this.errors[i][j] += 1.0D;
    this.nEntry += 1;
    this.sumWeight += 1.0D;
    this.sumWeightSquared += 1.0D;
    this.meanX += paramDouble1;
    this.rmsX += paramDouble1;
    this.meanY += paramDouble2;
    this.rmsY += paramDouble2;
  }
  
  public void fill(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    int i = mapX(this.xAxis.coordToIndex(paramDouble1));
    int j = mapY(this.yAxis.coordToIndex(paramDouble2));
    this.entries[i][j] += 1;
    this.heights[i][j] += paramDouble3;
    this.errors[i][j] += paramDouble3 * paramDouble3;
    this.nEntry += 1;
    this.sumWeight += paramDouble3;
    this.sumWeightSquared += paramDouble3 * paramDouble3;
    this.meanX += paramDouble1 * paramDouble3;
    this.rmsX += paramDouble1 * paramDouble3 * paramDouble3;
    this.meanY += paramDouble2 * paramDouble3;
    this.rmsY += paramDouble2 * paramDouble3 * paramDouble3;
  }
  
  protected IHistogram1D internalSliceX(String paramString, int paramInt1, int paramInt2)
  {
    if (paramInt2 < paramInt1) {
      throw new IllegalArgumentException("Invalid bin range");
    }
    int i = this.xAxis.bins() + 2;
    int[] arrayOfInt = new int[i];
    double[] arrayOfDouble1 = new double[i];
    double[] arrayOfDouble2 = new double[i];
    for (int j = 0; j < i; j++) {
      for (int k = paramInt1; k <= paramInt2; k++)
      {
        arrayOfInt[j] += this.entries[j][k];
        arrayOfDouble1[j] += this.heights[j][k];
        arrayOfDouble2[j] += this.errors[j][k];
      }
    }
    Histogram1D localHistogram1D = new Histogram1D(paramString, this.xAxis);
    localHistogram1D.setContents(arrayOfInt, arrayOfDouble1, arrayOfDouble2);
    return localHistogram1D;
  }
  
  protected IHistogram1D internalSliceY(String paramString, int paramInt1, int paramInt2)
  {
    if (paramInt2 < paramInt1) {
      throw new IllegalArgumentException("Invalid bin range");
    }
    int i = this.yAxis.bins() + 2;
    int[] arrayOfInt = new int[i];
    double[] arrayOfDouble1 = new double[i];
    double[] arrayOfDouble2 = new double[i];
    for (int j = paramInt1; j <= paramInt2; j++) {
      for (int k = 0; k < i; k++)
      {
        arrayOfInt[k] += this.entries[j][k];
        arrayOfDouble1[k] += this.heights[j][k];
        arrayOfDouble2[k] += this.errors[j][k];
      }
    }
    Histogram1D localHistogram1D = new Histogram1D(paramString, this.yAxis);
    localHistogram1D.setContents(arrayOfInt, arrayOfDouble1, arrayOfDouble2);
    return localHistogram1D;
  }
  
  public double meanX()
  {
    return this.meanX / this.sumWeight;
  }
  
  public double meanY()
  {
    return this.meanY / this.sumWeight;
  }
  
  public void reset()
  {
    for (int i = 0; i < this.entries.length; i++) {
      for (int j = 0; j < this.entries[0].length; j++)
      {
        this.entries[i][j] = 0;
        this.heights[i][j] = 0.0D;
        this.errors[i][j] = 0.0D;
      }
    }
    this.nEntry = 0;
    this.sumWeight = 0.0D;
    this.sumWeightSquared = 0.0D;
    this.meanX = 0.0D;
    this.rmsX = 0.0D;
    this.meanY = 0.0D;
    this.rmsY = 0.0D;
  }
  
  public double rmsX()
  {
    return Math.sqrt(this.rmsX / this.sumWeight - this.meanX * this.meanX / this.sumWeight / this.sumWeight);
  }
  
  public double rmsY()
  {
    return Math.sqrt(this.rmsY / this.sumWeight - this.meanY * this.meanY / this.sumWeight / this.sumWeight);
  }
  
  void setContents(int[][] paramArrayOfInt, double[][] paramArrayOfDouble1, double[][] paramArrayOfDouble2)
  {
    this.entries = paramArrayOfInt;
    this.heights = paramArrayOfDouble1;
    this.errors = paramArrayOfDouble2;
    for (int i = 0; i < paramArrayOfInt.length; i++) {
      for (int j = 0; j < paramArrayOfInt[0].length; j++)
      {
        this.nEntry += paramArrayOfInt[i][j];
        this.sumWeight += paramArrayOfDouble1[i][j];
      }
    }
    this.sumWeightSquared = (0.0D / 0.0D);
    this.meanX = (0.0D / 0.0D);
    this.rmsX = (0.0D / 0.0D);
    this.meanY = (0.0D / 0.0D);
    this.rmsY = (0.0D / 0.0D);
  }
  
  public double sumAllBinHeights()
  {
    return this.sumWeight;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hep.aida.ref.Histogram2D
 * JD-Core Version:    0.7.0.1
 */