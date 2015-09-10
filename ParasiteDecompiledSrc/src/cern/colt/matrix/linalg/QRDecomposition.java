package cern.colt.matrix.linalg;

import cern.colt.function.DoubleDoubleFunction;
import cern.colt.matrix.DoubleFactory2D;
import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.jet.math.Functions;
import java.io.Serializable;

public class QRDecomposition
  implements Serializable
{
  static final long serialVersionUID = 1020L;
  private DoubleMatrix2D QR;
  private int m;
  private int n;
  private DoubleMatrix1D Rdiag;
  
  public QRDecomposition(DoubleMatrix2D paramDoubleMatrix2D)
  {
    Property.DEFAULT.checkRectangular(paramDoubleMatrix2D);
    Functions localFunctions = Functions.functions;
    this.QR = paramDoubleMatrix2D.copy();
    this.m = paramDoubleMatrix2D.rows();
    this.n = paramDoubleMatrix2D.columns();
    this.Rdiag = paramDoubleMatrix2D.like1D(this.n);
    DoubleDoubleFunction localDoubleDoubleFunction = Algebra.hypotFunction();
    DoubleMatrix1D[] arrayOfDoubleMatrix1D1 = new DoubleMatrix1D[this.n];
    DoubleMatrix1D[] arrayOfDoubleMatrix1D2 = new DoubleMatrix1D[this.n];
    for (int i = 0; i < this.n; i++)
    {
      arrayOfDoubleMatrix1D1[i] = this.QR.viewColumn(i);
      arrayOfDoubleMatrix1D2[i] = this.QR.viewColumn(i).viewPart(i, this.m - i);
    }
    for (i = 0; i < this.n; i++)
    {
      double d1 = 0.0D;
      for (int j = i; j < this.m; j++) {
        d1 = Algebra.hypot(d1, this.QR.getQuick(j, i));
      }
      if (d1 != 0.0D)
      {
        if (this.QR.getQuick(i, i) < 0.0D) {
          d1 = -d1;
        }
        arrayOfDoubleMatrix1D2[i].assign(Functions.div(d1));
        this.QR.setQuick(i, i, this.QR.getQuick(i, i) + 1.0D);
        for (j = i + 1; j < this.n; j++)
        {
          DoubleMatrix1D localDoubleMatrix1D = this.QR.viewColumn(j).viewPart(i, this.m - i);
          double d2 = arrayOfDoubleMatrix1D2[i].zDotProduct(localDoubleMatrix1D);
          d2 = -d2 / this.QR.getQuick(i, i);
          for (int k = i; k < this.m; k++) {
            this.QR.setQuick(k, j, this.QR.getQuick(k, j) + d2 * this.QR.getQuick(k, i));
          }
        }
      }
      this.Rdiag.setQuick(i, -d1);
    }
  }
  
  public DoubleMatrix2D getH()
  {
    return Algebra.DEFAULT.trapezoidalLower(this.QR.copy());
  }
  
  public DoubleMatrix2D getQ()
  {
    Functions localFunctions = Functions.functions;
    DoubleMatrix2D localDoubleMatrix2D = this.QR.like();
    for (int i = this.n - 1; i >= 0; i--)
    {
      DoubleMatrix1D localDoubleMatrix1D1 = this.QR.viewColumn(i).viewPart(i, this.m - i);
      localDoubleMatrix2D.setQuick(i, i, 1.0D);
      for (int j = i; j < this.n; j++) {
        if (this.QR.getQuick(i, i) != 0.0D)
        {
          DoubleMatrix1D localDoubleMatrix1D2 = localDoubleMatrix2D.viewColumn(j).viewPart(i, this.m - i);
          double d = localDoubleMatrix1D1.zDotProduct(localDoubleMatrix1D2);
          d = -d / this.QR.getQuick(i, i);
          localDoubleMatrix1D2.assign(localDoubleMatrix1D1, Functions.plusMult(d));
        }
      }
    }
    return localDoubleMatrix2D;
  }
  
  public DoubleMatrix2D getR()
  {
    DoubleMatrix2D localDoubleMatrix2D = this.QR.like(this.n, this.n);
    for (int i = 0; i < this.n; i++) {
      for (int j = 0; j < this.n; j++) {
        if (i < j) {
          localDoubleMatrix2D.setQuick(i, j, this.QR.getQuick(i, j));
        } else if (i == j) {
          localDoubleMatrix2D.setQuick(i, j, this.Rdiag.getQuick(i));
        } else {
          localDoubleMatrix2D.setQuick(i, j, 0.0D);
        }
      }
    }
    return localDoubleMatrix2D;
  }
  
  public boolean hasFullRank()
  {
    for (int i = 0; i < this.n; i++) {
      if (this.Rdiag.getQuick(i) == 0.0D) {
        return false;
      }
    }
    return true;
  }
  
  public DoubleMatrix2D solve(DoubleMatrix2D paramDoubleMatrix2D)
  {
    Functions localFunctions = Functions.functions;
    if (paramDoubleMatrix2D.rows() != this.m) {
      throw new IllegalArgumentException("Matrix row dimensions must agree.");
    }
    if (!hasFullRank()) {
      throw new IllegalArgumentException("Matrix is rank deficient.");
    }
    int i = paramDoubleMatrix2D.columns();
    DoubleMatrix2D localDoubleMatrix2D = paramDoubleMatrix2D.copy();
    int k;
    for (int j = 0; j < this.n; j++) {
      for (k = 0; k < i; k++)
      {
        double d = 0.0D;
        for (int i2 = j; i2 < this.m; i2++) {
          d += this.QR.getQuick(i2, j) * localDoubleMatrix2D.getQuick(i2, k);
        }
        d = -d / this.QR.getQuick(j, j);
        for (i2 = j; i2 < this.m; i2++) {
          localDoubleMatrix2D.setQuick(i2, k, localDoubleMatrix2D.getQuick(i2, k) + d * this.QR.getQuick(i2, j));
        }
      }
    }
    for (j = this.n - 1; j >= 0; j--)
    {
      for (k = 0; k < i; k++) {
        localDoubleMatrix2D.setQuick(j, k, localDoubleMatrix2D.getQuick(j, k) / this.Rdiag.getQuick(j));
      }
      for (k = 0; k < j; k++) {
        for (int i1 = 0; i1 < i; i1++) {
          localDoubleMatrix2D.setQuick(k, i1, localDoubleMatrix2D.getQuick(k, i1) - localDoubleMatrix2D.getQuick(j, i1) * this.QR.getQuick(k, j));
        }
      }
    }
    return localDoubleMatrix2D.viewPart(0, 0, this.n, i);
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str = "Illegal operation or error: ";
    localStringBuffer.append("-----------------------------------------------------------------\n");
    localStringBuffer.append("QRDecomposition(A) --> hasFullRank(A), H, Q, R, pseudo inverse(A)\n");
    localStringBuffer.append("-----------------------------------------------------------------\n");
    localStringBuffer.append("hasFullRank = ");
    try
    {
      localStringBuffer.append(String.valueOf(hasFullRank()));
    }
    catch (IllegalArgumentException localIllegalArgumentException1)
    {
      localStringBuffer.append(str + localIllegalArgumentException1.getMessage());
    }
    localStringBuffer.append("\n\nH = ");
    try
    {
      localStringBuffer.append(String.valueOf(getH()));
    }
    catch (IllegalArgumentException localIllegalArgumentException2)
    {
      localStringBuffer.append(str + localIllegalArgumentException2.getMessage());
    }
    localStringBuffer.append("\n\nQ = ");
    try
    {
      localStringBuffer.append(String.valueOf(getQ()));
    }
    catch (IllegalArgumentException localIllegalArgumentException3)
    {
      localStringBuffer.append(str + localIllegalArgumentException3.getMessage());
    }
    localStringBuffer.append("\n\nR = ");
    try
    {
      localStringBuffer.append(String.valueOf(getR()));
    }
    catch (IllegalArgumentException localIllegalArgumentException4)
    {
      localStringBuffer.append(str + localIllegalArgumentException4.getMessage());
    }
    localStringBuffer.append("\n\npseudo inverse(A) = ");
    try
    {
      localStringBuffer.append(String.valueOf(solve(DoubleFactory2D.dense.identity(this.QR.rows()))));
    }
    catch (IllegalArgumentException localIllegalArgumentException5)
    {
      localStringBuffer.append(str + localIllegalArgumentException5.getMessage());
    }
    return localStringBuffer.toString();
  }
}


/* Location:           G:\ParasiteTrade\Parasite_20150226.jar
 * Qualified Name:     cern.colt.matrix.linalg.QRDecomposition
 * JD-Core Version:    0.7.0.1
 */