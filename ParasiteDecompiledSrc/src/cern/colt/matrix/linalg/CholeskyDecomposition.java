package cern.colt.matrix.linalg;

import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.jet.math.Functions;
import java.io.Serializable;

public class CholeskyDecomposition
  implements Serializable
{
  static final long serialVersionUID = 1020L;
  private DoubleMatrix2D L;
  private int n;
  private boolean isSymmetricPositiveDefinite;
  
  public CholeskyDecomposition(DoubleMatrix2D paramDoubleMatrix2D)
  {
    Property.DEFAULT.checkSquare(paramDoubleMatrix2D);
    this.n = paramDoubleMatrix2D.rows();
    this.L = paramDoubleMatrix2D.like(this.n, this.n);
    this.isSymmetricPositiveDefinite = (paramDoubleMatrix2D.columns() == this.n);
    DoubleMatrix1D[] arrayOfDoubleMatrix1D = new DoubleMatrix1D[this.n];
    for (int i = 0; i < this.n; i++) {
      arrayOfDoubleMatrix1D[i] = this.L.viewRow(i);
    }
    for (i = 0; i < this.n; i++)
    {
      double d1 = 0.0D;
      for (int j = 0; j < i; j++)
      {
        double d2 = arrayOfDoubleMatrix1D[j].zDotProduct(arrayOfDoubleMatrix1D[i], 0, j);
        d2 = (paramDoubleMatrix2D.getQuick(i, j) - d2) / this.L.getQuick(j, j);
        arrayOfDoubleMatrix1D[i].setQuick(j, d2);
        d1 += d2 * d2;
        this.isSymmetricPositiveDefinite = ((this.isSymmetricPositiveDefinite) && (paramDoubleMatrix2D.getQuick(j, i) == paramDoubleMatrix2D.getQuick(i, j)));
      }
      d1 = paramDoubleMatrix2D.getQuick(i, i) - d1;
      this.isSymmetricPositiveDefinite = ((this.isSymmetricPositiveDefinite) && (d1 > 0.0D));
      this.L.setQuick(i, i, Math.sqrt(Math.max(d1, 0.0D)));
      for (j = i + 1; j < this.n; j++) {
        this.L.setQuick(i, j, 0.0D);
      }
    }
  }
  
  public DoubleMatrix2D getL()
  {
    return this.L;
  }
  
  public boolean isSymmetricPositiveDefinite()
  {
    return this.isSymmetricPositiveDefinite;
  }
  
  public DoubleMatrix2D solve(DoubleMatrix2D paramDoubleMatrix2D)
  {
    DoubleMatrix2D localDoubleMatrix2D = paramDoubleMatrix2D.copy();
    int i = paramDoubleMatrix2D.columns();
    for (int j = 0; j < i; j++)
    {
      double d;
      int m;
      for (int k = 0; k < this.n; k++)
      {
        d = paramDoubleMatrix2D.getQuick(k, j);
        for (m = k - 1; m >= 0; m--) {
          d -= this.L.getQuick(k, m) * localDoubleMatrix2D.getQuick(m, j);
        }
        localDoubleMatrix2D.setQuick(k, j, d / this.L.getQuick(k, k));
      }
      for (k = this.n - 1; k >= 0; k--)
      {
        d = localDoubleMatrix2D.getQuick(k, j);
        for (m = k + 1; m < this.n; m++) {
          d -= this.L.getQuick(m, k) * localDoubleMatrix2D.getQuick(m, j);
        }
        localDoubleMatrix2D.setQuick(k, j, d / this.L.getQuick(k, k));
      }
    }
    return localDoubleMatrix2D;
  }
  
  private DoubleMatrix2D XXXsolveBuggy(DoubleMatrix2D paramDoubleMatrix2D)
  {
    Functions localFunctions = Functions.functions;
    if (paramDoubleMatrix2D.rows() != this.n) {
      throw new IllegalArgumentException("Matrix row dimensions must agree.");
    }
    if (!this.isSymmetricPositiveDefinite) {
      throw new IllegalArgumentException("Matrix is not symmetric positive definite.");
    }
    DoubleMatrix2D localDoubleMatrix2D = paramDoubleMatrix2D.copy();
    int i = paramDoubleMatrix2D.columns();
    DoubleMatrix1D[] arrayOfDoubleMatrix1D = new DoubleMatrix1D[this.n];
    for (int j = 0; j < this.n; j++) {
      arrayOfDoubleMatrix1D[j] = localDoubleMatrix2D.viewRow(j);
    }
    int k;
    for (j = 0; j < this.n; j++)
    {
      for (k = j + 1; k < this.n; k++) {
        arrayOfDoubleMatrix1D[k].assign(arrayOfDoubleMatrix1D[j], Functions.minusMult(this.L.getQuick(k, j)));
      }
      arrayOfDoubleMatrix1D[j].assign(Functions.div(this.L.getQuick(j, j)));
    }
    for (j = this.n - 1; j >= 0; j--)
    {
      arrayOfDoubleMatrix1D[j].assign(Functions.div(this.L.getQuick(j, j)));
      for (k = 0; k < j; k++) {
        arrayOfDoubleMatrix1D[k].assign(arrayOfDoubleMatrix1D[j], Functions.minusMult(this.L.getQuick(j, k)));
      }
    }
    return localDoubleMatrix2D;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str = "Illegal operation or error: ";
    localStringBuffer.append("--------------------------------------------------------------------------\n");
    localStringBuffer.append("CholeskyDecomposition(A) --> isSymmetricPositiveDefinite(A), L, inverse(A)\n");
    localStringBuffer.append("--------------------------------------------------------------------------\n");
    localStringBuffer.append("isSymmetricPositiveDefinite = ");
    try
    {
      localStringBuffer.append(String.valueOf(isSymmetricPositiveDefinite()));
    }
    catch (IllegalArgumentException localIllegalArgumentException1)
    {
      localStringBuffer.append(str + localIllegalArgumentException1.getMessage());
    }
    localStringBuffer.append("\n\nL = ");
    try
    {
      localStringBuffer.append(String.valueOf(getL()));
    }
    catch (IllegalArgumentException localIllegalArgumentException2)
    {
      localStringBuffer.append(str + localIllegalArgumentException2.getMessage());
    }
    localStringBuffer.append("\n\ninverse(A) = ");
    try
    {
      localStringBuffer.append(String.valueOf(solve(DoubleFactory2D.dense.identity(this.L.rows()))));
    }
    catch (IllegalArgumentException localIllegalArgumentException3)
    {
      localStringBuffer.append(str + localIllegalArgumentException3.getMessage());
    }
    return localStringBuffer.toString();
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.linalg.CholeskyDecomposition
 * JD-Core Version:    0.7.0.1
 */