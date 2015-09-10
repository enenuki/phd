package hep.aida.ref;

import hep.aida.IAxis;
import hep.aida.IHistogram1D;

abstract class AbstractHistogram1D
  extends Histogram
  implements IHistogram1D
{
  protected IAxis xAxis;
  
  AbstractHistogram1D(String paramString)
  {
    super(paramString);
  }
  
  public int allEntries()
  {
    return entries() + extraEntries();
  }
  
  public int dimensions()
  {
    return 1;
  }
  
  public int entries()
  {
    int i = 0;
    int j = this.xAxis.bins();
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      i += binEntries(j);
    }
    return i;
  }
  
  public int extraEntries()
  {
    return binEntries(-2) + binEntries(-1);
  }
  
  int map(int paramInt)
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
  
  public int[] minMaxBins()
  {
    double d1 = 1.7976931348623157E+308D;
    double d2 = 4.9E-324D;
    int i = -1;
    int j = -1;
    int k = this.xAxis.bins();
    for (;;)
    {
      k--;
      if (k < 0) {
        break;
      }
      double d3 = binHeight(k);
      if (d3 < d1)
      {
        d1 = d3;
        i = k;
      }
      if (d3 > d2)
      {
        d2 = d3;
        j = k;
      }
    }
    int[] arrayOfInt = { i, j };
    return arrayOfInt;
  }
  
  public double sumAllBinHeights()
  {
    return sumBinHeights() + sumExtraBinHeights();
  }
  
  public double sumBinHeights()
  {
    double d = 0.0D;
    int i = this.xAxis.bins();
    for (;;)
    {
      i--;
      if (i < 0) {
        break;
      }
      d += binHeight(i);
    }
    return d;
  }
  
  public double sumExtraBinHeights()
  {
    return binHeight(-2) + binHeight(-1);
  }
  
  public IAxis xAxis()
  {
    return this.xAxis;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hep.aida.ref.AbstractHistogram1D
 * JD-Core Version:    0.7.0.1
 */