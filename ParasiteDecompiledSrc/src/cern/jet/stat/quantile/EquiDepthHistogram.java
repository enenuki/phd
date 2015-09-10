package cern.jet.stat.quantile;

import cern.colt.PersistentObject;
import java.io.PrintStream;
import java.util.Arrays;

public class EquiDepthHistogram
  extends PersistentObject
{
  protected float[] binBoundaries;
  
  public EquiDepthHistogram(float[] paramArrayOfFloat)
  {
    this.binBoundaries = paramArrayOfFloat;
  }
  
  public int binOfElement(float paramFloat)
  {
    int i = Arrays.binarySearch(this.binBoundaries, paramFloat);
    if (i >= 0)
    {
      if (i == this.binBoundaries.length - 1) {
        i--;
      }
    }
    else
    {
      i++;
      if ((i == 0) || (i == this.binBoundaries.length)) {
        throw new IllegalArgumentException("Element=" + paramFloat + " not contained in any bin.");
      }
      i--;
    }
    return i;
  }
  
  public int bins()
  {
    return this.binBoundaries.length - 1;
  }
  
  public float endOfBin(int paramInt)
  {
    return this.binBoundaries[(paramInt + 1)];
  }
  
  public double percentFromTo(float paramFloat1, float paramFloat2)
  {
    return phi(paramFloat2) - phi(paramFloat1);
  }
  
  public double phi(float paramFloat)
  {
    int i = this.binBoundaries.length;
    if (paramFloat <= this.binBoundaries[0]) {
      return 0.0D;
    }
    if (paramFloat >= this.binBoundaries[(i - 1)]) {
      return 1.0D;
    }
    double d1 = 1.0D / (i - 1);
    int j = Arrays.binarySearch(this.binBoundaries, paramFloat);
    if (j >= 0) {
      return d1 * j;
    }
    int k = -j - 1;
    double d2 = this.binBoundaries[(k - 1)];
    double d3 = this.binBoundaries[k] - d2;
    double d4 = (paramFloat - d2) / d3;
    return d1 * (d4 + (k - 1));
  }
  
  /**
   * @deprecated
   */
  public int size()
  {
    return this.binBoundaries.length;
  }
  
  public float startOfBin(int paramInt)
  {
    return this.binBoundaries[paramInt];
  }
  
  public static void test(float paramFloat)
  {
    float[] arrayOfFloat = { 50.0F, 100.0F, 200.0F, 300.0F, 1400.0F, 1500.0F, 1600.0F, 1700.0F, 1800.0F, 1900.0F, 2000.0F };
    EquiDepthHistogram localEquiDepthHistogram = new EquiDepthHistogram(arrayOfFloat);
    System.out.println("elem=" + paramFloat + ", phi=" + localEquiDepthHistogram.phi(paramFloat));
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.jet.stat.quantile.EquiDepthHistogram
 * JD-Core Version:    0.7.0.1
 */