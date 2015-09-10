package hep.aida.ref;

import hep.aida.IAxis;
import java.util.Arrays;

public class VariableAxis
  implements IAxis
{
  protected double min;
  protected int bins;
  protected double[] edges;
  
  public VariableAxis(double[] paramArrayOfDouble)
  {
    if (paramArrayOfDouble.length < 1) {
      throw new IllegalArgumentException();
    }
    for (int i = 0; i < paramArrayOfDouble.length - 1; i++) {
      if (paramArrayOfDouble[(i + 1)] <= paramArrayOfDouble[i]) {
        throw new IllegalArgumentException("edges must be sorted ascending and must not contain multiple identical values");
      }
    }
    this.min = paramArrayOfDouble[0];
    this.bins = (paramArrayOfDouble.length - 1);
    this.edges = ((double[])paramArrayOfDouble.clone());
  }
  
  public double binCentre(int paramInt)
  {
    return (binLowerEdge(paramInt) + binUpperEdge(paramInt)) / 2.0D;
  }
  
  public double binLowerEdge(int paramInt)
  {
    if (paramInt == -2) {
      return (-1.0D / 0.0D);
    }
    if (paramInt == -1) {
      return upperEdge();
    }
    return this.edges[paramInt];
  }
  
  public int bins()
  {
    return this.bins;
  }
  
  public double binUpperEdge(int paramInt)
  {
    if (paramInt == -2) {
      return lowerEdge();
    }
    if (paramInt == -1) {
      return (1.0D / 0.0D);
    }
    return this.edges[(paramInt + 1)];
  }
  
  public double binWidth(int paramInt)
  {
    return binUpperEdge(paramInt) - binLowerEdge(paramInt);
  }
  
  public int coordToIndex(double paramDouble)
  {
    if (paramDouble < this.min) {
      return -2;
    }
    int i = Arrays.binarySearch(this.edges, paramDouble);
    if (i < 0) {
      i = -i - 1 - 1;
    }
    if (i >= this.bins) {
      return -1;
    }
    return i;
  }
  
  public double lowerEdge()
  {
    return this.min;
  }
  
  protected static String toString(double[] paramArrayOfDouble)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("[");
    int i = paramArrayOfDouble.length - 1;
    for (int j = 0; j <= i; j++)
    {
      localStringBuffer.append(paramArrayOfDouble[j]);
      if (j < i) {
        localStringBuffer.append(", ");
      }
    }
    localStringBuffer.append("]");
    return localStringBuffer.toString();
  }
  
  public double upperEdge()
  {
    return this.edges[(this.edges.length - 1)];
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     hep.aida.ref.VariableAxis
 * JD-Core Version:    0.7.0.1
 */