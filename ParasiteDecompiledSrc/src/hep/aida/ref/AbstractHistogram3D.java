package hep.aida.ref;

import hep.aida.IAxis;
import hep.aida.IHistogram2D;
import hep.aida.IHistogram3D;

abstract class AbstractHistogram3D
  extends Histogram
  implements IHistogram3D
{
  protected IAxis xAxis;
  protected IAxis yAxis;
  protected IAxis zAxis;
  
  AbstractHistogram3D(String paramString)
  {
    super(paramString);
  }
  
  public int allEntries()
  {
    int i = 0;
    int j = this.xAxis.bins();
    j--;
    if (j >= -2)
    {
      int k = this.yAxis.bins();
      for (;;)
      {
        k--;
        if (k < -2) {
          break;
        }
        int m = this.zAxis.bins();
        for (;;)
        {
          m--;
          if (m < -2) {
            break;
          }
          i += binEntries(j, k, m);
        }
      }
    }
    return i;
  }
  
  public int dimensions()
  {
    return 3;
  }
  
  public int entries()
  {
    int i = 0;
    for (int j = 0; j < this.xAxis.bins(); j++) {
      for (int k = 0; k < this.yAxis.bins(); k++) {
        for (int m = 0; m < this.zAxis.bins(); m++) {
          i += binEntries(j, k, m);
        }
      }
    }
    return i;
  }
  
  public int extraEntries()
  {
    return allEntries() - entries();
  }
  
  public void fill(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    fill(paramDouble1, paramDouble2, paramDouble3, 1.0D);
  }
  
  protected abstract IHistogram2D internalSliceXY(String paramString, int paramInt1, int paramInt2);
  
  protected abstract IHistogram2D internalSliceXZ(String paramString, int paramInt1, int paramInt2);
  
  protected abstract IHistogram2D internalSliceYZ(String paramString, int paramInt1, int paramInt2);
  
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
  
  int mapZ(int paramInt)
  {
    int i = this.zAxis.bins() + 2;
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
    int n = -1;
    int i1 = -1;
    int i2 = this.xAxis.bins();
    i2--;
    if (i2 >= 0)
    {
      int i3 = this.yAxis.bins();
      for (;;)
      {
        i3--;
        if (i3 < 0) {
          break;
        }
        int i4 = this.zAxis.bins();
        for (;;)
        {
          i4--;
          if (i4 < 0) {
            break;
          }
          double d3 = binHeight(i2, i3, i4);
          if (d3 < d1)
          {
            d1 = d3;
            i = i2;
            j = i3;
            k = i4;
          }
          if (d3 > d2)
          {
            d2 = d3;
            m = i2;
            n = i3;
            i1 = i4;
          }
        }
      }
    }
    int[] arrayOfInt = { i, j, k, m, n, i1 };
    return arrayOfInt;
  }
  
  public IHistogram2D projectionXY()
  {
    String str = title() + " (projectionXY)";
    return internalSliceXY(str, mapZ(-2), mapZ(-1));
  }
  
  public IHistogram2D projectionXZ()
  {
    String str = title() + " (projectionXZ)";
    return internalSliceXZ(str, mapY(-2), mapY(-1));
  }
  
  public IHistogram2D projectionYZ()
  {
    String str = title() + " (projectionYZ)";
    return internalSliceYZ(str, mapX(-2), mapX(-1));
  }
  
  public IHistogram2D sliceXY(int paramInt)
  {
    return sliceXY(paramInt, paramInt);
  }
  
  public IHistogram2D sliceXY(int paramInt1, int paramInt2)
  {
    int i = mapZ(paramInt1);
    int j = mapZ(paramInt2);
    String str = title() + " (sliceXY [" + paramInt1 + ":" + paramInt2 + "])";
    return internalSliceXY(str, i, j);
  }
  
  public IHistogram2D sliceXZ(int paramInt)
  {
    return sliceXZ(paramInt, paramInt);
  }
  
  public IHistogram2D sliceXZ(int paramInt1, int paramInt2)
  {
    int i = mapY(paramInt1);
    int j = mapY(paramInt2);
    String str = title() + " (sliceXZ [" + paramInt1 + ":" + paramInt2 + "])";
    return internalSliceXY(str, i, j);
  }
  
  public IHistogram2D sliceYZ(int paramInt)
  {
    return sliceYZ(paramInt, paramInt);
  }
  
  public IHistogram2D sliceYZ(int paramInt1, int paramInt2)
  {
    int i = mapX(paramInt1);
    int j = mapX(paramInt2);
    String str = title() + " (sliceYZ [" + paramInt1 + ":" + paramInt2 + "])";
    return internalSliceYZ(str, i, j);
  }
  
  public double sumAllBinHeights()
  {
    double d = 0.0D;
    int i = this.xAxis.bins();
    i--;
    if (i >= -2)
    {
      int j = this.yAxis.bins();
      for (;;)
      {
        j--;
        if (j < -2) {
          break;
        }
        int k = this.zAxis.bins();
        for (;;)
        {
          k--;
          if (k < -2) {
            break;
          }
          d += binHeight(i, j, k);
        }
      }
    }
    return d;
  }
  
  public double sumBinHeights()
  {
    double d = 0.0D;
    for (int i = 0; i < this.xAxis.bins(); i++) {
      for (int j = 0; j < this.yAxis.bins(); j++) {
        for (int k = 0; k < this.zAxis.bins(); k++) {
          d += binHeight(i, j, k);
        }
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
  
  public IAxis zAxis()
  {
    return this.zAxis;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hep.aida.ref.AbstractHistogram3D
 * JD-Core Version:    0.7.0.1
 */