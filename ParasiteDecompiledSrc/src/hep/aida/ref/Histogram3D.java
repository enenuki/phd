package hep.aida.ref;

import hep.aida.IAxis;
import hep.aida.IHistogram2D;
import hep.aida.IHistogram3D;

public class Histogram3D
  extends AbstractHistogram3D
  implements IHistogram3D
{
  private double[][][] heights;
  private double[][][] errors;
  private int[][][] entries;
  private int nEntry;
  private double sumWeight;
  private double sumWeightSquared;
  private double meanX;
  private double rmsX;
  private double meanY;
  private double rmsY;
  private double meanZ;
  private double rmsZ;
  
  public Histogram3D(String paramString, double[] paramArrayOfDouble1, double[] paramArrayOfDouble2, double[] paramArrayOfDouble3)
  {
    this(paramString, new VariableAxis(paramArrayOfDouble1), new VariableAxis(paramArrayOfDouble2), new VariableAxis(paramArrayOfDouble3));
  }
  
  public Histogram3D(String paramString, int paramInt1, double paramDouble1, double paramDouble2, int paramInt2, double paramDouble3, double paramDouble4, int paramInt3, double paramDouble5, double paramDouble6)
  {
    this(paramString, new FixedAxis(paramInt1, paramDouble1, paramDouble2), new FixedAxis(paramInt2, paramDouble3, paramDouble4), new FixedAxis(paramInt3, paramDouble5, paramDouble6));
  }
  
  public Histogram3D(String paramString, IAxis paramIAxis1, IAxis paramIAxis2, IAxis paramIAxis3)
  {
    super(paramString);
    this.xAxis = paramIAxis1;
    this.yAxis = paramIAxis2;
    this.zAxis = paramIAxis3;
    int i = paramIAxis1.bins();
    int j = paramIAxis2.bins();
    int k = paramIAxis3.bins();
    this.entries = new int[i + 2][j + 2][k + 2];
    this.heights = new double[i + 2][j + 2][k + 2];
    this.errors = new double[i + 2][j + 2][k + 2];
  }
  
  public int allEntries()
  {
    return this.nEntry;
  }
  
  public int binEntries(int paramInt1, int paramInt2, int paramInt3)
  {
    return this.entries[mapX(paramInt1)][mapY(paramInt2)][mapZ(paramInt3)];
  }
  
  public double binError(int paramInt1, int paramInt2, int paramInt3)
  {
    return Math.sqrt(this.errors[mapX(paramInt1)][mapY(paramInt2)][mapZ(paramInt3)]);
  }
  
  public double binHeight(int paramInt1, int paramInt2, int paramInt3)
  {
    return this.heights[mapX(paramInt1)][mapY(paramInt2)][mapZ(paramInt3)];
  }
  
  public double equivalentBinEntries()
  {
    return this.sumWeight * this.sumWeight / this.sumWeightSquared;
  }
  
  public void fill(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    int i = mapX(this.xAxis.coordToIndex(paramDouble1));
    int j = mapY(this.yAxis.coordToIndex(paramDouble2));
    int k = mapZ(this.zAxis.coordToIndex(paramDouble3));
    this.entries[i][j][k] += 1;
    this.heights[i][j][k] += 1.0D;
    this.errors[i][j][k] += 1.0D;
    this.nEntry += 1;
    this.sumWeight += 1.0D;
    this.sumWeightSquared += 1.0D;
    this.meanX += paramDouble1;
    this.rmsX += paramDouble1;
    this.meanY += paramDouble2;
    this.rmsY += paramDouble2;
    this.meanZ += paramDouble3;
    this.rmsZ += paramDouble3;
  }
  
  public void fill(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
  {
    int i = mapX(this.xAxis.coordToIndex(paramDouble1));
    int j = mapY(this.yAxis.coordToIndex(paramDouble2));
    int k = mapZ(this.zAxis.coordToIndex(paramDouble3));
    this.entries[i][j][k] += 1;
    this.heights[i][j][k] += paramDouble4;
    this.errors[i][j][k] += paramDouble4 * paramDouble4;
    this.nEntry += 1;
    this.sumWeight += paramDouble4;
    this.sumWeightSquared += paramDouble4 * paramDouble4;
    this.meanX += paramDouble1 * paramDouble4;
    this.rmsX += paramDouble1 * paramDouble4 * paramDouble4;
    this.meanY += paramDouble2 * paramDouble4;
    this.rmsY += paramDouble2 * paramDouble4 * paramDouble4;
    this.meanZ += paramDouble3 * paramDouble4;
    this.rmsZ += paramDouble3 * paramDouble4 * paramDouble4;
  }
  
  protected IHistogram2D internalSliceXY(String paramString, int paramInt1, int paramInt2)
  {
    if (paramInt2 < paramInt1) {
      throw new IllegalArgumentException("Invalid bin range");
    }
    int i = this.xAxis.bins() + 2;
    int j = this.yAxis.bins() + 2;
    int[][] arrayOfInt = new int[i][j];
    double[][] arrayOfDouble1 = new double[i][j];
    double[][] arrayOfDouble2 = new double[i][j];
    for (int k = 0; k < i; k++) {
      for (int m = 0; m < j; m++) {
        for (int n = paramInt1; n <= paramInt2; n++)
        {
          arrayOfInt[k][m] += this.entries[k][m][n];
          arrayOfDouble1[k][m] += this.heights[k][m][n];
          arrayOfDouble2[k][m] += this.errors[k][m][n];
        }
      }
    }
    Histogram2D localHistogram2D = new Histogram2D(paramString, this.xAxis, this.yAxis);
    localHistogram2D.setContents(arrayOfInt, arrayOfDouble1, arrayOfDouble2);
    return localHistogram2D;
  }
  
  protected IHistogram2D internalSliceXZ(String paramString, int paramInt1, int paramInt2)
  {
    if (paramInt2 < paramInt1) {
      throw new IllegalArgumentException("Invalid bin range");
    }
    int i = this.xAxis.bins() + 2;
    int j = this.zAxis.bins() + 2;
    int[][] arrayOfInt = new int[i][j];
    double[][] arrayOfDouble1 = new double[i][j];
    double[][] arrayOfDouble2 = new double[i][j];
    for (int k = 0; k < i; k++) {
      for (int m = paramInt1; m <= paramInt2; m++) {
        for (int n = 0; k < j; n++)
        {
          arrayOfInt[k][n] += this.entries[k][m][n];
          arrayOfDouble1[k][n] += this.heights[k][m][n];
          arrayOfDouble2[k][n] += this.errors[k][m][n];
        }
      }
    }
    Histogram2D localHistogram2D = new Histogram2D(paramString, this.xAxis, this.zAxis);
    localHistogram2D.setContents(arrayOfInt, arrayOfDouble1, arrayOfDouble2);
    return localHistogram2D;
  }
  
  protected IHistogram2D internalSliceYZ(String paramString, int paramInt1, int paramInt2)
  {
    if (paramInt2 < paramInt1) {
      throw new IllegalArgumentException("Invalid bin range");
    }
    int i = this.yAxis.bins() + 2;
    int j = this.zAxis.bins() + 2;
    int[][] arrayOfInt = new int[i][j];
    double[][] arrayOfDouble1 = new double[i][j];
    double[][] arrayOfDouble2 = new double[i][j];
    for (int k = paramInt1; k <= paramInt2; k++) {
      for (int m = 0; m < i; m++) {
        for (int n = 0; n < j; n++)
        {
          arrayOfInt[m][n] += this.entries[k][m][n];
          arrayOfDouble1[m][n] += this.heights[k][m][n];
          arrayOfDouble2[m][n] += this.errors[k][m][n];
        }
      }
    }
    Histogram2D localHistogram2D = new Histogram2D(paramString, this.yAxis, this.zAxis);
    localHistogram2D.setContents(arrayOfInt, arrayOfDouble1, arrayOfDouble2);
    return localHistogram2D;
  }
  
  public double meanX()
  {
    return this.meanX / this.sumWeight;
  }
  
  public double meanY()
  {
    return this.meanY / this.sumWeight;
  }
  
  public double meanZ()
  {
    return this.meanZ / this.sumWeight;
  }
  
  public void reset()
  {
    for (int i = 0; i < this.entries.length; i++) {
      for (int j = 0; j < this.entries[0].length; j++) {
        for (int k = 0; j < this.entries[0][0].length; k++)
        {
          this.entries[i][j][k] = 0;
          this.heights[i][j][k] = 0.0D;
          this.errors[i][j][k] = 0.0D;
        }
      }
    }
    this.nEntry = 0;
    this.sumWeight = 0.0D;
    this.sumWeightSquared = 0.0D;
    this.meanX = 0.0D;
    this.rmsX = 0.0D;
    this.meanY = 0.0D;
    this.rmsY = 0.0D;
    this.meanZ = 0.0D;
    this.rmsZ = 0.0D;
  }
  
  public double rmsX()
  {
    return Math.sqrt(this.rmsX / this.sumWeight - this.meanX * this.meanX / this.sumWeight / this.sumWeight);
  }
  
  public double rmsY()
  {
    return Math.sqrt(this.rmsY / this.sumWeight - this.meanY * this.meanY / this.sumWeight / this.sumWeight);
  }
  
  public double rmsZ()
  {
    return Math.sqrt(this.rmsZ / this.sumWeight - this.meanZ * this.meanZ / this.sumWeight / this.sumWeight);
  }
  
  public double sumAllBinHeights()
  {
    return this.sumWeight;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hep.aida.ref.Histogram3D
 * JD-Core Version:    0.7.0.1
 */