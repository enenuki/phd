package hep.aida.ref;

import hep.aida.IAxis;
import hep.aida.IHistogram1D;
import hep.aida.IHistogram2D;

class Util
{
  public int maxBin(IHistogram1D paramIHistogram1D)
  {
    int i = -1;
    double d1 = 4.9E-324D;
    int j = paramIHistogram1D.xAxis().bins();
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      double d2 = paramIHistogram1D.binHeight(j);
      if (d2 > d1)
      {
        d1 = d2;
        i = j;
      }
    }
    return i;
  }
  
  public int maxBinX(IHistogram2D paramIHistogram2D)
  {
    double d1 = 4.9E-324D;
    int i = -1;
    int j = -1;
    int k = paramIHistogram2D.xAxis().bins();
    for (;;)
    {
      k--;
      if (k < 0) {
        break;
      }
      int m = paramIHistogram2D.yAxis().bins();
      for (;;)
      {
        m--;
        if (m < 0) {
          break;
        }
        double d2 = paramIHistogram2D.binHeight(k, m);
        if (d2 > d1)
        {
          d1 = d2;
          i = k;
          j = m;
        }
      }
    }
    return i;
  }
  
  public int maxBinY(IHistogram2D paramIHistogram2D)
  {
    double d1 = 4.9E-324D;
    int i = -1;
    int j = -1;
    int k = paramIHistogram2D.xAxis().bins();
    for (;;)
    {
      k--;
      if (k < 0) {
        break;
      }
      int m = paramIHistogram2D.yAxis().bins();
      for (;;)
      {
        m--;
        if (m < 0) {
          break;
        }
        double d2 = paramIHistogram2D.binHeight(k, m);
        if (d2 > d1)
        {
          d1 = d2;
          i = k;
          j = m;
        }
      }
    }
    return j;
  }
  
  public int minBin(IHistogram1D paramIHistogram1D)
  {
    int i = -1;
    double d1 = 1.7976931348623157E+308D;
    int j = paramIHistogram1D.xAxis().bins();
    for (;;)
    {
      j--;
      if (j < 0) {
        break;
      }
      double d2 = paramIHistogram1D.binHeight(j);
      if (d2 < d1)
      {
        d1 = d2;
        i = j;
      }
    }
    return i;
  }
  
  public int minBinX(IHistogram2D paramIHistogram2D)
  {
    double d1 = 1.7976931348623157E+308D;
    int i = -1;
    int j = -1;
    int k = paramIHistogram2D.xAxis().bins();
    for (;;)
    {
      k--;
      if (k < 0) {
        break;
      }
      int m = paramIHistogram2D.yAxis().bins();
      for (;;)
      {
        m--;
        if (m < 0) {
          break;
        }
        double d2 = paramIHistogram2D.binHeight(k, m);
        if (d2 < d1)
        {
          d1 = d2;
          i = k;
          j = m;
        }
      }
    }
    return i;
  }
  
  public int minBinY(IHistogram2D paramIHistogram2D)
  {
    double d1 = 1.7976931348623157E+308D;
    int i = -1;
    int j = -1;
    int k = paramIHistogram2D.xAxis().bins();
    for (;;)
    {
      k--;
      if (k < 0) {
        break;
      }
      int m = paramIHistogram2D.yAxis().bins();
      for (;;)
      {
        m--;
        if (m < 0) {
          break;
        }
        double d2 = paramIHistogram2D.binHeight(k, m);
        if (d2 < d1)
        {
          d1 = d2;
          i = k;
          j = m;
        }
      }
    }
    return j;
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hep.aida.ref.Util
 * JD-Core Version:    0.7.0.1
 */