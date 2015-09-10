package cern.colt.matrix.linalg;

import cern.colt.matrix.DoubleMatrix2D;
import java.io.Serializable;

public class LUDecomposition
  implements Serializable
{
  static final long serialVersionUID = 1020L;
  protected LUDecompositionQuick quick = new LUDecompositionQuick(0.0D);
  
  public LUDecomposition(DoubleMatrix2D paramDoubleMatrix2D)
  {
    this.quick.decompose(paramDoubleMatrix2D.copy());
  }
  
  public double det()
  {
    return this.quick.det();
  }
  
  private double[] getDoublePivot()
  {
    return this.quick.getDoublePivot();
  }
  
  public DoubleMatrix2D getL()
  {
    return this.quick.getL();
  }
  
  public int[] getPivot()
  {
    return (int[])this.quick.getPivot().clone();
  }
  
  public DoubleMatrix2D getU()
  {
    return this.quick.getU();
  }
  
  public boolean isNonsingular()
  {
    return this.quick.isNonsingular();
  }
  
  public DoubleMatrix2D solve(DoubleMatrix2D paramDoubleMatrix2D)
  {
    DoubleMatrix2D localDoubleMatrix2D = paramDoubleMatrix2D.copy();
    this.quick.solve(localDoubleMatrix2D);
    return localDoubleMatrix2D;
  }
  
  public String toString()
  {
    return this.quick.toString();
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.linalg.LUDecomposition
 * JD-Core Version:    0.7.0.1
 */