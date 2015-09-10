package hep.aida.ref;

import hep.aida.IAxis;

public class FixedAxis
  implements IAxis
{
  private int bins;
  private double min;
  private double binWidth;
  private int xunder;
  private int xover;
  
  public FixedAxis(int paramInt, double paramDouble1, double paramDouble2)
  {
    if (paramInt < 1) {
      throw new IllegalArgumentException("bins=" + paramInt);
    }
    if (paramDouble2 <= paramDouble1) {
      throw new IllegalArgumentException("max <= min");
    }
    this.bins = paramInt;
    this.min = paramDouble1;
    this.binWidth = ((paramDouble2 - paramDouble1) / paramInt);
  }
  
  public double binCentre(int paramInt)
  {
    return this.min + this.binWidth * paramInt + this.binWidth / 2.0D;
  }
  
  public double binLowerEdge(int paramInt)
  {
    if (paramInt == -2) {
      return (-1.0D / 0.0D);
    }
    if (paramInt == -1) {
      return upperEdge();
    }
    return this.min + this.binWidth * paramInt;
  }
  
  public int bins()
  {
    return this.bins;
  }
  
  public double binUpperEdge(int paramInt)
  {
    if (paramInt == -2) {
      return this.min;
    }
    if (paramInt == -1) {
      return (1.0D / 0.0D);
    }
    return this.min + this.binWidth * (paramInt + 1);
  }
  
  public double binWidth(int paramInt)
  {
    return this.binWidth;
  }
  
  public int coordToIndex(double paramDouble)
  {
    if (paramDouble < this.min) {
      return -2;
    }
    int i = (int)Math.floor((paramDouble - this.min) / this.binWidth);
    if (i >= this.bins) {
      return -1;
    }
    return i;
  }
  
  public double lowerEdge()
  {
    return this.min;
  }
  
  public double upperEdge()
  {
    return this.min + this.binWidth * this.bins;
  }
  
  int xgetBin(double paramDouble)
  {
    if (paramDouble < this.min) {
      return this.xunder;
    }
    int i = (int)Math.floor((paramDouble - this.min) / this.binWidth);
    if (i > this.bins) {
      return this.xover;
    }
    return i + 1;
  }
  
  int xmap(int paramInt)
  {
    if (paramInt >= this.bins) {
      throw new IllegalArgumentException("bin=" + paramInt);
    }
    if (paramInt >= 0) {
      return paramInt + 1;
    }
    if (paramInt == -2) {
      return this.xunder;
    }
    if (paramInt == -1) {
      return this.xover;
    }
    throw new IllegalArgumentException("bin=" + paramInt);
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hep.aida.ref.FixedAxis
 * JD-Core Version:    0.7.0.1
 */