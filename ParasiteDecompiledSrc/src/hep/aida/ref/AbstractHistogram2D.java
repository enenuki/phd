package hep.aida.ref;

import hep.aida.IAxis;
import hep.aida.IHistogram1D;
import hep.aida.IHistogram2D;

abstract class AbstractHistogram2D
  extends Histogram
  implements IHistogram2D
{
  protected IAxis xAxis;
  protected IAxis yAxis;
  
  AbstractHistogram2D(String paramString)
  {
    super(paramString);
  }
  
  public int allEntries()
  {
    int i = 0;
    int j = this.xAxis.bins();
    for (;;)
    {
      j--;
      if (j < -2) {
        break;
      }
      int k = this.yAxis.bins();
      for (;;)
      {
        k--;
        if (k < -2) {
          break;
        }
        i += binEntries(j, k);
      }
    }
    return i;
  }
  
  public int binEntriesX(int paramInt)
  {
    return projectionX().binEntries(paramInt);
  }
  
  public int binEntriesY(int paramInt)
  {
    return projectionY().binEntries(paramInt);
  }
  
  public double binHeightX(int paramInt)
  {
    return projectionX().binHeight(paramInt);
  }
  
  public double binHeightY(int paramInt)
  {
    return projectionY().binHeight(paramInt);
  }
  
  public int dimensions()
  {
    return 2;
  }
  
  public int entries()
  {
    int i = 0;
    for (int j = 0; j < this.xAxis.bins(); j++) {
      for (int k = 0; k < this.yAxis.bins(); k++) {
        i += binEntries(j, k);
      }
    }
    return i;
  }
  
  public int extraEntries()
  {
    return allEntries() - entries();
  }
  
  public void fill(double paramDouble1, double paramDouble2)
  {
    fill(paramDouble1, paramDouble2, 1.0D);
  }
  
  protected abstract IHistogram1D internalSliceX(String paramString, int paramInt1, int paramInt2);
  
  protected abstract IHistogram1D internalSliceY(String paramString, int paramInt1, int paramInt2);
  
  int mapX(int paramInt)
  {
    int i = this.xAxis.bins() + 2;
    if (paramInt >= i) {
      throw new IllegalArgumentException("bin=" + paramInt);
    }
    if (paramInt >= 0) {
      return paramInt + 1;
    }
    if (paramInt == -2) {
      return 0;
    }
    if (paramInt == -1) {
      return i - 1;
    }
    throw new IllegalArgumentException("bin=" + paramInt);
  }
  
  int mapY(int paramInt)
  {
    int i = this.yAxis.bins() + 2;
    if (paramInt >= i) {
      throw new IllegalArgumentException("bin=" + paramInt);
    }
    if (paramInt >= 0) {
      return paramInt + 1;
    }
    if (paramInt == -2) {
      return 0;
    }
    if (paramInt == -1) {
      return i - 1;
    }
    throw new IllegalArgumentException("bin=" + paramInt);
  }
  
  public int[] minMaxBins()
  {
    double d1 = 1.7976931348623157E+308D;
    double d2 = 4.9E-324D;
    int i = -1;
    int j = -1;
    int k = -1;
    int m = -1;
    int n = this.xAxis.bins();
    for (;;)
    {
      n--;
      if (n < 0) {
        break;
      }
      int i1 = this.yAxis.bins();
      for (;;)
      {
        i1--;
        if (i1 < 0) {
          break;
        }
        double d3 = binHeight(n, i1);
        if (d3 < d1)
        {
          d1 = d3;
          i = n;
          j = i1;
        }
        if (d3 > d2)
        {
          d2 = d3;
          k = n;
          m = i1;
        }
      }
    }
    int[] arrayOfInt = { i, j, k, m };
    return arrayOfInt;
  }
  
  public IHistogram1D projectionX()
  {
    String str = title() + " (projectionX)";
    return internalSliceX(str, mapY(-2), mapY(-1));
  }
  
  public IHistogram1D projectionY()
  {
    String str = title() + " (projectionY)";
    return internalSliceY(str, mapX(-2), mapX(-1));
  }
  
  public IHistogram1D sliceX(int paramInt)
  {
    int i = mapY(paramInt);
    String str = title() + " (sliceX [" + paramInt + "])";
    return internalSliceX(str, i, i);
  }
  
  public IHistogram1D sliceX(int paramInt1, int paramInt2)
  {
    int i = mapY(paramInt1);
    int j = mapY(paramInt2);
    String str = title() + " (sliceX [" + paramInt1 + ":" + paramInt2 + "])";
    return internalSliceX(str, i, j);
  }
  
  public IHistogram1D sliceY(int paramInt)
  {
    int i = mapX(paramInt);
    String str = title() + " (sliceY [" + paramInt + "])";
    return internalSliceY(str, i, i);
  }
  
  public IHistogram1D sliceY(int paramInt1, int paramInt2)
  {
    int i = mapX(paramInt1);
    int j = mapX(paramInt2);
    String str = title() + " (slicey [" + paramInt1 + ":" + paramInt2 + "])";
    return internalSliceY(str, i, j);
  }
  
  public double sumAllBinHeights()
  {
    double d = 0.0D;
    int i = this.xAxis.bins();
    for (;;)
    {
      i--;
      if (i < -2) {
        break;
      }
      int j = this.yAxis.bins();
      for (;;)
      {
        j--;
        if (j < -2) {
          break;
        }
        d += binHeight(i, j);
      }
    }
    return d;
  }
  
  public double sumBinHeights()
  {
    double d = 0.0D;
    for (int i = 0; i < this.xAxis.bins(); i++) {
      for (int j = 0; j < this.yAxis.bins(); j++) {
        d += binHeight(i, j);
      }
    }
    return d;
  }
  
  public double sumExtraBinHeights()
  {
    return sumAllBinHeights() - sumBinHeights();
  }
  
  public IAxis xAxis()
  {
    return this.xAxis;
  }
  
  public IAxis yAxis()
  {
    return this.yAxis;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hep.aida.ref.AbstractHistogram2D
 * JD-Core Version:    0.7.0.1
 */